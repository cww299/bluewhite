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
                                <h3 class="panel-title">Hover rows</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                            <div class="row" style="height: 30px">
			<div class="col-xs-8 col-sm-8  col-md-8">
				<form class="form-search" >
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-7">
							<div class="input-group"> 
								<table><tr><td>产品编号:</td><td><input type="text" name="number" id="number" class="form-control search-query number" /></td>
								<td>产品名称:</td><td><input type="text" name="name" id="name" class="form-control search-query name" /></td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-default btn-square btn-sm btn-3d searchtask">
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
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">产品序号</th>
                                            <th class="text-center">产品编号</th>
                                            <th class="text-center">产品名</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent">
                                        
                                    </tbody>
                                    <button type="button" id="addproduct" class="btn btn-success btn-3d pull-right">新增产品</button>
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
				<!-- PAGE CONTENT BEGINS -->
				<form class="form-horizontal addDictDivTypeForm">
				<div class="row col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>

					<div class="form-group col-md-6">
						<label class="col-sm-3  control-label no-padding-right"
							for="description">产品编号:</label>

						<div >
							<input type="text" id="productNumber" class="col-md-4 col-sm-4"/>
						</div>
						
					</div>
					<div class="form-group col-md-6">
						<label class="col-sm-3  control-label no-padding-right"
							for="description">产品名:</label>

						<div >
							<input type="text" id="productName" class="col-md-4 col-sm-4"/>
						</div>
					</div>
</div>
</form>
</div>
</div>
<!--隐藏框 产品新增结束  -->
<!--隐藏框 产品工序开始  -->
        <div id="addworking" style="display: none;">
			<div class="panel-body">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">工序名称</th>
                                            <th class="text-center">工序时间</th>
                                            <th class="text-center">工序类型</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tableworking">
                                        
                                    </tbody>
                                </table>
                               
                            </div>
