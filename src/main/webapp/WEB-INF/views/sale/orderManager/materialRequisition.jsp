<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/formSelect/formSelects-v4.css" />
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>领料单</title>
	<style>
		.layui-form-pane .layui-item{
			margin-top:10px;
		}
	</style>
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
    		<td><input type="text" class="layui-input" name="processNumber" id="processNumber" lay-verify="required"></td>
    	</tr>
    	<tr>
    		<td>领取人：</td>
    		<td><select name="userId"  lay-search id="addUserSelect"><option value="">请选择</option></select></td>
    	</tr>
    	<tr>
    		<td>备注：</td>
    		<td><input type="text" name="remark" id="remark" class="layui-input">
				<input type="hidden" id="editId" name="id">
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
    		<td><input type="text" name="processNumber" id="processNumber" class="layui-input" lay-verify="number"></td>
    	</tr>
    	<tr>
    		<td>加工点：</td>
    		<td><select name="customerId" lay-search id="addCustomerSelect"><option value="">请选择</option></select></td>
    	</tr>
    	<tr>
    		<td>备注：</td>
    		<td><input type="text" name="remark"  class="layui-input" id="remark">
				<input type="hidden" id="editId" name="id">
				<span style="display:none;" lay-submit id="addOut" lay-filter="addOut"></span></td>
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
	outOrderModel : 'layui/myModules/sale/outOrderModel' ,
}).define(
	['mytable','laydate','outOrderModel'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table
		, laydate = layui.laydate
		, outOrderModel = layui.outOrderModel
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		outOrderModel.init();
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
					url:'${ctx}/ledger/getMaterialRequisition?&orderId='+$('#orderIdSelect').val(),
				})
			else
				table.reload('tableData',{
					data:[],
					url:'',
				})
		})
		
		mytable.render({
			elem:'#tableData',
			curd:{
				btn:[4],
				otherBtn:function(obj){
					if(obj.event=="addEdit"){
						var check = table.checkStatus('tableData').data;
						if(check.length!=1)
							return myutil.emsg('只能修改一条数据！');
						if(check[0].outsource)
							outOrder(check[0]);
						else
							picikingOrder(check[0]);
					}else if(obj.event=="audit"){
						myutil.deleTableIds({
							table:'tableData',
							text:'请选择相关信息|是否确认审核?',
							url:'/ledger/auditMaterialRequisition',
						});
					}else if(obj.event=='outOrder' || obj.event=='outOrderWaifa'){
						var id = $('#orderIdSelect').val();
						if(!id)
							return myutil.emsg('请选择合同');
						var outsource = obj.event=='outOrder'?0:1;
						var name = $('#orderIdSelect').find('option[value="'+id+'"]').html();
						outOrderModel.add({
							data:{ 
								orderId: id,
								outsource: outsource,
							}
						})
					}
				}
			},
			autoUpdate:{
				deleUrl:'/ledger/deleteMaterialRequisition',
			},
			data:[],
			toolbar:['<span class="layui-btn layui-btn-sm" lay-event="addEdit">修改</span>',
					 '<span class="layui-btn layui-btn-sm" lay-event="audit">审核</span>',
					 '<span lay-event="outOrder" class="layui-btn layui-btn-sm" >加工单</span>',
					 '<span lay-event="outOrderWaifa" class="layui-btn layui-btn-sm" >外发加工单</span>',].join(' '),
			colsWidth:[0,12,0,7,8,8,8,12,8,8],
			cols:[[
			       { type:'checkbox',},
			       { title:'开单时间',   field:'openOrderTime',	type:'datetime'},
			       { title:'库存单编号',   field:'scatteredOutbound_orderProcurement_orderProcurementNumber',},
			       { title:'领取模式',   field:'scatteredOutbound_orderMaterial_receiveMode_name',  },
			       { title:'领取人',   field:'name', templet: getName(),   },
			       { title:'任务数量',   field:'processNumber', 	},
			       { title:'领取用量',   field:'dosage',	},
			       { title:'备注',   field:'remark',	},
			       { title:'是否外发',   field:'outsource', transData:{data:['领料单','外发领料单']}	},
			       { title:'是否审核',   field:'outsource', transData:{data:['否','是']}	},
		    ]]
		})
		function getName(){
			return function(d){
				if(d.user)
					return d.user.userName;
				if(d.customer)
					return d.customer.name;
				else
					return "";
			}
		}
		function picikingOrder(d){
			var addWin = layer.open({
				type:1,
				title:'领料单',
				offset:'80px',
				btn:['确定','取消'],
				btnAlign:'c',
				area:['40%','40%'],
				content:$('#pickingTpl').html(),
				success:function(){
					laydate.render({ elem:'#openOrderTime',type:'datetime',});
					$('#addUserSelect').append(allUserSelect);
					$('#openOrderTime').val(d.openOrderTime || "");
					$('#processNumber').val(d.processNumber);
					$('#remark').val(d.remark);
					$('#editId').val(d.id);
					$('#addUserSelect').find('option[value="'+(d.userId || 0)+'"]').attr('selected','selected');
					form.on('submit(addPicking)',function(obj){
						myutil.saveAjax({
							url:'/ledger/updateMaterialRequisition',
							data:obj.field,
							success:function(){
								layer.close(addWin);
								table.reload('tableData');
							}
						})
					})
					form.render();
				},
				yes:function(){
					$('#addPicking').click();
				}
			})
			
		}
		function outOrder(d){
			var addWin = layer.open({
				type:1,
				title:'外发领料单',
				offset:'80px',
				btn:['确定','取消'],
				btnAlign:'c',
				area:['40%','40%'],
				content:$('#outTpl').html(),
				success:function(){
					laydate.render({ elem:'#openOrderTime',type:'datetime', });
					$('#addCustomerSelect').append(allCustomSelect);
					$('#openOrderTime').val(d.openOrderTime || "");
					$('#processNumber').val(d.processNumber);
					$('#remark').val(d.remark);
					$('#editId').val(d.id);
					$('#addCustomerSelect').find('option[value="'+(d.customerId || 0)+'"]').attr('selected','selected');
					form.on('submit(addOut)',function(obj){
						myutil.saveAjax({
							url:'/ledger/updateMaterialRequisition',
							data:obj.field,
							success:function(){
								layer.close(addWin);
								table.reload('tableData');
							}
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