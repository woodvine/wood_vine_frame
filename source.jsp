<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>信源管理 </title>
<meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=0;">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/swf/swfobject.js"></script>
<%@ include file="/common/script.jsp" %>
<script type="text/javascript">
	var vpid = <%=MemoryData.getVPID() %>;
	var apid = <%=MemoryData.getAPID() %>;	
	<%-- var group = <%=MemoryData.TYPE_GROUP %>; --%>	
	<%-- var source= <%=MemoryData.TYPE_SOURCE %>; --%>
	//信源类型: Source.sourceType
	var DISPLAY = <%=MemoryData.SOURCETYPE_DISPLAY %>; 
	var BHIP92 = <%=MemoryData.SOURCETYPE_ENCODER %>;
	var IPC = <%=MemoryData.SOURCETYPE_IPC %>;
	var CA92 = <%=MemoryData.SOURCETYPE_CA92 %>;
</script>
</head>
 <body>
<div class="container">
	<div class="row clearfix header">
		<div class="col-xs-3 col-md-3 col-lg-3 column title">
			画面云管理调度系统
		</div>
		<div class="col-xs-9 col-md-9 col-lg-9 column source_titleBtn">
			<div class="dropdown pull-right"  style="margin-top:3px" >
				<a class="dropdown-toggle" href="#" data-toggle="dropdown">
					<span class="glyphicon glyphicon-user"></span>
				</a>
				<ul id="setting" class="dropdown-menu" aria-labelledby="dLabel" role="menu" >
				<!-- <li id="sysManager" role="presentation">
						<a  role="menuitem" href="/mcs/screen.jsp">切换到前台</a>
						<hr>
					</li>  -->
					<li id="editPwd" role="presentation">
						<a  role="menuitem" href="/mcs/personalInfo.jsp" >个人信息</a>
						<hr>
					</li>
					<li id="exit" role="presentation">
						<a  role="menuitem" href="javascript:logout();">安全退出</a>
					</li>
				</ul>
			</div>
			<%if(ca92Enable==1){ %>
			<a href="/mcs/menu.jsp">
			<%}else{ %>
			<a href="/mcs/menu_noca92.jsp">
			<%} %>
				<img title="返回首页" src="${pageContext.request.contextPath}/img/home.png" class="titleImg pull-right" id="homeImg"/> 
			</a>
		</div>
	</div>
</div>
<div class="container">
	<div class="row clearfix">
		<div class="col-xs-3 col-md-3 col-lg-3 column decoderLeft">
			<div class="row clearfix titleBanner"  >
				信源分组
				<div class="pull-right">
					<span class="glyphicon glyphicon-plus" title="添加" id="addGroup_btn" ></span>
					<span class="glyphicon glyphicon-pencil"  title="修改" id="updateGroup_btn" ></span>
					<span class="glyphicon glyphicon-trash"  title="删除" id="deleteGroup_btn" ></span>
				</div>	
			</div>
			<div id="dtreeDiv" style="overflow:auto;overflow-x:hidden;">				
			</div>
		</div>
		<div class="col-xs-9 col-md-9 col-lg-9 column" style="padding-right:0px;" id="bottomDiv">
			<div id="sourceGroup_title" class="row clearfix titleBanner "  style="margin-right: 0px;">全部
			</div>
			<div class="panel-body col_main_body" id="scrollDiv">
				<form class="form-horizontal" role="form" method="post" id="searchForm">
				<div class="form-group">
					<div style="float: left;">
						<span style="width: 100px;">信源名称:</span>
					</div>
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="width:22%">
						<input type="text" class="form-control" size="20" id="sourceName" placeholder="" />
					</div>
					<div style="float: left;">
						<span style="width: 100px;">信源地址:</span>
					</div>
				 	<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="width:22%">
				 		<input type="text" class="form-control" size="20" id="sourceIP" placeholder="" />
					</div> 
					<div class="col-xs-4 col-sm-4 col-md-64 col-lg-4 search_btn_div">
						<span class="glyphicon glyphicon-search" id="selectSource_btn" title="查询"></span>
						<span class="glyphicon glyphicon-plus" id="addSource_btn" title="添加"></span>
						<span class="glyphicon glyphicon-pencil" id="updateSource_btn" title="修改"></span>
						<span class="glyphicon glyphicon-trash" id="deleteSource_btn" title="删除"></span>
						<span class="glyphicon glyphicon-share" id="changeGroup_btn" title="更改分组"></span>
					</div>
				</div>
			</form>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th><input type="checkbox" name="chk" id="chkAll" onclick="checkAll()"></th>
						<th>信源分组</th>
						<th>信源类型</th>
						<th>信源名称</th>
						<th>信源地址</th>
						<!-- CA92Enable = 1 时，显示预览操作 -->
						<%if(ca92Enable == 1){%>
							<th>预览</th>
						<%} %>
					</tr>
				</thead>
				<tbody id="templateDate">
				</tbody>
			</table>
			<div class="pageDiv pull-right bottom-page" >
					<ul class="pagination" id="page_ul"> </ul> <!-- 分页菜单 -->
			</div>
			</div>
		</div>
	</div>
