<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
	<!--<![endif]-->

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>报销申请</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	</head>

	<body>
		<section id="main-wrapper" class="theme-default">
			<%@include file="../../decorator/leftbar.jsp"%> 
			<section id="main-content" class="animated fadeInUp">
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">工具表</h3>
								<div class="actions pull-right">
									<i class="fa fa-expand"></i>
									<i class="fa fa-chevron-down"></i>
								</div>
							</div>
							<table>
							<tr>
							<td>
									<button type="button" class="layui-btn" name="file" id="test1">
		 									 <i class="layui-icon">&#xe67c;</i>上传文件
									</button>
							</td>
							<td>&nbsp&nbsp</td>
							<td>输入当前月份:</td>
												<td>
													<input id="startTime" name="orderTimeBegin" placeholder="请输入开始时间" class="form-control laydate-icon">
												</td>
							</tr>
							</table>
							<div class="panel-body">
								<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
							</div>

						</div>
					</div>
				</div>
			</section>
		</section>
		</section>

		<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
		<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug'
			}).define(
				['tablePlug', 'laydate', 'element','upload'],
				function() {
					var $ = layui.jquery,
						layer = layui.layer //弹层
						,
						form = layui.form //表单
						,
						table = layui.table //表格
						,
						laydate = layui.laydate //日期控件
						,
						tablePlug = layui.tablePlug //表格插件
						,
				 		upload = layui.upload,
						element = layui.element;
					var a;
					laydate.render({
						elem: '#startTime',
						type : 'month',
						format:'yyyy-MM-01 HH:mm:ss',
						done: function(value, date) {
							a=value;
							upload.render({
							   	  elem: '#test1'
							   	  ,url: '${ctx}/excel/importActualprice'
							   	  ,data: {
							   		currentMonth:a
							   		}
							   	  ,done: function(res, index, upload){ //上传后的回调
							   		layer.msg(res.message, {icon: 1});
							   		table.reload('tableData', {
									});
							   	  } 
							   	  ,accept: 'file' //允许上传的文件类型
							   	  ,field:'file'
							   	  //,size: 50 //最大允许上传的文件大小
							   	  //,……
							   	})
						}
					});
				   	tablePlug.smartReload.enable(true); 
				   	
					table.render({
						elem: '#tableData',
						size: 'lg',
						url: '${ctx}/fince/actualprice',
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
							limit:15
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
								align: 'center',
								fixed: 'left'
							}, {
								field: "batchNumber",
								title: "产品批次",
								align: 'center',
							}, {
								field: "productName",
								title: "产品名",
								align: 'center',
							}, {
								field: "budgetPrice",
								title: "预算成本",
								align: 'center',
							}, {
								field: "combatPrice",
								title: "实战成本",
								align: 'center',
							}]
						],
								});

				}
			)
		</script>
	</body>

</html>