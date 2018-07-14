package com.whjn.dfwdsj.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whjn.common.base.ListView;
import com.whjn.common.base.QueryResult;
import com.whjn.common.controller.BaseController;
import com.whjn.common.framework.web.WebUtil;
import com.whjn.common.util.JsonUtil;
import com.whjn.common.util.RequestUtils;
import com.whjn.dfwdsj.model.po.Equipment;
import com.whjn.dfwdsj.model.po.EquipmentType;
import com.whjn.dfwdsj.model.po.OilWell;
import com.whjn.dfwdsj.model.po.OilWellDetail;
import com.whjn.dfwdsj.service.EquipmentService;
import com.whjn.dfwdsj.service.EquipmentTypeService;
import com.whjn.dfwdsj.service.OilWellAnalyseService;
import com.whjn.dfwdsj.service.OilWellDetailService;
import com.whjn.dfwdsj.service.OilWellService;
import com.whjn.sysManage.model.po.SysComCode;
import com.whjn.sysManage.model.po.SysMenu;
import com.whjn.sysManage.model.po.SysUser;
import com.whjn.sysManage.service.SysOrgService;

import net.sf.json.JSONException;


@Controller
@RequestMapping("/dfwdsj/oilWell")
public class OilWellController extends BaseController {

	@Resource
	private OilWellService oilWellService;
	
	@Resource
	private OilWellDetailService oilWellDetailService;
	
	@Resource
	private OilWellAnalyseService oilWellAnalyseService;
	
	@Resource
	private EquipmentTypeService equipmentTypeService;
	
	@Resource
	private EquipmentService equipmentService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	
	
	
	/**
	 * 
	* @Title: getJkjcsjList 
	* @Description: 获取井矿信息列表
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月14日 下午8:10:10 
	* @version V1.0
	 */
	@RequestMapping(value = "/getJkjcsjList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getJkjcsjList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 节点Id
		OilWell oilWell = new OilWell();
		// 获取当前用户信息
		SysUser user = WebUtil.getCurrUserInfo(request);
		oilWell.setFirstResult(firstResult);
		oilWell.setPageSize(pageSize);
		QueryResult<OilWell> queryResult = oilWellService.getJkjcsjList(oilWell,user);
		ListView<OilWell> oilWellListView = new ListView<OilWell>();
		oilWellListView.setData(queryResult.getResultList());
		oilWellListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, oilWellListView);
	}
	
	
	/**
	 * 
	* @Title: saveOilWellInfo 
	* @Description: 保存井矿信息
	* @param @param entity
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月15日 上午9:09:07 
	* @version V1.0
	 */
	@RequestMapping(value = "/saveOilWellInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveOilWellInfo(OilWell entity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (entity.getId() == -1) {// ID为-1代表新增
			//检查井名是否存在
			OilWell checkJm = oilWellService.getByProerties("jm", entity.getJm());
			if (null == checkJm) {// 井名不存在
				//创建日期
				entity.setCjrq(new Date());
				//创建人
				SysUser user = WebUtil.getCurrUserInfo(request);
				entity.setUser(user);
				oilWellService.persist(entity);
				
				entity.setSuccess(true);
			} else {
				entity.setSuccess(false);
				entity.setMessage("保存失败，井名已存在，请重新输入！");
			}
		} else {// 修改
			//根据ID获取原数据
			OilWell oldObj = oilWellService.getByProerties("id", entity.getId());
			//关联创建人
			entity.setUser(oldObj.getUser());
			oilWellService.merge(entity);
			entity.setSuccess(true);
		}
		writeJSON(response, entity);
	}
	
	/**
	 * 
	* @Title: getOilWellInfo 
	* @Description: 获取油井表单信息
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月15日 上午9:10:23 
	* @version V1.0
	 */
	@RequestMapping("/getOilWellInfo")
	public void getOilWellInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 菜单Id
		long id = RequestUtils.getIntParameter(request, "id");
		List<OilWell> resultList = oilWellService.getOilWellInfo(id);
//		String[] col = { "id","jm","yt","qk","skds","zdjx","tggj","tggg","wjfs","csfs","yggg","syyl","tcfs",
//				"ypcs","gjsj","snfs","gjzl","sysj","tgcx","jxfwj","skfw","dxfs","cjrq","user","state",
//				"wjylx","wjymd","fpl","yl","ph","bkfs","skqxw","km","sj","skqlx","skd","yccy","ycco","sjcy","sjco"};
		StringBuffer sbf = new StringBuffer();
		sbf.append("{success:true,data:{");
		if(resultList.size()>0) {
			OilWell oilWell = resultList.get(0);
			sbf.append(oilWell);
		}
		sbf.append("}}");
		writeJSON(response, sbf.toString());
	}
	
	
	/**
	 * 
	* @Title: getOilWellDetailList 
	* @Description: 获取小层信息列表
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月20日 下午4:25:47 
	* @version V1.0
	 */
	@RequestMapping("/getOilWellDetailList")
	public void getOilWellDetailList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 节点Id
		long oilWellId = RequestUtils.getIntParameter(request, "oilWellId");
		OilWellDetail oilWellDetail = new OilWellDetail();
		oilWellDetail.setFirstResult(firstResult);
		oilWellDetail.setPageSize(pageSize);
		QueryResult<OilWellDetail> queryResult = oilWellDetailService.getOilWellDetailList(oilWellDetail, oilWellId);
		ListView<OilWellDetail> oilWellDetailListView = new ListView<OilWellDetail>();
		oilWellDetailListView.setData(queryResult.getResultList());
		oilWellDetailListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, oilWellDetailListView);
	}
	
	
	/**
	 * 
	* @Title: getOilWellDetailInfo 
	* @Description: 获取小层信息
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月25日 上午11:46:26 
	* @version V1.0
	 */
	@RequestMapping("/getOilWellDetailInfo")
	public void getOilWellDetailInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 小层ID
		long oilWellDetaiId = RequestUtils.getLongParameter(request, "id");
		List<OilWellDetail> resultList = oilWellDetailService.getOilWellDetailInfo(oilWellDetaiId);
		//层位，小层编号，层段起始，层段截至，油层倾角，孔数，小层压力
		//地层系数，射开厚度，有效厚度，对应斜度，夹层厚度，有效渗透率，孔隙度
		String[] col = { "id", "cw", "xcbh", "cdqs", "cdjz", "ycqj", "ks", "yl", 
				"xs", "skhd", "yxhd", "dyxd", "jchd", "yxstl", "kxd"};
		String result = JsonUtil.fillListJsonString(col, resultList);
		writeJSON(response, result);
	}

	/**
	 * 
	* @Title: saveOilWellDetailInfo 
	* @Description: 保存小层信息
	* @param @param entity
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年6月25日 上午11:46:39 
	* @version V1.0
	 */
	@RequestMapping(value = "/saveOilWellDetailInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveOilWellDetailInfo(OilWellDetail entity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		long oilWellId = RequestUtils.getLongParameter(request, "oilWellId");
		OilWell oilWell = oilWellService.get(oilWellId);
		entity.setOilWell(oilWell);
		oilWellDetailService.merge(entity);
		entity.setSuccess(true);
		writeJSON(response, entity);
	}
	
}
