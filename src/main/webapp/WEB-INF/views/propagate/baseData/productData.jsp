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
#addEditDiv .layui-item{
	margin:5px 0px;
}
</style>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td><select name=""><option value="skuCode">按商品编号查找</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><input type='text' name='skuCode' class='layui-input' placeholder='请输入查找信息'></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="productTable" lay-filter="productTable"></table>
	</div>
</div>
</body>
<!-- 表格工具栏模板 -->
<script type="text/html" id="productTableToolbar">
<div>
	<span  class="layui-btn layui-btn-sm" id="addNew" >新增商品</span>
	<span  class="layui-btn layui-btn-sm" id="updateProduct" >修改商品</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除商品</span>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	addNew : 'layui/myModules/addNewProduct' ,
}).define(
	['tablePlug','addNew'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form		
		, table = layui.table 
		, addNew = layui.addNew
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.config.msgOffset = '200px';
		myutil.clickTr();
		var allInventory=[];
		getAllInventory();
		var cols=[[
					{align:'center', type:'checkbox',},
					{ title:'商品编号',   field:'skuCode',	width:'20%',},
					{ title:'商品高度',   field:'size',    width:'',},
					{ title:'商品重量', 	field:'weight', width:'',},
					{ title:'1688单价',   	field:'oseePrice',		width:'', },
					{ title:'天猫单价',   	field:'tianmaoPrice',	width:'',},
					{ title:'线下单价',   	field:'offlinePrice',	width:'',},
					{ title:'成本价', 		field:'cost',	width:'',},
					{ title:'广宣成本', 	field:'propagandaCost',	width:'',},
					{ title:'材质', 		field:'material',	},
					{ title:'填充物', 		field:'fillers',	},
					{ title:'备注', 		field:'remark',	},
		           ]];
		for(var i=0;i<allInventory.length;i++)
			cols[0].push({ field:'id'+allInventory[i].id, title:allInventory[i].name, sort:true, templet : getInventoryNumber(allInventory[i].id),	})
		function getInventoryNumber(warehouseId){
			return function(d){
				var inv=d.product.inventorys;
				for(var j=0;j<inv.length;j++){
					if(inv[j].warehouse.id==warehouseId){   //LAY_INDEX 为表格缓冲记录数，该数会加上翻页之前的数
						var length = layui.table.cache.productTable.length;					//当前每页显示的数量
						var index = (d.LAY_INDEX-1)%length;									//当前条目在本页数据的位置
						layui.table.cache.productTable[index]['id'+warehouseId] = inv[j].number;	//更新数据表格的内容，用于导出数据时显示
						return '<span style="color:blue;">'+inv[j].number+'</span>';
					}
				}
				return '<span style="color:red;">无库存</span>';
			}
		}
		table.render({
			elem:'#productTable',
			url:'${ctx}/inventory/commodityPage',
			toolbar:'#productTableToolbar',
			colFilterRecord:'local',
			page:true,
			autoSort:false,
			limits:[10,25,50,100],
			limit:100,
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return {data:ret.data.rows,count:ret.data.total,msg:ret.message,code:ret.code } },
			cols:cols,
			done:function(){
				addNew.render({		//新增商品
					elem: '#addNew',
					success: function(){
						table.reload('productTable');
					}
				})
				addNew.update({		//修改商品
					elem: '#updateProduct',
					table: 'productTable',
					success: function(){
						table.reload('productTable');
					}
				})
			}
		})
		form.on('submit(search)',function(obj){
			table.reload('productTable',{
				where:{skuCode:obj.field.skuCode},
				page: { curr : 1}
			})
		}) 
		table.on('sort(productTable)',function(obj){
			 table.reload('productTable', {
			    initSort: obj,
			    where: { 
			      warehouseSort: obj.field.substring(2,99)+":"+obj.type
			    }
			 }) 
		})
		
		table.on('toolbar(productTable)',function(obj){	
			switch(obj.event){
			case 'delete':	deletes();			break;
			} 	
		}) 
		function deletes(){
			var choosed=layui.table.checkStatus('productTable').data;
			if(choosed.length<1)
				return myutil.emsg('请选择商品');
			layer.confirm("是否确认删除？",{offset:'200px'},function(){
				var ids=[];
				for(var i=0;i<choosed.length;i++)
					ids.push(choosed[i].id);
				myutil.deleteAjax({
					url:"/inventory/deleteCommodity",
					ids: ids.join(','),
					success:function(){
						table.reload('productTable');
					}
				})
			})
		}
		function getAllInventory(){					
			$.ajax({
				url:'${ctx}/basedata/list?type=inventory',
				async:false,
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.length;i++){
							allInventory.push({ id:r.data[i].id,name:r.data[i].name});
						}
					}
				}
			})
		}
		
	}//end define function
)//endedefine
</script>
</html>