<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>采购汇总</title>
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td style="width:130px;"><select class="layui-input" id="selectone">
					<option value="expenseDate">预计付款日期</option>
					<option value="paymentDate">实际付款日期</option></select></td>
				<td><input id="startTime" name="orderTimeBegin" placeholder="请输入时间" class="layui-input "></td>
				<td>&nbsp;&nbsp;</td>
				<td>采购单编号:</td>
				<td><input type="text" name="content" class="layui-input" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>是否审核:</td>
				<td style="width:100px;">
					<select name="flag"><option value="">请选择</option><option value="0">未审核</option>
						<option value="1">已审核</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="LAY-search">
						<i class="layui-icon layui-icon-search"></i></span></td>
				<td>&nbsp;&nbsp;</td>
				<td style="width:130px;"></td>
				<td style="font-size: 20px;">未支付总额:</td>
				<td id="allPrice" style="color:red;font-size: 20px;"></td>
			</tr>
		</table>
		<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
	</div>
</div>
<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable', 'laydate',],
	function() {
		var $ = layui.jquery,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		laydate = layui.laydate,
		myutil = layui.myutil,
		mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({ elem: '#startTime', range: '~', });
		mytable.render({
			elem: '#tableData',
			url: '${ctx}/fince/getConsumption?type=10' ,
			cellMinWidth: 120,
			ifNull:'',
			scrollX:true,
			cols:[[
					{ type:'checkbox',},
					{ title:'申请日期', field:'expenseDate', type:'dateTime',},
					{ title:'外发单编号', field:'orderOutSource_outSourceNumber', },
					{ title:'生产单编号', field:'orderOutSource_materialRequisition_order_orderNumber', },
					{ title:'供应商', field:'customer_name', },
					{ title:'金额', field:'money', },
					{ title:'备注', field:'remark', },
			        { field: "flag", title: "审核状态", transData:{data:['未审核','审核','部分审核'],},fixed:'right',}
				 ]],
		});
		myutil.getData({
			url:'${ctx}/fince/totalAmount?type=10&flag=0',
			success:function(d){
				$('#allPrice').html(d);
			}
		})
		form.on('submit(LAY-search)', function(obj) {
			var f = obj.field;
			var timeType = $('#selectone').val();
			if(f.orderTimeBegin){
				var time = f.orderTimeBegin.split(' ~ ');
				f.orderTimeBegin = time[0]+' 00:00:00';
				f.orderTimeEnd = time[1]+' 23:59:59';
			}else{
				f.orderTimeEnd = '';
			}
			if(timeType=='expenseDate'){
				f.expenseDate = '2019-11-20 17:18:34';
				f.paymentDate = '';
			}else{
				f.expenseDate = '';
				f.paymentDate = '2019-11-20 17:18:34';
			}
			table.reload('tableData', {
				where: f
			});
			delete f.flag;
			myutil.getData({
				url:'${ctx}/fince/totalAmount?type=2&flag=0',
				data: f,
				success:function(d){
					$('#allPrice').html(d);
				}
			})
		});
	}
)
</script>
</body>
</html>