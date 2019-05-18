<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->
<head>
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>  <!-- 时间插件 -->
	<script src="${ctx }/static/js/layer/layer.js"></script>
	<script src="${ctx }/static/js/laypage/laypage.js"></script> 
	<link rel="stylesheet" href="${ctx }/static/css/main.css">
	<link rel="stylesheet" href="${ctx }/static/sfm/css/bootstrap-datetimepicker.min.css">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>批次管理</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>

<body>

<div class="panel panel-default">
	<div class="panel-body">
		<table>
			<tr>
				<td>批次号:</td>
				<td><input type="text" name="number" id="number"
					placeholder="请输入批次号"
					class="form-control search-query number" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>产品名称:</td>
				<td><input type="text" name="name" id="name"
					placeholder="请输入产品名称"
					class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>开始时间:</td>
				<td><input id="startTime" placeholder="请输入开始时间"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>结束时间:</td>
				<td><input id="endTime" placeholder="请输入结束时间"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="input-group-btn">
						<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask"> 查&nbsp;找</button> </span></td>
			</tr>
		</table>
		<h1 class="page-header"></h1>
		<table class="table table-condensed table-hover" style="padding:0px">
				<thead>
					<tr>
						<th class="center"><label> <input type="checkbox"
								class="ace checks" /> <span class="lbl"></span>
						</label></th>
						<th class="text-center">批次号</th>
						<th class="text-center">时间</th>
						<th class="text-center" style="width: 250px;">产品名</th>
						<th class="text-center">数量</th>
						<th class="text-center">预计生产单价</th>
						<th class="text-center">外发价格</th>
						<th class="text-center">任务价值</th>
						<th class="text-center">地区差价</th>
						<th class="text-center">当批用时</th>
						<th class="text-center">备注</th>
						<th class="text-center">完成状态</th>
						<th class="text-center">操作</th>
					</tr>
				</thead>
				<tbody id="tablecontent">

				</tbody>
				<thead>    <!--  此处修改：加入合计  -->
					<tr>
						<td class="center">合计</td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center" id="total"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center" id="tota2"></td>
						<td class="text-center"></td>
						<td class="text-center" id="tota3"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
					</tr>
				</thead>
		</table>
		<div id="pager" class="pull-right"></div>
	</div>
