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
                                <h3 class="panel-title">针工填写</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                             <div class="panel-body">
                                <div class="tab-wrapper tab-primary">
                                    <ul class="nav nav-tabs col-md-12">
                                        <li class="active col-md-2" style="width: 50%"><a href="#home1" class="home1" data-toggle="tab">针工页面</a>
                                        </li>
                                        <li class="col-md-2"style="width: 50%;"><a href="#profile1" class="profile1"  data-toggle="tab">针工时间设定</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="home1">
                                        <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
			<div class="col-xs-11 col-sm-11  col-md-11">
				<form class="form-search" >
					<div class="row">
						<div class="col-xs-11 col-sm-11 col-md-11">
							<div class="input-group"> 
								<table><tr>
								<td>产品名:</td><td><input type="text" name="name" id="productName" placeholder="请输入产品名称" class="form-control search-query name" data-provide="typeahead" autocomplete="off"/ ></td>
								<td>&nbsp&nbsp</td>
								<td>默认数量:</td><td><input type="text" name="number" id="number" placeholder="请输入默认数量" class="form-control search-query number" /></td>
									<td>&nbsp&nbsp</td>
								<td>默认耗损:</td><td><input type="text" name="name" id="loss" placeholder="请输入产品名称" class="form-control search-query name" /></td>
								<!-- <td>&nbsp&nbsp</td> -->
								<!-- <td>完成状态:</td><td><select class="form-control" id="selectstate"><option value=0>未完成</option><option value=1>已完成</option></select></td> -->
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask">
											查&nbsp找
									</button>
								</span>
								 <td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
									<button type="button" id="addCutting" class="btn btn-success  btn-sm btn-3d export">
									新增
									</button>
								</span> 
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
									<button type="button" class="btn btn-danger  btn-sm btn-3d start">
									一键删除
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
                                        
                                            <table class="table table-hover" >
                                    <thead>
                                        <tr>
                                        <th class="text-center">
											<label> 
											<input type="checkbox" class="ace checks" /> 
											<span class="lbl"></span>
											</label>
											</th>
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
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-10 col-sm-10  col-md-10">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr><td>批次:</td><td><input type="text" name="number" id="number" placeholder="请输入批次号" class="form-control search-query number" /></td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask">
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
			      			 $(result.data.rows).each(function(i,o){
			      				if(o.costPrice==o.reckoningEmbroideryPrice || o.costPrice==o.reckoningSewingPrice){
			      					 
			      				 }else{
			      					o.costPrice=""
			      				 }
			      				html+='<tr><td class="text-center reste"><label> <input type="checkbox" class="ace checkboxId" data-productid='+o.productId+' value="'+o.id+'"/><span class="lbl"></span></label></td>'
			      				 +'<td  class="text-center edit name needleworkName2" data-needlewor2='+o.needleworkName+' >'+o.needleworkName+'</td>'
			      				+'<td class="text-center edit selectid3 hidden"  >'+o.productId+'</td>'
			      				 +'<td class="text-center edit selectid hidden">'+o.id+'</td>'
			   					 +'<td class="text-center edit classify2">'+o.classify+'</td>'
			   					 +'<td class="text-center edit times2">'+o.seconds+'</td>'
			   					 +'<td class="text-center edit selectCompany">'+o.needleworkName+o.classify+'</td>'
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
				      			 $(result.data.rows).each(function(i,o){
				      				html+='<tr>'
				      				 +'<td  class="text-center edit name tailorName2" data-productid='+o.productId+'>'+o.needleworkName+o.classify+'</td>'
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
														ttat.parent().parent().find('.selectid').text(id);
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