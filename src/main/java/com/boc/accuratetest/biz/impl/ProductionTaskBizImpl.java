package com.boc.accuratetest.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.accuratetest.biz.ProductionTaskBiz;
import com.boc.accuratetest.mappers.ProductionTaskMapper;
import com.boc.accuratetest.pojo.ProductionTask;
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

}
