package com.boc.accuratetest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boc.accuratetest.annotation.SecurityIgnoreHandler;

import net.sf.json.JSONObject;

@Controller
public class Index {
	/*
	 * 默认首页
	 */
	@SecurityIgnoreHandler
	@RequestMapping("/")
	public String index() {
		return "redirect:changeCode/index";
	}
	@SecurityIgnoreHandler
	@RequestMapping("/test")
	@ResponseBody
	public JSONObject test() throws InterruptedException {
		JSONObject json = new JSONObject();
		json.put("success", true);
		Thread.currentThread().sleep(10000);
		return json;
	}
	
}
