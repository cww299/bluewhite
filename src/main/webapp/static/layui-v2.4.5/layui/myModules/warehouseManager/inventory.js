/* 2019/11/2
 * author: 299
   *   库存管理：库存 模板
 * inventory.render({
 * 	 elem:'绑定元素', 
 * 	 ctx:'ctx',
 *   chooseProductWin: true,  //是否只用来双击选择商品弹窗
 * }) 
 * type: 库存类型，引用模块前先设置 inventory.type;
 * 		1.物料库存、2:成品库存、3:皮壳库存
 */
layui.extend({
	inputWarehouseOrder: 'layui/myModules/warehouseManager/inputWarehouseOrder',
	outWarehouseOrder: 'layui/myModules/warehouseManager/outWarehouseOrder',
	askfor: 'layui/myModules/askfor/askfor',
}).define(
	['jquery','layer','form','laydate','mytable',],
	function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		table = layui.table,
		mytable = layui.mytable,
		inputWarehouseOrder = layui.inputWarehouseOrder,
		outWarehouseOrder = layui.outWarehouseOrder,
		askfor = layui.askfor,
		myutil = layui.myutil;
	var TPL_MAIN = [	//页面主模板
				'<table class="layui-form">',
					'<tr>',
						'<td>编号:</td>',
						'<td><input type="text" name="number" class="layui-input"></td>',
						'<td>&nbsp;商品名:</td>',
						'<td><input type="text" name="name" class="layui-input"></td>',
						'<td>&nbsp;&nbsp;</td>',
						'<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>',
						`
						<td>&nbsp;&nbsp;</td>
						<td><span class="layui-badge" id="tipsInfo">点击标绿的商品库存，查看库存详情</span></td>
						`,
					'</tr>',
				'</table>',
				'<table id="tableData" lay-filter="tableData"></table>',
				`
					<div style="display:none;" id="orderOutSourceTableWin">
						<div style="width:600px;">
							<table id="orderOutSourceTable" lay-filter="orderOutSourceTable"></table>
						</div>
					</div>
				`,
	           ].join(' ');
	
	var inventory = {
			type:2,	//默认为成品库存
	},allWarehouseType = [];
	
	inventory.render = function(opt){
		inventory.type = opt.type || inventory.type;
		$(opt.elem).append(TPL_MAIN);
		if(opt.chooseProductWin){
			$('#tipsInfo').html('双击进行选择');
		}
		form.render();
		allWarehouseType = [];
		myutil.getDataSync({
			url: myutil.config.ctx+'/basedata/list?type=warehouseType',
			success:function(d){
				layui.each(d,function(index,item){
					if(item.ord==inventory.type){
						allWarehouseType.push(item);
					}
				})
			}
		});
		
		var cols = [
	       { type:'checkbox',},
	       { title:'产品编号',   field:'number',width:'10%',	},
	       { title:'产品名',   field:'name',	},
	    ];
		layui.each(allWarehouseType,function(i,data){
			cols.push({
				title: data.name,
				field: 'wid'+data.id,
				event: 'wid-'+data.id,
				width: '12%',
				templet: function(d){
					if(d['wid'+data.id]==0)
						return '<b style="color:red">'+d['wid'+data.id]+'</b>';
					else
						return '<b style="color:green">'+d['wid'+data.id]+'</b>';
				}
			})
		})
		mytable.render({
			elem:'#tableData',
			parseData: function(r){
				if(r.code==0){
					layui.each(r.data.rows,function(i,item){
						layui.each(item.mapList,function(j,itemMapList){
							item['wid'+itemMapList.warehouseTypeId] = itemMapList.number;
						})
					})
					return {  msg:r.message,  code:r.code , data:r.data.rows, count:r.data.total }; 
				}
				else
					return {  msg:r.message,  code:r.code , data:[], count:0 }; 
			},
			limit: opt.chooseProductWin?10:15,
			limits:[10,15,20,30,50,100],
			curd:{
				btn:[],
				otherBtn:function(obj){
					var check = layui.table.checkStatus('tableData').data;
					if(check.length!=1)
						return myutil.emsg("只能选择一条数据进行操作");
					if(obj.event=='addInp'){
						inputWarehouseOrder.add({
							data:{
								productId: check[0].id,
							}
						})
					}else if(obj.event=="addOut"){
						outWarehouseOrder.add({
							data:{
								productId: check[0].id,
							},
						})
					}else if(obj.event=='askfor'){
						askfor.add({
							productId: check[0].id,
						});
					}
				},
			},
			toolbar: opt.chooseProductWin?'':[
					'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="addInp">生成入库单</span>',
					'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="addOut">生成出库单</span>',
					'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="askfor">申请单</span>',
				].join(' '),
			url: opt.ctx+'/inventory/productPages?warehouse='+inventory.type,
			cols:[ cols ]
		})
		table.on('tool(tableData)', function(obj){
			if(opt.chooseProductWin)
				return;
			var data = obj.data;
			var event = obj.event.split('-');
			if(event.length==2 && event[0]=="wid"){
				if(data[event[0]+event[1]]==0)
					return myutil.emsg('该商品在该仓库无库存！');
				layer.open({
					type:1,
					area:['100%','80%'],
					shadeClose:true,
					content:`
						<div>
							<table id="warehoseInof" lay-filter="warehoseInof"></table>
						</div>
					`,
					title:'库存详情',
					success:function(){
						mytable.renderNoPage({
							elem:'#warehoseInof',
							url: myutil.config.ctx+'/ledger/inventory/detailsInventory',
							where:{
								warehouseTypeId: event[1],
								productId: data.id,
							},
							ifNull:'---',
							cols:[[
								{ title:'入库编号', field:'serialNumber',},
								{ title:'入库时间', field:'arrivalTime',},
								{ title:'入库数量', field:'arrivalNumber',},
								{ title:'剩余数量', field:'surplusNumber',},
								{ title:'库区', field:'storageArea_name',},
								{ title:'库位', field:'storageLocation_name',},
								{ title:'状态',   field:'inStatus',  transData:{ data:['','生产入库','','调拨入库','退货入库','换货入库','盘亏入库'], } },
							]],
							done:function(){
								var tipInventory = 0;
								layui.each($('div[lay-id=warehoseInof] td[data-field="inStatus"]'),function(index,item){
									$(item).on('mouseover',function(){
										var elem = $(item);
										var index = elem.closest('tr').data('index');
										var trData = table.cache['warehoseInof'][index];
										if(trData.inStatus==1){		//如果是生产入库
											var order = trData.orderOutSource.order;
											layui.each(order.orderChilds,function(i,d1){
												d1.bacthNumber = order.bacthNumber;
												d1.allNumber = order.number;
												d1.orderDate = order.orderDate;
											})
											mytable.renderNoPage({
												elem:'#orderOutSourceTable',
												data: order.orderChilds,
												cols:[[
													{ title:'批次号', field:'bacthNumber',},
													{ title:'下单时间', field:'orderDate',},
													{ title:'总数量', field:'allNumber',},
													{ title:'数量', field:'childNumber',},
													{ title:'客户', field:'customer_name',},
													{ title:'跟单人', field:'user_userName',},
												]],
												done:function(){
													merge('bacthNumber');
													merge('orderDate');
													merge('allNumber');
													function merge(field){
														var allCol = $('#orderOutSourceTable').next().find('td[data-field="'+field+'"]');
														layui.each(allCol,function(index,item){
															if(index!=0){
																$(item).css('display','none')
															}else{
																$(item).attr('rowspan',allCol.length)
															}
														})
													}
													layer.close(tipInventory)
													tipInventory = layer.tips($('#orderOutSourceTableWin').html(), elem,{
														time:0,
														area: '630px',
														tips: [2, 'rgb(95, 184, 120)'],
													})
													$('div[lay-id=orderOutSourceTable] tr').append(`
													<style>div[lay-id=orderOutSourceTable] tr:hover{background-color:transparent}</style>
													`);
												}
											})
										}
									})
								})
								$(document).on('mousedown', function (event) { layer.close(tipInventory); });
							}
						})
					}
					
				})
			}
		});
		form.on('submit(search)',function(obj){
			var t = $('#searchTime').val();
			if(t){
				t = t.split(' ~ ');
				if($('#searchTimeType').val()==0){
					obj.field.arrivalTime = '2019-11-02 00:00:00';
					obj.field.openOrderTime = '';
				}else{
					obj.field.arrivalTime = '';
					obj.field.openOrderTime = '2019-11-02 00:00:00';
				}
				t[0]+=' 00:00:00';
				t[1]+=' 23:59:59';
			}else
				t = ['',''];
			obj.field.orderTimeBegin = t[0];
			obj.field.orderTimeEnd = t[1];
			table.reload('tableData',{
				where: obj.field,
				page: { curr:1 },
			})
		})
		opt.done && opt.done();
		if(!opt.chooseProductWin){
			layui.use(['inputWarehouseOrder','outWarehouseOrder','askfor'],function(){
				inputWarehouseOrder = layui.inputWarehouseOrder;
				outWarehouseOrder = layui.outWarehouseOrder;
				askfor = layui.askfor;
				
				inputWarehouseOrder.type = inventory.type;
				outWarehouseOrder.type = inventory.type;
				askfor.type = askfor.allType.outInput.id;
				
				inputWarehouseOrder.init();
				outWarehouseOrder.init();
			})
		}
	}
	exports('inventory',inventory);
})