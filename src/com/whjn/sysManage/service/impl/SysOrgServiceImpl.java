package com.whjn.sysManage.service.impl;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.sysManage.dao.SysOrgDao;
import com.whjn.sysManage.model.po.SysOrg;
import com.whjn.sysManage.service.SysOrgService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrg> implements SysOrgService {

	private SysOrgDao sysOrgDao;

	@Resource
	public void setSysOrgDao(SysOrgDao sysOrgDao) {
		this.sysOrgDao = sysOrgDao;
		this.baseDao = sysOrgDao;
	}

	/* (非 Javadoc) 
	* @Title: getOrgTreeByParentId
	* @Description:
	* @param @param parentId
	* @param @return 
	* @see com.whjn.sysManage.service.SysOrgService#getOrgTreeByParentId(long) 
	*/
//	@Override
//	public List<SysOrg> getOrgTreeByParentId(long parentId) {
//		List<SysOrg> mainComCodeList = sysOrgDao.getOrgTreeByParentId(parentId);
//		List resultList = new ArrayList();
//		for (int i = 0; i < mainComCodeList.size(); i++) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("id", mainComCodeList.get(i).getId());
//			jsonObject.put("code", mainComCodeList.get(i).getOrgCode());
//			jsonObject.put("text", mainComCodeList.get(i).getOrgName());
//			jsonObject.put("parentId", mainComCodeList.get(i).getParentId());
//			jsonObject.put("statue", mainComCodeList.get(i).getStatue());
//			jsonObject.put("attr", mainComCodeList.get(i).getAttr());
//			jsonObject.put("createTime", mainComCodeList.get(i).getCreateTime());
//			resultList.add(jsonObject);
//		}
//		return resultList;
//	}

	
	@Override
	public List getOrgTreeByParentId(long parentId) {
		List<SysOrg> OrgList = sysOrgDao.getOrgTreeByParentId(parentId);
		List resultList = new ArrayList();
		for (int i = 0; i < OrgList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", OrgList.get(i).getId());
			jsonObject.put("code", OrgList.get(i).getOrgCode());
			jsonObject.put("text", OrgList.get(i).getOrgName());
			jsonObject.put("parentId", OrgList.get(i).getParentId());
			jsonObject.put("statue", OrgList.get(i).getStatue());
			jsonObject.put("attr", OrgList.get(i).getAttr());
			jsonObject.put("createTime", OrgList.get(i).getCreateTime());
			//子节点
			List<SysOrg> OrgChildList = sysOrgDao.getOrgTreeByParentId(OrgList.get(i).getId());
			equipTreeNode(jsonObject, OrgChildList);
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
	*/
	private void equipTreeNode(JSONObject jsonObject, List<SysOrg> orgList) {
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<orgList.size();i++) {
			JSONObject childrenJsonObject = new JSONObject();
			List<SysOrg> orgChildList = sysOrgDao.getOrgTreeByParentId(orgList.get(i).getId());
			childrenJsonObject.put("id", orgList.get(i).getId());
			childrenJsonObject.put("code", orgList.get(i).getOrgCode());
			childrenJsonObject.put("text", orgList.get(i).getOrgName());
			childrenJsonObject.put("parentId", orgList.get(i).getParentId());
			childrenJsonObject.put("statue", orgList.get(i).getStatue());
			childrenJsonObject.put("attr", orgList.get(i).getAttr());
			childrenJsonObject.put("createTime", orgList.get(i).getCreateTime());
			equipTreeNode(childrenJsonObject, orgChildList);
			jsonArray.add(childrenJsonObject);
		}
		jsonObject.element("children", jsonArray);
	}

	/* (非 Javadoc) 
	* @Title: getOrgList
	* @Description:
	* @param @param sysMenu
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.service.SysOrgService#getOrgList(com.whjn.sysManage.model.SysMenu, java.lang.Integer) 
	*/
	@Override
	public QueryResult<SysOrg> getOrgList(SysOrg sysOrg, Integer nodeId) {
		QueryResult<SysOrg> menuList = sysOrgDao.getMenuList(sysOrg, nodeId);
		return menuList;
	}

	/* (非 Javadoc) 
	* @Title: getOrgInfo
	* @Description:
	* @param @param orgId
	* @param @return 
	* @see com.whjn.sysManage.service.SysOrgService#getOrgInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysOrg> getOrgInfo(Integer orgId) {
		return sysOrgDao.getOrgInfo(orgId);
	}

	/* (非 Javadoc) 
	* @Title: delOrgByIds
	* @Description:
	* @param @param entity
	* @param @param ids 
	* @see com.whjn.sysManage.service.SysOrgService#delOrgByIds(com.whjn.sysManage.model.SysMenu, java.lang.Long[]) 
	*/
	@Override
	@Transactional
	public void delOrgByIds(SysOrg entity, Long[] ids) {
		boolean result = false;
		for (int i = 0; i < ids.length; i++) {
			List<SysOrg> list = (List<SysOrg>) queryByProerties("parentId", ids[i]);
			if(list.size()>0) {
				entity.setMessage("删除失败，所选节点中，存在子节点！");
				break;
			} else {
				result = deleteByPK(ids[i]);
			}
		}
		entity.setSuccess(result);
	}


}
