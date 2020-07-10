package com.boc.accuratetest.biz;

import java.util.List;

import com.boc.accuratetest.pojo.MethodChainOriginal;
import com.boc.accuratetest.pojo.TestedMethods;

public interface MethodChainOriginalBiz {

	List<MethodChainOriginal> getAll();

	int findTotal();

	void insertBatch(List<MethodChainOriginal> ms);

	void insertBatchForTestedMethods(List<TestedMethods> tms);

}
