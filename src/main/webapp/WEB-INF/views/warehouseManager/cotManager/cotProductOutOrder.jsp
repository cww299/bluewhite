<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>皮壳生产出库</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>产品名:</td>
				<td><input type="text" name="productName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
<script>
layui.config({
	/* base : '${ctx}/static/layui-v2.4.5/' */
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		
		mytable.render({
			elem:'#tableData',
			url:'',
			curd:{
				otherBtn:function(obj){
					
				},
			},
			autoUpdate:{
				saveUrl:'',
				deleUrl:'',
				field:{ },
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'',   field:'',	},
			       { title:'',   field:'',   },
			       { title:'',   field:'', 	},
			       { title:'',   field:'',	},
			       { title:'',   field:'',	},
			       { title:'',   field:'',	},
			       ]]
		})
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
	}//end define function
)//endedefine
</script>
</body>
</html>