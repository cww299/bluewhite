<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />



<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->

<head>
   <link rel="stylesheet" type="text/css" href="${ctx }/static/lun/style.css">
  
</head>
  
<body>
    <section id="main-wrapper" class="theme-default">
    <%@include file="decorator/leftbar.jsp"%> 
        <!--main content start-->
       
            <section id="main-content" class="animated fadeInUp">
               <div id="decoroll2" class="imgfocus">

	<div id="decoimg_a2" class="imgbox">
		<div class="decoimg_b2"><a href="#"><img src="${ctx }/static/img/1.jpg"></a></div>
		<div class="decoimg_b2"><a href="#"><img src="${ctx }/static/img/2.jpg"></a></div>
		<div class="decoimg_b2"><a href="#"><img src="${ctx }/static/img/3.jpg"></a></div>
		<div class="decoimg_b2"><a href="#"><img src="${ctx }/static/img/4.jpg"></a></div>
	</div>
	
	<ul id="deconum2" class="num_a2">
		<li><a href="javascript:void(0)" hidefocus="true" target="_self">杨幂</a></li>
		<li><a href="javascript:void(0)" hidefocus="true" target="_self">范冰冰</a></li>
		<li><a href="javascript:void(0)" hidefocus="true" target="_self">高圆圆</a></li>
		<li><a href="javascript:void(0)" hidefocus="true" target="_self">刘诗诗</a></li>
	</ul>
	
</div>
            </section>
        </section>
        <!--main content end-->
    </section>

    <script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx }/static/lun/koala.min.1.5.js"></script>
    <script src="${ctx }/static/plugins/navgoco/jquery.navgoco.min.js"></script>
    <script src="${ctx }/static/plugins/pace/pace.min.js"></script>
    <script src="${ctx }/static/plugins/fullscreen/jquery.fullscreen-min.js"></script>
    <script src="${ctx }/static/js/src/app.js"></script>
    <!--Page Level JS-->
    <script src="${ctx }/static/plugins/countTo/jquery.countTo.js"></script>
    <script src="${ctx }/static/plugins/weather/js/skycons.js"></script>
    <script src="${ctx }/static/plugins/daterangepicker/moment.min.js"></script>
    <script src="${ctx }/static/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- ChartJS  -->
    <script src="${ctx }/static/plugins/chartjs/Chart.min.js"></script>
    <!-- Morris  -->
    <script src="${ctx }/static/plugins/morris/js/morris.min.js"></script>
    <script src="${ctx }/static/plugins/morris/js/raphael.2.1.0.min.js"></script>
    <!-- Vector Map  -->
    <script src="${ctx }/static/plugins/jvectormap/js/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="${ctx }/static/plugins/jvectormap/js/jquery-jvectormap-world-mill-en.js"></script>
    <!-- Calendar  -->
    <script src="${ctx }/static/plugins/calendar/clndr.js"></script>
    <script src="${ctx }/static/plugins/calendar/clndr-demo.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
    <!-- Switch -->
    <script src="${ctx }/static/plugins/switchery/switchery.min.js"></script>
    <!--Load these page level functions-->
     <script type="text/javascript">

	  Qfast.add('widgets', { path: "${ctx }/static/lun/terminator2.2.min.js", type: "js", requires: ['fx'] });  
	  Qfast(false, 'widgets', function () {
	  	K.tabs({
	  		id: 'decoroll2',//焦点图包裹id  
	  		conId: "decoimg_a2",//大图域包裹id  
	  		tabId:"deconum2",//小圆点数字提示id
	  		tabTn:"a",
	  		conCn: '.decoimg_b2',//大图域配置class       
	  		auto: 1,//自动播放 1或0
	  		effect: 'fade',//效果配置
	  		eType: 'mouseover',// 鼠标事件
	  		pageBt:true,//是否有按钮切换页码
	  		bns: ['.prev', '.next'],//前后按钮配置class                          
	  		interval: 3000// 停顿时间  
	  	}) 
  });


</script>
     
</body>

</html>
