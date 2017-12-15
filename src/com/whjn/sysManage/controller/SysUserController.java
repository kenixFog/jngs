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
import com.whjn.sysManage.model.po.SysUser;
import com.whjn.sysManage.service.SysOrgService;
import com.whjn.sysManage.service.SysUserService;

@Controller
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SysOrgService sysOrgService;

	@RequestMapping("/getUserList")
	public void getUserList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 节点Id
		Integer nodeId = RequestUtils.getIntParameter(request, "nodeId");
		Map<String, String> paramMap = new HashMap<String, String>();
		String[] fields = new String[] { "realName", "statue" };
		// 获取查询条件
		for (int i = 0; i < fields.length; i++) {
			paramMap.put(fields[i], RequestUtils.getStringParameter(request, fields[i]));
		}
		SysUser sysUser = new SysUser();
		sysUser.setQueryDynamicConditions(paramMap);
		sysUser.setFirstResult(firstResult);
		sysUser.setPageSize(pageSize);
		QueryResult<SysUser> queryResult = sysUserService.getUserList(sysUser, nodeId);
		ListView<SysUser> menuListView = new ListView<SysUser>();
		menuListView.setData(queryResult.getResultList());
		menuListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, menuListView);
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
		// 用户Id
		Integer userId = RequestUtils.getIntParameter(request, "userId");
		List<SysUser> resultList = sysUserService.getUserInfo(userId);
		String[] col = { "id", "userName", "realName", "orgId", "email", "tel", "statue" };
		String result = JsonUtil.fillListJsonString(col, resultList);
		writeJSON(response, result);
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
		if (entity.getId() == -1) {// ID为-1代表新增
			//检查用户名是否存在
			SysUser checkUserName = sysUserService.getByProerties("userName", entity.getUserName());
			if (null == checkUserName) {// 编码不存在
				SysOrg sysOrg = sysOrgService.get(RequestUtils.getLongParameter(request, "orgId"));
				entity.setOrg(sysOrg);
				entity.setCreateTime(new Date());
				sysUserService.persist(entity);
				entity.setSuccess(true);
			} else {
				entity.setSuccess(false);
				entity.setMessage("保存失败，用户名已存在，请重新输入！");
			}
		} else {// 修改
			//根据ID获取原数据
			SysUser oldObj = sysUserService.getByProerties("id", entity.getId());
			// 根据新修改的用户名去数据库查询数据
			SysUser checkUserName = sysUserService.getByProerties("userName", entity.getUserName());
			if ((!oldObj.getUserName().equals(entity.getUserName())) && null != checkUserName) {// 编码被修改,并且数据库存在已修改的编码
				entity.setSuccess(false);
				entity.setMessage("保存失败，用户名已存在，请重新输入！");
			} else {
				SysOrg sysOrg = sysOrgService.get(RequestUtils.getIntParameter(request, "orgId"));
				entity.setOrg(sysOrg);
				sysUserService.merge(entity);
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
	@RequestMapping(value = "/delUserByIds", method = { RequestMethod.POST, RequestMethod.GET })
	public void delUserByIds(SysUser entity, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ids") Long[] ids) throws IOException {
		boolean result = sysUserService.deleteByPK(ids);
		entity.setSuccess(result);
		if(!result) {//删除失败
			entity.setMessage("删除失败，请与系统开发人员联系！");
		}
		writeJSON(response, entity);
	}

}
