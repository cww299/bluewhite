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
<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
<%-- <script src="${ctx }/static/layui-v2.4.5/autocomplete/jquery-ui.js"></script> --%>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/static/css/main.css">  <!-- 界面样式 -->
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script src="${ctx }/static/js/vendor/typeahead.js"></script>
 <!--  <link rel="stylesheet" href="/resources/demos/style.css"> -->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>报销申请</title>
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
							<td><input type="text" name="Username" id="firstNames" class="layui-input" /></td>
							<td>&nbsp&nbsp</td>
							<td>报销内容:</td>
							<td><input type="text" name="content" class="layui-input" /></td>
							<td>&nbsp&nbsp</td>
							<td><select class="form-control" name="expenseDate" id="selectone">
									<option value="2018-10-08 00:00:00">申请日期</option>
								</select></td>
							<td>&nbsp&nbsp</td>
							<td>开始:</td>
							<td><input id="startTime" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp&nbsp</td>
							<td>结束:</td>
							<td><input id="endTime" name="orderTimeEnd" placeholder="请输入结束时间" class="layui-input laydate-icon">
							</td>
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

		


<form action="" id="layuiadmin-form-admin"
		style="padding: 20px 30px 0 60px; display: none; text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<input type="text" name="id" id="usID" style="display:none;">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">供应商名称</label>
				<div class="layui-input-inline">
						<input type="text" name="customerName" id="userId"
						placeholder="请输入供应商名称"
						class="layui-input laydate-icon" data-provide="typeahead">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">票面金额</label>
				<div class="layui-input-inline">
					<input type="text" name="money" id="money"
						lay-verify="required" placeholder="请输入票面金额"
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">税点</label>
				<div class="layui-input-inline">
					<input type="text" name="taxPoint" id="taxPoint"
						lay-verify="required" placeholder="请输入税点"
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">申请日期</label>
				<div class="layui-input-inline">
					<input type="text" name="paymentDate"
						id="paymentDate" lay-verify="required"
						placeholder="请输入付款日期 " class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">扣款原因</label>
				<div class="layui-input-inline">
					<input type="text" name="withholdReason" id="withholdReason"
						lay-verify="required" placeholder="请输入付款日期"
						class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">扣款金额</label>
				<div class="layui-input-inline">
					<input type="text" name="withholdMoney" id="withholdMoney"
						lay-verify="required" placeholder="请输入扣款金额"
						class="layui-input">
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
			</div>
		</script>

	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-trans layui-btn-xs"  lay-event="update">编辑</a>
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
						elem: '#paymentDate',
						type: 'datetime',
					});
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
					});
					laydate.render({
						elem: '#endTime',
						type: 'datetime',
					});
				 
				
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
						url: '${ctx}/fince/getConsumption' ,
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
							limit:15
						},//开启分页
						where:{
							type:4
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
							}, {
								field: "userId",
								title: "供应商名称",
								align: 'center',
								search: true,
								edit: 'text',
								templet: function(d){
									return d.custom.name
								}
							}, {
								field: "money",
								title: "票面金额",
								align: 'center',
								edit: 'text',
							}, {
								field: "taxPoint",
								title: "税点",
								edit: 'text',
							}, {
								field: "expenseDate",
								title: "申请日期",
								edit: 'text'
							}, {
								field: "withholdReason",
								title: "扣款原因",
								edit: 'text'
							}, {
								field: "withholdMoney",
								title: "扣款金额",
								edit: 'text'
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
								var dicDiv=$('#layuiadmin-form-admin');
								document.getElementById("layuiadmin-form-admin").reset();
					        	layui.form.render();
								layer.open({
							         type: 1
							        ,title: "新增" //不显示标题栏
							        ,closeBtn: false
							        ,zindex:-1
							        ,area:['30%', '70%']
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
							        		console.log(self.getIndex())
							        		console.log(data)
							        	var	field={
							        			customerName:data.field.customerName,
							        			money:data.field.money,
							        			customId:self.getIndex(),
							        			expenseDate:data.field.paymentDate,
							        			taxPoint:data.field.taxPoint,
							        			withholdMoney:data.field.withholdMoney,
							        			withholdReason:data.field.withholdReason,
							        			type:4
							        		}
							        	  mainJs.fAdd(field); 
							        	document.getElementById("layuiadmin-form-admin").reset();
							        	layui.form.render();
										})
										
							        }
							        ,end:function(){
							        	document.getElementById("layuiadmin-form-admin").reset();
							        	layui.form.render();
							        	timeAll=""
									  } 
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
								/* data.forEach(function(postData,i){
									 mainJs.fAdd(postData);
									table.cleanTemp(tableId);
									}) */	
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
					});
					
					//编辑
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
					form.on('submit(LAY-search)', function(data) {
						var field = data.field;
						table.reload('tableData', {
							where: field
						});
					});
				      $("#userId").typeahead({
						source : function(query, process) {
							return $.ajax({
								url : '${ctx}/system/user/pages',
								type : 'GET',
								data : {
									userName:query,
								},
								success : function(result) {
									//转换成 json集合
									 var resultList = result.data.rows.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.userName, id:item.id}
					                        //处理 json对象为字符串
					                        return JSON.stringify(aItem);
					                    });
									
									if(result.data.rows==""){
										 self.setIndex("");
									}
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