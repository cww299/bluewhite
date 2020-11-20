<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<title>二楼机工任务分配管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
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
		<td><input type="text" name="taskNumber" class="layui-input" placeholder="任务编号"></td>
		<td></td>
		<td><input type="text" name="productName" class="layui-input" placeholder="产品名称"></td>
		<td></td>
		<td><input type="text" name="processesName" class="layui-input" placeholder="工序名称"></td>
		<td></td>
		<td><input type="text" name="userNames" class="layui-input" placeholder="分配人员"></td>
		<td></td>
		<td style="width:120px;"><select name="status"><option value="">任务状态</option>
								<option value="0">进行中 </option>
								<option value="1">暂停</option>
								<option value="2">结束</option></select></td>
		<td></td>
		<td><span class="layui-btn layui-btn-" lay-submit lay-filter="search">搜索</span></td>
	</tr>
	</table>
	<table id="tableData" lay-filter="tableData"></table>
 </div>
</div>
</body>
<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<script>
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
}).define(['mytable','laydate'],function(){
	var $ = layui.jquery
	, layer = layui.layer 				
	, form = layui.form			 		
	, table = layui.table 
	, laydate = layui.laydate
	, myutil = layui.myutil
	, laytpl = layui.laytpl
	, mytable = layui.mytable
	,tablePlug = layui.tablePlug; 
	myutil.config.ctx = '${ctx}';
	myutil.clickTr();
	
	var cols = [
       { type:'checkbox',},
       { title:'分配日期',   field:'createdAt', type: 'date',  width: 120, },
       { title:'任务编号',   field:'taskNumber', width: 125, event: "look",
    	   templet: function(d) { return '<a class="aBtn">' + d.task.taskNumber + '</a>'}},
       { title:'产品/工序名称',   field:'task_productName', templet: function(d) {
    	   return d.task.productName + (d.task.processesName ? (' -- ' + d.task.processesName) : "");
       }},
       { title:'分配员工',   field:'userNames', width: 200, },
       { title:'分配数量',   field:'number', width: 115, },
       { title:'完成数量',   field:'finishNumber', width: 115,},
       { title:'退回数量',   field:'returnNumber', width: 115, },
       { title:'剩余数量',   field:'surplusNumber', width:110, width: 90 },
       { title:'状态',   field:'status', width:110, transData: {data:['进行中 ', '暂停', '结束']} },
   	];
	mytable.render({
		elem:'#tableData',
		url: myutil.config.ctx+'/taskAllocation/list',
		smartReloadModel: true,
		cols:[cols],
		toolbar: [
			'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="pause">暂停任务</span>',
			'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="start">开始任务</span>',
			'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="finish">完成</span>',
			'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="returns">退回</span>',
			'<span class="layui-btn layui-btn-sm layui-btn-primary" lay-event="finishs">一键完成</span>'
		].join(' '),
		autoUpdate:{
			deleUrl:'/taskAllocation/delete',
		},
		curd:{
			btn:[4],
			otherBtn: function(obj){
				var check = layui.table.checkStatus('tableData').data;
				var event = obj.event;
				if(event == 'returns' || event == 'finish'){
					if(check.length!=1)
						return myutil.emsg('请选择一条数据');
					var text = { 'returns': '退回', 'finish': '完成' };
					returnsOrFinish(text[event], function(number, layerIndex) {
						myutil.saveAjax({
							url: '/taskAllocation/' + event,
							data: { allocationId: check[0].id, number: number, },
							success: function(){
								layer.close(layerIndex);
								table.reload('tableData');
							}
						})
					})
				} else if(event == 'pause' || event == 'start' || event == 'finishs') {
					var text = { 'pause': '暂停', 'start': '开始', 'finishs': '一键完成' };
					myutil.deleTableIds({
						text: '是否确认' + text[event] +'任务？',
						url: '/taskAllocation/' + event,
						type: event == 'pause' ? 'post' : 'get'
					})
				}
			},
		},
	})
	
	function returnsOrFinish(text, done) {
		layer.open({
			type:1,
			area:['350px','180px'],
			offset: '100px',
			title: text + '任务',
			content: laytpl([
				'<div style="padding:10px;">',
					'<p><b class="red">*</b>请填写{{ d }}数量</p>',
					'<input class="layui-input" id="inputNumber">',
				'</div>',
			].join('')).render(text),
			btn:['确认', '取消'],
			yes:function(layerIndex){
				var number = $('#inputNumber').val();
				if (number <= 0) {
					return myutil.emsg('请正确填写' + text + '数量');
				}
				done && done(number, layerIndex);
			}
		})
	}
	table.on('tool(tableData)', function(obj) {
		if (obj.event == 'look') {
			lookTimeLine(obj.data)
		}
	})
	
	function lookTimeLine(data) {
		myutil.getData({
			url: myutil.config.ctx + '/taskProcess/all?taskAllocationId=' + data.id,
			success: function(process) {
				layer.open({
					type:1,
					area:['400px','600px'],
					offset: '50px',
					shadeClose: true,
					title: '任务进度：        <b class="red">' + data.task.taskNumber + '</b>',
					content: laytpl(TPL_TIME_LINE).render(process),
				})
			}
		})
	}
	
	form.on('submit(search)',function(obj){
		var field = obj.field;
		table.reload('tableData',{
			where: obj.field,
			page:{ curr:1 },
		})
	}) 
})
</script>
</html>