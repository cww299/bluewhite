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
}).define(
	['layer','mytable','laydate','sendGoodOrder'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form		
		, laydate = layui.laydate
		, myutil = layui.myutil
		, table = layui.table 
		, sendGoodOrder = layui.sendGoodOrder
		, mytable = layui.mytable;
		
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		myutil.getLastData();
		laydate.render({ elem:'#searchTime',range:'~'  })
		mytable.render({
			elem:'#sendTable',
			url: myutil.config.ctx+'/ledger/getSendGoods',
			toolbar: [
				'<span class="layui-btn layui-btn-sm" lay-event="addSendOrder">新增发货单</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="sendGood">发货</span>',
			].join(' '),
			curd:{
				btn:[4],
				otherBtn:function(obj){
					if(obj.event=='addSendOrder'){
						sendGoodOrder.add({});
					}else if(obj.event=='sendGood'){
						var check = layui.table.checkStatus('sendTable').data;
						if(check.length!=1)
							return myutil.emsg('请选择一条信息进行发货');
						var allInputNumber = 0; //计算总库存数量，发货数量不能超过该值
						var sendGoodWin = layer.open({
							type: 1,
							area:['50%','50%'],
							content:[
								'<div style="padding:10px 0;">',
									'<table>',
										'<tr>',
											'<td>发货数量：</td>',
											'<td><input type="text" class="layui-input" id="sendAllNumber"></td></tr>',
									'</table>',
									'<table id="chooseInputOrder" lay-filter="chooseInputOrder"></table>',
								'</div>',
							].join(' '),
							title:'剩余发货数量：'+check[0].surplusNumber,
							btn:['确定','取消'],
							btnAlign:'c',
							success:function(){
								$('#sendAllNumber').val(check[0].surplusNumber);
								mytable.renderNoPage({
									elem:'#chooseInputOrder',
									//totalRow:['sendNumber','number'],
									url: myutil.config.ctx+'/ledger/inventory/getPutStorageDetails?id='+check[0].id,
									cols:[[
										{ type:'checkbox',},
										{ title:'入库单编号',field:'serialNumber'},
										{ title:'数量',field:'number'},
										{ title:'发货数量',field:'sendNumber',edit:true,
											templet:'<span>{{ d.sendNumber || "" }}</span>'},
									]],
									done:function(r){
										layui.each(r.data,function(index,item){
											allInputNumber -= (-item.number);
										})
									}
								})
								table.on('edit(chooseInputOrder)',function(obj){
									var index = $(this).closest('tr').data('index');
									var trData = table.cache['chooseInputOrder'][index];
									var val = obj.value;
									if(obj.field==='sendNumber'){
										if(isNaN(val) || val<0 || val%1.0!=0.0){
											$(this).val(myutil.lastData);
											trData.sendNumber = myutil.lastData;
											myutil.emsg('请正确填写发货数量');
										}
									}
								})
							},
							yes:function(){
								var checkChild = layui.table.checkStatus('chooseInputOrder').data;
								if(checkChild.length<1)
									return myutil.emsg('请选择入库单');
								var inputNumber = $('#sendAllNumber').val() || 0;
								if(allInputNumber<inputNumber)
									return myutil.esmg('发货数量不能超过库存数量！');
								var childJson = [],allChildNumer = 0;
								for(var i=0,len=checkChild.length;i<len;i++){
									allChildNumer -= (-checkChild[i].sendNumber || 0);
									childJson.push({
										id: checkChild[i].id,
										number: checkChild[i].sendNumber || '',
									})
								}
								var msg = '';
								if(allChildNumer>0){
									if(inputNumber>0){
										if(inputNumber!=allChildNumer)
											msg = '填写的发货数量与总发货数量不同！请检查';
									}else{
										inputNumber = allChildNumer;
									}
								}else if(inputNumber==0){
									msg = '请填写发货数量';
								}
								if(msg)
									return myutil.emsg(msg);
								myutil.saveAjax({
									url: '/ledger/inventory/sendOutStorage',
									data:{
										id: check[0].id,
										sendNumber: inputNumber,
										putStorage: JSON.stringify(childJson),
									},
									success:function(){
										layer.close(sendGoodWin);
										table.reload('sendTable');
									}
								})
							}
						})
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
			     /*   { title:'剩余数量',   field:'surplusNumber',  width:'6%',	}, */
			       { title:'发货状态',field:'status',width:'8%',transData:{data:['库存充足','库存不足','无库存',]}, },
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