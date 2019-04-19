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
<title>订单管理</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

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
							<h3 class="panel-title">订单信息</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i> <i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="row" style="height: 30px; margin: 15px 0 10px">
							<div class="col-xs-10 col-sm-10  col-md-10">
								<form class="form-search">
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12">
											<div class="input-group">
												<table>
													<tr>
														<td>乙方:</td>
														<td><input type="text" name="name" id="partyNames"
															class="form-control search-query name" /></td>
														<td>&nbsp&nbsp</td>
														<td>产品名:</td>
														<td><input type="text" name="name" id="productName"
															class="form-control search-query name" /></td>
														<td>&nbsp&nbsp</td>
														<td>批次号:</td>
														<td><input type="text" name="name" id="batchNumber2"
															class="form-control search-query name" /></td>
														<td>合同开始:</td>
														<td><input id="startTime" placeholder="请输入开始时间"
															class="form-control laydate-icon"
															onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
														</td>
														<td>&nbsp&nbsp</td>
														<td>合同结束:</td>
														<td><input id="endTime" placeholder="请输入结束时间"
															class="form-control laydate-icon"
															onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
														</td>
													</tr>
												</table>
												<span class="input-group-btn">
													<button type="button"
														class="btn btn-info btn-square btn-sm navbar-right btn-3d searchtask">
														查找 <i class="icon-search icon-on-right bigger-110"></i>
													</button>
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
										<th class="text-center">乙方电话</th>
										<th class="text-center">乙方其他信息</th>
										<th class="text-center">线上or线下</th>
										<th class="text-center">当月销售编号</th>
										<th class="text-center">合同签订日期</th>
										<th class="text-center">甲方</th>
										<th class="text-center">乙方</th>
										<th class="text-center">当批批次号</th>
										<th class="text-center">当批产品名</th>
										<th class="text-center">当批合同数量</th>
										<th class="text-center">当批合同总价（元）</th>
										<th class="text-center">预付款备注</th>
										<th class="text-center">单只价格</th>
										<th class="text-center">未到岸</th>
										<th class="text-center">到岸数量</th>
										<th class="text-center">预计借款日期</th>
										<th class="text-center">争议数字</th>
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



	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctx }/static/plugins/navgoco/jquery.navgoco.min.js"></script>
	<script src="${ctx }/static/plugins/switchery/switchery.min.js"></script>
	<script src="${ctx }/static/plugins/pace/pace.min.js"></script>
	<script
		src="${ctx }/static/plugins/fullscreen/jquery.fullscreen-min.js"></script>
	<script src="${ctx }/static/js/src/app.js"></script>
	<script src="${ctx }/static/js/laypage/laypage.js"></script>
	<script src="${ctx }/static/plugins/dataTables/js/jquery.dataTables.js"></script>
	<script
		src="${ctx }/static/plugins/dataTables/js/dataTables.bootstrap.js"></script>
	<script src="${ctx }/static/js/vendor/typeahead.js"></script>
	<script src="${ctx }/static/js/vendor/mSlider.min.js"></script>
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
		  	this.getCount = function(){
		  		return _count;
		  	}
		  	this.setCount = function(count){
		  		_count=count;
		  	}
		  	function p(s) {
				return s < 10 ? '0' + s: s;
				}
			var myDate = new Date();
				//获取当前年
				var year=myDate.getFullYear();
				//获取当前月
				var month=myDate.getMonth()+1;
				//获取当前日
				var date=myDate.getDate(); 
				var h=myDate.getHours();       //获取当前小时数(0-23)
				var m=myDate.getMinutes();     //获取当前分钟数(0-59)
				var s=myDate.getSeconds();  
				var myDate2 = new Date();
				//获取当前年
				var year2=myDate2.getFullYear();
				//获取当前月
				var month2=myDate2.getMonth()+1;
				//获取当前日
				var date2=myDate2.getDate();
				var now2=year2+'-'+p(month2)+"-"+p(date2)+' '+'00:00:00';
				var day = new Date(year,month,0);
				var firstdate = year + '-' + p(month) + '-01'+' '+'00:00:00';
				var getday = year + '-' + p(month) + date+' '+'00:00:00';
				var lastdate = year + '-' + p(month) + '-' + day.getDate() +' '+'23:59:59';
				$('#startTime').val(firstdate);
				$('#endTime').val(lastdate);
			 var data={
						page:1,
				  		size:13,
				  		ashoreCheckr:0,
				  		orderTimeBegin:firstdate,
				  		orderTimeEnd:lastdate,	
				} 
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
			}
			 function p(s) {
					return s < 10 ? '0' + s: s;
					}
   				var myDate = new Date();
					//获取当前年
					var year=myDate.getFullYear();
					//获取当前月
					var month=myDate.getMonth()+1;
					//获取当前日
					var date=myDate.getDate(); 
					var h=myDate.getHours();       //获取当前小时数(0-23)
					var m=myDate.getMinutes();     //获取当前分钟数(0-59)
					var s=myDate.getSeconds();  
					var myDate2 = new Date();
					myDate2.setMonth(month + 2);
					//获取当前年
					var year2=myDate2.getFullYear();
					//获取当前月
					var month2=myDate2.getMonth();
					//获取当前日
					var date2=myDate2.getDate(); 
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/fince/getOrder",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				var newDate=/\d{4}-\d{1,2}-\d{1,2}/g.exec(o.contractTime)//去除时分秒的格式
		      				var temper=o.contractTime
		      				var dt = new Date(temper.replace(/-/,"/"))//转换成日期格式
		      				var date2=dt.getDate();//获取天数
							var now2=year2+'-'+p(month2)+"-"+p(date2);
		      				var a="线下";
		      				if(o.online==1){
		      					a="线上"
		      				}
		      				html +='<tr>'
		      				+'<td class="hidden batch" data-id='+o.id+'>'+o.id+'</td>'
		      				+'<td class="text-center  salesNumber">'+o.contact.conPhone+'</td>'
		      				+'<td class="text-center  salesNumber">'+o.contact.conWechat+'</td>'
		      				+'<td class="text-center  salesNumber">'+a+'</td>'
		      				+'<td class="text-center  salesNumber">'+o.salesNumber+'</td>'
		      				+'<td class="text-center edit contractTime">'+newDate+'</td>'
		      				+'<td class="text-center editt firstNames">'+o.firstNames+'</td>'
		      				+'<td class="text-center editt partyNames">'+o.partyNames+'</td>'
		      				+'<td class="text-center edit1 batchNumber">'+o.batchNumber+'</td>'
		      				+'<td class="text-center edit3 productName">'+o.productName+'</td>'
		      				+'<td class="text-center edit4 contractNumber">'+(o.contractNumber!=null ? o.contractNumber : 0)+'</td>'
		      				+'<td class="text-center  name contractPrice" style=" color:#c11f34">'+(o.contractPrice!=null ? o.contractPrice : 0)+'</td>'
		      				+'<td class="text-center edit5 remarksPrice">'+(o.remarksPrice!=null ? o.remarksPrice : "")+'</td>'
		      				+'<td class="text-center  name">'+(o.price!=null ? o.price : "")+'</td>'
		      				+'<td class="text-center  roadNumber">'+(o.roadNumber!=null ? o.roadNumber : o.contractNumber)+'</td>'
		      				+'<td class="text-center  name"><input type="text" class="ashoreNumber text-center" value="'+(o.ashoreNumber!=null ? o.ashoreNumber : "")+'" style="border: none;width:70px; height:30px; background-color: #BFBFBF;"></td>'
		      				+'<td class="text-center  ashoreTime">'+now2+'</td>'
		      				+'<td class="text-center  name"><input type="text" class="disputeNumber text-center" value="'+(o.disputeNumber!=null ? o.disputeNumber : "")+'" style="border: none;width:70px; height:30px; background-color: #BFBFBF;"></td></tr>'
		      			}); 
		      			self.setCount(result.data.pageNum)
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
									  		productName:$('#productName').val(),
								  			firstNames:$('#firstNames').val(),
								  			partyNames:$('#partyNames').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(), 
								  			batchNumber:$("#batchNumber2").val(),
								  			ashoreCheckr:0,
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
				/* $(".ashoreTime").on('click',function(){
					var ttat=$(this)
					laydate({elem:this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})
				}) */
				$(".ashoreNumber").blur(function(){
					var that=$(this)
					var postData = {
							id:$(this).parent().parent().find(".batch").data('id'),
							ashoreNumber:$(this).val(),
							disputeNumber:$(this).parent().parent().find(".disputeNumber").val(),
							ashoreTime:$(this).parent().parent().find(".ashoreTime").text()+' '+'00:00:00',
					}
					var index;
					$.ajax({
						url:"${ctx}/fince/addOrder",
						data:postData,
						type:"POST",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						success:function(result){
							if(0==result.code){
							that.parent().parent().find(".roadNumber").text(result.data.roadNumber)
							layer.msg("当批还有"+result.data.roadNumber+"未到岸", {icon: 1});
							layer.close(index);
							}else{
								layer.msg("失败！", {icon: 1});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
				})				
				
				$(".disputeNumber").blur(function(){
					var that=$(this)
					var postData = {
							id:$(this).parent().parent().find(".batch").data('id'),
							ashoreNumber:$(this).parent().parent().find(".ashoreNumber").val(),
							disputeNumber:$(this).val(),
							ashoreTime:$(this).parent().parent().find(".ashoreTime").text()+' '+'00:00:00',
					}
					var index;
					$.ajax({
						url:"${ctx}/fince/addOrder",
						data:postData,
						type:"POST",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						success:function(result){
							if(0==result.code){
							that.parent().parent().find(".roadNumber").text(result.data.roadNumber)
							layer.msg("当批还有"+result.data.roadNumber+"未到岸", {icon: 1});
							layer.close(index);
							}else{
								layer.msg("失败！", {icon: 1});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
				})	
				
				
			}
			this.events = function(){
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			productName:$('#productName').val(),
				  			firstNames:$('#firstNames').val(),
				  			partyNames:$('#partyNames').val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  			batchNumber:$("#batchNumber2").val(),
				  			ashoreCheckr:0,
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