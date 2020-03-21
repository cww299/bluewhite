<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<meta charset="utf-8">
	<title>物流管理财务汇总</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="searchTable layui-form">
			<tr>
				<td>客户名称：</td>
				<td><input type="text" name="customer.name_like" class="layui-input"></td>
				<td>物流点：</td>
				<td><input type="text" name="logistics.name_like" class="layui-input"></td>
				<td>包装方式：</td>
				<td><select name="outerPackagingId" id="searchOuterPackagingId">
					<option value="">请选择</option></select></td>
				<td>结算方式：</td>
				<td style="width:100px;"><select name="settlement">
					<option value="">请选择</option>
					<option value="1">单月结</option>
					<option value="2">双月结</option></select></td>
				<td>付款方式：</td>
				<td style="width:100px;"><select name="payment">
					<option value="">请选择</option>
					<option value="1">到付</option>
					<option value="2">垫付</option></select></td>
				<td></td>
				<td><span class="layui-btn" lay-submit lay-filter="searchBtn">搜索</span></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
<script type="text/html" id="addEditTpl">
	<div class="layui-form layui-form-pane" style="padding:20px;"> 
		<div class="layui-form-item" pane>
		    <label class="layui-form-label">客户</label> 
		    <div class="layui-input-block">
			    <select name="customerId" lay-verify="required" lay-search>
					<option value="">请选择</option></select>
			</div>
		</div>
	    <div class="layui-form-item" pane>
		    <label class="layui-form-label">物流点</label>
		    <div class="layui-input-block">
			    <select name="logisticsId" lay-verify="required" lay-search>
					<option value="">请选择</option></select></div></div>
	    <div class="layui-form-item" pane>
		    <label class="layui-form-label">包装方式</label>
		    <div class="layui-input-block">
	    	    <select name="outerPackagingId" lay-verify="required" lay-search>
	    	    	<option value="">请选择</option></select></div></div>
	    <div class="layui-form-item" pane>
	        <label class="layui-form-label">含税价</label>
	        <div class="layui-input-block">
	            <input type="text" name="taxIncluded" placeholder="请输入" lay-verify="required"
	            	value="{{ d.taxIncluded || 0 }}" class="layui-input"></div></div>
	    <div class="layui-form-item" pane>
		    <label class="layui-form-label">不含税价</label>
		    <div class="layui-input-block">
			    <input type="text" name="excludingTax" placeholder="请输入" lay-verify="required"
			    	value="{{ d.excludingTax || 0 }}" class="layui-input"></div></div>
		<div class="layui-form-item">
		    <label class="layui-form-label" pane>结算方式</label>
		    <div class="layui-input-block">
			    <select name="settlement" lay-verify="required">
					<option value="1" {{ d.settlement!=2?"selected":"" }}>单月结</option>
					<option value="2" {{ d.settlement==2?"selected":"" }}>双月结</option></select>
			</div></div>
		<div class="layui-form-item" pane>
		    <label class="layui-form-label">付款方式</label>
		    <div class="layui-input-block">
			    <select name="payment" lay-verify="required">
					<option value="1" {{ d.payment!=2?"selected":"" }}>到付</option>
					<option value="2" {{ d.payment==2?"selected":"" }}>垫付</option></select>
			</div></div>
		<input type="hidden" value="{{ d.id || "" }}" name="id">
	    <button type="button" lay-submit lay-filter="sureAddEditBtn" style="display:none;"></button>
	</div>
</script>
<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(['mytable','jquery','laytpl','form'],
	function() {
		var $ = layui.jquery
		table = layui.table,
		laytpl = layui.laytpl,
		form = layui.form,
		mytable = layui.mytable,
		myutil = layui.myutil;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		var logisticsHtml = myutil.getBaseDataSelect({ type: 'logistics', });
		var packagMethodHtml = myutil.getBaseDataSelect({ type: 'outerPackaging', });
		var allCustomer = '';
		$('#searchOuterPackagingId').append(packagMethodHtml);
		form.render();
		myutil.getData({
			url: myutil.config.ctx+'/ledger/getCustomer?customerTypeId=459',
			success:function(d){
				for(var i in d){
					allCustomer += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
			}
		})
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/ledger/logisticsCostsPage',
			toolbar:[
				'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="add">新增</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-primary" lay-event="edit">修改</span>',
			].join(' '),
			curd:{
				btn:[4],
				otherBtn:function(obj){
					var check = table.checkStatus('tableData').data;
					if(obj.event==='add'){
						addEdit({
							data:{},
							type:'add',
						});
					}else if(obj.event==="edit"){
						if(check.length!=1)
							return myutil.emsg("只能选择一条数据进行编辑");
						addEdit({
							data:check[0],
							type:'edit',
						});
					}
				},
			},
			autoUpdate:{
				deleUrl:'/ledger/deleteLogisticsCosts',
			},
			cols:[[
				{ type:'checkbox', },
				{ title:'客户名', field:'customer_name', },
				{ title:'包装', field:'outerPackaging_name', },
				{ title:'不含税价', field:'excludingTax', },
				{ title:'含税价格', field:'taxIncluded', },
				{ title:'物流', field:'logistics_name', },
				{ title:'结算方式', field:'settlement',transData:{data:["","单月结","双月结"]} },
				{ title:'付款方式', field:'payment',transData:{data:["","到付","垫付"]} },
			]]
		})
		
		function addEdit(opt){
			var title = '新增物流单价';
			if(opt.type=='edit'){
				title = '修改物流单价';
			}
			layer.open({
				title : title
				,type : 1
				,btn : ['确定','取消']
				,area:['400px','540px']
				,shade : 0
				,content : laytpl($('#addEditTpl').html()).render(opt.data)
				,yes : function(){
					$('button[lay-filter="sureAddEditBtn"]').click();
				}
				,success:function(layerElem,layerIndex){
					$('select[name="logisticsId"]').append(logisticsHtml);
					$('select[name="outerPackagingId"]').append(packagMethodHtml);
					$('select[name="customerId"]').append(allCustomer);
					if(opt.data.id){
						var d = opt.data;
						$('select[name="logisticsId"]').val(d.logistics.id);
						$('select[name="outerPackagingId"]').val(d.outerPackaging.id);
						$('select[name="customerId"]').val(d.customer.id);
					}
					form.on('submit(sureAddEditBtn)',function(obj){
						myutil.saveAjax({
							url: '/ledger/addlogisticsCosts',
							data:obj.field,
							success:function(){
								table.reload('tableData');
								layer.close(layerIndex);
							},
						})
					})
					form.render();
				}
			});
		}
		form.on('submit(searchBtn)',function(obj){
			table.reload('tableData',{
				where:obj.field,
				page:{ curr:1 },
			})
		})
	}
)
</script>
</body>
</html>