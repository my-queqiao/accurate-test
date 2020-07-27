package com.boc.accuratetest.biz.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.UserBiz;
import com.boc.accuratetest.mappers.UserMapper;
import com.boc.accuratetest.pojo.Permission;
import com.boc.accuratetest.pojo.User;
import com.boc.accuratetest.pojo.UserExample;
import com.boc.accuratetest.pojo.UserExample.Criteria;
@Service
public class UserBizImpl implements UserBiz{
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> findByNameAndPassword(String user, String password) {
		UserExample e = new UserExample();
		Criteria cc = e.createCriteria();
		cc.andUserNameEqualTo(user).andPassWordEqualTo(password);
		List<User> list = userMapper.selectByExample(e);
		return list;
	}

	@Override
	public List<Permission> getPermissionsBy(User user) {
		return userMapper.getPermissionsBy(user);
	}
}
