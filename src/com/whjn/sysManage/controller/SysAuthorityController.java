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
import com.whjn.sysManage.service.SysOrgService;
import com.whjn.sysManage.service.SysRoleService;
import com.whjn.sysManage.service.SysRoleTypeService;

@Controller
@RequestMapping("/sys/authority")
public class SysAuthorityController extends BaseController {

	@Resource
	private SysRoleService sysRoleService;
	
	
	@Resource
	private SysOrgService sysOrgService;
	
	
	@Resource
	private SysRoleTypeService sysRoleTypeService;

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
		List resultList = sysRoleTypeService.getRoleTreeByParentId(parentId);
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
			QueryResult<SysRoleType> queryResult = sysRoleTypeService.getRoleTypeList(sysRoleType, nodeId);
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
	 * @Title: getRoleInfo
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
	@RequestMapping("/getRoleInfo")
	public void getRoleInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 角色Id
		Integer roleId = RequestUtils.getIntParameter(request, "roleId");
		//当前选中的节点类型
		Integer nodeType = RequestUtils.getIntParameter(request, "nodeType");
		String[] col = { "id", "roleName", "roleCode", "comments"};
		String result = "";
		if(nodeType == 0) {//获取分组
			List<SysRoleType> resultList = sysRoleTypeService.getRoleTypeInfo(roleId);
			result = JsonUtil.fillListJsonString(col, resultList);
		}else {//获取角色
			List<SysRole> resultList = sysRoleService.getRoleInfo(roleId);
			result = JsonUtil.fillListJsonString(col, resultList);
		}
		writeJSON(response, result);
	}

	/**
	 * @Title: saveRoleInfo
	 * @Description: 保存角色信息
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
	@RequestMapping(value = "/saveRoleTypeInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveRoleTypeInfo(SysRoleType entity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (entity.getId() == -1) {// ID为-1代表新增
			//检查编码和名称是否存在
			SysRoleType checkRoleTypeName = sysRoleTypeService.getByProerties("roleName", entity.getRoleName());
			SysRoleType checkRoleTypeCode = sysRoleTypeService.getByProerties("roleCode", entity.getRoleCode());
			if (null == checkRoleTypeCode && null==checkRoleTypeName) {// 编码和名称都不存在
				SysOrg sysOrg = sysOrgService.get(RequestUtils.getLongParameter(request, "nodeId"));
				entity.setOrg(sysOrg);
				entity.setCreateTime(new Date());
				sysRoleTypeService.persist(entity);
				entity.setSuccess(true);
			} else {
				entity.setSuccess(false);
				entity.setMessage("保存失败，编码或名称已存在，保存失败！");
			}
		} else {// 修改
			//根据ID获取原数据
			SysRoleType oldObj = sysRoleTypeService.getByProerties("id", entity.getId());
			// 根据新修改的用户名去数据库查询数据
			SysRoleType checkRoleTypeName = sysRoleTypeService.getByProerties("roleName", entity.getRoleName());
			SysRoleType checkRoleTypeCode = sysRoleTypeService.getByProerties("roleCode", entity.getRoleCode());
			if (((!oldObj.getRoleName().equals(entity.getRoleName())) && null != checkRoleTypeName)||
					((!oldObj.getRoleCode().equals(entity.getRoleCode())) && null != checkRoleTypeCode)	
					) {//名称被修改，且数据库存在已修改的编码 或 编码被修改,且数据库存在已修改的编码
				entity.setSuccess(false);
				entity.setMessage("保存失败，码或名称已存在，保存失败！");
			} else {
				SysOrg sysOrg = sysOrgService.get(RequestUtils.getLongParameter(request, "nodeId"));
				entity.setOrg(sysOrg);
				entity.setCreateTime(oldObj.getCreateTime());
				sysRoleTypeService.merge(entity);
				entity.setSuccess(true);
			}
		}	
		writeJSON(response, entity);
		
	}
	
	/**
	 * @Title: saveRoleInfo
	 * @Description: 保存角色信息
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
	@RequestMapping(value = "/saveRoleInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveRoleInfo(SysRole entity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String nodeId = RequestUtils.getStringParameter(request, "nodeId");
		if (entity.getId() == -1) {// ID为-1代表新增
			//检查编码和名称是否存在
			SysRole checkRoleName = sysRoleService.getByProerties("roleName", entity.getRoleName());
			SysRole checkRoleCode = sysRoleService.getByProerties("roleCode", entity.getRoleCode());
			if (null == checkRoleCode && null==checkRoleName) {// 编码和名称都不存在
				SysRoleType sysRoleType = sysRoleTypeService.get(nodeId.substring(0, nodeId.indexOf("-")));
				entity.setSysRoleType(sysRoleType);
				entity.setCreateTime(new Date());
				sysRoleService.persist(entity);
				entity.setSuccess(true);
			} else {
				entity.setSuccess(false);
				entity.setMessage("保存失败，编码或名称已存在，保存失败！");
			}
		} else {// 修改
			//根据ID获取原数据
			SysRole oldObj = sysRoleService.getByProerties("id", entity.getId());
			// 根据新修改的用户名去数据库查询数据
			SysRole checkRoleName = sysRoleService.getByProerties("roleName", entity.getRoleName());
			SysRole checkRoleCode = sysRoleService.getByProerties("roleCode", entity.getRoleCode());
			if (((!oldObj.getRoleName().equals(entity.getRoleName())) && null != checkRoleName)||
					((!oldObj.getRoleCode().equals(entity.getRoleCode())) && null != checkRoleCode)	
					) {//名称被修改，且数据库存在已修改的编码 或 编码被修改,且数据库存在已修改的编码
				entity.setSuccess(false);
				entity.setMessage("保存失败，编码或名称已存在，保存失败！");
			} else {
				SysRoleType sysRoleType = sysRoleTypeService.get(Long.parseLong(nodeId.substring(0, nodeId.indexOf("-"))));
				entity.setSysRoleType(sysRoleType);
				entity.setCreateTime(oldObj.getCreateTime());
				sysRoleService.merge(entity);
				entity.setSuccess(true);
			}
		}
		writeJSON(response, entity);
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
	@RequestMapping(value = "/delRoleByIds", method = { RequestMethod.POST, RequestMethod.GET })
	public void delRoleByIds(SysRole entity, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ids") Long[] ids) throws IOException {
		Integer nodeType = RequestUtils.getIntParameter(request, "nodeType");
		Map<String, Object> result = new HashMap<String, Object>();
		if (ids != null && ids.length > 0) {
			if(nodeType == 0) {//删除的是分组，需要判断下面有没有角色，有则提示不能删除
				sysRoleTypeService.delRoleTypeByIds(result,ids);
			} else {//删除的是角色，需要判断下面有没有用户，有则提示不能删除
				sysRoleService.delRoleByIds(result,ids);
			}
		}
		writeJSON(response, result);
	}

}
