<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>通讯录</title>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr><td>用户名：</td>
				<td>&nbsp;&nbsp;</td>
				<td id="selectTd"><input type="text" name="userName" class="layui-input"></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button>
		</table>
		<table class="layui-table" id="userTable" lay-filter="userTable"></table>
	</div>
</div>
</body>
<!-- 搜索下拉框模板 -->
<script type="text/html" id="addEditUserRoleTpl">
	<select id="userIdSelect" lay-search name="id" >
		<option value="">请选择</option>
		{{# layui.each(d,function(index,item){ }}
 			<option value="{{ item.id }}"  >{{ item.userName }}</option>
		{{# }); }}
	</select>
</script>
<script>
layui.use(['form','table','layer','jquery'],function(){
	var form = layui.form,
		table = layui.table;
		laytpl = layui.laytpl;
		$ = layui.$;
		layer = parent.layer === undefined ? layui.layer : top.layer;
	renderSelect();				//渲染下拉框
	table.render({
		elem:"#userTable",
		url:'${ctx}/system/user/findUserList',
		loading:true,
		size : 'lg',
		page:false,
		request:{
			
		},
		cols:[[
				{field : "id",title : "用户id",sort : true,align : 'center'},
				{field : "userName",title : "用户名",sort : true,align : 'center'},
				{field : "phone",title : "手机",align : 'center'},
		      ]],
	})
	
	form.on('submit(search)',function(obj){			//监听搜索按钮
		table.reload('userTable',{
			url:'${ctx}/system/user/findUserList?id='+obj.field.id
		}); 
	})
	
	function renderSelect(){
		var tpl=addEditUserRoleTpl.innerHTML;
		var html='';
		$.ajax({
			url:'${ctx}/system/user/findUserList',	//获取全部用户
			success:function(result){
				if(result.code==0){
					var row=result.data;
					laytpl(tpl).render(row,function(h){
						html=h;
					})
					$('#selectTd').html(html);
					form.render();
				}
			}
		}) 
	}
})

</script>
</html>