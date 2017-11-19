package com.whjn.sysManage.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.sysManage.dao.SysComCodeDao;
import com.whjn.sysManage.model.SysComCode;
import com.whjn.sysManage.service.SysComCodeService;


@Service
public class SysComCodeServiceImpl extends BaseServiceImpl<SysComCode> implements SysComCodeService {

	private SysComCodeDao sysComCodeDao;

	@Resource
	public void setSysMenuDao(SysComCodeDao sysComCodeDao) {
		this.sysComCodeDao = sysComCodeDao;
		this.baseDao = sysComCodeDao;
	}
}
