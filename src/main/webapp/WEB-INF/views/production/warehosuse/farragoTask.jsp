<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>杂工管理</title>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/myModules/css/bacthManager.css" media="all">
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<style type="text/css">
.layui-form-item .equalesDiv .layui-form-checkbox[lay-skin=primary] {
	margin-top: 2px;
	width: 120px;
}
.layui-form-item {
    width: 100%;
}
</style>
</head>

<body>
	


<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td class="smHidden">批次名:</td>
							<td class="smHidden"><input   name="bacth" placeholder="请输入批次名" class="layui-input laydate-icon">
							</td>
							<td class="smHidden">&nbsp;&nbsp;</td>
							<td>工序名称:</td>
							<td><input   name="name" style="width: 170px;"  placeholder="请输入工序名称" class="layui-input laydate-icon"></td>
							<td>&nbsp;&nbsp;</td>
							<td>时间:</td>
							<td><input id="startTime" style="width: 180px;"  placeholder="请输入时间" class="layui-input laydate-icon"></td>
							<td>&nbsp;&nbsp;</td>
							<td id="block">状态:</td>
							<td id="block2" style="width: 100px;"><select id="status" name="status"><option value="">请选择</option><option value="0">进行中</option><option value="1">已完成</option></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" id="LAY-search5" lay-submit lay-filter="LAY-search">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
		</div>
	</div>
<!-- 查看工序 -->
 <div style="display: none;" id="working">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<div id="content">
						
					</div>
				</div>
			</div>
</div> 

<div style="display: none;" id="layuiShare">
			<table id="layuiShare2"  class="table_th_search" lay-filter="layuiShare"></table>
</div>

<form action="" id="layuiadmin-form-admin2"
		style="display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<div style="position: relative; padding-top: 15px;">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">日期</label>
					<div class="layui-input-inline">
						<input type="text" 
							style="width: 190px;" name="allotTime"
							id="allotTime" lay-verify="tradeDaysTime" placeholder="请输入日期"
							class="layui-input laydate-icon">
					</div>
			</div>
				
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">批次名</label>
				<div class="layui-input-inline">
					<input name="bacth" style="width:190px;" lay-filter="id" id="bacth" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item bigHidden">
			<label class="layui-form-label bigHidden" style="width: 100px; ">开始时间</label>
					<div class="layui-input-inline">
						<input type="text" 
							style="width: 190px;" name="startTime"
							id="startTimes" lay-verify="tradeDaysTime" placeholder="开始时间"
							class="layui-input laydate-icon">
					</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">工序名</label>
				<div class="layui-input-inline">
					<input name="name" style="width:190px;" lay-filter="id" id="name" lay-search="true" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">时间</label>
				<div class="layui-input-inline">
					<input name="time" style="width:190px;" lay-filter="id"  id="time" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">备注</label>
				<div class="layui-input-inline">
					<input name="remarks" style="width:190px;" lay-filter="id" id="remarks" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
			</div><div style="position: absolute; left: 360px; top: 10px;border:0px solid; width: 270px; height:380px; overflow-y:scroll;" >
			<div>
				<label>完成人</label>
				<div id="test7">
					<div id="test7" class="demo-tree"></div>
					<fieldset class="layui-elem-field layui-field-title">
					</fieldset>
				</div>
			</div>
			</div>
		</div>
	</form>

	<form action="" id="layuiadmin-form-admin3"
		style="padding-top:10px;padding-right:10px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin3" id="findLayui">
			<!-- <div class="layui-form-item">
				<table id="findty"></table>
	        	<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">完成人：</label>
				<div class="layui-input-inline" id="usercheck">
					
				</div>
	         </div>
	        </div> -->
		</div>
	</form>

<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="add">新增杂工</span>
				<span class="layui-btn layui-btn-sm" lay-event="update">修改杂工</span>
				<span class="layui-btn layui-btn-sm" lay-event="addIp">加绩</span>
				<span class="layui-btn layui-btn-sm" lay-event="query">查看人员</span>
				<span class="layui-btn layui-btn-sm bigHidden" lay-event="endTask">结束任务</span>
				<button type="button" class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除任务</button>
			</div>
