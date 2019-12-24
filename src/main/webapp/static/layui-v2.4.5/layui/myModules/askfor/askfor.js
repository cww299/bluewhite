/**2019/12/240 
 * author:299
 * 申请模块 askfor
 */
layui.extend({
	//formSelects : 'formSelect/formSelects-v4'
}).define(['jquery','layer','form','laytpl','laydate','mytable','table'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		laytpl = layui.laytpl,
		mytable = layui.mytable,
		table = layui.table,
		myutil = layui.myutil;
	var TPL_MAIN = `
		<div class="layui-form layui-form-pane" style="padding:10px;">
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">申请时间</label>
		    <div class="layui-input-block">
		      <input type="text" name="time" required  lay-verify="required" id="askforTime"
		       autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">申请原因</label>
		    <div class="layui-input-block">
		      <input type="text" name="cause" value="{{ d.cause || '' }}" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">被申请人</label>
		    <div class="layui-input-block">
		      <select name="approvalUserId" id="approvalUserId">
		        <option value="">请选择</option>
		      </select>
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label">申请数量</label>
		    <div class="layui-input-block">
		      <input type="number" name="number" required  lay-verify="required" value="{{ d.number || ''}}"
		       autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <p style="display:none;">
		  	<input type="hidden" name="applyVoucherTypeId" value="{{ d.applyVoucherTypeId }}">
		  	<input type="hidden" name="applyVoucherKindId" value="{{ d.applyVoucherKindId }}">
		  	<span lay-submit lay-filter="saveAskforBtn" id="saveAskforBtn">11</span>
		  </p>
		</div>
	`;
	var askfor = {
		type: 0,
		kind: 0,
		allType:{
			sale: {			//销售申请
				id: 438,
				child:{
					borrow: 441,	//借货
				}
			},	
			outInput: {		//入库出库申请
				id: 439,
				child:{
					allot: 464,			//调拨
					returnWork: 442,	//反工
					loss: 463,			//盘亏
					profit: 470,		//盘盈
					returnGood:465,		//退货
					changeGood:466,		//换货
				}
			},	
		},
	};
	var allUserSelect = '';
	askfor.add = function(opt){
		opt = opt || {};
		opt.data = opt.data || {};
		askfor.upload(opt);
	}
	askfor.upload = function(opt){
		var renderData = opt.data;
		renderData.applyVoucherTypeId = askfor.type;
		renderData.applyVoucherKindId = askfor.kind;
		var html = '';
		laytpl(TPL_MAIN).render(renderData,function(h){
			html = h;
		})
		var title = '新增申请单';
		if(renderData.id)
			title = '修改申请单';
		layer.open({
			type:1,
			title:title,
			content: html,
			area:['420px','380px'],
			btn:['确定','取消'],
			btnAlign:'c',
			yes:function(){
				$('#saveAskforBtn').click();
			},
			success:function(layero, layerIndex){
				$('#approvalUserId').append(allUserSelect);
				form.render();
				form.on('submit(saveAskforBtn)',function(obj){
					myutil.saveAjax({
						url: '/ledger/dispatch/saveApplyVoucher',
						data: obj.field,
						success:function(){
							layer.close(layerIndex);
						}
					})
				})
			},
		})
	}
	askfor.init = function(){
		myutil.getData({
			url: myutil.config.ctx + '/system/user/findUserList?quit=0',
			success: function(data){
				layui.each(data,function(index,item){
					allUserSelect += '<option value="'+item.id+'">'+item.userName+'</option>';
				})
			}
		})
	}
	exports('askfor',askfor);
})