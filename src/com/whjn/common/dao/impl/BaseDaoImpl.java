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
import com.whjn.common.util.PageUtil;


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

	public void persist(E entity) {
		getSession().save(entity);
	}

	
	
	
	
	
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

	public void deleteByProperties(String[] propName, Object[] propValue) {
		if (propName != null && propName.length > 0 && propValue != null && propValue.length > 0 && propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("delete from " + entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			query.executeUpdate();
		}
	}

	public void delete(E entity) {
		getSession().delete(entity);
	}

	public void deleteByProperties(String propName, Object propValue) {
		deleteByProperties(new String[] { propName }, new Object[] { propValue });
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue) {
		if (propertyName != null && propertyName.length > 0 && propertyValue != null && propertyValue.length > 0 && propertyName.length == propertyValue.length && conditionValue != null && conditionValue.length > 0) {
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

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue) {
		updateByProperties(conditionName, conditionValue, new String[] { propertyName }, new Object[] { propertyValue });
	}

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue) {
		updateByProperties(new String[] { conditionName }, new Object[] { conditionValue }, propertyName, propertyValue);
	}

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue) {
		updateByProperties(new String[] { conditionName }, new Object[] { conditionValue }, new String[] { propertyName }, new Object[] { propertyValue });
	}

	public void update(E entity) {
		getSession().update(entity);
	}

	public void update(E entity, Serializable oldId) {
		deleteByPK(oldId);
		persist(entity);
	}

	public E merge(E entity) {
		return (E) getSession().merge(entity);
	}

	/** 
	* @Title: getByProerties 
	* @Description: 根据属性名称以及属性值获取实体对象
	* @param @param propName[] 属性名称数组
	* @param @param propValue[] 属性值数组
	* @param @param sortedCondition 排序条件
	* @return E    实体对象
	* @author kenix
	* @throws
	* @date 2017年8月18日 上午11:11:19 
	* @version V1.0   
	*/
	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		if (propName != null && propName.length > 0 && propValue != null && propValue.length > 0 && propValue.length == propName.length) {
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

	public E get(Serializable id) {
		return (E) getSession().get(entityClass, id);
	}

	public E load(Serializable id) {
		return (E) getSession().load(entityClass, id);
	}

	/** 
	* @Title: getByProerties 
	* @Description: 根据属性名称以及属性值获取实体对象
	* @param @param propName[] 属性名称数组
	* @param @param propValue[] 属性值数组
	* @return E    实体对象
	* @author kenix
	* @throws
	* @date 2017年8月18日 上午11:11:19 
	* @version V1.0   
	*/
	public E getByProerties(String[] propName, Object[] propValue) {
		return getByProerties(propName, propValue, null);
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
	public E getByProerties(String propName, Object propValue) {
		return getByProerties(new String[] { propName }, new Object[] { propValue });
	}

	public E getByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return getByProerties(new String[] { propName }, new Object[] { propValue }, sortedCondition);
	}

	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top) {
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

	public List<E> queryByProerties(String[] propName, Object[] propValue, Integer top) {
		return queryByProerties(propName, propValue, null, top);
	}

	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return queryByProerties(propName, propValue, sortedCondition, null);
	}

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, sortedCondition, top);
	}

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, sortedCondition, null);
	}

	public List<E> queryByProerties(String propName, Object propValue, Integer top) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, null, top);
	}

	public List<E> queryByProerties(String[] propName, Object[] propValue) {
		return queryByProerties(propName, propValue, null, null);
	}

	public List<E> queryByProerties(String propName, Object propValue) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, null, null);
	}

	public Long countAll() {
		return (Long) getSession().createQuery("select count(*) from " + entityClass.getName()).uniqueResult();
	}

	public void clear() {
		getSession().clear();
	}

	public void evict(E entity) {
		getSession().evict(entity);
	}

	
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

	public List<E> doQueryAll() {
		return doQueryAll(null, null);
	}

	public List<E> doQueryAll(Integer top) {
		return doQueryAll(null, top);
	}

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

	public List<E> doQuery(BaseParameter param) {
		if (param == null)
			return null;
		Criteria criteria = getSession().createCriteria(entityClass);
		processQuery(criteria, param);
		try {
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

	public QueryResult<E> doPaginationQuery(BaseParameter param) {
		return doPaginationQuery(param, true);
	}

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

	protected void buildSorted(BaseParameter param, StringBuffer hql) {
		if (param.getSortedConditions() != null && param.getSortedConditions().size() > 0) {
			hql.append(" order by ");
			Map<String, String> sorted = param.getSortedConditions();
			for (Iterator<String> it = sorted.keySet().iterator(); it.hasNext();) {
				String col = it.next();
				hql.append(col + " " + sorted.get(col) + ",");
			}
			hql.deleteCharAt(hql.length() - 1);
		}
	}

	private String transferColumn(String queryCondition) {
		return queryCondition.substring(queryCondition.indexOf('_', 1) + 1);
	}

	protected void setParameter(Map<String, Object> mapParameter, Query query) {
		for (Iterator<String> it = mapParameter.keySet().iterator(); it.hasNext();) {
			String parameterName = (String) it.next();
			Object value = mapParameter.get(parameterName);
			query.setParameter(parameterName, value);
		}
	}

	protected Map handlerConditions(BaseParameter param) throws Exception {
		Map staticConditions = BeanUtil.describe(param);
		Map<String, Object> dynamicConditions = param.getQueryDynamicConditions();
		if (dynamicConditions.size() > 0) {
			for (Iterator it = staticConditions.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				Object value = staticConditions.get(key);
				if (key.startsWith("$") && value != null && !"".equals(value)) {
					dynamicConditions.put(key, value);
				}
			}
			staticConditions = dynamicConditions;
		}
		return staticConditions;
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

	private void processQuery(Criteria criteria, BaseParameter param) {
		try {
			Map<String, Object> staticConditionMap = BeanUtil.describeAvailableParameter(param);
			Map<String, Object> dynamicConditionMap = param.getQueryDynamicConditions();
			if ((staticConditionMap != null && staticConditionMap.size() > 0)) {
				for (Entry<String, Object> e : staticConditionMap.entrySet()) {
					Object value = e.getValue();
					if (value != null && !(value instanceof String && "".equals((String) value))) {
						String prop = getPropName(e.getKey());
						String methodName = getOpt(e.getKey());
						Method m = getMethod(methodName);
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value, MatchMode.ANYWHERE }));
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
				for (Entry<String, Object> e : dynamicConditionMap.entrySet()) {
					map.put(getPropName(e.getKey()), e.getValue());
				}
				BeanUtils.populate(bean, map);
				for (Entry<String, Object> e : dynamicConditionMap.entrySet()) {
					String pn = e.getKey();
					String prop = getPropName(pn);
					String methodName = getOpt(pn);
					Method m = getMethod(methodName);
					Object value = PropertyUtils.getNestedProperty(bean, prop);

					if (value != null && !(value instanceof String && "".equals((String) value))) {
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value, MatchMode.ANYWHERE }));
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

	private void extendprocessQuery(Criteria criteria, BaseParameter param) {
		try {
			Map<String, Object> staticConditionMap = BeanUtil.describeAvailableParameter(param);
			Map<String, Object> dynamicConditionMap = param.getQueryDynamicConditions();
			if ((staticConditionMap != null && staticConditionMap.size() > 0)) {
				for (Entry<String, Object> e : staticConditionMap.entrySet()) {
					Object value = e.getValue();
					if (value != null && !(value instanceof String && "".equals((String) value))) {
						String prop = getPropName(e.getKey());
						String methodName = getOpt(e.getKey());
						Method m = getExtendMethod(methodName);
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value, MatchMode.ANYWHERE }));
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
								// criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, obj }));
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
				for (Entry<String, Object> e : dynamicConditionMap.entrySet()) {
					map.put(getPropName(e.getKey()), e.getValue());
				}
				BeanUtils.populate(bean, map);
				for (Entry<String, Object> e : dynamicConditionMap.entrySet()) {
					String pn = e.getKey();
					String prop = getPropName(pn);
					String methodName = getOpt(pn);
					Method m = getMethod(methodName);
					Object value = PropertyUtils.getNestedProperty(bean, prop);

					if (value != null && !(value instanceof String && "".equals((String) value))) {
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value, MatchMode.ANYWHERE }));
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
     * 根据SQL分页查询
     * 
     * @param hql
     * @param pageUtil
     */
    public void getListByPage(String hql, PageUtil<E> pageUtil) {
        if (null == hql) {
            return;
        }
        try {
            Query query = getSession().createQuery(hql);
            query.setFirstResult(pageUtil.getFirstRec());
            query.setMaxResults(pageUtil.getPageSize());
            pageUtil.setList(query.list());
 
            String queryString = "";
            if (hql.toUpperCase().indexOf("SELECT") != -1) {
                int i = query.getQueryString().toUpperCase().indexOf("FROM");
                queryString = "Select count(*) "
                        + hql.substring(i, hql.length());
            } else {
                queryString = "Select count(*) " + hql;
            }
            // 去掉ORDER BY 的部分
            int j = queryString.toUpperCase().lastIndexOf("ORDER");
            if (j != -1) {
                queryString = queryString.substring(0, j);
            }
            Query cquery = getSession().createQuery(queryString);
            cquery.setCacheable(true);
            int recTotal = ((Integer) cquery.iterate().next()).intValue();
            pageUtil.setRecTotal(recTotal);
        } finally {
            if (getSession() != null) {
            	getSession().close();
            }
        }
    }

	@Override
	public List<E> queryForPage(String hql, int firstResult, int pagSize) {
		 Query q = getSession().createQuery(hql);
		 q.setFirstResult(firstResult);
		 q.setMaxResults(pagSize);
		 return q.list();
	}
	
	//获取总记录数
	public int getCount(String hql) {
		Query q = getSession().createQuery(hql);
		return Integer.parseInt(q.list().get(0).toString());
	}

}
