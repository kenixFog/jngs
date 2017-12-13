package com.whjn.sysManage.service;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.po.SysRole;
import com.whjn.sysManage.model.po.SysRoleType;


public interface SysRoleService extends BaseService<SysRole> {

	
	/** 
	* @Title: getRoleTreeByParentId 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List    
	* @author Chen Cai
	* @throws
	* @date 2017年12月13日 下午3:52:15 
	* @version V1.0   
	*/
	List getRoleTreeByParentId(long parentId);

	/** 
	* @Title: getRoleTypeList 
	* @Description: 
	* @param @param sysRoleType
	* @param @param nodeId
	* @param @return  
	* @return QueryResult<SysRoleType>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月13日 下午5:24:11 
	* @version V1.0   
	*/
	QueryResult<SysRoleType> getRoleTypeList(SysRoleType sysRoleType, String nodeId);

	/** 
	* @Title: getRoleList 
	* @Description: 
	* @param @param sysRole
	* @param @param nodeId
	* @param @return  
	* @return QueryResult<SysRole>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月13日 下午5:24:38 
	* @version V1.0   
	*/
	QueryResult<SysRole> getRoleList(SysRole sysRole, String nodeId);
	
	

}
