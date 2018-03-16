package com.whjn.sysManage.dao;


import java.util.List;

import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.po.SysRoleMenu;


public interface SysRoleMenuDao extends BaseService<SysRoleMenu> {

	/** 
	* @Title: getRoleMenu 
	* @Description: 
	* @param @param nodeId
	* @param @return  
	* @return List<SysRoleMenu>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月26日 下午3:19:59 
	* @version V1.0   
	*/
	List<SysRoleMenu> getRoleMenu(String roleId);

	/** 
	* @Title: saveAuthorityMenu 
	* @Description: 
	* @param @param roleId
	* @param @param long1  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年12月28日 上午8:56:05 
	* @version V1.0   
	*/
	void saveAuthorityMenu(long roleId, Long long1);


	

}
