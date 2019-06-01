<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
  	<script src="${ctx }/static/js/vendor/typeahead.js"></script>
  	<link rel="stylesheet" href="${ctx }/static/css/bootstrap.min.css"> 
	<link rel="stylesheet" href="${ctx }/static/css/main.css">  <!-- 界面样式 -->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>利息申请</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>

<body>
	<div class="layui-card" style="height: 800px;">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>借款方:</td>
							<td><input type="text" name="customerName" id="firstNames" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td><select class="form-control" name="expenseDate" id="selectone">
									<option value="2018-10-08 00:00:00">申请日期</option>
								</select></td>
							<td>&nbsp;&nbsp;</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>是否核对:
							<td><select class="form-control" name="flag">
									<option value="">请选择</option>
									<option value="0">未核对</option>
									<option value="1">已核对</option>
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">
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

<!--新增  -->
<script type="text/html" id="addEditTpl">
	<form action="" id="layuiadmin-form-admin" style="padding: 20px 30px 0 60px;">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<input type="hidden" name="id" value="{{ d.id }}">
			<input type="hidden" name="type" value="10">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">借款方</label>
				<div class="layui-input-inline">
						<input type="text" value="{{d.custom.name }}" name="customerName" id="userId"
						placeholder="请输入借款方名称" class="layui-input" data-provide="typeahead">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">内容</label>
				<div class="layui-input-inline">
					<input type="text" value="{{d.content }}" name="content" id="content"
						lay-verify="required" placeholder="请输入内容" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">借款金额</label>
				<div class="layui-input-inline">
					<input type="text" value="{{d.money }}" name="money" id="money"
						lay-verify="required" placeholder="请输入支付金额" class="layui-input ">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">预计付款日期</label>
				<div class="layui-input-inline">
					<input type="text" value="{{d.expenseDate }}" name="expenseDate"
						id="expenseDate" lay-verify="required"
						placeholder="请输入预计付款日期 " class="layui-input laydate-icon">
				</div>
			</div>
			<button type="button" id="addEditSureBtn" lay-filter="addEditSureBtn" lay-submit style="display:none;"> 
		</div>
	</form>	
</script>	
<script type="text/html" id="toolbar">
	<div class="layui-btn-container layui-inline">
		<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增</span>
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
		<span class="layui-btn layui-btn-sm" lay-event="update">编辑</span>
	</div>
</script>

