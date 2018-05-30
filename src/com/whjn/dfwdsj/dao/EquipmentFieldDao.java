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

	/** 
	* @Title: getByProerties 
	* @Description: 
	* @param @param propName
	* @param @param propValue
	* @param @param nodeId
	* @param @return  
	* @return List<EquipmentField>    
	* @author Chen Cai
	* @throws
	* @date 2018年5月25日 下午2:39:36 
	* @version V1.0   
	*/
	List<EquipmentField> getByProerties(String propName, Object propValue, int nodeId);

	/** 
	* @Title: delEquipmentFieldList 
	* @Description: 
	* @param @param string
	* @param @param typeId
	* @param @return  
	* @return boolean    
	* @author Chen Cai
	* @throws
	* @date 2018年5月30日 上午11:47:18 
	* @version V1.0   
	*/
	boolean delEquipmentFieldList(String string, int typeId);

	/** 
	* @Title: getEquipmentFieldList 
	* @Description: 
	* @param @param typeId
	* @param @return  
	* @return List<EquipmentField>    
	* @author Chen Cai
	* @throws
	* @date 2018年5月30日 下午2:59:38 
	* @version V1.0   
	*/
	List<EquipmentField> getEquipmentFieldList(int typeId);

}
