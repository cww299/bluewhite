<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发货单</title>
<style>
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
		<table id="sendTable" lay-filter="sendTable"></table>
	</div>
</div>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
	sendGoodOrder: 'layui/myModules/sale/sendGoodOrder',
	outInventory: 'layui/myModules/warehouseManager/outInventory',
}).define(
	['layer','mytable','laydate','sendGoodOrder','outInventory'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form		
		, laydate = layui.laydate
		, myutil = layui.myutil
		, table = layui.table 
		, sendGoodOrder = layui.sendGoodOrder
		, outInventory = layui.outInventory
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		myutil.getLastData();
		var currUser = myutil.getDataSync({ url: myutil.config.ctx+'/getCurrentUser', });
		var roles = currUser.role;
		var isSaleUser = roles.indexOf('salesUser')>-1 || roles.indexOf('superAdmin')>-1;
		var isWarehouseRole = ( roles.indexOf('eightFinishedWarehouse')>-1 || 
								roles.indexOf('onlineWarehouse')>-1 || 
								roles.indexOf('sceneWarehouse')>-1 || 
								roles.indexOf('finishedWarehouse')>-1 || 
								roles.indexOf('superAdmin')>-1); 
		laydate.render({ elem:'#searchTime',range:'~'  })
		mytable.render({
			elem:'#sendTable',
			url: myutil.config.ctx+'/ledger/getSendGoods',
			toolbar: [
				isSaleUser?'<span class="layui-btn layui-btn-sm" lay-event="addSendOrder">新增发货单</span>':'',
				isWarehouseRole?'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="sendGood">发货</span>':'',
			].join(' '),
			curd:{
				btn: isSaleUser?[4]:[],
				otherBtn:function(obj){
					if(obj.event=='addSendOrder'){
						sendGoodOrder.add({currUser: currUser});
					}else if(obj.event=='sendGood'){
						var check = layui.table.checkStatus('sendTable').data;
						if(check.length!=1)
							return myutil.emsg('请选择一条信息进行发货');
						outInventory.add(check[0],{
							reloadTable:'sendTable',
						});
					}
				}
			},
			autoUpdate:{
				deleUrl:'/ledger/deleteSendGoods',
			},
			size:'lg',
			cols:[[
			       { type:'checkbox',},
			       { title:'发货日期',   field:'sendDate', width:'10%',  },
			       { title:'客户',   field:'customer_name',   width:'12%',   },
			       { title:'产品', 	field:'product_name',  },
			       { title:'产品类型', 	field:'productType', width:'10%', transData:{data:['','成品','皮壳']} },
			       { title:'数量',   field:'number',  width:'6%',},
			       { title:'剩余发货数量',   field:'surplusNumber',  width:'8%',	},
			       { title:'实际发货数量',   field:'sendNumber',  width:'8%',	}, 
			       { title:'库存状态',field:'status',width:'8%',transData:{data:['库存充足','库存不足','无库存',]}, },
			       ]],
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
			table.reload('sendTable',{
				where: obj.field ,
				page:{ curr:1 },
			})
		}) 
		sendGoodOrder.init();
	}//end define function
)//endedefine
</script>
</body>
</html>