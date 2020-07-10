package com.boc.accuratetest.pojo;

public class TestingExample {
    private Integer id;

    private String belongProduct;

    private String function;

    private String subfunction;

    private String testItem;

    private String testPoint;

    private String testCaseNumber;

    private String testOperationExplain;

    private String expectedResults;

    private String productionTaskNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBelongProduct() {
        return belongProduct;
    }

    public void setBelongProduct(String belongProduct) {
        this.belongProduct = belongProduct == null ? null : belongProduct.trim();
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function == null ? null : function.trim();
    }

    public String getSubfunction() {
        return subfunction;
    }

    public void setSubfunction(String subfunction) {
        this.subfunction = subfunction == null ? null : subfunction.trim();
    }

    public String getTestItem() {
        return testItem;
    }

    public void setTestItem(String testItem) {
        this.testItem = testItem == null ? null : testItem.trim();
    }

    public String getTestPoint() {
        return testPoint;
    }

    public void setTestPoint(String testPoint) {
        this.testPoint = testPoint == null ? null : testPoint.trim();
    }

    public String getTestCaseNumber() {
        return testCaseNumber;
    }

    public void setTestCaseNumber(String testCaseNumber) {
        this.testCaseNumber = testCaseNumber == null ? null : testCaseNumber.trim();
    }

    public String getTestOperationExplain() {
        return testOperationExplain;
    }

    public void setTestOperationExplain(String testOperationExplain) {
        this.testOperationExplain = testOperationExplain == null ? null : testOperationExplain.trim();
    }

    public String getExpectedResults() {
        return expectedResults;
    }

    public void setExpectedResults(String expectedResults) {
        this.expectedResults = expectedResults == null ? null : expectedResults.trim();
    }

    public String getProductionTaskNumber() {
        return productionTaskNumber;
    }

    public void setProductionTaskNumber(String productionTaskNumber) {
        this.productionTaskNumber = productionTaskNumber == null ? null : productionTaskNumber.trim();
    }
}