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
import com.whjn.sysManage.dao.SysRoleMenuDao;
import com.whjn.sysManage.dao.SysRoleTypeDao;
import com.whjn.sysManage.model.po.SysOrg;
import com.whjn.sysManage.model.po.SysRole;
import com.whjn.sysManage.model.po.SysRoleType;
import com.whjn.sysManage.model.po.SysRoleUser;
import com.whjn.sysManage.service.SysRoleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
	
	@Resource
	private SysRoleDao sysRoleDao;
	
	@Resource
	private SysRoleTypeDao sysRoleTypeDao;

	@Resource
	private SysOrgDao sysOrgDao;
	
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;

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
				result.put("message", "删除失败，所选角色中存在用户，不能删除！");
				break;
			} else {
				flag = deleteByPK(ids[i]);
				sysRoleMenuDao.deleteByProperties("roleId", ids[i]);
			}
		}
		result.put("success", flag);
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
		JSONObject jsonObj = new JSONObject();
		List<SysRoleType> roleTypeList = sysRoleTypeDao.getRoleTypeByOrgId(parentId);
		for(int i=0;i<roleTypeList.size();i++) {
			JSONObject roleTypeJsonObject = new JSONObject();
			roleTypeJsonObject.put("id", roleTypeList.get(i).getId()+"-");
			roleTypeJsonObject.put("code", roleTypeList.get(i).getRoleCode());
			roleTypeJsonObject.put("text", roleTypeList.get(i).getRoleName());
			roleTypeJsonObject.put("leaf", 0);
			roleTypeJsonObject.put("parentId", roleTypeList.get(i).getOrg().getId());
			roleTypeJsonObject.put("nodeType", "1");//代表分组
			equipRoleTreeNode(roleTypeJsonObject,  roleTypeList.get(i).getId());
			resultList.add(roleTypeJsonObject);
		}
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
			equipRoleTypeTreeNode(jsonObject, OrgChildList, OrgList.get(i).getId());
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
	private void equipRoleTypeTreeNode(JSONObject jsonObject, List<SysOrg> orgList, Long currentOrgId) {
		JSONArray jsonArray = new JSONArray();
		//获取当前组织机构下的角色分组
		List<SysRoleType> roleTypeList = sysRoleTypeDao.getRoleTypeByOrgId(currentOrgId);
		for(int i=0;i<roleTypeList.size();i++) {
			JSONObject roleTypeJsonObject = new JSONObject();
			roleTypeJsonObject.put("id", roleTypeList.get(i).getId()+"-");
			roleTypeJsonObject.put("code", roleTypeList.get(i).getRoleCode());
			roleTypeJsonObject.put("text", roleTypeList.get(i).getRoleName());
			roleTypeJsonObject.put("leaf", 0);
			roleTypeJsonObject.put("parentId", roleTypeList.get(i).getOrg().getId());
			roleTypeJsonObject.put("nodeType", "1");//代表分组
			equipRoleTreeNode(roleTypeJsonObject,  roleTypeList.get(i).getId());
			jsonArray.add(roleTypeJsonObject);
		}
		for(int i=0;i<orgList.size();i++) {
			JSONObject childrenJsonObject = new JSONObject();
			List<SysOrg> orgChildList = sysOrgDao.getOrgTreeByParentId(orgList.get(i).getId());
			childrenJsonObject.put("id", orgList.get(i).getId());
			childrenJsonObject.put("code", orgList.get(i).getOrgCode());
			childrenJsonObject.put("text", orgList.get(i).getOrgName());
			childrenJsonObject.put("leaf", 0);
			childrenJsonObject.put("parentId", orgList.get(i).getParentId()+"-");
			childrenJsonObject.put("nodeType", "0");
			equipRoleTypeTreeNode(childrenJsonObject, orgChildList, orgList.get(i).getId());
			jsonArray.add(childrenJsonObject);
		}
		jsonObject.element("children", jsonArray);
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
	private void equipRoleTreeNode(JSONObject jsonObject, Long currentOrgId) {
		JSONArray jsonArray = new JSONArray();
		//获取当角色分组下的角色
		List<SysRole> roleList = sysRoleDao.getRoleByRoleTypeId(currentOrgId);
		for(int i=0;i<roleList.size();i++) {
			JSONObject roleJsonObject = new JSONObject();
			roleJsonObject.put("id", roleList.get(i).getId()+"+");
			roleJsonObject.put("code", roleList.get(i).getRoleCode());
			roleJsonObject.put("text", roleList.get(i).getRoleName());
			roleJsonObject.put("leaf", 1);
			roleJsonObject.put("parentId", currentOrgId+"-");
			roleJsonObject.put("nodeType", "3");//代表角色
			jsonArray.add(roleJsonObject);
		}
		jsonObject.element("children", jsonArray);
	}
	
}
