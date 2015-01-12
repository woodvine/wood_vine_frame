<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Welcome to WoodVine</title>
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
	</head>
	<script type="text/javascript">
	function login(){
		var name = $("#loginName").val();
		var pwd = $("#password").val();
		if(name ==''){
			$("#msg").html("用户名不能为空!");
			return;
		}
		if(pwd ==''){
			$("#msg").html("密码不能为空!");
			return;
		}
		
		$("#loginForm").submit();
	}
	
	function register(){
		var path = '<%=basePath%>';
		window.location.href = "base_user.add.jsp";
	}
</script>
	<body>
	   <div class="shell">
	   		<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2>Welcome to woodvine</h2>
					</div>
					<!-- End Box Head -->
					
					<form id="loginForm" action="<%=basePath%>login" method="post">
						<!-- Form -->
						<div class="form">
						   <ul>
								<li style="text-align:center">
									<span style="float:left;">用户名:</span>
									<input id="loginName" name="loginName" type="text" value="admin" class="field size4" style="margin-left:100px; "/>
								</li>
								<li>
									<span style="float:left;">密&nbsp;&nbsp;码 :</span>
									<input id="password" name="userPwd" type="password" value="123456" class="field size4" style="margin-left:100px; "/>
								</li>
								
								<li><input id="rememberPwd" type="checkbox" class="checkbox" value="1">记住密码</input></li>
          						<li><div id="msg" style="font-size:12px;color:red; height:15px;text-align:left;">${msg }</div></li>
							</ul>
						</div>
						<!-- End Form -->
						
						<!-- Form Buttons -->
						<div class="buttons">
							<input type="button" class="button" value="登陆" onclick="login();"/>
							<input type="button" class="button" value="注册" onclick="register();"/>
						</div>
						<!-- End Form Buttons -->
					</form>
				</div>
	   </div>
	</body>
</html>