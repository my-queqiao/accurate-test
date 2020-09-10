package com.boc.accuratetest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boc.accuratetest.acl.IndexRank;
import com.boc.accuratetest.acl.ProductionTaskNumberRank;
import com.boc.accuratetest.annotation.SecurityIgnoreHandler;
import com.boc.accuratetest.annotation.SecurityManagement;
import com.boc.accuratetest.biz.ProductionTaskBiz;
import com.boc.accuratetest.biz.UserBiz;
import com.boc.accuratetest.constant.NotLoginInException;
import com.boc.accuratetest.constant.ProductionTaskSession;
import com.boc.accuratetest.demo.StackTraceInfo;
import com.boc.accuratetest.pojo.Permission;
import com.boc.accuratetest.pojo.ProductionTask;
import com.boc.accuratetest.pojo.User;

import net.sf.json.JSONObject;

@Controller
public class IndexController {
	Logger logger = LoggerFactory.getLogger(IndexController.class);
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
	public JSONObject register(String user,String password) {
		user = user.trim();
		password = password.trim();
		JSONObject json = new JSONObject();
		json.put("success", false);
		
		List<User> users = userBiz.findByName(user);
		if(!users.isEmpty()) {
			json.put("msg", "该用户已存在");
			return json;
		}
		userBiz.insert(user,password);
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
			session.setMaxInactiveInterval(0);
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
	 * 	首页	(登陆成功之后)
	 */
	@SecurityManagement(IndexRank.class)
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	/**
	 * 	获取生产任务编号
	 * @param session
	 * @return
	 */
	@SecurityManagement(ProductionTaskNumberRank.class)
	@RequestMapping("/getPuductionTaskNumber")
	@ResponseBody
	public JSONObject getPuductionTaskNumber(HttpSession session) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		List<ProductionTask> pts = null;
		User user = null;
		try {
			pts = productionTaskBiz.getAll();
			Object attribute = session.getAttribute(ProductionTaskSession.loginUser);
			user = (User)attribute;
			
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "获取生产任务编号列表出错");
			return json;
		}
		json.put("rows", pts);
		json.put("total", pts.size());
		json.put("currentProductionTaskNumber", user.getProductionTaskNumber()==null?"":user.getProductionTaskNumber());
		json.put("success", true);
		return json;
	}
	/**
	 * 	指定生产任务编号
	 * @param session
	 * @return
	 */
	@SecurityManagement(ProductionTaskNumberRank.class)
	@RequestMapping("/selectProductionTaskNumber")
	@ResponseBody
	public JSONObject selectProductionTaskNumber(HttpSession session,String productionTaskNumber) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		Object u = session.getAttribute(ProductionTaskSession.loginUser);
		User user = null;
		try {
			user = (User)(u);
			user.setProductionTaskNumber(productionTaskNumber.equals("0")?null:productionTaskNumber);
			session.setAttribute(ProductionTaskSession.loginUser, user);
			session.setMaxInactiveInterval(0);
		} catch (Exception e) {
			json.put("msg", "指定生产任务编号出错");
			return json;
		}
		json.put("msg", "操作成功");
		json.put("success", true);
		return json;
	}
	/**
	 * 	新增生产任务编号
	 * @param session
	 * @return
	 */
	@SecurityManagement(ProductionTaskNumberRank.class)
	@RequestMapping("/addProductionTaskNumber")
	@ResponseBody
	public JSONObject addProductionTaskNumber(HttpSession session,String productionTaskNumber) {
		productionTaskNumber = productionTaskNumber.trim();
		JSONObject json = new JSONObject();
		json.put("success", false);
		List<ProductionTask> findBy = productionTaskBiz.findBy(productionTaskNumber);
		if(!findBy.isEmpty()) {
			json.put("msg", "该生产任务编号已存在");
			return json;
		}
		ProductionTask pt = new ProductionTask();
		pt.setProductionTaskNumber(productionTaskNumber);
		productionTaskBiz.insert(pt);
		json.put("msg", "添加成功");
		json.put("success", true);
		return json;
	}
	@SecurityManagement(ProductionTaskNumberRank.class)
	@RequestMapping("/proTaskNumberIndex")
	public String proTaskNumberIndex(HttpSession session) {
		Object u = session.getAttribute(ProductionTaskSession.loginUser);
		if(null == u) {
			return "redirect:/";
		}
		return "proTaskNumber_index";
	}
}
