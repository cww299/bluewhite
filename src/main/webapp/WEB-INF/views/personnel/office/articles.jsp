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
<title>办公库存</title>
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
						
							<td>物品名:</td>
							<td><input  style="width: 250px;"  name="name" placeholder="请输入物品名" class="layui-input">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>仓位:</td>
							<td><input  style="width: 250px;"  name="location" placeholder="请输入仓位名" class="layui-input">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>面试时间:</td>
							<td><input id="startTime" style="width: 300px;" name="startTime" placeholder="请输入面试时间" class="layui-input laydate-icon">
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
	
<form action="" id="layuiadmin-form-admin2"
		style="padding: 20px 0px 0 50px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">日期</label>
					<div class="layui-input-inline">
						<input type="text" 
							style="width: 190px;" name="time"
							id="time"	lay-verify="required" placeholder="请输入日期"
							class="layui-input laydate-icon">
					</div>
				</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">数量</label>
				<div class="layui-input-inline">
					<input type="text" 
							style="width: 190px;" name="number"
								 lay-verify="required" placeholder="请输入入库数量"
							class="layui-input laydate-icon">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">备注</label>
				<div class="layui-input-inline">
					<input type="text" 
							style="width: 190px;" name="remark"
							class="layui-input laydate-icon">
				</div>
			</div>
			
		</div>
	</form>

