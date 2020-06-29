package com.boc.accuratetest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.ChangedCharSetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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































