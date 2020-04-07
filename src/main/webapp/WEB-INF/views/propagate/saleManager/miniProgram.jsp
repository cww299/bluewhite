<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>小程序订单</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td><input id="uploadTime" class="layui-input" placeholder="导出时间"></td>
				<td></td>
				<td><span class="layui-btn layui-btn-" id="downloadBtn">导出</span></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
}).define(
	['mytable','laydate'],
	function(){
		var $ = layui.jquery
		,laydate = layui.laydate
		,table = layui.table
		,mytable = layui.mytable
		,myutil = layui.myutil;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({
			elem:'#uploadTime',range:'~'
		})
		mytable.renderNoPage({
			elem:'#tableData',
			url: myutil.config.ctx+'/apiExtOrder/list',
			cols:[[
				{ type:'checkbox', },
				{ title:'用户信息', field:'', },
				{ title:'订单数/订单号', field:'orderNumber', },
				{ title:'状态', field:'statusStr', },
				{ title:'价格', field:'amount', },
				{ title:'实收', field:'amountReal', },
				{ title:'交易', field:'dateUpdate', },
			]]
		})
		$('#downloadBtn').click(function(){
			var t = $('#uploadTime').val();
			if(!t)
				return myutil.emsg("导出时间不能为空！");
			t = t.split(' ~ ');
			myutil.getData({
				url : myutil.config.ctx + '/apiExtOrder/list/goods',
				data:{
					dateBegin: t[0],
					dateEnd: t[1],
				},
				success:function(data){
					var newData = [];
					for(var i in data){
						var d = data[i];
						var name = d["property"];
						if(name){
							name = name.split('型号:')[1];
							name = name.replace(/,/g,"-");
						}else
							name = d.goodsName;
						newData.push([
							d.dateAdd,
							name,
							d.amountSingle,
							d.number,
							d.amount,
						])
					}
					console.log(newData)
					table.exportFile(['下单日期','商品名','单价','数量','金额',], newData, 'xls'); 
				}
			})
		})
	}//end define function
)//endedefine
</script>
</html>