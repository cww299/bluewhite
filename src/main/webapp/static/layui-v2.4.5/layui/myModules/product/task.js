/**生产管理任务管理模块 task
 * 2020/1/14   auhor:299
 * product.render({
 * 	type:'',  //引用类型
 *  ctx:'',	  //前缀路径
 *  elem:'',  //绑定元素 默认app
 * })
 * type: 一楼质检1，一楼包装2，二楼针工3，二楼机工4，八号裁剪5
 */
layui.base({
	base: 'static/layui-v2.4.5/',
}).extend({
	mytable: 'layui/myModules/mytable',
}).define(['jquery','table','form','mytable','laytpl','laydate','upload'],function(exports){
	"use strict";
	var $ = layui.jquery
	, table = layui.table 
	, form = layui.form
	, upload = layui.upload
	, myutil = layui.myutil
	, laytpl = layui.laytpl
	, laydate = layui.laydate
	, mytable = layui.mytable
	, MODNAME = 'task'
	, task = {
		type: 1,
	}
	, Class = function(opt){
		this.render(opt);
	};
	
	var TPL_MAIN = [
	'<div class="layui-card">',
		'<div class="layui-card-body">',
			'<table class="layui-form searchTable">',
				'<tr>',
					'<td>时间：</td>',
					'<td><input type="text" class="layui-input" name="orderTimeBegin" id="searchTime"></td>',
					'<td>产品名：</td>',
					'<td><input type="text" class="layui-input" name="productName"></td>',
					'<td>批次号：</td>',
					'<td><input type="text" class="layui-input" name="bacthNumber"></td>',
					'<td>工序名称：</td>',
					'<td><input type="text" class="layui-input" name="procedureName"></td>',
					'<td>工序：</td>',
					'<td><select></select></td>',
					'<td></td>',
					'<td><span class="layui-btn layui-btn-" lay-submit lay-filter="searchBtn">搜索</span></td>',
				'</tr>',
			'</table>',
			'<table id="tableData" lay-filter="tableData"></table>',
		'</div>',
	'</div>',
	].join(' ');
	var cols = [
		{ type:'checkbox', fixed:'left' },
		{ title:'任务编号',field:'id', },
		{ title:'批次号',field:'bacthNumber', },
		{ title:'产品名',field:'productName', },
		{ title:'时间',field:'allotTime', },
		{ title:'工序',field:'procedureName', },
		{ title:'预计时间',field:'expectTime', },
		{ title:'任务价值',field:'expectTaskPrice', },
		{ title:'b工资净值',field:'payB', },
		{ title:'数量',field:'number', },
	];
	var tableId = 'tableData',defaultElem = '#app';
	Class.prototype.render = function(opt){
		$(defaultElem).append(TPL_MAIN);
		laydate.render({elem:'#searchTime',range:'~'});
		myutil.clickTr();
		form.render();
		mytable.render({
			elem: '#'+tableId,
			url: '/task/allTask?type='+task.type,
			toolbar:[
				'<span class="layui-btn layui-btn-sm" lay-event="lookover">查看人员</span>',
				(function(){
					if(task.type==2)
						return '<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="lookoverPrice">查看加价</span>';
					return '';
				})(),
			].join(' '),
			curd:{
				btn:[4],
				otherBtn:function(obj){
					var check = layui.table.checkStatus(tableId).data;
					if(obj.event=='lookover'){
						if(check.length!=1)
							return myutil.emsg('请选择一条数据进行操作！');
						lookoverPeople(check[0].id);
					}else if(obj.event=='lookoverPrice'){
						if(check.length!=1)
							return myutil.emsg('请选择一条数据进行操作！');
						lookoverPrice(check[0].id);
					}
				}
			},
			autoUpdate:{
				deleteUrl: '/task/delete?type='+task.type,
				saveUrl: '/task/upTask',
			},
			cols: [cols],
		})
		form.on('submit(searchBtn)',function(obj){
			var field = obj.field;
			if(field.orderTimeBegin){
				var t = field.orderTimeBegin.split(' ~ ');
				field.orderTimeBegin = t[0]+' 00:00:00';
				field.orderTimeEnd = t[1]+' 23:59:59';
			}else
				field.orderTimeEnd = '';
			table.reload(tableId,{
				where: field,
				page:{
					curr:1,
				}
			})
		})
	}
	function lookover(id,isPeople){
		layer.open({
			type:1,
			title: isPeople?'查看人员':'查看加价',
			area:['400px','600px'],
			shadeClose: true,
			content:[
				'<div style="padding:10px 0;">',
					'<table id="lookoverTable" lay-filter="lookoverTable"></table>',
				'</div>',
			].join(' '),
			success:function(){
				mytable.renderNoPage({
					elem:'#lookoverTable',
					url:isPeople?'/task/taskUser?id='+id:'/task/getUserPerformance?id='+id,
					cols:[
						(function(){
							return isPeople?[
								{ type:'checkbox' },
								{ title:'员工姓名',field:'userName', },
							]:[
								{ type:'checkbox' },
								{ title:'员工姓名',field:'', },
							];
						})(),
					]
				})
			}
		})
	};
	task.render = function(opt){
		myutil.config.ctx = opt.ctx;
		task.type = opt.type;
		new Class(opt);
	}
})