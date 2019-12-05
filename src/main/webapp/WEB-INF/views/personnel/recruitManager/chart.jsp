<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在职员工信息统计</title>
<style>
	.layui-row>div{
		height:300px;
	}
</style>
</head>
<body>
	<div class="layui-card" >
		<div class="layui-card-body">
		  <div class="layui-row layui-col-space10">
		  	<!-- <div class="layui-col-md4" id="safe" style="height:500px;"></div>
		  	<div class="layui-col-md4" id="quit" style="height:500px;"></div> -->
		  	<div class="layui-col-md4" style="height:50px;" id="date3"></div>
		    <div class="layui-col-md12" id="education" style="height:500px;"></div>
		    <div class="layui-col-md4" style="height:50px;" id="date4"></div>
		    <div class="layui-col-md12" id="orgName" style="height:500px;"></div>
		    <div class="layui-col-md4" style="height:50px;" id="date5"></div>
		    <div class="layui-col-md12" id="user" style="height:500px;"></div>
		    <div class="layui-col-md4" style="height:50px;" id="date2"></div>
		    <div class="layui-col-md12" id="orgQuit" style="height:600px;"></div>
		    <!-- <div class="layui-col-md12" id="entryQuit" style="height:500px;"></div> -->
		  </div>
		</div> 
	</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	echarts : 'layui/myModules/echarts/echarts',
	china : 'layui/myModules/echarts/china',
	mytable : 'layui/myModules/mytable',
}).define(
		[ 'echarts','china','mytable','laydate'],
		function() {
			var $ = layui.jquery
			, layer = layui.layer
			, myutil = layui.myutil
			, mytable = layui.mytable
			,laydate = layui.laydate 
			,form = layui.form //表单
			, echarts = layui.echarts;
			function p(s) { return s < 10 ? '0' + s: s; }
			var myDate = new Date();
			var date=myDate.getDate(); 
			var year=myDate.getFullYear();
			var month=myDate.getMonth()+1;
			var day = new Date(year,month,0);
			var x;
			if(date<10){
				x="0"
			}else{
				x=""
			}
			var firstdate = year + '-' + p(month) + '-01'+' '+'00:00:00';
			var lastdate = year + '-' + p(month) + '-' + day.getDate() +' '+'23:59:59';
			var month = year + '-' + p(month);
			$("#date3").html("<table class='layui-form'><tr><td><input id='startTime3' style='width: 320px;' name='startTime3'   class='layui-input laydate-icon'></td><td>  <select class='form-control' lay-filter='lay_selecte' id='selectOne'><option value='1'>邀约面试</option><option value='2'>应邀面试</option><option value='3'>面试合格</option><option value='4'>拒绝入职</option><option value='5'>已入职且在职</option><option value='6'>即将入职</option><option value='7'>已离职</option></select></td></tr></table>" )
			$("#date4").html("<table class='layui-form'><tr><td><input id='startTime4' style='width: 320px;' name='startTime4'   class='layui-input laydate-icon'></td><td>  <select class='form-control' lay-filter='lay_selecte2' id='selectOne3'><option value='1'>邀约面试</option><option value='2'>应邀面试</option><option value='3'>面试合格</option><option value='4'>拒绝入职</option><option value='5'>已入职且在职</option><option value='6'>即将入职</option><option value='7'>已离职</option></select></td></tr></table>" )
			$("#date5").html("<table class='layui-form'><tr><td><input id='startTime5' style='width: 320px;' name='startTime5'   class='layui-input laydate-icon'></td><td>  <select class='form-control' lay-filter='lay_selecte3' id='selectOne4'><option value='1'>邀约面试</option><option value='2'>应邀面试</option><option value='3'>面试合格</option><option value='4'>拒绝入职</option><option value='5'>已入职且在职</option><option value='6'>即将入职</option><option value='7'>已离职</option></select></td></tr></table>" )
			form.render();
			var orderTimeBegin;
			var orderTimeEnd;
			laydate.render({
				elem: '#startTime3',
				type: 'datetime',
				range: '~',
				value : firstdate+' ~ '+lastdate,
				done: function(value, date) {
					var orderTime=value.split('~');
					orderTimeBegin=orderTime[0];
					orderTimeEnd=orderTime[1];
					var type="";
					var adopt="";
					var state="";
					var quit="";
					var value=$("#selectOne").val();
					if(value==1){
						type="";
						adopt="";
						state="";
						quit="";
					}
					if(value==2){
						type=1;
						adopt="";
						state="";
						quit="";
					}
					if(value==3){
						adopt=1;
						type="";
						state="";
						quit="";
					}
					if(value==4){
						state=2;
						type="";
						adopt="";
						quit="";
					}
					if(value==5){
						state=1;
						quit=0;
						type="";
						adopt="";
					}
					if(value==6){
						state=3;
						type="";
						adopt="";
						quit="";
					}
					if(value==7){
						state="";
						type="";
						adopt="";
						quit=1;
					}
					mainJs3(orderTimeBegin,orderTimeEnd,state,type,adopt,quit)
					}
			});
			laydate.render({
				elem: '#startTime4',
				type: 'datetime',
				range: '~',
				value : firstdate+' ~ '+lastdate,
				done: function(value, date) {
					var orderTime=value.split('~');
					orderTimeBegin=orderTime[0];
					orderTimeEnd=orderTime[1];
					var type="";
					var adopt="";
					var state="";
					var quit="";
					var value=$("#selectOne3").val();
					if(value==1){
						type="";
						adopt="";
						state="";
						quit="";
					}
					if(value==2){
						type=1;
						adopt="";
						state="";
						quit="";
					}
					if(value==3){
						adopt=1;
						type="";
						state="";
						quit="";
					}
					if(value==4){
						state=2;
						type="";
						adopt="";
						quit="";
					}
					if(value==5){
						state=1;
						quit=0;
						type="";
						adopt="";
					}
					if(value==6){
						state=3;
						type="";
						adopt="";
						quit="";
					}
					if(value==7){
						state="";
						type="";
						adopt="";
						quit=1;
					}
					mainJs4(orderTimeBegin,orderTimeEnd,state,type,adopt,quit)
					}
			});
			laydate.render({
				elem: '#startTime5',
				type: 'datetime',
				range: '~',
				value : firstdate+' ~ '+lastdate,
				done: function(value, date) {
					var orderTime=value.split('~');
					orderTimeBegin=orderTime[0];
					orderTimeEnd=orderTime[1];
					var type="";
					var adopt="";
					var state="";
					var quit="";
					var value=$("#selectOne4").val();
					if(value==1){
						type="";
						adopt="";
						state="";
						quit="";
					}
					if(value==2){
						type=1;
						adopt="";
						state="";
						quit="";
					}
					if(value==3){
						adopt=1;
						type="";
						state="";
						quit="";
					}
					if(value==4){
						state=2;
						type="";
						adopt="";
						quit="";
					}
					if(value==5){
						state=1;
						quit=0;
						type="";
						adopt="";
					}
					if(value==6){
						state=3;
						type="";
						adopt="";
						quit="";
					}
					if(value==7){
						state="";
						type="";
						adopt="";
						quit=1;
					}
					mainJs5(orderTimeBegin,orderTimeEnd,state,type,adopt,quit)
					}
			});
			var type="";
			var adopt="";
			var state="";
			var quit="";
			form.on('select(lay_selecte)', function(data) {
			var value=data.value;
			if(value==1){
				type="";
				adopt="";
				state="";
				quit="";
			}
			if(value==2){
				type=1;
				adopt="";
				state="";
				quit="";
			}
			if(value==3){
				adopt=1;
				type="";
				state="";
				quit="";
			}
			if(value==4){
				state=2;
				type="";
				adopt="";
				quit="";
			}
			if(value==5){
				state=1;
				quit=0;
				type="";
				adopt="";
			}
			if(value==6){
				state=3;
				type="";
				adopt="";
				quit="";
			}
			if(value==7){
				state="";
				type="";
				adopt="";
				quit=1;
			}
			var orderTime=$("#startTime3").val().split('~');
			orderTimeBegin=orderTime[0];
			orderTimeEnd=orderTime[1];
			mainJs3(orderTimeBegin,orderTimeEnd,state,type,adopt,quit)
			})
		form.on('select(lay_selecte2)', function(data) {
			var value=data.value;
			if(value==1){
				type="";
				adopt="";
				state="";
				quit="";
			}
			if(value==2){
				type=1;
				adopt="";
				state="";
				quit="";
			}
			if(value==3){
				adopt=1;
				type="";
				state="";
				quit="";
			}
			if(value==4){
				state=2;
				type="";
				adopt="";
				quit="";
			}
			if(value==5){
				state=1;
				quit=0;
				type="";
				adopt="";
			}
			if(value==6){
				state=3;
				type="";
				adopt="";
				quit="";
			}
			if(value==7){
				state="";
				type="";
				adopt="";
				quit=1;
			}
			var orderTime=$("#startTime4").val().split('~');
			orderTimeBegin=orderTime[0];
			orderTimeEnd=orderTime[1];
			mainJs4(orderTimeBegin,orderTimeEnd,state,type,adopt,quit)
			})	
			form.on('select(lay_selecte3)', function(data) {
			var value=data.value;
			if(value==1){
				type="";
				adopt="";
				state="";
				quit="";
			}
			if(value==2){
				type=1;
				adopt="";
				state="";
				quit="";
			}
			if(value==3){
				adopt=1;
				type="";
				state="";
				quit="";
			}
			if(value==4){
				state=2;
				type="";
				adopt="";
				quit="";
			}
			if(value==5){
				state=1;
				quit=0;
				type="";
				adopt="";
			}
			if(value==6){
				state=3;
				type="";
				adopt="";
				quit="";
			}
			if(value==7){
				state="";
				type="";
				adopt="";
				quit=1;
			}
			var orderTime=$("#startTime5").val().split('~');
			orderTimeBegin=orderTime[0];
			orderTimeEnd=orderTime[1];
			mainJs5(orderTimeBegin,orderTimeEnd,state,type,adopt,quit)
			})
		var mainJs3=function(orderTimeBegin,orderTimeEnd){
		var address = [],addressOther=0, 
		orgName = { name:[],value:[],other:0},
		platform = { name:[],value:[],other:0},
		agreement = { name:[], value:[], other:0},
		recruitName = { name:[],value:[],other:0},
		company = { lb:0,lc:0,other:0 },
		safe = 0,
		entryAndQuit = {};
		var allOrg = [];
		var allUser = myutil.getDataSync({ url: '${ctx}/personnel/getRecruit?size=999999&orderTimeBegin='+orderTimeBegin+'&orderTimeEnd='+orderTimeEnd+'&type='+type+'&adopt='+adopt+'&state='+state+'&quit='+quit+'&time=2019-06-02 11:00:00',  });
		for(var i=0,len=allUser.length;i<len;i++){
			var d = allUser[i];
			(function(){
				if(d.recruitName){
					var j=0,l=recruitName.name.length,news = true;
					for(;j<l;j++){
						if(d.recruitName == recruitName.name[j]){
							news = false;
							recruitName.value[j]++;
						}
					}
					if(news){
						recruitName.name[j] = d.recruitName;
						recruitName.value[j] = 1;
						allOrg.push(d.recruitName)
					}
				}else
					orgName.other++;
			})();
			(function(){
				if(d.platform.name){
					var j=0,l=platform.name.length,news = true;
					for(;j<l;j++){
						if(d.platform.name == platform.name[j]){
							news = false;
							platform.value[j]++;
						}
					}
					if(news){
						platform.name[j] = d.platform.name;
						platform.value[j] = 1;
						allOrg.push(d.platform)
					}
				}else
					orgName.other++;
			})();
			(function(){
				if(d.orgName){
					var j=0,l=orgName.name.length,news = true;
					for(;j<l;j++){
						if(d.orgName.name == orgName.name[j]){
							news = false;
							orgName.value[j]++;
						}
					}
					if(news){
						orgName.name[j] = d.orgName.name;
						orgName.value[j] = 1;
						allOrg.push(d.orgName)
					}
				}else
					orgName.other++;
			})();
		}
		
		var educationDiv = echarts.init(document.getElementById('education'));
		educationDiv.setOption(
				{
		            title: {		//标题
		                text: '招聘平台分布情况',
		            },
		            tooltip: {},	
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
		            xAxis: {		//横坐标
		            	data: platform.name,
		            	axisLabel: {
		                    interval: 0,
		                    rotate: 30
		                },
		            },
		            yAxis: {
		            	axisLabel: {
				            formatter: '{value} /人'
				        }
		            },		//纵坐标
		            series: [
		            	{
			                name: '平台',
			                type: 'bar',
						    markPoint: {	//标注点
				                data: [
				                    {type: 'max', name: '最多人数'},
				                    {type: 'min', name: '最少人数'}
				                ]
				            },
				            markLine: {		//标注线
				                data: [
				                    {type: 'average', name: '平均部门人数'}
				                ]
				           	},
				            label: {
				                normal: {
				                    show: true,
				                    position: 'inside'
				                }
				            },
			                data: platform.value,
		        		},
		        	]
		    });
		educationDiv.off("click");//防止累计触发
		educationDiv.on('click',function(obj){	//点击事件
			var platformId;
			for(var j=0;j<allOrg.length;j++){
				if(obj.name===allOrg[j].name){
					platformId = allOrg[j].id;
					break;
				}
			}
			searchUser({
				platformId: platformId
			})
		})
		function searchUser(opt){
			var orderTime=$("#startTime3").val().split('~');
			orderTimeBegin=orderTime[0];
			orderTimeEnd=orderTime[1];
			
			var value=$("#selectOne").val();
			if(value==1){
				type="";
				adopt="";
				state="";
				quit="";
			}
			if(value==2){
				type=1;
				adopt="";
				state="";
				quit="";
			}
			if(value==3){
				adopt=1;
				type="";
				state="";
				quit="";
			}
			if(value==4){
				state=2;
				type="";
				adopt="";
				quit="";
			}
			if(value==5){
				state=1;
				quit=0;
				type="";
				adopt="";
			}
			if(value==6){
				state=3;
				type="";
				adopt="";
				quit="";
			}
			if(value==7){
				state="";
				type="";
				adopt="";
				quit=1;
			}
			
			layer.open({
					type:1,
					title:'员工信息',
					area:['60%','70%'],
					shadeClose:true,
					content:'<table id="tableData" lay-filter="tableData"></table>',
					success:function(){
						mytable.render({
							url:'${ctx}/personnel/getRecruit?&orderTimeBegin='+orderTimeBegin+'&orderTimeEnd='+orderTimeEnd+'&type='+type+'&adopt='+adopt+'&state='+state+'&quit='+quit+'&time=2019-06-02 11:00:00',
							elem:'#tableData',
							where:opt,
							cols:[[
								{ title:'姓名', field:'name'},
								{ title:'职位', field:'name',templet:function(d){return d.position.name}},
								{ title:'面试时间', field:'time'},
								{ title:'平台', field:'platform',templet:function(d){return d.platform.name}},
								{ title:'招聘人', field:'recruitName'},
								{ title:'入职时间', field:'recruitName',templet:function(d){return d.user==null ? "":d.user.entry==null ? "" :d.user.entry}},
								{ title:'离职时间', field:'recruitName',templet:function(d){return d.user==null ? "":d.user.quitDate==null ? "" :d.user.quitDate}},
								{ title:'离职理由', field:'recruitName',templet:function(d){return d.user==null ? "":d.user.reason==null ? "" :d.user.reason}},
							]]
						})
					}
				})
				
			} 
		}
		mainJs3(firstdate,lastdate)
		var mainJs4=function(orderTimeBegin,orderTimeEnd){
			var address = [],addressOther=0, 
			orgName = { name:[],value:[],other:0},
			platform = { name:[],value:[],other:0},
			agreement = { name:[], value:[], other:0},
			recruitName = { name:[],value:[],other:0},
			company = { lb:0,lc:0,other:0 },
			safe = 0,
			entryAndQuit = {};
			var allOrg = [];
			var allUser = myutil.getDataSync({ url: '${ctx}/personnel/getRecruit?size=999999&orderTimeBegin='+orderTimeBegin+'&orderTimeEnd='+orderTimeEnd+'&type='+type+'&adopt='+adopt+'&state='+state+'&quit='+quit+'&time=2019-06-02 11:00:00',  });
			for(var i=0,len=allUser.length;i<len;i++){
				var d = allUser[i];
				(function(){
					if(d.recruitName){
						var j=0,l=recruitName.name.length,news = true;
						for(;j<l;j++){
							if(d.recruitName == recruitName.name[j]){
								news = false;
								recruitName.value[j]++;
							}
						}
						if(news){
							recruitName.name[j] = d.recruitName;
							recruitName.value[j] = 1;
							allOrg.push(d.recruitName)
						}
					}else
						orgName.other++;
				})();
				(function(){
					if(d.platform.name){
						var j=0,l=platform.name.length,news = true;
						for(;j<l;j++){
							if(d.platform.name == platform.name[j]){
								news = false;
								platform.value[j]++;
							}
						}
						if(news){
							platform.name[j] = d.platform.name;
							platform.value[j] = 1;
							allOrg.push(d.platform)
						}
					}else
						orgName.other++;
				})();
				(function(){
					if(d.orgName){
						var j=0,l=orgName.name.length,news = true;
						for(;j<l;j++){
							if(d.orgName.name == orgName.name[j]){
								news = false;
								orgName.value[j]++;
							}
						}
						if(news){
							orgName.name[j] = d.orgName.name;
							orgName.value[j] = 1;
							allOrg.push(d.orgName)
						}
					}else
						orgName.other++;
				})();
			}
		
        var orgNameDiv = echarts.init(document.getElementById('orgName'));
		orgNameDiv.setOption(
	        {
	            title: {		//标题
	                text: '招聘部门分布情况',
	            },
	            tooltip: {},	
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
	            xAxis: {		//横坐标
	            	data: orgName.name,
	            	axisLabel: {
	                    interval: 0,
	                    rotate: 30
	                },
	            },
	            yAxis: {
	            	axisLabel: {
			            formatter: '{value} /人'
			        }
	            },		//纵坐标
	            series: [
	            	{
		                name: '部门',
		                type: 'bar',
					    markPoint: {	//标注点
			                data: [
			                    {type: 'max', name: '最多人数'},
			                    {type: 'min', name: '最少人数'}
			                ]
			            },
			            markLine: {		//标注线
			                data: [
			                    {type: 'average', name: '平均部门人数'}
			                ]
			           	},
			            label: {
			                normal: {
			                    show: true,
			                    position: 'inside'
			                }
			            },
		                data: orgName.value,
	        		},
	        	]
	    });
		orgNameDiv.off("click");//防止累计触发
		orgNameDiv.on('click',function(obj){	//点击事件
			var orgId;
			for(var j=0;j<allOrg.length;j++){
				if(obj.name===allOrg[j].name){
					orgId = allOrg[j].id;
					break;
				}
			}
			searchUser({
				orgNameId: orgId
			})
		})
		function searchUser(opt){
			var orderTime=$("#startTime4").val().split('~');
			orderTimeBegin=orderTime[0];
			orderTimeEnd=orderTime[1];
			
			var value=$("#selectOne3").val();
			if(value==1){
				type="";
				adopt="";
				state="";
				quit="";
			}
			if(value==2){
				type=1;
				adopt="";
				state="";
				quit="";
			}
			if(value==3){
				adopt=1;
				type="";
				state="";
				quit="";
			}
			if(value==4){
				state=2;
				type="";
				adopt="";
				quit="";
			}
			if(value==5){
				state=1;
				quit=0;
				type="";
				adopt="";
			}
			if(value==6){
				state=3;
				type="";
				adopt="";
				quit="";
			}
			if(value==7){
				state="";
				type="";
				adopt="";
				quit=1;
			}
			
			layer.open({
					type:1,
					title:'员工信息',
					area:['60%','70%'],
					shadeClose:true,
					content:'<table id="tableData" lay-filter="tableData"></table>',
					success:function(){
						mytable.render({
							url:'${ctx}/personnel/getRecruit?&orderTimeBegin='+orderTimeBegin+'&orderTimeEnd='+orderTimeEnd+'&type='+type+'&adopt='+adopt+'&state='+state+'&quit='+quit+'&time=2019-06-02 11:00:00',
							elem:'#tableData',
							where:opt,
							cols:[[
								{ title:'姓名', field:'name'},
								{ title:'职位', field:'name',templet:function(d){return d.position.name}},
								{ title:'面试时间', field:'time'},
								{ title:'平台', field:'platform',templet:function(d){return d.platform.name}},
								{ title:'招聘人', field:'recruitName'},
								{ title:'入职时间', field:'recruitName',templet:function(d){return d.user==null ? "":d.user.entry==null ? "" :d.user.entry}},
								{ title:'离职时间', field:'recruitName',templet:function(d){return d.user==null ? "":d.user.quitDate==null ? "" :d.user.quitDate}},
								{ title:'离职理由', field:'recruitName',templet:function(d){return d.user==null ? "":d.user.reason==null ? "" :d.user.reason}},
							]]
						})
					}
				})
				
			} 
		}
		mainJs4(firstdate,lastdate)	
		var mainJs5=function(orderTimeBegin,orderTimeEnd){
			var address = [],addressOther=0, 
			orgName = { name:[],value:[],other:0},
			platform = { name:[],value:[],other:0},
			agreement = { name:[], value:[], other:0},
			recruitName = { name:[],value:[],value2:[],other:0},
			company = { lb:0,lc:0,other:0 },
			safe = 0,
			entryAndQuit = {};
			var allOrg = [];
			var allUser = myutil.getDataSync({ url: '${ctx}/personnel/getRecruit?size=999999&orderTimeBegin='+orderTimeBegin+'&orderTimeEnd='+orderTimeEnd+'&type='+type+'&adopt='+adopt+'&state='+state+'&quit='+quit+'&time=2019-06-02 11:00:00',  });
			for(var i=0,len=allUser.length;i<len;i++){
				var d = allUser[i];
				(function(){
					if(d.recruitName){
						var j=0,l=recruitName.name.length,news = true;
						for(;j<l;j++){
							if(d.recruitName == recruitName.name[j]){
								news = false;
								recruitName.value[j]++;
							}
						}
						if(news){
							recruitName.name[j] = d.recruitName;
							recruitName.value[j] = 1;
							recruitName.value2[j] =d.recruitId
							allOrg.push(d.recruitName)
							allOrg.push(recruitName)
						}
					}else
						orgName.other++;
				})();
				(function(){
					if(d.platform.name){
						var j=0,l=platform.name.length,news = true;
						for(;j<l;j++){
							if(d.platform.name == platform.name[j]){
								news = false;
								platform.value[j]++;
							}
						}
						if(news){
							platform.name[j] = d.platform.name;
							platform.value[j] = 1;
							allOrg.push(d.platform)
						}
					}else
						orgName.other++;
				})();
				(function(){
					if(d.orgName){
						var j=0,l=orgName.name.length,news = true;
						for(;j<l;j++){
							if(d.orgName.name == orgName.name[j]){
								news = false;
								orgName.value[j]++;
							}
						}
						if(news){
							orgName.name[j] = d.orgName.name;
							orgName.value[j] = 1;
							allOrg.push(d.orgName)
						}
					}else
						orgName.other++;
				})();
			}
		var userDiv = echarts.init(document.getElementById('user'));
		userDiv.setOption(
	        {
	            title: {		//标题
	                text: '招聘人分布情况',
	            },
	            tooltip: {},	
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
	            xAxis: {		//横坐标
	            	data: recruitName.name,
	            	axisLabel: {
	                    interval: 0,
	                    rotate: 30
	                },
	            },
	            yAxis: {
	            	axisLabel: {
			            formatter: '{value} /人'
			        }
	            },		//纵坐标
	            series: [
	            	{
		                name: '人名',
		                type: 'bar',
					    markPoint: {	//标注点
			                data: [
			                    {type: 'max', name: '最多人数'},
			                    {type: 'min', name: '最少人数'}
			                ]
			            },
			            markLine: {		//标注线
			                data: [
			                    {type: 'average', name: '平均部门人数'}
			                ]
			           	},
			            label: {
			                normal: {
			                    show: true,
			                    position: 'inside'
			                }
			            },
		                data: recruitName.value,
	        		},
	        	]
	    });
		userDiv.off("click");//防止累计触发
		userDiv.on('click',function(obj){	//点击事件
			var platformId;
			for(var j=0;j<recruitName.name.length;j++){
				if(obj.name===recruitName.name[j]){
					platformId = recruitName.value2[j];
					break;
				}
			}
			searchUser({
				recruitId: platformId
			})
		})
		function searchUser(opt){
			var orderTime=$("#startTime5").val().split('~');
			orderTimeBegin=orderTime[0];
			orderTimeEnd=orderTime[1];
			
			var value=$("#selectOne4").val();
			if(value==1){
				type="";
				adopt="";
				state="";
				quit="";
			}
			if(value==2){
				type=1;
				adopt="";
				state="";
				quit="";
			}
			if(value==3){
				adopt=1;
				type="";
				state="";
				quit="";
			}
			if(value==4){
				state=2;
				type="";
				adopt="";
				quit="";
			}
			if(value==5){
				state=1;
				quit=0;
				type="";
				adopt="";
			}
			if(value==6){
				state=3;
				type="";
				adopt="";
				quit="";
			}
			if(value==7){
				state="";
				type="";
				adopt="";
				quit=1;
			}
			
			layer.open({
					type:1,
					title:'员工信息',
					area:['60%','70%'],
					shadeClose:true,
					content:'<table id="tableData" lay-filter="tableData"></table>',
					success:function(){
						mytable.render({
							url:'${ctx}/personnel/getRecruit?&orderTimeBegin='+orderTimeBegin+'&orderTimeEnd='+orderTimeEnd+'&type='+type+'&adopt='+adopt+'&state='+state+'&quit='+quit+'&time=2019-06-02 11:00:00',
							elem:'#tableData',
							where:opt,
							cols:[[
								{ title:'姓名', field:'name'},
								{ title:'职位', field:'name',templet:function(d){return d.position.name}},
								{ title:'面试时间', field:'time'},
								{ title:'平台', field:'platform',templet:function(d){return d.platform.name}},
								{ title:'招聘人', field:'recruitName'},
								{ title:'入职时间', field:'recruitName',templet:function(d){return d.user==null ? "":d.user.entry==null ? "" :d.user.entry}},
								{ title:'离职时间', field:'recruitName',templet:function(d){return d.user==null ? "":d.user.quitDate==null ? "" :d.user.quitDate}},
								{ title:'离职理由', field:'recruitName',templet:function(d){return d.user==null ? "":d.user.reason==null ? "" :d.user.reason}},
							]]
						})
					}
				})
		}
		
		}
		mainJs5(firstdate,lastdate)	
		$("#date2").html("<table class='layui-form'><tr><td><input id='startTime2' style='width: 320px;' name='startTime2'   class='layui-input laydate-icon'></td><td>  <select class='form-control' lay-filter='lay_selecte4' id='selectOne2'><option value='1'>离职率</option><option value='2'>留存率</option><option value='3'>面试通过率</option><option value='4'>入职率</option><option value='5'>短期流失率</option></select></td></tr></table>" )
		form.render();
		laydate.render({
			elem: '#startTime2',
			type: 'datetime',
			range: '~',
			value : firstdate+' ~ '+lastdate,
			done: function(value, date) {
				var orderTime=value.split('~');
				orderTimeBegin=orderTime[0];
				orderTimeEnd=orderTime[1];
				var value=$("#selectOne2").val();
				mainJs2(orderTimeBegin,orderTimeEnd,value)
				}
		});
		form.on('select(lay_selecte4)', function(data) {
			var value=data.value;
			var orderTime=$("#startTime2").val().split('~');
			orderTimeBegin=orderTime[0];
			orderTimeEnd=orderTime[1];
			mainJs2(orderTimeBegin,orderTimeEnd,value)
			})
		var mainJs2=function(orderTimeBegin,orderTimeEnd,md){
			var orges = { name:[],value1:[],value2:[],value3:[],value4:[],value5:[],other:0}
			var allQuit = myutil.getDataSync({ url: '${ctx}/personnel/quitOrgName?orderTimeBegin='+orderTimeBegin+'&orderTimeEnd='+orderTimeEnd+'', });
			for(var i=0,len=allQuit.length;i<len;i++){
				var d = allQuit[i];
				if(md==1){
				orges.value1.push(d.md4)
				}
				if(md==2){
				orges.value1.push(d.md5)
				}
				if(md==3){
				orges.value1.push(d.md1)
				}
				if(md==4){
				orges.value1.push(d.md2)
				}
				if(md==5){
				orges.value1.push(d.md3)
				}
				orges.name.push(d.orgName)
			}
			 var orgQuitDiv = echarts.init(document.getElementById('orgQuit'));
			 orgQuitDiv.setOption(
					 {
				            title: {		//标题
				                text: '招聘平台分布情况',
				            },
				            tooltip: {},	
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
				            xAxis: {		//横坐标
				            	data: orges.name,
				            	axisLabel: {
				                    interval: 0,
				                    rotate: 30
				                },
				            },
				            yAxis: [{
				            	min: 0,
				                max: 100,
				            	axisLabel: {
						            formatter: '{value}%'
						        }
				            
				            }],		//纵坐标
				            series: [
				            	{
					                name: '平台',
					                type: 'bar',
								    markPoint: {	//标注点
						                data: [
						                    {type: 'max', name: '最多人数'},
						                    {type: 'min', name: '最少人数'}
						                ]
						            },
						            markLine: {		//标注线
						                data: [
						                    {type: 'average', name: '平均部门人数'}
						                ]
						           	},
						            label: {
						                normal: {
						                    show: true,
						                    position: 'inside'
						                }
						            },
					                data: orges.value1,
				        		},
				        	]
				    });
			}
		mainJs2(firstdate,lastdate,1)
		
		
	}//end defind
);
</script>
</html>
