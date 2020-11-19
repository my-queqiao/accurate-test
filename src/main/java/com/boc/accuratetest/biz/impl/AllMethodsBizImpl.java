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
	public List<AllMethods> statisticClassNumber(String productionTaskNumber) {
		return allMethodsMapper.statisticClassNumber(productionTaskNumber);
	}
	@Override
	public List<AllMethods> statisticMethodInfoInClass(String packageName,String productionTaskNumber) {
		return allMethodsMapper.statisticMethodInfoInClass(packageName,productionTaskNumber);
	}
	@Override
	public List<AllMethods> methodInfoInClass(String packageName, String simpleClassName,String productionTaskNumber) {
		return allMethodsMapper.methodInfoInClass(packageName,simpleClassName,productionTaskNumber);
	}
	
}
