<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>杂工管理</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="${ctx }/static/sxjg/index.css">
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/layer/layer.js"></script>	
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
	<link rel="stylesheet" href="${ctx }/static/css/main.css">
	<script src="${ctx }/static/js/laypage/laypage.js"></script>
	<script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
</head>

<body>
	
<div class="panel panel-default">
	<div class="panel-body">					
		<!--查询开始  -->	
		<table>
			<tr>
				<td>批次名:</td>
				<td><input type="text" name="number" id="number" placeholder="请输入批次号"
					class="form-control search-query number" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>工序名称:</td>
				<td><input type="text" name="name" id="name" placeholder="请输入产品名称"
					class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>开始时间:</td>
				<td><input id="startTime" placeholder="请输入开始时间" style="width: 230px;" class="form-control"></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="input-group-btn">
						<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask">查&nbsp找</button></span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><span class="input-group-btn">
						<button type="button" id="addgroup"class="btn btn-success btn-sm btn-3d pull-right">新增杂工</button></span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><span class="input-group-btn">
					<button type="button"class="btn btn-success  btn-sm btn-3d addDict">加绩</button></span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td class="visible-sm"><span class="input-group-btn">
					<button type="button"class="btn btn-success  btn-sm btn-3d update">杂工修改</button></span></td>	
			</tr>
		</table>
		<h1 class="page-header"></h1>		
		<table class="table table-hover">
			<thead>
				<tr>
					<th class="center"><label> <input type="checkbox"
							class="ace checks" /> <span class="lbl"></span>
					</label></th>
					<th class="text-center">批次名</th>
					<th class="text-center">日期</th>
					<th class="text-center">工序名</th>
					<th class="text-center">现场管理时间</th>
					<th class="text-center">备注</th>
					<th class="text-center hidden-sm">任务价值</th>
					<th class="text-center hidden-sm">B工资净值</th>
					<th class="text-center">添加工价</th>
					<th class="text-center">加绩工资</th>
					<th class="text-center">人员详情</th>
					<th class="text-center visible-sm">状态</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
			<tbody id="tablecontent">

			</tbody>
			<thead>
				<tr>
					<td class="text-center hidden-sm">合计</td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center hidden-sm" id="totale"></td>
					<td class="text-center hidden-sm" id="totaltw"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
					<td class="text-center"></td>
				</tr>
			</thead>
		</table>
		<div id="pager" class="pull-right"></div>
	</div>
</div>



	<div id="savegroup" style="display: none;">
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">人员分组详情</h4>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
	<!--隐藏框 产品新增结束  -->
	<!--隐藏框 新增杂工开始  -->
	<div id="addDictDivType" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeForm">
				<div class="row col-xs-12  col-sm-12  col-md-12 ">
					<div class="form-group">
						<label class="col-sm-3 control-label">日期:</label>
						<div class="col-sm-6">
							<input id="Time" placeholder="时间可不填"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#Time', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
					</div>

					<div class="form-group">

						<label class="col-sm-3 control-label">批次名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control bacth">
						</div>
					</div>
					
					<div class="form-group visible-sm">
						<label class="col-sm-3 control-label">开始时间</label>
						<div class="col-sm-6">
							<input id="startTimes" placeholder="请输入开始时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#startTimes', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
					</div>
					
					<div class="form-group visible-sm">
						<label class="col-sm-3 control-label">结束时间</label>
						<div class="col-sm-6">
							<input id="endTimes" placeholder="请输入结束时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#endTimes', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
					</div>
					
					<div class="form-group">

						<label class="col-sm-3 control-label">工序名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control sumnumber">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label">现场管理时间</label>
						<div class="col-sm-6 ">
							<input type="text" class="form-control timedata">
						</div>
					</div>

					<!-- <div class="form-group">
						<label class="col-sm-3 control-label">加绩工序选择</label>
						<div class="col-sm-6 working"></div>
					</div> -->
					<div class="form-group">
						<label class="col-sm-3 control-label">备注</label>
						<div class="col-sm-6">
							<input type="text" class="form-control remarks">
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label">完成人</label>
						<div class="col-sm-6 completetw">
							<input type="text" class="form-control">
						</div>
						<div class="col-sm-2 selecttw"></div>
					</div>
					<!-- <div class="form-group">
						<div class="to__block">
				        <div class="to__left">
				            <div class="to__arrow"><span>←</span><span>→</span></div>
				            <div class="to__title">选择组织架构</div>
				            <div class="to__content" id="leftCont"></div>
				        </div>
				        <div class="to__right">
				            <div class="to__title">已选择</div>
				            <div class="to__content" id="rightCont"></div>
				        </div>
    					</div>
					</div>	 -->
					
				</div>
		</div>

		</form>
	</div>
	</div>
	<!--隐藏框 新增结束  -->
	<!--隐藏框 工序加绩分配开始  -->
	<div id="addDictDivTypetw" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeFormtw">
				<div class="row col-xs-12  col-sm-12  col-md-12" id="type"></div>
				<div class="form-group">

					<label class="col-sm-1 control-label" style="width: 17.1%;">完成人:</label>
					<div class="col-sm-2 complete" style="width: 20%;">
						<input type="text" class="form-control" >
					</div>
					<div class="col-sm-2 select"></div>
				</div>
		</div>

		</form>
	</div>
	</div>
	<!--隐藏框 工序加绩分配结束  -->
	<!--隐藏框 查看加价开始  -->
	<div id="savegrouptw" style="display: none;">
		<div class="modal fade" id="myModaltw" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">查看加价</h4>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
	<!--隐藏框 查看加价结束  -->
