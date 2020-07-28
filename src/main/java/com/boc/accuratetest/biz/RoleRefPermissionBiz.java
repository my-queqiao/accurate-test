package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.RoleRefPermission;

public interface RoleRefPermissionBiz {

	List<Integer> getPermissionsByRoleId(Integer roleId);

	void deleteByUserId(Integer roleId);

	void insertBatch(List<RoleRefPermission> rrps);

}
