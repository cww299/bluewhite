<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出库单</title>
<style>
td{
	text-align:center;
}
.layui-card .layui-table-cell{		/*  取消高度    */
	  height:auto;
	  padding:0px;
}
.layui-table tbody tr:hover, .layui-table-hover {
	background-color: transparent;
}
</style>
</head>
<body>

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
						<option value="">出库类型</option>
						<option value="0">销售出库</option>
						<option value="1">调拨出库</option>
						<option value="2">销售换货出库</option>
						<option value="3">采购退货出库 </option></select></td>
			    <td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span>
				    <span class="layui-btn" id="uploadDataBtn">导入出库单</span></td>
			</tr>
		</table>
		<table class="layui-table" id="outOrderTable" lay-filter="outOrderTable"></table>
	</div>
</div>

<!-- 上传文件隐藏框 -->
<div id='uploadDiv' style="display:none;padding:20px;" class="layui-form">
	<select id='uploadUser' lay-search></select>
	<!-- <select id='uploadCustom' lay-search><option value="">选择客户</option></select> -->
	<select id='uploadWarehouse'><option value="">仓库选择</option></select>
	<button type='button' id='uploadData' style="display:none;"></button>
</div>

<!-- 添加订单隐藏框  -->
<div id="addOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table" lay-size="sm" lay-skin="nob">
		<tr><td>下单时间<input type="hidden" name="type" value="3" ></td>	<!-- 默认type类型为3，表示为出库单 -->
			<td><input type="text" class="layui-input" name="createdAt" id="addCreatedAt"></td>
			<td>经手人</td>
			<td><select name="userId" id='userIdSelect' lay-search><option value="1" >获取数据中...</option></select></td>
			<td>客户</td>
			<td><select name="onlineCustomerId" id='customerIdSelect' lay-search lay-verify="required"><option value="1">获取数据中...</option></select></td>
			<td>默认出库类型</td>
			<td><select lay-filter="defaultSelect" type='status'>
						<option value="0">销售出库</option>
						<option value="1">调拨出库</option>
						<option value="2">销售换货出库</option>
						<option value="3">采购退货出库 </option></select></td></tr>
		<tr>
			<!-- <td>默认批次号</td>	
			<td><input type="text" class="layui-input" id="addBatchNumber" name='batchNumber'></td> -->
			<td>默认出库数量</td>
			<td><select lay-filter="defaultSelect" type='number' ><option value="zero">不出库</option><option value="all">出库全部</option></select></td>
			<td>备注</td>
			<td><input type="text" name="remark" class="layui-input" id="addRemark"></td>
			<td><span style="float:left;margin-top: 10px;">出库数量</span>
				<input type="text" class="layui-input" name='number' id="addOrderNumber" readonly value="0" style="width:50px;float:right;"></td>
			<td style="text-align:right;"><span class="layui-btn" lay-submit lay-filter="sureAdd" >确定</span>
							<span class="layui-btn layui-btn-danger" id='resetAddOrder' >清空数据</span></td></tr>
	</table>
	<table class="layui-table" id="productListTable" lay-filter="productListTable"></table>
</div>

<!-- 查看订单隐藏框  -->
<div id="lookoverOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table"  lay-size="sm" lay-skin="nob">
		<tr><td>下单时间</td>	
			<td><input type="text" class="layui-input" readonly id="look_createdAt"></td>
			<td>经手人</td>
			<td><input type="text" class="layui-input" readonly id="look_user" value='无经手人'></td>
			<td>总数量</td>
			<td><input type="text" class="layui-input" id="look_number" readonly></td></tr>
		<tr><td>备注</td>
			<td colspan="3"><input type="text" id="look_remark" class="layui-input" readonly></td>
			<td>出库类型</td>
			<td><input type="text" class="layui-input" id="look_type" readonly></td></tr>
	</table>
	<table class="layui-table" id="lookOverProductListTable" lay-filter="lookOverProductListTable"></table>
</div>


