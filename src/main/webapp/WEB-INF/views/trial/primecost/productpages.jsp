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
<title>产品总汇</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<style type="text/css">
.selectotb {
  	border: 0;
	display: block;
	position: relative;
	white-space: nowrap;
	width: 100%;
	overflow: hidden;
	background-color: #eee;
  	background: transparent;
  	appearance:none;
	-moz-appearance:none; /* Firefox */
	-webkit-appearance:none; /* Safari 和 Chrome */
}


</style>
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
							<h3 class="panel-title">产品信息</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i> <i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="row" style="height: 30px; margin: 15px 0 10px">
							<div class="col-xs-10 col-sm-10  col-md-10">
								<form class="form-search">
									<div class="row">
										<div class="col-xs-10 col-sm-10 col-md-10">
											<div class="input-group">
												<table>
													<tr>
														<td>产品名:</td><td><input type="text" name="name" id="productName2" placeholder="请输入产品名称" class="form-control search-query name" data-provide="typeahead" autocomplete="off"/ ></td>
														<td>&nbsp&nbsp&nbsp&nbsp</td>
														<td>产品编号:</td>
														<td><input type="text" name="number" id="number"
															class="form-control search-query number" /></td>
													<td>默认数量:</td><td><input type="text" name="number" id="number2" placeholder="请输入默认数量" class="form-control search-query number" /></td>
													</tr>
												</table>
												<span class="input-group-btn">
													<button type="button"
														class="btn btn-info btn-square btn-sm btn-3d searchtask">
														查&nbsp找</button>
												</span>
												<td>&nbsp&nbsp&nbsp&nbsp</td> <span class="input-group-btn">
													<button type="button" id="addproduct"
														class="btn btn-success  btn-sm btn-3d pull-right">
														新增产品</button>
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
										<th class="text-center">产品序号</th>
										<th class="text-center">产品编号</th>
										<th class="text-center">产品名</th>
										<th class="text-center">是否含开票</th>
										<th class="text-center">综合税负加所得税负</th>
										<th class="text-center">剩余到手的</th>
										<th class="text-center">预算成本</th>
										<th class="text-center">预算成本加价率</th>
										<th class="text-center">实战成本</th>
										<th class="text-center">实战成本加价率</th>
										<th class="text-center">操作</th>
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
	<!--隐藏框 产品新增开始  -->
	<div id="addDictDivType" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<!-- PAGE CONTENT BEGINS -->
			<form class="form-horizontal addDictDivTypeForm">
				<div class="row col-xs-12  col-sm-12  col-md-12 ">
					<div style="height: 30px"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label">产品编号:</label>
						<div class="col-sm-6">
							<input type="text" id="productNumber" class="form-control">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">产品名:</label>
						<div class="col-sm-6">
							<input type="text" id="productName" class="form-control">
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!--隐藏框 产品新增结束  -->
	
	
	
	<!--隐藏框 成本价格表开始  -->
	
	<div id="addworking" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
					<div class="row col-xs-12  col-sm-12  col-md-12 ">
						<div style="height: 40px"></div>
						<div class="form-group">
                           <label class="col-sm-2 col-md-2 control-label" >当批产量:</label>
                              <div class="col-sm-2 col-md-2">
                                  <input type="text" class="form-control primecostnumber" style="background-color:#0d92f59c">
                              </div>
                               <label class="col-sm-2 control-label">批成本:</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control bacthPrimeCost"  disabled="disabled">
                                      </div>
                            <label class="col-sm-2 control-label">单只成本:</label>
                              <div class="col-sm-2 ">
                              <input type="text" class="form-control onePrimeCost"  disabled="disabled">
                              <input type="text" class="form-control onePrimeCost hidden" id="paramNum">
                              </div>
                    	</div>
                    	
                    	
                    	
           		<div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped ">
                                        <thead>
                                            <tr>
		                                        <th class="text-center">是否可开票</th>
												<th class="text-center">需要支付开票点</th>
												<th class="text-center">产品的大类分解</th>
												<th class="text-center">各环节的批价格</th>
												<th class="text-center">单只价格</th>
												<th class="text-center">当下生产放价填写</th>
												<th class="text-center">与实战差价</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td class="" style="background-color:#0d92f59c"> 
                                                <select class="selectotb cutPartsInvoice" >
                                                <option value="0">否</option>
                                                <option value="1">是</option>
                                                </select>
                                                </td>
                                                <td class="cutPartsPriceInvoice" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td id="doua"><a style="color:#F00">面料价格(含复合物料和加工费）</a></td>
                                                <td class="cutPartsPrice" ></td>
                                                <td class="oneCutPartsPrice" ></td>
                                                <td class="cutPartsPricePricing" rowspan="2" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td class="cutPartsD" rowspan="2" ></td>
                                            </tr>
                                            <tr>
                                                <td class="" style="background-color:#0d92f59c">
                                                 <select class="selectotb otherCutPartsInvoice" >
	                                                <option value="0">否</option>
	                                                <option value="1">是</option>
	                                             </select>
                                                </td>
                                                <td class="otherCutPartsPriceInvoice" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td  id="doua1"><a style="color:#F00">除面料以外的其他物料价格</a> </td>
                                                <td class="otherCutPartsPrice" ></td>
                                                <td class="oneOtherCutPartsPrice" ></td>
                                            </tr>
                                            <tr>
                                                <td class="" style="background-color:#0d92f59c">
                                                <select class="selectotb cutInvoice"  >
	                                                <option value="0">否</option>
	                                                <option value="1">是</option>
	                                             </select>
                                                </td>
                                                <td class="cutPriceInvoice" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td  id="doua2"><a style="color:#F00">裁剪价格</a></td>
                                                <td class="cutPrice" ></td>
                                                <td class="oneCutPrice" ></td>
                                                <td class="cutPricePricing" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td class="cutPriceD" ></td>
                                            </tr>
                                            <tr>
                                                <td class="" style="background-color:#0d92f59c">
                                                 <select class="selectotb machinistInvoice" >
	                                                <option value="0">否</option>
	                                                <option value="1">是</option>
	                                             </select>
                                                </td>
                                                <td class="machinistPriceInvoice" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td id="doua3" ><a style="color:#F00">机工价格</a></td>
                                                <td class="machinistPrice" ></td>
                                                <td class="oneMachinistPrice" ></td>
                                                <td class="machinistPricePricing" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td class="machinistPriceD" ></td>
                                            </tr>
                                            <tr>
                                                <td class="" style="background-color:#0d92f59c">
                                                <select class="selectotb embroiderInvoice" >
	                                                <option value="0">否</option>
	                                                <option value="1">是</option>
	                                             </select>
                                                </td>
                                                <td class="embroiderPriceInvoice" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td  id="doua4"><a style="color:#F00">绣花价格</a></td>
                                                <td class="embroiderPrice" ></td>
                                                <td class="oneEmbroiderPrice" ></td>
                                                <td class="embroiderPricePricing" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td class="embroiderPriceD" ></td>
                                            </tr>
                                            <tr>
                                                <td class="" style="background-color:#0d92f59c">
                                                 <select class="selectotb needleworkInvoice" >
	                                                <option value="0">否</option>
	                                                <option value="1">是</option>
	                                             </select>
                                                </td>
                                                <td class="needleworkPriceInvoice" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td  id="doua5"><a style="color:#F00">针工价格</a></td>
                                                <td class="needleworkPrice" ></td>
                                                <td class="oneNeedleworkPrice" ></td>
                                                <td class="needleworkPricePricing" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td class="needleworkPriceD" ></td>
                                            </tr>
                                            <tr>
                                                <td class="" style="background-color:#0d92f59c">
                                                <select class="selectotb packInvoice" >
	                                                <option value="0">否</option>
	                                                <option value="1">是</option>
	                                             </select>
                                                </td>
                                                <td class="packPriceInvoice" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td id="doua6"><a style="color:#F00">内外包装和出入库的价格</a></td>
                                                <td class="packPrice" ></td>
                                                <td class="onePackPrice" ></td>
                                                <td class="packPricePricing" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td class="packPriceD" ></td>
                                            </tr>
                                            <tr>
                                                <td class="" style="background-color:#0d92f59c">
                                                <select class="selectotb freightInvoice" >
	                                                <option value="0">否</option>
	                                                <option value="1">是</option>
	                                             </select>
                                                </td>
                                                <td class="freightPriceInvoice" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td >预计运费价格</td>
                                                <td class="freightPrice"  ></td>
                                                <td class="oneFreightPrice"  ></td>
                                                <td class="freightPricePricing" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td class="freightPriceD"  ></td>
                                            </tr>
                                        </tbody>
                                        
                                        
                                        
                                         <thead>
                                            <tr>
		                                        <th class="text-center">是否有返点</th>
												<th class="text-center">返点比</th>
												<th class="text-center">调整外发产量</th>
												<th class="text-center">批成本</th>
												<th class="text-center">单只成本</th>
												<th class="text-center">单只实战成本</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td class=""  style="background-color:#0d92f59c">
                                                  <select class="selectotb rebate" >
	                                                <option value="0">否</option>
	                                                <option value="1">是</option>
	                                             </select>
                                                
                                                </td>
                                                <td class="rebateRate" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td class="adjustNumber" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td class="bacthPrimeCostAdjustNumber" ></td>
                                                <td class="onePrimeCost"></td>
                                                <td class="oneaAtualPrimeCost" ></td>
                                            </tr>
                                           
                                        </tbody>
                                        
                                       
                                       <thead>
                                            <tr>
		                                        <th class="text-center">是否有版权点</th>
												<th class="text-center">版权点比</th>
												<th class="text-center">产品的大类分解</th>
												<th class="text-center">各环节的批价格</th>
												<th class="text-center">单只价格</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td class="" style="background-color:#0d92f59c">
                                                <select class="selectotb copyright" >
	                                                <option value="0">否</option>
	                                                <option value="1">是</option>
	                                             </select>
                                                </td>
                                                <td class="copyrightRate" contentEditable="true" style="background-color:#0d92f59c"></td>
                                                <td >面料价格(含复合物料和加工费）</td>
                                                <td class="oneCutPartsPriceBacth"></td>
                                                <td class="oneCutPartsPrice" ></td>
                                            </tr>
                                            <tr>
                                                <td ></td>
                                                <td ></td>
                                                <td  >除面料以外的其他物料价格 </td>
                                                <td class="oneOtherCutPartsPriceBacth" ></td>
                                                <td class="oneOtherCutPartsPrice" ></td>
                                            </tr>
                                            <tr>
                                                <td ></td>
                                                <td ></td>
                                                <td  >裁剪价格</td>
                                                <td class="oneCutPriceBacth" ></td>
                                                <td class="oneCutPrice" ></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td ></td>
                                                <td  >机工价格</td>
                                                <td class="oneMachinistPriceBacth" ></td>
                                                <td class="oneMachinistPrice" ></td>
                                            </tr>
                                            <tr>
                                                <td ></td>
                                                <td ></td>
                                                <td  >绣花价格</td>
                                                <td class="oneEmbroiderPriceBacth" ></td>
                                                <td class="oneEmbroiderPrice" ></td>
                                            </tr>
                                            <tr>
                                                <td ></td>
                                                <td ></td>
                                                <td  >针工价格</td>
                                                <td class="oneNeedleworkPriceBacth" ></td>
                                                <td class="oneNeedleworkPrice" ></td>
                                            </tr>
                                            <tr>
                                                <td ></td>
                                                <td ></td>
                                                <td >内外包装和出入库的价格</td>
                                                <td class="onePackPriceBacth" ></td>
                                                <td class="onePackPrice" ></td>
                                            </tr>
                                            <tr>
                                                <td ></td>
                                                <td ></td>
                                                <td  >预计运费价格</td>
                                                <td class="oneFreightPriceBacth" ></td>
                                                <td class="oneFreightPrice" ></td>
                                            </tr>
                                        </tbody>
                                         
                                    </table>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>


				<div class="form-group1">
					<label class="col-sm-2 col-md-2 control-label">目前售价:</label>
					<div class="col-sm-2 col-md-2">
						<input type="text" class="form-control sale"  style="background-color:#0d92f59c">
					</div>
					<label class="col-sm-2 control-label">是否含开票:</label>
					<div class="col-sm-2" >
						  	<select class="form-control invoice" style="background-color:#0d92f59c" >
	                            <option value="0">否</option>
	                            <option value="1">是</option>
	                         </select>
					</div>
					<label class="col-sm-2 control-label">付上游开票点:</label>
					<div class="col-sm-2 ">
						<input type="text" class="form-control upstream" disabled="disabled">
					</div>
				</div>

				<div class="form-group2">
					<label class="col-sm-2 col-md-2 control-label">预计多付国家的:</label>
					<div class="col-sm-2 col-md-2">
						<input type="text" class="form-control expectState" disabled="disabled">
					</div>
					<label class="col-sm-2 control-label">付国家的:</label>
					<div class="col-sm-2">
						<input type="text" class="form-control state" disabled="disabled">
					</div>
					<label class="col-sm-2 control-label">考虑多付国家的不付需要的进项票点:</label>
					<div class="col-sm-2 ">
						<input type="text" class="form-control noState" disabled="disabled">
					</div>
				</div>

				<div class="form-group3">
					<label class="col-sm-2 col-md-2 control-label">付返点和版权点:</label>
					<div class="col-sm-2 col-md-2">
						<input type="text" class="form-control recidivate" disabled="disabled">
					</div>
					<label class="col-sm-2 control-label">付运费:</label>
					<div class="col-sm-2">
						<input type="text" class="form-control freight" disabled="disabled">
					</div>
					<label class="col-sm-2 control-label">为目前得出综合税负加所得税负填写:</label>
					<div class="col-sm-2 ">
						<input type="text" class="form-control taxes" style="background-color:#0d92f59c">
					</div>
				</div>

				<div class="form-group4">
					<label class="col-sm-2 col-md-2 control-label">剩余到手的:</label>
					<div class="col-sm-2 col-md-2">
						<input type="text" class="form-control surplus" disabled="disabled">
					</div>
					<label class="col-sm-2 control-label">实际加价率:</label>
					<div class="col-sm-2">
						<input type="text" class="form-control makeRate" disabled="disabled">
					</div>
					<label class="col-sm-2 control-label">目前综合税负加所得税负比:</label>
					<div class="col-sm-2 ">
						<input type="text" class="form-control taxesRate" disabled="disabled">
					</div>
				</div>






			</div>
		</div>     
        
</div>        
	
	<!--隐藏框 成本价格表结束  -->
	
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
	<script src="${ctx }/static/js/vendor/typeahead.js"></script>
	<script>
		jQuery(function($) {
			var Login = function() {
				var self = this;
				//表单jsonArray
				//初始化js
				var _cache;

				this.setCache = function(cache) {
					_cache = cache;
				}
				this.getCache = function() {
					return _cache;
				}

				this.setIndex = function(index) {
					_index = index;
				}
				this.getIndex = function() {
					return _index;
				}
			
				this.setNum = function(num) {
					_num = num;
				}
				this.getNum = function() {
					return _num;
				}
				
				var data = {
					page : 1,
					size : 13,
					type : 2,

				}

				this.init = function() {
					//注册绑定事件
					self.events();
					self.loadPagination(data);
				}

				//加载分页
				this.loadPagination = function(data) {
					var index;
					var html = '';
					$
							.ajax({
								url : "${ctx}/getProductPages",
								data : data,
								type : "GET",
								beforeSend : function() {
									index = layer.load(1, {
										shade : [ 0.1, '#fff' ]
									//0.1透明度的白色背景
									});
								},
								success : function(result) {
									$(result.data.rows)
											.each(
													function(i, o) {
														var primeCost = o.primeCost;
														var invoice = 0;
														if(primeCost!=null){
															invoice = o.primeCost;
														}
														html += '<tr>'
																+ '<td class="text-center id">'
																+ o.id
																+ '</td>'
																+ '<td class="text-center edit number">'
																+ o.number
																+ '</td>'
																+ '<td class="text-center edit name">'
																+ o.name
																+ '</td>'
																+ '<td class="text-center  ">'
																+ (invoice == 0 ? " 否" : "是")
																+ '</td>'
																+ '<td class="text-center  ">'
																+ (primeCost == null ? ""
																		: primeCost.taxIncidence)
																+ '</td>'
																+ '<td class="text-center  ">'
																+ (primeCost == null ? ""
																		: primeCost.surplus)
																+ '</td>'
																+ '<td class="text-center  ">'
																+ (primeCost == null ? ""
																		: primeCost.budget)
																+ '</td>'
																+ '<td class="text-center  ">'
																+ (primeCost == null ? ""
																		: primeCost.budgetRate)
																+ '</td>'
																+ '<td class="text-center  ">'
																+ (primeCost == null ? ""
																		: primeCost.actualCombat)
																+ '</td>'
																+ '<td class="text-center  ">'
																+ (primeCost == null ? ""
																		: primeCost.actualCombatRate)
																+ '</td>'
																+ '<td class="text-center"><button class="btn btn-xs btn-info  btn-trans update" data-id='+o.id+'>编辑</button><button class="btn btn-xs btn-primary btn-trans addPrimeCost" data-id='+o.id+'>查看成本价格</button> <button class="btn btn-xs btn-info  btn-trans updatecopy" data-id='+o.id+'>复制的产品</button> </td></tr>'

													});
									self.setIndex(result.data.pageNum);
									//显示分页
									laypage({
										cont : 'pager',
										pages : result.data.totalPages,
										curr : result.data.pageNum || 1,
										jump : function(obj, first) {
											if (!first) {

												var _data = {
													page : obj.curr,
													size : 13,
													type : 2,
												
												}

												self.loadPagination(_data);
											}
										}
									});
									layer.close(index);
									$("#tablecontent").html(html);
									self.loadEvents();

								},
								error : function() {
									layer.msg("加载失败！", {
										icon : 2
									});
									layer.close(index);
								}
							});
				}

				this.loadEvents = function() {

					//触发工序弹框 加载内容方法
					$('.addPrimeCost').on('click', function() {
						var _index;
						var productId = $(this).data('id');
						$("#doua").on('click',function(){
						/* var a=$(".third").attr("name")
						var b="trial/primecost/cutparts"
							var p= b;
						if(p!="#"){
							location.href = "${ctx}/menusToUrl?url="+p;
						 $.cookie("navstation", $(this).html());//添加cookie 
						 $.cookie("navstationtwo",null);
						} */
						location.href = "${ctx}/product/menusToUrl?url=trial/primecost/cutparts&paramNum="+productId+"";
					})
					$("#doua1").on('click',function(){
						location.href = "${ctx}/product/menusToUrl?url=trial/primecost/nocutparts&paramNum="+productId+"";
					})
					$("#doua2").on('click',function(){
						location.href = "${ctx}/product/menusToUrl?url=trial/primecost/tailor&paramNum="+productId+"";
					})
					$("#doua3").on('click',function(){
						location.href = "${ctx}/product/menusToUrl?url=trial/primecost/machinist&paramNum="+productId+"";
					})
					$("#doua4").on('click',function(){
						location.href = "${ctx}/product/menusToUrl?url=trial/primecost/embroidery&paramNum="+productId+"";
					})
					$("#doua5").on('click',function(){
						location.href = "${ctx}/product/menusToUrl?url=trial/primecost/needlework&paramNum="+productId+"";
					})
					$("#doua6").on('click',function(){
						location.href = "${ctx}/product/menusToUrl?url=trial/primecost/pack&paramNum="+productId+"";
					})
						var dicDiv = $('#addworking');
						var postData;
						//打开隐藏框
						_index = layer.open({
							type : 1,
							skin : 'layui-layer-rim', //加上边框
							area : [ '85%', '85%' ],
							btnAlign : 'c',//宽高
							maxmin : true,
							title : name,
							content : dicDiv,
							btn: ['确定', '取消'],
							yes : function(index, layero) {
								 postData={
										  productId:productId,
										  number:$('.primecostnumber').val(),
										  cutPartsPriceInvoice:$('.cutPartsPriceInvoice').text(),
										  otherCutPartsPriceInvoice:$('.otherCutPartsPriceInvoice').text(),
										  cutPriceInvoice:$('.cutPriceInvoice').text(),
										  machinistPriceInvoice:$('.machinistPriceInvoice').text(),
										  embroiderPriceInvoice:$('.embroiderPriceInvoice').text(),
										  needleworkPriceInvoice:$('.needleworkPriceInvoice').text(),
										  packPriceInvoice:$('.packPriceInvoice').text(),
										  freightPriceInvoice:$('.freightPriceInvoice').text(),
										  cutPartsInvoice:$('.cutPartsInvoice').val(),
										  otherCutPartsInvoice:$('.otherCutPartsInvoice').val(),
										  cutInvoice:$('.cutInvoice').val(),
										  machinistInvoice:$('.machinistInvoice').val(),
										  embroiderInvoice:$('.embroiderInvoice').val(),
										  needleworkInvoice:$('.needleworkInvoice').val(),
										  packInvoice:$('.packInvoice').val(),
										  freightInvoice:$('.freightInvoice').val(),
										  invoice:$('.invoice').val(),
										  rebate:$('.rebate').val(),
										  rebateRate:$('.rebateRate').text(),
										  copyright:$('.copyright').val(),
										  copyrightRate:$('.copyrightRate').text(),
										  adjustNumber:$('.adjustNumber').text(),
										  //当下生产放价填写
										  cutPartsPricePricing:$('.cutPartsPricePricing').text(),
										  cutPricePricing:$('.cutPricePricing').text(),
										  machinistPricePricing:$('.machinistPricePricing').text(),
										  embroiderPricePricing:$('.embroiderPricePricing').text(),
										  needleworkPricePricing:$('.needleworkPricePricing').text(),
										  packPricePricing:$('.packPricePricing').text(),
										  freightPricePricing:$('.freightPricePricing').text()
										  
											
										  
								  }
								 
								   $.ajax({
										url:"${ctx}/getPrimeCost",
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
												layer.close(index);
												self.loadPagination(data);
											}else{
												layer.msg(result.message, {icon: 2});
												$("#productId").text("");
											}
											layer.close(index);
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									}); 
								},

							end : function() {
								$('#addworking').hide();
								data = {
									page : self.getIndex(),
									size : 13,
									type : 2,
								}
								self.loadPagination(data);
							}
						});
						self.setCache(productId);
						self.loadworking();
					})

					
					
					//修改方法
					$('.update').on(
							'click',
							function() {
								if ($(this).text() == "编辑") {
									$(this).text("保存")

									$(this).parent().siblings(".edit").each(
											function() { // 获取当前行的其他单元格
												$(this).html(
														"<input class='input-mini' type='text' value='"
																+ $(this).text()
																+ "'>");
											});
								} else {
									$(this).text("编辑")
									$(this).parent().siblings(".edit").each(
											function() { // 获取当前行的其他单元格
												obj_text = $(this).find("input:text"); // 判断单元格下是否有文本框
												$(this).html(obj_text.val());

											});
									var postData = {
										id : $(this).data('id'),
										number : $(this).parent().parent('tr')
												.find(".number").text(),
										name : $(this).parent().parent('tr')
												.find(".name").text(),
									}

									var index;
									$.ajax({
										url : "${ctx}/updateProduct",
										data : postData,
										type : "POST",
										beforeSend : function() {
											index = layer.load(1, {
												shade : [ 0.1, '#fff' ]
											//0.1透明度的白色背景
											});
										},

										success : function(result) {
											if (0 == result.code) {
												layer.msg("修改成功！", {
													icon : 1
												});
												layer.close(index);
											} else {
												layer.msg("修改失败！", {
													icon : 1
												});
												layer.close(index);
											}
										},
										error : function() {
											layer.msg("操作失败！", {
												icon : 2
											});
											layer.close(index);
										}
									});

								}
							})

				}

				//弹框内容加载
				this.loadworking = function() {
					var productId = self.getCache()
					var index
					var html = '';
					var data = {
						productId : productId,
					}
					$
							.ajax({
								url : "${ctx}/getPrimeCost",
								data : data,
								type : "POST",
								beforeSend : function() {
									index = layer.load(1, {
										shade : [ 0.1, '#fff' ]
									//0.1透明度的白色背景
									});
								},
								success : function(result) {
									var primeCost = result.data;
									
									$(".primecostnumber").val(
											primeCost.number);
									
									$(".bacthPrimeCost").val(
											primeCost.bacthPrimeCost);
									
									$(".onePrimeCost").val(
											primeCost.onePrimeCost);
									
									
									$(".cutPartsPrice").text(
											primeCost.cutPartsPrice);
									$(".oneCutPartsPrice").text(
											primeCost.oneCutPartsPrice);
									$(".otherCutPartsPrice").text(
											primeCost.otherCutPartsPrice);
									$(".oneOtherCutPartsPrice").text(
											primeCost.oneOtherCutPartsPrice);
									$(".cutPrice").text(
											primeCost.cutPrice);
									$(".oneCutPrice").text(
											primeCost.oneCutPrice);
									$(".machinistPrice").text(
											primeCost.machinistPrice);
									$(".oneMachinistPrice").text(
											primeCost.oneMachinistPrice);
									$(".embroiderPrice").text(
											primeCost.embroiderPrice);
									$(".oneEmbroiderPrice").text(
											primeCost.oneEmbroiderPrice);
									$(".needleworkPrice").text(
											primeCost.needleworkPrice);
									$(".oneNeedleworkPrice").text(
											primeCost.oneNeedleworkPrice);
									$(".packPrice").text(
											primeCost.packPrice);
									$(".onePackPrice").text(
											primeCost.onePackPrice);
									$(".freightPrice").text(
											primeCost.freightPrice);
									$(".oneFreightPrice").text(
											primeCost.oneFreightPrice);
									
									
								    //调整后的批成本
								    $(".bacthPrimeCostAdjustNumber").text(
								    		(primeCost.bacthPrimeCost/primeCost.number)*primeCost.adjustNumber);
								    
									$(".oneCutPartsPriceBacth").text(
											primeCost.oneCutPartsPrice*primeCost.adjustNumber);
									$(".oneOtherCutPartsPriceBacth").text(
											primeCost.oneOtherCutPartsPrice*primeCost.adjustNumber);
									$(".oneCutPriceBacth").text(
											primeCost.oneCutPrice*primeCost.adjustNumber);
									$(".oneMachinistPriceBacth").text(
											primeCost.oneMachinistPrice*primeCost.adjustNumber);
									$(".oneEmbroiderPriceBacth").text(
											primeCost.oneEmbroiderPrice*primeCost.adjustNumber);
									$(".oneNeedleworkPriceBacth").text(
											primeCost.oneNeedleworkPrice*primeCost.adjustNumber);
									$(".onePackPriceBacth").text(
											primeCost.onePackPrice*primeCost.adjustNumber);
									$(".oneFreightPriceBacth").text(
											primeCost.oneFreightPrice*primeCost.adjustNumber);
									
								    
								    
									//当下生产放价填写
									$(".cutPartsPricePricing").text(
											primeCost.cutPartsPricePricing);
									$(".cutPricePricing").text(
											primeCost.cutPricePricing);
									$(".machinistPricePricing").text(
											primeCost.machinistPricePricing);
									$(".embroiderPricePricing").text(
											primeCost.embroiderPricePricing);
									$(".needleworkPricePricing").text(
											primeCost.needleworkPricePricing);
									$(".packPricePricing").text(
											primeCost.packPricePricing);
									$(".freightPricePricing").text(
											primeCost.freightPricePricing);
									
									//与实战差价
									$(".cutPartsD").text(
											(primeCost.oneCutPartsPrice + primeCost.oneOtherCutPartsPrice)-primeCost.cutPartsPricePricing);
									$(".cutPriceD").text(
											(primeCost.oneCutPrice-primeCost.cutPricePricing));
									$(".machinistPriceD").text(
											(primeCost.oneMachinistPrice-primeCost.machinistPricePricing));
									$(".embroiderPriceD").text(
											(primeCost.oneEmbroiderPrice-primeCost.embroiderPricePricing));
									$(".needleworkPriceD").text(
											(primeCost.oneNeedleworkPrice-primeCost.needleworkPricePricing));
									$(".packPriceD").text(
											(primeCost.onePackPrice-primeCost.packPricePricing));
									$(".freightPriceD").text(
											(primeCost.oneFreightPrice-primeCost.freightPricePricing));
									
									
									//需要支付开票点
									$(".cutPartsPriceInvoice").text(
											primeCost.cutPartsPriceInvoice);
									$(".otherCutPartsPriceInvoice").text(
											primeCost.otherCutPartsPriceInvoice);
									$(".cutPriceInvoice").text(
											primeCost.cutPriceInvoice);
									$(".machinistPriceInvoice").text(
											primeCost.machinistPriceInvoice);
									$(".embroiderPriceInvoice").text(
											primeCost.embroiderPriceInvoice);
									$(".needleworkPriceInvoice").text(
											primeCost.needleworkPriceInvoice);
									$(".packPriceInvoice").text(
											primeCost.packPriceInvoice);
									$(".freightPriceInvoice").text(
											primeCost.freightPriceInvoice);
									//是否可开票
									$('.cutPartsInvoice').each(function(i, o) {
										$(o).val(primeCost.cutPartsInvoice);
									});
									$('.otherCutPartsInvoice').each(function(i, o) {
										$(o).val(primeCost.otherCutPartsInvoice);
									});
									$('.cutInvoice').each(function(i, o) {
										$(o).val(primeCost.cutInvoice);
									});
									$('.machinistInvoice').each(function(i, o) {
										$(o).val(primeCost.machinistInvoice);
									});
									$('.embroiderInvoice').each(function(i, o) {
										$(o).val(primeCost.embroiderInvoice);
									});
									$('.needleworkInvoice').each(function(i, o) {
										$(o).val(primeCost.needleworkInvoice);
									});
									$('.packInvoice').each(function(i, o) {
										$(o).val(primeCost.packInvoice);
									});
									$('.freightInvoice').each(function(i, o) {
										$(o).val(primeCost.freightInvoice);
									});
									$('.rebate').each(function(i, o) {
										$(o).val(primeCost.rebate);
									});
									$(".rebateRate").text(primeCost.rebateRate);
									$(".adjustNumber").text(
											primeCost.adjustNumber);
									$(".bacthPrimeCost").text(
											primeCost.bacthPrimeCost);
									$(".onePrimeCost").text(
											primeCost.onePrimeCost);
									$(".oneaAtualPrimeCost").text(
											primeCost.oneaAtualPrimeCost);

									$('.copyright').each(function(i, o) {
										$(o).val(primeCost.copyright);
									});
									$(".copyrightRate").text(
											primeCost.copyrightRate);
									
									
								
									$(".sale").val(
											primeCost.sale);
									
									$('.invoice').each(function(i, o) {
										$(o).val(primeCost.invoice);
									});
									
									$(".upstream").val(
											primeCost.upstream);
									
									$(".expectState").val(
											primeCost.expectState);
									
									$(".state").val(
											primeCost.state);
									
									$(".noState").val(
											primeCost.noState);
									
									$(".recidivate").val(
											primeCost.recidivate);
									
									$(".freight").val(
											primeCost.freight);
									
									$(".taxes").val(
											primeCost.taxes);
									
									$(".surplus").val(
											primeCost.surplus);
									
									$(".makeRate").val(
											primeCost.makeRate);
									
									$(".taxesRate").val(
											primeCost.taxesRate);
									

									layer.close(index);
								}
							});

				}

				this.events = function() {
					//查询
					$('.searchtask').on('click', function() {
						var data = {
							page: 1,
							size: 13,
							name: $('#productName2').val(),
							number:$('#number').val(),
							id:self.getNum(),
							primeCostNumber:$('#number2').val(),
						}
						self.loadPagination(data);
					});

					//提示产品名
					$("#productName2").typeahead({
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
						        self.setNum(item.id)
						        $('#number2').val(item.number)
						    	return item.name
						    },
							//item是选中的数据
							updater:function(item){
								//转出成json对象
								var item = JSON.parse(item);
								self.setNum(item.id)
								 $('#number2').val(item.number)
									return item.name
							},
							
						});
					//新增产品
					$('#addproduct')
						.on(
							'click',
							function() {
								var _index
								var index
								var postData
								var dicDiv = $('#addDictDivType');
								_index = layer
									.open({
										type: 1,
										skin: 'layui-layer-rim', //加上边框
										area: ['30%', '30%'],
										btnAlign: 'c', //宽高
										maxmin: true,
										title: "新增产品",
										content: dicDiv,
										btn: ['确定', '取消'],
										yes: function(index,
											layero) {

											postData = {
												number: $("#productNumber").val(),
												name: $("#productName").val(),
											}
											$.ajax({
													url: "${ctx}/addProduct",
													data: postData,
													traditional: true,
													type: "post",
													beforeSend: function() {
														index = layer.load(1, 
																{shade: [0.1,'#fff']//0.1透明度的白色背景
																	});
															},
													success: function(result) {
														if(0 == result.code) {
															layer.msg("添加成功！", {icon: 1});
															$(".addDictDivTypeForm")[0].reset();
															self.loadPagination(data);
															$('#addDictDivType').hide();
														} else {
															layer.msg("添加失败", {icon: 2});
														}
														layer.close(index);
													},
													error: function() {
														layer.msg("操作失败！", {icon: 2});
														layer.close(index);
													}
												});
										},
										end: function() {
											$('.addDictDivTypeForm')[0].reset();
											$('#addDictDivType').hide();

										}
									});
							})
				}
			}
			var login = new Login();
			login.init();
		})
	</script>

</body>

</html>