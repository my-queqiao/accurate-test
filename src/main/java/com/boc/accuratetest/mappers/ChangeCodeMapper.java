package com.boc.accuratetest.mappers;

import com.boc.accuratetest.pojo.ChangeCode;
import com.boc.accuratetest.pojo.ChangeCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChangeCodeMapper {
    long countByExample(ChangeCodeExample example);

    int deleteByExample(ChangeCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChangeCode record);

    int insertSelective(ChangeCode record);

    List<ChangeCode> selectByExample(ChangeCodeExample example);

    ChangeCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChangeCode record, @Param("example") ChangeCodeExample example);

    int updateByExample(@Param("record") ChangeCode record, @Param("example") ChangeCodeExample example);

    int updateByPrimaryKeySelective(ChangeCode record);

    int updateByPrimaryKey(ChangeCode record);

	List<ChangeCode> page(Integer search, int limit, Integer pageSize, Byte dataOfPart,String productionTaskNumber);

	int findTotal(Integer search, Byte dataOfPart,String productionTaskNumber);

	void insertBatch(List<ChangeCode> ccs);

	List<ChangeCode> countByChangeType(String productionTaskNumber);

	List<ChangeCode> findChangeCodeLinkTestExample(String productionTaskNumber);
}