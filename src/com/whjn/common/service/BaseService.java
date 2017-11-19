package com.whjn.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.QueryResult;


public interface BaseService<E> {

	public void persist(E entity);

	public boolean deleteByPK(Serializable... id);

	public void delete(E entity);

	public void deleteByProperties(String propName, Object propValue);

	public void deleteByProperties(String[] propName, Object[] propValue);

	public void update(E entity);

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue);

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue);

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue);

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue);

	public void update(E entity, Serializable oldId);

	public E merge(E entity);

	public E get(Serializable id);

	public E load(Serializable id);

	public E getByProerties(String[] propName, Object[] propValue);

	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition);

	public E getByProerties(String propName, Object propValue);

	public E getByProerties(String propName, Object propValue, Map<String, String> sortedCondition);

	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top);

	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition);

	public List<E> queryByProerties(String[] propName, Object[] propValue, Integer top);

	public List<E> queryByProerties(String[] propName, Object[] propValue);

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top);

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition);

	public List<E> queryByProerties(String propName, Object propValue, Integer top);

	public List<E> queryByProerties(String propName, Object propValue);

	public void clear();

	public void evict(E entity);

	public Long countAll();

	public List<E> doQueryAll();

	public List<E> doQueryAll(Map<String, String> sortedCondition, Integer top);

	public List<E> doQueryAll(Integer top);

	public Long doCount(BaseParameter parameter);

	public List<E> doQuery(BaseParameter parameter);

	public QueryResult<E> doPaginationQuery(BaseParameter parameter);

	public QueryResult<E> doPaginationQuery(BaseParameter parameter, boolean bool);
	
	public List<E> queryForPage(String hql, int firstResult, int pagSize);

}
