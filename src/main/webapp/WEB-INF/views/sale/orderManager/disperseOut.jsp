<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>出库下单</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>合同日期:</td>
				<td><input type="text" name="orderTimeBegin" id="searchTime" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>合同:</td>
				<td style="width:500px;"><select name="orderId" disabled id="orderIdSelect" lay-search lay-filter="agreementSelect"></select></td>
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
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer
		, laydate = layui.laydate
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		var today = myutil.getSubDay(0,'yyyy-MM-dd');
		laydate.render({
			elem:'#searchTime',
			range:'~',
			value: today+' ~ '+today,
			done:function(val){
				if(val){
					val = val.split(' ~ ');
					getAgreementSelect({
						orderTimeBegin:val[0]+' 00:00:00',
						orderTimeEnd:val[1]+' 23:59:59',
					});
				}else{
					$('#orderIdSelect').attr('disabled','disabled');
					form.render();
				}
			}
		})
		getAgreementSelect({
			orderTimeBegin: today+' 00:00:00',
			orderTimeEnd: today+' 23:59:59',
		});
		function getAgreementSelect(data){
			myutil.getDataSync({
				url:'${ctx}/ledger/getOrder',
				data: data,
				success:function(d){
					var html = '<option value="">请选择</option>';
					for(var i in d){
						html += '<option value="'+d[i].id+'">'+d[i].bacthNumber+' ~ '+d[i].product.name+'</option>';
					}
					$('#orderIdSelect').html(html);
					$('#orderIdSelect').removeAttr('disabled');
					form.render();
				}
			})
		}
		form.on('select(agreementSelect)',function(obj){
			if(obj.value!='')
				table.reload('tableData',{
					url:'${ctx}/ledger/getScatteredOutbound?audit=1&orderId='+obj.value,//
				})
			else
				table.reload('tableData',{
					data:[],
					url:'',
				})
		})
		var allUser = [];
		myutil.getDataSync({
			url: '${ctx}/system/user/findUserList',
			success:function(d){
				allUser = d;
				allUser.unshift({
					id:'',userName:'请选择'
				})
			}
		})
		mytable.render({
			elem:'#tableData',
			size:'lg',
			data:[],
			curd:{
				btn:[],
			},
			ifNull:'',
			colsWidth:[0,10,0,6,8,8,6,6,0,6],
			autoUpdate:{
				updateUrl:'/ledger/updatePlaceOrder',
				otherBtn:function(obj){
					if(obj.type=='onekey'){
						myutil.deleTableIds({
							table:'tableData',
							text:'请选择相关信息|是否确认审核?',
							url:'/ledger/generatePlaceOrder',
						});
					}
				},
				field:{ receiveUser_id:'receiveUserId', user_id:'userId' },
			},
			toolbar:'<span class="layui-btn layui-btn-sm" lay-event="onekey">一键审核</span>',
			cols:[[
			       { type:'checkbox',},
			       { title:'下单日期',   field:'placeOrderTime', type:'dateTime', edit:true,	},
			       { title:'分散出库编号',   field:'outboundNumber',	},
			       { title:'领取人',   field:'receiveUser', edit:true,  },
			       { title:'领取模式',   field:'orderMaterial_receiveMode_name',  },
			       { title:'跟单人',   field:'user_id',	type:'select', select:{ data:allUser,name:'userName' }, },
			       { title:'领取用量',   field:'dosage',	},
			       { title:'下单数量',   field:'orderMaterial_order_number',	},
			       { title:'备注',   field:'remark', edit:true, },
			       { title:'是否审核',   field:'audit', transData:{data:['否','是'],}	},
			       ]]
		})
	}//end define function
)//endedefine
</script>

</html>