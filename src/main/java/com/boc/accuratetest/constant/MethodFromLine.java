package com.boc.accuratetest.constant;

import java.util.List;
/**
 * 原始数据依据getStackTrace方法分为若干个小链，去重整合之后是大链。
 * @author tom
 */
public class MethodFromLine {
	/**
	 * 注意：主键id是唯一的，可以确定唯一一个当前对象，methodInfo不同方法链中可以有同一个方法
	 */
	private String id; // 这一行数据的主键id
	private boolean father = false; // 是否为顶级方法
	private String methodInfo; // 包、类、方法名、参数类型（就是收集的原始数据的任意一行）
	
	private List<MethodFromLine> nexts; // 下一个、或多个方法
	private MethodFromLine last; // 上一个方法
	
	public MethodFromLine() {
		// TODO Auto-generated constructor stub
	}
	public MethodFromLine(boolean father, String id, String methodInfo, MethodFromLine last,List<MethodFromLine> nexts) {
		this.id = id;
		this.father = father;
		this.methodInfo = methodInfo;
		this.nexts = nexts;
		this.last = last;
	}

	public MethodFromLine getLast() {
		return last;
	}
	public void setLast(MethodFromLine last) {
		this.last = last;
	}
	public boolean isFather() {
		return father;
	}
	public void setFather(boolean father) {
		this.father = father;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMethodInfo() {
		return methodInfo;
	}
	public void setMethodInfo(String methodInfo) {
		this.methodInfo = methodInfo;
	}
	public List<MethodFromLine> getNexts() {
		return nexts;
	}
	public void setNexts(List<MethodFromLine> nexts) {
		this.nexts = nexts;
	}
	
}
