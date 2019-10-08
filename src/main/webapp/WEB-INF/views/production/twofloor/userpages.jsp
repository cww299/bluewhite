<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
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
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="saveTempData">一键添加考勤</span>
			</div>
			<input id="startTimes" style="width: 180px; height: 30px;"  name="time" placeholder="请输入考勤日期">
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
				  			type:3,
				  	}
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmls +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
					      $("#group").html(htmls);
					      }
					  });
					
					// 处理操作列
					var fn1 = function(field) {
						return function(d) {
							return [
								'<select name="selectOne" class="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' +  (d.group==null ? '' : d.group.id) + '">' +
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
						url: '${ctx}/system/user/pages',
						where:{
							orgNameIds:84,
							quit:0,
							isType:3,
							foreigns:0,
							orderTimeBegin:lastdate,
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
							var tableElem = this.elem.next('.layui-table-view');
							layui.each(tableElem.find('.layui-table-box').find('select'), function(index, item) {
								var elem = $(item);
								elem.val(elem.data('value'));
							});
							form.render();
							laydate.render({
								elem: '#startTimes',
								type : 'date',
								value:lastdate2,
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
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'saveTempData':
								var data = table.checkStatus(tableId).data;
								console.log(data)
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
										type:3,
										usersId:arr,
										overtimes:overtimes,
										turnWorkTimes:turnWorkTime,
										allotTime:$("#startTimes").val()+' '+'00:00:00',
								}
								 mainJs.fAdd(postData);
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

</html>