</div>
<!-- 信源分组信息 -->
<div class="modal fade" data-backdrop="static" id="group_div" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header addNewBanner">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="groupInfo_title" contenteditable="false">信源分组信息</h4>
      </div>
      <div class="modal-body">
      		<form class="form-horizontal" role="form" method="post" id="plan_form" name="plan_form">
				<div class="row clearfix">					
					<div class="form-group">
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">分组名称:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">
							<input type="hidden" id="groupId" />			
							<input type="text" class="form-control" id="groupname" />
						</div>
					</div>
					<div class="form-group text-center" style="margin-top: 30px;">
						<div class="col-md-3 col-xs-3 col-lg-3 col-md-offset-3 col-lg-offset-3 col-xs-offset-3">
							<button type="button" id="saveGroup_btn" class="btn btn-primary btn-lg btn-block">确&nbsp;定</button>
						</div>
						<div class="col-md-3 col-lg-3  col-xs-3 col-md-offset-1 col-lg-offset-1 col-xs-offset-1">
							<button type="button" class="btn btn-primary btn-lg btn-block" data-dismiss="modal">取&nbsp;消</button>
						</div>
					</div>
				</div>
			</form>
      </div>
    </div>
  </div>
</div>
<!-- 选择信源类型 -->
<div class="modal fade" data-backdrop="static" id="sourceType_div" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header addNewBanner">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="sourceType_title" contenteditable="false">选择信源类型</h4>
      </div>
      <div class="modal-body">
      		<form class="form-horizontal" role="form" method="post" id="plan_form" name="plan_form">
				<div class="row clearfix">					
					<div class="form-group">
						 <label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">信源名称:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="newSourceName"/>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2">信源类型:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">		
							<div class="row">
								<div class="col-lg-10">
									<input type="radio" name="sourceType" value="IPC" id="IPC" checked="checked"/>IPC摄像头
								</div>
							</div>
							<div class="row">
								<div class="col-lg-10">
									<input type="radio" name="sourceType" value="CA92"/>第三方编码器
								</div>
							</div>
							<div class="row">
								<div class="col-lg-10">
									<input type="radio" name="sourceType" value="PicEncoder"/>高清底图机
								</div>
							</div>
						</div>
					</div>
					<div class="form-group text-center" style="margin-top: 30px;">
						<div class="col-md-3 col-xs-3 col-lg-3 col-md-offset-3 col-lg-offset-3 col-xs-offset-3">
							<button type="button" id="nextStep_btn" class="btn btn-primary btn-lg btn-block">下一步</button>
						</div>
						<div class="col-md-3 col-lg-3  col-xs-3 col-md-offset-1 col-lg-offset-1 col-xs-offset-1">
							<button type="button" class="btn btn-primary btn-lg btn-block" data-dismiss="modal">取&nbsp;消</button>
						</div>
					</div>
				</div>
			</form>
      </div>
    </div>
  </div>
