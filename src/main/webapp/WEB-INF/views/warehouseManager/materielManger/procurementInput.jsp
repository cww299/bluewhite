<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>采购入库</title>
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
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
	inputWarehouseOrder: 'layui/myModules/warehouseManager/inputWarehouseOrder',
}).define(
	['mytable','inputWarehouseOrder','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, inputWarehouseOrder = layui.inputWarehouseOrder
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		var currentUser = myutil.getDataSync({url: myutil.config.ctx+'/getCurrentUser'});
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/ledger/getOrderProcurement',
			size:'lg',
			ifNull:'',
			scrollX:true,
			toolbar: [ '<span lay-event="add" class="layui-btn layui-btn-sm">生成入库单</span>',
					   '<span lay-event="audit" class="layui-btn layui-btn-sm">审核到货</span>',].join(' '),
			curd:{
				btn:[],
				otherBtn:function(obj){
					if(obj.event=="add"){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据生成入库单');
						inputWarehouseOrder.add({
							data:{
								inStatus: 2,	//采购入库
								materielId: check[0].materiel.id,
								orderProcurementId: check[0].id,
							}
						});
					}else if(obj.event=='audit'){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length<1)
							return myutil.emsg('请选择审核的信息');
						var auditWin = layer.open({
							type:1,
							title:'确认审核到货',
							area:['300px','200px'],
							btn:['确定','取消'],
							content:'<div style="padding:20px;"><table><tr><td>到货时间：</td><td>'+
									'<input type="text" id="arriveTime" class="layui-input"></td></tr></table></div>',
							success:function(){
								laydate.render({
									elem: '#arriveTime',
									type:'datetime',
									value: new Date(),
								})
							},
							yes:function(){
								var val = $('#arriveTime').val();
								if(val==''){
									myutil.emsg('到货时间不能为空！');
									return false;
								}
								var ids = [];
								for(var i in check){
									ids.push(check[i].id);
								}
								myutil.deleteAjax({
									url:'/ledger/inventory/arrivalOrderProcurement?time='+val,
									ids: ids.join(','),
									success:function(){
										table.reload('tableData');
										layer.close(auditWin);
									}
								})
							}
						})
					}
				}
			},
			colsWidth:[0,8,0,6,6,6,6,8,10,8,6,],
			cols:[[
					{ type:'checkbox',fixed:'left' },
					{ title:'下单日期', field:'placeOrderTime', type:'date'},
					{ title:'采购编号', field:'orderProcurementNumber', },
					{ title:'采购数量', field:'placeOrderNumber', },
					{ title:'预计价格', field:'price', },
					{ title:'约定克重', field:'conventionSquareGram', },
					{ title:'订购人', field:'user_userName', },
					{ title:'供应商', field:'customer_name', },
					{ title:'预计到货日期', field:'expectArrivalTime',type:'date'},
					{ title:'实际到货日期', field:'arrivalTime', type:'date', },
					{ title:'是否入库',field:'arrival',transData:{data:['否','是'],},},
			       ]]
		})
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
		inputWarehouseOrder.type = 1;
		inputWarehouseOrder.init();
	}//end define function
)//endedefine
</script>
</body>
</html>