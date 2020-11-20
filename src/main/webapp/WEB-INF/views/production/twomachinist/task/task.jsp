<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<title>二楼机工任务管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/formSelect/formSelects-v4.css" />
	<style>
	.aBtn {
	    color: #108ee9;
    	cursor: pointer;
   	}
   	.aBtn:hover {
   	 	color: #108ee9 !important;
   	}
    </style>
</head>
<body>
  <div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td></td>
				<td><input type="text" name="orderTimeBegin" class="layui-input" id="searchTime" autocomplete="off"
					 placeholder="创建时间"></td>
			    <td></td>
				<td><input type="text" name="taskNumber" class="layui-input" placeholder="任务编号"></td>
				<td></td>
				<td><input type="text" name="productName" class="layui-input" placeholder="产品名称"></td>
				<td></td>
				<td><input type="text" name="processesName" class="layui-input" placeholder="工序名称"></td>
				<td></td>
				<td style="width:120px;"><select name="type"><option value="">任务类型</option>
										<option value="0">产品</option>
										<option value="1">工序</option></select></td>
				<td></td>
				<td style="width:120px;"><select name="status"><option value="">任务状态</option>
										<option value="0">未分配 </option>
										<option value="1">进行中</option>
										<option value="2">完成</option></select></td>
				<td></td>
				<td><span class="layui-btn layui-btn-" lay-submit lay-filter="search">搜索</span></td>
				<td></td>
				<td><span class="layui-btn layui-btn-primary" id="warnBtn">交付预警<b id="warnNum"></b></span></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
  </div>
