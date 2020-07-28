package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.UserRefRole;

public interface UserRefRoleBiz {

	List<Integer> getRoleIdsByUserId(Integer userId);

	void deleteByUserId(Integer userId);

	void insertBatch(List<UserRefRole> urrs);

}
