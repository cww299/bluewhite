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
</head>
<body>
<div id="particles-js"></div>
<ul class="cb-slideshow">
	<li></li>
	<li></li>
	<li></li>
</ul>
<div class="container-fluid">
	<div class="row cb-slideshow-text-container">
		<div class="tm-content col-xl-6 col-sm-8 col-xs-8 ml-auto section">
			<header class="mb-5">
				<h1>
					<a>蓝白ERP系统</a>
				</h1>
			</header>
			<P class="mb-5">
				希望你永远有一颗少女心<br>
				不一定要喜欢粉红色<br>
				不一定要在打雷的时候尖叫着躲回被子<br>
				但是<br>
				一定要有一大堆玩偶
			</P>
			<form id="form" action="##" onsubmit="return false" method="post"
				class="subscribe-form">
				<div class="row form-section">
					<div class="col-md-6 col-sm-7 col-xs-7">
						<input type="text" name="username" class="form-control" placeholder="登录名" required />
					</div>
					<div class="col-md-7 col-sm-7 col-xs-7"></div>
					<br>
					<div class="col-md-6 col-sm-7 col-xs-7">
						<input name="password" type="password" class="form-control" placeholder="密码" required />
					</div>
					<div class="col-md-5 col-sm-5 col-xs-5">
						<button type="submit" class="tm-btn-subscribe" id="submitBtn">登录</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="static/login/js/modernizr.custom.86080.js"></script>
<script src="static/js/vendor/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="static/login/js/particles.js"></script>
<script type="text/javascript" src="static/login/js/app.js"></script>
<script src="static/js/vendor/jquery.cookie.js"></script>
<script src="static/js/layer/layer.js"></script>
<script src="${ctx }/static/layuiadmin/layui/layui.js"></script>
<script type="text/javascript">
layui.data('cookieMenu', {key:'openMenu',value:[],});
layui.data('cookieMenu', {key:'thisMenu',value:'',});
if(layui.data('cookieMenu').remeberMenu==='undefined')
	layui.data('cookieMenu', {key:'remeberMenu',value:false,});
if(window.top.location != window.location){
	top.location.href= location.href;  
};
$('#submitBtn').on('click',function(){
	var index = layer.load(1, {
		  shade: [0.5,'#black']
	});
	 $.ajax({
	      url:"${ctx}/login",
	      type:"post", 
	   	  data: $('#form').serialize(), 
  		  success: function (result) {
  			  if(result.code==0){
      				location.href = "${ctx}/";
  			  }else{
  				layer.close(index);
  				layer.msg(result.message, {icon: 2}); 
  			  }
	      },
	      error:function(){
				layer.msg("登入失败", {icon: 2});
				layer.close(index);
		  }

	  })
})
</script>
</body>
</html>