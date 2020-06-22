package com.boc.accuratetest.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class User {
	public static void main(String[] args) {
//		RestTemplate restTemplate = new RestTemplate();
////		String url = "http://192.168.8.100:8000/deal_diff";
//		String url = "http://192.168.8.100:8000/branch_list";
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//	    MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
//	    map.add("git_url", "https://github.com/my-queqiao/springboot-saolei.git");
////	    map.add("master", "master");
////	    map.add("dev", "dev");
//	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//	    ResponseEntity<String> response = null;
//		try {
//			response = restTemplate.postForEntity( url, request , String.class );
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//	    String res = response.getBody(); // 返回数据
//	    System.out.println(res);
		// {"dev": "dev", "master": "master"}
		JSONObject json = JSONObject.fromObject("{\"dev\": \"dev\", \"master\": \"master\"}");
		Collection values = json.values();
		Iterator iter = values.iterator();
		List<String> branchs = new ArrayList<>();
		while(iter.hasNext()) {
			String v = (String)iter.next();
			branchs.add(v);
		}
		
	}
}
