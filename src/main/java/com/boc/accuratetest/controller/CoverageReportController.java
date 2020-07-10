package com.boc.accuratetest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boc.accuratetest.annotation.SecurityIgnoreHandler;
import com.boc.accuratetest.biz.AllMethodsBiz;
import com.boc.accuratetest.biz.MethodChainOriginalBiz;
import com.boc.accuratetest.biz.TestedMethodsBiz;
import com.boc.accuratetest.pojo.AllMethods;
import com.boc.accuratetest.pojo.AllMethodsRes;
import com.boc.accuratetest.pojo.PackageInfoRes;
import com.boc.accuratetest.pojo.TestedMethods;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("coverageReport")
public class CoverageReportController {
	@Autowired
	private AllMethodsBiz allMethodsBiz;
	@Autowired
	private MethodChainOriginalBiz methodChainOriginalBiz;
	@Autowired
	private TestedMethodsBiz testedMethodsBiz;
	/**
	 * 	跳转到知识库页面
	 * @return
	 */
	@SecurityIgnoreHandler
	@RequestMapping("index")
	public String knowledgeBase(String ipOnTestExample) {
		if(!StringUtils.isEmpty(ipOnTestExample)) {
			getAllMethodInfo(ipOnTestExample);
		}
		return "coverageReport";
	}
	/**
	 * 	覆盖率列表
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
		
		// 统计每个包的各自类数量、方法数量、增加方法数量，及其已测试数量
		List<AllMethods> statisticClassNumber = allMethodsBiz.statisticClassNumber();
		
		json.put("rows", statisticClassNumber);
		json.put("total", statisticClassNumber.size());
		return json;
	}
	public void getAllMethodInfo(String ipOnTestExample) {
		if(StringUtils.isEmpty(ipOnTestExample) ) {
			return ;
		}
		ipOnTestExample = ipOnTestExample.trim();
		Socket client = null;
    	OutputStream os = null;        
    	PrintWriter pw = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
		try {
        	client = new Socket(ipOnTestExample, 8765);
        	os = client.getOutputStream();
        	pw = new PrintWriter(os);
            pw.write("allMethodInfo.txt");
            pw.flush();
            client.shutdownOutput();// 关闭输出流
            is = client.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            List<AllMethods> ms = new ArrayList<>();
            String info = null;
            while ((info = br.readLine()) != null) {
            	AllMethods m = insertPrepare(info);
            	ms.add(m);
            }
            // 存储
            allMethodsBiz.insertBatch(ms);
        } catch (UnknownHostException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }finally {
            try {
            	br.close();
            	isr.close();
            	is.close();
            	pw.close();
            	os.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	/**
	 * 解析方法链中的一行
	 * @param tempStr com.boc.accuratetest.biz.impl.TestingExampleBizImpl.page(Integer,Integer,String)
	 * @return
	 */
	private AllMethods insertPrepare(String tempStr) {
		AllMethods m = new AllMethods();
		String params = tempStr.substring(tempStr.indexOf("(")+1, tempStr.indexOf(")")); // 参数
    	String split = tempStr.split("\\(")[0];
    	String[] split2 = split.split("\\.");
    	String methodName = split2[split2.length-1]; // 方法名
    	String className = split2[split2.length-2]; // 类名
    	String packageName = tempStr.substring(0,tempStr.indexOf(className)-1); // 包路径
    	m.setPackageName(packageName);
    	m.setJavabeanName(className);
    	m.setMethodName(methodName);
    	m.setParamType(params);
    	return m;
	}
	/**
	 * 解析方法链中的一行
	 * @param tempStr 1594003355480.com.boc.accuratetest.biz.impl.TestingExampleBizImpl.page(Integer,Integer,String)
	 * @return
	 */
	private TestedMethods insertPrepareForTestedMethods(String tempStr) {
		TestedMethods m = new TestedMethods();
    	String params = tempStr.substring(tempStr.indexOf("(")+1, tempStr.indexOf(")")); // 参数
    	String split = tempStr.split("\\(")[0];
    	String callTime = split.substring(0, split.indexOf(".")); // 调用时间
    	String[] split2 = split.split("\\.");
    	String methodName = split2[split2.length-1]; // 方法名
    	String className = split2[split2.length-2]; // 类名
    	String packageName = tempStr.substring(tempStr.indexOf(callTime)+callTime.length()+1, 
    			tempStr.indexOf(className)-1); // 包路径
    	m.setPackageName(packageName);
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
	 * 从测试服务获取已经测试过的方法
	 * @return 
	 */
	@SecurityIgnoreHandler
	@RequestMapping("getTestedMethods")
	@ResponseBody
	public JSONObject getTestedMethods(String testServerIp) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		testServerIp = testServerIp.trim();
		Socket client = null;
    	OutputStream os = null;        
    	PrintWriter pw = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
		try {
        	client = new Socket(testServerIp, 8765);
        	os = client.getOutputStream();
        	pw = new PrintWriter(os);
            pw.write("chazhuang.txt");
            pw.flush();
            client.shutdownOutput();// 关闭输出流
            is = client.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            List<TestedMethods> tms = new ArrayList<>();
            String info = null;
            while ((info = br.readLine()) != null) {
            	System.out.println("info:"+info);
            	TestedMethods tm = insertPrepareForTestedMethods(info);
            	tms.add(tm);
            }
            methodChainOriginalBiz.insertBatchForTestedMethods(tms);
        } catch (UnknownHostException e) {
        	e.printStackTrace();
        	return json;
        } catch (IOException e) {
        	e.printStackTrace();
        	return json;
        }catch(Exception e){
        	e.printStackTrace();
        	return json;
        }finally {
            try {
            	br.close();
            	isr.close();
            	is.close();
            	pw.close();
            	os.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		json.put("success", true);
		return json;
	}
}

