package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.boc.accuratetest.biz.RoleBiz;
import com.boc.accuratetest.mappers.RoleMapper;
import com.boc.accuratetest.pojo.Role;
import com.boc.accuratetest.pojo.RoleExample;
import com.boc.accuratetest.pojo.RoleExample.Criteria;
@Service
public class RoleBizImpl implements RoleBiz{
	@Autowired
	private RoleMapper roleMapper;
	@Override
	public List<Role> page(Integer pageNumber, Integer pageSize, String search) {
		// SELECT * FROM table LIMIT 5,10;  // 检索记录行 6-15
		int limit = 0;
		if(pageNumber.intValue() == 1) {
			limit = 0;
		}else {
			limit = (pageNumber-1)*pageSize;
		}
		List<Role> list = roleMapper.page(search,limit,pageSize);
		return list;
	}

	@Override
	public Integer findTotal(String search) {
		RoleExample e = new RoleExample();
		Criteria criteria = e.createCriteria();
		if(!StringUtils.isEmpty(search)) {
			criteria.andRoleNameLike("%"+search+"%");
		}
		long i = roleMapper.countByExample(e);
		return (int)i;
	}
}
