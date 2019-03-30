<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>考勤总汇</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
</head>

<body>

	<section id="main-wrapper" class="theme-default">
		<%@include file="../decorator/leftbar.jsp"%>
		<!--main content start-->
		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<table><tr>    
							 <td><font size="8" style="background-color:#9e9e1f;color: #fff">迟到</font></td>
							 <td><td>&nbsp&nbsp</td></td>
							 <td><font size="8" style="background-color:red;color: #fff">缺勤</font></td>
							 </tr></table>
							  </div>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i> <i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="panel-body">
							<div class="layui-form layui-card-header layuiadmin-card-header-auto">
								<div class="layui-form-item">
									<table>
										<tr>
											<td>人员:</td>
											<td>
												<select name="userId" class="form-control search-query name" id="firstNames"
													lay-search="true"></select>
											</td>
											<td>&nbsp&nbsp</td>
											<td>部门:</td>
											<td id="orgNameId"></td>
											<td>&nbsp&nbsp</td>
											<td>考勤汇总月份:</td>
											<td>
												<input name="orderTimeBegin" id="startTime" style="width: 200px;" placeholder="请输入考勤汇总月份"
													class="form-control laydate-icon">
											</td>
											<td>&nbsp&nbsp</td>
											<td>
												<div class="layui-inline">
													<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-search">
													 初始化考勤机
														<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
													</button>
												</div>
												<div class="layui-inline">
													<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-searche">
													计算后的考勤
														<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
													</button>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<table class="layui-hide" lay-filter="test3" id="test">

							</table>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>

	</section>



	<script src="${ctx }/static/js/laydate/laydate.js"></script>
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>

	<script>
		layui.config({
					base : '${ctx}/static/layui-v2.4.5/'
				})
				.extend({
					tablePlug : 'tablePlug/tablePlug'
				})
				.define(
						[ 'table', 'laydate', 'element', 'form' ],
						function() {
							var $ = layui.jquery, layer = layui.layer //弹层
							, form = layui.form //表单
							, table = layui.table //表格
							, laydate = layui.laydate //日期控件
							, tablePlug = layui.tablePlug //表格插件
							, element = layui.element;

							laydate.render({
								elem : '#startTime',
								type : 'month',
								format:'yyyy-MM-01 HH:mm:ss'
							});
							var htmls = '<option value="">请选择</option>';
							var index = layer.load(1, {
								shade : [ 0.1, '#fff' ]
							//0.1透明度的白色背景
							});
							$.ajax({
								url : '${ctx}/system/user/findAllUser',
								type : "GET",
								async : false,
								beforeSend : function() {
									index;
								},
								success : function(result) {
									$(result.data)
											.each(
													function(i, o) {
														htmls += '<option value=' + o.id + '>'
																+ o.userName
																+ '</option>'
													})
									layer.close(index);
									$("#firstNames").html(htmls);
									form.render();
								},
								error : function() {
									layer.msg("操作失败！", {
										icon : 2
									});
									layer.close(index);
								}
							});

							var getdata = {
								type : "orgName",
							}
							var htmlfr = ""
							$.ajax({
										url : "${ctx}/basedata/list",
										data : getdata,
										type : "GET",
										beforeSend : function() {
											index;
										},
										success : function(result) {
											$(result.data).each(
												function(k, j) {
													htmlfr += '<option value="'+j.id+'">'+ j.name+ '</option>'
												});
											var htmlth = '<select name="orgNameId" id="selectOrgNameId" class="form-control"><option value="">请选择</option>'
														+ htmlfr + '</select>'
											$("#orgNameId").html(htmlth);
											form.render('select');
											layer.close(index);
										}
									});
							
							form.on('submit(LAY-role-search)', function(data) {
								var field=data.field
								var postUrl='${ctx}/personnel/intAttendanceTime'
								even(postUrl,field)
							})
							
							form.on('submit(LAY-role-searche)', function(data) {
								var field={
										userId:data.field.userId,
										orgNameId:data.field.orgNameId,
										orderTimeBegin:data.field.orderTimeBegin,
										sign:1,
								}
								var postUrl='${ctx}/personnel/addAttendanceTime'
								even(postUrl,field)
							})
							var even = function(url, data) {
								table.render({
											elem : '#test',
											size:'sm',
											url : url,
											where : data,
											cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
											method : 'POST',
											parseData : function(res) { //res 即为原始返回的数据
												return {
													"code" : res.code, //解析接口状态
													"msg" : res.message, //解析提示文本
													"data" : res.data,
													"countd":0
												//解析数据列表
												};
											},
											cols : [],
											done : function(res, curr, count) {
												if(res.code==2){
													layer.open({
														   title: '在线调试'
														  ,content:'考勤已经汇总，是否覆盖'
														  ,btn: ['确认', '取消']
														,yes: function(index, layero){
															var field={
																	userId:$('#firstNames').val(),
																	orgNameId:$('#selectOrgNameId').val(),
																	orderTimeBegin:$('#startTime').val(),
																	sign:2,
															}
															var postUrl='${ctx}/personnel/addAttendanceTime'
															even(postUrl,field)
															layer.closeAll();
											       			 }
														}); 
												}
												var data = res.data;
												var list = [];
												var list1 = [];
												var list2 = [];
												var list3 = [];
												var a;
												var b;
												var c;
												var d;
												var length = data[0].attendanceTimeData.length
												$.each(data[0].attendanceTimeData,
														function(i, v) {
															list[0] = {
																align : 'center',
																title : '<span style="color:red">姓名</span>',
																width : 80,
																fixed : 'left',
																style : 'background-color: #5FB878;color: #fff',
																rowspan : 3,
																templet : function(
																		d) {
																	return d.attendanceTimeData[i].userName
																}
															};
															list[length + 1] = {
																align : 'center',
																title : '<span style="color:red">出勤</span>',
																fixed : 'right',
																width : 70,
																style : 'background-color: #5FB878;color: #fff',
																rowspan : 3,
																templet : function(
																		d) {
																	return '<span id="1">'+d.collect.turnWork+'<span>'
																}
															};
															list[length + 2] = {
																align : 'center',
																title : '<span style="color:red">加班</span>',
																fixed : 'right',
																width : 70,
																style : 'background-color: #5FB878;color: #fff',
																rowspan : 3,
																templet : function(
																		d) {
																	return d.collect.overtime
																}
															};
															list[length + 3] = {
																align : 'center',
																title : '<span style="color:red">缺勤</span>',
																fixed : 'right',
																width : 70,
																style : 'background-color: #5FB878;color: #fff',
																rowspan : 3,
																templet : function(
																		d) {
																	return d.collect.dutyWork
																}
															};
															list[length + 4] = {
																align : 'center',
																title : '<span style="color:red">总出勤</span>',
																fixed : 'right',
																width : 70,
																style : 'background-color: #5FB878;color: #fff',
																rowspan : 3,
																templet : function(
																		d) {
																	return d.collect.allWork
																}
															};
															list[i + 1] = {
																align : 'center',
																title : v.time,
																colspan : 4
															};
															list1[i] = {
																align : 'center',
																title : v.week,
																colspan : 4
															};
															a = {
																align : 'center',
																title : '出勤',
																edit: 'text',
																templet : function(d) {
																	if (d.attendanceTimeData[i].turnWorkTime == 0)
																		return '';
																	else
																		return d.attendanceTimeData[i].turnWorkTime;
																}
															}
															b = {
																align : 'center',
																title : '加班',
																edit: 'text',
																templet : function(
																		d) {
																	if (d.attendanceTimeData[i].overtime == 0)
																		return '';
																	else
																		return d.attendanceTimeData[i].overtime;
																}
															};
															c = {
																align : 'center',
																title : '缺勤',
																edit: 'text',
																templet : function(d) {
																	if (d.attendanceTimeData[i].dutytime == 0)
																		return '';
																	else
																		var colo;
																		if(d.attendanceTimeData[i].holidayType == 0){
																			
																		}
																		return '<div style="background-color:'+colo+';color: #fff">'+d.attendanceTimeData[i].dutytime+'</div>';
																}
															}
															d = {
																	align : 'center',
																	title : '迟到(M)',
																	width : 80,
																	edit: 'text',
																	
																	templet : function(d) {
																		if (d.attendanceTimeData[i].belateTime == 0)
																			return '';
																		else
																			return '<div style="background-color:#9e9e1f;color: #fff">'+d.attendanceTimeData[i].belateTime+'</div>';
																	}
																}
															list3.push(a);
															list3.push(b);
															list3.push(c);
															list3.push(d);
														});

												list2.push(list)
												list2.push(list1)
												list2.push(list3)
												table.init('test3', {
													cols : list2,
													data : res.data,
													
												});
											},
											page : false
										});
							}
							 table.on('row(test3)', function (obj) {
								 alert(1)
			                        var index = $("tr").index(obj.tr);
			                        console.log(index)
			                    });
							
							table.on('edit(test3)', function(obj) {
								console.log(obj.field)
								console.log(obj.data)
								var value = obj.value ,//得到修改后的值
									data = obj.data ,//得到所在行所有键值
									field = obj.field, //得到字段
									id = data.id;
								console.log(data.parents())
							});
							
							
							
							
						})
	</script>

</body>

</html>