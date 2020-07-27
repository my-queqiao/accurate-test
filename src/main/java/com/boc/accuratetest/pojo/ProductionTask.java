package com.boc.accuratetest.pojo;

public class ProductionTask {
    private Integer id;

    private String productionTaskNumber;

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
}