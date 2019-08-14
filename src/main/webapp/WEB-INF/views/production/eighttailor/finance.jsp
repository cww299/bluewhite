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
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/layer/layer.js"></script>	
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
	<link rel="stylesheet" href="${ctx }/static/css/main.css">
	<script src="${ctx }/static/js/laypage/laypage.js"></script>
	<script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
	<section id="main-wrapper" class="theme-default">

	<%-- 	<%@include file="../../decorator/leftbar.jsp"%> --%>

		<!--main content start-->

		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						
						<div class="panel-body">
							<div class="tab-wrapper tab-primary">
								<ul class="nav nav-tabs col-md-12">
									<li class="active col-md-4"><a href="#home1"
										data-toggle="tab">A工资流水详情</a></li>
									<li class="col-md-4" id="profiles"><a href="#profile1" data-toggle="tab">B工资流水详情</a>
									</li>
									<li class="col-md-4" id="profiles2"><a href="#profile2" data-toggle="tab">杂工资流水详情</a>
									</li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="home1">
										<!--查询开始  -->
										<div class="row" style="height: 30px; margin: 15px 0 10px">
											<div class="col-xs-8 col-sm-8  col-md-8">
												<form class="form-search">
													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12">
															<div class="input-group">
																<table>
																	<tr>
																		<td>姓名:</td>
																		<td><input type="text" name="name"
																			id="usernameth" placeholder="请输入姓名"
																			class="form-control search-query name" /></td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>开始:</td>
																		<td><input id="startTimeth" placeholder="请输入开始时间"
																			class="form-control laydate-icon"
																			onClick="laydate({elem: '#startTimeth', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
																		</td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>结束:</td>
																		<td><input id="endTimeth" placeholder="请输入结束时间"
																			class="form-control laydate-icon"
																			onClick="laydate({elem: '#endTimeth', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
																		</td>
																	</tr>
																</table>
																<span class="input-group-btn">
																	<button type="button"
																		class="btn btn-info btn-square btn-sm btn-3d searchtaskth">
																		查&nbsp找</button>
																</span>
																<td>&nbsp&nbsp&nbsp&nbsp</td> <span
																	class="input-group-btn">
																	<button type="button"
																		class="btn btn-danger  btn-sm btn-3d start">
																		一键删除</button>
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
													<th class="center"><label> <input
															type="checkbox" class="ace checks" /> <span class="lbl"></span>
													</label></th>
													<th class="text-center">姓名</th>
													<th class="text-center">考勤日期</th>
													<th class="text-center">工作小时</th>
													<th class="text-center">出勤时间</th>
													<th class="text-center">加班时间</th>
													<th class="text-center">同种最高工资</th>
													<th class="text-center">未拿到差价</th>
													<th class="text-center">到岗预计每小时收入</th>
													<th class="text-center">A工资</th>
													<th class="text-center">操作</th>
												</tr>
											</thead>
											<tbody id="tablecontentth">

											</tbody>
											<thead>
											<tr>
												<td class="text-center">合计</td>
												<td class="text-center"></td>
												<td class="text-center"></td>
												<td class="text-center" id="totale"></td>
												<td class="text-center"></td>
												<td class="text-center"></td>
												<td class="text-center"></td>
												<td class="text-center"></td>
												<td class="text-center"></td>
												<td class="text-center" id="totaltw"></td>
											</tr>
										</thead>
										</table>
										<div id="pagerth" class="pull-right"></div>
									</div>
									<!-- B工资流水开始 -->
									<div class="tab-pane" id="profile1">
										<!--查询开始  -->
										<div class="row" style="height: 30px; margin: 15px 0 10px">
											<div class="col-xs-12 col-sm-12  col-md-12">
												<form class="form-search">
													<div class="row">
														<div class="col-xs-11 col-sm-11 col-md-11">
															<div class="input-group">
																<table>
																	<tr>
																		<td>批次:</td>
																		<td><input type="text" name="number" id="number"
																			placeholder="请输入批次号"
																			class="form-control search-query number" /></td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>产品:</td>
																		<td><input type="text" name="name" id="name"
																			placeholder="请输入产品名称"
																			class="form-control search-query name" /></td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>姓名:</td>
																		<td><input type="text" name="name" id="username"
																			placeholder="请输入姓名"
																			class="form-control search-query name" /></td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>开始:</td>
																		<td><input id="startTime" placeholder="请输入开始时间"
																			class="form-control laydate-icon"
																			onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
																		</td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>结束:</td>
																		<td><input id="endTime" placeholder="请输入结束时间"
																			class="form-control laydate-icon"
																			onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
																		</td>
																	</tr>
																</table>
																<span class="input-group-btn">
																	<button type="button"
																		class="btn btn-info btn-square btn-sm btn-3d searchtask">
																		查找 <i class="icon-search icon-on-right bigger-110"></i>
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
													<th class="text-center">工序名</th>
													<th class="text-center">时间</th>
													<th class="text-center">加绩工资</th>
													<th class="text-center">B工资</th>
												</tr>
											</thead>
											<tbody id="tablecontent">

											</tbody>
											<thead>
												<tr>
													<td class="text-center">合计</td>
													<td class="text-center"></td>
													<td class="text-center"></td>
													<td class="text-center"></td>
													<td class="text-center"></td>
													<td class="text-center" id="total2"></td>
													<td class="text-center" id="total"></td>
												</tr>
						</thead>
										</table>
										<div id="pager" class="pull-right"></div>
									</div>
									<!-- B工资流水结束 -->
									<div class="tab-pane" id="profile2">
										<!--查询开始  -->
										<div class="row" style="height: 30px; margin: 15px 0 10px">
											<div class="col-xs-8 col-sm-8  col-md-8">
												<form class="form-search">
													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12">
															<div class="input-group">
																<table>
																	<tr>
																		<td>工序:</td>
																		<td><input type="text" name="number"
																			id="numbertw" placeholder="请输入工序名"
																			class="form-control search-query number" /></td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>姓名:</td>
																		<td><input type="text" name="name"
																			id="usernametw" placeholder="请输入姓名"
																			class="form-control search-query name" /></td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>开始:</td>
																		<td><input id="startTimetw" placeholder="请输入开始时间"
																			class="form-control laydate-icon"
																			onClick="laydate({elem: '#startTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
																		</td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>结束:</td>
																		<td><input id="endTimetw" placeholder="请输入结束时间"
																			class="form-control laydate-icon"
																			onClick="laydate({elem: '#endTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
																		</td>
																	</tr>
																</table>
																<span class="input-group-btn">
																	<button type="button"
																		class="btn btn-info btn-square btn-sm btn-3d searchtasktw">
																		查找 <i class="icon-search icon-on-right bigger-110"></i>
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
													<th class="text-center">杂工B工资</th>
												</tr>
											</thead>
											<tbody id="tablecontenttw">

											</tbody>
											<thead>
											<tr>
												<td class="text-center">合计</td>
												<td class="text-center"></td>
												<td class="text-center"></td>
												<td class="text-center" id="totaltr"></td>
				
											</tr>
						</thead>
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
		  	this.getCount = function(){
		  		return _count;
		  	}
		  	this.setCount = function(count){
		  		_count=count;
		  	}
			 var data={
						page:1,
				  		size:13,	
				  		type:5,
				  		sign:1,
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
				$('#startTimetw').val(firstdate);
				$('#endTimetw').val(lastdate);
				$('#startTimeth').val(firstdate);
				$('#endTimeth').val(lastdate);
				var data={
						page:1,
				  		size:13,	
				  		type:5,
				  		sign:1,
				  		orderTimeBegin:$("#startTimeth").val(),
			  			orderTimeEnd:$("#endTimeth").val(),
				}
			this.init = function(){
				
				//注册绑定事件
				self.events();
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
		      				 var a;
		      				 if(o.performancePayNumber==null){
		      					 a=0;
		      				 }else{
		      					 a=o.performancePayNumber
		      				 }
		      				html +='<tr>'
		      				+'<td class="text-center edit ">'+o.userName+'</td>'
		      				+'<td class="text-center edit ">'+o.bacth+'</td>'
		      				+'<td class="text-center edit ">'+o.productName+'</td>'
		      				+'<td class="text-center edit ">'+o.task.procedureName+'</td>'
		      				+'<td class="text-center edit ">'+o.allotTime+'</td>'
		      				+'<td class="text-center edit ">'+parseFloat((a).toFixed(3))+'</td>'
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
									  		type:5,
									  		type:5,
								  			productName:$('#name').val(),
								  			userName:$('#username').val(),
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
			      			$("#totaltr").text(result.data.statData.statAmount)
			      			 $(result.data.rows).each(function(i,o){
			      				htmltw +='<tr>'
			      				+'<td class="text-center edit ">'+o.userName+'</td>'
			      				+'<td class="text-center edit ">'+o.taskName+'</td>'
			      				+'<td class="text-center edit ">'+o.allotTime+'</td>'
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
										  		type:5,
										  		userName:$('#usernametw').val(),
									  			taskName:$('#numbertw').val(),
									  			orderTimeBegin:$("#startTimetw").val(),
									  			orderTimeEnd:$("#endTimetw").val(),
									  	}
							        
							            self.loadPaginationtw(_data);
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
			  this.checkedd=function(){
					
					$(".checks").on('click',function(){
						
	                    if($(this).is(':checked')){ 
				 			$('.checkboxId').each(function(){  
	                    //此处如果用attr，会出现第三次失效的情况  
	                     		$(this).prop("checked",true);
				 			})
	                    }else{
	                    	$('.checkboxId').each(function(){ 
	                    		$(this).prop("checked",false);
	                    		
	                    	})
	                    }
	                }); 
					
				}
			this.loadPaginationth=function(data){
				//A工资
				var index;
			    var htmlth = '';
			    $.ajax({
				      url:"${ctx}/finance/allAttendancePay",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			$("#totale").text(result.data.statData.stateCount)
		      			  $("#totaltw").text(result.data.statData.statAmount)
		      			 $(result.data.rows).each(function(i,o){
		      				 if(o.overTime==null){
		      					o.overTime=0
		      				 }
		      				 if(o.dutyTime==null){
		      					o.dutyTime=0
		      				 }
		      				
		      				
		      				htmlth +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center  ">'+o.userName+'</td>'
		      				+'<td class="text-center ">'+o.allotTime+'</td>'
		      				+'<td class="text-center edit workTime">'+o.workTime+'</td>'
		      				+'<td class="text-center edit turnWorkTime">'+o.turnWorkTime+'</td>'
		      				+'<td class="text-center edit overtimes">'+o.overTime+'</td>'
		      				+'<td class="text-center ">'+o.maxPay+'</td>'
		      				+'<td class="text-center ">'+o.disparity+'</td>'
		      				+'<td class="text-center  ">'+o.workPrice+'</td>'
		      				+'<td class="text-center  ">'+o.payNumber+'</td>'
		      				+'<td class="text-center"> <button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button> </td></tr>'
		      			}); 
		      			self.setCount(result.data.pageNum)
				        //显示分页
					   	 laypage({
					      cont: 'pagerth', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		 
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		type:5,
									  		sign:1,
									  		userName:$('#usernameth').val(),
								  			orderTimeBegin:$("#startTimeth").val(),
								  			orderTimeEnd:$("#endTimeth").val(),
								  	}
						        
						            self.loadPaginationth(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontentth").html(htmlth); 
					   	self.loadEvents();
					   	self.checkedd();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
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
									workTime:$(this).parent().parent('tr').find(".workTime").text(),
									turnWorkTime:$(this).parent().parent('tr').find(".turnWorkTime").text(),
									overtime:$(this).parent().parent('tr').find(".overtimes").text(),
							}
							var index;
							
							$.ajax({
								url:"${ctx}/finance/updateAttendance",
								data:postData,
								type:"GET",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									if(0==result.code){
									layer.msg("修改成功！", {icon: 1});
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
				$('.start').on('click',function(){
					  var  that=$(".table-hover");
					  var arr=new Array()//员工id
					  	that.parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  var postData = {
								ids:arr,
						}
						
						var index;
						 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/finance/deleteAttendance",
							data:postData,
							traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
								layer.msg("删除成功！", {icon: 1});
								var data = {
					        			page:self.getCount(),
								  		size:13,
								  		type:5,
								  		sign:1,
								  		userName:$('#usernameth').val(),
							  			orderTimeBegin:$("#startTimeth").val(),
							  			orderTimeEnd:$("#endTimeth").val(),
							  	}
								self.loadPaginationth(data)
								layer.close(index);
								}else{
									layer.msg("删除失败！", {icon: 2});
									layer.close(index);
								}
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 })
				})
				$('.searchtask').on('click',function(){
					$("#total").text("计算中...")
	      			 $("#total2").text("计算中...")
					var data = {
				  			page:1,
				  			size:13,
				  			type:5,
				  			productName:$('#name').val(),
				  			userName:$('#username').val(),
				  			bacth:$('#number').val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  	}
					$.ajax({
					      url:"${ctx}/finance/allPayBSum",
					      data:data,
					      type:"GET",
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			 $("#total").text(result.data.sumPayNumber)
			      			 $("#total2").text(result.data.sumPerformancePayNumber)
					      },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);
						  }
					  });
				self.loadPagination(data);
				});
				$('.searchtasktw').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:5,
				  			userName:$('#usernametw').val(),
				  			taskName:$('#numbertw').val(),
				  			orderTimeBegin:$("#startTimetw").val(),
				  			orderTimeEnd:$("#endTimetw").val(), 
				  	}
			
				self.loadPaginationtw(data);
				});
				$('.searchtaskth').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:5,
				  			userName:$('#usernameth').val(),
				  			orderTimeBegin:$("#startTimeth").val(),
				  			orderTimeEnd:$("#endTimeth").val(), 
				  	}
			
				self.loadPaginationth(data);
				});
				
				
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>


</body>

</html>