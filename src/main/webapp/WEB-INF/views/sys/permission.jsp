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
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限控制</title>
</head>
<body>

	<div class="layui-card" >
		<div class="layui-card-body" style="height:800px;">
			<div style="float:left;" id="table1">
				<table id="permission-info-table1" class="table_th_search"  lay-filter="permission-info-table1" ></table>
			</div>
			<div style="float:left;" id="table2">
				<table id="permission-info-table2" class="table_th_search"  lay-filter="permission-info-table2" ></table>
			</div>
			<div style="float:left;" id="table3">
				<table id="permission-info-table3" class="table_th_search"  lay-filter="permission-info-table3"></table>
			</div>
		</div> 
	</div>
	
</body>


<!-- 表格操作按钮  -->
<script type="text/html" id="templ-aboutPerson">
	<button type="button" class="layui-btn layui-btn-sm" value="{{d.url}}" lay-event="lookoverRole">查看角色</button>
	<button type="button" class="layui-btn layui-btn-sm" value="{{d.url}}" lay-event="edit">编辑</button>
	<button type="button" class="layui-btn layui-btn-sm" value="{{d.url}}" lay-event="lookoverChild">查看下级菜单</button>
</script>

<!-- 表格工具栏按钮  -->
<script type="text/html" id="permission-toolbar">
  	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除菜单</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增菜单</span>
	</div>
</script>

<!-- 编辑菜单模板 -->
<script type="text/html" id="templEditMenu">
<div class="layui-form" id="editRoleDiv" style="padding:20px;"> 
<div class="layui-form-item">
	<input type="hidden" id="editRoleId" placeholder="请输入" autocomplete="off" lay-verify="required" value="{{ d.id }}" class="layui-input"></div>
<div class="layui-form-item">
	<label class="layui-form-label">身份</label>
	<div class="layui-input-block">
		<input type="text" id="editRoleName" placeholder="请输入" autocomplete="off" lay-verify="required" value="{{ d.identity }}" class="layui-input"></div>
</div>
<div class="layui-form-item">
	<label class="layui-form-label">菜单名称</label>
	<div class="layui-input-block">
    	<input type="text" id="editRole" placeholder="请输入" autocomplete="off" lay-verify="required" value="{{ d.name }}" class="layui-input"></div>
</div>
<div class="layui-form-item">
  <label class="layui-form-label">是否显示</label>
  <div class="layui-input-block">
    <input type="checkbox" lay-skin="switch" id="isShow" lay-text="可用|不可用" {{ d.isShow==true?'checked':'' }} ></div>
</div>
<div class="layui-form-item layui-form-text">
	<label class="layui-form-label">图标</label>
	<div class="layui-input-block">
		<input type="text" id="editRole" placeholder="请输入" autocomplete="off" lay-verify="required" value="{{ d.icon }}" class="layui-input"></div>
</div>
<div class="layui-form-item layui-form-text">
	<label class="layui-form-label">父菜单id</label>
	<div class="layui-input-block">
		<input type="text" id="editRole" placeholder="请输入" autocomplete="off" lay-verify="required" value="{{ d.parentId }}" class="layui-input"></div>
</div>
<div class="layui-form-item layui-form-text">
	<label class="layui-form-label">url</label>
	<div class="layui-input-block">
		<input type="text" id="editRole" placeholder="请输入" autocomplete="off" lay-verify="required" value="{{ d.url }}" class="layui-input"></div>
</div>
<div class="layui-form-item layui-form-text">
	<label class="layui-form-label">span</label>
	<div class="layui-input-block">
		<input type="text" id="editRole" placeholder="请输入" autocomplete="off" lay-verify="required" value="{{ d.span }}" class="layui-input"></div>
</div>
</div>
</script>

<!-- 新增菜单模板 -->

<!-- 查看角色模板 -->

<!-- <table>
	<tbody>
		<tr id="tr-select"><td>选择菜单：</td><td style="width:150px;"><select class="layui-input" id="first-menus" lay-filter="first-menus">
										<option value="" >选择菜单</option></select></td><td>&nbsp;&nbsp;</td>
			</tr>								
	</tbody>
