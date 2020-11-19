package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.AllMethods;

public interface AllMethodsBiz {

	void insertBatch(List<AllMethods> ms);

	List<AllMethods> page(Integer pageNumber, Integer pageSize, String search);

	List<AllMethods> statisticClassNumber(String productionTaskNumber);

	List<AllMethods> statisticMethodInfoInClass(String packageName,String productionTaskNumber);

	List<AllMethods> methodInfoInClass(String packageName, String simpleClassName,String productionTaskNumber);

}
