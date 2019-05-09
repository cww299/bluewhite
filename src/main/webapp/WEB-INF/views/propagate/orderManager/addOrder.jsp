<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
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
	<div class="layui-card-body" style="height:850px">	<!-- 主页面内容 -->
		<table class="layui-form" style="width:100%" id="headerTool">
			<tr>
				<td><a style="color:blue" href="#"  id="customName">客户名称：</a></td>			
				<td><input type="text" class="layui-input" name="sellerNick" id="customNames"></td>
				<td>订单编号：</td>			
				<td><input type="text" class="layui-input" name="tid"></td>
				<td>订单状态：</td>			
				<td><input type="text" class="layui-input" name="status"></td>
			</tr>
			<tr>
				<td>收货人：</td>			
				<td><input type="text" class="layui-input" name="" id="customRealName"></td>
				<td>收款金额：</td>			
				<td><input type="text" class="layui-input" name="payment"></td>
				<td>所属客服：</td>			
				<td><input type="text" class="layui-input"></td>
			</tr>
			<tr>
				<td>手机：</td>			
				<td><input type="text" class="layui-input" id="customPhone"></td>
				<td>整单优惠：</td>			
				<td><input type="text" class="layui-input" name="allBillPreferential"></td>
				<td>发货仓库：</td>			
				<td><input type="text" class="layui-input" name="warehouse"></td>
			</tr>
			<tr>
				<td>客户类别：</td>			
				<td><input type="text" class="layui-input" id="customType"></td>
				<td>邮费：</td>			
				<td><input type="text" class="layui-input" name="postFee"></td>
				<td>电话：</td>			
				<td><input type="text" class="layui-input"></td>
			</tr>
			<tr>
				<td>所在地：</td>			
				<td colspan="3">
						<div class="layui-form-item">
								<div class="layui-input-inline"><select lay-search id="province"></select></div>
								<div class="layui-input-inline"><select lay-search id="city"></select></div>
								<div class="layui-input-inline"><select lay-search id="area"></select></div>
								</div></td>
				<td>物流方式：</td>			
				<td><input type="text" class="layui-input" name="shippingType"></td>
			</tr>
			<tr>
				<td>详细地址：</td>			
				<td colspan="3"><input type="text" class="layui-input" id="customAddress" placeholder="您可以直接黏贴淘宝或拼多多的收货地址,会自动提取省市区和收货人信息"></td>
				<td>邮编：</td>			
				<td><input type="text" class="layui-input" id="customZipCode"></td>
			</tr>
			<tr>
				<td>卖家备注：</td>			
				<td colspan="3"><input type="text" class="layui-input" placeholder="" name="sellerMemo"></td>
				<td rowspan="2">旗帜：</td>			
				<td rowspan="2"><button class="layui-btn" lay-submit lay-filter="sureAdd">确定添加</button></td>
			</tr>
			<tr>
				<td>内部备注：</td>			
				<td colspan="3"><input type="text" class="layui-input" placeholder=""></td>
			</tr>
		</table>
		<table class="layui-table" id="productTable" lay-filter="productTable"></table>	<!-- 已选中的产品列表  -->
	</div>
</div>

</body>

<!-- 商品选择隐藏框 -->
<div id="addProductDiv" style="display:none;">
	<table class="layui-form" lay-filter="productListTool">
		<tr>
			<td><select><option value="">出售中			</option></select></td>			<td>&nbsp;</td>
			<td><select><option value="">按产品分类		</option></select></td>			<td>&nbsp;</td>
			<td><select><option value="">按淘宝宝贝分类	</option></select></td>			<td>&nbsp;</td>
			<td><select><option value="">按产品名称		</option></select></td>			<td>&nbsp;</td>
			<td><input type="text" class="layui-input"></td>							<td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" >搜索</button></td><td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="addNewProduct" >添加新商品</button></td>			 <td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="refreshProduct" >刷新</button></td>					 <td>&nbsp;</td>
			<td><button type="button" class="layui-btn layui-btn-sm" id="sure" >确定添加</button></td>
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
			<td><button lay-submit 	lay-filter="searchCustom"	type="button" class="layui-btn layui-btn-sm">搜索</button>				<td>&nbsp;</td>
			<td><button id="addCustom"	type="button" class="layui-btn layui-btn-sm">添加新客户</button>		<td>&nbsp;</td>
			<td><button id="refreshCustom"	type="button" class="layui-btn layui-btn-sm">刷新</button>			<td>&nbsp;</td>
			<td><span class="layui-badge">小提示：双击选中客户对象</span></td>
		</tr>
	</table>
	<table id="customTable" lay-filter="customTable" class="layui-table"></table>
