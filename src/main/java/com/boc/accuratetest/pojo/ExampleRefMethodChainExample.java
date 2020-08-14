package com.boc.accuratetest.pojo;

import java.util.ArrayList;
import java.util.List;

public class ExampleRefMethodChainExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExampleRefMethodChainExample() {
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

        public Criteria andTestingExampleIdIsNull() {
            addCriterion("testing_example_id is null");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdIsNotNull() {
            addCriterion("testing_example_id is not null");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdEqualTo(Integer value) {
            addCriterion("testing_example_id =", value, "testingExampleId");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdNotEqualTo(Integer value) {
            addCriterion("testing_example_id <>", value, "testingExampleId");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdGreaterThan(Integer value) {
            addCriterion("testing_example_id >", value, "testingExampleId");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("testing_example_id >=", value, "testingExampleId");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdLessThan(Integer value) {
            addCriterion("testing_example_id <", value, "testingExampleId");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdLessThanOrEqualTo(Integer value) {
            addCriterion("testing_example_id <=", value, "testingExampleId");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdIn(List<Integer> values) {
            addCriterion("testing_example_id in", values, "testingExampleId");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdNotIn(List<Integer> values) {
            addCriterion("testing_example_id not in", values, "testingExampleId");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdBetween(Integer value1, Integer value2) {
            addCriterion("testing_example_id between", value1, value2, "testingExampleId");
            return (Criteria) this;
        }

        public Criteria andTestingExampleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("testing_example_id not between", value1, value2, "testingExampleId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdIsNull() {
            addCriterion("method_chain_original_id is null");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdIsNotNull() {
            addCriterion("method_chain_original_id is not null");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdEqualTo(String value) {
            addCriterion("method_chain_original_id =", value, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdNotEqualTo(String value) {
            addCriterion("method_chain_original_id <>", value, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdGreaterThan(String value) {
            addCriterion("method_chain_original_id >", value, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdGreaterThanOrEqualTo(String value) {
            addCriterion("method_chain_original_id >=", value, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdLessThan(String value) {
            addCriterion("method_chain_original_id <", value, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdLessThanOrEqualTo(String value) {
            addCriterion("method_chain_original_id <=", value, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdLike(String value) {
            addCriterion("method_chain_original_id like", value, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdNotLike(String value) {
            addCriterion("method_chain_original_id not like", value, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdIn(List<String> values) {
            addCriterion("method_chain_original_id in", values, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdNotIn(List<String> values) {
            addCriterion("method_chain_original_id not in", values, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdBetween(String value1, String value2) {
            addCriterion("method_chain_original_id between", value1, value2, "methodChainOriginalId");
            return (Criteria) this;
        }

        public Criteria andMethodChainOriginalIdNotBetween(String value1, String value2) {
            addCriterion("method_chain_original_id not between", value1, value2, "methodChainOriginalId");
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