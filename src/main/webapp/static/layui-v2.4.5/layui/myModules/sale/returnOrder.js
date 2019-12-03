/* 2019/12/03
 * author: 299
 * 新增、修改退货单模板
 * 需要在本模块前引入myutil,并设置ctx后调用init()
 * returnOrder.add({ success:function(){ 成功函数的回调 }  }) 绑定新增按钮
 * returnOrder.update({ data:{修改前的数据、回显},   })
 */
layui.extend({
	//formSelects : 'formSelect/formSelects-v4'
}).define(['jquery','layer','form','laytpl','laydate','formSelects','laydate'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		formSelects = layui.formSelects,
		laytpl = layui.laytpl,
		myutil = layui.myutil;
	//获取值 formSelects.value('roleIdSelect', 'valStr'),
	//渲染 formSelects.render();
	var TPL = ['<div class="layui-form layui-form-pane" style="padding:20px;">',
				'<p style="display:none;"><button lay-submit lay-filter="sureAddOutOrder" id="sureAddReturnOrder">确定</button></p>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">退货日期</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="required" id="returnTime" name="returnTime" ',
							'value="{{ d.returnTime || "" }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">工序</label>',
					'<div class="layui-input-block">',
						'<select id="processSelect" lay-search name="outsourceTaskIds" xm-select="processSelect"',
						   'xm-select-show-count="5">',
							'<option value="">请选择</option>',
						'</select>',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">外发数量</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" lay-verify="required" id="processNumber" readonly ',
							'value="{{ d.processNumber || "" }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">退货数量</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" name="returnNumber" lay-verify="number" value="{{ d.returnNumber || ""  }}">',
					'</div>',
				'</div>',
				'<div class="layui-item" pane>',
					'<label class="layui-form-label">退货原因</label>',
					'<div class="layui-input-block">',
						'<input class="layui-input" name="returnRemark" value="{{ d.returnRemark || ""  }}">',
					'</div>',
				'</div>',
			   '</div>',
	           ].join(' ');
	
	var returnOrder = {}, allProcess = '';
	
	returnOrder.add = function(opt){
		opt.data.orderOutSourceId = opt.data.id;
		opt.data.id = '';
		returnOrder.update(opt)
	}
	
	returnOrder.update = function(opt){
		var data = opt.data,title="生成退货单";
		if(!data){
			console.error('请给定数据！');
			return;
		}
		if(data.id){
			title = "修改退货单"
		}
		var html = '';
		laytpl(TPL).render(data,function(h){
			html = h;
		})
		var win = layer.open({
			type:1,
			content:html,
			offset:'50px',
			area:['32%','610px'],
			btn:['确定','取消'],
			title: title,
			shadeClose:true,
			btnAlign:'c',
			success:function(){
				laydate.render({ elem:'#returnTime', type:'datetime', value: new Date(), })
				allProcess='';
				layui.each(data.outsourceTask,function(index,item){
					allProcess += '<option value="'+item.id+'">'+item.name+'</option>';
				})
				$('#processSelect').append(allProcess);
				formSelects.render();
				if(data.id){	//如果存在id，进行数据回显
					var processIds = [];
					for(var i=0,len=data.outsourceTaskChoosed.length;i<len;i++)
						processIds.push(data.outsourceTaskChoosed[i].id);
					formSelects.value('processSelect',processIds); 
				}
				form.on('submit(sureAddOutOrder)',function(obj){
					var url = '/ledger/saveRefundBills';
					obj.field.orderOutSourceId = data.orderOutSourceId;
					if(data.id)
						obj.field.id = data.id;
					if(!obj.field.outsourceTaskIds)
						return myutil.emsg('工序不能为空！');
					var val = $('#processNumber').val();
					if(val-obj.field.returnNumber<0)
						return myutil.emsg('退货数量不能大于外发数量！');
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
				$('#sureAddReturnOrder').click();
			}
		})
	}
	
	returnOrder.init = function(){
	}
	exports('returnOrder',returnOrder);
})