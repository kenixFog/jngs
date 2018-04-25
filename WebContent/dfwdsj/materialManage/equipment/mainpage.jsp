<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1" />
	<jsp:include flush="true" page="/common/loader.jsp" />

<%-- 	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/common/bootstrap/dist/css/bootstrap.css">
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/common/CropperUpload/head/cropper.min.css">
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/common/CropperUpload/head/sitelogo.css">
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/common/font-awesome/css/font-awesome.css">
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/common/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/common/bootstrap/dist/js/bootstrap.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/common/CropperUpload/head/cropper.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/common/CropperUpload/head/sitelogo.js"></script>
	<style type="text/css">
	.avatar-btns button {
		height: 35px;
	}
	</style> --%>
	<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/common/imgUpload/prototype.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/imgUpload/scriptaculous.js?load=builder,dragdrop"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/imgUpload/cropper.js"></script> --%>
	<script type="text/javascript" src="main.js"></script>
	<script type="text/javascript" src="tree.js"></script>
	<script type="text/javascript" src="panel.js"></script>
	<script type="text/javascript" src="entry.js"></script>
	<script type="text/javascript" src="iconImg.js"></script>
</head>
<body>
	<script type="text/javascript">
	(function(){
		var mdlCfg = {
				getCodeCfg : {
					//
					groupCode : ["GLOBAL_YES_NO"],
					codeVar : ["dawdsj.materialManage.equipment.yesOrNoArray"]
				},
				//按钮的隐藏方式，disable（禁用，变灰），hide（禁用，隐藏）
				btnCtrlMode : 'hide'
		};
	    whjn.openModule(dawdsj.materialManage.equipment.main.initMainPanel,null,
	    		mdlCfg);
	})();
	</script>
	<iframe id="exportFrame" name="exportFrame" width="0" height="0"/>
</body>
</html>