</div>


<!-- 添加新商品隐藏框 -->
<table class="layui-form layui-table" style="display:none;" id="addProductWindow">
	<tr><td>商品名称</td>
		<td><input type="text" class="layui-input" lay-verify="required" name="name"></td>
		<td>商品重量</td>
		<td><input type="text" class="layui-input" name="weight"></td></tr>
	<tr><td>商品材质</td>
		<td><input type="text" class="layui-input" name="material"></td>
		<td>商品规格</td>
		<td><input type="text" class="layui-input" lay-verify="required" name="size"></td></tr>
	<tr><td>商品填充物</td>
		<td><input type="text" class="layui-input" name="fillers"></td>
		<td>商品成本</td>
		<td><input type="text" class="layui-input" name="cost"></td></tr>
	<tr><td>库存数量</td>
		<td><input type="text" class="layui-input" name="quantity"></td>
		<td>仓库类型</td>
		<td><input type="text" class="layui-input" name="warehouse"></td></tr>
	<tr><td>商品单价</td>
		<td><input type="text" class="layui-input" lay-verify="required" name="price"></td>
		<td>广宣成本</td>
		<td><input type="text" class="layui-input" name="propagandaCost"></td></tr>
	<tr><td>商品编号</td>
		<td><input type="text" class="layui-input" lay-verify="required" name="number"></td>
		<td>备注</td>
		<td><textarea type="text" class="layui-input" name="remark"></textarea></td></tr>
	<tr><td colspan="4"><button lay-submit lay-filter="sureAddNew" class="layui-btn layui-btn-sm">确定</button></td></tr>
</table>

<!-- 添加客户隐藏框  -->
<table class="layui-form layui-table" style="display:none;" id="addCustomWindow">
	<tr><td>买家姓名</td>
		<td><input type="text" class="layui-input" name="buyerName" lay-verify="required"></td>
		<td>客户名称</td>
		<td><input type="text" class="layui-input" name="name" lay-verify="required"></td></tr>
	<tr><td>客户等级</td>
		<td><input type="text" class="layui-input" name="grade"></td>
		<td>客户类型</td>
		<td><input type="text" class="layui-input" name="type"></td></tr>
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
		<td colspan="2"><button class="layui-btn layui-btn-sm" lay-submit lay-filter="sureAddCustom">确定</button></td></tr>
</table>

