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
								productId: check[0].materialRequisition.order.product.id,
								orderOutSourceId: check[0].id,
								inStatus:1,
							}
						})
					}else if(obj.event=='addOutBtn'){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据操作！');
						outInventory(check[0])
					}
				},
			},
			ifNull:'',
			/*colsWidth:[0,0,10,6,10,6,8,8,6,6,],*/
			toolbar:[
				productionInput.type!=4?
				'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="addBtn">生成入库单</span>':
					'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="addOutBtn">生成出库单</span>',
			].join(' '),
			cols:[[
			       { type:'checkbox',},
			       { title:'编号',   field:'outSourceNumber',	},
			       { title:'工序',   field:'process',	templet: getProcess(), },
			       { title:'数量',   field:'processNumber',	},
			       { title:'时间',   field:'openOrderTime', type:'dateTime',},
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
	};
	
	function outInventory(data){
		var allInputNumber = 0; //计算总库存数量，发货数量不能超过该值
		var sendGoodWin = layer.open({
			type: 1,
			title:'剩余发货数量：<b style="color:red">'+data.surplusNumber+'</b>',
			area:['50%','600px'],
			content:[
				'<div style="padding:10px 0;">',
					'<table>',
						'<tr>',
							'<td>发货数量：</td>',
							'<td><input type="text" class="layui-input" id="sendAllNumber"></td></tr>',
					'</table>',
					'<table id="chooseInputOrder" lay-filter="chooseInputOrder"></table>',
				'</div>',
			].join(' '),
			btn:['确定','取消'],
			btnAlign:'c',
			success:function(){
				mytable.renderNoPage({
					elem:'#chooseInputOrder',
					height:'400px',
					url: myutil.config.ctx+'/ledger/inventory/getOrderOutSourcePutStorageDetails?id='+data.id,
					cols:[[
						{ type:'checkbox',},
						{ title:'入库单编号',field:'serialNumber'},
						{ title:'数量',field:'number'},
						{ title:'发货数量',field:'sendNumber',edit:true,
							templet:'<span>{{ d.sendNumber || "" }}</span>'},
					]],
					done:function(r){
						layui.each(r.data,function(index,item){
							allInputNumber -= (-item.number);
						})
					}
				})
				table.on('edit(chooseInputOrder)',function(obj){
					var index = $(this).closest('tr').data('index');
					var trData = table.cache['chooseInputOrder'][index];
					var val = obj.value;
					if(obj.field==='sendNumber'){
						if(isNaN(val) || val<0 || val%1.0!=0.0){
							$(this).val(myutil.lastData);
							trData.sendNumber = myutil.lastData;
							myutil.emsg('请正确填写发货数量');
						}
					}
				})
			},
			yes:function(){
				var checkChild = layui.table.checkStatus('chooseInputOrder').data;
				if(checkChild.length<1)
					return myutil.emsg('请选择入库单');
				var inputNumber = $('#sendAllNumber').val() || 0;
				if(allInputNumber<inputNumber)
					return myutil.esmg('发货数量不能超过库存数量！');
				var childJson = [],allChildNumer = 0;
				for(var i=0,len=checkChild.length;i<len;i++){
					allChildNumer -= (-checkChild[i].sendNumber || 0);
					childJson.push({
						id: checkChild[i].id,
						number: checkChild[i].sendNumber || '',
					})
				}
				var msg = '';
				if(allChildNumer>0){
					if(inputNumber>0){
						if(inputNumber!=allChildNumer)
							msg = '填写的发货数量与总发货数量不同！请检查';
					}else{
						inputNumber = allChildNumer;
					}
				}else if(inputNumber==0){
					msg = '请填写发货数量';
				}
				if(msg)
					return myutil.emsg(msg);
				myutil.saveAjax({
					url: '/ledger/inventory/sendOutStorage',
					data:{
						flag: 2,
						id: data.id,
						sendNumber: inputNumber,
						putStorage: JSON.stringify(childJson),
					},
					success:function(){
						layer.close(sendGoodWin);
						table.reload('tableData');
					}
				})
			}
		})
	};
	
	exports('productionInput',productionInput);
})