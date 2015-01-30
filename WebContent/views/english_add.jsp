<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Add English</title>
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
				url : path+'getEnglish', //通过url传递name参数
				data : {
					id:id,
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						var record = data.result;
						author = record.author;
						$("#content").val(record.content);
						$("#chinese").val(record.chinese);
						$("#description").val(record.description);
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
			if($("#content").val()==''){
				$("#msgContent").text("(必填项:请输入英语学习内容!)");
				$("#content").focus();
				return;
			}
			if($("#chinese").val()==''){
				$("#msgChinese").text("(必填项:请输入中文解释!)");
				$("#chinese").focus();
				return;
			}
			
			$.ajax({
				type : 'POST',
				url : path+'addEnglish', //通过url传递name参数
				data : {
					id:id,
					author:author,
					content:$("#content").val(),
					chinese:$("#chinese").val(),
					description:$("#description").val(),
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
					<h2>每天学一点英语</h2>
				</div>
				<!-- End Box Head -->
				
				<form action="" method="post">
					<!-- Form -->
					<div class="form">
							<p>
								<span class="req">不能为空,最长30个字符</span>
								<label>类型: <span style="color:red;">(必填)</span></label>
								<select id="type" class="field size1">
									<option value="1">单词</option>
									<option value="2">句子</option>
								</select>
							</p>	
							<p>
								<span class="req">不能为空,最长200个字符</span>
								<label>内容: <span style="color:red;" id="msgContent">(必填)</span></label>
								<textarea id="content" style="width:716px;" maxlength="200" class="field" rows="4" cols="50"></textarea>
							</p>	
							
							<p>
								<span class="req">不能为空,最长150个字符</span>
								<label>中文释义: <span style="color:red;" id="msgChinese">(必填)</span></label>
								<textarea id="chinese" style="width:716px;" maxlength="150" class="field" rows="3" cols="50"></textarea>
							</p>	
							<p>
								<span class="req">不能为空,最长100个字符</span>
								<label>使用说明: <span style="color:green;">(选填，对单词或句子的额外说明)</span></label>
								<textarea id="description" style="width:716px;" maxlength="100" class="field" rows="2" cols="50"></textarea>
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