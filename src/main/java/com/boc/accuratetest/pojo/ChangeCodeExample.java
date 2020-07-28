package com.boc.accuratetest.pojo;

import java.util.ArrayList;
import java.util.List;

public class ChangeCodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChangeCodeExample() {
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

        public Criteria andPackageNameIsNull() {
            addCriterion("package_name is null");
            return (Criteria) this;
        }

        public Criteria andPackageNameIsNotNull() {
            addCriterion("package_name is not null");
            return (Criteria) this;
        }

        public Criteria andPackageNameEqualTo(String value) {
            addCriterion("package_name =", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameNotEqualTo(String value) {
            addCriterion("package_name <>", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameGreaterThan(String value) {
            addCriterion("package_name >", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameGreaterThanOrEqualTo(String value) {
            addCriterion("package_name >=", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameLessThan(String value) {
            addCriterion("package_name <", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameLessThanOrEqualTo(String value) {
            addCriterion("package_name <=", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameLike(String value) {
            addCriterion("package_name like", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameNotLike(String value) {
            addCriterion("package_name not like", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameIn(List<String> values) {
            addCriterion("package_name in", values, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameNotIn(List<String> values) {
            addCriterion("package_name not in", values, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameBetween(String value1, String value2) {
            addCriterion("package_name between", value1, value2, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameNotBetween(String value1, String value2) {
            addCriterion("package_name not between", value1, value2, "packageName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameIsNull() {
            addCriterion("javabean_name is null");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameIsNotNull() {
            addCriterion("javabean_name is not null");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameEqualTo(String value) {
            addCriterion("javabean_name =", value, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameNotEqualTo(String value) {
            addCriterion("javabean_name <>", value, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameGreaterThan(String value) {
            addCriterion("javabean_name >", value, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameGreaterThanOrEqualTo(String value) {
            addCriterion("javabean_name >=", value, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameLessThan(String value) {
            addCriterion("javabean_name <", value, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameLessThanOrEqualTo(String value) {
            addCriterion("javabean_name <=", value, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameLike(String value) {
            addCriterion("javabean_name like", value, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameNotLike(String value) {
            addCriterion("javabean_name not like", value, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameIn(List<String> values) {
            addCriterion("javabean_name in", values, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameNotIn(List<String> values) {
            addCriterion("javabean_name not in", values, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameBetween(String value1, String value2) {
            addCriterion("javabean_name between", value1, value2, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andJavabeanNameNotBetween(String value1, String value2) {
            addCriterion("javabean_name not between", value1, value2, "javabeanName");
            return (Criteria) this;
        }

        public Criteria andMethodNameIsNull() {
            addCriterion("method_name is null");
            return (Criteria) this;
        }

        public Criteria andMethodNameIsNotNull() {
            addCriterion("method_name is not null");
            return (Criteria) this;
        }

        public Criteria andMethodNameEqualTo(String value) {
            addCriterion("method_name =", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotEqualTo(String value) {
            addCriterion("method_name <>", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameGreaterThan(String value) {
            addCriterion("method_name >", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameGreaterThanOrEqualTo(String value) {
            addCriterion("method_name >=", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLessThan(String value) {
            addCriterion("method_name <", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLessThanOrEqualTo(String value) {
            addCriterion("method_name <=", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLike(String value) {
            addCriterion("method_name like", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotLike(String value) {
            addCriterion("method_name not like", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameIn(List<String> values) {
            addCriterion("method_name in", values, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotIn(List<String> values) {
            addCriterion("method_name not in", values, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameBetween(String value1, String value2) {
            addCriterion("method_name between", value1, value2, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotBetween(String value1, String value2) {
            addCriterion("method_name not between", value1, value2, "methodName");
            return (Criteria) this;
        }

        public Criteria andParamTypeIsNull() {
            addCriterion("param_type is null");
            return (Criteria) this;
        }

        public Criteria andParamTypeIsNotNull() {
            addCriterion("param_type is not null");
            return (Criteria) this;
        }

        public Criteria andParamTypeEqualTo(String value) {
            addCriterion("param_type =", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotEqualTo(String value) {
            addCriterion("param_type <>", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeGreaterThan(String value) {
            addCriterion("param_type >", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeGreaterThanOrEqualTo(String value) {
            addCriterion("param_type >=", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeLessThan(String value) {
            addCriterion("param_type <", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeLessThanOrEqualTo(String value) {
            addCriterion("param_type <=", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeLike(String value) {
            addCriterion("param_type like", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotLike(String value) {
            addCriterion("param_type not like", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeIn(List<String> values) {
            addCriterion("param_type in", values, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotIn(List<String> values) {
            addCriterion("param_type not in", values, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeBetween(String value1, String value2) {
            addCriterion("param_type between", value1, value2, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotBetween(String value1, String value2) {
            addCriterion("param_type not between", value1, value2, "paramType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeIsNull() {
            addCriterion("change_type is null");
            return (Criteria) this;
        }

        public Criteria andChangeTypeIsNotNull() {
            addCriterion("change_type is not null");
            return (Criteria) this;
        }

        public Criteria andChangeTypeEqualTo(Byte value) {
            addCriterion("change_type =", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeNotEqualTo(Byte value) {
            addCriterion("change_type <>", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeGreaterThan(Byte value) {
            addCriterion("change_type >", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("change_type >=", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeLessThan(Byte value) {
            addCriterion("change_type <", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeLessThanOrEqualTo(Byte value) {
            addCriterion("change_type <=", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeIn(List<Byte> values) {
            addCriterion("change_type in", values, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeNotIn(List<Byte> values) {
            addCriterion("change_type not in", values, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeBetween(Byte value1, Byte value2) {
            addCriterion("change_type between", value1, value2, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("change_type not between", value1, value2, "changeType");
            return (Criteria) this;
        }

        public Criteria andMethodBodyIsNull() {
            addCriterion("method_body is null");
            return (Criteria) this;
        }

        public Criteria andMethodBodyIsNotNull() {
            addCriterion("method_body is not null");
            return (Criteria) this;
        }

        public Criteria andMethodBodyEqualTo(String value) {
            addCriterion("method_body =", value, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyNotEqualTo(String value) {
            addCriterion("method_body <>", value, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyGreaterThan(String value) {
            addCriterion("method_body >", value, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyGreaterThanOrEqualTo(String value) {
            addCriterion("method_body >=", value, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyLessThan(String value) {
            addCriterion("method_body <", value, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyLessThanOrEqualTo(String value) {
            addCriterion("method_body <=", value, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyLike(String value) {
            addCriterion("method_body like", value, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyNotLike(String value) {
            addCriterion("method_body not like", value, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyIn(List<String> values) {
            addCriterion("method_body in", values, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyNotIn(List<String> values) {
            addCriterion("method_body not in", values, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyBetween(String value1, String value2) {
            addCriterion("method_body between", value1, value2, "methodBody");
            return (Criteria) this;
        }

        public Criteria andMethodBodyNotBetween(String value1, String value2) {
            addCriterion("method_body not between", value1, value2, "methodBody");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotIsNull() {
            addCriterion("testing_or_not is null");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotIsNotNull() {
            addCriterion("testing_or_not is not null");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotEqualTo(Byte value) {
            addCriterion("testing_or_not =", value, "testingOrNot");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotNotEqualTo(Byte value) {
            addCriterion("testing_or_not <>", value, "testingOrNot");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotGreaterThan(Byte value) {
            addCriterion("testing_or_not >", value, "testingOrNot");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotGreaterThanOrEqualTo(Byte value) {
            addCriterion("testing_or_not >=", value, "testingOrNot");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotLessThan(Byte value) {
            addCriterion("testing_or_not <", value, "testingOrNot");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotLessThanOrEqualTo(Byte value) {
            addCriterion("testing_or_not <=", value, "testingOrNot");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotIn(List<Byte> values) {
            addCriterion("testing_or_not in", values, "testingOrNot");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotNotIn(List<Byte> values) {
            addCriterion("testing_or_not not in", values, "testingOrNot");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotBetween(Byte value1, Byte value2) {
            addCriterion("testing_or_not between", value1, value2, "testingOrNot");
            return (Criteria) this;
        }

        public Criteria andTestingOrNotNotBetween(Byte value1, Byte value2) {
            addCriterion("testing_or_not not between", value1, value2, "testingOrNot");
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