package com.whjn.sysManage.service;


import java.util.List;

import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.SysComCode;
import com.whjn.sysManage.model.SysOrg;


public interface SysOrgService extends BaseService<SysOrg> {

	/** 
	* @Title: getOrgTreeByParentId 
	* @Description: 获取基准组织树
	* @param @param parentId
	* @param @return  
	* @return List<SysComCode>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月1日 下午4:39:40 
	* @version V1.0   
	*/
	List<SysOrg> getOrgTreeByParentId(long parentId);

	
}
