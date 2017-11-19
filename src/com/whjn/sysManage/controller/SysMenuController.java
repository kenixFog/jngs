package com.whjn.sysManage.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whjn.common.base.ListView;
import com.whjn.common.base.QueryResult;
import com.whjn.common.controller.BaseController;
import com.whjn.common.util.RequestUtils;
import com.whjn.sysManage.model.SysMenu;
import com.whjn.sysManage.service.SysMenuService;


@Controller
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController<SysMenu> {

	@Resource
	private SysMenuService sysMenuService;

	/**
	* @Title: getUseMenuByAuthority 
	* @Description: 根据用户使用菜单权限获取菜单
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年11月17日 下午4:48:37 
	* @version V1.0
	 */
	@RequestMapping("/getUseMenuByAuthority")
	public void getUseMenuByAuthority(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long userId= Long.parseLong(request.getParameter("userId"));
		long parentId= Long.parseLong(request.getParameter("parentId"));
		List<SysMenu> resultList = sysMenuService.getUseMenuByAuthority(userId,parentId);
		writeJSON(response, resultList);
	}
	
	/**
	* @Title: getButton 
	* @Description: 根据用户权限获取按钮权限
	* @param @param request
	* @param @param response
	* @param @throws Exception  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年9月8日 下午5:05:40 
	* @version V1.0
	 */
	@RequestMapping("/getButton")
	public void getButton(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		long userId= Long.parseLong(request.getParameter("userId"));
		long muneId= Long.parseLong(request.getParameter("muneId"));
		System.out.println(userId + "//"+ muneId);
		String buttonList = sysMenuService.getButton(userId,muneId);
		result.put("objCodeArr", buttonList);
		writeJSON(response, result);
	}
	
	/**
	 * 
	* @Title: getMenuTreeByParentId 
	* @Description: 根据用户管理菜单权限获取菜单
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年11月1日 下午3:00:51 
	* @version V1.0
	 */
	//异步树
	@RequestMapping("/getMenuTreeByParentId")
	public void getMenuTreeByParentId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long parentId = Long.parseLong(request.getParameter("parentId"));
		List<SysMenu> resultList = sysMenuService.getMenuTreeByParentId(parentId);
		writeJSON(response, resultList);
	}
	
	/**
	 * 
	* @Title: getMenuList 
	* @Description: 获取菜单列表
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年11月1日 下午3:00:26 
	* @version V1.0
	 */
	@RequestMapping("/getMenuList")
	public void getMenuList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		//每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		//查询参数
		String qryName = RequestUtils.getStringParameter(request, "qryName");
		//节点Id
		Integer nodeId = RequestUtils.getIntParameter(request, "nodeId");
		SysMenu sysMenu = new SysMenu();
		sysMenu.setFirstResult(firstResult);
		sysMenu.setPageSize(pageSize);
		QueryResult<SysMenu> queryResult = sysMenuService.getMenuList(sysMenu,qryName,nodeId);
		ListView<SysMenu> menuListView = new ListView<SysMenu>();
		menuListView.setData(queryResult.getResultList());
		menuListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, menuListView);
	}
	
}
