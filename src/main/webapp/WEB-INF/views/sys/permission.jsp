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


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限控制</title>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<div class="layui-form">
		
		</div>
		<table id="permission-info" class="table_th_search"></table>
	</div>
</div> 

<script type="text/html" id="templ-isShow">
 	 	<input type="checkbox" name="isShow" value="{{d.isShow}}" lay-skin="switch" lay-text="显示|不显示" lay-filter="isShow" {{ d.isShow == true ? 'checked' : '' }} >
</script>
<script type="text/html" id="templ-aboutPerson">
 	 	<button type="button" class="layui-btn layui-btn-sm" value="{{d.url}}">查看人员</button>
</script>

<script type="text/html" id="permission-toolbar">
  	<div class="layui-btn-container layui-inline layui-form">
    	<table>
			<tbody>
				<tr><td>一级菜单：</td><td><select class="layui-input" id="first-menus" lay-event="first-menus">
												<option value="" >请选择一级菜单</option></select></td><td>&nbsp;&nbsp;</td>
					<td>二级菜单：</td><td><select class="layui-input" id="second-menus" lay-event="second-menus">
												<option value="">请选择二级菜单</option></select></td><td>&nbsp;&nbsp;</td>
					<td>三级菜单：</td><td><select class="layui-input" id="third-menus"  lay-event="third-menus">
												<option value="">请选择三级菜单</option></select></td><td>&nbsp;&nbsp;</td>
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
  
  //第一个实例
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
    ,cols: [[ //表头
      {field: 'name', title: '身份',templet:'<span>{{d.name}}管理员</span>'}
      ,{field: 'isShow', title: '菜单是否显示',templet:'#templ-isShow'} 
      ,{field: 'name', title: '菜单名字', }
      ,{field: 'parentId', title: '所属菜单',  sort: true}
      ,{field: 'url', title: '页面跳转',templet:'' }
      ,	{title : '具体人员',templet:'#templ-aboutPerson'} 
    ]]
  });
	
  table.on('toolbar(permission-info)', function (obj) {
      var config = obj.config;
      var btnElem = $(this);
      var tableId = config.id;
      alert(config+" "+btnElem+"  "+tableId);
     /*   switch (obj.event) {
        case 'addTempData':  table.addTemp(tableId, function (trElem) { });
         					 break;
     
      } */ 
    });
	  function aboutPerson(){
		  alert(this.value);
	  }
});
</script>

	

</body>
</html>