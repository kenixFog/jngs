package com.whjn.dfwdsj.service;


import java.util.List;

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

	/** 
	* @Title: getOilWellDetailInfo 
	* @Description: 
	* @param @param oilWellDetaiId
	* @param @return  
	* @return List<OilWellDetail>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月25日 上午11:38:53 
	* @version V1.0   
	*/
	List<OilWellDetail> getOilWellDetailInfo(long oilWellDetaiId);

}
