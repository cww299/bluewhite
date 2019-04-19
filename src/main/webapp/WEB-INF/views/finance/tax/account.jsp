<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>报销申请</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet"
	href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
</head>

<body>
	<section id="main-wrapper" class="theme-default">
		<%@include file="../../decorator/leftbar.jsp"%>
		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">报销申请</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i> <i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="panel-body">
							<div
								class="layui-form layui-card-header layuiadmin-card-header-auto">
								<div class="layui-form-item">
									<table>
										<tr>
											<td>报销人:</td>
											<td><input type="text" name="Username" id="firstNames"
												class="form-control search-query name" /></td>
											<td>&nbsp&nbsp</td>
											<td>报销内容:</td>
											<td><input type="text" name="content"
												class="form-control search-query" /></td>
											<td>&nbsp&nbsp</td>
											<td><select class="form-control" name="expenseDate"
												id="selectone"><option value="2018-10-08 00:00:00">付款日期</option></select></td>
											<td>&nbsp&nbsp</td>
											<td>开始:</td>
											<td><input id="startTime" name="orderTimeBegin"
												placeholder="请输入开始时间" class="form-control laydate-icon">
											</td>
											<td>&nbsp&nbsp</td>
											<td>结束:</td>
											<td><input id="endTime" name="orderTimeEnd"
												placeholder="请输入结束时间" class="form-control laydate-icon">
											</td>
											<td>&nbsp&nbsp</td>
											<td>是否核对:
											<td><select class="form-control" name="flag"><option
														value="">请选择</option>
													<option value="0">未核对</option>
													<option value="1">已核对</option></select></td>
											<td>&nbsp&nbsp</td>
											<td>
												<div class="layui-inline">
													<button class="layui-btn layuiadmin-btn-admin" lay-submit
														lay-filter="LAY-role-search">
														<i
															class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
													</button>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<table id="demo" class="table_th_search" lay-filter="test"></table>
						</div>

					</div>
				</div>
			</div>
		</section>
	</section>
	</section>



	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	<script>




layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug'
}).define(
		[ 'tablePlug', 'laydate','element' ],function () {

		      var $ = layui.jquery
		        , layer = layui.layer //弹层
		        , form = layui.form //弹层
		        , table = layui.table //表格
		        , laydate = layui.laydate //日期控件
		        , tablePlug = layui.tablePlug //表格插件
		        , element=layui.element;
		      
		      laydate.render({
					elem : '#startTime',
					type : 'datetime',
				});
		      laydate.render({
					elem : '#endTime',
					type : 'datetime',
				});
		        var htmls = '<option value="">请选择</option>'; //全局变量
				var data={
						page:1,
				  		size:1000,
				}
	            $.ajax({
					url:'${ctx}/system/user/pages',
					data:data,
					type:"GET",
					async:false,
					beforeSend:function(){
					index = layer.load(1, {
						shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
						},
					success:function(result){
					$(result.data.rows).each(function(i,o){
						htmls +='<option value='+o.id+'>'+o.userName+'</option>'
						})
					layer.close(index);
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
							});
		     // 处理操作列
		      var fn1 = function (field) {
		          return function (d) {
						 return [
							'<select name="city" lay-filter="city_selecte" lay-search="true" data-value="' + d.userId + '">'
							+htmls+
							'</select>'
				            ].join('');
		            
		          };
		        };
		        
		        var fn2 = function (field) {
			          return function (d) {
			        	  return ['<select name="citye2" lay-filter="city_selecte" lay-search="true" data-value="' + d.budget + '">',
								'<option value="0">请选择</option>',
								'<option value="1">预算</option>',
									'</select>' 
									].join('');
			            
			          };
			        }; 
			        var fn3 = function (field) {
				          return function (d) {
				        	  return ['<select name="citye3" lay-filter="city_selecte" lay-search="true" data-value="' + d.settleAccountsMode + '">',
									'<option value="0">请选择</option>',
									'<option value="1">现金</option>',
									'<option value="2">月结</option>',
										'</select>' 
										].join('');
				            
				          };
				        }; 
				  /* tablePlug.smartReload.enable(true);//智能重载 */
		        table.render({
		            elem: '#demo'
		            // , height: 'full-120'
		            , size: 'lg'
		            , url: '${ctx}/fince/getExpenseAccount' //数据接口
		            // , data: data
		            , title: '用户表'
		            , page: {
		            	
		            } //开启分页
		            , loading: true
		            , toolbar: '#toolbarDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		            /* , totalRow: true //开启合计行 */
		            , cellMinWidth: 90
		            // 是否开启字段筛选的记忆功能，支持true/false/'local'/'session'/其他 开启的情况下默认是session，除非显式的指定为'local'
		            , colFilterRecord: true
		            // 开启智能重载
		            , smartReloadModel: true
		            // 设置开启部分选项不可选
		            // 设置表格的主键（主要用在记录选中状态还有不可操作记录的时候用
		            , primaryKey: 'id'
		            , checkDisabled: {
		              enabled: true,
		              data: [10000, 10001, 10002, 10003, 10004, 10005, 10009]
		            }
		            // , pageLanguage: 'zh-TW' // 需要自己定义对应的文本
		            // , pageLanguage: 'en' // tablePlug里面已经定义了，如果觉得不满意可以用tablePlug.set去更新默认的配置;
		            // , pageLanguage: true // 无效的设置方式，只支持字符串或者对象的
		            // 也可以针对某个表格有特殊的配置如下面对象的设置方法,但是如果没有必要单独的自定义建议使用直接赋值成语言名称的字符串形式
		            , done: function () {
		              var tableView = this.elem.next();

		              tableView.find('.layui-table-grid-down').remove();
		              var totalRow = tableView.find('.layui-table-total');
		              var limit = this.page ? this.page.limit : this.limit;
		              layui.each(totalRow.find('td'), function (index, tdElem) {
		                tdElem = $(tdElem);
		                var text = tdElem.text();
		                if (text && !isNaN(text)) {
		                  text = (parseFloat(text) / limit).toFixed(2);
		                  tdElem.find('div.layui-table-cell').html(text);
		                }
		              });
		            },
		            parseData: function (ret) {
		              return {
		                code: ret.code,
		                msg: ret.msg,
		                count: ret.data ? (ret.data.total || 0) : 0,
		                data: ret.data.rows
		              }
		            }
		            , checkStatus: {}
		            // , autoSort: false
		            // , initSort: {
		            //   field: 'id' //排序字段，对应 cols 设定的各字段名
		            //   , type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
		            // }
		            , cols : [ 
						[  {
							type: 'checkbox',
							align : 'center',
							fixed: 'left'
							}, {
							field : "content",
							title : "报销内容",
							align : 'center',
							edit : 'text'
						}, {
							field : "userId",
							title : "报销人",
							align : 'center',
							search: true, 
							edit: false, 
							type: 'normal',
							templet:fn1('city')
						}, {
							field : "budget",
							title : "是否是预算",
							align : 'center',
							search: true, 
							edit: false, 
							type: 'normal',
							templet:fn2('citye2')
						}, {
							field : "money",
							title : "付款日要付金额",
							edit : 'text'
						}, {
							field : "expenseDate",
							title : "付款日期",
							edit : 'text'
						}, {
							field : "withholdReason",
							title : "扣款事由",
							edit : 'text'
						}, {
							field : "withholdMoney",
							title : "扣款金额",
							edit : 'text'
						}, {
							field : "settleAccountsMode",
							title : "结款模式",
							search: true, 
							edit: false, 
							type: 'normal',
							templet:fn3('citye3')
						}   ] ]
		            ,done : function(res, curr, count) {
		            	 var tableView = this.elem.next();
						var tableElem = this.elem.next('.layui-table-view');
						layui.each(tableElem.find('select'), function(index, item) {
							var elem = $(item);
							elem.val(elem.data('value'));
						});
						form.render();
		     // 初始化laydate
		   		  layui.each(tableView.find('td[data-field="expenseDate"]'), function (index, tdElem) {
		            tdElem.onclick = function (event) {
		              layui.stope(event)
		            };
		            laydate.render({
		              elem: tdElem.children[0],
		              // closeStop: tdElem,
		              format: 'yyyy-MM-dd HH:mm:ss',
		              done: function (value, date) {
		            	var id=table.cache[tableView.attr('lay-id')][index].id
		                var trElem = $(this.elem[0]).closest('tr');
		                table.cache.demo[trElem.data('index')]['expenseDate'] = value;
		                if(id!=undefined){
							var postData={
							    	id:id,
							    	expenseDate:value,
								}
							        $.ajax({
									url:"${ctx}/fince/updateExpenseAccount",
									data:postData,
									type:"POST",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										if(0==result.code){
											layer.msg(result.message, {icon: 1});
											layer.close(index);
										}else{
											layer.msg(result.message, {icon: 2});
											layer.close(index);
										}
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
		                }
		              }
		            })
		            	  
		          })
		         
					},
		          
		          });
		        
		        
		        
		     // 监听表格中的下拉选择将数据同步到table.cache中
				form.on('select(city_selecte)', function(data) {
					var selectElem = $(data.elem);
					var tdElem = selectElem.closest('td');
					var trElem = tdElem.closest('tr');
					var tableView = trElem.closest('.layui-table-view');
					table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
					var id=table.cache[tableView.attr('lay-id')][trElem.data('index')].id
					if(id!=undefined){
					var postData={
					    	id:id,
					    	[tdElem.data('field')]:data.value,
						}
					        $.ajax({
							url:"${ctx}/fince/updateExpenseAccount",
							data:postData,
							type:"POST",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								if(0==result.code){
									layer.msg(result.message, {icon: 1});
									layer.close(index);
								}else{
									layer.msg(result.message, {icon: 2});
									layer.close(index);
								}
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						}); 
					}
				});
		      //监听头工具栏事件
		        table.on('toolbar(test)', function (obj) {
		          var config = obj.config;
		          var btnElem = $(this);
		          var tableId = config.id;
		          console.log(obj)
		          // var tableView = config.elem.next();
		          switch (obj.event) {
		            case 'addTempData':
		              table.addTemp(tableId, function (trElem) {
		                // 进入回调的时候this是当前的表格的config
		                var that = this;
		                // 初始化laydate
		                layui.each(trElem.find('td[data-field="expenseDate"]'), function (index, tdElem) {
		                  tdElem.onclick = function(event) {layui.stope(event)};
		                  laydate.render({
		                    elem: tdElem.children[0],
		                    format: 'yyyy-MM-dd HH:mm:ss',
		                    done: function (value, date) {
		                      var trElem = $(this.elem[0]).closest('tr');
		                      table.cache[that.id][trElem.data('index')]['expenseDate'] = value;
		                    }
		                  })
		                })
		              });
		              break;
		            case 'getTempData':
		              $(table.getTemp(tableId).data).each(function(i,o){
		            		 if(o.content==undefined){
		            			return layer.msg("第"+i+"条数据请填写报销内容", {icon: 2});
		            		 }
		            		 if(o.money==undefined){
		 						return layer.msg("第"+i+"条数据请填写报销金额", {icon: 2});
		 					}
		            	  var postData = {
		            			content:o.content,
		  						budget:o.budget,
		  						money:o.money,
		  						expenseDate:o.expenseDate,
		  						withholdReason:o.withholdReason,
		  						withholdMoney:o.withholdMoney,
		  						settleAccountsMode:o.settleAccountsMode,
		  						userId:o.userId,
		            	  }
		            	  var index;
							  $.ajax({
								url:"${ctx}/fince/addExpenseAccount",
								data:postData,
								type:"POST",
								async:false,
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									if(0==result.code){
									layer.msg("新增成功！", {icon: 1});
									layer.close(index);
									table.reload('demo', {
								           where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
								          }
								        });
									layer.close(index);
									}else{
										layer.msg("新增失败！", {icon: 2});
										layer.close(index);
									}
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							}); 
		              })
		              break;
		            case 'getChecked':
		              layer.alert(JSON.stringify(table.checkStatus(tableId).data));
		              break;
		            case 'deleteSome':
		              // 获得当前选中的，不管它的状态是什么？只要是选中的都会获得
		              var checkedIds = tablePlug.tableCheck.getChecked(tableId);
		              
		              layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function () {
		                 var postData = {
								ids:checkedIds,
						} 
		                 $.ajax({
							url:"${ctx}/fince/deleteExpenseAccount",
							data:postData,
							traditional:true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								if(0==result.code){
									table.reload('demo', {
										
									});
								layer.msg("删除成功！", {icon: 1});
								layer.close(index);
								}else{
									layer.msg(result.message, {icon: 2});
									layer.close(index);
								}
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						}); 
		              });
		              break;
		          }
		        });   
		      
		      //监听单元格编辑
				  table.on('edit(test)', function(obj){
				    var value = obj.value //得到修改后的值
				    ,data = obj.data //得到所在行所有键值
				    ,field = obj.field; //得到字段
				    if(data.id!=undefined){
				    var postData={
				    	id:data.id,
						content:data.content,
						userId:data.userId,
						budget:data.budget,
						money:data.money,
						expenseDate:data.expenseDate,
						withholdReason:data.withholdReason,
						withholdMoney:data.withholdMoney,
						settleAccountsMode:data.settleAccountsMode,
					}
				       $.ajax({
						url:"${ctx}/fince/updateExpenseAccount",
						data:postData,
						type:"POST",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						success:function(result){
							if(0==result.code){
								layer.msg(result.message, {icon: 1});
								layer.close(index);
							}else{
								layer.msg(result.message, {icon: 2});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});  
				    }
				  });
		      
		      
				//监听搜索
					form.on('submit(LAY-role-search)', function(data) {
						var field = data.field;
							 $.ajax({
								url : "${ctx}/fince/getExpenseAccount",
								type : "get",
								data : field,
								dataType : "json",
								success : function(result) {
								 	table.reload('demo', {
										 where:  field
									}); 	
								}
							});
					});
		        
		}
		)
</script>
	<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container layui-inline">
    <span class="layui-btn layui-btn-sm" lay-event="deleteSome">批量删除</span>
    <span class="layui-btn layui-btn-sm" lay-event="addTempData">添加临时数据</span>
<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="getTempData">保存</span>
  </div>
</script>
</body>
</html>