<!-- 商品选择隐藏框 -->
<div id="productChooseDiv" style="display:none;">
	<table class="layui-form layui-table" lay-size="sm" lay-skin="nob" style='width:60%;'>
		<tr>
			<td><select><option value="1">按产品名称</option></select></td>			
			<td><input type="text" class="layui-input" name="skuCode" placeholder="请输入查找的商品名"></td>				
			<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" >搜索</button>			
				<button type="button" class="layui-btn layui-btn-sm" id="sure" >确定添加</button></td>
		</tr>
	</table>
	<table class="layui-table" id="productChooseTable" lay-filter="productChooseTable"></table>
</div>


<!-- 出库单表格工具栏 -->
<script type="text/html" id="outOrderTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增出库单</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >一键反冲</span>
	<span lay-event="transSendOrder"  class="layui-btn layui-btn-sm" >转换发货单</span>
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
	{{# var inv=d.product.inventorys;
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
		str+='天猫价格:'+(d.tianmaoPrice ||'---')+' ';
		str+='1688价格:'+(d.oseePrice ||'---')+' ';
		str+='线下价格:'+(d.offlinePrice ||'---');
	}}
	<span style='color:orange;'>{{ str }}</span>
</script>

<!-- 出库单查看类型转换模板 -->
<script type="text/html" id='statusTpl'>
	{{#	var text='',color='';
		switch(d.status){
		case 0: text='销售出库'; 	color='';	 break;
		case 1: text='调拨出库'; 	color='blue'; break;
		case 2: text='销售换货出库'; color='orange'; break;
		case 3: text='采购退货出库'; color='green'; 	break;
		}
	}}	
	<span class='layui-badge layui-bg-{{color}}'>{{text}}</span>
