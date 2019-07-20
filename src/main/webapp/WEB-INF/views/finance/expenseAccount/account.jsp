<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>报销申请</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<style type="text/css">
	td{
		text-align:center;
	}
	.layui-table-cell{
		text-align:center;
	}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<div class="layui-form">
			<table>
				<tr>
					<td>报销人:</td>
					<td><input type="text" name="Username" id="firstNames" class="layui-input" /></td>
					<td>&nbsp;&nbsp;</td>
					<td>报销内容:</td>
					<td><input type="text" name="content"  class="layui-input" /></td>
					<td>&nbsp;&nbsp;</td>
					<td><select id="dateTypeSelect">
							<option value="expenseDate">申请日期</option>
							<option value="realityDate" disabled="disabled">实际日期</option>
						</select></td>
					<td>&nbsp;&nbsp;</td>
					<td>
						<input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input">
					</td>
					<td>&nbsp;&nbsp;</td>
					<td>报销金额:</td>
					<td style="width:100px;"><input type="text" name="money"  class="layui-input" /></td>
					<td>&nbsp;&nbsp;</td>
					<td>是否核对:
					<td style="width:100px;"><select class="form-control" name="flag">
												<option value="">请选择</option>
												<option value="0">未核对</option>
												<option value="1">已核对</option></select></td>
					<td>&nbsp;&nbsp;</td>
					<td>
						<div class="layui-inline">
							<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">
								<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
							</button>
						</div>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>是否预算:
					<td style="width:100px;"><select lay-filter="isBudget">
													<option value="0">否</option>
													<option value="1">是</option></select></td>
				</tr>
				<tr style="height:5px;"></tr>
				<tr id="searchTr" style="display:none;">
					<td>类型:</td>
					<td style="width:182px;"><select id="applyTypeIdSelect"><option value="">请选择</option></select></td>
					<td>&nbsp;&nbsp;</td>
					<td>月底删除:</td>
					<td style="width:182px;"><select id="deleteFlagSelect">
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option></select></td>
				</tr>
			</table>
		</div>
		<table id="tableData" lay-filter="tableData"></table>
		<table id="tableDataTwo" lay-filter="tableDataTwo"></table>
		
		<shiro:hasAnyRoles name="superAdmin,personnel">
  			<p id="totalAll" style="text-align:center;color:red;"></p>
		</shiro:hasAnyRoles> 
	</div>
</div>
<div style="display: none;" id="layuiOpen">
	<table id="tableBudget" class="table_th_search" lay-filter="tableBudget"></table>
</div>
	
<script type="text/html" id="toolbar">
	<div class="layui-btn-container layui-inline">
		<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
		<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
		<shiro:hasAnyRoles name="superAdmin,personnel">
			<span class="layui-btn layui-btn-sm" lay-event="openBudget">预算报销单</span>
		</shiro:hasAnyRoles> 
	</div>
