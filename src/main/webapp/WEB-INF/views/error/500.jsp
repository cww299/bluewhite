<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>500错误</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
</head>

<body>

<div style="align:center;">
	<h1>没有权限，请与管理员联系！</h1>
	<button class="layui-btn" id="back" type="button">返回首页</button>
</div>
	
</body>

<script>
//一般直接写在一个js文件中
layui.use(['layer', 'form'], function(){
	var $ = layui.jquery ;
  	
	$('#back').on('click',function(){
		window.location.href = "${ctx}/";
	})
  
});
</script>

</html>