<!-- 已添加商品表的工具栏  -->
<script type="text/html" id="productTableToolbar">
<div class="layui-button-container">
	<span class="layui-btn layui-btn-sm" lay-event="add">选择商品</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除商品</span>
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
		
		var choosedProduct=[];			//用户已经选择上的产品
		var chooseCuctomWin				//各个弹窗
			,chooseProductWin
			,addNewProductWin
			,addNewCustomWin;
		
		getdataOfSelect(0,'province');	//渲染province地址下拉框
		getdataOfSelect(0,'addProvince');
		$('#headerTool').find("td:even").css({backgroundColor:"rgba(65, 161, 210, 0.45)",padding:"1px"}); 
		form.render();
		
		table.render({
			elem:"#productTable", 
			toolbar:'#productTableToolbar',
			height:'580',
			loading:true,
			data:[],
			totalRow:true,
			page:false,
			cols:[[
			       {type:'checkbox',align:'center',fixed:'left'},
			       {field:'number',	title:'商品编号',	align:'center'},
			       {field:'name',	title:'商品名称',	align:'center'},
			       {field:'num',	title:'数量',       align:'center',		edit:'text',},
			       {field:'price',   title:'单价',   	align:'center',		edit:'text',},
			       {field:'price',   title:'金额',   	align:'center'},
			       {field:'price',   title:'系统优惠',   align:'center',		edit:'text',},
			       {field:'price',   title:'卖家调价',   align:'center',		edit:'text',},
			       {field:'price',   title:'实际金额',   align:'center',	},
			       /* 
			       {field:'price',   title:'金额',   		align:'center'},
			       {field:'cost',	title:'商品成本',	align:'center'},
			       {field:'warehouse',	title:'仓库类型',	align:'center'},
			       {field:'remark',	title:'备注', 	align:'center'},  */
			       ]]
			
		})
		
		table.on('edit(productTable)', function(obj){ //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
		  console.log(obj.value); //得到修改后的值
		  console.log(obj.field); //当前编辑的字段名
		  console.log(obj.data); //所在行的所有相关数据  
		});
		//主页面一个四个按钮。确定添加、新增、删除、客户名分别进行绑定
		form.on('submit(sureAdd)',function(obj){					//确定添加按钮
			layer.msg('确定添加');
			console.log(obj.field)
		})
		
		table.on('toolbar(productTable)',function(obj){				//新增、删除按钮
			switch(obj.event){
			case 'add': openChooseProductWin(); break;
			case 'delete': deletes(); break;
			}
		})
		
		$('#customName').on('click',function(){						//客户选择按钮
			openCustomWindow();
		})
		
		//选择客户弹窗按钮监听---三个按钮监听:搜索、添加新客户、刷新客户列表
		form.on('submit(searchCustom)',function(obj){
			layer.msg('搜索');
		})
		$('#addCustom').on('click',function(){
			//layer.msg('添加客户');
			openAddNewCustomWin();
		})
		$('#refreshCustom').on('click',function(){
			layer.msg('刷新客户列表');
		})
		
		//选择商品弹窗按钮监听.添加商品弹窗共4个按钮监听。搜索、添加新商品、确定添加、刷新
		$('#sure').on('click',function(){								
			if(sureChoosed())											//如果选择成功
				layer.close(chooseProductWin);							
		})
		$('#refresh').on('click',function(){
			layer.msg('刷新');
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
					return{
						code:	ret.code,msg:	ret.message,data:	ret.data.rows,count:	ret.total,
					}
				},
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'商品名称', field:'name',},
				       {align:'center', title:'成本', 	  field:'cost',},
				       {align:'center', title:'仓库类型', field:'warehouse',},
				       {align:'center', title:'总库存',   field:'quantity',},
				       {align:'center', title:'销售价',   field:'price',},
				       {align:'center', title:'备注', 	  field:'remark',}, 
				      ]],
			});
			form.render();
		}
		table.on('rowDouble(customTable)', function(obj){
			layer.close(customChooseWin);
			//此处再进行相应的数据回显
			console.log(obj.data)
			$('#customNames').val(obj.data.name);
			$('#customRealName').val(obj.data.buyerName);
			$('#customPhone').val(obj.data.phone);
			$('#customType').val(obj.data.type);
			$('#customAddress').val(obj.data.address);
			$('#customZipCode').val(obj.data.zipCode);
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
			table.reload('productTable',{
				data:choosedProduct,
			})
		}
		function sureChoosed(){
			var choosed=layui.table.checkStatus('productListTable').data;
			if(choosed.length<1){
				layer.msg("请选择相关商品");
				return false;
			}
			for(var i=0;i<choosed.length;i++){
				var j=0;
				for(var j=0;j<choosedProduct.length;j++){
					if(choosedProduct[j].number==choosed[i].number)	{			//判断选择的商品是否已存在选择列表
						choosedProduct[j].num++;
						break;
					}
				}
				if(!(j<choosedProduct.length) || choosedProduct.length==0){				//如果不存在
					choosed[i].num=1;
					choosedProduct.push(choosed[i]);
				}
			}
			table.reload('productTable',{
				data:choosedProduct
			});
			layer.msg('添加成功');
			return true;
		}
		
		form.on('select', function(data){					//监听地址下拉框的选择
			var select='';
			var html="<option value=''>请选择</option>";
			switch(data.elem.id){
			case 'province': select='city'; $('#city').html(html);$('#area').html(html);break;
			case 'city':	select='area';	$('#area').html(html);break;
			case 'addProvince': select='addCity'; $('#addCity').html(html);$('#addArea').html(html);break;
			case 'addCity':	select='addArea';	$('#addArea').html(html);break;
			}
			if(select!=''){
				getdataOfSelect(data.value,select);
			}
		}); 
		
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
		function getdataOfSelect(parentId,select){
			var child=[];
			$.ajax({
				url:"${ctx}/regionAddress/queryProvince",
				data:{parentId:parentId},
				async:false,
				success:function(result){
					if(0==result.code){
						var html='<option value="">请选择</option>';
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
		
	}
)
</script>
</html>