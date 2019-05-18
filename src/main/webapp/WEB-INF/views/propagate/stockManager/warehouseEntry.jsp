<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>入库单</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
</head>
<body>

<!-- 主页面 -->
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td><input type="text" class="layui-input" name="" id="date"></td>
				<td>&nbsp;&nbsp;</td>
				<td><select name=""><option value="">按单据编号</option></select>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" class="layui-input" name=""></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>
			</tr>
		</table>
		<table class="layui-table" id="warehouseTable" lay-filter="warehouseTable"></table>
	</div>
</div>

<!-- 商品选择隐藏框 -->
<div id="productChooseDiv" style="display:none;">
	<table class="layui-form" lay-filter="productChooseTool">
		<tr>
			<td><select><option value="">出售中			</option></select></td>			<td>&nbsp;</td>
			<td><select><option value="">按产品分类		</option></select></td>			<td>&nbsp;</td>
			<td><select><option value="">按淘宝宝贝分类	</option></select></td>			<td>&nbsp;</td>
			<td><select><option value="">按产品名称		</option></select></td>			<td>&nbsp;</td>
			<td><input type="text" class="layui-input"></td>							<td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" >
					<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i></button></td>					<td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="addNewProduct" >添加新商品</button></td>		<td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="refreshProduct" >刷新</button></td>				<td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="sure" >确定添加</button></td>
		</tr>
	</table>
	<table class="layui-table" id="productChooseTable" lay-filter="productChooseTable"></table>
</div>

<!-- 添加新商品隐藏框 -->
<table class="layui-form layui-table" style="display:none;" id="addNewProductWin">
	<tr><td>商品名称</td>
		<td><input type="text" class="layui-input" lay-verify="required" name="name"></td>
		<td>商品重量</td>
		<td><input type="text" class="layui-input" name="weight"></td></tr>
	<tr><td>商品材质</td>
		<td><input type="text" class="layui-input" name="material"></td>
		<td>商品规格</td>
		<td><input type="text" class="layui-input" lay-verify="required" name="size"></td></tr>
	<tr><td>商品填充物</td>
		<td><input type="text" class="layui-input" name="fillers"></td>
		<td>商品成本</td>
		<td><input type="text" class="layui-input" name="cost"></td></tr>
	<tr><td>库存数量</td>
		<td><input type="text" class="layui-input" name="quantity"></td>
		<td>仓库类型</td>
		<td><input type="text" class="layui-input" name="warehouse"></td></tr>
	<tr><td>商品单价</td>
		<td><input type="text" class="layui-input" lay-verify="required" name="price"></td>
		<td>广宣成本</td>
		<td><input type="text" class="layui-input" name="propagandaCost"></td></tr>
	<tr><td>商品编号</td>
		<td><input type="text" class="layui-input" lay-verify="required" name="number"></td>
		<td>备注</td>
		<td><textarea type="text" class="layui-input" name="remark"></textarea></td></tr>
	<tr><td colspan="4"><button lay-submit lay-filter="sureAddNew" class="layui-btn layui-btn-sm">确定</button></td></tr>
</table>

<!-- 入库单表格工具栏 -->
<script type="text/html" id="warehouseTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改</span>
	<span lay-event="refresh"  class="layui-btn layui-btn-sm" >刷新</span>
</div>
</script>

<script type="text/html" id="addEditTpl">
<div class="padding:10px;">
<table class="layui-form layui-table">
	<tr><td><input type="hidden" name="id" value="{{ d.id }}"></td>
		<td><input type="hidden" name="type" value="0"></td></tr>
	<tr><td>入库类型</td>
		<td><select name="status" value="{{ d.status }}"><option value="0">采购入库</option>
								  <option value="1">销售退货入库</option>
								  <option value="2">销售换货入库</option>
								  <option value="3">生产入库</option></select>
		<td>入库仓库</td>
		<td><select name="warehouse" value="{{ d.warehouse }}"><option value="0">主仓库</option>
									 <option value="1">客供仓库</option>
									 <option value="2">次品</option></select></td>
		<td>经手人</td>
		<td><select name="userId" value="{{ d.userId }}"><option value="1">管理员</option></select></td></tr>
	<tr><td>备注</td>
		<td colspan="3"><input type="text" name="remark" class="layui-input" value="{{ d.remark }}"></td>
		<td>操作</td>
		<td><span class="layui-btn" lay-submit lay-filter="sureAdd" >确定</span></td></tr>
