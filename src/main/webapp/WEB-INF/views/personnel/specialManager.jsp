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
				<td><select name="type">
						<option value="1">一楼质检</option>
						<option value="2">一楼包装</option>
						<option value="3">二楼针工</option>
					</select>
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
				<td style="width:70%; text-align:right;">
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
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug: 'tablePlug/tablePlug',
}).define(['jquery','laydate','table','tablePlug'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, tablePlug = layui.tablePlug 
		, laytpl = layui.laytpl;
		
		var LOAD;      //遮罩变量
		laydate.render({
			elem: '#dayTime',
			type: 'date', 
			range : '~'
		})
		laydate.render({
			elem: '#monthTime',
			type: 'month', 
		})
		tablePlug.smartReload.enable(true); 
		table.render({
			elem:'#specialTable',
			data:[],
			toolbar: true,
			totalRow:true,
			loading:false,
			size:'sm',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data,  msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', title:'日期',   field:'date',	totalRowText:'合计',},
			       {align:'center', title:'分组/姓名', 	field:'name',filter:true,	 	},
			       {align:'center', title:'总工时',   field:'sumWorkTime',totalRow:true,  },
			       {align:'center', title:'工种',   field:'kindWork', filter:true,	},
			       {align:'center', title:'是否本厂',   field:'foreigns',filter:true,	},
			       {align:'center', title:'小时单价',   field:'price',	edit:true,},
			       {align:'center', title:'总工资',   field:'sumPrice',	totalRow:true,},
			       ]],
			done:function(){
				layer.close(LOAD);
			}
		}) 
		var isUser = false;
		table.on('edit(specialTable)',function(obj){
			var val = obj.value,
			    data = obj.data;
			if(isNaN(val)){
				layer.msg('小时单价只能为数字',{icon:2,offset:'200px'})
			}else if(val<0){
				layer.msg('小时单价不能为负数',{icon:2,offset:'200px'})
			}else if(!isUser){
				layer.msg('无效修改！',{icon:2,offset:'200px'})
			}else{
				var load = layer.load(1);
				$.ajax({
					url:'${ctx}/system/user/updateForeigns',
					type:'post',
					async:false,
					data: { id: data.id, price: parseFloat(val)},
					success:function(r){
						var icon = 2;
						if(r.code == 0)
							icon = 1;
						layer.msg(r.message,{icon:icon,offset:'200px'});
					}
				})
				layer.close(load);
			}
			table.reload('specialTable');
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
			if(data.viewTypeUser == 1)
				isUser = true;
			else 
				isUser = false;
			LOAD = layer.load(1);
			table.reload('specialTable',{
				url:'${ctx}/production/sumTemporarily?type='+obj.field.type,
				where:data
			}) 
		}) 
	}//end  function
)//end use
</script>

</html>
