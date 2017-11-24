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



public class BaseServiceImpl<E> implements BaseService<E> {

	/** 
	* @Fields baseDao : 通用的dao层接口申明
	*/ 
	protected BaseDao<E> baseDao;

	/*
	 * (非 Javadoc) 
	* @Title: persist
	* @Description:保存实体
	* @param @param entity 实体
	* @see com.whjn.common.service.BaseService#persist(java.lang.Object)
	 */
	@Transactional
	public void persist(E entity) {
		baseDao.persist(entity);
	}

	/*
	 * (非 Javadoc) 
	* @Title: deleteByPK
	* @Description:根据id删除对象
	* @param @param id主建数组
	* @param @return 
	* @see com.whjn.common.service.BaseService#deleteByPK(java.io.Serializable[])
	 */
	@Transactional
	public boolean deleteByPK(Serializable... id) {
		return baseDao.deleteByPK(id);
	}

	/*
	 * (非 Javadoc) 
	* @Title: delete
	* @Description:删除实体类
	* @param @param entity 实体
	* @see com.whjn.common.service.BaseService#delete(java.lang.Object)
	 */
	@Transactional
	public void delete(E entity) {
		baseDao.delete(entity);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: deleteByProperties
	* @Description:根据属性名和属性值删除实体
	* @param @param propName 属性名
	* @param @param propValue 属性值
	* @see com.whjn.common.service.BaseService#deleteByProperties(java.lang.String, java.lang.Object)
	 */
	@Transactional
	public void deleteByProperties(String propName, Object propValue) {
		baseDao.deleteByProperties(propName, propValue);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: deleteByProperties
	* @Description:根据属性名和属性值批量删除实体
	* @param @param propName 属性名数组
	* @param @param propValue 属性值数组
	* @see com.whjn.common.service.BaseService#deleteByProperties(java.lang.String, java.lang.Object)
	 */
	@Transactional
	public void deleteByProperties(String[] propName, Object[] propValue) {
		baseDao.deleteByProperties(propName, propValue);
	}

	/*
	 * (非 Javadoc) 
	* @Title: update
	* @Description:更新实体
	* @param @param entity 实体
	* @see com.whjn.common.service.BaseService#update(java.lang.Object)
	 */
	@Transactional
	public void update(E entity) {
		baseDao.update(entity);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: updateByProperties
	* @Description:根据条件，属性值更新数据
	* @param @param conditionName 条件名
	* @param @param conditionValue 条件值
	* @param @param propertyName 属性名
	* @param @param propertyValue  属性值
	* @see com.whjn.common.service.BaseService#updateByProperties(...)
	 */
	@Transactional
	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue) {
		baseDao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	@Transactional
	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue) {
		baseDao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	@Transactional
	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue) {
		baseDao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	@Transactional
	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue) {
		baseDao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: merge
	* @Description:更新实体（与之前实体属性合并，有更新的更新，没有跟新的保留）
	* @param @param entity实体
	* @param @return 
	* @see com.whjn.common.service.BaseService#merge(java.lang.Object)
	 */
	@Transactional
	public E merge(E entity) {
		return baseDao.merge(entity);
	}

	/*
	 * (非 Javadoc) 
	* @Title: get
	* @Description: 根据Id获取对象(后台数据层立即执行查询数据库操作)
	* @param @param id
	* @param @return 
	* @see com.whjn.common.service.BaseService#get(java.io.Serializable)
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E get(Serializable id) {
		return baseDao.get(id);
	}

	/*
	 * (非 Javadoc) 
	* @Title: load
	* @Description: 根据Id获取对象(a、首先通过id在session缓存中查找对象，如果存在此id的对象，直接将其返回
	*                           b、在二级缓存中查找，找到后将 其返回。
	*                           c、：如果在session缓存和二级缓存中都找不到此对象，则从数据库中加载有此ID的对象)
	* @param @param id
	* @param @return 
	* @see com.whjn.common.service.BaseService#load(java.io.Serializable)
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E load(Serializable id) {
		return baseDao.load(id);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: clear
	* @Description:清除Session所有缓存，不包括当前使用的对象
	* @param  
	* @see com.whjn.common.service.BaseService#clear()
	 */
	public void clear() {
		baseDao.clear();
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: evict
	* @Description:清除指定的缓存对象
	* @param @param entity 
	* @see com.whjn.common.service.BaseService#evict(java.lang.Object)
	 */
	public void evict(E entity) {
		baseDao.evict(entity);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: getByProerties
	* @Description: 获取满足条件的唯一对象
	* @param @param propName 属性名
	* @param @param propValue 属性值
	* @param @param sortedCondition 排序条件
	* @param @return 
	* @see com.whjn.common.service.BaseService#getByProerties(java.lang.String[], java.lang.Object[], java.util.Map)
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return baseDao.getByProerties(propName, propValue, sortedCondition);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String[] propName, Object[] propValue) {
		return baseDao.getByProerties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return baseDao.getByProerties(propName, propValue, sortedCondition);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String propName, Object propValue) {
		return baseDao.getByProerties(propName, propValue);
	}

	/*
	 * (非 Javadoc) 
	* @Title: queryByProerties
	* @Description: 获取满足条件的对象列表
	* @param @param propName 属性名
	* @param @param propValue 属性值
	* @param @param sortedCondition 排序条件
	* @param @param top 获取条目数
	* @param @return 
	* @see com.whjn.common.service.BaseService#queryByProerties(...)
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top) {
		return baseDao.queryByProerties(propName, propValue, sortedCondition, top);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return baseDao.queryByProerties(propName, propValue, sortedCondition);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue, Integer top) {
		return baseDao.queryByProerties(propName, propValue, top);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue) {
		return baseDao.queryByProerties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top) {
		return baseDao.queryByProerties(propName, propValue, sortedCondition, top);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return baseDao.queryByProerties(propName, propValue, sortedCondition);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue, Integer top) {
		return baseDao.queryByProerties(propName, propValue, top);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue) {
		return baseDao.queryByProerties(propName, propValue);
	}

	/*
	 * (非 Javadoc) 
	* @Title: countAll
	* @Description:获取数据库条目数
	* @param @return 
	* @see com.whjn.common.service.BaseService#countAll()
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Long countAll() {
		return baseDao.countAll();
	}

	/*
	 * (非 Javadoc) 
	* @Title: doCount
	* @Description:===========================================================================
	* @param @param parameter
	* @param @return 
	* @see com.whjn.common.service.BaseService#doCount(com.whjn.common.base.BaseParameter)
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Long doCount(BaseParameter parameter) {
		return baseDao.doCount(parameter);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: doQueryAll
	* @Description: 根据排序条件查询全部数据列表
	* @param @param sortedCondition 排序条件
	* @param @param top 需要获取的条目数
	* @param @return 
	* @see com.whjn.common.service.BaseService#doQueryAll(java.util.Map, java.lang.Integer)
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQueryAll(Map<String, String> sortedCondition, Integer top) {
		return baseDao.doQueryAll(sortedCondition, top);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQueryAll(Integer top) {
		return baseDao.doQueryAll(top);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQueryAll() {
		return baseDao.doQueryAll();
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQuery(BaseParameter parameter) {
		return baseDao.doQuery(parameter);
	}

	/*
	 * (非 Javadoc) 
	* @Title: doPaginationQuery
	* @Description: 分页查询
	* @param @param parameter 实体对象
	* @param @param bool 标志位
	* @param @return 
	* @see com.whjn.common.service.BaseService#doPaginationQuery(...)
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public QueryResult<E> doPaginationQuery(BaseParameter parameter, boolean bool) {
		return baseDao.doPaginationQuery(parameter, bool);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public QueryResult<E> doPaginationQuery(BaseParameter parameter) {
		return baseDao.doPaginationQuery(parameter);
	}
}