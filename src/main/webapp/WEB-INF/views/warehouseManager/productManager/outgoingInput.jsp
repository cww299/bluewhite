<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>成品管理外发入库</title>
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
	outgoing : 'layui/myModules/inventory/outgoing' ,
}).define(
	['outgoing','myutil'],
	function(){
		var $ = layui.jquery
		,myutil = layui.myutil
		,outgoing = layui.outgoing;
		
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		outgoing.render({
			elem:'#app',
			ctx:'${ctx}',
		})
	}//end define function
)//endedefine
</script>
</html>