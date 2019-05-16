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
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/style/admin.css" media="all">

</head>
<body class="layui-layout-body">
  
  <div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
      <div class="layui-header">
        <!-- 头部区域 -->
        <ul class="layui-nav layui-layout-left">
          <li class="layui-nav-item layadmin-flexible" lay-unselect>
            <a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
              <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
            </a>
          </li>
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;" layadmin-event="refresh" title="刷新">
              <i class="layui-icon layui-icon-refresh-3"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <input type="text" style="margin-top:9px;" placeholder="搜索..." autocomplete="off" class="layui-input layui-input-search" layadmin-event="serach" lay-action="template/search.html?keywords="> 
          </li>
        </ul>
        <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
          
        <!--   <li class="layui-nav-item" lay-unselect>
            <a lay-href="app/message/index.html" layadmin-event="message" lay-text="消息中心">
              <i class="layui-icon layui-icon-notice"></i>  
              	如果有新消息，则显示小圆点
              <span class="layui-badge-dot"></span>
            </a>
          </li> -->
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="theme">
              <i class="layui-icon layui-icon-theme"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="note">
              <i class="layui-icon layui-icon-note"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="fullscreen">
              <i class="layui-icon layui-icon-screen-full"></i>
            </a>
          </li>
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;">
              <cite><shiro:principal /></cite>
            </a>
            <dl class="layui-nav-child">
              <!-- <dd><a lay-href="set/user/info.html">基本资料</a></dd> -->
               <dd><a lay-href="${ctx}/menusToUrl?url=decorator/addresslist">通讯录</a></dd>
              <dd><a lay-href="${ctx}/menusToUrl?url=decorator/updatePwd">修改密码</a></dd>
              <dd style="text-align: center;"><a id="logout">退出</a></dd>
            </dl>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;"></a>
          </li>
         <!--  <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="about"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
          <li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-unselect>
            <a href="javascript:;" layadmin-event="more"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li> -->
        </ul>
      </div>
      
      <!-- 侧边菜单 -->
      <div class="layui-side layui-side-menu">
        <div class="layui-side-scroll">
          <div class="layui-logo">
            <span>蓝白玩偶</span>
          </div>
          <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
          </ul>
        </div>
      </div>

      <!-- 页面标签 -->
      <div class="layadmin-pagetabs" id="LAY_app_tabs">
        <div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-down">
          <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
            <li class="layui-nav-item" lay-unselect>
              <a href="javascript:;"></a>
              <dl class="layui-nav-child layui-anim-fadein">
                <dd layadmin-event="closeThisTabs"><a href="javascript:;">关闭当前标签页</a></dd>
                <dd layadmin-event="closeOtherTabs"><a href="javascript:;">关闭其它标签页</a></dd>
                <dd layadmin-event="closeAllTabs"><a href="javascript:;">关闭全部标签页</a></dd>
              </dl>
            </li>
          </ul>
        </div>
        <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
          <ul class="layui-tab-title" id="LAY_app_tabsheader">
            <li lay-id="home/console.html" lay-attr="home/console.html" class="layui-this"><i class="layui-icon layui-icon-home"></i></li>
          </ul>
        </div>
      </div>
      
      
      <!-- 主体内容 -->
      <div class="layui-body" id="LAY_app_body">
        <div class="layadmin-tabsbody-item layui-show">
         <!--  <iframe src="home/console.html" frameborder="0" class="layadmin-iframe"></iframe> -->
        </div>
      </div>
      
      <!-- 辅助元素，一般用于移动设备下遮罩 -->
      <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
  </div>

 <script src="${ctx }/static/layuiadmin/layui/layui.js"></script>
 <script>
 layui.config({
   base: '${ctx }/static/layuiadmin/', //静态资源所在路径
 }).extend({
   index : 'lib/index',  //主入口模块
 }).use('index');
 
