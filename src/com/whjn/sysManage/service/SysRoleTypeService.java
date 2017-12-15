package com.whjn.sysManage.service;




import java.util.List;
import java.util.Map;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.po.SysRoleType;


public interface SysRoleTypeService extends BaseService<SysRoleType> {

	
	
	/** 
	* @Title: getRoleTreeByParentId 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List    
	* @author Chen Cai
	* @throws
	* @date 2017年12月14日 下午3:25:11 
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
	* @date 2017年12月14日 下午3:20:54 
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
	* @date 2017年12月14日 下午3:17:23 
	* @version V1.0   
	*/
	List<SysRoleType> getRoleTypeInfo(Integer roleId);


	/** 
	* @Title: delRoleTypeByIds 
	* @Description: 
	* @param @param ids  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年12月15日 上午9:37:52 
	* @version V1.0   
	 * @param result 
	*/
	void delRoleTypeByIds(Map<String, Object> result, Long[] ids);

	

	


}
