package com.whjn.dfwdsj.dao;

import java.util.List;

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
	List getEquipments(Integer firstResult, Integer pageSize, Integer nodeId, String[] fields);

}
