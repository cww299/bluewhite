/* 2019/12/2
 * author: 299
 * 新增、修改出库单模板
 * 需要在本模块前引入myutil,并设置ctx后调用init()
 * outWarehouseOrder.add({ success:function(){ 成功函数的回调 }  }) 绑定新增按钮
 * outWarehouseOrder.update({ data:{修改前的数据、回显},   })
 * 
 * outWarehouseOrder.add({
 * 		type: 0,		//入库类型1:物料出库（默认）、2:成品出库、3:皮壳出库
 * 
 * })
 */
layui.extend({
}).define(['jquery','layer','form','laytpl','laydate','mytable'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		mytable = layui.mytable,
		table = layui.table,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		myutil = layui.myutil;
	var TPL = ['<div class="layui-form layui-form-pane" style="padding:20px;">',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">出库时间</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="required" name="arrivalTime" id="arrivalTime" value="{{ d.arrivalTime?d.arrivalTime:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">出库数量</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="number" name="arrivalNumber" ',
							'value="{{ d.arrivalNumber?d.arrivalNumber:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">出库人</label>',
					'<div class="layui-input-block">',
						'<select lay-search name="userStorageId" id="userStorageId">',
							'<option value="">请选择</option></select>',
					'</div>',
				'</div>',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">出库类型</label>',
					'<div class="layui-input-block">',
						'<select name="outStatus">',
							'<option value="1">生产出库</option>',
							'<option value="2">调拨出库</option>',
							'<option value="3">销售换货出库</option>',
							'<option value="4">采购退货出库</option>',
							'<option value="5">盘盈出库</option>',
							`{{#
							 	if(layui.outWarehouseOrder.type!=1){
							 }}
									'<option value="6">返工出库</option>'
							 {{#
							 	 }
							  }}
							  `,
							'</select>',
					'</div>',
				'</div>',
				'<div class="layui-form-item">',
					'<label class="layui-form-label textareaLable" style="">入库单</label>',
					'<div class="layui-input-block">',
						'<div class="layui-input textareaDiv" id="inputOrderChoose" >',
							'<p class="textareaTips">单击进行选择</p>',
						'</div>',
					'</div>',
				'</div>',
				'<div>',
					'<input type="hidden" name="inWarehouseTypeId" value="379">',
					'<input type="hidden" name="id" value="{{d.id || ""}}">',
					'<input type="hidden" name="materialPutStorageId" id="materialPutStorageId">',
					`{{#
				 	if(layui.outWarehouseOrder.type==1){
					}}
						<input type="hidden" name="materielId" value="{{ d.materielId }}">
					{{#
				 	 	}else{
					}}
						<input type="hidden" name="productId" value="{{ d.productId }}">
					{{#
				 	 	}
					}}
					`,
				'</div>',
				'<p style="display:none;"><button lay-submit lay-filter="sureAddOutOrder" id="sureAddOutOrder">确定</button></p>',
				'</div>',
	           ].join(' ');
	
	var outWarehouseOrder = {
			type:1,
		}, allStorageLocation = '',allStorageArea = '',allUser = '';
	
	outWarehouseOrder.add = function(opt){
		outWarehouseOrder.update(opt)
	}
	
	outWarehouseOrder.update = function(opt){
		var data = opt.data,title="生成出库单";
		if(!data){
			console.error('请给定数据！');
			return;
		}
		if(data.id){
			title = "修改出库单";
			if(outWarehouseOrder.type==1){
				data.materielId = data.materiel.id;
			}else{
				data.productId = data.productId;
			}
		}
		var html = '';
		laytpl(TPL).render(data,function(h){
			html = h;
		})
		var win = layer.open({
			type:1,
			content:html,
			area:['32%','500px'],
			offset:'50px',
			btn:['确定','取消'],
			title: title,
			btnAlign:'c',
			success:function(){
				laydate.render({ elem:'#arrivalTime', type:'datetime', });
				$('#userStorageId').append(allUser);
				$('#storageLocationId').append(allStorageLocation);
				$('#storageAreaId').append(allStorageArea);
				if(data.id){	//如果存在id，进行数据回显
					$('#userStorageId').val(data.userStorage?data.userStorage.id:'');
					$('#storageAreaId').val(data.storageArea?data.storageArea.id:'');
					$('#storageLocationId').val(data.storageLocation?data.storageLocation.id:'');
					$('#storageLocationId').val(data.storageLocation?data.storageLocation.id:'');
					$('#inStatus').val(data.inStatus);
					form.render();
				}
				$('#inputOrderChoose').click(function(){
					var chooseInputWin = layer.open({
						typr:1,
						btn:[],
						title:'入库单选择',
						area:['70%','80%'],
						content:`<div>
									<table id="chooseTable" lay-filter="chooseTable"></table>
								 </div>
								`,
						success:function(){
							var url = '/ledger/inventory/materialPutStoragePage';
							if(outWarehouseOrder.type!=1)
								url = '/ledger/inventory/putStoragePage';
							mytable.render({
								elem:'#chooseTable',
								url: myutil.config.ctx+url,
								toolbar:`<div>
											<span class="layui-btn layui-btn-sm" lay-event="sureChoosedInputOrder">确定选择</span>
										</div>`,
								ifNull:'--',
								cols:[[
								       { type:'checkbox',},
								       { title:'入库编号', field:'serialNumber',width:'30%',},
								       { title:'入库时间',   field:'arrivalTime', type:'dateTime',width:'15%',	},
								       { title:'库区',   field:'storageArea_name', 	},
								       { title:'库位',   field:'storageLocation_name',	},
								       { title:'剩余数量',   field:'surplusNumber',width:'10%',	},
								       ]],
								done:function(){
									
								}
							})
							table.on('toolbar(chooseTable)',function(obj){
								var check = layui.table.checkStatus('chooseTable').data;
								if(obj.event=='sureChoosedInputOrder'){
									if(check.length<1)
										return myutil.emsg('请选择相关数据');
									var html = '';
									var choosedIds = $('#materialPutStorageId').val().split(',');
									for(var i=0;i<check.length;i++){
										for(var j=0;j<choosedIds.length;j++){
											if(choosedIds[j]==check[i].id){
												myutil.emsg('入库单：'+check[i].serialNumber+'已选择 ，请勿重复添加');
												return false;
											}
										}
										html += ['<span class="layui-badge layui-bg-green">',
													check[i].serialNumber,
													'<i class="layui-icon layui-icon-close" data-id="'+check[i].id+'"></i>',
												'</span>',].join(' ');
										choosedIds.push(check[i].id);
									}
									if($('#materialPutStorageId').val()=="")
										$('#inputOrderChoose').html(html);
									else
										$('#inputOrderChoose').append(html);
									$('#materialPutStorageId').val(choosedIds.join(','));
									$('#inputOrderChoose .layui-icon').unbind().on('click',function(e){
										layui.stope(e);
										var id = $(this).data('id');
										var choosedIds = $('#materialPutStorageId').val().split(',');
										for(var i in choosedIds){
											if(choosedIds[i]==id){
												choosedIds.splice(i,1);
												$('#materialPutStorageId').val(choosedIds.join(','));
												$(this).closest('span').remove();
												break;
											}
										}
									})
									layer.close(chooseInputWin);
									form.render();
								}
							})
						}
					})
				})
				form.on('submit(sureAddOutOrder)',function(obj){
					var url = '/ledger/inventory/saveOutStorage';
					if(outWarehouseOrder.type==1)
						url = '/ledger/inventory/saveMaterialOutStorage';
					myutil.saveAjax({
						url: url,
						data: obj.field,
						success:function(){
							layer.close(win);
							opt.success && opt.success();
						}
					})
				})
				form.render();
			},
			yes:function(){
				$('#sureAddOutOrder').click();
			}
		})
	}
	
	outWarehouseOrder.init = function(done){
		var success = 0;
		myutil.getData({	//获取所有人员
			url: myutil.config.ctx+'/system/user/findUserList?quit=0',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allUser += '<option value="'+d[i].id+'">'+d[i].userName+'</option>';
				}
				outWarehouseOrder.allUser = allUser;
				success++;
				if(success==1)
					done && done();
			}
		})
		var filePath = layui.cache.modules.outWarehouseOrder
	    .substr(0, layui.cache.modules.outWarehouseOrder.lastIndexOf('/'))
		layui.link(filePath+"/../css/warehouseManager/outWarehouseOrder.css")
	}
	exports('outWarehouseOrder',outWarehouseOrder);
})