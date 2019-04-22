<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script> 
<script src="${ctx }/static/js/layer/layer.js"></script>
<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<script src="${ctx}/static/js/common/iframeResizer.min.js"></script>  
<head>
<style>
  iframe {
    width: 1px;
    min-width: 100%;
  }
</style>
</head>

<body>
	<section id="main-wrapper" class="theme-default" >
		 <%@include file="decorator/leftbar.jsp"%> 
		<!--main content start-->
		<section id="main-content" class="animated fadeInUp"  style="padding:0">
			<div id="decoroll2" class="imgfocus">
				<div>
					<div class="layui-tab layui-tab-brief" lay-allowClose="true" lay-filter="myTab">
						<ul class="layui-tab-title">
						</ul>
						<div class="layui-tab-content" style="padding:0;">
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>
	<!--main content end-->
		
<script>
var open=[];
layui.use('element', function(){  
	 var element = layui.element; 		
});

//判断是否需要打开新的页面，或者定位到已打开的页面
function openPage(title,show,url,id){       //title当前iframe页名，show是否显示，url iframe的url，id唯一标识
	 var element = layui.element; 
	 var index=0;
	for(index=0;index<open.length;index++){     //定位到已打开的页面
		if(open[index].url==url){
			open[index].show=true;
			if(open[index].show==true)
			    element.tabChange('myTab', open[index].id); //切换到 lay-id="yyy" 的这一项
			leftNavChange(open[index].id);
			break;
		}
	} 	
 	if(index>=open.length){                //打开新的页面
		 open.push({"title":title,"show":show,"url":url,"id":id});
		 element.tabAdd('myTab',{            
			title:title,  
			content:'<iframe src="${ctx}/menusToUrl?url='+url+'"  frameborder="no"  scrolling="no" id="'+id+'"></iframe>',		
			id:id
		}); 
		 element.tabChange('myTab',id);
		 var newid='#'+id;
		 iFrameResize({autoResize:true});
 	}
 	
 	leftNavChange(id);     //切换菜单栏位置
 	
  	element.on('tab(myTab)', function(data){  //tab选项绑定点击事件
 		leftNavChange($(this).attr("lay-id"));
 	});
 
 	$(".layui-tab").on("click",function(e){   //关闭事件删除数组元素
		if($(e.target).is(".layui-tab-close")){
			var closeId=$(e.target).parent().attr("lay-id");
			for(var i=0;i<open.length;i++){
				if(open[i].id==closeId){
					open.splice(i,1);  
				}
			}
		}
	}) 
}
</script>
</body>
</html>
