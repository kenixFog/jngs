package com.whjn.dfwdsj.controller;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whjn.common.base.QueryResult;
import com.whjn.common.controller.BaseController;
import com.whjn.common.framework.web.WebUtil;
import com.whjn.common.util.RequestUtils;
import com.whjn.dfwdsj.model.po.Equipment;
import com.whjn.dfwdsj.service.OilWellAnalyseService;
import com.whjn.dfwdsj.service.OilWellDetailService;
import com.whjn.dfwdsj.service.OilWellService;
import com.whjn.sysManage.model.po.SysUser;
import com.whjn.sysManage.service.SysRoleTypeService;



@Controller
@RequestMapping("/dfwdsj/oilWellAnalyse")
public class OilWellAnalyseController extends BaseController {

	@Resource
	private OilWellService oilWellService;
	
	@Resource
	private OilWellDetailService oilWellDetailService;
	
	@Resource
	private OilWellAnalyseService oilWellAnalyseService;
	
	@Resource
	private SysRoleTypeService sysRoleTypeService;
	
	/**
	* @Title: getTotalList 
	* @Description: 获取统计信息
	* @param @param km
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月28日 上午11:23:45 
	* @version V1.0
	 */
	@RequestMapping("/getTotalList")
	public void getTotalList(@RequestParam("km") String[] km, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取当前用户信息
		SysUser user = WebUtil.getUserSessionInfo();
		//根据用户信息获取当前用户角色信息
		List userRoleType = sysRoleTypeService.getUserRoleType(user.getId());
		
		QueryResult queryResult = oilWellAnalyseService.getTotalList(km,user);
		
		String[] col = {"dw",// 单位
		"skhd",// 射开厚度
		"yxhd",// 有效厚度
		"zj",// 直井
		"xj",// 斜井
		"a",// 小层厚度小于等于3m
		"b",// 小层大于3m小于等于5m
		"c",// 小层大于5m
		"d",// 小层深度小于等于500m
		"e",// 小层深度大于500m小于等于1000m
		"f",// 小层深度大于1000m小于等于1500m
		"g",// 小层深度大于1500m小于等于2000m
		"h"// 小层深度大于2000m
		 };
		String result = fillListJsonString(col,km,queryResult);
		writeJSON(response, result);
	}


	/** 
	* @Title: fillListJsonString 
	* @Description: 
	* @param @param col
	* @param @param km
	* @param @param queryResult
	* @param @return  
	* @return String    
	* @author Chen Cai
	* @throws
	* @date 2018年6月28日 上午11:23:41 
	* @version V1.0   
	*/
	private String fillListJsonString(String[] col, String[] km, QueryResult queryResult) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
