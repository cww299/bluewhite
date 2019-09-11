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
	
<!-- <div class="panel panel-default">
<div class="panel-body">
	<table>
		<tr>
			<td>批次号:</td>
			<td><input type="text" name="number" id="number"
				placeholder="请输入批次号"
				class="form-control search-query number" /></td>
			<td>&nbsp;&nbsp;</td>
			<td>产品名称:</td>
			<td><input type="text" name="name" id="name"
				placeholder="请输入产品名称"
				class="form-control search-query name" /></td>
			<td>&nbsp;&nbsp;</td>
			<td>开始时间:</td>
			<td><input id="startTime" placeholder="请输入开始时间"
				class="form-control laydate-icon"
				onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>结束时间:</td>
			<td><input id="endTime" placeholder="请输入结束时间"
				class="form-control laydate-icon"
				onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>完成状态:</td>
			
			<td><select class="form-control" id="selectstate"><option
						value=0>未完成</option>
					<option value=1>已完成</option></select></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="input-group-btn">
				<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask"> 查&nbsp;找</button></span></td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
			<td><span class="input-group-btn">
				<button type="button" class="btn btn-success  btn-sm btn-3d start">一键完成</button> </span></td>
		</tr>
	</table>
	<h1 class="page-header"></h1>		
	<table class="table table-condensed table-hover">
		<thead>
			<tr>
				<th class="center"><label> <input type="checkbox"
						class="ace checks" /> <span class="lbl"></span>
				</label></th>
				<th class="text-center">批次号</th>
				<th class="text-center">时间</th>
				<th class="text-center" style="width: 250px;">产品名</th>
				<th class="text-center">数量</th>
				<th class="text-center">预计生产单价</th>
				<th class="text-center">外发价格</th>
				<th class="text-center">任务价值</th>
				<th class="text-center">地区差价</th>
				<th class="text-center">当批用时</th>
				<th class="text-center">备注</th>
				<th class="text-center">完成状态</th>
				<th class="text-center">操作</th>
			</tr>
		</thead>
		<tbody id="tablecontent">
	
		</tbody>
		<thead>
			<tr>
				<td class="center">合计</td>
				<td class="text-center"></td>
				<td class="text-center"></td>
				<td class="text-center"></td>
				<td class="text-center" id="total"></td>
				<td class="text-center"></td>
				<td class="text-center"></td>
				<td class="text-center" id="totaltw"></td> 修改此处
				<td class="text-center" id="tota2">1</td>
				<td class="text-center"></td>
				<td class="text-center" id="tota3">1</td>
				
				<td class="text-center"></td>
				<td class="text-center"></td>
			</tr>
		</thead>
	</table>
		<div id="pager" class="pull-right"></div>
	</div>

			
							
