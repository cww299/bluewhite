/** 2019/12/13
 *  author:299
 *  新增、修改申请单模块
 * 	addEditAskfor.add();
 *  addEditAskfor.update({
 *    data: editData,
 *    table: reloadTable,
 *  })
 */
layui.extend({
}).define(['jquery','layer','form','laydate','laytpl',],
	function(exports){
		"use strict";
		var $ = layui.jquery,
			form = layui.form,
			table = layui.table,
			laytpl = layui.laytpl,
			laydate = layui.laydate,
			mytable = layui.mytable,
			myutil = layui.myutil;
		
		var TPL_MAIN = `
			<div class="layui-form layui-form-pane" style="padding:10px;">
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label">申请时间</label>
			    <div class="layui-input-block">
			      <input type="text" name="time" id="askforTimeInp" lay-verify="required"  class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label">被申请人</label>
			    <div class="layui-input-block">
			      <select name="approvalUserId" id="approvalUserSelect" lay-search lay-verify="required">
			        <option value=""></option>
			      </select>
			    </div>
			  </div>
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label">申请数量</label>
			    <div class="layui-input-block">
			      <input type="text" name="number" lay-verify="number" value="{{ d.number || ""}}" 
			      		placeholder="请输入申请数量"  class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label">申请原因</label>
			    <div class="layui-input-block">
			      <input type="text" name="cause" placeholder="请输入申请原因"  value="{{ d.cause || ""}}" 
			      	class="layui-input">
			      	<input type="hidden" name="id" value="{{ d.id || ""}}" >
			    </div>
			  </div>
			  <p style="display:none;">
			  	<span lay-submit lay-filter="sureAddEditSave" id="sureAddEditSave">确定保存</span>
			  </p>
			</div>
		`;
		var addEditAskfor = {
			
		};
		var BASE_OPT = {
			data:{ },
		},allUserSelect = '';
		
		addEditAskfor.add = function(opt){
			openWin($.entend({},BASE_OPT,opt));
		};
		
		addEditAskfor.update = function(opt){
			openWin(opt);
		};
		
		function openWin(opt){
			var title = '新增申请单',data = opt.data,html = '';
			if(data.id){
				title = '修改申请单';
			}
			laytpl(TPL_MAIN).render(data,function(h){
				html = h;
			})
			var addEditWin = layer.open({
				type: 1,
				title: title,
				area: ['500px','50%'],
				btn: ['保存','取消'],
				btnAlign:'c',
				content: html,
				success:function(){
					laydate.render({ elem:'#askforTimeInp',value: (data.time || new Date())  });
					$('#approvalUserSelect').append(allUserSelect);
					if(data.id){
						$('#approvalUserSelect').val(data.approvalUser.id);
					}
					form.on('submit(sureAddEditSave)',function(obj){
						myutil.saveAjax({
							url:'/ledger/dispatch/updateApplyVoucher',
							data: obj.field,
							success:function(){
								layer.close(addEditWin);
								addEditAskfor.table && table.reload(addEditAskfor.table);
							}
						})
					})
					form.render();
				},
				yes:function(){
					$('#sureAddEditSave').click();
				}
			})
		}
		
		addEditAskfor.init = function(){
			myutil.getData({
				url: myutil.config.ctx + '/system/user/pages?size=9999&quit=0',
				success: function(data){
					layui.each(data,function(index,item){
						allUserSelect += '<option value="'+item.id+'">'+item.userName+'</option>';
					})
				}
			})
		}
		exports('addEditAskfor',addEditAskfor);
})