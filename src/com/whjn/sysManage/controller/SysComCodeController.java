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
import com.whjn.common.util.StringUtil;
import com.whjn.sysManage.model.SysComCode;
import com.whjn.sysManage.service.SysComCodeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sys/comCode")
public class SysComCodeController extends BaseController<SysComCode> {

	@Resource
	private SysComCodeService sysComCodeService;

	/**
	 * 
	 * @Title: getComCodeTreeByParentId
	 * @Description:获取公共代码树
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author kenix
	 * @throws @date
	 *             2017年11月20日 下午4:25:10
	 * @version V1.0
	 */
	// 异步树
	@RequestMapping("/getComCodeTreeByParentId")
	public void getComCodeTreeByParentId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long parentId = Long.parseLong(request.getParameter("parentId"));
		List<SysComCode> resultList = sysComCodeService.getComCodeTreeByParentId(parentId);
		writeJSON(response, resultList);
	}

	/**
	 * 
	 * @Title: getComCode
	 * @Description: 获取公共代码列表
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getComCodeList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getComCodeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		// 每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		// 节点Id
		Integer nodeId = RequestUtils.getIntParameter(request, "nodeId");
		SysComCode sysComCode = new SysComCode();
		sysComCode.setFirstResult(firstResult);
		sysComCode.setPageSize(pageSize);
		QueryResult<SysComCode> queryResult = sysComCodeService.getComCodeList(sysComCode, nodeId);
		ListView<SysComCode> comCodeListView = new ListView<SysComCode>();
		comCodeListView.setData(queryResult.getResultList());
		comCodeListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, comCodeListView);
	}

	/**
	 *
	 * @Title: getComCodeInfo
	 * @Description: 获取公共代码详细信息
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @param @throws
	 *            JSONException
	 * @return void
	 * @author kenix
	 * @throws @date
	 *             2017年11月1日 下午3:01:43
	 * @version V1.0
	 * @throws JSONException
	 */
	@RequestMapping("/getComCodeInfo")
	public void getComCodeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 公共代码Id
		Integer comCodeId = RequestUtils.getIntParameter(request, "comCodeId");
		List<SysComCode> resultList = sysComCodeService.getComCodeInfo(comCodeId);
		String[] col = { "ID", "parentId", "code", "name", "isLeaf", "createTime", "lastEditTime", "comments", "type",
				"statue" };
		String result = JsonUtil.fillListJsonString(col, resultList);
		writeJSON(response, result);
	}

	/**
	 * @Title: saveComCodeInfo
	 * @Description: 保存公共代码
	 * @param @param
	 *            entity 公共代码实体
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            IOException
	 * @return void
	 * @author Chen Cai
	 * @throws @date
	 *             2017年11月23日 下午3:51:27
	 * @version V1.0
	 */
	@RequestMapping(value = "/saveComCodeInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveComCodeInfo(SysComCode entity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		SysComCode checkCode = sysComCodeService.getByProerties("code", entity.getCode());
		if (entity.getId() == -1) {// ID为-1代表新增
			if (null == checkCode) {// 编码不存在
				entity.setCreateTime(new Date());
				entity.setLastEditTime(new Date());
				sysComCodeService.persist(entity);
				entity.setSuccess(true);
			} else {
				entity.setSuccess(false);
				entity.setMessage("保存失败，编码已存在，请重新输入！");
			}
		} else {// 修改
			entity.setCreateTime(checkCode.getCreateTime());
			entity.setLastEditTime(new Date());
			sysComCodeService.merge(entity);
			entity.setSuccess(true);
		}
		writeJSON(response, entity);
	}

	/**
	 * @Title: delComCodeByIds
	 * @Description: 删除公共代码
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
	 *             2017年11月23日 下午3:51:53
	 * @version V1.0
	 */
	@RequestMapping(value = "/delComCodeByIds", method = { RequestMethod.POST, RequestMethod.GET })
	public void delComCodeByIds(SysComCode entity, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ids") Long[] ids) throws IOException {
		sysComCodeService.delComCodeByIds(entity, ids);
		writeJSON(response, entity);
	}

	/**
	 * @Title: getCommonCode
	 * @Description: 根据页面传入公共代码的CODE获取对应的值
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @throws
	 *            Exception
	 * @return void
	 * @author kenix
	 * @throws @date
	 *             2017年9月15日 上午9:52:44
	 * @version V1.0
	 */
	@RequestMapping("/getComCode")
	public void getCommonCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取前台传入的公共代码参数
		JSONObject jsonObj = JSONObject.fromObject(request.getParameter("reqData"));
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");
		// 请求初始化公用代码
		if (jsonObj.has("getCodeCfg")) {
			JSONArray groupIds = null, groupCodes = null;
			// 公共代码中是否包含停用状态的数据
			boolean includeDisabled = false;
			JSONObject codeCfgJson = jsonObj.getJSONObject("getCodeCfg");
			// 如果传入的是公共代码的Id，获取ID
			if (codeCfgJson.has("groupId"))
				groupIds = codeCfgJson.getJSONArray("groupId");
			// 如果传入的是公共代码的CODE，获取CODE
			if (codeCfgJson.has("groupCode"))
				groupCodes = codeCfgJson.getJSONArray("groupCode");
			// 如果包含停用状态的数据，则获取includeDisabled
			if (codeCfgJson.has("includeDisabled"))
				includeDisabled = codeCfgJson.getBoolean("includeDisabled");
			sbf.append("codeData:");
			this.getComCodeJSON(sbf, jsonArr2StrArr(groupIds), jsonArr2StrArr(groupCodes), includeDisabled);
			sbf.append("}");
		}
		result.put("success", true);
		result.put("data", sbf.toString());
		writeJSON(response, result);
	}

	/***
	 * 获取公共代码中的数据
	 * 
	 * @param sbf
	 *            字符串
	 * @param groupIds
	 *            公共代码分组ID数组
	 * @param groupCodes
	 *            公共代码分组编码数组
	 * @param includeDisabled
	 *            是否包含停用状态的数据，true为包含，false为不包含；
	 * @throws Exception
	 *             异常
	 */
	private void getComCodeJSON(StringBuffer sbf, String[] groupIds, String[] groupCodes, boolean includeDisabled)
			throws Exception {
		if (groupIds != null) {// 根据公共代码ID获取公共代码
			sbf.append("{");
			for (int i = 0; i < groupIds.length; i++) {
				List<SysComCode> lst = sysComCodeService.getComCodeListByParentId(Long.decode(groupIds[i]), includeDisabled);
				if (i > 0)
					sbf.append(",");
				sbf.append("comCode_").append(Integer.parseInt(groupIds[i])).append(":");
				getComCodeJSONArray(lst, sbf, includeDisabled);
			}
			sbf.append("}");
		} else {// 根据公共代码CODE获取公共代码
			sbf.append("{");
			for (int i = 0; i < groupCodes.length; i++) {
				List<SysComCode> lst = sysComCodeService.getComCodeListByCode(groupCodes[i], includeDisabled);
				if (i > 0)
					sbf.append(",");
				sbf.append("'comCode_").append(groupCodes[i]).append("':");
				getComCodeJSONArray(lst, sbf, includeDisabled);
			}
			sbf.append("}");
		}
	}

	/**
	 * 将公共代码拼接成数组
	 * 
	 * @param codeEntryList
	 *            获取到的公共代码数据集合
	 * @param sbf
	 *            字符串
	 * @param includeDisabled
	 *            是否包含停用状态的数据，true为包含，false为不包含；
	 */
	private static void getComCodeJSONArray(List<SysComCode> codeEntryList, StringBuffer sbf, boolean includeDisabled) {
		sbf.append("[");
		boolean b = false;
		if (codeEntryList.size() == 0)
			sbf.append("[]");
		else {
			for (int i = 0; i < codeEntryList.size(); i++) {
				SysComCode de = (SysComCode) codeEntryList.get(i);
				if (!includeDisabled) {
					// includeDisabled 为false，则过滤不可用状态
					if (SysComCode.STATE_ENABLE != de.getStatue())
						continue;
				}
				if (b)
					sbf.append(",");
				else
					b = true;
				sbf.append("['").append(de.getCode()).append("','").append(de.getName()).append("',")
						.append(SysComCode.STATE_ENABLE == de.getStatue() ? true : false).append("]");
			}
		}
		sbf.append("]");
	}

	/**
	 * 将JSONArray对象转换为String[]对象
	 * 
	 * @param jsonArr
	 * @return String[]
	 */
	private String[] jsonArr2StrArr(JSONArray jsonArr) {
		if (jsonArr == null)
			return null;
		String[] strArr = new String[jsonArr.size()];
		for (int i = 0; i < strArr.length; i++) {
			try {
				strArr[i] = jsonArr.getString(i);
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
		return strArr;
	}

}
