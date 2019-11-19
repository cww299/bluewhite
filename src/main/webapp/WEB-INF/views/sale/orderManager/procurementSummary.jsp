<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>采购单</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>产品名:</td>
				<td><input type="text" name="productName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>产品编号:</td>
				<td><input type="text" name="productNumber" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
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
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/ledger/getOrderProcurement',
			size:'lg',
			colsWidth:[0,10,0,6,6,6,6,6,6,8,10,10,6,6,8,10,10,10,10,10,10,10,10,10,10],
			curd:{
				btn:[],
				otherBtn:function(obj){
					if(obj.event=='audit'){
						myutil.deleTableIds({
							url:'/ledger/arrivalOrderProcurement',
							table:'tableData',
							text:'请选择信息|是否确认审核？',
						})
					}
				}
			},
			ifNull:'',
			toolbar: '<span lay-event="audit" class="layui-btn layui-btn-sm">审核入库</span>',
			cols:[[
					{ type:'checkbox',fixed:'left' },
					{ title:'物料名', field:'materiel_name', fixed:'left',},
					{ title:'物料定性', field:'materiel_materialQualitative_name', },
					{ title:'下单日期', field:'placeOrderTime', type:'date',},
					{ title:'采购编号', field:'orderProcurementNumber', },
					{ title:'采购数量', field:'placeOrderNumber', },
					{ title:'预计价格', field:'conventionPrice', },
					{ title:'实际价格', field:'price', },
					{ title:'预计克重', field:'conventionSquareGram', },
					{ title:'实际克重', field:'squareGram', },
					{ title:'订购人', field:'user_userName', },
					{ title:'供应商', field:'customer_name', },
					{ title:'预计到货', field:'expectArrivalTime',},
					{ title:'到货日期', field:'arrivalTime',  type:'date', },
					{ title:'到货数量', field:'arrivalNumber', },
					{ title:'退货数量', field:'returnNumber', },
					{ title:'延期付款数量', field:'partDelayNumber', },
					{ title:'延期付款金额', field:'partDelayPrice', },
					{ title:'延期付款日期', field:'partDelayTime', type:'date',},
					{ title:'缺克重价值', field:'gramPrice;', },
					{ title:'占用资金利息', field:'interest', },
					{ title:'生成账单', field:'bill', fixed:'right',transData:{data:['否','是']}},
			       ]]
		})
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>