</script>
	<script>
	
	layui.config({
		base: '${ctx}/static/layui-v2.4.5/'
	}).extend({
		tablePlug: 'tablePlug/tablePlug',
		menuTree : 'layui/myModules/menuTree',
	}).define(
		['tablePlug', 'laydate', 'element','tree','menuTree','jquery'],
		function() {
			var $ = layui.jquery
				,layer = layui.layer //弹层
				,form = layui.form //表单
				,table = layui.table //表格
				,laydate = layui.laydate //日期控件
				,tablePlug = layui.tablePlug //表格插件
				,element = layui.element
				,tree = layui.tree
				,jQuery = layui.jquery
				,menuTree = layui.menuTree;
			
			var ua = navigator.userAgent.toLocaleLowerCase();
		      var pf = navigator.platform.toLocaleLowerCase();
		      var isAndroid = (/android/i).test(ua)||((/iPhone|iPod|iPad/i).test(ua) && (/linux/i).test(pf))
		          || (/ucweb.*linux/i.test(ua));
		      var isIOS =(/iPhone|iPod|iPad/i).test(ua) && !isAndroid;
		      var isWinPhone = (/Windows Phone|ZuneWP7/i).test(ua);
		  
		      var mobileType = {
		         pc:!isAndroid && !isIOS && !isWinPhone,
		         ios:isIOS,
		         android:isAndroid,
		         winPhone:isWinPhone
		   };
		      var mobileType=mobileType.pc;
			//全部字段
			var allField;
			var productId="";
			var self = this;
			this.setIndex = function(index){
		  		_index=index;
		  	}
		  	
		  	this.getIndex = function(){
		  		return _index;
		  	}
		  	function p(s) { return s < 10 ? '0' + s: s; }
			var myDate = new Date();
			var date=myDate.getDate(); 
			var year=myDate.getFullYear();
			var month=myDate.getMonth()+1;
			var day = new Date(year,month,0);
			var x;
			if(date<10){
				x="0"
			}else{
				x=""
			}
			var firstdate = year + '-' + p(month) + '-01'+' '+'00:00:00';
			var lastdate = year + '-' + p(month) + '-' + day.getDate() +' '+'23:59:59';
			var firstdate3 = year + '-' + p(month) + '-01';
			var lastdate3 = year + '-' + p(month) + '-' + day.getDate();
			var firstdate2 = year + '-' + p(month) + '-'+ x+date+' '+'00:00:00';
			var lastdate2 = year + '-' + p(month) + '-' + x+date+' '+'23:59:59';
			laydate.render({
				elem: '#startTime',
				type: 'date',
				range: '~',
				value : firstdate3+' ~ '+lastdate3,
			});
			laydate.render({
				elem: '#startTimes',
				type: 'datetime',
			});
			//select全局变量
			var htmls = '<option value="0">请选择</option>';
			var htmlsh = '<option value="0">请选择</option>';
			var index = layer.load(1, {
				shade: [0.1, '#fff'] //0.1透明度的白色背景
			});
			
			var html="<option value=''>请选择</option>"
				$.ajax({
					url:"${ctx}/task/pickTaskPerformance",
					type:"GET",
					success:function(result){
						$(result.data).each(function(i,o){
						html+='<option value="'+i+'" data-name="'+o.name+'" data-value="'+o.number+'">'+o.name+'</option>'
						})
						form.render(); 
					}
				});
			
			form.on('select(lay_selecte3)', function(data){
				var data={
					  id:data.value,
					  type:2,
			   }
			var htmltwo="";
			$.ajax({
				url:"${ctx}/production/allGroup",
				data:data,
				type:"GET",
				beforeSend:function(){
					index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						});
				},
				success:function(result){
					$(result.data).each(function(i,o){
					
					$(o.users).each(function(i,o){
						htmltwo +='<div class="input-group"><input type="checkbox" lay-ignore style="display:inline;" class="stuCheckBox" value="'+o.id+'" data-username="'+o.userName+'">'+o.userName+'</input></div>'
					})
					})
					var s="<div class='input-group'><input type='checkbox' lay-ignore style='display:inline;' class='checkall'>全选</input></div>"
					$('.select').html(s+htmltwo)
					$(".checkall").on('click',function(){
		                    if($(this).is(':checked')){ 
					 			$('.stuCheckBox').each(function(){  
		                    //此处如果用attr，会出现第三次失效的情况  
		                     		$(this).prop("checked",true);
					 			})
		                    }else{
		                    	$('.stuCheckBox').each(function(){ 
		                    		$(this).prop("checked",false);
		                    		
		                    	})
		                    }
		                });
					layer.close(index);
				},error:function(){
					layer.msg("操作失败！", {icon: 2});
					layer.close(index);
				}
			});
			})
			
			layer.close(index);
			laydate.render({
				elem: '#allotTime',
				type : 'datetime',
			});
			
			 var col="";
			 if(mobileType){
				 $("#block").hide();
				 $("#block2").hide();
				 col = [
					  {type: 'checkbox',align: 'center',},
					  {field: "bacth",title: "批次名",align: 'center',search: true,edit: false,},
					  {field: "allotTime",title: "时间",align: 'center',},
					  {field: "name",title: "工序名",align: 'center',},
					  {field: "time",title: "现场管理时间",align: 'center',edit: false,},
					  {field: "remarks",title: "备注",align: 'center',edit: false,},
					  {field: "price",title: "任务价值",templet:function(d){return parseFloat((d.price==null ? 0 : d.price).toFixed(3))}},
					  {field: "payB",title: "B工资净值",align: 'center',templet:function(d){return parseFloat((d.payB==null ? 0 : d.payB).toFixed(3))}},
					  {field: "performancePrice",title: "加绩工资",align: 'center',templet:function(d){return parseFloat((d.performancePrice==null ? 0 : d.performancePrice).toFixed(3))}}
				 ]
			 }
			 if(mobileType==false){
				 col = [
					  {type: 'checkbox',align: 'center',},
					  {field: "allotTime",title: "时间",width:160, height:'50px', align: 'center',},
					  {field: "name",title: "工序名",width:200,align: 'center',},
					  {field: "time",title: "时间",width:60,align: 'center',edit: false,},
					  {field: "remarks",title: "备注",align: 'center',edit: false,},
					  {field: "performancePrice",title: "加绩工资",width:90,align: 'center',templet:function(d){return parseFloat((d.performancePrice==null ? 0 : d.performancePrice).toFixed(3))}},
					  {field: "status",title: "状态",align: 'center',width:90,templet:function(d){return d.status==0 ? "<span class='layui-badge '>进行中</span>" :"<span class='layui-badge layui-bg-green'>已完成</span> "}},
				 ]
			 }
			
			var htmltwo="";
		   	tablePlug.smartReload.enable(true); 
			table.render({
				elem: '#tableData',
				size:mobileType==true ? '' : '', 
				url: '${ctx}/farragoTask/allFarragoTask',
				where:{
					type:2,
					orderTimeBegin:firstdate,
			  		orderTimeEnd:lastdate,
				},
				limits:[14,20,50,100,500,1000],	
				limit:14,
				request:{
					pageName: 'page' ,//页码的参数名称，默认：page
					limitName: 'size' //每页数据量的参数名，默认：limit
				},
				page: {
				},//开启分页
				loading: true,
				toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
				//totalRow: true,		 //开启合计行 */
				colFilterRecord: true,
				smartReloadModel: true,// 开启智能重载
				parseData: function(ret) {
					layui.each(ret.data.rows,function(index,item){
						item.overtimes = 0;
					})
					return {
						code: ret.code,
						msg: ret.message,
						count:ret.data.total,
						data: ret.data.rows
					}
				},
				cols: [col],
				
						});
			
			
						
					/* $(".layui-badge").parent().on('click',function(){
						$(this).prev().addClass("layui-form-checked")
					}) */
						
			table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						
						var myDate = new Date();
						function p(s) { return s < 10 ? '0' + s: s; }
						//获取当前年
						var year=myDate.getFullYear();
						//获取当前月
						var month=myDate.getMonth()+1;
						//获取当前日
						var date=myDate.getDate();
						var h=myDate.getHours();              //获取当前小时数(0-23)
						var m=myDate.getMinutes();          //获取当前分钟数(0-59)
						var s=myDate.getSeconds();
						var day = new Date(year,month,0);  
						var firstdate = year + '-' + '0'+month + '-01'+' '+'00:00:00';
						var lastdate = year + '-' + '0'+month + '-' + day.getDate() +' '+'23:59:59';
						var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);//当前时间
						var myDate2 = new Date(new Date().getTime() - 86400000);
						var year2=myDate2.getFullYear();
						//获取当前月
						var month2=myDate2.getMonth()+1;
						//获取当前日
						var date2=myDate2.getDate();
						var Front=year2 + '-' + p(month2)+ '-' + p(date2)+' '+'00:00:00'//前一天时间
						if(mobileType==false){
						$("#allotTime").val(now)
						$("#startTimes").val(now)
						}else{
							$("#allotTime").val(Front)
						}
						switch(obj.event) {
						case 'add':
							var arr=new Array()
							 var array=new Array()
							 var id="";
							//遍历人名组别
						    $.ajax({
							      url:"${ctx}/production/getGroup",
							      data:{
							    	  type:2
							      },
							      type:"GET",
							      async:false,
					      		  success: function (r) {
					      			if(r.code==0){
										var groupPeople = r.data;
										$(r.data).each(function(i,o){
											id=o.id
										var ids=o.id	
										var	name=o.name
											var data={
					      							type:2,
												 	id:id,
												 	temporarilyDate:$('#allotTime').val(),
										   		}
												$.ajax({
													url:"${ctx}/production/allGroup",
													data:data,
													 async:false,
													type:"GET",
													beforeSend:function(){
														index = layer.load(1, {
															  shade: [0.1,'#fff'] //0.1透明度的白色背景
															});
													},
													success:function(r){
														$(r.data).each(function(j,k){
															var children=new Array()
															$(k.userList).each(function(i,o){
																if(o.status==1){
																	children.push({
																		title:o.name+' -- <span class="layui-badge layui-bg-green type">正</span>',
																		id:o.id,
																		userId:o.userId,
																		falsew:0,
																	});	
																	
																}
															})
															$(k.temporarilyUser).each(function(i,o){
																if(o.status==1){
																	children.push({
																		title:o.name+' -- <span class="layui-badge type">临</span>',
																		id:o.id,
																		userId:o.userId,
																		falsew:1,
																	});	
																}
															})
															layer.close(index);
															var	fals=false;
															if(jQuery.isEmptyObject(r.data.temporarilyUser)==true && jQuery.isEmptyObject(r.data.userList)==true){
																fals=true
															}
														arr.push({
															title:name,
															id:ids,
															children:children,
															disabled:fals,
															});
														})	
													},error:function(){
														layer.msg("操作失败！", {icon: 2});
														layer.close(index);
													}
												});	
											})
									}
							      }
							  });
							
							
						tree.render({
						    elem: '#test7'
						    ,data: arr
						    ,id: 'demoId1'
						    ,showCheckbox: true
						  });
							
						$(".type").parent().on('click',function(){
							if($(this).prev().prev().is(':checked')==false){ 
								$(this).prev().addClass("layui-form-checked")
								$(this).prev().prev().prop("checked",true);
								$(this).closest('.layui-tree-pack').prev().find('.layui-tree-iconClick').next().next().addClass("layui-form-checked");
								$(this).closest('.layui-tree-pack').prev().find('.layui-tree-iconClick').next().next().prev().prop("checked",true);
							}else{
								var arrat=new Array()
							$(this).closest('.layui-tree-pack').find(':input').each(function(i,o){
								if($(this).is(':checked')){
									arrat.push(1)
								}
							})
							if(arrat.length==1){
								$(this).closest('.layui-tree-pack').prev().find('.layui-tree-iconClick').next().next().prev().prop("checked",false);
								$(this).closest('.layui-tree-pack').prev().find('.layui-tree-iconClick').next().next().removeClass("layui-form-checked");
							}
								$(this).prev().removeClass("layui-form-checked")
								$(this).prev().prev().prop("checked",false);
							}
						})	
						var	dicDiv=$("#layuiadmin-form-admin2");
						var index=layer.open({
								type:1,
								title:'新增杂工',
								area:['630px','500px'],
								btn:['确认','取消'],
								content:dicDiv,
								id: 'LAY_layuipro' ,
								btnAlign: 'c',
							    moveType: 1, //拖拽模式，0或者1
								success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole',
										'lay-submit' : ''
									})
						        },
								yes:function(){
									form.on('submit(addRole)', function(data) {
										data.field.type=6;
										 var arr=new Array()
										 var arrtem=new Array()
										 var ids=new Array()
										 var temporaryIds=new Array()
										 var checkData = tree.getChecked('demoId1')
										$(checkData).each(function(j,k) {   
											$(k.children).each(function(o,l) {  
												if(l.falsew==1){
													arrtem.push(l.userId);
											   		temporaryIds.push(l.id);
												}
												if(l.falsew==0){
													arr.push(l.userId); 
												    ids.push(l.id);
												}
											})
										});
										 data.field.ids=arr
										 data.field.temporaryIds=arrtem
										 data.field.userIds=ids
										 data.field.temporaryUserIds=temporaryIds
										 data.field.type=2
										 if($("#allotTime").val()==""){
											 layer.msg("日期不能为空",{icon:2});
												return;
										 }
										 if(arr.length<=0 && arrtem.length<=0){
											 layer.msg("领取人不能为空",{icon:2});
												return;
										 }
										 if($("#name").val()==""){
											 return layer.msg("工序不能为空", {icon:2 });
										  }
										 if($("#startTimes").val()!="" && $("#time").val()!=""){
											  return layer.msg("填写开始时间后不能填写现场管理时间", {icon:2 });
										  }
										mainJs.fAdd(data.field); 
										document.getElementById("layuiadmin-form-admin2").reset();
							        	layui.form.render();
							        	layer.close(index);
									})
								},end:function(){ 
						        	document.getElementById("layuiadmin-form-admin2").reset();
						        	layui.form.render();
								  }
							})
							break;
							
						case 'update':
							var choosed=layui.table.checkStatus("tableData").data;
							if(choosed.length==0){
								layer.msg("请选择一条信息进行编辑",{icon:2});
								return;
							}
							if(choosed.length>1){
								layer.msg("无法同时编辑多条信息",{icon:2});
								return;
							}
							/* if(choosed[0].status==1){
								return layer.msg("当前任务已完成,不能进行修改", {icon: 2});
							} */
							$("#allotTime").val(choosed[0].allotTime)
							$("#bacth").val(choosed[0].bacth)
							$("#name").val(choosed[0].name)
							$("#time").val(choosed[0].time)
							$("#remarks").val(choosed[0].remarks)
							arrTemporaryUserIds=choosed[0].temporaryUserIds.split(',');
							arrUserIds=choosed[0].userIds.split(',');
							var c = arrUserIds.concat(arrTemporaryUserIds);
							var arr=new Array()
							 var array=new Array()
							 var id="";
							var h=false;
							//遍历人名组别
						    $.ajax({
							      url:"${ctx}/production/getGroup",
							      data:{
							    	  type:2,
							      },
							      type:"GET",
							      async:false,
					      		  success: function (r) {
					      			if(r.code==0){
										var groupPeople = r.data;
										$(r.data).each(function(i,o){
											id=o.id
										var ids=o.id	
										var	name=o.name
											var data={
					      							type:2,
												 	id:id,
												 	temporarilyDate:$('#allotTime').val(),
										   		}
												$.ajax({
													url:"${ctx}/production/allGroup",
													data:data,
													 async:false,
													type:"GET",
													beforeSend:function(){
														index = layer.load(1, {
															  shade: [0.1,'#fff'] //0.1透明度的白色背景
															});
													},
													success:function(r){
														$(r.data).each(function(j,k){
															var children=new Array()
															$(k.userList).each(function(i,o){
																for (var i = 0; i < arrUserIds.length; i++) {
																	if(o.id==arrUserIds[i]){
																		h=true	
																	}
																}
																
																if(o.status==1){
																	children.push({
																		title:o.name+' -- <span class="layui-badge layui-bg-green type">正</span>',
																		id:o.id,
																		userId:o.userId,
																		falsew:0,
																		
																	});	
																	
																}
															})
															$(k.temporarilyUser).each(function(i,o){
																for (var i = 0; i < arrTemporaryUserIds.length; i++) {
																	if(o.id==arrTemporaryUserIds[i]){
																		h=true	
																	}
																}
																if(o.status==1){
																	children.push({
																		title:o.name+' -- <span class="layui-badge type">临</span>',
																		id:o.id,
																		userId:o.userId,
																		falsew:1,
																	});	
																}
															})
															layer.close(index);
															var	fals=false;
															if(jQuery.isEmptyObject(r.data.temporarilyUser)==true && jQuery.isEmptyObject(r.data.userList)==true){
																fals=true
															}
														arr.push({
															title:name,
															id:ids,
															children:children,
															disabled:fals,
															spread:h,
															});
														})	
													},error:function(){
														layer.msg("操作失败！", {icon: 2});
														layer.close(index);
													}
												});	
											})
									}
							      }
							  });
						tree.render({
						    elem: '#test7'
						    ,data: arr
						    ,id: 'demoId1'
						    ,showCheckbox: true
						  });
						
						
						 tree.setChecked('demoId1',c);
						$(".type").parent().on('click',function(){
							if($(this).prev().prev().is(':checked')==false){ 
								$(this).prev().addClass("layui-form-checked")
								$(this).prev().prev().prop("checked",true);
								$(this).closest('.layui-tree-pack').prev().find('.layui-tree-iconClick').next().next().addClass("layui-form-checked");
								$(this).closest('.layui-tree-pack').prev().find('.layui-tree-iconClick').next().next().prev().prop("checked",true);
							}else{
								var arrat=new Array()
							$(this).closest('.layui-tree-pack').find(':input').each(function(i,o){
								if($(this).is(':checked')){
									arrat.push(1)
								}
							})
							if(arrat.length==1){
								$(this).closest('.layui-tree-pack').prev().find('.layui-tree-iconClick').next().next().prev().prop("checked",false);
								$(this).closest('.layui-tree-pack').prev().find('.layui-tree-iconClick').next().next().removeClass("layui-form-checked");
							}
								$(this).prev().removeClass("layui-form-checked")
								$(this).prev().prev().prop("checked",false);
							}
						})
							var	dicDiv=$("#layuiadmin-form-admin2");
						var index=layer.open({
								type:1,
								title:'修改杂工',
								area:['630px','500px'],
								btn:['确认','取消'],
								content:dicDiv,
								id: 'LAY_layuipro' ,
								btnAlign: 'c',
							    moveType: 1, //拖拽模式，0或者1
								success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole',
										'lay-submit' : ''
									})
						        },
								yes:function(){
									form.on('submit(addRole)', function(data) {
										data.field.type=6;
										 var arr=new Array()
										 var arrtem=new Array()
										 var ids=new Array()
										 var temporaryIds=new Array()
										var checkData = tree.getChecked('demoId1')
										$(checkData).each(function(j,k) {   
											$(k.children).each(function(o,l) {  
												if(l.falsew==1){
													arrtem.push(l.userId);
											   		temporaryIds.push(l.id);
												}
												if(l.falsew==0){
													arr.push(l.userId); 
												    ids.push(l.id);
												}
											})
										});
										 data.field.id=choosed[0].id
										 data.field.ids=arr
										 data.field.temporaryIds=arrtem
										 data.field.userIds=ids
										 data.field.temporaryUserIds=temporaryIds
										 data.field.type=2
										 if($("#allotTime").val()==""){
											 layer.msg("日期不能为空",{icon:2});
												return;
										 }
										 if(arr.length<=0 && arrtem.length<=0){
											 layer.msg("领取人不能为空",{icon:2});
												return;
										 }
										 if($("#name").val()==""){
											 return layer.msg("工序不能为空", {icon:2 });
										  }
										 if($("#startTimes").val()!="" && $("#time").val()!=""){
											  return layer.msg("填写开始时间后不能填写现场管理时间", {icon:2 });
										  }
										mainJs.fupdate(data.field); 
										document.getElementById("layuiadmin-form-admin2").reset();
										layer.close(index);
									})
								},end:function(){ 
						        	document.getElementById("layuiadmin-form-admin2").reset();
						        	layui.form.render();
								  }
							})
							break;
						
						case 'addIp':
							var choosed=layui.table.checkStatus("tableData").data;
							if(choosed.length==0){
								layer.msg("请选择一条信息进行编辑",{icon:2});
								return;
							}
							var arrytw=new Array()
							var arryth=new Array()
							var tasksId=new Array()
							var f;
							var g;
							$(choosed).each(function(j,k) {
								  arr=k.id;  
								  tasksId.push(k.id);  
								  arrytw.push(k.name);
								  arryth.push(k.performance); 
								  if(k.performance==""){
									  f=0;
								  }else{
									  g=1;
								  }
								  if(k.performance!=""){
									  g=1;
								  }else{
									  f=0;
								  }
								});
							  	if(f==0 && g==1){
									return layer.msg("不能同时选择新增修改", {icon: 2});
								} 
								var  update;
								  if(f==0){
									  update=0;  
								  }
								  if(g==1){
									  update=1;  
								  }
								  var ss="";
								  var htmltwo = "";
								   var data={
										  id:arr,
								   }
								   if(update==0){
				      				$.ajax({
										url:"${ctx}/farragoTask/taskUser",
										data:data,
										type:"GET",
										beforeSend:function(){
											indexs = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										
										success:function(result){
											$(result.data).each(function(i,o){
												htmltwo +='<input type="checkbox" lay-skin="primary" class="stuCheckBoxee"   value="'+o.id+'" data-username="'+o.userName+'" title="'+o.userName+'"></input>'
											})
											var ss="<div class='layui-form-item'><label class='layui-form-label'>完成人：</label><div class='layui-input-block equalesDiv' id='usercheck' checkallee ><input type='checkbox'  lay-filter='check' lay-skin='primary' class='allCkecks' title='全选'></input>"+htmltwo+"</div></div>"
											//var s="<div class='input-group checkallee'><input type='checkbox' lay-skin='primary' class='ckecks'><span class='checkallFont'>全选</span></input></div>"
											/* $('#usercheck').html(s+htmltwo) */
											$('#findty').append(ss)
											
											form.on('checkbox(check)', function(data){
													if(data.elem.checked){
													 $(this).parent().find('.stuCheckBoxee').each(function(i,o){
														 $(this).prop("checked",true);
														 form.render();
													 })
													}else{
														$(this).parent().find('.stuCheckBoxee').each(function(i,o){
															 $(this).prop("checked",false);
															 form.render();
														 })
													}
												})
														form.render();
											layer.close(indexs);
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									});
							
								 //遍历杂工加绩比值
									var html=""
									var htm=""
										for (var i = 0; i < arrytw.length; i++) {
										var array_element = arrytw[i];
										var array_element2 =arryth[i];
											var id="";
											var htmls="<option>请选择</option>"
													$.ajax({
														url:"${ctx}/task/pickTaskPerformance",
														type:"GET",
														data:data,
														async: false,
														beforeSend:function(){
															index = layer.load(1, {
																  shade: [0.1,'#fff'] //0.1透明度的白色背景
																});
														},
														success:function(result){
															$(result.data).each(function(i,o){
																var a="";
																if(o.checked==1){
																	id=o.name
																}
																	ids=array_element2==""?id:array_element2;
																
															htmls+='<option value="'+o.name+'" data-number="'+o.number+'" '+(ids==o.name ? "selected" : "")+'>'+o.name+'</option>'
															
															})
															layer.close(index);
														},error:function(){
															layer.msg("操作失败！", {icon: 2});
															layer.close(index);
														}
													});
											htm+="<div class='layui-form-item' id='findty'><table ><tr><td><label class='layui-form-label'>工序：</label><div class='layui-input-block'><input type='text' class='layui-input' readonly='readonly' id='number' value="+array_element+"></div></td><td><label class='layui-form-label'>加绩工序：</label><div class='layui-input-block'><select class='selectchangtw'>"+htmls+"</select></div></td></tr><tr><td>&nbsp;</td></tr></table>"
											$('#findLayui').html(htm);
										}
								   }else{
									 //  修改加绩
									 //遍历杂工加绩比值
										var html=""
										var htm=""
											for (var i = 0; i < arrytw.length; i++) {
											var array_element = arrytw[i];
											var array_element2 =arryth[i];
												var id="";
												var htmls="<option>请选择</option>"
														$.ajax({
															url:"${ctx}/task/pickTaskPerformance",
															type:"GET",
															data:data,
															async: false,
															beforeSend:function(){
																index = layer.load(1, {
																	  shade: [0.1,'#fff'] //0.1透明度的白色背景
																	});
															},
															success:function(result){
																$(result.data).each(function(i,o){
																	var a="";
																	if(o.checked==1){
																		id=o.name
																	}
																		ids=array_element2==""?id:array_element2;
																	
																htmls+='<option value="'+o.name+'" data-number="'+o.number+'" '+(ids==o.name ? "selected" : "")+'>'+o.name+'</option>'
																})
																layer.close(index);
															},error:function(){
																layer.msg("操作失败！", {icon: 2});
																layer.close(index);
															}
														});
												$.ajax({
													url:"${ctx}/farragoTask/taskUser",
													data:{id:tasksId[i]},
													type:"GET",
													async: false,
													beforeSend:function(){
														indexs = layer.load(1, {
															  shade: [0.1,'#fff'] //0.1透明度的白色背景
															});
													},
													success:function(result){
														var userArr=new Array()
														$.ajax({
															url:"${ctx}/farragoTask/getUserPerformance",
															data:{id:tasksId[i]},
															type:"GET",
															async: false,
															success:function(result){
																$(result.data).each(function(i,o){
																	userArr.push(o.username)
																})
															},error:function(){
																layer.msg("操作失败！", {icon: 2});
																layer.close(index);
															}
														});
														var htmltwo="";
														$(result.data).each(function(j,o){
															var flasg=false;
															for (var i = 0; i < userArr[0].length; i++) {
																if(userArr[0][i] ==o.userName){
																	flasg=true;
																}
															}
															htmltwo +='<input type="checkbox" lay-skin="primary" class="stuCheckBoxee" '+(flasg==true ? "checked" : "")+'  value="'+o.id+'" data-username="'+o.userName+'" title="'+o.userName+'"></input>'
														})
														var ss=""
												htm+="<div class='layui-form-item' id='findty'><table ><tr><td><label class='layui-form-label'>工序：</label><div class='layui-input-block'><input type='text' readonly='readonly' class='layui-input' id='number' value="+array_element+"></div></td><td><label class='layui-form-label'>加绩工序：</label><div class='layui-input-block'><select class='selectchangtw'>"+htmls+"</select></div></td></tr><tr><td>&nbsp;</td></tr></table><div class='layui-form-item'><label class='layui-form-label'>完成人：</label>"+
												"<div class='layui-input-block equalesDiv' id='usercheck' checkallee >"+
												"<input type='checkbox'  lay-filter='check' lay-skin='primary' class='allCkecks' title='全选'></input>"+htmltwo+"</div></div>"
												$('#findLayui').html(htm);
												form.on('checkbox(check)', function(data){
													if(data.elem.checked){
													 $(this).parent().find('.stuCheckBoxee').each(function(i,o){
														 $(this).prop("checked",true);
														 form.render();
													 })
													}else{
														$(this).parent().find('.stuCheckBoxee').each(function(i,o){
															 $(this).prop("checked",false);
															 form.render();
														 })
													}
												})
														form.render();
														layer.close(indexs);
													},error:function(){
														layer.msg("操作失败！", {icon: 2});
														layer.close(index);
													}
												});
											}
								   }
							var	dicDiv=$("#layuiadmin-form-admin3");
							var index=layer.open({
								type:1,
								title:'加绩',
								area:['700px','500px'],
								btn:['确认','取消'],
								content:dicDiv,
								id: 'LAY_layuipro2' ,
								btnAlign: 'c',
							    moveType: 1, //拖拽模式，0或者1
								success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole',
										'lay-submit' : ''
									})
						        },
								yes:function(){
									form.on('submit(addRole)', function(data) {
										var arry=new Array()
										var arrys=new Array()
										$(".stuCheckBoxee:checked").each(function() {  
										    arry.push($(this).val());   
										}); 
										$(".allCkecks").each(function(){
											var arryAll=new Array()
											$(this).parent().find('.stuCheckBoxee:checked').each(function(i,o){
												arryAll.push($(this).val());
											})
										arrys.push(arryAll)
										})
										if(arr.length<=0){
											return layer.msg("至少选择一个员工！", {icon: 2});
										}
										var performanceNumber=new Array()
										var performance=new Array()
										var fal=false;
										$(".selectchangtw option:selected").each(function() {
											performanceNumber.push($(this).data('number'));
											performance.push($(this).text());
											if($(this).text()=="请选择"){
												fal=true;
											}
										})
										if(fal==true){
											return layer.msg("工序不能为空", {icon: 2});
										}
										if(update==1){
											for (var i = 0; i < tasksId.length; i++) {
											 data.field.taskIds=tasksId[i]
											 data.field.performanceNumber=performanceNumber[i]
											 data.field.performance=performance[i]
											 data.field.update=update
											 data.field.ids= arrys[i]
										     mainJs.fupdateTask(data.field);  
											}
										}
										if(update==0){
											data.field.taskIds=tasksId
											 data.field.ids=arry
											 data.field.performanceNumber=performanceNumber
											 data.field.performance=performance
											 data.field.update=update	
										     mainJs.fupdateTask(data.field); 
										}
										document.getElementById("layuiadmin-form-admin3").reset();
										layer.close(index);
									})
								},end:function(){ 
						        	document.getElementById("layuiadmin-form-admin3").reset();
						        	layui.form.render();
								  }
							})
							break;
							
						case 'endTask':
							var choosed=layui.table.checkStatus("tableData").data;
							if(choosed.length==0){
								layer.msg("请选择一条信息进行编辑",{icon:2});
								return;
							}
							if(choosed.length>1){
								layer.msg("无法同时编辑多条信息",{icon:2});
								return;
							}
							var index=layer.open({
								type:1,
								title:'结束时间：',
								area:['250px','200px'],
								btn:['确认','取消'],
								content:['<div>',
						         	'<input id="endTime" style="margin-top: 36px;text-align: center; width:200px; margin-left: 25px;" placeholder="结束时间" class="layui-input laydate-icon">',
							         '</div>',].join(' '),
								id: 'LAY_layuipro2' ,
								btnAlign: 'c',
							    moveType: 1, //拖拽模式，0或者1
								success : function(layero, index) {
									laydate.render({
										elem: '#endTime',
										type: 'datetime',
										value : new Date(),
									});
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole',
										'lay-submit' : ''
									})
						        },
								yes:function(){
									form.on('submit(addRole)', function(data) {
										var end = $('#endTime').val();
										 var data={
											id: choosed[0].id,
											startTime: choosed[0].startTime,
											endTime: end,
										}
										  mainJs.endTask(data);  
										layer.close(index);
									})
								},end:function(){ 
						        	document.getElementById("layuiadmin-form-admin2").reset();
						        	layui.form.render();
								  }
							})
							break;
							
							/* case 'markup':
								var choosed=layui.table.checkStatus("tableData").data;
								if(choosed.length==0){
									layer.msg("请选择一条信息进行编辑",{icon:2});
									return;
								}
								if(choosed.length>1){
									layer.msg("无法同时编辑多条信息",{icon:2});
									return;
								}
								var dicDiv=$('#working');
								var data={
									id:choosed[0].id,
								}
								mainJs.loadworkingMarkup(data);
								layer.open({
							         type: 1
							        ,title: '查看加绩' //不显示标题栏
							        ,closeBtn: false
							        ,zindex:-1
							        ,offset:'80px'
									,area:['40%','30%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro2' //设定一个id，防止重复弹出
							        ,btn: ['取消']
							        ,btnAlign: 'c'
							        ,moveType: 1 //拖拽模式，0或者1
							        ,content:dicDiv
							        ,success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole2',
											'lay-submit' : ''
										})
							        }
							        ,end:function(){
									  } 
							      });
							break; */
							case 'query':
								var choosed=layui.table.checkStatus("tableData").data;
								if(choosed.length==0){
									layer.msg("请选择一条信息进行查看",{icon:2});
									return;
								}
								if(choosed.length>1){
									layer.msg("无法同时查看多条信息",{icon:2});
									return;
								}
								var dicDiv=$('#working');
								var data={
									id:choosed[0].id,
								}
								mainJs.loadworkingTable(data);
								var dicDiv=$('#layuiShare');
								layer.open({
							         type: 1
							        ,title: '人员详情' //不显示标题栏
							        ,closeBtn: false
							        ,zindex:-1
									,area:['50%','80%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro2' //设定一个id，防止重复弹出
							        ,btn: ['取消']
							        ,btnAlign: 'c'
							        ,moveType: 1 //拖拽模式，0或者1
							        ,content:dicDiv
							        ,success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole2',
											'lay-submit' : ''
										})
							        }
							        ,end:function(){
							        	$("#layuiShare").hide();
									  } 
							      });
								break;
							case 'delete':
								// 获得当前选中的
								var choosed=layui.table.checkStatus("tableData").data;
								var arr=new Array()
								for (var i = 0; i < choosed.length; i++) {
									arr.push(choosed[i].id);
								}
								layer.confirm('您是否确定要删除选中的条记录？', function() {
									var postData = {
										id:arr,
										type:2
									}
									$.ajax({
										url: "${ctx}/farragoTask/delete",
										data: postData,
										traditional: true,
										type: "GET",
										beforeSend: function() {
											index;
										},
										success: function(result) {
											if(0 == result.code) {
												var configTemp = tablePlug.getConfig("tableData");
									            if (configTemp.page && configTemp.page.curr > 1) {
									              table.reload("tableData", {
									                page: {
									                  curr: configTemp.page.curr - 1
									                }
									              })
									            }else{
									            	table.reload("tableData", {
										                page: {
										                }
										              })
									            };
												layer.msg(result.message, {
													icon: 1,
													time:800
												});
											} else {
												layer.msg(result.message, {
													icon: 2,
													time:800
												});
											}
										},
										error: function() {
											layer.msg("操作失败！", {
												icon: 2
											});
										}
									});
									layer.close(index);
								});
								break;
						} 
			});
			
			//监听搜索
			form.on('submit(LAY-search)', function(obj) {		//修改此处
				var field = obj.field;
				field.type=2;
				var orderTime=$("#startTime").val().split('~');
				field.orderTimeBegin=orderTime[0]+' '+'00:00:00';
				field.orderTimeEnd=orderTime[1]+' '+'23:59:59';
				table.reload('tableData', {
					where: field,
					 page: { curr : 1 }
				});  
			});
			$(document).on('click', '.layui-table-view tbody tr', function(event) {
				var elemTemp = $(this);
				var tableView = elemTemp.closest('.layui-table-view');
				var trIndex = elemTemp.data('index');
				tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
			})
			//封装ajax主方法
			var mainJs = {
				//新增							
			    fAdd : function(data){
			    	$.ajax({
						url: "${ctx}/farragoTask/addFarragoTask",
						data: data,
						type: "POST",
						traditional: true,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							if(0 == result.code) {
								$(".checkworking").text("");
							 	 table.reload("tableData", {
					                page: {
					                }
					              }) 
								layer.msg(result.message, {
									icon: 1,
									time:800
								});
							
							} else {
								layer.msg(result.message, {
									icon: 2,
									time:800
								});
							}
						},
						error: function() {
							layer.msg("操作失败！请重试", {
								icon: 2
							});
						},
					});
			    	//修改
					layer.close(index);
			    },
			    
			    fupdate : function(data){
			    	$.ajax({
						url: "${ctx}/farragoTask/updateFarragoTask",
						data: data,
						type: "POST",
						traditional: true,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							if(0 == result.code) {
								$(".checkworking").text("");
							 	 table.reload("tableData", {
					                page: {
					                }
					              }) 
								layer.msg(result.message, {
									icon: 1,
									time:800
								});
							
							} else {
								layer.msg(result.message, {
									icon: 2,
									time:800
								});
							}
						},
						error: function() {
							layer.msg("操作失败！请重试", {
								icon: 2
							});
						},
					});
			    	layer.close(index);
			    },
			    
			    endTask : function(data){
			    	$.ajax({
						url: "${ctx}/farragoTask/overFarragoTask",
						data: data,
						type: "POST",
						traditional: true,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							if(0 == result.code) {
							 	 table.reload("tableData", {
					                page: {
					                }
					              }) 
								layer.msg(result.message, {
									icon: 1,
									time:800
								});
							
							} else {
								layer.msg(result.message, {
									icon: 2,
									time:800
								});
							}
						},
						error: function() {
							layer.msg("操作失败！请重试", {
								icon: 2
							});
						},
					});
			    	layer.close(index);
			    },
			    
			    fupdateTask : function(data){
			    	$.ajax({
						url: "${ctx}/farragoTask/giveTaskPerformance",
						data: data,
						type: "POST",
						traditional: true,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							if(0 == result.code) {
							 	 table.reload("tableData", {
					                page: {
					                }
					              }) 
								layer.msg(result.message, {
									icon: 1,
									time:800
								});
							} else {
								layer.msg('加绩失败', {
									icon: 2,
									time:800
								});
							}
						},
						error: function() {
							layer.msg("操作失败！请重试", {
								icon: 2
							});
							layer.close(index);
						},
					});
			    	layer.close(index);
			    },
			    
		    loadworkingTable:function(data){
				table.render({
							elem: '#layuiShare2',
							where:data,
							url: '${ctx}/farragoTask/taskUser',
							data:[],
							loading: true,
							toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
							totalRow: true,		 //开启合计行 */
							colFilterRecord: true,
							smartReloadModel: true,// 开启智能重载
							parseData: function(ret) {
								return {
									code: ret.code,
									msg: ret.message,
									data: ret.data
								}
							},
							cols: [
								[{
									field: "id",
									title: "id",
									align: 'center',
								},{
									field: "userName",
									title: "姓名",
									align: 'center',
								}
								]
							],
									});
			},
			loadworkingMarkup:function(data){
				var html="";
				$.ajax({
					url:"${ctx}/farragoTask/getUserPerformance",
					data:data,
					type:"GET",
					beforeSend:function(){
						index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							});
					},
					
					success:function(result){
						$(result.data).each(function(i,o){
						html+=o.performance+":"+o.username+"&nbsp&nbsp&nbsp&nbsp"
						})
						$('#content').html(html);
						layer.close(index);
						
					},error:function(){
						layer.msg("操作失败！", {icon: 2});
						layer.close(index);
					}
				});
			},
			}

		}
	)
    </script>

</body>

</html>