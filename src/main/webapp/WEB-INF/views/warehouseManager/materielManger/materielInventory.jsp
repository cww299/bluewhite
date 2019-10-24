<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>物料库存</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>物料名:</td>
				<td><input type="text" name="name" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>物料编号:</td>
				<td><input type="text" name="number" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>物料类型:</td>
				<td><select name="materielTypeId" id="materielTypeIdSelect"><option value="">请选择</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
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
		
		myutil.getData({
			url:'${ctx}/basedata/list?type=materielType&size=9999',
			success:function(d){
				var html = '';
				for(var i in d)
					html += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				$('#materielTypeIdSelect').append(html);
				form.render();
			}
		})
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/product/getMaterielPage',
			ifNull:0,
			cols:[[
			       { type:'checkbox',},
			       { title:'物料编号',   field:'number',	},
			       { title:'物料名',   field:'name',   },
			       { title:'单位',   field:'unit_name', 	},
			       { title:'物料类型',   field:'materielType_name',	},
			       { title:'库存数量',   field:'inventoryNumber',	},
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

</html>