<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">

<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<script src="${ctx }/static/js/shujuhuixian/sjhx.js"></script> 
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>考勤初始设定</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>

<body>

<div class="layui-card">
	<div class="layui-card-body">
		<div
			class="layui-form">
			<div class="layui-form-item">
				<table>
					<tr>
						<td>人员:</td>
						<td><select id="userId" class="layui-input"  lay-search="true" name="userId"></select></td>
						<td>&nbsp;&nbsp;</td>
						<td>部门:</td>
						<td>
							<select  id="orgNameId" class="layui-input" lay-search="true" name="orgNameId">
								<option value="">请选择</select></td>
						<td>
							<div class="layui-inline">
								<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-search">
									<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
								</button>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>

		<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
	</div>

</div>
	
	<form action="" id="layuiadmin-form-admin"
		style="padding: 20px 30px 0 60px; display: none; text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<input type="text" name="id" id="usID" style="display:none;">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">员工姓名</label>
				<div class="layui-input-inline">
					<select name="userId" lay-filter="lay_selecte"
						lay-verify="required" id="selectOne" lay-search="true"><option value="">请选择</option></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">休息方式</label>
				<div class="layui-input-inline">
					<select name="restType" lay-filter="restType" id="restType"
						lay-verify="required" lay-search="true"><option value="1">周休一天</option>
						<option value="2">月休两天，其他周日算加班</option>
						<option value="3">全年无休</option></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">出勤方式</label>
				<div class="layui-input-inline">
					<select name="workType" lay-filter="workType" id="workType"
						lay-verify="required" lay-search="true"><option value="0">请选择</option>
						<option value="1">无到岗要求</option>
						<option value="2">无打卡要求</option>
						<option value="3">按到岗小时计算</option></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">早到加班</label>
				<div class="layui-input-block">
					<input type="checkbox" name="earthWork" value="true" id="kai"
						lay-text="是|否" lay-skin="switch">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">约定休息时间</label>
				<div class="layui-input-inline">
					<input type="text" name="applytime" id="applytime"
						placeholder="请输入申请时间" class="layui-input laydate-icon">
					<td>&nbsp;&nbsp;</td>
					<div>
						<textarea name="restDay" id="inputapplytime"
							class="layui-textarea"></textarea>
					</div>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">夏令时工作区间</label>
				<div class="layui-input-inline">
					<input type="text" name="workTimeSummer" id="workTimeSummer"
						lay-verify="required" placeholder="请输入夏令时工作区间"
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">冬令时工作区间</label>
				<div class="layui-input-inline">
					<input type="text" name="workTimeWinter" id="workTimeWinter"
						lay-verify="required" placeholder="请输入冬令时工作区间"
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">夏令出勤时长</label>
				<div class="layui-input-inline">
					<input type="text" name="turnWorkTimeSummer"
						id="turnWorkTimeSummer" lay-verify="required"
						placeholder="请输入夏令出勤时长 " class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">冬令出勤时长</label>
				<div class="layui-input-inline">
					<input type="text" name="turnWorkTimeWinter"
						id="turnWorkTimeWinter" lay-verify="required"
						placeholder="请输入冬令出勤时长 " class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">夏令时午休区间</label>
				<div class="layui-input-inline">
					<input type="text" name="restTimeSummer" id="restTimeSummer"
						lay-verify="required" placeholder="请输入夏令时午休区间"
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">冬令时午休区间</label>
				<div class="layui-input-inline">
					<input type="text" name="restTimeWinter" id="restTimeWinter"
						lay-verify="required" placeholder="请输入冬令时午休区间"
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">夏令午休时长</label>
				<div class="layui-input-inline">
					<input type="text" name="restSummer" id="restSummer"
						lay-verify="required" placeholder="请输入夏令午休时长 "
						class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">冬令午休时长</label>
				<div class="layui-input-inline">
					<input type="text" name="restWinter" id="restWinter"
						lay-verify="required" placeholder="请输入冬令午休时长" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">午休状态</label>
				<div class="layui-input-inline">
					<select name="restTimeWork" lay-filter="restTimeWork"
						id="restTimeWork" lay-verify="required" lay-search="true"><option
							value="1">默认休息</option>
						<option value="2">出勤</option>
						<option value="3">加班</option></select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">核算加班</label>
				<div class="layui-input-inline">
					<select name="overTimeType" id="overTimeType"
						lay-filter="overTimeType" lay-verify="required" lay-search="true"><option
							value="1">看加班申请</option>
						<option value="2">打卡核算</option></select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">加班后到岗</label>
				<div class="layui-input-inline">
					<select name="comeWork" id="comeWork" lay-filter="comeWork"
						lay-verify="required" lay-search="true"><option value="1">按点上班</option>
						<option value="2">第二天上班时间超过24:00往后推</option>
						<option value="3">超过24:30后默认休息7.5小时</option></select>
				</div>
			</div>
		</div>
	</form>

	<form action="" id="layuiadmin-form-admin2"
		style="padding: 20px 30px 0 15px; display: none; text-align:">
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">周休一天</label> <input
				type="text" name="id" value="1" style="display:none;">
			<div class="layui-input-inline">
				<input type="text" id="weekly" placeholder="请输入周休一天的设定时间"
					class="layui-input laydate-icon">
				<td>&nbsp;&nbsp;</td>
				<div>
					<textarea name="keyValue" id="weeklyRestDate"
						class="layui-textarea"></textarea>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">月休2天</label>
			<div class="layui-input-inline">
				<input type="text" id="month"
					placeholder="请输入月休2天的设定时间" class="layui-input laydate-icon">
				<td>&nbsp;&nbsp;</td>
				<div>
					<textarea name="keyValueTwo" id="monthRestDate"
						class="layui-textarea"></textarea>
				</div>
			</div>
		</div>
	</form>
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="notice">新增</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
				<span class="layui-btn layui-btn-sm" lay-event="set">设定休息时间</span>
			</div>
		</script>
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-trans layui-btn-xs"  lay-event="update">编辑</a>
</script>
	
	<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug'
			}).define(
				['tablePlug', 'laydate', 'element','form'],
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
						element = layui.element;

					//select全局变量
					var htmls = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					laydate.render({
						elem: '#workTimeSummer',
						range : '-',
						type: 'time',
					});
					laydate.render({
						elem: '#workTimeWinter',
						range : '-',
						type: 'time',
					});
					laydate.render({
						elem: '#restTimeSummer',
						range : '-',
						type: 'time',
					});
					laydate.render({
						elem: '#restTimeWinter',
						range : '-',
						type: 'time',
					});
					var timeAll='';
					laydate.render({
						elem: '#applytime',
						format: 'yyyy-MM-dd',
						done: function(value, date) {
							var c=$('#inputapplytime').val()
							timeAll=(timeAll==''? value:(c+','+value));
							$("#inputapplytime").val(timeAll)
						}
					});
					var timeAll2='';
					laydate.render({
						elem: '#weekly',
						format: 'yyyy-MM-dd',
						done: function(value, date) {
							timeAll2=(timeAll2==''? value:(timeAll2+','+value));
							$("#weeklyRestDate").val(timeAll2)
						}
					});
					var timeAll3='';
					laydate.render({
						elem: '#month',
						format: 'yyyy-MM-dd',
						done: function(value, date) {
							timeAll3=(timeAll3==''? value:(timeAll3+','+value));
							$("#monthRestDate").val(timeAll3)
						}
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
						$('#selectOne').html(htmls);
						$("#userId").append(htmls);
						},
						error: function() {
							layer.msg("操作失败！", {
								icon: 2
							});
							layer.close(index);
						}
					});
					
					var getdata = {
							type : "orgName",
						}
					var htmlfr=""
						$.ajax({
							url : "${ctx}/basedata/list",
							data : getdata,
							type : "GET",
							beforeSend : function() {
								index;
							},
							success : function(result) {
								$(result.data).each(function(k, j) {
									htmlfr += '<option value="'+j.id+'">' + j.name + '</option>'
								});
								
								$("#orgNameId").append(htmlfr);
								layer.close(index);
							}
						});

					
					
					
				 	/* tablePlug.smartReload.enable(true);  */
					table.render({
						elem: '#tableData',
						size: 'lg',
						height:'700px',
						url: '${ctx}/personnel/findAttendanceInit',
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
						} //开启分页
						,
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
								field: "",
								title: "员工姓名",
								align: 'center',
								fixed: 'left',
								search: true,
								edit: false,
								templet: function(d){
									return d.user.userName;
								}
							}, {
								field: "",
								title: "休息方式",
								align: 'center',
								edit: false,
								templet: function(d){
									if(d.restType==1){
										return "周休一天";
									}
									if(d.restType==2){
										return "月休两天,其他周日算加班";
									}
									if(d.restType==3){
										return "全年无休";
									}
								}
							}, {
								field: "",
								title: "出勤方式",
								align: 'center',
								edit: false,
								templet: function(d){
									if(d.workType==0){
									return "";
									}
									if(d.workType==1){
										return "无到岗要求";
									}
									if(d.workType==2){
										return "无打卡要求";
									}
									if(d.workType==3){
										return "按到岗小时计算";
									}
								}
							},
							{
								field: "restDay",
								align: 'center',
								title: "约定休息日",
							},{
								field: "workTimeSummer",
								align: 'center',
								width:170,
								title: "夏令时工作区间",
							},{
								field: "workTimeWinter",
								width:170,
								align: 'center',
								title: "冬令时工作区间",
							},{
								field: "turnWorkTimeSummer",
								width:150,
								align: 'center',
								title: "夏令出勤时长",
							},{
								field: "turnWorkTimeWinter",
								width:150,
								align: 'center',
								title: "冬令出勤时长",
							},{
								field: "restTimeSummer",
								width:150,
								align: 'center',
								title: "夏令时午休区间",
							},{
								field: "restTimeWinter",
								width:150,
								align: 'center',
								title: "冬令时午休区间",
							},{
								field: "restSummer",
								width:150,
								align: 'center',
								title: "夏令时午休时长",
							},{
								field: "restWinter",
								width:150,
								align: 'center',
								title: "冬令时午休时长",
							},{
								field: "restTimeWork",
								align: 'center',
								width:150,
								title: "午休状态",
								templet: function(d){
									if(d.restTimeWork==1){
									return "休息";
									}
									if(d.restTimeWork==2){
										return "出勤";
									}
									if(d.restTimeWork==3){
										return "加班";
									}
								}
							},{
								field: "restTimeWork",
								align: 'center',
								width:150,
								title: "早到加班",
								templet: function(d){
									if(d.earthWork==false){
									return "否";
									}
									if(d.restTimeWork==true){
										return "是";
									}
								}
							},{
								field: "overTimeType",
								align: 'center',
								title: "核算加班",
								width:150,
								templet: function(d){
									if(d.overTimeType==1){
									return "加班申请";
									}
									if(d.overTimeType==2){
										return "打卡核算";
									}
									
								}
							},{
								field: "comeWork",
								align: 'center',
								width:150,
								title: "加班后到岗",
								templet: function(d){
									if(d.comeWork==1){
									return "按点上班";
									}
									if(d.comeWork==2){
										return "第二天上班时间超过24:00往后推";
									}
									if(d.comeWork==3){
										return "超过24:30后默认休息7.5小时";
									}
								}
							},{fixed:'right', title:'操作', align: 'center', toolbar: '#barDemo'}]
						],
						done: function() {
							var tableView = this.elem.next();
							tableView.find('.layui-table-grid-down').remove();
							var totalRow = tableView.find('.layui-table-total');
							var limit = this.page ? this.page.limit : this.limit;
							layui.each(totalRow.find('td'), function(index, tdElem) {
								tdElem = $(tdElem);
								var text = tdElem.text();
								if(text && !isNaN(text)) {
									text = (parseFloat(text) / limit).toFixed(2);
									tdElem.find('div.layui-table-cell').html(text);
								}
							});
						},
						//下拉框回显赋值
						done: function(res, curr, count) {
							var tableView = this.elem.next();
							var tableElem = this.elem.next('.layui-table-view');
							layui.each(tableElem.find('select'), function(index, item) {
								var elem = $(item);
								elem.val(elem.data('value'));
							});
							form.render();
						},

					});

					
					
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'deleteSome':
								// 获得当前选中的
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
									var postData = {
										ids: checkedIds,
									}
									$.ajax({
										url: "${ctx}/personnel/deleteAttendanceInit",
										data: postData,
										traditional: true,
										type: "GET",
										beforeSend: function() {
											index;
										},
										success: function(result) {
											if(0 == result.code) {
												table.reload('tableData');
												layer.msg(result.message, {
													icon: 1,
													time:500
												});
											} else {
												layer.msg(result.message, {
													icon: 2,
													time:500
												});
											}
										},
										error: function() {
											layer.msg("操作失败！", {
												icon: 2
											});
										}
									});
									layer.close(index);
								});
								break;
							case 'notice':
								var dicDiv=$('#layuiadmin-form-admin');
								document.getElementById("layuiadmin-form-admin").reset();
					        	layui.form.render();
								layer.open({
							         type: 1
							        ,title: "新增" //不显示标题栏
							        ,closeBtn: false
							        ,zindex:-1
							        ,area:['30%', '100%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
							        ,btn: ['确认', '取消']
							        ,btnAlign: 'c'
							        ,moveType: 1 //拖拽模式，0或者1
							        ,content:dicDiv
							        ,success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        }
							        ,yes: function(index, layero){
							        	form.on('submit(addRole)', function(data) {
							        		var key=data.field.restDay
							        		var s=key.charAt(key.length-1)
							        		if(s==","){
							        			return layer.msg("约定休息日末尾不能是,号", {icon: 2});
							        		}else{
							        	  mainJs.fAdd(data.field); 
							        	 }
							        	timeAll=""
										})
										
							        }
							        ,end:function(){
							        	document.getElementById("layuiadmin-form-admin").reset();
							        	layui.form.render();
							        	timeAll=""
									  } 
							      });
								break;
							case 'set':
								
								$.ajax({
									url: "${ctx}/personnel/findRestType",
									type: "GET",
									beforeSend: function() {
										index;
									},
									success: function(result) {
										$("#layuiadmin-form-admin2").setForm({weeklyRestDate:result.data[0].weeklyRestDate,monthRestDate:result.data[0].monthRestDate});
									},
									error: function() {
										layer.msg("操作失败！请重试", {
											icon: 2
										});
									},
								});
								var dicDiv=$('#layuiadmin-form-admin2');
								layer.open({
							         type: 1
							        ,title: "设定休息时间" //不显示标题栏
							        ,closeBtn: false
							        ,area:['30%', '500px']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
							        ,btn: ['确认', '取消']
							        ,btnAlign: 'c'
							        ,moveType: 1 //拖拽模式，0或者1
							        ,content:dicDiv
							        ,success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole2',
											'lay-submit' : ''
										})
							        }
							        ,yes: function(index, layero){
							        	form.on('submit(addRole2)', function(data) {
							        		var data=data.field
							        		var key=data.keyValue
							        		var s=key.charAt(key.length-1)
							        		var key2=data.keyValueTwo
							        		var s2=key2.charAt(key2.length-1)
							        		if(s=="," ||s2==","){
							        			return layer.msg("周休一天或月休两天的末尾不能是,号", {icon: 2});
							        		}else{
							        		$.ajax({
												url: "${ctx}/personnel/updateRestType",
												data: data,
												type: "Post",
												beforeSend: function() {
													index;
												},
												success: function(result) {
													if(0 == result.code) {
														layer.msg(result.message, {
															icon: 1,
															time:500
														});
													
													} else {
														layer.msg(result.message, {
															icon: 2,
															time:500
														});
													}
												},
												error: function() {
													layer.msg("操作失败！请重试", {
														icon: 2
													});
												},
											});
							        		}
							        	  document.getElementById("layuiadmin-form-admin2").reset();
							        	layui.form.render();
							        	timeAll2="" 
							        	timeAll3="" 
										})  
										
							        }
							        ,end:function(){
							        	 document.getElementById("layuiadmin-form-admin2").reset();
							        	layui.form.render();
							        	timeAll2=""
							        	timeAll3=""
									  } 
							      });
								break;
						}
					});
					
					//监听单元格编辑
					table.on('tool(tableData)', function(obj) {
						document.getElementById("layuiadmin-form-admin").reset();
			        	layui.form.render();
						var data = obj.data;
						var value = obj.value
						var id=data.id;
						var val=obj.field
						$("#usID").val(id)
					    if(obj.event === 'update'){
					    	
					    	var dicDiv=$('#layuiadmin-form-admin');
					    	$('#selectOne').each(function(j,k){
								var id=data.user.id;
								$(k).val(id);
								form.render('select');
							});
					    	$('#restType').each(function(j,k){
					    		var id=data.restType;
								$(k).val(id);
								form.render('select');
							});
					    	$('#workType').each(function(j,k){
					    		var id=data.workType;
								$(k).val(id);
								form.render('select');
							});
					    	$('#restTimeWork').each(function(j,k){
					    		var id=data.restTimeWork;
								$(k).val(id);
								form.render('select');
							});
							$('#overTimeType').each(function(j,k){
					    		var id=data.overTimeType;
								$(k).val(id);
								form.render('select');
							});
							$('#comeWork').each(function(j,k){
					    		var id=data.comeWork;
								$(k).val(id);
								form.render('select');
							});
							if(data.earthWork==true){
							$("#kai").attr("checked","checked");
					    	form.render();
					       }else{
					    	   $("#kai").attr("checked",false);
						    	form.render();
					       }
							$("#restTimeSummer").val(data.restTimeSummer)
					    	$("#layuiadmin-form-admin").setForm({restDay:data.restDay,workTimeSummer:data.workTimeSummer,workTimeWinter:data.workTimeWinter,turnWorkTimeSummer:data.turnWorkTimeSummer,turnWorkTimeWinter:data.turnWorkTimeWinter,restTimeSummer:data.restTimeSummer,restTimeWinter:data.restTimeWinter,restSummer:data.restSummer,restWinter:data.restWinter});
					    	layer.open({
						         type: 1
						        ,title: "修改" //不显示标题栏
						        ,closeBtn: false
						        ,area:['30%', '100%']
						        ,shade: 0.5
						        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
						        ,btn: ['确定', '取消']
						        ,btnAlign: 'c'
						        ,moveType: 1 //拖拽模式，0或者1
						        ,content:dicDiv
						        ,success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole',
										'lay-submit' : ''
									})
						        }
						        ,yes: function(index, layero){
						        	form.on('submit(addRole)', function(data) {
						        		var key=data.field.restDay
						        		var s=key.charAt(key.length-1)
						        		if(s==","){
						        			return layer.msg("约定休息日末尾不能是,号", {icon: 2});
						        		}else{
						        	 mainJs.fAdd(data.field); 
						        		}
									})
									
						        }
						        ,end:function(){
						        	document.getElementById("layuiadmin-form-admin").reset();
						        	layui.form.render();
						        	timeAll=""
								  } 
						       
						      });
					    	
					    	
					    }
					});

					//监听搜索
					form.on('submit(LAY-role-search)', function(data) {
						var field = data.field;
						$.ajax({
							url: "${ctx}/personnel/findAttendanceInit",
							type: "get",
							data: field,
							dataType: "json",
							success: function(result) {
								table.reload('tableData', {
									where: field
								});
							}
						});
					});
					
					
					//封装ajax主方法
					var mainJs = {
						//新增							
					    fAdd : function(data){
					    	$.ajax({
								url: "${ctx}/personnel/addAttendanceInit",
								data: data,
								type: "Post",
								beforeSend: function() {
									index;
								},
								success: function(result) {
									if(0 == result.code) {
										table.reload('tableData');
										layer.msg(result.message, {
											icon: 1,
											time:500
										});
										if(data.id==null){
										document.getElementById("layuiadmin-form-admin").reset();
							        	layui.form.render();
										}
									} else {
										layer.msg(result.message, {
											icon: 2,
											time:500
										});
									}
								},
								error: function() {
									layer.msg("操作失败！请重试", {
										icon: 2
									});
								},
							});
							layer.close(index);
					    }
					}

				}
			)
		</script>
</body>
</html>