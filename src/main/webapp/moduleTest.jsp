<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="static/layui-v2.4.5/layui/layui.js"></script>
	<script src="static/js/vendor/jquery-3.3.1.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cesgu</title>
<style>
/* .layui-form-checkbox[lay-skin=primary] i {
    right: auto;
    left: 0;
    width: 16px;
    height: 16px;
    line-height: 16px;
    border: 1px solid #d2d2d2;
    font-size: 12px;
    border-radius: 2px;
    background-color: #fff;
    -webkit-transition: .1s linear;
    transition: .1s linear;
}
.layui-form-checked[lay-skin=primary] i {
    border-color: #5FB878;
    background-color: #5FB878;
    color: #fff;
}
.layui-form-checkbox[lay-skin=primary] {
    height: auto!important;
    line-height: normal!important;
    min-width: 18px;
    min-height: 18px;
    border: none!important;
    margin-right: 0;
    padding-left: 28px;
    padding-right: 0;
    background: 0 0;
} */
</style>
</head>
<body>

	<div id="das" style="height:100px;background-color:;"></div>
</body>

<script>


layui.config({	
    base:'${ctx}/static/layui-v2.4.5/layui/myModules/'
  }).use(['form','element','menuTree'], function(){
      var menuTree = layui.menuTree;
      
      menuTree.render({
    	  elem:'#das',
    	  url: "${ctx}/getTreeMenuPage",
    	  checked:[1,2,3,4,5,6,7,8,9,10],
      });

});
</script>

</html>