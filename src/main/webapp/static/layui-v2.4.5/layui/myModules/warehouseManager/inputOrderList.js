/* 2019/12/4
 * author: 299
   *   入库单列表模块
 * inputOrderList.render({
 * 	 elem:'绑定元素', ctx:'ctx',
 * }) 
 * type: 库存类型，引用模块前先设置 inputOrderList.type;
 * 		1:物料入库单、2:成品入库单、3:皮壳入库单
 */
layui.extend({
	mytable: 'layui/myModules/mytable',
	inputWarehouseOrder: 'layui/myModules/warehouseManager/inputWarehouseOrder',
}).define(
	['jquery','layer','form','laydate','mytable','inputWarehouseOrder'],
	function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		table = layui.table,
		laydate = layui.laydate,
		mytable = layui.mytable,
		inputWarehouseOrder = layui.inputWarehouseOrder,
		myutil = layui.myutil;
	
	var TPL_MAIN = [	//页面主模板
				`
				<table class="layui-form searchTable">
					<tr>
						<td>入库日期:</td>
						<td><input type="text" name="orderTimeBegin" id="inputDate" class="layui-input"></td>
						<td>物料编号:</td>
						<td><input type="text" name="materielNumber" class="layui-input"></td>
						<td>物料名称:</td>
						<td><input type="text" name="materielName" class="layui-input"></td>
						<td>库区:</td>
						<td><select name="storageAreaId" id="storageAreaId"><option value="">请选择</option></select></td>
					</tr>
					<tr>
						<td>库位:</td>
						<td><select name="storageLocationId" id="storageLocationId"><option value="">请选择</option></select></td>
						<td class="verifyGood">是否验货:</td>
						<td class="verifyGood"><select name="inspection"><option value="">请选择</option>
													  <option value="0">否</option>
													  <option value="1">是</option></select></td>
						<td>采购单状态:</td>
						<td><select name="inStatus"><option value="">请选择</option>
									<option value="1">生产入库</option>
									<option value="2">调拨入库</option>
									<option value="3">退货入库</option>
									<option value="4">换货入库</option>
									<option value="5">盘亏入库</option></select></td>
						<td colspan="2" class="btnTd">
							<button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
					</tr>
				</table>
				<table id="tableData" lay-filter="tableData"></table>
				`,
	           ].join(' ');
	
	var inputOrderList = {
			type:2,	//默认为成品库存
	};
	inputOrderList.render = function(opt){
		$(opt.elem).append(TPL_MAIN);
		if(inputOrderList.type==2 || inputOrderList.type==3)
			$('.verifyGood').hide();
		laydate.render({elem:'#inputDate',range:'~'})
		form.render();
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/ledger/inventory/putStoragePage',
			curd:{
				otherBtn:function(obj){
					var check = layui.table.checkStatus('tableData').data;
					if(obj.event=="update"){
						if(check.length!=1)
							return myutil.emsg('只能修改一条数据');
						inputWarehouseOrder.update({
							data: check[0],
							success: function(){
								table.reload('tableData');
							}
						});
					}else if(obj.event=='delete'){
						myutil.deleTableIds({
							url:'/ledger/inventory/deletePutStorage',
							table:'tableData',
							text:'请选择信息|是否确认撤销？',
						})
					}
				},
				btn:[],
			},
			toolbar:[
				/*'<span class="layui-btn layui-btn-sm" lay-event="update">修改</span>',*/
				'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">撤销入库</span>',
			].join(''),
			autoUpdate:{
				deleUrl:'/ledger/inventory/deletePutStorage',
			},
			ifNull:'--',
			cols:[[
			       { type:'checkbox',},
			       { title:'入库编号', field:'serialNumber',width:'12%',	},
			       { title:'入库时间',   field:'arrivalTime', type:'date',width:'10%',	},
			       { title:'入库数量',   field:'arrivalNumber',   },
			       { title:'剩余数量',   field:'surplusNumber',width:'8%',	},
			       { title:'库区',   field:'storageArea_name', 	},
			       { title:'库位',   field:'storageLocation_name',	},
			       { title:'入库人',   field:'userStorage_userName',	},
			       { title:'产品',   field:'product_name',	width:'18%',},
			       { title:'入库内容',   field:'orderProcurement_orderProcurementNumber',	width:'20%'},
			       { title:'状态',   field:'inStatus',  transData:{ data:['','生产入库','','调拨入库','退货入库','换货入库','盘亏入库'], } },
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
		inputWarehouseOrder.type = inputOrderList.type;
		inputWarehouseOrder.init(function(){
			$('#storageLocationId').append(inputWarehouseOrder.allStorageLocation)
			$('#storageAreaId').append(inputWarehouseOrder.allStorageArea)
			form.render();
		});
	}
	
	exports('inputOrderList',inputOrderList);
})