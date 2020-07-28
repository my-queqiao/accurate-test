package com.boc.accuratetest.pojo;

import java.util.ArrayList;
import java.util.List;

public class ProductionTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductionTaskExample() {
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

        public Criteria andGitUrlIsNull() {
            addCriterion("git_url is null");
            return (Criteria) this;
        }

        public Criteria andGitUrlIsNotNull() {
            addCriterion("git_url is not null");
            return (Criteria) this;
        }

        public Criteria andGitUrlEqualTo(String value) {
            addCriterion("git_url =", value, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlNotEqualTo(String value) {
            addCriterion("git_url <>", value, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlGreaterThan(String value) {
            addCriterion("git_url >", value, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlGreaterThanOrEqualTo(String value) {
            addCriterion("git_url >=", value, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlLessThan(String value) {
            addCriterion("git_url <", value, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlLessThanOrEqualTo(String value) {
            addCriterion("git_url <=", value, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlLike(String value) {
            addCriterion("git_url like", value, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlNotLike(String value) {
            addCriterion("git_url not like", value, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlIn(List<String> values) {
            addCriterion("git_url in", values, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlNotIn(List<String> values) {
            addCriterion("git_url not in", values, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlBetween(String value1, String value2) {
            addCriterion("git_url between", value1, value2, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andGitUrlNotBetween(String value1, String value2) {
            addCriterion("git_url not between", value1, value2, "gitUrl");
            return (Criteria) this;
        }

        public Criteria andMasterBranchIsNull() {
            addCriterion("master_branch is null");
            return (Criteria) this;
        }

        public Criteria andMasterBranchIsNotNull() {
            addCriterion("master_branch is not null");
            return (Criteria) this;
        }

        public Criteria andMasterBranchEqualTo(String value) {
            addCriterion("master_branch =", value, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchNotEqualTo(String value) {
            addCriterion("master_branch <>", value, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchGreaterThan(String value) {
            addCriterion("master_branch >", value, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchGreaterThanOrEqualTo(String value) {
            addCriterion("master_branch >=", value, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchLessThan(String value) {
            addCriterion("master_branch <", value, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchLessThanOrEqualTo(String value) {
            addCriterion("master_branch <=", value, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchLike(String value) {
            addCriterion("master_branch like", value, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchNotLike(String value) {
            addCriterion("master_branch not like", value, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchIn(List<String> values) {
            addCriterion("master_branch in", values, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchNotIn(List<String> values) {
            addCriterion("master_branch not in", values, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchBetween(String value1, String value2) {
            addCriterion("master_branch between", value1, value2, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andMasterBranchNotBetween(String value1, String value2) {
            addCriterion("master_branch not between", value1, value2, "masterBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchIsNull() {
            addCriterion("test_branch is null");
            return (Criteria) this;
        }

        public Criteria andTestBranchIsNotNull() {
            addCriterion("test_branch is not null");
            return (Criteria) this;
        }

        public Criteria andTestBranchEqualTo(String value) {
            addCriterion("test_branch =", value, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchNotEqualTo(String value) {
            addCriterion("test_branch <>", value, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchGreaterThan(String value) {
            addCriterion("test_branch >", value, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchGreaterThanOrEqualTo(String value) {
            addCriterion("test_branch >=", value, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchLessThan(String value) {
            addCriterion("test_branch <", value, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchLessThanOrEqualTo(String value) {
            addCriterion("test_branch <=", value, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchLike(String value) {
            addCriterion("test_branch like", value, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchNotLike(String value) {
            addCriterion("test_branch not like", value, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchIn(List<String> values) {
            addCriterion("test_branch in", values, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchNotIn(List<String> values) {
            addCriterion("test_branch not in", values, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchBetween(String value1, String value2) {
            addCriterion("test_branch between", value1, value2, "testBranch");
            return (Criteria) this;
        }

        public Criteria andTestBranchNotBetween(String value1, String value2) {
            addCriterion("test_branch not between", value1, value2, "testBranch");
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