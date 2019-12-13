/**2019/12/13
 * author: 299
 * 后勤库存信息模块 inventory
 * type: 1、办公用品  2、机械配件  3、食材库存
 * elem: '', 容器
 * inventory.render({
 * 		type: 1,
 * })
 */
layui.extend({
}).define(['jquery','layer','form','laytpl','laydate','mytable','table'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		laytpl = layui.laytpl,
		table = layui.table,
		mytable = layui.mytable,
		myutil = layui.myutil;
	
	var TPL_MAIN = `
		<table class="layui-form searchTable">
			<tr>
				<td>物品名:</td>
				<td><input type="text" name="name" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>仓位:</td>
				<td><input type="text" name="location" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>时间:</td>
				<td><input type="text" name="" class="layui-input" id="searchTime"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	`;
	var INPUT_TPL = `
	<div style="text-align:center;">
		<table class="layui-form" style="margin:30px auto;">
			<tr>
				<td>日期：</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="time" class="layui-input" id="addInputTime"></td>
			</tr>
			<tr>
				<td>数量：</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="number" lay-verify="number" class="layui-input" id="inputTime"></td>
			</tr>
			<tr>
				<td>备注</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="remark" class="layui-input">
					<input type="hidden" name="flag" value="1">
					<span lay-submit lay-filter="addInputBtn" id="addInputBtn" style="display:none;">1</span></td>
			</tr>
		</table>
	</div>
	`;
	var OUT_TPL = `
	<div style="text-align:center;">
		<table class="layui-form" style="margin:30px auto;">
			<tr>
				<td>日期：</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="time" class="layui-input" id="addOutTime"></td>
			</tr>
			<tr>
				<td>数量：</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="number" lay-verify="number" class="layui-input"></td>
			</tr>
			<tr>
				<td>部门：</td>
				<td>&nbsp;&nbsp;</td>
				<td><select  name="orgNameId" id="addOrg" lay-search></select></td>
			</tr>
			<tr>
				<td>领取人：</td>
				<td>&nbsp;&nbsp;</td>
				<td><select  name="userId" id="addUser" lay-search></select></td>
			</tr>
			<tr>
				<td>用餐类型：</td>
				<td>&nbsp;&nbsp;</td>
				<td><select  name="mealType">
						<option value="1">早餐</option>
						<option value="2">午餐</option>
						<option value="3">晚餐</option>
						<option value="4">夜宵</option>
						<option value="5">早午晚餐</option>
						<option value="6">午晚餐</option>
					</select></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="remark" class="layui-input">
					<input type="hidden" name="flag" value="0">
					<span lay-submit lay-filter="addOutBtn" id="addOutBtn" style="display:none;">1</span></td>
			</tr>
		</table>
	</div>
	`
	var inventory = {
			type: 1,  //默认食材
	};
	
	inventory.render = function(opt){
		$(opt.elem || '#app').html(TPL_MAIN);
		laydate.render({ elem:'#searchTime', range:'~', })
		form.render();
		var orgNameSelectHtml = '<option value="">请选择</option>', userSelectHtml = '<option value="">请选择</option>';
		var unitData = myutil.getDataSync({ url: myutil.config.ctx+'/basedata/list?type=officeUnit' });
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/personnel/getOfficeSupplies?type='+inventory.type,
			curd:{
				addTemp:{
					type: inventory.type,
					createdAt:myutil.getSubDay(0,'yyyy-MM-dd')+' 00:00:00',
					location:'',name:'',price:'',inventoryNumber:'',libraryValue:'',
				},
				addTempAfter:function(trElem){
					var timeElem = $(trElem).find('td[data-field="createdAt"]').find('div')[0];
					laydate.render({
						elem: timeElem,
						done: function(val){
							var index = $(this.elem).closest('tr').data('index');
							table.cache['tableData'][index]['createdAt'] = val+' 00:00:00';
						}
					}) 
				}, 
			},
			size:'lg',
			limits:[10,50,100,200,500,1000],
			totalRow:['libraryValue'],
			autoUpdate:{
				saveUrl:'/personnel/addOfficeSupplies',
				deleUrl:'/product/deleteOfficeSupplies',
				field:{unit_id:'unitId', },
			},
			cols:[[
					{ type: 'checkbox', align: 'center', fixed: 'left',},
					{ field: "createdAt", title: "时间", type:'date',},
					{ field: "location", title: "仓位", filter:true, edit: true, },
					{ field: "name", title: "物品名", edit: true, },
					{ field: "unit_id", title: "单位", type:'select', select:{data:unitData,}, },
					{ field: "price", title: "单价", edit: true, },
					{ field: "inventoryNumber", title: "库存数量", edit: false, },
					{ field: "libraryValue", title: "库值", edit: false, },
					{ field: "", title: "操作", edit: false, templet:getTpl(), },
			       ]],
			done:function(){
				$('.inputBtn').unbind().on('click',function(obj){
					var index = $(obj.target).closest('tr').data('index');
					var trData = table.cache['tableData'][index];
					var win = layer.open({
						type:1,
						area:['30%','40%'],
						btnAlign:'c',
						btn:['确定',"取消"],
						title: trData.name,
						content: INPUT_TPL,
						success:function(){
							laydate.render({ elem:'#addInputTime',value:myutil.getSubDay(0,'yyyy-MM-dd'), })
							form.on('submit(addInputBtn)',function(obj){
								obj.field.officeSuppliesId = trData.id;
								obj.field.time = obj.field.time+' 00:00:00';
								myutil.saveAjax({
									url:'/personnel/addInventoryDetail',
									data: obj.field,
									success:function(){
										layer.close(win);
										table.reload('tableData');
									}
								})
							})
							form.render();
						},
						yes:function(){
							$('#addInputBtn').click();
						}
					})
				})
				$('.outBtn').unbind().on('click',function(obj){
					var index = $(obj.target).closest('tr').data('index');
					var trData = table.cache['tableData'][index];
					var win = layer.open({
						type:1,
						area:['30%','50%'],
						btn:['确定',"取消"],
						btnAlign:'c',
						title: trData.name,
						content: OUT_TPL,
						success:function(){
							laydate.render({ elem:'#addOutTime', value:myutil.getSubDay(0,'yyyy-MM-dd'), });
							if(inventory.type===3){
								$('#addUser').closest('tr').hide();
							}
							else{
								$('#mealType').closest('tr').hide();
								$('#addUser').html(userSelectHtml);
							}
							$('#addOrg').html(orgNameSelectHtml);
							form.on('submit(addOutBtn)',function(obj){
								obj.field.officeSuppliesId = trData.id;
								obj.field.time = obj.field.time+' 00:00:00';
								if(inventory.type===3)
									obj.field.mealType=3;
								myutil.saveAjax({
									url:'/personnel/addInventoryDetail',
									data: obj.field,
									success:function(){
										layer.close(win);
										table.reload('tableData');
									}
								})
							})
							form.render();
						},
						yes:function(){
							$('#addOutBtn').click();
						}
					})
				})
			}
		})
		if(inventory.type===3){
			orgNameSelectHtml += '<option value="1">总经办</option>';
			userSelectHtml = '';
		}else{
			myutil.getData({
				url: myutil.config.ctx+'/basedata/list?type=orgName',
				success:function(d){ 
					for(var i=0,len=d.length;i<len;i++){
						orgNameSelectHtml += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
					}
				},
			})
			myutil.getData({
				url: myutil.config.ctx+'/system/user/findUserList?foreigns=0&isAdmin=false',
				success:function(d){
					for(var i=0,len=d.length;i<len;i++){
						userSelectHtml += '<option value="'+d[i].id+'">'+d[i].userName+'</option>';
					}
				},
			})
		}
		
		form.on('submit(search)',function(obj){
			var t = $('#searchTime').val();
			if(t!=''){
				t = t.split(' ~ ');
				t[0] = t[0].trim()+' 00:00:00';
				t[1] =  t[1].trim()+' 00:00:00';
			}else
				t = ['',''];
			obj.field.orderTimeBegin = t[0];
			obj.field.orderTimeEnd =  t[1];
			
			table.reload('tableData',{
				where:obj.field,
				page:{curr:1},
			})
		})
		function getTpl(){
			return function(d){
				if(d.id){
					return [
					        '<span class="layui-btn layui-btn-sm inputBtn">入库</span>',
					        '<span class="layui-btn layui-btn-sm outBtn">出库</span>',
					        ].join('');
				}else
					return '';
			}
		}
	}

	exports('inventory',inventory);
})