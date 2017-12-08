package com.whjn.sysManage.service;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.po.SysOrg;
import com.whjn.sysManage.model.po.SysUser;


public interface SysUserService extends BaseService<SysUser> {

	/** 
	* @Title: getUserList 
	* @Description: 
	* @param @param sysUser
	* @param @param nodeId
	* @param @return  
	* @return QueryResult<SysUser_Vo>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月6日 上午9:46:37 
	* @version V1.0   
	*/
	QueryResult<SysUser> getUserList(SysUser sysUser, Integer nodeId);

	/** 
	* @Title: getUserInfo 
	* @Description: 
	* @param @param userId
	* @param @return  
	* @return List<SysUser>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月7日 下午6:07:27 
	* @version V1.0   
	*/
	List<SysUser> getUserInfo(Integer userId);

}
