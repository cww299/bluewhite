<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础数据</title>
</head>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr><td>数据类型：</td><td>&nbsp;&nbsp;</td>
				<td id="tdSelect">
					<select><option value="">请选择</option></select><!-- 此处select的作用仅仅是为了页面加载时，保证页面不出现延迟加载，真正的select会在获取数据后渲染模板，重新填充 -->
				</td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" lay-submit class="layui-btn layui-btn-sm" lay-filter="find">查找</button></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" id="addDataType">添加数据类型</button></td>
			</tr>
		</table>
		<table class="table_th_search" id="baseDataTable" lay-filter="baseDataTable"></table>
	</div>
</div>

</body>
<!-- 新增类型弹窗 -->
<div id="addTypeDiv" style="display:none;padding:20px;" class="layui-form">
	<div class="layui-form-item">
		<label class="layui-form-label">数据名称</label>
		<div class="layui-input-block">
			<input type="text" placeholder="请输入" lay-verify="required" class="layui-input" name="name">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block">
			<input type="text" placeholder="请输入" lay-verify="required" class="layui-input" name="remark">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">数据类型</label>
		<div class="layui-input-block">
			<input type="text" placeholder="请输入" lay-verify="required" class="layui-input" name="type">
		</div>
	</div>
	<!-- 以下为默认值，不需要显示，作为默认参数传参 -->
	<input type="hidden" name="flag" value="1">
	<input type="hidden" name="parentId" value="0">
	<p align="center"><button lay-submit class="layui-btn layui-btn-sm" lay-filter="addType">确定</button></p>
</div>
<!-- 表格工具栏 -->
<script type="text/html" id="baseDataToolBar">
	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除基础数据</span>
		<span class="layui-btn layui-btn-sm" lay-event="add">新增基础数据</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑数据</span>
		<span class="layui-btn layui-btn-sm" lay-event="more">查看下级数据</span>
	</div>
</script>
<!-- 添加、编辑基础数据的弹窗模板 -->
<script type="text/html" id="addEditTpl">
	<div class="layui-form" style="padding:20px;">
		<div class="layui-form-item">
			<label class="layui-form-label">数据名称</label>
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
			<label class="layui-form-label">排序</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.ord }}" name="ord" placeholder="请输入" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">类型</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.type }}" name="type" class="layui-input" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注remark</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.remark }}" name="remark" class="layui-input" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">父ID</label>
			<div class="layui-input-block">
				<input type="text" value="{{ d.parentId }}" readonly name="parentId" readonly class="layui-input">
			</div>
		</div>
		<input type="hidden" name="id" value="{{ d.id }}">
		<p align="center"><button type="button" lay-submit lay-filter="addData" class="layui-btn layui-btn-sm">添加</button></p>
	</div>
