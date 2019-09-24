<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>批次管理</title>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>

</head>

<body>
	


<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>产品名:</td>
							<td><input   name="name" placeholder="请输入产品名" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>批次号:</td>
							<td><input   name="bacthNumber" placeholder="请输入批次号" class="layui-input laydate-icon"></td>
							<td>&nbsp;&nbsp;</td>
							<td>时间:</td>
							<td><input id="startTime" style="width: 300px;" name="startTime" placeholder="请输入时间" class="layui-input laydate-icon"></td>
							<td>&nbsp;&nbsp;</td>
							<td>是否完成:
							<td><select class="form-control" name="status">
									<option value="">请选择</option>
									<option value="0">未完成</option>
									<option value="1">完成</option>
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
			<div class="layui-form-item" style="float: left; vertical-align: top">
				<label class="layui-form-label" style="width: 100px;">任务数量</label>
				<div class="layui-input-inline">
					<input name="number" style="width:190px;" lay-filter="id" lay-verify="required" id="number" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
		
			<div class="" style="float: left;margin-left: 200px;">
				<label class="layui-form-label" style="width: 100px;">实际任务时间</label>
				<div class="layui-input-inline">
					<input name="remark" style="width:210px;" lay-filter="id"  id="remark" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item" style="float: left;">
				<label class="layui-form-label" style="width: 100px;">任务分配时间</label>
				<div class="layui-input-inline">
					<input name="allotTime" style="width:190px;" lay-filter="id" id="allotTime" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
	
			<div class="" style="float: left;margin-left: 200px;">
				<label class="layui-form-label" style="width: 100px;">加绩工序</label>
				<div class="layui-input-inline">
					<select class="form-control" lay-filter="lay_selecte4" style="width:200px;" name="sourg" id="workingtw"></select>
				</div>
			</div>
			
			<div class="layui-form-item" style="float: left; ">
				<label class="layui-form-label" style="width: 100px;">工序</label>
				<div class="layui-input-inline">
					<select class="form-control" lay-filter="lay_selecte2" style="width:200px;" name="sourg" id="working"></select>
				</div>
				<div style="float: left; margin-left:330px; position: absolute; z-index: 99" class="checkworking"></div>
			</div>
			
			<div class="" style="float: left;margin-left: 200px;">
				<label class="layui-form-label" style="width: 100px;">完成人</label>
				<div class="layui-input-inline">
					<select class="form-control" lay-filter="lay_selecte3" style="width:200px;" name="sourg" id="complete"></select>
				</div>
				<div style="float: right; margin-left: 10px" class="select"></div>
			</div>
		</div>
	</form>
	
	
	<script type="text/html" id="barDemo">
			<button type="button" class="layui-btn layui-btn-xs" lay-event="queryOut">分配</button>
	</script>

	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
				<span class="layui-btn layui-btn-sm" lay-event="start">一键完成</span>
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
			var productId="";
			var self = this;
			this.setIndex = function(index){
		  		_index=index;
		  	}
		  	
		  	this.getIndex = function(){
		  		return _index;
		  	}
		  	function p(s) { return s < 10 ? '0' + s: s; }
			var myDate = new Date();
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
			var firstdate = year + '-' + p(month) + '-01'+' '+'00:00:00';
			var lastdate = year + '-' + p(month) + '-' + day.getDate() +' '+'23:59:59';
			var firstdate2 = year + '-' + p(month) + '-'+ x+date+' '+'00:00:00';
			var lastdate2 = year + '-' + p(month) + '-' + x+date+' '+'23:59:59';
			laydate.render({
				elem: '#startTime',
				type: 'datetime',
				range: '~',
				value : firstdate+' ~ '+lastdate,
			});
			//select全局变量
			var htmls = '<option value="0">请选择</option>';
			var htmlsh = '<option value="0">请选择</option>';
			var index = layer.load(1, {
				shade: [0.1, '#fff'] //0.1透明度的白色背景
			});
			layer.close(index);
			laydate.render({
				elem: '#time',
				type : 'datetime',
			});
			laydate.render({
				elem: '#allotTime',
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
	      			  $("#working").html(htmls);
			      }
			  });
			
			var html="<option value=''>请选择</option>"
				$.ajax({
					url:"${ctx}/task/pickTaskPerformance",
					type:"GET",
					success:function(result){
						$(result.data).each(function(i,o){
						html+='<option value="'+i+'" data-name="'+o.name+'" data-value="'+o.number+'">'+o.name+'</option>'
						})
						$('#workingtw').html(html);
						form.render(); 
					}
				});
			
			form.on('select(lay_selecte2)', function(data){
				var data={
						   productId:productId,
						   type:6,
						   bacthId:bacthId,
						   procedureTypeId:data.value,
				   }
				var htmlfv="";
				//查询各个工序的名称
				   $.ajax({
						url:"${ctx}/production/typeToProcedure",
						data:data,
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						success:function(result){
							$(result.data).each(function(i,o){
								htmlfv +='<div class="input-group"><input type="checkbox" lay-ignore class="checkWork"  style="display: inline;"  value="'+o.id+'" data-name="'+o.name+'" data-residualnumber="'+o.residualNumber+'">'+o.name+' 剩余:'+o.residualNumber+'</input></div>'
							})
							var s="<div class='input-group'><input type='checkbox' lay-ignore style='display:inline;' class='checkWorkAll'>全选</input></div>"
							$('.checkworking').html(s+htmlfv);
							form.render();
							$(".checkWorkAll").on('click',function(){
			                    if($(this).is(':checked')){ 
						 			$('.checkWork').each(function(){  
			                    //此处如果用attr，会出现第三次失效的情况  
			                     		$(this).prop("checked",true);
						 			})
			                    }else{
			                    	$('.checkWork').each(function(){ 
			                    		$(this).prop("checked",false);
			                    		
			                    	})
			                    }
			                });
							layer.close(index);
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
			})
			
		  var getdata={type:"6",}
		    $.ajax({
			      url:"${ctx}/production/getGroup",
			      data:getdata,
			      type:"GET",
	      		  success: function (result) {
	      			  $(result.data).each(function(k,j){
	      				htmlsh +='<option value="">全部</option><option value="'+j.id+'">'+j.name+'</option>'
	      			  });  
	      			  $("#complete").html(htmlsh);
	      			form.render(); 
			      }
			  });
			
			form.on('select(lay_selecte3)', function(data){
				console.log(data)
				var data={
					  id:data.value,
					  type:6,
					  /* temporarilyDate:$('#Time').val(), */
			   }
			var htmltwo="";
			$.ajax({
				url:"${ctx}/production/allGroup",
				data:data,
				type:"GET",
				beforeSend:function(){
					index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						});
				},
				success:function(result){
					$(result.data).each(function(i,o){
					
					$(o.users).each(function(i,o){
						htmltwo +='<div class="input-group"><input type="checkbox" lay-ignore style="display:inline;" class="stuCheckBox" value="'+o.id+'" data-username="'+o.userName+'">'+o.userName+'</input></div>'
					})
					})
					var s="<div class='input-group'><input type='checkbox' lay-ignore style='display:inline;' class='checkall'>全选</input></div>"
					$('.select').html(s+htmltwo)
					$(".checkall").on('click',function(){
		                    if($(this).is(':checked')){ 
					 			$('.stuCheckBox').each(function(){  
		                    //此处如果用attr，会出现第三次失效的情况  
		                     		$(this).prop("checked",true);
					 			})
		                    }else{
		                    	$('.stuCheckBox').each(function(){ 
		                    		$(this).prop("checked",false);
		                    		
		                    	})
		                    }
		                });
					layer.close(index);
				},error:function(){
					layer.msg("操作失败！", {icon: 2});
					layer.close(index);
				}
			});
			})
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
				url: '${ctx}/bacth/allBacth',
				where:{
					type:6,
					flag:0,
					status:0,
					orderTimeBegin:firstdate,
			  		orderTimeEnd:lastdate,
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
						field: "bacthNumber",
						title: "批次号",
						align: 'center',
						search: true,
						edit: false,
					},{
						field: "allotTime",
						title: "时间",
						align: 'center',
					},{
						field: "name",
						title: "产品名",
						align: 'center',
						templet:function(d){
							return d.product.name
						}
					},{
						field: "number",
						title: "数量",
						align: 'center',
						edit: 'text',
					},{
						field: "bacthDepartmentPrice",
						title: "预计生产单价",
						align: 'center',
						edit: false,
					},{
						field: "bacthHairPrice",
						title: "外发价格",
						align: 'center',
						edit: false,
					},{
						field: "sumTaskPrice",
						title: "任务价值",
						align: 'center',
						edit: false,
					},{
						field: "regionalPrice",
						title: "地区差价",
						align: 'center',
						edit: false,
					},{
						field: "time",
						title: "当批用时",
						align: 'center',
						edit: false,
					},{
						field: "remarks",
						title: "备注",
						align: 'center',
						edit: 'text',
					},{
						field: "status",
						title: "完成状态",
						align: 'center',
						edit: false,
						templet:function(d){
							return (d.status==0 ? "未完成" :"完成")
						}
					},{
						field: "",
						title: "操作",
						align: 'center',
						toolbar: '#barDemo',
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
					layui.each(tableView.find('td[data-field="allotTime"]'), function(index, tdElem) {
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
										allotTime: value,
									};
									//调用新增修改
									mainJs.fUpdate(postData);
										}
									})
								})
							},
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
			
			
			
			
			
			//监听工具事件
			table.on('tool(tableData)', function(obj){
				 var data = obj.data;
				 var productName=data.product.name
				switch(obj.event) {
				case 'queryOut':
					productId=data.product.id;
					bacthId=data.id;
					var bacthNumber=data.bacthNumber;
					$("#productName").val(data.name)
					var	dicDiv=$("#layuiadmin-form-admin2");
					layer.open({
						type:1,
						title:productName,
						area:['70%','70%'],
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
								var values=new Array()
								var arr=new Array()
								var username=new Array()
								var numberr=new Array()
								$(".stuCheckBox:checked").each(function() {   
									arr.push($(this).val())
									username.push($(this).data('username'));
								}); 
								$(".checkWork:checked").each(function() {  
									values.push($(this).val())
									numberr.push($(this).data('residualnumber'));
								});
								 if(values.length<=0){
										return layer.msg("至少选择一个工序！", {icon: 2});
									}
									if(arr.length<=0){
										return layer.msg("至少选择一个员工！", {icon: 2});
									}
									number=data.field.number;
									for (var i = 0; i < numberr.length; i++) {
										if(numberr[i]-number<0){
											return layer.msg("有工序剩余数量不足！", {icon: 2});
										}
									}	
								var performanceNumber=$("#workingtw option:selected").data('value');
								var performance=$("#workingtw option:selected").text()=="请选择" ? "" : $("#workingtw option:selected").text();
								var allotTime=data.field.allotTime;
								var number=data.field.number;
								var remark=data.field.remark;
								var holeNumber=$(".inputblock").val();
								var postData = {
										type:6,
										bacthId:bacthId,
										procedureIds:values,
										userIds:arr,
										number:number,
										userNames:username,
										performance:performance,
										performanceNumber:performanceNumber,
										productName:productName,
										bacthNumber:bacthNumber,
										allotTime:allotTime,
										productId:productId,
										remark:remark,
								}
								 mainJs.fAdd(postData);  
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
			
			table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
						case 'deleteSome':
							// 获得当前选中的
							var checkedIds = tablePlug.tableCheck.getChecked(tableId);
							layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
								var postData = {
									ids: checkedIds,
								}
								$.ajax({
									url: "${ctx}/bacth/deleteBacth",
									data: postData,
									traditional: true,
									type: "POST",
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
							
						case 'start':
							var checkedIds = tablePlug.tableCheck.getChecked(tableId);
							var postData = {
									ids:checkedIds,
									type:6,
									status:1,
								}
								//调用新增修改
								mainJs.fUpdateStatus(postData);
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
				field.type=6;
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
						url: "${ctx}/task/addTask",
						data: data,
						type: "POST",
						traditional: true,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							if(0 == result.code) {
								$(".checkworking").text("");
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
			
			  //新增							
			    fAdd2 : function(data){
			    	$.ajax({
						url: "${ctx}/task/addTask",
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
					url: "${ctx}/bacth/addBacth",
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
			    fUpdateStatus : function(data){
			    	$.ajax({
						url: "${ctx}/bacth/statusBacth",
						data: data,
						type: "Get",
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
			}

		}
	)
	
    
    </script>

</body>

</html>