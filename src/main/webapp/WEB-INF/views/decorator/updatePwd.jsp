<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<h2 style="text-align:center;">修改密码</h2>
		<div style="margin:0 auto;width:60%;margin-top:60px;margin-bottom:60px;">
			<div class="layui-form">
				<div class="layui-form-item">
					<label class="layui-form-label">原密码：</label>
					<div class="layui-input-block">
						<input type="password" lay-verify="required" name="oldPwd" class="layui-input"></div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">新密码：</label>
					<div class="layui-input-block">
						<input type="password" lay-verify="required" name="newPwd1" class="layui-input"></div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">确认密码：</label>
					<div class="layui-input-block">
						<input type="password" lay-verify="required" name="newPwd2" class="layui-input"></div>
				</div>
				<button type="button" lay-submit class="layui-btn" lay-filter="update">修改</button>
			</div>
		</div>
	</div>
</div>
</body>


<script>
layui.use(['form','element','layer','jquery'],function(){
	var form = layui.form,
		element = layui.element;
		$ = layui.$;
		layer = parent.layer === undefined ? layui.layer : top.layer;
		
		form.render();
		form.on('submit(update)',function(obj){
			if(obj.field.newPwd1!=obj.field.newPwd2){
				layer.msg("两次输入的密码不一致");
				return;
			}
			var load=layer.load(1);
			$.ajax({
				url:"${ctx}/system/user/checkPassword",
				data:{
					password:obj.field.oldPwd
				},
				type:"post",
				success:function(result){
					if(result.code==0){
						$.ajax({
							url:"${ctx}/system/user/updatePassword",
							type:"post",
							data:{password:obj.field.newPwd1},
							success:function(result){
								if(0==result.code){
									 layer.msg(result.message,{icon:1});
								}else{
									layer.msg(result.message,{icon:2});
								}
								layer.close(load);
							},
							error:function(result){
								layer.msg('ajax错误',{icon:2});
							}
						})
					 }
					else{
						 layer.msg(result.message,{icon:2});
					}
					layer.close(load);
				},
				error:function(result){
					layer.msg('ajax错误',{icon:2});
				}
			}) 
			
		})
		
})

</script>
</html>