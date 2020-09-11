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
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>考勤总汇</title>
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<head>
<style>
	.compareTable td{
		border:1px solid #d2d2d2;
	}
	.compareTable tr:hover{
		background-color: #dafdf3;
		cursor: pointer;
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
			<li style="display: inline;"><i class="fa fa-circle" style="color: #12b119"></i>哺乳假</li>
		</ul>
	</div>
	<div class="layui-card-body">
		<div class="layui-form">
			<div class="layui-form-item">
				<table>
					<tr>
						<td>部门:</td>
						<td><select id="orgNameId" lay-filter="orgNameId" class="form-control search-query name" lay-search="true" name="orgNameId"><option value="">请选择</option></select></td>
						<td>&nbsp;&nbsp;</td>
						<td>人员:</td>
						<td><select id="userId" class="form-control search-query name"  lay-search="true" name="userId"></select></td>
						<td>&nbsp;&nbsp;</td>
						<td>考勤汇总月份:</td>
						<td><input name="orderTimeBegin" autocomplete="off" id="startTime" style="width: 200px;" placeholder="请输入考勤汇总月份" lay-verify="required" class="layui-input laydate-icon"></td>
						<td>&nbsp;&nbsp;</td>
						<td>
							<div class="layui-inline">
								<button type="button" class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-role-searche">查找考勤</button>
								<button type="button" class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-sealAttendanceCollect" id="sealAttendanceCollect">存档</button>
							</div>
							<shiro:hasAnyRoles name="superAdmin,productEightTailor,productTwoMachinist,productTwoDeedle,productFristPack,productFristQuality,personnel">
								<div class="layui-inline">
									<button class="layui-btn" lay-submit id="personMachineCompare" >人机对比 </button>
								</div>
							</shiro:hasAnyRoles>
						</td>
						<td>标注行颜色：<div id="colorChoose" style="width:30px;height:30px;"></div></td>
						<td>&nbsp;&nbsp;</td>
						<td>
							<button type="button" class="layui-btn" id="clearColor">清空标注</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="layui-tab">
			<ul class="layui-tab-title">
				<li class="layui-this">考勤修改</li>
				<li>考勤汇总</li>
			</ul>
			<div class="layui-tab-content">
				<div class="layui-tab-item layui-show" id="div-test3">
					<table class="layui-hide" lay-filter="test3" id="test3"></table>
				</div>
				<div class="layui-tab-item" id="div-test5">
					<table class="layui-hide" lay-filter="test5" id="test5"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug'
}).define(
	[ 'table', 'laydate', 'element', 'form','colorpicker'],
	function() {
		var $ = layui.jquery, layer = layui.layer 
		, form = layui.form 
		, table = layui.table 
		, colorpicker = layui.colorpicker
		, laydate = layui.laydate
		, tablePlug = layui.tablePlug
		, element = layui.element;
		
		form.on('select(orgNameId)', function(obj){
			$.ajax({
				url: '${ctx}/system/user/findUserList?foreigns=0&isAdmin=false&orgNameIds='+obj.value,
				success: function(result) {
					var htmls = '<option value="">请选择</option>';
					$(result.data).each(function(i, o) {
						htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
					})
					$('#userId').val('');
					$('#userId').html(htmls);
					form.render('select');
				},
			});
		});
		
		
		laydate.render({
			elem : '#startTime',
			type : 'month',
		});
		;!(function(){
			if(document.getElementById('personMachineCompare')!=null){
				var compareWin = '';
				$('#personMachineCompare').on('click',function(){
					orgId = $('#orgNameId').val();
					if(orgId=='')
						return layer.msg('请选择部门',{icon:2});
					var html = '获取对比数据异常';
					var loads = layer.load(1,{shade: [0.5,'black'] })
					$.ajax({
						url: '${ctx}/personnel/workshopAttendanceContrast?orgNameId='+orgId+'&orderTimeBegin='+$('#startTime').val()+'-01 00:00:00',
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
										sub==0 && ( color = 'green');
										var trColor = '';
										if(item1.warning && item1.warning==1)
											trColor = '#ff5722';
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
										if($(this).next().length==0)
											return;
										var id = $(obj.currentTarget.lastElementChild).html();
										var warning = 0;
										if(layui.getStyle(this, 'background-color')=='rgb(255, 87, 34)'){	//如果背景颜色是橙色的
											$(this).css('background-color','');
										}else{
											warning = 1;
											$(this).css('background-color','#ff5722');
										}
										$.ajax({
											url: '${ctx}/finance/updateAttendance?id='+id+'&warning='+warning,
										})
									})
								}
							})
							layer.close(loads);
						}
					})
				})
			}
		})();
		var bgColorCol = []; 		//记录变色的列
		var bgColorRow = [];
		var bgColor = '#ffb800';	//默认颜色
		colorpicker.render({
		    elem: '#colorChoose'
		    ,color: '#ffb800' 
		    ,change: function(color){
		    	bgColor = color;
		    	layui.each(bgColorCol,function(index,item){
					layui.each($('div[lay-id="test3"]').find('.layui-table-body').find('td[data-key="'+item+'"]'),function(index1,item1){
						$(item1).css('background-color',bgColor);	
					})
				})
				layui.each(bgColorRow,function(index,item){
					$('div[lay-id="test3"]').find('.layui-table-body').find('tr[data-index="'+item+'"]').css('background-color',bgColor); 
				})
		    }
	  	});
		$('#clearColor').on('click',function(){
			layui.each(bgColorCol,function(index,item){
				layui.each($('div[lay-id="test3"]').find('.layui-table-body').find('td[data-key="'+item+'"]'),function(index1,item1){
					$(item1).css('background-color','');
				})
			})
			layui.each(bgColorRow,function(index,item){
				$('div[lay-id="test3"]').find('.layui-table-body').find('tr[data-index="'+item+'"]').css('background-color',''); 
			})
			bgColorCol = []; 		
			bgColorRow = [];
		})
		var onlyField={	};
		form.on('submit(LAY-role-searche)', function(data) {
			var field=data.field
			if(!field.userId && !field.orgNameId){			
				layer.msg('请选择部门获取相关人员',{icon:2})
				return;
			}
			field.orderTimeBegin = field.orderTimeBegin+"-01 00:00:00";
			onlyField = field;
			even(onlyField)
			event(onlyField)
		})
		form.on('submit(LAY-sealAttendanceCollect)', function() {  //进行存档
			var load = layer.load(1);
			$.ajax({
				url:"${ctx}/personnel/sealAttendanceCollect",
				data:onlyField,
				async:false,
				type:"POST",
				success:function(result){
					var icon = 2;
					if(0==result.code)
						icon = 1;
					layer.msg(result.message, {icon: icon});
				}
			}); 
			layer.close(load);
		})
		var even = function(data) {
			table.render({
				elem : '#test3',
				size:'sm',
				url : '${ctx}/personnel/findAttendanceTime',
				where :data,
				cellMinWidth : 80,
				parseData : function(res) {  return { "code" : res.code,  "msg" : res.message, "data" : res.data, "countd":0 }; },
				cols : [],
				done : function(res, curr, count) {
					var data = res.data;
					var list = [];
					var list1 = [];
					var list2 = [];
					var list3 = [];
					var a;
					var b;
					var c;
					var d;
					var length = data[0]? data[0].attendanceTimeData.length : 0;
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
							width : 90,
							style : 'background-color: #5FB878;color: #fff',
							rowspan : 3,
							templet : function(d) {return d.collect.allWork}
						};
						list[i + 1] = {align : 'center', title : v.time, colspan : 4 };
						list1[i] = { align : 'center', title : v.week, colspan : 4 };
						a = {
							align : 'center',
							title : '出勤',
							event: 'tdClick-0-'+i,
							edit: 'text',
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
							edit: 'text',
							templet : function(
									d) {
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
							edit: 'text',
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
										if(d.attendanceTimeData[i].holidayType==7)
											colo='#12b119';
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
						height:'650px',
						cols : list2,
						limit: 500,
						data : res.data,
					});
					$('.layui-table-fixed-l').find('.layui-table-body').find('tr').on('click',function(){
						var index = $(this).attr('data-index');
						bgColorRow.push(index);
						$('div[lay-id="test3"]').find('.layui-table-body').find('tr[data-index='+index+']').css('background-color',bgColor);
					})
					$($('div[lay-id="test3"]').find('.layui-table-header').find('tr')[2]).find('th').on('click',function(){
						var index = $(this).attr('data-field');
						var key = $($('div[lay-id="test3"]').find('.layui-table-body').find('td')[1]).attr('data-key').split('-');
						var dataKay = key[0]+'-'+key[1]+'-'+index;
						bgColorCol.push(dataKay)
						$('div[lay-id="test3"]').find('.layui-table-body').find('td[data-key='+dataKay+']').css('background-color',bgColor);
					})
				},
			});
		}
		//考勤汇总
		var event = function(data) {
			 table.render({
				elem: '#test5',
				size: 'lg',
				url: '${ctx}/personnel/findAttendanceCollect',
				where :data,
				height:'700px',
				method : 'POST',
				toolbar: true, //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
				parseData: function(ret) { 
					for(var k in ret.data){
						ret.data[k].belateDetails = ret.data[k].belateDetails.split(',').join('。');
						ret.data[k].leaveDetails = ret.data[k].leaveDetails.split(',').join('。');
						ret.data[k].dutyDetails = ret.data[k].dutyDetails.split(',').join('。');
						ret.data[k].takeDetails = ret.data[k].takeDetails.split(',').join('。');
					}
					return { code: ret.code, msg: ret.message, count:ret.data.total, data: ret.data } 
				},
				cols: [
					[ { field: "userName", title: "人名", align: 'center',
					},{ field: "leaveDetails", title: "请假详情", align: 'center',
					},{ field: "belateDetails", title: "迟到详情", align: 'center', width:500,
					},{ field: "dutyWork", title: "请假时长", align: 'center',
					},{ field: "dutyDetails", title: "缺勤事项详情", align: 'center',
					},{ field: "takeDetails", title: "调休事项详情", align: 'center',
					},{ field: "turnWork", title: "出勤时长", align: 'center',
					},{ field: "ordinaryOvertime", title: "普通加班时长", align: 'center',
					},{ field: "productionOvertime", title: "生产加班时长", align: 'center',
					},{ field: "overtime", title: "总加班时长", align: 'center',
					},{ field: "remarks", title: "备注", align: 'center', edit:'text'
					},{ field: "sign", title: "签字", align: 'center',
					}]
				],
			});
		}		
		table.on('edit(test5)', function(obj) {
			var field = obj.field;
			var postData={
				id: obj.data.id,
			}
			postData[field] = obj.value;
			var load = layer.load(1);
		    $.ajax({
				url:"${ctx}/personnel/updateAttendanceCollect",
				data:postData,
				async:false,
				type:"POST",
				success:function(r){
					var icon = 2;
					if(0==r.code)
						icon = 1;
					layer.msg(r.message, {icon:icon});
				}
			}); 
		    layer.close(load);
		})	
		table.on('edit(test3)', function(obj) {
			var that=this
			var tde = $(that).closest('td')
			var key=tde[0].dataset.key
			var s=key.lastIndexOf("-")+1
			var indexoff= key.substring(s,key.length)//为了得到是第几个数组
			var indexof=parseInt(indexoff)+1
			var a;
			if((indexof)/4<1)
				a=0
			else if((indexof)/4>=1)
				a=parseInt((indexof)/4)
			var id=obj.data.attendanceTimeData[a].id
			var value = obj.value 
			var field;
			if(obj.field==0)
				field='turnWorkTime'
			if(obj.field==1)
				field='overtime'
			if(obj.field==2){
				field='dutytime'
			}
			var postData={
					id:id,
			}
			postData[field] = value;
			var load = layer.load(1);
			$.ajax({
				url:"${ctx}/personnel/updateAttendanceTime",
				data:postData,
				type:"POST",
				async:false,
				success:function(r){
					var icon = 2;
					if(0==r.code){
						icon = 1;
						even(onlyField);
					}
					layer.msg(r.message, {icon: icon});
				}
			}); 
		    layer.close(load);
		});
		//---------------还原变色------------------------------------------------------------
		var cssEdit = '';           //记录编辑单元格的颜色，用于还原
		var tdElem = null;
		$(document).on('mouseenter', 'td[data-edit="text"]', function (event) {		//进入单元格td
			if($('.layui-table-edit').length==0){									//当前页面不存在编辑框
				cssEdit = '';
				if($(this).find('div').find('div').length>0)
					cssEdit = $(this).find('div').find('div').css('background-color');	//记录进入td的div样式
			}
		}).on('focus','.layui-table-edit',function(){							//编辑框获得焦点，记录编辑框所在td
			tdElem = $(this).parent().find('div')[0];								
		}).on('blur', '.layui-table-edit', function (event) {					//编辑框失去焦点，还原编辑框样式
			if(cssEdit!='')
				$(tdElem).html('<div style="background-color:'+cssEdit+';color: #fff">'+$(this).val()+'</div>')
		});
		
		
		;(function(){
			var index = layer.load(1);
			$.ajax({
				url : '${ctx}/system/user/findAllUser', 
				async : false,
				success : function(result) {
					var htmls = '<option value="">请选择</option>';
					$(result.data).each(function(i, o) {
						htmls += '<option value=' + o.id + '>'+ o.userName+ '</option>'
					})
					$("#userId").append(htmls);
				},
			});
			$.ajax({
				url : "${ctx}/basedata/list?type=orgName",
				async : false,
				success : function(result) {
					var htmlfr = ""
					$(result.data).each(function(k, j) {
						htmlfr += '<option value="'+j.id+'">'+ j.name+ '</option>'
					});
					var htmlth = '<select name="orgNameId" id="selectOrgNameId" class="form-control" lay-search="true"><option value="">请选择</option>'
								+ htmlfr + '</select>'
					$("#orgNameId").html(htmlth);
					
				}
			});
			form.render('select');
			layer.close(index);
		})();
})
</script>
</body>
</html>