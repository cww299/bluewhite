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

<body >
	<section id="main-wrapper" class="theme-default" >
		 <%@include file="decorator/leftbar.jsp"%> 
		<!--main content start-->
		<section id="main-content" class="animated fadeInUp"  style="padding:0">
			<div id="decoroll2" class="imgfocus">
				<div>
					<div class="layui-tab layui-tab-brief" lay-allowClose="true" lay-filter="myTab" style="margin:0px;">
						<ul class="layui-tab-title" style="margin-bottom:5px;">
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
var open=[];   //存入已打开页面的信息title当前iframe页名，url iframe的url，id为超链接a拼接lay-iframe-a.id即为选项卡的id,isShow为当前选项卡是否显示
layui.use('element', function(){  
	 var element = layui.element
	 	,layer=layui.layer; 		
}); 

//判断是否需要打开新的页面，或者定位到已打开的页面
function openPage(title,url,id){       //title当前iframe页名，url iframe的url，id唯一标识超链接a的id，由点击leffbar中菜单触发并传参
	
	 var element = layui.element; 
	 var index=0;   //遍历open的索引
	for(index=0;index<open.length;index++){     //如果点击的菜单已打开则定位到已打开的页面
		 if(open[index].url==url){
			 checkIsUpdate();    //检查当前被切换的是否为考勤汇总
			    //切换到当前选项卡,isShow为true
			 element.tabChange('myTab',open[index].id);  //切换到 lay-id=open[index].id 的这一项			 
			 open[index].isShow=true;
			 var ifm=document.getElementById('iframe-'+id);
			 if(ifm.style.height=='0px'){        //如果ifm高度为0px的bug
				 ifm.style.height=ifm.contentDocument.body.scrollHeight+'px';    //重新对ifm高度赋值
			 }
			 break;
		 }
	} 	
 	if(index>=open.length){             //打开新的页面
 		checkIsUpdate();
		 element.tabAdd('myTab',{            
			title:title,  
			content:'<iframe src="${ctx}/menusToUrl?url='+url+'"  frameborder="no"  scrolling="no" id="iframe-'+id+'" name="iframe-'+id+'"/>',		
			id:'lay-iframe-'+id
		}); 
		element.tabChange('myTab','lay-iframe-'+id);     //切换到当前新打开的页面
		iFrameResize({autoResize:true},'#iframe-'+id);  //计算iframe高度
		 
		element.on('tab(myTab)', function(data){  //tab选项绑定点击事件  用于点击切换选项卡时保证leftbar中菜单位置的一致
		 	 leftNavChange($(this).attr("lay-id"));
		 	 checkIsUpdate();
		 	 for(var i=0;i<open.length;i++){
		 		 if(open[i].id==$(this).attr("lay-id"))
		 			 open[i].isShow=true;
		 	 }
			 var id=$(this).attr("lay-id").substring(4);	//切割前4位，保留为iframe的id
			 var ifm=document.getElementById(id);
			 if(ifm.style.height=='0px'){       
				 ifm.style.height=ifm.contentDocument.body.scrollHeight+'px';    //重新对ifm高度赋值
			 }
		 });
		
	 	  $(".layui-tab").on("click",function(e){   //关闭事件删除数组元素，tab删除之后
	 		if($(e.target).is(".layui-tab-close")){  
				var closeId=$(e.target).parent().attr("lay-id"); 
				for(var i=0;i<open.length;i++){
					if(open[i].id==closeId){ 
						open.isShow=false;
						open.splice(i,1);  
					}
				}
			}  
		  }) 
	
		 open.push({"title":title,"url":url,"id":'lay-iframe-'+id,"isShow":true});   //擦入已经打开的页面数组
 	}
 	leftNavChange('lay-iframe-'+id);     //调用leftbar中的函数,切换菜单栏位置
}
$(document).on('mousedown', '.layui-tab-close', function (event) {   //监听关闭点击事件  tab删除之前
	if($(event.target).parent().attr("lay-id")=='lay-iframe-personnelCollect'){
		for(var i=0;i<open.length;i++){
			if(open[i].id=="lay-iframe-personnelCollect")
				open[i].isShow=false;
		}
		var closeBtn=$(this);
		 if($(window.frames["iframe-personnelCollect"].document).find("#div-test3").text().length>100){ 
			layer.confirm("考勤汇总是否存档",{
				btn:['确认','取消']}
				,function(index){
					$(window.frames["iframe-personnelCollect"].document).find("#sealAttendanceCollect").click();
					layer.close(index);
					setTimeout(function () {
						closeBtn.click();
					}, 100);
					}
				,function(index){layer.close(index);
					closeBtn.click();
				});
		} 
		
	}
});
function checkIsUpdate(){    //检查切换的是否是考勤汇总
	for(var i=0;i<open.length;i++){
		if(open[i].id=="lay-iframe-personnelCollect" && open[i].isShow==true){  //被切换的tab为考勤汇总
			if($(window.frames["iframe-personnelCollect"].document).find("#div-test3").text().length<100){ //查看是否有内容
				open[i].isShow=false;
				return;
			} 
			open[i].isShow=false;
			layer.confirm("考勤汇总是否存档",{
				btn:['确认','取消']},
				function(index){
						$(window.frames["iframe-personnelCollect"].document).find("#sealAttendanceCollect").click();//对存档按钮进行点击
						layer.close(index);
					},
				function(index){
					layer.close(index);
				});
		}
		open[i].isShow=false;
	}
}
</script>
</body>
</html>
