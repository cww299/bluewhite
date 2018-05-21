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
		<%@include file="../../decorator/leftbar.jsp"%> 
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
                            <div class="row" style="height: 30px">
			<div class="col-xs-8 col-sm-8  col-md-8">
				<form class="form-search" >
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-7">
							<div class="input-group"> 
								<table><tr>
								<td>员工姓名:</td><td><input type="text" name="name" id="name" class="form-control search-query name" /></td>
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
                                            <th class="text-center">序号</th>
                                            <th class="text-center">编号</th>
                                            <th class="text-center">姓名</th>
                                            <th class="text-center">手机号</th>
                                            <th class="text-center">身份证号</th>
                                            <th class="text-center">部门</th>
                                            <th class="text-center">职位</th>
                                            <th class="text-center">操作</th>
                                            <th class="text-center">员工分组</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent" style="font-size: 14px">
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
				  		size:10,	
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
		      				  var a;
		      				 if(o.group==null){
		      					a=0
		      				 }else{
		      					 a=o.group.id
		      				 }
		      				 var order = i+1;
		      				html +='<tr>'
		      				+'<td class="text-center edit price">'+order+'</td>'
		      				+'<td class="text-center edit price">'+o.number+'</td>'
		      				+'<td class="text-center edit price">'+o.userName+'</td>'
		      				+'<td class="text-center edit price">'+o.phone+'</td>'
		      				+'<td class="text-center edit price">'+o.idCard+'</td>'
		      				+'<td class="text-center edit price">'+o.orgName.name+'</td>'
		      				+'<td class="text-center edit price">'+o.position.name+'</td>'
							+'<td class="text-center"><button class="btn btn-xs btn-primary btn-3d update">详细信息</button></td>'
							+'<td class="text-center"><div class="groupChange" data-id="'+o.id+'" data-groupid="'+a+'" ></div></td></tr>'
							
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
									  		size:10,
									  		userName:$('#name').val(),
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
			//遍历组名信息
			var data={
					page:1,
			  		size:100,	
			  		type:1,

			} 
			var index;
		    var html = '';
		    $.ajax({
			      url:"${ctx}/production/getGroup",
			      data:data,
			      type:"GET",
			     
	      		  success: function (result) {
	      			 $(result.data).each(function(i,o){
	      				html +='<option value="'+o.id+'">'+o.name+'</option>'
	      			}); 
			        //显示分页
			       var htmlto='<select class="form-control selectgroupChange"><option value="0">请选择</option>'+html+'</select>'
				   	$(".groupChange").html(htmlto); 
				   	self.chang();
				   	self.selected();
			      },error:function(){
						layer.msg("加载失败！", {icon: 2});
						layer.close(index);
				  }
			  });
	  }
		this.chang=function(){
			$('.selectgroupChange').change(function(){
				var that=$(this);
				var data={
						userIds:that.parent().data("id"),
						groupId:that.val(),
					}
				var _data={
						page:1,
				  		size:10,	
				} 
				$.ajax({
					url:"${ctx}/production/userGroup",
					data:data,
					type:"POST",
					success:function(result){
						if(0==result.code){
							layer.msg("分组成功！", {icon: 1});
							
						}else{
							layer.msg("分组失败", {icon: 2});			
						}
						
					},error:function(){
						layer.msg("操作失败！", {icon: 2});
					}
				})
				
				
			})
		}
		this.selected=function(){
			
			$('.selectgroupChange').each(function(i,o){
				var id=$(o).parent().data("groupid");
				$(o).val(id);
			})
			
		}
		this.events = function(){
			$('.searchtask').on('click',function(){
				var data = {
			  			page:1,
			  			size:10,
			  			userName:$('#name').val(),
			  	}
				
	            self.loadPagination(data);
			});
			
	  }
   	}
	var login = new Login();
	  login.init();
})
    </script>
    
    
        
</body>
</html>