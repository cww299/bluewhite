<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>

<title>角色管理</title>





</head>

<body>
	
<div class="layui-card">
	<div class="layui-card-body">
		<div class="layui-form layui-card-header layuiadmin-card-header-auto">
			<table>
				<tr>
					<td>用户名：</td>
					<td><input type="text" class="layui-input" placeholder="请输入用户名"></td>
					<td>角色名：</td>
					<td><select name="city" lay-verify="required">
						        <option value=""></option>
						        <option value="0">北京</option>
						      </select></td>
				</tr>
			</table>
		</div>
		
	
	
		<div id="LAY-role-table" class="table_th_search" lay-filter="LAY-role-table"></div>
	</div>

	
</div>
			
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


	<script>
		layui.config({
			base : '${ctx}/static/layui-v2.4.5/'
		}).extend({
			tablePlug : 'tablePlug/tablePlug'
		}).define(
				[ 'tablePlug', 'laydate' ],
				function() {
					var $ = layui.jquery
					, layer = layui.layer //弹层
					, form = layui.form //表单
					, table = layui.table //表格
					, laydate = layui.laydate //日期控件
					, tablePlug = layui.tablePlug; //表格插件
					
					// 处理操作列
					var fn1 = function(field) {
						return function(data) {
							return ['<select name="citye" lay-filter="city_selecte" lay-search="true">',
								'<option value="管理员">管理员</option>',
								'<option value="超级管理员">超级管理员</option>',
									'</select>' 
									].join('');
						};
					};
					
					table.render({
						elem : '#LAY-role-table',
						size : 'lg',
						url : '${ctx}/roles/page' ,
						page : {},        //开启分页
						loading : true,     //开启加载动画
						toolbar : '#toolbarDemo',//开启工具栏，此处显示默认图标
						colFilterRecord : true,// 开启智能重载
						smartReloadModel : true,// 设置开启部分选项不可选
						done : function() {      //加载完后的动作
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
						parseData : function(ret) {    //
							return {
								code : ret.code,
								msg : ret.msg,
								count : ret.data.total, 
								data : ret.data.rows
							}
						},
						checkStatus : {},
						cols : [ 
						[   {type: 'checkbox',align : 'center',fixed: 'left'},
							{field : "id",title : "ID",width : 80,sort : true}, 
							{field : "name",title : "角色名",edit : 'text'}, 
							{field : "role",title : "英文名称",edit : 'text'}, 
							{field : "roleType",title : "角色类型",align : 'center',search: true,edit: false, type: 'normal',templet:fn1('citye')}, 
							{field : "isShow",title : "是否可用",templet : '#switchTpl'},
							{field : "description",title : "具体描述",edit : 'text'} 
						] ]
					});

					// 监听表格中的下拉选择将数据同步到table.cache中
					form.on('select(city_selecte)', function(data) {
						var selectElem = $(data.elem);
						var tdElem = selectElem.closest('td');
						var trElem = tdElem.closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
					});

					
					//监听头工具栏事件
				    table.on('toolbar(LAY-role-table)', function (obj) {
				      var config = obj.config;
				      var btnElem = $(this);
				      var tableId = config.id;
				      // var tableView = config.elem.next();
				      switch (obj.event) {
				        case 'addTempData':  table.addTemp(tableId, function (trElem) { });
				         					 break;
				        case 'getTempData':
									          layer.alert('临时数据:' + JSON.stringify(table.getTemp(tableId).data));
									          break;
				        case 'cleanTempData':
									          table.cleanTemp(tableId);  layer.msg('临时数据已删除');
									          break;
				        case 'openSelect':
								          layer.open({
								            type: 1,
								            title: '测试下拉效果单页面',
								            area: ['3000px', '160px'],
								            content: '<div class="layui-form" style="padding: 20px;"><select><option value="1">北京</option><option value="2">上海</option><option value="3">广州</option><option value="4">深圳</option></select></div>',
								            success: function (layero, index) {
								              form.render();
								            }
								          });
				          					break;
				        case 'openIframeSelect':
								          layer.open({
								            type: 2,
								            title: '测试下拉效果iframe',
								            shade: false,
								            area: ['300px', '160px'],
								            content: 'testIframe.html?time=' + new Date().getTime(),
								            success: function (layero, index) {
								            }
								          });
								          break;
				        case 'autoReload':
								          if (!layui._autoReloadIndex) {
								            layui._autoReloadIndex = setInterval(function () {
								              table.reload(tableId, {});
								            }, 300);
								          } else {
								            clearInterval(layui._autoReloadIndex);
								            layui._autoReloadIndex = 0;
								          }
								          break;
				        case 'LAYTABLE_EXPORT':  // 点击导出图标的时候
				          
								          $(this).find('.layui-table-tool-panel li').unbind('click').click(function () {
								            var dataTemp = table.cache[tableId];  // 干掉了原始的事件了，自己定义需要的
								            if (!dataTemp || !dataTemp.length) 
								              dataTemp = [{}];   // 处理如果没有数据的时候导出为空的excel，没有导出thead的问题
								            table.exportFile(tableId, dataTemp, $(this).data('type'));// 实际可以根据需要还可以直接请求导出全部，或者导出选中的数据而不是只导出当前的页的数据
								          });
								          break;
				        case 'getChecked':
								          layer.alert(JSON.stringify(table.checkStatus(tableId).data));
								          break;
				        case 'getCheckedStatus':
								          var status = table.checkStatus(tableId).status;
								          layer.alert('新增的：' + JSON.stringify(status[tablePlug.CHECK_TYPE_ADDITIONAL]) + '<br>'
								            + '删除的：' + JSON.stringify(status[tablePlug.CHECK_TYPE_REMOVED]));
								          break;
				        case 'deleteSome':
								          // 获得当前选中的，不管它的状态是什么？只要是选中的都会获得
								          var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								          layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function () {
								            layer.alert('do something with: ' + JSON.stringify(checkedIds));
								          });
								          break;
				        case 'jump':
								          var pageCurr = btnElem.data('page');
								          table.reload(config.id, {url: 'json/data1' + pageCurr + '.json', page: {curr: pageCurr}});
								          break;
				        case 'reload':
							          var options = {page: {curr: 1}};
							          var urlTemp = btnElem.data('url');
							          if (urlTemp) {
							            options.url = 'json/' + urlTemp + '.json';
							          }
							          var optionTemp = eval('(' + (btnElem.data('option') || '{}') + ')');
			
							          table.reload(config.id, $.extend(true, options, optionTemp));
							          break;
				        case 'reloadIns':
							          tablePlug.getIns(config.id).reload({
							            // page: false
							          });
							          break;
				        case 'setDisabled':
							          // tablePlug.tableCheck.disabled(config.id, [10003, 10004, 10010]);
							          // table.reload(tableId, {});
							          tablePlug.disabledCheck(tableId, [10003, 10004, 10010]);
							          break;
				        case 'setDisabledNull':
							          tablePlug.disabledCheck(tableId, false);
							          break;
				        case 'ranksConversion':
							          // 表格行列转换
							          break;
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