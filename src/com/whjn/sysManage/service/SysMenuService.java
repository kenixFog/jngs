package com.whjn.sysManage.service;

import java.util.List;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.SysMenu;

public interface SysMenuService extends BaseService<SysMenu> {

	/**
	 * @Title: getUseMenuByAuthority
	 * @Description: 根据用户使用菜单权限获取菜单
	 * @param @param
	 *            userId
	 * @param @param
	 *            parentId
	 * @param @return
	 * @return List<SysMenu>
	 * @author kenix
	 * @throws @date
	 *             2017年11月17日 下午4:49:20
	 * @version V1.0
	 */
	List<SysMenu> getUseMenuByAuthority(long userId, long parentId);

	/** 
	* @Title: getButton 
	* @Description: 根据用户权限获取按钮权限
	* @param @param userId
	* @param @param muneId
	* @param @return  
	* @return String    
	* @author kenix
	* @throws
	* @date 2017年11月17日 下午5:04:26 
	* @version V1.0   
	*/
	String getButton(long userId, long muneId);

	/** 
	* @Title: getMenuTreeByParentId 
	* @Description: 根据用户管理菜单权限获取菜单
	* @param @param parentId
	* @param @return  
	* @return List<SysMenu>    
	* @author kenix
	* @throws
	* @date 2017年11月17日 下午5:48:58 
	* @version V1.0   
	*/
	List<SysMenu> getMenuTreeByParentId(long parentId);

	/** 
	* @Title: getMenuList 
	* @Description: 获取菜单列表
	* @param @param sysMenu
	* @param @param qryName
	* @param @param nodeId
	* @param @return  
	* @return QueryResult<SysMenu>    
	* @author kenix
	* @throws
	* @date 2017年11月17日 下午5:53:41 
	* @version V1.0   
	*/
	QueryResult<SysMenu> getMenuList(SysMenu sysMenu, String qryName, Integer nodeId);

	/** 
	* @Title: getMenuInfo 
	* @Description: 获取菜单信息
	* @param @param menuId
	* @param @return  
	* @return List<SysComCode>    
	* @author Chen Cai
	* @throws
	* @date 2017年11月28日 上午10:15:29 
	* @version V1.0   
	*/
	List<SysMenu> getMenuInfo(Integer menuId);

	/** 
	* @Title: delMenuByIds 
	* @Description: 删除菜单信息
	* @param @param entity
	* @param @param ids  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月28日 上午10:17:38 
	* @version V1.0   
	*/
	void delMenuByIds(SysMenu entity, Long[] ids);

}
