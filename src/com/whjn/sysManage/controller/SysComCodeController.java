package com.whjn.sysManage.controller;

import java.io.IOException;
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
import com.whjn.common.util.JsonUtil;
import com.whjn.common.util.RequestUtils;
import com.whjn.sysManage.model.SysComCode;
import com.whjn.sysManage.service.SysComCodeService;



@Controller
@RequestMapping("/sys/comCode")
public class SysComCodeController extends BaseController<SysComCode> {

	
	@Resource
	private SysComCodeService sysComCodeService;
	
	/**
	 * 
	* @Title: getComCodeTreeByParentId 
	* @Description: 
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年11月20日 下午4:25:10 
	* @version V1.0
	 */
	//异步树
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
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年11月1日 下午3:00:26 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/getComCodeList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getComCodeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//起始页
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		//每页显示条目数
		Integer pageSize = Integer.valueOf(request.getParameter("limit"));
		//节点Id
		Integer nodeId = RequestUtils.getIntParameter(request, "nodeId");
		SysComCode sysComCode = new SysComCode();
		sysComCode.setFirstResult(firstResult);
		sysComCode.setPageSize(pageSize);
		QueryResult<SysComCode> queryResult = sysComCodeService.getComCodeList(sysComCode,nodeId);
		ListView<SysComCode> comCodeListView = new ListView<SysComCode>();
		comCodeListView.setData(queryResult.getResultList());
		comCodeListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, comCodeListView);
	}
	
	
	/**
	*
	* @Title: getComCodeInfo 
	* @Description: 获取公共代码详细信息
	* @param @param request
	* @param @param response
	* @param @throws IOException
	* @param @throws JSONException  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年11月1日 下午3:01:43 
	* @version V1.0
	 * @throws JSONException 
	 */
	@RequestMapping("/getComCodeInfo")
	public void getComCodeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//公共代码Id
		Integer comCodeId= RequestUtils.getIntParameter(request, "comCodeId");
		List<SysComCode> resultList = sysComCodeService.getComCodeInfo(comCodeId);
		String [] col = {"ID", "parentId", "code", "name", "isLeaf", 
				"comments","type","statue"};
		String result = JsonUtil.fillListJsonString(col, resultList);
		writeJSON(response, result);
	}
	
//	
//	/**
//	* @Title: getCommonCode 
//	* @Description: 根据页面传入公共代码的CODE获取对应的值
//	* @param @param request
//	* @param @param response
//	* @param @throws Exception  
//	* @return void    
//	* @author kenix
//	* @throws
//	* @date 2017年9月15日 上午9:52:44 
//	* @version V1.0
//	 */
//	@RequestMapping("/getComCode")
//	public void getCommonCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
////		System.out.println(request.getParameter("reqData"));
////		JSONObject jsonObj=new JSONObject(request.getParameter("reqData"));
////		System.out.println(jsonObj);	
////		StringBuffer sbf=new StringBuffer();
////		boolean needComma=false;
////		//请求初始化公用代码
////		if (jsonObj.has("getCodeCfg")){
////			JSONArray groupIds=null,groupCodes=null;
////			//公共代码中是否包含停用状态的数据
////			boolean includeDisabled = false;
////			JSONObject codeCfgJson=jsonObj.getJSONObject("getCodeCfg");
////			//如果传入的是公共代码的Id，获取ID
////			if (codeCfgJson.has("groupId")) 
////				groupIds=codeCfgJson.getJSONArray("groupId");
////			//如果传入的是公共代码的CODE，获取CODE
////			if (codeCfgJson.has("groupCode")) 
////				groupCodes=codeCfgJson.getJSONArray("groupCode");
////			//如果包含停用状态的数据，则获取includeDisabled
////			if(codeCfgJson.has("includeDisabled")) 
////				includeDisabled = codeCfgJson.getBoolean("includeDisabled");
////			sbf.append("codeData:");
////			this.getComCodeJSON(sbf,jsonArr2StrArr(groupIds),jsonArr2StrArr(groupCodes),includeDisabled);
////			needComma=true;
////		}
////		Map<String, Object> result = new HashMap<String, Object>();
////		result.put("success", true);
////		result.put("result", "test");
////		writeJSON(response, result);
////		return;
//	}
//	
//	
//	/***
//	 * 获取公共代码中的数据
//	 * @param sbf 字符串
//	 * @param groupIds 公共代码分组ID数组
//	 * @param groupCodes 公共代码分组编码数组
//	 * @param includeDisabled 是否包含停用状态的数据，true为包含，false为不包含；
//	 * @throws Exception 异常
//	 */
//	private void getComCodeJSON(StringBuffer sbf,String[] groupIds,String[] groupCodes,
//			boolean includeDisabled) throws Exception{
//		if (groupIds!=null){
//			sbf.append("{");
////			for (int i=0;i<groupIds.length;i++){
////				List<SysComCode> lst = commonService.getComCodeListById(Long.decode(groupIds[i]),includeDisabled);
////				if (i>0) sbf.append(",");
////				sbf.append("comCode_").append(Integer.parseInt(groupIds[i])).append(":");
////				getComCodeJSONArray(lst,sbf,includeDisabled);
////			}
//			sbf.append("}");
//		}else{
//			sbf.append("{");
////			for (int i=0;i<groupCodes.length;i++){
////				List<SysComCode> lst = null;
////				if(groupCodes[i].startsWith("commontable:")){
////					// 从公共数据表中取数
////					String temp = groupCodes[i].substring("commontable:".length());
////					String[] tempArr = StringUtils.split(temp,",");
////					lst = commonService.getCommonTableData(tempArr[0], tempArr[1], tempArr[2]);
////				}else{
////					//从公共代码中取数
////					lst = commonService.getComCodeListByCode(groupCodes[i],includeDisabled);
////				}
////				if (i>0) sbf.append(",");
////				sbf.append("'comCode_").append(groupCodes[i]).append("':");
////				getComCodeJSONArray(lst,sbf,includeDisabled);
////			}
//			sbf.append("}");			
//		}		
//	}
//	
//	
//	/**
//	 * 将公共代码拼接成数组
//	 * @param codeEntryList 获取到的公共代码数据集合
//	 * @param sbf 字符串
//	 * @param includeDisabled 是否包含停用状态的数据，true为包含，false为不包含；
//	 */
//	private static void getComCodeJSONArray(List<SysComCode> codeEntryList,StringBuffer sbf,boolean includeDisabled){
//		sbf.append("[");
//		boolean b=false;
//		if (codeEntryList.size()==0) sbf.append("[]");
//		
//		sbf.append("]");
//	}
//	
//	/**
//	 * 将JSONArray对象转换为String[]对象
//	 * @param jsonArr 
//	 * @return String[]
//	 */
//	private String[] jsonArr2StrArr(JSONArray jsonArr){
//		if (jsonArr==null) return null;
//		String[] strArr=new String[jsonArr.length()];
//		for (int i=0;i<strArr.length;i++){
//			try {
//				strArr[i]=jsonArr.getString(i);
//			} catch (JSONException e) {
//				throw new RuntimeException(e);
//			}
//		}
//		return strArr;
//	}

}
