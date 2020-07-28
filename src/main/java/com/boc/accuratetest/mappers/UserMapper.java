package com.boc.accuratetest.mappers;

import com.boc.accuratetest.pojo.Permission;
import com.boc.accuratetest.pojo.User;
import com.boc.accuratetest.pojo.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	List<Permission> getPermissionsBy(User user);

	List<User> page(String search, int limit, Integer pageSize);
}