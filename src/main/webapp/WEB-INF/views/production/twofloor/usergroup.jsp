<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>二楼针工员工分组</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<div id="div"></div>
	</div>
</div>
</body>

</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	usergroup: 'layui/myModules/product/usergroup',
}).define(
	['usergroup'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, usergroup = layui.usergroup;
		
		
		usergroup.render({
			elem:'#div',
			ctx:'${ctx}',
			type:3,
		})
		
		
	}//end define function
)//endedefine
</script>

</html>