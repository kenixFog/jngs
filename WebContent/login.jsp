<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1" />
		<title>西安物华巨能科研管理平台</title>
		<jsp:include flush="true" page="/common/loader.jsp" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/jquery/jquery-1.11.0.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/login/main.js"></script>
		<link href="<%=request.getContextPath()%>/resources/css/login/login.css" type="text/css" rel="stylesheet"> 
		<script type="text/javascript">
		</script>
	</head>
	<body style="background:url(/jngs/resources/img/login/web_login_bg.jpg) no-repeat center; background-size: cover;">
		<div class="login">
		    <div class="message">科研管理平台</div>
		    <div id="darkbannerwrap"></div>
		    
		    <form method="post" action="" id="login_id">
				<input name="action" value="login" type="hidden">
				<input name="userName" placeholder="请输入用户名" required id="userName" type="text" value="admin">
				<hr class="hr15">
				<input name="password" placeholder="请输入密码" required id="password" type="password" value="123456">
				<hr class="hr15">
				<input value="登录" style="width:100%;" type="button" onclick="login();">
				<hr class="hr20" id="errorTip">
			</form>
		</div>
	</body>
</html>
