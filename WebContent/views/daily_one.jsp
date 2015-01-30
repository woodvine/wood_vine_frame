<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>User List</title>
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
	</head>
	<script type="text/javascript">
		var goodOne = ${goodOne};
		$(document).ready(function (){
			var str = '';
			str+="<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+goodOne.content+"</td></tr>";
			str+="<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;"+goodOne.chinese+"</td></tr>";
			str+="<tr><td style='float:right;margin-right:100px;'>————"+goodOne.author+"</td></tr>";
			$("#tableData").html(str);
		});
	</script>
 <body>
	<div class="shell">
			<!-- Box -->
			<div class="box">
				<!-- Box Head -->
				<div class="box-head">
					<h2 class="left">经典名句，积累前行的勇气</h2>
				</div>
				<!-- End Box Head -->	

				<!-- Table -->
				<div class="table">
					<table  width="100%" border="0" cellspacing="0" cellpadding="0">
					  <thead>
					  </thead>
					  <tbody id="tableData">
					  </tbody>
					</table>
				</div>
				<!-- Table -->
				
				
			</div>
		</div>
		<!-- End Box -->
	</body>
</html>