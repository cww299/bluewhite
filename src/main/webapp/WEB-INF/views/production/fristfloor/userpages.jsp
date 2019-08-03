<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
<style type="text/css">
	.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50% !important;
     transform: translateY(-50%);
}
</style>
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员汇总</title>
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
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
				<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
			</div>
	</script>
<script type="text/html" id="switchTpl2">
  <input type="checkbox" name="status" value="{{d.id}}" data-id="{{d.status}}" lay-skin="switch" lay-text="工作|休息" lay-filter="status" {{ d.status == 0 ? 'checked' : '' }}>
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
					var htmls = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					layer.close(index);
					laydate.render({
						elem: '#startTime',
						type : 'datetime',
					});
				
					
					var data = {
				  			type:1,
				  	}
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmls +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
					      }
					  });
					
					// 处理操作列
					var fn1 = function(field) {
						return function(d) {
							return [
								'<select name="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' + (d.group==null ? '' : d.group.id) + '">' +
								htmls +
								'</select>'
							].join('');
							form.render(); 
						};
					};
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						/* size: 'lg', */
						url: '${ctx}/system/user/pages',
						where:{
							orgNameId:48,
							quit:0,
							type:1,
							orderTimeBegin:"2019-08-01 00:00:00",
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
								item.overtimes = '';
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
								field: "userName",
								title: "姓名",
								align: 'center',
								search: true,
								edit: false,
							},{
								field: "orgName",
								title: "部门",
								align: 'center',
								templet:function(d){
									if(d.orgName!=null){
									return d.orgName.name
									}else{
									return ""
									}
								}
							},{
								field: "position",
								title: "职位",
								align: 'center',
								templet:function(d){
									if(d.position!=null){
									return d.position.name
									}else{
									return ""
									}
								}
							},{
								field: "turnWorkTime",
								title: "出勤时长",
								align: 'center',
								edit: 'text',
								/* templet:function(d){
									return "<input class='turnWorkTimes' value="+d.turnWorkTime+" />"
								} */
							},{
								field: "overtimes",
								title: "加班时长",
								align: 'center',
								edit: 'text',
							},{
								field: "price",
								title: "当月预计收入",
								align: 'center',
								edit: 'text',
							},{
								field: "status",
								title: "工作状态",
								align: 'center',
								edit: false,
								filter:true,
								type: 'normal',
								templet:'#switchTpl2', 
								unresize: true
							},{
								field: "groupId",
								align: 'center',
								title: "员工分组",
								edit: false,
								type: 'normal',
								templet: fn1('selectOne')
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
					});
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
						case 'addTempData':
							if($('#startTime').val()==''){
					 			layer.msg('请先选择日期',{icon:2});
					 			return;
					 		}
							allField = {id: '', content: '',type:'0',time:$('#startTime').val()};
							table.addTemp(tableId,allField,function(trElem) {
								// 进入回调的时候this是当前的表格的config
								var that = this;
							
							});
							break;
							case 'saveTempData':
								var data = table.checkStatus(tableId).data;
								console.log(data)
								var arr=new Array()//员工id
								var turnWorkTime=new Array()//出勤时间
								var overtimes=new Array()//加班时间
								data.forEach(function(j,k) {  
									turnWorkTime.push(j.turnWorkTime)
									overtimes.push(j.overtimes)
									arr.push(j.id)
								});
								if(arr.length<=0){
									return layer.msg("至少选择一个！", {icon: 2});
								}
								var postData={
										type:1,
										usersId:arr,
										overtimes:overtimes,
										turnWorkTimes:turnWorkTime,
										allotTime:$("#startTime").val(),
								}
								 mainJs.fAdd(postData);
								
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
						}
					});
	
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
					
					
					/* $(document).keydown(function(event){
						　　if(event.keyCode==13){
						　   $("#LAY-search5").click();
						　　}
						}); */
					
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
					    } 
					}

				}
			)
		</script>
