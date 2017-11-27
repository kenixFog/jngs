package com.whjn.common.framework.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.whjn.common.base.Constant;

/**
 * 参数信息，可以从数据库T_SYS_PARAM表中读取 <p>
 * 创建日期：2017-10-17<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author 
 * @version 1.0
 */

public class GlobalData extends Constant implements Serializable{
	/**
	 * 构造函数
	 */
	private GlobalData(){
		
	}
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5437610089150518962L;

	/**
	 * 本参数分组
	 */
	public static final String DEFAULT_PARAM_GROUP = "DEFAULT";

	
	/**
	 * 用于存放所有参数的Map
	 */
	private static Map<String,Map<String,String>> allParamMap = new LinkedHashMap<String,Map<String,String>>();
	
	
	/**
	 * 系统监控是否开启
	 */
	private static Boolean isMonitorSystem = false;
	/**
	 * 提交异常日志管理员ID
	 */
	private static String  ecpAdminId;
	
	/**
	 * 是否开启缓存对象  默认启用缓存 
	 */
	private static boolean oscacheFlag = true ;
	
	
	/**
	 * 耗时较长的sql开关 是否开启
	 */
	private static boolean costLongTimeSqlFlg = true;

	/**
	 * 获取异常日志官员ID
	 * @return 提交异常日志管理员ID
	 */
	public static String getEcpAdminId() {
		return ecpAdminId;
	}

	/**
	 * 设置提交异常日志管理员ID
	 * @param ecpAdminId 提交异常日志管理员ID
	 */
	public static void setEcpAdminId(String ecpAdminId) {
		GlobalData.ecpAdminId = ecpAdminId;
	}

	/**
	 * 不判断Session的URI的正则表达式
	 */
	private static String uncheckUriRegex="(.*/system.do)|(.*/login.*)|(.*/ext/.*)|" +
			"                              (.*/public.*)|(.*/test.jsp)|(.*/services/.*)|(.*/webservice/.*)";	
	/**
	 * 不判断Session的URI的Pattern对象
	 */
	private static Pattern uncheckUriPattern = Pattern.compile(uncheckUriRegex);
	/**
	 * 不能cache的URI的正则表达式
	 */
	private static String nocacheUriRegex = "(.*\\.jsp)|(.*\\.do)";
	/**
	 * 不能cache的URI的Pattern
	 */
	private static Pattern nocacheUriPattern = Pattern.compile(nocacheUriRegex);

	/**
	 *本地登录地址 
	 */
	private static String localLogonUrl = "login.jsp";
	
	
	/**
	 * 域名
	 */
	private static String domainName = "whjn.com.cn";
	
	
	/**
	 * 每页的显示多少条数据
	 */
	private static Integer pageSize = NUM_20;
	

	/**
	 * 获取参数
	 * @param paramGroup 参数所属分组
	 * @param paramCode  参数编码
	 * @return 参数值
	 */
	public static String getParam(String paramGroup,String paramCode){
		
		Map<String,String> paramMap =allParamMap.get(paramGroup);
		if(paramMap == null){
			return null;
		}
		return paramMap.get(paramCode);				
	}
	
	/**
	 * 获取缺省分组的参数值
	 * @param paramCode 参数编码
	 * @return 参数值
	 */
	public static String getParam(String paramCode){
		return getParam(DEFAULT_PARAM_GROUP, paramCode);		
	} 
	
	/**
	 * 设置参数
	 * @param paramGroup 参数所属分组
	 * @param paramCode  参数编码
	 * @param paramValue 参数值
	 */
	public static synchronized void setParam(String paramGroup,String paramCode,String paramValue){
		Map<String,String> paramMap =allParamMap.get(paramGroup);
		if(paramMap == null){
			//如果该分组不存在，那么就创建该分组
			paramMap = new LinkedHashMap<String,String>();
			allParamMap.put(paramGroup, paramMap);
		}
		paramMap.put(paramCode, paramValue);
	}
	


	/**
	 *	获取不进行用户访问控制的uri表达式 
	 * @return	String
	 */
	public static String getUncheckUriRegex() {
		return uncheckUriRegex;
	}

