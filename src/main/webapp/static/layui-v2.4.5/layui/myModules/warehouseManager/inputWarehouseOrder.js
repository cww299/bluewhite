/* 2019/12/2
 * author: 299
 * 新增、修改入库单模板
 * 需要在本模块前引入myutil,并设置ctx后调用init(done)//回调函数
 * inputWarehouseOrder.add({ success:function(){ 成功函数的回调 }  }) 绑定新增按钮
 * inputWarehouseOrder.update({ data:{修改前的数据、回显},   })
 * type: 0,		//入库类型1:物料入库（默认）、2:成品入库、3:皮壳入库
 * inputWarehouseOrder.add({
 * 		inStatus: 1,    //采购入库	物料type=1时的值
 * 		inStatus: 2,    //生产入库 如果新增为这两种状态，请给定inStatus值
 * 		inStatus: 3,    //调拨入库,
 * 		inStatus: 4,	//退货入库,
 * 		inStatus: 5,	//换货入库,
 * 		inStatus: 6, 	//盘亏入库,
 * 		orderProcurementId:'',  //修改物料入库采购入库时，传入的订单id
 * 		materielId:'',			//物料入库时传入
 * 		productId:'',   		//成品、皮壳入库时传入
 * 		orderOutSourceId:'',    //成品、皮壳生产入库时，传入的订单id
 * })
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
						'<select lay-search name="userStorageId" id="userStorageId" lay-verify="required">',
							'<option value="">请选择</option></select>',
					'</div>',
				'</div>',
				'<div class="layui-form-item" pane>',
					'<label class="layui-form-label">入库类型</label>',
					'<div class="layui-input-block">',
						'<select name="inStatus" {{(d.inStatus==1 || d.inStatus==2)?"disabled":""}} ',
							' value="{{ d.inStatus || 3}}" id="inStatus">',
							`{{#
								 if(layui.inputWarehouseOrder.type==layui.inputWarehouseOrder.allType.WL){
							 }}
									<option value="2" {{ d.inStatus!=2?"disabled":"" }}>采购入库</option>
							 {{#
							 	 }else{
							  }}
							  		<option value="1" {{ d.inStatus!=1?"disabled":"" }}>生产入库</option>
							  {{#
							     }
							  }}
							  `,
							'<option value="3">调拨入库</option>',
							'<option value="4">退货入库</option>',
							'<option value="5">换货入库</option>',
							'<option value="6">盘亏入库</option>',
							'</select>',
					'</div>',
				'</div>',
				'<div>',
					`{{#
						 if(layui.inputWarehouseOrder.type==layui.inputWarehouseOrder.allType.WL){
					 }}
							<input type="hidden" name="materielId" value="{{ d.materielId }}">
							<input type="hidden" name="orderProcurementId" value="{{ d.orderProcurementId || "" }}">
					 {{#
					 	 }else{
					  }}
					  		<input type="hidden" name="productId" value="{{ d.productId }}">
							<input type="hidden" name="orderOutSourceId" value="{{ d.orderOutSourceId || "" }}">
					  {{#
					     }
					  }}
				  `,
					'<input type="hidden" name="warehouseTypeId" value="{{'+
						' layui.inputWarehouseOrder.type==layui.inputWarehouseOrder.allType.WL?434:(layui.inputWarehouseOrder.type==2?274:379) }}">',
					'<input type="hidden" name="id" value="{{d.id || ""}}">',
				'</div>',
				'<p style="display:none;"><button lay-submit lay-filter="sureAddOutOrder" id="sureAddOutOrder">确定</button></p>',
				'</div>',
	           ].join(' ');
	
	
	var inputWarehouseOrder = {
			type: 1,	//默认为物料入库
			allType :{
				WL : 1, CP : 2, PK : 3,
			}
		}, 
		allStorageLocation = '',allStorageArea = '',allUser = '';
	
	inputWarehouseOrder.add = function(opt){
		inputWarehouseOrder.update(opt || {})
	}
	
	inputWarehouseOrder.update = function(opt){
		var data = opt.data,title="生成入库单";
		if(!data){
			console.error('请给定数据！');
			return;
		}
		if(data.id){
			title = "修改入库单";
			if(inputWarehouseOrder.type==inputWarehouseOrder.allType.WL){
				data.materielId = data.materiel.id;
				data.orderProcurementId = data.orderProcurement.id;
			}else{
				data.productId = '';
				data.orderOutSourceId = '';
			}
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
					$('#inStatus').val(data.inStatus);
					if(inputWarehouseOrder.type==inputWarehouseOrder.allType.WL){
						
					}else{
						
					}
					form.render();
				}
				form.on('submit(sureAddOutOrder)',function(obj){
					var url = '/ledger/inventory/savePutStorage';
					if(inputWarehouseOrder.type==inputWarehouseOrder.allType.WL)
						url = '/ledger/inventory/saveMaterialPutStorage';
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
					done && done();
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
					done && done();
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
					done && done();
			}
		})
	}
	exports('inputWarehouseOrder',inputWarehouseOrder);
})