package com.whjn.common.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.common.service.BaseService;

/** 
* @ClassName: BaseService 
* @Description: 通用的service层服务 
* @author chenc 
* @date 2017年8月18日 上午11:01:41 
* @version V1.0 
*/
@Transactional
public class BaseServiceImpl<E> implements BaseService<E> {

	/** 
	* @Fields baseDao : 通用的dao层接口申明
	*/ 
	protected BaseDao<E> baseDao;

	public void persist(E entity) {
		baseDao.persist(entity);
	}

	public boolean deleteByPK(Serializable... id) {
		return baseDao.deleteByPK(id);
	}

	public void delete(E entity) {
		baseDao.delete(entity);
	}

	public void deleteByProperties(String[] propName, Object[] propValue) {
		baseDao.deleteByProperties(propName, propValue);
	}

	public void deleteByProperties(String propName, Object propValue) {
		baseDao.deleteByProperties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E get(Serializable id) {
		return baseDao.get(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String[] propName, Object[] propValue) {
		return baseDao.getByProerties(propName, propValue);
	}

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
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String propName, Object propValue) {
		return baseDao.getByProerties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return baseDao.getByProerties(propName, propValue, sortedCondition);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return baseDao.getByProerties(propName, propValue, sortedCondition);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E load(Serializable id) {
		return baseDao.load(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue) {
		return baseDao.queryByProerties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue) {
		return baseDao.queryByProerties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return baseDao.queryByProerties(propName, propValue, sortedCondition);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return baseDao.queryByProerties(propName, propValue, sortedCondition);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top) {
		return baseDao.queryByProerties(propName, propValue, sortedCondition, top);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue, Integer top) {
		return baseDao.queryByProerties(propName, propValue, top);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top) {
		return baseDao.queryByProerties(propName, propValue, sortedCondition, top);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue, Integer top) {
		return baseDao.queryByProerties(propName, propValue, top);
	}

	public E merge(E entity) {
		return baseDao.merge(entity);
	}

	public void update(E entity) {
		baseDao.update(entity);
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue) {
		baseDao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue) {
		baseDao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue) {
		baseDao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue) {
		baseDao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void update(E entity, Serializable oldId) {
		baseDao.update(entity, oldId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQueryAll() {
		return baseDao.doQueryAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQueryAll(Map<String, String> sortedCondition, Integer top) {
		return baseDao.doQueryAll(sortedCondition, top);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQueryAll(Integer top) {
		return baseDao.doQueryAll(top);
	}

	public void evict(E entity) {
		baseDao.evict(entity);
	}

	public void clear() {
		baseDao.clear();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Long countAll() {
		return baseDao.countAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Long doCount(BaseParameter parameter) {
		return baseDao.doCount(parameter);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQuery(BaseParameter parameter) {
		return baseDao.doQuery(parameter);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public QueryResult<E> doPaginationQuery(BaseParameter parameter) {
		return baseDao.doPaginationQuery(parameter);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public QueryResult<E> doPaginationQuery(BaseParameter parameter, boolean bool) {
		return baseDao.doPaginationQuery(parameter, bool);
	}

	@Override
	public List<E> queryForPage(String hql, int firstResult, int pagSize) {
		return baseDao.queryForPage(hql,firstResult,pagSize);
	}

}