<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品资料</title>
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
				<td><select name="name" lay-search><option value="">商品名称</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><select name="type" lay-search><option value="">按分类</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="productTable" lay-filter="productTable"></table>
	</div>
</div>

<!-- 查看商品库存隐藏框 -->
<div style="display:none;" id="lookoverDiv">
	<table class="layui-table" id="lookoverTable" lay-filter='lookoverTable'></table>
</div>


</body>

<!-- 表格工具栏模板 -->
<script type="text/html" id="productTableToolbar">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改</span>
	<span class="layui-badge" >提示：双击查看库存</span>
</div>
</script>

</body>

<!-- 新增、修改商品模板 -->
<script type="text/html" id="addEditTpl">
<div class="layui-form" style="padding:10px;">
	<input type="hidden" name="id" value="{{d.id}}">
	<div class="layui-item">
		<label class="layui-form-label">商品编号</label>
		<div class="layui-input-block">
			<input class="layui-input" name="skuCode" value="{{d.skuCode}}" lay-verify="required">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">1688批发价</label>
		<div class="layui-input-block">
			<input class="layui-input" name="OSEEPrice" value="{{d.oSEEPrice}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">天猫单价</label>
		<div class="layui-input-block">
			<input class="layui-input" name="tianmaoPrice" value="{{d.tianmaoPrice}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">线下批发价</label>
		<div class="layui-input-block">
			<input class="layui-input" name="offlinePrice" value="{{d.offlinePrice}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">商品重量</label>
		<div class="layui-input-block">
			<input class="layui-input" name="weight" value="{{d.weight}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">商品高度</label>
		<div class="layui-input-block">
			<input class="layui-input" name="size" value="{{d.size}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">成本</label>
		<div class="layui-input-block">
			<input class="layui-input" name="cost" value="{{d.cost}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">广宣成本</label>
		<div class="layui-input-block">
			<input class="layui-input" name="propagandaCost" value="{{d.propagandaCost}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">材质</label>
		<div class="layui-input-block">
			<input class="layui-input" name="material" value="{{d.material}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">填充物</label>
		<div class="layui-input-block">
			<input class="layui-input" name="fillers" value="{{d.fillers}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block">
			<input class="layui-input" name="remark" value="{{d.remark}}">
		</div>
	</div>
	<p style="text-align:center;"><button class="layui-btn layui-btn-sm" lay-submit lay-filter="sure">确定</button></p>
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
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug;
		
		table.render({
			elem:'#productTable',
			url:'${ctx}/inventory/commodityPage',
			toolbar:'#productTableToolbar',
			loading:true,
			page:true,
			size:'lg',
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
			       {align:'center', title:'商品编号',   field:'skuCode',	width:'10%',},
			       {align:'center', title:'商品高度',   field:'size',    width:'7%',},
			       {align:'center', title:'商品重量', 	field:'weight', width:'7%',},
			       {align:'center', title:'1688单价',   	field:'oSEEPrice',		width:'7%',},
			       {align:'center', title:'天猫单价',   	field:'tianmaoPrice',	width:'7%',},
			       {align:'center', title:'线下单价',   	field:'offlinePrice',	width:'7%',},
			       {align:'center', title:'成本价', 		field:'cost',	width:'5%',},
			       {align:'center', title:'广宣成本', 	field:'propagandaCost',	width:'7%',},
			       {align:'center', title:'材质', 		field:'material',	},
			       {align:'center', title:'填充物', 		field:'fillers',	},
			       {align:'center', title:'备注', 		field:'remark',	},
			       ]]
		})
		
		form.on('submit(search)',function(obj){
			layer.msg(JSON.stringify(obj.field));
			table.reload('productTable',{
				where:{name:obj.field.name,grade:obj.field.type}
			})
		}) 
		table.on('toolbar(productTable)',function(obj){
			switch(obj.event){
			case 'add':		addEdit('add');		break;
			case 'update':	addEdit('edit'); 	break;
			case 'delete':	deletes();			break;
			}
		})
		table.on('rowDouble(productTable)',function(obj){
			lookover(obj.data);
		})
		function lookover(data){
			layer.open({
				title:'查看商品库存',
				type:1,
				area:['80%','80%'],
				content:$('#lookoverDiv'),
			})
			table.render({
				elem:'#lookoverTable',
				data:data.inventorys,
				size:'lg',
				page:{},
				totalRow:true,
				cols:[[
					   {align:'center',  title:'商品名称',  field:'',  templet:function(d){ return data.skuCode; }},
				       {align:'center',  title:'仓库名称',  field:'',  templet:'<span>{{ d.warehouse.name }}</span>'},
				       {align:'center',  title:'库存数量',  field:'number',totalRow:true,  },
				       {align:'center',  title:'仓位',  field:'place', },
				       ]],
			})
		}
		
		function addEdit(type){
			var data={id:'',skuCode:'',weight:'',size:'',material:'',fillers:'',cost:'',propagandaCost:'',remark:'',tianmaoPrice:'',oSEEPrice:'',offlinePrice:''},
			choosed=layui.table.checkStatus('productTable').data,
			tpl=addEditTpl.innerHTML,
			title='新增商品',
			html='';
			if(type=='edit'){
				if(choosed.length>1){
					layer.msg("不能同时编辑多条信息",{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg("至少选择一条信息编辑",{icon:2});
					return;
				}
				data=choosed[0];
				title="修改商品";
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			var addEditWin=layer.open({
				type:1,
				title:title,
				area:['40%','65%'],
				content:html
			})
			form.render();
			form.on('submit(sure)',function(obj){
				var load=layer.load(1);
				$.ajax({
					url:'${ctx}/inventory/addCommodity',
					type:'post',
					data:obj.field,
					success:function(result){
						if(0==result.code){
							table.reload('productTable');
							layer.close(addEditWin);
							layer.msg(result.message,{icon:1});
						}else
							layer.msg(result.message,{icon:2});
						layer.close(load);
					},
					error:function(){
						layer.msg("服务器异常");
						layer.close(load);
					}
				})
			})
		}
		function deletes(){
			var choosed=layui.table.checkStatus('productTable').data;
			if(choosed.length<1){
				layer.msg('请选择商品',{icon:2});
				return;
			}
			layer.confirm("是否确认删除？",function(){
				var ids='';
				for(var i=0;i<choosed.length;i++){
					ids+=(choosed[i].id+",");
				}
				var load=layer.load(1);
				$.ajax({
					url:"${ctx}/inventory/deleteCommodity",
					data:{ids:ids},
					success:function(result){
						if(0==result.code){
							layer.msg(result.message,{icon:1});
							table.reload('productTable');
						}
						else{
							layer.msg(result.message,{icon:2});
						}
						layer.close(load);
					},
					error:function(result){
						layer.msg('ajax异常',{icon:2});
						layer.close(load);
					}
				})
			})
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