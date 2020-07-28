package com.boc.accuratetest.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.UserRefRoleBiz;
import com.boc.accuratetest.mappers.UserRefRoleMapper;
import com.boc.accuratetest.pojo.UserRefRole;
import com.boc.accuratetest.pojo.UserRefRoleExample;
@Service
public class UserRefRoleBizImpl implements UserRefRoleBiz{
	@Autowired
	private UserRefRoleMapper userRefRoleMapper;
	@Override
	public List<Integer> getRoleIdsByUserId(Integer userId) {
		UserRefRoleExample e = new UserRefRoleExample();
		e.createCriteria().andUserIdEqualTo(userId);
		List<UserRefRole> list = userRefRoleMapper.selectByExample(e);
		List<Integer> ids = new ArrayList<Integer>();
		for (UserRefRole userRefRole : list) {
			ids.add(userRefRole.getRoleId());
		}
		return ids;
	}
	@Override
	public void deleteByUserId(Integer userId) {
		UserRefRoleExample e = new UserRefRoleExample();
		e.createCriteria().andUserIdEqualTo(userId);
		userRefRoleMapper.deleteByExample(e);
	}
	@Override
	public void insertBatch(List<UserRefRole> urrs) {
		userRefRoleMapper.insertBatch(urrs);
	}
	
}
