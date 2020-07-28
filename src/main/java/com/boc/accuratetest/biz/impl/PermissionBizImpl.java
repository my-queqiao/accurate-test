package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.boc.accuratetest.biz.PermissionBiz;
import com.boc.accuratetest.mappers.PermissionMapper;
import com.boc.accuratetest.pojo.Permission;
import com.boc.accuratetest.pojo.PermissionExample;
@Service
public class PermissionBizImpl implements PermissionBiz{
	@Autowired
	private PermissionMapper permissionMapper;
	@Override
	public void insertBatch(List<Permission> ps) {
		permissionMapper.insertBatch(ps);
	}
	@Override
	public List<Permission> page(Integer pageNumber, Integer pageSize, String search) {
		// SELECT * FROM table LIMIT 5,10;  // 检索记录行 6-15
		int limit = 0;
		if(pageNumber.intValue() == 1) {
			limit = 0;
		}else {
			limit = (pageNumber-1)*pageSize;
		}
		List<Permission> list = permissionMapper.page(search,limit,pageSize);
		return list;
	}
	@Override
	public Integer findTotal(String search) {
		PermissionExample e = new PermissionExample();
		if(!StringUtils.isEmpty(search)) {
			e.createCriteria().andRankDescLike("%"+search+"%");
		}
		long i = permissionMapper.countByExample(e);
		return (int)i;
	}

}
