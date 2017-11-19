package com.whjn.sysManage.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.sysManage.dao.SysMenuDao;
import com.whjn.sysManage.model.SysMenu;
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
			jsonObject.put("leaf", mainMenuList.get(i).getIsLeaf());
			jsonObject.put("isedit", mainMenuList.get(i).getIsEdit());
			jsonObject.put("isdelete", mainMenuList.get(i).getIsDelete());
			jsonObject.put("type", mainMenuList.get(i).getType());
			resultList.add(jsonObject);
		}
		return resultList;
	}

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
			jsonObject.put("leaf", mainMenuList.get(i).getIsLeaf());
			jsonObject.put("isedit", mainMenuList.get(i).getIsEdit());
			jsonObject.put("isdelete", mainMenuList.get(i).getIsDelete());
			jsonObject.put("type", mainMenuList.get(i).getType());
			resultList.add(jsonObject);
		}
		return resultList;
	}

	@Override
	public QueryResult<SysMenu> getMenuList(SysMenu sysMenu, String qryName, Integer nodeId) {
		QueryResult<SysMenu> menuList = sysMenuDao.getMenuList(sysMenu, qryName, nodeId);
		return menuList;
	}

}
