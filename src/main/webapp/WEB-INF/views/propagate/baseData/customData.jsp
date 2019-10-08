<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
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
				<td><input type="text" name="buyerName" class="layui-input" placeholder="客户名称"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><select name="grade" lay-search>
						<option value="">客户等级</option>
						<option value="0">一级</option>
						<option value="1">二级</option>
						<option value="2">三级</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><select name="type" lay-search>
						<option value="">客户类型</option>
						<option value="0">电商</option>
						<option value="1">商超</option>
						<option value="2">线下</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" lay-submit lay-filter="search" class="layui-btn layui-btn-sm">搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="customTable" lay-filter="customTable"></table>
	</div>
</div>


</body>


<!-- 新增、修改用户模板 -->
<script type="text/html" id="addEditTpl">
	<form class="layui-form" style="padding:10px;">
		<input type="hidden" name="id" value="{{d.id}}">
		<div class="layui-item">
			<label class="layui-form-label">昵称</label>
			<div class="layui-input-block">
				<input class="layui-input" name="name" value="{{d.name}}" lay-verify="required">
			</div>
		</div>
		<div class="layui-item">
			<label class="layui-form-label">真实姓名</label>
			<div class="layui-input-block">
				<input class="layui-input" name="buyerName" value="{{d.buyerName}}" >
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
			<label class="layui-form-label">业务员</label>
			<div class="layui-input-block">
				<select name="userId" id='userIdSelect' lay-search><option>获取数据中...</option></select>
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
				<input class="layui-input" name="phone" value="{{d.phone}}" lay-verify="required">
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
		<p style="text-align:center;margin-top:20px;">
									 <button class="layui-btn layui-btn-sm" type="reset" id="resetCustom">清空</button>
									 <button class="layui-btn layui-btn-sm" type="button" lay-submit lay-filter="sure">确定</button></p>	
	</form>
</script>

<!-- 表格工具栏模板 -->
<script type="text/html" id="customTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增客户</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除客户</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改客户信息</span>
</div>
</script>
<!-- 客户等级转换模板 -->
<script type="text/html" id="gradeTpl">
{{# if(d.grade==0){ }}
	<span class="layui-badge layui-bg-green">一级</span>
{{# }else if(d.grade==1){ }}
	<span class="layui-badge layui-bg-orange">二级</span>
{{# }else if(d.grade==2){ }}
	<span class="layui-badge ">三级</span>
{{# } }}
</script>
<!-- 客户类型转换模板 -->
<script type="text/html" id="typeTpl">
{{# if(d.type==0){ }}
	<span class="layui-badge layui-bg-orange">电商</span>
{{# }else if(d.type==1){ }}
	<span class="layui-badge layui-bg-cyan">商超</span>
{{# }else if(d.type==2){ }}
	<span class="layui-badge layui-bg-blue">线下</span>
{{# } }}

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
		var allUser=[],
			currUser={id:''};     
		
		getAllUser();
		getCurrUser();
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
			       {align:'center', title:'业务员',  field:'user',		width:'10%', templet:'<span>{{ d.user?d.user.userName:"" }}</span>'},
			       {align:'center', title:'等级',   field:'grade',		width:'10%',	templet:"#gradeTpl",},
			       {align:'center', title:'类型',   field:'type',		width:'10%',	templet:"#typeTpl",},
			       {align:'center', title:'所在地', field:'address'},
			       ]]
		})
		
		form.on('submit(search)',function(obj){
			table.reload('customTable',{
				where:obj.field,
				page : { curr : 1}
			});
		})
		
		table.on('toolbar(customTable)',function(obj){
			switch(obj.event){
			case 'add':		addEdit('add');		break;
			case 'update':	addEdit('edit'); 	break;
			case 'delete':	deletes();			break;
			}
		})
		
		function addEdit(type){
			var data={
					id:'',name:'',buyerName:'',grade:'',type:'',address:'',phone:'',account:'',zipCode:''
			},
			title='新增客户',
			html='',
			userId=currUser.id,		//当前业务员id
			btnText='清空信息',
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
				btnText='恢复初始值',
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
				userId = data.userId;
			}
			laytpl(tpl).render(data,function(h){ html=h; })
			var addEditWin=layer.open({
				type:1,
				title:title,
				area:['40%','60%'],
				content:html
			})
			getUserSelect(userId,'userIdSelect');
			$('#resetCustom').html(btnText);
			getdataOfSelect(0,'province',provinceId);
			getdataOfSelect(cityParentId,'city',cityId);
			getdataOfSelect(countyParentId,'county',countyId);
			
			$('#resetCustom').on('click',function(){			//重置按钮时,恢复地址下拉框的默认值
				getdataOfSelect(0,'province',provinceId);
				getdataOfSelect(cityParentId,'city',cityId);
				getdataOfSelect(countyParentId,'county',countyId);
				getUserSelect(currUser.id,'userIdSelect');
			})
			
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
			var choosed=layui.table.checkStatus('customTable').data;
			if(choosed.length<1){
				layer.msg('请选择客户',{icon:2});
				return;
			}
			layer.confirm('是否确认删除？',function(){
				var ids='';
				for(var i=0;i<choosed.length;i++){
					ids+=(choosed[i].id+",");
				}
				var load=layer.load(1);
				$.ajax({
					url:"${ctx}/inventory/deleteOnlineCustomer",
					data:{ids:ids},
					success:function(result){
						if(0==result.code){
							layer.msg(result.message,{icon:1});
							table.reload('customTable');
						}else{
							layer.msg(result.message,{icon:2});
						}
						layer.close(load);
					},
					error:function(){
						layer.msg("ajax错误",{icon:2});
						layer.close(load);
					}
				})
			})
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
		function getUserSelect(id,select){ 
			var html='';
			if(id==null || id=='')
				html+='<option value="">无所属业务员</option>'
			for(var i=0;i<allUser.length;i++){
				var selected=( id==allUser[i].id?'selected':'' );
				html+='<option value="'+allUser[i].id+'" '+selected+'>'+allUser[i].userName+'</option>';
			}
			$('#'+select).html(html);
			form.render();
		}
		function getAllUser(){
			$.ajax({
				url:'${ctx}/system/user/pages?size=999',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allUser.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].userName
							})
					}
				}
			})
		}
		function getCurrUser(){
			$.ajax({
				url:'${ctx}/getCurrentUser',
				success:function(r){
					if(0==r.code)
						currUser = r.data;
				}
			})
		}
		
	}//end define function
)//endedefine
</script>


</html>