<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单列表</title>
</head>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
.layui-table, .layui-table-view {
     margin: 0px; 
}
#headerTool .layui-form-item .layui-input-inline{		/* 查看订单弹窗样式 */
	width:100px;
	margin-left: 5px;
	margin-right:0px;
}
#headerTool .layui-input, .layui-select, .layui-textarea {
    height: 28px;
}
#headerTool .layui-form-label {
    padding: 5px 5px;
    width: 10px;
}
#positionChoose{		/* 客户所在地选择隐藏框样式*/
    height: 300px;
    width: 400px;
    padding: 5px;
    border: 1px solid #d2d2d2;
    position: absolute;
    left: 47%;
    top: 4%;
    display: none;
    background-color: #cfe2db;
    z-index: 99999;
}
#positionChoose td{
	text-align:left;
}
td{
	text-align:center;
}
</style>
<body>
<div class="layui-card">
	<div class="layui-card-body ">
			<div class="layui-form">
				<div class="layui-form-item">
					<div class="layui-input-inline">
						<select lay-search name="userId" id='userIdSelect'><option value="">客服人员</option></select></div>
					<div class="layui-input-inline">
						<input type="text" name="onlineCustomerName" id='customNameSelect' class='layui-input' placeholder='客户名称'></div>
					<div class="layui-input-inline">
						<select lay-search name='status'><option value="">交易状态</option>
														<option  value="WAIT_SELLER_SEND_GOODS">等待卖家发货,即:买家已付款</option>
														<option  value="TRADE_NO_CREATE_PAY">没有创建支付宝交易</option>
														<option  value="WAIT_BUYER_PAY">等待买家付款</option>
														<option  value="SELLER_CONSIGNED_PART">卖家部分发货</option>
														<option  value="TRADE_BUYER_SIGNED">买家已签收,货到付款专用</option>
														<option  value="TRADE_FINISHED">交易成功</option>
														<option  value="WAIT_BUYER_CONFIRM_GOODS">等待买家确认收货,即:卖家已发货</option></select></div>
					<div class="layui-input-inline">
						<select lay-search name='shippingType'><option value="">物流方式</option>
																<option  value="free"		>卖家包邮</option>
																 <option  value="post"		>平邮</option>
																 <option  value="express"	>快递</option>
																 <option  value="ems"		>EMS</option>
																 <option  value="virtual"	>虚拟发货</option></select></div>
					<div class="layui-input-inline" style='width:70px;'><a href="#" style="color:blue;" id="customPosition">所在省份</a></div>  <!-- provincesId收货人的所在省份 -->
					<div class="layui-input-inline">
						<input type="text" class="layui-input" name='documentNumber' placeholder='请输入产品编号'></div>
					<div class="layui-input-inline">
						<input type="text" class='layui-input' id='searchTime' placeholder='输入查找时间'></div>
					<div class="layui-input-inline">
						<select lay-search name='flag'><option value="">是否反冲</option>
														<option value='1'>反冲</option>
														<option value='0'>不反冲</option></select></div>
					<div class="layui-input-inline">
						<button class="layui-btn" lay-submit lay-filter="search">搜索</button></div>
				</div>
			</div>
		<table class="laui-table" id="onlineOrder" lay-filter="onlineOrder" ></table>
	</div>
</div>

<!-- 位置选择隐藏框 -->
<div id="positionChoose">
</div>	
		
</body>


