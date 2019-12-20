<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>食材库存</title>
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
	mytable : 'layui/myModules/mytable',
	inventory : 'layui/myModules/logisticsManager/inventory' ,
}).define(
	['mytable','inventory',],
	function(){
		var $ = layui.jquery
		,inventory = layui.inventory
		,myutil = layui.myutil;
		
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		inventory.type = 2;
		inventory.render({
			elem:'#app',
			ctx:'${ctx}',
		})
	}//end define function
)//endedefine
</script>
</html>