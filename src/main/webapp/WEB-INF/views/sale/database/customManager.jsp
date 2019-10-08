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
				<td><input type="text" name="name" class="layui-input" placeholder="客户名称"></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" lay-submit lay-filter="search" id="searchBtn" class="layui-btn">搜索</button></td>
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
			<label class="layui-form-label">客户名称</label>
			<div class="layui-input-block">
				<input class="layui-input" name="name" value="{{d.name}}" lay-verify="required">
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
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil : 'layui/myModules/myutil',
}).define(
	['tablePlug','myutil'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		//myutil.config.msgOffset = '150px';
		myutil.clickTr();		
		myutil.keyDownEntry(function(){   //监听回车事件
			$('#searchBtn').click();
		})
		
		var allUser=[],
			currUser={id:''};     
		
		getAllUser();
		getCurrUser();
		form.render();
		
		table.render({
			elem:'#customTable',
			url:'${ctx}/ledger/customerPage',
			toolbar:'#customTableToolbar',
			page:true,
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'名称',   field:'name',		width:'15%',},
			       {align:'center', title:'手机',   field:'phone',		width:'10%',},
			       {align:'center', title:'业务员',  field:'user',		width:'10%', templet:'<span>{{ d.user?d.user.userName:"无所属业务员" }}</span>'},
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
			var data={id:'',name:'',provincesId:'',cityId:'',countyId:'',address:'',phone:''},
			title='新增客户',
			html='',
			userId=currUser.id,		//当前业务员id
			btnText='清空信息',
			provinceId='110000',cityId='110100',countyId=0,		//默认北京省、北京市
			choosed=layui.table.checkStatus('customTable').data,
			tpl=addEditTpl.innerHTML;
			if(type=='edit'){
				if(choosed.length>1)
					return layer.msg('无法同时编辑多条信息',{icon:2});
				if(choosed.length<1)
					return layer.msg("请选择客户编辑",{icon:2});
				title="修改客户信息";
				btnText='恢复初始值',
				data=choosed[0];
				if(data.provinces!=null)
					provinceId=data.provinces.id;
				if(data.city!=null)
					cityId=data.city.id;
				if(data.county!=null)
					countyId=data.county.id;
				userId = data.user.id;
			}
			laytpl(tpl).render(data,function(h){ html=h; })
			var addEditWin=layer.open({
				type:1,
				title:title,
				area:['40%','50%'],
				content:html
			})
			getUserSelect(userId,'userIdSelect');
			$('#resetCustom').html(btnText);
			getdataOfSelect(0,'province',provinceId);
			getdataOfSelect(provinceId,'city',cityId);
			getdataOfSelect(cityId,'county',countyId);
			
			$('#resetCustom').on('click',function(){			//重置按钮时,恢复地址下拉框的默认值
				getdataOfSelect(0,'province',provinceId);
				getdataOfSelect(provinceId,'city',cityId);
				getdataOfSelect(cityId,'county',countyId);
				getUserSelect(currUser.id,'userIdSelect');
			})
			
			form.render();
			form.on('submit(sure)',function(obj){
				myutil.saveAjax({
					url:'/ledger/addCustomer',
					data:obj.field,
					success: function(){
						table.reload('customTable');
						layer.close(addEditWin);
					},
				});
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
			if(choosed.length<1)
				return myutil.emsg("请选择删除数据");
			layer.confirm('是否确认删除？',function(){
				var ids='';
				for(var i=0;i<choosed.length;i++){
					ids+=(choosed[i].id+",");
				}
				myutil.deleteAjax({
					url:"/ledger/deleteCustomer",
					ids: ids,
					success: function(){
						table.reload('customTable');
					}
				})
			})
		}
		function getdataOfSelect(parentId,select,value){			//根据父类id已经elem渲染相关的elem
			var html = myutil.getSelectHtml({
				url:'/regionAddress/queryProvince?parentId='+parentId,
				selectOption: value,
				title: 'regionName',
				value: 'regionCode',
			})
			$('#'+select).html(html);
			form.render();
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
			myutil.getData({
				url:'${ctx}/system/user/pages?size=999',
				done: function(data){
					for(var i=0;i<data.length;i++)
						allUser.push({
							id:			data[i].id,
							userName:	data[i].userName
						})
				}
			})
		}
		function getCurrUser(){
			myutil.getData({
				url:'${ctx}/getCurrentUser',
				done: function(data){  currUser = data; }
			})
		}
	}//end define function
)//endedefine
</script>


</html>