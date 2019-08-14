<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">


<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">



<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>采购审核</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

</head>

<body>
<div class="layui-card">
	<div class="layui-card-body">
		<div class="layui-form layui-card-header layuiadmin-card-header-auto">
			<div class="layui-form-item">
				<table>
					<tr>
						<td>批次号:</td>
						<td><input type="text" name="batchNumber" id="firstNames" class="layui-input" /></td>
						<td>&nbsp;&nbsp;</td>
						<td><select class="layui-input" id="selectone">
								<option value="expenseDate">付款日期</option>
								<option value="paymentDate">实际付款日期</option>
						</select></td>
						<td>&nbsp;&nbsp;</td>
						<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input">
						</td>
						<td>&nbsp;&nbsp;</td>
						<td>是否核对:
						<td><select class="form-control" name="flag">
								<option value="0">未审核</option>
								<option value="1">已审核</option>
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
	
<script type="text/html" id="toolbar">
	<div class="layui-btn-container layui-inline">
		<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="audit">审核</span>
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="noAudit">取消审核</span>
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
		var $ = layui.jquery,
			layer = layui.layer,
			form = layui.form,
			table = layui.table,
			laydate = layui.laydate,
			tablePlug = layui.tablePlug;
		
		laydate.render({ elem: '#startTime', type: 'datetime', range: '~', });
	   	tablePlug.smartReload.enable(true); 
		table.render({
			elem: '#tableData',
			size: 'lg',
			height:'700px',
			url: '${ctx}/fince/getConsumption?type=2' ,
			where:{ flag:0 },
			request:{ pageName: 'page' , limitName: 'size'  },
			page: {},
			loading: true,
			toolbar: '#toolbar', 
			cellMinWidth: 90,
			colFilterRecord: true,
			smartReloadModel: true,
			parseData: function(ret) {
				return {
					code: ret.code,
					msg: ret.message,
					count:ret.data.total,
					data: ret.data.rows
				}
			},
			cols: [[
			       { align: 'center', type: 'checkbox', fixed: 'left' },
			       { align: 'center', field: "batchNumber", 	title: "批次号", 		width:'10%', },
			       { align: 'center', field: "content", 		title: "内容",},
			       { align: 'center', field: "", 				title: "客户",   		width:'12%', templet:function(d){ return d.custom.name; }}, 
			       { align: 'center', field: "", 				title: "订料人",   		width:'6%',  templet:function(d){ return d.user==null?'':d.user.userName }},
			       { align: 'center', field: "money", 			title: "金额", 			width:'6%',}, 
			       { align: 'center', field: "expenseDate", 	title: "付款日期", 		width:'10%', },
			       { align: 'center', field: "logisticsDate", 	title: "到货日",			width:'10%', },
			       { align: 'center', field: "paymentDate", 	title: "实际付款时间", 	width:'10%', style:'background-color: #d8fe83', }, 
			       { align: 'center', field: "paymentMoney",	title: "付款金额", 		width:'6%',  style:'background-color: #d8fe83', edit: 'text', },
			       { align: 'center', field: "flag", 			title: "审核状态", 		width:'6%', templet:  function(d){ return d.flag==0?'未审核':'已审核';}}
			       ]],
	       done: function(res, curr, count) {
				var tableView = this.elem.next();
				var tableElem = this.elem.next('.layui-table-view');
				layui.each(tableElem.find('select'), function(index, item) {
					var elem = $(item);
					elem.val(elem.data('value'));
				});
				form.render();
				// 初始化laydate
				layui.each(tableView.find('td[data-field="paymentDate"]'), function(index, tdElem) {
					tdElem.onclick = function(event) {
						layui.stope(event)
					};
					laydate.render({
						elem: tdElem.children[0],
						format: 'yyyy-MM-dd HH:mm:ss',
						done: function(value, date) {
								var id = table.cache[tableView.attr('lay-id')][index].id
								var postData = {
									id: id,
									paymentDate: value,
								};
								mainJs.fUpdate(postData);
						}
					})
				})
			},
		});

		
		//监听头工具栏事件
		table.on('toolbar(tableData)', function(obj) {
			var config = obj.config;
			var btnElem = $(this);
			var tableId = config.id;
			var checkedIds = tablePlug.tableCheck.getChecked(tableId);
			if(checkedIds.length<1){
				layer.msg('请选择数据',{icon:2});
				return;
			}
			switch(obj.event) {
				case 'audit':
					layer.confirm('您是否确定要审核选中的' + checkedIds.length + '条记录？', function() {
						var postData = { ids:checkedIds, flag:1, }
						mainJs.fAudit(postData);
					});
					break;
				case 'noAudit':
					layer.confirm('您是否确定取消审核选中的' + checkedIds.length + '条记录？', function() {
						var postData = { ids:checkedIds, flag:0, }
						mainJs.fAudit(postData);
					});
					break;
			}
		});

		//监听单元格编辑
		table.on('edit(tableData)', function(obj) {
			var postData = {
				id:obj.data.id,
				paymentMoney:obj.value
			}
			mainJs.fUpdate(postData);
		});

		//监听搜索
		form.on('submit(LAY-search)', function(data) {
			var field = data.field;
			var orderTime=field.orderTimeBegin.split('~');
			orderTimeBegin=orderTime[0];
			orderTimeEnd=orderTime[1];
			var a="";
			var b="";
			if($("#selectone").val()=="expenseDate"){
				a="2019-05-08 00:00:00"
			}else{
				b="2019-05-08 00:00:00"
			}
			var post={
				flag:field.flag,
				orderTimeBegin:orderTimeBegin,
				orderTimeEnd:orderTimeEnd,
				expenseDate:a,
				paymentDate:b,
				batchNumber:field.batchNumber,
			}
			table.reload('tableData', {
				where: post
			});
		});
		
		//封装ajax主方法
		var mainJs = {
		    fUpdate : function(data){
		    	var load=layer.load(1);
		    	$.ajax({
					url: "${ctx}/fince/addConsumption",
					data: data,
					async:true,
					type: "POST",
					success: function(result) {
						if(0 == result.code) {
						 	table.reload("tableData")   
							layer.msg(result.message, { icon: 1, time:800 });
						} else {
							layer.msg(result.message, { icon: 2, time:800 });
						}
					},
					error: function() {
						layer.msg("操作失败！请重试", { icon: 2 });
					},
				});
				layer.close(load);
		    },
			fAudit : function(postData){
				var load=layer.load(1);
		    	$.ajax({
					url: "${ctx}/fince/auditConsumption",
					data: postData,
					async:false,
					traditional: true,
					type: "POST",
					success: function(result) {
						if(0 == result.code) {
						 	 table.reload("tableData")   
							layer.msg(result.message, { icon: 1, time:800 });
						} else {
							layer.msg(result.message, { icon: 2, time:800 });
						}
					},
					error: function() {
						layer.msg("操作失败！请重试", { icon: 2 });
					},
				});
				layer.close(load);
		    }
		}
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