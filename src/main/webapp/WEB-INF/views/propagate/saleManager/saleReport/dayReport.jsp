<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/vendor/jquery-3.3.1.min.js"></script><!-- 使用cookie文件需要引用此文件，layui自带的jquery不可 -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售日报表</title>
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
	cookieCol : 'layui/myModules/cookieCol',
}).define(
	['tablePlug','laydate','cookieCol'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, cookieCol = layui.cookieCol
		, laydate = layui.laydate
		, tablePlug = layui.tablePlug;
		cookieCol.cookieName('saleManagerDayReportCookie');		//记录筛选列模块
		form.render();
		function p(s) { return s < 10 ? '0' + s: s; }
		var myDate = new Date();
		var year=myDate.getFullYear();
		var month=myDate.getMonth()+1;
		var day = new Date(year,month,0);
		var firstdate = year + '-' + p(month) + '-01'+' '+'00:00:00';
		var lastdate = year + '-' + p(month) + '-' + day.getDate() +' '+'23:59:59';
		
		$('#search').on('click',function(){
			var time=$('#time').val();
			if(time==''){
				layer.msg('请输入搜索时间！',{icon:2});
				return;
			}
			var t=time.split('~');
			table.reload('dayReport',{
				url:'${ctx}/inventory/report/salesDay?report=1',
				where:{
					orderTimeBegin : t[0],
					orderTimeEnd : t[1].substring(0,12)+'23:59:59'
				}
			})
		})
		
		laydate.render({ 
			elem:'#time', 
			type: 'datetime', 
			range:'~', 
			value: firstdate+' ~ '+lastdate,
		}) 
		
		
		table.render({
			url : '${ctx}/inventory/report/salesDay?report=1',
			where:{
				orderTimeBegin : firstdate,
				orderTimeEnd : lastdate
			},
			elem:'#dayReport',
			loading:true,
			size:'sm',
			totalRow:true,
			toolbar: true,
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){
				return {   msg:ret.message,  code:ret.code , data:ret.data, } },
			cols:[[
			       {align:'center', title:'时间',      totalRowText: '合计', field:'time',	 },
			       {align:'center', title:'成交金额',   field:'sumPayment',	totalRow:true,style:"color:#ff000a;"},
			       {align:'center', title:'成交单数',   field:'singular',  totalRow:true, },
			       {align:'center', title:'实际邮费', 	field:'sumpostFee',totalRow:true, 	},
			       {align:'center', title:'成交宝贝数量',   field:'proNumber',totalRow:true,	},
			       {align:'center', title:'每单平均金额',   field:'averageAmount',totalRow:true,	},
			       {align:'center', title:'广宣成本',   field:'sumCost',	style:"color:blue;",totalRow:true,},
			       {align:'center', title:'利润',   field:'profits',	style:"color:#0fda87;",totalRow:true,},
			       ]]
		})
	}//end define function
)//endedefine
</script>

</html>