<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<title>二楼针工批次管理</title>
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/myModules/css/bacthManager.css" media="all">
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
	bacthManager: 'layui/myModules/product/bacthManager',
}).define(
	['bacthManager'],
	function(){
		layui.bacthManager.render({
			elem:'#div',
			ctx:'${ctx}',
			type:3,
		})
	}//end define function
)//endedefine
</script>
</html>