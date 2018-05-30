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
	boolean delEquipment(Long[] ids, int typeId);

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
	void updateFieldCode(String oldFileCode, String newFiledCode, int nodeId);

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
	boolean delEquipmentList(String string, int typeId);

}
