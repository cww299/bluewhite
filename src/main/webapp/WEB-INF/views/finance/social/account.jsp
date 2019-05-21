<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">

<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/autocomplete.css" media="all">
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<%-- <link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/autocomplete/jquery-ui.css" media="all"> --%>
<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<%-- <script src="${ctx }/static/layui-v2.4.5/autocomplete/jquery-ui.js"></script> --%>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="${ctx }/static/css/bootstrap.min.css"> 
<link rel="stylesheet" href="${ctx }/static/css/main.css">  <!-- 界面样式 -->
  <script src="${ctx }/static/js/vendor/typeahead.js"></script>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>物流申请</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>

<body>
	<div class="layui-card" style="height: 800px;">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>报销人:</td>
							<td><input type="text" name="customerName" id="firstNames" class="layui-input" /></td>
							<td>&nbsp&nbsp</td>
							<td><select class="form-control" name="expenseDate" id="selectone">
									<option value="2018-10-08 00:00:00">预计付款日期</option>
								</select></td>
							<td>&nbsp&nbsp</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<!-- <td>&nbsp&nbsp</td>
							<td>结束:</td>
							<td><input id="endTime" name="orderTimeEnd" placeholder="请输入结束时间" class="layui-input laydate-icon">
							</td> -->
							<td>&nbsp&nbsp</td>
							<td>是否核对:
							<td><select class="form-control" name="flag">
									<option value="">请选择</option>
									<option value="0">未核对</option>
									<option value="1">已核对</option>
							</select></td>
							<td>&nbsp&nbsp</td>
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

<!--新增  -->
<script type="text/html" id="addEditTpl">
	<form action="" id="layuiadmin-form-admin"
		style="padding: 20px 30px 0 60px; text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<input type="text" name="id" id="usID" style="display:none;">

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">报销人</label>
				<div class="layui-input-inline">
						<input type="text" value="{{d.custom.name }}" name="customerName" id="userId"
						placeholder="请输入借款方名称"
						class="layui-input laydate-icon" data-provide="typeahead">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">内容</label>
				<div class="layui-input-inline">
					<input type="text" value="{{d.content }}" name="content" id="content"
						lay-verify="required" placeholder="请输入内容"
						class="layui-input laydate-icon">
				</div>
			</div>
			
			<div class="layui-form-item">
					<label class="layui-form-label" style="width: 130px;">是否是预算</label>
					<div class="layui-input-inline">
						<select name="budget" id="budget">
							<option value="0" {{ d.budget==0?'selected':'' }}>是</option>
							<option value="1" {{ d.budget==1?'selected':'' }}>否</option>
							</select>
					</div>
				</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">借款金额</label>
				<div class="layui-input-inline">
					<input type="text" value="{{d.money }}" name="money" id="money"
						lay-verify="required" placeholder="请输入支付金额"
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">预计付款日期</label>
				<div class="layui-input-inline">
					<input type="text" value="{{d.expenseDate }}" name="expenseDate"
						id="expenseDate" lay-verify="required"
						placeholder="请输入预计付款日期 " class="layui-input">
				</div>
			</div>
		</div>
	</form>	