<!-- 查看订单隐藏框 -->
<script type="text/html" id="addEditTpl">
	<div>
		<table class="layui-form" style="width:100%" id="headerTool">
			<tr>
				<td>操作</td>
				<td colspan="3" style="text-align:left;">
					<button class="layui-btn layui-btn-sm" lay-submit lay-filter="sureAdd">确定</button>
					<input type="hidden" name="onlineCustomerId" id="customId" value="{{ d.onlineCustomer.id }}">
					<input type="hidden" name="id" value="{{ d.id }}">
				</td>
				<td>订单状态：</td>			
				<td>{{# var text='';
						switch(d.status){
						case "WAIT_SELLER_SEND_GOODS":	text='等待卖家发货,即:买家已付款';  break;
						case 'TRADE_NO_CREATE_PAY':	text='没有创建支付宝交易';  break;
						case 'WAIT_BUYER_PAY':	text='等待买家付款';  break;
						case 'SELLER_CONSIGNED_PART':		text='卖家部分发货';  break;
						case 'TRADE_BUYER_SIGNED':	text='买家已签收,货到付款专用';  break;
						case 'TRADE_FINISHED':	text='交易成功';  break;
						case 'WAIT_BUYER_CONFIRM_GOODS':	text='等待买家确认收货,即:卖家已发货';  break;
						}
					}}
						<input type="text" class="layui-input" value="{{ text }}" readonly></td>		
			</tr>
			<tr>
				<td>客户名称：</td>	
				<td><input type="text" class="layui-input" name="name" id="customNames" lay-verify="required" readonly value="{{ d.onlineCustomer.name }}"></td>
				<td>订单编号：</td>			
				<td><input type="text" class="layui-input" name="tid" value="{{ d.tid }}" readonly></td>
				<td>所属客服：</td>			
				<td><input type="text" class="layui-input" name="" value="{{ d.user.userName }}" readonly></td>
			</tr>
			<tr>
				<td>收货人：</td>			
				<td><input type="text" class="layui-input" id="customRealName" name="buyerName" value="{{ d.buyerName }}" readonly></td>
				<td>收款金额：</td>			
				<td><input type="text" class="layui-input" id="customPayment" name="payment" value="{{ d.payment }}" readonly></td>
				
				<td>发货仓库：</td>	
				<td><select name="warehouse" disabled>
									 <option {{ d.warehouse=='0'?'selected':'' }} value="0">主仓库</option>
									 <option {{ d.warehouse=='1'?'selected':'' }} value="1">客供仓库</option>
									 <option {{ d.warehouse=='2'?'selected':'' }} value="2">次品</option></select></td>		
			</tr>
			<tr>
				<td>手机：</td>			
				<td><input type="text" class="layui-input" id="customPhone" name="phone" value="{{ d.phone }}" readonly></td>
				<td>整单优惠：</td>			
				<td><input type="text" class="layui-input" name="allBillPreferential" value="{{ d.allBillPreferential }}" readonly></td>
				<td>邮费：</td>			
				<td><input type="text" class="layui-input" name="postFee" value="{{ d.postFee }}" readonly></td>
			</tr>
			<tr>
				<td>所在地：</td>			
				<td colspan="3">
						<div class="layui-form-item">
								<div class="layui-input-inline"><input type='text' class='layui-input' readonly value='{{ d.provinces.regionName}}'></div>
								<div class="layui-input-inline"><input type='text' class='layui-input' readonly value='{{ d.city.regionName}}'></div>
								<div class="layui-input-inline"><input type='text' class='layui-input' readonly value='{{ d.county.regionName}}'></div>
								</div></td>
				<td>物流方式：</td>
				<td>{{# var text='';
						switch(d.shippingType){
						case 'free':	text='卖家包邮';  break;
						case 'post':	text='平邮';  break;
						case 'express':	text='快递';  break;
						case 'ems':		text='EMS';  break;
						case 'virtual':	text='虚拟发货';  break;
						}
					}}
						<input type="text" class="layui-input" value="{{ text }}" readonly></td>			
			</tr>
			<tr>
				<td>详细地址：</td>			
				<td colspan="3"><input type="text" class="layui-input" id="customAddress" name="address" readonly
								placeholder="您可以直接黏贴淘宝或拼多多的收货地址,会自动提取省市区和收货人信息" value="{{ d.address }}"></td>
				<td>邮编：</td>			
				<td><input type="text" class="layui-input" id="customZipCode" name="zipCode" value="{{ d.zipCode }}" readonly></td>
			</tr>
			<tr>
				<td>卖家备注：</td>			
				<td colspan="3"><input type="text" class="layui-input" placeholder="" name="sellerMemo" value="{{ d.sellerMemo }}" readonly></td>
				<td rowspan="2">旗帜：</td>			
				<td rowspan="2"></td>
			</tr>
			<tr>
				<td>买家备注：</td>			
				<td colspan="3"><input type="text" class="layui-input" name="buyerMemo" value="{{ d.buyerMemo }}" readonly></td>
			</tr>
		</table>
		<table class="layui-table" id="productTable" lay-filter="productTable"></table>
	</div>
</script>	

<!-- 订单列表的工具栏  -->
<script type="text/html" id="onlineOrderToolbar">
<div class="layui-button-container">
	<span class="layui-btn layui-btn-sm" lay-event="add">导入订单</span>
	<span class="layui-btn layui-btn-sm" lay-event="edit">查看订单</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">反冲订单</span>