</body>
	<!-- <div class="panel-body">
		<div>
			<table>
				<tr>
					<td>员工姓名:</td>
					<td><input type="text" name="name" id="name"
						class="form-control search-query name" /></td>
					<td>&nbsp;&nbsp;</td>
					<td>小组查询:</td>
					<td id="groupp"></td>
					<td>&nbsp;&nbsp;</td>
					<td>
						<span class="input-group-btn">
							<button type="button" class="btn btn-info  btn-sm btn-3d  searchtask"> 查&nbsp;找</button> </span>
					</td>
				</tr>
			</table>
		</div>
		<h1 class="page-header"></h1>
		<table>
			<tr>
				<td><button type="button"
						class="btn btn-info btn-square btn-sm btn-3d attendance">一键添加考勤</button>&nbsp;&nbsp;</td>
				<td><input id="startTime" placeholder="请输入考勤日期"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button"
						class="btn btn-info btn-square btn-sm btn-3d position">到岗预计小时收入</button>&nbsp;&nbsp;</td>
				<td><input id="endTime" placeholder="请输入时间"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="name" id="workPrice"
					class="form-control search-query" placeholder="预计收入" /></td>
			</tr>
		</table>
		<table class="table table-hover">
			<thead>
				<tr style="font-size: 14px">
					<th class="center"><label> <input type="checkbox"
							class="ace checks" /> <span class="lbl"></span>
					</label></th>
					<th class="text-center">序号</th>
					<th class="text-center">姓名</th>
					<th class="text-center">部门</th>
					<th class="text-center">职位</th>
					<th class="text-center">考勤时间</th>
					<th class="text-center">当月预计收入</th>
					<th class="text-center">工作状态</th>
					<th class="text-center">员工分组</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
			<tbody id="tablecontent" style="font-size: 14px">
			</tbody>
		</table>
		<div id="pager" class="pull-right"></div>
	</div>
