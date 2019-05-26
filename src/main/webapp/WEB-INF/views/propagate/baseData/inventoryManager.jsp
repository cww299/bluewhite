<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>仓库管理</title>
</head>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
<body>
	
<div class="layui-card">
	<div class="layui-card-body" style="height:800px;">
		<table class="table_th_search" id="inventoryTable" lay-filter="inventoryTable"></table>
	</div>
</div>

</body>

<!-- 表格工具栏 -->
<script type="text/html" id="inventoryTableToolBar">
	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除仓库</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增仓库</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑仓库</span>
	</div>
</script>
<!-- 添加、编辑基础数据的弹窗模板 -->
<script type="text/html" id="addEditTpl">
	<div class="layui-form" style="padding:20px;">
		<div class="layui-form-item">
			<label class="layui-form-label">仓库名称</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.name }}" name="name" placeholder="请输入" class="layui-input" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">是否可用</label>
			<div class="layui-input-block">
				<input type="checkbox" value="1" name="flag" lay-skin="switch" {{ d.flag==1?'checked':'' }} lay-text="是|否"}>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.remark }}" name="remark" class="layui-input" lay-verify="required">
			</div>
		</div>
		<input type="hidden" value="0" name="parentId">
		<input type="hidden" value="inventory" name="type">
		<input type="hidden" name="id" value="{{ d.id }}">
		<p align="center"><button type="button" lay-submit lay-filter="addData" class="layui-btn layui-btn-sm">确定</button></p>
	</div>
</script>

<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug'
}).define(
	[ 'tablePlug'],
	function() {
		var $ = layui.jquery
		, table = layui.table
		, laytpl = layui.laytpl
		, form = layui.form
		, tablePlug = layui.tablePlug; 							
		form.render();									//渲染表单
		renderTable('#inventoryTable');			//渲染表格
		
		table.on('toolbar(inventoryTable)',function(obj){ //函数参数说明：(type,parent,thisTable)作用类型、父类、获取哪个表格的选中数据
			switch(obj.event){
			case 'add': addEidt('add'); 	break;
			case 'edit': addEidt('edit'); break;
			case 'delete': deletes();	break;
			}
		});
		
		function addEidt(type){ 
			var choosedData=layui.table.checkStatus('inventoryTable').data;
			var data={id:'',name:'',remark:'仓库',flag:'1'};
			var title="新增数据";
			var html="";
			var tpl=addEditTpl.innerHTML;
			if(type=='edit'){
				if(choosedData.length>1){
					layer.msg("不能同时编辑多条数据",{icon:2});
					return;
				}
				if(choosedData.length<1){
					layer.msg("至少选中一条数据进行编辑",{icon:2});
					return;
				}
				data=choosedData[0];
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			var open=layer.open({
				title:title,
				area:['30%','20%'],
				type:1,
				content:html,
			})
			form.render();
			form.on('submit(addData)',function(obj){
				if(obj.field.flag!=1)			//如果开关没打开
					obj.field.flag=0;
				var load=layer.load(1);
				$.ajax({
					url:"${ctx}/basedata/add",
					data:obj.field,
					type:"post",
					success:function(result){
						if(0==result.code){
							layer.close(open);
							table.reload('inventoryTable');
							if(type=='add')
								layer.msg("添加成功",{icon:1});		//返回的messag为空
							else if(type=='edit')
								layer.msg("修改成功",{icon:1});
						}
						else
							layer.msg(result.code+'添加异常',{icon:2});
						layer.close(load);
					}
				})
			});
		}
		
		function deletes(){
			var choosedData=layui.table.checkStatus('inventoryTable').data;
			if(choosedData.length<1){
				layer.msg("请至少选中一条数据删除",{icon:2});
				return;
			}
			layer.confirm("是否确认删除"+choosedData.length+"条数据",function(){
				var load=layer.load(1);
				var i=0;
				for(i=0;i<choosedData.length;i++){ 
					$.ajax({
						url:"${ctx}/basedata/delete",
						async:false,
						data:{id:choosedData[i].id},
						success:function(result){
							if(0!=result.code){
							}
						},
						error:function(result){
							layer.msg("服务器异常",{icon:2});
						}
					})
				}
				layer.close(load);
				if(i<choosedData.length){
					layer.msg("删除第"+i+"条数据时发生异常",{icon:2});
				}else{
					layer.msg("成功删除"+choosedData.length+"条数据",{icon:1});
					table.reload('inventoryTable');
				}
				
			})
		}
		
		function renderTable(elem){								
			table.render({
				elem : elem,
				size : 'lg',
				page : false,
				url:"${ctx}/basedata/list?type=inventory",
				loading : true,  
				toolbar : "#inventoryTableToolBar",
				parseData : function(ret) {    		
					return {
						code : ret.code,
						msg : ret.message,
						data : ret.data,
					}
				},
				cols:[[
				       {type: 'checkbox',align : 'center',fixed: 'left'},
						{field : "id",title : "数据id",sort : true,align : 'center'},
						{field : "name",title : "名称",align : 'center'},
						{field : "remark",title : "remark",align : 'center'},
				      ]],
			});
		}
		
		// tr点击触发复选列点击
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
		
	}//end function
)//end defind

</script>

</html>