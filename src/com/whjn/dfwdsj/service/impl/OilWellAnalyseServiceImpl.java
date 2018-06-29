package com.whjn.dfwdsj.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.base.QueryResult;
import com.whjn.dfwdsj.dao.OilWellAnalyseDao;
import com.whjn.dfwdsj.service.OilWellAnalyseService;
import com.whjn.sysManage.model.po.SysUser;

@Service
public class OilWellAnalyseServiceImpl implements OilWellAnalyseService {
	
	@Resource
	private OilWellAnalyseDao oilWellAnalyseDao;
	
	/* (非 Javadoc) 
	* @Title: getTotalList
	* @Description:
	* @param @param km
	* @param @return 
	* 
	* @see com.whjn.dfwdsj.service.OilWellAnalyseService#getTotalList(java.lang.String[]) 
	*/
	@Override
	public QueryResult getTotalList(String[] km, SysUser user) {
		
		
		
		QueryResult totalList = oilWellAnalyseDao.getTotalList(km);
		return totalList;
	}

}
