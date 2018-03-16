package com.whjn.sysManage.service;


import java.util.List;
import java.util.Map;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.po.SysRole;


public interface SysRoleService extends BaseService<SysRole> {


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


	/** 
	* @Title: getRoleInfo 
	* @Description: 
	* @param @param roleId
	* @param @return  
	* @return List<SysRoleType>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月14日 下午3:06:39 
	* @version V1.0   
	*/
	List<SysRole> getRoleInfo(Integer roleId);


	/** 
	* @Title: delete 
	* @Description: 
	* @param @param entity
	* @param @param ids  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年12月14日 下午5:16:15 
	* @version V1.0   
	 * @param nodeType 
	*/
	void delete(SysRole entity, Long[] ids, Integer nodeType);


	/** 
	* @Title: delRoleByIds 
	* @Description: 
	* @param @param ids  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年12月15日 上午9:37:57 
	* @version V1.0   
	 * @param result 
	*/
	void delRoleByIds(Map<String, Object> result, Long[] ids);


	/** 
	* @Title: getRoleTreeByParentId 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List    
	* @author Chen Cai
	* @throws
	* @date 2017年12月18日 下午4:51:40 
	* @version V1.0   
	*/
	List getRoleTreeByParentId(long parentId);
	
	

}
