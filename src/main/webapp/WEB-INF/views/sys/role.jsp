<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<title>角色管理</title>
	<style type="text/css">
		#editRoleDiv,#treeDiv{
			height:90%;
			float:left;
			width:50%;
			padding:10px;
			
		}
		#treeDiv,#userDiv{
			margin-top: 5px;
			width:30%;
			margin-left:3%;
			padding:10px 1%;
			border:1px solid #e2e2e2;
			overflow-y: auto;
		}
		#userDiv{
			width:9%;
			margin-left:1%;
			height:90%;
			float:left;
		}
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<div id="LAY-role-table" lay-filter="LAY-role-table"></div>
	</div>
</div>
<!-- 编辑、添加角色模板  -->
<script type="text/html" id="editRoleTempl">
<div class="layui-form layui-form-pane" id="editRoleDiv"> 
  <div class="layui-form-item" pane>
    <label class="layui-form-label">角色名</label>
    <div class="layui-input-block">
      <input type="text" placeholder="请输入" autocomplete="off" 
		lay-verify="required" value="{{ d.name || "" }}" name="name" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">英文名称</label>
    <div class="layui-input-block">
      <input type="text" name="role" placeholder="请输入" autocomplete="off" lay-verify="required" 
		value="{{ d.role || "" }}" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">角色类型</label>
    <div class="layui-input-block">
      <select name="editRoleType">
      	<option value="管理员" {{  d.roleType=='管理员'?'checked':'' }}>管理员</option>
		<option value="超级管理员"   {{  d.roleType=='超级管理员'?'checked':'' }}>超级管理员</option>
      </select>
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">是否可用</label>
    <div class="layui-input-block">
      <input type="checkbox" lay-skin="switch" name="isShow" value="true" lay-text="可用|不可用"  {{ d.isShow==true?'checked':'' }} >
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">请填写描述 </label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容" class="layui-textarea" name="description">{{ d.description || "" }}</textarea>
    </div>
  </div>
  <div class="layui-form-item" style="display:none;">
  	<input type="hidden" name="id" value="{{ d.id || "" }}" class="layui-input">
	<span lay-submit lay-filter="saveRoleBtn" id="saveRoleBtn">1</span>
  </div>
</div>
<div id="treeDiv"></div>
<div id="userDiv">
</div>
</script>
<!-- 角色表格按钮 -->
<script type="text/html" id="aboutPermission">
	<button type="button" class="layui-btn layui-btn-sm">编辑</button>
