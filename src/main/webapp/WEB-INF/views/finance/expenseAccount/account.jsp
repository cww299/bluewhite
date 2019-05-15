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
							<td><input type="text" name="Username" id="firstNames" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td>报销内容:</td>
							<td><input type="text" name="content"  class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td><select class="form-control" name="expenseDate" id="selectone">
									<option value="2018-10-08 00:00:00">申请日期</option>
								</select></td>
							<td>&nbsp;&nbsp;</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<!-- <td>结束:</td>
							<td><input id="endTime" name="orderTimeEnd" placeholder="请输入结束时间" class="layui-input laydate-icon">
							</td> -->
							<!-- 修改如下 -->
							<td>&nbsp;&nbsp;</td>
							<td>是否预算:
							<td><select class="form-control" name="budget">
									<option value="">请选择</option>
									<option value="1">是</option>
									<option value="0">否</option>
							</select></td>
							
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
			
			<shiro:hasAnyRoles name="superAdmin,personnel">
   				 <p id="totalAll" style="text-align:center;color:red;"></p>
			</shiro:hasAnyRoles> 
			
		</div>
	</div>
	
	<div style="display: none;" id="layuiOpen">
			<table id="tableBudget" class="table_th_search" lay-filter="tableBudget"></table>
		</div>
	
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
				<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
				<span class="layui-btn layui-btn-sm" lay-event="openBudget">预算报销单</span>
			</div>
	</script>

