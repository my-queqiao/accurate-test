package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.TestingExample;

public interface TestingExampleBiz {

	List<TestingExample> page(Integer pageNumber, Integer pageSize, String search);

	Integer findTotal(String search);

	void batchSave(List<TestingExample> tes);

	List<TestingExample> findByIds(List<Integer> teids);
}
