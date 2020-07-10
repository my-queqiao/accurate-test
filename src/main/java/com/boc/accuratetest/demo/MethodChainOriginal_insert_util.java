package com.boc.accuratetest.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.boc.accuratetest.pojo.MethodChainOriginal;
public class MethodChainOriginal_insert_util {
	// 方法链数据文件，本地地址
	public final static String filename = "C:\\Users\\tom\\Desktop\\chazhuang.txt";
	public final static String url = "jdbc:mysql://39.106.188.246:3306/accurate_testing";
	public final static String user = "root";
	public final static String password = "131122ab";
	
	
	public static void main(String[] args) throws Exception {
		List<MethodChainOriginal> ms = readFileContent(filename);
		insertBatch(ms);
	}
	public static Connection conn() throws Exception {
		//1。加载驱动
		Class.forName("com.mysql.jdbc.Driver");     
		//2.获取连接conn
		Connection conn=DriverManager.getConnection(url, user,password );
		return conn;
	}
	public static void insertBatch(List<MethodChainOriginal> ms) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = conn();
			for(MethodChainOriginal m:ms) {
				stmt = conn.prepareStatement(
						"INSERT INTO method_chain_original(package_url,javabean_name,method_name,"
						+ "param_type,call_time,call_time_long) VALUES(?,?,?,?,?,?)");
				stmt.setString(1, m.getPackageName()); // 包路径
				stmt.setString(2, m.getJavabeanName()); // 类名称
				stmt.setString(3, m.getMethodName()); // 方法名称
				stmt.setString(4, m.getParamType()==null || m.getParamType().equals("")?"empty":m.getParamType()); // 参数类型
				stmt.setString(5, m.getCallTime()); // 
				stmt.setLong(6, m.getCallTimeLong()); // 
				//stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
				int i = 0;
				try {
					i = stmt.executeUpdate();
				} catch (Exception e) {
					continue;
				}
				if(i>0){
					System.out.println("success");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(stmt, conn);
		}
	}
	public static void close(Statement stmt,Connection conn) {
		if(stmt!=null){
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
		}
		stmt = null;
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
		}
		conn = null;
		}
	}
	/**
	 * 	获取指定文件的内容
	 * @param fileName 文件的绝对路路径
	 * @return
	 */
	public static List<MethodChainOriginal> readFileContent(String fileName) {
		List<MethodChainOriginal> ms = new ArrayList<>();
	    File file = new File(fileName);
	    BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempStr = null;
	        while ((tempStr = reader.readLine()) != null) {
	        	// 1591580665410.com.example.controller.SaoLeiController.sl(javax.servlet.http.HttpSession,int)
	        	// 1591580649397.com.example.controller.Index.index()
	        	MethodChainOriginal m = new MethodChainOriginal();
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
	        	ms.add(m);
	        }
	        reader.close();
	        return ms;
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
	    return ms;
	}
}
