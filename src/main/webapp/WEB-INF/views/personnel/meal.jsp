<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>餐费管理</title>
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
							<td>姓名:</td>
							<td><select class="form-control" id="selectUserId" lay-search="true"  name="userId"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>日期:</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>部门:</td>
							<td ><select class="form-control" name="orgNameId" id="orgName"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>报餐类型:</td>
							<td><select class="form-control" name="mode">
									<option value="">请选择</option>
									<option value="1">早餐</option>
									<option value="2">中餐</option>
									<option value="3">晚餐</option>
									<option value="4">夜宵</option>
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" id="LAY-search5" lay-submit lay-filter="LAY-search">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
							<td>&nbsp;&nbsp;</td>
							<td><input id="startTime2" style="width: 150px;"  placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-eat">
										<i class="layui-icon layuiadmin-button-btn"> 同步</i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
			
			<shiro:hasAnyRoles name="superAdmin,personnel">
   				 <p id="totalAll" style="text-align:center;color:red;"></p>
			</shiro:hasAnyRoles> 
			
		</div>
	</div>
	
	<form action="" id="layuiadmin-form-admin"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" name="id" id="ids" style="display:none;">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">早餐金额</label>
				<div class="layui-input-inline">
					<input type="text"  name="keyValue" id="keyValue"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">中餐金额</label>
				<div class="layui-input-inline">
					<input type="text" name="keyValueTwo" id="keyValueTwo"
						lay-verify="required"
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">晚餐金额</label>
				<div class="layui-input-inline">
					<input type="text"  name="keyValueThree" id="keyValueThree" lay-verify="required" class="layui-input">
				</div>
			</div>
		</div>
	</form>	
	
	
	<form action="" id="layuiadmin-form-admin2"
		style="padding: 20px 0px 0 50px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">姓名</label>
				<div class="layui-input-inline">
					<select name="userId" style="width:290px;" lay-filter="id" id="userId" lay-search="true"></select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">报餐类型</label>
				<div class="layui-input-inline">
					<select name="mode" style="width:290px;"  lay-filter="mode" id="mode" lay-search="true">
						<option value="">请选择</option>
						<option value="1">早餐</option>
						<option value="2">中餐</option>
						<option value="3">晚餐</option>
						<option value="4">夜宵</option>
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">日期</label>
					<div class="layui-input-inline">
						<input type="text" 
							style="width: 190px; position: absolute; float: left;" name="time"
							id="tradeDaysTime" lay-verify="tradeDaysTime" placeholder="请输入日期"
							class="layui-input laydate-icon">
					</div>
				</div>
		</div>
	</form>

<div style="display: none;" id="layuiShare5">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>姓名:</td>
							<td><select id="userIds" class="layui-input"  lay-search="true" name="userId"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>部门:</td>
							<td><select  id="orgNameIds" class="layui-input" lay-search="true" name="orgNameId">
							<option value="">请选择</select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-search7">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="layuiShare6"  class="table_th_search" lay-filter="layuiShare8"></table>
