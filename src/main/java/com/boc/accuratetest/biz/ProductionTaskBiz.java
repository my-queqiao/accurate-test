package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.ProductionTask;

public interface ProductionTaskBiz {

	List<ProductionTask> findBy(String productionTaskNumber);

	void insert(ProductionTask pt);

	List<ProductionTask> getAll();

	void updateByProductionTaskNumber(String productionTaskNumber, String git_url, String master_branch,
			String test_branch);

}
