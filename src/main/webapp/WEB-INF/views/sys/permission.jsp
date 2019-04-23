<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>

	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/layer/layer.js"></script>
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限控制</title>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table id="permission-info" class="table_th_search" lay-filter="permission-info"></table>
	</div>
</div> 

<script type="text/html" id="templ-isShow">
 	 	<input type="checkbox" name="isShow" value="{{d.isShow}}" lay-skin="switch" lay-text="显示|不显示" lay-filter="cb" {{ d.isShow == true ? 'checked' : '' }}  >
</script>
<script type="text/html" id="templ-aboutPerson">
 	 	<button type="button" class="layui-btn layui-btn-sm" value="{{d.url}}" lay-event="aaa">查看人员</button>
</script>

<script type="text/html" id="permission-toolbar">
  	<div class="layui-btn-container layui-inline layui-form">
    	<table>
			<tbody>
				<tr id="tr-select"><td>一级菜单：</td><td><select class="layui-input" id="first-menus" lay-filter="first-menus">
												<option value="" >请选择一级菜单</option></select></td><td>&nbsp;&nbsp;</td>
					<td><span class="layui-btn layui-btn-sm" lay-event="sure">确定</span></td></tr>								
			</tbody>
		</table>
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
				var allMenu=[];   //存放所有菜单
				var first=[];		//存放一级菜单
				var second=[];	//存放二级菜单
				var third=[];		//存放三级菜单
				initToolBar();
				table.render({
					elem: '#permission-info'
				    ,cellMinWidth: 90
				    ,url: "${ctx}/getMenuPage" //数据接口
				    ,page: true  //开启分页
				    ,size:'lg'
				    ,toolbar:'#permission-toolbar'
				    ,request:{           
						pageName: 'page', //页码的参数名称，默认：page
						limitName: 'size' //每页数据量的参数名，默认：limit
					}
				    ,page: {
						limit:10
					}
				    ,parseData : function(ret) {    //
						return {
							code : ret.code,
							msg : ret.msg,
							count : ret.data.total, 
							data : ret.data.rows
						}
					}
				    ,done:function(obj){
				     	
				    }
				    ,cols: [[ //表头
				      {field: 'name', title: '身份',templet:'<span>{{d.name}}管理员</span>'}
				      ,{field: 'isShow', title: '菜单是否显示',templet:'#templ-isShow'} 
				      ,{field: 'name', title: '菜单名字', }
				      ,{field: 'parentId', title: '所属菜单',  sort: true}
				      ,{field: 'url', title: '页面跳转',templet:'' }
				      ,	{title : '具体人员',templet:'#templ-aboutPerson'} 
				    ]]
				});
				table.on('tool(permission-info)', function (obj) {
					layer.alert(JSON.stringify(obj.data));    
				});
				form.on('select', function (obj) {
					if(obj.value!=null && obj.value!='')
						showNextLeavel(obj.value);
					else
						hideNextLeavel(obj.value);
				});
				form.on('switch(cb)', function(obj){
				   layer.tips(this.value=='true'?'显示已开启':'显示已关闭', obj.othis);
				});
				function initToolBar(){
					 $.ajax({
						url : "${ctx}/getMenuPage?size=1000",
						type : "get",
						success : function(result) {
							var rows=result.data.rows;  
							for(var i=0;i<rows.length;i++){
								allMenu.push(rows[i]);
								if(rows[i].parentId==0)    //父id为0 为1级菜单
									first.push(rows[i]);
								else if(rows[i].url=="#"){  //父id不为0，url为#为2级菜单
									second.push(rows[i]);
								}
								else {   					//其他为三级菜单
									third.push(rows[i]);
								}
							}
							var html='';
							for(var i=0;i<first.length;i++){  //拼接一级菜单
								html+=('<option value="'+first[i].identity+'">'+first[i].name+'</option>');
							}
							$('#first-menus').append(html);
						} 
					 });
				}
				function showNextLeavel(identity){
					
				}
	}
);
</script>

	

</body>
</html>