<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<!-- <script type="text/javascript">
var checkedList = [] // 选中列表


// 节点单击事件
function domClick(e){
    // 选中子元素所有input框
    var subItem = e.parentNode.nextElementSibling
    var inputList = subItem.querySelectorAll(".to__item")
    for(var i = 0; i < inputList.length; i++){
        var item = inputList[i]
        item.querySelector("input").checked = !e.querySelector("input").checked
        var subName = item.querySelector(".to__name").innerHTML
    }
    
    // 选中当前input框
    e.querySelector("input").checked = !e.querySelector("input").checked
    select(e)
}

// 冒泡事件
function checkboxClick(e){
    e.checked = !e.checked
}

// checkedbox选中事件
function select(){
    // 筛选在右侧区域需要遍历的内容
    checkedList = []
    var cList = document.getElementsByName("cName")
    for( var i = 0; i < cList.length; i++){
        var classArr = (cList[i].parentNode.parentNode.className).split(' ')
        var className = classArr[classArr.length - 1]
        var level = parseInt(className.replace(/\w+-/g,'')) // 

        // 设置显示的级别
        var levelArr = document.querySelectorAll("[class^='to__item level-']")
        var max = 1

        for (var a = 0; a < levelArr.length; a++) {
            var item = levelArr[a];
            var arr = item.className.split('-')
            if(parseInt(arr[1]) > max){
                max = parseInt(arr[1])
            }
        }
        if(level == max && cList[i].checked == true){
            checkedList.push(cList[i].value)
        }
    }

    // 右侧区域添加选中内容
    var dom = document.getElementById("rightCont")
    dom.innerHTML = ""
    for (var i = 0; i < checkedList.length; i++) {
        var item = checkedList[i];
        var div = document.createElement("div")
        div.className = "to__item"
        div.innerHTML = '<span>' + item + '</span><span class="to__close" onclick="cancel(this)"><i>+</i></span>'
        dom.appendChild(div)
    }
}

// 取消选中事件
function cancel(dom){
    var cList = document.getElementsByName("cName")
    for (var i = 0; i < cList.length; i++) {
        var item = cList[i];
        if(item.value == dom.previousElementSibling.innerHTML)
        {
            item.checked = false
            select();
            break;
        }
    }
}

// 下拉框点击事件
function dropClick(dom){
    // 切换样式状态
    if(dom.className.indexOf("to__roate") > -1){
        dom.className = ""
    }
    else{
        dom.className = "to__roate"
    }
    
    // 显示隐藏内容
    var domShow = dom.parentNode.parentNode.nextElementSibling
    if(domShow.className.indexOf("to__show") > -1){
        domShow.className = "to__subItem"
    }
    else{
        domShow.className = "to__subItem to__show"
    }
}

