package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boc.accuratetest.biz.MethodChainOriginalBiz;
import com.boc.accuratetest.mappers.ExampleRefMethodChainMapper;
import com.boc.accuratetest.mappers.MethodChainOriginalMapper;
import com.boc.accuratetest.mappers.TestedMethodsMapper;
import com.boc.accuratetest.pojo.MethodChainOriginal;
import com.boc.accuratetest.pojo.MethodChainOriginalExample;
import com.boc.accuratetest.pojo.TestedMethods;
@Service
public class MethodChainOriginalBizImpl implements MethodChainOriginalBiz{
	@Autowired
	private MethodChainOriginalMapper methodChainOriginalMapper;
	@Autowired
	private TestedMethodsMapper testedMethodsMapper;

	@Override
	public void insertBatch(List<MethodChainOriginal> ms) {
		methodChainOriginalMapper.insertBatch(ms);
	}
	
	@Override
	@Transactional
	public void insertBatchForTestedMethods(List<TestedMethods> tms) {
		testedMethodsMapper.insertBatchForTestedMethods(tms);
	}

	@Override
	public List<MethodChainOriginal> getMethodLinkByTestExampleId(Integer testExampleId) {
		MethodChainOriginalExample e = new MethodChainOriginalExample();
		e.createCriteria().andTestingExampleIdEqualTo(testExampleId);
		return methodChainOriginalMapper.selectByExample(e);
	}

	@Override
	public void deleteByTestingExampleId(Integer testExampleId) {
		MethodChainOriginalExample e = new MethodChainOriginalExample();
		e.createCriteria().andTestingExampleIdEqualTo(testExampleId);
		methodChainOriginalMapper.deleteByExample(e);
	}
	
}
