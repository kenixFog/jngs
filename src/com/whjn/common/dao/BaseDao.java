package com.whjn.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.QueryResult;


public interface BaseDao<E> {

	/**
	* @Title: persist 
	* @Description: 保存实体
	* @param @param entity  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 上午9:04:59 
	* @version V1.0
	 */
	public void persist(E entity);
	
	/**
	* @Title: deleteByPK 
	* @Description: 根据ID删除实体
	* @param @param id主建数组
	* @param @return  
	* @return boolean    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 上午9:12:43 
	* @version V1.0
	 */
	public boolean deleteByPK(Serializable... id);

	/**
	* @Title: delete 
	* @Description: 删除实体类
	* @param @param entity  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 上午11:17:16 
	* @version V1.0
	 */
	public void delete(E entity);

	/**
	* @Title: deleteByProperties 
	* @Description: 根据属性名和属性值删除实体
	* @param @param propName 属性名
	* @param @param propValue 属性值
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 上午11:37:10 
	* @version V1.0
	 */
	public void deleteByProperties(String propName, Object propValue);

	/**
	* @Title: deleteByProperties 
	* @Description: 根据属性名和属性值批量删除实体
	* @param @param propName 属性名数组
	* @param @param propValue 属性值数组
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 上午11:37:10 
	* @version V1.0
	 */
	public void deleteByProperties(String[] propName, Object[] propValue);

	/**
	* @Title: update 
	* @Description: 更新实体（属性有更新的更新，没有的设置为默认值）
	* @param @param entity  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午12:26:47 
	* @version V1.0
	 */
	public void update(E entity);

	/**
	* @Title: updateByProperties 
	** @Description: 根据条件，属性值更新数据
	* @param @param conditionName 条件名
	* @param @param conditionValue 条件值
	* @param @param propertyName 属性名
	* @param @param propertyValue  属性值 
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午12:14:19 
	* @version V1.0
	 */
	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue);

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue);

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue);

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue);

	/**
	* @Title: merge 
	* @Description: 更新实体（与之前实体属性合并，有更新的更新，没有跟新的保留）
	* @param @param entity实体
	* @param @return  
	* @return E    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午12:24:20 
	* @version V1.0
	 */
	public E merge(E entity);

	
	/**
	* @Title: get 
	* @Description: 根据Id获取对象(后台数据层立即执行查询数据库操作)
	* @param @param id
	* @param @return  
	* @return E    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午1:51:12 
	* @version V1.0
	 */
	public E get(Serializable id);

	/**
	* @Title: load 
	* @Description: 根据Id获取对象(a、首先通过id在session缓存中查找对象，如果存在此id的对象，直接将其返回
	*                           b、在二级缓存中查找，找到后将 其返回。
	*                           c、：如果在session缓存和二级缓存中都找不到此对象，则从数据库中加载有此ID的对象)
	* @param @param id
	* @param @return  
	* @return E    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午1:51:42 
	* @version V1.0
	 */
	public E load(Serializable id);

	/**
	* @Title: clear 
	* @Description: 清除Session所有缓存，不包括当前使用的对象
	* @param   
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午2:02:38 
	* @version V1.0
	 */
	public void clear();

	/**
	* @Title: evict 
	* @Description: 清除指定的缓存对象
	* @param @param entity  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午2:04:33 
	* @version V1.0
	 */
	public void evict(E entity);


	/**
	* @Title: getByProerties 
	* @Description: 获取满足条件的唯一对象
	* @param @param propName 属性名
	* @param @param propValue 属性值
	* @param @param sortedCondition 排序条件
	* @param @return  
	* @return E    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午2:15:14 
	* @version V1.0
	 */
	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition);
	
	public E getByProerties(String[] propName, Object[] propValue);

	public E getByProerties(String propName, Object propValue, Map<String, String> sortedCondition);
	
	public E getByProerties(String propName, Object propValue);

	/**
	* @Title: queryByProerties 
	* @Description: 获取满足条件的对象列表
	* @param @param propName 属性名
	* @param @param propValue 属性值
	* @param @param sortedCondition 排序条件
	* @param @param top 获取条目数
	* @param @return  
	* @return List<E>    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午2:31:44 
	* @version V1.0
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
	* @Title: countAll 
	* @Description: 获取数据库条目数
	* @param @return  
	* @return Long    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午2:45:33 
	* @version V1.0
	 */
	public Long countAll();

	/**
	* @Title: doCount 
	* @Description: ===============================================================
	* @param @param parameter
	* @param @return  
	* @return Long    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午2:51:32 
	* @version V1.0
	 */
	public Long doCount(BaseParameter parameter);
	
	/**
	* @Title: doQueryAll 
	* @Description: 根据排序条件查询全部数据列表
	* @param @param sortedCondition 排序条件
	* @param @param top 需要获取的条目数
	* @param @return  
	* @return List<E>    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午2:51:49 
	* @version V1.0
	 */
	public List<E> doQueryAll(Map<String, String> sortedCondition, Integer top);

	public List<E> doQueryAll(Integer top);

	public List<E> doQueryAll();
	
	public List<E> doQuery(BaseParameter parameter);
	
	/**
	* @Title: doPaginationQuery 
	* @Description: 分页查询
	* @param @param parameter 实体对象
	* @param @param bool 标志位
	* @param @return  
	* @return QueryResult<E>    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午3:40:06 
	* @version V1.0
	 */
	public QueryResult<E> doPaginationQuery(BaseParameter parameter, boolean bool);

	public QueryResult<E> doPaginationQuery(BaseParameter parameter);

}
