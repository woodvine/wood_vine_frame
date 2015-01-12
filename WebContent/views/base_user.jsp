<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>User List</title>
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=basePath %>resource/pagination/pagination.css" type="text/css" media="all" />
		<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
		<script type='text/javascript' src="<%=basePath%>resource/pagination/pagination.js"></script>
	</head>
	<script type="text/javascript">
		var path = '<%=basePath%>';
		$(document).ready(function(){
			queryUsers();
		});
		
		function queryUsers(){
			$.ajax({
				type : 'POST',
				url : path+'queryUsers', //通过url传递name参数
				data : {
						page:_currentPage,
						pageSize:_pageSize
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						showTable(data.result);
						//调用分页插件，初始化分页Div
						pageShow("queryUsers",data.ext.total);
					}else{
						alert(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function showTable(list){
			var content = "";
			if(list==null||list.length==0){
				content+="<tr><td colspan='9'>没有相关数据!</td></tr>";
			}else{
				for(var i = 0;i<list.length;i++){
					var user = list[i];
					//奇偶交错样式
					if(i%2==0){
						content+="<tr>";
					}else{
						content+="<tr class='odd'>";
					}
					content+="<td><input type='checkbox' class='checkbox' value='"+user.id+"'/></td>";
					content+="<td>"+formatValue(user.loginName)+"</td>";
					content+="<td>"+formatValue(user.userName)+"</td>";
					content+="<td>"+formatValue(user.nickname)+"</td>";
					content+="<td>"+formatValue(user.email)+"</td>";
					content+="<td>"+formatStatus(user.status,1)+"</td>";
					content+="<td>"+formatValue(user.createTime)+"</td>";
					content+="<td>"+formatValue(user.lastLoginTime)+"</td>";
					content+='<td><a href="#" class="ico del">'+formatStatus(user.status,2)+'</a><a href="#" class="ico edit">重置密码</a></td>';
					content+="</tr>";
				}
			}
			
			$("#tableData").html(content);
		}
		
		function formatValue(value){
			if(value==null){
				return "";
			}
			return value;
		}
		
		function formatStatus(status,type){
			switch(type){
			case 1:
				if(status==1){
					return "正常";
				}else{
					return "已禁用";
				}
			case 2:
				if(status==1){
					return "禁用";
				}else{
					return "启用";
				}
			}
		}
	</script>
	
	<body>
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">Current Users</h2>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table">
						<table  width="100%" border="0" cellspacing="0" cellpadding="0">
						  <thead>
							<tr>
								<th width="5%"><input type="checkbox" class="checkbox" /></th>
								<th width="10">登录名</th>
								<th width="10">用户名</th>
								<th width="15%">昵称</th>
								<th width="15%">邮箱</th>
								<th width="5%">账号状态</th>
								<th width="15%">注册时间</th>
								<th width="15%">最后登录时间</th>
								<th width="10%" class="ac">操作</th>
							</tr>
						  </thead>
						  <tbody id="tableData"></tbody>
						</table>
						
						
						<!-- Pagging Div-->
						<div id="pageShow">
						</div>
						
					</div>
					<!-- Table -->
					
				</div>
				<!-- End Box -->
	</body>
</html>