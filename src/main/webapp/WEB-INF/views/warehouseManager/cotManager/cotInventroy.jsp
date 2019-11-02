<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>皮壳库存</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body" id="app"></div>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	inventory : 'layui/myModules/inventory/inventory' ,
}).define(
	['inventory','myutil'],
	function(){
		var $ = layui.jquery
		,myutil = layui.myutil
		,inventory = layui.inventory;
		
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		inventory.render({
			elem:'#app',
			ctx:'${ctx}',
		})
	}//end define function
)//endedefine
</script>
</html>