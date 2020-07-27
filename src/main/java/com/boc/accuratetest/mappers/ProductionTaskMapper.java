package com.boc.accuratetest.mappers;

import java.util.List;

import com.boc.accuratetest.pojo.ProductionTask;

public interface ProductionTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductionTask record);

    int insertSelective(ProductionTask record);

    ProductionTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductionTask record);

    int updateByPrimaryKey(ProductionTask record);

	List<ProductionTask> findBy(String productionTaskNumber);
}