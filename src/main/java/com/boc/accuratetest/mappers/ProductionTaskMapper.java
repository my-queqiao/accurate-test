package com.boc.accuratetest.mappers;

import com.boc.accuratetest.pojo.ProductionTask;
import com.boc.accuratetest.pojo.ProductionTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductionTaskMapper {
    long countByExample(ProductionTaskExample example);

    int deleteByExample(ProductionTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductionTask record);

    int insertSelective(ProductionTask record);

    List<ProductionTask> selectByExample(ProductionTaskExample example);

    ProductionTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductionTask record, @Param("example") ProductionTaskExample example);

    int updateByExample(@Param("record") ProductionTask record, @Param("example") ProductionTaskExample example);

    int updateByPrimaryKeySelective(ProductionTask record);

    int updateByPrimaryKey(ProductionTask record);

	List<ProductionTask> findBy(String productionTaskNumber);

	List<ProductionTask> getAll();
}