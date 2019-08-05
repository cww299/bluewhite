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
<!-- 商品选择隐藏框 -->
<div id="choosedProductDiv" style="display:none;padding:10px;">
	<table class="layui-form">
		<tr>
			<td>商品名称：</td>
			<td><input class="layui-input" type="text" name="name"></td>
			<td>&nbsp;&nbsp;</td>
			<td>商品编号：</td>
			<td><input class="layui-input" type="text" name="number"></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" id="searchBtn">搜索</span></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-badge">提示：双击选择</span></td>
		</tr>
	</table>
	<table id="choosedTable" lay-filter="choosedTable"></table>
</div>
<!-- 表格工具栏模板 -->
<script type="text/html" id="productTableToolbar">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增商品</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改商品</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除商品</span>
</div>
</script>

<!-- 新增、修改商品模板 -->
<script type="text/html" id="addEditTpl">
<div class="layui-form layui-form-pane" style="padding:20px;" id="addEditDiv">
	<input type="hidden" name="id" value="{{d.id}}">
	<div class="layui-item">
		<label class="layui-form-label">商品编号</label>
		<div class="layui-input-block">
			<input type="hidden" name="number" id="addEditNumber">
			<input type="text" class="layui-input" id="choosedProductBtn" value="点击选择商品" name="skuCode" readonly>
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">1688价/元</label>
		<div class="layui-input-block">
			<input class="layui-input" name="oseePrice" value="{{d.oseePrice==null?'':d.oseePrice}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">天猫价/元</label>
		<div class="layui-input-block">
			<input class="layui-input" name="tianmaoPrice" value="{{d.tianmaoPrice==null?'':d.tianmaoPrice}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">线下价/元</label>
		<div class="layui-input-block">
			<input class="layui-input" name="offlinePrice" value="{{d.offlinePrice==null?'':d.offlinePrice}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">商品重量/g</label>
		<div class="layui-input-block">
			<input class="layui-input" name="weight" value="{{d.weight==null?'':d.weight}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">商品高度/cm</label>
		<div class="layui-input-block">
			<input class="layui-input" name="size" value="{{d.size==null?'':d.size}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">成本/元</label>
		<div class="layui-input-block">
			<input class="layui-input" name="cost" value="{{d.cost==null?'':d.cost}}">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">广宣成本/元</label>
		<div class="layui-input-block">
			<input class="layui-input" name="propagandaCost" value="{{d.propagandaCost==null?'':d.propagandaCost}}">
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
	<p><button lay-submit lay-filter="sure" id="addEditSureBtn" style="display:none">确定</button></p>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	/* cookieCol : 'layui/myModules/cookieCol' */
	myutil : 'layui/myModules/myutil' ,
}).define(
	['tablePlug','myutil',],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		//cookieCol.cookieName('productDataCookie');		//记录筛选列模块
		myutil.config.ctx = '${ctx}';
		myutil.config.msgOffset = '200px';
		myutil.clickTr();
		var allInventory=[],productSelect='',choosedWin='';
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
				var inv=d.inventorys;
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
			cols:cols
		})
		
		form.on('submit(search)',function(obj){
			table.reload('productTable',{
				where:{skuCode:obj.field.skuCode},
				page: { curr : 1}
			})
		}) 
		table.on('toolbar(productTable)',function(obj){	
			switch(obj.event){
			case 'add':		addEdit('add');		break;
			case 'update':	addEdit('edit'); 	break;
			case 'delete':	deletes();			break;
			} 	
		})
		table.on('sort(productTable)',function(obj){
			 table.reload('productTable', {
			    initSort: obj 
			    ,where: { 
			      warehouseSort: obj.field.substring(2,99)+":"+obj.type
			    }
			 }) 
		})
		function addEdit(type){	
			var data={id:'',skuCode:'',weight:'',size:'',material:'',fillers:'',cost:'',propagandaCost:'',remark:'',tianmaoPrice:'',oseePrice:'',offlinePrice:''},
			choosed=layui.table.checkStatus('productTable').data,
			tpl=addEditTpl.innerHTML,
			title='新增商品',
			html='';
			if(type=='edit'){	
				if(choosed.length>1)
					return myutil.emsg("不能同时编辑多条信息");
				if(choosed.length<1)
					return myutil.emsg("至少选择一条信息编辑");
				data=choosed[0];
				title="修改商品";
			}
			laytpl(tpl).render(data,function(h){ html=h; })
			var addEditWin=layer.open({
				type : 1,
				title : title,
				offset:'10px;',
				btn: ['确定','取消'],
				area : ['40%','75%'],
				content : html,
				success: function(){
					$("#addEditNumber").val(data.number?data.number:'');
					$("#choosedProductBtn").val(data.name?data.name:'点击选择商品'); 
					$('#choosedProductBtn').unbind().on('click',function(){
						choosedWin = layer.open({
							title: '选择产品',
							type:1,
							offset:'200px;',
							area:['50%','70%'],
							content: $('#choosedProductDiv'),
							success:function(){
								table.resize('choosedTable');
							}
						})
					})
					form.render();
				},
				yes: function(){
					$('#addEditSureBtn').click();
				}
			})
			form.render();
			form.on('submit(sure)',function(obj){
				if(obj.field.number=='')
					return myutil.emsg('请选择商品');
				myutil.saveAjax({
					url:'/inventory/addCommodity',
					data:obj.field,
					success:function(result){
							table.reload('productTable');
							layer.close(addEditWin);
					}
				})
			})
		}
		table.on('rowDouble',function(obj){
			$("#addEditNumber").val(obj.data.number);
			$("#choosedProductBtn").val(obj.data.name);
			layer.close(choosedWin);
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
		table.render({
			elem: '#choosedTable',
			page: true,
			url:'${ctx}/productPages',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
					{ align:'center', title:'产品编号',	field:'number',	},
					{ align:'center', title:'产品名称',	field:'name',	},
			       ]],
		})
		form.on('submit(searchProduct)',function(obj){
			table.reload('choosedTable',{
				where: obj.field,
				page: { curr:1 },
			})
		})
	}//end define function
)//endedefine
</script>
</html>