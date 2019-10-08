<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
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
				<td><select id="userIdSelect" lay-search><option value="">获取数据中</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" id='search'>搜索</button></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-badge">双击查看员工的销售详情</span></td>
			</tr>
		</table>
		<table class="layui-form" id="saleUserReport" lay-filter="saleUserReport"></table>
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
		, laydate = layui.laydate
		, cookieCol = layui.cookieCol
		, tablePlug = layui.tablePlug;
		//cookieCol.cookieName('saleManagerUserReportCookie');		//记录筛选列模块
		function p(s) { return s < 10 ? '0' + s: s; }
		var myDate = new Date();
		var year=myDate.getFullYear();
		var month=myDate.getMonth()+1;
		var day = new Date(year,month,0);
		var firstdate = year + '-' + p(month) + '-01'+' '+'00:00:00';
		var lastdate = year + '-' + p(month) + '-' + day.getDate() +' '+'23:59:59';
		
		getAllUser();
		form.render();
	 	laydate.render({ elem:'#time', type: 'datetime', range:'~', value : firstdate+' ~ '+lastdate, });
	 	
	 	
	 	
		$('#search').on('click',function(){
			var time=$('#time').val();
			if(time==''){
				layer.msg('请输入查找时间',{icon:2});
				return;
			}
			var t=time.split('~');
			table.reload('saleUserReport',{
				url:'${ctx}/inventory/report/salesUser?report=3',
				where:{
					orderTimeBegin : t[0],
					orderTimeEnd : t[1],
					userId : $('#userIdSelect').val(),
				}
			})
		})
		table.render({
			url:'${ctx}/inventory/report/salesUser?report=3',
			where:{
				orderTimeBegin : firstdate,
				orderTimeEnd : lastdate,
			},
			colFilterRecord:'local',
			elem:'#saleUserReport',
			loading:true,
			size:'sm',
			totalRow:true,			
			toolbar: true,
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return {  msg:ret.message,  code:ret.code , data:ret.data, } },
			cols:[[
			       {align:'center', title:'用户',   totalRowText: '合计', field:'user',	},
			       {align:'center', title:'成交单数',   field:'singular',   totalRow:true,},
			       {align:'center', title:'宝贝数量', 	field:'proNumber', totalRow:true,	},
			       {align:'center', title:'成交金额',   field:'sumPayment',	totalRow:true,},
			       {align:'center', title:'实际运费',   field:'sumpostFee',	totalRow:true,},
			       {align:'center', title:'每单平均金额',   field:'averageAmount',	totalRow:true,},
			       ]]
		})
		table.on("rowDouble(saleUserReport)",function(obj){
			layer.open({
				type:1,
				title:obj.data.user,
				area:['80%','80%'],
				content:$('#lookoverDiv'),
				shadeClose:true,
			})
			table.render({
				url : '${ctx}/inventory/report/salesUserDetailed?userId='+obj.data.userId,
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
		var allUser=[];
		function getAllUser(){
			$.ajax({
				url:'${ctx}/system/user/pages?size=99',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allUser.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].userName
							})
						renderUserSelect('userIdSelect');
					}
				}
			})
		}
		function renderUserSelect(select){			//根据id渲染客服下拉框
			var html='<option value="">销售人员</option>';
			for(var i=0;i<allUser.length;i++)
				html+='<option value="'+allUser[i].id+'">'+allUser[i].userName+'</option>';
			$('#'+select).html(html);
			form.render();
		}
	}//end define function
)//endedefine
</script>

</html>