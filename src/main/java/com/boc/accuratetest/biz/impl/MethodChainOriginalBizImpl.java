package com.boc.accuratetest.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boc.accuratetest.biz.MethodChainOriginalBiz;
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

	@Override
	public List<MethodChainOriginal> getMethodLinkByTestExampleIds(Set<Integer> testExampleIds) {
		List<Integer> testExampleIds2 = new ArrayList<Integer>();
		for (Integer id : testExampleIds) {
			testExampleIds2.add(id);
		}
		MethodChainOriginalExample e = new MethodChainOriginalExample();
		e.createCriteria().andTestingExampleIdIn(testExampleIds2);
		return methodChainOriginalMapper.selectByExample(e);
	}
	
}
