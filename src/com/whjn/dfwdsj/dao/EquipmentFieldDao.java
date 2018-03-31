package com.whjn.dfwdsj.dao;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.dfwdsj.model.po.EquipmentField;


public interface EquipmentFieldDao extends BaseDao<EquipmentField> {


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
	* @date 2018年3月27日 下午2:34:52 
	* @version V1.0   
	*/
	List<EquipmentField> getEquipmentFields(int typeId);

}
