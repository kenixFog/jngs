package com.whjn.sysManage.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.sysManage.dao.SysMenuDao;
import com.whjn.sysManage.model.po.SysMenu;
import com.whjn.sysManage.service.SysMenuService;

import net.sf.json.JSONObject;



@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements SysMenuService {

	private SysMenuDao sysMenuDao;

	@Resource
	public void setSysMenuDao(SysMenuDao sysMenuDao) {
		this.sysMenuDao = sysMenuDao;
		this.baseDao = sysMenuDao;
	}

	/*
	 * (非 Javadoc) 
	* @Title: getUseMenuByAuthority
	* @Description:根据用户使用菜单权限获取菜单
	* @param @param userId
	* @param @param parentId
	* @param @return 
	* @see com.whjn.sysManage.service.SysMenuService#getUseMenuByAuthority(long, long)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SysMenu> getUseMenuByAuthority(long userId, long parentId) {
		List<SysMenu> mainMenuList = sysMenuDao.getUseMenuByAuthority(userId, parentId);
		List resultList = new ArrayList();
		for (int i = 0; i < mainMenuList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", mainMenuList.get(i).getId());
			jsonObject.put("code", mainMenuList.get(i).getMenuCode());
			jsonObject.put("text", mainMenuList.get(i).getMenuName());
			jsonObject.put("url", mainMenuList.get(i).getUrl());
			jsonObject.put("parentId", mainMenuList.get(i).getParentId());
			jsonObject.put("statue", mainMenuList.get(i).getStatue());
			jsonObject.put("isedit", mainMenuList.get(i).getIsEdit());
			jsonObject.put("isdelete", mainMenuList.get(i).getIsDelete());
			jsonObject.put("leaf", mainMenuList.get(i).getType());
			resultList.add(jsonObject);
		}
		return resultList;
	}

	/*
	 * (非 Javadoc) 
	* @Title: getButton
	* @Description:根据用户权限获取按钮权限
	* @param @param userId
	* @param @param muneId
	* @param @return 
	* @see com.whjn.sysManage.service.SysMenuService#getButton(long, long)
	 */
	@Override
	public String getButton(long userId, long muneId) {
		List<SysMenu> mainMenuList = sysMenuDao.getButton(userId, muneId);
		StringBuffer sbBuffer = new StringBuffer("['");
		for (int i = 0; i < mainMenuList.size(); i++) {
			sbBuffer.append(mainMenuList.get(i).getMenuCode());
			if(i==mainMenuList.size()-1) {
				sbBuffer.append("']");
			} else {
				sbBuffer.append("','");
			}
			
		}
		return sbBuffer.toString();
	}

	/*
	 * (非 Javadoc) 
	* @Title: getMenuTreeByParentId
	* @Description:根据用户管理菜单权限获取菜单
	* @param @param parentId
	* @param @return 
	* @see com.whjn.sysManage.service.SysMenuService#getMenuTreeByParentId(long)
	 */
	@Override
	public List<SysMenu> getMenuTreeByParentId(long parentId) {
		List<SysMenu> mainMenuList = sysMenuDao.getMenuTreeByParentId(parentId);
		List resultList = new ArrayList();
		for (int i = 0; i < mainMenuList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", mainMenuList.get(i).getId());
			jsonObject.put("code", mainMenuList.get(i).getMenuCode());
			jsonObject.put("text", mainMenuList.get(i).getMenuName());
			jsonObject.put("url", mainMenuList.get(i).getUrl());
			jsonObject.put("parentId", mainMenuList.get(i).getParentId());
			jsonObject.put("statue", mainMenuList.get(i).getStatue());
			jsonObject.put("isedit", mainMenuList.get(i).getIsEdit());
			jsonObject.put("isdelete", mainMenuList.get(i).getIsDelete());
			jsonObject.put("leaf", mainMenuList.get(i).getType());
			resultList.add(jsonObject);
		}
		return resultList;
	}

	/*
	 * (非 Javadoc) 
	* @Title: getMenuList
	* @Description:获取菜单列表
	* @param @param sysMenu
	* @param @param qryName
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.service.SysMenuService#getMenuList(com.whjn.sysManage.model.SysMenu, java.lang.String, java.lang.Integer)
	 */
	@Override
	public QueryResult<SysMenu> getMenuList(SysMenu sysMenu, String qryName, Integer nodeId) {
		QueryResult<SysMenu> menuList = sysMenuDao.getMenuList(sysMenu, qryName, nodeId);
		return menuList;
	}

	/* (非 Javadoc) 
	* @Title: getMenuInfo
	* @Description:获取菜单信息
	* @param @param menuId
	* @param @return 
	* @see com.whjn.sysManage.service.SysMenuService#getMenuInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysMenu> getMenuInfo(Integer menuId) {
		return sysMenuDao.getMenuInfo(menuId);
	}

	/* (非 Javadoc) 
	* @Title: delMenuByIds
	* @Description:删除菜单信息
	* @param @param entity
	* @param @param ids 
	* @see com.whjn.sysManage.service.SysMenuService#delComCodeByIds(com.whjn.sysManage.model.SysComCode, java.lang.Long[]) 
	*/
	@Override
	@Transactional
	public void delMenuByIds(SysMenu entity, Long[] ids) {
		boolean result = false;
		for (int i = 0; i < ids.length; i++) {
			List<SysMenu> list = (List<SysMenu>) queryByProerties("parentId", ids[i]);
			if(list.size()>0) {
				entity.setMessage("删除失败，所选节点中，存在子节点！");
				break;
			} else {
				List<SysMenu> obj = (List<SysMenu>) queryByProerties("id", ids[i]);
				if(obj.get(0).getIsDelete() == 0) {
					entity.setMessage("存在不可删除的节点！");
					break;
				} else {
					result = deleteByPK(ids[i]);
				}
			}
		}
		entity.setSuccess(result);
		
	}

}
