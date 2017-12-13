package com.whjn.sysManage.dao;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.sysManage.model.po.SysRole;
import com.whjn.sysManage.model.po.SysRoleType;

public interface SysRoleDao extends BaseDao<SysRole> {

	
	/** 
	* @Title: getRoleTypeByOrgId 
	* @Description: 
	* @param @param currentOrgId
	* @param @return  
	* @return List<SysRoleType>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月13日 下午4:13:21 
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
	* @date 2017年12月13日 下午5:27:11 
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
	* @date 2017年12月13日 下午5:27:30 
	* @version V1.0   
	*/
	QueryResult<SysRole> getRoleList(SysRole sysRole, String nodeId);

	

}
