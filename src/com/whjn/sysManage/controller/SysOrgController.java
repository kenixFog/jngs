package com.whjn.sysManage.controller;


import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import com.whjn.sysManage.service.SysOrgService;





@Controller
@RequestMapping("/sys/org")
public class SysOrgController extends BaseController{

	@Resource
	private SysOrgService sysOrgService;
	
	/**
	* @Title: getOrgTreeByParentId 
	* @Description: 获取基准组织树
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年12月1日 下午4:39:23 
	* @version V1.0
	*/
	@RequestMapping("/getOrgTreeByParentId")
	public void getOrgTreeByParentId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long parentId = Long.parseLong(request.getParameter("parentId"));
		List resultList = sysOrgService.getOrgTreeByParentId(parentId);
		writeJSON(response, resultList);
	}
	
	/**
	 * 
	 * @Title: getOrgList
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
	@RequestMapping("/getOrgList")
	public void getOrgList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 节点Id
		Integer nodeId = RequestUtils.getIntParameter(request, "nodeId");
		SysOrg sysOrg = new SysOrg();
		sysOrg.setFirstResult(firstResult);
		sysOrg.setPageSize(pageSize);
		QueryResult<SysOrg> queryResult = sysOrgService.getOrgList(sysOrg,nodeId);
		ListView<SysOrg> menuListView = new ListView<SysOrg>();
		menuListView.setData(queryResult.getResultList());
		menuListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, menuListView);
	}
	

	/**
	 * @Title: getOrgInfo
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
	@RequestMapping("/getOrgInfo")
	public void getOrgInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 基准组织Id
		Integer orgId = RequestUtils.getIntParameter(request, "orgId");
		List<SysOrg> resultList = sysOrgService.getOrgInfo(orgId);
		String[] col = { "ID", "parentId", "orgCode", "orgName", "type", "attr", "statue",
				"createTime"};
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
	@RequestMapping(value = "/saveOrgInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveMenuInfo(SysOrg entity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (entity.getId() == -1) {// ID为-1代表新增
			// 根据编码获取数据
			SysOrg checkCode = sysOrgService.getByProerties("orgCode", entity.getOrgCode());
			if (null == checkCode) {//编码不重复，则直接新增
				entity.setCreateTime(new Date());
				sysOrgService.persist(entity);
				entity.setSuccess(true);
			} else {
				entity.setSuccess(false);
				entity.setMessage("保存失败，编码已存在，请重新输入！");
			}
		} else {// 修改
			// 根据ID获取原数据
			SysOrg oldObj = sysOrgService.getByProerties("id", entity.getId());
			// 根据新修改的编码去数据库查询数据
			SysOrg checkCode = sysOrgService.getByProerties("orgCode", entity.getOrgCode());
			if ((!oldObj.getOrgCode().equals(entity.getOrgCode())) && null != checkCode) {// 编码被修改
				entity.setSuccess(false);
				entity.setMessage("保存失败，编码已存在，请重新输入！");
			} else {
				entity.setCreateTime(oldObj.getCreateTime());
				sysOrgService.merge(entity);
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
	@RequestMapping(value = "/delOrgByIds", method = { RequestMethod.POST, RequestMethod.GET })
	public void delOrgByIds(SysOrg entity, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ids") Long[] ids) throws IOException {
		sysOrgService.delOrgByIds(entity, ids);
		writeJSON(response, entity);
	}
}
