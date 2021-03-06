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
import com.whjn.sysManage.dao.SysRoleTypeDao;
import com.whjn.sysManage.model.po.SysOrg;
import com.whjn.sysManage.model.po.SysRole;
import com.whjn.sysManage.model.po.SysRoleType;
import com.whjn.sysManage.service.SysRoleTypeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class SysRoleTypeServiceImpl extends BaseServiceImpl<SysRoleType> implements SysRoleTypeService {

	@Resource
	private SysRoleTypeDao sysRoleTypeDao;
	
	@Resource
	private SysRoleDao sysRoleDao;
	
	@Resource
	private SysOrgDao sysOrgDao;

	@Resource
	public void setSysOrgDao(SysOrgDao sysOrgDao) {
		this.sysOrgDao = sysOrgDao;
	}
	
	@Resource
	public void setSysRoleTypeDao(SysRoleTypeDao sysRoleTypeDao) {
		this.sysRoleTypeDao = sysRoleTypeDao;
		this.baseDao = sysRoleTypeDao;
	}
	
	
	
	/* (非 Javadoc) 
	* @Title: getRoleTypeTreeByParentId
	* @Description:
	* @param @param parentId
	* @param @return 
	* @see com.whjn.sysManage.service.SysRoleService#getRoleTreeByParentId(long) 
	*/
	@Override
	public List getRoleTypeTreeByParentId(long parentId) {
		List<SysOrg> OrgList = sysOrgDao.getOrgTreeByParentId(parentId);
		List resultList = new ArrayList();
		JSONObject jsonObj = new JSONObject();
		List<SysRoleType> roleTypeList = sysRoleTypeDao.getRoleTypeByOrgId(parentId);
		for(int i=0;i<roleTypeList.size();i++) {
			JSONObject roleTypeJsonObject = new JSONObject();
			roleTypeJsonObject.put("id", roleTypeList.get(i).getId()+"-");
			roleTypeJsonObject.put("code", roleTypeList.get(i).getRoleCode());
			roleTypeJsonObject.put("text", roleTypeList.get(i).getRoleName());
			roleTypeJsonObject.put("leaf", 1);
			roleTypeJsonObject.put("parentId", roleTypeList.get(i).getOrg().getId());
			roleTypeJsonObject.put("nodeType", "1");//代表分组
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
		List<SysRoleType> roleTypeList = sysRoleTypeDao.getRoleTypeByOrgId(currentOrgId);
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
		QueryResult<SysRoleType> roleTypeList = sysRoleTypeDao.getRoleTypeList(sysRoleType, nodeId);
		return roleTypeList;
	}
	
	
	/* (非 Javadoc) 
	* @Title: getRoleTypeInfo
	* @Description:
	* @param @param roleId
	* @param @return 
	* @see com.whjn.sysManage.service.SysRoleTypeService#getRoleTypeInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysRoleType> getRoleTypeInfo(Integer roleId) {
		return sysRoleTypeDao.getRoleTypeInfo(roleId);
	}

	/* (非 Javadoc) 
	* @Title: delRoleTypeByIds
	* @Description:
	* @param @param result
	* @param @param ids 
	* @see com.whjn.sysManage.service.SysRoleTypeService#delRoleTypeByIds(java.util.Map, java.lang.Long[]) 
	*/
	@Transactional
	@Override
	public void delRoleTypeByIds(Map<String, Object> result, Long[] ids) {
		boolean flag = false;
		for (int i = 0; i < ids.length; i++) {
			List<SysRole> list = sysRoleDao.getRoleByRoleTypeId(ids[i]);
			if(list.size()>0) {
				result.put("message", "删除失败，所选分组中存在角色，不能删除！");
				break;
			} else {
				flag = deleteByPK(ids[i]);
			}
		}
		result.put("success", flag);
	}

	/* (非 Javadoc) 
	* @Title: getUserRoleType
	* @Description:
	* @param @param id
	* @param @return 
	* @see com.whjn.sysManage.service.SysRoleTypeService#getUserRoleType(java.lang.Long) 
	*/
	@Override
	public List getUserRoleType(Long id) {
		return sysRoleTypeDao.getUserRoleType(id);
	}

}
