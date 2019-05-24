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
				<td>操作</td>
				<td colspan="3" style="text-align:left;">
					<button class="layui-btn layui-btn-sm" lay-submit       type="button" lay-filter="sureAdd">确定添加</button>
					<button class="layui-btn layui-btn-sm layui-btn-danger" type="reset" id="resetAll">清空</button>
					<input type="hidden" name="onlineCustomerId" id="customId">
				</td>
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
				<td><a style="color:blue" href="#"  id="customName">客户名称：</a></td>	
				<td><input type="text" class="layui-input" name="name" id="customNames" lay-verify="required" readonly ></td>
				<td>订单编号：</td>			
				<td><input type="text" class="layui-input" name="tid"></td>
				
				
				<td>所属客服：</td>			
				<td><select name="userId"><option value="1">客服1</option></select></td>
			</tr>
			<tr>
				<td>收货人：</td>			
				<td><input type="text" class="layui-input" id="customRealName" name="buyerName"></td>
				<td>收款金额：</td>			
				<td><input type="text" class="layui-input" name="payment" id="customPayment"></td>
				
				<td>发货仓库：</td>			
				<td><select name="warehouse">
									 <option  value="0">主仓库</option>
									 <option  value="1">客供仓库</option>
									 <option  value="2">次品</option></select></td>		
			</tr>
			<tr>
				<td>手机：</td>			
				<td><input type="text" class="layui-input" id="customPhone" name="phone"></td>
				<td>整单优惠：</td>			
				<td><input type="text" class="layui-input" name="allBillPreferential"></td>
				<td>邮费：</td>			
				<td><input type="text" class="layui-input" name="postFee"></td>
			</tr>
			<tr>
				<td>所在地：</td>			
				<td colspan="3">
						<div class="layui-form-item">
								<div class="layui-input-inline"><select lay-search id="province" name="provincesId"></select></div>
								<div class="layui-input-inline"><select lay-search id="city" name="cityId"></select></div>
								<div class="layui-input-inline"><select lay-search id="area" name="countyId"></select></div>
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
				<td rowspan="2">旗帜：</td>			
				<td rowspan="2"></td>
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
			<td><select name='defaultInventorySelect' id="defaultInventorySelect">				
					<option value="">设置默认发货仓库</option></select></td>	<td>&nbsp;</td>
			<td><select name='defaultPriceSelect'>
					<option value="">设置默认销售价</option>
					<option value="tianmao">天猫销售价</option>
					<option value="_1688">1688销售价</option>
					<option value="offline">线下批发价</option></select></td>	<td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" >确定</button></td><td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="addNewProduct" >添加新商品</button></td>			 <td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="sure" >确定添加</button></td>						<td>&nbsp;</td>
		</tr>
	</table>
	<table class="layui-table" id="productListTable" lay-filter="productListTable"></table>
</div>

