<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>401 没有权限</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/style/admin.css" media="all">
  <style>
  	html{
  		background: white;
  	}
  	h2 span{
  		color: #009688;
  	}
  </style>
</head>
<body>


<div class="layui-fluid">
  <div class="layadmin-tips">
    <i class="layui-icon" face>&#xe664;</i>
    <div class="layui-text">
      <h1>
        <span class="layui-anim layui-anim-loop layui-anim-">4</span> 
        <span class="layui-anim layui-anim-loop layui-anim-rotate">0</span> 
        <span class="layui-anim layui-anim-loop layui-anim-">1</span>
      </h1>
      <h2>
        <span class="layui-anim layui-anim-loop layui-anim-">对不起！您没有权限访问本页面，请进行申请</span> 
      </h2>
    </div>
  </div>
</div>

  <script src="${ctx }/static/layuiadmin/layui/layui.js"></script>
  <script>
  layui.config({
	   base: '${ctx }/static/layuiadmin/', //静态资源所在路径
	 }).extend({
	   index : 'lib/index',  //主入口模块
	 }).use(['index'])
  </script>
</body>
</html>