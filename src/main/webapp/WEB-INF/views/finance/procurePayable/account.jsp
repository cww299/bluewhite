<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/vendor/typeahead.js"></script>
  	<link rel="stylesheet" href="${ctx }/static/css/bootstrap.min.css"> 
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx }/static/css/main.css">  

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>报销申请</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>

<body>
<div class="layui-card">
	<div class="layui-card-body">
		<div class="layui-form layui-card-header layuiadmin-card-header-auto">
			<div class="layui-form-item">
				<table>
					<tr>
						<td>订料人:</td>
						<td><select name="userId" id="userIdSelect" lay-search></select></td>
						<td>&nbsp;&nbsp;</td>
						<td>采购内容:</td>
						<td><input type="text" name="content"  class="layui-input" /></td>
						<td>&nbsp;&nbsp;</td>
						<td><select class="form-control" name="dataType" id="selectone">
								<option value="expenseDate">付款日期</option>
								<option value="logisticsDate">到货日期</option>
							</select></td>
						<td>&nbsp;&nbsp;</td>
						<td><input id="startTime" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input">
						</td>
						<td>&nbsp;&nbsp;</td>
						<td>是否核对:
						<td><select class="form-control" name="flag">
								<option value="">请选择</option>
								<option value="0">未核对</option>
								<option value="1">已核对</option>
						</select></td>
						<td>&nbsp;&nbsp;</td>
						<td>
							<div class="layui-inline">
								<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">
									<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
								</button>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
	</div>
</div>
	
<script type="text/html" id="addEditTpl">
	<div class="layui-form" style="padding:20px;">
		<div class="layui-form-item">
			<label class="layui-form-label">批次号</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input" name="batchNumber" value="{{ d.batchNumber }}"></div></div>
		<div class="layui-form-item">
			<label class="layui-form-label">内容</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input" name="content"  value="{{ d.content }}"></div></div>
		<div class="layui-form-item">
			<label class="layui-form-label">客户</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input" id="addEditCustomName" name="customName" value="{{ d.custom.name }}">
				<input type="hidden" id="addEditCustomId" name="customId" value="{{ d.custom.id }}"></div></div>
		<div class="layui-form-item">
			<label class="layui-form-label">订料人</label>
			<div class="layui-input-block">
				<select type="text" name="userId" id="addEditUser" lay-search></select></div></div>
		<div class="layui-form-item">
			<label class="layui-form-label">金额</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input" name="money"  value="{{ d.money }}"></div></div>
		<div class="layui-form-item">
			<label class="layui-form-label">付款日期</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input" name="expenseDate" id="addEditExpenseDate" value="{{ d.expenseDate }}"></div></div>
		<div class="layui-form-item">
			<label class="layui-form-label">到货日</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input" name="logisticsDate" id="addEditLogisticsDate" value="{{ d.logisticsDate }}"></div></div>
		<input type="hidden" name="id" value="{{ d.id }}">
		<input type="hidden" name="type" value="2">
		<span id="addEditSureBtn" lay-submit lay-filter="addEditSuer" style="display:none;">隐藏确定按钮</span>
	</div>
</script>
	
<script type="text/html" id="toolbar">
	<div class="layui-btn-container layui-inline">
		<span class="layui-btn layui-btn-sm" lay-event="add">新增</span>
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑</span>
	</div>
</script>

