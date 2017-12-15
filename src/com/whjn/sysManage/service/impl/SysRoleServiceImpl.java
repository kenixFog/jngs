package com.whjn.sysManage.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.sysManage.dao.SysOrgDao;
import com.whjn.sysManage.dao.SysRoleDao;
import com.whjn.sysManage.model.po.SysOrg;
import com.whjn.sysManage.model.po.SysRole;
import com.whjn.sysManage.model.po.SysRoleType;
import com.whjn.sysManage.model.po.SysRoleUser;
import com.whjn.sysManage.model.po.SysUser;
import com.whjn.sysManage.service.SysRoleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
	
	@Resource
	private SysRoleDao sysRoleDao;

	
//	@Resource
//	public void setSysRoleDao(SysRoleDao sysRoleDao) {
//		this.sysRoleDao = sysRoleDao;
//		this.baseDao = sysRoleDao;
//	}

	/* (非 Javadoc) 
	* @Title: getRoleList
	* @Description:
	* @param @param sysRole
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.service.SysRoleService#getRoleList(com.whjn.sysManage.model.po.SysRole, java.lang.Integer) 
	*/
	@Override
	public QueryResult<SysRole> getRoleList(SysRole sysRole, String nodeId) {
		nodeId = nodeId.substring(0, nodeId.lastIndexOf("-"));
		QueryResult<SysRole> roleList = sysRoleDao.getRoleList(sysRole, nodeId);
		return roleList;
	}


	/* (非 Javadoc) 
	* @Title: getRoleInfo
	* @Description:
	* @param @param roleId
	* @param @return 
	* @see com.whjn.sysManage.service.SysRoleService#getRoleInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysRole> getRoleInfo(Integer roleId) {
		return sysRoleDao.getRoleInfo(roleId);
	}

	/* (非 Javadoc) 
	* @Title: delete
	* @Description:
	* @param @param entity
	* @param @param ids 
	* @see com.whjn.sysManage.service.SysRoleService#delete(com.whjn.sysManage.model.po.SysRole, java.lang.Long[]) 
	*/
	@Override
	public void delete(SysRole entity, Long[] ids, Integer nodeType) {
		
	}

	/* (非 Javadoc) 
	* @Title: delRoleByIds
	* @Description:
	* @param @param result
	* @param @param ids 
	* @see com.whjn.sysManage.service.SysRoleService#delRoleByIds(java.util.Map, java.lang.Long[]) 
	*/
	@Transactional
	@Override
	public void delRoleByIds(Map<String, Object> result, Long[] ids) {
		boolean flag = false;
		for (int i = 0; i < ids.length; i++) {
			List<SysRoleUser> list = sysRoleDao.getRoleUserByRoleId(ids[i]);
			if(list.size()>0) {
				result.put("message", "删除失败，所选角色中用户，不能删除！");
				break;
			} else {
				flag = deleteByPK(ids[i]);
			}
		}
		result.put("success", flag);
		
	}
}
