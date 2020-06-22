package com.boc.accuratetest.mappers;

import java.util.List;

import com.boc.accuratetest.pojo.MethodChainOriginal;

public interface MethodChainOriginalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MethodChainOriginal record);

    int insertSelective(MethodChainOriginal record);

    MethodChainOriginal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MethodChainOriginal record);

    int updateByPrimaryKey(MethodChainOriginal record);

	List<MethodChainOriginal> getAll();

	int findTotal();

	void insertBatch(List<MethodChainOriginal> ms);
}