package com.whjn.sysManage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.sysManage.dao.SysUserDao;
import com.whjn.sysManage.model.po.SysOrg;
import com.whjn.sysManage.model.po.SysUser;
import com.whjn.sysManage.service.SysUserService;


@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

	private SysUserDao sysUserDao;

	@Resource
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
		this.baseDao = sysUserDao;
	}

	/* (非 Javadoc) 
	* @Title: getUserList
	* @Description:
	* @param @param sysUser
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.service.SysUserService#getUserList(com.whjn.sysManage.model.vo.SysUser_Vo, java.lang.Integer) 
	*/
	@Override
	public QueryResult<SysUser> getUserList(SysUser sysUser, Integer nodeId) {
		QueryResult<SysUser> menuList = sysUserDao.getMenuList(sysUser, nodeId);
		return menuList;
	}

	/* (非 Javadoc) 
	* @Title: getUserInfo
	* @Description:
	* @param @param userId
	* @param @return 
	* @see com.whjn.sysManage.service.SysUserService#getUserInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysUser> getUserInfo(Integer userId) {
		return sysUserDao.getUserInfo(userId);
	}

}
