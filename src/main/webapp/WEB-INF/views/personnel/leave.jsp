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
			<%@include file="../decorator/leftbar.jsp"%>
			<section id="main-content" class="animated fadeInUp">
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">报销申请</h3>
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
												<td>报销人:</td>
												<td>
													<input type="text" name="Username" id="firstNames" class="form-control search-query name" />
												</td>
												<td>&nbsp&nbsp</td>
												<td>报销内容:</td>
												<td>
													<input type="text" name="content" class="form-control search-query" />
												</td>
												<td>&nbsp&nbsp</td>
												<td>
													<select class="form-control" name="expenseDate" id="selectone">
														<option value="2018-10-08 00:00:00">付款日期</option>
													</select>
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
												<td>是否核对:
													<td>
														<select class="form-control" name="flag">
															<option value="">请选择</option>
															<option value="0">未核对</option>
															<option value="1">已核对</option>
														</select>
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
      <label class="layui-form-label" style="width: 90px;">申请人</label>
      <div class="layui-input-inline">
        <select name="userId" lay-filter="lay_selecte" id="selectOne" lay-search="true"></select>
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">申请时间</label>
      <div class="layui-input-inline">
        <input type="text" name="applytime" id="applytime" lay-verify="applytime" placeholder="请输入申请时间" class="form-control laydate-icon">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">申请项</label>
      <div class="layui-input-inline" id="selectradio" style="width: 325px;">
      <input type="radio" lay-filter="variable" id="qing" name="variable" value="0" title="请假" checked="">
      <input type="radio" lay-filter="variable" id="tiao" name="variable" value="1" title="调休">
      <input type="radio" lay-filter="variable" id="bu" name="variable" value="2" title="补签">
      <input type="radio" lay-filter="variable" id="jia" name="variable" value="3" title="加班">
      </div>
    </div>
    <div id="leave" style="display: block;">
     <div class="layui-form-item">
     	<label class="layui-form-label" style="width: 90px;">请假类型</label>
     	<div class="layui-input-inline">
        	<select name="holidayType" lay-filter="holidayType" id="holidayType"  lay-search="true"><option value="0">请选择</option><option value="1">事假</option><option value="2">病假</option><option value="3">丧假</option><option value="4">婚假</option><option value="5">产假</option><option value="6">护理假</option></select>
      	</div>
     </div>
     <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">请假原因</label>
      <div class="layui-input-inline">
        <input type="text" name="content" id="content" lay-verify="content" placeholder="请输入请假原因 " class="form-control">
      </div>
    </div>
     <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">请假日期</label>
      <div class="layui-input-inline">
        <input type="text" name="leavetime" id="leavetime" lay-verify="leavetime"  placeholder="请输入请假日期" class="form-control laydate-icon">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">请假时长</label>
      <div class="layui-input-inline">
        <input type="text" name="leaveduration" id="leaveduration" lay-verify="leaveduration" placeholder="请输入请假时长 " class="form-control">
      </div>
    </div>
    </div>
   
   <div id="Break" style="display: none;">
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">调休时长</label>
      <div class="layui-input-inline">
        <input type="text" name="breakduration" id="breakduration" lay-verify="breakduration" placeholder="请输入调休时长 " class="form-control">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">调休至时间</label>
      <div class="layui-input-inline">
        <input type="text" name="breaktime" id="breaktime" lay-verify="breaktime" placeholder="请输入调休到哪一天的时间" class="form-control laydate-icon">
      </div>
    </div>
    </div>
    
    <div id="repair" style="display: none;">
     <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">补签日期</label>
      <div class="layui-input-inline">
        <input type="text" name="repairtime" id="repairtime" lay-verify="repairtime" placeholder="请输入补签日期" class="form-control laydate-icon">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">补签状态</label>
      <div class="layui-input-inline" style="width: 325px;">
      <input type="radio" name="Sign" id="qianru" value="0" title="签入" checked="">
      <input type="radio" name="Sign" id="qianchu" value="1" title="签出">
      </div>
    </div>
    </div>
    
    <div id="overtime" style="display: none;">
     <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">加班日期</label>
      <div class="layui-input-inline">
        <input type="text" name="overtime" id="overdurationtime" lay-verify="overtime" placeholder="请输入加班日期" class="form-control laydate-icon">
      </div>
      </div>
      <div class="layui-form-item">
      <label class="layui-form-label" style="width: 90px;">加班时长</label>
      <div class="layui-input-inline">
        <input type="text" name="overduration" id="overduration" lay-verify="overduration" placeholder="请输入加班时长 " class="form-control">
      </div>
    </div>
    </div>
    <div class="layui-form-item layui-hide">
     <button type="submit" class="layui-btn layui-btn-primary">重置</button>
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
						elem: '#overdurationtime',
						type: 'datetime',
					});
					laydate.render({
						elem: '#breaktime',
						format: 'yyyy-MM-dd',
					});
					laydate.render({
						elem: '#repairtime',
						type: 'datetime',
					});
					laydate.render({
						elem: '#applytime',
						type: 'datetime',
					});
					laydate.render({
						elem: '#leavetime',
						range : '~',
						format: 'yyyy-MM-dd',
					});
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
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
						$('#selectOne').html(htmls);
						},
						error: function() {
							layer.msg("操作失败！", {
								icon: 2
							});
							layer.close(index);
						}
					});
					
			

					
					
					
				 	/* tablePlug.smartReload.enable(true);  */
					table.render({
						elem: '#tableData',
						size: 'lg',
						url: '${ctx}/personnel/getApplicationLeavePage' ,
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
								field: "userId",
								title: "申请人",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: function(d){
									
									return d.user.userName
								}
							}, {
								field: "writeTime",
								title: "申请时间",
							}, {
								field: "expenseDate",
								title: "申请项",
								templet: function(d){
									if(d.addSignIn==true){
									return "补签"
									}
									if(d.applyOvertime==true){
										return "加班"
									}
									if(d.holiday==true){
										return "请假"
									}
									if(d.tradeDays==true){
										return "调休"
									}
								}
							}, {
								field: "withholdReason",
								title: "详情",
								templet: function(d){
									if(d.addSignIn==true){
									return "补签"
									}
									if(d.applyOvertime==true){
										return "加班"
									}
									if(d.holiday==true){
										var a;
										if(d.holidayType==1){
											a='事假'
										}
										if(d.holidayType==2){
											a='病假'
										}
										if(d.holidayType==3){
											a='丧假'
										}
										if(d.holidayType==4){
											a='婚假'
										}
										if(d.holidayType==5){
											a='产假'
										}
										return "请假类型:"+a+"-请假原因:"+d.content+" -请假日期:"+JSON.parse(d.time).date+" -请假时长:"+JSON.parse(d.time).time+"小时";
									}
									if(d.tradeDays==true){
										return "调休"
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
							// 初始化laydate
							layui.each(tableView.find('td[data-field="expenseDate"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								laydate.render({
									elem: tdElem.children[0],
									format: 'yyyy-MM-dd HH:mm:ss',
									done: function(value, date) {
											var id = table.cache[tableView.attr('lay-id')][index].id
											var postData = {
												id: id,
												expenseDate: value,
											};
											//调用新增修改
											mainJs.fAdd(postData);
									}
								})

							})

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
								
								layer.open({
							         type: 1
							        ,title: "新增" //不显示标题栏
							        ,closeBtn: false
							        ,area:['540px', '60%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
							        ,btn: ['火速围观', '残忍拒绝']
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
							        		var variable='';
							        		var holidayType='';
							        		var content='';
							        		var leavetime='';
							        		var leaveduration='';
							        		var time='';
							        		if(data.field.variable==0){
							        			variable='holiday';
							        			holidayType=data.field.holidayType;
							        			content=data.field.content;
							        			leavetime=data.field.leavetime;
							        			leaveduration=data.field.leaveduration;
							        			time={date:leavetime,time:leaveduration};
							        		}
							        		if(data.field.variable==1){
							        			variable='tradeDays'
							        			breaktime=data.field.breaktime;
							        			breakduration=data.field.breakduration;
							        			time={date:breaktime,time:breakduration};
							        		}
							        		if(data.field.variable==2){
							        			variable='addSignIn' 
							        			repairtime=data.field.repairtime;
							        			Sign=data.field.Sign
							        			time={date:repairtime,time:Sign};
							        		}
							        		if(data.field.variable==3){
							        			variable='applyOvertime'
							        			overtime=data.field.overtime;
							        			overduration=data.field.overduration;
							        			time={date:overtime,time:overduration};
							        		}
							        	var postData={
							        			userId:data.field.userId,
							        			writeTime:data.field.applytime,
							        			[variable]:'true',
							        			holidayType:holidayType,
							        			content:content,
							        			time:JSON.stringify(time)
							        	}	
							        	mainJs.fAdd(postData);
							        	table.reload('tableData', {
										});
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
						        ,title: "新增" //不显示标题栏
						        ,closeBtn: false
						        ,area:['540px', '60%']
						        ,shade: 0.5
						        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
						        ,btn: ['火速围观', '残忍拒绝']
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
						        		var variable='';
						        		var holidayType='';
						        		var content='';
						        		var leavetime='';
						        		var leaveduration='';
						        		var time='';
						        		if(data.field.variable==0){
						        			variable='holiday';
						        			holidayType=data.field.holidayType;
						        			content=data.field.content;
						        			leavetime=data.field.leavetime;
						        			leaveduration=data.field.leaveduration;
						        			time={date:leavetime,time:leaveduration};
						        		}
						        		if(data.field.variable==1){
						        			variable='tradeDays'
						        			breaktime=data.field.breaktime;
						        			breakduration=data.field.breakduration;
						        			time={date:breaktime,time:breakduration};
						        		}
						        		if(data.field.variable==2){
						        			variable='addSignIn' 
						        			repairtime=data.field.repairtime;
						        			Sign=data.field.Sign
						        			time={date:repairtime,time:Sign};
						        		}
						        		if(data.field.variable==3){
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
						        	table.reload('tableData', {
									});
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
								url: "${ctx}/personnel/addApplicationLeave",
								data: data,
								type: "GET",
								beforeSend: function() {
									index;
								},
								success: function(result) {
									if(0 == result.code) {
										/* table.reload('tableData');  */
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