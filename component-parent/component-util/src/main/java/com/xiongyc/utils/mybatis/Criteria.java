package com.xiongyc.utils.mybatis;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author YouCai.Xiong
 * @Date 2019年12月14日 - 下午10:48:32
 * @Info 初始版本  封装入参对象
 * @Version 1.0
 */
public class Criteria<T extends IModel> {

	public Criteria(Integer pageNum, Integer pageSize) {
		setPageNum(pageNum);
		setPageSize(pageSize);
	}

	public Criteria() {
		// TODO Auto-generated constructor stub
	}

	public Criteria<T> initParams() {
		this.params = new HashMap<String, Object>();
		return this;
	}
	
	public Criteria<T> clearParams() {
		if (this.params != null) this.params.clear();
		return this;
	}

	public Criteria<T> addParam(String key, Object val) {
		if(this.params == null) initParams();
		this.params.put(key, val);
		return this;
	}

	public Criteria<T> addAll(Map<String, Object> params) {  
		this.params.putAll(params);
		return this;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public T getRecord() {
		return record;
	}

	public void setRecord(T record) {
		this.record = record;
	}

	public String getOrderBy() {
		return orderBy;
	}
//
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
//
	public Integer getPageSize() {
		return pageSize;
	}

	public Criteria<T> setPageSize(Integer pageSize) {
		this.pageSize = pageSize;

		return this;
	}

	public Integer getPageNum() {
		return pageNum;
	} 

	public Criteria<T> setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
		return this;
	}

	private T record;

	private String orderBy;

	private Map<String, Object> params; 

	private Integer pageSize = 30; 

	private Integer pageNum = 0;
	
	
	public String toString() {
		return this.getClass().getName() + " params=" + params + " record=" + record;
	}

}
