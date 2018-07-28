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
    <title>杂工管理</title>
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
                                <h3 class="panel-title">返工详情</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                             <!--查询开始  -->
          <div class="row" style="height: 30px; margin:15px 0 10px">
			<div class="col-xs-10 col-sm-10 col-md-10">
				<form class="form-search" >
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr><td>批次名:</td><td><input type="text" name="number" id="number" placeholder="请输入批次号" class="form-control search-query number" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>产品名称:</td><td><input type="text" name="name" id="name" placeholder="请输入产品名称" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>开始时间:</td>
								<td>
								<input id="startTime" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
				<td>&nbsp&nbsp&nbsp&nbsp</td>
				<td>结束时间:</td>
				<td>
					<input id="endTime" placeholder="请输入结束时间" class="form-control laydate-icon"
             onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask">
										查&nbsp找
									</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
									<button type="button" id="addgroup" class="btn btn-success btn-sm btn-3d pull-right">新增返工</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
									<button type="button" class="btn btn-success  btn-sm btn-3d export">
									导出返工价值
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
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
                                            <th class="text-center">时间</th>
                                            <th class="text-center">产品名</th>
                                            <th class="text-center">数量</th>
                                            <th class="text-center">任务价值</th>
                                            <th class="text-center">当批用时</th>
                                            <th class="text-center">备注</th>
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
				<button type="button" class="btn btn-info" data-dismiss="modal">关闭
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</div>
<!--隐藏框 产品新增结束  -->
<!--隐藏框 新增杂工开始  -->
        <div id="addDictDivType" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeForm">
					<div class="row col-xs-12  col-sm-12  col-md-12 ">
					<div class="form-group">
                                        <label class="col-sm-3 control-label">日期:</label>
                                        <div class="col-sm-6">
                                            <input id="Time" placeholder="时间可不填" class="form-control laydate-icon"
             					onClick="laydate({elem: '#Time', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                        </div>
                 </div>
                 
                	 <div class="form-group">
						
                           <label class="col-sm-3 control-label">批次名:</label>
                              <div class="col-sm-6">
                                  <input type="text" class="form-control bacth">
                              </div>
                    	</div>
                    	<div class="form-group">
						
                           <label class="col-sm-3 control-label">产品名:</label>
                              <div class="col-sm-6">
                                  <input type="text" class="form-control productName">
                              </div>
                    	</div>
						<div class="form-group">
						
                           <label class="col-sm-3 control-label">数量:</label>
                              <div class="col-sm-6">
                                  <input type="text" class="form-control sumnumber">
                              </div>
                    	</div>
                    	
                    	
                    	<div class="form-group">
                            <label class="col-sm-3 control-label">备注:</label>
                                <div class="col-sm-6">
                                  <input type="text" class="form-control remarks">
                                </div>
                    	</div>
                 </div>
				</div>

				</form>
</div>
</div>
<!--隐藏框 新增结束  -->
 <!--隐藏框 工序分配开始  -->
        <div id="addDictDivTypetw" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12 ">
		
                 
						<div class="form-group">
                           <label class="col-sm-2 col-md-2 control-label">任务数量:</label>
                              <div class="col-sm-2 col-md-2">
                                  <input type="text" class="form-control sumnumbertw">
                              </div>
                               <label class="col-sm-3 control-label">任务分配:</label>
                                 <div class="col-sm-2">
                                          <input id="Timetw" placeholder="时间可不填" class="form-control laydate-icon"
           									onClick="laydate({elem: '#Timetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                      </div>
                            
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 col-md-2 control-label">任务时间:</label>
                              <div class="col-sm-2 col-md-2">
                                  <input type="text" class="form-control timestart">
                              </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">选择工序:</label>
                              <div class="col-sm-2 working">
                              </div>
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
<!-- 任务详情开始-->
<div id="addworking" style="display: none;position: absolute;z-index: 3;">
<table><tr>           
                        <td><button type="button" class="btn btn-default btn-danger btn-xs btn-3d attendance">一键删除</button>&nbsp&nbsp</td>
                        <td><button type="button" class="btn btn-info  btn-xs btn-3d startto">一键开始</button>&nbsp&nbsp</td>
                        <td><button type="button" class="btn btn-default btn-success btn-xs btn-3d suspend">一键暂停</button>&nbsp&nbsp</td>
                        </tr></table>             
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="center">
											<label> 
											<input type="checkbox" class="ace checksto" /> 
											<span class="lbl"></span>
											</label>
											</th>
											<th class="text-center">任务编号</th>
                                        	<th class="text-center">批次号</th>
                                            <th class="text-center">产品名</th>
                                            <th class="text-center">时间</th>
                                            <th class="text-center">工序</th>
                                            <th class="text-center">预计时间</th>
                                            <th class="text-center">任务价值</th>
                                            <th class="text-center">b工资净值</th>
                                            <th class="text-center">数量</th>
                                            <th class="text-center">开始结束</th>
                                            <th class="text-center">实际用时</th>
                                            <th class="text-center">完成人</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontentto">
                                        
                                    </tbody>
                                </table>
                                <div id="pager" class="pull-right">
                                
                                </div>
                            </div>

