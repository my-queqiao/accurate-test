package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.TestedMethodsBiz;
import com.boc.accuratetest.mappers.TestedMethodsMapper;
import com.boc.accuratetest.pojo.TestedMethods;

@Service
public class TestedMethodsBizImpl implements TestedMethodsBiz{
	@Autowired
	private TestedMethodsMapper testedMethodsMapper;
	@Override
	public List<TestedMethods> getAll() {
		return testedMethodsMapper.getAll();
	}

}
