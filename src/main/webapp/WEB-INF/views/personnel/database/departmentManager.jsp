<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础数据</title>
</head>
<body>
	
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr><td>查找部门：</td><td>&nbsp;&nbsp;</td>
				<td><input type="text" class="layui-input" name=""></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" lay-submit class="layui-btn" lay-filter="find">查找</button></td>
			</tr>
		</table>
		<table class="layui-table" id=departmentTable lay-filter="departmentTable"></table>
	</div>
</div>
</body>
<!-- 部门工具栏 -->
<script type="text/html" id="departmentToolbar">
	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除部门</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增部门</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑部门</span>
		<span class="layui-badge">提示：双击查看部门职位</span>
	</div>
</script>
<!-- 职位工具栏 -->
<script type="text/html" id="positionToolbar">
	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除职位</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增职位</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑职位</span>
	</div>
</script>
<!-- 新增、修改部门弹窗模板 -->
<script type="text/html" id="addEditDepartmentTpl">
	<div class="layui-form" style="padding:20px;">
		<div class="layui-form-item">
			<label class="layui-form-label">名称</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.name }}" name="name" placeholder="请输入" class="layui-input" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">是否可用</label>
			<div class="layui-input-block">
				<input type="hidden" name="flag" value="0">
				<input type="checkbox" lay-skin="switch" name="flag" value="1" lay-text="可用|不可用" {{ d.flag==1?'checked':'' }}  >
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.remark }}" name="remark" class="layui-input" lay-verify="required">
			</div>
		</div>
		<input type="hidden" name="id" value="{{ d.id }}">
		<p align="center"><button type="button" lay-submit lay-filter="addData" class="layui-btn layui-btn-sm">确定</button></p>
	</div>
</script>

<script>
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
		, tablePlug = layui.tablePlug; 							
		form.render();									
		
		var lookoverId;
		table.render({
			elem : '#departmentTable',
			url : '${ctx}/basedata/list?type=orgName',
			page : false,
			toolbar : "#departmentToolbar",
			parseData : function(ret) { return { code : ret.code, msg : ret.message, data : ret.data, } },
			cols:[[
			        {type: 'checkbox',align : 'center',},
					{field : "name",title : "部门名称",align : 'center'},
					{field : "remark",title : "备注",align : 'center'},
					{field : "flag",title : "是否可用",align : 'center',templet:function(d){ return d.flag==1?"是":"否";}},
			      ]],
		});
		
		table.on('toolbar(departmentTable)',function(obj){ 
			switch(obj.event){
			case 'add': addEidt('add','departmentTable'); 	break;
			case 'edit': addEidt('edit','departmentTable'); break; 
			case 'delete': deletes('departmentTable');	break;
			}
		});
		table.on('rowDouble(departmentTable)',function(obj){
			lookoverId =obj.data.id;
			more(lookoverId);
		})
		function more(id){					//查看子数据
			layer.open({
				title:'部门',
				type:1,
				offset:'50px',
				area:['90%','90%'],
				content:'<div class="layui-table" lay-filter="positionTable" id="positionTable"></div>' ,
				success:function(){
					table.render({
						elem : '#positionTable',
						url : '${ctx}/basedata/children?id='+id,
						toolbar : "#positionToolbar",
						parseData : function(ret) { return { code : ret.code, msg : ret.message, data : ret.data, } },
						cols:[[
						        {type: 'checkbox',align : 'center',},
								{field : "name",title : "部门名称",align : 'center'},
								{field : "remark",title : "备注",align : 'center'},
								{field : "flag",title : "是否可用",align : 'center',templet:function(d){ return d.flag==1?"是":"否";}},
						      ]],
					}); 
					
				}
			})
			table.on('toolbar(positionTable)',function(obj){ 
				switch(obj.event){
				case 'delete':deletes('positionTable'); 			break;
				case 'add': addEidt('add','positionTable');   break;
				case 'edit': addEidt('edit','positionTable'); break; 
				}
			});
		}
		function addEidt(type,thisTable){ 
			var choosedData=layui.table.checkStatus(thisTable).data;
			var data={ id:'', name:'', remark:'', flag:0, };
			var title="新增";
			var html="";
			var tpl=addEditDepartmentTpl.innerHTML;
			if(type=='edit'){
				var msg = '';
				if(choosedData.length>1)
					msg = "不能同时编辑多条数据";
				else if(choosedData.length<1)
					msg = "至少选中一条数据进行编辑";
				if(msg!=''){
					layer.msg(msg,{icon:2,offset:'300px',});
					return;					
				}
				title = "修改";
				data=choosedData[0];
			}
			laytpl(tpl).render(data,function(h){ html=h; })
			var addEditWin = layer.open({
				title:title,
				area:['30%','30%'],
				type:1,
				content:html,
				offset:'200px',
			})
			form.on('submit(addData)',function(obj){
				if(thisTable=='departmentTable'){
					obj.field.parentId = 0;
					obj.field.type = 'orgName';
				}else{
					obj.field.parentId = lookoverId;
					obj.field.type = 'position';
				}
				addAjax(obj.field,addEditWin);
				table.reload(thisTable);
			}); 
			form.render();
		}
		function deletes(thisTable){
			var choosedData=layui.table.checkStatus(thisTable).data;
			if(choosedData.length<1){
				layer.msg("请至少选中一条数据删除",{icon:2,offset:'200px',});
				return;
			}
			layer.confirm("是否确认删除"+choosedData.length+"条数据",{offset:'200px',},function(){
				var load=layer.load(1);
				var i=0;
				for(i=0;i<choosedData.length;i++){ 
					$.ajax({
						url:"${ctx}/basedata/delete",
						async:false,
						data:{id:choosedData[i].id},
					})
				}
				layer.close(load);
				if(i<choosedData.length){
					layer.msg("删除第"+i+"条数据时发生异常",{icon:2,offset:'200px',});
				}else{
					layer.msg("成功删除"+choosedData.length+"条数据",{icon:1,offset:'200px',});
					table.reload(thisTable);
				}
			})
		} 
		function addAjax(data,win){
			var load=layer.load(1);
			$.ajax({
				url:"${ctx}/basedata/add",
				data: data,
				async: false,
				type:"post",
				success:function(result){
					var icon = 2;
					if(0==result.code){
						icon = 1;
						layer.close(win);
					}
					layer.msg(result.message,{icon:icon,offset:'200px',}); 
				}
			})
			layer.close(load);
		}
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	}//end function
)//end defind

</script>

</html>