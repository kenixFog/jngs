package com.whjn.sysManage.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whjn.common.controller.BaseController;
import com.whjn.common.framework.web.WebUtil;
import com.whjn.common.util.MD5;
import com.whjn.sysManage.model.po.SysUser;
import com.whjn.sysManage.service.SysUserService;



@Controller
@RequestMapping("/sys")
public class SysLoginController extends BaseController{

	@Resource
	private SysUserService sysUserService;

	/** 
	* @Title: login 
	* @Description:  用户登录
	* @param @param sysUserModel 用户模型
	* @param @param request 用户请求
	* @param @param response 服务器返回值
	* @param @throws IOException IO异常
	* @return void    返回类型
	* @author chenc 
	* @throws
	* @date 2017年8月18日 上午11:05:54 
	* @version V1.0   
	*/
	@RequestMapping("/login")
	public void login(SysUser sysUserModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		String userName = sysUserModel.getUserName();
		System.out.println(userName);
		//获取用户信息
		SysUser sysUser = sysUserService.getByProerties("userName", userName);
		if (sysUser == null ) { // 用户名有误
			result.put("result", "用户名有误");
			System.out.println(1);
			writeJSON(response, result);
		}else if (sysUser.getStatue() == 0) { // 用户名已被禁用
			result.put("result", "用户已被禁用，请与管理员联系");
			System.out.println(2);
			writeJSON(response, result);
		}else if (!sysUser.getPassword().equals(MD5.crypt(sysUserModel.getPassword()))) { // 密码错误
			result.put("result", "密码错误");
			System.out.println(3);
			writeJSON(response, result);
		}
		//设置最后一次登录时间
		sysUser.setLastLoginTime(new Date());
		//更新用户登录时间
		sysUserService.update(sysUser);
		//设置session
		WebUtil.setUserSessionInfo(sysUser,request);
		result.put("result", "ok");
		writeJSON(response, result);
	}

	@RequestMapping("/home")
	public String home(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (WebUtil.getCurrUserInfo(request) == null) {
			System.out.println(123);
//			response.sendRedirect(request.getSession().getServletContext().getContextPath()+GlobalData.getLocalLogonUrl());
			return "";
		} else {
			return "mainpage";
		}
	}
	
	
//	@RequestMapping("/out")
//	public String out(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		if (WebUtil.getCurrUserInfo(request) == null) {
//			return "";
//		} else {
//			return "mainpage";
//		}
//	}
}
