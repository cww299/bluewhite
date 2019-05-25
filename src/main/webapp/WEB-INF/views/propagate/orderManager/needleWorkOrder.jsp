<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>针工单</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
td{
	text-align:center;
}
</style>
</head>
<body>

<!-- 主页面 -->
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td><select name=""><option>按批次号</option></select>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" class="layui-input" name="batchNumber" placeholder='请输入要查找的相关信息'></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>
			</tr>
		</table>
		<table class="layui-table" id="needleOrderTable" lay-filter="needleOrderTable"></table>
	</div>
</div>

<!-- 查看订单隐藏框  -->
<div id="lookoverOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table" lay-skin="line">
		<tr><td>批次号</td>	
			<td><input type="text" class="layui-input" readonly id="look_batchNumber"></td>
			<td>经手人</td>
			<td><select disabled><option value="1" id="look_userName">测试人admin</option></select></td>
			<td>总数量</td>
			<td><input type="text" class="layui-input" id="look_number" readonly></td></tr>
		<tr><td>备注</td>
			<td colspan="5"><input type="text" id="look_remark" class="layui-input" readonly></td></tr>
	</table>
	<table class="layui-table" id="lookOverProductListTable" lay-filter="lookOverProductListTable"></table>
</div>

<!-- 生成入库单隐藏框  -->
<div id="becomeOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table">
		<tr><td>批次号<input type="hidden" name="type" value="2" ></td>	<!-- 默认type类型为2，表示为入库单 -->
			<td><input type="text" class="layui-input" name='batchNumber' id="become_bacthNumber" readonly></td>
			<td>经手人</td>
			<td><select name="userId"><option value="1" >测试人admin</option></select></td>
			<td>总数量</td>
			<td><select lay-filter="defaultNumberSelect"><option value="false">默认生成针工单数量为0</option><option value="true">全部生成针工单</option></select></tr>
		<tr><td>备注</td>
			<td colspan="3"><input type="text" name="remark" class="layui-input"></td>
			<td>操作</td>
			<td><span class="layui-btn" lay-submit lay-filter="sureBecome" >确定</span></td></tr>
	</table>
	<table class="layui-table" id="becomeProductListTable" lay-filter="becomeProductListTable"></table>
</div>


<!-- 入库单表格工具栏 -->
<script type="text/html" id="needleOrderTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >一键反冲</span>
	<span lay-event="becomeEntry"  class="layui-btn layui-btn-sm" >生产针工单</span>
	<span class="layui-badge" >小提示：双击查看详细信息</span>
</div>
</script>


