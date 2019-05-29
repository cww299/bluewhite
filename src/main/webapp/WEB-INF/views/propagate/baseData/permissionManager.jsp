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
</head>
<body>
	<div class="layui-card">
		<div class="layui-card-body">
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
			<select id="roleIdSelect" lay-search  xm-select="roleIdSelect" xm-select-show-count="5" name='ids'>
					<option value="">获取数据中...</option></select>
		</div>
	</div>
	<p style="text-align:center;margin-top:20px;">
		<button type='button' class='layui-btn layui-btn-sm' lay-submit lay-filter='sureAdd'>确定</button></p>
</div>


<!-- 用户角色模板 -->
<script type="text/html" id="roleTpl">
	{{# var role = d.roles;
		for(var i=0;i<role.length;i++){  }}
			<span style="margin-top:10px;" class="layui-badge layui-bg-green">{{ role[i].name }}</span>
	{{# } }}
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
			url:'${ctx}/system/user/pages',
			loading:true,
			page:true,
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){
				return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', title:'用户id',   field:'id',	},
			       {align:'center', title:'用户姓名',   field:'userName',   },
			       {align:'center', title:'角色      <span class="layui-badge" >提示：双击分配角色</span>', templet:'#roleTpl'},
			       ]]
		})
		table.on('rowDouble()',function(obj){
			layer.open({
				title:'分配用户角色',
				area:['40%','60%'],
				type:1,
				content:$('#addRoleDiv'),
			})
			$('#addRoleUserId').val( obj.data.id );
			$('#addRoleUserName').val( obj.data.userName );
			var ids='';
			for(var i=0;i<obj.data.roles.length;i++)
				ids+=(obj.data.roles[i].id+',');
			$('#roleIdSelect').html(getSelectRoleHtml(ids));
			formSelects.render();
		})
		
		form.on('submit(sureAdd)',function(obj){
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
						table.reload('userTable');
					}
					else
						layer.msg(result.code+' '+result.message,{icon:2});
					layer.close(load);
				}
			}) 
		})
		
		function getSelectRoleHtml(idStr){
			var html='<option value="">请选择</option>';
			var ids=idStr.split(',');
			var isSelect='';
			layui.each(allRole,function(index,item){ 
				isSelect='';
				for(var i=0;i<ids.length;i++){
					if(ids[i]==item.id){
						isSelect='selected';
						break;
					}
				}
				html+='<option value="'+item.id+'" '+isSelect+' >'+item.name+'</option>';
			})
			return html;
		}
		
		function getAllRole(){
			$.ajax({
				url:'${ctx}/roles/page?size=99' ,											//获取全部角色
				success:function(result){
					if(result.code==0){
						var row=result.data.rows;
						for(var i=0;i<row.length;i++){
							var role={id:row[i].id,name:row[i].name};
							allRole.push(role);
						}
					}
				}
			})
		}
		
	}//end define function
)//endedefine
</script>

</html>