<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>出库单</title>
	<style>
	  .searchTable td:nth-of-type(odd){
	  	padding: 5px 0;
	  	padding-left: 15px;
	  	padding-right: 5px;
	  }
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td>出库日期:</td>
				<td><input type="text" name="orderTimeBegin" id="inputDate" class="layui-input"></td>
				<td>物料编号:</td>
				<td><input type="text" name="materielNumber" class="layui-input"></td>
				<td>物料名称:</td>
				<td><input type="text" name="materielName" class="layui-input"></td>
				<td>出库状态:</td>
				<td><select name="outStatus"><option value="">请选择</option>
							<option value="1">生产出库</option>
							<option value="2">调拨出库</option>
							<option value="3">销售换货出库</option>
							<option value="4">采购退货出库</option>
							<option value="5">盘盈出库</option></select></td>
				<td>
					<button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
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
	outWarehouseOrder: 'layui/myModules/warehouseManager/outWarehouseOrder',
}).define(
	['mytable','outWarehouseOrder','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, outWarehouseOrder = layui.outWarehouseOrder
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({elem:'#inputDate',range:'~'})
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/ledger/inventory/materialOutStoragePage',
			curd:{
				otherBtn:function(obj){
					var check = layui.table.checkStatus('tableData').data;
					if(obj.event=="verify"){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据进行操作');
						var win = layer.open({
							title:'验货',
							offset:'150px',
							area:['400px','220px'],
							btn:['确定','取消'],
							type:1,
							content:
								['<div class="layui-form" style="padding:20px;">',
									'<div class="layui-form-item">',
										'<label class="layui-form-label">实际克重</label>',
										'<div class="layui-input-block">',
											 '<input type="text" class="layui-input" id="squareGram">',
										'</div>',
									'</div>',
								 '</div>',].join(''),
							yes:function(){
								myutil.saveAjax({
									url:'/ledger/inventory/inspectionMaterialPutStorage',
									type:'get',
									data:{
										squareGram: $('#squareGram').val(),
										id: check[0].id,
									},
									success:function(){
										layer.close(win);
										table.reload('tableData');
									}
								})
							}
						})
					}else if(obj.event=="update"){
						if(check.length!=1)
							return myutil.emsg('只能修改一条数据');
						outWarehouseOrder.update({
							data: check[0],
							success: function(){
								table.reload('tableData');
							}
						});
					}
				},
				btn:[4],
			},
			toolbar:[
				'<span class="layui-btn layui-btn-sm" lay-event="update">修改</span>',
			].join(''),
			autoUpdate:{
				saveUrl:'',
				deleUrl:'/ledger/inventory/deleteMaterialPutStorage',
				field:{ },
			},
			ifNull:'--',
			cols:[[
			       { type:'checkbox',},
			       { title:'出库编号', field:'serialNumber',},
			       { title:'出库时间',   field:'arrivalTime', type:'dateTime',width:'10%',	},
			       { title:'出库数量',   field:'arrivalNumber',   },
			       { title:'出库操作人',   field:'userStorage_userName',	},
			       { title:'面料',   field:'materiel_name',	},
			       { title:'状态',   field:'outStatus',  transData:{ data:['','生产出库','调拨出库','销售换货出库','采购退货出库','盘盈出库'], } },
			       ]]
		})
		form.on('submit(search)',function(obj){
			var field = obj.field;
			if(field.orderTimeBegin){
				var time = field.orderTimeBegin.split(' ~ ');
				field.orderTimeBegin = time[0]+' 00:00:00';
				field.orderTimeEnd = time[1]+' 23:59:59';
			}else{
				field.orderTimeEnd = ''; 
			}
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
		outWarehouseOrder.init();
	}//end define function
)//endedefine
</script>
</body>
</html>