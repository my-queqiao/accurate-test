package com.boc.accuratetest.pojo;

public class TestingExample {
    private Integer id;

    private String testingExampleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestingExampleName() {
        return testingExampleName;
    }

    public void setTestingExampleName(String testingExampleName) {
        this.testingExampleName = testingExampleName == null ? null : testingExampleName.trim();
    }
}