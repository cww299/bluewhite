<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>针工单</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
td{
	text-align:center;
}
.layui-card .layui-table-cell{	
	  height:auto;
	 /*  overflow:visible; */
	  padding:0px;
}
.layui-card  .layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(0%);
}
.layui-table tbody tr:hover, .layui-table-hover {
	background-color: transparent;
}
</style>
</head>
<body>

<!-- 主页面 -->
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>批次号:</td>
				<td><input type="text" class="layui-input" name="batchNumber" placeholder='请输入批次号'></td>
				<td>&nbsp;&nbsp;</td>
				<td>商品名:</td>
				<td><input type="text" class="layui-input" name="commodityName" placeholder='请输入商品名'></td>
				<td>&nbsp;&nbsp;</td>
				<td><select name="flag"><option value="">是否反冲</option><option value="1">反冲</option><option value="0" selected>未反冲</option></select>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>
			</tr>
		</table>
		<table class="layui-table" id="needleOrderTable" lay-filter="needleOrderTable"></table>
	</div>
</div>

<!-- 查看订单隐藏框  -->
<div id="lookoverOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table"  lay-size="sm" lay-skin="nob">
		<tr><td>下单时间</td>	
			<td><input type="text" class="layui-input" readonly id="look_createdAt"></td>
			<td>经手人</td>
			<td><input type="text" class="layui-input" readonly id="look_user" val='无经手人'></td>
			<td>总数量</td>
			<td><input type="text" class="layui-input" id="look_number" readonly></td></tr>
		<tr><td>备注</td>
			<td colspan="3"><input type="text" id="look_remark" class="layui-input" readonly></td></tr>
	</table>
	<table class="layui-table" id="lookOverProductListTable" lay-filter="lookOverProductListTable"></table>
</div>
<!-- 添加订单隐藏框  -->
<div id="addOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table"  lay-size="sm" lay-skin="nob">
		<tr>
			<td>下单时间 <input type="hidden" name="type" value="1" ></td> <!-- 默认type类型为1，表示为针工单 -->
			<td><input type="text" class="layui-input" name="createdAt" id="addCreatedAt"></td>
			<td>经手人</td>
			<td><select name="userId" id='userIdSelectAdd' lay-search><option value="1" >获取数据中...</option></select></td>
			<td>数量</td>
			<td><input type="text" class="layui-input" name="number" id="addNumber" readonly value='0'></td></tr>
		<tr>
			<td>默认批次号</td>	
			<td><input type="text" class="layui-input" name="batchNumber"  id="addBatchNumber"></td>
			<td>备注</td>
			<td><input type="text" name="remark" class="layui-input" id="addRemark"></td>
			<td colspan="2" style="text-align:right;"><span class="layui-btn" lay-submit lay-filter="sureAdd" >确定新增</span>
							<span class="layui-btn layui-btn-danger" id='resetAddOrder' >清空数据</span> </td></tr>
	</table>
	<table class="layui-table" id="productListTable" lay-filter="productListTable"></table>
</div>
<!-- 生成入库单隐藏框  -->
<div id="becomeOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table" lay-size="sm" lay-skin="nob">
		<tr><td>下单时间<input type="hidden" name="type" value="2" >
					  <input type="hidden" name="id" id='becomeOrderId' ></td>	<!-- 默认type类型为2，表示为入库单 -->
			<td><input type="text" class="layui-input" name="createdAt" id="becomeCreatedAt"  ></td>
			<td>经手人</td>
			<td><select name="userId" id='userIdSelect'><option value="1" >获取数据中...</option></select></td>
			<td>备注</td>
			<td colspan="3"><input type="text" name="remark" class="layui-input"></td></tr>
		<tr>
			<td>默认转单数量</td>
			<td><select lay-filter="defaultSelect" type='number'><option value="zero">默认生成入库单数量为0</option><option value="all">全部生成入库单</option></select>
			<td>默认入库仓库</td>
			<td><select lay-filter="defaultSelect" type='inventory' id='defaultInventorySelect'><option value="">获取数据中.....</option></select></td>
			<td>默认入库类型</td>
			<td><select lay-filter="defaultSelect" type='status' name='status' disabled>
						<option value="0">生产入库</option>
						<option value="1">调拨入库</option>
						<option value="2">销售退货入库</option>
						<option value="3">销售换货入库 </option>
						<option value="4">采购入库</option></select></td>
			<td>操作</td>
			<td><span class="layui-btn" lay-submit lay-filter="sureBecome" >确定</span></td></tr>
	</table>
	<table class="layui-table" id="becomeProductListTable" lay-filter="becomeProductListTable"></table>
