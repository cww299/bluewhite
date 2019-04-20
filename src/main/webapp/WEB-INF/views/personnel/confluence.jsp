<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>

<html class="no-js">
	<link rel="stylesheet" href="${ctx }/static/css/font-awesome.min.css">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script> 
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/autoheight.js"></script>   
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>考勤详细</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

</head>

<body>

<div class="layui-card">
	<div class="layui-card-header">
		<ul>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #9e9e1f"></i>迟到</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #bf1515"></i>缺勤</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #00b0ff"></i>事假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #13161c"></i>病假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #b8c2d6"></i>丧假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #da06af"></i>婚假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #13a8bd"></i>产假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #1211e2"></i>护理假</li>
		</ul>
	</div>
	<div class="layui-card-body">
		<div class="layui-form ">
			<div class="layui-form-item">
				<table>
					<tr>
						<td>人员:</td>
						<td id="firstNames"></td>
						<td>&nbsp&nbsp</td>
						<td>部门:</td>
						<td id="orgNameId"></td>
						<td>&nbsp&nbsp</td>
						<td>考勤汇总月份:</td>
						<td><input name="orderTimeBegin" id="startTime" lay-verify="required" style="width: 200px;" placeholder="请输入考勤汇总月份" class="layui-input laydate-icon">
						</td>
						<td>&nbsp&nbsp</td>
						<td>
							<div class="layui-inline">
								<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-search">
									初始化考勤机 <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
								</button>
							</div>
							<div class="layui-inline">
								<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-searche">
									计算后的考勤 <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
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
							var loading;
							$.ajax({
								url : '${ctx}/system/user/findAllUser',
								type : "GET",
								async : false,
								beforeSend : function() {
									index;
								},
								success : function(result) {
									htmls='<select name="userId" class="layui-input"  lay-search="true"><option value="">请选择</option>';
									$(result.data)
											.each(
													function(i, o) {
														htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
													})
									layer.close(index);
									htmls+='</select>';
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
											var htmlth = '<select name="orgNameId" id="selectOrgNameId" class="layui-input" lay-search="true" ><option value="">请选择</option>'
														+ htmlfr + '</select>'
											$("#orgNameId").html(htmlth);
											form.render('select');
											layer.close(index);
										}
									});
							
							form.on('submit(LAY-role-search)', function(data) {
								if($("#firstNames").val()==""){
									if($("#selectOrgNameId").val()==""){
										return layer.msg("请输入人员 或者 部门", {icon: 2});
									}
								}
								var field=data.field
								var postUrl='${ctx}/personnel/intAttendanceTime'
								even(postUrl,field)
								
							})
							
							form.on('submit(LAY-role-searche)', function(data) {
								if($("#firstNames").val()==""){
									if($("#selectOrgNameId").val()==""){
										return layer.msg("请输入人员 或者 部门", {icon: 2});
									}
								}
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
											height:500,
											method : 'POST',
											loading:true,
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
														   title: '提示'
														  ,content:res.msg
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
															loading = layer.load(0, {
											                    shade: false,
											                });
											       			 }
														}); 
												}
												layer.close(loading)
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
												var ID;
												$.each(data[0].attendanceTimeData,function(i, v) {
													ID = v.id;
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
																templet : function(d) {
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
																templet : function(d) {
																	return d.collect.dutyWork
																}
															};
															list[length + 4] = {
																	align : 'center',
																	title : '<span style="color:red">调休</span>',
																	fixed : 'right',
																	width : 70,
																	style : 'background-color: #5FB878;color: #fff',
																	rowspan : 3,
																	templet : function(d) {
																		return d.collect.takeWork
																	}
																};
															list[length + 5] = {
																align : 'center',
																title : '<span style="color:red">总出勤</span>',
																fixed : 'right',
																width : 80,
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
																templet : function(d) {
																	if (d.attendanceTimeData[i].dutytime == 0)
																		return '';
																	else
																		var colo;
																		if(d.attendanceTimeData[i].flag == 0){
																			colo='#fff';
																		}
																		if(d.attendanceTimeData[i].flag ==1){
																			colo='#bf1515';
																		}
																		if(d.attendanceTimeData[i].flag==2){
																			if(d.attendanceTimeData[i].holidayType==0){
																				colo='#00b0ff';
																			}
																			if(d.attendanceTimeData[i].holidayType==1){
																				colo='#13161c';
																			}
																			if(d.attendanceTimeData[i].holidayType==2){
																				colo='#b8c2d6';
																			}
																			if(d.attendanceTimeData[i].holidayType==3){
																				colo='#da06af';
																			}
																			if(d.attendanceTimeData[i].holidayType==4){
																				colo='#13a8bd';
																			}
																			if(d.attendanceTimeData[i].holidayType==5){
																				colo='#1211e2';
																			}
																		}
																		return '<div style="background-color:'+colo+';color: #fff">'+d.attendanceTimeData[i].dutytime+'</div>';
																}
															}
															d = {
																	align : 'center',
																	title : '迟到(M)',
																	width : 80,
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
													height:750,
													cols : list2,
													data : res.data,
													limit:500,
													loading:true,
												});
											},
											page : false
										});
							}
						
							
						})
	</script>
</body>
<script type="text/javascript">
$(function(){
	  var height = $(document).height();
		window.parent.changeHeight(height);
		$("body").bind('resize',function(){
		    var height = $(document).height();
			window.parent.changeHeight(height);
		});
}); 
</script>
</html>