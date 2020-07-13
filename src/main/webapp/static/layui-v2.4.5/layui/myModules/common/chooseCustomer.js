/**选择客户模板
 * @author 299
 */

layui.extend({
}).define(['jquery','layer','form','laytpl','laydate','mytable','table'],function(exports){
	"use strict";
	var $ = layui.jquery,
	form = layui.form,
	laydate = layui.laydate,
	layer = layui.layer,
	laytpl = layui.laytpl,
	mytable = layui.mytable,
	table = layui.table,
	myutil = layui.myutil;
	
	var tableId = 'chooseCustomerTable';
	
	var TPL_MAIN = `
	<div style="padding: 10px;">
		<table class="searchTable layui-form">
			<td></td>
			<td><input type="text" name="name" class="layui-input" placeholder="客户名称"></td>
			<td></td>
			<td><input type="text" name="phone" class="layui-input" placeholder="手机号码"></td>
			<td></td>
			<td><input type="text" name="userName" class="layui-input" placeholder="所属业务员"></td>
			<td></td>
			<td><select name="customerTypeId" id="searchCustomerTypeId">
					<option value="">客户类型</option></select></td>
			<td><span class="layui-btn" lay-submit lay-filter="searchCustomer">
				<i class="layui-icon layui-icon-search"></i>搜索</span></td>
		</table>
		<table id="`+tableId+`" lay-filter="`+tableId+`"></table>
	</div>
	`;
	const allCustomerType = [
		{ id: 454, name:'税收单位', code: 'ssdw', },
		{ id: 455, name:'菜商', code: 'ssdw', },
		{ id: 456, name:'供应商', code: 'gys', },
		{ id: 457, name:'电商', code: 'ds', },
		{ id: 458, name:'商超', code: 'sc', },
		{ id: 459, name:'线下', code: 'xx', },
		{ id: 460, name:'加工点', code: 'jgd', },
	];
	var chooseCustomer = {
		model: 'one',
		type: ['all'],
	}
	
	chooseCustomer.choose = function(opt = {}){
		opt = $.extend({}, chooseCustomer, opt)
		// 过滤出客户类型
		var allType = [];
		if(opt.type.indexOf('all') > -1)
			allType = allCustomerType
		else {
			allType = allCustomerType.filter(type => opt.type.indexOf(type.code) > -1)
		}
		return new Promise(function(resolve, reject){
			layer.open({
				type: 1,
				title: '选择客户',
				area: ['1100px', '700px'],
				content: TPL_MAIN,
				shadeClose: true,
				success: function(layerElem, layerIndex){
					var html = ""
					allType.forEach(type => html += `<option value="${type.id}">${type.name}</option>`)
					$('#searchCustomerTypeId').html(html)
					form.render();
					mytable.render({
						elem:'#'+tableId,
						curd: { btn: [], },
						height: '550px',
						url: myutil.config.ctx+'/ledger/customerPage',
						where: {
							customerTypeId: allType[0].id,
						},
						toolbar: opt.model == 'one' ?
								'<span class="layui-badge">提示：双击进行选择</span>':
							'<span class="layui-btn layui-btn-sm layui-btn-primary" lay-event="choose">确定选择</span>',
						ifNull:'--',
						cols:[[
							{ type:'checkbox', },
							{ title:'名称', field:'name', },
							{ title:'手机号码', field:'phone', },
							{ title:'账户', field:'account', },
							{ title:'业务员', field:'user_userName', },
						]]
					})
					if(opt.model === 'one') {
						table.on('row('+ tableId +')', function(obj){
							if(opt.verify) {
								if(!opt.verify(obj.data))
									return;
							}
							resolve(obj.data)
							layer.close(layerIndex)
						});
					} else {
						table.on('toolbar('+ tableId +')',function(obj){
							if(obj.event=="choose") {
								resolve(table.checkStatus(tableId).data)
							}
						})
					}
					form.on('submit(searchCustomer)',function(obj){
						table.reload(tableId,{
							where: obj.field,
							page: { curr: 1 }
						})
					})
				}
			})
		})
	}
	
	exports('chooseCustomer', chooseCustomer);
})