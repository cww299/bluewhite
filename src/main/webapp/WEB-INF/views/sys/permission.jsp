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
<style>

.layui-table-view .layui-form-radio>i {		/*单选框按钮垂直居中   */
    margin-top: 20px;
    font-size: 20px;
    
}
</style>

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
	<button type="button" class="layui-btn layui-btn-sm" value="{{d.url}}" lay-event="edit">编辑</button>
	<button type="button" class="layui-btn layui-btn-sm" value="{{d.url}}" lay-event="lookoverChild">查看下级菜单</button>
</script>

<!-- 表格工具栏按钮  -->
<script type="text/html" id="permission-toolbar">
  	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除菜单</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增菜单</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑</span>
		<span class="layui-btn layui-btn-sm" lay-event="lookoverChild">查看下级菜单</span>
	</div>
</script>

<!-- 编辑菜单模板、新增菜单模板 -->
<script type="text/html" id="templEditMenu">
<div class="layui-form" id="editMenuDiv" style="padding:20px;"> 
<div class="layui-form-item">
	<input type="hidden" id="menuId" value="{{ d.id }}"></div>
<div class="layui-form-item">
	<label class="layui-form-label">身份</label>
	<div class="layui-input-block">
		<input type="text" id="menuIdentity" placeholder="请输入" value="{{ d.identity }}" class="layui-input"></div>
</div>
<div class="layui-form-item">
	<label class="layui-form-label">菜单名称</label>
	<div class="layui-input-block">
    	<input type="text" id="menuName" placeholder="请输入" value="{{ d.name }}" class="layui-input"></div>
</div>
<div class="layui-form-item">
  <label class="layui-form-label">是否显示</label>
  <div class="layui-input-block">
    <input type="checkbox" lay-skin="switch" id="menuIsShow" lay-text="是|否" {{ d.isShow==true?'checked':'' }} ></div>
</div>
<div class="layui-form-item layui-form-text">
	<label class="layui-form-label">图标</label>
	<div class="layui-input-block">
		<input type="text" id="menuIcon" placeholder="请输入" autocomplete="off" value="{{ d.icon }}" class="layui-input"></div>
</div>
<div class="layui-form-item layui-form-text">
	<label class="layui-form-label">父菜单id</label>
	<div class="layui-input-block">
		<input type="text" id="menuParentId" placeholder="请输入" readonly autocomplete="off" value="{{ d.parentId }}" class="layui-input"></div>
</div>
<div class="layui-form-item layui-form-text">
	<label class="layui-form-label">url</label>
	<div class="layui-input-block">
		<input type="text" id="menuUrl" placeholder="请输入" autocomplete="off" value="{{ d.url }}" class="layui-input"></div>
</div>
<div class="layui-form-item layui-form-text">
	<label class="layui-form-label">span</label>
	<div class="layui-input-block">
		<input type="text" id="menuSpan" placeholder="请输入" autocomplete="off" value="{{ d.span }}" class="layui-input"></div>
</div>
</div>
</script>



<script>

layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug'
}).define(
		[ 'tablePlug', 'laydate' ],
		function() {
			var $ = layui.jquery
			, layer = layui.layer 			//弹层
			, form = layui.form 			//表单
			, table = layui.table 			//表格
			, tablePlug = layui.tablePlug 	//表格插件
			, laytpl = layui.laytpl;
				var isTable2Render=false;		//记录表格2是否渲染了，作用于表格重载时，判断此表格有没有被渲染出来，如果该表格没有被渲染出来，则忽略，否则对表格重载
				var isTable3Render=false;		//记录表格3是否渲染了
				var radioObj1;  				//用于记录单选框选中的对象
				tableRender('#permission-info-table1',0);							//渲染table1
				table.on('toolbar(permission-info-table1)', function (obj) {		//监听工具栏
					 switch (obj.event) {
						 case 'add':	addMenu(0);	break;							//如果为添加一级菜单，则父id为0
						 case 'delete': deleteMenu(radioObj1); break;
						 case 'edit': editMenu(radioObj1); break;
						 case 'lookoverChild':  if(radioObj1==null ||radioObj1=="")	//如果单选框没有选中任何行
		 											layer.msg("请选择菜单",{icon:2});
						 						else lookoverChild(radioObj1);break;
					 }
				});
				table.on('radio(permission-info-table1)',function(obj){				//监听单选按钮
					radioObj1=obj;
				//console.log($(this))
				}) 
				table.on('row(permission-info-table1)', function(obj){				//监听行点击事件
					//$(this).children()[0].getElementsByTagName("i")[0].click();
				
					//$(this).children()[0]).getElementsByTagName("div")[1].click();
				}); 
				
				
				function lookoverChild(obj){			//这是监听第一个表格的下级菜单按钮
					$("#table3").hide();
					isTable2Render=true;				//table2渲染
					var parentId=obj.data.id;			//记录当前对象的id。用于新增菜单时，记录其父菜单的id
					var radioObj2;						//记录当前单选框选中的
					tableRender('#permission-info-table2',parentId);				//渲染table2
					table.on('toolbar(permission-info-table2)', function (obj) {	//对第二级表格的监听
						 switch (obj.event) {
							 case 'add':	addMenu(parentId); break;
							 case 'delete':	deleteMenu(radioObj2);  break;
							 case 'edit': editMenu(radioObj2); break;
							 case 'lookoverChild':  if(radioObj2==null ||radioObj2=="")
							 							layer.msg("请选择菜单",{icon:2});
													else  lookoverChild2(radioObj2);break;
						 }
					});
					table.on('radio(permission-info-table2)',function(obj){
						radioObj2=obj;
					}) ;
					
					
					function lookoverChild2(obj){								//这是监听第二个表格的下级菜单按钮
						isTable3Render=true;
						$("#table3").show();
						var parentId=obj.data.id; 
						tableRender('#permission-info-table3',parentId);				//渲染table3
						var radioObj3;
						table.on('toolbar(permission-info-table3)', function (obj) {	//对第三级表格的监听
							switch (obj.event) {
								 case 'add':	addMenu(parentId);	break;
								 case 'delete': deleteMenu(radioObj3); break;
								 case 'edit': editMenu(radioObj3); break;
								 case 'lookoverChild':layer.msg("该菜单没有下级菜单",{icon:2});break;
							 }
						});
						table.on('radio(permission-info-table3)',function(obj){
							radioObj3=obj;
						})
					}
				}

				
				function editMenu(obj){							//编辑菜单信息
					var html="";
					var tpl=templEditMenu.innerHTML;
					laytpl(tpl).render(obj.data,function(h){	//渲染模板内容
						html=h;
					});
					layer.open({
						title:'编辑菜单信息'
						,type:1
						,btn:['确定','取消']
						,area:['30%','60%']
						,content:html
						,yes:function(){
							save();
						}
					});
					form.render();							//渲染表单，否则开关按钮不显示
				}
				function addMenu(parentId){					//新增菜单
					var html="";
					var tpl=templEditMenu.innerHTML;
					var data={								//使用空数据渲染模板
							icon:'',id:'',identity:'',isShow: false,name: "",parentId: parentId,span: "",url: ""
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
							save();							//ajax保存事件
						}
					});
					form.render();	
				}
				function deleteMenu(obj){
					if(obj==null || obj==''){
						layer.msg("请选择菜单",{icon:2});
						return;
					}
					layer.confirm("是否确认删除？",function(index){
						var load=layer.load(1);
						$.ajax({
							url:"${ctx }/deleteMenu",
							data:{ids:""+obj.data.id},
							success:function(result){
								if(0==result.code){
									layer.closeAll();
									reloadTable();
									layer.msg(result.message,{icon:1});
								}
								else
									layer.msg(result.code+" "+result.message,{icon:2});
								layer.close(load);
							}
						})
					})
					
				}
				function tableRender(elem,parentId){		//渲染表格，elem被渲染的table容器，parentId接口的父id
					table.render({							//2级菜单表格
						elem: ''+elem
					    ,page: false 
					    ,url:"${ctx }/getTreeMenuParent?id="+parentId
						,parseData:function(res){ 			//res 即为原始返回的数据
						        return {
						            "code": res.code, 		//解析接口状态
						            "msg": res.message, 	//解析提示文本
						            "data": res.data 		//解析数据列表
						          }; }
					    ,size:'lg'
					    ,height:'700'
					    ,width:'550'
					    ,toolbar:'#permission-toolbar'
					    ,cols: [[ 
					        {type: 'radio',align : 'center',fixed: 'left',align:'center'},
					        {field: 'name', title: '菜单名字', },
					        {field: 'identity', title: '身份', },
						      {field: 'isShow', title: '是否显示', }
					    ]]
					});
					form.render();
				}
				function save(){					//用于修改和新增菜单的方法
					if($('#menuName').val()==''){
						layer.msg('菜单名不能为空',{icon:2});
						return;
					}
					if($('#menuIdentity').val()==''){
						layer.msg('菜单身份不能为空',{icon:2});
						return;
					}
					var data;
					if($('#menuId').val()=='')		//判断id是否有值。区别于修改还是新增
						data={
								identity:$('#menuIdentity').val(),
								name:$('#menuName').val(),
								isShow:document.getElementById("menuIsShow").checked?true:false,  
								icon:$('#menuIcon').val(),
								parentId:parseInt($('#menuParentId').val()),
								url:$('#menuUrl').val(),
								span:$('#menuSpan').val()
						};
					else
						data={
							id:parseInt($('#menuId').val()),
							identity:$('#menuIdentity').val(),
							name:$('#menuName').val(),
							isShow:document.getElementById("menuIsShow").checked?true:false,  
							icon:$('#menuIcon').val(),
							parentId:parseInt($('#menuParentId').val()),
							url:$('#menuUrl').val(),
							span:$('#menuSpan').val()
						};
					var load=layer.load(1);
					$.ajax({
						url:'${ctx}/saveMenu',
						data:data,
						success:function(result){
							if(0==result.code){
								layer.msg(result.message,{icon:1});
								reloadTable();
								layer.closeAll();
							}else{
								layer.msg(result.code+' '+result.message,{icon:2});
							}
							layer.close(load);
						}
					});
				}
				function reloadTable(){				//表格的重载
					table.reload('permission-info-table1');
					if(isTable2Render)
						table.reload('permission-info-table2');
					if(isTable3Render)
						table.reload('permission-info-table3');
				}
	}//end defind
);
</script>
</html>
