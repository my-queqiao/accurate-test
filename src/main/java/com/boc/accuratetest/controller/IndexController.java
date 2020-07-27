package com.boc.accuratetest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boc.accuratetest.acl.IndexRank;
import com.boc.accuratetest.annotation.SecurityIgnoreHandler;
import com.boc.accuratetest.annotation.SecurityManagement;
import com.boc.accuratetest.biz.ProductionTaskBiz;
import com.boc.accuratetest.biz.UserBiz;
import com.boc.accuratetest.constant.ProductionTaskSession;
import com.boc.accuratetest.pojo.Permission;
import com.boc.accuratetest.pojo.ProductionTask;
import com.boc.accuratetest.pojo.User;

import net.sf.json.JSONObject;

@Controller
public class IndexController {
	@Autowired
	private ProductionTaskBiz productionTaskBiz;
	@Autowired
	private UserBiz userBiz;
	/*
	 * 	登陆
	 */
	@SecurityIgnoreHandler
	@RequestMapping("/")
	public String login_index() {
		return "login_index";
	}
	@SecurityIgnoreHandler
	@RequestMapping("/register")
	@ResponseBody
	public JSONObject register(String productionTaskNumber) {
		productionTaskNumber = productionTaskNumber.trim();
		JSONObject json = new JSONObject();
		json.put("success", false);
		List<ProductionTask> pts = productionTaskBiz.findBy(productionTaskNumber);
		if(pts.isEmpty()) {
			// 存储
			ProductionTask pt = new ProductionTask();
			pt.setProductionTaskNumber(productionTaskNumber);
			productionTaskBiz.insert(pt);
		}else {
			json.put("msg", "已存在该编号，请直接登陆");
			return json;
		}
		json.put("msg", "注册成功");
		json.put("success", true);
		return json;
	}
	@SecurityIgnoreHandler
	@RequestMapping("/login")
	@ResponseBody
	public JSONObject login(String user,String password,HttpSession session) {
		user = user.trim();
		password = password.trim();
		JSONObject json = new JSONObject();
		json.put("success", false);
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		List<User> users = userBiz.findByNameAndPassword(user,password);
		if(users.isEmpty()) {
			json.put("msg", "不存在该用户，请先注册");
			return json;
		}else {
			// 登陆成功，将用户信息、及其拥有的权限存放到session中。
			// 权限用户——》角色——》权限
			List<Permission> ps = userBiz.getPermissionsBy(users.get(0));
			session.setAttribute(ProductionTaskSession.permissionsOfLoginUser, ps);
			session.setAttribute(ProductionTaskSession.loginUser, users.get(0));
		}
		json.put("msg", "登陆成功");
		json.put("success", true);
		return json;
	}
	@SecurityIgnoreHandler
	@RequestMapping("/loginOut")
	public String loginOut(HttpSession session) {
		session.setAttribute(ProductionTaskSession.loginUser, null);
		session.setAttribute(ProductionTaskSession.permissionsOfLoginUser, null);
		return "redirect:/";
	}
	/*
	 * 	首页
	 */
	@SecurityManagement(IndexRank.class)
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
}
