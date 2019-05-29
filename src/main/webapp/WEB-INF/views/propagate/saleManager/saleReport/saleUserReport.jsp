<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售人员报表</title>
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
				url:'${ctx}/inventory/report/salesUser?report=3',
				where:{
					orderTimeBegin : t[0],
					orderTimeEnd : t[1],
				}
			})
		})
		table.render({
			elem:'#dayReport',
			loading:true,
			size:'sm',
			totalRow:true,
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){
				return {  
					msg:ret.message, 
					code:ret.code ,
					data:ret.data,
					} },
			cols:[[
			       {align:'center', title:'用户',   field:'user',	},
			       {align:'center', title:'成交单数',   field:'singular',   totalRow:true,},
			       {align:'center', title:'宝贝数量', 	field:'proNumber', totalRow:true,	},
			       {align:'center', title:'成交金额',   field:'sumPayment',	totalRow:true,},
			       {align:'center', title:'实际运费',   field:'sumpostFee',	totalRow:true,style:"color:blue;"},
			       {align:'center', title:'每单平均金额',   field:'averageAmount',	totalRow:true,},
			       ]]
		})
		
	}//end define function
)//endedefine
</script>

</html>