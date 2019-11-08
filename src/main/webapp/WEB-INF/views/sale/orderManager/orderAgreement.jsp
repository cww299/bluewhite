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
.layui-table tbody tr:hover, .layui-table-hover {
	background-color: transparent; 
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
<script id="addTpl" type="text/html">
<div style="padding:10px;">
	<table class="layui-form">
		<tr>
			<td>商品选择：</td>
			<td><input readonly placeholder="点击选择商品" class="layui-input" id="chooseProductInput">
				<input type="hidden" name="productId" id="productIdHidden"></td>
			<td>&nbsp;&nbsp;</td>
			<td>订单类型：</td>
			<td style="width:100px;"><select id="orderTypeSelect"><option value="">请选择</option></select></td>
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
</script>
<script id="toolbar" type="text/html">
<div>
	<span lay-event="addAgreement"  class="layui-btn layui-btn-sm" >新增合同</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除合同</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改合同</span>
</div>
</script>
<script  id="addTableToolbar" type="text/html">
<div>
	<span lay-event="chooseCustomer"  class="layui-btn layui-btn-sm">选择客户</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除客户</span>
	<span lay-event="sureAdd" class="layui-btn layui-btn-sm">确定新增</span>
</div>
</script>
<script  id="chooseCustomerWin" type="text/html">
<div style="padding:10px;">
	<table class="layui-form">
		<tr>
			<td>客户名称：</td>
			<td><input class="layui-input" type="text" name="name"></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" id="searchBtn">搜索</span></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-btn layui-btn-sm" id="sureChoosed">确定</span></td>
		</tr>
	</table>
	<table id="choosedCustomerTable" lay-filter="choosedCustomerTable"></table>
</div>
</script>
<script  id="choosedProductWin" type="text/html">
<div style="padding:10px;">
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
</script>
<script  id="editAgreement" type="text/html">
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
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block">
			<input class="layui-input" name="remark" value="{{d.remark}}">
		</div>
	</div>
	<div class="layui-item" pane id="editOrderDate">
		<label class="layui-form-label">下单时间</label>
		<div class="layui-input-block">
			<input class="layui-input" name="remark" id="editOrderDateInput" value="{{d.orderDate || ''}}">
		</div>
	</div>
	<p style="display:none;"><button lay-submit lay-filter="sureEdit" id="sureEdit">确定</button></p>
</div>
</script>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
	orderAgreementTpl:'layui/tpl/sale/orderAgreementTpl',
}).define(
	['mytable','laydate',],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.getLastData();
		myutil.config.msgOffset = '250px';
		
		//主页面功能，新增合同时的下单人，全局获取一次
		var allUser = [];
		myutil.getData({
			url:'${ctx}/system/user/findUserList',
			success:function(d){
				allUser = d;
				allUser.unshift({id:'',userName:'请选择'});
			}
		})
		laydate.render({ elem:'#searchTime', range:'~' })
		mytable.render({
			elem:'#tableAgreement',
			url:'${ctx}/ledger/orderPage',
			toolbar: $('#toolbar').html(),
			height:'750',
			colsWidth:[0,10,7,8,0,6,0,5,8,8,8,8,],
			parseData:function(ret){
				if(ret.code==0){
					var data = [],d = ret.data.rows;
					for(var i=0,len=d.length;i<len;i++){
						var child = d[i].orderChilds;
						for(var j=0,l=child.length;j<l;j++){
							data.push({
								id: d[i].id,
								bacthNumber: d[i].bacthNumber,
								audit: d[i].audit,
								number: d[i].number,
								orderDate: d[i].orderDate,
								remark: d[i].remark,
								product: d[i].product,
								childCustome: child[j].customer,
								childNumber: child[j].childNumber,
								childRemark: child[j].childRemark,
								childUser: child[j].user,
							})
						}
					}
					return {  msg:ret.message,  code:ret.code , data: data, count:ret.data.total }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			},
			ifNull:'---',
			cols:[[
			       { type:'checkbox',},
			       { title:'批次号',   	field:'bacthNumber', 	},
			       { title:'下单时间',   	field:'orderDate',   type:'date' 	},
			       { title:'产品编号',	field:'product_number', 	},
			       { title:'产品名称',	field:'product_name',	},
			       { title:'总数量',   field:'number',	 },
			       { title:'备注',   field:'remark',	 },
			       { title:'审核',   field:'audit',	 transData:{ data:['否','是'],}},
			       { title:'客户',   field:'childCustome_name',	},
			       { title:'数量',   field:'childNumber',	},
			       { title:'备注',   field:'childRemark',	},
			       { title:'跟单人',   field:'childUser_userName',	},
			       ]],
			done:function(){
				merge('0');
				merge('bacthNumber');
				merge('orderDate');
				merge('product_number');
				merge('product_name');
				merge('number');
				merge('remark');
				merge('audit');
				function merge(field){
					var rowspan = 1,mainCols=0;
					var cache = table.cache['tableAgreement'];
					var allCol = $('#tableAgreement').next().find('td[data-field="'+field+'"]');
					layui.each(allCol,function(index,item){
						if(index!=0){
							var thisData = cache[index],lastData = index!=0?cache[index-1]:{id:-1};
							if(!thisData)
								return;
							if(thisData.id!=lastData.id){
								$(allCol[mainCols]).attr('rowspan',rowspan)
								mainCols = index;
								rowspan = 1;
							}else{	//与上一列相同
								rowspan++;
								$(item).css('display','none')
							}
						}
					})
					$(allCol[mainCols]).attr('rowspan',rowspan)
				}
			}
		})
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
			case 'addAgreement': addAgreement();		break;
			case 'update':	edit(); 	break;
			case 'delete':	deletes();			break;
			} 
		})
		//新增合同
		function addAgreement(){
			var addWin = layer.open({
				title: '新增合同',
				type:1,
				area:['90%','100%'],
				content: $('#addTpl').html(),
				success:function(){
					laydate.render({
						elem:'#orderTime',
						type:'datetime',
					})
					$('#orderTypeSelect').append(allTypeSelectHtml);
					$('#chooseProductInput').unbind().on('click',function(){
						var chooseProductWinNew = layer.open({
							type:1,
							title:'商品选择',
							area:['50%','70%'],
							content: $('#choosedProductWin').html(),
							shadeClose:true,
							success:function(){
								mytable.render({
									elem:'#chooseProductTable',
									url:'${ctx}/productPages',
									cols:[[
										{title:'产品编号',field:'number',},
										{title:'产品名称',field:'name',},
									]],
								})
								form.on('submit(searchProductBtn)',function(obj){
									table.reload('chooseProductTable',{
										where:obj.field,
										page:{ curr:1, }
									})
								})
								table.on('rowDouble(chooseProductTable)', function(obj){
									var data = obj.data;
									$('#productIdHidden').val(data.id);
									$('#chooseProductInput').val(data.name);
								  	layer.close(chooseProductWinNew);
								});
							}
						})
					})
					mytable.renderNoPage({
						elem: '#addTable',
						toolbar: $('#addTableToolbar').html(),
						size:'lg',
						data:[],
						cols:[[
								{ type:'checkbox',},
								{ title:'客户',   	field:'name', 	},
								{ title:'下单人',    field:'userId',    type:'select', select:{data:allUser,name:'userName'}},
								{ title:'数量',   	field:'number',  	edit:true, },
								{ title:'备注',   	field:'remark',	edit:true, },
						       ]],
						done:function(){
							table.on('edit(addTable)',function(obj){
								if(obj.field == "number"){
									var allCache = table.cache['addTable'];
									var index = $(obj.tr).data('index');
									var cache = allCache[index];
									var number = obj.data.number;
									if(isNaN(number) || number<0 || number%1.0!=0 ){
										cache['number'] = myutil.lastData;	
										$(this).val(myutil.lastData);   //回滚数据
										return myutil.emsg('请确证填写数量！');
									}
									var val = parseInt(number);
									cache['number'] = val;	
									$(this).val(val);   
									var sum = 0;
									for(var i in allCache){
										sum -= -allCache[i].number;
									}
									$('#orderNumber').val(sum);
								}
							})
							form.render();
						}
					})
				}
			})
			table.on('toolbar(addTable)',function(obj){
				switch(obj.event){
				case 'chooseCustomer':	chooseCustomer();	break;
				case 'delete':	deleteCustomer();			break;
				case 'sureAdd':	sureAdd(); 	break;
				}
			})
			function chooseCustomer(){
				customerList = [];
				var chooseProductWin = layer.open({
					title: '选择客户',
					type:1,
					area:['50%','90%'],
					content: $('#chooseCustomerWin').html(),
					success:function(){
						mytable.render({
							elem: '#choosedCustomerTable',
							url:'${ctx}/ledger/customerPage?', //type=1
							cols:[[
									{ type:'checkbox',},
									{ title:'客户编号',	field:'id',	},
									{ title:'客户名称',	field:'name',	},
							       ]],
							done: function(res, curr, count){		//回显复选框选中
								for(var i=0;i< res.data.length;i++){
			                         for (var j = 0; j < customerList.length; j++) {
			                             if(res.data[i].id == customerList[j].id)
			                             {
			                                 res.data[i]["LAY_CHECKED"]='true';
			                                 var index= res.data[i]['LAY_TABLE_INDEX'];
			                                 $('#choosedCustomerTable').next().find('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
			                                 $('#choosedCustomerTable').next().find('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
			                             }
			                         }
			                     }
			                     var checkStatus = table.checkStatus('choosedTable');
			                     if(checkStatus.isAll){
			                         $('#choosedCustomerTable').next().find(' .layui-table-header th[data-field="0"] input[type="checkbox"]').prop('checked', true);
			                         $('#choosedCustomerTable').next().find('.layui-table-header th[data-field="0"] input[type="checkbox"]').next().addClass('layui-form-checked');
			                     }
			                     form.render();
							}
						})
					}
				})
				table.on('checkbox(choosedCustomerTable)', function (obj) {	//监听复选框事件
					var currPageData = table.cache['choosedCustomerTable'];
					if(obj.checked==true){
					    if(obj.type=='one')
					    	customerList.push(obj.data);
					   else{
					        for(var i=0;i<currPageData.length;i++){
					     	   var isHas=false;
					     	   layui.each(customerList,function(index,item){
					     		   if(item.id == currPageData[i].id){
					     			   isHas = true;
					     		   	   return;
					     		   }
					     	   })
					     	   if(!isHas)
					     		  customerList.push(currPageData[i]);
					        }
					    }
					}else{
					    if(obj.type=='one'){
					        for(var i=0;i<customerList.length;i++)
					           if(customerList[i].id==obj.data.id){
					        	   	customerList.splice(i,1);
									break;
					           }
					    }else{
					 	   	for(var i=0;i<currPageData.length;i++)
					 		   	for(var j=0;j<customerList.length;j++)
					 			   	if(customerList[j].id == currPageData[i].id){	
					 				  	customerList.splice(j,1);
					 				   	break;
					 			   	}
					    }
					}
	    		});
				form.on('submit(searchProduct)',function(obj){
					table.reload('choosedCustomerTable',{
						where: obj.field,
						page: { curr:1 },
					})
				})
				$('#sureChoosed').unbind().on('click',function(){
					var t = table.cache['addTable'];
					var newCus = [];
					var isHas = false;
					layui.each(customerList,function(index1,item1){
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
			}
			function sureAdd(){
				var orderChild = [];
				var msg = '';
				var productId = $('#productIdHidden').val();
				var bacthNumber = $('#bacthNumber').val();
				var orderDate = $('#orderTime').val();
				var typeId = $('#orderTypeSelect').val();
				layui.each(table.cache['addTable'],function(index,item){
					orderChild.push({
						childNumber: item.number,
						userId: item.userId,
						customerId: item.customerId,
						childRemark: item.remark,
					})
					if(item.userId=='' || item.numbe==""){
						return msg = '数量、跟单人不能为空!';
					}
					if(isNaN(item.number)|| item.number<=0)
						return msg = '请正确填写数量!';
				})	
				orderChild.length==0 && (msg = '请选择客户');
				if(productId == '')
					return myutil.emsg('请选择商品!');
				else if(typeId=="")
					msg = '订单类型不能为空!';
				else if(!orderDate)
					msg = '订单时间不能为空!';
				else if(bacthNumber=="")
					msg = '批次号不能为空!';
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
						orderTypeId: typeId,
					},
					success:function(){
						table.reload('tableAgreement');
						layer.close(addWin);
					}
				})
			}// end sureAdd
			function deleteCustomer(){
				var checked = layui.table.checkStatus('addTable').data;
				if(checked.length==0)
					return layer.msg('请选择删除的客户',{icon:2});
				var confirm = layer.confirm('是否确认删除？',function(){
					var allCache = table.cache['addTable'];
					layui.each(checked,function(index1,item1){
						layui.each(allCache,function(index2,item2){
							if(item1.customerId == item2.customerId ){
								allCache.splice(index2,1);
								return;
							}
						})
					})
					var sum = 0;
					for(var i in allCache){
						sum -= -allCache[i].number;
					}
					$('#orderNumber').val(sum);
					table.reload('addTable',{ data: allCache, })
					layer.close(confirm);
				})
			}// end deleteCustomer
		}
		
		
		function edit(){
			var choosed=layui.table.checkStatus('tableAgreement').data,
			html='';
			if(choosed.length!=1)
				return myutil.emsg('只能选择一条信息进行编辑');
			if(choosed[0].audit==1)
				return myutil.emsg("该合同数据已经审核，无法修改");
			laytpl($('#editAgreement').html()).render(choosed[0],function(h){
				html=h;
			})
			var addEditWin=layer.open({
				type:1,
				title: '修改',
				area:['30%','65%'],
				btn: ['确定','取消'],
				content:html,
				success:function(){
					laydate.render({
						elem:'#editOrderDateInput',
						type:'datetime'
					})
					getAllCustom('editCustomSelect',choosed[0].customer?choosed[0].customer.id:"");
					$('#editProductName,#editProductNumber').on('click',function(){
						var chooseProductWin = layer.open({
							title: '选择产品',
							type:1,
							area:['45%','90%'],
							shadeClose:true,
							content: $('#choosedProductWin').html(),
							success:function(){
								mytable.render({
									elem: '#chooseProductTable',
									url:'${ctx}/productPages',
									cols:[[
											{ title:'产品编号',	field:'number',	},
											{ title:'产品名称',	field:'name',	},
									       ]],
								})
								form.on('submit(searchProduct)',function(obj){
									table.reload('choosedTable',{
										where: obj.field,
										page: { curr:1 },
									})
								})
								table.on('rowDouble(chooseProductTable)',function(obj){
									$('#editProductId').val(obj.data.id);
									$('#editProductNumber').val(obj.data.number);
									$('#editProductName').val(obj.data.name);
									form.render();
									layer.close(chooseProductWin);
								})
							},
						})
					})
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
					form.render();
				},
				yes:function(){
					$('#sureEdit').click();
				}
			})
		}
		function deletes(){
			myutil.deleTableIds({
				table:'tableAgreement',
				url:"/ledger/deleteOrder",
				text:'请选择商品|是否确认删除？',
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
		var allTypeSelectHtml = '';
		myutil.getData({
			url:'${ctx}/basedata/list?type=orderNumberType',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++)
					allTypeSelectHtml+='<option value="'+d[i].id+'">'+d[i].name+'</option>'
			}
		})
	}//end define function
)//endedefine
</script>
</html>