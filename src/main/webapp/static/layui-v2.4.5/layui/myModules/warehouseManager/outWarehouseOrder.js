/* 2019/12/2
 * author: 299
 * 新增、修改出库单模板
 * 需要在本模块前引入myutil,并设置ctx后调用init()
 * outWarehouseOrder.add({ success:function(){ 成功函数的回调 }  }) 绑定新增按钮
 * outWarehouseOrder.update({ data:{修改前的数据、回显},   })
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
							'</select>',
					'</div>',
				'</div>',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">入库单</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="required" name="" id="inputOrderChoose" ',
							'placeholder="单击进行选择" ',
							'value="{{ d.materialPutStorage?d.MaterialPutStorage.serialNumber:"" }}" readonly>',
					'</div>',
				'</div>',
				'<div>',
					'<input type="hidden" name="materielId" value="{{ d.materielId }}">',
					'<input type="hidden" name="inWarehouseTypeId" value="379">',
					'<input type="hidden" name="materialPutStorageId" id="materialPutStorageId" value="{{d.materialPutStorage?d.materialPutStorage.id:""}}">',
					'<input type="hidden" name="id" value="{{d.id || ""}}">',
				'</div>',
				'<p style="display:none;"><button lay-submit lay-filter="sureAddOutOrder" id="sureAddOutOrder">确定</button></p>',
				'</div>',
	           ].join(' ');
	
	var outWarehouseOrder = {}, allStorageLocation = '',allStorageArea = '',allUser = '';
	
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
			data.materielId = data.materiel.id;
		}
		var html = '';
		laytpl(TPL).render(data,function(h){
			html = h;
		})
		var win = layer.open({
			type:1,
			content:html,
			area:['32%','480px'],
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
						title:'入库单选择&nbsp;&nbsp;&nbsp;<span class="layui-badge">提示：双击进行选择</span>',
						area:['70%','80%'],
						content:`<div>
									<table id="chooseTable" lay-filter="chooseTable"></table>
								 </div>
								`,
						success:function(){
							mytable.render({
								elem:'#chooseTable',
								url: myutil.config.ctx+'/ledger/inventory/materialPutStoragePage',
								ifNull:'--',
								cols:[[
								       { type:'checkbox',},
								       { title:'入库编号', field:'serialNumber',},
								       { title:'入库时间',   field:'arrivalTime', type:'dateTime',width:'10%',	},
								       { title:'库区',   field:'storageArea_name', 	},
								       { title:'库位',   field:'storageLocation_name',	},
								       { title:'剩余数量',   field:'surplusNumber',width:'10%',	},
								       { title:'面料',   field:'materiel_name',	},
								       { title:'入库内容',   field:'orderProcurement_orderProcurementNumber',	width:'33%'},
								       ]],
								done:function(){
									table.on('row(chooseTable)', function(obj){
										layer.close(chooseInputWin);
										var data = obj.data;
										$('#materialPutStorageId').val(data.id);
										$('#inputOrderChoose').val(data.serialNumber);
										form.render();
									});
								}
							})
						}
					})
				})
				form.on('submit(sureAddOutOrder)',function(obj){
					var url = '/ledger/inventory/saveMaterialOutStorage';
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
	}
	exports('outWarehouseOrder',outWarehouseOrder);
})