</script>	
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
				<span class="layui-btn layui-btn-sm" lay-event="update">编辑</span>
			</div>
	</script>

	<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug',
			}).define(
				['tablePlug', 'laydate', 'element','form'],
				function() {
					var $ = layui.jquery
						,layer = layui.layer //弹层
						,form = layui.form //表单
						,table = layui.table //表格
						,laydate = layui.laydate //日期控件
						,tablePlug = layui.tablePlug //表格插件
						,element = layui.element
						,laytpl = layui.laytpl
					//全部字段
					var allField;
					var self = this;
					var _index;
					this.setIndex = function(index){
				  		_index=index;
				  	}
				  	this.getIndex = function(){
				  		return _index;
				  	}
				  	var _cache;
					this.setCache = function(cache){
				  		_cache=cache;
				  	}
				  	this.getCache = function(){
				  		return _cache;
				  	}
					//select全局变量
					var htmls = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					
					laydate.render({
						elem: '#paymentDate',
						type: 'datetime',
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
				 
				
					var mycars=new Array()
					$.ajax({
						url: '${ctx}/system/user/findAllUser',
						type: "GET",
						async: false,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							$(result.data).each(function(i, o) {
								mycars.push(o.userName)
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
					/* var fn1 = function(field) {
						return function(d) {
							return [
								/* '<input type="text" name="HandoverCompany" id="HandoverCompany" class="layui-input" style="position:absolute;z-index:2;width:70%;" lay-verify="required" value="" onkeyup="search()" autocomplete="off">' */
							/* 	'<div id="test"><select name="selectOne" selected = "selected" id="hc_select" lay-filter="lay_selecte" lay-search="true" autocomplete="off"   data-value="' + d.userId + '">' +
								htmls +
								'</select></div>'
							].join('');

						};
					}; */ 
					
					
					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						height:'700px',
						url: '${ctx}/fince/getConsumption' ,
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
							limit:15
						},//开启分页
						where:{
							type:7
						},
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
								field: "withholdReason",
								title: "报销人",
								templet: function(d){
									return d.custom.name
								}
							},{
								field: "content",
								title: "内容",
								align: 'center',
							},{
								field: "budget",
								title: "是否预算",
								align: 'center',
								search: true,
								edit: false,
								templet: function(d){
									if(d.budget==0){
										return "是"
									}else{
									return "否"
									}
								}
							},{
								field: "money",
								title: "支付金额",
								align: 'center',
							}, {
								field: "expenseDate",
								title: "预计付款日期",
								align: 'center',
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
								 addEidt(null)
								break;
							case 'update' :
								 addEidt('edit')
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
								
						}
					});
					
					
					function addEidt(type){
						var data={custom:{name:''},money:'',contactName:'',expenseDate:'',content:'',withholdMoney:'',withholdReason:''};
						var title="新增数据";
						var html="";
						var tpl=addEditTpl.innerHTML;
						var choosed=layui.table.checkStatus("tableData").data;
						var id="";
						if(type=='edit'){
							title="编辑数据"
							if(choosed.length>1){
								layer.msg("无法同时编辑多条信息",{icon:2});
								return;
							}
							data=choosed[0];
							id=data.id
						}
						laytpl(tpl).render(data,function(h){
							html=h;
						})
						layer.open({
							type:1,
							title:title,
							area:['30%','60%'],
							btn:['确认','取消'],
							content:html,
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
									console.log(self.getIndex())
									console.log(self.getCache())
						        	var	field={
						        			id:id,
						        			customerName:data.field.customerName,
						        			content:data.field.content,
						        			money:data.field.money,
						        			budget:data.field.budget,
						        			customId:self.getIndex(),
						        			contactName:data.field.contactName,
						        			expenseDate:data.field.expenseDate,
						        			type:7
						        		}
						        	  mainJs.fAdd(field);
						        	if(id==""){
						        	document.getElementById("layuiadmin-form-admin").reset();
						        	layui.form.render();
						        	}
									})
							}
						})
						form.render();
						laydate.render({
							elem: '#expenseDate',
							type: 'datetime',
						});
						laydate.render({
							elem: '#logisticsDate',
							type: 'datetime',
						});
						//提示查询
						 $("#userId").typeahead({
								source : function(query, process) {
									return $.ajax({
										url : '${ctx}/fince/findCustom',
										type : 'GET',
										data : {
											name:query,
											type:7
										},
										success : function(result) {
											//转换成 json集合
											 var resultList = result.data.map(function (item) {
												 	//转换成 json对象
							                        var aItem = {name: item.name, id:item.id}
							                        //处理 json对象为字符串
							                        return JSON.stringify(aItem);
							                    });
												 self.setIndex(0);
											//提示框返回数据
											 return process(resultList);
										},
									})
									//提示框显示
								}, highlighter: function (item) {
								    //转出成json对象
									 var item = JSON.parse(item);
									return item.name
									//按条件匹配输出
				                }, matcher: function (item) {
				                	//转出成json对象
							        var item = JSON.parse(item);
							        self.setIndex(item.id);
							    	return item.id
							    },
								//item是选中的数据
								updater:function(item){
									//转出成json对象
									var item = JSON.parse(item);
									self.setIndex(item.id);
										return item.name
								},

								
							});
					}
					
					
					
				
				
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