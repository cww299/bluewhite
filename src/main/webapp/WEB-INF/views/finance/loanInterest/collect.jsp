<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>利息汇总</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>

<body>
	<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>借款方:</td>
							<td><input type="text" name="customerName" id="firstNames" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td><select class="layui-input" name="selectone" id="selectone">
									<option value="expenseDate">申请日期</option>
								</select></td>
							<td>&nbsp;&nbsp;</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入时间" class="layui-input laydate-icon"></td>
							<td>&nbsp;&nbsp;</td>
							<td>需要支付总额:</td>
							<td><input type="text" id="allPrice" disabled class="layui-input"  /></td>
							<td>&nbsp;&nbsp;</td>
							<td><button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">
									<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
								</button></td>
							<td>&nbsp;&nbsp;</td>
							<td><button class="layui-btn" id="uploadData"><i class="layui-icon">&#xe67c;</i>导入数据</button></td>
						</tr>
					</table>
				</div>
			</div>
			<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
		</div>
	</div>
<script>
	layui.config({
		base: '${ctx}/static/layui-v2.4.5/'
	}).extend({
		tablePlug: 'tablePlug/tablePlug'
	}).define(
		['tablePlug', 'laydate',],
		function() {
			var $ = layui.jquery,
				layer = layui.layer,
				form = layui.form,
				table = layui.table,
				laydate = layui.laydate,
				tablePlug = layui.tablePlug,
				upload = layui.upload;
			laydate.render({ elem: '#startTime', type: 'datetime', range: '~', });
		   	
			table.render({
				elem: '#tableData',
				size: 'lg',
				height:'700px',
				url: '${ctx}/fince/getConsumption?type=10' ,
				where:{ flag:0, },
				request:{
					pageName: 'page' ,
					limitName: 'size' 
				},
				page: {},
				loading: true,
				cellMinWidth: 90,
				colFilterRecord: true,
				smartReloadModel: true,// 开启智能重载
				parseData: function(ret) {
					$('#allPrice').val(ret.data.statData.statAmount)
					return {
						code: ret.code,
						msg: ret.message,
						count:ret.data.total,
						data: ret.data.rows
					}
				},
				cols:[[
				       { align: 'center', field: "withholdReason", 	title: "借款方", 		templet: function(d){ return d.custom.name } },
				       { align: 'center', field: "content", 		title: "内容", },
					   { align: 'center', field: "money", 			title: "报销申请金额", },
					   { align: 'center', field: "expenseDate", 	title: "报销申请日期", },
					   { align: 'center', field: "flag", 			title: "审核状态", 		templet:  function(d){ return d.flag==0?'未审核':'已审核';} }
					 ]],
			});

			form.on('submit(LAY-search)', function(data) {
				var field = data.field;
				var orderTime=field.orderTimeBegin.split('~');
				orderTimeBegin=orderTime[0];
				orderTimeEnd=orderTime[1];
				var post={
					customerName:field.customerName,
					flag:field.flag,
					orderTimeBegin:orderTimeBegin,
					orderTimeEnd:orderTimeEnd,
					expenseDate:"2019-05-08 00:00:00",
				}
				table.reload('tableData', {
					where: post
				});
			});
			
			
			$('#uploadData').on('click',function(){
				layer.msg('尚未完善！',{icon:2});
			})
			//报错？！！
		    var load;		
			upload.render({
			   	  elem: '#uploadData',
			   	  url: '${ctx}/excel/importActualprice',
			 	  before: function(obj){ 
			 		 load = layer.load(3); 
				  },
				  done: function(res, index, upload){ //上传后的回调
			   		layer.closeAll();
			   		layer.msg(res.message, {icon: 1});
			   		table.reload('tableData');
			   	  },
			   	  accept: 'file',
			   	  field:'file'
			}) 
		}
	)
</script>
</body>

</html>