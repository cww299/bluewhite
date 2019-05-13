<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/formSelect/formSelects-v4.css" />
	
<title>权限管理</title>
</head>
<body>

<div class="layui-card" >
	<div class="layui-card-body" style="height:800px;">
		<table class="layui-table" lay-filter="userRoleTable" id="userRoleTable"></table>
	</div> 
</div>

<!-- 权限类型管理隐藏框 -->
<div style="padding:5px;display:none;" id="permissionTypeDiv">
	<table class="layui-table" id="permissionTypeTable" lay-filter="permissionTypeTable"></table>
</div>
<!-- 权限类型管理工具栏 -->
<script type="text/html" id="permissionTypeTableToolbar">
	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除权限类型</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增权限类型</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑</span>
	</div>
</script>
<!-- 权限类型编辑、添加模板 -->
<script type="text/html" id="permissionTypeTpl">
	<div class="layui-form" style="padding:20px">
		<input type="hidden" name="id" value="{{ d.id }}">
		<div class="layui-form-item">
			<label class="layui-form-label">名称</label>
			<div class="layui-input-block">
				<input type="text" name="name" class="layui-input" value="{{ d.name }}" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">是否可用</label>
			<div class="layui-input-block">
				<input type="hidden" name="show" value="0">
				<input type="checkbox" name="show" lay-skin="switch" value="1" lay-text="是|否"{{ d.show==true?'checked':'' }} name="isShow">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">权限类型</label>
			<div class="layui-input-block">
				<input type="text" name="permission" class="layui-input" value="{{ d.permission }}" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">说明</label>
			<div class="layui-input-block">
				<input type="text" name="description" class="layui-input" value="{{ d.description }}">
			</div>
		</div>
		<p align="center"><button type="button" lay-submit lay-filter="sure" class="layui-btn layui-btn-sm">确定</button></p>
	</div>	
</script>


