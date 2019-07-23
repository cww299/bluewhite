<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">


<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">



<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>财务审核</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

</head>

<body>
	<div class="layui-card">
		<div class="layui-card-body">
			<div
				class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>报销人:</td>
							<td><input type="text" name="Username" id="firstNames" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td>报销内容:</td>
							<td><input type="text" name="content" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td>报销金额:</td>
							<td><input type="text" name="money" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td><select class="layui-input" name="selectone" id="selectone">
									<option name="expenseDate" value="2018-10-08 00:00:00">申请日期</option>
									<option name="paymentDate" value="2018-11-08 00:00:00">付款日期</option>
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input">
							</td>
							<!-- <td>&nbsp&nbsp</td>
							<td>结束:</td>
							<td><input id="endTime" name="orderTimeEnd" placeholder="请输入结束时间" class="layui-input">
							</td> -->
							<td>&nbsp;&nbsp;</td>
							<td>是否核对:
							<td><select class="form-control" name="flag">
									<option value="0">未审核</option>
									<option value="1">已审核</option>
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" lay-submit
										lay-filter="LAY-search">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="tableData" class="table_th_search"
				lay-filter="tableData"></table>
		</div>

	</div>

	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="audit">审核</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="noAudit">取消审核</span>
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
					var $ = layui.jquery,
						layer = layui.layer //弹层
						,
						form = layui.form //表单
						,
						table = layui.table //表格
						,
						laydate = layui.laydate //日期控件
						,
						tablePlug = layui.tablePlug //表格插件
						,
						element = layui.element;
					//全部字段
					var allField;
					//select全局变量
					var htmls = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
					});
					/* laydate.render({
						elem: '#endTime',
						type: 'datetime',
					}); */
				 
					$.ajax({
						url: '${ctx}/system/user/findAllUser',
						type: "GET",
						async: false,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							$(result.data).each(function(i, o) {
								htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
							})
							layer.close(index);
						},
						error: function() {
							layer.msg("操作失败！", {
								icon: 2
							});
							layer.close(index);
						}
					});
					
					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						height:'700px',
						url: '${ctx}/fince/getConsumption' ,
						where:{
							flag:0,
							type:1,
							budget:0,
						},
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
							
						},//开启分页
						loading: true,
						toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						/*totalRow: true //开启合计行 */
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								count:ret.data.total,
								data: ret.data.rows
							}
						},
						cols: [
							[{
								type: 'checkbox',
								align: 'center',
								fixed: 'left'
							}, {
								field: "content",
								title: "报销内容",
								align: 'center',
							}, {
								field: "userId",
								title: "报销人",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: function(d){
									return d.user.userName;
								}
							}, {
								field: "budget",
								title: "是否预算",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: function(d){
									if(d.budget==0){
										return "";
									}
									if(d.budget==null){
										return "";
									}
									if(d.budget==1){
										return "预算";
									}
								}
							}, {
								field: "money",
								title: "报销申请金额",
							}, {
								field: "expenseDate",
								title: "报销申请日期",
							}, {
								field: "withholdReason",
								title: "扣款事由",
							}, {
								field: "withholdMoney",
								title: "扣款金额",
							}, {
								field: "settleAccountsMode",
								title: "结款模式",
								search: true,
								edit: false,
								templet:  function(d){
									if(d.settleAccountsMode==0){
										return "";
									}
									if(d.settleAccountsMode==null){
										return "";
									}
									if(d.settleAccountsMode==1){
										return "现金";
									}
									if(d.settleAccountsMode==2){
										return "月结";
									}
								}
							}, {
								field: "paymentDate",
								title: "付款时间",
								style:'background-color: #d8fe83',
							}, {
								field: "paymentMoney",
								title: "付款金额",
								edit: 'text',
								style:'background-color: #d8fe83',
							}, {
								field: "flag",
								title: "审核状态",
								templet:  function(d){
									if(d.flag==0){
										return "未审核";
									}
									if(d.flag==1){
										return "已审核";
									}
								}
							}]
						],
						done: function() {
							var tableView = this.elem.next();
							tableView.find('.layui-table-grid-down').remove();
							var totalRow = tableView.find('.layui-table-total');
							var limit = this.page ? this.page.limit : this.limit;
							layui.each(totalRow.find('td'), function(index, tdElem) {
								tdElem = $(tdElem);
								var text = tdElem.text();
								if(text && !isNaN(text)) {
									text = (parseFloat(text) / limit).toFixed(2);
									tdElem.find('div.layui-table-cell').html(text);
								}
							});
						},
						//下拉框回显赋值
						done: function(res, curr, count) {
							var tableView = this.elem.next();
							var tableElem = this.elem.next('.layui-table-view');
							layui.each(tableElem.find('select'), function(index, item) {
								var elem = $(item);
								elem.val(elem.data('value'));
							});
							form.render();
							// 初始化laydate
							layui.each(tableView.find('td[data-field="paymentDate"]'), function(index, tdElem) {
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
												paymentDate: value,
											};
											//调用新增修改
											mainJs.fUpdate(postData);
												}
											})
										})
									},
								});

				
					
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'audit':
								// 获得当前选中的
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								layer.confirm('您是否确定要审核选中的' + checkedIds.length + '条记录？', function() {
									var postData = {
										ids:checkedIds,
										flag:1,
									}
									mainJs.fAudit(postData);
								});
								break;
							case 'noAudit':
								// 获得当前选中的
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								layer.confirm('您是否确定取消审核选中的' + checkedIds.length + '条记录？', function() {
									var postData = {
										ids:checkedIds,
										flag:0,
									}
									mainJs.fAudit(postData);
								});
								break;
						}
					});

					//监听单元格编辑
					table.on('edit(tableData)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							id = data.id;
							var postData = {
								id:id,
								[field]:value
							}
							//调用新增修改
							mainJs.fUpdate(postData);
					});

					//监听搜索
					form.on('submit(LAY-search)', function(data) {
						var field = data.field;
						var orderTime=field.orderTimeBegin.split('~');
						orderTimeBegin=orderTime[0];
						orderTimeEnd=orderTime[1];
						var a="";
						var b="";
						if($("#selectone").val()=="expenseDate"){
							a="2019-05-08 00:00:00"
						}else{
							b="2019-05-08 00:00:00"
						}
						var post={
							Username:field.Username,
							flag:field.flag,
							orderTimeBegin:orderTimeBegin,
							orderTimeEnd:orderTimeEnd,
							expenseDate:a,
							paymentDate:b,
							budget:0,
							content:field.content,
							money:field.money,
						}
						table.reload('tableData', {
							where: post
						});  
						 
					});
					
					
					//封装ajax主方法
					var mainJs = {
					//修改							
				    fUpdate : function(data){
				    	if(data.id==""){
				    		return;
				    	}
				    	$.ajax({
							url: "${ctx}/fince/addConsumption",
							data: data,
							type: "POST",
							beforeSend: function() {
								index;
							},
							success: function(result) {
								if(0 == result.code) {
								 	 table.reload("tableData", {
						                page: {
						                }
						              })   
									layer.msg(result.message, {
										icon: 1,
										time:800
									});
								
								} else {
									layer.msg(result.message, {
										icon: 2,
										time:800
									});
								}
							},
							error: function() {
								layer.msg("操作失败！请重试", {
									icon: 2
								});
							},
						});
						layer.close(index);
				    },
					
					fAudit : function(postData){
				    	$.ajax({
							url: "${ctx}/fince/auditConsumption",
							data: postData,
							traditional: true,
							type: "POST",
							beforeSend: function() {
								index;
							},
							success: function(result) {
								if(0 == result.code) {
								 	 table.reload("tableData", {
						                page: {
						                }
						              })   
									layer.msg(result.message, {
										icon: 1,
										time:800
									});
								
								} else {
									layer.msg(result.message, {
										icon: 2,
										time:800
									});
								}
							},
							error: function() {
								layer.msg("操作失败！请重试", {
									icon: 2
								});
							},
						});
						layer.close(index);
				    }
					}

				}
			)
		</script>
</body>

</html>