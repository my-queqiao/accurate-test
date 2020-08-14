package com.boc.accuratetest.biz.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.boc.accuratetest.biz.UserBiz;
import com.boc.accuratetest.demo.StackTraceInfo;
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

	@Override
	public List<User> getAll() {
		return userMapper.selectByExample(null);
	}

	@Override
	public List<User> page(Integer pageNumber, Integer pageSize, String search) {
		// SELECT * FROM table LIMIT 5,10;  // 检索记录行 6-15
		int limit = 0;
		if(pageNumber.intValue() == 1) {
			limit = 0;
		}else {
			limit = (pageNumber-1)*pageSize;
		}
		List<User> list = userMapper.page(search,limit,pageSize);
		return list;
	}

	@Override
	public Integer findTotal(String search) {
		UserExample e = new UserExample();
		Criteria cc = e.createCriteria();
		if(!StringUtils.isEmpty(search)) {
			cc.andUserNameLike("%"+search+"%");
		}
		int i = (int)(userMapper.countByExample(e));
		return i;
	}

	@Override
	public List<User> findByName(String user) {
		UserExample e = new UserExample();
		e.createCriteria().andUserNameEqualTo(user);
		return userMapper.selectByExample(e);
	}

	@Override
	public void insert(String user, String password) {
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		User record = new User();
		record.setUserName(user);
		record.setPassWord(password);
		String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		record.setCreateTime(createTime);
		userMapper.insert(record);
	}
}
