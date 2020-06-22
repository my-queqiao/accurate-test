package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.MethodChainOriginal;

public interface MethodChainOriginalBiz {

	List<MethodChainOriginal> getAll();

	int findTotal();

	void insertBatch(List<MethodChainOriginal> ms);

}
