package com.whjn.sysManage.dao;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.sysManage.model.po.SysRole;
import com.whjn.sysManage.model.po.SysRoleUser;

public interface SysRoleDao extends BaseDao<SysRole> {

	
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

	/** 
	* @Title: getRoleInfo 
	* @Description: 
	* @param @param roleId
	* @param @return  
	* @return List<SysRole>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月14日 下午3:34:20 
	* @version V1.0   
	*/
	List<SysRole> getRoleInfo(Integer roleId);

	/** 
	* @Title: getUserByRoleId 
	* @Description: 
	* @param @param id
	* @param @return  
	* @return List<SysUser>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月15日 上午10:11:32 
	* @version V1.0   
	*/
	List<SysRoleUser> getRoleUserByRoleId(Long id);
	
	
	
	/** 
	* @Title: getUserByRoleId 
	* @Description: 
	* @param @param id
	* @param @return  
	* @return List<SysUser>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月15日 上午10:11:32 
	* @version V1.0   
	*/
	List<SysRole> getRoleByRoleTypeId(Long id);

	

}
