/* 2019/11/2
 * author: 299
   *   库存管理：库存 模板
 * inventory.render({
 * 	 elem:'绑定元素', ctx:'ctx',
 * }) 
 * type: 库存类型，引用模块前先设置 inventory.type;
 * 		1.物料库存、2:成品库存、3:皮壳库存
 */
layui.extend({
	mytable: 'layui/myModules/mytable',
	inputWarehouseOrder: 'layui/myModules/warehouseManager/inputWarehouseOrder',
	outWarehouseOrder: 'layui/myModules/warehouseManager/outWarehouseOrder',
}).define(
	['jquery','layer','form','laydate','mytable','inputWarehouseOrder','outWarehouseOrder'],
	function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		table = layui.table,
		mytable = layui.mytable,
		inputWarehouseOrder = layui.inputWarehouseOrder,
		outWarehouseOrder = layui.outWarehouseOrder,
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
					'</tr>',
				'</table>',
				'<table id="tableData" lay-filter="tableData"></table>',
	           ].join(' ');
	
	var inventory = {
			type:2,	//默认为成品库存
	};
	inventory.render = function(opt){
		$(opt.elem).append(TPL_MAIN);
		form.render();
		mytable.render({
			elem:'#tableData',
			limit:15,
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
					}
				},
			},
			toolbar:[
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="addInp">生成入库单</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="addOut">生成出库单</span>',
			].join(' '),
			url: opt.ctx+'/inventory/productPages?warehouse='+inventory.type,
			cols:[[
			       { type:'checkbox',},
			       { title:'产品编号',   field:'number',	},
			       { title:'产品名',   field:'name',	},
			       { title:'库存',   field:'inventorys',	templet:function(d){
			    	   		var number = 0;
			    	   		/*for(var i=0,len=d.inventorys.length;i<len;i++){
			    	   			number += d.inventorys[i].number;
			    	   		}
			    	   		var color = number?'green':'red';*/
			    	   		return '<span style="color:'+color+';">'+number+'</span>';
			       		}},
			       ]]
		})
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
		inputWarehouseOrder.type = inventory.type;
		outWarehouseOrder.type = inventory.type;
		inputWarehouseOrder.init();
		outWarehouseOrder.init();
	}
	
	exports('inventory',inventory);
})