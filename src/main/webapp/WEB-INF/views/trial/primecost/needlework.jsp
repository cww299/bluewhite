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
<title>针工填写</title>
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
							<h3 class="panel-title">针工填写</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i> <i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="panel-body">
							<div class="tab-wrapper tab-primary">
								<ul class="nav nav-tabs col-md-12">
									<li class="active col-md-2" style="width: 50%"><a
										href="#home1" class="home1" data-toggle="tab">针工页面</a></li>
									<li class="col-md-2" style="width: 50%;"><a
										href="#profile1" class="profile1" data-toggle="tab">针工时间设定</a>
									</li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="home1">
										<!--查询开始  -->
										<div class="row" style="height: 30px; margin: 15px 0 10px">
											<div class="col-xs-12 col-sm-12  col-md-12">
												<form class="form-search">
													<div class="row">
														<div class="col-xs-11 col-sm-11 col-md-11">
															<div class="input-group">
																<table>
																	<tr>
																		<td>产品名:</td>
																		<td><input type="text" name="name"
																			id="productName" placeholder="请输入产品名称"
																			class="form-control search-query name"
																			data-provide="typeahead" autocomplete="off"/ ></td>
																		<td>&nbsp&nbsp</td>
																		<td>默认数量:</td>
																		<td><input type="text" name="number" id="number"
																			disabled="disabled" placeholder="请输入默认数量"
																			class="form-control search-query number" /></td>
																		<td>&nbsp&nbsp</td>
																		<td>默认耗损:</td>
																		<td><input type="text" name="name" id="loss"
																			placeholder="请输入产品名称"
																			class="form-control search-query name" /></td>
																		<td>&nbsp&nbsp</td>
																		<td>裁剪价格:</td>
																		<td><input type="text" name="name" id="ntwo"
																			disabled="disabled"
																			class="form-control search-query name" /></td>
																	</tr>
																</table>
																<span class="input-group-btn">
																	<button type="button"
																		class="btn btn-info btn-square btn-sm btn-3d searchtask">
																		查&nbsp找</button>
																</span>
																<td>&nbsp&nbsp&nbsp&nbsp</td> <span
																	class="input-group-btn">
																	<button type="button" id="addCutting3"
																		class="btn btn-success  btn-sm btn-3d export">
																		默认工序</button>
																</span>
																<td>&nbsp&nbsp&nbsp&nbsp</td> <span
																	class="input-group-btn">
																	<button type="button" id="addCutting"
																		class="btn btn-success  btn-sm btn-3d export">
																		新增</button>
																</span>
																<td>&nbsp&nbsp&nbsp&nbsp</td> <span
																	class="input-group-btn">
																	<button type="button" id="addCutting2"
																		class="btn btn-success  btn-sm btn-3d export">
																		新增手动工序</button>
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
													<th class="text-center"><label> <input
															type="checkbox" class="ace checks" /> <span class="lbl"></span>
													</label></th>
													<th class="text-center">设定完毕的针工工序</th>
													<th class="text-center">请选择在该工序下的分类</th>
													<th class="text-center">自动跳出设定秒数</th>
													<th class="text-center">针工步骤</th>
													<th class="text-center">该工序有可能用到的物料</th>
													<th class="text-center">针工价格</th>
													<th class="text-center">入成本批量价格</th>
													<th class="text-center">上道压价（整个皮壳）</th>
												</tr>
											</thead>
											<tbody id="tablecontent">

											</tbody>
										</table>
									</div>
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
																		<td>裁剪价格:</td>
																		<td><input type="text" name="name" id="ntwo1"
																			disabled="disabled"
																			class="form-control search-query name" /></td>
																	</tr>
																</table>
																<span class="input-group-btn">
																	<button type="button"
																		class="btn btn-info btn-square btn-sm btn-3d navbar-right searchtask2">
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

													<th class="text-center">针工步骤</th>
													<th class="text-center">对工序大，辅工定性选择↓</th>
													<th class="text-center">该步骤用时</th>
													<th class="text-center">二次手动调整价格（有需要填写）</th>
													<th class="text-center">单工序单只时间</th>
													<th class="text-center">单工序单只放快手时间</th>
													<th class="text-center">工价/单只（含快手)</th>
													<th class="text-center">设备折旧和房水电费</th>
													<th class="text-center">管理人员费用</th>
													<th class="text-center">针工工序费用</th>
												</tr>
											</thead>
											<tbody id="tablecontent2">

											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>




	<div class="wrap">
		<div class="layer-right5" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12">
						<table>

							<tr>
								<th class="text-center">
								<td><input type="text" id="ordid5" class="hidden"></td>
								<th><button type="button"
										class="btn btn-info  btn-sm  btn-trans updateord">修改</button></th>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">放快手比:</th>
								<td><input type="text" id="quickWorker5"
									class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">冲棉机设备价值:</th>
								<td><input type="text" id="worth5"
									class="form-control actualtimetw"></td>
								<th class="text-center">每秒设备折旧费用:</th>
								<td><input type="text" disabled="disabled"
									id="depreciation5" class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">设置分摊天数:</th>
								<td><input type="text" id="shareDay5"
									class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">每天机器工作时间设置/小时:</th>
								<td><input type="text" id="workTime5"
									class="form-control actualtimetw"></td>
								<th class="text-center">每秒耗3费:</th>
								<td><input type="text" disabled="disabled"
									id="perSecondPriceTwo5" class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">小零件费用:</th>
								<td><input type="text" id="laserTubePrice5"
									class="form-control actualtimetw"></td>
								<th class="text-center">每秒小零件费用:</th>
								<td><input type="text" disabled="disabled"
									id="laserTubePriceSecond5" class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">分摊小时:</th>
								<td><input type="text" id="shareTime5"
									class="form-control actualtimetw"></td>
								<th class="text-center">每秒维护费用:</th>
								<td><input type="text" disabled="disabled"
									id="maintenanceChargeSecond5" class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">维护费用:</th>
								<td><input type="text" id="maintenanceCharge5"
									class="form-control actualtimetw"></td>
								<th class="text-center">冲棉间每小时耗电/元:</th>
								<td><input type="text" id="needleworkTwo5"
									class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">分摊小时:</th>
								<td><input type="text" id="shareTimeTwo5"
									class="form-control actualtimetw"></td>
								<th class="text-center">冲棉间每小时耗水/元:</th>
								<td><input type="text" id="needleworkThree5"
									class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">每小时耗电/元:</th>
								<td><input type="text" id="omnHorElectric5"
									class="form-control actualtimetw"></td>
								<th class="text-center">冲棉间每小时耗房租/元:</th>
								<td><input type="text" id="needleworkFour5"
									class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">每小时耗水/元:</th>
								<td><input type="text" id="omnHorWater5"
									class="form-control actualtimetw"></td>
								<th class="text-center">每秒耗3费:</th>
								<td><input type="text" disabled="disabled"
									id="perSecondPrice5" class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">每小时耗房租/元:</th>
								<td><input type="text" id="omnHorHouse5"
									class="form-control actualtimetw"></td>
								<th class="text-center">设定同时参与冲棉人员数量:</th>
								<td><input type="text" id="needleworkOne5"
									class="form-control"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">每小时冲棉工工价:</th>
								<td><input type="text" id="omnHorMachinist5"
									class="form-control actualtimetw"></td>
								<th class="text-center">每秒工价:</th>
								<td><input type="text" disabled="disabled"
									id="perSecondMachinist5" class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">每小时高端针工工价（面部表情，绣鼻子等）:</th>
								<td><input type="text" id="needleworkFive5"
									class="form-control actualtimetw"></td>
								<th class="text-center">每秒工价:</th>
								<td><input type="text" disabled="disabled"
									id="needleworkSeven5" class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">每小时一般针工工价（普通工序）:</th>
								<td><input type="text" id="needleworkSix5"
									class="form-control actualtimetw"></td>
								<th class="text-center">每秒工价:</th>
								<td><input type="text" disabled="disabled"
									id="needleworkEight5" class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">每小时辅工工价:</th>
								<td><input type="text" id="omnHorAuxiliary5"
									class="form-control actualtimetw"></td>
								<th class="text-center">每秒工价:</th>
								<td><input type="text" disabled="disabled"
									id="perSecondMachinistTwo5" class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">制版分配任务管理人员工资:</th>
								<td><input type="text" id="managePrice5"
									class="form-control actualtimetw"></td>
								<th class="text-center">每秒管理费用:</th>
								<td><input type="text" disabled="disabled"
									id="perSecondManage5" class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">管理设备数量:</th>
								<td><input type="text" id="manageEquipmentNumber5"
									class="form-control actualtimetw"></td>
								<th class="text-center">设置激光设备利润比:</th>
								<td><input type="text" id="equipmentProfit5"
									class="form-control actualtimetw"></td>
							</tr>
						</table>
					</div>

				</form>
			</div>
		</div>
	</div>






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
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
	<script src="${ctx }/static/js/vendor/mSlider.min.js"></script>
	<script src="${ctx }/static/js/vendor/typeahead.js"></script>
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
	  	this.getNum = function(){
	  		return _num;
	  	}
	  	this.setNum = function(num){
	  		_num=num;
	  	}
	  	var productIdAll="${productId}";
	  	var productNameAll="${productNamexx}";
	  	var productNumberAll="${productNumberxx}";
	  	self.setCache(productIdAll)
	  	$("#productName").val(productNameAll);
	  	$("#number").val(productNumberAll);
		 var data={
					page:1,
			  		size:100,	
			  		productId:productIdAll,
			} 
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
				self.loadPagination2(data);
			}
			//加载分页
			  this.loadPagination = function(data){
				  var index;
				    var html = '';
				    $.ajax({
					      url:"${ctx}/product/getNeedlework",
					      data:data,
					      type:"GET",
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			if(result.data.rows!=null && result.data.rows!=""){
				      			$("#ntwo").val(result.data.rows[0].oneNeedleworkPrice)
				      			}
			      			 $(result.data.rows).each(function(i,o){
			      				if(o.costPrice==o.reckoningEmbroideryPrice || o.costPrice==o.reckoningSewingPrice){
			      					 
			      				 }else{
			      					o.costPrice=""
			      				 }
			      				html+='<tr><td class="text-center reste"><label> <input type="checkbox" class="ace checkboxId" data-productid='+o.productId+' value="'+o.id+'"/><span class="lbl"></span></label></td>'
			      				 +'<td  class="text-center edit name needleworkName2" data-needlewor2='+o.needleworkName+' >'+o.needleworkName+'</td>'
			      				+'<td class="text-center edit selectid3 hidden"  >'+o.productId+'</td>'
			      				 +'<td class="text-center edit selectid hidden">'+o.id+'</td>'
			   					 +'<td class="text-center edit classify2">'+(o.classify!=null?o.classify:"")+'</td>'
			   					 +'<td class="text-center edit times2">'+o.seconds+'</td>'
			   					 +'<td class="text-center edit selectCompany">'+o.needleworkName+(o.classify!=null?o.classify:"")+'</td>'
			   					+'<td class="text-center edit materialsr" data-materialsr='+o.materials+' data-id='+o.id+' data-productid='+o.productId+'></td>'
			   					 +'<td class="text-center edit needleworkPrice">'+(o.needleworkPrice!=null?o.needleworkPrice:"")+'</td>'
			   					 +'<td class="text-center edit allCostPrice">'+(o.allCostPrice!=null?o.allCostPrice:"")+'</td>'
			   					 +'<td class="text-center edit priceDown">'+(o.priceDown!=null?o.priceDown:"")+'</td></tr>'
			      			}); 
						   	layer.close(index);
						   	 $("#tablecontent").html(html);
						   	self.loadEvents()
						   	 self.mater2()
						   	 self.checkedd()
					      },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);
						  }
					  });
			}
			  
			  this.loadPagination2 = function(data){
				  //机缝时间
				  $(".profile1").on('click',function(){
					  var index;
					    var html = '';
					    $.ajax({
						      url:"${ctx}/product/getNeedlework",
						      data:data,
						      type:"GET",
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			if(result.data.rows!=null && result.data.rows!=""){
					      			$("#ntwo1").val(result.data.rows[0].oneNeedleworkPrice)
					      			}
				      			 $(result.data.rows).each(function(i,o){
				      				html+='<tr>'
				      				 +'<td  class="text-center edit name tailorName2" data-productid='+o.productId+'>'+o.needleworkName+(o.classify!=null?o.classify:"")+'</td>'
				   					 +'<td class="text-center edit selectid2 hidden"  >'+o.id+'</td>'
				   					+'<td class="text-center edit selectid5 hidden"  >'+o.productId+'</td>'
				   					 +'<td class="text-center edit helpWork" data-helpwork='+o.helpWork+'></td>'
				   					 +'<td class="text-center edit">'+o.seconds+'</td>'
				   					 +'<td class="text-center edit"><input class="form-control secondaryPrice" data-id="'+o.id+'" value='+(o.secondaryPrice!=null?o.secondaryPrice:"")+'></td>'
				   					 +'<td class="text-center singleTime">'+parseFloat((o.singleTime).toFixed(5))+'</td>'
				   					 +'<td class="text-center edit singleQuickTime">'+parseFloat((o.singleQuickTime).toFixed(5))+'</td>'
				   					 +'<td class="text-center edit labourCost">'+parseFloat((o.labourCost).toFixed(5))+'</td>'
				   					 +'<td class="text-center edit equipmentPrice">'+parseFloat((o.equipmentPrice).toFixed(5))+'</td>'
				   					 +'<td class="text-center edit administrativeAtaff">'+parseFloat((o.administrativeAtaff).toFixed(5))+'</td>'
				   					 +'<td class="text-center edit needleworkPrice">'+parseFloat((o.needleworkPrice).toFixed(5))+'</td></tr>'
				      			 }); 
							   	layer.close(index);
							   	 $("#tablecontent2").html(html); 
							   	 self.loadEvents2();
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				  })
			  }
			  this.loadEvents2=function(){
				  
				  
				  
				//调用基础数据
				  var data2 = {
							type:"needlework",
						}
				  var index;
				  $.ajax({
				      url:"${ctx}/product/getPrimeCoefficient",
				      data:data2,
				      type:"GET",
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      			$("#ordid5").val(o.id)
		      			$("#electricPushOne5").val(o.electricPushOne)
		      			$("#electricPushTwo5").val(o.electricPushTwo)
		      			$("#electricPushThree5").val(o.electricPushThree)
		      			$("#electricPushFour5").val(o.electricPushFour)
		      			$("#electricPushFive5").val(o.electricPushFive)
		      			$("#time5").val(o.time)
		      			$("#quickWorker5").val(o.quickWorker)
		      			$("#worth5").val(o.worth)
		      			$("#depreciation5").val(o.depreciation)
		      			$("#shareDay5").val(o.shareDay)
		      			$("#workTime5").val(o.workTime)
		      			$("#laserTubePrice5").val(o.laserTubePrice)
		      			$("#laserTubePriceSecond5").val(o.laserTubePriceSecond)
		      			$("#shareTime5").val(o.shareTime)
		      			$("#maintenanceChargeSecond5").val(o.maintenanceChargeSecond)
		      			$("#maintenanceCharge5").val(o.maintenanceCharge)
		      			$("#shareTimeTwo5").val(o.shareTimeTwo)
		      			$("#omnHorElectric5").val(o.omnHorElectric)
		      			$("#omnHorWater5").val(o.omnHorWater)
		      			$("#perSecondPrice5").val(o.perSecondPrice)
		      			$("#omnHorHouse5").val(o.omnHorHouse)
		      			$("#omnHorMachinist5").val(o.omnHorMachinist)
		      			$("#perSecondMachinist5").val(o.perSecondMachinist)
		      			$("#omnHorAuxiliary5").val(o.omnHorAuxiliary)
		      			$("#perSecondMachinistTwo5").val(o.perSecondMachinistTwo)
		      			$("#managePrice5").val(o.managePrice)
		      			$("#perSecondManage5").val(o.perSecondManage)
		      			$("#manageEquipmentNumber5").val(o.manageEquipmentNumber)
		      			$("#equipmentProfit5").val(o.equipmentProfit)
		      			$("#embroideryLaserNumber5").val(o.embroideryLaserNumber)
		      			$("#perimeterLess5").val(o.perimeterLess)
		      			$("#perimeterLessNumber5").val(o.perimeterLessNumber)
		      			$("#omnHorAuxiliary5").val(o.omnHorAuxiliary)
		      			$("#perSecondMachinistTwo5").val(o.perSecondMachinistTwo)
		      			$("#machinistOne5").val(o.machinistOne)
		      			$("#machinisttwo5").val(o.machinisttwo)
		      			$("#machinistThree5").val(o.machinistThree)
		      			$("#embroiderySix5").val(o.embroiderySix)
		      			$("#embroideryOne5").val(o.embroideryOne)
		      			$("#embroideryTwo5").val(o.embroideryTwo)
		      			$("#embroideryThree5").val(o.embroideryThree)
		      			$("#embroideryFour5").val(o.embroideryFour)
		      			$("#embroideryFive5").val(o.embroideryFive)
		      			$("#embroiderySeven5").val(o.embroiderySeven)
		      			$("#embroideryEight5").val(o.embroideryEight)
		      			$("#embroideryNine5").val(o.embroideryNine)
		      			$("#embroideryTen5").val(o.embroideryTen)
		      			$("#embroideryEleven5").val(o.embroideryEleven)
		      			$("#embroideryTwelve5").val(o.embroideryTwelve)
		      			$("#embroideryThirteen5").val(o.embroideryThirteen)
		      			$("#embroideryFourteen5").val(o.embroideryFourteen)
		      			$("#needleworkFive5").val(o.needleworkFive)
		      			$("#needleworkSix5").val(o.needleworkSix)
		      			$("#needleworkSeven5").val(o.needleworkSeven)
		      			$("#needleworkEight5").val(o.needleworkEight)
		      			$("#needleworkOne5").val(o.needleworkOne)
		      			$("#perSecondPriceTwo5").val(o.perSecondPriceTwo)
		      			$("#needleworkTwo5").val(o.needleworkTwo)
		      			$("#needleworkThree5").val(o.needleworkThree)
		      			$("#needleworkFour5").val(o.needleworkFour)
		      			 }); 
		      		  },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
				  $(".updateord").on('click',function(){
					  var data={
					  	id:$("#ordid5").val(),
					  	extent:12,
					  	needleworkFour:$("#needleworkFour5").val(),
					  	needleworkThree:$("#needleworkThree5").val(),
					  	needleworkTwo:$("#needleworkTwo5").val(),
					  	perSecondPriceTwo:$("#perSecondPriceTwo5").val(),
					  	needleworkOne:$("#needleworkOne5").val(),
					  	needleworkEight:$("#needleworkEight5").val(),
					  	needleworkSeven:$("#needleworkSeven5").val(),
					  	needleworkSix:$("#needleworkSix5").val(),
					  	needleworkFive:$("#needleworkFive5").val(),
					  	embroiderySeven:$("#embroiderySeven5").val(),
					  	embroideryEight:$("#embroideryEight5").val(),
					  	embroideryNine:$("#embroideryNine5").val(),
					  	embroideryTen:$("#embroideryTen5").val(),
					  	embroideryEleven:$("#embroideryEleven5").val(),
					  	embroideryTwelve:$("#embroideryTwelve5").val(),
					  	embroideryThirteen:$("#embroideryThirteen5").val(),
					  	embroideryFourteen:$("#embroideryFourteen5").val(),
					  	embroideryOne:$("#embroideryOne5").val(),
					  	embroideryTwo:$("#embroideryTwo5").val(),
					  	embroideryThree:$("#embroideryThree5").val(),
					  	embroideryFour:$("#embroideryFour5").val(),
					  	embroideryFive:$("#embroideryFive5").val(),
					  	embroiderySix:$("#embroiderySix5").val(),
					  	electricPushOne:$("#electricPushOne5").val(),
					  	electricPushTwo:$("#electricPushTwo5").val(),
					  	electricPushThree:$("#electricPushThree5").val(),
					  	electricPushFour:$("#electricPushFour5").val(),
					  	electricPushFour:$("#electricPushFour5").val(),
					  	electricPushFive:$("#electricPushFive5").val(),
					  	quickWorker:$("#quickWorker5").val(),
					  	worth:$("#worth5").val(),
					  	shareDay:$("#shareDay5").val(),
					  	workTime:$("#workTime5").val(),
					  	laserTubePrice:$("#laserTubePrice5").val(),
					  	shareTime:$("#shareTime5").val(),
					  	maintenanceCharge:$("#maintenanceCharge5").val(),
					  	shareTimeTwo:$("#shareTimeTwo5").val(),
					  	omnHorElectric:$("#omnHorElectric5").val(),
					  	omnHorWater:$("#omnHorWater5").val(),
					  	omnHorHouse:$("#omnHorHouse5").val(),
					  	omnHorMachinist:$("#omnHorMachinist5").val(),
					  	omnHorAuxiliary:$("#omnHorAuxiliary5").val(),
					  	managePrice:$("#managePrice5").val(),
					  	manageEquipmentNumber:$("#manageEquipmentNumber5").val(),
					  	equipmentProfit:$("#equipmentProfit5").val(),
					  	perimeterLessNumber:$("#perimeterLessNumber5").val(),
					  	perimeterLess:$("#perimeterLess5").val(),
					  	embroideryLaserNumber:$("#embroideryLaserNumber5").val(),
					  	perSecondMachinistTwo:$("#perSecondMachinistTwo5").val(),
					  	omnHorAuxiliary:$("#omnHorAuxiliary5").val(),
					  	machinistOne:$("#machinistOne5").val(),
					  	machinisttwo:$("#machinisttwo5").val(),
					  	machinistThree:$("#machinistThree5").val(),
					  }
					  $.ajax({
					      url:"${ctx}/product/updatePrimeCoefficient",
					      data:data,
					      type:"POST",
			      		  success: function (result) {
			      			 $(result.data).each(function(i,o){
			      				$("#time5").val(o.time)
			      				$("#depreciation5").val(o.depreciation)
			      				$("#laserTubePriceSecond5").val(o.laserTubePriceSecond)
			      				$("#maintenanceChargeSecond5").val(o.maintenanceChargeSecond)
			      				$("#perSecondPrice5").val(o.perSecondPrice)
			      				$("#perSecondMachinist5").val(o.perSecondMachinist)
			      				$("#perSecondManage5").val(o.perSecondManage)
			      				$("#perSecondMachinistTwo5").val(o.perSecondMachinistTwo)
			      				$("#time5").val(o.time)
			      				if(0==result.code){
									layer.msg(result.message, {icon: 1});
									layer.close(index);
									}else{
										layer.msg(result.message, {icon: 2});
										layer.close(index);
									}
			      			}); 
			      		  },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);sa
						  }
					  });
				  })
				  
				  
				  
				  
				  
				  
				  
					 var data6 = {
							type:"helpWork",
						}
						var index;
						var html6 = '';
						var htmlto6= '';
						$.ajax({
						url:"${ctx}/product/getBaseOne",
						data:data6,
						type:"GET",
						success: function (result) {
						$(result.data).each(function(i,o){
							html6 +='<option value="'+o.id+'" data-textua='+o.textualTime+'>'+o.name+'</option>'
							}); 
							htmlto6='<select class="text-center form-control selecttailorType8" ><option value="">请选择</option>'+html6+'</select>'
							 $(".helpWork").html(htmlto6)
									      		 
									      		  //改变事件
							 $(".selecttailorType8").each(function(i,o){
								var id=	$(o).parent().data("helpwork");
								$(o).find("option:selected").text(id)
							})
									$(".selecttailorType8").change(function(i,o){
									      				var that=$(this);
									      				var tailorId=$(this).parent().parent().find(".selecttailorType8").val();
									      				var helpWorkPrice=$(this).parent().parent().find(".selecttailorType8 option:selected").data('textua');
									      				var name=$(this).parent().parent().find(".selecttailorType8 option:selected").text();
									      				var dataeee={
									      						id:that.parent().parent().find('.selectid2').text(),
									      						helpWork:name,
									      						helpWorkPrice:helpWorkPrice,
									      						productId:that.parent().parent().find('.selectid5').text(),
									      				}
									      				var index;
									      				 $.ajax({
														      url:"${ctx}/product/addNeedlework",
														      data:dataeee,
														      type:"POST",
														      beforeSend:function(){
																	index = layer.load(1, {
																		  shade: [0.1,'#fff'] //0.1透明度的白色背景
																		});
																},
																success:function(result){
																	if(0==result.code){
																	$(result.data).each(function(i,o){
														      			 that.parent().parent().find(".singleTime").text(o.singleTime);
														      			 that.parent().parent().find(".singleQuickTime").text(parseFloat((o.singleQuickTime).toFixed(5)));  
														      			that.parent().parent().find(".labourCost").text(parseFloat((o.labourCost).toFixed(5)));  
														      			that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
														      			that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));
														      			that.parent().parent().find(".needleworkPrice").text(parseFloat((o.needleworkPrice).toFixed(5)));
														      			$("#ntwo1").val(o.oneNeedleworkPrice)	
																	}); 
																	layer.close(index);
																	}else{
																		layer.msg(result.message, {icon: 2});
																		layer.close(index);
																	}
																},error:function(){
																	layer.msg("加载失败！", {icon: 2});
																	layer.close(index);sa
															  }
														  }); 
													})
									      		  },error:function(){
														layer.msg("加载失败！", {icon: 2});
														layer.close(index);
												  }
											  });
						
							$(".secondaryPrice").blur(function(){
								var that=$(this);
								var dataeee={
			      						id:that.parent().parent().find('.selectid2').text(),
			      						productId:that.parent().parent().find('.selectid5').text(),
			      						secondaryPrice:that.parent().parent().find('.secondaryPrice').val(),
			      				}
								var index;
			      				 $.ajax({
								      url:"${ctx}/product/addNeedlework",
								      data:dataeee,
								      type:"POST",
								      beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										success:function(result){
											if(0==result.code){
											layer.close(index);
											$(result.data).each(function(i,o){
								      			 that.parent().parent().find(".singleTime").text(o.singleTime);
								      			 that.parent().parent().find(".singleQuickTime").text(parseFloat((o.singleQuickTime).toFixed(5)));  
								      			that.parent().parent().find(".labourCost").text(parseFloat((o.labourCost).toFixed(5)));  
								      			that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
								      			that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));
								      			that.parent().parent().find(".needleworkPrice").text(parseFloat((o.needleworkPrice).toFixed(5)));
								      			$("#ntwo1").val(o.oneNeedleworkPrice)	
											});
											}else{
												layer.msg(result.message, {icon: 2});
												layer.close(index);
											}
										},error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(index);sa
									  }
								  }); 
							})
												
			  }
			  this.loadEvents=function(){
				 
				  var dataeer={
						  productId:productIdAll,
						  type:"needlework",
				  }
				  var index;
				  var htmli = '';
				  var htmltoi= '';
				  $.ajax({
				      url:"${ctx}/product/getOverStock",
				      data:dataeer,
				      type:"GET",
				      beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
		      		  success: function (result) {
		      			$(result.data).each(function(i,o){
		      				htmli +='<option value="'+o.name+'">'+o.name+'</option>'
		      			}); 
				       htmltoi='<select class="text-center form-control selecttailorTypey" ><option value="">请选择</option>'+htmli+'</select>'
		      		  $(".materialsr").html(htmltoi)
		      				layer.close(index);
				       $(".selecttailorTypey").each(function(i,o){
		      				var id=	$(o).parent().data("materialsr");
							$(o).val(id)
						})
				       
						$(".selecttailorTypey").change(function(i,o){
				      				var that=$(this);
				      				var id=$(this).parent().parent().find(".materialsr").data('id');
				      				var productId=$(this).parent().parent().find(".materialsr").data('productid');
				      				var dataeee={
				      						id:id,
				      						productId:productId,
				      						materials:$(this).parent().parent().find(".selecttailorTypey").val(),
				      						
				      				}
				      				var index;
				      				$.ajax({
									      url:"${ctx}/product/addNeedlework",
									      data:dataeee,
									      type:"POST",
									      beforeSend:function(){
												index = layer.load(1, {
													  shade: [0.1,'#fff'] //0.1透明度的白色背景
													});
											},
											success:function(result){
												if(0==result.code){
												layer.close(index);
												}else{
													layer.msg("添加失败！", {icon: 2});
													layer.close(index);
												}
											},error:function(){
												layer.msg("加载失败！", {icon: 2});
												layer.close(index);sa
										  }
									  });
								})
						
						
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
				  
				  
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
			  
			  this.mater2=function(){
					
				  var data = {
							type:"needlework",//传产品id
						}
						var index;
					    var html = '';
					    var htmlto= '';
					    var htmlto2= '';
					    
					    $.ajax({
						      url:"${ctx}/product/getBaseOne",
						      data:data,
						      type:"GET",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      				html +='<option value="'+o.id+'">'+o.name+'</option>'
				      			}); 
						       htmlto='<select class="text-center form-control selecttailorType2" ><option value="">请选择</option>'+html+'</select>'
						       $(".needleworkName2").html(htmlto)	
						       $(".selecttailorType2").each(function(i,o){
				      				var id=	$(o).parent().data("needlewor2");
									$(o).find('option:selected').text(id)
								})
						      $(".selecttailorType2").change(function(i,o){
						       var datae={
						    		   id:$(this).parent().parent().find(".selecttailorType2").val(),
						       }
						       var html2 = '';
						       $.ajax({
								      url:"${ctx}/product/getBaseOne",
								      data:datae,
								      type:"GET",
						      		  success: function (result) {
						      			 $(result.data[0].baseOneTimes).each(function(j,k){
						      				html2 +='<option value="'+k.time+'">'+k.categorySetting+'</option>'
						      			}); 
						      			 htmlto2='<select class="text-center form-control selecttailorTypee2" ><option value="">请选择</option>'+html2+'</select>'
						      			 $(".classify2").html(htmlto2)
						      			 
						      			 $(".selecttailorTypee2").change(function(i,o){
						      				$(".times2").text($(this).parent().parent().find(".selecttailorTypee2").val())
						      				var that=$(this)
						      				var dataeee={
					      						id:that.parent().parent().find('.selectid').text(),
					      						needleworkName:that.parent().parent().find('.selecttailorType2 option:selected').text(),
					      						classify:that.parent().parent().find(".selecttailorTypee2 option:selected").text(),
					      						seconds:$(this).parent().parent().find(".selecttailorTypee2").val(),
					      						productId: that.parent().parent().find('.selectid3').text(),
					      						number:$('#number').val(),
					      				}
					      				var index;
					      				$.ajax({
										      url:"${ctx}/product/addNeedlework",
										      data:dataeee,
										      type:"POST",
										      beforeSend:function(){
													index = layer.load(1, {
														  shade: [0.1,'#fff'] //0.1透明度的白色背景
														});
												},
												success:function(result){
													if(0==result.code){
														var id=result.data.id
														that.parent().parent().find('.selectid').text(id);
													layer.close(index);
													}else{
														layer.msg(result.message, {icon: 2});
														layer.close(index);
													}
												},error:function(){
													layer.msg("加载失败！", {icon: 2});
													layer.close(index);sa
											  }
										  });
						      			 })
						      		  },error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(index);
									  }
								  });
						      })
				      		 
				      		  },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				}
			  
			  
			  this.mater=function(){
					
				  var data = {
							type:"needlework",//传产品id
						}
						var index;
					    var html = '';
					    var htmlto= '';
					    var htmlto2= '';
					    var html2 = '';
					    $.ajax({
						      url:"${ctx}/product/getBaseOne",
						      data:data,
						      type:"GET",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      				html +='<option value="'+o.id+'">'+o.name+'</option>'
				      			}); 
						       htmlto='<select class="text-center form-control selecttailorType2" ><option value="">请选择</option>'+html+'</select>'
						       $(".needleworkName").html(htmlto)	
						      $(".selecttailorType2").change(function(i,o){
						       var datae={
						    		   id:$(this).parent().parent().find(".selecttailorType2").val(),
						       }
						       var html2 = '';
						       $.ajax({
								      url:"${ctx}/product/getBaseOne",
								      data:datae,
								      type:"GET",
						      		  success: function (result) {
						      			 $(result.data[0].baseOneTimes).each(function(j,k){
						      				html2 +='<option value="'+k.time+'">'+k.categorySetting+'</option>'
						      			}); 
						      			 htmlto2='<select class="text-center form-control selecttailorTypee2" ><option value="">请选择</option>'+html2+'</select>'
						      			 $(".classify").html(htmlto2)
						      			 $(".selecttailorTypee2").change(function(i,o){
						      				$(".times").text($(this).parent().parent().find(".selecttailorTypee2").val())
						      				var that=$(this)
						      				var dataeee={
					      						id:that.parent().parent().find('.selectid').text(),
					      						needleworkName:that.parent().parent().find('.selecttailorType2 option:selected').text(),
					      						classify:that.parent().parent().find(".selecttailorTypee2 option:selected").text(),
					      						seconds:$(this).parent().parent().find(".selecttailorTypee2").val(),
					      						productId: self.getCache(),
					      						number:$('#number').val(),
					      				}
					      				var index;
					      				$.ajax({
										      url:"${ctx}/product/addNeedlework",
										      data:dataeee,
										      type:"POST",
										      beforeSend:function(){
													index = layer.load(1, {
														  shade: [0.1,'#fff'] //0.1透明度的白色背景
														});
												},
												success:function(result){
													if(0==result.code){
													layer.close(index);
													}else{
														layer.msg(result.message, {icon: 2});
														layer.close(index);
													}
												},error:function(){
													layer.msg("加载失败！", {icon: 2});
													layer.close(index);sa
											  }
										  });
						      			 })
						      		  },error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(index);
									  }
								  });
						      })
				      		 
				      		  },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				}
			
			this.events = function(){
				$('#addCutting3').on('click',function(){
				 var arr = ["验货","吹毛","打吊牌","剪线头","绞口","挑脸整形","整体冲棉","翻皮壳"];
				var arr1 = ["20CM","25CM","2个","25CM","4CM以下","20CM","80克左右","25CM左右"];
				var arr2 = ["9","3.5","7","4.5","32","20","19.8","11"];
				for (var i = 0; i < 8; i++) {
					var d=arr[i]
					var t=arr1[i]
					var o=arr2[i]
					var dataeee={
      						needleworkName:d,
      						classify:t,
      						seconds:o,
      						productId:productIdAll,
      						number:$('#number').val(),
      				}
					var index;
      				$.ajax({
					      url:"${ctx}/product/addNeedlework",
					      data:dataeee,
					      async:false,
					      type:"POST",
					      beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								if(0==result.code){
									var data = {
								  			page:1,
								  			size:100,
								  			productId:productIdAll,
								  	}
						            self.loadPagination(data);
								layer.close(index);
								}else{
									layer.msg(result.message, {icon: 2});
									layer.close(index);
								}
							},error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);sa
						  }
					  });
				} 
				 })
				$(".home1").on('click',function(){
					var data={
							page:1,
					  		size:100,	
					  		productId:productIdAll,
					}
					self.loadPagination(data);
				})
				
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:100,
				  			productId:self.getCache(),
				  	}
		            self.loadPagination(data);
				});
				
				$(".searchtask2").on('click',function(){
					$(".layer-right5").css("display","block");
					var demo = new mSlider({
						dom:".layer-right5",
						direction: "right",
						distance:"35%",
						
					})
					demo.open()
				})
				
				$('.start').on('click',function(){
					  var  that=$(this);
					  var arr=new Array()//员工id
						$(this).parent().parent().parent().parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() { 
							arr.push($(this).val());   
						});
					   if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						} 
						var data={
								ids:arr,
						}
						
						var index;
						 index = layer.confirm('确定一键删除吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/product/deleteNeedlework",
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
									var data={
											page:1,
									  		size:100,	
									  		productId:productIdAll,
									}
									self.loadPagination(data);
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
				
			//新增
				var html="";
				$('#addCutting').on('click',function(){
					 html='<tr><td  class="text-center"></td><td  class="text-center edit needleworkName"></td>'
					 +'<td class="text-center edit selectid hidden"></td>'
					 +'<td class="text-center edit classify" ></td>'
					 +'<td class="text-center edit times" ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit "></td></tr>';
					  /* $(html).insertBefore("#tablecontent"); */
					/* $("#tablecontent").append(html); */
					$("#tablecontent").prepend(html);
					self.mater();
				})
				$('#addCutting2').on('click',function(){
					 html='<tr><td  class="text-center"></td><td  class="text-center edit "><input class="text-center form-control needleworkName22"/></td>'
					 +'<td class="text-center edit selectid22 hidden"></td>'
					 +'<td class="text-center edit classify22" ></td>'
					 +'<td class="text-center edit " ><input class="text-center form-control times22"/></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit "></td></tr>';
					  /* $(html).insertBefore("#tablecontent"); */
					/* $("#tablecontent").append(html); */
					$("#tablecontent").prepend(html);
					$(".times22").blur(function(){
						var ttat=$(this)
						var a=$(this).val()
						var b=$(this).parent().parent().find(".needleworkName22").val()
						if(b==""){
							return layer.msg("设定完毕的针工工序不能为空", {icon: 2});
						}
						var dataeee={
								id:$(this).parent().parent().find(".selectid22").text(),
	      						needleworkName:b,
	      						seconds:a,
	      						productId:productIdAll,
	      						number:$('#number').val(),
	      				}
						var index;
	      				$.ajax({
						      url:"${ctx}/product/addNeedlework",
						      data:dataeee,
						      type:"POST",
						      beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								success:function(result){
									if(0==result.code){
										var id=result.data.id
										ttat.parent().parent().find('.selectid22').text(id);
									layer.close(index);
									}else{
										layer.msg(result.message, {icon: 2});
										layer.close(index);
									}
								},error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);sa
							  }
						  });
					})
				})
				//提示产品名
				$("#productName").typeahead({
					//ajax 拿way数据
					source : function(query, process) {
							return $.ajax({
								url : '${ctx}/getProductPages',
								type : 'GET',
								data : {
									name:query,
								},
								
								success : function(result) {
									//转换成 json集合
									 var resultList = result.data.rows.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.name, id:item.id, number:item.primeCost==null ? "" : item.primeCost.number}
					                        //处理 json对象为字符串
					                        return JSON.stringify(aItem);
					                    });
									//提示框返回数据
									 return process(resultList);
								},
							})
							//提示框显示
						}, highlighter: function (item) {
						    //转出成json对象
							 var item = JSON.parse(item);
							return item.name+"-"+item.id
							//按条件匹配输出
		                }, matcher: function (item) {
		                	//转出成json对象
					        var item = JSON.parse(item);
					        self.setCache(item.id)
					        $('#number').val(item.number)
					    	return item.name
					    },
						//item是选中的数据
						updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
							self.setCache(item.id)
							 $('#number').val(item.number)
								return item.name
						},
						
					});
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>


</body>

</html>