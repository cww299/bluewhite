<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
	<title>蓝白erp系统</title>
	<link rel="stylesheet" href="static/css/style.css">

	<!-- For-Mobile-Apps-and-Meta-Tags -->
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="Simple Login Form Widget Responsive, Login Form Web Template, Flat Pricing Tables, Flat Drop-Downs, Sign-Up Web Templates, Flat Web Templates, Login Sign-up Responsive Web Template, Smartphone Compatible Web Template, Free Web Designs for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design" />
		<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
	<!-- //For-Mobile-Apps-and-Meta-Tags -->

</head>

<body>
    <h1>蓝白erp系统</h1>
    <div class="container w3">
        <h2>欢迎使用</h2>
		<form action="#" method="post">
			<div class="username">
				<span class="username" style="height:19px">用户:</span>
				<input type="text" name="name" class="name" placeholder="" required="required">
				<div class="clear"></div>
			</div>
			<div class="password-agileits">
				<span class="username"style="height:19px">密码:</span>
				<input type="password" name="password" class="password" placeholder="" required="required">
				<div class="clear"></div>
			</div>
			<div class="rem-for-agile">
				<input type="checkbox" name="remember" class="remember">请记住我
<br>
				<a href="#">忘记密码</a><br>
			</div>
			<div class="login-w3">
					<input type="submit" id="submitBtn" class="login" value="登录">
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="footer-w3l">
		<p> 蓝白erp系统</p>
	</div>
	 <script src="static/js/vendor/jquery-3.3.1.min.js"></script>
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
						      data:{
						    	  name:$('.name').val(),
						    	  password:$('.password').val()  
						      },
						     
				      		  success: function (result) {
				      			  if(result.code==""){
					      				 
					      				location.href = "${ctx}/index";

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



