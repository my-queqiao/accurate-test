package com.boc.accuratetest.pojo;

import org.springframework.util.StringUtils;

public class ChangeCode {
    private Integer id;

    private String gitUrl;

    private String packageName;

    private String javabeanName;

    private String methodName;

    private String paramType;

    private Byte changeType;

    private String methodBody;

    private Byte testingOrNot;
    
    private String linkTestExample; // 非数据库字段，关联多个测试用例时，用英文逗号分割
    
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

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl == null ? null : gitUrl.trim();
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
}