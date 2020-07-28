package com.boc.accuratetest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boc.accuratetest.acl.PermissionRank;
import com.boc.accuratetest.annotation.SecurityManagement;
import com.boc.accuratetest.biz.PermissionBiz;
import com.boc.accuratetest.biz.RoleBiz;
import com.boc.accuratetest.biz.RoleRefPermissionBiz;
import com.boc.accuratetest.biz.UserBiz;
import com.boc.accuratetest.biz.UserRefRoleBiz;
import com.boc.accuratetest.pojo.Permission;
import com.boc.accuratetest.pojo.Role;
import com.boc.accuratetest.pojo.RoleRefPermission;
import com.boc.accuratetest.pojo.User;
import com.boc.accuratetest.pojo.UserRefRole;

@Controller
@RequestMapping("permission")
public class PermissionController {
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private UserRefRoleBiz userRefRoleBiz;
	@Autowired
	private RoleBiz roleBiz;
	@Autowired
	private PermissionBiz permissionBiz;
	@Autowired
	private RoleRefPermissionBiz roleRefPermissionBiz;
	/*
	 * 	权限设置首页
	 */
	@SecurityManagement(PermissionRank.class)
	@RequestMapping("index")
	public String index() {
		return "permission_index";
	}
	@SecurityManagement(PermissionRank.class)
	@RequestMapping("getAllUsers")
	@ResponseBody
	public Map<String,Object> getAllUsers(Integer pageNumber,Integer pageSize,String search,HttpSession session) {
		Map<String,Object> map = new HashMap<>();
		try {
			List<User> page = userBiz.page(pageNumber, pageSize, search);
			Integer total = userBiz.findTotal(search);
			map.put("rows", page);
			map.put("total", total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@SecurityManagement(PermissionRank.class)
	@RequestMapping("getAllRoles")
	@ResponseBody
	public Map<String,Object> getAllRoles(Integer pageNumber,Integer pageSize,String search,HttpSession session) {
		Map<String,Object> map = new HashMap<>();
		try {
			List<Role> page = roleBiz.page(pageNumber, pageSize, search);
			Integer total = roleBiz.findTotal(search);
			map.put("rows", page);
			map.put("total", total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@SecurityManagement(PermissionRank.class)
	@RequestMapping("getRolesByUserId")
	@ResponseBody
	public Map<String,Object> getRolesByUserId(Integer pageNumber,Integer pageSize,String search,Integer userId) {
		Map<String,Object> map = new HashMap<>();
		try {
			List<Role> page = roleBiz.page(pageNumber, pageSize, null);
			List<Integer> roleIds = userRefRoleBiz.getRoleIdsByUserId(userId);
			for (Role r : page) {
				for (Integer id : roleIds) {
					if(r.getId().intValue() == id.intValue()) {
						r.setChecked(1);
						break;
					}
				}
			}
			Integer total = roleBiz.findTotal(null);
			map.put("rows", page);
			map.put("total", total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@SecurityManagement(PermissionRank.class)
	@RequestMapping("userLinkRoles")
	@ResponseBody
	public Map<String,Object> userLinkRoles(Integer userId,String roleIds) {
		Map<String,Object> map = new HashMap<>();
		map.put("success", false);
		try {
			// 关联之前，先删除旧的
			userRefRoleBiz.deleteByUserId(userId);
			List<UserRefRole> urrs = new ArrayList<UserRefRole>();
			String[] split = roleIds.split(",");
			for (String ri : split) {
				UserRefRole urr = new UserRefRole();
				urr.setUserId(userId);
				urr.setRoleId(Integer.valueOf(ri));
				urrs.add(urr);
			}
			userRefRoleBiz.insertBatch(urrs);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return map;
		}
		map.put("success", true);
		return map;
	}
	@SecurityManagement(PermissionRank.class)
	@RequestMapping("roleIndex")
	public String roleIndex() {
		return "role_index";
	}
	@SecurityManagement(PermissionRank.class)
	@RequestMapping("getAllPermissions")
	@ResponseBody
	public Map<String, Object> getAllPermissions(Integer pageNumber,Integer pageSize,String search) {
		Map<String,Object> map = new HashMap<>();
		List<Permission> ps = permissionBiz.page(pageNumber, pageSize, search);
		Integer total = permissionBiz.findTotal(search);
		map.put("rows", ps);
		map.put("total", total);
		return map;
	}
	
	@SecurityManagement(PermissionRank.class)
	@RequestMapping("getPermissionsByRoleId")
	@ResponseBody
	public Map<String,Object> getPermissionsByRoleId(Integer pageNumber,Integer pageSize,String search,Integer roleId) {
		Map<String,Object> map = new HashMap<>();
		try {
			List<Permission> page = permissionBiz.page(pageNumber, pageSize, null);
			List<Integer> permissionIds = roleRefPermissionBiz.getPermissionsByRoleId(roleId);
			for (Permission r : page) {
				for (Integer id : permissionIds) {
					if(r.getId().intValue() == id.intValue()) {
						r.setChecked(1);
						break;
					}
				}
			}
			Integer total = roleBiz.findTotal(null);
			map.put("rows", page);
			map.put("total", total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@SecurityManagement(PermissionRank.class)
	@RequestMapping("roleLinkPermissions")
	@ResponseBody
	public Map<String,Object> roleLinkPermissions(Integer roleId,String permissionIds) {
		Map<String,Object> map = new HashMap<>();
		map.put("success", false);
		try {
			// 关联之前，先删除旧的
			roleRefPermissionBiz.deleteByUserId(roleId);
			List<RoleRefPermission> rrps = new ArrayList<RoleRefPermission>();
			String[] split = permissionIds.split(",");
			for (String ri : split) {
				RoleRefPermission rrp = new RoleRefPermission();
				rrp.setRoleId(roleId);
				rrp.setPermissionId(Integer.valueOf(ri));
				rrps.add(rrp);
			}
			roleRefPermissionBiz.insertBatch(rrps);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return map;
		}
		map.put("success", true);
		return map;
	}
}