</div>
</script>
<!-- 解析状态模板 -->
<script type="text/html" id="statusTpl">
  {{#  if(d.status == 'WAIT_SELLER_SEND_GOODS' ){ }}
    	买家已付款
  {{#  } else if(d.status == 'TRADE_NO_CREATE_PAY'){ }}
    	没有创建交易
  {{#  } else if(d.status == 'WAIT_BUYER_PAY'){ }}
    	等待买家付款
  {{#  } else if(d.status == 'SELLER_CONSIGNED_PART'){ }}
    	卖家部分发货
  {{#  } else if(d.status == 'TRADE_BUYER_SIGNED'){ }}
    	买家已签收
  {{#  } else if(d.status == 'TRADE_FINISHED'){ }}
    	交易成功
  {{#  } else if(d.status == 'WAIT_BUYER_CONFIRM_GOODS'){ }}
    	卖家已发货
  {{#  } }}						
</script>		
<script type="text/html" id="provincesTpl">
  {{ d.provinces.regionName }}
</script>				

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
	['tablePlug','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug;
		
		
		var choosedProduct=[];			//用户已经选择上的产品
		var allInventory=[],			//所有的仓库
			allUser=[],
			allCustom=[];
		
		getAllUser();
		getAllCustom();
		
		laydate.render({ elem:'#searchTime', type: 'datetime', range:'~' }) 
		form.render();
		form.on('submit(search)',function(obj){			//订单列表搜索
			var provincesIds='';
			$('input[name="provinces"]:checked').each(function(){
				provincesIds+=($(this).val()+',');
	        });
			obj.field.provincesIds=provincesIds;
			var t=$('#searchTime').val().split('~');
			obj.field.orderTimeBegin = t[0];
			obj.field.orderTimeEnd = t[1];
			table.reload('onlineOrder',{
				where:obj.field
			})
		})
		
		$("#customPosition").hover(function() {			//鼠标移入事件
			$('#positionChoose').show();
			form.render();
		});
		$("#positionChoose").mouseleave (function () {	//mouseout进入复选框时会发生冒泡事件，引起out事件触发，因此需要使用mouseleave代替
			$('#positionChoose').hide();	
	    });

		table.render({		//渲染主页面表格
			elem:'#onlineOrder',
			url:'${ctx}/inventory/onlineOrderPage',
			toolbar:'#onlineOrderToolbar',
			loading:true,
			page:true,
			request:{
				pageName: 'page' ,		
				limitName: 'size' 		
			},
			parseData:function(ret){
				return{ code:ret.code, msg:ret.message, data:ret.data.rows, count:ret.data.total }
			},
			cols:[[
			       {type:'checkbox',align:'center',fixed:'left'},
			       {field:'createdAt',	title:'下单时间',   align:'center', width:'10%'},
			       {field:'tid',        title:'订单号',     align:'center', width:'8%',},
			       {field:'buyerMemo',  title:'买家留言',   align:'center'},
			       {field:'sellerMemo', title:'卖家备注',   align:'center'},
			       {field:'postFee',    title:'邮费',       align:'center', width:'4%'},
			       {field:'payment',    title:'实收金额',   align:'center', width:'6%'},
			       {field:'num',     	title:'件数',       align:'center', width:'4%'},
			       {field:'trackingNumber',title:'运单号',  align:'center', width:'8%',},
			       {field:'status',        title:'状态',    align:'center', width:'8%', templet:'#statusTpl'},
			       {field:'provinces',     title:'所在地区',align:'center', width:'8%', templet:'#provincesTpl'},
			       ]]
		}) 
		
		table.on('toolbar(onlineOrder)',function(obj){				//新增、删除按钮
			switch(obj.event){
			case 'add':    layer.msg('还未完善');  break;
			case 'edit':   addEditOrder('edit'); break;
			case 'delete': deletes(); break;
			}
		})
		
		function addEditOrder(type){
			choosedProduct=[];				//清空之前选中的数据
			var tpl=addEditTpl.innerHTML
			, html=''
			, winTitle='新增订单'
			, choosed=layui.table.checkStatus('onlineOrder').data
			, data={
				onlineCustomer:{ id:'',name:''},//客户
				id:'',							//订单id
				buyerName:'',					//收货人
				phone:'',						//手机
				address:'',						//详细地址
				buyerMemo:'',					//买家备注
				sellerMemo:'',					//卖家备注
				tid:'',							//订单编号
				payment:'',						//实收金额
				allBillPreferential:'',			//整单优惠
				status:'',						//订单状态
				userId:'',						//所属人员
				warehouse:'',					//发货仓库
				postFee:'',						//邮费
				shippingType:'',				//物流方式
				zipCode:'',						//邮编
				buyerFlag:'',					//买家旗帜
			};
			if(type=='edit'){
				if(choosed.length>1){
					layer.msg('无法同时查看多个订单',{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg('请选择订单查看',{icon:2});
					return;
				}
				winTitle='查看订单';
				data=choosed[0];		//渲染基本数据
				//渲染商品信息
				var all=choosed[0].onlineOrderChilds;
				for(var i=0;i<all.length;i++){
					var orderChild={
							skuCode:all[i].commodity.skuCode,	//商品编号
							name:all[i].commodity.name,			//商品名称
							commodityId:all[i].commodity.id,	//商品id
							number:all[i].number,				//商品数量
							price:all[i].price,					//商品单价
							sumPrice:all[i].sumPrice,			//单价总金额
							systemPreferential:all[i].systemPreferential,	 //系统优惠
							sellerReadjustPrices:all[i].sellerReadjustPrices,//卖家调价
							actualSum:all[i].actualSum,			//实际金额
							status:all[i].status,				//状态默认值
							}
					choosedProduct.push(orderChild); 
				}
			}
			laytpl(tpl).render(data,function(h){ html=h; })			//新增、修改订单模板渲染
			layer.open({
				title:winTitle,
				type:1,
				area:['90%','90%'],
				content:html
			})
			form.render();
			initAddEditOrderWin();			//弹窗的初始化，表格的渲染等。。
		}
		
		function deletes(){
			var choosed=layui.table.checkStatus('onlineOrder').data;
			if(choosed.length<1){
				layer.msg('请选择至少一条信息',{icon:2});
			}
			else{
				layer.confirm('是否确认删除？',function(){
					var ids='';
					for(var i=0;i<choosed.length;i++)
						ids+=(choosed[i].id+',');
					var load=layer.load(1);
					$.ajax({
						url:'${ctx}/inventory/deleteOnlineOrder',
						data:{ids:ids},
						success:function(result){
							if(0==result.code){
								layer.msg(result.message,{icon:1});
								table.reload('onlineOrder');
							}
							else
								layer.msg(result.message,{icon:2});
							layer.close(load);
						}
					})//end ajax
				})//end confirm
			}
		}
		
		
		
		
		function initAddEditOrderWin(){			//初始化，新增修改订单的弹窗功能
			$('#headerTool').find("td:even").css({backgroundColor:"rgba(65, 161, 210, 0.45)",padding:"1px"}); //设置表头背景颜色
			form.render();
			table.render({
				elem:"#productTable", 					//表单中选择商品的表格
				loading:true,
				data:[], //此处不能使用choosedProduct数据进行渲染，否则会造成数据的绑定。当监听修改表格中的数据时，会自动修改数组数据的内容。所以所有数据的渲染只能使用reload
				totalRow:true,
				page:{},
				cols:[[
				       {field:'skuCode',		title:'商品名称',	align:'center'},
				       {field:'inventory',	title:'发货仓库',	align:'center', width:'8%', templet:function(d){
																								for(var i=0;i<allInventory.length;i++){ 
																									if(allInventory[i].id==d.inventory)
																										return '<span>'+allInventory[i].name+'</span>';}	}},
				       {field:'number',		title:'数量',       align:'center', width:'4%',		templet:'#numberTpl', totalRow:true,},
				       {field:'price',   	title:'单价',   	    align:'center', width:'4%',		templet:'#priceTpl'},
				       {field:'sumPrice',   title:'单价总金额', align:'center', width:'8%', totalRow:true, style:"color:blue;"},
				       {field:'systemPreferential',   		title:'系统优惠',   align:'center', width:'6%',	 templet:'#systemPreferentialTpl'},
				       {field:'sellerReadjustPrices',   		title:'卖家调价',   align:'center', width:'6%',	 templet:"#sellerReadjustPricesTpl"},
				       {field:'actualSum',  title:'实际金额',   align:'center', width:'6%',	totalRow:true, style:"color:blue;"},
				       ]]
			})
			table.reload('productTable',{
				data:choosedProduct
			})
			
			//主窗口一个四个按钮。确定添加、新增、删除、客户名分别进行绑定
			form.on('submit(sureAdd)',function(obj){					//确定添加按钮
				layer.closeAll();
				return;
			})
			
		}
		getProvince();
		function getProvince(){			//获取省份
			$.ajax({
				url:"${ctx}/regionAddress/queryProvince",
				data:{parentId:0},
				success:function(result){
					if(0==result.code){
						var html='<table>';
						var data=result.data;	
						for(var i=0;i<data.length;i++){
							if(i%3==0)
								html+='<tr>';
							html+='<td><input type="checkbox" name="provinces" value="'+data[i].id+'" >'+data[i].regionName+'</td>';
							if(i+2%3==0)
								html+='</tr>';
						}
						html+='</html>';
						$('#positionChoose').html(html);
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
		function getAllCustom(){
			$.ajax({
				url:'${ctx}/inventory/onlineCustomerPage',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allCustom.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].buyerName
							})
						renderCustomSelect('customIdSelect');
					}
				}
			})
		}
		function renderCustomSelect(select){			//根据id渲染客户下拉框
			var html='';
			for(var i=0;i<allCustom.length;i++){
				html+='<option value="'+allCustom[i].id+'">'+allCustom[i].userName+'</option>';
			}
			$('#'+select).append(html);
			form.render();
		}
		function renderUserSelect(select){			//根据id渲染客服下拉框
			var html='';
			for(var i=0;i<allUser.length;i++){
				html+='<option value="'+allUser[i].id+'">'+allUser[i].userName+'</option>';
			}
			$('#'+select).append(html);
			form.render();
		}
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	}//define function
)
</script>
</html>