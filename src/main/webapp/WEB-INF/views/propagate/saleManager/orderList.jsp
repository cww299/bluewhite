<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>>
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
														<option  value="WAIT_SELLER_SEND_GOODS">买家已付款</option>
														<!-- <option  value="TRADE_NO_CREATE_PAY">没有创建支付宝交易</option>
														<option  value="WAIT_BUYER_PAY">等待买家付款</option>
														<option  value="SELLER_CONSIGNED_PART">卖家部分发货</option>
														<option  value="TRADE_BUYER_SIGNED">买家已签收,货到付款专用</option>
														<option  value="TRADE_FINISHED">交易成功</option> -->
														<option  value="SELLER_CONSIGNED_PART">部分发货</option>
														<option  value="WAIT_BUYER_CONFIRM_GOODS">卖家已发货</option></select></div>
					<div class="layui-input-inline">
						<input type="text" class='layui-input' name='documentNumber' placeholder='输入查找订单号'></div>
					<div class="layui-input-inline" style='width:70px;'><a href="#" style="color:blue;" id="customPosition">所在省份</a></div>  <!-- provincesId收货人的所在省份 -->
					<div class="layui-input-inline">
						<input type="text" class="layui-input" name='commodityName' placeholder='请输入产品编号'></div>
					<div class="layui-input-inline">
						<input type="text" class='layui-input' id='searchTime' placeholder='输入查找时间'></div>
					<div class="layui-input-inline">
						<select lay-search name='flag'><option value="">是否反冲</option>
														<option value='1'>反冲</option>
														<option value='0' selected>不反冲</option></select></div>
					<div class="layui-input-inline">
						<button class="layui-btn" lay-submit lay-filter="search">搜索</button>
						<button class="layui-btn" id="uploadDataBtn">导入</button></div>
				</div>
			</div>
		<table class="laui-table" id="onlineOrder" lay-filter="onlineOrder" ></table>
	</div>
</div>

<!-- 位置选择隐藏框 -->
<div id="positionChoose"></div>	
		
</body>

<!-- 上传文件隐藏框 -->
<div id='uploadDiv' style="display:none;padding:20px;" class="layui-form">
	<select id='uploadUser' lay-search><option value="">经手人</option></select>
	<select id='uploadCustom' lay-search><option value="">选择客户</option></select>
	<select id='uploadWarehouse'><option value="">仓库选择</option></select>
	<button type='button' id='uploadData' style="display:none;"></button>
</div>

<!-- 查看订单隐藏框 -->
<script type="text/html" id="addEditTpl">
	<div id="orderContentDiv">
		<table class="layui-form" style="width:100%" id="headerTool">
			<tr>
				<td>客户名称：<input type="hidden" name="onlineCustomerId" id="customId" value="{{ d.onlineCustomer.id }}">
							  <input type="hidden" name="id" value="{{ d.id }}"></td>	
				<td><input type="text" class="layui-input" name="name" id="customNames" lay-verify="required" readonly value="{{ d.onlineCustomer.name }}"></td>
				<td>订单编号：</td>			
				<td><input type="text" class="layui-input" name="tid" value="{{ d.tid==null?'无订单编号':d.tid }}" readonly></td>
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
				<td>收货人：</td>			
				<td><input type="text" class="layui-input" id="customRealName" name="buyerName" value="{{ d.buyerName }}" readonly></td>
				<td>收款金额：</td>			
				<td><input type="text" class="layui-input" id="customPayment" name="payment" value="{{ d.payment }}" readonly></td>
				<td>所属客服：</td>			
				<td><input type="text" class="layui-input" name="" value="{{ d.user.userName }}" readonly></td>
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
				<td>卖家备注：</td>			
				<td><input type="text" class="layui-input" placeholder="" name="sellerMemo" value="{{ d.sellerMemo }}" readonly></td>
				<td>买家备注：</td>			
				<td><input type="text" class="layui-input" name="buyerMemo" value="{{ d.buyerMemo }}" readonly></td>
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
				<td>所在地：</td>			
				<td colspan="3">
						<div class="layui-form-item">
								<div class="layui-input-inline"><input type='text' class='layui-input' readonly value='{{ d.provinces.regionName}}'></div>
								<div class="layui-input-inline"><input type='text' class='layui-input' readonly value='{{ d.city.regionName}}'></div>
								<div class="layui-input-inline"><input type='text' class='layui-input' readonly value='{{ d.county.regionName}}'></div>
								</div></td>
				<td>邮编：</td>			
				<td><input type="text" class="layui-input" id="customZipCode" name="zipCode" value="{{ d.zipCode }}" readonly></td>
			</tr>
			<tr>
				<td>详细地址：</td>			
				<td colspan="3"><input type="text" class="layui-input" id="customAddress" name="address" readonly value="{{ d.address }}"></td>
				<td>操作</td>
				<td style="float:left;padding-left:10px;"><button class="layui-btn layui-btn-sm" type="button" id="printBtn">导出订单</button></td>
			</tr>
			
		</table>
		<table class="layui-table" id="productTable" lay-filter="productTable"></table>
	</div>
