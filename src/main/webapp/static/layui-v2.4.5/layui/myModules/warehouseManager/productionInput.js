/* 2019/11/2
 * author: 299
   *   库存管理：生产入库模块
 * productionInput.render({
 * 	 elem:'绑定元素',
 *   ctx:'ctx',
 *   
 * }) 
 * productionInput.type = 2.成品生产入库。 3.皮壳生产入库 4.皮壳生产出库
 */
layui.extend({
	mytable: 'layui/myModules/mytable',
	inputWarehouseOrder: 'layui/myModules/warehouseManager/inputWarehouseOrder',
}).define(['jquery','layer','form','laydate','mytable','inputWarehouseOrder',],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		laydate = layui.laydate,
		table = layui.table,
		inputWarehouseOrder = layui.inputWarehouseOrder,
		mytable = layui.mytable,
		myutil = layui.myutil;
	
	var TPL_MAIN = [	//页面主模板
				'<table class="layui-form">',
					'<tr>',
						'<td style="width:100px;"><select id="searchTimeType">',
								'<option value="1">外发时间<option>',
							'</select></td>',
						'<td><input type="text" class="layui-input" id="searchTime"></td>',
						'<td>&nbsp;编号:	</td>',
						'<td><input type="text" name="outSourceNumber" class="layui-input"></td>',
						'<td>&nbsp;工序:	</td>',
						'<td><input type="text" name="process" class="layui-input"></td>',
						'<td>&nbsp;跟单人:</td>',
						'<td><input type="text" name="userName" class="layui-input"></td>',
						'<td>&nbsp;加工点:</td>',
						'<td><input type="text" name="customerName" class="layui-input"></td>',
						'<td>&nbsp;是否外发:</td>',
						'<td style="width:100px;"><select name="outsource">',
								'<option value="">是否外发<option>',
								'<option value="0">否<option>',
								'<option value="1">是<option>',
							'</select></td>',
						'<td>&nbsp;</td>',
						'<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>',
					'</tr>',
				'</table>',
				'<table id="tableData" lay-filter="tableData"></table>',
	           ].join(' ');
	
	var productionInput = {
		type: 2,
	};
	productionInput.render = function(opt){
		inputWarehouseOrder.type = productionInput.type;
		$(opt.elem).append(TPL_MAIN);
		form.render();
		laydate.render({
			elem:'#searchTime',
			range:'~',
		})
		var otherParam = '';
		if(productionInput.type==3){
			otherParam = '&outsourceTaskId=387';
		}else if(productionInput.type==2 || productionInput.type==4){
			otherParam = '&outsourceTaskId=388';
		}
		mytable.render({
			elem:'#tableData',
			size:'lg',
			url: opt.ctx+'/ledger/orderOutSourcePage?audit=1'+otherParam,
			autoUpdate:{
				saveUrl: '/ledger/inventory/updateInventoryOrderOutSource',
				field:{ inWarehouseType_id:'inWarehouseTypeId', },
			},
			verify:{ count:['arrivalNumber'], },
			curd:{
				btn:[],
				otherBtn:function(obj){
					var check = layui.table.checkStatus('tableData').data;
					if(obj.event=='addBtn'){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据操作！');
						inputWarehouseOrder.add({
							data:{
								productId: check[0].materialRequisition.order.product.id,
								orderOutSourceId: check[0].id,
								inStatus:1,
							}
						})
					}else if(obj.event=='addOutBtn'){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据操作！');
						opt.outInventory.add(check[0]);
					}else if(obj.event=='askfor'){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据操作！');
						askfor(check[0]);
					}
				},
			},
			ifNull:'',
			toolbar:[
				productionInput.type!=4?
				'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="addBtn">生成入库单</span>':
					('<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="addOutBtn">生成出库单</span>'+
					  '<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="askfor">借货申请</span>'
					),
			].join(' '),
			cols:[
				(function(){
				var arr =  [{ type:'checkbox',},
			       { title:'编号',   field:'outSourceNumber',	width:190,},
			       { title:'加工单',   field:'materialRequisition_order_orderNumber',	},
			       { title:'工序',   field:'process',	templet: getProcess(), width:100,},
			       { title:'数量',   field:'processNumber',	width:100, },
			       { title:'时间',   field:'openOrderTime', type:'dateTime', width:200,},
			       { title:'跟单人',   field:'user_userName',	width:120,},
			       { title:'加工点',   field:'customer_name',	width:120,},
			       { title:'是否外发',   field:'outsource',	transData:{ data:['否','是'],}, width:100,},];
				if(productionInput.type==3 || productionInput.type==2)
					arr.push({ title:'剩余数量',   field:'remainingInventory',width:100,	});
				else if(productionInput.type==4){
					arr.push({ title:'剩余数量',   field:'cotSurplusNumber',	width:100,});
					arr.push({ title:'库存状态',   field:'cotStatus',	transData:{data:['库存充足','库存不足','无库存',]}, width:100,});
				}
				return arr;
				})(),
			]
		})
		function getProcess(){
			return function(d){
				var html = '';
				layui.each(d.outsourceTask,function(index,item){
					html += '<span class="layui-badge layui-bg-green">'+item.name+'</span>&nbsp;&nbsp;';
				})
				return html;
			}
		}
		form.on('submit(search)',function(obj){
			var t = $('#searchTime').val();
			if(t){
				t = t.split(' ~ ');
				if($('#searchTimeType').val()==1){
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
		inputWarehouseOrder.init();
	};
	function askfor(data){		//皮壳生产出库 type=4  借货申请
		layer.open({
			type: 1,
			title:'借货申请',
			area:['50%','600px'],
			content:[
				'<div style="padding-top:10px;">',
					'<table id="chooseInputOrder" lay-filter="chooseInputOrder"></table>',
				'</div>',
			].join(' '),
			btn:['确定','取消'],
			btnAlign:'c',
			success:function(){
				mytable.renderNoPage({
					elem:'#chooseInputOrder',
					height:'460px',
					url: myutil.config.ctx+'/ledger/inventory/getOrderOutSourcePutStorageDetails?id='+data.id,
					cols:[[
						{ type:'checkbox', },
						{ field:'serialNumber',title:'入库单编号', },
						{ field:'bacthNumber',title:'批次号', },
						{ field:'number',title:'数量', },
						{ field:'',title:'申请数量', edit:'number', },
					]]
				})
			},
			yes:function(index, layero){
				
			}
		})
	}
	exports('productionInput',productionInput);
})