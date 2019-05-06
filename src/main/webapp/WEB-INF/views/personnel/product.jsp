<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body" style="height:800px;">
		<table class="layui-form">
			<tr>
				<td>产品名：</td>
				<td><input type="text" class="layui-input" name="product" lay-verify="required"></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-filter="find" lay-submit>查找</button></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" id="add">新增</button>
		</table>
		<table class="layui-table" id="productTable"></table>
	</div>
</div>
</body>
<script>
layui.config({
	base:'${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug'
}).define(
	['tablePlug'],
	function(){
		var $ = layui.jquery
		, table = layui.table
		, laytpl = layui.laytpl
		, form = layui.form
		, tablePlug = layui.tablePlug; 		
		form.render();
		form.on('submit(find)',function(obj){
			layer.msg(obj.field)
		})
		$('#add').on('click',function(){
			layer.msg("asd")
		})
	}
)

</script>
</html>