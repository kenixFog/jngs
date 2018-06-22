package com.whjn.dfwdsj.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.dfwdsj.dao.OilWellDetailDao;
import com.whjn.dfwdsj.model.po.OilWellDetail;
import com.whjn.dfwdsj.service.OilWellDetailService;
import com.whjn.sysManage.model.po.SysMenu;

@Service
public class OilWellDetailServiceImpl extends BaseServiceImpl<OilWellDetail> implements OilWellDetailService {

	@Resource
	private OilWellDetailDao oilWellDetailDao;

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
}
