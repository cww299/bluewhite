<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>考勤总汇</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
</head>

<body>
	<section id="main-wrapper" class="theme-default">
		<%@include file="../decorator/leftbar.jsp"%>
		<!--main content start-->
		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">总汇信息</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i>
								<i class="fa fa-chevron-down"></i>
							</div>
						</div>

						<div class="panel-body">
							<table>
								<tr>
									<td>
										<div class="demoTable">
											搜索编号：
											<div class="layui-inline">
												<input class="layui-input" name="id" id="demoReload" autocomplete="off">
											</div>
									</td>
									<td>
										<form class="layui-form" action="">
											<select name="interest" id="select1" lay-filter="aiha">
												<option value="192.168.1.204">三楼打卡机</option>
												<option value="192.168.1.250">二楼打卡机</option>
												<option value="192.168.1.205">一楼打卡机</option>
											</select>
										</form>
									</td>
									<td>
										<button class="layui-btn" id="search" data-type="reload">搜索</button>
										</div>
									</td>
									<td style="width: 63%;"></td>
									<td>
										<button class="layui-btn" id="synchronization" data-type="synchronization">同步</button>
									</td>
								</tr>
							</table>
							<table class="layui-hide" lay-filter="test3" id="test">

							</table>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>

	</section>



<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<script>
	layui.use('table', function(){
		  var table = layui.table;
		  var form = layui.form;
		  table.render({
		    elem: '#test'
		    ,url:'${ctx}/personnel/getAllUser'
		    ,where: {address:'192.168.1.204'} 
		    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		    , method:'GET'
		  /*,parseData: function(res){ //res 即为原始返回的数据
	         	res.data.code=0
	             return {
	               "code": res.data.code, //解析接口状态
	               "msg": res.data.message, //解析提示文本
	               "count": res.data.total, //解析数据长度
	               "data": res.data //解析数据列表
	             };
	           }*/
		    ,cols: [[
		      {field:'number', width:'25%', title: '编号', sort: true,align: 'center'}
		      ,{field:'name', width:'25%', title: '用户名',align: 'center',edit: 'text'}
		      ,{field:'privilege', width:'25%',align: 'center',  title: '权限', sort: true,templet:function(d){
	             	if(d.privilege=='3') return '管理员'; 
	           	else if(d.privilege=='0') return '普通用户';}
				}
		      ,{field:'enabled', width:'20%',align: 'center', title: '是否启用'}
		      ,{fixed: 'right', title:'操作', align: 'center', toolbar: '#barDemo', width:'5%'}]]
	          ,id: 'testReload'
		    ,page: false
		  });
		  
		//监听单元格编辑
		  table.on('edit(test3)', function(obj){
		    var value = obj.value //得到修改后的值
		    ,data = obj.data //得到所在行所有键值
		    ,field = obj.field; //得到字段
		    var postData={
					number:data.number,
					[field]:value,
					address:$("#select1").val(),
					isPrivilege:data.privilege,
					enabled:data.enabled
			}
		     $.ajax({
				url:"${ctx}/personnel/updateUser",
				data:postData,
				type:"GET",
				beforeSend:function(){
					index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						});
				},
				success:function(result){
					if(0==result.code){
						layer.msg('[ID: '+ data.number +'] ' + field + ' 字段更改为：'+ value); 
						layer.close(index);
					}else{
						layer.msg("修改失败！", {icon: 2});
						layer.close(index);
					}
				},error:function(){
					layer.msg("操作失败！", {icon: 2});
					layer.close(index);
				}
			}); 
		  });
		
		/*刪除 */
		  table.on('tool(test3)', function(obj){
			    var data = obj.data;
			    if(obj.event === 'del'){
			    	var index;
			    	index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
			    	  var postData={
								number:data.number,
								address:$("#select1").val(),
						}
			    	  $.ajax({
							url:"${ctx}/personnel/deleteUser",
							data:postData,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								if(0==result.code){
									layer.msg("删除成功！", {icon: 1});
									obj.del();
									layer.close(index);
								}else{
									layer.msg("删除失败！", {icon: 2});
									layer.close(index);
								}
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
			      });
			    }
		  });
		/*搜索*/
		  var $ = layui.$, active = {
				    reload: function(){
				      var demoReload = $('#demoReload');
				      //执行重载
				      table.reload('testReload', {
				        where: {
				            number: demoReload.val(),
				            address:$("#select1").val()
				        }
				      });
				    }
				  };
		  
		$('#search').on('click', function(){
			    var type = $(this).data('type');
			    active[type] ? active[type].call(this) : '';
			  });
			});
	
		/*同步*/
		$('#synchronization').on('click',function(){
			var postData={
					address:$("#select1").val(),
			}
		  $.ajax({
				url:"${ctx}/personnel/syncAttendanceUser",
				data:postData,
				type:"GET",
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
		})
</script>
<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-danger layui-btn-xs"  lay-event="del">删除</a>
</script>
</body>
</html>