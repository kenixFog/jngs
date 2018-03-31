package com.whjn.sysManage.dao;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.sysManage.model.po.SysOrg;


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

	/** 
	* @Title: getMenuList 
	* @Description: 
	* @param @param sysOrg
	* @param @param nodeId
	* @param @return  
	* @return QueryResult<SysOrg>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月4日 下午2:59:45 
	* @version V1.0   
	*/
	QueryResult<SysOrg> getMenuList(SysOrg sysOrg, Integer nodeId);

	/** 
	* @Title: getOrgInfo 
	* @Description: 
	* @param @param orgId
	* @param @return  
	* @return List<SysOrg>    
	* @author Chen Cai
	* @throws
	* @date 2017年12月4日 下午4:02:26 
	* @version V1.0   
	*/
	List<SysOrg> getOrgInfo(Integer orgId);

	/** 
	* @Title: getBaseOrg 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return SysOrg    
	* @author Chen Cai
	* @throws
	* @date 2018年3月21日 上午10:49:17 
	* @version V1.0   
	*/
	SysOrg getBaseOrg(Long parentId);


}