</div>

	<!--隐藏框 工序分配开始  -->
	<div id="addDictDivType" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeForm">
				<div class="row col-xs-12  col-sm-12  col-md-12 ">


					<div class="form-group">
						<label class="col-sm-2 col-md-2 control-label">任务数量:</label>
						<div class="col-sm-2 col-md-2">
							<input type="text" class="form-control sumnumber">
						</div>
						<label class="col-sm-3 control-label">任务分配:</label>
						<div class="col-sm-2">
							<input id="Time" placeholder="时间可不填"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#Time', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>

					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">选择工序:</label>
						<div class="col-sm-2 working"></div>
						<div class="col-sm-2 checkworking"></div>
						<label class="col-sm-1 control-label">完成人:</label>
						<div class="col-sm-2 complete">
							<input type="text" class="form-control">
						</div>
						<div class="col-sm-2 select"></div>
					</div>
				</div>
		</div>

		</form>
	</div>
	
	<!--隐藏框 工序分配结束  -->

	<!-- 任务详情开始-->
	<div id="addworking"
		style="display: none; position: absolute; z-index: 3;">
		<table>
			<tr>
				<td><button type="button"
						class="btn btn-default btn-danger btn-xs btn-3d attendance">一键删除</button>&nbsp;&nbsp;</td>
			</tr>
		</table>
		<div class="panel-body">
			<table class="table table-condensed table-hover">
				<thead>
					<tr>
						<th class="center"><label> <input type="checkbox"
								class="ace checksto" /> <span class="lbl"></span>
						</label></th>
						<th class="text-center">任务编号</th>
						<th class="text-center">批次号</th>
						<th class="text-center">产品名</th>
						<th class="text-center">时间</th>
						<th class="text-center">工序</th>
						<th class="text-center">预计时间</th>
						<th class="text-center">任务价值</th>
						<th class="text-center">b工资净值</th>
						<th class="text-center">数量</th>
						<th class="text-center">完成人</th>
						<th class="text-center">操作</th>
					</tr>
				</thead>
				<tbody id="tablecontentto">

				</tbody>
				
			</table>
			<div id="pagerr" class="pull-right"></div>
		</div>

	</div>
	<!-- 任务详情结束-->

	<!--隐藏框 人员信息开始  -->
	<div id="userInformation" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeForm">
				<div class="form-group">
					<div   id="modal-body" style="text-align:center;">
						
					</div>
				</div>
			</form>
		</div>
	</div>
	<!--隐藏框 人员信息结束  -->






	


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
		  	this.setNum = function(num){
		  		_num=num;
		  	}
		  	this.getNum = function(){
		  		return _num;
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
				  		type:5,
				  		flag:0,
				  		status:$('#selectstate').val(),
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
				      url:"${ctx}/bacth/allBacth",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			//修改合计
			      			$("#total").text(result.data.statData.number)
			      			$("#tota2").text(result.data.statData.sumTaskPrice)
			      			$("#tota3").text(result.data.statData.time)
			      			//修改此处
		      			 $(result.data.rows).each(function(i,o){
		      				 if(o.bacthHairPrice==null){
		      					o.bacthHairPrice=0;
		      				 }
		      				if(o.bacthDeedlePrice==null){
		      					o.bacthDeedlePrice=0;
		      				 }
		      				if(o.time==null){
		      					o.time=0;
		      				 }
		      				 var strname="";
		      				 if(o.status==1){
		      					strname="完成";
		      				 }else{
		      					strname="未完成";
		      				 }
		      				 var a="";
		      				 if(o.sign==0){
		      					 a="（激光）"
		      				 }else{
		      					 a="（冲床）"
		      				 }
		      				 html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="hidden batch">'+o.id+'</td>'
		      				+'<td class="text-center  bacthNumber">'+o.bacthNumber+'</td>'
		      				+'<td class="text-center edit allotTime">'+o.allotTime+'</td>'
		      				+'<td class="text-center  names" data-id='+o.id+'>'+o.product.name+a+'</td>'
		      				+'<td class="text-center edit number">'+o.number+'</td>'
		      				+'<td class="text-center  bacthDepartmentPrice">'+parseFloat((o.bacthDepartmentPrice).toFixed(3))+'</td>'
		      				+'<td class="text-center edit bacthHairPrice">'+o.bacthHairPrice+'</td>'
		      				+'<td class="text-center  sumTaskPrice">'+ parseFloat((o.sumTaskPrice*1).toFixed(3))+'</td>'
		      				+'<td class="text-center  regionalPrice">'+parseFloat((o.regionalPrice*1).toFixed(3))+'</td>'
		      				+'<td class="text-center ">'+parseFloat((o.time).toFixed(3))+'</td>'
		      				+'<td class="text-center edit remarks">'+o.remarks+'</td>'
		      				+'<td class="text-center ">'+strname+'</td>'
							+'<td class="text-center"><button class="btn btn-sm btn-primary btn-trans addDict" data-id='+o.id+' data-proid='+o.product.id+' data-bacthnumber='+o.bacthNumber+' data-proname='+o.product.name+'>分配</button>  <button class="btn btn-sm btn-info  btn-trans updateremaketw" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>' 
							
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
									  		type:5,
								  			name:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			flag:0,
								  			status:$('#selectstate').val(),
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
			
			  this.loadPaginationto = function(data){
				    var index;
				    var html = '';
				    var htmlto="";
				    $.ajax({
					      url:"${ctx}/task/allTask",
					      data:data,
					      type:"GET",
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			 $(result.data.rows).each(function(i,o){
			      				 var a=""
			      				 var s=o.procedureName
			      				if(o.flag==1){
			      					a="(返工)"
			      				}
			      				 if(o.taskActualTime==null){
			      					o.taskActualTime=0
			      				 }
			      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxIdto" value="'+o.id+'"/><span class="lbl"></span></label></td>'
			      				+'<td class="text-center  name">'+o.id+'</td>'
			      				+'<td class="text-center  name">'+o.bacthNumber+'</td>'
			      				+'<td class="text-center name">'+o.productName+'</td>'
			      				+'<td class="text-center edit allotTimetw">'+o.allotTime+'</td>'
			      				+'<td class="text-center  name">'+s+a+'</td>'
			      				+'<td class="text-center  name">'+parseFloat((o.expectTime).toFixed(4))+'</td>'
			      				+'<td class="text-center  name">'+parseFloat((o.taskPrice).toFixed(4))+'</td>'
			      				+'<td class="text-center  name">'+parseFloat((o.payB).toFixed(4))+'</td>'
			      				+'<td class="text-center edit number">'+o.number+'</td>'
			      				+'<td class="text-center"><button class="btn btn-primary btn-trans btn-sm savemode" data-toggle="modal" data-target="#myModal" data-id="'+o.id+'")">查看人员</button></td>'
								+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans deletetw" data-id='+o.id+'>删除</button></td></tr>'
								
			      			}); 
					        //显示分页
						   	 laypage({
						      cont: 'pagerr', 
						      pages: result.data.totalPages, 
						      curr:  result.data.pageNum || 1, 
						      jump: function(obj, first){ 
						    	  if(!first){ 
						    		 
							        	var _data = {
							        			page:obj.curr,
										  		size:13,
										  		type:5,
										  		bacthId:self.getCache(),
									  			
									  	}
							        
							            self.loadPaginationto(_data);
								     }
						      }
						    });  
						   	layer.close(index);
						   
						   	 $("#tablecontentto").html(html); 
						   	 self.loadEventss();
						   	self.checkeddto();
						   	self.checkedddto();
					      },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);
						  }
					  });
				}
			  this.checkeddto=function(){
					
					$(".checksto").on('click',function(){
						
	                    if($(this).is(':checked')){ 
				 			$('.checkboxIdto').each(function(){  
	                    //此处如果用attr，会出现第三次失效的情况  
	                     		$(this).prop("checked",true);
				 			})
	                    }else{
	                    	$('.checkboxIdto').each(function(){ 
	                    		$(this).prop("checked",false);
	                    		
	                    	})
	                    }
	                }); 
					
				}
			  this.checkedddto=function(){
					
					$(".rest").each(function(i,o){
							
							var rest=$(o).parent().data("status");
						
							if($(o).val()==rest){
							
								$(o).attr('checked', 'checked');;
							}
					})
					
				}
			  this.loadEventss = function(){
					
					
					$('.rest').on('click',function(){
						var  del=$(this);
						var id = $(this).parent().data('id');
						var rest = $(this).val();
						
					    	  $.ajax({
									url:"${ctx}/task/getTaskActualTime",
									data:{
										ids:id,
										status:rest,
										},
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									success:function(result){
										//选择1
										
										if(rest==0){
									
											del.parent().find(".rest").eq(1).prop("checked", false);

										}else{
											del.parent().find(".rest").eq(0).prop("checked", false);
											
										}
										layer.msg("操作成功！", {icon: 1});
										layer.close(index);
									
								
										
									},
									error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
					    	  
					 
			
					});
					
					
					//修改方法
					$('.updateremake').on('click',function(){
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
										allotTime:$(this).parent().parent('tr').find(".allotTimetw").text(),
								}
								var index;
								$.ajax({
									url:"${ctx}/task/upTask",
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
							        			page:1,
										  		size:13,
										  		type:5,
										  		bacthId:self.getCache(),
									  			
									  	}
							        
							            self.loadPaginationto(_data);
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
					
					
					//删除
							$('.deletetw').on('click',function(){
								var postData = {
										ids:$(this).data('id'),
								}
								
								var index;
								 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
								$.ajax({
									url:"${ctx}/task/delete",
									data:postData,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
										layer.msg("删除成功！", {icon: 1});
										var _data={
												page:1,
										  		size:13,
												bacthId:self.getCache(),
												type:5,
										}
										self.loadPaginationto(_data)
										layer.close(index);
										}else{
											layer.msg("删除失败！", {icon: 1});
											layer.close(index);
										}
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
								 })
					})
					
					//人员详细显示方法
					$('.savemode').on('click',function(){
						var id=$(this).data('id')
						var arr=new Array();
						var html="";
						var dicDiv=$('#userInformation');
						 var postData={
									id:id,
							}
						  $.ajax({
								url:"${ctx}/task/taskUser",
								data:postData,
								type:"GET",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									$(result.data).each(function(i,o){
									html+=o.userName+"&nbsp;&nbsp;&nbsp;&nbsp;"
									})
									$('#modal-body').html(html);
									layer.close(index);
									
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
						_index = layer.open({
							  type: 1,
							  skin: 'layui-layer-rim', //加上边框
							  area: ['30%', '200px'], 
							  btnAlign: 'c',//宽高
							  offset:(parent.document.documentElement.scrollTop+20)+'px',
							  maxmin: true,
							  title:"人员信息",
							  content: dicDiv,
							  btn: ['关闭'],
							  end:function(){
								  $('#addDictDivType').hide();
								  $('.addDictDivTypeForm')[0].reset(); 
								
							  }
						});
						
					})
					
					
				}  
			  
			this.loadEvents = function(){
				
				$('.names').on('click',function(){
					var that=$(this);
					var a=$(this).data('id');
					self.setCache(a);
					var data={
							bacthId:$(this).data('id'),
							page:1,
					  		size:13,	
					  		type:5,
					} 
					self.loadPaginationto(data);
					 var ids=that.data("id");
						$(".batch").each(function(i,o){
							var a=$(o).text();
							if(a==ids){
								$(o).parent().addClass("danger");
								$(o).parent().siblings().removeClass("danger");
							}
						})
				var dicDiv=$('#addworking');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['80%', '100%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:this.innerHTML,
						  content: dicDiv,
						  
						  yes:function(index, layero){
							 
							},
						  end:function(){
							  $('#addworking').hide();
							  data={
									page:1,
								  	size:13,	
								  	type:5,
								  	name:$('#name').val(),
						  			number:$('#number').val(),
							  }
							
						  }
					});
				})
				//删除
				$('.delete').on('click',function(){
					var postData = {
							id:$(this).data('id'),
					}
					
					var index;
					 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
					$.ajax({
						url:"${ctx}/bacth/deleteBacth",
						data:postData,
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
				        			page:1,
							  		size:13,
							  		type:5,
						  			name:$('#name').val(),
						  			bacthNumber:$('#number').val(),
						  			orderTimeBegin:$("#startTime").val(),
						  			orderTimeEnd:$("#endTime").val(),
						  			flag:0,
						  			status:$('#selectstate').val(),
						  	}
							self.loadPagination(data)
							layer.close(index);
							}else{
								layer.msg("删除失败！", {icon: 1});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					 })
				})
				//修改方法
				$('.updateremaketw').on('click',function(){
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
									remarks:$(this).parent().parent('tr').find(".remarks").text(),
									bacthHairPrice:$(this).parent().parent('tr').find(".bacthHairPrice").text(),
									bacthDeedlePrice:$(this).parent().parent('tr').find(".bacthDeedlePrice").text(),
									allotTime:$(this).parent().parent('tr').find(".allotTime").text(),
							}
							
							var index;
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
									layer.msg("修改成功！", {icon: 1});
									var data={
											page:self.getCount(),
									  		size:13,	
									  		type:5,
									  		flag:0,
									  		status:$('#selectstate').val(),
									} 
								   self.loadPagination(data);
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
				
				//分配1
				$('.addDict').on('click',function(){
					var that=$(this)
					var productId=$(this).data('proid')
					var productName=$(this).data('proname')
					var bacthId=$(this).data('id')
					var bacthNumber=$(this).data('bacthnumber')
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    
				    var htmlth = '';
				    var htmlfr = '';
				    var ids=that.data("id");
					$(".batch").each(function(i,o){
						var a=$(o).text();
						if(a==ids){
							$(o).parent().addClass("danger");
							$(o).parent().siblings().removeClass("danger");
						}
					})
				    //遍历工序类型
				    var getdata={type:"productEightTailor",}
	      			$.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdata,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfr +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			$('.working').html("<select class='form-control selectchang'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlfr+"</select>")
							//改变事件
			      			$(".selectchang").change(function(){
			      				var htmlfv="";
			      				var	id=$(this).val()
			      				self.setNum(id);
			      				if(id==109 || id==""){
			      					$('#dis').css("display","block")
			      				}else{
			      					$('#dis').css("display","none")
			      				}
								   var data={
										   productId:productId,
										   type:5,
										   bacthId:bacthId,
										   procedureTypeId:id,
										   flag:0,
								   }
			      				//查询各个工序的名称
								   $.ajax({
										url:"${ctx}/production/typeToProcedure",
										data:data,
										type:"GET",
										beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										
										success:function(result){
											$(result.data).each(function(i,o){
												htmlfv +='<div class="input-group"><input type="checkbox" class="checkWork" value="'+o.id+'" data-residualnumber="'+o.residualNumber+'">'+o.name+' 剩余:'+o.residualNumber+'</input></div>'
											})
											var s="<div class='input-group'><input type='checkbox' class='checkWorkAll'>全选</input></div>"
											$('.checkworking').html(s+htmlfv);
											$(".checkWorkAll").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.checkWork').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.checkWork').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
											layer.close(index);
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									});
							 })
					      }
					  });
					var data={
							type:5
					}
					//遍历人名组别
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			 $('.complete').html("<select class='form-control selectcomplete'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlth+"</select>") 
							//改变事件
			      			 $(".selectcomplete").change(function(){
			      				var htmltwo = "";
			      				var	id=$(this).val()
								   var data={
										  id:id,
										  type:5,
										 
								   }
			      				$.ajax({
									url:"${ctx}/production/allGroup",
									data:data,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										$(result.data).each(function(i,o){
										
										$(o.users).each(function(i,o){
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBox" value="'+o.id+'" data-username="'+o.userName+'">'+o.userName+'</input></div>'
										})
										})
										var s="<div class='input-group'><input type='checkbox' class='checkall'>全选</input></div>"
										$('.select').html(s+htmltwo)
										$(".checkall").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.stuCheckBox').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.stuCheckBox').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
							 }) 
					      }
					  });
					var postData
					var dicDiv=$('#addDictDivType');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '400px'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  offset:(parent.document.documentElement.scrollTop+20)+'px',
						  title:productName,
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var values=new Array()
							  var numberr=new Array()
								$(".checkWork:checked").each(function() {   
									values.push($(this).val());
									numberr.push($(this).data('residualnumber'));
								}); 
							  var arr=new Array()
							  
								$(".stuCheckBox:checked").each(function() {   
								    arr.push($(this).val());   
								}); 
							  var username=new Array()
							  $(".stuCheckBox:checked").each(function() {   
								  username.push($(this).data('username'));   
								});
							  if(values.length<=0){
									return layer.msg("至少选择一个工序！", {icon: 2});
								}
								if(arr.length<=0){
									return layer.msg("至少选择一个员工！", {icon: 2});
								}
								number=$(".sumnumber").val();
								for (var i = 0; i < numberr.length; i++) {
									if(numberr[i]-number<0){
										return layer.msg("有工序剩余数量不足！", {icon: 2});
									}
								}
								
								var postData = {
										type:5,
										bacthId:that.data("id"),
										procedureIds:values,
										userIds:arr,
										number:number,
										userNames:username,
										productName:productName,
										bacthNumber:bacthNumber,
										allotTime:$('#Time').val(),
										productId:productId,
								}
							    $.ajax({
									url:"${ctx}/task/addTask",
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
										  /* $('.addDictDivTypeForm')[0].reset();  */
										  var htmlfv="";
										  var data={
												   productId:productId,
												   type:5,
												   bacthId:bacthId,
												   procedureTypeId:self.getNum(),
												   flag:0,
										   }
					      				//查询各个工序的名称
										   $.ajax({
												url:"${ctx}/production/typeToProcedure",
												data:data,
												type:"GET",
												beforeSend:function(){
													index = layer.load(1, {
														  shade: [0.1,'#fff'] //0.1透明度的白色背景
														});
												},
												
												success:function(result){
													$(result.data).each(function(i,o){
														htmlfv +='<div class="input-group"><input type="checkbox" class="checkWork" value="'+o.id+'" data-residualnumber="'+o.residualNumber+'">'+o.name+' 剩余:'+o.residualNumber+'</input></div>'
													})
													var s="<div class='input-group'><input type='checkbox' class='checkWorkAll'>全选</input></div>"
													$('.checkworking').html(s+htmlfv);
													$(".checkWorkAll").on('click',function(){
									                    if($(this).is(':checked')){ 
												 			$('.checkWork').each(function(){  
									                    //此处如果用attr，会出现第三次失效的情况  
									                     		$(this).prop("checked",true);
												 			})
									                    }else{
									                    	$('.checkWork').each(function(){ 
									                    		$(this).prop("checked",false);
									                    		
									                    	})
									                    }
									                });
													layer.close(_index);
												},error:function(){
													layer.msg("操作失败！", {icon: 2});
													layer.close(index);
												}
											});
										  $('.stuCheckBox').prop("checked",false);
										  /* $('.select').text(""); */
											layer.msg("添加成功！", {icon: 1});
											
											
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
							  $('.addDictDivTypeForm')[0].reset(); 
							  $("#addDictDivType").hide();
							   $('.checkworking').text(""); 
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
				  			type:5,
				  			name:$('#name').val(),
				  			bacthNumber:$('#number').val(),
				  			 orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  			status:$("#selectstate").val(),
				  			flag:0,
				  	}
		            self.loadPagination(data);
				});
				/* 一键完成  */
				/* $('.start').on('click',function(){
					  var  that=$(".table-condensed table-hover");
					  var arr=new Array()//员工id
					  that.parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var data={
								status:1,
								type:5,
								ids:arr,
								flag:0,
						}
						var _datae={
								status:0,
								type:5,
								ids:arr,
								flag:0,
						}
						var index;
						 index = layer.confirm('确定一键完成吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/bacth/statusBacth",
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
									self.loadPagination(_datae);
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
				  }) */
				  
				  
				  /* 一键删除 */
				$('.attendance').on('click',function(){
					  var  that=$(this);
					  var arr=new Array()//员工id
						$(this).parent().parent().parent().parent().parent().find(".checkboxIdto:checked").each(function() {  
							arr.push($(this).val());   
						});
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var data={
								type:5,
								ids:arr,
						}
						var _data={
								page:1,
						  		size:13,
								bacthId:self.getCache(),
								type:5,
								flag:0,
								status:$("#selectstate").val(),
						}
						var index;
						 index = layer.confirm('确定一键删除吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/task/delete",
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
									self.loadPaginationto(_data);
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
				  
				
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>

</body>

</html>