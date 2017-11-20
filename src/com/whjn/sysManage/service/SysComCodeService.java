package com.whjn.sysManage.service;

import java.util.List;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.SysComCode;


public interface SysComCodeService extends BaseService<SysComCode> {

	/** 
	* @Title: getComCodeTreeByParentId 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List<SysComCode>    
	* @author kenix
	* @throws
	* @date 2017年11月20日 下午4:26:00 
	* @version V1.0   
	*/
	List<SysComCode> getComCodeTreeByParentId(long parentId);

	/** 
	* @Title: getSysComCodeList 
	* @Description: 获取公共代码列表
	* @param param 实体类
	* @param nodeId 树节点ID
	* @param @return  
	* @author kenix
	* @throws
	* @date 2017年9月15日 下午5:40:54 
	* @version V1.0   
	*/
	QueryResult getComCodeList(BaseParameter param, Integer nodeId);


	/** 
	* @Title: getComCodeInfo 
	* @Description: 获取公共代码信息
	* @param @param ComCodeId
	* @param @return  
	* @return List    
	* @author kenix
	* @throws
	* @date 2017年10月9日 下午2:36:25 
	* @version V1.0   
	*/
	List<SysComCode> getComCodeInfo(Integer ComCodeId);

	/** 
	* @Title: getList 
	* @Description: 
	* @param @param resultList
	* @param @return  
	* @return List<SysComCode>    
	* @author kenix
	* @throws
	* @date 2017年11月10日 上午9:55:23 
	* @version V1.0   
	*/
	List<SysComCode> getList(List<SysComCode> resultList);

	/** 
	* @Title: getComCodeList1 
	* @Description: 
	* @param @param param
	* @param @param nodeId
	* @param @return  
	* @return List<SysComCode>    
	* @author kenix
	* @throws
	* @date 2017年11月10日 下午3:11:19 
	* @version V1.0   
	*/
	List<SysComCode> getComCodeList1(BaseParameter param, Integer nodeId);
}
