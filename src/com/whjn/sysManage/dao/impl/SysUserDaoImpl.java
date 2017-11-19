package com.whjn.sysManage.dao.impl;

import org.springframework.stereotype.Repository;

import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.sysManage.dao.SysUserDao;
import com.whjn.sysManage.model.SysUser;


@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDao {

	/**  
	* @Title:  
	* @Description:  
	* @param @param entityClass    
	*/
	public SysUserDaoImpl() {
		super(SysUser.class);
	}


}
