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
.layui-card .layui-table-cell{	
	  height:auto;
	  padding:0px;
}
.layui-card  .layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(0%);
}
.layui-table tbody tr:hover, .layui-table-hover {
	background-color: transparent;
}
</style>
</head>
<body>
<!-- 主页面 -->
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>批次号:</td>
				<td><input type="text" class="layui-input" name="batchNumber" placeholder='请输入批次号'></td>
				<td>&nbsp;&nbsp;</td>
				<td>单据编号:</td>
				<td><input type="text" class="layui-input" name="documentNumber" placeholder='请输入单据编号'></td>
				<td>&nbsp;&nbsp;</td>
				<td>商品名:</td>
				<td><input type="text" class="layui-input" name="commodityName" placeholder='请输入商品名'></td>
				<td>&nbsp;&nbsp;</td>
				<td><select name="flag"><option value="">是否反冲</option><option value="1">反冲</option><option value="0" selected>未反冲</option></select>
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
		<tr><td>下单时间</td>	
			<td><input type="text" class="layui-input" readonly id="look_createdAt"></td>
			<td>经手人</td>
			<td><input type="text" class="layui-input" readonly id="look_user" val='无经手人'></td>
			<td>总数量</td>
			<td><input type="text" class="layui-input" id="look_number" readonly></td></tr>
		<tr><td>备注</td>
			<td colspan="3"><input type="text" id="look_remark" class="layui-input" readonly></td></tr>
	</table>
	<table class="layui-table" id="lookOverProductListTable" lay-filter="lookOverProductListTable"></table>
</div>
<!-- 添加订单隐藏框  -->
<div id="addOrderDiv" style="display:none;padding:10px;">
	<table class="layui-form layui-table"  lay-size="sm" lay-skin="nob">
		<tr>
			<td>下单时间 <input type="hidden" name="type" value="1" ></td> <!-- 默认type类型为1，表示为针工单 -->
			<td><input type="text" class="layui-input" name="createdAt" id="addCreatedAt"></td>
			<td>经手人</td>
			<td><select name="userId" id='userIdSelectAdd' lay-search><option value="1" >获取数据中...</option></select></td>
			<td>数量</td>
			<td><input type="text" class="layui-input" name="number" id="addNumber" readonly value='0'></td></tr>
		<tr>
			<td>备注</td>
			<td><input type="text" name="remark" class="layui-input" id="addRemark"></td>
			<td></td>	
			<td></td>
			<td colspan="2" style="text-align:right;"><span class="layui-btn" lay-submit lay-filter="sureAdd" >确定新增</span>
							<span class="layui-btn layui-btn-danger" id='resetAddOrder' >清空数据</span> </td></tr>
	</table>
	<table class="layui-table" id="productListTable" lay-filter="productListTable"></table>
</div>
<!-- 商品选择隐藏框 -->
<div id="productChooseDiv" style="display:none;">
	<table class="layui-form layui-table" lay-size="sm" lay-skin="nob"  style="width:60%;">
		<tr>
			<td><select><option value="1">按商品名称查找</option></select></td>			
			<td><input type="text" class="layui-input" name="commodityName" placeholder="请输入查找的信息"></td>				
			<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" >搜索</button>				
				<button type="button" class="layui-btn layui-btn-sm" id="sure" >确定添加</button></td>
		</tr>
	</table>
	<table class="layui-table" id="productChooseTable" lay-filter="productChooseTable"></table>
