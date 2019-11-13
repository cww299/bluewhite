/* 2019/11/1
 * author: 299
 * 新增、修改外发单模板
 * 需要在本模块前引入myutil,并设置ctx后调用init()
 * outOrderModel.add({ success:function(){ 成功函数的回调 }  }) 绑定新增按钮
 * OutOrderModel.update({ data:{修改前的数据、回显},   })
 */
layui.define(['jquery','layer','form','laytpl','laydate'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		myutil = layui.myutil;
	
	var TPL = ['<div class="layui-form layui-form-pane" style="padding:20px;">',
				'<p style="display:none;"><button lay-submit lay-filter="sureAddOutOrder" id="sureAddOutOrder">确定</button></p>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">开单时间</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="required" name="openOrderTime" id="openOrderTime" value="{{ d.openOrderTime?d.openOrderTime:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">外发编号</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" name="outSourceNumber" readonly value="{{ d.outSourceNumber }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">棉花类型</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" name="fill" value="{{ d.fill?d.fill:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">棉花备注</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" name="fillRemark" value="{{ d.fillRemark?d.fillRemark:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">外发工序</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" name="process" value="{{ d.process?d.process:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">外发数量</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="required" name="processNumber" value="{{ d.processNumber?d.processNumber:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">克重</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" name="gramWeight" value="{{ d.gramWeight?d.gramWeight:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">千克重</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" name="gramWeight" value="{{ d.gramWeight?d.gramWeight:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">加工点</label>',
					'<div class="layui-input-block">',
						'<select lay-search name="customerId" id="customerId" data-value="{{ d.customer?d.customer.id:"" }}"><option value="">请选择</option></select>',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">跟单人</label>',
					'<div class="layui-input-block">',
						'<select lay-search name="userId" id="userId" data-value="{{ d.user?d.user.id:"" }}"><option value="">请选择</option></select>',
					'</div>',
				'</div>',
	           ].join(' ');
	
	var outOrderModel = {}, allUser = [], allCustom = [], allWarehouse = [];
	
	outOrderModel.add = function(opt){
		outOrderModel.update(opt)
	}
	
	outOrderModel.update = function(opt){
		var data = opt.data,title="生成外发单",t="";
		if(!data){
			console.error('请给定数据！');
			return;
		}
		if(data.id){
			title = "修改外发单"
			t = TPL +[
					'<div class="layui-item" pane>',
						'<label class="layui-form-label">预计仓库</label>',
						'<div class="layui-input-block">',
							'<select name="warehouseTypeId" id="warehouseTypeId" lay-search data-value="{{ d.warehouseType?d.warehouseType.id:"" }}">',
								'<option value="">请选择</option></select>',
						'</div>',
					'</div>',
					'<div class="layui-item" pane>',
						'<label class="layui-form-label">外发时间</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="outGoingTime" id="outGoingTime" value="{{ d.outGoingTime?d.outGoingTime:"" }}">',
						'</div>',
					'</div>',
					'<input type="hidden" name="id" value="{{ d.id }}">',
			        ].join(' ');
		}else{
			t = TPL+'<input type="hidden" name="orderId" value="{{ d.orderId }}">';
		}
		t+='</div>';
		var html = '';
		laytpl(t).render(data,function(h){
			html = h;
		})
		var win = layer.open({
			type:1,
			content:html,
			area:['30%','80%'],
			btn:['确定','取消'],
			title: title,
			shadeClose:true,
			btnAlign:'c',
			success:function(){
				laydate.render({ elem:'#openOrderTime', type:'datetime', });
				laydate.render({ elem:'#outGoingTime', type:'datetime', });
				form.on('submit(sureAddOutOrder)',function(obj){
					var url = '/ledger/saveOrderOutSource';
					if(data.id)
						url = '/ledger/updateOrderOutSource'
					myutil.saveAjax({
						url: url,
						data: obj.field,
						success:function(){
							layer.close(win);
							opt.success && opt.success();
						}
					})
				})
				$('#customerId').append(allCustom);
				$('#userId').append(allUser);
				$('#warehouseTypeId').append(allWarehouse);
				$('#customerId').val($('#customerId').data('value'));
				$('#userId').val($('#userId').data('value'));
				$('#warehouseTypeId').val($('#warehouseTypeId').data('value'));
				form.render();
			},
			yes:function(){
				$('#sureAddOutOrder').click();
			}
		})
	}
	
	outOrderModel.init = function(){
		myutil.getData({
			url: myutil.config.ctx+'/system/user/findUserList?orgNameIds=20,23',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allUser += '<option value="'+d[i].id+'">'+d[i].userName+'</option>';
				}
			}
		})
		myutil.getData({
			url: myutil.config.ctx+'/ledger/allCustomer?type=5',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allCustom += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
			}
		})
		myutil.getData({
			url: myutil.config.ctx+'/basedata/list?type=warehouseType&size=9999',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allWarehouse += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
			}
		})
	}
	
	
	exports('outOrderModel',outOrderModel);
})