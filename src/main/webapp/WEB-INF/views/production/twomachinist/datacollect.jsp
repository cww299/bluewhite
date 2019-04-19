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
	<section id="main-wrapper" class="theme-default">

		<%-- <%@include file="../../decorator/leftbar.jsp"%> --%>

		<!--main content start-->

		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">数据汇总详细</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i> <i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="panel-body">
							<div class="tab-wrapper tab-primary">
								<ul class="nav nav-tabs col-md-12">
									<li class="active col-md-6"><a href="#home1"
										data-toggle="tab">生产成本数据汇总</a></li>
									<li class="col-md-6"><a href="#profile1" data-toggle="tab">员工成本数据汇总</a>
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
																		<td>开始时间:</td>
																		<td><input id="startTimeth" placeholder="请输入开始时间"
																			class="form-control laydate-icon"
																			onClick="laydate({elem: '#startTimeth', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
																		</td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>结束时间:</td>
																		<td><input id="endTimeth" placeholder="请输入结束时间"
																			class="form-control laydate-icon"
																			onClick="laydate({elem: '#endTimeth', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
																		</td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td><input type="checkbox" id="check" value="1">详情</td>
																	</tr>
																</table>
																<span class="input-group-btn">
																	<button type="button"
																		class="btn btn-info btn-square btn-sm btn-3d searchtaskth">
																		查&nbsp找</button>
																</span>
															</div>
														</div>
													</div>
												</form>
											</div>
										</div>
										<!-- 查询结束 -->

										<table class="table table-hover">

											<tbody id="tablecontentth">

											</tbody>
										</table>
										<div id="pagerth" class="pull-right"></div>
									</div>
									<!-- B工资流水开始 -->
									<div class="tab-pane" id="profile1">
										<!--查询开始  -->
										<div class="row" style="height: 30px; margin: 15px 0 10px">
											<div class="col-xs-8 col-sm-8  col-md-8">
												<form class="form-search">
													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12">
															<div class="input-group">
																<table>
																	<tr>
																		<td>股东占比:</td>
																		<td><input type="text" name="number" id="number"
																			placeholder="请输入批次号"
																			class="form-control search-query number" /></td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>开始时间:</td>
																		<td><input id="startTime" placeholder="请输入开始时间"
																			class="form-control laydate-icon"
																			onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
																		</td>
																		<td>&nbsp&nbsp&nbsp&nbsp</td>
																		<td>结束时间:</td>
																		<td><input id="endTime" placeholder="请输入结束时间"
																			class="form-control laydate-icon"
																			onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
																		</td>
																	</tr>
																</table>
																<span class="input-group-btn">
																	<button type="button"
																		class="btn btn-info btn-square btn-sm btn-3d searchtask">
																		查&nbsp找</button>
																</span>
															</div>
														</div>
													</div>
												</form>
											</div>
										</div>
										<!-- 查询结束 -->
										<table class="table table-hover">

											<tbody id="tablecontent">

											</tbody>
										</table>
										<div id="pager" class="pull-right"></div>
									</div>
									<!-- B工资流水结束 -->
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
			 var data={
				  		type:4,

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
			    var htmlth = '';
			    //B工资流水开始
			    $.ajax({
				      url:"${ctx}/finance/collectInformation",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			html +='<tr>'
			      				+'<td class="edit">打算给予A汇总</td>'
			      				+'<td class="edit">'+result.data.sumAttendancePay+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">我们可以给予一线的</td>'
			      				+'<td class="edit">'+result.data.giveThread+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">一线剩余给我们</td>'
			      				+'<td class="edit">'+result.data.surplusThread+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">管理费汇总</td>'
			      				+'<td class="edit">'+result.data.manage+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">考虑管理费，预留在手等。可调配资金</td>'
			      				+'<td class="edit">'+result.data.deployPrice+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit"> 模拟得出可调配资金</td>'
			      				+'<td class="edit">'+result.data.analogDeployPrice+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">从A考勤开始日期以消费的房租</td>'
			      				+'<td class="edit">'+result.data.sumChummage+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">从A考勤开始日期以消费的水电</td>'
			      				+'<td class="edit">'+result.data.sumHydropower+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">从A考勤开始日期以消费的后勤</td>'
			      				+'<td class="edit">'+result.data.sumLogistics+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit"> 模拟当月非一线人员发货绩效</td>'
			      				+'<td class="edit">'+result.data.analogPerformance+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">剩余净管理</td>'
			      				+'<td class="edit">'+result.data.surplusManage+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">净管理费给付比→</td>'
			      				+'<td class="edit">'+result.data.manageProportion+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">从开始日至今可发放管理费加绩比</td>'
			      				+'<td class="edit">'+result.data.managePerformanceProportion+'</td>'
			      				+'</tr>'
			      				+'<tr>' 
			      				+'<td class="edit">模拟当月非一线人员出勤小时</td>'
			      				+'<td class="edit">'+result.data.analogTime+'</td>'
			      				+'</tr>'
			      				+'<td class="edit">每小时可发放</td>'
			      				+'<td class="edit">'+result.data.grant+'</td>'
			      				+'</tr>'
			      				+'<td class="edit">给付后车间剩余</td>'
			      				+'<td class="edit">'+result.data.giveSurplus+'</td>'
			      				+'</tr>'
			      				+'<td class="edit">其中股东占比</td>'
			      				+'<td class="edit">'+result.data.shareholderProportion+'</td>'
			      				+'</tr>'
			      				+'<td class="edit">其中股东收益</td>'
			      				+'<td class="edit">'+result.data.shareholder+'</td>'
			      				+'</tr>'
			      				+'<td class="edit">车间剩余</td>'
			      				+'<td class="edit">'+result.data.workshopSurplus+'</td>'
			      				+'</tr>'
					   	 $("#tablecontent").html(html); 
		      			htmlth +='<tr>'
		      				+'<td class="edit">全表加工费  汇总</td>'
		      				+'<td class="edit">'+result.data.sumTask+'</td>'
		      				+'</tr>'
		      				+'<tr>' 
		      				+'<td class="edit">返工费 汇总</td>'
		      				+'<td class="edit">'+result.data.sumTaskFlag+'</td>'
		      				+'</tr>'
		      				+'<tr>' 
		      				+'<td class="edit">杂工费 汇总</td>'
		      				+'<td class="edit">'+result.data.sumFarragoTask+'</td>'
		      				+'</tr>'
		      				+'<tr>' 
		      				+'<td class="edit">全表加工费,返工费和杂工费汇总</td>'
		      				+'<td class="edit">'+result.data.priceCollect+'</td>'
		      				+'</tr>'
		      				+'<tr>' 
		      				+'<td class="edit">不予给付汇总占比</td>'
		      				+'<td class="edit">'+result.data.proportion+'</td>'
		      				+'</tr>'
		      				+'<tr>' 
		      				+'<td class="edit">预算多余在手部分</td>'
		      				+'<td class="edit">'+result.data.overtop+'</td>'
		      				+'</tr>'
		      				+'<tr>' 
		      				+'<td class="edit">各批次地区差价汇总(不予给付汇总)</td>'
		      				+'<td class="edit">'+result.data.regionalPrice+'</td>'
		      				+'</tr>'
				   	layer.close(index);
				   	 $("#tablecontentth").html(htmlth);
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			  //B工资流水结束
			}
			this.events = function(){
				$('.searchtask').on('click',function(){
					var status;
					if($("#check").is(':checked')==true){
						status=1
					}else{
						status=""
					}
					var data = {
					  		type:4,
				  			shareholderProportion:$('#number').val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  			status:status
				  	}
			
				self.loadPagination(data);
				});
				$('.searchtaskth').on('click',function(){
					var status;
					if($("#check").is(':checked')==true){
						status=1
					}else{
						status=""
					}
					var data = {
					  		type:4,
				  			orderTimeBegin:$("#startTimeth").val(),
				  			orderTimeEnd:$("#endTimeth").val(), 
				  			status:status
				  	}
			
				self.loadPagination(data);
				});
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>


</body>

</html>