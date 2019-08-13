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
td{
	text-align:center;
}
.layui-card .layui-table-cell{	
	  height:auto;
	  /* overflow:visible; */
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
				<td>单据编号:</td>
				<td><input type="text" class="layui-input" name="documentNumber" placeholder='请输入单据编号'></td>
				<td>&nbsp;&nbsp;</td>
				<td>商品名:</td>
				<td><input type="text" class="layui-input" name="commodityName" placeholder='请输入商品名'></td>
				<td>&nbsp;&nbsp;</td>
				<td><select name="flag"><option value="">是否反冲</option><option value="1">反冲</option><option value="0" selected>未反冲</option></select>
				<td>&nbsp;&nbsp;</td>
				<td><select name='status'>
						<option value="">入库类型</option>
						<option value="0">生产入库</option>
						<option value="1">调拨入库</option>
						<option value="2">销售退货入库</option>
						<option value="3">销售换货入库 </option>
						<option value="4">采购入库</option>
						<option value="5">盘亏入库</option></select></td>
						<td>&nbsp;&nbsp;</td>
				<td><select name='confirm'>
						<option value="">是否审核</option>
						<option value="1">审核</option>
						<option value="0">未审核</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>
			</tr>
		</table>
		<table class="layui-table" id="entryOrderTable" lay-filter="entryOrderTable"></table>
	</div>
</div>

<!-- 添加订单隐藏框  -->
<div id="addOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table" lay-size="sm" lay-skin="nob">
		<tr><td>下单时间<input type="hidden" name="type" value="2" ></td>	<!-- 默认type类型为2，表示为入库单 -->
			<td><input type="text" class="layui-input" name="createdAt" id="addCreatedAt"></td>
			<td>经手人</td>
			<td><select name="userId" id='userIdSelect' lay-search><option value="1" >获取数据中...</option></select></td>
			<td>备注</td>
			<td colspan=""><input type="text" name="remark" id="addRemark" class="layui-input"></td>
			<td>入库数量</td>
			<td><input type="text" class="layui-input" name='number' id="addOrderNumber" value='0' readonly></td></tr>
		<tr>
			<td>默认入库仓库</td>
			<td><select lay-filter="defaultSelect" type='inventory' id='defaultInventorySelect'><option value="">获取数据中.....</option></select></td>
			<td>入库类型</td>
			<td><select lay-filter="defaultSelect" type='status' id="entryTypeSelect">
						<option value="0">生产入库</option>
						<option value="1">调拨入库</option>
						<option value="2">销售退货入库</option>
						<option value="3">销售换货入库 </option>
						<option value="4">采购入库</option>
						<option value="5">盘亏入库</option></select></td>
			<td id='textTd'></td>
			<td id='selectTd' style='width:280px;'></td>
			<td colspan="2" style="text-align:right;"><span class="layui-btn" lay-submit lay-filter="sureAdd" >确定新增</span>
						<span class="layui-btn layui-btn-danger" id='resetAddOrder' >清空数据</span></td></tr>
	</table>
	<div id="tableDiv" style="display:none;">
		<table class="layui-table" id="productListTable" lay-filter="productListTable"></table>
	</div>
	<div id="tableDiv1">
		<table class="layui-table" id="productListTable1" lay-filter="productListTable1"></table>
	</div>
</div>

<!-- 查看订单隐藏框  -->
<div id="lookoverOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table"  lay-size="sm" lay-skin="nob">
		<tr><td>下单时间</td>	
			<td><input type="text" class="layui-input" readonly id="look_createdAt"></td>
			<td>经手人</td>
			<td><input type="text" class="layui-input" readonly id="look_user"></td>
			<td>总数量</td>
			<td><input type="text" class="layui-input" id="look_number" readonly></td></tr>
		<tr><td>备注</td>
			<td><input type="text" id="look_remark" class="layui-input" readonly></td>
			<td>入库类型</td>
			<td><input type="text" class="layui-input" id="look_status" readonly></td>
			<td id='look_textTd'></td>
			<td id='look_inputTd' style='width:280px;'></td></tr>
	</table>
	<table class="layui-table" id="lookOverProductListTable" lay-filter="lookOverProductListTable"></table>
</div>


<!-- 商品选择隐藏框 -->
<div id="productChooseDiv" style="display:none;">
	<table class="layui-form layui-table" lay-size="sm" lay-skin="nob" style='width:60%;'>
		<tr>
			<td><select><option value="1">按产品名称查找</option></select></td>		
			<td><input type="text" class="layui-input" name="skuCode" placeholder="请输入查找的信息"></td>	
			<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" >搜索</button>				
				<button type="button" class="layui-btn layui-btn-sm" id="sure" >确定添加</button>
				<button type="button" class="layui-btn layui-btn-sm" id="addNewProduct" >添加新商品</button></td>	
		</tr>
	</table>
	<table class="layui-table" id="productChooseTable" lay-filter="productChooseTable"></table>
</div>

<!-- 入库单表格工具栏 -->
<script type="text/html" id="entryOrderTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增入库单</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >一键反冲</span>
	<span lay-event="isVerify"  class="layui-btn layui-btn-sm" >一键审核</span>
	<span class="layui-badge" >小提示：双击查看详细信息</span>
</div>
</script>

<!-- 商品列表表格工具栏 -->
<script type="text/html" id="productListTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >添加商品</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除商品</span>
</div>
</script>

<!-- 是否反冲转换模板 -->
<script type="text/html" id="flagTpl">
	{{# var color=d.flag==1?'':'green',msg=d.flag==1?'反冲数据':'未反冲';}}
	<span class="layui-badge layui-bg-{{ color }}">{{ msg }}</span>
</script>

<!-- 入库单查看类型转换模板 -->
<script type="text/html" id='statusTpl'>
	{{#	var text='',color='';
		switch(d.status){
		case 0: text='生产入库'; 	color='blue'; break;
		case 1: text='调拨入库'; 	color='cyan'; break;
		case 2: text='销售退货入库'; color='green'; break;
		case 3: text='销售换货入库'; color='gray'; break;
		case 4: text='采购入库'; 	color='orange'; break;
		}
	}}	
	<span class='layui-badge layui-bg-{{ color }}'>{{text}}</span>
</script>


<!-- 商品销售属性模板 -->
<script type="text/html" id="saleAttributeTpl">
	{{# var str='';
		if(d.size!=null)
			str+='高度：'+d.size+'cm   ';
		if(d.weight!=null)
			str+='重量：'+d.weight+'g';
	}}
	<span style='color:blue;'>{{ str }}</span>
</script>
<!-- 商品库存情况模板 -->
<script type="text/html" id="inventoryTpl">
	{{# var inv=d.inventorys;
		var str='';
		var color='red';
		if(inv.length>0){
			for(var i=0;i<inv.length;i++){
				str+=inv[i].warehouse.name+':'+inv[i].number+'  ';
			}
			color='green';
		}else
			str='暂无库存';
	}}
	<span style='color:{{ color }};'>{{ str }}</span>
</script>
<!-- 商品价格模板 -->
<script type="text/html" id="priceTpl">
	{{# var str='';
		str+='天猫价格:'+d.tianmaoPrice+' ';
		str+='1688价格:'+d.oseePrice+' ';
		str+='线下价格:'+d.offlinePrice;
	}}
	<span style='color:orange;'>{{ str }}</span>
</script>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	addNew : 'layui/myModules/addNewProduct' ,
}).use(['tablePlug','laydate','addNew'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, addNew = layui.addNew
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		myutil.config.msgOffset = '250px';	
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();	
		
		var chooseProductWin,		//选择商品弹窗
			addNewPorductWin;
		var allInventory=[], allUser=[], allCustom=[], allUserOrg=[], currUser={ id:'' };    
		
		var searchBatchNumber = '';			//记录搜索使用的数据，用于过滤筛选子单数据
		var searchCommodityName = '';
		getData();
		renderInventorySelect('defaultInventorySelect');
		
		form.render();
		table.render({				//渲染主页面单表格
			elem:'#entryOrderTable',
			url:'${ctx}/inventory/procurementPage?type=2&flag=0',
			toolbar:'#entryOrderTableToolbar',
			page:{},
			request:{pageName:'page',limitName:'size'},
			parseData:function(ret){ return {data:ret.data.rows,count:ret.data.total,msg:ret.message,code:ret.code}},
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'单据编号',   	field:'documentNumber',width:'10%',	},
			       {align:'center', title:'计划数量', field:'number', width:'4%',	},
			       {align:'center', title:'剩余数量', field:'residueNumber', width:'4%',},
			       {align:'center', title:'经手人',	templet:'<p>{{ d.user.userName }}</p>',width:'4%',	},
			       {align:'center', title:'入库类型', templet:'#statusTpl',width:'6%',	},
			       {align:'center', title:'是否反冲', 	field:'flag', templet:'#flagTpl',width:'4%',	},
			       {align:'center', title:'日期',   	field:'createdAt',	width:'9%',},
			       {align:'center', title:'批次号',	  templet: orderContent('batchNumber'),   width:'10%'	,},
				   {align:'center', title:'商品名',	  templet: orderContent('skuCode'),		  width:'18%'	,},
			       {align:'center', title:'计划数量',  templet: orderContent('number'),		  width:'4%',	},
				   {align:'center', title:'剩余数量',  templet: orderContent('residueNumber'), width:'4%'	,},
				   {align:'center', title:'备注', 	  templet: orderContent('childRemark'),   	},
			       ]]
		})
		function orderContent(field){
			return function(d){
				var html = '<table style="width:100%;" class="layui-table">';
				for(var i=0;i<d.procurementChilds.length;i++){
					var t=d.procurementChilds[i];
					var style = 'border-right:none;';
					if(t.batchNumber.indexOf(searchBatchNumber)==-1 || t.commodity.skuCode.indexOf(searchCommodityName)==-1)
						continue;
					if(i==d.procurementChilds.length-1)
						style += 'border-bottom:none;';
					if(field == 'childRemark')
						style += 'text-align:left;';
					html+='<tr><td style="'+style+'">'+ (field == 'skuCode' ? t.commodity[field] : t[field]) +'&nbsp;</td></tr>';
				}
				return html+'</table>';
			}
		}
		
		table.on('toolbar(entryOrderTable)',function(obj){	//监听单表格按钮
			switch(obj.event){
			case 'add':			add();			break;
			case 'delete':		deletes();		break;
			case 'isVerify':
				var choosed = table.checkStatus('entryOrderTable').data;
				if(choosed.length<1)
					return myutil.emsg('请选择信息');
				var ids = [];
				layui.each(choosed,function(index,item){
					ids.push(item.id);
				})
				myutil.deleteAjax({
					url: '/inventory/auditProcurement',
					ids: ids.join(','),
				})
				break;
			}
		})
		
		table.on('rowDouble(entryOrderTable)',function(obj){
			lookover(obj.data);
		})
		form.on('submit(search)',function(obj){
			obj.field.batchNumber = obj.field.batchNumber.trim();
			obj.field.commodityName = obj.field.commodityName.trim();
			searchBatchNumber = obj.field.batchNumber;
			searchCommodityName = obj.field.commodityName;
			table.reload('entryOrderTable',{
				where:obj.field,
				page: {  curr: 1   },
				url:'${ctx}/inventory/procurementPage?type=2',
			})
		})
		function deletes(){
			var choosed=layui.table.checkStatus('entryOrderTable').data;
			if(choosed.length<1)
				return myutil.emsg('请选择生产单');
			layer.confirm('是否确认反冲？',{offset:'100px',},function(){
				var ids=[];
				for(var i=0;i<choosed.length;i++)
					ids.push(choosed[i].id);
				myutil.deleAjax({
					url: '/inventory/deleteProcurement?ids='+ids.join(','),
					success:function(){
						table.reload('entryOrderTable');
					}
				})
			})
		}
		function lookover(data){
			if(data.type==undefined)				//防止空数据弹窗bug，双击详细内容时
				return;
			layer.open({
				type : 1,
				title : '查看入库单',
				offset:'30px',
				area : ['90%','90%'],
				content : $('#lookoverOrderDiv')
			})
			table.render({									//渲染选择后的商品表格
				elem:'#lookOverProductListTable',
				data:data.procurementChilds,
				page:{},
				cols:[[
				       {align:'center', title:'批次号', field:'batchNumber',},
				       {align:'center', title:'商品名称',  templet:'<p>{{ d.commodity.skuCode }}</p>'},
				       {align:'center', title:'数量',     field:'number',},
				       {align:'center', title:'剩余数量', field:'residueNumber'},
				       {align:'center', title:'入库仓库', 	  templet:function(d){return d.warehouse.name; },}, 
				       {align:'center', title:'入库类型', 	 templet:'#statusTpl',}, 
				       {align:'center', title:'备注', 	  field:'childRemark',}, 
				       ]]
			})
			$('#look_createdAt').val(data.createdAt);
			$('#look_remark').val(data.remark);
			$('#look_number').val(data.number);
			var statusText='无类型';
			var tdText='';
			var tdInput='';
			switch(data.status){
			case 0: statusText='生产入库'; break;
			case 1: statusText='调拨入库'; 
					tdText='调拨人';
					tdInput='<input type="text" readonly class="layui-input" value="'+data.transfersUser.userName+'">';
					break;
			case 2: statusText='销售退货入库'; 
					tdText='客户';
					tdInput='<input type="text" readonly class="layui-input" value="'+data.onlineCustomer.buyerName+'">';
					break;
			case 3: statusText='销售换货入库'; break;
			case 4: statusText='采购入库'; break;
			}
			$('#look_textTd').html(tdText);
			$('#look_inputTd').html(tdInput);
			$('#look_status').val(statusText);
			$('#look_user').val(data.user.userName);
		}
		
		//-------新增入库单功能---------------
		laydate.render({ elem:'#addCreatedAt', type:'datetime' });
		var choosedProduct=[];		//用户已经选择上的产品,渲染新增单的产品表格数据
		var defaultStatus = 0;
		var defaultInventory='';
		var addOrderWin = '';
		renderAddTable('productListTable');
		renderAddTable('productListTable1');
		function renderAddTable(tid){
			var cols = [[
			       {type:'checkbox', align:'center', fixed:'left'},
			       {align:'center', title:'批次号', field:'batchNumber', edit:true, style:'color:blue',},
			       {align:'center', title:'商品名称', field:'skuCode',},
			       {align:'center', title:'计划数量',     field:'number', edit:'true', width:'8%', },
			       {align:'center', title:'入库仓库',     field:'warehouseId', 	templet: getInventorySelectHtml()},
			       {align:'center', title:'入库类型',     field:'status',  		templet: getStatusSelectHtml()},
			       {align:'center', title:'仓位',  	 	  field:'place', 	edit:true, style:'color:blue',}, 
			       {align:'center', title:'备注',  	  field:'childRemark',  edit:true, style:'color:blue',}, 
			       ]];
			if(tid=='productListTable1'){
				cols[0].splice(1,1,{align:'center', title:'批次号', field:'batchNumber',style:'color:blue',}) 
				cols[0].splice(4,0,{align:'center',title:'剩余数量',field:'residueNumber',style:'color:red',width:'8%',}) 
			}
			table.render({									//渲染选择后的商品表格
				elem:'#'+tid,
				toolbar:'#productListTableToolbar',
				page:{},
				data:[],
				size:'lg',
				cols: cols,
			})
		}
		function add(){										//新增单
			choosedProduct = [];
			defaultInventory = defaultInventory==''?allInventory[0].id:defaultInventory;
			getUserSelect( currUser.id,'userIdSelect',allUser );
			addOrderWin = layer.open({
				type : 1,
				title : '新增入库单',
				area : ['90%','90%'],
				offset:'30px',
				content : $('#addOrderDiv'),
				success: function(){
					table.reload('productListTable',{ data:[] });
					table.reload('productListTable1',{ data:[] });
				}
			})
		}
		table.on('toolbar(productListTable)',function(obj){		//监听选择商品表格的工具栏按钮
			switch(obj.event){
			case 'add': openChooseProductWin(); break;
			case 'delete': deleteChoosedProduct('productListTable');break;
			}
		})
		table.on('toolbar(productListTable1)',function(obj){	
			switch(obj.event){
			case 'add': openChooseProductWin(); break;
			case 'delete': deleteChoosedProduct('productListTable1');break;
			}
		})
		form.on('select(selectInventory)', function (data) {
            var elem = $(data.elem);
            var trElem = elem.parents('tr');
            choosedProduct[trElem.data('index')].warehouseId = data.value;
        });
		form.on('select(defaultSelect)',function(obj){
			switch(obj.elem.getAttribute('type')){
			case 'inventory' : defaultInventory=obj.value;    
								for(var i=0;i<choosedProduct.length;i++)
									choosedProduct[i].warehouseId=defaultInventory;
							break;
			case 'status' :
							if(defaultStatus==0 && obj.value!=0){  //如果之前是生产入库，现在不是
								$('#tableDiv1').hide();
								$('#tableDiv').show();
								$('#addOrderNumber').val(0);
								choosedProduct = [];
							}else if(obj.value==0 && defaultStatus!=0){		//如果之前不是生产入库且现在选中生产入库
								$('#tableDiv1').show();
								$('#tableDiv').hide();
								$('#addOrderNumber').val(0);
								choosedProduct = [];
							}
							defaultStatus=obj.value; 	
							for(var i=0;i<choosedProduct.length;i++)			//设置已选商品的状态默认值
								choosedProduct[i].status=defaultStatus;
							if(obj.value==1){
								$('#textTd').html('调拨人');
								$('#selectTd').html('<select id="userIdOrg" lay-search name="transfersUserId"><option>获取数据中...</option></select>');
								getUserSelect('','userIdOrg',allUserOrg);
							}
							else if(obj.value==2){
								$('#textTd').html('客户');
								$('#selectTd').html('<select id="customId" lay-search name="onlineCustomerId"><option>获取数据中...</option></select>');
								getUserSelect('','customId',allCustom);
							}else{
								$('#textTd').html('');
								$('#selectTd').html('');
							}
							break;
			}
			table.reload('productListTable',{ data : choosedProduct });
			table.reload('productListTable1',{ data : choosedProduct });
		})
		tableOnEdit('productListTable');
		tableOnEdit('productListTable1');
		function tableOnEdit(tid){
			table.on('edit('+tid+')', function(obj){ 							//监听编辑表格单元
				if(obj.field=='number'){
					var msg='';
					isNaN(obj.value) && (msg="请输入正确的数字");
					(obj.value=='')  && (msg='计划的数量不能为空'); 
					(obj.value<0)  && (msg='计划的数量不能小于0');
					(obj.value%1 !== 0) && (msg='计划的数量必须为整数');
					if(tid=='productListTable1')
						(obj.value>obj.data.residueNumber) && (msg='计划的数量不能大于剩余数量');
					for(var i=0;i<choosedProduct.length;i++){
						if(choosedProduct[i].id==obj.data.id){		
							if(msg!=''){
								myutil.emsg('"'+obj.value+'" 为无效修改！'+msg);
								$(this).val(choosedProduct[i].number)
								return;
							}
							$('#addOrderNumber').val($('#addOrderNumber').val()-choosedProduct[i].number-(-parseInt(obj.value)));
							$(this).val(parseInt(obj.value))
							choosedProduct[i].number=parseInt(obj.value);
							return;
						}
					}
				}else{
					for(var i=0;i<choosedProduct.length;i++){
						 if(choosedProduct[i].id==obj.data.id){		
							choosedProduct[i][obj.field] = obj.value;
						 	break;
						}
					}
				}
			});
		}
		form.on('submit(sureAdd)',function(obj){					//确定添加入库单
			var child=[];
			var data=obj.field;
			if(!choosedProduct.length>0)
				return myutil.emsg('入库商品不能为空！');
			for(var i=0;i<choosedProduct.length;i++){
				var t=choosedProduct[i];		
				if(t.batchNumber.replace(/(^\s*)|(\s*$)/g, '')=='')
					return myutil.emsg('商品批次号不可省略！');
				if(t.number<1)
					return myutil.emsg('计划数量不能为0！');
				for(var j=0;j<choosedProduct.length;j++){
					var item = choosedProduct[j];
					if( item.id != choosedProduct[i].id && item.commodityId == choosedProduct[i].commodityId && item.batchNumber == choosedProduct[i].batchNumber)
						return myutil.emsg('相同的商品不能同时使用相同的批次号！');
				}
				var temp = {
					productId : 	t.commodityId,
					number : 		t.number,
					warehouseId : 	t.warehouseId,
					status : 		t.status,
					place : 		t.place,
					childRemark : 	t.childRemark,
					batchNumber : 	t.batchNumber.replace(/(^\s*)|(\s*$)/g, ''),
				};
				if(t.status==0)
					temp.parentId = t.parentId;
				child.push(temp);
			}
			data.status=defaultStatus;
			data.number=$('#addOrderNumber').val();
			data.commodityNumber=JSON.stringify(child);			//子列表商品
			myutil.saveAjax({
				url: '/inventory/addProcurement',
				data: data,
				success: function(){
					$('#resetAddOrder').click();
					table.reload('entryOrderTable');
					choosedProduct = [];
					layer.close(addOrderWin);
				}
			})
		}) 
		//选择商品隐藏框的按钮监听.添加商品弹窗共4个按钮监听。搜索、添加新商品、确定添加
		$('#sure').on('click',function(){	
			if(sureChoosed())											//如果选择成功
				layer.close(chooseProductWin);							
		})
		$('#resetAddOrder').on('click',function(){			
			$('#addRemark').val('');
			$('#addOrderNumber').val(0);
			$('#addCreatedAt').val('');
			choosedProduct=[];	
			getUserSelect(currUser.id,'userIdSelect',allUser);
			if(defaultStatus==0)
				table.reload('productListTable1',{ data:choosedProduct })
			else
				table.reload('productListTable',{ data:choosedProduct })
		})
		form.on('submit(searchProduct)',function(obj){
			if(defaultStatus==0){
				obj.field.productName = obj.field.skuCode;
				delete obj.field.skuCode;
			}
			table.reload('productChooseTable',{
				where:obj.field,
				page: {  curr: 1   }
			})
		})
		
		//----添加新商品功能--------------

		form.on('submit(sureAddNew)',function(obj){		
			myutil.saveAjax({
				url: '/inventory/addCommodity',
				data: obj.field,
				success:function(){
					table.reload('productChooseTable');
					layer.close(addNewPorductWin);
				}
			})
		})
		function deleteChoosedProduct(tid){								//删除商品
			var choosed = layui.table.checkStatus(tid).data;
			if(choosed.length==0)
				return myutil.emsg("请选择商品删除");
			for(var i=0;i<choosed.length;i++){
				for(var j=0;j<choosedProduct.length;j++){
					if(choosed[i].id==choosedProduct[j].id){
						$('#addOrderNumber').val($('#addOrderNumber').val()-choosedProduct[j].number);
						choosedProduct.splice(j,1);
						break;
					}
				}
			}
			table.reload(tid,{ data:choosedProduct, })
		}
		addNew.render({		//绑定新增商品按钮
			elem: "#addNewProduct",
			success: function(){
				table.reload('productChooseTable');
			}
		})
		function openChooseProductWin(){					//商品选择隐藏框
			chooseProductWin = layer.open({		
				type:1,
				title:'选择产品',
				area:['80%','80%'],
				offset:'60px',
				content:$('#productChooseDiv'),
			})
			$('#addNewProduct').show();
			var url = '${ctx}/inventory/commodityPage',
			    cols = [[
					       {type:'checkbox', align:'center', fixed:'left'},
					       {align:'center', title:'商品名称', field:'skuCode',},
					       {align:'center', title:'销售属性', 	  templet:'#saleAttributeTpl',},
					       {align:'center', title:'库存情况', 	  templet:'#inventoryTpl',}, 
					       {align:'center', title:'售价详情', 	  templet:'#priceTpl',}, 
					      ]];
			if(defaultStatus == 0){
				$('#addNewProduct').hide();
				url = '${ctx}/inventory/procurementProPage?type=1'
				cols = [[
					       {type:'checkbox', align:'center', fixed:'left'},
					       {align:'center', title:'批次号', field:'batchNumber',width:'16%'},
					       {align:'center', title:'商品名称', field:'productName', templet:'<span>{{ d.commodity.name }}</span>'},
					       {align:'center', title:'计划数量', field:'number',width:'8%'},
					       {align:'center', title:'剩余数量', 	  field:'residueNumber',width:'8%'},
					       {align:'center', title:'备注', 	  field:'childRemark',}, 
					      ]];
			}
			table.render({
				elem:'#productChooseTable',
				url: url,
				page:true,
				request:{ pageName:'page', limitName:'size' },
				parseData:function(ret){ return{ code:ret.code, msg:ret.message, data:ret.data.rows, count:ret.data.total,}},
				cols: cols,
			});
			form.render();
		}
		var choosedId=0;
		function sureChoosed(){					//确定商品选择
			var choosed=layui.table.checkStatus('productChooseTable').data;
			if(choosed.length<1){
				myutil.emsg("请选择相关商品");
				return false;
			}
			if(defaultStatus != 0 ){
		 		for(var i=0;i<choosed.length;i++){
		 			var orderChild={
							skuCode : choosed[i].skuCode,		
							commodityId : choosed[i].productId,		
							number : 1,						
							childRemark : choosed[i].remark,		
							batchNumber : '',
							status : defaultStatus,
							warehouseId : defaultInventory,
							place : '',
							id : choosedId++,  					//仅仅用于标识不同的数据
					};
					$('#addOrderNumber').val($('#addOrderNumber').val()-(-1));
					choosedProduct.push(orderChild);
				}
			}else{
				for(var i=0;i<choosed.length;i++){
		 			var orderChild={
							skuCode : choosed[i].commodity.name,		
							commodityId : choosed[i].commodity.productId,
							number : 0,						
							childRemark : choosed[i].childRemark,		
							batchNumber : choosed[i].batchNumber,	
							status : defaultStatus,
							warehouseId : defaultInventory,
							place : '',
							residueNumber: choosed[i].residueNumber,
							parentId: choosed[i].id,
							id : choosedId++, 
					};
					choosedProduct.push(orderChild);
				}
			}
	 		if(defaultStatus==0)
	 			table.reload('productListTable1',{ data:choosedProduct });
	 		else
				table.reload('productListTable',{ data:choosedProduct });
			myutil.smsg('添加成功');
			return true;
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
		function getAllUserOrg(){
			$.ajax({
				url:'${ctx}/system/user/pages?size=99&orgNameIds=29&quit=0',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allUserOrg.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].userName
							})
					}
				}
			})
		}
		function getAllCustom(){
			$.ajax({
				url:'${ctx}/inventory/onlineCustomerPage?size=9999',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allCustom.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].buyerName
							})
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
		function getUserSelect(id,select,user){
			var html='';
			for(var i=0;i<user.length;i++){
				var selected=( id==user[i].id?'selected':'' );
				html+='<option value="'+user[i].id+'" '+selected+'>'+user[i].userName+'</option>';
			}
			$('#'+select).html(html);
			form.render();
		}
		function renderInventorySelect(select){
			var html='';
			if(select!='defaultInventorySelect')
				html='<option value="">入库仓库</option>';
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
				var html='<select  disabled > ';
					html+=	'<option value="0" '+ (d.status==0?"selected":"") +'>生产入库</option>';
					html+=	'<option value="1" '+ (d.status==1?"selected":"") +'>调拨入库</option>';
					html+=	'<option value="2" '+ (d.status==2?"selected":"") +'>销售退货入库</option>';
					html+=	'<option value="3" '+ (d.status==3?"selected":"") +'>销售换货入库 </option>';
					html+=	'<option value="4" '+ (d.status==4?"selected":"") +'>采购入库</option>';
					html+=	'<option value="4" '+ (d.status==5?"selected":"") +'>盘亏入库</option>';
					html+=	'</select>';
				return html;
			};
		}
		function getInventorySelectHtml() {				//获取仓库下拉框
			return function(d) {		
				if(allInventory.length==0){
					return '没有可用仓库';
				}
				var html='<select id="selectInventory" lay-filter="selectInventory" > ';
				for(var i=0;i<allInventory.length;i++){
					var disable = allInventory[i].flag==1?'':'disabled';
					var selected = d.warehouseId ==allInventory[i].id?'selected':'';
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
		function getData(){
			getAllInventory();
			getAllUser();
			getAllCustom();
			getAllUserOrg();
			getCurrUser();
		}
	}//end define function
)//endedefine
</script>
</html>