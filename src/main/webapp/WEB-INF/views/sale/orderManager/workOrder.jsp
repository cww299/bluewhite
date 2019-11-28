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
	<title>加工单</title>
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
<!-- 打印模板 -->
<script type="text/html" id="printTpl">
<div style="text-align:center;padding:30px;" id="printWin">
{{# for(var i in d){  }} 
	<table style="margin: auto; width: 100%;page-break-before:always;">
		<tr>
			<td style="text-align:center;border: 1px solid;width:30%;">{{ d[i].processingUser?d[i].processingUser.userName:"" }}</td>
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
			<td style="border: 1px solid;text-align:center;">{{# var process = [];
																layui.each(d[i].outsourceTask,function(index,item){
																	process.push(item.name);
																}) 
															  }}
 																{{ process.join(',') }}
															  </td>
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
					{{ d[i].processNumber * parseFloat(d[i].gramWeight) /100+'kg' }}
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
		myutil.clickTr();
		outOrderModel.init();
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
					url:'${ctx}/ledger/orderOutSourcePage?outsource=0&orderId='+$('#orderIdSelect').val(),
				})
			else
				table.reload('tableData',{
					data:[],
					url:'',
				})
		})
		mytable.render({
			elem:'#tableData',
			data:[],
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
					}else if(obj.event=='print'){
						printWin();
					}else if(obj.event=="edit"){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据编辑！');
						outOrderModel.update({
							data:check[0],
							success:function(){
								table.reload('tableData');
							}
						});
					}
				},
			},
			ifNull:'---',
			colsWidth:[0,0,18,4,7,6,8,7,4,4,8,4], 
			toolbar:[
					 '<span class="layui-btn layui-btn-sm" lay-event="edit">修改加工单</span>',
					 '<span class="layui-btn layui-btn-sm" lay-event="print">打印</span>',
			         '<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="audit">审核</span>',
			         ].join(' '),
			cols:[[
			       { type:'checkbox',},
			       { title:'编号',   field:'order_orderNumber',	},
			       { title:'工序',   field:'process', templet: getProcess(),	},
			       { title:'数量',   field:'processNumber',	},
			       { title:'时间',   field:'openOrderTime', type:'date',},
			       { title:'跟单人',   field:'user_userName',	},
			       { title:'加工点',   field:'processingUser_userName',	},
			       { title:'棉花类型',   field:'fill',	},
			       { title:'千克',   field:'kilogramWeight',	},
			       { title:'克重',   field:'gramWeight',	},
			       { title:'预计仓库',   field:'warehouseType_name',	},
			       { title:'审核',   field:'audit',	transData:{ data:['否','是'],}, },
			       ]]
		})
		function getProcess(){
			return function(data){
				var html = '';
				for(var i=0,len=data.outsourceTask.length;i<len;i++)
					html += '<span class="layui-badge layui-bg-green" style="margin:0 5px;">'
							+data.outsourceTask[i].name+'</span>';
				return html;
			}
		}
		function printWin(){
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
	}//end define function
)//endedefine
</script>

</html>