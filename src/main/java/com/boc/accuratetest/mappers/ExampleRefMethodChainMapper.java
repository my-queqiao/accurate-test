package com.boc.accuratetest.mappers;

import com.boc.accuratetest.pojo.ExampleRefMethodChain;
import com.boc.accuratetest.pojo.ExampleRefMethodChainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExampleRefMethodChainMapper {
    long countByExample(ExampleRefMethodChainExample example);

    int deleteByExample(ExampleRefMethodChainExample example);

    int insert(ExampleRefMethodChain record);

    int insertSelective(ExampleRefMethodChain record);

    List<ExampleRefMethodChain> selectByExample(ExampleRefMethodChainExample example);

    int updateByExampleSelective(@Param("record") ExampleRefMethodChain record, @Param("example") ExampleRefMethodChainExample example);

    int updateByExample(@Param("record") ExampleRefMethodChain record, @Param("example") ExampleRefMethodChainExample example);

	void insertBatch(List<ExampleRefMethodChain> refs);

	void deleteByTestingExampleId(Integer testingExampleId);
}