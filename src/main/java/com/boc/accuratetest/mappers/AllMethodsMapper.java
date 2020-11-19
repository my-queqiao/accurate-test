package com.boc.accuratetest.mappers;

import com.boc.accuratetest.pojo.AllMethods;
import com.boc.accuratetest.pojo.AllMethodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AllMethodsMapper {
    long countByExample(AllMethodsExample example);

    int deleteByExample(AllMethodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AllMethods record);

    int insertSelective(AllMethods record);

    List<AllMethods> selectByExample(AllMethodsExample example);

    AllMethods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AllMethods record, @Param("example") AllMethodsExample example);

    int updateByExample(@Param("record") AllMethods record, @Param("example") AllMethodsExample example);

    int updateByPrimaryKeySelective(AllMethods record);

    int updateByPrimaryKey(AllMethods record);

	void insertBatch(List<AllMethods> ms);

	List<AllMethods> page(String search, int limit, Integer pageSize);

	List<AllMethods> statisticClassNumber(String productionTaskNumber);

	List<AllMethods> statisticMethodInfoInClass(@Param("packageName")String packageName,
			@Param("productionTaskNumber")String productionTaskNumber);

	List<AllMethods> methodInfoInClass(@Param("packageName")String packageName, 
			@Param("simpleClassName")String simpleClassName,@Param("productionTaskNumber")String productionTaskNumber);
}