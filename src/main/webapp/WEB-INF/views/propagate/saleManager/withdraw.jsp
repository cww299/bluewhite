<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
  <head>
  <link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
  <script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>提现管理</title>
  <style>
	.red{
		color: red;
	}
	.searchTables td{
		padding-right: 7px;
	}
  </style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTables">
			<tr>
				<td><input class="layui-input" name="mobileUser" placeholder="手机号"></td>
				<td><input class="layui-input" name="uid" placeholder="用户ID"></td>
				<td><input class="layui-input" name="nick" placeholder="昵称"></td>
				<td><input class="layui-input" name="number" placeholder="提现编号"></td>
				<td><select name="status"><option value=""></option>
									<option value="0">等待处理</option>
									<option value="1">成功</option>
									<option value="2">失败</option>
									<option value="3">处理中</option></select></td>
				<td><input class="layui-input" name="dateAddBegin" placeholder="申请发起时间" id="dateAddBegin"></td>
				<td><input class="layui-input" name="dateUpdateBegin" placeholder="更新时间" id="dateUpdateBegin"></td>
				<td><span class="layui-btn layui-btn-" lay-submit lay-filter="searchBtn">搜索</span></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData" class="bigTable"></table>
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
		laydate.render({ elem:'#dateAddBegin',range:'~' })
		laydate.render({ elem:'#dateUpdateBegin',range:'~' })
		var userMap = {};
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/user/extUserWithdraw/list',
			request:{ pageName: 'page' ,limitName: 'pageSize'},
			parseData: parseData(),
			limit:15,
			limits:[10,15,20,50,100],
			size:'lg',
			cols:[[
				{ type:'checkbox', },
				{ title:'手机/昵称', field:'threeRow_1', templet: getUserInfo(), },
				{ title:'提现编号', field:'number', },
				{ title:'提现账号', field:'tx',  templet: getTxAcount(),},
				{ title:'金额/手续费', field:'twoRow_1', templet: getMoney(), },
				{ title:'状态', field:'status', transData:{ data:['等待处理 ','成功','失败','处理中'], }},
				{ title:'提现/更新时间', field:'twoRow_2', templet: getTime(), },
				{ title:'操作', field:'', templet: getBtn(), },
			]],
			done:function(r){
				
			}
		})
		table.on('tool(tableData)',function(obj){
			if(obj.event=="success"){
				successPay(obj.data);
			}else if(obj.event=="fail"){
				failPay(obj.data);
			}
		})
		function successPay(data){
			layer.confirm("请确保已经手动转账给用户，设为成功后，冻结的提现金额将被扣除！",{ offset:'50px',icon:1 },function(){
				myutil.saveAjax({
					url:'/user/extUserWithdraw/success',
					data:{ id: data.id, },
					success:function(){
						table.reload('tableData');
					}
				})
			})
		}
		function failPay(data){
			layer.confirm("驳回后，冻结的提现金额将返还给用户，用户可重新发起提现申请！",{ offset:'50px',icon:2 },function(){
				myutil.saveAjax({
					url:'/user/extUserWithdraw/refuse',
					data:{ id: data.id, },
					success:function(){
						table.reload('tableData');
					}
				})
			})
		}
		function getBtn(){
			return function(d){
				if(d.status==0 || status==3)
					return [
						'<span class="layui-btn layui-btn-sm layui-btn-green" lay-event="success">成功</span>',
						'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="fail">驳回</span>',
					].join(' ');
				return "";
			}
		}
		function getUserInfo(){
			return function(d){
				return [
					'<img src="'+userMap[d.uid].avatarUrl+'">',
					userMap[d.uid].mobile,
					'<br>',
					userMap[d.uid].nick,
				].join('');
			}
		}
		function getTxAcount(){
			return function(d){
				return userMap[d.uid].extJson.aliPay || "---";
			}
		}
		function getMoney(){
			return function(d){
				return [
					d.money,
					'<br>',
					d.moneyFee,
				].join('');
			}
		}
		function getTime(){
			return function(d){
				return [
					d.dateAdd || '---',
					'<br>',
					d.dateUpdate || '---',
				].join('');
			}
		}
		function parseData(){
			return function(ret){
				if(ret.code==0){
					userMap = ret.data.userMap;
					var idsSet = [];
					for(var i in userMap)
						idsSet.push(i);
					myutil.getDataSync({
						url: myutil.config.ctx+'/user/apiExtUser/info',
						data:{ ids: idsSet.join(','), },
						success:function(d){
							for(var i in d)
								userMap[d[i].userBase.id].extJson = d[i].extJson;
						}
					})
					return {  msg:ret.message,  code:ret.code , data:ret.data.result, 
						count:ret.data.totalRow }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			};
		}
		form.on('submit(searchBtn)',function(obj){
			var f = obj.field;
			if(f.dateAddBegin){
				var t = f.dateAddBegin.split(' ~ ');
				f.dateAddBegin = t[0];
				f.dateAddEnd = t[1];
			}else
				f.dateAddEnd = "";
			if(f.dateUpdateBegin){
				var t = f.dateUpdateBegin.split(' ~ ');
				f.dateUpdateBegin = t[0];
				f.dateUpdateEnd = t[1];
			}else
				f.dateUpdateEnd = "";
			table.reload('tableData',{
				where: f,
				page:{ curr:1 },
			})
		})
	}//end define function
)//endedefine
</script>
</html>