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
	<title>招聘汇总</title>
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head> 

<body>
	<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>查询月份:</td>
							<td><input id="monthDate" style="width: 180px;" name="time" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-search">
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

<div style="display: none;" id="layuitable">
			<table id="layuitable2"  class="table_th_search" lay-filter="layuitable2"></table>
</div>

<div style="display: none;" id="layuiShow">
			<table id="layuiShow2"  class="table_th_search" lay-filter="layuiShow2"></table>
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

<div style="display: none;" id="sumday">
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
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-searchsumday">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="analysisRecuitsumday"  class="table_th_search" lay-filter="layuiShare10"></table>
</div>
<!-- 查看汇总弹窗 -->
<div style="display:none;padding:4px;" id="lookoverDiv">
	<table class="layui-form">
		<tr>
			<td><input type="text" name="time" id="totalTime" lay-verify="required" class="layui-input"></td>
			<td> &nbsp;&nbsp;</td>
			<td><button type="button" lay-filter="searchTotal"  lay-submit class="layui-btn layui-btn-sm">搜索</button> </td>
		</tr>
	</table>
	<table class="layui-table" id="totalTable" lay-filter="totalTable"></table>
</div>
<!-- 查看部门汇总弹窗 -->
<div style="display:none;padding:4px;" id="departmentDiv">
	<table class="layui-form">
		<tr>
			<td><input type="text" name="time" id="totalDepartmentTime" lay-verify="required" class="layui-input"></td>
			<td> &nbsp;&nbsp;</td>
			<td><button type="button" lay-filter="searchDepartmentTotal"  lay-submit class="layui-btn layui-btn-sm">搜索</button> </td>
		</tr>
	</table>
	<table class="layui-table" id="totalDepartmentTable" lay-filter="totalDepartmentTable"></table>
</div>
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="short">短期离职</span>
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="quit">离职人员</span>
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="analysis">招聘数据汇总分析</span>
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="sumday">每日分析</span>
				<span class="layui-btn layui-btn-sm" lay-event="lookoverTotal">招聘费用汇总</span>
				<span class="layui-btn layui-btn-sm" lay-event="departmentTotal">部门招聘成本</span>
			</div>
		</script>

