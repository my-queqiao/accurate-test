package com.boc.accuratetest.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boc.accuratetest.constant.MethodInfo;
import com.boc.accuratetest.constant.MethodInfoUtil;

public class CengjiDiaoyong_3 {
	public static void main(String[] args) throws Exception {
		String gen = System.getProperty("user.dir");
		//listAllFile(new File(gen+"/src/main/java"));
		String fileName = "E:\\my-workspace\\accurate-test\\src\\main\\java\\com\\boc\\accuratetest\\controller\\IndexController.java";
		String content = MethodInfoUtil.readFileContent(fileName); // 已去除注释
//		System.out.println(content);
		List<MethodInfo> methodInfos = MethodInfoUtil.getMethodInfo(content);
		System.out.println(methodInfos);
	}
	public static void listAllFile(File f) throws Exception {
		File[] files = f.listFiles();
		for (File file : files) {
			//System.out.println(file);
			String fileLujin = file.toString();
			
			int end=fileLujin.lastIndexOf(".java");
			if(end==fileLujin.length()-5){ // 必须是java文件
				System.out.println(fileLujin); 
			}
			 /**层级调用问题。*/
			
			if (file.isDirectory()) {
				listAllFile(file);
			}
		}
	}
	
	/**
	 * 	获取指定文件的内容
	 * @param fileName 文件的绝对路路径
	 * @return
	 */
	public static String readFileContent(String fileName) {
	    File file = new File(fileName);
	    BufferedReader reader = null;
	    StringBuffer sbf = new StringBuffer();
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempStr;
	        while ((tempStr = reader.readLine()) != null) {
	            sbf.append(tempStr+"\r\n");
	        }
	        reader.close();
	        return sbf.toString();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    return sbf.toString();
	}
	
	/**
	 * 	获取包路径、类名、方法名、参数
	 * @param a "public static java.sql.Connection com.example.demo.CengjiDiaoyong2.conn(java.lang.String) throws java.lang.Exception"
	 * @return 
	 */
	public static Map<String, String> getZiduan(String a) {
		Map<String,String> map = new HashMap<String, String>();
		String packageUrl = null;
		String javabeanName = null;
		String methodName = null;
		String param = null;
		if(a.contains("throws")) {
			String a2 = a.split("throws")[0].trim();
			String[] split = a2.split(" ");
			String a3 = split[split.length-1]; // com.example.demo.CengjiDiaoyong2.conn()
			if(a3.contains("()")) {
				String[] split2 = a3.split("\\."); // .是转义字符？
				javabeanName = split2[split2.length-2];
				System.out.println(javabeanName);
				String[] split3 = a3.split("\\."+javabeanName);
				packageUrl = split3[0];
				System.out.println(packageUrl);
				methodName = split3[1].substring(1,split3[1].length()-2);
				System.out.println(methodName);
			}else { // 有参数
				String[] split2 = a3.split("\\(");
				param = split2[1].substring(0,split2[1].length()-1);
				System.out.println(param);
				String baoBeanMethod = split2[0];// com.example.demo.CengjiDiaoyong2.readFileContent
				String[] split3 = baoBeanMethod.split("\\.");
				javabeanName = split3[split3.length-2];
				System.out.println(javabeanName);
				String[] split4 = baoBeanMethod.split("\\."+javabeanName+".");
				packageUrl = split4[0];
				methodName = split4[1];
				System.out.println(packageUrl);
				System.out.println(methodName);
			}
		}else { // 没有throws
			String[] split = a.split(" ");
			String a2 = split[split.length-1]; // com.example.demo.CengjiDiaoyong2.conn(java.lang.String)
			if(a.contains("()")) {
				String[] split2 = a2.split("\\."); // .是转义字符？
				javabeanName = split2[split2.length-2];
				System.out.println(javabeanName);
				String[] split3 = a2.split("\\."+javabeanName); // com.example.pojo.Lei.getLeiIds()
				packageUrl = split3[0];
				System.out.println(packageUrl);
				methodName = split3[1].substring(1,split3[1].length()-2);
				System.out.println(methodName);
			}else { // 有参数
				String[] split2 = a2.split("\\(");
				param = split2[1].substring(0,split2[1].length()-1);
				System.out.println(param);
				String baoBeanMethod = split2[0];// com.example.demo.CengjiDiaoyong2.readFileContent
				String[] split3 = baoBeanMethod.split("\\.");
				javabeanName = split3[split3.length-2];
				System.out.println(javabeanName);
				String[] split4 = baoBeanMethod.split("\\."+javabeanName+".");
				packageUrl = split4[0];
				methodName = split4[1];
				System.out.println(packageUrl);
				System.out.println(methodName);
			}
		}
		map.put("packageUrl", packageUrl);
		map.put("javabeanName", javabeanName);
		map.put("methodName", methodName);
		map.put("param", param);
		return map;
	}
}
