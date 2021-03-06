package com.boc.accuratetest.biz;

import java.util.List;
import java.util.Set;

import com.boc.accuratetest.pojo.MethodChainOriginal;
import com.boc.accuratetest.pojo.TestedMethods;

public interface MethodChainOriginalBiz {

	void insertBatch(List<MethodChainOriginal> ms);

	void insertBatchForTestedMethods(List<TestedMethods> tms);

	List<MethodChainOriginal> getMethodLinkByTestExampleId(Integer testExampleId);

	void deleteByTestingExampleId(Integer testExampleId);

	List<MethodChainOriginal> getMethodLinkByTestExampleIds(Set<Integer> testExampleIds);

}
