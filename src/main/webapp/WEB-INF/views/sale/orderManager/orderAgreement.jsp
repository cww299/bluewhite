<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>下单合同</title>
<style>
.layui-form-pane .layui-item{
	margin-top:10px;
}
</style>
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
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableAgreement" lay-filter="tableAgreement"></table>
	</div>
</div>

</body>

<!-- 新增弹窗 -->
<div style="padding:10px;display:none;" id="addWin">
	<table class="layui-form">
		<tr>
			<td>商品选择：</td>
			<td><input readonly placeholder="点击选择商品" class="layui-input" id="chooseProductInput">
				<input type="hidden" name="productId" id="productIdHidden"></td>
			<td>&nbsp;&nbsp;</td>
			<td>订单类型：</td>
			<td style="width:100px;"><select id="orderTypeSelect"></select></td>
			<td>&nbsp;&nbsp;</td>
			<td>订单时间：</td>
			<td><input type="text" id="orderTime" class="layui-input" ></td>
			<td>&nbsp;&nbsp;</td>
			<td>批次号：</td>
			<td><input type="text" id="bacthNumber" class="layui-input" ></td>
			<td>&nbsp;&nbsp;</td>
			<td>订单备注：</td>
			<td><input type="text" id="orderRemak" class="layui-input" ></td>
			<td>&nbsp;&nbsp;</td>
			<td>订单数量：</td>
			<td style="width:80px;"><input type="text" id="orderNumber" value="0" readonly class="layui-input" ></td>
		</tr>
	</table>
	<table id="addTable" lay-filter="addTable"></table>
</div>
<!-- 商品选择弹窗 -->
<div style="padding:10px;display:none;" id="chooseProductWinNew">
	<table class="layui-form">
		<tr>
			<td>商品名称：</td>
			<td><input class="layui-input" type="text" name="name"></td>
			<td>&nbsp;&nbsp;</td>
			<td>商品编号：</td>
			<td><input class="layui-input" type="text" name="number"></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProductBtn" >搜索</span></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-badge">双击选择</span></td>
		</tr>
	</table>
	<table id="chooseProductTable" lay-filter="chooseProductTable"></table>
</div>
<!-- 客户选择弹窗 -->
<div style="padding:10px;display:none;" id="chooseProductWin">
	<table class="layui-form">
		<tr>
			<td>客户名称：</td>
			<td><input class="layui-input" type="text" name="name"></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" id="searchBtn">搜索</span></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-btn layui-btn-sm" id="sureChoosed">确定</span></td>
			<td><span class="layui-badge" id="tipDouble" style="display:none;">双击选择</span></td>
		</tr>
	</table>
	<table id="choosedTable" lay-filter="choosedTable"></table>
</div>
<!-- 修改模板 -->
<script type="text/html" id="editTpl">
<div class="layui-form layui-form-pane" style="padding:20px;">
	<input type="hidden" name="id" value="{{d.id}}">
	<input type="hidden" name="productId" value="{{d.product.id}}" id="editProductId" >
	<input type="hidden" name="orderDate" value="{{d.orderDate}}">
	<div class="layui-item" pane>
		<label class="layui-form-label">客户</label>
		<div class="layui-input-block">
			<select id="editCustomSelect" name="customerId" lay-search></select>
		</div>
	</div>
	<div class="layui-item" pane>
		<label class="layui-form-label">批次号</label>
		<div class="layui-input-block">
			<input class="layui-input" name="bacthNumber" value="{{d.bacthNumber}}" lay-verify="required">
		</div>
	</div>
	<div class="layui-item" pane>
		<label class="layui-form-label">产品编号</label>
		<div class="layui-input-block">
			<input class="layui-input" value="{{d.product.number}}" readonly id="editProductNumber">
		</div>
	</div>
	<div class="layui-item" pane>
		<label class="layui-form-label">产品名称</label>
		<div class="layui-input-block">
			<input class="layui-input" value="{{d.product.name}}" readonly id="editProductName">
		</div>
	</div>
	<div class="layui-item" pane>
		<label class="layui-form-label">数量</label>
		<div class="layui-input-block">
			<input class="layui-input" name="number" value="{{d.number}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item" pane>
		<label class="layui-form-label">价格</label>
		<div class="layui-input-block">
			<input class="layui-input" name="price" value="{{d.price}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item" pane>
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block">
			<input class="layui-input" name="remark" value="{{d.remark}}">
		</div>
	</div>
	<p style="display:none;"><button lay-submit lay-filter="sureEdit" id="sureEdit">确定</button></p>
