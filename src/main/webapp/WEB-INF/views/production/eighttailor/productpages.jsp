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
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>  <!-- 时间插件 -->
<script src="${ctx }/static/js/layer/layer.js"></script>
<script src="${ctx }/static/js/laypage/laypage.js"></script> 
<link rel="stylesheet" href="${ctx }/static/css/main.css">

</head>

<body>
	
<div class="panel panel-default">
	<div class="panel-body" style="height:750px;">
		<table>
			<tr>
				<td>产品编号:</td>
				<td><input type="text" name="number" id="number"
					class="form-control search-query number" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>产品名称:</td>
				<td><input type="text" name="name" id="name"
					class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><span class="input-group-btn">
					<button type="button"
						class="btn btn-info btn-square btn-sm btn-3d searchtask">
						查&nbsp;找</button>
				</span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<!-- <td> <span class="input-group-btn">
					<button type="button" id="addproduct"
						class="btn btn-success  btn-sm btn-3d pull-right">
						新增产品</button>
				</span></td> -->
			</tr>
		</table>

		
		<table class="table table-hover">
			<thead>
				<tr>
					<th class="text-center">产品序号</th>
					<th class="text-center">产品编号</th>
					<th class="text-center">产品名</th>
					<th class="text-center">激光预计生产单价</th>
					<th class="text-center">激光外发价格</th>
					<th class="text-center">冲床预计生产单价</th>
					<th class="text-center">冲床外发价格</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
			<tbody id="tablecontent">

			</tbody>
		</table>
		<div id="pager" class="pull-right"></div>
	</div>
