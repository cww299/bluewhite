<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特急管理</title>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td><input type="text" placeholder="选择日期" class="layui-input" id="dayTime">
					<input type="text" placeholder="选择月份" class="layui-input" id="monthTime" style="display:none;">
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
				<td style="width:85%; text-align:right;">
					<input type="radio" name="viewTypeUser" lay-filter="user" value="1" title="按人员" checked>
      				<input type="radio" name="viewTypeUser" lay-filter="user" value="2" title="按分组">
      				<input type="radio" name="viewTypeDate" lay-filter="time" value="1" title="按日期" checked>
      				<input type="radio" name="viewTypeDate" lay-filter="time" value="2" title="按月份">
				</td>
			</tr>
		</table>
		<table id="specialTable" lay-filter="specialTable"></table>
	</div>
</div>
</body>
<script>
var TYPE = 2;
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({						
	specialManager : 'layui/myModules/specialManager'
}).use(['specialManager'],
	function(){
		var specialManager = layui.specialManager;
		
		specialManager.render(TYPE,'${ctx}');
});
</script>

</html>
