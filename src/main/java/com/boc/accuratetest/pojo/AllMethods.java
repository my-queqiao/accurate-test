package com.boc.accuratetest.pojo;

public class AllMethods {
    private Integer id;

    private String packageName;

    private String javabeanName;

    private String methodName;

    private String paramType;
    
    // 统计。（非数据库字段）
    private Integer classNumber; // 每个包的各自类数量
    private Integer testedclassNumber; // 每个包的各自已测试类数量
    
    private Integer methodNumber; // 每个包、类中的方法数量
    private Integer methodTestedNumber; // 每个包、类中的已测试方法数量
    
    private Integer addFunNumber; // 每一个包、类中的新增方法数量
    private Integer addFunTestedNumber; // 每个包、类中的新增方法已测试数量
    
    private Integer modifyFunNumber; // 每一个包、类中的新增方法数量
    private Integer modifyFunTestedNumber; // 每个包、类中的新增方法已测试数量
    
    private Integer changeType; // 是否为新增方法，变更类型
    private String testedOrNot; // 
    
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

	public Integer getTestedclassNumber() {
		return testedclassNumber;
	}

	public void setTestedclassNumber(Integer testedclassNumber) {
		this.testedclassNumber = testedclassNumber;
	}

	public Integer getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
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

	public String getTestedOrNot() {
		return testedOrNot;
	}

	public void setTestedOrNot(String testedOrNot) {
		this.testedOrNot = testedOrNot;
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
}