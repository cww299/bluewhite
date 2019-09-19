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
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />


</head>

<body>
	
<!-- <div class="panel panel-default">
	<div class="panel-body">
		<table>
			<tr>
				<td>批次名:</td>
				<td><input type="text" name="number" id="number"
					placeholder="请输入批次号"
					class="form-control search-query number" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>工序名称:</td>
				<td><input type="text" name="name" id="name"
					placeholder="请输入产品名称"
					class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>开始时间:</td>
				<td><input id="startTime" placeholder="请输入开始时间"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>结束时间:</td>
				<td><input id="endTime" placeholder="请输入结束时间"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>
					<span class="input-group-btn">
						<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask"> 查&nbsp;找</button> </span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td>
					<span class="input-group-btn"> <button type="button" id="addgroup" class="btn btn-success btn-sm btn-3d pull-right"> 新增杂工</button> </span></td>
			</tr>
		</table>
		<h1 class="page-header"></h1>
		<table class="table table-hover">
			<thead>
				<tr>
					<th class="text-center">批次名</th>
					<th class="text-center">日期</th>
					<th class="text-center">工序名</th>
					<th class="text-center">现场管理时间</th>
					<th class="text-center">备注</th>
					<th class="text-center">工序加绩选择</th>
					<th class="text-center">任务价值</th>
					<th class="text-center">B工资净值</th>
					<th class="text-center">添加工价</th>
					<th class="text-center">人员详情</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
			<tbody id="tablecontent">
			</tbody>
			<thead>
				<tr>
					<td class="text-center">合计</td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center" id="totale"></td>
					<td class="text-center" id="totaltw"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
				</tr>
			</thead>
		</table>
		<div id="pager" class="pull-right"></div>
	</div>	
</div>


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
	隐藏框 新增杂工开始 
	<div id="addDictDivType" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeForm">
				<div class="row col-xs-12  col-sm-12  col-md-12 ">
					<div class="form-group">
						<label class="col-sm-3 control-label">日期:</label>
						<div class="col-sm-6">
							<input id="Time" placeholder="时间可不填"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#Time', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
					</div>

					<div class="form-group">

						<label class="col-sm-3 control-label">批次名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control bacth">
						</div>
					</div>
					<div class="form-group">

						<label class="col-sm-3 control-label">工序名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control sumnumber">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label">现场管理时间</label>
						<div class="col-sm-6 ">
							<input type="text" class="form-control timedata">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label">加绩工序选择</label>
						<div class="col-sm-6 working"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">完成人</label>
						<div class="col-sm-6 complete">
							<input type="text" class="form-control">
						</div>
						<div class="col-sm-2 select"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">备注</label>
						<div class="col-sm-6">
							<input type="text" class="form-control remarks">
						</div>
					</div>
				</div>
			</form>
		</div>

	</div> -->
	<!--隐藏框 新增结束  -->

<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>批次名:</td>
							<td><input   name="bacth" placeholder="请输入批次名" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>工序名称:</td>
							<td><input   name="name" placeholder="请输入工序名称" class="layui-input laydate-icon"></td>
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

<form action="" id="layuiadmin-form-admin2"
		style="padding: 20px 0px 0 50px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">日期</label>
					<div class="layui-input-inline">
						<input type="text" 
							style="width: 190px; position: absolute; float: left;" name="allotTime"
							id="time" lay-verify="tradeDaysTime" placeholder="请输入日期"
							class="layui-input laydate-icon">
					</div>
			</div>
				
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">批次名</label>
				<div class="layui-input-inline">
					<input name="bacth" style="width:190px;" lay-filter="id" id="productName" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
		
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">工序名</label>
				<div class="layui-input-inline">
					<input name="name" style="width:190px;" lay-filter="id" id="bacthNumber" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">现在管理时间</label>
				<div class="layui-input-inline">
					<input name="time" style="width:190px;" lay-filter="id"  lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
			
			<div class="layui-form-item" >
				<label class="layui-form-label" style="width: 100px;">加绩工序</label>
				<div class="layui-input-inline">
					<select class="form-control" lay-filter="lay_selecte4" style="width:210px;"  id="workingtw"></select>
				</div>
			</div>
			
			<div class="layui-form-item" style="float: left;">
				<label class="layui-form-label" style="width: 100px;">完成人</label>
				<div class="layui-input-inline">
					<select class="form-control" lay-filter="lay_selecte3" style="width:190px;"  id="complete"></select>
				</div>
				<div style="float: right; margin-left: 10px" class="select"></div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">备注</label>
				<div class="layui-input-inline">
					<input name="remarks" style="width:190px;" lay-filter="id" id="remarks" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>

<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="add">新增杂工管理</span>
			</div>
