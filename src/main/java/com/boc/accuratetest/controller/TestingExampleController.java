package com.boc.accuratetest.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boc.accuratetest.annotation.SecurityIgnoreHandler;
import com.boc.accuratetest.biz.MethodChainOriginalBiz;
import com.boc.accuratetest.biz.TestingExampleBiz;
import com.boc.accuratetest.pojo.MethodChainOriginal;
import com.boc.accuratetest.pojo.TestingExample;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("testingExample")
public class TestingExampleController {
	@Autowired
	private TestingExampleBiz testingExampleBiz;
	@Autowired
	private MethodChainOriginalBiz methodChainOriginalBiz;
	
	/**
	 * 	跳转到知识库页面
	 * @return
	 */
	@SecurityIgnoreHandler
	@RequestMapping("knowledgeBase")
	public String knowledgeBase(@ModelAttribute("success") String success, Model model) {
		System.out.println("拿到重定向得到的参数success:" + success);
		if(success == null || success.equals("")) success = "-1";
		model.addAttribute("success", success);
		return "knowledgeBase";
	}
	/**
	 * 	获取测试用例列表
	 * @param pageNumber
	 * @param pageSize
	 * @param search
	 * @return
	 */
	@SecurityIgnoreHandler
	@RequestMapping("getAll")
	@ResponseBody
	public JSONObject getAll(Integer pageNumber,Integer pageSize,String search) {
		JSONObject json = new JSONObject();
		List<TestingExample> page = testingExampleBiz.page(pageNumber, pageSize, search);
		Integer total = testingExampleBiz.findTotal(search);
		json.put("rows", page);
		json.put("total", total);
		return json;
	}
	/**
	 * 	上传记录方法链的文件
	 * @param file
	 * @param model
	 * @return
	 */
	@SecurityIgnoreHandler
	@PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,RedirectAttributes model) {
		model.addFlashAttribute("success", false);
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".txt")) {
        	return "redirect:/testingExample/knowledgeBase";
        }
        List<MethodChainOriginal> ms = new ArrayList<>();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
			inputStream = file.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String tempStr = null;
			while(null != ( tempStr=reader.readLine() ) ) {
				if(tempStr.length() > 13) { // 改行确定有内容，不仅仅含有一个回车符
					MethodChainOriginal m = insertPrepare(tempStr);
					ms.add(m);
				}
			}
			methodChainOriginalBiz.insertBatch(ms);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/testingExample/knowledgeBase";
		}finally {
			try {
				inputStream.close();
				reader.close();
			} catch (IOException e) {
			}
		}
        model.addFlashAttribute("success", true);
        return "redirect:/testingExample/knowledgeBase";
    }
	private MethodChainOriginal insertPrepare(String tempStr) {
		MethodChainOriginal m = new MethodChainOriginal();
    	String params = tempStr.substring(tempStr.indexOf("(")+1, tempStr.indexOf(")")); // 参数
    	String split = tempStr.split("\\(")[0];
    	String callTime = split.substring(0, split.indexOf(".")); // 调用时间
    	String[] split2 = split.split("\\.");
    	String methodName = split2[split2.length-1]; // 方法名
    	String className = split2[split2.length-2]; // 类名
    	String packageName = tempStr.substring(tempStr.indexOf(callTime)+callTime.length()+1, 
    			tempStr.indexOf(className)-1); // 包路径
    	m.setPackageUrl(packageName);
    	m.setJavabeanName(className);
    	m.setMethodName(methodName);
    	m.setParamType(params);
    	long l = Long.valueOf(callTime);
    	m.setCallTimeLong(l);
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
    	m.setCallTime(df.format(new Date(l)));
    	return m;
	}
	/**
	 * 	获取方法链
	 * @return
	 */
	@SecurityIgnoreHandler
	@RequestMapping("getAllMethodChainOriginal")
	@ResponseBody
	public JSONObject getAllMethodChainOriginal() {
		JSONObject json = new JSONObject();
		List<MethodChainOriginal> ms = methodChainOriginalBiz.getAll();
		int total = methodChainOriginalBiz.findTotal();
		json.put("rows", ms);
		json.put("total", total);
		return json;
	}
	/**
	 * 	测试用例关联方法链
	 */
	@SecurityIgnoreHandler
	@RequestMapping("exampleLinkMethodChain")
	@ResponseBody
	public void exampleLinkMethodChain() {
		
	}
}

