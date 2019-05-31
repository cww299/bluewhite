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
				<td><select name=""><option>按批次号查找</option></select>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" class="layui-input" name="batchNumber" placeholder='请输入要查找的相关信息'></td>
				<td>&nbsp;&nbsp;</td>
				<td><select name="flag"><option value="">是否反冲</option><option value="1">反冲</option><option value="0">未反冲</option></select>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>
			</tr>
		</table>
		<table class="layui-table" id="needleOrderTable" lay-filter="needleOrderTable"></table>
	</div>
</div>

<!-- 查看订单隐藏框  -->
<div id="lookoverOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table"  lay-size="sm" lay-skin="nob">
		<tr><td>批次号</td>	
			<td><input type="text" class="layui-input" readonly id="look_batchNumber"></td>
			<td>经手人</td>
			<td><input type="text" class="layui-input" readonly id="look_user" val='无经手人'></td>
			<td>总数量</td>
			<td><input type="text" class="layui-input" id="look_number" readonly></td></tr>
		<tr><td>备注</td>
			<td colspan="3"><input type="text" id="look_remark" class="layui-input" readonly></td></tr>
	</table>
	<table class="layui-table" id="lookOverProductListTable" lay-filter="lookOverProductListTable"></table>
</div>

<!-- 生成入库单隐藏框  -->
<div id="becomeOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table" lay-size="sm" lay-skin="nob">
		<tr><td>批次号<input type="hidden" name="type" value="2" >
					  <input type="hidden" name="id" id='becomeOrderId' ></td>	<!-- 默认type类型为2，表示为入库单 -->
			<td><input type="text" class="layui-input" name='batchNumber' id="become_bacthNumber" readonly></td>
			<td>经手人</td>
			<td><select name="userId" id='userIdSelect'><option value="1" >测试人admin</option></select></td>
			<td>备注</td>
			<td colspan="3"><input type="text" name="remark" class="layui-input"></td></tr>
		<tr>
			<td>默认转单数量</td>
			<td><select lay-filter="defaultSelect" type='number'><option value="zero">默认生成入库单数量为0</option><option value="all">全部生成入库单</option></select>
			<td>默认入库仓库</td>
			<td><select lay-filter="defaultSelect" type='inventory' id='defaultInventorySelect'><option value="">获取数据中.....</option></select></td>
			<td>默认入库类型</td>
			<td><select lay-filter="defaultSelect" type='status' name='status' disabled>
						<option value="0">生产入库</option>
						<option value="1">调拨入库</option>
						<option value="2">销售退货入库</option>
						<option value="3">销售换货入库 </option>
						<option value="4">采购入库</option></select></td>
			<td>操作</td>
			<td><span class="layui-btn" lay-submit lay-filter="sureBecome" >确定</span></td></tr>
	</table>
	<table class="layui-table" id="becomeProductListTable" lay-filter="becomeProductListTable"></table>
</div>


<!-- 入库单表格工具栏 -->
<script type="text/html" id="needleOrderTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="becomeEntry"  class="layui-btn layui-btn-sm" >生成入库单</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >一键反冲</span>
	<span class="layui-badge" >小提示：双击查看详细信息</span>
