<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>产品总汇</title>
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
							<td><input   name="name" placeholder="请输入姓名" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>编号:</td>
							<td><input   name="departmentNumber" placeholder="请输入编号" class="layui-input laydate-icon"></td>
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
							<td>工序:</td>
							<td><select class="form-control" name="sourg" id="sourg">
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-search2">
										<i class="layui-icon layuiadmin-button-btn">新增</i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="layuiShare2"  class="table_th_search" lay-filter="layuiShare"></table>
	</div>
	
	<form action="" id="layuiadmin-form-admin2"
		style="padding: 20px 0px 0 50px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">产品名称</label>
				<div class="layui-input-inline">
					<input name="productName" style="width:200px;" lay-filter="id" id="productName" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
		
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">批次号</label>
				<div class="layui-input-inline">
					<input name="bacthNumber" style="width:200px;" lay-filter="id" id="bacthNumber" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">数量</label>
				<div class="layui-input-inline">
					<input name="number" style="width:200px;" lay-filter="id" id="number" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
	
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">备注</label>
				<div class="layui-input-inline">
					<input name="remarks" style="width:200px;" lay-filter="id" id="remarks" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">日期</label>
					<div class="layui-input-inline">
						<input type="text" 
							style="width: 200px; position: absolute; float: left;" name="allotTime"
							id="time" lay-verify="tradeDaysTime" placeholder="请输入日期"
							class="layui-input laydate-icon">
					</div>
				</div>
		</div>
	</form>
	
	
	<script type="text/html" id="barDemo">
			<button type="button" class="layui-btn layui-btn-xs" lay-event="query">添加工序</button>
			<button type="button" class="layui-btn layui-btn-xs" lay-event="queryOut">填写批次</button>
	</script>

	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增</span>
				<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
			</div>
				<input id="start" style="width: 180px; height: 30px;text-align:center;"  placeholder="想要保存工序 请输入名称">
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
			var productId="";
			var self = this;
			this.setIndex = function(index){
		  		_index=index;
		  	}
		  	
		  	this.getIndex = function(){
		  		return _index;
		  	}
		  	function p(s) { return s < 10 ? '0' + s: s; }
			var myDate2 = new Date();
			var myDate = new Date(myDate2.getTime() - 24*60*60*1000);
			var date=myDate.getDate(); 
			var year=myDate.getFullYear();
			var month=myDate.getMonth()+1;
			var day = new Date(year,month,0);
			var x;
			if(date<10){
				x="0"
			}else{
				x=""
			}
			var lastdate = year + '-' + p(month) + '-' + x+date +' '+'00:00:00';
			var lastdate2= year + '-' + p(month) + '-' + x+date;
			//select全局变量
			var htmls = '';
			var htmlsh = '<option value="">请选择</option>';
			var index = layer.load(1, {
				shade: [0.1, '#fff'] //0.1透明度的白色背景
			});
			layer.close(index);
			laydate.render({
				elem: '#startTime',
				type : 'datetime',
			});
			laydate.render({
				elem: '#time',
				type : 'datetime',
			});
			var getdata={type:"warehouse",}
		    $.ajax({
			      url:"${ctx}/basedata/list",
			      data:getdata,
			      type:"GET",
	      		  success: function (result) {
	      			  $(result.data).each(function(k,j){
	      				htmls +='<option value="'+j.id+'">'+j.name+'</option>'
	      			  });  
			      }
			  });
			
		    $.ajax({
			      url:"${ctx}/production/getsoon",
			      type:"GET",
	      		  success: function (result) {
	      			  $(result.data).each(function(k,j){
	      				htmlsh +='<option value="'+j.sourg+'">'+j.sourg+'</option>'
	      			  }); 
	      			  $("#sourg").html(htmlsh);
	      			  form.render(); 
			      }
			  });
			
			var fn1 = function(field) {
				return function(d) {
					return [
						'<select name="selectOne" class="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' +  (d.procedureType==null ? '' : d.procedureType.id) + '">' +
						htmls +
						'</select>'
					].join('');
					form.render(); 
				};
			};
		
		   	tablePlug.smartReload.enable(true); 
			table.render({
				elem: '#tableData',
				size: 'lg', 
				url: '${ctx}/productPages',
				where:{
					type:6
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
					layui.each(ret.data.rows,function(index,item){
						item.overtimes = 0;
					})
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
					},{
						field: "id",
						title: "产品序号",
						align: 'center',
						search: true,
						edit: false,
					},{
						field: "departmentNumber",
						title: "产品编号",
						align: 'center',
					},{
						field: "name",
						title: "产品名",
						align: 'center',
					},{
						field: "departmentPrice",
						title: "生产预计单价",
						align: 'center',
						edit: false,
						templet:function(d){
							return (d.departmentPrice==null ? 0 : d.departmentPrice)
						}
					},{
						field: "hairPrice",
						title: "外发价格",
						align: 'center',
						edit: false,
						templet:function(d){
							return (d.hairPrice==null ? 0 : d.hairPrice)
						}
					},{
						field: "",
						title: "操作",
						align: 'center',
						toolbar: '#barDemo',
					}]
				],
						});
			
			form.on('switch()', function(obj){
				var field=this.name
				var id=this.value
				var a=""
				if(obj.elem.checked==true){
					a=0
				}else{
					a=1
				}
			    var postData = {
						id: id,
						status:a
					}
					//调用新增修改
					mainJs.fUpdate(postData);
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
					userIds: id,
					groupId:data.value
				}
				//调用新增修改
				mainJs.fUpdateGroup(postData);
				form.render(); 
			});
			//监听头工具栏事件
			table.on('toolbar(layuiShare)', function(obj) {
				var config = obj.config;
				var btnElem = $(this);
				var tableId = config.id;
				switch(obj.event) {
				case 'addTempData':
					allField = {name: '',workingTime:'',procedureTypeId:'293'};
					table.addTemp(tableId,allField,function(trElem) {
						// 进入回调的时候this是当前的表格的config
						var that = this;
						// 初始化laydate
					});
					break;
					case 'saveTempData':
						var data = table.getTemp('layuiShare2').data;
						data.forEach(function(data2,i){
							var postData={
									  name:data2.name,
									  sourg:$("#start").val(),
									  workingTime:data2.workingTime,
									  type:6,
									  flag:0,
									  productId:productId,
									  procedureTypeId:data2.procedureTypeId,
								}
							  mainJs.fAdd(postData); 
							table.cleanTemp(tableId);
							})
				          break;
					case 'deleteSome':
						// 获得当前选中的
						var checkedIds = tablePlug.tableCheck.getChecked(tableId);
						layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
							var postData = {
								ids: checkedIds,
							}
							$.ajax({
								url: "${ctx}/production/delete",
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
							              table.reload("layuiShare2", {
							                page: {
							                  curr: configTemp.page.curr - 1
							                }
							              })
							            }else{
							            	table.reload("layuiShare2", {
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
			
			//监听工具事件
			table.on('tool(tableData)', function(obj){
				 var data = obj.data;
				switch(obj.event) {
				case 'query':
					$("#queryId").val(data.id)//把组ID放进查询  方便查询调用
					productId=data.id;//全局变量 赋值产品ID
					var data={
						productId:data.id,
						type:6,
					}
					mainJs.loadworkingTable(data);
					var dicDiv=$('#layuiShare');
					layer.open({
				         type: 1
				        ,title: '工序详情' //不显示标题栏
				        ,closeBtn: false
				        ,zindex:-1
				        ,area:['40%', '90%']
				        ,shade: 0.5
				        ,id: 'LAY_layuipro29' //设定一个id，防止重复弹出
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
				        	$("#layuiShare").hide();
						  } 
				      });
					break;
				case 'queryOut':
					productId2=data.id
					$("#productName").val(data.name)
					var	dicDiv=$("#layuiadmin-form-admin2");
					layer.open({
						type:1,
						title:'填写批次',
						area:['30%','50%'],
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
								data.field.type=6;
								data.field.flag=0;
								data.field.bacthDepartmentPrice=(data.field.bacthDepartmentPrice==null ? 0 : data.field.bacthDepartmentPrice)
								data.field.bacthHairPrice=(data.field.bacthHairPrice==null ? 0 : data.field.bacthHairPrice)
								data.field.productId=productId2
								 mainJs.fAdd2(data.field);  
								document.getElementById("layuiadmin-form-admin2").reset();
					        	layui.form.render();
							})
						},end:function(){ 
				        	document.getElementById("layuiadmin-form-admin2").reset();
				        	layui.form.render();
						  }
					})
					break;
				}
			})
			
			//监听单元格编辑
			table.on('edit(tableData)', function(obj) {
				var value = obj.value ,//得到修改后的值
					data = obj.data ,//得到所在行所有键值
					field = obj.field, //得到字段
					id = data.id;
					if(field=="turnWorkTime"){
						
					}else if(field=="overtimes") {
						
					}else{
						var postData = {
							id:id,
							[field]:value
						}
						//调用新增修改
						mainJs.fUpdate(postData);
					}
			});
			
			//监听工序单元格编辑
			table.on('edit(layuiShare)', function(obj) {
				var value = obj.value ,//得到修改后的值
					data = obj.data ,//得到所在行所有键值
					field = obj.field, //得到字段
					id = data.id;
						var postData = {
							id:id,
							sourg:$("#start").val(),
							[field]:value
						}
					//调用新增修改
					if(postData.id)
						mainJs.fAdd(postData);
			});
			
			//监听搜索
			form.on('submit(LAY-search)', function(obj) {		//修改此处
				var field = obj.field;
				field.type=6;
				table.reload('tableData', {
					where: field,
					 page: { curr : 1 }
				});  
			});
			//根据选中的工序新增
			form.on('submit(LAY-search2)', function(obj) {	
				var field = obj.field;
			data={
					productId:productId,
					sourg:field.sourg
				}
			$.ajax({
				url: "${ctx}/production/getAdd",
				data: data,
				type: "GET",
				traditional: true,
				beforeSend: function() {
					index;
				},
				success: function(result) {
					if(0 == result.code) {
					 	 table.reload("layuiShare2", {
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
						url: "${ctx}/production/addProcedure",
						data: data,
						type: "POST",
						traditional: true,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							if(0 == result.code) {
							 	 table.reload("layuiShare2", {
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
			
			  //新增							
			    fAdd2 : function(data){
			    	$.ajax({
						url: "${ctx}/bacth/addBacth",
						data: data,
						type: "POST",
						traditional: true,
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
					url: "${ctx}/system/user/update",
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
			    fUpdateGroup : function(data){
			    	if(data.id==""){
			    		return;
			    	}
			    	$.ajax({
						url: "${ctx}/production/userGroup",
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
			    loadworkingTable:function(data){
			    	table.render({
						elem: '#layuiShare2',
						size: 'lg', 
						url: '${ctx}/production/getProcedure',
						where:data,
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
							if(ret.data!=''){
							$("#start").val(ret.data[0].sourg)
							}
							layui.each(ret.data.rows,function(index,item){
								item.overtimes = 0;
							})
							return {
								code: ret.code,
								msg: ret.message,
								count:ret.data.total,
								data: ret.data
							}
						},
						cols: [
							[{
								type: 'checkbox',
								align: 'center',
							},{
								field: "name",
								title: "工序名称",
								align: 'center',
								edit: 'text',
							},{
								field: "workingTime",
								title: "工序时间",
								align: 'center',
								edit: 'text',
							},{
								field: "procedureTypeId",
								align: 'center',
								title: "工序类型",
								edit: false,
								type: 'normal',
								templet: fn1('selectOne')
							}]
						],
								});
						
				},
			}

		}
	)
	
    
    </script>

</body>

</html>