<script type="text/html" id="switchTpl2">
  <input type="checkbox" name="type" value="{{d.id}}" data-id="{{d.state}}" lay-skin="switch" lay-text="是|否" lay-filter="type" {{ d.type == 1 ? 'checked' : '' }}>
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
					var htmltt = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					layer.close(index);
					
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
					var firstdate3=year + '-' + p(month);
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
						value : firstdate+' ~ '+lastdate,
					});
					form.render();
					laydate.render({
						elem: '#monthDate',
						type : 'month',
						value:firstdate3,
					}); 
					laydate.render({
				 		elem: '#totalTime',
				 		type: 'month',
				 	})
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
					laydate.render({
						elem: '#monthDate9',
						type: 'datetime',
						range: '~',
						value : firstdate2+' ~ '+lastdate2,
					}); 
					var data="";
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						url: '${ctx}/personnel/Statistics' ,
						where:{
							time : firstdate,
						},
						loading: true,
						toolbar: '#toolbar', 
						totalRow: true,
						sort:true,
						colFilterRecord: true,
						smartReloadModel: true,
						parseData: function(ret) { 
							return { code: ret.code, msg: ret.message, count:ret.data.total, data: ret.data } 
						},
						cols: [
							[{
								field: "username",
								title: "部门",
								align: 'center',
								totalRowText: '合计'
							},{
								field: "mod1",
								title: "邀约面试",
								align: 'center',
								totalRow: true,
								event:'mod10'
							},{
								field: "mod2",
								title: "应邀面试",
								align: 'center',
								totalRow: true,
								event:'mod11'
							},{
								field: "mod3",
								title: "面试合格",
								align: 'center',
								totalRow: true,
								event:'mod12'
							},{
								field: "mod4",
								title: "拒绝入职",
								align: 'center',
								totalRow: true,
								event:'mod13'
							},{
								field: "mod5",
								title: "已入职且在职",
								align: 'center',
								totalRow: true,
								event:'mod14'
							},{
								field: "mod6",
								title: "即将入职",
								align: 'center',
								totalRow: true,
								event:'mod15'
							},{
								field: "mod7",
								title: "短期入职离职",
								align: 'center',
								totalRow: true,
								event:'mod16'
							}
							]
						],
						done:function(){
							$(".layui-table-total").unbind().on('click',function(obj){
								var className=obj.toElement.className
								var name=className[className.length-1]
								  var dicDiv=$('#layuiShow');
									layer.open({
								         type: 1
								        ,title: '招聘人员情况汇总' //不显示标题栏
								        ,closeBtn: false
								        ,zindex:-1
								        ,area:['70%', '90%']
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
				      			table.render({
									elem: '#layuiShow2',
									url: '${ctx}/personnel/Statistics' ,
									where:{
										time : $("#monthDate").val()+'-01'+' '+'00:00:00',
									},
									loading: true,
									toolbar: '#toolbar2', 
									sort:true,
									colFilterRecord: true,
									smartReloadModel: true,
									parseData: function(ret) { 
										var newArr = [];
										layui.each(ret.data,function(index,item){
											if(name=="1"){
												layui.each(item.mod10,function(j,k){
													newArr.push(k);
													k.position=k.position.name;
													k.platform=k.platform.name;
													k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
													k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
													k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
												})
											}
											if(name=="2"){
												layui.each(item.mod11,function(j,k){
													newArr.push(k);
													k.position=k.position.name;
													k.platform=k.platform.name;
													k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
													k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
													k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
												})
											}
											if(name=="3"){
												layui.each(item.mod12,function(j,k){
													newArr.push(k);
													k.position=k.position.name;
													k.platform=k.platform.name;
													k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
													k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
													k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
												})
											}
											if(name=="4"){
												layui.each(item.mod13,function(j,k){
													newArr.push(k);
													k.position=k.position.name;
													k.platform=k.platform.name;
													k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
													k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
													k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
												})
											}
											if(name=="5"){
												layui.each(item.mod14,function(j,k){
													newArr.push(k);
													k.position=k.position.name;
													k.platform=k.platform.name;
													k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
													k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
													k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
												})
											}
											if(name=="6"){
												layui.each(item.mod15,function(j,k){
													newArr.push(k);
													k.position=k.position.name;
													k.platform=k.platform.name;
													k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
													k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
													k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
												})
											}
											if(name=="7"){
												layui.each(item.mod16,function(j,k){
													newArr.push(k);
													k.position=k.position.name;
													k.platform=k.platform.name;
													k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
													k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
													k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
												})
											}
										})
										return { code: ret.code, msg: ret.message, data:newArr } 
									},
									cols: [
										[{
											field: "name",
											title: "人名",
											align: 'center',
										},{
											field: "position",
											title: "职位",
											align: 'center',
										},{
											field: "time",
											title: "面试时间",
											align: 'center',
											width:160
										},{
											field: "platform",
											title: "平台",
											align: 'center',
										},{
											field: "recruitName",
											title: "招聘人",
											align: 'center',
										},{
											field: "entry",
											title: "入职时间",
											align: 'center',
											width:160
										},{
											field: "quitDate",
											title: "离职时间",
											align: 'center',
											width:160
										},{
											field: "reason",
											title: "离职原因",
											align: 'center',
										},{
											field: "remarksOne",
											title: "备注",
											align: 'center',
										}
										]
									],
											});
							})
						}
								});
					
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
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
								
							case 'sumday':
								var dicDiv=$('#sumday');
								table.reload("analysisRecuitsumday");
								layer.open({
							         type: 1
							        ,title: '每日分析' //不显示标题栏
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
							        }
							        ,end:function(){
							        	$("#sumday").hide();
									  } 
							      });
								break;	
								
							case 'update' :
								 addEidt('edit')
								break;
							case 'lookoverTotal': 
								lookoverTotal();
							break;
							case 'departmentTotal': 
								departmentTotal();
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
					form.on('submit(LAY-searchsumday)', function(obj) {
						var field = obj.field;
						var orderTime=field.orderTimeBegin.split('~');
						field.orderTimeBegin=orderTime[0];
						field.orderTimeEnd=orderTime[1];
						eventd8(field);
						
					})
					var eventd8=function(data){
						table.reload("analysisRecuitsumday", {
							url: '${ctx}/personnel/sumday' ,
							where:data,
			              });
					};
					
					table.render({
						elem: '#analysisRecuitsumday',
						where:data,
						data:[],
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
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
								field: "md1",
								title: "录用人数",
								align: 'center',
							},{
								field: "md2",
								title: "待定人数",
								align: 'center',
							},{
								field: "md3",
								title: "不合格人数",
								align: 'center',
							},{
								field: "md4",
								title: "面试人数",
								align: 'center',
							},{
								field: "md6",
								title: "应面人数",
								align: 'center',
							},{
								field: "md5",
								title: "未应面人数",
								align: 'center',
							}
							]
						],
					
								});
					
					table.render({
						elem: '#analysisRecuit',
						where:data,
						data:[],
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
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
								totalRowText:'合计'
							},{
								field: "md6",
								title: "人数",
								align: 'center',
								totalRow:true,
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
								title: "部门",
								align: 'center',
								totalRowText: '合计'
							},{
								field: "mod1",
								title: "邀约面试",
								align: 'center',
								totalRow: true,
								event:'mod10'
							},{
								field: "mod2",
								title: "应邀面试",
								align: 'center',
								totalRow: true,
								event:'mod11'
							},{
								field: "mod3",
								title: "面试合格",
								align: 'center',
								totalRow: true,
								event:'mod12'
							},{
								field: "mod4",
								title: "拒绝入职",
								align: 'center',
								totalRow: true,
								event:'mod13'
							},{
								field: "mod5",
								title: "已入职且在职",
								align: 'center',
								totalRow: true,
								event:'mod14'
							},{
								field: "mod6",
								title: "即将入职",
								align: 'center',
								totalRow: true,
								event:'mod15'
							},{
								field: "mod7",
								title: "短期入职离职",
								align: 'center',
								totalRow: true,
								event:'mod16'
							}
							]
						],
								});
					
					table.on('tool(tableData)', function(obj) {
						var modName=obj.event
						var html="";
						var dicDiv=$('#layuitable');
						layer.open({
					         type: 1
					        ,title: '招聘人员情况' //不显示标题栏
					        ,closeBtn: false
					        ,zindex:-1
					        ,area:['70%', '90%']
					        ,shade: 0.5
					        ,id: 'LAY_layuipro26' //设定一个id，防止重复弹出
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
					        	$("#layuitable").hide();
							  } 
					      });
						var newArr = [];
						if(obj.event=='mod10'){
							$(obj.data.mod10).each(function(j,k){
								newArr.push(k);
								k.position=k.position.name;
								k.platform=k.platform.name;
								k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
								k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
								k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
							})
						}
						if(obj.event=='mod11'){
							$(obj.data.mod11).each(function(j,k){
								newArr.push(k);
								k.position=k.position.name;
								k.platform=k.platform.name;
								k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
								k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
								k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
							})
						}
						if(obj.event=='mod12'){
							$(obj.data.mod12).each(function(j,k){
								newArr.push(k);
								k.position=k.position.name;
								k.platform=k.platform.name;
								k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
								k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
								k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
							})
						}
						if(obj.event=='mod13'){
							$(obj.data.mod13).each(function(j,k){
								newArr.push(k);
								k.position=k.position.name;
								k.platform=k.platform.name;
								k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
								k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
								k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
							})
						}
						if(obj.event=='mod14'){
							$(obj.data.mod14).each(function(j,k){
								newArr.push(k);
								k.position=k.position.name;
								k.platform=k.platform.name;
								k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
								k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
								k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
							})
						}
						if(obj.event=='mod15'){
							$(obj.data.mod15).each(function(j,k){
								newArr.push(k);
								k.position=k.position.name;
								k.platform=k.platform.name;
								k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
								k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
								k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
							})
						}
						if(obj.event=='mod16'){
							$(obj.data.mod16).each(function(j,k){
								newArr.push(k);
								k.position=k.position.name;
								k.platform=k.platform.name;
								k.entry=(k.user==null ? "":k.user.entry==null ? "" :k.user.entry);
								k.quitDate=(k.user==null ? "":k.user.quitDate==null ? "":k.user.quitDate);
								k.reason=(k.user==null ? "":k.user.reason==null ? "":k.user.reason);
							})
						}
						table.render({
							elem: '#layuitable2',
							loading: true,
							toolbar: '#toolbar2', 
							sort:true,
							colFilterRecord: true,
							smartReloadModel: true,
							data:newArr,
							cols: [
								[{
									field: "name",
									title: "人名",
									align: 'center',
								},{
									field: "position",
									title: "职位",
									align: 'center',
								},{
									field: "time",
									title: "面试时间",
									align: 'center',
									width:160
								},{
									field: "platform",
									title: "平台",
									align: 'center',
								},{
									field: "recruitName",
									title: "招聘人",
									align: 'center',
								},{
									field: "entry",
									title: "入职时间",
									align: 'center',
									width:160
								},{
									field: "quitDate",
									title: "离职时间",
									align: 'center',
									width:160
								},{
									field: "reason",
									title: "离职原因",
									align: 'center',
								},{
									field: "remarksOne",
									title: "备注",
									align: 'center',
								}
								]
							],
									});
					})
					var eventd2=function(data){
						table.reload("layuiShare6", {
							url: '${ctx}/personnel/soon' ,
							where:data,
			              })
					};
					
					table.render({
						elem: '#layuiShare6',
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
									if(d.user!=null){
									return d.position.name		
									}
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
					
					 var check="off";
					form.on('checkbox(lockDemo)', function(obj){
						if(obj.elem.checked==true){
							check="on"
						}else{
						check="off"
						}
					  });
					var searchTime = '';  //记录搜索查询时间
				 	form.on('submit(searchTotal)',function(obj){
				 		searchTime = obj.field.time+'-01 00:00:00';
				 		table.reload('totalTable',{
					 		url: '${ctx}/personnel/getBasics',
					 		where : { time : searchTime},
				 		})
				 	})
					function lookoverTotal(){
				 		layer.open({
				 			type:1,
				 			content: $('#lookoverDiv'),
				 			shadeClose : true,
				 			area : ['60%','60%'],
				 		})
				 		table.render({
				 			elem: '#totalTable',
				 			data: [],
				 			parseData:function(ret){ 
				 				var data = [];
				 				data.push(ret.data);
				 				return { data:data,  msg:ret.message, code:ret.code } 
				 			},
				 			cols: [[
							       {align:'center', title:'宣传费',   field:'advertisementPrice',	  }, 
							       {align:'center', title:'人工费用',   field:'recruitUserPrice',  edit: true,  },
							       {align:'center', title:'培训费用',   field:'trainPrice',    },
							       {align:'center', title:'招聘费用汇总',   field:'sumPrice',    },
							       {align:'center', title:'招聘计划人数',   field:'planNumber',},
							       {align:'center', title:'入职人员数量',   field:'admissionNum',  },
							       {align:'center', title:'计划每人分到应聘费用',   field:'planPrice',},
							       {align:'center', title:'每人占到应聘费用',   field:'sharePrice',},
				 			        ]],
				 		})
				 	}
				 	laydate.render({
				 		elem: '#totalDepartmentTime',
				 		type: 'month',
				 	})
					function departmentTotal(){
				 		layer.open({
				 			type:1,
				 			content: $('#departmentDiv'),
				 			shadeClose : true,
				 			area : ['60%','60%'],
				 		})
				 		table.render({
				 			elem: '#totalDepartmentTable',
				 			data: [],
							parseData:function(ret){ return { data:ret.data, msg:ret.message, code:ret.code } },
							cols: [[
							       {align:'center', title:'部门',   field:'username',	  }, 
							       {align:'center', title:'部门招聘奖励金',   field:'ReceivePrice',},
							       {align:'center', title:'培训费用',   field:'trainPrice',},
							       {align:'center', title:'该部门占应聘费用',   field:'occupyPrice',},
							       {align:'center', title:'计划该部门占应聘费用',   field:'planPrice',},
							       {align:'center', title:'定向招聘费用',   field:'directional',},
					 			]],
				 		})
				 	}
				 	form.on('submit(searchDepartmentTotal)',function(obj){
				 		table.reload('totalDepartmentTable',{
					 		url: '${ctx}/personnel/findBasicsSummary',
					 		where : { time : obj.field.time+'-01 00:00:00'},
				 		})
				 	})
					//监听搜索
					form.on('submit(LAY-search)', function(data) {
						var field = data.field;
						var orderTime=field.time;
						field.time=orderTime+"-01 00:00:00";
						table.reload('tableData', {
							where: field,
						});
						table.reload("layuitable2")
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
								 async:false,
								beforeSend: function() {
									index;
								},
								success: function(result) {
									if(0 == result.code) {
									 	 table.reload("tableData", {
							                page: {
							                }
							              })   
							             /*  document.getElementById("layuiadmin-form-admin").reset(); */
						        	layui.form.render();
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
