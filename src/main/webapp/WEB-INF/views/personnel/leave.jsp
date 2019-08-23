<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<script src="${ctx }/static/js/shujuhuixian/sjhx.js"></script>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>请假调休</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<style>
.showTimeDiv{
	height:120px;
	overflow-y: scroll;
}
.showTimeDiv p{
	height:28px;
}
.showTimeDiv span{
	height:24px;
}
#orgAndPersonDiv{
	float: right;
    width: 200px;
    height: 400px;
    padding: 10px; 
    border: 1px solid #e2e2e2;
	overflow-y: scroll;
	display:none;
}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<div class="layui-form "> 
			<div class="layui-form-item">
				<table>
					<tr>
						<shiro:lacksRole name="attendanceStatistician">
							<td>申请人:</td>
							<td><select id="userId" class="layui-input " lay-search="true" name="userId"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>部门:</td>
							<td><select id="orgNameId" class="layui-input "  lay-search="true" name="orgNameId">
									<option value=""></option></select></td>
						</shiro:lacksRole>
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
		<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
	</div>
</div>
	
<form action="" id="layuiadmin-form-admin" style="padding: 20px; display: none; text-align:">
	<div class="layui-form" style="float:left;width:500px;" lay-filter="layuiadmin-form-admin">
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 90px;">申请人</label>
			<div class="layui-input-inline">
				<select name="userId" lay-filter="lay_selecte" id="selectOne" lay-search="true"></select>
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
		<!-- 请假 -->
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
					<input type="text" name="leavetime" id="leavetime" placeholder="请输入请假日期" class="layui-input">
				</div>
				<div class="layui-input-inline" style="width: 15%;">
					<input type="text" name="leaveduration" id="leaveduration" placeholder="时长 " class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 90px;">显示</label>
				<div class="layui-input-inline" style="width: 230px;">
					<div name="inputapplytime2" id="inputapplytime2" class="layui-textarea showTimeDiv"></div>
				</div>
			</div>
		</div>
		<!-- 调休 -->
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
			      <div name="inputapplytime3"   id="inputapplytime3" class="layui-textarea showTimeDiv"></div>
		      </div>
		    </div>
	    </div>
		<!-- 补签 -->
		<div id="repair" style="display: none;">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 90px;">补签状态</label>
				<div class="layui-input-inline" style="width: 325px;">
					<input type="radio" name="Sign" id="qianru" value="0" title="签入" checked=""> <input type="radio" name="Sign" id="qianchu" value="1" title="签出">
				</div>
			</div>
			<div class="layui-form-item"> 
				<label class="layui-form-label" style="width: 90px;">补签日期</label>
				<div class="layui-input-inline">
					<input type="text" id="repairtime" placeholder="请输入补签日期" class="layui-input">
				</div>
				<input type="checkbox" id="moren" name="like[write]" title="默认" checked lay-filter='moren'>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 90px;">显示</label>
				<div class="layui-input-inline" style="width: 230px;">
					<div name="repairtime" id="inputapplytime" class="layui-textarea showTimeDiv"></div>
				</div>
			</div>
		</div>
		<!-- 加班 -->
		<div id="overtime" style="display: none;">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 90px;">加班类型</label>
				<div class="layui-input-inline">
					<select name="overtime_type" lay-filter="overtime_type" id="overtime_type" lay-search="true" value="1">
						<option value="1">正常加班</option>
						<option value="3">生产加班</option>
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
					<div name="inputapplytime4" id="inputapplytime4" class="layui-textarea showTimeDiv"></div>
				</div>
			</div>
		</div>
		</div>
	<div id="orgAndPersonDiv"></div>
</form>
<script type="text/html" id="toolbar">
	<div class="layui-btn-container layui-inline">
	<span class="layui-btn layui-btn-sm" lay-event="notice">新增</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
</div>
</script>
<script type="text/html" id="barDemo">
	<a class="layui-btn layui-btn-trans layui-btn-xs layui-bg-blue"  lay-event="update">编辑</a>
