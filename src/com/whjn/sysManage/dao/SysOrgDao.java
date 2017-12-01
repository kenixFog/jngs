package com.whjn.sysManage.dao;


import java.util.List;

import com.whjn.common.dao.BaseDao;
import com.whjn.sysManage.model.SysOrg;


public interface SysOrgDao extends BaseDao<SysOrg> {

	/** 
	* @Title: getOrgTreeByParentId 
	* @Description: 获取基准组织树
	* @param @param parentId
	* @param @return  
	* @return List<SysOrg>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月1日 下午4:46:40 
	* @version V1.0   
	*/
	List<SysOrg> getOrgTreeByParentId(long parentId);


}
