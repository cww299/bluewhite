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
                                        <li class="active col-md-4"><a href="#home1" data-toggle="tab">A工资流水详情</a>
                                        </li>
                                        <li class="col-md-4"><a href="#profile1" data-toggle="tab">B工资流水详情</a>
                                        </li>
                                        <li class="col-md-4"><a href="#profile2" data-toggle="tab">杂工资流水详情</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="home1">
                                            <p>1
                                            </p>
                                        </div>
                     <!-- B工资流水开始 -->
            <div class="tab-pane" id="profile1">
                      <!--查询开始  -->
          		 <div class="row" style="height: 30px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr><td>批次:</td><td><input type="text" name="number" id="number" placeholder="请输入批次号" class="form-control search-query number" /></td>
								<td>产品:</td><td><input type="text" name="name" id="name" placeholder="请输入产品名称" class="form-control search-query name" /></td>
								<td>姓名:</td><td><input type="text" name="name" id="username" placeholder="请输入姓名" class="form-control search-query name" /></td>
								<td>开始:</td>
								<td>
								<input id="startTime" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
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
                                        	<th class="text-center">批次号</th>
                                            <th class="text-center">产品名</th>
                                            <th class="text-center">时间</th>
                                            <th class="text-center">B工资</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent">
                                        
                                    </tbody>
                                </table>
                                <div id="pager" class="pull-right"></div>
                                 </div>
                                 <!-- B工资流水结束 -->
                                    <div class="tab-pane" id="profile2">
                                     <!--查询开始  -->
          		 <div class="row" style="height: 30px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr><td>工序:</td><td><input type="text" name="number" id="numbertw" placeholder="请输入工序名" class="form-control search-query number" /></td>
								<td>姓名:</td><td><input type="text" name="name" id="usernametw" placeholder="请输入姓名" class="form-control search-query name" /></td>
								<td>开始:</td>
								<td>
								<input id="startTimetw" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
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
                                        	<th class="text-center">姓名</th>
                                        	<th class="text-center">杂工工序名</th>
                                            <th class="text-center">时间</th>
                                            <th class="text-center">杂工加绩工资</th>
                                            <th class="text-center">杂工B工资</th>
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
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
				self.loadPaginationtw(data);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html = '';
			    //B工资流水开始
			    $.ajax({
				      url:"${ctx}/finance/allPayB",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				html +='<tr>'
		      				+'<td class="text-center edit ">'+o.userName+'</td>'
		      				+'<td class="text-center edit ">'+o.bacth+'</td>'
		      				+'<td class="text-center edit ">'+o.productName+'</td>'
		      				+'<td class="text-center edit ">'+o.allotTime+'</td>'
		      				+'<td class="text-center edit ">'+parseFloat((o.payNumber).toFixed(3))+'</td></tr>'
							
		      			}); 
				        //显示分页
					   	 laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		 
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		type:1,
									  		productName:$('#name').val(),
								  			bacth:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			  //B工资流水结束
			}
			  this.loadPaginationtw = function(data){
				//杂工工资流水开始
				    var index;
				    var htmltw = '';
				    $.ajax({
					      url:"${ctx}/finance/allFarragoTaskPay",
					      data:data,
					      type:"GET",
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			  console.log(result)
			      			 $(result.data.rows).each(function(i,o){
			      				htmltw +='<tr>'
			      				+'<td class="text-center edit ">'+o.userName+'</td>'
			      				+'<td class="text-center edit ">'+o.taskName+'</td>'
			      				+'<td class="text-center edit ">'+o.allotTime+'</td>'
			      				+'<td class="text-center edit ">'+parseFloat((o.performancePayNumber).toFixed(3))+'</td>'
			      				+'<td class="text-center edit ">'+parseFloat((o.payNumber).toFixed(3))+'</td></tr>'
								
			      			}); 
					        //显示分页
						   	 laypage({
						      cont: 'pagertw', 
						      pages: result.data.totalPages, 
						      curr:  result.data.pageNum || 1, 
						      jump: function(obj, first){ 
						    	  if(!first){ 
						    		 
							        	var _data = {
							        			page:obj.curr,
										  		size:13,
										  		type:1,
										  		productName:$('#name').val(),
									  			bacth:$('#number').val(),
									  			orderTimeBegin:$("#startTime").val(),
									  			orderTimeEnd:$("#endTime").val(),
									  	}
							        
							            self.loadPagination(_data);
								     }
						      }
						    });  
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
				  			page:1,
				  			size:13,
				  			type:1,
				  			productName:$('#name').val(),
				  			userName:$('#username').val(),
				  			bacth:$('#number').val(),
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
				  			userName:$('#usernametw').val(),
				  			taskName:$('#numbertw').val(),
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