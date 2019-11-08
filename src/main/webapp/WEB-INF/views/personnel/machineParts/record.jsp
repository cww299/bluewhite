<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>出入记录</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>配件名:</td>
				<td><input type="text" name="name" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>时间:</td>
				<td><input type="text" id="searchTime" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>部门:</td>
				<td><select id="orgNameId"  name="orgNameId"><option value="">请选择</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>出入库:</td>
				<td style="width:100px;"><select name="flag"><option value="">请选择</option>
										<option value="0">出库</option>
										<option value="1">入库</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>备注:</td>
				<td><input type="text" name="remark" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script type="text/html" id="partTpl">
<div  style="width:50%;float:left;">
	<table>
		<tr>
			<td>&nbsp;&nbsp;</td>
			<td>时间：</td>
			<td>&nbsp;&nbsp;</td>
			<td><input type="text" class="layui-input" id="partTime">
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-btn" id="searchBtn">搜索</span></td>
		</tr>
	</table>
	<table id="partTable" lay-filter="partTable"></table>
</div>
<div id="pieChart" style="width:50%;float:left;height:90%;"></div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
	echarts : 'layui/myModules/echarts/echarts' ,
}).define(
	['mytable'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		var echarts;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		
		laydate.render({ elem:'#searchTime', range:'~', });
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/personnel/getInventoryDetail?type=2',
			curd:{
				btn:[4],
				otherBtn:function(obj){
					if(obj.event=="partPrice"){
						layer.open({
							type:1,
							title:'部门分摊',
							area:['90%','70%'],
							shadeClose:true,
							content: $('#partTpl').html(),
							success:function(){
								var firstDay = myutil.getSubDay(0,'yyyy-MM-01'),
									nowDay = myutil.getSubDay(0,'yyyy-MM-dd');
								laydate.render({
									elem:'#partTime',range:'~', value: firstDay+' ~ '+nowDay,
								})
								mytable.renderNoPage({
									elem:'#partTable',
									url:'${ctx}/personnel/statisticalInventoryDetail?type=2',
									where:{
										orderTimeBegin: firstDay+' 00:00:00',
										orderTimeEnd: nowDay+' 23:59:59',
									},
									totalRow:['sumCost','accounted'],
									height:'450',
									cols:[[
									       { field:'orgName', title:'部门', },
									       { field:'sumCost', title:'部门费用', },
									       { field:'accounted', title:'占比', },
									       ]],
									done:function(obj){
										var data = obj.data,allOrg = [],allData = [];
										var pieCharts = echarts.init(document.getElementById('pieChart'));
										for(var i=0,len=data.length;i<len;i++){
											allOrg.push(data[i].orgName);
											allData.push({
												name: data[i].orgName,
												value: data[i].sumCost,
											})
										}
										pieCharts.setOption({
											title: {		
								                text: '部门费用占比饼状图', x:'center',
								            },
								            legend: {		
										        orient: 'vertical', x: 'left',
										        data: allOrg,
										    },
										    toolbox: {
									            show: true, left: 'right', top: 'top',
									            feature: {
									                restore: {},
									                saveAsImage: {},
									            }
									        },
								            tooltip: {
								            	trigger: 'item',
								        		formatter: "{a} <br/>{b} : {c}/元 (占比：{d}%)",  
								            }, 
										    series: [
										        {
										            name: '部门费用占比',
										            selectedMode: 'single', 
										            type: 'pie',
										            data: allData,
										        }
										    ]
										})
									}
								})
								$('#searchBtn').unbind().on('click',function(){
									var val = $('#partTime').val();
									if(val=='')
										return myutil.emsg('请输入搜索时间');
									val = val.split(' ~ ');
									table.reload('partTable',{
										where:{
											orderTimeBegin: val[0].trim()+' 00:00:00',
											orderTimeEnd: val[1].trim()+' 23:59:59',
										},
									})
								})
							}
						})
					}
				},
			},
			autoUpdate:{
				deleUrl:'/personnel/deleteInventoryDetail',
			},
			ifNull:'--',
			totalRow:['number','outboundCost'],
			limits:[10,50,100,200,500,1000],
			toolbar:'<span class="layui-btn layui-btn-sm" lay-event="partPrice">部门分摊费用</span>',
			cols:[[
			       { type: 'checkbox', fixed: 'left', },
			       { field: "time", title: "时间", },
			       { field: "officeSupplies_name", title: "物品名", },
			       { field: "officeSupplies_price", title: "单价", },
			       { field: "flag", title: "出入库", transData:{data:['出库','入库']}  },
			       { field: "number",  title: "数量", },
			       { field: "outboundCost", title: "领用价值", },
			       { field: "user_userName", title: "领取人", },
			       { field: "orgName_name", title: "部门", },
			       { field: "remark", title: "备注", },
			       ]]
		})
		layui.use('echarts',function(){	//页面渲染结束后再进行echarts加载。
			echarts = layui.echarts;
		});  
		myutil.getData({
			url:'${ctx}/basedata/list?type=orgName',
			success:function(d){ 
				var html = '';
				for(var i=0,len=d.length;i<len;i++){
					html += '<option value="'+d[i].id+'">'+d[i].name+'</option>';
				}
				$('#orgNameId').append(html);
				form.render();
			},
		})
		
		form.on('submit(search)',function(obj){
			var t = $('#searchTime').val();
			if(t!=''){
				t = t.split(' ~ ');
				t[0] = t[0].trim()+' 00:00:00';
				t[1] =  t[1].trim()+' 00:00:00';
			}else
				t = ['',''];
			obj.field.orderTimeBegin = t[0];
			obj.field.orderTimeEnd =  t[1];
			table.reload('tableData',{
				where:obj.field,
				page:{curr:1},
			})
		})
	}//end define function
)//endedefine
</script>
</html>