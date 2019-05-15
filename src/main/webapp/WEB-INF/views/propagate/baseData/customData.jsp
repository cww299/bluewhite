<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户资料</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
#provinceCityCounty .layui-form-select .layui-input {		/* 设置省份下拉框的宽度 */
    width: 150px;
}
</style>
</head>
<body>


<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td><select name="grade" lay-search>
						<option value="">客户等级</option>
						<option value="0">一级</option>
						<option value="1">二级</option>
						<option value="2">三级</option></select></td>
				<td><select name="type" lay-search>
						<option value="">客户类型</option>
						<option value="0">电商</option>
						<option value="1">商超</option>
						<option value="2">线下</option></select></td>
				<td><button type="button" lay-submit lay-filter="search" class="layui-btn layui-btn-sm">搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="customTable" lay-filter="customTable"></table>
	</div>
</div>


</body>


<!-- 新增、修改用户模板 -->
<script type="text/html" id="addEditTpl">
	<div class="layui-form" style="padding:10px;">
		<input type="hidden" name="id" value="{{d.id}}">
		<div class="layui-item">
			<label class="layui-form-label">昵称</label>
			<div class="layui-input-block">
				<input class="layui-input" name="name" value="{{d.name}}">
			</div>
		</div>
		<div class="layui-item">
			<label class="layui-form-label">真实姓名</label>
			<div class="layui-input-block">
				<input class="layui-input" name="buyerName" value="{{d.buyerName}}">
			</div>
		</div>
		<div class="layui-item">
			<label class="layui-form-label">等级</label>
			<div class="layui-input-block">
				<select name="grade">
					<option value="0" {{ d.grade==0?'selected':'' }}>一级</option>
					<option value="1" {{ d.grade==1?'selected':'' }}>二级</option>
					<option value="2" {{ d.grade==2?'selected':'' }}>三级</option>
				</select>
			</div>
		</div>
		<div class="layui-item">
			<label class="layui-form-label">类型</label>
			<div class="layui-input-block">
				<select name="type">
					<option value="0" {{ d.type==0?'selected':'' }}>电商</option>
					<option value="1" {{ d.type==1?'selected':'' }}>商超</option>
					<option value="2" {{ d.type==2?'selected':'' }}>线下</option>
				</select>
			</div>
		</div>
		<div class="layui-item">
			<label class="layui-form-label">省份</label>
			<div class="layui-input-block" id="provinceCityCounty">
				<div class="layui-input-inline"><select lay-search id="province" name="provincesId"></select></div>
				<div class="layui-input-inline"><select lay-search id="city" name="cityId"></select></div>
				<div class="layui-input-inline"><select lay-search id="county" name="countyId"></select></div>
			</div>
		</div>
		<div class="layui-item">
			<label class="layui-form-label">详细地址</label>
			<div class="layui-input-block">
				<input class="layui-input" name="address" value="{{d.address}}">
			</div>
		</div>
		<div class="layui-item">
			<label class="layui-form-label">手机</label>
			<div class="layui-input-block">
				<input class="layui-input" name="phone" value="{{d.phone}}">
			</div>
		</div>
		<div class="layui-item">
			<label class="layui-form-label">账号</label>
			<div class="layui-input-block">
				<input class="layui-input" name="account" value="{{d.account}}">
			</div>
		</div>
		<div class="layui-item">
			<label class="layui-form-label">邮编</label>
			<div class="layui-input-block">
				<input class="layui-input" name="zipCode" value="{{d.zipCode}}">
			</div>
		</div>
		<p style="text-align:center;"><button class="layui-btn layui-btn-sm" lay-submit lay-filter="sure">确定</button></p>	
	</div>
</script>

<!-- 表格工具栏模板 -->
<script type="text/html" id="customTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改</span>
	<span lay-event="refresh"  class="layui-btn layui-btn-sm" >刷新</span>
</div>
</script>


