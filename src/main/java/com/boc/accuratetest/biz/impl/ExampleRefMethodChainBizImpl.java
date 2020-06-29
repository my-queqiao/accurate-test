package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.ExampleRefMethodChainBiz;
import com.boc.accuratetest.mappers.ExampleRefMethodChainMapper;
import com.boc.accuratetest.pojo.ExampleRefMethodChain;
@Service
public class ExampleRefMethodChainBizImpl implements ExampleRefMethodChainBiz{
	@Autowired
	private ExampleRefMethodChainMapper exampleRefMethodChainMapper;
	@Override
	public void insertBatch(List<ExampleRefMethodChain> refs) {
		exampleRefMethodChainMapper.insertBatch(refs);
	}
	@Override
	public void deleteByTestingExampleId(Integer testingExampleId) {
		exampleRefMethodChainMapper.deleteByTestingExampleId(testingExampleId);
	}

}
