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
		<div class="layui-form layui-card-header layuiadmin-card-header-auto">
			<div class="layui-form-item">
				<table>
					<tr>
						<td>报销人:</td>
						<td><input type="text" name="Username" id="firstNames" class="layui-input" /></td>
						<td>&nbsp;&nbsp;</td>
						<td>报销内容:</td>
						<td><input type="text" name="content"  class="layui-input" /></td>
						<td>&nbsp;&nbsp;</td>
						<td>报销金额:</td>
						<td style="width:100px;"><input type="text" name="money"  class="layui-input" /></td>
						<td>&nbsp;&nbsp;</td>
						<td>申请日期:</td>
						<td>&nbsp;&nbsp;</td>
						<td>
							<input type="hidden" name="expenseDate" value="2018-10-08 00:00:00"><!-- 默认查找申请日期 -->
							<input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
						</td>
						<td>&nbsp;&nbsp;</td>
						<td>是否预算:
						<td style="width:100px;"><select class="form-control" lay-filter="isBudget">
														<option value="0">否</option>
														<option value="1">是</option></select></td>
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
					</tr>
				</table>
			</div>
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
		
		getDate();
		var allField;
		var self = this;
		this.setIndex = function(index){
	  		_index=index;
	  	}
	  	this.getIndex = function(){
	  		return _index;
	  	}
	  	var load = null;
	  	var loads = function(){
	  		load = layer.load(1,{shade:[0.1,'black']} );
	  	}
		var htmls = '<option value="">请选择</option>';
		var index = layer.load(1);
		
		laydate.render({
			elem: '#startTime',
			type: 'datetime',
			range: '~',
		});
	 
		form.on('select(isBudget)',function(obj){
			if(obj.value==0){
				$('#tableData').next().hide();
				$('#tableDataTwo').next().show();
			}else{
				$('#tableData').next().show();
				$('#tableDataTwo').next().hide();
			}
		})		
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
		
		// 处理操作列
		var fn1 = function(field) {
			return function(d) {
				return [
					'<select name="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' + d.userId + '">',
					htmls,
					'</select>'
				].join('');
			};
		};

		var fn2 = function(field) {
			return function(d) {
				return ['<select name="selectTwo" lay-filter="lay_selecte" lay-search="true" data-value="' + d.budget + '">',
					'<option value="0">请选择</option>',
					'<option value="1">预算</option>',
					'</select>'
				].join('');

			};
		};
		var fn3 = function(field) {
			return function(d) {
				return ['<select name="selectThree" lay-filter="lay_selecte" lay-search="true" data-value="' + d.settleAccountsMode + '">',
					'<option value="0">请选择</option>',
					'<option value="1">现金</option>',
					'<option value="2">月结</option>',
					'</select>'
				].join('');

			};
		};
		var cols = [
					[{ type: 'checkbox', fixed: 'left' }, 
					 { field: "content", title: "报销内容", edit: 'text' }, 
					 { field: "userId",  title: "报销人",   edit: false, templet: fn1('selectOne') }, 
					 { field: "budget",  title: "是否预算", edit: false, templet: function(d){ return d.budget==1?'是':'否';} }, 
					 { field: "money",   title: "报销申请金额",     edit: 'text' }, 
					 { field: "expenseDate", title: "报销申请日期", edit: 'text' },
					 { field: "withholdReason", title: "扣款事由", edit: 'text' },
					 { field: "withholdMoney",  title: "扣款金额",  edit: 'text' }, 
					 { field: "settleAccountsMode", title: "结款模式", edit: false, templet: fn3('selectThree')
					}]
				];
		function renderTable(t){
			table.render({
				elem:t.elem,
				url:t.url,
				cols:t.cols,
				done:t.done,
				where:t.where,
				toolbar:t.toolbar,
				colFilterRecord: true,
				smartReloadModel: true,
				height:'700px',
				size:'lg',
				page: { },
				request:{ pageName: 'page' , limitName: 'size' },
				parseData: function(ret) { return { code: ret.code, msg: ret.message, count:ret.data.total, data: ret.data.rows } },
			})
		}
		renderTable({
			elem: '#tableData',
			toolbar: '#toolbar', 
			url: '${ctx}/fince/getConsumption?budget=1' ,
			where:{ type:1 },
			cols: cols,
				done: function(res, curr, count) {
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
						})
					})
				},
		})
	   	tablePlug.smartReload.enable(true); 
		/* table.render({
			elem: '#tableData',
			size: 'lg',
			url: '${ctx}/fince/getConsumption?budget=1' ,
			where:{ type:1 },
			height:'700px',
			request:{ pageName: 'page' , limitName: 'size' },
			page: { },
			toolbar: '#toolbar', 
			colFilterRecord: true,
			smartReloadModel: true,// 开启智能重载
			parseData: function(ret) {
				return { code: ret.code, msg: ret.message, count:ret.data.total, data: ret.data.rows }
			},
			cols: [
				[{ type: 'checkbox', fixed: 'left' }, 
				 { field: "content", title: "报销内容", edit: 'text' }, 
				 { field: "userId",  title: "报销人",   edit: false, templet: fn1('selectOne') }, 
				 { field: "budget",  title: "是否预算", edit: false, templet: function(d){ return d.budget==1?'是':'否';} }, 
				 { field: "money",   title: "报销申请金额",     edit: 'text' }, 
				 { field: "expenseDate", title: "报销申请日期", edit: 'text' },
				 { field: "withholdReason", title: "扣款事由", edit: 'text' },
				 { field: "withholdMoney",  title: "扣款金额",  edit: 'text' }, 
				 { field: "settleAccountsMode", title: "结款模式", edit: false, templet: fn3('selectThree')
				}]
			],
			done: function(res, curr, count) {
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
					})
				})
			},
		}); */
		
		form.on('select(lay_selecte)', function(data) {
			var selectElem = $(data.elem);
			var tdElem = selectElem.closest('td');
			var trElem = tdElem.closest('tr');
			var tableView = trElem.closest('.layui-table-view');
			var field = tdElem.data('field');
			table.cache['tableData'][trElem.data('index')][tdElem.data('field')] = data.value;
			var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
			var postData = {
				id: id,
				[field]:data.value
			}
			mainJs.fUpdate(postData);
		});
		
		table.on('toolbar(tableData)', function(obj) {
			var config = obj.config;
			var btnElem = $(this);
			var tableId = config.id;
			switch(obj.event) {
				case 'addTempData':
					allField = {id: '', content: '', budget: 0,userId:'',money: '', expenseDate: '', withholdReason: '',withholdMoney:'',settleAccountsMode:'',type:'1'};
					table.addTemp(tableId,allField,function(trElem) {
						var that = this;
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
									table.cache[that.id][trElem.data('index')]['expenseDate'] = value;
									var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
									var postData = {
										id: id,
										expenseDate:value,
									}
									mainJs.fUpdate(postData);
								}
							})
						})
					});
					break;
				case 'saveTempData':
					var data = table.getTemp(tableId).data;
					var flag=false;
					var a=0;
					data.forEach(function(postData,i){
					 	if(postData.userId==""){
				    		return layer.msg("请填写报销申请人", { icon: 2, });
				    	}
				    	if(postData.money=="" || isNaN(postData.money)){
				    		return layer.msg("请填写报销申请金额（且必须为数字）", { icon: 2, });
				    	} 
				    	if(postData.expenseDate==""){
				    		return layer.msg("请填写报销申请日期", { icon: 2, });
				    	}
				    	a++;
				    	if(a==data.length){
				    		flag=true
				    	}
					})
					if(flag==true){
						data.forEach(function(postData,i){
						 	mainJs.fAdd(postData);
						})	
						table.cleanTemp(tableId);
					}
			          break;
				case 'deleteSome':
					var checkedIds = tablePlug.tableCheck.getChecked(tableId);
					layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
						var postData = {
							ids: checkedIds,
						}
						$.ajax({
							url: "${ctx}/fince/deleteConsumption",
							data: postData,
							traditional: true,
							beforeSend: function() {
								index;
							},
							success: function(result) {
								var icon = 2;
								if(0 == result.code) {
									var configTemp = tablePlug.getConfig("tableData");
						            if (configTemp.page && configTemp.page.curr > 1) {
						              	table.reload("tableData", {  page: { curr: configTemp.page.curr - 1  } })
						            }else{
						            	table.reload("tableData")
						            };
						            icon = 1;
								} 
								layer.msg(result.message, { icon: icon, time:800 });
							},
							error: function() {
								layer.msg("操作失败！", { icon: 2 });
							}
						});
						layer.close(index);
					});
					break;
				case 'openBudget':
					var checkedIds = layui.table.checkStatus(tableId).data;
					if(checkedIds.length>1){
						return layer.msg("只能选择一条数据", { icon: 2 });
					}
					if(checkedIds.length<1){
						return layer.msg("请选择一条数据", { icon: 2 });
					}
					var str = checkedIds[0].id;
					self.setIndex(str)
					table.render({
						elem: '#tableBudget',
						size: 'lg',
						url: '${ctx}/fince/getConsumption' ,
						where:{ type:1, parentId:str },
						request:{ pageName: 'page' , limitName: 'size' },
						page: { },
						toolbar: '#toolbar2', 
						totalRow: true,
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,
						parseData: function(ret) {
							return { code: ret.code, msg: ret.message, count:ret.data.total, data: ret.data.rows }
						},
						cols: [[
						     { type: 'checkbox', fixed: 'left', totalRowText:'合计' }, 
							 { field: "content", title: "报销内容", edit: 'text' }, 
							 { field: "userId",  title: "报销人",   edit: false, templet: fn1('selectOne') }, 
							 { field: "money",   title: "报销申请金额", 		edit: 'text', totalRow:true },
							 { field: "expenseDate", title: "报销申请日期", 	edit: 'text' }, 
							 { field: "withholdReason", title: "扣款事由", 	edit: 'text' }, 
							 { field: "withholdMoney",  title: "扣款金额",	edit: 'text' }, 
							 { field: "settleAccountsMode", title: "结款模式", edit: false, templet: fn3('selectThree') }
							 ]],
						done: function(res, curr, count) {
							var tableView = this.elem.next();
							var tableElem = this.elem.next('.layui-table-view');
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
									format: 'yyyy-MM-dd HH:mm:ss',
									done: function(value, date) {
											var id = table.cache[tableView.attr('lay-id')][index].id
											var postData = {
												id: id,
												expenseDate: value,
											};
											mainJs.fUpdate(postData);
									}
								})
							})
						},
					});
					layer.open({
				         type: 1
				        ,title: "预算报销单" 
				        ,area:['80%', '90%']
				        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
				        ,btn: ['取消']
				        ,btnAlign: 'c'
				        ,content:$('#layuiOpen')
				      });
					break;
				case 'cleanTempData':	 table.cleanTemp(tableId);
					break;
			}
		});

		table.on('toolbar(tableBudget)', function(obj) {
			var config = obj.config;
			var btnElem = $(this);
			var tableId = config.id;
			switch(obj.event) {
				case 'addTempData':
					allField = {id: '', content: '', budget: 0,userId:'',money: '', expenseDate: '', 
						withholdReason: '',withholdMoney:'',settleAccountsMode:'',type:'1',parentId:self.getIndex()};
					table.addTemp(tableId,allField,function(trElem) {
						var that = this;
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
									table.cache[that.id][trElem.data('index')]['expenseDate'] = value;
									var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
									var postData = {
										id: id,
										expenseDate:value,
									}
									mainJs.fUpdate(postData);
								}
							})
						})
					});
					break;
				case 'saveTempData':
					var data = table.getTemp(tableId).data;
					var flag=false;
					var a=0;
					data.forEach(function(postData,i){
					 	if(postData.userId==""){
				    		return layer.msg("请填写报销申请人", { icon: 2, });
				    	}
				    	if(postData.money=="" || isNaN(postData.money)){
				    		return layer.msg("请填写报销申请金额（且必须为数字）", { icon: 2, });
				    	} 
				    	if(postData.expenseDate==""){
				    		return layer.msg("请填写报销申请日期", { icon: 2, });
				    	}
				    	a++;
				    	if(a==data.length){
				    		flag=true
				    	}
					})
					if(flag==true){
						data.forEach(function(postData,i){
							mainJs.fAdd(postData);
						})	
						table.cleanTemp(tableId);
					}
			          break;
				case 'deleteSome':
					var checkedIds = tablePlug.tableCheck.getChecked(tableId);
					layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
						var postData = {
							ids: checkedIds,
						}
						$.ajax({
							url: "${ctx}/fince/deleteConsumption",
							data: postData,
							traditional: true,
							beforeSend: function() {
								index;
							},
							success: function(result) {
								if(0 == result.code) {
									var configTemp = tablePlug.getConfig("tableData");
						            if (configTemp.page && configTemp.page.curr > 1) {
						              table.reload("tableData", { page: {  curr: configTemp.page.curr - 1 }  })
						            }else{
						            	table.reload("tableBudget", { page: {} })
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
						layer.close(index);
					});
					break;
				case 'cleanTempData':	table.cleanTemp(tableId);
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
					table.reload("tableData")  
				}
			)
		});
		
		table.on('edit(tableBudget)', function(obj) {
			var  field = obj.field;
				var postData = {
					id: data.id,
					[field]: obj.value
				}
			mainJs.fUpdate(postData);
		});
		
		form.on('submit(LAY-search)', function(obj) {		
			var field = obj.field;
			var orderTime=field.orderTimeBegin.split('~');
			field.orderTimeBegin=orderTime[0];
			field.orderTimeEnd=orderTime[1];
			table.reload('tableData', {
				where: field,
				page:{curr:1},
			});  
		});
		
		var mainJs = {
		    fAdd : function(data){
		    	$.ajax({
					url: "${ctx}/fince/addConsumption",
					data: data,
					type: "POST",
					beforeSend: function() {
						index;
					},
					success: function(result) {
						var icon = 2;
						if(0 == result.code) 
				            icon = 1;
					 	table.reload("tableData"); 
			            table.reload("tableBudget");  
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
		    }
		};
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
		$('#tableData').next().hide();
	}
)
</script>
</body>
</html>