</div>
										
						
				
	隐藏框 工序分配开始 
	<div id="addDictDivType" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeForm">
				<div class="row col-xs-12  col-sm-12  col-md-12 ">


					<div class="form-group">
						<label class="col-sm-3 col-md-2 control-label">任务数量:</label>
						<div class="col-sm-3 col-md-3">
							<input type="text" class="form-control sumnumber">
						</div>
						<div>
							<label class="col-sm-2 col-md-2 control-label">预计完成时间:</label>
							<div class="col-sm-3 col-md-3">
								<input type="text" placeholder="非返工任务不填写"
									class="form-control sumtime">
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">实际任务时间:</label>
						<div class="col-sm-3">
							<input type="text" class="form-control actualtime">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">任务分配:</label>
						<div class="col-sm-3">
							<input id="Time" placeholder="时间可不填"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#Time', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
						<label class="col-sm-2 control-label">加绩工序:</label>
						<div class="col-sm-3 workingtw"></div>
					</div>


					<div class="form-group">
						<label class="col-sm-2 control-label">选择工序:</label>
						<div class="col-sm-2 working"></div>
						<div class="col-sm-2 checkworking"></div>
						<label class="col-sm-1 control-label">完成人:</label>
						<div class="col-sm-2 complete">
							<input type="text" class="form-control">
						</div>
						<div class="col-sm-2 select"></div>
					</div>
				</div>

			</form>
		</div>
	</div>
	隐藏框 工序分配结束 





	隐藏框 工序分配2开始 
	<div id="addDictDivTypetw" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeFormtw">
				<div class="row col-xs-12  col-sm-12  col-md-12 " id="tabs">


					<div class="form-group">
						<label class="col-sm-2 control-label">加绩工序:</label>
						<div class="col-sm-3 workingth"></div>
						<label class="col-sm-2 control-label">选择工序:</label>
						<div class="col-sm-3 workingtw"></div>
						<div class="col-sm-2 checkworkingtw"></div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">任务时间:</label>
						<div class="col-sm-3">
							<input id="Timet" placeholder="时间可不填"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#Timet', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
						<label class="col-sm-2 control-label">预计时间:</label>
						<div class="col-sm-3">
							<input type="text" placeholder="非返工任务不填写"
								class="form-control sumtimetw">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">实际任务时间:</label>
						<div class="col-sm-3">
							<input type="text" class="form-control actualtimetw">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 col-md-2 control-label">开始时间:</label>
						<div class="col-sm-2 col-md-2">
							<input id="Timetstr" class="form-control laydate-icon"
								onClick="laydate({elem: '#Timetstr', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
						<div>
							<label class="col-sm-1 col-md-1 control-label">结束时间:</label>
							<div class="col-sm-2 col-md-2">
								<input id="Timetend" class="form-control laydate-icon"
									onClick="laydate({elem: '#Timetend', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
							</div>
						</div>
						<label class="col-sm-1 control-label">完成人:</label>
						<div class="col-sm-2 completetw">
							<input type="text" class="form-control">
						</div>
						<div class="col-sm-1 selecttw"></div>
						<div class="col-sm-2 col-md-1">
							<input type="button" class="btn btn-sm  btn-success" id="save"
								value="新增"></input>
						</div>
					</div>

				</div>
			</form>
		</div>

	</div>
	隐藏框 工序分配2结束 
	任务详情开始
	<div id="addworking"
		style="display: none; position: absolute; z-index: 3;">
		<table>
			<tr>
				<td><button type="button"
						class="btn btn-default btn-danger btn-xs btn-3d attendance">一键删除</button>&nbsp&nbsp</td>
			</tr>
		</table>
		<div class="panel-body">
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="center"><label> <input type="checkbox"
								class="ace checks" /> <span class="lbl"></span>
						</label></th>
						<th class="text-center">批次号</th>
						<th class="text-center">产品名</th>
						<th class="text-center">时间</th>
						<th class="text-center">工序</th>
						<th class="text-center">预计时间</th>
						<th class="text-center">任务价值</th>
						<th class="text-center">b工资净值</th>
						<th class="text-center">数量</th>
						<th class="text-center">工序加价</th>
						<th class="text-center">加绩工资</th>
						<th class="text-center">完成人</th>
						<th class="text-center">操作</th>
					</tr>
				</thead>
				<tbody id="tablecontentto">

				</tbody>

			</table>
			<div id="pagerr" class="pull-right"></div>
		</div>

	</div>
	任务详情结束

