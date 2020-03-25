<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
	.layui-form-label{
		width: 120px !important;
	}
	</style>
<title>员工档案</title>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>员工姓名:</td>
				<td><select class="form-control" name="userId" lay-search="true" lay-filter="lay_selecte2" id="userIds"></select></td>
				<td>&nbsp;&nbsp;</td>	
		 		<td>
				<div class="layui-inline">
					<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">
						<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
					</button>
				</div>
				</td>
			</tr>
		</table>
		<table class="layui-form" id="recruitTable" lay-filter="recruitTable" ></table>
	</div>
</div>

<form action="" id="layuiadmin-form-admin3"
		style="padding: 20px 0px 0 50px; display:none;  text-align:">
		<div class="layui-form layui-form-pane" lay-filter="layuiadmin-form-admin6">
				
			<div class="layui-form-item">
		    <div class="layui-inline">
		      <label class="layui-form-label">员工姓名</label>
		      <div class="layui-input-inline">
		       <select class="form-control" name="userId" lay-search="true" lay-filter="lay_selecte2" id="userId"></select>
		      </div>
		    </div>
		    
		    <div class="layui-inline">
		      <label class="layui-form-label">照片数量</label>
		      <div class="layui-input-inline">
		        <input type="text" name="pic"  id="pic" autocomplete="off" class="layui-input">
		      </div>
		    </div>
			<div class="layui-inline">
		      <label class="layui-form-label">身份证数量</label>
		      <div class="layui-input-inline">
		        <input type="tel"  name="idCard"  id="idCard"   autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">员工档案</label>
		      <div class="layui-input-inline">
		        <input type="tel"  name="archives"  id="archives"  autocomplete="off" class="layui-input">
		      </div>
		    </div>
  			</div>
			
			<div class="layui-form-item">
		    <div class="layui-inline">
		      <label class="layui-form-label">银行卡数量</label>
		      <div class="layui-input-inline">
		        <input type="tel"  name="bankCard" id="bankCard"  autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">体检</label>
		      <div class="layui-input-inline">
		      <input type="tel"  name="physical" id="physical"  autocomplete="off" class="layui-input">
				</div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">资格证书</label>
		      <div class="layui-input-inline">
		        <input type="text" name="qualification" id="qualification"  autocomplete="off" class="layui-input">
		      </div>
		    </div>
			<div class="layui-inline">
		      <label class="layui-form-label">学历证书</label>
		      <div class="layui-input-inline">
		        <input type="tel"  name="formalSchooling"  id="formalSchooling"   autocomplete="off" class="layui-input">
		      </div>
		    </div>
  			</div>
			
			<div class="layui-form-item">
		    <div class="layui-inline">
		      <label class="layui-form-label">其他协议</label>
		      <div class="layui-input-inline">
				 <input type="text" name="agreement"  id="agreement"   autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">保密协议</label>
		      <div class="layui-input-inline">
				 <input type="text" name="secrecyAgreement"  id="secrecyAgreement"   autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">合同数量</label>
		      <div class="layui-input-inline">
		        <input type="text" name="contract"  id="contract"  autocomplete="off" class="layui-input">
		      </div>
		    </div>
			<div class="layui-inline">
		      <label class="layui-form-label">复工</label>
		      <div class="layui-input-inline">
		        <input type="text" name="remark"  id="remark"   autocomplete="off" class="layui-input">
		      </div>
		    </div>
  			</div>
			
			<div class="layui-form-item">
		    
  			</div>
		</div>
	</form>

</body>



<!-- 表格工具栏模板 -->

<script type="text/html" id="recruitToolbar">
<div class="layui-btn-container layui-inline">
	<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增</span>
	<span class="layui-btn layui-btn-sm" lay-event="updateData">编辑</span>
	<span class="layui-btn layui-btn-trans layui-btn-sm layui-bg-red"  lay-event="deleteSome">删除</span>
</div>
</script>
</body>

