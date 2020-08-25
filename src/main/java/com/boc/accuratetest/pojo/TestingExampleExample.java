package com.boc.accuratetest.pojo;

import java.util.ArrayList;
import java.util.List;

public class TestingExampleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestingExampleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBelongProductIsNull() {
            addCriterion("belong_product is null");
            return (Criteria) this;
        }

        public Criteria andBelongProductIsNotNull() {
            addCriterion("belong_product is not null");
            return (Criteria) this;
        }

        public Criteria andBelongProductEqualTo(String value) {
            addCriterion("belong_product =", value, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductNotEqualTo(String value) {
            addCriterion("belong_product <>", value, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductGreaterThan(String value) {
            addCriterion("belong_product >", value, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductGreaterThanOrEqualTo(String value) {
            addCriterion("belong_product >=", value, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductLessThan(String value) {
            addCriterion("belong_product <", value, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductLessThanOrEqualTo(String value) {
            addCriterion("belong_product <=", value, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductLike(String value) {
            addCriterion("belong_product like", value, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductNotLike(String value) {
            addCriterion("belong_product not like", value, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductIn(List<String> values) {
            addCriterion("belong_product in", values, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductNotIn(List<String> values) {
            addCriterion("belong_product not in", values, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductBetween(String value1, String value2) {
            addCriterion("belong_product between", value1, value2, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andBelongProductNotBetween(String value1, String value2) {
            addCriterion("belong_product not between", value1, value2, "belongProduct");
            return (Criteria) this;
        }

        public Criteria andFunctionIsNull() {
            addCriterion("function is null");
            return (Criteria) this;
        }

        public Criteria andFunctionIsNotNull() {
            addCriterion("function is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionEqualTo(String value) {
            addCriterion("function =", value, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionNotEqualTo(String value) {
            addCriterion("function <>", value, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionGreaterThan(String value) {
            addCriterion("function >", value, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionGreaterThanOrEqualTo(String value) {
            addCriterion("function >=", value, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionLessThan(String value) {
            addCriterion("function <", value, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionLessThanOrEqualTo(String value) {
            addCriterion("function <=", value, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionLike(String value) {
            addCriterion("function like", value, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionNotLike(String value) {
            addCriterion("function not like", value, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionIn(List<String> values) {
            addCriterion("function in", values, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionNotIn(List<String> values) {
            addCriterion("function not in", values, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionBetween(String value1, String value2) {
            addCriterion("function between", value1, value2, "function");
            return (Criteria) this;
        }

        public Criteria andFunctionNotBetween(String value1, String value2) {
            addCriterion("function not between", value1, value2, "function");
            return (Criteria) this;
        }

        public Criteria andSubfunctionIsNull() {
            addCriterion("subfunction is null");
            return (Criteria) this;
        }

        public Criteria andSubfunctionIsNotNull() {
            addCriterion("subfunction is not null");
            return (Criteria) this;
        }

        public Criteria andSubfunctionEqualTo(String value) {
            addCriterion("subfunction =", value, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionNotEqualTo(String value) {
            addCriterion("subfunction <>", value, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionGreaterThan(String value) {
            addCriterion("subfunction >", value, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionGreaterThanOrEqualTo(String value) {
            addCriterion("subfunction >=", value, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionLessThan(String value) {
            addCriterion("subfunction <", value, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionLessThanOrEqualTo(String value) {
            addCriterion("subfunction <=", value, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionLike(String value) {
            addCriterion("subfunction like", value, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionNotLike(String value) {
            addCriterion("subfunction not like", value, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionIn(List<String> values) {
            addCriterion("subfunction in", values, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionNotIn(List<String> values) {
            addCriterion("subfunction not in", values, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionBetween(String value1, String value2) {
            addCriterion("subfunction between", value1, value2, "subfunction");
            return (Criteria) this;
        }

        public Criteria andSubfunctionNotBetween(String value1, String value2) {
            addCriterion("subfunction not between", value1, value2, "subfunction");
            return (Criteria) this;
        }

        public Criteria andTestItemIsNull() {
            addCriterion("test_item is null");
            return (Criteria) this;
        }

        public Criteria andTestItemIsNotNull() {
            addCriterion("test_item is not null");
            return (Criteria) this;
        }

        public Criteria andTestItemEqualTo(String value) {
            addCriterion("test_item =", value, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemNotEqualTo(String value) {
            addCriterion("test_item <>", value, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemGreaterThan(String value) {
            addCriterion("test_item >", value, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemGreaterThanOrEqualTo(String value) {
            addCriterion("test_item >=", value, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemLessThan(String value) {
            addCriterion("test_item <", value, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemLessThanOrEqualTo(String value) {
            addCriterion("test_item <=", value, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemLike(String value) {
            addCriterion("test_item like", value, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemNotLike(String value) {
            addCriterion("test_item not like", value, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemIn(List<String> values) {
            addCriterion("test_item in", values, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemNotIn(List<String> values) {
            addCriterion("test_item not in", values, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemBetween(String value1, String value2) {
            addCriterion("test_item between", value1, value2, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestItemNotBetween(String value1, String value2) {
            addCriterion("test_item not between", value1, value2, "testItem");
            return (Criteria) this;
        }

        public Criteria andTestPointIsNull() {
            addCriterion("test_point is null");
            return (Criteria) this;
        }

        public Criteria andTestPointIsNotNull() {
            addCriterion("test_point is not null");
            return (Criteria) this;
        }

        public Criteria andTestPointEqualTo(String value) {
            addCriterion("test_point =", value, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointNotEqualTo(String value) {
            addCriterion("test_point <>", value, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointGreaterThan(String value) {
            addCriterion("test_point >", value, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointGreaterThanOrEqualTo(String value) {
            addCriterion("test_point >=", value, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointLessThan(String value) {
            addCriterion("test_point <", value, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointLessThanOrEqualTo(String value) {
            addCriterion("test_point <=", value, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointLike(String value) {
            addCriterion("test_point like", value, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointNotLike(String value) {
            addCriterion("test_point not like", value, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointIn(List<String> values) {
            addCriterion("test_point in", values, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointNotIn(List<String> values) {
            addCriterion("test_point not in", values, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointBetween(String value1, String value2) {
            addCriterion("test_point between", value1, value2, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestPointNotBetween(String value1, String value2) {
            addCriterion("test_point not between", value1, value2, "testPoint");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberIsNull() {
            addCriterion("test_case_number is null");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberIsNotNull() {
            addCriterion("test_case_number is not null");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberEqualTo(String value) {
            addCriterion("test_case_number =", value, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberNotEqualTo(String value) {
            addCriterion("test_case_number <>", value, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberGreaterThan(String value) {
            addCriterion("test_case_number >", value, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberGreaterThanOrEqualTo(String value) {
            addCriterion("test_case_number >=", value, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberLessThan(String value) {
            addCriterion("test_case_number <", value, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberLessThanOrEqualTo(String value) {
            addCriterion("test_case_number <=", value, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberLike(String value) {
            addCriterion("test_case_number like", value, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberNotLike(String value) {
            addCriterion("test_case_number not like", value, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberIn(List<String> values) {
            addCriterion("test_case_number in", values, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberNotIn(List<String> values) {
            addCriterion("test_case_number not in", values, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberBetween(String value1, String value2) {
            addCriterion("test_case_number between", value1, value2, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestCaseNumberNotBetween(String value1, String value2) {
            addCriterion("test_case_number not between", value1, value2, "testCaseNumber");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainIsNull() {
            addCriterion("test_operation_explain is null");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainIsNotNull() {
            addCriterion("test_operation_explain is not null");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainEqualTo(String value) {
            addCriterion("test_operation_explain =", value, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainNotEqualTo(String value) {
            addCriterion("test_operation_explain <>", value, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainGreaterThan(String value) {
            addCriterion("test_operation_explain >", value, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainGreaterThanOrEqualTo(String value) {
            addCriterion("test_operation_explain >=", value, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainLessThan(String value) {
            addCriterion("test_operation_explain <", value, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainLessThanOrEqualTo(String value) {
            addCriterion("test_operation_explain <=", value, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainLike(String value) {
            addCriterion("test_operation_explain like", value, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainNotLike(String value) {
            addCriterion("test_operation_explain not like", value, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainIn(List<String> values) {
            addCriterion("test_operation_explain in", values, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainNotIn(List<String> values) {
            addCriterion("test_operation_explain not in", values, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainBetween(String value1, String value2) {
            addCriterion("test_operation_explain between", value1, value2, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andTestOperationExplainNotBetween(String value1, String value2) {
            addCriterion("test_operation_explain not between", value1, value2, "testOperationExplain");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsIsNull() {
            addCriterion("expected_results is null");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsIsNotNull() {
            addCriterion("expected_results is not null");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsEqualTo(String value) {
            addCriterion("expected_results =", value, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsNotEqualTo(String value) {
            addCriterion("expected_results <>", value, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsGreaterThan(String value) {
            addCriterion("expected_results >", value, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsGreaterThanOrEqualTo(String value) {
            addCriterion("expected_results >=", value, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsLessThan(String value) {
            addCriterion("expected_results <", value, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsLessThanOrEqualTo(String value) {
            addCriterion("expected_results <=", value, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsLike(String value) {
            addCriterion("expected_results like", value, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsNotLike(String value) {
            addCriterion("expected_results not like", value, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsIn(List<String> values) {
            addCriterion("expected_results in", values, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsNotIn(List<String> values) {
            addCriterion("expected_results not in", values, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsBetween(String value1, String value2) {
            addCriterion("expected_results between", value1, value2, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andExpectedResultsNotBetween(String value1, String value2) {
            addCriterion("expected_results not between", value1, value2, "expectedResults");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberIsNull() {
            addCriterion("production_task_number is null");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberIsNotNull() {
            addCriterion("production_task_number is not null");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberEqualTo(String value) {
            addCriterion("production_task_number =", value, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberNotEqualTo(String value) {
            addCriterion("production_task_number <>", value, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberGreaterThan(String value) {
            addCriterion("production_task_number >", value, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberGreaterThanOrEqualTo(String value) {
            addCriterion("production_task_number >=", value, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberLessThan(String value) {
            addCriterion("production_task_number <", value, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberLessThanOrEqualTo(String value) {
            addCriterion("production_task_number <=", value, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberLike(String value) {
            addCriterion("production_task_number like", value, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberNotLike(String value) {
            addCriterion("production_task_number not like", value, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberIn(List<String> values) {
            addCriterion("production_task_number in", values, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberNotIn(List<String> values) {
            addCriterion("production_task_number not in", values, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberBetween(String value1, String value2) {
            addCriterion("production_task_number between", value1, value2, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andProductionTaskNumberNotBetween(String value1, String value2) {
            addCriterion("production_task_number not between", value1, value2, "productionTaskNumber");
            return (Criteria) this;
        }

        public Criteria andExecutedIsNull() {
            addCriterion("executed is null");
            return (Criteria) this;
        }

        public Criteria andExecutedIsNotNull() {
            addCriterion("executed is not null");
            return (Criteria) this;
        }

        public Criteria andExecutedEqualTo(Byte value) {
            addCriterion("executed =", value, "executed");
            return (Criteria) this;
        }

        public Criteria andExecutedNotEqualTo(Byte value) {
            addCriterion("executed <>", value, "executed");
            return (Criteria) this;
        }

        public Criteria andExecutedGreaterThan(Byte value) {
            addCriterion("executed >", value, "executed");
            return (Criteria) this;
        }

        public Criteria andExecutedGreaterThanOrEqualTo(Byte value) {
            addCriterion("executed >=", value, "executed");
            return (Criteria) this;
        }

        public Criteria andExecutedLessThan(Byte value) {
            addCriterion("executed <", value, "executed");
            return (Criteria) this;
        }

        public Criteria andExecutedLessThanOrEqualTo(Byte value) {
            addCriterion("executed <=", value, "executed");
            return (Criteria) this;
        }

        public Criteria andExecutedIn(List<Byte> values) {
            addCriterion("executed in", values, "executed");
            return (Criteria) this;
        }

        public Criteria andExecutedNotIn(List<Byte> values) {
            addCriterion("executed not in", values, "executed");
            return (Criteria) this;
        }

        public Criteria andExecutedBetween(Byte value1, Byte value2) {
            addCriterion("executed between", value1, value2, "executed");
            return (Criteria) this;
        }

        public Criteria andExecutedNotBetween(Byte value1, Byte value2) {
            addCriterion("executed not between", value1, value2, "executed");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}