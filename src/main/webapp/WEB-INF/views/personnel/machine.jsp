<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">

<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script> 
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>  
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>考勤机设置</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>

<body>

<div class="layui-card">
	<div class="layui-card-body">
	<form class="layui-form" action="">
		<table>
			<tr>
				<td>搜索编号：</td>
				<td>
					<div class="layui-inline">
						<input class="layui-input" name="id" id="demoReload" autocomplete="off">
					</div>
				</td>
				<td>&nbsp;卡机选择：</td>
				<td>
					<select name="interest" id="select1" lay-filter="aiha" class="layui-inline">
						<option value="192.168.1.204">三楼打卡机</option>
						<option value="192.168.1.250">二楼打卡机</option>
						<option value="192.168.1.205">一楼打卡机</option>
					</select>
				</td>
				<td>&nbsp;</td>
				<td>
					<button type="button" class="layui-btn" id="search" data-type="reload" >搜索</button>
				</td>
				<td style="width:58%;"></td>
				<td>
					<button type="button" class="layui-btn" id="synchronization" data-type="synchronization">同步</button>
				</td>
			</tr>
		</table>
		</form>
		<table class="layui-hide" lay-filter="test3" id="test"> 
		</table>
	</div>
</div>
	
	<script>
	layui.use('table', function(){
		 var $ = layui.$;
		  var table = layui.table;
		  var form = layui.form;
		  table.render({
		    elem: '#test'
		    ,url:'${ctx}/personnel/getAllUser'
		    ,toolbar: '#toolbarDemo'
		    ,where: {address:'192.168.1.204'} 
		    ,method:'GET'
		  ,parseData: function(res){ //res 即为原始返回的数据
	             return {
	               "code": res.code, //解析接口状态
	               "msg": res.message, //解析提示文本
	               "count": res.data.total, //解析数据长度
	               "data": res.data //解析数据列表
	             };
	           }
		    ,cols: [[
		      {field:'number', title: '编号', sort: true,align: 'center'}
		      ,{field:'name', title: '用户名',align: 'center',edit: 'text'}
		      ,{field:'privilege',align: 'center',  title: '权限', sort: true,templet:function(d){
	             	if(d.privilege=='3') return '管理员'; 
	           	else if(d.privilege=='0') return '普通用户';}
				}
		      ,{field:'enabled',align: 'center', title: '是否启用'}
		      ,{fixed: 'right', title:'操作', align: 'center', toolbar: '#barDemo'}
		      ]]
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
			    	  var postData={
								number:data.number,
								address:$("#select1").val(),
						}
			    	  console.log(postData)
			    	index = layer.confirm('<div>输入密码:<input id="password" /></div>', {btn: ['确定', '取消']},function(){
			    		if($("#password").val()==3116){
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
			    		}
			      });
			    }
		  });
		/*搜索*/
		  
		  	active = {
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
					index = layer.load(
							1, {shade: [0.1,'black'], offset:'150px'})
				},
				success:function(result){
					if(0==result.code){
						layer.msg(result.message, {icon: 1,offset:'150px'});
						layer.close(index);
					}else{
						layer.msg(result.message, {icon: 2,offset:'150px'});
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

</script>
</html>