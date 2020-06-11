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
<title>机工填写</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />


</head>

<body>
	<section id="main-wrapper" class="theme-default">


		<!--main content start-->

		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">机工填写页面</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i> <i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="panel-body">
							<div class="tab-wrapper tab-primary">
								<ul class="nav nav-tabs col-md-12">
									<li class="active col-md-2" style="width: 50%"><a
										href="#home1" class="home1" data-toggle="tab">机工页面</a></li>
									<li class="col-md-2" style="width: 50%;"><a
										href="#profile1" class="profile1" data-toggle="tab">机缝时间</a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="home1">
										<!--查询开始  -->
										<div class="row" style="height: 30px; margin: 15px 0 10px">
											<div class="col-xs-11 col-sm-11  col-md-11">
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
																			placeholder="请输入默认数量"
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
																	<button type="button" id="addCutting"
																		class="btn btn-success  btn-sm btn-3d export">
																		新增</button>
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
													<th class="text-center">填写机缝名</th>
													<th class="text-center">用除裁片以外的物料</th>
													<th class="text-center">选择用到裁片</th>
													<th class="text-center">用到裁片或上道</th>
													<th class="text-center">电脑推算机缝工序费用</th>
													<th class="text-center">试制机缝工序费用</th>
													<th class="text-center">选择入行成本价</th>
													<th class="text-center">入成本价格</th>
													<th class="text-center">各单道比全套工价</th>
													<th class="text-center">物料和上道压（裁剪）价</th>
													<th class="text-center">物料和上道压（裁剪,上道机工价</th>
													<th class="text-center">为针工准备的压价</th>
													<th class="text-center">单独机工工序外发的压价</th>
												</tr>
											</thead>
											<tbody id="tablecontent">

											</tbody>
										</table>
									</div>
									<div class="tab-pane" id="profile1">
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
										<table class="table table-hover">
											<thead>
												<tr>
													<th class="text-center">机缝工序</th>
													<th class="text-center">针号</th>
													<th class="text-center">线色或线号</th>
													<th class="text-center">针距</th>
													<th class="text-center">试制净快手时间</th>
													<th class="text-center">该工序回针次数</th>
													<th class="text-center">直线机缝模式</th>
													<th class="text-center">该工序满足G列</th>
													<th class="text-center">弧线机缝模式</th>
													<th class="text-center">该工序满足I列</th>
													<th class="text-center">弯曲复杂机缝模式</th>
													<th class="text-center">该工序满足K列</th>
													<th class="text-center">单一机缝需要时间/秒</th>
													<th class="text-center">设备折旧和房水电费</th>
													<th class="text-center">管理人员费用</th>
													<th class="text-center">电脑推算机缝该工序费用</th>
													<th class="text-center">试制机缝该工序费用</th>
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








	<div id="addworking" style="display: none;">
		<div class="panel-body">
			<div class="form-group"></div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="text-center">用到裁片或上道</th>
						<th class="text-center">压价</th>
						<th class="text-center">操作</th>
					</tr>
				</thead>
				<tbody id="tableworking2">
				</tbody>
			</table>
			<div id="pagerr" class="pull-right"></div>
		</div>
		<!--隐藏框 已完成的批次结束  -->
	</div>


	<div class="wrap">
		<div class="layer-right5" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12">
						<table>

							<tr>
								<td><input type="text" id="ordid5" class="hidden"></td>
								<th><button type="button"
										class="btn btn-info  btn-sm  btn-trans updateord">修改</button></th>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">黏片时间/片/秒:</th>
								<td><input type="text" id="machinistOne5"
									class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">剪线时间/片/秒:</th>
								<td><input type="text" id="machinisttwo5"
									class="form-control actualtimetw"></td>
								<th class="text-center">回针时间/个/秒:</th>
								<td><input type="text" id="machinistThree5"
									class="form-control actualtimetw"></td>
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
								<th class="text-center">缝纫机设备价值:</th>
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
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">分摊小时:</th>
								<td><input type="text" id="shareTimeTwo5"
									class="form-control actualtimetw"></td>
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">每小时耗电/元:</th>
								<td><input type="text" id="omnHorElectric5"
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
							</tr>
							<tr>
								<td><div style="height: 10px"></div></td>
							</tr>
							<tr>
								<th class="text-center">每小时机工工价:</th>
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
			  		/* sort:{"keys":"id","sortType":"ASC"}, */
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
					      url:"${ctx}/product/getMachinist",
					      data:data,
					      type:"GET",
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			if(result.data.rows!=null && result.data.rows!=""){
			      			$("#ntwo").val(result.data.rows[0].oneMachinistPrice)
			      			}
			      			 $(result.data.rows).each(function(i,o){
			      				if(o.costPrice==o.reckoningSewingPrice || o.costPrice==o.trialSewingPrice){
			      					 
			      				 }else{
			      					o.costPrice=""
			      				 }
			      				html+='<tr><td class="text-center reste"><label> <input type="checkbox" class="ace checkboxId" data-productid='+o.productId+' value="'+o.id+'"/><span class="lbl"></span></label></td>'
			      				 +'<td  class="text-center edit name"  style="padding: 9px 0px 2px 4px;"><input type="text" value="'+o.machinistName+'" style="border: none;width:120px; height:30px; background-color: #BFBFBF;" data-provide="typeahead" autocomplete="off" class="text-center  machinistName" /></td>'
			   					 +'<td class="text-center edit selectid hidden">'+o.id+'</td>'
			   					 +'<td class="text-center edit materialsr" data-materialsr='+o.materials+' data-id='+o.id+' data-productid='+o.productId+'></td>'
			   					 +'<td class="text-center edit selectCompany" style="padding: 2px 0px 2px 0px;"></td>'
			   					 +'<td class="text-center edit selectbodytw"><a>查看</a></td>'
			   					 +'<td class="text-center edit selectbody2 hidden">'+o.cutparts+','+'</td>'
			   					 +'<td class="text-center edit selectprice2 hidden">'+o.cutpartsPrice+','+'</td>'
			   					 +'<td class="text-center edit name">'+o.reckoningSewingPrice+'</td>'
			   					 +'<td class="text-center edit" >'+o.trialSewingPrice+' </td>'
			   					 +'<td class="text-center edit"><select class="form-control costPrice"><option value="'+(o.costPrice!=null?o.costPrice:"")+'">'+(o.costPrice!=null?o.costPrice:"")+'</option><option value="'+(o.reckoningSewingPrice!=null?o.reckoningSewingPrice:"")+'">'+(o.reckoningSewingPrice!=null?o.reckoningSewingPrice:"")+'</option><option value="'+(o.trialSewingPrice!=null?o.trialSewingPrice:"")+'">'+(o.trialSewingPrice!=null?o.trialSewingPrice:"")+'</option></select></td>'
			   					 +'<td class="text-center edit allCostPrice" >'+parseFloat((o.allCostPrice).toFixed(5))+' </td>'
			   					 +'<td class="text-center edit scaleMaterial" >'+parseFloat((o.scaleMaterial).toFixed(5))+' </td>'
			   					 +'<td class="text-center edit priceDown" >'+parseFloat((o.priceDown).toFixed(5))+' </td>'
			   					 +'<td class="text-center edit priceDownRemark" >'+parseFloat((o.priceDownRemark).toFixed(5))+' </td>'
			   					 +'<td class="text-center edit needleworkPriceDown" >'+parseFloat((o.needleworkPriceDown).toFixed(5))+' </td>'
			   					 +'<td class="text-center edit machinistPriceDown" >'+parseFloat((o.machinistPriceDown).toFixed(5))+' </td></tr>'
			      			}); 
			      			self.setCount(result.data.pageNum)
					        //显示分页
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
						      url:"${ctx}/product/getMachinist",
						      data:data,
						      type:"GET",
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			if(result.data.rows!=null && result.data.rows!=""){
				      			$("#ntwo1").val(result.data.rows[0].oneMachinistPrice)
				      			}
				      			 $(result.data.rows).each(function(i,o){
				      				 html +='<tr>'
				      				+'<td class="text-center edit ">'+o.machinistName+'</td>'
				      				+'<td class="text-center needlesize"  data-id='+o.id+' data-productid='+o.productId+' data-needlesize='+o.needlesize+' style="width: 80px;"></td>'
				      				+'<td class="text-center wiresize" data-wiresize='+o.wiresize+' style="width: 100px;"></td>'
				      				+'<td class="text-center edit needlespur" data-needlespur='+o.needlespur+' style="width: 80px;"></td>'
				      				+'<td class="text-center edit"><input class="form-control time"  style="width: 50px;"  value='+(o.time!=0?o.time:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control backStitchCount" data-id="'+o.id+'" style="width: 50px;"  value='+(o.backStitchCount!=0?o.backStitchCount:"")+'></td>'
				      				+'<td class="text-center beeline" data-beeline='+o.beeline+' style="width: 100px;"></td>'
				      				+'<td class="text-center"><input class="form-control beelineNumber" data-id="'+o.id+'" style="width: 80px;" value='+(o.beelineNumber!=0?o.beelineNumber:"")+'></td>'
				      				+'<td class="text-center arc" data-arc='+o.arc+' style="width: 100px;"></td>'
				      				+'<td class="text-center"><input class="form-control arcNumber" data-id="'+o.id+'" style="width: 80px;" value='+(o.arcNumber!=0?o.arcNumber:"")+'></td>'
				      				+'<td class="text-center bend" data-bend='+o.bend+' style="width: 100px;"></td>'
				      				+'<td class="text-center"><input class="form-control bendNumber" data-id="'+o.id+'" style="width: 80px;" value='+(o.bendNumber!=0?o.bendNumber:"")+'></td>'
				      				+'<td class="text-center edit oneSewingTime">'+parseFloat((o.oneSewingTime).toFixed(5))+'</td>'
				      				+'<td class="text-center edit equipmentPrice">'+parseFloat((o.equipmentPrice).toFixed(5))+'</td>'
				      				+'<td class="text-center edit administrativeAtaff">'+parseFloat((o.administrativeAtaff).toFixed(5))+'</td>'
				      				+'<td class="text-center edit reckoningSewingPrice">'+parseFloat((o.reckoningSewingPrice).toFixed(5))+'</td>'
				      				+'<td class="text-center edit trialSewingPrice">'+parseFloat((o.trialSewingPrice).toFixed(5))+'</td></tr>'
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
			  this.loadEvents=function(){
				  
				  
				  var dataeer={
						  productId:productIdAll,
						  type:"machinist",
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
									      url:"${ctx}/product/addMachinist",
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
				  
				  
				  
				  
				  $(".costPrice").change(function(){
				    	var that=$(this);
		      			var id=$(this).parent().parent().find(".checkboxId").val();
		      			var productId=$(this).parent().parent().find(".checkboxId").data('productid');
		      			var	datae={
				    			id:id,
				    			costPrice:$(this).val(),
				    			productId:productId
				    	}
		      			var index;
				    	$.ajax({
						      url:"${ctx}/product/addMachinist",
						      data:datae,
						      type:"POST",
						      beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
				      		  success: function (result) {
				      			if(0==result.code){
									$(result.data).each(function(i,o){
										that.parent().parent().find(".allCostPrice").text(parseFloat((o.allCostPrice).toFixed(5)));
						      			 that.parent().parent().find(".scaleMaterial").text(parseFloat((o.scaleMaterial).toFixed(5)));
						      			 that.parent().parent().find(".priceDown").text(parseFloat((o.priceDown).toFixed(5)));  
						      			that.parent().parent().find(".needleworkPriceDown").text(parseFloat((o.needleworkPriceDown).toFixed(5)));  
						      			that.parent().parent().find(".priceDownRemark").text(parseFloat((o.priceDownRemark).toFixed(5)));
						      			that.parent().parent().find(".machinistPriceDown").text(parseFloat((o.machinistPriceDown).toFixed(5)));
						      			$("#ntwo").val(o.oneMachinistPrice)	 
									});
								layer.close(index);
								}else{
									layer.msg("添加失败！", {icon: 2});
									layer.close(index);
								}
				      			
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
							type:"machinist",
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
				  
				  
				  
				  
				  
				//遍历裁剪方式
				  var data = {
							type:"needlesize",
						}
						var index;
					    var html = '';
					    var htmlto= '';
					    $.ajax({
						      url:"${ctx}/product/getBaseOne",
						      data:data,
						      type:"GET",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      				html +='<option value="'+o.id+'">'+o.name+'</option>'
				      			}); 
						       htmlto='<select class="text-center form-control selecttailorType2" ><option value="">请选择</option>'+html+'</select>'
				      		  $(".needlesize").html(htmlto)
				      		  //改变事件
				      		  $(".selecttailorType2").each(function(i,o){
				      				var id=	$(o).parent().data("needlesize");
									$(o).val(id)
								})
								$(".selecttailorType2").change(function(i,o){
				      				var that=$(this);
				      				var id=$(this).parent().parent().find(".needlesize").data('id');
				      				var productId=$(this).parent().parent().find(".needlesize").data('productid');
				      				var dataeee={
				      						id:id,
				      						productId:productId,
				      						needlesize:$(this).parent().parent().find(".selecttailorType2").val(),
				      				}
				      				var index;
				      				$.ajax({
									      url:"${ctx}/product/addMachinist",
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
														that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
										      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
										      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
										      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
										      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
										      			$("#ntwo1").val(o.oneMachinistPrice)	
													});
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
					    
					    
					    var datae = {
								type:"wiresize",
							}
							var index;
						    var html1 = '';
						    var htmlto1= '';
						    $.ajax({
							      url:"${ctx}/product/getBaseOne",
							      data:datae,
							      type:"GET",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				html1 +='<option value="'+o.id+'">'+o.name+'</option>'
					      			}); 
							       htmlto1='<select class="text-center form-control selecttailorType3" ><option value="">请选择</option>'+html1+'</select>'
					      		  $(".wiresize").html(htmlto1)
					      		  //改变事件
					      		  $(".selecttailorType3").each(function(i,o){
					      				var id=	$(o).parent().data("wiresize");
										$(o).val(id)
									}) 
									
									
									$(".selecttailorType3").change(function(i,o){
				      				var that=$(this);
				      				var id=$(this).parent().parent().find(".needlesize").data('id');
				      				var productId=$(this).parent().parent().find(".needlesize").data('productid');
				      				var dataeee={
				      						id:id,
				      						productId:productId,
				      						wiresize:$(this).parent().parent().find(".selecttailorType3").val(),
				      				}
				      				var index;
				      				$.ajax({
									      url:"${ctx}/product/addMachinist",
									      data:dataeee,
									      type:"POST",
									      beforeSend:function(){
												index = layer.load(1, {
													  shade: [0.1,'#fff'] //0.1透明度的白色背景
													});
											},
											success:function(result){
												$(result.data).each(function(i,o){
													that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
									      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
									      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
									      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
									      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
									      			$("#ntwo1").val(o.oneMachinistPrice)	
												});
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
										layer.close(index);sa
								  }
							  });
				  
						    var datae2 = {
									type:"needlespur",
								}
								var index;
							    var html2 = '';
							    var htmlto2= '';
							    $.ajax({
								      url:"${ctx}/product/getBaseOne",
								      data:datae2,
								      type:"GET",
						      		  success: function (result) {
						      			 $(result.data).each(function(i,o){
						      				html2 +='<option value="'+o.id+'" data-time="'+o.time+'">'+o.name+'</option>'
						      			}); 
								       htmlto2='<select class="text-center form-control selecttailorType4"><option value="">请选择</option>'+html2+'</select>'
						      		  $(".needlespur").html(htmlto2)
						      		  //改变事件
						      		  $(".selecttailorType4").each(function(i,o){
						      				var id=	$(o).parent().data("needlespur");
											$(o).val(id)
										}) 
										
										$(".selecttailorType4").change(function(i,o){
				      					var that=$(this);
				      					var id=$(this).parent().parent().find(".needlesize").data('id');
				      					var productId=$(this).parent().parent().find(".needlesize").data('productid');
				      					var time=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
				      					var ids=$(this).parent().parent().find(".selecttailorType6").val();
				      					var ids2=$(this).parent().parent().find(".selecttailorType7").val();
				      					var ids3=$(this).parent().parent().find(".selecttailorType8").val();
				      					
										var dataeer={
													type:time,
													id:ids,
										}
										var dataeer2={
												type:time,
												id:ids2,
										}
										var dataeer3={
												type:time,
												id:ids3,
										}
										$.ajax({
										      url:"${ctx}/product/getBaseFourDate",
										      data:dataeer3,
										      type:"GET",
										      beforeSend:function(){
													index = layer.load(1, {
														  shade: [0.1,'#fff'] //0.1透明度的白色背景
														});
												},
												success:function(result){
													if(0==result.code){
													var	baseFourDate=result.data
													var dataee={
								      						id:id,
								      						productId:productId,
								      						needlesize:that.parent().parent().find(".selecttailorType2").val(),
								      						wiresize:that.parent().parent().find(".selecttailorType3").val(),
								      						needlespur:that.parent().parent().find(".selecttailorType4").val(),
								      						backStitchCount:that.parent().parent().find(".backStitchCount").val(),
								      						time:that.parent().parent().find(".time").val(),
								      						baseFourDateThree:baseFourDate,
								      						bend:that.parent().parent().find(".selecttailorType8").val(),
								      						bendNumber:that.parent().parent().find(".bendNumber").val(),
								      						needlespur:that.parent().parent().find(".selecttailorType4").val(),
								      				}
														$.ajax({
														      url:"${ctx}/product/addMachinist",
														      data:dataee,
														      type:"POST",
														      beforeSend:function(){
																	index = layer.load(1, {
																		  shade: [0.1,'#fff'] //0.1透明度的白色背景
																		});
																},
																success:function(result){
																	if(0==result.code){
																		$(result.data).each(function(i,o){
																			that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
															      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
															      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
															      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
															      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
															      			$("#ntwo1").val(o.oneMachinistPrice)	
																		});
																	layer.close(index);
																	}else{
																		layer.msg("添加失败！", {icon: 2});
																		layer.close(index);
																	}
																},error:function(){
																	layer.msg("加载失败！", {icon: 2});
																	layer.close(index);
															  }
														  });
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
										$.ajax({
										      url:"${ctx}/product/getBaseFourDate",
										      data:dataeer2,
										      type:"GET",
										      beforeSend:function(){
													index = layer.load(1, {
														  shade: [0.1,'#fff'] //0.1透明度的白色背景
														});
												},
												success:function(result){
													if(0==result.code){
													var	baseFourDate=result.data
													var dataee={
								      						id:id,
								      						productId:productId,
								      						needlesize:that.parent().parent().find(".selecttailorType2").val(),
								      						wiresize:that.parent().parent().find(".selecttailorType3").val(),
								      						needlespur:that.parent().parent().find(".selecttailorType4").val(),
								      						backStitchCount:that.parent().parent().find(".backStitchCount").val(),
								      						time:that.parent().parent().find(".time").val(),
								      						baseFourDateTwo:baseFourDate,
								      						arc:that.parent().parent().find(".selecttailorType7").val(),
								      						arcNumber:that.parent().parent().find(".arcNumber").val(),
								      						needlespur:that.parent().parent().find(".selecttailorType4").val(),
								      				}
														$.ajax({
														      url:"${ctx}/product/addMachinist",
														      data:dataee,
														      type:"POST",
														      beforeSend:function(){
																	index = layer.load(1, {
																		  shade: [0.1,'#fff'] //0.1透明度的白色背景
																		});
																},
																success:function(result){
																	if(0==result.code){
																		$(result.data).each(function(i,o){
																			that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
															      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
															      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
															      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
															      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
															      			$("#ntwo1").val(o.oneMachinistPrice)	
																		});
																	layer.close(index);
																	}else{
																		layer.msg("添加失败！", {icon: 2});
																		layer.close(index);
																	}
																},error:function(){
																	layer.msg("加载失败！", {icon: 2});
																	layer.close(index);
															  }
														  });
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
										$.ajax({
										      url:"${ctx}/product/getBaseFourDate",
										      data:dataeer,
										      type:"GET",
										      beforeSend:function(){
													index = layer.load(1, {
														  shade: [0.1,'#fff'] //0.1透明度的白色背景
														});
												},
												success:function(result){
													if(0==result.code){
													var	baseFourDate=result.data
													var dataee={
								      						id:id,
								      						productId:productId,
								      						needlesize:that.parent().parent().find(".selecttailorType2").val(),
								      						wiresize:that.parent().parent().find(".selecttailorType3").val(),
								      						needlespur:that.parent().parent().find(".selecttailorType4").val(),
								      						backStitchCount:that.parent().parent().find(".backStitchCount").val(),
								      						time:that.parent().parent().find(".time").val(),
								      						baseFourDateOne:baseFourDate,
								      						beeline:that.parent().parent().find(".selecttailorType6").val(),
								      						beelineNumber:that.parent().parent().find(".beelineNumber").val(),
								      						needlespur:that.parent().parent().find(".selecttailorType4").val(),
								      				}
														$.ajax({
														      url:"${ctx}/product/addMachinist",
														      data:dataee,
														      type:"POST",
														      beforeSend:function(){
																	index = layer.load(1, {
																		  shade: [0.1,'#fff'] //0.1透明度的白色背景
																		});
																},
																success:function(result){
																	if(0==result.code){
																		$(result.data).each(function(i,o){
																			that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
															      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
															      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
															      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
															      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
															      			$("#ntwo1").val(o.oneMachinistPrice)	
																		});
																	layer.close(index);
																	}else{
																		layer.msg("添加失败！", {icon: 2});
																		layer.close(index);
																	}
																},error:function(){
																	layer.msg("加载失败！", {icon: 2});
																	layer.close(index);
															  }
														  });
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
											layer.close(index);sa
									  }
								  });
							    $(".time").blur(function(){
							    	var that=$(this);
			      					var id=$(this).parent().parent().find(".needlesize").data('id');
			      					var productId=$(this).parent().parent().find(".needlesize").data('productid');
			      					var time=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
			      					var dataeee={
			      						id:id,
			      						productId:productId,
			      						needlesize:$(this).parent().parent().find(".selecttailorType2").val(),
			      						wiresize:$(this).parent().parent().find(".selecttailorType3").val(),
			      						needlespur:$(this).parent().parent().find(".selecttailorType4").val(),
			      						backStitchCount:$(this).parent().parent().find(".backStitchCount").val(),
			      						time:$(this).parent().parent().find(".time").val(),
			      				}
			      				var index;
			      				$.ajax({
								      url:"${ctx}/product/addMachinist",
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
													that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
									      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
									      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
									      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
									      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
									      			$("#ntwo1").val(o.oneMachinistPrice)	
												});
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
							   
							    $(".backStitchCount").blur(function(){
							    	var that=$(this);
			      					var id=$(this).parent().parent().find(".needlesize").data('id');
			      					var productId=$(this).parent().parent().find(".needlesize").data('productid');
			      					var time=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
			      					var dataeee={
			      						id:id,
			      						productId:productId,
			      						needlesize:$(this).parent().parent().find(".selecttailorType2").val(),
			      						wiresize:$(this).parent().parent().find(".selecttailorType3").val(),
			      						needlespur:$(this).parent().parent().find(".selecttailorType4").val(),
			      						backStitchCount:$(this).parent().parent().find(".backStitchCount").val(),
			      						time:$(this).parent().parent().find(".time").val(),
			      				}
			      				var index;
			      				$.ajax({
								      url:"${ctx}/product/addMachinist",
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
													that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
									      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
									      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
									      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
									      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
									      			$("#ntwo1").val(o.oneMachinistPrice)	
												});
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
							    
							    $(".beelineNumber").blur(function(){
							    	var that=$(this);
			      					var id=$(this).parent().parent().find(".needlesize").data('id');
			      					var productId=$(this).parent().parent().find(".needlesize").data('productid');
			      					var time=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
			      					var dataeee={
			      						id:id,
			      						productId:productId,
			      						needlesize:$(this).parent().parent().find(".selecttailorType2").val(),
			      						wiresize:$(this).parent().parent().find(".selecttailorType3").val(),
			      						needlespur:$(this).parent().parent().find(".selecttailorType4").val(),
			      						backStitchCount:$(this).parent().parent().find(".backStitchCount").val(),
			      						time:$(this).parent().parent().find(".time").val(),
			      						beelineNumber:that.parent().parent().find(".beelineNumber").val(),
			      					}
			      				var index;
			      				$.ajax({
								      url:"${ctx}/product/addMachinist",
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
													that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
									      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
									      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
									      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
									      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
									      			$("#ntwo1").val(o.oneMachinistPrice)	 
												});
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
							    
							    $(".arcNumber").blur(function(){
							    	var that=$(this);
			      					var id=$(this).parent().parent().find(".needlesize").data('id');
			      					var productId=$(this).parent().parent().find(".needlesize").data('productid');
			      					var time=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
			      					var dataeee={
			      						id:id,
			      						productId:productId,
			      						needlesize:$(this).parent().parent().find(".selecttailorType2").val(),
			      						wiresize:$(this).parent().parent().find(".selecttailorType3").val(),
			      						needlespur:$(this).parent().parent().find(".selecttailorType4").val(),
			      						backStitchCount:$(this).parent().parent().find(".backStitchCount").val(),
			      						time:$(this).parent().parent().find(".time").val(),
			      						arcNumber:that.parent().parent().find(".arcNumber").val(),
			      					}
			      				var index;
			      				$.ajax({
								      url:"${ctx}/product/addMachinist",
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
													that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
									      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
									      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
									      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
									      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
									      			$("#ntwo1").val(o.oneMachinistPrice)	
												});
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
							    
							    $(".bendNumber").blur(function(){
							    	var that=$(this);
			      					var id=$(this).parent().parent().find(".needlesize").data('id');
			      					var productId=$(this).parent().parent().find(".needlesize").data('productid');
			      					var time=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
			      					var dataeee={
			      						id:id,
			      						productId:productId,
			      						needlesize:$(this).parent().parent().find(".selecttailorType2").val(),
			      						wiresize:$(this).parent().parent().find(".selecttailorType3").val(),
			      						needlespur:$(this).parent().parent().find(".selecttailorType4").val(),
			      						backStitchCount:$(this).parent().parent().find(".backStitchCount").val(),
			      						time:$(this).parent().parent().find(".time").val(),
			      						bendNumber:that.parent().parent().find(".bendNumber").val(),
			      					}
			      				var index;
			      				$.ajax({
								      url:"${ctx}/product/addMachinist",
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
													that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
									      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
									      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
									      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
									      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
									      			$("#ntwo1").val(o.oneMachinistPrice)	
												});
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
							    var datae2 = {
									
								}
								var index;
							    var html5 = '';
							    var htmlto5= '';
							    $.ajax({
								      url:"${ctx}/product/getBaseFour",
								      data:datae2,
								      type:"GET",
						      		  success: function (result) {
						      			 $(result.data).each(function(i,o){
						      				html5 +='<option value="'+o.id+'">'+o.sewingOrder+'</option>'
						      			}); 
								       htmlto5='<select class="text-center form-control selecttailorType6"><option value="">请选择</option>'+html5+'</select>'
						      		  $(".beeline").html(htmlto5)
						      		  //改变事件
						      		  $(".selecttailorType6").each(function(i,o){
						      				var id=	$(o).parent().data("beeline");
											$(o).val(id)
										}) 
										$(".selecttailorType6").change(function(i,o){
													var type=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
													var that=$(this);
							      					var id=$(this).parent().parent().find(".needlesize").data('id');
							      					var productId=$(this).parent().parent().find(".needlesize").data('productid');
							      					var time=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
							      					
													if(type==""){
														layer.msg("请选择针距", {icon: 2});
													}
													var ids=$(this).parent().parent().find(".selecttailorType6").val();
											var dataeer={
														type:type,
														id:ids,
											}
											$.ajax({
											      url:"${ctx}/product/getBaseFourDate",
											      data:dataeer,
											      type:"GET",
											      beforeSend:function(){
														index = layer.load(1, {
															  shade: [0.1,'#fff'] //0.1透明度的白色背景
															});
													},
													success:function(result){
														if(0==result.code){
														var	baseFourDate=result.data
														var dataee={
									      						id:id,
									      						productId:productId,
									      						needlesize:that.parent().parent().find(".selecttailorType2").val(),
									      						wiresize:that.parent().parent().find(".selecttailorType3").val(),
									      						needlespur:that.parent().parent().find(".selecttailorType4").val(),
									      						backStitchCount:that.parent().parent().find(".backStitchCount").val(),
									      						time:that.parent().parent().find(".time").val(),
									      						baseFourDateOne:baseFourDate,
									      						beeline:that.parent().parent().find(".selecttailorType6").val(),
									      						beelineNumber:that.parent().parent().find(".beelineNumber").val(),
									      				}
															$.ajax({
															      url:"${ctx}/product/addMachinist",
															      data:dataee,
															      type:"POST",
															      beforeSend:function(){
																		index = layer.load(1, {
																			  shade: [0.1,'#fff'] //0.1透明度的白色背景
																			});
																	},
																	success:function(result){
																		if(0==result.code){
																			$(result.data).each(function(i,o){
																				that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
																      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
																      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
																      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
																      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
																      			$("#ntwo1").val(o.oneMachinistPrice)	
																			});
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
											layer.close(index);sa
									  }
								  });
							  
							    
							    var datae3 = {
										
								}
								var index;
							    var html6 = '';
							    var htmlto6= '';
							    $.ajax({
								      url:"${ctx}/product/getBaseFour",
								      data:datae3,
								      type:"GET",
						      		  success: function (result) {
						      			 $(result.data).each(function(i,o){
						      				html6 +='<option value="'+o.id+'">'+o.sewingOrder+'</option>'
						      			}); 
								       htmlto6='<select class="text-center form-control selecttailorType7"><option value="">请选择</option>'+html6+'</select>'
						      		  $(".arc").html(htmlto6)
						      		  //改变事件
						      		  $(".selecttailorType7").each(function(i,o){
						      				var id=	$(o).parent().data("arc");
											$(o).val(id)
										}) 
										$(".selecttailorType7").change(function(i,o){
													var type=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
													var that=$(this);
							      					var id=$(this).parent().parent().find(".needlesize").data('id');
							      					var productId=$(this).parent().parent().find(".needlesize").data('productid');
							      					var time=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
							      					
													if(type==""){
														layer.msg("请选择针距", {icon: 2});
													}
													var ids=$(this).parent().parent().find(".selecttailorType7").val();
											var dataeer={
														type:type,
														id:ids,
											}
											$.ajax({
											      url:"${ctx}/product/getBaseFourDate",
											      data:dataeer,
											      type:"GET",
											      beforeSend:function(){
														index = layer.load(1, {
															  shade: [0.1,'#fff'] //0.1透明度的白色背景
															});
													},
													success:function(result){
														if(0==result.code){
														var	baseFourDate=result.data
														var dataee={
									      						id:id,
									      						productId:productId,
									      						needlesize:that.parent().parent().find(".selecttailorType2").val(),
									      						wiresize:that.parent().parent().find(".selecttailorType3").val(),
									      						needlespur:that.parent().parent().find(".selecttailorType4").val(),
									      						backStitchCount:that.parent().parent().find(".backStitchCount").val(),
									      						time:that.parent().parent().find(".time").val(),
									      						baseFourDateTwo:baseFourDate,
									      						arc:that.parent().parent().find(".selecttailorType7").val(),
									      						arcNumber:that.parent().parent().find(".arcNumber").val(),
									      				}
															$.ajax({
															      url:"${ctx}/product/addMachinist",
															      data:dataee,
															      type:"POST",
															      beforeSend:function(){
																		index = layer.load(1, {
																			  shade: [0.1,'#fff'] //0.1透明度的白色背景
																			});
																	},
																	success:function(result){
																		if(0==result.code){
																			$(result.data).each(function(i,o){
																				that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
																      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
																      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
																      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
																      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
																      			$("#ntwo1").val(o.oneMachinistPrice)	
																			});
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
											layer.close(index);sa
									  }
								  });
							    
								var datae4 = {
										
								}
								var index;
							    var html7 = '';
							    var htmlto7= '';
							    $.ajax({
								      url:"${ctx}/product/getBaseFour",
								      data:datae4,
								      type:"GET",
						      		  success: function (result) {
						      			 $(result.data).each(function(i,o){
						      				html7 +='<option value="'+o.id+'">'+o.sewingOrder+'</option>'
						      			}); 
								       htmlto7='<select class="text-center form-control selecttailorType8"><option value="">请选择</option>'+html7+'</select>'
						      		  $(".bend").html(htmlto7)
						      		  //改变事件
						      		  $(".selecttailorType8").each(function(i,o){
						      				var id=	$(o).parent().data("bend");
											$(o).val(id)
										}) 
										$(".selecttailorType8").change(function(i,o){
													var type=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
													var that=$(this);
							      					var id=$(this).parent().parent().find(".needlesize").data('id');
							      					var productId=$(this).parent().parent().find(".needlesize").data('productid');
							      					var time=$(this).parent().parent().find(".selecttailorType4 option:selected").data('time');
							      					
													if(type==""){
														layer.msg("请选择针距", {icon: 2});
													}
													var ids=$(this).parent().parent().find(".selecttailorType8").val();
											var dataeer={
														type:type,
														id:ids,
											}
											$.ajax({
											      url:"${ctx}/product/getBaseFourDate",
											      data:dataeer,
											      type:"GET",
											      beforeSend:function(){
														index = layer.load(1, {
															  shade: [0.1,'#fff'] //0.1透明度的白色背景
															});
													},
													success:function(result){
														if(0==result.code){
														var	baseFourDate=result.data
														var dataee={
									      						id:id,
									      						productId:productId,
									      						needlesize:that.parent().parent().find(".selecttailorType2").val(),
									      						wiresize:that.parent().parent().find(".selecttailorType3").val(),
									      						needlespur:that.parent().parent().find(".selecttailorType4").val(),
									      						backStitchCount:that.parent().parent().find(".backStitchCount").val(),
									      						time:that.parent().parent().find(".time").val(),
									      						baseFourDateThree:baseFourDate,
									      						bend:that.parent().parent().find(".selecttailorType8").val(),
									      						bendNumber:that.parent().parent().find(".bendNumber").val(),
									      				}
															$.ajax({
															      url:"${ctx}/product/addMachinist",
															      data:dataee,
															      type:"POST",
															      beforeSend:function(){
																		index = layer.load(1, {
																			  shade: [0.1,'#fff'] //0.1透明度的白色背景
																			});
																	},
																	success:function(result){
																		if(0==result.code){
																			$(result.data).each(function(i,o){
																				that.parent().parent().find(".oneSewingTime").text(parseFloat((o.oneSewingTime).toFixed(5)));
																      			 that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
																      			 that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));  
																      			that.parent().parent().find(".reckoningSewingPrice").text(parseFloat((o.reckoningSewingPrice).toFixed(5)));  
																      			that.parent().parent().find(".trialSewingPrice").text(parseFloat((o.trialSewingPrice).toFixed(5)));
																      			$("#ntwo1").val(o.oneMachinistPrice)
																			});
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
											layer.close(index);sa
									  }
								  });
							  /* //提示产品名
								$(".beeline").typeahead({
									//ajax 拿way数据
									source : function(query, process) {
											return $.ajax({
												url : '${ctx}/product/getBaseFour',
												type : 'GET',
												data : {
													sewingOrder:query,
												},
												
												success : function(result) {
													//转换成 json集合
													 var resultList = result.data.map(function (item) {
														 	//转换成 json对象
									                        var aItem = {name: item.sewingOrder, id:item.id}
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
											return item.name
											//按条件匹配输出
						                }, matcher: function (item) {
						                	//转出成json对象
									        var item = JSON.parse(item);
									    	return item.name
									    },
										//item是选中的数据
										updater:function(item){
											//转出成json对象
											var item = JSON.parse(item);
												return item.name
										},
										
									});
							  
								$(".arc").typeahead({
									//ajax 拿way数据
									source : function(query, process) {
											return $.ajax({
												url : '${ctx}/product/getBaseFour',
												type : 'GET',
												data : {
													sewingOrder:query,
												},
												
												success : function(result) {
													//转换成 json集合
													 var resultList = result.data.map(function (item) {
														 	//转换成 json对象
									                        var aItem = {name: item.sewingOrder, id:item.id}
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
											return item.name
											//按条件匹配输出
						                }, matcher: function (item) {
						                	//转出成json对象
									        var item = JSON.parse(item);
									    	return item.name
									    },
										//item是选中的数据
										updater:function(item){
											//转出成json对象
											var item = JSON.parse(item);
												return item.name
										},
										
									});
								
								$(".bend").typeahead({
									//ajax 拿way数据
									source : function(query, process) {
											return $.ajax({
												url : '${ctx}/product/getBaseFour',
												type : 'GET',
												data : {
													sewingOrder:query,
												},
												
												success : function(result) {
													//转换成 json集合
													 var resultList = result.data.map(function (item) {
														 	//转换成 json对象
									                        var aItem = {name: item.sewingOrder, id:item.id}
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
											return item.name
											//按条件匹配输出
						                }, matcher: function (item) {
						                	//转出成json对象
									        var item = JSON.parse(item);
									    	return item.name
									    },
										//item是选中的数据
										updater:function(item){
											//转出成json对象
											var item = JSON.parse(item);
												return item.name
										},
									});
							     */
								
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
					$(".machinistName").blur(function(){
						var ttat=$(this)
						if($(this).val()==""){
						return	layer.msg("机缝名不能为空", {icon: 2});
						}
						ttat.parent().parent().find('.selectbody2').text("")
			    		ttat.parent().parent().find('.selectprice2').text("")
						var data={
								id:ttat.parent().parent().find('.selectid').text(),
								productId: self.getCache(),
								number:$('#number').val(),
								machinistName:$(this).val()
						}
						$.ajax({
							url:"${ctx}/product/addMachinist",
							data:data,
							traditional: true,
							type:"POST",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								if(0==result.code){
									layer.close(index);
									var id=result.data.id
									ttat.parent().parent().find('.selectid').text(id);
									var data = {
											id:self.getCache(),//需要传产品id
										}
										var indexx;
									    var html = '';
									    $.ajax({
										      url:"${ctx}/product/getMachinistName",
										      data:data,
										      type:"GET",
										     
								      		  success: function (result) {
								      			 $(result.data).each(function(i,o){
								      				html +='<option value="'+o.price+'">'+o.name+'</option>'
								      			}); 
										       var htmlto='<select class="selectmac" style="border: none;width:50px; height:30px; background-color: #BFBFBF;"><option value=""></option>'+html+'</select>'
										       ttat.parent().parent().find(".selectCompany").html(htmlto); 
										      	  
										       ttat.parent().parent().find(".selectmac").change(function(){
										    		var thta=$(this)   
										    	   	thta.parent().parent().find('.selectbodytw').html(thta.find("option:selected").text())
										    	   	thta.parent().parent().find('.selectbody2').append(thta.find("option:selected").text()+',')
										    	   	thta.parent().parent().find('.selectprice2').append(thta.val()+',')
										    	    thta.find("option:selected").hide()
										    	   
										    	   	var values=""
										    	   	var name=""
										    		name=thta.parent().parent().find('.selectbody2').text()
										    		name = name.substr(0,name.length-1);
										    		values=thta.parent().parent().find('.selectprice2').text()
										    		values = values.substr(0,values.length-1);
										    		var postData={
										    			id:id,
										    			productId:self.getCache(),
										    			cutparts:name,
										    			cutpartsPrice:values,
										    			needlesize:172,
										    			wiresize:194,
										    			needlespur:204,
										    			beeline:28,
										    			arc:29,
										    			bend:30,
										    		}
										    	   $.ajax({
														url:"${ctx}/product/addMachinist",
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
															layer.close(index);
															}else{
																layer.msg("添加失败！", {icon: 2});
																layer.close(index);
															}
														},error:function(){
															layer.msg("操作失败！", {icon: 2});
															layer.close(index);
														}
													});
										       })
										       layer.close(indexx);
										      },error:function(){
													layer.msg("加载失败！", {icon: 2});
													layer.close(index);
											  }
										  });
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
					
					
					$(".selectbodytw").on('click',function(){
						var id=$(this).parent().find('.selectid').text();
						var postData={
								id:id,
								productId:productIdAll,
						}
						$.ajax({
							url:"${ctx}/product/getMachinist",
							data:postData,
							traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
							var name=result.data.rows[0].cutparts
							var valey=result.data.rows[0].cutpartsPrice
							if(name==null){
								return ""
							} 
							var cutparts = name.split(",");
							var cutpartsPrice= valey.split(",");
							var html=""
							for (var i = 0; i < cutparts.length; i++) {
								var array_element = cutparts[i];
								var array_element2 = cutpartsPrice[i];
								html+='<tr><td class="text-center edit name1">'+array_element+'</td>'
									+'<td class="text-center edit price1">'+array_element2+'</td>'
									+'<td><button class="btn btn-sm btn-danger btn-trans delete" data-id='+i+'>删除</button></td></tr>'
							}
							if(cutparts==""){
								html=""
							}
							$("#tableworking2").html(html)
								layer.close(index);
							 $(".delete").on('click',function(){
								$(this).parent().parent().find(".name1").text("");
								$(this).parent().parent().find(".price1").text("");
								$(this).parent().parent().find(".price1").hide()
								$(this).parent().parent().find(".name1").hide()
								 var cutparts=new Array();
								  var cutpartsPrice=new Array();
								  $('.name1').each(function(i,o){
										 var a=$(this).text()
										 var c=$(this).parent().find('.price1').text();
										if(a!=""){
											cutparts.push(a)
										}
										if(c!=""){
											cutpartsPrice.push(c)
										}
									 })
									 if(cutparts==""){
										 cutparts=""
									 }
								  if(cutpartsPrice==""){
									  cutpartsPrice=""
								  }
									 var postData={
						    			id:id,
						    			productId:self.getCache(),
						    			cutparts:cutparts,
						    			cutpartsPrice:cutpartsPrice,
						    		}
						    	   $.ajax({
										url:"${ctx}/product/addMachinist",
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
												layer.msg("删除成功！", {icon: 1});
											layer.close(index);
											}else{
												layer.msg("添加失败！", {icon: 2});
												layer.close(index);
											}
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									});
								
							}) 
							
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						var dicDiv=$('#addworking');
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
								 
									 
								},
							  end:function(){
								  $('#addworking').hide();
								  $('.name1').text("")
								  $('.price1').text("")
								  layer.close(index);
							  }
						});
							
					})
					
				}
			  
			  
			  
			  this.mater=function(){
					$(".machinistName").blur(function(){
						var ttat=$(this)
						if($(this).val()==""){
						return	layer.msg("机缝名不能为空", {icon: 2});
						}
						var data={
								id:ttat.parent().parent().find('.selectid').text(),
								productId: self.getCache(),
								number:$('#number').val(),
								machinistName:$(this).val()
						}
						$.ajax({
							url:"${ctx}/product/addMachinist",
							data:data,
							traditional: true,
							type:"POST",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								if(0==result.code){
									layer.close(index);
									var id=result.data.id
									ttat.parent().parent().find('.selectid').text(id);
									var data = {
											id:self.getCache(),//需要传产品id
										}
										var indexx;
									    var html = '';
									    $.ajax({
										      url:"${ctx}/product/getMachinistName",
										      data:data,
										      type:"GET",
										     
								      		  success: function (result) {
								      			 $(result.data).each(function(i,o){
								      				html +='<option value="'+o.price+'">'+o.name+'</option>'
								      			}); 
										       var htmlto='<select class="selectmac" style="border: none;width:50px; height:30px; background-color: #BFBFBF;"><option value=""></option>'+html+'</select>'
										       ttat.parent().parent().find(".selectCompany").html(htmlto); 
										      	  
										       ttat.parent().parent().find(".selectmac").change(function(){
										    		var thta=$(this)   
										    	   	thta.parent().parent().find('.selectbody').html(thta.find("option:selected").text())
										    	   	thta.parent().parent().find('.selectbody2').append(thta.find("option:selected").text()+',')
										    	   	thta.parent().parent().find('.selectprice2').append(thta.val()+',')
										    	    thta.find("option:selected").hide()
										    	    
										    	   	var values=""
										    	   	var name=""
										    		name=thta.parent().parent().find('.selectbody2').text()
										    		name = name.substr(0,name.length-1);
										    		values=thta.parent().parent().find('.selectprice2').text()
										    		values = values.substr(0,values.length-1);
										    		var postData={
										    			id:id,
										    			productId:self.getCache(),
										    			cutparts:name,
										    			cutpartsPrice:values,
										    			needlesize:172,
										    			wiresize:194,
										    			needlespur:204,
										    			beeline:28,
										    			arc:29,
										    			bend:30,
										    		}
										    	   $.ajax({
														url:"${ctx}/product/addMachinist",
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
															layer.close(index);
															}else{
																layer.msg("添加失败！", {icon: 2});
																layer.close(index);
															}
														},error:function(){
															layer.msg("操作失败！", {icon: 2});
															layer.close(index);
														}
													});
										       })
										       layer.close(indexx);
										      },error:function(){
													layer.msg("加载失败！", {icon: 2});
													layer.close(index);
											  }
										  });
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
					
					
					$(".selectbody").on('click',function(){
						var id=$(this).parent().find('.selectid').text();
						var postData={
								id:id,
								productId:productIdAll,
						}
						$.ajax({
							url:"${ctx}/product/getMachinist",
							data:postData,
							traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
							var name=result.data.rows[0].cutparts
							var valey=result.data.rows[0].cutpartsPrice
							if(name==null){
								return ""
							}
							var cutparts = name.split(",");
							var cutpartsPrice= valey.split(",");
							var html=""
							for (var i = 0; i < cutparts.length; i++) {
								var array_element = cutparts[i];
								var array_element2 = cutpartsPrice[i];
								html+='<tr><td class="text-center edit name1">'+array_element+'</td>'
									+'<td class="text-center edit price1">'+array_element2+'</td>'
									+'<td><button class="btn btn-sm btn-danger btn-trans delete" data-id='+i+'>删除</button></td></tr>'
							}
							$("#tableworking2").html(html)
								layer.close(index);
							 $(".delete").on('click',function(){
								$(this).parent().parent().find(".name1").text("");
								$(this).parent().parent().find(".price1").text("");
								 var cutparts=new Array();
								  var cutpartsPrice=new Array();
								  $('.name1').each(function(i,o){
										 var a=$(this).text()
										 var c=$(this).parent().find('.price1').text();
										if(a!=""){
											cutparts.push(a)
										}
										if(c!=""){
											cutpartsPrice.push(c)
										}
									 })
									 if(cutparts==""){
										 cutparts=""
									 }
								  if(cutpartsPrice==""){
									  cutpartsPrice=""
								  }
									 var postData={
						    			id:id,
						    			productId:self.getCache(),
						    			cutparts:cutparts,
						    			cutpartsPrice:cutpartsPrice,
						    		}
						    	   $.ajax({
										url:"${ctx}/product/addMachinist",
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
												layer.msg("删除成功！", {icon: 1});
											layer.close(index);
											}else{
												layer.msg("添加失败！", {icon: 2});
												layer.close(index);
											}
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									});
								
							}) 
							
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						var dicDiv=$('#addworking');
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
								 
									 
								},
							  end:function(){
								  $('#addworking').hide();
								  $('.name1').text("")
								  $('.price1').text("")
								  layer.close(index);
							  }
						});
							
					})
					
				}
			
			this.events = function(){
				
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
				
				
				$(".home1").on('click',function(){
					var data={
							page:1,
					  		size:100,	
					  		productId:productIdAll,
					  		/* sort:{"keys":"id","sortType":"ASC"}, */
				 } 
					$.ajax({
					      url:"${ctx}/product/getMachinist",
					      data:data,
					      type:"GET",
					      async:false, 
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			if(result.data.rows!=null && result.data.rows!=""){
			      			$("#ntwo").val(result.data.rows[0].oneMachinistPrice)
			      			}
			      			 $(result.data.rows).each(function(i,o){
			      				 var a;
			      				 if(o.costPrice==0){
			      					 a=o.trialSewingPrice
			      				 }else{
			      					 a=o.costPrice
			      				 }
			      				var	datae={
						    			id:o.id,
						    			costPrice:a,
						    			productId:productIdAll
						    	}
			      				
				      			var index;
						    	$.ajax({
								      url:"${ctx}/product/addMachinist",
								      data:datae,
								      type:"POST",
								      beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
						      		  success: function (result) {
						      			if(0==result.code){
						      				var data={
													page:1,
											  		size:100,	
											  		productId:productIdAll,
											}
											self.loadPagination(data);
										layer.close(index);
										}else{
											layer.msg("添加失败！", {icon: 2});
											layer.close(index);
										}
						      			
								      },error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(index);
									  }
								  });
			      			}); 
					      },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);
						  }
					  });
					
					
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
							url:"${ctx}/product/deleteMachinist",
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
					for (var i = 0; i < 10; i++) {
					var a=$('#loss').val();
					 html='<tr><td  class="text-center"></td><td  class="text-center edit name"  style="padding: 9px 0px 2px 4px;"><input type="text" style="border: none;width:120px; height:30px; background-color: #BFBFBF;" data-provide="typeahead" autocomplete="off" class="text-center  machinistName" /></td>'
					 +'<td class="text-center edit selectid hidden"></td>'
					 +'<td class="text-center edit materialstw"></td>'
					 +'<td class="text-center edit selectCompany" style="padding: 2px 0px 2px 0px;"></td>'
					 +'<td class="text-center edit selectbody"></td>'
					 +'<td class="text-center edit selectbody2 hidden"></td>'
					 +'<td class="text-center edit selectprice2 hidden"></td>'
					 +'<td class="text-center edit " ></td>'
					 +'<td class="text-center edit " ></td>'
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
					}
					self.mater();
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