</table><span class="layui-btn layui-btn-sm" lay-event="sure">确定</span> -->


<script>

var allMenu=[];

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
$(function(){  //菜单初始化应放在页面加载完成后，否则表格的渲染会快于异步获取的数据，导致表格渲染出的数据为空
	getMenu();
})
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
			, tablePlug = layui.tablePlug //表格插件
			, laytpl = layui.laytpl;
			/* 	var allMenu=[];   //存放所有菜单
				var first=[];		//存放一级菜单
				var second=[];	//存放二级菜单
				var third=[];		//存放三级菜单 */
				//initToolBar();
				table.render({
					elem: '#permission-info-table1'
				    ,page: true  //开启分页
				    ,size:'lg'
				    ,height:'700'
				    ,width:'500'
				    ,toolbar:'#permission-toolbar'
				    ,data:allMenu
				    ,done:function(obj){
				    	
				    }
				    ,cols: [[ //表头
				      {field: 'name', title: '菜单名字', width:'180'}
				      ,	{title : '相关操作',templet:'#templ-aboutPerson',width:'316'} 
				    ]]
				});
				
				table.on('tool(permission-info-table1)', function (obj) {  //对第一级表格的监听
					switch(obj.event){
					case 'lookoverRole':break;
					case 'edit': editMenu(obj); break;
					case 'lookoverChild':lookoverChild(obj);break;
					}
				});
				table.on('toolbar(permission-info-table1)', function (obj) {
					 switch (obj.event) {
						 case 'sure':	console.log(obj);break;
						 case 'add':	addMenu();	break;
						 case 'delete':break;
						 		
					 }
				});
				
				function lookoverChild(obj){	//这是监听第一个表格的下级菜单按钮
					$("#table3").hide();
					table.render({
						elem: '#permission-info-table2'
					    ,page: true  //开启分页
					    ,size:'lg'
					    ,height:'700'
					    ,width:'500'
					    ,toolbar:'#permission-toolbar'
					    ,data:obj.data.children
					    ,cols: [[ //表头
					      {field: 'name', title: '菜单名字', width:'180'}
					      ,	{title : '相关操作',templet:'#templ-aboutPerson',width:'316'} 
					    ]]
					});
					table.on('toolbar(permission-info-table2)', function (obj) {	//对第二级表格的监听
						 switch (obj.event) {
							 case 'sure':	break;
							 case 'add':break;
							 case 'delete':break;
						 }
					});
					table.on('tool(permission-info-table2)', function (obj) {
						switch(obj.event){
						case 'lookoverRole':break;
						case 'edit': editMenu(obj); break;
						case 'lookoverChild':if(obj.data.children==null)
												layer.msg("该菜单没有下级菜单",{icon:2});
											else lookoverChild2(obj);break;
						}
					});
					
					function lookoverChild2(obj){	//这是监听第二个表格的下级菜单按钮
						$("#table3").show();
						table.render({
							elem: '#permission-info-table3'
						    ,page: true  //开启分页
						    ,size:'lg'
						    ,height:'700'
						    ,width:'500'
						    ,toolbar:'#permission-toolbar'
						    ,data:obj.data.children
						    ,cols: [[ //表头
						      {field: 'name', title: '菜单名字', width:'180'}
						      ,	{title : '相关操作',templet:'#templ-aboutPerson',width:'316'} 
						    ]]
						});
						table.on('toolbar(permission-info-table3)', function (obj) {	//对第三级表格的监听
							switch (obj.event) {
								 case 'sure':	break;
								 case 'add':break;
								 case 'delete':break;
							 }
						});
						table.on('tool(permission-info-table3)', function (obj) {
							switch(obj.event){
							case 'lookoverRole':break;
							case 'edit': editMenu(obj); break;
							case 'lookoverChild':lookoverChild3(obj);break;
							}
						});
					}
				}

				function lookoverRole(obj){
					
				}
				function editMenu(obj){
					var html="";
					var tpl=templEditMenu.innerHTML;
					laytpl(tpl).render(obj.data,function(h){	//渲染模板内容
						html=h;
					});
					console.log(obj.data)
					layer.open({
						title:'编辑菜单信息'
						,type:1
						,btn:['确定','取消']
						,area:['30%','60%']
						,content:html
						,yes:function(){
							
						}
					});
					form.render();	//渲染表单，否则开关按钮不显示
				}
				function addMenu(){
					var html="";
					var tpl=templEditMenu.innerHTML;
					var data={
							icon:'',
							id:'',
							identity:'',
							isShow: false,
							name: "",
							parentId: '',
							span: "",
							url: ""
					};
					laytpl(tpl).render(data,function(h){	//渲染模板内容
						html=h;
					});
					layer.open({
						title:'新增菜单信息'
						,type:1
						,btn:['确定','取消']
						,area:['30%','60%']
						,content:html
						,yes:function(){
							
						}
					});
					form.render();	//渲染表单，否则开关按钮不显示
				}
				function deleteMenu(){
					
				}
				
				
				
			/* 	function initToolBar(){
					 $.ajax({
						url : "${ctx}/getMenuPage?size=1000",
						type : "get",
						success : function(result) {
							var rows=result.data.rows;  
							for(var i=0;i<rows.length;i++){
								allMenu.push(rows[i]);
								if(rows[i].parentId==0)    //父id为0 为1级菜单
									first.push(rows[i]);
								else if(rows[i].url=="#"){  //父id不为0，url为#为2级菜单
									second.push(rows[i]);
								}
								else {   					//其他为三级菜单
									third.push(rows[i]);
								}
							}
							var html='';
							for(var i=0;i<first.length;i++){  //拼接一级菜单
								html+=('<option value="'+first[i].id+'">'+first[i].name+'</option>');
							}
							$('#first-menus').append(html);
							form.render();
						} 
					 });
				}//end initToolBar
				function showSelect(obj){
					console.log($('#dsadkjaaskjd').elem);
					var id=obj.value; 						//当前菜单id					
					var selectId;				//存放当前菜单子菜单select id
					if(obj.elem!=null)
						selectId=obj.elem.id+"-child";		//如果传值为jquer对象
					else
						selectId=obj.id;					//为dom对象
					var tdSelectId='td-'+selectId;          //存放当前菜单子菜单select的td 的id
					if(id==null||id==''){				//如果id为空即取消当前菜单的选择
						if(document.getElementById(selectId+'-child')!=null){   //如果子菜单下存在子菜单
							document.getElementById(selectId+'-child').value='';  
							showSelect(document.getElementById(selectId+'-child'));
						}
						document.getElementById(tdSelectId).innerHTML='';
					}else{
						for(var i=0;i<allMenu.length;i++){
							if(allMenu[i].id==id){   
								if(allMenu[i].parentId==0 ||allMenu[i].url=="#") {    //有下级菜单时
									if(document.getElementById(tdSelectId)!=null){  //如果存放select的td存在则清空内容
										document.getElementById(tdSelectId).innerHTML='';
									}else{  											//不存在，则拼接存放子菜单列表的td
										$('#tr-select').append('<td id="'+tdSelectId+'" style="width:150px;"></td><td>&nbsp;&nbsp;</td>');
									}
									var html='<select id="'+selectId+'"><option value="">请选择</option>';
									for(var j=0;j<allMenu.length;j++){                //拼接子菜单内容
										if(allMenu[j].parentId==id)
											html+=('<option value="'+allMenu[j].id+'">'+allMenu[j].name+'</optopn>');
									}
									html+='</select>';
									$('#td-'+selectId).append(html);
									if(document.getElementById(selectId+'-child')!=null){  //如果子子菜单存在
										document.getElementById(selectId+'-child').value='';  
										showSelect(document.getElementById(selectId+'-child'));
									}
									form.render();
									return;
								}
								else {   					//其他无下级菜单
									return;
								}
							}//end id=allMenu[i].id
						}//end for i<allMenu.length
					}//end else id!=null
				} //end showSelect */
			
	}//end defind
);
</script>
</html>