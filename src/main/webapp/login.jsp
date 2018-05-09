<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>登入</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="${ctx }/static/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${ctx }/static/assets/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${ctx }/static/assets/css/ace.min.css" />
		<link rel="stylesheet" href="${ctx }/static/assets/css/ace-rtl.min.css" />
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1 style="background-color:rgba(0, 0, 0, 0.6)">
									<img src="${ctx }/static/assets/images/logo.png" alt="logo">
									<span class="red"></span>
									<span class="white"></span>
								</h1>
								<h4 class="blue"></h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>
												请在此输入登录信息
											</h4>

											<div class="space-6"></div>

											<form id="form"  action="${ctx }/user/login" method="post">
		
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" id="username" name="name" class="form-control" placeholder="请输入账号" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" id="password" name="password" class="form-control" placeholder="请输入密码" />
															<i class="icon-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<!-- <input type="checkbox" class="ace" />
															<span class="lbl"> 记住我</span> -->
														</label>

														<button type="button" id="submitBtn" class="btn btn-large btn-block btn-primary">
															<i class="icon-key"></i>
															登录
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

										</div><!-- /widget-main -->

									</div><!-- /widget-body -->
								</div><!-- /login-box -->


							</div><!-- /position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->



		<script src="${ctx }/static/assets/js/jquery-2.0.3.min.js"></script>
		<script src="${ctx }/static/assets/layer/layer.js"></script>

		<!-- <![endif]-->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='${ctx }/static/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='${ctx }/static/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>

		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			function show_box(id) {
			 jQuery('.widget-box.visible').removeClass('visible');
			 jQuery('#'+id).addClass('visible');
			}
		</script>
		<script type="text/javascript">
			$(function(){
				var Login = function(){
					var self = this;
					//表单jsonArray
					
					//初始化js
					this.init = function(){
						//注册绑定事件
						self.events();
					}
					
					//绑定事件
					this.events = function(){
						$('#submitBtn').on('click',function(){
							if($('#username').val()=='' || $('#password').val() == ''){
								layer.msg("账号或密码不能为空!", {icon: 2});
								$('#form')[0].reset();
							}else{
								
								var index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									  });
								 $.ajax({
								      url:"${ctx}/user/login",
								      type:"post", 
								      data:{
								    	  name:$('#username').val(),
								    	  password:$('#password').val()  
								      },
						      		  success: function (result) {
						      			  if(result.message=="success"){
							      				 
							      				location.href = "${ctx}/homePage/index";

						      			  }else{
						      				 
						      				layer.close(index);
						      				layer.msg(result.message, {icon: 2}); 
						      			  }
							      			
							      			  
							      			  
									      },error:function(){
												layer.msg("登入失败", {icon: 2});
												layer.close(index);
										  }
	
								  })
							}
						})
						
					}
					
				}
				var login = new Login();
				login.init();
				
			})
		</script>
	</body>
</html>



