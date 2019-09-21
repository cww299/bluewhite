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
<title>批次管理</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	
	<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx }/static/css/main.css">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>  <!-- 时间插件 -->
	<script src="${ctx }/static/js/layer/layer.js"></script>
	<script src="${ctx }/static/js/laypage/laypage.js"></script> 
	<script src="${ctx}/static/js/vendor/jquery.cookie.js"></script>
</head>

<body>

<div class="panel panel-default">			
	<div class="panel-body" style="width: 100%">
		<table>
			<tr>
				<td class="hidden-sm">批次号:</td>
				<td class="hidden-sm"><input type="text" name="number" id="number" placeholder="请输入批次号"
					class="form-control search-query number" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>产品名称:</td>
				<td><input type="text" name="name" id="name" placeholder="请输入产品名称"
					class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>开始时间:</td>
				<td><input id="startTime" placeholder="请输入开始时间" style="width: 250px;" class="form-control"></td>
				<td>&nbsp;&nbsp;</td>
				<td>完成状态:</td>
				<td><select class="form-control selectchoice"><option value="0">未完成</option>
						<option value="1">已完成</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><span class="input-group-btn">
						<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask">查&nbsp找</button></span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><span class="input-group-btn hidden-sm">
						<button type="button" id="addprocedure" class="btn btn-success btn-sm btn-3d pull-right">一键接收</button></span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><button type="button"
							class="btn btn-default btn-danger btn-sm btn-3d attendance2">一键删除</button>&nbsp&nbsp</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><span class="input-group-btn">
						<button type="button" class="btn btn-success  btn-sm btn-3d start">一键完成</button></span></td>
			</tr>
		</table>
		<h1 class="page-header"></h1>
		<table  class="table table-condensed table-hover" style="width: 100%"><!-- class="table table-condensed table-hover" --> 
			<thead>
				<tr>
					<th class="center"><label> <input type="checkbox"
							class="ace checkstw" /> <span class="lbl"></span>
					</label></th>
					<th class="text-center hidden-sm">批次号</th>
					<th class="text-center">时间</th>
					<th class="text-center" style="width: 240px;">产品名</th>
					<th class="text-center">数量</th>
					<th class="text-center hidden-sm">预计生产单价</th>
					<th class="text-center hidden-sm">外发价格</th>
					<th class="text-center hidden-sm">任务价值</th>
					<th class="text-center hidden-sm">地区差价</th>
					<th class="text-center">当批用时</th>
					<th class="text-center">备注</th>
					<th class="text-center">状态</th>
					<th class="text-center" style="width: 170px;">操作</th>
					<th class="text-center"></th>
					
				</tr>
			</thead>
			<tbody id="tablecontent" style="width: 100%">
			</tbody>
			<thead>
				<tr>
					<td class="center">合计</td>
					<td class="text-center hidden-sm"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center" id="total"></td>	
					<td class="text-center hidden-sm"></td>
					<td class="text-center hidden-sm"></td>
					<!-- <td class="text-center" id="totaltw"></td> -->  <!-- 修改此处 -->
					<td class="text-center hidden-sm" id="tota2">1</td>
					<td class="text-center hidden-sm"></td>
					<td class="text-center" id="tota3">1</td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
				</tr>
			</thead>
		</table>
		<div id="pager" class="pull-right"></div>
	</div>
