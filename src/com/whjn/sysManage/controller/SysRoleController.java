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
import com.whjn.sysManage.model.po.SysOrg;
import com.whjn.sysManage.model.po.SysRole;
import com.whjn.sysManage.model.po.SysRoleType;
import com.whjn.sysManage.model.po.SysUser;
import com.whjn.sysManage.service.SysOrgService;
import com.whjn.sysManage.service.SysRoleService;
import com.whjn.sysManage.service.SysUserService;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController<SysRole> {

	@Resource
	private SysRoleService sysRoleService;
//	
//	@Resource
//	private SysRoleTypeService sysRoleTypeService;

	/**
	* @Title: getRoleTreeByParentId 
	* @Description: 获取角色树
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年12月1日 下午4:39:23 
	* @version V1.0
	*/
	@RequestMapping("/getRoleTreeByParentId")
	public void getRoleTreeByParentId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long parentId = Long.parseLong(request.getParameter("parentId"));
		List resultList = sysRoleService.getRoleTreeByParentId(parentId);
		writeJSON(response, resultList);
	}
	
	
	/**
	* @Title: getRoleList 
	* @Description: 获取角色或角色分组
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年12月13日 下午5:15:18 
	* @version V1.0
	 */
	@RequestMapping("/getRoleList")
	public void getRoleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 节点Id
		String nodeId = RequestUtils.getStringParameter(request, "nodeId");
		// 节点类型，根据类型判断是获取角色分组还是角色
		Integer nodeType = RequestUtils.getIntParameter(request, "nodeType");
		
		if(nodeType==0) {//组织机构，需要获取分组
			SysRoleType sysRoleType = new SysRoleType();
			sysRoleType.setFirstResult(firstResult);
			sysRoleType.setPageSize(pageSize);
			QueryResult<SysRoleType> queryResult = sysRoleService.getRoleTypeList(sysRoleType, nodeId);
			ListView<SysRoleType> roletypeListView = new ListView<SysRoleType>();
			roletypeListView.setData(queryResult.getResultList());
			roletypeListView.setTotalRecord(queryResult.getTotalCount());
			writeJSON(response, roletypeListView);
		} else {//角色分组，需要获取角色
			SysRole sysRole = new SysRole();
			sysRole.setFirstResult(firstResult);
			sysRole.setPageSize(pageSize);
			QueryResult<SysRole> queryResult = sysRoleService.getRoleList(sysRole, nodeId);
			ListView<SysRole> roleListView = new ListView<SysRole>();
			roleListView.setData(queryResult.getResultList());
			roleListView.setTotalRecord(queryResult.getTotalCount());
			writeJSON(response, roleListView);
		}
	}

	/**
	 * @Title: getUserInfo
	 * @Description: 获取用户信息
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
	@RequestMapping("/getUserInfo")
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		// 用户Id
//		Integer userId = RequestUtils.getIntParameter(request, "userId");
//		List<SysUser> resultList = sysUserService.getUserInfo(userId);
//		String[] col = { "id", "userName", "realName", "orgId", "email", "tel", "statue" };
//		String result = JsonUtil.fillListJsonString(col, resultList);
//		writeJSON(response, result);
	}

	/**
	 * @Title: saveUserInfo
	 * @Description: 保存用户信息
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
	@RequestMapping(value = "/saveUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveUserInfo(SysUser entity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
//		if (entity.getId() == -1) {// ID为-1代表新增
//			//检查用户名是否存在
//			SysUser checkUserName = sysUserService.getByProerties("userName", entity.getUserName());
//			if (null == checkUserName) {// 编码不存在
//				SysOrg sysOrg = sysOrgService.get(RequestUtils.getLongParameter(request, "orgId"));
//				entity.setOrg(sysOrg);
//				entity.setCreateTime(new Date());
//				sysUserService.persist(entity);
//				entity.setSuccess(true);
//			} else {
//				entity.setSuccess(false);
//				entity.setMessage("保存失败，用户名已存在，请重新输入！");
//			}
//		} else {// 修改
//			//根据ID获取原数据
//			SysUser oldObj = sysUserService.getByProerties("id", entity.getId());
//			// 根据新修改的用户名去数据库查询数据
//			SysUser checkUserName = sysUserService.getByProerties("userName", entity.getUserName());
//			if ((!oldObj.getUserName().equals(entity.getUserName())) && null != checkUserName) {// 编码被修改,并且数据库存在已修改的编码
//				entity.setSuccess(false);
//				entity.setMessage("保存失败，用户名已存在，请重新输入！");
//			} else {
//				SysOrg sysOrg = sysOrgService.get(RequestUtils.getIntParameter(request, "orgId"));
//				entity.setOrg(sysOrg);
//				sysUserService.merge(entity);
//				entity.setSuccess(true);
//			}
//		}
//		writeJSON(response, entity);
	}

	/**
	 * @Title: delUserByIds
	 * @Description: 根据ID删除用户
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
	@RequestMapping(value = "/delUserByIds", method = { RequestMethod.POST, RequestMethod.GET })
	public void delUserByIds(SysUser entity, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ids") Long[] ids) throws IOException {
//		boolean result = sysUserService.deleteByPK(ids);
//		entity.setSuccess(result);
//		if(!result) {//删除失败
//			entity.setMessage("删除失败，请与系统开发人员联系！");
//		}
//		writeJSON(response, entity);
	}

}
