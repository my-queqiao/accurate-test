package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.AllMethodsBiz;
import com.boc.accuratetest.mappers.AllMethodsMapper;
import com.boc.accuratetest.pojo.AllMethods;

@Service
public class AllMethodsBizImpl implements AllMethodsBiz{
	@Autowired
	private AllMethodsMapper allMethodsMapper;
	@Override
	public void insertBatch(List<AllMethods> ms) {
		allMethodsMapper.insertBatch(ms);
	}
	@Override
	public List<AllMethods> page(Integer pageNumber, Integer pageSize, String search) {
		int limit = 0;
		if(pageNumber.intValue() == 1) {
			limit = 0;
		}else {
			limit = (pageNumber-1)*pageSize;
		}
		List<AllMethods> list = allMethodsMapper.page(search,limit,pageSize);
		return list;
	}
	@Override
	public Integer findTotal(String search) {
		return allMethodsMapper.findTotal(search);
	}
	@Override
	public List<AllMethods> getAll() {
		return allMethodsMapper.getAll();
	}
	@Override
	public List<AllMethods> statisticClassNumber() {
		return allMethodsMapper.statisticClassNumber();
	}
	
}
