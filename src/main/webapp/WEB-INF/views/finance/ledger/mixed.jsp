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
				<td>客户名称：</td>
				<td><input type="text" name="customerName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>日期：</td>
				<td><input type="text" id="searchTime" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="mixTable" lay-filter="mixTable"></table>
	</div>
</div>

</body>

<!-- 表格工具栏模板 -->
<script type="text/html" id="mixToolbar">
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
	['tablePlug','myutil','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, laydate = layui.laydate
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({ elem: '#searchTime',range: '~' })
		var allCustomer = [];
		myutil.getData({
			url:'${ctx}/ledger/allCustomer',
			async: false,
			done: function(data){
				allCustomer = data;
			}
		});
		form.on('submit(search)',function(obj){
			var val = $('#searchTime').val(),beg='',end='';
			if(val!=''){
				beg = val.split('~')[0].trim()+' 00:00:00';
				end = val.split('~')[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = beg;
			obj.field.orderTimeEnd = end;
			table.reload('mixTable',{
				where: obj.field,
				page: {curr:1}
			})
		}) 
		table.render({
			elem:'#mixTable',
			url:'${ctx}/ledger/mixedPage',
			toolbar:'#mixToolbar',
			page:true,
			size: 'lg',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'客户',   field:'customerId',	 edit:false, templet: getCustomSelect()	}, 
			       {align:'center', title:'日期',   field:'mixtTime',    edit:false, templet: '<span>{{ d.mixtTime?d.mixtTime.split(" ")[0]:""}}</span>'},
			       {align:'center', title:'金额',   field:'mixPrice',	 edit:true,},
			       {align:'center', title:'明细',   field:'mixDetailed', edit:true, 	},
			       ]],
			done:function(){
				layui.each($('#mixTable').next().find('td[data-field="mixtTime"]'),function(index,item){
					item.children[0].onclick = function(event) { layui.stope(event) };
					laydate.render({
						elem: item.children[0],
						done: function(val){
							var index = $(this.elem).closest('tr').attr('data-index');
							var trData = table.cache['mixTable'][index];
							update({
								id: trData.id,
								mixtTime: val+' 00:00:00'
							})
						}
					})
				})
			}
		})
		form.on('select(tableSelect)',function(obj){
			var index = $(obj.elem).closest('tr').attr('data-index');
			var trData = layui.table.cache['mixTable'][index];
			trData['mixTable'] = obj.value;
			index>=0 && ( update({ id: trData.id,customerId: obj.value}) );
		})
		table.on('edit(mixTable)',function(obj){
			var val = obj.value, trData = obj.data, data = { id : trData.id },field = obj.field,msg = '';
			data[field] = val;
			if(field == 'mixPrice' && trData.id>0){
				val=='' && (msg='修改失败，金额不能为空');
				isNaN(val) && (msg='修改失败，金额只能为数字');
				if(msg!=''){
					table.reload('mixTable');
					return myutil.emsg(msg);
				}
			}
			trData.id>0 && update(data);
		})
		function getCustomSelect(){
			return function(d){
				var html = '<select lay-search lay-filter="tableSelect">';
				layui.each(allCustomer,function(index,item){
					var selected = d.customer?d.customer.id == item.id ? 'selected' : '' : '';
					html+='<option value="'+item.id+'" '+selected+'>'+item.name+'</option>'
				})
				return html+='</select>';
			}
		}
		table.on('toolbar(mixTable)',function(obj){
			switch(obj.event){
			case 'addTempData': 	addTempData(); 	break;
			case 'saveTempData': 	saveTempData(); break;
			case 'deleteSome': 		deleteSome(); 	break;
			case 'cleanTempData': 	table.cleanTemp('mixTable'); break;
			}
		})
		function addTempData(){
			var field = { customerId: allCustomer[0].id,mixtTime:'',mixDetailed:'',mixPrice:''};
			table.addTemp('mixTable',field,function(trElem){ 
				var mixtTimeTd = trElem.find('td[data-field="mixtTime"]')[0];
				laydate.render({
					elem: mixtTimeTd.children[0],
					done: function(val) {
						var index = $(this.elem).closest('tr').attr('data-index');
						table.cache['mixTable'][index]['mixtTime'] = val+' 00:00:00';
					}
				}) 
			});
		}
		function saveTempData(){
			var data = table.getTemp('mixTable').data,success = 0,msg='';
			for(var i=0;i<data.length;i++){
				data[i].mixPrice=='' && (msg='金额不能为空');
				data[i].mixtTime=='' && (msg='时间不能为空');
				isNaN(data[i].mixPrice) && (msg='修改失败，金额只能为数字');
			}
			if(msg!='') return myutil.emsg(msg);
			for(var i=0;i<data.length;i++)
				myutil.saveAjax({
					url: '/ledger/addMixed',
					data: data[i],
					success: function(){
						success++;
					}
				})
			if(success==data.length){
				myutil.smsg('成功新增：'+success+'条数据');
				table.reload('mixTable');
			}
		}
		function deleteSome(){
			var choosed=layui.table.checkStatus('mixTable').data;
			if(choosed.length<1)
				return myutil.emsg('请选择商品');
			layer.confirm("是否确认删除？",function(){
				var ids='';
				for(var i=0;i<choosed.length;i++)
					ids+=(choosed[i].id+",");
				myutil.deleteAjax({
					url:"/ledger/deleteMixed",
					ids: ids,
					success:function(){
						table.reload('mixTable');
					},
				})
			})
		}
		function update(data){
			myutil.saveAjax({
				url: '/ledger/addMixed',
				data: data,
			})
		}
	}//end define function
)//endedefine
</script>

</html>