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
<title>单餐费用</title>
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
							<td><input id="startTime"  name="time" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>物料:</td>
							<td><select class="form-control" id="singleMealConsumptionId" lay-search="true"  name="singleMealConsumptionId"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>报餐类型:</td>
							<td><select class="form-control" name="type">
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
						</tr>
					</table>
				</div>
			</div>
			<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
			
			
			
		</div>
	</div>
	
	<!-- <form action="" id="layuiadmin-form-admin"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>查询时间:</td>
							<td><input id="monthDate9" style="width: 320px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" type="button"  lay-submit lay-filter="LAY-searchsumday">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<div class="layui-form-item">
				<div class="layui-input-inline">
					<table><tr><td>
					<input type="text" style="width: 150px;"  name="keyValue" id="keyValue"
						value="物料分类"
						class="layui-input laydate-icon">
						</td><td>
					<input type="text" style="width: 150px;"  name="keyValue" id="keyValue"
						 
						class="layui-input laydate-icon">	
					</td></tr></table>	
				</div>
			</div>
		</div>
	</form>	 -->

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

<script type="text/html" id="toolbar2">
	用餐人数<input id="start"  disabled="disabled" style="width:100px; height: 30px;">  单人费用<input id="price"  disabled="disabled" style="width:100px; height: 30px;">
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
						elem: '#startTime',
						type : 'datetime',
					});
					laydate.render({
						elem: '#monthDate9',
						type: 'date',
						
					}); 
					
					var getdataa={type:"siteType",}
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
			      			  $("#singleMealConsumptionId").html(htmlfrn)
			      			layer.close(indextwo);
					      }
					  });
					
				    var getdataa={type:"costType",}
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
			      			  $("#singleMealConsumptionId").html(htmlfrn)
			      			layer.close(indextwo);
					      }
					  });
					
					
					// 处理操作列
					var fn1 = function(field) {
						return function(d) {
							return [
								'<select name="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' + d.siteTypeId + '">' +
								htmls +
								'</select>'
							].join('');

						};
					};

					var fn2 = function(field) {
						return function(d) {
							return ['<select name="selectTwo" class="selectTwo" lay-filter="lay_selecte" lay-search="true" data-value="' + d.costTypeId + '">',
								htmlfrn+
								'</select>'
							].join('');
						};
						form.render(); 
					};
					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						url: '${ctx}/personnel/costLivingPage' ,
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
								field: "beginTime",
								title: "缴费开始日期",
								align: 'center',
							},{
								field: "endTime",
								title: "缴费结束日期",
								align: 'center',
							},{
								field: "siteTypeId",
								title: "公司所在地",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: fn1('selectOne')
							},{
								field: "costTypeId",
								title: "费用类型",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: fn2('selectTwo')
							},{
								field: "total",
								title: "总量",
								align: 'center',
								edit: 'text',
							},{
								field: "totalCost",
								title: "总费用",
								align: 'center',
								edit: 'text',
							},{
								field: "liveRemark",
								title: "备注",
								align: 'center',
								edit: 'text',
							},{
								field: "averageCost",
								title: "平均每天费用",
								align: 'center',
								 edit: false,
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
							layui.each(tableView.find('td[data-field="beginTime"]'), function(index, tdElem) {
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
												time: value,
											};
											//调用新增修改
											mainJs.fUpdate(postData);
												}
											})
										})
								
							layui.each(tableView.find('td[data-field="endTime"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								laydate.render({
									elem: tdElem.children[0],
									format: 'yyyy-MM-dd 23:59:59',
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
							allField = {id: ''};
							table.addTemp(tableId,allField,function(trElem) {
								// 进入回调的时候this是当前的表格的config
								var that = this;
								// 初始化laydate
								layui.each(trElem.find('td[data-field="beginTime"]'), function(index, tdElem) {
									tdElem.onclick = function(event) {
										layui.stope(event)
									};
									laydate.render({
										elem: tdElem.children[0],
										format: 'yyyy-MM-dd HH:mm:ss',
										done: function(value, date) {
											var trElem = $(this.elem[0]).closest('tr');
											var tableView = trElem.closest('.layui-table-view');
											table.cache[that.id][trElem.data('index')]['beginTime'] = value;
											var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
											var postData = {
												id: id,
												time:value,
											}
											mainJs.fUpdate(postData);
										}
									})
								})
								layui.each(trElem.find('td[data-field="endTime"]'), function(index, tdElem) {
									tdElem.onclick = function(event) {
										layui.stope(event)
									};
									laydate.render({
										elem: tdElem.children[0],
										format: 'yyyy-MM-dd 23:59:59',
										done: function(value, date) {
											var trElem = $(this.elem[0]).closest('tr');
											var tableView = trElem.closest('.layui-table-view');
											table.cache[that.id][trElem.data('index')]['endTime'] = value;
											var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
											var postData = {
												id: id,
												time:value,
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
							    	a++;
							    	if(a==data.length){
							    		flag=true
							    	}
									})
								if(flag==true){
								data.forEach(function(postData,i){
									/* postData.time=$('#startTime').val() */
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
										url: "${ctx}/personnel/deleteSingleMeal",
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
							case 'sumDay':
								var dicDiv=$('#layuiShare');
								/* table.reload("analysisRecuitsumday"); */
								layer.open({
							         type: 1
							        ,title: '每天费用' //不显示标题栏
							        ,closeBtn: false
							        ,zindex:-1
							        ,area:['50%', '90%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro10' //设定一个id，防止重复弹出
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
										table.resize('layuiShare2');
							        }
							        ,end:function(){
							        	$("#layuiShare").hide();
									  } 
							      });
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
					
					
				var data="";
					form.on('submit(LAY-search2)', function(obj) {
						var field = obj.field;
						field.orderTimeBegin=field.time+' 00:00:00';
						field.orderTimeEnd=field.time+' 23:59:59';
						eventd(field);
					})
					
					var eventd=function(data){
						var datae = [],error = false,msg = '';
						var china = ['物料采购和数据跟进费','房（自动生成）','电（自动生成）','煤气（自动生成）','人工绩效','人工工资','水（自动生成）']
						var size;
						var sumPrice;
						$.ajax({
							url:'${ctx}/personnel/getSummaryWage',
							data:{
								orderTimeBegin:data.orderTimeBegin,
								orderTimeEnd:data.orderTimeEnd
							},
							async:false,
							success:function(r){
								if(r.code==0){
									var i =0;
									size=r.data[0].size	
									sumPrice=r.data[0].sumPrice	
									for(var val in r.data[0]){
										if(val == 'size' || val == 'sumPrice')
											continue;
										datae.push({
											type:'',
											content: china[i++],
											price: r.data[0][val]
										})
									}
								}else{
									error = true;
									msg = r.message;
								}
							}
						})
						if(!error){
							$.ajax({
								url:'${ctx}/personnel/getSingle',
								data:{
									orderTimeBegin:data.orderTimeBegin,
									orderTimeEnd:data.orderTimeEnd
								},
								async:false,
								success:function(r){
									if(0==r.code){
										$(r.data).each(function(j,k){
											datae.push({
												type:k.singleMealConsumption.name,
												content:k.content,
												price:k.price,
											})
										})
									}else{
										msg = r.message;
										datae = [];
									}
								}
							})
						}
						table.reload("layuiShare2", {
							data:datae,
							text:{
								none:msg
							},
							done:function(){
								$("#start").val(size);
								$("#price").val(sumPrice);
							}
			              })
					};
					table.render({
						elem: '#layuiShare2',
						data:[],
						toolbar: '#toolbar2', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						colFilterRecord: true,
						loading: true,
						smartReloadModel: true,// 开启智能重载
						cols: [
							[{
								field: "type",
								title: "物料分类",
								align: 'center',
								totalRowText: '合计'
							},{
								field: "content",
								title: "组成项目",
								align: 'center',
							},{
								field: "price",
								title: "每天花费",
								align: 'center',
								totalRow: true
							}
							]
						],
					
					});
					
					
					//监听搜索
					form.on('submit(LAY-search)', function(obj) {		//修改此处
						var field = obj.field;
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
								url: "${ctx}/personnel/saveCostLiving",
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
							url: "${ctx}/personnel/saveCostLiving",
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
