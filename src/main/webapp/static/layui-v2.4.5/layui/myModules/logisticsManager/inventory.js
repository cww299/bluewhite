/**2019/12/13
 * author: 299
 * 后勤库存信息模块 inventory
 * type: 1、办公用品  2、机械配件  3、食材库存  4、小卖部
 * elem: '', 容器
 * inventory.render({
 * 		type: 1,
 * })
 */
layui.extend({
	goodFlag: 'layui/myModules/logisticsManager/goodFlag',
	soundTips: 'layui/myModules/util/soundTips',
}).define(['jquery','layer','form','laytpl','laydate','mytable','table','goodFlag','soundTips'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		laytpl = layui.laytpl,
		soundTips = layui.soundTips,
		table = layui.table,
		mytable = layui.mytable,
		myutil = layui.myutil;
	var STYLE = `
	<style>
		.scanInput{
  			height: 50px;
  			margin-bottom: 10px;
	  	}
	  	.scanInput.layui-input:focus{
	    	border-color: #12aef5!important;
		    border-width: 2px;
		    box-shadow: 0px 0px 10px #12aef5!important;
		}
		.outBill{
			padding: 10px 0;
			color: gray;
		}
		.outBill p{
			font-size: 17px;
			padding: 5px 10px;
		}
		.outBill ul li{
		
		}
		.outBill ul li span{
			width: 31%;
			display: inline-block;
		    text-align: center;
		    font-size: 16px;
		    margin: 7px 0;
		}
	</style>
	`;
	$('head').append(STYLE);
	
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
		type: 1,  //默认办公用品
	};
	
	inventory.render = function(opt){
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
		`+(function() {
			if (inventory.type==3) {
				return `<td>材料:</td><td><select id="mealConsumptionSelect" name="singleMealConsumptionId"></select></td>`;
			}
			return '';
		})() +`
					<td></td>
					<td><button type="button" class="layui-btn layui-btn-" lay-submit lay-filter="search">搜索</button></td>
				</tr>
			</table>
			<table id="tableData" lay-filter="tableData"></table>
		`;
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
		var orgNameSelectHtml = '<option value="">请选择部门</option>', 
			userSelectHtml = '<option value="">请选择领取人</option>',
			allCustomerSelectHtml = '<option value="">请选择</option>';
		var unitData = myutil.getDataSync({ url: myutil.config.ctx+'/basedata/list?type=officeUnit' });
		var allSingleMealConsumption =  [];
		if(inventory.type==3) {
			allSingleMealConsumption = 
				myutil.getDataSync({ url: myutil.config.ctx+'/basedata/list?type=singleMealConsumption' });
			
			var html = '<option value="">请选择</option>';
			(allSingleMealConsumption || []).forEach(sign => {
				html += "<option value='"+sign.id+"'>"+sign.name+"</option>"
			})
			$('#mealConsumptionSelect').append(html);
			form.render();
		}
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/personnel/getOfficeSupplies?type='+inventory.type,
			curd:{
				addTemp:{
					qcCode: '',
					type: inventory.type,
					createdAt:myutil.getSubDay(0,'yyyy-MM-dd')+' 00:00:00',
					location:'',name:'',price:'',salePrice:'',inventoryNumber:'',libraryValue:'',
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
				otherBtn: function(obj) {
					if (obj.event === 'sacanOut') {
						outScan();
					} else if (obj.event=='sacanIn'){
						inScan();
					} else if (obj.event === 'printQcCode'){
						var check = table.checkStatus('tableData').data;
						if(check.length==0)
							return myutil.emsg("请选择打印数据");
						check = check.filter(item => { return item.qcCode })
						if(check.length==0)
							return myutil.emsg("请选择有条形码的数据");
						layui.goodFlag.print(check)
					}
				}
			},
			toolbar: [
				(function(){
					if(inventory.type==1 || inventory.type==4){
						return '<span class="layui-btn layui-btn-primary layui-btn-sm" lay-event="sacanOut">扫码出库</span>'+
						'<span class="layui-btn layui-btn-warm layui-btn-sm" lay-event="sacanIn">扫码入库</span>'+
						'<span class="layui-btn layui-btn-normal layui-btn-sm" lay-event="printQcCode">打印条形码</span>';
					}
					return "";
				})(),
			].join(),
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
					];
					if(inventory.type==1 || inventory.type==4){
						cols.push(
							{ field: "qcCode", title: "条形码", edit: true }
						)
					}
				    var c = [
						{ field: "location", title: "仓位", filter:true, edit: true, },
						{ field: "name", title: "物品名", edit: true, },
						{ field: "unit_id", title: "单位", type:'select', select:{data:unitData,}, },
			        ];
				    cols = cols.concat(c);
					if(inventory.type===3){
						cols.push({
							field: "singleMealConsumption_id", title: "材料分类",   type:'select',
							select:{ data: allSingleMealConsumption, },
						})
					}
					cols.push({ field: "price", title: "单价", edit: true, });
					if(inventory.type===4){
						cols.push({ field: "salePrice", title: "售价", edit: true, })
					}
					c = [
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
		
		function inScan(){
			layer.open({
				type:1,
				title:'扫码入库',
				area:['90%','90%'],
				content:[
					'<div style="padding:10px;">',
						'<input class="layui-input scanInput" placeholder="扫码枪扫码时，请将光标聚集在输入框内">',
						'<table>',
							'<tr class="layui-form">',
								'<td><input class="layui-input" id="inName" placeholder="入库人"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><span class="layui-btn layui-btn-primary" id="oneKeyIn">一键入库</span></td>',
							'</tr>',
						'</table>',
						'<table id="inTable" lay-filter="inTable"></table>',
					'</div>',
				].join(' '),
				success:function(){
					form.render();
					var outCols = [
						{ type:'checkbox', },
						{ field:'name', title:'物品名',  },
						{ field:'location', title:'仓位', },
						{ field:'price', title:'单价', },
						{ field:'inNumber', title:'入库数量', edit:'number', style:'background:#d8ff83' },
					];
					var inTableData = [];
					mytable.renderNoPage({
						elem:'#inTable',
						data: inTableData,
						cols: [outCols],
						limit:9999,
					})
					$('#oneKeyIn').click(function(){
						var check = table.checkStatus('inTable').data;
						if(check.length == 0)
							return myutil.emsg('请勾选入库数据');
						var allMoney = 0;
						for(var i in check){
							if(check[i].inNumber<=0)
								return myutil.emsg("请正确填写入库数量");
							allMoney += check[i].inNumber*check[i].price
						}
						var inName = $('#inName').val();
						if(!inName)
							return myutil.emsg("请填写入库人！");
						var tplData = {
							inName: inName,
							outData: check,
							allMoney: allMoney,
						};
						layer.open({
							type: 1,
							title: '入库详细',
							area: ['700px','700px'],
							content: laytpl([
							'<div class="printDiv">',
							'<style>',
								'.outBill ul li span{',
									'width: 19%;',
									'display: inline-block;',
								    'text-align: center;',
								    'font-size: 16px;',
								    'margin: 7px 0;',
								'}',
							'</style>',
							'<div class="outBill">',
								'<h3 style="text-align:center;">蓝白办公用品入库单</h3>',
								'<hr><hr>',
								'<p>入库日期：   {{   layui.myutil.getSubDay(0,"yyyy-MM-dd hh:mm:ss") }}</p>',
								'<hr>',
								'<ul>',
									'<li><span>商品名</span><span>库位</span><span>单价</span><span>入库数量</span><span>价值</span></li>',
								'{{# layui.each(d.outData,function(index,item){ }}',
									'<li>',
							    		'<span>{{ item.name }}</span>',
							    		'<span>{{ item.location }}</span>',
							    		'<span>{{ item.price }}</span>',
							    		'<span>{{ item.inNumber }}</span>',
							    		'<span>{{ (item.price*item.inNumber).toFixed(2) }}</span>',
									'</li>',
								'{{# }) }}',
								'</ul>',
								'<hr>',
								'<p style="text-align:right;">总价值：   {{  d.allMoney.toFixed(2) }}</p>',
								'<p style="text-align:right;">入库人：   {{  d.inName }}</p>',
							'</div>',
							'</div>',
							].join(' ')).render(tplData),
							btn: ['打印','确认入库','取消'],
							yes: function(layerIndex,layero){
								layui.goodFlag.printpage($(layero).find('.printDiv').html());
							},
							btn2: function(layerIndex){
								var inData = [];
								for(var i in check)
									inData.push({
										id: check[i].id,
										number: check[i].inNumber
									})
								myutil.saveAjax({
									url: '/personnel/addInventoryDetailMoresIn',
									data: {
										inName: inName,
										inList: JSON.stringify(inData),
									},
									success: function(){
										layer.close(layerIndex)
										table.reload('tableData')
										inTableData = []
										table.reload('inTable',{
											data: inTableData,
										})
										soundTips.inSuccess();
										form.render();
									}
								})
								return false
							}
						})
					})
					$('.scanInput').focus();
					$('.scanInput').unbind().on('keypress', function(e) {
						if (e.which == 13) {
							var val = $(this).val();
							if(!val)
								return;
							myutil.getDataSync({
								url: myutil.config.ctx+'/personnel/getOfficeSupplies',
								data: {
									type: inventory.type,
									qcCode: val,
								},
								success:function(d){
									if(d!=null && d.length>0){
										for(var i in d){
											d[i].inNumber = 1;
											var has = false;
											for(var j in inTableData){
												if(inTableData[j].id == d[i].id){
													has = true;
													inTableData[j].inNumber++;
												}
											}
											if(!has)
												inTableData.push(d[i]);
										}
										table.reload('inTable',{
											data: inTableData
										});
										soundTips.scanSuccess();
									}else{
										layer.msg("找不到商品："+val)
										soundTips.noFind();
									}
								},
								error: function(){
									soundTips.error();
								}
							})
							$(this).val('');
		                }
			        });
				}
			})
		}
		function outScan(){
			layer.open({
				type:1,
				title:'扫码出库',
				area:['90%','90%'],
				content:[
					'<div style="padding:10px;">',
						'<input class="layui-input scanInput" placeholder="扫码枪扫码时，请将光标聚集在输入框内">',
						'<table>',
							'<tr class="layui-form">',
								'<td><select id="outOrgName" lay-search>'+orgNameSelectHtml+'</select></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><select id="outPeople" lay-search>'+userSelectHtml+'</select></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><input class="layui-input" id="remarkInput" placeholder="备注"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><input class="layui-input" id="outName" placeholder="出库人"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><span class="layui-btn layui-btn-primary" id="oneKeyOut">一键出库</span></td>',
							'</tr>',
						'</table>',
						'<table id="outTable" lay-filter="outTable"></table>',
					'</div>',
				].join(' '),
				success:function(){
					form.render();
					var outCols = [
						{ type:'checkbox', },
						{ field:'name', title:'物品名',  },
						{ field:'location', title:'仓位', },
						{ field:'inventoryNumber', title:'库存数量', },
						{ field:'price', title:'单价', },
						{ field:'outNumber', title:'出库数量', edit:'number', style:'background:#d8ff83' },
					];
					var outTableData = [];
					mytable.renderNoPage({
						elem:'#outTable',
						data: outTableData,
						cols: [outCols],
						limit:9999,
					})
					$('#oneKeyOut').click(function(){
						var check = table.checkStatus('outTable').data;
						if(check.length == 0)
							return myutil.emsg('请勾选出库数据');
						var allMoney = 0;
						for(var i in check){
							if(check[i].outNumber<=0)
								return myutil.emsg("请正确填写出库数量");
							if(check[i].outNumber>check[i].inventoryNumber){
								return myutil.emsg("商品："+check[i].name+" 库位："+check[i].inventoryNumber+" 库存量不足，无法出库");
							}
							allMoney += check[i].outNumber*check[i].price
						}
						var orgId = $('#outOrgName').val(),
						userId = $('#outPeople').val(),
						remark = $('#remarkInput').val(),
						outName = $('#outName').val(),
						orgName = '---', userName = '---';
						if(!outName)
							return myutil.emsg("请填写出库人！");
						if(orgId)
							orgName = $('#outOrgName').find('option[value='+orgId+']').html();
						if(userId)
							userName = $('#outPeople').find('option[value='+userId+']').html();
						var tplData = {
							outName: outName,
							orgName: orgName,
							userName: userName,
							remark: remark || '---',
							outData: check,
							allMoney: allMoney,
						};
						layer.open({
							type: 1,
							title: '出库详细',
							area: ['700px','700px'],
							content: laytpl([
							'<div class="printDiv">',
							'<style>',
								'.outBill ul li span{',
									'width: 19%;',
									'display: inline-block;',
								    'text-align: center;',
								    'font-size: 16px;',
								    'margin: 7px 0;',
								'}',
							'</style>',
							'<div class="outBill">',
								'<h3 style="text-align:center;">蓝白办公用品出库单</h3>',
								'<hr><hr>',
								'<p>领取日期：   {{   layui.myutil.getSubDay(0,"yyyy-MM-dd hh:mm:ss") }}</p>',
								'<p>领取部门：   {{   d.orgName }}</p>',
								'<p>领取人：   {{  d.userName }}</p>',
								'<p>备注：   {{  d.remark }}</p>',
								'<hr>',
								'<ul>',
									'<li><span>商品名</span><span>库位</span><span>单价</span><span>出库数量</span><span>价值</span></li>',
								'{{# layui.each(d.outData,function(index,item){ }}',
									'<li>',
							    		'<span>{{ item.name }}</span>',
							    		'<span>{{ item.location }}</span>',
							    		'<span>{{ item.price }}</span>',
							    		'<span>{{ item.outNumber }}</span>',
							    		'<span>{{ (item.price*item.outNumber).toFixed(2) }}</span>',
									'</li>',
								'{{# }) }}',
								'</ul>',
								'<hr>',
								'<p style="text-align:right;">总价值：   {{  d.allMoney.toFixed(2) }}</p>',
								'<p style="text-align:right;">出库人：   {{  d.outName }}</p>',
							'</div>',
							'</div>',
							].join(' ')).render(tplData),
							btn: ['打印','确认出库','取消'],
							yes: function(layerIndex,layero){
								layui.goodFlag.printpage($(layero).find('.printDiv').html());
							},
							btn2: function(layerIndex){
								var outData = [];
								for(var i in check)
									outData.push({
										id: check[i].id,
										number: check[i].outNumber
									})
								myutil.saveAjax({
									url: '/personnel/addInventoryDetailMores',
									data: {
										orgId: orgId,
										userId: userId,
										remark: remark,
										operator: outName,
										outList: JSON.stringify(outData),
									},
									success: function(){
										layer.close(layerIndex)
										table.reload('tableData')
										outTableData = []
										table.reload('outTable',{
											data: outTableData,
										})
										$('#outPeople').val('')
										$('#remarkInput').val('')
										soundTips.outSuccess();
										form.render();
									}
								})
								return false
							}
						})
					})
					$('.scanInput').focus();
					$('.scanInput').unbind().on('keypress', function(e) {
						if (e.which == 13) {
							var val = $(this).val();
							if(!val)
								return;
							myutil.getDataSync({
								url: myutil.config.ctx+'/personnel/getOfficeSupplies',
								data: {
									type: inventory.type,
									qcCode: val,
								},
								success:function(d){
									if(d!=null && d.length>0){
										for(var i in d){
											d[i].outNumber = 1;
											var has = false;
											for(var j in outTableData){
												if(outTableData[j].id == d[i].id){
													has = true;
													outTableData[j].outNumber++;
												}
											}
											if(!has)
												outTableData.push(d[i]);
										}
										table.reload('outTable',{
											data: outTableData
										});
										soundTips.scanSuccess();
									}else{
										layer.msg("找不到商品："+val)
										soundTips.noFind();
									}
								},
								error: function(){
									soundTips.error();
								}
							})
							$(this).val('');
		                }
			        });
				}
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