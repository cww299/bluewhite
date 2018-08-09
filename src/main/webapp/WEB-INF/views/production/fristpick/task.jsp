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
    <title>任务管理</title>
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
                                <h3 class="panel-title">任务信息</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                  <!--查询开始  -->
          <div class="row" style="height: 30px; margin:15px 0 10px">
			<div class="col-xs-11 col-sm-11 col-md-11">
				<form class="form-search" >
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr><td>批次号:</td><td><input type="text" name="number" id="number" placeholder="请输入批次号" class="form-control search-query number" /></td>
								<td>&nbsp&nbsp</td>
								<td>产品名称:</td><td><input type="text" name="name" id="name" placeholder="请输入产品名称" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp</td>
								<td>开始时间:</td>
								<td>
								<input id="startTime" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
				<td>&nbsp&nbsp</td>
				<td>结束时间:</td>
				<td>
					<input id="endTime" placeholder="请输入结束时间" class="form-control laydate-icon"
            					 onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</td>
								<td>&nbsp&nbsp</td>
							<td>工序:</td><td><select class="form-control selectchoice" ><option value="">请选择</option><option value="0">包装工序</option><option value="1">返工工序</option></select></td>
								</tr></table> 
								<span class="input-group-btn"><button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask">查&nbsp找</button></span>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn"><button type="button" class="btn btn-default btn-danger btn-sm btn-3d attendance">一键删除</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
									<button type="button" class="btn btn-success  btn-sm btn-3d addDict">
									加绩
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->    
            <h1 class="page-header"></h1>
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
                                        	<th class="text-center">批次号</th>
                                            <th class="text-center">产品名</th>
                                            <th class="text-center">时间</th>
                                            <th class="text-center">工序</th>
                                            <th class="text-center">预计时间</th>
                                            <th class="text-center">任务价值</th>
                                            <th class="text-center">b工资净值</th>
                                            <th class="text-center">数量</th>
                                            <th class="text-center">工序加价</th>
                                            <th class="text-center">加绩工资</th>
                                            <th class="text-center">完成人</th>
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

 <!--隐藏框 工序加绩分配开始  -->
        <div id="addDictDivTypetw" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12 ">
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">加绩工序:</label>
                              <div class="col-sm-3 workingtw">
                              </div> 
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
</div>
<!--隐藏框 工序加绩分配结束  -->



