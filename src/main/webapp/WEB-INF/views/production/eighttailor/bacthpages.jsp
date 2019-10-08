<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/myModules/css/bacthManager.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>八号批次管理</title>
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
	bacthManager: 'layui/myModules/product/bacthManager',
}).define(
	['bacthManager'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 	
		, bacthManager = layui.bacthManager;
		
		bacthManager.render({
			elem:'#div',
			ctx:'${ctx}',
			type:5,
		})
	}//end define function
)//endedefine
</script>

</html>