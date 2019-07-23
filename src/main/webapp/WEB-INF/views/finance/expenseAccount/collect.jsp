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
<title>财务汇总</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
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
					<td>报销人:</td>
					<td><input type="text" name="Username" id="firstNames" class="layui-input" /></td>
					<td>&nbsp;&nbsp;</td>
					<td>报销内容:</td>
					<td><input type="text" name="content" class="layui-input" /></td>
					<td>&nbsp;&nbsp;</td>
					<td>报销金额:</td>
					<td><input type="text" name="money" class="layui-input" /></td>
					<td>&nbsp;&nbsp;</td>
					<td><select class="layui-input" name="selectone" id="selectone">
							<option value="expenseDate">申请日期</option>
							</select></td>
					<td>&nbsp;&nbsp;</td>
					<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input"></td>
					<td><button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">搜索</button></td>
					<td>&nbsp;&nbsp;</td>
					<td>未支付总额:
					<td><span id="allPrice" style="color:red;font-size: 20px;"></span></td>
				</tr>
				<tr style="height:5px;"></tr>
				<tr>
					<td>是否预算:</td>
					<td style="width:100px;"><select name="budget">
													<option value="">请选择</option>
													<option value="0">否</option>
													<option value="1">是</option></select></td>
					<td>&nbsp;&nbsp;</td>
					<td>是否审核:</td>
					<td style="width:100px;"><select name="flags">
												<option value="">请选择</option>
												<option value="0">审核</option>
												<option value="1">未审核</option>
												<option value="2">部分审核</option></select></td>
				</tr>
			</table>
			<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
		</div>
	</div>
	<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug'
			}).define(
				['tablePlug', 'laydate'],
				function() {
					var $ = layui.jquery,
						layer = layui.layer,
						form = layui.form,
						table = layui.table,
						laydate = layui.laydate,
						tablePlug = layui.tablePlug;
					var allField;
					var htmls = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
					});
					laydate.render({
						elem: '#endTime',
						type: 'datetime',
					});
				 
					$.ajax({
						url: '${ctx}/system/user/findAllUser',
						type: "GET",
						async: false,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							$(result.data).each(function(i, o) {
								htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
							})
							layer.close(index);
						},
						error: function() {
							layer.msg("操作失败！", {
								icon: 2
							});
							layer.close(index);
						}
					});
					
					(function(){
						$.ajax({
							url:'${ctx}/fince/totalAmount?type=1',
							success:function(r){
								if(r.code==0){
									$('#allPrice').html(r.data);
								}
							}
						})
					})();
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						height:'700px',
						url: '${ctx}/fince/getConsumption' ,
						where:{
							flags:'0,2',
							type:1
						},
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
							
						},//开启分页
						loading: true,
						toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						/*totalRow: true //开启合计行 */
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								count:ret.data.total,
								data: ret.data.rows
							}
						},
						cols: [
							[{
								type: 'checkbox',
								fixed: 'left'
							}, {
								field: "content",
								title: "报销内容",
							}, {
								field: "userId",
								title: "报销人",
								search: true,
								edit: false,
								type: 'normal',
								templet: function(d){
									return d.user.userName;
								}
							}, {
								field: "budget",
								title: "是否预算",
								search: true,
								edit: false,
								type: 'normal',
								templet: function(d){
									if(d.budget==0){
										return "";
									}
									if(d.budget==null){
										return "";
									}
									if(d.budget==1){
										return "预算";
									}
								}
							}, {
								field: "money",
								title: "报销申请金额",
							}, {
								field: "expenseDate",
								title: "报销申请日期",
							}, {
								field: "withholdReason",
								title: "扣款事由",
							}, {
								field: "withholdMoney",
								title: "扣款金额",
							}, {
								field: "settleAccountsMode",
								title: "结款模式",
								search: true,
								edit: false,
								templet:  function(d){
									if(d.settleAccountsMode==0){
										return "";
									}
									if(d.settleAccountsMode==null){
										return "";
									}
									if(d.settleAccountsMode==1){
										return "现金";
									}
									if(d.settleAccountsMode==2){
										return "月结";
									}
								}
							}, {
								field: "flag",
								title: "审核状态",
								templet:  function(d){
									var text = "";
									if(d.flag==0)
										text = "未审核";
									else if(d.flag==1)
										text = "已审核";
									else if(d.flag==2)
										text = "部分审核";
									return text;
								}
							}]
						],
								});

				
					


					//监听搜索
					form.on('submit(LAY-search)', function(data) {
						var field = data.field;
						var orderTime=field.orderTimeBegin.split('~');
						orderTimeBegin=orderTime[0];
						orderTimeEnd=orderTime[1];
						var post={
							Username:field.Username,
							flags:field.flags,
							orderTimeBegin:orderTimeBegin,
							orderTimeEnd:orderTimeEnd,
							expenseDate:"2019-05-08 00:00:00",
							content:field.content,
							money:field.money,
							budget:field.budget,
						}
						table.reload('tableData', {
							where: post,
							page:{curr:1}
						}); 
					});
					

				}
			)
		</script>
</body>

</html>