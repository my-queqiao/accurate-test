package com.boc.accuratetest.pojo;

public class AllMethods {
    private Integer id;

    private String packageName;

    private String javabeanName;

    private String methodName;

    private String paramType;

    private String productionTaskNumber;
    // 统计。（非数据库字段）
    private Integer classNumber = 0; // 每个包的各自类数量
    private Integer testedclassNumber = 0; // 每个包的各自已测试类数量
    
    private Integer methodNumber = 0; // 每个包、类中的方法数量
    private Integer methodTestedNumber = 0; // 每个包、类中的已测试方法数量
    
    private Integer addFunNumber = 0; // 每一个包、类中的新增方法数量
    private Integer addFunTestedNumber = 0; // 每个包、类中的新增方法已测试数量
    
    private Integer modifyFunNumber = 0; // 每一个包、类中的新增方法数量
    private Integer modifyFunTestedNumber = 0; // 每个包、类中的新增方法已测试数量
    
    private Integer changeType; // 是否为新增方法，变更类型
    
    // 分析报告页面
	private Integer testExampleNumber = 0; // 关联案例
	private Integer recommendTestExampleNumber = 0; // 推荐案例
	private Integer executedTestExampleNumber = 0; // 已执行案例

    public Integer getTestExampleNumber() {
		return testExampleNumber;
	}

	public void setTestExampleNumber(Integer testExampleNumber) {
		this.testExampleNumber = testExampleNumber;
	}

	public Integer getRecommendTestExampleNumber() {
		return recommendTestExampleNumber;
	}

	public void setRecommendTestExampleNumber(Integer recommendTestExampleNumber) {
		this.recommendTestExampleNumber = recommendTestExampleNumber;
	}

	public Integer getExecutedTestExampleNumber() {
		return executedTestExampleNumber;
	}

	public void setExecutedTestExampleNumber(Integer executedTestExampleNumber) {
		this.executedTestExampleNumber = executedTestExampleNumber;
	}

	public Integer getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}

	public Integer getTestedclassNumber() {
		return testedclassNumber;
	}

	public void setTestedclassNumber(Integer testedclassNumber) {
		this.testedclassNumber = testedclassNumber;
	}

	public Integer getMethodNumber() {
		return methodNumber;
	}

	public void setMethodNumber(Integer methodNumber) {
		this.methodNumber = methodNumber;
	}

	public Integer getMethodTestedNumber() {
		return methodTestedNumber;
	}

	public void setMethodTestedNumber(Integer methodTestedNumber) {
		this.methodTestedNumber = methodTestedNumber;
	}

	public Integer getAddFunNumber() {
		return addFunNumber;
	}

	public void setAddFunNumber(Integer addFunNumber) {
		this.addFunNumber = addFunNumber;
	}

	public Integer getAddFunTestedNumber() {
		return addFunTestedNumber;
	}

	public void setAddFunTestedNumber(Integer addFunTestedNumber) {
		this.addFunTestedNumber = addFunTestedNumber;
	}

	public Integer getModifyFunNumber() {
		return modifyFunNumber;
	}

	public void setModifyFunNumber(Integer modifyFunNumber) {
		this.modifyFunNumber = modifyFunNumber;
	}

	public Integer getModifyFunTestedNumber() {
		return modifyFunTestedNumber;
	}

	public void setModifyFunTestedNumber(Integer modifyFunTestedNumber) {
		this.modifyFunTestedNumber = modifyFunTestedNumber;
	}

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
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

    public String getProductionTaskNumber() {
        return productionTaskNumber;
    }

    public void setProductionTaskNumber(String productionTaskNumber) {
        this.productionTaskNumber = productionTaskNumber == null ? null : productionTaskNumber.trim();
    }
}