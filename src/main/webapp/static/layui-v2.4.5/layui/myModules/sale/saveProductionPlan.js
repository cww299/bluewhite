/* 2019/12/03
 * author: 299
 * 新增、修改退货单模板
 * 需要在本模块前引入mytable,并设置ctx后调用init()
 * saveProductionPlan.add({ success:function(){ 成功函数的回调 }  }) 绑定新增按钮
 * saveProductionPlan.update({ data:{修改前的数据、回显},   })
 */
layui.extend({
	//formSelects : 'formSelect/formSelects-v4'
	//mytable: 'layui/myModules/mytable'
}).define(['jquery','layer','form','laytpl','laydate','laydate','mytable'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		laytpl = layui.laytpl,
		table = layui.table,
		mytable = layui.mytable,
		myutil = layui.myutil;
	
	var TPL_MAIN = 
		`<div style="padding:10px;">
			<table class="layui-form">
				<tr>
					<td><b class="red">*</b>商品选择：</td>
					<td><input readonly placeholder="点击选择商品" class="layui-input" id="chooseProductInput" 
							lay-verify="required" value="{{ d.product?d.product.name : ""}}">
						<input type="hidden" name="productId" id="productIdHidden" value="{{ d.product?d.product.id:"" }}">
						<input type="hidden" name="id" value="{{ d.id || "" }}">
						</td>
					<td>&nbsp;&nbsp;</td>
					<td><b class="red">*</b>订单类型：</td>
					<td style="width:100px;"><select id="orderTypeSelect" name="orderTypeId" data-value="{{d.orderType?d.orderType.id:"" }}" 
							lay-verify="required">
							<option value="">请选择</option></select></td>
					<td>&nbsp;&nbsp;</td>
					<td><b class="red">*</b>订单时间：</td>
					<td><input type="text" name="orderDate" id="orderDate" class="layui-input" 
							value="{{ d.orderDate || "" }}" autocomplete="off" lay-verify="required"></td>
					<td>&nbsp;&nbsp;</td>
					<td><b class="red">*</b>批次号：</td>
					<td><input type="text" name="bacthNumber" class="layui-input" value="{{ d.bacthNumber || ""}}" 
							lay-verify="required"></td>
					<td>&nbsp;&nbsp;</td>
					<td>订单备注：</td>
					<td><input type="text" name="remark" class="layui-input" value="{{ d.remark || ""}}"></td>
					<td>&nbsp;&nbsp;</td>
					<td><b class="red">*</b>订单数量：</td>
					<td style="width:80px;">
						<input type="number" id="orderNumber" readonly class="layui-input" name="number" 
							value="{{d.number || 0}}" lay-verify="required">
						</td>
					<td>&nbsp;&nbsp;</td>
					<td><span lay-submit lay-filter="saveProductPlan" class="layui-btn">保存</span></td>
				</tr>
			</table>
			<table id="addTable" lay-filter="addTable"></table>
		</div>`;
	var addTableToolbar = `
	<div>
		<span lay-event="chooseCustomer"  class="layui-btn layui-btn-sm">选择客户</span>
		<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除客户</span>
	</div>`;
	
	var chooseCustomerWin = `
	<div style="padding:10px;">
		<table class="layui-form">
			<tr>
				<td>客户名称：</td>
				<td><input class="layui-input" type="text" name="name"></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" id="searchBtn">搜索</span></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn layui-btn-sm" id="sureChoosed">确定</span></td>
			</tr>
		</table>
		<table id="choosedCustomerTable" lay-filter="choosedCustomerTable"></table>
	</div>
	`;
	var choosedProductWin = `
	<div style="padding:10px;">
		<table class="layui-form">
			<tr>
			<td>商品名称：</td>
			<td><input class="layui-input" type="text" name="name"></td>
			<td>&nbsp;&nbsp;</td>
			<td>商品编号：</td>
			<td><input class="layui-input" type="text" name="number"></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProductBtn" >搜索</span></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-badge">双击选择</span></td>
			</tr>
		</table>
		<table id="chooseProductTable" lay-filter="chooseProductTable"></table>
	</div>
	`;
	var allTypeSelectHtml = '', allUser = [], deleteChildIds = [],
	saveProductionPlan = {
		reloadTable:'',
	};
	
	saveProductionPlan.update = function(opt){
		var d = [];
		layui.each(opt.data.orderChilds,function(index,item){
			d.push({
				id:item.id,
				user: item.user,
				userId: item.user ? item.user.id : '',
				customerId: item.customer.id,
				name: item.customer.name,
				remark: item.childRemark,
				number: item.childNumber,
			})
		})
		saveProductionPlan.add({
			data: $.extend({},opt.data,{ orderChilds:d }),
		});
	}
	
	saveProductionPlan.add = function(opt){
		deleteChildIds = [];
		var data = opt.data,title="新增合同",customerList = [];
		if(!data){
			console.error('请给定数据！');
			return;
		}
		if(data.id){
			title = "修改合同";
			customerList = data.orderChilds;
		}
		var html = '';
		laytpl(TPL_MAIN).render(data,function(h){
			html = h;
		})
		
		var addWin = layer.open({
			title: title,
			type:1,
			area:['90%','100%'],
			content: html,
			success:function(){
				laydate.render({ elem:'#orderDate', type:'datetime', });
				$('#orderTypeSelect').append(allTypeSelectHtml);
				$('#orderTypeSelect').val($('#orderTypeSelect').data('value'));
				form.render();
				$('#chooseProductInput').unbind().on('click',function(){
					var chooseProductWinNew = layer.open({
						type:1,
						title:'商品选择',
						area:['50%','70%'],
						content: choosedProductWin,
						shadeClose:true,
						success:function(){
							mytable.render({
								elem:'#chooseProductTable',
								url: myutil.config.ctx+'/productPages',
								cols:[[
									{title:'产品编号',field:'number',},
									{title:'产品名称',field:'name',},
								]],
							})
							form.on('submit(searchProductBtn)',function(obj){
								table.reload('chooseProductTable',{
									where:obj.field,
									page:{ curr:1, }
								})
							})
							table.on('rowDouble(chooseProductTable)', function(obj){
								var data = obj.data;
								$('#productIdHidden').val(data.id);
								$('#chooseProductInput').val(data.name);
							  	layer.close(chooseProductWinNew);
							});
							

						}
					})
				})
				mytable.renderNoPage({
					elem: '#addTable',
					toolbar: addTableToolbar,
					size:'lg',
					data: customerList,
					ifNull: '',
					cols:[[
							{ type:'checkbox',},
							{ title:'客户',   	field:'name', 	},
							{ title:'下单人',    field:'user_userName',    
							/*下单人不可能为空！
							 * type:'select', select:{data:allUser,name:'userName',isDisabled:true,unsearch:true,}*/
							},
							{ title:'数量',   	field:'number',  	edit: 'number', },
							{ title:'备注',   	field:'remark',	edit: true, },
					       ]],
					done:function(){
						table.on('edit(addTable)',function(obj){
							if(obj.field == "number"){
								var allCache = table.cache['addTable'];
								var index = $(obj.tr).data('index');
								var cache = allCache[index];
								var number = obj.data.number;
								if(isNaN(number) || number<0 || number%1.0!=0 ){
									cache['number'] = myutil.lastData;	
									$(this).val(myutil.lastData);   //回滚数据
									return myutil.emsg('请确证填写数量！');
								}
								var val = parseInt(number);
								cache['number'] = val;	
								$(this).val(val);   
								var sum = 0;
								for(var i in allCache){
									sum -= -allCache[i].number;
								}
								$('#orderNumber').val(sum);
							}
						})
						form.render();
					}
				})
				form.on('submit(saveProductPlan)',function(obj){
					var orderChild = [];
					var msg = '';
					layui.each(table.cache['addTable'],function(index,item){
						orderChild.push({
							childNumber: item.number,
							userId: item.userId,
							customerId: item.customerId,
							childRemark: item.remark,
							id: item.id || "",
						})
						if(!item.number || isNaN(item.number)|| item.number<=0)
							return msg = '请正确填写数量!';
					})	
					orderChild.length==0 && (msg = '请选择客户');
					if(msg!='')
						return myutil.emsg(msg);
					obj.field.orderChild = JSON.stringify(orderChild);
					obj.field.orderNumber = obj.field.bacthNumber+$('#chooseProductInput').val();
					var url = '/ledger/addOrder';
					if(data.id){
						url = '/ledger/updateOrder';
						if(deleteChildIds.length>0){
							obj.field.deleteIds = deleteChildIds.join(',');
						}
					}
					myutil.saveAjax({
						url: url,
						data: obj.field,
						success:function(){
							table.reload(saveProductionPlan.reloadTable);
							layer.close(addWin);
						}
					})
				})
			}
		})
		table.on('toolbar(addTable)',function(obj){
			switch(obj.event){
			case 'chooseCustomer':	chooseCustomer();	break;
			case 'delete':	deleteCustomer();			break;
			}
		})
		function chooseCustomer(){
			customerList = [];
			var chooseProductWin = layer.open({
				title: '选择客户',
				type:1,
				area:['50%','90%'],
				content: chooseCustomerWin,
				success:function(){
					mytable.render({
						elem: '#choosedCustomerTable',
						url: myutil.config.ctx+'/ledger/customerPage?customerTypeId=459', //type=1
						ifNull: '---',
						cols:[[
								{ type:'checkbox',},
								{ title:'客户编号',	field:'id',	},
								{ title:'客户名称',	field:'name',	},
								{ title:'业务员',	field:'user_userName',	},
					       ]],
						done: function(res){		//回显复选框选中
							for(var i=0;i< res.data.length;i++){
		                         for (var j = 0; j < customerList.length; j++) {
		                             if(res.data[i].id == customerList[j].id)
		                             {
		                                 res.data[i]["LAY_CHECKED"]='true';
		                                 var index= res.data[i]['LAY_TABLE_INDEX'];
		                                 $('#choosedCustomerTable').next().find('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
		                                 $('#choosedCustomerTable').next().find('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
		                             }
		                         }
		                     }
		                     var checkStatus = table.checkStatus('choosedTable');
		                     if(checkStatus.isAll){
		                         $('#choosedCustomerTable').next().find(' .layui-table-header th[data-field="0"] input[type="checkbox"]').prop('checked', true);
		                         $('#choosedCustomerTable').next().find('.layui-table-header th[data-field="0"] input[type="checkbox"]').next().addClass('layui-form-checked');
		                     }
		                     form.render();
						}
					})
				}
			})
			table.on('checkbox(choosedCustomerTable)', function (obj) {	//监听复选框事件
				var currPageData = table.cache['choosedCustomerTable'];
				if(obj.checked==true){
				    if(obj.type=='one')
				    	customerList.push(obj.data);
				   else{
				        for(var i=0;i<currPageData.length;i++){
				     	   var isHas=false;
				     	   layui.each(customerList,function(index,item){
				     		   if(item.id == currPageData[i].id){
				     			   isHas = true;
				     		   	   return;
				     		   }
				     	   })
				     	   if(!isHas)
				     		  customerList.push(currPageData[i]);
				        }
				    }
				}else{
				    if(obj.type=='one'){
				        for(var i=0;i<customerList.length;i++)
				           if(customerList[i].id==obj.data.id){
				        	   	customerList.splice(i,1);
								break;
				           }
				    }else{
				 	   	for(var i=0;i<currPageData.length;i++)
				 		   	for(var j=0;j<customerList.length;j++)
				 			   	if(customerList[j].id == currPageData[i].id){	
				 				  	customerList.splice(j,1);
				 				   	break;
				 			   	}
				    }
				}
    		});
			form.on('submit(searchProduct)',function(obj){
				table.reload('choosedCustomerTable',{
					where: obj.field,
					page: { curr:1 },
				})
			})
			$('#sureChoosed').unbind().on('click',function(){
				var t = table.cache['addTable'];
				var newCus = [];
				var isHas = false;
				layui.each(customerList,function(index1,item1){
					if(isHas)
						return;
					for(var i=0;i<t.length;i++)
						if(t[i].customerId == item1.id){
							isHas = true;
							myutil.emsg('客户:'+item1.name+' 已存在请勿重复添加客户');
						}
					newCus.push({
						userId: item1.user ? item1.user.id : "",
						user: item1.user || null,
						name: item1.name,
						remark: '',
						number: 0,
						customerId: item1.id,
					})
				})
				if(isHas)
					return;
				table.reload('addTable',{ data: t.concat(newCus) })
				layer.close(chooseProductWin);
			})
		}
		function deleteCustomer(){
			var checked = layui.table.checkStatus('addTable').data;
			if(checked.length==0)
				return layer.msg('请选择删除的客户',{icon:2});
			var confirm = layer.confirm('是否确认删除？',function(){
				var allCache = table.cache['addTable'];
				layui.each(checked,function(index1,item1){
					layui.each(allCache,function(index2,item2){
						if(item1.customerId == item2.customerId ){
							allCache.splice(index2,1);
							if(item1.id)
								deleteChildIds.push(item1.id);
							return;
						}
					})
				})
				var sum = 0;
				for(var i in allCache){
					sum -= -allCache[i].number;
				}
				$('#orderNumber').val(sum);
				table.reload('addTable',{ data: allCache, })
				layer.close(confirm);
			})
		}// end deleteCustomer
	}
	saveProductionPlan.init = function(){
		myutil.getData({
			url: myutil.config.ctx+'/basedata/list?type=orderNumberType',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++)
					allTypeSelectHtml+='<option value="'+d[i].id+'">'+d[i].name+'</option>'
			}
		})
		myutil.getData({
			url: myutil.config.ctx+'/system/user/findUserList',
			success:function(d){
				allUser = d;
				allUser.unshift({id:'',userName:'请选择'});
			}
		})
	}
	exports('saveProductionPlan',saveProductionPlan);
})