package com.whjn.dfwdsj.service;

import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.dfwdsj.model.po.Equipment;
import com.whjn.dfwdsj.model.po.EquipmentType;
import com.whjn.sysManage.model.po.SysUser;


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
	QueryResult<EquipmentType> getEquipmentTypeList(EquipmentType equipmentType, long nodeId, long orgId);

	/** 
	* @Title: delEquipmentType 
	* @Description: 
	* @param @param ids
	* @param @return  
	* @return boolean    
	* @author Chen Cai
	* @throws
	* @date 2018年5月29日 上午9:42:26 
	* @version V1.0   
	 * @param entity 
	*/
	void delEquipmentType(EquipmentType entity, long ids);

	/** 
	* @Title: insertFieldType 
	* @Description: 
	* @param @param id
	* @param @param name
	* @param @param code
	* @param @param isLeaf
	* @param @param nodeId
	* @param @param user  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月5日 下午12:28:16 
	* @version V1.0   
	*/
	void insertType(long id, String name, String code, short isLeaf, long nodeId, SysUser user);


}
