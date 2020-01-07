<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/formSelect/formSelects-v4.css" />
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>外发加工单</title>
	<style>
		.layui-form-pane .layui-item {
		    margin-top: 10px;
		}
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>合同日期:</td>
				<td><input type="text" name="orderTimeBegin" id="searchTime" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>合同:</td>
				<td style="width:500px;"><select name="orderId" disabled id="orderIdSelect" lay-search lay-filter="agreementSelect"></select></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
<div style="display:none;padding:5px;" id="tipWin">
	<div style="width:380px;">
		<table id="tipTable" lay-filter="tipTable"></table>
	</div>
</div>
</body>
<!-- 打印模板 -->
<script type="text/html" id="printTpl">
<div style="text-align:center;padding:30px;" id="printWin">
{{# for(var i in d){  }} 
	<table style="margin: auto; width: 100%;page-break-before:always;">
		<tr>
			<td style="text-align:center;border: 1px solid;width:30%;">{{ d[i].customer?d[i].customer.name:"" }}</td>
			<td style="text-align:center;border: 1px solid;width:10%;">&nbsp;</td>
			<td style="text-align:center;border: 1px solid;width:10%;">&nbsp;</td>
			<td style="text-align:center;border: 1px solid;width:20%;">&nbsp;</td>
			<td style="text-align:center;border: 1px solid;width:30%;">
				{{# var t = [];
					if(d[i].outGoingTime ){   
						t = d[i].outGoingTime.split(' ')[0].split('-');
				}}
					{{ t[0]+'年'+t[1]+'月'+t[2]+'日' }}
				{{# } }}
			</td>
		</tr>
		<tr>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
		</tr>
		<tr>
			<td style="border: 1px solid;text-align:center;">{{ d[i].outSourceNumber }}</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].processNumber?d[i].processNumber:"" }}</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].process }}</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].remark }}</td>
		</tr>
    </table>
	<br/>
	<table style="margin: auto; width: 100%;border: 1px solid;page-break-before:always;">
		<tr>
			<td style="border: 1px solid;text-align:center;width:30%;">{{ d[i].customer?d[i].customer.name:"" }}</td>
			<td style="border: 1px solid;text-align:center;width:10%;">&nbsp;</td>
			<td style="border: 1px solid;text-align:center;width:10%;">&nbsp;</td>
			<td style="border: 1px solid;text-align:center;width:20%;">&nbsp;</td>
			<td style="text-align:center;border: 1px solid;width:30%;">
				{{# var t = [];
					if(d[i].outGoingTime ){   
						t = d[i].outGoingTime.split(' ')[0].split('-');
				}}
					{{ t[0]+'年'+t[1]+'月'+t[2]+'日' }}
				{{# } }}
			</td>
		</tr>
		<tr>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
		</tr>
		<tr>
			<td style="border: 1px solid;text-align:center;">{{ d[i].outSourceNumber }}</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].processNumber?d[i].processNumber:"" }}</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].gramWeight }}</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].remark }}</td>
		</tr>
		<tr>
			<td style="border: 1px solid;"></td>
			<td style="border: 1px solid;text-align:center;">合计</td>
			<td style="border: 1px solid;text-align:center;">{{# if(d[i].gramWeight &&  d[i].processNumber){   }}
					{{ d[i].processNumber * parseFloat(d[i].gramWeight.split('g')[0]) /100+'kg' }}
				{{# } }}
			</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
		</tr>
    </table>
	<br/><hr class="layui-bg-red"><br/>
{{# }  }} 
</div>
</script>
<div id="toolbarTpl" style="display:none;">
	<shiro:hasAnyRoles name="superAdmin,productionProcessPlan">
		<span class="layui-btn layui-btn-sm" lay-event="edit" id="productionProcessPlanBtn">修改加工单</span>
		<span class="layui-btn layui-btn-sm" lay-event="print">打印</span>
		<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="audit">审核</span>
		<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="returnOrder">退货单</span>
	</shiro:hasAnyRoles>
	<shiro:hasAnyRoles name="superAdmin,productionBillAccounting">
		<span class="layui-btn layui-btn-sm layui-btn-primary" lay-event="addBill">生成账单</span>
	</shiro:hasAnyRoles>
</div>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
	outOrderModel : 'layui/myModules/sale/outOrderModel',
	returnOrder : 'layui/myModules/sale/returnOrder',
}).define(
	['mytable','laydate','outOrderModel','returnOrder'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer
		, laydate = layui.laydate
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, outOrderModel = layui.outOrderModel
		, returnOrder = layui.returnOrder
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		outOrderModel.init();
		var today = myutil.getSubDay(0,'yyyy-MM-dd');
		laydate.render({
			elem:'#searchTime',
			range:'~',
			value: today+' ~ '+today,
			done:function(val){
				if(val){
					val = val.split(' ~ ');
					getAgreementSelect({
						orderTimeBegin:val[0]+' 00:00:00',
						orderTimeEnd:val[1]+' 23:59:59',
					});
				}else{
					$('#orderIdSelect').attr('disabled','disabled');
					form.render();
				}
			}
		})
		getAgreementSelect({
			orderTimeBegin: today+' 00:00:00',
			orderTimeEnd: today+' 23:59:59',
		});
		function getAgreementSelect(data){
			myutil.getDataSync({
				url:'${ctx}/ledger/getOrder',
				data: data,
				success:function(d){
					var html = '<option value="">请选择</option>';
					for(var i in d){
						html += '<option value="'+d[i].id+'">'+d[i].orderNumber+'</option>';
					}
					$('#orderIdSelect').html(html);
					$('#orderIdSelect').removeAttr('disabled');
					form.render();
				}
			})
		}
		form.on('select(agreementSelect)',function(obj){
			if(obj.value!='')
				table.reload('tableData',{
					url:'${ctx}/ledger/orderOutSourcePage?outsource=1&orderId='+$('#orderIdSelect').val(),
				})
			else
				table.reload('tableData',{
					data:[],
					url:'',
				})
		})
		mytable.render({
			elem:'#tableData',
			data:[],
			autoUpdate:{
				deleUrl:'/ledger/deleteOrderOutSource',
			},
			curd:{
				btn: $('#productionProcessPlanBtn').length>0?[4]:[],
				otherBtn:function(obj){
					var check = layui.table.checkStatus('tableData').data;
					if(obj.event=='audit'){
						myutil.deleTableIds({
							url:'/ledger/auditOrderOutSource',
							table:'tableData',
							text:'请选择相关信息进行审核|是否确认审核？',
						})
					}else if(obj.event=='flag'){
						myutil.deleTableIds({
							url:'/ledger/invalidOrderOutSource',
							table:'tableData',
							text:'请选择相关信息进行作废|是否确认作废？',
						})
					}else if(obj.event=='print'){
						printWin();
					}else if(obj.event=="edit"){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据编辑！');
						outOrderModel.update({
							data:check[0],
							success:function(){
								table.reload('tableData');
							}
						});
					}else if(obj.event=='returnOrder'){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据编辑！');
						returnOrder.add({
							data: check[0],
						})
					}else if(obj.event=='addBill'){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据生成账单！');
						if(check[0].chargeOff)
							return myutil.emsg('该数据已生产账单，请勿重复添加！');
						addBill(check[0]);
					}
				},
			},
			ifNull:'---',
			toolbar: $('#toolbarTpl').html(),
			cellMinWidth:100,
			cols:[[
			       { type:'checkbox',},
			       { title:'编号',   field:'outSourceNumber',	width:210, },
			       { title:'工序',   field:'process', templet: getProcess(),	width:110, },
			       { title:'开单数',   field:'processNumber',	},
			       { title:'退货数',   field:'refundBillsNumber',	},
			       { title:'实际数',   field:'actualQuantity',	},
			       { title:'时间',   field:'openOrderTime', type:'date', width:110,},
			       { title:'跟单人',   field:'user_userName',	},
			       { title:'加工点',   field:'customer_name',	},
			       { title:'棉花类型',   field:'fill',	},
			       { title:'千克',   field:'kilogramWeight',	},
			       { title:'克重',   field:'gramWeight',	},
			       { title:'审核',   field:'audit',	transData:true, width:90,},
			       ]],
			done:function(){
				var tipWin;
				var first = 0;
				layui.each($('td[data-field="process"]'),function(index,item){
					var elem = $(item);
					var index = elem.closest('tr').data('index');
					var trData = table.cache['tableData'][index];
					$(item).on('mouseenter',function(){
						if(first==0){
							mytable.renderNoPage({
								elem:'#tipTable',
								ifNull:'0',
								totalRow:['allPrice'],
								parseData:function(ret){
									if(ret.code==0){
										layui.each(ret.data,function(index,item){
											item.allPrice = (item.price || 0)*item.number;
										})
									}
									return {  msg:ret.message,  code:ret.code , data:ret.data, };
								},
								url:'${ctx}/ledger/mixOutSoureRefund?id='+trData.id,
								ifNull:0,
								cols:[[
									{field:'name',title:'工序',},
									{field:'number',title:'数量',},
									{field:'price',title:'价格',},
									{field:'allPrice',title:'总价格',},
								]],
								done:function(){
									layer.close(tipWin);
									tipWin = layer.tips($('#tipWin').html(), elem,{
										time:0,
										area: '410px',
										//area:['400px','300px'],
										tips: [2, 'rgb(95, 184, 120)'],
										success:function(layero, layerIndex){
											
										}
									})
								}
							})
						}else{
							table.reload('tipTable',{
								url:'${ctx}/ledger/mixOutSoureRefund?id='+trData.id,
							})
						}
						first++;
					})
				})
				$(document).on('mousedown', function (event) { layer.close(tipWin); });
			}
		})
		function getProcess(){
			return function(data){
				var html = '';
				for(var i=0,len=data.outsourceTask.length;i<len;i++)
					html += '<span class="layui-badge layui-bg-green" style="margin:0 5px;">'
							+data.outsourceTask[i].name+'</span>';
				return html;
			}
		}
		function printWin(){
			var check = layui.table.checkStatus('tableData').data;
			if(check.length<1)
				return myutil.emsg('请选择相应的数据打印');
			var html = '';
			laytpl($('#printTpl').html()).render(check,function(h){
				html = h;
			})
			layer.open({
				type:1,
				title:'打印信息',
				content:html,
				area:['80%','80%'],
				btn:['打印','取消'],
				btnAlign:'c',
				shadeClose:true,
				yes: function(myDiv){    
					var printHtml = document.getElementById('printWin').innerHTML;
					var wind = window.open("",'newwindow', 'height=800, width=1500, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
					wind.document.body.innerHTML = printHtml;
					wind.print();
				},
			})
		}
		function addBill(data){
			var addBillWin = layer.open({
				type:1,
				title:'生成账单',
				offset:'50px',
				area:['50%','50%'],
				btn:['确认生成','取消'],
				shadeClose:true,
				content:[
					'<div style="padding:10px;">',
						'<table>',
							'<tr><td>账单时间：</td>',
							'<td><input type="text" class="layui-input" id="addBillTime"></td></tr>',
						'</table>',
						'<table id="addBillTable" lay-filter="addBillTable"></table>',
					'</div>'
				].join(' '),
				success:function(){
					laydate.render({
						elem:'#addBillTime',type:'datetime',value:new Date(),
					})
					mytable.renderNoPage({
						elem:'#addBillTable',
						autoUpdate:{
							saveUrl:'/ledger/updateProcessPrice',
							isReload:true,
						},
						parseData:function(ret){
							if(ret.code==0){
								layui.each(ret.data,function(index,item){
									item.allPrice = (item.price || 0)*item.number;
								})
							}
							return {  msg:ret.message,  code:ret.code , data:ret.data, };
						},
						ifNull:'0',
						totalRow:['allPrice'],
						verify:{
							price:['price'],
						},
						url:'${ctx}/ledger/mixOutSoureRefund?id='+data.id,
						cols:[[
							{field:'name',title:'工序',},
							{field:'number',title:'数量',},
							{field:'price',title:'价格',edit:true,},
							{field:'allPrice',title:'总价格',},
						]],
					})
				},
				yes:function(){
					var money = 0,verify=true;
					layui.each(table.cache['addBillTable'],function(index,item){
						if(!item.price && item.number!=0){
							verify = false;
							return;
						}
						money += item.allPrice;
					});
					if(!verify){
						myutil.emsg('工序价格不能为0！');
						return false;
					}
					var time = $('#addBillTime').val();
					if(!time){
						myutil.emsg('时间不能为空！');
						return false;
					}
					myutil.saveAjax({
						url:'/ledger/saveOutSoureBills',
						data:{
							expenseDate: time,
							id: data.id,
							money: money,
						},
						success:function(){
							layer.close(addBillWin);
							table.reload('tableData');
						}
					})
				}
			})
		}
	}//end define function
)//endedefine
</script>
</html>