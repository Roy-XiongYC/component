package com.xiongyc.utils.mybatis;

import java.util.HashMap;
import java.util.Map;

public class Criteria<T extends IModel> {

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
//	public Integer getPageSize() {
//		return pageSize;
//	}
//
//	public Criteria<T> setPageSize(Integer pageSize) {
//		this.pageSize = pageSize;
//
//		return this;
//	}
//
//	public Integer getStartIndex() {
//		return startIndex;
//	} 
//
//	public Criteria<T> setStartIndex(Integer startIndex) {
//		this.startIndex = startIndex;
//
//		return this;
//	}

	private T record;

	private String orderBy;

	private Map<String, Object> params; 

//	private Integer pageSize = 30; 
//
//	private Integer startIndex = 0;
	
	public String toString() {
		return this.getClass().getName() + " params=" + params + " record=" + record;
	}

}
