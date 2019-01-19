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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
   
   
</head>

<body>
    <section id="main-wrapper" class="theme-default">
        
        <%@include file="../../decorator/leftbar.jsp"%> 
        
        <!--main content start-->
        
           <section id="main-content" class="animated fadeInUp">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">工资详情</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                             <div class="panel-body">
                                <div class="tab-wrapper tab-primary">
                                    <ul class="nav nav-tabs col-md-12">
                                        <li class="active col-md-3"><a href="#home1" data-toggle="tab">绩效流水</a>
                                        </li>
                                        <li class="col-md-3"><a href="#profile1" data-toggle="tab">绩效汇总</a>
                                        </li>
                                        <li class="col-md-3"><a href="#profile2" data-toggle="tab">质检月产量报表</a>
                                        </li>
                                        <li class="col-md-3"><a href="#profile3" data-toggle="tab">非一线员工绩效汇总</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="home1">
                                        <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-10 col-sm-10  col-md-10">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr>
								<td>我想上浮下调比例:</td><td><input type="text" name="name" id="usernameth" placeholder="请输入比例" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>无加绩的配合奖励:</td><td><input type="text" name="name" id="code" placeholder="请输入奖励" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>开始时间:</td>
								<td>
								<input id="startTimeth" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTimeth', istime: true, format: 'YYYY-MM-DD 00:00:00'})"> 
								</td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>结束时间:</td>
								<td>
								<input id="endTimeth" placeholder="请输入结束时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#endTimeth', istime: true, format: 'YYYY-MM-DD 23:59:59'})">
								</td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtaskth">
										查&nbsp找
									</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
									 <button type="button" id="export" class="btn btn-success  btn-sm btn-3d pull-right">
									 导出绩效
									 </button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
                                        
                                            <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">姓名</th>
                                        	<th class="text-center">到岗时间</th>
                                            <th class="text-center">A工资</th>
                                            <th class="text-center">B工资</th>
                                            <th class="text-center">上浮后的B</th>
                                            <th class="text-center">考虑个人调节上浮后的B</th>
                                            <th class="text-center">个人调节发放比例</th>
                                            <th class="text-center">上浮后的加绩</th>
                                            <th class="text-center">手动调节的加绩</th>
                                            <th class="text-center">上浮后无加绩固定给予</th>
                                            <th class="text-center">无绩效小时工资</th>
                                            <th class="text-center">有绩效小时工资</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontentth">
                                        
                                    </tbody>
                                </table>
                                <div id="pagerth" class="pull-right"></div>
                                        </div>
                     <!-- 绩效水开始 -->
            <div class="tab-pane" id="profile1">
                      <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr>
								<td>姓名:</td><td><input type="text" name="name" id="username" placeholder="请输入姓名" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>开始:</td>
								<td>
								<input id="startTime" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>结束:</td>
								<td>
								<input id="endTime" placeholder="请输入结束时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask">
										查&nbsp找
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->  
                                   <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">姓名</th>
                                        	<th class="text-center">A工资</th>
                                        	<th class="text-center">B工资</th>
                                        	<th class="text-center">上浮后的B</th>
                                        	<th class="text-center">汇总加绩</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent">
                                        
                                    </tbody>
                                </table>
                                <div id="pager" class="pull-right"></div>
                                 </div>
                                 <!-- 绩效汇总结束 -->
                                    <div class="tab-pane" id="profile2">
                                     <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr>
								<td>开始:</td>
								<td>
								<input id="startTimetw" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>结束:</td>
								<td>
								<input id="endTimetw" placeholder="请输入结束时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#endTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtasktw">
										查&nbsp找
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
                                    
                                            <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">日期</th>
                                        	<th class="text-center">考勤人数</th>
                                        	<th class="text-center">考勤总时间</th>
                                            <th class="text-center">当天产量  </th>
                                            <th class="text-center">当天产值</th>
                                            <th class="text-center">返工出勤人数</th>
                                            <th class="text-center">返工出勤时间 </th>
                                            <th class="text-center">返工人员</th>
                                            <th class="text-center">返工个数</th>
                                            <th class="text-center">返工时间</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontenttw">
                                        
                                    </tbody>
                                </table>
                                <div id="pagertw" class="pull-right"></div>
                                        </div>
                                                                  <div class="tab-pane" id="profile3">
                      <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr>
								<td>姓名:</td><td><input type="text" name="name" id="usernamefr" placeholder="请输入姓名" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>开始:</td>
								<td>
								<input id="startTimefr" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTimefr', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>结束:</td>
								<td>
								<input id="endTimefr" placeholder="请输入结束时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#endTimefr', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</td>
								
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtaskfr">
										查&nbsp找
									</button>
								</span>
								
								
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->  
                                   <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">姓名</th>
                                        	<th class="text-center">考勤工资和已发绩效</th>
                                        	<th class="text-center">考勤</th>
                                        	<th class="text-center">剩余管理发放绩效</th>
                                        	<th class="text-center">累计产生的发货绩效</th>
                                        	<th class="text-center">总产量</th>
                                        	<th class="text-center">单只协助发货费用</th>
                                        	<th class="text-center">人为手动加减量化绩效比</th>
                                        	<th class="text-center">
									<input id="endTimefv"  class=" laydate-icon"
             					onClick="laydate({elem: '#endTimefv', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
             					</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontentfv">
                                        
                                    </tbody>
                                </table>
                                <div id="pagerfv" class="pull-right"></div>
                                 </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>

                        </div>
            </section>
        </section>


