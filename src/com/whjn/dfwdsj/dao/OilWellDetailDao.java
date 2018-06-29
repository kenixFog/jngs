package com.whjn.dfwdsj.dao;


import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.BaseDao;
import com.whjn.dfwdsj.model.po.OilWellDetail;

public interface OilWellDetailDao extends BaseDao<OilWellDetail> {

	/** 
	* @Title: getOilWellDetailList 
	* @Description: 
	* @param @param oilWellId
	* @param @return  
	* @return QueryResult<OilWellDetail>    
	* @author Chen Cai
	* @throws
	* @date 2018年6月20日 下午4:28:52 
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
	* @date 2018年6月25日 上午11:39:26 
	* @version V1.0   
	*/
	List<OilWellDetail> getOilWellDetailInfo(long oilWellDetaiId);


}