</div>
				
	<!--隐藏框 工序分配开始  -->
	<div id="addDictDivType" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeForm">
				<div class="row col-xs-12  col-sm-12  col-md-12 ">


					<div class="form-group">
						<label class="col-sm-2 col-md-2 control-label">任务数量:</label>
						<div class="col-sm-4 col-md-4">
							<input type="text" class="form-control sumnumber">
						</div>
						<div>
							<label class="col-sm-2 col-md-2 control-label">预计完成时间:</label>
							<div class="col-sm-4 col-md-3">
								<input type="text" placeholder="非返工任务不填写"
									class="form-control sumtime">
							</div>
						</div>
					</div>


					<div class="form-group">
						<label class="col-sm-2 control-label col-md-2">任务分配:</label>
						<div class="col-sm-4 col-md-3">
							<input id="Time" placeholder="时间可不填"
								class="form-control">
						</div>
						<div class="col-sm-2 hidden-sm col-md-1" >
							<input type="checkbox" id="remember">记住
						</div>
						<label class="col-sm-2 control-label hidden-sm col-md-2">加绩工序:</label>
						<div class="col-sm-3 workingtw hidden-sm col-md-3"></div>
					</div>


					<div class="form-group">
						<label class="text-center col-sm-2 col-md-2 control-label">工序:</label>
						<div class="col-sm-2 working"></div>
						<div class="col-sm-2 checkworking" style="width: 23%"></div>
						<label class="col-sm-1 control-label"  style="width: 12%">完成人:</label>
						<div class="col-sm-2 complete" style="width: 10%">
							<input type="text" class="form-control">
						</div>
						<div class="col-sm-2 select" id="showB"></div>
					</div>
				</div>
		</div>

		</form>
	</div>
	</div>
	<!--隐藏框 工序分配结束  -->





	<!--隐藏框 工序分配2开始  -->
	<!-- <div id="addDictDivTypetw" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeFormtw">
				<div class="row col-xs-12  col-sm-12  col-md-12 " id="tabs">


					<div class="form-group">
						<label class="col-sm-2 control-label">加绩工序:</label>
						<div class="col-sm-3 workingth"></div>
						<label class="col-sm-2 control-label">选择工序:</label>
						<div class="col-sm-3 workingtw"></div>
						<div class="col-sm-2 checkworkingtw"></div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">任务时间:</label>
						<div class="col-sm-3">
							<input id="Timet" placeholder="时间可不填"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#Timet', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
						<label class="col-sm-2 control-label">预计时间:</label>
						<div class="col-sm-3">
							<input type="text" placeholder="非返工任务不填写"
								class="form-control sumtimetw">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 col-md-2 control-label">开始时间:</label>
						<div class="col-sm-2 col-md-2">
							<input id="Timetstr" class="form-control laydate-icon"
								onClick="laydate({elem: '#Timetstr', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
						<div>
							<label class="col-sm-1 col-md-1 control-label">结束时间:</label>
							<div class="col-sm-2 col-md-2">
								<input id="Timetend" class="form-control laydate-icon"
									onClick="laydate({elem: '#Timetend', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
							</div>
						</div>
						<label class="col-sm-1 control-label">完成人:</label>
						<div class="col-sm-2 completetw">
							<input type="text" class="form-control">
						</div>
						<div class="col-sm-1 selecttw"></div>
						<div class="col-sm-2 col-md-1">
							<input type="button" class="btn btn-sm  btn-success" id="save"
								value="新增"></input>
						</div>
					</div>

				</div>
		</div>

		</form>
	</div> -->
	<!--隐藏框 工序分配2结束  -->



	<!--隐藏框已完成的批次开始  -->
	<div id="addworking" style="display: none;">
		<div class="row" style="height: 30px; margin: 15px 0 10px">
			<div class="col-xs-12 col-sm-12  col-md-12">
				<form class="form-search">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group">
								<table>
									<tr>
										<td>批次号:</td>
										<td><input type="text" name="number" id="numbertw"
											placeholder="请输入批次号" class="form-control search-query number" /></td>
										<td>&nbsp&nbsp</td>
										<td>产品名称:</td>
										<td><input type="text" name="name" id="nametw"
											placeholder="请输入产品名称" class="form-control search-query name" /></td>
										<td>&nbsp&nbsp</td>
										<td>开始时间:</td>
										<td><input id="startTimetw" placeholder="请输入开始时间"
											class="form-control laydate-icon"
											onClick="laydate({elem: '#startTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
										</td>
										<td>&nbsp&nbsp</td>
										<td>结束时间:</td>
										<td><input id="endTimetw" placeholder="请输入结束时间"
											class="form-control laydate-icon"
											onClick="laydate({elem: '#endTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
										</td>
										<td>&nbsp&nbsp</td>
										<td>完成状态:</td>
										<td><select class="form-control selectchoicetw"><option
													value="0">未完成</option>
												<option value="1">已完成</option></select></td>
									</tr>
								</table>
								<span class="input-group-btn">
									<button type="button"
										class="btn btn-info btn-square btn-sm btn-3d searchtasktw">
										查&nbsp找</button>
								</span>
								<td>&&nbsp;&&nbsp;&nbsp;&nbsp;</td> <span class="input-group-btn">
									<button type="button"
										class="btn btn-default btn-danger btn-sm btn-3d receive">一键接收</button>&nbsp&nbsp
									</td>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		
		
		<div class="panel-body" >
			<div class="form-group"></div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="center"><label> <input type="checkbox"
								class="ace checksth" /> <span class="lbl"></span>
						</label></th>
						<th class="text-center">批次号</th>
						<th class="text-center">下货时间</th>
						<th class="text-center">产品名</th>
						<th class="text-center">数量</th>
						<th class="text-center">部门</th>
						<th class="text-center">待接收数量</th>
					</tr>
				</thead>
				<tbody  id="tableworking">
				</tbody>
			</table>
			<div id="pagerrtw" class="pull-right"></div>
		</div>
		</div>
		<!--隐藏框 已完成的批次结束  -->


		</section>

		<!-- 任务详情开始-->
		<div id="addwork"
			style="display: none; position: absolute; z-index: 3;">
			<table>
				<tr>
					<td><button type="button"
							class="btn btn-default btn-danger btn-xs btn-3d attendance">一键删除</button>&nbsp&nbsp</td>
				</tr>
			</table>
			<div class="panel panel-default" style="margin: 0 auto;">	
			<div class="panel-body" >
				<table >
					<thead>
						<tr>
							<th class="center"><label> <input type="checkbox"
									class="ace checks" /> <span class="lbl"></span>
							</label></th>
							<th class="text-center hidden-sm">批次号</th>
							<th class="text-center hidden-sm" >产品名</th>
							<th class="text-center">时间</th>
							<th class="text-center">工序</th>
							<th class="text-center hidden-sm">预计时间</th>
							<th class="text-center hidden-sm">任务价值</th>
							<th class="text-center hidden-sm">b工资净值</th>
							<th class="text-center hidden-sm">数量</th>
							<th class="text-center ">工序加价</th>
							<th class="text-center">加绩工资</th>
							<th class="text-center">完成人</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody id="tablecontentto" style="width: 100%">

					</tbody>
				</table>
				<div id="pagerr" class="pull-right"></div>
			</div>
</div>
		</div>


