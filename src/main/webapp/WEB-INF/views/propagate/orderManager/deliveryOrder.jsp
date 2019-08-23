<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>发货单</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr><td>&nbsp;&nbsp;&nbsp;</td>
				<td>发货日期:</td>
				<td><input type="text" class="layui-input" id="sendDateSearch"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>批次号:</td>
				<td><input type="text" class="layui-input" name="bacthNumber"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>产品名:</td>
				<td><input type="text" class="layui-input" name="productName"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<!-- <td>是否发货:</td>
				<td><select name="flag"><option value=""></option>
													<option value="1">发货</option>
													<option value="0" selected>未发货</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td> -->
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<!-- 表格工具栏模板 -->
<script type="text/html" id="tableToolbar">
<div>
	<span lay-event="sendGoods"  class="layui-btn layui-btn-sm">一键发货</span>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laydate = layui.laydate
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({
			elem:'#sendDateSearch',
			range:'~'
		})
		form.on('submit(search)',function(obj){
			var val = $('#sendDateSearch').val(),beg = '',end = '';
			if(val!=''){
				beg = val.split('~')[0].trim()+' 00:00:00';
				end = val.split('~')[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = beg;
			obj.field.orderTimeEnd = end;
			table.reload('tableData',{
				where: obj.field
			})
		})
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/inventory/packingChildPage',
			where:{ flag:0 },  //默认查找未发货
			//toolbar:'#tableToolbar',
			autoUpdate:{ saveUrl:'/inventory/updateInventoryPackingChild', },
			verify:{ count:['count'],notNull:['count']  },
			cols:[[
			       { type:'checkbox',},
			       { title:'发货日期',   field:'sendDate',	},
			       { title:'批次号',   field:'bacthNumber',	},
			       { title:'客户',   field:'customer_name',	},
			       { title:'产品',   field:'product_name',	},
			       { title:'发货数量',   field:'count',  edit:true,},
			      // { title:'是否发货',   field:'flag', transData:{ data:["未发货","发货"]}	},
			       ]],
		})
		table.on('toolbar(tableData)',function(obj){
			if(obj.event == 'sendGoods')
				myutil.deleTableIds({
					url: '/inventory/sendProcurement',
					table: 'tableData',
					text: '请选择信息发货|是否确认发货？'
				})
		})
	}
)
</script>
</html> 