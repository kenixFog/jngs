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
	QueryResult<EquipmentField> getEquipmentFieldList(EquipmentField equipmentField, Integer nodeId);



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
	List<EquipmentField> getEquipmentFields(int typeId);


}
