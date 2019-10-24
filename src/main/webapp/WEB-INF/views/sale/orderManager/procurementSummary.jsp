<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>采购汇总</title>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>产品名:</td>
				<td><input type="text" name="productName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>产品编号:</td>
				<td><input type="text" name="productNumber" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" id="adminBtn" style="display:none;">角色:--</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		var allUser = myutil.getDataSync({url: '${ctx}/system/user/findUserList?orgNameIds=51'});
		allUser.unshift({ id:'',userName:'请选择' });
		var currentUser = myutil.getDataSync({url: '${ctx}/getCurrentUser'});
		var canUp = true; //currentUser.orgNameId==51?true:false;
		if(currentUser.isAdmin){	//增加admin调试模式
			canUp = layui.data('theTable').canUp;
			$('#adminBtn').html(canUp?'角色:可编辑':'角色:不可编辑');
			$('#adminBtn').show();
			$('#adminBtn').click(function(){
				layui.data('theTable',{key:'canUp', value:!canUp});
				if(canUp)
					$('#adminBtn').html('角色:不可编辑');
				else
					$('#adminBtn').html('角色:可编辑');
				canUp = !canUp;
			})
		}
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/ledger/getOrderProcurement',
			size:'lg',
			autoUpdate:{
				saveUrl:'/ledger/updateOrderProcurement',
				field:{ userStorage_id:'userStorageId', },
			},
			curd:{
				btn:[],
				otherBtn:function(obj){
					if(obj.event=='audit'){
						myutil.deleTableIds({
							url:'/ledger/auditOrderProcurement',
							table:'tableData',
							text:'请选择信息|是否确认审核？',
						})
					}
				}
			},
			ifNull:'',
			toolbar: canUp?'<span lay-event="audit" class="layui-btn layui-btn-sm">审核入科</span>':'',
			cols:[[
					{ type:'checkbox' },
					{ title:'下单日期', field:'placeOrderTime', },
					{ title:'采购编号', field:'orderProcurementNumber', },
					{ title:'采购数量', field:'placeOrderNumber', },
					{ title:'预计价格', field:'price', },
					{ title:'订购人', field:'user_userName', },
					{ title:'供应商', field:'customer_name', },
					{ title:'预计到货', field:'expectArrivalTime',},
					{ title:'到货日期', field:'arrivalTime', edit:canUp, type:'dateTime', },
					{ title:'到货数量', field:'arrivalNumber', edit:canUp,},
					{ title:'入库人', field:'userStorage_id', type:'select', select:{ data:allUser, name:'userName',isDisabled:!canUp,unsearch:!canUp, }},
					{ title:'是否入库',field:'arrival',transData:{data:['否','是'],}}
			       ]]
		})
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
	}//end define function
)//endedefine
</script>

</html>