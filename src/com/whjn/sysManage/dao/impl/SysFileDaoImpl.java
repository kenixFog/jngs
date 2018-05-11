package com.whjn.sysManage.dao.impl;

import org.springframework.stereotype.Repository;

import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.sysManage.dao.SysFileDao;
import com.whjn.sysManage.model.po.SysFile;

@Repository
public class SysFileDaoImpl extends BaseDaoImpl<SysFile> implements SysFileDao{

	/**  
	* @Description:  
	* @param @param entityClass    
	*/
	public SysFileDaoImpl() {
		super(SysFile.class);
	}


}