</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).define(
	['tablePlug'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, tablePlug = layui.tablePlug;
		
		var chooseProductWin
		,choosedProduct=[];			//用户已经选择上的产品
		
		form.render();
		
		table.render({				//渲染主页面入库单表格
			elem:'#needleOrderTable',
			url:'${ctx}/inventory/procurementPage?type=2',
			toolbar:'#needleOrderTableToolbar',
			loading:true,
			page:{},
			request:{pageName:'page',limitName:'size'},
			parseData:function(ret){
				return {data:ret.data.rows,count:ret.data.total,msg:ret.message,code:ret.code}},
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'批次号',   field:'batchNumber',		   width:'',},
			       {align:'center', title:'商品数量', 	field:'number'},
			       {align:'center', title:'经手人',templet:'<p>{{ d.user.userName }}</p>'},
			       {align:'center', title:'备注', 	field:'remark'},
			       ]]
		})
		
		table.on('toolbar(needleOrderTable)',function(obj){	//监听入库单表格按钮
			switch(obj.event){
			case 'delete':		deletes();		break;
			case 'becomeEntry':becomeEntry(); break;
			}
		})
		
		table.on('rowDouble(needleOrderTable)',function(obj){
			lookover(obj.data);
		})
		form.on('submit(search)',function(obj){
			table.reload('needleOrderTable',{
				where:obj.field
			})
		})
		
		
		function deletes(){							//反冲针工单表格
			var choosed=layui.table.checkStatus('needleOrderTable').data;
			if(choosed.length<1){
				layer.msg('请选择生产单',{icon:2});
				return;
			}
			layer.confirm('是否确认反冲？',function(){
				var ids='';
				for(var i=0;i<choosed.length;i++)
					ids+=(choosed[i].id+',');
				var load=layer.load(1);
				$.ajax({
					url:'${ctx}/inventory/deleteProcurement?ids='+ids,
					success:function(result){
						if(0==result.code){
							table.reload('needleOrderTable');
							layer.msg(result.message,{icon:1});
						}else
							layer.msg(result.message,{icon:2});
						layer.close(load);
					}
				})
			})
		}
		
		//-------生成针工单功能---------------
		var becomeProduct=[];
		var defaultBecomeNumber=false;
		function becomeEntry(){
			becomeProduct=[];
			var choosed = layui.table.checkStatus('needleOrderTable').data;
			if(choosed.length<1){
				layer.msg('请选择信息',{icon:2});
				return;
			}
			if(choosed.length>1){
				layer.msg('无法同时使用多条信息生产针工单',{icon:2});
				return;
			}
			layer.open({
				type : 1,
				title:'生成针工单',
				area : ['90%','90%'],
				content:$('#becomeOrderDiv'),
			})
			table.render({									//渲染选择后的商品表格
				elem:'#becomeProductListTable',
				page:{},
				loading:true,
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'商品名称', templet:'<p>{{ d.commodity.skuCode }}</p>',},
				       {align:'center', title:'商品数量', field:'number', },
				       {align:'center', title:'生成针工单数量',    field:'becomeNumber', 	 edit:true,  templet:function(d){ return defaultBecomeNumber?d.number:0;}},
				       {align:'center', title:'针工单备注',  	  field:'becomeChildRemark', edit:true}, 
				       ]]
			})
			becomeProduct=choosed[0].procurementChilds;
			$('#become_bacthNumber').val(choosed[0].batchNumber);
			table.reload('becomeProductListTable',{
				data:becomeProduct
			})
		}
		form.on('select(defaultNumberSelect)',function(obj){
			defaultBecomeNumber=obj.value
			table.reload('becomeProductListTable');
		})
		form.on('submit(sureBecome)',function(obj){
			var choosed = layui.table.checkStatus('becomeProductListTable').data;
			if(choosed.length<1){
				layer.msg('请勾选需要生成针工单的信息',{icon:2});
				return;
			}
			var c=[];       //用于存放提取真正需要的数据
			for(var i=0;i<choosed.length;i++){
				var t=choosed[i];
				if(t.becomeNumber==undefined){
					layer.msg('生成针工单的商品数量不能为空，请检查是否漏填或者错误勾选！',{icon:2});
					return;
				}else{
					c.push({
						commodityId:t.commodity.id,
						number:t.becomeNumber,
						childRemark:t.becomeChildRemark==undefined?'':t.becomeChildRemark
					})
				}
			}
			var data=obj.field;
			data.number=$('#become_number').val();
			data.commodityNumber=JSON.stringify(c);		
			var load = layer.load(1);
			$.ajax({
				url:"${ctx}/inventory/addProcurement",
				type:"post",
				data:data,			
				success:function(result){
					if(0==result.code){
						layer.closeAll();
						table.reload('needleOrderTable');
						layer.msg(result.message,{icon:1});
					}else{
						layer.msg(result.message,{icon:2});
					}
					layer.close(load);
				},
				error:function(){
					layer.msg("服务器异常",{icon:2});
					layer.close(load);
				}
			})
		})
		table.on('edit(becomeProductListTable)', function(obj){ 			//监听编辑表格单元
			if(obj.field=='becomeNumber'){
				if(isNaN(obj.value)){
					layer.msg("修改无效！请输入正确的数字",{icon:2});
				}
				else{
					for(var i=0;i<becomeProduct.length;i++){
						 if(becomeProduct[i].id==obj.data.id){		//重新对该行的相关数据进行计算
							 if(obj.value>becomeProduct[i].number)
								 layer.msg('转成针工单的数量不能大于商品本身数量',{icon:2});
							 else if(obj.value<0)
								 layer.msg('转成针工单的数量不能小于0',{icon:2});
							 else if(obj.value%1 !== 0)
								 layer.msg('转成针工单的数量必须为整数',{icon:2});
							 else{
							 	 becomeProduct[i].becomeNumber=obj.value;
							 }
						 	break;
						}
					}
				}
			}else{
				for(var i=0;i<becomeProduct.length;i++){
					 if(becomeProduct[i].id==obj.data.id){		//重新对该行的相关数据进行计算
						 becomeProduct[i].becomeChildRemark=obj.data.becomeChildRemark;
					 	break;
					}
				}
			}
			table.reload('becomeProductListTable',{
				data:becomeProduct
			})
		});
		//-------查看生产单功能--------------------
		function lookover(data){
			layer.open({
				type : 1,
				title : '查看入库单',
				area : ['90%','90%'],
				content : $('#lookoverOrderDiv')
			})
			table.render({									//渲染选择后的商品表格
				elem:'#lookOverProductListTable',
				data:data.procurementChilds,
				page:{},
				loading:true,
				cols:[[
				       {align:'center', title:'商品名称',  templet:'<p>{{ d.commodity.skuCode }}</p>'},
				       {align:'center', title:'数量',     field:'number',},
				       {align:'center', title:'备注', 	  field:'childRemark',}, 
				       ]]
			})
			$('#look_batchNumber').val(data.batchNumber);
			$('#look_remark').val(data.remark);
			$('#look_number').val(data.number);
			//$('#look_user').val(choosed[0].user);
		}
	
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
	}//end define function
)//endedefine
</script>
</html>