<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).define(
	['tablePlug','laydate','upload'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug
		,upload = layui.upload;
		
		laydate.render({
			elem: '#startTime',
			type: 'date',
			range: '~',
			
		});
		
		var htmls='<option value="">请选择</option>';
		$.ajax({
			url: '${ctx}/system/user/findUserList',
			type: "GET",
			async: false,
			success: function(result) {
				$(result.data).each(function(i, o) {
					htmls += '<option value=' +o.id+ '>' + o.userName + '</option>'
				})
				$("#userId").html(htmls);
				$("#userIds").html(htmls);
				form.render();
			},
			error: function() {
				layer.msg("操作失败！", {
					icon: 2
				});
				layer.close(index);
			}
		});
		
		var allPlatform = [];
	 	tablePlug.smartReload.enable(true);  
	 	table.render({
			elem:'#recruitTable',
			url:'${ctx}/system/user/userContractPages',
			toolbar:'#recruitToolbar',
			loading:true,
			page:true,
			size:'lg',
			smartReloadModel: true,    // 开启智能重载
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[{align: 'center',type: 'checkbox', },
			       {align:'center', title:'姓名',templet:function(d){return d.user==null ? "" :d.user.userName}},
			       {align:'center', title:'位置编号',field:'lotionNumber',edit: false,templet:function(d){return  d.user==null ? "" : d.user.lotionNumber}	},
			       {align:'center', title:'员工档案',   field:'archives',edit: false,},
			       {align:'center',title: "照片数量", field:"pic"},
			       {align:'center',field: "idCard",title: "身份证数量"},
			       {align:'center', title:'银行卡数量',field:'bankCard'},
			       {align:'center', title:'体检',field:'physical'}, 
			       {align:'center', title:'资格证书',field:'qualification'}, 
			       {align:'center', title:'学历证书',field:'formalSchooling'}, 
			       {align:'center', title:'其他协议',field:'agreement'},
			       {align:'center', title:'保密协议',field:'secrecyAgreement'},
			       {align:'center', title:'合同数量',field:'contract'},
			       {align:'center', title:'复工',field:'remark'},
			       ]],
		})
		
	
	 	table.on('toolbar(recruitTable)',function(obj){
			var config = obj.config;
			var btnElem = $(this);
			var tableId = config.id;
			switch(obj.event){
			case 'addTempData':
				var id="";
				tips(id);
				break;
				case 'updateData':
					var data=layui.table.checkStatus("recruitTable").data;
					if(data.length>1){
						layer.msg("无法同时编辑多条信息",{icon:2});
						return;
					}
					$("#userId").val(data[0].user.id);
					$("#agreement").val(data[0].agreement);
					$("#archives").val(data[0].archives);
					$("#bankCard").val(data[0].bankCard);
					$("#contract").val(data[0].contract);
					$("#formalSchooling").val(data[0].formalSchooling);
					$("#idCard").val(data[0].idCard);
					$("#physical").val(data[0].physical);
					$("#pic").val(data[0].pic);
					$("#qualification").val(data[0].qualification);
					$("#remark").val(data[0].remark);
					$("#secrecyAgreement").val(data[0].secrecyAgreement);
			 		form.render();
			 		id=data[0].id
					tips(id);
			          break;
			case 'deleteSome':
				var checkedIds = tablePlug.tableCheck.getChecked(tableId);
				var index;
				index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
				 $.ajax({
					url:"${ctx}/system/user/deleteUserContract",
					data:{ids:checkedIds},
					type:"GET",
					traditional: true,
					beforeSend:function(){
						index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							});
					},
					success:function(result){
						if(0==result.code){
						layer.msg(result.message, {icon: 1});
						layer.close(index);
						}else if(1500==result.code){
							layer.msg("该名人员无法删除 存在数据关联", {icon: 2});
							layer.close(index);
						}else{
							layer.msg(result.message, {icon: 2});
							layer.close(index);
						}
					},error:function(){
						layer.msg("操作失败！", {icon: 2});
						layer.close(index);
					}
				}); 
				}); 
				break;
			}
		})
		
		function tips(id){
	 		var	dicDiv=$("#layuiadmin-form-admin3");
			var index=layer.open({
				type:1,
				title:'档案',
				area:['83%','60%'],
				btn:['确认','取消'],
				content:dicDiv,
				id: 'LAY_layuipro9' ,
				btnAlign: 'c',
			    moveType: 1, //拖拽模式，0或者1
				success : function(layero, index) {
					// 将保存按钮改变成提交按钮
					layero.addClass('layui-form');
					layero.find('.layui-layer-btn0').attr({
						'lay-filter' : 'addRole2',
						'lay-submit' : ''
					})
		        },
				yes:function(){
					form.on('submit(addRole2)', function(data) {
						data.field.id=id;
						 $.ajax({
							url:"${ctx}/system/user/addUserContract",
							data:data.field,
							type:"POST",
							traditional: true,
							success:function(result){
								if(0==result.code){
									layer.msg("修改成功！", {icon: 1});
									table.reload('recruitTable')
									layer.close(index);
								}else{
									layer.msg(result.message, {icon: 2});
								}
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						}); 
			        	layui.form.render();
					})
				},end:function(){ 
		        	layui.form.render();
				  }
			})
	 	}
	 	
	 	form.on('submit(LAY-search)', function(data) {
	 		var field = data.field;
	 		table.reload('recruitTable', {
				where: field,
				page: {
					curr:1
                }
			});
	 	})
	 	$(document).on('click', '.layui-table-view tbody tr', function(event) {
						var elemTemp = $(this);
						var tableView = elemTemp.closest('.layui-table-view');
						var trIndex = elemTemp.data('index');
						tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
					})
	 	//待转正人员
	 	/* function openSpecialWin(){					//打开特急人员弹窗
			var specialWin=layer.open({
				title:'待转正人员 ',
				type:1,
				area:['50%','80%'],
				content:$('#specialWinDiv'),
				end:function(){
					$('#specialWinDiv').hide();		//在弹窗销毁后，隐藏弹窗内容DIV
				}
			})
			table.render({
				elem:'#specialTable',
				toolbar:'#specialTableToolbar',
				url:'${ctx}/system/user/getPositiveUser',
				page:false,
				loading:true,
				parseData:function(ret){
					return{ code:ret.code, data:ret.data, }
				},
				cols:[[
				       {align:'center',type:'checkbox'},
				       {align:'center',field:'userName',title:'姓名'},
				       {align:'center',field:'type',	title:'类型',	templet:'#typeTpl'},
				       {align:'center',field:'phone',	title:'手机',	edit:true,},
				       ]]		
			})
			table.on('edit(specialTable)',function(obj){
				var load=layer.load(1);
			 	$.ajax({
					url:'${ctx}/system/user/update',
					type:'post',
					data:{	id:obj.data.id,
						  	phone:obj.data.phone,
						  	positive:'true'},
					success:function(r){
						if(2==r.code){
							layer.msg(r.message,{icon:1});
						}else
							layer.msg(r.message,{icon:2});
						layer.close(load);
					},
					error:function(){
						layer.msg('ajax异常',{icon:2});
						layer.close(load);
					}
				}) 
			})
			table.on('toolbar(specialTable)',function(obj){	
				switch(obj.event){
				case 'becomeFull':becomeFull(); break; 
				}
			})
			function becomeFull(){
				var choosed = layui.table.checkStatus('specialTable').data;
				if(choosed.length<1){
					layer.msg('请选择人员',{icon:2});
					return;
				}
				if(choosed.length>1){
					layer.msg('不能同时转正多名人员',{icon:2});
					return;
				}
				if(choosed[0].phone==''){
					layer.msg('转正人员需要填写号码！',{icon:2});
					return;
				}
				layer.confirm('是否确认转正？',function(){
					var positiveUser='';
					for(var i=0;i<choosed.length;i++){
						positiveUser+=(choosed[i].id+',');
					}
					var load=layer.load(1);
					$.ajax({
						url:'${ctx}/system/user/positiveUser?positiveUser='+positiveUser,
						success:function(result){
							if(0==result.code){
								positiveNumber--;
								 $('#lookoverBecome').html('待转正人员 <span class="layui-badge">'+positiveNumber+'</span></li>')
								table.reload('specialTable');
								$('#openEditBtn').data('id',choosed[0].id);
								$('#openEditBtn').click();
								layer.close(load);
								layer.msg(result.message,{icon:1});
							}else{
								layer.msg(result.message,{icon:2});
							}
							layer.close(load);
						}
					})
				})
			}
		} */
		
	}//end define function
)//endedefine
</script>
</html>