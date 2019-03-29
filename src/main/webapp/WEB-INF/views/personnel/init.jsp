<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
	<!--<![endif]-->

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>考勤初始设定</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	</head>

	<body>
		<section id="main-wrapper" class="theme-default">
			<%@include file="../decorator/leftbar.jsp"%>
			<section id="main-content" class="animated fadeInUp">
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">考勤初始设定</h3>
								<div class="actions pull-right">
									<i class="fa fa-expand"></i>
									<i class="fa fa-chevron-down"></i>
								</div>
							</div>
							<div class="panel-body">
								<div class="layui-form layui-card-header layuiadmin-card-header-auto">
									<div class="layui-form-item">
										<table>
											<tr>
												<td>申请人:</td>
												<td>
												<select name="userId" class="form-control search-query name"  id="firstNames" lay-search="true"></select>
												</td>
												<td>&nbsp&nbsp</td>
												<td>部门:</td>
												<td id="orgNameId">
												</td>
												<td>&nbsp&nbsp</td>
												<td>开始:</td>
												<td>
													<input id="startTime" name="orderTimeBegin" placeholder="请输入开始时间" class="form-control laydate-icon">
												</td>
												<td>&nbsp&nbsp</td>
												<td>结束:</td>
												<td>
													<input id="endTime" name="orderTimeEnd" placeholder="请输入结束时间" class="form-control laydate-icon">
												</td>
													<td>&nbsp&nbsp</td>
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
					</div>
				</div>
			</section>
		</section>
		</section>
		<form action="" id="layuiadmin-form-admin" style="padding: 20px 30px 0 60px; display: none; text-align: ">
	<div class="layui-form" lay-filter="layuiadmin-form-admin" >
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 130px;">员工姓名</label>
      <div class="layui-input-inline">
        <select name="userId" lay-filter="lay_selecte" lay-verify="required" id="selectOne" lay-search="true"></select>
      </div>
    </div>
    <div class="layui-form-item">
     	<label class="layui-form-label" style="width: 130px;">休息方式</label>
     	<div class="layui-input-inline">
        	<select name="restType" lay-filter="restType" lay-verify="required"  lay-search="true"><option value="0">请选择</option><option value="1">无到岗要求</option><option value="2">周休一天</option><option value="3">月休两天，其他周日算加班</option><option value="4">全年无休</option><option value="5">按到岗小时计算（类似全年无休，有自己的节假日休息））</option></select>
      	</div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 130px;">约定休息时间</label>
      <div class="layui-input-inline">
        <input type="text"  name="applytime" id="applytime"  placeholder="请输入申请时间" class="form-control laydate-icon">
        <td>&nbsp&nbsp</td>
        <input type="text" name="restDay"  id="inputapplytime"   class="form-control">
      </div>
    </div>
    
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 130px;">夏令时工作区间</label>
      <div class="layui-input-inline">
        <input type="text"  name="workTimeSummer" id="workTimeSummer" lay-verify="required" placeholder="请输入夏令时工作区间" class="form-control laydate-icon">
      </div>
    </div>
    
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 130px;">冬令时工作区间</label>
      <div class="layui-input-inline">
        <input type="text"  name="workTimeWinter" id="workTimeWinter" lay-verify="required" placeholder="请输入冬令时工作区间" class="form-control laydate-icon">
      </div>
    </div>
    
   <div class="layui-form-item">
      <label class="layui-form-label" style="width: 130px;">夏令出勤时长</label>
      <div class="layui-input-inline">
        <input type="text" name="turnWorkTimeSummer" id="turnWorkTimeSummer" lay-verify="required" placeholder="请输入夏令出勤时长 " class="form-control">
      </div>
    </div>
   
   <div class="layui-form-item">
      <label class="layui-form-label" style="width: 130px;">冬令出勤时长</label>
      <div class="layui-input-inline">
        <input type="text" name="turnWorkTimeWinter" id="turnWorkTimeWinter" lay-verify="required" placeholder="请输入冬令出勤时长 " class="form-control">
      </div>
    </div>
    
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 130px;">夏令时午休区间</label>
      <div class="layui-input-inline">
        <input type="text"  name="restTimeSummer" id="restTimeSummer" lay-verify="required" placeholder="请输入夏令时午休区间" class="form-control laydate-icon">
      </div>
    </div>
    
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 130px;">冬令时午休区间</label>
      <div class="layui-input-inline">
        <input type="text"  name="restTimeWinter" id="restTimeWinter" lay-verify="required" placeholder="请输入冬令时午休区间" class="form-control laydate-icon">
      </div>
    </div>
    
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 130px;">夏令午休时长</label>
      <div class="layui-input-inline">
        <input type="text" name="restSummer" id="restSummer" lay-verify="required" placeholder="请输入夏令午休时长 " class="form-control">
      </div>
    </div>
    
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 130px;">冬令午休时长</label>
      <div class="layui-input-inline">
        <input type="text" name="restWinter" id="restWinter" lay-verify="required" placeholder="请输入冬令午休时长" class="form-control">
      </div>
    </div>
    
    <div class="layui-form-item">
     	<label class="layui-form-label" style="width: 130px;">午休状态</label>
     	<div class="layui-input-inline">
        	<select name="restTimeWork" lay-filter="restTimeWork" lay-verify="required"  lay-search="true"><option value="0">请选择</option><option value="1">默认休息</option><option value="2">出勤</option><option value="3">加班</option></select>
      	</div>
    </div>
    
    <div class="layui-form-item">
     	<label class="layui-form-label" style="width: 130px;">核算加班</label>
     	<div class="layui-input-inline">
        	<select name="overTimeType" lay-filter="overTimeType" lay-verify="required"  lay-search="true"><option value="0">请选择</option><option value="1">看加班申请</option><option value="2">打卡核算</option></select>
      	</div>
    </div>
    
    <div class="layui-form-item">
     	<label class="layui-form-label" style="width: 130px;">加班后到岗</label>
     	<div class="layui-input-inline">
        	<select name="comeWork" lay-filter="comeWork" lay-verify="required"  lay-search="true"><option value="0">请选择</option><option value="1">按点上班</option><option value="2">第二天上班时间以超过24:00后的时间往后推</option><option value="3">超过24:30后默认休息7.5小时</option></select>
      	</div>
    </div>
    
		</div>
		</form>
		<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="notice">新增</span>
				<span class="layui-btn layui-btn-sm" lay-event="deleteSome">批量删除</span>
			</div>
		</script>
