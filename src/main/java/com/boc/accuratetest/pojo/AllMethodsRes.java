package com.boc.accuratetest.pojo;

public class AllMethodsRes {
    private Integer id;

    private String packageName;

    private String javabeanName;

    private String methodName;

    private String paramType;
    
    // 统计。（非数据库字段）
    private Integer funNumber; // 每个类中的方法数量
    private Integer testedNumber; // 每个类中的已测试方法数量
    private Integer addFunNumber; // 每一个类中的新增方法数量
    private Integer addFunTestedNumber; // 每个类中的新增方法已测试数量
    
    private Integer testedOrNot; // 0未测试，1已测试
    private Integer addOrNot; // 是否为新增方法
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((javabeanName == null) ? 0 : javabeanName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		AllMethodsRes other = (AllMethodsRes) obj;
		if (javabeanName == null) {
			if (other.javabeanName != null)
				return false;
		} else if (!javabeanName.equals(other.javabeanName))
			return false;
		return true;
	}

	public Integer getFunNumber() {
		return funNumber;
	}

	public void setFunNumber(Integer funNumber) {
		this.funNumber = funNumber;
	}

	public Integer getTestedNumber() {
		return testedNumber;
	}

	public void setTestedNumber(Integer testedNumber) {
		this.testedNumber = testedNumber;
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

	public Integer getTestedOrNot() {
		return testedOrNot;
	}

	public void setTestedOrNot(Integer testedOrNot) {
		this.testedOrNot = testedOrNot;
	}

	public Integer getAddOrNot() {
		return addOrNot;
	}

	public void setAddOrNot(Integer addOrNot) {
		this.addOrNot = addOrNot;
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