	/**
	 * 设置不需要进行用户访问控制的uri表达式
	 * @param uncheckUriRegex	不判断Session的URI的正则表达式
	 */
	public static void setUncheckUriRegex(String uncheckUriRegex) {
		GlobalData.uncheckUriRegex = uncheckUriRegex;
		//set同时编译,避免每次使用都要编译
		if(org.apache.commons.lang.StringUtils.isBlank(uncheckUriRegex)){
			GlobalData.uncheckUriPattern = null;
		}else{
			GlobalData.uncheckUriPattern = Pattern.compile(uncheckUriRegex);
		}
	}

	/**
	 * 不进行cache的uri表达式
	 * @return	String
	 */
	public static String getNocacheUriRegex() {
		return nocacheUriRegex;
	}

	/**
	 * 设置不进行cache的uri表达式
	 * @param nocacheUriRegex	不进行cache的uri表达式
	 */
	public static void setNocacheUriRegex(String nocacheUriRegex) {
		GlobalData.nocacheUriRegex = nocacheUriRegex;
		//set同时编译,避免每次使用都要编译
		if(org.apache.commons.lang.StringUtils.isBlank(nocacheUriRegex)){
			GlobalData.nocacheUriPattern = null;
		}else{
			GlobalData.nocacheUriPattern = Pattern.compile(nocacheUriRegex);
		}
	}

	/**
	 *	编译后的不需要进行用户登录控制的uri表达式
	 * @return	Pattern
	 */
	public static Pattern getUncheckUriPattern() {
		return uncheckUriPattern;
	}

	/**
	 * 编译后的不进行cache的uri表达式
	 * @return	Pattern
	 */
	public static Pattern getNocacheUriPattern() {
		return nocacheUriPattern;
	}



	/**
	 * 网站域名，默认为whjn.com.cn
	 * @return	String
	 */
	public static String getDomainName() {
		return domainName;
	}

	/**
	 * 设置域名
	 * @param domainName	域名
	 */
	public static void setDomainName(String domainName) {
		GlobalData.domainName = domainName;
	}



	/**
	 * 每页显示多少行数据
	 * @return	Integer
	 */
	public static Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示数据行数
	 * @param pageSize	每页显示数据行数
	 */
	public static void setPageSize(Integer pageSize) {
		GlobalData.pageSize = pageSize;
	}

	/**
	 * 系统监控是否开启
	 * @return 是否
	 */
	public static Boolean getIsMonitorSystem() {
		return isMonitorSystem;
	}

	/**
	 * 系统监控是否开启
	 * @param isMonitorSystem 是否
	 */
	public static void setIsMonitorSystem(Boolean isMonitorSystem) {
		GlobalData.isMonitorSystem = isMonitorSystem;
	}
	
	/**
	 * @return  oscaheFlg 是否
	 */
	public static boolean getOscacheFlag() {
		return oscacheFlag;
	}

	/**
	 * 缓存是否启用
	 * @param oscaheFlg 缓存是否启用开关
	 */
	public static void setOscacheFlag(boolean oscacheFlag) {
		GlobalData.oscacheFlag = oscacheFlag;
	}

	/**
	 * 耗时较长的sql开关
	 * @return 耗时较长的sql开关
	 */
	public static boolean isCostLongTimeSqlFlg() {
		return costLongTimeSqlFlg;
	}

	/**
	 * 耗时较长的sql开关
	 * @param costLongTimeSqlFlg  耗时较长的sql开关
	 */
	public static void setCostLongTimeSqlFlg(boolean costLongTimeSqlFlg) {
		GlobalData.costLongTimeSqlFlg = costLongTimeSqlFlg;
	}

	/** 
	* @return localLogonUrl 
	*/
	public static String getLocalLogonUrl() {
		return localLogonUrl;
	}

	/** 
	* @param localLogonUrl 要设置的 localLogonUrl 
	*/
	public static void setLocalLogonUrl(String localLogonUrl) {
		GlobalData.localLogonUrl = localLogonUrl;
	}

}
