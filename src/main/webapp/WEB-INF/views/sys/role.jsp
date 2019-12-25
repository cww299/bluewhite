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
			padding:10px 0;
			
		}
		#treeDiv{
			width:40%;
			margin-left:3%;
			padding:10px 1%;
			border:1px solid #e2e2e2;
			overflow-y: auto;
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
<div class="layui-form" id="editRoleDiv"> 
  <div class="layui-form-item">
    <label class="layui-form-label">角色名</label>
    <div class="layui-input-block">
      <input type="text" placeholder="请输入" autocomplete="off" 
		lay-verify="required" value="{{ d.name || "" }}" name="name" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">英文名称</label>
    <div class="layui-input-block">
      <input type="text" name="role" placeholder="请输入" autocomplete="off" lay-verify="required" 
		value="{{ d.role || "" }}" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">角色类型</label>
    <div class="layui-input-block">
      <select name="editRoleType">
      	<option value="管理员" {{  d.roleType=='管理员'?'checked':'' }}>管理员</option>
		<option value="超级管理员"   {{  d.roleType=='超级管理员'?'checked':'' }}>超级管理员</option>
      </select>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">是否可用</label>
    <div class="layui-input-block">
      <input type="checkbox" lay-skin="switch" name="isShow" value="true" lay-text="可用|不可用"  {{ d.isShow==true?'checked':'' }} >
    </div>
  </div>
  <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">请填写描述</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容" class="layui-textarea" name="description">{{ d.description || "" }}</textarea>
    </div>
  </div>
  <div class="layui-form-item" style="display:none;">
  	<input type="hidden" id="editRoleId" value="{{ d.id || "" }}" class="layui-input">
	<span lay-submit lay-filter="saveRoleBtn" id="saveRoleBtn">1</span>
  </div>
