package com.boc.accuratetest.pojo;

public class ChangeCode {
    private Integer id;

    private String packageName;

    private String javabeanName;

    private String methodName;

    private String paramType;

    private Byte changeType;

    private String methodBody;

    private Byte testingOrNot;

    private String productionTaskNumber;
    
    private String linkTestExample; // 非数据库字段，关联的测试用例
    
    private String testExampleId; // 非数据库字段，关联的测试用例（一对一、一对多的关系。该字段仅表示一个用例的主键id）
    
    private String mcoid; // 非数据库字段，知识库表主键
    
    public String getMcoid() {
		return mcoid;
	}

	public void setMcoid(String mcoid) {
		this.mcoid = mcoid;
	}

	public String getTestExampleId() {
		return testExampleId;
	}

	public void setTestExampleId(String testExampleId) {
		this.testExampleId = testExampleId;
	}

	public String getLinkTestExample() {
		return linkTestExample;
	}

	public void setLinkTestExample(String linkTestExample) {
		this.linkTestExample = linkTestExample;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Byte getChangeType() {
        return changeType;
    }

    public void setChangeType(Byte changeType) {
        this.changeType = changeType;
    }

    public String getMethodBody() {
        return methodBody;
    }

    public void setMethodBody(String methodBody) {
        this.methodBody = methodBody == null ? null : methodBody.trim();
    }

    public Byte getTestingOrNot() {
        return testingOrNot;
    }

    public void setTestingOrNot(Byte testingOrNot) {
        this.testingOrNot = testingOrNot;
    }

    public String getProductionTaskNumber() {
        return productionTaskNumber;
    }

    public void setProductionTaskNumber(String productionTaskNumber) {
        this.productionTaskNumber = productionTaskNumber == null ? null : productionTaskNumber.trim();
    }
}