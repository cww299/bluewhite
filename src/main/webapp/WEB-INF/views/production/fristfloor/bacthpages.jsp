<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>一楼质检批次管理</title>
<style>
	.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
	     top: 50%;
	     transform: translateY(-50%);
	}
	#looktime,#lookName,#lookbacth{
		color:red;
	}
	.userDiv,.procedureDiv{
		float:left;
		width:45%;
		height: 90%;
	}
	.layui-form-item{
		width:86%;
	}
	#userTree,#procedureTree{
		border: 1px solid #80808063;
	    padding: 10px;
	    width: 80%;
	    height: 80%;
	    margin: auto;
	}
	.procedureDiv p{
		margin-left: 25px;
	}
	.userDiv p{
		margin-left: 25px;
	}
	.hiddenTip{
		display:none;
		padding-left: 25px;
    	margin-bottom: 5px;
	}
	@media screen and (max-width: 1200px){
		/* 小屏幕样式  */
		.leftDiv{
			float: inherit;
    		width: 50%;
		}
		#isSmallScreen{
			display:none;
		}
		.layui-table-body .layui-btn-sm{
			line-height: 20px;
    		height: 20px;
		}
		.searchTable{
			font-size: 13px;
		}
		.searchTable input{
			width:120px;
		}
		.smHidden{
			display:none;
		}
		#userTree,#procedureTree{
			border: 1px solid #0000008f;;
		}
	}
</style>
</head>
<body>
	<div class="layui-card">
		<div class="layui-card-body">
			<div id="div"></div>
		</div>
	</div>
</body>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	bacthManager: 'layui/myModules/product/bacthManager',
}).define(
	['bacthManager'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 	
		, bacthManager = layui.bacthManager;
		
		bacthManager.render({
			elem:'#div',
			ctx:'${ctx}',
			type:1,
		})
	}//end define function
)//endedefine
</script>

</html>