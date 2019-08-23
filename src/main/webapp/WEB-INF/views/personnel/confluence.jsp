<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
	<link rel="stylesheet" href="${ctx }/static/css/font-awesome.min.css">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script> 
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">	
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>考勤统计</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<style>
.layui-table td, .layui-table th {
    position: unset;
}
.compareTable td{
	border:1px solid #d2d2d2;
}
.compareTable tr:hover{
	background-color: #dafdf3;
} 
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-header">
		<ul>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #9e9e1f"></i>迟到</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #bf1515"></i>缺勤</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #00b0ff"></i>事假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #13161c"></i>病假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #b8c2d6"></i>丧假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #da06af"></i>婚假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #13a8bd"></i>产假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #1211e2"></i>护理假</li>
			<li style="display: inline;"><i class="fa fa-circle" style="color: #ff5b00"></i>抵消迟到</li>
		</ul>
	</div>
	<div class="layui-card-body">
		<div class="layui-form ">
			<div class="layui-form-item">
				<table>
					<tr>
						<shiro:lacksRole name="attendanceStatistician">
							<td>人员:</td>
							<td><select class="layui-input"  lay-search="true" id="userId" name="userId" >  
									<option value="">请选择</option></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>部门:</td>
							<td><select  id="orgNameId" class="layui-input"  lay-search="true" name="orgNameId">
									<option value="">请选择</option></select></td>
						</shiro:lacksRole>
						<td>&nbsp;&nbsp;</td>
						<td>考勤汇总月份:</td>
						<td><input name="orderTimeBegin" id="startTime" lay-verify="required" style="width: 200px;" placeholder="请输入考勤汇总月份" class="layui-input">
						</td>
						<td>&nbsp;&nbsp;</td>
						<td><div class="layui-inline">
								<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-search">查找考勤 </button>
							</div>
							<div class="layui-inline">
								<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-searche">统计考勤 </button>
							</div>
							<shiro:hasAnyRoles name="superAdmin,productEightTailor,productTwoMachinist,productTwoDeedle,productFristPack,productFristQuality,personnel">
								<div class="layui-inline">
									<button class="layui-btn" lay-submit id="personMachineCompare" >人机对比 </button>
								</div>
							</shiro:hasAnyRoles>
							<div class="layui-inline">标注行颜色：<div id="colorChoose" style="width:30px;height:30px;"></div></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="layui-hide" lay-filter="test3" id="test3"></table>
	</div>
