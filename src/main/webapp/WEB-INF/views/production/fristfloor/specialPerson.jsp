<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<title>一楼特急人员</title>
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
	specialPerson: 'layui/myModules/product/specialPerson',
}).define(
	['specialPerson'],
	function(){
		layui.specialPerson.render({
			elem:'#div',
			ctx:'${ctx}',
			type:1,
		})
	}//end define function
)//endedefine
</script>
</html>