</div>

<!-- 商品选择隐藏框 -->
<div id="productChooseDiv" style="display:none;">
	<table class="layui-form layui-table" lay-size="sm" lay-skin="nob"  style="width:60%;">
		<tr>
			<td><select><option value="1">按商品名称查找</option></select></td>			
			<td><input type="text" class="layui-input" name="skuCode" placeholder="请输入查找的信息"></td>				
			<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" >搜索</button>				
				<button type="button" class="layui-btn layui-btn-sm" id="sure" >确定添加</button>
				<button type="button" class="layui-btn layui-btn-sm" id="addNewProduct" >添加新商品</button>	</td>
		</tr>
	</table>
	<table class="layui-table" id="productChooseTable" lay-filter="productChooseTable"></table>
</div>


<!-- 添加新商品隐藏框 -->
<form class="layui-form layui-table" style="display:none;" id="addNewProductWin">
<table style="width:100%;">
	<tr><td>商品名称</td>
		<td><input type="text" class="layui-input" lay-verify="required"	name="skuCode"></td>
		<td>1688批发价/元</td>
		<td><input type="text" class="layui-input" 		name="OSEEPrice"></td></tr>
	<tr><td>天猫单价/元</td>
		<td><input type="text" class="layui-input" 		name="tianmaoPrice"> </td>
		<td>线下批发价/元</td>
		<td><input type="text" class="layui-input" 		name="offlinePrice"></td></tr>
	<tr><td>商品重量/g</td>
		<td><input type="text" class="layui-input" 		name="weight" ></td>
		<td>商品高度/cm</td>
		<td><input type="text" class="layui-input" 		name="size" ></td></tr>
	<tr><td>商品成本/元</td>
		<td><input type="text" class="layui-input" 		name="cost"></td>
		<td>广宣成本/元</td>
		<td><input type="text" class="layui-input" 		name="propagandaCost" ></td></tr>
	<tr><td>商品填充物</td>
		<td><input type="text" class="layui-input" name="fillers"></td>
		<td>商品材质</td>
		<td><input type="text" class="layui-input" name="material"></td></tr>
	<tr><td>备注</td>
		<td colspan="3"><textarea type="text" class="layui-input" name="remark"></textarea></td></tr>
	<tr><td colspan="4"><button type="reset"   class="layui-btn layui-btn-sm layui-btn-danger">清空</button>
						<button type="button"  class="layui-btn layui-btn-sm"  lay-submit lay-filter="sureAddNew">确定</button></td></tr>
</table>
</form>