</script>
<script type="text/html" id="toolbar2">
	<div class="layui-btn-container layui-inline">
		<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
		<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
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
			,layer = layui.layer 
			,form = layui.form 
			,table = layui.table 
			,laydate = layui.laydate 
			,tablePlug = layui.tablePlug 
			,element = layui.element;
		
		getDate();		//获取合计数据
		var parentId = '';
	  	var load = null;
	  	function loads(){
	  		load = layer.load(1,{shade:[0.1,'black']} );
	  	}
		var htmls = '<option value="">请选择</option>';
		var typeSelectHtml = '<option value="">请选择</option>';
		var index = layer.load(1);
		
		laydate.render({
			elem: '#startTime',
			type: 'datetime',
			range: '~',
		});
		form.on('select(isBudget)',function(obj){
			if(obj.value==0){
				$('#tableData').next().hide();
				$('#searchTr').hide();
				$('#tableDataTwo').next().show();
				$('#dateTypeSelect').find('option[value="realityDate"]').removeAttr('selected');
				$('#dateTypeSelect').find('option[value="realityDate"]').attr('disabled','disabled');
			}else{
				$('#searchTr').show();
				$('#tableData').next().show();
				$('#dateTypeSelect').find('option[value="realityDate"]').removeAttr('disabled');
				$('#tableDataTwo').next().hide();
			}
			$('#dateTypeSelect').find('option[value="expenseDate"]').attr('selected','selected');
			form.render();
		})	
		getAllUser();
		getAllAccountType();
		//渲染表格
		var cols = [[
		             { type: 'checkbox', fixed: 'left' }, 
					 { field: "content", title: "报销内容", edit: 'text' }, 
					 { field: "userId",  title: "报销人",   edit: false, templet: fn1('selectOne') }, 
					 { field: "budget",  title: "是否预算", edit: false, templet: function(d){ return d.budget==1?'是':'否';},width:'7%', }, 
					 { field: "money",   title: "申请金额",     edit: 'text',width:'7%', }, 
					 { field: "expenseDate", title: "报销申请日期", edit: false, },
					 { field: "withholdReason", title: "扣款事由", edit: 'text' },
					 { field: "withholdMoney",  title: "扣款金额",  edit: 'text',width:'7%', }, 
					 { field: "settleAccountsMode", title: "结款模式", edit: false,width:'7%', templet: fn3('selectThree')}
					]];
		renderTable({			//渲染预算表格
			elem: '#tableData',
			toolbar: '#toolbar', 
			url: '${ctx}/fince/getConsumption?budget=1&type=1' ,
			cols:  (function(){
						var c = [[]];
						layui.each(cols[0],function(index,item){	//进行深拷贝，避免影响其他表格的渲染
							c[0].push(item);
						})
						c[0].push({field: "realityDate", title: "实际时间", edit: false });
						c[0].push({field: "applyTypeId", title: "报销类型", edit: false ,templet: getAccountType(),});
						c[0].push({field: "deleteFlag",  title: "月底删除", edit: false ,templet: getDeleteFlag(), width:'7%',})
						return c;
					})(),
			done:function(that){
					var tableView = that.elem.next();
					var tableElem = that.elem.next('.layui-table-view').find('.layui-table-box');
					layui.each(tableView.find('td[data-field="realityDate"]'), function(index, tdElem) {
						laydate.render({
							elem: tdElem.children[0],
							type: 'datetime',
							done: function(value, date) {
									var id = table.cache['tableData'][index].id
									var postData = {
										id: id,
										realityDate: value,
									};
									mainJs.fUpdate(postData);
							}
						})
					})
				}
		})
		renderTable({		//渲染非预算表格
			elem: '#tableDataTwo',
			toolbar: '#toolbar2', 
			url: '${ctx}/fince/getConsumption?budget=0&type=1' ,
			cols:cols,
		})
		var colsChild = [[
					     { type: 'checkbox', fixed: 'left', totalRowText:'合计' }, 
						 { field: "content", title: "报销内容", edit: 'text' }, 
						 { field: "userId",  title: "报销人",   edit: false, templet: fn1('selectOne') }, 
						 { field: "money",   title: "报销申请金额", 		edit: 'text', totalRow:true },
						 { field: "expenseDate", title: "报销申请日期", 	edit: 'text' }, 
						 { field: "withholdReason", title: "扣款事由", 	edit: 'text' }, 
						 { field: "withholdMoney",  title: "扣款金额",	edit: 'text' }, 
						 { field: "settleAccountsMode", title: "结款模式", edit: false, templet: fn3('selectThree') }
						]];
		renderTable({		//渲染预算报销单表格
			elem: '#tableBudget',
			toolbar: '#toolbar2', 
			data: [],
			totalRow:true,
			height: '600',
			cols: colsChild,
		})
		//所有表格下拉框选择修改功能
	   	tablePlug.smartReload.enable(true); 
		form.on('select(lay_selecte)', function(data) {
			var selectElem = $(data.elem);
			var tdElem = selectElem.closest('td');
			var trElem = tdElem.closest('tr');
			var tableView = trElem.closest('.layui-table-view');
			var tableId = tableView.attr('lay-id');
			var field = tdElem.data('field');
			table.cache[tableId][trElem.data('index')][tdElem.data('field')] = data.value;
			var id = table.cache[tableId][trElem.data('index')].id;
			var postData = {
				id: id,
				[field]:data.value
			}
			mainJs.fUpdate(postData);
		});
		//增、删、改
		function addTempData(t){
			table.addTemp(t.tableId,t.allField,function(trElem) {
				layui.each(trElem.find('td[data-field="expenseDate"]'), function(index, tdElem) {
					tdElem.onclick = function(event) {
						layui.stope(event)
					};
					laydate.render({
						elem: tdElem.children[0],
						format: 'yyyy-MM-dd HH:mm:ss',
						done: function(value, date) {
							var trElem = $(this.elem[0]).closest('tr');
							var tableView = trElem.closest('.layui-table-view');
							table.cache[t.tableId][trElem.data('index')]['expenseDate'] = value;
							var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
							var postData = {
								id: id,
								expenseDate:value,
							}
							mainJs.fUpdate(postData);
						}
					})
				})
				t.done && t.done(trElem);
			});
		}
		function saveTempData(tableId){
			var data = table.getTemp(tableId).data;
			var msg = '';
			for(var i=0;i<data.length;i++){
				var postData = data[i];
			 	postData.userId=="" && (msg = "请填写报销申请人");
		    	(postData.money=="" || isNaN(postData.money)) && (msg = "请填写报销申请金额（且必须为数字）");
		    	postData.expenseDate=="" && (msg = "请填写报销申请日期");
		    	if(msg!=''){
		    		layer.msg(msg,{icon:2});
		    		return false;
		    	}
			}
			data.forEach(function(postData,i){
				mainJs.fAddNotReload(postData);
			})	
			table.cleanTemp(tableId);
		 	table.reload("tableData"); 
		 	table.reload("tableDataTwo"); 
            table.cache['tableBudget'] && table.reload("tableBudget");  
			return true;
		}
		function deleteSome(tableId){
			var checkedIds = tablePlug.tableCheck.getChecked(tableId);
			layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
				var postData = {
					ids: checkedIds,
				}
				$.ajax({
					url: "${ctx}/fince/deleteConsumption",
					data: postData,
					traditional: true,
					success: function(result) {
						var icon = 2;
						if(0 == result.code) {
			              	table.reload('tableData',{
			              		done:function(){
					              	table.reload('tableDataTwo',{
					              		done:function(){
					              			table.cache['tableBudget'] && table.reload('tableBudget');
					              		}
					              	});
			              			
			              		}
			              	})
				            icon = 1;
						} 
						layer.msg(result.message, { icon: icon, time:800 });
					},
					error: function() {
						layer.msg("操作失败！", { icon: 2 });
					}
				});
			});
		}
		//监听工具栏事件
		table.on('toolbar(tableData)', function(obj) {
			var tableId = 'tableData';
			switch(obj.event) {
				case 'addTempData':
					var allField = {id: '', content: '', budget: 1,userId:'',money: '', expenseDate: '', withholdReason: '',
								withholdMoney:'',settleAccountsMode:'',type:'1',realityDate:'',applyTypeId:'',deleteFlag:''  };
					addTempData({
						tableId:tableId,
						allField:allField,
						done:function(trElem){
							layui.each(trElem.find('td[data-field="realityDate"]'), function(index, tdElem) {
								tdElem.onclick = function(event) { layui.stope(event) };
								laydate.render({
									elem: tdElem.children[0],
									type: 'datetime',
									done: function(value, date) {
										var trElem = $(this.elem[0]).closest('tr');
										var tableView = trElem.closest('.layui-table-view');
										table.cache[that.id][trElem.data('index')]['expenseDate'] = value;
										var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
										var postData = {
											id: id,
											realityDate: value,
										}
										mainJs.fUpdate(postData);
									}
								})
							})
						}
					})
					break;
				case 'saveTempData':
					saveTempData(tableId);
			        break;
				case 'deleteSome':
					deleteSome(tableId);
					break;
				case 'cleanTempData':	 table.cleanTemp(tableId);
					break;
				case 'openBudget':
					var checkedIds = layui.table.checkStatus(tableId).data;
					if(checkedIds.length>1){
						return layer.msg("只能选择一条数据", { icon: 2 });
					}
					if(checkedIds.length<1){
						return layer.msg("请选择一条数据", { icon: 2 });
					}
					parentId = checkedIds[0].id;
					table.reload('tableBudget',{
						url: '${ctx}/fince/getConsumption',
						where: { type:1, parentId: parentId },
					})
					layer.open({
				         type: 1
				        ,title: "预算报销单" 
				        ,area:['80%', '90%']
				        ,btn: ['取消']
				        ,btnAlign: 'c'
				        ,content:$('#layuiOpen')
				    });
					break;
			}
		});
		table.on('toolbar(tableBudget)', function(obj) {
			var tableId = 'tableBudget';
			switch(obj.event) {
				case 'addTempData':
					var allField = {id: '', content: '', budget: 0,userId:'',money: '', expenseDate: '', 
						withholdReason: '',withholdMoney:'',settleAccountsMode:'',type:'1',parentId:parentId};
					addTempData({
						tableId:tableId,
						allField:allField,
					})
					break;
				case 'saveTempData':
					saveTempData(tableId);
			        break;
				case 'deleteSome':
					deleteSome(tableId);
					break;
				case 'cleanTempData':	table.cleanTemp(tableId);
			break;
			}
		})
		table.on('toolbar(tableDataTwo)',function(obj){
			var tableId = 'tableDataTwo';
			switch(obj.event){
				case 'addTempData':
					var allField = {id: '', content: '', budget: 0,userId:'',money: '', expenseDate: '', withholdReason: '',
								withholdMoney:'',settleAccountsMode:'',type:'1', };
					addTempData({
						tableId:tableId,
						allField:allField,
					})
					break;
				case 'saveTempData':
					saveTempData(tableId);
			        break;
				case 'deleteSome':
					deleteSome(tableId);
					break;
				case 'cleanTempData':	 table.cleanTemp(tableId);
					break;
			}
		})
		//监听单元格编辑
		table.on('edit(tableData)', function(obj) {
			if(!obj.data.id) 
				return;
			layer.confirm('是否确认修改？',
				function(){
					var data = obj.data;
						var postData = {
							id: data.id,
							[obj.field]: obj.value
						}
						mainJs.fUpdate(postData);
				},
				function(){ 
					table.reload("tableDataTwo")  
				}
			)
		});
		table.on('edit(tableBudget)', function(obj) {
			var  data = obj.data;
			if(data.id=='')
				return;
			var postData = {
				id: data.id,
				[obj.field]: obj.value
			}
			mainJs.fUpdate(postData);
		});
		//搜索功能
		form.on('submit(LAY-search)', function(obj) {		
			var field = obj.field;
			if(field.orderTimeBegin!=''){
				var orderTime=field.orderTimeBegin.split('~');
				field.orderTimeBegin=orderTime[0];
				field.orderTimeEnd=orderTime[1];
				field[$('#dateTypeSelect').val()] = '2018-01-01 00:00:00';
			}
			var tableId = 'tableDataTwo';
			var dis = $('#tableData').next().css('display');
			if(dis!='none'){
				tableId = 'tableData';
				field.applyTypeId = $('#applyTypeIdSelect').val();
				field.deleteFlag = $('#deleteFlagSelect').val();
			}
			table.reload(tableId, {
				where: field,
				page:{ curr:1 },
			});  
		});
		//新增、修改接口
		var mainJs = {
		    fAdd : function(data){
		    	$.ajax({
					url: "${ctx}/fince/addConsumption",
					data: data,
					async: false,
					type: "POST",
					beforeSend: function() {
						index;
					},
					success: function(result) {
						var icon = 2;
						if(0 == result.code) 
				            icon = 1;
						table.reload('tableData',{
		              		done:function(){
				              	table.reload('tableDataTwo',{
				              		done:function(){
				              			table.cache['tableBudget'] && table.reload('tableBudget');
				              		}
				              	});
		              			
		              		}
		              	})
						layer.msg(result.message, {icon: icon, time:800});
					},
					error: function() {
						layer.msg("操作失败！请重试", { icon: 2 });
					},
				});
				layer.close(index);
		    },
		    fUpdate : function(data){
		    	if(data.id=="")
		    		return;
		    	this.fAdd(data);
		    },
		    fAddNotReload : function(data){
		    	$.ajax({
					url: "${ctx}/fince/addConsumption",
					data: data,
					async: false,
					type: "POST",
					beforeSend: function() {
						index;
					},
					success: function(result) {
						var icon = 2;
						if(0 == result.code) 
				            icon = 1;
						layer.msg(result.message, {icon: icon, time:800});
					},
					error: function() {
						layer.msg("操作失败！请重试", { icon: 2 });
					},
				});
				layer.close(index);
		    },
		};
		//其他功能函数
		function getDate(){
			if(document.getElementById("totalAll")!=null){
				$.ajax({
					url:"${ctx}/fince/countConsumptionMoney",
					success:function(result){
						if(0==result.code){
							var html="";
							var data=result.data;
							html+='<label>预算报销总计:'+data.budget+'<label>&nbsp;&nbsp;&nbsp;&nbsp;'
								+'<label>报销总计：'+data.nonBudget+'</label>&nbsp;&nbsp;&nbsp;&nbsp;'
								+'<label>共计：'+data.sumBudget+'</label>';
							$('#totalAll').html(html);
						}else{
							$('#totalAll').append('<label style="color:red;">获取数据异常</label>');
						}
					}
				})
			}
		}
		// 获取下拉框
		function fn1(field) {
			return function(d) {
				return [
					'<select name="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' + d.userId + '">',
					htmls,
					'</select>'
				].join('');
			};
		};
		function fn3(field) {
			return function(d) {
				return ['<select name="selectThree" lay-filter="lay_selecte" lay-search="true" data-value="' + d.settleAccountsMode + '">',
					'<option value="0">请选择</option>',
					'<option value="1">现金</option>',
					'<option value="2">月结</option>',
					'</select>'
				].join('');
			};
		};
		function getAccountType(){
			return function(d){
				return ['<select lay-filter="lay_selecte" lay-search="true" data-value="' + (d.applyType && d.applyType.id) + '">',
				        typeSelectHtml,
						'</select>',
					].join('');
			}
		}
		function getDeleteFlag(){
			return function(d) {
				return ['<select lay-filter="lay_selecte" lay-search="true" data-value="' + d.deleteFlag + '">',
					'<option value="">请选择</option>',
					'<option value="1">是</option>',
					'<option value="0">否</option>',
					'</select>'
				].join('');
			};
		}
		function getAllUser(){
			loads();
			$.ajax({
				url: '${ctx}/system/user/findAllUser',
				async: false,
				success: function(result) {
					$(result.data).each(function(i, o) {
						htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
					})
				},
				error: function() {
					layer.msg("操作失败！", { icon: 2 });
				}
			});
			layer.close(load);
		};
		function getAllAccountType(){
			loads();
			$.ajax({
				url: '${ctx}/basedata/list?type=applyType',
				async: false,
				success: function(result) {
					$(result.data).each(function(i, o) {
						typeSelectHtml += '<option value=' + o.id + '>' + o.name + '</option>'
					});
					$('#applyTypeIdSelect').html(typeSelectHtml);
					form.render();
				},
				error: function() {
					layer.msg("操作失败！", { icon: 2 });
				}
			});
			layer.close(load);
		}
		function renderTable(t){
			table.render({
				elem:t.elem,
				url:t.url,
				cols:t.cols,
				where:t.where,
				toolbar:t.toolbar,
				totalRow: t.totalRow || false,
				colFilterRecord: true,
				smartReloadModel: true,
				height: t.height || '700',
				size:'lg',
				page: { },
				request:{ pageName: 'page' , limitName: 'size' },
				parseData: function(ret) { return { code: ret.code, msg: ret.message, count:ret.data.total, data: ret.data.rows } },
				done:function(res, curr, count){
					var tableView = this.elem.next();
					var tableElem = this.elem.next('.layui-table-view').find('.layui-table-box');
					layui.each(tableElem.find('select'), function(index, item) {
						var elem = $(item);
						elem.val(elem.data('value'));
					});
					form.render();
					layui.each(tableView.find('td[data-field="expenseDate"]'), function(index, tdElem) {
						tdElem.onclick = function(event) {
							layui.stope(event)
						};
						laydate.render({
							elem: tdElem.children[0],
							type: 'datetime',
							done: function(value, date) {
									var id = table.cache['tableData'][index].id
									var postData = {
										id: id,
										expenseDate: value,
									};
									mainJs.fUpdate(postData);
							}
						})//end laydate
					})//end layui.each
					t.done && t.done(this);
					
				}//end done
			})//end render
		}
		$('#tableData').next().hide();
	}
)
</script>
</body>
</html>
