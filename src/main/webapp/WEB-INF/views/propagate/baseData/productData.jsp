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
<title>商品资料</title>
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
				<td><select name="" lay-search><option value="">商品名称</option></select></td>
				<td><select name="" lay-search><option value="">按分类</option></select></td>
				<td><button type="button" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="productTable" lay-filter="productTable"></table>
	</div>
</div>


</body>

<!-- 表格工具栏模板 -->
<script type="text/html" id="productTableToolbar">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改</span>
	<span lay-event="refresh"  class="layui-btn layui-btn-sm" >刷新</span>
</div>
</script>

</body>

<!-- 新增、修改商品模板 -->
<script type="text/html" id="addEditTpl">
<div class="layui-form" style="padding:10px;">
	<input type="hidden" name="id" value="{{d.id}}">
	<div class="layui-item">
		<label class="layui-form-label">编号</label>
		<div class="layui-input-block">
			<input class="layui-input" name="number" value="{{d.number}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">名称</label>
		<div class="layui-input-block">
			<input class="layui-input" name="name" value="{{d.name}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">重量</label>
		<div class="layui-input-block">
			<input class="layui-input" name="weight" value="{{d.weight}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">销售属性</label>
		<div class="layui-input-block">
			<input class="layui-input" name="size" value="{{d.size}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">材质</label>
		<div class="layui-input-block">
			<input class="layui-input" name="material" value="{{d.material}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">填充物</label>
		<div class="layui-input-block">
			<input class="layui-input" name="fillers" value="{{d.fillers}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">成本</label>
		<div class="layui-input-block">
			<input class="layui-input" name="cost" value="{{d.cost}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">广宣成本</label>
		<div class="layui-input-block">
			<input class="layui-input" name="propagandaCost" value="{{d.propagandaCost}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block">
			<input class="layui-input" name="remark" value="{{d.remark}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">单价</label>
		<div class="layui-input-block">
			<input class="layui-input" name="price" value="{{d.price}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">数量</label>
		<div class="layui-input-block">
			<input class="layui-input" name="quantity" value="{{d.quantity}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">仓库类型</label>
		<div class="layui-input-block">
			<select name="warehouse">
				<option value="0" {{ d.warehouse==0?'selected':'' }} >主仓库</option>
				<option value="1" {{ d.warehouse==1?'selected':'' }} >客供仓库</option>
				<option value="2" {{ d.warehouse==2?'selected':'' }} >次品</option>
			</select>
		</div>
	</div>
	<p style="text-align:center;"><button class="layui-btn layui-btn-sm" lay-submit lay-filter="sure">确定</button></p>
</div>
</script>
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
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug;
		
		table.render({
			elem:'#productTable',
			url:'${ctx}/inventory/commodityPage',
			toolbar:'#productTableToolbar',
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
			       {align:'center', title:'商品编号',   field:'number',	width:'10%',},
			       {align:'center', title:'名称',   		field:'name'},
			       {align:'center', title:'销售属性',   field:'size'},
			       {align:'center', title:'销售价',   	field:'price',	width:'5%',},
			       {align:'center', title:'成本价', 		field:'cost',	width:'5%',},
			       {align:'center', title:'重量', 		field:'weight', width:'5%',},
			       ]]
		})
		
		table.on('toolbar(productTable)',function(obj){
			switch(obj.event){
			case 'add':		addEdit('add');		break;
			case 'update':	addEdit('edit'); 	break;
			case 'delete':	deletes();			break;
			case 'refresh':	refresh();			break;
			}
		})
		
		function addEdit(type){
			var data={
					id:'',number:'',name:'',weight:'',size:'',material:'',fillers:'',cost:'',propagandaCost:'',remark:'',price:'',quantity:'',warehouse:''},
			title='新增商品',
			html='',
			choosed=layui.table.checkStatus('productTable').data,
			tpl=addEditTpl.innerHTML;
			if(type=='edit'){
				if(choosed.length>1){
					layer.msg("不能同时编辑多条信息",{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg("至少选择一条信息编辑",{icon:2});
					return;
				}
				data=choosed[0];
				title="修改商品";
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			var addEditWin=layer.open({
				type:1,
				title:title,
				area:['40%','90%'],
				content:html
			})
			form.render();
			form.on('submit(sure)',function(obj){
				var load=layer.load(1);
				$.ajax({
					url:'${ctx}/inventory/addCommodity',
					type:'post',
					data:obj.field,
					success:function(result){
						if(0==result.code){
							table.reload('productTable');
							layer.close(addEditWin);
							layer.msg(result.message,{icon:1});
						}else
							layer.msg(result.message,{icon:2});
						layer.close(load);
					},
					error:function(){
						layer.msg("服务器异常");
						layer.close(load);
					}
				})
			})
		}
		function deletes(){
			var choosed=layui.table.checkStatus('productTable').data;
			if(choosed.length<1){
				layer.msg('请选择商品',{icon:2});
				return;
			}
			
			layer.msg("接口还未完善");
		}
		function refresh(){
			table.reload('productTable');
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