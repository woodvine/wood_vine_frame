<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/views/base_include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
</head>
<body>

<div data-role="page" id="pageone">
   <div data-role="header">
	    <div data-role="navbar">
		    <ul>
			    <li><a href="#pageone" class="ui-btn-active ui-state-persist" data-icon="home">并发概念</a></li>
			    <li><a href="views/mobile_dailyEnglish.jsp"  data-icon="search">每日英语</a></li>
			    <li><a href="views/mobile_aboutMe.jsp"  data-icon="info">关于阿木</a></li>
			    <li><a href="views/mobile_contactMe.jsp" data-icon="grid" >联系我</a></li>
		    </ul>
	    </div>
  </div>
  
  <div data-role="content">
      <ul data-role="listview">
  		<li>锁的劣势
  			<ul>
  			   <li>
  			      <a href="#pageone" data-icon="back">返回</a>
  			   </li>
  			   <li>
  			      <span>锁是一种悲观的并发策略，采用独占访问的方式</span>
  			   </li>
  			</ul>
  		</li>
  		<li>硬件对并发的支持
  			<ul>
  			   <li>
  			      <a href="#pageone" data-icon="back">返回</a>
  			   </li>
  			   <li>
  			      <span>锁是一种悲观的并发策略，采用独占访问的方式</span>
  			   </li>
  			</ul>
  		</li>
  		<li>原子变量类
  		
  		</li>
  		<li>非阻塞算法
  		
  		</li>
	  </ul>
  </div>
</div> 

</body>
</html>
			