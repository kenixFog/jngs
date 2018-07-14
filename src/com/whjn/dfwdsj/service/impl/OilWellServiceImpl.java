package com.whjn.dfwdsj.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.base.QueryResult;
import com.whjn.common.framework.web.WebUtil;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.dfwdsj.dao.OilWellDao;
import com.whjn.dfwdsj.model.po.OilWell;
import com.whjn.dfwdsj.service.OilWellService;
import com.whjn.sysManage.model.po.SysUser;

@Service
public class OilWellServiceImpl extends BaseServiceImpl<OilWell> implements OilWellService {

	@Resource
	private OilWellDao oilWellDao;
	
	@Resource
	public void setOilWellDao(OilWellDao oilWellDao) {
		this.oilWellDao = oilWellDao;
		this.baseDao = oilWellDao;
	}

	/* (非 Javadoc) 
	* @Title: getJkjcsjList
	* @Description:
	* @param @param oilWell
	* @param @return 
	* @see com.whjn.dfwdsj.service.OilWellService#getJkjcsjList(com.whjn.dfwdsj.model.po.OilWell) 
	*/
	@Override
	public QueryResult<OilWell> getJkjcsjList(OilWell oilWell, SysUser cUser) {
		QueryResult<OilWell> oilWellList = oilWellDao.getJkjcsjList(oilWell,cUser);
		return oilWellList;
	}

	/* (非 Javadoc) 
	* @Title: getOilWellInfo
	* @Description:
	* @param @param id
	* @param @return 
	* @see com.whjn.dfwdsj.service.OilWellService#getOilWellInfo(long) 
	*/
	@Override
	public List<OilWell> getOilWellInfo(long id) {
		return oilWellDao.getOilWellInfo(id);
	}
}
