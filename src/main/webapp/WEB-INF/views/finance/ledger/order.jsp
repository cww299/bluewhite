<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单管理</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>发货日期：</td>
				<td><input type="text" class="layui-input" id="searchTime"></td>
				<td>&nbsp;&nbsp;</td>
				<td>是否审核：</td>
				<td style="width:150px;"><select name="audit"><option value="">是否审核</option>
															 <option value="1">审核</option>
															 <option value="0" selected>未审核</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>是否版权：</td>
				<td style="width:150px;"><select name="copyright"><option value="">是否版权</option>
															 <option value="1">是</option>
															 <option value="0">否</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>是否借调：</td>
				<td style="width:150px;"><select name="newBacth"><option value="">是否借调</option>
															 <option value="1">借调</option>
															 <option value="0">非借调</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>客户名：</td>
				<td><input type="text" class="layui-input" name="customerName"></td>
				<td>&nbsp;&nbsp;</td>
				<td>批次号：</td>
				<td><input type="text" class="layui-input" name="bacthNumber"></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script id="tableToolbar" type="text/html">
<div>
	<span class="layui-btn layui-btn-sm" lay-event="onekeyAudit">一键审核</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="unAudit">取消审核</span>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil: 'layui/myModules/myutil',
}).define(
	['tablePlug','myutil','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table
		, laydate = layui.laydate
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.config.msgOffset = '200px';
		myutil.clickTr();
		var tipWin = '';
		laydate.render({
			elem:'#searchTime',range:'~'
		})
		var sty = "background-color: #5FB878;color: #fff;";
		var bg = "background-color: #ecf7b8;";
		table.render({
			elem:'#tableData',
			url:'${ctx}/ledger/salePage',
			where: { audit: 0 },
			page:true,
			toolbar: '#tableToolbar',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ 
				layui.each(ret.data.rows,function(index,item){
					if(item.deliveryStatus==0){
						item.deliveryNumber = '';
						item.deliveryDate = '';
						item.disputeNumber = '';
						item.disputeRemark = '';
					}
				})
				return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } 
			},
			limits:[15,30,50,100],
			limit:15,
			cols:[[
			       {align:'center', type:'checkbox', fixed:'left',},
			       {align:'center', title:'销售编号',	width:'11%',field:'saleNumber',   fixed:'left', style: sty },
			       {align:'center', title:'发货日期',   	width:'7%',	field:'sendDate',templet:'<span>{{ d.sendDate?d.sendDate.split(" ")[0]:""}}</span>',  fixed:'left', style:sty },
			       {align:'center', title:'业务员',   	width:'8%',	field:'user',	 templet:'<span>{{ d.customer?d.customer.user.userName:""}}</span>'},
			       {align:'center', title:'客户',   		width:'8%',	field:'custom',	 templet:'<span>{{ d.customer?d.customer.name:""}}</span>'},
			       {align:'center', title:'批次号',   	width:'8%',	field:'bacthNumber',	},
			       {align:'center', title:'是否借调',   	width:'6%',	field:'newBacth', templet:'<span>{{ d.newBacth==1?"借调":"非借调"}}</span>'	},
			       {align:'center', title:'产品名',   	width:'15%',field:'productName',	templet:'<span>{{ d.product?d.product.name:""}}</span>'},
			       {align:'center', title:'离岸数量',   	width:'6%',	field:'count',	},
			       {align:'center', title:'总价',   		width:'5%',	field:'sumPrice',	},
			       {align:'center', title:'单价',   		width:'5%',	field:'price', 	edit: 'text', 	style: bg },
			       {align:'center', title:'备注',   		width:'8%',	field:'remark', edit: 'text', 	style: bg },
			       {align:'center', title:'到岸数量',   	width:'6%',	field:'deliveryNumber',	},
			       {align:'center', title:'到岸日期',   	width:'7%',	field:'deliveryDate',templet:'<span>{{ d.deliveryDate?d.deliveryDate.split(" ")[0]:""}}</span>',	},
			       {align:'center', title:'争议数量',   	width:'6%',	field:'disputeNumber',	},
			       {align:'center', title:'争议备注',   	width:'8%',	field:'disputeRemark',	},
			       {align:'center', title:'预计结款日期',width:'8%',	field:'deliveryCollectionDate',	},
			       {align:'center', title:'版权',   		width:'6%',	field:'copyright', 	fixed:'right',	templet:'<span>{{ d.copyright?"是":"否"}}</span>', style:sty},
			       {align:'center', title:'是否审核', 	width:'6%',	field:'audit',		fixed:'right', 	templet:'<span>{{ d.audit==1?"是":"否"}}</span>',style:sty},
			       ]],
	       done:function(){
				var isDouble=0;
				$('td[data-field="price"]').on('click',function(obj){
					if(++isDouble%2!=0)
						$(this).click();
					if($('.layui-table-edit').length>0){
						var elem = this;
						var index = $(elem).closest('tr').attr('data-index');
						var trData = table.cache['tableData'][index];
						myutil.getData({
							url:'${ctx}/ledger/getSalePrice?customerId='+trData.customer.id+'&productId='+trData.product.id,
							done: function(data){
								var html = '无以往价格';
								if(data.length!=0){
									html='';
									layui.each(data,function(index,item){
										html += '<span style="margin:5px;" class="layui-badge layui-bg-green" data-price="'+item.price+'" data-id="'+trData.id+'">发货日期：'+
										item.sendDate.split(' ')[0]+' -- ￥'+item.price+'</span><br>';
									})
								}
								tipWin = layer.tips(html, elem, {
									  tips: [4, '#78BA32'],
					                  time:0,
					            });
								$('.layui-layer-tips .layui-badge').unbind().on('click',function(event){
									layui.stope(event)
									myutil.saveAjax({
										url:'/ledger/updateFinanceSale',
										data: {
											id: $(this).data('id'),
											price: $(this).data('price')
										},
										success:function(){ 
											layer.close(tipWin); 
											table.reload('tableData'); 
										}
									}) 
								}).mouseover(function(){
						    		$(this).css("cursor","pointer");								
						    	}).mouseout(function (){  
						    		$(this).css("cursor","default");
						        });
							}
						})
					}
				})
			}
		})
		$(document).on('mousedown', '', function (event) { 
			if($('.layui-layer-tips').length>0)
				layer.close(tipWin);
		});
		table.on('toolbar(tableData)',function(obj){
			switch(obj.event){
			case 'onekeyAudit': onekeyAudit(1); break;
			case 'unAudit': onekeyAudit(0); break;
			}
		})
		function onekeyAudit(isAudit){
			var choosed = layui.table.checkStatus('tableData').data;
			if(choosed.length==0)
				return myutil.emsg('请选择相关信息');
			var ids = [];
			layui.each(choosed,function(index,item){
				ids.push(item.id);
			})
			myutil.saveAjax({
				url:'/ledger/auditSale?audit='+isAudit,
				type: 'get',
				data: { ids:ids.join(',') },
				success: function(){
					table.reload('tableData');
				}
			})
		}
		table.on('edit(tableData)',function(obj){
			var val = obj.value, msg='';
			isNaN(val) && (msg="请正确输入单价");
			val<0 && (msg="单价不能小于0");
			if(msg!='')
				myutil.emsg(msg);
			else
				myutil.saveAjax({
					url:'/ledger/updateFinanceSale',
					data: {
						id: obj.data.id,
						price: val
					}
				}) 
			layer.close(tipWin);
			table.reload('tableData');
		})
		form.on('submit(search)',function(obj){
			var val = $('#searchTime').val(), beg='',end='';
			if(val!=''){
				val = val.split('~');
				beg = val[0].trim()+' 00:00:00';
				end = val[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = beg;
			obj.field.orderTimeEnd = end;
			table.reload('tableData',{
				where: obj.field,
				page: { curr:1 },
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>