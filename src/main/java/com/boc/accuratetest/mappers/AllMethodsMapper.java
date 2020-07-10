package com.boc.accuratetest.mappers;

import java.util.List;

import com.boc.accuratetest.pojo.AllMethods;

public interface AllMethodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AllMethods record);

    int insertSelective(AllMethods record);

    AllMethods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AllMethods record);

    int updateByPrimaryKey(AllMethods record);

	void insertBatch(List<AllMethods> ms);

	List<AllMethods> page(String search, int limit, Integer pageSize);

	Integer findTotal(String search);

	List<AllMethods> getAll();

	List<AllMethods> statisticClassNumber();
}