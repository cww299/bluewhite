<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>蓝白玩偶登录中心</title>
  <link rel="stylesheet" href="static/login/css/font-awesome.min.css">
  <link rel="stylesheet" href="static/login/css/bootstrap.min.css">
  
  <link rel="stylesheet" href="static/login/css/demo.css" />
  <link rel="stylesheet" href="static/login/css/templatemo-style.css">
  <link rel="stylesheet" href="static/3dfont/style.css"> 
  <script type="text/javascript" src="static/login/js/modernizr.custom.86080.js"></script>
		
	</head>

	<body>

			<div id="particles-js"></div>
		
			<ul class="cb-slideshow">
	            <li></li>
	            <li></li>
	            <li></li>
	            <li></li>
	            <li></li>
	            <li></li>
	        </ul>

			<div class="container-fluid">
				<div class="row cb-slideshow-text-container ">
					<div class= "tm-content col-xl-6 col-sm-8 col-xs-8 ml-auto section">
					<header class="mb-5"><h1><a>蓝白ERP系统</a></h1></header>
					<P class="mb-5"><a>A sign-up letter template with three background images shuffling by fade in out movements. Thank you for visiting our site!</a></P>
					
                    <form  id="form" action="##"  onsubmit="return false" method="post" class="subscribe-form">
               	    	<div class="row form-section">

							<div class="col-md-6 col-sm-7 col-xs-7">
			                      <input  type="text" name="username" class="form-control" id="contact_email" placeholder="登录名" required/>
			                      
				  			</div>
				  			<div class="col-md-7 col-sm-7 col-xs-7">
			                      
				  			</div>
				  			<br>
				  			<div class="col-md-6 col-sm-7 col-xs-7">
			                      <input name="password" type="password" class="form-control" id="contact_email" placeholder="密码" required/>
				  			</div>
				  			
							<div class="col-md-5 col-sm-5 col-xs-5">
								<button type="submit"  class="tm-btn-subscribe" id="submitBtn">登录</button>
							</div>
						
						</div>
                    </form>
                    
					

					</div>
				</div>	
				
			</div>	
	<script src="static/js/vendor/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="static/login/js/particles.js"></script>
	<script type="text/javascript" src="static/login/js/app.js"></script>
	<script src="static/js/vendor/jquery.cookie.js"></script>
	 <script src="static/js/layer/layer.js"></script>
	 <script type="text/javascript">
	 jQuery(function($){
			var Login = function(){
				var self = this;
				//表单jsonArray
				
				//初始化js
				this.init = function(){
					//注册绑定事件
					self.events();
				}
				this.events = function(){
					$('#submitBtn').on('click',function(){
						var index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						 $.ajax({
						      url:"${ctx}/login",
						      type:"post", 
						   	  data: $('#form').serialize(), 
						     
				      		  success: function (result) {
				      			  if(result.code==0){
					      				location.href = "${ctx}/index";
					      				$.cookie("navstation", null);
										$.cookie("navstationtwo",null)
				      			  }else{
				      				layer.close(index);
				      				layer.msg(result.message, {icon: 2}); 
				      			  }
					      			
					      			  
					      			  
							      },error:function(){
										layer.msg("登入失败", {icon: 2});
										layer.close(index);
								  }

						  })
					})
				}
			}
				var login = new Login();
				login.init();
			})
	 </script>
	</body>

</html>