</script>	
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	menuTree : 'layui/myModules/menuTree',
	mytable : 'layui/myModules/mytable',
}).define(
	[ 'jquery','menuTree','mytable','form'],
	function() {
		var $ = layui.jquery
		, layer = layui.layer
		, form = layui.form	
		, table = layui.table
		, menuTree = layui.menuTree 
		, myutil = layui.myutil
		, mytable = layui.mytable
		, laytpl = layui.laytpl;			//模板引擎
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		var allMenu=[]; 
		mytable.render({
			elem: '#LAY-role-table',
			url: myutil.config.ctx+'/roles/page' ,
			toolbar: [
				'<span class="layui-btn layui-btn-sm" lay-event="addRole">添加角色</span>',
			].join(''),
			limit:15,
			limits:[10,15,20,30,50,100],
			curd:{
				btn:[4],
				otherBtn:function(obj){
					if(obj.event=='addRole'){
						addEditRole({});
					}
				}
			},
			autoUpdate:{
				deleUrl:'/roles/delete',
			},
			cols : [[   {type: 'checkbox',fixed: 'left'},
				{field : "id",title : "ID",width : 80,sort : true,}, 
				{field : "name",title : "角色名",}, 
				{field : "role",title : "英文名称",}, 
				{field : "roleType",title : "角色类型",templet:'<p>管理员</p>'}, 
				{field : "isShow",title : "是否可用", templet : '<p>{{ d.isShow == true ? "是" : "否" }} </p>'},
				{field : "description",title : "具体描述", },
				{title : "操作",templet : "#aboutPermission", event:"editRole"},
			]] 
		});

		table.on('tool(LAY-role-table)',function(obj){
			switch(obj.event){
				case 'editRole': addEditRole(obj.data); break;
			}
		})

		function addEditRole(data){
			var html='',title = '添加角色';
			laytpl($('#editRoleTempl').html()).render(data,function(h){
				html=h;
			})
			if(data.id){
				title = '修改角色';
			}
        	layer.open({
	       		type: 1,
	            title: title,
	            area:['1000px','480px'],
	            offset:'100px',
	            content: html,
	            shadeClose:true,
	            btn:["确定",'取消'],
	            btnAlign:'c',
	            success:function(layero,layerIndex){
	            	var rolePermission = [];
	            	if(data.id){
	            		rolePermission = myutil.getDataSync({ url: myutil.config.ctx+'/getRole?id='+data.id })
	            	}
	            	menuTree.render({
				    	  elem:'#treeDiv',
				    	  data : allMenu,
				    	  checked : getPermissionMenuId(rolePermission),
				    });
	            	$('#userDiv').append((function(){
	            		var html = '';
	            		if(rolePermission[0] && rolePermission[0].role && rolePermission[0].role.users)
		            		layui.each(rolePermission[0].role.users,function(index,item){
		            			html += '<span style="margin-top:5px;" class="layui-badge layui-bg-green">'+
		            				item.userName+'</span><br>';
		            		})
	            		return html;
	            	})())
	            	form.on('submit(saveRoleBtn)',function(obj){
	            		var isTrue = true;
	            		if(data.role!=obj.field.role)
	            			isTrue = myutil.getDataSync({ url:myutil.config.ctx + '/roles/exists?name='+obj.field.role, });
	            		if(!isTrue)
	            			return myutil.esmg('该角色已存在！请勿重复添加');
	            		var checkedData = menuTree.getVal('treeDiv');		//获取所有选中数据
	            		var url = "/roles/add";
	            		if(obj.field.id)
	            			url = "/roles/update";
	            		myutil.saveAjax({
	            			url: url,
	            			data: obj.field,
	            			success:function(result){
	            				layer.close(layerIndex);
	            				table.reload('LAY-role-table');
	            				var roleId = data.id;
	            				if(result.data && result.data.id)
	            					roleId = result.data.id;
	            				deletePermissionIds(rolePermission,checkedData);
	    	            		addRolePermission(getPermissionMenuId(rolePermission),checkedData,roleId);
	            			}
	            		})
	            	})
	            },
	            yes:function(){
	            	$('#saveRoleBtn').click();
				},
			})
			form.render();
   		}
		function deletePermissionIds(first,now){
			var deletes = [];
			layui.each(first,function(i1,firstItem){	//找出第一个数组中存在，第二个数组中不存在的菜单id集合
				if(now.indexOf(firstItem.menuId)<0)
					deletes.push(firstItem.id);
			})
			if(deletes.length>0){
				myutil.getDataSync({
					url: myutil.config.ctx + '/roles/deleteRoleMenuPermission?ids='+deletes.join(','),
				})
			}
		}
		function addRolePermission(first,now,roleId){
			var add = [];
			layui.each(now,function(i1,nowItem){	//找出第二个数组中存在，第一个数组中不存在的菜单id集合
				if(first.indexOf(nowItem)<0)
					add.push({
						menuId: nowItem,
						permissionIds: 1,
					});
			})
			if(add.length>0){
				myutil.getDataSync({
					url: myutil.config.ctx + '/roles/changeRole',
					type:'post',
					data:{
						roleId: roleId,
						permissions: JSON.stringify(add),
					}
				})
			}
		}
		function getPermissionMenuId(arr){
			var checked = [];
			layui.each(arr,function(index,item){
				checked.push(item.menuId);
			})
			return checked;
		}
		;(function getMenu(){
			myutil.getData({
				url : myutil.config.ctx + "/getTreeMenuPage",
				success : function(d) {
					allMenu = d;
				}
			})
		})();
	}// defind end
);
</script>
</body>
</html>