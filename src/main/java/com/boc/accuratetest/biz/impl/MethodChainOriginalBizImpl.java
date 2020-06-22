package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.MethodChainOriginalBiz;
import com.boc.accuratetest.mappers.MethodChainOriginalMapper;
import com.boc.accuratetest.pojo.MethodChainOriginal;
@Service
public class MethodChainOriginalBizImpl implements MethodChainOriginalBiz{
	@Autowired
	private MethodChainOriginalMapper methodChainOriginalMapper;
	@Override
	public List<MethodChainOriginal> getAll() {
		return methodChainOriginalMapper.getAll();
	}

	@Override
	public int findTotal() {
		return methodChainOriginalMapper.findTotal();
	}

	@Override
	public void insertBatch(List<MethodChainOriginal> ms) {
		methodChainOriginalMapper.insertBatch(ms);
	}
	
}
