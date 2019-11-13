<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>耗料出库出库</title>
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
<script type="text/html" id="pickingTpl">
<div style="padding:20px;text-align: center;">
    <table class="layui-form" style="margin: auto;">
    	<tr>
			<td>下单时间：</td>
			<td><input type="text" class="layui-input" id="openOrderTime" name="openOrderTime"></td>
		</tr>
    	<tr>
    		<td>数量：</td>
    		<td><input type="text" class="layui-input" name="processNumber" lay-verify="required"></td>
    	</tr>
    	<tr>
    		<td>领取人：</td>
    		<td><select name="userId"  lay-search id="addUserSelect"><option value="">请选择</option></select></td>
    	</tr>
    	<tr>
    		<td>备注：</td>
    		<td><input type="text" name="remark" class="layui-input" lay-verify="required">
				<input type="hidden" value="1" name="type">
				<input type="hidden" value="0" name="outsource">
				<span style="display:none;" lay-submit id="addPicking" lay-filter="addPicking"></span></td>
    	</tr>
    </table>
</div>
</script>
<script type="text/html" id="outTpl">
<div style="padding:20px;text-align: center;">
    <table class="layui-form" style="margin: auto;">
    	<tr>
			<td>下单时间：</td>
			<td><input type="text" name="openOrderTime" id="openOrderTime" class="layui-input"></td>
		</tr>
    	<tr>
    		<td>数量：</td>
    		<td><input type="text" name="processNumber"  class="layui-input" lay-verify="number"></td>
    	</tr>
    	<tr>
    		<td>加工点：</td>
    		<td><select name="customerId" lay-search id="addCustomerSelect"><option value="">请选择</option></select></td>
    	</tr>
    	<tr>
    		<td>备注：</td>
    		<td><input type="text" name="remark"  class="layui-input">
				<input type="hidden" value="1" name="type">
				<input type="hidden" value="1" name="outsource">
				<span style="display:none;" lay-submit id="addOut" lay-filter="addOut"></span></td>
    	</tr>
    </table>
</div>
</script>
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
		
		var allUserSelect,allCustomSelect;
		myutil.getData({
			url:'${ctx}/system/user/findUserList',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allUserSelect += '<option value="'+d[i].id+'">'+d[i].userName+'</option>';
				}
			}
		})
		myutil.getData({
			url:'${ctx}/ledger/allCustomer?type=5',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					allCustomSelect += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
			}
		})
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
						html += '<option value="'+d[i].id+'">'+d[i].orderNumber+'</option>';
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
		mytable.render({
			elem:'#tableData',
			size:'lg',
			data:[],
			curd:{
				btn:[],
				otherBtn:function(obj){
					if(obj.event=='onekey'){
						myutil.deleTableIds({
							table:'tableData',
							text:'请选择相关信息|是否确认审核?',
							url:'/ledger/generatePlaceOrder',
						});
					}else if(obj.event=='picikingOrder'){
						picikingOrder();
					}else if(obj.event=='outOrder'){
						outOrder();
					}
				},
			},
			ifNull:'',
			colsWidth:[0,10,0,6,8,8,10,6],
			toolbar:['<span class="layui-btn layui-btn-sm" lay-event="onekey">一键审核</span>',
				     '<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="picikingOrder">领料单</span>',
					 '<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="outOrder">外发领料单</span>',
					].join(''),
			cols:[[
			       { type:'checkbox',},
			       { title:'审核日期',   field:'auditTime', type:'dateTime', },
			       { title:'分散出库编号',   field:'orderProcurement_orderProcurementNumber',	},
			       { title:'领取模式',   field:'orderMaterial_receiveMode_name',  },
			       { title:'领取用量',   field:'dosage',	},
			       { title:'下单数量',   field:'orderMaterial_order_number',	},
			       { title:'备注',   field:'remark',  },
			       { title:'是否审核',   field:'audit', transData:{data:['否','是'],}	},
			       ]]
		})
		function picikingOrder(){
			var check = table.checkStatus('tableData').data;
			if(check.length!=1)
				return myutil.emsg('领料单只能选择一条数据进行领取');
			layer.open({
				type:1,
				title:'领料单',
				offset:'80px',
				btn:['确定','取消'],
				btnAlign:'c',
				area:['40%','40%'],
				content:$('#pickingTpl').html(),
				success:function(){
					laydate.render({ elem:'#openOrderTime',type:'datetime',value:myutil.getSubDay(0,'yyyy-MM-dd hh:mm:ss'), });
					$('#addUserSelect').append(allUserSelect);
					form.on('submit(addPicking)',function(obj){
						obj.field.orderId = $('#orderIdSelect').val();
						obj.field.scatteredOutboundId = check[0].id;
						myutil.saveAjax({
							url:'/ledger/saveMaterialRequisition',
							data:obj.field,
						})
					})
					form.render();
				},
				yes:function(){
					$('#addPicking').click();
				}
			})
			
		}
		function outOrder(){
			var check = table.checkStatus('tableData').data;
			if(check.length!=1)
				return myutil.emsg('领料单只能选择一条数据进行领取');
			layer.open({
				type:1,
				title:'外发领料单',
				offset:'80px',
				btn:['确定','取消'],
				btnAlign:'c',
				area:['40%','40%'],
				content:$('#outTpl').html(),
				success:function(){
					laydate.render({ elem:'#openOrderTime',type:'datetime',value:myutil.getSubDay(0,'yyyy-MM-dd hh:mm:ss'), });
					$('#addCustomerSelect').append(allCustomSelect);
					form.on('submit(addOut)',function(obj){
						obj.field.orderId = $('#orderIdSelect').val();
						obj.field.scatteredOutboundId = check[0].id;
						myutil.saveAjax({
							url:'/ledger/saveMaterialRequisition',
							data:obj.field,
						})
					})
					form.render();
				},
				yes:function(){
					$('#addOut').click();
				}
			})
			
		}
	}//end define function
)//endedefine
</script>

</html>