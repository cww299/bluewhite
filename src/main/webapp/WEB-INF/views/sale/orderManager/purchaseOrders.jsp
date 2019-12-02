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
	<style>
	.showInState3,.showInState5{
		display:none;
	}
	.updateDiv{
	    padding: 0 30px;
    	padding-top: 10px;
	}
	.updateDiv .layui-form-label{
		width: 110px;
	}
	.updateDiv .layui-input-block{
		margin-left: 140px;
	}
	</style>
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

<script id="updateTpl" type="text/html">
<div class="layui-form updateDiv" action="">
  <div class="layui-form-item">
    <label class="layui-form-label">到货接收状态</label>
    <div class="layui-input-block">
      <select name="arrivalStatus" id="stateSelect" lay-filter="stateSelect">
			  <option value="1">全部接收</option>
      	      <option disabled value="2">全部退货</option>
      	      <option value="3">降价接收</option>
      	      <option disabled value="4">部分接收，部分退货</option>
      	      <option value="5">部分接收，部分延期付款</option></select>
    </div>
  </div>
  <div class="layui-form-item showInState3">
    <label class="layui-form-label">面料价格</label>
    <div class="layui-input-block">
      <input type="text" name="price" class="layui-input" value="{{d.price}}" id="updatePrice">
    </div>
  </div>
  <div class="layui-form-item showInState3">
    <label class="layui-form-label">应付总价</label>
    <div class="layui-input-block">
      <input type="text" name="paymentMoney" class="layui-input" value="{{d.paymentMoney}}" readOnly id="updatePay">
    </div>
  </div>
  <div class="layui-form-item showInState5">
    <label class="layui-form-label">延期付款数量</label>
    <div class="layui-input-block">
      <input type="text" name="partDelayNumber" class="layui-input" value="{{d.partDelayNumber || '' }}" id="updatePrice5">
    </div>
  </div>
  <div class="layui-form-item showInState5">
    <label class="layui-form-label">延期付款金额</label>
    <div class="layui-input-block">
      <input type="text" name="partDelayPrice" class="layui-input" value="{{ d.partDelayPrice || ''}}" readOnly id="updatePay5">
    </div>
  </div>
  <div class="layui-form-item showInState5">
    <label class="layui-form-label">延期付款日期</label>
    <div class="layui-input-block">
      <input type="text" name="partDelayTime" class="layui-input" value="{{d.partDelayTime || ''}}" id="updateTime">
	  <input type="hidden" name="id"  value="{{d.id}}">
      <span style="display:none;" lay-submit lay-filter="updateBtn" id="updateBtn">修改</span>
    </div>
  </div>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laytpl','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/ledger/getOrderProcurement?inspection=1',
			size:'lg',
			ifNull:'',
			scrollX:true,
			toolbar: [
					  '<span lay-event="creatBill" class="layui-btn layui-btn-sm layui-btn-normal">生成账单</span>',
					  '<span lay-event="update" class="layui-btn layui-btn-sm">修改</span>',].join(''),
			curd:{
				btn:[],
				otherBtn:function(obj){
					if(obj.event=='creatBill'){
						myutil.deleTableIds({
							url:'/ledger/billOrderProcurement',
							table:'tableData',
							text:'请选择信息|是否确认生成账单？',
						})
					}else if(obj.event=='update'){
						var check = table.checkStatus('tableData').data;
						if(check.length!=1)
							return myutil.emsg('只能修改一条数据');
						openUpdateWin(check[0]);
					}
				}
			},
			colsWidth:[0,10,8,8,40,6,6,6,6,6,8,8,8,6,8,8,8,8,8,8,6],
			cols:[[
					{ type:'checkbox',fixed:'left' },
					{ title:'物料名', field:'materiel_name', fixed:'left',},
					{ title:'物料定性', field:'materiel_materialQualitative_name', },
					{ title:'下单日期', field:'placeOrderTime', type:'date',},
					{ title:'采购编号', field:'orderProcurementNumber', },
					{ title:'采购数量', field:'placeOrderNumber', },
					{ title:'面料价格', field:'price', },
					{ title:'总价格', field:'paymentMoney', },
					{ title:'约定克重', field:'conventionSquareGram', },
					{ title:'订购人', field:'user_userName', },
					{ title:'供应商', field:'customer_name', },
					{ title:'预计到货日期', field:'expectArrivalTime',type:'date',},
					{ title:'实际到货日期', field:'arrivalTime',  type:'date', },
					{ title:'到货数量', field:'arrivalNumber', },
					{ title:'延期付款数量', field:'partDelayNumber', },
					{ title:'延期付款金额', field:'partDelayPrice', },
					{ title:'延期付款日期', field:'partDelayTime', type:'date',},
					{ title:'缺克重价值', field:'gramPrice', },
					{ title:'占用资金利息', field:'interest', },
					{ title:'加急补货', field:'replenishment', templet:getTpl(), },
					{ title:'生成账单', field:'bill', fixed:'right',transData:{data:['否','是']}},
			       ]]
		})
		function getTpl(){transData:{data:['正常','加急补货']}
			return function(d){
				if(d.replenishment==0)
					return '<span class="layui-badge layui-bg-green">正常</span>';
				return '<span class="layui-badge">正常加急补货</span>';
			}
		}
		function openUpdateWin(d){
			var html = '';
			laytpl($('#updateTpl').html()).render(d,function(h){
				html = h;
			})
			var win = layer.open({
				type:1,
				title:'修改采购单',
				content: html,
				offset:'80px',
				area:['500px','300px'],
				btn:['确定修改','取消'],
				btnAlign:'c',
				success:function(){
					laydate.render({ elem:'#updateTime',type:'datetime'});
					$('#stateSelect').val(d.arrivalStatus);
					if(d.arrivalStatus==3)
						$('.showInState3').show();
					else if(d.arrivalStatus==5)
						$('.showInState5').show();
					else if(d.arrivalStatus==2 || d.arrivalStatus==4)
						$('#stateSelect').attr('disabled','disabled');
					
					$('#updatePrice5').unbind().blur(function(){
						var price = $(this).val(),allPrice = 0;
						if(isNaN(price) || price<0){
							myutil.emsg('请正确输入价格！');
							price = d.price;
							allPrice = d.paymentMoney;
						}else{
							allPrice = price*d.arrivalNumber;
						}
						$('#updatePay5').val(allPrice);
					})
					$('#updatePrice').unbind().blur(function(){
						var price = $(this).val(),allPrice = 0;
						if(isNaN(price) || price<0){
							myutil.emsg('请正确输入价格！');
							price = d.price;
							allPrice = d.paymentMoney;
						}else{
							allPrice = price*d.arrivalNumber;
						}
						$('#updatePay').val(allPrice);
					})
					form.on('select(stateSelect)',function(obj){
						if(obj.value==3){
							$('.showInState3').show();
							$('.showInState5').hide();
						}else if(obj.value==5){
							$('.showInState5').show();
							$('.showInState3').hide();
						}
					})
					form.on('submit(updateBtn)',function(obj){
						var data = obj.field;
						if(data.arrivalStatus==5){
							if(!data.partDelayNumber || !data.partDelayPrice || !data.partDelayTime)
								return myutil.emsg('请正确填写数据！');
						}else if(data.arrivalStatus==3){
							if(!data.price || !data.paymentMoney)
								return myutil.emsg('请正确填写数据！');
						}
						myutil.saveAjax({
							url:'/ledger/updateBillOrderProcurement',
							data: data,
							success:function(){
								layer.close(win);
								table.reload('tableData');
							}
						})
					})
					form.render();
				},
				yes: function(){
					$('#updateBtn').click();
				}
			})
		}
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>