<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
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
	<div class="layui-card-body">	<!-- 主页面内容 -->
	<form>
		<table class="layui-form" style="width:100%" id="headerTool">
			<tr>
				<td><a style="color:blue" href="#"  id="customName">客户名称：</a></td>	
				<td><input type="text" class="layui-input" name="name" id="customNames" lay-verify="required" readonly ></td>
				<td>订单编号：</td>		
				<td><input type="text" class="layui-input" name="tid"></td>
				<td>订单状态：</td>			
				<td><select name="status">
						<option  value="WAIT_SELLER_SEND_GOODS">等待卖家发货,即:买家已付款</option>
						<option  value="TRADE_NO_CREATE_PAY">没有创建支付宝交易</option>
						<option  value="WAIT_BUYER_PAY">等待买家付款</option>
						<option  value="SELLER_CONSIGNED_PART">卖家部分发货</option>
						<option  value="TRADE_BUYER_SIGNED">买家已签收,货到付款专用</option>
						<option  value="TRADE_FINISHED">交易成功</option>
						<option  value="WAIT_BUYER_CONFIRM_GOODS">等待买家确认收货,即:卖家已发货</option></select></td>	
			</tr>
			<tr>
				<td>收货人：</td>			
				<td><input type="text" class="layui-input" id="customRealName" name="buyerName"></td>
				<td>收款金额：</td>			
				<td><input type="text" class="layui-input" name="payment" id="customPayment"></td>
				<td>所属客服：</td>			
				<td><select name="userId" id='userIdSelect' lay-search><option value="">获取数据中..</option></select></td>
			</tr>
			<tr>
			</tr>
			<tr>
				<td>手机：</td>			
				<td><input type="text" class="layui-input" id="customPhone" name="phone"></td>
				<td>整单优惠：</td>			
				<td><input type="text" class="layui-input" name="allBillPreferential" value="0"></td>
				<td>邮费：</td>			
				<td><input type="text" class="layui-input" name="postFee" value='0'></td>
			</tr>
			<tr>
				<td>所在地：</td>			
				<td colspan="3">
						<div class="layui-form-item">
								<div class="layui-input-inline"><select lay-search id="province" name="provincesId"><option value="">获取数据中..</option></select></div>
								<div class="layui-input-inline"><select lay-search id="city" 	name="cityId"><option value="">获取数据中..</option></select></div>
								<div class="layui-input-inline"><select lay-search id="area" 	name="countyId"><option value="">获取数据中..</option></select></div>
								</div></td>
				<td>物流方式：</td>			
				<td><select name="shippingType">
									 <option  value="free"		>卖家包邮</option>
									 <option  value="post"		>平邮</option>
									 <option  value="express"	>快递</option>
									 <option  value="ems"		>EMS</option>
									 <option  value="virtual"	>虚拟发货</option></select></td>	
			</tr>
			<tr>
				<td>详细地址：</td>			
				<td colspan="3"><input type="text" class="layui-input" id="customAddress" name="address"
								placeholder="您可以直接黏贴淘宝或拼多多的收货地址,会自动提取省市区和收货人信息"></td>
				<td>邮编：</td>			
				<td><input type="text" class="layui-input" id="customZipCode" name="zipCode"></td>
			</tr>
			<tr>
				<td>卖家备注：</td>			
				<td colspan="3"><input type="text" class="layui-input" placeholder="" name="sellerMemo"></td>
				<td rowspan="2">操作</td>
				<td rowspan="2">
					<button class="layui-btn layui-btn-sm" lay-submit       type="button" lay-filter="sureAdd">确定添加</button>
					<button class="layui-btn layui-btn-sm layui-btn-danger" type="reset" id="resetAll">清空</button>
					<input type="hidden" name="onlineCustomerId" id="customId">
				</td>
			</tr>
			<tr>
				<td>买家备注：</td>			
				<td colspan="3"><input type="text" class="layui-input" name="buyerMemo"></td>
			</tr>
		</table>
	</form>
		<table class="layui-table" id="productTable" lay-filter="productTable"></table>	<!-- 已选中的产品列表  -->
	</div>
</div>

</body>

