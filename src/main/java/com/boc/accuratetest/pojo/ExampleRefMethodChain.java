package com.boc.accuratetest.pojo;

public class ExampleRefMethodChain {
    private Integer testingExampleId;

    private String methodChainOriginalId;

    public Integer getTestingExampleId() {
        return testingExampleId;
    }

    public void setTestingExampleId(Integer testingExampleId) {
        this.testingExampleId = testingExampleId;
    }

    public String getMethodChainOriginalId() {
        return methodChainOriginalId;
    }

    public void setMethodChainOriginalId(String methodChainOriginalId) {
        this.methodChainOriginalId = methodChainOriginalId == null ? null : methodChainOriginalId.trim();
    }
}