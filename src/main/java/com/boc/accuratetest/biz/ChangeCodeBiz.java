package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.ChangeCode;


public interface ChangeCodeBiz {

	List<ChangeCode> page(Integer pageNumber, Integer pageSize, Integer search,Byte dataOfPart);

	Integer findTotal(Integer search,Byte dataOfPart);

	void insertBatch(List<ChangeCode> ccs);

	void deleteByGitUrlAndBranchs(String gitUrlAndBranchs);

	List<ChangeCode> countByChangeType();

	List<ChangeCode> findChangeCodeLinkTestExample();
}
