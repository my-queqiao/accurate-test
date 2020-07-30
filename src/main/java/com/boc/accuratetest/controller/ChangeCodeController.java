package com.boc.accuratetest.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
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
import com.boc.accuratetest.annotation.SecurityIgnoreHandler;
import com.boc.accuratetest.biz.ChangeCodeBiz;
import com.boc.accuratetest.biz.ProductionTaskBiz;
import com.boc.accuratetest.biz.TestingExampleBiz;
import com.boc.accuratetest.constant.NotSelectProductionTaskException;
import com.boc.accuratetest.constant.ProductionTaskSession;
import com.boc.accuratetest.pojo.ChangeCode;
import com.boc.accuratetest.pojo.ProductionTask;
import com.boc.accuratetest.pojo.TestingExample;
import com.boc.accuratetest.pojo.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("changeCode")
public class ChangeCodeController {
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
	/**
	 * 精准测试项目首页
	 * @return
	 */
	@SecurityIgnoreHandler
	@RequestMapping("index")
	public String index() {
		/*HttpServletRequest request = ( (ServletRequestAttributes)RequestContextHolder.getRequestAttributes() ).getRequest();
		Object productionTaskNumber = request.getSession().getAttribute(ProductionTaskSession.number);
		if(null == productionTaskNumber) {
			throw new NotLoginInException("您尚未登陆");
		}*/
		return "cc_index";
	}
	/**
	 * 展示差异代码
	 * @param pageNumber 页码
	 * @param pageSize 每页行数
	 * @param dataOfPart 部分数据  增加、修改、删除、全部
	 * @param search
	 * @return
	 */
	@SecurityIgnoreHandler
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
					sb.append(link.getPackageName()+",");
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
	@SecurityIgnoreHandler
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
		List<ProductionTask> pts = productionTaskBiz.findBy(productionTaskNumber);
		if(pts.size() > 0) {
			ProductionTask pt = pts.get(0);
			if(pt.getGitUrl().equals(git_url) && pt.getMasterBranch().equals(master_branch) 
					&& pt.getTestBranch().equals(test_branch)) {
				// 重新获取数据
			}else {
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
		
		// 每一个生产任务编号，对应唯一的git地址、稳定分支、测试分支
		productionTaskBiz.updateByProductionTaskNumber(productionTaskNumber,git_url,master_branch,test_branch);
		
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
	@SecurityIgnoreHandler
	@RequestMapping("getBranchList")
	@ResponseBody
	public JSONObject getBranchList(String git_url) {
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
		Collection values = json.values();
		Iterator iter = values.iterator();
		List<String> branchs = new ArrayList<>();
		while(iter.hasNext()) {
			String v = (String)iter.next();
			branchs.add(v);
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
			cc.setMethodBody(js.getString("content"));
			ccs.add(cc);
		}
		changeCodeBiz.insertBatch(ccs);
	}
	/**
	 * 	统计方法的增删改的个数
	 * @return
	 */
	@SecurityIgnoreHandler
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
	
	@SecurityIgnoreHandler
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
	 * 	推荐测试用例
	 * @return
	 */
	@SecurityIgnoreHandler
	@RequestMapping("recommendTestExample")
	@ResponseBody
	public JSONObject recommendTestExample(HttpSession session) {
		JSONObject json = new JSONObject();
		User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		if(StringUtils.isEmpty(productionTaskNumber)) {
			throw new NotSelectProductionTaskException("请选择一个生产任务编号");
		}
		List<ChangeCode> list = changeCodeBiz.findChangeCodeLinkTestExample(productionTaskNumber);
		// list.get(0).getId(); // 变更表主键id
		// list.get(0).getPackageName(); // 测试用例表主键id（借用字段暂存）
		// <"'用例1','用例2'"   ,   变更表主键id> // 用例组———》及其对应的某个变更方法
		Map<String, Integer> teidRefCcid = teidRefCcid(list);
		// 测试用例去重，得到所有的测试用例
		Set<String> keyQuchong = new HashSet<>();
		for (String key : teidRefCcid.keySet()) {
			String[] split = key.split(",");
			for (String s : split) {
				keyQuchong.add(s);
			}
		}
		Set<String> recommend = new HashSet<>();
		recommend(keyQuchong, teidRefCcid, recommend);
		// 推荐的测试用例(数字是用例表主键id)：[234@235@236,240,260]
		System.out.println("推荐的测试用例："+recommend); // 推荐的测试用例
		// 根据推荐的测试用例主键，获取其测试案例编号
		List<Integer> testExampleIds = new ArrayList<>();
		for (String r : recommend) {
			String[] split = r.split("@");
			for (String s : split) {
				testExampleIds.add(Integer.valueOf(s));
			}
		}
		if(testExampleIds.isEmpty()) {
			json.put("list", new ArrayList<>());
			return json;
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
		json.put("list", recommend2);
		return json;
	}
	/**
	 * 整理数据，得到：用例组——》变更方法 : <"'用例1主键','用例2主键'"   ,   变更表主键>
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
					sb.append(c2.getPackageName()+",");
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
	public static void main(String[] args) {
		// <"'用例1','用例2'"   ,   变更表主键id>
		Map<String,Integer> teidRefCcid = new HashMap<>();
		teidRefCcid.put("用例1,用例4", 234);
		teidRefCcid.put("用例1", 235);
		teidRefCcid.put("用例1,用例2", 235);
		teidRefCcid.put("用例2,用例9", 236);
		teidRefCcid.put("用例19,用例12", 236);
		teidRefCcid.put("用例5", 236);
		System.out.println(teidRefCcid.size());
		// 得到所有测试用例，找出出现次数最高的用例
		
		// 测试用例去重，得到所有的测试用例
		Set<String> keyQuchong = new HashSet<>();
		for (String key : teidRefCcid.keySet()) {
			String[] split = key.split(",");
			for (String s : split) {
				keyQuchong.add(s);
			}
		}
		Set<String> recommend = new HashSet<>();
		recommend(keyQuchong, teidRefCcid, recommend);
		System.out.println("最终推荐："+recommend); // 最终推荐：[用例2@用例9, 用例5, 用例19@用例12, 用例1]
		
		
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
				if(key.contains(s)) {
					map.put(s, (map.get(s)==null?0:map.get(s)) +1);
				}
			}
		}
		//System.out.println("每一个测试用例出现的次数："+map); // 每一个测试用例出现的次数：{用例5=1, 用例9=1, 用例2=1}	（比如：用例2、9应该二选一）
		// 拿到出现次数最大的测试用例
		int maxnum = Collections.max(map.values()); // 
		StringBuilder sb = new StringBuilder();
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
			}
		}
		// 将含有推荐测试用例的用例组合剔除
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
	}
}


