<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/linkSelect/cyStyle.css" media="all">
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/linkSelect/cyType.css" media="all">
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/linkSelect/font-awesome.min.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<script src="${ctx }/static/layui-v2.4.5/linkSelect/transferTool.js"></script>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>报销申请</title>
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
							<td>报销人:</td>
							<td><select class="form-control" id="selectUserId" name="userId"></select></td>
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
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
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
			<!-- 宿舍人员详情 -->
			<div style="display: none;" id="layuiOpen">
			<table id="tableBudget" class="table_th_search" lay-filter="tableBudget"></table>
			</div>
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
	
	
	<form action="" id="layuiadmin-form-admin2"style="padding: 20px 0px 0 50px; display:none;  text-align:">
		<div class="layui-input-normal layui-form">
		<input type="text" name="id" id="hostelId" style="display: none;"><!--阻止回车键提交表单   如果form里面只有一个input type＝text，那么无论有没有submit按钮，在input中回车都会提交表单。
     如果不想回车提交，需要再加一个input type=text，然后设置display:none.  -->
    	<div cyType="transferTool" cyProps="url:'${ctx}/system/user/findAllUser'"  data_value="10,12,11"  id="divID" name="province[]" ></div>
		</div>
	</form>
	
	
	<form action="" id="layuiadmin-form-admin3"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">宿舍名</label>
				<div class="layui-input-inline">
					<input type="text"  name="name"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>
	
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增宿舍</span>
				<span class="layui-btn layui-btn-sm" lay-event="openMeal">报餐价格</span>
			</div>
	</script>
	
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-trans layui-btn-sm"  lay-event="update">人员</a>
	</script>
	
	<script type="text/html" id="barDemo2">
  		<a class="layui-btn layui-btn-trans layui-btn-sm"  lay-event="live">宿舍人员详情</a>
	</script>
	<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug',
				linkSelect:'linkSelect/linkSelect'
			}).define(
				['tablePlug', 'laydate','element','linkSelect'],
				function() {
					var $ = layui.jquery
						,layer = layui.layer //弹层
						,form = layui.form //表单
						,table = layui.table //表格
						,laydate = layui.laydate //日期控件
						,tablePlug = layui.tablePlug //表格插件
						,element = layui.element
						,linkSelect = layui.linkSelect;
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
				 
				  // 多选
				  laydate.render({
				    elem: '#tradeDaysTime',
				    type: 'date',
				    range: '~',
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

					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						url: '${ctx}/personnel/getHostel' ,
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
								field: "name",
								title: "宿舍名",
								align: 'center',
								edit: false,
							}
							,{fixed:'right', title:'宿舍详情', align: 'center', toolbar: '#barDemo2'}
							,{fixed:'right', title:'操作', align: 'center', toolbar: '#barDemo'}]
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
					
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData':
								var	dicDiv=$("#layuiadmin-form-admin3");
								layer.open({
									type:1,
									title:'新增宿舍',
									area:['35%','20%'],
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
											mainJs.fAdd(data.field)
											document.getElementById("layuiadmin-form-admin3").reset();
								        	layui.form.render();
										})
									},end:function(){ 
							        	$('#fightDiv').html("");
									  }
								})
								break;
							case 'cleanTempData':	
									table.cleanTemp(tableId);
							break;
						}
					});

					//监听单元格编辑
					table.on('tool(tableData)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							title=obj.data.name,
							id = data.id,
							userid=data.users,
							myArray = [];
							for (var i = 0; i < userid.length; i++) {
								myArray.push(userid[i].id)
							}
						var a=0;
						if(obj.event === 'update'){
							$("#hostelId").val(id)
							$("#divID").attr("data_value",myArray)
							$("#divID").attr("name","province[]")
							var transferTools = $("[cyType='transferTool']");
						    for (var i = 0; i < transferTools.length; i++) {
						        $(transferTools[i]).transferTool();
						    }
						    form.render();
							var	dicDiv=$("#layuiadmin-form-admin2");
						var index=layer.open({
								type:1,
								title:'宿舍分配',
								area:['35%','60%'],
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
										post={
											jsonName:JSON.stringify(data.field)
										}
										 mainJs.fUpdate(post) 
										layer.close(index);
									})
								},end:function(){ 
						        	$('#fightDiv').html("");
								  }
							})
						}
						//查看宿舍人员详情
						if(obj.event === 'live'){
							
							table.render({
								elem: '#tableBudget',
								size: 'lg',
								url: '${ctx}/personnel/getHostel' ,
								where:{
									id:id,
								},
								request:{
									pageName: 'page' ,//页码的参数名称，默认：page
									limitName: 'size' //每页数据量的参数名，默认：limit
								},
								page: {
								},//开启分页
								loading: true,
								//toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
								//totalRow: true,		 //开启合计行 */
								cellMinWidth: 90,
								colFilterRecord: true,
								smartReloadModel: true,// 开启智能重载
								parseData: function(ret) {
									return {
										code: ret.code,
										msg: ret.message,
										count:ret.data.total,
										data: ret.data.rows[0].users
									}
								},
								cols: [
									[{
										field: "userName",
										title: "姓名",
										align: 'center',
									},{
										field: "name",
										title: "部门",
										align: 'center',
										templet:function(d){
											return d.orgName.name
										}
									},{
										field: "age",
										title: "年龄",
										align: 'center',
									},{
										field: "bed",
										title: "床号",
										align: 'center',
										edit:"text",
									},{
										field: "inLiveDate",
										title: "入住日期",
										align: 'center',
										edit:"text",
									},{
										field: "otLiveDate",
										title: "退房日期",
										align: 'center',
										edit:"text",
									},{
										field: "liveRemark",
										title: "备注",
										align: 'center',
										edit:"text",
									}
									]
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
									layui.each(tableView.find('td[data-field="inLiveDate"]'), function(index, tdElem) {
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
														inLiveDate: value,
													};
													//调用新增修改
													 mainJs.fUpdateUser(postData);
														}
													})
												})
									layui.each(tableView.find('td[data-field="otLiveDate"]'), function(index, tdElem) {
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
														otLiveDate: value,
													};
													//调用新增修改
													 mainJs.fUpdateUser(postData);
														}
													})
												})			
											},
										});
							
							var dicDiv=$('#layuiOpen');
							layer.open({
						         type: 1
						        ,title: title //不显示标题栏
						        ,closeBtn: false
						        ,zindex:-1
						        ,area:['80%', '90%']
						        ,shade: 0.5
						        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
						        ,btn: ['取消']
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
						        ,end:function(){
								  } 
						      });
						}
						
					});
					
					//监听搜索
					form.on('submit(LAY-search)', function(obj) {		//修改此处
						var field = obj.field;
						var orderTime=field.orderTimeBegin.split('~');
						field.orderTimeBegin=orderTime[0];
						field.orderTimeEnd=orderTime[1];
						table.reload('tableData', {
							where: field
						});  
					});
					
					
					//封装ajax主方法
					var mainJs = {
						//新增							
					    fAdd : function(data){
					    	$.ajax({
								url: "${ctx}/fince/addHostel",
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
							url: "${ctx}/fince/updateUserHostelId",
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
					    
					  //修改员工信息							
					    fUpdateUser : function(data){
					    	if(data.id==""){
					    		return;
					    	}
					    	$.ajax({
								url: "${ctx}/fince/updateUser",
								data: data,
								type: "POST",
								beforeSend: function() {
									index;
								},
								success: function(result) {
									if(0 == result.code) {
									 	 table.reload("tableBudget", {
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
