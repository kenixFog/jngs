package com.whjn.sysManage.dao;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.sysManage.model.po.SysRoleType;

public interface SysRoleTypeDao extends BaseDao<SysRoleType> {

	
	
	
	
	
	
	/** 
	* @Title: getRoleTypeByOrgId 
	* @Description: 
	* @param @param currentOrgId
	* @param @return  
	* @return List<SysRoleType>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月14日 下午3:27:27 
	* @version V1.0   
	*/
	List<SysRoleType> getRoleTypeByOrgId(Long currentOrgId);
	
	

	/** 
	* @Title: getRoleTypeList 
	* @Description: 
	* @param @param sysRoleType
	* @param @param nodeId
	* @param @return  
	* @return QueryResult<SysRoleType>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月14日 下午3:23:32 
	* @version V1.0   
	*/
	QueryResult<SysRoleType> getRoleTypeList(SysRoleType sysRoleType, String nodeId);

	

	/** 
	* @Title: getRoleTypeInfo 
	* @Description: 
	* @param @param roleId
	* @param @return  
	* @return List<SysRoleType>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月14日 下午3:14:13 
	* @version V1.0   
	*/
	List<SysRoleType> getRoleTypeInfo(Integer roleId);

	

}
