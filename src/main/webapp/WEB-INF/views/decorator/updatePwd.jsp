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
<style>

.layadmin-user-login-header {
    text-align: center;
}

.layadmin-user-login-box {
    padding: 20px;
}
.layadmin-user-login-body .layui-form-item .layui-input {
    padding-left: 38px;
}
.layui-input, .layui-textarea {
    display: block;
    width: 100%;
    padding-left: 10px;
}
.layadmin-user-login-icon {
    position: absolute;
    left: 1px;
    top: 1px;
    width: 38px;
    line-height: 36px;
    text-align: center;
    color: #d2d2d2;
}
</style>
</head>
<body>


<div class="layui-card">
	<div class="layui-card-body">
		<div class="layadmin-user-login layadmin-user-display-show" style="margin:0 auto;width:375px;">
		    <div class="layadmin-user-login-main ">
		      <div class="layadmin-user-login-box layadmin-user-login-header">
		        <h2>蓝白工艺</h2>
		        <p>蓝白工艺后台管理--修改密码</p>
		      </div>
		      <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
		         <div class="layui-form-item">
		         	<div class="layui-row">
		             <div class="layui-col-xs12">
			             <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="oldPwd"></label>
			             <input type="password" name="oldPwd" id="oldPwd" lay-verify="required" placeholder="请输入原密码" class="layui-input">
		         	</div>
		         	</div>
		         </div>
		         <div class="layui-form-item">
		           <div class="layui-row">
		             <div class="layui-col-xs12">
		               <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="newPwd1"></label>
		               <input type="password" name="newPwd1" id="newPwd1" lay-verify="required" placeholder="请输入新密码" class="layui-input">
		             </div>
		           </div>
		         </div>
		         <div class="layui-form-item">
		           <div class="layui-row">
		             <div class="layui-col-xs12">
		               <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="newPwd2"></label>
		               <input type="password" name="newPwd2" id="newPwd2" lay-verify="required" placeholder="请再一次输入新密码" class="layui-input">
		             </div>
		           </div>
		         </div>
		         <div class="layui-form-item">
		           <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="update">确认修改</button>
		         </div>
		    </div>
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