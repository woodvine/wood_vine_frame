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
		$(document).ready(function(){
			getProvinces();
		});
		
		function getProvinces(){
			$.ajax({
				type : 'POST',
				url : path+'getProvinces', //通过url传递name参数
				data : {
					},
				dataType : 'json',
				async:false,
				success:function(data){
					if(data.status){
						var result = data.result;
						var str = "";
						if(result!=null&&result.length>0){
							for(var i = 0;i<result.length;i++){
								str+="<option value='"+result[i].code+"'>"+result[i].name+"</option>";
							}
							$("#provinces").append(str);
							//初始化为第一个省的城市信息
							getCitys(result[0].code);
						}
					}else{
						fillWeatherData('');
					}
				},
				error:function(e){
					fillWeatherData('');
				}
			});
		}
		
		function getCitys(theRegionCode){
			$.ajax({
				type : 'POST',
				url : path+'getCitys', //通过url传递name参数
				data : {
					theRegionCode:theRegionCode
					},
				dataType : 'json',
				async:false,
				success:function(data){
					//初始化清空
					$("#citys").find("option").remove();
					
					//重新设置新的城市列表
					if(data.status){
						var result = data.result;
						var str = "";
						if(result!=null&&result.length>0){
							for(var i = 0;i<result.length;i++){
								str+="<option value='"+result[i].code+"'>"+result[i].name+"</option>";
							}
							$("#citys").append(str);
							//默认显示第一个城市的天气
							queryWeather(result[0].code);
						}
					}else{
						fillWeatherData('');
					}
				},
				error:function(e){
					fillWeatherData('');
				}
			});
		}
		
		function provinceChange(element){
			getCitys(element.value);
		}
		
		function cityChange(element){
			queryWeather(element.value);
		}
		
		function queryWeather(theRegionCode){
			$.ajax({
				type : 'POST',
				url : path+'queryWeather', //通过url传递name参数
				data : {
					theRegionCode:theRegionCode
					},
				dataType : 'json',
				async:false,
				success:function(data){
					//重新设置新的城市列表
					if(data.status){
						var result = data.result;
						var str = "";
						if(result!=null&&result.length>0){
							for(var i = 0;i<result.length;i++){
							}
							$("#title").text(result[0]+"——"+result[1]+"地区近5日天气信息");
						}
						fillWeatherData(data.ext);
						fillTodayData(data.result);
					}else{
						fillWeatherData('');
					}
				},
				error:function(e){
					fillWeatherData('');
				}
			});
		}
		
		function fillWeatherData(list){
			if(list==''||list.length<1){
				$("#tableData").html("<tr colspan='4'><td>没有查询到相关天气信息，请稍后重试!</td></tr>");
				return;
			}
			
			var str = '';
			for(var i = 0;i<list.length;i++){
				var data = list[i];
				str+='<tr><td>'+data.date+'</td>';
				str+='<td><img src="'+path+"resource/weather/"+data.image+'">'+data.weather+'</img></td>';
				str+='<td>'+data.temperature+'</td>';
				str+='<td>'+data.wind+'</td>';
				str+='</tr>'
			}
			$("#tableData").html(str);
		}
		
		function fillTodayData(list){
			if(list==''||list.length<1){
				$("#todayData").html("<tr colspan='3'><td>没有查询到相关天气信息，请稍后重试!</td></tr>");
				return;
			}
			
			var str = '';
			str+='<tr rowspan="3"><td>'+list[3]+'</td>';
			str+='<td>'+list[4]+'</td>';
			str+='<td>'+list[6]+'</td>';
			str+='</tr>'
			$("#todayData").html(str);
			$("#tadayTable").css("display","block");
		}
	</script>
	
 <body>
	<div class="shell">
			<!-- Box -->
			<div class="box">
				<!-- Box Head -->
				<div class="box-head">
					<h2 class="left">近5日天气查询（请选择省份和城市）</h2>
					<div class="right">
						<label  style="float:none;">省份:</label>
						<select  class="field small-field size4" id="provinces" onchange="provinceChange(this);"></select>
						<label  style="float:none;">城市:</label>
						<select  class="field small-field size4" id="citys" onchange="cityChange(this);"></select>
					</div>
				</div>
				<div class="table">
					<table  width="100%" border="0" cellspacing="0" cellpadding="0">
					  <thead>
					 	 <tr>
							<th colspan="4" id="title">
							</th>
						</tr>
						<tr>
							<th width="30%">日期</th>
							<th width="20%">天气</th>
							<th width="20%">气温</th>
							<th width="30%">风力/风向</th>
						</tr>
					  </thead>
					  <tbody id="tableData"></tbody>
					</table>
					<table  id="tadayTable" style="display:none;" width="100%" border="0" cellspacing="0" cellpadding="0">
					  <thead>
					 	 <tr>
							<th colspan="4">今日天气实况</th>
						</tr>
						<tr>
							<th width="20%">最近发布时间</th>
							<th width="20%">当前天气实况</th>
							<th width="60%">健康关注</th>
						</tr>
					  </thead>
					  <tbody id="todayData"></tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- End Box -->
	</body>
</html>