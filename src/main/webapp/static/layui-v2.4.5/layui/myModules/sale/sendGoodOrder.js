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
	        <div style="">
	          <p class="smallTitle"><span class="blueBlock">&nbsp;</span>发货单基础信息
	          	<span class="headTips"><b class="red">*</b>为必填项</span>
	          </p>
	          <table class="layui-form">
	            <tr>
	              <td class="titleTd">发货类型：</td>
	              <td style="min-width:150px;">
	              	  <input type="radio" value="1" title="成品" name="productType" {{ d.productType!=2?"checked":"" }}>
					  <input type="radio" value="2" title="皮壳" name="productType" {{ d.productType==2?"checked":"" }}>
	              </td>
	              <td class="titleTd"><b class="red">*</b>客户名称：</td>
	              <td colspan="">
	              	<input type="text" class="layui-input" id="customInputChoose" value="{{ d.customer?d.customer.name:"" }}"
	              		placeholder="点击进行客户选择" readonly lay-verify="required" ></td>
	              <td class="imgTd" colspan="4" rowspan="4">
	                <div><b id="allWarehouseNumber">0</b><p>总库存数量</p></div>
	                <div>0<p>业务员所属数量</p></div>
	                <div>需要申请<p>发货状态</p></div>
	              </td>
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
	              <td colspan="3">
	              	<input type="text" class="layui-input" name="remark" value="{{ d.remark || "" }}">
	              	<input type="hidden" id="customerIdHidden" name="customerId" value="{{ d.customer?d.customer.id:"" }}">
	              	<input type="hidden" id="productIdHidden" name="productId" value="{{ d.product?d.product.id:"" }}">
	              	<input type="hidden" name="id" value="{{ d.id || "" }}">
					<span style="" lay-submit lay-filter="sureAddSendOrderSubmit" id="sureAddSendOrderSubmit"></span>
				  </td>
	            </tr>
	          </table>
	        </div>
	        
	      </div>
	    </div>
	    <div class="layui-card twoDiv" >
	      <p class="smallTitle"><span class="blueBlock">&nbsp;</span>其他业务员库存所属信息
	            <span class="layui-btn layui-btn-xs layui-btn-normal fright" id="addAskfor">添加申请</span></p>
	      <table id="otherWarehouseTable" lay-filter="otherWarehouseTable"></table>
	    </div>
		<div class="layui-card twoDiv" style="float: right;">
			<p class="smallTitle"><span class="blueBlock">&nbsp;</span>申请借货单&nbsp;&nbsp;
					申请时间：<span class="layui-btn layui-btn-primary layui-btn-xs" id="askforDate">2019-12-06 00:00:00</span>
					<span class="layui-btn layui-btn-xs layui-btn-danger fright" id="deleteAskfor">删除</span></p>
			<table id="askForTable" lay-filter="askForTable"></table>
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
			offset:'20px',
			content:html,
			area:['80%','90%'],
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
				mytable.renderNoPage({
					elem:'#askForTable',
					data:[],
					height:'420px',
					totalRow:['number'],
					cols:[[
						{ type:'checkbox', },
						{ field:'bacth',title:'批次号',},
						{ field:'users',title:'所属业务员', templet: getAllUserName(),},
						{ field:'number',title:'库存数量', width:'15%',},
						{ field:'askNumber',title:'申请数量',edit:true,width:'15%',},
					]],
					done:function(){
						$('#deleteAskfor').unbind().on('click',function(){
							var check = layui.table.checkStatus('askForTable').data;
							if(check.length==0)
								return myutil.emsg('请选择删除的申请信息');
							var askCache = layui.table.cache['askForTable'];
							var newData = [];
							layui.each(askCache,function(index,item){
								var isDelete = false;
								layui.each(check,function(i2,askItem){
									if(askItem.bacth==item.bacth){
										isDelete = true;
										return;
									}
								})
								if(!isDelete){
									newData.push(item)
								}
							})
							table.reload('askForTable',{
								data: newData,
							})
							
						})
						/*table.on('edit(askForTable)', function(obj){
							var trData = obj.data;
							var val = obj.value;
							if(isNaN(val) || val<0 || val%1.0!=0.0){
								var last = myutil.getLastData();
								trData['askNumber'] = last;
								$(this).val(last);
							}
						});*/
					}
				})
				mytable.renderNoPage({
					elem:'#otherWarehouseTable',
					data:[],
					height:'420px',
					totalRow:['number'],
					cols:[[
						{ type:'checkbox', },
						{ field:'bacth',title:'批次号',},
						{ field:'users',title:'所属业务员', templet: getAllUserName(),},
						{ field:'number',title:'库存数量',},
					]],
					done:function(){
						$('#addAskfor').unbind().on('click',function(){
							var check = layui.table.checkStatus('otherWarehouseTable').data;
							var askCache = layui.table.cache['askForTable'];
							if(check.length==0)
								return myutil.emsg('请选择需要申请的信息');
							layui.each(check,function(index,item){
								var isChoosed = false;
								layui.each(askCache,function(i2,askItem){
									if(askItem.bacth==item.bacth){
										isChoosed = true;
										return;
									}
								})
								if(!isChoosed){
									item.askNumber = 0;
									askCache.push(item)
								}
							})
							table.reload('askForTable',{
								data: askCache,
							})
						})
					}
				})
				if(data.id){	//如果存在id，进行数据回显
					
				}
				form.on('submit(sureAddSendOrderSubmit)',function(obj){
					if(obj.field.number==0)
						return myutil.emsg('发货数量不能为0');
					var data = obj.field;
					var url = '/ledger/addSendGoods';
					if(data.id)
						url = '';
					var tableData = layui.table.cache['askForTable'],json = [];
					var time = $('#askforDate').val(),msg = '';
					layui.each(tableData,function(index,item){
						if(msg)
							return;
						layui.each(item.userList,function(i2,childItem){
							if(msg)
								return;
							if(!item.askNumber || isNaN(item.askNumber) || item.askNumber%1.0!=0 )
								msg = '请正确填写申请数量';
							json.push({
								time: time,
								number: item.askNumber,
								approvalUserId: childItem.id,
							})
						})
					})
					if(msg)
						return myutil.emsg(msg);
					data.applyVoucher = JSON.stringify(json);
					
					console.log(data)
					return 
					myutil.saveAjax({
						url: url,
						data: data,
						success:function(){
							layer.close(win);
							opt.success && opt.success();
							table.reload('sendTable');
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
	
	function getAllUserName(){
		return function(d){
			var html = '';
			layui.each(d.userList,function(index,item){
				html += '<span class="layui-badge layui-bg-green">'+item.name+'</span>&nbsp;&nbsp;';
			})
			return html;
		}
	}
	function getWarehouseInfo(){
		var pid = $('#productIdHidden').val();
		if(pid){
			table.reload('otherWarehouseTable',{
				url: myutil.config.ctx+'/ledger/getOrderSend',
				where:{
					productId: pid,
					include: 1,
				},
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
							var allWarehouseNum = 0;
							layui.each(data.mapList,function(index,item){
								allWarehouseNum += item.number;
							})
							$('#allWarehouseNumber').html(allWarehouseNum);
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