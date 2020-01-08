<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>皮壳管理生产出库</title>
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
	productionInput : 'layui/myModules/warehouseManager/productionInput' ,
	outInventory: 'layui/myModules/warehouseManager/outInventory' ,
}).define(
	['productionInput','myutil','outInventory'],
	function(){
		var $ = layui.jquery
		,myutil = layui.myutil
		,productionInput = layui.productionInput;
		myutil.config.ctx = '${ctx}';
		layui.outInventory.type = 2;
		myutil.clickTr();
		productionInput.type = 4;
		productionInput.render({
			elem:'#app',
			ctx:'${ctx}',
			outInventory: layui.outInventory,
		})
	}//end define function
)//endedefine
</script>
</html>