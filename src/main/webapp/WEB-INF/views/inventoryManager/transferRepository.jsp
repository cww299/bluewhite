<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>调拨转库</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
.minTd{
	width:100px;
}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr><td>&nbsp;&nbsp;&nbsp;</td>
				<td>批次号:</td>
				<td><input type="text" class="layui-input" name="bacthNumber"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>产品:</td>
				<td><input type="text" class="layui-input" name="productName"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>入库仓库:</td>
				<td><select name="" lay-search><option value=""></option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>是否审核:</td>
				<td class="minTd"><select name="confirm"><option value=""></option>
													<option value="1">审核</option>
													<option value="0" selected>未审核</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<!-- 表格工具栏模板 -->
<script type="text/html" id="tableToolbar">
<div>
	<span lay-event="audit"  class="layui-btn layui-btn-sm">一键审核</span>
	<span lay-event="noAudit"  class="layui-btn layui-btn-sm layui-btn-danger">取消审核</span>
</div>
</script>
<!-- 表格工具栏模板 -->
<script type="text/html" id="confirmTpl">
    {{#  var color = '',text = "未审核";
         if(d.confirm==1){
			color = 'green'; text = "审核";
         }
    }}
	<span class="layui-badge layui-bg-{{ color }}" style="margin-top: 10px;">{{ text }}</span>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil : 'layui/myModules/myutil' ,
}).define(
	['tablePlug','myutil'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		var cacheData = { }; //用于回滚数据
		var allInventory = myutil.getDataSync({ url: '${ctx}/basedata/list?type=inventory&limit=99',  })
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field
			})
		})
		table.render({
			elem:'#tableData',
			url:'${ctx}/ledger/packingChildPage?type=2&flag=1',
			where: { confirm:0 },
			toolbar:'#tableToolbar',
			page:true,
			size:'lg',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'批次号',   field:'bacthNumber',	},
			       {align:'center', title:'仓库',   field:'warehouseName',  templet:"<span>{{ d.warehouseType.name }}</span>" },
			       {align:'center', title:'入库仓库',   field:'warehouse',  templet: getWarehouseSelect() },
			       {align:'center', title:'产品',   field:'productName', templet:"<span>{{ d.product.name }}</span>"	},
			       {align:'center', title:'调拨数量',   field:'count',},
			       {align:'center', title:'确认数量',   field:'confirmNumber', edit:true	},
			       {align:'center', title:'剩余数量',   field:'surplusNumber',},
			       {align:'center', title:'是否审核',   field:'confirm', templet:'#confirmTpl'	},
			       ]],
			done:function(res){
				layui.each(res.data,function(index,item){
					cacheData['data_'+item.id] = item.confirmNumber || '';
				})
				form.render();
			}
		})
		function getWarehouseSelect(){
			return function(d){
				var id = d.warehouse?d.warehouse.id:'';
				var tip = id!='' ? '' : '<option value="">请选择仓库</option>';
				var disabled = d.confirm==1?'disabled':'';
				var html = '<select lay-filter="laySelect" '+disabled+'>'+tip;
				layui.each(allInventory,function(index,item){
					var selected = item.id==id?'selected':'';
					html += '<option value="'+item.id+'" '+selected+'>'+item.name+'</option>';
				})
				return html;
			}
		};
		form.on('select(laySelect)',function(obj){
			var index = $(obj.elem).closest('tr').data('index');
			var trData = table.cache['tableData'][index];
			myutil.saveAjax({
				url: '/ledger/updateInventoryPackingChild',
				data: { id: trData.id, warehouseId: obj.value },
			})
		})
		table.on('toolbar(tableData)',function(obj){
			switch(obj.event){
			case 'audit':	toConfirm();	break;
			case 'noAudit': toConfirm('no');	break;
			}
		})
		table.on('edit(tableData)',function(obj){
			var val = obj.value;
			var data = { id: obj.data.id, confirmNumber: val }, msg = '';
			val=='' && (msg="确认数量不能为空");
			isNaN(val) && (msg="确认数量只能为数字");
			val<0 && (msg="确认数量不能小于0");
			val%1.0!=0 && (msg="确认数量只能为整数");
			obj.data.count<val && (msg="确认数量不能大于调拨数量");
			if(msg!=''){
				$(this).val(cacheData['data_'+data.id])
				return myutil.emsg('修改无效！'+msg);
			}
			myutil.saveAjax({
				url: '/ledger/updateInventoryPackingChild',
				data: data,
				success:function(result){
					cacheData['data_'+data.id] = val;
				},
			})
		})
		function toConfirm(no){
			var url = no?'/ledger/cancelConfirmPackingChild':'/ledger/confirmPackingChild';
			var text = no?'请选择信息取消审核|是否确认取消审核？':'请选择信息审核|是否确认审核？';
			myutil.deleTableIds({
				url: url,
				table: 'tableData',
				text: text
			})
		}
	}//end define function
)//endedefine
</script>
</html> 