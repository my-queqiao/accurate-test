package com.boc.accuratetest.mappers;

import com.boc.accuratetest.pojo.UserRefRole;
import com.boc.accuratetest.pojo.UserRefRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRefRoleMapper {
    long countByExample(UserRefRoleExample example);

    int deleteByExample(UserRefRoleExample example);

    int insert(UserRefRole record);

    int insertSelective(UserRefRole record);

    List<UserRefRole> selectByExample(UserRefRoleExample example);

    int updateByExampleSelective(@Param("record") UserRefRole record, @Param("example") UserRefRoleExample example);

    int updateByExample(@Param("record") UserRefRole record, @Param("example") UserRefRoleExample example);

	void insertBatch(List<UserRefRole> urrs);
}