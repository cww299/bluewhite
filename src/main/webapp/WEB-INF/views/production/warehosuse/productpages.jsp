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
	
<!-- <div class="panel panel-default">
	<div class="panel-body"  style="height:750px;">
		<table>
			<tr>
				<td>产品编号:</td>
				<td><input type="text" name="number" id="number"
					class="form-control search-query number" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>产品名称:</td>
				<td><input type="text" name="name" id="name"
					class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><span class="input-group-btn">
					<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask"> 查&nbsp;找</button></span>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><span class="input-group-btn">
					<button type="button" id="addproduct" class="btn btn-success  btn-sm btn-3d pull-right"> 新增产品</button>
				</span>
				</td>
					
			</tr>
		</table>
		<h1 class="page-header"></h1>
		<table class="table table-hover">
			<thead>
				<tr>
					<th class="text-center">产品序号</th>
					<th class="text-center">产品编号</th>
					<th class="text-center">产品名</th>
					<th class="text-center">生产预计单价</th>
					<th class="text-center">外发价格</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
			<tbody id="tablecontent"> 
			</tbody>
		</table>
		<div id="pager" class="pull-right"></div>
	</div>

	
</div>
									
						
					
	隐藏框 产品新增开始 
	<div id="addDictDivType" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			PAGE CONTENT BEGINS
			<form class="form-horizontal addDictDivTypeForm">
				<div class="row col-xs-12  col-sm-12  col-md-12 ">
					<div style="height: 30px"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label">产品编号:</label>
						<div class="col-sm-6">
							<input type="text" id="productNumber" class="form-control">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">产品名:</label>
						<div class="col-sm-6">
							<input type="text" id="productName" class="form-control">
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	隐藏框 产品新增结束 
	隐藏框 产品工序开始 
	<div id="addworking" style="display: none;">
		<div class="panel-body">
			<div class="form-group">
				<input type="file" name="file" id="upfile" style="display: inline">
				<button type="button" class="btn btn-success btn-sm" id="btn"
					style="display: inline">点击导入</button>
				<button class="btn btn-sm btn-danger  " id="deleteprocedure">一键删除</button>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="text-center">全选</th>
						<th class="text-center">工序名称</th>
						<th class="text-center">工序时间(秒)</th>
						<th class="text-center">工序类型</th>
						<th class="text-center">操作</th>
					</tr>
				</thead>
				<tbody id="tableworking">
				</tbody>
			</table>
		</div>
	</div>
	隐藏框 产品工序结束 
	隐藏框 批次填写开始 
	<div id="addbatch" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addbatchForm">
				<div class="form-group">
					<label class="col-sm-3 control-label">产品名称:</label>
					<div class="col-sm-6">
						<input type="text" id="proName" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">批次号:</label>
					<div class="col-sm-6">
						<input type="text" id="bacthNumber" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">数量:</label>
					<div class="col-sm-6">
						<input type="text" id="prosum" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">备注:</label>
					<div class="col-sm-6">
						<input type="text" id="remarks" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">批次时间:</label>
					<div class="col-sm-6">
						<input id="Time" placeholder="时间可不填"
							class="form-control laydate-icon"
							onClick="laydate({elem: '#Time', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
					</div>
				</div>
			</form>
		</div>
	</div>
	隐藏框 批次填写结束 
	</section> -->
	
	<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>姓名:</td>
							<td><input   name="userName" placeholder="请输入姓名" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>小组:</td>
							<td><select class="form-control" name="groupId" id="group">
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
							<td>查询月份:</td>
							<td><input id="monthDate3" style="width: 180px;" name="time" placeholder="请输入开始时间" class="layui-input laydate-icon">
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
			var index = layer.load(1, {
				shade: [0.1, '#fff'] //0.1透明度的白色背景
			});
			layer.close(index);
			laydate.render({
				elem: '#startTime',
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
			var fn1 = function(field) {
				return function(d) {
					return [
						'<select name="selectOne" class="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' +  (d.procedureTypeId==null ? '' : d.procedureTypeId) + '">' +
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
						field: "id",
						title: "产品序号",
						align: 'center',
						search: true,
						edit: false,
					},{
						field: "number",
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
						edit: 'text',
					},{
						field: "hairPrice",
						title: "外发价格",
						align: 'center',
						edit: 'text',
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
					allField = {name: '',workingTime:''};
					table.addTemp(tableId,allField,function(trElem) {
						// 进入回调的时候this是当前的表格的config
						var that = this;
						// 初始化laydate
					});
					break;
					case 'saveTempData':
						var data = table.checkStatus(tableId).data;
						var arr=new Array()//员工id
						var turnWorkTime=new Array()//出勤时间
						var overtimes=new Array()//加班时间
						data.forEach(function(j,k) {  
							turnWorkTime.push(j.turnWorkTime)
							if(j.overtimes==""){
								j.overtimes=0
							}
							overtimes.push(j.overtimes)
							arr.push(j.id)
						});
						if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						if($("#startTimes").val()==""){
							return layer.msg("注意添加考勤时间", {icon: 2});
						}
						var postData={
								type:6,
								usersId:arr,
								overtimes:overtimes,
								turnWorkTimes:turnWorkTime,
								allotTime:$("#startTimes").val()+' '+'00:00:00',
						}
						 mainJs.fAdd(postData);
				          break;
				}
			});
			
			//监听工具事件
			table.on('tool(tableData)', function(obj){
				 var data = obj.data;
				switch(obj.event) {
				case 'query':
					$("#queryId").val(data.id)//把组ID放进查询  方便查询调用
					var data={
						productId:data.id,
						type:6,
					}
					mainJs.loadworkingTable(data);
					var dicDiv=$('#layuiShare');
					layer.open({
				         type: 1
				        ,title: '人员详情' //不显示标题栏
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
					var data={
						type:6,
						temporarilyDate:$("#startTime2").val()
					}
					mainJs.loadworking(data);
					var dicDiv=$('#layuiShare2');
					layer.open({
				         type: 1
				        ,title: '人员详情' //不显示标题栏
				        ,closeBtn: false
				        ,zindex:-1
				        ,area:['50%', '90%']
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
						url: "${ctx}/finance/addAttendance",
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
	
  /*  $(document).ready(function() {
        $('#example').dataTable();
    });  */
    /* jQuery(function($){
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
			var _cache;
			this.setCache = function(cache){
		  		_cache=cache;
		  	}
		  	this.getCache = function(){
		  		return _cache;
		  	}
		  	this.setIndex = function(index){
		  		_index=index;
		  	}
		  	
		  	this.getIndex = function(){
		  		return _index;
		  	}
			 var data={
						page:1,
				  		size:13,	
				  		type:1,

				} 
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/productPages",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			
		      			 $(result.data.rows).each(function(i,o){
		      				
		      				html +='<tr>'
		      				+'<td class="text-center id">'+o.id+'</td>'
		      				+'<td class="text-center edit number">'+o.departmentNumber+'</td>'
		      				+'<td class="text-center edit name">'+o.name+'</td>'
		      				+'<td class="text-center  departmentPrice">'+o.departmentPrice*1+'</td>'
		      				+'<td class="text-center  hairPrice">'+o.hairPrice*1+'</td>'
							+'<td class="text-center">' //<button class="btn btn-xs btn-info  btn-trans update" data-id='+o.id+'>编辑</button>  
							+'<button class="btn btn-xs btn-primary btn-trans addprocedure" data-id='+o.id+' data-name='+o.name+'>添加工序</button> <button class="btn btn-xs btn-success btn-trans addbatch" data-id='+o.id+' data-name='+o.name+'>填写批次</button></td></tr>'
							
		      			}); 
		      			self.setIndex(result.data.pageNum);
				        //显示分页
					   	 laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		 
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		type:1,
									  		name:$('#name').val(),
									  		departmentNumber:$('#number').val(),
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   	self.loadEvents();
					   
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			
			this.loadEvents = function(){
				//触发批次弹框
				$('.addbatch').on('click',function(){
					var _index
					var index
					var postData
					var dicDiv=$('#addbatch');
					var name=$(this).data('name');
					var bacthDepartmentPrice=$(this).parent().parent().find('.departmentPrice').text();
					var bacthHairPrice=$(this).parent().parent().find('.hairPrice').text();
					$('#proName').val(name);
					var id=$(this).data('id');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['30%', '55%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"填写批次",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  if($('#bacthNumber').val()==""){
								  return layer.msg("批次号不能为空", {icon: 2});
							  }
							  if($('#prosum').val()==""){
								  return layer.msg("数量不能为空", {icon: 2});
							  }
							  postData={
									  productId:id,
									  bacthNumber:$('#bacthNumber').val(),
									  number:$('#prosum').val(),
									  remarks:$('#remarks').val(),
									  bacthDepartmentPrice:bacthDepartmentPrice,
									  bacthHairPrice:bacthHairPrice,
									  type:1,
									  allotTime:$('#Time').val(),
									  flag:0,
							  }
							   $.ajax({
									url:"${ctx}/bacth/addBacth",
									data:postData,
									type:"POST",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
											layer.msg("添加成功！", {icon: 1});
											layer.close(_index);
											$('.addbatchForm')[0].reset(); 
											//$('#addbatch').hide();
											
										}else{
											layer.msg(result.message, {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								}); 
							},
						  end:function(){
							  $('.addbatchForm')[0].reset(); 
							  $('#addbatch').hide();
						  }
					});
				})
				
				
				
				//触发工序弹框 加载内容方法
				$('.addprocedure').on('click',function(){
					var _index
					var productId=$(this).data('id')
					var name=$(this).data('name')
					var dicDiv=$('#addworking');
					  //打开隐藏框
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:name,
						  content: dicDiv,
						  
						  yes:function(index, layero){
							 
							},
						  end:function(){
							  $('#addworking').hide();
							  data={
									page:self.getIndex(),
								  	size:13,	
								  	type:1,
								  	name:$('#name').val(),
								  	departmentNumber:$('#number').val(),
							  }
							self.loadPagination(data);
						  }
					});
					self.setCache(productId);
					self.loadworking();
					
					
				})
				//修改方法
				$('.update').on('click',function(){
					if($(this).text() == "编辑"){
						$(this).text("保存")
						
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
				        });
					}else{
							$(this).text("编辑")
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							
							var postData = {
									type:1,
									id:$(this).data('id'),
									departmentNumber:$(this).parent().parent('tr').find(".number").text(),
									name:$(this).parent().parent('tr').find(".name").text(),
							}
							
							var index;
							$.ajax({
								url:"${ctx}/updateProduct",
								data:postData,
								type:"POST",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									if(0==result.code){
									layer.msg("修改成功！", {icon: 1});
									layer.close(index);
									}else{
										layer.msg("修改失败！", {icon: 1});
										layer.close(index);
									}
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
							
							
							
							
					}
				})
				
			}
			//弹框内容加载
			this.loadworking=function(){
				//添加工序
					var productId=self.getCache()
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    var htmltwo = '';
				    var htmlth = '';
				    var htmlfr = '';
				    var data={
				    		productId:productId,
				    		type:1,
				    }
				    //遍历工序类型
				  var getdata={type:"productFristQuality",}
	      			$.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdata,
					      type:"GET",
					      beforeSend:function(){
					    	  indextwo = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfr +='<input type="radio" class="Proceduretypeid"  value='+j.id+' >'+j.name+''
			      			  });  
			      			//查询工序
							    $.ajax({
								      url:"${ctx}/production/getProcedure",
								      data:data,
								      type:"GET",
								      beforeSend:function(){
								    	  indextwo = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										  });
									  }, 
						      			 
						      		  success: function (result) {
						      			
						      			  $(result.data).each(function(i,o){
						      				  
						      				htmltwo +='<tr><td class="text-center reste"><label> <input type="checkbox" class="ace checkboxIdtw" value="'+o.id+'"/><span class="lbl"></span></label></td>'
						      				+'<td class="text-center edit workingnametwo id">'+o.name+'</td>'
						      				+'<td class="text-center edit workingtimetwo">'+o.workingTime+'</td>'
						      				+'<td data-id="'+o.id+'" class="text-center" data-code="'+o.procedureType.id+'">'+htmlfr+'</td>' 
											+'<td class="text-center"><button class="btn btn-xs btn-primary btn-3d updateworking" data-id='+o.id+'>编辑</button> </td></tr>'
											
						      			});  
									   	   
									   	layer.close(indextwo);
									   	//新增时 查找工序类型
								      			htmltwo="<tr><td class='text-center'><label><input type='checkbox' class='ace checkstw' /><span class='lbl'></span></label></td><td class='text-center'><input type='text' class='input-large workingname'></td><td class='text-center'><input type='text' class='input-small workingtime' ></td><td class='text-center'>"+htmlfr+"</td><td class='text-center'><button class='btn btn-xs btn-primary btn-3d add' data-productid="+productId+">新增</button></td></tr>"+htmltwo;
								      			$("#tableworking").html(htmltwo); 
								      			
						      			
						      			
									   	  $("#tableworking").html(htmltwo);  
								      			self.loadevenstwo();
									   	self.checked();
									   	self.checkeddd();
								      },error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(indextwo);
									  }
								  });
					      }
					  });
				  
				
			}
this.checkeddd=function(){
				
				$(".checkstw").on('click',function(){
					
                    if($(this).is(':checked')){ 
			 			$('.checkboxIdtw').each(function(){  
                    //此处如果用attr，会出现第三次失效的情况  
                     		$(this).prop("checked",true);
			 			})
                    }else{
                    	$('.checkboxIdtw').each(function(){ 
                    		$(this).prop("checked",false);
                    		
                    	})
                    }
                }); 
				
			}
			this.checked=function(){
				$(".Proceduretypeid").each(function(i,o){
						
						var rest=$(o).parent().data("code");
						if($(o).val()==rest){
							$(o).attr('checked', 'checked')
						}
				})
				
			}	
			this.loadevenstwo=function(){
			
			//修改工序内容
				 $(".updateworking").on('click',function(){
					 if($(this).text() == "编辑"){
							$(this).text("保存")
							
							$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

					            $(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
					        });
						}else{
								$(this).text("编辑")
							$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

						            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

						       
						                $(this).html(obj_text.val()); 
										
								});
								var data={
										id:$(this).data('id'),
										name:$(this).parent().parent('tr').find('.workingnametwo').text(),
										workingTime:$(this).parent().parent('tr').find('.workingtimetwo').text(),
								}
								$.ajax({
									url:"${ctx}/production/addProcedure",
									data:data,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										
									if(result.code==0){
									
										layer.msg("修改成功！", {icon: 1});
										layer.close(index);
									}
								
										
									},
									error:function(){
										layer.msg("修改失败！", {icon: 2});
										layer.close(index);
									}
								});
								
								
								
								
						}
				 })
				   $(".Proceduretypeid").on('click',function(){
					  $(this).parent().find(".Proceduretypeid").each(function(i,o){
							$(o).prop("checked", false);

						})
							 $(this).prop("checked", true);
					var index; 
					var del=$(this);
					var id = $(this).parent().data('id');
					var rest = $(this).val();
					var flag=0;
					if(rest==109){
						flag=1
					}
					if(id!=undefined){
					$.ajax({
						url:"${ctx}/production/addProcedure",
						data:{
							flag:flag,
							id:id,
							procedureTypeId:rest,
							},
						type:"post",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						success:function(result){
							
							del.parent().find(".Proceduretypeid").each(function(i,o){
								$(o).prop("checked", false);

							})
								del.prop("checked", true);
						
							layer.msg("修改成功！", {icon: 1});
							layer.close(index);
						
					
							
						},
						error:function(){
							layer.msg("修改失败！", {icon: 2});
							layer.close(index);
						}
					});
					}
				})  
				//新增工序
				$('.add').on('click',function(){
					var index;
					var postData;
					var workingtime=$(".workingtime").val();
					if($(this).parent().parent().find("input:radio:checked").val()==null){
						return 	layer.msg("工序类型不能为空！", {icon: 2});
					}
					if($(".workingname").val()==""){
						return 	layer.msg("工序名不能为空！", {icon: 2});
					}
					/* if($(".workingtime").val()==""){
						return 	layer.msg("工序时间不能为空！", {icon: 2});
					} 
					var flag=0;
					if($(this).parent().parent().find("input:radio:checked").val()==109){
						flag=1;
					}
					
					postData={
							flag:flag,
							name:$(".workingname").val(),
							workingTime:workingtime,
							  type:1,
							  productId:$(this).data('productid'),
							  procedureTypeId:$(this).parent().parent().find("input:radio:checked").val(),
					  }
					
					   $.ajax({
							url:"${ctx}/production/addProcedure",
							data:postData,
				            traditional: true,//传数组
							type:"post",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
									layer.msg("添加成功！", {icon: 1});
									self.loadworking();
									layer.close(index);
								}else{
									layer.msg("添加失败", {icon: 2});
								}
								
								
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						}); 
				})
			}
			this.events = function(){
				
				 /* 一键删除针工工序 
				$('#deleteprocedure').on('click',function(){
					  var  that=$(this);
					  var arr=new Array()//员工id
						$(this).parent().parent().parent().parent().parent().find(".checkboxIdtw:checked").each(function() {  
							arr.push($(this).val());   
						});
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var data={
								ids:arr,
						}
						console.log(arr)
						var _indexx;
						var index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/production/delete",
							data:data,
							traditional: true,
							type:"GET",
							beforeSend:function(){
								_indexx = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							}, 
							success:function(result){
							if(result.code==0){
								layer.msg(result.message, {icon: 1});
								self.loadworking();
								layer.close(_indexx);
							}else{
								layer.msg(result.message, {icon: 2});
								layer.close(_indexx);
							}
							},
							error:function(){
								layer.msg("删除失败！", {icon: 2});
								layer.close(_indexx);
							}
						});
						});
				  })
				//导入
				$('#btn').on('click',function(){
				if($('#upfile')[0].files[0]==null){
					return layer.msg("请选择需要导入的文件", {icon: 2});
				}
				
					  var imageForm = new FormData();
				
				  			
							imageForm.append("file",$('#upfile')[0].files[0]);
				  			imageForm.append("productId",self.getCache());
				  			imageForm.append("type",1);
				  			imageForm.append("flag",0);
					 $.ajax({
							url:"${ctx}/excel/importProcedure",
							data:imageForm,
							type:"post",
							processData:false,
							contentType: false,
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								
								layer.msg(result.message, {icon: 1});
								self.loadworking();
								layer.close(index);
							},
							error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						}); 
		          
					
				});
				
				
				
				
				//查询
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:1,
				  			name:$('#name').val(),
				  			departmentNumber:$('#number').val(),
				  	}
		            self.loadPagination(data);
				});
				
				
				//新增产品
				$('#addproduct').on('click',function(){
					
					var _index
					var index
					var postData
					var dicDiv=$('#addDictDivType');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['40%', '40%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"新增产品",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							 
							  postData={
									  departmentNumber:$("#productNumber").val(),
									  name:$("#productName").val(),
									  
							  }
							  $.ajax({
									url:"${ctx}/addProduct",
									data:postData,
						            traditional: true,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
											layer.msg("添加成功！", {icon: 1});
											$(".addDictDivTypeForm")[0].reset();
											self.loadPagination(data);
											layer.close(_index);
											//$('#addDictDivType').hide();
										}else{
											layer.msg(result.message, {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
							},
						  end:function(){
							  $('.addDictDivTypeForm')[0].reset(); 
							  $('#addDictDivType').hide();
						
							
						  }
					});
				})
			}
   	}
   			var login = new Login();
				login.init();
			}) */
    
    </script>

</body>

</html>