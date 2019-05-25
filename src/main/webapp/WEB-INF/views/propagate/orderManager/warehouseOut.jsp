<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出库单</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
td{
	text-align:center;
}
</style>
</head>
<body>

<!-- 主页面 -->
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td><select name=""><option>按批次号</option></select>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" class="layui-input" name="batchNumber" placeholder='请输入要查找的相关信息'></td>
				<td>&nbsp;&nbsp;</td>
				<td><select name="flag"><option value="">是否反冲</option><option value="1">反冲</option><option value="0">未反冲</option></select>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>
			</tr>
		</table>
		<table class="layui-table" id="outOrderTable" lay-filter="outOrderTable"></table>
	</div>
</div>

<!-- 添加订单隐藏框  -->
<div id="addOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table">
		<tr><td>批次号<input type="hidden" name="type" value="3" ></td>	<!-- 默认type类型为2，表示为入库单 -->
			<td><input type="text" class="layui-input" name='batchNumber' lay-verify='required'></td>
			<td>经手人</td>
			<td><select name="userId"><option value="1" >测试人admin</option></select></td>
			<td>备注</td>
			<td colspan="3"><input type="text" name="remark" class="layui-input"></td></tr>
		<tr>
			<td>出库数量</td>
			<td><input type="text" class="layui-input" name='number' id="addOrderNumber" value='0' readonly></td>
			<td>默认出库数量</td>
			<td><select lay-filter="defaultSelect" type='number' ><option value="all">出库全部</option><option value="zero">不出库</option></select></td>
			<td>默认出库类型</td>
			<td><select lay-filter="defaultSelect" type='status'>
						<option value="0">销售出库</option>
						<option value="1">调拨出库</option>
						<option value="2">销售换货出库</option>
						<option value="3">采购退货出库 </option></select></td>
			<td>操作</td>
			<td><span class="layui-btn" lay-submit lay-filter="sureAdd" >确定</span></td></tr>
	</table>
	<table class="layui-table" id="productListTable" lay-filter="productListTable"></table>
</div>

<!-- 查看订单隐藏框  -->
<div id="lookoverOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table" lay-skin="line">
		<tr><td>批次号</td>	
			<td><input type="text" class="layui-input" readonly id="look_batchNumber"></td>
			<td>经手人</td>
			<td><select disabled><option value="1" id="look_userName">测试人admin</option></select></td>
			<td>总数量</td>
			<td><input type="text" class="layui-input" id="look_number" readonly></td></tr>
		<tr><td>备注</td>
			<td colspan="5"><input type="text" id="look_remark" class="layui-input" readonly></td></tr>
	</table>
	<table class="layui-table" id="lookOverProductListTable" lay-filter="lookOverProductListTable"></table>
</div>


<!-- 商品选择隐藏框 -->
<div id="productChooseDiv" style="display:none;">
	<table class="layui-form" lay-filter="productChooseTool">
		<tr>
			<td><select><option value="">按产品名称		</option></select></td>			<td>&nbsp;</td>
			<td><input type="text" class="layui-input" name="skuCode" placeholder="请输入查找的商品名"></td>				<td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" >
					<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i></button></td>					<td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="addNewProduct" >添加新商品</button></td>		<td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="sure" >确定添加</button></td>
		</tr>
	</table>
	<table class="layui-table" id="productChooseTable" lay-filter="productChooseTable"></table>
</div>


<!-- 出库单表格工具栏 -->
<script type="text/html" id="outOrderTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >一键反冲</span>
	<span class="layui-badge" >小提示：双击查看详细信息</span>
</div>
</script>

<!-- 商品列表表格工具栏 -->
<script type="text/html" id="productListTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
</div>
</script>

