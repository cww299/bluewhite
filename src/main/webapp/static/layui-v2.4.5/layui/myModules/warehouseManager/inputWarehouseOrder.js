/* 2019/12/2
 * author: 299
 * 新增、修改入库单模板
 * 需要在本模块前引入myutil,并设置ctx后调用init()
 * inputWarehouseOrder.add({ success:function(){ 成功函数的回调 }  }) 绑定新增按钮
 * inputWarehouseOrder.update({ data:{修改前的数据、回显},   })
 */
layui.extend({
}).define(['jquery','layer','form','laytpl','laydate'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		myutil = layui.myutil;
	var TPL = ['<div class="layui-form layui-form-pane" style="padding:20px;">',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">入库时间</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="required" name="arrivalTime" id="arrivalTime" value="{{ d.arrivalTime?d.arrivalTime:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">入库数量</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="number" name="arrivalNumber" ',
							'value="{{ d.arrivalNumber?d.arrivalNumber:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">库区</label>',
					'<div class="layui-input-block">',
						'<select lay-search name="storageAreaId" id="storageAreaId">',
							'<option value="">请选择</option></select>',
					'</div>',
				'</div>',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">库位</label>',
					'<div class="layui-input-block">',
						'<select lay-search name="storageLocationId" id="storageLocationId">',
							'<option value="">请选择</option></select>',
					'</div>',
				'</div>',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">入库人</label>',
					'<div class="layui-input-block">',
						'<select lay-search name="userStorageId" id="userStorageId">',
							'<option value="">请选择</option></select>',
					'</div>',
				'</div>',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">入库状态</label>',
					'<div class="layui-input-block">',
						'<select name="inStatus" disabled value="{{ d.inStatus || 1}}" id="inStatus">',
							'<option value="1">采购入库</option>',
							'<option value="2">调拨入库</option>',
							'<option value="3">退货入库</option>',
							'<option value="4">换货入库</option>',
							'<option value="5">盘亏入库</option>',
							'</select>',
					'</div>',
				'</div>',
				'<div>',
					'<input type="hidden" name="materielId" value="{{ d.materielId }}">',
					'<input type="hidden" name="orderProcurementId" value="{{ d.orderProcurementId }}">',
					'<input type="hidden" name="inWarehouseTypeId" value="379">',
					'<input type="hidden" name="id" value="{{d.id || ""}}">',
				'</div>',
				'<p style="display:none;"><button lay-submit lay-filter="sureAddOutOrder" id="sureAddOutOrder">确定</button></p>',
				'</div>',
	           ].join(' ');
	
	var inputWarehouseOrder = {}, allStorageLocation = '',allStorageArea = '',allUser = '';
	
	inputWarehouseOrder.add = function(opt){
		inputWarehouseOrder.update(opt)
	}
	
	inputWarehouseOrder.update = function(opt){
		var data = opt.data,title="生成入库单";
		if(!data){
			console.error('请给定数据！');
			return;
		}
		if(data.id){
			title = "修改入库单";
			data.materielId = data.materiel.id;
			data.orderProcurementId = data.orderProcurement.id;
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
				form.on('submit(sureAddOutOrder)',function(obj){
					var url = '/ledger/inventory/saveMaterialPutStorage';
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
	
	inputWarehouseOrder.init = function(done){
		var success = 0;
		myutil.getDataSync({	//获取库区
			url: myutil.config.ctx+'/basedata/list?type=storageArea',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allStorageArea += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
				inputWarehouseOrder.allStorageArea = allStorageArea;
				success++;
				if(success==3)
					done();
			}
		})
		myutil.getData({	//获取所有人员
			url: myutil.config.ctx+'/system/user/findUserList?quit=0',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allUser += '<option value="'+d[i].id+'">'+d[i].userName+'</option>';
				}
				inputWarehouseOrder.allUser = allUser;
				success++;
				if(success==3)
					done();
			}
		})
		myutil.getData({	//获取库位
			url: myutil.config.ctx+'/basedata/list?type=storageLocation&size=9999',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allStorageLocation += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
				inputWarehouseOrder.allStorageLocation = allStorageLocation;
				success++;
				if(success==3)
					done();
			}
		})
	}
	exports('inputWarehouseOrder',inputWarehouseOrder);
})