	// document initial statement
	var picEncoder = null;
	
	// variable about document
	var encoderDiv = document.getElementById("encoderDiv");// 右边div对象
	var fragment = document.createDocumentFragment();
	
	// variable about hammer
	var hammertime = new Hammer(encoderDiv, { dragMaxTouches: 0, preventDefault: false });
	var last_touches = new Array();//触屏坐标
	
	// bind event to encoder 
	FrameEvents.on(encoderDiv, "touch", updateTouchs, collectTouches);
	FrameEvents.on(encoderDiv, "drag", updateDrag, collectTouches);
	FrameEvents.on(encoderDiv, "release", updateRelease, collectTouches);
	
	/**
	 * 创建底图机的信源信息
	 * 1 初始化左侧信源列表
	 * 2 初始化PicEncoder平面区域（默认2*3）
	 * 3 弹出PicEncoder的操作区域
	 */
	function createPicEncoder(){
		// 设置弹出框的高度,布局默认2*4
		$("#picModalContent").css("height", (600) + "px");
		$("#picSumbitBtn").css("margin-top", (450) + "px");
		$('#rowX').val('2');
		$('#rowY').val('4');
		
		//高清底图机的基本信息
		var rowX = $("#rowX").val();
		var rowY = $("#rowY").val();
		var name = $("#newSourceName").val();
		
		//初始化类信息
		picEncoder = new PicEncoder(name,rowX,rowY);
		var result = picEncoder.init();
		if(!result){
			console.log("init picEncoder failure...");
			bootbox.alert('没有可用编码器信源,请稍后重试!');
			return;
		}
		
		//show 
		$("#layoutDiv").css("display","block");
		$("#picencoder_div").modal('show');
	}
	
	/**
	 * 修改高清底图机的信源信息
	 * 1 初始化左侧信源列表
	 * 2 初始化PicEncoder平面区域（默认2*3）
	 * 3 弹出PicEncoder的操作区域
	 */
	function modifyPicEncoder(data){
		var source = data.source; 
		var blocks = data.blocks;
		
		//置弹出框与修改相关的属性
		$('#picEncoderId').val(source.id);
		$('#group_picencoder').html(initSelectOptions(source.parent.id));
		$('#picEncoderName').val(source.name);
		$("#modifyInfo").css("display","block");
		$("#layoutDiv").css("display","none");
		$("#picModalContent").css("height", (630) + "px");
		$("#picSumbitBtn").css("margin-top", (500) + "px");
		
		//高清底图机的类信息初始化
		var rowX = source.rowX;
		var rowY = source.rowY;
		var name = source.name;
		picEncoder = new PicEncoder(name,rowX,rowY);
		picEncoder.setAction('modify');
		picEncoder.setBlocks(blocks);
		picEncoder.init();
		
		$("#picencoder_div").modal('show');
	}
	
	/**
	 *define class named PicEncoder
	 *@ comment:高清底图机确定屏幕的划分
	 */
	function PicEncoder(name,rowX,rowY) {
		//操作类型：添加或者修改
		this.action = 'add';
		
		//默认为2*3的画布
		this.sourceType = "PicEncoder";
		this.name = name;
		this.rowX = rowX;
		this.rowY = rowY;
		this.uRefWidth = '1920';
		this.uRefHeight = '1080';
		this.picEncoderBlocks = null;//包含的Block信息
		
		// variable about position
		this.move = false; // 移动的初始化
		this.$bg = null;
		this.initDiv = null; // 拖拽对象 目标对象
		this.tarDiv = null;
		this.tarDivHalf = 0;
		this.wHalf = 0;
		this.initPos = {x: 0, y: 0};
		this.relPos = {x: 0, y: 0};
		this.temPos = {x: 0, y: 0}; 
		this.dragPos = {x1: 0, x2: 0, y1: 0, y2: 0};// 拖拽对象的四个坐标 
		this.tarDivPos = {x1: 0, y1: 0, x2: 0, y2: 0}; //目标对象的四个坐标 
	
		// variable about encoder
		this.encoderList = new Array;// 编码器列表
		this.encodersTouched = new Array();// 已经拖拽的编码器
		this.encoderSelected = null; // 当前选中的编码器
	
		//高清底图机关联的Block：修改操作该值非空
		this.setBlocks = function(blocks){
			if(blocks!=null&&blocks.length>0){
				this.picEncoderBlocks = blocks;
			}
		},
		
		//修改操作会重置action
		this.setAction = function(action){
			if(action!=''&&(action=='add'
				||action=='modify')){
				this.action = action;
			}
		},
		 
		// init 各种绑定事件
		this.init = function() {
			var obthis=this;
			//保存按钮:先解除与先前对象的绑定事件，在重新设置click事件
			$("#btn-confirm").unbind();
			$("#btn-confirm").on("click",function(){
				obthis.savePicEncoder();
			});	
			
			//关闭按钮
			$("#picModalCancel").unbind();
			$("#picModalCancel").on("click",function(){
				$("#newSourceName").val('');
				$("#picencoder_div").modal('hide');
				$("#modifyInfo").css("display","none");
			});	
			
			//取消按钮
			$("#btn-quit").unbind();
			$("#btn-quit").on("click",function(){
				$("#newSourceName").val('');
				$("#picencoder_div").modal('hide');
				$("#modifyInfo").css("display","none");
			});	
			
			$("#rowX").unbind();
			$("#rowX").on("change",function(){
				obthis.changeLayout($("#rowX").val(), $("#rowY").val());
			});	
			
			$("#rowY").unbind();
			$("#rowY").on("change",function(){
				obthis.changeLayout($("#rowX").val(), $("#rowY").val());
			});		
			
			var result = obthis.initEncoderList();
			if(result||obthis.action=='modify'){
				obthis.drawPlane();
				return true;
			}else{
				return false;
			}
		},
	
		// bind remove event to encoder
		this.removeEncoder = function() {
			var obthis=this;
			$(".deleteImg").unbind();
			$(".deleteImg").on("click", function(){
				var obth = $(this);
				bootbox.confirm("确定要移除该编码器吗?", function(result){
					if(result){
						var par=$(obth.parent().parent().find(".decoderLabel"));
						var id=par.attr('value');
						var cons='';
						cons+='<div class="decoderDiv" id="encoderDiv'+id+'" >';
						cons+='<div ontouchmove="event.preventDefault()" code="'+id+'" class="';
						if(obthis.encoderList[id].status=="1"){
							cons+='undecoderImg';
						}else{
							cons+='decoderImg decoderImgs';
						}
						cons+='" id="decoderImg'+id+'"></div>';
						cons+='<label class="decoderSpan"  style="width:100%;"  value="'+id+'" >'+obthis.encoderList[id].name+'</label>';
						cons+='</div>';
						
						//返回最初的列表框内	
						$("#encoderDiv").append(cons);
						var size=$.inArray(id, obthis.encodersTouched);
						obthis.encodersTouched.splice(size,1);
						obth.parent().parent().html("").removeClass("decoderSelect").addClass("decoderNone");
					}
				});
			});
		},
	
		// get all available encoders
		this.initEncoderList = function() {
			var obthis=this;
			var data = getAbailableEncoders();
			if(data==null||data.length==0){
				return false;
			}
			
			//数据初始化左侧列表
			$("#encoderDiv").html('');
			var cons = '';
			for(var i in data){
				//用下标填充encoderList
				obthis.encoderList[i]=data[i];
				cons+='<div class="decoderDiv" id="encoderDiv'+data[i].id+'" >';
				cons+='<div  ontouchmove="event.preventDefault()" code="'+data[i].id+'" class="';
				if(data[i].status=="1"){
					cons+='undecoderImg';
				}else{
					cons+='decoderImg decoderImgs';
				}
				cons+='" id="decoderImg'+data[i].id+'"></div>';
				//value属性存储该编码器的下标
				cons+='<label class="decoderSpan"  style="width:100%;"  value="'+i+'">';
				if(data[i].name == undefined ){
					cons+='&nbsp;';
				}else{
					cons+=data[i].name;
				}
				cons+='</label>';
				cons+='</div>';
			}
			console.log("obthis.encoderList:"+obthis.encoderList.length);
			$("#encoderDiv").html(cons);
			return true;
		},
	
		// change row and column number
		this.changeLayout = function (rows,columns){
			this.rowX = rows;
			this.rowY = columns;
			this.initEncoderList();
			this.drawPlane();
		},
		
		// draw plane 
		this.drawPlane = function(){
			//清除先前的画面信息
			$("#divLib").html('');
			//根据操作类型绘制画布
			if(this.action=='add'){
				this.drawAddPane();
			}else if(this.action=='modify'){
				this.drawModifyPane();
			}
		},
		
		this.drawAddPane = function(){
			//重新按照rowX*rowY创建画布
			var obthis=this;
			var width=100/parseInt(obthis.rowY);
			var height=100/parseInt(obthis.rowX);
			var con="";
			
			//初始化各个Blcok
			var blocks=new Array();
			var border = [];
			for(var i=0;i<parseInt(obthis.rowX);i++){
				for(var j=0;j<parseInt(obthis.rowY);j++){
					var map={};
					xF=parseInt(obthis.uRefWidth)*j;	
					yF=parseInt(obthis.uRefHeight)*i;
					//修正每个Block的边框
					if(i==0&&j>0){
						map.border = 'border-left-style:none;';
					}else if(i>0&&j==0){
						map.border = 'border-top-style:none;';
					}else if(i>0&&j>0){
						map.border = 'border-top-style:none;border-left-style:none;';
					}else{
						map.border = '';
					}
					map.encoderId='';
					map.startX=xF;
					map.startY=yF;
					map.endX=xF+parseInt(obthis.uRefWidth);
					map.endY=yF+parseInt(obthis.uRefHeight);					
					blocks.push(map);
				}
			}
			
			//绘制屏幕
			for(var i=0;i<blocks.length;i++){	
				con+='<div style="'+blocks[i].border+'width:'+width+'%;height:'+height+'%" class="decoderNone" value="'+i+'"></div>';
			}
			
			obthis.picEncoderBlocks = blocks;
			$("#divLib").html(con);
		},
		
		this.drawModifyPane = function(){
			//重新按照rowX*rowY创建画布
			var obthis=this;
			var width=100/parseInt(obthis.rowY);
			var height=100/parseInt(obthis.rowX);
			
			//初始化各个Blcok
			var blocks=new Array();
			for(var i=0;i<parseInt(obthis.rowX);i++){
				for(var j=0;j<parseInt(obthis.rowY);j++){
					var map={};
					xF=parseInt(obthis.uRefWidth)*j;	
					yF=parseInt(obthis.uRefHeight)*i;
					//修正每个Block的边框
					if(i==0&&j>0){
						map.border = 'border-left-style:none;';
					}else if(i>0&&j==0){
						map.border = 'border-top-style:none;';
					}else if(i>0&&j>0){
						map.border = 'border-top-style:none;border-left-style:none;';
					}else{
						map.border = '';
					}
					map.startX=xF;
					map.startY=yF;
					map.endX=xF+parseInt(obthis.uRefWidth);
					map.endY=yF+parseInt(obthis.uRefHeight);	
					var savedBlock = findSavedBlock(map,obthis.picEncoderBlocks);
					map.encoderId= savedBlock.encoderId;
					map.id = savedBlock.id;
					blocks.push(map);
				}
			}
			
			//根据Blcok绘制屏幕
			obthis.picEncoderBlocks = blocks;
			var indexArray = [];
			var indexArrayLength = indexArray.length;
			var con="";
			for(var i=0;i<blocks.length;i++){
				var block = blocks[i];
				var encoderId = block.encoderId;
				if(encoderId==null||encoderId==''){
					con+='<div style="'+block.border+'width:'+width+'%;height:'+height+'%" class="decoderNone" value="'+i+'"></div>';
				}else{
					//记录需要调整字体大小的元素
					indexArray[indexArrayLength] = i;
					indexArrayLength = indexArray.length;
					
					//查到该encoder的信息，加入到encoderList
					var encoder = getEncoderInfo(encoderId);
					var nextIndex = obthis.encoderList.length;
					obthis.encoderList[nextIndex] = encoder;
					
					//拼接到画布
					if(encoder.status=="1"){
						con+='<div style="'+block.border+'background-color:#666666;width:'+width+'%;height:'+height+'%" class="decoderSelect" value="'+i+'">';
					}else{
						con+='<div style="'+block.border+'width:'+width+'%;height:'+height+'%" class="decoderSelect" value="'+i+'">';
					}
					con+='<div class="bgColorDiv">';
					con+='<img title="解除绑定" class="deleteImg" style="width:30px;" src="'+basePath+'/img/unbind_blue.png" />';
					con+='</div>';
					con+='<lable class="decoderLabel" value="'+i+'" style="font-size:18px;">'+encoder.name+'</label>';
					con+='</div>';
				}
			}
			
			$("#divLib").html(con);
			//bind removeEncoder to deleteImg
			this.removeEncoder();	
			
			//修正字体大小
			for(var j = 0;j<indexArray.length;j++){
				var value = indexArray[j];
				var layerW=parseFloat($(".decoderLabel[value='"+value+"']").parent().width());
				var labelW=$(".decoderLabel[value='"+value+"']").text().length;
				var size=parseInt(layerW/labelW)+2;
				if(size>18){
					$(".decoderLabel[value='"+value+"']").css("font-size","18px");
				}else{
					$(".decoderLabel[value='"+value+"']").css("font-size",size+"px");
				} 
				var wi=$(".decoderSelect").width();
				if(wi>300){
					$(".decoderSelect").find("img").attr("width","30px");
				}else{
					$(".decoderSelect").find("img").attr("width",wi/10+"px");
				}
			}
		},
		
		/**
		 * 保存底图机的信源信息
		 */
		this.savePicEncoder = function (){
			var obth=this;
			//屏幕块列表
			var blocks= obth.picEncoderBlocks;	
			//统计屏幕信息的数组
			var blocksArray=new Array();
			//已经绑定encoder的屏幕墙
			$(".decoderSelect").each(function(){
				var obthis=$(this);
				
				//该选中的Block的下标
				var index=$(this).attr("value");
				var map=blocks[index];
				
				//拖拽到该Block的encoder在encoderList中的下标
				var obj=$(obthis.find(".decoderLabel"));
				var encoderIndex=obj.attr('value');
				console.log("encoder index:"+encoderIndex);
				
				map.encoderId=obth.encoderList[encoderIndex].id;
				blocksArray.push(map);
			});
			
			//未绑定的Block
			$(".decoderNone").each(function(){
				var index=$(this).attr("value");
				var map=blocks[index];
				//如果是modify则需要重新设置Block对应的encoderId为空
				if(obth.action=='modify'){
					map.encoderId = '';
				}
				blocksArray.push(map);
			});
			
			var map = {};
			map.sourceType = obth.sourceType;
			map.name = obth.name;
			map.rowX= obth.rowX;
			map.rowY = obth.rowY;
			//根据行、列重新计算分辨率
			map.uRefWidth = obth.uRefWidth*obth.rowY;
			map.uRefHeight = obth.uRefHeight*obth.rowX;
			map.picEncoderBlockList = blocksArray;
			
			var url  = basePath+"/management/source/savePicEncoder";
			var parentId = selectGroup.id;
			if(this.action=='modify'){
				url = basePath+"/management/source/modifyPicEncoder";
				map.id = $("#picEncoderId").val();
				map.name = $("#picEncoderName").val();
				parentId = $('#group_picencoder').val();
				console.log("parent id:"+parentId);
			}
			
			//提交
			$.ajax({
				type : "post",
				url : url, 
				data : {
					parentId:parentId,
					picEncoder:JSON.stringify(map)
				},
				dataType : "json",
				success : function(data) {
					var json=data; 
					var result=json.result;
					if(result=='success'){
						//刷新分组列表
						getGroupTree(); 
						findSourceList(1);
						//关闭弹出框
						$("#newSourceName").val('');
						$("#picencoder_div").modal('hide');
					}else if(result=='failure'){
						bootbox.alert(json.failureMsg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(textStatus=="error"){
						bootbox.alert("暂时无法连接服务器，请稍后重试");
					}
				}
			});
		},
		
		// refresh encoder list timing
		this.refreshEncoderList = function() {
		};
	}
	
	/**
	 * 选中事件处理
	 */
	function updateTouchs() {
		Hammer.utils.each(last_touches, function(touch) {
			//判断状态是否可用
		   	if(!Hammer.utils.inStr(touch.target.className, "undecoderImg") && Hammer.utils.inStr(touch.target.className, "decoderImgs")) {
		   		encoderSelected = $(touch.target).attr('code');
		   		picEncoder.initDiv = $(touch.target).parent(); //拖拽对象 
				// 鼠标 与 目标元素的相对坐标 
				picEncoder.relPos.x = touch.pageX - $(touch.target).offset().left; 
				picEncoder.relPos.y = touch.pageY - $(touch.target).offset().top;
				picEncoder.move = true;//判断是否已经按下
				picEncoder.$bg = $(".decoderNone");
				picEncoder.initDiv.removeClass("decoderDiv").addClass("decoderDivDrag");
				picEncoder.initDiv.css({ left:  (touch.pageX-parseInt($(touch.target).width())/2 ) +'px', top: (touch.pageY-parseInt($(touch.target).height())/2 ) + 'px' });
				picEncoder.initDiv.appendTo($("body"));
			}
	    });
	    encoderDiv.appendChild(fragment);
	}
	
	/**
	 * 拖拽事件处理
	 * 判断拖拽目标位置落在哪个屏幕块，并高亮该区域块 
	 */
	function updateDrag() {
		Hammer.utils.each(last_touches, function(touch) {
	  		//如果已经按下，继续，否则返回
		  	if (!picEncoder.move) { 
		  		return false; 
		  	} 
		  	
			// 下列代码是 if(move)的 程序 
			picEncoder.dragPos.x1 = touch.pageX - picEncoder.relPos.x; 
			picEncoder.dragPos.y1 = touch.pageY - picEncoder.relPos.y; 
			picEncoder.dragPos.x2 = picEncoder.dragPos.x1 + picEncoder.initDiv.innerWidth(); 
			picEncoder.dragPos.y2 = picEncoder.dragPos.y1 + picEncoder.initDiv.innerHeight(); 
			// 目标元素随鼠标移动的坐标 
			picEncoder.initDiv.css("left",picEncoder.dragPos.x1 +'px');
			picEncoder.initDiv.css("top",picEncoder.dragPos.y1 + 'px' );
			
			picEncoder.$bg.each(function() { 
				picEncoder.tarDiv = $(this); 
				// 目标对象的坐标 
				picEncoder.tarDivPos.x1 = picEncoder.tarDiv.offset().left; 
				picEncoder.tarDivPos.x2 = picEncoder.tarDivPos.x1 + picEncoder.tarDiv.width(); 
				picEncoder.tarDivPos.y1 = picEncoder.tarDiv.offset().top; 
				picEncoder.tarDivPos.y2 = picEncoder.tarDivPos.y1 + picEncoder.tarDiv.height(); 
				picEncoder.tarDivHalf = picEncoder.tarDiv.height(); //临时变量，以便于在if判断中使用 
				picEncoder.wHalf = picEncoder.tarDiv.width(); 
				//目标区域高亮
				if (picEncoder.dragPos.x2 >= (picEncoder.tarDivPos.x1 + picEncoder.wHalf/6) 	
						&& picEncoder.dragPos.x2 <= (picEncoder.tarDivPos.x2 )
						&& picEncoder.dragPos.y2 >= (picEncoder.tarDivPos.y1+picEncoder.tarDivHalf/3) 
						&& picEncoder.dragPos.y2 <= picEncoder.tarDivPos.y2) { 
					if(picEncoder.tarDiv.children().length >0 ) {
						return false;
					}// 解决重叠元素 移动到一个目标元素 
					
					picEncoder.tarDiv.addClass("bgColor");		
				} else { 
					picEncoder.tarDiv.removeClass("bgColor");
				}
			}); 		      
		 });
		
		 encoderDiv.appendChild(fragment);
	}
	
	/**
	 * 选中时，记录该解码器信息
	 * @param ev
	 */
	function collectTouches(ev) {
		last_touches = ev.gesture.touches;
	}
	
	/**
	 * 释放事件处理
	 * 1）释放时鼠标位置位于屏幕区域中
	 *     1.1 记录该区域信息
	 *     1.2 添加移除链接
	 * 2）释放时鼠标位于非屏幕中
	 *     2.1 还原该解码器到解码器列表
	 */
	function updateRelease() {
		 Hammer.utils.each(last_touches, function(touch) {
		  	if (!picEncoder.move) { 
		  		return ; 
		  	}
			
			if($(".bgColor").length > 0){	
				//获取选中的编码器在encoderList中的下标
				var deval=picEncoder.initDiv.children(":last").attr("value");	
				console.log("deval:"+deval);
				picEncoder.initDiv.remove();
				$(".bgColor").html('');
				
				var	con='<div class="bgColorDiv">';
				con+='<img title="解除绑定" class="deleteImg" src="'+basePath+'/img/unbind_blue.png" />';
				con+='</div>';
				con+='<lable class="decoderLabel" value="'+deval+'" >'+picEncoder.initDiv.children(":last").html()+'</label>';
				$(".bgColor").html(con);
				$(".bgColor").removeClass("bgColor decoderNone").addClass("decoderSelect");		
				if(picEncoder.encoderList[deval].status=="1"){
					$(".decoderLabel[value='"+deval+"']").parent().css("background-color","#666666");
				}
				
				picEncoder.encodersTouched.push(deval.toString());
				picEncoder.removeEncoder();	//bind removeEncoder to deleteImg
				
				//设置字体大小
				var layerW=parseFloat($(".decoderLabel[value='"+deval+"']").parent().width());
				var labelW=$(".decoderLabel[value='"+deval+"']").text().length;
				var size=parseInt(layerW/labelW)+2;
				if(size>18){
					$(".decoderLabel[value='"+deval+"']").css("font-size","18px");
				}else{
					$(".decoderLabel[value='"+deval+"']").css("font-size",size+"px");
				} 
				var wi=$(".decoderSelect").width();
				if(wi>300){
					$(".decoderSelect").find("img").attr("width","30px");
				}else{
					$(".decoderSelect").find("img").attr("width",wi/10+"px");
				}
			}else{
				picEncoder.initDiv.appendTo($("#encoderDiv"));//返回最初的父div内
			}
			picEncoder.initDiv.removeClass("decoderDivDrag").addClass("decoderDiv").removeAttr("style"); //恢复拖拽对象初始的样式 
			picEncoder.move = false; 
		  });
		  encoderDiv.appendChild(fragment);
	}
	
    //获取编码器列表
	function getAbailableEncoders(){
		var encoderList = [];
		$.ajax({
			type : "post",
			url : basePath+"/management/source/getEncoderSource", 
			data : {
			},
			dataType : "json",
			async:false,
			success : function(data) {
				encoderList = data;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(textStatus=="error"){
					bootbox.alert("暂时无法连接服务器，请稍后重试");
				}
			}
		});
		return encoderList;
	}
	
	//获取编码器信息
	function getEncoderInfo(id){
		var encoder = [];
		$.ajax({
			type : "post",
			url : basePath+"/management/source/findSourceById", 
			data : {
				id:id
			},
			dataType : "json",
			async:false,
			success : function(data) {
				encoder = data.source;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(textStatus=="error"){
					bootbox.alert("暂时无法连接服务器，请稍后重试");
				}
			}
		});
		return encoder;
	}
	
	//找到画布Block对应的数据库的Block对象
	function findSavedBlock(current,blocks){
		for(var i = 0;i<blocks.length;i++){
			var block = blocks[i];
			if(current.startX==block.startX
					&&current.startY==block.startY
					&&current.endX==block.endX
					&&current.endY==block.endY){
				return block;
			}
		}
	}