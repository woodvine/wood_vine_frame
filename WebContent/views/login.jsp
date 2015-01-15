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
	$(document).ready(function() {
		//按下回车键自动登录
		 $("#loginName").focus();
	});
	
	function login(){
		var name = $("#loginName").val();
		var pwd = $("#userPwd").val();
		if(name ==''){
			$("#msg").html("用户名不能为空!");
			return;
		}
		if(pwd ==''){
			$("#msg").html("密码不能为空!");
			return;
		}
		
		//登陆之前设置Cookie信息
		setLoginCookie();
		$("#loginForm").submit();
	}
	
	function register(){
		var path = '<%=basePath%>';
		window.location.href = path+"views/base_user.add.jsp";
	}
	 
	function setLoginCookie() {
		var userName=$("#loginName").val();
		var pas=$("#userPwd").val();
		if ($("#chkbox").attr("checked")) {
			SetCookie("INEServer_username",userName);
			SetCookie("INEServer_password", encrypt(pas,'18_%&A@b'));
		}else{
			SetCookie("INEServer_password", "");
		}
	}
	
	//Cookie相关的函数
	function SetCookie(name, value){
		var Days = 7; 
		var exp  = new Date();
		exp.setTime(exp.getTime() + Days*24*60*60*1000);
		document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	}
	function GetCookie (name){
		var arg = name + "="; 
		var alen = arg.length; 
		var clen = document.cookie.length; 
		var i = 0; 
		while (i < clen) 
		{ 
			var j = i + alen; 
			if (document.cookie.substring(i, j) == arg) 
			
				return getCookieVal (j); 
			i = document.cookie.indexOf(" ", i) + 1; 
			if (i == 0)		 break; 
		} 
		return null; 
	}
	function getCookieVal (offset){
		var endstr = document.cookie.indexOf (";", offset); 
		if (endstr == -1) 
			endstr = document.cookie.length; 
		return unescape(document.cookie.substring(offset, endstr)); 
	} 
	 
	function encrypt(str, pwd) {
		  if(pwd == null || pwd.length <= 0) {
		    alert("Please enter a password with which to encrypt the message.");
		    return null;
		  }
		  var prand = "";
		  for(var i=0; i<pwd.length; i++) {
		    prand += pwd.charCodeAt(i).toString();
		  }
		  var sPos = Math.floor(prand.length / 5);
		  var mult = parseInt(prand.charAt(sPos) + prand.charAt(sPos*2) + prand.charAt(sPos*3) + prand.charAt(sPos*4) + prand.charAt(sPos*5));
		  var incr = Math.ceil(pwd.length / 2);
		  var modu = Math.pow(2, 31) - 1;
		  if(mult < 2) {
		    alert("Algorithm cannot find a suitable hash. Please choose a different password. \nPossible considerations are to choose a more complex or longer password.");
		    return null;
		  }
		  var salt = Math.round(Math.random() * 1000000000) % 100000000;
		  prand += salt;
		  while(prand.length > 10) {
		    prand = (parseInt(prand.substring(0, 10)) + parseInt(prand.substring(10, prand.length))).toString();
		  }
		  prand = (mult * prand + incr) % modu;
		  var enc_chr = "";
		  var enc_str = "";
		  for(var i=0; i<str.length; i++) {
		    enc_chr = parseInt(str.charCodeAt(i) ^ Math.floor((prand / modu) * 255));
		    if(enc_chr < 16) {
		      enc_str += "0" + enc_chr.toString(16);
		    } else enc_str += enc_chr.toString(16);
		    prand = (mult * prand + incr) % modu;
		  }
		  salt = salt.toString(16);
		  while(salt.length < 8)salt = "0" + salt;
		  enc_str += salt;
		  return enc_str;
		}

		function decrypt(str, pwd) {
		  if(str == null || str.length < 8) {
		    alert("A salt value could not be extracted from the encrypted message because it's length is too short. The message cannot be decrypted.");
		    return;
		  }
		  if(pwd == null || pwd.length <= 0) {
		    alert("Please enter a password with which to decrypt the message.");
		    return;
		  }
		  var prand = "";
		  for(var i=0; i<pwd.length; i++) {
		    prand += pwd.charCodeAt(i).toString();
		  }
		  var sPos = Math.floor(prand.length / 5);
		  var mult = parseInt(prand.charAt(sPos) + prand.charAt(sPos*2) + prand.charAt(sPos*3) + prand.charAt(sPos*4) + prand.charAt(sPos*5));
		  var incr = Math.round(pwd.length / 2);
		  var modu = Math.pow(2, 31) - 1;
		  var salt = parseInt(str.substring(str.length - 8, str.length), 16);
		  str = str.substring(0, str.length - 8);
		  prand += salt;
		  while(prand.length > 10) {
		    prand = (parseInt(prand.substring(0, 10)) + parseInt(prand.substring(10, prand.length))).toString();
		  }
		  prand = (mult * prand + incr) % modu;
		  var enc_chr = "";
		  var enc_str = "";
		  for(var i=0; i<str.length; i+=2) {
		    enc_chr = parseInt(parseInt(str.substring(i, i+2), 16) ^ Math.floor((prand / modu) * 255));
		    enc_str += String.fromCharCode(enc_chr);
		    prand = (mult * prand + incr) % modu;
		  }
		  return enc_str;
		}
</script>
	<body onkeydown="if(event.keyCode == 13){login();}">
  		<div class="login_main">
			<!-- Box Head -->
			<div class="box-head">
				<h2>Welcome to woodvine</h2>
			</div>
			<!-- End Box Head -->
			
			<form id="loginForm" action="<%=basePath%>login" method="post">
				<!-- Form -->
				 <ul>
					<li><span style="float:left;">用户名:</span>
					<input id="loginName" name="loginName" type="text" value="" class="field size4" style="margin-left:50px; "/></li>
					<li><span style="float:left;">密&nbsp;&nbsp;码 :</span>
					<input id="userPwd" name="userPwd" type="password" value="" class="field size4" style="margin-left:50px; "/></li>
				    <li style="margin-top:30px;margin-left:50px;">
				        <input id="chkbox" type="checkbox" class="checkbox" value="1">记住密码</input>
   						<div id="msg" style="font-size:12px;color:red; height:15px;text-align:center;">${msg }</div>
   					</li>
					<li>
						<input type="button" class="button" style="margin-left:150px;" value="登陆" onclick="login();"/>
						<input type="button" class="button" style="margin-left:10px;" value="忘记密码" onclick="register();"/>
					</li>
				</ul>
				<!-- End Form -->
			</form>
		</div>
	</body>
</html>