<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
										htmlth +='<li><a href="#" name='+f+' class="third" title='+e+'><i class="fa   fa-fw '+Thirdicon+'" id="'+f+'"></i>'+e+'</a></li>' //拼接第三层
									}
			      				}
			      				htmltr +='<li class="nav-dropdown"><a href="#" name='+d+' class="onclic" title='+c+'><i class="fa   fa-fw '+secondicon+'"></i>'+c+'</a><ul class="nav-sub" style="display:none;">'+htmlth+'</ul></li>'
								} 
			      				 }
			      			html +='<li class="nav-dropdown"><a href="#" class="sele"><i class="fa  fa-fw '+fristicon+'"></i>'+a+'</a><ul class="nav-sub" style="display:none;">'+htmltr+'</ul></li>'
							}
			      			
					  $('#informatic').append("<li class='active'><a href='${ctx }/index' class='index'   title='首页'><i class='fa  fa-fw fa-tachometer'></i> 首页</a></li>"+html); 
					  var navstation = $.cookie("navstation");
					  var navstationtwo=$.cookie("navstationtwo");
					 if(navstation!=null){
						//三级确认跳转后菜单栏
						  $('#informatic li ul li ul li a').each(function(){
							  if($(this).html() == navstation){
								$(this).parent().parent().css("display","block").parent().parent().css("display","block")
								$(this).parent().parent().parent().parent().parent().addClass("active")
								$(this).parent().addClass("active")
								$(this).parent().parent().parent().parent().parent().siblings().removeClass("active")
							  }
						  }) 
					 } 
					  if(navstationtwo!=null){
						//二级确认跳转后菜单栏
						  $('#informatic li ul li a').each(function(){
							  if($(this).html() == navstationtwo){
								$(this).parent().parent().css("display","block")
								$(this).parent().parent().parent().addClass("active")
								$(this).parent().addClass("active")
							$(this).parent().parent().parent().siblings().removeClass("active")
							  }
						  }) 
					 }
					 
					   $('.index').on('click',function(){ 
						  $.cookie("navstation", null);
						  $.cookie("navstationtwo",null);
						 }) 
					  
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
							$.cookie("navstationtwo", $(this).html());//添加cookie 
							$.cookie("navstation", null);
						}
					 })
					 //第三层级联跳转页面
					 $('.third').on('click',function(){
						 
						var p= $(this).attr("name");
							
						if(p!="#"){
							location.href = "${ctx}/menusToUrl?url="+p;
						 $.cookie("navstation", $(this).html());//添加cookie 
						 $.cookie("navstationtwo",null);
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
                
            </ul>
            
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown profile hidden-xs">
                    <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
                        <span class="meta">
                            <span class="avatar">
                                <img src="${ctx }/static/images/profile.jpg" class="img-circle" alt="">
                            </span>
                        <span class="text"> <shiro:principal/></span>
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
                    <a href="javascript:void(0);"  aria-expanded="false"><h4><shiro:principal/></h4></a>
                    <small class="title">Front-end Developer</small>
                </div>
            </div>
            <nav>
               
                <ul class="nav nav-pills nav-stacked " id="informatic">
                </ul>
            </nav> 
            
              
        </aside>
        
        <!--sidebar left end-->
        
        <section class="main-content-wrapper">
          <!--sidebar right start-->
    <aside id="sidebar-right">
        <h4 class="sidebar-title">contact List</h4>
        <div id="contact-list-wrapper">
            <div class="heading">
                <ul>
                    <li class="new-contact"><a href="javascript:void(0)"><i class="fa fa-plus"></i></a>
                    </li>
                    <li>
                        <input type="text" class="search" placeholder="Search">
                        <button type="submit" class="btn btn-sm btn-search"><i class="fa fa-search"></i>
                        </button>
                    </li>
                </ul>
            </div>
            <div id="contact-list">
                <ul>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="${ctx }/static/images/avatar3.png" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Ashley Bell </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Sarasota, FL</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="${ctx }/static/images/avatar1.png" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Brian Johnson </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> San Francisco, CA</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="${ctx }/static/images/avatar2.png" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Chris Jones </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Brooklyn, NY</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="${ctx }/static/images/avatar4.jpg" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Erica Hill </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Palo Alto, Ca</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="${ctx }/static/images/avatar5.png" class="img-circle" alt="">
                          <i class="away animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Greg Smith </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> London, UK</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="${ctx }/static/images/avatar6.png" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Jason Kendall</div>
                                <small class="location text-muted"><i class="icon-pointer"></i> New York, NY </small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="${ctx }/static/images/avatar7.png" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Kristen Davis </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Greenville, SC</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="${ctx }/static/images/avatar8.png" class="img-circle off" alt="">
                          <i class="off animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Michael Shepard </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Vancouver, BC</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="${ctx }/static/images/avatar9.png" class="img-circle off" alt="">
                          <i class="off animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Paul Allen</div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Savannah, GA</small>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div id="contact-user">
                <div class="chat-user active"><span><i class="icon-bubble"></i></span>
                </div>
                <div class="email-user"><span><i class="icon-envelope-open"></i></span>
                </div>
                <div class="call-user"><span><i class="icon-call-out"></i></span>
                </div>
            </div>
        </div>
    </aside>
    <!--/sidebar right end-->
        
</body>
        
</html>