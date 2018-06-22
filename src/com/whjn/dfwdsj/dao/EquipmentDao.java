package com.whjn.dfwdsj.dao;

import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.dfwdsj.model.po.Equipment;

public interface EquipmentDao extends BaseDao<Equipment> {

	/** 
	* @Title: getEquipments 
	* @Description: 
	* @param @param firstResult
	* @param @param pageSize
	* @param @param nodeId
	* @param @param fields
	* @param @return  
	* @return QueryResult    
	* @author Chen Cai
	* @throws
	* @date 2018年3月28日 下午3:10:17 
	* @version V1.0   
	*/
	List getEquipments(Integer firstResult, Integer pageSize, long nodeId, String[] fields);

	/** 
	* @Title: delEquipment 
	* @Description: 
	* @param @param ids
	* @param @param typeId
	* @param @return  
	* @return boolean    
	* @author Chen Cai
	* @throws
	* @date 2018年5月29日 下午5:02:43 
	* @version V1.0   
	*/
	boolean delEquipment(long[] ids, long typeId);

	/** 
	* @Title: updateFieldCode 
	* @Description: 
	* @param @param oldFileCode
	* @param @param newFiledCode
	* @param @param nodeId  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年5月30日 上午10:58:17 
	* @version V1.0   
	*/
	void updateFieldCode(String oldFileCode, String newFiledCode, long nodeId);

	/** 
	* @Title: delEquipmentList 
	* @Description: 
	* @param @param string
	* @param @param typeId
	* @param @return  
	* @return boolean    
	* @author Chen Cai
	* @throws
	* @date 2018年5月30日 上午11:47:14 
	* @version V1.0   
	*/
	boolean delEquipmentList(String string, long typeId);

	/** 
	* @Title: getByProerties 
	* @Description: 
	* @param @param propName
	* @param @param propValue
	* @param @param typeId
	* @param @return  
	* @return List<Equipment>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 上午10:44:09 
	* @version V1.0   
	*/
	List<Equipment> getByProerties(String propName, String propValue, long typeId);

	/** 
	* @Title: getOldDate 
	* @Description: 
	* @param @param string
	* @param @param qcId
	* @param @return  
	* @return List<Equipment>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 上午11:00:40 
	* @version V1.0   
	*/
	List<Equipment> getOldDate(String string, long qcId, long typeId);

	/** 
	* @Title: updateEquipment 
	* @Description: 
	* @param @param string
	* @param @param string2
	* @param @param qcId
	* @param @param typeId  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 上午11:15:47 
	* @version V1.0   
	*/
	void updateEquipment(String field, String value, long qcId, long typeId);

	/** 
	* @Title: insertEquipment 
	* @Description: 
	* @param @param id
	* @param @param string
	* @param @param string2
	* @param @param qcId
	* @param @param typeId  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 上午11:23:56 
	* @version V1.0   
	*/
	void insertEquipment(long id, String field, String vaule, long qcId, long typeId);

	/** 
	* @Title: getEquipmentList 
	* @Description: 
	* @param @param qcId
	* @param @return  
	* @return QueryResult<Equipment>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 下午5:08:51 
	* @version V1.0   
	 * @param fields 
	*/
	QueryResult<Equipment> getEquipmentList(String[] fields, long qcId);

	/** 
	* @Title: getEquipmentByValueFieldAndId 
	* @Description: 
	* @param @param field
	* @param @param id
	* @param @return  
	* @return List<Equipment>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月14日 下午8:29:01 
	* @version V1.0   
	*/
	List<Equipment> getEquipmentByValueFieldAndId(String field, long id);

	/** 
	* @Title: getSkdLx 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List    
	* @author Chen Cai
	* @throws
	* @date 2018年6月19日 下午4:47:24 
	* @version V1.0   
	*/
	List getSkdLx(long parentId);

}