</div>

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
	<!--隐藏框 产品针工工序开始  -->
	<div id="addworking" style="display: none;">
		<div class="panel-body">
			<div class="form-group">
				<input type="file" name="file" id="upfile" style="display: inline">
				<select id="selectstate"><option value=0>激光</option>
					<option value=1>冲床</option></select>
				<button type="button" class="btn btn-success btn-sm" id="btn"
					style="display: inline">点击导入</button>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="text-center">工序名称</th>
						<th class="text-center">工序时间(秒)</th>
						<th class="text-center">工序类型</th>
						<th class="text-center">操作</th>
					</tr>
				</thead>
				<tbody id="tableworking">
				</tbody>
			</table>
		</div>
	</div>
	<!--隐藏框 产品针工工序结束  -->
	<!--隐藏框 批次填写开始  -->
	<div id="addbatch" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addbatchForm">
				<div class="form-group">
					<label class="col-sm-3 control-label">产品名称:</label>
					<div class="col-sm-6">
						<input type="text" id="proName" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">裁剪方式:</label>
					<div class="col-sm-6">
						<select id="selectcut" class="form-control"><option
								value=0>激光</option>
							<option value=1>冲床</option></select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">批次号:</label>
					<div class="col-sm-6">
						<input type="text" id="bacthNumber" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">数量:</label>
					<div class="col-sm-6">
						<input type="text" id="prosum" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">备注:</label>
					<div class="col-sm-6">
						<input type="text" id="remarks" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">批次时间:</label>
					<div class="col-sm-6">
						<input id="Time" placeholder="时间可不填"
							class="form-control laydate-icon"
							onClick="laydate({elem: '#Time', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
					</div>
				</div>
			</form>
		</div>
	</div>
	<!--隐藏框 批次填写结束  -->
	</section>




	<script>
 
  /*  $(document).ready(function() {
        $('#example').dataTable();
    });  */
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
			 var data={
						page:1,
				  		size:13,	
				  		type:5,

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
		      				if(o.hairPrice==null){
		      					o.hairPrice=0;
		      				}
		      				if(o.deedlePrice==null){
		      					o.deedlePrice=0;
		      				}
		      				if(o.puncherHairPrice==null){
		      					o.puncherHairPrice=0;
		      				}
		      				html +='<tr>'
		      				+'<td class="text-center id">'+o.id+'</td>'
		      				+'<td class="text-center number">'+o.departmentNumber+'</td>'
		      				+'<td class="text-center name">'+o.name+'</td>'
		      				+'<td class="text-center  departmentPrice">'+o.departmentPrice*1+'</td>'
		      				+'<td class="text-center edit  workPrice">'+o.hairPrice+'</td>'
		      				+'<td class="text-center  puncherDepartmentPrice">'+o.puncherDepartmentPrice*1+'</td>'
		      				+'<td class="text-center edit  puncherHairPrice">'+o.puncherHairPrice+'</td>'
							+'<td class="text-center"><button class="btn btn-xs btn-info  btn-trans update" data-id='+o.id+'>编辑</button>  <button class="btn btn-xs btn-primary btn-trans addprocedure" data-id='+o.id+' data-name='+o.name+'>添加裁剪工序</button> <button class="btn btn-xs btn-success btn-trans addbatch" data-id='+o.id+' data-name='+o.name+'>填写批次</button></td></tr>'
		      			}); 
		      			 self.setIndex(result.data.pageNum);
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
									  		type:5,
									  		name:$('#name').val(),
									  		departmentNumber:$('#number').val(),
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
				//触发批次弹框
				$('.addbatch').on('click',function(){
					var _index
					var index
					var postData
					var dicDiv=$('#addbatch');
					var name=$(this).data('name');
					var bacthDepartmentPrice=$(this).parent().parent().find('.departmentPrice').text();
					var bacthHairPrice=$(this).parent().parent().find('.workPrice').text();
					var bacthDeedlePrice=$(this).parent().parent().find('.deedlePrice').text();
					var puncherDepartmentPrice=$(this).parent().parent().find('.puncherDepartmentPrice').text();
					var puncherHairPrice=$(this).parent().parent().find('.puncherHairPrice').text();
					
					$('#proName').val(name);
					var id=$(this).data('id');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['30%', '65%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"填写批次",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  if($('#bacthNumber').val()==""){
								  return layer.msg("批次号不能为空", {icon: 2});
							  }
							  if($('#prosum').val()==""){
								  return layer.msg("数量不能为空", {icon: 2});
							  }
							  var a;
							  var b;
							  if($('#selectcut').val()==0){
								  a=bacthDepartmentPrice;
								  b=bacthHairPrice;
							  }
							  if($('#selectcut').val()==1){
								  a=puncherDepartmentPrice;
								  b=puncherHairPrice;
							  }
							  postData={
									  productId:id,
									  bacthNumber:$('#bacthNumber').val(),
									  number:$('#prosum').val(),
									  remarks:$('#remarks').val(),
									  bacthDepartmentPrice:a,
									  bacthHairPrice:b,
									  bacthDeedlePrice:bacthDeedlePrice,
									  type:5,
									  allotTime:$('#Time').val(),
									  flag:0,
									  sign:$('#selectcut').val(),
							  }
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
											layer.msg("添加成功！", {icon: 1});
											 
											$('.addbatchForm')[0].reset(); 
											//$('#addbatch').hide();
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
							  $('.addbatchForm')[0].reset(); 
							  $('#addbatch').hide();
						  }
					});
				})
				
				
				
				//触发针工工序弹框 加载内容方法
				$('.addprocedure').on('click',function(){
					var _index
					var productId=$(this).data('id')
					var name=$(this).data('name')
					var dicDiv=$('#addworking');
					  //打开隐藏框
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:name,
						  content: dicDiv,
						  
						  yes:function(index, layero){
							 
							},
						  end:function(){
							  $('#addworking').hide();
							  data={
									page: self.getIndex(),
								  	size:13,	
								  	type:5,
								  	name:$('#name').val(),
								  	departmentNumber:$('#number').val(),
							  }
							self.loadPagination(data);
						  }
					});
					self.setCache(productId);
					self.loadworking();
					
					
				})
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
									type:5,
									id:$(this).data('id'),
									departmentNumber:$(this).parent().parent('tr').find(".number").text(),
									name:$(this).parent().parent('tr').find(".name").text(),
									hairPrice:$(this).parent().parent('tr').find(".workPrice").text(),
									puncherHairPrice:$(this).parent().parent('tr').find(".puncherHairPrice").text(),
							}
							
							var index;
							$.ajax({
								url:"${ctx}/updateProduct",
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
			//弹框内容加载
			this.loadworking=function(){
				//添加工序
					var productId=self.getCache()
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    var htmltwo = '';
				    var htmlth = '';
				    var htmlfr = '';
				    var data={
				    		productId:productId,
				    		type:5,
				    		flag:0,
				    }
				    //遍历工序类型
				    var getdata={type:"productEightTailor",}
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
			      			  });  
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
						      			  var allTime = 0;		//汇总时间
						      			  $(result.data).each(function(i,o){
						      				allTime+=o.workingTime;
						      				htmltwo +='<tr>'
						      				+'<td class="text-center edit workingnametwo id">'+o.name+'</td>'
						      				+'<td class="text-center edit workingtimetwo">'+o.workingTime+'</td>'
						      				+'<td data-id="'+o.id+'" class="text-center" data-code="'+o.procedureType.id+'">'+htmlfr+'</td>' 
											+'<td class="text-center"><button class="btn btn-xs btn-primary btn-3d updateworking" data-id='+o.id+'>编辑</button>  <button class="btn btn-xs btn-danger btn-3d deleteprocedure">删除</button></td></tr>'
						      			  });  
						      			  htmltwo += ['<tr>',
				      						'<td class="text-center">合计</td>',
				      						'<td class="text-center">'+allTime+'</td>',
				      						'<td></td>',
				      						'<td></td>',
				      						'<td></td>',
			      						  '</tr>'].join(' ');
									   	layer.close(indextwo);
									   	//新增时 查找工序类型
								      			htmltwo="<tr><td class='text-center'><input type='text' class='input-large workingname'></td><td class='text-center'><input type='text' class='input-small workingtime' ></td><td class='text-center'>"+htmlfr+"</td><td class='text-center'><button class='btn btn-xs btn-primary btn-3d add' data-productid="+productId+">新增</button></td></tr>"+htmltwo;
								      			$("#tableworking").html(htmltwo); 
								      			
						      			
						      			
									   	  $("#tableworking").html(htmltwo);  
								      			self.loadevenstwo();
									   	self.checked();
								      },error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(indextwo);
									  }
								  });
					      }
					  });
				  
				
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
				//删除工序
				$(".deleteprocedure").on('click',function(){
					var data={
							id:$(this).parent().prev().data('id')
							}
					var _indexx;
					var index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
					$.ajax({
						url:"${ctx}/production/delete",
						data:data,
						type:"GET",
						beforeSend:function(){
							_indexx = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						}, 
						success:function(result){
						if(result.code==0){
							layer.msg(result.message, {icon: 1});
							self.loadworking();
							layer.close(_indexx);
						}else{
							layer.msg(result.message, {icon: 2});
							layer.close(_indexx);
						}
						},
						error:function(){
							layer.msg("删除失败！", {icon: 2});
							layer.close(_indexx);
						}
					});
					  });
				})
				//删除工序
				$(".deleteproceduretw").on('click',function(){
					var data={
							id:$(this).parent().prev().data('id')
							}
					var _indexx;
					var index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
					$.ajax({
						url:"${ctx}/production/delete",
						data:data,
						type:"GET",
						beforeSend:function(){
							_indexx = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						}, 
						success:function(result){
						if(result.code==0){
							layer.msg(result.message, {icon: 1});
							self.loadworkingtw();
							layer.close(_indexx);
						}
						},
						error:function(){
							layer.msg("删除失败！", {icon: 2});
							layer.close(_indexx);
						}
					});
					  });
				})
			//修改工序内容
				 $(".updateworking").on('click',function(){
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
								var data={
										id:$(this).data('id'),
										name:$(this).parent().parent('tr').find('.workingnametwo').text(),
										workingTime:$(this).parent().parent('tr').find('.workingtimetwo').text(),
								}
								$.ajax({
									url:"${ctx}/production/addProcedure",
									data:data,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										
									if(result.code==0){
									
										layer.msg("修改成功！", {icon: 1});
										layer.close(index);
									}
								
										
									},
									error:function(){
										layer.msg("修改失败！", {icon: 2});
										layer.close(index);
									}
								});
								
								
								
								
						}
				 })
				 $(".updateworkingtw").on('click',function(){
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
								var data={
										id:$(this).data('id'),
										name:$(this).parent().parent('tr').find('.workingnametwotw').text(),
										workingTime:$(this).parent().parent('tr').find('.workingtimetwotw').text(),
								}
								$.ajax({
									url:"${ctx}/production/addProcedure",
									data:data,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										
									if(result.code==0){
									
										layer.msg("修改成功！", {icon: 1});
										layer.close(index);
									}
								
										
									},
									error:function(){
										layer.msg("修改失败！", {icon: 2});
										layer.close(index);
									}
								});
								
								
								
								
						}
				 })
				  $(".Proceduretypeid").on('click',function(){
					  $(this).parent().find(".Proceduretypeid").each(function(i,o){
							$(o).prop("checked", false);

						})
							 $(this).prop("checked", true);
					var index; 
					var del=$(this);
					var id = $(this).parent().data('id');
					var rest = $(this).val();
					var flag=0;
					if(rest==100){
						flag=1
					}
					if(id!=undefined){
					$.ajax({
						url:"${ctx}/production/addProcedure",
						data:{
							flag:flag,
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
					}
				})  
				//新增工序
				$('.add').on('click',function(){
					var index;
					var postData;
					var workingtime=$(".workingtime").val();
					if($(this).parent().parent().find("input:radio:checked").val()==null){
						return 	layer.msg("工序类型不能为空！", {icon: 2});
					}
					if($(".workingname").val()==""){
						return 	layer.msg("工序名不能为空！", {icon: 2});
					}
					var b;
					if($(this).parent().parent().find("input:radio:checked").val()==140){
						b=0;
					}
					if($(this).parent().parent().find("input:radio:checked").val()==141){
						b=1;
					}
					postData={
							  flag:0,
							  sign:b,
							  name:$(".workingname").val(),
							  workingTime:workingtime,
							  type:5,
							  productId:$(this).data('productid'),
							  procedureTypeId:$(this).parent().parent().find("input:radio:checked").val(),
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
									self.loadworking();
									layer.close(index);
								}else{
									layer.msg("添加失败", {icon: 2});
								}
								
								
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						}); 
				})
				
			
			}
			this.events = function(){
				
				//导入
				$('#btn').on('click',function(){
				var a=$('#selectstate').val();
					if($('#upfile')[0].files[0]==null){
						return layer.msg("请选择需要导入的文件", {icon: 2});
					}
					  var imageForm = new FormData();
				
					  		imageForm.append("sign",a);
							imageForm.append("file",$('#upfile')[0].files[0]);
				  			imageForm.append("productId",self.getCache());
				  			imageForm.append("type",5);
				  			
					 $.ajax({
							url:"${ctx}/excel/importEightTailor",
							data:imageForm,
							type:"post",
							processData:false,
							contentType: false,
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
								self.loadworking();
								layer.close(index);
							},
							error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						}); 
		          
					
				});
				
				
				
				//查询
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:5,
				  			name:$('#name').val(),
				  			departmentNumber:$('#number').val(),
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
						  area: ['30%', '40%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"新增产品",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  if($("#productNumber").val()==null){
								return  layer.msg("产品编号不能为空！", {icon: 2});
							  }
							  if($("#productName").val()==null){
								  return  layer.msg("产品名！", {icon: 2});
							  }
							  postData={
									  departmentNumber:$("#productNumber").val(),
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
											//$('#addDictDivType').hide();
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