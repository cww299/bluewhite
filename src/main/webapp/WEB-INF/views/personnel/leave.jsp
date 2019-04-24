<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">


<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<script src="${ctx }/static/js/shujuhuixian/sjhx.js"></script>
<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script>  
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>请假调休</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>
<body>

	<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form ">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>申请人:</td>
							<td><select id="userId" class="layui-input " lay-search="true" name="userId"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>部门:</td>
							<td >
								<select id="orgNameId" class="layui-input "  lay-search="true" name="orgNameId">
									<option value=""></option></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>开始:</td>
							<td><input id="startTime" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon"></td>
							<td>&nbsp;&nbsp;</td>
							<td>结束:</td>
							<td><input id="endTime" name="orderTimeEnd" placeholder="请输入结束时间" class="layui-input laydate-icon"> </td>
							<td>&nbsp;&nbsp;</td>
							<td> <div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-search">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>

			<table id="tableData" class="table_th_search"
				lay-filter="tableData"></table>
		</div>

	</div>
	
	<form action="" id="layuiadmin-form-admin" style="padding: 20px 0px 0 60px; display: none; text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 90px;">申请人</label>
				<div class="layui-input-inline">
					<select name="userId" lay-filter="lay_selecte" lay-verify="required" id="selectOne" lay-search="true"></select>
				</div>
			</div>
		
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 90px;">申请时间</label>
				<div class="layui-input-inline">
					<input type="text" name="applytime" id="applytime"
						lay-verify="required" placeholder="请输入申请时间"
						class="layui-input laydate-icon">
				</div>
			</div>
		
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 90px;">申请项</label>
				<div class="layui-input-inline" id="selectradio" style="width: 330px;">
					<input type="radio" lay-filter="variable" id="qing" name="variable" value="0" title="请假" checked="">
					<input type="radio" lay-filter="variable" id="tiao" name="variable" value="1" title="调休"> 
					<input type="radio" lay-filter="variable" id="bu" name="variable" value="2" title="补签">
					<input type="radio" lay-filter="variable" id="jia" name="variable" value="3" title="加班">
				</div>
			</div>
			
			<div id="leave" style="display: block;">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 90px;">请假类型</label>
					<div class="layui-input-inline">
						<select name="holidayType" lay-filter="holidayType"
							id="holidayType" lay-search="true"><option value="">请选择</option>
							<option value="0">事假</option>
							<option value="1">病假</option>
							<option value="2">丧假</option>
							<option value="3">婚假</option>
							<option value="4">产假</option>
							<option value="5">护理假</option>
							<option value="6">抵消迟到</option></select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 90px;">请假原因</label>
					<div class="layui-input-inline">
						<input type="text" name="content" id="content"
							lay-verify="content" placeholder="请输入请假原因 " class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 90px;">日期时长</label>
					<div class="layui-input-inline">
						<input type="text"
							style="width: 190px; position: absolute; float: left;"
							name="leavetime" id="leavetime" lay-verify="leavetime"
							placeholder="请输入请假日期" class="layui-input laydate-icon">
						<input type="text"
							style="width: 90px; position: absolute; float: left; margin-left: 210px;"
							name="leaveduration" id="leaveduration"
							lay-verify="leaveduration" placeholder="时长 " class="layui-input">

					</div>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 90px;">显示</label>
					<div class="layui-input-inline" style="width: 230px;">
						<textarea name="inputapplytime2" id="inputapplytime2" class="layui-textarea"></textarea>
					</div>
				</div>
			</div>

			

		   <div id="Break" style="display: none;">
			    <div class="layui-form-item">
			      <label class="layui-form-label" style="width: 90px;">调休时间</label>
			      <div class="layui-input-inline">
			        <input type="text" name="breaktime" id="breaktime" style="width: 190px; position:absolute; float: left;" lay-verify="breaktime" placeholder="请输入调休到哪一天的时间" class="layui-input laydate-icon">
			      	<input type="text" style="width: 90px; position:absolute; float: left; margin-left: 210px;" name="breakduration" id="breakduration" lay-verify="breakduration" placeholder="时长 " class="layui-input">
			      </div>
			    </div>
			    
			    <div class="layui-form-item" >
				      <label class="layui-form-label" style="width: 90px;">显示</label>
				      <div class="layui-input-inline" style="width: 230px;">
				      <textarea name="inputapplytime3"   id="inputapplytime3" class="layui-textarea"></textarea>
			      </div>
			    </div>
		    </div>

			<div id="repair" style="display: none;">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 90px;">补签日期</label>
					<div class="layui-input-inline">
						<input type="text" id="repairtime" lay-verify="repairtime" placeholder="请输入补签日期" class="layui-input laydate-icon">
					</div>
					<input type="checkbox" id="moren" name="like[write]" title="默认" checked="true">
					<td>&nbsp;&nbsp;</td>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 90px;">显示</label>
					<div class="layui-input-inline" style="width: 230px;">
						<textarea name="repairtime" id="inputapplytime" class="layui-textarea"></textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 90px;">补签状态</label>
					<div class="layui-input-inline" style="width: 325px;">
						<input type="radio" name="Sign" id="qianru" value="0" title="签入" checked=""> <input type="radio" name="Sign" id="qianchu" value="1" title="签出">
					</div>
				</div>
				
			</div>

			<div id="overtime" style="display: none;">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 90px;">加班类型</label>
					<div class="layui-input-inline">
						<select name="overtime_type" lay-filter="overtime_type" id="overtime_type" lay-search="true">
							<option value="1">正常加班</option>
							<option value="2">撤销加班</option></select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 90px;">加班</label>
					<div class="layui-input-inline">
						<input type="text" name="overtime"
							style="width: 190px; position: absolute; float: left;"
							id="overdurationtime" lay-verify="overtime" placeholder="请输入加班日期"
							class="layui-input laydate-icon"> <input type="text"
							name="overduration"
							style="width: 90px; position: absolute; float: left; margin-left: 210px;"
							id="overduration" lay-verify="overduration" placeholder="加班时长 "
							class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 90px;">显示</label>
					<div class="layui-input-inline" style="width: 230px;">
						<textarea name="inputapplytime4" id="inputapplytime4" class="layui-textarea"></textarea>
					</div>
				</div>
			</div>
		
			<div class="layui-form-item layui-hide">
				<button type="submit" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
	
	
	
	
	
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="notice">新增</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
			</div>
		</script>
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-trans layui-btn-xs"  lay-event="update">编辑</a>
	</script>
	
	<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug'
			}).define(
				['tablePlug', 'laydate', 'element','form'],
				function() {
					var $ = layui.jquery,
						layer = layui.layer //弹层
						,
						form = layui.form //表单
						,
						table = layui.table //表格
						,
						laydate = layui.laydate //日期控件
						,
						tablePlug = layui.tablePlug //表格插件
						,
						element = layui.element;

					//select全局变量
					var htmls = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					timeAll4='';
					laydate.render({
						elem: '#overdurationtime',
						type: 'datetime',
						done: function(value, date) {
							var c=$("#inputapplytime4").val()
							var a=$("#overduration").val();
							if(a!=""){
							timeAll4=(timeAll4==''? value+','+a:(c+'\n'+value+','+a));
							$("#inputapplytime4").val(timeAll4)
							}
						}
					});
					timeAll5=""
					laydate.render({
						elem: '#breaktime',
						format: 'yyyy-MM-dd',
						done: function(value, date) {
							var c=$("#inputapplytime3").val()
							var a=$("#breakduration").val();
							if(a!=""){
							timeAll5=(timeAll5==''? value+','+a:(c+'\n'+value+','+a));
							$("#inputapplytime3").val(timeAll5)
							}
						}
					});
					var timeAll=""
					laydate.render({
						elem: '#repairtime',
						type: 'datetime',
						done: function(value, date) {
							var c=$("#inputapplytime").val()
							var time=/\d{4}-\d{1,2}-\d{1,2}/g.exec(value)
							if($("#moren").get(0).checked==true){
								var a='';
								if($("#qianru").get(0).checked==true){
									a = time[0]+' '+'08:30:00'
									timeAll=(timeAll==''? a:(c+','+a));
								}
								if($("#qianchu").get(0).checked==true){
									a=time[0]+' '+'17:30:00'
									timeAll=(timeAll==''? a:(c+','+a));
								}
							}else{
								timeAll=(timeAll==''? value:(c+','+value));
							}
							$("#inputapplytime").val(timeAll)
						}
					});
					laydate.render({
						elem: '#applytime',
						type: 'datetime',
					});
					var timeAll2='';
					laydate.render({
						elem: '#leavetime',
						range : '~',
						format: 'yyyy-MM-dd',
						done: function(value, date) {
							var c= $("#inputapplytime2").val()
							var a=$("#leaveduration").val();
							if(a!=""){
							timeAll2=(timeAll2==''? value+','+a:(c+'\n'+value+','+a));
							$("#inputapplytime2").val(timeAll2)
							}
						}
					});
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
					});
					laydate.render({
						elem: '#endTime',
						type: 'datetime',
					});
					$("#leaveduration").blur(function(){
						var c= $("#inputapplytime2").val()
						var b=$("#leavetime").val();
						var a=$("#leaveduration").val();
						if(b!=""){
							timeAll2=(timeAll2==''? b+','+a:(c+'\n'+b+','+a));
							$("#inputapplytime2").val(timeAll2)
							}
						$("#leavetime").val("");
						$("#leaveduration").val("");
					})
					$("#overduration").blur(function(){
						var c= $("#inputapplytime4").val()
						var b=$("#overdurationtime").val();
						var a=$("#overduration").val();
						if(b!=""){
							timeAll4=(timeAll4==''? b+','+a:(c+'\n'+b+','+a));
							$("#inputapplytime4").val(timeAll4)
							}
						$("#overdurationtime").val("");
						$("#overduration").val("");
					})
					$("#breakduration").blur(function(){
						var c= $("#inputapplytime3").val()
						var b=$("#breaktime").val();
						var a=$("#breakduration").val();
						if(b!=""){
							timeAll5=(timeAll5==''? b+','+a:(c+'\n'+b+','+a));
							$("#inputapplytime3").val(timeAll5)
							}
						$("#breaktime").val("");
						$("#breakduration").val("");
					})
					$.ajax({
						url: '${ctx}/system/user/findAllUser',
						type: "GET",
						async: false,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							$(result.data).each(function(i, o) {
								htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
							})
							layer.close(index);
						$('#selectOne').append(htmls);
						$("#userId").append(htmls);
						},
						error: function() {
							layer.msg("操作失败！", {
								icon: 2
							});
							layer.close(index);
						}
					});
					
					var getdata = {
							type : "orgName",
						}
					var htmlfr=""
						$.ajax({
							url : "${ctx}/basedata/list",
							data : getdata,
							type : "GET",
							beforeSend : function() {
								index;
							},
							success : function(result) {
								$(result.data).each(function(k, j) {
									htmlfr += '<option value="'+j.id+'">' + j.name + '</option>'
								});
								$("#orgNameId").append(htmlfr);
								layer.close(index);
							}
						});

					
					
					
				 	/* tablePlug.smartReload.enable(true);  */
					table.render({
						elem: '#tableData',
						size: 'lg',
						url: '${ctx}/personnel/getApplicationLeavePage' ,
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
						} //开启分页
						,
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
							}, {
								field: "userId",
								title: "申请人",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: function(d){
									
									return d.user.userName
								}
							}, {
								field: "writeTime",
								title: "申请时间",
							}, {
								field: "expenseDate",
								title: "申请项",
								templet: function(d){
									if(d.addSignIn==true){
									return "补签"
									}
									if(d.applyOvertime==true){
										return "加班"
									}
									if(d.holiday==true){
										return "请假"
									}
									if(d.tradeDays==true){
										return "调休"
									}
								}
							}, {
								field: "holidayDetail",
								title: "详情",
							},{fixed:'right', title:'操作', align: 'center', toolbar: '#barDemo'}]
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
							layui.each(tableElem.find('select'), function(index, item) {
								var elem = $(item);
								elem.val(elem.data('value'));
							});
							form.render();
						},

					});

					
					form.on("radio(variable)", function(data) {
						if(data.value==0){
							$("#leave").css("display","block")
							$("#Break").css("display","none")
							$("#repair").css("display","none")
							$("#overtime").css("display","none")
						}
						if(data.value==1){
							$("#Break").css("display","block")
							$("#leave").css("display","none")
							$("#repair").css("display","none")
							$("#overtime").css("display","none")
							 
						}
						if(data.value==2){
							$("#repair").css("display","block")
							$("#Break").css("display","none")
							$("#leave").css("display","none")
							$("#overtime").css("display","none")
						}
						if(data.value==3){
							$("#overtime").css("display","block")
							$("#leave").css("display","none")
							$("#Break").css("display","none")
							$("#repair").css("display","none")
						}
					});	
					
					
					//监听头工具栏事件
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
										url: "${ctx}/personnel/deleteApplicationLeave",
										data: postData,
										traditional: true,
										type: "GET",
										beforeSend: function() {
											index;
										},
										success: function(result) {
											if(0 == result.code) {
												table.reload('tableData');
												layer.msg(result.message, {
													icon: 1,
													time:500
												});
											} else {
												layer.msg(result.message, {
													icon: 2,
													time:500
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
							case 'notice':
								var dicDiv=$('#layuiadmin-form-admin');
								document.getElementById("layuiadmin-form-admin").reset();
					        	$("#leave").css("display","block")
								$("#Break").css("display","none")
								$("#repair").css("display","none")
								$("#overtime").css("display","none")
					        	layui.form.render();
								layer.open({
							         type: 1
							        ,title: "新增" //不显示标题栏
							        ,closeBtn: false
							        ,area:['540px', '550px']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
							        ,btn: ['确认', '取消']
							        ,btnAlign: 'c'
							        ,moveType: 1 //拖拽模式，0或者1
							        ,content:dicDiv
							        ,success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        }
							        ,yes: function(index, layero){
							        	
							        	form.on('submit(addRole)', function(data) {
							        		var variable='';
							        		var holidayType='';
							        		var content='';
							        		var leavetime='';
							        		var leaveduration='';
							        		var time='';
							        		var myArray = [];
							        		var overtime_type='';
												
							        		if(data.field.variable==0){
							        			if(data.field.holidayType==""){
							        				return layer.msg("请假类型不能为空", {icon: 2});
							        			}
							        			if(data.field.content==""){
							        				return layer.msg("请假原因不能为空", {icon: 2});
							        			}
							        			if(data.field.inputapplytime2==""){
							        				return layer.msg("请假日期跟内容不能为空", {icon: 2});
							        			}
							        			variable='holiday';
							        			holidayType=data.field.holidayType;
							        			content=data.field.content;
							        			leavetime=data.field.leavetime;
							        			leaveduration=data.field.leaveduration;
							        			var a=(data.field.inputapplytime2)
								        		ss = a.split("\n")
								        		for (var i = 0; i < ss.length; i++) {
								        			if(ss[i] != ""){  
													var b=ss[i]
													cc = b.split(",")
													time={"date":cc[0],"time":cc[1]};
													myArray.push(time)
								                    } 
												}
							        		}
							        		if(data.field.variable==1){
							        			if(data.field.inputapplytime3==""){
							        				return layer.msg("调休时长不能为空", {icon: 2});
							        			}
							        			
							        			variable='tradeDays'
							        			breaktime=data.field.breaktime;
							        			breakduration=data.field.breakduration;
							        			overtime_type=data.field.overtime_type;
							        			var a=(data.field.inputapplytime3)
								        		ss = a.split("\n")
								        		for (var i = 0; i < ss.length; i++) {
								        			if(ss[i] != ""){  
														var b=ss[i]
														cc = b.split(",")
														time={"date":cc[0],"time":cc[1]};
														myArray.push(time)
									                    } 
												}
							        			
							        		}
							        		if(data.field.variable==2){
							        			if(data.field.repairtime==""){
							        				return layer.msg("补签日期不能为空", {icon: 2});
							        			}
							        			variable='addSignIn' 
							        			repairtime=data.field.repairtime;
							        			Sign=data.field.Sign
							        			time={date:repairtime,time:Sign};
							        			myArray.push(time)
							        		}
							        		if(data.field.variable==3){
							        			
							        			if(data.field.inputapplytime4==""){
							        				return layer.msg("加班日期跟内容不能为空", {icon: 2});
							        			}
							        			variable='applyOvertime'
							        			overtime=data.field.overtime;
							        			overduration=data.field.overduration;
							        			var a=(data.field.inputapplytime4)
								        		ss = a.split("\n")
								        		for (var i = 0; i < ss.length; i++) {
								        			if(ss[i] != ""){  
														var b=ss[i]
														cc = b.split(",")
														time={"date":cc[0],"time":cc[1]};
														myArray.push(time)
									                    } 
												}
							        		}
							        	var postData={
							        			userId:data.field.userId,
							        			writeTime:data.field.applytime,
							        			[variable]:'true',
							        			holidayType:holidayType,
							        			overtime_type:overtime_type,
							        			content:content,
							        			time:JSON.stringify(myArray)
							        	}	
							        	 mainJs.fAdd(postData);
							        	
							        	timeAll=""
										})
										
							        }
							        ,end:function(){
							        	document.getElementById("layuiadmin-form-admin").reset();
							        	layui.form.render();
							        	timeAll=""
									  } 
							      });
								break;
						}
					});
					
					//监听单元格编辑
					table.on('tool(tableData)', function(obj) {
						document.getElementById("layuiadmin-form-admin").reset();
			        	layui.form.render();
						var data = obj.data;
						var id=data.id;
					    if(obj.event === 'update'){
					    	var dicDiv=$('#layuiadmin-form-admin');
					    	$('#selectOne').each(function(j,k){
								var id=data.user.id;
								$(k).val(id);
								form.render('select');
							});
					    	$('#holidayType').each(function(j,k){
					    		var id=data.holidayType;
								$(k).val(id);
								form.render('select');
							});
					    	if(data.holiday==true){
					    		$("#leave").css("display","block")
								$("#Break").css("display","none")
								$("#repair").css("display","none")
								$("#overtime").css("display","none")
					    	$("#qing").get(0).checked=true;
					    	form.render('radio');
					    	$("#layuiadmin-form-admin").setForm({content:data.content,applytime:data.writeTime,leavetime:JSON.parse(data.time).date,leaveduration:JSON.parse(data.time).time});
					    	
					    	var arr=JSON.parse(JSON.parse(JSON.stringify(data.time)))
					    	var html="";
					    	for (var i = 0; i < arr.length; i++) {
					    		html+=arr[i].date+','+arr[i].time+'\n'
							}
					    	 $("#inputapplytime2").val(html)
					    	}
					    	if(data.tradeDays==true){
					    		$("#Break").css("display","block")
								$("#leave").css("display","none")
								$("#repair").css("display","none")
								$("#overtime").css("display","none")
					    		$("#tiao").get(0).checked=true;
					    		form.render('radio');
					    		$("#layuiadmin-form-admin").setForm({applytime:data.writeTime});
					    		var arr=JSON.parse(JSON.parse(JSON.stringify(data.time)))
						    	var html="";
						    	for (var i = 0; i < arr.length; i++) {
						    		html+=arr[i].date+','+arr[i].time+'\n'
								}
						    	 $("#inputapplytime3").val(html)
					    	}
					    	if(data.addSignIn==true){
					    		$("#repair").css("display","block")
								$("#Break").css("display","none")
								$("#leave").css("display","none")
								$("#overtime").css("display","none")
					    		$("#bu").get(0).checked=true;
					    		form.render('radio');
					    		$("#layuiadmin-form-admin").setForm({applytime:data.writeTime,repairtime:JSON.parse(data.time)[0].date});
					    		if(JSON.parse(data.time).time==0){
					    			$("#qianru").get(0).checked=true;
						    		form.render('radio');
					    		}
					    		if(JSON.parse(data.time).time==1){
					    			$("#qianchu").get(0).checked=true;
						    		form.render('radio');
					    		}
					    	}
					    	if(data.applyOvertime==true){
					    		$("#overtime").css("display","block")
								$("#leave").css("display","none")
								$("#Break").css("display","none")
								$("#repair").css("display","none")
					    		$("#jia").get(0).checked=true;
					    		form.render('radio');
					    		$("#layuiadmin-form-admin").setForm({applytime:data.writeTime});
					    		var arr=JSON.parse(JSON.parse(JSON.stringify(data.time)))
						    	var html="";
						    	for (var i = 0; i < arr.length; i++) {
						    		html+=arr[i].date+','+arr[i].time+'\n'
								}
						    	 $("#inputapplytime4").val(html)
					    	}
					    	layer.open({
						         type: 1
						        ,title: "修改" //不显示标题栏
						        ,closeBtn: false
						        ,area:['540px', '60%']
						        ,shade: 0.5
						        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
						        ,btn: ['确定', '取消']
						        ,btnAlign: 'c'
						        ,moveType: 1 //拖拽模式，0或者1
						        ,content:dicDiv
						        ,success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole',
										'lay-submit' : ''
									})
						        }
						        ,yes: function(index, layero){
						        	form.on('submit(addRole)', function(data) {
						        		var variable='';
						        		var holidayType='';
						        		var content='';
						        		var leavetime='';
						        		var leaveduration='';
						        		var time='';
						        		var myArray = [];
						        		var overtime_type='';
						        		if(data.field.variable==0){
						        			if(data.field.holidayType==""){
						        				return layer.msg("请假类型不能为空", {icon: 2});
						        			}
						        			if(data.field.content==""){
						        				return layer.msg("请假原因不能为空", {icon: 2});
						        			}
						        			if(data.field.inputapplytime2==""){
						        				return layer.msg("请假日期跟内容不能为空", {icon: 2});
						        			}
						        			variable='holiday';
						        			holidayType=data.field.holidayType;
						        			content=data.field.content;
						        			leavetime=data.field.leavetime;
						        			leaveduration=data.field.leaveduration;
						        			var a=(data.field.inputapplytime2)
							        		ss = a.split("\n")
							        		for (var i = 0; i < ss.length; i++) {
							        			if(ss[i] != ""){  
												var b=ss[i]
												cc = b.split(",")
												time={"date":cc[0],"time":cc[1]};
												myArray.push(time)
							                    } 
											}
						        		}
						        		if(data.field.variable==1){
						        			if(data.field.inputapplytime3==""){
						        				return layer.msg("调休时长跟日期不能为空", {icon: 2});
						        			}
						        			variable='tradeDays'
						        			var a=(data.field.inputapplytime3)
						        			ss = a.split("\n")
							        		for (var i = 0; i < ss.length; i++) {
							        			if(ss[i] != ""){  
												var b=ss[i]
												cc = b.split(",")
												time={"date":cc[0],"time":cc[1]};
												myArray.push(time)
							                    } 
											}
						        		}
						        		if(data.field.variable==2){
						        			if(data.field.repairtime==""){
						        				return layer.msg("补签日期不能为空", {icon: 2});
						        			}
						        			variable='addSignIn' 
						        			repairtime=data.field.repairtime;
						        			Sign=data.field.Sign
						        			time={date:repairtime,time:Sign};
						        			myArray.push(time);
						        			console.log(JSON.stringify(myArray));
						        		}
						        		if(data.field.variable==3){
						        			if(data.field.inputapplytime4==""){
						        				return layer.msg("加班日期跟内容不能为空", {icon: 2});
						        			}
						        			variable='applyOvertime'
						        			overtime=data.field.overtime;
						        			overduration=data.field.overduration;
						        			overtime_type=data.field.overtime_type;
						        			var a=(data.field.inputapplytime4)
							        		ss = a.split("\n")
							        		for (var i = 0; i < ss.length; i++) {
							        			if(ss[i] != ""){  
													var b=ss[i]
													cc = b.split(",")
													time={"date":cc[0],"time":cc[1]};
													myArray.push(time)
								                    } 
											}
						        		}
						        	var postData={
						        			id:id,
						        			userId:data.field.userId,
						        			writeTime:data.field.applytime,
						        			[variable]:'true',
						        			holidayType:holidayType,
						        			overtime_type:overtime_type,
						        			content:content,
						        			time:JSON.stringify(myArray)
						        	}	
						        	 mainJs.fAdd(postData); 
						        	
									})

						        },end:function(){
						        	document.getElementById("layuiadmin-form-admin").reset();
						        	layui.form.render();
						        	timeAll=""
								  } 
						       
						      });
					    	
					    	
					    }
					});

					//监听搜索
					form.on('submit(LAY-role-search)', function(data) {
						var field = data.field;
						$.ajax({
							url: "${ctx}/personnel/getApplicationLeavePage",
							type: "get",
							data: field,
							dataType: "json",
							success: function(result) {
								table.reload('tableData', {
									where: field
								});
							}
						});
					});
					
					
					//封装ajax主方法
					var mainJs = {
						//新增							
					    fAdd : function(data){console.log(data);
					    	$.ajax({  
								url: "${ctx}/personnel/addApplicationLeave",
								data: data,
								type: "Post",
								beforeSend: function() {
									index;
								},
								success: function(result) {
									if(0 == result.code) {
										table.reload('tableData');
										layer.msg(result.message, {
											icon: 1,
											time:1000
										});
										if(data.id==null){
										document.getElementById("layuiadmin-form-admin").reset();
							        	$("#leave").css("display","block")
										$("#Break").css("display","none")
										$("#repair").css("display","none")
										$("#overtime").css("display","none")
							        	layui.form.render();
										}
									} else {
										layer.msg(result.message, {
											icon: 2,
											time:5000
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