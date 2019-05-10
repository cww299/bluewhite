<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>入库单</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
</head>
<body>

<div class="layui-card" style="height:800px;">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td><input type="text" class="layui-input" name="" id="date"></td>
				<td><select name=""><option value="">按单据编号</option></select>
				<td><input type="text" class="layui-input" name=""></td>
				<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</span></td>
			</tr>
		</table>
		<table class="layui-table" id="warehouseTable" lay-filter="warehouseTable"></table>
	</div>
</div>


<!-- 入库单表格工具栏 -->
<script type="text/html" id="warehouseTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改</span>
	<span lay-event="refresh"  class="layui-btn layui-btn-sm" >刷新</span>
</div>
</script>

</body>

<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).define(
	['tablePlug'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laytpl = layui.laytp
		, tablePlug = layui.tablePlug;
		
		
		form.render();
		
		table.render({
			elem:'#warehouseTable',
			url:'',
			toolbar:'#warehouseTableToolbar',
			loading:true,
			page:true,
			request:{
				pageName:'page',
				limitName:'size'
			},
			parseData:function(ret){
				return {
					data:ret.data.rows,
					count:ret.data.total,
					msg:ret.message,
					code:ret.code
				}
			},
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'单据编号',   field:'',		width:'',},
			       {align:'center', title:'入库类型',   field:'',	width:'',},
			       {align:'center', title:'日期',   field:'',		width:'',},
			       {align:'center', title:'入库仓库',   field:'',		width:'',},
			       {align:'center', title:'总数量', field:''},
			       {align:'center', title:'经手人', field:''},
			       {align:'center', title:'备注', field:''},
			       ]]
		})
		
		table.on('toolbar(warehouseTable)',function(obj){
			console.log(123)
			switch(obj.event){
			case 'add':		addEdit('add');		break;
			case 'update':	addEdit('edit'); 	break;
			case 'delete':	deletes();			break;
			case 'refresh':	refresh();			break;
			}
		})
		
		function addEdit(type){
			layer.msg('新增修改');return
			var data={
					
			},
			title='',
			html='',
			choosed=layui.table.checkStatus('customTable').data,
			tpl=addEditTpl.innerHTML;
			if(type=='edit'){
				if(choosed.length>1){
					layer.msg('无法同时编辑多条信息',{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg("请选择编辑",{icon:2});
					return;
				}
				title="";
				data=choosed[0];
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			var addEditWin=layer.open({
				type:1,
				title:title,
				area:['40%','80%'],
				content:html
			})
			form.render();
			form.on('submit(sure)',function(obj){
				var load = layer.load(1);
				$.ajax({
					url:"r",
					type:"post",
					data:obj.field,
					success:function(result){
						if(0==result.code){
							table.reload('customTable');
							layer.msg(result.message,{icon:1});
							layer.close(addEditWin);
						}else{
							layer.msg(result.message,{icon:2});
						}
						layer.close(load);
					},
					error:function(){
						layer.msg("服务器异常",{icon:2});
						layer.close(load);
					}
				})
			}) 
		}
		function deletes(){
			layer.msg('删除');
		}
		function refresh(){
			//table.reload('');
			layer.msg('刷新成功',{icon:1});
		}
		
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}//end define function
)//endedefine
</script>


</html>