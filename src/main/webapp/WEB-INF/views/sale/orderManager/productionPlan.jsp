<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>生产计划单</title>
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
<script id="toolbar" type="text/html">
<div>
	<span lay-event="addAgreement"  class="layui-btn layui-btn-sm" >新增合同</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >批量删除</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改合同</span>
	<span lay-event="auditAgreement"  class="layui-btn layui-btn-sm layui-btn-normal" >审核</span>
</div>
</script>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
	saveProductionPlan:'layui/myModules/sale/saveProductionPlan',
}).define(
	['mytable','laydate','saveProductionPlan'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, saveProductionPlan = layui.saveProductionPlan
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.getLastData();
		myutil.clickTr();
		myutil.config.msgOffset = '250px';
		
		var noTransTableData = [];
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
					noTransTableData = d;
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
	       autoMerge:{
		    	 field:['bacthNumber','orderDate','product_number','product_name','number','remark',
		    		 'audit','0'],  
		   },
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
			case 'auditAgreement': 
				myutil.deleTableIds({
					table:'tableAgreement',
					text:'请选择相关信息|是否确认审核?',
					url:'/ledger/auditOrder',
				});
				break;
			} 
		})
		//新增合同
		function addAgreement(){
			saveProductionPlan.add({
				data:{}
			});
		}
		function edit(){
			var choosed=layui.table.checkStatus('tableAgreement').data;
			if(choosed.length!=1)
				return myutil.emsg('只能选择一条信息进行编辑');
			if(choosed[0].audit==1)
				return myutil.emsg("该合同数据已经审核，无法修改");
			for(var i in noTransTableData){
				if(noTransTableData[i].id==choosed[0].id){
					saveProductionPlan.update({
						data: noTransTableData[i]
					});
					break;
				}
			}
		}
		function deletes(){
			myutil.deleTableIds({
				table:'tableAgreement',
				url:"/ledger/deleteOrder",
				text:'请选择商品|是否确认删除？',
			})
		}
		saveProductionPlan.reloadTable = 'tableAgreement';
		saveProductionPlan.init();
	}//end define function
)//endedefine
</script>
</html>