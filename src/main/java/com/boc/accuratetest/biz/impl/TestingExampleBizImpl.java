package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.TestingExampleBiz;
import com.boc.accuratetest.mappers.TestingExampleMapper;
import com.boc.accuratetest.pojo.TestingExample;

@Service
public class TestingExampleBizImpl implements TestingExampleBiz{
	@Autowired
	private TestingExampleMapper testingExampleMapper;
	@Override
	public List<TestingExample> page(Integer pageNumber, Integer pageSize, String search,String productionTaskNumber) {
		// SELECT * FROM table LIMIT 5,10;  // 检索记录行 6-15
		int limit = 0;
		if(pageNumber.intValue() == 1) {
			limit = 0;
		}else {
			limit = (pageNumber-1)*pageSize;
		}
		List<TestingExample> list = testingExampleMapper.page(search,limit,pageSize,productionTaskNumber);
		return list;
	}

	@Override
	public Integer findTotal(String search,String productionTaskNumber) {
		int total = testingExampleMapper.findTotal(search,productionTaskNumber);
		return total;
	}

	@Override
	public void batchSave(List<TestingExample> tes) {
		testingExampleMapper.batchSave(tes);
	}

	@Override
	public List<TestingExample> findByIds(List<Integer> teids) {
		List<TestingExample> tes =testingExampleMapper.findByIds(teids);
		return tes;
	}

}
