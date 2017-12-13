package com.whjn.sysManage.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.whjn.sysManage.model.po.SysUser;
import com.whjn.sysManage.service.SysRoleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

	private SysRoleDao sysRoleDao;

	
	private SysOrgDao sysOrgDao;

	@Resource
	public void setSysOrgDao(SysOrgDao sysOrgDao) {
		this.sysOrgDao = sysOrgDao;
	}
	
	@Resource
	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
		this.baseDao = sysRoleDao;
	}

	/* (非 Javadoc) 
	* @Title: getRoleTreeByParentId
	* @Description:
	* @param @param parentId
	* @param @return 
	* @see com.whjn.sysManage.service.SysRoleService#getRoleTreeByParentId(long) 
	*/
	@Override
	public List getRoleTreeByParentId(long parentId) {
		List<SysOrg> OrgList = sysOrgDao.getOrgTreeByParentId(parentId);
		List resultList = new ArrayList();
		for (int i = 0; i < OrgList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", OrgList.get(i).getId());
			jsonObject.put("code", OrgList.get(i).getOrgCode());
			jsonObject.put("text", OrgList.get(i).getOrgName());
			jsonObject.put("parentId", OrgList.get(i).getParentId());
			jsonObject.put("leaf", 0);
			jsonObject.put("nodeType", "0");//0代表组织机构
			//子节点
			List<SysOrg> OrgChildList = sysOrgDao.getOrgTreeByParentId(OrgList.get(i).getId());
			equipTreeNode(jsonObject, OrgChildList, OrgList.get(i).getId());
			resultList.add(jsonObject);
		}
		return resultList;
	}
	
	
	/** 
	* @Title: equipTreeNode 
	* @Description: 
	* @param @param jsonObject
	* @param @param orgChildList  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年12月8日 下午4:21:17 
	* @version V1.0   
	 * @param currentOrgId 
	*/
	private void equipTreeNode(JSONObject jsonObject, List<SysOrg> orgList, Long currentOrgId) {
		JSONArray jsonArray = new JSONArray();
		//获取当前组织机构下的角色分组
		List<SysRoleType> roleTypeList = sysRoleDao.getRoleTypeByOrgId(currentOrgId);
		for(int i=0;i<roleTypeList.size();i++) {
			JSONObject roleTypeJsonObject = new JSONObject();
			roleTypeJsonObject.put("id", roleTypeList.get(i).getId()+"-");
			roleTypeJsonObject.put("code", roleTypeList.get(i).getRoleCode());
			roleTypeJsonObject.put("text", roleTypeList.get(i).getRoleName());
			roleTypeJsonObject.put("leaf", 1);
			roleTypeJsonObject.put("parentId", roleTypeList.get(i).getOrg().getId());
			roleTypeJsonObject.put("nodeType", "1");//代表分组
			jsonArray.add(roleTypeJsonObject);
		}
		for(int i=0;i<orgList.size();i++) {
			JSONObject childrenJsonObject = new JSONObject();
			List<SysOrg> orgChildList = sysOrgDao.getOrgTreeByParentId(orgList.get(i).getId());
			childrenJsonObject.put("id", orgList.get(i).getId());
			childrenJsonObject.put("code", orgList.get(i).getOrgCode());
			childrenJsonObject.put("text", orgList.get(i).getOrgName());
			childrenJsonObject.put("leaf", 0);
			childrenJsonObject.put("parentId", orgList.get(i).getParentId());
			childrenJsonObject.put("nodeType", "0");
			equipTreeNode(childrenJsonObject, orgChildList, orgList.get(i).getId());
			jsonArray.add(childrenJsonObject);
		}
		jsonObject.element("children", jsonArray);
	}

	/* (非 Javadoc) 
	* @Title: getRoleTypeList
	* @Description:
	* @param @param sysRoleType
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.service.SysRoleService#getRoleTypeList(com.whjn.sysManage.model.po.SysRoleType, java.lang.Integer) 
	*/
	@Override
	public QueryResult<SysRoleType> getRoleTypeList(SysRoleType sysRoleType, String nodeId) {
		QueryResult<SysRoleType> roleTypeList = sysRoleDao.getRoleTypeList(sysRoleType, nodeId);
		return roleTypeList;
	}

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

}
