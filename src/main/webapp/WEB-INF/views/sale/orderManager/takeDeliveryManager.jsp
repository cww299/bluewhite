<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>发货日期：</td>
				<td><input type="text" class="layui-input" id="searchTime"></td>
				<td>&nbsp;&nbsp;</td>
				<td>是否审核：</td>
				<td style="width:150px;"><select name="audit"><option value="">是否审核</option>
															 <option value="1">审核</option>
															 <option value="0" selected>未审核</option></select></td>
															 <td>&nbsp;&nbsp;</td>
				<td>是否确认：</td>
				<td style="width:150px;"><select name="deliveryStatus"><option value="">是否确认</option>
															 <option value="1">确认</option>
															 <option value="0" selected>未确认</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>客户名：</td>
				<td><input type="text" class="layui-input" name="customerName"></td>
				<td>&nbsp;&nbsp;</td>
				<td>批次号：</td>
				<td><input type="text" class="layui-input" name="bacthNumber"></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script id="tableToolbar" type="text/html">
<div>
	<span class="layui-btn layui-btn-sm" lay-event="sure">确认</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="unsure">取消确认</span>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil: 'layui/myModules/myutil',
}).define(
	['tablePlug','myutil','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table
		, laydate = layui.laydate
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.config.msgOffset = '200px';
		myutil.clickTr();
		var tipWin = '';
		laydate.render({
			elem:'#searchTime',range:'~'
		})
		var sty = "background-color: #5FB878;color: #fff;";
		table.render({
			elem:'#tableData',
			url:'${ctx}/ledger/salePage',
			where: { audit: 0,deliveryStatus:0 },
			page:true,
			toolbar: '#tableToolbar',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox', fixed:'left',},
			       {align:'center', title:'销售编号',	width:'6%', field:'saleNumber',   fixed:'left', style:sty },
			       {align:'center', title:'发货日期',   	width:'7%',	field:'sendDate', 	  fixed:'left', style:sty, templet:'<span>{{ d.sendDate?d.sendDate.split(" ")[0]:""}}</span>' },
			       {align:'center', title:'业务员',   	width:'8%',	field:'user',	 templet:'<span>{{ d.customer?d.customer.user.userName:""}}</span>'},
			       {align:'center', title:'客户',   		width:'8%',	field:'custom',	 templet:'<span>{{ d.customer?d.customer.name:""}}</span>'},
			       {align:'center', title:'批次号',   	width:'8%',	field:'bacthNumber',	},
			       {align:'center', title:'产品名',   	width:'15%',field:'productName',	templet:'<span>{{ d.product?d.product.name:""}}</span>'},
			       {align:'center', title:'离岸数量',   	width:'6%',	field:'count',	},
			       {align:'center', title:'总价',   		width:'6%',	field:'sumPrice',	},
			       {align:'center', title:'到岸数量',   	width:'6%',	field:'deliveryNumber',	edit:'text', },
			       {align:'center', title:'到岸日期',   	width:'7%',	field:'deliveryDate',	templet:'<span>{{ d.deliveryDate?d.deliveryDate.split(" ")[0]:""}}</span>', },
			       {align:'center', title:'争议数量',   	width:'6%',	field:'disputeNumber',	edit:'text', },
			       {align:'center', title:'争议备注',   	field:'disputeRemark',	edit:'text', },
			       {align:'center', title:'是否确认',   	width:'6%',	field:'deliveryStatus',	templet:'<span>{{ d.deliveryStatus==1?"确认":"未确认"}}</span>', fixed:'right', style:sty },
			       ]],
	        done:function(){
	        	layui.each($('td[data-field="deliveryDate"]'),function(index,item){
	        		item.children[0].onclick = function(event) { layui.stope(event) };
					laydate.render({
						elem: item.children[0],
						done: function(val){
							var index = $(this.elem).closest('tr').attr('data-index');
							var trData = table.cache['tableData'][index];
							myutil.saveAjax({
								url:'/ledger/updateUserSale',
								data: {
									id: trData.id,
									deliveryDate: val+' 00:00:00'
								}
							}) 
						}
					}) 
	        	})
			}
		})
		table.on('toolbar(tableData)',function(obj){
			switch(obj.event){
			case 'sure': sure(1); break;
			case 'unsure': sure(0); break;
			}
		})
		function sure(issure){
			var choosed = layui.table.checkStatus('tableData').data;
			if(choosed.length==0)
				return myutil.emsg('请选择相关信息');
			var ids = [];
			for(var i=0;i<choosed.length;i++){
				if( issure==1 &&(choosed[i].deliveryNumber==null || choosed[i].deliveryNumber==''))
					return myutil.emsg('请填写到岸数量后再进行确认')
				ids.push(choosed[i].id);
			}
			myutil.saveAjax({
				url:'/ledger/auditUserSale?deliveryStatus='+issure,
				type: 'get',
				data: { ids:ids.join(',') },
				success: function(){
					table.reload('tableData');
				}
			})
		}
		table.on('edit(tableData)',function(obj){
			var val = obj.value, msg='', field = obj.field;
			switch(field){
			case 'deliveryNumber':  isNaN(val) && (msg="请正确输入到岸数量"); 
									val<0 && (msg="到岸数量不能小于0"); 
									val%1.0!=0 && (msg="到岸数量只能为整数"); break;
			case 'disputeNumber':   isNaN(val) && (msg="请正确输入争议数量"); 
									val<0 && (msg="争议数量不能小于0");
									val%1.0!=0 && (msg="争议数量只能为整数"); break;
			}
			if(msg!='')
				myutil.emsg(msg);
			else{
				var data = { id: obj.data.id, };
				data[field] = val
				myutil.saveAjax({
					url:'/ledger/updateUserSale',
					data: data
				}) 
			}
			table.reload('tableData');
		})
		form.on('submit(search)',function(obj){
			var val = $('#searchTime').val(), beg='',end='';
			if(val!=''){
				val = val.split('~');
				beg = val[0].trim()+' 00:00:00';
				end = val[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = beg;
			obj.field.orderTimeEnd = end;
			table.reload('tableData',{
				where: obj.field,
				page: { curr:1 },
			})
		}) 
	}//end define function
)//endedefine
</script>

</html>