</script>
<script>
	layui.config({
		base: '${ctx}/static/layui-v2.4.5/'
	}).extend({
		tablePlug: 'tablePlug/tablePlug',
		menuTree : 'layui/myModules/menuTree'
	}).define(
		['tablePlug', 'laydate', 'element','form','menuTree'],
		function() {
			var $ = layui.jquery,
				layer = layui.layer,
				form = layui.form,
				menuTree = layui.menuTree,
				table = layui.table ,
				laydate = layui.laydate,
				tablePlug = layui.tablePlug,
				element = layui.element;
			//select全局变量
			var htmls = '<option value="">请选择</option>';
			var index = layer.load(1, { shade: [0.1] });
			layer.close(index)
			timeAll4='';
			laydate.render({
				elem: '#overdurationtime',
				type: 'datetime',
			});
			timeAll5=""
			laydate.render({
				elem: '#breaktime',
				format: 'yyyy-MM-dd',
			});
			var timeAll=""
			laydate.render({
				elem: '#repairtime',
				type: 'datetime',
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
			});
			laydate.render({
				elem: '#startTime',
				type: 'datetime',
				calendar: true,
			});
			laydate.render({
				elem: '#endTime',
				type: 'datetime',
			});
			var moren = true;
			
			var isAttend = true,orgId = '';	  //是否是考情记录员,和所在部门
			;!(function(){
				if(document.getElementById('userId')==null){
					;!(function(){
						$.ajax({
							url:'${ctx}/getCurrentUser',		
							async:false,
							success:function(r){
								if(0==r.code)
									orgId = r.data.orgNameId;
							}
						})
					})();
				}else{
					isAttend = false;
					var load = layer.load(1);	//下拉框初始渲染
					$.ajax({
						url : "${ctx}/basedata/list?type=orgName",
						async: false,
						success : function(result) {
							var htmlfr=""
							$(result.data).each(function(k, j) {
								htmlfr += '<option value="'+j.id+'">' + j.name + '</option>'
							});
							$("#orgNameId").append(htmlfr);
						}
					});
					form.render();
					layer.close(load);
				}
				$.ajax({
					url: '${ctx}/system/user/findUserList?foreigns=0&isAdmin=false'+(isAttend?'&orgNameIds='+orgId:''),
					async: false,
					success: function(result) {
						$(result.data).each(function(i, o) {
							htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
						})
						$('#selectOne').append(htmls);
						$("#userId").append(htmls);
					},
				});
			})();
			form.on('checkbox(moren)',function (data) {
				moren = data.elem.checked;
			})
			$('#inputapplytime').on('click',function(){
				var val=$("#repairtime").val();	
				var title = val;
				if(val!=''){
					var time = 0;
					if(moren){	//如果默认补签
						if($("#qianru").get(0).checked==true){
							title = val.split(' ')[0] + ' 补签入';
							val = val.split(' ')[0] + ' 00:00:00';
						}
						if($("#qianchu").get(0).checked==true){
							time = 1;
							title = val.split(' ')[0] + ' 补签出';
							val = val.split(' ')[0] + ' 00:00:00'
						}
					}else{	//如果是0点，默认加1秒
						if(val.split(' ')[1]=='00:00:00')
							val = val.split(' ')[0]+' 00:00:01';
					}
					var html = ['<p>',
									'<span class="layui-badge layui-bg-green" data-value="'+val+'" data-time="'+time+'">',
									title,
						            '<i class="layui-icon layui-icon-close"></i></span>',
						         '</p>',].join(' ');
					$("#repairtime").val("");		//清空内容
					$("#inputapplytime").append(html)
					$('#inputapplytime').find('.layui-icon-close').on('click',function(){	//删除节点
						$(this).parent().parent().remove();
					})
				}
			})
			$("#leaveduration").blur(function(){		//当时长输入框失去焦点的时候
				var leaveDate=$("#leavetime").val();
				var leaveTime=$("#leaveduration").val();
				if(leaveTime!='' && leaveDate!=''){
					var val = leaveDate+','+leaveTime;
					var html = '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
					$("#leavetime").val("");		//清空内容
					$("#leaveduration").val("");
					$("#inputapplytime2").append(html)
					$('#inputapplytime2').find('.layui-icon-close').on('click',function(){	//删除节点
						$(this).parent().parent().remove();
					})
				}
			})
			$("#overduration").blur(function(){
				var leaveDate=$("#overdurationtime").val();
				var leaveTime=$("#overduration").val();
				if(leaveTime!='' && leaveDate!=''){
					var val = leaveDate+','+leaveTime;
					var html = '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
					$("#overdurationtime").val("");		//清空内容
					$("#overduration").val("");
					$("#inputapplytime4").append(html)
					$('#inputapplytime4').find('.layui-icon-close').on('click',function(){	//删除节点
						$(this).parent().parent().remove();
					})
				}
			})
			$("#breakduration").blur(function(){
				var leaveDate=$("#breaktime").val();
				var leaveTime=$("#breakduration").val();
				if(leaveTime!='' && leaveDate!=''){
					var val = leaveDate+','+leaveTime;
					var html = '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
					$("#breaktime").val("");		//清空内容
					$("#breakduration").val("");
					$("#inputapplytime3").append(html)
					$('#inputapplytime3').find('.layui-icon-close').on('click',function(){	//删除节点
						$(this).parent().parent().remove();
					})
				}
			})
			
			var getdata = {	 };
				
			table.render({
				elem: '#tableData',
				size: 'lg',
				height:'700px',
				url: '${ctx}/personnel/getApplicationLeavePage'+(isAttend?'?orgNameId='+orgId:'') ,
				request:{ pageName: 'page' , limitName: 'size'  },
				page: {},
				toolbar: '#toolbar', 
				cellMinWidth: 90,
				colFilterRecord: true,
				smartReloadModel: true,
				parseData: function(ret) { return { code: ret.code, msg: ret.message, count:ret.data.total, data: ret.data.rows } },
				cols: [
					[{ type: 'checkbox', align: 'center', fixed: 'left', },
					 { field: "userId", title: "申请人", align: 'center', type: 'normal', width: '10%',
						templet: function(d){
							return d.user.userName
						}
					}, 
					{ field: "writeTime", title: "申请时间", align: 'center', width: '12%', }, 
					{ field: "expenseDate", title: "申请项", align: 'center', width: '8%',
						templet: function(d){
							if(d.addSignIn)
								return "补签"
							if(d.applyOvertime)
								return "加班"
							if(d.holiday)
								return "请假"
							if(d.tradeDays)
								return "调休"
						}
					}, 
					{
						field: "holidayDetail", align: 'center', title: "详情",
						templet:function(d){
							var arr = d.holidayDetail.split(',');
							var html = '';
							for(i in arr){
								if(arr[i]!='')
									html+='<span class="layui-badge layui-bg-green">'+arr[i]+'</span>&nbsp;&nbsp;';
							}
							return html;
						}
					},
					{fixed:'right', title:'操作', align: 'center', width: '10%',toolbar: '#barDemo'}]
				],
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
										layer.msg(result.message, { icon: 1, time:500 });
									} else {
										layer.msg(result.message, { icon: 2, time:500 });
									}
								},
								error: function() {
									layer.msg("操作失败！", { icon: 2 });
								}
							});
							layer.close(index);
						});
						break;
					case 'notice':
						var dicDiv=$('#layuiadmin-form-admin');
						document.getElementById("layuiadmin-form-admin").reset();
						$('#inputapplytime').html('');
						$('#inputapplytime2').html('');
						$('#inputapplytime3').html('');
						$('#inputapplytime4').html('');
			        	$("#leave").css("display","block")
						$("#Break").css("display","none")
						$("#repair").css("display","none")
						$("#overtime").css("display","none")
			        	layui.form.render();
			        	$('#orgAndPersonDiv').show();	//新增时候显示人员
			        	moren = true;
						layer.open({
					         type: 1
					        ,title: "新增" //不显示标题栏
					        ,area:['800px', '550px']
					        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
					        ,btn: ['确认', '取消']
					        ,btnAlign: 'c'
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
					        		var overtime_type='1';
					        		if(data.field.variable==0){
					        			if(data.field.holidayType==""){
					        				return layer.msg("请假类型不能为空", {icon: 2});
					        			}
					        			if(data.field.content=="")
					        				return layer.msg("请假原因不能为空", {icon: 2});
					        			if($('#inputapplytime2').find('p').length==0)
					        				return layer.msg("请假日期跟内容不能为空", {icon: 2});
					        			variable='holiday';
					        			holidayType=data.field.holidayType;
					        			content=data.field.content;
					        			leavetime=data.field.leavetime;
					        			leaveduration=data.field.leaveduration;
					        			//新增
										layui.each($('#inputapplytime2').find('span'),function(index,item){
											var val = $(item).attr('data-value').split(',');
											myArray.push({
												"date": val[0],
												"time": val[1],
											})
										})
					        		} 
					        		if(data.field.variable==1){
					        			if($('#inputapplytime3').find('p').length==0){
					        				return layer.msg("调休时长不能为空", {icon: 2});
					        			}
					        			variable='tradeDays'
					        			breaktime=data.field.breaktime;
					        			breakduration=data.field.breakduration;
					        			overtime_type=data.field.overtime_type;
					        			//新增
										layui.each($('#inputapplytime3').find('span'),function(index,item){
											var val = $(item).attr('data-value').split(',');
											myArray.push({
												"date": val[0],
												"time": val[1],
											})
										})
					        		}
					        		if(data.field.variable==2){
					        			if($('#inputapplytime').find('p').length==0){
					        				return layer.msg("补签日期不能为空", {icon: 2});
					        			}
					        			variable='addSignIn' 
					        			//新增
					        			layui.each($('#inputapplytime').find('span'),function(index,item){
											var val = $(item).attr('data-value');
											var time = $(item).attr('data-time');
											myArray.push({
												"date": val,
												"time": time,
											})
										})
					        		}
					        		if(data.field.variable==3){
					        			if($('#inputapplytime4').find('p').length==0){
					        				return layer.msg("加班日期跟内容不能为空", {icon: 2});
					        			}
					        			variable='applyOvertime'
					        			overtime=data.field.overtime;
					        			overtime_type=data.field.overtime_type;
					        			overduration=data.field.overduration;
										layui.each($('#inputapplytime4').find('span'),function(index,item){
											var val = $(item).attr('data-value').split(',');
											myArray.push({
												"date": val[0],
												"time": val[1],
											})
										})
					        		}
					        		//进行是否同时多选，单选判断
						        	var ids = menuTree.getVal('orgAndPersonDiv');
						        	if(ids.length>0 && data.field.userId!='')
						        		return layer.msg('不能同时单选员工与多选员工！',{icon:2});
						        	var userIds = '';
						        	layui.each(ids,function(index1,item1){
						        		if(item1!='' && item1!=0)
						        			userIds+=(item1+',');
					        		}); 
						        	var postData={
						        			userId:data.field.userId,
						        			userIds:userIds,
						        			writeTime:data.field.applytime,
						        			holidayType:holidayType,
						        			content:content,
						        			time: JSON.stringify(myArray),
						        	};
						        	postData[variable] = 'true';
						        	if(variable=='applyOvertime')	//如果是加班类型
						        		postData['overtimeType'] = overtime_type;
						        	mainJs.fAdd(postData);
						        	timeAll=""; 
								})
					        }
					        ,end:function(){ 
					        	document.getElementById("layuiadmin-form-admin").reset();
					        	layui.form.render();
					        	timeAll="";
					        	$('#orgAndPersonDiv').hide();
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
				    	//回显
				    	var allTime = JSON.parse(JSON.parse(JSON.stringify(data.time)));
				    	var html = '';
				    	for (var i = 0; i < allTime.length; i++) {	
				    		var val = allTime[i].date+','+allTime[i].time;
				    		html+='<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
						}
				    	$("#inputapplytime2").html(html); 
				    	$('#inputapplytime2').find('.layui-icon-close').on('click',function(){	//监听删除节点
							$(this).parent().parent().remove();
						})
			    	}
			    	if(data.tradeDays==true){
			    		$("#Break").css("display","block")
						$("#leave").css("display","none")
						$("#repair").css("display","none")
						$("#overtime").css("display","none")
			    		$("#tiao").get(0).checked=true;
			    		form.render('radio');
			    		$("#layuiadmin-form-admin").setForm({applytime:data.writeTime});
			    		//回显
				    	var allTime = JSON.parse(JSON.parse(JSON.stringify(data.time)));
				    	var html = '';
				    	for (var i = 0; i < allTime.length; i++) {	
				    		var val = allTime[i].date+','+allTime[i].time;
				    		html+='<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
						}
				    	$("#inputapplytime3").html(html); 
				    	$('#inputapplytime3').find('.layui-icon-close').on('click',function(){	
							$(this).parent().parent().remove();
						})
			    	}
			    	if(data.addSignIn==true){
			    		$("#repair").css("display","block")
						$("#Break").css("display","none")
						$("#leave").css("display","none")
						$("#overtime").css("display","none")
			    		$("#bu").get(0).checked=true;
			    		form.render('radio');
			    		$("#layuiadmin-form-admin").setForm({applytime:data.writeTime});
			    		//回显
			    		var time = JSON.parse(data.time);
			    		var html = '';
			    		layui.each(time,function(index,item){
			    			var val = item.date;
			    			html+='<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
			    		})
			    		$("#inputapplytime").html(html); 
				    	$('#inputapplytime').find('.layui-icon-close').on('click',function(){	
							$(this).parent().parent().remove();
						})
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
			    		//回显
				    	var allTime = JSON.parse(JSON.parse(JSON.stringify(data.time)));
				    	var html = '';
				    	for (var i = 0; i < allTime.length; i++) {	
				    		var val = allTime[i].date+','+allTime[i].time;
				    		html+='<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
						}
				    	$("#inputapplytime4").html(html); 
				    	$('#inputapplytime4').find('.layui-icon-close').on('click',function(){	
							$(this).parent().parent().remove();
						})
			    	}
			    	moren = true;
			    	layer.open({
				         type: 1
				        ,title: "修改" //不显示标题栏
				        ,closeBtn: false
				        ,area:['540px', '550px']
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
				        			if($('#inputapplytime2').find('p').length==0){
				        				return layer.msg("请假日期跟内容不能为空", {icon: 2});
				        			}
				        			if($('#selectOne').val()==''){
				        				return layer.msg("申请人不能为空", {icon: 2});
				        			}
				        			variable='holiday';
				        			holidayType=data.field.holidayType;
				        			content=data.field.content;
				        			leavetime=data.field.leavetime;
				        			leaveduration=data.field.leaveduration;
				        			//修改
				        			layui.each($('#inputapplytime2').find('span'),function(index,item){
										var val = $(item).attr('data-value').split(',');
										myArray.push({
											"date": val[0],
											"time": val[1],
										})
									})
				        		}
				        		if(data.field.variable==1){
				        			if($('#inputapplytime3').find('p').length==0){
				        				return layer.msg("调休时长跟日期不能为空", {icon: 2});
				        			}
				        			variable='tradeDays'
				        			//修改
									layui.each($('#inputapplytime3').find('span'),function(index,item){
										var val = $(item).attr('data-value').split(',');
										myArray.push({
											"date": val[0],
											"time": val[1],
										})
									})
				        		}
				        		if(data.field.variable==2){
				        			if($('#inputapplytime').find('p').length==0){
				        				return layer.msg("补签日期不能为空", {icon: 2});
				        			}
				        			variable='addSignIn' 
				        			layui.each($('#inputapplytime').find('span'),function(index,item){
										var val = $(item).attr('data-value');
										myArray.push({
											"date": val,
											"time": data.field.Sign,
										})
									})
				        		}
				        		if(data.field.variable==3){
				        			if($('#inputapplytime4').find('p').length==0){
				        				return layer.msg("加班日期跟内容不能为空", {icon: 2});
				        			}
				        			variable='applyOvertime'
				        			overtime=data.field.overtime;
				        			overduration=data.field.overduration;
				        			overtime_type=data.field.overtime_type;
									layui.each($('#inputapplytime4').find('span'),function(index,item){
										var val = $(item).attr('data-value').split(',');
										myArray.push({
											"date": val[0],
											"time": val[1],
										})
									})
				        		}
				        		var postData={
				        			id:id,
				        			userId:data.field.userId,
				        			writeTime:data.field.applytime,
				        			holidayType:holidayType,
				        			overtime_type:overtime_type,
				        			content:content,
				        			time:JSON.stringify(myArray)
				        		};
				        		postData[variable] = 'true';
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
							where: field,
							page:{curr:1},
						});
					}
				});
			});
			//封装ajax主方法
			var mainJs = {
				//新增							
			    fAdd : function(data){ 
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
								$('#inputapplytime').html('');
								$('#inputapplytime2').html('');
								$('#inputapplytime3').html('');
								$('#inputapplytime4').html('');
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
			};
		   (function(){			//渲染部门人员树形结构
				 $.ajax({
					url:'${ctx}/system/user/findUserOrg',
					success:function(r){
						if(0==r.code){
							var data = [{ id:0, name:'全选部门', children:[], }];
							for(var i=0;i<r.data.length;i++){	
								var item = r.data[i];
								if(isAttend && item.id != orgId)
									continue;
								if(item.users.length>0){
									var children = [];
									for(var j=0;j<item.users.length;j++){
										var item1 = item.users[j];
										children.push({
											id: item1.id,
											name:item1.userName,
										})
									}
									data[0].children.push({
										id:0,			
										name:item.name,
										children: children,
									})
								} 
							}
							menuTree.render({				
					    	  elem:'#orgAndPersonDiv',
					    	  data : data,
							}) 
						}
						else
							layer.msg('请求员工数据异常！',{icon:2});
					}
				})  
			})(); 
		}
	)
</script>
</body>
</html>