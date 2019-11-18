<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>蓝白工艺</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/layui/css/layui.css" media="all">
  <style>
  	body{
  	    background: white;
  	}
  	.main{
 		margin-top: 100px;
	    text-align: center;
	    padding: 50px;
  	}
  	h1{
  		color: #91addc;
  	}
  	.day{
  		color: #91addc;
 		margin-top: 40px;
    	font-size: 18px;
  	}
  </style>
</head>
<body>
<div class="main">
	<h1>欢迎 <b id="userName">您</b> 使用蓝白管理系统！</h1>
	<img src="${ctx }/static/img/welcome.png">
	<p class="day"></p>
</div>
<script src="${ctx }/static/layuiadmin/layui/layui.js"></script>
<script>
 layui.use(['jquery','util'],function(){
	var $ = layui.$,
	util = layui.util;
	
	$.ajax({
		url:'${ctx}/getCurrentUser',		//获取当前登录用户
		async:false,
		success:function(r){
			if(0==r.code){
				var user = r.data;
				$('#userName').html(user.userName);
				if(!user.isAdmin){
					$.ajax({
						url:'${ctx}/system/user/pages?id='+user.id,
						success:function(r2){
							var thisUser = r2.data.rows[0];
							var entry = new Date(Date.parse(thisUser.entry));
							var now = new Date();
							var day = (now.getTime()-entry.getTime())/1000/60/60/24;
							$('.day').html('今天是您加入蓝白的第：<b>'+parseInt(day)+'</b>天');
						}
					})
				}
			}
		}
	})
 })
</script>
</body>
</html>