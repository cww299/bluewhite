/* 2019/11/1
 * author: 299
 * 新增、修改外发单模板
 * 需要在本模块前引入myutil,并设置ctx后调用init()
 * outOrderModel.add({ success:function(){ 成功函数的回调 }  }) 绑定新增按钮
 * OutOrderModel.update({ data:{修改前的数据、回显},   })
 */
layui.extend({
	formSelects : 'formSelect/formSelects-v4'
}).define(['jquery','layer','form','laytpl','laydate','formSelects'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		formSelects = layui.formSelects,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		myutil = layui.myutil;
	//获取值 formSelects.value('roleIdSelect', 'valStr'),
	//渲染 formSelects.render();
	var TPL = ['<div class="layui-form layui-form-pane" style="padding:20px;">',
				'<p style="display:none;"><button lay-submit lay-filter="sureAddOutOrder" id="sureAddOutOrder">确定</button></p>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">开单时间</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="required" name="openOrderTime" id="openOrderTime" value="{{ d.openOrderTime?d.openOrderTime:"" }}">',
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
						'<select id="processSelect" lay-search name="outsourceTaskIds" xm-select="processSelect"',
						   'xm-select-show-count="4">',
							'<option value="">请选择</option>',
						'</select>',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">外发数量</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="required" name="processNumber" ',
							'value="{{ d.processNumber?d.processNumber:"" }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">克重</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" id="gWeight" lay-verify="number" name="gramWeight"', 
							'value="{{ d.gramWeight?d.gramWeight:'+'""'+' }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">千克重</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" id="kgWeight" lay-verify="number" name="kilogramWeight"',
							'value="{{ d.kilogramWeight?d.kilogramWeight:"" }}" >',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">加工点</label>',
					'<div class="layui-input-block">',
						'<select lay-search name="customerId" id="customerId" data-value="{{ d.customer?d.customer.id:"" }}">',
							'<option value="">请选择</option></select>',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">跟单人</label>',
					'<div class="layui-input-block">',
						'<select lay-search name="userId" id="userId" data-value="{{ d.user?d.user.id:"" }}">',
							'<option value="">请选择</option></select>',
					'</div>',
				'</div>',
	           ].join(' ');
	
	var outOrderModel = {}, allOrgUser ='', allCustom = '', allWarehouse = '',allProcess = '',allUser = '';
	
	outOrderModel.add = function(opt){
		outOrderModel.update(opt)
	}
	
	outOrderModel.update = function(opt){
		var data = opt.data,title="生成加工单",t="";
		if(!data){
			console.error('请给定数据！');
			return;
		}
		if(data.outsource)
			title = '生成外发加工单';
		if(data.id){
			title = "修改加工单"
			if(data.outsource)
				title = "修改外发加工单";
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
			t = TPL+['<input type="hidden" name="orderId" value="{{ d.orderId }}">',
			        '<input type="hidden" name="outsource" value="{{ d.outsource }}">'].join(' ');
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
			    $('#gWeight').attr('onkeyup',"value=value.replace(/[^\\d.]/g,'')");
			    $('#kgWeight').attr('onkeyup',"value=value.replace(/[^\\d.]/g,'')");
				$('#gWeight').blur(function(){
					var val = $(this).val();
					if(isNaN(val) || val<0){
						$('#kgWeight').val('');
						$(this).val('');
						return myutil.emsg('请正确输入克重！');
					}
					$('#kgWeight').val(val/1000);
				})
				$('#kgWeight').blur(function(){
					var val = $(this).val();
					if(isNaN(val) || val<0){
						$('#kgWeight').val('');
						$(this).val('');
						return myutil.emsg('请正确输入千克重！');
					}
					$('#gWeight').val(val*1000);
				})
				form.on('submit(sureAddOutOrder)',function(obj){
					var url = '/ledger/saveOrderOutSource';
					if(data.id)
						url = '/ledger/updateOrderOutSource';
					myutil.saveAjax({
						url: url,
						data: obj.field,
						success:function(){
							layer.close(win);
							opt.success && opt.success();
						}
					})
				})
				$('#userId').append(allOrgUser);
				if(data.outsource==1){
					$('#customerId').append(allCustom);
				}else{
					$('#customerId').append(allUser);
					$('#customerId').attr('name','processingUserId');
				}
				$('#warehouseTypeId').append(allWarehouse);
				$('#processSelect').append(allProcess);
				
				
				/*$('#customerId').val($('#customerId').data('value'));
				$('#userId').val($('#userId').data('value'));
				$('#warehouseTypeId').val($('#warehouseTypeId').data('value'));*/
				form.render();
				formSelects.render();
			},
			yes:function(){
				$('#sureAddOutOrder').click();
			}
		})
	}
	
	outOrderModel.init = function(){
		myutil.getData({	//获取工序类型
			url: myutil.config.ctx+'/basedata/list?type=outsourceTask',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allProcess += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
			}
		})
		myutil.getData({	//获取部门、生产、外协部人员
			url: myutil.config.ctx+'/system/user/findUserList?orgNameIds=20,23',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allOrgUser += '<option value="'+d[i].id+'">'+d[i].userName+'</option>';
				}
			}
		})
		myutil.getData({	//获取所有人员
			url: myutil.config.ctx+'/system/user/findUserList?quit=0',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allUser += '<option value="'+d[i].id+'">'+d[i].userName+'</option>';
				}
			}
		})
		myutil.getData({	//获取所有客户
			url: myutil.config.ctx+'/ledger/allCustomer?type=5',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allCustom += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
			}
		})
		myutil.getData({	//获取仓库类型
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