<script type="text/html" id="toolbar2">
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
					
					getDate();
					//预算报销总计、报销总计、共计。数据获取
					function getDate(){
						if(document.getElementById("totalAll")!=null){
							$.ajax({
								url:"${ctx}/fince/countConsumptionMoney",
								success:function(result){
									if(0==result.code){
										var html="";
										var data=result.data;
										html+='<label>预算报销总计:'+data.budget+'<label>&nbsp;&nbsp;&nbsp;&nbsp;'
											+'<label>报销总计：'+data.nonBudget+'</label>&nbsp;&nbsp;&nbsp;&nbsp;'
											+'<label>共计：'+data.sumBudget+'</label>';
										$('#totalAll').html(html);
									}else{
										$('#totalAll').append('<label style="color:red;">获取数据异常</label>');
									}
								}
							})
						}
					}
					
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
					/* laydate.render({
						elem: '#endTime',
						type: 'datetime',
					}); */
				 
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
						},
						error: function() {
							layer.msg("操作失败！", {
								icon: 2
							});
							layer.close(index);
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
							return ['<select name="selectTwo" lay-filter="lay_selecte" lay-search="true" data-value="' + d.budget + '">',
								'<option value="0">请选择</option>',
								'<option value="1">预算</option>',
								'</select>'
							].join('');

						};
					};
					var fn3 = function(field) {
						return function(d) {
							return ['<select name="selectThree" lay-filter="lay_selecte" lay-search="true" data-value="' + d.settleAccountsMode + '">',
								'<option value="0">请选择</option>',
								'<option value="1">现金</option>',
								'<option value="2">月结</option>',
								'</select>'
							].join('');

						};
					};
					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						height:'700px',
						url: '${ctx}/fince/getConsumption' ,
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
							}, {
								field: "content",
								title: "报销内容",
								align: 'center',
								edit: 'text'
							}, {
								field: "userId",
								title: "报销人",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: fn1('selectOne')
							}, {
								field: "budget",
								title: "是否预算",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: fn2('selectTwo')
							}, {
								field: "money",
								title: "报销申请金额",
								edit: 'text'
							}, {
								field: "expenseDate",
								title: "报销申请日期",
								edit: 'text'
							}, {
								field: "withholdReason",
								title: "扣款事由",
								edit: 'text'
							}, {
								field: "withholdMoney",
								title: "扣款金额",
								edit: 'text'
							}, {
								field: "settleAccountsMode",
								title: "结款模式",
								search: true,
								edit: false,
								type: 'normal',
								templet: fn3('selectThree')
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
								allField = {id: '', content: '', budget: '',userId:'',money: '', expenseDate: '', 
									withholdReason: '',withholdMoney:'',settleAccountsMode:'',type:'1'};
								table.addTemp(tableId,allField,function(trElem) {
									// 进入回调的时候this是当前的表格的config
									var that = this;
									// 初始化laydate
									layui.each(trElem.find('td[data-field="expenseDate"]'), function(index, tdElem) {
										tdElem.onclick = function(event) {
											layui.stope(event)
										};
										laydate.render({
											elem: tdElem.children[0],
											format: 'yyyy-MM-dd HH:mm:ss',
											done: function(value, date) {
												var trElem = $(this.elem[0]).closest('tr');
												var tableView = trElem.closest('.layui-table-view');
												table.cache[that.id][trElem.data('index')]['expenseDate'] = value;
												var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
												var postData = {
													id: id,
													expenseDate:value,
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
								 	if(postData.userId==""){
							    		return layer.msg("请填写报销申请人", {
											icon: 2,
										});
							    	}
							    	if(postData.money=="" || isNaN(postData.money)){
							    		return layer.msg("请填写报销申请金额（且必须为数字）", {
											icon: 2,
										});
							    	} 
							    	if(postData.expenseDate==""){
							    		return layer.msg("请填写报销申请日期", {
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
										url: "${ctx}/fince/deleteConsumption",
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
							case 'openBudget':
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
									if(checkedIds.length>1){
										return layer.msg("只能选择一条数据", {
											icon: 2
										});
									}
								var str = checkedIds.join(',');
								if(str==""){
									return layer.msg("请选择一条数据", {
										icon: 2
									});
								}
								self.setIndex(str)
								table.render({
									elem: '#tableBudget',
									size: 'lg',
									url: '${ctx}/fince/getConsumption' ,
									where:{
										type:1,
										parentId:str
									},
									request:{
										pageName: 'page' ,//页码的参数名称，默认：page
										limitName: 'size' //每页数据量的参数名，默认：limit
									},
									page: {
									},//开启分页
									loading: true,
									toolbar: '#toolbar2', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
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
											field: "content",
											title: "报销内容",
											align: 'center',
											edit: 'text'
										}, {
											field: "userId",
											title: "报销人",
											align: 'center',
											search: true,
											edit: false,
											type: 'normal',
											templet: fn1('selectOne')
										}, {
											field: "money",
											title: "报销申请金额",
											align: 'center',
											edit: 'text'
										}, {
											field: "expenseDate",
											title: "报销申请日期",
											align: 'center',
											edit: 'text'
										}, {
											field: "withholdReason",
											title: "扣款事由",
											align: 'center',
											edit: 'text'
										}, {
											field: "withholdMoney",
											title: "扣款金额",
											align: 'center',
											edit: 'text'
										}, {
											field: "settleAccountsMode",
											title: "结款模式",
											align: 'center',
											search: true,
											edit: false,
											type: 'normal',
											templet: fn3('selectThree')
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
														mainJs.fUpdate(postData);
															}
														})
													})
												},
											});
								var dicDiv=$('#layuiOpen');
								layer.open({
							         type: 1
							        ,title: "预算报销单" //不显示标题栏
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
								break;
							case 'cleanTempData':	
									table.cleanTemp(tableId);
							break;
						}
					});

					
					
					table.on('toolbar(tableBudget)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData':
								allField = {id: '', content: '', budget: '',userId:'',money: '', expenseDate: '', 
									withholdReason: '',withholdMoney:'',settleAccountsMode:'',type:'1',parentId:self.getIndex()};
								table.addTemp(tableId,allField,function(trElem) {
									// 进入回调的时候this是当前的表格的config
									var that = this;
									// 初始化laydate
									layui.each(trElem.find('td[data-field="expenseDate"]'), function(index, tdElem) {
										tdElem.onclick = function(event) {
											layui.stope(event)
										};
										laydate.render({
											elem: tdElem.children[0],
											format: 'yyyy-MM-dd HH:mm:ss',
											done: function(value, date) {
												var trElem = $(this.elem[0]).closest('tr');
												var tableView = trElem.closest('.layui-table-view');
												table.cache[that.id][trElem.data('index')]['expenseDate'] = value;
												var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
												var postData = {
													id: id,
													expenseDate:value,
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
								 	if(postData.userId==""){
							    		return layer.msg("请填写报销申请人", {
											icon: 2,
										});
							    	}
							    	if(postData.money=="" || isNaN(postData.money)){
							    		return layer.msg("请填写报销申请金额（且必须为数字）", {
											icon: 2,
										});
							    	} 
							    	if(postData.expenseDate==""){
							    		return layer.msg("请填写报销申请日期", {
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
										url: "${ctx}/fince/deleteConsumption",
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
					})
					
					
					
					
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
					
					//监听单元格编辑
					table.on('edit(tableBudget)', function(obj) {
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
								url: "${ctx}/fince/addConsumption",
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
					    },
						
					//修改							
				    fUpdate : function(data){
				    	if(data.id==""){
				    		return;
				    	}
				    	$.ajax({
							url: "${ctx}/fince/addConsumption",
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
