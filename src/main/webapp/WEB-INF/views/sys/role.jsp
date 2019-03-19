<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<style type="text/css">
/* 当有table组件使用selet时使用：使得下拉框与单元格刚好合适 */
td .layui-form-select {
	margin-top: -10px;
	margin-left: -15px;
	margin-right: -15px;
}
</style>
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
								<button class="layui-btn layuiadmin-btn-admin" data-type="batchdel">删除</button>
								<button class="layui-btn layuiadmin-btn-admin" data-type="add">添加</button>
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






	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>

	



	 <script>
		layui.use([ 'table', 'jquery', 'form', 'laypage' ], function() {
			var table = layui.table, 
			form = layui.form, 
			laypage = layui.laypage, 
			$ = layui.$;

			// 用于存放表格数据
			var tableData = new Array();
			$.ajax({
				url : "${ctx}/roles/page",
				type : "get",
				async : false,
				dataType : "json",
				success : function(result) {
					//分页完整功能
					laypage.render({
						elem : 'page',
						count : result.data.total,
						layout : [ 'count', 'prev', 'page', 'next', 'limit', 'skip' ],
						jump : function(obj, first) {
							$.ajax({
								url : "${ctx}/roles/page",
								type : "get",
								data : {
									size : obj.limit,
									page : obj.curr
								},
								dataType : "json",
								success : function(result) {
								 	table.reload('LAY-role-table', {
										// 更新数据
										data : result.data.rows,
									}); 	
								}
							});
						}
					});
				}
			});

			//表格数据展现
			table.render({
				elem : '#LAY-role-table',
				data : tableData,
				method : 'GET',
				cols : [ [
						{
							type : "checkbox",
							fixed : "left"
						},
						{
							field : "id",
							title : "ID",
							width : 80,
							sort : true
						},
						{
							field : "name",
							title : "角色名",
							edit : 'text'
						},
						{
							field : "role",
							title : "英文名称",
							edit : 'text'
						},
						{
							field : "roleType",
							title : "角色类型",
							align : 'center',
							templet : function(d) {
								//返回下拉框模版
								return '<select name="city" lay-filter="tableSelect" lay-verify="required" data-value="' + d.roleType + '" >\n' 
								+ '<option value=""></option>\n'
								+ '<option value="1">管理员</option>\n' 
								+ '<option value="2">员工</option>\n' 
								+ '</select>';
							}
						}, {
							field : "isShow",
							title : "是否可用",
							templet : '#switchTpl'
						}, {
							field : "description",
							title : "具体描述",
							edit : 'text'
						} ] ],
				page : false,
				done : function(res, curr, count) {
					var tableElem = this.elem.next('.layui-table-view');
					count || tableElem.find('.layui-table-header').css('overflow', 'auto');
					layui.each(tableElem.find('select'), function(index, item) {
						var elem = $(item);
						elem.val(elem.data('value')).parents('div.layui-table-cell').css('overflow', 'visible');
					});
					form.render();
				},
			});

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
			

			// 监听修改update到表格中
			form.on('select(tableSelect)', function(data) {
				var elem = $(data.elem);
				var trElem = elem.parents('tr');
				var tableData = table.cache['LAY-role-table'];
				// 更新到表格的缓存数据中，才能在获得选中行等等其他的方法中得到更新之后的值
				tableData[trElem.data('index')][elem.attr('name')] = data.value;
				// 其他的操作看需求 
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
	</script>

</body>
</html>