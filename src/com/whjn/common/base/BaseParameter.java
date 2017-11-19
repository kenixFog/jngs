package com.whjn.common.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseParameter extends Constant implements Serializable {

	private static final long serialVersionUID = -2050801753454734869L;

	//返回成功标志
	private Boolean success;
	//返回消息
	private String message;
	//菜单编码
	private String cmd;
	//每页显示条目数
	private Integer pageSize;
	//起始页
	private Integer firstResult;
	//动态查询条件
	private Map<String, Object> queryDynamicConditions = new HashMap<String, Object>(4);
	//排序条件
	private Map<String, String> sortedConditions = new LinkedHashMap<String, String>(2);
	//动态属性
	private Map<String, Object> dynamicProperties = new HashMap<String, Object>(4);

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	/** 
	* @return pageSize 
	*/
	public Integer getPageSize() {
		return pageSize;
	}

	/** 
	* @param pageSize 要设置的 pageSize 
	*/
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/** 
	* @return firstResult 
	*/
	public Integer getFirstResult() {
		return firstResult;
	}

	/** 
	* @param firstResult 要设置的 firstResult 
	*/
	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}
	
	public Map<String, Object> getQueryDynamicConditions() {
		return queryDynamicConditions;
	}

	public void setQueryDynamicConditions(Map<String, Object> queryDynamicConditions) {
		this.queryDynamicConditions = queryDynamicConditions;
	}

	public Map<String, String> getSortedConditions() {
		return sortedConditions;
	}

	public void setSortedConditions(Map<String, String> sortedConditions) {
		this.sortedConditions = sortedConditions;
	}

	public Map<String, Object> getDynamicProperties() {
		return dynamicProperties;
	}

	public void setDynamicProperties(Map<String, Object> dynamicProperties) {
		this.dynamicProperties = dynamicProperties;
	}

}