<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1" />
	<jsp:include flush="true" page="/common/loader.jsp" />
	<script type="text/javascript" src="main.js"></script>
	<script type="text/javascript" src="tree.js"></script>
	<script type="text/javascript" src="panel.js"></script>
	<script type="text/javascript" src="entry.js"></script>
</head>
<body>
	<script type="text/javascript">
	(function(){
		var mdlCfg = {
				getCodeCfg : {
					//状态
					groupCode : ["GLOBAL_ZT" ],
					codeVar : [ "sysManage.authorityManage.role.statusArray"],
					includeDisabled : false
				},
				//按钮的隐藏方式，disable（禁用，变灰），hide（禁用，隐藏）
				btnCtrlMode : 'hide'
		};
	    whjn.openModule(sysManage.authorityManage.role.main.initMainPanel,null,mdlCfg);
	})();
	</script>
	<iframe id="exportFrame" name="exportFrame" width="0" height="0"/>
</body>
</html>