<form action="" id="layuiadmin-form-admin3"
		style="padding: 20px 0px 0 50px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">日期</label>
					<div class="layui-input-inline">
						<input type="text" 
							style="width: 190px;" name="time"
							id="time2"	lay-verify="required" placeholder="请输入日期"
							class="layui-input laydate-icon">
					</div>
				</div>
		
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">数量</label>
				<div class="layui-input-inline">
					<input type="text" 
							style="width: 190px;" name="number"
								 lay-verify="required" placeholder="请输入出库数量"
							class="layui-input laydate-icon">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">部门</label>
				<div class="layui-input-inline">
					<select name="orgNameId"  id="selectorgNameId" lay-search="true"></select>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">领取人</label>
				<div class="layui-input-inline">
					<select name="userId"  id="selectUserId" lay-search="true"></select>
				</div>
			</div>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">备注</label>
				<div class="layui-input-inline">
					<input type="text" 
							style="width: 190px;" name="remark"
							class="layui-input laydate-icon">
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
			<button type="button" class="layui-btn layui-btn-xs" lay-event="inLibrary">入库</button>
			<button type="button" class="layui-btn layui-btn-xs" lay-event="outLibrary">出库</button>
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
					var allUnit;
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
						elem: '#time',
						type: 'datetime',
					});
					
					laydate.render({
						elem: '#time2',
						type: 'datetime',
					});
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
					});
					var getdataa={type:"officeUnit",}
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
			      			  allUnit = result.data;
			      			  $(result.data).each(function(k,j){
			      				htmls +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });
			      			layer.close(indextwo);
					      }
					  });
				    var htmlsh= '<option value="">请选择</option>';
				    $.ajax({
						url: '${ctx}/system/user/findUserList?foreigns=0&isAdmin=false',
						success: function(result) {
							$(result.data).each(function(i, o) {
								htmlsh += '<option value=' + o.id + '>' + o.userName + '</option>'
							})
							$('#selectUserId').html(htmlsh);
							form.render();
						},
					});
					
				    var getdataa={type:"orgName",}
					var htmlst= '<option value="">请选择</option>';
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
			      				htmlst +='<option value="'+j.id+'">'+j.name+'</option>'
			      				$('#selectorgNameId').html(htmlst);
			      				form.render();
			      			  });
			      			layer.close(indextwo);
					      }
					  });
					
					// 处理操作列
					var fn1 = function(field) {
						return function(d) {
							return [
								'<select name="selectOne"   lay-filter="lay_selecte" lay-search="true" data-value="'+( d.unit?d.unit.id :"")+'">',
								htmls +
								'</select>'
							].join('');

						};
					};
					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						url: '${ctx}/personnel/getOfficeSupplies' ,
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
						},//开启分页
						loading: true,
						toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							if(ret.code==0){
								layui.each(ret.data.rows,function(index,item){
									layui.each(allUnit,function(index2,item2){
										if((item.unit && item.unit.id) == item2.id)
											item.unitName = item2.name;
									})
								})
							}
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
								fixed: 'left',
								totalRowText: '合计'
								
							},{
								field: "createdAt",
								title: "时间",
								align: 'center',
							},{
								field: "location",
								title: "仓位",
								filter:true,
								align: 'center',
								edit: 'text',
							},{
								field: "name",
								title: "物品名",
								align: 'center',
								edit: 'text',
							},{
								field: "unitName",
								title: "单位",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: fn1('selectOne')
							},{
								field: "price",
								title: "单价",
								align: 'center',
								edit: 'text',
							},{
								field: "inventoryNumber",
								title: "库存数量",
								align: 'center',
								edit: false,
							},{
								field: "libraryValue",
								title: "库值",
								align: 'center',
								edit: false,
								totalRow: true
							},{
								field: "",
								title: "操作",
								align: 'center',
								toolbar: '#barDemo',
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
									
									},
								});
					
					
					
					
					
					
					
					// 监听表格中的下拉选择将数据同步到table.cache中
					form.on('select(lay_selecte)', function(data) {
						var selectElem = $(data.elem);
						var tdElem = selectElem.closest('td');
						var trElem = tdElem.closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
						var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id;
						if(id)
							table.cache[tableView.attr('lay-id')][trElem.data('index')]['unitName'] = $(data.elem).find('option[value="'+data.value+'"]').html();
						else
							table.cache[tableView.attr('lay-id')][trElem.data('index')]['unitId'] = data.value;
						var postData = {
							id: id,
							unitId:data.value
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
							allField = {id: '',};
							table.addTemp(tableId,allField,function(trElem) {
								var that = this;
								// 初始化laydate
								layui.each(trElem.find('td[data-field="createdAt"]'), function(index, tdElem) {
									tdElem.onclick = function(event) {
										layui.stope(event)
									};
									laydate.render({
										elem: tdElem.children[0],
										format: 'yyyy-MM-dd HH:mm:ss',
										done: function(value, date) {
											var trElem = $(this.elem[0]).closest('tr');
											var tableView = trElem.closest('.layui-table-view');
											table.cache[that.id][trElem.data('index')]['createdAt'] = value;
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
									 mainJs.fAdd(postData);
									table.cleanTemp(tableId);
									})	
								}
						          break;
							case 'deleteSome':
								// 获得当前选中的
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								/* var d = table.checkStatus(tableId).data; */
								layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
									var postData = {
										ids: checkedIds,
									}
									$.ajax({
										url: "${ctx}/product/deleteOfficeSupplies",
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
					
					table.on('tool(tableData)', function(obj){
						 var data = obj.data;
						 var officeSuppliesId=data.id
						 var inventoryNumber=data.inventoryNumber
						 var name=data.name
						switch(obj.event) {
						case 'inLibrary':
							var	dicDiv=$("#layuiadmin-form-admin2");
							layer.open({
								type:1,
								title:name,
								area:['30%','40%'],
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
										data.field.officeSuppliesId=officeSuppliesId
										data.field.flag=1
										mainJs.faddInOut(data.field); 
										document.getElementById("layuiadmin-form-admin2").reset();
							        	layui.form.render();
									})
								},end:function(){ 
						        	document.getElementById("layuiadmin-form-admin2").reset();
						        	layui.form.render();
								  }
							})
							break;
						case 'outLibrary':
							var	dicDiv=$("#layuiadmin-form-admin3");
							layer.open({
								type:1,
								title:name,
								area:['30%','40%'],
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
										data.field.officeSuppliesId=officeSuppliesId
										data.field.flag=0
										if(inventoryNumber<data.field.number){
											return layer.msg('出库数量不能大于剩余库存', {icon: 2});
										}
										mainJs.faddInOut(data.field); 
										document.getElementById("layuiadmin-form-admin3").reset();
							        	layui.form.render();
									})
								},end:function(){ 
						        	document.getElementById("layuiadmin-form-admin3").reset();
						        	layui.form.render();
								  }
							})
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
						var orderTime=field.startTime.split('~');
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
					//封装ajax主方法
					var mainJs = {
						//新增							
					    fAdd : function(data){
					    	$.ajax({
								url: "${ctx}/personnel/addOfficeSupplies",
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
							url: "${ctx}/personnel/addOfficeSupplies",
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
					   
					  //出入库						
					    faddInOut : function(data){
					    	if(data.id==""){
					    		return;
					    	}
					    	$.ajax({
								url: "${ctx}/personnel/addInventoryDetail",
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
