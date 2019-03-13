<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>考勤总汇</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
</head>

<body>

	<section id="main-wrapper" class="theme-default">
		<%@include file="../decorator/leftbar.jsp"%>
		<!--main content start-->
		<section id="main-content" class="animated fadeInUp">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">总汇信息</h3>
							<div class="actions pull-right">
								<i class="fa fa-expand"></i>
								<i class="fa fa-chevron-down"></i>
							</div>
						</div>
						<div class="row" style="height: 30px; margin: 15px 0 10px">
							<div class="col-xs-12 col-sm-12  col-md-12">
								<form class="form-search">
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12">
											<div class="input-group">
												<table>
													<tr>
														<td>员工姓名:</td>
														<td>
															<input type="text" id="name" class="form-control name" />
														</td>
														<td>&nbsp&nbsp</td>
														<td>员工部门:</td>
														<td id="department"></td>
														<td>&nbsp&nbsp</td>
														<td>签入签出时间:</td>
														<td>
															<input id="startTime"  placeholder="请输入签到区间" class="form-control laydate-icon">
														</td>
														<td>&nbsp&nbsp</td>

														<td>上班下班时间:</td>
														<td>
															<input id="startTime1" style="width: 160px;" placeholder="请输入上班下班区间" class="form-control laydate-icon">
														</td>

														<td>&nbsp&nbsp</td>
														<td>休息区间:</td>
														<td>
															<input id="restBeginTime" style="width: 160px;" placeholder="请输入休息区间" class="form-control laydate-icon">
														</td>
													</tr>
												</table>
												<span class="input-group-btn">
													<button type="button" class="btn btn-default btn-square btn-sm btn-3d  searchAtt">查&nbsp找</button>
												</span>
												&nbsp
												<span class="input-group-btn">
													<button type="button" id="export" class="btn btn-success btn-sm btn-3d pull-right">导出考勤</button>
												</span>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="panel-body">
							<!-- <table class="layui-table" lay-data="{width:800, url:'/test/table/demo2.json?v=2', page: true, limit: 6, limits:[6]}">
  <thead>
    <tr>
      <th lay-data="{checkbox:true, fixed:'left'}" rowspan="2"></th>
      <th lay-data="{field:'username', width:150}" rowspan="2">联系人</th>
      <th lay-data="{align:'center'}" colspan="3">地址</th>
      <th lay-data="{field:'amount', width:120}" rowspan="2">金额</th>
      <th lay-data="{fixed: 'right', width: 160, align: 'center', toolbar: '#barDemo'}" rowspan="2">操作</th>
    </tr>
    <tr>
      <th lay-data="{field:'province', width:120}">省</th>
      <th lay-data="{field:'city', width:120}">市</th>
      <th lay-data="{field:'zone', width:200}">区</th>
    </tr>
  </thead>