隐藏框 人员信息开始 
	<div id="userInformation" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeForm">
				<div class="form-group">
					<div   id="modal-body" style="text-align:center">
						
					</div>
				</div>
			</form>
		</div>
	</div> -->
	<!--隐藏框 人员信息结束  -->

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
				<label class="layui-form-label" style="width: 100px;">备注</label>
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
			var htmls = '<option value="0">请选择</option>';
			var htmlsh = '<option value="0">请选择</option>';
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
	      			  $("#working").html(htmls);
			      }
			  });
			
			var html="<option value='0'>请选择</option>"
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
					flag:0
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
						edit: false,
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
						edit: false,
					},{
						field: "status",
						title: "完成状态",
						align: 'center',
						edit: false,
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
				 var productName=data.product.name
				switch(obj.event) {
				case 'queryOut':
					productId=data.product.id;
					bacthId=data.id;
					productId2=data.id
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
								console.log(data)
								data.field.type=6;
								data.field.flag=0;
								data.field.bacthDepartmentPrice=(data.field.bacthDepartmentPrice==null ? 0 : data.field.bacthDepartmentPrice)
								data.field.bacthHairPrice=(data.field.bacthHairPrice==null ? 0 : data.field.bacthHairPrice)
								data.field.productId=productId2
								/*  mainJs.fAdd2(data.field);  
								document.getElementById("layuiadmin-form-admin2").reset(); */
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
	
	
  /*  jQuery(function($){
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
		  	this.getCount = function(){
		  		return _count;
		  	}
		  	this.setCount = function(count){
		  		_count=count;
		  	}
		  	
		  	function p(s) {
				return s < 10 ? '0' + s: s;
				}
		  	var myDate = new Date(new Date().getTime() - 86400000);
			//获取当前年
			var year=myDate.getFullYear();
			//获取当前月
			var month=myDate.getMonth()+1;
			//获取当前日
			var date=myDate.getDate(); 
			var day = new Date(year,month,0);  
			var firstdate = year + '-' + '0'+month + '-01'+' '+'00:00:00';
			var lastdate = year + '-' + '0'+month + '-' + day.getDate() +' '+'23:59:59';
			$('#startTime').val(firstdate);
			$('#endTime').val(lastdate);
			 var data={
						page:1,
				  		size:13,	
				  		type:1,
				  		flag:0,
				  		status:$('#selectstate').val(),
				  		orderTimeBegin:$("#startTime").val(),
				  		orderTimeEnd:$("#endTime").val(),
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
				      url:"${ctx}/bacth/allBacth",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			  //修改合计
			      			$("#total").text(result.data.statData.number)
			      			$("#tota2").text(result.data.statData.sumTaskPrice)
			      			$("#tota3").text(result.data.statData.time)
			      			//修改此处
		      			  
		      			  
		      			  
		      			 $("#total").text(result.data.statData.stateCount)
		      			  $("#totaltw").text(result.data.statData.statAmount)
		      			 $(result.data.rows).each(function(i,o){
		      				var strname="";
		      				 if(o.status==1){
		      					strname="完成";
		      				 }else{
		      					strname="未完成";
		      				 }
		      				 html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="hidden batch">'+o.id+'</td>'
		      				+'<td class="text-center  bacthNumber">'+o.bacthNumber+'</td>'
		      				+'<td class="text-center edit allotTime">'+o.allotTime+'</td>'
		      				+'<td class="text-center  names" data-id='+o.id+'>'+o.product.name+'</td>'
		      				+'<td class="text-center edit number">'+o.number+'</td>'
		      				+'<td class="text-center  bacthDepartmentPrice">'+parseFloat((o.bacthDepartmentPrice).toFixed(3))+'</td>'
		      				+'<td class="text-center  bacthHairPrice">'+o.bacthHairPrice+'</td>'
		      				+'<td class="text-center  sumTaskPrice">'+ parseFloat((o.sumTaskPrice*1).toFixed(3))+'</td>'
		      				+'<td class="text-center  regionalPrice">'+parseFloat((o.regionalPrice*1).toFixed(3))+'</td>'
		      				+'<td class="text-center ">'+parseFloat((o.time).toFixed(3))+'</td>'
		      				+'<td class="text-center edit remarks">'+o.remarks+'</td>'
		      				+'<td class="text-center ">'+strname+'</td>'
							+'<td class="text-center"><button class="btn btn-sm btn-primary btn-trans addDict" data-id='+o.id+' data-proid='+o.product.id+' data-bacthnumber='+o.bacthNumber+' data-proname='+o.product.name+'>分配</button>'
							+'<button class="btn btn-sm btn-primary btn-trans addDicttw" data-id='+o.id+' data-proid='+o.product.id+' data-bacthnumber='+o.bacthNumber+' data-proname='+o.product.name+' data-number='+o.number+'>分配2</button>'
							+'<button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button>'
							+'<button class="btn btn-sm btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>' 
							
		      			}); 
		      			self.setCount(result.data.pageNum)
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
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			flag:0,
									  		status:$('#selectstate').val(),
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   	self.loadEvents();
					   	self.checkedd();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
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
			  this.loadPaginationto = function(data){
				    var index;
				    var html = '';
				    var htmlto="";
				    $.ajax({
					      url:"${ctx}/task/allTask",
					      data:data,
					      type:"GET",
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			 $(result.data.rows).each(function(i,o){
			      				 var a=""
			      				 var s=o.procedureName
			      				if(o.flag==1){
			      					a="(返工)"
			      				}
			      				 if(o.taskActualTime==null){
			      					o.taskActualTime=0
			      				 }
			      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxIdto" value="'+o.id+'"/><span class="lbl"></span></label></td>'
			      				+'<td class="text-center ">'+o.bacthNumber+'</td>'
			      				+'<td class="text-center ">'+o.productName+'</td>'
			      				+'<td class="text-center edit allotTimetw">'+o.allotTime+'</td>'
			      				+'<td class="text-center ">'+s+a+'</td>'
			      				+'<td class="text-center ">'+parseFloat((o.expectTime).toFixed(4))+'</td>'
			      				+'<td class="text-center ">'+parseFloat((o.taskPrice).toFixed(4))+'</td>'
			      				+'<td class="text-center ">'+parseFloat((o.payB).toFixed(4))+'</td>'
			      				+'<td class="text-center edit number">'+o.number+'</td>'
			      				+'<td class="text-center ">'+o.performance+'</td>'
			      				+'<td class="text-center ">'+parseFloat((o.performancePrice).toFixed(4))+'</td>'
			      				+'<td class="text-center"><button class="btn btn-primary btn-trans btn-sm savemode" data-toggle="modal" data-target="#myModal" data-id="'+o.id+'")">查看人员</button></td>'
								+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans updateremaketw" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans deletetw" data-id='+o.id+'>删除</button></td></tr>'
								
			      			}); 
					        //显示分页
						   	 laypage({
						      cont: 'pagerr', 
						      pages: result.data.totalPages, 
						      curr:  result.data.pageNum || 1, 
						      jump: function(obj, first){ 
						    	  if(!first){ 
						    		 
							        	var _data = {
							        			page:obj.curr,
										  		size:13,
										  		type:1,
										  		bacthId:self.getCache(),
									  	}
							        
							            self.loadPaginationto(_data);
								     }
						      }
						    });  
						   	layer.close(index);
						   
						   	 $("#tablecontentto").html(html); 
						   	 self.loadEventss();
						   	
					      },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);
						  }
					  });
				}
			  this.loadEventss = function(){
					
					
					$('.rest').on('click',function(){
						var  del=$(this);
						var id = $(this).parent().data('id');
						var rest = $(this).val();
						
					    	  $.ajax({
									url:"${ctx}/task/getTaskActualTime",
									data:{
										ids:id,
										status:rest,
										},
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										//选择1
										
										if(rest==0){
									
											del.parent().find(".rest").eq(1).prop("checked", false);

										}else{
											del.parent().find(".rest").eq(0).prop("checked", false);
											
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
					
					
					//修改方法
					$('.updateremaketw').on('click',function(){
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
										id:$(this).data('id'),
										number:$(this).parent().parent('tr').find(".number").text(),
										allotTime:$(this).parent().parent('tr').find(".allotTimetw").text(),
								}
								var index;
								$.ajax({
									url:"${ctx}/task/upTask",
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
										var _data = {
							        			page:1,
										  		size:13,
										  		type:1,
										  		bacthId:self.getCache(),
									  	}
							        
							            self.loadPaginationto(_data);
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
					
					
					//删除
							$('.deletetw').on('click',function(){
								var postData = {
										ids:$(this).data('id'),
								}
								
								var index;
								 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
								$.ajax({
									url:"${ctx}/task/delete",
									data:postData,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
										layer.msg("删除成功！", {icon: 1});
										var _data={
												page:1,
										  		size:13,
												bacthId:self.getCache(),
												type:1,
										}
										self.loadPaginationto(_data)
										layer.close(index);
										}else{
											layer.msg("删除失败！", {icon: 1});
											layer.close(index);
										}
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
								 })
					})
					
					//人员详细显示方法
					$('.savemode').on('click',function(){
						var id=$(this).data('id')
						var arr=new Array();
						var html="";
						var dicDiv=$('#userInformation');
						 var postData={
									id:id,
							}
						  $.ajax({
								url:"${ctx}/task/taskUser",
								data:postData,
								type:"GET",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									$(result.data).each(function(i,o){
									html+=o.userName+"&nbsp;&nbsp;&nbsp;&nbsp;"
									})
									$('#modal-body').html(html);
									layer.close(index);
									
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
						_index = layer.open({
							  type: 1,
							  skin: 'layui-layer-rim', //加上边框
							  area: ['40%', '200px'], 
							  btnAlign: 'c',//宽高
							  maxmin: true,
							  title:"人员信息",
							  content: dicDiv,
							  btn: ['关闭'],
							  end:function(){
								  $('#addDictDivType').hide();
							
								  $('.addDictDivTypeForm')[0].reset(); 
								
							  }
						});
						
					})
					
					
				} 
			this.loadEvents = function(){
				$('.names').on('click',function(){
					var that=$(this);
					var a=$(this).data('id');
					self.setCache(a);
					var data={
							bacthId:$(this).data('id'),
							page:1,
					  		size:13,	
					  		type:1,
					} 
					self.loadPaginationto(data);
					 var ids=that.data("id");
						$(".batch").each(function(i,o){
							var a=$(o).text();
							if(a==ids){
								$(o).parent().addClass("danger");
								$(o).parent().siblings().removeClass("danger");
							}
						})
				var dicDiv=$('#addworking');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['80%', '100%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:this.innerHTML,
						  content: dicDiv,
						  
						  yes:function(index, layero){
							 
							},
						  end:function(){
							  $('#addworking').hide();
							  data={
									page:1,
								  	size:13,	
								  	type:1,
								  	name:$('#name').val(),
						  			number:$('#number').val(),
							  }
							
						  }
					});
				})
				//删除
				$('.delete').on('click',function(){
					var postData = {
							id:$(this).data('id'),
					}
					
					var index;
					 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
					$.ajax({
						url:"${ctx}/bacth/deleteBacth",
						data:postData,
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							if(0==result.code){
							layer.msg("删除成功！", {icon: 1});
							var data={
									page:1,
								  	size:13,	
								  	type:1,
									name:$('#name').val(),
						  			bacthNumber:$('#number').val(),
						  			orderTimeBegin:$("#startTime").val(),
						  			orderTimeEnd:$("#endTime").val(),
						  			flag:0,
							  		status:$('#selectstate').val(),
							}
							self.loadPagination(data)
							layer.close(index);
							}else{
								layer.msg("删除失败！", {icon: 1});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					 })
				})
				//修改方法
				$('.updateremake').on('click',function(){
					if($(this).text() == "编辑"){
						$(this).text("保存")
						$(this).parent().siblings(".edit").each(function(index) {  // 获取当前行的其他单元格
							//修改编辑单元弹出，时间选择板。代码如下：
							if(index==0){	
								$(this).html('<input type="text" id="editTime" class="input-mini form-control laydate-icon" value="'+$(this).text()+'"/>');
								document.getElementById('editTime').onclick=function(){
									laydate({
									    elem: '#editTime',
									    istime: true, format: "YYYY-MM-DD hh:mm:ss"
									  });
								}
							}else
				       			$(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
							//原代码：
							//$(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
				        });
					}else{
						$(this).text("编辑")
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格
			            	obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框
			                $(this).html(obj_text.val()); 
						});
						var postData = {
								id:$(this).data('id'),
								number:$(this).parent().parent('tr').find(".number").text(),
								remarks:$(this).parent().parent('tr').find(".remarks").text(),
								allotTime:$(this).parent().parent('tr').find(".allotTime").text(),
						}
						var index;
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
								layer.msg("修改成功！", {icon: 1});
								var data={
										page:self.getCount(),
								  		size:13,	
								  		type:1,
								  		flag:0,
								  		status:$('#selectstate').val(),
								} 
							   self.loadPagination(data);
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
				
				//分配1
				$('.addDict').on('click',function(){
					var that=$(this)
					var productId=$(this).data('proid')
					var productName=$(this).data('proname')
					var bacthId=$(this).data('id')
					var bacthNumber=$(this).data('bacthnumber')
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    
				    var htmlth = '';
				    var htmlfr = '';
				 	
				    //遍历工序类型
				    var getdata={type:"productFristQuality",}
	      			$.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdata,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfr +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			$('.working').html("<select class='form-control selectchang'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlfr+"</select>")
							//改变事件
			      			$(".selectchang").change(function(){
			      				var htmlfv="";
			      				var	id=$(this).val()
			      				if(id==109 || id==""){
			      					$('#dis').css("display","block")
			      				}else{
			      					$('#dis').css("display","none")
			      				}
								   var data={
										   productId:productId,
										   type:1,
										   bacthId:bacthId,
										   procedureTypeId:id,
								   }
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
												htmlfv +='<div class="input-group"><input type="checkbox" class="checkWork" value="'+o.id+'" data-name="'+o.name+'" data-residualnumber="'+o.residualNumber+'">'+o.name+' 剩余:'+o.residualNumber+'</input></div>'
											})
											var s="<div class='input-group'><input type='checkbox' class='checkWorkAll'>全选</input></div>"
											$('.checkworking').html(s+htmlfv);
											$('.checkWork').each(function(i,o){
												if($(o).data('name')=="贴破洞"){
													$(this).before('<input class="inputblock"type="text" ></input>');
												}
											})
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
					      }
					  });
					var data={
							type:1
					}
					//遍历人名组别
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			 $('.complete').html("<select class='form-control selectcomplete'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlth+"</select>") 
							//改变事件
			      			 $(".selectcomplete").change(function(){
			      				var htmltwo = "";
			      				var	id=$(this).val()
								   var data={
										  id:id,
										  type:1,
										  temporarilyDate:$('#Time').val(),
								   }
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
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBox" value="'+o.id+'" data-username="'+o.userName+'">'+o.userName+'</input></div>'
										})
										})
										var s="<div class='input-group'><input type='checkbox' class='checkall'>全选</input></div>"
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
					      }
					  });
				    
					//遍历杂工加绩比值
					var html=""
					$.ajax({
						url:"${ctx}/task/taskPerformance",
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+='<option value="'+o.number+'" data-name="'+o.name+'">'+o.name+'</option>'
							})
							$('.workingtw').html("<select class='form-control selectchangtw'><option value='0'></option>"+html+"</select>");
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
				    
					var postData
					var dicDiv=$('#addDictDivType');
					//var scrollHeight=;
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['80%', '400px'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:productName,
						  content: dicDiv,
						  offset:(parent.document.documentElement.scrollTop+50)+'px',
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var values=new Array()
							  var numberr=new Array()
								$(".checkWork:checked").each(function() {   
									values.push($(this).val());
									numberr.push($(this).data('residualnumber'));
								}); 
							  var arr=new Array()
							  
								$(".stuCheckBox:checked").each(function() {   
								    arr.push($(this).val());   
								}); 
							  var username=new Array()
							  $(".stuCheckBox:checked").each(function() {   
								  username.push($(this).data('username'));   
								});
							  if(values.length<=0){
									return layer.msg("至少选择一个工序！", {icon: 2});
								}
								if(arr.length<=0){
									return layer.msg("至少选择一个员工！", {icon: 2});
								}
								number=$(".sumnumber").val();
								for (var i = 0; i < numberr.length; i++) {
									if(numberr[i]-number<0){
										return layer.msg("有工序剩余数量不足！", {icon: 2});
									}
								}
								expectTime=$(".sumtime").val();
								var performanceNumber=$(".selectchangtw").val();
								var holeNumber=$(".inputblock").val();
								var performance=$(".selectchangtw option:selected").text();
								if(performance=="请选择请选择"){
									performance="";
								}
								var postData = {
										type:1,
										bacthId:that.data("id"),
										procedureIds:values,
										userIds:arr,
										number:number,
										userNames:username,
										performance:performance,
										performanceNumber:performanceNumber,
										productName:productName,
										expectTime:expectTime,
										bacthNumber:bacthNumber,
										allotTime:$('#Time').val(),
										productId:productId,
										holeNumber:holeNumber,
										remark:$('.actualtime').val(),
								}
							    $.ajax({
									url:"${ctx}/task/addTask",
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
										  $('.addDictDivTypeForm')[0].reset(); 
										$('.checkworking').text("");
										  $('.select').text("");
											layer.msg("添加成功！", {icon: 1});
											layer.close(_index);
											
										}else{
											layer.msg("添加失败", {icon: 2});
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
							  $("#addDictDivType").hide();
							  $('.checkworking').text(""); 
						  } 
					});
					
					
				})
				
				
				
				
				
				
				
				
				
				
				//分配2
				$('.addDicttw').on('click',function(){
					var that=$(this)
					var productId=$(this).data('proid')
					var productName=$(this).data('proname')
					var bacthId=$(this).data('id')
					var bacthNumber=$(this).data('bacthnumber')
					var number=$(this).data('number')
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    
				    var htmlth = '';
				    var htmlfr = '';
				 	
				    //遍历工序类型
				    var getdata={type:"productFristQuality",}
	      			$.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdata,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfr +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			$('.workingtw').html("<select class='form-control selectchangtt'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlfr+"</select>")
							//改变事件
			      			$(".selectchangtt").change(function(){
			      				var htmlfv="";
			      				var	id=$(this).val()
			      				if(id==109 || id==""){
			      					$('#diss').css("display","block")
			      				}else{
			      					$('#diss').css("display","none")
			      				}
								   var data={
										   productId:productId,
										   type:1,
										   bacthId:bacthId,
										   procedureTypeId:id,
								   }
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
												htmlfv +='<div class="input-group"><input type="checkbox" class="checkWorks" value="'+o.id+'" data-residualnumber="'+o.residualNumber+'">'+o.name+' 剩余:'+o.residualNumber+'</input></div>'
											})
											var s="<div class='input-group'><input type='checkbox' class='checkWorkAlls'>全选</input></div>"
											$('.checkworkingtw').html(s+htmlfv);
											$(".checkWorkAlls").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.checkWorks').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.checkWorks').each(function(){ 
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
					      }
					  });
					var data={
							type:1
					}
					//遍历人名组别
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			 $('.completetw').html("<select class='form-control selectcompletet'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlth+"</select>") 
							//改变事件
			      			 $(".selectcompletet").change(function(){
			      				var htmltwo = "";
			      				var	id=$(this).val()
								   var data={
										  id:id,
										  type:1,
										  temporarilyDate:$('#Time').val(),
								   }
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
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBoxt" value="'+o.id+'" data-username="'+o.userName+'">'+o.userName+'</input></div>'
										})
										})
										var s="<div class='input-group'><input type='checkbox' class='checkalls'>全选</input></div>"
										$('.selecttw').html(s+htmltwo)
										$(".checkalls").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.stuCheckBoxt').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.stuCheckBoxt').each(function(){ 
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
					      }
					  });
				    
					//遍历杂工加绩比值
					var html=""
					$.ajax({
						url:"${ctx}/task/taskPerformance",
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+='<option value="'+o.number+'" data-name="'+o.name+'">'+o.name+'</option>'
							})
							$('.workingth').html("<select class='form-control selectchangtwt'><option value='0'>请选择</option>"+html+"</select>");
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					var time;
					var timeover;
					var ss;
					var times=new Array();
					var values=new Array();
					var roleidArray = new Array();
					var str1;
					var i = -1;
					$('#save').on('click',function(){
					var trHtml="";
						i++;
						time=$("#Timetstr").val();
						timeover=$("#Timetend").val();
						var dt1 = new Date(Date.parse(time));
						var dt2 = new Date(Date.parse(timeover));
						ss=(dt2-dt1)/60000
						var arr=new Array()
						var username=new Array()
						if(dt1=="Invalid Date"){
							return layer.msg("开始时间不能为空！", {icon: 2});
						}
						if(dt2=="Invalid Date"){
							return layer.msg("结束时间不能为空！", {icon: 2});
						}
						if(dt2-dt1<=0){
							return layer.msg("结束时间不能小于开始时间！", {icon: 2});
						}
						$(".stuCheckBoxt:checked").each(function() {   
						    arr.push($(this).val()); 
						    username.push($(this).data('username'))
						}); 
						if(arr.length==0){
							 return layer.msg("领取人不能为空", {icon: 2});
						}
						
						  times.push(ss);
						  roleidArray.push(arr)
						  str1=roleidArray.join(".")
						 
						 trHtml ='<div class="form-group" data-id="'+i+'">'
	                           +'<label class="col-sm-3 col-md-2 control-label">开始时间:</label>'
                        	   +'<div class="col-sm-2 col-md-2">'
                               +'<input  class="form-control laydate-icon" value="'+time+'" onClick="laydate({elem: "#Timetstr", istime: true, format: "YYYY-MM-DD hh:mm:ss"})">'
                               +'</div>'
                               +'<div>'
                               +'<label class="col-sm-1 col-md-1 control-label" >结束时间:</label>'
                               +'<div class="col-sm-2 col-md-2">'
                               +'<input value="'+timeover+'"  class="form-control laydate-icon" onClick="laydate({elem: "#Timetend", istime: true, format: "YYYY-MM-DD hh:mm:ss"})">'
                               +'</div>'
                               +'</div>'
                               +'<label class="col-sm-1 control-label">完成人:</label>'
                               +'<div class="col-sm-2 completetw">'
                               +'<input type="text" value="'+username+'" class="form-control">'
                               +'</div>'
                               +'<div class="col-sm-1 "></div>'
                               +'<div class="col-sm-2 col-md-1"><input type="button" class="btn btn-sm btn-success del" id="'+i+'" value="删除"></input></div></div>'
                              
                               $("#tabs").append(trHtml); 
                            	 
	                       $('.del').on('click',function(){
                            	   var va=$(this).parent().parent().data('id');
                            	   times.splice(va,1,"delete");
                            		roleidArray.splice(va,1,["delete"]);
                            	   str1=roleidArray.join(".");
                            	   $(this).parent().parent().hide();
                            	   return layer.msg("删除成功", {icon: 1});
                               })
						return layer.msg("添加成功", {icon: 1});
					})
					var postData
					var dicDiv=$('#addDictDivTypetw');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['80%', '400px'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:productName,
						  offset:(parent.document.documentElement.scrollTop+50)+'px',
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var values=new Array()
							  var numberr=new Array()
							
								$(".checkWorks:checked").each(function() {   
									values.push($(this).val());
									numberr.push($(this).data('residualnumber'));
								}); 
							  var arr=new Array()
							  
								$(".stuCheckBoxt:checked").each(function() {   
								    arr.push($(this).val());   
								}); 
							  var username=new Array()
							  $(".stuCheckBoxt:checked").each(function() {   
								  username.push($(this).data('username'));   
								});
							  if(values.length<=0){
									return layer.msg("至少选择一个工序！", {icon: 2});
								}
								if(arr.length<=0){
									return layer.msg("至少选择一个员工！", {icon: 2});
								}
								 for (var i = 0; i < numberr.length; i++) {
									if(numberr[i]-number<0){
										return layer.msg("有工序剩余数量不足！", {icon: 2});
									}
								} 
								expectTime=$(".sumtimetw").val();
								var performanceNumber=$(".selectchangtwt").val();
								
								var performance=$(".selectchangtwt option:selected").text();
								if(performance=="请选择"){
									performance="";
								}
								var postData = {
										type:1,
										times:times,
										users:str1,
										bacthId:that.data("id"),
										procedureIds:values,
										number:number,
										performance:performance,
										performanceNumber:performanceNumber,
										productName:productName,
										expectTime:expectTime,
										bacthNumber:bacthNumber,
										allotTime:$('#Timet').val(),
										remark:$('.actualtimetw').val(),
								}
							    $.ajax({
									url:"${ctx}/task/addTaskTwo",
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
										  $('.addDictDivTypeFormtw')[0].reset(); 
										$('.checkworkingtw').text("");
										  $('.selecttw').text("");
											layer.msg("添加成功！", {icon: 1});
											layer.close(_index);
											
										}else{
											layer.msg("添加失败", {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});  
							},
						   end:function(){
							  $('.addDictDivTypeFormtw')[0].reset(); 
							  $("#addDictDivTypetw").hide();
							  $('.checkworkingtw').text("");
							  $('.selecttw').text("");
							  var data={
										page:self.getCount(),
										size:13,
							  			type:1,
							  			name:$('#name').val(),
							  			bacthNumber:$('#number').val(),
							  			 orderTimeBegin:$("#startTime").val(),
							  			orderTimeEnd:$("#endTime").val(), 
							  			status:$("#selectstate").val(),
							  			flag:0,
								} 
							   self.loadPagination(data);
						  } 
					});
					
					
				})
				
				
				
				
				
				
				
				
				
			}
			
			
			this.events = function(){
				  /* 一键删除 
				$('.attendance').on('click',function(){
					  var  that=$(this);
					  var arr=new Array()//员工id
						$(this).parent().parent().parent().parent().parent().find(".checkboxIdto:checked").each(function() {  
							arr.push($(this).val());   
						});
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var data={
								type:5,
								ids:arr,
						}
						var _data={
								page:1,
						  		size:13,
								bacthId:self.getCache(),
								type:1,
						}
						var index;
						 index = layer.confirm('确定一键删除吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/task/delete",
							data:data,
				            traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
									layer.msg(result.message, {icon: 1});
									self.loadPaginationto(_data);
								}else{
									layer.msg(result.message, {icon: 2});
								}
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 });
				  })
				/* 一键完成
				$('.start').on('click',function(){
					  var  that=$(".table-hover");
					  var arr=new Array()//员工id
					  	that.parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var _datae={
								status:0,
								type:1,
								ids:arr,
						}
						var index;
						index = layer.confirm('<input type="text" id="some" class="tele form-control " placeholder="请输入时间" onClick=laydate({elem:"#some",istime:true,format:"YYYY-MM-DD"})>', {btn: ['确定', '取消'],offset:'(parent.document.documentElement.scrollTop+50)'+'px',},function(){
							var a="";
							if($('#some').val()==""){
								a="";
							}else{
								a=$('#some').val()+" "+"00:00:00"
							}
							var data={
								status:1,
								type:1,
								ids:arr,
								time:a,
						}
						$.ajax({
							url:"${ctx}/bacth/statusBacth",
							data:data,
				            traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
									layer.msg(result.message, {icon: 1});
									var _datae = {
								  			page:1,
								  			size:13,
								  			type:1,
								  			name:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			 orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(), 
								  			status:$("#selectstate").val(),
								  			flag:0,
								  	}
									self.loadPagination(_datae);
								}else{
									layer.msg(result.message, {icon: 2});
								}
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 });
				  })
				//查询
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:1,
				  			name:$('#name').val(),
				  			bacthNumber:$('#number').val(),
				  			 orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  			status:$("#selectstate").val(),
				  			flag:0,
				  	}
		            self.loadPagination(data);
				});
				
				
				
			}
   	}
   			var login = new Login();
				login.init();
			}) */
    
    </script>

</body>

</html>