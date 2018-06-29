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
<script type="text/javascript" src="panel.js"></script>
<script type="text/javascript" src="entry.js"></script>
<script type="text/javascript" src="skdTree.js"></script>
<script type="text/javascript" src="skqTree.js"></script>
<script type="text/javascript" src="oilDetailWin.js"></script>
<script type="text/javascript" src="oilDetailEntry.js"></script>
<style type="text/css">
a:link {
	color: blue;
	text-decoration: none;
}

a:visited {
	color: blue;
	text-decoration: none;
}

a:hover {
	color: #999999;
	text-decoration: underline;
}
</style>
</head>
<body>
	<script type="text/javascript">
	(function(){
		var mdlCfg = {
				getCodeCfg : {
					//完井方式、传输方式、投产方式、定向方式、完井液类型、布孔方式、相位、孔密
					groupCode : ["JK_WJFS","JK_CSFS","JK_TCFS","JK_DXFS","JK_WJYLX","JK_BKFS","JK_XW","SKQ_KM" ],
					codeVar : [
						"dfwdsj.jkxx.jbxxwh.WJFS",
						"dfwdsj.jkxx.jbxxwh.CSFS",
						"dfwdsj.jkxx.jbxxwh.TCFS",
						"dfwdsj.jkxx.jbxxwh.DXFS",
						"dfwdsj.jkxx.jbxxwh.WJYLX",
						"dfwdsj.jkxx.jbxxwh.BKFS",
						"dfwdsj.jkxx.jbxxwh.XW",
						"dfwdsj.jkxx.jbxxwh.KM"]
				},
				//按钮的隐藏方式，disable（禁用，变灰），hide（禁用，隐藏）
				btnCtrlMode : 'hide'
		};
	    whjn.openModule(dfwdsj.jkxx.jbxxwh.main.initMainPanel,null,mdlCfg);
	})();
	</script>
	<iframe id="exportFrame" name="exportFrame" width="0" height="0" />
</body>
</html>