</script>	
<!-- 发货单模板 -->
<script type="text/html" id="deliveryOrderTpl">
<div style="padding:20px;" id="deliveryDiv{{ d.id }}">          
    <table style="margin: auto;width: 80%;">
        <tr>
            <td style="text-align:center;">买家：</td><td>{{ d.name }} </td>
            <td>&nbsp;&nbsp;</td>
            <td style="text-align:center;">收货人：</td><td>{{ d.buyerName }}</td>
            <td>&nbsp;&nbsp;</td>
            <td style="text-align:center;">手机：</td><td>{{ d.phone }}</td>
        </tr>
        <tr>
            <td style="text-align:center;">收货地址：</td><td colspan="7" style="text-align: left;">{{ d.address }}</td>
        </tr>
        <tr>
            <td style="text-align:center;">发货日期：</td><td>{{ d.createdAt }}</td>
            <td>&nbsp;&nbsp;</td>
            <td style="text-align:center;">物流单号：</td><td>{{ d.trackingNumber}}</td>
            <td>&nbsp;&nbsp;</td>
            <td style="text-align:center;">发货合计数量：</td><td>{{ d.sumNumber }}</td>
        </tr>
    </table>
    <table border="1" style="margin: auto;width: 80%;text-align:center;">
        <tr>
            <td>行号</td>
            <td style="width:500px;">商品编号</td>
            <td>发货数量</td>
        </tr>
		{{# layui.each(d.deliveryChilds,function(index,item){  }}
		<tr>
            <td>{{ index+1 }}</td>
            <td>{{ item.commodity.skuCode}}</td>
            <td>{{ item.number}}</td>
        </tr>
        {{# }) }}
    </table>
</div>
	<p style="text-align:center;"><button type="button" class="layui-btn layui-btn-sm" data-deliveryDiv="deliveryDiv{{ d.id }}">打印</button></p>
<hr/>
</script>
<!-- 订单列表的工具栏  -->
<script type="text/html" id="onlineOrderToolbar">
<div class="layui-button-container">
	<span class="layui-btn layui-btn-sm" lay-event="oneKey">发货</span>
	<span class="layui-btn layui-btn-sm" lay-event="partDelivery">部分发货</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">反冲订单</span>
	<span class="layui-btn layui-btn-sm " lay-event="printOrder">导出订单</span>
	<span class="layui-btn layui-btn-sm " lay-event="lookoverDelivery">查看发货单</span>
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
  {{#  } else if(d.status == 'SELLER_CONSIGNED_PART'){ }}
    	部分发货
  {{#  } }}						
</script>		
<script type="text/html" id="provincesTpl">
  {{#  var str="";
		str+=(d.name+',');
		str+=(d.phone+',');
		str+=(d.address);
  }}
	<span>{{ str }}</span>
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
	['tablePlug','laydate','upload'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, upload = layui.upload
		, tablePlug = layui.tablePlug;
		
		
		var choosedProduct=[];			//用户已经选择上的产品
		var allInventory=[],			//所有的仓库
			allUser=[],
			allCustom=[];
		
		getAllUser();
		getAllCustom();
		getAllInventory();
		//------------搜索功能---------------------
		laydate.render({ elem:'#searchTime', type: 'datetime', range:'~' }) 
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
				where:obj.field,
				url:'${ctx}/inventory/onlineOrderPage',
				page : { curr : 1 },
			})
		})
		form.render();
		//--------------上传文件功能-----------------------		
		var load;
		upload.render({
		   	  elem: '#uploadData'
		   	  ,url: '${ctx}/inventory/import/excelOnlineOrder'
		 	  ,before: function(obj){ 	
		 		  this.data={  
		 				  userId:$('#uploadUser').val(),  
		 				  onlineCustomerId:$('#uploadCustom').val(),  
		 				  warehouseId:$('#uploadWarehouse').val()  };
		 		 load = layer.load(1); 
			  }
		   	  ,done: function(res, index, upload){ 
		   		  if(res.code==0){
				   		layer.closeAll();
				   		layer.msg(res.message,{icon:1});
				   		table.reload('onlineOrder');
		   		  }else{
			   			layer.close(load);
			   			layer.msg(res.message,{icon:2});
		   		  }
		   	  } 
		   	  ,accept: 'file' 
		   	  ,exts: 'xlsx|xls'
		})
		$('#uploadDataBtn').on('click',function(){
			layer.open({
				title:'导入订单数据',
				area:['30%','40%'],
				type:1,
				content:$('#uploadDiv'),
				btn:['选择文件','取消'],
				yes:function(){
					if($('#uploadUser').val()!='' && $('#uploadCustom').val()!=''){
						$('#uploadData').click();
					}else
						layer.msg('请选择经手人与客户',{icon:2});
				}
			})
		})
		
		//-----------------主页面功能--------------------
		$("#customPosition").hover(function() {			//鼠标移入事件
			$('#positionChoose').show();
			form.render();
		});
		$("#positionChoose").mouseleave (function () {	//mouseout进入复选框时会发生冒泡事件，引起out事件触发，因此需要使用mouseleave代替
			$('#positionChoose').hide();	
	    });

		table.render({		//渲染主页面表格
			elem:'#onlineOrder',
			url:'${ctx}/inventory/onlineOrderPage?flag=0&status=SELLER_CONSIGNED_PART,WAIT_SELLER_SEND_GOODS',		//默认查找未反冲+未发货+部分发货
			toolbar:'#onlineOrderToolbar',
			loading:true,
			size:'sm',
			page:true,
			limits:[20,30,50,100],
			limit:20,
			request:{
				pageName: 'page' ,		
				limitName: 'size' 		
			},
			parseData:function(ret){
				return{ code:ret.code, msg:ret.message, data:ret.data.rows, count:ret.data.total }
			},
			cols:[[
			       {type:'checkbox',align:'center',fixed:'left'},
			       {field:'createdAt',	title:'下单时间',   align:'center', width:'9%'},
			       {field:'documentNumber',        title:'订单号',     align:'center', width:'10%',},
			       {field:'name',           title:'客户名称',     align:'center', width:'8%', },
			       {field:'userName',           title:'业务员',     align:'center', width:'6%', templet:function(d){return d.user.userName}},
			       {field:'buyerMemo',  title:'买家留言',   align:'center',width:'8%', },
			       {field:'sellerMemo', title:'卖家备注',   align:'center',width:'8%', },
			       {field:'postFee',    title:'邮费',       align:'center', width:'4%'},
			       {field:'payment',    title:'实收金额',   align:'center', width:'5%'},
			       {field:'num',     	title:'件数',       align:'center', width:'4%'},
			       {field:'residueNumber', title:'剩余件数',       align:'center', width:'5%'},
			       {field:'',title:'运单号',  align:'center', width:'8%', templet: getTrackingNumber() },
			       {field:'status',        title:'状态',    align:'center', width:'8%', templet:'#statusTpl'},
			       {field:'isFlag',		   title:'是否反冲',    align:'center', width:'5%', templet:isFlag(),},
			       {field:'provinces',     title:'所在地区',align:'center', templet:'#provincesTpl'},
			       ]],
		}) 
		function getTrackingNumber(){
			return function(d){
				var tackingNumber = d.deliverys;
				var html = '';
				layui.each(tackingNumber,function(index,item){
					if(item.trackingNumber)
						html+=(item.trackingNumber+',');
				})
				return html;
			}
		}
		function isFlag(){
			return function(d){
				if(d.flag == 0)
					return '<span class="layui-badge layui-bg-green">未反冲</span>';
				else
					return '<span class="layui-badge">反冲</span>';
			}
		}
		table.on('toolbar(onlineOrder)',function(obj){				//新增、删除按钮
			switch(obj.event){
			case 'oneKey':   oneKey(); break;
			case 'delete': deletes(); break;
			case 'partDelivery' : partDelivery(); break;
			case 'printOrder' : printOrder(); break;
			case 'lookoverDelivery' : lookoverDelivery(); break;
			}
		})
		table.on('rowDouble(onlineOrder)',function(obj){
			addEditOrder(obj.data);
		})
		function lookoverDelivery(){
			var checked = layui.table.checkStatus('onlineOrder').data;
			if(checked.length>1){
				layer.msg('无法同时查看多个订单的发货单',{icon:2});return;
			}
			if(checked.length<1){
				layer.msg('请选择订单',{icon:2});return;
			}
			var html = '';
			layui.each(checked[0].deliverys,function(index,item){
				item.name = checked[0].name;
				item.buyerName = checked[0].buyerName;
				item.address = checked[0].address;
				item.phone = checked[0].phone;
				var tpl = deliveryOrderTpl.innerHTML;
				laytpl(tpl).render(item,function(h){ html += h; })			
			})
			layer.open({
				type : 1,
				title : '查看发货单',
				area : ['80%','80%'],
				shadeClose : true,
				content : html,
			});
			$('button[data-deliveryDiv]').on('click',function(obj){
				printpage($(this).attr('data-deliveryDiv'));
			})
		}
		//打印的元素ID
		function printpage(myDiv){    
			var printHtml = document.getElementById(myDiv).innerHTML;
			var wind = window.open("",'newwindow', 'height=800, width=1500, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
			wind.document.body.innerHTML = printHtml;
			wind.print();
			return false; 
		}  
		function  printOrder(){
			var choosed = layui.table.checkStatus('onlineOrder').data;
			if(choosed.length<1){ layer.msg('请选择订单',{icon:2}); return; }
			var ids=[];
			for(var i=0;i<choosed.length;i++){
				ids.push(choosed[i].id);
			}
			location.href='${ctx}/inventory/export/excelOnlineOrderDetail?ids='+ids.join(',');
		}
		function partDelivery(){	
			var choosed = layui.table.checkStatus('onlineOrder').data;
			if(choosed.length<1){ layer.msg('请选择订单',{icon:2}); return; }
			if(choosed.length>1){ layer.msg('部分发货无法同时发货多天信息',{icon:2}); return; }
			var obj = choosed[0];
			layer.open({
				type : 1,
				area : ['80%','70%'],
				title:'部分发货：'+obj.documentNumber,
				content : '<div style="padding:10px;">'+
							'<span style="float:left;">运单号：</span>'+
							'<input type="text" class="layui-input" id="deliveryNumber" style="float:left;width:200px;">'+
							'<table id="partDeliveryTable" lay-filter="partDeliveryTable" class="layui-table"></table></div>',
				btn : ['确认发货','取消'],
				yes : function(){
					var c =[];  	//发货的对象
					if(layui.table.checkStatus('partDeliveryTable').data.length==0){
						layer.msg('请勾选发货的信息！',{icon:2}); return false;
					}
					var dataSuccess = true;
					layui.each(partDeliveryTable.config.data,function(index,item){
						if(item.LAY_CHECKED){		//如果选中
							if(isNaN(item.deliveryNumber)){ 
								layer.msg('商品：'+item.commodity.skuCode+' 发货数量请输入正确的数字',{icon:2}); 
								dataSuccess=false; 
								return;
							}
							if(item.deliveryNumber>item.residueNumber){ 
								layer.msg('商品：'+item.commodity.skuCode+' 发货数量不能大于剩余数量',{icon:2}); 
								dataSuccess=false; 
								return;
							}
							c.push({
								trackingNumber : $('#deliveryNumber').val().trim(),
								warehouseId : item.warehouse.id,
								id: item.id,
								number: item.deliveryNumber
							})
						}
					}) 
					if(!dataSuccess) return;			//如果数据格式非法	
					var load;
					$.ajax({
						url:'${ctx}/inventory/delivery',
						type:'post',
						data:{delivery:JSON.stringify(c)},
						beforeSend:function(){ load = layer.load(1); },
						success:function(r){
							if(0==r.code){
								layer.msg(r.message,{icon:1});
								table.reload('onlineOrder');
							}else
								layer.msg(r.message,{icon:2});
							layer.close(load);
						}
					})
				}
			})
			layui.each(obj.onlineOrderChilds,function(index,item){			//设置默认发货数量为剩余全部数量
				item.deliveryNumber = item.residueNumber;
			})
			var partDeliveryTable = table.render({
				elem : '#partDeliveryTable',
				data : obj.onlineOrderChilds,
				page : {},
				cols : [[
				           { align:'center', type:'checkbox',},
				           {field:'skuCode',		title:'商品名称',			  align:'center', templet:function(d){ return '<span>'+d.commodity.skuCode+'</span>';} },
					       {field:'inventory',	title:'发货仓库',	  align:'center', width:'8%', templet:function(d){ return '<span>'+d.warehouse.name+'</span>';} },
					       {field:'number',		title:'总数量',        align:'center', width:'10%',		},
					       {field:'residueNumber',   title:'剩余发货数量',    align:'center', width:'10%',		},
					       {field:'deliveryNumber',  title:'本次发货数量',  align:'center', width:'10%',  edit:true,   },
				         ]],
			})
		}
		function oneKey(){
			var choosed = layui.table.checkStatus('onlineOrder').data;
			if(choosed.length<1){
				layer.msg('请选择订单',{icon:2});
				return;
			}
			if(choosed.length>1){
				layer.msg('无法选择多个订单',{icon:2});
				return;
			}
			layer.open({
				title : '发货',
				content : '运单号：<input style="width:82%;float:right;" type="text" class="layui-input" id="numberOfDelivery">',
				area : ['25%','20%'],
				btn : ['确定','取消'],
				yes : function(){
					var c=[];
					for(var j=0;j<choosed.length;j++){
						var child=choosed[j].onlineOrderChilds;
						for(var i=0;i<child.length;i++){
							c.push({
								trackingNumber : $('#numberOfDelivery').val().trim(),
								warehouseId : child[i].warehouse.id,
								id:child[i].id,
								number:child[i].residueNumber
							})
						}
					}
					var load;
					$.ajax({
						url:'${ctx}/inventory/delivery',
						type:'post',
						data:{delivery:JSON.stringify(c)},
						beforeSend:function(){ load = layer.load(1); },
						success:function(r){
							if(0==r.code){
								layer.msg(r.message,{icon:1});
								table.reload('onlineOrder');
							}else
								layer.msg(r.message,{icon:2});
							layer.close(load);
						}
					})
				}
			})
			/* layer.confirm('是否确认一键发货？',function(){
				var c=[];
				for(var j=0;j<choosed.length;j++){
					var child=choosed[j].onlineOrderChilds;
					for(var i=0;i<child.length;i++){
						c.push({
							
							warehouseId : child[i].warehouse.id,
							id:child[i].id,
							number:child[i].residueNumber
						})
					}
				}
				var load;
				$.ajax({
					url:'${ctx}/inventory/delivery',
					type:'post',
					data:{delivery:JSON.stringify(c)},
					beforeSend:function(){ load = layer.load(1); },
					success:function(r){
						if(0==r.code){
							layer.msg(r.message,{icon:1});
							table.reload('onlineOrder');
						}else
							layer.msg(r.message,{icon:2});
						layer.close(load);
					}
				})
			}) */
		}
		function addEditOrder(data){
			var tpl=addEditTpl.innerHTML
				, html='';
			laytpl(tpl).render(data,function(h){ html=h; })			//新增、修改订单模板渲染
			layer.open({
				title: '查看订单',
				type:1,
				offset : '30px',
				area:['90%','90%'],
				shadeClose:true,
				content:html
			})
			initAddEditOrderWin(data.onlineOrderChilds);			//弹窗的初始化，表格的渲染等。。
			$('#printBtn').on('click',function(obj){				//导出订单
				location.href='${ctx}/inventory/export/excelOnlineOrderDetail?ids='+data.id;
			})
		}
		
		function deletes(){
			var choosed=layui.table.checkStatus('onlineOrder').data;
			if(choosed.length<1){
				layer.msg('请选择至少一条信息',{icon:2});
			}
			else{
				layer.confirm('是否确认反冲？',function(){
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
		
		
		function initAddEditOrderWin(child){			//初始化，新增修改订单的弹窗功能
			$('#headerTool').find("td:even").css({backgroundColor:"rgba(65, 161, 210, 0.45)",padding:"1px"}); //设置表头背景颜色
			form.render();
			table.render({
				elem:"#productTable", 					//表单中选择商品的表格
				loading:true,
				data: child,
				page:{},
				totalRow:true,
				cols:[[
				       {field:'a',		title:'商品名称',	align:'center',templet:function(d){ return '<span>'+d.commodity.skuCode+'</span>';} },
				       {field:'inventory',	title:'发货仓库',	align:'center', width:'8%', templet:function(d){ return '<span>'+d.warehouse.name+'</span>';} },
				       {field:'number',		title:'数量',       align:'center', width:'5%',		templet:'#numberTpl', totalRow:true,},
				       {field:'residueNumber',title:'剩余发货数量',   align:'center', width:'8%', totalRow:true,},
				       {field:'price',   	title:'单价',   	    align:'center', width:'4%',		templet:'#priceTpl'},
				       {field:'sumPrice',   title:'单价总金额', align:'center', width:'8%', totalRow:true, style:"color:blue;"},
				       {field:'systemPreferential',   		title:'系统优惠',   align:'center', width:'6%',	 templet:'#systemPreferentialTpl'},
				       {field:'sellerReadjustPrices',   		title:'卖家调价',   align:'center', width:'6%',	 templet:"#sellerReadjustPricesTpl"},
				       {field:'actualSum',  title:'实际金额',   align:'center', width:'6%',	totalRow:true, style:"color:blue;"},
				       ]]
			})
			form.render();
		}
		getProvince();	//获取省份,用于地址筛选
		function getProvince(){			
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
				url:'${ctx}/system/user/pages?size=99&quit=0',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allUser.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].userName
							})
						renderUserSelect('userIdSelect');
						renderUserSelect('uploadUser');
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
						renderCustomSelect('customIdSelect');
						renderCustomSelect('uploadCustom');
					}
				}
			})
		}
		function getAllInventory(){					//获取所有仓库
			$.ajax({
				url:'${ctx}/basedata/list?type=inventory',
				success:function(r){
					if(0==r.code){
						var html='';
						for(var i=0;i<r.data.length;i++){
							var disabled='';
							if(r.data[i].flag==0)
								disabled='disabled';
							html+='<option value="'+r.data[i].id+'" '+disabled+'>'+r.data[i].name+'</option>';
						}
						$('#uploadWarehouse').html(html);
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
				var selected='';
				if(select == 'uploadUser' && currentUser.id == allUser[i].id)
					selected='selected';
				html+='<option value="'+allUser[i].id+'" '+selected+'>'+allUser[i].userName+'</option>';
			}
			$('#'+select).append(html);
			form.render();
		}
		var currentUser;
		getCurrentUser();
		function getCurrentUser(){			//根据id渲染客服下拉框
			$.ajax({
				url:'${ctx}/getCurrentUser',		//获取当前登录用户
				success:function(r){
					if(0==r.code){
						currentUser = r.data;
					}
				}
			})
		}
		var currentUser;
		getCurrentUser();
		function getCurrentUser(){			//根据id渲染客服下拉框
			$.ajax({
				url:'${ctx}/getCurrentUser',		//获取当前登录用户
				success:function(r){
					if(0==r.code){
						currentUser = r.data;
					}
				}
			})
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
