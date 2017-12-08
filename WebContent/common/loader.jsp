<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.whjn.common.framework.web.WebUtil"%>
<%@page import="com.whjn.common.framework.domain.GlobalData"%>

<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/common/ext/examples/shared/include-ext.js"></script> --%> 
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ext/bootstart.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ext/locale/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/constant.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/comdlg.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ext/ux/test.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/font-awesome/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/global/whjn.css">

<script type="text/javascript">
/* Ext.namespace("whjn"); */

webContextRoot = "<%=request.getContextPath()%>";
//设置ext扩展插件的路径，实现动态加载
Ext.Loader.setPath("Ext.ux", webContextRoot+"/common/ext/ux");
//定方位定射角 
Ext.Loader.setPath("Dfwdsj.app", webContextRoot+"/dfwdsj");
//系统管理
Ext.Loader.setPath("Sys.app", webContextRoot+"/sysManage");
//图标文件夹
whjn.ATOM_IMG_PATH = webContextRoot + "/resources/img/atom/";

/**
 * 分页常量
 * @type {Number}
 */
whjn.PS=<%=GlobalData.getPageSize()%>;

//存放用户信息，userId，username,realName
var currUser={};
//存放请求参数
if (typeof request=="undefined") request={};
//解析请求参数
<%
if (request.getParameter("nodeId")!=null){
%>
	function analyseRequest(){<%=WebUtil.analyseRequest(request)%>};
	analyseRequest();
<%}%>

//解析当前用户信息
function analyseCurrUser(){<%=WebUtil.analyseCurrUser(request)%>};
analyseCurrUser();

</script>