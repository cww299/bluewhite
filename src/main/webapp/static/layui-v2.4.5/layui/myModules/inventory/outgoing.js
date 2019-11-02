/* 2019/11/2
 * author: 299
   *   库存管理：外发入库 模板
 * outgoing.render({
 * 	 elem:'绑定元素', ctx:'ctx',
 * }) 
 */
layui.extend({
	mytable: 'layui/myModules/mytable',
}).define(['jquery','layer','form','laydate','mytable'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		laydate = layui.laydate,
		table = layui.table,
		mytable = layui.mytable,
		myutil = layui.myutil;
	
	var TPL_MAIN = [	//页面主模板
				'<table class="layui-form">',
					'<tr>',
						'<td style="width:100px;"><select id="searchTimeType">',
								'<option value="1">外发时间<option><option value="0">到货时间<option>',
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
						'<td>&nbsp;是否审核:</td>',
						'<td style="width:100px;"><select name=""><option value="">请选择</option>',
							'<option value="1">是</option>',
							'<option value="0">否</option>',
							'</select></td>',
						'<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>',
					'</tr>',
				'</table>',
				'<table id="tableData" lay-filter="tableData"></table>',
	           ].join(' ');
	
	var outgoing = {
			
	};
	outgoing.render = function(opt){
		$(opt.elem).append(TPL_MAIN);
		form.render();
		laydate.render({
			elem:'#searchTime',
			range:'~',
		})
		var allInventory = myutil.getDataSync({ url: opt.ctx+'/basedata/list?type=warehouseType&size=9999', });
		allInventory.unshift({ id:'',name:'请选择', });
		mytable.render({
			elem:'#tableData',
			size:'lg',
			url: opt.ctx+'/ledger/orderOutSourcePage',
			autoUpdate:{
				saveUrl: '/ledger/inventory/updateInventoryOrderOutSource',
				field:{ inWarehouseType_id:'inWarehouseTypeId', },
			},
			verify:{ count:['arrivalNumber'], },
			curd:{
				btn:[],
				otherBtn:function(obj){
					if(obj.event=='audit'){
						myutil.deleTableIds({
							url:'/ledger/inventory/confirmOrderOutSource',
							table:'tableData',
							text:'请选择相关信息进行审核入库|是否确认审核入库？',
						})
					}
				},
			},
			ifNull:'',
			/*colsWidth:[0,0,10,6,10,6,8,8,6,6,],*/
			toolbar:['<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="audit">审核入库</span>',].join(' '),
			cols:[[
			       { type:'checkbox',},
			       { title:'外发编号',   field:'outSourceNumber',	},
			       { title:'外发工序',   field:'process',	},
			       { title:'外发数量',   field:'processNumber',	},
			       { title:'外发时间',   field:'openOrderTime', type:'date',},
			       { title:'跟单人',   field:'user_userName',	},
			       { title:'加工点',   field:'customer_name',	},
			       { title:'预计仓库',   field:'warehouseType_name',	},
			       { title:'到货仓库',   field:'inWarehouseType_id', type:'select', select:{data:allInventory,}	},
			       { title:'到货数量',   field:'arrivalNumber',	 edit:true, },
			       { title:'到货时间',   field:'arrivalTime', type:'date', edit:true, 	},
			       { title:'是否审核',   field:'arrival',	transData:{ data:['否','是'],}, },
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
	}
	
	exports('outgoing',outgoing);
})