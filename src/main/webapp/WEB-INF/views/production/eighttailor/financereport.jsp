<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>工资总汇</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/layer/layer.js"></script>	
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
	<link rel="stylesheet" href="${ctx }/static/css/main.css">
	<script src="${ctx }/static/js/laypage/laypage.js"></script>
	<script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
</head>

<body>
	
<div class="panel panel-default">
	
	<div class="panel-body">
		<div class="tab-wrapper tab-primary">
			<ul class="nav nav-tabs col-md-12">
				<li class="active col-md-6"><a href="#profile1" data-toggle="tab">绩效汇总</a></li>
				<li class="col-md-6"><a href="#profile2" data-toggle="tab">质检月产量报表</a></li>
			</ul>
			<div class="tab-content">
				<!-- 绩效汇总开始 -->
				<div class="tab-pane active" id="profile1">
					<!--查询开始  -->
					<table>
						<tr>
							<td>姓名:</td>
							<td><input type="text" name="name" id="username"
								placeholder="请输入姓名"
								class="form-control search-query name" /></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td>开始:</td>
							<td><input id="startTime" placeholder="请输入开始时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
							</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td>结束:</td>
							<td><input id="endTime" placeholder="请输入结束时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
							</td>
						</tr>
					</table>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><span class="input-group-btn">
						<button type="button"
							class="btn btn-info btn-square btn-sm btn-3d searchtask">
							查&nbsp;找</button>
					</span></td>
					<h1 class="page-header"></h1>			
					<!-- 查询结束 -->
					<table class="table table-hover">
						<thead>
							<tr>
								<th class="text-center">姓名</th>
								<th class="text-center">考勤时间</th>
								<th class="text-center">A工资汇总</th>
								<th class="text-center">B工资汇总</th>
								<th class="text-center">比值</th>
							</tr>
						</thead>
						<tbody id="tablecontent">

						</tbody>
					</table>
					<div id="pager" class="pull-right"></div>
				</div>
				<!-- 质检月产量报表 -->
				<div class="tab-pane" id="profile2">
				
					<table>
						<tr>
							<td>开始:</td>
							<td><input id="startTimetw" placeholder="请输入开始时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#startTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
							</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td>结束:</td>
							<td><input id="endTimetw" placeholder="请输入结束时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#endTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
							</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><span class="input-group-btn">
								<button type="button"
									class="btn btn-info btn-square btn-sm btn-3d searchtasktw">
									查&nbsp;找</button>
							</span></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
							<td><span
								class="input-group-btn">
								<button type="button"
									class="btn btn-success btn-sm btn-3d pull-right export">导出</button>
							</span></td>
						</tr>
					</table>
								
					<h1 class="page-header"></h1>

					<table class="table table-hover">
						<thead>
							<tr>
								<th class="text-center">日期</th>
								<th class="text-center">考勤人数</th>
								<th class="text-center">考勤总时间</th>
								<th class="text-center">当天产量</th>
								<th class="text-center">当天产值</th>
								<th class="text-center">杂工价值</th>
								<th class="text-center">杂工时间</th>
							</tr>
						</thead>
						<tbody id="tablecontenttw">

						</tbody>
					</table>
					<div id="pagertw" class="pull-right"></div>
				</div>
			</div>
		</div>
	</div>
