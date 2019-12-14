/**2019/12/13
 * author: 299
 * 出入库记录模块 record
 * type: 1、办公用品  2、机械配件  3、食材库存
 * elem: '', 容器
 * record({
 * 		type: 1,
 * })
 */
layui.extend({
	echarts : 'layui/myModules/echarts/echarts' ,
}).define(['jquery','layer','form','laydate','mytable','table',],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		laytpl = layui.laytpl,
		table = layui.table,
		mytable = layui.mytable,
		myutil = layui.myutil;
	
	var TPL_MAIN = `
		<table class="layui-form">
			<tr>
				<td>物品名:</td>
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
	`;
	var PART_TPL = `
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
	<div id="pieChart" style="width:50%;float:left;height:100%;"></div>
	`;
	var CPMPARE_TPL = `
		<div  style="padding:10px;">
			<table class="layui-form searchTable">
				<tr>
					<td>输入对比月份：</td>
					<td><input type="text" class="layui-input" id="firstTime" lay-verify="required">
					<td><input type="text" class="layui-input" id="secondTime" lay-verify="required">
					<td><span class="layui-btn" lay-submit lay-filter="comparyBtn">搜索</span></td>
				</tr>
			</table>
		</div>
		<div id="comparyDiv" style="width:100%;float:left;height:100%;"></div>
	`;
	
	var record = {
			type: 1,  //默认食材
	};
	
	record.render = function(opt){
		$(opt.elem || '#app').html(TPL_MAIN);
		laydate.render({ elem:'#searchTime', range:'~', })
		form.render();
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/personnel/getInventoryDetail?type='+record.type,
			curd:{
				btn:[4],
				otherBtn:function(obj){
					if(obj.event=="partPrice"){
						partPrice();
					}else if(obj.event=='comparyPrice'){
						comparyPrice();
					}
				},
			},
			autoUpdate:{
				deleUrl:'/personnel/deleteInventoryDetail',
			},
			ifNull:'--',
			totalRow:['number','outboundCost'],
			limits:[10,50,100,200,500,1000],
			toolbar: record.type==3?'<span class="layui-btn layui-btn-sm" lay-event="comparyPrice">费用对比</span>':
					'<span class="layui-btn layui-btn-sm" lay-event="partPrice">部门分摊费用</span>',
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
			url: myutil.config.ctx+'/basedata/list?type=orgName',
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
	}
	
	function partPrice(){
		layer.open({
			type:1,
			title:'部门分摊',
			area:['90%','70%'],
			shadeClose:true,
			content: PART_TPL,
			success:function(){
				var firstDay = myutil.getSubDay(0,'yyyy-MM-01'),
					nowDay = myutil.getSubDay(0,'yyyy-MM-dd');
				laydate.render({
					elem:'#partTime',range:'~', value: firstDay+' ~ '+nowDay,
				})
				mytable.renderNoPage({
					elem:'#partTable',
					url: myutil.config.ctx+'/personnel/statisticalInventoryDetail?type='+record.type,
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
	function comparyPrice(){
		layer.open({
			type:1,
			title:'费用对比',
			area:['90%','70%'],
			shadeClose:true,
			content: CPMPARE_TPL,
			success:function(){
				var thisMonth = myutil.getSubDay(0,'yyyy-MM'),
					lastMonth = myutil.getLastMonth();
				laydate.render({ elem:'#firstTime', value: lastMonth, type:'month', });
				laydate.render({ elem:'#secondTime', value: thisMonth, type:'month', });
				renderEchart(thisMonth,lastMonth);
				
				form.on('submit(comparyBtn)',function(obj){
					renderEchart($('#firstTime').val(),$('#secondTime').val())
				})
				
				function renderEchart(t1,t2){
					var d1 = getDetailData(t1);
					var d2 = getDetailData(t2);
					var comparyDiv = echarts.init(document.getElementById('comparyDiv'));
					return
					comparyDiv.setOption({
						title:{ text:'食材费用月份对比图', },
					    tooltip:{  trigger: 'axis', },	
					    legend: {  data:['年龄','男','女']  },
					    toolbox: {	
					        show: true,
					        feature: {
					            dataZoom: {
					                yAxisIndex: 'none'
					            },
					            dataView: {readOnly: false},
					            magicType: {type: ['line', 'bar']},
					            restore: {},
					            saveAsImage: {}
					        }
					    },
					    xAxis: {
					        type: 'category',
					        boundaryGap: false,
					        data: [],
					    },
					    yAxis: {
					        type: 'value',
					        axisLabel: {
					            formatter: '{value} /'
					        }
					    },
					    series: [
					    	{
					    		name:'男',
						        data: [],
						        type: 'line',
					    	},
					    	{
					    		name:'女',
						        data: [],
						        type: 'line',
					    	},
					    ]
					})
				}
			}
		})
	}
	function getDetailData(t){
		return myutil.getDataSync({
			url: myutil.config.ctx+'/personnel/ingredientsStatisticalInventoryDetail',
			data:{
				type:3,
				orderTimeBegin: t+'-01 00:00:00',
			},
		})
	}
	exports('record',record);
})