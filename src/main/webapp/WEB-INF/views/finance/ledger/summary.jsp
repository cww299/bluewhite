<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>汇总</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
</head>
<style type="text/css">
	td{
		text-align:center;
	}
	.layui-table-cell{
		text-align:center;
	}
	.minTd{
		width:120px;
	}
</style>
<body>
	
<div class="layui-card">
	<div class="layui-card-body">					
		<table class="layui-form" id="searchTable">
			<tr>
				<td>货款日期：</td>
				<td><input id="searchTime" placeholder="请输入时间" class="layui-input"></td>
				<td>&nbsp;&nbsp;</td>
				<td>客户：</td>
				<td><select name="customerId" id="searchCustomer" lay-search><option value="">请选择</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchTable">统计</button></td>
			</tr>
		</table>
		<table id="summaryTable" lay-filter="summaryTable"></table>
	</div>
</div>
<!-- 查看明细隐藏框 -->
<div id="moreInfoDiv" style="display:none;padding:10px;">
	<table id="moreInfoTable" lay-filter="moreInfoTable"></table>
</div>
<script id="moreInfoTableToolbar" type="html/css">
<div class="layui-btn-container layui-inline">
	<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deletes">删除</span>
</div>
</script>
<script id="summaryTableToolbar" type="html/css">
	<div>
		<span class="layui-badge">提示：双击查看已到款明细</button>
	</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	myutil : 'layui/myModules/myutil',
	tablePlug : 'tablePlug/tablePlug',
}).define(
	[ 'myutil','jquery','table','laydate','tablePlug'],
	function() {
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form 
		, laydate = layui.laydate
		, table = layui.table
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.config.msgOffset = '200px';	
		myutil.timeFormat();
		myutil.clickTr();
		var thisMonth = new Date().format("yyyy-MM");
		var thisMonthFirstDay = thisMonth+'-01 00:00:00';
		laydate.render({ elem:'#searchTime', range:"~" });
		var lookoverCustomerId = '';
		table.render({
			elem:'#summaryTable',
			url: '${ctx}/ledger/collectBill',
			toolbar:'#summaryTableToolbar',
			totalRow: true,
			parseData:function(ret){ return { code : ret.code, msg : ret.msg, data : ret.data } },
			cols:[[
				   {type:'checkbox', totalRowText:'合计'},
			       {title:'客户',				field:'customerName',	},
			       {title:'货款总值',			field:'offshorePay',	totalRow:true,},
			       {title:'客户认可货款',		field:'acceptPay', 		totalRow:true,},
			       {title:'杂支应付',			field:'acceptPayable',	totalRow:true,},
			       {title:'争议货款',			field:'disputePay',		totalRow:true,},
			       {title:'未到货款',			field:'nonArrivalPay',	totalRow:true,},
			       {title:'客户多付货款',		field:'overpaymentPay',	totalRow:true,},
			       {title:'已到货款',			field:'arrivalPay',		totalRow:true,},
			       ]],
		})
		form.on('submit(searchTable)',function(obj){
			var val = $('#searchTime').val(), beg="",end="";
			if(val!=''){
				beg = val.split('~')[0].trim()+' 00:00:00';
				end = val.split('~')[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = beg;
			obj.field.orderTimeEnd = end;
			table.reload('summaryTable',{
				url:'${ctx}/ledger/collectBill',
				where: obj.field,
			})
		})
		table.on('rowDouble(summaryTable)',function(obj){
			lookoverCustomerId = obj.data.customerId;
			var win = layer.open({
				type:1,
				content:$('#moreInfoDiv'),
				area:['50%','70%'],
				success:function(){
					table.reload('moreInfoTable',{
						url:'${ctx}/ledger/receivedMoneyPage',
						where: {
							customerId: lookoverCustomerId,
							billDate: obj.data.billDate ? obj.data.billDate:'',
						}
					}) 
				}
			})
		})
		table.render({
			elem:'#moreInfoTable',
			data:[],
			toolbar:'#moreInfoTableToolbar',
			page: true,
			parseData:function(ret){ return { code : ret.code, msg : ret.msg, data : ret.data.rows, count:ret.data.total } },
			request:{ pageName: 'page' ,limitName: 'size' },
			cols:[[
				   {type:'checkbox'},
			       {title:'日期',	field:'receivedMoneyDate',	edit: false,	templet:'<span>{{ d.receivedMoneyDate.split(" ")[0]}}</span>'},
			       {title:'到账款',	field:'receivedMoney',		edit: true, },
			       {title:'批注',	field:'receivedRemark', 	edit: true, },
			       ]],
			done: function(){
				layui.each($('#moreInfoTable').next().find('td[data-field="receivedMoneyDate"]'),function(index,item){
					item.children[0].onclick = function(event) { layui.stope(event) };
					laydate.render({
						elem: item.children[0],
						done: function(val){
							var index = $(this.elem).closest('tr').attr('data-index');
							var trData = table.cache['moreInfoTable'][index];
							myutil.saveAjax({
								url:'/ledger/addReceivedMoney',
								data: { id: trData.id, receivedMoneyDate: val+' 00:00:00' }
							});
						}
					})
				})
			}
		});
		table.on('edit(moreInfoTable)',function(obj){
			var id = obj.data.id, index = $(obj.tr[0]).data('index');
			table.cache['moreInfoTable'][obj.field] = obj.value;
			if(id>0){
				var data = { id: id };
				data[obj.field] = obj.value;
				myutil.saveAjax({
					url:'/ledger/addReceivedMoney',
					data: data
				});
			}
		})
		table.on('toolbar(moreInfoTable)',function(obj){
			switch(obj.event){
			case 'addTempData': 	addTempData(); break;
			case 'cleanTempData': 	table.cleanTemp('moreInfoTable'); break;
			case 'saveTempData': 	saveTempData(); 	break;
			case 'deletes': 		deleteSome(); 		break;
			}
		})
		function addTempData(){
			allField = {receivedMoneyDate: '', receivedMoney:0,receivedRemark:'',};
			table.addTemp('moreInfoTable',allField,function(trElem) {
				var time = trElem.find('td[data-field="receivedMoneyDate"]')[0];
				laydate.render({
					elem: time.children[0],
					done: function(value, date) {
						var trElem = $(this.elem[0]).closest('tr');
						var index = trElem.data('index');
						table.cache['moreInfoTable'][index]['receivedMoneyDate'] = value+' 00:00:00';
					}
				}) 
			});
	 	}
		function saveTempData(){
			var tempData = table.getTemp('moreInfoTable').data;
			for(var i=0;i<tempData.length;i++){
				var t = tempData[i], msg='';
				t.customerId = lookoverCustomerId;
				isNaN(t.receivedMoney) && (msg='到账款只能为数字！');
				t.receivedMoney<0 && (msg='到账款不能小于0！');
				!t.receivedMoney && (msg='新增数据到账款不能为空！');
				!t.receivedMoneyDate && (msg='新增数据日期不能为空！');
				if(msg!='') return myutil.emsg(msg);
			}
			var success=0;
			for(var i=0;i<tempData.length;i++){
				myutil.saveAjax({
					url:'/ledger/addReceivedMoney',
					data: tempData[i],
					success: function(){  success++; }
				}); 
			}
			if(success == tempData.length){
				myutil.smsg('成功新增：'+success+'条数据');
				table.reload('moreInfoTable');
			}else
				myutil.emsg('新增第'+(success+1)+'条数据时发生异常');
		}
		function deleteSome(){
			var checked = layui.table.checkStatus('moreInfoTable').data;
			var data = table.cache['moreInfoTable'];
			if(checked.length==0){
				myutil.emsg('请选择数据');
				return;
			}
			layer.confirm('是否确认删除？',function(){
				var ids = [], checked = layui.table.checkStatus('moreInfoTable').data;
				if(checked.length==0)
					return myutil.emsg('请选择信息');
				layui.each(checked,function(index,item){
					ids.push(item.id);
				})
				myutil.deleteAjax({
					url:'/ledger/deleteReceivedMoney',
					ids: ids.join(','),
					success:function(){
						table.reload('moreInfoTable');
					}
				}); 
			})
		}
		myutil.getSelectHtml({
			url:'/ledger/allCustomer',
			value: 'id',
			title: 'name',
			tips: '请选择客户',
			done: function(html){
				$('#searchCustomer').html(html);
				form.render();
			}
		})
})
</script>
</body>
</html>