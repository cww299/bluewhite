<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>耗料订单</title>
	<style>
		.tipProcurement{
		}
		.fenge{
			border-bottom: 1px dashed black;
	    	margin: 8px 0;
		}
		.layui-layer-tips{
			width: auto !important;
		}
		.tipProcurement>p{
			font-size: 14px;
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
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" id="warm" class="layui-btn">库存预警<span class="layui-badge" id="warmNumber">0</span></button></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><span class="layui-badge">提示：查看库存详情移入库存数量单元格中</span></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
<!-- 新增弹窗 -->
<div style="display:none;padding:10px;" id="addBuyWin">
	<form class="layui-form layui-form-pane" action="">
	  <input type="hidden" name="orderMaterialId" id="orderMaterialId">
	  <input type="hidden" name="id" id="addEditId">
	  <div class="layui-form-item" pane>
	    <label class="layui-form-label">下单日期：</label>
	    <div class="layui-input-block">
	      <input type="text" name="placeOrderTime" class="layui-input" id="placeOrderTime" lay-verify="required">
	    </div>
	  </div>
	  <div class="layui-form-item" pane>
	    <label class="layui-form-label">供应商：</label>
	    <div class="layui-input-block">
	      <select name="customerId" lay-search id="supplierSelect" lay-filter="supplierSelect" lay-verify="required">
	      		<option value="">请选择</option></select>
	    </div>
	  </div>
	  <div class="layui-form-item" pane>
	    <label class="layui-form-label">订货数量：</label>
	    <div class="layui-input-block">
	      <input type="text" name="placeOrderNumber" id="placeOrderNumber" class="layui-input" lay-verify="number">
	    </div>
	  </div>
	  <div class="layui-form-item" pane>
	    <label class="layui-form-label">约定价格：</label>
	    <div class="layui-input-block">
	      <input type="text" name="price" class="layui-input" id="addEditPrice">
	    </div>
	  </div>
	  <div class="layui-form-item" pane>
	    <label class="layui-form-label">订料人：</label>
	    <div class="layui-input-block">
	      <select name="userId" lay-search id="userIdSelect" lay-verify="required">
	      		<option value="">请选择</option></select>
	    </div>
	  </div>
	  <div class="layui-form-item" pane>
	    <label class="layui-form-label">约定克重：</label>
	    <div class="layui-input-block">
	      <input type="text" name="conventionSquareGram" class="layui-input" id="areaG">
	    </div>
	  </div>
	  <div class="layui-form-item" pane>
	    <label class="layui-form-label">生成编号：</label>
	    <div class="layui-input-block">
	      <input type="text" name="newCode" class="layui-input" id="autoNumber" disabled lay-verify="required">
	    </div>
	  </div>
	  <div class="layui-form-item" pane>
	    <label class="layui-form-label">到库日期：</label>
	    <div class="layui-input-block">
	      <input type="text" name="expectArrivalTime" class="layui-input" id="comeDate" lay-verify="required">
	    </div>
	  </div>
	  <div class="layui-form-item" pane>
	    <label class="layui-form-label">付款日期：</label>
	    <div class="layui-input-block">
	      <input type="text" name="expectPaymentTime" class="layui-input" id="exceptDate" lay-verify="required">
	    </div>
	  </div>
	  <span style="display:none;" lay-filter="sureAdd" id="sureAdd" lay-submit>确定</span>
	  <button style="display:none;" type="reset" id="restBtn">重置</button>
	</form>
</div>
</body>
<script type="text/html" id="procurementTpl">
 {{# var color = 'blue',text="否"; 
     if(d.orderProcurements.length>0){
        color=''; text = '是';
     }
 }}
 <span class="layui-badge layui-bg-{{color}}">{{ text }}</span>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laydate','layer'],
	function(){
		var $ = layui.jquery
		, form = layui.form			 		
		, table = layui.table 
		, layer = layui.layer
		, myutil = layui.myutil
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.config.msgOffset = '120px';
		myutil.clickTr();
		
		$('#warm').click(function(){
			getWarm('click');
		})
		getWarm();
		function getWarm(click){
			myutil.getData({
				url:'${ctx}/ledger/warningOrderProcurement',
				success:function(d){
					if(d.length>0){
						$('#warm').find('span').html(d.length);
						layer.open({
							type:1,
							title:'库存预警',
							area:['80%','80%'],
							shadeClose:true,
							content:'<table id="warmTable" lay-filter="warmTable"></table>',
							success:function(){
								mytable.renderNoPage({
									elem:'#warmTable',
									data: d,
									curd:{ 	btn:[], 
											otherBtn:function(obj){
												if(obj.event=='onekeyUpdate'){
													myutil.deleTableIds({
														url:'/ledger/fixOrderProcurement',
														table:'warmTable',
														text:'请选择更新数据|是否确认更新？',
														success:function(){
															myutil.getData({
																url:'${ctx}/ledger/warningOrderProcurement',
																success:function(d){
																	$('#warm').find('span').html(d.length);
																	table.reload('warmTable',{
																		data:d,
																	})
																}
															})
														},
													})
												}
											}
									},
									colsWidth:[0,0,7,13,7,13,7],
									toolbar:'<span class="layui-btn layui-btn-sm" lay-event="onekeyUpdate">一键更新订单数量</span>',
									cols:[[
									       { type:'checkbox' },
									       { title:'采购单编号', field:'orderProcurementNumber' },
									       { title:'采购数量', field:'placeOrderNumber', },
									       { title:'采购时间', field:'placeOrderTime', },
									       { title:'入库数量', field:'arrivalNumber',style:'color:red;'},
									       { title:'入库时间', field:'arrivalTime' },
									       { title:'入库人', field:'userStorage_userName' },
									       ]]
								})
							} 
							
						})
					}else if(click){
						myutil.emsg('无库存预警！');
					}
				}
			})
		}
		laydate.render({
			elem: '#comeDate',
			type:'datetime',
		})
		laydate.render({
			elem: '#exceptDate',
			type:'datetime',
		})
		laydate.render({
			elem: '#placeOrderTime',
			type:'datetime',
		})
		var allCustom = [],allUser = [];
		myutil.getData({
			url: '${ctx}/ledger/getCustomer?type=4',
			success:function(d){
				var html = '';
				allCustom = d;
				for(var i in d){
					html+='<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
				$('#supplierSelect').html(html);
				form.render();
			}
		})
		myutil.getData({
			url: '${ctx}/system/user/findUserList?orgNameIds=20',
			success:function(d){
				var html = '';
				allUser = d;
				for(var i in d){
					html+='<option value="'+d[i].id+'">'+d[i].userName+'</option>';
				}
				$('#userIdSelect').html(html);
				form.render();
			}
		})
		var today = myutil.getSubDay(0,'yyyy-MM-dd');
		laydate.render({
			elem:'#searchTime',
			range:'~',
			value:today+' ~ '+today,
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
				url:'${ctx}/ledger/getOrder?consumption=1',
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
					url:'${ctx}/ledger/getOrderMaterial?audit=1&orderId='+obj.value,//
				})
			else
				table.reload('tableData',{
					data:[],
					url:'',
				})
		})
		var tipProcurement = '', tipInventory = ''; 
		$(document).on('mousedown', '', function (event) { //关闭提示窗
			if($('.layui-layer-tips').length>0 && $(event.target).closest('.tipProcurement').length==0){
				layer.close(tipProcurement);
				layer.close(tipInventory);
			}
		});
		mytable.render({
			elem:'#tableData',
			data:[],
			curd:{
				btn:[],	
				otherBtn: getOther(),
			},
			ifNull:'---',
			toolbar:'<span class="layui-btn layui-btn-sm" lay-event="addBuy">生成采购单</span>'+
					'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="allProcurement">采购单</span>'+
					'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="inventedOut">生成出库单</span>'+
					'<span class="layui-btn layui-btn-sm" lay-event="audit">审核出库单</span>'+
					'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deletes">清除出库单</span>'+
					'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="outOrder">出库单</span>',
			colsWidth:[0,10,0,10,10,8,8,8,8,8],
			parseData:function(ret){
				if(ret.code==0){
					for(var i in ret.data.rows){
						if(ret.data.rows[i].outbound)
							ret.data.rows[i].state = 0;
					}
					return {  msg:ret.message,  code:ret.code , data:ret.data.rows, count:ret.data.total }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			},
			limit:15,
			limits:[10,15,30,50,100],
			cols:[[
			       { type:'checkbox',},
			       { title:'用料编号', field:'materiel_number', },
			       { title:'物料',   field:'materiel_name',   },
			       { title:'领取模式',   field:'receiveMode_name',	},
			       { title:'单位',   field:'unit_name',	},
			       { title:'用量',   field:'dosage',	},
			       { title:'库存状态',   field:'state', transData:{ data:['已出库','库存充足','无库存','有库存量不足'],text:'未知' },	},
			       { title:'库存数量',   field:'inventoryTotal',	},
			       { title:'是否审核出库', field:'outAudit', transData:{ data:['未审核','审核'],}},
			       ]],
			done:function(){
				layui.each($('td[data-field="inventoryTotal"]'),function(index,item){
					$(item).on('mouseover',function(){
						var elem = $(item);
						var index = elem.closest('tr').data('index');
						var trData = table.cache['tableData'][index];
						var html = [
						            '<div class="tipProcurement">',
						            	(function(){
						            		var html = '';
						            		var d = trData.materiel.orderProcurements;
						            		if(d.length==0)
						            			html= '<p>无库存详情</p>';
					            			else{
					            				 for(var i in d){
								            		   if(i!=0)
								            			   html+='<p class="fenge"></p>';
								            		   html+=['<p>下单日期：'+d[i].placeOrderTime+'</p>',
									            		      '<p>采购编号：'+d[i].orderProcurementNumber+'</p>',
								            		          '<p>剩余数量：'+d[i].residueNumber+'</p>',
								            		          '<p>预计价格：'+d[i].price+'</p>',
								            		          '<p>订购人：'+d[i].user.userName+'</p>',
									            		      '<p>供应商：'+d[i].customer.name+'</p>',
								            		    ].join('');
								            	   }
					            			}
						            		return html;
						            	})(),
						            '</div>',
						            ].join('');
						layer.close(tipProcurement)
						tipInventory = layer.tips(html, elem,{
							time:0,
							tips: [4, 'rgb(95, 184, 120)'],
						})
					})
				})
				/* layui.each($('td[data-field="orderProcurements"]'),function(ind,item){
					$(item).on('mouseover',function(){
						var elem = $(item);
						var index = elem.closest('tr').data('index');
						var trData = table.cache['tableData'][index];
						var html = [
						            '<div class="tipProcurement">',
						               (function(){
						            	   var d = trData.orderProcurements;
						            	   var html = '';
						            	   if(d.length==0)
						            		   html = '<p>无出库详情</p>';
						            	   for(var i in d){
						            		   if(i!=0)
						            			   html+='<p class="fenge"></p>';
						            		   html+=['<p>下单日期：'+d[i].placeOrderTime+'</p>',
							            		      '<p>采购编号：'+d[i].orderProcurementNumber+'</p>',
						            		          '<p>出库数量：'+d[i].placeOrderNumber+'</p>',
						            		          '<p>预计价格：'+d[i].price+'</p>',
						            		          '<p>订购人：'+d[i].user.userName+'</p>',
							            		      '<p>供应商：'+d[i].customer.name+'</p>',
							            		      '<p>预计到货：'+d[i].expectArrivalTime+'</p>',
						            		    ].join('');
						            	   }
						            	   return html;
						               })(),
						            '<div>',
						            ].join(' ');
						layer.close(tipInventory)
						tipProcurement = layer.tips(html, elem,{
							time:0,
							tips: [4, 'rgb(95, 184, 120)'],
						})
					})
				}) */
			}
		})
		function getOther(){
			return function(obj){
				var checked = layui.table.checkStatus('tableData').data;
				if(obj.event=='outOrder'){
					var orderId = $('#orderIdSelect').val();
					if(!orderId)
						return myutil.emsg('请选择合同');
					var allWin = layer.open({
						title:'出库单',
						type:1,
						shadeClose:true,
						area:['90%','90%'],
						content:'<span class="layui-badge">提示：操作时请确认是否有下一页，请勿遗漏</span>'+
								'<table id="outTable" lay-filter="outTable"></table>',
						success:function(){
							mytable.render({
								elem: '#outTable',
								url: '${ctx}/ledger/getScatteredOutbound?orderId='+orderId,
								ifNull:'',
								colsWidth:[0,15,15,0,6,6],
								cols:[[
									   { type:'checkbox' },
								       { title:'出库日期',   field:'auditTime',	type:'dateTime',},
								       { title:'分散出库编号',   field:'outboundNumber',	},
								       { title:'采购单编号',   field:'orderProcurement_orderProcurementNumber',  },
								       { title:'领取用量',   field:'dosage',	},
								       { title:'是否审核',   field:'audit', transData:{data:['否','是'],}	},
								       ]]
							})
						},
						end:function(){
							table.reload('tableData');
						}
					})
				}else if(obj.event=='allProcurement'){
					var orderId = $('#orderIdSelect').val();
					if(!orderId)
						return myutil.emsg('请选择合同');
					var allWin = layer.open({
						title:'采购单',
						type:1,
						shadeClose:true,
						area:['90%','90%'],
						content:'<span class="layui-badge">提示：操作时请确认是否有下一页，请勿遗漏</span>'+
								'<table id="allTable" lay-filter="allTable"></table>',
						success:function(){
							mytable.render({
								elem: '#allTable',
								url: '${ctx}/ledger/getOrderProcurement?orderId='+orderId,
								toolbar:['<span class="layui-btn layui-btn-sm" lay-event="updateProcurement">修改采购单</span>',
										 '<span class="layui-btn layui-btn-sm" lay-event="auditProcurement">审核</span>'].join(''),
								curd:{
									btn:[4],
									otherBtn:function(obj){
										if(obj.event=="updateProcurement"){
											var checked = layui.table.checkStatus('allTable').data;
											if(checked.length!=1)
												return myutil.emsg('只能修改一条数据');
											if(obj.event=='updateProcurement'){
												var trData = table.cache['tableData'][$(obj.target).data('index')];
												addEditBuy('edit',checked[0]);
											}
										}else if(obj.event=="auditProcurement"){
											myutil.deleTableIds({
												table:'allTable',
												text:'请选择相关信息|是否确认审核?',
												url:'/ledger/auditOrderProcurement',
											});
										}
									}
								},
								autoUpdate:{
									deleUrl:'/ledger/deleteOrderProcurement',
								},
								colsWidth:[0,13,0,6,6,6,8,13,13,8],
								cols:[[
									   { type:'checkbox' },
								       { title:'下单日期', field:'placeOrderTime', },
								       { title:'采购编号', field:'orderProcurementNumber', },
								       { title:'采购数量', field:'placeOrderNumber', },
								       { title:'预计价格', field:'price', },
								       { title:'订购人', field:'user_userName', },
								       { title:'供应商', field:'customer_name', },
								       { title:'预计到货', field:'expectArrivalTime',},
								       { title:'付款日期', field:'expectPaymentTime', },
								       { title:'审核', field:'audit',transData:{data:['审核','未审核'],}},
								       ]]
							})
						},
						end:function(){
							table.reload('tableData');
						}
					})
				}else if(obj.event=='audit'){
					var c = table.checkStatus('tableData').data;
					if(c.length<1)
						return myutil.emsg('请选择审核的信息');
					var ids = [];
					for(var i in c)
						ids.push(c[i].id);
					var auditWin = layer.open({
						type:1,
						area:['30%','20'],
						btn:['确定','取消'],
						content:['<div style="padding:20px;">',
						         	'<span class="layui-badge">提示：如果填写时间则为统一审核时间，已填写时间将会被覆盖</span>',
						         	'<input type="text" id="auditTime" class="layui-input">',
						         '</div>',
						         ].join(' '),
						success:function(){
							laydate.render({
								elem:'#auditTime',
								type:'datetime',
								value: myutil.getSubDay(0,'yyyy-MM-dd hh:mm:ss'),
							})	
						},
						yes:function(){
							myutil.deleteAjax({
								url:'/ledger/auditScatteredOutbound?time='+$('#auditTime').val(),
								ids: ids.join(','),
								success:function(){
									layer.close(auditWin);
									table.reload('outTable');
								}
							})
						}
					})
				}else if(obj.event=='addBuy'){
					if(checked.length!=1)
						return myutil.emsg('只能选择一条信息增加');
					if(checked[0].state==1)
						return myutil.emsg('库存量充足、无需采购');
					addEditBuy('add',checked[0]);
				}else if(obj.event=='inventedOut'){
					myutil.deleTableIds({
						table:'tableData',
						text:'请选择相关信息|是否确认分散出库?',
						url:'/ledger/saveScatteredOutbound',
					});
				}else if(obj.event=='deletes'){
					myutil.deleTableIds({
						table:'tableData',
						text:'请选择相关信息|是否确认清除出库单?',
						url:'/ledger/deleteScatteredOutbound',
					});
				}
			}
		}
		function addEditBuy(addOrEdit,data){	//新增、修改采购单
			var title = '新增采购单';
			if(addOrEdit=='edit'){
				title = '修改采购单';
			}
			var addNewOrder = layer.open({
				type:1,
				title:title,
				btn:['确定','取消'],
				offset:'50px',
				btnAlign:'c',
				content:$('#addBuyWin'),
				area:['40%','70%'],
				success:function(){
					var srr = '';
					if(addOrEdit=='add'){
						var number = data.materiel.number.replace(/[^0-9]/ig,"");	//面类、辅料编号
						var type = data.materiel.number.replace(/\d/ig,"");		//面料、辅料类型
						str = type+'- “'+allCustom[0].name+'“ '+number+' ';	//拼接
						$('#orderMaterialId').val(data.id);
					}else if(addOrEdit=='edit'){
						var d = data;
						var t = d.orderProcurementNumber.split('/');
						str = t[t.length-1];
						//设置下拉框、输入框数据
						$('#addEditId').val(d.id);
						$('#supplierSelect').val(d.customer.id);
						$('#userIdSelect').val(d.user.id);
						$('#placeOrderTime').val(d.placeOrderTime);
						$('#placeOrderNumber').val(d.placeOrderNumber);
						$('#addEditPrice').val(d.conventionPrice);
						$('#areaG').val(d.conventionSquareGram);
						$('#comeDate').val(d.expectArrivalTime);
						$('#orderMaterialId').val('');
					}
					$('#autoNumber').val(str);
					form.on('select(supplierSelect)',function(obj){
						var n = $(obj.elem).find('option[value="'+obj.value+'"]').html();
						var old = $('#autoNumber').val().split('“');
						var str = old[0]+"“"+n+"“"+old[2];
						$('#autoNumber').val(str);
					})
					$('#areaG').blur(function(obj){
						var val = $(obj.target).val(),str='';
						var text = $('#autoNumber').val().split('{');
						if(val!='')
							str = text[0]+'{ 平方克重:'+val+'克 }';
						else
							str = text[0];
						$('#autoNumber').val(str);
					})
					form.on('submit(sureAdd)',function(obj){
						myutil.saveAjax({
							url:'/ledger/confirmOrderProcurement',
							data: obj.field,
							success:function(){
								table.reload('tableData');
								layer.close(addNewOrder);
								if(addOrEdit=='edit'){
									table.reload('allTable');
								}
							}
						})
					})
					form.render();
				},
				yes:function(){
					$('#sureAdd').click();	//点击表单提交按钮
				},
				end:function(){
					$('#restBtn').click(); //重置弹窗内容
					$('#addEditId').val('');
					table.reload('tableData');
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