<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
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
		<table class="layui-form" id="warningTable" lay-filter="warningTable"></table>
	</div>
</div>

</body>

<!-- 表格工具栏模板 -->
<script type="text/html" id="warningTableToolbar">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增预警</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除预警</span>
	<span class="layui-badge" >提示：双击进行编辑</span>
</div>
</script>
</body>

<!-- 新增、修改模板 -->
<script type="text/html" id="addEditTpl">
<div class="layui-form" style="padding:10px;">
	<input type="hidden" name="id" value="{{d.id}}">
	<div class="layui-item">
		<label class="layui-form-label">预警仓库</label>
		<div class="layui-input-block">
			<select id="addEditWarehouse" name='warehouseId' ><option value=''>获取数据中...</option></select>
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">预警类型</label>
		<div class="layui-input-block">
			<select id="addEditType" lay-filter='addEditType' name='type'><option value=''>获取数据中...</option></select>
		</div>
	</div>
	<div class="layui-item" id='addEditTime'>
		<label class="layui-form-label">预警时间</label>
		<div class="layui-input-block">
			<input class="layui-input" name="time" value="{{d.time}}" lay-verify='number'>
			<span class="layui-badge" >提示：时间单位/天数</span>
		</div>
	</div>
	<p style="text-align:center;"><button class="layui-btn layui-btn-sm" lay-submit lay-filter="sure">确定</button></p>
</div>
</script>
<script type="text/html" id='typeTpl'>
{{# var color='';
	var text='';
	switch(d.type){
		case 1: text='库存下限预警'; color=''; break;
		case 2: text='库存上限预警'; color='green'; break;
		case 3: text='库存时间过长预警'; color='blue'; break;
	}
}}
<span style='margin-top: 10px;' class='layui-badge layui-bg-{{ color}}'>{{ text }}</span>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).define(
	['tablePlug','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laytpl = layui.laytpl
		, laydate = layui.laydate
		, tablePlug = layui.tablePlug;
		
		var allInventory=[];		//所有仓库
		
		getAllInventory();
		$('#warehouseIdSelect').append(getWarehouseSelectHtml(0));
		form.render();
		
		table.render({
			elem:'#warningTable',
			url:'${ctx}/inventory/getWarning',
			toolbar:'#warningTableToolbar',
			loading:true,
			size:'lg',
			parseData:function(ret){
				return { data:ret.data, 
						 msg:ret.message, 
						 code:ret.code } }, 
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'预警仓库', field:'',	templet:'<span>{{d.warehouse.name}}</span>'},
			       {align:'center', title:'预警类型', field:'type', templet:'#typeTpl'},
			       {align:'center', title:'预警时间/天', field:'time'},
			       ]]
		})
		
		table.on('toolbar(warningTable)',function(obj){
			switch(obj.event){
			case 'add':		addEdit('add');		break;
			case 'delete':	deletes();			break;
			}
		})
		
		table.on('rowDouble()',function(obj){
			addEdit('edit',obj.data);
		})
		
		function addEdit(type,d){ 
			var data={ id:'',number:'0',time:'0',warehouseId:'',type:'1'},
			tpl=addEditTpl.innerHTML,
			title='新增预警',
			html='';
			if(type=='edit'){
				data=d;
				title='修改预警';
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			var addEditWin=layer.open({
				type:1,
				title:title,
				area:['40%','35%'],
				content:html
			})
			//初始化页面设置，下拉框的填充。显示隐藏的切换
			$('#addEditWarehouse').html(getWarehouseSelectHtml(data.warehouseId));
			$('#addEditType').html(getTypeSelectHtml(data.type));
			form.render();
			form.on('submit(sure)',function(obj){
				var msg='';
				if(obj.field.time%1!==0)
					msg='预警时间只能为整数！';
				else if(parseInt(obj.field.time)<0)
					msg='预警时间不能为负数！';
				if(msg!=''){
					layer.msg(msg,{icon:2});
					return;
				}
				obj.field.time=parseInt(obj.field.time);
				var load=layer.load(1);
				$.ajax({
					url:'${ctx}/inventory/addWarning',
					type:'post',
					data:obj.field,
					success:function(result){
						if(0==result.code){
							table.reload('warningTable');
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
		}
		function deletes(){
			var choosed=layui.table.checkStatus('warningTable').data;
			if(choosed.length<1){
				layer.msg('请选择预警',{icon:2});
				return;
			}
			layer.confirm("是否确认删除？",function(){
				var ids='';
				for(var i=0;i<choosed.length;i++){
					ids+=(choosed[i].id+",");
				}
				var load=layer.load(1);
				$.ajax({
					url:"${ctx}/inventory/deleteWarning",
					data:{ids:ids},
					success:function(result){
						if(0==result.code){
							layer.msg(result.message,{icon:1});
							table.reload('warningTable');
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
		}
		
		function getWarehouseSelectHtml(warehouse){
			var html='';
			if(allInventory.length==0){
				html='<option value="">无仓库</option>';
			}else{
				for(var i=0;i<allInventory.length;i++){
					var t=allInventory[i],
					    disable = t.flag==1?'':'disabled',
						selected = warehouse==t.id?'selected':'';
					html+=('<option value="'+t.id+'" '+disable+' '+selected+'>'+t.name+'</option>');
				}
			}
			return html;
		}
		function getTypeSelectHtml(type){
			var html='';
			html+='<option value="1" '+ (type==1?"selected":"") +'>库存下限预警</option>';
			html+='<option value="2" '+ (type==2?"selected":"") +'>库存上限预警</option>'
			html+='<option value="3" '+ (type==3?"selected":"") +'>库存时间过长预警</option>'
			return html;
		}
		function getAllInventory(){
			$.ajax({
				url:'${ctx}/basedata/list?type=inventory',
				async:false,
				success:function(r){
					if(0==r.code){
						allInventory=r.data;
					}
				}
			})
		}
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