package com.whjn.dfwdsj.service;

import java.util.List;

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
	List getEquipments(Integer firstResult, Integer pageSize, Integer nodeId, String[] fields);

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
	* @date 2018年5月30日 上午10:56:57 
	* @version V1.0   
	*/
	void updateFieldCode(String oldFileCode, String newFiledCode, int nodeId);


}
