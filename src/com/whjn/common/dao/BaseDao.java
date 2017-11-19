package com.whjn.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.QueryResult;


public interface BaseDao<E> {

	/**
	 * Persist object
	 * 
	 * @param entity
	 */
	public void persist(E entity);

	/**
	 * @param id
	 * @return
	 */
	public boolean deleteByPK(Serializable... id);

	/**
	 * Remove a persistent instance
	 * 
	 * @param entity
	 */
	public void delete(E entity);

	/**
	 * delete entity by property though hql
	 * 
	 * @param propName
	 * @param propValue
	 */
	public void deleteByProperties(String propName, Object propValue);

	/**
	 * delete entity by properties though hql
	 * 
	 * @param propName
	 * @param propValue
	 */
	public void deleteByProperties(String[] propName, Object[] propValue);

	/**
	 * Update the persistent instance with the identifier of the given detached instance.
	 * 
	 * @param entity
	 */
	public void update(E entity);

	/**
	 * update property batch
	 * 
	 * @param conditionName where clause condiction property name
	 * @param conditionValue where clause condiction property value
	 * @param propertyName update clause property name array
	 * @param propertyValue update clase property value array
	 */
	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue);

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue);

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue);

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue);

	/**
	 * cautiously use this method, through delete then insert to update an entity when need to update primary key value (unsupported) use this method
	 * 
	 * @param entity updated entity
	 * @param oldId already existed primary key
	 */
	public void update(E entity, Serializable oldId);

	/**
	 * Merge the state of the given entity into the current persistence context.
	 * 
	 * @param entity
	 */
	public E merge(E entity);

	/**
	 * Get persistent object
	 * 
	 * @param id
	 * @return
	 */
	public E get(Serializable id);

	/**
	 * load persistent object
	 * 
	 * @param id
	 * @return
	 */
	public E load(Serializable id);

	/**
	 * get an entity by properties
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public E getByProerties(String[] propName, Object[] propValue);

	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition);

	/** 
	* @Title: getByProerties 
	* @Description: 根据属性名称以及属性值获取实体对象
	* @param @param propName 属性名称
	* @param @param propValue 属性值
	* @return E    实体对象
	* @author kenix
	* @throws
	* @date 2017年8月18日 上午11:11:19 
	* @version V1.0   
	*/
	public E getByProerties(String propName, Object propValue);

	public E getByProerties(String propName, Object propValue, Map<String, String> sortedCondition);

	/**
	 * query by property
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top);

	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition);

	public List<E> queryByProerties(String[] propName, Object[] propValue, Integer top);

	public List<E> queryByProerties(String[] propName, Object[] propValue);

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top);

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition);

	public List<E> queryByProerties(String propName, Object propValue, Integer top);

	public List<E> queryByProerties(String propName, Object propValue);

	/**
	 * Completely clear the session.
	 */
	public void clear();

	/**
	 * Remove this instance from the session cache.
	 */
	public void evict(E entity);

	/**
	 * count all
	 * 
	 * @return
	 */
	public Long countAll();

	/**
	 * Query all
	 * 
	 * @return
	 */
	public List<E> doQueryAll();

	public List<E> doQueryAll(Map<String, String> sortedCondition, Integer top);

	public List<E> doQueryAll(Integer top);

	public Long doCount(BaseParameter parameter);

	public List<E> doQuery(BaseParameter parameter);

	public QueryResult<E> doPaginationQuery(BaseParameter parameter);

	public QueryResult<E> doPaginationQuery(BaseParameter parameter, boolean bool);

	/** 
	* @Title: queryForPage 
	* @Description: 
	* @param @param hql
	* @param @param firstResult
	* @param @param pagSize
	* @param @return  
	* @return List<E>    
	* @author kenix
	* @throws
	* @date 2017年9月19日 下午5:48:36 
	* @version V1.0   
	*/
	public List<E> queryForPage(String hql, int firstResult, int pagSize);

}
