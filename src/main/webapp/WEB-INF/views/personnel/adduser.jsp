<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<title>打卡记录</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
</head>

<body>
	<div class="layui-card">
		<div class="layui-card-body">
			<table class="layui-form" style="margin:10px 0px;">
				<tr>
					<td>&nbsp;&nbsp;</td>
					<td>员工姓名:</td>
					<td><select id="userId" name="userId" lay-search><option value="">请选择</option></select></td>
					<td>&nbsp;&nbsp;</td>
					<td>员工编号:</td>
					<td><input type="text" id="number" name="number" class="layui-input" /></td>
					<td>&nbsp;&nbsp;</td>
					<shiro:lacksRole name="attendanceStatistician">
						<td>员工部门:</td>
						<td><select name="orgNameId" id="department"><option value="">请选择</option></select></td>
						<td>&nbsp;&nbsp;</td>
					</shiro:lacksRole>
					<td>开始时间:</td>
					<td><input id="startTime" placeholder="请输入查找时间" class="layui-input" ></td>
					<td>&nbsp;&nbsp;</td>
					<td><button type="button" class="layui-btn" lay-submit lay-filter='search'>查找</button></td>
					<td>&nbsp;&nbsp;</td>
					<td><button type="button" id="export" class="layui-btn">导出签到</button></td>
					<td style="width:15%;"></td> 
					<shiro:lacksRole name="attendanceStatistician">
						<td><button type="button" id="synchronization2" class="layui-btn layui-btn-danger">考勤重置</button></td>
					</shiro:lacksRole>
				</tr>
			</table>
			<table id="tableData" lay-filter="tableData"></table>
		</div>
	</div>
<script>
layui.use(['table','jquery','form','laydate','layer'],function(){
	var $ = layui.jquery
	, table = layui.table
	, form = layui.form
	, laydate =layui.laydate
	, layer = layui.layer;
	laydate.render({
		elem:'#startTime',
		range:'~',
	})
	var orgId = '';
	;!(function(){
		$.ajax({
			url:'${ctx}/getCurrentUser',		
			async:false,
			success:function(r){
				if(0==r.code)
					orgId = r.data.orgNameId || '';		//获取当前登录用户的部门id
			}
		})
	})();
	$.ajax({
		url: '${ctx}/system/user/findUserList?foreigns=0&isAdmin=false&orgNameIds='+orgId,
		async: false,
		success: function(result) {
			var htmls = '';
			$(result.data).each(function(i, o) {
				htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
			})
			$('#userId').append(htmls);
			form.render();
		},
	});
	
	
	var isAttend = true,orgId = '';	  //是否是考情记录员,和所在部门
	;!(function(){
		if(document.getElementById('department')==null){
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
			;!(function(){
				$.ajax({
					url:"${ctx}/basedata/list?type=orgName",
					success: function (result) {
						var html='';
						$(result.data).each(function(k,j){
							html +='<option value="'+j.id+'">'+j.name+'</option>'
						});
						$("#department").append(html);
						form.render('select');
					}
				});
			})();
		}
	})();
	
	table.render({
		elem: '#tableData',
		url:'${ctx}/personnel/findPageAttendance'+(isAttend?'?orgNameId='+orgId:''),
		request:{pageName: 'page' ,	 limitName: 'size' 	},
		parseData : function(ret) { return { code : ret.code, msg : ret.message, data : ret.data.rows, count:ret.data.total,} },
		page:{},
		limits:[15,50,100],
		limit:15,
		cols: [[
		        {align:'center',field:'number',title:'员工编号'},
		        {align:'center',field:'name',title:'员工姓名',templet:function(d){ return (d.user == null ? "" : d.user.userName)}},
		        {align:'center',field:'time',title:'签到时间'},
		        {align:'center',field:'mode',title:'验证方式',templet: getMode()},
		        {align:'center',field:'status',title:'签到状态',templet: getStatu()},
		        ]]
	})
	form.on('submit(search)',function(obj){
		var val = $('#startTime').val();
		var start = '',end = '';
		if(val!=''){
			val = val.split('~');
			start = val[0]+'00:00:00';
			end = val[1]+' 23:59:59';
		}
		obj.field.orderTimeBegin = start;
		obj.field.orderTimeEnd = end;
		table.reload('tableData',{
			where:obj.field,
			page:{curr:1},
		})
	})
	$('#synchronization2').on('click',function(){
		var val = $('#startTime').val();
		if(val=='')
			return layer.msg('请选择时间范围',{icon:2});
		layer.confirm('是否确认重置考勤？',function(){
			var load = layer.load(1,{shade:[0.1, 'black']});
		 	$.ajax({
				url: "${ctx}/personnel/restAttendance",
				data: {
					startTime:val.split('~')[0]+'00:00:00',
					endTime: val.split('~')[1]+' 23:59:59',
					userId: $('#userId').val(),
				},
				success: function(result){
					var icon = 2;
					0==result.code && (icon = 1);
					layer.msg(result.message, {icon: icon});
					layer.close(load);
				},
				error: function(){
					layer.close(load)
				}
			}); 
		})
	}); 
	$('#export').on('click',function() {
		var userName = $('#userName').val();
		var orgNameId = $("#department").val();
		var number = $('#number').val();
		var orderTimeBegin = '';
		var orderTimeEnd = '';
		var val = $('#startTime').val();
		isAttend && (orgNameId = orgId);
		if(val!=''){
			orderTimeBegin = val.split('~')[0]+'00:00:00';
			orderTimeEnd = val.split('~')[1]+' 23:59:59';
		}
		location.href = "${ctx}/excel/importExcel/personnel/DownAttendanceSign?userName=" + userName + "&orgNameId=" + orgNameId + "&orderTimeBegin=" + orderTimeBegin
				+ "&orderTimeEnd=" + orderTimeEnd + "" +"&number="+number;
	});
	function getMode(){
		return function(d){
			var mode = "面部验证";
			if(!d.verifyMode)
				mode = "";
			else
				switch(d.verifyMode){
				case 0: mode = '密码验证'; break;
				case 1: mode = '指纹识别'; break;
				case 2: mode = '打卡验证'; break;
				}
			return mode;
		}
	}
	function getStatu(){
		return function(d){
			var statu = "补签";
			if(!d.inOutMode)
				statu = "暂无";
			else
				switch(d.inOutMode){
				case 0: statu = '上班签到'; break;
				case 1: statu = '下班签到'; break;
				}
			return statu;
		}
	};
})
</script>
</body>
</html>