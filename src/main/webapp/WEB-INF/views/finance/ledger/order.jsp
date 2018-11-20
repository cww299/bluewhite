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
    <title>订单管理</title>
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
                                <h3 class="panel-title">订单信息</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">当月销售编号</th>
                                            <th class="text-center">合同签订日期</th>
                                            <th class="text-center">甲方</th>
                                            <th class="text-center">乙方</th>
                                            <th class="text-center">当批批次号</th>
                                            <th class="text-center">当批计划单号</th>
                                            <th class="text-center">当批产品名</th>
                                            <th class="text-center">当批合同数量</th>
                                            <th class="text-center">当批合同总价（元）</th>
                                            <th class="text-center">预付款备注</th>
                                            <th class="text-center">手动填写当批单只价格</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                    <thead>
                                        <tr>
                                        	<td class="text-center"></td>
                                            <td class="text-center" style="padding: 9px 0px 2px 4px;"><input id="contractTime" placeholder="请输入时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#contractTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" style="border: none;width:90px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="aName" style="border: none;width:68px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="bName" style="border: none;width:68px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="batchNumber" style="border: none;width:105px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="planNumbers" placeholder="可不填" style="border: none;width:60px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="ProductName" style="border: none;width:150px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="contractNumber" style="border: none;width:50px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"></td>
                                            <td class="text-center"><input type="text" id="remarksPrice" placeholder="可不填" style="border: none;width:80px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"></td>
                                            <td class="text-center"><button type="button" id="addgroup" class="btn btn-success btn-sm btn-3d pull-right">新增订单</button></td>
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
        <!-- <div id="addDictDivType" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeForm">
				<div class="form-group">
                                        <label class="col-sm-3 control-label">合同签订日期:</label>
                                        <div class="col-sm-6">
                                            <input id="contractTime" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#contractTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">甲方:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="aName" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">乙方:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="bName" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">当批批次号:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="batchNumber" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">当批产品名:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="ProductName" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">当批合同数量:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="contractNumber" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">预付款备注:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="remarksPrice" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">当批计划单号:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="planNumbers" class="form-control">
                                        </div>
                 </div>
				</form>
</div>
</div> -->
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
				      url:"${ctx}/fince/getOrder",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				var newDate=/\d{4}-\d{1,2}-\d{1,2}/g.exec(o.contractTime)
		      				html +='<tr>'
		      				+'<td class="text-center edit name">'+o.salesNumber+'</td>'
		      				+'<td class="text-center edit name">'+newDate+'</td>'
		      				+'<td class="text-center edit name">'+o.firstNames+'</td>'
		      				+'<td class="text-center edit name">'+o.partyNames+'</td>'
		      				+'<td class="text-center edit name">'+o.batchNumber+'</td>'
		      				+'<td class="text-center edit name">'+o.planNumbers+'</td>'
		      				+'<td class="text-center edit name">'+o.productName+'</td>'
		      				+'<td class="text-center edit name">'+o.contractNumber+'</td>'
		      				+'<td class="text-center edit name">'+o.contractPrice+'</td>'
		      				+'<td class="text-center edit name">'+o.remarksPrice+'</td>'
		      				+'<td class="text-center edit name">'+o.price+'</td>'
		      				+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans update" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>'
							
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
									  		type:5,
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
					var postData={
							id:id,
					}
					 var arr=new Array();
					var html="";
					$.ajax({
						url:"${ctx}/production/getGroupOne",
						data:postData,
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data.users).each(function(i,o){
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
				
				
				//删除
							$('.delete').on('click',function(){
								var postData = {
										ids:$(this).data('id'),
								}
								var index;
								 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
								$.ajax({
									url:"${ctx}/production/group/delete",
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
												type:5,
										}
										self.loadPagination(_data)
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
			this.selected=function(){
				
				$('.selectgroupChange').each(function(i,o){
					var id=$(o).parent().data("groupid");
					$(o).val(id);
				})
				
			}
			this.chang=function(){
				$('.selectgroupChange').change(function(){
					var that=$(this);
					var data={
							id:that.parent().data("id"),
							kindWorkId:that.val(),
						}
					var _data={
							page:1,
					  		size:10,	
					} 
					$.ajax({
						url:"${ctx}/production/addGroup",
						data:data,
						type:"POST",
						success:function(result){
							if(0==result.code){
								layer.msg("分配工种成功！", {icon: 1});
								
							}else{
								layer.msg("分配工种失败", {icon: 2});			
							}
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
						}
					})
					
					
				})
			}
			/* this.matertw=function(){
				//提示人员姓名
				
					
				
				 $(".leadertw").typeahead({
					//ajax 拿way数据
					
					source : function(query, process) {
							return $.ajax({
								url : '${ctx}/system/user/pages',
								type : 'GET',
								data : {
									userName:query
								},
								success : function(result) {
									alert(1)
									//转换成 json集合
									 var resultList = result.data.rows.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.userName, id:item.id}
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
							return item.name
							//按条件匹配输出
		                }, matcher: function (item) {
		                	//转出成json对象
					        var item = JSON.parse(item);
					        self.setIndex(item.id);
					        self.setName(item.name);
					    	return item.id
					    },
						//item是选中的数据
						updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
							self.setIndex(item.id);
						  	self.setName(item.name);
								return item.name
						},

						
					}); 
			} */
			
			this.mater=function(){
				//提示人员姓名
				$("#leader").typeahead({
					//ajax 拿way数据
					source : function(query, process) {
							return $.ajax({
								url : '${ctx}/system/user/pages',
								type : 'GET',
								data : {
									userName:query
								},
								success : function(result) {
									//转换成 json集合
									 var resultList = result.data.rows.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.userName, id:item.id}
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
							return item.name
							//按条件匹配输出
		                }, matcher: function (item) {
		                	//转出成json对象
					        var item = JSON.parse(item);
					        self.setIndex(item.id);
					        self.setName(item.name);
					    	return item.id
					    },
						//item是选中的数据
						updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
							self.setIndex(item.id);
						  	self.setName(item.name);
								return item.name
						},

						
					});
			}
			this.events = function(){
				//新增小组
				$('#addgroup').on('click',function(){
					self.mater();
					var _index;
					var index;
					var postData;
					if($("#contractTime").val()==""){
					return	layer.msg("请填写合同日期", {icon: 2});
					}
					if($("#aName").val()==""){
						return	layer.msg("请填写甲方", {icon: 2});
					}
					if($("#bName").val()==""){
						return	layer.msg("请填写乙方", {icon: 2});
					}
					if($("#batchNumber").val()==""){
						return layer.msg("请填写批次号", {icon: 2});
					}
					if($("#ProductName").val()==""){
						return layer.msg("请填写产品名", {icon: 2});
					}
					if($("#contractNumber").val()==""){
						return layer.msg("请填写当批合同数量", {icon: 2});
					}
					  postData={
							  contractTime:$("#contractTime").val(),
							  firstNames:$("#aName").val(),
							  partyNames:$("#bName").val(),
							  batchNumber:$("#batchNumber").val(),
							  planNumbers:$("#planNumbers").val(),
							  productName:$("#ProductName").val(),
							  contractNumber:$("#contractNumber").val(),
							  remarksPrice:$("#remarksPrice").val(),
					  }
					  $.ajax({
							url:"${ctx}/fince/addOrder",
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
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>
       
</body>

</html>