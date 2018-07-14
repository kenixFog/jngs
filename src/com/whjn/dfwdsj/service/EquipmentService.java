package com.whjn.dfwdsj.service;

import java.util.List;
import java.util.Map;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.dfwdsj.model.po.Equipment;

public interface EquipmentService extends BaseService<Equipment>{

	/** 
	* @Title: getEquipmentFieldList 
	* @Description: 
	* @param @param firstResult FirstResult
	* @param @param pageSize MaxResults
	* @param @param nodeId 当前节点id typeId
	* @param @param fields 属性字段
	* @param @return  
	* @return QueryResult    
	* @author Chen Cai
	* @throws
	* @date 2018年3月28日 下午3:08:10 
	* @version V1.0   
	*/
	QueryResult getEquipments(Integer firstResult, Integer pageSize, long nodeId, String[] fields);

	/** 
	* @Title: delEquipment 
	* @Description: 
	* @param @param ids
	* @param @return  
	* @return boolean    
	* @author Chen Cai
	* @throws
	* @date 2018年5月29日 下午2:53:06 
	* @version V1.0   
	 * @param typeId 
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
	* @date 2018年5月30日 上午10:56:57 
	* @version V1.0   
	*/
	void updateFieldCode(String oldFileCode, String newFiledCode, long nodeId);

	/** 
	* @Title: insertEquipment 
	* @Description: 
	* @param @param fields
	* @param @param paramMap
	* @param @param typeId  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 上午10:27:06 
	* @version V1.0   
	*/
	long insertEquipment(String[] fields, Map<String, String> paramMap, long typeId);

	/** 
	* @Title: getByProerties 
	* @Description: 
	* @param @param propName
	* @param @param propValue
	* @param @param typeId
	* @param @return  
	* @return Equipment    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 上午10:43:23 
	* @version V1.0   
	*/
	Equipment getByProerties(String propName, String propValue, long typeId);

	/** 
	* @Title: getOldDate 
	* @Description: 
	* @param @param string
	* @param @param id
	* @param @return  
	* @return Equipment    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 上午10:59:34 
	* @version V1.0   
	*/
	Equipment getOldDate(String string, long qcId, long typeId);

	/** 
	* @Title: updateEquipment 
	* @Description: 
	* @param @param fields
	* @param @param paramMap
	* @param @param typeId  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 上午11:11:11 
	* @version V1.0   
	 * @param qcId 
	*/
	void updateEquipment(String[] fields, Map<String, String> paramMap, long qcId, long typeId);

	/** 
	* @Title: getEquipmentList 
	* @Description: 
	* @param @param equipment
	* @param @param qcId
	* @param @return  
	* @return QueryResult<Equipment>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 下午5:07:29 
	* @version V1.0   
	 * @param fields 
	*/
	QueryResult<Equipment> getEquipmentList(String[] fields, long qcId);

	/** 
	* @Title: getEquipmentByValueFieldAndId 
	* @Description: 
	* @param @param string
	* @param @param id
	* @param @return  
	* @return long    
	* @author Chen Cai
	* @throws
	* @date 2018年6月14日 下午8:26:55 
	* @version V1.0   
	*/
	long getEquipmentByValueFieldAndId(String string, long id);

	/** 
	* @Title: getSkd 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List    
	* @author Chen Cai
	* @throws
	* @date 2018年6月15日 下午4:52:20 
	* @version V1.0   
	*/
	List getSkd(long parentId,String code);


}
