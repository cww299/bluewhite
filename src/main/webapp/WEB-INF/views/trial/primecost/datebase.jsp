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
<title>基础数据库</title>
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
							<h3 class="panel-title">基础数据库</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i> <i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="panel-body">

							<div class="row" style="height: 30px; margin: 15px 0 10px">
								<div class="col-xs-8 col-sm-8  col-md-8">
									<form class="form-search">
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-12">
												<div class="input-group">
													<table>
														<tr>
															<td>物料编号:</td>
															<td><input type="text" name="name" id="ntwo"
																class="form-control search-query name" /></td>
															<td>&nbsp&nbsp</td>
															<td>物料名:</td>
															<td><input type="text" name="name" id="ntwo2"
																class="form-control search-query name" /></td>
														</tr>
													</table>
													<span class="input-group-btn">
														<button type="button"
															class="btn btn-info btn-square btn-sm navbar-right btn-3d searchtask">
															查找 <i class="icon-search icon-on-right bigger-110"></i>
														</button>
													</span>
													<button type="button" id="addgroup"
														class="btn btn-success btn-sm btn-3d pull-right">新增小组</button>
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>



							<table class="table table-hover">
								<thead>
									<tr>
										<th class="text-center">物料编号</th>
										<th class="text-center">物料名</th>
										<th class="text-center">物料最新价格</th>
										<th class="text-center">物料克重等备注</th>
										<th class="text-center">物料换算单位</th>
										<th class="text-center">物料换算后价格</th>
										<th class="text-center">操作</th>
									</tr>
								</thead>
								<tbody id="tablecontent">

								</tbody>

								<tbody id="tablecontenttw">
								</tbody>
							</table>
							<div id="pager" class="pull-right"></div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>
	<!--隐藏框 小组新增开始  -->
	<div id="addDictDivType" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeForm">

				<div class="form-group">
					<label class="col-sm-3 control-label">物料编号:</label>
					<div class="col-sm-6">
						<input type="text" id="groupnumber" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">物料名:</label>
					<div class="col-sm-6">
						<input type="text" id="groupName" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">物料最新价格:</label>
					<div class="col-sm-6">
						<input type="text" id="groupprice" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">物料克重等备注:</label>
					<div class="col-sm-6">
						<input type="text" id="groupunit" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">物料换算单位:</label>
					<div class="col-sm-6">
						<input type="text" id="groupconvertUnit" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">物料换算后的价格:</label>
					<div class="col-sm-6">
						<input type="text" id="groupconvertPrice" class="form-control">
					</div>
				</div>
			</form>
		</div>
	</div>
	<!--隐藏框 小组新增结束  -->

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
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
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
				      url:"${ctx}/product/getMaterielPage",
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
		      				+'<td class="text-center number">'+o.number+'</td>'
		      				+'<td class="text-center edit name">'+o.name+'</td>'
		      				+'<td class="text-center edit price">'+o.price+'</td>'
		      				+'<td class="text-center edit unit">'+o.unit+'</td>'
		      				+'<td class="text-center edit convertUnit">'+o.convertUnit+'</td>'
		      				+'<td class="text-center edit convertPrice">'+(o.convertPrice!=null?o.convertPrice:"")+'</td>'
							+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans update" data-id='+o.id+'>编辑</button></td></tr>'
							
		      			}); 
				        //显示分页
				        self.setCount(result.data.pageNum)
					   	 laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		number:$("#ntwo").val(),
											name:$("#ntwo2").val(),
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
									price:$(this).parent().parent('tr').find(".price").text(),
									unit:$(this).parent().parent('tr').find(".unit").text(),
									convertUnit:$(this).parent().parent('tr').find(".convertUnit").text(),
									convertPrice:$(this).parent().parent('tr').find(".convertPrice").text(),
							}
							
							var index;
							$.ajax({
								url:"${ctx}/product/addMateriel",
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
						        			page:self.getCount(),
									  		size:13,
									  		number:$("#ntwo").val(),
											name:$("#ntwo2").val(),
								  	}
						        
						            self.loadPagination(_data);
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
				
			}
			this.events = function(){
				//新增小组
				$('#addgroup').on('click',function(){
					
					var _index
					var index
					var postData
					var dicDiv=$('#addDictDivType');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['30%', '50%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"新增小组",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							 
							  postData={
									  name:$("#groupName").val(),
									  number:$("#groupnumber").val(),
									  price:$("#groupprice").val(),
									  unit:$("#groupunit").val(),
									  convertUnit:$("#groupconvertUnit").val(),
									  convertPrice:$("#groupconvertPrice").val(),
							  }
							  $.ajax({
									url:"${ctx}/product/addMateriel",
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
				//查询
				$('.searchtask').on('click',function(){
						var datae={
								page:1,
						  		size:13,
								number:$("#ntwo").val(),
								name:$("#ntwo2").val(),
						}
						self.loadPagination(datae);
				});
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>

</body>

</html>