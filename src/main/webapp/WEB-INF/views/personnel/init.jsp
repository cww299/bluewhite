<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">

<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<script src="${ctx }/static/js/shujuhuixian/sjhx.js"></script> 
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>考勤初始设定</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<style>
#setTimeDiv{
	float: left;
    width: 50%;
}
#orgAndPersonDiv{
	float: right;
    width: 45%;
    height: 450px;
    padding: 10px;
    border: 1px solid #e2e2e2;
	overflow-y: scroll;
}
.showTimeDiv{
	height:200px;
	overflow-y: scroll;
}
.showTimeDiv p{
	height:28px;
}
.showTimeDiv span{
	height:24px;
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
						<td>人员:</td>
						<td><select id="userId" class="layui-input"  lay-search="true" name="userId"></select></td>
						<td>&nbsp;&nbsp;</td>
						<td>部门:</td>
						<td><select  id="orgNameId" class="layui-input" lay-search="true" name="orgNameId">
							<option value="">请选择</option></select></td>
						<td>&nbsp;&nbsp;</td>
						<td>在离职状态:</td>
						<td><select  id="quit" class="layui-input" lay-search="true" name="quit">
							<option value="0">在职</option><option value="1">离职</option></select></td>
						<td>&nbsp;&nbsp;</td>			
						<td>&nbsp;&nbsp;</td>
						<td><button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-search">
									<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i></button></td>
					</tr>
				</table>
			</div>
		</div>
		<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
	</div>
</div>
<!-- 新增弹窗 -->
<form action="" id="layuiadmin-form-admin" style="padding: 20px 30px 0 60px; display: none; text-align:">
	<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" name="id" id="usID" style="display:none;">
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">员工姓名</label>
			<div class="layui-input-inline">
				<select name="userId" lay-filter="lay_selecte" lay-verify="required" id="selectOne" lay-search="true">
					<option value="">请选择</option></select></div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">休息方式</label>
			<div class="layui-input-inline">
				<select name="restType" lay-filter="restType" id="restType"
					lay-verify="required" lay-search="true"><option value="1">周休一天</option>
					<option value="2">月休两天，其他周日算加班</option>
					<option value="3">全年无休</option></select></div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">出勤方式</label>
			<div class="layui-input-inline">
				<select name="workType" lay-filter="workType" id="workType" lay-verify="required" lay-search="true">
					<option value="0">请选择</option>
					<option value="1">无到岗要求</option>
					<option value="2">无打卡要求</option>
					<option value="3">按到岗小时计算</option></select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">早到加班</label>
			<div class="layui-input-block">
				<input type="checkbox" name="earthWork" value="true" id="kai" lay-text="是|否" lay-skin="switch">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">约定休息时间</label>
			<div class="layui-input-inline">
				<input type="text" name="applytime" id="applytime" placeholder="请输入申请时间" class="layui-input laydate-icon">
				&nbsp;&nbsp;
				<div>
					<div id="inputapplytime" class="layui-textarea" style="height:100px;overflow-y:scroll;"></div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">夏令时工作区间</label>
			<div class="layui-input-inline">
				<input type="text" name="workTimeSummer" id="workTimeSummer" lay-verify="required" placeholder="请输入夏令时工作区间" class="layui-input laydate-icon">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">冬令时工作区间</label>
			<div class="layui-input-inline">
				<input type="text" name="workTimeWinter" id="workTimeWinter" lay-verify="required" placeholder="请输入冬令时工作区间" class="layui-input laydate-icon">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">夏令出勤时长</label>
			<div class="layui-input-inline">
				<input type="text" name="turnWorkTimeSummer" id="turnWorkTimeSummer" lay-verify="required" placeholder="请输入夏令出勤时长 " class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">冬令出勤时长</label>
			<div class="layui-input-inline">
				<input type="text" name="turnWorkTimeWinter" id="turnWorkTimeWinter" lay-verify="required" placeholder="请输入冬令出勤时长 " class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">夏令时午休区间</label>
			<div class="layui-input-inline">
				<input type="text" name="restTimeSummer" id="restTimeSummer" lay-verify="required" placeholder="请输入夏令时午休区间" class="layui-input laydate-icon">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">冬令时午休区间</label>
			<div class="layui-input-inline">
				<input type="text" name="restTimeWinter" id="restTimeWinter" lay-verify="required" placeholder="请输入冬令时午休区间" class="layui-input laydate-icon">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">夏令午休时长</label>
			<div class="layui-input-inline">
				<input type="text" name="restSummer" id="restSummer" lay-verify="required" placeholder="请输入夏令午休时长 " class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">冬令午休时长</label>
			<div class="layui-input-inline">
				<input type="text" name="restWinter" id="restWinter" lay-verify="required" placeholder="请输入冬令午休时长" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">午休状态</label>
			<div class="layui-input-inline">
				<select name="restTimeWork" lay-filter="restTimeWork" id="restTimeWork" lay-verify="required" lay-search="true">
					<option value="1">默认休息</option>
					<option value="2">出勤</option>
					<option value="3">加班</option></select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">核算加班</label>
			<div class="layui-input-inline">
				<select name="overTimeType" id="overTimeType" lay-filter="overTimeType" lay-verify="required" lay-search="true">
					<option value="1">看加班申请</option>
					<option value="2">打卡核算</option></select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">加班后到岗</label>
			<div class="layui-input-inline">
				<select name="comeWork" id="comeWork" lay-filter="comeWork" lay-verify="required" lay-search="true">
					<option value="1">按点上班</option>
					<option value="2">延迟24:00到下班时</option>
					<option value="3">延迟24:30到下班时</option></select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">吃饭状态</label>
			<div class="layui-input-inline">
				<select name="eatType" lay-filter="eatType" id="eatType"  lay-search="true">
					<option value="0">请选择</option>
					<option value="1">早餐</option>
					<option value="2">晚餐</option>
					<option value="3">早餐or晚餐</option>
					<option value="4">晚夜宵</option>
					<option value="5">早晚夜宵</option>
					</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 130px;">倒班状态</label>
			<div class="layui-input-inline">
				<select name="fail" lay-filter="fail" id="fail"  lay-search="true">
					<option value="1">不倒班</option>
					<option value="2">倒班</option></select>
			</div>
		</div>
	</div>
</form>
<!-- 新增固定休息时间弹窗 -->
<form action="" id="layuiadmin-form-admin2" style="padding: 0px 10px; display: none; text-align:">
	<table id="fixedRestDay" lay-filter="fixedRestDay"></table>
</form>
<!-- 新增约定休息时间弹窗 -->
<div class="layui-form" id="agreedSetDiv" style="padding:10px;display:none;">
	<div id="setTimeDiv">
		<div class="layui-form-item">
			<label class="layui-form-label">日期</label>
			<div class="layui-input-inline">
				<input type="text" id="agreedSetTime" placeholder="请输入日期" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">显示</label>
			<div class="layui-input-inline">
				<div id="agreedShowTime" class="layui-textarea showTimeDiv"></div>
			</div>
		</div>
	</div>
	<div id="orgAndPersonDiv"></div>
</div>
<script type="text/html" id="toolbar">
	<div class="layui-btn-container layui-inline">
		<span class="layui-btn layui-btn-sm" lay-event="notice">新增</span>
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
		<span class="layui-btn layui-btn-sm" lay-event="set">固定休息时间</span>
		<span class="layui-btn layui-btn-sm" lay-event="agreedSet">约定休息时间</span>
	</div>
</script>
<!-- 固定休息日表格工具栏 -->
<script type="text/html" id="fixedRestDayToolbar">
	<div class="layui-btn-container layui-inline">
		<span class="layui-btn layui-btn-sm" lay-event="add">新增</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">修改</span>
	</div>
</script>
<!-- 新增、修改固定休息日模板 -->
<script type="text/html" id="addEditFixedTpl">
<div class="layui-form" style="padding:20px 0px;">
	<input type="text" name="id" value="{{d.id}}" style="display:none;">
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 130px;">设定月份</label> 
		<div class="layui-input-inline">
			<input type="text" id="setMonth" placeholder="设定月份" lay-verify="required" class="layui-input" value="{{ d.time }}" name="time">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 130px;">周休一天</label> 
		<div class="layui-input-inline">
			<input type="text" id="weekly" placeholder="请输入周休一天的设定时间" class="layui-input">
			&nbsp;&nbsp;
			<div id="weeklyRestDate" class="layui-textarea" style="height:150px;overflow-y:scroll;">
				{{#
					var html = '';
					layui.each(d.keyValue.split(','),function(index1,val){
						if(val=='')
							return;
						html += '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
					})
				}}
				{{ html }}
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 130px;">月休2天</label>
		<div class="layui-input-inline">
			<input type="text" id="month" placeholder="请输入月休2天的设定时间" class="layui-input">
			&nbsp;&nbsp;
			<div name="" id="monthRestDate" class="layui-textarea" style="height:150px;overflow-y:scroll;">
				{{#
					var html = '';
					layui.each(d.keyValueTwo.split(','),function(index1,val){
						if(val=='')
							return;
						html += '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
					})
				}}
				{{ html }}
			</div>
		</div>
	</div>
	<button type="button" lay-submit lay-filter="addEditBtn" id="addEditBtn" style="display:none;"></button>
</div>
</script>
<script type="text/html" id="barDemo">
	<a class="layui-btn layui-btn-trans layui-btn-xs"  lay-event="update">编辑</a>
</script>
<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug: 'tablePlug/tablePlug',
	menuTree : 'layui/myModules/menuTree'
}).define(
	['tablePlug', 'laydate', 'element','form','menuTree','laytpl'],
	function() {
		var $ = layui.jquery,
			layer = layui.layer,
			form = layui.form,
			table = layui.table,
			laytpl = layui.laytpl,
			laydate = layui.laydate,
			menuTree = layui.menuTree,
			tablePlug = layui.tablePlug,
			element = layui.element;
		//新增约定休息时间初始设定
		(function(){
			laydate.render({
				elem:'#agreedSetTime',
				type:'date',
			})
			$('#agreedShowTime').on('click',function(){
				var val = $('#agreedSetTime').val();
				if(val){
					$('#agreedSetTime').val('');
					var html = '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
					$("#agreedShowTime").append(html)
					$('#agreedShowTime').find('.layui-icon-close').on('click',function(){	//删除节点
						$(this).parent().parent().remove();
					})
				}
			})
			$.ajax({
				url:'${ctx}/system/user/findUserOrg',
				success:function(r){
					if(0==r.code){
						var data = [{
							id:0,
							name:'全选所有部门',
							children:[],
						}];
						layui.each(r.data,function(index,item){
							if(item.users.length>0){
								var children = [];
								layui.each(item.users,function(index1,item1){
									children.push({
										id:item1.id,
										name:item1.userName,
									});
								});
								data[0].children.push({
									id:0,			//排除部门id的影响
									name:item.name,
									children: children,
								});
							};
						});
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
		(function renderTime(arr){
			for(var key in arr){
				laydate.render({
					elem: '#'+arr[key],	
					range : '-',
					type: 'time',
				});
			}
		})(['workTimeSummer','workTimeWinter','restTimeSummer','restTimeWinter']);
		var timeAll='';
		laydate.render({
			elem: '#applytime',
			format: 'yyyy-MM-dd',
			done: function(val, date) {
				var html = '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
				$('#inputapplytime').append(html);
				$('#inputapplytime').find('.layui-icon-close').on('click',function(){	//删除节点
					$(this).parent().parent().remove();
				})
			}
		});
		$.ajax({

			url: '${ctx}/system/user/findUserList',
			data:{
				foreigns:0,
				isAdmin:'false'
			},
			type: "GET",
			async: false,
			success: function(result) {
				var html = '<option value="">请选择</option>';
				$(result.data).each(function(i, o) {
					html += '<option value=' + o.id + '>' + o.userName + '</option>'
				})
				$('#selectOne').html(html);
				$("#userId").append(html);
				form.render();
			},
		});
		var getdata = { type : "orgName", }
		$.ajax({
			url : "${ctx}/basedata/list",
			data : getdata,
			success : function(result) {
				var html="";
				$(result.data).each(function(k, j) {
					html += '<option value="'+j.id+'">' + j.name + '</option>'
				});
				
				$("#orgNameId").append(html);
				form.render();
			}
		});
		var	quit= $("#quit").val()
		table.render({
			elem: '#tableData',
			size: 'lg',
			height:'700px',
			where:{ quit:quit },
			url: '${ctx}/personnel/findAttendanceInit',
			request:{ pageName: 'page' ,limitName: 'size'  },
			page: { },
			toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
			cellMinWidth: 90,
			colFilterRecord: true,
			smartReloadModel: true,// 开启智能重载
			parseData: function(ret) {
				return { code: ret.code, msg: ret.message, count:ret.data.total, data: ret.data.rows }
			},
			cols: [[
				 { type: 'checkbox', align: 'center', fixed: 'left' },
				 { field: "", title: "员工姓名", align: 'center', fixed: 'left', templet: function(d){ return d.user.userName;} }, 
				 { field: "", title: "休息方式", align: 'center',
					templet: function(d){
						if(d.restType==1)
							return "周休一天";
						if(d.restType==2)
							return "月休两天,其他周日算加班";
						if(d.restType==3)
							return "全年无休";
					} }, 
				 { field: "", title: "出勤方式", align: 'center',
					templet: function(d){
						if(d.workType==0)
							return "";
						if(d.workType==1)
							return "无到岗要求";
						if(d.workType==2)
							return "无打卡要求";
						if(d.workType==3)
							return "按到岗小时计算"; } },
				{ field: "restDay", align: 'center', title: "约定休息日", },
				{ field: "workTimeSummer", align: 'center', width:170, title: "夏令时工作区间", },
				{ field: "workTimeWinter", width:170, align: 'center', title: "冬令时工作区间", },
				{ field: "turnWorkTimeSummer", width:150, align: 'center', title: "夏令出勤时长", },
				{ field: "turnWorkTimeWinter", width:150, align: 'center', title: "冬令出勤时长", },
				{ field: "restTimeSummer", width:150, align: 'center', title: "夏令时午休区间", },
				{ field: "restTimeWinter", width:150, align: 'center', title: "冬令时午休区间", },
				{ field: "restSummer", width:150, align: 'center', title: "夏令时午休时长", },
				{ field: "restWinter", width:150, align: 'center', title: "冬令时午休时长", },
				{ field: "restTimeWork", align: 'center', width:150, title: "午休状态",
					templet: function(d){
						if(d.restTimeWork==1)
							return "休息";
						if(d.restTimeWork==2)
							return "出勤";
						if(d.restTimeWork==3)
							return "加班"; } },
				{ field: "restTimeWork", align: 'center', width:150, title: "早到加班",
					templet: function(d){ return d.earthWork?'是':'否'; } },
				{ field: "overTimeType", align: 'center', title: "核算加班", width:150,
					templet: function(d){
						return d.overTimeType==1?"加班申请":"打卡核算"; } },
				{ field: "comeWork", align: 'center', width:150, title: "加班后到岗",
					templet: function(d){
						if(d.comeWork==1)
							return "按点上班";
						if(d.comeWork==2)
							return "第二天上班时间超过24:00往后推";
						if(d.comeWork==3)
							return "超过24:30后默认休息7.5小时"; } },
				{fixed:'right', title:'操作', align: 'center', toolbar: '#barDemo'}]
			],
		});
		//监听头工具栏事件
		table.on('toolbar(tableData)', function(obj) {
			var config = obj.config;
			var btnElem = $(this);
			var tableId = config.id;
			switch(obj.event) {
				case 'deleteSome':
					var checkedIds = tablePlug.tableCheck.getChecked(tableId);
					layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
						var postData = { ids: checkedIds, };
						var load = layer.load(1);
						$.ajax({
							url: "${ctx}/personnel/deleteAttendanceInit",
							data: postData,
							traditional: true,
							success: function(result) {
								var icon = 2;
								if(0 == result.code) {
									icon = 1;
									table.reload('tableData');
								}
								layer.msg(result.message, { icon: icon });
							}
						});
						layer.close(load);
					});
					break;
				case 'notice':
					var dicDiv=$('#layuiadmin-form-admin');
					document.getElementById("layuiadmin-form-admin").reset();
		        	layui.form.render();
					layer.open({
				         type: 1
				        ,title: "新增" //不显示标题栏
				        ,closeBtn: false
				        ,zindex:-1
				        ,area:['45%', '100%']
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
				        		var restDay = '';
			        			layui.each($('#agreedShowTime').find('span'),function(index,item){
									var val = $(item).attr('data-value');
									restDay += (val+',');
								})
								data.field.restDay = restDay;
				        	 	mainJs.fAdd(data.field); 
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
				case 'agreedSet':
					var setWin = layer.open({
						type:1,
						area:['40%','70%'],
						btn:['确定','取消'],
						content:$('#agreedSetDiv'),
						yes:function(){
							var userIds = '';
							var restDay = '';
							layui.each(menuTree.getVal('orgAndPersonDiv'),function(index1,item1){
								if(item1!=0)
									userIds+=(item1+',');
							})
							layui.each($('#agreedShowTime').find('span'),function(index,item){
								var val = $(item).attr('data-value');
								restDay += (val+',');
							})
							if(userIds==''){
								layer.msg('设定人员或时间不能为空',{icon:2});
								return;
							}
							var load = layer.load(1);
							$.ajax({
								url:'${ctx}/system/user/setUserRestDate',
								async:false,
								data:{
									userIds:userIds,
									restDay:restDay,
								},
								success:function(r){
									var icon = 2;
									if(0==r.code){
										menuTree.reload('orgAndPersonDiv',{ checked : [], });	//取消勾选状态
										$('#agreedShowTime').html('');							//清空时间选项
										layer.close(setWin);
										icon=1;
									}
									layer.msg(r.message,{icon:icon});
								}
							})
							layer.close(load);
						}
					})
					break;
				case 'set':
					table.render({
						elem: '#fixedRestDay',
						url: '${ctx}/personnel/findRestTypePage',
						page:true,
						parseData:function(r){
							layui.each(r.data.rows,function(index,item){
								var t = item.time.split('-');
								item.time = t[0]+'-'+t[1];
							})
							return { code: r.code, msg:r.message, count:r.data.total, data:r.data.rows };
						},
						toolbar:'#fixedRestDayToolbar',
						request:{ pageName: 'page' , limitName: 'size' },
						cols:[[
						       { align:'center', type:'checkbox', },
						       { align:'center', field:'time', title:'月份',},
						       { align:'center', field:'keyValue', title:'周休一天',},
						       { align:'center', field:'keyValueTwo', title:'月休两天',},
						       ]]
					})
					table.on('toolbar(fixedRestDay)',function(obj){
						var check = layui.table.checkStatus('fixedRestDay').data
						, tpl = addEditFixedTpl.innerHTML
						, data={	time:'',keyValue: "",keyValueTwo:'',id:'', }
						, html = '';
						switch(obj.event){
						case 'add':  break;
						case 'edit': 
							if(!check.length>0)
								return layer.msg('请选择相关信息',{icon:2});
							data = check[0];
							break;
						}
						laytpl(tpl).render(data,function(h){	
							html=h;
						});
						layer.open({
							type:1,
							title:'固定休息日',
							area:['30%','80%'],
							content: html,
							btn: ['确定','取消'],
							btnAlign: 'c',
							success:function(){
								laydate.render({
									elem: '#setMonth',
									type: 'month',
								});
								laydate.render({
									elem: '#weekly',
									format: 'yyyy-MM-dd',
									done: function(val, date) {
										var html = '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
										$('#weeklyRestDate').append(html);
										$('#weeklyRestDate').find('.layui-icon-close').on('click',function(){	//删除节点
											$(this).parent().parent().remove();
										})
									}
								});
								laydate.render({
									elem: '#month',
									format: 'yyyy-MM-dd',
									done: function(val, date) {
										var html = '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
										$('#monthRestDate').append(html);
										$('#monthRestDate').find('.layui-icon-close').on('click',function(){	//删除节点
											$(this).parent().parent().remove();
										})
									}
								});
								$('#weeklyRestDate').find('.layui-icon-close').on('click',function(){	//删除节点
									$(this).parent().parent().remove();
								})
								$('#monthRestDate').find('.layui-icon-close').on('click',function(){	//删除节点
									$(this).parent().parent().remove();
								})
								form.on('submit(addEditBtn)', function(data) {
					        		var data=data.field
					        		data.time+='-01 00:00:00';
					        		data.keyValue='';
									data.keyValueTwo='';
					        		layui.each($('#weeklyRestDate').find('span'),function(index,item){
										var val = $(item).attr('data-value');
										data.keyValue += (val+',');
									})
									layui.each($('#monthRestDate').find('span'),function(index,item){
										var val = $(item).attr('data-value');
										data.keyValueTwo += (val+',');
									})
									var url = '${ctx}/personnel/addRestType';
									if(data.id)
										url = "${ctx}/personnel/updateRestType";
					        		$.ajax({
										url: url,
										data: data,
										type: "Post",
										success: function(r) {
											var icon = r.code==0?1:2;
											layer.msg(r.message, { icon: icon });
											table.reload('fixedRestDay');
										},
									});
								}) 
							},
							yes:function(){
								$('#addEditBtn').click();
							},
						})
					})
					var dicDiv=$('#layuiadmin-form-admin2');
					layer.open({
				         type: 1
				        ,title: "设定休息时间" //不显示标题栏
				        ,area:['70%', '70%']
				        ,shade: 0.5
				        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
				        ,content:dicDiv
				      });
					break;
			}
		});
		
		//监听单元格编辑
		table.on('tool(tableData)', function(obj) {
			document.getElementById("layuiadmin-form-admin").reset();
        	layui.form.render();
			var data = obj.data;
			var value = obj.value
			var id=data.id;
			var val=obj.field
			$("#usID").val(id)
		    if(obj.event === 'update'){
		    	var dicDiv=$('#layuiadmin-form-admin');
		    	$('#selectOne').val(data.user.id);
		    	$('#restType').val(data.restType);
		    	$('#workType').val(data.workType);
		    	$('#restTimeWork').val(data.restTimeWork);
				$('#overTimeType').val(data.overTimeType);
				$('#comeWork').val(data.comeWork);
				if(data.earthWork){
					$("#kai").attr("checked","checked");
		        }else{
		    	   	$("#kai").attr("checked",false);
		        }
				$('#eatType').val(data.eatType);
				$('#fail').val(data.fail);
				$("#restTimeSummer").val(data.restTimeSummer)
		    	$("#layuiadmin-form-admin").setForm({
		    		restDay: data.restDay,
		    		workTimeSummer: data.workTimeSummer,
		    		workTimeWinter: data.workTimeWinter,
		    		turnWorkTimeSummer: data.turnWorkTimeSummer,
		    		turnWorkTimeWinter: data.turnWorkTimeWinter,
		    		restTimeSummer: data.restTimeSummer,
		    		restTimeWinter: data.restTimeWinter,
		    		restSummer: data.restSummer,
		    		restWinter: data.restWinter
		    	});
				form.render();
		    	layer.open({
			         type: 1
			        ,title: "修改" //不显示标题栏
			        ,area:['30%', '100%']
			        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
			        ,btn: ['确定', '取消']
			        ,btnAlign: 'c'
			        ,content:dicDiv
			        ,success : function(layero, index) {
			        	layero.addClass('layui-form');
						layero.find('.layui-layer-btn0').attr({
							'lay-filter' : 'addRole',
							'lay-submit' : ''
						})
						$('#inputapplytime').html('');
						layui.each(data.restDay.split(','),function(index1,val){
							if(val=='')
								return;
							var html = '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
							$('#inputapplytime').append(html);
						})
						$('#inputapplytime').find('.layui-icon-close').on('click',function(){	//删除节点
							$(this).parent().parent().remove();
						})
			        }
			        ,yes: function(index, layero){
			        	form.on('submit(addRole)', function(data) {
			        		var restDay = '';
		        			layui.each($('#agreedShowTime').find('span'),function(index,item){
								var val = $(item).attr('data-value');
								restDay += (val+',');
							})
							data.field.restDay = restDay;
			        	 	mainJs.fAdd(data.field); 
						})
			        }
			        ,end:function(){
			        	document.getElementById("layuiadmin-form-admin").reset();
			        	layui.form.render();
			        	timeAll=""
					  } 
			      });
		    }
		});
		form.on('submit(LAY-role-search)', function(data) {
			var field = data.field;
			$.ajax({
				url: "${ctx}/personnel/findAttendanceInit",
				data: field,
				success: function(result) {
					table.reload('tableData', { where: field });
				}
			});
		});
		//封装ajax主方法
		var mainJs = {
			//新增
		    fAdd : function(data){
		    	var load = layer.load(1);
		    	$.ajax({
					url: "${ctx}/personnel/addAttendanceInit",
					data: data,
					type: "Post",
					success: function(result) {
						var icon = 2;
						if(0 == result.code) {
							icon = 1;
							table.reload('tableData');
							if(data.id==null){
								document.getElementById("layuiadmin-form-admin").reset();
					        	layui.form.render();
							}
						} 
						layer.msg(result.message, { icon: 1 });
					},
				});
				layer.close(load);
		    }
		}
	}
)
</script>
</body>
</html>