<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-trans layui-btn-xs"  lay-event="update">编辑</a>
</script>
		<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
		<script src="${ctx }/static/js/shujuhuixian/sjhx.js"></script>
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
							timeAll=(timeAll==''? value:(timeAll+','+value));
							$("#inputapplytime").val(timeAll)
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
						$("#firstNames").html(htmls);
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
								var htmlth = '<select name="orgNameId" class="form-control"><option value="">请选择</option>' + htmlfr + '</select>'
								$("#orgNameId").html(htmlth);
								layer.close(index);
							}
						});

					
					
					
				 	/* tablePlug.smartReload.enable(true);  */
					table.render({
						elem: '#tableData',
						 size: 'lg',
						url: '${ctx}/personnel/findAttendanceInit',
						method:'POST',
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
							limit:15
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
									return "无到岗要求";
									}
									if(d.restType==2){
										return "周休一天";
									}
									if(d.restType==3){
										return "月休两天,其他周日算加班";
									}
									if(d.restType==4){
										return "全年无休";
									}
									if(d.restType==5){
										return "按到岗小时计算(类似全年无休，有自己的节假日休息)";
									}
								}
							},{
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
										return "第二天上班时间以超过24:00后的时间往后推";
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

					
					form.on("radio(variable)", function(data) {
						if(data.value==0){
							$("#leave").css("display","block")
							$("#Break").css("display","none")
							$("#repair").css("display","none")
							$("#overtime").css("display","none")
						}
						if(data.value==1){
							$("#Break").css("display","block")
							$("#leave").css("display","none")
							$("#repair").css("display","none")
							$("#overtime").css("display","none")
							 
						}
						if(data.value==2){
							$("#repair").css("display","block")
							$("#Break").css("display","none")
							$("#leave").css("display","none")
							$("#overtime").css("display","none")
						}
						if(data.value==3){
							$("#overtime").css("display","block")
							$("#leave").css("display","none")
							$("#Break").css("display","none")
							$("#repair").css("display","none")
						}
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
										url: "${ctx}/personnel/deleteApplicationLeave",
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
							        ,area:['540px', '99%']
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
							        		console.log(data)
							        		
							        	 mainJs.fAdd(data.field); 
							        	document.getElementById("layuiadmin-form-admin").reset();
							        	layui.form.render();
										})
										
							        }
							        ,end:function(){
							        	document.getElementById("layuiadmin-form-admin").reset();
							        	layui.form.render();
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
						var id=data.id;
					    if(obj.event === 'update'){
					    	var dicDiv=$('#layuiadmin-form-admin');
					    	$('#selectOne').each(function(j,k){
								var id=data.user.id;
								$(k).val(id);
								form.render('select');
							});
					    	$('#holidayType').each(function(j,k){
					    		var id=data.holidayType;
								$(k).val(id);
								form.render('select');
							});
					    	if(data.holiday==true){
					    		$("#leave").css("display","block")
								$("#Break").css("display","none")
								$("#repair").css("display","none")
								$("#overtime").css("display","none")
					    	$("#qing").get(0).checked=true;
					    	form.render('radio');
					    	$("#layuiadmin-form-admin").setForm({content:data.content,applytime:data.writeTime,leavetime:JSON.parse(data.time).date,leaveduration:JSON.parse(data.time).time});
					    	}
					    	if(data.tradeDays==true){
					    		$("#Break").css("display","block")
								$("#leave").css("display","none")
								$("#repair").css("display","none")
								$("#overtime").css("display","none")
					    		$("#tiao").get(0).checked=true;
					    		form.render('radio');
					    		$("#layuiadmin-form-admin").setForm({applytime:data.writeTime,breaktime:JSON.parse(data.time).date,breakduration:JSON.parse(data.time).time});
					    	}
					    	if(data.addSignIn==true){
					    		$("#repair").css("display","block")
								$("#Break").css("display","none")
								$("#leave").css("display","none")
								$("#overtime").css("display","none")
					    		$("#bu").get(0).checked=true;
					    		form.render('radio');
					    		$("#layuiadmin-form-admin").setForm({applytime:data.writeTime,repairtime:JSON.parse(data.time).date});
					    		if(JSON.parse(data.time).time==0){
					    			$("#qianru").get(0).checked=true;
						    		form.render('radio');
					    		}
					    		if(JSON.parse(data.time).time==1){
					    			$("#qianchu").get(0).checked=true;
						    		form.render('radio');
					    		}
					    	}
					    	if(data.applyOvertime==true){
					    		$("#overtime").css("display","block")
								$("#leave").css("display","none")
								$("#Break").css("display","none")
								$("#repair").css("display","none")
					    		$("#jia").get(0).checked=true;
					    		form.render('radio');
					    		$("#layuiadmin-form-admin").setForm({applytime:data.writeTime,overtime:JSON.parse(data.time).date,overduration:JSON.parse(data.time).time});
					    	}
					    	layer.open({
						         type: 1
						        ,title: "修改" //不显示标题栏
						        ,closeBtn: false
						        ,area:['540px', '60%']
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
						        		var variable='';
						        		var holidayType='';
						        		var content='';
						        		var leavetime='';
						        		var leaveduration='';
						        		var time='';
						        		if(data.field.variable==0){
						        			if(data.field.holidayType==0){
						        				return layer.msg("请假类型不能为空", {icon: 2});
						        			}
						        			if(data.field.content==""){
						        				return layer.msg("请假原因不能为空", {icon: 2});
						        			}
						        			if(data.field.leavetime==""){
						        				return layer.msg("请假日期不能为空", {icon: 2});
						        			}
						        			if(data.field.leaveduration==""){
						        				return layer.msg("请假时长不能为空", {icon: 2});
						        			}
						        			variable='holiday';
						        			holidayType=data.field.holidayType;
						        			content=data.field.content;
						        			leavetime=data.field.leavetime;
						        			leaveduration=data.field.leaveduration;
						        			time={date:leavetime,time:leaveduration};
						        		}
						        		if(data.field.variable==1){
						        			if(data.field.breaktime==""){
						        				return layer.msg("调休日期不能为空", {icon: 2});
						        			}
						        			if(data.field.breakduration==""){
						        				return layer.msg("调休时长不能为空", {icon: 2});
						        			}
						        			variable='tradeDays'
						        			breaktime=data.field.breaktime;
						        			breakduration=data.field.breakduration;
						        			time={date:breaktime,time:breakduration};
						        		}
						        		if(data.field.variable==2){
						        			if(data.field.repairtime==""){
						        				return layer.msg("补签日期不能为空", {icon: 2});
						        			}
						        			variable='addSignIn' 
						        			repairtime=data.field.repairtime;
						        			Sign=data.field.Sign
						        			time={date:repairtime,time:Sign};
						        		}
						        		if(data.field.variable==3){
						        			if(data.field.overtime==""){
						        				return layer.msg("加班日期不能为空", {icon: 2});
						        			}
						        			if(data.field.overduration==""){
						        				return layer.msg("加班时长不能为空", {icon: 2});
						        			}
						        			variable='applyOvertime'
						        			overtime=data.field.overtime;
						        			overduration=data.field.overduration;
						        			time={date:overtime,time:overduration};
						        		}
						        	var postData={
						        			id:id,
						        			userId:data.field.userId,
						        			writeTime:data.field.applytime,
						        			[variable]:'true',
						        			holidayType:holidayType,
						        			content:content,
						        			time:JSON.stringify(time)
						        	}	
						        	mainJs.fAdd(postData);
						        	
									})

						        }
						       
						      });
					    	
					    	
					    }
					});

					//监听搜索
					form.on('submit(LAY-role-search)', function(data) {
						var field = data.field;
						$.ajax({
							url: "${ctx}/fince/getExpenseAccount",
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