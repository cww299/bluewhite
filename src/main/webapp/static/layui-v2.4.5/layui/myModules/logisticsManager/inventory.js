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
				<td>仓位:</td>
				<td><input type="text" name="location" class="layui-input"></td>
				<td>时间:</td>
				<td><input type="text" name="" class="layui-input" id="searchTime"></td>
				<td>库存数量:</td>
				<td><input type="number" name="inventoryNumber" class="layui-input"></td>
				<td></td>
				<td><button type="button" class="layui-btn layui-btn-" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	`;
	var OUT_TPL = `
	<div style="text-align:center;">
		<div class="layui-form layui-form-pane" style="padding:10px;">
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">日期</label>
		    <div class="layui-input-block">
		      <input type="text" name="time" class="layui-input" id="addOutTime">
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">数量</label>
		    <div class="layui-input-block">
		      <input type="number" name="number" lay-verify="number" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">部门</label>
		    <div class="layui-input-block">
		      <select  name="orgNameId" id="addOrg" lay-search></select>
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">领取人</label>
		    <div class="layui-input-block">
		      <select  name="userId" id="addUser" lay-search></select>
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">用餐类型</label>
		    <div class="layui-input-block">
		      <select  name="mealType" id="mealType">
						<option value="1">早餐</option>
						<option value="2">午餐</option>
						<option value="3">晚餐</option>
						<option value="4">夜宵</option>
						<option value="5">早午晚餐</option>
						<option value="6">午晚餐</option>
					</select>
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">出库类型</label>
		    <div class="layui-input-block">
		      <select name="status">
						<option value="1">正常出库</option>
						<option value="2">平账出库</option>
					</select>
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">备注</label>
		    <div class="layui-input-block">
		      <input type="text" name="remark" class="layui-input">
			  <input type="hidden" name="flag" value="0">
			  <span lay-submit lay-filter="addOutBtn" id="addOutBtn" style="display:none;">1</span>
		    </div>
		  </div>
		</div>
	</div>
	`
	var inventory = {
		type: 1,  //默认食材
	};
	
	inventory.render = function(opt){
		var INPUT_TPL = `
			<div style="text-align:center;">
				<div class="layui-form layui-form-pane" style="padding:10px;">
				  <div class="layui-form-item" pane>
				    <label class="layui-form-label">日期</label>
				    <div class="layui-input-block">
				      <input type="text" name="time" class="layui-input" id="addInputTime">
				    </div>
				  </div>
				  <div class="layui-form-item" pane>
				    <label class="layui-form-label">数量</label>
				    <div class="layui-input-block">
				      <input type="number" name="number" lay-verify="number" class="layui-input" id="inputTime">
				    </div>
				  </div>
				  <div class="layui-form-item" pane style="display:none;">
				    <label class="layui-form-label">供应商</label>
				    <div class="layui-input-block">
				      <select  name="customerId" id="supplierIdSelcte" lay-search
				      	lay-url="`+myutil.config.ctx+`/ledger/getCustomer?customerTypeId=455"
				      	 ></select>
				    </div>
				  </div>
				  <div class="layui-form-item" pane>
				    <label class="layui-form-label">备注</label>
				    <div class="layui-input-block">
				      <input type="text" name="remark" class="layui-input">
				    </div>
				  </div>
				  <div class="layui-form-item" pane>
				    <label class="layui-form-label">入库类型</label>
				    <div class="layui-input-block">
				      <select name="status">
								<option value="1">正常入库</option>
								<option value="2">平账入库</option>
							</select>
				    </div>
				  </div>
				  <p style="display:none;">
				  	<input type="hidden" name="flag" value="1">
					<span lay-submit lay-filter="addInputBtn" id="addInputBtn" style="display:none;">1</span>
				  </p>
				</div>
			</div>
			`;
		layui.tablePlug.smartReload.enable(true);
		$(opt.elem || '#app').html(TPL_MAIN);
		laydate.render({ elem:'#searchTime', range:'~', })
		form.render();
		var orgNameSelectHtml = '<option value="">请选择</option>', userSelectHtml = '<option value="">请选择</option>',
			allCustomerSelectHtml = '<option value="">请选择</option>';
		var unitData = myutil.getDataSync({ url: myutil.config.ctx+'/basedata/list?type=officeUnit' });
		var allSingleMealConsumption =  [];
		if(inventory.type==3)
			allSingleMealConsumption =myutil.getDataSync({ url: myutil.config.ctx+'/basedata/list?type=singleMealConsumption' });
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/personnel/getOfficeSupplies?type='+inventory.type,
			curd:{
				addTemp:{
					type: inventory.type,
					createdAt:myutil.getSubDay(0,'yyyy-MM-dd')+' 00:00:00',
					location:'',name:'',price:'',inventoryNumber:'',libraryValue:'',
					unitId: unitData[0].id,
					singleMealConsumptionId: allSingleMealConsumption[0]?allSingleMealConsumption[0].id : "",
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
			ifNull:'',
			limits:[10,50,100,200,500,1000],
			totalRow:['libraryValue'],
			autoUpdate:{
				saveUrl:'/personnel/addOfficeSupplies',
				deleUrl:'/product/deleteOfficeSupplies',
				field:{unit_id:'unitId',singleMealConsumption_id:'singleMealConsumptionId', },
			},
			cols:[
				(function(){
					var cols = [
					{ type: 'checkbox', align: 'center', fixed: 'left',},
					{ field: "createdAt", title: "时间", type:'date',},
					{ field: "location", title: "仓位", filter:true, edit: true, },
					{ field: "name", title: "物品名", edit: true, },
					{ field: "unit_id", title: "单位", type:'select', select:{data:unitData,}, },
			       ];
					if(inventory.type===3){
						cols.push({
							field: "singleMealConsumption_id", title: "材料分类",   type:'select',
							select:{ data: allSingleMealConsumption, },
						})
					}
					var c = [{ field: "price", title: "单价", edit: true, },
					{ field: "inventoryNumber", title: "库存数量", edit: false, },
					{ field: "libraryValue", title: "库值", edit: false, },
					{ field: "", title: "操作", edit: false, templet:getTpl(), },];
					return cols.concat(c);
				})(),
			],
			done:function(){
				$('.inputBtn').unbind().on('click',function(obj){
					var index = $(obj.target).closest('tr').data('index');
					var trData = table.cache['tableData'][index];
					var win = layer.open({
						type:1,
						area:['30%',inventory.type==3?'400px':'350px'],
						offset:'100px',
						btnAlign:'c',
						btn:['确定',"取消"],
						title: trData.name,
						content: INPUT_TPL,
						success:function(){
							if(inventory.type===3){
								$('#supplierIdSelcte').closest('div.layui-form-item').show();
								$('#supplierIdSelcte').html(allCustomerSelectHtml);
							}
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
						offset:'100px',
						area:['30%','450px'],
						btn:['确定',"取消"],
						btnAlign:'c',
						title: trData.name,
						content: OUT_TPL,
						success:function(){
							laydate.render({ elem:'#addOutTime', value:myutil.getSubDay(0,'yyyy-MM-dd'), });
							if(inventory.type===3){
								$('#addUser').closest('div.layui-form-item').hide();
							}
							else{
								$('#mealType').closest('div.layui-form-item').hide();
								$('#addUser').html(userSelectHtml);
							}
							$('#addOrg').html(orgNameSelectHtml);
							form.on('submit(addOutBtn)',function(obj){
								obj.field.officeSuppliesId = trData.id;
								obj.field.time = obj.field.time+' 00:00:00';
								/*if(inventory.type===3)
									obj.field.mealType=3;*/
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
			allCustomerSelectHtml = '';
			myutil.getData({
				url: myutil.config.ctx+'/ledger/getCustomer?customerTypeId=455',
				success:function(d){ 
					for(var i=0,len=d.length;i<len;i++){
						allCustomerSelectHtml += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
					}
				},
			})
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
				url: myutil.config.ctx+'/system/user/findUserList?foreigns=0&isAdmin=false&quit=0',
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