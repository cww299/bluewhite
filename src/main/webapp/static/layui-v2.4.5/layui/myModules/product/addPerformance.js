/**2020/1/14  author:299
 * 加绩模块  addPerformance
 * addPerformance.addEdit({
 *     table: '获取数据的表格',
 *     type: 1,  //1.任务加绩。2.杂工加绩
 * })
 */
layui.base({
}).extend({
}).define(['laytpl','form'],function(exports){
	"use strict";
	var $ = layui.jquery
	, table = layui.table 
	, form = layui.form
	, myutil = layui.myutil
	, laytpl = layui.laytpl
	, laydate = layui.laydate
	, mytable = layui.mytable
	, MODNAME = 'addPerformance'
	, addPerformance = {
		type: 1,
	};
	
	var MAIN_TPL = [
	'<div style="padding:10px;">',
		'<table>',
			'<tr>',
				'<td>工序：</td>',
				'<td><input type="text" class="layui-input" disabled id="procedureName" value="{{ }}"></td>',
				'<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>',
				'<td>加绩工序：</td>',
				'<td>',
					'<select name="performance" id="performance" lay-verify="required">',
						'<option value="">请选择</option></select>',
					'<input type="hidden" name="update" value="1">',
					'<input type="hidden" name="taskIds" id="taskIds">',
				'</td>',
			'</tr>',
		'</table>',
		'<div>',
			'<ul class="">',
				'{{#',
					'layui.each(d,function(index,item){',
					
					
					'})',
				'}}',
			'</ul>',
		'</div>',
	'</div>',
	].join(' ');
	
	
	addPerformance.addEdit = function(opt){
		opt = opt || { type:1, };	//默认任务加绩
		var check = table.checkStatus(opt.table || 'tableData').data;
		if(check.length==0)
			myutil.emsg('请选择信息进行加绩！');
		var isAdd = 0;
		for(var i=0;i<check.length;i++){
			if(!check[i].performancePrice)
				isAdd++;
		}
		if(isAdd!=check.length)
			return myutil.emsg('无法同时新增修改加绩!');
		
		///task/pickTaskPerformance   获取所有工序    传参    id
		///farragoTask/taskUser      获取所有人员   传参  id
		///farragoTask/getUserPerformance		获取修改  传参  id
	};
	
	function addEdit(data){
		
	}
	
	exports(MODNAME,addPerformance);
})