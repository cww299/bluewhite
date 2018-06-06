<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员汇总</title>
</head>
<body>
    <section id="main-wrapper" class="theme-default">
		<%@include file="../decorator/leftbar.jsp"%> 
            <section id="main-content" class="animated fadeInUp">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">人员信息</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>编号</th>
                                            <th>姓名</th>
                                            <th>手机号</th>
                                            <th>身份证号</th>
                                            <th>部门</th>
                                            <th>职位</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent">
                                    </tbody>
                                </table>
                                 <div id="pager">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </section>
        
        
        
        
        
        
         <!--隐藏框 修改开始  -->
 <div id="addbatch" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addbatchForm">
				<div class="form-group">
                                        <label class="col-sm-3 control-label">姓名:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="proName" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">批次号:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="bacthNumber" class="form-control">
                                        </div>
                 </div>
                
				</form>
</div>
</div>       
    <!--隐藏框 修改结束  -->
        
        
        
        
        
        
    </section>
 
    <!--Global JS-->
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
   jQuery(function($){
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
			 var data={
						page:1,
				  		size:15,	
				} 
			this.init = function(){
			//注册绑定事件
				self.events();
				self.loadPagination(data);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html ='';
			    $.ajax({
				      url:"${ctx}/system/user/pages",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				 var order = i+1;
		      				html +='<tr>'
		      				+'<td class="edit price">'+order+'</td>'
		      				+'<td class="edit price">'+o.number+'</td>'
		      				+'<td class="edit price">'+o.userName+'</td>'
		      				+'<td class="edit price">'+o.phone+'</td>'
		      				+'<td class="edit price">'+o.idCard+'</td>'
		      				+'<td class="edit price">'+o.orgName.name+'</td>'
		      				+'<td class="edit price">'+o.position.name+'</td>'
							+'<td><button class="btn btn-xs btn-success btn-trans addbatch" data-id='+o.id+' data-name='+o.userName+'>修改</button></td></tr>'
							
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
									  		size:15,
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
						var userName=$(this).data('name');
						var bacthDepartmentPrice=$(this).parent().parent().find('.departmentPrice').text();
						var bacthHairPrice=$(this).parent().parent().find('.hairPrice').text();
						$('#proName').val(userName);
						var id=$(this).data('id');
						
						
						//遍历工序类型
				    var indextwo;
				    var htmltwo = '';
				    var htmlth = '';
				    var htmlfr = '';
				    var data={
				    		type:1,
				    }
					    var getdata={type:"orgName",}
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
				      			layer.close(indextwo);
				      			//查询工序
								   /*  $.ajax({
									      url:"${ctx}/production/getProcedure",
									      data:data,
									      type:"GET",
									      beforeSend:function(){
									    	  indextwo = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											  });
										  }, 
							      			 
							      		  success: function (result) {
							      			
							      			  $(result.data).each(function(i,o){
							      				  
							      				htmltwo +='<tr>'
							      				+'<td class="text-center edit workingnametwo id">'+o.name+'</td>'
							      				+'<td class="text-center edit workingtimetwo">'+o.workingTime+'</td>'
							      				+'<td data-id="'+o.id+'" class="text-center" data-code="'+o.procedureType.id+'">'+htmlfr+'</td>' 
												+'<td class="text-center"><button class="btn btn-xs btn-primary btn-3d updateworking" data-id='+o.id+'>编辑</button>  <button class="btn btn-xs btn-danger btn-3d deleteprocedure">删除</button></td></tr>'
												
							      			});  
										   	   
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
									  }); */
						      }
						  });
						
						
						_index = layer.open({
							  type: 1,
							  skin: 'layui-layer-rim', //加上边框
							  area: ['30%', '50%'], 
							  btnAlign: 'c',//宽高
							  maxmin: true,
							  title:userName,
							  content: dicDiv,
							  btn: ['确定', '取消'],
							  yes:function(index, layero){
								 
								  postData={
										  productId:id,
										  bacthNumber:$('#bacthNumber').val(),
										  number:$('#prosum').val(),
										  remarks:$('#remarks').val(),
										  bacthDepartmentPrice:bacthDepartmentPrice,
										  bacthHairPrice:bacthHairPrice,
										  type:1,
										  allotTime:$('#Time').val(),
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
												$('#addbatch').hide();
												
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
								  $('.addbatchForm')[0].reset(); 
								  $('#addbatch').hide();
							  }
						});
					})
			  }
			this.events = function(){
			}
   	}
	var login = new Login();
	  login.init();
})
    </script>
    
    
        
</body>
</html>