</div>
 
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/',
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil: 'layui/myModules/myutil',
}).define(
	[ 'table', 'laydate', 'element', 'form','colorpicker' ,'myutil'],
	function() {
		var $ = layui.jquery, layer = layui.layer 
		, form = layui.form 
		, colorpicker = layui.colorpicker
		, table = layui.table
		, laydate = layui.laydate 
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug 
		, element = layui.element;
		
		var bgColorCol = []; 		//记录变色的列
		var bgColorRow = [];
		var bgColor = '#ffb800';	//默认颜色
		var LOAD = null;
		colorpicker.render({
		    elem: '#colorChoose'
		    ,color: '#ffb800' 
		    ,change: function(color){
		    	bgColor = color;
		    	layui.each(bgColorCol,function(index,item){
					layui.each($('.layui-table-body').find('td[data-key="'+item+'"]'),function(index1,item1){
						$(item1).css('background-color',bgColor);	//列变色
					})
				})
				layui.each(bgColorRow,function(index,item){
					$('.layui-table-body').find('tr[data-index="'+item+'"]').css('background-color',bgColor); 
				})
		    }
	  	});
		myutil.timeFormat();
		laydate.render({
			elem : '#startTime',
			type : 'month',
			value : new Date().format('yyyy-MM'),	//默认显示当月
		});
		//如果存在部门和人员选择下拉框
		var isAttend = true,orgId = '';	  //是否是考情记录员,和所在部门
		;!(function(){
			if(document.getElementById('userId')==null){
				;!(function(){
					$.ajax({
						url:'${ctx}/getCurrentUser',		
						async:false,
						success:function(r){
							if(0==r.code)
								orgId = r.data.orgNameId;
						}
					})
				})();
			}else{
				isAttend = false;
				(function(){
					var index = layer.load(1);
					$.ajax({
						url : '${ctx}/system/user/findAllUser',
						async : false,
						success : function(result) {
							var htmls='<option value="">请选择</option>';;
							$(result.data).each(function(i, o) {
								htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
							})
							$("#userId").append(htmls);
							form.render();
						},
					});
					$.ajax({
						url : "${ctx}/basedata/list?type=orgName",
						async : false,
						success : function(result) {
							var htmlfr = ""
							$(result.data).each(
								function(k, j) {
									htmlfr += '<option value="'+j.id+'">'+ j.name+ '</option>'
							});
							$("#orgNameId").append(htmlfr);
							layer.close(index);
						}
					});
					form.render('select');
					layer.close(index);
				})();
			}
			if(document.getElementById('personMachineCompare')!=null){
				var compareWin = '';
				$('#personMachineCompare').on('click',function(){
					if(!isAttend){
						orgId = $('#orgNameId').val();
						if(orgId=='')
							return layer.msg('请选择部门',{icon:2});
					}
					if($('#startTime').val()=='')
						return layer.msg('请填写日期',{icon:2});
					var html = '获取对比数据异常';
					$.ajax({
						url: '${ctx}/personnel/workshopAttendanceContrast?orgNameId='+orgId+'&orderTimeBegin='+$('#startTime').val()+'-01 00:00:00',
						async: false,
						success: function(r){
							var userData = {};
							if(r.code==0){
								html = '';
								if(r.data.length==0)
									html='<h3 style="text-align:center;color:#999;;">无对比差距</h3>';
								layui.each(r.data,function(index,item){		//根据人员分组
									var t = 'user_'+item.userId;
									if(!userData[t])
										userData[t] = [];
									userData[t].push(item);
								})
								layui.each(userData,function(index,item){
									var allSub = 0;
									layui.each(item,function(index1,item1){
										if(index1==0){
											html += ['<hr><h3 style="color:red;">考勤人员：'+item1.name+'</h3>',
													 '<table style="text-align:center;margin: auto;width:90%;" class="compareTable">',
													 	'<th>异常日期</th>',
													 	'<th>打卡出勤</th>',
													 	'<th>记录出勤</th>',
													 	'<th>打卡加班</th>',
													 	'<th>记录加班</th>',
													 	'<th>时差</th>',
													].join('');
										}
										var sub = (item1.clockInTurnWorkTime-(-item1.clockInOvertime)-item1.recordTurnWorkTime-item1.recordOverTime);
										allSub+=sub;
										var color = sub>0?"blue":"red";
										var trColor = '';
										if(item1.warning && item1.warning==1)
											trColor = 'red';
										html+= ['<tr style="background-color:'+trColor+'">',
													'<td><span class="layui-badge layui-bg-green">'+item1.date+'</span></td>',
													'<td>'+item1.clockInTurnWorkTime+'</td>',
													'<td>'+item1.recordTurnWorkTime+'</td>',
													'<td>'+item1.clockInOvertime+'</td>',
													'<td>'+item1.recordOverTime+'</td>',
													'<td style="color:'+color+'">'+sub+'</td>',
													'<td style="display:none;">'+item1.id+'</td>',	 //隐藏行id
												'</tr>',
											   ].join(' ');
										if(index1==item.length-1){
											html+='<tr><td><span class="layui-badge">总时差：</span></td><td></td><td></td><td></td><td></td><td style="color:red;">'+allSub+'</td></tr>'
											html+='</table>';
										}
									})
								})
							}else
								html = '<h3 style="text-align:center;color:#999;;">'+r.message+'</h3>';
						}
					})
					if(compareWin)
						layer.close(compareWin);
					compareWin = layer.open({
						type:1,
						title:'人机考勤对比',
						offset:'r',
						area:['28%','100%'],
						shade:0,
						content: html,
						success:function(){
							$('.compareTable').find('tr').unbind().on('click',function(obj){
								var id = $(obj.currentTarget.lastElementChild).html();
								var warning = 0;
								if(layui.getStyle(this, 'background-color')=='rgb(255, 0, 0)'){	//如果背景颜色是红的
									$(this).css('background-color','');
								}else{
									warning = 1;
									$(this).css('background-color','red');
								}
								$.ajax({
									url: '${ctx}/finance/updateAttendance?id='+id+'&warning='+warning,
								})
							})
						}
					})
				})
			}
		})();
		form.on('submit(LAY-role-search)', function(data) {
			var field=data.field
			if(!field.userId && !field.orgNameId && !isAttend){			
				layer.msg('请选择部门或者相关人员',{icon:2})
				return;
			}
			isAttend && (field.orgNameId = orgId);
			field.orderTimeBegin+="-01 00:00:00";
			var postUrl='${ctx}/personnel/intAttendanceTime';
			even(postUrl,field)
		})
		form.on('submit(LAY-role-searche)', function(data) {
			var field=data.field
			if(!field.userId && !field.orgNameId && !isAttend){			
				layer.msg('请选择部门获取相关人员',{icon:2})
				return;
			}
			var d={
					userId:$('#userId').val(),
					orgNameId:$('#orgNameId').val(),
					orderTimeBegin:$('#startTime').val()+'-01 00:00:00',
			}
			isAttend && (d.orgNameId = orgId);
			var postUrl='${ctx}/personnel/addAttendanceTime'
			even(postUrl,d)
		})
		var tipWin = '';//补签提示窗
		var lastDay = '', lastIndex = '';	//用于记录上次弹窗的td位置、防止移入同一个td时重复弹窗。
		var even = function(url, data) {
			LOAD = layer.load(1,{shade: [0.1,'black'] });
			table.render({
				elem : '#test3',
				size:'sm',
				url : url,
				loading:false,
				where : data,
				cellMinWidth : 80,
				height:'700px',
				method : 'POST',
				parseData : function(res) { return { "code" : res.code, "msg" : res.message, "data" : res.data,  }; },
				cols : [],
				done : function(res, curr, count) {
					layer.close(LOAD);
					var data = res.data;
					var list = [];
					var list1 = [];
					var list2 = [];
					var list3 = [];
					var a;
					var b;
					var c;
					var d;
					var length = data[0].attendanceTimeData.length
					var ID;
					$.each(data[0].attendanceTimeData,function(i, v) {
						ID = v.id;
						list[0] = {
							align : 'center',
							title : '<span style="color:red">姓名</span>',
							width : 80,
							fixed : 'left',
							style : 'background-color: #5FB878;color: #fff',
							rowspan : 3,
							templet : function(d) {return d.attendanceTimeData[i].userName}
						};
						list[length + 1] = {
							align : 'center',
							title : '<span style="color:red">出勤</span>',
							fixed : 'right',
							width : 70,
							style : 'background-color: #5FB878;color: #fff',
							rowspan : 3,
							templet : function(d) {return '<span id="1">'+d.collect.turnWork+'<span>'}
						};
						list[length + 2] = {
							align : 'center',
							title : '<span style="color:red">加班</span>',
							fixed : 'right',
							width : 70,
							style : 'background-color: #5FB878;color: #fff',
							rowspan : 3,
							templet : function(d) {return d.collect.overtime}
						};
						list[length + 3] = {
							align : 'center',
							title : '<span style="color:red">缺勤</span>',
							fixed : 'right',
							width : 70,
							style : 'background-color: #5FB878;color: #fff',
							rowspan : 3,
							templet : function(d) {return d.collect.dutyWork}
						};
						list[length + 4] = {
								align : 'center',
								title : '<span style="color:red">调休</span>',
								fixed : 'right',
								width : 70,
								style : 'background-color: #5FB878;color: #fff',
								rowspan : 3,
								templet : function(d) {return d.collect.takeWork}
						};
						list[length + 5] = {
							align : 'center',
							title : '<span style="color:red">总出勤</span>',
							fixed : 'right',
							width : 80,
							style : 'background-color: #5FB878;color: #fff',
							rowspan : 3,
							templet : function(d) {return d.collect.allWork}
						};
						list[i + 1] = {
							align : 'center',
							title : v.time,
							colspan : 4
						};
						list1[i] = {
							align : 'center',
							title : v.week,
							colspan : 4,
						};
						a = {
							align : 'center',
							title : '出勤',
							event: 'tdClick-0-'+i,		//用于点击变色事件
							templet : function(d) {
								if (d.attendanceTimeData[i].turnWorkTime == 0)
									return '';
								else
									return d.attendanceTimeData[i].turnWorkTime;
							}
						}
						b = {
							align : 'center',
							title : '加班',
							event: 'tdClick-1-'+i,
							templet : function(d) {
								if (d.attendanceTimeData[i].overtime == 0)
									return '';
								else
									return d.attendanceTimeData[i].overtime;
							}
						};
						c = {
							align : 'center',
							title : '缺勤',
							event: 'tdClick-2-'+i,
							templet : function(d) {
								if (d.attendanceTimeData[i].dutytime == 0)
									return '';
								else
									var colo;
									if(d.attendanceTimeData[i].flag == 0)
										colo='#fff';
									if(d.attendanceTimeData[i].flag ==1)
										colo='#bf1515';
									if(d.attendanceTimeData[i].flag==2)
										if(d.attendanceTimeData[i].holidayType==0)
											colo='#00b0ff';
										if(d.attendanceTimeData[i].holidayType==1)
											colo='#13161c';
										if(d.attendanceTimeData[i].holidayType==2)
											colo='#b8c2d6';
										if(d.attendanceTimeData[i].holidayType==3)
											colo='#da06af';
										if(d.attendanceTimeData[i].holidayType==4)
											colo='#13a8bd';
										if(d.attendanceTimeData[i].holidayType==5)
											colo='#1211e2';
										if(d.attendanceTimeData[i].holidayType==6)
											colo='#ff5b00';
									return '<div style="background-color:'+colo+';color: #fff">'+d.attendanceTimeData[i].dutytime+'</div>';
						 	}
						 }
						 d = {
								align : 'center',
								title : '迟到',
								event: 'tdClick-3-'+i,
								templet : function(d) {
									if (d.attendanceTimeData[i].belateTime == 0)
										return '';
									else
										return '<div style="background-color:#9e9e1f;color: #fff">'+d.attendanceTimeData[i].belateTime+'</div>';
								}
						 }
						list3.push(a);
						list3.push(b);
						list3.push(c);
						list3.push(d);
					});
					list2.push(list)
					list2.push(list1)
					list2.push(list3)
					table.init('test3', {
						cols : list2,
						data : res.data,
						limit:500,
						loading:false,
						height: '700px',
					});
					layui.each($('td[data-field="0"]'),function(i,item){
						$(item).unbind().on('mouseover',function(){
							var elem = $(item);
							var index = elem.closest('tr').data('index');
							var day = parseInt(elem.attr('data-key').split('-')[2]/4);
							if(day == lastDay && index == lastIndex)	//判断本次移入是否与上一次位置一致
								return;
							lastDay = day;
							lastIndex = index;
							var trData = table.cache['test3'][index];
							var dayData = trData.attendanceTimeData[day];
							var html = ['<div>',
							            	'<p style="text-align:center;">当日打卡记录</p>',
							            	'签入时间：'+(dayData.checkIn || '----')+'<br>',
							            	'签出时间：'+(dayData.checkOut || '----')+'<br>',
							            	'<span class="layui-badge" data-id="'+dayData.userId+'" data-type="0" data-day="'+dayData.time+'">补签入</span>',
							            	'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
							            	'<span class="layui-badge layui-bg-green" data-id="'+dayData.userId+'" ',
							            			'data-type="1" data-day="'+dayData.time+'">补签出</span>',
							            '</div>',
							            ].join(' ');
							tipWin = layer.tips(html, elem, {
								tips: [4, '#78BA32'],
			                	time:0,
				            }); 
							$('.layui-layer-tips .layui-badge').unbind().on('click',function(event){
								layui.stope(event);
								lastDay = ''; lastIndex = '';
								var userId = $(event.target).data('id')
								   ,type = $(event.target).data('type')
								   ,day = $(event.target).data('day');
								myutil.saveAjax({
									url:"${ctx}/personnel/defaultRetroactive",
									type:'get',
									data:{
										userId: userId,
										writeTime: day+' 00:00:00',
										sign: type,
									}
								})
							}).mouseover(function(){
					    		$(this).css("cursor","pointer");								
					    	}).mouseout(function (){  
					    		$(this).css("cursor","default");
					        });
						});
					})
				},
			});
			$(document).on('mousedown', '', function (event) { //关闭提示窗
				if($('.layui-layer-tips').length>0 && !$(event.target).data('day')){
					lastDay = ''; lastIndex = '';
					layer.close(tipWin);
				}
			});
			table.on('tool(test3)', function(obj){			//自定义事件名  tdClick-0123(代表出勤、加班、缺勤..)-i  (代表的是第几个)
				//自定义事件名主要用于计算点击的td的 data-key值（对应点击的列数）  2-2-() 
				//对应的key值计算
				var event = (obj.event).split('-');
				var key = event[2]*4-(-event[1]);
				var tableKey = $($('.layui-table-body').find('td')[1]).attr('data-key').split('-');
				var dataKey = tableKey[0]+'-'+tableKey[1]+'-'+key;
				layui.each($('.layui-table-body').find('td[data-key="'+dataKey+'"]'),function(index,item){
					$(item).css('background-color',bgColor);	//列变色
				})
				bgColorCol.push(dataKey);
				bgColorRow.push(obj.tr[0].dataset.index);
				//行变色
				$(obj.tr[0]).css('background-color',bgColor)
			});
			table.on('rowDouble(test3)',function(obj){
				layui.each(bgColorCol,function(index,item){
					layui.each($('.layui-table-body').find('td[data-key="'+item+'"]'),function(index1,item1){
						$(item1).css('background-color','');	//列变色
					})
				})
				bgColorCol = [];
				bgColorRow = [];
				layui.each($('.layui-table-body').find('tr'),function(index,item){
					$(item).css('background-color','#ffffff');
				})
			})
		};
})
</script>
</body>
</html>