<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户销售报表</title>
<style>
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>查询时间:&nbsp;&nbsp;</td>
				<td><input type='text' id='time' class='layui-input' style='width:350px;' placeholder='请输入查询时间'></td>
				<td>&nbsp;&nbsp;</td>
				<td><select id="customIdSelect"><option value="">获取数据中</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" id='search'>搜索</button></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-badge">双击查看客户的购买详情</span></td>
			</tr>
		</table>
		<table class="layui-form" id="dayReport" lay-filter="dayReport"></table>
	</div>
</div>
</body>
<!-- 查看销售详情隐藏框 -->
<div style="display:none;padding:20px;" id="lookoverDiv" >
	<label>销售人员销售明细：</label>
	<table class="layui-table" id="lookoverTable" lay-filter="lookoverTable"></table>
</div>

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
		
		getAllCustom();
		form.render();
	 	laydate.render({ elem:'#time', type: 'datetime', range:'~' }) 
		$('#search').on('click',function(){
			var time=$('#time').val();
			if(time==''){
				layer.msg('请输入查找时间',{icon:2});
				return;
			}
			var t=time.split('~');
			table.reload('dayReport',{
				url:'${ctx}/inventory/report/salesUser?report=4',
				where:{
					orderTimeBegin : t[0],
					orderTimeEnd : t[1],
					customId : $('#customIdSelect').val(),
				}
			})
		})
		table.render({
			elem:'#dayReport',
			loading:true,
			size:'sm',
			toolbar: true,
			totalRow:true,
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return {  msg:ret.message,  code:ret.code , data:ret.data, } },
			cols:[[
			       {align:'center', title:'客户',   totalRowText: '合计', field:'user',	},
			       {align:'center', title:'成交单数',   field:'singular',  totalRow:true, },
			       {align:'center', title:'宝贝数量', 	field:'proNumber', totalRow:true,	},
			       {align:'center', title:'成交金额',   field:'sumPayment',	totalRow:true,},
			       {align:'center', title:'实际运费',   field:'sumpostFee',	totalRow:true,style:"color:blue;"},
			       {align:'center', title:'每单平均金额',   field:'averageAmount',totalRow:true,	},
			       ]]
		})
		table.on("rowDouble(userReport)",function(obj){
			layer.open({
				type:1,
				title:obj.data.user,
				content:$('#lookoverDiv'),
				shadeClose:true,
			})
			/* table.render({
				url : '${ctx}/inventory/onlineOrderPage?userId='+obj.data.userId,
				elem : 'lookoverTable',
				size : 'sm',
				page : true,
				request:{ pageName:'page', limitName:'size' },
				parseData:function(ret){ return {  msg:ret.message,  code:ret.code , data:ret.data, } },
				cols:[[
				       {align:'center', title:'日期',   		field:'createdAt',	},
				       {align:'center', title:'单据编号',   	field:'documentNumber', 	},
				       {align:'center', title:'商品名称', 	field:'', 	},
				       {align:'center', title:'仓库名称',   	field:'',	},
				       {align:'center', title:'客户名称',   	field:'',	},
				       {align:'center', title:'经手人',   	field:'',	},
				       ]]
			}) */
		})
		var allCustom=[];
		function getAllCustom(){
			$.ajax({
				url:'${ctx}/inventory/onlineCustomerPage',
				success:function(r){
					if(0==r.code){
						for(var i=0;i<r.data.rows.length;i++)
							allCustom.push({
								id:			r.data.rows[i].id,
								userName:	r.data.rows[i].name
							})
							renderCustomSelect('customIdSelect');
					}
				}
			})
		}
	 	function renderCustomSelect(select){			//根据id渲染客服下拉框
			var html='<option value="">客户</option>';
			for(var i=0;i<allCustom.length;i++)
				html+='<option value="'+allCustom[i].id+'">'+allCustom[i].userName+'</option>';
			$('#'+select).html(html);
			form.render();
		}
	}//end define function
)//endedefine
</script>
</html>