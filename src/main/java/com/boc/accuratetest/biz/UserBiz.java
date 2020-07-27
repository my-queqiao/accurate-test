package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.Permission;
import com.boc.accuratetest.pojo.User;

public interface UserBiz {

	List<User> findByNameAndPassword(String user, String password);

	List<Permission> getPermissionsBy(User user);

}
