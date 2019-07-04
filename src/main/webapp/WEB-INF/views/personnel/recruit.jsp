<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">

<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>


<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>招聘信息</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<style type="text/css">
.layui-form-switch{
	
}
</style>
</head>

<body>
	<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>姓名:</td>
							<td><input type="text" name="name" id="firstNames" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>是否应面:
							<td><select class="form-control" name="type">
									<option value="">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option>
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td>一面:
							<td><select class="form-control" name="typeOne">
									<option value="">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option>
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td>二面:
							<td><select class="form-control" name="typeTwo">
									<option value="">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option>
							</select></td>
							<td>&nbsp;&nbsp;</td>
							
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
				<div class="layui-form-item">
				<table>
					<tr>
					<td>入职状态:
							<td><select class="form-control" name="state">
									<option value="">请选择</option>
									<option value="0">未入职</option>
									<option value="1">已入职</option>
									<option value="2">拒绝入职</option>
									<option value="3">即将入职</option>
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td>在职情况:</td>
							<td><select class="form-control" name="quit">
									<option value="">请选择</option>
									<option value="0">在职</option>
									<option value="1">离职</option>
							</select></td>
							<td>
								<div class="layui-input-block">
							      <input type="checkbox" name="like[write]" lay-filter="lockDemo" title="入职在职">
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

<div style="display: none;" id="layuiSharequit">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>查询月份:</td>
							<td><input id="monthDate5" style="width: 180px;" name="time" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="layuiSharequit2">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="layuiSharequit1"  class="table_th_search" lay-filter="layuiShare"></table>
			
			<table id="layuiSharequitquit"  class="table_th_search" lay-filter="layuiSharequitquit"></table>
</div>


<div style="display: none;" id="layuiShare5">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>查询月份:</td>
							<td><input id="monthDate4" style="width: 180px;" name="time" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-search7">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="layuiShare6"  class="table_th_search" lay-filter="layuiShare8"></table>
</div>	

<div style="display: none;" id="analysis">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>查询月份:</td>
							<td><input id="monthDate6" style="width: 180px;" name="time" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-searchAnalysis">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="analysisRecuit"  class="table_th_search" lay-filter="layuiShare8"></table>
			<table id="analysisRecuit2"  class="table_th_search" lay-filter="layuiShare8"></table>
</div>

	<script type="text/html" id="addEditTpl">
	<form action="" id="layuiadmin-form-admin"
		style="padding: 20px 30px 0 60px; text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<input type="text" name="id" id="usID" style="display:none;">

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">面试时间</label>
				<div class="layui-input-inline">
					<input type="text" value="{{d.time }}" name="time"
						id="time" lay-verify="required"
						 class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">面试人</label>
				<div class="layui-input-inline">
						<input type="text" value="{{d.name }}" name="name" id="userId"
						 lay-verify="required"  placeholder="请输入姓名"
						class="layui-input laydate-icon" data-provide="typeahead">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">招聘人</label>
				<div class="layui-input-inline">
						<select name="recruitId" lay-filter="recruitId" id="recruitId" lay-search="true">
								
						</select>

					</div>
			</div>

			<div class="layui-form-item">
					<label class="layui-form-label" style="width: 130px;">邀约平台</label>
					<div class="layui-input-inline">
						<select name="platformId" lay-filter="platformId" id="platformId">
							
						</select>
					</div>
			</div>	
			
			<div class="layui-form-item">
					<label class="layui-form-label" style="width: 130px;">部门</label>
					<div class="layui-input-inline">
						<select name="orgNameId" lay-filter="orgNameId" id="orgNameId" lay-search="true">
								
						</select>
					</div>
			</div>

			<div class="layui-form-item">
					<label class="layui-form-label" style="width: 130px;">职位</label>
					<div class="layui-input-inline" >
					<select name="positionId" lay-filter="positionId" id="position" lay-search="true">
								
						</select>

								
						</select>

					</div>
			</div>			


			<div class="layui-form-item">
					<label class="layui-form-label" style="width: 130px;">性别</label>
					<div class="layui-input-inline">
						<select name="gender" lay-filter="gender" 
							id="gender" lay-search="true"><option value="">请选择</option>
							<option {{d.gender==0 ? "selected" : ""}} value="0">男</option>
							<option {{d.gender==1 ? "selected" : ""}} value="1">女</option>
							</select>
					</div>
			</div>			

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">电话</label>
				<div class="layui-input-inline">
					<input type="text" value="{{d.phone }}" name="phone" id="phone"
						lay-verify="required" placeholder="请输入内容"
						class="layui-input laydate-icon">
				</div>
			</div>
			
			

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">现居住地址</label>
				<div class="layui-input-inline">
					<input type="text" value="{{d.livingAddress }}" name="livingAddress" id="livingAddress"
						lay-verify="required" placeholder="请输入现居住地址"
						class="layui-input laydate-icon">
				</div>
			</div>


			<div class="layui-form-item">
					<label class="layui-form-label" style="width: 130px;">是否应面</label>
					<div class="layui-input-inline">
						<select name="type" lay-filter="type" 
							id="type" lay-search="true"><option value="">请选择</option>
							<option {{d.type==0 ? "selected" : ""}} value="0">否</option>
							<option {{d.type==1 ? "selected" : ""}} value="1">是</option>
							</select>
					</div>
			</div>		  

		   <div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">备注</label>
				<div class="layui-input-inline">
					<input type="text" value="{{d.remarks }}" name="remarks"
						id="remarks" 
						 class="layui-input">
				</div>
			</div>
		</div>
	</form>	
	
	</script>
	
	
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增</span>
				<span class="layui-btn layui-btn-sm" lay-event="update">编辑</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="soon">即将入职</span>
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="audit">一键入职</span>
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="onaudit">拒绝入职</span>
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="summary">招聘汇总</span>
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="short">短期离职</span>
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="quit">离职人员</span>
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="analysis">招聘分析</span>
			</div>
		</script>