</script>
<script type="text/html" id="barDemo">
			<button type="button" class="layui-btn layui-btn-normal" lay-event="query">查看人员</button>
</script>
<script type="text/html" id="barDemo2">
			<button type="button" class="layui-btn-sm layui-btn-danger" lay-event="delete">删除</button>
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
						$('#workingtw').html(html);
						form.render(); 
					}
				});
			
			var getdata={type:"6",}
		    $.ajax({
			      url:"${ctx}/production/getGroup",
			      data:getdata,
			      type:"GET",
	      		  success: function (result) {
	      			  $(result.data).each(function(k,j){
	      				htmlsh +='<option value="">全部</option><option value="'+j.id+'">'+j.name+'</option>'
	      			  });  
	      			  $("#complete").html(htmlsh);
	      			form.render(); 
			      }
			  });
			
			form.on('select(lay_selecte3)', function(data){
				var data={
					  id:data.value,
					  type:6,
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
				url: '${ctx}/farragoTask/allFarragoTask',
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
						field: "bacth",
						title: "批次名",
						align: 'center',
						search: true,
						edit: false,
					},{
						field: "allotTime",
						title: "时间",
						align: 'center',
					},{
						field: "name",
						title: "工序名",
						align: 'center',
					},{
						field: "time",
						title: "现场管理时间",
						align: 'center',
						edit: false,
					},{
						field: "remarks",
						title: "备注",
						align: 'center',
						edit: false,
					},{
						field: "performance",
						title: "工序加绩选择",
						align: 'center',
						edit: false,
					},{
						field: "price",
						title: "任务价值",
						align: 'center',
					},{
						field: "payB",
						title: "B工资净值",
						align: 'center',
						edit: false,
					},{
						field: "performancePrice",
						title: "添加工价",
						align: 'center',
					},{
						field: "status",
						title: "人员详情",
						align: 'center',
						toolbar: '#barDemo',
					},{
						field: "",
						title: "操作",
						align: 'center',
						toolbar:'#barDemo2',
					}]
				],
				
						});
			
			//监听工具事件
			table.on('tool(tableData)', function(obj){
				 var data = obj.data;
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
				case 'delete':
					// 获得当前选中的
					layer.confirm('您是否确定要删除选中的条记录？', function() {
						var postData = {
							id:data.id,
							type:6
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
			})
			
			table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
						case 'add':
							var	dicDiv=$("#layuiadmin-form-admin2");
							layer.open({
								type:1,
								title:'新增杂工',
								area:['30%','50%'],
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
										$(".stuCheckBox:checked").each(function() {   
											arr.push($(this).val())
										});
										data.field.performanceNumber=$("#workingtw option:selected").data('value');
										data.field.performance=$("#workingtw option:selected").text()=="请选择" ? "" : $("#workingtw option:selected").text();
										data.field.userIds=arr;
										  mainJs.fAdd(data.field); 
										document.getElementById("layuiadmin-form-admin2").reset();
							        	layui.form.render();
									})
								},end:function(){ 
						        	document.getElementById("layuiadmin-form-admin2").reset();
						        	layui.form.render();
								  }
							})
							break;
						}
			});
			
			//监听搜索
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
					layer.close(index);
			    },
		    loadworkingTable:function(data){
						var html="";
						$.ajax({
							url:"${ctx}/farragoTask/taskUser",
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
	
 /*   jQuery(function($){
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
			var _cache;
			this.setCache = function(cache){
		  		_cache=cache;
		  	}
		  	this.getCache = function(){
		  		return _cache;
		  	}
			this.getCount = function(){
		  		return _count;
		  	}
		  	this.setCount = function(count){
		  		_count=count;
		  	}
			 var data={
						page:1,
				  		size:13,	
				  		type:1,

				} 
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/farragoTask/allFarragoTask",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			$("#totaltw").text(result.data.statData.stateCount)
		      			  $("#totale").text(result.data.statData.statAmount)
		      			 $(result.data.rows).each(function(i,o){
		      				html +='<tr>'
		      				+'<td class="text-center edit name">'+o.bacth+'</td>'
		      				+'<td class="text-center edit name">'+o.allotTime+'</td>'
		      				+'<td class="text-center edit name">'+o.name+'</td>'
		      				+'<td class="text-center edit name">'+o.time+'</td>'
		      				+'<td class="text-center edit name">'+o.remarks+'</td>'
		      				+'<td class="text-center edit name">'+o.performance+'</td>'
		      				+'<td class="text-center edit name">'+parseFloat((o.price).toFixed(3))+'</td>'
		      				+'<td class="text-center edit name">'+parseFloat((o.payB).toFixed(3))+'</td>'
		      				+'<td class="text-center edit name">'+parseFloat((o.performancePrice).toFixed(3))+'</td>'
		      				+'<td class="text-center"><button class="btn btn-primary btn-trans btn-sm savemode" data-toggle="modal" data-target="#myModal" data-id="'+o.id+'")">查看人员</button></td>'
							+'<td class="text-center"><button class="btn btn-sm btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>'
							
		      			}); 
		      			self.setCount(result.data.pageNum)
				        //显示分页
					   	 laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		 
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		type:1,
									  		name:$('#name').val(),
								  			bacth:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(), 
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   	self.loadEvents();
					   
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			
			this.loadEvents = function(){
				//删除方法
				$('.delete').on('click',function(){
					var postData = {
							id:$(this).data('id'),
					}
					
					var index;
					 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
					$.ajax({
						url:"${ctx}/farragoTask/delete",
						data:postData,
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							if(0==result.code){
							layer.msg("删除成功！", {icon: 1});
							var data = {
				        			page:self.getCount(),
							  		size:13,
							  		type:1,
							  		name:$('#name').val(),
						  			bacth:$('#number').val(),
						  			orderTimeBegin:$("#startTime").val(),
						  			orderTimeEnd:$("#endTime").val(), 
						  	}
							self.loadPagination(data)
							layer.close(index);
							}else{
								layer.msg("删除失败！", {icon: 1});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					 })
				})
				
				//人员详细显示方法
				$('.savemode').on('click',function(){
					var id=$(this).data('id')
					 var display =$("#savegroup").css("display")
					 if(display=='none'){
							$("#savegroup").css("display","block");  
						}
					var postData={
							id:id,
					}
					 var arr=new Array();
					var html="";
					$.ajax({
						url:"${ctx}/farragoTask/taskUser",
						data:postData,
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
							$('.modal-body').html(html);
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					
					
					
				})
				
				
			}
			this.events = function(){
				
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:1,
				  			name:$('#name').val(),
				  			bacth:$('#number').val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  	}
		            self.loadPagination(data);
				});
				//新增杂工
				$('#addgroup').on('click',function(){
					var _index
					var index
					var postData
					var dicDiv=$('#addDictDivType');
					var html=""
					var htmlth=""
					var data={
							type:1
					}
					
					//遍历人名组别
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			 $('.complete').html("<select class='form-control selectcomplete'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlth+"</select>") 
							//改变事件
			      			 $(".selectcomplete").change(function(){
			      				var htmltwo = "";
			      				var	id=$(this).val()
								   var data={
										  id:id,
										  type:1,
										  temporarilyDate:$('#Time').val(),
								   }
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
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBox" value="'+o.id+'" data-username="'+o.userName+'">'+o.userName+'</input></div>'
										})
										})
										var s="<div class='input-group'><input type='checkbox' class='checkall'>全选</input></div>"
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
					      }
					  });
					//遍历杂工加绩比值
					$.ajax({
						url:"${ctx}/farragoTask/farragoTaskPerformance",
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+='<option value="'+o.number+'" data-name="'+o.name+'">'+o.name+'</option>'
							})
							$('.working').html("<select class='form-control selectchang'><option value='0'>请选择</option>"+html+"</select>");
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['40%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  offset:'50px',
						  title:"新增杂工",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var performanceNumber=$(".selectchang").val();
							  var performance=$(".selectchang option:selected").text();
							 if(performance=="请选择"){
								 performance="";
							 }
							  var arr=new Array()
								$(".stuCheckBox:checked").each(function() {   
								    arr.push($(this).val());   
								});
							  if(arr.length<=0){
								 return layer.msg("领取人不能为空", {icon:2 });
							  }
							  if($(".sumnumber").val()==""){
									 return layer.msg("工序不能为空", {icon:2 });
								  }
							  if($(".bacth").val()==""){
									 return layer.msg("批次号不能为空", {icon:2 });
								  }
							  postData={
									  allotTime:$("#Time").val(),
									  name:$(".sumnumber").val(),
									  time:$(".timedata").val(),
									  remarks:$(".remarks").val(),
									  performance:performance,
									  performanceNumber:performanceNumber,
									  userIds:arr,
									  bacth:$(".bacth").val(),
									  type:1,
							  }
							  $.ajax({
									url:"${ctx}/farragoTask/addFarragoTask",
									data:postData,
						            traditional: true,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
											layer.msg("添加成功！", {icon: 1});
										 self.loadPagination(data); 
										 layer.close(_index);
										 //$('.addDictDivTypeForm')[0].reset();
											
										}else{
											layer.msg("添加失败", {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
							},
						  end:function(){
							  $('#addDictDivType').hide();
						
							  $('.addDictDivTypeForm')[0].reset(); 
							
						  }
					});
				})
			}
   	}
   			var login = new Login();
				login.init();
			}) */
    
    </script>

</body>

</html>