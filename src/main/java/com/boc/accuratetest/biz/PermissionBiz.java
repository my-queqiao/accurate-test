package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.Permission;

public interface PermissionBiz {

	void insertBatch(List<Permission> ps);

	List<Permission> page(Integer pageNumber, Integer pageSize, String search);

	Integer findTotal(String search);

}
