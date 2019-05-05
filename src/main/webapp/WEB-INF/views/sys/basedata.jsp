<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础数据</title>
</head>
<body>
	
<div class="layui-card">
	<div class="layui-card-body" style="height:800px;">
		<table class="layui-form">
			<tr><td>数据类型：</td><td>&nbsp;&nbsp;</td>
				<td id="tdSelect"><select><option value="">请选择</option></select></td>			<!-- 此处select的作用仅仅是为了页面加载时，保证页面不出现延迟加载，真正的select会在获取数据后渲染模板，重新填充 -->
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" lay-submit class="layui-btn layui-btn-sm">查找</button></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" id="addDataType">添加数据类型</button></td>
			</tr>
		</table>
		<table class="table_th_search" id="baseDataTable" lay-filter="baseDataTable"></table>
	</div>
</div>
			
<!-- select选择的模板 -->
<script type="text/html" id="selectTpl">
	<select id="selectId" lay-verify="required" name="baseDataType">
		<option value="">请选择</option>
		{{# layui.each(d,function(index,item){  }}
			<option value="{{item.value}}">{{item.name}}</option>
		{{# }); }}
	</select>
</script>
<!-- 表格工具栏 -->
<script type="text/html" id="baseDataToolBar">
	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除基础数据</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增基础数据</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑数据</span>
		<span class="layui-btn layui-btn-sm" lay-event="more">查看下级数据</span>
	</div>
</script>
<!-- 添加、编辑基础数据的弹窗模板 -->
<script type="text/html" id="addEditTpl">
	<div class="layui-form" >
		<div class="layui-form-item">
			<label class="layui-form-label">数据ID</label>
			<div class="layui-input-block">
				<input type="text" readonly value="{{ d.id }}" id="baseDateId"  class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">数据名称</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.name }}" id="baseDataName" placeholder="请输入" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">是否可用</label>
			<div class="layui-input-block">
				<input type="checkbox" lay-skin="switch" id="baseDataFlag" lay-text="可用|不可用" {{ d.flag==1?'checked':'' }} >
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">类型</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.type }}" id="baseDataType" placeholder="请输入" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">排序</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.ord }}" id="baseDataOrd" placeholder="请输入" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">父ID</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.parentId }}" id="baseDataParentId" readonly class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">remark</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.remark }}" id="baseDataRemark" placeholder="请输入" class="layui-input">
			</div>
		</div>
	</div>
</script>
<!-- 查看下级数据模板 -->
<script type="text/html" id="moreTpl">
	<div>
		<table class="layui-table" lay-data="{height:315, url:'${ctx}/basedata/children?id={{d}}', page:false, id:'children1' }" lay-filter="children1">
 		 <thead>
    		<tr>
     		 <th lay-data="{field:'id', width:80, sort: true}">数据ID</th>
     		 <th lay-data="{field:'name', width:80}">名称</th>
     		 <th lay-data="{field:'ord', width:80, sort: true}">排序</th>
      		 <th lay-data="{field:'parentId'}">父id</th>
      		 <th lay-data="{field:'remark'}">remark</th>
      		 <th lay-data="{field:'flag', sort: true}">是否可用</th>
     		 <th lay-data="{field:'type', sort: true}">类型</th>
    		</tr>
 		 </thead>
		</table>
	</div>
</script>
<script>
var allType=[];


layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug'
}).define(
	[ 'tablePlug'],
	function() {
		var $ = layui.jquery
		, table = layui.table
		, laytpl = layui.laytpl
		, form = layui.form
		, tablePlug = layui.tablePlug; 		//表格插件
		form.render();
		function getDataType(){ 
			$.ajax({
				url:"${ctx}/basedata/types",
				async:false,
				success:function(result){
					if(0==result.code){
						var html='';
						var tpl=selectTpl.innerHTML;
						laytpl(tpl).render(result.data,function(h){
							html=h;
						})
						$('#tdSelect').html(html);
						form.render();
					}	
					else
						layer.msg("获取数据异常",{icon:2});
				}
			})
		}
		form.on('submit',function(obj){
			table.reload('baseDataTable',{
				url:"${ctx}/basedata/list?type="+obj.field.baseDataType
			})
		});
		$('#addDataType').on('click',function(){
			layer.msg("该功能还没有完善",{icon:2});
		});
		getDataType();
		table.on('toolbar(baseDataTable)',function(obj){ 
			switch(obj.event){
			case 'add': addEidt(null); break;
			case 'edit': addEidt('edit'); break;
			case 'delete': break;
			case 'more': more(); break;
			}
		});
		var choosed=[];							//存放复选框选中的对象，用于删除、编辑
		table.on('checkbox(baseDataTable)', function(obj){
			if(obj.type=='all' ){ 
				if(obj.checked){
					var row=layui.table.cache.baseDataTable;
					choosed=[];
					for(var i=0;i<row.length;i++){
						choosed.push(row[i]);
					}
				}else
					choosed=[];
			}else{ 
				if(obj.checked)
					choosed.push(obj.data);
				else
					for(var i=0;i<choosed.length;i++){
						if(choosed[i].id==obj.data.id)
							choosed.splice(i,1);
					}
			}
		});
		function more(){
			if(choosed.length>1){
				layer.msg("无法同时查看多条信息",{icon:2});
				return;
			}
			var html="";
			var tpl=moreTpl.innerHTML;
			laytpl(tpl).render(choosed[0].id,function(h){
				html=h;
				table.render();
				table.reload('children1');
			})
			layer.open({
				title:'子数据',
				area:['80%','80%'],
				content:html
			})
			
			/* $.ajax({
				url:"${ctx}/basedata/children?id="+choosed[0].id,
				success:function(result){
					console.log(result.data)
				}
			}) */
		}
		function addEidt(type){
			var data={id:'',name:'',ord:'',parentId:'',remark:'',type:'',flag:0};
			var title="新增数据";
			var html="";
			var tpl=addEditTpl.innerHTML;
			if(type=='edit'){
				if(choosed.length>1){
					layer.msg("无法同时编辑多条信息",{icon:2});
					return;
				}
				data=choosed[0];
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			layer.open({
				title:title,
				area:['30%','60%'],
				btn:['确认','取消'],
				content:html,
				yes:function(){
					
				}
			})
			form.render();
		}
		function deletes(){
			
		}
		// tr点击触发复选列点击
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
		table.render({
			elem : '#baseDataTable',
			size : 'lg',
			page : false,
			height : '700',
			loading : true,  
			toolbar : "#baseDataToolBar",
			parseData : function(ret) {    		
				return {
					code : ret.code,
					msg : ret.message,
					data : ret.data,
				}
			},
			cols:[[
			       {type: 'checkbox',align : 'center',fixed: 'left'},
					{field : "id",title : "数据id",sort : true,align : 'center'},
					{field : "name",title : "名称",sort : true,align : 'center'},
					{field : "ord",title : "排序",align : 'center'},
					{field : "parentId",title : "父id",align : 'center'},
					{field : "remark",title : "remark",align : 'center'},
					{field : "flag",title : "是否可用",align : 'center'},
					{field : "type",title : "类型",align : 'center'}
			      ]],
			 done:function(){
				 choosed=[];
			 }
		});
		
		
	}//end function
)//end defind

</script>



</body>
</html>