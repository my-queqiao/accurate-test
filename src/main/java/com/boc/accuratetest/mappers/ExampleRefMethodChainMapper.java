package com.boc.accuratetest.mappers;

import java.util.List;

import com.boc.accuratetest.pojo.ExampleRefMethodChain;

public interface ExampleRefMethodChainMapper {
    int insert(ExampleRefMethodChain record);

    int insertSelective(ExampleRefMethodChain record);

	void insertBatch(List<ExampleRefMethodChain> refs);

	void deleteByTestingExampleId(Integer testingExampleId);
}