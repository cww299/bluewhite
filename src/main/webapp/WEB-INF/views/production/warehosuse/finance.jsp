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
<title>任务管理</title>
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
</head>

<body>
	
<!-- <div class="panel panel-default">
	<div class="panel-body">
		<table>
			<tr>
				<td>批次号:</td>
				<td><input type="text" name="number" id="number" placeholder="请输入批次号" class="form-control search-query number" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>产品名称:</td>
				<td><input type="text" name="name" id="name" placeholder="请输入产品名称" class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>工序名称:</td>
				<td><input type="text" name="procedureName" id="procedureName" placeholder="请输入工序名称" class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>开始时间:</td>
				<td><input id="startTime" placeholder="请输入开始时间" class="form-control laydate-icon" onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>结束时间:</td>
				<td><input id="endTime" placeholder="请输入结束时间" class="form-control laydate-icon" onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"></td>
				<td>&nbsp;&nbsp;</td>
				<td>工序:</td>
				<td><select class="form-control selectchoice">
						<option value="">请选择</option>
						<option value="0">质检工序</option>
						<option value="1">返工工序</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><span class="input-group-btn"><button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask">查&nbsp找</button></span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><span class="input-group-btn">
					<button type="button" class="btn btn-default btn-danger btn-sm btn-3d attendance">一键删除</button> </span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><span class="input-group-btn">
					<button type="button" class="btn btn-success  btn-sm btn-3d export"> 导出返工价值</button></span></td>
			</tr>
		</table>
		<h1 class="page-header"></h1>
		<table class="table table-condensed table-condensed table-hover">
			<thead>
				<tr>
					<th class="center"><label> <input type="checkbox"
							class="ace checks" /> <span class="lbl"></span>
					</label></th>
					<th class="text-center">批次号</th>
					<th class="text-center" style="width: 250px;">产品名</th>
					<th class="text-center">时间</th>
					<th class="text-center">工序</th>
					<th class="text-center">预计时间</th>
					<th class="text-center">任务价值</th>
					<th class="text-center">b工资净值</th>
					<th class="text-center">数量</th>
					<th class="text-center">工序加价</th>
					<th class="text-center">加绩工资</th>
					<th class="text-center">完成人</th>
					<th class="text-center">备注（实际时间）</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
			<tbody id="tablecontent">
	
			</tbody>
	
		</table>
		<div id="pager" class="pull-right"></div>
	</div>
</div>
					
	隐藏框 产品新增开始 
	<div id="addDictDivType" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeForm">
				<div class="form-group">
					<label class="col-sm-3 control-label">名称:</label>
					<div class="col-sm-6">
						<input type="text" id="groupName" class="form-control">
					</div>
				</div>
			</form>
		</div>
	</div>
	隐藏框 产品新增结束 



	<div id="savegroup" style="display: none;">
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">人员分组详情</h4>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
				</div>
				/.modal-content
			</div>
			/.modal
		</div>
	</div>
	隐藏框 产品新增结束 
	</section> -->


<div class="layui-tab layui-tab-card">
  <ul class="layui-tab-title">
    <li class="layui-this" id="pyA" style="width: 30%">A工资流水</li>
    <li id="pyB" style="width: 30%">B工资流水</li>
    <li id="pyc" style="width: 30%">杂工工资流水</li>
  </ul>
  <div class="layui-tab-content" style="height: 100px;">
    <div class="layui-tab-item layui-show">
	<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>员工姓名:</td>
							<td><input   name="userName" placeholder="请输入员工姓名" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>时间:</td>
							<td><input id="startTime" style="width: 300px;" name="startTime" placeholder="请输入时间" class="layui-input laydate-icon"></td>
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

<div style="display: none;" id="layuiShare">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<div style="">
						<table class="table table-hover" style="margin:auto;width:100%;">
								<tbody id="tableUserTime">
								</tbody>
						</table>
					</div>
				</div>
			</div>
</div>
</div>
    <div class="layui-tab-item">
		<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>批次:</td>
							<td><input   name="bacth" placeholder="请输入批次" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>产品:</td>
							<td><input   name="productName" placeholder="请输入产品" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>姓名:</td>
							<td><input   name="userName" placeholder="请输入姓名" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>时间:</td>
							<td><input id="startTime2" style="width: 300px;" name="startTime2" placeholder="请输入时间" class="layui-input laydate-icon"></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" id="LAY-search5" lay-submit lay-filter="LAY-search2">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="tableData2" class="table_th_search" lay-filter="tableData2"></table>
		</div>
	</div>
	</div>
    <div class="layui-tab-item">
		
		<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>工序:</td>
							<td><input   name="taskName" placeholder="请输入工序名" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>姓名:</td>
							<td><input   name="userName" placeholder="请输入姓名" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>时间:</td>
							<td><input id="startTime3" style="width: 300px;" name="startTime3" placeholder="请输入时间" class="layui-input laydate-icon"></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" id="LAY-search5" lay-submit lay-filter="LAY-search3">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="tableData3" class="table_th_search" lay-filter="tableData3"></table>
		</div>
	</div>
		
	</div>
  </div>
