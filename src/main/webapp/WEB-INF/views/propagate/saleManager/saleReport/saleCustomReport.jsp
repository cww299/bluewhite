<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户销售报表</title>
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
				<td><select id="customIdSelect" lay-search><option value="">获取数据中</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" id='search'>搜索</button></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-badge">双击查看客户的购买详情</span></td>
			</tr>
		</table>
		<table class="layui-form" id="saleCustomReport" lay-filter="saleCustomReport"></table>
	</div>
</div>
</body>
<!-- 查看销售详情隐藏框 -->
<div style="display:none;" id="lookoverDiv" >
	<table class="layui-table" id="lookoverTable" lay-filter="lookoverTable"></table>
</div>

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
		
		//cookieCol.cookieName('saleManagerCustomReportCookie');		//记录筛选列模块
		function p(s) { return s < 10 ? '0' + s: s; }
		var myDate = new Date();
		var year=myDate.getFullYear();
		var month=myDate.getMonth()+1;
		var day = new Date(year,month,0);
		var firstdate = year + '-' + p(month) + '-01'+' '+'00:00:00';
		var lastdate = year + '-' + p(month) + '-' + day.getDate() +' '+'23:59:59';
		
		getAllCustom();
		form.render();
	 	laydate.render({ 
	 		elem:'#time', 
	 		type: 'datetime',
	 		range:'~',
	 		value:firstdate+' ~ '+lastdate,
	 	}) 
		$('#search').on('click',function(){
			var time=$('#time').val();
			if(time==''){
				layer.msg('请输入查找时间',{icon:2});
				return;
			}
			var t=time.split('~');
			table.reload('saleCustomReport',{
				url:'${ctx}/inventory/report/salesUser?report=4',
				where:{
					orderTimeBegin : t[0],
					orderTimeEnd : t[1],
					onlineCustomerId : $('#customIdSelect').val(),
				}
			})
		})
		table.render({
			url:'${ctx}/inventory/report/salesUser?report=4',
			where:{
				orderTimeBegin : firstdate,
				orderTimeEnd : lastdate,
			},
			elem:'#saleCustomReport',
			loading:true,
			colFilterRecord:'local',
			size:'sm',
			toolbar: true,
			totalRow:true,
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return {  msg:ret.message,  code:ret.code , data:ret.data, } },
			cols:[[
			       {align:'center', title:'客户',   totalRowText: '合计', field:'user',	},
			       {align:'center', title:'成交单数',   field:'singular',  totalRow:true, },
			       {align:'center', title:'宝贝数量', 	field:'proNumber', totalRow:true,	},
			       {align:'center', title:'成交金额',   field:'sumPayment',	totalRow:true,},
			       {align:'center', title:'实际运费',   field:'sumpostFee',	totalRow:true,},
			       {align:'center', title:'每单平均金额',   field:'averageAmount',totalRow:true,	},
			       ]]
		})
		table.on("rowDouble(saleCustomReport)",function(obj){
			layer.open({
				type:1,
				title:obj.data.user,
				area:['80%','80%'],
				offset:'30px',
				content:$('#lookoverDiv'),
				shadeClose:true,
			})
			table.render({
				url : '${ctx}/inventory/report/salesUserDetailed?onlineCustomerId='+obj.data.userId,
				elem : '#lookoverTable',
				size : 'sm',
				page : true,
				request:{ pageName:'page', limitName:'size' },
				parseData:function(ret){ return {  msg:ret.message,  code:ret.code , data:ret.data.rows, count:ret.data.total, } },
				cols:[[
						{align:'center', title:'日期',   		field:'createdAt',	width:'11%',},
						{align:'center', title:'单据编号',   	templet:'<span>{{ d.onlineOrder.documentNumber }}</span>', 	width:'12%',},
						{align:'center', title:'运单号',   		templet:'<span>{{ d.onlineOrder.trackingNumber }}</span>', 	width:'8%',},
						{align:'center', title:'商品名称', 		templet:'<span>{{ d.commodity.skuCode }}</span>', 	},
						{align:'center', title:'商品数量', 		field:'number', width:'6%', 	},
						{align:'center', title:'商品单价', 		field:'price',	width:'6%', },
						{align:'center', title:'商品总价', 		field:'sumPrice', 	width:'6%', },
						{align:'center', title:'仓库名称',   	templet:'<span>{{ d.warehouse.name }}</span>',	width:'7%',},
						{align:'center', title:'客户名称',   	templet:'<span>{{ d.onlineOrder.onlineCustomer.name }}</span>',	width:'7%',},
						{align:'center', title:'经手人',   		templet:'<span>{{ d.onlineOrder.user.userName }}</span>',	width:'6%',},
				       ]]
			}) 
		})
		var allCustom=[];
		function getAllCustom(){
			$.ajax({
				url:'${ctx}/inventory/onlineCustomerPage',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allCustom.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].name
							})
							renderCustomSelect('customIdSelect');
					}
				}
			})
		}
	 	function renderCustomSelect(select){			//根据id渲染客服下拉框
			var html='<option value="">客户</option>';
			for(var i=0;i<allCustom.length;i++)
				html+='<option value="'+allCustom[i].id+'">'+allCustom[i].userName+'</option>';
			$('#'+select).html(html);
			form.render();
		}
	 	
	}//end define function
)//endedefine
</script>
</html>