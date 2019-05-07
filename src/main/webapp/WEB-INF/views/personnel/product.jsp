<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body" style="height:800px;">
		<table class="layui-form">
			<tr>
				<td>产品名：</td>
				<td><input type="text" class="layui-input" name="name" ></td>
				<td>&nbsp;&nbsp;</td>
				<td>产品编号：</td>
				<td><input type="text" class="layui-input" name="number" ></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-filter="find" lay-submit>查找</button></td>
		</table>
		<table class="layui-table" id="productTable" lay-filter="productTable"></table>
	</div>
</div>
</body>
<!-- 编辑修改产品信息 -->
<script type="text/html" id="addEditTpl">
<div class="layui-form" style="padding:20px;">
	<input type="hidden" name="id" value="{{ d.id }}">
	<div class="layui-form-item">
		<label class="layui-form-label">产品编号</label>
		<div class="layui-input-block">
			<input type="text" name="departmentNumber" lay-verify="required" class="layui-input" value="{{ d.departmentNumber }}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">产品名</label>
		<div class="layui-input-block">
			<input type="text" name="name" lay-verify="required" class="layui-input" value="{{ d.name }}">
		</div>
	</div>
	<p align="center"><button lay-submit class="layui-btn layui-btn-sm" type="button" lay-filter="sure">确定</button></p>
</div>
</script>
<!-- 工具栏 -->
<script type="text/html" id="toolbarOfProduct">
	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除产品</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增产品</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑产品</span>
	</div>	
</script>
<script>
layui.config({
	base:'${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug'
}).define(
	['tablePlug'],
	function(){
		var $ = layui.jquery
		, table = layui.table
		, laytpl = layui.laytpl
		, layer = layui.layer
		, form = layui.form
		, tablePlug = layui.tablePlug; 		

		form.on('submit(find)',function(obj){
			table.reload('productTable',{
				url:"${ctx}/productPages?",
				where:{
					page:1,
					name:obj.field.name,
					number:obj.field.number
				}
			});
		})
		
		table.render({
			elem:'#productTable',
			page:{},
			size:'lg',
			loading:true,
			url:"${ctx}/productPages",
			colFilterRecord : true,	
			toolbar:"#toolbarOfProduct",
			height:'700',
			request:{
				pageName: 'page' ,		
				limitName: 'size' 		
			},
			parseData:function(ret){
				return{
					code : ret.code,
					msg : ret.message,
					count : ret.data.total, 
					data : ret.data.rows,
				}
			},
			cols : [[  
			            {type: 'checkbox',align : 'center',fixed: 'left'},
						{field : "id",title : "ID",align : 'center',sort : true}, 
						{field : "number",title : "产品编号",align : 'center',sort : true}, 
						{field : "name",title : "产品名",align : 'center'}, 
						{field : "url",title : "图片",align : 'center'}, 
					]] 
		})
		table.on('toolbar(productTable)',function(obj){	
			switch(obj.event){
			case 'add': addEdit('add'); break;
			case 'edit': addEdit('edit'); break;
			case 'delete': deletes(); break;
			}
		})
		
		function addEdit(type){
			console.table(layui.table.checkStatus('productTable').data)
			var choosed=layui.table.checkStatus('productTable').data
				,data={id:'',name:'',departmentNumber:''}
				,html=''
				,tpl=addEditTpl.innerHTML;
			var typeName=(type=='add'?'新增':'修改');
			if('edit'==type){
				if(choosed.length>1){
					layer.msg("只能编辑一条数据",{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg("请选择至少一条数据编辑",{icon:2});
					return;
				}
				data=choosed[0];
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			layer.open({
				type:1,
				title:typeName+'产品',
				content:html,
				area:['30%','30%']
			})
			form.render();
			
			
			form.on('submit(sure)',function(obj){
				//去除空格
				obj.field.name=obj.field.name.replace(/\s*/g,"");
				obj.field.departmentNumber=obj.field.departmentNumber.replace(/\s*/g,"");
				var load=layer.load(1);
				$.ajax({
					url:"${ctx}/addProduct",
					type:"post",
					data:obj.field,
					success:function(result){
						if(result.code==0){
							layer.closeAll();
							layer.msg(typeName+'成功',{icon:1});
							table.reload('productTable');
						}
						else
							layer.msg(result.code+' '+typeName+'失败',{icon:2});
						layer.close(load);
					}
				})
			})
		}
		
		function deletes(){
			var choosed=layui.table.checkStatus('productTable').data;
			if(choosed.length<1){
				layer.msg("请选择至少一条数据删除",{icon:2});
				return;
			}
			layer.confirm("是否确认删除"+choosed.length+'条数据',function(){
				var load=layer.load(1);
				var successDel=0;
				var targetDel=choosed.length;
				for(var i=0;i<choosed.length;i++){
					$.ajax({
						url:"${ctx}/deleteProduct",
						data:{id:choosed[i].id},
						async:false,
						success:function(result){
							if(0==result.code){
								successDel++;
							}	
							else
								layer.msg("删除发生错误",{icon:2});
						},
						error:function(result){
							layer.msg("删除发生错误",{icon:2});
						}
					})
				}
				if(successDel==targetDel){
					layer.msg("成功删除"+choosed.length+'条数据',{icon:1});
				}
				else{
					layer.msg("删除发生异常，删除目标数："+targetDel+' 实际删除数：'+successDel,{icon:2});
				}
				table.reload('productTable');
				layer.close(load);
			})
		}
		// tr点击触发复选列点击
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	}
)

</script>
</html>