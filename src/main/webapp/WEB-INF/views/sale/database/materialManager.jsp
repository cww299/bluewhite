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
<title>包装</title>
</head>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
<body>
	<table id="baseDataTable" lay-filter="baseDataTable"></table>
</body>
<!-- 表格工具栏 -->
<script type="text/html" id="baseDataToolBar">
	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除包装材料</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增包装材料</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑材料</span>
	</div>
</script>
<!-- 添加、编辑基础数据的弹窗模板 -->
<script type="text/html" id="addEditTpl">
	<div class="layui-form" style="padding:20px;">
		<div class="layui-form-item">
			<label class="layui-form-label">数据名称</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.name }}" name="name" placeholder="请输入" class="layui-input" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注remark</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.remark }}" name="remark" class="layui-input" lay-verify="required">
			</div>
		</div>
		<input type="hidden" name="parentId" value="0">
		<input type="hidden" name="type" value="packingMaterials">
		<input type="hidden" name="flag" value="1">
		<input type="hidden" name="id" value="{{ d.id }}">
		<p align="center"><button type="button" lay-submit lay-filter="addData" class="layui-btn layui-btn-sm">添加</button></p>
	</div>
</script>
<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil: 'layui/myModules/myutil'
}).define(
	[ 'tablePlug','myutil'],
	function() {
		var $ = layui.jquery
		, table = layui.table
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, form = layui.form
		, tablePlug = layui.tablePlug; 							
		myutil.config.msgOffset = '150px';	
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();				
		table.on('toolbar(baseDataTable)',function(obj){ 
			switch(obj.event){
			case 'add': addEidt('add'); 	break;
			case 'edit': addEidt('edit'); break;
			case 'delete': deletes();	break;
			}
		});
		function addEidt(type){ 
			var choosedData=layui.table.checkStatus('baseDataTable').data;
			var data={id:'',name:'',remark:'包装材料',};
			var title="新增数据";
			var html="";
			var tpl=addEditTpl.innerHTML;
			if(type=='edit'){
				choosedData.length>1 && myutil.emsg("不能同时编辑多条数据");
				choosedData.length<1 && myutil.emsg("至少选中一条数据进行编辑");
				if(choosedData.length !=1)
					return;
				data=choosedData[0];
			}
			laytpl(tpl).render(data,function(h){ html=h; })
			var open=layer.open({
				title:title,
				area:['30%','60%'],
				type:1,
				offset:'200px',
				content:html,
			})
			form.render();
			form.on('submit(addData)',function(obj){
				myutil.saveAjax({
					url:'/basedata/add',
					data:obj.field,
					success: function(){
						layer.close(open);
						table.reload('baseDataTable');	
					}
				});
			});
		}
		function deletes(){
			var choosedData=layui.table.checkStatus('baseDataTable').data;
			if(choosedData.length<1)
				return myutil.emsg("请至少选中一条数据删除");
			layer.confirm("是否确认删除"+choosedData.length+"条数据",{offset:'200px'},function(){
				var load=layer.load(1);
				var i=0;
				for(i=0;i<choosedData.length;i++){ 
					$.ajax({
						url:"${ctx}/basedata/delete",
						async:false,
						data:{id:choosedData[i].id},
					})
				}
				layer.close(load);
				if(i<choosedData.length){
					myutil.emsg("删除第"+i+"条数据时发生异常");
				}else{
					myutil.smsg("成功删除"+choosedData.length+"条数据");
					table.reload('baseDataTable');
				}
			})
		}
		table.render({
			elem : '#baseDataTable',
			toolbar : "#baseDataToolBar",
			url: '${ctx}/basedata/list?type=packagingMaterials',
			parseData : function(ret) { return { code : ret.code, msg : ret.message, data : ret.data, } },
			cols:[[
			       {type: 'checkbox',align : 'center',fixed: 'left'},
					{field : "id",title : "数据id",sort : true,align : 'center'},
					{field : "name",title : "名称",align : 'center'},
					{field : "remark",title : "备注",align : 'center',},
			      ]]
		});
	}//end function
)//end defind
</script>
</html>