</div>
<div id="treeDiv"></div>
</script>
<!-- 角色表格按钮 -->
<script type="text/html" id="aboutPermission">
	<button type="button" class="layui-btn layui-btn-sm" lay-event="editRole">编辑</button>
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
				{title : "操作",templet : "#aboutPermission", },
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
	            area:['60%','60%'],
	            content: html,
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
	            	form.on('submit(saveRoleBtn)',function(obj){
	            		var isTrue = true;
	            		if(data.role!=obj.field.role)
	            			isTrue = myutil.getDataSync({ url:myutil.config.ctx + '/roles/exists?name='+obj.field.role, });
	            		if(!isTrue)
	            			return myutil.esmg('该角色已存在！请勿重复添加');
	            		var checkedData = menuTree.getTreeData('treeDiv');		//获取所有选中数据
	            		deletePermissionIds(rolePermission,checkedData);
	            		
	            		myutil.saveAjax({
	            			url:"/roles/update",
	            			data: obj.field,
	            			success:function(){
	            				layer.close(layerIndex);
	            				table.reload('LAY-role-table');
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
			return deletes;
		}
		function getPermissionMenuId(arr){
			var checked = [];
			layui.each(arr,function(index,item){
				checked.push(item.menuId);
			})
			return checked;
		}
		
		function lookOverPermission(data){  		//查看角色权限方法
			var roleId=data.id;						//角色id
			var aboutPermission=layer.open({     	//打开查看权限内容的弹窗
				   title: '查看角色权限：'+data.name
				   ,type:1
				   ,area: ['80%', '90%']
				   ,content:'<div class="table_th_search" lay-filter="lookOverPermissions" id="lookOverPermissions"></div>'  //permissionTable
				   ,end:function(){
					   $('#lookOverPermissions').css("display","none");
				   }
			});
			
			table.render({     //查看权限的表格
				elem:'#lookOverPermissions',
				url:"${ctx}/getRole?id="+data.id,
				toolbar:'<div class="layui-btn-container layui-inline"><span class="layui-btn layui-btn-sm" lay-event="add" data-roleid="'+data.id+'">添加权限</span></div>',
				cols:[[
					       {field:'id',title:'id',align:'center'},
					       {field:'createdAt',title:'创建时间',align:'center'},
					       {field:'menuName',title:'菜单名',align:'center'},
					       {field:'permissionNames',title:'权限等级',templet:'#permissionLevelDiv',align:'center'},
					       {field:'updatedAt',title:'更新时间',align:'center'},
					       {title:'操作',align:'center',templet:'<div class="layui-btn-container layui-inline">'+
					    	   									'<span class="layui-btn layui-btn-sm" lay-event="edit">编辑</span>&nbsp;&nbsp;'+
					    	   									'<span class="layui-btn layui-btn-sm" lay-event="remove">删除</span></div>'}
				       ]],
				id:'lookOverPermissions'
			})	
			table.on('tool(lookOverPermissions)',function(obj){    	//监听表格中的按钮tool 
				switch(obj.event){
					case 'edit':  editPermission(obj);   break;     		//编辑权限
					case 'remove': removePermission(obj); break;			//删除权限
				}
			})
		 	table.on('toolbar(lookOverPermissions)', function (obj) {  //监听表格中的工具栏按钮toolbar
					switch(obj.event){
					case 'add': 
								var roleId=$(this).data('roleid');
								addPermission(roleId);						//新增权限
								break;
				}
			})
			function editPermission(obj){   								//编辑权限,obj当前所在行对象，可根据obj.data获取所在行信息
				var array=(obj.data.permissionNames).split(",");			//将权限字符串分割为数组
				var html='<p>权限id：'+obj.data.id+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;菜单名：'+obj.data.menuName+'</p><select name="permissionLevelSelect "'+
						 'xm-select="selectId'+obj.data.id+'" xm-select-show-count="5">';  //xm-select-show-count="5", 超出后隐藏
				for(var i=0;i<permissionLevel.length;i++){   
					var selected='';
					for(var j=0;j<array.length;j++){
						if(array[j]==permissionLevel[i].name)
							selected='selected';
					}
					html+='<option '+selected+' value="'+permissionLevel[i].id+'" >'+permissionLevel[i].name+'</option>';
				}
				html+="</select>"; 
			}
			function addPermission(roleId){    			//增加权限
				var html='';       						//打开加权限窗口的内容
				html += '<div style="float:left;" class="treeMenuDiv" id="menuDiv"></div>'+        //左侧存放联级菜单的div
						'<div style="float:right;" class="treeMenuDiv" id="choosedDiv"></div>'+    //右侧存放选中菜单的div
						'<div class="treeMenuControl">'+
							'<button type="button" class="layui-btn layui-btn-sm layui-btn-primary" style="margin-bottom:10px;" id="btnNext">'+
								'<i class="layui-icon layui-icon-next"></i></button>'+
							'<button type="button" class="layui-btn layui-btn-sm layui-btn-primary" style="margin-left:0px;" id="btnPrev">'+
								'<i class="layui-icon layui-icon-prev"></i></button>'+
						'</div>'+
						'<div style="float:right;width:100%;text-align:center;"><button type="button" lay-submit lay-filter="addPermissionSure"'+
						' value="'+roleId+'" class="layui-btn layui-btn-sm" >确定</button></div>'; //确定按钮
				var addPer=layer.open({    							//打开添加权限的窗口
					 title: '编辑权限'
					   ,type:1
					   ,area: ['60%', '80%']
					   ,content:html
				}) 
				var allRolePermission=layui.table.cache.lookOverPermissions;		//该角色的相关权限详细信息
				var checkedTree = []; 												//选中树形结构菜单
				for(var i=0;i<allRolePermission.length;i++)
					checkedTree.push(allRolePermission[i].menuId);
				menuTree.render({				//渲染全部菜单
			    	  elem:'#menuDiv',
			    	  data : allMenu,
			    	  checked : checkedTree,
			    });
				var data = menuTree.getTreeData('menuDiv');
				menuTree.render({				//渲染已有菜单
						elem : '#choosedDiv',
						data : data,
						hide : false,
						closeCheckLink : true,  //关闭复选框联动，即选中子菜单，父菜单不会选中
				})  
				
				
				$('.treeMenuControl').find('#btnNext').on('click',function(){
					menuTree.reload('choosedDiv',{
						checked : menuTree.getVal('choosedDiv'),  //还原之前勾选的数据
						data : menuTree.getTreeData('menuDiv'),
					});
				})
				$('.treeMenuControl').find('#btnPrev').on('click',function(){
					menuTree.reload('choosedDiv',{
						data : menuTree.getUncheckedTreeData('choosedDiv')
					});
				})
				form.on('submit(addPermissionSure)',function(obj){   	//加权限中，确定按钮的监听
					var roleId=obj.elem.value;	
					var newCheck = [];
					getAllId(menuTree.getAllData('choosedDiv'));
					function getAllId(data){							//递归获取角色所有选中的新权限
						layui.each(data,function(index,item){
							newCheck.push(item.id);	
							if(item.children.length>0)
								getAllId(item.children);
						});
					}
					newCheck.push("15");		  //添加首页的权限，首页id为15 线上的数据库也为15
					layer.confirm('是否保存更改？',function(){
						var load = layer.load(1);
						layer.msg('保存权限更改中......');
						layui.each(checkedTree,function(index,item){		//首先删除之前存在，本次却没有选择的权限
							if(!(newCheck.indexOf(item+"")>-1)){
								for(var i=0;i<allRolePermission.length;i++){
									if(allRolePermission[i].menuId == item){
										$.ajax({
											url:'${ctx}/roles/deleteRole',
											async:false,
											type:"post",
											data:{ id : allRolePermission[i].id },
											success:function(result){
												if(0!=result.code)
													layer.msg(result.code+''+result.message,{icon:2});
											}
										}) 
										break;
									}
								}
							}
						})
						var permissions=[];
						layui.each(newCheck,function(index,item){				//然后，添加之前没有，本次选择的权限
							if(checkedTree.indexOf(parseInt(item)) == -1){
								permissions.push({ "menuId" : item, "permissionIds" : '1,' });
							}
						})
						if(permissions.length>0){
							var data={ roleId:roleId, permissions:JSON.stringify(permissions) };
							$.ajax({
								url:"${ctx}/roles/changeRole",
								type:"POST",
								async:false,
								data:data,
								success:function(result){
									if(result.code!=0)
										layer.msg(result.code+''+result.message,{icon:2});
								},
							}) 
						}
						layer.close(load);
						layer.close(addPer);
						table.reload('lookOverPermissions');
						layer.msg('保存成功......',{icon:1});
					})
				})
				 
			}//加权限结束（窗口的弹出、逻辑的判断。。）
			
		}//查看角色权限结束
		
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