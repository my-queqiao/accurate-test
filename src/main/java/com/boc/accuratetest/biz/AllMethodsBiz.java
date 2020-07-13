package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.AllMethods;

public interface AllMethodsBiz {

	void insertBatch(List<AllMethods> ms);

	List<AllMethods> page(Integer pageNumber, Integer pageSize, String search);

	Integer findTotal(String search);

	List<AllMethods> getAll();

	List<AllMethods> statisticClassNumber();

	List<AllMethods> statisticMethodInfoInClass(String packageName);

	List<AllMethods> methodInfoInClass(String packageName, String simpleClassName);

}
