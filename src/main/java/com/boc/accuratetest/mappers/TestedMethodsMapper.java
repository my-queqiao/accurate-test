package com.boc.accuratetest.mappers;

import com.boc.accuratetest.pojo.TestedMethods;
import com.boc.accuratetest.pojo.TestedMethodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestedMethodsMapper {
    long countByExample(TestedMethodsExample example);

    int deleteByExample(TestedMethodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TestedMethods record);

    int insertSelective(TestedMethods record);

    List<TestedMethods> selectByExample(TestedMethodsExample example);

    TestedMethods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TestedMethods record, @Param("example") TestedMethodsExample example);

    int updateByExample(@Param("record") TestedMethods record, @Param("example") TestedMethodsExample example);

    int updateByPrimaryKeySelective(TestedMethods record);

    int updateByPrimaryKey(TestedMethods record);

	void insertBatchForTestedMethods(List<TestedMethods> tms);

	List<TestedMethods> getAll();

	void updateChangeCodeTestingOrNot(String productionTaskNumber);
}