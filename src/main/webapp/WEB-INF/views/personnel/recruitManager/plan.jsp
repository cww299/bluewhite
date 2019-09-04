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
<title>水电明细</title>
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
							<td>日期:</td>
							<td><input id="startTime" style="width: 310px;"  name="time" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" id="LAY-search5" lay-submit lay-filter="LAY-search">
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
	

<div style="display: none;" id="layuiShare">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>查询日期:</td>
							<td><input id="monthDate9" style="width: 180px;" name="time" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-search2">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="layuiShare2"  class="table_th_search" lay-filter="layuiShare"></table>
</div>	
	
	
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
				<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
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
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					laydate.render({
						elem: '#startTimes',
						type: 'month',
					});
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
					});
					var getdataa={type:"orgName",}
					var htmls= '<option value="">请选择</option>';
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
			      				htmls +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });
			      			  $("#siteTypeId").html(htmls)
			      			layer.close(indextwo);
					      }
					  });
					
					var htmlfrn= '<option value="">请选择</option>';
					
				    form.on('select(lay_selecte2)', function(data){
				    	var self = data;
			      			$.ajax({								//获取当前部门下拉框选择的子数据：职位
							      url:"${ctx}/basedata/children",
							      data:{ id:data.value },
							      async:false,
					      		  success: function (result) {				//填充职位下拉框
					      			  	var html='<option value="">请选择</option>'
					      			  	$(result.data).each(function(i,o){
					      			  		html +='<option  value="'+o.id+'">'+o.name+'</option>'
					      				});
					      			    $(data.elem).closest('td').next().find('select').html(html);
					      			    form.render();
							      }
							  });
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
					 })
					// 处理操作列
					var fn1 = function(field) {
						return function(d) {
							return [
								'<select name="selectOne" lay-filter="lay_selecte2" lay-search="true" data-value="' +d.orgNameId+ '">',
								htmls +
								'</select>'
							].join('');
						};
						form.render(); 
					};

					var fn2 = function(d) {
						return function(d) {
							var html = '<option value="">请选择</option>';
							if(d && d.orgNameId)
								$.ajax({								//获取当前部门下拉框选择的子数据：职位
								      url:"${ctx}/basedata/children",
								      data:{ id:d.orgNameId },
								      async:false,
						      		  success: function (result) {				//填充职位下拉框
						      			  	$(result.data).each(function(i,o){
						      			  		html +='<option  value="'+o.id+'">'+o.name+'</option>'
						      				});
								      }
								  });
							return ['<select name="selectTwo" class="selectTwo" lay-filter="lay_selecte" lay-search="true" data-value="' +d.positionId+ '">',
								html,
								'</select>'
							].join('');
						};
						form.render(); 
					};
					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						url: '${ctx}/personnel/getPlan' ,
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
								field: "time",
								title: "所属月份",
								align: 'center',
								templet:function(d){
									return (/\d{4}-\d{1,2}/g.exec(d.time)==null ? "" :/\d{4}-\d{1,2}/g.exec(d.time))
								}
							},{
								field:"orgNameId",
								title: "部门",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: fn1('selectOne')
							},{
								field: "positionId",
								title: "职位",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: fn2('selectTwo')
							},{
								field: "number",
								title: "人数",
								align: 'center',
								edit: 'text',
							},{
								field: "estimate",
								title: "预计到岗时间",
								align: 'center',
								edit: 'text',
							},{
								field: "target",
								title: "招聘目标人数",
								align: 'center',
								edit: 'text',
							},{
								field: "coefficient",
								title: "系数",
								align: 'center',
								edit: 'text',
							}]
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
							// 初始化laydate
							layui.each(tableView.find('td[data-field="time"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								laydate.render({
									elem: tdElem.children[0],
									type:'month',
									done: function(value, date) {
											var id = table.cache[tableView.attr('lay-id')][index].id
											var postData = {
												id: id,
												time: value+'-01 00:00:00',
											};
											//调用新增修改
											mainJs.fUpdate(postData);
											form.render();
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
						}
						postData[field] = data.value
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
							allField = {id: '',time:''};
							table.addTemp(tableId,allField,function(trElem) {
								// 进入回调的时候this是当前的表格的config
								var that = this;
								layui.each(trElem.find('td[data-field="time"]'), function(index, tdElem) {
									tdElem.onclick = function(event) {
										layui.stope(event)
									};
									laydate.render({
										elem: tdElem.children[0],
										type:'month',
										done: function(value, date) {
											var trElem = $(this.elem[0]).closest('tr');
											var tableView = trElem.closest('.layui-table-view');
											table.cache[that.id][trElem.data('index')]['time'] = value;
											var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
										}
									})
								})
							});
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
									 postData.time=postData.time+'-01 00:00:00',
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
										url: "${ctx}/personnel/deletePlan",
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
							case '': 
								table.cleanTemp('tableData');
							break;
						}
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
					
					//监听搜索
					form.on('submit(LAY-search)', function(obj) {		//修改此处
						var field = obj.field;
						var orderTime=field.time.split('~');
						field.orderTimeBegin=orderTime[0];
						field.orderTimeEnd=orderTime[1];
						if(field.orderTimeEnd){
							field.orderTimeEnd = field.orderTimeEnd.split(' ')[1]+' 23:59:59';
						}
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
					//封装ajax主方法
					var mainJs = {
						//新增							
					    fAdd : function(data){
					    	$.ajax({
								url: "${ctx}/personnel/addPlan",
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
							url: "${ctx}/personnel/addPlan",
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
