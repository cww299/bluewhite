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
    <title>内外包装和杂工</title>
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
                                        <li class="active col-md-2" style="width: 50%"><a href="#home1" class="home1" data-toggle="tab">内外包装和杂工</a>
                                        </li>
                                        <li class="col-md-2"style="width: 50%;"><a href="#profile1" class="profile1"  data-toggle="tab">内外包装和杂工时间设定</a>
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
                                        <div style="width:50%;float:left;text-align:center;vertical-align:top">
                                            <table class="table table-hover" >
                                    		<thead>
                                        	<tr>
                                       		 	<th class="text-center">
												<label> 
												<input type="checkbox" class="ace checks" /> 
												<span class="lbl"></span>
												</label>
												</th>
                                           		<th class="text-center">请选择一个设定完毕的内外包装sss（有档位）工序</th>
                                        		<th class="text-center">请选择在该工序下的分类</th>
                                            	<th class="text-center">自动跳出设定秒数</th>
                                            	<th class="text-center">手填该包装可装单品数量/只↓</th>
                                            	<th class="text-center">单只产品用时/秒</th>
                                       		 </tr>
                                    		</thead>
                                    		<tbody id="tablecontent">
                                        
                                    		</tbody>
                                		</table>
                                		</div >
                                		<div style="width:50%;float:left;text-align:center;vertical-align:top">
												<table class="table table-hover" >
                                    		<thead>
                                        	<tr>
                                       		 	<th class="text-center">
												<label> 
												<input type="checkbox" class="ace checks2" /> 
												<span class="lbl"></span>
												</label>
												</th>
                                           		<th class="text-center">请选择一个设定完毕的内外包装      sss（无档位）工序</th>
                                        		<th class="text-center">自动得到工序的用时/秒</th>
                                            	<th class="text-center">手填该包装可装单品数量/只↓</th>
                                            	<th class="text-center">单只产品用时/秒</th>
                                       		 </tr>
                                    		</thead>
                                    		<tbody id="tablecontentt">
                                        
                                    		</tbody>
                                		</table>
										</div>
                                        </div>
            <div class="tab-pane" id="profile1">
                                   <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	
                                            <th class="text-center">内外包装工序</th>
                                            <th class="text-center">工种定性↓</th>
                                            <th class="text-center">工种定性↓</th>
                                            <th class="text-center">单只用时/秒</th>
                                            <th class="text-center">批量用时/秒(含快手）</th>
                                            <th class="text-center">单只时间（含快手）</th>
                                            <th class="text-center">该工序有可能用到的物料</th>
                                             <th class="text-center">设备折旧和房水电费</th>
                                            <th class="text-center">管理人员费用</th>
                                            <th class="text-center">入成本批量价格</th>
                                            <th class="text-center">上道压价（整个成品）</th>
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
				    var html2 = '';
				    $.ajax({
					      url:"${ctx}/product/getPack",
					      data:data,
					      type:"GET",
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			 $(result.data.rows).each(function(i,o){
			      				if(o.gear==2){
			      				html+='<tr><td class="text-center reste"><label> <input type="checkbox" class="ace checkboxId" data-productid='+o.productId+' value="'+o.id+'"/><span class="lbl"></span></label></td>'
			      				 +'<td  class="text-center edit name packName2" data-packname='+o.packName+' ></td>'
			      				+'<td class="text-center edit selectid3 hidden"  >'+o.productId+'</td>'
			      				 +'<td class="text-center edit selectid9 hidden">'+o.id+'</td>'
			   					 +'<td class="text-center edit classify2">'+o.type+'</td>'
			   					 +'<td class="text-center edit times2">'+o.time+'</td>'
			   					+'<td class="text-center edit "><input type="text" value='+o.packNumber+' style="border: none;width:68px; height:30px; background-color: #BFBFBF;" class="text-center  packNumber2" /></td>'
			   					 +'<td class="text-center edit oneTime2">'+o.oneTime+'</td></tr>'
			      				}
			      			}); 
			      			$(result.data.rows).each(function(i,o){
			      				if(o.gear==1){
			      				html2+='<tr><td class="text-center reste"><label> <input type="checkbox" class="ace checkboxIdtw" data-productid='+o.productId+' value="'+o.id+'"/><span class="lbl"></span></label></td>'
			      				 +'<td  class="text-center edit name packName22" data-packname='+o.packName+' ></td>'
			      				+'<td class="text-center edit selectid33 hidden"  >'+o.productId+'</td>'
			      				 +'<td class="text-center edit selectid99 hidden">'+o.id+'</td>'
			   					 +'<td class="text-center edit times22">'+o.time+'</td>'
			   					+'<td class="text-center edit "><input type="text" value='+o.packNumber+' style="border: none;width:68px; height:30px; background-color: #BFBFBF;" class="text-center  packNumber22" /></td>'
			   					 +'<td class="text-center edit oneTime22">'+o.oneTime+'</td></tr>'
			      				}
			      			});
						   	layer.close(index);
						   	 $("#tablecontent").html(html);
						   	$("#tablecontentt").html(html2);
						   	 self.mater2()
						   	 self.checkedd()
						   	 self.checkeddd()
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
						      url:"${ctx}${ctx}/product/getPack",
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
				      				 +'<td  class="text-center edit " data-productid='+o.productId+'>'+o.packName+'</td>'
				      				+'<td class="text-center edit" data-kindwork='+o.kindWork+'><select class="text-center form-control kindWork"><option value="">请选择</option><option value="0">杂工</option><option value="1">力工</option></select></td>'
				   					 +'<td class="text-center edit selectid2 hidden"  >'+o.id+'</td>'
				   					 +'<td class="text-center edit selectid5 hidden"  >'+o.productId+'</td>'
				   					 +'<td class="text-center edit helpWork" data-helpwork='+o.exteriorInterior+'></td>'
				   					 +'<td class="text-center oneTime">'+parseFloat((o.oneTime).toFixed(5))+'</td>'
				   					+'<td class="text-center batchTime">'+parseFloat((o.batchTime).toFixed(5))+'</td>'
				   					+'<td class="text-center onePackTime">'+parseFloat((o.onePackTime).toFixed(5))+'</td>'
				   					+'<td class="text-center">'+o.materiel+'</td>'
				   					+'<td class="text-center packPrice">'+parseFloat((o.packPrice).toFixed(5))+'</td>'
				   					+'<td class="text-center equipmentPrice">'+parseFloat((o.equipmentPrice).toFixed(5))+'</td>'
				   					+'<td class="text-center administrativeAtaff">'+parseFloat((o.administrativeAtaff).toFixed(5))+'</td>'
				   					+'<td class="text-center allCostPrice">'+parseFloat((o.allCostPrice).toFixed(5))+'</td>'
				   					+'<td class="text-center priceDown">'+parseFloat((o.priceDown).toFixed(5))+'</td></tr>'
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
				  $(".kindWork").each(function(i,o){
						var id=	$(o).parent().data("kindwork");
						$(o).val(id)
					})
				  
				  $('.kindWork').change(function(){
					  var that=$(this)
				  
					  var date={
							  id:that.parent().parent().find(".selectid2").text(),
							  productId:that.parent().parent().find(".selectid5").text(),
							  kindWork:that.parent().parent().find(".kindWork").val(),
					  }
					  var index;
	      				$.ajax({
						      url:"${ctx}/product/addPack",
						      data:date,
						      type:"POST",
						      beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								success:function(result){
									if(0==result.code){
										$(result.data).each(function(i,o){
							      			 that.parent().parent().find(".oneTime").text(parseFloat((o.oneTime).toFixed(5)));
							      			 that.parent().parent().find(".batchTime").text(parseFloat((o.batchTime).toFixed(5)));  
							      			that.parent().parent().find(".onePackTime").text(parseFloat((o.onePackTime).toFixed(5)));  
							      			that.parent().parent().find(".packPrice").text(parseFloat((o.packPrice).toFixed(5)));
							      			that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
							      			that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));
							      			that.parent().parent().find(".allCostPrice").text(parseFloat((o.allCostPrice).toFixed(5)));
							      			that.parent().parent().find(".priceDown").text(parseFloat((o.priceDown).toFixed(5)));
							      			 })
									layer.close(index);
									}else{
										layer.msg(result.message, {icon: 2});
										layer.close(index);
									}
								},error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				  })
				  
				  
					 var data6 = {
							type:"overstock",
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
							html6 +='<option value="'+o.id+'">'+o.name+'</option>'
							}); 
							htmlto6='<select class="text-center form-control selecttailorType8" ><option value="">请选择</option>'+html6+'</select>'
							 $(".helpWork").html(htmlto6)
									      		 
									      		  //改变事件
							 $(".selecttailorType8").each(function(i,o){
								var id=	$(o).parent().data("helpwork");
								$(o).val(id)
							})
									$(".selecttailorType8").change(function(i,o){
									      				var that=$(this);
									      				var tailorId=$(this).parent().parent().find(".selecttailorType8").val();
									      				var name=$(this).parent().parent().find(".selecttailorType8 option:selected").text();
									      				var dataeee={
									      						 id:that.parent().parent().find(".selectid2").text(),
																 productId:that.parent().parent().find(".selectid5").text(),
																 exteriorInterior:tailorId,
									      				}
									      				var index;
									      				 $.ajax({
														      url:"${ctx}/product/addPack",
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
														      			 that.parent().parent().find(".oneTime").text(parseFloat((o.oneTime).toFixed(5)));
														      			 that.parent().parent().find(".batchTime").text(parseFloat((o.batchTime).toFixed(5)));  
														      			that.parent().parent().find(".onePackTime").text(parseFloat((o.onePackTime).toFixed(5)));  
														      			that.parent().parent().find(".packPrice").text(parseFloat((o.packPrice).toFixed(5)));
														      			that.parent().parent().find(".equipmentPrice").text(parseFloat((o.equipmentPrice).toFixed(5)));
														      			that.parent().parent().find(".administrativeAtaff").text(parseFloat((o.administrativeAtaff).toFixed(5)));
														      			that.parent().parent().find(".allCostPrice").text(parseFloat((o.allCostPrice).toFixed(5)));
														      			that.parent().parent().find(".priceDown").text(parseFloat((o.priceDown).toFixed(5)));
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
			  }
			  this.loadEvents=function(){
				 
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
			  
			  this.checkeddd=function(){
					
					$(".checks2").on('click',function(){
						
	                    if($(this).is(':checked')){ 
				 			$('.checkboxIdtw').each(function(){  
	                    //此处如果用attr，会出现第三次失效的情况  
	                     		$(this).prop("checked",true);
				 			})
	                    }else{
	                    	$('.checkboxIdtw').each(function(){ 
	                    		$(this).prop("checked",false);
	                    		
	                    	})
	                    }
	                }); 
					
				}
			  this.mater2=function(){
					
				  var data = {
							type:"endocyst",//传产品id
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
						       $(".packName2").html(htmlto)	
						       $(".selecttailorType2").each(function(i,o){
								var id=	$(o).parent().data("packname");
								$(o).find("option:selected").text(id)
							})
						      $(".selecttailorType2").change(function(i,o){
						    	  var thatt=$(this)
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
						      				html2 +='<option value="'+k.textualTime+'">'+k.categorySetting+'</option>'
						      			}); 
						      			 htmlto2='<select class="text-center form-control selecttailorTypee2" ><option value="">请选择</option>'+html2+'</select>'
						      			thatt.parent().parent().find(".classify2").html(htmlto2)
						      			 $(".selecttailorTypee2").change(function(i,o){
						      				$(this).parent().parent().find(".times2").text($(this).parent().parent().find(".selecttailorTypee2").val())
						      				var that=$(this)
						      				var dataeee={
					      						id:that.parent().parent().find('.selectid9').text(),
					      						packName:that.parent().parent().find('.selecttailorType2 option:selected').text(),
					      						type:that.parent().parent().find(".selecttailorTypee2 option:selected").text(),
					      						time:$(this).parent().parent().find(".selecttailorTypee2").val(),
					      						productId: that.parent().parent().find('.selectid3').text(),
					      						number:$('#number').val(),
					      						gear:2
					      				}
					      				var index;
					      				$.ajax({
										      url:"${ctx}/product/addPack",
										      data:dataeee,
										      type:"POST",
										      beforeSend:function(){
													index = layer.load(1, {
														  shade: [0.1,'#fff'] //0.1透明度的白色背景
														});
												},
												success:function(result){
													if(0==result.code){
														var onetime=result.data.oneTime
														that.parent().parent().find('.oneTime11').text(onetime);
													layer.close(index);
													}else{
														layer.msg(result.message, {icon: 2});
														layer.close(index);
													}
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
						      })
				      		 
				      		  },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
					    
					    var data = {
								type:"fixation",//传产品id
							}
							var index;
						    var htmll = '';
						    var htmltol= '';
						    var htmlto2l= '';
						    
						    $.ajax({
							      url:"${ctx}/product/getBaseOne",
							      data:data,
							      type:"GET",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				htmll +='<option value="'+o.textualTime+'">'+o.name+'</option>'
					      			}); 
							       htmltol='<select class="text-center form-control selecttailorType3" ><option value="">请选择</option>'+htmll+'</select>'
							       $(".packName22").html(htmltol)
							       $(".selecttailorType3").each(function(i,o){
								var id=	$(o).parent().data("packname");
								$(o).find("option:selected").text(id)
							})
							      $(".selecttailorType3").change(function(i,o){
							    		   $($(this).parent().parent().find(".times22")).text($(this).parent().parent().find(".selecttailorType3").val())
							       var html2l = '';
							    		   var that=$(this)
						      				var dataeee={
					      						id:that.parent().parent().find('.selectid99').text(),
					      						packName:that.parent().parent().find('.selecttailorType3 option:selected').text(),
					      						time:$(this).parent().parent().find(".selecttailorType3").val(),
					      						productId:that.parent().parent().find('.selectid33').text(),
					      						number:$('#number').val(),
					      						gear:1
					      				}
					      				var index;
					      				$.ajax({
										      url:"${ctx}/product/addPack",
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
													layer.close(index);
											  }
										  });
							      })
					      		 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    
						    $(".packNumber22").blur(function(){
						    	var that=$(this)
						    	if(that.parent().parent().find('.times22').text()==""){
						    		return layer.msg("请先填写工序", {icon: 2});
						    	}
			      				var dataeee={
		      						id:that.parent().parent().find('.selectid99').text(),
		      						packNumber:that.parent().parent().find(".packNumber22").val(),
		      						productId: that.parent().parent().find('.selectid33').text(),
		      						number:$('#number').val(),
		      						gear:1
		      				}
						    	var index;
			      				$.ajax({
								      url:"${ctx}/product/addPack",
								      data:dataeee,
								      type:"POST",
								      beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										success:function(result){
											if(0==result.code){
												var onetime=result.data.oneTime
												that.parent().parent().find('.oneTime22').text(onetime);
											layer.close(index);
											}else{
												layer.msg(result.message, {icon: 2});
												layer.close(index);
											}
										},error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(index);
									  }
								  });
						    })
						    
					    $(".packNumber2").blur(function(){
					    	var that=$(this)
					    	if(that.parent().parent().find('.times2').text()==""){
					    		return layer.msg("请先填写工序", {icon: 2});
					    	}
		      				var dataeee={
	      						id:that.parent().parent().find('.selectid9').text(),
	      						packNumber:that.parent().parent().find(".packNumber2").val(),
	      						productId: that.parent().parent().find('.selectid3').text(),
	      						number:$('#number').val(),
	      						gear:2
	      				}
					    	var index;
		      				$.ajax({
							      url:"${ctx}/product/addPack",
							      data:dataeee,
							      type:"POST",
							      beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										if(0==result.code){
											var onetime=result.data.oneTime
											that.parent().parent().find('.oneTime2').text(onetime);
										layer.close(index);
										}else{
											layer.msg(result.message, {icon: 2});
											layer.close(index);
										}
									},error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
				}
			  
			  
			  this.mater=function(){
					
				  var data = {
							type:"endocyst",//传产品id
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
						       $(".packName").html(htmlto)	
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
						      				html2 +='<option value="'+k.textualTime+'">'+k.categorySetting+'</option>'
						      			}); 
						      			 htmlto2='<select class="text-center form-control selecttailorTypee2" ><option value="">请选择</option>'+html2+'</select>'
						      			 $(".classify").html(htmlto2)
						      			 $(".selecttailorTypee2").change(function(i,o){
						      				$($(this).parent().parent().find(".times")).text($(this).parent().parent().find(".selecttailorTypee2").val())
						      				var that=$(this)
						      				var dataeee={
					      						id:that.parent().parent().find('.selectid').text(),
					      						packName:that.parent().parent().find('.selecttailorType2 option:selected').text(),
					      						type:that.parent().parent().find(".selecttailorTypee2 option:selected").text(),
					      						time:$(this).parent().parent().find(".selecttailorTypee2").val(),
					      						productId: self.getCache(),
					      						number:$('#number').val(),
					      						gear:2
					      				}
					      				var index;
					      				$.ajax({
										      url:"${ctx}/product/addPack",
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
													layer.close(index);
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
					    
					    var data = {
								type:"fixation",//传产品id
							}
							var index;
						    var htmll = '';
						    var htmltol= '';
						    var htmlto2l= '';
						    
						    $.ajax({
							      url:"${ctx}/product/getBaseOne",
							      data:data,
							      type:"GET",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				htmll +='<option value="'+o.textualTime+'">'+o.name+'</option>'
					      			}); 
							       htmltol='<select class="text-center form-control selecttailorType3" ><option value="">请选择</option>'+htmll+'</select>'
							       $(".packName11").html(htmltol)	
							      $(".selecttailorType3").change(function(i,o){
							    		   $($(this).parent().parent().find(".times11")).text($(this).parent().parent().find(".selecttailorType3").val())
							       var html2l = '';
							    		   var that=$(this)
						      				var dataeee={
					      						id:that.parent().parent().find('.selectid11').text(),
					      						packName:that.parent().parent().find('.selecttailorType3 option:selected').text(),
					      						time:$(this).parent().parent().find(".selecttailorType3").val(),
					      						productId: self.getCache(),
					      						number:$('#number').val(),
					      						gear:1
					      				}
					      				var index;
					      				$.ajax({
										      url:"${ctx}/product/addPack",
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
														that.parent().parent().find('.selectid11').text(id);
													layer.close(index);
													}else{
														layer.msg(result.message, {icon: 2});
														layer.close(index);
													}
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
					    
						    $(".packNumber11").blur(function(){
						    	var that=$(this)
						    	if(that.parent().parent().find('.times11').text()==""){
						    		return layer.msg("请先填写工序", {icon: 2});
						    	}
			      				var dataeee={
		      						id:that.parent().parent().find('.selectid11').text(),
		      						packNumber:that.parent().parent().find(".packNumber11").val(),
		      						productId: self.getCache(),
		      						number:$('#number').val(),
		      						gear:1
		      				}
						    	var index;
			      				$.ajax({
								      url:"${ctx}/product/addPack",
								      data:dataeee,
								      type:"POST",
								      beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										success:function(result){
											if(0==result.code){
												var onetime=result.data.oneTime
												that.parent().parent().find('.oneTime11').text(onetime);
											layer.close(index);
											}else{
												layer.msg(result.message, {icon: 2});
												layer.close(index);
											}
										},error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(index);
									  }
								  });
						    })
						    
					    $(".packNumber").blur(function(){
					    	var that=$(this)
					    	if(that.parent().parent().find('.times').text()==""){
					    		return layer.msg("请先填写工序", {icon: 2});
					    	}
		      				var dataeee={
	      						id:that.parent().parent().find('.selectid').text(),
	      						packNumber:that.parent().parent().find(".packNumber").val(),
	      						productId: self.getCache(),
	      						number:$('#number').val(),
	      						gear:2
	      				}
					    	var index;
		      				$.ajax({
							      url:"${ctx}/product/addPack",
							      data:dataeee,
							      type:"POST",
							      beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										if(0==result.code){
											var onetime=result.data.oneTime
											that.parent().parent().find('.oneTime').text(onetime);
										layer.close(index);
										}else{
											layer.msg(result.message, {icon: 2});
											layer.close(index);
										}
									},error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
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
					  $(this).parent().parent().parent().parent().parent().parent().parent().parent().find(".checkboxIdtw:checked").each(function() { 
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
							url:"${ctx}/product/deletePack",
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
				var htm2="";
				$('#addCutting').on('click',function(){
					 html='<tr><td  class="text-center"></td><td  class="text-center edit packName"></td>'
					 +'<td class="text-center edit selectid hidden"></td>'
					 +'<td class="text-center edit classify" ></td>'
					 +'<td class="text-center edit times" ></td>'
					 +'<td class="text-center edit " ><input type="text" style="border: none;width:68px; height:30px; background-color: #BFBFBF;" class="text-center  packNumber" /></td>'
					 +'<td class="text-center edit oneTime" ></td></tr>';
					$("#tablecontent").prepend(html);
					html2='<tr><td  class="text-center"></td><td  class="text-center edit packName11"></td>'
						 +'<td class="text-center edit selectid11 hidden"></td>'
						 +'<td class="text-center edit times11" ></td>'
						 +'<td class="text-center edit " ><input type="text" style="border: none;width:68px; height:30px; background-color: #BFBFBF;" class="text-center  packNumber11" /></td>'
						 +'<td class="text-center edit oneTime11" ></td></tr>';
						 $("#tablecontentt").prepend(html2);
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