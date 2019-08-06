<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>


<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>餐费汇总</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>

<body>
	<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>姓名:</td>
							<td><select class="form-control" id="selectUserId" lay-search="true" name="userId"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>日期:</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>部门:</td>
							<td ><select class="form-control" name="orgNameId" lay-search="true" id="orgName"></select></td>
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
			
			<shiro:hasAnyRoles name="superAdmin,personnel">
   				 <p id="totalAll" style="text-align:center;color:red;"></p>
			</shiro:hasAnyRoles> 
			
		</div>
	</div>

<form action="" id="layuiadmin-form-admin"
		style="display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" name="id" id="ids" style="display:none;">
			<div class="layui-form-item">
				<label class="layui-form-label">水费占比</label>
				<div class="layui-input-block">
					<input type="text"  name="keyValue" id="keyValue"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">电费占比</label>
				<div class="layui-input-block">
					<input type="text" name="keyValueTwo" id="keyValueTwo"
						lay-verify="required"
						class="layui-input laydate-icon">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">房租占比</label>
				<div class="layui-input-block">
					<input type="text" name="keyValueThree" id="keyValueThree"
						lay-verify="required"
						class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>		

	
<script type="text/html" id="toolbar">
	<div class="layui-btn-container layui-inline">
		<span class="layui-btn layui-btn-sm" lay-event="addTempData">预设水电</span>
	</div>
</script>
	
	<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug'
			}).define(
				['tablePlug', 'laydate', 'element'],
				function() {
					var $ = layui.jquery
						,layer = layui.layer //弹层
						,form = layui.form //表单
						,table = layui.table //表格
						,laydate = layui.laydate //日期控件
						,tablePlug = layui.tablePlug //表格插件
						,element = layui.element;
					
					//全部字段
					var allField;
					var self = this;
					this.setIndex = function(index){
				  		_index=index;
				  	}
				  	
				  	this.getIndex = function(){
				  		return _index;
				  	}
					//select全局变量
					var htmls = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
					});
				 
					$.ajax({
						url: '${ctx}/system/user/findAllUser',
						type: "GET",
						async: false,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							$(result.data).each(function(i, o) {
								htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
							})
							$("#selectUserId").append(htmls)
							form.render();
							layer.close(index);
						},
						error: function() {
							layer.msg("操作失败！", {
								icon: 2
							});
							layer.close(index);
						}
					});
					
					var getdataa={type:"orgName",}
					var htmlfrn= '<option value="">请选择</option>';
				    $.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdataa,
					      type:"GET",
					      async:false,
					      beforeSend:function(){
					    	  indextwo = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfrn +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });
			      			$("#orgName").append(htmlfrn);
			      			form.render();
			      			layer.close(indextwo);
					      }
					  });
				   	tablePlug.smartReload.enable(true); 
				   	var data="";
					table.render({
						elem: '#tableData',
						size: 'lg',
						where:data,
						data:[],
						where:data,
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						loading: true,
						toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								count:ret.data.total,
								data: ret.data
							}
						},
						cols: [
							[{
								field: "username",
								title: "姓名",
								align: 'center',
								totalRowText: '合计'
							},{
								field: "orgName",
								title: "部门",
								align: 'center',
								edit: false,
								filter:true,
							},{
								field: "sumPrice",
								title: "餐费汇总",
								align: 'center',
								edit: false,
								totalRow: true
							},{
								field: "modeOne",
								title: "早餐次数/金额",
								align: 'center',
								edit: false,
								templet:function(d){
									return d.modeOne+'     ～￥'+d.modeOnePrice
								}
							},{
								field: "modeTwo",
								title: "中餐次数/金额",
								align: 'center',
								edit: false,
								templet:function(d){
									return d.modeTwo+'     ～￥'+d.modeTwoPrice
								}
							},{
								field: "modeThree",
								title: "晚餐次数/金额",
								align: 'center',
								edit: false,
								templet:function(d){
									return d.modeThree+'     ～￥'+d.modeThreePrice
								}
							},{
								field: "modeFour",
								title: "夜宵次数/金额",
								align: 'center',
								edit: false,
								templet:function(d){
									return d.modeFour+'     ～￥'+d.modeFourPrice
								}
							}]
						],
								});
				   	
					
					
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData':
								$.ajax({
									url: '${ctx}/personnel/getpersonVariabledao',
									type: "GET",
									data:{
										type:5
									},
									async: false,
									beforeSend: function() {
										index;
									},
									success: function(result) {
										$(result.data).each(function(i, o) {
											$("#ids").val(o.id);
											$("#keyValue").val(o.keyValue);
											$("#keyValueTwo").val(o.keyValueTwo);
											$("#keyValueThree").val(o.keyValueThree);
										})
										
										layer.close(index);
									},
									error: function() {
										layer.msg("操作失败！", {
											icon: 2
										});
										layer.close(index);
									}
								});
								
								//报价修改
								var	dicDiv=$("#layuiadmin-form-admin");
									layer.open({
										type:1,
										title:'水费标准',
										area:['22%','30%'],
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
												$.ajax({
													url: '${ctx}/personnel/addPersonVaiable',
													type: "POST",
													data:data.field,
													async: false,
													beforeSend: function() {
														index;
													},
													success: function(result) {
													if(result.code==0){
														layer.msg("修改成功！", {
															icon: 1
														});
													}else{
														layer.msg("修改失败！", {
															icon: 2
														});
													}
														layer.close(index);
													},
													error: function() {
														layer.msg("操作失败！", {
															icon: 2
														});
														layer.close(index);
													}
												});
											})
										}
									})
								break;
						}
					});
					
				   	var even=function(data){
						table.reload("tableData", {
							url: '${ctx}/personnel/getSummaryMeal' ,
							where:data,
			              });
			             
					};
					
					//监听搜索
					form.on('submit(LAY-search)', function(obj) {		//修改此处
						var field = obj.field;
						var orderTime=field.orderTimeBegin.split('~');
						field.orderTimeBegin=orderTime[0];
						field.orderTimeEnd=orderTime[1];
						even(field)
					});
				}
			)
		</script>
</body>

</html>