</div>
<!-- IPC摄像头 -->
<div class="modal fade" data-backdrop="static" id="ipcInfo_div" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header addNewBanner">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="ipcInfo_title" contenteditable="false">IPC摄像头</h4>
      </div>
      <div class="modal-body">
      		<form class="form-horizontal" role="form" method="post" id="plan_form" name="plan_form">
				<div class="row clearfix">					
					<div class="form-group">
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">默认分组:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">
							<input type="hidden" id="ipcId" value="" />
							<input type="hidden" id="ipcType" value="" />					
							<select id="group_ipc" class="form-control">
								<option value="">全部</option>
							</select>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">名称:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ipcName"/>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">IP地址:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ipcIP"/>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">端口:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ipcPort"/>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">用户名:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ipcUsername"/>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">密码:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ipcPwd"/>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">品牌:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">	
							<select id="ipcBrand" class="form-control"></select>			
							<!-- <input type="text" class="form-control" id="ipcBrand"/>-->
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">型号:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">	
							<select id="ipcVersion" class="form-control"></select>			
							<!-- <input type="text" class="form-control" id="ipcVersion"/>-->
						</div>
					</div>
					<div>
						<span style="color:red;" id="ipcerrorMsg"></span>
					</div>
					<div class="form-group text-center" style="margin-top: 30px;">
						<div class="col-md-3 col-xs-3 col-lg-3 col-md-offset-3 col-lg-offset-3 col-xs-offset-3">
							<button type="button" id="saveIpc_btn" class="btn btn-primary btn-lg btn-block">确&nbsp;定</button>
						</div>
						<div class="col-md-3 col-lg-3  col-xs-3 col-md-offset-1 col-lg-offset-1 col-xs-offset-1">
							<button type="button" class="btn btn-primary btn-lg btn-block" data-dismiss="modal">取&nbsp;消</button>
						</div>
					</div>
				</div>
			</form>
      </div>
    </div>
  </div>
</div>
<!-- 第三方编码器 -->
<div class="modal fade" data-backdrop="static" id="ca92Info_div" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header addNewBanner">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="ca92Info_title" contenteditable="false">第三方编码器</h4>
      </div>
      <div class="modal-body">
      		<form class="form-horizontal" role="form" method="post" id="plan_form" name="plan_form">
				<div class="row clearfix">					
					<div class="form-group">
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2"></label>
						<div class="col-md-6 col-lg-6 col-xs-6">		
							<div class="row">
								<div class="col-lg-10" id="castModule_div">
									<input type="radio" name="isMulticast" value="0"  checked="checked"/>单播模式
									<input type="radio" name="isMulticast" value="1"/>组播模式
								</div>
							</div>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">默认分组:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">					
							<input type="hidden" id="ca92Id" value="" />
							<input type="hidden" id="ca92Type" value="" />					
							<select id="group_ca92" class="form-control">
								<option value="">全部</option>
							</select>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">名称:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ca92Name"/>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" id="ca92IP_label" style="width: 17%">预留地址:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ca92IP"/>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" id="ca92Port_label" style="width: 17%">预留端口:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ca92Port"/>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">视频ID:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ca92VPID" value="48"/>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">音频ID:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ca92APID" value="49"/>
						</div>
					</div>
					<div>
						<span style="color:red;" id="ca92errorMsg"></span>
					</div>
					<div class="form-group text-center" style="margin-top: 30px;">
						<div class="col-md-3 col-xs-3 col-lg-3 col-md-offset-3 col-lg-offset-3 col-xs-offset-3">
							<button type="button" id="saveCa92_btn" class="btn btn-primary btn-lg btn-block">确&nbsp;定</button>
						</div>
						<div class="col-md-3 col-lg-3  col-xs-3 col-md-offset-1 col-lg-offset-1 col-xs-offset-1">
							<button type="button" class="btn btn-primary btn-lg btn-block" data-dismiss="modal">取&nbsp;消</button>
						</div>
					</div>
				</div>
			</form>
      </div>
    </div>
  </div>
