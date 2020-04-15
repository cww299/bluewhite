/**2020/01/10 author:299
 * 位数记录列表模块  numberList
 * type:1  尾数找回，进行入库  库存
 * type:2  尾数丢失，清报财务  记录
 */
layui.config({
	base: 'static/layui-v2.4.5/'
}).extend({
	clearNumberOrder: 'layui/myModules/product/clearNumberOrder',
}).define(['jquery','layer','form','laydate','mytable','table','clearNumberOrder'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		clearNumberOrder = layui.clearNumberOrder,
		laytpl = layui.laytpl,
		table = layui.table,
		mytable = layui.mytable,
		myutil = layui.myutil;
	
	var TPL = [
		'<table class="layui-form searchTable">',
			'<tr>',
				'<td>入库时间：</td>',
				'<td><input class="layui-input" name="time_be_date" id="searchTime" type="text"></td>',
				'<td>批次号：</td>',
				'<td><input class="layui-input" name="underGoods.bacthNumber_like" type="text"></td>',
				'<td>产品名：</td>',
				'<td><input class="layui-input" name="underGoods.product.name_like" type="text"></td>',
				'<td><span class="layui-btn" lay-submit lay-filter="searchBtn">搜索</span></td>',
			'</tr>',
		'</table>',
		'<table id="tableData" lay-filter="tableData"></table>',
	].join(' ');
	
	var OUT_INVENTORY_TPL = [
		'<div class="layui-form layui-form-pane" style="padding:20px;" id="outInventoryWin">',
		'<p style="display:none;"><button lay-submit lay-filter="sureOutInventory" id="sureOutInventory"></button></p>',
		'<div class="layui-form-item" pane>',
			'<label class="layui-form-label">出库时间</label>',
			'<div class="layui-input-block">',
				'<input class="layui-input" lay-verify="required" name="time" id="outTime" autocomplete="off" type="text">',
			'</div>',
		'</div>',
		'<div class="layui-form-item" pane>',
			'<label class="layui-form-label">出库数量</label>',
			'<div class="layui-input-block">',
				'<input class="layui-input" lay-verify="number" name="number" autocomplete="off" >',
			'</div>',
		'</div>',
	'</div>',
	].join(' ');
	var numberList = {
		type: 1,
	};
	numberList.render = function(opt){
		numberList.type = opt.type || numberList.type;
		myutil.config.ctx = opt.ctx || "";
		myutil.clickTr();
		$(opt.elem || '#app').append(TPL);
		form.render();
		laydate.render({ elem:'#searchTime',range:'~', });
		var cols = [
			{ type:'checkbox', },
			{ title:'入库编号',field:'mantissaNumber', width:165, },
			{ title:'入库时间',field:'time', width:165, },
			{ title:'批次号',field:'underGoods_bacthNumber',  width:165, },
			{ title:'产品名',field:'underGoods_product_name', },
			{ title:'数量',field:'number', width:120, },
		];
		if(numberList.type==1)
			cols.push({ title:'剩余数量',field:'surplusNumber', width:120, });
		cols.push({ title:'备注',field:'remarks', });
		mytable.render({
			elem:'#tableData',
			colFilterRecord:'local',
			url: myutil.config.ctx+'/temporaryPack/findPagesMantissaLiquidation?type='+numberList.type,
			toolbar:[
				'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="edit">修改</span>',
				(function(){
					if(numberList.type==1)
						return '<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="outInventory">出库</span>';
					return '';
				})(),
			].join(' '),
			curd:{
				btn:[4],
				otherBtn:function(obj){
					var check = layui.table.checkStatus('tableData').data;
					if(obj.event=='edit'){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据进行编辑！',{ anim:6, });
						clearNumberOrder.update(check[0]);
					}else if(obj.event=='outInventory'){
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据进行出库！',{ anim:6, });
						outInventory(check[0]);
					}
				}
			},
			autoUpdate:{
				deleUrl:'/temporaryPack/deleteMantissaLiquidation',
			},
			cols:[cols]
		});
		function outInventory(data){
			layer.open({
				type:1,
				title:'出库',
				btn:['确定','取消'],
				area:'auto',
				btnAlign:'c',
				offset:'50px',
				content: OUT_INVENTORY_TPL,
				success:function(layerElem,layerIndex){
					laydate.render({ elem:'#outTime', value:new Date(), type:'datetime', });
					form.on('submit(sureOutInventory)',function(obj){
						if(obj.field.number>data.surplusNumber){
							$('#outInventoryWin input[name="number"]').addClass('layui-form-danger');
							$('#outInventoryWin input[name="number"]').focus();
							return myutil.emsg('出库数量不能大于剩余数量',{ anim:6, });
						}
						obj.field.id = data.id;
						myutil.saveAjax({
							url:'/temporaryPack/mantissaLiquidationToUnderGoods',
							data: obj.field,
							success:function(){
								table.reload('tableData');
								layer.close(layerIndex);
							}
						})
					})
				},
				yes:function(){
					$('#sureOutInventory').click();
				},
			})
			
		}
		form.on('submit(searchBtn)',function(obj){
			table.reload('tableData',{
				where: obj.field,
				page:{ curr:1 },
			})
		})
	};
	exports('numberList',numberList);
})