window.onload = function(){
    // 模拟数据
    var html=""
					var htmlth=""
					var data={
							type:2
					}
					//遍历人名组别
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				  console.log(j.name)
			      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
					      }
					  });
    
    var data = [
        {
            name : "员工分组",
            child: [
                {
                    name : "程序部",
                    child : [
                        {
                            name : "小明",
                            child : [],
                            id:5,
                        },
                        {
                            name : "小红",
                            child : [],
                            id:6,
                        }
                    ]
                },
                {
                    name : "技术部",
                    child : [
                        {
                            name : "小白",
                            child : []
                        },
                        {
                            name : "小黑",
                            child : []
                        }
                    ]
                },
                {
                    name : "UI部",
                    child : []
                }
            ]
        }
    ]
    
    var endHtml = 0
    var html = ""
    var level = 1

    // 遍历树形结构
    function getData(data, dom){
        if(Object.prototype.toString.call(data) === '[object Array]'){
            for(var i = 0; i < data.length; i++){

                // 添加标题
                var item = document.createElement("div")
                var arrow = '<span class="to__dropdownList" ><i onclick="dropClick(this)"><svg t="1550632829702" class="icon" style="" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1783" xmlns:xlink="http://www.w3.org/1999/xlink" width="100%" height="100%"><defs><style type="text/css"></style></defs><path d="M959.52557 254.29773 511.674589 702.334953 63.824631 254.29773Z" p-id="1784"></path></svg></i></span>'

                // 设置显示级别
                if(level == 3){
                    arrow = ""
                }
                item.innerHTML = '<div class="to__item level-' + level + '">' + arrow + '<span onclick="domClick(this)"><input type="checkbox" name="cName" value="' +data[i].name+ '" data-id="'+data[i].id+'" onclick="checkboxClick(this)" /><div class="to__name">' +data[i].name+ '</div></span></div>'
                dom.appendChild(item)
                
                // 添加子元素
                var subItem = document.createElement("div")
                subItem.className = "to__subItem"
                item.appendChild(subItem)

                if(data[i].child.length > 0){
                    level++
                    getData(data[i].child,subItem)
                }
                else{
                    if(i == data.length - 1){
                        level--
                    }
                }
            }
        }
    }

    // 赋值
    var baseDom = document.createElement("div")
    getData(data,baseDom)
    document.getElementById("leftCont").innerHTML =  baseDom.innerHTML
    
}

    </script> -->
	<script>
	
   jQuery(function($){
	   //判断当前设备的UA
	   var ua = navigator.userAgent.toLocaleLowerCase();
	      var pf = navigator.platform.toLocaleLowerCase();
	      var isAndroid = (/android/i).test(ua)||((/iPhone|iPod|iPad/i).test(ua) && (/linux/i).test(pf))
	          || (/ucweb.*linux/i.test(ua));
	      var isIOS =(/iPhone|iPod|iPad/i).test(ua) && !isAndroid;
	      var isWinPhone = (/Windows Phone|ZuneWP7/i).test(ua);
	  
	      var mobileType = {
	         pc:!isAndroid && !isIOS && !isWinPhone,
	         ios:isIOS,
	         android:isAndroid,
	         winPhone:isWinPhone
	   };
	      var mobileType=mobileType.android;
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
			var _cache;
			this.setCache = function(cache){
		  		_cache=cache;
		  	}
		  	this.getCache = function(){
		  		return _cache;
		  	}
		  	this.getCount = function(){
		  		return _count;
		  	}
		  	this.setCount = function(count){
		  		_count=count;
		  	}
			  	var myDate = new Date(new Date().getTime() - 86400000);
				//获取当前年
				var year=myDate.getFullYear();
				//获取当前月
				var month=myDate.getMonth()+1;
				//获取当前日
				var date=myDate.getDate(); 
				var day = new Date(year,month,0);  
				if(month < 10){
					month = "0" + month;
					}
				var firstdate = year + '-' +month + '-01'+' '+'00:00:00';
				var lastdate = year + '-' +month + '-' + day.getDate() +' '+'23:59:59'; 
				function p(s) {
					return s < 10 ? '0' + s: s;
					}
				var myDate2 = new Date(new Date().getTime());
				var year2=myDate2.getFullYear();
				//获取当前月
				var month2=myDate2.getMonth()+1;
				//获取当前日
				var date2=myDate2.getDate();
				var h=myDate.getHours();              //获取当前小时数(0-23)
				var m=myDate.getMinutes();          //获取当前分钟数(0-59)
				var s=myDate.getSeconds();
				var now=year2+'-'+p(month2)+"-"+p(date2)+" "+p(h)+':'+p(m)+":"+p(s);//当前时间
				$("#startTimes").val(now)
			 layui.use(['laydate'],function(){
					var laydate = layui.laydate;
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
						value : firstdate+' ~ '+lastdate,
					});
			 }) 
			 var data={
						page:1,
				  		size:13,	
				  		type:2,
				  		orderTimeBegin:firstdate,
			  			orderTimeEnd:lastdate,
				} 
			
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/farragoTask/allFarragoTask",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			$("#totaltw").text(result.data.statData.stateCount)
		      			  $("#totale").text(result.data.statData.statAmount)
		      			 $(result.data.rows).each(function(i,o){
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" data-procedurename="'+o.name+'" value="'+o.id+'" data-proname="'+o.productName+'"  data-performance="'+o.performance+'" data-status="'+o.status+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center edit name">'+o.bacth+'</td>'
		      				+'<td class="text-center edit name">'+o.allotTime+'</td>'
		      				+'<td class="text-center edit name">'+o.name+'</td>'
		      				+'<td class="text-center edit name">'+(o.time==null ? 0 :o.time)+'</td>'
		      				+'<td class="text-center edit name">'+o.remarks+'</td>'
		      				+'<td class="text-center edit name hidden-sm">'+parseFloat((o.price==null ? 0 : o.price).toFixed(3))+'</td>'
		      				+'<td class="text-center edit name hidden-sm">'+parseFloat((o.payB).toFixed(3))+'</td>'
		      				+'<td class="text-center "><button class="btn btn-primary btn-trans btn-sm savemodePerformance" data-toggle="modal" data-target="#myModaltw" data-id="'+o.id+'")">查看加价</button></td>'
		      				+'<td class="text-center edit name">'+parseFloat((o.performancePrice==null ? 0 : o.performancePrice).toFixed(3))+'</td>'
		      				+'<td class="text-center"><button class="btn btn-primary btn-trans btn-sm savemode" data-toggle="modal" data-target="#myModal" data-id="'+o.id+'")">查看人员</button></td>'
		      				+'<td class="text-center edit visible-sm">'+(o.status==0 ? "进行中" : "完成")+'</td>'
		      				+'<td class="text-center"><button class="btn btn-sm btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>'
							
		      			}); 
		      			self.setCount(result.data.pageNum)
				        //显示分页
					   	 laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		  var orderTime=$("#startTime").val().split('~');
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		type:2,
									  		name:$('#name').val(),
								  			bacth:$('#number').val(),
								  			orderTimeBegin:orderTime[0],
								  			orderTimeEnd:orderTime[1], 
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   	self.loadEvents();
						self.checkeddd();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			
			this.loadEvents = function(){
				//删除方法
				$('.delete').on('click',function(){
					var postData = {
							id:$(this).data('id'),
					}
					
					var index;
					 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
					$.ajax({
						url:"${ctx}/farragoTask/delete",
						data:postData,
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							if(0==result.code){
							layer.msg("删除成功！", {icon: 1});
							var orderTime=$("#startTime").val().split('~');
							var _data = {
				        			page:self.getCount(),
							  		size:13,
							  		type:2,
							  		name:$('#name').val(),
						  			bacth:$('#number').val(),
						  			orderTimeBegin:orderTime[0],
						  			orderTimeEnd:orderTime[1], 
						  	}
							self.loadPagination(data)
							layer.close(index);
							}else{
								layer.msg("删除失败！", {icon: 1});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					 })
				})
				
				//人员详细显示方法
				$('.savemode').on('click',function(){
					var id=$(this).data('id')
					 var display =$("#savegroup").css("display")
					 if(display=='none'){
							$("#savegroup").css("display","block");  
						}
					var postData={
							id:id,
					}
					 var arr=new Array();
					var html="";
					$.ajax({
						url:"${ctx}/farragoTask/taskUser",
						data:postData,
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+=o.userName+"&nbsp&nbsp&nbsp&nbsp"
							})
							$('.modal-body').html(html);
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					
					
					
				})
				//查看加价
				$('.savemodePerformance').on('click',function(){
					var id=$(this).data('id')
					 var display =$("#savegrouptw").css("display")
					 if(display=='none'){
							$("#savegrouptw").css("display","block");  
						}
					 var postData={
							id:id,
					}
					 var arr=new Array();
					var html="";
					$.ajax({
						url:"${ctx}/farragoTask/getUserPerformance",
						data:postData,
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+=o.performance+":"+o.username+"&nbsp&nbsp&nbsp&nbsp"
							})
							$('.modal-body').html(html);
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					}); 
					
					
					
				})
				
			}
			$(document).on('click', '#tablecontent  tr ', function(event) {
				  if($(this).find('.checkboxId').is(':checked')==false){
					$(this).find('.checkboxId').prop("checked",true)
					 $(this).css("color","red");
				  }else{
					  $(this).find('.checkboxId').prop("checked",false)
					  $(this).css("color","inherit");
				  }
				})
				 this.checkeddd=function(){
				
				$(".checks").on('click',function(){
					
                    if($(this).is(':checked')){ 
			 			$('.checkboxId').each(function(){  
                    //此处如果用attr，会出现第三次失效的情况  
                     		$(this).prop("checked",true);
			 			})
                    }else{
                    	$('.checkboxId').each(function(){ 
                    		$(this).prop("checked",false);
                    		
                    	})
                    }
                }); 
				
			}	
			this.events = function(){
				
				$('.searchtask').on('click',function(){
					 var orderTime=$("#startTime").val().split('~');
					var data = {
				  			page:1,
				  			size:13,
				  			type:2,
				  			name:$('#name').val(),
				  			bacth:$('#number').val(),
				  			orderTimeBegin:orderTime[0],
				  			orderTimeEnd:orderTime[1],  
				  	}
		            self.loadPagination(data);
				});
				//杂工修改
				$(".update").on('click',function(){
					
					var arrs=new Array()
					var html=""
						var htmlth=""
						var data={
								type:2
						}
						//遍历人名组别
					    $.ajax({
						      url:"${ctx}/production/getGroup",
						      data:data,
						      type:"GET",
						      async:false,
				      		  success: function (result) {
				      			  $(result.data).each(function(k,j){
				      				  arrs.push(j.id)
				      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
				      				arrs.push
				      			  });  
				      			 $('.completetw').html("<select class='form-control selectcompletee'><option value="+0+">请选择</option>"+htmlth+"</select>") 
						      }
						  });
					
					
					var  thae=$(".table-hover");
					var Arrys=new Array()
					thae.parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function(j,k) {
						Arrys.push($(this).val())
						if(Arrys.length!=1){
							return layer.msg("只能选择一条任务进行修改", {icon: 2});
						}
					var id=$(this).val();
					var status=$(this).data('status');
					if(status==1){
						return layer.msg("当前任务已完成", {icon: 2});
					}
					var ids="";
					var user="";
						data={
							id:id,
						}
						$.ajax({
						      url:"${ctx}/farragoTask/allFarragoTask",
						      data:data,
						      type:"GET",
						      async:false,
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			 $(result.data.rows).each(function(i,o){
				      				$("#Time").val(o.allotTime)
				      				$(".bacth").val(o.bacth)
				      				$("#startTimes").val(o.startTime)
				      				$("#endTimes").val(o.endTime)
				      				$(".sumnumber").val(o.name)
				      				$(".timedata").val(o.time)
				      				user=o.userIds.split(',')
				      				users=o.temporaryUserIds.split(',')
				      				ids=o.userIds.split(',')[0]
				      				temporaryUserIds=o.temporaryUserIds.split(',')[0]
				      			}); 
							   	layer.close(index);
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
						var s="";
						var h="";
						for (var i = 0; i < arrs.length; i++) {
						$.ajax({
						      url:"${ctx}/production/allGroup",
						      data:{
						    	  id:arrs[i],
						    	  type:2,
						    	  temporarilyDate:$('#Time').val(),
						      },
						      type:"GET",
						      async:false,
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			$(result.data).each(function(i,o){
				      			$(o.userList).each(function(i,o){
									if(o.id==ids){
										h=o.groupId	
									}
								})
								$(o.temporarilyUser).each(function(i,o){
									if(o.id==temporaryUserIds){
										h=o.groupId	
									}		
										})
				      			})
							   	layer.close(index);
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
						}
						 $(".selectcompletee").each(function(j,k){
								$(k).val(h);
			      			  })
			      			var htmltwo = "";
		      				var  htmltwh = "";
		      				/* var	id=$(this).val() */
							   var data={
		      							type:2,
									 	id:h,
									 	temporarilyDate:$('#Time').val(),
							   }
		      				if(id==0){
		      					$('.selecttw').html("");
		      				}else{
		      				$.ajax({
								url:"${ctx}/production/allGroup",
								data:data,
								type:"GET",
								async:false,
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									$(result.data).each(function(i,o){
									$(o.userList).each(function(i,o){
										if(o.status==1){
										htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBoxtt" value="'+o.userId+'" data-value='+o.userId+' data-secondment='+o.secondment+' data-id="'+o.id+'" data-ids="'+o.id+'" data-username="'+o.name+'">'+o.name+'</input></div>'
										}
									})
									$(o.temporarilyUser).each(function(i,o){
										if(o.status==1){
										htmltwh +='<div class="input-group"><input type="checkbox" class="stuCheckBoxtt" value='+o.userId+' data-value='+o.userId+' data-secondment='+o.secondment+' data-id="t-'+o.id+'" data-ids="'+o.id+'" data-username="'+o.name+'">'+o.name+'</input></div>'
										}
									})
									})
									var s="<div class='input-group'><input type='checkbox' class='checkalltt'>全选</input></div>"
									$('.selecttw').html(s+htmltwo+htmltwh)
											/* $(".stuCheckBoxtt").each(function(j,k){
												
							      			  }) */
										$(".stuCheckBoxtt").each(function(j,k){
											 for (var i = 0; i < user.length; i++) {
												 var a=$(this).data('ids')
												 if(a==user[i]){
													$(k).attr("checked","true"); 
												}
											 } 
											for (var i = 0; i < users.length; i++) {
												var a=$(this).data('ids')
												 if(a==users[i]){
													$(k).attr("checked","true"); 
											}
												
											}
						      			  })
									$(".checkalltt").on('click',function(){
						                    if($(this).is(':checked')){ 
									 			$('.stuCheckBoxtt').each(function(){  
						                    //此处如果用attr，会出现第三次失效的情况  
						                     		$(this).prop("checked",true);
									 			})
						                    }else{
						                    	$('.stuCheckBoxtt').each(function(){ 
						                    		$(this).prop("checked",false);
						                    		
						                    	})
						                    }
						                });
									layer.close(index);
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
		      				}	  
			      			  
						//改变事件
		      			 $(".selectcompletee").change(function(){
		      				var htmltwo = "";
		      				var  htmltwh = "";
		      				var	id=$(this).val()
							   var data={
		      							type:2,
									 	id:id,
									 	temporarilyDate:$('#Time').val(),
							   }
		      				if(id==0){
		      					$('.selecttw').html("");
		      				}else{
		      				$.ajax({
								url:"${ctx}/production/allGroup",
								data:data,
								type:"GET",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									$(result.data).each(function(i,o){
									$(o.userList).each(function(i,o){
										if(o.status==1){
										htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBoxtt" value="'+o.userId+'" data-secondment='+o.secondment+' data-id="'+o.id+'" data-username="'+o.name+'">'+o.name+'</input></div>'
										}
									})
									$(o.temporarilyUser).each(function(i,o){
										if(o.status==1){
										htmltwh +='<div class="input-group"><input type="checkbox" class="stuCheckBoxtt" value="'+o.userId+'" data-secondment='+o.secondment+' data-id="t-'+o.id+'" data-username="'+o.name+'">'+o.name+'</input></div>'
										}
									})
									})
									var s="<div class='input-group'><input type='checkbox' class='checkalltt'>全选</input></div>"
									$('.selecttw').html(s+htmltwo+htmltwh)
									$(".checkalltt").on('click',function(){
						                    if($(this).is(':checked')){ 
									 			$('.stuCheckBoxtt').each(function(){  
						                    //此处如果用attr，会出现第三次失效的情况  
						                     		$(this).prop("checked",true);
									 			})
						                    }else{
						                    	$('.stuCheckBoxtt').each(function(){ 
						                    		$(this).prop("checked",false);
						                    		
						                    	})
						                    }
						                });
									layer.close(index);
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
		      				}
						 }) 
						
						var dicDiv=$('#addDictDivType');
						_index = layer.open({
							  type: 1,
							  skin: 'layui-layer-rim', //加上边框
							  area: ['580px', '550px'], 
							  btnAlign: 'c',//宽高
							  maxmin: true,
							  title:"修改杂工",
							  offset:'30px',
							  content: dicDiv,
							  btn: ['确定', '取消'],
							  yes:function(index, layero){
								  var performanceNumber=$(".selectchang").val();
								  var performance=$(".selectchang option:selected").text();
								 if(performance=="请选择"){
									 performance="";
								 }
								 var arr=new Array()
								 var arrtem=new Array()
								 var ids=new Array()
								 var temporaryIds=new Array()
								 $(".stuCheckBoxtt:checked").each(function() {   
										var id = $(this).data('id');
										if(String(id).indexOf('-')>0){
									   		arrtem.push($(this).data('value'));
									   		temporaryIds.push(id.split('-')[1]);
										}else{
										    arr.push($(this).val()); 
										    ids.push($(this).data('id'));
										}
									});
								  
								   if(arr.length<=0 && arrtem.length<=0){
									 return layer.msg("领取人不能为空", {icon:2 });
								  } 
								  if($(".sumnumber").val()==""){
										 return layer.msg("工序不能为空", {icon:2 });
									  }
								  postData={
										  id:id,
										  allotTime:$("#Time").val(),
										  name:$(".sumnumber").val(),
										  time:$(".timedata").val(),
										  remarks:$(".remarks").val(),
										  startTime:$("#startTimes").val(),
										  endTime:$("#endTimes").val(),
										  performance:performance,
										  performanceNumber:performanceNumber,
										  ids:arr,
										  temporaryIds:arrtem,
										  userIds:ids,
										  temporaryUserIds:temporaryIds,
										  bacth:$(".bacth").val(),
										  type:2,
								  }
								  $.ajax({
										url:"${ctx}/farragoTask/updateFarragoTask",
										data:postData,
							            traditional: true,
										type:"post",
										beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										
										success:function(result){
											if(0==result.code){
											layer.msg("修改成功！", {icon: 1});
											var orderTime=$("#startTime").val().split('~');
											var data={
													page:self.getCount(),
											  		size:13,	
											  		type:2,
											  		name:$('#name').val(),
										  			bacth:$('#number').val(),
										  			orderTimeBegin:orderTime[0],
										  			orderTimeEnd:orderTime[1], 
											} 
											 self.loadPagination(data); 
											 layer.close(_index);
											 $('.addDictDivTypeForm')[0].reset();
												
											}else{
												layer.msg(result.message, {icon: 2});
											}
											
											layer.close(index);
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									});
								},
							  end:function(){
								  $('#addDictDivType').hide();
								$('.selecttw').text(' ');
								  $('.addDictDivTypeForm')[0].reset(); 
								
							  }
						});
						
					});
				})
				
				
				//加绩
				$('.addDict').on('click',function(){
					var  thae=$(".table-hover");
					var arr=""//员工id
					var arrytw=new Array()
					var arryth=new Array()
					var tasksId=new Array()
					var CheckCount=0;
					var productName;
					var f;
					var g;
					  thae.parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
						  CheckCount++;
						  arr=$(this).val();  
						  tasksId.push($(this).val());  
						  arrytw.push($(this).data('procedurename'));
						  arryth.push($(this).data('performance')); 
						  productName=$(this).data('procedurename');
						  if($(this).data('performance')==""){
							  f=0;
						  }else{
							  g=1;
						  }
						  if($(this).data('performance')!=""){
							  g=1;
						  }else{
							  f=0;
						  }
						});
					  if(f==0 && g==1){
							return layer.msg("不能同时选择新增修改", {icon: 2});
						} 
					var  update;
					  if(f==0){
						  update=0;  
					  }
					  if(g==1){
						  update=1;  
					  }
					if(arr==""){
						return layer.msg("请选择一条任务", {icon: 2});
					}
					var that=$(this)
					var productName=$(this).data('proname')
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    
				    var htmlth = '';
				    var htmlfr = '';
					//遍历人名组别
			      			  $('.complete').html("<select class='form-control selectcomplete'><option value="+""+">全部</option></select>")
							//改变事件
			      				var htmltwo = "";
			      				var	id=$(this).val()
								   var data={
										  id:arr,
								   }
			      				$.ajax({
									url:"${ctx}/farragoTask/taskUser",
									data:data,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										$(result.data).each(function(i,o){
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBoxee" value="'+o.id+'" data-username="'+o.userName+'"><span class="nameFont">'+o.userName+'</span></input></div>'
										})
										var s="<div class='input-group'><input type='checkbox' class='checkallee'><span class='checkallFont'>全选</span></input></div>"
										$('.select').html(s+htmltwo)
										$(".checkallee").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.stuCheckBoxee').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.stuCheckBoxee').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
										$(".checkallFont").on('click',function(obj){
											var checked = !$(obj.target).parent().find('input').is(':checked');
											$(obj.target).parent().find('input').prop('checked',checked);
											$('.stuCheckBoxee').each(function(){  
					                     		$(this).prop("checked",checked);
								 			})
										})
										$('.nameFont').unbind().on('click',function(obj){
											$(obj.target).parent().find('input').prop('checked',!$(obj.target).parent().find('input').is(':checked'));
										})
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
			      			//遍历杂工加绩比值
								var html=""
								var htm=""
									for (var i = 0; i < arrytw.length; i++) {
									var array_element = arrytw[i];
									var array_element2 =arryth[i];
										var id="";
										var htmls="<option>请选择</option>"
												$.ajax({
													url:"${ctx}/task/pickTaskPerformance",
													type:"GET",
													data:data,
													async: false,
													beforeSend:function(){
														index = layer.load(1, {
															  shade: [0.1,'#fff'] //0.1透明度的白色背景
															});
													},
													success:function(result){
														$(result.data).each(function(i,o){
															var a="";
															if(o.checked==1){
																id=o.name
															}
																ids=array_element2==""?id:array_element2;
															
														htmls+='<option value="'+o.name+'" data-number="'+o.number+'" '+(ids==o.name ? "selected" : "")+'>'+o.name+'</option>'
														
														})
														layer.close(index);
													},error:function(){
														layer.msg("操作失败！", {icon: 2});
														layer.close(index);
													}
												});
									
									htm+="<label class='col-sm-2 control-label'>工序:</label><div class='col-sm-3 type'><input type='text' disabled='disabled' class='form-control' value="+array_element+"></div><div class='form-group'><label class='col-sm-3 control-label'>加绩工序:</label><div class='col-sm-3 workingtw'><select class='form-control selectchangtw'>"+htmls+"</select></div>"
									+"</div>"
									$('#type').html(htm)
						      			
									}
				    
					var postData
					var dicDiv=$('#addDictDivTypetw');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['650px', '500px'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  offset:(parent.document.documentElement.scrollTop+100)+'px',
						  title:productName,
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var arry=new Array()
								$(".stuCheckBoxee:checked").each(function() {   
								    arry.push($(this).val());   
								}); 
								if(arr.length<=0){
									return layer.msg("至少选择一个员工！", {icon: 2});
								}
								var performanceNumber=new Array()
								var performance=new Array()
								$(".selectchangtw option:selected").each(function() {
									performanceNumber.push($(this).data('number'));
									performance.push($(this).text());
								})
								var postData = {
										taskIds:tasksId,
										ids:arry,
										performanceNumber:performanceNumber,
										performance:performance,
										update:update,
								}
							    $.ajax({
									url:"${ctx}/farragoTask/giveTaskPerformance",
									data:postData,
						            traditional: true,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
										  $('.addDictDivTypeFormtw')[0].reset(); 
											layer.msg("成功！", {icon: 1});
											var orderTime=$("#startTime").val().split('~');
											  var date={
														page:self.getCount(),
												  		size:13,	
												  		type:2,
												  		name:$('#name').val(),
											  			bacth:$('#number').val(),
											  			orderTimeBegin:orderTime[0],
											  			orderTimeEnd:orderTime[1], 
												} 
											   self.loadPagination(date);
										layer.close(_index);
										}else{
											layer.msg("添加失败", {icon: 2});
										}
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});  
							},
						   end:function(){
							  $('.addDictDivTypeFormtw')[0].reset(); 
							  $("#addDictDivTypetw").hide();
							  $('.select').text("");
							  
							
							
						  } 
					});
				})
				
				//新增杂工
				$('#addgroup').on('click',function(){
					var myDate = new Date();
					function p(s) { return s < 10 ? '0' + s: s; }
					//获取当前年
					var year=myDate.getFullYear();
					//获取当前月
					var month=myDate.getMonth()+1;
					//获取当前日
					var date=myDate.getDate();
					var h=myDate.getHours();              //获取当前小时数(0-23)
					var m=myDate.getMinutes();          //获取当前分钟数(0-59)
					var s=myDate.getSeconds();
					var day = new Date(year,month,0);  
					var firstdate = year + '-' + '0'+month + '-01'+' '+'00:00:00';
					var lastdate = year + '-' + '0'+month + '-' + day.getDate() +' '+'23:59:59';
					var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);//当前时间
					$("#startTimes").val(now)
					if(mobileType==true){
						$("#Time").val(now)
					}
					var _index
					var index
					var postData
					var dicDiv=$('#addDictDivType');
					var html=""
					var htmlth=""
					var data={
							type:2
					}
					//遍历人名组别
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			 $('.completetw').html("<select class='form-control selectcompletee'><option value="+0+">请选择</option>"+htmlth+"</select>") 
							//改变事件
			      			 $(".selectcompletee").change(function(){
			      				var htmltwo = "";
			      				var  htmltwh = "";
			      				var	id=$(this).val()
								   var data={
			      							type:2,
										 	id:id,
										 	temporarilyDate:$('#Time').val(),
								   }
			      				if(id==0){
			      					$('.selecttw').html("");
			      				}else{
			      				$.ajax({
									url:"${ctx}/production/allGroup",
									data:data,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										$(result.data).each(function(i,o){
										$(o.userList).each(function(i,o){
											if(o.status==1){
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBoxtt" value="'+o.userId+'" data-secondment='+o.secondment+' data-id="'+o.id+'" data-username="'+o.name+'">'+o.name+'</input></div>'
											}
										})
										$(o.temporarilyUser).each(function(i,o){
											if(o.status==1){
											htmltwh +='<div class="input-group"><input type="checkbox" class="stuCheckBoxtt" value="'+o.userId+'" data-secondment='+o.secondment+' data-id="t-'+o.id+'" data-username="'+o.name+'">'+o.name+'</input></div>'
											}
										})
										})
										var s="<div class='input-group'><input type='checkbox' class='checkalltt'>全选</input></div>"
										$('.selecttw').html(s+htmltwo+htmltwh)
										$(".checkalltt").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.stuCheckBoxtt').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.stuCheckBoxtt').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
			      				}
							 }) 
					      }
					  });
					//遍历杂工加绩比值
					$.ajax({
						url:"${ctx}/farragoTask/farragoTaskPerformance",
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+='<option value="'+o.number+'" data-name="'+o.name+'">'+o.name+'</option>'
							})
							$('.working').html("<select class='form-control selectchang'><option value='0'>请选择</option>"+html+"</select>");
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['580px', '550px'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"新增杂工",
						  offset:'30px',
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var performanceNumber=$(".selectchang").val();
							  var performance=$(".selectchang option:selected").text();
							 if(performance=="请选择"){
								 performance="";
							 }
							 var arr=new Array()
							 var arrtem=new Array()
							 var ids=new Array()
							 var temporaryIds=new Array()
							 $(".stuCheckBoxtt:checked").each(function() {   
									var id = $(this).data('id');
									if(String(id).indexOf('-')>0){
								   		arrtem.push($(this).val());
								   		temporaryIds.push(id.split('-')[1]);
									}else{
									    arr.push($(this).val()); 
									    ids.push($(this).data('id'));
									}
								});
							  
							   if(arr.length<=0 && arrtem.length<=0){
								 return layer.msg("领取人不能为空", {icon:2 });
							  } 
							  if($(".sumnumber").val()==""){
									 return layer.msg("工序不能为空", {icon:2 });
								  }
							  if($("#startTimes").val()!="" && $(".timedata").val()!=""){
								  return layer.msg("填写开始时间后不能填写现场管理时间", {icon:2 });
							  }
							  postData={
									  allotTime:$("#Time").val(),
									  name:$(".sumnumber").val(),
									  time:$(".timedata").val(),
									  remarks:$(".remarks").val(),
									  startTime:$("#startTimes").val(),
									  endTime:$("#endTimes").val(),
									  performance:performance,
									  performanceNumber:performanceNumber,
									  ids:arr,
									  temporaryIds:arrtem,
									  userIds:ids,
									  temporaryUserIds:temporaryIds,
									  bacth:$(".bacth").val(),
									  type:2,
							  }
							  $.ajax({
									url:"${ctx}/farragoTask/addFarragoTask",
									data:postData,
						            traditional: true,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
										layer.msg("添加成功！", {icon: 1});
										 self.loadPagination(data); 
										 layer.close(_index);
										 $('.addDictDivTypeForm')[0].reset();
											
										}else{
											layer.msg(result.message, {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
							},
						  end:function(){
							  $('#addDictDivType').hide();
						
							  $('.addDictDivTypeForm')[0].reset(); 
							
						  }
					});
				})
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>

</body>
</html>