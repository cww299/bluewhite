/**2020/01/10 author:299
 * 位数记录列表模块  numberList
 * type:1  尾数找回，进行入库
 * type:2  尾数丢失，清报财务
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
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/temporaryPack/findPagesMantissaLiquidation?type='+numberList.type,
			toolbar:[
				'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="edit">修改</span>'
			].join(' '),
			curd:{
				btn:[4],
				otherBtn:function(obj){
					if(obj.event=='edit'){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length!=1)
							return myutil.emsg('只能选择一天数据进行编辑！',{ anim:6, });
						clearNumberOrder.update(check[0]);
					}
				}
			},
			autoUpdate:{
				deleUrl:'/temporaryPack/deleteMantissaLiquidation',
			},
			cols:[[
				{ type:'checkbox', },
				{ title:'入库编号',field:'mantissaNumber', width:165, },
				{ title:'入库时间',field:'time', width:165, },
				{ title:'批次号',field:'underGoods_bacthNumber',  width:165, },
				{ title:'产品名',field:'underGoods_product_name', },
				{ title:'数量',field:'number', width:120, },
				{ title:'备注',field:'remarks', },
			]]
		});
		form.on('submit(searchBtn)',function(obj){
			table.reload('tableData',{
				where: obj.field,
				page:{ curr:1 },
			})
		})
	};
	exports('numberList',numberList);
})