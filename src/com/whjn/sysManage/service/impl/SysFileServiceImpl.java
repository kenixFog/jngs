package com.whjn.sysManage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.sysManage.dao.SysFileDao;
import com.whjn.sysManage.model.po.SysFile;
import com.whjn.sysManage.service.SysFileService;

@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFile> implements SysFileService{

	
	private SysFileDao sysFileDao;
	
	@Resource
	public void setSysFileDao(SysFileDao sysFileDao) {
		this.sysFileDao = sysFileDao;
		this.baseDao = sysFileDao;
	}
}