</div>
<!-- BHIP92 -->
<div class="modal fade" data-backdrop="static" id="encoderInfo_div" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header addNewBanner">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="encoderInfo_title" contenteditable="false">硬件92</h4>
      </div>
      <div class="modal-body">
      		<form class="form-horizontal" role="form" method="post" id="plan_form" name="plan_form">
				<div class="row clearfix">					
					<div class="form-group">
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">默认分组:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">					
							<input type="hidden" id="encoderId" value="" />
							<input type="hidden" id="encoderType" value="" />					
							<select id="group_ip92" class="form-control">
								<option value="">全部</option>
							</select>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">名称:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="ip92Name"/>
						</div>
					</div>
					<div>
						<span style="color:red;" id="encodererrorMsg"></span>
					</div>
					<div class="form-group text-center" style="margin-top: 30px;">
						<div class="col-md-3 col-xs-3 col-lg-3 col-md-offset-3 col-lg-offset-3 col-xs-offset-3">
							<button type="button" id="saveEncoder_btn" class="btn btn-primary btn-lg btn-block">确&nbsp;定</button>
						</div>
						<div class="col-md-3 col-lg-3  col-xs-3 col-md-offset-1 col-lg-offset-1 col-xs-offset-1">
							<button type="button" class="btn btn-primary btn-lg btn-block" data-dismiss="modal">取&nbsp;消</button>
						</div>
					</div>
				</div>
			</form>
      </div>
    </div>
  </div>
</div>
<!-- DISPLAY -->
<div class="modal fade" data-backdrop="static" id="displayInfo_div" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header addNewBanner">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="displayInfo_title" contenteditable="false">硬件92</h4>
      </div>
      <div class="modal-body">
      		<form class="form-horizontal" role="form" method="post" id="plan_form" name="plan_form">
				<div class="row clearfix">					
					<div class="form-group">
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">默认分组:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">					
							<input type="hidden" id="displayId" value="" />
							<input type="hidden" id="displayType" value="" />					
							<select id="group_display" class="form-control">
								<option value="">全部</option>
							</select>
						</div>
						<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">名称:</label>
						<div class="col-md-6 col-lg-6 col-xs-6">				
							<input type="text" class="form-control" id="displayName"/>
						</div>
					</div>
					<div>
						<span style="color:red;" id="displayerrorMsg"></span>
					</div>
					<div class="form-group text-center" style="margin-top: 30px;">
						<div class="col-md-3 col-xs-3 col-lg-3 col-md-offset-3 col-lg-offset-3 col-xs-offset-3">
							<button type="button" id="saveDisplay_btn" class="btn btn-primary btn-lg btn-block">确&nbsp;定</button>
						</div>
						<div class="col-md-3 col-lg-3  col-xs-3 col-md-offset-1 col-lg-offset-1 col-xs-offset-1">
							<button type="button" class="btn btn-primary btn-lg btn-block" data-dismiss="modal">取&nbsp;消</button>
						</div>
					</div>
				</div>
			</form>
      </div>
    </div>
  </div>
</div>
<!-- 设置信源分组路径 -->
<div class="modal fade" data-backdrop="static" id="changeGroup_div" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header addNewBanner">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" contenteditable="false">设置信源分组</h4>
      </div>
      <div class="modal-body">
      		<form class="form-horizontal" role="form" method="post" id="plan_form" name="plan_form">
				<div class="row clearfix">					
					<div class="form-group">
						<div class="col-md-6 col-lg-6 col-xs-6">
						<div class="col-lg-10">
							<input type="hidden" id="setToParentId" value="" />
							<input type="hidden" id="idsStr" value="" />
							<div id="set_dtreeDiv" style="overflow:auto;overflow-x:hidden;">				
							</div>
						</div>
						</div>
					</div>
					<div class="form-group text-center" style="margin-top: 30px;">
						<div class="col-md-3 col-xs-3 col-lg-3 col-md-offset-3 col-lg-offset-3 col-xs-offset-3">
							<button type="button" id="saveSetGroup_btn" class="btn btn-primary btn-lg btn-block">确&nbsp;定</button>
						</div>
						<div class="col-md-3 col-lg-3  col-xs-3 col-md-offset-1 col-lg-offset-1 col-xs-offset-1">
							<button type="button" class="btn btn-primary btn-lg btn-block" data-dismiss="modal">取&nbsp;消</button>
						</div>
					</div>
				</div>
			</form>
      </div>
    </div>
  </div>
