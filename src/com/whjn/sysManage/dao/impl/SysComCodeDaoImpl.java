package com.whjn.sysManage.dao.impl;

import org.springframework.stereotype.Repository;

import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.sysManage.dao.SysComCodeDao;
import com.whjn.sysManage.model.SysComCode;


@Repository
public class SysComCodeDaoImpl extends BaseDaoImpl<SysComCode> implements SysComCodeDao {

	/**  
	* @Title:  
	* @Description:  
	* @param @param entityClass    
	*/
	public SysComCodeDaoImpl() {
		super(SysComCode.class);
	}

}
