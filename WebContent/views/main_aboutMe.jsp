<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>Welcome to WoodVine</title>
	<link rel="stylesheet" href="<%=basePath%>resource/css/style.css" type="text/css" media="all" />
</head>
<body>
<!-- Footer -->
<div id="aboutMe">
	<div class="info">
		<p >
			姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:&nbsp;&nbsp;${name }
		</p>
		<p >
			出生日期:&nbsp;&nbsp;${birthday }
		</p>
		<p>
			籍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贯:&nbsp;&nbsp;${hometown }
		</p>
		<p>
			 毕业院校:&nbsp;&nbsp;${college }
		</p>
		<p>
			 职业技能:&nbsp;&nbsp;${skill }
		</p>
		<p>
			兴趣爱好:&nbsp;&nbsp;${hobby }
		</p>
		<p>
			 联系电话:&nbsp;&nbsp;${telephone }
		</p>
		<p>
			 博客地址:&nbsp;&nbsp;<a href="${blogUrl }">${blogUrl }</a>
		</p>
		<p>
			个人简介:&nbsp;&nbsp;${introduction }
		</p>
		<p>
			欣赏植物:&nbsp;&nbsp;
		</p>
		<p>	<img src="<%=basePath%>resource/images/vine.jpg" width="200" height="200" style="padding-left:100px;"></img>
		</p>
	</div>
</div>
<!-- End Footer -->
	
</body>
</html>