</body>
<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<script>
var TPL = [
'<div class="layui-form layui-form-pane" style="padding:5px;">',
	'<p style="display:none;"><button lay-submit lay-filter="sureAddTask" id="sureAddTask">确定</button></p>',
	'<div class="layui-form-item" pane>',
		'<label class="layui-form-label"><b class="red">*</b>任务产品</label>',
		'<div class="layui-input-block">',
			'<input class="layui-input" id="productNameInput" lay-verify="required" placeholder="点击进行选择商品" ', 
				'name="productName" readonly  {{ d.isEdit ? "disabled" : ""}}',
				' style="cursor: {{ d.isEdit ? "not-allowed" : "pointer"}};" value="{{ d.productName || ""}}">',
		'</div>',
	'</div>',
	'<div class="layui-form-item" pane>',
		'<label class="layui-form-label">任务类型</label>',
		'<div class="layui-input-block">',
			'<input type="radio" name="type" lay-filter="taskType" value="0" title="按产品" ',
			 	'{{ d.type ? "" : "checked"}} {{ d.isEdit ? "disabled" : ""}}>',
      		'<input type="radio" name="type" lay-filter="taskType" value="1" title="按工序" ',
      			'{{ d.type ? "checked" : ""}} disabled>',
		'</div>',
	'</div>',
	'<div class="layui-form-item" pane id="processContent" style="display: {{ d.type ? "block" : "none"}};">',
		'<label class="layui-form-label"><b class="red">*</b>产品工序</label>',
		'<div class="layui-input-block">',
			'{{# if(!d.isEdit) { }}',
			    '<select id="processSelect" xm-select="processSelect" xm-select-show-count="3">',
			    	'<option value="">请选择</option>',
				'</select>',
			'{{# } else { }}',
				'<input class="layui-input" value="{{ d.processesName || ""}}" readonly style="cursor:not-allowed">',
			'{{# } }}',
		'</div>',
	'</div>',
	'<div class="layui-form-item" pane>',
		'<label class="layui-form-label"><b class="red">*</b>任务数量</label>',
		'<div class="layui-input-block">',
			'<input class="layui-input" name="number" autocomplete="off" lay-verify="number" value="{{ d.number || ""}}">',
		'</div>',
	'</div>',
	'<div class="layui-form-item" pane id="timeSecondContent">',
		'<label class="layui-form-label"><b class="red">*</b>预计耗时</label>',
		'<div class="layui-input-inline">',
			'<input class="layui-input" name="timeSecond" autocomplete="off" id="timeSecondInput"',
				' value="{{ d.timeSecond || ""}}" lay-verify="number">',
		'</div>',
		'<div class="layui-form-mid layui-word-aux">单个预计耗时/秒</div>',
	'</div>',
	'<div class="layui-form-item" pane>',
		'<label class="layui-form-label">备注</label>',
		'<div class="layui-input-block">',
			'<input class="layui-input" name="remark" autocomplete="off" value="{{ d.remark || "" }}">',
		'</div>',
	'</div>',
	'<input type="hidden" name="id" value="{{ d.id || ""}}">',
	'<input type="hidden" name="status" value="{{ d.status || 0}}">',
	'<input type="hidden" name="productId" value="{{ d.productId || ""}}" id="hiddenProductId">',
'</div>',
].join(' ');
var TPL_ALLOCATION = [
'<div class="layui-form layui-form-pane" style="padding:5px;">',
	'<p style="display:none;"><button lay-submit lay-filter="sureAddAllocation" id="sureAddAllocation">确定</button></p>',
	'<div class="layui-form-item" pane>',
		'<label class="layui-form-label"><b class="red">*</b>分配员工</label>',
		'<div class="layui-input-block">',
		    '<select id="userSelect" xm-select="userSelect" xm-select-show-count="10">',
		    	'<option value="">请选择</option>',
			'</select>',
		'</div>',
	'</div>',
	'<div class="layui-form-item" pane>',
		'<label class="layui-form-label"><b class="red">*</b>分配数量</label>',
		'<div class="layui-input-inline">',
			'<input class="layui-input" name="number" autocomplete="off" lay-verify="number" value="{{ d.number || ""}}">',
		'</div>',
		'<div class="layui-form-mid layui-word-aux">当前剩余未分配：{{ d.surplusNumber || 0 }}</div>',
	'</div>',
	'<input type="hidden" name="id" value="{{ d.id || ""}}">',
	'<input type="hidden" name="taskId" value="{{ d.taskId || ""}}">',
	'<input type="hidden" name="status" value="{{ d.status || 0}}">',
'</div>',
].join(' ');
var TPL_CHOOSE_PRODUCT = [
'<div style="padding:10px;">',
	'<table class="layui-form searchTable"><tr>',
		'<td>产品编号：</td>',
		'<td><input type="text" name="number" class="layui-input"></td>',
		'<td>产品名：</td>',
		'<td><input type="text" name="name" class="layui-input"></td>',
		'<td><span class="layui-btn" lay-submit lay-filter="searchProductBtn">搜索</span></td>',
		'<td></td>',
		'<td><span class="layui-badge">双击进行选择</span></td>',
	'</tr></table>',
	'<table id="productTable" lay-filter="productTable"></table>',
'</div>'
].join(' ');
var TPL_TIME_LINE = [
'<div style="padding: 10px 30px;">',
  	'<ul class="layui-timeline">',
  	  '{{# layui.each(d, function(index, item){ }}',
		  '<li class="layui-timeline-item">',
		    '<i class="layui-icon layui-timeline-axis">&#xe63f;</i>',
		    '<div class="layui-timeline-content layui-text">',
		      '<h3 class="layui-timeline-title">{{ item.createdAt }}</h3>',
		      '<p>',
		        '内容：{{ item.remark }}',
		        '<br>数量：{{ item.number }} /个',
		        '<br>耗时：{{ item.timeMin }} /分钟',
		        '{{# if (item.userNames) { }}',
			        '<br>人员：{{ item.userNames }}',
		        '{{# } }}',
		      '</p>',
		    '</div>',
		  '</li>',
	  '{{# }) }}',
	'</ul>',
'</div>',
].join(' ')

layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
	formSelects : 'formSelect/formSelects-v4'
}).define(['mytable','laydate', 'formSelects'],function(){
	var $ = layui.jquery
	, layer = layui.layer 				
	, form = layui.form			 		
	, table = layui.table 
	, laydate = layui.laydate
	, myutil = layui.myutil
	, laytpl = layui.laytpl
	, mytable = layui.mytable
	, formSelects = layui.formSelects
	,tablePlug = layui.tablePlug; 
	myutil.config.ctx = '${ctx}';
	myutil.clickTr();
	laydate.render({ elem:'#searchTime', range:'~' });
	layui.tablePlug.smartReload.enable(true);
	var cols = [
       { type:'checkbox',},
       { title:'任务编号',   field:'taskNumber', width: 125, event: "look",
    	   templet: function(d) { return '<a class="aBtn">' + d.taskNumber + '</a>'}},
       { title:'产品/工序名称',   field:'productName', templet: function(d) {
    	   return d.productName + (d.processesName ? (' -- ' + d.processesName) : "");
       }},
       { title:'类型',   field:'type', width:110, transData: {data:['产品', '工序']}, width: 90 },
       { title:'任务数量',   field:'number', width: 115,},
       { title:'预计耗时/分',   field:'timeMin', width: 115, },
       { title:'当前完成数量',   field:'finishNumber', width: 115, },
       { title:'当前耗时/分',   field:'currTimeMin', width: 115, },
       { title:'未分配数量',   field:'surplusNumber', width: 115, },
       // { title:'已交付数量',   field:'deliverNumber', width: 115, },
       { title:'状态',   field:'status', width:110, transData: {data:['未分配 ', '进行中', '完成']} },
       { title:'备注',   field:'remark', width:150, },
   	];
	mytable.render({
		elem:'#tableData',
		url: myutil.config.ctx+'/tasks/list',
		smartReloadModel: true,
		cols:[cols],
		toolbar: [
			'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="add">新增任务</span>',
			'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="edit">修改任务</span>',
			'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="deliver">交付计划</span>',
			'<span class="layui-btn layui-btn-sm layui-btn-primary" lay-event="allocation">分配任务</span>'
		].join(' '),
		autoUpdate:{
			deleUrl:'/tasks/delete',
		},
		curd:{
			btn:[4],
			otherBtn: function(obj){
				var check = layui.table.checkStatus('tableData').data;
				if(obj.event == 'add'){
					addEdit();
				} else if(obj.event == 'edit'){
					if(check.length!=1)
						return myutil.emsg('只能选择一条数据编辑');
					addEdit(check[0]);
				} else if(obj.event == 'deliver') {
					if(check.length!=1)
						return myutil.emsg('只能选择一条数据交付');
					warnWin(check[0]);
				} else if(obj.event == 'allocation') {
					if(check.length!=1)
						return myutil.emsg('只能选择一条数据分配');
					allocation(check[0]);
				}
			},
		},
	})
	
	table.on('tool(tableData)', function(obj) {
		if (obj.event == 'look') {
			lookTimeLine(obj.data)
		}
	})
	myutil.getData({
		url: myutil.config.ctx + '/taskDeliver/warn',
		success: function(data) {
			if(data) {
				warnWin();
				$('#warnNum').html('<span class="layui-badge">' + data + '</span>');
			}
		}
	})
	
	$('#warnBtn').click(function() {
		warnWin();
	})
	var userHtml = '';
	myutil.getData({
		url: myutil.config.ctx + '/system/user/pages?size=999&orgNameIds=76&quit=0',
		success: function(users) {
			users.forEach(function(user) {
				userHtml += [
					'<option value="'+ user.id +'">',
						user.userName,
					'</option>',
				].join(' ');
			})
		}
	})
	function allocation(data) {
		layer.open({
			type: 1,
			area:['500px','450px'],
			offset: '50px',
			title: '任务分配',
			content: laytpl(TPL_ALLOCATION).render({ taskId: data.id, surplusNumber: data.surplusNumber }),
			btn:['保存','取消'],
			success: function(layerElem, layerIndex) {
				$('#userSelect').append(userHtml);
				formSelects.render();
				form.on('submit(sureAddAllocation)',function(obj){
					if(!isCount(obj.field.number) || data.surplusNumber < obj.field.number) {
						return myutil.emsg('清正确填写分配数量！');
					}
					// [{name: "鲍文梅", value: "42"}]
					var users = formSelects.value('userSelect');
					if (users.length == 0) {
						return myutil.emsg('请选择分配人员');
					}
					var userIds = [], userNames = [];
					users.forEach(function(user) {
						userIds.push(user.value);
						userNames.push(user.name);
					})
					obj.field.userIds = userIds.join(',');
					obj.field.userNames = userNames.join('/')
					myutil.saveAjax({
						url:'/taskAllocation/save',
						data: obj.field,
						success:function(){
							table.reload('tableData')
							layer.close(layerIndex);
						}
					})
				})
			},
			yes:function(){
				$('#sureAddAllocation').click();
			}
		})
		
		
	}
	
	function lookTimeLine(data) {
		myutil.getData({
			url: myutil.config.ctx + '/taskProcess/all?taskId=' + data.id,
			success: function(process) {
				process.push({
					createdAt: data.createdAt,
					number: data.number,
					timeMin: 0,
					type: -1,
					remark: '创建任务',
				})
				layer.open({
					type:1,
					area:['400px','600px'],
					offset: '50px',
					shadeClose: true,
					title: '任务进度：        <b class="red">' + data.taskNumber + '</b>',
					content: laytpl(TPL_TIME_LINE).render(process),
				})
			}
		})
	}
	
	function addEdit(data) {
		var isEdit = !!data;
		data = data || {};
		if (data.status == 2) {
			return myutil.emsg('任务已完成，无法修改');
		}
		var title = isEdit ? '修改任务' : '新增任务';
		data.isEdit = isEdit;
		var layerIndex = layer.open({
			type:1,
			area:['500px','600px'],
			offset: '50px',
			title: title,
			content: laytpl(TPL).render(data),
			btn:['保存','取消'],
			success: function(layerElem, layerIndex){
				openDoneRender();
				form.render();
				formSelects.render();
			},
			yes:function(){
				$('#sureAddTask').click();
			}
		})
		function openDoneRender() {
			form.on('submit(sureAddTask)',function(obj){
				if(!isCount(obj.field.number))
					return myutil.emsg('请正确填写任务数量！');
				if(!isPrice(obj.field.timeSecond))
					return myutil.emsg('请正确填写预计耗时！');
				if (obj.field.type == 1 && !isEdit) {
					// 1308~拼膀子~42.75,1310~拼嘴~11
					var processStr = formSelects.value('processSelect', 'valStr');
					if (!processStr) {
						return myutil.emsg('工序不能为空！');
					}
					var process = processStr.split(',');
					var processesIds = [], processesNames = [], timeSeconds = [];
					process.forEach(function(d) {
						var arr = d.split('~');
						processesIds.push(arr[0]);
						processesNames.push(arr[1]);
						timeSeconds.push(arr[2]);
					})
					obj.field.processesIds = processesIds.join(',');
					obj.field.processesNames = processesNames.join(',');
					obj.field.timeSeconds = timeSeconds.join(',');
				}
				myutil.saveAjax({
					url:'/tasks/save',
					data: obj.field,
					success:function(){
						table.reload('tableData')
						layer.close(layerIndex);
					}
				})
			})
			form.on('radio(taskType)', function(data){
				if (data.value == 1) {
					$('#processContent').show();
					$('#timeSecondContent').hide();
					if(!$('#timeSecondInput').val()) {
						$('#timeSecondInput').val('0');
					}
				} else {
					$('#processContent').hide();
					$('#timeSecondContent').show();
				}
			});
			$('#productNameInput').unbind().on('click',function(){
				layer.open({
					type:1,
					area:['720px','520px'],
					title:'商品选择',
					content: TPL_CHOOSE_PRODUCT,
					success:function(layerProduct, layerIndexProduct){
						mytable.render({
							elem: '#productTable',
							url: myutil.config.ctx + '/productPages?type=2',
							size:'sm',
							cols:[[
								{ title:'商品编号', field:'number',},
								{ title:'商品名称', field:'name',},
							]],
						})
						table.on('rowDouble(productTable)', function(obj){
							var trData = obj.data;
							$('#hiddenProductId').val(trData.id);
							$('#productNameInput').val(trData.name);
							layer.close(layerIndexProduct);
							myutil.getData({
								url: myutil.config.ctx + '/production/getProcedure?type=4&flag=0&productId=' + trData.id,
								success: function(data) {
									if (data.length) {
										$('[lay-filter="taskType"][value="1"]').attr('disabled', false);
										var html = '';
										var allTime = 0.0;
										data.forEach(function(d){
											allTime += d.workingTime;
											html += ['<option value="' + 
												d.id + '~' + d.name + '~' + d.workingTime + '">',
												d.name + '(耗时：' + d.workingTime + ')</option>'].join('');
										})
										$('[name="timeSecond"]').val(allTime);
										$('#processSelect').html(html);
									} else {
										$('[lay-filter="taskType"][value="0"]').prop('checked', 'checked');
										$('[lay-filter="taskType"][value="1"]').attr('disabled', true);
										$('#processSelect').html('');
										
									}
									formSelects.render();
									form.render();
								}
							})
						});
						form.on('submit(searchProductBtn)',function(obj){
							table.reload('productTable',{
								where: obj.field,
								page: {
									curr: 1,
								}
							})
						})
					}
				})
			})
		}
	}
	
	function warnWin(data) {
		var isAdd = !!data;
		layer.open({
			type: 1,
			title: '交付预警     ' + (data ? ('<b class="red">'+data.taskNumber+'</b>') : ''),
			offset: '80px',
			area: ['70%', '70%'],
			shadeClose: true,
			content: [
				'<div style="padding: 10px;">',
					'<table class="layui-form searchTable">',
					  '<tr>',
						'<td></td>',
						'<td><input type="text" name="taskNumber" class="layui-input" autocomplete="off"',
							 'placeholder="任务编号"></td>',
					    '<td></td>',
					    '<td><input type="text" name="productName" class="layui-input" autocomplete="off"',
						 	'placeholder="产品/工序名称"></td>',
					 	'<td></td>',
						'<td><span class="layui-btn layui-btn-" lay-submit lay-filter="searchDeliver">搜索</span></td>',
						'<td></td>',
						'<td style="color:gray;">'+ (isAdd ? ('任务数量：' + data.number) : '') +'</td>',
					  '</tr>',
					'</table>',
					'<table id="warnTable" lay-filter="warnTable"></table>',
				'</div>',
			].join(' '),
			success: function() {
				data = data || {};
				var addTemp = { 
					taskId: data.id, number: 0, taskNumber: data.taskNumber,
					deliverDate: myutil.getSubDay(0), remark: '', 
					productName: data.productName + (data.processesName ? (' -- ' + data.processesName) : ""),  }
				mytable.render({
					elem:'#warnTable',
					url: myutil.config.ctx + '/taskDeliver' + (isAdd ? '/list' : '/warnList'),
					smartReloadModel: true,
					autoUpdate: {
						saveUrl: '/taskDeliver/save',
						deleUrl:'/taskDeliver/delete',
					},
					where: {
						taskId: data.id,
					},
					curd: {
						btn: isAdd ? [1,2,3,4] : [4],
						addTemp: addTemp,
					},
					totalRow: isAdd ? ['number'] : [],
					verify: {
						count: ['number'],
					},
					cols:[[
					   { type: 'checkbox' },
					   { title: '交付日期',   field:'deliverDate', width: 115, type: 'date', edit: true },
				       { title: '交付数量',   field:'number', width: 115, edit: true },
					   { title: '任务编号',   field:'taskNumber', width: 125, edit: false },
					   { title: '产品/工序名称',   field:'productName',  edit: false },
				       { title: '备注',   field:'remark', edit: true },
					]],
					done: function(res, t) {
						if (!isAdd) {
							if (res.count) {
								$('#warnNum').html('<span class="layui-badge">' + res.count + '</span>');
							} else {
								$('#warnNum').html('');
							}
						}
					}
				})
				form.on('submit(searchDeliver)',function(obj){
					table.reload('warnTable',{
						where: obj.field,
						page:{ curr:1 },
					})
				}) 
			}
		})	
	}
	function isCount(val){	//验证是否为正整数
		var res = true;
		isNaN(val) && (res=false);
		val<0 && (res=false);
		(val%1.0!=0.0) && (res=false);
		return res;
	}
	function isPrice(val){	//是否为合法数字
		var res = true;
		isNaN(val) && (res=false);
		val<0 && (res=false);
		return res;
	}
	form.on('submit(search)',function(obj){
		var field = obj.field;
		if(field.orderTimeBegin){
			var t = field.orderTimeBegin.split(' ~ ');
			field.orderTimeBegin = t[0]+' 00:00:00';
			field.orderTimeEnd = t[1]+' 23:59:59';
		}else
			field.orderTimeEnd = '';
		table.reload('tableData',{
			where: obj.field,
			page:{ curr:1 },
		})
	}) 
})
</script>
</html>