<!--隐藏框 人员信息开始  -->
	<div id="userInformation" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeForm">
				<div class="form-group">
					<div   id="modal-body" style="text-align:center">
						
					</div>
				</div>
			</form>
		</div>
	</div>
	<!--隐藏框 人员信息结束  -->
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
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
		  	function p(s) {
				return s < 10 ? '0' + s: s;
				}
		  	var myDate = new Date(new Date().getTime() - 86400000);
			//获取当前年
			var year=myDate.getFullYear();
			//获取当前月
			var month=myDate.getMonth()+1;
			//获取当前日
			var date=myDate.getDate(); 
			var day = new Date(year,month,0);  
			var firstdate = year + '-' + '0'+month + '-01'+' '+'00:00:00';
			var lastdate = year + '-' + '0'+month + '-' + day.getDate() +' '+'23:59:59';
			/* $('#startTime').val(firstdate);
			$('#endTime').val(lastdate); */
			 var data={
						page:1,
				  		size:12,	
				  		type:2,
				  		status:$('.selectchoice').val(),
				  		orderTimeBegin:firstdate,
				  		orderTimeEnd:lastdate,
				} 
			 layui.use(['laydate'],function(){
					var laydate = layui.laydate;
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
						value : firstdate+' ~ '+lastdate,
					});
			 })
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
				      url:"${ctx}/bacth/allBacth",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			  //修改合计
		      			$("#total").text(result.data.statData.number)
		      			$("#tota2").text(result.data.statData.sumTaskPrice)
		      			$("#tota3").text(result.data.statData.time)
		      			//修改此处
		      			
		      			
		      			  $("#total").text(result.data.statData.stateCount)
		      			  $("#totaltw").text(result.data.statData.statAmount)
		      			 $(result.data.rows).each(function(i,o){
		      				var strname="";
		      				 if(o.status==1){
		      					strname="完成";
		      				 }else{
		      					strname="未完成";
		      				 }
		      				 html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center  bacthNumber hidden-sm">'+o.bacthNumber+'</td>'
		      				+'<td class="text-center edit allotTime">'+o.allotTime+'</td>'
		      				+'<td class="text-center  names" data-id='+o.id+'>'+o.product.name+'</td>'
		      				+'<td class="text-center edit number">'+o.number+'</td>'
		      				+'<td class="text-center  bacthDepartmentPrice hidden-sm">'+parseFloat((o.bacthDepartmentPrice).toFixed(3))+'</td>'
		      				+'<td class="text-center  bacthHairPrice hidden-sm">'+o.bacthHairPrice+'</td>'
		      				+'<td class="text-center  sumTaskPrice hidden-sm">'+ parseFloat((o.sumTaskPrice*1).toFixed(3))+'</td>'
		      				+'<td class="text-center  regionalPrice hidden-sm">'+parseFloat((o.regionalPrice*1).toFixed(3))+'</td>'
		      				+'<td class="text-center ">'+parseFloat((o.time*1).toFixed(3))+'</td>'
		      				+'<td class="text-center edit remarks">'+o.remarks+'</td>'
		      				+'<td class="text-center ">'+strname+'</td>'
							+'<td class="text-center"><button class="btn btn-sm btn-primary btn-trans addDict" data-id='+o.id+' data-proid='+o.product.id+' data-bacthnumber='+o.bacthNumber+' data-proname='+o.product.name+'>分配</button>  <button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button> </td></tr>' 
							
		      			}); 
		      			self.setCount(result.data.pageNum)
				        //显示分页
					   	  laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		  var orderTime=$("#startTime").val().split('~');
						        	var _data = {
						        			page:obj.curr,
									  		size:12,
									  		type:2,
								  			name:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:orderTime[0],
								  			orderTimeEnd:orderTime[1], 
								  			status:$('.selectchoice').val(),
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   	 
					   	self.loadEvents();
					   	self.checkeddd();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			  this.checkeddd=function(){
					
					$(".checkstw").on('click',function(){
						
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
			  this.loadPaginationto = function(data){
				    var index;
				    var html = '';
				    var htmlto="";
				    $.ajax({
					      url:"${ctx}/task/allTask",
					      data:data,
					      type:"GET",
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			 $(result.data.rows).each(function(i,o){
			      				 var a=""
			      				 var s=o.procedureName
			      				if(o.flag==1){
			      					a="(返工)"
			      				}
			      				 if(o.taskActualTime==null){
			      					o.taskActualTime=0
			      				 }
			      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxIdto" value="'+o.id+'"/><span class="lbl"></span></label></td>'
			      				+'<td class="text-center hidden-sm">'+o.bacthNumber+'</td>'
			      				+'<td class="text-center hidden-sm">'+o.productName+'</td>'
			      				+'<td class="text-center edit allotTimetw">'+o.allotTime+'</td>'
			      				+'<td class="text-center ">'+s+a+'</td>'
			      				+'<td class="text-center hidden-sm">'+parseFloat((o.expectTime).toFixed(4))+'</td>'
			      				+'<td class="text-center hidden-sm">'+parseFloat((o.taskPrice).toFixed(4))+'</td>'
			      				+'<td class="text-center hidden-sm">'+parseFloat((o.payB).toFixed(4))+'</td>'
			      				+'<td class="text-center edit number hidden-sm">'+o.number+'</td>'
			      				+'<td class="text-center ">'+o.performance+'</td>'
			      				+'<td class="text-center ">'+parseFloat((o.performancePrice).toFixed(4))+'</td>'
			      				+'<td class="text-center"><button class="btn btn-primary btn-trans btn-sm savemode" data-toggle="modal" data-target="#myModal" data-id="'+o.id+'")">查看人员</button></td>'
								+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans updateremaketw" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans deletetw" data-id='+o.id+'>删除</button></td></tr>'
								
			      			}); 
					        //显示分页
						   	 laypage({
						      cont: 'pagerr', 
						      pages: result.data.totalPages, 
						      curr:  result.data.pageNum || 1, 
						      jump: function(obj, first){ 
						    	  if(!first){ 
						    		 
							        	var _data = {
							        			page:obj.curr,
										  		size:12,
										  		type:2,
										  		bacthId:self.getCache(),
									  	}
							        
							            self.loadPaginationto(_data);
								     }
						      }
						    });  
						   	layer.close(index);
						   
						   	 $("#tablecontentto").html(html); 
						   	 self.loadEventss();
						   	self.checked();
					      },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);
						  }
					  });
				}
			  this.checked=function(){
					
					$(".checks").on('click',function(){
						
	                    if($(this).is(':checked')){ 
				 			$('.checkboxIdto').each(function(){  
	                    //此处如果用attr，会出现第三次失效的情况  
	                     		$(this).prop("checked",true);
				 			})
	                    }else{
	                    	$('.checkboxIdto').each(function(){ 
	                    		$(this).prop("checked",false);
	                    		
	                    	})
	                    }
	                }); 
					
				}
			  this.loadEventss = function(){
					
					
					$('.rest').on('click',function(){
						var  del=$(this);
						var id = $(this).parent().data('id');
						var rest = $(this).val();
						
					    	  $.ajax({
									url:"${ctx}/task/getTaskActualTime",
									data:{
										ids:id,
										status:rest,
										},
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										//选择1
										
										if(rest==0){
									
											del.parent().find(".rest").eq(1).prop("checked", false);

										}else{
											del.parent().find(".rest").eq(0).prop("checked", false);
											
										}
										layer.msg("操作成功！", {icon: 1});
										layer.close(index);
									
								
										
									},
									error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
					    	  
					 
			
					});
					
					
					//修改方法
					$('.updateremaketw').on('click',function(){
						if($(this).text() == "编辑"){
							$(this).text("保存")
							
							$(this).parent().siblings(".edit").each(function(index) {  // 获取当前行的其他单元格
								//修改编辑单元弹出，时间选择板。代码如下：
								if(index==0){	
									$(this).html('<input type="text" id="editTime" class="input-mini form-control laydate-icon" value="'+$(this).text()+'"/>');
									document.getElementById('editTime').onclick=function(){
										laydate({
										    elem: '#editTime',
										    istime: true, format: "YYYY-MM-DD hh:mm:ss"
										  });
									}
								}else
					       			$(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
								//原代码：
								//$(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
					        });
						}else{
								$(this).text("编辑")
							$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

						            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

						       
						                $(this).html(obj_text.val()); 
										
								});
								
								var postData = {
										id:$(this).data('id'),
										number:$(this).parent().parent('tr').find(".number").text(),
										allotTime:$(this).parent().parent('tr').find(".allotTimetw").text(),
								}
								var index;
								$.ajax({
									url:"${ctx}/task/upTask",
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
										var _data = {
							        			page:1,
										  		size:12,
										  		type:2,
										  		bacthId:self.getCache(),
									  	}
							        
							            self.loadPaginationto(_data);
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
					
					
					//删除
							$('.deletetw').on('click',function(){
								var postData = {
										ids:$(this).data('id'),
								}
								
								var index;
								 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
								$.ajax({
									url:"${ctx}/task/delete",
									data:postData,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
										layer.msg("删除成功！", {icon: 1});
										var _data={
												page:1,
										  		size:12,
												bacthId:self.getCache(),
												type:2,
										}
										self.loadPaginationto(_data)
										layer.close(index);
										}else{
											layer.msg("删除失败！", {icon: 1});
											layer.close(index);
										}
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
								 })
					})
					
					//人员详细显示方法
					$('.savemode').on('click',function(){
						var id=$(this).data('id')
						var arr=new Array();
						var html="";
						var dicDiv=$('#userInformation');
						 var postData={
									id:id,
							}
						  $.ajax({
								url:"${ctx}/task/taskUser",
								data:postData,
								type:"GET",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									$(result.data).each(function(i,o){
									html+=o.userName+"&nbsp;&nbsp;&nbsp;&nbsp;"
									})
									$('#modal-body').html(html);
									layer.close(index);
									
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
						_index = layer.open({
							  type: 1,
							  skin: 'layui-layer-rim', //加上边框
							  area: ['60%', '300px'], 
							  btnAlign: 'c',//宽高
							  maxmin: true,
							  offset:'100px',
							  title:"人员信息",
							  content: dicDiv,
							  btn: ['关闭'],
							  end:function(){
								  $('#addDictDivType').hide();
								  $('.addDictDivTypeForm')[0].reset(); 
								
							  }
						});
						
					})
					
					
				} 
			  $(document).on('click', '#tablecontent  tr', function(event) {
				  if($(this).find('.checkboxId').is(':checked')==false){
					$(this).find('.checkboxId').prop("checked",true)
					 $(this).css("color","red");
				  }else{
					  $(this).find('.checkboxId').prop("checked",false)
					  $(this).css("color","inherit");
				  }
				})
			this.loadEvents = function(){
				$('.names').on('click',function(){
					var that=$(this);
					var a=$(this).data('id');
					self.setCache(a);
					var data={
							bacthId:$(this).data('id'),
							page:1,
					  		size:12,	
					  		type:2,
					} 
					self.loadPaginationto(data);
					 var ids=that.data("id");
						$(".batch").each(function(i,o){
							var a=$(o).text();
							if(a==ids){
								$(o).parent().addClass("danger");
								$(o).parent().siblings().removeClass("danger");
							}
						})
				var dicDiv=$('#addwork');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['95%', '80%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:this.innerHTML,
						  content: dicDiv,
						  
						  yes:function(index, layero){
							 
							},
						  end:function(){
							  $('#addwork').hide();
							  data={
									page:1,
								  	size:12,	
								  	type:2,
								  	name:$('#name').val(),
						  			number:$('#number').val(),
							  }
							
						  }
					});
				})
				
				//修改方法
				$('.updateremake').on('click',function(){
					if($(this).text() == "编辑"){
						$(this).text("保存")
						
						$(this).parent().siblings(".edit").each(function(index) {  // 获取当前行的其他单元格

							//修改编辑单元弹出，时间选择板。代码如下：
							if(index==0){	
								$(this).html('<input type="text" id="editTime" class="input-mini form-control laydate-icon" value="'+$(this).text()+'"/>');
								document.getElementById('editTime').onclick=function(){
									laydate({
									    elem: '#editTime',
									    istime: true, format: "YYYY-MM-DD hh:mm:ss"
									  });
								}
							}else
				       			$(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
							//原代码：
							//$(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
				        });
					}else{
							$(this).text("编辑")
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							
							var postData = {
									id:$(this).data('id'),
									number:$(this).parent().parent('tr').find(".number").text(),
									remarks:$(this).parent().parent('tr').find(".remarks").text(),
									allotTime:$(this).parent().parent('tr').find(".allotTime").text(),
							}
							
							var index;
							$.ajax({
								url:"${ctx}/bacth/addBacth",
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
									var data={
											page:self.getCount(),
									  		size:12,	
									  		type:2,
									  		flag:0,
									  		status:$('.selectchoice').val(),
									} 
								   self.loadPagination(data);
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
				
				//分配1
				$('.addDict').on('click',function(){
					var that=$(this)
					var productId=$(this).data('proid')
					var productName=$(this).data('proname')
					var bacthId=$(this).data('id')
					var bacthNumber=$(this).data('bacthnumber')
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    
				    var htmlth = '';
				    var htmlfr = '';
				 	
				    //遍历工序类型
				    var getdata={type:"productFristPack",}
	      			$.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdata,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfr +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			$('.working').html("<select class='form-control selectchang'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlfr+"</select>")
							//改变事件
			      			$(".selectchang").change(function(){
			      				var htmlfv="";
			      				var	id=$(this).val()
			      				if(id==109 || id==""){
			      					$('#dis').css("display","block")
			      				}else{
			      					$('#dis').css("display","none")
			      				}
								   var data={
										   productId:productId,
										   type:2,
										   bacthId:bacthId,
										   procedureTypeId:id,
								   }
			      				//查询各个工序的名称
								   $.ajax({
										url:"${ctx}/production/typeToProcedure",
										data:data,
										type:"GET",
										beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										
										success:function(result){
											$(result.data).each(function(i,o){
												htmlfv +='<div class="input-group"><input type="checkbox" class="checkWork" value="'+o.id+'" data-residualnumber="'+o.residualNumber+'">'+o.name+' 剩余:'+o.residualNumber+'</input></div>'
											})
											var s="<div class='input-group'><input type='checkbox' class='checkWorkAll'>全选</input></div>"
											$('.checkworking').html(s+htmlfv);
											$(".checkWorkAll").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.checkWork').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.checkWork').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
											layer.close(index);
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									});
							 })
					      }
					  });
					var data={
							type:2
					}
					//遍历人名组别
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlth +='<div class="input-group"><input type="checkbox" class="checkall" value="'+j.id+'" ><a href="javascript:void(0)" class="showA" data-id='+j.id+'><span style="font-size:18px;color:#5480f0">'+j.name+'</span></a></input></div>'
			      			  });  
			      			 $('.complete').html(htmlth)
			      			
							//改变事件
			      			 $('.showA').on('click',function(){
			      				$('.checkall').each(function(){ 
		                    		$(this).prop("checked",false);
		                    		
		                    	})
			      				var checked=$(this).is(':checked')
			      				var htmltwo = "";
			      				var	id=$(this).data('id')
								   var data={
										  id:id,
										  type:2,
										  temporarilyDate:$('#Time').val(),
								   }
			      				$.ajax({
									url:"${ctx}/production/allGroup",
									data:data,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										$(result.data).each(function(j,k){
										
										$(k.users).each(function(i,o){
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBox"   value="'+o.id+'" data-groupid="'+k.id+'"><label style="width:70px;text-align:center;color:gray;">'+o.userName+'</label><input class="hidden-sm" style="width:100px;" class="time2" data-id="'+o.adjustTimeId+'" data-temporarily="'+o.temporarily+'" value="'+(o.adjustTime!=null ? o.adjustTime : "")+'" /></div>'
										})
										})
										 var s="<div class='input-group'><input type='checkbox' class='checkedAll'><label style='width:70px;text-align:center;color:gray;'>全选</label></input></div>" 
										$('.select').html(s+htmltwo)
										$(".time2").blur(function(){
											var a=$(this).data('temporarily')
											var id=$(this).data('id')
											if(a==1){
												var postData={
														id:id,
														workTime:$(this).val()
													}
												$.ajax({
													url:"${ctx}/production/updateTemporarily",
													data:postData,
										            traditional: true,
													type:"post",
													beforeSend:function(){
														index = layer.load(1, {
															  shade: [0.1,'#fff'] //0.1透明度的白色背景
															});
													},
													success:function(result){
														if(0==result.code){
															layer.msg(result.message, {icon: 1});
														}else{
															layer.msg(result.message, {icon: 2});
														}
														layer.close(index);
													},error:function(){
														layer.msg(result.message, {icon: 2});
														layer.close(index);
													}
												});
											}else{
												var postData={
														adjustId:id,
														adjustTime:$(this).val()
													}
														$.ajax({
															url:"${ctx}/production/updateAdjustTime",
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
																	layer.msg(result.message, {icon: 1});
																}else{
																	layer.msg(result.message, {icon: 2});
																}
																layer.close(index);
															},error:function(){
																layer.msg("操作失败！", {icon: 2});
																layer.close(index);
															}
														});
											}
										}) 
										 $(".checkedAll").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.stuCheckBox').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.stuCheckBox').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                }); 
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
							 }) 
					      }
					  });
				    
					//遍历杂工加绩比值
					var html=""
					$.ajax({
						url:"${ctx}/task/pickTaskPerformance",
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+='<option value="'+o.number+'" data-name="'+o.name+'">'+o.name+'</option>'
							})
							$('.workingtw').html("<select class='form-control selectchangtw'><option value='0'></option>"+html+"</select>");
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
				     
					var postData
					var dicDiv=$('#addDictDivType');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['100%', '500px'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:productName,
						  offset:(parent.document.documentElement.scrollTop+50)+'px',
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  success:function(){
							  //cookie设置输入框的时间值
							  var cookieData = $.cookie('batchTime') || '';
							  if(cookieData){
								  $('#remember').prop("checked",true);
							  }else
								  $('#remember').prop("checked",false);
							  $('#remember').unbind().on('click',function(){
								  if(!$('#remember').prop("checked"))
									  $.cookie('batchTime','');
								  else{
									  $.cookie('batchTime',$('#Time').val());
								  }
							  })
							  $('#Time').val(cookieData);
							  $('#Time').on('click',function(){
								  laydate({
									  elem: '#Time', 
									  istime: true, 
									  format: 'YYYY-MM-DD hh:mm:ss',
									  choose: function(value){
										 var check = $('#remember').prop("checked");
										 if(check){
											 $.cookie('batchTime',value);
										 }
									  }  
								  });
							  })
						  },
						  yes:function(index, layero){
							  var values=new Array()
							  var numberr=new Array()
								$(".checkWork:checked").each(function() {   
									values.push($(this).val());
									numberr.push($(this).data('residualnumber'));
								}); 
							  var check=new Array()
							  $(".checkall:checked").each(function() {   
								  check.push($(this).val());  
								});
							 
							  var arr=new Array()
							  var groupId;
							  if(check.length<=0){
								$(".stuCheckBox:checked").each(function() {   
								    arr.push($(this).val());
								    groupId=$(this).data('groupid');
								}); 
							  }else{
								  $(".stuCheckBox:checked").each(function() {   
									    arr.push($(this).val());   
									});
								  if(arr.length>0){
									  return layer.msg("选组后不能单独选择员工", {icon: 2});
								  }
								  if(check.length==1){
									  for (var i = 0; i < check.length; i++) {
										 groupId=check[0]
									  }
								  }
								  for (var i = 0; i < check.length; i++) {
									   var data={
										  id:check[i],
										  type:2,
								  		 }
									$.ajax({
										url:"${ctx}/production/allGroup",
										data:data,
							            traditional: true,
							            async:false,
										type:"GET",
										beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										success:function(result){
											if(0==result.code){
												$(result.data).each(function(i,o){
													$(o.users).each(function(i,o){
														  arr.push(o.id);   
													})
													})
											}else{
												layer.msg(result.message, {icon: 2});
											}
											layer.close(index);
										},error:function(){
											layer.msg(result.message, {icon: 2});
											layer.close(index);
										}
									});
								} 
							  }
							  if(values.length<=0){
									return layer.msg("至少选择一个工序！", {icon: 2});
								}
								 if(arr.length<=0){
									return layer.msg("至少选择一个员工！", {icon: 2});
								} 
								 number=$(".sumnumber").val();
								 if(number==""){
									 number=0;
								 }
								 for (var i = 0; i < numberr.length; i++) {
									if(numberr[i]-number<0){
										return layer.msg("有工序剩余数量不足！", {icon: 2});
									}
								}  
								expectTime=$(".sumtime").val();
								var performanceNumber=$(".selectchangtw").val();
								
								var performance=$(".selectchangtw option:selected").text();
								if(performance=="请选择请选择"){
									performance="";
								}
								var postData = {
										type:2,
										bacthId:that.data("id"),
										procedureIds:values,
										userIds:arr,
										number:number,
										performance:performance,
										performanceNumber:performanceNumber,
										productName:productName,
										expectTime:expectTime,
										bacthNumber:bacthNumber,
										allotTime:$('#Time').val(),
										productId:productId,
										groupId:groupId
								}
								
							    $.ajax({
									url:"${ctx}/task/addTask",
									data:postData,
						            traditional: true,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
										  $('.addDictDivTypeForm')[0].reset(); 
										$('.checkworking').text("");
										  $('.select').text("");
											layer.msg(result.message, {icon: 1});
											layer.close(_index);
										}else{
											layer.msg(result.message, {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});  
							},
						   end:function(){
							  $('.addDictDivTypeForm')[0].reset(); 
							  $("#addDictDivType").hide();
							  $('.checkworking').text(""); 
							  $('#showB').text(""); 
							  
						  } 
					});
					
					
				})
				
				
				
				
				
				
				
				//分配2
				$('.addDicttw').on('click',function(){
					var that=$(this)
					var productId=$(this).data('proid')
					var productName=$(this).data('proname')
					var bacthId=$(this).data('id')
					var bacthNumber=$(this).data('bacthnumber')
					var number=$(this).data('number')
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    
				    var htmlth = '';
				    var htmlfr = '';
				 	
				    //遍历工序类型
				    var getdata={type:"productFristPack",}
	      			$.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdata,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfr +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			$('.workingtw').html("<select class='form-control selectchangtt'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlfr+"</select>")
							//改变事件
			      			$(".selectchangtt").change(function(){
			      				var htmlfv="";
			      				var	id=$(this).val()
			      				if(id==109 || id==""){
			      					$('#diss').css("display","block")
			      				}else{
			      					$('#diss').css("display","none")
			      				}
								   var data={
										   productId:productId,
										   type:2,
										   bacthId:bacthId,
										   procedureTypeId:id,
								   }
			      				//查询各个工序的名称
								   $.ajax({
										url:"${ctx}/production/typeToProcedure",
										data:data,
										type:"GET",
										beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										
										success:function(result){
											$(result.data).each(function(i,o){
												htmlfv +='<div class="input-group"><input type="checkbox" class="checkWorks" value="'+o.id+'" data-residualnumber="'+o.residualNumber+'">'+o.name+' 剩余:'+o.residualNumber+'</input></div>'
											})
											var s="<div class='input-group'><input type='checkbox' class='checkWorkAlls'>全选</input></div>"
											$('.checkworkingtw').html(s+htmlfv);
											$(".checkWorkAlls").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.checkWorks').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.checkWorks').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
											layer.close(index);
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									});
							 })
					      }
					  });
					var data={
							type:2
					}
					//遍历人名组别
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			 $('.completetw').html("<select class='form-control selectcompletet'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlth+"</select>") 
							//改变事件
			      			 $(".selectcompletet").change(function(){
			      				var htmltwo = "";
			      				var	id=$(this).val()
								   var data={
										  id:id
								   }
			      				$.ajax({
									url:"${ctx}/production/allGroup",
									data:data,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										$(result.data).each(function(i,o){
										
										$(o.users).each(function(i,o){
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBoxt" value="'+o.id+'" data-username="'+o.userName+'">'+o.userName+'</input></div>'
										})
										})
										var s="<div class='input-group'><input type='checkbox' class='checkalls'>全选</input></div>"
										$('.selecttw').html(s+htmltwo)
										$(".checkalls").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.stuCheckBoxt').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.stuCheckBoxt').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
							 }) 
					      }
					  });
				    
					//遍历杂工加绩比值
					var html=""
					$.ajax({
						url:"${ctx}/task/taskPerformance",
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+='<option value="'+o.number+'" data-name="'+o.name+'">'+o.name+'</option>'
							})
							$('.workingth').html("<select class='form-control selectchangtwt'><option value='0'>请选择</option>"+html+"</select>");
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					var time;
					var timeover;
					var ss;
					var times=new Array();
					var values=new Array();
					var roleidArray = new Array();
					var str1;
					var i = -1;
					$('#save').on('click',function(){
					var trHtml="";
						i++;
						time=$("#Timetstr").val();
						timeover=$("#Timetend").val();
						var dt1 = new Date(Date.parse(time));
						var dt2 = new Date(Date.parse(timeover));
						ss=(dt2-dt1)/60000
						var arr=new Array()
						var username=new Array()
						if(dt1=="Invalid Date"){
							return layer.msg("开始时间不能为空！", {icon: 2});
						}
						if(dt2=="Invalid Date"){
							return layer.msg("结束时间不能为空！", {icon: 2});
						}
						if(dt2-dt1<=0){
							return layer.msg("结束时间不能小于开始时间！", {icon: 2});
						}
						$(".stuCheckBoxt:checked").each(function() {   
						    arr.push($(this).val()); 
						    username.push($(this).data('username'))
						}); 
						if(arr.length==0){
							 return layer.msg("领取人不能为空", {icon: 2});
						}
						
						  times.push(ss);
						  roleidArray.push(arr)
						  str1=roleidArray.join(".")
						 
						 trHtml ='<div class="form-group" data-id="'+i+'">'
	                           +'<label class="col-sm-3 col-md-2 control-label">开始时间:</label>'
                        	   +'<div class="col-sm-2 col-md-2">'
                               +'<input  class="form-control laydate-icon" value="'+time+'" onClick="laydate({elem: "#Timetstr", istime: true, format: "YYYY-MM-DD hh:mm:ss"})">'
                               +'</div>'
                               +'<div>'
                               +'<label class="col-sm-1 col-md-1 control-label" >结束时间:</label>'
                               +'<div class="col-sm-2 col-md-2">'
                               +'<input value="'+timeover+'"  class="form-control laydate-icon" onClick="laydate({elem: "#Timetend", istime: true, format: "YYYY-MM-DD hh:mm:ss"})">'
                               +'</div>'
                               +'</div>'
                               +'<label class="col-sm-1 control-label">完成人:</label>'
                               +'<div class="col-sm-2 completetw">'
                               +'<input type="text" value="'+username+'" class="form-control">'
                               +'</div>'
                               +'<div class="col-sm-1 "></div>'
                               +'<div class="col-sm-2 col-md-1"><input type="button" class="btn btn-sm btn-success del" id="'+i+'" value="删除"></input></div></div>'
                              
                               $("#tabs").append(trHtml); 
                            	 
	                       $('.del').on('click',function(){
                            	   var va=$(this).parent().parent().data('id');
                            	   times.splice(va,1,"delete");
                            		roleidArray.splice(va,1,["delete"]);
                            	   str1=roleidArray.join(".");
                            	   $(this).parent().parent().hide();
                            	   return layer.msg("删除成功", {icon: 1});
                               })
						return layer.msg("添加成功", {icon: 1});
					})
					var postData
					var dicDiv=$('#addDictDivTypetw');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:productName,
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var values=new Array()
							  var numberr=new Array()
							
								$(".checkWorks:checked").each(function() {   
									values.push($(this).val());
									numberr.push($(this).data('residualnumber'));
								}); 
							  var arr=new Array()
							  
								$(".stuCheckBoxt:checked").each(function() {   
								    arr.push($(this).val());   
								}); 
							  var username=new Array()
							  $(".stuCheckBoxt:checked").each(function() {   
								  username.push($(this).data('username'));   
								});
							  if(values.length<=0){
									return layer.msg("至少选择一个工序！", {icon: 2});
								}
								if(arr.length<=0){
									return layer.msg("至少选择一个员工！", {icon: 2});
								}
								 for (var i = 0; i < numberr.length; i++) {
									if(numberr[i]-number<0){
										return layer.msg("有工序剩余数量不足！", {icon: 2});
									}
								} 
								expectTime=$(".sumtimetw").val();
								var performanceNumber=$(".selectchangtwt").val();
								
								var performance=$(".selectchangtwt option:selected").text();
								
								if(performance=="请选择"){
									performance="";
								}
								var postData = {
										type:2,
										times:times,
										users:str1,
										bacthId:that.data("id"),
										procedureIds:values,
										number:number,
										performance:performance,
										performanceNumber:performanceNumber,
										productName:productName,
										expectTime:expectTime,
										bacthNumber:bacthNumber,
										allotTime:$('#Timet').val(),
										productId:productId,
								}
							    $.ajax({
									url:"${ctx}/task/addTaskTwo",
									data:postData,
						            traditional: true,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
										  $('.addDictDivTypeFormtw')[0].reset(); 
										$('.checkworkingtw').text("");
										  $('.selecttw').text("");
											layer.msg(result.message, {icon: 1});
											
											
										}else{
											layer.msg(result.message, {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});  
							},
						   end:function(){
							  $('.addDictDivTypeFormtw')[0].reset(); 
							  $("#addDictDivTypetw").hide();
							  $('.checkworkingtw').text(""); 
							  var data={
										page:self.getCount(),
										size:12,
							  			type:2,
							  			name:$('#name').val(),
							  			bacthNumber:$('#number').val(),
							  			 orderTimeBegin:$("#startTime").val(),
							  			orderTimeEnd:$("#endTime").val(), 
							  			status:$('.selectchoice').val(),
								} 
							   self.loadPagination(data);
							 
							
						  } 
					});
					
					
				})
				
				
				
				
				
				
				
				
				
			}
			this.loadworking=function(data){
				
				var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/bacth/allBacth",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			  
		      			 $(result.data.rows).each(function(i,o){
		      				 var a=o.number
		      				 var c="";
		      				 if(o.type==1){
		      					 c="一楼质检"
		      				 }
		      				 if(o.type==3){
		      					 c="二楼针工"
		      				 }
		      				 html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxIdtr" data-number='+o.number+' value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center  bacthNumber">'+o.bacthNumber+'</td>'
		      				+'<td class="text-center  allotTime">'+o.statusTime+'</td>'
		      				+'<td class="text-center  name">'+o.product.name+'</td>'
		      				+'<td class="text-center edit number">'+o.number+'</td>'
		      				+'<td class="text-center number">'+c+'</td>'
		      				+'<td class="text-center edit numberfr"><input class="work"  value="'+a+'"></input></td><tr>'
							
		      			}); 
		      			 laypage({
						      cont: 'pagerrtw', 
						      pages: result.data.totalPages, 
						      curr:  result.data.pageNum || 1, 
						      jump: function(obj, first){ 
						    	  if(!first){ 
						    		 
							        	var _data = {
							        			page:obj.curr,
							        			size:10,
									  			name:$('#nametw').val(),
									  			bacthNumber:$('#numbertw').val(),
									  			 orderTimeBegin:$("#startTimetw").val(),
									  			orderTimeEnd:$("#endTimetw").val(),
									  			statusTime:$("#startTimetw").val(),
									  			receive:$('.selectchoicetw').val(),
									  			status:1,
										  		flag:0,
									  	}
							        self.loadworking(_data);
								     }
						      }
						    }); 
					   	layer.close(index);
					   	 document.getElementById("tableworking").innerHTML=html;
					   	self.loadEventsth();
					   	self.checkedddd();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			this.checkedddd=function(){
				
				$(".checksth").on('click',function(){
					
                    if($(this).is(':checked')){ 
			 			$('.checkboxIdtr').each(function(){  
                    //此处如果用attr，会出现第三次失效的情况  
                     		$(this).prop("checked",true);
			 			})
                    }else{
                    	$('.checkboxIdtr').each(function(){ 
                    		$(this).prop("checked",false);
                    		
                    	})
                    }
                }); 
				
			}
			this.loadEventsth=function(){
				 $('.receive').on('click',function(){
					 var numbers=new Array()
					 var arr=new Array()//员工id
					 var  that=$(".table-hover");
						that.parent().parent().parent().parent().find(".checkboxIdtr:checked").each(function() {  
							arr.push($(this).val()); 
							numbers.push($(this).parent().parent().parent().find(".work").val());
						});
						
					 var postData = {
							 ids:arr,
							 numbers:numbers,
							 receive:1,
						}
						var index;
						 index = layer.confirm('确定接收吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/bacth/receiveBacth",
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
								layer.msg(result.message, {icon: 1});
								self.loadworking();
								layer.close(index);
								}else{
									layer.msg(result.message, {icon: 1});
									layer.close(index);
								}
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 })
				}) 
			}
			this.events = function(){
				
				/* 一键删除 */
				$('.attendance2').on('click',function(){
					  var  that=$(this);
					  var arr=new Array()//员工id
						$(this).parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var postData={
								ids:arr,
						}
						var index;
						 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/bacth/deleteBacth",
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
								var orderTime=$("#startTime").val().split('~');
								var data = {
					        			page:1,
								  		size:12,
								  		type:2,
							  			name:$('#name').val(),
							  			bacthNumber:$('#number').val(),
							  			orderTimeBegin:orderTime[0],
							  			orderTimeEnd:orderTime[1], 
							  			status:$('.selectchoice').val(),
							  	}
								self.loadPagination(data)
								layer.close(index);
								}else{
									layer.msg("删除失败！", {icon: 1});
									layer.close(index);
								}
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 })
				  })
				
				/* 一键删除 */
				$('.attendance').on('click',function(){
					  var  that=$(this);
					  var arr=new Array()//员工id
						$(this).parent().parent().parent().parent().parent().find(".checkboxIdto:checked").each(function() {  
							arr.push($(this).val());   
						});
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var data={
								type:5,
								ids:arr,
						}
						var _data={
								page:1,
						  		size:12,
								bacthId:self.getCache(),
								type:2,
						}
						var index;
						 index = layer.confirm('确定一键删除吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/task/delete",
							data:data,
				            traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
									layer.msg(result.message, {icon: 1});
									self.loadPaginationto(_data);
								}else{
									layer.msg(result.message, {icon: 2});
								}
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 });
				  })
				/* 一键完成  */
				$('.start').on('click',function(){
					  var  that=$(".table-hover");
					  var arr=new Array()//员工id
					  that.parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
					  var _datae={
								status:0,
								type:2,
								ids:arr,
								flag:0,
						}
						var index;
						 index = layer.confirm('<input type="text" id="some" class="tele form-control " placeholder="请输入时间" onClick=laydate({elem:"#some",istime:true,format:"YYYY-MM-DD"})>', {btn: ['确定', '取消'],offset:'(parent.document.documentElement.scrollTop+50)'+'px',},function(){
							 var a="";
								if($('#some').val()==""){
									a="";
								}else{
									a=$('#some').val()+" "+"00:00:00"
								}
							 var data={
								status:1,
								type:2,
								ids:arr,
								time:a,
						}
						$.ajax({
							url:"${ctx}/bacth/statusBacth",
							data:data,
				            traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
									layer.msg(result.message, {icon: 1});
									var orderTime=$("#startTime").val().split('~');
									var _datae = {
								  			page:1,
								  			size:12,
								  			type:2,
								  			name:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:orderTime[0],
								  			orderTimeEnd:orderTime[1], 
								  			status:$('.selectchoice').val(),
								  	}
									self.loadPagination(_datae);
								}else{
									layer.msg(result.message, {icon: 2});
								}
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 });
				  })
				//查询
				$('.searchtask').on('click',function(){
					var orderTime=$("#startTime").val().split('~');
					var data = {
				  			page:1,
				  			size:12,
				  			type:2,
				  			name:$('#name').val(),
				  			bacthNumber:$('#number').val(),
				  			 orderTimeBegin:orderTime[0],
				  			orderTimeEnd:orderTime[1], 
				  			status:$('.selectchoice').val(),
				  	}
		            self.loadPagination(data);
				});
				
				//查询
				$('.searchtasktw').on('click',function(){
					var data = {
				  			page:1,
				  			size:10,
				  			name:$('#nametw').val(),
				  			bacthNumber:$('#numbertw').val(),
				  			 orderTimeBegin:$("#startTimetw").val(),
				  			orderTimeEnd:$("#endTimetw").val(),
				  			statusTime:$("#startTimetw").val(),
				  			receive:$('.selectchoicetw').val(),
				  			status:1,
					  		flag:0,
				  	}
		            self.loadworking(data);
				});
				//触发工序弹框 加载内容方法
				$('#addprocedure').on('click',function(){
					var _index
					var productId=$(this).data('id')
					var name=$(this).data('name')
					var dicDiv=$('#addworking');
					  //打开隐藏框
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['85%', '90%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:name,
						  content: dicDiv,
						  
						  yes:function(index, layero){
							 
							},
						  end:function(){
							  $('#addworking').hide();
							  data={
									page:1,
								  	size:12,	
								  	type:2,
								  	name:$('#name').val(),
						  			number:$('#number').val(),
						  			status:$('.selectchoice').val(),
							  }
							self.loadPagination(data);
						  }
					});
					var data={
							page:1,
							size:10,
					  		status:1,
					  		receive:0,
					  		flag:0,
					}
					self.loadworking(data); 
					
					
				})
				
				
				
				
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>
</body>
</html>