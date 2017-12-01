package com.whjn.sysManage.controller;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whjn.common.controller.BaseController;
import com.whjn.sysManage.model.SysComCode;
import com.whjn.sysManage.model.SysOrg;
import com.whjn.sysManage.service.SysOrgService;




@Controller
@RequestMapping("/sys/org")
public class SysOrgController extends BaseController<SysOrg>{

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
		List<SysOrg> resultList = sysOrgService.getOrgTreeByParentId(parentId);
		writeJSON(response, resultList);
	}
	
}
