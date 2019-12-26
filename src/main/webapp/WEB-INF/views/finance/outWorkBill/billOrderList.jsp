<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>对账单列表</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td></td>
				<td></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
}).use(['mytable','jquery'],function(){
	var $ = layui.jquery,
		mytable = layui.mytable,
		myutil = layui.myutil;
	myutil.config.ctx = '${ctx}';
	myutil.clickTr();
	
	mytable.render({
		elem: '#tableData',
		url: myutil.config.ctx+'/fince/getConsumption?type=10',
		curd:{
			btn:[4],
		},
		autoUpdate:{
			deleUrl:'/fince/deleteConsumption',
		},
		cols:[[
			{ type:'checkbox',},
			{ title:'申请日期', field:'expenseDate', type:'dateTime',},
			{ title:'外发单编号', field:'orderOutSource_outSourceNumber', },
			{ title:'生产单编号', field:'orderOutSource_materialRequisition_order_orderNumber', },
			{ title:'供应商', field:'customer_name', },
			{ title:'金额', field:'money', },
			{ title:'备注', field:'remark', },
			
		]],
	})
});
</script>
</html> 