<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).define(
	['tablePlug'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug;
		
		form.render();
		
		table.render({
			elem:'#customTable',
			url:'${ctx}/inventory/onlineCustomerPage',
			toolbar:'#customTableToolbar',
			loading:true,
			page:true,
			request:{
				pageName:'page',
				limitName:'size'
			},
			parseData:function(ret){
				return {
					data:ret.data.rows,
					count:ret.data.total,
					msg:ret.message,
					code:ret.code
				}
			},
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'名称',   field:'name',		width:'15%',},
			       {align:'center', title:'姓名',   field:'buyerName',	width:'10%',},
			       {align:'center', title:'手机',   field:'phone',		width:'10%',},
			       {align:'center', title:'等级',   field:'grade',		width:'15%',},
			       {align:'center', title:'所在地', field:'address'},
			       ]]
		})
		
		table.on('toolbar(customTable)',function(obj){
			switch(obj.event){
			case 'add':		addEdit('add');		break;
			case 'update':	addEdit('edit'); 	break;
			case 'delete':	deletes();			break;
			case 'refresh':	refresh();			break;
			}
		})
		
		function addEdit(type){
			var data={
					id:'',name:'',buyerName:'',grade:'',type:'',address:'',phone:'',account:'',zipCode:''
			},
			title='新增客户',
			html='',
			provinceId=0,cityId=0,countyId=0,cityParentId='110000',countyParentId='110100',
			choosed=layui.table.checkStatus('customTable').data,
			tpl=addEditTpl.innerHTML;
			if(type=='edit'){
				if(choosed.length>1){
					layer.msg('无法同时编辑多条信息',{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg("请选择客户编辑",{icon:2});
					return;
				}
				title="修改客户信息";
				data=choosed[0];
				if(data.provinces!=null)
					provinceId=data.provinces.id;
				if(data.city!=null){
					cityId=data.city.id;
					cityParentId=data.city.parentId;
				}
				if(data.county!=null){
					countyParentId=data.county.parentId;
					countyId=data.county.id;
				}
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			var addEditWin=layer.open({
				type:1,
				title:title,
				area:['40%','80%'],
				content:html
			})
			getdataOfSelect(0,'province',provinceId);
			getdataOfSelect(cityParentId,'city',cityId);
			getdataOfSelect(countyParentId,'county',countyId);
			
			form.render();
			form.on('submit(sure)',function(obj){
				var load = layer.load(1);
				$.ajax({
					url:"${ctx}/inventory/addOnlineCustomer",
					type:"post",
					data:obj.field,
					success:function(result){
						if(0==result.code){
							table.reload('customTable');
							layer.msg(result.message,{icon:1});
							layer.close(addEditWin);
						}else{
							layer.msg(result.message,{icon:2});
						}
						layer.close(load);
					},
					error:function(){
						layer.msg("服务器异常",{icon:2});
						layer.close(load);
					}
				})
			}) 
			
			
		 	form.on('select', function(data){					//监听地址下拉框的选择
				switch(data.elem.id){
				case 'province':getdataOfSelect(data.value,'city',0);
								getdataOfSelect($('#city').val(),'county',0);break;
				case 'city':	getdataOfSelect(data.value,'county',0);break;
				}
			});  
		}
		function deletes(){
			layer.msg('删除');
		}
		function refresh(){
			table.reload('customTable');
			layer.msg('刷新成功',{icon:1});
		}
		
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
		
		function getdataOfSelect(parentId,select,value){			//根据父类id已经elem渲染相关的elem
			$.ajax({
				url:"${ctx}/regionAddress/queryProvince",
				data:{parentId:parentId},
				async:false,
				success:function(result){
					if(0==result.code){
						var html='';
						var data=result.data;	
						for(var i=0;i<data.length;i++){
							var isSelected='';
							if(value==data[i].id){
								isSelected='selected';
							}
							html+='<option value="'+data[i].id+'" '+isSelected+'>'+data[i].regionName+'</option>';
						}
						$('#'+select).html(html);
						form.render();
					}
					else{
						layer.msg(result.message,{icon:2});
					}
				}
			});
		}
		
	}//end define function
)//endedefine
</script>


</html>