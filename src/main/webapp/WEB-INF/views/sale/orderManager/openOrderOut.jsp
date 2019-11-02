<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>外发单</title>
	<style>
		.layui-form-pane .layui-item {
		    margin-top: 10px;
		}
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>日期:</td>
				<td><input type="text" id="searchTime" class="layui-input"></td>
				<td>&nbsp;&nbsp;</td>
				<td>编号:</td>
				<td><input type="text" name="outSourceNumber" class="layui-input"></td>
				<td>&nbsp;&nbsp;</td>
				<td>工序:</td>
				<td><input type="text" name="process" class="layui-input"></td>
				<td>&nbsp;&nbsp;</td>
				<td>跟单人:</td>
				<td><input type="text" name="userName" class="layui-input"></td>
				<td>&nbsp;&nbsp;</td>
				<td>加工点:</td>
				<td><input type="text" name="customerName" class="layui-input"></td>
				<td>&nbsp;&nbsp;</td>
				<td>是否作废:</td>
				<td style="width:100px;"><select name="flag"><option value="">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>是否审核:</td>
				<td style="width:100px;"><select name="audit"><option value="">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<!-- 打印模板 -->
<script type="text/html" id="printTpl">
<div style="text-align:center;padding:30px;" id="printWin">
{{# for(var i in d){  }} 
	<table style="margin: auto; width: 100%;page-break-before:always;">
		<tr>
			<td style="text-align:center;border: 1px solid;width:30%;">{{ d[i].customer?d[i].customer.name:"" }}</td>
			<td style="text-align:center;border: 1px solid;width:10%;">&nbsp;</td>
			<td style="text-align:center;border: 1px solid;width:10%;">&nbsp;</td>
			<td style="text-align:center;border: 1px solid;width:20%;">&nbsp;</td>
			<td style="text-align:center;border: 1px solid;width:30%;">
				{{# var t = [];
					if(d[i].outGoingTime ){   
						t = d[i].outGoingTime.split(' ')[0].split('-');
				}}
					{{ t[0]+'年'+t[1]+'月'+t[2]+'日' }}
				{{# } }}
			</td>
		</tr>
		<tr>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
		</tr>
		<tr>
			<td style="border: 1px solid;text-align:center;">{{ d[i].outSourceNumber }}</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].processNumber?d[i].processNumber:"" }}</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].process }}</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].remark }}</td>
		</tr>
    </table>
	<br/>
	<table style="margin: auto; width: 100%;border: 1px solid;page-break-before:always;">
		<tr>
			<td style="border: 1px solid;text-align:center;width:30%;">{{ d[i].customer?d[i].customer.name:"" }}</td>
			<td style="border: 1px solid;text-align:center;width:10%;">&nbsp;</td>
			<td style="border: 1px solid;text-align:center;width:10%;">&nbsp;</td>
			<td style="border: 1px solid;text-align:center;width:20%;">&nbsp;</td>
			<td style="text-align:center;border: 1px solid;width:30%;">
				{{# var t = [];
					if(d[i].outGoingTime ){   
						t = d[i].outGoingTime.split(' ')[0].split('-');
				}}
					{{ t[0]+'年'+t[1]+'月'+t[2]+'日' }}
				{{# } }}
			</td>
		</tr>
		<tr>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
		</tr>
		<tr>
			<td style="border: 1px solid;text-align:center;">{{ d[i].outSourceNumber }}</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].processNumber?d[i].processNumber:"" }}</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].gramWeight }}</td>
			<td style="border: 1px solid;text-align:center;">{{ d[i].remark }}</td>
		</tr>
		<tr>
			<td style="border: 1px solid;"></td>
			<td style="border: 1px solid;text-align:center;">合计</td>
			<td style="border: 1px solid;text-align:center;">{{# if(d[i].gramWeight &&  d[i].processNumber){   }}
					{{ d[i].processNumber * parseFloat(d[i].gramWeight.split('g')[0]) /100+'kg' }}
				{{# } }}
			</td>
			<td style="border: 1px solid;">&nbsp;</td>
			<td style="border: 1px solid;">&nbsp;</td>
		</tr>
    </table>
	<br/><hr class="layui-bg-red"><br/>
{{# }  }} 
</div>
</script>
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
		, laydate = layui.laydate
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, outOrderModel = layui.outOrderModel
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.timeFormat();
		outOrderModel.init();
		laydate.render({
			elem:'#searchTime',
			range:'~',
		})
		var allUser = [],allCustom = [];
		mytable.render({
			elem:'#tableData',
			size:'lg',
			url:'${ctx}/ledger/orderOutSourcePage',
			autoUpdate:{
				deleUrl:'/ledger/deleteOrderOutSource',
			},
			curd:{
				btn:[4],
				otherBtn:function(obj){
					if(obj.event=='audit'){
						myutil.deleTableIds({
							url:'/ledger/auditOrderOutSource',
							table:'tableData',
							text:'请选择相关信息进行审核|是否确认审核？',
						})
					}else if(obj.event=='flag'){
						myutil.deleTableIds({
							url:'/ledger/invalidOrderOutSource',
							table:'tableData',
							text:'请选择相关信息进行作废|是否确认作废？',
						})
					}else if(obj.event=='print'){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length<1)
							return myutil.emsg('请选择相应的数据打印');
						var html = '';
						laytpl($('#printTpl').html()).render(check,function(h){
							html = h;
						})
						layer.open({
							type:1,
							title:'打印信息',
							content:html,
							area:['80%','80%'],
							btn:['打印','取消'],
							btnAlign:'c',
							shadeClose:true,
							yes: function(myDiv){    
								var printHtml = document.getElementById('printWin').innerHTML;
								var wind = window.open("",'newwindow', 'height=800, width=1500, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
								wind.document.body.innerHTML = printHtml;
								wind.print();
							},
						})
					}
				},
			},
			ifNull:'---',
			colsWidth:[0,0,10,6,10,6,8,8,6,6,], 
			toolbar:['<span class="layui-btn layui-btn-sm" lay-event="print">打印</span>',
			         '<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="audit">审核</span>',
			         '<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="flag">作废</span>',].join(' '),
			cols:[[
			       { type:'checkbox',},
			       { title:'外发编号',   field:'outSourceNumber',	},
			       { title:'外发工序',   field:'process',	},
			       { title:'外发数量',   field:'processNumber',	},
			       { title:'外发时间',   field:'openOrderTime', type:'date',},
			       { title:'跟单人',   field:'user_userName',	},
			       { title:'加工点',   field:'customer_name',	},
			       { title:'预计仓库',   field:'warehouseType_name',	},
			       { title:'是否作废',   field:'flag',	transData:{ data:['否','是'],}, },
			       { title:'是否审核',   field:'audit',	transData:{ data:['否','是'],}, },
			       ]]
		})
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var trIndex = $(this).data('index');
			var trData = table.cache['tableData'][trIndex];
			outOrderModel.update({
				data:trData,
				success:function(){
					table.reload('tableData');
				}
			})
		})
		form.on('submit(search)',function(obj){
			var t = $('#searchTime').val();
			if(t!=''){
				t = t.split(' ~ ');
				t[0] += ' 00:00:00';
				t[1] += ' 23:59:59';
			}else
				t = ['',''];
			obj.field.orderTimeBegin = t[0];
			obj.field.orderTimeEnd = t[1];
			table.reload('tableData',{
				where: obj.field,
				page: { curr:1 },
			})
			
		})
	}//end define function
)//endedefine
</script>

</html>