package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.PermissionBiz;
import com.boc.accuratetest.mappers.PermissionMapper;
import com.boc.accuratetest.pojo.Permission;
@Service
public class PermissionBizImpl implements PermissionBiz{
	@Autowired
	private PermissionMapper permissionMapper;
	@Override
	public void insertBatch(List<Permission> ps) {
		permissionMapper.insertBatch(ps);
	}

}
