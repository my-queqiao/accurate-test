package com.boc.accuratetest.demo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.boc.accuratetest.pojo.MethodChainOriginal;

public class StackTraceInfo {
	public static void main12(String[] args) throws IOException {
		String a = "1597389605765.com.boc.accuratetest.controller.IndexController.login(String,String,HttpSession)\r\n" + 
				"57595&1597389605765.java.lang.Thread.getStackTrace\r\n" + 
				"1597389606030.com.boc.accuratetest.controller.IndexController.login\r\n" + 
				"1597389606030.com.boc.accuratetest.biz.impl.UserBizImpl.findByNameAndPassword(String,String)\r\n" + 
				"41314&1597389606030.java.lang.Thread.getStackTrace\r\n" + 
				"1597389606035.com.boc.accuratetest.controller.IndexController.login\r\n" + 
				"1597389606035.com.boc.accuratetest.biz.impl.UserBizImpl.findByNameAndPassword\r\n" + 
				"1597389606035.com.boc.accuratetest.pojo.UserExample.createCriteria()\r\n" + 
				"69478&1597389606035.java.lang.Thread.getStackTrace";
		System.out.println(a);
		System.out.println("=========================================================");
		// 
		String[] split = a.split("[1—9]{5}&[0-9]{13}java.lang.Thread.getStackTrace\r\n"); // 每一个方法打印的链
		List<String> linesForFindParams = new ArrayList<>();
		for (String linkOfEveryMethod : split) { // 遍历每一个方法的方法链
			String[] lines = linkOfEveryMethod.split("\r\n");
			for (int i=0;i<lines.length;i++) {
				String line = lines[i].substring(lines[i].indexOf(".")+1, lines[i].length());
				System.out.println("line:"+line);
				if("java.lang.Thread.getStackTrace".equals(line)) {
					continue;
				}
				linesForFindParams.add(line);
				//System.out.println("line========"+line);
				if(!line.contains("(")) {
					// 补齐参数类型：后头找，使用第二个遇到的同包、同类、同方法名，并且有参数类型的（重载方法嵌套调用的，不能这样找，以后再解决）
					for(int j=linesForFindParams.size()-1;j>=0;j--) {
						if(linesForFindParams.get(j).contains(line+"(")) {
							line = linesForFindParams.get(j);
						}
					}
				}
				// 转为对象
				MethodChainOriginal insertPrepare = insertPrepare(line);
				
			}
		}
		System.out.println("===========================================================");
		
	}
	/**
	 * 	解析方法链中的一行
	 * @param methodStr com.boc.accuratetest.biz.impl.TestingExampleBizImpl.page(String,Integer)
	 * @return
	 */
	private static MethodChainOriginal insertPrepare(String methodStr) {
		MethodChainOriginal m = new MethodChainOriginal();
    	String params = methodStr.substring(methodStr.indexOf("(")+1, methodStr.indexOf(")")); // 参数
    	String split = methodStr.split("\\(")[0];
    	String[] split2 = split.split("\\.");
    	String methodName = split2[split2.length-1]; // 方法名
    	String className = split2[split2.length-2]; // 类名
    	String packageName = methodStr.substring(0, methodStr.indexOf(className)-1); // 包路径
    	m.setPackageName(packageName);
    	m.setJavabeanName(className);
    	m.setMethodName(methodName);
    	m.setParamType(params);
    	return m;
	}
	public static void main(String[] args) {
//		String a = "com.boc.accuratetest.biz.impl.TestingExampleBizImpl.page(String,Integer)";
//		insertPrepare(a);
		System.out.println("223");
		System.out.println(UUID.randomUUID().toString());
	}
}
