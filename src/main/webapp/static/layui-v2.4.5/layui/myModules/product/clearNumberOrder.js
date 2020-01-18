/**2020/1/10 author:299
 * 编辑清除位数单模块  clearNumberOrder
 */
layui.define(['jquery','layer','form','laytpl','laydate','mytable','table'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		laytpl = layui.laytpl,
		table = layui.table,
		mytable = layui.mytable,
		myutil = layui.myutil;
	
	var TPL_CLEAR = [
		'<div class="layui-form layui-form-pane" style="padding:20px;">',
			'<p style="display:none;"><button lay-submit lay-filter="sureAddClear" id="sureAddClear">确定</button></p>',
			'<div class="layui-form-item" pane>',
				'<label class="layui-form-label">时间</label>',
				'<div class="layui-input-block">',
					'<input class="layui-input" lay-verify="required" name="time" id="clearTime" type="text">',
				'</div>',
			'</div>',
			'<div class="layui-form-item" pane>',
				'<label class="layui-form-label">数量</label>',
				'<div class="layui-input-block">',
					'<input class="layui-input" lay-verify="number" name="number" ',
						'value="{{ d.number || "" }}">',
				'</div>',
			'</div>',
			'<div class="layui-form-item" pane>',
			'<label class="layui-form-label">类型</label>',
				'<div class="layui-input-block">',
					'<select name="type" value="{{ d.type || "" }}" {{ d.id?"disabled":""}}>',
						'<option value="1" {{ d.type==1?"selected":"" }}>尾数找回，进行入库</option>',
						'<option value="2" {{ d.type==2?"selected":"" }}>尾数丢失，清报财务</option>',
					'</select>',
				'</div>',
			'</div>',
			'<div class="layui-form-item" pane>',
				'<label class="layui-form-label">备注</label>',
				'<div class="layui-input-block">',
					'<input class="layui-input" name="remarks" value="{{ d.remarks || "" }}">',
					'<input type="hidden" name="id" value="{{ d.id || "" }}">',
					'<input type="hidden" name="underGoodsId" value="{{ d.underGoodsId || "" }}">',
				'</div>',
			'</div>',
		'</div>',
		].join(' ');
	
	var clearNumberOrder = {
			
	};
	
	clearNumberOrder.add = function(underGoodsId){
		clearNumberOrder.update({
			underGoodsId: underGoodsId,
		});
	};
	
	clearNumberOrder.update = function(data){
		var html = laytpl(TPL_CLEAR).render(data);
		var title = '新增尾数清算单';
		if(data.id){
			title = '修改尾数清算单';
		}
		layer.open({
			type:1,
			title: title,
			area:'auto',
			btn:['确定','取消'],
			btnAlign:'c',
			content: html,
			success:function(layerElem,layerIndex){
				laydate.render({ elem:'#clearTime',value: data.time || new Date(), type:'datetime', });
				form.render();
				form.on('submit(sureAddClear)',function(obj){
					myutil.saveAjax({
						url:'/temporaryPack/saveMantissaLiquidation',
						data: obj.field,
						success:function(){
							table.reload('tableData');
							layer.close(layerIndex);
						}
					})
				})
			},
			yes:function(){
				$('#sureAddClear').click();
			}
		})
	}
	exports('clearNumberOrder',clearNumberOrder)
})