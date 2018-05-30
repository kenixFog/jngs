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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.whjn.common.base.ListView;
import com.whjn.common.base.QueryResult;
import com.whjn.common.controller.BaseController;
import com.whjn.common.framework.web.WebUtil;
import com.whjn.common.util.JsonUtil;
import com.whjn.common.util.RequestUtils;
import com.whjn.dfwdsj.model.po.Equipment;
import com.whjn.dfwdsj.model.po.EquipmentField;
import com.whjn.dfwdsj.model.po.EquipmentType;
import com.whjn.dfwdsj.service.EquipmentFieldService;
import com.whjn.dfwdsj.service.EquipmentService;
import com.whjn.dfwdsj.service.EquipmentTypeService;
import com.whjn.sysManage.model.po.SysUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/dfwdsj/equipment")
public class EquipmentController extends BaseController {

	@Resource
	private EquipmentTypeService equipmentTypeService;

	@Resource
	private EquipmentFieldService equipmentFieldService;
	
	@Resource
	private EquipmentService equipmentService;

	/**
	 * 
	* @Title: getEquipmentType 
	* @Description: 
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年3月27日 下午5:19:52 
	* @version V1.0
	 */
	@RequestMapping("/getEquipmentType")
	public void getEquipmentType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 当前节点Id
		long parentId = Long.parseLong(request.getParameter("parentId"));
		// 当前用户所在组织机构根Id
		long orgId = WebUtil.getCurrUserInfo(request).getBaseOrg().getId();
		List<EquipmentType> resultList = equipmentTypeService.getEquipmentType(parentId, orgId);
		writeJSON(response, resultList);
	}

	/**
	 * 
	* @Title: getEquipmentTypeList 
	* @Description: 
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年3月27日 下午5:19:48 
	* @version V1.0
	 */
	@RequestMapping("/getEquipmentTypeList")
	public void getEquipmentTypeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 节点Id
		Integer nodeId = RequestUtils.getIntParameter(request, "nodeId");
		// 当前用户所在组织机构根Id
		long orgId = WebUtil.getCurrUserInfo(request).getBaseOrg().getId();

		EquipmentType equipmentType = new EquipmentType();
		equipmentType.setFirstResult(firstResult);
		equipmentType.setPageSize(pageSize);
		QueryResult<EquipmentType> queryResult = equipmentTypeService.getEquipmentTypeList(equipmentType, nodeId,
				orgId);
		ListView<EquipmentType> typeListView = new ListView<EquipmentType>();
		typeListView.setData(queryResult.getResultList());
		typeListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, typeListView);
	}

	/**
	 * 
	* @Title: getEquipmentFieldList 
	* @Description: 
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年3月27日 下午5:19:45 
	* @version V1.0
	 */
	@RequestMapping("/getEquipmentFieldList")
	public void getEquipmentFieldList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 节点Id
		Integer nodeId = RequestUtils.getIntParameter(request, "nodeId");
		EquipmentField equipmentField = new EquipmentField();
		equipmentField.setFirstResult(firstResult);
		equipmentField.setPageSize(pageSize);
		QueryResult<EquipmentField> queryResult = equipmentFieldService.getEquipmentFieldList(equipmentField, nodeId);
		ListView<EquipmentField> typeListView = new ListView<EquipmentField>();
		typeListView.setData(queryResult.getResultList());
		typeListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, typeListView);
	}
	
	/**
	 * 
	* @Title: getEquipments 
	* @Description: 
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年3月27日 下午5:19:41 
	* @version V1.0
	 */
	@RequestMapping("/getEquipments")
	public void getEquipments(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "";
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 节点Id
		Integer nodeId = RequestUtils.getIntParameter(request, "nodeId");
		// 字段
		String[] fields = RequestUtils.getStringParameters(request, "fields");
		ListView typeListView = new ListView();
		if(fields.length>0) {
			List list = equipmentService.getEquipments(firstResult, pageSize, nodeId, fields);
			result = JsonUtil.fillGridList(fields, list);
		}
		writeJSON(response, result);
	}

	/**
	 * @Title: saveEquipmentType
	 * @Description: 保存器材类型
	 * @param @param
	 *            equipmentType
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author Chen Cai
	 * @throws @date
	 *             2018年3月15日 下午2:52:55
	 * @version V1.0
	 */
	@RequestMapping("/saveEquipmentType")
	public void saveEquipmentType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		String obj = request.getParameter("paramArray");
		// 获取前台传入的要保存或修改的参数
		JSONArray paramArray = JSONArray.fromObject(obj);
		for (int i = 0; i < paramArray.size(); i++) {// 循环遍历每一条数据
			JSONObject paramObj = paramArray.getJSONObject(i);
			// ID为空时新增记录，否则修改记录
			if (paramObj.get("id") == null || "".equals(paramObj.get("id"))) {// 新增
				// 当前节点，当前code的字段
				String[] typeCode = { "parentId", "typeCode" };
				// 当前节点和当前code的值
				Object[] typeCodeValue = { nodeId, paramObj.get("typeCode") };
				// 当前节点，当前name的字段
				String[] typeName = { "parentId", "typeName" };
				// 当前节点，当前name的值
				Object[] typeNameValue = { nodeId, paramObj.get("typeName") };
				// 获取当前节点下，是否存已经存在要保存的code
				EquipmentType checkCode = equipmentTypeService.getByProerties(typeCode, typeCodeValue);
				// 获取当前节点下，是否存已经存在要保存的name
				EquipmentType checkName = equipmentTypeService.getByProerties(typeName, typeNameValue);

				if (null == checkCode && null == checkName) {
					// 当前用户
					SysUser user = WebUtil.getCurrUserInfo(request);

					EquipmentType equipmentType = new EquipmentType(paramObj.get("typeName").toString(),
							paramObj.get("typeCode").toString(),
							Short.valueOf(paramObj.get("isLeaf").toString()).shortValue(), nodeId, user.getBaseOrg(),
							user, user, new Date(), new Date());
					equipmentTypeService.persist(equipmentType);
					result.put("success", true);
				} else {
					result.put("success", false);
					if (null != checkCode) {
						result.put("message", "保存失败，编码[" + paramObj.get("typeCode") + "]已存在，请重新输入！");
					}
					if (null != checkName) {
						result.put("message", "保存失败，名称[" + paramObj.get("typeName") + "]已存在，请重新输入！");
					}
					break;
				}
			} else {// 更新
				System.out.println(paramObj.get("id").getClass().getTypeName());
				// 根据ID获取原数据
				EquipmentType oldObj = equipmentTypeService.getByProerties("id", paramObj.get("id"));
				// 当前节点，当前code的字段
				String[] typeCode = { "parentId", "typeCode" };
				// 当前节点和当前code的值
				Object[] typeCodeValue = { nodeId, paramObj.get("typeCode") };
				// 当前节点，当前name的字段
				String[] typeName = { "parentId", "typeName" };
				// 当前节点，当前name的值
				Object[] typeNameValue = { nodeId, paramObj.get("typeName") };
				// 获取当前节点下，是否存已经存在要保存的code
				EquipmentType checkCode = equipmentTypeService.getByProerties(typeCode, typeCodeValue);
				// 获取当前节点下，是否存已经存在要保存的name
				EquipmentType checkName = equipmentTypeService.getByProerties(typeName, typeNameValue);

				if((!oldObj.getTypeCode().equals(paramObj.get("typeCode")))&& null != checkCode){
					result.put("message", "保存失败，编码[" + paramObj.get("typeCode") + "]已存在，请重新输入！");
					break;
				} else if((!oldObj.getTypeName().equals(paramObj.get("typeName"))) && null != checkName){
					result.put("message", "保存失败，名称[" + paramObj.get("typeName") + "]已存在，请重新输入！");
					break;
				} else {//保存数据
					SysUser user = WebUtil.getCurrUserInfo(request);
					EquipmentType equipmentType = new EquipmentType(
							oldObj.getId(),
							paramObj.get("typeName").toString(), 
							paramObj.get("typeCode").toString(),
							Short.valueOf(paramObj.get("isLeaf").toString()).shortValue(),
							nodeId,
							oldObj.getSysOrg(),
							oldObj.getCreateUser(),
							user,
							oldObj.getCreateTime(),
							new Date());
					equipmentTypeService.merge(equipmentType);
					result.put("success", true);
				}
			}
		}
		writeJSON(response, result);
	}

	/**
	 * @Title: saveEquipmentField
	 * @Description: 保存器材类型字段
	 * @param @param
	 *            equipmentType
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author Chen Cai
	 * @throws @date
	 *             2018年3月15日 下午2:52:55
	 * @version V1.0
	 */
	@RequestMapping("/saveEquipmentField")
	public void saveEquipmentField(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		String obj = request.getParameter("paramArray");
		// 获取前台传入的要保存或修改的参数
		JSONArray paramArray = JSONArray.fromObject(obj);
		for (int i = 0; i < paramArray.size(); i++) {// 循环遍历每一条数据
			JSONObject paramObj = paramArray.getJSONObject(i);
			// ID为空时新增记录，否则修改记录
			if (paramObj.get("id") == null || "".equals(paramObj.get("id"))) {// 新增
				EquipmentField checkCode = equipmentFieldService.getByProerties("fieldCode", paramObj.get("fieldCode"),nodeId);
				EquipmentField checkName = equipmentFieldService.getByProerties("fieldName", paramObj.get("fieldName"),nodeId);

				if (null == checkCode && null == checkName) {
					EquipmentType equipmentType = equipmentTypeService.getByProerties("id",nodeId);
					EquipmentField equipmentField = new EquipmentField(equipmentType,
							  paramObj.get("fieldName").toString(), paramObj.get("fieldCode").toString(),
							paramObj.get("fieldType").toString(),
							Integer.valueOf(paramObj.get("fieldLength").toString()).intValue(),
							paramObj.get("fieldContent").toString());
					equipmentFieldService.persist(equipmentField);
					result.put("success", true);
				} else {
					result.put("success", false);
					if (null != checkCode) {
						result.put("message", "保存失败，编码[" + paramObj.get("fieldCode") + "]已存在，请重新输入！");
					}
					if (null != checkName) {
						result.put("message", "保存失败，名称[" + paramObj.get("fieldName") + "]已存在，请重新输入！");
					}
					break;
				}
			} else {// 更新
				// 根据ID获取原数据
				System.out.println(paramObj.get("id").getClass().getTypeName());
				Long id = Long.parseLong(paramObj.get("id").toString());
				EquipmentField oldObj = equipmentFieldService.getByProerties("id",id);
				// 根据新修改的编码去数据库查询数据
				EquipmentField checkCode = equipmentFieldService.getByProerties("fieldCode", paramObj.get("fieldCode"),nodeId);
				EquipmentField checkName = equipmentFieldService.getByProerties("fieldName", paramObj.get("fieldName"),nodeId);
				if ((!oldObj.getFieldCode().equals(paramObj.get("fieldCode"))) && null != checkCode
						&& null != checkName) {// 编码被修改
					result.put("success", false);
					if (null != checkCode) {
						result.put("message", "保存失败，编码[" + paramObj.get("fieldCode") + "]已存在，请重新输入！");
					}
					if (null != checkName) {
						result.put("message", "保存失败，名称[" + paramObj.get("fieldName") + "]已存在，请重新输入！");
					}
					break;
				} else {
					EquipmentType equipmentType = equipmentTypeService.getByProerties("id",nodeId);
					EquipmentField equipmentField = new EquipmentField(
							Integer.valueOf(paramObj.get("id").toString()).intValue(), equipmentType,
							paramObj.get("fieldName").toString(), paramObj.get("fieldCode").toString(),
							paramObj.get("fieldType").toString(),
							Integer.valueOf(paramObj.get("fieldLength").toString()).intValue(),
							paramObj.get("fieldContent").toString());
					equipmentFieldService.merge(equipmentField);
					//旧编码
					String oldFileCode = oldObj.getFieldCode();
					//新编码
					String newFiledCode = paramObj.get("fieldCode").toString();
					//更新器材数据表中对应的字段编码
					equipmentService.updateFieldCode(oldFileCode,newFiledCode,nodeId);
					result.put("success", true);
				}
			}
		}
		writeJSON(response, result);
	}

	
	
	/**
	 * 
	* @Title: getEquipmentFields 
	* @Description: 
	* @param @param request
	* @param @param response
	* @param @throws Exception  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年3月27日 下午2:31:33 
	* @version V1.0
	 */
	@RequestMapping("/getEquipmentFields")
	public void getEquipmentFields(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Map<String, Object> result = new HashMap<String, Object>();
		int typeId = Integer.parseInt(request.getParameter("typeId"));
		StringBuffer sbf = new StringBuffer();
		sbf.append("{success:true,data:{");
		List<EquipmentField> list = equipmentFieldService.getEquipmentFields(typeId);
		sbf.append("'fieldsInfo':[");
		getFieldTypeJSONArray(list,sbf);
		writeJSON(response, sbf.append("]}}").toString());
	}
	
	/**
	* @Title: delEquipmentType 
	* @Description: 删除器材类型
	* @param @param entity
	* @param @param request
	* @param @param response
	* @param @param ids
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年5月29日 上午9:13:54 
	* @version V1.0
	 */
	@RequestMapping(value = "/delEquipmentType", method = { RequestMethod.POST, RequestMethod.GET })
	public void delEquipmentType(EquipmentType entity, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("data") int[] ids) throws IOException {
		boolean result = false;
		for(int i = 0 ;i<ids.length;i++) {
			List<EquipmentField> list = equipmentFieldService.getEquipmentFieldList(ids[i]);
			if(list.size()>0 ) {
				entity.setMessage("删除失败，所选节点ID为【"+ids[i]+"】下存在器材分类属性！");
				entity.setSuccess(result);
				break;
			} else {
				equipmentTypeService.delEquipmentType(entity, ids[i]);
			}
		}
		writeJSON(response, entity);
	}
	
	
	/**
	* @Title: delEquipmentField
	* @Description: 删除器材类型字段
	* @param @param entity
	* @param @param request
	* @param @param response
	* @param @param ids
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年5月29日 上午9:13:54 
	* @version V1.0
	 */
	@RequestMapping(value = "/delEquipmentField", method = { RequestMethod.POST, RequestMethod.GET })
	public void delEquipmentField(EquipmentField entity, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("data") String[] fieldCodes) throws IOException {
		//节点类型（当前选中的器材分类）
		int typeId = Integer.parseInt(request.getParameter("typeId"));
		equipmentFieldService.delEquipmentField(entity,fieldCodes,typeId);
		writeJSON(response, entity);
	}
	
	/**
	 * 
	* @Title: delEquipment 
	* @Description: 删除器材
	* @param @param entity
	* @param @param request
	* @param @param response
	* @param @param ids
	* @param @throws IOException  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年5月29日 下午2:52:32 
	* @version V1.0
	 */
	@RequestMapping(value = "/delEquipment", method = { RequestMethod.POST, RequestMethod.GET })
	public void delEquipment(Equipment entity, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ids") Long[] ids) throws IOException {
		int typeId = Integer.parseInt(request.getParameter("typeId"));
		boolean result = equipmentService.delEquipment(ids, typeId);
		entity.setSuccess(result);
		if(!result) {//删除失败
			entity.setMessage("删除失败，请与系统开发人员联系！");
		}
		writeJSON(response, entity);
	}
	
	
	
	
	/**
	 * 
	* @Title: getFieldTypeJSONArray 
	* @Description: 拼接器材分类字段（[编码,名称,类型,长度,默认值]）
	* @param @param FieldEntryList
	* @param @param sbf  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2018年3月27日 下午3:46:29 
	* @version V1.0
	 */
	private static void getFieldTypeJSONArray(List<EquipmentField> FieldEntryList, StringBuffer sbf) {
		boolean b = false;
		if (FieldEntryList.size() == 0)
			sbf.append("[]");
		else {
			for (int i = 0; i < FieldEntryList.size(); i++) {
				EquipmentField de = (EquipmentField) FieldEntryList.get(i);
				if (b)
					sbf.append(",");
				else
					b = true;
				 sbf.append("['").append(de.getFieldCode()).append("','").append(de.getFieldName()).append("','")
				 .append(de.getFieldType()).append("','").append(de.getFieldLength()).append("','")
				 .append(de.getFieldContent()).append("']");
			}
		}
	}
}
