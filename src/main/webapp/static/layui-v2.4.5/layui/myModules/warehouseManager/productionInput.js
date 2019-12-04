/* 2019/11/2
 * author: 299
   *   库存管理：生产入库模块
 * productionInput.render({
 * 	 elem:'绑定元素', ctx:'ctx',
 * }) 
 * productionInput.type = 2.成品生产入库。3.皮壳生产入库
 */
layui.extend({
	mytable: 'layui/myModules/mytable',
	inputWarehouseOrder: 'layui/myModules/warehouseManager/inputWarehouseOrder',
}).define(['jquery','layer','form','laydate','mytable','inputWarehouseOrder'],function(exports){
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
		mytable.render({
			elem:'#tableData',
			size:'lg',
			url: opt.ctx+'/ledger/orderOutSourcePage?audit=1',
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
								productId: check[0].order.product.id,
								orderOutSourceId: check[0].id,
								inStatus:1,
							}
						})
					}
				},
			},
			ifNull:'',
			/*colsWidth:[0,0,10,6,10,6,8,8,6,6,],*/
			toolbar:['<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="addBtn">生产入库单</span>',].join(' '),
			cols:[[
			       { type:'checkbox',},
			       { title:'外发编号',   field:'outSourceNumber',	},
			       { title:'外发工序',   field:'process',	templet: getProcess(), },
			       { title:'外发数量',   field:'processNumber',	},
			       { title:'外发时间',   field:'openOrderTime', type:'dateTime',},
			       { title:'跟单人',   field:'user_userName',	},
			       { title:'加工点',   field:'customer_name',	},
			       { title:'是否外发',   field:'outsource',	transData:{ data:['否','是'],}, },
			       ]]
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
	}
	exports('productionInput',productionInput);
})