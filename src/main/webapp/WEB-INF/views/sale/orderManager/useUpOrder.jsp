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
				<td><span class="layui-badge">提示：查看采购详情与库存详情移入是否出库和库存数量单元格中</span></td>
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
	    <label class="layui-form-label">面料价格：</label>
	    <div class="layui-input-block">
	      <input type="text" name="price" class="layui-input" id="addEditPrice" lay-verify="number">
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
	    <label class="layui-form-label">平方克重：</label>
	    <div class="layui-input-block">
	      <input type="text" name="squareGram" class="layui-input" id="areaG">
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
		laydate.render({
			elem: '#comeDate',
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
			url: '${ctx}/system/user/findUserList',
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
				url:'${ctx}/ledger/getOrder',
				data: data,
				success:function(d){
					var html = '<option value="">请选择</option>';
					for(var i in d){
						html += '<option value="'+d[i].id+'">'+d[i].bacthNumber+' ~ '+d[i].product.name+'</option>';
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
					url:'${ctx}/ledger/getOrderMaterial?&orderId='+obj.value,//audit=1
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
			ifNull:'---',
			toolbar:'<div><span class="layui-btn layui-btn-sm" lay-event="addBuy">新增采购单</span>'+
						'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="disperseOut">分散出库</span>'+
						'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="inventedOut">虚拟出库</span>'+
						'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="allProcurement">采购汇总</span>'+
					'</div>',
			cols:[[
			       { type:'checkbox',},
			       { title:'用料编号', field:'materiel_number', },
			       { title:'物料',   field:'materiel_name',   },
			       { title:'领取模式',   field:'receiveMode_name',	},
			       { title:'单位',   field:'unit_name',	},
			       { title:'用量',   field:'dosage',	},
			       { title:'库存状态',   field:'state', transData:{ data:['-','库存充足','无库存','有库存量不足'],text:'未知' },	},
			       { title:'库存数量',   field:'inventoryTotal',	},
			       { title:'是否出库',   field:'orderProcurements',	templet: '#procurementTpl', filter:true,},
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
						            			html= '<p>无库存信息</p>';
					            			else{
					            				
					            			
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
				layui.each($('td[data-field="orderProcurements"]'),function(ind,item){
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
						            		   html = '<p>无采购信息</p>';
						            	   for(var i in d){
						            		   if(i!=0)
						            			   html+='<br>';
						            		   html+=['<p>下单日期：'+d[i].placeOrderTime+'</p>',
						            		          '<p>采购数量：'+d[i].placeOrderNumber+'</p>',
						            		          '<p>预计价格：'+d[i].price+'</p>',
						            		          '<p>订购人：'+d[i].user.userName+'</p>',
							            		      '<p>采购编号：'+d[i].orderProcurementNumber+'</p>',
							            		      '<p>供应商：'+d[i].customer.name+'</p>',
							            		      '<p>预计到货：'+d[i].expectArrivalTime+'</p>',
							            		      '</p>',
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
				})
				table.on('toolbar(tableData)',function(obj){
					var checked = layui.table.checkStatus('tableData').data;
					if(obj.event=='allProcurement'){
						var orderId = $('#orderIdSelect').val();
						if(!orderId)
							return myutil.emsg('请选择合同');
						var allWin = layer.open({
							title:'采购汇总',
							type:1,
							shadeClose:true,
							area:['90%','90%'],
							content:'<table id="allTable" lay-filter="allTable"></table>',
							success:function(){
								mytable.render({
									elem: '#allTable',
									colsWidth:[0,13,0,6,6,6,8,13],
									url: '${ctx}/ledger/getOrderProcurement?orderId='+orderId,
									toolbar:['<span class="layui-btn layui-btn-sm" lay-event="updateProcurement">修改采购单</span>'].join(''),
									curd:{
										btn:[4],
										otherBtn:function(obj){
											var checked = layui.table.checkStatus('allTable').data;
											if(checked.length!=1)
												return myutil.emsg('只能修改一条数据');
											if(obj.event=='updateProcurement'){
												var trData = table.cache['tableData'][$(obj.target).data('index')];
												addEditBuy('edit',checked[0]);
											}
										}
									},
									autoUpdate:{
										deleUrl:'/ledger/deleteOrderProcurement',
									},
									cols:[[
										   { type:'checkbox' },
									       { title:'下单日期', field:'placeOrderTime', },
									       { title:'采购编号', field:'orderProcurementNumber', },
									       { title:'采购数量', field:'placeOrderNumber', },
									       { title:'预计价格', field:'price', },
									       { title:'订购人', field:'user_userName', },
									       { title:'供应商', field:'customer_name', },
									       { title:'预计到货', field:'expectArrivalTime',},
									       ]]
								})
							}
						})
					}else if(obj.event=="disperseOut"){
						myutil.deleTableIds({
							table:'tableData',
							text:'请选择相关信息|是否确认分散出库?',
							url:'',
						});
					}else if(obj.event=='addBuy'){
						if(checked.length!=1)
							return myutil.emsg('只能选择一条信息增加');
						if(checked[0].orderProcurements.length>0)
							return myutil.emsg('该面料已经采购、请勿重复添加');
						addEditBuy('add',checked[0]);
					}else if(obj.event=='inventedOut'){
						myutil.deleTableIds({
							table:'tableData',
							text:'请选择相关信息|是否确认虚拟出库?',
							url:'',
						});
					}
				})
			}
		})
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
						$('#addEditPrice').val(d.price);
						$('#areaG').val(d.squareGram);
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