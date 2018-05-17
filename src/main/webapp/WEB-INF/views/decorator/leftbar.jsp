<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>

 <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>蓝白工艺</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="shortcut icon" href="${ctx }/static/images/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx }/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx }/static/css/simple-line-icons.css">
    <link rel="stylesheet" href="${ctx }/static/css/animate.css">
    <link rel="stylesheet" href="${ctx }/static/plugins/daterangepicker/daterangepicker-bs3.css">
    <link rel="stylesheet" href="${ctx }/static/plugins/switchery/switchery.min.css">
    <!-- Custom styles for this theme -->
    <link rel="stylesheet" href="${ctx }/static/css/main.css"> 
    <link rel="stylesheet" href="${ctx }/static/js/laypage/skin/laypage.css"> 
    
<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<script src="${ctx }/static/js/vendor/jquery.cookie.js"></script>
 <script src="${ctx }/static/js/vendor/modernizr-2.6.2.min.js"></script>
 <script src="${ctx }/static/js/layer/layer.js"></script>
<script type="text/javascript">
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
				this.init = function(){
					//注册绑定事件
					self.events();
					
				}
				
				this.events = function(){
					
					var html = '';
					var htmlto;
					var htmlth;
					var htmltr;
			      	var arr=new Array();
			      	var arrTo=new Array();
			      	var arrTh=new Array();
			      	var a=""; 
	 			    var b="";
			      	var c="";
		 			var d="";
		 			var e="";
		 			var f="";
		 			var fristicon="";
		 			var secondicon="";
		 			var Thirdicon="";
					/* 遍历菜单栏 */
		 			$.ajax({
					      url:"${ctx}/menus",
					      type:"GET", 
			      		  success: function (result) {
			      			arr=result.data[0]
			      			for (var i = 0; i < arr.length; i++) {
			      			 	a=arr[i].name //第一层次的命名
			      				b= arr[i].url //第一层次的链接
			      				fristicon=arr[i].icon //第一层次的图标
			      				arrTo=arr[i].children //第二层次的数组
			      				htmlto = "";
			      			 	htmltr = "";
			      				 if(arrTo!=null){
			      				 for (var k = 0; k < arrTo.length;k++) {
			      					 c=arrTo[k].name //第二层次的命名
			      					d=arrTo[k].url  //第二层次的链接
			      					secondicon=arrTo[k].icon //第二层次的图标
			      					arrTh=arrTo[k].children //第三层次的数组
			      					htmlto +='<li><a href="#"  name='+d+' title='+c+'><i class="fa  fa-fw '+secondicon+'"></i>'+c+'</a></li>' //拼接第二层
			      					htmlth = "";
			      				if(arrTh!=null){
			      					for (var j = 0; j < arrTh.length; j++) {
										e=arrTh[j].name //第三层次的命名
										f=arrTh[j].url  //第三层次的链接
										Thirdicon=arrTh[j].icon //第三层次的图标
										htmlth +='<li><a href="#" name='+f+' class="third" title='+e+'><i class="fa  fa-fw '+Thirdicon+'"></i>'+e+'</a></li>' //拼接第三层
									}
			      				}
			      				htmltr +='<li class="nav-dropdown"><a href="#" name='+d+' class="onclic" title='+c+'><i class="fa  fa-fw '+secondicon+'"></i>'+c+'</a><ul class="nav-sub" style="display:none;">'+htmlth+'</ul></li>'
								} 
			      				 }
			      			html +='<li class="nav-dropdown"><a href="#" class="sele"><i class="fa  fa-fw '+fristicon+'"></i>'+a+'</a><ul class="nav-sub" style="display:none;">'+htmltr+'</ul></li>'
							}
			      			
					  $('#informatic').append("<li class='active'><a href='${ctx }/index'  title='首页'><i class='fa  fa-fw fa-tachometer'></i> 首页</a></li>"+html); 
					  var navstation = $.cookie("navstation");
					  console.log(navstation)
					  //显示隐藏级联
					  $('.sele').on('click',function(){ 
						 var display =$(this).next().css("display")
						if(display=='none'){
							$(this).next().css("display","block");  
						}else{
							$(this).next().css("display","none"); 
						}
					 })
					 //显示隐藏级联 +++ 第二层级联跳转页面
					 $('.onclic').on('click',function(){
						 var display =$(this).next().css("display")
						if(display=='none'){
							$(this).next().css("display","block");  
						}else{
							$(this).next().css("display","none"); 
						}
						var p= $(this).attr("name");
						if(p!="#"){
							location.href = "${ctx}/menusToUrl?url="+p;
							
						}
					 })
					 //第三层级联跳转页面
					 $('.third').on('click',function(){
						 
						var p= $(this).attr("name");
							
						if(p!="#"){
							location.href = "${ctx}/menusToUrl?url="+p;
							$.cookie("navstation", $(this).html(), { path: "/" });
						}
					 })
					 $('#login').on('click',function(){
						
						 location.href = "${ctx}/logout";
						
					 })
						      },error:function(){
									layer.msg("失败", {icon: 2});
									layer.close(index);
							  }

					  })
					  
							
				}
				
				
			}
				var login = new Login();
				login.init();
        }) 
        
        </script>  