<!-- 商品列表表格工具栏 -->
<script type="text/html" id="productListTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增商品</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除商品</span>
</div>
</script>
<!-- 入库单表格工具栏 -->
<script type="text/html" id="needleOrderTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="addNeedle"  class="layui-btn layui-btn-sm" >新增针工单</span>
	<span lay-event="becomeEntry"  class="layui-btn layui-btn-sm" >生成入库单</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >一键反冲</span>
	<span class="layui-badge" >小提示：双击查看详细信息</span>
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
	['tablePlug','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, tablePlug = layui.tablePlug;
		
		var allInventory=[],
			allUser=[],
		    currUser={id:''};      
		
		getAllInventory();
		getAllUser();
		getCurrUser();
		renderDefaultInventorySelect();
		
		form.render();
		
		table.render({				//渲染主页面入库单表格
			elem:'#needleOrderTable',
			url:'${ctx}/inventory/procurementPage?type=1&flag=0',
			toolbar:'#needleOrderTableToolbar',
			loading:true,
			page:{},
			request:{pageName:'page',limitName:'size'},
			parseData:function(ret){
				return {data:ret.data.rows,count:ret.data.total,msg:ret.message,code:ret.code}},
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'单据编号',   	field:'documentNumber',	},
			       {align:'center', title:'计划数量', 	field:'number',			width:'6%',	},
			       {align:'center', title:'剩余数量', 	field:'residueNumber',	width:'6%',	},
			       {align:'center', title:'经手人',templet:'<p>{{ d.user.userName }}</p>',width:'8%',},
			       {align:'center', title:'是否反冲', 	field:'flag', templet:'#flagTpl',width:'6%',},
			       {align:'center', title:'日期',   	field:'createdAt',	},
			       {align:'center', title:'批次号',	templet: orderContentBatchNumber(), width:'10%'	,},
					{align:'center', title:'商品名',	templet: orderContentName(),width:'20%'	,},
					{align:'center', title:'剩余数量',templet: orderContentNumber(), width:'5%'	,},
			       {align:'center', title:'备注', 	templet: orderContentRemark(), width:'10%'	,	},
			       ]]
		})
		function orderContentRemark(){
			return function(d){
				var html='<table style="width:100%;" class="layui-table">';
				for(var i=0;i<d.procurementChilds.length;i++){
					var t=d.procurementChilds[i];
					var style='';
					if(i==d.procurementChilds.length-1)
						style='border-bottom:none';
					html+='<tr><td style="border-right:none; '+style+'">'+t.childRemark+'&nbsp;</td></tr>';
				}
				return html+'</table>';
			}
		}
		function orderContentBatchNumber(){
			return function(d){
				var html='<table style="width:100%;" class="layui-table">';
				for(var i=0;i<d.procurementChilds.length;i++){
					var t=d.procurementChilds[i];
					var style='';
					if(i==d.procurementChilds.length-1)
						style='border-bottom:none';
					html+='<tr><td style="border-right:none; '+style+'">'+t.batchNumber+'&nbsp;</td></tr>';
				}
				return html+'</table>';
			}
		}
		function orderContentName(){
			return function(d){
				var html='<table style="width:100%;" class="table-noclickline">';
				for(var i=0;i<d.procurementChilds.length;i++){
					var t=d.procurementChilds[i];
					var style='';
					if(i==d.procurementChilds.length-1)
						style='border-bottom:none';
					html+='<tr><td style="border-right:none; '+style+'">'+t.commodity.skuCode+'&nbsp;</td></tr>';
				}
				return html+'</table>';
			}
		}
		function orderContentNumber(){
			return function(d){
				var html='<table style="width:100%;" class="table-noclickline">';
				for(var i=0;i<d.procurementChilds.length;i++){
					var t=d.procurementChilds[i];
					var style='';
					if(i==d.procurementChilds.length-1)
						style='border-bottom:none';
					html+='<tr><td style="border-right:none; '+style+'">'+t.residueNumber+'&nbsp;</td></tr>';
				}
				return html+'</tbody></table>'; 
			}
		}
		table.on('toolbar(needleOrderTable)',function(obj){	//监听入库单表格按钮
			switch(obj.event){
			case 'delete':		deletes();		break;
			case 'becomeEntry':becomeEntry(); break;
			case 'addNeedle':	addNeedle();	break;
			}
		})
		
		table.on('rowDouble(needleOrderTable)',function(obj){
			lookover(obj.data);
		})
		form.on('submit(search)',function(obj){
			table.reload('needleOrderTable',{
				where:obj.field,
				page: {  curr: 1   },
				url:'${ctx}/inventory/procurementPage?type=1',
			})
		})
		//--------------新增针工单------------
		laydate.render({ elem:'#addCreatedAt', type:'datetime' });
		var choosedProduct=[];			//用户已经选择上的产品
		function addNeedle(){
			layer.open({
				type : 1,
				title : '新增针工单',
				offset:'30px',
				area : ['90%','90%'],
				content : $('#addOrderDiv')
			})
			getUserSelect(currUser.id,'userIdSelectAdd');
			table.render({									//渲染选择后的商品表格
				elem:'#productListTable',
				toolbar:'#productListTableToolbar',
				loading:true,
				page:{},
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'批次号',   field:'batchNumber', edit:true,style:'color:blue', },
				       {align:'center', title:'商品名称', field:'skuCode',},
				       {align:'center', title:'数量',     field:'number', edit:true,style:'color:blue', },
				       {align:'center', title:'备注',  	  field:'childRemark', edit:true,style:'color:blue', }, 
				       ]]
			})
			table.reload('productListTable',{ data : choosedProduct });
		}
		$('#addBatchNumber').change(function(){			//默认批次号改变
			for(var i=0;i<choosedProduct.length;i++){
				choosedProduct[i].batchNumber=$('#addBatchNumber').val();
			}
			table.reload('productListTable',{ data : choosedProduct });
		})
		table.on('toolbar(productListTable)',function(obj){		//监听选择商品表格的工具栏按钮
			switch(obj.event){
			case 'add': openChooseProductWin(); break;
			case 'delete':deleteChoosedProduct();break;
			}
		})
		table.on('edit(productListTable)', function(obj){ 			//监听编辑表格单元
			if(obj.field=='number'){
				var msg='';
				if(isNaN(obj.value))
					msg="修改无效！请输入正确的数字";
				else if(obj.value=='')
					msg='计划的数量不能为空';
				else if(obj.value<0)
					msg='计划的数量不能小于0';
				else if(obj.value%1 !== 0)
					msg='计划的数量必须为整数';
				for(var i=0;i<choosedProduct.length;i++){
					if(choosedProduct[i].id==obj.data.id){		
						if(msg!=''){
							layer.msg('"'+obj.value+'" 为非法输入！'+msg,{icon:2,offset:'100px',});
							$(this).val(1)
							choosedProduct[i].number=1;
							return;
						}
						$('#addNumber').val($('#addNumber').val()-choosedProduct[i].number-(-parseInt(obj.value)));
						$(this).val(parseInt(obj.value))
						choosedProduct[i].number=parseInt(obj.value);
						return;
					}
				}
			}else{
				for(var i=0;i<choosedProduct.length;i++){
					 if(choosedProduct[i].id==obj.data.id){		//重新对该行的相关数据进行计算
						if(obj.field=='childRemark')
						 	choosedProduct[i].childRemark=obj.data.childRemark;
						else if(obj.field=='batchNumber')
							choosedProduct[i].batchNumber=obj.data.batchNumber;
					 	layer.msg('修改成功！',{icon:1,offset:'100px',});
					 	break;
					}
				}
			}
		});
		form.on('submit(sureAdd)',function(obj){					//确定添加生产单
			var data=obj.field;
			if(choosedProduct.length==0){
				layer.msg("请选择商品",{icon:2,offset:'100px',});
				return;
			}
			var child=[];
			for(var i=0;i<choosedProduct.length;i++){
				console.log(choosedProduct[i].batchNumber)
				if(choosedProduct[i].batchNumber.replace(/(^\s*)|(\s*$)/g, '')==''){
					layer.msg('商品批次号不可省略！',{icon:2,offset:'100px',});
					return;
				}
				for(var j=0;j<choosedProduct.length;j++){
					var item = choosedProduct[j];
					if( item.id != choosedProduct[i].id && item.commodityId == choosedProduct[i].commodityId && item.batchNumber == choosedProduct[i].batchNumber){
						layer.msg('相同的商品不能同时使用相同的批次号！',{icon:2,offset:'100px',});
						return;
					}
				}
				child.push({batchNumber : choosedProduct[i].batchNumber.replace(/(^\s*)|(\s*$)/g, ''),
							commodityId : choosedProduct[i].commodityId,
							number : choosedProduct[i].number,
							childRemark : choosedProduct[i].childRemark});
			}
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
						table.reload('needleOrderTable');
						layer.msg(result.message,{icon:1,offset:'100px',});
					}else{
						layer.msg(result.message,{icon:2,offset:'100px',});
					}
					layer.close(load);
				},
				error:function(){
					layer.msg("服务器异常",{icon:2,offset:'100px',});
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
			$('#addCreatedAt').val('');
			$('#addNumber').val(0);
			choosedProduct=[];	
			getUserSelect(currUser.id,'userIdSelectAdd');
			table.reload('productListTable',{ data:choosedProduct })
		})
		form.on('submit(searchProduct)',function(obj){
			table.reload('productChooseTable',{
				where:obj.field,
				page: {  curr: 1   }
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
						layer.msg(result.message,{icon:1,offset:'100px',});
					}
					else
						layer.msg(result.message,{icon:2,offset:'100px',});
					layer.close(load);
				},
				error:function(result){
					layer.msg('发生未知错误',{icon:2,offset:'100px',});
					layer.close(load);
				}
			})
		})
		function deleteChoosedProduct(){								//删除商品
			var choosed = layui.table.checkStatus('productListTable').data;
			if(choosed.length==0){
				layer.msg("请选择商品删除",{icon:2,offset:'100px',});
				return;
			}
			for(var i=0;i<choosed.length;i++){
				for(var j=0;j<choosedProduct.length;j++){
					if(choosed[i].id==choosedProduct[j].id){
						$('#addNumber').val($('#addNumber').val()-choosedProduct[j].number);
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
				offset:'60px',
				area:['80%','80%'],
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
				       {align:'center', title:'广宣成本', 	  field:'propagandaCost',}, 
				      ]],
			});
			form.render();
		}
		var choosedId=0;	//由于可以选择不同的商品进行不同的批次号选择，因此原本的商品id无法作为唯一标识，因此相同的商品可能多次选择，因此需要添加一个字段作为标识
		function sureChoosed(){					//确定商品选择
			var choosed=layui.table.checkStatus('productChooseTable').data;
			if(choosed.length<1){
				layer.msg("请选择相关商品",{icon:2,offset:'100px',});
				return false;
			}
			for(var i=0;i<choosed.length;i++){
				var orderChild={
						skuCode : choosed[i].skuCode,			//商品名称
						commodityId : choosed[i].id,			//商品id
						number : 1,								//商品数量
						cost : choosed[i].cost,					//成本
						remark : choosed[i].remark,				//备注
						batchNumber : $('#addBatchNumber').val(),
						id : choosedId++,  						//仅仅用于标识不同的数据
				};
				$('#addNumber').val($('#addNumber').val()-(-1));
				choosedProduct.push(orderChild);
			}
			table.reload('productListTable',{ data:choosedProduct });
			layer.msg('添加成功',{icon:1,offset:'100px',});
			return true;
		}
		function openAddNewPorductWin(){		//添加新产品窗口
			addNewPorductWin = layer.open({								
				type:1,
				title:'添加产品',
				offset:'100px',
				content:$('#addNewProductWin'),
				area:['60%','60%']
			})
			form.render();
		}
		//------------反冲针工单----------------------------
		function deletes(){							//反冲针工单表格
			var choosed=layui.table.checkStatus('needleOrderTable').data;
			if(choosed.length<1){
				layer.msg('请选择生产单',{icon:2,offset:'100px',});
				return;
			}
			layer.confirm('是否确认反冲？',{offset:'100px',},function(){
				var ids='';
				for(var i=0;i<choosed.length;i++)
					ids+=(choosed[i].id+',');
				var load=layer.load(1);
				$.ajax({
					url:'${ctx}/inventory/deleteProcurement?ids='+ids,
					success:function(result){
						if(0==result.code){
							table.reload('needleOrderTable');
							layer.msg(result.message,{icon:1,offset:'100px',});
						}else
							layer.msg(result.message,{icon:2,offset:'100px',});
						layer.close(load);
					}
				})
			})
		}
		
		//-------生成入库单功能---------------
		laydate.render({ elem:'#becomeCreatedAt', type:'datetime' })
		var becomeProduct=[];
		var defaultBecomeNumber='zero';	//默认转单数量
		var defaultInventory='';
		var defaultStatus=0;
		function becomeEntry(){
			defaultInventory=defaultInventory==''?allInventory[0].id:defaultInventory;
			becomeProduct=[];			//清空之前的数据
			$('#becomeOrderId').val('');
			var choosed = layui.table.checkStatus('needleOrderTable').data;
			if(choosed.length<1){
				layer.msg('请选择信息',{icon:2,offset:'100px',});
				return;
			}
			if(choosed.length>1){
				layer.msg('无法同时使用多条信息生成入库单',{icon:2,offset:'100px',});
				return;
			}
			if(choosed[0].flag==1){
				layer.msg('已反冲的数据无法进行转换',{icon:2,offset:'100px',});
				return;
			}
			$('#becomeOrderId').val(choosed[0].id);		//设置被转成针工单的生产单id
			layer.open({
				type : 1,
				title:'生成入库单',
				offset:'30px',
				area : ['90%','90%'],
				content:$('#becomeOrderDiv'),
			})
			table.render({									//渲染选择后的商品表格
				elem:'#becomeProductListTable',
				page:{},
				size:'lg',
				loading:true,
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'批次号',   field:'batchNumber',},
				       {align:'center', title:'商品名称', templet:'<p>{{ d.commodity.skuCode }}</p>',},
				       {align:'center', title:'商品数量', field:'number', },
				       {align:'center', title:'剩余数量', field:'residueNumber'},
				       {align:'center', title:'入库仓库',  	  field:'warehouseId', 	templet: getInventorySelectHtml()}, 
				       {align:'center', title:'入库类型',  	  field:'status', 		templet: getStatusSelectHtml()}, 
				       {align:'center', title:'仓位',  	 	  field:'place', 				 edit:true, style:'color:blue',}, 
				       {align:'center', title:'生成入库单数量',    field:'becomeNumber', 	 edit:true, style:'color:blue',},
				       {align:'center', title:'入库单备注',  	  field:'becomeChildRemark', edit:true, style:'color:blue',}, 
				       ]],
		       	done: function (res, curr, count) {				
	                form.render(); 
	            },
			})
			becomeProduct=choosed[0].procurementChilds;				//回显该订单的子订单
			for(var i=0;i<becomeProduct.length;i++){				//设置默认值
				becomeProduct[i].becomeNumber=(defaultBecomeNumber=='zero'?0:becomeProduct[i].residueNumber);
				becomeProduct[i].warehouseId=defaultInventory;
				becomeProduct[i].status=defaultStatus;
			}
			$('#become_bacthNumber').val(choosed[0].batchNumber);
			getUserSelect(currUser.id,'userIdSelect');		
			table.reload('becomeProductListTable',{
				data:becomeProduct
			})
		}
	
		form.on('select(selectInventory)', function (data) {
            var elem = $(data.elem);
            var trElem = elem.parents('tr');
            var tableData = table.cache['becomeProductListTable'];
            becomeProduct[trElem.data('index')]['warehouseId'] = data.value;
        });
		
		form.on('select(defaultSelect)',function(obj){
			switch(obj.elem.getAttribute('type')){
			case 'number': defaultBecomeNumber=obj.value;  
							for(var i=0;i<becomeProduct.length;i++)			
								becomeProduct[i].becomeNumber=(defaultBecomeNumber=='zero'?0:becomeProduct[i].residueNumber);
							break;
			case 'inventory' : defaultInventory=obj.value;    
							for(var i=0;i<becomeProduct.length;i++)
								becomeProduct[i].warehouseId=defaultInventory;
							break;
			case 'status' : defaultStatus=obj.value; 		
							for(var i=0;i<becomeProduct.length;i++)
								becomeProduct[i].status=defaultStatus;
							break;
			}
			table.reload('becomeProductListTable',{data:becomeProduct});
		})
		
		form.on('submit(sureBecome)',function(obj){
			var choosed = layui.table.checkStatus('becomeProductListTable').data;
			if(choosed.length<1){
				layer.msg('请勾选需要生成入库单的信息',{icon:2,offset:'100px',});
				return;
			}
			var c=[];       //用于存放提取真正需要的数据
			var allNum=0;
			for(var i=0;i<choosed.length;i++){
				var t=choosed[i];					
				if(t.becomeNumber==0){			
					layer.msg('转单的数量不能为0，请检查是否错勾选或填写有误！',{icon:2,offset:'100px',});
					return;
				}
				allNum+=t.becomeNumber;
				c.push({																//如果没有选择或者设置值，则为默认值
					batchNumber : 	t.batchNumber,
					commodityId : 	t.commodity.id,
					number : 		t.becomeNumber,
					warehouseId : 	t.warehouseId,
					status : 		t.status,
					place : 		t.place==undefined ? '' : t.place,
					childRemark : 	t.becomeChildRemark==undefined ? '' : t.becomeChildRemark
				})
			}
			var data=obj.field;						//订单的基本数据，在form表单中
			data.number=allNum;						//订单的总数量
			data.commodityNumber=JSON.stringify(c);	//订单的子订单
			var load = layer.load(1);
			$.ajax({
				url:"${ctx}/inventory/addProcurement",
				type:"post",
				data:data,			
				success:function(result){
					if(0==result.code){
						layer.closeAll();
						table.reload('needleOrderTable');
						layer.msg(result.message,{icon:1,offset:'100px',});
					}else{
						layer.msg(result.message,{icon:2,offset:'100px',});
					}
					layer.close(load);
				},
				error:function(){
					layer.msg("服务器异常",{icon:2});
					layer.close(load);
				}
			})
		})
		table.on('edit(becomeProductListTable)', function(obj){ 			//监听编辑表格单元
			if(obj.field=='becomeNumber'){
				if(isNaN(obj.value)){
					layer.msg("修改无效！请输入正确的数字",{icon:2,offset:'100px',});
				}else if(obj.value=='')
					layer.msg('转成入库单的数量不能为空',{icon:2,offset:'100px',});
				else if(obj.value<0)
					layer.msg('转成入库单的数量不能小于0',{icon:2,offset:'100px',});
				else if(obj.value%1 !== 0)
					 layer.msg('转成入库单的数量必须为整数',{icon:2,offset:'100px',});
				else{
					for(var i=0;i<becomeProduct.length;i++){
						 if(becomeProduct[i].id==obj.data.id){		//重新对该行的相关数据进行计算
							 if(obj.value>becomeProduct[i].residueNumber)
								 layer.msg('转成入库单的数量不能大于剩余数量',{icon:2,offset:'100px',});
							 else{
							 	 becomeProduct[i].becomeNumber=parseInt(obj.value);
							 }
						 	break;
						}
					}
				}
			}else{
				for(var i=0;i<becomeProduct.length;i++){
					 if(becomeProduct[i].id==obj.data.id){		//重新对该行的相关数据进行计算
					 	if(obj.field=='becomeChildRemark')
							 becomeProduct[i].becomeChildRemark=obj.data.becomeChildRemark;
						else
							becomeProduct[i].place = obj.data.place;
					 	break;
					}
				}
			}
			table.reload('becomeProductListTable',{
				data:becomeProduct
			})
		});
		//-------查看针工单功能--------------------
		function lookover(data){
			if(data.type==undefined)				//防止空数据弹窗bug，双击详细内容时
				return;
			layer.open({
				type : 1,
				title : '查看针工单',
				offset:'30px',
				area : ['90%','90%'],
				content : $('#lookoverOrderDiv')
			})
			table.render({									//渲染选择后的商品表格
				elem:'#lookOverProductListTable',
				data:data.procurementChilds,
				page:{},
				loading:true,
				cols:[[
					   {align:'center', title:'批次号',   	field:'batchNumber',	},
				       {align:'center', title:'商品名称',  templet:'<p>{{ d.commodity.skuCode }}</p>'},
				       {align:'center', title:'数量',     field:'number',},
				       {align:'center', title:'剩余数量', field:'residueNumber'},
				       {align:'center', title:'备注', 	  field:'childRemark',}, 
				       ]]
			})
			$('#look_createdAt').val(data.createdAt);
			$('#look_remark').val(data.remark);
			$('#look_number').val(data.number);
			$('#look_user').val(data.user.userName);
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
		function getAllUser(){
			$.ajax({
				url:'${ctx}/system/user/pages?size=999',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allUser.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].userName
							})
					}
				}
			})
		}
		function getUserSelect(id,select){
			var html='';
			for(var i=0;i<allUser.length;i++){
				var selected=( id==allUser[i].id?'selected':'' );
				html+='<option value="'+allUser[i].id+'" '+selected+'>'+allUser[i].userName+'</option>';
			}
			$('#'+select).html(html);
			form.render();
		}
		
		function renderDefaultInventorySelect(){
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
			$('#defaultInventorySelect').html(html);
		}
		function getStatusSelectHtml(){				//获取类型下拉框
			return function(d) {		
				var html='<select disabled > ';
					html+=	'<option value="0"  '+ (d.status==0?"selected":"") +'>生产入库</option>';
					html+=	'<option value="1"  '+ (d.status==1?"selected":"") +'>调拨入库</option>';
					html+=	'<option value="2"  '+ (d.status==2?"selected":"") +'>销售退货入库</option>';
					html+=	'<option value="3"  '+ (d.status==3?"selected":"") +'>销售换货入库 </option>';
					html+=	'<option value="4"  '+ (d.status==4?"selected":"") +'>采购入库</option>';
					html+=	'</select>';
				return html;

			};
		}
		function getInventorySelectHtml() {				//获取仓库下拉框
			return function(d) {		
				if(allInventory.length==0){
					return '没有可用仓库';
				}
				var html='<select id="selectInventory" lay-filter="selectInventory"  > ';
				for(var i=0;i<allInventory.length;i++){
					var disable = allInventory[i].flag==1?'':'disabled';
					var selected = d.warehouseId==allInventory[i].id?'selected':'';
					html+='<option value="'+allInventory[i].id+'" '+disable+' '+selected+'>'+allInventory[i].name+'</option>';
				}
				return html; 
			};
		};
		function getCurrUser(){
			$.ajax({
				url:'${ctx}/getCurrentUser',
				success:function(r){
					if(0==r.code)
						currUser = r.data;
				}
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