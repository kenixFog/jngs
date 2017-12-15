package com.whjn.sysManage.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.whjn.common.base.ListView;
import com.whjn.common.base.QueryResult;
import com.whjn.common.controller.BaseController;
import com.whjn.common.util.JsonUtil;
import com.whjn.common.util.RequestUtils;
import com.whjn.sysManage.model.po.SysMenu;
import com.whjn.sysManage.service.SysMenuService;

@Controller
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

	@Resource
	private SysMenuService sysMenuService;

	/**
	 * @Title: getUseMenuByAuthority
	 * @Description: 根据用户使用菜单权限获取菜单
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author kenix
	 * @throws @date
	 *             2017年11月17日 下午4:48:37
	 * @version V1.0
	 */
	@RequestMapping("/getUseMenuByAuthority")
	public void getUseMenuByAuthority(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long userId = Long.parseLong(request.getParameter("userId"));
		long parentId = Long.parseLong(request.getParameter("parentId"));
		List<SysMenu> resultList = sysMenuService.getUseMenuByAuthority(userId, parentId);
		writeJSON(response, resultList);
	}

	/**
	 * @Title: getButton
	 * @Description: 根据用户权限获取按钮权限
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            Exception
	 * @return void
	 * @author kenix
	 * @throws @date
	 *             2017年9月8日 下午5:05:40
	 * @version V1.0
	 */
	@RequestMapping("/getButton")
	public void getButton(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		long userId = Long.parseLong(request.getParameter("userId"));
		long muneId = Long.parseLong(request.getParameter("muneId"));
		System.out.println(userId + "//" + muneId);
		String buttonList = sysMenuService.getButton(userId, muneId);
		result.put("objCodeArr", buttonList);
		writeJSON(response, result);
	}

	/**
	 * 
	 * @Title: getMenuTreeByParentId
	 * @Description: 根据用户管理菜单权限获取菜单
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author kenix
	 * @throws @date
	 *             2017年11月1日 下午3:00:51
	 * @version V1.0
	 */
	// 异步树
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
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author kenix
	 * @throws @date
	 *             2017年11月1日 下午3:00:26
	 * @version V1.0
	 */
	@RequestMapping("/getMenuList")
	public void getMenuList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 查询参数
		String qryName = RequestUtils.getStringParameter(request, "qryName");
		// 节点Id
		Integer nodeId = RequestUtils.getIntParameter(request, "nodeId");
		SysMenu sysMenu = new SysMenu();
		sysMenu.setFirstResult(firstResult);
		sysMenu.setPageSize(pageSize);
		QueryResult<SysMenu> queryResult = sysMenuService.getMenuList(sysMenu, qryName, nodeId);
		ListView<SysMenu> menuListView = new ListView<SysMenu>();
		menuListView.setData(queryResult.getResultList());
		menuListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, menuListView);
	}

	/**
	 * @Title: getMenuInfo
	 * @Description: 获取菜单信息
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author Chen Cai
	 * @throws @date
	 *             2017年11月28日 下午3:52:33
	 * @version V1.0
	 */
	@RequestMapping("/getMenuInfo")
	public void getMenuInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 菜单Id
		Integer menuId = RequestUtils.getIntParameter(request, "menuId");
		List<SysMenu> resultList = sysMenuService.getMenuInfo(menuId);
		String[] col = { "ID", "parentId", "menuCode", "menuName", "type", "url", "isDelete", "isEdit", "statue",
				"createTime", "lastEditTime" };
		String result = JsonUtil.fillListJsonString(col, resultList);
		writeJSON(response, result);
	}

	/**
	 * @Title: saveMenuInfo
	 * @Description: 保存菜单信息
	 * @param @param
	 *            entity
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author Chen Cai
	 * @throws @date
	 *             2017年11月28日 下午3:52:40
	 * @version V1.0
	 */
	@RequestMapping(value = "/saveMenuInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveMenuInfo(SysMenu entity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (entity.getId() == -1) {// ID为-1代表新增
			// 根据编码获取数据
			SysMenu checkCode = sysMenuService.getByProerties("menuCode", entity.getMenuCode());
			if (entity.getType() == 2 || null == checkCode) {// 新增按钮，或编码不重复，则直接新增
				entity.setCreateTime(new Date());
				entity.setLastEditTime(new Date());
				sysMenuService.persist(entity);
				entity.setSuccess(true);
			} else {
				entity.setSuccess(false);
				entity.setMessage("保存失败，编码已存在，请重新输入！");
			}
		} else {// 修改
			// 根据ID获取原数据
			SysMenu oldObj = sysMenuService.getByProerties("id", entity.getId());
			// 根据新修改的编码去数据库查询数据
			SysMenu checkCode = sysMenuService.getByProerties("menuCode", entity.getMenuCode());
			if ((!oldObj.getMenuCode().equals(entity.getMenuCode())) && entity.getType() != 2 && null != checkCode) {// 编码被修改
				entity.setSuccess(false);
				entity.setMessage("保存失败，编码已存在，请重新输入！");
			} else {
				entity.setCreateTime(oldObj.getCreateTime());
				entity.setLastEditTime(new Date());
				sysMenuService.merge(entity);
				entity.setSuccess(true);
			}
		}
		writeJSON(response, entity);
	}

	/**
	 * @Title: delMenuByIds
	 * @Description: 根据ID删除菜单
	 * @param @param
	 *            entity
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @param
	 *            ids
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author Chen Cai
	 * @throws @date
	 *             2017年11月28日 下午3:52:46
	 * @version V1.0
	 */
	@RequestMapping(value = "/delMenuByIds", method = { RequestMethod.POST, RequestMethod.GET })
	public void delMenuByIds(SysMenu entity, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ids") Long[] ids) throws IOException {
		sysMenuService.delMenuByIds(entity, ids);
		writeJSON(response, entity);
	}
}