<script type="text/html" id="switchTpl">
  <input type="checkbox" name="adopt" value="{{d.id}}" lay-skin="switch" lay-text="通过|不通过" lay-filter="adopt" {{ d.adopt == 1 ? 'checked' : '' }}>
</script>
<script type="text/html" id="switchTpl2">
  <input type="checkbox" name="type" value="{{d.id}}" lay-skin="switch" lay-text="是|否" lay-filter="type" {{ d.type == 1 ? 'checked' : '' }}>
</script>
<script type="text/html" id="switchTpl3">
  <input type="checkbox" name="typeOne" value="{{d.id}}" lay-skin="switch" lay-text="是|否" lay-filter="typeOne" {{ d.typeOne == 1 ? 'checked' : '' }}>
</script>
<script type="text/html" id="switchTpl4">
  <input type="checkbox" name="typeTwo" value="{{d.id}}" lay-skin="switch" lay-text="是|否" lay-filter="typeTwo" {{ d.typeTwo == 1 ? 'checked' : '' }}>
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
						,element = layui.element
						,laytpl = layui.laytpl;
					//全部字段
					var allField;
					//select全局变量
					var htmls = '<option value="">请选择</option>';
					var htmltwo = '<option value="">请选择</option>';
					var htmlth = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
					});
					 laydate.render({
						elem: '#monthDate3',
						type : 'month',
					}); 
				 	laydate.render({
						elem: '#monthDate4',
						type : 'month',
					}); 
				 	laydate.render({
						elem: '#monthDate5',
						type : 'month',
					}); 
				 	laydate.render({
						elem: '#monthDate6',
						type : 'month',
					}); 
					 var getdata={type:"orgName",}
		      			$.ajax({								//获取部门列表数据，部门下拉框的填充
						      url:"${ctx}/basedata/list",
						      data:getdata,
						      type:"GET",
						      async:false,
						      beforeSend:function(){
						    	  indextwo = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {				//初始填充部门
				      			  $(result.data).each(function(k,j){
				      				htmls +='<option value="'+j.id+'">'+j.name+'</option>'
				      			  });
				      		layui.form.render()
				      			layer.close(indextwo);
						      }
						  });
					
					var data="";
				    
					
					// 处理操作列
					var fn1 = function(field) {
						return function(d) { 
							return [
								'<select name="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' + d.orgNameId + '">' +
								htmls +
								'</select>'
							].join('');

						};
					};

					var fn2 = function(field) {
						return function(d) {
							return ['<select name="selectTwo" align: "center" lay-filter="lay_selecte" lay-search="true" data-value="' + d.platformId + '">',
								'<option value="0">请选择</option>',
								'<option value="1">广告招聘</option>',
								'<option value="2">前程无忧</option>',
								'<option value="3">58同城</option>',
								'<option value="4">BOSS直聘</option>',
								'<option value="5">扬子人才网</option>',
								'</select>'
							].join('');

						};
					};
					var fn3 = function(field) {
						return function(d) {
							return ['<select name="selectThree" lay-filter="lay_selecte" lay-search="true" data-value="' + d.gender + '">',
								'<option value="0">男</option>',
								'<option value="1">女</option>',
								'</select>'
							].join('');

						};
					};
					
					var fn4 = function(field) {
						return function(d) {
							return ['<select name="selectFour" lay-filter="lay_selecte" lay-search="true" data-value="' + d.type + '">',
								'<option value="0">否</option>',
								'<option value="1">是</option>',
								'</select>'
							].join('');

						};
					};
					
					var fn5 = function(field) {
						return function(d) {
							return ['<select name="selectFive" lay-filter="lay_selecte" lay-search="true" data-value="' + d.typeOne + '">',
								'<option value="0">否</option>',
								'<option value="1">是</option>',
								'</select>'
							].join('');

						};
					};
					
					var fn6 = function(field) {
						return function(d) {
							return ['<select name="selectSix" lay-filter="lay_selecte" lay-search="true" data-value="' + d.typeTwo + '">',
								'<option value="0">否</option>',
								'<option value="1">是</option>',
								'</select>'
							].join('');

						};
					};
					
				
					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						height:'700px',
						url: '${ctx}/personnel/getRecruit' ,
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
						},//开启分页
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
								field: "name",
								title: "面试人姓名",
								align: 'center',
								fixed : 'left',
							},{
								field: "time",
								title: "面试时间",
								align: 'center',
								width : 160,
							},{
								field: "platformId",
								title: "邀约平台",
								align: 'center',
								edit: false,
								type: 'normal',
								templet:  function(d){ 
									return d.platform.name
									}
							},{
								field: "orgNameId",
								title: "部门",
								align: 'center',
								edit: false,
								templet:  function(d){ 
									return d.orgName.name
									}
							},{
								field: "position",
								title: "职位",
								align: 'center',
								templet:  function(d){ 
									return d.position.name
									}
							},{
								field: "gender",
								align: 'center',
								title: "性别",
								edit: false,
								type: 'normal',
								templet: function(d){ 
									if(d.gender==0){
										return "男"
									}else{
										return "女"
									}
									
								}
							},{
								field: "recruitName",
								title: "招聘人",
								align: 'center',
							},{
								field: "type",
								align: 'center',
								title: "是否应面",
								edit: false,
								type: 'normal',
								templet:'#switchTpl2', unresize: true
							},{
								field: "remarks",
								title: "备注",
								align: 'center',
								edit: 'text'
							},{
								field: "typeOne",
								align: 'center',
								title: "一面",
								edit: false,
								type: 'normal',
								templet:'#switchTpl3', unresize: true
							},{
								field: "remarksOne",
								title: "一面备注",
								align: 'center',
								edit: 'text'
							},{
								field: "typeTwo",
								align: 'center',
								title: "二面",
								edit: false,
								type: 'normal',
								templet:'#switchTpl4', unresize: true
							},{
								field: "remarksTwo",
								title: "二面备注",
								align: 'center',
								edit: 'text'
							},{
								field: "adopt",
								align: 'center',
								title: "面试情况",
								edit: false,
								type: 'normal',
								templet: '#switchTpl', unresize: true
							},{ 
								align: 'center',
								field: "state",
								title: "入职状态", 
								templet:  function(d){ 
									if(d.state==0){
										return '未入职'; 
									}
									if(d.state==1){
										return '已入职';
									}
									if(d.state==2){
										return '拒绝入职';
									}
									if(d.state==3){
										return '即将入职';
									}		
								}
							},{
								field: "phone",
								title: "电话",
								align: 'center',
								width : 120,
							},{
								field: "livingAddress",
								title: "现居住地址",
								align: 'center',
							},{
								field: "testTime",
								title: "时间",
								align: 'center',
								fixed : 'right',
								width : 118,
							},{
								field: "remarksThree",
								title: "备注",
								align: 'center',
								fixed : 'right',
								edit: 'text'
							}]
						],
						//下拉框回显赋值
						done: function(res, curr, count) {
							var tableView = this.elem.next();
							var tableElem = this.elem.next('.layui-table-view');
							// 初始化laydate
							layui.each(tableView.find('div[class="layui-table-fixed layui-table-fixed-r"]').find('td[data-field="testTime"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								console.log(index)
								laydate.render({
									elem: tdElem.children[0],
									format: 'yyyy-MM-dd HH:mm:ss',
									done: function(value, date) {
										var id = table.cache['tableData'][index].id
											var postData = {
												id: id,
												testTime: value,
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
								a=1
							}else{
								a=0
							}
						    var postData = {
									id: id,
									[field]:a
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
								addEidt("")
								break;
							case 'deleteSome':
								// 获得当前选中的
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
									var postData = {
										ids: checkedIds,
									}
									$.ajax({
										url: "${ctx}/personnel/deleteRecruit",
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
								
							
							case 'soon':
								// 获得当前选中的
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								var data = table.checkStatus(tableId).data;//获取选中数据
								if(data[0].adopt==0){
									return layer.msg("面试未通过",{icon: 2})
								} 
								if(data[0].testTime==null){
									return layer.msg("时间不能为空",{icon: 2})
								}
								 if(data[0].state==1){
										return layer.msg("当前员工已入职",{icon: 2})
									} 
								if(checkedIds.length>1){
									return layer.msg("只能选择一条",{icon: 2})
								}
								layer.confirm('您是否确定要审核选中的' + checkedIds.length + '条记录？', function() {
									var postData = {
										ids:checkedIds,
										state:3,
									}
									mainJs.fUpdate2(postData);
								});
								break;
							
							case 'audit':
								// 获得当前选中的
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								var data = table.checkStatus(tableId).data;//获取选中数据
								if(data[0].adopt==0){
									return layer.msg("面试未通过",{icon: 2})
								}
								if(data[0].testTime==null){
									return layer.msg("时间不能为空",{icon: 2})
								}
								if(data[0].state==1){
									return layer.msg("当前员工已入职",{icon: 2})
								}
								if(checkedIds.length>1){
									return layer.msg("只能选择一条",{icon: 2})
								}
								layer.confirm('您是否确定要审核选中的'+ checkedIds.length + '条记录？', function() {
									var postData = {
										ids:checkedIds,
										state:1,
									}
									mainJs.fUpdate2(postData);
								});
								break;
								
							case 'onaudit':
								// 获得当前选中的
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								var data = table.checkStatus(tableId).data;//获取选中数据
								if(data[0].adopt==0){
									return layer.msg("面试未通过",{icon: 2})
								}
								 if(data[0].state==1){
									return layer.msg("当前员工已入职",{icon: 2})
								} 
								if(checkedIds.length>1){
									return layer.msg("只能选择一条",{icon: 2})
								}
								layer.confirm('您是否确定要审核选中的' + checkedIds.length + '条记录？', function() {
									var postData = {
										ids:checkedIds,
										state:2,
									}
									mainJs.fUpdate2(postData);
								});
								break;
								
							case 'summary':
								var dicDiv=$('#layuiShare');
								table.reload("layuiShare2");
								layer.open({
							         type: 1
							        ,title: '招聘汇总' //不显示标题栏
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
							        }
							        ,end:function(){
							        	$("#layuiShare").hide();
									  } 
							      });
								break;	
							
							case 'short':
								var dicDiv=$('#layuiShare5');
								table.reload("layuiShare6");
								layer.open({
							         type: 1
							        ,title: '短期入职离职人员' //不显示标题栏
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
							        }
							        ,end:function(){
							        	$("#layuiShare5").hide();
									  } 
							      });
								break;
								
							case 'quit':
								var dicDiv=$('#layuiSharequit');
								table.reload("layuiSharequit1");
								table.reload("layuiSharequitquit");
								layer.open({
							         type: 1
							        ,title: '离职人员' //不显示标题栏
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
							        }
							        ,end:function(){
							        	$("#layuiSharequit").hide();
									  } 
							      });
								break;
							
							case 'analysis':
								var dicDiv=$('#analysis');
								table.reload("analysisRecuit");
				              table.reload("analysisRecuit2");
								layer.open({
							         type: 1
							        ,title: '招聘分析' //不显示标题栏
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
							        }
							        ,end:function(){
							        	$("#analysis").hide();
									  } 
							      });
								break;
								
							case 'update' :
								 addEidt('edit')
								break;	
						}
					});
					
					form.on('submit(layuiSharequit2)', function(obj) {
						onlyField=obj.field;
						onlyField.time=onlyField.time+'-01 00:00:00';
						eventd3(onlyField);
						
					})
					
					
					form.on('submit(LAY-search7)', function(obj) {
						onlyField=obj.field;
						onlyField.time=onlyField.time+'-01 00:00:00';
						eventd2(onlyField);
						
					})
					
					form.on('submit(LAY-search2)', function(obj) {
						onlyField=obj.field;
						onlyField.time=onlyField.time+'-01 00:00:00';
						eventd(onlyField);
						
					})
					
					form.on('submit(LAY-searchAnalysis)', function(obj) {
						onlyField=obj.field;
						onlyField.time=onlyField.time+'-01 00:00:00';
						eventd4(onlyField);
						
					})
					var eventd4=function(data){
						table.reload("analysisRecuit", {
							url: '${ctx}/personnel/analysis' ,
							where:data,
			              });
		              table.reload("analysisRecuit2", {
		            	  url: '${ctx}/personnel/analysis' ,
						where:data,
		              })
					};
					table.render({
						elem: '#analysisRecuit',
						size: 'lg',
						where:data,
						data:[],
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								data: ret.data.Analysis
							}
						},
						cols: [
							[{
								field: "md1",
								title: "面试通过率",
								align: 'center',
								templet:  function(d){return d.md1+'%'}
							},{
								field: "md2",
								title: "入职率",
								align: 'center',
								templet:  function(d){return d.md2+'%'}
							},{
								field: "md3",
								title: "短期流失率",
								align: 'center',
								templet:  function(d){return d.md3+'%'}
							},{
								field: "md4",
								title: "离职率",
								align: 'center',
								templet:  function(d){return d.md4+'%'}
							},{
								field: "md5",
								title: "留用率",
								align: 'center',
								templet:  function(d){return d.md5+'%'}
							}
							]
						],
					
								});
					
					
					table.render({
						elem: '#analysisRecuit2',
						size: 'lg',
						where:data,
						data:[],
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								data: ret.data.summaryCount
							}
						},
						cols: [
							[{
								field: "md7",
								title: "入职途径",
								align: 'center',
								templet:  function(d){ 
									if(d.md7==1){
										return "广告招聘"
									}
									if(d.md7==2){
										return "前程无忧"
									}
									if(d.md7==3){
										return "58同城"
									}
									if(d.md7==4){
										return "BOSS直聘"
									}
									if(d.md7==5){
										return "扬子人才网"
									}
									
									}
							},{
								field: "md6",
								title: "人数",
								align: 'center',
								
							}
							]
						],
					
								});
					
					var eventd=function(data){
						table.reload("layuiShare2", {
							url: '${ctx}/personnel/Statistics',
							where:data,
			              })
					};
					
					table.render({
						elem: '#layuiShare2',
						size: 'lg',
						where:data,
						/* url: '${ctx}/personnel/Statistics' , */
						data:[],
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								data: ret.data
							}
						},
						cols: [
							[{
								field: "username",
								title: "姓名",
								align: 'center',
								totalRowText: '合计'
							},{
								field: "mod1",
								title: "邀约面试",
								align: 'center',
								totalRow: true
							},{
								field: "mod2",
								title: "应邀面试",
								align: 'center',
								totalRow: true
							},{
								field: "mod3",
								title: "面试合格",
								align: 'center',
								totalRow: true
							},{
								field: "mod4",
								title: "拒绝入职",
								align: 'center',
								totalRow: true
							},{
								field: "mod5",
								title: "已入职且在职",
								align: 'center',
								totalRow: true
							},{
								field: "mod6",
								title: "即将入职",
								align: 'center',
								totalRow: true
							},{
								field: "mod7",
								title: "短期入职离职",
								align: 'center',
								totalRow: true
							}
							]
						],
					
								});
					
					var eventd2=function(data){
						table.reload("layuiShare6", {
							url: '${ctx}/personnel/soon' ,
							where:data,
			              })
					};
					
					table.render({
						elem: '#layuiShare6',
						size: 'lg',
						data:[],
						where:data,
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								data: ret.data
							}
						},
						cols: [
							[{
								field: "name",
								title: "姓名",
								align: 'center',
								totalRowText: '合计'
							},{
								field: "orgName",
								title: "部门",
								align: 'center',
								templet:  function(d){ 
									return d.orgName.name		
								}
							},{
								field: "position",
								title: "职位",
								align: 'center',
								templet:  function(d){ 
									return d.position.name		
								}
							},{
								field: "entry",
								title: "入职时间",
								align: 'center',
								templet:  function(d){ 
									return d.user.entry		
								}
							},{
								field: "quitDate",
								title: "离职时间",
								align: 'center',
								templet:  function(d){ 
									return d.user.quitDate		
								}
							},{
								field: "reason",
								title: "离职原因",
								align: 'center',
								templet:  function(d){ 
									return d.user.reason		
								}
							}
							]
						],
					
								});
					
					var eventd3=function(data){
						table.reload("layuiSharequit1", {
							url: '${ctx}/personnel/usersl' ,
							where:data,
			              });
			              table.reload("layuiSharequitquit", {
							url: '${ctx}/personnel/usersl' ,
							where:data,
			              })
					};
					
					table.render({
						elem: '#layuiSharequit1',
						size: 'lg',
						where:data,
						data:[],
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								data: ret.data.StringUser
							}
						},
						cols: [
							[{
								field: "userName",
								title: "姓名",
								align: 'center',
							},{
								field: "orgName",
								title: "部门",
								align: 'center',
							},{
								field: "positionName",
								title: "职位",
								align: 'center',
							},{
								field: "entry",
								title: "入职时间",
								align: 'center',
							},{
								field: "quitDate",
								title: "离职时间",
								align: 'center',
							},{
								field: "reason",
								title: "离职原因",
								align: 'center',
							}
							]
						],
								});
					table.render({
						elem: '#layuiSharequitquit',
						size: 'lg',
						where:data,
						data:[],
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								data: ret.data.countUser
							}
						},
						cols: [
							[{
								field: "orgName",
								title: "部门",
								align: 'center',
								totalRowText: '合计'
							},{
								field: "count",
								title: "离职人数",
								align: 'center',
								totalRow: true
							}
							]
						],
								});
					
					
					
					
					

					function addEidt(type){
						var data={time:"",platformId:'',orgNameId:'',name:'',gender:'',phone:'',livingAddress:'',entry:'',type:'',remarks:'',typeOne:'',remarksOne:'',typeTwo:'',remarksTwo:'',state:0};
						var title="新增数据";
						var html="";
						var tpl=addEditTpl.innerHTML;
						var choosed=layui.table.checkStatus("tableData").data;
						var id="";
						var state=0;
						var orgNameId="";
						var platformId="";
						if(type=='edit'){
							title="编辑数据"
							if(choosed.length>1){
								layer.msg("无法同时编辑多条信息",{icon:2});
								return;
							}
							data=choosed[0];
							id=data.id;
							state=data.state;
							orgNameId=data.orgNameId;
							positionId=data.positionId;
							recruitId=data.recruitId;
							platformId=data.platformId;
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
									data.field.id=id;
									data.field.state=state;
									if(!(/^1[3456789]\d{9}$/.test(data.field.phone))){ 
										return layer.msg("手机号码有误,请重新填写",{icon: 2}) 
								    } 
									data.field.recruitName=$('#recruitId option:selected').text();
						        	mainJs.fAdd(data.field)
						        	if(id==""){
						        	document.getElementById("layuiadmin-form-admin").reset();
						        	layui.form.render();
						        	}
									})
							}
						})
						form.render();
						 laydate.render({
								elem: '#entry',
								type: 'time',
							});
						 laydate.render({
								elem: '#time',
								type: 'datetime',
							});
						 var getdata={type:"orgName",}
			      			$.ajax({								//获取部门列表数据，部门下拉框的填充
							      url:"${ctx}/basedata/list",
							      data:getdata,
							      type:"GET",
							      async:false,
							      beforeSend:function(){
							    	  indextwo = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									  });
								  }, 
					      		  success: function (result) {				//初始填充部门
					      			  $(result.data).each(function(k,j){
					      				htmls +='<option '+(orgNameId==j.id ? "selected" : "")+' value="'+j.id+'">'+j.name+'</option>'
					      			  });
					      		  $("#orgNameId").html(htmls);
					      		layui.form.render()
					      			layer.close(indextwo);
							      }
							  });
						 
						 var getdata={type:"platform",}
			      			$.ajax({								//获取部门列表数据，部门下拉框的填充
							      url:"${ctx}/basedata/list",
							      data:getdata,
							      type:"GET",
							      async:false,
							      beforeSend:function(){
							    	  indextwo = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									  });
								  }, 
					      		  success: function (result) {				//初始填充部门
					      			  $(result.data).each(function(k,j){
					      				htmlth +='<option '+(platformId==j.id ? "selected" : "")+' value="'+j.id+'">'+j.name+'</option>'
					      			  });
					      		  $("#platformId").html(htmlth);
					      		layui.form.render()
					      			layer.close(indextwo);
							      }
							  });
						 
						 var htmlr='<option value="">请选择</option>';
						 $.ajax({
								url: '${ctx}/system/user/findUserList',
								data:{
									foreigns:0
								},
								type: "GET",
								async: false,
								beforeSend: function() {
									index;
								},
								success: function(result) {
									$(result.data).each(function(i, o) {
										htmlr += '<option '+(recruitId==o.id ? "selected" : "")+' value=' + o.id + '>' + o.userName + '</option>'
									})
									$("#recruitId").html(htmlr)
									layui.form.render()
									layer.close(index);
								},
								error: function() {
									layer.msg("操作失败！", {
										icon: 2
									});
									layer.close(index);
								}
							});
						 
						 
						 var html=""
				      			$.ajax({								//获取当前部门下拉框选择的子数据：职位
								      url:"${ctx}/basedata/children",
								      data:{id:orgNameId},
								      type:"GET",
								      async:false,
						      		  success: function (result) {				//填充职位下拉框
						      			  	$(result.data).each(function(i,o){
							      				  html +='<option '+(positionId==i.id ? "selected" : "")+' value="'+o.id+'">'+o.name+'</option>'
						      				});  
						      			$("#position").html(html); 
						      			layui.form.render()
								      }
								  });
						 form.on('select(orgNameId)', function(data){
							 var html=""
				      			$.ajax({								//获取当前部门下拉框选择的子数据：职位
								      url:"${ctx}/basedata/children",
								      data:{id:data.value},
								      type:"GET",
								      async:false,
						      		  success: function (result) {				//填充职位下拉框
						      			  	$(result.data).each(function(i,o){
							      				  html +='<option  value="'+o.id+'">'+o.name+'</option>'
						      				});  
						      			$("#position").html(html); 
						      			layui.form.render()
								      }
								  });
						 })
			      			
					}
					
					 var check="off";
					form.on('checkbox(lockDemo)', function(obj){
						if(obj.elem.checked==true){
							check="on"
						}else{
						check="off"
						}
					  });
					//监听搜索
					form.on('submit(LAY-search)', function(data) {
						var field = data.field;
						var orderTime=field.orderTimeBegin.split('~');
						field.orderTimeBegin=orderTime[0];
						field.orderTimeEnd=orderTime[1];
						field.time="2019-06-02 11:00:00";
						if(check=="on"){
						field.time="";
						field.testTime="2019-06-02 11:00:00";
						field.quit=0;
						field.state=1;
						}else{
							field.testTime="";
						}
						if(field.state!=""){
							field.testTime="2019-06-02 11:00:00";
							field.time="";
						}
						table.reload('tableData', {
							where: field
						});
					});
					
					
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
					
					//封装ajax主方法
					var mainJs = {
						//新增							
					    fAdd : function(data){
					    	$.ajax({
								url: "${ctx}/personnel/addRecruit",
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
							url: "${ctx}/personnel/addRecruit",
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
					    fUpdate2 : function(data){
					    	if(data.id==""){
					    		return;
					    	}
					    	$.ajax({
								url: "${ctx}/personnel/updateRecruit",
								data: data,
								type: "GET",
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
					    }
					}

				}
			)
		</script>
</body>

</html>