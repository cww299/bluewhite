<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础数据</title>
</head>
<body>
	<section id="main-wrapper" class="theme-default">
		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>序号</th>
										<th>基础数据名称</th>
										<th>基础数据类型</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="tablecontent">
								</tbody>
							</table>
							<div id="pager"></div>
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
	<script
		src="${ctx }/static/plugins/fullscreen/jquery.fullscreen-min.js"></script>
	<script src="${ctx }/static/js/src/app.js"></script>
	<script src="${ctx }/static/js/laypage/laypage.js"></script>
	<script src="${ctx }/static/plugins/dataTables/js/jquery.dataTables.js"></script>
	<script
		src="${ctx }/static/plugins/dataTables/js/dataTables.bootstrap.js"></script>
<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<script src="${ctx }/static/js/layer/layer.js"></script>
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
				      url:"${ctx}/basedata/types",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      				 var order = i+1;
		      				html +='<tr>'
		      				+'<td class="edit price">'+order+'</td>'
		      				+'<td class="edit price">'+o.name+'</td>'
		      				+'<td class="edit price">'+o.value+'</td>'
							+'<td><button class="btn btn-xs btn-primary update">编辑</button></td></tr>'
							
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
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
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