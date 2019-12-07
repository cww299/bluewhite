/* 2019/12/6
 * author: 299
 * 新增、修改发货单模板
 * 需要在本模块前引入myutil,并设置ctx后调用init()
 * 
 * sendGoodOrder.update({ 
 * 		data:{修改前的数据、回显},   
 * })
 * sendGoodOrder.add({
 * 
 * 
 * })
 */
layui.extend({
	inventory: 'layui/myModules/warehouseManager/inventory',
}).define(['jquery','layer','form','laytpl','laydate','mytable','inventory'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		mytable = layui.mytable,
		table = layui.table,
		inventory = layui.inventory,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		myutil = layui.myutil;
	var TPL = `
	  <div class="addTable">
	    <div class="layui-card layerContentDiv">
	      <div class="layui-card-body layerContent">
	        <div style="width: 48.5%;float: left;padding: 0 0.5%;">
	          <p class="smallTitle"><span class="blueBlock">&nbsp;</span>发货单基础信息
	          	<span class="headTips"><b class="red">*</b>为必填项</span>
	          </p>
	          <table class="layui-form">
	            <tr>
	              <td class="titleTd">发货类型：</td>
	              <td style="min-width:150px;">
	              	  <input type="radio" value="1" title="成品" name="productType" {{ d.productType==1?"checked":"" }}>
					  <input type="radio" value="2" title="皮壳" name="productType" {{ d.productType==2?"checked":"" }}>
	              </td>
	              <td class="titleTd"><b class="red">*</b>客户名称：</td>
	              <td colspan="">
	              	<input type="text" class="layui-input" id="customInputChoose" value="{{ d.customer?d.customer.name:"" }}"
	              		placeholder="点击进行客户选择" readonly lay-verify="required"></td>
	            </tr>
	            <tr>
	              <td class="titleTd"><b class="red">*</b>商品名称：</td>
	              <td colspan="3">
	              	<input type="text" class="layui-input" id="productInputChoose" value="{{ d.product?d.product.name:"" }}"
	              		placeholder="点击进行商品选择" readonly lay-verify="required"></td>
	            </tr>
	            <tr>
	              <td class="titleTd"><b class="red">*</b>发货日期：</td>
	              <td colspan=""><input type="text" class="layui-input" value="" name="sendDate" value="{{ d.sendDate || ""}}"
	              		id="sendGoodDate" lay-verify="required"></td>
	              <td class="titleTd"><b class="red">*</b>发货数量：</td>
	              <td colspan=""><input type="text" class="layui-input" lay-verify="number" name="number" value="{{ d.number || 0 }}">
	            </tr>
	            <tr>
	              <td class="titleTd">备注：</td>
	              <td colspan="3"><input type="text" class="layui-input" name="remark" value="{{ d.remark || "" }}">
	              	<input type="hidden" id="customerIdHidden" name="customerId" value="{{ d.customer?d.customer.id:"" }}">
	              	<input type="hidden" id="productIdHidden" name="productId" value="{{ d.product?d.product.id:"" }}">
	              	<input type="hidden" name="id" value="{{ d.id || "" }}"></td>
					<span style="display:none;" lay-submit lay-filter="sureAddSendOrderSubmit" id="sureAddSendOrderSubmit"></span>
	            </tr>
	            <tr>
	              <td class="imgTd" colspan="4">
	                <div>0<p>总库存数量</p></div>
	                <div>0<p>业务员所属数量</p></div>
	                <div>需要申请<p>发货状态</p></div>
	              </td>
	            </tr>
	          </table>
	        </div>
	        <div style="width: 48.5%;float: right;padding: 0 0.5%;">
	          <p class="smallTitle"><span class="blueBlock">&nbsp;</span>申请借货单&nbsp;&nbsp;
	          	申请时间：<span class="layui-btn layui-btn-primary layui-btn-xs" id="askforDate">2019-12-06 00:00:00</span>
	            <span class="layui-btn layui-btn-xs layui-btn-danger fright" id="deleteAskfor">删除</span></p>
	          <table id="askForTable" lay-filter="askForTable"></table>
	        </div>
	      </div>
	    </div>
	    <div class="layui-card twoDiv">
	      <p class="smallTitle"><span class="blueBlock">&nbsp;</span>业务员所属库存信息</p>
	      <table id="myWarehouseTable" lay-filter="myWarehouseTable"></table>
	    </div>
	    <div class="layui-card twoDiv" style="float: right;">
	      <p class="smallTitle"><span class="blueBlock">&nbsp;</span>其他业务员库存所属信息
	            <span class="layui-btn layui-btn-xs layui-btn-normal fright" id="addAskfor">添加申请</span></p>
	      <table id="otherWarehouseTable" lay-filter="otherWarehouseTable"></table>
	    </div>
	  </div>
	`;
	
	var chooseCustomerWin = `
		<div style="padding:10px;">
			<table class="layui-form">
				<tr>
					<td>客户名称：</td>
					<td><input class="layui-input" type="text" name="name"></td>
					<td>&nbsp;&nbsp;</td>
					<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" id="searchBtn">搜索</span></td>
					<td>&nbsp;&nbsp;</td>
					<td><span class="layui-btn layui-btn-sm" id="sureChoosed">确定</span></td>
					<td>&nbsp;&nbsp;</td>
					<td><span class="layui-badge">双击选择</span></td>
				</tr>
			</table>
			<table id="choosedCustomerTable" lay-filter="choosedCustomerTable"></table>
		</div>
		`;
	var choosedProductWin = `
	<div style="padding:10px;">
		<div id="inventoryRenderDiv"></div>
	</div>
	`;
		
	var sendGoodOrder = { };
	
	sendGoodOrder.add = function(opt){
		opt.data = {};
		sendGoodOrder.update(opt)
	}
	
	sendGoodOrder.update = function(opt){
		var data = opt.data,title="生成发货单";
		if(!data){
			console.error('请给定数据！');
			return;
		}
		if(data.id){
			title = "修改发货单";
		}
		var html = '';
		laytpl(TPL).render(data,function(h){
			html = h;
		})
		var win = layer.open({
			type:1,
			move: false,
			content:html,
			area:['60%','100%'],
			title: [	'<i class="layui-icon layui-icon-spread-left"></i>&nbsp;',
						title,
						'<span class="layui-btn layui-btn-sm" id="sureAddSendOrder">确定新增</span>'
					].join(' '),
			success:function(){
				laydate.render({ elem:'#sendGoodDate', type:'datetime', });
				laydate.render({ elem:'#askforDate', type:'datetime', value: new Date(), });
				$('#sureAddSendOrder').click(function(){
					$("#sureAddSendOrderSubmit").click();
				})
				mytable.render({
					elem:'#askForTable',
					data:[],
					height:'320px',
					totalRow:[],
					cols:[[
						{ type:'checkbox', },
						{ field:'bacthNumber',title:'批次号',},
						{ field:'userName',title:'所属业务员',},
						{ field:'number',title:'剩余数量',},
						{ field:'askNumber',title:'申请数量',edit:true,},
					]],
					done:function(){
						$('#deleteAskfor').click(function(){
							var check = layui.table.checkStatus('askForTable').data;
							if(check.length==0)
								return myutil.emsg('请选择删除的申请信息');
							
						})
						table.on('edit(askForTable)', function(obj){
							var trData = obj.data;
							var val = obj.value;
							if(isNaN(val) || val<0 || val%1.0!=0.0){
								var last = myutil.getLastData();
								trData['askNumber'] = last;
								$(this).val(last);
							}
						});
					}
				})
				mytable.renderNoPage({
					elem:'#myWarehouseTable',
					data:[],
					height:'400px',
					totalRow:[],
					cols:[[
						{ type:'checkbox', },
						{ field:'bacthNumber',title:'批次号',},
						{ field:'',title:'所属业务员',},
						{ field:'',title:'剩余数量',},
					]]
				})
				mytable.renderNoPage({
					elem:'#otherWarehouseTable',
					data:[],
					height:'400px',
					totalRow:[],
					cols:[[
						{ type:'checkbox', },
						{ field:'bacthNumber',title:'批次号',},
						{ field:'',title:'所属业务员',},
						{ field:'',title:'剩余数量',},
					]],
					done:function(){
						$('#addAskfor').click(function(){
							var check = layui.table.checkStatus('otherWarehouseTable').data;
							if(check.length==0)
								return myutil.emsg('请选择需要申请的信息');
							var askforTable = layui.table.cache['askForTable'];
							
						})
					}
				})
				
				if(data.id){	//如果存在id，进行数据回显
					
				}
				form.on('submit(sureAddSendOrderSubmit)',function(obj){
					var data = obj.field;
					var url = '/ledger/addSendGoods';
					if(data.id)
						url = '';
					var tableData = layui.table.cache['askForTable'],json = [];
					layui.each(tableData,function(index,item){
						json.push({
							time: $('#askforDate').val(),
							number: item.askNumber,
							approvalUserId: item.userId,
						})
					})
					obj.field.applyVoucher = JSON.stringify(json);
					myutil.saveAjax({
						url: url,
						data: obj.field,
						success:function(){
							layer.close(win);
							opt.success && opt.success();
						}
					})
				})
				$('#customInputChoose').click(function(){
					openChooseCustomerWin();
				})
				$('#productInputChoose').click(function(){
					openChooseProductWin();
				})
				form.render();
			},
		})
	}
	
	//解析接口返回数据
	function getParseData(){
		return function(r){
			if(r.code==0){
				
			}
			return {  msg: r.message,  code: r.code , data: r.data, };
		}
	}
	
	function getWarehouseInfo(){
		var pid = $('#productIdHidden').val();
		if(pid){
			table.reload('otherWarehouseTable',{
				url: myutil.config.ctx+'/ledger/getOrder',
				where:{
					productId: pid,
					include: 1,
				},
				done:function(r){
					
				}
			})
			table.reload('myWarehouseTable',{
				url: myutil.config.ctx+'/ledger/getOrder',
				where:{
					productId: pid,
				},
				done:function(r){
					
				}
			})
		}
	}
	
	function openChooseCustomerWin(){
		var chooseProductWin = layer.open({
			title: '选择客户',
			type:1,
			area:['50%','90%'],
			content: chooseCustomerWin,
			shadeClose:true,
			success:function(){
				mytable.render({
					elem: '#choosedCustomerTable',
					url: myutil.config.ctx+'/ledger/customerPage?', //type=1
					cols:[[
						{ title:'客户编号',	field:'id',	},
						{ title:'客户名称',	field:'name',	},
						]],
				})
				table.on('rowDouble(choosedCustomerTable)',function(obj){
					var data = obj.data;
					$('#customerIdHidden').val(data.id);
						$('#customInputChoose').val(data.name);
					layer.close(chooseProductWin);
				});
			},
		})
	}
	
	function openChooseProductWin(){
		var chooseProductWinNew = layer.open({
			type:1,
			title:'商品选择',
			area:['90%','90%'],
			content: choosedProductWin,
			shadeClose:true,
			success:function(){
				inventory.render({
					elem: '#inventoryRenderDiv',
					chooseProductWin: true,
					ctx: myutil.config.ctx,
					type: $('input:radio[name="productType"]:checked').val()-(-1),
					done:function(){
						table.on('rowDouble(tableData)', function(obj){
							var data = obj.data;
							$('#productIdHidden').val(data.id);
							$('#productInputChoose').val(data.name);
							layer.close(chooseProductWinNew);
							getWarehouseInfo();
						});
					}
				})
			}
		})
		
	}
	
	sendGoodOrder.init = function(done){
		var filePath = layui.cache.modules.sendGoodOrder.substr(0, layui.cache.modules.sendGoodOrder.lastIndexOf('/'));
		layui.link(filePath+"/../css/sale/sendGoodOrder.css");
	};
	exports("sendGoodOrder",sendGoodOrder);
});