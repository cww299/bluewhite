<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>下单合同</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
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
			<td>客户选择：</td>
			<td><select id="customSelect" lay-search><option value="">请选择</option></select></td>
			<td>&nbsp;&nbsp;</td>
			<td>订单时间：</td>
			<td><input type="text" id="addDefaultTime" class="layui-input" ></td>
		</tr>
	</table>
	<table id="addTable" lay-filter="addTable"></table>
</div>
<!-- 商品选择弹窗 -->
<div style="padding:10px;display:none;" id="chooseProductWin">
	<table class="layui-form">
		<tr>
			<td>商品名称：</td>
			<td><input class="layui-input" type="text" name="name"></td>
			<td>&nbsp;&nbsp;</td>
			<td>商品编号：</td>
			<td><input class="layui-input" type="text" name="number"></td>
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
	<div class="layui-item">
		<label class="layui-form-label">客户</label>
		<div class="layui-input-block">
			<select id="editCustomSelect" name="customerId" lay-search></select>
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">批次号</label>
		<div class="layui-input-block">
			<input class="layui-input" name="bacthNumber" value="{{d.bacthNumber}}" lay-verify="required">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">产品编号</label>
		<div class="layui-input-block">
			<input class="layui-input" value="{{d.product.number}}" readonly id="editProductNumber">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">产品名称</label>
		<div class="layui-input-block">
			<input class="layui-input" value="{{d.product.name}}" readonly id="editProductName">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">数量</label>
		<div class="layui-input-block">
			<input class="layui-input" name="number" value="{{d.number}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">价格</label>
		<div class="layui-input-block">
			<input class="layui-input" name="price" value="{{d.price}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item">
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
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改</span>
</div>
</script>
<!-- 表格工具栏模板 -->
<script type="text/html" id="addTableToolbar">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm">商品选择</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除商品</span>
	<span lay-event="sureAdd" class="layui-btn layui-btn-sm">确定新增</span>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil : 'layui/myModules/myutil',
}).define(
	['tablePlug','myutil','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		//myutil.config.msgOffset = '150px';
		myutil.keyDownEntry(function(){   //监听回车事件
			$('#searchBtn').click();
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
			       { align:'center', title:'下单时间',   	field:'orderDate', width:'12%'  	},
			       { align:'center', title:'产品编号',	field:'productNumber', width:'10%', 	templet:'<span>{{ d.product?d.product.number:""}}</span>'	},
			       { align:'center', title:'产品名称',	field:'productName',	templet:'<span>{{ d.product?d.product.name:""}}</span>'},
			       { align:'center', title:'数量',   field:'number',	 width:'6%'},
			       { align:'center', title:'价格',   field:'price',  width:'6%'	 },
			       { align:'center', title:'备注',   field:'remark',	 },
			       ]]
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
			case 'add':		add();		break;
			case 'update':	edit(); 	break;
			case 'delete':	deletes();			break;
			}
		})
		function add(){
			var productList = [];	//记录复选框选中的值，用于回显复选框选中
			var defaultTime = '';
			var addWin = layer.open({
				title: '新增',
				type:1,
				area:['90%','90%'],
				content: $('#addWin')
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
			table.render({
				elem: '#addTable',
				toolbar: '#addTableToolbar',
				page: true,
				data:[],
				cols:[[
						{ align:'center', type:'checkbox',},
						{ align:'center', title:'批次号',   	field:'bacthNumber',  edit:true,	},
						{ align:'center', title:'下单时间',   field:'orderDateTime', width:'10%',edit:false,	},
						{ align:'center', title:'产品编号',	field:'productNumber', },
						{ align:'center', title:'产品名称',	field:'name',	  },
						{ align:'center', title:'产品价格',	field:'price',	edit:true,	},
						{ align:'center', title:'数量',   	field:'number',  edit:true,	},
						{ align:'center', title:'备注',   	field:'remark', edit:true, 	},
				       ]],
				done: function(){
					layui.each($('#addTable').next().find('td[data-field="orderDateTime"]'),function(index,item){
						item.children[0].onclick = function(event) { layui.stope(event) };
						laydate.render({
							elem: item.children[0],
							done: function(val){
								var index = $(this.elem).closest('tr').attr('data-index');
								table.cache['addTable'][index]['orderDateTime'] = val;
							}
						})
					})
				}
			})
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
					title: '选择产品',
					type:1,
					area:['50%','70%'],
					content: $('#chooseProductWin')
				})
				table.render({
					elem: '#choosedTable',
					page: true,
					url:'${ctx}/productPages',
					request:{ pageName:'page', limitName:'size' },
					parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
					cols:[[
							{ align:'center', type:'checkbox',},
							{ align:'center', title:'产品编号',	field:'number',	},
							{ align:'center', title:'产品名称',	field:'name',	},
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
				table.on('checkbox(choosedTable)', function (obj) {		//监听复选框事件
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
					var newPro = [];
					var isHas = false;
					layui.each(productList,function(index1,item1){
						if(isHas)
							return;
						for(var i=0;i<t.length;i++)
							if(t[i].id == item1.id){
								isHas = true;
								myutil.emsg(item1.number+':'+item1.name+'已存在请勿重复添加商品');
							}
						newPro.push({
							id: item1.id,
							name: item1.name,
							productNumber: item1.number,
							bacthNumber: '',
							price: 0,
							remark: '',
							number: 1,
							orderDateTime: defaultTime,
						})
					})
					if(isHas)
						return;
					table.reload('addTable',{ data: t.concat(newPro) })
					layer.close(chooseProductWin);
				})
			}// end choosedProduct
			function sureAdd(){
				var orderChild = [];
				var msg = '';
				var customerId = $('#customSelect').val();
				if(customerId == '')
					return myutil.emsg('请选择客户!');
				layui.each(table.cache['addTable'],function(index,item){
					orderChild.push({
						productId: item.id,
						bacthNumber: item.bacthNumber,
						price: item.price,
						remark: item.remark,
						number: item.number,
						orderDate: item.orderDateTime,
					})
					if(item.bacthNumber=='')
						return msg = '批次号不能为空!';
				})	
				orderChild.length==0 && (msg = '请选择商品');
				if(msg!='')
					return myutil.emsg(msg);
				orderChild = JSON.stringify(orderChild);
				myutil.saveAjax({
					url: '/ledger/addOrder',
					data: { orderChild: orderChild,
							customerId: customerId},
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
			tpl=editTpl.innerHTML,
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
					area:['45%','70%'],
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