package com.boc.accuratetest.mappers;

import com.boc.accuratetest.pojo.RoleRefPermission;
import com.boc.accuratetest.pojo.RoleRefPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleRefPermissionMapper {
    long countByExample(RoleRefPermissionExample example);

    int deleteByExample(RoleRefPermissionExample example);

    int insert(RoleRefPermission record);

    int insertSelective(RoleRefPermission record);

    List<RoleRefPermission> selectByExample(RoleRefPermissionExample example);

    int updateByExampleSelective(@Param("record") RoleRefPermission record, @Param("example") RoleRefPermissionExample example);

    int updateByExample(@Param("record") RoleRefPermission record, @Param("example") RoleRefPermissionExample example);
}