layui.use(['form','element','layer','jquery'],function(){
	var form = layui.form,
		element = layui.element;
		$ = layui.$;
    	layer = parent.layer === undefined ? layui.layer : top.layer;
		
    	$('#logout').on('click',function(){
    		location.href = "${ctx}/logout";
    	})
    	$('#updatePwd').on('click',function(){				//修改密码
    		$('#hiddenButton').click();
    	})
    	
    	$.ajax({
			url:"${ctx}/menus",
			type:"GET", 
			success: function (result) {
				if(0==result.code){
					var html='';
					var data=result.data;
					for(var i=0;i<data.length;i++){
						$("#nav").find('span.layui-nav-bar').remove();
						html+='<li data-name="'+data[i].identity+'" class="layui-nav-item">'+
								'<a href="javascript:;" lay-tips="'+data[i].name+'" lay-direction="2">'+
									'<i class="layui-icon layui-icon-'+data[i].icon+'"></i>  '+
										'<cite>'+data[i].name+'</cite></a>'
						if(data[i].children!=null)
							html+=getChildren(data[i].children);
						html+='</li>';
					}
					$('#LAY-system-side-menu').append(html);
					element.render();
				}
				
			}
    	})

    	function getChildren(child){
    		var html='<dl class="layui-nav-child">';
    		for(var i=0;i<child.length;i++){
    			if(child[i].children!=null){
    				html+='<dd data-name="'+child[i].identity+'" class>'+
						'<a href="javascript:;">'+
							'<i class="layui-icon layui-icon-'+child[i].icon+'"></i>  '+
							'<cite>'+child[i].name+'</cite></a>';
						
    			}else{
    				var href='${ctx}/menusToUrl?url='+child[i].url;
		    		html+='<dd data-name="'+child[i].identity+'" class>'+
		    					'<a lay-href="'+href+'">'+
		    					'<i class="layui-icon layui-icon-'+child[i].icon+'"></i>  '+
								'<cite>'+child[i].name+'</cite></a>';
    			}
	    		if(child[i].children!=null)
	    			html+=getChildren(child[i].children); 
	    		html+='</dd>';
    		}
    		return html+'</dl>';
    	}
    	 //监听关闭点击事件是否为考勤汇总
    	$(document).on('mousedown', '.layui-tab-close', function (event) {  
    		 if($(event.target).parent().attr("lay-id")=='/bluewhite/menusToUrl?url=personnel/update'){
    			var closeBtn=$(this);
    			var ifmDocument=document.getElementById('/bluewhite/menusToUrl?url=personnel/update').contentWindow.document;	//获取考勤汇总iframe中的document对象
    			  if($(ifmDocument.getElementById('div-test3')).text().length>100){ 
    				layer.confirm("考勤汇总是否存档",{
    					btn:['确认','取消']}
    					,function(index){
    						ifmDocument.getElementById('sealAttendanceCollect').click();
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
    	 //监听刷新的页面是否为考勤汇总
    	$(document).on('mousedown', '.layui-icon-refresh-3', function (event) {  
    		 var elem=document.getElementById('/bluewhite/menusToUrl?url=personnel/update');			//获取考勤汇总iframe元素对象
	   		 if(elem!=null && elem.parentNode.getAttribute('class').indexOf('layui-show')>0){			//如果ifram存在，且为当前显示状态
	   			  var refresh=$(this);
	   			  if($(elem.contentWindow.document.getElementById('div-test3')).text().length>100){ 	//如果有查询内容
	   				layer.confirm("考勤汇总是否存档",{
	   					btn:['确认','取消']}
	   					,function(index){
	   						elem.contentWindow.document.getElementById('sealAttendanceCollect').click();
	   						layer.close(index);
	   						setTimeout(function () {
	   							refresh.click();
	   						}, 100);
	   					}
	   					,function(index){layer.close(index);
	   						refresh.click();
	   					});
	   			}  
   			} 
   		});
   		

})
 </script>
</body>
</html>


