<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/formSelect/formSelects-v4.css" />
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>加工退货单</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td>退货日期：</td>
				<td><input type="text" name="orderTimeBegin" id="searchTime" class="layui-input"></td>
				<td>订单号：</td>
				<td><input type="text" name="orderName" class="layui-input"></td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
	formSelects : 'formSelect/formSelects-v4',
	returnOrder : 'layui/myModules/sale/returnOrder',
}).define(
	['mytable','formSelects','returnOrder','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, formSelects = layui.formSelects
		, returnOrder = layui.returnOrder
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		
		laydate.render({ elem:'#searchTime', range:'~', })
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/ledger/refundBillsPage',
			toolbar:[ '<span class="layui-btn layui-btn-sm" lay-event="update">修改</span>',].join(''),
			curd:{
				btn:[4],
				otherBtn:function(obj){
					var check = layui.table.checkStatus('tableData').data;
					if(obj.event=="update"){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据进行编辑');
						var data = check[0].orderOutSource;
						data.outsourceTaskChoosed = check[0].outsourceTask;
						data.orderOutSourceId = check[0].orderOutSource.id;
						data = $.extend({},data,check[0])
						data.outsourceTask = data.orderOutSource.outsourceTask;
						returnOrder.update({
							data: data,
						})
					}
				}
			},
			autoUpdate:{
				deleUrl:'/ledger/deleteRefundBills',
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'退货日期',   field:'returnTime',   },
			       { title:'退货工序',   field:'outsourceTaskIds',templet: getProcess(),	},
			       { title:'退货数量',   field:'returnNumber',	},
			       { title:'退货原因',   field:'returnRemark', 	},
			       ]]
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
		form.on('submit(search)',function(obj){
			if(obj.field.orderTimeBegin){
				var t = obj.field.orderTimeBegin.split(' ~ ');
				obj.field.orderTimeBegin = t[0]+' 00:00:00';
				obj.field.orderTimeEnd = t[1]+' 23:59:59';
			}else
				obj.field.orderTimeEnd = '';
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
	}//end define function
)//endedefine
</script>
</body>
</html>