</div>

	<script>
     
   jQuery(function($){
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
			var _cache;
			this.setCache = function(cache){
		  		_cache=cache;
		  	}
		  	this.getCache = function(){
		  		return _cache;
		  	}
			 var data={
						page:1,
				  		size:13,	
				  		type:5,

				} 
			 var myDate = new Date(new Date().getTime() - 86400000);
				//获取当前年
				var year=myDate.getFullYear();
				//获取当前月
				var month=myDate.getMonth()+1;
				//获取当前日
				var date=myDate.getDate(); 
				var h=myDate.getHours();       //获取当前小时数(0-23)
				var m=myDate.getMinutes();     //获取当前分钟数(0-59)
				var s=myDate.getSeconds(); 
				var day = new Date(year,month,0);  
				var firstdate = year + '-' + '0'+month + '-01'+' '+'00:00:00';
				var lastdate = year + '-' + '0'+month + '-' + day.getDate() +' '+'23:59:59';
				$('#startTime').val(firstdate);
				$('#endTime').val(lastdate);
				var a=year + '-' + '0'+month + '-' + date+' '+'00:00:00'
				var b=year + '-' + '0'+month + '-' + date+' '+'23:59:59'
				$('#startTimetw').val(a);
				$('#endTimetw').val(b);
				
			 var date={
				  		type:5,
				  		orderTimeBegin:firstdate,
				  		orderTimeEnd:lastdate,	
				} 
			 
			 var datatw={
					 type:5,
				  	orderTimeBegin:a,
				  	orderTimeEnd:b,
			 }
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(date);
				self.loadPaginationtw(datatw);
			}
			//加载分页
			  this.loadPagination = function(date){
			    var index;
			    var html = '';
			    //绩效汇总开始
			    $.ajax({
				      url:"${ctx}/finance/twoPerformancePay",
				      data:date,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      				if(o.addSelfNumber==null){
		      					o.addSelfNumber=0
		      				}
		      				if(o.timePrice==null){
		      					o.timePrice=0
		      				}
		      				var c="%";
		      				html +='<tr>'
		      				+'<td class="text-center edit ">'+o.userName+'</td>'
		      				+'<td class="text-center edit ">'+o.time+'</td>'
		      				+'<td class="text-center edit ">'+parseFloat((o.payA*1).toFixed(3))+'</td>'
		      				+'<td class="text-center edit ">'+parseFloat((o.payB*1).toFixed(3))+'</td>'
		      				+'<td class="text-center edit ">'+o.ratio+c+'</td></tr>'
		      				
		      				
							
		      			}); 
				       
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			  //绩效汇总结束
			}
			
			  this.loadPaginationtw = function(datatw){
				//质检月报表
				    var index;
				    var htmltw = '';
				    $.ajax({
					      url:"${ctx}/finance/monthlyProduction",
					      data:datatw,
					      type:"GET",
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			 $(result.data).each(function(i,o){
			      				htmltw +='<tr>'
			      				+'<td class="text-center edit ">'+o.orderTimeBegin+'</td>'
			      				+'<td class="text-center edit ">'+o.peopleNumber+'</td>'
			      				+'<td class="text-center edit ">'+o.time+'</td>'
			      				+'<td class="text-center edit ">'+o.productNumber+'</td>'
			      				+'<td class="text-center edit ">'+o.productPrice+'</td>'
			      				+'<td class="text-center edit ">'+o.farragoTaskPrice+'</td>'
			      				+'<td class="text-center edit ">'+o.farragoTaskTime+'</td>'
			      				+'</tr>'
								
			      			});  
					        //显示分页
						   /* 	 laypage({
						      cont: 'pagertw', 
						      pages: result.data.totalPages, 
						      curr:  result.data.pageNum || 1, 
						      jump: function(obj, first){ 
						    	  if(!first){ 
						    		 
							        	var _data = {
							        			page:obj.curr,
										  		size:13,
										  		type:1,
										  		userName:$('#usernametw').val(),
									  			taskName:$('#numbertw').val(),
									  			orderTimeBegin:$("#startTimetw").val(),
									  			orderTimeEnd:$("#endTimetw").val(),
									  	}
							        
							            self.loadPaginationtw(_data);
								     }
						      }
						    });  */ 
						   	layer.close(index);
						   	 $("#tablecontenttw").html(htmltw); 
						   
					      },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);
						  }
					  });
				  //杂工工资流水结束
			  }
			
			
			this.events = function(){
				$('.searchtask').on('click',function(){
					var data = {
							type:5,
							userName:$("#username").val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  	}
			
				self.loadPagination(data);
				});
				$('.searchtasktw').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:5,
				  			orderTimeBegin:$("#startTimetw").val(),
				  			orderTimeEnd:$("#endTimetw").val(), 
				  	}
			
				self.loadPaginationtw(data);
				});
				
				//导出
				$('.export').on('click',function(){
					var index; 
					var a=$("#startTimetw").val();
					var c= $("#endTimetw").val();
					location.href="${ctx}/excel/importExcel/monthlyProduction?orderTimeBegin="+a+"&orderTimeEnd="+c+"&type="+5;
				})
				
				//导出
				$('.exporttw').on('click',function(){
					var index; 
					var d=$("#startTimetw").val();
					var e= $("#endTimetw").val();
					location.href="${ctx}/excel/importExcel/groupProduction?orderTimeBegin="+d+"&orderTimeEnd="+e+"&type="+5;
				})
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>


</body>

</html>