<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
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
			<tr>
				<td><select name="" lay-search><option value=""></option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><select name="" lay-search><option value=""></option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil: 'layui/myModules/myutil',
}).define(
	['tablePlug','myutil'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		table.render({
			elem:'#tableData',
			url:'${ctx}/ledger/packingChildPage',
			page:true,
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'当月销售编号',   field:'saleNumber',   },
			       {align:'center', title:'发货日期',   field:'sendDate', 	},
			       {align:'center', title:'甲方',   field:'user',	templet:'<span>{{ d.customer?d.customer.user.userName:""}}</span>'},
			       {align:'center', title:'乙方',   field:'custom',	templet:'<span>{{ d.customer?d.customer.name:""}}</span>'},
			       {align:'center', title:'批次号',   field:'bacthNumber',	},
			       {align:'center', title:'产品名',   field:'productName',	templet:'<span>{{ d.product?d.product.name:""}}</span>'},
			       {align:'center', title:'版权',   field:'copyright',width:'6%',	templet:'<span>{{ d.copyright?"是":"否"}}</span>'},
			       {align:'center', title:'数量',   field:'count',	width:'8%',},
			       {align:'center', title:'合同总价',   field:'sumPrice',	width:'8%',},
			       {align:'center', title:'单只价',   field:'price', edit: 'text', width:'6%',	},
			       ]],
	       done:function(){
				var isDouble=0;
				$('td[data-edit="text"]').on('click',function(obj){
					if(++isDouble%2!=0)
						$(this).click();
					if($('.layui-table-edit').length>0){
						var elem = this;
						myutil.getData({
							url:'${ctx}/ledger/getPackingChildPrice',
							done: function(data){
								data=='' && (data="无以往价格");
								layer.tips(data, elem, {
									  tips: [4, '#78BA32'],
					                  time:0
					            });
								$('.layui-layer-tips').unbind().on('click',function(){
									layer.closeAll();
								})
							}
						})
						
					}
					
				})
			}
		})
		table.on('edit(tableData)',function(){
			/* myutil.saveAjax({
				url:'/ledger/updatePackingChild',
			}) */
		})
		form.on('submit(search)',function(obj){
			layer.msg(JSON.stringify(obj.field));
			table.reload('',{
				where:{}
			})
		}) 
	}//end define function
)//endedefine
</script>

</html>