</div>
<!--隐藏框 产品工序结束  -->
   
        
        
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
    <script>
 
  /*  $(document).ready(function() {
        $('#example').dataTable();
    });  */
   jQuery(function($){
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
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
				      url:"${ctx}/productPages",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			
		      			 $(result.data.rows).each(function(i,o){
		      				
		      				html +='<tr>'
		      				+'<td class="text-center id">'+o.id+'</td>'
		      				+'<td class="text-center edit number">'+o.number+'</td>'
		      				+'<td class="text-center edit name">'+o.name+'</td>'
							+'<td class="text-center"><button class="btn btn-xs btn-primary btn-3d update" data-id='+o.id+'>编辑</button>  <button class="btn btn-xs btn-success btn-3d addprocedure" data-id='+o.id+'>添加工序</button></td></tr>'
							
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
									  		name:$('#name').val(),
								  			number:$('#number').val(),
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
				//修改方法
				$('.update').on('click',function(){
					if($(this).text() == "编辑"){
						$(this).text("保存")
						
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
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
									name:$(this).parent().parent('tr').find(".name").text(),
							}
							
							var index;
							$.ajax({
								url:"${ctx}/updateProduct",
								data:postData,
								type:"PUT",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									if(0==result.code){
									layer.msg("修改成功！", {icon: 1});
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
				
				//添加工序
				$('.addprocedure').on('click',function(){
					var productId=$(this).data('id')
					var _index
					var index
					var postData
					var dicDiv=$('#addworking');
					 
					
					//工序遍历  
				    var indextwo;
				    var htmltwo = '';
				    var htmlth = '';
				    var htmlfr = '';
				    
				    //遍历工序类型
				    var getdata={type:"fristprocedure",}
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
			      				htmlfr +='<input type="radio" class="Proceduretypeid"  value='+j.id+' >'+j.name+''
			      			 alert(1)
			      				
			      			  });  
			      			
					      }
					  });
				  
				  var data={
				    		productId:productId,
				    		type:1,
				    }
				    //查询工序
				    $.ajax({
					      url:"${ctx}/production/getProcedure",
					      data:data,
					      type:"GET",
					      beforeSend:function(){
					    	  indextwo = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      			 
			      		  success: function (result) {
			      			alert(2)
			      			  $(result.data).each(function(i,o){
			      				  
			      				htmltwo +='<tr>'
			      				+'<td class="text-center id">'+o.name+'</td>'
			      				+'<td class="text-center edit number">'+o.workingTime+'</td>'
			      				+'<td data-id="'+o.id+'" class="text-center" data-code="'+o.procedureType.id+'">'+htmlfr+'</td>' 
								+'<td class="text-center"><button class="btn btn-xs btn-primary btn-3d update" data-id='+o.id+'>编辑</button>  <button class="btn btn-xs btn-success btn-3d addprocedure">添加工序</button></td></tr>'
								
			      			});  
						   	   
						   	layer.close(indextwo);
						   	//新增时 查找工序类型
						  alert(3)
					      			htmltwo="<tr><td class='text-center'><input type='text' class='input-large workingname'></td><td class='text-center'><input type='text' class='input-small workingtime'></td><td class='text-center'>"+htmlfr+"</td><td class='text-center'><button class='btn btn-xs btn-primary btn-3d add' data-productid="+productId+">新增</button></td></tr>"+htmltwo;
					      			$("#tableworking").html(htmltwo); 
					      			
			      			
			      			
						   	  $("#tableworking").html(htmltwo);  
					      			self.loadevenstwo();
						   	self.checked();
					      },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(indextwo);
						  }
					  });
				    //打开隐藏框
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"产品工序",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							 
							},
						  end:function(){
							  $('#addworking').hide();
							
						  }
					});
				})
				
			}
			this.checked=function(){
				
				$(".Proceduretypeid").each(function(i,o){
						
						var rest=$(o).parent().data("code");
						if($(o).val()==rest){
							$(o).attr('checked', 'checked')
						}
				})
				
			}	
			this.loadevenstwo=function(){
				  $(".Proceduretypeid").on('click',function(){
					  $(this).parent().find(".Proceduretypeid").each(function(i,o){
							$(o).prop("checked", false);

						})
							 $(this).prop("checked", true);
					var index; 
					var del=$(this);
					var id = $(this).parent().data('id');
					var rest = $(this).val();
					
					$.ajax({
						url:"${ctx}/production/addProcedure",
						data:{
							id:id,
							procedureTypeId:rest,
							},
						type:"post",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						success:function(result){
							
							del.parent().find(".Proceduretypeid").each(function(i,o){
								$(o).prop("checked", false);

							})
								del.prop("checked", true);
						
							layer.msg("修改成功！", {icon: 1});
							layer.close(index);
						
					
							
						},
						error:function(){
							layer.msg("修改失败！", {icon: 2});
							layer.close(index);
						}
					});
				})  
				
				$('.add').on('click',function(){
					var index;
					var postData;
					postData={
							name:$(".workingname").val(),
							workingTime:$(".workingtime").val(),
							  type:1,
							  productId:$(this).data('productid'),
							  procedureTypeId:$(this).parent().parent().find("input:radio:checked").val(),
					  }
					if($(this).parent().parent().find("input:radio:checked").val()==null){
						return 	layer.msg("工序类型不能为空！", {icon: 2});
					}
					if($(".workingname").val()==""){
						return 	layer.msg("工序名不能为空！", {icon: 2});
					}
					if($(".workingtime").val()==""){
						return 	layer.msg("工序时间不能为空！", {icon: 2});
					}
					
					   $.ajax({
							url:"${ctx}/production/addProcedure",
							data:postData,
				            traditional: true,//传数组
							type:"post",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
									layer.msg("添加成功！", {icon: 1});
									$("#addworking").load(location.href+" #addworking"); 
									
									
								}else{
									layer.msg("添加失败", {icon: 2});
								}
								
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						}); 
				})
			}
			this.events = function(){
				
				//查询
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			name:$('#name').val(),
				  			number:$('#number').val(),
				  	}
		            self.loadPagination(data);
				});
				
				
				//新增产品
				$('#addproduct').on('click',function(){
					
					var _index
					var index
					var postData
					var dicDiv=$('#addDictDivType');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"新增产品",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							 
							  postData={
									  number:$("#productNumber").val(),
									  name:$("#productName").val(),
									  
							  }
							  $.ajax({
									url:"${ctx}/addProduct",
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
											layer.msg("添加成功！", {icon: 1});
											$(".addDictDivTypeForm")[0].reset();
											self.loadPagination(data);
											$('#addDictDivType').hide();
											
										}else{
											layer.msg("添加失败", {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
							},
						  end:function(){
							  $('#addDictDivType').hide();
						
							  $('.addDictDivTypeForm')[0].reset(); 
							
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