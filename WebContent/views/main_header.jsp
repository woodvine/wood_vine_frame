<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp" %>
<% 
     
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>Welcome to WoodVine</title>
	<link rel="stylesheet" href="<%=basePath%>resource/css/style.css" type="text/css" media="all" />
	<script type="text/javascript" src="<%=basePath%>resource/js/DateFormat.js"></script>
	<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
</head>
<script type="text/javascript">
	var authoritys = ${authoritys};
	var path = '<%=basePath%>';
	var user = ${currentUser};
	var userName = '';
	$(document).ready(function(){
		if(user==null||user.userName==null||user.userName==''){
			userName = '新朋友';
		}else{
			//显示用户操作
			userName = user.userName;
		}
		var titleStr = userName+' 您好，今天是' + getSystemcnLongDate();
		$("#systemmessage").html(titleStr);
		loadMenus(authoritys);
	});
	
	function loadMenus(authoritys){
		//初始化菜单
		var categoryView =  $("#menusUl");
		$("#menusUl >li").remove();
		
		//遍历拼接到menusUl
		var str = "";
		for(var i = 0;i<authoritys.length;i++){
			var authority = authoritys[i];
			str+=formatMenu(authority,i==0);
		}
		categoryView.append(str);
	}
	
	//<li><a href="#" class="active"><span>Dashboard</span></a></li>
	function formatMenu(authority,isSelected){
		var id = authority.id;
		var str = "<li><a id='"+id+"' href='javascript:changeMenu("+id+",\""+authority.authorityUrl+"\")'";
		if(isSelected){
			str += " class='active'";
			//设置操作区域的菜单
			window.parent.document.getElementById("container").
				src=path+authority.authorityUrl;
		}
		str+="><span>"+authority.authorityName+"</span></a></li>";
		return str;
	}
	
	function changeMenu(id,url){
		//设置操作区域的菜单
		window.parent.document.getElementById("container").
			src=path+url;
		//移除原选中样式
		$("#menusUl >li >a").removeClass()
		//重置选中菜单的样式
		$("#"+id).addClass("active");
	}
	
	function login(){
		window.location.href = "views/login.jsp";
	}
</script>
<body>
<!-- Header -->
<div id="header">
	<div class="shell">
		<!-- Logo + Top Nav -->
		<div id="top">
			<h1>WoodVine——爱上生活,简单幸福！</h1>
			<div id="top-navigation">
				<strong id="systemmessage"></strong>
					<span>|</span>
					<a href="#">修改密码</a>
					<span>|</span>
					<a href="#">头像设置</a>
					<span>|</span>
					<a href="#">退出</a>
					<span>|</span>
					<a href="javascript:login();">登陆</a>
			</div>
		</div>
		<!-- End Logo + Top Nav -->
		
		<!-- Main Nav -->
		<div id="navigation">
			<ul id="menusUl">
			</ul>
		</div>
		<!-- End Main Nav -->
	</div>
</div>
<!-- End Header -->
	
</body>
</html>