package com.boc.accuratetest.pojo;

import java.util.List;

public class MethodChainOriginal {
    private String id;

    private String packageName;

    private String javabeanName;

    private String methodName;

    private String paramType;

    private String callTime;

    private Long callTimeLong;

    private String productionTaskNumber;

    private String lastMethodId;

    private Integer testingExampleId;
    
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName == null ? null : packageName.trim();
    }

    public String getJavabeanName() {
        return javabeanName;
    }

    public void setJavabeanName(String javabeanName) {
        this.javabeanName = javabeanName == null ? null : javabeanName.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType == null ? null : paramType.trim();
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime == null ? null : callTime.trim();
    }

    public Long getCallTimeLong() {
        return callTimeLong;
    }

    public void setCallTimeLong(Long callTimeLong) {
        this.callTimeLong = callTimeLong;
    }

    public String getProductionTaskNumber() {
        return productionTaskNumber;
    }

    public void setProductionTaskNumber(String productionTaskNumber) {
        this.productionTaskNumber = productionTaskNumber == null ? null : productionTaskNumber.trim();
    }

    public String getLastMethodId() {
        return lastMethodId;
    }

    public void setLastMethodId(String lastMethodId) {
        this.lastMethodId = lastMethodId == null ? null : lastMethodId.trim();
    }

    public Integer getTestingExampleId() {
        return testingExampleId;
    }

    public void setTestingExampleId(Integer testingExampleId) {
        this.testingExampleId = testingExampleId;
    }
}