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
                                        <li class="active col-md-4"><a href="#home1" data-toggle="tab">绩效流水</a>
                                        </li>
                                        <li class="col-md-4"><a href="#profile1" data-toggle="tab">绩效汇总</a>
                                        </li>
                                        <li class="col-md-4"><a href="#profile2" data-toggle="tab">质检月产量报表</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="home1">
                                        <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-9 col-sm-9  col-md-9">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr>
								<td>我想上浮下调比例:</td><td><input type="text" name="name" id="usernameth" placeholder="请输入比例" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>无加绩的配合奖励:</td><td><input type="text" name="name" id="code" placeholder="请输入奖励" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>开始:</td>
								<td>
								<input id="startTimeth" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTimeth', istime: true, format: 'YYYY-MM-DD 00:00:00'})"> 
								</td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>结束:</td>
								<td>
								<input id="endTimeth" placeholder="请输入结束时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#endTimeth', istime: true, format: 'YYYY-MM-DD 23:59:59'})">
								</td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-default btn-square btn-sm btn-3d searchtaskth">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
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
									<button type="button" class="btn btn-default btn-square btn-sm btn-3d searchtask">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
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
									<button type="button" class="btn btn-default btn-square btn-sm btn-3d searchtasktw">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
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
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>
                        </div>
            </section>
        </section>





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
				  		type:1,

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
				  		type:1,
				  		orderTimeBegin:firstdate,
				  		orderTimeEnd:lastdate,	
				} 
			 
			 var datatw={
					 type:1,
				  	orderTimeBegin:a,
				  	orderTimeEnd:b,
			 }
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(date);
				self.loadPaginationtw(datatw);
				self.loadPaginationth(data);
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
				  			type:1,
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
		      				+'<td class="text-center  ">'+o.addPerformancePay+'</td>'
		      				+'<td class="text-center  ">'+o.noPerformanceNumber+'</td>'
		      				+'<td class="text-center  ">'+o.noTimePay+'</td>'
		      				+'<td class="text-center  ">'+o.timePay+'</td>'
		      				+'<td class="text-center"> <button class="btn btn-sm btn-warning btn-3d updateremake" data-id='+o.id+'>编辑</button></td></tr>'
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
			this.loadEvents = function(){
				//修改方法
				$('.updateremake').on('click',function(){
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
									$(".searchtaskth").click()
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
				$('.searchtask').on('click',function(){
					var data = {
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
				  			type:1,
				  			orderTimeBegin:$("#startTimetw").val(),
				  			orderTimeEnd:$("#endTimetw").val(), 
				  	}
			
				self.loadPaginationtw(data);
				});
				
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>
  
       
</body>

</html>