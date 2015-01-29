<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Add diary</title>
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=basePath %>resource/pagination/pagination.css" type="text/css" media="all" />
		<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
		<script type='text/javascript' src="<%=basePath%>resource/pagination/pagination.js"></script>
	</head>
	<script type="text/javascript">
		var path = '<%=basePath%>';
		var author = '${param.author}';
		var id =  '${param.id}';
		$(document).ready(function(){
			if(id!=''){
				init();
			}
		});
		
		function init(){
			$.ajax({
				type : 'POST',
				url : path+'getDiary', //通过url传递name参数
				data : {
					id:id,
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						var record = data.result;
						author = record.author;
						$("#diaryContent").val(record.content);
						$("#title").val(record.title);
						$("#type").val(record.type);
					}else{
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function sumbit(){
			$.ajax({
				type : 'POST',
				url : path+'addDiary', //通过url传递name参数
				data : {
					id:id,
					author:author,
					content:$("#diaryContent").val(),
					title:$("#title").val(),
					type:$("#type").val()
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						history.back();
					}else{
						$("#resultMsg").text(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function cancel(){
			history.back();
		}
	</script>
	
	<body  onkeydown="if(event.keyCode == 13){sumbit();}">
	<div class="shell">
			<!-- 添加 -->
			<div class="box">
				<!-- Box Head -->
				<div class="box-head">
					<h2>记录时间的痕迹</h2>
				</div>
				<!-- End Box Head -->
				
				<form action="" method="post">
					<!-- Form -->
					<div class="form">
							<p>
								<span class="req">不能为空,最长30个字符</span>
								<label>类型: <span style="color:red;">(必填)</span></label>
								<select id="type" class="field size1">
									<option value="1">日志</option>
									<option value="2">备忘</option>
								</select>
							</p>	
							<p>
								<span class="req">不能为空,最长30个字符</span>
								<label>标题: <span style="color:red;">(必填)</span></label>
								<input id="title" maxlength="30" type="text" class="field size1" />
							</p>	
							
							<p>
								<span class="req">不能为空,最长500个字符</span>
								<label>内容: <span style="color:red;">(必填)</span></label>
								<textarea id="diaryContent" maxlength="500" class="field size1" rows="10" cols="50"></textarea>
							</p>	
					</div>
					<div class="buttons">
						<input type="button"  class="button" onclick="sumbit();" value="保存" />
						<input type="button"  class="button" onclick="cancel();" value="取消" />
					</div>
					<!-- End Form -->
					
					<!-- Form Buttons -->
					<!-- End Form Buttons -->
				</form>
			</div>
		</div>
	</body>
</html>