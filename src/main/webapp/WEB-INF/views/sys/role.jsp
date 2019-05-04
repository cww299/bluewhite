<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/formSelect/formSelects-v4.css" />
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script> 
<title>角色管理</title>

</head>

<body>
	
<div class="layui-card">
	<div class="layui-card-body" style="height:800px"">
		<div id="LAY-role-table" class="table_th_search" lay-filter="LAY-role-table"></div>
	</div>
</div>


<!-- 编辑、添加角色模板  -->
<script type="text/html" id="editRoleTempl">
<div class="layui-form" id="editRoleDiv" style="padding:20px;"> 
  <div class="layui-form-item">
  <input type="hidden" id="editRoleId" placeholder="请输入" autocomplete="off" lay-verify="required" value="{{ d.id }}" class="layui-input">
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">角色名</label>
    <div class="layui-input-block">
      <input type="text" id="editRoleName" placeholder="请输入" autocomplete="off" lay-verify="required" value="{{ d.name }}" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">英文名称</label>
    <div class="layui-input-block">
      <input type="text" id="editRole" placeholder="请输入" autocomplete="off" lay-verify="required" value="{{ d.role }}" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">角色类型</label>
    <div class="layui-input-block">
      <select id="editRoleType" lay-filter="aihao">
      	<option value="管理员">管理员</option>
		<option value="超级管理员">超级管理员</option>
      </select>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">是否可用</label>
    <div class="layui-input-block">
      <input type="checkbox" lay-skin="switch" id="editIsShow" lay-text="可用|不可用" {{ d.isShow==true?'checked':'' }} >
    </div>
  </div>
  <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">请填写描述</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容" class="layui-textarea" id="editDescription" id="editDescription" value="{{ d.description }}"></textarea>
    </div>
  </div>
