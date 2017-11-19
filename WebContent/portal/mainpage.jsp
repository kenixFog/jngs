<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1" />
	<title>西安物华巨能科研管理系统</title>
    <jsp:include flush="true" page="/common/loader.jsp" />
  	<script type="text/javascript" src="<%=request.getContextPath()%>/portal/main.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/portal/tree.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/portal/tablePanel.js"></script>
    <script type="text/javascript">
		var appName = '科研管理平台';
    </script>
</head>
<body>
    <span id="app-msg" style="display:none;"></span>
</body>
</html>
