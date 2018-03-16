package com.whjn.sysManage.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.sysManage.dao.SysMenuDao;
import com.whjn.sysManage.dao.SysRoleMenuDao;
import com.whjn.sysManage.model.po.SysMenu;
import com.whjn.sysManage.model.po.SysRoleMenu;
import com.whjn.sysManage.service.SysRoleMenuService;

import net.sf.json.JSONObject;



@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu> implements SysRoleMenuService {

	@Resource
	private SysMenuDao sysMenuDao;
	
	
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;

	
	/* (非 Javadoc) 
	* @Title: getAuthorityTree
	* @Description:
	* @param @param parentId
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.service.SysRoleMenuService#getAuthorityTree(long, java.lang.String) 
	*/
	@Override
	public List<SysMenu> getAuthorityTree(long parentId, String roleId) {
		List<SysMenu> mainMenuList = sysMenuDao.getAuthorityMenu(parentId);
		
		List<SysRoleMenu> roleMenuList = sysRoleMenuDao.getRoleMenu(roleId);
		
		List resultList = new ArrayList();
		
		boolean flag = false;
		
		for (int i = 0; i < mainMenuList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", mainMenuList.get(i).getId());
			jsonObject.put("code", mainMenuList.get(i).getMenuCode());
			jsonObject.put("text", mainMenuList.get(i).getMenuName());
			for(int j = 0; j<roleMenuList.size();j++) {
				if(roleMenuList.get(j).getMenuId().equals(mainMenuList.get(i).getId())) {//权限存在
					flag = true;
					break;
				} else {
					flag = false;
				}
			}
			jsonObject.put("checked", flag);
			jsonObject.put("parentId", mainMenuList.get(i).getParentId());
			jsonObject.put("expanded", true);
			resultList.add(jsonObject);
		}
		return resultList;
	}


	/* (非 Javadoc) 
	* @Title: saveAuthorityMenu
	* @Description:
	* @param @param entity
	* @param @param roleId
	* @param @param ids
	* @param @param result 
	* @see com.whjn.sysManage.service.SysRoleMenuService#saveAuthorityMenu(com.whjn.sysManage.model.po.SysRoleMenu, java.lang.String, java.lang.Long[], java.util.Map) 
	*/
	@Override
	@Transactional
	public void saveAuthorityMenu(long roleId, Long[] ids) {
		//先删除原由的数据
		sysRoleMenuDao.deleteByProperties("roleId", roleId);
		//再重新保存
		for(int i = 0 ;i<ids.length;i++) {
			sysRoleMenuDao.saveAuthorityMenu(roleId, ids[i]);
		}
	}

}
