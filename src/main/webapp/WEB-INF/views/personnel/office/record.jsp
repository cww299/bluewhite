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
<title>出入记录</title>
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
							<td>时间:</td>
							<td><input id="startTime" style="width: 300px;" name="startTime"  placeholder="请输入时间" class="layui-input laydate-icon">
							<td>&nbsp;&nbsp;</td>
							<td>部门:</td>
							<td><select class="form-control"  name="orgNameId" lay-search="true" id="selectorgNameId">
									
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td>出入库:</td>
							<td><select class="form-control"  name="flag" lay-search="true" >
									<option value="">请选择</option>
									<option  value="0">出库</option>
									<option  value="1">入库</option>
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
	
<div style="display: none;" id="layuiShare">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>查询时间:</td>
							<td><input id="monthDate" style="width: 300px;"  placeholder="请输入开始时间" class="layui-input laydate-icon">
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
			<table id="layuiShare2"  class="table_th_search" lay-filter="layuiShare2"></table>
</div>

	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
				<span class="layui-btn layui-btn-sm" lay-event="share">部门分摊费用</span>
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
					layer.close(index);
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range:'~',
					});
					
					laydate.render({
						elem: '#monthDate',
						type: 'datetime',
						range:'~',
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
					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						url: '${ctx}/personnel/getInventoryDetail' ,
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
									item.name=item.officeSupplies.name
									item.price=item.officeSupplies.price
									item.flag=(item.flag==0 ? "出库" :"入库")
									item.userName=(item.user==null ? "" :item.user.userName)
									item.orgName=(item.orgName==null ? "" :item.orgName.name)
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
								field: "time",
								title: "时间",
								align: 'center',
								edit: false,
							},{
								field: "name",
								title: "物品名",
								align: 'center',
								templet:function(d){
									return d.officeSupplies.name
								}
							},{
								field: "price",
								title: "单价",
								align: 'center',
							},{
								field: "flag",
								title: "出入库",
								align: 'center',
								
							},{
								field: "number",
								title: "数量",
								align: 'center',
								totalRow: true
							},{
								field: "outboundCost",
								title: "领用价值",
								align: 'center',
								totalRow: true
							},{
								field: "userName",
								title: "领取人",
								align: 'center',
								
							},{
								field: "orgName",
								title: "部门",
								align: 'center',
								
							},{
								field: "remark",
								title: "备注",
								align: 'center',
							}]
						],
								});
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
						case 'share':
							var dicDiv=$('#layuiShare');
							table.reload("layuiShare2");
							layer.open({
						         type: 1
						        ,title: '部门分摊' //不显示标题栏
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
									table.resize('layuiShare2');
						        }
						        ,end:function(){
						        	$("#layuiShare").hide();
								  } 
						      });
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
										url: "${ctx}/personnel/deleteInventoryDetail",
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
					form.on('submit(LAY-search2)', function(obj) {
						onlyField=obj.field;
						var orderTime=$("#monthDate").val().split('~');
						onlyField.orderTimeBegin=orderTime[0];
						onlyField.orderTimeEnd=orderTime[1].split(" ")[1] +" 23:59:59";
						eventd(onlyField);
						
					})
					var eventd=function(data){
						table.reload("layuiShare2", {
							url: '${ctx}/personnel/statisticalInventoryDetail',
							where:data,
			              })
					};
					var data="";
					table.render({
						elem: '#layuiShare2',
						where:data,
						/* url: '${ctx}/personnel/Statistics' , */
						data:[],
						request:{
							pageName: 'page' ,
							limitName: 'size' 
						},
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						cellMinWidth: 90,
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								data: ret.data
							}
						},
						cols: [
							[{
								field: "orgName",
								title: "部门",
								align: 'center',
							},{
								field: "sumCost",
								title: "部门费用",
								align: 'center',
							},{
								field: "accounted",
								title: "占比",
								align: 'center',
							}
							]
						],
								});
					
					
					//监听搜索
					form.on('submit(LAY-search)', function(obj) {		//修改此处
						var field = obj.field;
					if($("#startTime").val()!=""){
						var orderTime=$("#startTime").val().split('~');
						field.orderTimeBegin=orderTime[0];
						field.orderTimeEnd=orderTime[1].split(" ")[1] +" 23:59:59";
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
					   
					}

				}
			)
		</script>
</body>

</html>
