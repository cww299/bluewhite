<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">

<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>


<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>工资申请</title>
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
							<td><input type="text" name="Username" id="firstNames" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td>报销内容:</td>
							<td><input type="text" name="content" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td><select class="form-control" name="expenseDate" id="selectone">
									<option value="2018-10-08 00:00:00">工资申请日期</option>
								</select></td>
							<td>&nbsp;&nbsp;</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<!-- <td>&nbsp&nbsp</td>
							<td>结束:</td>
							<td><input id="endTime" name="orderTimeEnd" placeholder="请输入结束时间" class="layui-input laydate-icon">
							</td> -->
							<td>&nbsp;&nbsp;</td>
							<td>是否核对:
							<td><select class="form-control" name="flag">
									<option value="">请选择</option>
									<option value="0">未核对</option>
									<option value="1">已核对</option>
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
		</div>
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
					//select全局变量
					var htmls = '<option value="">请选择</option>';
					var htmltwo = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
					});
					/* laydate.render({
						elem: '#endTime',
						type: 'datetime',
					}); */
				 
					 var getdata={type:"orgName",}
	      			$.ajax({								//获取部门列表数据，部门下拉框的填充
					      url:"${ctx}/basedata/list",
					      data:getdata,
					      type:"GET",
					      async:false,
					      beforeSend:function(){
					    	  indextwo = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {				//初始填充部门
			      			  $(result.data).each(function(k,j){
			      				htmls +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });
			      			layer.close(indextwo);
					      }
					  });
					form.on('select(lay_selecte)', function(data) {
						alert(1)
					})
					
					var data={
    						id:10,
    					  }
				     $.ajax({
					      url:"${ctx}/basedata/children",
					      data:data,
					      type:"GET",
					      async:false,
					      beforeSend:function(){
					    	  indextwo = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			  $(result.data).each(function(i,o){
			      				htmltwo +='<option value="'+o.id+'">'+o.name+'</option>'
			      			});  
			      			layer.close(indextwo);
					      }
					  });
					
					// 处理操作列
					var fn1 = function(field) {
						return function(d) { 
							return [
								'<select name="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' + d.orgNameId + '">' +
								htmls +
								'</select>'
							].join('');

						};
					};

					var fn2 = function(field) {
						return function(d) {
							return ['<select name="selectTwo" align: "center" lay-filter="lay_selecte" lay-search="true" data-value="' + d.platformId + '">',
								'<option value="0">请选择</option>',
								'<option value="1">广告招聘</option>',
								'<option value="2">前程无忧</option>',
								'<option value="3">58同城</option>',
								'<option value="4">BOSS直聘</option>',
								'<option value="5">扬子人才网</option>',
								'</select>'
							].join('');

						};
					};
					var fn3 = function(field) {
						return function(d) {
							return ['<select name="selectThree" lay-filter="lay_selecte" lay-search="true" data-value="' + d.gender + '">',
								'<option value="0">男</option>',
								'<option value="1">女</option>',
								'</select>'
							].join('');

						};
					};
					
					var fn4 = function(field) {
						return function(d) {
							return ['<select name="selectFour" lay-filter="lay_selecte" lay-search="true" data-value="' + d.type + '">',
								'<option value="0">否</option>',
								'<option value="1">是</option>',
								'</select>'
							].join('');

						};
					};
					
					var fn5 = function(field) {
						return function(d) {
							return ['<select name="selectFive" lay-filter="lay_selecte" lay-search="true" data-value="' + d.typeOne + '">',
								'<option value="0">否</option>',
								'<option value="1">是</option>',
								'</select>'
							].join('');

						};
					};
					
					var fn6 = function(field) {
						return function(d) {
							return ['<select name="selectSix" lay-filter="lay_selecte" lay-search="true" data-value="' + d.typeTwo + '">',
								'<option value="0">否</option>',
								'<option value="1">是</option>',
								'</select>'
							].join('');

						};
					};
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						height:'700px',
						url: '${ctx}/personnel/getRecruit' ,
						where:{
							type:3
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
								align: 'center',
								fixed: 'left'
							},{
								field: "time",
								title: "时间",
								align: 'center',
								edit: 'text'
							},{
								field: "platformId",
								title: "邀约平台",
								align: 'center',
								edit: false,
								type: 'normal',
								templet: fn2('selectTwo')
							},{
								field: "orgNameId",
								title: "部门",
								align: 'center',
								edit: false,
								type: 'normal',
								templet: fn1('selectOne')
							},{
								field: "position",
								title: "职位",
								align: 'center',
								edit: 'text'
							},{
								field: "name",
								title: "面试人姓名",
								align: 'center',
								edit: 'text'
							},{
								field: "gender",
								align: 'center',
								title: "性别",
								edit: false,
								type: 'normal',
								templet: fn3('selectThree')
							},{
								field: "phone",
								title: "电话",
								align: 'center',
								edit: 'text'
							},{
								field: "livingAddress",
								title: "现居住地址",
								align: 'center',
								edit: 'text'
							},{
								field: "entry",
								title: "面试时间",
								align: 'center',
								edit: 'text'
							},{
								field: "type",
								align: 'center',
								title: "是否应面",
								edit: false,
								type: 'normal',
								templet: fn4('selectFour')
							},{
								field: "remarks",
								title: "备注",
								align: 'center',
								edit: 'text'
							},{
								field: "typeOne",
								align: 'center',
								title: "一面",
								edit: false,
								type: 'normal',
								templet: fn5('selectFive')
							},{
								field: "remarksOne",
								title: "一面备注",
								align: 'center',
								edit: 'text'
							},{
								field: "typeTwo",
								align: 'center',
								title: "二面",
								edit: false,
								type: 'normal',
								templet: fn6('selectSix')
							},{
								field: "remarksTwo",
								title: "二面备注",
								align: 'center',
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
							layui.each(tableElem.find('select'), function(index, item) {
								var elem = $(item);
								elem.val(elem.data('value'));
							});
							form.render();
							// 初始化laydate
							layui.each(tableView.find('td[data-field="entry"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								laydate.render({
									elem: tdElem.children[0],
									format: 'HH:mm:ss',
									type: 'time',
									done: function(value, date) {
											var id = table.cache[tableView.attr('lay-id')][index].id
											var postData = {
												id: id,
												entry: value,
											};
											//调用新增修改
											mainJs.fUpdate(postData);
												}
											})
										})
										
							layui.each(tableView.find('td[data-field="time"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								laydate.render({
									elem: tdElem.children[0],
									type: 'datetime',
									done: function(value, date) {
											var id = table.cache[tableView.attr('lay-id')][index].id
											var postData = {
												id: id,
												time: value,
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
								allField = {id: '', name: '', entry: '',typeOne:0,type:0,typeTwo:0,gender:0};
								table.addTemp(tableId,allField,function(trElem) {
									// 进入回调的时候this是当前的表格的config
									var that = this;
									// 初始化laydate
									layui.each(trElem.find('td[data-field="entry"]'), function(index, tdElem) {
										tdElem.onclick = function(event) {
											layui.stope(event)
										};
										laydate.render({
											elem: tdElem.children[0],
											format: 'HH:mm:ss',
											type: 'time',
											done: function(value, date) {
												var trElem = $(this.elem[0]).closest('tr');
												var tableView = trElem.closest('.layui-table-view');
												table.cache[that.id][trElem.data('index')]['entry'] = value;
												var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
												var postData = {
													id: id,
													entry:value,
												}
												mainJs.fUpdate(postData);
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
								 	if(postData.name==""){
							    		return layer.msg("请填写人员姓名", {
											icon: 2,
										});
							    	}
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
										url: "${ctx}/personnel/deleteRecruit",
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
								
							case 'cleanTempData':	
									table.cleanTemp(tableId);
							break;
						}
					});


					//监听搜索
					form.on('submit(LAY-search)', function(data) {
						var field = data.field;
						var orderTime=field.orderTimeBegin.split('~');
						field.orderTimeBegin=orderTime[0];
						field.orderTimeEnd=orderTime[1];
						table.reload('tableData', {
							where: field
						});
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
					
					//封装ajax主方法
					var mainJs = {
						//新增							
					    fAdd : function(data){
					    	$.ajax({
								url: "${ctx}/personnel/addRecruit",
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
							url: "${ctx}/personnel/addRecruit",
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