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
<head>

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
var newUrl;
layui.use('element', function(){  
	 var element = layui.element; 		
});

//判断是否需要打开新的页面，或者定位到已打开的页面
function openPage(name,show,url){   
	 var element = layui.element; 
	 var index=0;
	// var iheight;
	for(index=0;index<open.length;index++){     //定位到已打开的页面
		if(open[index].url==url){
			open[index].show=true;
			if(open[index].show==true)
			    element.tabChange('myTab', open[index].url); //切换到 lay-id="yyy" 的这一项
			leftNavChange(open[index].url);
			break;
		}
	} 	
 	if(index>=open.length){                //打开新的页面
 		newUrl=url;
		 open.push({"name":name,"show":show,"url":url});
		 element.tabAdd('myTab',{            
			title:name,
			content:'<iframe src="${ctx}/menusToUrl?url='+url+'" width="100%" height="100%" frameborder="no"  scrolling="yes" id="'+url+'" name="'+url+'" onload="iframeHeight()"></iframe>',		
			id:url
			
		}); 
		 element.tabChange('myTab',url); 
 	}
 	leftNavChange(url);     //切换菜单栏位置
 	
 	element.on('tab(myTab)', function(data){  //tab选项绑定点击事件
 		leftNavChange($(this).attr("lay-id"));
 	});
 
 	$(".layui-tab").on("click",function(e){   //关闭事件删除数组元素
		if($(e.target).is(".layui-tab-close")){
			var closeUrl=$(e.target).parent().attr("lay-id");
			for(var i=0;i<open.length;i++){
				if(open[i].url==closeUrl){
					open.splice(i,1);  
				}
			}
		}
	})
	
	
}
function iframeHeight(){ 
	var ifm= document.getElementById(newUrl);
	//  console.log(ifm.contentWindow.document.getElementById("main-content"));
	//	console.log(ifm.contentWindow.document.getElementById("main-content").scrollHeight);  
	//ifm.height=ifm.contentWindow.document.getElementById("main-content").scrollHeight;
	ifm.height="1000px";
}
</script>


</body>

</html>