<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug: 'tablePlug/tablePlug'
}).define(
	['tablePlug', 'laydate', 'element'],
	function() {
		var $ = layui.jquery
			,layer = layui.layer //弹层
			,form = layui.form //表单
			,table = layui.table //表格
			,laydate = layui.laydate //日期控件
			,tablePlug = layui.tablePlug //表格插件
			,laytpl = layui.laytpl;
		
		var allUser = getAllUser();
		laydate.render({ elem: '#startTime', type: 'datetime'});
		$('#userIdSelect').html(getSelectHtml(''));
		form.render();
		
		table.render({
			elem: '#tableData',
			size: 'lg',
			url: '${ctx}/fince/getConsumption?type=2', 
			request:{
				pageName: 'page' ,
				limitName: 'size' 
			},
			page: {},
			loading: true,
			toolbar: '#toolbar', 
			cellMinWidth: 90,
			colFilterRecord: true,
			smartReloadModel: true,
			parseData: function(ret) {
				return {
					code: ret.code,
					msg: ret.message,
					count:ret.data.total,
					data: ret.data.rows
				}
			},
			cols: [[
			        { align: 'center', type: 'checkbox',  fixed: 'left' }, 
			        { align: 'center', width:'10%', field: "batchNumber", 	title: "批次号",  },
			        { align: 'center',  			field: "content", 		title: "采购内容",  },
			        { align: 'center', width:'12%', field: "", 				title: "客户",   	templet:function(d){ return d.custom.name; }}, 
			        { align: 'center', width:'6%',  field: "", 				title: "订料人",   	templet:function(d){ return d.user==null?'':d.user.userName }}, 
			        { align: 'center', width:'10%', field: "expenseDate", 	title: "付款日期",}, 
			        { align: 'center', width:'6%',  field: "money", 		title: "金额", 		},
			        { align: 'center', width:'10%', field: "logisticsDate", title: "到货日", }
			        ]],
		});
		
		table.on('toolbar(tableData)', function(obj) {
			var config = obj.config;
			var btnElem = $(this);
			var tableId = config.id;
			switch(obj.event) {
				case 'edit':  addEdit('edit');  break;
				case 'add':	  addEdit('add');   break;
				case 'deleteSome':	 deletes(); break;
			}
		});
	
		function addEdit(type){
			var	data={id:'',batchNumber:'',content:'',money:'',expenseDate:'',logisticsDate:'',custom:{name:'',id:0}},
				choosed = layui.table.checkStatus('tableData').data,
				tpl=addEditTpl.innerHTML,
				title='新增',
				userId='',
				html='';
			if(type=='edit'){
				if(choosed.length>1){
					layer.msg('无法同时编辑多条信息',{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg('请选择信息',{icon:2});
					return;
				}
				data=choosed[0];
				userId=choosed[0].userId==null?'':choosed[0].userId;
				title='修改';
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			layer.open({
				type:1,
				title:title,
				area:['30%','55%'],
				btn:['确定','取消'],
				content:html,
				yes:function(){
					$('#addEditSureBtn').click();
				}
			})
			$('#addEditUser').html(getSelectHtml(userId));
			laydate.render({ elem: '#addEditExpenseDate', type: 'datetime'});
			laydate.render({ elem: '#addEditLogisticsDate', type: 'datetime'});
			form.on('submit(addEditSuer)',function(obj){
				save(obj.field);
			})
			form.render();
			  $("#addEditCustomName").typeahead({
					source : function(query, process) {
						return $.ajax({
							url : '${ctx}/fince/findCustom',
							type : 'GET',
							data : { name:query, type:2 },
							success : function(result) {
								 var resultList = result.data.map(function (item) {	//转换成 json集合
				                        var aItem = {name: item.name, id:item.id}	//转换成 json对象
				                        return JSON.stringify(aItem);				//处理 json对象为字符串
				                    });
								 	$('#addEditCustomId').val(0);
								 return process(resultList);
							},
						})
					}, highlighter: function (item) {
						 var item = JSON.parse(item);
						return item.name
	                }, matcher: function (item) {
				        var item = JSON.parse(item);
				        $('#addEditCustomId').val(item.id);
				    	return item.id
				    },
					updater:function(item){
						var item = JSON.parse(item);
						 $('#addEditCustomId').val(item.id);
						return item.name
					},
				}); 
		}
		
		//监听搜索
		form.on('submit(LAY-search)', function(obj) {		
			var field = obj.field;
			var searchData = {
				userId: 		field.userId,
				content:		field.content,
				flag:			field.flag,				
				expenseDate:	'',
				logisticsDate:	'',
			};
			if(field.dataType=='expenseDate'){
				searchData.expenseDate = field.orderTimeBegin;
			}else
				searchData.logisticsDate = field.orderTimeBegin;
			table.reload('tableData', {
				where: searchData
			});  
		});
		
		function deletes(){
			var choosed = layui.table.checkStatus('tableData').data;
			if(choosed.length<1){
				layer.msg('please choosed info',{icon:2});
				return;
			}
			var ids='';
			for(var i=0;i<choosed.length;i++){
				ids+=(choosed[i].id+',')
			}
			layer.confirm('您是否确定要删除选中的' + choosed.length + '条记录？', function() {
				var load = layer.load(1);
				$.ajax({
					url: "${ctx}/fince/deleteConsumption",
					data: { ids: ids, },
					async:false,
					success: function(result) {
						if(0 == result.code) {
							var configTemp = tablePlug.getConfig("tableData");
				            if (configTemp.page && configTemp.page.curr > 1) {
				              table.reload("tableData", {
				                page: { curr: configTemp.page.curr - 1 }
				              })
				            }else{
				            	table.reload("tableData")
				            };
							layer.msg(result.message, { icon: 1, time:800 });
						} else {
							layer.msg(result.message, { icon: 2, time:800 });
						}
					},
					error: function() {
						layer.msg("操作失败！", { icon: 2 });
					}
				});
				layer.close(load);
			});
		}
		function save(data){
			var load = layer.load(1);
	    	$.ajax({
				url: "${ctx}/fince/addConsumption",
				data: data,
				type: "POST",
				async:false,
				success: function(result) {
					if(0 == result.code) {
					 	table.reload("tableData"); 
					 	layer.closeAll();
						layer.msg(result.message, { icon: 1, time:800 });
					} else {
						layer.msg(result.message, { icon: 2, time:800 });
					}
				},
				error: function() {
					layer.msg("操作失败！请重试", { icon: 2 });
				},
			});
	    	layer.close(load);
	    }
		function getSelectHtml(userId){
			var html='<option value="">请选择</option>';
			for(var i=0;i<allUser.length;i++){
				var selected=allUser[i].id==userId?'selected':'';
				html+='<option value="'+allUser[i].id+'" '+selected+'>'+allUser[i].userName+'</option>';
			}
			return html;
		}
		function getAllUser(){
			var userList=[];			
			$.ajax({
				url: '${ctx}/system/user/findAllUser',
				async: false,
				success: function(result) {
					$(result.data).each(function(index, item ) {
						userList.push(item);
					})
				},
				error: function() {
					layer.msg("操作失败！", { icon: 2 });
				}
			});
			return userList;
		}
		
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	}
)
</script>
</body>
</html>
