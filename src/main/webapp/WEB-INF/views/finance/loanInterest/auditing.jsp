<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<title>利息审核</title>
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body" id="app"></div>
</div>
<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
	audit : 'layui/myModules/finance/audit' ,
}).define(
	['mytable','audit'],
	function() {
		layui.myutil.config.ctx = '${ctx}';
		layui.audit.type = 10;
		layui.audit.render();
	}
)
</script>
</body>
</html>