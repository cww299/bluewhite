<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>入库单</title>
	<style>
	  .searchTable td:nth-of-type(odd){
	  	padding: 5px 0;
	  	padding-left: 15px;
	  	padding-right: 5px;
	  }
	  .btnTd{
	    text-align:center;
	    padding-left: 0px;
	  	padding-right: 0px;
	  	padding-bottom: 0px;
	  	padding-top: 2px;
	  }
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td>入库日期:</td>
				<td><input type="text" name="orderTimeBegin" id="inputDate" class="layui-input"></td>
				<td>物料编号:</td>
				<td><input type="text" name="materielNumber" class="layui-input"></td>
				<td>物料名称:</td>
				<td><input type="text" name="materielName" class="layui-input"></td>
				<td>库区:</td>
				<td><select name="storageAreaId" id="storageAreaId"><option value="">请选择</option></select></td>
			</tr>
			<tr>
				<td>库位:</td>
				<td><select name="storageLocationId" id="storageLocationId"><option value="">请选择</option></select></td>
				<td>是否验货:</td>
				<td><select name="inspection"><option value="">请选择</option>
											  <option value="0">否</option>
											  <option value="1">是</option></select></td>
				<td>采购单状态:</td>
				<td><select name="inStatus"><option value="">请选择</option>
							<option value="1">采购入库</option>
							<option value="2">调拨入库</option>
							<option value="3">退货入库</option>
							<option value="4">换货入库</option>
							<option value="5">盘亏入库</option></select></td>
				<td colspan="2" class="btnTd">
					<button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
