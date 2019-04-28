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
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
<title>角色管理</title>





</head>

<body>
	
<div class="layui-card">
	<div class="layui-card-body">
		<div class="layui-form layui-card-header layuiadmin-card-header-auto">
			<table>
				<tr>
					<td>用户名：</td>
					<td><input type="text" class="layui-input" placeholder="请输入用户名"></td>
					<td>角色名：</td>
					<td><select name="city" lay-verify="required">
						        <option value=""></option>
						        <option value="0">北京</option>
						      </select></td>
				</tr>
			</table>
		</div>
		
		<div id="LAY-role-table" class="table_th_search" lay-filter="LAY-role-table"></div>
	</div>
</div>

<!-- 添加角色隐藏框 -->
<div class="layui-form" id="addRoleDiv" style="display:none;">
		<table class="layui-table" lay-skin="line" style="text-align:center;">
			<thead></thead>
			<tbody>
				<tr><td>角色名：</td>
					<td><input type="text" class="layui-input" lay-verify="required" name="name"></td></tr>
				<tr><td>英文名称：</td>
					<td><input type="text"  class="layui-input" name="role"></td></tr>
				<tr><td>具体描述：</td>
					<td><input type="text" class="layui-input" name="description"></td></tr>
				<tr><td colspan="2"><button type="button" lay-filter="addRoleSure" lay-submit class="layui-btn layui-btn-sm">确定</button></td></tr>
			</tbody>

	</table>
</div>



      

			
<!-- 选择框组件 -->
<script type="text/html" id="switchTpl">
	<input type="checkbox" name="isShow" value="{{d.isShow}}" lay-skin="switch" lay-text="可用|不可用" lay-filter="isShow" {{ d.isShow == true ? 'checked' : '' }} >
</script>

<script type="text/html" id="toolbarDemo">
  	<div class="layui-btn-container layui-inline">
   		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteRole">批量删除</span>
    	<span class="layui-btn layui-btn-sm" lay-event="addRole">添加角色</span>
	</div>
</script>

<script type="text/html" id="aboutPermission">
		<button type="button" class="layui-btn layui-btn-sm" lay-event="aaa">查看权限</button>
</script>

<script type="text/html" id="addPermission">
  	
</script>
<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script> 
<script>

var allMenu=[];    //所有的menus接口菜单
var choosePermission=[];  //链接菜单中被选中的三级菜单，id存放本身id，name存放菜单名，parent存放父id
var parentMenu=[];		//用于联级子菜单点击父菜单的显示功能,id存放的本身id，name存放菜单名，parent存放父id，choosed子菜单是否有被选中,choosedNum子菜单有多少个被选中
var permissionLevel=[];   //用于存放相关的权限等级
function getMenu(){
	 $.ajax({
		url : "${ctx}/getTreeMenuPage",
		type : "get",
		success : function(result) {
			var rows=result.data;  
			for(var i=0;i<rows.length;i++){  
				allMenu.push(rows[i]);   
			}
		} 
	 });
}
function getPermissionLvevl(){   //获取相关的权限等级
	$.ajax({
		url:"",
		type:"",
		success:function(){
			
		}
	})
}

function getPermissionLvevlSelct(menu){
	
}

layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug'
}).define(
	[ 'tablePlug', 'laydate','element' ],
	function() {
		var $ = layui.jquery
		, layer = layui.layer //弹层
		, element = layui.element
		, form = layui.form //表单
		, table = layui.table //表格
		, laydate = layui.laydate //日期控件
		, tablePlug = layui.tablePlug; //表格插件
		// 处理操作列
		var fn1 = function(field) {
			return function(data) {
				return ['<select name="citye" lay-filter="city_selecte" lay-search="true">',
					'<option value="管理员">管理员</option>',
					'<option value="超级管理员">超级管理员</option>',
						'</select>' 
						].join('');
			};
		};
		getMenu();   //获取菜单，添加权限级联时使用
		table.render({
			elem : '#LAY-role-table',
			size : 'lg',
			url : '${ctx}/roles/page' ,
			request:{
				pageName: 'page' ,//页码的参数名称，默认：page
				limitName: 'size' //每页数据量的参数名，默认：limit
			},
			page : {},        //开启分页
			loading : true,     //开启加载动画
			toolbar : '#toolbarDemo',//开启工具栏，此处显示默认图标
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
				{field : "description",title : "具体描述",edit : 'text'},
				{title : "查看权限",templet : "#aboutPermission"}
			] ]
		});

		// 监听表格中的下拉选择将数据同步到table.cache中
		form.on('select(city_selecte)', function(data) {
			var selectElem = $(data.elem);
			var tdElem = selectElem.closest('td');
			var trElem = tdElem.closest('tr');
			var tableView = trElem.closest('.layui-table-view');
			table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
		});
		table.on('tool(LAY-role-table)',function(obj){   //监听查看权限按钮,obj为监听该行的对象
			var data=obj.data;           
			var rp=data.resourcePermission;   //拥有的权限
			var permissionTable='<p>&nbsp;<button class="layui-btn layui-btn-sm" lay-filter="addPermission" lay-submit value="'+obj.id+'">新增权限</button></p>'+
								'<table class="layui-table" style="text-align:center"><thead><th>创建时间</th><th>菜单id</th><th>id</th><th>权限等级</th><th>'+
								'更新时间</th><th>移除</th></head><tbody>';
			if(rp.length>0){         //如果拥有权限，拼接权限的表格内容
				for(var i=0;i<rp.length;i++){
					var p=rp[i];
					permissionTable+=('<tr><td>'+p.createdAt+'</td><td>'+p.menuId+'</td><td>'+p.id+'</td><td>'+p.permissionIds[0]+'</td><td>'
										+p.updatedAt+'</td><td><button class="layui-btn layui-btn-sm" onclick="removePermission('+p.id+')">移除</button></td></tr>');
				}
			}
			else{
				permissionTable+='<tr><td colspan="6" style="text-align:center">该角色还没有权限</td></tr>';
			}
			permissionTable+='</tbody></table>';

			
			form.on('submit(addPermission)',function(obj){  //监听添加权限按钮
				addPermission(obj.id);
			});
			form.render();
			
			var aboutPermission=layer.open({     //打开查看权限内容的弹窗
				   title: '查看角色权限：'+data.name
				   ,type:1
				   ,area: ['80%', '80%']
				   ,content:permissionTable
			}); 
			
			
			function addPermission(roleId){    //给某一角色添加权限，需要该角色的id，和相关权限信息
				choosePermission=[];   //每次打开添加权限的按钮，对之前所添加的权限清空
				var html='';       //打开添加权限窗口的内容
				html+='<div style="width:40%;float:left;border:1px solid gray;height:400px;overflow:auto;margin:10px;padding:10px;" id="menuDiv">';    //左侧存放联级菜单的div
				for(var i=0;i<allMenu.length;i++){    //拼接菜单级联
					html+='<div><p><a href="javascript:;" value="'+allMenu[i].id+'" url="'+allMenu[i].url+'" parent="'+allMenu[i].parentId+'" name="'+allMenu[i].name+'">'+allMenu[i].name+'</a></p>';
					if(allMenu[i].children!=null)   //如果有下级菜单，进行递归拼接 creatHtml(子菜单,'相对于父菜单所使用的缩进')
						html+=creatHtml(allMenu[i].children,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
					html+='</div>';
				}
				html+='</div>'+   //左侧菜单链接div结束
						'<div style="margin:auto;margin-top:150px;float:left;width:5%;text-align:center;"><p><i class="layui-icon layui-icon-next" ></i></p>'+	 
						'<p>选择添加权限</p></div>'+	   //中间存放添加菜单按钮的div
						'<div style="float:right;width:40%;height:400px;border:1px solid gray;margin:10px;padding:10px;overflow:auto;" id="choosedDiv"></div>'+    //右侧存放选中菜单的div
						'<div style="float:right;width:100%;text-align:center;"><button type="button" lay-submit lay-filter="addPermissionSure" class="layui-btn layui-btn-sm">确定</button></div>'; //确定按钮
				
				function creatHtml(menu,nbsp){   //对多级菜单进行递归拼接
					var str='<div style="display:none;">';
					for(var i=0;i<menu.length;i++){
						str+='<p>'+nbsp+'<a href="javascript:;" value="'+menu[i].id+'" url="'+menu[i].url+'" name="'+menu[i].name+'" parent="'+menu[i].parentId+'">|-'+menu[i].name+'</a>';
						if(menu[i].children!=null){
							str+=creatHtml(menu[i].children,nbsp+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
						}
						str+='</p>';
					}
					return str+'</div>';
				}
				var addPer=layer.open({    //打开添加权限的窗口
					 title: '添加权限'
					   ,type:1
					   ,area: ['40%', '80%']
					   ,content:html
				}) 
				form.on('submit(addPermissionSure)',function(){   //添加权限中，确定按钮的监听
					alert("添加失败");
				})
				
				//监听添加权限中菜单级联中 a 的点击操作
				$('#menuDiv').find('a').on('click',function(){   
					 var p= $(this).attr("url");
					 if(p=="#"){                    //如果为#,则只切换下级菜单栏的显示、隐藏。不做其他操作 return
						 var display =$(this).parent().next().css("display")    //菜单下级菜单隐藏显示的切换
						 if(display=='none'){
							$(this).parent().next().css("display","block");  
						}else{
							$(this).parent().next().css("display","none"); 
						}  
					 	//将该菜单存于父parent菜单数组中
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
			 						choosedNum:0};
					 		parentMenu.push(par); 
					 	}
					 	return;
					 }  
					//以下是选中权限的操作，通过背景颜色判断是选中还是取消操作         
					 var bg=$(this).parent().css("background-color");
					 if(bg=="rgb(204, 255, 255)"){         //如果有背景颜色,则该点击为取消选中操作
					 	$(this).parent().css("background-color","");
					 	for(var i=0;i<choosePermission.length;i++){    //从选中数组中去除该对象
					 		if(choosePermission[i].id==$(this).attr("value"))
					 				choosePermission.splice(i,1);
					 	}
					 }
					 else{					//如果没有颜色，则该点击为选中操作
					 	$(this).parent().css("background-color","#CCFFFF");   //CCFFFF对应rgb(204, 255, 255)
					 	var perm={
					 			id:$(this).attr("value"),
					 			name:$(this).attr("name"),
					 			parent:$(this).attr("parent"),
					 	}
					 	choosePermission.push(perm);  //添加到选中数组
					 }
					 
					 var html='';       //对选中数组在选中div中的显示
					 for(var i=0;i<choosePermission.length;i++){
						html+='<p>&nbsp;<a href="javascript:;" value="'+choosePermission[i].id+'">'+choosePermission[i].name+'<a>----<select id="'+choosePermission[i].id+'">'+
							  '<option value="1">1</option><option value="2">2</option></select></p>';
					 }
					 $('#choosedDiv').html("已选中："+choosePermission.length+"条权限"+html);
					
					 parentChoose();  	//子菜单选中对父级菜单的级联反应，选中子菜单，父菜单默认选中
					 function parentChoose(){
						for(var i=0;i<parentMenu.length;i++){  //对父菜单数据初始化，便于重新计算
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
						for(var i=0;i<parentMenu.length;i++){      //对父菜单的父菜单的计算
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
				 })
				//监听a点击事件结束		
			
			}//添加权限函数功能结束（窗口的弹出、逻辑的判断。。）
			
			
			
			
		
			
			
			
		})
		//监听表格编辑，修改
		table.on('edit(LAY-role-table)',function(obj){
			var load=layer.load(1);
			var id=obj.data.id;
			var field=obj.field;
			var value=obj.value;
			var data={
					id:id,
					[field]:value
			}
			$.ajax({
				url:'${ctx}/roles/update',
				type:"post",
				data:data,
				success:function(result){
					if(0==result.code){
						layer.msg('修改成功！',{icon:1});
					}
					else
						layer.msg(result.message,{icon:2});
					layer.close(load);
				},
				error:function(){
					layer.msg('ajax error,',{icon:2});
					layer.close(load);
				}
			})
		})
		
		//监听头工具栏事件
	    table.on('toolbar(LAY-role-table)', function (obj) {
	      var config = obj.config;
	      var btnElem = $(this);
	      var tableId = config.id;
	      // var tableView = config.elem.next();
	      switch (obj.event) {
	        case 'addRole': 
	        	var addRole=layer.open({
	        		type: 1,
		            title: '添加角色',
		            area: ['500px', '300px'],
		            content:$('#addRoleDiv')
	  					})
  					form.on('submit(addRoleSure)',function(obj){
  						var load=layer.load(1);
  						$.ajax({
  							url:"${ctx}/roles/exists?name="+obj.field.name,
  							type:"get",
  							success:function(result){
  								if(result.code==0){
  									$.ajax({
  			  							url:"${ctx}/roles/add",
  			  							type:"post",
  			  							data:obj.field,
  			  							success:function(result){
  			  								if(0==result.code){
  			  									layer.msg('添加成功' ,{icon: 1});
  			  								}else
  			  									layer.msg(result.message, {icon: 2});
  			  								
  			  							},
  			  							error:function(){
  			  								layer.msg("操作失败！", {icon: 2});
  			  								layer.close(load);
  			  								layer.close(addRole);
  			  							}
  			  						}) 
  								}
  								else{
  									layer.msg('该角色名已存在',{icon:2});
  								}
  								layer.close(load);		//关闭加载
	  							layer.close(addRole);	//关闭添加角色的窗口
  							}
  						})
  					});
   					break;
	         					
	        case 'deleteRole':  // 获得当前选中的，不管它的状态是什么？只要是选中的都会获得
	        	  var deleteData={    //设置要删除的id集合
	        			  ids: tablePlug.tableCheck.getChecked(tableId)
	        	  }
		          layer.confirm('您是否确定要删除选中的条记录？', function () {
		        	  	var load=layer.load(1);   //打开加载层
		            	$.ajax({
		            		url:"${ctx}/roles/delete",
		            		type:"get",
		            		data:deleteData,
		            		traditional:true,     //阻止深度序列化参数对象
		            		suceess:function(result){
		            			if(result.code==0)
		            				layer.msg('删除成功' ,{icon: 1});
		            			else
		            				layer.msg(result.message,{icon:2});
		            			layer.close(load);
		            		},
		            		error:function(){
		            			layer.msg(result.message,{icon:2});
		            			layer.close(load);
		            		}
		            	})
		        	  
		          });
		          break;
	       
	      }
	    });
		

		// tr点击触发复选列点击
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
		
	}
);
</script>



</body>
</html>