<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工序管理</title>
<style>
</style>
</head>
<body>
	<div class="layui-card" >
		<div class="layui-card-body">
			<table id="tableData" lay-filter="tableData"></table>
		</div> 
	</div>
</body>
<!-- 编辑菜单模板、新增菜单模板 -->
<script type="text/html" id="addEditTpl">
<div class="layui-form layui-form-pane" style="padding:20px;"> 
	<div class="layui-form-item" pane>
	    <label class="layui-form-label">工序名称</label> 
	    <div class="layui-input-block">
		    <input type="text" name="name" value="{{ d.name || "" }}" lay-verify="required" class="layui-input">
		</div>
	</div>
    <div class="layui-form-item" pane>
	    <label class="layui-form-label">工序耗时</label>
	    <div class="layui-input-block">
		    <input type="number" name="time" lay-verify="number" value="{{ d.time || ""}}" class="layui-input">
		</div>
	</div>
    <div class="layui-form-item" pane>
	    <label class="layui-form-label">包装方式</label>
	    <div class="layui-input-block">
			<select name="packagMethodId" id="addEditSelect">
				<option value="">请选择</option>
			</select>
		</div>
	</div>
    <div class="layui-form-item" pane>
        <label class="layui-form-label">公共属性</label>
        <div class="layui-input-block">
            <input type="checkbox" lay-skin="switch" name="publicType" lay-text="是|否" {{ d.publicType?'checked':'' }} >
		</div>
	</div>
	<input type="hidden" name="id" value="{{ d.id || "" }}" >
    <button type="button" id="submitBtn" lay-submit lay-filter="submitBtn" style="display:none;"></button>
</div>
</script>

<script>

layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable'
}).define([ 'mytable','laytpl'],
function() {
	var $ = layui.jquery
	, layer = layui.layer 				
	, form = layui.form
	, table = layui.table
	, mytable = layui.mytable
	, myutil = layui.myutil
	, laytpl = layui.laytpl;
	myutil.config.ctx = '${ctx}';
	myutil.clickTr();
	
	mytable.render({
		elem : '#tableData',
		url :  myutil.config.ctx+"/processes/processesPage",
		toolbar: ['<span class="layui-btn layui-btn-sm layui-btn-" lay-event="add">新增工序</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="edit">修改工序</span>',],
		curd:{
			btn: [4],
			otherBtn:function(obj){
				if(obj.event=='add'){
					addEdit();
				}else if(obj.event=='edit'){
					var check = table.checkStatus('tableData').data;
					if(check.length!=1)
						return myutil.emsg("请选择一条数据进行编辑");
					addEdit(check[0]);
				}
			},
		},
		autoUpdate:{
			deleUrl:'/processes/deleteProcesses',
		},
		ifNull:'',
		cols:[[
		       { type:'checkbox',},
		       { title:'工序名称',   field:'name', 	},
		       { title:'工序耗时',   field:'time', 	},
		       { title:'包装方式',   field:'packagMethod_name',    },
		       { title:'公共属性',   field:'publicType', transData:true, width:110,},
		       ]],
	})
	
	function addEdit(d){			
		var html=""
		,title ='新增'
		,data= d || {};
		if(data.id){
			title = '编辑';
		}
		layer.open({
			title : title
			,type : 1
			,btn : ['确定','取消']
			,area:['25%','400px']
			,content : laytpl(addEditTpl.innerHTML).render(data)
			,yes : function(){
				$('#submitBtn').click();
			}
			,success:function(layerElem,layerIndex){
				$('#addEditSelect').append(myutil.getBaseDataSelect({ 
					type:'packagMethod', 
					id: data.packagMethod?data.packagMethod.id : "",
				}))
				form.on('submit(submitBtn)',function(obj){
					obj.field.publicType = obj.field.publicType?1:0;
					myutil.saveAjax({
						url:'/processes/addProcesses',
						data: obj.field,
						success:function(){
							layer.close(layerIndex);
							table.reload('tableData');
						}
					})
				})
				form.render();	
			}
		});
	}
});
</script>
</html>
