<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发货管理</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
} 
#defaultDate{
	font-weight: bolder;
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
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>批次号：</td>
				<td><input type="text" name="bacthNumber" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>产品名称：</td>
				<td><input type="text" name="productName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>客户名称：</td>
				<td><input type="text" name="customerName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<!-- 表格工具栏模板 -->
<script type="text/html" id="tableDataToolbar">
<div class="layui-btn-container layui-inline">
	<span class="layui-btn layui-btn-primary" id="defaultDate">0000-00-00</span>
	<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil: 'layui/myModules/myutil',
}).define(
	['tablePlug','layer','myutil','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form		
		, laydate = layui.laydate
		, myutil = layui.myutil
		, table = layui.table 
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		myutil.timeFormat();
		var allBatch = [],allCustom = []; 	//所有的批次号、客户
		var searchTime = new Date().format("yyyy-MM-dd");;
		allCustom = myutil.getDataSync({ url:'${ctx}/ledger/allCustomer', });
		allBatch = myutil.getDataSync({ url:'${ctx}/ledger/getOrder', });
		laydate.render({ elem:'#searchTime',range:'~'  })
		table.render({
			elem:'#tableData',
			url:'${ctx}/ledger/getSendGoods',
			toolbar:'#tableDataToolbar',
			page:true,
			size:'lg',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'发货日期',   field:'sendDate', edit:false, width:'10%', templet:'<span>{{ d.sendDate.split(" ")[0] }}</span>', },
			       {align:'center', title:'客户',   field:'customerId',  edit:false,  width:'12%', templet: getSelectHtml(allCustom,'customerId'),  },
			       {align:'center', title:'批次号',   field:'bacthNumber', edit:false, templet: getSelectHtml(allBatch,'bacthNumber'),  },
			       {align:'center', title:'产品', 	field:'productName', edit:false, templet: '<span>{{d.product?d.product.name:""}}</span>'	},
			       {align:'center', title:'数量',   field:'number',	edit:true,  width:'6%',},
			       {align:'center', title:'剩余数量',   field:'surplusNumber', edit:false, width:'6%',	},
			       ]],
			done:function(){
				layui.each($('#tableData').next().find('td[data-field="sendDate"]'),function(index,item){
					item.children[0].onclick = function(event) { layui.stope(event) };
					laydate.render({
						elem: item.children[0],
						done: function(val){
							var index = $(this.elem).closest('tr').attr('data-index');
							var trData = table.cache['tableData'][index];
							update({
								id: trData.id,
								sendDate: val+' 00:00:00'
							})
						}
					})
				})
				laydate.render({ 
					elem:'#defaultDate',
					value: searchTime,
					done:function(val){
						searchTime = val;
					}
				})
			}
		})
		function getSelectHtml(data,field){
			return function(d){
				var html = '<select lay-filter="selectFilter" lay-search><option value="">请选择</option>';
				layui.each(data,function(index,item){
					var id = d.customer ? d.customer.id : '';
					var title = item.name;
					if(field=='bacthNumber'){
						id=d.orderId;
						title = item.bacthNumber+"~ "+item.product.name;
					}
					var selected = (item.id==id ? 'selected' : '');
					html += '<option value="'+item.id+'" '+selected+'>'+title+'</option>';
				})
				return html += '</select>';
			}
		}
		form.on('select(selectFilter)',function(obj){
			var index = $(obj.elem).closest('tr').attr('data-index');
			var field = $(obj.elem).closest('td').attr('data-field');
			var trData = layui.table.cache['tableData'][index];
			if(field == 'bacthNumber'){
				var opt = $(obj.elem).find('option[value="'+obj.value+'"]'); 
				var text = $(opt).html();
				$(obj.elem).closest('tr').find('td[data-field=productName]').find('div').html(text.split('~')[1]);
				trData['orderId'] = obj.value;
			}else
				trData[field] = obj.value;
			if(index>=0){
				var data = { id: trData.id, }
				if(field=='bacthNumber'){
					data.orderId = obj.value;
				}else
					data.customerId = obj.value;
				update(data);
			}
		})
		table.on('edit(tableData)',function(obj){
			var data = obj.data;
			var val = obj.value, msg ='';
			isNaN(val) && (msg = '数量只能为数字');
			val<0 && (msg = '数量只能为数字');
			if(msg!='')
				return myutil.emsg(msg);
			if(data.id!=''){
				myutil.saveAjax({
					url: '/ledger/addSendGoods',
					data: { id: data.id, number: parseInt(val) },
					success: function(){
						$(obj.tr[0]).find('td[data-field="surplusNumber"]').find('div').html(val-data.sendNumber);
					}
				})
			}
		})
		form.on('submit(search)',function(obj){
			var val = $('#searchTime').val();
			var beg = '', end = '';
			if(val!=''){
				beg = val.split('~')[0].trim()+' 00:00:00';
				end = val.split('~')[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = beg;
			obj.field.orderTimeEnd = end;
			table.reload('tableData',{
				where: obj.field ,
				page:{ curr:1 },
			})
		}) 
		table.on('toolbar(tableData)',function(obj){
			switch(obj.event){
			case 'addTempData': 	addTempData(); 	break;
			case 'saveTempData': 	saveTempData(); break;
			case 'deleteSome': 		deleteSome(); 	break;
			case 'cleanTempData': 	table.cleanTemp('tableData'); break;
			}
		})
		function addTempData(){
			var allField = {customerId:'',number:'',sendDate:searchTime+' 00:00:00' ,id:'',orderId:'' };
			table.addTemp('tableData',allField,function(trElem){
				var sendDateTd = trElem.find('td[data-field="sendDate"]')[0];
				laydate.render({
					elem: sendDateTd.children[0],
					value: searchTime,
					done: function(val) {
						var index = $(this.elem).closest('tr').attr('data-index');
						table.cache['tableData'][index]['sendDate'] = val+' 00:00:00';
					}
				}) 
			});
	 	}
		function saveTempData(){
			var tempData = table.getTemp('tableData').data;
			for(var i=0;i<tempData.length;i++){
				var t = tempData[i];
				var msg = '';
				t.number=='' && (msg='请填写发货数量');
				t.orderId=='' && (msg='请选择批次号');
				t.customerId=='' && (msg='请选择客户');
				t.sendDate=='' && (msg='请填写发货时间');
				if(msg!='')
					return myutil.emsg(msg);
			}
			var successAdd=0;
			for(var i=0;i<tempData.length;i++){
				myutil.saveAjax({
					url: '/ledger/addSendGoods',
					data: tempData[i],
					success: function(r){
						r.code==0 && (successAdd++);
					}
				})
			}
			if(successAdd==tempData.length){
				myutil.smsg('成功新增：'+successAdd+'条数据');
				table.cleanTemp('tableData');
				table.reload('tableData')
			}
			else
				myutil.emsg('新增异常：'+(tempData.length-successAdd)+'条数据');
		}
		function deleteSome(){
			myutil.deleTableIds({
				url: '/ledger/deleteSendGoods',
				text: '请选择删除信息|是否确认删除？',
				table: 'tableData',
			})
		}
		function update(data){
			myutil.saveAjax({
				url: '/ledger/addSendGoods',
				data: data
			})
		}
	}//end define function
)//endedefine
</script>

</html>