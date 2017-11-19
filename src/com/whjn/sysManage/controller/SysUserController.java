package com.whjn.sysManage.controller;


import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whjn.common.controller.BaseController;
import com.whjn.sysManage.model.SysUser;
import com.whjn.sysManage.service.SysUserService;




@Controller
@RequestMapping("/sys/user")
public class SysUserController extends BaseController<SysUser>{

	@Resource
	private SysUserService sysUserService;
	
}
