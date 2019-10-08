<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发货管理</title>
<style>
#defaultDate{
	font-weight: bolder;
}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>发货日期：</td>
				<td><input type="text" class="layui-input" id="searchTime"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>批次号：</td>
				<td><input type="text" name="bacthNumber" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>产品名称：</td>
				<td><input type="text" name="productName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>客户名称：</td>
				<td><input type="text" name="customerName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<!-- 表格工具栏模板 -->
<script type="text/html" id="tableDataToolbar">
	<span class="layui-btn layui-btn-primary" id="defaultDate">0000-00-00</span>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
}).define(
	['layer','laydate','mytable'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form		
		, laydate = layui.laydate
		, myutil = layui.myutil
		, table = layui.table 
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		myutil.timeFormat();
		var allBatch = [],allCustom = []; 	//所有的批次号、客户
		var searchTime = new Date().format("yyyy-MM-dd");;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		myutil.getData({
			url:'${ctx}/ledger/allCustomer',
			async: false,
			done: function(data){
				allCustom = data;
			}
		});
		myutil.getData({
			url:'${ctx}/ledger/getOrder',
			async: false,
			done: function(data){
				allBatch = data;
			}
		});
		laydate.render({ elem:'#searchTime',range:'~'  })
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/ledger/getSendGoods',
			toolbar:'#tableDataToolbar',
			size:'lg',
			autoUpdate:{
				saveUrl: '/ledger/addSendGoods',
				deleUrl: '/ledger/deleteSendGoods',
				field: { customer_id:'customerId', }
			},
			curd:{
				addTemp: {customerId:allCustom[0].id,number:'',sendDate:searchTime+' 00:00:00' ,id:'',orderId:allBatch[0].id,sendNumber:'', surplusNumber:''},
			},
			colsWidth:[0,10,12,0,6,6,6],
			verify:{
				notNull:['customer_id','orderId','sendDate','number'],
				count:['number'],
			},
			cols:[[
			       { type:'checkbox',},
			       { type:'date', title:'发货日期',   field:'sendDate',   },
			       { type:'select', title:'客户',   field:'customer_id',    select:{ data: allCustom }  },
			       { type:'select', title:'批次号',   field:'orderId',  select:{ data: allBatch , name:['orderDate','bacthNumber','product_name']} },
			       { title:'数量',   field:'number',	edit:true,  },
			       { title:'发货数量',   field:'sendNumber',	edit:false, },
			       { title:'剩余数量',   field:'surplusNumber', edit:false, },
			       ]],
			done:function(){
				laydate.render({ 
					elem:'#defaultDate',
					value: searchTime,
					done:function(val){
						searchTime = val;
					}
				})
			}
		})
		form.on('submit(search)',function(obj){
			var val = $('#searchTime').val();
			var beg = '', end = '';
			if(val!=''){
				beg = val.split('~')[0].trim()+' 00:00:00';
				end = val.split('~')[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = beg;
			obj.field.orderTimeEnd = end;
			table.reload('tableData',{
				where: obj.field ,
				page:{ curr:1 },
			})
		}) 
	}//end define function
)//endedefine
</script>

</html>