</div> -->

	<!-- <script>
	
   jQuery(function($){
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
			 var data={
						page:1,
				  		size:10,	
				} 
			this.init = function(){
			//注册绑定事件
				self.events();
				self.loadPagination(data);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html ='';
			    $.ajax({
				      url:"${ctx}/system/user/pages",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				  var a;
		      				 if(o.group==null){
		      					a=0
		      				 }else{
		      					 a=o.group.id
		      				 }
		      				 var order = i+1;
		      				var k;
		      				 if(o.orgName==null){
		      					 k=""
		      				 }else{
		      					 k=o.orgName.name
		      				 }
		      				 var l;
		      				 if(o.position==null){
		      					 l=""
		      				 }else{
		      					 l=o.position.name
		      				 }
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center ">'+order+'</td>'
		      				+'<td class="text-center ">'+o.userName+'</td>'
		      				+'<td class="text-center ">'+k+'</td>'
		      				+'<td class="text-center ">'+l+'</td>'
		      				+'<td class="text-center "><input class="work"></input></td>'
		      				+'<td class="text-center edit workPrice">'+o.price*1+'</td>'
							+'<td class="text-center" data-status="'+o.status+'" data-id="'+o.id+'"><input type="radio"   class="rest" value="0">工作<input type="radio"   class="rest" value="1">休息 </td>'
							+'<td class="text-center"><div class="groupChange" data-id="'+o.id+'" data-groupid="'+a+'" ></div></td>'
							+'<td class="text-center"> <button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button></td></tr>'
		      			}); 
				        //显示分页
					  laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
						        	var _data = {
						        			page:obj.curr,
									  		size:10,
									  		userName:$('#name').val(),
									  		groupId:$('.selectcomplete').val()
								  	}
						            self.loadPagination(_data);
							     }
					      }
					    });
				        
					   	layer.close(index);
					   	$("#tablecontent").html(html); 
					   	self.loadEvents();
					   self.checked();
					   self.checkedd();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			  this.checked=function(){
					
					$(".rest").each(function(i,o){
							
							var rest=$(o).parent().data("status");
							if($(o).val()==rest){
							
								$(o).attr('checked', 'checked');;
							}
					})
					
				}
			
			  this.checkedd=function(){
					
					$(".checks").on('click',function(){
						
	                    if($(this).is(':checked')){ 
				 			$('.checkboxId').each(function(){  
	                    //此处如果用attr，会出现第三次失效的情况  
	                     		$(this).prop("checked",true);
				 			})
	                    }else{
	                    	$('.checkboxId').each(function(){ 
	                    		$(this).prop("checked",false);
	                    		
	                    	})
	                    }
	                }); 
					
				}
		this.loadEvents = function(){
			
			//修改方法
			$('.updateremake').on('click',function(){
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
								price:$(this).parent().parent('tr').find(".workPrice").text(),
						}
						var index;
						
						$.ajax({
							url:"${ctx}/system/user/update",
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
			
			
			
			
			
			$('.rest').on('click',function(){
				var  del=$(this);
				var id = $(this).parent().data('id');
				var rest = $(this).val();
				
			    	  $.ajax({
							url:"${ctx}/system/user/update",
							data:{
								id:id,
								status:rest,
								},
							type:"POST",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								//选择1
								if(rest>0){
									del.parent().find(".rest").eq(0).prop("checked", false);
										
								}else{
									del.parent().find(".rest").eq(1).prop("checked", false);	
								}
								layer.msg("操作成功！", {icon: 1});
								layer.close(index);
							
						
								
							},
							error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
			    	  
			 
	
			});
			
			//遍历组名信息
			var data={
					page:1,
			  		size:100,	
			  		type:1,

			} 
			var index;
		    var html = '';
		    $.ajax({
			      url:"${ctx}/production/getGroup",
			      data:data,
			      type:"GET",
			     
	      		  success: function (result) {
	      			 $(result.data).each(function(i,o){
	      				html +='<option value="'+o.id+'">'+o.name+'</option>'
	      			}); 
			       var htmlto='<select class="form-control  selectgroupChange"><option value="">去除分组</option>'+html+'</select>'
				   	$(".groupChange").html(htmlto); 
				   	self.chang();
				   	self.selected();
			      },error:function(){
						layer.msg("加载失败！", {icon: 2});
						layer.close(index);
				  }
			  });
	  }
		this.chang=function(){
			$('.selectgroupChange').change(function(){
				var that=$(this);
				var data={
						userIds:that.parent().data("id"),
						groupId:that.val(),
					}
				var _data={
						page:1,
				  		size:10,	
				} 
				$.ajax({
					url:"${ctx}/production/userGroup",
					data:data,
					type:"POST",
					success:function(result){
						if(0==result.code){
							layer.msg("分组成功！", {icon: 1});
							
						}else{
							layer.msg("分组失败", {icon: 2});			
						}
						
					},error:function(){
						layer.msg("操作失败！", {icon: 2});
					}
				})
				
				
			})
		}
		this.selected=function(){
			
			$('.selectgroupChange').each(function(i,o){
				var id=$(o).parent().data("groupid");
				$(o).val(id);
			})
			
		}
		this.events = function(){
			$('.position').on('click',function(){
				var  that=$(this);
				var arr=new Array()
				$(this).parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
					arr.push($(this).val());   
				});
				if(arr.length<=0){
					return layer.msg("必须选择一个用户", {icon: 2});
				}
				if($("#workPrice").val()==""){
					return layer.msg("预计收入不能为空", {icon: 2});
				}
				if($("#endTime").val()==""){
					return layer.msg("时间不能为空", {icon: 2});
				}
				var postData={
						usersId:arr,
						workPrice:$("#workPrice").val(),
						allotTime:$("#endTime").val(),
				}
				
				$.ajax({
					url:"${ctx}/finance/updateAllAttendance",
					data:postData,
		            traditional: true,
					type:"GET",
					beforeSend:function(){
						index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							});
					},
					
					success:function(result){
						if(0==result.code){
							layer.msg("修改成功！", {icon: 1});
						}else{
							layer.msg("添加失败", {icon: 2});
						}
						layer.close(index);
					},error:function(){
						layer.msg("操作失败！", {icon: 2});
						layer.close(index);
					}
				});  
			   })
			
			
			$('.attendance').on('click',function(){
				  var  that=$(this);
				  var arr=new Array()//员工id
				  var time=new Array()//考勤时间
					$(this).parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
						time.push($(this).parent().parent().siblings().find(".work").val());
						arr.push($(this).val());   
					});
				  if(arr.length<=0){
						return layer.msg("至少选择一个！", {icon: 2});
					}
					var data={
							type:1,
							usersId:arr,
							workTimes:time,
							allotTime:$("#startTime").val(),
					}
					$.ajax({
						url:"${ctx}/finance/addAttendance",
						data:data,
			            traditional: true,
						type:"POST",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							if(0==result.code){
							
								layer.msg(result.message, {icon: 1});
							}else{
								layer.msg(result.message, {icon: 2});
							}
							layer.close(index);
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					}); 
			  })
			
			
			//遍历人名组别
			var htmlth="";
			var data = {
		  			type:1,
		  	}
		    $.ajax({
			      url:"${ctx}/production/getGroup",
			      data:data,
			      type:"GET",
	      		  success: function (result) {
	      			  $(result.data).each(function(k,j){
	      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
	      			  });  
	      			 $('#groupp').html("<select class='form-control selectcomplete'><option value="+""+">请选择&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</option>"+htmlth+"</select>") 
			      }
			  });
			
			
			$('.searchtask').on('click',function(){
				var data = {
			  			page:1,
			  			size:10,
			  			userName:$('#name').val(),
			  			groupId:$('.selectcomplete').val()
			  	}
				
	            self.loadPagination(data);
			});
			
	  }
   	}
	var login = new Login();
	  login.init();
}) -->



</html>