</div>
<!-- 信源预览 -->
<div class="modal fade" data-backdrop="static" id="previewDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog modal-show">
    <div class="modal-content">
      <div class="modal-header addNewBanner">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="closePreview_btn">×</button>
        <h4 class="modal-title" id="myModalLabel" contenteditable="false">预览</h4>
      </div>
      <div class="modal-body" style="padding:0; height:250px;" id="previewBox">
      	<div id="previewPlayer"></div>
      </div>
    </div>
  </div>
</div>

<!-- 高清底图机 -->
<div class="modal fade" data-backdrop="static" id="picencoder_div" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog" style="width: 1000px;">
    <div class="modal-content" style="width:100%;" id="picModalContent">
      <div class="modal-header addNewBanner">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="picModalCancel">×</button>
        <h4 class="modal-title" id="ca92Info_title" contenteditable="false">高清底图机</h4>
      </div>
      <div class="modal-body" >
  		    <!-- 可修改的部分 -->
  		    <div  id="modifyInfo" style="display:none;">
  		        <label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">默认分组:</label>
				<div class="col-md-6 col-xs-6" style="width: 42%;">					
					<input type="hidden" id="picEncoderId" value="" />
					<select id="group_picencoder" class="form-control">
						<option value="">全部</option>
					</select>
				</div>
				<label class="col-md-2 col-lg-2 col-xs-2  control-label col-md-offset-2 col-lg-offset-2 col-xs-offset-2" style="width: 17%">名称:</label>
				<div class="col-md-6 col-xs-6" style="width: 42%;">				
					<input type="text" class="form-control" id="picEncoderName"/>
				</div>
  		    </div>
  		    
    		<!-- 高清底图机的基本信息：行，列，分辨率 -->
			<div class="form-group" id="layoutDiv" style="height: 20px; margin-left: 150px;">
				<label style=" float: left; font-size: 16px;">布&nbsp;&nbsp;局:</label>
				<div class="col-md-2" style="width: 27%;">
					<select name="rowX" id="rowX" class="form-control">
						<option value="1">1</option>
						<option value="2" selected>2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
					</select>
				</div>
				<div class="col-md-1 col-lg-1  col-xs-1 text-left clearlr">
					<label>行&nbsp;&nbsp;&nbsp;X</label>
				</div>
				<div class="col-md-2" style="width: 27%;">
					<select name="rowY" id="rowY" class="form-control">	
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3" selected>3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
					</select>
				</div>
				<div class="col-md-1 col-lg-1  col-xs-1 text-left clearlr">
					<label>列</label>
				</div>
			</div>
		
			<!-- 左侧编码器列表 -->
			<div id="encoderDiv" class="row" style="overflow:auto; border: 2px solid #000000; background:#EEF2FD; float: left; width: 25%; height: 395px; margin: 15px 0 5px 0;">				
			</div>
		
			<!-- 画面画布 -->
			<div class="clearfix" id="encoderScrollDiv" style="width: 75%; height: 395px; float: right; margin: 15px 0 5px 0;">
				<div id="divLib" class="divLib" style="width: 98%; height: 395px; float: right;border:none;">					
				</div>	
			</div>
			
			<!-- 提交 -->
			<div class="form-group text-center" id="picSumbitBtn" style="margin-top: 450px;">
				<div class="col-md-3 col-lg-3  col-xs-3 col-md-offset-3 col-lg-offset-3 col-xs-offset-3">
					<button type="button" id="btn-confirm" class="btn btn-primary btn-lg btn-block">确&nbsp;定</button>
				</div>
				<div class="col-md-3 col-lg-3  col-xs-3 col-md-offset-1 col-lg-offset-1 col-xs-offset-1">
					<button type="button" id="btn-quit" class="btn btn-primary btn-lg btn-block" data-dismiss="modal">取&nbsp;消</button>
				</div>
			</div>
      </div>
    </div>
  </div>
</div>

<%@ include file="footer.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsp/source.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsp/picencoder.js"></script>
</body>
</html>
