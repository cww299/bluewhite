<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/static/js/vendor/jquery.cookie.js"></script>
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
				<td><select id="customIdSelect" lay-search><option value="">获取数据中</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" id='search'>搜索</button></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="layui-badge">双击查看客户的购买详情</span></td>
			</tr>
		</table>
		<table class="layui-form" id="customReport" lay-filter="customReport"></table>
	</div>
</div>
</body>
<!-- 查看销售详情隐藏框 -->
<div style="display:none;" id="lookoverDiv" >
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
		
		function p(s) { return s < 10 ? '0' + s: s; }
		var myDate = new Date();
		var year=myDate.getFullYear();
		var month=myDate.getMonth()+1;
		var day = new Date(year,month,0);
		var firstdate = year + '-' + p(month) + '-01'+' '+'00:00:00';
		var lastdate = year + '-' + p(month) + '-' + day.getDate() +' '+'23:59:59';
		
		getAllCustom();
		form.render();
	 	laydate.render({ 
	 		elem:'#time', 
	 		type: 'datetime',
	 		range:'~',
	 		value:firstdate+' ~ '+lastdate,
	 	}) 
		$('#search').on('click',function(){
			var time=$('#time').val();
			if(time==''){
				layer.msg('请输入查找时间',{icon:2});
				return;
			}
			var t=time.split('~');
			table.reload('customReport',{
				url:'${ctx}/inventory/report/salesUser?report=4',
				where:{
					orderTimeBegin : t[0],
					orderTimeEnd : t[1],
					onlineCustomerId : $('#customIdSelect').val(),
				}
			})
		})
		table.render({
			url:'${ctx}/inventory/report/salesUser?report=4',
			where:{
				orderTimeBegin : firstdate,
				orderTimeEnd : lastdate,
			},
			elem:'#customReport',
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
		table.on("rowDouble(customReport)",function(obj){
			layer.open({
				type:1,
				title:obj.data.user,
				area:['80%','80%'],
				content:$('#lookoverDiv'),
				shadeClose:true,
			})
			table.render({
				url : '${ctx}/inventory/report/salesUserDetailed?onlineCustomerId='+obj.data.userId,
				elem : '#lookoverTable',
				size : 'sm',
				page : true,
				request:{ pageName:'page', limitName:'size' },
				parseData:function(ret){ return {  msg:ret.message,  code:ret.code , data:ret.data.rows, count:ret.data.total, } },
				cols:[[
						{align:'center', title:'日期',   		field:'createdAt',	width:'11%',},
						{align:'center', title:'单据编号',   	templet:'<span>{{ d.onlineOrder.documentNumber }}</span>', 	width:'12%',},
						{align:'center', title:'运单号',   		templet:'<span>{{ d.onlineOrder.trackingNumber }}</span>', 	width:'8%',},
						{align:'center', title:'商品名称', 		templet:'<span>{{ d.commodity.skuCode }}</span>', 	},
						{align:'center', title:'商品数量', 		field:'number', width:'6%', 	},
						{align:'center', title:'商品单价', 		field:'price',	width:'6%', },
						{align:'center', title:'商品总价', 		field:'sumPrice', 	width:'6%', },
						{align:'center', title:'仓库名称',   	templet:'<span>{{ d.warehouse.name }}</span>',	width:'7%',},
						{align:'center', title:'客户名称',   	templet:'<span>{{ d.onlineOrder.onlineCustomer.name }}</span>',	width:'7%',},
						{align:'center', title:'经手人',   		templet:'<span>{{ d.onlineOrder.user.userName }}</span>',	width:'6%',},
				       ]]
			}) 
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
	 	
	 	//-----------------------cookie保存筛选列-----------------
		var COOKIENAME = 'saleManagerCustomReportCookie';		//变量名，存放的cookie名
	 	readCookie();
	 	function readCookie(){							//读取cookie值并，设置筛选的字段
	 		var hideField = $.cookie(COOKIENAME);		//读取cookie
	 		if(hideField==undefined || hideField=='')	
	 			return;
	 		$('.layui-icon-cols').click();    			//打开筛选列
	 		var panel = $('.layui-table-tool-panel');	//筛选面板
	 		
	 		var hf = hideField.split(',');
	 		for(var i=0;i<hf.length;i++){
	 			panel.find('.layui-form-checkbox')[hf[i]].click();
	 		}
	 		$('.layui-table').click(); 					//关闭筛选列，随便点击一个地方即关闭
	 	}
	 	//$.removeCookie(COOKIENAME); 
	 	function saveCookie(event){						//保存用户筛选的设置
	 		if($.cookie(COOKIENAME)==undefined)
	 			$.cookie(COOKIENAME,'');
	 		var panel = $('.layui-table-tool-panel');				//筛选面板
	 		var leng = panel.find('.layui-form-checkbox').length-1;	//获取所有字段的总数
	 		var key='';												//根据点击的不同位置获取点击的索引,可点击div span i
	 		if(event.target.localName=='i')
	 			key = $(event.target).parent().parent().children("input:first-child").attr('data-key')
	 		else if(event.target.localName=='span')
	 			key = $(event.target).parent().parent().children("input:first-child").attr('data-key')
	 		else if(event.target.localName=='div')
	 			key = $(event.target).parent().children("input:first-child").attr('data-key');
	 		var hideField = $.cookie(COOKIENAME);
 			if(key==undefined){							//点击全选的时候
 				var hf=[];
 				if(hideField=='')
 					for(var i=0;i<leng;i++)
 						hf.push(i-(-1));				//第一个复选框为全选，因此需要加1
 				$.cookie(COOKIENAME, hf.join(','));
 			}else{
 				var hf = hideField==''?[]:hideField.split(',');			//
 				var i;
 				var index = (key.split('-'))[1]-(-1)
	 			for(i=0;i<hf.length;i++)
	 				if(hf[i] == index)
	 					break;
	 			if(i<hf.length)
	 				hf.splice(i,1);
	 			else
	 				hf.push(index);
	 			$.cookie(COOKIENAME, hf.join(','));
 			}
	 	}
	 	$(document).on('mousedown', '.layui-unselect', function (event) { 
	 		saveCookie(event);
	   	});
	 	//----------------------cookie结束----------------------------
	}//end define function
)//endedefine
</script>
</html>