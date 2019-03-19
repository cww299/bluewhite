<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
</head>

<body>
	<section id="main-wrapper" class="theme-default">
		<%@include file="../decorator/leftbar.jsp"%>
		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">角色管理</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i>
								<i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="panel-body">

							<div class="layui-form layui-card-header layuiadmin-card-header-auto">
								<div class="layui-form-item">
									<div class="layui-inline">
										<label class="layui-form-label">id</label>
										<div class="layui-input-block">
											<input type="text" name="id" placeholder="请输入id" autocomplete="off" class="layui-input">
										</div>
									</div>
									<div class="layui-inline">
										<label class="layui-form-label">名称</label>
										<div class="layui-input-block">
											<input type="text" name="name" placeholder="请输入名称" autocomplete="off" class="layui-input">
										</div>
									</div>


									<div class="layui-inline">
										<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-search">
											<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
										</button>
									</div>
								</div>
							</div>

							<div class="layui-card-body">
								<table class="layui-table" id="LAY-role-table" lay-filter="LAY-role-table"></table>
								<div id="page"></div>
							</div>
						</div>
					</div>
				</div>
		</section>
	</section>
	</section>



	<!-- 选择框组件 -->
	<script type="text/html" id="switchTpl">
 	 	<input type="checkbox" name="isShow" value="{{d.isShow}}" lay-skin="switch" lay-text="可用|不可用" lay-filter="isShow" {{ d.isShow == true ? 'checked' : '' }} >
	</script>
	
	<script type="text/html" id="toolbarDemo">
  	<div class="layui-btn-container layui-inline">
    <span class="layui-btn layui-btn-sm" lay-event="getChecked">获得选中的数据</span>
    <span class="layui-btn layui-btn-sm" lay-event="deleteSome">批量删除</span>
    <span class="layui-btn layui-btn-sm" lay-event="addTempData">添加临时数据</span>
    <span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="getTempData">获得临时数据</span>
    <span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空临时数据</span>
	</div>
	</script>






	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>

	<script>
		layui.config({
			base : '${ctx}/static/layui-v2.4.5/'
		}).extend({
			tablePlug : 'tablePlug/tablePlug'
		}).define(
				[ 'tablePlug', 'laydate' ],
				function() {
					var $ = layui.jquery, layer = layui.layer //弹层
					, form = layui.form //弹层
					, table = layui.table //表格
					, laydate = layui.laydate //日期控件
					, tablePlug = layui.tablePlug; //表格插件
					
					// 处理操作列
					var fn1 = function(field) {
						return function(data) {
							return [ '<select name="city" lay-filter="city_select" lay-search="true">', 
									'<option value="" >请选择或搜索</option>', 
									'<option value="0" >超级管理员</option>',
									'<option value="1" >管理员</option>', 
									'<option value="2" >员工</option>',
									'</select>' 
									].join('');
						};
					};
					
					

					
					table.render({
						elem : '#LAY-role-table',
						size : 'lg',
						url : '${ctx}/roles/page' ,
						page : {},
						loading : true,
						toolbar : '#toolbarDemo',//开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow : true ,//开启合计行
						colFilterRecord : true,// 开启智能重载
						smartReloadModel : true,// 设置开启部分选项不可选
						primaryKey : 'id',// 设置表格的主键（主要用在记录选中状态还有不可操作记录的时候用
						checkDisabled : {
							enabled : true,
							data : [ 10000, 10001, 10002, 10003, 10004, 10005, 10009 ]
						},
						done : function() {
							var tableView = this.elem.next();
							tableView.find('.layui-table-grid-down').remove();
							var totalRow = tableView.find('.layui-table-total');
							var limit = this.page ? this.page.limit : this.limit;
							layui.each(totalRow.find('td'), function(index, tdElem) {
								tdElem = $(tdElem);
								var text = tdElem.text();
								if (text && !isNaN(text)) {
									text = (parseFloat(text) / limit).toFixed(2);
									tdElem.find('div.layui-table-cell').html(text);
								}
							});

							// 初始化laydate
							layui.each(tableView.find('td[data-field="birthday"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								laydate.render({
									elem : tdElem.children[0],
									format : 'yyyy/MM/dd',
									done : function(value, date) {
										var trElem = $(this.elem[0]).closest('tr');
										table.cache.demo[trElem.data('index')]['birthday'] = value;
									}
								})
							})
						},
						parseData : function(ret) {
							return {
								code : ret.code,
								msg : ret.msg,
								count : ret.data.total, 
								data : ret.data.rows
							}
						},
						checkStatus : {},
						cols : [ 
						[{type: 'checkbox', fixed: 'left', rowspan: 2}],
						[  {
							field : "id",
							title : "ID",
							width : 80,
							sort : true
						}, {
							field : "name",
							title : "角色名",
							edit : 'text'
						}, {
							field : "role",
							title : "英文名称",
							edit : 'text'
						}, {
							field : "roleType",
							title : "角色类型",
							align : 'center',
							templet : fn1('city')
						}, {
							field : "isShow",
							title : "是否可用",
							templet : '#switchTpl'
						}, {
							field : "description",
							title : "具体描述",
							edit : 'text'
						} ] ]
					});

					// 监听表格中的下拉选择将数据同步到table.cache中
					form.on('select(city_select)', function(data) {
						var selectElem = $(data.elem);
						var tdElem = selectElem.closest('td');
						var trElem = tdElem.closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
					});

					//监听排序事件
					table.on('sort(test)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
						// console.log(obj.field); //当前排序的字段名
						// console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
						// console.log(this); //当前排序的 th 对象

						//尽管我们的 table 自带排序功能，但并没有请求服务端。
						//有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
						table.reload('LAY-role-table', {
							initSort : obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。
							,
							where : { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
								field : obj.field,
								order : obj.type
							//排序方式
							}
						});
					});

					// 监听编辑如果评分负数给回滚到修改之前并且弹出提示信息并且重新获得焦点等待输入
					table.on('edit(test)', function(obj) {
						var tableId = obj.tr.closest('.layui-table-view').attr('lay-id');
						var trIndex = obj.tr.data('index');
						var that = this;
						var tdElem = $(that).closest('td');

						var field = obj.field;
						var value = obj.value;
						if (field === 'score') {
							value = parseInt(value);
							if (value < 0) {
								setTimeout(function() {
									// 小于0回滚再次获得焦点打开
									obj.update({
										score : table._dataTemp[tableId][trIndex][field]
									});
									layer.msg('评分不能为负数!', {
										anim : 6
									});
									tdElem.click();
								}, 100);
							}
						}
					});

					// tr点击触发复选列点击
					$(document).on('click', '.layui-table-view tbody tr', function(event) {
						var elemTemp = $(this);
						var tableView = elemTemp.closest('.layui-table-view');
						var trIndex = elemTemp.data('index');
						tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
					})
				});
	</script>



















































	<!-- <script>
			//监听搜索
			form.on('submit(LAY-role-search)', function(data) {
				var field = data.field;
				console.log(field);
					$.ajax({
						url : "${ctx}/roles/page",
						type : "get",
						data : field,
						dataType : "json",
						success : function(result) {
						 	table.reload('LAY-role-table', {
								// 更新数据
								data : result.data.rows,
							}); 	
						}
					});
			});

			//事件
			var active = {
				//批量删除
				batchdel : function() {
					var checkStatus = table.checkStatus('LAY-role-table'), 
					checkData = checkStatus.data; //得到选中的数据
					if (checkData.length === 0) {
						return layer.msg('请选择数据');
					}
					layer.prompt({
						formType : 1,
						title : '敏感操作，请验证口令'
					},

					function(value, index) {
						layer.close(index);
						layer.confirm('确定删除吗？', function(index) {

							//执行 Ajax 后重载
							/*
							admin.req({
							  url: 'xxx'
							  //,……
							});
							 */
							table.reload('LAY-role-table');
							layer.msg('已删除');
						});
					});
				},
				// 新增一条
				add : function() {
					var oldData =  table.cache["LAY-role-table"];
					oldData.unshift({});
					table.reload('LAY-role-table',{
			              data : oldData
			          });
				}
			};

			//用于确定是什么事件
			$('.layui-btn.layuiadmin-btn-admin').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
 -->
</body>
</html>