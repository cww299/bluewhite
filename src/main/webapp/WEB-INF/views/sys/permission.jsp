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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限控制</title>
</head>
<body>
	
<div class="layui-card">
	<div class="layui-card-body" style="padding:0;">
		<!--  <table class="layui-table" >
			<thead>
				<tr>
					<th>序号</th>
					<th>菜单名称</th>
					<th>菜单身份</th>
					<th>访问路径</th>
					<th>父类id</th>
					<th>图标样式</th>
					<th>是否显示</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="tablecontent">
			</tbody>
		</table>
		<div id="pager"></div>  -->
		
		<div id="LAY-permission-table" class="table_th_search" lay-filter="LAY-permission-table"></div>
	</div>
</div>
	
<script>


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
			table.render({
				elem : '#LAY-permission-table',
				size : 'lg',
				url : "${ctx}/menuAll",
				page : {},        //开启分页
				loading : true,     //开启加载动画
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
			
			
			}
			
		)





 /*  jQuery(function($){
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
			 var data={
			} 
			this.init = function(){  //注册绑定事件
				self.events();
				self.loadPagination(data); 
			}
			this.loadPagination = function(data){  	//加载表格内容
		    var index;
		    var html ='';
		    $.ajax({
			      url:"${ctx}/menuAll",
			      data:data, 
			      type:"GET",
			      beforeSend:function(){
				 	  index = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					  });
				  }, 
	      		  success: function (result) {
	      			 $(result.data).each(function(i,o){
	      				 var order = i+1;
	      				html +='<tr>'
	      				+'<td class="edit price">'+order+'</td>'
	      				+'<td class="edit price">'+o.name+'</td>'
	      				+'<td class="edit price">'+o.identity+'</td>'
	      				+'<td class="edit price">'+o.url+'</td>'
	      				+'<td class="edit price">'+o.parentId+'</td>'
	      				+'<td class="edit price">'+o.icon+'</td>'
	      				+'<td class="edit price">'+o.isShow+'</td>'
						+'<td><button class="btn btn-xs btn-primary update">编辑</button></td></tr>'
						
	      			}); 
				   	layer.close(index);
				   	$("#tablecontent").html(html); 
			      },error:function(){
						layer.msg("加载失败！", {icon: 2});
						layer.close(index);
				  }
			  });
			}
			this.events = function(){
			}
   	}
	var login = new Login();
	login.init();
}) */
</script>



</body>
</html>