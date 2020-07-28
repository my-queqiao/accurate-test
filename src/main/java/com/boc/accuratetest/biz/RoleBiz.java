package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.Role;

public interface RoleBiz {

	List<Role> page(Integer pageNumber, Integer pageSize, String search);

	Integer findTotal(String search);

}
