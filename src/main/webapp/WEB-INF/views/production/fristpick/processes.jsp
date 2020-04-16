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
			<table class="layui-form searchTable">
				<tr>
				   <td class="">工序名称：</td>
		           <td class=""><input type="text" name="name" class="layui-input" placeholder="请输入工序名称"></td>
		           <td>包装方式：</td>
		           <td><select name="packagMethodId" lay-search id="searchSelect">
		           			<option value="">请选择</option></select></td>
		           <td>是否手填：</td>
		           <td style="width:120px;"><select name="isWrite">
		           						<option value="">请选择</option>
		           						<option value="0">否</option>
		           						<option value="1">是</option></select></td>
		           <td>公共属性：</td>
		           <td style="width:120px;"><select name="publicType">
		           						<option value="">请选择</option>
		           						<option value="0">否</option>
		           						<option value="1">是</option></select></td>
		           <td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="search">查找</span></td>
				</tr>
			</table>
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
        <label class="layui-form-label">是否手填</label>
        <div class="layui-input-block">
            <input type="checkbox" lay-skin="switch" lay-filter="isWrite" 
				name="isWrite" lay-text="是|否" {{ d.isWrite?'checked':'' }} >
		</div>
	</div>
	<div class="layui-form-item isHide" pane>
	    <label class="layui-form-label">工序耗时</label>
	    <div class="layui-input-block">
		    <input type="number" name="time" value="{{ d.time || ""}}" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item isHide" pane>
        <label class="layui-form-label">公共属性</label>
        <div class="layui-input-block">
            <input type="checkbox" lay-skin="switch" lay-filter="publicType" name="publicType" 
				lay-text="是|否" {{ d.publicType?'checked':'' }} >
		</div>
	</div>
	<div class="layui-form-item isHide noPublic" pane {{ d.publicType?"style='display:none;'":"" }}>
	    <label class="layui-form-label">包装方式</label>
	    <div class="layui-input-block">
			<select name="packagMethodId" id="addEditSelect">
				<option value="">请选择</option>
			</select>
		</div>
	</div>
	<div class="layui-form-item isHide isPublic" pane {{ d.publicType?"":"style='display:none;'" }}>
	    <label class="layui-form-label">耗时总数量</label>
	    <div class="layui-input-block">
		    <input type="number" name="sumCount" value="{{ d.sumCount || ""}}" class="layui-input">
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
	
	$('#searchSelect').append(myutil.getBaseDataSelect({ type:'packagMethod', }));
	form.render();
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
		       { title:'耗时总数量',   field:'sumCount', 	},
		       { title:'包装方式',   field:'packagMethod_name',    },
		       { title:'公共属性',   field:'publicType', transData:true, width:110,},
		       { title:'是否手填',   field:'isWrite', transData:true, width:110,},
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
			,area:['25%','470px']
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
					var f = obj.field;
					f.isWrite = f.isWrite?1:0;
					f.publicType = f.publicType?1:0;
					if(f.isWrite){
						delete f.time;
						delete f.sumCount;
						delete f.packagMethodId;
						f.publicType = 0;
					}else{
						if(f.publicType)
							delete f.packagMethodId;
						else
							delete f.sumCount;
					}
					myutil.saveAjax({
						url:'/processes/addProcesses',
						data: f,
						success:function(){
							layer.close(layerIndex);
							table.reload('tableData');
						}
					})
				})
				form.on('switch(isWrite)', function(data){
					if(data.elem.checked)
						$('.isHide').hide();
					else
						$('.isHide').show();
				});
				form.on('switch(publicType)', function(data){
					if(data.elem.checked){
						$('.isPublic').show();
						$('.noPublic').hide();
					}
					else{
						$('.isPublic').hide();
						$('.noPublic').show();
					}
				});
				form.render();	
			}
		});
	}
	
	form.on('submit(search)',function(obj){
		table.reload('tableData',{
			where: obj.field,
			page: { curr:1 },
		})
	})
});
</script>
</html>
