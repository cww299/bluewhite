<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品入库报表</title>
<style>
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>查询时间:&nbsp;&nbsp;</td>
				<td><input type='text' id='time' class='layui-input' style='width:350px;' placeholder='请输入查询时间'></td>
				<td>&nbsp;&nbsp;</td>
				<td>商品名称:&nbsp;&nbsp;</td>
				<td><input type='text' id='skuCode' class='layui-input' placeholder='请输入查询信息'></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" id='search'>搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="dayReport" lay-filter="dayReport"></table>
	</div>
</div>
</body>

<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).define(
	['tablePlug','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, tablePlug = layui.tablePlug;
		
		form.render();
	 	laydate.render({
			elem:'#time',
			type: 'datetime',
			range:'~'
		}) 
		$('#search').on('click',function(){
			var time=$('#time').val();
			var t=time.split('~');
			table.reload('dayReport',{
				url:'${ctx}/inventory/report/storageGoods',
				where:{
					orderTimeBegin : t[0],
					orderTimeEnd : t[1],
					skuCode : $('#skuCode') .val()
				}
			})
		})
		table.render({
			elem:'#dayReport',
			loading:true,
			size:'sm',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){
				return {  
					msg:ret.message, 
					code:ret.code ,
					data:ret.data,
					} },
			cols:[[
			       {align:'center', title:'时间',       field:'time',	 },
			       {align:'center', title:'商品名称',   field:'name',	 },
			       {align:'center', title:'成交单数',   field:'singular',   },
			       {align:'center', title:'总金额', 	    field:'sumPayment', 	},
			       {align:'center', title:'总数量',   field:'sunNumber',	},
			       {align:'center', title:'每单平均金额',   field:'averageAmount',	},
			       ]]
		})
		
	}//end define function
)//endedefine
</script>

</html>