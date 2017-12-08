package com.whjn.sysManage.dao;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.sysManage.model.po.SysUser;

public interface SysUserDao extends BaseDao<SysUser> {

	/** 
	* @Title: getMenuList 
	* @Description: 
	* @param @param sysUser
	* @param @param nodeId
	* @param @return  
	* @return QueryResult<SysUser_Vo>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月6日 上午9:50:21 
	* @version V1.0   
	*/
	QueryResult<SysUser> getMenuList(SysUser sysUser, Integer nodeId);

	/** 
	* @Title: getUserInfo 
	* @Description: 
	* @param @param userId
	* @param @return  
	* @return List<SysUser>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月7日 下午6:12:58 
	* @version V1.0   
	*/
	List<SysUser> getUserInfo(Integer userId);

}
