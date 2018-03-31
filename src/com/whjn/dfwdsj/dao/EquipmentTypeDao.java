package com.whjn.dfwdsj.dao;

import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.dfwdsj.model.po.EquipmentType;


public interface EquipmentTypeDao extends BaseDao<EquipmentType> {

	/** 
	* @Title: getComCodeTreeByParentId 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List<SysComCode>    
	* @author Chen Cai
	* @throws
	* @date 2017年11月20日 下午4:28:02 
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
	* @date 2018年3月13日 下午4:50:06 
	* @version V1.0   
	 * @param orgId 
	*/
	QueryResult<EquipmentType> getEquipmentTypeList(EquipmentType equipmentType, Integer nodeId, long orgId);

}
