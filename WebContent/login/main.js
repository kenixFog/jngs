function login() {
	var $userName = document.getElementById("userName").value;
	var $password = document.getElementById("password").value;
	if($userName == ""){
		$("#tip").html("请输入用户名");
		return;
	}
	if($password == ""){
		$("#tip").html("请输入密码");
		return;
	}
	
	Ext.Ajax.request({
		url : webContextRoot + "/sys/login",
		method : 'POST',
		async: false,
		params : {
			userName : $userName,
			password : $password
		},
		success : function(response, options) {
			var obj = eval("(" + response.responseText + ")");
			if (obj.result == 'ok') {
				window.location.href= webContextRoot+"/sys/home";
			} else {
				$("#errorTip").html(obj.result);
			}
			
		},
		failure : function() {
			
		}
	});
}