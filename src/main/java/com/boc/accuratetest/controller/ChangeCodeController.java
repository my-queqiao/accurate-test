package com.boc.accuratetest.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.ChangedCharSetException;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.boc.accuratetest.annotation.SecurityIgnoreHandler;
import com.boc.accuratetest.biz.ChangeCodeBiz;
import com.boc.accuratetest.pojo.ChangeCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("changeCode")
public class ChangeCodeController {
	@Autowired
	private ChangeCodeBiz changeCodeBiz;
	@Value("${release_diff_url}")
	private String release_diff_url;
	@Value("${release_branch_url}")
	private String release_branch_url;
	/**
	 * 精准测试项目首页
	 * @return
	 */
	@SecurityIgnoreHandler
	@RequestMapping("index")
	public String index() {
		HttpServletRequest request = ( (ServletRequestAttributes)RequestContextHolder.getRequestAttributes() ).getRequest();
		String remoteAddr = request.getRemoteAddr();
		System.out.println("remoteAddr:"+remoteAddr);
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
	public JSONObject getList(Integer pageNumber,Integer pageSize,Integer search,Byte dataOfPart) {
		JSONObject json = new JSONObject();
		List<ChangeCode> page = changeCodeBiz.page(pageNumber, pageSize, search,dataOfPart);
		Integer total = changeCodeBiz.findTotal(search,dataOfPart);
		
		// 查找关联的测试用例。变更表——方法链表——方法链ref用例表——用例表(package_name暂存测试用例名称)
		List<ChangeCode> links = changeCodeBiz.findChangeCodeLinkTestExample();
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
	public JSONObject getChangeData(String git_url,String master_branch,String test_branch) {
		git_url = git_url.trim();
		// 如果数据库中已有该git仓库、版本号下的差异代码，先全部删除
		changeCodeBiz.deleteByGitUrlAndBranchs(git_url+","+master_branch+","+test_branch);
		JSONObject jsonRes = new JSONObject();
		jsonRes.put("success", false);
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
			String gitUrl= git_url+","+master_branch+","+test_branch;
			insertBatch(gitUrl,values);
		} catch (Exception e) {
			jsonRes.put("res", "解析、存储数据时出现异常："+e.getMessage());
			return jsonRes;
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
	public static void main(String[] args) {
		String diffMethod = diffMethod("C:\\Users\\tom\\Desktop\\springboot-saolei_test", "master", "origin/test");
		System.out.println("diffMethod==="+diffMethod);
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
	private void insertBatch(String gitUrl,Collection<JSONObject> values) {
		List<ChangeCode> ccs = new ArrayList<>();
		for (JSONObject js : values) {
			ChangeCode cc = new ChangeCode();
			cc.setGitUrl(gitUrl);
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
	public JSONObject statistics() {
		JSONObject json = new JSONObject();
		List<ChangeCode> statistics = changeCodeBiz.countByChangeType();
		json.put("data", statistics);
		return json;
	}
}































