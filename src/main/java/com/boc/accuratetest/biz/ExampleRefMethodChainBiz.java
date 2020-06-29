package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.ExampleRefMethodChain;

public interface ExampleRefMethodChainBiz {

	void insertBatch(List<ExampleRefMethodChain> refs);

	void deleteByTestingExampleId(Integer testingExampleId);

}