</table>
<table class="layui-table" id="productListTable" lay-filter="productListTable"></table>
</div>
</script>
<!-- 入库单商品列表表格工具栏 -->
<script type="text/html" id="productListTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
</div>
</script>

</body>
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
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug;
		
		/*参数说明：
			choosedProduct：新增、修改入库单时，用于记录本订单已经选择的商品列表
			productListTable：显示已经选择的商品列表表格
			productChooseTable：打开商品选择时，所有的商品显示的表格
			warehouseTable：主页面显示的订单表格
			addNewProductWin：添加新商品的弹窗
			chooseProductWin：选择商品的弹窗
		*/
		var chooseProductWin
		,addNewProductWin
		,choosedProduct=[];			//用户已经选择上的产品
		
		form.render();
		
		table.render({				//渲染主页面入库单表格
			elem:'#warehouseTable',
			url:'${ctx}/inventory/procurementPage?type=0',
			toolbar:'#warehouseTableToolbar',
			loading:true,
			page:true,
			request:{
				pageName:'page',
				limitName:'size'
			},
			parseData:function(ret){
				return {
					data:ret.data.rows,
					count:ret.data.total,
					msg:ret.message,
					code:ret.code
				}
			},
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'单据编号',   field:'',		width:'',},
			       {align:'center', title:'入库类型',   field:'',	width:'',},
			       {align:'center', title:'日期',   field:'',		width:'',},
			       {align:'center', title:'入库仓库',   field:'',		width:'',},
			       {align:'center', title:'总数量', field:''},
			       {align:'center', title:'经手人', field:''},
			       {align:'center', title:'备注', field:''},
			       ]]
		})
		
		table.on('toolbar(warehouseTable)',function(obj){	//监听入库单表格按钮
			switch(obj.event){
			case 'add':		addEdit('add');		break;
			case 'update':	addEdit('edit'); 	break;
			case 'delete':	deletes();			break;
			case 'refresh':	refresh();			break;
			}
		})
		
		function addEdit(type){										//新增、编辑入库单
			choosedProduct=[];										//清空已选中的商品内容
			var data={ id:'',status:0,warehouse:0,userId:0,remark:'' },
			title='新增入库单',
			html='',
			choosed=layui.table.checkStatus('warehouseTable').data,
			tpl=addEditTpl.innerHTML;
			if(type=='edit'){
				if(choosed.length>1){
					layer.msg('无法同时编辑多条信息',{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg("请选择编辑",{icon:2});
					return;
				}
				title="";
				data=choosed[0];
			}
			laytpl(tpl).render(data,function(h){					//渲染新增、编辑入库单的模板
				html=h;
			})
			var addEditWin=layer.open({
				type:1,
				title:title,
				area:['90%','90%'],
				content:html
			})
			form.render();
			table.render({										//渲染选择后的商品表格
				elem:'#productListTable',
				toolbar:'#productListTableToolbar',
				size:'lg',
				loading:true,
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'商品名称', field:'name',},
				       {align:'center', title:'成本', 	  field:'cost',},
				       {align:'center', title:'仓库类型', field:'warehouse',},
				       {align:'center', title:'总库存',   field:'quantity',},
				       {align:'center', title:'数量',     field:'number',},
				       {align:'center', title:'销售价',   field:'price',},
				       {align:'center', title:'备注', 	  field:'remark',}, 
				       ]]
			})
			table.on('toolbar(productListTable)',function(obj){		//监听选择商品表格的工具栏按钮
				switch(obj.event){
				case 'add': openChooseProductWin(); break;
				case 'delete':deleteChoosedProduct();break;
				}
			})
			form.on('submit(sureAdd)',function(obj){					//确定添加入库单
				var data=obj.field;
				if(choosedProduct.length==0){
					layer.msg("请选择商品",{icon:2});
					return;
				}
				var child=[],allNum=0;
				for(var i=0;i<choosedProduct.length;i++){
					child.push({commodityId:choosedProduct[i].commodityId,number:choosedProduct[i].number});
					allNum+=choosedProduct[i].number;
				}
				data.number=allNum;
				data.commodityNumber=JSON.stringify(child);			//子列表商品
				var load = layer.load(1);
				$.ajax({
					url:"${ctx}/inventory/addProcurement",
					type:"post",
					data:obj.field,			//总数量。单据号传递
					success:function(result){
						if(0==result.code){
							table.reload('warehouseTable');
							layer.msg(result.message,{icon:1});
							layer.close(addEditWin);
						}else{
							layer.msg(result.message,{icon:2});
						}
						layer.close(load);
					},
					error:function(){
						layer.msg("服务器异常",{icon:2});
						layer.close(load);
					}
				})
			}) 
		}
		function deletes(){							//删除入库单表格
			layer.msg('删除');
		}
		function refresh(){							//刷新入库单表格
			layer.msg('刷新成功',{icon:1});
		}
		//选择商品隐藏框的按钮监听.添加商品弹窗共4个按钮监听。搜索、添加新商品、确定添加、刷新
		$('#sure').on('click',function(){	;
			if(sureChoosed())											//如果选择成功
				layer.close(chooseProductWin);							
		})
		$('#refreshProduct').on('click',function(){
			table.reload('productChooseTable');
			layer.msg('刷新成功！',{icon:1});
		})
		$('#addNewProduct').on('click',function(){						//添加新产品
			openAddNewPorductWin();
		})
	
		//添加新产品隐藏框监听-------弹窗按钮---1个：确定添加
		form.on('submit(sureAddNew)',function(obj){			
			var load=layer.load(1);
			$.ajax({
				url:'${ctx}/inventory/addCommodity',
				type:"post",
				data:obj.field,
				success:function(result){
					if(0==result.code){
						table.reload('productChooseTable');
						layer.close(addNewPorductWin);
						layer.msg(result.message,{icon:1});
					}
					else
						layer.msg(result.message,{icon:2});
					layer.close(load);
				},
				error:function(result){
					layer.msg('发生未知错误',{icon:2});
					layer.close(load);
				}
			})
		})
		function deleteChoosedProduct(){								//删除商品
			var choosed = layui.table.checkStatus('productListTable').data;
			if(choosed.length==0){
				layer.msg("请选择商品删除");
				return;
			}
			for(var i=0;i<choosed.length;i++){
				for(var j=0;j<choosedProduct.length;j++){
					if(choosed[i].id==choosedProduct[j].id){
						choosedProduct.splice(j,1);
						break;
					}
				}
			}
			table.reload('productListTable',{
				data:choosedProduct,
			})
		}
		function sureChoosed(){					//确定商品选择
			var choosed=layui.table.checkStatus('productChooseTable').data;
			if(choosed.length<1){
				layer.msg("请选择相关商品");
				return false;
			}
	 		for(var i=0;i<choosed.length;i++){
				var j=0;
				for(var j=0;j<choosedProduct.length;j++){	
					if(choosedProduct[j].commodityId==choosed[i].id)	{			//判断选择的商品是否已存在选择列表
						choosedProduct[j].number++;
						break;
					}
				}
				if(!(j<choosedProduct.length) || choosedProduct.length==0){				//如果不存在
					var orderChild={
							name:choosed[i].name,			//商品名称
							commodityId:choosed[i].id,		//商品id
							number:1,							//商品数量
							cost:choosed[i].cost,			//成本
							warehouse:choosed[i].warehouse, //仓库类型
							quantity:choosed[i].quantity,	//总库存
							remark:choosed[i].remark,		//备注
							price:choosed[i].price,			//商品单价
							actualSum:choosed[i].price,		//实际金额
					};
					choosedProduct.push(orderChild);
				} 
			}
			table.reload('productListTable',{
				data:choosedProduct
			});
			layer.msg('添加成功');
			return true;
		}
		
		function openAddNewPorductWin(){		//添加新产品窗口
			addNewPorductWin = layer.open({								
				type:1,
				title:'添加产品',
				content:$('#addNewProductWin'),
				area:['60%','60%']
			})
			form.render();
		}
		function openChooseProductWin(){					//商品选择隐藏框
			chooseProductWin = layer.open({		
				type:1,
				title:'选择产品',
				area:['80%','85%'],
				content:$('#productChooseDiv'),
			})
			table.render({
				elem:'#productChooseTable',
				size:'lg',
				url:'${ctx}/inventory/commodityPage',
				loading:true,
				page:true,
				height:'605',
				request:{
					pageName:'page',
					limitName:'size'
				},
				parseData:function(ret){	
					return{
						code:ret.code, msg:ret.message, data:ret.data.rows, count:ret.data.total,
					}
				},
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'商品名称', field:'name',},
				       {align:'center', title:'成本', 	  field:'cost',},
				       {align:'center', title:'仓库类型', field:'warehouse',},
				       {align:'center', title:'总库存',   field:'quantity',},
				       {align:'center', title:'销售价',   field:'price',},
				       {align:'center', title:'备注', 	  field:'remark',}, 
				      ]],
			});
			form.render();
		}
		
		
		
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
		
		
	}//end define function
)//endedefine
</script>


</html>