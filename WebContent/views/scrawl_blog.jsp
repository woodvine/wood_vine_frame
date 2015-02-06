<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>Welcome to WoodVine</title>
	<link rel="stylesheet" href="<%=basePath%>resource/css/style.css" type="text/css" media="all" />
	<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
</head>
<script type="text/javascript">
		var path = '<%=basePath%>';
		function sumbit(){
			if($("#blogOwner").val()==''){
				$("#blogOwner").focus();
				$("#msg").text("导出的CSDN用户名不能为空!");
				return;
			}
			
			$.ajax({
				type : 'POST',
				url : path+'scrawlBlog', //通过url传递name参数
				data : {
					blogOwner:$("#blogOwner").val()
				},
				dataType : 'json',
				success:function(data){
					if(data.status){
						$("#resultMsg").text(data.description);
						$("#resultMsg").attr("style","color:green;");
					}else{
						$("#resultMsg").text(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function clear(){
			$("#blogOwner").val('');
			$("#resultMsg").text('');
		}
</script>
<body onkeydown="if(event.keyCode == 13){sumbit();}">
<div id="scrawl">
	<div class="shell">
		<div class="box">
				<!-- Box Head -->
				<div class="box-head">
					<h2>CSDN博文导出</h2>
				</div>
				<!-- End Box Head -->
				
				<form action="" method="post">
					<!-- Form -->
					<div class="form">
							<p>
								<span class="req">不能为空,最长20个字符</span>
								<label>CSDN博主用户名: <span id="msg" style="color:red;">(必填)</span></label>
								<input name="blogOwner" id="blogOwner" maxlength="20" type="text" class="field size1" />
							</p>	
							<br/>
							<h2><span id="resultMsg" style="color:red;"></span></h2>	
					</div>
					<div class="buttons">
						<input type="button"  class="button" onclick="sumbit();" value="保存" />
						<input type="button"  class="button" onclick="clear();" value="清除" />
					</div>
					<!-- End Form -->
					
				</form>
			</div>
	</div>
</div>	
</body>
</html>