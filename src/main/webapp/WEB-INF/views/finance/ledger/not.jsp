<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>离岸成品</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<style type="text/css">
	td{
		text-align:center;
	}
	.layui-table-cell{
		text-align:center;
	}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">					
		<table class="layui-form">
			<tr>
				<td>甲方:</td>
				<td><input type="text" name="firstNames" class="layui-input" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>乙方:</td>
				<td><input type="text" name="partyNames" id="partyNames" class="layui-input" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>产品名:</td>
				<td><input type="text" name="productName" id="productName" class="layui-input" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>批次号:</td>
				<td><input type="text" name="batchNumber" id="batchNumber2" class="layui-input" /></td>
			</tr>
			<tr><td><div style="height: 5px"></div></td></tr>
			<tr>
				<td>审核:</td>
				<td><select name="ashoreCheckr"><option value="0">未核对</option><option value="1">已核对</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>争议:</td>
				<td><select name="type"><option value="">请选择</option><option value="1">有争议数字</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>合同开始:</td>
				<td><input name="orderTimeBegin" id="beginTime" placeholder="请输入开始时间" class="layui-input"></td>
				<td>&nbsp;&nbsp;</td>
				<td>合同结束:</td>
				<td><input name="orderTimeEnd" id="endTime" placeholder="请输入结束时间" class="layui-input"> </td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><button type="button" class="layui-btn" lay-submit lay-filter="searchNotTable">查找</button></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><button type="button" id="export" class="layui-btn"> 导出绩效</button></td>
			</tr>
		</table>
		<table id="notTable" lay-filter="notTable"></table>
	</div>
</div>
<script id="notTableToolbar" type="html/css">
	<div>
		<button class="layui-btn layui-btn-sm" type="button" lay-event="ashoreCheckr">审核</button>
		<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" lay-event="noAshoreCheckr">取消审核</button>
	</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	myutil : 'layui/myModules/myutil',
}).define(
	[ 'myutil','laytpl','jquery','table','laydate'],
	function() {
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form 
		, laydate = layui.laydate
		, table = layui.table
		, myutil = layui.myutil
		, laytpl = layui.laytpl;
		laydate.render({
			elem:'#beginTime',
			type:'datetime'
		})
		laydate.render({
			elem:'#endTime',
			type:'datetime'
		})
		table.render({
			elem:'#notTable',
			page:true,
			size:'lg',
			url:'${ctx}/fince/getOrder',
			toolbar:'#notTableToolbar',
			loading:false,
			height:'680px',
			cellMinWidth: 100,
			parseData:function(ret){ return { code : ret.code, msg : ret.msg, count : ret.data.total, data : ret.data.rows } },
			request:{ pageName: 'page' ,limitName: 'size' },
			cols:[[
				   {type:'checkbox',fixed:'left'},
			       {title:'乙方电话',field:'phone',		width:'8%',templet:function(d){ return d.contact.conPhone; }},
			       {title:'乙方信息',field:'conWechat',	width:'8%',templet:function(d){ return d.contact.conWechat; }},
			       {title:'线上/线下',field:'online', 	width:'6%',templet:function(d){ return d.online==1?'线上':'线下'; },},
			       {title:'当月销售编号',field:'salesNumber',	width:'8%',},
			       {title:'合同签订日期',field:'contractTime',	width:'10%',},
			       {title:'甲方',field:'firstNames'},
			       {title:'乙方',field:'partyNames'},
			       {title:'批次号',field:'batchNumber',	width:'9%',},
			       {title:'产品名',field:'productName',	width:'8%',},
			       {title:'数量',field:'contractNumber',edit:true, style:"background-color:#acdd65;"},
			       {title:'总价',field:'contractPrice'},
			       {title:'备注',field:'remarksPrices'},
			       {title:'单价',field:'price'},
			       {title:'在途数量',field:'roadNumber'},
			       {title:'到岸数量',field:'ashoreNumber'},
			       {title:'预计借款日期',field:'date',width:'10%',},
			       {title:'争议数字',field:'disputeNumber',edit:true,style:"background-color:#acdd65;"},
			       {title:'到岸价格',field:'ashorePrice'},
			       {title:'核对审核',field:'ashoreCheckr',fixed:'right',templet:function(d){ return d.ashoreCheckr==0?'未核对':'已核对' }},
			       ]],
		})
		form.on('submit(searchNotTable)',function(obj){
			table.reload('notTable',{
				page:{curr:1},
				where:obj.field
			})
		})
		table.on('toolbar(notTable)',function(obj){
			var checked = layui.table.checkStatus('notTable').data;
			switch(obj.event){
			case 'ashoreCheckr':	
				if(checked.length==0){
					 myutil.emsg('请选择审核信息');
					 return;
				}
				layui.each(checked,function(index,data){
					myutil.saveAjax({
						url:'/fince/addOrder',
						data:{
							id:data.id,
							ashoreCheckr:1,
						},
					});
				})
				table.reload('notTable');
				break;
			case 'noAshoreCheckr':
				if(checked.length==0){
					 myutil.emsg('请选择审核信息');
					 return;
				}
				layui.each(checked,function(index,data){
					myutil.saveAjax({
						url:'/fince/addOrder',
						data:{
							id:data.id,
							ashoreCheckr:0,
						},
					});
				})
				table.reload('notTable');
				break;
			}
		})
		table.on('edit(notTable)',function(obj){
			myutil.saveAjax({
				url:'/fince/addOrder',
				data:{
					id:obj.data.id,
					[obj.field]:obj.value,
				},
			});
		})
})
</script>
</body>
</html>