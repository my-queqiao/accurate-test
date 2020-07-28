package com.boc.accuratetest.pojo;

public class ProductionTask {
    private Integer id;

    private String productionTaskNumber;

    private String gitUrl;

    private String masterBranch;

    private String testBranch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductionTaskNumber() {
        return productionTaskNumber;
    }

    public void setProductionTaskNumber(String productionTaskNumber) {
        this.productionTaskNumber = productionTaskNumber == null ? null : productionTaskNumber.trim();
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl == null ? null : gitUrl.trim();
    }

    public String getMasterBranch() {
        return masterBranch;
    }

    public void setMasterBranch(String masterBranch) {
        this.masterBranch = masterBranch == null ? null : masterBranch.trim();
    }

    public String getTestBranch() {
        return testBranch;
    }

    public void setTestBranch(String testBranch) {
        this.testBranch = testBranch == null ? null : testBranch.trim();
    }
}