<!-- 商品选择隐藏框 -->
<div id="addProductDiv" style="display:none;padding:10px;">
	<table class="layui-form" lay-filter="productListTool">
		<tr>
			<td><select><option>按产品名称</option></select></td>			<td>&nbsp;</td>
			<td><input type="text" class="layui-input" name="textSearch" placeholder="请输入搜索内容"></td>							<td>&nbsp;</td>
			<td>默认发货价</td><td>&nbsp;</td>
			<td><select name='defaultPriceSelect'>
					<option value="tianmao">天猫销售价</option>
					<option value="_1688">1688销售价</option>
					<option value="offline">线下批发价</option></select></td>	<td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" > <i class="layui-icon">&#xe615;</i>搜索</button></td><td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="addNewProduct" >新增商品</button></td>			 <td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="sure" >确定添加</button></td>						<td>&nbsp;</td>
		</tr>
	</table>
	<table class="layui-table" id="productListTable" lay-filter="productListTable"></table>
</div>

<!-- 客户选择隐藏框 -->
<div id="customNameDiv" style="display:none;padding:5px;">
	<table class="layui-form">
		<tr>
			<td>客户名称：</td>
			<td><input type="text" name='name' class="layui-input" placeholder='请输入查找信息'></td>		<td>&nbsp;</td>
			<td>手机号：</td>
			<td><input type="text" name='phone' class="layui-input" placeholder='请输入查找信息'></td>		<td>&nbsp;</td>
			<td><button lay-submit 	lay-filter="searchCustom"	type="button" class="layui-btn layui-btn-sm">
					<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i></button>				<td>&nbsp;</td>
			<td><button id="addCustom"	type="button" class="layui-btn layui-btn-sm">添加新客户</button>		<td>&nbsp;</td>
			<td><span class="layui-badge">小提示：双击选中客户对象</span></td>
		</tr>
	</table>
	<table id="customTable" lay-filter="customTable" class="layui-table"></table>
</div>


<!-- 添加新商品隐藏框 -->
<form class="layui-form layui-table" style="display:none;" id="addProductWindow">
<table style="width:100%;">
	<tr><td>商品名称</td>
		<td><input type="text" class="layui-input" lay-verify="required"	name="skuCode"></td>
		<td>1688批发价</td>
		<td><input type="text" class="layui-input" 		name="oseePrice"></td></tr>
	<tr><td>天猫单价</td>
		<td><input type="text" class="layui-input" 		name="tianmaoPrice"> </td>
		<td>线下批发价</td>
		<td><input type="text" class="layui-input" 		name="offlinePrice"></td></tr>
	<tr><td>商品重量</td>
		<td><input type="text" class="layui-input" 		name="weight" ></td>
		<td>商品高度</td>
		<td><input type="text" class="layui-input" 		name="size" ></td></tr>
	<tr><td>商品成本</td>
		<td><input type="text" class="layui-input" 		name="cost"></td>
		<td>广宣成本</td>
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

<!-- 添加新客户隐藏框  -->
<form style="display:none;" id="addCustomWindow">
<table class="layui-form layui-table">
	<tr><td>买家姓名</td>
		<td><input type="text" class="layui-input" name="buyerName" lay-verify="required"></td>
		<td>客户名称</td>
		<td><input type="text" class="layui-input" name="name" lay-verify="required"></td></tr>
	<tr><td>客户等级</td>
		<td><select name="grade" id="addCustomGrade">
					<option value="0">一级</option>
					<option value="1">二级</option>
					<option value="2">三级</option>
				</select></td>
		<td>客户类型</td>
		<td><select name="type" id="addCustomType">
					<option value="0">电商</option>
					<option value="1">商超</option>
					<option value="2">线下</option></select></td></tr>
	<tr><td>手机</td>
		<td><input type="text" class="layui-input" name="phone" lay-verify="required"></td>
		<td>帐号</td>
		<td><input type="text" class="layui-input" name="account"></td></tr>
	<tr><td>所在地：</td>			
		<td colspan="3">
				<div class="layui-form-item">
					<div class="layui-input-inline"><select lay-search name="provincesId" id="addProvince"></select></div>
					<div class="layui-input-inline"><select lay-search name="cityId" id="addCity"></select></div>
					<div class="layui-input-inline"><select lay-search name="countyId" id="addArea"></select></div>
					</div></td></tr>
	<tr><td>详细地址</td>
		<td colspan="3"><input type="text" class="layui-input" name="address"></td></tr>
	<tr><td>邮编</td>
		<td><input type="text" class="layui-input" name="zipCode"></td>
		<td colspan="2"><button type="reset" class="layui-btn layui-btn-danger" id="resetCustomAdd">清空</button>
						<button class="layui-btn " type="button" lay-submit lay-filter="sureAddCustom">确定</button></td></tr>