<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug: 'tablePlug/tablePlug',
}).define(
	['tablePlug', 'laydate', 'element','form'],
	function() {
		var $ = layui.jquery
			,layer = layui.layer
			,form = layui.form 
			,table = layui.table 
			,laydate = layui.laydate 
			,tablePlug = layui.tablePlug 
			,laytpl = layui.laytpl;

		var allField;
		var self = this;
		var _index;
		this.setIndex = function(index){
	  		_index=index;
	  	}
	  	this.getIndex = function(){
	  		return _index;
	  	}

		var mycars=new Array()
		$.ajax({
			url: '${ctx}/system/user/findAllUser',
			type: "GET",
			async: false,
			success: function(result) {
				$(result.data).each(function(i, o) {
					mycars.push(o.userName)
				})
			},
			error: function() {
				layer.msg("获取数据失败！", { icon: 2 });
			}
		});
		
		laydate.render({ elem: '#startTime', type: 'datetime', range: '~', });

	   	tablePlug.smartReload.enable(true); 
		table.render({
			elem: '#tableData',
			size: 'lg',
			height:'700px',
			url: '${ctx}/fince/getConsumption?type=10' ,
			request:{pageName: 'page' ,limitName: 'size'},
			page: {},
			loading: true,
			toolbar: '#toolbar', 
			cellMinWidth: 90,
			colFilterRecord: true,
			smartReloadModel: true,// 开启智能重载
			parseData: function(ret) {
				return {
					code: ret.code,
					msg: ret.message,
					count:ret.data.total,
					data: ret.data.rows
				}
			},
			cols: [[
			        { align: 'center', type: 'checkbox',  fixed: 'left' },
			        { align: 'center', field: "withholdReason", title: "借款方", templet: function(d){ return d.custom.name } },
			        { align: 'center', field: "content", title: "内容", },
				    { align: 'center', field: "money", title: "支付金额", }, 
				    { align: 'center', field: "expenseDate", title: "预计付款日期", }]
			],
		});

		//监听头工具栏事件
		table.on('toolbar(tableData)', function(obj) {
			var config = obj.config;
			var btnElem = $(this);
			var tableId = config.id;
			switch(obj.event) {
				case 'addTempData': addEidt(null); break;
				case 'update' :  addEidt('edit');  break;
				case 'deleteSome':	deletes(tableId);	break;
			}
		});
		
		function addEidt(type){
			var data={id:'',custom:{name:''},money:'',expenseDate:'',content:'借款利息',withholdMoney:'',withholdReason:''};
			var title="新增数据";
			var html="";
			var tpl=addEditTpl.innerHTML;
			var choosed=layui.table.checkStatus("tableData").data;
			if(type=='edit'){
				title="编辑数据"
				if(choosed.length>1){
					layer.msg("无法同时编辑多条信息",{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg("请选择信息编辑",{icon:2});
					return;
				}
				data=choosed[0];
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			var addEditWin = layer.open({
				type:1,
				title:title,
				area:['30%','60%'],
				btn:['确认','取消'],
				content:html,
				id: 'LAY_layuipro' ,
				btnAlign: 'c',
				yes:function(){
					$('#addEditSureBtn').click();
				}
			})
			form.on('submit(addEditSureBtn)', function(obj) {
				var submitData=obj.field;
				submitData.customId=self.getIndex();
				var load = layer.load(1);
		    	$.ajax({
					url: "${ctx}/fince/addConsumption",
					data: submitData,
					type: "POST",
					success: function(result) {
						if(0 == result.code) {
						 	table.reload("tableData");
						 	layer.close(addEditWin);
							layer.msg(result.message, {icon: 1, time:800 });
						} else {
							layer.msg(result.message, {icon: 2, time:800 });
						}
					},
					error: function() {
						layer.msg("操作失败！请重试", {icon: 2});
					},
				});
				layer.close(load);
			})
			form.render();
			laydate.render({ elem: '#expenseDate', type: 'datetime', });
			
			//提示查询
			 $("#userId").typeahead({
					source : function(query, process) {
						return $.ajax({
							url : '${ctx}/fince/findCustom',
							type : 'GET',
							data : { name:query, type:10 },
							success : function(result) {
								 var resultList = result.data.map(function (item) {	//转换成 json集合
				                        var aItem = {name: item.name, id:item.id}	//转换成 json对象
				                        return JSON.stringify(aItem);				//处理 json对象为字符串
				                    });
									 self.setIndex(0);
								//提示框返回数据
								 return process(resultList);
							},
						})
						//提示框显示
					}, highlighter: function (item) {
					    //转出成json对象
						 var item = JSON.parse(item);
						return item.name
						//按条件匹配输出
	                }, matcher: function (item) {
	                	//转出成json对象
				        var item = JSON.parse(item);
				        self.setIndex(item.id);
				    	return item.id
				    },
					//item是选中的数据
					updater:function(item){
						//转出成json对象
						var item = JSON.parse(item);
						self.setIndex(item.id);
							return item.name
					},
				});
		}
		function deletes(tableId){
			var checkedIds = tablePlug.tableCheck.getChecked(tableId);
			if(checkedIds.length<1){
				layer.msg('至少选择一条数据',{icon:2});
				return;
			}
			layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
				var index = layer.load(1);
				$.ajax({
					url: "${ctx}/fince/deleteConsumption",
					data: { ids: checkedIds, },
					traditional:true,
					success: function(result) {
						if(0 == result.code) {
							var configTemp = tablePlug.getConfig("tableData");
				            if (configTemp.page && configTemp.page.curr > 1) {
				              table.reload("tableData", {
				                page: { curr: configTemp.page.curr - 1 }
				              })
				            }else{
				            	table.reload("tableData")
				            };
							layer.msg(result.message, { icon: 1, time:800 });
						} else {
							layer.msg(result.message, { icon: 2, time:800 });
						}
					},
					error: function() {
						layer.msg("操作失败！", { icon: 2 });
					}
				});
				layer.close(index);
			});
		}
		//监听搜索
		form.on('submit(LAY-search)', function(data) {
			var field = data.field;
			var orderTime=field.orderTimeBegin.split('~');
			field.orderTimeBegin=orderTime[0];
			field.orderTimeEnd=orderTime[1];
			table.reload('tableData', {
				where: field
			});
		});
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
	}
)
</script>
</body>
</html>