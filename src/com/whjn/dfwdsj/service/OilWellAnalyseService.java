package com.whjn.dfwdsj.service;

import com.whjn.common.base.QueryResult;
import com.whjn.sysManage.model.po.SysUser;

public interface OilWellAnalyseService {

	/** 
	* @Title: getTotalList 
	* @Description: 
	* @param @param km
	* @param @return  
	* @return QueryResult    
	* @author Chen Cai
	* @throws
	* @date 2018年6月28日 上午11:24:09 
	* @version V1.0   
	 * @param user 
	*/
	QueryResult getTotalList(String[] km, SysUser user);

}