<script>
var addEditReturnTpl = [
	'<div class="layui-form layui-form-pane" style="padding:20px;margin:10px 5px;">',
	  '<div class="layui-form-item" pane>',
	    '<label class="layui-form-label">退货时间</label>',
	    '<div class="layui-input-block">',
	      '<input type="text" name="time" class="layui-input" id="returnTime" required>',
	    '</div>',
	  '</div>',
	  '<div class="layui-form-item" pane>',
	    '<label class="layui-form-label">退货数量</label>',
	    '<div class="layui-input-block">',
	      '<input type="number" name="number" class="layui-input" lay-verify="number" value="{{ d.number || "" }}">',
	    '</div>',
	  '</div>',
	  '<div class="layui-form-item" pane>',
	    '<label class="layui-form-label">退货原因</label>',
	    '<div class="layui-input-block">',
	      '<input type="text" name="remark" class="layui-input" value="{{ d.remark || "" }}">',
	    '</div>',
	  '</div>',
	  '<p style="display:none;">',
	  	'<span lay-submit lay-filter="addReturn" id="addReturn"></span>',
	  	'<input type="hidden" name="id" value="{{ d.id || "" }}">',
	  	'<input type="hidden" name="materielId" value="{{ d.materielId || "" }}">',
	  	'<input type="hidden" name="materialPutStorageId" value="{{ d.materialPutStorageId || ""}}">',
	  '</p>',
	'</div>',
].join(' ');
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
	inputWarehouseOrder: 'layui/myModules/warehouseManager/inputWarehouseOrder',
}).define(
	['mytable','inputWarehouseOrder','laydate','laytpl'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laytpl = layui.laytpl
		, laydate = layui.laydate
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, inputWarehouseOrder = layui.inputWarehouseOrder
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({elem:'#inputDate',range:'~'})
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/ledger/inventory/materialPutStoragePage',
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
						inputWarehouseOrder.update({
							data: check[0],
							success: function(){
								table.reload('tableData');
							}
						});
					}else if(obj.event=='addReturn'){
						if(check.length!=1)
							return myutil.emsg('只能修改一条数据');
						addReturnOrder({
							materialPutStorageId: check[0].id,
							materielId: check[0].materiel.id,
						});
					}else if(obj.event=='lookoverReturn'){
						if(check.length!=1)
							return myutil.emsg('只能修改一条数据');
						lookoverReturnOrder(check[0]);
					}
				},
				btn:[4],
			},
			toolbar:[
				'<span class="layui-btn layui-btn-sm" lay-event="update">修改</span>',
				'<span class="layui-btn layui-btn-normal layui-btn-sm" lay-event="verify">验货</span>',
				//'<span class="layui-btn layui-btn-warm layui-btn-sm" lay-event="addReturn">新增退货单</span>',
				//'<span class="layui-btn layui-btn-primary layui-btn-sm" lay-event="lookoverReturn">查看退货单</span>',
			].join(''),
			autoUpdate:{
				saveUrl:'',
				deleUrl:'/ledger/inventory/deleteMaterialPutStorage',
				field:{ },
			},
			ifNull:'--',
			cols:[[
			       { type:'checkbox',},
			       { title:'入库编号', field:'serialNumber',width:200,	},
			       { title:'面料',   field:'materiel_name',	width:'8%',},
			       { title:'入库时间',   field:'arrivalTime', type:'date',	width:120,	},
			       { title:'入库数量',   field:'arrivalNumber',   },
			       { title:'剩余数量',   field:'surplusNumber',width:'8%',	},
			       { title:'库区',   field:'storageArea_name', 	},
			       { title:'库位',   field:'storageLocation_name',	},
			       { title:'入库人',   field:'userStorage_userName',	},
			       { title:'平方克重',   field:'squareGram',	},
			       { title:'入库内容',   field:'orderProcurement_orderProcurementNumber',	width:'20%'},
			       { title:'状态',   field:'inStatus',  transData:{ data:['','采购入库','调拨入库','退货入库','换货入库','盘亏入库'], } },
			       { title:'是否验货',   field:'inspection',  transData:{ data:['否','是',], } },
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
		function addReturnOrder(data){
			var title = '新增退货单';
			if(data.id){
				title = '修改退货单';
			}
			laytpl(addEditReturnTpl).render(data,function(h){
				html = h;
			})
			layer.open({
				type:1,
				title: title,
				content: html,
				btn:['确定','取消'],
				btnAlign:'c',
				area:'auto',
				offset:'150px',
				success:function(winE,winIndex){
					laydate.render({elem:'#returnTime',type:'datetime',value:data.time || new Date(), });
					form.on('submit(addReturn)',function(obj){
						var field = obj.field;
						myutil.saveAjax({
							url:'/ledger/inventory/saveMaterialReturn',
							data:field,
							success:function(){
								layer.close(winIndex);
								table.reload('tableData');
								if(table.cache['returnOrderTable'])
									table.reload('returnOrderTable');
							}
						})
					})
				},
				yes:function(winE,winIndex){
					$('#addReturn').click();
				}
			})
		}
		function lookoverReturnOrder(data){
			layer.open({
				type:1,
				title: '退货单',
				content: [
					'<div style="padding:10px;">',
						'<table id="returnOrderTable" lay-filter="returnOrderTable"></table>',
					'</div>',
				].join(' '),
				area:['800px','600px'],
				shadeClose:true,
				offset:'150px',
				success:function(winE,winIndex){
					mytable.renderNoPage({
						elem:'#returnOrderTable',
						url: myutil.config.ctx+'/ledger/inventory/getMaterialReturn?materialPutStorageId='+data.id,
						curd:{
							btn:[4],
							otherBtn:function(obj){
								if(obj.event=='edit'){
									var check = layui.table.checkStatus('returnOrderTable').data;
									if(check.length!=1)
										return myutil.emsg('只能修改一条数据');
									check[0].materialPutStorageId = data.id;
									check[0].materielId = data.materiel.id;
									addReturnOrder(check[0]);
								}
							},
						},
						autoUpdate:{
							deleUrl:'/ledger/inventory/deleteMaterialReturn ',
						},
						toolbar:[
							'<span class="layui-btn layui-btn-normal layui-btn-sm" lay-event="edit">修改</span>',
						].join(' '),
						cols:[[
							{ type:'checkbox', },
							{ title:'退货时间',field:'time', },
							{ title:'退货数量',field:'number', },
							{ title:'退货原因',field:'remark', },
						]]
					})
				},
			})
		}
		inputWarehouseOrder.init(function(){
			$('#storageLocationId').append(inputWarehouseOrder.allStorageLocation)
			$('#storageAreaId').append(inputWarehouseOrder.allStorageArea)
			form.render();
		});
	}//end define function
)//endedefine
</script>
</body>
</html>