package com.whjn.dfwdsj.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.dfwdsj.dao.OilWellDetailDao;
import com.whjn.dfwdsj.model.po.OilWellDetail;
import com.whjn.dfwdsj.service.OilWellDetailService;

@Service
public class OilWellDetailServiceImpl extends BaseServiceImpl<OilWellDetail> implements OilWellDetailService {

	@Resource
	private OilWellDetailDao oilWellDetailDao;

	@Resource
	public void setOilWellDetailDao(OilWellDetailDao oilWellDetailDao) {
		this.oilWellDetailDao = oilWellDetailDao;
		this.baseDao = oilWellDetailDao;
	}
	/*
	 * (非 Javadoc)
	 * 
	 * @Title: getOilWellDetailList
	 * 
	 * @Description:
	 * 
	 * @param @param oilWellId
	 * 
	 * @param @return
	 * 
	 * @see com.whjn.dfwdsj.service.OilWellDetailService#getOilWellDetailList(long)
	 */
	@Override
	public QueryResult<OilWellDetail> getOilWellDetailList(OilWellDetail oilWellDetail, long oilWellId) {
		QueryResult<OilWellDetail> oilWellDetailList = oilWellDetailDao.getOilWellDetailList(oilWellDetail, oilWellId);
		return oilWellDetailList;
	}

	/* (非 Javadoc) 
	* @Title: getOilWellDetailInfo
	* @Description:
	* @param @param oilWellDetaiId
	* @param @return 
	* @see com.whjn.dfwdsj.service.OilWellDetailService#getOilWellDetailInfo(long) 
	*/
	@Override
	public List<OilWellDetail> getOilWellDetailInfo(long oilWellDetaiId) {
		return oilWellDetailDao.getOilWellDetailInfo(oilWellDetaiId);
	}
}
