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
<title>菜单管理</title>
<style>
#menuTreeDiv{
	border: 1px solid #e2e2e2;
    padding: 10px;
    width: 22%;
    overflow: auto;
    height: 780px;
}
</style>
</head>
<body>
	<div class="layui-card" >
		<div class="layui-card-body">
			<div id="menuTreeDiv"></div>
		</div> 
	</div>
</body>

<!-- 编辑菜单模板、新增菜单模板 -->
<script type="text/html" id="templEditMenu">
<div class="layui-form" id="editMenuDiv" style="padding:20px;"> 
    <div class="layui-form-item">
	    <label class="layui-form-label">身份</label>
	    <div class="layui-input-block">
		    <input type="text" name="identity" placeholder="请输入" lay-verify="required" value="{{ d.identity }}" class="layui-input"></div></div>
    <div class="layui-form-item">
	    <label class="layui-form-label">菜单名称</label>
	    <div class="layui-input-block">
    	    <input type="text" name="name" placeholder="请输入" lay-verify="required" value="{{ d.name }}" class="layui-input"></div></div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否显示</label>
        <div class="layui-input-block">
            <input type="checkbox" lay-skin="switch" name="isShow" lay-text="是|否" {{ d.isShow==true?'checked':'' }} ></div></div>
    <div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">图标</label>
	    <div class="layui-input-block">
		    <input type="text" name="icon" placeholder="请输入" value="{{ d.icon }}" class="layui-input"></div></div>
	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">父菜单id</label>
	    <div class="layui-input-block">
		    <input type="text" name="parentId" placeholder="请输入" readonly value="{{ d.parentId }}" class="layui-input"></div></div>
	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">url</label>
	    <div class="layui-input-block">
		    <input type="text" name="url" placeholder="请输入" value="{{ d.url }}" class="layui-input"></div></div>
	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">span</label>
	    <div class="layui-input-block">
		    <input type="text" name="span" placeholder="请输入" value="{{ d.span }}" class="layui-input"></div></div>
	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">排序</label>
	    <div class="layui-input-block">
		    <input type="text" name="orderNo" placeholder="请输入" value="{{ d.orderNo }}" class="layui-input"></div></div>
    <input type="hidden" name="id" value="{{ d.id }}">
    <button type="button" id="submitBtn-{{ d.id ? 'edit-'+d.id : 'add-'+d.parentId }}" lay-submit lay-filter="submitBtn" style="display:none;"></button>
</div>
</script>

<script>

layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	menuTree : 'layui/myModules/menuTree'
}).define(
		[ 'menuTree','laytpl'],
		function() {
			var $ = layui.jquery
			, layer = layui.layer 				
			, form = layui.form 				
			, menuTree = layui.menuTree
			, laytpl = layui.laytpl;
			
			menuTree.render({
				elem : '#menuTreeDiv',
				url :  "${ctx}/getTreeMenuPage",
				checkbox : false,
				toolbar : true,
			})
			menuTree.onToolbar('menuTreeDiv',function(obj){
				switch(obj.type){
				case 'add': addEditMenu('add',obj.data);
							break;
				case 'edit': addEditMenu('edit',obj.data);
							break;
				case 'delete': deleteMenu(obj.data);
							break;
				}
			})
			
			function addEditMenu(type,d){			
				if($('#submitBtn-'+type+'-'+d.id).length > 0){				//通过判断提交按钮是否存在判断窗口是否打开
					layer.msg('该编辑窗口已经打开，请勿重复打开',{icon:2});
					return;
				}
				var html=""
				,title = d.name+'：新增子菜单'
				,tpl=templEditMenu.innerHTML
				,data={	icon:'',id:'',identity:'',isShow: false,name: "",parentId: d.id,span: "",url: "",orderNo:0 };
				if(type == 'edit'){
					data = d;
					title = '编辑菜单：'+d.name;
				}
				laytpl(tpl).render(data,function(h){	
					html=h;
				});
				layer.open({
					title : title
					,type : 1
					,btn : ['确定','取消']
					,area:['25%','60%']
					,shade : 0
					,content : html
					,yes : function(){
						//console.log($(this.content).find('button[lay-submit]'))
						//$(this.content).find('button[lay-submit]')[0].click();
						$('#submitBtn-'+type+'-'+d.id).click();
					}
				});
				form.on('submit(submitBtn)',function(obj){
					obj.field.isShow = obj.field.isShow ? true : false;
					var load=layer.load(1);
					$.ajax({
						url : '${ctx}/saveMenu',
						data : obj.field,
						success:function(result){
							var icon = result.code == 0 ? 1 : 2;
							menuTree.reloadData('menuTreeDiv');
							layer.msg(result.message, {icon : icon});
							layer.close(load);
						}
					});
				})
				form.render();	
			}
			function deleteMenu(data){
				var id = data.id;
				layer.confirm("是否确认删除？",function(){
					var load=layer.load(1);
					$.ajax({
						url:"${ctx }/deleteMenu?ids="+id,
						success:function(result){
							if(0==result.code){
								layer.closeAll();
								layer.msg(result.message,{icon:1});
							}
							else
								layer.msg(result.code+" "+result.message,{icon:2});
							layer.close(load);
						}
					})
				})
			}
	}//end defind
);
</script>
</html>
