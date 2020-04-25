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
				<td>下单时间：</td>
				<td><input id="searchTime" class="layui-input" name="dateAddBegin"></td>
				<td>状态：</td>
				<td><select name="status">
					<option value="">订单状态</option>
					<option value="-1">关闭订单</option>
					<option value="0">待支付</option>
					<option value="1">已支付待发货</option>
					<option value="2">已发货待确认</option>
					<option value="3"> 确认收货待评价</option>
					<option value="4">已评价 </option>
					</select></td>
				<td><span class="layui-btn layui-btn-" lay-submit lay-filter="searchBtn">搜索</span></td>
				<td></td>
				<td><span class="layui-btn layui-btn-primary" id="downloadBtn">导出销售明细</span></td>
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
	['mytable','laydate','form'],
	function(){
		var $ = layui.jquery
		,laydate = layui.laydate
		,table = layui.table
		,mytable = layui.mytable
		,form = layui.form
		,myutil = layui.myutil;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({
			elem:'#searchTime',range:'~'
		})
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/apiExtOrder/list',
			request:{ pageName: 'page' ,limitName: 'pageSize'},
			parseData:function(ret){
				if(ret.code==0){
					for(var i in ret.data.apiExtUserMap){
						var user = ret.data.apiExtUserMap[i];
						for(var j in ret.data.result){
							var order = ret.data.result[j];
							if(order.uid==i){
								order.user = user;
								break;
							}
						}
					}
					return {  msg:ret.message,  code:ret.code , data:ret.data.result, 
						count:ret.data.totalRow }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			},
			limit:50,
			cols:[[
				{ type:'checkbox', },
				{ title:'用户信息', field:'user_nick', },
				{ title:'订单数/订单号', field:'orderNumber', },
				{ title:'状态', field:'statusStr', },
				{ title:'价格', field:'amount', },
				{ title:'实收', field:'amountReal', },
				{ title:'下单时间', field:'dateAdd', },
			]]
		})
		$('#downloadBtn').click(function(){
			layer.open({
				type:1,
				title:'导出销售明细',
				area:['300px','200px'],
				btn:['确定','取消'],
				content:[
					'<div style="padding:10px;">',
						'<input id="uploadTime" class="layui-input" placeholder="导出时间">',
					'</div>'
				].join(''),
				success:function(){
					laydate.render({
						elem:'#uploadTime',range:'~'
					})
				},
				yes:function(){
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
							data = data.result;
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
							table.exportFile(['下单日期','商品名','单价','数量','金额',], newData, 'xls'); 
						}
					})
				}
			})
		})
		form.on('submit(searchBtn)',function(obj){
			var f = obj.field;
			if(f.dateAddBegin){
				var t = f.dateAddBegin.split(' ~ ');
				f.dateAddBegin = t[0];
				f.dateAddEnd = t[1];
			}else
				f.dateAddEnd = "";
			table.reload('tableData',{
				where: f,
				page:{ curr:1 },
			})
		})
	}//end define function
)//endedefine
</script>
</html>