</div>
</script>
<!-- 表格工具栏模板 -->
<script type="text/html" id="agreementToolbar">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增合同</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除合同</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改合同</span>
	<span lay-event="productUseup"  class="layui-btn layui-btn-sm layui-btn-normal" >生成耗料订单</span>
	<span lay-event="lookoverUseup"  class="layui-btn layui-btn-sm" >查看耗料订单</span>
	<span lay-event="outOrder"  class="layui-btn layui-btn-sm layui-btn-warm" >生成外发单</span>
</div>
</script>
<!-- 表格工具栏模板 -->
<script type="text/html" id="addTableToolbar">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm">选择客户</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除客户</span>
	<span lay-event="sureAdd" class="layui-btn layui-btn-sm">确定新增</span>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
	outOrderModel : 'layui/myModules/sale/outOrderModel' ,
	mytable : 'layui/myModules/mytable',
	orderAgreementTpl:'layui/tpl/sale/orderAgreementTpl',
}).define(
	['mytable','laydate','outOrderModel','orderAgreementTpl'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, orderAgreementTpl = layui.orderAgreementTpl
		, myutil = layui.myutil
		, outOrderModel = layui.outOrderModel
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		myutil.config.msgOffset = '250px';
		outOrderModel.init();
		myutil.keyDownEntry(function(){   //监听回车事件
			$('#searchBtn').click();
		})
		var allUserSelectHtml = '<option value="">请选择</option>';
		myutil.getData({
			url:"${ctx}/basedata/list?type=orderNumberType",
			success:function(d){
				var html = '';
				for(var i =0,len=d.length;i<len;i++){
					html += '<option value="'+d[i].id+'">'+d[i].name+'</option>'
				}
				$('#orderTypeSelect').html(html);
				form.render();
			}
		})
		myutil.getData({
			url:'${ctx}/system/user/findUserList',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++)
					allUserSelectHtml+= '<option value="'+d[i].id+'">'+d[i].userName+'</option>';
			}
		})
		laydate.render({ elem:'#searchTime', range:'~' })
		table.render({
			elem:'#tableAgreement',
			url:'${ctx}/ledger/orderPage',
			toolbar:'#agreementToolbar',
			page:true,
			size:'lg',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       { align:'center', type:'checkbox',},
			       { align:'center', title:'客户名称',	field:'customerId',	 width:'10%', templet:'<span>{{ d.customer?d.customer.name:""}}</span>'},
			       { align:'center', title:'批次号',   	field:'bacthNumber', width:'12%'  	},
			       { align:'center', title:'下单时间',   	field:'orderDate', width:'8%',  templet:'<span>{{ d.orderDate?d.orderDate.split(" ")[0]:""}}</span>'  	},
			       { align:'center', title:'产品编号',	field:'productNumber', width:'8%', 	templet:'<span>{{ d.product?d.product.number:""}}</span>'	},
			       { align:'center', title:'产品名称',	field:'productName',	templet:'<span>{{ d.product?d.product.name:""}}</span>'},
			       { align:'center', title:'数量',   field:'number',	 width:'4%'},
			       { align:'center', title:'剩余数量',   field:'surplusNumber',	 width:'6%'},
			       { align:'center', title:'价格',   field:'price',  width:'6%'	 },
			       { align:'center', title:'备注',   field:'remark',	 },
			       { align:'center', title:'生成耗料单',   field:'',  width:'7%', templet:getTpl(),	 },
			       { align:'center', title:'备料充足',   field:'prepareEnough',  width:'7%', templet:getTpl2(),	 },
			       ]]
		})
		function getTpl2(){
			return function(d){
				if(d.prepareEnough)
					return '<span class="layui-badge layui-bg-green">是</span>';
				return '<span class="layui-badge">否</span>';
			}
		}
		function getTpl(){
			return function(d){
				if(d.orderMaterials && d.orderMaterials.length>0)
					return '<span class="layui-badge layui-bg-">是</span>';
				return '<span class="layui-badge layui-bg-blue">否</span>';
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
			case 'add':		add();		break;
			case 'update':	edit(); 	break;
			case 'delete':	deletes();			break;
			case 'lookoverUseup': lookoverUseup(); break;
			case 'productUseup': productUseup(); break;
			case 'outOrder': outOrder(); break;
			} 
		})
		function outOrder(){
			var checked = layui.table.checkStatus('tableAgreement').data;
			if(checked.length!=1)
				return myutil.emsg('只能选择一条信息进行生成');
			outOrderModel.add({
				data:{ 
					orderId:checked[0].id, 
					outSourceNumber: checked[0].bacthNumber+checked[0].product.name,
				}
			})
		}
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
						url:'${ctx}/ledger/getOrderMaterial?orderId='+checked[0].id,
						toolbar:'<span class="layui-btn layui-btn-sm" lay-event="onekey">一键审核</span>',
						size:'lg',
						limit:15,
						limits:[10,15,20,50,],
						colsWidth:[0,0,10,10,20,10],
						curd:{
							btn:[4],
							otherBtn:function(obj){
								if(obj.event=="onekey"){
									myutil.deleTableIds({
										url:'/ledger/auditOrderMaterial',
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
						       { title:'物料名',   field:'materiel_name', },
						       { title:'单位',   field:'unit_name',  },
						       { title:'领取用量',   field:'dosage', 	},
						       { title:'领取模式',   field:'receiveMode_id',	type:'select', select:{data:mode}, },
						       { title:'审核状态', field:'audit', transData:{ data:['未审核','审核'] }},
						       ]],
					})
				},
			})
		}
		function productUseup(){
			myutil.deleTableIds({
				url:'/ledger/confirmOrderMaterial',
				table:'tableAgreement',
				text:'请选择相关信息|是否确认生成耗料表',
			})
		}
		
		function add(){
			var productList = [];	//记录复选框选中的值，用于回显复选框选中
			var defaultTime = '', defaultRemark = '';
			var addWin = layer.open({
				title: '新增合同',
				type:1,
				area:['90%','100%'],
				content: $('#addWin'),
				success:function(){
					laydate.render({
						elem:'#orderTime',
						type:'datetime',
					})
					$('#chooseProductInput').unbind().on('click',function(){
						var chooseProductWinNew = layer.open({
							type:1,
							title:'商品选择',
							area:['50%','70%'],
							content: $('#chooseProductWinNew'),
							shadeClose:true,
							success:function(){
								mytable.render({
									elem:'#chooseProductTable',
									url:'${ctx}/productPages',
									cols:[[
										{title:'产品编号',field:'number',},
										{title:'产品名称',field:'name',},
									]],
									done:function(){
										
									}
								})
								form.on('submit(searchProductBtn)',function(obj){
									table.reload('chooseProductTable',{
										where:obj.field,
										page:{
											curr:1,
										}
									})
								})
								table.on('row(chooseProductTable)', function(obj){
									var data = obj.data;
									$('#productIdHidden').val(data.id);
									$('#chooseProductInput').val(data.name);
								  	layer.close(chooseProductWinNew);
								});
							}
						})
					})
				}
			})
			$('#defaultRemark').on('blur',function(obj){
				var val = $(this).val();
				if(val!=defaultRemark){
					defaultRemark = val;
					layui.each(table.cache['addTable'],function(index,item){
						item['remark'] = defaultRemark;
					})
					table.reload('addTable');
				}
			})
			getAllCustom('customSelect',0);
			laydate.render({
				elem: '#addDefaultTime',
				done: function(d){
					defaultTime = d;
					layui.each(table.cache['addTable'],function(index,item){
						item.orderDateTime = defaultTime;
					})
					table.reload('addTable');
				}
			})
			mytable.render({
				elem: '#addTable',
				toolbar: '#addTableToolbar',
				page: true,
				size:'lg',
				data:[],
				cols:[[
						{ type:'checkbox',},
						{ title:'客户',   	field:'name', 	},
						{ title:'下单人',    field:'',    templet: getUserSelect(),},
						{ title:'数量',   	field:'number',  	edit:true, },
						{ title:'备注',   	field:'remark',	edit:true, },
				       ]],
				done:function(){
					layui.each($('.userSelect'),function(index,item){
						var cache = table.cache['addTable'];
						$(item).find('option[value="'+cache[index].userId+'"]').attr('selected','selected');
					})
					form.on('select(userSelect)',function(obj){
						var index = $(obj.elem).closest('tr').data('index');
						var trData = table.cache['addTable'][index];
						trData.userId = obj.value;
					})
					table.on('edit(addTable)',function(obj){
						if(obj.field == "number"){
							var data = obj.data;
							if(isNaN(data.number)){
								data.number = 0;
								myutil.emsg('请确证填写数量！');
							}
							var sum = 0;
							var cache = table.cache['addTable'];
							for(var i in cache){
								sum -= -cache[i].number;
							}
							$('#orderNumber').val(sum);
						}
					})
					form.render();
				}
			})
			function getUserSelect(){
				return function(d){
					return '<select lay-search lay-filter="userSelect" class="userSelect">'+allUserSelectHtml+'</select>';
				}
			}
			table.on('toolbar(addTable)',function(obj){
				switch(obj.event){
				case 'add':		choosedProduct();		break;
				case 'delete':	deleteProduct();			break;
				case 'sureAdd':	sureAdd(); 	break;
				}
			})
			function choosedProduct(){
				productList = [];
				var chooseProductWin = layer.open({
					title: '选择客户',
					type:1,
					area:['50%','90%'],
					content: $('#chooseProductWin')
				})
				mytable.render({
					elem: '#choosedTable',
					url:'${ctx}/ledger/customerPage?', //type=1
					cols:[[
							{ type:'checkbox',},
							{ title:'客户编号',	field:'id',	},
							{ title:'客户名称',	field:'name',	},
					       ]],
					done: function(res, curr, count){		//回显复选框选中
						for(var i=0;i< res.data.length;i++){
	                         for (var j = 0; j < productList.length; j++) {
	                             if(res.data[i].id == productList[j].id)
	                             {
	                                 res.data[i]["LAY_CHECKED"]='true';
	                                 var index= res.data[i]['LAY_TABLE_INDEX'];
	                                 $('#choosedTable').next().find('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
	                                 $('#choosedTable').next().find('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
	                             }
	                         }
	                     }
	                     var checkStatus = table.checkStatus('choosedTable');
	                     if(checkStatus.isAll){
	                         $('#choosedTable').next().find(' .layui-table-header th[data-field="0"] input[type="checkbox"]').prop('checked', true);
	                         $('#choosedTable').next().find('.layui-table-header th[data-field="0"] input[type="checkbox"]').next().addClass('layui-form-checked');
	                     }
					}
				})
				table.on('checkbox(choosedTable)', function (obj) {	//监听复选框事件
					var currPageData = table.cache['choosedTable'];
					if(obj.checked==true){
					    if(obj.type=='one')
					    	productList.push(obj.data);
					   else{
					        for(var i=0;i<currPageData.length;i++){
					     	   var isHas=false;
					     	   layui.each(productList,function(index,item){
					     		   if(item.id == currPageData[i].id){
					     			   isHas = true;
					     		   		return;
					     		   }
					     	   })
					     	   if(!isHas)
					     		  productList.push(currPageData[i]);
					        }
					    }
					}else{
					    if(obj.type=='one'){
					        for(var i=0;i<productList.length;i++)
					           if(productList[i].id==obj.data.id){
					        	   	productList.splice(i,1);
									break;
					           }
					    }else{
					 	   	for(var i=0;i<currPageData.length;i++)
					 		   	for(var j=0;j<productList.length;j++)
					 			   	if(productList[j].id == currPageData[i].id){	
					 				  	productList.splice(j,1);
					 				   	break;
					 			   	}
					    }
					}
	    		});
				form.on('submit(searchProduct)',function(obj){
					table.reload('choosedTable',{
						where: obj.field,
						page: { curr:1 },
					})
				})
				$('#sureChoosed').unbind().on('click',function(){
					var t = table.cache['addTable'];
					var newCus = [];
					var isHas = false;
					layui.each(productList,function(index1,item1){
						if(isHas)
							return;
						for(var i=0;i<t.length;i++)
							if(t[i].customerId == item1.id){
								isHas = true;
								myutil.emsg('客户:'+item1.name+' 已存在请勿重复添加客户');
							}
						newCus.push({
							userId: '',
							name:item1.name,
							remark: '',
							number: 0,
							customerId: item1.id,
						})
					})
					if(isHas)
						return;
					table.reload('addTable',{ data: t.concat(newCus) })
					layer.close(chooseProductWin);
				})
			}// end choosedProduct
			function sureAdd(){
				var orderChild = [];
				var msg = '';
				var productId = $('#productIdHidden').val();
				var bacthNumber = $('#bacthNumber').val();
				if(productId == '')
					return myutil.emsg('请选择客户!');
				layui.each(table.cache['addTable'],function(index,item){
					orderChild.push({
						childNumber: item.number,
						userId: item.userId,
						customerId: item.customerId,
						childRemark: item.remark,
					})
					if(item.userId=='' || item.customerId=="" ||  item.numbe=="" || bacthNumber==""){
						return msg = '数量、跟单人、批次号不能为空!';
					}
					if(isNaN(item.number)|| item.number<0)
						return msg = '请正确填写数量!';
				})	
				orderChild.length==0 && (msg = '请选择客户');
				var orderDate = $('#orderTime').val();
				if(!orderDate)
					msg = '订单时间不能为空!';
				if(msg!='')
					return myutil.emsg(msg);
				orderChild = JSON.stringify(orderChild);
					
				myutil.saveAjax({
					url: '/ledger/addOrder',
					data: { 
						productId: productId,
						bacthNumber: bacthNumber,
						orderChild: orderChild,
						remark: $('#orderRemark').val(),
						orderDate: orderDate,
						number: $('#orderNumber').val(),
						orderTypeId: $('#orderTypeSelect').val(),
					},
					success:function(r){
						table.reload('tableAgreement');
						layer.close(addWin);
					}
				})
			}// end sureAdd
			function deleteProduct(){
				var checked = layui.table.checkStatus('addTable').data;
				if(checked.length==0)
					return layer.msg('请选择删除的产品',{icon:2});
				var confirm = layer.confirm('是否确认删除？',function(){
					layui.each(checked,function(index1,item1){
						layui.each(table.cache['addTable'],function(index2,item2){
							if(item1.id == item2.id ){
								table.cache['addTable'].splice(index2,1);
								return;
							}
						})
					})
					table.reload('addTable',{ data: table.cache['addTable'], })
					layer.close(confirm);
				})
			}// end deleteProduct
		}
		function edit(){
			var choosed=layui.table.checkStatus('tableAgreement').data,
			tpl = orderAgreementTpl.editTpl,
			//tpl=editTpl.innerHTML,
			html='';
			var msg = '';
			choosed.length>1 && (msg = "不能同时编辑多条信息");
			choosed.length<1 && (msg = "至少选择一条信息编辑");
			if(choosed.length!=1)
				return myutil.emsg(msg);
			laytpl(tpl).render(choosed[0],function(h){
				html=h;
			})
			var addEditWin=layer.open({
				type:1,
				title: '修改',
				area:['55%','65%'],
				btn: ['确定','取消'],
				content:html,
				yes:function(){
					$('#sureEdit').click();
				}
			})
			getAllCustom('editCustomSelect',choosed[0].customer.id);
			form.render();
			form.on('submit(sureEdit)',function(obj){
				myutil.saveAjax({
					url: '/ledger/updateOrder',
					data: obj.field,
					success:function(r){
						table.reload('tableAgreement');
						layer.close(addEditWin);
					}
				})
			})
			$('#editProductName,#editProductNumber').on('click',function(){
				var chooseProductWin = layer.open({
					title: '选择产品',
					type:1,
					area:['45%','90%'],
					shadeClose:true,
					content: $('#chooseProductWin'),
					success:function(){
						$('#sureChoosed').hide();
						$('#tipDouble').show();
					},
					end:function(){
						$('#sureChoosed').show();
						$('#tipDouble').hide();
					}
				})
				table.render({
					elem: '#choosedTable',
					page: true,
					url:'${ctx}/productPages',
					request:{ pageName:'page', limitName:'size' },
					parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
					cols:[[
							{ align:'center', title:'产品编号',	field:'number',	},
							{ align:'center', title:'产品名称',	field:'name',	},
					       ]],
				})
				form.on('submit(searchProduct)',function(obj){
					table.reload('choosedTable',{
						where: obj.field,
						page: { curr:1 },
					})
				})
				table.on('rowDouble(choosedTable)',function(obj){
					$('#editProductId').val(obj.data.id);
					$('#editProductNumber').val(obj.data.number);
					$('#editProductName').val(obj.data.name);
					form.render();
					layer.close(chooseProductWin);
				})
			})
		}
		function deletes(){
			var choosed=layui.table.checkStatus('tableAgreement').data;
			if(choosed.length<1)
				return layer.msg('请选择商品',{icon:2});
			layer.confirm("是否确认删除？",function(){
				var ids='';
				for(var i=0;i<choosed.length;i++)
					ids+=(choosed[i].id+",");
				myutil.deleteAjax({
					url:"/ledger/deleteOrder",
					ids: ids,
					success: function(){
						table.reload('tableAgreement');
					}
				})
			})
		}
		function getAllCustom(id,selected){
			myutil.getSelectHtml({
				url:'/ledger/allCustomer',
				title: 'name',
				value: 'id',
				selectOption: selected,
				done: function(html){
					$('#'+id).html(html);
					form.render();
				}
			})
		}
	}//end define function
)//endedefine
</script>

</html>