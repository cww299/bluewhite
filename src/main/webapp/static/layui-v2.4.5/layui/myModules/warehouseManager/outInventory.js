/**2020/1/6 author:299
 * 发货单模块 type：1  发货单发货填写。   type:2 皮壳生产出库填写
 * outInventory
 */
layui.define(['jquery','layer','form',],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		mytable = layui.mytable,
		table = layui.table,
		myutil = layui.myutil;
	
	var outInventory = {
		type:1,
	};
	
	outInventory.add = function(data,opt){
		opt = opt || {};
		var url = '/ledger/inventory/getPutStorageDetails?id=',fieldNumber = 'surplusNumber';
		if(outInventory.type==2){
			url = '/ledger/inventory/getOrderOutSourcePutStorageDetails?id=';
			fieldNumber = 'cotSurplusNumber';
		}
		var sendGoodWin = layer.open({
			type: 1,
			title:'剩余发货数量：<b style="color:red">'+data[fieldNumber]+'</b>',
			area:['50%','600px'],
			content:[
				'<div style="padding:10px 0;">',
					'<table>',
						'<tr>',
							'<td style="padding-left:19px;">发货数量：</td>',
							'<td><input type="text" class="layui-input" id="sendAllNumber" value="0" readonly></td></tr>',
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
					parseData:function(ret){
						layui.each(ret.data || [],function(index,item){
							item.sendNumber = 0;
						})
						return {  msg:ret.message,  code:ret.code , data:ret.data, };
					},
					url: myutil.config.ctx+url+data.id,
					cols:[[
						{ type:'checkbox',},
						{ title:'入库单编号',field:'serialNumber'},
						{ title:'批次号', field:'bacthNumber',},
						{ title:'数量',field:'number'},
						{ title:outInventory.type==1?'发货数量':'申请数量',
							field:'sendNumber',edit:'number', },
					]],
				})
				table.on('edit(chooseInputOrder)',function(obj){
					var index = $(this).closest('tr').data('index');
					var trData = table.cache['chooseInputOrder'][index];
					var val = obj.value;
					if(obj.field==='sendNumber'){
						if(isNaN(val) || val<0 || val%1.0!=0.0 || val>trData.number){
							$(this).val(myutil.lastData);
							trData.sendNumber = myutil.lastData;
							myutil.emsg('请正确填写发货数量');
						}else{
							$(this).val(parseInt(obj.value));
							trData.sendNumber = parseInt(obj.value);
						}
						var check = $(this).closest('tr').find('input[type="checkbox"]').prop('checked');
						var checkbox = $(this).closest('tr').find('div.layui-form-checkbox');
						if(trData.sendNumber && !check){
							$(checkbox).click();
						}else if(!trData.sendNumber && check){
							$(checkbox).click();
						}
						sumAllNumber();
					}
				})
				table.on('checkbox(chooseInputOrder)', function(obj){
					sumAllNumber();
				});
				function sumAllNumber(){
					var check = layui.table.checkStatus('chooseInputOrder').data;
					var sum = 0;
					layui.each(check,function(index,item){
						sum -= -(item.sendNumber || 0);
					})
					$('#sendAllNumber').val(sum);
				}
			},
			yes:function(){
				var checkChild = layui.table.checkStatus('chooseInputOrder').data;
				if(checkChild.length<1)
					return myutil.emsg('请选择入库单');
				var inputNumber = $('#sendAllNumber').val() || 0;
				var childJson = [];
				for(var i=0,len=checkChild.length;i<len;i++){
					if(!checkChild[i].sendNumber || checkChild[i].sendNumber==0)
						return myutil.emsg('请填写数量！');
					if(checkChild[i].sendNumber>checkChild[i].number)
						return myutil.emsg('发货数量不能大于剩余数量！');
					childJson.push({
						id: checkChild[i].id,
						number: checkChild[i].sendNumber || 0,
					})
				}
				if(data[fieldNumber]==0){
					layer.confirm('剩余发货数量为0是否确认发货',function(){
						sendGoods();
					})
				}else
					sendGoods();
				function sendGoods(){
					myutil.saveAjax({
						url: '/ledger/inventory/sendOutStorage',
						data:{
							flag: outInventory.type,	//1.发货单发货    2.皮壳生产出库
							id: data.id,
							sendNumber: inputNumber,
							putStorage: JSON.stringify(childJson),
						},
						success:function(){
							layer.close(sendGoodWin);
							table.reload(opt.reloadTable || 'tableData');
						}
					})
				}
			}
		})
	};
	exports('outInventory',outInventory);
})
	