<!--隐藏框已完成的批次开始  -->
        <div id="addworking" style="display: none;">
			<div class="panel-body">
 <div class="form-group">
  </div> 
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">日期</th>
                                            <th class="text-center">数量</th>
                                            <th class="text-center">奖励</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tableworking">
                                    </tbody>
                                </table>
                                 <div id="pagerr" class="pull-right">
                            </div>
</div>
<!--隐藏框 已完成的批次结束  -->
</div>

    </section>
    
   
   
   <script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
    <script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx }/static/plugins/navgoco/jquery.navgoco.min.js"></script>
    <script src="${ctx }/static/plugins/switchery/switchery.min.js"></script>
    <script src="${ctx }/static/plugins/pace/pace.min.js"></script>
    <script src="${ctx }/static/plugins/fullscreen/jquery.fullscreen-min.js"></script>
    <script src="${ctx }/static/js/src/app.js"></script>
     <script src="${ctx }/static/js/laypage/laypage.js"></script> 
    <script src="${ctx }/static/plugins/dataTables/js/jquery.dataTables.js"></script>
    <script src="${ctx }/static/plugins/dataTables/js/dataTables.bootstrap.js"></script>
    <script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
    <script>
     
   /*  var date_ = new Date();  
    var year = date_.getYear();  
    var month = date_.getMonth() + 1;  
    var firstdate = year + '-' + month + '-01' 
    var day = new Date(year,month,0);      
    var lastdate = year + '-' + month + '-' + day.getDate();  */ 
    
    
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
				  		type:2,

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
				var getday = year + '-' + '0'+month + date+' '+'00:00:00';
				var lastdate = year + '-' + '0'+month + '-' + day.getDate() +' '+'23:59:59';
				$('#startTime').val(firstdate);
				$('#endTime').val(lastdate);
				var a=year + '-' + '0'+month + '-' + date+' '+'00:00:00'
				var b=year + '-' + '0'+month + '-' + date+' '+'23:59:59'
				$('#startTimetw').val(a);
				$('#endTimetw').val(b);
				$('#endTimefv').val(firstdate);
				$('#startTimefr').val(a);
				$('#endTimefr').val(b);
			 var date={
				  		type:2,
				  		orderTimeBegin:firstdate,
				  		orderTimeEnd:lastdate,	
				} 
			 
			 var datatw={
					 type:2,
				  	orderTimeBegin:a,
				  	orderTimeEnd:b,
			 }
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(date);
				self.loadPaginationtw(datatw);
				self.loadPaginationth(data);
				self.loadPaginationfv(datatw);
			}
			//加载分页
			  this.loadPagination = function(date){
			    var index;
			    var html = '';
			    //绩效汇总开始
			    $.ajax({
				      url:"${ctx}/finance/sumCollectPay",
				      data:date,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      				
		      				html +='<tr>'
		      				+'<td class="text-center edit ">'+o.userName+'</td>'
		      				+'<td class="text-center edit ">'+parseFloat((o.payA*1).toFixed(3))+'</td>'
		      				+'<td class="text-center edit ">'+parseFloat((o.payB*1).toFixed(3))+'</td>'
		      				+'<td class="text-center edit ">'+parseFloat((o.addPayB*1).toFixed(3))+'</td>'
		      				+'<td class="text-center edit ">'+o.addPerformancePay+'</td></tr>'
							
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
			      				+'<td class="text-center edit ">'+o.reworkNumber+'</td>'
			      				+'<td class="text-center edit ">'+o.reworkTurnTime+'</td>'
			      				+'<td class="text-center edit ">'+o.userName+'</td>'
			      				+'<td class="text-center edit ">'+o.rework+'</td>'
			      				+'<td class="text-center edit ">'+o.reworkTime+'</td>'
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
			this.loadPaginationth=function(postdata){
				//绩效计算
				var index;
			    $('.searchtaskth').on('click',function(){
			    var htmlth = '';
			    var orderTimeBegin=$("#startTimeth").val();
			    var orderTimeEnd=$("#endTimeth").val();
			    var  d   =   new   Date(Date.parse(orderTimeBegin.replace(/-/g,   "/")));
			    var  c   =   new   Date(Date.parse(orderTimeEnd.replace(/-/g,   "/")));
			    var addNumber=$('#usernameth').val();
			    var noPerformancePay=$('#code').val();
			    if(c-d!=86399000){
			    	return layer.msg("必须输入同一天日期", {icon: 2});
			    }
			    if(addNumber==""){
			    	return layer.msg("我想上浮的比例不能为空", {icon: 2});
			    }
			    if(noPerformancePay==""){
			    	return layer.msg("无加绩的配合奖励不能为空", {icon: 2});
			    }
			    	var postdata = {
				  			type:2,
				  			addNumber:addNumber,
				  			noPerformancePay:noPerformancePay,
				  			orderTimeBegin:orderTimeBegin,
				  			orderTimeEnd:orderTimeEnd, 
				  	}
			    	
			    $.ajax({
				      url:"${ctx}/finance/collectPay",
				      data:postdata,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 
		      			 $(result.data).each(function(i,o){
		      				htmlth +='<tr>'
		      				+'<td class="text-center  ">'+o.userName+'</td>'
		      				+'<td class="text-center ">'+o.time+'</td>'
		      				+'<td class="text-center ">'+o.payA+'</td>'
		      				+'<td class="text-center  ">'+parseFloat((o.payB*1).toFixed(3))+'</td>'
		      				+'<td class="text-center  ">'+parseFloat((o.addPayB*1).toFixed(3))+'</td>'
		      				+'<td class="text-center  ">'+parseFloat((o.addSelfPayB*1).toFixed(3))+'</td>'
		      				+'<td class="text-center  edit addSelfNumber">'+o.addSelfNumber+'</td>'
		      				+'<td class="text-center  addPerformancePaytw">'+o.addPerformancePay+'</td>'
		      				+'<td class="text-center  edit hardAddPerformancePay">'+o.hardAddPerformancePay+'</td>'
		      				+'<td class="text-center  noPerformanceNumbertw">'+o.noPerformanceNumber+'</td>'
		      				+'<td class="text-center  noTimePaytw">'+o.noTimePay+'</td>'
		      				+'<td class="text-center  timePaytw">'+o.timePay+'</td>'
		      				+'<td class="text-center"> <button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button></td></tr>'
		      			}); 
				          
					   	layer.close(index);
					   	 $("#tablecontentth").html(htmlth); 
					   	self.loadEvents();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			    })
			}
			
			this.loadPaginationfv = function(datatw){
			    var index;
			    var html = '';
			    //绩效汇总开始
			    $.ajax({
				      url:"${ctx}/finance/headmanPay",
				      data:datatw,
				      type:"POST",
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
		      				if(o.onePay==null){
		      					o.onePay=""
		      				}
		      				if(o.addition==null){
		      					o.addition=""
		      				}
		      				if(o.yields==null){
		      					o.yields=""
		      				}
		      				if(o.cumulative==null){
		      					o.cumulative=0
		      				}
		      				if(o.surplusManagement==null){
		      					o.surplusManagement=0
		      				}
		      				html +='<tr>'
		      				+'<td class="text-center edit selectuserName">'+o.userName+'</td>'
		      				+'<td class="text-center edit ">'+o.pay+'</td>'
		      				+'<td class="text-center edit ">'+o.time+'</td>'
		      				+'<td class="text-center edit ">'+o.surplusManagement+'</td>'
		      				+'<td class="text-center edit ">'+o.cumulative+'</td>'
		      				+'<td class="text-center edit ">'+o.accumulateYield+'</td>'
		      				+'<td class="text-center edit "><input class="workto" value="'+o.onePay+'"></input></td>'
		      				+'<td class="text-center edit "><input class="workth" value="'+o.addition+'"></input></td>'
		      				+'<td class="text-center edit "><button class="btn btn-primary btn-trans btn-sm savemode" data-id="'+o.id+'">填写</button></tr>'
							
		      			}); 
				       
					   	layer.close(index);
					   	 $("#tablecontentfv").html(html); 
					  
					 self.loadEventstw();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			  //绩效汇总结束
			}
			
			this.loadEventstw = function(){
				
				
				//触发工序弹框 加载内容方法
				$('.savemode').on('click',function(){
					var _index
					var productId=$(this).data('id')
					var name=$(this).data('name')
					var dicDiv=$('#addworking');
					 var html = '';
					 var htmlth = '';
					 var id=$(this).data('id');
					 var onePay=$(this).parent().parent().find('.workto').val();
					 var addition=$(this).parent().parent().find('.workth').val();
					var postData={
						id:$(this).data('id'),
						date:$('#endTimefv').val(),
					}  
					$.ajax({
							url:"${ctx}/finance/getMouthYields",
							data:postData,
							type:"POST",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								
								$(result.data.data).each(function(i,o){
				      				html +='<tr>'
				      				+'<td class="text-center edit sumname">'+o.name+'</td>'
				      				+'<td class="text-center edit "><input class="sumva" value="'+o.value+'"></input></td>'
				      				+'<td class="text-center edit "><input class="sumvatw" disabled="disabled" value="'+o.price+'"></input></td></tr>'
				      			}); 
								
								
								layer.close(index);
							   	 $("#tableworking").html(html);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['30%', '70%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:name,
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var c;
							  var arr= new Array();
							  var date;
							  $('.sumname').each(function(i,o){
								var a= $(this).text();
								var b= $(this).next().find('.sumva').val();
								var d= $(this).next().next().find('.sumvatw').val();
								 c={"name":a,"value":b,"price":d};
								 arr.push(c);
							  })
							  date={"data":arr};
							  var postData = {
									  	type:2,
										id:id,
										onePay:onePay,
										addition:addition,
										yields:JSON.stringify(date),
								}
							  var index;
							  if(onePay==""){
									return layer.msg("单只协助发货费不能为空！", {icon: 2});
								 }
								   if(addition==null){
										return layer.msg("人为手动加减量化绩效比不能为空！", {icon: 2});
									 }
								$.ajax({
									url:"${ctx}/finance/updateHeadmanPay",
									data:postData,
									traditional: true,
									type:"POST",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
											layer.msg("成功！", {icon: 1});
										 	var _date={
											  		type:2,
											  		orderTimeBegin:firstdate,
											  		orderTimeEnd:lastdate,	
											}
											self.loadPaginationfv(_date);
										layer.close(index);
										}else{
											layer.msg("修改失败！", {icon: 2});
											layer.close(index);
										}
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
							},
						  end:function(){
							  $('#addworking').hide();
							  /* data={
									page:1,
								  	size:13,	
								  	type:2,
								  	name:$('#name').val(),
						  			number:$('#number').val(),
						  			status:$('.selectchoice').val(),
							  }
							self.loadPaginationfv(data); */
						  }
					});
					
					/* self.loadworking();  */
					
					
				})
			}
			this.loadEvents = function(){
				//修改方法
				$('.updateremake').on('click',function(){
					var that=$(this)
					if($(this).text() == "编辑"){
						$(this).text("保存")
						
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
				        });
					}else{
							$(this).text("编辑")
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							
							var postData = {
									id:$(this).data('id'),
									addSelfNumber:$(this).parent().parent('tr').find(".addSelfNumber").text(),
									hardAddPerformancePay:$(this).parent().parent('tr').find(".hardAddPerformancePay").text(),
							}
							var index;
							
							$.ajax({
								url:"${ctx}/finance/updateCollectPay",
								data:postData,
								type:"POST",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									if(0==result.code){
									layer.msg("修改成功！", {icon: 1});
									that.parent().parent().find('.addPerformancePaytw').text(result.data.addPerformancePay)
									that.parent().parent().find('.noPerformanceNumbertw').text(result.data.noPerformanceNumber)
									that.parent().parent().find('.noTimePaytw').text(result.data.noTimePay)
									that.parent().parent().find('.timePaytw').text(result.data.timePay)
									layer.close(index);
									}else{
										layer.msg("修改失败！", {icon: 1});
										layer.close(index);
									}
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
					}
				})
			}
			this.events = function(){
				//导出
				$('#export').on('click',function(){
					var index; 
					var a=$("#startTimeth").val();
					var c= $("#endTimeth").val();
					var d=$("#usernameth").val();
					var e=$("#code").val();
					location.href="${ctx}/excel/importExcel/DownCollectPay?orderTimeBegin="+a+"&orderTimeEnd="+c+"&type="+2+"&addNumber="+d+"&noPerformancePay="+e;
				})
				$('.searchtask').on('click',function(){
					var data = {
							userName:$("#username").val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(),
				  			type:2,
				  	}
			
				self.loadPagination(data);
				});
				$('.searchtasktw').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:2,
				  			orderTimeBegin:$("#startTimetw").val(),
				  			orderTimeEnd:$("#endTimetw").val(), 
				  	}
			
				self.loadPaginationtw(data);
				});
				$('.searchtaskfr').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:2,
				  			userName:$("#usernamefr").val(),
				  			orderTimeBegin:$("#startTimefr").val(),
				  			orderTimeEnd:$("#endTimefr").val(), 
				  	}
			
				self.loadPaginationfv(data);
				});
				
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>
  
       
</body>

</html>