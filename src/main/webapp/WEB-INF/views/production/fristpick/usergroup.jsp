<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<title>一楼包装员工分组</title>
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<div id="div"></div>
	</div>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	usergroup: 'layui/myModules/product/usergroup',
}).define(
	['usergroup'],
	function(){
		layui.usergroup.render({
			elem:'#div',
			ctx:'${ctx}',
			type:2,
		})
	}//end define function
)//endedefine
</script>
</html>