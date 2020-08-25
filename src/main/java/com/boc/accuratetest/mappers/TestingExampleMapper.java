package com.boc.accuratetest.mappers;

import com.boc.accuratetest.pojo.TestingExample;
import com.boc.accuratetest.pojo.TestingExampleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestingExampleMapper {
    long countByExample(TestingExampleExample example);

    int deleteByExample(TestingExampleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TestingExample record);

    int insertSelective(TestingExample record);

    List<TestingExample> selectByExample(TestingExampleExample example);

    TestingExample selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TestingExample record, @Param("example") TestingExampleExample example);

    int updateByExample(@Param("record") TestingExample record, @Param("example") TestingExampleExample example);

    int updateByPrimaryKeySelective(TestingExample record);

    int updateByPrimaryKey(TestingExample record);

	List<TestingExample> page(String search, int limit, Integer pageSize, String productionTaskNumber);

	int findTotal(String search, String productionTaskNumber);

	void batchSave(List<TestingExample> tes);

	List<TestingExample> findByIds(List<Integer> teids);
}