<!-- 客户选择隐藏框 -->
<div id="customNameDiv" style="display:none;">
	<table class="layui-form">
		<tr>
			<td><select name=""><option value="">按来往单位分类</option></select></td>			<td>&nbsp;</td>
			<td><select name=""><option value="">按单位名称</option></select></td>				<td>&nbsp;</td>
			<td><input type="text" class="layui-input"></td>									<td>&nbsp;</td>
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
		<td><input type="text" class="layui-input" lay-verify="number"		name="OSEEPrice"></td></tr>
	<tr><td>天猫单价</td>
		<td><input type="text" class="layui-input" lay-verify="number"		name="tianmaoPrice"> </td>
		<td>线下批发价</td>
		<td><input type="text" class="layui-input" lay-verify="number"		name="offlinePrice"></td></tr>
	<tr><td>商品重量</td>
		<td><input type="text" class="layui-input" lay-verify="number"		name="weight" ></td>
		<td>商品高度</td>
		<td><input type="text" class="layui-input" lay-verify="number"		name="size" ></td></tr>
	<tr><td>商品成本</td>
		<td><input type="text" class="layui-input" lay-verify="number"		name="cost"></td>
		<td>广宣成本</td>
		<td><input type="text" class="layui-input" lay-verify="number"		name="propagandaCost" ></td></tr>
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
		<td colspan="2"><button type="reset" class="layui-btn layui-btn-sm layui-btn-danger" id="resetCustomAdd">清空</button>
						<button class="layui-btn layui-btn-sm" type="button" lay-submit lay-filter="sureAddCustom">确定</button></td></tr>
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
			defaultInventory,			//商品默认发货仓库。
			defaultPrice,
			allInventory=[];			//所有的仓库
		var chooseCuctomWin				//各个弹窗
			,chooseProductWin
			,addNewProductWin
			,addNewCustomWin;
		
		$('#headerTool').find("td:even").css({backgroundColor:"rgba(65, 161, 210, 0.45)",padding:"1px"}); //表格颜色
		rederSelect();						//渲染所有地址下拉框，给出默认值
		renderSelectAdd();
		getAllInventory();					//获取所有仓库
		renderDefaultInventorySelect();		//渲染默认仓库下拉框
		defaultInventory=allInventory.length>0?allInventory[0].id:'';	//默认值设置为第一个仓库，天猫销售价
		defaultPrice='tianmao';
		form.render();
		
		table.render({
			elem:"#productTable", 
			toolbar:'#productTableToolbar',
			height:'565',
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
		
		
		//主页面一个四个按钮。确定添加、新增、删除、客户名分别进行绑定
		form.on('submit(sureAdd)',function(obj){					//确定添加按钮
			layer.confirm('一旦添加完成，订单的数据无法修改，请确认是否输入有误！是否确认添加？',function(){
				var data=obj.field;
				if(choosedProduct.length==0){
					layer.msg("请选择商品",{icon:2});
					return;
				}
				var updataData=[];
				for(var i=0;i<choosedProduct.length;i++){			//取出真正需要的数据进行传参
					var c=choosedProduct[i];
					var t={
						commodityId : 	c.commodityId,
						skuCode : 		c.skuCode,
						number : 		c.number,
						price : 		c.price,
						status : 		c.status,
						inventory : 	c.inventory,
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
		
		$('#resetAll').on('click',function(){
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
		
		//选择客户弹窗按钮监听---三个按钮监听:搜索、添加新客户、刷新客户列表
		form.on('submit(searchCustom)',function(obj){
			layer.msg('搜索');
		})
		$('#addCustom').on('click',function(){
			openAddNewCustomWin();
		})
		
		
		//选择商品弹窗按钮监听.添加商品弹窗共4个按钮监听。搜索、添加新商品、确定添加、刷新
		$('#sure').on('click',function(){								
			sureChoosed();											//如果选择成功
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
				},
				error:function(result){
					layer.msg('发生未知错误',{icon:2});
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
				},
				error:function(result){
					layer.msg('发生未知错误',{icon:2});
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
			defaultInventory=data.defaultInventorySelect==''?allInventory[0].id:data.defaultInventorySelect;
			defaultPrice=data.defaultPriceSelect==''?'tianmao':data.defaultPriceSelect;
			table.reload('productListTable',{
				where:{ skuCode : obj.field.textSearch}
			});
		})
		
		//以下是功能函数---------------------------------------------------------------------------------------------------
		
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
				height:'600',
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
		      	done: function (res, curr, count) {				//回调函数进行数据表格中的下拉框进行渲染
	                layui.each( $('select'), function (index, item) {
	                    var elem = $(item);
	                	if(elem.data('value')!=undefined)		//进行初始设置默认值 elem.val(elem.data('value'))
	                    	elem.val(elem.data('value')).parents('div.layui-table-cell').css('overflow', 'visible');
	                });
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
            var str=data.value;
            tableData[trElem.data('index')]['price'] = str.substring(0,str.length-3);
            var type=str.substring(str.length-2,str.length);
            if(type=='tm')
            	tableData[trElem.data('index')]['priceType']='tianmao';
            else if(type=='16')
            	tableData[trElem.data('index')]['priceType']='_1688';
            else if(type=='xx')
            	tableData[trElem.data('index')]['priceType']='offline';
        });				
		form.on('select(selectInventory)', function (data) {
            var elem = $(data.elem);
            var trElem = elem.parents('tr');
            var tableData = table.cache['productListTable'];
            tableData[trElem.data('index')]['inventory'] = data.value;
        });
		
		function getPriceSelectHtml(){
			return function(d) {			//d为当行数据
				var price='';				//价格加上后缀，区分不同的value值，防止相同的价格引起的bug
				if(defaultPrice=='tianmao')
					price=d.tianmaoPrice+'_tm';
				else if(defaultPrice=="_1688")
					price=d.oSEEPrice+'_16';
				else if(defaultPrice=="offline")
					price=d.offlinePrice+'_xx';
				var html='<select id="selectPrice" lay-filter="selectPrice" lay-search="true" data-value="'+price+'" aaa="a"> '+
						'<option value="'+d.tianmaoPrice+'_tm">天猫售价：'+d.tianmaoPrice+'</option>'+
						'<option value="'+d.oSEEPrice+'_16">1688批发价：'+d.oSEEPrice+'</option>'+
						'<option value="'+d.offlinePrice+'_xx">线下批发价：'+d.offlinePrice+'</option>'+
						'</select>';
				return html;

			};
		}
		function getInventorySelectHtml() {
			return function(d) {		//d为当行数据
				if(allInventory.length==0){
					return '没有可用仓库';
				}
				var html='<select id="selectInventory" lay-filter="selectInventory" lay-search="true" data-value="'+defaultInventory+'" aaa="a"  > ';
				for(var i=0;i<allInventory.length;i++){
					html+='<option value="'+allInventory[i].id+'">'+allInventory[i].name+'</option>';
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
				title:'选择来往单位',
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
			var allPayment=$('#customPayment').val();
			for(var i=0;i<choosed.length;i++){
				for(var j=0;j<choosedProduct.length;j++){
					if(choosed[i].commodityId==choosedProduct[j].commodityId){
						allPayment-=choosedProduct[j].actualSum;	//计算收款金额
						choosedProduct.splice(j,1);
						break;
					}
				}
			}
			$('#customPayment').val(allPayment);	
			table.reload('productTable',{
				data:choosedProduct,
			})
		}
		function sureChoosed(){												//确定商品选择
			var choosed=layui.table.checkStatus('productListTable').data;
			if(choosed.length<1){
				layer.msg("请选择相关商品");
				return false;
			}
			for(var i=0;i<choosed.length;i++){
				var j=0;
				for(var j=0;j<choosedProduct.length;j++){	
					if(choosedProduct[j].commodityId==choosed[i].id)	{											//判断选择的商品是否已存在选择列表
						//如果商品已经存在列表，判断该产品的发货仓库或者价格是否与之前选择的商品不同
						if(choosed[i].inventory==undefined && choosedProduct[j].inventory != defaultInventory){		//如果产品没有选择仓库，且默认的与已选中的不同
							layer.msg('相同的产品无法同时从多个仓库发货，请重新选择商品的发货仓库',{icon:2});
							return;
						}
						if(choosed[i].inventory!=undefined && choosedProduct[j].inventory != choosed[i].inventory){	//如果产品选择了仓库，且选择的仓库的与已选中的不同
							layer.msg('相同的产品无法同时从多个仓库发货，请重新选择商品的发货仓库',{icon:2});
							return;
						}
						if(choosed[i].priceType==undefined && choosedProduct[j].priceType != defaultPrice){			//如果产品没有价格，且默认的与已选中的不同
							layer.msg('相同的产品无法同时选择多个价格',{icon:2});
							return;
						}
						if(choosed[i].priceType!=undefined && choosedProduct[j].priceType != choosed[i].priceType){	//如果产品选择了价格，且选择的仓库的与已选中的不同
							layer.msg('相同的产品无法同时选择多个价格',{icon:2});
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
					if(choosed[i].price==undefined ){	//如果该商品的价格没有赋值，则使用默认值（通过全局变量默认的价格来赋值）
						var price='';
						if(defaultPrice=='tianmao')
							price=choosed[i].tianmaoPrice;
						else if(defaultPrice=='_1688')
							price=choosed[i].oSEEPrice;
						else if(defaultPrice=='offline')
							price=choosed[i].offlinePrice;
						choosed[i].price=price;
						choosed[i].priceType=defaultPrice;
					}
					if(choosed[i].inventory==undefined)		//默认发货仓库
						choosed[i].inventory=defaultInventory;
					var orderChild={
							skuCode:choosed[i].skuCode,		//商品编号
							name:choosed[i].name,			//商品名称
							commodityId:choosed[i].id,		//商品id
							number:1,						//商品数量
							price:choosed[i].price,			//商品单价
							priceType:choosed[i].priceType,	//商品的价格类型：tianmao\_1688\_offline
							inventory:choosed[i].inventory,	//发货仓库
							sumPrice:choosed[i].price,		//单价总金额
							systemPreferential:0,			//系统优惠
							sellerReadjustPrices:0,			//卖家调价
							actualSum:choosed[i].price,		//实际金额
							status:'WAIT_SELLER_SEND_GOODS',//状态默认值
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
		
		/* form.on('select', function(data){		//监听6个地址下拉框的选择
			var select='';
			var html="";
			switch(data.elem.id){
			case 'province': select='city'; $('#city').html(html);$('#area').html(html);break;
			case 'city':	select='area';	$('#area').html(html);break;
			case 'addProvince': select='addCity'; $('#addCity').html(html);$('#addArea').html(html);break;
			case 'addCity':	select='addArea';	$('#addArea').html(html);break;
			}
			if(select!=''){
				getdataOfSelect(data.value,select);
			}
		});  */
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
		function getdataOfSelect(parentId,select){			//根据父id获取下级地址菜单的信息，并拼接成html返回
			var child=[];
			$.ajax({
				url:"${ctx}/regionAddress/queryProvince",
				data:{parentId:parentId},
				async:false,
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
		
		function getAllInventory(){					//获取所有仓库
			$.ajax({
				url:'${ctx}/basedata/list?type=inventory',
				async:false,
				success:function(r){
					if(0==r.code)
						allInventory=r.data;
				}
			})
		}
		function renderDefaultInventorySelect(){	//渲染默认仓库下拉框
			var html='<option value="">设置默认发货仓库</option>';
			for(var i=0;i<allInventory.length;i++)
				html+='<option value="'+allInventory[i].id+'">'+allInventory[i].name+'</option>'
			$('#defaultInventorySelect').html(html);			
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