</head>
<body>
   <header id="header">
            <!--logo start-->
            <div class="brand">
                <a href="index.html" class="logo">
                    <i class="icon-layers"></i>
                    <span>蓝白</span>工艺</a>
            </div>
            <!--logo end-->
            <ul class="nav navbar-nav navbar-left">
                <li class="toggle-navigation toggle-left">
                    <button class="sidebar-toggle" id="toggle-left">
                        <i class="fa fa-bars"></i>
                    </button>
                </li>
                <li class="toggle-profile hidden-xs">
                    <button type="button" class="btn btn-default" id="toggle-profile">
                        <i class="icon-user"></i>
                    </button>
                </li>
                <li class="hidden-xs hidden-sm">
                    <input type="text" class="search" placeholder="Search project...">
                    <button type="submit" class="btn btn-sm btn-search"><i class="fa fa-search"></i>
                    </button>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown profile hidden-xs">
                    <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
                        <span class="meta">
                            <span class="avatar">
                                <img src="${ctx }/static/images/profile.jpg" class="img-circle" alt="">
                            </span>
                        <span class="text">用户</span>
                        <span class="caret"></span>
                        </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight" role="menu">
                        <li>
                            <a href="#" id="login">
                                <span class="icon"><i class="fa fa-sign-out"></i>
                                </span>退出</a>
                        </li>
                    </ul>
                </li>
                 <li class="toggle-fullscreen hidden-xs">
                    <button type="button" class="btn btn-default expand" id="toggle-fullscreen">
                        <i class="fa fa-expand"></i>
                    </button>
                </li>
                <li class="toggle-navigation toggle-right">
                    <button class="sidebar-toggle" id="toggle-right">
                        <i class="fa fa-indent"></i>
                    </button>
                </li>
            </ul>
        </header> 
         <!--sidebar left start-->
        <aside class="sidebar sidebar-left">
            <div class="sidebar-profile">
                <div class="avatar">
                    <img class="img-circle profile-image" src="${ctx }/static/images/profile.jpg" alt="profile">
                    <i class="on border-dark animated bounceIn"></i>
                </div>
                <div class="profile-body dropdown">
                    <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><h4>Mike Adams <span class="caret"></span></h4></a>
                    <small class="title">Front-end Developer</small>
                    <ul class="dropdown-menu animated fadeInRight" role="menu">
                        <li class="profile-progress">
                            <h5>
                                <span>80%</span>
                                <small>Profile complete</small>
                            </h5>
                            <div class="progress progress-xs">
                                <div class="progress-bar progress-bar-primary" style="width: 80%">
                                </div>
                            </div>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="javascript:void(0);">
                                <span class="icon"><i class="fa fa-user"></i>
                                </span>My Account</a>
                        </li>
                        <li>
                            <a href="javascript:void(0);">
                                <span class="icon"><i class="fa fa-envelope"></i>
                                </span>Messages</a>
                        </li>
                        <li>
                            <a href="javascript:void(0);">
                                <span class="icon"><i class="fa fa-cog"></i>
                                </span>Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="javascript:void(0);">
                                <span class="icon"><i class="fa fa-sign-out"></i>
                                </span>Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
            <nav>
                <h5 class="sidebar-header">Navigation</h5>
                <ul class="nav nav-pills nav-stacked " id="informatic">
                </ul>
            </nav> 
            
              
        </aside>
        
        <!--sidebar left end-->
        <section class="main-content-wrapper">
            <div class="pageheader">
                <h1>Data Tables</h1>
                <div class="breadcrumb-wrapper hidden-xs">
                    <span class="label">你在这里:</span>
                    <ol class="breadcrumb">
                        <li><a href="${ctx }/index">首页</a>
                        </li>
                        <!-- <li>Tables</li>
                        <li class="active">Data Tables</li> -->
                    </ol>
                </div>
            </div>
        
</body>
        
</html>