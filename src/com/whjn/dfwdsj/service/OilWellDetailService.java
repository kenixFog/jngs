package com.whjn.dfwdsj.service;


import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.dfwdsj.model.po.OilWellDetail;


public interface OilWellDetailService extends BaseService<OilWellDetail> {

	/** 
	* @Title: getOilWellDetailList 
	* @Description: 
	* @param @param oilWellId
	* @param @return  
	* @return QueryResult<OilWellDetail>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月20日 下午4:27:57 
	* @version V1.0   
	 * @param oilWellDetail 
	*/
	QueryResult<OilWellDetail> getOilWellDetailList(OilWellDetail oilWellDetail, long oilWellId);

}