</div>
<!-- 任务详情结束-->
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
		  	this.setIndex = function(index){
		  		_index=index;
		  	}
		  	
		  	this.getIndex = function(){
		  		return _index;
		  	}
		  	this.setName = function(name){
		  		_name=name;
		  	}
		  	this.getName = function(){
		  		return _name;
		  	}
			 var data={
						page:1,
				  		size:13,	
				  		type:3,
				  		flag:1,

				} 
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
			}
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
			      			  
			      			 $(result.data.rows).each(function(i,o){
			      				
			      				if(o.time==null){
			      					o.time=0;
			      				 }
			      				 var strname="";
			      				 if(o.status==1){
			      					strname="完成";
			      				 }else{
			      					strname="未完成";
			      				 }
			      				 html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
			      				+'<td class="hidden batch">'+o.id+'</td>'
			      				+'<td class="text-center edit bacthNumber">'+o.bacthNumber+'</td>'
			      				+'<td class="text-center  allotTime">'+o.allotTime+'</td>'
			      				+'<td class="text-center  names" data-id='+o.id+'>'+o.product.name+'</td>'
			      				+'<td class="text-center edit number">'+o.number+'</td>'
			      				+'<td class="text-center  sumTaskPrice">'+ parseFloat((o.sumTaskPrice*1).toFixed(3))+'</td>'
			      				+'<td class="text-center ">'+o.time+'</td>'
			      				+'<td class="text-center edit remarks">'+o.remarks+'</td>'
								+'<td class="text-center"><button class="btn btn-sm btn-primary btn-trans addDict" data-id='+o.id+' data-proid='+o.product.id+' data-bacthnumber='+o.bacthNumber+' data-proname='+o.product.name+'>分配</button>  <button class="btn btn-sm btn-info  btn-trans updateremaketw" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>' 
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
										  		type:3,
									  			name:$('#name').val(),
									  			bacthNumber:$('#number').val(),
									  			orderTimeBegin:$("#startTime").val(),
									  			orderTimeEnd:$("#endTime").val(),
									  			flag:1,
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
			      				+'<td class="text-center name">'+o.bacthNumber+'</td>'
			      				+'<td class="text-center name">'+o.productName+'</td>'
			      				+'<td class="text-center  name">'+o.allotTime+'</td>'
			      				+'<td class="text-center  name">'+s+a+'</td>'
			      				+'<td class="text-center  name">'+parseFloat((o.expectTime).toFixed(4))+'</td>'
			      				+'<td class="text-center  name">'+parseFloat((o.taskPrice).toFixed(4))+'</td>'
			      				+'<td class="text-center  name">'+parseFloat((o.payB).toFixed(4))+'</td>'
			      				+'<td class="text-center edit number">'+o.number+'</td>'
			      				+'<td class="text-center" data-id="'+o.id+'" data-status="'+o.status+'"><input type="radio"  class="rest" value="0">开始<input type="radio" class="rest" value="1">暂停</td>'
			      				+'<td class="text-center edit name">'+o.taskActualTime+'</td>'
			      				+'<td class="text-center"><button class="btn btn-primary btn-trans btn-sm savemode" data-toggle="modal" data-target="#myModal" data-id="'+o.id+'")">查看人员</button></td>'
								+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>'
								
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
										  		type:3,
										  		productName:$('#name').val(),
									  			bacthNumber:$('#number').val(),
									  			orderTimeBegin:$("#startTime").val(),
									  			orderTimeEnd:$("#endTime").val(),
									  			flag:0,
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
							$('.delete').on('click',function(){
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
												type:3,
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
					  		type:3,
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
						  area: ['70%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:name,
						  content: dicDiv,
						  
						  yes:function(index, layero){
							 
							},
						  end:function(){
							  $('#addworking').hide();
							  data={
									page:1,
								  	size:13,	
								  	type:3,
								  	name:$('#name').val(),
						  			number:$('#number').val(),
							  }
							
						  }
					});
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
									bacthNumber:$(this).parent().parent('tr').find(".bacthNumber").text(),
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
				//删除方法
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
				//分配
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
				    var getdata={type:"productTwoReDeedle",}
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
			      				if(id==109 || id==""){
			      					$('#dis').css("display","block")
			      				}else{
			      					$('#dis').css("display","none")
			      				}
								   var data={
										   productId:productId,
										   type:3,
										   bacthId:bacthId,
										   procedureTypeId:id,
										   flag:1,
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
							type:3
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
										  type:3,
										 
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
								number=$(".sumnumbertw").val();
								for (var i = 0; i < numberr.length; i++) {
									if(numberr[i]-number<0){
										return layer.msg("有工序剩余数量不足！", {icon: 2});
									}
								}
								
								var postData = {
										type:3,
										bacthId:that.data("id"),
										procedureIds:values,
										userIds:arr,
										number:number,
										userNames:username,
										productName:productName,
										bacthNumber:bacthNumber,
										allotTime:$('#Timetw').val(),
										taskTime:$('.timestart').val(),
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
										  $('.addDictDivTypeFormtw')[0].reset(); 
										$('.checkworking').text("");
										  $('.select').text("");
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
						
							
						  } 
					});
					
					
				})
				//导出返工价值
					$('.export').on('click',function(){
						var index; 
						var a=$("#startTime").val();
						var c= $("#endTime").val();
						location.href="${ctx}/excel/importExcel?startTime="+a+"&endTime="+c;
					})
				
			}
			this.mater=function(){
				//提示
				$(".bacth").typeahead({
					//ajax 拿way数据
					source : function(query, process) {
							return $.ajax({
								url : '${ctx}/bacth/allBacth',
								type : 'GET',
								data : {
									bacthNumber:query,
									type:3,
									flag:0,
								},
								success : function(result) {
									//转换成 json集合
									 var resultList = result.data.rows.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.bacthNumber, id:item.product.id,proname:item.product.name,number:item.number}
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
							return item.name+"-"+item.proname
							//按条件匹配输出
		                }, matcher: function (item) {
		                	//转出成json对象
					        var item = JSON.parse(item);
					        self.setIndex(item.id);
					        self.setName(item.name);
					        $('.sumnumber').val(item.number);
					        $('.productName').val(item.proname);
					    	return item.name
					    },
						//item是选中的数据
						updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
							self.setIndex(item.id);
						  	self.setName(item.name);
						  	$('.sumnumber').val(item.number);
						  	$('.productName').val(item.proname);
								return item.name
						},

						
					});
			}
			this.events = function(){
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
								type:3,
								ids:arr,
						}
						var _data={
								page:1,
						  		size:13,
								bacthId:self.getCache(),
								type:3,
								
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
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:3,
				  			name:$('#name').val(),
				  			bacth:$('#number').val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  			flag:1,
				  	}
		            self.loadPagination(data);
				});
				//新增返工
				$('#addgroup').on('click',function(){
					self.mater();
					var _index
					var index
					var postData
					var dicDiv=$('#addDictDivType');
					var html=""
					var htmlth=""
					var data={
							type:3
							
					}
					
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['30%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"新增返工工",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  if($(".sumnumber").val()==""){
									 return layer.msg("数量不能为空", {icon:2 });
								  }
							  if($(".bacth").val()==""){
									 return layer.msg("批次号不能为空", {icon:2 });
								  }
							  postData={
									  productId:self.getIndex(),
									  bacthNumber:self.getName(),
									  allotTime:$("#Time").val(),
									  number:$(".sumnumber").val(),
									  remarks:$(".remarks").val(),
									  bacth:$(".bacth").val(),
									  type:3,
									  flag:1,
							  }
							  var _data={
										page:1,
								  		size:13,	
								  		type:3,
								  		flag:1,

								} 
							  $.ajax({
									url:"${ctx}/bacth/addBacth",
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
										 self.loadPagination(_data); 
										 $('.addDictDivTypeForm')[0].reset();
											
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