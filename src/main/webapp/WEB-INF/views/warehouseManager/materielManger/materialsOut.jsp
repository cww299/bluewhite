<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/formSelect/formSelects-v4.css" />
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>领料出库</title>
	<style>
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td style="width:100px;">
					<select id="timeType">
						<option value="requisitionTime">领取时间</option>
						<option value="openOrderTime">开单时间</option>
					</select></td>
				<td><input type="text" name="orderTimeBegin" id="searchTime" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>领取人：</td>
				<td><input type="text" name="customerName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>库存单编号：</td>
				<td><input type="text" name="orderProcurementNumber" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="searchBtn">搜索</span></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script type="text/html" id="auditWinTpl">
<div class="layui-form layui-form-pane" style="padding:10px;">
  <div class="layui-form-item" pane>
    <label class="layui-form-label">出库数量：</label>
    <div class="layui-input-block">
      <input type="number" name="arrivalNumber" class="layui-input" lay-verify="required">
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">备注：</label>
    <div class="layui-input-block">
      <input type="text" name="remark" class="layui-input">
    </div>
  </div>
  <p style="display:none;">
	<span lay-filter="sureAuditBtn" id="sureAuditBtn" lay-submit>1</span>
  </p>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laydate',],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table
		, laydate = layui.laydate
		, outOrderModel = layui.outOrderModel
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({
			elem:'#searchTime',
			range:'~',
		})
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/ledger/getMaterialRequisition?audit=1',
			scrollX:true,
			curd:{
				btn:[],
				otherBtn:function(obj){
					if(obj.event=="audit"){
						var check = table.checkStatus('tableData').data;
						if(check.length!=1)
							return myutil.emsg('只能审核一条数据');
						var auditWin = layer.open({
							type:1,
							title:'审核出库',
							offset:'50px',
							content: $('#auditWinTpl').html(),
							area:['400px','250px'],
							btn:['确定','取消'],
							btnAlign:'c',
							success:function(){
								form.on('submit(sureAuditBtn)',function(obj){
									obj.field.materialRequisitionId = check[0].id;
									obj.field.materielId = check[0].scatteredOutbound.orderMaterial.materiel.id;
									myutil.saveAjax({
										url:'/ledger/inventory/outboundMaterialRequisition',
										data: obj.field,
										success:function(){
											layer.close(auditWin);
											table.reload('tableData');
										}
									})
								})
							},
							yes:function(){
								$('#sureAuditBtn').click();
							}
						})
					}
				}
			},
			autoUpdate:{
				saveUrl:'/ledger/updateiInventoryMaterialRequisition'
			},
			toolbar:['<span class="layui-btn layui-btn-sm" lay-event="audit">审核出库</span>',].join(' '),
			colsWidth:[0,8,8,12,0,7,8,8,12,8],
			ifNull:'',
			cols:[[
			       { type:'checkbox',fixed:'left',},
			       { title:'开单时间',   field:'openOrderTime',	fixed:'left', type:'date'},
			       { title:'物料编号',   field:'scatteredOutbound_orderMaterial_materiel_name',  fixed:'left',},
			       { title:'物料名称',   field:'scatteredOutbound_orderMaterial_materiel_number',  },
			       { title:'库存单编号',   field:'scatteredOutbound_orderProcurement_orderProcurementNumber',},
			       { title:'领取模式',   field:'scatteredOutbound_orderMaterial_receiveMode_name',  },
			       { title:'领取人',   field:'name', templet: getName(),   },
			       { title:'领取总量',   field:'dosage',	},
			       { title:'已领数量',   field:'requisitionCount',	},
		    ]]
		})
		function getName(){
			return function(d){
				if(d.user)
					return d.user.userName;
				if(d.customer)
					return d.customer.name;
				else
					return "";
			}
		}
		form.on('submit(searchBtn)',function(obj){
			var f = obj.field;
			if(f.orderTimeBegin){
				var time = f.orderTimeBegin.split(' ~ ');
				f.orderTimeBegin = time[0]+' 00:00:00';
				f.orderTimeEnd = time[1]+' 23:59:59';
			}else{
				f.orderTimeEnd = '';
			}
			var timeType = $('#timeType').val();
			if(timeType=='requisitionTime'){
				f.requisitionTime = '2019-11-20 00:00:00';
				f.openOrderTime = '';
			}else{
				f.requisitionTime = '';
				f.openOrderTime = '2019-11-20 00:00:00';
			}
			table.reload('tableData',{
				where: f,
			})
		})
	}//end define function
)//endedefine
</script>
</html>