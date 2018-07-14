package com.whjn.dfwdsj.service;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.dfwdsj.model.po.OilWell;
import com.whjn.sysManage.model.po.SysUser;


public interface OilWellService extends BaseService<OilWell> {

	/** 
	* @Title: getJkjcsjList 
	* @Description: 
	* @param @param oilWell
	* @param @return  
	* @return QueryResult<OilWell>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月14日 下午8:11:36 
	* @version V1.0   
	*/
	QueryResult<OilWell> getJkjcsjList(OilWell oilWell,SysUser cUser);

	/** 
	* @Title: getOilWellInfo 
	* @Description: 
	* @param @param id
	* @param @return  
	* @return List<OilWell>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月15日 上午9:14:33 
	* @version V1.0   
	*/
	List<OilWell> getOilWellInfo(long id);

}
