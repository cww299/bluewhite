<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>蓝白工艺</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/style/admin.css" media="all">
  <style>
    .layui-side .layui-form-switch{
      min-width: 0px;
	  width: 26px;
	  height: 10px;
	  line-height: 10px;
	  margin-top: 0px;
	  padding: 0px;
	  float: right;
      margin-top: 19px;
    }
    .layui-side .layui-form-switch i{
      top: 0px;
      width: 10px;
      height: 10px;
      margin-left: -5px;
    }
    .layui-side .layui-form-onswitch em{
      display:none !important;
    }
    .layui-side .layui-form-switch em{
      display:none !important;
    }
    .layui-side .layui-form-onswitch i{
      margin-left: -10px;
    }
    .layadmin-side-shrink .layui-form-switch{
      display:none;
    }
    .layui-nav-tree .layui-nav-item.allMenu{
   	  display:none;
    }
    .layui-nav-tree .layui-nav-item.myMenu{
   	  display:block;
    }
    .layui-layer-tips .layui-layer-content{
      margin-top: -15px;
    }
    .myMenu.layui-anim {
	    -webkit-animation-duration: 1s;
	    animation-duration: 1s;
	    -webkit-animation-fill-mode: both;
	    animation-fill-mode: both;
	}
	.allMenu.layui-anim {
	    -webkit-animation-duration: 1s;
	    animation-duration: 1s;
	    -webkit-animation-fill-mode: both;
	    animation-fill-mode: both;
	}
	.layui-badge-number{
        top: 27% !important;
    	left: 67px;
   	    border-radius: 10px;
	}
	.layui-side-menu .layui-nav .layui-nav-child .layui-nav-child .layui-nav-child a{
		padding-left:75px;
	}
	.layui-side-menu .layui-nav .layui-nav-item a:hover{
		background-color: #0096883d !important
	}
  </style>
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
          
           <!-- 广宣部门查看预警按钮 
          <shiro:hasAnyRoles name="superAdmin,propagateManager">
	          <li class="layui-nav-item layui-hide-xs" lay-unselect>
	            <a href="javascript:;" id='lookoverWarn' >仓库预警<span class="layui-badge layui-badge-number" id='warnNumber'>0</span></a>
	          </li>
          </shiro:hasAnyRoles> 
          -->
           <!-- 考勤预警按钮 
          <shiro:hasAnyRoles name="superAdmin,personnel,productEightTailor,productTwoMachinist,productTwoDeedle,productFristPack,productFristQuality">
	          <li class="layui-nav-item layui-hide-xs" lay-unselect>
	            <a href="javascript:;" id='confluenceWarn' >错误考勤<span class="layui-badge layui-badge-number" id='warnConfluenceNumber'>0</span></a>
	          </li>
          </shiro:hasAnyRoles> 
          -->
          
          
          <!-- <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="theme">
              <i class="layui-icon layui-icon-theme"></i>
            </a>
          </li> -->
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
               <!-- <dd><a lay-href="${ctx}/menusToUrl?url=decorator/addresslist">通讯录</a></dd> -->
              <dd><a lay-href="${ctx}/menusToUrl?url=decorator/updatePwd">修改密码</a></dd>
              <dd style="text-align: center;"><a id="logout">退出</a></dd>
            </dl>
          </li>
          <!-- <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;"></a>
          </li> 
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="about"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li> -->
          
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="theme"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
          <!-- <li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-unselect>
            <a href="javascript:;" layadmin-event="more"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li> -->
          
        </ul>
      </div>
      
      <!-- 侧边菜单 -->
      <div class="layui-side layui-side-menu">
        <div class="layui-side-scroll">
          <div class="layui-logo layui-form">
            <span>蓝白玩偶</span>
            <input type="checkbox" name="zzz" lay-skin="switch" lay-filter="changeMenu" lay-text="&nbsp;|&nbsp;">
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
            <li lay-id="${ctx}/menusToUrl?url=home/index" lay-attr="${ctx}/menusToUrl?url=home/index" class="layui-this"><i class="layui-icon layui-icon-home"></i></li>
          </ul>
        </div>
      </div>
      
      
      <!-- 主体内容 -->
      <div class="layui-body" id="LAY_app_body">
        <div class="layadmin-tabsbody-item layui-show">
         <iframe src="${ctx}/menusToUrl?url=home/index" frameborder="0" class="layadmin-iframe"></iframe>
        </div>
      </div>
      
      <!-- 辅助元素，一般用于移动设备下遮罩 -->
      <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
  </div>
	
	<!-- 广宣预警弹窗 -->
	<shiro:hasAnyRoles name="superAdmin,propagateManager">
   		<div id="warningDiv" style="display:none;">
   			<table class="layui-form" style="margin: 10px 20px 0px;">
   				<tr>
   					<td>商品名称： &nbsp;&nbsp;</td>
   					<td><input type="text" name="skuCode" class="layui-input"></td>
   					<td>&nbsp;&nbsp;</td>
   					<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="searchWarnOfProduct">搜索</button></td>
   				</tr>
   			</table>
			<table id='warnTable' lay-filter='warnTable'></table>   		
   		</div>
	</shiro:hasAnyRoles> 
	<!-- 考勤错误预警弹窗 -->
	<shiro:hasAnyRoles name="superAdmin,personnel,productEightTailor,productTwoMachinist,productTwoDeedle,productFristPack,productFristQuality">
   		<div id="warningConfluenceDiv" style="display:none;padding:10px;">
   			<shiro:hasAnyRoles name="superAdmin,personnel">
   				<span class="layui-btn" id="verifyBtn">核对</span>
   			</shiro:hasAnyRoles>
			<table id='warningConfluenceTable' lay-filter='warningConfluenceTable'></table>   		
   		</div>
	</shiro:hasAnyRoles>
	
	
