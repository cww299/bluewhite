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
</style>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
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
		var allBatch = [],allCustom = []; 	//所有的批次号、客户
		myutil.getData({
			url:'${ctx}/ledger/allCustomer',
			async: false,
			done: function(data){
				allCustom = data;
			}
		});
		myutil.getData({
			url:'${ctx}/ledger/getOrder',
			async: false,
			done: function(data){
				allBatch = data;
			}
		});
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
			       {align:'center', title:'发货日期',   field:'sendDate', edit:false,},
			       {align:'center', title:'客户',   field:'customerId',  edit:false, templet: getSelectHtml(allCustom,'customr'), },
			       {align:'center', title:'批次号',   field:'bacthNumber', edit:false, templet: getSelectHtml(allBatch,'batch'),  },
			       {align:'center', title:'产品', 	field:'productName', edit:false, templet: '<span>{{d.product?d.product.name:""}}</span>'	},
			       {align:'center', title:'数量',   field:'number',	edit:true,},
			       {align:'center', title:'发货数量',   field:'sendNumber',	edit:false,},
			       {align:'center', title:'剩余数量',   field:'surplusNumber', edit:false,	},
			       ]],
			done:function(){
				layui.each($('#tableData').next().find('td[data-field="sendDate"]'),function(index,item){
					item.children[0].onclick = function(event) { layui.stope(event) };
					laydate.render({
						elem: item.children[0],
						type: 'datetime',
						done: function(val){
							var index = $(this.elem).closest('tr').attr('data-index');
							var trData = table.cache['tableData'][index];
							update({
								id: trData.id,
								sendDate: val
							})
						}
					})
				})
			}
		})
		
		function getSelectHtml(data,field){
			return function(d){
				var html = '<select lay-filter="selectFilter" lay-search><option value="">请选择</option>';
				layui.each(data,function(index,item){
					var pid = item.product?item.product.id:'';
					var title = item.buyerName?item.buyerName:(item.bacthNumber+"~ "+item.product.name);
					var id = (field=='customr'?(d.customer?d.customer.id:''):d.bacthNumber);
					var selected = (item.id==id?'selected':'');
					html += '<option value="'+item.id+'" data-pid="'+pid+'" '+selected+'>'+title+'</option>';
				})
				return html += '</select>';
			}
		}
		form.on('select(selectFilter)',function(obj){
			/* var selected = $(obj.elem).next().find('input');
			var text = $(selected).val().split('~')[0];
			$(selected).attr('value',text) */
			var index = $(obj.elem).closest('tr').attr('data-index');
			var field = $(obj.elem).closest('td').attr('data-field');
			var pid = '';
			var trData = layui.table.cache['tableData'][index];
			if(field == 'bacthNumber'){
				var opt = $(obj.elem).find('option[value="'+obj.value+'"]'); 
				pid = $(opt).attr('data-pid');
				var text = $(opt).html();
				trData['productId'] = pid;
				$(obj.elem).closest('tr').find('td[data-field=productName]').find('div').html(text.split('~')[1]);
			}
			trData[field] = obj.value;
			if(index>=0){
				var data = { id: trData.id, }
				if(field=='bacthNumber'){
					data.bacthNumber = obj.value;
					data.productId = pid;
				}else
					data.customerId = obj.value;
				update(data);
			}
		})
		table.on('edit(tableData)',function(obj){
			var data = obj.data;
			if(data.id!=''){
				update({
					id: data.id,
					number: obj.value
				})
			}
		})
		form.on('submit(search)',function(obj){
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
			var allField = {customerId:'',bacthNumber:'',productId:'',number:'', };
			table.addTemp('tableData',allField,function(trElem){
				var sendDateTd = trElem.find('td[data-field="sendDate"]')[0];
				laydate.render({
					elem: sendDateTd.children[0],
					type:'datetime',
					done: function(val) {
						var index = $(this.elem).closest('tr').attr('data-index');
						table.cache['tableData'][index]['sendDate'] = val;
					}
				}) 
			});
	 	}
		function saveTempData(){
			var tempData = table.getTemp('tableData').data;
			for(var i=0;i<tempData.length;i++){
				var t = tempData[i];
				var msg = '';
				t.number=='' && (msg=='请填写发货数量');
				t.bacthNumber=='' && (msg='请选择批次号');
				t.customerId=='' && (msg='请选择客户');
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
		function deletes(){
			var choosed=layui.table.checkStatus('tableData').data;
			if(choosed.length<1)
				return myutil.emsg('请选择商品');
			layer.confirm("是否确认删除？",function(){
				var ids='';
				for(var i=0;i<choosed.length;i++)
					ids+=(choosed[i].id+",");
				myutil.deleteAjax({
					url:"",
					ids: ids,
					success: function(){
						table.reload('tableData');
					}
				})
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