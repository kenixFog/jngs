package com.whjn.dfwdsj.dao;

import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.dfwdsj.model.po.EquipmentType;
import com.whjn.sysManage.model.po.SysUser;


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
	QueryResult<EquipmentType> getEquipmentTypeList(EquipmentType equipmentType, long nodeId, long orgId);

	/** 
	* @Title: insertType 
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
	* @date 2018年6月5日 下午12:29:25 
	* @version V1.0   
	*/
	void insertType(long id, String name, String code, String isLeaf, long nodeId, SysUser user);

	/** 
	* @Title: getLx 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List<EquipmentType>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月15日 下午3:27:40 
	* @version V1.0   
	 * @param skqId 
	*/
	List getLx(long parentId, String code, long skqId);

	/** 
	* @Title: getId 
	* @Description: 
	* @param @param code
	* @param @return  
	* @return long    
	* @author Chen Cai
	* @throws
	* @date 2018年7月4日 下午4:22:50 
	* @version V1.0   
	*/
	List getId(String code);

}
