package com.whjn.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.common.util.BeanUtil;

public class BaseDaoImpl<E> implements BaseDao<E> {

	protected final Logger log = Logger.getLogger(BaseDaoImpl.class);

	private static Map<String, Method> MAP_METHOD = new HashMap<String, Method>();

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Resource(name = "sessionFactory")
	public void setSF(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	protected Class<E> entityClass;

	public BaseDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: persist
	 * 
	 * @Description:保存实体
	 * 
	 * @param @param entity
	 * 
	 * @see com.whjn.common.dao.BaseDao#persist(java.lang.Object)
	 */
	public void persist(E entity) {
		getSession().save(entity);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: deleteByPK
	 * 
	 * @Description:根据id删除实体
	 * 
	 * @param @param id主建数组对象
	 * 
	 * @param @return
	 * 
	 * @see com.whjn.common.dao.BaseDao#deleteByPK(java.io.Serializable[])
	 */
	public boolean deleteByPK(Serializable... id) {
		boolean result = false;
		if (id != null && id.length > 0) {
			for (int i = 0; i < id.length; i++) {
				E entity = get(id[i]);
				if (entity != null) {
					getSession().delete(entity);
					result = true;
				}
			}
		}
		return result;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: delete
	 * 
	 * @Description:删除实体类
	 * 
	 * @param @param entity
	 * 
	 * @see com.whjn.common.dao.BaseDao#delete(java.lang.Object)
	 */
	public void delete(E entity) {
		getSession().delete(entity);
	}

	/*
	 * (非 Javadoc) 
	* @Title: deleteByProperties
	* @Description:根据属性名和属性值删除实体
	* @param @param propName 属性名
	* @param @param propValue 属性值
	* @see com.whjn.common.dao.BaseDao#deleteByProperties(java.lang.String, java.lang.Object)
	 */
	public void deleteByProperties(String propName, Object propValue) {
		deleteByProperties(new String[] { propName }, new Object[] { propValue });
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: deleteByProperties
	* @Description:根据属性名和属性值批量删除实体
	* @param @param propName 属性名数组
	* @param @param propValue 属性值数组
	* @see com.whjn.common.dao.BaseDao#deleteByProperties(java.lang.String[], java.lang.Object[])
	 */
	public void deleteByProperties(String[] propName, Object[] propValue) {
		if (propName != null && propName.length > 0 && propValue != null && propValue.length > 0
				&& propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("delete from " + entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			query.executeUpdate();
		}
	}

	/*
	 * (非 Javadoc) 
	* @Title: update
	* @Description:更新实体
	* @param @param entity 实体
	* @see com.whjn.common.service.BaseDao#update(java.lang.Object)
	 */
	public void update(E entity) {
		getSession().update(entity);
	}

	
	/*
	 * (非 Javadoc) 
	* @Title: updateByProperties
	* @Description:根据条件，属性值更新数据
	* @param @param conditionName 条件名
	* @param @param conditionValue 条件值
	* @param @param propertyName 属性名
	* @param @param propertyValue  属性值
	* @see com.whjn.common.dao.BaseDao#updateByProperties(...)
	 */
	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName,
			Object[] propertyValue) {
		if (propertyName != null && propertyName.length > 0 && propertyValue != null && propertyValue.length > 0
				&& propertyName.length == propertyValue.length && conditionValue != null && conditionValue.length > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("update " + entityClass.getName() + " o set ");
			for (int i = 0; i < propertyName.length; i++) {
				sb.append(propertyName[i] + " = :p_" + propertyName[i] + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(" where 1=1 ");
			appendQL(sb, conditionName, conditionValue);
			Query query = getSession().createQuery(sb.toString());
			for (int i = 0; i < propertyName.length; i++) {
				query.setParameter("p_" + propertyName[i], propertyValue[i]);
			}
			setParameter(query, conditionName, conditionValue);
			query.executeUpdate();
		} else {
			throw new IllegalArgumentException("Method updateByProperties in BaseDao argument is illegal!");
		}
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName,
			Object propertyValue) {
		updateByProperties(conditionName, conditionValue, new String[] { propertyName },
				new Object[] { propertyValue });
	}

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName,
			Object[] propertyValue) {
		updateByProperties(new String[] { conditionName }, new Object[] { conditionValue }, propertyName,
				propertyValue);
	}

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName,
			Object propertyValue) {
		updateByProperties(new String[] { conditionName }, new Object[] { conditionValue },
				new String[] { propertyName }, new Object[] { propertyValue });
	}

	/*
	 * (非 Javadoc) 
	* @Title: merge
	* @Description:更新实体（与之前实体属性合并，有更新的更新，没有跟新的保留）
	* @param @param entity 实体
	* @param @return 
	* @see com.whjn.common.dao.BaseDao#merge(java.lang.Object)
	 */
	public E merge(E entity) {
		return (E) getSession().merge(entity);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: get
	* @Description: 根据Id获取对象(后台数据层立即执行查询数据库操作)
	* @param @param id
	* @param @return 
	* @see com.whjn.common.service.BaseDao#get(java.io.Serializable)
	 */
	public E get(Serializable id) {
		return (E) getSession().get(entityClass, id);
	}

	/*
	 * (非 Javadoc) 
	* @Title: load
	* @Description: 根据Id获取对象(a、首先通过id在session缓存中查找对象，如果存在此id的对象，直接将其返回
	*                           b、在二级缓存中查找，找到后将 其返回。
	*                           c、：如果在session缓存和二级缓存中都找不到此对象，则从数据库中加载有此ID的对象)
	* @param @param id
	* @param @return 
	* @see com.whjn.common.service.BaseDao#load(java.io.Serializable)
	 */
	public E load(Serializable id) {
		return (E) getSession().load(entityClass, id);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: clear
	* @Description:清除Session所有缓存，不包括当前使用的对象
	* @param  
	* @see com.whjn.common.dao.BaseDao#clear()
	 */
	public void clear() {
		getSession().clear();
	}

	/*
	 * (非 Javadoc) 
	* @Title: evict
	* @Description:清除指定的缓存对象
	* @param @param entity 
	* @see com.whjn.common.dao.BaseDao#evict(java.lang.Object)
	 */
	public void evict(E entity) {
		getSession().evict(entity);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: getByProerties
	* @Description: 获取满足条件的唯一对象
	* @param @param propName 属性名
	* @param @param propValue 属性值
	* @param @param sortedCondition 排序条件
	* @param @return 
	* @see com.whjn.common.dao.BaseDao#getByProerties(java.lang.String[], java.lang.Object[], java.util.Map)
	 */
	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		if (propName != null && propName.length > 0 && propValue != null && propValue.length > 0
				&& propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("select o from " + entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			if (sortedCondition != null && sortedCondition.size() > 0) {
				sb.append(" order by ");
				for (Entry<String, String> e : sortedCondition.entrySet()) {
					sb.append(e.getKey() + " " + e.getValue() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			List<E> list = query.list();
			if (list != null && list.size() > 0)
				return list.get(0);
		}
		return null;
	}

	public E getByProerties(String[] propName, Object[] propValue) {
		return getByProerties(propName, propValue, null);
	}

	public E getByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return getByProerties(new String[] { propName }, new Object[] { propValue }, sortedCondition);
	}
	
	public E getByProerties(String propName, Object propValue) {
		return getByProerties(new String[] { propName }, new Object[] { propValue });
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
	* @see com.whjn.common.dao.BaseDao#queryByProerties(...)
	 */
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition,
			Integer top) {
		if (propName != null && propValue != null && propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("select o from " + entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			if (sortedCondition != null && sortedCondition.size() > 0) {
				sb.append(" order by ");
				for (Entry<String, String> e : sortedCondition.entrySet()) {
					sb.append(e.getKey() + " " + e.getValue() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			if (top != null) {
				query.setFirstResult(0);
				query.setMaxResults(top);
			}
			return query.list();
		}
		return null;
	}

	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return queryByProerties(propName, propValue, sortedCondition, null);
	}
	
	public List<E> queryByProerties(String[] propName, Object[] propValue, Integer top) {
		return queryByProerties(propName, propValue, null, top);
	}

	public List<E> queryByProerties(String[] propName, Object[] propValue) {
		return queryByProerties(propName, propValue, null, null);
	}
	
	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition,
			Integer top) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, sortedCondition, top);
	}

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, sortedCondition, null);
	}

	public List<E> queryByProerties(String propName, Object propValue, Integer top) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, null, top);
	}

	public List<E> queryByProerties(String propName, Object propValue) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, null, null);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: countAll
	* @Description: 获取数据库数据条目数
	* @param @return 
	* @see com.whjn.common.dao.BaseDao#countAll()
	 */
	public Long countAll() {
		return (Long) getSession().createQuery("select count(*) from " + entityClass.getName()).uniqueResult();
	}

	/*
	 * (非 Javadoc) 
	* @Title: doCount
	* @Description:=====================================================================
	* @param @param param
	* @param @return 
	* @see com.whjn.common.dao.BaseDao#doCount(com.whjn.common.base.BaseParameter)
	 */
	public Long doCount(BaseParameter param) {
		if (param == null)
			return null;
		Criteria criteria = getSession().createCriteria(entityClass);
		processQuery(criteria, param);
		try {
			criteria.setProjection(Projections.rowCount());
			return ((Number) criteria.uniqueResult()).longValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: doQueryAll
	* @Description: 根据排序条件查询全部数据列表
	* @param @param sortedCondition 排序条件
	* @param @param top 需要获取的条目数
	* @param @return 
	* @see com.whjn.common.dao.BaseDao#doQueryAll()
	 */
	public List<E> doQueryAll(Map<String, String> sortedCondition, Integer top) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if (sortedCondition != null && sortedCondition.size() > 0) {
			for (Iterator<String> it = sortedCondition.keySet().iterator(); it.hasNext();) {
				String pm = it.next();
				if (BaseParameter.SORTED_DESC.equals(sortedCondition.get(pm))) {
					criteria.addOrder(Order.desc(pm));
				} else if (BaseParameter.SORTED_ASC.equals(sortedCondition.get(pm))) {
					criteria.addOrder(Order.asc(pm));
				}
			}
		}
		if (top != null) {
			criteria.setMaxResults(top);
			criteria.setFirstResult(0);
		}
		return criteria.list();
	}

	public List<E> doQueryAll(Integer top) {
		return doQueryAll(null, top);
	}
	
	public List<E> doQueryAll() {
		return doQueryAll(null, null);
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: doQuery
	* @Description: 查询实体列表
	* @param @param param 实体
	* @param @return 
	* @see com.whjn.common.dao.BaseDao#doQuery(com.whjn.common.base.BaseParameter)
	 */
	public List<E> doQuery(BaseParameter param) {
		if (param == null)
			return null;
		Criteria criteria = getSession().createCriteria(entityClass);
		processQuery(criteria, param);
		try {
			//增加排序条件
			if (param.getSortedConditions() != null && param.getSortedConditions().size() > 0) {
				Map<String, String> map = param.getSortedConditions();
				for (Iterator<String> it = param.getSortedConditions().keySet().iterator(); it.hasNext();) {
					String pm = it.next();
					if (BaseParameter.SORTED_DESC.equals(map.get(pm))) {
						criteria.addOrder(Order.desc(pm));
					} else if (BaseParameter.SORTED_ASC.equals(map.get(pm))) {
						criteria.addOrder(Order.asc(pm));
					}
				}
			}
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (非 Javadoc) 
	* @Title: doPaginationQuery
	* @Description:
	* @param @param param
	* @param @param bool
	* @param @return 
	* @see com.whjn.common.dao.BaseDao#doPaginationQuery(com.whjn.common.base.BaseParameter, boolean)
	 */
	public QueryResult<E> doPaginationQuery(BaseParameter param, boolean bool) {
		if (param == null)
			return null;
		Criteria criteria = getSession().createCriteria(entityClass);
		if (bool)
			processQuery(criteria, param);
		else
			extendprocessQuery(criteria, param);
		try {
			QueryResult<E> qr = new QueryResult<E>();
			criteria.setProjection(Projections.rowCount());
			qr.setTotalCount(((Number) criteria.uniqueResult()).longValue());
			if (qr.getTotalCount() > 0) {
				if (param.getSortedConditions() != null && param.getSortedConditions().size() > 0) {
					Map<String, String> map = param.getSortedConditions();
					for (Iterator<String> it = param.getSortedConditions().keySet().iterator(); it.hasNext();) {
						String pm = it.next();
						if (BaseParameter.SORTED_DESC.equals(map.get(pm))) {
							criteria.addOrder(Order.desc(pm));
						} else if (BaseParameter.SORTED_ASC.equals(map.get(pm))) {
							criteria.addOrder(Order.asc(pm));
						}
					}
				}
				criteria.setProjection(null);
				criteria.setMaxResults(param.getPageSize());
				criteria.setFirstResult(param.getFirstResult());
				qr.setResultList(criteria.list());
			} else {
				qr.setResultList(new ArrayList<E>());
			}
			return qr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public QueryResult<E> doPaginationQuery(BaseParameter param) {
		return doPaginationQuery(param, true);
	}


	/**
	* @Title: appendQL 
	* @Description: 根据属性名称和属性值组装sql
	* @param @param sb SQL语句
	* @param @param propName 属性名称
	* @param @param propValue  属性值
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 上午11:49:58 
	* @version V1.0
	 */
	private void appendQL(StringBuffer sb, String[] propName, Object[] propValue) {
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object value = propValue[i];
			if (value instanceof Object[] || value instanceof Collection<?>) {
				Object[] arraySerializable = (Object[]) value;
				if (arraySerializable != null && arraySerializable.length > 0) {
					sb.append(" and o." + name + " in (:" + name.replace(".", "") + ")");
				}
			} else {
				if (value == null) {
					sb.append(" and o." + name + " is null ");
				} else {
					sb.append(" and o." + name + "=:" + name.replace(".", ""));
				}
			}
		}
	}

	/**
	* @Title: setParameter 
	* @Description: 设置预编译参数值
	* @param @param query 查询语句
	* @param @param propName 参数名
	* @param @param propValue  参数值
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午12:22:26 
	* @version V1.0
	 */
	private void setParameter(Query query, String[] propName, Object[] propValue) {
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object value = propValue[i];
			if (value != null) {
				if (value instanceof Object[]) {
					query.setParameterList(name.replace(".", ""), (Object[]) value);
				} else if (value instanceof Collection<?>) {
					query.setParameterList(name.replace(".", ""), (Collection<?>) value);
				} else {
					query.setParameter(name.replace(".", ""), value);
				}
			}
		}
	}

	/** ************ for QBC ********** */
	private Method getMethod(String name) {
		if (!MAP_METHOD.containsKey(name)) {
			Class<Restrictions> clazz = Restrictions.class;
			Class[] paramType = new Class[] { String.class, Object.class };
			Class[] likeParamType = new Class[] { String.class, String.class, MatchMode.class };
			Class[] isNullType = new Class[] { String.class };
			try {
				Method method = null;
				if ("like".equals(name)) {
					method = clazz.getMethod(name, likeParamType);
				} else if ("isNull".equals(name)) {
					method = clazz.getMethod(name, isNullType);
				} else {
					method = clazz.getMethod(name, paramType);
				}
				MAP_METHOD.put(name, method);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return MAP_METHOD.get(name);
	}

	private Method getExtendMethod(String name) {
		if (!MAP_METHOD.containsKey(name)) {
			Class<Restrictions> clazz = Restrictions.class;
			Class[] paramType = new Class[] { String.class, Object.class };
			Class[] likeParamType = new Class[] { String.class, String.class, MatchMode.class };
			Class[] isNullType = new Class[] { String.class };
			// Class[] inparamType=new Class[]{String.class,Arrays.class};
			try {
				Method method = null;
				if ("like".equals(name)) {
					method = clazz.getMethod(name, likeParamType);
				} else if ("isNull".equals(name)) {
					method = clazz.getMethod(name, isNullType);
				} else if ("IN".equals(name.toUpperCase())) {
					// method=clazz.getMethod(name,inparamType);
				} else {
					method = clazz.getMethod(name, paramType);
				}
				MAP_METHOD.put(name, method);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return MAP_METHOD.get(name);
	}

	private String getOpt(String value) {
		return (value.substring(0, value.indexOf('_', 1))).substring(1);
	}

	private String getPropName(String value) {
		return value.substring(value.indexOf('_', 1) + 1);
	}

	/**
	* @Title: processQuery 
	* @Description: 处理Criteria根据相应的参数设置相应的查询条件
	* @param @param criteria 容器
	* @param @param param 实体
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午3:04:37 
	* @version V1.0
	 */
	private void processQuery(Criteria criteria, BaseParameter param) {
		try {
			Map<String, Object> staticConditionMap = BeanUtil.describeAvailableParameter(param);
			Map<String, String> dynamicConditionMap = param.getQueryDynamicConditions();
			if ((staticConditionMap != null && staticConditionMap.size() > 0)) {
				for (Entry<String, Object> e : staticConditionMap.entrySet()) {
					Object value = e.getValue();
					if (value != null && !(value instanceof String && "".equals((String) value))) {
						String prop = getPropName(e.getKey());
						String methodName = getOpt(e.getKey());
						Method m = getMethod(methodName);
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class,
									new Object[] { prop, value, MatchMode.ANYWHERE }));
						} else if ("isNull".equals(methodName) && value instanceof Boolean) {
							if ((Boolean) value) {
								criteria.add(Restrictions.isNull(prop));
							} else {
								criteria.add(Restrictions.isNotNull(prop));
							}
						} else {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value }));
						}
					}
				}
			}
			if (dynamicConditionMap != null && dynamicConditionMap.size() > 0) {
				Object bean = entityClass.newInstance();
				Map<String, Object> map = new HashMap<String, Object>();
				for (Entry<String, String> e : dynamicConditionMap.entrySet()) {
					map.put(getPropName(e.getKey()), e.getValue());
				}
				BeanUtils.populate(bean, map);
				for (Entry<String, String> e : dynamicConditionMap.entrySet()) {
					String pn = e.getKey();
					String prop = getPropName(pn);
					String methodName = getOpt(pn);
					Method m = getMethod(methodName);
					Object value = PropertyUtils.getNestedProperty(bean, prop);

					if (value != null && !(value instanceof String && "".equals((String) value))) {
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class,
									new Object[] { prop, value, MatchMode.ANYWHERE }));
						} else if ("isNull".equals(methodName) && value instanceof Boolean) {
							if ((Boolean) value) {
								criteria.add(Restrictions.isNull(prop));
							} else {
								criteria.add(Restrictions.isNotNull(prop));
							}
						} else {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value }));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	* @Title: extendprocessQuery 
	* @Description: 
	* @param @param criteria
	* @param @param param  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午3:43:40 
	* @version V1.0
	 */
	private void extendprocessQuery(Criteria criteria, BaseParameter param) {
		try {
			Map<String, Object> staticConditionMap = BeanUtil.describeAvailableParameter(param);
			Map<String, String> dynamicConditionMap = param.getQueryDynamicConditions();
			if ((staticConditionMap != null && staticConditionMap.size() > 0)) {
				for (Entry<String, Object> e : staticConditionMap.entrySet()) {
					Object value = e.getValue();
					if (value != null && !(value instanceof String && "".equals((String) value))) {
						String prop = getPropName(e.getKey());
						String methodName = getOpt(e.getKey());
						Method m = getExtendMethod(methodName);
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class,
									new Object[] { prop, value, MatchMode.ANYWHERE }));
						} else if ("isNull".equals(methodName) && value instanceof Boolean) {
							if ((Boolean) value) {
								criteria.add(Restrictions.isNull(prop));
							} else {
								criteria.add(Restrictions.isNotNull(prop));
							}
						} else {
							if (value != null && value instanceof Object[] && "IN".equals(methodName.toUpperCase())) {
								Object[] obj = (Object[]) value;
								criteria.add(Restrictions.in(prop, obj));
								// criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop,
								// obj }));
							} else {
								criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value }));
							}
						}
					}
				}
			}

			if (dynamicConditionMap != null && dynamicConditionMap.size() > 0) {
				Object bean = entityClass.newInstance();
				Map<String, Object> map = new HashMap<String, Object>();
				for (Entry<String, String> e : dynamicConditionMap.entrySet()) {
					map.put(getPropName(e.getKey()), e.getValue());
				}
				BeanUtils.populate(bean, map);
				for (Entry<String, String> e : dynamicConditionMap.entrySet()) {
					String pn = e.getKey();
					String prop = getPropName(pn);
					String methodName = getOpt(pn);
					Method m = getMethod(methodName);
					Object value = PropertyUtils.getNestedProperty(bean, prop);

					if (value != null && !(value instanceof String && "".equals((String) value))) {
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class,
									new Object[] { prop, value, MatchMode.ANYWHERE }));
						} else if ("isNull".equals(methodName) && value instanceof Boolean) {
							if ((Boolean) value) {
								criteria.add(Restrictions.isNull(prop));
							} else {
								criteria.add(Restrictions.isNotNull(prop));
							}
						} else {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value }));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
