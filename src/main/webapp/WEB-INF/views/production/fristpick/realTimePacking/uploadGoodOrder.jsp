<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>下货单</title>
	<style>
		.searchTable td{
		}
	</style>
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

</body>
<script>
var TPL = [
	'<div class="layui-form layui-form-pane" style="padding:20px;">',
		'<p style="display:none;"><button lay-submit lay-filter="sureAddOrder" id="sureAddOrder">确定</button></p>',
		'<div class="layui-form-item" pane>',
			'<label class="layui-form-label">下单时间</label>',
			'<div class="layui-input-block">',
				'<input class="layui-input" lay-verify="required" name="allotTime" id="allotTime" value="{{ d.allotTime || "" }}">',
			'</div>',
		'</div>',
		'<div class="layui-form-item" pane>',
			'<label class="layui-form-label">批次号</label>',
			'<div class="layui-input-block">',
				'<input class="layui-input" name="bacthNumber" value="{{ d.bacthNumber || "" }}">',
			'</div>',
		'</div>',
		'<div class="layui-form-item" pane>',
			'<label class="layui-form-label">批次数量</label>',
			'<div class="layui-input-block">',
				'<input class="layui-input" lay-verify="number" name="number" ',
					'value="{{ d.number || 0 }}">',
			'</div>',
		'</div>',
		'<div class="layui-form-item" pane>',
			'<label class="layui-form-label">产品名</label>',
			'<div class="layui-input-block">',
				'<input class="layui-input" id="productNameInput" lay-verify="required" placeholder="点击进行选择商品" ', 
					'value="{{ d.product?d.product.name:"" }}">',
			'</div>',
		'</div>',
		'<div class="layui-form-item" pane>',
			'<label class="layui-form-label">备注</label>',
			'<div class="layui-input-block">',
				'<input class="layui-input" name="remarks" value="{{ d.remarks || "" }}">',
				'<input type="hidden" name="id" value="{{ d.id || "" }}">',
				'<input type="hidden" name="productId" id="hiddenProductId" value="{{ d.product?d.product.id : "" }}">',
			'</div>',
		'</div>',
	'</div>',
	].join(' ');
var TPL_CHOOSE_PRODUCT = [
		'<div style="padding:10px;">',
			'<table class="layui-form searchTable"><tr>',
				'<td>商品名：</td>',
				'<td><input type="text" name="number" class="layui-input"></td>',
				'<td>商品名：</td>',
				'<td><input type="text" name="name" class="layui-input"></td>',
				'<td><span class="layui-btn" lay-submit lay-filter="searchProductBtn">搜索</span></td>',
				'<td></td>',
				'<td><span class="layui-badge">双击进行选择</span></td>',
			'</tr></table>',
			'<table id="productTable" lay-filter="productTable"></table>',
		'</div>'
	].join(' ');
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laydate','upload'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, upload = layui.upload
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/temporaryPack/findPagesUnderGoods',
			data:[],
			curd:{
				btn:[4],
				otherBtn: function(obj){
					if(obj.event=='add'){
						addEdit('add',{});
					}else if(obj.event=='update'){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据编辑');
						addEdit('update',check[0]);
					}
				},
			},
			autoUpdate:{
				deleUrl:'/temporaryPack/deleteUnderGoods',
			},
			toolbar:[
				'<span class="layui-btn layui-btn-sm" lay-event="add">新增数据</span>',
				'<span class="layui-btn layui-btn-sm" lay-event="update">修改数据</span>',
				'<span class="layui-btn layui-btn-sm" id="uploadBtn">导入数据</span>',
			].join(' '),
			cols:[[
			       { type:'checkbox',},
			       { title:'下单时间',   field:'allotTime',	},
			       { title:'批次号',   field:'bacthNumber',   },
			       { title:'产品名',   field:'product_name',	},
			       { title:'批次数量',   field:'number', 	},
			       { title:'备注',   field:'remarks',	},
			       { title:'状态',   field:'',	},
			       ]],
			 done:function(){
				 upload.render({
				   	  elem: '#uploadBtn'
				   	  ,url: '${ctx}/temporaryPack/import/excelUnderGoods'
				 	  ,before: function(obj){ 	
				 		 load = layer.load(1); 
					  }
				   	  ,done: function(res, index, upload){ 
				   		  if(res.code==0){
						   		layer.closeAll();
						   		layer.msg(res.message,{icon:1});
						   		table.reload('tableData');
				   		  }else{
					   			layer.close(load);
					   			layer.msg(res.message,{icon:2});
				   		  }
				   	  } 
				   	  ,accept: 'file' 
				   	  ,exts: 'xlsx|xls'
				})
			 }
		})
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
		function addEdit(type,data){
			var title = '新增下货单'
			var html = '';
			laytpl(TPL).render(data,function(h){
				html = h;
			})
			if(data.id){
				title = '修改下货单';
			}
			var addEditWin = layer.open({
				type:1,
				area:['500px','500px'],
				title: title,
				content: html,
				btn:['保存','取消'],
				success:function(){
					laydate.render({ elem:'#allotTime', type:'datetime', });
					$('#productNameInput').unbind().on('click',function(){
						var chooseProducyWin = layer.open({
							type:1,
							area:['700px','700px'],
							title:'商品选择',
							content: TPL_CHOOSE_PRODUCT,
							success:function(){
								mytable.render({
									elem: '#productTable',
									url: '${ctx}/productPages?type=2',
									cols:[[
										{ title:'商品编号', field:'number',},
										{ title:'商品名称', field:'name',},
									]],
								})
								table.on('rowDouble(productTable)', function(obj){
									var trData = obj.data;
									$('#hiddenProductId').val(trData.id);
									$('#productNameInput').val(trData.name);
									layer.close(chooseProducyWin);
								});
								form.on('submit(searchProductBtn)',function(obj){
									table.reload('productTable',{
										where: obj.field,
									})
								})
							}
						})
					})
					form.on('submit(sureAddOrder)',function(obj){
						myutil.saveAjax({
							url:'/temporaryPack/saveUnderGoods',
							data: obj.field,
							success:function(){
								table.reload('tableData')
								layer.close(addEditWin);
							}
						})
					})
				},
				yes:function(){
					$('#sureAddOrder').click();
				}
			})
		}
	}//end define function
)//endedefine
</script>
</html>