</div>
</script>
<!-- 权限等级样式模板 -->
<script type="text/html" id="permissionLevelDiv">
	{{# var arr=d.permissionNames.split(",");
			layui.each(arr, function(index, item){  }}
		<span class="layui-badge layui-bg-green">{{item}}</span>&nbsp;&nbsp;
	{{# }); }}
</script>
<!-- 角色表格工具栏  -->
<script type="text/html" id="toolbarDemo">
  	<div class="layui-btn-container layui-inline">
   		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteRole">批量删除</span>
    	<span class="layui-btn layui-btn-sm" lay-event="addRole">添加角色</span>
	</div>
</script>
<!-- 角色表格按钮 -->
<script type="text/html" id="aboutPermission">
	<button type="button" class="layui-btn layui-btn-sm" lay-event="lookOverPermission">查看权限</button>
	<button type="button" class="layui-btn layui-btn-sm" lay-event="editRole">编辑</button>
</script>	
<script>

var allMenu=[];    //所有的menus接口菜单,用于添加权限时的级联拼接
var choosePermission=[];  //链接菜单中被选中的三级菜单，id存放本身id，name存放菜单名，parent存放父id
var parentMenu=[];		//用于联级子菜单点击父菜单的显示功能,id存放的本身id，name存放菜单名，parent存放父id，choosed子菜单是否有被选中,choosedNum子菜单有多少个被选中
var permissionLevel=[];   //用于存放相关的权限等级

layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	formSelects : 'formSelect/formSelects-v4'
}).define(
	[ 'tablePlug','jquery','formSelects'],
	function() {
		var $ = layui.jquery
		, formSelects = layui.formSelects	//多选下拉框插件
		, layer = layui.layer 				//弹层
		, form = layui.form			 		//表单
		, table = layui.table 				//表格
		, tablePlug = layui.tablePlug 		//表格插件
		, laytpl = layui.laytpl;			//模板引擎
		
		getMenu();   			//获取菜单，添加权限级联时使用
		getPermissionLevel();   //获取权限等级
		formSelects.btns('select1', ['select', 'remove', 'reverse']);
		table.render({
			elem : '#LAY-role-table',
			size : 'lg',
			url : '${ctx}/roles/page' ,
			height:'700px',
			request:{
				pageName: 'page' ,		//页码的参数名称，默认：page
				limitName: 'size' 		//每页数据量的参数名，默认：limit
			},
			page : {},       			//开启分页
			loading : true,     		//开启加载动画
			toolbar : '#toolbarDemo',	//开启工具栏，此处显示默认图标
			colFilterRecord : true,		//开启智能重载

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
				{field : "id",title : "ID",width : 80,sort : true,align : 'center'}, 
				{field : "name",title : "角色名",align : 'center'}, 
				{field : "role",title : "英文名称",align : 'center'}, 
				{field : "roleType",title : "角色类型",align : 'center',type: 'normal',templet:'<p>管理员</p>'}, 
				{field : "isShow",title : "是否可用",align : 'center',templet : '<p>{{ d.isShow == true ? "是" : "否" }} </p>'},
				{field : "description",title : "具体描述",align : 'center'},
				{title : "操作",templet : "#aboutPermission",align : 'center'}
			] ] 
		});

		table.on('tool(LAY-role-table)',function(obj){   						//监听表格按钮,obj为监听该行的对象
			switch(obj.event){
				case 'lookOverPermission':lookOverPermission(obj.data);break;  	//查看角色权限
				case 'editRole':editRole(obj.data); break;						//编辑角色
			}
		})

	    table.on('toolbar(LAY-role-table)', function (obj) { 	//监听头工具栏事件
			var tableId = obj.config.id;  						//用于获取复选框按钮选中，用于删除事件
			switch (obj.event) {
			  case 'addRole': addRole();						//新增角色
								break;
			  case 'deleteRole':  								// 获得当前选中的，不管它的状态是什么？只要是选中的都会获得
				  				deleteRole(tableId);  			//删除角色
				     			break;
			}
	    });
		
		
		/* 以下是对各个方法的调用 ******************************************************************************************************************************************/ 
		/* 以下是对各个方法的调用 ******************************************************************************************************************************************/
		/* 以下是对各个方法的调用***************************************************************************************************************************************** */
		/* 以下是对各个方法的调用 ******************************************************************************************************************************************/
		
		
		// tr点击触发复选列点击
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		function addRole(){  										 	//添加角色方法
			var data={
				id:'',name:'',role:'',isShow:false,description:''		//使用空数据对模板渲染，得到空弹窗
			};				
			var html='';
			var tpl=editRoleTempl.innerHTML;     						//进行模板渲染，将渲染的结果作为弹出窗的内容
			laytpl(tpl).render(data,function(h){
				html=h;
			})
        	var addRole=layer.open({
	        		type: 1,
		            title: '添加角色',
		            area:['30%','60%'],
		            content:html,
		            btn:["确定",'取消'],
		            yes:function(){
		            	var load=layer.load(1);
						var role={
								name:$('#editRoleName').val(),								//角色名
								role:$('#editRole').val(),									//角色英文名
								roleType:$('#editRoleType').val(),							//角色类型
								isShow:document.getElementById("editIsShow").checked?1:0,  	//是否有用
								description:$('#editDescription').val()						//具体描述
						}
						$.ajax({
  							url:"${ctx}/roles/add",
  							type:"post",
  							data:role,
  							success:function(result){
  								if(0==result.code){
  									layer.msg("添加成功" ,{icon: 1});		//返回的message为空。
  									table.reload('LAY-role-table');
  									layer.close(addRole); 					//关闭添加角色的窗口
  								}else
  									layer.msg(result.data, {icon: 2});		//返回的message为空。结果放在data中
  							}
  						})  
						layer.close(load);		//关闭加载
  					}
  				})
  				form.render();					//渲染表单，否则开关与下拉框不显示
   		}
		function editRole(data){  				//修改角色方法
			var html='';
			var tpl=editRoleTempl.innerHTML;    //进行模板渲染，将渲染的结果作为弹出窗的内容
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			var edit=layer.open({
				title:'编辑角色信息'
				,type:1
				,btn:['确定','取消']
				,area:['30%','60%']
				,content:html
				,yes:function(){
					var load=layer.load(1);
					var updateName=false;						//记录名字是否修改
					if(data.name!=$('#editRoleName').val()){ 	//判断角色名是否进行了修改
						var res=$.ajax({  						//判断角色名是否存在
							url:'${ctx}/roles/exists?name='+$('#editRoleName').val(),
							success:function(result){
								if(0!=result.code){  
									updateName=true;	//修改了角色名，且与其他角色名冲突
								}
							}
						})
					}
					if(updateName){
						layer.msg("该角色名已存在",{icon:2});
						layer.close(load);
						return;
					}
					var role={
						id:$('#editRoleId').val(),			//当前角色id
						name:$('#editRoleName').val(),		//角色名
						role:$('#editRole').val(),			//角色英文名
						roleType:$('#editRoleType').val(),	//角色类型
						isShow:document.getElementById("editIsShow").checked?1:0,  //是否有用
						description:$('#editDescription').val()	//具体描述
					};
					$.ajax({
						url:"${ctx}/roles/update",
						type:"post",
						data:role,
						success:function(result){
							if(result.code==0){
								layer.msg("修改成功",{icon:1});
								layer.close(edit);
							}
							else
								layer.msg(result.code+" "+result.message,{icon:2});
							table.reload('LAY-role-table');
							layer.close(load);
						}
					})
					
				}
			})
			form.render();
		}
		function deleteRole(tableId){    	//删除角色方法
			var deleteData={   			 	//设置要删除的id集合
			  ids: tablePlug.tableCheck.getChecked(tableId)
			}
			layer.confirm('您是否确定要删除选中的条记录？', function () {
			 	var load=layer.load(1);   //打开加载层
			  	$.ajax({
			  		url:"${ctx}/roles/delete",
			  		type:"get",
			  		data:deleteData,
			  		traditional:true,     	//阻止深度序列化参数对象
			  		success:function(result){ 
			  			if(result.code==0){
			  				layer.msg(result.message,{icon:1});
			  				table.reload('LAY-role-table');
			  			}
			  			else
			  				layer.msg(result.message,{icon:2});
			  			layer.close(load);
			  		},
			  		error:function(result){
			  			layer.msg(result.message,{icon:2});
			  			layer.close(load);
			  		}
			  	})
			 
			});
        }
		function lookOverPermission(data){  		//查看角色权限方法
			var aboutPermission=layer.open({     	//打开查看权限内容的弹窗
				   title: '查看角色权限：'+data.name
				   ,type:1
				   ,area: ['80%', '80%']
				   ,content:'<div class="table_th_search" lay-filter="LAY-lookOver-Permissions" id="LAY-lookOver-Permissions"></div>'  //permissionTable
				   ,end:function(){
					   $('#LAY-lookOver-Permissions').css("display","none");
				   }
			});
			
			table.render({     //查看权限的表格
				elem:'#LAY-lookOver-Permissions',
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
				id:'LAY-lookOver-Permissions'
			})	
			table.on('tool(LAY-lookOver-Permissions)',function(obj){    	//监听表格中的按钮tool 
				switch(obj.event){
					case 'edit':  editPermission(obj);   break;     		//编辑权限
					case 'remove': removePermission(obj); break;			//删除权限
				}
			})
		 	table.on('toolbar(LAY-lookOver-Permissions)', function (obj) {  //监听表格中的工具栏按钮toolbar
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
				layer.open({
					title:'编辑权限'
					,type:1
				 	,area: ['40%', '40%']
					,content:html
					,btn: ['确认', '取消']
					,yes:function(){  													// 进行修改
						var ids=formSelects.value('selectId'+obj.data.id, 'valStr');	//获取多选下拉框的值，用,分割开的字符串: 1,2
						if(ids==""){
							layer.msg("权限类型不能为空",{icon:2});
							return;
						}
						var data={
							id:	obj.data.id,
							permissionIds:ids
						};
						var load=layer.load(1);
						$.ajax({
							url:"${ctx}/roles/changeRole",
							type:"POST",
							data:data,
							success:function(result){
								if(result.code==0){
									layer.msg("修改成功",{icon:1});
									layer.close(addPer);
								}
								else
									layer.msg(result.code+''+result.message,{icon:2});
								table.reload('LAY-lookOver-Permissions');
								layer.close(load);
							},
							error:function(result){
								layer.close(load);
								layer,msg(result.message,{icon:2});
							}
						}) 
					}
				})
				formSelects.render('selectId'+obj.id);  //无参数时，自动渲染所有
			}
			function removePermission(obj){  			//删除权限
				var id=obj.data.id;
				layer.confirm("确定删除吗？",function(){
					var load=layer.load(1);
					$.ajax({
						url:'${ctx}/roles/deleteRole',
						type:"post",
						data:{id:id},
						success:function(result){
							if(0==result.code){
								layer.msg('删除成功!',{icon:1});
							}
							else
								layer.msg(result.code+''+result.message,{icon:2});
							table.reload('LAY-lookOver-Permissions');
							layer.close(load);
						}
					})
				})
			}
			function addPermission(roleId){    			//增加权限
				
				choosePermission=[];  		 			//每次打开加权限的按钮，对之前所添加的权限清空
				var html='';       						//打开加权限窗口的内容
				html+='<div style="width:40%;float:left;border:1px solid gray;height:400px;overflow:auto;margin:10px;padding:10px;" id="menuDiv">';    //左侧存放联级菜单的div
				for(var i=0;i<allMenu.length;i++){    	//拼接菜单级联
					html+='<div><p><a href="javascript:;" value="'+allMenu[i].id+'" url="'+allMenu[i].url+'" parent="'+allMenu[i].parentId+'" name="'+allMenu[i].name+'">'+allMenu[i].name+'</a></p>';
					if(allMenu[i].children!=null)   	//如果有下级菜单，进行递归拼接 creatHtml(子菜单,'相对于父菜单所使用的缩进')
						html+=creatHtml(allMenu[i].children,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
					html+='</div>';
				}
				html+='</div>'+   						//左侧菜单链接div结束
						'<div style="margin:auto;margin-top:150px;float:left;width:5%;text-align:center;"><p><i class="layui-icon layui-icon-next" ></i></p>'+	 
						'<p>选择添加权限</p></div>'+	   //中间存放添加菜单按钮的div
						'<div style="float:right;width:40%;height:400px;border:1px solid gray;margin:10px;padding:10px;overflow:auto;" id="choosedDiv"></div>'+    //右侧存放选中菜单的div
						'<div style="float:right;width:100%;text-align:center;"><button type="button" lay-submit lay-filter="addPermissionSure"'+
						' value="'+roleId+'" class="layui-btn layui-btn-sm" >确定</button></div>'; //确定按钮
				function creatHtml(menu,nbsp){   		//对多级菜单进行递归拼接
					var str='<div style="display:none;">';
					for(var i=0;i<menu.length;i++){
						str+='<p>'+nbsp+'<a href="javascript:;" value="'+menu[i].id+'" url="'+menu[i].url+'" name="'+menu[i].name+'" parent="'+menu[i].parentId+'">|-'+menu[i].name+'</a>';
						if(menu[i].children!=null){ 	//如果有下级，继续递归
							str+=creatHtml(menu[i].children,nbsp+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
						}
						str+='</p>';
					}
					return str+'</div>';
				}
				var addPer=layer.open({    							//打开添加权限的窗口
					 title: '添加权限'
					   ,type:1
					   ,area: ['40%', '80%']
					   ,content:html
				}) 
				form.on('submit(addPermissionSure)',function(obj){   	//加权限中，确定按钮的监听
					var roleId=obj.elem.value;							
					//--------------传递数据初始化-------------
					var permissions=[]; 
					for(var i=0;i<choosePermission.length;i++){   		//将所有的select的选择赋值给choosePermission
						var t={"menuId":''+choosePermission[i].id,
								"permissionIds":formSelects.value('selectId'+choosePermission[i].id, 'valStr') }; 
						permissions.push(t);
					}
					var PLids=[];
					for(var i=0;i<permissionLevel.length;i++){
						PLids.push(permissionLevel[i].id);
					}
					var ids=PLids.join(',');
					for(var i=0;i<parentMenu.length;i++){
						if(parentMenu[i].choosed==true){
							var t={"menuId":''+parentMenu[i].id,
									"permissionIds":ids }; 
							permissions.push(t);
						}
					}
				 	//-------------传递数据准备结束----------
					var data={
								roleId:roleId,
								permissions:JSON.stringify(permissions)
							};
				 	var load=layer.load(1);
					 $.ajax({
						url:"${ctx}/roles/changeRole",
						type:"POST",
						data:data,
						success:function(result){
							if(result.code==0){
								layer.msg("添加成功",{icon:1});
								layer.close(addPer);
							}
							else
								layer.msg(result.code+''+result.message,{icon:2});
							table.reload('LAY-lookOver-Permissions');
							layer.close(load);
						},
						error:function(result){
							layer.close(load);
							layer,msg(result.message,{icon:2});
						}
					})
				})
				//监听加权限中菜单级联中 a 的点击操作
				$('#menuDiv').find('a').on('click',function(){   
					 var p= $(this).attr("url");
					 if(p=="#"){                   								//如果为#,则只切换下级菜单栏的显示、隐藏。不做其他操作 return
						 var display =$(this).parent().next().css("display")    //菜单下级菜单隐藏显示的切换
						 if(display=='none'){
							$(this).parent().next().css("display","block");  
						}else{
							$(this).parent().next().css("display","none"); 
						}  
					 	//将该菜单存于父parent菜单数组中,用于子菜单被选中时，父菜单的联级反应
					 	var i=0;
					 	for(;i<parentMenu.length;i++){
					 		if(parentMenu[i].id==$(this).attr("value"))
					 			break;
					 	}
					 	if(!(i<parentMenu.length)){
					 		var par={ id:$(this).attr("value"),
			 						name:$(this).attr("name"),
			 						parent:$(this).attr("parent"),
			 						choosed:false,
			 						level:1,
			 						choosedNum:0};
					 		parentMenu.push(par); 
					 	}
					 	return;
					 }  
					//以下是选中权限的操作，通过背景颜色判断是选中还是取消操作         
					 var bg=$(this).parent().css("background-color");
					 if(bg=="rgb(204, 255, 255)"){         				//如果有背景颜色,则该点击为取消选中操作
					 	$(this).parent().css("background-color","");
					 	for(var i=0;i<choosePermission.length;i++){    	//从选中数组中去除该对象
					 		if(choosePermission[i].id==$(this).attr("value")){
					 			$('#p-'+$(this).attr("value")).remove();
					 			choosePermission.splice(i,1);
					 		}
					 	}
					 }
					 else{														//如果没有颜色，则该点击为选中操作
					 	$(this).parent().css("background-color","#CCFFFF");   	//CCFFFF对应rgb(204, 255, 255)
					 	var perm={
					 			id:$(this).attr("value"),
					 			name:$(this).attr("name"),
					 			parent:$(this).attr("parent"),
					 			level:''
					 	}
					 	choosePermission.push(perm);  	//添加到选中数组
					 	 var html='';       			//对选中数组在选中div中的显示
						 html+='<p id="p-'+perm.id+'">&nbsp;<a href="javascript:;" value="'+perm.id+'">'+perm.name+'</a>'+getPermissionLevelSelect(perm)+'</p>';
						 $('#choosedDiv').append(html);
						 formSelects.render('selectId'+perm.id);  	//渲染多选下拉框。无参数时，自动渲染所有
					 }
					 
					 parentChoose();  	//子菜单选中对父级菜单的级联反应，选中子菜单，父菜单默认选中
					 function parentChoose(){
						for(var i=0;i<parentMenu.length;i++){  		//对父菜单数据初始化，便于重新计算
							parentMenu[i].choosed=false;
							parentMenu[i].choosedNum=0;
						}
						for(var i=0;i<choosePermission.length;i++){   //重新计算父菜单中子菜单选中的个数
							var c=choosePermission[i];
							for(var j=0;j<parentMenu.length;j++){     //如果选中的菜单数组中父id等于父数组中的id
								if(c.parent==parentMenu[j].id){
									if(parentMenu[j].choosed==false){
										parentMenu[j].choosed=true;
										parentMenu[j].choosedNum=1;
									}
									else 
										parentMenu[j].choosedNum+=1;
								}
							}
						}  //for end
						for(var i=0;i<parentMenu.length;i++){ //对父菜单的父菜单的计算，此处为写死，只能渲染3级菜单，正确写法应该使用递归进行渲染
							if(parentMenu[i].parent!=0 && parentMenu[i].choosedNum!=0 ){//parentMenu[i]父id不为0，表示该菜单有上级菜单,且该菜单中有子菜单被选中
								for(var j=0;j<parentMenu.length;j++){    
									if(parentMenu[i].parent==parentMenu[j].id ){  	//找出该菜单的父菜单parentMenu[j]
										if(parentMenu[j].choosed==false){
											parentMenu[j].choosed=true;
											parentMenu[j].choosedNum=parentMenu[i].choosedNum;
										}
										else
											parentMenu[j].choosedNum+=parentMenu[i].choosedNum;
									}
								}
							}
						}  // for end
						for(var i=0;i<parentMenu.length;i++){      //对父菜单的渲染
							$('#menuDiv').find('a').each(function(){
								if($(this).attr("value")==parentMenu[i].id){ 
									var prefix='';
									if($(this).text().indexOf("|-")>=0)
										prefix="|-";
									if(parentMenu[i].choosed==false){
										$(this).html(prefix+parentMenu[i].name);
										$(this).parent().css("background-color","");
									}else{
										$(this).parent().css("background-color",'#CCFFFF');
										$(this).html(prefix+parentMenu[i].name+' '+'<span class="layui-badge">'+parentMenu[i].choosedNum+'</span>')
									}
								}
							}) //('a').each() end
						} // for end
					 }//子菜单选中，父菜单默认选中结束
				 })//监听a点击事件结束		
			}//加权限结束（窗口的弹出、逻辑的判断。。）
			
			
		}//查看角色权限结束
		
	}// defind end
);

function getMenu(){
	 $.ajax({
		url : "${ctx}/getTreeMenuPage",
		type : "get",
		success : function(result) {
			if(0==result.code){
				var rows=result.data;  
				for(var i=0;i<rows.length;i++){  
					allMenu.push(rows[i]);   
				}
			}
		} 
	 });
}
function getPermissionLevel(){   //获取相关的权限等级
	$.ajax({
		url:"${ctx }/getPermission",
		type:"get",
		success:function(result){
			if(0==result.code){
				var data=result.data;
				for(var i=0;i<data.length;i++){
					permissionLevel.push(data[i]);
				}
			}
		}
	})
}

function getPermissionLevelSelect(menu){   //获取选中菜单的下拉框
	var html='<select name="permissionLevelSelect" xm-select="selectId'+menu.id+'" xm-select-show-count="3" >';  //xm-select-show-count="2", 超出后隐藏
	
	for(var i=0;i<permissionLevel.length;i++){   
		var selected='';
		if(i==0)   //默认选中某一列
			selected='selected';
		html+='<option '+selected+' value="'+permissionLevel[i].id+'">'+permissionLevel[i].name+'</option>';
	}
	html+="</select>";
	return html;
}
</script>



</body>
</html>