</script>
<script>
layui.config({
	base: '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil: 'layui/myModules/myutil'
}).define(
	[ 'tablePlug','myutil'],
	function() {
		var $ = layui.jquery
		, table = layui.table
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, form = layui.form
		, tablePlug = layui.tablePlug; 							
		myutil.config.msgOffset = '150px';	//设置对话框高度
		myutil.clickTr();					//设置行点击
		
		form.render();									//渲染表单
		getDataType();									//渲染下拉框
		renderTable('#baseDataTable');			//渲染表格
		var parent={type:'',remark:'',id:0};					//用于记录当前下拉框的选中的值，用于标识type用于记录下拉框的文本，用于标识remark,id
		form.on('submit(find)',function(obj){				
			table.reload('baseDataTable',{ url:"${ctx}/basedata/list?type="+obj.field.baseDataType })
			parent.type=obj.field.baseDataType;
			parent.remark=$('#selectId').find("option:selected").text();
		});
		$('#addDataType').on('click',function(obj){		
			var addType=layer.open({
				title:"新增数据类型",
				area:['30%','35%'],
				offset:'200px',
				type:1,
				content:$('#addTypeDiv')
			});
			form.on('submit(addType)',function(obj){
				myutil.saveAjax({
					url:'/basedata/add',
					data:obj.field,
				},function(){
					getDataType();			
					layer.close(addType);
				});
			})
		});
		
		table.on('toolbar(baseDataTable)',function(obj){ //函数参数说明：(type,parent,thisTable)作用类型、父类、获取哪个表格的选中数据
			switch(obj.event){
			case 'add': addEidt('add',parent,'baseDataTable'); 	break;
			case 'edit': addEidt('edit',parent,'baseDataTable'); break;
			case 'delete': deletes('baseDataTable');	break;
			case 'more': more('baseDataTable'); 		break;
			}
		});
		
		function more(thisTable){					//查看子数据
			var choosedData=layui.table.checkStatus(thisTable).data;
			choosedData.length>1 && myutil.emsg("不能同时查看多条数据");
			choosedData.length==0 && myutil.emsg("至少选中一条数据进行查看");
			if(choosedData.length!=1) return;
			var parent={id:choosedData[0].id,remark:'',type:''};
			layer.open({
				title:'子数据',
				type:1,
				offset:'30px',
				area:['90%','90%'],
				content:'<div class="table_th_search" lay-filter="childrenTable" id="childrenTable"></div>' ,
			})
			renderTable('#childrenTable');
			table.reload('childrenTable',{ url:"${ctx}/basedata/children?id="+parent.id })
			table.on('toolbar(childrenTable)',function(obj){ 
				switch(obj.event){
				case 'add': addEidt('add',parent,'childrenTable');   break;
				case 'edit': addEidt('edit',parent,'childrenTable'); break;
				case 'delete':deletes('childrenTable'); 			break;
				case 'more':  myutil.emsg("该数据没有子数据"); break;
				}
			});
			
		}
		function addEidt(type,parent,thisTable){ //新增、修改数据。type区分新增或修改，parent作用于新增时的父id,type,remark，thisTable监听表格（parentId,thisTable复用函数而加入的参数）
			var choosedData=layui.table.checkStatus(thisTable).data;
			var data={id:'',name:'',ord:'',parentId:parent.id,remark:parent.remark,type:parent.type,flag:0};
			var title="新增数据";
			var html="";
			var tpl=addEditTpl.innerHTML;
			if(type=='edit'){
				choosedData.length>1 && myutil.emsg("不能同时编辑多条数据");
				choosedData.length<1 && myutil.emsg("至少选中一条数据进行编辑");
				if(choosedData!=1)
					return;
				data=choosedData[0];
			}
			laytpl(tpl).render(data,function(h){ html=h; })
			var open=layer.open({
				title:title,
				area:['30%','60%'],
				type:1,
				offset:'200px',
				content:html,
			})
			form.render();
			form.on('submit(addData)',function(obj){
				myutil.saveAjax({
					url:'/basedata/add',
					data:obj.field,
				},function(){
					getDataType();			
					layer.close(open);
					table.reload(thisTable);
				});
			});
		}
		
		function deletes(thisTable){
			var choosedData=layui.table.checkStatus(thisTable).data;
			if(choosedData.length<1){
				myutil.emsg("请至少选中一条数据删除");
				return;
			}
			layer.confirm("是否确认删除"+choosedData.length+"条数据",{offset:'200px'},function(){
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
					myutil.emsg("删除第"+i+"条数据时发生异常");
				}else{
					myutil.smsg("成功删除"+choosedData.length+"条数据");
					table.reload(thisTable);
				}
				
			})
		}
		
		function renderTable(elem){									//表格渲染，要渲染的表格元素，以及高度设置，不设置的话不会出现滚动条（与子数据列表复用函数）
			table.render({
				elem : elem,
				toolbar : "#baseDataToolBar",
				parseData : function(ret) { return { code : ret.code, msg : ret.message, data : ret.data, } },
				cols:[[
				       {type: 'checkbox',align : 'center',fixed: 'left'},
						{field : "id",title : "数据id",sort : true,align : 'center'},
						{field : "name",title : "名称",align : 'center'},
						{field : "ord",title : "排序",align : 'center',sort : true},
						{field : "parentId",title : "父id",align : 'center'},
						{field : "remark",title : "remark",align : 'center'},
						{field : "flag",title : "是否可用",align : 'center',sort : true},
						{field : "type",title : "类型",align : 'center'}
				      ]],
				 done:function(){
					 choosed=[];
					 childChoosed=[];
				 } 
			});
		}
		function getDataType(){
			var html = myutil.getSelectHtml({ 
				url:'/basedata/types',
				filter: 'selectId',
				name: 'baseDataType',
			});
			$('#tdSelect').html(html);
			form.render();
		}
	}//end function
)//end defind
</script>
</html>