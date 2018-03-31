package com.whjn.dfwdsj.service;

import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.dfwdsj.model.po.EquipmentType;


public interface EquipmentTypeService extends BaseService<EquipmentType> {

	/** 
	* @Title: getComCodeTreeByParentId 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List<SysComCode>    
	* @author kenix
	* @throws
	* @date 2017年11月20日 下午4:26:00 
	* @version V1.0   
	 * @param orgId 
	*/
	List<EquipmentType> getEquipmentType(long parentId, long orgId);

	/** 
	* @Title: getEquipmentTypeList 
	* @Description: 
	* @param @param equipmentType
	* @param @param nodeId
	* @param @return  
	* @return QueryResult<EquipmentType>    
	* @author Chen Cai
	* @throws
	* @date 2018年3月13日 下午4:48:49 
	* @version V1.0   
	 * @param orgId 
	*/
	QueryResult<EquipmentType> getEquipmentTypeList(EquipmentType equipmentType, Integer nodeId, long orgId);


}
