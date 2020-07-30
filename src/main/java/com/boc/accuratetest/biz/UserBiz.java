package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.Permission;
import com.boc.accuratetest.pojo.User;

public interface UserBiz {

	List<User> findByNameAndPassword(String user, String password);

	List<Permission> getPermissionsBy(User user);

	List<User> getAll();

	List<User> page(Integer pageNumber, Integer pageSize, String search);

	Integer findTotal(String search);

	List<User> findByName(String user);

	void insert(String user, String password);

}
