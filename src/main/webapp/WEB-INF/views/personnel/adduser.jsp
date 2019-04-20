<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>员工考勤</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	
	<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>  <!-- 时间插件 -->
	<script src="${ctx }/static/js/layer/layer.js"></script>
	<script src="${ctx }/static/js/laypage/laypage.js"></script> 
	<link rel="stylesheet" href="${ctx }/static/css/main.css">
	<script src="${ctx}/static/js/common/autoheight.js"></script>  
</head>

<body>
	<section id="main-wrapper" class="theme-default">
		<!--main content start-->
		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">考勤信息</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i> <i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="row" style="height: 30px; margin: 15px 0 10px">
							<div class="col-xs-12 col-sm-12  col-md-12">
								<form class="form-search">
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12">
											<div class="input-group">
												<table>
													<tr>
														<td>员工姓名:</td>
														<td><input type="text" id="name"
															class="form-control name" /></td>
														<td>&nbsp&nbsp</td>
														<td>员工部门:</td>
														<td id="department"></td>
														<td>&nbsp&nbsp</td>
														<td>开始时间:</td>
														<td><input id="startTime" placeholder="请输入签到开始时间"
															class="form-control laydate-icon"
															onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
														</td>
														<td>&nbsp&nbsp</td>
														<td>结束时间:</td>
														<td><input id="endTime" placeholder="请输入签到结束时间"
															class="form-control laydate-icon"
															onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
														</td>
													</tr>
												</table>
												<span class="input-group-btn">
													<button type="button"
														class="btn btn-default btn-square btn-sm btn-3d  searchtask">
														查&nbsp找</button>
												</span> &nbsp <span class="input-group-btn">
													<button type="button" id="synchronization"
														class="btn btn-success btn-sm btn-3d pull-right">人员同步</button>
												</span> &nbsp <span class="input-group-btn">
													<button type="button" id="export"
														class="btn btn-success btn-sm btn-3d pull-right">导出签到</button>
												</span> &nbsp <span class="input-group-btn">
													<button type="button" id="synchronization2"
														class="btn btn-danger btn-sm btn-3d pull-right">考勤重置</button>
												</span>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="panel-body">
							<table class="table table-hover">
								<thead>
									<tr>
										<th class="text-center">员工编号</th>
										<th class="text-center">员工姓名</th>
										<th class="text-center">签到时间</th>
										<th class="text-center">验证方式</th>
										<th class="text-center">签到状态</th>
									</tr>
								</thead>
								<tbody id="tablecontent">

								</tbody>
							</table>
							<div id="pager" class="pull-right"></div>
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
		  	this.setIndex = function(index){
		  		_index=index;
		  	}
		  	
		  	this.getIndex = function(){
		  		return _index;
		  	}
		  	this.setName = function(name){
		  		_name=name;
		  	}
		  	this.getName = function(){
		  		return _name;
		  	}
			 var data={
						page:1,
				  		size:13,	
				} 
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/personnel/findPageAttendance",
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
		      				 if(o.verifyMode==1){
		      					a="指纹识别"
		      				 }else if(o.verifyMode==2){
		      					 a="打卡验证"
		      				 }else if(o.verifyMode==0){
		      					 a="密码验证"
		      				 }else if(o.verifyMode==null){
		      					 a=""
		      				 }else{
		      					a="面部验证"
		      				 }
		      				 var b;
		      				 if(o.inOutMode==null){
		      					b="暂无"
		      				 } else if(o.inOutMode==0){
		      					 b="上班签到"
		      				 }else if(o.inOutMode==1){
		      					 b="下班签到"
		      				 }else{
		      					b="补签"
		      				 }
		      				 
		      				html +='<tr>'
		      				+'<td class="text-center">'+o.number+'</td>'
		      				+'<td class="text-center edit name">'+(o.user == null ? "" : o.user.userName)+'</td>'
		      				+'<td class="text-center">'+o.time+'</td>'
		      				+'<td class="text-center">'+a+'</td>'
		      				+'<td class="text-center">'+b+'</td></tr>'
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
									  		userName:$('#name').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			orgNameId:$(".selectgroupChange").val(),
								  	}
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	$("#tablecontent").html(html); 
					   	self.loadEvents();
					   
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			
			this.loadEvents = function(){
				
			}
			this.chang=function(){
			}
			

			this.events = function(){
				
				/*同步*/
				$('#synchronization').on('click',function(){
					var postData={
							startTime:$("#startTime").val(),
							endTime: $("#endTime").val(),
					}
				  $.ajax({
						url:"${ctx}/personnel/fixAttendance",
						data:postData,
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						success:function(result){
							if(0==result.code){
								layer.msg(result.message, {icon: 1});
								var data = {
							  			page:1,
							  			size:13,
							  			userName:$('#name').val(),
							  			orderTimeBegin:$("#startTime").val(),
							  			orderTimeEnd:$("#endTime").val(),
							  			orgNameId:$(".selectgroupChange").val(),
							  	}
					            self.loadPagination(data);
								layer.close(index);
							}else{
								layer.msg(result.message, {icon: 2});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
				})
				//考勤重置
				$('#synchronization2').on('click',function(){
					layer.open({
						   title: '提示'
						  ,content:'确定重置考勤吗？'
						  ,btn: ['确认', '取消']
						,yes: function(index, layero){
					var postData={
							startTime:$("#startTime").val(),
							endTime: $("#endTime").val(),
					}
							 $.ajax({
									url:"${ctx}/personnel/restAttendance",
									data:postData,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										if(0==result.code){
											layer.msg(result.message, {icon: 1});
											var data = {
										  			page:1,
										  			size:13,
										  			userName:$('#name').val(),
										  			orderTimeBegin:$("#startTime").val(),
										  			orderTimeEnd:$("#endTime").val(),
										  			orgNameId:$(".selectgroupChange").val(),
										  	}
								            self.loadPagination(data);
											layer.close(index);
										}else{
											layer.msg(result.message, {icon: 2});
											layer.close(index);
										}
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
			       			 }
						}); 
				 
				})
				
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			userName:$('#name').val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(),
				  			orgNameId:$(".selectgroupChange").val(),
				  	}
		            self.loadPagination(data);
				});
				
				//导出签到
				$('#export').on(
						'click',
						function() {
							//参数
							var userName = $('#name').val();
							var orgNameId = $(".selectgroupChange").val();
							var orderTimeBegin = $("#startTime").val();
							var orderTimeEnd = $("#endTime").val();
							location.href = "${ctx}/excel/importExcel/personnel/DownAttendanceSign?userName=" + userName + "&orgNameId=" + orgNameId + "&orderTimeBegin=" + orderTimeBegin
									+ "&orderTimeEnd=" + orderTimeEnd + "";
						})
				
				
				var indextwo;
			    var htmltwo = '';
			    var htmlth = '';
			    var htmlfr = '';
			    var html = '';
			    var htmlthh= '';
			    var htmlthhh= '';
				    var getdata={type:"orgName",}
	      			$.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdata,
					      type:"GET",
					      beforeSend:function(){
					    	  indextwo = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfr +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });
			      			var htmlth='<select class="form-control  selectgroupChange"><option value="">请选择</option>'+htmlfr+'</select>'
			      			$("#department").html(htmlth);
					      }
					  });
				
			}
   	}
   			var login = new Login();
				login.init();
			})
    </script>
</body>
<script type="text/javascript">
$(function(){
	  var height = $(document).height();
		window.parent.changeHeight(height);
		$("body").bind('resize',function(){
		    var height = $(document).height();
			window.parent.changeHeight(height);
		});
}); 
</script>
</html>