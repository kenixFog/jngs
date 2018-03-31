package com.whjn.sysManage.service;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.po.SysOrg;


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
	List getOrgTreeByParentId(long parentId);

	/** 
	* @Title: getOrgList 
	* @Description: 
	* @param @param sysMenu
	* @param @param nodeId
	* @param @return  
	* @return QueryResult<SysMenu>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月4日 下午2:57:47 
	* @version V1.0   
	*/
	QueryResult<SysOrg> getOrgList(SysOrg sysOrg, Integer nodeId);

	/** 
	* @Title: getOrgInfo 
	* @Description: 
	* @param @param orgId
	* @param @return  
	* @return List<SysOrg>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月4日 下午4:01:52 
	* @version V1.0   
	*/
	List<SysOrg> getOrgInfo(Integer orgId);

	/** 
	* @Title: delOrgByIds 
	* @Description: 
	* @param @param entity
	* @param @param ids  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年12月4日 下午4:06:34 
	* @version V1.0   
	*/
	void delOrgByIds(SysOrg entity, Long[] ids);

	/** 
	* @Title: getBaseOrg 
	* @Description: 根据当前组织获取根组织机构
	* @param @param parentId
	* @param @return  
	* @return SysOrg    
	* @author Chen Cai
	* @throws
	* @date 2018年3月21日 上午10:47:25 
	* @version V1.0   
	*/
	SysOrg getBaseOrg(Long parentId);

	
}
