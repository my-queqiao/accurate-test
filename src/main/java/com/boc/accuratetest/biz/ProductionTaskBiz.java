package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.ProductionTask;

public interface ProductionTaskBiz {

	List<ProductionTask> findBy(String productionTaskNumber);

	void insert(ProductionTask pt);

}
