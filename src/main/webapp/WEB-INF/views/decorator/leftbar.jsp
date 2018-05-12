<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
 <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>NeuBoard</title>
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
    
    <script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
    <script src="${ctx }/static/js/vendor/modernizr-2.6.2.min.js"></script>
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
					var html = '';
					var htmlto = '';
			      	var arr=new Array();
			      	var arrTo=new Array();
			      	var a="";
			      	var b="";
			      	var c="";
			      	var d="";
					$.ajax({
					      url:"${ctx}/menus",
					      type:"GET", 
			      		  success: function (result) {
			      			arr=result.data
			      			
			      			
			      			for (var i = 0; i < arr[0].length; i++) {
			      			 	a=arr[0][i].name
			      				b= arr[0][i].url
			      				 arrTo=arr[0][i].children
			      				 if(arrTo!=null){
			      					 
			      				for (var i = 0; i < arrTo.length; i++) {
			      				var	c=arrTo[i].name
			      					d=arrTo[i].url
			      					htmlto +='<li><a href="${ctx }'+d+'" title="Buttons">'+c+'</a></li>'
								}
			      				 }
			      			html +='<li class="nav-dropdown"><a href="#"><i class="fa  fa-fw fa-cogs"></i>'+a+'</a><ul class="nav-sub" style="display:blok">'+htmlto+'</ul></li>'
							}
			      					
					 /*  $('#informatic').append("<li class='active'><a href='${ctx }/index' title='首页'><i class='fa  fa-fw fa-tachometer'></i> 首页</a></li>"+html) */
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
                    <span>NEU</span>BOARD</a>
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
                        <span class="text">Mike Adams</span>
                        <span class="caret"></span>
                        </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight" role="menu">
                        <li>
                            <span class="arrow top"></span>
                            <h5>
                                <span>80%</span>
                                <small class="text-muted">Profile complete</small>
                            </h5>
                            <div class="progress progress-xs">
                                <div class="progress-bar progress-bar" style="width: 80%">
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
                    
                    
                    
                    <li class="nav-dropdown">
                        <a href="#" title="产品总汇">
                            <i class="fa  fa-fw fa-cogs"></i> 产品总汇
                        </a>
                        <ul class="nav-sub" >
                             <li>
                                <a href="${ctx }/product/information"  title="Buttons"> 产品信息
                                </a>
                            </li> 
                           
                           <li>
                                <a href="${ctx }/product/information"  title="Buttons"> 产品信息
                                </a>
                            </li> 
                        </ul>
                    </li>  
                    
                </ul>
            </nav>
            
              
        </aside>
        <!--sidebar left end-->
</body>
        
</html>