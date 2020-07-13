<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>杂支应付</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td>客户名称：</td>
				<td><input type="text" name="customerName" class="layui-input"></td>
				<td>日期：</td>
				<td><input type="text" id="searchTime" class="layui-input"></td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="mixTable" lay-filter="mixTable"></table>
	</div>
</div>
<script type="text/html" id="addEditTpl">
<div class="layui-form" style="padding:10px;">
	<div class="layui-form-item">
		<label class="layui-form-label"><b class="red">*</b>客户</label>
		<div class="layui-input-block">
			<input class="layui-input" lay-verify="required" value="{{ d.customer ? d.customer.name : ""}}" 
					id="chooseCustomer" placeholder="点击选择客户" readonly>
			<input type="hidden" name="customerId" value="{{ d.customer ? d.customer.id : "" }}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><b class="red">*</b>日期</label>
		<div class="layui-input-block">
			<input class="layui-input" name="mixtTime" value="{{ d.mixtTime || "" }}" 
				id="mixtTime" lay-verify="required">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><b class="red">*</b>金额</label>
		<div class="layui-input-block">
			<input type="text" value="{{ d.mixPrice || 0 }}" name="mixPrice" class="layui-input"
					lay-verify="required">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">明细</label>
		<div class="layui-input-block">
			<input type="text" value="{{ d.mixDetailed || "" }}" name="mixDetailed" class="layui-input">
		</div>
	</div>
	<input type="hidden" name="id" value="{{ d.id || "" }}">
	<span style="display:none;" lay-submit lay-filter="addEditBtn" ></span>
</div>
</script>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
	chooseCustomer: 'layui/myModules/common/chooseCustomer'
}).define(
	['mytable','laydate','laytpl','chooseCustomer'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, laydate = layui.laydate
		, chooseCustomer = layui.chooseCustomer
		, mytable = layui.mytable;
		
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({ elem: '#searchTime',range: '~' })
		var allCustomer = [];
		
		myutil.getData({
			url:'${ctx}/ledger/allCustomer?customerTypeId=459',
			done: function(data){
				allCustomer = data;
			}
		});
		
		mytable.render({
			elem:'#mixTable',
			url:'${ctx}/ledger/mixedPage',
			cols:[[
			       { type:'checkbox',},
			       { title:'客户',   field:'customer_name' }, 
			       { title:'日期',   field:'mixtTime',   type: 'date' },
			       { title:'金额',   field:'mixPrice',	},
			       { title:'明细',   field:'mixDetailed', },
		       ]],
		    curd: {
		    	btn: [4],
		    	otherBtn: function(obj){
		    		if(obj.event == "add"){
		    			addEdit();
		    		}else if(obj.event == "edit"){
		    			var check = table.checkStatus('mixTable').data
		    			if(check.length != 1)
		    				return myutil.emsg("请选择一条数据进行编辑");
		    			addEdit(check[0]);
		    		}
		    	}
		    },
		    autoUpdate:{
		    	deleUrl: "/ledger/deleteMixed",
		    },
		    toolbar: [
		    	'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="add">新增杂支</span>',
		    	'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="edit">修改杂支</span>',
		    ].join(''),
		})
		
		function addEdit(data = {}){
			layer.open({
				type: 1,
				title: '杂支信息',
				btn: ['保存','取消'],
				btnAlign: 'c',
				offset: '150px',
				area: ['540px', '350px'],
				content: laytpl($('#addEditTpl').html()).render(data),
				success: function(layerElem, layerIndex) {
					form.render();
					laydate.render({
						elem: '#mixtTime',
						value: data.mixtTime ? data.mixtTime.split(' ')[0] : new Date(),
					})
					$('#chooseCustomer').click(function(){
						chooseCustomer.choose({ type: ['xx','ds']}).then(data => {
							$('input[type="hidden"][name="customerId"]').val(data.id)
							$('#chooseCustomer').val(data.name)
						})
					})
					form.on('submit(addEditBtn)',function(obj){
						obj.field.mixtTime += " 00:00:00"
						myutil.saveAjax({
							url: '/ledger/addMixed',
							data: obj.field,
							success: function(){
								layer.close(layerIndex)
								table.reload('mixTable')
							}
						})
					})
				},
				yes: function(){
					$('[lay-filter="addEditBtn"]').click()
				}
			})
		}
		
		form.on('submit(search)',function(obj){
			var val = $('#searchTime').val(),beg='',end='';
			if(val!=''){
				beg = val.split('~')[0].trim()+' 00:00:00';
				end = val.split('~')[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = beg;
			obj.field.orderTimeEnd = end;
			table.reload('mixTable',{
				where: obj.field,
				page: {curr:1}
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>