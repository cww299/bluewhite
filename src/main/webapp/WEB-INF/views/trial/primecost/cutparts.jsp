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
    <title>员工分组</title>
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
								<td>默认耗损:</td><td><input type="text" name="name" id="name" placeholder="请输入产品名称" class="form-control search-query name" /></td>
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
									新增裁片
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
                                        	<th class="text-center">裁片名</th>
                                            <th class="text-center">片数</th>
                                            <th class="text-center">编号</th>
                                            <th class="text-center">名称</th>
                                            <th class="text-center">是否复合</th>
                                            <th class="text-center">单片用料</th>
                                            <th class="text-center">单位</th>
                                            <th class="text-center">全套比用料</th>
                                            <th class="text-center">单只用料</th>
                                            <th class="text-center">耗损</th>
                                            <th class="text-center">产品单价</th>
                                            <th class="text-center">产品单位</th>
                                            <th class="text-center">单片用料</th>
                                            <th class="text-center">单片价格</th>
                                            <th class="text-center">复合物料编号</th>
                                            <th class="text-center">复合物料名称</th>
                                            <th class="text-center">是否双层对复</th>
                                            <th class="text-center">复合物单价</th>
                                            <th class="text-center">复合物备注</th>
                                            <th class="text-center">复合物料耗损比</th>
                                            <th class="text-center">复合物用料</th>
                                            <th class="text-center">复合单片价格</th>
                                            <th class="text-center">复合加工费价格</th>
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
				      url:"${ctx}/product/getCutParts",
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
		      				+'<td class="text-center edit name" contentEditable="true">'+o.cutPartsName+'</td>'
		      				+'<td class="text-center edit name" contentEditable="true">'+o.cutPartsNumber+'</td>'
							+'<td class="text-center edit" contentEditable="true"><button class="btn btn-sm btn-info  btn-trans update" data-id='+o.id+'>编辑</button></td></tr>'
							
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
				
			} 
			this.mater=function(){
				//提示裁片名
				$(".cuttingName").typeahead({
					//ajax 拿way数据
					source : function(query, process) {
							return $.ajax({
								url : '${ctx}/product/getBaseOne',
								type : 'GET',
								data : {
									name:query,
									type:"cutParts",
								},
								success : function(result) {
									//转换成 json集合
									 var resultList = result.data.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.name, id:item.id}
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
					    	return item.name
					    },
						//item是选中的数据
						updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
								return item.name
						},
					});
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
		      				html +='<option value="'+o.name+'">'+o.name+'</option>'
		      			}); 
				       var htmlto='<select class="  selectgroupChange" style="border: none;width:50px; height:30px; background-color: #BFBFBF;"><option value=""></option>'+html+'</select>'
					   	$(".selectCompany").html(htmlto); 
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
				
				
			
			}
			this.events = function(){
				/*保存  */
				$('#save').on('click',function(){
					
					var leng = $(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').length;
				for (var i = 0; i <leng; i++) {
					var postData = {
						productId:6017,
						number:9000,
						cutPartsName:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.cuttingName').val(),
						cutPartsNumber:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.sliceNumber').val(),
						materielNumber:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.materielNumber').text(),
						materielName:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.materiel').val(),
						composite:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.selectname').val(),
						oneMaterial:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.oneMaterial').val(),
						unit:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.selectgroupChange').val(),
						manualLoss:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.manualLoss').val(),
						productCost:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.unitPrice').text(),
						productRemark:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.unit').text(),
						complexMaterielName:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.complexMateriel').val(),
						complexMaterielNumber:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.complexMaterielNumber').text(),
						bilayer:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.bilayer').val(),
						complexProductCost:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.unitPricetw').text(),			
						complexProductRemark:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.unittw').text(),
						compositeManualLoss:$(this).parent().parent().parent().parent().parent().parent().parent().next().find('#tablecontent tr').eq(i).find('.compositeManualLoss').val(),
					}
					var index;
					$.ajax({
						url:"${ctx}/product/addCutParts",
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
							/* var _data={
									page:1,
							  		size:13,
							}
							self.loadPaginationto(_data) */
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
								url : '${ctx}/productPages',
								type : 'GET',
								data : {
									name:query,
								},
								
								success : function(result) {
									//转换成 json集合
									 var resultList = result.data.rows.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.name, id:item.id}
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
					    	return item.name
					    },
						//item是选中的数据
						updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
								return item.name
						},
						
					});
				
				//新增裁片
					
					var html="";
				$('#addCutting').on('click',function(){
					
					 html='<tr><td  style="padding: 2px 0px 2px 4px;"><input type="text" style="border: none;width:68px; height:30px; background-color: #BFBFBF;" data-provide="typeahead" autocomplete="off" class="text-center  cuttingName" /></td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><input type="text" style="border: none;width:40px; height:30px; background-color: #BFBFBF;" class="text-center sliceNumber" /></td>'
					 +'<td class="text-center edit materielNumber " ></td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><input type="text"    style="border: none;width:120px; height:30px; background-color: #BFBFBF;" class="text-center   materiel"  /></td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><select class="text-center  selectname" style="border: none;width:60px; height:30px; background-color: #BFBFBF;"><option value="0"></option><option value="1">复</option></select></td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><input type="text" style="border: none;width:60px; height:30px; background-color: #BFBFBF;" class="text-center oneMaterial" /></td>'
					 +'<td class="text-center edit selectCompany" style="padding: 2px 0px 2px 0px;></td>'
					 +'<td class="text-center edit name"></td>'
					 +'<td class="text-center edit name"></td>'
					 +'<td class="text-center edit name"</td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><input type="text"  style="border: none;width:40px; height:30px; background-color: #BFBFBF;" class="text-center manualLoss" /></td>'
					 +'<td class="text-center edit unitPrice" ></td>'
					 +'<td class="text-center edit unit"></td>'
					 +'<td class="text-center edit name"> </td>'
					 +'<td class="text-center edit name"> </td>'
					 +'<td class="text-center edit complexMaterielNumber"></td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><input type="text"    style="border: none;width:120px; height:30px; background-color: #BFBFBF;" class="text-center   complexMateriel"  /></td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><select class="text-center bilayer" style="border: none;width:90px; height:30px; background-color: #BFBFBF;"><option value="0"></option><option value="1">面料对复合</option></select></td>'
					 +'<td class="text-center edit unitPricetw" ></td>'
					 +'<td class="text-center edit unittw" ></td>'
					 +'<td class="text-center edit name" style="padding: 2px 0px 2px 0px;"><input type="text"  style="border: none;width:40px; height:30px; background-color: #BFBFBF;" class="text-center compositeManualLoss" /></td>'
					 +'<td class="text-center edit name"> </td>'
					 +'<td class="text-center edit name"> </td>'
					 +'<td class="text-center edit name"> </td></tr>';
					$("#tablecontent").append(html);
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
										type:"material",
									},
									success : function(result) {
										//转换成 json集合
										 var resultList = result.data.map(function (item) {
											 	//转换成 json对象
						                        var aItem = {name: item.name, number:item.number, price:item.price, unit:item.unit}
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
						        that.parent().prev().text(item.number);
						        that.parent().parent().find('.unitPrice').text(item.price);
						        that.parent().parent().find('.unit').text(item.unit);
						    	return item.name
						    },
							//item是选中的数据
							updater:function(item){
								//转出成json对象
								var item = JSON.parse(item);
								that.parent().prev().text(item.number);
								that.parent().parent().find('.unitPrice').text(item.price);
								that.parent().parent().find('.unit').text(item.unit);
									return item.name
							},
							
						});
				});
			
				var thae;
				$(document).on('click','.complexMateriel',function(){
					 thae=$(this)
					//提示复合物料名
					$(".complexMateriel").typeahead({
						//ajax 拿way数据
						scrollHeight:1,
						source : function(query, process) {
								return $.ajax({
									url : '${ctx}/product/getMateriel',
									type : 'GET',
									data : {
										name:query,
										type:"material",
									},
									success : function(result) {
										//转换成 json集合
										 var resultList = result.data.map(function (item) {
											 	//转换成 json对象
						                        var aItem = {name: item.name, number:item.number, price:item.price, unit:item.unit}
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
						        thae.parent().prev().text(item.number);
						        thae.parent().parent().find('.unitPricetw').text(item.price);
						        thae.parent().parent().find('.unittw').text(item.unit);
						    	return item.name
						    },
							//item是选中的数据
							updater:function(item){
								//转出成json对象
								var item = JSON.parse(item);
								thae.parent().prev().text(item.number);
								thae.parent().parent().find('.unitPricetw').text(item.price);
								thae.parent().parent().find('.unittw').text(item.unit);
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