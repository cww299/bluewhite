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
					<td>姓名:</td>
					<td style="width:120px;"><select id="userId" name="userId" lay-search><option value="">请选择</option></select></td>
					<td>&nbsp;&nbsp;</td>
					<td>编号:</td>
					<td><input type="text" id="number" name="number" class="layui-input" /></td>
					<td>&nbsp;&nbsp;</td>
					<shiro:lacksRole name="attendanceStatistician">
						<td>部门:</td>
						<td style="width:120px;"><select name="orgNameId" id="department" lay-search><option value="">请选择</option></select></td>
						<td>&nbsp;&nbsp;</td>
					</shiro:lacksRole>
					<td>时间:</td>
					<td><input id="startTime" placeholder="请输入查找时间" class="layui-input" ></td>
					<td>&nbsp;&nbsp;</td>
					<td>打卡机:</td>
					<td style="width:120px;">
						<select name="sourceMachine" id="addressSelect" class="layui-inline">
							<option value="">请选择</option>
							<option value="192.168.1.204">三楼打卡机</option>
							<!-- <option value="192.168.1.250">二楼打卡机</option> -->
							<option value="192.168.1.205">一楼打卡机</option>
							<option value="192.168.7.123">面辅料打卡机</option>
							<option value="192.168.6.73">成品打卡机</option>
							<option value="192.168.14.201">11号打卡机</option></select></td>
					<td>&nbsp;&nbsp;</td>
					<td>状态:</td>
					<td style="width:120px;">
										<select name="inOutMode"><option value="">请选择</option>
										<option value="1">正常签到</option>
										<option value="2">补签</option></select></td>
					<td>&nbsp;&nbsp;</td>
					<td><button type="button" class="layui-btn" lay-submit lay-filter='search'>查找</button></td>
					<td>&nbsp;&nbsp;</td> 
					<shiro:lacksRole name="attendanceStatistician">
						<td><button type="button" id="synchronization2" class="layui-btn layui-btn-danger">考勤重置</button></td>
						<td>&nbsp;&nbsp;</td>
						<td><button type="button" id="export" class="layui-btn layui-btn-normal">导出</button></td>
					</shiro:lacksRole>
				</tr>
			</table>
			<table id="tableData" lay-filter="tableData"></table>
		</div>
	</div>
<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug: 'tablePlug/tablePlug',
}).use(['tablePlug','table','jquery','form','laydate','layer'],function(){
	var $ = layui.jquery
	, table = layui.table
	, form = layui.form
	, laydate =layui.laydate
	, tablePlug = layui.tablePlug
	, layer = layui.layer;
	laydate.render({
		elem:'#startTime',
		range:'~',
	})
	
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
	})();
	
	table.render({
		elem: '#tableData',
		url:'${ctx}/personnel/findPageAttendance'+(isAttend?'?orgNameId='+orgId:''),
		request:{pageName: 'page' ,	 limitName: 'size' 	},
		toolbar:true,
		parseData : function(ret) { 
			if(ret.code==0){
				for(var key in ret.data.rows){
					var d = ret.data.rows[key];
					d.name = d.user?d.user.userName:'---';
					d.userName = d.userName || '---';
				}
			}
			return { code : ret.code, msg : ret.message, data : ret.data.rows, count:ret.data.total,} 
		},
		page:{},
		limits:[15,50,100],
		limit:15,
		cols: [[
		        {align:'center',field:'number',title:'员工编号'},
		        {align:'center',field:'name',title:'员工姓名',},
		        {align:'center',field:'userName',title:'考勤姓名',}, 
		        {align:'center',field:'time',title:'签到时间'},
		        {align:'center',field:'mode',title:'验证方式',templet: getMode()},
		        {align:'center',field:'status',title:'签到状态',templet: getStatu(),filter:true,},
		        {align:'center',field:'sourceMachine',title:'打卡地点',templet: getMachine(),filter:true,},
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
		var address = $('#addressSelect').val();
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
					address: address,
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
		var userId = $('#userId').val();
		var orgNameId = $("#department").val();
		var number = $('#number').val();
		var orderTimeBegin = '';
		var orderTimeEnd = '';
		var val = $('#startTime').val();
		var address = $('#addressSelect').val();
		isAttend && (orgNameId = orgId);
		if(val!=''){
			orderTimeBegin = val.split('~')[0]+'00:00:00';
			orderTimeEnd = val.split('~')[1]+' 23:59:59';
		}
		location.href = "${ctx}/excel/importExcel/personnel/DownAttendanceSign?userId=" + (userId || "") + "&orgNameId=" + orgNameId + "&orderTimeBegin=" + orderTimeBegin
				+ "&orderTimeEnd=" + orderTimeEnd + "" +"&number="+number+"&address="+address;
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
	function getMachine(){
		return function(d){
			var machine = '---';
			switch(d.sourceMachine){
			case 'THREE_FLOOR': machine='三楼打卡机'; break;
			case 'TWO_FLOOR': machine='二楼打卡机'; break;
			case 'ONE_FLOOR': machine='一楼打卡机'; break;
			case 'EIGHT_WAREHOUSE': machine='面辅料打卡机'; break;
			case 'NEW_IGHT_WAREHOUSE': machine='成品打卡机'; break;
			case 'ELEVEN_WAREHOUSE': machine='11号打卡机'; break;
			}
			return machine;
		}
	}
	function getStatu(){
		return function(d){
			return d.inOutMode==2?"补签":"正常签到";
		}
	};
})
</script>
</body>
</html>