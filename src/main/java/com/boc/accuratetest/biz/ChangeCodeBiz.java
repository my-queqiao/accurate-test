package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.ChangeCode;


public interface ChangeCodeBiz {

	List<ChangeCode> page(Integer pageNumber, Integer pageSize, Integer search,Byte dataOfPart,String productionTaskNumber);

	Integer findTotal(Integer search,Byte dataOfPart,String productionTaskNumber);

	void insertBatch(List<ChangeCode> ccs);

	List<ChangeCode> countByChangeType(String productionTaskNumber);

	List<ChangeCode> findChangeCodeLinkTestExample(String productionTaskNumber);

	void deleteByProductionTaskNumber(String productionTaskNumber);

	ChangeCode getById(Integer id);
}
