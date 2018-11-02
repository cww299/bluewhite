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
    <title>除裁片用料</title>
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
                                <h3 class="panel-title">裁片填写页面</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
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
									<button type="button" id="save" class="btn btn-success  btn-sm btn-3d">
									保存
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
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="center">
											<label> 
											<input type="checkbox" class="ace checks" /> 
											<span class="lbl"></span>
											</label>
											</th>
                                        	<th class="text-center">定性选择</th>
                                            <th class="text-center">请选择物料名↓</th>
                                            <th class="text-center">填写单只用料</th>
                                            <th class="text-center">单位填写选择↓</th>
                                            <th class="text-center">按选定单位产品单价</th>
                                            <th class="text-center">手动损耗选择↓</th>
                                            <th class="text-center">自动得到产品单价</th>
                                            <th class="text-center">自动得到产品备注</th>
                                            <th class="text-center">当批当品种用量(手选单位）</th>
                                            <th class="text-center">当批当品种价格</th>
                                            <th class="text-center">压货环节</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent">
                                        
                                    </tbody>
                                </table>
                                <div id="pager" class="pull-right">
                                
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </section>
        <!--隐藏框 产品新增开始  -->
        <div id="addDictDivType" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeForm">
				<div class="form-group">
                                        <label class="col-sm-3 control-label">名称:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="groupName" class="form-control">
                                        </div>
                 </div>
				</form>
</div>
</div>
 <!--隐藏框 产品新增结束  -->



<div id="savegroup" style="display: none;">
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					人员分组详情
				</h4>
			</div>
			<div class="modal-body">
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</div>
<!--隐藏框 产品新增结束  -->
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
				  		size:13,	
				  		productId:productIdAll,
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
				      url:"${ctx}/product/getProductMaterials",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				 
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				/* +'<td  style="padding: 2px 0px 2px 4px;"><input type="text" style="border: none;width:68px; height:30px; background-color: #BFBFBF;" data-provide="typeahead" autocomplete="off" class="text-center  cuttingName" value="'+o.cutPartsName+'" /></td>' */
		      				+'<td class="text-center edit " >'+o.materialsName+'</td>'
		      				+'<td class="text-center editt materialsNamett" >'+o.materialsName+'</td>'
		      				+'<td class="text-center materielNumbertw" >'+o.oneMaterial+'</td>'
		      				+'<td class="text-center unite name" >'+o.unit+'</td>'
		      				+'<td class="text-center unitCosttr name" >'+parseFloat((o.unitCost).toFixed(4))+'</td>'
		      				+'<td class="text-center manualLoss name" >'+(o.manualLoss!=null?o.manualLoss:"")+'</td>'
		      				+'<td class="text-center unitPrice name" >'+parseFloat((o.productCost).toFixed(4))+'</td>'
		      				+'<td class="text-center unit name" >'+o.productUnit+'</td>'
		      				+'<td class="text-center " >'+o.batchMaterial+'</td>'
		      				+'<td class="text-center  name" >'+o.batchMaterialPrice+'</td>'
		      				+'<td class="text-center  name helpWork" data-overstockid='+o.overstockId+'  data-id='+o.id+' data-productid='+o.productId+'></td>'
							+'<td class="text-center "><button class="btn btn-sm btn-info  btn-trans update" data-id='+o.id+'  data-unit='+o.unitId+'>编辑</button></td></tr>'
							
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
									  		name:$('#name').val(),
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   	self.loadEvents();
					   self.checkedd();
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
			this.loadEvents = function(){
				
				//压货环节
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
							 
							 $(".selecttailorType8").each(function(i,o){
								var id=	$(o).parent().data("overstockid");
								$(o).val(id)
							})
									$(".selecttailorType8").change(function(i,o){
									      				var that=$(this);
									      				var overstockId=$(this).parent().parent().find(".selecttailorType8").val();
									      				var name=$(this).parent().parent().find(".selecttailorType8 option:selected").text();
									      				var dataeee={
									      						 id:that.parent().data('id'),
																 productId:that.parent().data('productid'),
																 overstockId:overstockId,
																 overstock:name,
									      				}
									      				var index;
									      				 $.ajax({
														      url:"${ctx}/product/updateProductMaterials",
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
				
				//修改方法
				//选择单位
				var data = {
					type:"unit",
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
				       htmlto='<select class="text-center selectunit"  style="border: none;width:50px; height:30px; background-color: #BFBFBF;text-align-last: center"><option value=""></option>'+html+'</select>'
		      		  },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);sa
					  }
				  });
			    
				$('.update').on('click',function(){
					var aa=$(this).data("composite")
					var cc=$(this).data("unit")
					var dd=$(this).data("doublecomposite")
					if($(this).text() == "编辑"){
						$(this).text("保存")
						
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini text-center cuttingName' style='border: none;width:120px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        })
						$(this).parent().siblings(".editt").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini text-center materiel' style='border: none;width:120px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        })
						$(this).parent().siblings(".materielNumbertw").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini text-center' style='border: none;width:40px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        })
				        $(this).parent().siblings(".unite").each(function() {  // 获取当前行的其他单元格
				        	 $(this).html(htmlto);
				        })
				        $(this).parent().siblings(".manualLoss").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini text-center' style='border: none;width:40px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        })
						$('.selectunit').each(function(i,o){
						
						$(o).val(cc)
						}) 
						 $('.selectdoubleCompositetw').each(function(i,o){
						$(o).val(dd);
						})
						   $(".selectunit").change(function(){
				    	   that=$(this)
				    	   var ccc
				       if(self.getNum()==null){
				    	   ccc=""
				       }else{
				    	   ccc=self.getNum()
				       } 
				       var datae = {
								type:"fill",
								id:ccc,
							}
				    	   $.ajax({
							      url:"${ctx}/product/getMateriel",
							      data:datae,
							      type:"GET",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      			var aaa=$(".selectunit").find("option:selected").text();
					      				if(aaa==o.convertUnit){
					      					that.parent().parent().find('.unitCosttr').text(o.convertPrice);
					      				}else if(aaa==o.unit){
					      					that.parent().parent().find('.unitCosttr').text(o.price);
					      				}else{
					      					that.parent().parent().find('.unitCosttr').text("");
					      				}
					      			}); 
					      			layer.close(index)
							      },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  }); 
				       })
						self.mater();
					}else{
							$(this).text("编辑")
							/* $(this).parent().parent().find(".composite").find(".selectdoubleComposite").attr("disabled",'disabled');
						  $(this).parent().parent().find(".composite").find(".selectdoubleComposite").css('background','none'); */
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							})
							$(this).parent().siblings(".editt").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框
					                $(this).html(obj_text.val()); 
							})
							$(this).parent().siblings(".materielNumbertw").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框
					                $(this).html(obj_text.val()); 
							})
							$(this).parent().siblings(".unite").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框
					                $(this).html(obj_text.val()); 
							})
							$(this).parent().siblings(".manualLoss").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框
					                $(this).html(obj_text.val()); 
							})
							/* $(this).parent().siblings(".doubleComposite").each(function() {  // 获取当前行的其他单元格

								$(this).removeAttr("style");
					            
							}) */
							var postData = {
									id:$(this).data('id'),
									materialsName:$(this).parent().parent('tr').find(".materialsNamett").text(),
									oneMaterial:$(this).parent().parent('tr').find(".materielNumbertw").text(),
									unitId:$(this).parent().parent('tr').find(".unite").find(".selectunit").val(),
									unit:$(this).parent().parent('tr').find(".unite option:selected").text(),
									manualLoss:$(this).parent().parent('tr').find(".manualLoss").text(),
									unitCost:$(this).parent().parent('tr').find(".unitCosttr").text(),
									productCost:$(this).parent().parent('tr').find(".unitPrice").text(),
									productUnit:$(this).parent().parent('tr').find(".unit").text(),
							}
							
							var index;
							$.ajax({
								url:"${ctx}/product/updateProductMaterials",
								data:postData,
								type:"POST",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									if(0==result.code){
									layer.msg(result.message, {icon: 1});
									var data={
											page:self.getCount(),
									  		size:13,	
									  		productId:productIdAll,
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
				})
			} 
			this.mater=function(){
				//选择单位
				var data = {
					type:"unit",
				}
				var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/product/getBaseOne",
				      data:data,
				      type:"GET",
				     
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      				html +='<option value="'+o.id+'" data-name="'+o.name+'">'+o.name+'</option>'
		      			}); 
				       var htmlto='<select class="  selectgroupChange" style="border: none;width:50px; height:30px; background-color: #BFBFBF;"><option value=""></option>'+html+'</select>'
					   	$(".selectCompany").html(htmlto); 
				       $(".selectgroupChange").change(function(){
				    	   that=$(this)
				    	   var ccc
				       if(self.getNum()==null){
				    	   ccc=""
				       }else{
				    	   ccc=self.getNum()
				       } 
				       var datae = {
								type:"fill",
								id:ccc,
							}
				    	   $.ajax({
							      url:"${ctx}/product/getMateriel",
							      data:datae,
							      type:"GET",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      			var aaa=$(".selectgroupChange").find("option:selected").text();
					      				if(aaa==o.convertUnit){
					      					that.parent().parent().find('.selectprice').text(o.convertPrice);
					      				}else if(aaa==o.unit){
					      					that.parent().parent().find('.selectprice').text(o.price);
					      				}else{
					      					that.parent().parent().find('.selectprice').text("");
					      				}
					      			}); 
					      			layer.close(index)
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
				//一键删除
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
							url:"${ctx}/product/deleteProductMaterials",
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
								  		/* productId:"", */
							  	}
								self.loadPagination(data)
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
				//查询
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:100,
				  			productId:self.getCache(),
				  	}
		            self.loadPagination(data);
				});
				/*保存  */
				$('#save').on('click',function(){
					 if($('#number').val()==""){
						 return layer.msg("默认数量不能为空！", {icon: 2});
					}
					if($('#productName').val()==""){
						 return layer.msg("产品名不能为空！", {icon: 2});
					} 
					var leng = $(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').length;
				for (var i = 0; i <leng; i++) {
					var postData = {
						productId:self.getCache(),
						number:$('#number').val(),  
						materialsName:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.materiel').val(),
						oneMaterial:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.oneMaterial').val(),
						unit:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.selectgroupChange option:selected').text(),
						unitId:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.selectgroupChange').val(),
						manualLoss:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.manualLoss').val(),
						unitCost:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.selectprice').text(),
						productCost:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.unitPrice').text(),
						productUnit:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.unit').text(),
					}
					var index;
					$.ajax({
						url:"${ctx}/product/addProductMaterials",
						data:postData,
						type:"POST",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							if(0==result.code){
							layer.msg("新增成功！", {icon: 1});
							 var _data={
									page:1,
							  		size:13,
							  		productId:self.getCache(),
							}
							self.loadPagination(_data) 
							layer.close(index);
							}else{
								layer.msg("新增失败！", {icon: 2});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					
					
						
				}
						
					
					
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
				
				//新增
					
					var html="";
				$('#addCutting').on('click',function(){
					var a=$('#loss').val();
					 html='<tr><td></td><td  style="padding: 2px 0px 2px 4px;"><input type="text" style="border: none;width:120px; height:30px; background-color: #BFBFBF;" data-provide="typeahead" autocomplete="off" class="text-center  cuttingName" /></td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><input type="text" style="border: none;width:120px; height:30px; background-color: #BFBFBF;"  class="text-center materiel" /></td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><input type="text"    style="border: none;width:40px; height:30px; background-color: #BFBFBF;" class="text-center  oneMaterial"  /></td>'
					 +'<td class="text-center edit selectCompany" style="padding: 2px 0px 2px 0px;></td>'
					 +'<td class="text-center edit name"></td>'
					 +'<td class="text-center edit selectprice"></td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><input type="text" value="'+a+'" style="border: none;width:40px; height:30px; background-color: #BFBFBF;" class="text-center manualLoss" /></td>'
					 +'<td class="text-center edit unitPrice" ></td>'
					 +'<td class="text-center edit unit"></td></tr>';
					  /* $(html).insertBefore("#tablecontent"); */
					/* $("#tablecontent").append(html); */
					$("#tablecontent").prepend(html);
					self.mater();
				})
			
				var that;
				$(document).on('click','.materiel',function(){
					 that=$(this)
					//提示物料名
					$(".materiel").typeahead({
						//ajax 拿way数据
						scrollHeight:1,
						source : function(query, process) {
								return $.ajax({
									url : '${ctx}/product/getMateriel',
									type : 'GET',
									data : {
										name:query,
										type:"fill",
									},
									success : function(result) {
										//转换成 json集合
										 var resultList = result.data.map(function (item) {
											 	//转换成 json对象
						                        var aItem = {name: item.name, id:item.id, number:item.number, price:item.price, unit:item.unit}
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
								return item.name+"-"+item.number
								//按条件匹配输出
			                }, matcher: function (item) {
			                	//转出成json对象
						        var item = JSON.parse(item);
						        that.parent().parent().find('.unitPrice').text(item.price);
						        that.parent().parent().find('.unit').text(item.unit);
						        self.setNum(item.id)
						    	return item.name
						    },
							//item是选中的数据
							updater:function(item){
								//转出成json对象
								var item = JSON.parse(item);
								that.parent().parent().find('.unitPrice').text(item.price);
								that.parent().parent().find('.unit').text(item.unit);
								self.setNum(item.id)
									return item.name
							},
							
						});
				});
                                                                                                                                                                                                                                     				
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>
       
</body>

</html>