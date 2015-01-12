<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script></head>
<body>

<div data-role="page" id="pageThree">
   <div data-role="header">
	    <div data-role="navbar">
		    <ul>
			    <li><a href="<%=basePath%>index.jsp"  data-icon="home">并发概念</a></li>
			    <li><a href="#pageThree" class="ui-btn-active ui-state-persist" data-icon="search">每日英语</a></li>
			    <li><a href="mobile_aboutMe.jsp"  data-icon="info">关于阿木</a></li>
			    <li><a href="mobile_contactMe.jsp" data-icon="grid" >联系我</a></li>
		    </ul>
	    </div>
  </div>
  
  <div data-role="content">
      <ul data-role="listview">
  		<li>
  		   <span>绝不:No way!</span>
  		</li>
  		<li>
  		   <span>祝你好运:May God bless you!</span>
  		</li>
  		<li>
  		   <span>好久不见:Long time no see!</span>
  		</li>
  		<li>
  		   <span>我也一样:So do I!</span>
  		</li>
  		<li>
  		   <span>做得好:Well done!</span>
  		</li>
  		<li>
  		   <span>等等:Hold on ,please!</span>
  		</li>
  		<li>
  		   <span>生命不息，奋斗不止:Cease to struggle ,cease to live!</span>
  		</li>
  		<li>
  		   <span>请问您贵姓:May I have your name ,please?</span>
  		</li>
	  </ul>
  </div>
</div> 

</body>
</html>
			