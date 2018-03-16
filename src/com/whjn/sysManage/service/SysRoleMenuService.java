package com.whjn.sysManage.service;


import java.util.List;
import java.util.Map;

import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.po.SysMenu;
import com.whjn.sysManage.model.po.SysRoleMenu;


public interface SysRoleMenuService extends BaseService<SysRoleMenu> {

	/** 
	* @Title: getAuthorityTree 
	* @Description: 
	* @param @param parentId
	* @param @param nodeId
	* @param @return  
	* @return List<SysMenu>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月26日 下午3:01:00 
	* @version V1.0   
	*/
	List<SysMenu> getAuthorityTree(long parentId, String roleId);

	/** 
	* @Title: saveAuthorityMenu 
	* @Description: 
	* @param @param parseLong
	* @param @param ids  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年12月27日 下午6:21:48 
	* @version V1.0   
	*/
	void saveAuthorityMenu(long parseLong, Long[] ids);

}
