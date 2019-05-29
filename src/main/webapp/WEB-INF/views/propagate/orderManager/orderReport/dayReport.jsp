<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>入库日报表</title>
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
			if(time==''){
				layer.msg('请输入搜索时间！',{icon:2});
				return;
			}
			var t=time.split('~');
			table.reload('dayReport',{
				url:'${ctx}/inventory/report/storageDay?report=1',
				where:{
					orderTimeBegin : t[0],
					orderTimeEnd : t[1].substring(0,12)+'23:59:59'
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
			       {align:'center', title:'时间',       field:'time',	 },
			       {align:'center', title:'成交金额',   totalRow:true,field:'sumPayment',	style:"color:#ff000a;"},
			       {align:'center', title:'成交单数',   totalRow:true,field:'singular',   },
			       {align:'center', title:'实际邮费', 	totalRow:true,field:'sumpostFee', 	},
			       {align:'center', title:'成交宝贝数量', totalRow:true,  field:'proNumber',	},
			       {align:'center', title:'每单平均金额',  totalRow:true, field:'averageAmount',	},
			       {align:'center', title:'广宣成本',   totalRow:true,field:'sumCost',	style:"color:blue;"},
			       {align:'center', title:'利润',   totalRow:true,field:'profits',	style:"color:#0fda87;"},
			       ]]
		})
		
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	}//end define function
)//endedefine
</script>

</html>