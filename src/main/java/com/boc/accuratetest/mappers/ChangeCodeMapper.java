package com.boc.accuratetest.mappers;

import java.util.List;

import com.boc.accuratetest.pojo.ChangeCode;

public interface ChangeCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChangeCode record);

    int insertSelective(ChangeCode record);

    ChangeCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChangeCode record);

    int updateByPrimaryKey(ChangeCode record);

	int findTotal(Integer search,Byte dataOfPart);

	void insertBatch(List<ChangeCode> ccs);

	List<ChangeCode> page(Integer search, int limit, Integer pageSize,Byte dataOfPart);

	void deleteByGitUrlAndBranchs(String gitUrlAndBranchs);

	List<ChangeCode> countByChangeType();
}