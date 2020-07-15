package com.boc.accuratetest.mappers;

import java.util.List;

import com.boc.accuratetest.pojo.TestedMethods;

public interface TestedMethodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestedMethods record);

    int insertSelective(TestedMethods record);

    TestedMethods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestedMethods record);

    int updateByPrimaryKey(TestedMethods record);

	void insertBatchForTestedMethods(List<TestedMethods> tms);

	List<TestedMethods> getAll();

	void updateChangeCodeTestingOrNot();
}