</div>
	
	
	<form action="" id="layuiadmin-form-admin4"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" name="id" id="ids2" style="display:none;">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">早餐</label>
				<div class="layui-input-inline">
					<input type="text"  name="keyValue" id="keyValue2"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">中餐</label>
				<div class="layui-input-inline">
					<input type="text" name="keyValueTwo" id="keyValueTwo2"
						lay-verify="required"
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">晚餐</label>
				<div class="layui-input-inline">
					<input type="text"  name="keyValueThree" id="keyValueThree2" lay-verify="required" class="layui-input">
				</div>
			</div>
		</div>
	</form>	
	
	
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
				<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
				<span class="layui-btn layui-btn-sm" lay-event="openMeal">报餐价格</span>
				<span class="layui-btn layui-btn-sm" lay-event="delayed">吃饭延时</span>
				<span class="layui-btn layui-btn-sm" lay-event="eat">吃饭方式填写</span>
			</div>
	</script>

	
	<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug'
			}).define(
				['tablePlug', 'laydate', 'element'],
				function() {
					var $ = layui.jquery
						,layer = layui.layer //弹层
						,form = layui.form //表单
						,table = layui.table //表格
						,laydate = layui.laydate //日期控件
						,tablePlug = layui.tablePlug //表格插件
						,element = layui.element;
					
					//全部字段
					var allField;
					var self = this;
					this.setIndex = function(index){
				  		_index=index;
				  	}
				  	
				  	this.getIndex = function(){
				  		return _index;
				  	}
					//select全局变量
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
						elem: '#startTime2',
						type : 'month',
						format:'yyyy-MM-01 HH:mm:ss'
					});
				  // 多选
				  laydate.render({
				    elem: '#tradeDaysTime',
				    type: 'date',
				    range: '~',
				  });
				
					$.ajax({
						url: '${ctx}/system/user/findUserList',
						data:{
							foreigns:0
						},
						type: "GET",
						async: false,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							$(result.data).each(function(i, o) {
								htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
							})
							$("#selectUserId").html(htmls)
							$("#userId").html(htmls)
							$("#userIds").html(htmls)
							layer.close(index);
						},
						error: function() {
							layer.msg("操作失败！", {
								icon: 2
							});
							layer.close(index);
						}
					});
					
					var getdataa={type:"orgName",}
					var htmlfrn= '<option value="">请选择</option>';
				    $.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdataa,
					      type:"GET",
					      async:false,
					      beforeSend:function(){
					    	  indextwo = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfrn +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });
			      			$("#orgName").html(htmlfrn);
			      			$("#orgNameIds").html(htmlfrn);
			      			layer.close(indextwo);
					      }
					  });
					
					
					
					
					// 处理操作列
					var fn1 = function(field) {
						return function(d) {
							return [
								'<select name="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' + d.userId + '">' +
								htmls +
								'</select>'
							].join('');

						};
					};

					var fn2 = function(field) {
						return function(d) {
							return ['<select name="selectTwo" class="selectTwo" lay-filter="lay_selecte" lay-search="true" data-value="' + d.mode + '">',
								'<option value="">请选择</option>',
								'<option value="1">早餐</option>',
								'<option value="2">中餐</option>',
								'<option value="3">晚餐</option>',
								'<option value="4">夜宵</option>',
								'</select>'
							].join('');
						};
						form.render(); 
					};
					
					var fn3 = function(field) {
						return function(d) {
							return ['<select name="selectThree" class="selectThree" lay-filter="lay_selecte2" lay-search="true" data-value="' + d.eatType + '">',
								'<option value="">请选择</option>',
								'<option value="1">早餐</option>',
								'<option value="2">中餐</option>',
								'<option value="3">早餐or晚餐</option>',
								'<option value="4">晚夜宵</option>',
								'<option value="5">早晚夜宵</option>',
								'</select>'
							].join('');
						};
					};
					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						height:'700px',
						url: '${ctx}/personnel/getMeal' ,
						where:{
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
						//totalRow: true,		 //开启合计行 */
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
							},{
								field: "userId",
								title: "姓名",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: fn1('selectOne')
							},{
								field: "mode",
								title: "报餐类型",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: fn2('selectTwo')
							},{
								field: "price",
								title: "餐费",
								align: 'center',
								edit: false,
							},{
								field: "tradeDaysTime",
								title: "日期",
								edit: 'text'
							}]
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
							layui.each(tableElem.find('.layui-table-box').find('select'), function(index, item) {
								var elem = $(item);
								elem.val(elem.data('value'));
							});
							form.render();
							// 初始化laydate
							layui.each(tableView.find('td[data-field="tradeDaysTime"]'), function(index, tdElem) {
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
												tradeDaysTime: value,
											};
											//调用新增修改
											mainJs.fUpdate(postData);
												}
											})
										})
									},
								});
					
					
					
					
					
					
					
					// 监听表格中的下拉选择将数据同步到table.cache中
					form.on('select(lay_selecte)', function(data) {
						var selectElem = $(data.elem);
						var tdElem = selectElem.closest('td');
						var trElem = tdElem.closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						var field = tdElem.data('field');
						table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
						var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
						var postData = {
							id: id,
							[field]:data.value
						}
						//调用新增修改
						mainJs.fUpdate(postData);
					});
					//修改吃饭方式
					form.on('select(lay_selecte2)', function(data) {
						var selectElem = $(data.elem);
						var tdElem = selectElem.closest('td');
						var trElem = tdElem.closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						var field = tdElem.data('field');
						table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
						var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
						var postData = {
							id: id,
							[field]:data.value
						}
						$.ajax({
							url: "${ctx}/personnel/addAttendanceInit",
							data: postData,
							type: "POST",
							beforeSend: function() {
								index;
							},
							success: function(result) {
								if(0 == result.code) {
								 	 table.reload("layuiShare6", {
						                page: {
						                }
						              }) 
									layer.msg(result.message, {
										icon: 1,
										time:800
									});
								
								} else {
									layer.msg(result.message, {
										icon: 2,
										time:800
									});
								}
							},
							error: function() {
								layer.msg("操作失败！请重试", {
									icon: 2
								});
							},
						});
					});
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData':
								var	dicDiv=$("#layuiadmin-form-admin2");
								layer.open({
									type:1,
									title:'报餐新增',
									area:['30%','60%'],
									btn:['确认','取消'],
									content:dicDiv,
									id: 'LAY_layuipro' ,
									btnAlign: 'c',
								    moveType: 1, //拖拽模式，0或者1
									success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        },
									yes:function(){
										form.on('submit(addRole)', function(data) {
											mainJs.fAdd(data.field); 
											document.getElementById("layuiadmin-form-admin2").reset();
								        	layui.form.render();
										})
									},end:function(){ 
							        	document.getElementById("layuiadmin-form-admin2").reset();
							        	layui.form.render();
									  }
								})
								
								
								break;
							case 'saveTempData':
								var data = table.getTemp(tableId).data;
								var flag=false;
								var a=0;
								data.forEach(function(postData,i){
							    	a++;
							    	if(a==data.length){
							    		flag=true
							    	}
									})
								if(flag==true){
								data.forEach(function(postData,i){
									 mainJs.fAdd(postData);
									table.cleanTemp(tableId);
									})	
								}
						          break;
							case 'deleteSome':
								// 获得当前选中的
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
									var postData = {
										ids: checkedIds,
									}
									$.ajax({
										url: "${ctx}/fince/deleteMeal",
										data: postData,
										traditional: true,
										type: "GET",
										beforeSend: function() {
											index;
										},
										success: function(result) {
											if(0 == result.code) {
												var configTemp = tablePlug.getConfig("tableData");
									            if (configTemp.page && configTemp.page.curr > 1) {
									              table.reload("tableData", {
									                page: {
									                  curr: configTemp.page.curr - 1
									                }
									              })
									            }else{
									            	table.reload("tableData", {
										                page: {
										                }
										              })
									            };
												layer.msg(result.message, {
													icon: 1,
													time:800
												});
											} else {
												layer.msg(result.message, {
													icon: 2,
													time:800
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
							case 'openMeal':
								
								$.ajax({
									url: '${ctx}/personnel/getpersonVariabledao',
									type: "GET",
									data:{
										type:1
									},
									async: false,
									beforeSend: function() {
										index;
									},
									success: function(result) {
										$(result.data).each(function(i, o) {
											$("#ids").val(o.id);
											$("#keyValue").val(o.keyValue);
											$("#keyValueTwo").val(o.keyValueTwo);
											$("#keyValueThree").val(o.keyValueThree);
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
								
								//报价修改
							var	dicDiv=$("#layuiadmin-form-admin");
							var index3=	layer.open({
									type:1,
									title:'报餐价格',
									area:['30%','60%'],
									btn:['确认','取消'],
									content:dicDiv,
									id: 'LAY_layuipro' ,
									btnAlign: 'c',
								    moveType: 1, //拖拽模式，0或者1
									success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        },
									yes:function(){
										form.on('submit(addRole)', function(data) {
											$.ajax({
												url: '${ctx}/personnel/addPersonVaiable',
												type: "POST",
												data:data.field,
												async: false,
												beforeSend: function() {
													index;
												},
												success: function(result) {
												if(result.code==0){
													layer.msg("修改成功！", {
														icon: 1
													});
													layer.close(index3);
												}else{
													layer.msg("修改失败！", {
														icon: 2
													});
												}
													layer.close(index);
												},
												error: function() {
													layer.msg("操作失败！", {
														icon: 2
													});
													layer.close(index);
												}
											});
										})
									}
								})
								break;
								
							case 'delayed':
								$.ajax({
									url: '${ctx}/personnel/getpersonVariabledao',
									type: "GET",
									data:{
										type:4
									},
									async: false,
									beforeSend: function() {
										index;
									},
									success: function(result) {
										$(result.data).each(function(i, o) {
											$("#ids2").val(o.id);
											$("#keyValue2").val(o.keyValue);
											$("#keyValueTwo2").val(o.keyValueTwo);
											$("#keyValueThree2").val(o.keyValueThree);
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
								
								//报价修改
							var	dicDiv=$("#layuiadmin-form-admin4");
							var index2=	layer.open({
									type:1,
									title:'报餐延时',
									area:['30%','60%'],
									btn:['确认','取消'],
									content:dicDiv,
									id: 'LAY_layuipro' ,
									btnAlign: 'c',
								    moveType: 1, //拖拽模式，0或者1
									success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        },
									yes:function(){
										form.on('submit(addRole)', function(data) {
											$.ajax({
												url: '${ctx}/personnel/addPersonVaiable',
												type: "POST",
												data:data.field,
												async: false,
												beforeSend: function() {
													index;
												},
												success: function(result) {
												if(result.code==0){
													layer.msg("修改成功！", {
														icon: 1
													});
													layer.close(index2);
												}else{
													layer.msg("修改失败！", {
														icon: 2
													});
												}
													layer.close(index);
												},
												error: function() {
													layer.msg("操作失败！", {
														icon: 2
													});
													layer.close(index);
												}
											});
										})
									}
								})
								break;
								
							case 'eat':	
								var	dicDiv=$("#layuiShare5");
								table.reload("layuiShare6");
								layer.open({
							         type: 1
							        ,title: '吃饭方式' //不显示标题栏
							        ,closeBtn: false
							        ,zindex:-1
							        ,area:['50%', '90%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro2' //设定一个id，防止重复弹出
							        ,btn: ['取消']
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
							        ,end:function(){
							        	$("#layuiShare5").hide();
									  } 
							      });
							break;
							case 'cleanTempData':	
									table.cleanTemp(tableId);
							break;
						}
					});
	
					 form.on('submit(LAY-search7)', function(obj) {
							var field = obj.field;
							table.reload('layuiShare6', {
								where: field,
								page: {
									curr:1
				                }
							});
					})
				
					
					table.render({
						elem: '#layuiShare6',
						url: '${ctx}/personnel/findAttendanceInit',
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
						} ,
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
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
								field: "",
								title: "员工姓名",
								align: 'center',
								fixed: 'left',
								search: true,
								edit: false,
								templet: function(d){
									return d.user.userName;
								}
							},{
								field: "eatType",
								title: "吃饭类型",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: fn3('selectThree')
							}
							]
						],
						//下拉框回显赋值
						done: function(res, curr, count) {
							var tableView = this.elem.next();
							var tableElem = this.elem.next('.layui-table-view');
							layui.each(tableElem.find('.layui-table-box').find('select'), function(index, item) {
								var elem = $(item);
								elem.val(elem.data('value'));
							});
							form.render();
									},
								});
						
					
					//监听单元格编辑
					table.on('edit(tableData)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							id = data.id;
							var postData = {
								id:id,
								[field]:value
							}
							//调用新增修改
							mainJs.fUpdate(postData);
					});
					
					
					/* $(document).keydown(function(event){
						　　if(event.keyCode==13){
						　   $("#LAY-search5").click();
						　　}
						}); */
					
					//监听搜索
					form.on('submit(LAY-search)', function(obj) {		//修改此处
						var field = obj.field;
						var orderTime=field.orderTimeBegin.split('~');
						field.orderTimeBegin=orderTime[0];
						field.orderTimeEnd=orderTime[1];
						table.reload('tableData', {
							where: field,
							 page: { curr : 1 }
						});  
					});
					$(document).on('click', '.layui-table-view tbody tr', function(event) {
						var elemTemp = $(this);
						var tableView = elemTemp.closest('.layui-table-view');
						var trIndex = elemTemp.data('index');
						tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
					})
					form.on('submit(LAY-eat)', function(obj) {		//修改此处
							var a=$("#startTime2").val()
							if(a==""){
								return layer.msg("请先填写同步时间",{icon:2})
							}
						$.ajax({
							url: "${ctx}/personnel/getEatType",
							data: {
								orderTimeBegin:a
							},
							type: "GET",
							beforeSend: function() {
								 indextwo = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									  });
							},
							success: function(result) {
								if(0 == result.code) {
									layer.close(indextwo);
								 	 table.reload("tableData", {
						                page:{}
						              }) 
									layer.msg(result.message, {
										icon: 1,
										time:800
									});
								
								} else {
									layer.close(indextwo);
									layer.confirm(result.message + '请填写考勤初始化', function() {
											layer.close(indextwo);
									})
								}
							},
							error: function() {
								layer.close(indextwo);
								layer.msg("操作失败！请重试", {
									icon: 2
								});
							},
						});
					});
					//封装ajax主方法
					var mainJs = {
						//新增							
					    fAdd : function(data){
					    	$.ajax({
								url: "${ctx}/fince/addMeal",
								data: data,
								type: "POST",
								beforeSend: function() {
									index;
								},
								success: function(result) {
									if(0 == result.code) {
									 	 table.reload("tableData", {
							                page: {
							                }
							              }) 
										layer.msg(result.message, {
											icon: 1,
											time:800
										});
									
									} else {
										layer.msg(result.message, {
											icon: 2,
											time:800
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
					    },
						
					//修改							
				    fUpdate : function(data){
				    	if(data.id==""){
				    		return;
				    	}
				    	$.ajax({
							url: "${ctx}/fince/addMeal",
							data: data,
							type: "POST",
							beforeSend: function() {
								index;
							},
							success: function(result) {
								if(0 == result.code) {
								 	 table.reload("tableData", {
						                page: {
						                }
						              }) 
									layer.msg(result.message, {
										icon: 1,
										time:800
									});
								
								} else {
									layer.msg(result.message, {
										icon: 2,
										time:800
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