</table>
</form>

<!-- 已添加商品表的工具栏  -->
<script type="text/html" id="productTableToolbar">
<div class="layui-button-container">
	<span class="layui-btn layui-btn-sm" lay-event="add">选择商品</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除商品</span>
</div>
</script>

<!-- 字体颜色模板 -->
<script type="text/html" id="systemPreferentialTpl">
  {{#  if(d.systemPreferential < 0 ){ }}
    <span style="color: red;">{{d.systemPreferential}}</span>
  {{#  } else { }}
    <span style="color: #009688;">{{d.systemPreferential}}</span>
  {{#  } }}
</script>

<script type="text/html" id="sellerReadjustPricesTpl">
  {{#  if(d.sellerReadjustPrices < 0 ){ }}
    <p style="color: red;">{{d.sellerReadjustPrices}}</p>
  {{#  } else { }}
    <label style="color: #009688;">{{d.sellerReadjustPrices}}</label>
  {{#  } }}
</script>
<script type="text/html" id="priceTpl">
  {{#  if(d.price < 0 ){ }}
    <p style="color: red;">{{d.price}}</p>
  {{#  } else { }}
    <label style="color: #009688;">{{d.price}}</label>
  {{#  } }}
</script>
<script type="text/html" id="numberTpl">
  {{#  if(d.number < 0 ){ }}
    <p style="color: red;">{{d.number}}</p>
  {{#  } else { }}
    <label style="color: #009688;">{{d.number}}</label>
  {{#  } }}
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
		
		var choosedProduct=[],			//用户已经选择上的产品
			allInventory=[],			//所有的仓库
			allUser=[];
		var chooseCuctomWin				//各个弹窗
			,chooseProductWin
			,addNewProductWin
			,addNewCustomWin;
		
		$('#headerTool').find("td:even").css({backgroundColor:"rgba(65, 161, 210, 0.45)",padding:"1px"}); //表格颜色
		rederSelect();						//渲染地址下拉框，给出默认值
		getAllInventory();					//获取所有仓库
		getAllUser();
		form.render();
		
		table.render({
			elem:"#productTable", 
			toolbar:'#productTableToolbar',
			loading:true,
			data:[],
			totalRow:true,
			page:{},
			cols:[[
					{type:'checkbox',align:'center',fixed:'left'},
					{field:'skuCode',	title:'商品名称',	align:'center'},
					{field:'inventory',	title:'发货仓库',	align:'center', width:'8%',  templet:function(d){
																			for(var i=0;i<allInventory.length;i++){ 
																					if(allInventory[i].id==d.inventory)
																						return '<span>'+allInventory[i].name+'</span>';}	}},
					{field:'number',	title:'数量',       align:'center', width:'6%',		edit:'text',templet:'#numberTpl', totalRow:true,},
					{field:'price',   	title:'单价',   	    align:'center', width:'4%',		edit:'text',templet:'#priceTpl'},
					{field:'sumPrice',   title:'单价总金额', align:'center', width:'8%', totalRow:true, style:"color:blue;"},
					{field:'systemPreferential',   			title:'系统优惠',   align:'center', width:'6%',	edit:'text', templet:'#systemPreferentialTpl'},
					{field:'sellerReadjustPrices',   		title:'卖家调价',   align:'center', width:'6%',	edit:'text', templet:"#sellerReadjustPricesTpl"},
					{field:'actualSum',  title:'实际金额',   align:'center', width:'8%',	totalRow:true, style:"color:blue;"},
			       ]]
		})
		table.on('edit(productTable)', function(obj){ 			//监听编辑表格单元
			if(isNaN(obj.value))
				layer.msg("修改无效！请输入正确的数字",{icon:2});
			else{
				var allPayment=0;
				for(var i=0;i<choosedProduct.length;i++){
					if(choosedProduct[i].commodityId==obj.data.commodityId){		//重新对该行的相关数据进行计算
						var choosed=choosedProduct[i];
						choosed[obj.field]=obj.value;
						choosed.sumPrice=choosed.number*choosed.price;
						choosed.actualSum=(choosed.price-(-choosed.sellerReadjustPrices)-choosed.systemPreferential)*choosed.number;
						choosedProduct[i][obj.field]=obj.value;
						choosedProduct[i].sumPrice=choosed.sumPrice;
						choosedProduct[i].actualSum=choosed.actualSum;
					}
					allPayment+=choosedProduct[i].actualSum;	//计算收款金额
				}
				$('#customPayment').val(allPayment);			
			}
			table.reload('productTable',{
				data:choosedProduct
			})
		});
		
		
		form.on('submit(sureAdd)',function(obj){					//确定添加按钮
			var data=obj.field;										//对添加数据的判断
			var msg='';
			if(choosedProduct.length==0)		
				msg="请选择商品";
			else if(isNaN(data.payment))
				msg="收款金额只能为数字";
			else if(data.payment<0)
				msg="收款金额不能为负数";
			if(msg!=''){
				layer.msg(msg,{icon:2});
				return;
			}
			layer.confirm('一旦添加完成，订单的数据无法修改，请确认是否输入有误！是否确认添加？',function(){
				var updataData=[];
				for(var i=0;i<choosedProduct.length;i++){			//取出真正需要的数据进行传参
					var c=choosedProduct[i];
					var t={
						commodityId : 	c.commodityId,
						number : 		c.number,
						price : 		c.price,
						status : 		c.status,
						warehouseId : 	c.inventory,
						sumPrice : 		c.sumPrice,
						actualSum : 	c.actualSum,
						systemPreferential : 	c.systemPreferential,
						sellerReadjustPrices : 	c.sellerReadjustPrices,
					}
					updataData.push(t);
				}
				data.childOrder=JSON.stringify(updataData);
				var load=layer.load(1);
				$.ajax({
					url:"${ctx}/inventory/addOnlineOrder",
					type:"post",
					data:data,
					success:function(result){
						if(0==result.code){
							$('#resetAll').click();
							layer.msg(result.message,{icon:1});
						}
						else
							layer.msg(result.message,{icon:2});
						layer.close(load);
					},
					error:function(result){
						layer.msg("发生异常错误",{icon:2});
						layer.close(load);
					}
				})
			})
		})
		
		table.on('toolbar(productTable)',function(obj){				//新增、删除按钮
			switch(obj.event){
			case 'add': openChooseProductWin(); break;
			case 'delete': deletes(); break;
			}
		})
		
		$('#resetAll').on('click',function(){						//清空表单
			choosedProduct = [];
			rederSelect();
			table.reload('productTable',{
				data : choosedProduct,
			});
			form.render();
			layer.msg('清空成功',{icon:1});
		})
		
		$('#customName').on('click',function(){						//客户选择按钮
			openCustomWindow();
		})
		
		form.on('submit(searchCustom)',function(obj){				//搜索客户
			table.reload('customTable',{
				where:obj.field,
				page: {  curr: 1   }
			})
		})
		$('#addCustom').on('click',function(){
			renderSelectAdd();										//渲染地址下拉框
			openAddNewCustomWin();
		})
		
		
		$('#sure').on('click',function(){								
			sureChoosed();											//确定商品选择
		})
		
		$('#addNewProduct').on('click',function(){						//添加新产品
			openAddNewPorductWin();
		})
		
		//添加新产品-------弹窗按钮---1个：确定添加
		form.on('submit(sureAddNew)',function(obj){						//监听按钮
			var load=layer.load(1);
			$.ajax({
				url:'${ctx}/inventory/addCommodity',
				type:"post",
				data:obj.field,
				success:function(result){
					if(0==result.code){
						table.reload('productListTable');
						layer.close(addNewPorductWin);
						layer.msg(result.message,{icon:1});
					}
					else
						layer.msg(result.message,{icon:2});
					layer.close(load);
				}
			})
		})
		//添加新客户--------弹窗按钮---1个：确定添加
		form.on('submit(sureAddCustom)',function(obj){
			var load= layer.load(1);
			$.ajax({
				url:'${ctx}/inventory/addOnlineCustomer',
				type:"post",
				data:obj.field,
				success:function(result){
					if(0==result.code){
						table.reload('customTable');
						layer.close(addNewCustomWin);
						layer.msg(result.message,{icon:1});
					}
					else
						layer.msg(result.message,{icon:2});
					layer.close(load);
				}
			})
		})
		$('#resetCustomAdd').on('click',function(){									//重置新添加客户内容
			renderSelectAdd();														//重置地址下拉框信息
			document.getElementById('addCustomGrade').options[0].selected=true;		//更改下拉框显示内容
			document.getElementById('addCustomType').options[0].selected=true;
			form.render();
		})	
		//产品选择隐藏框，搜索按钮监听
		form.on('submit(searchProduct)',function(obj){
			var data = obj.field;
			defaultPrice=data.defaultPriceSelect==''?'tianmao':data.defaultPriceSelect;
			table.reload('productListTable',{
				where:{ skuCode : obj.field.textSearch},
				page: {  curr: 1   }
			});
		})
		
		//以下是功能函数---------------------------------------------------------------------------------------------------
		var defaultPrice='tianmao';  			//默认发货价
		function openChooseProductWin(){
			chooseProductWin = layer.open({		//选择产品窗口
				type:1,
				title:'选择产品',
				area:['80%','90%'],
				content:$('#addProductDiv'),
			})
			table.render({
				elem:'#productListTable',
				size:'lg',
				url:'${ctx}/inventory/commodityPage',
				loading:true,
				page:true,
				request:{
					pageName:'page',
					limitName:'size'
				},
				parseData:function(ret){	
					return{ code:	ret.code,msg:	ret.message,data:	ret.data.rows,count:	ret.data.total,}
				},
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'商品名称', field:'skuCode',},
				       {align:'center', title:'成本', 	  field:'cost',},
				       {align:'center', title:'发货仓库',  	  templet:getInventorySelectHtml()},
				       {align:'center', title:'销售价',        templet:getPriceSelectHtml()},
				       {align:'center', title:'备注', 	  field:'remark',}, 
				      ]],
		      	done: function (res, curr, count) {		
		      		var data=res.data;
		      		for(var i=0;i<data.length;i++){
		      			if(data[i].inventorys.length>0)
		      				data[i].inventory = data[i].inventorys[0].warehouse.id;
		      			switch(defaultPrice){
			      		case 'tianmao':data[i].price=data[i].tianmaoPrice;   break;
			      		case '_1688':  data[i].price=data[i].oseePrice; break;
			      		case 'offline':data[i].price=data[i].offlinePrice;break;
			      		}
		      		}
	                form.render(); 
	            },
			});
			form.render();
		}
		form.on('select(selectPrice)', function (data) {		//监听数据表格中的 价格选择下拉框
            var elem = $(data.elem);
            var trElem = elem.parents('tr');
            var tableData = table.cache['productListTable'];
            // 更新到表格的缓存数据中，才能在获得选中行等等其他的方法中得到更新之后的值
            // trElem.data('index')  当前行数据的索引
            // 修改缓存中的数据，字段为price
            tableData[trElem.data('index')]['price'] = data.value;
        });				
		form.on('select(selectInventory)', function (data) {
            var elem = $(data.elem);
            var trElem = elem.parents('tr');
            var tableData = table.cache['productListTable'];
            tableData[trElem.data('index')]['inventory'] = data.value;
        });
		
		function getPriceSelectHtml(){
			return function(d) {			//d为当行数据
				var html='<select lay-filter="selectPrice" > '+
						'<option value="'+d.tianmaoPrice+'" '+ (defaultPrice=="tianmao"?"selected":"") +'>天猫售价：'+d.tianmaoPrice+'</option>'+
						'<option value="'+d.oseePrice+'"    '+ (defaultPrice=="_1688"?"selected":"") +'>1688批发价：'+d.oseePrice+'</option>'+
						'<option value="'+d.offlinePrice+'" '+ (defaultPrice=="offline"?"selected":"") +'>线下批发价：'+d.offlinePrice+'</option>'+
						'</select>';
				return html;

			};
		}
		function getInventorySelectHtml() {
			return function(d) {		//d为当行数据
				var inv = d.inventorys;
				var html='<span style="color:red;">暂无库存！无法发货</span>';
				if(inv.length>0){
					html='<select lay-filter="selectInventory">'
					for(var i=0;i<inv.length;i++){
						html+='<option value="'+inv[i].warehouse.id+'">'+inv[i].warehouse.name+':'+inv[i].number+'</option>';
					}
					html+='</select>'
				}
				return html; 
			};
		};
		table.on('rowDouble(customTable)', function(obj){
			layer.close(customChooseWin);
			//此处再进行相应的数据回显
			$('#customNames').val(obj.data.name);
			$('#customRealName').val(obj.data.buyerName);
			$('#customPhone').val(obj.data.phone);
			$('#customType').val(obj.data.type);
			$('#customAddress').val(obj.data.address);
			$('#customZipCode').val(obj.data.zipCode);
			$('#customId').val(obj.data.id);
			$('#province').val('');
			$('#city').val('');
			$('#area').val('');
			if(null!=obj.data.provinces)
				$('#province').val(obj.data.provinces.id);
			if(null!=obj.data.provinces){
				getdataOfSelect(obj.data.provinces.id,'city');
				$('#city').val(obj.data.city.id);
			}
			if(null!=obj.data.city){
				getdataOfSelect(obj.data.city.id,'area');
				$('#area').val(obj.data.county.id);
			}
			form.render();
			layer.msg("客户选择成功",{icon:1});
		});
		function openCustomWindow(){		//选择客户弹窗
			customChooseWin=layer.open({
				title:'选择客户',
				area:['80%','80%'],
				type:1,
				content:$('#customNameDiv')
			});
			form.render();
			table.render({
				url:"${ctx}/inventory/onlineCustomerPage",
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
						msg:ret.message,
						data:ret.data.rows,
						count:ret.data.total,
					}
				},
				cols:[[
				       {align:'center',title:'客户名称',	field:'name'},
				       {align:'center',title:'客户类别',	field:'type'},
				       {align:'center',title:'手机',		field:'phone'},
				       {align:'center',title:'所在地',	field:'address'},
				       ]]
			})
		}
		function openAddNewPorductWin(){		//添加新产品窗口
			addNewPorductWin = layer.open({								
				type:1,
				title:'添加产品',
				content:$('#addProductWindow'),
				area:['60%','50%']
			})
			form.render();
		}
		function openAddNewCustomWin(){
			addNewCustomWin = layer.open({
				type:1,
				title:'添加新客户',
				area:['60%','50%'],
				content:$('#addCustomWindow')
			})
			form.render();
		}
		function deletes(){
			var choosed = layui.table.checkStatus('productTable').data;
			if(choosed.length==0){
				layer.msg("请选择商品删除",{icon:2});
				return;
			}
			for(var i=0;i<choosed.length;i++){
				for(var j=0;j<choosedProduct.length;j++){
					if(choosed[i].commodityId==choosedProduct[j].commodityId){
						choosedProduct.splice(j,1);
						break;
					}
				}
			}
			var allPayment=0;
			for(var j=0;j<choosedProduct.length;j++)		//重新对价格计算
				allPayment+=choosedProduct[j].actualSum;
			$('#customPayment').val(allPayment);	
			table.reload('productTable',{
				data:choosedProduct,
			})
		}
		function sureChoosed(){												//确定商品选择
			var choosed=layui.table.checkStatus('productListTable').data;
			if(choosed.length<1){
				layer.msg("请选择相关商品",{icon:2});
				return false;
			}
			for(var i=0;i<choosed.length;i++){
				var j=0;
				for(var j=0;j<choosedProduct.length;j++){	
					if(choosedProduct[j].commodityId==choosed[i].id)	{											
						//如果商品已经存在列表，判断该产品的发货仓库或者价格是否与之前选择的商品不同
						var msg='';
						if(choosedProduct[j].inventory != choosed[i].inventory)									//如果本次选择的的与已选中的不同
							msg='相同的产品无法同时从多个仓库发货，请重新选择商品的发货仓库';
						else if(choosed[i].price!=choosedProduct[j].price)			//如果产品没有价格，且默认的与已选中的不同
							msg='相同的产品无法同时选择多个价格';
						if(msg!=''){
							layer.msg(msg,{icon:2});
							return;
						}
						choosedProduct[j].number++;
						choosedProduct[j].sumPrice=choosedProduct[j].number*choosedProduct[j].price;
						$('#customPayment').val($('#customPayment').val()-choosedProduct[j].actualSum);			//减去之前的价格
						choosedProduct[j].actualSum=(choosedProduct[j].price-choosedProduct[j].sellerReadjustPrices-choosedProduct[j].systemPreferential)*choosedProduct[j].number;
						$('#customPayment').val($('#customPayment').val()-(-choosedProduct[j].actualSum));		//重新加上计算后的价格
						break;
					}
				}
				if(!(j<choosedProduct.length) || choosedProduct.length==0){			//如果不存在，取出真正需要的数据，用于json转换并传到后台。
					//如果商品不存在列表，需要添加进选择的商品列表中，并判断价格与仓库的选择
					if(choosed[i].inventory==undefined){
						layer.msg('商品无库存，无法选择',{icon:2});
						return;
					}
					var orderChild={
							skuCode:choosed[i].skuCode,		
							name:choosed[i].name,			
							commodityId:choosed[i].id,		
							number:1,						
							sumPrice:choosed[i].price,		
							systemPreferential:0,			
							sellerReadjustPrices:0,			
							actualSum : choosed[i].price,		
							status:'WAIT_SELLER_SEND_GOODS',
							inventory:choosed[i].inventory,	//发货仓库
							price:choosed[i].price,			//发货选择的价格
					}
					$('#customPayment').val($('#customPayment').val()-(-orderChild.actualSum));		//使用-计算、确保不会发生字符串拼接
					choosedProduct.push(orderChild);
				}
			}
			table.reload('productTable',{
				data:choosedProduct
			});
			layer.msg('添加成功',{icon:1});
			layer.close(chooseProductWin);	
		}
		
		form.on('select', function(data){					//监听地址下拉框的选择
			var select='';
			var html="";
			var parentId=data.value;
			switch(data.elem.id){
			case 'province': 	getdataOfSelect(parentId,'city'); parentId=$('#city').val();		//此处不加break,即选择的为省级下拉框时，市、县两级同时渲染
			case 'city':		select='area';	break;
			case 'addProvince': getdataOfSelect(parentId,'addCity'); parentId=$('#addCity').val();
			case 'addCity':		select='addArea';break;
			}
			if(select!=''){
				getdataOfSelect(parentId,select);
			}
		}); 
		
		function rederSelect(){					//渲染下拉框,默认主界面3个地址下拉框，添加新客户窗口3个地址下拉框
			getdataOfSelect(0,'province');
			getdataOfSelect('110000','city');
			getdataOfSelect('110100','area');
		}
		function renderSelectAdd(){
			getdataOfSelect(0,'addProvince');
			getdataOfSelect('110000','addCity');
			getdataOfSelect('110100','addArea');
		}
		function getdataOfSelect(parentId,select){			//根据父id获取下级地址菜单的信息
			var child=[];
			$.ajax({
				url:"${ctx}/regionAddress/queryProvince",
				data:{parentId:parentId},
				async:false,				//此处需要取消异步，否则多个下拉框同时渲染会发生错误
				success:function(result){
					if(0==result.code){
						var html='';
						var data=result.data;	
						for(var i=0;i<data.length;i++){
							html+='<option value="'+data[i].id+'">'+data[i].regionName+'</option>';
						}
						$('#'+select).html(html);
						form.render();
					}
					else{
						layer.msg(result.message,{icon:2});
					}
				}
			});
		}
		function getAllUser(){
			$.ajax({
				url:'${ctx}/system/user/pages?size=99',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allUser.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].userName
							})
						renderUserSelect('userIdSelect');
					}
				}
			})
		}
		function renderUserSelect(select){			//根据id渲染客服下拉框
			var html='';
			for(var i=0;i<allUser.length;i++){
				html+='<option value="'+allUser[i].id+'">'+allUser[i].userName+'</option>';
			}
			$('#'+select).html(html);
			form.render();
		}
		function getAllInventory(){					//获取所有仓库
			$.ajax({
				url:'${ctx}/basedata/list?type=inventory',
				async:false,       					//此处需要取消异步，否则商品选择仓库的默认值无法设置
				success:function(r){
					if(0==r.code){
						allInventory=r.data;
						renderInventorySelect('warehouseSelect');	
					}
				}
			})
		}
		function renderInventorySelect(select){				//根据id渲染仓库下拉框
			var html='';
			for(var i=0;i<allInventory.length;i++){
				html+='<option value="'+allInventory[i].id+'" '+( allInventory[i].flag==0?"disabled":"")+'>'+allInventory[i].name+'</option>';
			}
			$('#'+select).html(html);			
		}
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		}) 
	}//end defined
)
</script>
</html>