</div>
</script>
<!-- 是否反冲转换模板 -->
<script type="text/html" id="flagTpl">
	{{# var color=d.flag==1?'':'green';
		var msg=d.flag==1?'反冲数据':'未反冲';}}
	<span class="layui-badge layui-bg-{{ color }}">{{ msg }}</span>
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
		
		var choosedProduct=[];			//用户已经选择上的产品
		var allInventory=[],
			allUser=[];
		
		getAllInventory();
		getAllUser();
		renderDefaultInventorySelect();
		
		form.render();
		
		table.render({				//渲染主页面入库单表格
			elem:'#needleOrderTable',
			url:'${ctx}/inventory/procurementPage?type=1',
			toolbar:'#needleOrderTableToolbar',
			loading:true,
			page:{},
			request:{pageName:'page',limitName:'size'},
			parseData:function(ret){
				return {data:ret.data.rows,count:ret.data.total,msg:ret.message,code:ret.code}},
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'单据编号',   	field:'documentNumber',	},
			       {align:'center', title:'批次号',   	field:'batchNumber',	},
			       {align:'center', title:'计划数量', 	field:'number',			},
			       {align:'center', title:'剩余数量', 	field:'residueNumber',	},
			       {align:'center', title:'经手人',templet:'<p>{{ d.user.userName }}</p>'},
			       {align:'center', title:'备注', 		field:'remark',			},
			       {align:'center', title:'是否反冲', 	field:'flag', templet:'#flagTpl'},
			       {align:'center', title:'日期',   	field:'createAt',	},
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
				where:obj.field,
				page: {  curr: 1   }
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
		
		//-------生成入库单功能---------------
		var becomeProduct=[];
		var defaultBecomeNumber='zero';	//默认转单数量
		var defaultInventory='';
		var defaultStatus=0;
		function becomeEntry(){
			defaultInventory=defaultInventory==''?allInventory[0].id:defaultInventory;
			becomeProduct=[];			//清空之前的数据
			$('#becomeOrderId').val('');
			var choosed = layui.table.checkStatus('needleOrderTable').data;
			if(choosed.length<1){
				layer.msg('请选择信息',{icon:2});
				return;
			}
			if(choosed.length>1){
				layer.msg('无法同时使用多条信息生成入库单',{icon:2});
				return;
			}
			if(choosed[0].flag==1){
				layer.msg('已反冲的数据无法进行转换',{icon:2});
				return;
			}
			$('#becomeOrderId').val(choosed[0].id);		//设置被转成针工单的生产单id
			layer.open({
				type : 1,
				title:'生成入库单',
				area : ['90%','90%'],
				content:$('#becomeOrderDiv'),
			})
			table.render({									//渲染选择后的商品表格
				elem:'#becomeProductListTable',
				page:{},
				size:'lg',
				loading:true,
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'批次号',   field:'batchNumber',},
				       {align:'center', title:'商品名称', templet:'<p>{{ d.commodity.skuCode }}</p>',},
				       {align:'center', title:'商品数量', field:'number', },
				       {align:'center', title:'剩余数量', field:'residueNumber'},
				       {align:'center', title:'入库仓库',  	  field:'warehouseId', 	templet: getInventorySelectHtml()}, 
				       {align:'center', title:'入库类型',  	  field:'status', 		templet: getStatusSelectHtml()}, 
				       {align:'center', title:'仓位',  	 	  field:'place', 				 edit:true, style:'color:blue',}, 
				       {align:'center', title:'生成入库单数量',    field:'becomeNumber', 	 edit:true, style:'color:blue',},
				       {align:'center', title:'入库单备注',  	  field:'becomeChildRemark', edit:true, style:'color:blue',}, 
				       ]],
		       	done: function (res, curr, count) {				
	                form.render(); 
	            },
			})
			becomeProduct=choosed[0].procurementChilds;				//回显该订单的子订单
			for(var i=0;i<becomeProduct.length;i++){				//设置默认值
				becomeProduct[i].becomeNumber=(defaultBecomeNumber=='zero'?0:becomeProduct[i].residueNumber);
				becomeProduct[i].warehouseId=defaultInventory;
				becomeProduct[i].status=defaultStatus;
			}
			$('#become_bacthNumber').val(choosed[0].batchNumber);
			$('#userIdSelect').html(getUserSelect(''));		//如果经手人为同一人，此处因加上之前经手人的id
			table.reload('becomeProductListTable',{
				data:becomeProduct
			})
		}
	
		form.on('select(selectInventory)', function (data) {
            var elem = $(data.elem);
            var trElem = elem.parents('tr');
            var tableData = table.cache['becomeProductListTable'];
            becomeProduct[trElem.data('index')]['warehouseId'] = data.value;
        });
		
		form.on('select(defaultSelect)',function(obj){
			switch(obj.elem.getAttribute('type')){
			case 'number': defaultBecomeNumber=obj.value;  
							for(var i=0;i<becomeProduct.length;i++)			
								becomeProduct[i].becomeNumber=(defaultBecomeNumber=='zero'?0:becomeProduct[i].residueNumber);
							break;
			case 'inventory' : defaultInventory=obj.value;    
							for(var i=0;i<becomeProduct.length;i++)
								becomeProduct[i].warehouseId=defaultInventory;
							break;
			case 'status' : defaultStatus=obj.value; 		
							for(var i=0;i<becomeProduct.length;i++)
								becomeProduct[i].status=defaultStatus;
							break;
			}
			table.reload('becomeProductListTable',{data:becomeProduct});
		})
		
		form.on('submit(sureBecome)',function(obj){
			var choosed = layui.table.checkStatus('becomeProductListTable').data;
			if(choosed.length<1){
				layer.msg('请勾选需要生成入库单的信息',{icon:2});
				return;
			}
			var c=[];       //用于存放提取真正需要的数据
			var allNum=0;
			for(var i=0;i<choosed.length;i++){
				var t=choosed[i];					
				if(t.becomeNumber==0){			
					layer.msg('转单的数量不能为0，请检查是否错勾选或填写有误！',{icon:2});
					return;
				}
				allNum+=t.becomeNumber;
				c.push({																//如果没有选择或者设置值，则为默认值
					commodityId : 	t.commodity.id,
					number : 		t.becomeNumber,
					warehouseId : 	t.warehouseId,
					status : 		t.status,
					place : 		t.place==undefined ? '' : t.place,
					childRemark : 	t.becomeChildRemark==undefined ? '' : t.becomeChildRemark
				})
			}
			var data=obj.field;						//订单的基本数据，在form表单中
			data.number=allNum;						//订单的总数量
			data.commodityNumber=JSON.stringify(c);	//订单的子订单
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
				}else if(obj.value=='')
					layer.msg('转成入库单的数量不能为空',{icon:2});
				else if(obj.value<0)
					layer.msg('转成入库单的数量不能小于0',{icon:2});
				else if(obj.value%1 !== 0)
					 layer.msg('转成入库单的数量必须为整数',{icon:2});
				else{
					for(var i=0;i<becomeProduct.length;i++){
						 if(becomeProduct[i].id==obj.data.id){		//重新对该行的相关数据进行计算
							 if(obj.value>becomeProduct[i].residueNumber)
								 layer.msg('转成入库单的数量不能大于剩余数量',{icon:2});
							 else{
							 	 becomeProduct[i].becomeNumber=parseInt(obj.value);
							 }
						 	break;
						}
					}
				}
			}else{
				for(var i=0;i<becomeProduct.length;i++){
					 if(becomeProduct[i].id==obj.data.id){		//重新对该行的相关数据进行计算
					 	if(obj.field=='becomeChildRemark')
							 becomeProduct[i].becomeChildRemark=obj.data.becomeChildRemark;
						else
							becomeProduct[i].place = obj.data.place;
					 	break;
					}
				}
			}
			table.reload('becomeProductListTable',{
				data:becomeProduct
			})
		});
		//-------查看针工单功能--------------------
		function lookover(data){
			layer.open({
				type : 1,
				title : '查看针工单',
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
				       {align:'center', title:'剩余数量', field:'residueNumber'},
				       {align:'center', title:'备注', 	  field:'childRemark',}, 
				       ]]
			})
			$('#look_batchNumber').val(data.batchNumber);
			$('#look_remark').val(data.remark);
			$('#look_number').val(data.number);
			$('#look_user').val(data.user.userName);
		}
	
		
		function getAllInventory(){
			$.ajax({
				url:'${ctx}/basedata/list?type=inventory',
				async:false,
				success:function(r){
					if(0==r.code){
						allInventory=r.data;
					}
				}
			})
		}
		function getAllUser(){
			$.ajax({
				url:'${ctx}/system/user/pages?size=999',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allUser.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].userName
							})
					}
				}
			})
		}
		function getUserSelect(id){
			var html='';
			for(var i=0;i<allUser.length;i++){
				var selected=( id==allUser[i].id?'selected':'' );
				html+='<option value="'+allUser[i].id+'" '+selected+'>'+allUser[i].userName+'</option>';
			}
			return html;
		}
		
		function renderDefaultInventorySelect(){
			var html='';
			if(allInventory.length==0){
				html='<option value="">暂无仓库可使用</option>';
			}else{
				for(var i=0;i<allInventory.length;i++){
					var t=allInventory[i],
					    disable = t.flag==1?'':'disabled';
					html+=('<option value="'+t.id+'" '+disable+'>'+t.name+'</option>');
				}
			}
			$('#defaultInventorySelect').html(html);
		}
		function getStatusSelectHtml(){				//获取类型下拉框
			return function(d) {		
				var html='<select disabled > ';
					html+=	'<option value="0"  '+ (d.status==0?"selected":"") +'>生产入库</option>';
					html+=	'<option value="1"  '+ (d.status==1?"selected":"") +'>调拨入库</option>';
					html+=	'<option value="2"  '+ (d.status==2?"selected":"") +'>销售退货入库</option>';
					html+=	'<option value="3"  '+ (d.status==3?"selected":"") +'>销售换货入库 </option>';
					html+=	'<option value="4"  '+ (d.status==4?"selected":"") +'>采购入库</option>';
					html+=	'</select>';
				return html;

			};
		}
		function getInventorySelectHtml() {				//获取仓库下拉框
			return function(d) {		
				if(allInventory.length==0){
					return '没有可用仓库';
				}
				var html='<select id="selectInventory" lay-filter="selectInventory"  > ';
				for(var i=0;i<allInventory.length;i++){
					var disable = allInventory[i].flag==1?'':'disabled';
					var selected = d.warehouseId==allInventory[i].id?'selected':'';
					html+='<option value="'+allInventory[i].id+'" '+disable+' '+selected+'>'+allInventory[i].name+'</option>';
				}
				return html; 
			};
		};
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