</script>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil: 'layui/myModules/myutil',
}).define(
	['tablePlug','laydate','upload','myutil'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table
		, myutil = layui.myutil
		, upload = layui.upload 
		, laydate = layui.laydate
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.config.msgOffset = '250px';
		myutil.clickTr();
		var chooseProductWin,		//选择商品弹窗
		    allInventory = [],		//所有仓库
			allUser = [],
			allCustom = [],
			currUser = {id:''},    
		    searchBatchNumber = '',			//记录搜索使用的数据，用于过滤筛选子单数据,即显示搜索出相关的商品
		    searchCommodityName = '';
			
		getCurrUser();
		getAllInventory();
		getAllUser();
		getAllCustom();
		
		//--------------导入出库单功能-----------------------		
		var load;
		upload.render({
		   	  elem: '#uploadData'
		   	  ,url: '${ctx}/inventory/import/excelOutProcurement'
		 	  ,before: function(obj){ 	
		 		  this.data={  
		 				  userId:$('#uploadUser').val(),  
		 				  warehouseId:$('#uploadWarehouse').val()  };
		 		 load = layer.load(1); 
			  }
		   	  ,done: function(res, index, upload){ 
		   		  if(res.code==0){
				   		layer.closeAll();
				   		table.reload('outOrderTable');
		   		  }else{
			   			layer.close(load);
		   		  }
		   		  layer.alert(res.message, {
		   			offset:'200px',
		   		    skin: 'layui-layer-lan' ,
		   		    closeBtn: 0
		   		  });
		   	  } 
		   	  ,accept: 'file' 
		   	  ,exts: 'xlsx|xls'
		})
		$('#uploadDataBtn').on('click',function(){
			layer.open({
				title:'导入订单数据',
				area:['30%','40%'],
				offset: '100px',
				type:1,
				content:$('#uploadDiv'),
				btn:['选择文件','取消'],
				yes:function(){
					$('#uploadData').click();
				}
			})
			getUserSelect(currUser.id,'uploadUser');
		}) 
		
		//-----------------主页面功能--------------------
		
		form.render();
		table.render({				//渲染主页面单表格
			elem:'#outOrderTable',
			url:'${ctx}/inventory/procurementPage?type=3&flag=0',
			toolbar:'#outOrderTableToolbar',
			page:{},
			request:{pageName:'page',limitName:'size'},
			parseData:function(ret){ return {data:ret.data.rows,count:ret.data.total,msg:ret.message,code:ret.code}},
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'单据编号',   	field:'documentNumber',	width:'10%',},
			       {align:'center', title:'数量', field:'number', width:'4%',	},
			       {align:'center', title:'经手人',	templet:'<p>{{ d.user?d.user.userName:"---" }}</p>', width:'4%',	},
			       {align:'center', title:'备注',  field:'remark',	  	},
			       {align:'center', title:'客户',	templet:'<p>{{ d.onlineCustomer!=null?d.onlineCustomer.buyerName:"&nbsp" }}</p>', width:'4%',	},
			       {align:'center', title:'出库类型', templet:'#statusTpl', width:'6%',	},
			       {align:'center', title:'是否反冲', 	field:'flag', templet:'#flagTpl', width:'4%',	},
			       {align:'center', title:'日期',   	field:'createdAt',	width:'9%',},
			       {align:'center', title:'批次号',	  templet: orderContent('batchNumber'),   width:'10%'	,},
				   {align:'center', title:'商品名',	  templet: orderContent('skuCode'),		  width:'18%'	,},
			       {align:'center', title:'数量',     templet: orderContent('number'),		  width:'4%',	},
				   {align:'center', title:'备注',	  templet: orderContent('childRemark'),   	}
			       ]] 
		}) 
		function orderContent(field){		//子单内容显示
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
		
		table.on('toolbar(outOrderTable)',function(obj){	//监听单表格按钮
			switch(obj.event){
			case 'add':			add();			break;
			case 'delete':		deletes();		break;
			case 'transSendOrder': deletes('trans');   break;
			}
		})
		
		table.on('rowDouble(outOrderTable)',function(obj){
			lookover(obj.data);
		})
		form.on('submit(search)',function(obj){
			obj.field.batchNumber = obj.field.batchNumber.trim();
			obj.field.commodityName = obj.field.commodityName.trim();
			searchBatchNumber = obj.field.batchNumber;
			searchCommodityName = obj.field.commodityName;
			table.reload('outOrderTable',{
				where:obj.field,
				page: {  curr: 1   },
				url:'${ctx}/inventory/procurementPage?type=3',
			})
		})
		function deletes(trans){
			var url = trans?'/inventory/conversionProcurement':'/inventory/deleteProcurement';
			var text = trans?'请选择生产单|是否确认转换？':'请选择生产单|是否确认反冲？';
			myutil.deleTableIds({
				url: url,
				text: text,
				table: 'outOrderTable',
			})
		}
		
		//-------查看出库单功能--------------------
		function lookover(data){
			if(data.type==undefined)				//防止空数据弹窗bug，双击详细内容时
				return;
			layer.open({
				type : 1,
				title : '查看出库单',
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
				       {align:'center', title:'出库仓库', 	  templet:function(d){return d.warehouse.name; },}, 
				       {align:'center', title:'出库类型', 	 templet:'#statusTpl',}, 
				       {align:'center', title:'备注', 	  field:'childRemark',}, 
				       ]]
			})
			$('#look_createdAt').val(data.createdAt);
			$('#look_remark').val(data.remark);
			$('#look_number').val(data.number);
			$('#look_user').val(data.user.userName);
			var statusText='无类型';
			switch(data.status){
			case 0: statusText='销售出库'; break;
			case 1: statusText='调拨出库'; break;
			case 2: statusText='销售换货出库'; break;
			case 3: statusText='采购退货出库'; break;
			}
			$('#look_type').val(statusText);
		}
		
		//-------新增出库单功能---------------
		laydate.render({ elem:'#addCreatedAt', type:'datetime' });
		var choosedProduct=[];		//用户已经选择上的产品,渲染新增单的产品表格数据
		var defaultStatus=0;
		var defaultNumber='';
		function add(){										//新增单
			layer.open({
				type : 1,
				title : '新增出库单',
				area : ['90%','90%'],
				offset:'30px',
				content : $('#addOrderDiv')
			})
			getUserSelect(currUser.id,'userIdSelect');
			table.render({									//渲染选择后的商品表格
				elem:'#productListTable',
				toolbar:'#productListTableToolbar',
				page:{},
				size:'lg',
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'商品名称', field:'skuCode',},
				       {align:'center', title:'出库数量',     field:'number', edit:true,style:'color:blue',  },
				       {align:'center', title:'出库仓库',     field:'warehouseId', 	templet: getInventorySelectHtml()},
				       {align:'center', title:'出库类型',     field:'status',  		templet: getStatusSelectHtml()},
				       {align:'center', title:'仓位',  	 	  field:'place',   edit:true,style:'color:blue', }, 
				       {align:'center', title:'备注',  	  field:'childRemark', edit:true,style:'color:blue', }, 
				       ]],
			   	done: function (res, curr, count) {	//设置下拉框初始			
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
		form.on('select(selectInventory)', function (data) {
            var elem = $(data.elem);
            var trElem = elem.parents('tr');
            var inv = choosedProduct[trElem.data('index')].inventorys;
            for(var i=0;i<inv.length;i++){
            	if(inv[i].warehouse.id == data.value){
            		$('#addOrderNumber').val($('#addOrderNumber').val()-choosedProduct[trElem.data('index')].number);
            		choosedProduct[trElem.data('index')].warehouseId = inv[i].warehouse.id;
            		choosedProduct[trElem.data('index')].place = inv[i].place;
            		choosedProduct[trElem.data('index')].number = defaultNumber=='all'?inv[i].number:0;
            		$('#addOrderNumber').val($('#addOrderNumber').val()-(-choosedProduct[trElem.data('index')].number));
            		break;
            	}
            }
            table.reload('productListTable',{ data : choosedProduct });
		});
		form.on('select(defaultSelect)',function(obj){
			switch(obj.elem.getAttribute('type')){
			case 'number' : defaultNumber=obj.value;    
							var allNum=0;
							for(var i=0;i<choosedProduct.length;i++){				//设置默认值
								var inventorys=choosedProduct[i].inventorys;
								if(inventorys.length>0)
									for(var j=0;j<inventorys.length;j++){
										if(inventorys[j].warehouse.id == choosedProduct[i].warehouseId){
											choosedProduct[i].number = defaultNumber=='all'?inventorys[j].number:0;
											allNum+=choosedProduct[i].number;
											break;
										}
									}
							}
							$('#addOrderNumber').val(allNum);
							break;
			case 'status' : defaultStatus=obj.value; 	
							for(var i=0;i<choosedProduct.length;i++){				
								choosedProduct[i].status = defaultStatus;
							}
							break;
			}
			table.reload('productListTable',{data:choosedProduct});
		})
		table.on('edit(productListTable)', function(obj){ 			//监听编辑表格单元
			if(obj.field=='number'){
				var msg='';
				isNaN(obj.value) && (msg="修改无效！请输入正确的数字");
				obj.value=='' && (msg='计划的数量不能为空');
				obj.value<0 && (msg='计划的数量不能小于0');
				obj.value%1 !=0 && (msg='计划的数量必须为整数');
				for(var i=0;i<choosedProduct.length;i++){
					if(choosedProduct[i].id==obj.data.id){		
						if(msg!=''){
							$(this).val(0)
							choosedProduct[i].number=0;
							return myutil.emsg('"'+obj.value+'" 为非法输入！'+msg);
						}
						var inv=choosedProduct[i].inventorys;
						for(var j=0;j<inv.length;j++){
							if(inv[j].warehouse.id == choosedProduct[i].warehouseId){
								if(inv[j].number<obj.value)
									return myutil.emsg('计划数量不能大于仓库剩余数量，请重新修改数量或选择其他出货仓库！');
								break;
							}
						}
						$('#addOrderNumber').val($('#addOrderNumber').val()-choosedProduct[i].number-(-parseInt(obj.value)));
						$(this).val(parseInt(obj.value))
						choosedProduct[i].number=parseInt(obj.value);
						return;
					}
				}
			}else{
				for(var i=0;i<choosedProduct.length;i++){
					 if(choosedProduct[i].id==obj.data.id){		//重新对该行的相关数据进行计算
						 if(obj.field=='childRemark')
							choosedProduct[i].childRemark = obj.value;
						 else if(obj.field=='place')
							 choosedProduct[i].place = obj.value;
						 else if(obj.field=='batchNumber')
							 choosedProduct[i].batchNumber = obj.value;
					 	break;
					}
				}
			}
		});
		form.on('submit(sureAdd)',function(obj){					//确定添加出库单
			var child=[],allNum=0;
			if(!choosedProduct.length>0)
				return myutil.emsg('出库商品不能为空！');
			for(var i=0;i<choosedProduct.length;i++){
				var t=choosedProduct[i];			
				if(t.number<1)
					return myutil.emsg('计划数量不能为0！');
				if(isNaN(t.number))
					return myutil.emsg('不能选择库存为0的商品！');
				child.push({
					productId : 	t.commodityId,
					number : 		t.number,
					place : 		t.place,
					status : 		t.status,
					childRemark : 	t.childRemark || '',
					warehouseId :	t.warehouseId,
					batchNumber : 	t.batchNumber,
				});
			}
			var data=obj.field;
			data.number=$('#addOrderNumber').val();
			data.status = defaultStatus;
			data.commodityNumber=JSON.stringify(child);			//子列表商品
			myutil.saveAjax({
				url:"/inventory/addProcurement",
				data:data,			
				success:function(){
					$('#resetAddOrder').click();
					table.reload('outOrderTable');
					layer.closeAll();
				}
			})
		}) 
		form.on('submit(searchProduct)',function(obj){
			table.reload('productChooseTable',{
				where:obj.field, 
				page: {  curr: 1   }
			})
		})
		//选择商品隐藏框的按钮监听.添加商品弹窗共4个按钮监听。搜索、确定添加
		$('#sure').on('click',function(){	
			if(sureChoosed())											//如果选择成功
				layer.close(chooseProductWin);							
		})
		$('#resetAddOrder').on('click',function(){			//此处如果加confirm提示。则新增成功时无法清空
			$('#addRemark').val('');
			$('#addOrderNumber').val(0);
			$('#addCreatedAt').val('');
			choosedProduct=[];	
			getUserSelect(currUser.id,'userIdSelect');
			table.reload('productListTable',{ data:choosedProduct })
		})
		
		function deleteChoosedProduct(){								//删除商品
			var choosed = layui.table.checkStatus('productListTable').data;
			if(choosed.length==0)
				return myutil.emsg("请选择商品删除");
			for(var i=0;i<choosed.length;i++){
				for(var j=0;j<choosedProduct.length;j++){
					if(choosed[i].id==choosedProduct[j].id){
						if(!isNaN(choosedProduct[j].number))
							$('#addOrderNumber').val($('#addOrderNumber').val()-choosedProduct[j].number);
						choosedProduct.splice(j,1);
						break;
					}
				}
			}
			table.reload('productListTable',{ data:choosedProduct, })
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
				page:true,
				request:{ pageName:'page', limitName:'size' },
				parseData:function(ret){	return{ code:ret.code, msg:ret.message, data:ret.data.rows, count:ret.data.total,}},
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'商品名称', field:'skuCode',},
				       {align:'center', title:'销售属性', 	  templet:'#saleAttributeTpl',},
				       {align:'center', title:'库存情况', 	  templet:'#inventoryTpl',}, 
				       {align:'center', title:'售价详情', 	  templet:'#priceTpl',}, 
				      ]],
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
	 		for(var i=0;i<choosed.length;i++){
				var number='无库存数量，无法出库';
				var warehouseId='';
				var place='';
				var inventorys = choosed[i].product.inventorys;
				if(inventorys.length>0){
					number=defaultNumber=='all'? inventorys[0].number : 0;
					warehouseId = inventorys[0].warehouse.id;
					place =  inventorys[0].place;
					$('#addOrderNumber').val($('#addOrderNumber').val()-(-number));
				}
				var orderChild={
						skuCode:choosed[i].skuCode,			//商品名称
						commodityId: choosed[i].productId,		//商品id
						remark:choosed[i].remark,		//备注
						number:number,					 //商品数量，设置
						place:place,					//仓位备注
						status:defaultStatus,			//出库状态
						warehouseId:warehouseId,		 //选择的仓库id
						inventorys: inventorys,//库存情况
						//batchNumber : $('#addBatchNumber').val(),
						id : choosedId++,
				};
				choosedProduct.push(orderChild);
			}
			table.reload('productListTable',{ data:choosedProduct });
			myutil.smsg('添加成功');
			return true;
		}
		function getStatusSelectHtml(){					//获取类型下拉框
			return function(d) {		
				var html='<select  disabled> ';
				 	html+='<option value="0" '+ (defaultStatus=='0'?'selected':'') +'>销售出库</option>';
				 	html+='<option value="1" '+ (defaultStatus=='1'?'selected':'') +'>调拨出库</option>';
				 	html+='<option value="2" '+ (defaultStatus=='2'?'selected':'') +'>销售换货出库</option>';
				 	html+='<option value="3" '+ (defaultStatus=='3'?'selected':'') +'>采购退货出库 </option>';
				 	html+='</select>';
				return html;

			};
		}
		function getInventorySelectHtml() {				//渲染仓库下拉框
			return function(d) {	
				var inventorys=d.inventorys;
				if(inventorys.length==0){
					return '没有库存数量';
				}
				var html='<select lay-filter="selectInventory"> ';
				for(var i=0;i<inventorys.length;i++){
					var selected='';
					if(choosedProduct[d.LAY_TABLE_INDEX].warehouseId==inventorys[i].warehouse.id)
						selected='selected';
					var str=inventorys[i].warehouse.name+':'+inventorys[i].number;							//下拉框显示内容：仓库名：数量
					html+='<option value="'+inventorys[i].warehouse.id+'" '+selected+'>'+str+'</option>';
				}
				return html; 
			};
		};
		function getUserSelect(id,select){			//渲染经手人下拉框
			var html='';
			for(var i=0;i<allUser.length;i++){
				var selected=( id==allUser[i].id?'selected':'' );
				html+='<option value="'+allUser[i].id+'" '+selected+'>'+allUser[i].userName+'</option>';
			}
			$('#'+select).html(html);
			form.render();
		}
		function getCustomSelect(id,select){       //渲染客户下拉框
			var html='<option value="">客户选择</option>';
			for(var i=0;i<allCustom.length;i++){
				html+='<option value="'+allCustom[i].id+'">'+allCustom[i].userName+'</option>';
			}
			$('#'+select).html(html);
			form.render();
		}
		function getAllCustom(){				//获取所有客户
			myutil.getData({
				url:'${ctx}/inventory/onlineCustomerPage?size=99999',
				success:function(data){
					for(var i=0;i<data.length;i++){
						allCustom.push({
							id:			data[i].id,
							userName:	data[i].buyerName
						})
					}
					getCustomSelect('','uploadCustom');		//渲染导入文件的下拉框
					getCustomSelect('','customerIdSelect'); //渲染新增订单的客户下拉框
				}
			})
		}
		function getAllUser(){					//获取所有经手人
			myutil.getData({
				url:'${ctx}/system/user/pages?size=999&quit=0',
				success:function(data){
					for(var i=0;i<data.length;i++)
						allUser.push({
							id:			data[i].id,
							userName:	data[i].userName
						})
				}
			})
		}
		function getAllInventory(){				//获取所有仓库
			myutil.getDataSync({
				url:'${ctx}/basedata/list?type=inventory',
				success:function(data){
					var html = '';
					layui.each(data,function(index,item){
						html += '<option value="'+item.id+'">'+item.name+'</option>';
					})
					$('#uploadWarehouse').html(html);	//渲染导入文件仓库下拉框
				}
			})
		}
		function getCurrUser(){								//获取当前登录用户
			$.ajax({
				url:'${ctx}/getCurrentUser',
				success:function(r){
					if(0==r.code)
						currUser = r.data;
				}
			})
		}
	}//end define function
)//endedefine
</script>
</html>