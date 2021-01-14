<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>耗料明细</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>客户名称：</td>
				<td><input type="text" name="customerName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>批次号：</td>
				<td><input type="text" name="bacthNumber" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>下单时间：</td>
				<td><input type="text" class="layui-input" id="searchTime"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>产品名：</td>
				<td><input type="text" class="layui-input" name="productName"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>产品编号：</td>
				<td><input type="text" class="layui-input" name="productNumber"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableAgreement" lay-filter="tableAgreement"></table>
	</div>
</div>
<!-- 
trialProduce 试制部，生成，审核
salesManagement 生产计划部  查看
 -->
<div id="toolbar" style="display:none;">
	<div>
		<shiro:hasAnyRoles name="superAdmin,trialProduce" >
			<span lay-event="productUseup" class="layui-btn layui-btn-sm" id="isTrialProduce">生成耗料单</span>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="superAdmin,salesManagement">
		</shiro:hasAnyRoles>
		<span lay-event="lookoverUseup" class="layui-btn layui-btn-sm layui-btn-normal" >查看耗料单</span>
	</div>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
}).define(
	['mytable','laydate',],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, orderAgreementTpl = layui.orderAgreementTpl
		, myutil = layui.myutil
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		myutil.config.msgOffset = '250px';
		
		var isTrialProduce = $('#isTrialProduce').length > 0;
		var allUserSelectHtml = '<option value="">请选择</option>';
		myutil.getData({
			url:'${ctx}/system/user/findUserList',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++)
					allUserSelectHtml+= '<option value="'+d[i].id+'">'+d[i].userName+'</option>';
			}
		})
		laydate.render({ elem:'#searchTime', range:'~' })
		mytable.render({
			elem:'#tableAgreement',
			url:'${ctx}/ledger/orderPage?audit=1',
			toolbar: $('#toolbar').html(),
			cols:[[
			       { type:'checkbox',},
			       { title:'批次号',   	field:'bacthNumber', 	},
			       { title:'下单时间',   	field:'orderDate',  type:'date'	},
			       { title:'产品编号',	field:'product_number',  		},
			       { title:'产品名称',	field:'product_name', },
			       { title:'数量',   field:'number',	 },
			       { title:'放数数量',   field:'putNumber',	 },
			       { title:'备注',   field:'remark',	 },
			       { title:'生成耗料单',   field:'',   templet:getTpl(),	 },
			       { title:'备料充足',   field:'prepareEnough', transData:{data:['否','是']}},
			       ]]
		})
		function getTpl(){
			return function(d){
				if(d.orderMaterials && d.orderMaterials.length>0)
					return '<span class="layui-badge layui-bg-green">是</span>';
				return '<span class="layui-badge layui-bg">否</span>';
			}
		}
		form.on('submit(search)',function(obj){
			var time = $('#searchTime').val();
			var begin='',end='';
			if(time!=''){
				begin = time.split('~')[0].trim()+' 00:00:00';
				end = time.split('~')[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = begin;
			obj.field.orderTimeEnd = end;
			table.reload('tableAgreement',{
				where:obj.field,
				page:{ curr:1 }
			})
		}) 
		table.on('toolbar(tableAgreement)',function(obj){
			switch(obj.event){
			case 'lookoverUseup': lookoverUseup(); break;
			case 'productUseup': productUseup(); break;
			} 
		})
		var mode = [];
		function lookoverUseup(){
			var checked = layui.table.checkStatus('tableAgreement').data;
			if(checked.length!=1)
				return myutil.emsg('只能查看一条信息');
			if(mode.length==0){
				mode = myutil.getDataSync({ url: '${ctx}/product/getBaseOne?type=overstock' });
				myutil.getDataSync({ 
					url: '${ctx}/product/getBaseOne?type=tailor',
					success:function(d){
						for(var i in d)
							mode.push(d[i]);
					}
				});
			}
			layer.open({
				type:1,
				title:'耗料订单',
				area:['80%','80%'],
				content:[
				          '<div style="padding:0px;">', 
				         	 '<table id="lookoverTable" lay-filter="lookoverTable"></table>', 
				          '</div>', 
				         ].join(''),
				shadeClose:true,
				success:function(){
					mytable.render({
						elem:'#lookoverTable',
						url:'${ctx}/ledger/getOrderMaterial?orderId='+checked[0].id + (isTrialProduce? "" : "&audit=1"),
						toolbar: isTrialProduce ? 
								'<span class="layui-btn layui-btn-sm" lay-event="onekey">一键审核</span>' +
								'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cancel">取消审核</span>': "",
						size:'lg',
						limit:15,
						limits:[10,15,20,50,],
						colsWidth:[0,0,10,10,20,10],
						curd:{
							btn: isTrialProduce ? [4] : [],
							otherBtn:function(obj){
								if(obj.event=="onekey"){
									myutil.deleTableIds({
										url:'/ledger/auditOrderMaterial',
										table:'lookoverTable',
										text:'请选择相关信息|是否确认？',
									})
								} else if (obj.event == 'cancel') {
									myutil.deleTableIds({
										url:'/ledger/cancelAuditOrderMaterial',
										table:'lookoverTable',
										text:'请选择相关信息|是否确认？',
									})
									
								}
							}
						},
						autoUpdate:{
							deleUrl:'/ledger/deleteOrderMaterial',
							saveUrl:'/ledger/updateOrderMaterial',
							field:{receiveMode_id:'receiveModeId' },
						},
						cols:[[
							   { type:'checkbox', },
						       { title:'物料名',   field:'materiel_name', templet: getName(), },
						       { title:'领取用量',   field:'', templet: function(d) { return d.dosage + '/' +d.unit.name }	},
						       { title:'转换用量',   field:'', templet: getConvert()	},
						       { title:'领取模式',   field:'receiveMode_id',	type:'select', select:{data:mode}, },
						       { title:'审核状态', field:'audit', transData:{ data:['未审核','审核'] }},
					       ]],
					})
					
				},
			})
		}
		function getConvert() {
			return function(d) {
				var html = '---'
				const mate = d.materiel
				if(mate.convertUnit) {
					if(d.unit.id == mate.unit.id) {	// 如果当前单位是转化前的单位，这里输出转化后的单位
						html = (d.dosage * mate.converts).toFixed(2) + '/' + mate.convertUnit.name
					} else if(d.unit.id == mate.convertUnit.id) {
						html = (d.dosage / mate.converts).toFixed(2) + '/' + mate.unit.name
					}
				}
				return html
			}
		}
		function getName(){
			return function(d){
				var html = d.materiel.name
				if(d.cutPartsName) {
					html = '(<b class="red">'+ d.cutPartsName +'</b>)&nbsp;&nbsp;&nbsp;&nbsp;' + html
				}
				return html
			}
		}
		function productUseup(){
			myutil.deleTableIds({
				url:'/ledger/confirmOrderMaterial',
				table:'tableAgreement',
				text:'请选择相关信息|是否确认生成耗料表',
			})
		}
	}//end define function
)//endedefine
</script>

</html>