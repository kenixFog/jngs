package com.whjn.common.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.dao.DataBaseDao;
import com.whjn.common.service.DataBaseService;

@Service
public class DataBaseServiceImpl implements DataBaseService {

	/** 
	* @Fields DBDao : 通用的dao层接口申明
	*/ 
	@Resource
	protected DataBaseDao dataBaseDao;

	/* (非 Javadoc) 
	* @Title: getId
	* @Description:
	* @param @return 
	* @see com.whjn.common.service.DBService#getId() 
	*/
	@Override
	public long getId() {
		List list =  dataBaseDao.getId();
		dataBaseDao.updateId();
		return ((BigInteger)list.get(0)).longValue();
	}

	
}