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
<title>商品销售报表</title>
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
				<td>商品名称:&nbsp;&nbsp;</td>
				<td><input type='text' id='skuCode' class='layui-input' placeholder='请输入查询信息'></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" id='search'>搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="dayReport" lay-filter="dayReport"></table>
	</div>
</div>
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
		
		form.render();
	 	laydate.render({
			elem:'#time',
			type: 'datetime',
			range:'~'
		}) 
		$('#search').on('click',function(){
			var time=$('#time').val();
			var t=time.split('~');
			table.reload('dayReport',{
				url:'${ctx}/inventory/report/salesGoods',
				where:{
					orderTimeBegin : t[0],
					orderTimeEnd : t[1],
					skuCode : $('#skuCode') .val()
				}
			})
		})
		table.render({
			elem:'#dayReport',
			loading:true,
			size:'sm',
			toolbar: true,
			request:{ pageName:'page', limitName:'size' },
			totalRow:true,
			parseData:function(ret){
				return {  
					msg:ret.message, 
					code:ret.code ,
					data:ret.data,
					} },
			cols:[[
			       {align:'center', title:'商品名称',   totalRowText: '合计', field:'name',	 },
			       {align:'center', title:'成交单数',   field:'singular',   totalRow:true,},
			       {align:'center', title:'总金额', 	    field:'sumPayment', totalRow:true,	},
			       {align:'center', title:'总数量',   field:'sunNumber',	   totalRow:true,},
			       {align:'center', title:'每单平均金额',   field:'averageAmount',	totalRow:true,},
			       ]]
		})
		//-----------------------cookie保存筛选列-----------------
		var COOKIENAME = 'saleGoodReportCookie';		//变量名，存放的cookie名
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