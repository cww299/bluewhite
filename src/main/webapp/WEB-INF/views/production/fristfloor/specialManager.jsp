<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特急汇总</title>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td><input type="text" placeholder="选择日期" class="layui-input" id="dayTime">
					<input type="text" placeholder="选择月份" class="layui-input" id="monthTime" style="display:none;">
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
				<td style="width:85%; text-align:right;">
					<input type="radio" name="viewTypeUser" lay-filter="user" value="1" title="按人员" checked>
      				<input type="radio" name="viewTypeUser" lay-filter="user" value="2" title="按分组">
      				<input type="radio" name="viewTypeDate" lay-filter="time" value="1" title="按日期" checked>
      				<input type="radio" name="viewTypeDate" lay-filter="time" value="2" title="按月份">
				</td>
			</tr>
		</table>
		<table class="layui-form" id="specialTable" lay-filter="specialTable"></table>
	</div>
</div>
</body>
<script>
var TYPE = 1;			//常量
layui.use(['jquery','laydate','table'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl;
		
		var LOAD;
		laydate.render({
			elem: '#dayTime',
			type: 'date', 
			range : '~'
		})
		laydate.render({
			elem: '#monthTime',
			type: 'month', 
		})
		table.render({
			elem:'#specialTable',
			loading:true,
			data:[],
			totalRow:true,
			toolbar: true,
			size:'sm',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data,  msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', title:'日期',   field:'date',	},
			       {align:'center', title:'分组/姓名', 	field:'name', 	},
			       {align:'center', title:'总工时',   field:'sumWorkTime',  totalRow:true,},
			       {align:'center', title:'工种',   field:'kindWork',	},
			       {align:'center', title:'b工资',   field:'bPay',	totalRow:true,},
			       ]],
			done:function(){
				layer.close(LOAD);
			}
		}) 
		
		form.on('radio(time)', function(data){			//单选按钮切换
			switch(data.value){
			case '1':   $('#dayTime').show(); $('#monthTime').hide(); break;
			case '2':   $('#dayTime').hide(); $('#monthTime').show(); break;
			}
		});  
		
		form.on('submit(search)',function(obj){
			var data = obj.field;
			var msg="";
			if(data.viewTypeDate ==1){
				if($('#dayTime').val()==""){
					layer.msg('查询时间不能为空',{icon:2});
					return;
				}
				var time = $('#dayTime').val().split("~");
				data.orderTimeBegin = time[0]+"00:00:00";
				data.orderTimeEnd = time[1]+" 23:59:59";
			}else{
				if($('#monthTime').val()==""){
					layer.msg('查询时间不能为空',{icon:2});
					return;
				}
				var time = $('#monthTime').val();
				data.orderTimeBegin = time+"-01 00:00:00";
				data.orderTimeEnd = "";
			}
			LOAD = layer.load(1);
			table.reload('specialTable',{
				url:'${ctx}/production/sumTemporarily?type='+TYPE,
				where:data
			}) 
		}) 
	}//end  function
)//end use
</script>

</html>