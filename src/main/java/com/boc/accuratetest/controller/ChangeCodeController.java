package com.boc.accuratetest.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.boc.accuratetest.acl.ChangeCodeRank;
import com.boc.accuratetest.acl.RecommendTestExampleRank;
import com.boc.accuratetest.annotation.SecurityIgnoreHandler;
import com.boc.accuratetest.annotation.SecurityManagement;
import com.boc.accuratetest.biz.ChangeCodeBiz;
import com.boc.accuratetest.biz.MethodChainOriginalBiz;
import com.boc.accuratetest.biz.ProductionTaskBiz;
import com.boc.accuratetest.biz.TestingExampleBiz;
import com.boc.accuratetest.constant.NotLoginInException;
import com.boc.accuratetest.constant.NotSelectProductionTaskException;
import com.boc.accuratetest.constant.ProductionTaskSession;
import com.boc.accuratetest.pojo.ChangeCode;
import com.boc.accuratetest.pojo.MethodChainOriginal;
import com.boc.accuratetest.pojo.ProductionTask;
import com.boc.accuratetest.pojo.TestingExample;
import com.boc.accuratetest.pojo.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("changeCode")
public class ChangeCodeController {
	Logger logger=LoggerFactory.getLogger(ChangeCodeController.class);
	@Value("${release_diff_url}")
	private String release_diff_url;
	@Value("${release_branch_url}")
	private String release_branch_url;
	@Autowired
	private ChangeCodeBiz changeCodeBiz;
	@Autowired
	private TestingExampleBiz testingExampleBiz;
	@Autowired
	private ProductionTaskBiz productionTaskBiz;
	@Autowired
	private MethodChainOriginalBiz methodChainOriginalBiz;
	// < gitUrl  ,  分支名称 >
	Map<String,JSONObject> giturlRefBranchtag = new ConcurrentHashMap<String, JSONObject>();
	/**
	 * 变更代码首页
	 * @return
	 */
	@SecurityManagement(ChangeCodeRank.class)
	@RequestMapping("index")
	public String index(HttpSession session) {
		//HttpServletRequest request = ( (ServletRequestAttributes)RequestContextHolder.getRequestAttributes() ).getRequest();
		return "changeCode_index";
	}
	/**
	 * 展示差异代码
	 * @param pageNumber 页码
	 * @param pageSize 每页行数
	 * @param dataOfPart 部分数据 ：增加、修改、删除、全部
	 * @param search
	 * @return
	 */
	@SecurityManagement(ChangeCodeRank.class)
	@RequestMapping("getAll")
	@ResponseBody
	public JSONObject getList(Integer pageNumber,Integer pageSize,Integer search,Byte dataOfPart,HttpSession session) {
		JSONObject json = new JSONObject();
		User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		if(StringUtils.isEmpty(productionTaskNumber)) {
			throw new NotSelectProductionTaskException("请选择一个生产任务编号");
		}
		List<ChangeCode> page = changeCodeBiz.page(pageNumber, pageSize, search,dataOfPart,productionTaskNumber);
		Integer total = changeCodeBiz.findTotal(search,dataOfPart,productionTaskNumber);
		
		// 查找关联的测试用例。变更表——方法链表——方法链ref用例表——用例表(package_name暂存测试用例id)
		List<ChangeCode> links = changeCodeBiz.findChangeCodeLinkTestExample(productionTaskNumber);
		for (ChangeCode pg : page) {
			StringBuilder sb = new StringBuilder();
			for (ChangeCode link : links) {
				if(pg.getId().intValue() == link.getId().intValue()) {
					sb.append(link.getTestExampleId()+",");
				}
			}
			String linkTestExample = sb.toString();
			if(!StringUtils.isEmpty(linkTestExample)) {
				String substring = linkTestExample.substring(0, linkTestExample.lastIndexOf(","));
				pg.setLinkTestExample(substring);
			}
		}
		
		json.put("rows", page);
		json.put("total", total);
		return json;
	}
	/**
	 * 调用python接口，获取差异代码，存入数据库
	 * @param git_url
	 * @param master_branch
	 * @param test_branch
	 * @return
	 */
	@SecurityManagement(ChangeCodeRank.class)
	@RequestMapping("getChangeData")
	@ResponseBody
	public JSONObject getChangeData(String git_url,String master_branch,String test_branch,HttpSession session) {
		git_url = git_url.trim();
		JSONObject jsonRes = new JSONObject();
		jsonRes.put("success", false);
		
		// 每一个生产任务编号，只能关联一次、一个，git地址、稳定分支、测试分支
		User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		if(StringUtils.isEmpty(productionTaskNumber)) {
			throw new NotSelectProductionTaskException("您未选择生产任务编号");
		}
		// 获取分支、tag。	1、分支、tag不能比较。	2、两个tag可以比较
		JSONObject json2 = giturlRefBranchtag.get(git_url);
		//JSONObject branchJson = json2.getJSONObject("branch");
		JSONObject tagJson = json2.getJSONObject("tag");
		
		if(!tagJson.isNullObject() && !tagJson.isEmpty() &&
				tagJson.containsValue(master_branch) && !tagJson.containsValue(test_branch)){
			// 分支、和tag不能比较
			jsonRes.put("res", "分支和tag不能直接比较");
			return jsonRes;
		}else if(!tagJson.isNullObject() && !tagJson.isEmpty() &&
				!tagJson.containsValue(master_branch) && tagJson.containsValue(test_branch)){
			// 分支、和tag不能比较
			jsonRes.put("res", "分支和tag不能直接比较");
			return jsonRes;
		}
		
		List<ProductionTask> pts = productionTaskBiz.findBy(productionTaskNumber);
		if(pts.size() > 0) {
			ProductionTask pt = pts.get(0);
			if(StringUtils.isEmpty(pt.getGitUrl())) {
				// git地址为空，是第一次获取数据，可以获取数据
				
			}else if(git_url.equals(pt.getGitUrl()) 
					&& master_branch.equals(pt.getMasterBranch()) 
					&& test_branch.equals(pt.getTestBranch())) {
				// git地址，两个分支或tag相同，可以重新获取数据
				
			}else if(!tagJson.isNullObject() && !tagJson.isEmpty() &&
					tagJson.containsValue(master_branch) && tagJson.containsValue(test_branch)){
				// 如果本次分支是两个tag进行比较，可以获取数据
				
			}else{
				jsonRes.put("res", "当前生产任务编号已经有数据了");
				return jsonRes;
			}
		}
		
		// 如果数据库中已有该生产任务编号下的差异代码，先全部删除
		changeCodeBiz.deleteByProductionTaskNumber(productionTaskNumber);
		ResponseEntity<String> response = null;
		try {
			response = conn(git_url, master_branch, test_branch);
		} catch (RestClientException e) {
			jsonRes.put("res", "连接python服务时出现异常："+e.getMessage());
			return jsonRes;
		}
		// 返回的数据
	    String res = response.getBody(); 
	    JSONObject json = JSONObject.fromObject(res);
	    JSONObject info = json.getJSONObject("info");
	    if(!info.getString("success").equals("true")) {
	    	info.getString("reason");
	    	jsonRes.put("res", "获取数据失败："+info.getString("reason"));
	    	return jsonRes;
	    }
	    json.remove("info");
		Collection<JSONObject> values = json.values();
		// 存储数据
		try {
			insertBatch(productionTaskNumber,values);
		} catch (Exception e) {
			jsonRes.put("res", "解析、存储数据时出现异常："+e.getMessage());
			return jsonRes;
		}
		
		// 每一个生产任务编号，对应唯一的git地址、稳定分支、测试分支。（不记录tag）
		if(!tagJson.containsValue(master_branch) && !tagJson.containsValue(test_branch)) {
			productionTaskBiz.updateByProductionTaskNumber(productionTaskNumber,git_url,master_branch,test_branch);
		}
		
		jsonRes.put("success", true);
		jsonRes.put("res", "获取数据成功！");
		return jsonRes;
		
		// java实现
		// 1、再次克隆目标项目，单独存放，并且检出测试版分支代码
//		String gen = System.getProperty("user.dir");
//		gen = "C:\\Users\\tom\\Desktop"; // 测试，上线后需要修改
//		String targetProjectName = git_url.substring(git_url.lastIndexOf("/")+1, git_url.lastIndexOf(".git"));
//		String fileSaveUrl = gen+"/"+targetProjectName+"_"+test_branch; // 重命名
//		deleteFolder(fileSaveUrl);// fileSaveUrl，目标文件夹如果已存在，先删掉
//		CloneCommand cloneCommand = Git.cloneRepository();
//		cloneCommand.setURI(git_url);
//		cloneCommand.setDirectory(new File(fileSaveUrl)); // saolei是项目根目录
//		cloneCommand.setBare(false);
//		cloneCommand.setCloneAllBranches(true); // 克隆了所有分支，但默认只是检出了master分支，其余分支需要分别检出。
//		try {
//			cloneCommand.call();
//			
//			Git git2 = Git.open(new File(fileSaveUrl));
//			CheckoutCommand checkout = git2.checkout(); // 检出命令
//			test_branch = "origin/"+test_branch; // 克隆之后，本地分支的名称默认示例：master、origin/test
//			checkout.setName(test_branch); // 指定检出的分支
//			checkout.call();
//		} catch (InvalidRemoteException e) {
//			e.printStackTrace();
//		} catch (TransportException e) {
//			e.printStackTrace();
//		} catch (GitAPIException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		// 2、获取差异文件(fileSaveUrl中有master、和已经检出的测试分支代码)
//		String diffMethod = diffMethod(fileSaveUrl, master_branch, test_branch);
//		System.out.println("diffMethod============="+diffMethod);
//		// 解析差异文件，并和源码对比，找出新增、删除、和修改的方法
//		return null;
		
	}
	public static String diffMethod(String URL,String masterBranch, String testBranch){
		StringBuilder sb = new StringBuilder();
		Git git = null;
		try {
			git=Git.open(new File(URL));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Repository repository=git.getRepository();
		ObjectReader reader = repository.newObjectReader();
		CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
	
		try {
			ObjectId old = repository.resolve(masterBranch + "^{tree}");
			ObjectId head = repository.resolve(testBranch+"^{tree}");
			oldTreeIter.reset(reader, old);
			CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
			newTreeIter.reset(reader, head);
			List<DiffEntry> diffs= git.diff()
                    .setNewTree(newTreeIter)
                    .setOldTree(oldTreeIter)
                    .call();
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DiffFormatter df = new DiffFormatter(out);
			df.setRepository(git.getRepository());
			System.out.println("差异文件个数："+diffs.size());
			for (DiffEntry diffEntry : diffs) {
		         df.format(diffEntry);
		         String diffText = out.toString("UTF-8");
			     sb.append(diffText+"\n");
			     out.reset();
			}
			
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 * 获取指定仓库的所有分支
	 * @param git_url
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@SecurityManagement(ChangeCodeRank.class)
	@RequestMapping("getBranchList")
	@ResponseBody
	public JSONObject getBranchList(String git_url) {
		git_url = git_url.trim();
		JSONObject jsonRes = new JSONObject();
		jsonRes.put("success", false);
		ResponseEntity<String> response = null;
		try {
			response = connForBranchs(git_url);
		} catch (RestClientException e) {
			jsonRes.put("res", "连接python服务时出现异常："+e.getMessage());
			return jsonRes;
		}
		// 返回的数据
		String res = response.getBody(); 
		JSONObject json = JSONObject.fromObject(res);
		giturlRefBranchtag.put(git_url, json);
		JSONObject branchJson = json.getJSONObject("branch");
		JSONObject tagJson = json.getJSONObject("tag");
		
		Collection valuesBranch = branchJson.values();
		Collection valuesTag = tagJson.values();
		
		List<String> branchs = new ArrayList<>();
		Iterator iter = valuesBranch.iterator();
		while(iter.hasNext()) {
			String v = (String)iter.next();
			byte[] bytes = null;
			try {
				bytes = v.getBytes("gbk");
				v = new String(bytes,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			branchs.add(v);
		}
		if(!valuesTag.isEmpty()) {
			Iterator iter2 = valuesTag.iterator();
			while(iter2.hasNext()) {
				String v = (String)iter2.next();
				byte[] bytes = null;
				try {
					bytes = v.getBytes("gbk");
					v = new String(bytes,"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				branchs.add(v);
			}
		}
		jsonRes.put("success", true);
		jsonRes.put("list", branchs);
		return jsonRes;
		
		// java实现
//		JSONObject jsonRes = new JSONObject();
//		jsonRes.put("success", false);
//		String gen = System.getProperty("user.dir");
//		gen = "C:\\Users\\tom\\Desktop"; // 测试，上线后需要修改
//		String targetProjectName = git_url.substring(git_url.lastIndexOf("/")+1, git_url.lastIndexOf(".git"));
//		String fileSaveUrl = gen+"/"+targetProjectName;
//		deleteFolder(fileSaveUrl);// fileSaveUrl，目标文件夹如果已存在，先删掉
//		CloneCommand cloneCommand = Git.cloneRepository();
//		cloneCommand.setURI(git_url);
//		cloneCommand.setDirectory(new File(fileSaveUrl)); // saolei是项目根目录
//		cloneCommand.setBare(false);
//		cloneCommand.setCloneAllBranches(true); // 克隆了所有分支，但默认只是检出了master分支，其余分支需要分别检出。
//		try {
//			cloneCommand.call();
//		} catch (InvalidRemoteException e) {
//			e.printStackTrace();
//		} catch (TransportException e) {
//			e.printStackTrace();
//		} catch (GitAPIException e) {
//			e.printStackTrace();
//		}
//		// 获得远程分支
//		List<String> branchs = new ArrayList<>();
//		try {
//			Git git2 = Git.open(new File(fileSaveUrl));
//			ListBranchCommand listBranchCommand = git2.branchList();
//			listBranchCommand.setListMode(ListMode.REMOTE); // 远程仓库
//			List<Ref> call = listBranchCommand.call();
//			for (Ref ref : call) {
//				// refs/remotes/origin/test
//				String name = ref.getName();
//				String name2 = name.substring(name.lastIndexOf("/")+1);
//				branchs.add(name2);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (GitAPIException e) {
//			e.printStackTrace();
//		}
//		jsonRes.put("success", true);
//		jsonRes.put("list", branchs);
//		return jsonRes;
	}
	public static boolean deleteFolder(String url) {
        File file = new File(url);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            file.delete();
            return true;
        } else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                String root = files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径
                //System.out.println(root);
                deleteFolder(root);
            }
            file.delete();
            return true;
        }
    }
	private ResponseEntity<String> conn(String git_url,String master_branch,String test_branch) throws RestClientException{
		RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
	    map.add("git_url", git_url);
	    map.add("master", master_branch);
	    map.add("dev", test_branch);
	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
	    ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity( release_diff_url, request , String.class );
		} catch (RestClientException e) {
			throw e;
		}
		return response;
	}
	private ResponseEntity<String> connForBranchs(String git_url) throws RestClientException{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
		map.add("git_url", git_url);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity( release_branch_url, request , String.class );
		} catch (RestClientException e) {
			throw e;
		}
		return response;
	}
	private void insertBatch(String productionTaskNumber,Collection<JSONObject> values) {
		List<ChangeCode> ccs = new ArrayList<>();
		for (JSONObject js : values) {
			ChangeCode cc = new ChangeCode();
			cc.setProductionTaskNumber(productionTaskNumber);
			cc.setPackageName(js.getString("package_name").replace("\\", "."));
			cc.setJavabeanName(js.getString("class_name"));
			cc.setMethodName(js.getString("func_name"));
			JSONArray jsonArray = js.getJSONArray("agrs");
			if(jsonArray.isEmpty()) {
				cc.setParamType("");
			}else if(jsonArray.size() == 1){
				cc.setParamType(jsonArray.getString(0));
			}else {
				StringBuilder sb = new StringBuilder();
				for (int i=0;i<jsonArray.size();i++) {
					sb.append(jsonArray.getString(i));
					if(jsonArray.size()-1 != i)sb.append(",");
				}
				cc.setParamType(sb.toString());
			}
			String changeType = js.getString("change_type");
			if(changeType.equals("add"))cc.setChangeType((byte)1);
			if(changeType.equals("delete"))cc.setChangeType((byte)2);
			if(changeType.equals("change"))cc.setChangeType((byte)3);
			String body = js.getString("content");
			if(body.length() >= 4000) {
				body = body.substring(0, 3999);
			}
			cc.setMethodBody(body);
			ccs.add(cc);
		}
		changeCodeBiz.insertBatch(ccs);
	}
	/**
	 * 	统计方法的增删改的个数
	 * @return
	 */
	@SecurityManagement(ChangeCodeRank.class)
	@RequestMapping("statistics")
	@ResponseBody
	public JSONObject statistics(HttpSession session) {
		JSONObject json = new JSONObject();
		User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		if(StringUtils.isEmpty(productionTaskNumber)) {
			throw new NotSelectProductionTaskException("您未选择生产任务编号");
		}
		List<ChangeCode> statistics = changeCodeBiz.countByChangeType(productionTaskNumber);
		json.put("data", statistics);
		return json;
	}
	/**
	 * 查看某个变更方法关联的案例
	 * @param testExampleIds 变更方法关联的案例id
	 * @return
	 */
	@SecurityManagement(ChangeCodeRank.class)
	@RequestMapping("getLinkTestExample")
	@ResponseBody
	public JSONObject getLinkTestExample(String testExampleIds) {
		JSONObject json = new JSONObject();
		String[] ids = testExampleIds.split(",");
		List<Integer> teids = new ArrayList<>();
		for (String id : ids) {
			teids.add(Integer.valueOf(id));
		}
		List<TestingExample> tes = testingExampleBiz.findByIds(teids);
		json.put("list", tes);
		json.put("success", true);
		return json;
	}
	/**
	 * 推荐案例页面
	 * @return
	 */
	@SecurityManagement(RecommendTestExampleRank.class)
	@RequestMapping("recommend_testExample")
	public String recommend_testExample() {
		return "recommend_testExample";
	}
	
	/**
	 * 	推荐测试用例（考虑方法链）
	 * @return
	 */
	@SecurityManagement(RecommendTestExampleRank.class)
	@RequestMapping("recommendTestExample")
	@ResponseBody
	public JSONObject recommendTestExample(Integer pageNumber,Integer pageSize,String search,HttpSession session) {
		JSONObject json = new JSONObject();
		User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		if(StringUtils.isEmpty(productionTaskNumber)) {
			throw new NotSelectProductionTaskException("请选择一个生产任务编号");
		}
		List<ChangeCode> list = changeCodeBiz.findChangeCodeLinkTestExample(productionTaskNumber);
		// list.get(0).getId(); // 变更表主键id
		// list.get(0).getTestExampleId(); // 测试用例主键id
		// list.get(0).getMcoid(); // 知识库表主键id
		Set<Integer> testExampleIds = new HashSet<>(); // 收集所有的案例id
		Set<String> mcoids = new HashSet<>(); // 收集所有的知识库主键id（）
		for (ChangeCode cc : list) {
			testExampleIds.add(Integer.valueOf(cc.getTestExampleId()));
			mcoids.add(cc.getMcoid());
		}
		// 获取方法链
		List<MethodChainOriginal> mcos = methodChainOriginalBiz.getMethodLinkByTestExampleIds(testExampleIds);
		// 案例去重、合并
		/**
		 *	以每一个变更方法当作最底层方法往上找方法链，这个方法链就作为案例合并、去重的基本数据
		 *	然后，先在同一个案例中合并、去重，再在不同的案例间合并、去重
		 *	方法链样式：
		 *	案例1："方法链1，方法链2，方法链3"	案例2："方法链4，方法链5，方法链6"
		 *	方法链1样式：
		 *	"abc" // 那边是父节点待定，注意：两个方法链必须先比较父节点是否相等
		 *	链内方法分隔符，三个字符	:	$-$		不能用英文逗号	,	因为参数类型使用了
		 */
		// <案例id , 以变更方法为底的方法链集合>
		Map<Integer,List<String>> testExampleRefUpdateMethodLinks = new HashMap<>();
		for (String mcoid : mcoids) { // mcoids 变更方法在知识库中，对应的主键ids
			StringBuilder link = new StringBuilder();
			Integer testExampleId = null;
			testExampleId = lastMethod(mcoid, mcos, link,testExampleId); // 找到该变更方法（为底）所在的方法链，及案例id
			// 
			List<String> list2 = testExampleRefUpdateMethodLinks.get(testExampleId);
			if(null == list2) {
				list2 = new ArrayList<>();
			}
			list2.add(link.toString().substring(0, link.toString().length()-3)); //  (去掉最后的链内方法分隔符:$-$)
			testExampleRefUpdateMethodLinks.put(testExampleId, list2);
		}
		// System.out.println("最初数据："+testExampleRefUpdateMethodLinks);
		// 整理数据
		Map<Integer,List<String>> testExampleRefUpdateMethodLinks2 = new HashMap<>();
		for (Entry<Integer, List<String>> entry : testExampleRefUpdateMethodLinks.entrySet()) {
			List<String> links = entry.getValue();
			List<String> links2 = new ArrayList<>();
			
			for (String link : links) {
				// 改成父节点在前面
				StringBuilder sb = new StringBuilder();
				String[] methods = link.split("\\$-\\$"); // 不能用英文	,	分割，因为参数类型使用	,	分割的
				for(int i=methods.length-1;i>=0;i--) {
					sb.append(methods[i]);
					if(i != 0) sb.append("$-$");
				}
				links2.add(sb.toString());
			}
			
			testExampleRefUpdateMethodLinks2.put(entry.getKey(), links2);
		}
		// System.out.println("整理数据2，已改成父节点在前："+testExampleRefUpdateMethodLinks2);
		/*
		 241=[
		 com.boc.accuratetest.controller.IndexController.getPuductionTaskNumber(HttpSession),
		 com.boc.accuratetest.biz.impl.ProductionTaskBizImpl.getAll(),
		 com.boc.accuratetest.pojo.ProductionTask.setMasterBranch(String), 
		 // 上下一共两个方法链。（链中方法也是用英文逗号分割的，看上去混淆了，集合中数据没有混淆）
		 // 每一个方法链都是按照一个单独的字符串存储的，所以放到set中方法链中的方法调用顺序也不会乱
		 com.boc.accuratetest.controller.IndexController.getPuductionTaskNumber(HttpSession),
		 com.boc.accuratetest.biz.impl.ProductionTaskBizImpl.getAll(),
		 com.boc.accuratetest.pojo.ProductionTask.setTestBranch(String)],
		 */
		// 去重、去包含
		// 1、同一个案例去重、去包含（有可能有重复、包含的数据，比如：方法链：123.2、3都是变更方法，那么就有12、123两个方法链。）
		Map<Integer,Set<String>> testExampleRefUpdateMethodLinks3 = new HashMap<>();
		Set<Integer> keySet = testExampleRefUpdateMethodLinks2.keySet();
		for (Integer teid : keySet) {
			List<String> list2 = testExampleRefUpdateMethodLinks2.get(teid);
			// 去重、去包含
			List<String> removes = new ArrayList<>();
			for (String link : list2) {
				for (String link2 : list2) {
					if(//link.substring(0, 1).equals(link2.subSequence(0, 1)) &&
							 !link2.equals(link) && link2.contains(link) ) {
						// 包含关系，去掉link
						removes.add(link);
						break;
					}
				}
			}
			// 从list2中去掉removes
			Set<String> links = new HashSet<>();
			for (String linkOld : list2) {
				if(!removes.contains(linkOld)) {
					links.add(linkOld);
				}
			}
			testExampleRefUpdateMethodLinks3.put(teid, links); // 收集去重后的数据
		}
		// System.out.println("整理数据3，同一个案例下去重、去包含："+testExampleRefUpdateMethodLinks3);
		/**
		 * 	 不同案例间去重、去包含，并推荐案例
		 *  1、先找出所有需要测试的方法链（去重，去包含）
		 *	2、从拥有方法链最多的案例找起，再次多，直到找全需要测试的方法链，并推荐对应的案例
		 */
		Set<String> linksQuchong = new HashSet<>();
		for (Set<String> links : testExampleRefUpdateMethodLinks3.values()) {
			for (String link : links) {
				boolean repeat = false;
				// 遍历所有方法链，如果没有方法链包含link（规则举例：三个方法链abc、ab、bc，  abc包含ab，但不包含bc），就收集
				// 修改规则：推荐的案例只要能够测试到变更方法所在的方法链就可以，所以abc包含ab，也包含bc
				for (Set<String> links2 : testExampleRefUpdateMethodLinks3.values()) {
					for (String link2 : links2) {
						// link、link2比较，只查看link2是否包含link即可。
						if(//methods2[0].equals(methods[0]) && 
								link2.contains(link) && !link2.equals(link)) {
							// 满足这两个条件，link就是被包含的，不收集
							repeat = true;
							break;
						}
					}
					if(repeat) break;
				}
				if(repeat == false) linksQuchong.add(link); // 收集
			}
		}
		// System.out.println("linksQuchong:"+linksQuchong);
		// 推荐案例，从拥有方法链最多的案例开始找，再次多，直到覆盖了所有的待测试方法链：linksQuchong
		Set<Integer> recommendTeids = new HashSet<>();
		// 推荐案例计算		recommendByLink	推荐案例的主键id
		// Set<Integer> recommendByLink = recommendByLink(testExampleRefUpdateMethodLinks3, linksQuchong,recommendTeids);
		Set<Integer> recommendByLink = recommendByLink2(testExampleRefUpdateMethodLinks3, linksQuchong,recommendTeids);
		
		/*// 推荐的案例id
		Set<String> recommend = new HashSet<>();
		for (Integer teid : recommendByLink) {
			recommend.add(String.valueOf(teid));
		}
		Set<String> recommend2 = recommend2(recommend);
		json.put("list", recommend2);*/
		
		// 改成返回案例的全部内容
		List<Integer> testExampleIds2 = new ArrayList<>();
		for (Integer testExampleId : testExampleIds) {
			testExampleIds2.add(testExampleId);
		}
		List<TestingExample> tes = testingExampleBiz.findByIds(testExampleIds2);
		for (TestingExample te : tes) {
			if(recommendByLink.contains(te.getId())) {
				te.setTeType(2); // 推荐案例
				continue;
			}
		}
		// 排序，推荐案例放到前面
		Collections.sort(tes, new Comparator<TestingExample>() {
			@Override
			public int compare(TestingExample o1, TestingExample o2) {
				int a = o1.getTeType() - o2.getTeType();
				return -a;
			}
		});
		// 分页 pageSize: 10	pageNumber: 1
		
		json.put("rows", tes);
		json.put("total", tes.size());
		return json;
	}
	/**
	 * 	推荐案例（依据修改方法所在的方法链进行推荐）
	 * @param testExampleRefUpdateMethodLinks3	用例id 对应 修改方法所在的方法链
	 * @param linksQuchong	也是所有待测的方法链
	 * @return 
	 */
	private Set<Integer> recommendByLink(Map<Integer,Set<String>> testExampleRefUpdateMethodLinks3
			,Set<String> linksQuchong,Set<Integer> recommendTeids) {
		
		Map<Integer,Integer> linksnumberRefTeid = new HashMap<>();
		for (Entry<Integer, Set<String>> entry : testExampleRefUpdateMethodLinks3.entrySet()) {
			linksnumberRefTeid.put(entry.getValue().size(),entry.getKey());
		}
		//System.out.println("linksnumberRefTeid:"+linksnumberRefTeid);
		Integer max = Collections.max(linksnumberRefTeid.keySet());
		Integer teid = linksnumberRefTeid.get(max);
		Set<String> links = testExampleRefUpdateMethodLinks3.get(teid);
		// 先查看links中是否拥有linksQuchong中的任意一个，如果有一个就推荐？ 并且把该案例已覆盖的方法链从linksQuchong中剔除。
		// 再处理次多的案例，次多的案例不一定拥有linksQuchong中的方法链，所以要查看是否拥有
		boolean recommendCurrentTeid = false;
		for (String link : links) {
			if(linksQuchong.contains(link)) {
				recommendCurrentTeid = true;
				linksQuchong.remove(link); // 剔除
			}
		}
		testExampleRefUpdateMethodLinks3.remove(teid); // 剔除当前案例（不管当前案例有没有包含当前的待测试方法链）
		if(recommendCurrentTeid) {
			recommendTeids.add(teid);
		}
		if(linksQuchong.isEmpty()) {
			return recommendTeids;
		}
		return recommendByLink(testExampleRefUpdateMethodLinks3, linksQuchong,recommendTeids);
	}
	/**
	 * 	推荐案例（依据修改方法所在的方法链进行推荐）
	 * @param testExampleRefUpdateMethodLinks3	用例id 对应 修改方法所在的方法链
	 * @param linksQuchong	也是所有待测的方法链
	 * @return 
	 */
	private Set<Integer> recommendByLink2(Map<Integer,Set<String>> testExampleRefUpdateMethodLinks3
			,Set<String> linksQuchong,Set<Integer> recommendTeids) {
		
		Map<Integer,Integer> linksnumberRefTeid = new HashMap<>();
		for (Entry<Integer, Set<String>> entry : testExampleRefUpdateMethodLinks3.entrySet()) {
			linksnumberRefTeid.put(entry.getValue().size(),entry.getKey());
		}
		//System.out.println("linksnumberRefTeid:"+linksnumberRefTeid);
		Integer max = Collections.max(linksnumberRefTeid.keySet());
		Integer teid = linksnumberRefTeid.get(max);
		Set<String> links = testExampleRefUpdateMethodLinks3.get(teid);
		// 先查看links中是否拥有linksQuchong中的任意一个，如果有一个就推荐？ 并且把该案例已覆盖的方法链从linksQuchong中剔除。
		// 再处理次多的案例，次多的案例不一定拥有linksQuchong中的方法链，所以要查看是否拥有
		boolean recommendCurrentTeid = false;
		for (String link : links) {
			if(linksQuchong.contains(link)) {
				recommendCurrentTeid = true; // 只要该links有一个link在linksQuchong中就推荐
				linksQuchong.remove(link); // 剔除
			}
		}
		testExampleRefUpdateMethodLinks3.remove(teid); // 剔除当前案例（不管当前案例有没有包含当前的待测试方法链）
		Map<Integer,Set<String>> temp = new HashMap<>();
		if(recommendCurrentTeid) {
			recommendTeids.add(teid);
			/**优化：从testExampleRefUpdateMethodLinks3中，剔除掉推荐过的案例对应的方法链。
			 * 这样查找下一个含待测试案例最多时，就准确了，因为剔除之后剩下的方法链都是待测试的*/
			for (Entry<Integer, Set<String>> entry : testExampleRefUpdateMethodLinks3.entrySet()) {
				Set<String> oldLinks = entry.getValue();
				Set<String> newLinks = new HashSet<>();
				for (String oldLink : oldLinks) {
					if(!links.contains(oldLink)) {
						// 如果已推荐的方法链不包含该剩余方法链，就保留下来
						newLinks.add(oldLink);
					}
				}
				temp.put(entry.getKey(), newLinks);
			}
		}
		testExampleRefUpdateMethodLinks3 = temp; // 替换
		if(linksQuchong.isEmpty()) {
			return recommendTeids;
		}
		return recommendByLink2(testExampleRefUpdateMethodLinks3, linksQuchong,recommendTeids);
	}
	public static void main(String[] args) {
		Map<Integer,Set<String>> map = new HashMap<>();
		Set<String> set1 = new HashSet<>();
		set1.add("a,b,c");
		set1.add("a,m,n");
		set1.add("x,y,z");
		set1.add("y,z");
		map.put(234, set1);
		Set<String> set2 = new HashSet<>();
		set2.add("a,b,c");
		set2.add("m,n,z");
		map.put(235, set2);
		Set<String> set3 = new HashSet<>();
		set3.add("a,b,c");
		set3.add("m,n,z");
		map.put(236, set3);
		Set<String> set4 = new HashSet<>();
		set4.add("a,b");
		set4.add("m,n");
		map.put(237, set4);
		Set<String> set5 = new HashSet<>();
		set5.add("a,c");
		set5.add("b,c");
		map.put(238, set5);
		System.out.println(map);
		// 不用父节点的方式
		Set<String> linksQuchong = new HashSet<>();
		for (Set<String> links : map.values()) {
			for (String link : links) {
				String[] methods = link.split(",");
				boolean repeat = false;
				// 遍历所有方法链，如果没有方法链包含link（规则举例：三个方法链abc、ab、bc，  abc包含ab，但不包含bc），就收集
				for (Set<String> links2 : map.values()) {
					for (String link2 : links2) {
						String[] methods2 = link2.split(",");
						// link、link2比较，只查看link2是否包含link即可。
						if(//methods2[0].equals(methods[0]) && 
								link2.contains(link) 
								&& !link2.equals(link)
								) {
							// 满足这三个条件，link就是被包含的，不收集
							repeat = true;
							break;
						}
					}
					if(repeat) break;
				}
				if(repeat == false) linksQuchong.add(link); // 收集
			}
		}
		System.out.println("linksQuchong:"+linksQuchong);
	}
	private Integer lastMethod(String mcoid,List<MethodChainOriginal> mcos, StringBuilder link,Integer testExampleId) {
		for (MethodChainOriginal mco : mcos) { // 知识库（案例筛选过了，只包含变更方法对应的案例）
			// 开始找每一个变更方法为底的方法链，需要确定方法链属于那个案例
			if(mco.getId().equals(mcoid)) {
				
				StringBuilder sb = new StringBuilder();
				sb.append(mco.getPackageName()).append(".").append(mco.getJavabeanName())
					.append(".").append(mco.getMethodName()).append("(").append(mco.getParamType()).append(")");
				
				link.append(sb.toString()+"$-$");
				testExampleId = mco.getTestingExampleId();
				String lastMethodId = mco.getLastMethodId(); // 需要用递归，直到LastMethodId为空
				mcoid = lastMethodId;
				break;
			}
		}
		if(StringUtils.isEmpty(mcoid)) {
			return testExampleId;
		}
		return lastMethod(mcoid, mcos, link,testExampleId);
	}
	public void removeLinkNotHasUpdateMethod(List<MethodChainOriginal> mcos) {
		// 方法链去重，去包含。（只计算变更方法所在的链的去重、去包含，其他不包含变更方法的链不用管。
		// 所以发现了重复、被包含，整个用例就可以剔除了）
		// 分割用例?
		int latchCount = 0;
		for (MethodChainOriginal mco : mcos) {
			if(StringUtils.isEmpty(mco.getLastMethodId())) {
				latchCount++;
			}
		}
		CountDownLatch latch = new CountDownLatch(latchCount);
		for (MethodChainOriginal mco : mcos) {
			// 不管测试案例，以方法链为单位，剔除不包含变更方法的方法链？有必要吗？
			if(StringUtils.isEmpty(mco.getLastMethodId())) {
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						List<MethodChainOriginal> father = new ArrayList<>();
						father.add(mco);
						List<MethodChainOriginal> links = new ArrayList<>();
						links.add(mco);
						findLink(mcos, father, links);
						for (MethodChainOriginal link : links) {
							StringBuilder sb = new StringBuilder();
							sb.append(link.getPackageName()).append(".").append(link.getJavabeanName())
								.append(".").append(link.getMethodName()).append("(").append(link.getParamType()).append(")");
							System.out.println(sb.toString());
						}
						System.out.println("=====================================================================================");
						latch.countDown();
					}
				}).start();
				
			}
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(mcos.size());
	}
	// 找出指定父节点所在方法链的所有方法
	private void findLink(List<MethodChainOriginal> mcos,List<MethodChainOriginal> father,List<MethodChainOriginal> links) {
		for (MethodChainOriginal shang : father) {
			List<MethodChainOriginal> shang2 = new ArrayList<>();
			for (MethodChainOriginal mco : mcos) {
				if(mco.getLastMethodId().equals(shang.getId())) {
					// 下一级
					links.add(mco);
					shang2.add(mco);
				}
			}
			father = shang2;
		}
		if(!father.isEmpty()) {
			findLink(mcos, father, links);
		}
	}
	/**
	 * 	推荐测试用例第一种方式（不使用这种方式，太简单）
	 * @return
	 */
	@SecurityManagement(RecommendTestExampleRank.class)
	@RequestMapping("recommendTestExample_1")
	@ResponseBody
	public JSONObject recommendTestExample_1(HttpSession session) {
		JSONObject json = new JSONObject();
		User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		if(StringUtils.isEmpty(productionTaskNumber)) {
			throw new NotSelectProductionTaskException("请选择一个生产任务编号");
		}
		List<ChangeCode> list = changeCodeBiz.findChangeCodeLinkTestExample(productionTaskNumber);
		// list.get(0).getId(); // 变更表主键id
		// list.get(0).getTestExampleId(); // 测试用例表主键id
		// <"'用例1id','用例2id'"   ,   变更表主键id> // 用例组———》及其对应的某个变更方法
		Map<String, Integer> teidRefCcid = teidRefCcid(list);
		// 测试用例去重，得到所有的测试用例
		Set<String> keyQuchong = new HashSet<>();
		for (String key : teidRefCcid.keySet()) {
			String[] split = key.split(",");
			for (String s : split) {
				keyQuchong.add(s);
			}
		}
		// 一、暂不使用这样的方式推荐用例
		Set<String> recommend = new HashSet<>();
		recommend(keyQuchong, teidRefCcid, recommend); 
		// 推荐的测试用例(数字是用例表主键id，@表示或者)：[234@235@236,240,260]
		System.out.println("推荐的测试用例："+recommend); // 推荐的测试用例
		// 返回相应的用例的编号
		Set<String> recommend2 = recommend2(recommend);
		json.put("list", recommend2);
		return json;
	}
	/**
	 * 	推荐测试用例第二种方式（使用了方法链）
	 * @return
	 */
	@SecurityManagement(RecommendTestExampleRank.class)
	@RequestMapping("recommendTestExample_2")
	@ResponseBody
	public JSONObject recommendTestExample_2(HttpSession session) {
		JSONObject json = new JSONObject();
		User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		if(StringUtils.isEmpty(productionTaskNumber)) {
			throw new NotSelectProductionTaskException("请选择一个生产任务编号");
		}
		List<ChangeCode> list = changeCodeBiz.findChangeCodeLinkTestExample(productionTaskNumber);
		// list.get(0).getId(); // 变更表主键id
		// list.get(0).getTestExampleId(); // 测试用例表主键id
		Set<String> updateMethods = new HashSet<>();// 获取所有的变更方法
		for (ChangeCode cc : list) {
			StringBuilder sb = new StringBuilder();
			sb.append(cc.getPackageName()).append(".").append(cc.getJavabeanName())
				.append(".").append(cc.getMethodName()).append("(").append(cc.getParamType()).append(")");
			updateMethods.add(sb.toString());
		}
		Set<String> updateMethodIds = new HashSet<>();// 获取所有的变更方法主键id
		for (ChangeCode cc : list) {
			updateMethodIds.add(String.valueOf(cc.getId()));
		}
		/*
		 * 	二、推荐测试用例时，用上方法链
		 */
		// 得到<用例id,"'变更方法id','变更方法id'">
		Map<Integer, String> teidRefCcid2 = teidRefCcid2(list);
		/** updateMethodIds所有的变更方法id ，teidRefCcid2各个测试用例	  对应	它的所有变更方法id（到此数据准备好了，接下来是用哪些方式来推荐）	*/
		// 只要计算出来哪个、或哪几个用例能覆盖所有变更方法id，就可以了。
		// 先统计各个用例对应的变更方法个数
		Map<Integer, Integer> teidRefCcid2Count = new HashMap<>();
		Set<Integer> keySet = teidRefCcid2.keySet();
		for (Integer teid : keySet) {
			String ccids = teidRefCcid2.get(teid);
			teidRefCcid2Count.put(teid, ccids.split(",").length);
		}
		Set<String> recommend = new HashSet<>();
		// 测试时teidRefCcid2会存在value值相等的情况，实际可能不会出现这种情况。（<234,"2001">,<235,"2001">）
		recommend_2(updateMethodIds, teidRefCcid2, teidRefCcid2Count,recommend);
		// 返回相应的用例的编号
		Set<String> recommend2 = recommend2(recommend);
		json.put("list", recommend2);
		return json;
	}
	private static void recommend_2(Set<String> updateMethodIds,Map<Integer, String> teidRefCcid2,
			Map<Integer, Integer> teidRefCcid2Count,Set<String> recommend){
		// 先推荐拥有变更方法个数最多的用例，如果没有全覆盖，再根据个数依次推荐，直到全覆盖。
		int maxnum = Collections.max(teidRefCcid2Count.values()); // 
		Integer temp = null;
		for(Entry<Integer, Integer> entry1:teidRefCcid2Count.entrySet()) {
			if(entry1.getValue().intValue() == maxnum) {
				Integer key = entry1.getKey();
				recommend.add(String.valueOf(key));
				temp = key;
				break;
			}
		}
		// 剔除最大计数
		teidRefCcid2Count.remove(temp);
		// 查看是否还有未覆盖的变更方法，如果有就继续推荐
		Set<String> coveredMethodIds = new HashSet<>();
		for (String teid : recommend) {
			String ccids = teidRefCcid2.get(Integer.valueOf(teid));
			String[] split = ccids.split(",");
			for (String id : split) {
				coveredMethodIds.add(id);
			}
		}
		if(coveredMethodIds.size() == updateMethodIds.size()) {
			return ;
		}else {
			recommend_2(updateMethodIds, teidRefCcid2, teidRefCcid2Count,recommend);
		}
	}
	public Set<String> recommend2(Set<String> recommend) {
		// 根据推荐的测试用例主键，获取其测试案例编号
		List<Integer> testExampleIds = new ArrayList<>();
		for (String r : recommend) {
			String[] split = r.split("@");
			for (String s : split) {
				testExampleIds.add(Integer.valueOf(s));
			}
		}
		if(testExampleIds.isEmpty()) {
			return new HashSet<>();
		}
		List<TestingExample> tes = testingExampleBiz.findByIds(testExampleIds);
		Set<String> recommend2 = new HashSet<>();
		for (String r : recommend) {
			if(r.contains("@")) {
				String[] split = r.split("@");
				StringBuilder sb = new StringBuilder();
				for (String teid : split) {
					for(TestingExample te : tes) {
						if(Integer.valueOf(teid).intValue() == te.getId().intValue()) {
							sb.append(te.getTestCaseNumber()+"@");
						}
					}
				}
				recommend2.add(sb.toString());
			}else {
				for(TestingExample te : tes) {
					if(Integer.valueOf(r).intValue() == te.getId().intValue()) {
						recommend2.add(te.getTestCaseNumber());
						break;
					}
				}
			}
		}
		System.out.println(recommend2);
		return recommend2;
	}
	/**
	 * 	整理数据，得到：用例——》对应的所有变更方法 	: <用例1主键	,	"'变更表主键','变更表主键'">
	 * @param list
	 * @return
	 */
	private static Map<Integer, String> teidRefCcid2(List<ChangeCode> list) {
		
		// <用例1主键	,	"'变更表主键','变更表主键'"> // 用例———》及其对应的所有变更方法
		Map<Integer, String> teidRefCcid = new HashMap<>();
		for (ChangeCode cc : list) {
			String teid = teidRefCcid.get(Integer.valueOf(cc.getTestExampleId()));
			if(StringUtils.isEmpty(teid)) {
				teidRefCcid.put(Integer.valueOf(cc.getTestExampleId()), String.valueOf(cc.getId()));
			}else {
				teidRefCcid.put(Integer.valueOf(cc.getTestExampleId()), teid+","+cc.getId());
			}
		}
		return teidRefCcid;
	}
	/**
	 *	 整理数据，得到：用例组——》变更方法 : <"'用例1主键','用例2主键'"   ,   变更表主键>
	 * @param list
	 * @return
	 */
	private Map<String, Integer> teidRefCcid(List<ChangeCode> list) {
		Map<Integer,Integer> ccidCount = new HashMap<>();
		List<String> ss = new ArrayList<>();
		for (ChangeCode c : list) {
			Integer ccid = c.getId();
			if(null != ccidCount.get(ccid) && ccidCount.get(ccid) >= 1) {
				continue;
			}
			ccidCount.put(ccid, (ccidCount.get(ccid)==null?0:ccidCount.get(ccid)) +1);
			StringBuilder sb = new StringBuilder();
			sb.append(ccid+":");
			for (ChangeCode c2 : list) {
				if(ccid.intValue() == c2.getId().intValue()) {
					sb.append(c2.getTestExampleId()+",");
				}
			}
			String substring = sb.toString().substring(0, sb.toString().length()-1);
			ss.add(substring);
		}
		// <"'用例1id','用例2'"   ,   变更表主键id> // 用例组———》及其对应的某个变更方法
		Map<String,Integer> teidRefCcid = new HashMap<>(); 
		for (String s : ss) {
			String[] split = s.split(":");
			teidRefCcid.put(split[1], Integer.valueOf(split[0]));
		}
		return teidRefCcid;
	}
	/**
	 * 	当前逻辑：从用例组中查找出现次数最高的用例，并推荐。然后剔除包含该用例的用例组。再从头递归，直到teidRefCcid为空。
	 * 	特殊情况：某个用例组含有多个（出现次数为1的）用例，都推荐了。这属于重复推荐，应该一起推荐，让用户自己选择执行哪个用例
	 * @param keyQuchong  全部测试用例（去重后）
	 * @param teidRefCcid 用例组———》及其对应的某个变更方法	
	 * @param recommend   推荐的用例
	 */
	private static void recommend(Set<String> keyQuchong,Map<String,Integer> teidRefCcid,Set<String> recommend) {
		// 剔除后查看是否还有未推荐的用例（对应的方法没有测试到）
		if(teidRefCcid.size() <= 0) {
			return; // 结束推荐
		}
		// 计算每一个测试用例出现的次数
		Map<String,Integer> map = new HashMap<>();
		for (String s : keyQuchong) {
			for (String key : teidRefCcid.keySet()) {
				String[] split = key.split(",");
				for (String k : split) {
					if(k.equals(s)) {
						map.put(s, (map.get(s)==null?0:map.get(s)) +1);
						break;
					}
				}
			}
		}
		//System.out.println("每一个测试用例出现的次数："+map); // 每一个测试用例出现的次数：{用例5=1, 用例9=1, 用例2=1}	（比如：用例2、9应该二选一）
		// 拿到出现次数最大的测试用例
		int maxnum = Collections.max(map.values()); // 
		for(Entry<String, Integer> entry1:map.entrySet()) {
			if(entry1.getValue().intValue() == maxnum) {
				// recommend.add(entry1.getKey());// 推荐该测试用例
				if(maxnum == 1) { // 不能直接收集
				
					// 收集用例组中确实有多个用例(至此teidRefCcid中剩余的用例都只出现一次)的情况。
					Set<Entry<String,Integer>> entrySet = teidRefCcid.entrySet();
					for (Entry<String,Integer> entry2 : entrySet) {
						String key = entry2.getKey();
						if(key.contains(",") && key.contains(entry1.getKey())) {
							recommend.add(key.replace(",", "@")); // 有重复数据，用set去重
						}
						if(!key.contains(",") && key.equals(entry1.getKey())) {
							recommend.add(entry1.getKey());// 推荐该测试用例
						}
					}
				}
				if(maxnum > 1) {
					recommend.add(entry1.getKey());// 推荐该测试用例
				}
				/** 推荐一次之后，就应该剔除含有该用例的用例组合
				 	将含有推荐测试用例的用例组合剔除	 */
				Set<String> keySet = teidRefCcid.keySet();
				List<String> keyLeft = new ArrayList<>(); // 收集现有key
				for (String string : keySet) {
					keyLeft.add(string);
				}
				List<String> recommend2 = new ArrayList<>(); // 目前推荐的测试用例
				for(String r : recommend) {
					String[] split = r.split("@");
					for (String s : split) {
						recommend2.add(s);
					}
				}
				for (String key : keyLeft) {
					String[] split = key.split(",");
					for (String s : split) {
						if(recommend2.contains(s)) {
							teidRefCcid.remove(key); // 剔除
						}
					}
				}
				recommend(keyQuchong, teidRefCcid, recommend);// 递归推荐	
				break; // 出现次数相同的用例，只推荐一个
			}
		}
		
	}
}