<!-- 是否反冲转换模板 -->
<script type="text/html" id="flagTpl">
	{{# var color=d.flag==1?'':'green';
		var msg=d.flag==1?'反冲数据':'未反冲';}}
	<span class="layui-badge layui-bg-{{ color }}">{{ msg }}</span>
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
		, tablePlug = layui.tablePlug;
		
		var chooseProductWin;		//选择商品弹窗
		var allInventory=[];		//所有仓库
		
		getAllInventory();
		
		form.render();
		table.render({				//渲染主页面单表格
			elem:'#outOrderTable',
			url:'${ctx}/inventory/procurementPage?type=3',
			toolbar:'#outOrderTableToolbar',
			loading:true,
			page:{},
			request:{pageName:'page',limitName:'size'},
			parseData:function(ret){
				return {data:ret.data.rows,count:ret.data.total,msg:ret.message,code:ret.code}},
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'批次号',   field:'batchNumber',		},
			       {align:'center', title:'总数量', field:'number'},
			       {align:'center', title:'剩余总数量', field:'residueNumber'},
			       {align:'center', title:'经手人',	templet:'<p>{{ d.user }}</p>'},
			       {align:'center', title:'备注', 	field:'remark'},
			       {align:'center', title:'是否反冲', 	field:'flag', templet:'#flagTpl'},
			       ]]
		})
		
		table.on('toolbar(outOrderTable)',function(obj){	//监听单表格按钮
			switch(obj.event){
			case 'add':			add();			break;
			case 'delete':		deletes();		break;
			}
		})
		
		table.on('rowDouble(outOrderTable)',function(obj){
			lookover(obj.data);
		})
		form.on('submit(search)',function(obj){
			table.reload('outOrderTable',{
				where:obj.field
			})
		})
		
		function deletes(){							//删除生产单表格
			var choosed=layui.table.checkStatus('outOrderTable').data;
			if(choosed.length<1){
				layer.msg('请选择生产单',{icon:2});
				return;
			}
			layer.confirm('是否确认反冲？',function(){
				var ids='';
				for(var i=0;i<choosed.length;i++)
					ids+=(choosed[i].id+',');
				var load=layer.load(1);
				$.ajax({
					url:'${ctx}/inventory/deleteProcurement?ids='+ids,
					success:function(result){
						if(0==result.code){
							table.reload('outOrderTable');
							layer.msg(result.message,{icon:1});
						}else
							layer.msg(result.message,{icon:2});
						layer.close(load);
					}
				})
			})
		}
		
		//-------查看入库单功能--------------------
		function lookover(data){
			layer.open({
				type : 1,
				title : '查看生产单',
				area : ['90%','90%'],
				content : $('#lookoverOrderDiv')
			})
			table.render({									//渲染选择后的商品表格
				elem:'#lookOverProductListTable',
				data:data.procurementChilds,
				page:{},
				loading:true,
				cols:[[
				       {align:'center', title:'商品名称',  templet:'<p>{{ d.commodity.skuCode }}</p>'},
				       {align:'center', title:'数量',     field:'number',},
				       {align:'center', title:'出库仓库', 	  templet:function(d){return d.warehouse.name; },}, 
				       {align:'center', title:'出库类型', 	 templet:function(d){return d.status;},}, 
				       {align:'center', title:'仓位',  	  field:'place',}, 
				       {align:'center', title:'备注', 	  field:'childRemark',}, 
				       ]]
			})
			$('#look_batchNumber').val(data.batchNumber);
			$('#look_remark').val(data.remark);
			$('#look_number').val(data.number);
			//$('#look_user').val(choosed[0].user);
		}
		
		//-------新增出库单功能---------------
		var choosedProduct=[];		//用户已经选择上的产品,渲染新增单的产品表格数据
		var defaultStatus=0;
		var defaultNumber='';
		function add(){										//新增单
			layer.open({
				type : 1,
				title : '新增生产单',
				area : ['90%','90%'],
				content : $('#addOrderDiv')
			})
			table.render({									//渲染选择后的商品表格
				elem:'#productListTable',
				toolbar:'#productListTableToolbar',
				data:[],
				page:{},
				size:'lg',
				loading:true,
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'商品名称', field:'skuCode',},
				       {align:'center', title:'出库数量',     field:'number', edit:'true', },
				       {align:'center', title:'出库仓库',     field:'warehouseId', 	templet: getInventorySelectHtml()},
				       {align:'center', title:'出库类型',     field:'status',  		templet: getStatusSelectHtml()},
				       {align:'center', title:'仓位',  	 	  field:'place', 	edit : true,}, 
				       {align:'center', title:'备注',  	  field:'childRemark', edit:true}, 
				       ]],
			   	done: function (res, curr, count) {	//设置下拉框初始			
	                layui.each( $('select'), function (index, item) {
	                    var elem = $(item);
	                	if(elem.data('value')!=undefined)		
	                    	elem.val(elem.data('value')).parents('div.layui-table-cell').css('overflow', 'visible');
	                });
	                form.render(); 
	            },
			})
			table.reload('productListTable',{ data : choosedProduct });
		}
		table.on('toolbar(productListTable)',function(obj){		//监听选择商品表格的工具栏按钮
			switch(obj.event){
			case 'add': openChooseProductWin(); break;
			case 'delete':deleteChoosedProduct();break;
			}
		})
		form.on('select(selectStatus)', function (data) {		//监听数据表格中的 状态选择下拉框
            var elem = $(data.elem);
            var trElem = elem.parents('tr');
            choosedProduct[trElem.data('index')].status = data.value;
        });				
		form.on('select(selectInventory)', function (data) {
            var elem = $(data.elem);
            var trElem = elem.parents('tr');
            choosedProduct[trElem.data('index')].warehouseId = splicStr(data.value,'before');		//获取value中的id，value值为id_number
            choosedProduct[trElem.data('index')].number = defaultNumber=='all'?splicStr(data.value,'after'):0;
        });
		form.on('select(defaultSelect)',function(obj){
			switch(obj.elem.getAttribute('type')){
			case 'number' : defaultNumber=obj.value;    break;
			case 'status' : defaultStatus=obj.value; 		break;
			}
			table.reload('productListTable');
		})
		table.on('edit(productListTable)', function(obj){ 			//监听编辑表格单元
			if(obj.field=='number'){
				if(isNaN(obj.value))
					layer.msg("修改无效！请输入正确的数字",{icon:2});
				else if(obj.value=='')
					layer.msg('计划的数量不能为空',{icon:2});
				else if(obj.value<0)
					layer.msg('计划的数量不能小于0',{icon:2});
				else if(obj.value%1 !== 0)
					 layer.msg('计划的数量必须为整数',{icon:2});
				else
					for(var i=0;i<choosedProduct.length;i++){
						 if(choosedProduct[i].commodityId==obj.data.commodityId){		//重新对该行的相关数据进行计算
						 	$('#addOrderNumber').val($('#addOrderNumber').val()-choosedProduct[i].number-(-parseInt(obj.value)));
							choosedProduct[i].number=parseInt(obj.value);
						 	break;
						}
					}
			}else{
				for(var i=0;i<choosedProduct.length;i++){
					 if(choosedProduct[i].commodityId==obj.data.commodityId){		//重新对该行的相关数据进行计算
						 if(obj.field=='childRemark')
							choosedProduct[i].childRemark = obj.value;
						 else
							 choosedProduct[i].place = obj.value;
					 	break;
					}
				}
			}
			table.reload('productListTable',{
				data : choosedProduct
			})
		});
		form.on('submit(sureAdd)',function(obj){					//确定添加入库单
			var child=[],allNum=0;
			for(var i=0;i<choosedProduct.length;i++){
				var t=choosedProduct[i];			
				if(t.number<1){
					layer.msg('计划数量不能为0！',{icon:2});
					return;
				}
				child.push({
					commodityId : 	t.commodityId,
					number : 		t.number,
					place : 		t.place			==	undefined ? '' : t.place,
					status : 		t.status		==	undefined ? defaultStatus : t.status,
					childRemark : 	t.childRemark	==	undefined ? '' : t.childRemark
				});
			}
			var data=obj.field;
			data.number=$('#addOrderNumber').val();
			data.commodityNumber=JSON.stringify(child);			//子列表商品
			var load = layer.load(1);
			$.ajax({
				url:"${ctx}/inventory/addProcurement",
				type:"post",
				data:data,			
				success:function(result){
					if(0==result.code){
						$('#resetAddOrder').click();
						layer.closeAll();
						table.reload('outOrderTable');
						layer.msg(result.message,{icon:1});
					}else{
						layer.msg(result.message,{icon:2});
						layer.close(load);
					}
				},
				error:function(){
					layer.msg("服务器异常",{icon:2});
					layer.close(load);
				}
			})
		}) 
	
		//选择商品隐藏框的按钮监听.添加商品弹窗共4个按钮监听。搜索、添加新商品、确定添加
		$('#sure').on('click',function(){	
			if(sureChoosed())											//如果选择成功
				layer.close(chooseProductWin);							
		})
		
		$('#resetAddOrder').on('click',function(){			//此处如果加confirm提示。则新增成功时无法清空
			$('#addRemark').val('');
			$('#addBatchNumber').val('');
			$('#addOrderNumber').val(0);
			choosedProduct=[];	
			table.reload('productListTable',{
				data:choosedProduct
			})
		})
		
		
		//----添加新商品功能--------------
		$('#addNewProduct').on('click',function(){						
			openAddNewPorductWin();
		})

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
		function openChooseProductWin(){					//商品选择隐藏框
			chooseProductWin = layer.open({		
				type:1,
				title:'选择产品',
				area:['80%','70%'],
				content:$('#productChooseDiv'),
			})
			table.render({
				elem:'#productChooseTable',
				url:'${ctx}/inventory/commodityPage',
				loading:true,
				page:true,
				request:{
					pageName:'page',
					limitName:'size'
				},
				parseData:function(ret){	
					return{ code:ret.code, msg:ret.message, data:ret.data.rows, count:ret.data.total,}},
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'商品名称', field:'skuCode',},
				       {align:'center', title:'成本', 	  field:'cost',},
				       {align:'center', title:'备注', 	  field:'remark',}, 
				      ]],
			});
			form.render();
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
						$('#addOrderNumber').val($('#addOrderNumber').val()-(-1));
						break;
					}
				}
				if(!(j<choosedProduct.length) || choosedProduct.length==0){				//如果不存在
					var orderChild={
							skuCode:choosed[i].skuCode,			//商品名称
							commodityId:choosed[i].id,		//商品id
							number:0,						//商品数量，设置
							remark:choosed[i].remark,		//备注
							inventorys:choosed[i].inventorys,//库存情况
							
					};
					$('#addOrderNumber').val($('#addOrderNumber').val()-(-1));
					choosedProduct.push(orderChild);
				} 
			}
			table.reload('productListTable',{
				data:choosedProduct
			});
			layer.msg('添加成功',{icon:1});
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
		
		function getAllInventory(){
			$.ajax({
				url:'${ctx}/basedata/list?type=inventory',
				async:false,
				success:function(r){
					if(0==r.code){
						allInventory=r.data;
					}
				}
			})
		}
		function renderInventorySelect(select){
			var html='';
			if(allInventory.length==0){
				html='<option value="">暂无仓库可使用</option>';
			}else{
				for(var i=0;i<allInventory.length;i++){
					var t=allInventory[i],
					    disable = t.flag==1?'':'disabled';
					html+=('<option value="'+t.id+'" '+disable+'>'+t.name+'</option>');
				}
			}
			$('#'+select).html(html);
		}
		function getStatusSelectHtml(){				//获取类型下拉框
			return function(d) {		
				var html='<select id="selectStatus" lay-filter="selectStatus" lay-search="true" data-value="'+defaultStatus+'"> '+
						'<option value="0">销售出库</option>'+
						'<option value="1">调拨出库</option>'+
						'<option value="2">销售换货出库</option>'+
						'<option value="3">采购退货出库 </option>'+
						'</select>';
				return html;

			};
		}
		function getInventorySelectHtml() {				//获取仓库下拉框
			return function(d) {	
				var inventorys=d.inventorys;
				if(inventorys.length==0){
					return '没有库存数量';
				}
				choosedProduct[d.LAY_TABLE_INDEX].number=defaultNumber=='all'?inventorys[0].number:0;
				var value=str=inventorys[0].warehouse.id+'_'+inventorys[0].number;		//下拉框隐藏值，id_number
				var html='<select id="selectInventory" lay-filter="selectInventory" lay-search="true" data-value="'+value+'"> ';
				for(var i=0;i<inventorys.length;i++){
					var str=inventorys[i].warehouse.name+':'+inventorys[i].number;		//下拉框显示内容：仓库名：数量
					html+='<option value="'+inventorys[i].warehouse.id+'_'+inventorys[i].number+'">'+str+'</option>';
				}
				return html; 
			};
		};
		function splicStr(str,type){
			var r='';
			var index=str.indexOf('_');
			if(type=='before'){
				return str.substring(0,index);
			}else
				return str.substing(index+1);
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