<!--隐藏框 查看加价开始  -->
<div id="savegrouptw" style="display: none;">
<div class="modal fade" id="myModaltw" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					查看加价
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
<!--隐藏框 查看加价结束  -->

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
				  		type:2,

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
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center ">'+o.bacthNumber+'</td>'
		      				+'<td class="text-center ">'+o.productName+'</td>'
		      				+'<td class="text-center edit allotTime">'+o.allotTime+'</td>'
		      				+'<td class="text-center ">'+s+a+'</td>'
		      				+'<td class="text-center ">'+parseFloat((o.expectTime).toFixed(4))+'</td>'
		      				+'<td class="text-center ">'+parseFloat((o.taskPrice).toFixed(4))+'</td>'
		      				+'<td class="text-center ">'+parseFloat((o.payB).toFixed(4))+'</td>'
		      				+'<td class="text-center edit number">'+o.number+'</td>'
		      				+'<td class="text-center "><button class="btn btn-primary btn-trans btn-sm savemodePerformance" data-toggle="modal" data-target="#myModaltw" data-id="'+o.id+'")">查看加价</button></td>'
		      				+'<td class="text-center ">'+parseFloat((o.performancePrice).toFixed(4))+'</td>'
		      				+'<td class="text-center"><button class="btn btn-primary btn-trans btn-sm savemode" data-toggle="modal" data-target="#myModal" data-id="'+o.id+'")">查看人员</button></td>'
							+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>'
							
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
									  		type:2,
									  		productName:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			flag:$('.selectchoice').val(),
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
			
			this.loadEvents = function(){
				//删除
				$('.delete').on('click',function(){
							var postData = {
									ids:$(this).data('id'),
							}
							var that=$(this);
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
									var _data = {
						        			page:self.getCount(),
									  		size:13,
									  		type:2,
									  		productName:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			flag:$('.selectchoice').val(),
								  	}
									self.loadPagination(_data);
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
									allotTime:$(this).parent().parent('tr').find(".allotTime").text(),
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
									layer.msg(result.message, {icon: 1});
									var _data = {
						        			page:self.getCount(),
									  		size:13,
									  		type:2,
									  		productName:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			flag:$('.selectchoice').val(),
								  	}
									self.loadPagination(_data);
									layer.close(index);
									}else{
										layer.msg(result.message, {icon: 1});
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
					 var postData={
							id:id,
					}
					 var arr=new Array();
					var html="";
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
							html+=o.userName+"&nbsp&nbsp&nbsp&nbsp"
							})
							$('.modal-body').html(html);
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					}); 
					
					
					
				})
				
				//查看加价
				$('.savemodePerformance').on('click',function(){
					var id=$(this).data('id')
					 var display =$("#savegrouptw").css("display")
					 if(display=='none'){
							$("#savegrouptw").css("display","block");  
						}
					 var postData={
							id:id,
					}
					 var arr=new Array();
					var html="";
					$.ajax({
						url:"${ctx}/task/getUserPerformance",
						data:postData,
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+=o.performance+":"+o.username+"&nbsp&nbsp&nbsp&nbsp"
							})
							$('.modal-body').html(html);
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					}); 
					
					
					
				})
				
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
			this.events = function(){
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:2,
				  			productName:$('#name').val(),
				  			bacthNumber:$('#number').val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  			flag:$('.selectchoice').val(),
				  	}
		            self.loadPagination(data);
				});
				
				/* 一键删除 */
				$('.attendance').on('click',function(){
					  var  that=$(".table-hover");
					  var arr=new Array()//员工id
					  that.parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var data={
								type:2,
								ids:arr,
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
									var _data = {
						        			page:self.getCount(),
									  		size:13,
									  		type:2,
									  		productName:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			flag:$('.selectchoice').val(),
								  	}
									self.loadPagination(_data);
									layer.close(index);
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
				
				
				//分配1
				$('.addDict').on('click',function(){
					var  thae=$(".table-hover");
					var arr=""//员工id
					var CheckCount=0;
					  thae.parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
						  CheckCount++;
						  arr=$(this).val();  
						});
					  if(CheckCount>1){
							return layer.msg("只能选择一条任务", {icon: 2});
						}
					if(arr==""){
						return layer.msg("请选择一条任务", {icon: 2});
					}
					var that=$(this)
					var productName=$(this).data('proname')
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    
				    var htmlth = '';
				    var htmlfr = '';
					//遍历人名组别
			      			  $('.complete').html("<select class='form-control selectcomplete'><option value="+0+">请选择</option><option value="+""+">全部</option></select>")
							//改变事件
			      			 $(".selectcomplete").change(function(){
			      				var htmltwo = "";
			      				var	id=$(this).val()
								   var data={
										  id:arr,
								   }
			      				$.ajax({
									url:"${ctx}/task/taskUser",
									data:data,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										$(result.data).each(function(i,o){
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBox" value="'+o.id+'" data-username="'+o.userName+'">'+o.userName+'</input></div>'
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
					//遍历杂工加绩比值
					var html=""
					$.ajax({
						url:"${ctx}/task/pickTaskPerformance",
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+='<option value="'+o.number+'" data-name="'+o.name+'">'+o.name+'</option>'
							})
							$('.workingtw').html("<select class='form-control selectchangtw'><option value='0'></option>"+html+"</select>");
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
				    
					var postData
					var dicDiv=$('#addDictDivTypetw');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:productName,
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var arry=new Array()
								$(".stuCheckBox:checked").each(function() {   
								    arry.push($(this).val());   
								}); 
								if(arr.length<=0){
									return layer.msg("至少选择一个员工！", {icon: 2});
								}
								var performanceNumber=$(".selectchangtw").val();
								
								var performance=$(".selectchangtw option:selected").text();
								var postData = {
										id:arr,
										ids:arry,
										performanceNumber:performanceNumber,
										performance:performance,
								}
							    $.ajax({
									url:"${ctx}/task/giveTaskPerformance",
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
										  $('.addDictDivTypeFormtw')[0].reset(); 
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
							  $('.addDictDivTypeFormtw')[0].reset(); 
							  $("#addDictDivTypetw").hide();
							  $('.select').text("");
							  var date={
										page:self.getCount(),
								  		size:13,	
								  		type:2,
								} 
							   self.loadPagination(date);
							
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