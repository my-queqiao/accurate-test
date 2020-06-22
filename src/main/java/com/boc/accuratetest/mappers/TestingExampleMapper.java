package com.boc.accuratetest.mappers;

import java.util.List;

import com.boc.accuratetest.pojo.TestingExample;

public interface TestingExampleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestingExample record);

    int insertSelective(TestingExample record);

    TestingExample selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestingExample record);

    int updateByPrimaryKey(TestingExample record);

	List<TestingExample> page(String search, int limit, Integer pageSize);

	int findTotal(String search);
}