</table> -->
							<table class="layui-hide" lay-filter="test3" id="test">

							</table>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>

	</section>



	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctx }/static/plugins/navgoco/jquery.navgoco.min.js"></script>
	<script src="${ctx }/static/plugins/switchery/switchery.min.js"></script>
	<script src="${ctx }/static/plugins/pace/pace.min.js"></script>
	<script src="${ctx }/static/plugins/fullscreen/jquery.fullscreen-min.js"></script>
	<script src="${ctx }/static/js/src/app.js"></script>
	<script src="${ctx }/static/js/laypage/laypage.js"></script>
	<script src="${ctx }/static/plugins/dataTables/js/jquery.dataTables.js"></script>
	<script src="${ctx }/static/plugins/dataTables/js/dataTables.bootstrap.js"></script>
	<script src="${ctx }/static/js/vendor/typeahead.js"></script>
	<script src="${ctx }/static/js/laydate/laydate.js"></script>
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	<script>
		laydate.render({
			elem : '#startTime',
			type : 'datetime',
			range : '~'
		});

		laydate.render({
			elem : '#startTime1',
			type : 'time',
			range : '~'

		});
		laydate.render({
			elem : '#restBeginTime',
			type : 'time',
			range : '~'

		});
		$('.searchAtt').on('click', function(){
		layui.use('table', function(){
			  var table = layui.table;
			  var form = layui.form;
			  var startTime = $("#startTime").val()
				var arr = startTime.split("~");
				var startTime1 = $("#startTime1").val()
				var arr1 = startTime1.split("~");
				var restBeginTime=$("#restBeginTime").val()
				var arr2 = restBeginTime.split("~");
			  table.render({
			    elem: '#test'
			    ,url:'${ctx}/personnel/findAttendanceTime'
			    ,where: {userName : $('#name').val(),
					orgNameId : $(".selectgroupChange").val(),
					orderTimeBegin : arr[0],
					orderTimeEnd : arr[1],
					workTimeBegin : arr1[0],
					workTimeEnd : arr1[1],
					restBeginTime : arr2[0],
					restEndTime:arr2[1],} 
			    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
			    , method:'GET'
			    	
			  ,parseData: function(res){ //res 即为原始返回的数据
		         	res.data.code=0
		             return {
		               "code": res.data.code, //解析接口状态
		               "msg": res.data.message, //解析提示文本
		               "count": res.data.total, //解析数据长度
		               "data": res.data //解析数据列表
		             };
		           }
			    ,cols: []
			    ,done: function(res, curr, count){
			    	 var data = res.data;
		                var list = [];
		                var list1 = [];
		                var list2=[];
		                var list3=[];
		                var a;
		                var b;
		                var c;
		                var length=data[0].attendanceTimeData.length
			    	$.each(data[0].attendanceTimeData,function(i,v){
			    		list[0]={align: 'center',title:'<span style="color:red">姓名</span>',width:80,fixed: 'left',style: 'background-color: #5FB878;color: #fff',rowspan:3,templet:function(d){
	                		return d.attendanceTimeData[i].username	
	                	} }; 
			    		list[length+1]={align: 'center',title:'<span style="color:red">出勤</span>',fixed: 'right',width:70,style: 'background-color: #5FB878;color: #fff', rowspan:3,templet:function(d){
	                		return d.collect.turnWork	
	                	} };
			    		list[length+2]={align: 'center',title: '<span style="color:red">加班</span>',fixed: 'right',width:70,style: 'background-color: #5FB878;color: #fff',rowspan:3,templet:function(d){
	                		return d.collect.overtime	
	                	} };
			    		list[length+3]={align: 'center',title: '<span style="color:red">缺勤</span>',fixed: 'right',width:70,style: 'background-color: #5FB878;color: #fff',rowspan:3,templet:function(d){
	                		return d.collect.dutyWork	
	                	} };
			    		list[length+4]={align: 'center',title: '<span style="color:red">总出勤</span>',fixed: 'right',width:70,style: 'background-color: #5FB878;color: #fff',rowspan:3,templet:function(d){
	                		return d.collect.allWork	
	                	} };
	                	list[i+1]={
	                			align: 'center', title:v.time, colspan: 3
	                	};
	                	list1[i]={
	                			align: 'center', title:v.week, colspan: 3
		                	};
	                	a={align: 'center',title: '出勤',templet:function(d){
	                		if(d.attendanceTimeData[i].turnWorkTime==null) return ''; 
	                		else  return d.attendanceTimeData[i].turnWorkTime;
	                	} }
				    	b={align: 'center',title: '加班',templet:function(d){
				    		if(d.attendanceTimeData[i].overtime==null) return ''; 
	                		else  return d.attendanceTimeData[i].overtime;
	                	} };
				    	c={align: 'center',title: '缺勤',templet:function(d){
				    		if(d.attendanceTimeData[i].dutytime==null) return ''; 
	                		else  return d.attendanceTimeData[i].dutytime;
	                	} }
			    	list3.push(a);
				    list3.push(b);
				    list3.push(c)
	                });
			    	
			    	list2.push(list)
			    	list2.push(list1)
			    	list2.push(list3)
			    	table.init('test3', {
	                	 cols:list2
	                	,data:res.data
	                	,limit:500
	                });
			      } 
		          ,id: 'testReload'
			    ,page: false
			  });
			  
		
			  //查询
			/* $('.searchAtt').on('click', function(){
				//执行重载
				var startTime = $("#startTime").val()
					var arr = startTime.split("~");
					var startTime1 = $("#startTime1").val()
					var arr1 = startTime1.split("~");
					
			      table.reload('testReload', {
			        where: {
			        	userName : $('#name').val(),
						orgNameId : $(".selectgroupChange").val(),
						orderTimeBegin : arr[0],
						orderTimeEnd : arr[1],
						workTimeBegin : arr1[0],
						workTimeEnd : arr1[1],
						restTime : $("#restTime").val(),
			        }
			      });
				  }); */
				});
		});
		
		//部门遍历
		var indextwo;
		var htmlth = '';
		var htmlfr = '';
		var getdata = {
			type : "orgName",
		}
		$.ajax({
			url : "${ctx}/basedata/list",
			data : getdata,
			type : "GET",
			beforeSend : function() {
				indextwo = layer.load(1, {
					shade : [ 0.1, '#fff' ]
				//0.1透明度的白色背景
				});
			},
			success : function(result) {
				$(result.data).each(function(k, j) {
					htmlfr += '<option value="'+j.id+'">' + j.name + '</option>'
				});
				var htmlth = '<select class="form-control  selectgroupChange"><option value="">请选择</option>' + htmlfr + '</select>'
				$("#department").html(htmlth);
				layer.close(indextwo);
			}
		});
		
		//导出考勤
		$('#export').on(
				'click',
				function() {
					var startTime = $("#startTime").val()
					var arr = startTime.split("~");
					var startTime1 = $("#startTime1").val()
					var arr1 = startTime1.split("~");
					//参数
					var userName = $('#name').val();
					var orgNameId = $(".selectgroupChange").val();
					var orgName =   $(".selectgroupChange").find("option:selected").text();
					var orderTimeBegin = arr[0];
					var orderTimeEnd = arr[1];
					var workTimeBegin = arr1[0];
					var workTimeEnd = arr1[1];
					var restTime = $("#restTime").val();
					location.href = "${ctx}/excel/importExcel/personnel/DownAttendance?userName=" + userName + "&orgNameId=" + orgNameId + "&orderTimeBegin=" + orderTimeBegin
							+ "&orderTimeEnd=" + orderTimeEnd + "&workTimeBegin="+workTimeBegin+"&workTimeEnd="+workTimeEnd+" &restTime="+restTime+"&orgName="+orgName+"";
				})
		
	</script>

</body>

</html>