</div>
<!-- 商品列表表格工具栏 -->
<script type="text/html" id="productListTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增商品</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除商品</span>
</div>
</script>
<!-- 入库单表格工具栏 -->
<script type="text/html" id="needleOrderTableToolbar" >
<div  class="layui-button-container">
	<span lay-event="addNeedle"  class="layui-btn layui-btn-sm" >新增针工单</span>
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
	['tablePlug','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, tablePlug = layui.tablePlug;
		
		var allUser=[],
		    currUser={id:''};      
		
		var searchBatchNumber = '';			//记录搜索使用的数据，用于过滤筛选子单数据
		var searchCommodityName = '';
		
		getAllUser();
		getCurrUser();
		
		form.render();
		table.render({				//渲染主页面入库单表格
			elem:'#needleOrderTable',
			url:'${ctx}/inventory/procurementPage?type=1&flag=0',
			toolbar:'#needleOrderTableToolbar',
			page:{},
			request:{ pageName:'page',limitName:'size' },
			parseData:function(ret){ return {data:ret.data.rows,count:ret.data.total,msg:ret.message,code:ret.code}},
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'单据编号',   	field:'documentNumber',width:'10%',	},
			       {align:'center', title:'计划数量', field:'number', width:'4%',},
			       {align:'center', title:'剩余数量', field:'residueNumber', width:'4%',},
			       {align:'center', title:'经手人',	templet:'<p>{{ d.user.userName }}</p>',width:'4%',},
			       {align:'center', title:'是否反冲', 	field:'flag', templet:'#flagTpl',width:'4%',},
			       {align:'center', title:'日期',   	field:'createdAt',width:'9%',	},
				   {align:'center', title:'批次号',	  templet: orderContent('batchNumber'),   width:'10%'	,},
				   {align:'center', title:'商品名',	  templet: orderContent('skuCode'),		  width:'18%'	,},
				   {align:'center', title:'计划数量',  templet: orderContent('number'),		  width:'4%',	},
				   {align:'center', title:'剩余数量',  templet: orderContent('residueNumber'), width:'4%'	,},
				   {align:'center', title:'备注', 	  templet: orderContent('childRemark'),   	}
			       ]]
		})
		function orderContent(field){
			return function(d){
				var html = '<table style="width:100%;" class="layui-table">';
				for(var i=0;i<d.procurementChilds.length;i++){
					var t=d.procurementChilds[i];
					var style = 'border-right:none;';
					if(t.batchNumber.indexOf(searchBatchNumber)==-1 || t.commodity.skuCode.indexOf(searchCommodityName)==-1)
						continue;
					if(i==d.procurementChilds.length-1)
						style += 'border-bottom:none;';
					if(field == 'childRemark')
						style += 'text-align:left;';
					html+='<tr><td style="'+style+'">'+ (field == 'skuCode' ? t.commodity[field] : t[field]) +'&nbsp;</td></tr>';
				}
				return html+'</table>';
			}
		}
		
		table.on('toolbar(needleOrderTable)',function(obj){	//监听入库单表格按钮
			switch(obj.event){
			case 'delete':		deletes();		break;
			case 'becomeEntry':becomeEntry(); break;
			case 'addNeedle':	addNeedle();	break;
			}
		})
		
		table.on('rowDouble(needleOrderTable)',function(obj){
			lookover(obj.data);
		})
		form.on('submit(search)',function(obj){
			obj.field.batchNumber = obj.field.batchNumber.trim();
			obj.field.commodityName = obj.field.commodityName.trim();
			searchBatchNumber = obj.field.batchNumber;
			searchCommodityName = obj.field.commodityName;
			table.reload('needleOrderTable',{
				where:obj.field,
				page: {  curr: 1   },
				url:'${ctx}/inventory/procurementPage?type=1',
			})
		})
		//--------------新增针工单------------
		laydate.render({ elem:'#addCreatedAt', type:'datetime' });
		var choosedProduct=[];			//用户已经选择上的产品
		function addNeedle(){
			layer.open({
				type : 1,
				title : '新增针工单',
				offset:'30px',
				area : ['90%','90%'],
				content : $('#addOrderDiv')
			})
			getUserSelect(currUser.id,'userIdSelectAdd');
			table.render({									//渲染选择后的商品表格
				elem:'#productListTable',
				toolbar:'#productListTableToolbar',
				page:{},
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'批次号',   field:'batchNumber',style:'color:blue', },
				       {align:'center', title:'商品名称', field:'skuCode',},
				       {align:'center', title:'数量',     field:'number', edit:true,style:'color:blue', },
				       {align:'center', title:'备注',  	  field:'childRemark', edit:true,style:'color:blue', }, 
				       ]]
			})
			table.reload('productListTable',{ data : choosedProduct });
		}
		table.on('toolbar(productListTable)',function(obj){		//监听选择商品表格的工具栏按钮
			switch(obj.event){
			case 'add': openChooseProductWin(); break;
			case 'delete':deleteChoosedProduct();break;
			}
		})
		table.on('edit(productListTable)', function(obj){ 			//监听编辑表格单元
			if(obj.field=='number'){
				var msg='';
				(isNaN(obj.value)) && (msg="修改无效！请输入正确的数字");
				(obj.value=='') && (msg='计划的数量不能为空');
				(obj.value<0) && (msg='计划的数量不能小于0');
				(obj.value%1 !== 0) && (msg='计划的数量必须为整数');
				for(var i=0;i<choosedProduct.length;i++){
					if(choosedProduct[i].id==obj.data.id){		
						if(msg!=''){
							layer.msg('"'+obj.value+'" 为非法输入！'+msg,{icon:2,offset:'100px',});
							$(this).val(1)
							choosedProduct[i].number=1;
							return;
						}
						$('#addNumber').val($('#addNumber').val()-choosedProduct[i].number-(-parseInt(obj.value)));
						$(this).val(parseInt(obj.value))
						choosedProduct[i].number=parseInt(obj.value);
						return;
					}
				}
			}else{
				for(var i=0;i<choosedProduct.length;i++){
					 if(choosedProduct[i].id==obj.data.id){		//重新对该行的相关数据进行计算
						if(obj.field=='childRemark')
						 	choosedProduct[i].childRemark=obj.data.childRemark;
						else if(obj.field=='batchNumber')
							choosedProduct[i].batchNumber=obj.data.batchNumber;
					 	layer.msg('修改成功！',{icon:1,offset:'100px',});
					 	break;
					}
				}
			}
		});
		form.on('submit(sureAdd)',function(obj){
			var data=obj.field;
			if(choosedProduct.length==0){
				layer.msg("请选择商品",{icon:2,offset:'100px',});
				return;
			}
			var child=[];
			for(var i=0;i<choosedProduct.length;i++){
				if(choosedProduct[i].batchNumber.replace(/(^\s*)|(\s*$)/g, '')=='')
					return layer.msg('商品批次号不可省略！',{icon:2,offset:'100px',});
				for(var j=0;j<choosedProduct.length;j++){
					var item = choosedProduct[j];
					if( item.id != choosedProduct[i].id && item.commodityId == choosedProduct[i].commodityId && item.batchNumber == choosedProduct[i].batchNumber)
						return layer.msg('相同的商品不能同时使用相同的批次号！',{icon:2,offset:'100px',});
				}
				child.push({batchNumber : choosedProduct[i].batchNumber.replace(/(^\s*)|(\s*$)/g, ''),
							productId : choosedProduct[i].commodityId,
							number : choosedProduct[i].number,
							childRemark : choosedProduct[i].childRemark});
			}
			data.commodityNumber=JSON.stringify(child);			//子列表商品
			var load = layer.load(1);
			$.ajax({
				url:"${ctx}/inventory/addProcurement",
				type:"post",
				data:data,			
				success:function(result){
					var icon = 2;
					if(0==result.code){
						icon = 1;
						$('#resetAddOrder').click();
						layer.closeAll();
						table.reload('needleOrderTable');
					}
					layer.msg(result.message,{icon:icon,offset:'100px',});
				}
			})
			layer.close(load);
		}) 
		//选择商品隐藏框的按钮监听.添加商品弹窗共4个按钮监听。搜索、添加新商品、确定添加
		$('#sure').on('click',function(){	
			if(sureChoosed())											//如果选择成功
				layer.close(chooseProductWin);							
		})
		
		$('#resetAddOrder').on('click',function(){		//此处如果加confirm提示。则新增成功时无法清空
			$('#addRemark').val('');
			$('#addCreatedAt').val('');
			$('#addNumber').val(0);
			choosedProduct=[];	
			getUserSelect(currUser.id,'userIdSelectAdd');
			table.reload('productListTable',{ data:choosedProduct })
		})
		form.on('submit(searchProduct)',function(obj){
			table.reload('productChooseTable',{
				where:obj.field,
				page: {  curr: 1   }
			})
		})
		function deleteChoosedProduct(){								//删除商品
			var choosed = layui.table.checkStatus('productListTable').data;
			if(choosed.length==0){
				layer.msg("请选择商品删除",{icon:2,offset:'100px',});
				return;
			}
			for(var i=0;i<choosed.length;i++){
				for(var j=0;j<choosedProduct.length;j++){
					if(choosed[i].id==choosedProduct[j].id){
						$('#addNumber').val($('#addNumber').val()-choosedProduct[j].number);
						choosedProduct.splice(j,1);
						break;
					}
				}
			}
			table.reload('productListTable',{ data:choosedProduct, })
		}
		function openChooseProductWin(){					//商品选择隐藏框
			chooseProductWin = layer.open({		
				type:1,
				title:'选择产品',
				offset:'60px',
				area:['80%','80%'],
				content:$('#productChooseDiv'),
			})
			table.render({
				elem:'#productChooseTable',
				url:'${ctx}/inventory/procurementProPage?type=0',
				page:true,
				request:{ pageName:'page', limitName:'size' },
				parseData:function(ret){  return{ code:ret.code, msg:ret.message, data:ret.data.rows, count:ret.data.total,}},
				cols:[[
				       {type:'checkbox', align:'center', fixed:'left'},
				       {align:'center', title:'批次号', field:'batchNumber',width:'16%'},
				       {align:'center', title:'商品名称', field:'productName', templet:'<span>{{ d.commodity.name }}</span>'},
				       {align:'center', title:'计划数量', field:'number',width:'8%'},
				       {align:'center', title:'剩余数量', 	  field:'residueNumber',width:'8%'},
				       {align:'center', title:'备注', 	  field:'childRemark',}, 
				      ]],
			});
			form.render();
		}
		var choosedId=0;	//由于可以选择不同的商品进行不同的批次号选择，因此原本的商品id无法作为唯一标识，因此相同的商品可能多次选择，因此需要添加一个字段作为标识
		function sureChoosed(){					//确定商品选择
			var choosed=layui.table.checkStatus('productChooseTable').data;
			if(choosed.length<1){
				layer.msg("请选择相关商品",{icon:2,offset:'100px',});
				return false;
			}
			for(var i=0;i<choosed.length;i++){
				var orderChild={
						skuCode : choosed[i].commodity.name ,			//商品名称
						commodityId : choosed[i].id,			//商品id
						number : 1,								//商品数量
						cost : choosed[i].cost,					//成本
						childRemark : choosed[i].childRemark,				//备注
						batchNumber : choosed[i].batchNumber,
						id : choosedId++,  						//仅仅用于标识不同的数据
				};
				$('#addNumber').val($('#addNumber').val()-(-1));
				choosedProduct.push(orderChild);
			}
			table.reload('productListTable',{ data:choosedProduct });
			layer.msg('添加成功',{icon:1,offset:'100px',});
			return true;
		}
		function openAddNewPorductWin(){		//添加新产品窗口
			addNewPorductWin = layer.open({								
				type:1,
				title:'添加产品',
				offset:'100px',
				content:$('#addNewProductWin'),
				area:['60%','60%']
			})
			form.render();
		}
		//------------反冲针工单----------------------------
		function deletes(){						//反冲针工单表格
			var choosed=layui.table.checkStatus('needleOrderTable').data;
			if(choosed.length<1){
				layer.msg('请选择生产单',{icon:2,offset:'100px',});
				return;
			}
			layer.confirm('是否确认反冲？',{offset:'100px',},function(){
				var ids='';
				for(var i=0;i<choosed.length;i++)
					ids+=(choosed[i].id+',');
				var load=layer.load(1);
				$.ajax({
					url:'${ctx}/inventory/deleteProcurement?ids='+ids,
					success:function(result){
						if(0==result.code){
							table.reload('needleOrderTable');
							layer.msg(result.message,{icon:1,offset:'100px',});
						}else
							layer.msg(result.message,{icon:2,offset:'100px',});
						layer.close(load);
					}
				})
			})
		}
		//-------查看针工单功能--------------------
		function lookover(data){
			if(data.type==undefined)				//防止空数据弹窗bug，双击详细内容时
				return;
			layer.open({
				type : 1,
				title : '查看针工单',
				offset:'30px',
				area : ['90%','90%'],
				content : $('#lookoverOrderDiv')
			})
			table.render({									//渲染选择后的商品表格
				elem:'#lookOverProductListTable',
				data:data.procurementChilds,
				page:{},
				cols:[[
					   {align:'center', title:'批次号',   	field:'batchNumber',	},
				       {align:'center', title:'商品名称',  templet:'<p>{{ d.commodity.skuCode }}</p>'},
				       {align:'center', title:'数量',     field:'number',},
				       {align:'center', title:'剩余数量', field:'residueNumber'},
				       {align:'center', title:'备注', 	  field:'childRemark',}, 
				       ]]
			})
			$('#look_createdAt').val(data.createdAt);
			$('#look_remark').val(data.remark);
			$('#look_number').val(data.number);
			$('#look_user').val(data.user.userName);
		}
		function getAllUser(){
			$.ajax({
				url:'${ctx}/system/user/pages?size=999&quit=0',
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
		function getUserSelect(id,select){
			var html='';
			for(var i=0;i<allUser.length;i++){
				var selected=( id==allUser[i].id?'selected':'' );
				html+='<option value="'+allUser[i].id+'" '+selected+'>'+allUser[i].userName+'</option>';
			}
			$('#'+select).html(html);
			form.render();
		}
		
		function getCurrUser(){
			$.ajax({
				url:'${ctx}/getCurrentUser',
				success:function(r){
					if(0==r.code)
						currUser = r.data;
				}
			})
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