/* 2019/12/4
 * author: 299
   *   出库单列表模块
 * outOrderList.render({
 * 	 elem:'绑定元素', ctx:'ctx',
 * }) 
 * type: 库存类型，引用模块前先设置 outOrderList.type;
 * 		1:物料出库单、2:成品出库单、3:皮壳出库单
 */
layui.extend({
	mytable: 'layui/myModules/mytable',
	outWarehouseOrder: 'layui/myModules/warehouseManager/outWarehouseOrder',
}).define(
	['jquery','layer','form','laydate','mytable','outWarehouseOrder'],
	function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		table = layui.table,
		laydate = layui.laydate,
		mytable = layui.mytable,
		outWarehouseOrder = layui.outWarehouseOrder,
		myutil = layui.myutil;
	
	var TPL_MAIN = [	//页面主模板
				`
				<table class="layui-form searchTable">
					<tr>
						<td>出库日期:</td>
						<td><input type="text" name="orderTimeBegin" id="inputDate" class="layui-input"></td>
						<td>物料编号:</td>
						<td><input type="text" name="materielNumber" class="layui-input"></td>
						<td>物料名称:</td>
						<td><input type="text" name="materielName" class="layui-input"></td>
						<td>出库状态:</td>
						<td><select name="outStatus"><option value="">请选择</option>
									<option value="1">生产出库</option>
									<option value="2">调拨出库</option>
									<option value="3">销售换货出库</option>
									<option value="4">采购退货出库</option>
									<option value="5">盘盈出库</option></select></td>
						<td>
							<button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
					</tr>
				</table>
				<table id="tableData" lay-filter="tableData"></table>
				`,
	           ].join(' ');
	
	var outOrderList = {
			type:2,	//默认为成品库存
	};
	outOrderList.render = function(opt){
		outWarehouseOrder.type = outOrderList.type;
		$(opt.elem).append(TPL_MAIN);
		laydate.render({elem:'#inputDate',range:'~'})
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/ledger/inventory/outStoragePage',
			curd:{
				otherBtn:function(obj){
					var check = layui.table.checkStatus('tableData').data;
					if(obj.event=="update"){
						if(check.length!=1)
							return myutil.emsg('只能修改一条数据');
						outWarehouseOrder.update({
							data: check[0],
							success: function(){
								table.reload('tableData');
							}
						});
					}
				},
				btn:[4],
			},
			toolbar:[
				'<span class="layui-btn layui-btn-sm" lay-event="update">修改</span>',
			].join(''),
			autoUpdate:{
				deleUrl:'/ledger/inventory/deleteOutStorage',
			},
			ifNull:'--',
			cols:[[
			       { type:'checkbox',},
			       { title:'出库编号', field:'serialNumber',},
			       { title:'出库时间',   field:'arrivalTime', type:'dateTime',width:'10%',	},
			       { title:'出库数量',   field:'arrivalNumber',   },
			       { title:'出库操作人',   field:'userStorage_userName',	},
			       { title:'面料',   field:'materiel_name',	},
			       { title:'状态',   field:'outStatus',  transData:{ data:['','生产出库','调拨出库','销售换货出库','采购退货出库','盘盈出库'], } },
			       ]]
		})
		form.on('submit(search)',function(obj){
			var field = obj.field;
			if(field.orderTimeBegin){
				var time = field.orderTimeBegin.split(' ~ ');
				field.orderTimeBegin = time[0]+' 00:00:00';
				field.orderTimeEnd = time[1]+' 23:59:59';
			}else{
				field.orderTimeEnd = ''; 
			}
			table.reload('tableData',{
				where: obj.field,
			})
		})
		outWarehouseOrder.init();
	}
	
	exports('outOrderList',outOrderList);
})