package com.boc.accuratetest.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.RoleRefPermissionBiz;
import com.boc.accuratetest.mappers.RoleRefPermissionMapper;
import com.boc.accuratetest.pojo.RoleRefPermission;
import com.boc.accuratetest.pojo.RoleRefPermissionExample;
@Service
public class RoleRefPermissionBizImpl implements RoleRefPermissionBiz{
	@Autowired
	private RoleRefPermissionMapper roleRefPermissionMapper;

	@Override
	public List<Integer> getPermissionsByRoleId(Integer roleId) {
		RoleRefPermissionExample e = new RoleRefPermissionExample();
		e.createCriteria().andRoleIdEqualTo(roleId);
		List<RoleRefPermission> list = roleRefPermissionMapper.selectByExample(e);
		List<Integer> pids = new ArrayList<Integer>();
		for (RoleRefPermission rrp : list) {
			pids.add(rrp.getPermissionId());
		}
		return pids;
	}

	@Override
	public void deleteByUserId(Integer roleId) {
		RoleRefPermissionExample e = new RoleRefPermissionExample();
		e.createCriteria().andRoleIdEqualTo(roleId);
		roleRefPermissionMapper.deleteByExample(e);
	}

	@Override
	public void insertBatch(List<RoleRefPermission> rrps) {
		roleRefPermissionMapper.insertBatch(rrps);
	}
}
