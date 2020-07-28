package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.ProductionTaskBiz;
import com.boc.accuratetest.mappers.ProductionTaskMapper;
import com.boc.accuratetest.pojo.ProductionTask;
import com.boc.accuratetest.pojo.ProductionTaskExample;
@Service
public class ProductionTaskBizImpl implements ProductionTaskBiz{
	@Autowired
	private ProductionTaskMapper productionTaskMapper;
	@Override
	public List<ProductionTask> findBy(String productionTaskNumber) {
		List<ProductionTask> pts = productionTaskMapper.findBy(productionTaskNumber);
		return pts;
	}
	@Override
	public void insert(ProductionTask pt) {
		productionTaskMapper.insert(pt);
	}
	@Override
	public List<ProductionTask> getAll() {
		return productionTaskMapper.getAll();
	}
	@Override
	public void updateByProductionTaskNumber(String productionTaskNumber, String git_url, String master_branch,
			String test_branch) {
		ProductionTask pt = new ProductionTask();
		pt.setGitUrl(git_url);
		pt.setMasterBranch(master_branch);
		pt.setTestBranch(test_branch);
		
		ProductionTaskExample e = new ProductionTaskExample();
		e.createCriteria().andProductionTaskNumberEqualTo(productionTaskNumber);
		productionTaskMapper.updateByExampleSelective(pt, e);
	}

}
