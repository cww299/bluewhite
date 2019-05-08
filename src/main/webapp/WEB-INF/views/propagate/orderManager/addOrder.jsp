<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加订单</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
.layui-input, .layui-select, .layui-textarea {
    height: 28px;
}
#headerTool{
	border:1px solid white;
}
td{
	text-align:center;
}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body" style="height:850px">
		<table class="layui-form" style="width:100%" id="headerTool">
			<tr>
				<td><a style="color:blue" href="#" id="customName">客户名称：</a></td>			
				<td><input type="text" class="layui-input"></td>
				<td>订单编号：</td>			
				<td><input type="text" class="layui-input"></td>
				<td>订单状态：</td>			
				<td><input type="text" class="layui-input"></td>
			</tr>
			<tr>
				<td>整单优惠：</td>			
				<td><input type="text" class="layui-input"></td>
				<td>邮费：</td>			
				<td><input type="text" class="layui-input"></td>
				<td>所属客服：</td>			
				<td><input type="text" class="layui-input"></td>
			</tr>
			<tr>
				<td>收款账号：</td>			
				<td><input type="text" class="layui-input"></td>
				<td>收款金额：</td>			
				<td><input type="text" class="layui-input"></td>
				<td>发货仓库：</td>			
				<td><input type="text" class="layui-input"></td>
			</tr>
			<tr>
				<td>收货人：</td>			
				<td><input type="text" class="layui-input"></td>
				<td>手机：</td>			
				<td><input type="text" class="layui-input"></td>
				<td>电话：</td>			
				<td><input type="text" class="layui-input"></td>
			</tr>
			<tr>
				<td>所在地：</td>			
				<td colspan="3"><input type="text" class="layui-input"></td>
				<td>物流方式：</td>			
				<td><input type="text" class="layui-input"></td>
			</tr>
			<tr>
				<td>详细地址：</td>			
				<td colspan="3"><input type="text" class="layui-input" placeholder="您可以直接黏贴淘宝或拼多多的收货地址,会自动提取省市区和收货人信息"></td>
				<td>邮箱：</td>			
				<td><input type="text" class="layui-input"></td>
			</tr>
			<tr>
				<td>卖家备注：</td>			
				<td colspan="3"><input type="text" class="layui-input" placeholder=""></td>
				<td rowspan="2">旗帜：</td>			
				<td rowspan="2"><button class="layui-btn" lay-submit>确定添加</button></td>
			</tr>
			<tr>
				<td>内部备注：</td>			
				<td colspan="3"><input type="text" class="layui-input" placeholder=""></td>
			</tr>
		</table>
		<table class="layui-table" id="productTable" lay-filter="productTable"></table>
	</div>
</div>

</body>

<!-- 添加商品隐藏框 -->
<div id="addProductDiv" style="display:none;">
	<table class="layui-form" lay-filter="productListTool">
		<tr>
			<td><select><option value="">出售中			</option></select></td>			<td>&nbsp;</td>
			<td><select><option value="">按产品分类		</option></select></td>			<td>&nbsp;</td>
			<td><select><option value="">按淘宝宝贝分类	</option></select></td>			<td>&nbsp;</td>
			<td><select><option value="">按产品名称		</option></select></td>			<td>&nbsp;</td>
			<td><input type="text" class="layui-input"></td>												<td>&nbsp;</td>
			<td><button type="button" lay-submit   class="layui-btn layui-btn-sm">搜索</button></td>			<td>&nbsp;</td>
			<td><button type="button" id="refresh" class="layui-btn layui-btn-sm">刷新</button></td>			<td>&nbsp;</td>
			<td><button type="button" id="add"     class="layui-btn layui-btn-sm">添加商品</button></td>		<td>&nbsp;</td>
			<td><button type="button" id="sure"    class="layui-btn layui-btn-sm" >确定添加</button></td>
		</tr>
	</table>
	<table class="layui-table" id="productListTable" lay-filter="productListTable"></table>
</div>

