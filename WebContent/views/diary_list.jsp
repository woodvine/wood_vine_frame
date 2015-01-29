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
		<script type='text/javascript' src="<%=basePath%>resource/js/textUtil.js"></script>
		<script type='text/javascript' src="<%=basePath%>resource/pagination/pagination.js"></script>
	</head>
	<script type="text/javascript">
		var path = '<%=basePath%>';
		var author=${userId};
		$(document).ready(function(){
			queryList();
		});
		
		function queryList(){
			$.ajax({
				type : 'POST',
				url : path+'queryDiaryList', //通过url传递name参数
				data : {
					    author:author,
						page:_currentPage,
						pageSize:_pageSize,
						type:$("#queryType").val()
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						showTable(data.result);
						//调用分页插件，初始化分页Div
						pageShow("queryList",data.ext.total);
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
			if(list!=null&&list.length>0){
				for(var i = 0;i<list.length;i++){
					var diary = list[i];
					//奇偶交错样式
					if(i%2==0){
						content+="<tr>";
					}else{
						content+="<tr class='odd'>";
					}
					
					var type = diary.type;
					var typeText = '';
					if(type==1){
						typeText = '日志';
					}else{
						typeText = '备忘';
					}
					content+="<td><input type='checkbox' class='checkbox' value='"+diary.id+"'/></td>";
					content+="<td>"+typeText+"</td>";
					content+="<td>"+formatValue(diary.title)+"</td>";
					content+="<td>"+formatValue(diary.content)+"</td>";
					content+="<td>"+formatValue(diary.createTime)+"</td>";
					content+='<td>'+formatOperation(diary)+'</td>';
					content+="</tr>";
				}
			}
			$("#tableData").html(content);
		}
		
		//操作连接拼接
		function formatOperation(diary){
			var str = '';
			str+= "<a class='ico del' href='javascript:deleteDiary(\""+diary.id+"\");'>删除</a>";
			str+="<a class='ico edit' href='javascript:showDetail(\""+diary.id+"\");'>详情</a>";
			return str;
		}
		
		function add(){
			window.location.href=path+"/views/diary_add.jsp?author="+author;
		}
		
		function showDetail(id){
			window.location.href=path+"/views/diary_add.jsp?id="+id;
		}
		
		function deleteDiary(id){
			$.ajax({
				type : 'POST',
				url : path+'deleteDiary', //通过url传递name参数
				data : {
						id:id
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						queryList();
					}else{
						alert(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
	</script>
	
 <body>
	<div class="shell">
			<!-- Box -->
			<div class="box">
				<!-- Box Head -->
				<div class="box-head">
					<h2 class="left">我的记录</h2>
					<div class="right">
						<label>查找类型:</label>
						<select id="queryType" class="field small-field">
							<option value="">全部</option>
							<option value="1">日记</option>
							<option value="2">备忘</option>
						</select>
						
						<!-- <label>开始日期</label>
						<input type="text" class="field small-field" id="startDate"/>
						<label>&nbsp;&nbsp;&nbsp;结束日期</label>
						<input type="text" class="field small-field" id="endDate"/> -->
						<input type="button" class="button" style="float:none;" value="查询" onClick="javascript:queryList();"/>
					</div>
				</div>
				<!-- End Box Head -->	

				<!-- Table -->
				<div class="table">
					<table  width="100%" border="0" cellspacing="0" cellpadding="0">
					  <thead>
					 	 <tr>
							<th colspan="6">
								<a href="javascript:add();" class="add-button"><span>添加新记录</span></a>
							</th>
						</tr>
						<tr>
							<th width="5%"><input type="checkbox" class="checkbox" /></th>
							<th width="10%">类型</th>
							<th width="10%">标题</th>
							<th width="45%">内容</th>
							<th width="18%">创建时间</th>
							<th width="12%" class="ac">操作</th>
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
				
		</div>
		<!-- End Box -->
	</body>
</html>