</div>

<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
			</div>
</script>

<script type="text/html" id="barDemo">
			<button type="button" class="layui-btn layui-btn-normal" lay-event="query">查看人员</button>
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
			var firstdate2 = year + '-' + p(month) + '-'+ x+date+' '+'00:00:00';
			var lastdate2 = year + '-' + p(month) + '-' + x+date+' '+'23:59:59';
			laydate.render({
				elem: '#startTime',
				type: 'datetime',
				range: '~',
				value : firstdate+' ~ '+lastdate,
			});
			laydate.render({
				elem: '#startTime2',
				type: 'datetime',
				range: '~',
				value : firstdate+' ~ '+lastdate,
			});
			laydate.render({
				elem: '#startTime3',
				type: 'datetime',
				range: '~',
				value : firstdate+' ~ '+lastdate,
			});
			//select全局变量
			var htmls = '<option value="0">请选择</option>';
			var htmlsh = '<option value="0">请选择</option>';
			var index = layer.load(1, {
				shade: [0.1, '#fff'] //0.1透明度的白色背景
			});
			layer.close(index);
			laydate.render({
				elem: '#time',
				type : 'datetime',
			});
			laydate.render({
				elem: '#allotTime',
				type : 'datetime',
			});
			var htmltwo="";
		   	tablePlug.smartReload.enable(true); 
			table.render({
				elem: '#tableData',
				size: 'lg', 
				url: '${ctx}/finance/allAttendancePay',
				where:{
					type:6,
					orderTimeBegin:firstdate,
			  		orderTimeEnd:lastdate,
				},
				request:{
					pageName: 'page' ,//页码的参数名称，默认：page
					limitName: 'size' //每页数据量的参数名，默认：limit
				},
				page: {
				},//开启分页
				loading: true,
				toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
				//totalRow: true,		 //开启合计行 */
				cellMinWidth: 90,
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
				cols: [
					[{
						type: 'checkbox',
						align: 'center',
					},{
						field: "userName",
						title: "姓名",
						align: 'center',
						search: true,
						edit: false,
					},{
						field: "allotTime",
						title: "时间",
						align: 'center',
					},{
						field: "workTime",
						title: "工作小时",
						align: 'center',
					},{
						field: "workPrice",
						title: "到岗预计每小时收入",
						align: 'center',
						edit: false,
					},{
						field: "payNumber",
						title: "A工资",
						align: 'center',
						edit: false,
					},{
						field: "turnWorkTime",
						title: "出勤时长",
						align: 'center',
						edit: 'text',
					},{
						field: "overTime",
						title: "加班时长",
						align: 'center',
						edit: 'text',
					}]
				],
				//下拉框回显赋值
				done: function(res, curr, count) {
					var tableView = this.elem.next();
					var tableElem = this.elem.next('.layui-table-view');
					layui.each(tableElem.find('.layui-table-box').find('select'), function(index, item) {
						var elem = $(item);
						elem.val(elem.data('value'));
					});
					form.render();
							},
						});
			
			//监听工具事件
			table.on('tool(tableData)', function(obj){
				 var data = obj.data;
				 console.log(data)
				switch(obj.event) {
				case 'query':
					$("#queryId").val(data.id)//把组ID放进查询  方便查询调用
					var data={
						id:data.id,
					}
					mainJs.loadworkingTable(data);
					var dicDiv=$('#layuiShare');
					layer.open({
				         type: 1
				        ,title: '人员详情' //不显示标题栏
				        ,closeBtn: false
				        ,zindex:-1
				        ,area:['40%', '20%']
				        ,shade: 0.5
				        ,id: 'LAY_layuipro29' //设定一个id，防止重复弹出
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
				}
			})
			
			table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
						case 'deleteSome':
							// 获得当前选中的
							var checkedIds = tablePlug.tableCheck.getChecked(tableId);
							layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
								var postData = {
									ids: checkedIds,
									type:6
								}
								$.ajax({
									url: "${ctx}/task/delete",
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
			
			//监听单元格编辑
			table.on('edit(tableData)', function(obj) {
				var value = obj.value ,//得到修改后的值
					data = obj.data ,//得到所在行所有键值
					field = obj.field, //得到字段
					id = data.id;
						var postData = {
							id:id,
							[field]:value
						}
						//调用新增修改
						mainJs.fUpdate(postData);
			});
			
			//监听搜索 A工资
			form.on('submit(LAY-search)', function(obj) {		//修改此处
				var field = obj.field;
				field.type=6;
				var orderTime=field.startTime.split('~');
				field.orderTimeBegin=orderTime[0];
				field.orderTimeEnd=orderTime[1];
				table.reload('tableData', {
					where: field,
					 page: { curr : 1 }
				});  
			});
			//监听搜索 B工资
			form.on('submit(LAY-search2)', function(obj) {		//修改此处
				var field = obj.field;
				field.type=6;
				var orderTime=field.startTime2.split('~');
				field.orderTimeBegin=orderTime[0];
				field.orderTimeEnd=orderTime[1];
				table.reload('tableData2', {
					where: field,
					 page: { curr : 1 }
				});  
			});
			// 监听 杂工
			form.on('submit(LAY-search3)', function(obj) {		//修改此处
				var field = obj.field;
				field.type=6;
				var orderTime=field.startTime3.split('~');
				field.orderTimeBegin=orderTime[0];
				field.orderTimeEnd=orderTime[1];
				table.reload('tableData3', {
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
			//B工资
			$("#pyB").on('click',function(){
				tablePlug.smartReload.enable(true); 
				table.render({
					elem: '#tableData2',
					size: 'lg', 
					url: '${ctx}/finance/allPayB',
					where:{
						type:6,
						orderTimeBegin:firstdate,
				  		orderTimeEnd:lastdate,
					},
					request:{
						pageName: 'page' ,//页码的参数名称，默认：page
						limitName: 'size' //每页数据量的参数名，默认：limit
					},
					page: {
					},//开启分页
					loading: true,
					toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
					//totalRow: true,		 //开启合计行 */
					cellMinWidth: 90,
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
					cols: [
						[{
							type: 'checkbox',
							align: 'center',
						},{
							field: "userName",
							title: "姓名",
							align: 'center',
							search: true,
							edit: false,
						},{
							field: "bacth",
							title: "批次号",
							align: 'center',
						},{
							field: "productName",
							title: "产品名",
							align: 'center',
						},{
							field: "procedureName",
							title: "工序名",
							align: 'center',
							edit: false,
							templet:function(d){
								return d.task.procedureName
							}
						},{
							field: "allotTime",
							title: "时间",
							align: 'center',
							edit: false,
						},{
							field: "performancePayNumber",
							title: "加绩工资",
							align: 'center',
							edit: 'text',
						},{
							field: "payNumber",
							title: "B工资",
							align: 'center',
							edit: 'text',
						}]
					],
					//下拉框回显赋值
					done: function(res, curr, count) {
						var tableView = this.elem.next();
						var tableElem = this.elem.next('.layui-table-view');
						layui.each(tableElem.find('.layui-table-box').find('select'), function(index, item) {
							var elem = $(item);
							elem.val(elem.data('value'));
						});
						form.render();
								},
							});
			})
			
			$("#pyc").on('click',function(){
				tablePlug.smartReload.enable(true); 
				table.render({
					elem: '#tableData3',
					size: 'lg', 
					url: '${ctx}/finance/allFarragoTaskPay',
					where:{
						type:6,
						orderTimeBegin:firstdate,
				  		orderTimeEnd:lastdate,
					},
					request:{
						pageName: 'page' ,//页码的参数名称，默认：page
						limitName: 'size' //每页数据量的参数名，默认：limit
					},
					page: {
					},//开启分页
					loading: true,
					toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
					//totalRow: true,		 //开启合计行 */
					cellMinWidth: 90,
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
					cols: [
						[{
							type: 'checkbox',
							align: 'center',
						},{
							field: "userName",
							title: "姓名",
							align: 'center',
							search: true,
							edit: false,
						},{
							field: "taskName",
							title: "杂工工序",
							align: 'center',
						},{
							field: "allotTime",
							title: "时间",
							align: 'center',
							edit: false,
						},{
							field: "performancePayNumber",
							title: "杂工加绩工资",
							align: 'center',
							edit: 'text',
						},{
							field: "payNumber",
							title: "杂工B工资",
							align: 'center',
							edit: 'text',
						}]
					],
					//下拉框回显赋值
					done: function(res, curr, count) {
						var tableView = this.elem.next();
						var tableElem = this.elem.next('.layui-table-view');
						layui.each(tableElem.find('.layui-table-box').find('select'), function(index, item) {
							var elem = $(item);
							elem.val(elem.data('value'));
						});
						form.render();
								},
							});
			})
			
			//封装ajax主方法
			var mainJs = {
				//新增							
			    fAdd : function(data){
			    	$.ajax({
						url: "${ctx}/task/addTask",
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
			
			//修改							
		    fUpdate : function(data){
		    	if(data.id==""){
		    		return;
		    	}
		    	$.ajax({
					url: "${ctx}/finance/updateAttendance",
					data: data,
					type: "GET",
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
		    loadworkingTable:function(data){
						var html="";
						$.ajax({
							url:"${ctx}/task/taskUser",
							data:data,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								$(result.data).each(function(i,o){
								html+=o.userName+"&nbsp&nbsp&nbsp&nbsp"
								})
								$('#tableUserTime').html(html);
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