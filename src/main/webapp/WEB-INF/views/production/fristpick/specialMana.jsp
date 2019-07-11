<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特急管理</title>
<style>
#showTimeDiv{
	height:220px;
}
#showTimeDiv p{
	height:28px;
}
#showTimeDiv span{
	height:24px;
}
</style>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td><input type="text" placeholder="选择日期" class="layui-input" id="dayTime">
				 	<input type="hidden" name="viewTypeUser" value="1">
				 	<input type="hidden" name="viewTypeDate" value="1">
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="specialTable" lay-filter="specialTable"></table>
	</div>
</div>
<!-- 时间编辑弹窗 -->
<div style="display:none;padding:20px;" id="timeEditDiv">
	<div class="layui-form-item">
		<label class="layui-form-label">选择时间：</label>
		<div class="layui-input-block">
			<input type="text" palceholder="请输入" class="layui-input" id="timeInput">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">已选择：</label>
		<div class="layui-input-block">
			<div class="layui-textarea" id="showTimeDiv"></div>
		</div>
	</div>
</div>
</body>
<!-- 时间模板解析 -->
<script id="timeTpl" type="text/html">
	{{# layui.each(d.workTimeSlice.split(','),function(index,item){   }}
				<span class="layui-badge layui-bg-{{ item ? 'green':'' }}">{{ item || '无数据' }}</span>
	{{# })   }}  
</script>
<script>
var TYPE = 2;
layui.use(['table','laydate','layer','jquery'],
	function(){
		var LOAD;
		var $ = layui.jquery,
		    table = layui.table,
		    layer = layui.layer,
		    form = layui.form,
		    laydate = layui.laydate;
		laydate.render({
			elem: '#dayTime',
			type: 'date', 
			range : '~'
		})
		laydate.render({
			elem: '#timeInput'
		    ,type: 'time'
		    ,range : '~'
		});
		$('#showTimeDiv').on('click',function(){
			createNew();
		})
		function createNew(){
			var val = $('#timeInput').val();
			if(val!=''){
				var html = '<p><span class="layui-badge layui-bg-green" data-value="'+val+'">'+val+'<i class="layui-icon layui-icon-close"></i></span></p>';
				$('#showTimeDiv').append(html);			//新增节点
				$('#showTimeDiv').find('.layui-icon-close').on('click',function(){	//删除节点
					$(this).parent().parent().remove();
				})
				$('#timeInput').val('');			//清空内容
			}
		}
		table.render({
			elem:'#specialTable',
			data:[],
			totalRow:true,
			toolbar: true,
			loading:false,
			size:'sm',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data,  msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', title:'日期',   field:'temporarilyDate',	totalRowText:'合计', width:'10%',sort:true,},
			       {align:'center', title:'分组', 	field:'user', width:'10%',	templet:function(d){ return d.group.name; }},
			       {align:'center', title:'姓名', 	field:'user', width:'10%',	templet:function(d){ return d.user.userName; }},
			       {align:'center', title:'总工时',   field:'workTime',  totalRow:true, width:'10%', edit:true,},
			       {align:'center', title:'工时',   field:'workTimeSlice', templet:'#timeTpl'	},
			       ]],
			done:function(){
				layer.close(LOAD);
				var tableView = this.elem.next();
				var tableElem = this.elem.next('.layui-table-view');
				layui.each(tableView.find('td[data-field="workTimeSlice"]'), function(index, tdElem) {
					tdElem.onclick = function(event) {
						var addEditTimeWin = layer.open({
							type:1,
							title:'编辑时间段',
							content:$('#timeEditDiv'),
							area:['30%','50%'],
							offset:'180px',
							btn:['确定','取消'],
							success:function(){			//成功时进行初始值赋值
								var index = $(tdElem).parent().attr('data-index');		//获取当前行索引
								var workTimeSlice = layui.table.cache['specialTable'][index]['workTimeSlice'].split(',');
								var html = '';
								layui.each(workTimeSlice,function(index3,item3){
									if(item3)
										html += '<p><span class="layui-badge layui-bg-green" data-value="'+item3+'">'+item3+'<i class="layui-icon layui-icon-close"></i></span></p>';
								})
								$('#showTimeDiv').html(html);
								$('#showTimeDiv').find('.layui-icon-close').on('click',function(){	
									$(this).parent().parent().remove();
								})
							},
							yes:function(){
								var arr = [];
								layui.each($('#showTimeDiv').find('span'),function(index2,item2){
									var val = $(item2).attr('data-value');
									val && arr.push(val)
								})
								var index = $(tdElem).parent().attr('data-index');		
								var id = layui.table.cache['specialTable'][index]['id'];
								var data = {
										id: id,
										workTimeSlice: arr.join(',')
								}
								if(updateData(data)){
									table.reload('specialTable');
									layer.close(addEditTimeWin);
								} 
							},
							end:function(){
								$('#showTimeDiv').html('');  //销毁时清空内容
								$('#timeInput').val('');
							}
						})
					};
				})
			}
		}) 
		table.on('edit(specialTable)',function(obj){
			var val = obj.value,
			    data = obj.data;
			if(isNaN(val)){
				layer.msg('总工时只能为数字',{icon:2,offset:'200px'})
			}else if(val<0){
				layer.msg('工时不能为负数',{icon:2,offset:'200px'})
			}else{
				var data = { id: data.id, workTime: parseFloat(val)};
				updateData(data);
			}
			table.reload('specialTable');
		})
		form.on('submit(search)',function(obj){
			var data = obj.field;
			var msg="";
			if($('#dayTime').val()==""){
				layer.msg('查询时间不能为空',{icon:2});
				return;
			}
			var time = $('#dayTime').val().split("~");
			data.orderTimeBegin = time[0]+"00:00:00";
			data.orderTimeEnd = time[1]+" 23:59:59";
			LOAD = layer.load(1);
			table.reload('specialTable',{
				url: '${ctx}/production/getTemporarilyList?type='+TYPE,
				where:data
			}) 
		}) 
		function updateData(data){
			var load = layer.load(1);
			var result = false;
			$.ajax({
				url: '${ctx}/production/updateTemporarily',
				type:'post',
				async:false,
				data: data,
				success:function(r){
					var icon = 2;
					if(r.code == 0){
						icon = 1;
						result = true;
					}
					layer.msg(r.message,{icon:icon,offset:'200px'});
				}
			})
			layer.close(load);
			return result;
		}
});
</script>

</html>
