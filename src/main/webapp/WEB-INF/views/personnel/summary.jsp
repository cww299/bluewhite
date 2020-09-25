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
							<td><b class="red">*</b>日期:</td>
							<td><input id="startTime" name="orderTimeBegin" placeholder="请输入开始时间" 
									autocomplete="off" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<!-- 
							<td>姓名:</td>
							<td><select class="form-control" id="selectUserId" lay-search="true" name="userId"></select></td>
							<td>&nbsp;&nbsp;</td>
							
							<td>部门:</td>
							<td ><select class="form-control" name="orgNameId" lay-search="true" id="orgName"></select></td>
							<td>&nbsp;&nbsp;</td>
							 -->
							<td>餐费来源:</td>
							<td ><select class="form-control" name="site">
									<option value="">请选择</option>
									<option value="1">蓝白</option>
									<option value="2">9号食堂</option>
									<option value="3">总经办</option></select></td>
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



<div style="display: none;" id="layuiShare">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>查询月份:</td>
							<td><input id="monthDate9" style="width: 180px;" name="time" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-search2">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="layuiShare2"  class="table_th_search" lay-filter="layuiShare2"></table>
</div>

	
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
						type: 'month'
					});
					laydate.render({
						elem: '#monthDate9',
						type : 'month',
					}); 
					/*
					$.ajax({
						url: '${ctx}/system/user/findUserList',
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
				    */
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
							var data = ret.data || [];
							layui.each(data,function(index,d){
								d.mode1 = d.modeOne+' ～ ￥'+d.modeOneVal+' ～ ￥'+d.modeOnePrice;
								d.mode2 = d.modeTwo+' ～ ￥'+d.modeTwoVal+' ～ ￥'+d.modeTwoPrice;
								d.mode3 = d.modeThree+' ～ ￥'+d.modeThreeVal+' ～ ￥'+d.modeThreePrice;
								d.mode4 = d.modeFour+' ～ ￥'+d.modeFourVal+' ～ ￥'+d.modeFourPrice;
							})
							return {
								code: ret.code,
								msg: ret.message,
								data: data
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
								totalRow: true
							},{
								field: "mode1",
								title: "早餐次数/单餐金额/总金额",
								align: 'center',
							},{
								field: "mode2",
								title: "中餐次数/单餐金额/总金额",
								align: 'center',
							},{
								field: "mode3",
								title: "晚餐次数/单餐金额/总金额",
								align: 'center',
							},{
								field: "mode4",
								title: "夜宵次数/单餐金额/总金额",
								align: 'center',
							}]
						],
								});
				   	
					
4				
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData':
								var dicDiv=$('#layuiShare');
								/* table.reload("analysisRecuitsumday"); */
								layer.open({
							         type: 1
							        ,title: '每月食堂水电费用' //不显示标题栏
							        ,closeBtn: false
							        ,zindex:-1
							        ,area:['50%', '90%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro10' //设定一个id，防止重复弹出
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
										table.resize('layuiShare2');
							        }
							        ,end:function(){
							        	$("#layuiShare").hide();
									  } 
							      });
								break;
						}
					});
					
					var data="";
					form.on('submit(LAY-search2)', function(obj) {
						var field = obj.field;
						field.orderTimeBegin=field.time+'-01 00:00:00';
						eventd(field);
					})
					
					var eventd=function(data){
						table.reload("layuiShare2", {
							url: '${ctx}/personnel/getfindElectric',
							where:data,
			              })
					};
					
					table.render({
						elem: '#layuiShare2',
						where:data,
						data:[],
						totalRow: true,		 //开启合计行 */
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						cellMinWidth: 90,
						cols: [
							[{
								field: "name",
								title: "",
								align: 'center',
								totalRowText: '合计'
							},{
								field: "sum1",
								title: "缴费金额",
								align: 'center',
							},{
								field: "valPrice1",
								title: "占比",
								align: 'center',
								edit:"text",
							},{
								field: "val",
								title: "每月花费",
								align: 'center',
								totalRow: true
							},{
								field: "sumday1",
								title: "每天费用",
								align: 'center',
							}
							]
						],
						done:function(){
						
						}
					});
				   	var even=function(data){
						table.reload("tableData", {
							url: '${ctx}/personnel/getSummaryMeal' ,
							where:data,
							
			              });
			             
					};
					table.on('edit(layuiShare2)', function(obj) {
						var value = obj.value //得到修改后的值
						var	field = obj.data.modify //得到字段
							var postData = {
								id:8,
								[field]:value
							}
						$.ajax({
							url: '${ctx}/personnel/addPersonVaiable',
							type: "POST",
							data:postData,
							beforeSend: function() {
								index;
							},
							success: function(result) {
							if(result.code==0){
								layer.msg("修改成功！", {
									icon: 1
								});
								table.reload("layuiShare2");
								table.reload("tableData");
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
					});
					//监听搜索
					form.on('submit(LAY-search)', function(obj) {		
						var field = obj.field;
						if(!field.orderTimeBegin) {
							return layer.msg('日期不能为空！', { icon : 2 });
						}
						field.orderTimeBegin = field.orderTimeBegin + '-01 00:00:00';
						even(field)
					});
				}
			)
		</script>
</body>
</html>