<script type="text/html" id='typeTpl'>
{{# var color='';
	var text='';
	switch(d.type){
		case 1: text='库存下限预警'; color=''; break;
		case 2: text='库存上限预警'; color='green'; break;
		case 3: text='库存时间过长预警'; color='blue'; break;
	}
}}
<span style='margin-top: 10px;' class='layui-badge layui-bg-{{ color}}'>{{ text }}</span>
</script>
 <script src="${ctx }/static/layuiadmin/layui/layui.js"></script>
 <script>
 layui.config({
   base: '${ctx }/static/layuiadmin/', //静态资源所在路径
 }).extend({
   index : 'lib/index',  //主入口模块
 }).use('index',function(){
	 var index = layui.index,
	 	 $ = layui.jquery;
	 var thisMenu = layui.data('cookieMenu').thisMenu;
	 if(thisMenu){
		 index.admin.tabsPage = {
			 type: 'nav',
			 index: $('.layui-tab-title').find('li[lay-id="'+thisMenu+'"]').prevAll().length,
			 elem: $('a[lay-href="'+thisMenu+'"]').get(0),
		 };
	 }
 });
 
layui.use(['form','element','layer','jquery','table'],function(){
	var form = layui.form,
		element = layui.element,
		table = layui.table,
		$ = layui.$;
    	layer = parent.layer === undefined ? layui.layer : top.layer;
    	form.render();
    	//是否开启记忆tab
    	var remeberMenu = layui.data('cookieMenu').remeberMenu;
    	$('#logout').on('click',function(){
    		location.href = "${ctx}/logout";
    	})
    	$('#updatePwd').on('click',function(){				//修改密码
    		$('#hiddenButton').click();
    	})
    	$(document).on('click','a[lay-href]',function(){
    		$(this).parent().addClass('layui-this');
    	})
    	form.on('switch(changeMenu)',function(obj){
    		var anim = ['up','scale','upbit','fadein','rotate'];
    		var type = 3;
    		if(obj.elem.checked){
    			$('.allMenu').addClass('layui-anim-'+anim[type]);
    			$('.allMenu').show();
    			$('.myMenu').hide();
    		}else{
    			$('.myMenu').addClass('layui-anim-'+anim[type]);
    			$('.allMenu').hide();
    			$('.myMenu').show();
    		}
    	})
    	var tips;
    	$('.layui-form-switch').mouseover(function(){
		  var that = this;
		  tips = layer.tips('切换菜单显示', that,{time: 0});
		}).mouseout(function(){
			layer.close(tips);
		});
    	//-------------------------广宣预警弹窗-------------------------
    	var currUser = null; //当前登录用户
    	// $('#lookoverWarn').on('click',warn);
    	// $('#confluenceWarn').on('click',confluenceWarn);
    	// confluenceWarn();
    	// warn();
    	/*
    	function confluenceWarn(){
			if(document.getElementById('warningConfluenceDiv')!=null){
				if(!currUser)
					$.ajax({
						url:'${ctx}/getCurrentUser',		//获取当前登录用户
						async:false,
						success:function(r){
							if(0==r.code)
								currUser = r.data;
						}
					})
				table.render({
					elem:'#warningConfluenceTable',
					url: '${ctx}/finance/allAttendancePay?warning=1&orgNameId='+(currUser.orgNameId?currUser.orgNameId:""),
					page: true,
					request:{ pageName: 'page' ,limitName: 'size'},
					parseData:function(r){
						$('#warnConfluenceNumber').html(r.data.total);
						return { code:r.code, data:r.data.rows, msg:r.message, count:r.data.total} },
					cols:[[
						   {type:'checkbox',},
					       {align:'center', title:'时间', field:'allotTime',},
					       {align:'center', title:'人员', field:'userName',},
					       {align:'center', title:'工作时长', field:'workTime', },
					       {align:'center', title:'出勤时长', field:'turnWorkTime',edit:true,},
					       {align:'center', title:'加班时长', field:'overTime',edit:true,},
					       ]],
				}) 
			    layer.open({
					title:'考勤错误预警',
					type:1,
					shadeClose: true,
					area:['50%','600px'],
					content:$('#warningConfluenceDiv'),
					success:function(){
						$('#verifyBtn').unbind().on('click',function(){
							var check = layui.table.checkStatus('warningConfluenceTable').data;
							if(check.length<1)
								return layer.msg('请选择相关数据',{icon:2});
							var ids = [];
							for(var i in check)
								ids.push(check[i].id);
							var loads = layer.load(1,{shade: [0.5,'black'] });
							$.ajax({
								url:'${ctx}/finance/checkAttendance?ids='+ids.join(','),
								async:false,
								success:function(r){
									var icon = 2;
									if(r.code==0){
										icon = 1;
										table.reload('warningConfluenceTable');
									}
									layer.msg(r.message,{ icon:icon });
								}
							})
							layer.close(loads);
						})
					}
				})  
				table.on('edit(warningConfluenceTable)',function(obj){
					var val = obj.value;
					var field = obj.field;
					var id = obj.data.id;
					var data = { id: id }, msg = '';
					isNaN(val) && (msg='请正确输入数字');
					val<0 && (msg='不能小于0');
					if(msg!=''){
						layer.msg(msg,{icon:2});
						table.reload('warningConfluenceTable');
						return;
					}
					data[field] = val;
					$.ajax({
						url:'${ctx}/finance/updateAttendance',
						data: data,
						success:function(r){
							var icon = 2;
							if(r.code==0){
								icon = 1;
								table.reload('warningConfluenceTable');
							}
							layer.msg(r.message,{icon:icon});
						}
					})
				})
			}
    	}
    	*/
    	/*
    	function warn(){
			if(document.getElementById("warningDiv")!=null){
				table.render({
					elem:'#warnTable',
					size:'lg',
					toolbar: true,
					url:'${ctx}/inventory/checkWarning',
					parseData:function(r){
						$('#warnNumber').html(r.data.length);
						layui.each(r.data,function(index,item){
							switch(item.type){
							case 1: item.typeText='库存下限预警';  break;
							case 2: item.typeText='库存上限预警';  break;
							case 3: item.typeText='库存时间过长预警';  break;
							}
						})
						return { code:r.code, data:r.data, msg:r.message, } },
					cols:[[
					       {align:'center', title:'预警仓库', field:'inventoryName',width : '15%',},
					       {align:'center', title:'商品名称', field:'name', width : '',},
					       {align:'center', title:'预警类型', field:'typeText',templet:'#typeTpl', width : '20%',},
					       {align:'center', title:'仓库数量', field:'countInventory', width : '10%',},
					       {align:'center', title:'销售数量', field:'countSales', width : '10%',},
					       ]],
				}) 
				layer.open({
					title:'仓库预警',
					type:1,
					shadeClose: true,
					area:['50%','80%'],
					content:$('#warningDiv'),
					success:function(){
						table.resize('warnTable');
					}
				})
				form.on('submit(searchWarnOfProduct)',function(obj){
					obj.field.skuCode = obj.field.skuCode.trim();
					table.reload('warnTable',{
						where : obj.field,
					})
				})
			}
		}
    	*/
    	
    	//--------------------广宣预警弹窗结束--------------------
    	$.ajax({
			url:"${ctx}/getTreeMenuPage",
			async:false,
			success: function (result) {
				if(0==result.code){
					var html='';
					var data=result.data;
					for(var i=0;i<data.length;i++){
						if(data[i].url!='#'){
							var href='${ctx}/menusToUrl?url='+data[i].url;
				    		html+='<li data-name="'+data[i].identity+'" class="layui-nav-item layui-this layui-anim">'+
				    					'<a lay-href="'+href+'" id="'+data[i].identity+'">'+
				    					'<i class="layui-icon layui-icon-'+data[i].icon+'"></i>  '+
										'<cite>'+data[i].name+'</cite></a></li>';
						}else{
							html+='<li data-name="'+data[i].identity+'" class="layui-nav-item layui-anim allMenu" >'+
									'<a href="javascript:;" lay-tips="'+data[i].name+'" lay-direction="2" id="'+data[i].identity+'">'+
										'<i class="layui-icon layui-icon-'+data[i].icon+'"></i>  '+
											'<cite>'+data[i].name+'</cite></a>'
							if(data[i].children!=null)
								html+=getChildren(data[i].children);
							html+='</li>';
						}
						
					}
					$('#LAY-system-side-menu').append(html);
				}
				
			}
    	})
    	$.ajax({
			url:"${ctx}/menus",
			async:false,
			success: function (result) {
				if(0==result.code){
					var html='';
					var data=result.data;
					for(var i=0;i<data.length;i++){
						if(data[i].url!='#'){
							var href='${ctx}/menusToUrl?url='+data[i].url;
				    		html+='<li data-name="'+data[i].identity+'" class="layui-nav-item layui-anim layui-this myMenu">'+
				    					'<a lay-href="'+href+'" id="'+data[i].identity+'">'+
				    					'<i class="layui-icon layui-icon-'+data[i].icon+'"></i>  '+
										'<cite>'+data[i].name+'</cite></a></li>';
						}else{
							html+='<li data-name="'+data[i].identity+'" class="layui-nav-item layui-anim myMenu" >'+
									'<a href="javascript:;" lay-tips="'+data[i].name+'" lay-direction="2" id="'+data[i].identity+'">'+
										'<i class="layui-icon layui-icon-'+data[i].icon+'"></i>  '+
											'<cite>'+data[i].name+'</cite></a>'
							if(data[i].children!=null)
								html+=getChildren(data[i].children);
							html+='</li>';
						}
					}
					$('#LAY-system-side-menu').append(html);
					$("#LAY-system-side-menu").find('span.layui-nav-bar').remove();
					element.render();
				}
				
			}
    	})
    	function getChildren(child){
    		var html='<dl class="layui-nav-child">';
    		for(var i=0;i<child.length;i++){
    			if(child[i].children!=null){
    				html+='<dd data-name="'+child[i].identity+'" class>'+
						'<a href="javascript:;" id="'+child[i].identity+'">'+
							'<i class="layui-icon layui-icon-'+child[i].icon+'"></i>  '+
							'<cite>'+child[i].name+'</cite></a>';
						
    			}else{
    				var href='${ctx}/menusToUrl?url='+child[i].url;
		    		html+='<dd data-name="'+child[i].identity+'" class>'+
		    					'<a lay-href="'+href+'" id="'+child[i].identity+'">'+
		    					'<i class="layui-icon layui-icon-'+child[i].icon+'"></i>  '+
								'<cite>'+child[i].name+'</cite></a>';
    			}
	    		if(child[i].children!=null)
	    			html+=getChildren(child[i].children); 
	    		html+='</dd>';
    		}
    		return html+'</dl>';
    	}
    	 //监听关闭点击事件
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
    		if(remeberMenu){
	    		var openMenu = layui.data('cookieMenu').openMenu;
	    		var thisMenu = $(this).closest('li').attr('lay-id');
	    		for(var i in openMenu){
	    			if(openMenu[i].id==thisMenu){
	    				openMenu.splice(i,1);
	    				layui.data('cookieMenu',{ key:'openMenu',value:openMenu });
	    				break;
	    			}
	    		}
	    		var newThisMenu = $('.layui-tab').find('li[class=layui-this]').attr('lay-id');
	    		if(thisMenu == newThisMenu){		//如果关闭的正是当前展示的
		    		layui.data('cookieMenu',{ key:'thisMenu',value: $(this).closest('li').prev().attr('lay-id') });
	    		}
    		}
    	});
    	//是否开启记忆tab
    	if(remeberMenu){
	    	//监听菜单点击事件
	    	$(document).on('mousedown', 'a[lay-href]', function (event) {
	    		var id = $(this).attr('lay-href');
	    		var name = $(this).find('cite').html();
	    		var openMenu = layui.data('cookieMenu').openMenu;
	    		var i = 0;
	    		for(;i<openMenu.length;i++){
	    			if(openMenu[i].id==id)
	    				break;
	    		}
	    		if(i>=openMenu.length){
	    			openMenu.push({
	    				id:id,
	    				name:name,
	    			});
	    			layui.data('cookieMenu',{ key:'openMenu',value:openMenu });
	    		}
	   			layui.data('cookieMenu',{ key:'thisMenu',value:id });
	    	})
	    	//关闭多个菜单事件
	    	$('.layadmin-pagetabs').find('.layui-nav-item').find('dd').click(function(){
	    		var event = $(this).attr('layadmin-event');
	    		var cookieMenu = layui.data('cookieMenu');
	    		var openMenu = cookieMenu.openMenu;
	    		var layuiThis = $('.layadmin-pagetabs').find('.layui-tab-title').find('li.layui-this');
	    		var thisId = $(layuiThis).attr('lay-id');
	    		switch(event){
	    		case 'closeThisTabs': 
	    			for(var i in openMenu){
	        			if(openMenu[i].id == thisId){
	        				openMenu.splice(i,1);
	        				break;
	        			}
	        		}
	    			layui.data('cookieMenu',{ key:'thisMenu',value: $(layuiThis).closest('li').prev().attr('lay-id') });
	    			break;
	    		case 'closeOtherTabs': 
	    			for(var i in openMenu){
	        			if(openMenu[i].id == thisId){
	        				openMenu = [openMenu[i]];
	        				break;
	        			}
	        		}
	    			break;
	    		case 'closeAllTabs': 
	    			openMenu = []; 
	    			layui.data('cookieMenu',{ key:'thisMenu',value: '' });
	    			break;
	    		}
	    		layui.data('cookieMenu',{ key:'openMenu',value:openMenu });
	    	})
	    	//tab切换事件
	    	element.on('tab(layadmin-layout-tabs)', function(obj){
	    		var id = $(obj.elem.context).attr('lay-id');
	    		if(id)
		    		layui.data('cookieMenu',{ key:'thisMenu',value:id });
	  		});
	    	//进入页面进行回显
	    	var cookie = layui.data('cookieMenu');
	    	var open = cookie.openMenu;
	    	for(var i in open){
	    		element.tabAdd('layadmin-layout-tabs', {
	   			  title: open[i].name,
	   			  id: open[i].id
	   			}); 
	    		$('.layui-body').append('<div class="layadmin-tabsbody-item">'+
	    	   			  '<iframe src="'+open[i].id+'" id="'+open[i].id+'" frameborder="0" class="layadmin-iframe"></iframe></div>')
	    	}
	    	if(cookie.thisMenu){
	    		$('.layui-body').find('.layadmin-tabsbody-item').removeClass('layui-show');
	    		var show = $('.layui-body').find('.layadmin-tabsbody-item').find('iframe[src="'+cookie.thisMenu+'"]');
	    		$(show).parent().addClass('layui-show');
	    		$('.layui-tab').find('li').removeClass('layui-this');
	    		$('.layui-tab').find('li[lay-id="'+cookie.thisMenu+'"]').addClass('layui-this');
	    		$('.layui-nav-tree').find('li').removeClass('layui-this');
	    		var thisA = $('a[lay-href="'+cookie.thisMenu+'"]');
	    		$(thisA).parent().addClass('layui-this');
	    	}
    	}
    	
    	
    	 //监听刷新事件
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
</html>