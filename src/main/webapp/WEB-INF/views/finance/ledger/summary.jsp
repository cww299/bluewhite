<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>汇总</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
</head>
<style type="text/css">
	td{
		text-align:center;
	}
	.layui-table-cell{
		text-align:center;
	}
	.minTd{
		width:120px;
	}
</style>
<body>
	
<div class="layui-card">
	<div class="layui-card-body">					
		<table class="layui-form" id="searchTable">
			<tr>
				<td>货款日期：</td>
				<td><input id="searchTime" placeholder="请输入时间" class="layui-input"></td>
				<td>&nbsp;&nbsp;</td>
				<td>客户：</td>
				<td><select name="customerId" id="searchCustomer" lay-search><option value="">请选择</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="searchTable">统计</button></td>
			</tr>
		</table>
		<table id="summaryTable" lay-filter="summaryTable"></table>
	</div>
</div>
<!-- 查看明细隐藏框 -->
<div id="moreInfoDiv" style="display:none;padding:10px;">
	<table id="moreInfoTable" lay-filter="moreInfoTable"></table>
</div>
<script id="moreInfoTableToolbar" type="html/css">
<div class="layui-btn-container layui-inline">
	<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deletes">删除</span>
</div>
</script>
<script id="summaryTableToolbar" type="html/css">
	<div>
		<span class="layui-badge">提示：双击查看已到款明细</button>
	</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
}).define(
[ 'jquery','table','laydate','mytable'],
function() {
	var $ = layui.jquery
	, layer = layui.layer 				
	, form = layui.form 
	, laydate = layui.laydate
	, table = layui.table
	, myutil = layui.myutil
	, mytable = layui.mytable
	, tablePlug = layui.tablePlug;
	
	myutil.config.ctx = '${ctx}';
	myutil.config.msgOffset = '200px';	
	myutil.timeFormat();
	myutil.clickTr();
	var thisMonth = new Date().format("yyyy-MM");
	var thisMonthFirstDay = thisMonth+'-01 00:00:00';
	laydate.render({ elem:'#searchTime', range:"~" });
	var lookoverCustomerId = '';
	mytable.renderNoPage({
		elem:'#summaryTable',
		url: '${ctx}/ledger/collectBill',
		toolbar:'#summaryTableToolbar',
		totalRow: ['offshorePay','acceptPay','acceptPayable','disputePay','nonArrivalPay',
					'overpaymentPay','arrivalPay', ],
		cols:[[
			   {type:'checkbox', },
		       {title:'客户',	 field:'customerName',	},
		       {title:'货款总值', field:'offshorePay',	},
		       {title:'客户认可货款',		field:'acceptPay', 		},
		       {title:'杂支应付',			field:'acceptPayable',	},
		       {title:'争议货款',			field:'disputePay',		},
		       {title:'未到货款',			field:'nonArrivalPay',	},
		       {title:'客户多付货款',		field:'overpaymentPay',	},
		       {title:'已到货款',			field:'arrivalPay',		},
	       ]],
	})
	table.on('rowDouble(summaryTable)',function(obj){
		lookoverCustomerId = obj.data.customerId;
		var win = layer.open({
			type:1,
			content:$('#moreInfoDiv'),
			area:['50%','70%'],
			success:function(){
				table.reload('moreInfoTable',{
					url:'${ctx}/ledger/receivedMoneyPage',
					where: {
						customerId: lookoverCustomerId,
						billDate: obj.data.billDate ? obj.data.billDate:'',
					}
				}) 
			}
		})
	})
	mytable.render({
		elem:'#moreInfoTable',
		data:[],
		toolbar:'#moreInfoTableToolbar',
		curd: {
			btn: [1,2,3,4],
			addTemp: {receivedMoneyDate: '', receivedMoney:0,receivedRemark:'',},
			saveFun: function(data){
				for(var i in data)
					data[i].customerId = lookoverCustomerId;
				myutil.saveAjax({
					url: '/ledger/saveReceivedMoneyList',
					data: {
						jsonList: JSON.stringify(data)
					},
					success:function(){
						table.reload('moreInfoTable')
					}
				})
			}
		},
		autoUpdate: {
			updateUrl: '/ledger/addReceivedMoney',
			deleUrl: '/ledger/deleteReceivedMoney',
		},
		verify: { 
			count:['receivedMoney'],
			notNull: ['receivedMoneyDate', 'receivedMoney'],
		},
		cols:[[
		   { type:'checkbox'},
	       {title:'日期',	field:'receivedMoneyDate',	type: 'date', edit: true, },
	       {title:'到账款',	field:'receivedMoney',	edit: true, },
	       {title:'备注',	 field:'receivedRemark', 	edit: true, },
       ]]
	});
	myutil.getSelectHtml({
		url:'/ledger/allCustomer',
		value: 'id',
		title: 'name',
		tips: '请选择客户',
		done: function(html){
			$('#searchCustomer').html(html);
			form.render();
		}
	})
	form.on('submit(searchTable)',function(obj){
		var val = $('#searchTime').val(), beg="",end="";
		if(val!=''){
			beg = val.split('~')[0].trim()+' 00:00:00';
			end = val.split('~')[1].trim()+' 23:59:59';
		}
		obj.field.orderTimeBegin = beg;
		obj.field.orderTimeEnd = end;
		table.reload('summaryTable',{
			url:'${ctx}/ledger/collectBill',
			where: obj.field,
		})
	})
})
</script>
</body>
</html>