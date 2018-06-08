package com.whjn.dfwdsj.service;


import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.dfwdsj.model.po.EquipmentField;


public interface EquipmentFieldService extends BaseService<EquipmentField> {


	/**
	 * 
	* @Title: getEquipmentTypeList 
	* @Description: 
	* @param @param equipmentType
	* @param @param nodeId
	* @param @return  
	* @return QueryResult<EquipmentField>    
	* @author Chen Cai
	* @throws
	* @date 2018年3月20日 上午9:21:57 
	* @version V1.0
	 */
	QueryResult<EquipmentField> getEquipmentFieldList(EquipmentField equipmentField, long nodeId);



	/** 
	* @Title: getEquipmentFields 
	* @Description: 
	* @param @param typeId
	* @param @return  
	* @return List    
	* @author Chen Cai
	* @throws
	* @date 2018年3月27日 下午2:33:35 
	* @version V1.0   
	*/
	List<EquipmentField> getEquipmentFields(long typeId);


	/**
	 * 根据属性获取当前节点下数据
	* @Title: getByProerties 
	* @Description: 
	* @param @param propName
	* @param @param propValue
	* @param @param nodeId
	* @param @return  
	* @return List<EquipmentField>    
	* @author Chen Cai
	* @throws
	* @date 2018年5月25日 下午2:36:26 
	* @version V1.0
	 */
	EquipmentField getByProerties(String propName, Object propValue, long nodeId);



	/** 
	* @Title: delEquipmentField 
	* @Description: 
	* @param @param ids
	* @param @param typeId  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年5月30日 上午10:23:02 
	* @version V1.0   
	*/
	void delEquipmentField(EquipmentField entity,String[] fieldCodes, long typeId);



	/** 
	* @Title: getEquipmentFieldList 
	* @Description: 
	* @param @param i
	* @param @return  
	* @return EquipmentField    
	* @author Chen Cai
	* @throws
	* @date 2018年5月30日 下午2:57:47 
	* @version V1.0   
	*/
	List<EquipmentField> getEquipmentFieldList(long typeId);



	/** 
	* @Title: insertField 
	* @Description: 
	* @param @param string
	* @param @param string2
	* @param @param intValue
	* @param @param string3
	* @param @param id  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 上午11:54:19 
	* @version V1.0   
	*/
	void insertField(String code, String name, int length, String fileType, long typeId,short allowBlank);
}
