<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
	<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/layer/layer.js"></script>	
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
	<link rel="stylesheet" href="${ctx }/static/css/main.css">
	<script src="${ctx }/static/js/laypage/laypage.js"></script>
	<script src="${ctx }/static/js/vendor/typeahead.js"></script>
	<script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>员工分组</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />


</head>

<body>
	<section id="main-wrapper" class="theme-default">

		<%-- <%@include file="../../decorator/leftbar.jsp"%> --%>

		<!--main content start-->

		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-body" style="height:750px;">
							<table class="table table-hover">
								<thead>
									<tr>
										<th class="text-center">组名</th>
										<th class="text-center">人员信息</th>
										<th class="text-center">操作</th>
									</tr>
								</thead>
								<tbody id="tablecontent">

								</tbody>
								<button type="button" id="addgroup" class="btn btn-success btn-sm btn-3d pull-right">新增小组</button>
								<tbody id="tablecontenttw">
								<thead>
									<tr>
										<td class="text-center">外调组</td>
										<td class="text-center"><button
												class="btn btn-primary btn-trans btn-sm savemodetw"
												data-toggle="modal" data-target="#myModaltw">查看人员</button></td>
										<td class="text-center"><button type="button" id="add"
												class="btn btn-success btn-sm btn-3d">外调人员</button></td>
									</tr>
								</thead>
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
					<label class="col-sm-3 control-label">名称:</label>
					<div class="col-sm-6">
						<input type="text" id="groupName" class="form-control">
					</div>
				</div>
			</form>
		</div>
	</div>
	<!--隐藏框 小组新增结束  -->

	<!--隐藏框 小组新增开始  -->
	<div id="addDictDivTypetw" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeFormtw">
				<div class="form-group">
					<label class="col-sm-3 control-label">外调时间:</label>
					<div class="col-sm-6">
						<input type="text" id="startTime"
							class="form-control laydate-icon"
							onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD 00:00:00'})">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">是否本厂:</label>
					<div class="col-sm-6">
						<select class="form-control" id="isLocalFactory">
						   <option value='0'>是</option>
						   <option value="1" selected>否</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">人员名称:</label>
					<div class="col-sm-6">
						<input type="text" id="groupNametw" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">工作时长:</label>
					<div class="col-sm-6">
						<input type="text" id="grouptime" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">是否转正</label>
					<div class="col-sm-6">
						<select class="form-control" id="isPositive">
						   <option value='false'>不转正</option>
						   <option value="true">待转正</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">小组:</label>
					<div class="col-sm-6" id="groupp"></div>
				</div>
				<div class="hidden grouptw"></div>
			</form>
		</div>
	</div>
	<!--隐藏框 人员分组详情结束  -->

	<div id="savegroup" style="display: none;">
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
					<div class="row" style="height: 30px; margin: 15px 0 10px">
							<div class="col-xs-11 col-sm-11  col-md-11">
								<form class="form-search">
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12">
											<div class="input-group">
												<table>
													<tr>
														<td>开始时间:</td>
														<td><input id="startTimeTable" placeholder="请输入开始时间"
															class="form-control laydate-icon"
															onClick="laydate({elem: '#startTimeTable', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
														</td>
														<td>&nbsp;&nbsp;</td>
													</tr>
												</table>
												<span class="input-group-btn">
													<button type="button"
														class="btn btn-info btn-square btn-sm btn-3d searchtaskTable">
														查&nbsp;找</button></span>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<table class="table table-hover">
							<thead>
								<tr>
									<th class="text-center">人名</th>
									<th class="text-center">所在组工作时长</th>
								</tr>
							</thead>
							<tbody id="tableUserTime">

							</tbody>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
	<!--隐藏框 人员分组详情结束  -->

	<!--隐藏框 人员分组详情  -->

	<div id="savegrouptw" style="display: none;">
		<div class="modal fade" id="myModaltw" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">人员详情</h4>
					</div>
					<div class="modal-bodytw">
						<div class="row" style="height: 30px; margin: 15px 0 10px">
							<div class="col-xs-11 col-sm-11  col-md-11">
								<form class="form-search">
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12">
											<div class="input-group">
												<table>
													<tr>
														<td>开始时间:</td>
														<td><input id="startTimetw" placeholder="请输入开始时间"
															class="form-control laydate-icon"
															onClick="laydate({elem: '#startTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
														</td>
														<td>&nbsp;&nbsp;</td>
													</tr>
												</table>
												<span class="input-group-btn">
													<button type="button"
														class="btn btn-info btn-square btn-sm btn-3d searchtask">
														查&nbsp;找</button></span>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<table class="table table-hover">
							<thead>
								<tr>
									<th><label> <input type="checkbox"
											class="checkalls" /> <span class="lbl"></span>
									</label></th>
									<th class="text-center">人名</th>
									<th class="text-center">工作时长</th>
									<th class="text-center">小组名</th>
									<th class="text-center">日期</th>
								</tr>
							</thead>
							<tbody id="tablecontentfv">

							</tbody>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" id="delete">删除
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
	<!--人员分组详情 -->
	</section>



	
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
			 var data={
						page:1,
				  		size:13,	
				  		type:1,
				} 
			var selectId; 
			 $('#isLocalFactory').change(function(){		//是否本仓下拉框修改时清空人员名称
				$('#groupNametw').val(''); 
			 });
			 var myDate = new Date(new Date().getTime() - 86400000);
				//获取当前年
				var year=myDate.getFullYear();
				//获取当前月
				var month=myDate.getMonth()+1;
				//获取当前日
				var date=myDate.getDate(); 
				
				var h=myDate.getHours();       //获取当前小时数(0-23)
				var m=myDate.getMinutes();     //获取当前分钟数(0-59)
				var s=myDate.getSeconds(); 
				var day = new Date(year,month,0);  
				var firstdate = year + '-' + '0'+month + '-01'+' '+'00:00:00';
				var getday = year + '-' + '0'+month + date+' '+'00:00:00';
				var lastdate = year + '-' + '0'+month + '-' + day.getDate() +' '+'23:59:59';
				var a=year + '-' + '0'+month + '-' + date+' '+'00:00:00'
				var b=year + '-' + '0'+month + '-' + date+' '+'23:59:59'
				$('#startTimetw').val(a);
				$('#startTimeTable').val(a);
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
				      url:"${ctx}/production/getGroup",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      				html +='<tr>'
		      				+'<td class="text-center edit name">'+o.name+'</td>'
		      				+'<td class="text-center"><button class="btn btn-primary btn-trans btn-sm savemode" data-toggle="modal" data-target="#myModal" data-id="'+o.id+'")">查看人员</button></td>'
							+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans update" data-id='+o.id+'>编辑</button></td></tr>'
							
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
									  		type:1,
									  		name:$('#name').val(),
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
									name:$(this).parent().parent('tr').find(".name").text(),
							}
							
							var index;
							$.ajax({
								url:"${ctx}/production/addGroup",
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
				
				//人员详细显示方法
				$('.savemode').on('click',function(){
					var id=$(this).data('id')
					 var display =$("#savegroup").css("display")
					 if(display=='none'){
							$("#savegroup").css("display","block");  
						}
					selectId=id;
					var postData={
							id:id,
							temporarilyDate:$("#startTimeTable").val()
					}
					self.loadworkingTable(postData);
				})
				
				this.loadworkingTable=function(postData){
					 var arr=new Array();
						var html="";
						$.ajax({
							url:"${ctx}/production/allGroup",
							data:postData,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								$(result.data[0].users).each(function(i,o){
									html +='<tr>'
				      				+'<td class="text-center">'+o.userName+'</td>'
				      				+'<td class="text-center"><input  class="adjustTime" style="background:none;outline:none;border:0px;text-align:center;" data-id="'+o.id+'" data-ajid="'+o.adjustTimeId+'" value='+(o.adjustTime!=null ? o.adjustTime :0)+' /></td>'
								})
								$('#tableUserTime').html(html);
								layer.close(index);
								$(".adjustTime").blur(function(){
									var postData={
											adjustTime:$(this).val(),
											adjustId:$(this).data('ajid'),
										}
									$.ajax({
										url:"${ctx}/production/updateAdjustTime",
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
												layer.msg("修改成功", {icon: 1});
											}else{
												layer.msg(result.message, {icon: 2});
											}
											layer.close(index);
										},error:function(){
											layer.msg(result.message, {icon: 2});
											layer.close(index);
										}
									});
								})
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
					
				}
				
				//人员详细显示方法
				$('.savemodetw').on('click',function(){
					 var display =$("#savegrouptw").css("display")
					 if(display=='none'){
							$("#savegrouptw").css("display","block");  
						}
					var datae={
							
							temporarilyDate:$('#startTimetw').val(),
							type:1,
					}
					self.loadworking(datae);
				})
				
				//删除
							$('#delete').on('click',function(){
								var arr=new Array();
								var that=$(this);
								$(".stuCheckBoxt:checked").each(function() {   
								    arr.push($(this).val()); 
								}); 
								var postData = {
										ids:arr,
								}
								var index;
								 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
								$.ajax({
									url:"${ctx}/production/deleteTemporarily",
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
										$('.savemodetw').click();
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
			}
			this.loadworking=function(datae){
				  var arr=new Array();
					var html="";
					$.ajax({
						url:"${ctx}/production/getTemporarily",
						data:datae, 
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, { 
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html +='<tr><td class="center reste"><label> <input type="checkbox" class="stuCheckBoxt" value="'+o.id+'"/><span class="lbl"></span></label></td>'
			      				+'<td class="text-center  bacthNumber">'+o.user.userName+'</td>'
			      				+'<td class="text-center edit allotTime">'+o.workTime+'</td>'
			      				+'<td class="text-center edit allotTime">'+o.groupName+'</td>'
			      				+'<td class="text-center edit allotTime">'+o.temporarilyDate+'</td></tr>'
							})
							 $('#tablecontentfv').html(html);
							$(".checkalls").on('click',function(){
			                    if($(this).is(':checked')){ 
						 			$('.stuCheckBoxt').each(function(){  
			                    //此处如果用attr，会出现第三次失效的情况  
			                     		$(this).prop("checked",true);
						 			})
			                    }else{
			                    	$('.stuCheckBoxt').each(function(){ 
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
						  area: ['30%', '30%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"新增小组",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							 
							  postData={
									  name:$("#groupName").val(),
									  type:1,
							  }
							  $.ajax({
									url:"${ctx}/production/addGroup",
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
											layer.close(_index);
											//$('#addDictDivType').hide();
											
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
				
				//遍历人名组别
			var htmlth="";
			var data = {
		  			type:1,
		  	}
			var getGroup = function(){
			    $.ajax({
				      url:"${ctx}/production/getGroup",
				      data:data,
				      type:"GET",
		      		  success: function (result) {
		      			  var htmlth = '';
		      			  $(result.data).each(function(k,j){
		      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
		      			  });  
		      			 $('#groupp').html("<select class='form-control selectcomplete'><option value="+""+">请选择</option>"+htmlth+"</select>") 
				      }
				  });
			}
				//外调人员
				$('#add').on('click',function(){
					getGroup();
					var _index
					var index
					var postData
					var dicDiv=$('#addDictDivTypetw');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['30%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"新增人员",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var a;
							  if(self.getCache()==""){
								  a=1
							  }else{
								  a=0
							  }
							  postData={
									  userName:$('#groupNametw').val(),
									  userId:self.getCache(),
									  temporarilyDate:$('#startTime').val(),
									  workTime:$('#grouptime').val(),
									  positive:$('#isPositive').val(),		//新增是否转正
									  type:1,
									  groupId:$('.selectcomplete').val(),
									  foreign:a,
							  }
							  $.ajax({
									url:"${ctx}/production/addTemporarily",
									data:postData,
						            traditional: true,
									type:"post",
									async:false,
									beforeSend:function(){ index = layer.load(1); },
									success:function(result){
										if(0==result.code){
											layer.msg(result.message, {icon: 1});
											$('#addDictDivTypetw').hide();
											layer.close(_index);
										}else{
											layer.msg(result.message, {icon: 2});
										}
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
									}
								});
							    layer.close(index);
							},
						  end:function(){
							  $('#addDictDivTypetw').hide();
							  $('.addDictDivTypeFormtw')[0].reset(); 
							
						  }
					});
				})
				//查询
				$('.searchtask').on('click',function(){
						var datae={
								type:1,
								temporarilyDate:$("#startTimetw").val(),
						}
						self.loadworking(datae);
				});
				$('.searchtaskTable').on('click',function(){
							var postData={
									id:selectId,
									temporarilyDate:$("#startTimeTable").val()
							}
					self.loadworkingTable(postData);
			});
				//提示人员姓名
				$("#groupNametw").typeahead({
					//ajax 拿way数据
					source : function(query, process) {
							return $.ajax({
								url : '${ctx}/system/user/findUserList?foreigns='+$('#isLocalFactory').val(),
								type : 'GET',
								data : {
							  		temporarilyName:$.trim(query),
								},
								success : function(result) {
									
									//转换成 json集合
									 var resultList = result.data.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.userName, id:item.id}
					                        //处理 json对象为字符串
					                         return JSON.stringify(aItem); 
					                    });
									//提示框返回数据
									  if(resultList==""){
											var aItemtw = {"name":"查无此人", "id":""}
											resultList.push(JSON.stringify(aItemtw)); 
										} 
									 return process(resultList);
								},
							})
							//提示框显示
						}, highlighter: function (item) {
						    //转出成json对象
							 var item = JSON.parse(item);
							/*  var name;
						      if(item.id==""){
						    	  
						    	  name=$('#groupNametw').val()
						      }else{
						    	  name=item.name
						      } */
							return item.name
							//按条件匹配输出
		                }, matcher: function (item) {
		                	//转出成json对象
					        var item = JSON.parse(item);
					      /*    $('.product').val(item.name); */
					      var name;
					      if(item.id==""){
					    	  
					    	  name=$('#groupNametw').val()
					      }else{
					    	  name=item.name
					      }
					      self.setCache(item.id); 
					    	 return  name;
					    },
						//item是选中的数据
						 updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
							 var name;
						      if(item.id==""){
						    	  
						    	  name=$('#groupNametw').val()
						      }else{
						    	  name=item.name
						      }
							self.setCache(item.id);
								return name
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