<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Welcome to WoodVine</title>
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
		<script type='text/javascript' src="<%=basePath%>resource/js/jquery.cookie.js"></script>
	</head>
	<script type="text/javascript">
	var path = '<%=basePath%>';
	$(document).ready(function() {
		//按下回车键自动登录
		 $("#loginName").focus();
		 show_Vcode();
		 $("#change_captcha_img").click(function() {
			 show_Vcode();
		});
	});
	
	function login(){
		var name = $("#loginName").val();
		if(name ==''){
			$("#msg").html("用户名不能为空!");
			$("#loginName").focus();
			return;
		}
		
		var pwd = $("#userPwd").val();
		if(pwd ==''){
			$("#msg").html("密码不能为空!");
			$("#userPwd").focus();
			return;
		}
		
		var inputCode = $("#validateCode").val();
		if(inputCode==''){
			$("#msg").html("验证码不能为空!");
			$("#validateCode").focus();
			return;
		}
		
		$("#loginForm").submit();
	}
	
	//显示验证码
	function show_Vcode() {
		$("#validateCode").val("");
		var img = $("#validateImg");
		img.attr("src", path+"validate_image?rand="+Math.random());
		$("#validateImg").show();
	}
	
	function register(){
		var path = '<%=basePath%>';
		window.location.href = path+"views/base_user.add.jsp";
	}
	 
</script>
	<body onkeydown="if(event.keyCode == 13){login();}">
  			<div class="login_main">
				<!-- Box Head -->
				<div class="box-head">
					<h2>欢迎光临阿木的小站</h2>
				</div>
				<!-- End Box Head -->
				
				<form id="loginForm" action="<%=basePath%>login" method="post">
					<!-- Form -->
					<div class="form">
							<br/>
							<br/>
							<p>
								<label style="float:left;margin-left:100px;">用户名:&nbsp;&nbsp;</label>
								<input style="margin-left:50px; " id="loginName" maxlength="30" name="loginName" type="text" value="" class="field size4" />
							</p>	
							
							<p>
								<label style="float:left;margin-left:100px;">密&nbsp;&nbsp;&nbsp;码:&nbsp;&nbsp;</label>
								<input style="margin-left:50px; " id="userPwd" maxlength="30" name="userPwd" type="password" value="" class="field size4" />
							</p>	
							<p>
								<label style="float:left;margin-left:100px;">验证码:&nbsp;&nbsp;</label>
								<input style="margin-left:50px;" id="validateCode" maxlength="5" name="validateCode" type="text" value="" class="field size2" />
								<img id="validateImg" alt="验证码" style="float:left;margin-left:260px;margin-top:-20px;"></img></li>
								<a style="float:left;margin-left:340px;margin-top:-20px;text-decoration:none;" id="change_captcha_img" href="javascript:show_Vcode();">
									看不清？
								</a>
							</p>	
							<br/>
					</div>
					<div >
						<span id="msg" style="font-size:12px;color:red; height:15px;float:right;margin-right:300px;">${msg }</span>
						<input type="button" class="button" style="margin-left:300px;" value="登陆" onclick="login();"/>
						<input type="button" class="button" style="margin-left:10px;" value="忘记密码"/>
					</div>
					<!-- End Form -->
					
					<!-- Form Buttons -->
					<!-- End Form Buttons -->
				</form>
			</div>
	</body>
</html>