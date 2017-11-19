package com.whjn.sysManage.service;

import java.util.List;

import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.SysUser;


public interface SysUserService extends BaseService<SysUser> {

	List<SysUser> getSysUserList(List<SysUser> resultList);

}