<!-- 客户选择隐藏框 -->
<div id="customNameDiv" style="display:none;">
	<table class="layui-form">
		<tr>
			<td><select name=""><option value="">按来往单位分类</option></select></td>			<td>&nbsp;</td>
			<td><select name=""><option value="">按单位名称</option></select></td>				<td>&nbsp;</td>
			<td><input type="text" class="layui-input"></td>									<td>&nbsp;</td>
			<td><button lay-submit 		type="button" class="layui-btn layui-btn-sm">搜索</button>				<td>&nbsp;</td>
			<td><button id="addCustom"	type="button" class="layui-btn layui-btn-sm">添加来往单位</button>		<td>&nbsp;</td>
			<td><button id="refreshCustom"	type="button" class="layui-btn layui-btn-sm">刷新</button>			<td>&nbsp;</td>
		</tr>
	</table>
	<table id="customTable" lay-filter="customTable" class="layui-table"></table>
</div>

<!-- 已添加商品表的工具栏  -->
<script type="text/html" id="productTableToolbar">
<div class="layui-button-container">
	<span class="layui-btn layui-btn-sm" lay-event="add">新增商品</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除商品</span>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).define(
	['tablePlug'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 				
		, tablePlug = layui.tablePlug;
		
		$('#headerTool').find("td:even").css({backgroundColor:"rgba(65, 161, 210, 0.45)",padding:"1px"}); 
		
		form.render();
		
		form.on('submit',function(obj){
			layer.msg('确定添加')
		})
		table.on('toolbar(productTable)',function(obj){
			switch(obj.event){
			case 'add': add(); break;
			case 'delete': deletes(); break;
			}
		})
		$('#customName').on('click',function(){
			var customChoose=layer.open({
				title:'选择来往单位',
				area:['80%','80%'],
				type:1,
				content:$('#customNameDiv')
			});
			form.render();
			form.on('submit',function(obj){
				layer.msg('搜索');
			})
			$('#addCustom').on('click',function(){
				layer.msg('添加客户');
			})
			$('#refreshCustom').on('click',function(){
				layer.msg('刷新客户列表');
			})
			table.render({
				url:"",
				size:"lg",
				elem:"#customTable",
				loading:true,
				page:true,
				request:{
					pageName:'page',
					limitName:'size'
				},
				parseData:function(ret){
					return{
						code:ret.code,
						data:'',
						count:'',
						msg:ret.message,
					}
				},
				cols:[[
				       {align:'center',title:'单位名称',	field:''},
				       {align:'center',title:'所属类别',	field:''},
				       {align:'center',title:'手机',		field:''},
				       {align:'center',title:'所在地',	field:''},
				       ]]
			})
			
		})
		function add(){
			layer.open({
				type:1,
				title:'选择产品',
				area:['80%','90%'],
				content:$('#addProductDiv')
			})
			table.render({
				elem:'#productListTable',
				size:'lg',
				loading:true,
				page:true,
				height:'600',
				request:{
					pageName:'page',
					limitName:'size'
				},
				parseData:function(ret){
					return{
						code:	ret.code,
						msg:	ret.messafe,
						data:	'',
						count:	'',
					}
				},
				cols:[[
					       {type:'checkbox', align:'center', fixed:'left'},
					       {align:'center', title:'商品名称', field:'',},
					       {align:'center', title:'销售属性', field:'',},
					       {align:'center', title:'商家编码', field:'',},
					       {align:'center', title:'总库存',   field:'',},
					       {align:'center', title:'销售价',   field:'',},
					       {align:'center', title:'参考进价', field:'',},
				       ]]
				
			});
			form.render();
			form.on('submit',function(obj){
				layer.msg("搜索");
			});
			$('#add').on('click',function(){
				layer.msg("添加商品");
			})
			$('#refresh').on('click',function(){
				layer.msg('刷新');
			})
			$('#sure').on('click',function(){
				layer.msg('确定添加');
			})
		}
		
		function deletes(){
			layer.msg("删除")
		}
		
		table.render({
			elem:"#productTable", 
			toolbar:'#productTableToolbar',
			height:'580',
			loading:true,
			data:[],
			totalRow:true,
			page:false,
			cols:[[
			       {type:'checkbox',align:'center',fixed:'left'},
			       {filed:'',	title:'商家编码',	align:'center'},
			       {filed:'',	title:'商品简称',	align:'center'},
			       {filed:'',	title:'商品名称',	align:'center'},
			       {filed:'',	title:'数量',       align:'center'},
			       {filed:'',   title:'金额',   		align:'center'},
			       {filed:'',	title:'系统优惠',	align:'center'},
			       {filed:'',	title:'卖家调价',	align:'center'},
			       {filed:'',	title:'实际金额', 	align:'center'},
			       ]]
			
		})
		
		
		
		
		
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	}
)





</script>







</html>