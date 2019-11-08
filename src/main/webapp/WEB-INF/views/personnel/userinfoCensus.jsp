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
		    <div class="layui-col-md4" id="education"></div>
		    <div class="layui-col-md4" id="sex"></div>
		    <div class="layui-col-md4" id="agreement"></div>
		    <div class="layui-col-md8" id="age"></div>
		    <div class="layui-col-md4" id="company"></div>
		    <div class="layui-col-md8" id="address" style="height:500px;"></div>
		    <div class="layui-col-md4" id="safe" style="height:500px;"></div>
		    <div class="layui-col-md12" id="orgName" style="height:500px;"></div>
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
		[ 'echarts','china','mytable'],
		function() {
			var $ = layui.jquery
			, layer = layui.layer
			, myutil = layui.myutil
			, mytable = layui.mytable
			, echarts = layui.echarts;
		var education = { benke:0,  dazhuan:0, gaozhong:0, chuzhong:0, other:0, },
		age = { age20:0, age25:0, age30:0, age35:0, age40:0, age45:0, age50:0, age55:0, age60:0, age65:0, age66:0, other:0, },
		ageWoman = { age20:0, age25:0, age30:0, age35:0, age40:0, age45:0, age50:0, age55:0, age60:0, age65:0, age66:0, other:0, },
		ageMan = { age20:0, age25:0, age30:0, age35:0, age40:0, age45:0, age50:0, age55:0, age60:0, age65:0, age66:0, other:0, },
		sex = { man:0, woman:0, other:0, },
		address = [],addressOther=0,
		orgName = { name:[],value:[],other:0},
		agreement = { name:[], value:[], other:0},
		company = { lb:0,lc:0,other:0 },
		safe = 0,
		entryAndQuit = {};
		
		var allOrg = [];
		var allUser = myutil.getDataSync({ url: '${ctx}/system/user/pages?size=9999&quit=0', });
		/* allUser = layui.sort(allUser,'age'); */
		
		for(var i=0,len=allUser.length;i<len;i++){
			var d = allUser[i];
			switch(d.education){
			case '本科': education.benke++; break;
			case '大专': education.dazhuan++; break;
			case '高中': education.gaozhong++; break;
			case '初中及以下': education.chuzhong++; break;
			default: education.other++; break;
			};
			switch(true){
			case d.age<20: age.age20++; if(d.gender) ageWoman.age20++; else  ageMan.age20++; break;
			case d.age<25: age.age25++; if(d.gender) ageWoman.age25++; else  ageMan.age25++;  break;
			case d.age<30: age.age30++; if(d.gender) ageWoman.age30++; else  ageMan.age30++;  break;
			case d.age<35: age.age35++; if(d.gender) ageWoman.age35++; else  ageMan.age35++;  break;
			case d.age<40: age.age40++; if(d.gender) ageWoman.age40++; else  ageMan.age40++;  break;
			case d.age<45: age.age45++; if(d.gender) ageWoman.age45++; else  ageMan.age45++;  break;
			case d.age<50: age.age50++; if(d.gender) ageWoman.age50++; else  ageMan.age50++;  break;
			case d.age<55: age.age55++; if(d.gender) ageWoman.age55++; else  ageMan.age55++;  break;
			case d.age<60: age.age60++; if(d.gender) ageWoman.age60++; else  ageMan.age60++;  break;
			case d.age<65: age.age65++; if(d.gender) ageWoman.age65++; else  ageMan.age65++;  break;
			case d.age!=null: age.age66++; if(d.gender) ageWoman.age66++; else  ageMan.age66++;  break;
			default: age.other++;  if(d.gender) ageWoman.other++; else  ageMan.other++; break;
			};
			switch(d.gender){
			case 0: sex.man++; break;
			case 1: sex.woman++; break;
			default: sex.other++; break;
			}
			if(d.permanentAddress.indexOf('省')>0 || d.permanentAddress.indexOf('重庆')>=0 || d.permanentAddress.indexOf('北京')>=0){
				var addressName = d.permanentAddress?d.permanentAddress.split('省')[0]:'';
				if(d.permanentAddress.indexOf('重庆')>=0)
					addressName = '重庆';
				if(d.permanentAddress.indexOf('北京')>=0)
					addressName = '北京';
				if(addressName){
					var j=0,l=address.length,news = true;
					for(;j<l;j++){
						if(address[j].name==addressName){
							address[j].value++;
							news = false;
						}
					}
					if(news){
						address[j] = {
							name:addressName,
							value:1,
						}
					}
				}
			}else
				addressOther++;
			;(function(){
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
			;(function(){
				if(d.commitments){
					var j=0,l=agreement.name.length,news = true;
					for(;j<l;j++){
						if(d.commitments.name == agreement.name[j]){
							news = false;
							agreement.value[j]++;
						}
					}
					if(news){
						agreement.name[j] = d.commitments.name;
						agreement.value[j] = 1;
					}
				}else
					agreement.other++;
			})();
			if(d.company=='蓝白')
				company.lb++;
			else if(d.company=='乐程')
				company.lc++;
			else
				company.other++;
			if(d.safe)
				safe++;
			if(d.entry){
				var t = d.entry.split(' ')[0];
				if(entryAndQuit[t]){
					entryAndQuit[t].entry++;
				}else
					entryAndQuit[t] = { entry:1, quit:0, };
			}
				
		}
		
		var educationDiv = echarts.init(document.getElementById('education'));
		educationDiv.setOption(
	        {
	            title: {		//标题
	                text: '在职员工学历分布情况'
	            },
	            tooltip: {
	            	
	            },	
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
	            	data:['本科','大专','高中','初中及以下','其他'],
	            },
	            yAxis: {},		//纵坐标
	            series: [
	            	{
		                name: '学历',
		                type: 'bar',
		                data: [education.benke, education.dazhuan, education.gaozhong, education.chuzhong, education.other],
	        		},
	        	]
	    });
		educationDiv.on('click',function(obj){	//点击事件
			searchUser({
				education: obj.name
			})
		})
		var sexDiv = echarts.init(document.getElementById('sex'));
		sexDiv.setOption({
        	title: {		//标题
                text: '在职员工性别占比',
                x:'left', //水平方向
            },
            legend: {		
		        orient: 'vertical',
		        x: 'right',
		        data:['男','女','其他',]
		    },
            tooltip: {
            	trigger: 'item',
        		formatter: "{a} <br/>{b} : {c} ({d}%)",  
            },
		    series : [
		        {
		            name: '性别占比',
		            selectedMode: 'single', 
		            type: 'pie',
		            //radius: ['50%', '70%',],	//双圈格式
		            //roseType: 'radius',			//不同大小
		            data:[
		                {value:sex.man, name:'男'},
		                {value:sex.woman, name:'女'},
		                {value:sex.other, name:'其他'},
		            ]
		        }
		    ]
		})
		sexDiv.on('click',function(obj){	//点击事件
			var gender=2;
			if(obj.name==='女')
				gender = 1;
			if(obj.name==='男')
				gender = 0;
			searchUser({
				gender: gender
			}) 
		})
		var ageDiv = echarts.init(document.getElementById('age'));
		ageDiv.setOption({
			title:{
				text:'在职员工年龄分布',
			},
		    tooltip:{ 
		    	trigger: 'axis',
		    },	
		    legend: {	//交互
		        data:['年龄','男','女']
		    },
		    toolbox: {	//工具箱
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
		        data: ['20岁以下', '20-25岁', '25-30岁', '30-35岁', '35-40岁', '40-45岁','45-50岁','50-55岁','55-60岁','60-65岁','65岁以上',],
		    },
		    yAxis: {
		        type: 'value',
		        axisLabel: {
		            formatter: '{value} /人'
		        }
		    },
		    series: [
		    	{
		    		name:'年龄',
			        data: [age.age20,age.age25,age.age30,age.age35,age.age40,age.age45,age.age50,age.age55,age.age60,age.age65,age.age66,],
			        type: 'line',
			        markPoint: {	//标注点
		                data: [
		                    {type: 'max', name: '最多人数'},
		                    {type: 'min', name: '最少人数'}
		                ]
		            },
		    	},
		    	{
		    		name:'男',
			        data: [ageMan.age20,ageMan.age25,ageMan.age30,ageMan.age35,ageMan.age40,
			        	   ageMan.age45,ageMan.age50,ageMan.age55,ageMan.age60,ageMan.age65,ageMan.age66,],
			        type: 'line',
		    	},
		    	{
		    		name:'女',
			        data: [ageWoman.age20,ageWoman.age25,ageWoman.age30,ageWoman.age35,ageWoman.age40,
			        	   ageWoman.age45,ageWoman.age50,ageWoman.age55,ageWoman.age60,ageWoman.age65,ageWoman.age66,],
			        type: 'line',
		    	},
		    ]
		})
		var companyDiv = echarts.init(document.getElementById('company'));
		companyDiv.setOption({
        	title: {		//标题
                text: '员工签订公司',
                x:'left', //水平方向
            },
            legend: {		
		        orient: 'vertical',
		        x: 'right',
		        data:['蓝白','乐程','其他',]
		    },
            tooltip: {
            	trigger: 'item',
        		formatter: "{a} <br/>{b} : {c} ({d}%)",  
            },
		    series : [
		        {
		            name: '签订单位占比',
		            selectedMode: 'single', 
		            type: 'pie',
		            data:[
		                {value: company.lb, name:'蓝白'},
		                {value: company.lc, name:'乐程'},
		                {value: company.other, name:'其他'},
		            ]
		        }
		    ]
		})
		companyDiv.on('click',function(obj){	//点击事件
			console.log(obj)
			/* searchUser({
				gender: gender
			})  */
		})
		var addressDiv = echarts.init(document.getElementById('address'));
		addressDiv.setOption({
            title: {
                text: "在职员工祖籍分布",
                subtext: "其中未登录人员："+addressOther+'(人)',
                x:'left',
            },
            tooltip: { 
            },
            legend: {},
            dataRange: {
                orient: "horizontal",
                min: 0,
                max: 100,
                text: ["高", "低"],
                splitNumber: 0
            },
            toolbox: {
	            show: true,
	            //orient: 'vertical',
	            left: 'right',
	            top: 'top',
	            feature: {
	                dataView: {readOnly: false},
	                restore: {},
	                saveAsImage: {}
	            }
	        },
            series: [{
                name: "员工祖籍分布",
                type: "map",
                mapType: "china",
                //roam: true,		//是否可缩放
                selectedMode: "multiple",
                itemStyle: {
                    normal: { label: { show: !0 } },
                    emphasis: { label: {  show: !0  } }
                },
                data: address
            }]
        })
        var orgNameDiv = echarts.init(document.getElementById('orgName'));
		orgNameDiv.setOption(
	        {
	            title: {		//标题
	                text: '员工部门分布情况',
	                subtext: "其中未登录人员："+orgName.other+'(人)',
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
		orgNameDiv.on('click',function(obj){	//点击事件
			var orgId;
			for(var j=0;j<allOrg.length;j++){
				if(obj.name===allOrg[j].name){
					orgId = allOrg[j].id;
					break;
				}
			}
			searchUser({
				orgNameIds: orgId
			})
		})
		var agreementDiv = echarts.init(document.getElementById('agreement'));
		agreementDiv.setOption({
		    title: {
		        text: '员工合同签订图',
		        subText:'未签合同：'+agreement.other,
		    },
		    tooltip: {},
		    radar: {
		        shape: 'circle',	//圆形
		        name: {
		            textStyle: {	//显示字体样式
		                color: '#fff',
		                backgroundColor: '#999',
		                borderRadius: 3,
		                padding: [3, 5]
		           }
		        },
		        indicator: (function(){
		        	var a = [];
		        	for(var j=0;j<agreement.name.length;j++){
		        		a.push({
		        			name: agreement.name[j], max:300,
		        		})
		        	}
		        	return a;
		        })(),
		    },
		    series: [{
		        name: '合同签订',
		        type: 'radar',
		        //areaStyle: {normal: {}}, //区域面积样式
		        data : [
		            {
		                value : agreement.value,
		                name : '合同签订情况'
		            }
		        ]
		    }]
		});
		var safeDiv = echarts.init(document.getElementById('safe'));
		var jiaonalv = (safe/allUser.length*100).toFixed(2)+'%';
		safeDiv.setOption({
		    tooltip : {
		        formatter: "{a} <br/>{b} : "+jiaonalv
		    },
		    series: [
		        {
				    max: allUser.length,
		            name: '员工保险缴纳率',
		            type: 'gauge',
		            detail: {formatter: jiaonalv },
		            data: [{value: safe, name: '员工保险缴纳率'}]
		        }
		    ]
		});
		/* myutil.getData({
			url:'${ctx}/system/user/pages?size=9999&quit=1',
			success:function(d){
				for(var i=0,len=d.length;i<len;i++){
					if(d[i].entry){
						var t = d[i].entry.split(' ')[0];
						if(entryAndQuit[t])
							entryAndQuit[t].entry++;
						else
							entryAndQuit[t] = { entry:1, quit:0 };
					}
					if(d[i].quitDate){
						var t = d[i].quitDate.split(' ')[0];
						if(entryAndQuit[t])
							entryAndQuit[t].quit++;
						else
							entryAndQuit[t] = { entry:0, quit:1,  };
					}
				}
				var allEntry = [],allQuit = [],allDate = [];
				for(var i in entryAndQuit){
					allEntry.push(entryAndQuit[i].entry)
					allQuit.push(entryAndQuit[i].quit)
					allDate.push(i)
				}
				var entryQuitDiv = echarts.init(document.getElementById('entryQuit'));
				entryQuitDiv.setOption({
				    title : {
				        text: '员工入职、离职关系图',
				        subtext: '关系图未计算入未登记的员工信息',
				        x: 'center',
				        align: 'right'
				    },
				    grid: {  bottom: 80 },
				    toolbox: {
				        feature: {
				            dataZoom: { yAxisIndex: 'none' },
				            restore: {},
				            saveAsImage: {}
				        }
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'cross',
				            animation: false,
				            label: {
				                backgroundColor: '#505765'
				            }
				        }
				    },
				    legend: {
				        data:['离职员工','入职员工'],
				        x: 'left'
				    },
				    dataZoom: [
				        {
				            show: true,
				            realtime: true,
				            start: 65,
				            end: 85
				        },
				        {
				            type: 'inside',
				            realtime: true,
				            start: 65,
				            end: 85
				        }
				    ],
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            axisLine: {onZero: false},
				            data : allDate
				        }
				    ],
				    yAxis: [
				        {
				            name: '离职员工/人',
				            type: 'value',
				            max: 20
				        },
				        {
				            name: '入职员工/人',
				            nameLocation: 'start',
				            max: 20,
				            type: 'value',
				            inverse: true
				        }
				    ],
				    series: [
				        {
				            name:'离职员工',
				            type:'line',
				            animation: false,
				            areaStyle: {
				            },
				            lineStyle: {
				                width: 1
				            },
				            data: allQuit
				        },
				        {
				            name:'入职员工',
				            type:'line',
				            yAxisIndex:1,
				            animation: false,
				            areaStyle: { },
				            lineStyle: {
				                width: 1
				            },
				            data: allEntry
				        }
				    ]
				})
			}
		}) */
		function searchUser(opt){
			layer.open({
				type:1,
				title:'员工信息',
				area:['40%','70%'],
				shadeClose:true,
				content:'<table id="tableData" lay-filter="tableData"></table>',
				success:function(){
					mytable.render({
						url:'${ctx}/system/user/pages?quit=0',
						elem:'#tableData',
						where:opt,
						cols:[[
							{ title:'id', field:'id'},
							{ title:'姓名', field:'userName'},
							{ title:'年龄', field:'age'},
						]]
					})
				}
			})
		}
	}//end defind
);
</script>
</html>
