<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>入库单</title>
<style type="text/css">
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
				<td><select name="type"><option value="">按客户类型查找</option>
									<option value="1">报销</option>
									<option value="2">采购应付和预算</option>
									<option value="4">税点应付和预算</option>
									<option value="5">物流</option>
									<option value="6">应付借款本金</option>
									<option value="7">应付社保和税费</option>
									<option value="8">应入库周转的材料</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>
			</tr>
		</table>
		<table class="layui-table" id="customTable" lay-filter="customTable"></table>
	</div>
</div>


<!-- 客户表格工具栏 -->
<script type="text/html" id="customTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
</div>
</script>
<!-- 类型解析模板 -->
<script type="text/html" id="typeTpl" >
{{# var text = '';
    switch(d.type){
	case 1: text = '报销'; break;
	case 2: text = '采购应付和预算'; break;
	case 4: text = '税点应付和预算'; break;
	case 5: text = '物流'; break;
	case 6: text = '应付借款本金'; break;
	case 7: text = '应付社保和税费'; break;
	case 8: text = '应入库周转的材料'; break;
} }}
{{ text }}
</script>
</body>
<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug: 'tablePlug/tablePlug'
}).define(
	['tablePlug', 'laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table;
		
		form.render();
		
 		table.render({
			elem:'#customTable',
			url:'${ctx}/fince/findCustomPage',
			toolbar:'#customTableToolbar',
			loading:true,
			page:true,
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message,  code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'id',   field:'id',		},
			       {align:'center', title:'名称',   field:'name',	},
			       {align:'center', title:'类型',   field:'type',  templet:'#typeTpl'},
			       ]]
		})
		
		table.on('toolbar(customTable)',function(obj){
			switch(obj.event){
			case 'delete':	deletes();			break;
			}
		})
		form.on('submit(search)',function(obj){
			table.reload('customTable',{
				where: obj.field,
				page: {curr:1 }
			})
		})
		function deletes(){
 			var choosed = layui.table.checkStatus('customTable').data;
 			if(choosed.length<1){
 				layer.msg('请选择信息',{icon:2});
 				return;
 			}
 			var ids='';
 			for(var i=0;i<choosed.length;i++){
 				ids+=(choosed[i].id+',');
 			}
 			var load = layer.load(1);
 			$.ajax({
 				url:'${ctx }/fince/deleteCustom',
 				async:false,
 				data:{ids:ids},
 				success:function(r){
 					if(0==r.code){
 						layer.msg(r.message,{icon:1});
 						table.reload('customTable');
 					}else
 						layer.msg(r.message,{icon:2});
 				}
 			})
 			layer.close(load);
		}
		
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
	}//end use function
)//end use
</script>


</html>