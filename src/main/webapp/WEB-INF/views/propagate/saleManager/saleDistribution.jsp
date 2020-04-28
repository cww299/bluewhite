<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>小程序订单</title>
	<style>
		.red{
			color: red;
		}
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td>注册时间：</td>
				<td><input id="searchTime" class="layui-input" name="dateAddBegin" autocomplete="off"></td>
				<td>手机号：</td>
				<td><input class="layui-input" name="mobile" value=""></td>
				<td>昵称：</td>
				<td><input class="layui-input" name="nickLike"></td>
				<td>是否分销：</td>
				<td style="width:120px;"><select name="isSeller">
					<option value="">是否分销</option>
					<option value="false">否</option>
					<option value="true" selected>是</option></select></td>
				<td><span class="layui-btn layui-btn-" lay-submit lay-filter="searchBtn">搜索</span></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
}).define(
	['mytable','laydate','form'],
	function(){
		var $ = layui.jquery
		,laydate = layui.laydate
		,table = layui.table
		,mytable = layui.mytable
		,form = layui.form
		,myutil = layui.myutil;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({
			elem:'#searchTime',range:'~'
		})
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/user/apiExtUser/list',
			where:{
				isSeller:true,
			},
			request:{ pageName: 'page' ,limitName: 'pageSize'},
			parseData: parseData(),
			limit:15,
			limits:[10,15,20,50,100],
			cols:[[
				{ type:'checkbox', },
				{ title:'注册日期', field:'dateAdd', },
				{ title:'昵称', field:'nick', },
				{ title:'手机号', field:'mobile', templet:function(d){ return d.mobile || "---";}},
				{ title:'最后登录日期', field:'dateLogin', },
				{ title:'是否分销商', field:'isSeller', templet:function(d){
					var text = "否",color="red";
					if(d.isSeller){
						text = "是";color = "green";
					}
					return "<span class='layui-badge layui-bg-"+color+"'>"+text+"</span>";
				}},
				{ title:'是否实名', field:'isIdcardCheck', templet:function(d){
					var text = "否",color="red";
					if(d.isIdcardCheck){
						text = "是";color = "green";
					}
					return "<span class='layui-badge layui-bg-"+color+"'>"+text+"</span>";
				}},
				{ title:'操作', field:'', templet: getBtn(), },
			]]
		})
		function getBtn(){
			return function(d){
				return [
					'<span class="layui-btn layui-btn-xs layui-btn-" lay-event="lookover">团队明细</span>',
					'<span class="layui-btn layui-btn-xs layui-btn-normal" lay-event="lookoverCom">佣金明细</span>',
				].join(' ');
			}
		}
		table.on('tool(tableData)',function(obj){
			if(obj.event=="lookover"){
				openLookoverWin(obj.data);
			}else if(obj.event=="lookoverCom"){
				openLookoverWinCommission(obj.data);
			}
		})
		
		function openLookoverWinCommission(data){
			var sumData = { bill:0, money:0 };
			getTableData();
			function getTableData(field){
				field = field || {};
				field = $.extend({},{ uidm:data.id},field)
				myutil.getData({
					url: myutil.config.ctx+'/user/saleDistributionCommisionLog/list',
					data:field,
					success:function(d){
						sumData.bill = 0;
						sumData.money = 0;
						var m = d.userMapS || d.userMaps;
						var r = d.result;
						for(var i in r){
							r[i] = $.extend({},m[r[i].uids],r[i]);
							sumData.bill += r[i].bili;
							sumData.money += r[i].money;
						}
						table.reload('lookTable',{ data: r, page:{ curr:1, }});
					},
					error:function(r){
						sumData.bill = 0;
						sumData.money = 0;
						table.reload('lookTable',{ data: [], page:{ curr:1, }});
					}
				})
			}
			layer.open({
				type:1,
				title: '佣金明细',
				shadeClose:true,
				area:['80%','80%'],
				content:[
					'<div style="padding:10px;">',
						'<table class="layui-form searchTable">',
							'<tr>',
								'<td>日期：</td>',
								'<td><input id="searchLook" class="layui-input" name="dateAddBegin" autocomplete="off"></td>',
								'<td>分销级别：</td>',
								'<td style="width:120px;"><select name="level"><option value=""></option>',
										'<option value="1">一级</option>',
										'<option value="2">二级</option>',
										'<option value="3">三级</option></select>',
								'<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="searchLookBtn">搜索</span></td>',
								'<td></td>',
								'<td>总销售额：<b id="sumBill" class="red">0</b>元</td>',
								'<td></td>',
								'<td>总佣金：<b id="sumCom" class="red">0</b>元</td>',
							'</tr>',
						'</table>',
						'<table id="lookTable" lay-filter="lookTable"></table>',
					'</div>',
				].join(' '),
				success:function(){
					form.render();
					var cols = [
						{ type:'checkbox', },
						{ title:'日期', field:'dateAdd', },
						{ title:'分销级别', field:'level', },
						{ title:'昵称', field:'nick', },
						{ title:'商品名', field:'goodsName', },
						{ title:'账单金额', field:'bili', },
						{ title:'佣金金额', field:'money', },
					];
					laydate.render({ elem:'#searchLook', range:'~', }); 
					mytable.render({
						elem:'#lookTable',
						cols:[cols],
						data: [],
						done:function(){
							$('#sumBill').html(sumData.bill.toFixed(2));
							$('#sumCom').html(sumData.money.toFixed(2));
						}
					});
					form.on('submit(searchLookBtn)',function(obj){
						var f = obj.field;
						if(f.dateAddBegin){
							var t = f.dateAddBegin.split(' ~ ');
							f.dateAddBegin = t[0];
							f.dateAddEnd = t[1];
						}else
							f.dateAddEnd = "";
						getTableData(f);
					})
				},
			})
		}
		
		function openLookoverWin(data){
			layer.open({
				type:1,
				title: '分销明细',
				shadeClose:true,
				area:['80%','80%'],
				content:[
					'<div style="padding:10px;">',
						'<table class="layui-form searchTable">',
							'<tr>',
								'<td>日期：</td>',
								'<td><input id="searchLook" class="layui-input" name="dateAddBegin" autocomplete="off"></td>',
								'<td>分销级别：</td>',
								'<td style="width:120px;"><select name="level"><option value=""></option>',
										'<option value="1">一级</option>',
										'<option value="2">二级</option>',
										'<option value="3">三级</option></select>',
								'<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="searchLookBtn">搜索</span></td>',
								'<td></td>',
								'<td>团队总人数：<b id="sumPeople" class="red">0</b>人</td>',
							'</tr>',
						'</table>',
						'<table id="lookTable" lay-filter="lookTable"></table>',
					'</div>',
				].join(' '),
				success:function(){
					form.render();
					var cols = [
						{ type:'checkbox', },
						{ title:'日期', field:'dateAdd', },
						{ title:'分销级别', field:'level', },
						{ title:'昵称', field:'nick', },
					];
					laydate.render({ elem:'#searchLook', range:'~', }); 
					mytable.render({
						elem:'#lookTable',
						url: myutil.config.ctx+'/user/apiExtUserInviter/list',
						limit:10,
						request:{ pageName: 'page' ,limitName: 'pageSize'},
						parseData: function(ret){
							if(ret.code==0){
								var m = ret.data.userMapS;
								m = m || ret.data.userMaps;
								var r = ret.data.result;
								for(var i in r){
									r[i] = $.extend({},m[r[i].uids],r[i]);
								}
								$('#sumPeople').html(ret.data.totalRow);
								return {  msg:ret.message,  code:ret.code , data:ret.data.result, 
									count:ret.data.totalRow }; 
							}
							else
								return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
						},
						where:{
							uidm: data.id,
						},
						cols:[cols],
					})
					searchTable('searchLookBtn','lookTable');
				},
			})
		}
		
		function parseData(){
			return function(ret){
				if(ret.code==0){
					return {  msg:ret.message,  code:ret.code , data:ret.data.result, 
						count:ret.data.totalRow }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			};
		}
		searchTable('searchBtn','tableData');
		
		function searchTable(btn,tableId){
			form.on('submit('+btn+')',function(obj){
				var f = obj.field;
				if(f.dateAddBegin){
					var t = f.dateAddBegin.split(' ~ ');
					f.dateAddBegin = t[0];
					f.dateAddEnd = t[1];
				}else
					f.dateAddEnd = "";
				table.reload(tableId,{
					where: f,
					page:{ curr:1 },
				})
			})
		}
	}//end define function
)//endedefine
</script>
</html>