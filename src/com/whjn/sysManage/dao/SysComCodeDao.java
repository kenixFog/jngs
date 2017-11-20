package com.whjn.sysManage.dao;

import java.util.List;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.sysManage.model.SysComCode;


public interface SysComCodeDao extends BaseDao<SysComCode> {

	/** 
	* @Title: getComCodeTreeByParentId 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List<SysComCode>    
	* @author Chen Cai
	* @throws
	* @date 2017年11月20日 下午4:28:02 
	* @version V1.0   
	*/
	List<SysComCode> getComCodeTreeByParentId(long parentId);

	/** 
	* @Title: getComCodeList 
	* @Description: 
	* @param @param sysComCode
	* @param @param qryName
	* @param @param nodeId
	* @param @return  
	* @return QueryResult    
	* @author kenix
	* @throws
	* @date 2017年9月20日 下午2:53:27 
	* @version V1.0   
	*/
	QueryResult<SysComCode> getComCodeList(BaseParameter param, Integer nodeId);


	/** 
	* @Title: getComCodeInfo 
	* @Description: 获取公共代码信息
	* @param @param ComCodeId
	* @param @return  
	* @return List    
	* @author kenix
	* @throws
	* @date 2017年10月9日 下午2:40:30 
	* @version V1.0   
	*/
	List<SysComCode> getComCodeInfo(Integer ComCodeId);

	/** 
	* @Title: getComCodeList1 
	* @Description: 
	* @param @param param
	* @param @param nodeId
	* @param @return  
	* @return List<SysComCode>    
	* @author kenix
	* @throws
	* @date 2017年11月10日 下午3:10:00 
	* @version V1.0   
	*/
	List<SysComCode> getComCodeList1(BaseParameter param, Integer nodeId);
}
