<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="initial-scale=1,user-scalable=no,maximum-scale=1" />
<jsp:include flush="true" page="/common/loader.jsp" />
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript" src="tablePanel.js"></script>
<script type="text/javascript" src="totalPanel.js"></script>
<script type="text/javascript" src="totalChart.js"></script>
<style type="text/css">
.whjn-div{
		width:100%;
		height:100%;
	}
</style>

</head>
<body>
	<script type="text/javascript">
	(function(){
		var mdlCfg = {
				getCodeCfg : {
					//完井方式、传输方式、投产方式、定向方式、完井液类型、布孔方式、相位、孔密
					groupCode : ["JK_KM" ],
					codeVar : ["dfwdsj.tjfx.jkxxtj.KM"]
				},
				btnCtrlMode : 'hide'
		};
	    whjn.openModule(dfwdsj.tjfx.jkxxtj.main.initMainPanel,null,mdlCfg);
	})();
	</script>
	<iframe id="exportFrame" name="exportFrame" width="0" height="0" />
</body>
</html>