<!-- 添加、编辑角色弹窗 -->
<script type="text/html" id="addEditUserRoleTpl">
	<div class="layui-form" style="padding:20px">
		<div class="layui-form-item">
			<label class="layui-form-label">用户名</label>
			<div class="layui-input-block">
				{{# var isDisabled='' ;
					if(d.userId!='') isDisabled='disabled';
				}}
				<select id="userIdSelect" lay-search {{isDisabled}} >
					<option value="">请选择</option>
				{{# var isSelect='';
					layui.each(allUser,function(index,item){
						isSelect='';
						if(d.userId==item.id)
							isSelect='selected'; }}
					<option value="{{ item.id }}" {{ isSelect }} >{{ item.name }}</option>
				{{# }); }}
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">角色</label>
			<div class="layui-input-block">
				<select id="roleIdSelect" lay-search  xm-select="roleIdSelect" xm-select-show-count="3">
					<option value="">请选择</option>
				{{#
					var ids=d.ids.split(',');
					var isSelect='';
					layui.each(allRole,function(index,item){ 
						isSelect='';
						for(var i=0;i<ids.length;i++){
							if(ids[i]==item.id)
								isSelect='selected';
						}  }}
					<option value="{{ item.id }}" {{ isSelect }} >{{ item.name }}</option>
				{{# }); }}
				</select>
			</div>
		</div>
	</div>
</script>
<!-- 用户所拥有的角色 -->
<script id="roles" type="text/html">
	{{# layui.each(d.roles,function(index,item){  }}
		<span class="layui-badge layui-bg-green">{{item.name}}</span>&nbsp;&nbsp;
	{{# }); }}
</script>


<!-- 表格工具栏 -->
<script type="text/html" id="userRoleToolBar">
	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除用户角色</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增用户角色</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑</span>
		<span class="layui-btn layui-btn-sm" lay-event="permissionTypeManager">权限类型管理</span>
	</div>
</script>
<script>
var allRole=[];				//查新所有角色、用户，用于填充下拉框选择
var allUser=[];
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	formSelects : 'formSelect/formSelects-v4'
}).define(
	[ 'tablePlug','formSelects'],
	function() {
		var $ = layui.jquery
		, tablePlug = layui.tablePlug
		, table = layui.table
		, laytpl = layui.laytpl
		, formSelects = layui.formSelects
		, form = layui.form; 		
		ajaxGetData();
		var choosed=[];							//存放复选框选中的对象，用于删除、编辑
		table.render({
			elem : '#userRoleTable',
			size : 'lg',
			url : "${ctx}/allRoleUser",
			height : '700',
			request:{
				pageName: 'page' ,				//页码的参数名称，默认：page
				limitName: 'size' 				//每页数据量的参数名，默认：limit
			},
			parseData : function(ret) {    		//对回传的数据解析，解析成符合layui.table的风格
				return {
					code : ret.code,
					msg : ret.msg,
					count : ret.data.total, 
					data : ret.data.rows
				}
			},
			page : true,
			loading : true,  
			toolbar : "#userRoleToolBar",
			cols:[[
			       {type: 'checkbox',align : 'center',fixed: 'left'},
					{field : "id",title : "用户id",sort : true,align : 'center'},
					{field : "userName",title : "用户名",sort : true,align : 'center'},
					{field : "roles",title : "角色",align : 'center',templet:'#roles'},
			      ]],
			done:function(){
				choosed=[];						//每次翻页后，清空选中的对象
			}
		});
		table.on('checkbox(userRoleTable)', function(obj){
			if(obj.type=='all' ){ 
				if(obj.checked){
					var row=layui.table.cache.userRoleTable;
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
		table.on('toolbar(userRoleTable)',function(obj){
			var tableId = obj.config.id;  	
			var ids={ ids: tablePlug.tableCheck.getChecked(tableId) };
			switch(obj.event){
			case 'add':		addEdit('add');   break;
			case 'edit':	addEdit('edit');  break;
			case 'delete':	deleteUserRole(); break;
			case 'permissionTypeManager':	permissionTypeManager(); break;
			}
		})
		function permissionTypeManager(){
			layer.open({
				type:1,
				title:"权限类型管理",
				area:['80%','80%'],
				content:$('#permissionTypeDiv')
			})
			table.render({
				elem:'#permissionTypeTable',
				url:"${ctx}/permission",
				page:false,
				size:'lg',
				loading:true,
				toolbar:'#permissionTypeTableToolbar',
				cols:[[
				       {type:'checkbox',align:'center'},
				       {field:'id',title:'ID',align:'center'},
				       {field:'name',title:'名称',align:'center'},
				       {field:'show',title:'是否可用',align:'center'},
				       {field:'permission',title:'权限类型',align:'center'},
				       {field:'description',title:'说明',align:'center'},
				       ]]
			})
			table.on('toolbar(permissionTypeTable)',function(obj){
				switch(obj.event){
					case 'add':		addEditPermissionType('add');   break;
					case 'edit':	addEditPermissionType('edit');  break;
					case 'delete':	deletePermissionType(); break;
					case 'permissionTypeManager': layer.msg("无该功能"); break;
				}
			})
			function addEditPermissionType(type){
				var data={id:'',name:'',show:false,permission:'',description:''}
					,html=''
					,tpl=permissionTypeTpl.innerHTML
					,choosed=layui.table.checkStatus('permissionTypeTable').data
					,typeName='新增';
				if('edit'==type){
					if(choosed.length>1){
						layer.msg("无法同时编辑多条信息",{icon:2});
						return;
					}
					if(choosed.length<1){
						layer.msg("至少选择一条信息编辑",{icon:2});
						return;
					}
					typeName="修改";
					data=choosed[0];
				}
				laytpl(tpl).render(data,function(h){
					html=h;
				})
				var addEditWindow=layer.open({
					type:1,
					title:typeName+'信息',
					content:html,
					area:['30%','40%']
				})
				form.render();
				form.on('submit(sure)',function(obj){
					var load=layer.load(1);
					$.ajax({
						url:"${ctx}/savePermission",
						type:"post",
						data:obj.field,
						success:function(result){
							if(0==result.code){
								table.reload('permissionTypeTable');
								layer.close(addEditWindow);
								layer.msg(result.message,{icon:1});
							}
							else
								layer.msg(result.code+result.message,{icon:2});
							layer.close(load);
						}
					})
				})
			}
					
			function deletePermissionType(){
				var choosed=layui.table.checkStatus('permissionTypeTable').data;
				if(choosed.length<1){
					layer.msg("请至少选择一条数据删除",{icon:2});
					return;
				}
				var targetDel=choosed.length;
				var successDel=0;
				var load=layer.load(1);
				for(var i=0;i<targetDel;i++){
					$.ajax({
						url:"${ctx}/deletePermission",
						data:{id:choosed[i].id},
						async:false,
						success:function(result){
							if(0==result.code){
								successDel++;
							}
						}
					})
				}
				layer.close(load);
				table.reload('permissionTypeTable');
				if(successDel==targetDel){
					layer.msg('成功删除'+successDel+'条数据',{icon:1});
				}
				else
					layer.msg('删除发生异常，目标删除：'+targetDel+'条数据，实际删除：'+successDel,{icon:2});
			}
		}
		function deleteUserRole(){						//删除用户角色
			if(choosed.length==0){
				layer.msg("请选择至少一个对象删除",{icon:2});
				return;
			}
			var load=layer.load(1);
			for(var i=0;i<choosed.length;i++){
				var data={
						userId:choosed[i].id,
						ids:''
				};
				 $.ajax({
						url:'${ctx}/roles/saveUserRole',
						type:"post",
						data:data,
						success:function(result){
							if(result.code!=0)
								layer.msg(result.code+' '+result.message,{icon:2});
						}
				})  
			}
			layer.msg("成功删除"+choosed.length+'天记录',{icon:1});
			choosed=[];
			table.reload('userRoleTable');
			layer.close(load);
		}
		
		function addEdit(type){							//编辑或者添加
			var html='';								//渲染后的html，作为弹窗的内容
			var tpl=addEditUserRoleTpl.innerHTML;		//还未渲染的模板
			var data={ids:'',userId:''};				//渲染模板的数据
			var title="添加新用户角色";					//弹窗的标题
			if(type=='edit'){							
				if(choosed.length!=1){
					layer.msg("只能选择一个对象编辑",{icon:2});
					return;
				}
				title="修改用户角色";
				var arrIds=[];
				for(var i=0;i<choosed[0].roles.length;i++){
					arrIds.push(choosed[0].roles[i].id);
				}
				var ids=arrIds.join(',');
				data={
						userId:choosed[0].id,
						ids:ids
				};
			}
			laytpl(tpl).render(data,function(h){		
				html=h;
			})
			layer.open({
				title:title
				,type:1
				,btn:['确定','取消']
				,area:['30%','65%']
				,content:html
				,success:function(){
					form.render('select');
					formSelects.render();
				}
				,yes:function(){
					var data={
							userId:$('#userIdSelect').val(),
							ids:formSelects.value('roleIdSelect', 'valStr')
					}
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
				}
			})
		}
		
		
		
		// tr点击触发复选列点击
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
		
		function ajaxGetData(){
			$.ajax({
				url:'${ctx}/roles' ,											//获取全部角色
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
			$.ajax({
				url:'${ctx}/system/user/findUserList',	//获取全部用户
				success:function(result){
					if(result.code==0){
						var row=result.data;
						for(var i=0;i<row.length;i++){
							var user={id:row[i].id,name:row[i].userName};
							allUser.push(user);
						}
					}
				}
			}) 
		}
	}
);

</script>
</body>
</html>