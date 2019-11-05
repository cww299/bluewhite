<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>机械配件库存管理</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>物品名:</td>
				<td><input type="text" name="name" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>仓位:</td>
				<td><input type="text" name="location" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>时间:</td>
				<td><input type="text" name="" class="layui-input" id="searchTime"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
<!-- 入库隐藏弹窗 -->
<script type="text/html" id="inputWin">
	<div style="text-align:center;">
		<table class="layui-form" style="margin:30px auto;">
			<tr>
				<td>日期：</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="time" class="layui-input" id="addInputTime"></td>
			</tr>
			<tr>
				<td>数量：</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="number" lay-verify="number" class="layui-input" id="inputTime"></td>
			</tr>
			<tr>
				<td>备注</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="remark" class="layui-input">
					<input type="hidden" name="flag" value="1">
					<span lay-submit lay-filter="addInputBtn" id="addInputBtn" style="display:none;">1</span></td>
			</tr>
		</table>
	</div>
</script>
<!-- 出库隐藏弹窗 -->
<script type="text/html" id="outWin">
	<div style="text-align:center;">
		<table class="layui-form" style="margin:30px auto;">
			<tr>
				<td>日期：</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="time" class="layui-input" id="addOutTime"></td>
			</tr>
			<tr>
				<td>数量：</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="number" lay-verify="number" class="layui-input"></td>
			</tr>
			<tr>
				<td>部门：</td>
				<td>&nbsp;&nbsp;</td>
				<td><select  name="orgNameId" id="addOrg" lay-search></select></td>
			</tr>
			<tr>
				<td>领取人：</td>
				<td>&nbsp;&nbsp;</td>
				<td><select  name="userId" id="addUser" lay-search></select></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="remark" class="layui-input">
					<input type="hidden" name="flag" value="0">
					<span lay-submit lay-filter="addOutBtn" id="addOutBtn" style="display:none;">1</span></td>
			</tr>
		</table>
	</div>
</script>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form
		, laydate = layui.laydate
		, table = layui.table 
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		
		var orgNameSelectHtml = '<option value="">请选择</option>', userSelectHtml = '<option value="">请选择</option>';
		var unitData = myutil.getDataSync({ url:'${ctx}/basedata/list?type=officeUnit' });
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/personnel/getOfficeSupplies?type=2',
			curd:{
				addTemp:{
					type:2,
					createdAt:myutil.getSubDay(0,'yyyy-MM-dd')+' 00:00:00',
					location:'',name:'',price:'',inventoryNumber:'',libraryValue:'',
				},
				addTempAfter:function(trElem){
					var timeElem = $(trElem).find('td[data-field="createdAt"]').find('div')[0];
					laydate.render({
						elem: timeElem,
						done: function(val){
							var index = $(this.elem).closest('tr').data('index');
							table.cache['tableData'][index]['createdAt'] = val+' 00:00:00';
						}
					}) 
				}, 
			},
			size:'lg',
			totalRow:['libraryValue'],
			autoUpdate:{
				saveUrl:'/personnel/addOfficeSupplies',
				deleUrl:'/product/deleteOfficeSupplies',
				field:{unit_id:'unitId', },
			},
			/* colsWidth:[0,10,10,15,10,10,10,10,0], */
			cols:[[
					{ type: 'checkbox', align: 'center', fixed: 'left',},
					{ field: "createdAt", title: "时间", type:'date',},
					{ field: "location", title: "仓位", filter:true, edit: true, },
					{ field: "name", title: "物品名", edit: true, },
					{ field: "unit_id", title: "单位", type:'select', select:{data:unitData,}, },
					{ field: "price", title: "单价", edit: true, },
					{ field: "inventoryNumber", title: "库存数量", edit: false, },
					{ field: "libraryValue", title: "库值", edit: false, },
					{ field: "", title: "操作", edit: false, templet:getTpl(), },
			       ]],
			done:function(){
				$('.inputBtn').unbind().on('click',function(obj){
					var index = $(obj.target).closest('tr').data('index');
					var trData = table.cache['tableData'][index];
					var win = layer.open({
						type:1,
						area:['30%','40%'],
						btnAlign:'c',
						btn:['确定',"取消"],
						title: trData.name,
						content: $('#inputWin').html(),
						success:function(){
							laydate.render({ elem:'#addInputTime',value:myutil.getSubDay(0,'yyyy-MM-dd'), })
							form.on('submit(addInputBtn)',function(obj){
								obj.field.officeSuppliesId = trData.id;
								obj.field.time = obj.field.time+' 00:00:00';
								myutil.saveAjax({
									url:'/personnel/addInventoryDetail',
									data: obj.field,
									success:function(){
										layer.close(win);
										table.reload('tableData');
									}
								})
							})
							form.render();
						},
						yes:function(){
							$('#addInputBtn').click();
						}
					})
				})
				$('.outBtn').unbind().on('click',function(obj){
					var index = $(obj.target).closest('tr').data('index');
					var trData = table.cache['tableData'][index];
					var win = layer.open({
						type:1,
						area:['30%','50%'],
						btn:['确定',"取消"],
						btnAlign:'c',
						title: trData.name,
						content:$('#outWin').html(),
						success:function(){
							laydate.render({ elem:'#addOutTime', value:myutil.getSubDay(0,'yyyy-MM-dd'), })
							$('#addOrg').html(orgNameSelectHtml);
							$('#addUser').html(userSelectHtml);
							form.on('submit(addOutBtn)',function(obj){
								obj.field.officeSuppliesId = trData.id;
								obj.field.time = obj.field.time+' 00:00:00';
								myutil.saveAjax({
									url:'/personnel/addInventoryDetail',
									data: obj.field,
									success:function(){
										layer.close(win);
										table.reload('tableData');
									}
								})
							})
							form.render();
						},
						yes:function(){
							$('#addOutBtn').click();
						}
					})
				})
			}
		})
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
			})
		})
		
		laydate.render({ elem:'#searchTime', range:'~', })
		
		myutil.getData({
			url:'${ctx}/basedata/list?type=orgName',
			success:function(d){ 
				for(var i=0,len=d.length;i<len;i++){
					orgNameSelectHtml += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
			},
		})
		myutil.getData({
			url:'${ctx}/system/user/findUserList?foreigns=0&isAdmin=false',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					userSelectHtml += '<option value="'+d[i].id+'">'+d[i].userName+'</option>';
				}
			},
		})
		
		form.on('submit(search)',function(obj){
			var t = $('#searchTime').val();
			if(t!=''){
				t = t.split(' ~ ');
				t[0] = t[0].trim()+' 00:00:00';
				t[1] =  t[1].trim()+' 00:00:00';
			}else
				t = ['',''];
			obj.field.orderTimeBegin = t[0];
			obj.field.orderTimeEnd =  t[1];
			
			table.reload('tableData',{
				where:obj.field,
				page:{curr:1},
			})
		})
		
		function getTpl(){
			return function(d){
				if(d.id){
					return [
					        '<span class="layui-btn layui-btn-sm inputBtn">入库</span>',
					        '<span class="layui-btn layui-btn-sm outBtn">出库</span>',
					        ].join('');
				}else
					return '';
			}
		}
	}//end define function
)//endedefine
</script>

</html>