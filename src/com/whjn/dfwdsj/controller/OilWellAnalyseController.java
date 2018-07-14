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
	 * @param @param
	 *            km
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author Chen Cai
	 * @throws @date
	 *             2018年6月28日 上午11:23:45
	 * @version V1.0
	 */
	@RequestMapping("/getTotalList")
	public void getTotalList(@RequestParam("km") String[] km, @RequestParam("qryNames") String[] qryNames,
			@RequestParam("params") String[] params, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取当前用户信息
		SysUser user = WebUtil.getCurrUserInfo(request);
		// 根据用户信息获取当前用户角色信息
		// List userRoleType = sysRoleTypeService.getUserRoleType(user.getId());
		String[] col = { "dw", // 单位
				"skhd", // 射开厚度
				"yxhd", // 有效厚度
				"zj", // 直井
				"xj", // 斜井
				"a", // 小层厚度小于等于3m
				"b", // 小层大于3m小于等于5m
				"c", // 小层大于5m
				"d", // 小层深度小于等于500m
				"e", // 小层深度大于500m小于等于1000m
				"f", // 小层深度大于1000m小于等于1500m
				"g", // 小层深度大于1500m小于等于2000m
				"h"// 小层深度大于2000m
		};
		QueryResult queryResult = oilWellAnalyseService.getTotalList(col, km, user, params, qryNames);
		String result = fillListJsonString(col, km, queryResult);
		writeJSON(response, result);
	}

	/**
	 * @Title: fillListJsonString
	 * @Description:处理井矿统计信息的汇总数据
	 * @param @param
	 *            col
	 * @param @param
	 *            km
	 * @param @param
	 *            queryResult
	 * @param @return
	 * @return String
	 * @author Chen Cai
	 * @throws @date
	 *             2018年6月28日 上午11:23:41
	 * @version V1.0
	 */
	private String fillListJsonString(String[] col, String[] km, QueryResult queryResult) {
		StringBuffer sbf = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		sbf.append("{\"totalRecord\":").append(queryResult.getTotalCount()).append(",\"data\":[");
		List list = queryResult.getResultList();
		for (int j = 0; j < queryResult.getTotalCount(); j++) {
			sbf.append("{");
			if(j == queryResult.getTotalCount()-1) { //汇总行
				List countList =  (List) list.get(j);
				int i = 0;
				for (i = 0; i < col.length; i++) {
					sbf.append("\"").append(col[i]).append("\"").append(":").append("\"").append(countList.get(i)).append("\"")
							.append(",");
				}
				for (int k = 0; k < km.length; k++) {// 孔密信息
					sbf.append("\"").append(km[k] + "zj").append("\"").append(":").append("\"").append(countList.get((++i) + k))
							.append("\"").append(",");
					sbf.append("\"").append(km[k] + "zs").append("\"").append(":").append("\"").append(countList.get((i) + k))
							.append("\"").append(",");
				}
				temp.append(sbf.substring(0, sbf.lastIndexOf(",")));
				sbf.setLength(0);
				sbf.append(temp);
				temp.setLength(0);
				sbf.append("}");
			} else {
				Object[] obj = (Object[]) list.get(j);
				int i = 0;
				for (i = 0; i < col.length; i++) {
					sbf.append("\"").append(col[i]).append("\"").append(":").append("\"").append(obj[i]).append("\"")
							.append(",");
				}
				for (int k = 0; k < km.length; k++) {// 孔密信息
					sbf.append("\"").append(km[k] + "zj").append("\"").append(":").append("\"").append(obj[(++i) + k])
							.append("\"").append(",");
					sbf.append("\"").append(km[k] + "zs").append("\"").append(":").append("\"").append(obj[(i) + k])
							.append("\"").append(",");
				}
				temp.append(sbf.substring(0, sbf.lastIndexOf(",")));
				sbf.setLength(0);
				sbf.append(temp);
				temp.setLength(0);
				sbf.append("},");
			}
		}
		sbf.append("]}");
		return sbf.toString();
	}

}
