package com.boc.accuratetest.mappers;

import com.boc.accuratetest.pojo.MethodChainOriginal;
import com.boc.accuratetest.pojo.MethodChainOriginalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MethodChainOriginalMapper {
    long countByExample(MethodChainOriginalExample example);

    int deleteByExample(MethodChainOriginalExample example);

    int deleteByPrimaryKey(String id);

    int insert(MethodChainOriginal record);

    int insertSelective(MethodChainOriginal record);

    List<MethodChainOriginal> selectByExample(MethodChainOriginalExample example);

    MethodChainOriginal selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MethodChainOriginal record, @Param("example") MethodChainOriginalExample example);

    int updateByExample(@Param("record") MethodChainOriginal record, @Param("example") MethodChainOriginalExample example);

    int updateByPrimaryKeySelective(MethodChainOriginal record);

    int updateByPrimaryKey(MethodChainOriginal record);

	void insertBatch(List<MethodChainOriginal> ms);

	List<MethodChainOriginal> getMethodLinkByTestExampleId(Integer testExampleId);
}