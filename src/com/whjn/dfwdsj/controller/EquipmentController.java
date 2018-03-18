package com.whjn.dfwdsj.controller;



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

import com.whjn.common.base.ListView;
import com.whjn.common.base.QueryResult;
import com.whjn.common.controller.BaseController;
import com.whjn.common.util.RequestUtils;
import com.whjn.dfwdsj.model.po.EquipmentType;
import com.whjn.dfwdsj.service.EquipmentTypeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/dfwdsj/equipment")
public class EquipmentController extends BaseController {

	@Resource
	private EquipmentTypeService equipmentTypeService;
	
	@RequestMapping("/getEquipmentType")
	public void getEquipmentType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long parentId = Long.parseLong(request.getParameter("parentId"));
		List<EquipmentType> resultList = equipmentTypeService.getEquipmentType(parentId);
		writeJSON(response, resultList);
	}
	
	@RequestMapping("/getEquipmentTypeList")
	public void getEquipmentTypeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 节点Id
		Integer nodeId = RequestUtils.getIntParameter(request, "nodeId");
		EquipmentType equipmentType = new EquipmentType();
		equipmentType.setFirstResult(firstResult);
		equipmentType.setPageSize(pageSize);
		QueryResult<EquipmentType> queryResult = equipmentTypeService.getEquipmentTypeList(equipmentType, nodeId);
		ListView<EquipmentType> typeListView = new ListView<EquipmentType>();
		typeListView.setData(queryResult.getResultList());
		typeListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, typeListView);
	}
	
	/**
	* @Title: saveEquipmentType 
	* @Description: 保存器材类型
	* @param @param equipmentType
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年3月15日 下午2:52:55 
	* @version V1.0
	 */
	@RequestMapping("/saveEquipmentType")
	public void saveEquipmentType(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String obj = request.getParameter("paramArray");
		//获取前台传入的要保存或修改的参数
		JSONArray paramArray = JSONArray.fromObject(obj);
		for(int i = 0; i < paramArray.size(); i++) {//循环遍历每一条数据
			JSONObject paramObj = paramArray.getJSONObject(i);
			// ID为空时新增记录，否则修改记录
			if (paramObj.get("id") == null || "".equals(paramObj.get("id"))) {//新增
				
				EquipmentType checkCode = equipmentTypeService.getByProerties("typeCode", paramObj.get("typeCode").toString());
				if(null == checkCode){
					EquipmentType equipmentType = new EquipmentType(
							paramObj.get("typeName").toString(),
							paramObj.get("typeCode").toString(),
							Short.valueOf(paramObj.get("isLeaf").toString()).shortValue(),
							Long.valueOf(paramObj.get("parentId").toString()).longValue(),
							new Date(),
							new Date());
					equipmentTypeService.persist(equipmentType);
					result.put("success", true);
				}else {
					result.put("success", false);
					result.put("message", "保存失败，编码["+paramObj.get("typeCode")+"]已存在，请重新输入！");
					break;
				}
			} else {//更新
				// 根据ID获取原数据
				System.out.println(paramObj.get("id").getClass().getTypeName());
				EquipmentType oldObj = equipmentTypeService.getByProerties("id", paramObj.get("id"));
				// 根据新修改的编码去数据库查询数据
				EquipmentType checkCode = equipmentTypeService.getByProerties("typeCode", paramObj.get("typeCode"));
				if ((!oldObj.getTypeCode().equals(paramObj.get("typeCode"))) && null != checkCode) {// 编码被修改
					result.put("success", false);
					result.put("message", "保存失败，编码["+paramObj.get("typeCode")+"]已存在，请重新输入！");
					break;
				} else {
					EquipmentType equipmentType = new EquipmentType(
							Integer.valueOf(paramObj.get("id").toString()).intValue(),
							paramObj.get("typeName").toString(),
							paramObj.get("typeCode").toString(),
							Short.valueOf(paramObj.get("isLeaf").toString()).shortValue(),
							Long.valueOf(paramObj.get("parentId").toString()).longValue(),
							oldObj.getCreateTime(),
							new Date());
					equipmentTypeService.merge(equipmentType);
					result.put("success", true);
				}
			}
		}
		writeJSON(response, result);
	}
	
}
