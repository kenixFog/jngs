package com.whjn.dfwdsj.dao;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.dfwdsj.model.po.OilWell;
import com.whjn.sysManage.model.po.SysUser;

public interface OilWellDao extends BaseDao<OilWell> {

	/** 
	* @Title: getJkjcsjList 
	* @Description: 
	* @param @param oilWell
	* @param @return  
	* @return QueryResult<OilWell>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月14日 下午8:12:43 
	* @version V1.0   
	 * @param cUser 
	*/
	QueryResult<OilWell> getJkjcsjList(OilWell oilWell, SysUser cUser);

	/** 
	* @Title: getOilWellInfo 
	* @Description: 
	* @param @param id
	* @param @return  
	* @return List<OilWell>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月15日 上午9:15:18 
	* @version V1.0   
	*/
	List<OilWell> getOilWellInfo(long id);


}
