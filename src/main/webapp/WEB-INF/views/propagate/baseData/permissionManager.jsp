<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/formSelect/formSelects-v4.css" />
<title>角色管理</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td><select name="" lay-search><option value=""></option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><select name="" lay-search><option value=""></option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button>&nbsp;<span class="layui-badge" >提示：双击分配角色</span></td>
			</tr>
		</table>
		<table class="layui-form" id="userTable" lay-filter="userTable"></table>
	</div>
</div>

</body>

<!-- 分配角色隐藏框  -->
<div class='layui-form' style='display:none;padding:20px;' id='addRoleDiv'>
	<div class="layui-form-item">
		<input type='hidden' name='userId' id='addRoleUserId'>
		<label class="layui-form-label">用户名</label>
		<div class="layui-input-block">
			<input type='text' class='layui-input' readonly id='addRoleUserName'>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">角色</label>
		<div class="layui-input-block">
			<select id="roleIdSelect" lay-search  xm-select="roleIdSelect" xm-select-show-count="5" name='roleId'>
					<option value="">获取数据中...</option></select>
		</div>
	</div>
	<p style="text-align:center;">
		<button type='button' class='layui-btn layui-btn-sm' lay-submit lay-filter='sureAdd'>确定</button></p>
</div>

<!-- 表格工具栏模板 -->
<script type="text/html" id="userTableToolbar">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改</span>
	<span class="layui-badge" >提示：双击分配角色</span>
</div>
</script>

</body>

<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	formSelects : 'formSelect/formSelects-v4'
}).define(
	['tablePlug','formSelects'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, formSelects = layui.formSelects
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug;
		
		var allRole=[];
		getAllRole();
		
		table.render({
			elem:'#userTable',
			url:'${ctx}/system/user/pages?orgNameIds=35&quit=0',//(放到线上的时候需要把部门id去除)			//在职的广宣部人员
			loading:true,
			page:true,
			size:'lg',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){
				return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'用户id',   field:'id',	},
			       {align:'center', title:'用户姓名',   field:'userName',   },
			       {align:'center', title:'角色', 	field:'', 	},
			       ]]
		})
		
		table.on('rowDouble()',function(obj){
			renderSelect(0);
			layer.open({
				title:'分配用户角色',
				area:['40%','60%'],
				shadeClose: true,
				type:1,
				content:$('#addRoleDiv'),
			})
			$('#addRoleUserId').val( obj.data.id );
			$('#addRoleUserName').val( obj.data.userName );
			$('#roleIdSelect').html(getSelectRoleHtml(''));
			formSelects.render();
		})
		
		form.on('submit(sureAdd)',function(obj){
			layer.confirm('是否确认？',function(){
				//formSelects.value('roleIdSelect', 'valStr'),
				console.log(obj.field);
				return;
				
				
				var data=obj.field;
				var load=layer.load(1);
				$.ajax({
					url:'${ctx}/roles/saveUserRole',
					type:"post",
					data:data,
					success:function(result){
						if(result.code==0){
							layer.closeAll();
							layer.msg(result.message,{icon:1});
							table.reload('userRoleTable');
						}
						else
							layer.msg(result.code+' '+result.message,{icon:2});
						layer.close(load);
					}
				}) 
			})
			
		})
		
		function getSelectRoleHtml(idStr){
			var html='<option value="">请选择</option>';
			var ids=idStr.split(',');
			var isSelect='';
			layui.each(allRole,function(index,item){ 
				isSelect='';
				for(var i=0;i<ids.length;i++){
					if(ids[i]==item.id)
						isSelect='selected';
					html+='<option value="'+item.id+'" '+isSelect+' >'+item.name+'</option>';
				}
			})
			return html;
		}
		
		function renderSelect(role){
			var html='<select >请选择角色</select>'
			for(var i=0;i<allRole.length;i++){
				html+='<option value="'+allRole[i].id+'" '+(role==allRole[i].id?'selected':'')+'>'+ allRole[i].name+'</option>'
			}
			$('#roleSelect').html(html);
			form.render();
		}
		
		function getAllRole(){
			$.ajax({
				url:'${ctx}/roles/page' ,											//获取全部角色
				success:function(result){
					if(result.code==0){
						var row=result.data;
						for(var i=0;i<row.length;i++){
							var role={id:row[i].id,name:row[i].name};
							allRole.push(role);
						}
					}
				}
			})
		}
		table.on('toolbar()',function(obj){
			switch(obj.event){
			/* case 'add':		addEdit('add');		break;
			case 'update':	addEdit('edit'); 	break;
			case 'delete':	deletes();			break; */
			}
		})
		
		/* function addEdit(type){
			var data={},
			choosed=layui.table.checkStatus('').data,
			tpl=addEditTpl.innerHTML,
			title='',
			html='';
			if(type=='edit'){
				if(choosed.length>1){
					layer.msg("不能同时编辑多条信息",{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg("至少选择一条信息编辑",{icon:2});
					return;
				}
				data=choosed[0];
				title="";
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			var addEditWin=layer.open({
				type:1,
				title:title,
				area:['40%','65%'],
				content:html
			})
			form.render();
			form.on('submit(sure)',function(obj){
				var load=layer.load(1);
				$.ajax({
					url:'',
					type:'',
					data:obj.field,
					success:function(result){
						if(0==result.code){
							table.reload('');
							layer.close(addEditWin);
							layer.msg(result.message,{icon:1});
						}else
							layer.msg(result.message,{icon:2});
						layer.close(load);
					},
					error:function(){
						layer.msg("服务器异常");
						layer.close(load);
					}
				})
			})
		} */
	/* 	function deletes(){
			var choosed=layui.table.checkStatus('').data;
			if(choosed.length<1){
				layer.msg('请选择商品',{icon:2});
				return;
			}
			layer.confirm("是否确认删除？",function(){
				var ids='';
				for(var i=0;i<choosed.length;i++){
					ids+=(choosed[i].id+",");
				}
				var load=layer.load(1);
				$.ajax({
					url:"",
					data:{ids:ids},
					success:function(result){
						if(0==result.code){
							layer.msg(result.message,{icon:1});
							table.reload('');
						}
						else{
							layer.msg(result.message,{icon:2});
						}
						layer.close(load);
					},
					error:function(result){
						layer.msg('ajax异常',{icon:2});
						layer.close(load);
					}
				})
			})
		} */
		
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	}//end define function
)//endedefine
</script>

</html>