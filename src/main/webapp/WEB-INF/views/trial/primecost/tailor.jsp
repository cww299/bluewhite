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
    <title>裁剪</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
   
   
</head>

<body>
    <section id="main-wrapper" class="theme-default">
        
        <%@include file="../../decorator/leftbar.jsp"%> 
        
        <!--main content start-->
        
           <section id="main-content" class="animated fadeInUp">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">工资详情</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                             <div class="panel-body">
                                <div class="tab-wrapper tab-primary">
                                    <ul class="nav nav-tabs col-md-12">
                                        <li class="active col-md-2" style="width: 14.285%"><a href="#home1" data-toggle="tab">裁剪页面</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%;"><a href="#profile1" class="profile1"  data-toggle="tab">裁剪普通激光</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%"><a href="#profile2" data-toggle="tab">绣花定位激光</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%"><a href="#profile3" data-toggle="tab">冲床</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%"><a href="#profile4" data-toggle="tab">电烫</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%"><a href="#profile5" data-toggle="tab">电推</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%"><a href="#profile6" data-toggle="tab">手工剪刀</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="home1">
                                        <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr>
								<td>员工姓名:</td><td><input type="text" name="name" id="usernameth" placeholder="请输入姓名" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>开始时间:</td>
								<td>
								<input id="startTimeth" placeholder="请输入考勤开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTimeth', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>结束时间:</td>
								<td>
								<input id="endTimeth" placeholder="请输入考勤结束时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#endTimeth', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtaskth">
										查&nbsp找
									</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
									<button type="button" class="btn btn-danger  btn-sm btn-3d start">
									一键删除
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
                                        
                                            <table class="table table-hover" >
                                    <thead>
                                        <tr>
                                        <th class="text-center">
											<label> 
											<input type="checkbox" class="ace checks" /> 
											<span class="lbl"></span>
											</label>
											</th>
                                        	<th class="text-center">裁剪部位</th>
                                        	<th class="text-center">裁剪片数</th>
                                            <th class="text-center">当批片数</th>
                                            <th class="text-center">裁片的平方M</th>
                                            <th class="text-center">裁剪方式</th>
                                            <th class="text-center">得到理论(市场反馈）含管理价值</th>
                                            <th class="text-center">得到实验推算价格</th>
                                            <th class="text-center">市场价与实推价比</th>
                                            <th class="text-center">选择入成本价格↓</th>
                                            <th class="text-center">入成本价格</th>
                                            <th class="text-center">各单道比全套工价</th>
                                            <th class="text-center">物料压价</th>
                                            <th class="text-center">不含绣花环节的为机工压价</th>
                                            <th class="text-center">含绣花环节的为机工压价</th>
                                            <th class="text-center">为机工准备的压价</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent">
                                        
                                    </tbody>
                                    <thead>
                                        <tr>
                                       	    <td class="text-center">合计</td>
                                            <td class="text-center"></td>
                                            <td class="text-center" ></td>
                                            <td class="text-center" id="totale"></td>
                                            <td class="text-center" ></td>
                                            <td class="text-center" id="totaltw"></td>
                                            <td class="text-center" ></td>
                                            <td class="text-center"></td>
                                            
                                        </tr>
                                    </thead>
                                </table>
                                        </div>
                     <!-- B工资流水开始 -->
            <div class="tab-pane" id="profile1">
                      <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-10 col-sm-10  col-md-10">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr><td>批次:</td><td><input type="text" name="number" id="number" placeholder="请输入批次号" class="form-control search-query number" /></td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->  
                                   <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">裁剪部位</th>
                                        	<th class="text-center">手选裁剪方式</th>
                                            <th class="text-center">裁片周长/CM(≈)</th>
                                            <th class="text-center">激光停顿点</th>
                                            <th class="text-center">单双激光头</th>
                                            <th class="text-center">捡片时间</th>
                                            <th class="text-center">其他未考虑时间1</th>
                                            <th class="text-center">其他未考虑时间2</th>
                                            <th class="text-center">拉布时间</th>
                                            <th class="text-center">单片激光需要用净时</th>
                                            <th class="text-center">单片激光放快手时间</th>
                                            <th class="text-center">工价（含快手)</th>
                                            <th class="text-center">设备折旧和房水电费</th>
                                            <th class="text-center">管理人员费用</th>
                                            <th class="text-center">普通激光切割该裁片费用</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent2">
                                        
                                    </tbody>
                                </table>
                                 </div>
                                 <!-- B工资流水结束 -->
                 <div class="tab-pane" id="profile2">
                                     <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr><td>工序:</td><td><input type="text" name="number" id="numbertw" placeholder="请输入工序名" class="form-control search-query number" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>姓名:</td><td><input type="text" name="name" id="usernametw" placeholder="请输入姓名" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>开始:</td>
								<td>
								<input id="startTimetw" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>结束:</td>
								<td>
								<input id="endTimetw" placeholder="请输入结束时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#endTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtasktw">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
                                    
                                            <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">姓名</th>
                                        	<th class="text-center">杂工工序名</th>
                                            <th class="text-center">时间</th>
                                            <th class="text-center">杂工加绩工资</th>
                                            <th class="text-center">杂工B工资</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        
                                    </tbody>
                                    <thead>
                                        <tr>
                                       	    <td class="text-center">合计</td>
                                            <td class="text-center"></td>
                                            <td class="text-center"></td>
                                            <td class="text-center" ></td>
                                            <td class="text-center" id="totaltr"></td>
                                            
                                        </tr>
                                    </thead>
                                </table>
                                <div id="pagertw" class="pull-right"></div>
                                        </div>
                                </div>
                            </div>
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
    <script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
    <script>
   jQuery(function($){
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
				var h=myDate.getHours();       //获取当前小时数(0-23)
				var m=myDate.getMinutes();     //获取当前分钟数(0-59)
				var s=myDate.getSeconds(); 
				var day = new Date(year,month,0);  
				var firstdate = year + '-' + '0'+month + '-01'+' '+'00:00:00';
				var lastdate = year + '-' + '0'+month + '-' + day.getDate() +' '+'23:59:59';
				$('#startTime').val(firstdate);
				$('#endTime').val(lastdate);
				$('#startTimetw').val(firstdate);
				$('#endTimetw').val(lastdate);
				$('#startTimeth').val(firstdate);
				$('#endTimeth').val(lastdate);
				var data={
						page:1,
				  		size:100,	
				  		productId:"",
				}
				var data2={
						page:1,
				  		size:100,	
				  		productId:"",
				  		tailorTypeId:71,
				}
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
				self.loadPagination2(data2);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/product/getTailor",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				 html +='<tr><td class="text-center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center edit ">'+o.tailorName+'</td>'
		      				+'<td class="text-center edit ">'+o.tailorNumber+'</td>'
		      				+'<td class="text-center edit ">'+o.bacthTailorNumber+'</td>'
		      				+'<td class="text-center edit "><input class="form-control tailorSize"  value='+(o.tailorSize!=null?o.tailorSize:"")+'></td>'
		      				+'<td class="text-center edit tailorType" data-tailortypeid='+o.tailorTypeId+'></td>'
		      				+'<td class="text-center managePrice">'+(o.managePrice!=null?o.managePrice:"")+'</td>'
		      				+'<td class="text-center experimentPrice">'+parseFloat((o.experimentPrice*1).toFixed(5))+'</td>'
		      				+'<td class="text-center edit ">'+(o.ratePrice!=null?o.ratePrice:"")+'</td>'
		      				+'<td class="text-center edit "></td></tr>'
		      			}); 
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   
					   self.loadEvents()
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			  this.loadPagination2 = function(data2){
				  //裁剪普通激光
				  $(".profile1").on('click',function(){
					  var index;
					    var html = '';
					    $.ajax({
						      url:"${ctx}/product/getOrdinaryLaser",
						      data:data2,
						      type:"GET",
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			 $(result.data.rows).each(function(i,o){
				      				 html +='<tr>'
				      				+'<td class="text-center edit ">'+o.tailorName+'</td>'
				      				+'<td class="text-center tailorType2" data-tailortypeid2='+o.tailorTypeId+'></td>'
				      				+'<td class="text-center"><input class="form-control perimeter2" data-id="'+o.id+'" style="width: 80px;" value='+(o.perimeter!=0?o.perimeter:"")+'></td>'
				      				+'<td class="text-center edit"><input class="form-control stallPoint2" style="width: 50px;" value='+(o.stallPoint!=0?o.stallPoint:"")+'></td>'
				      				+'<td class="text-center edit"><select class="form-control stallPoint" style="width: 100px;"><option value="">请选择</option><option value="1">单</option><option value="2">双</option></select></td>'
				      				+'<td class="text-center"><input class="form-control time" style="width: 50px;"  value='+(o.time!=null?o.time:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeOne" style="width: 80px;" value='+(o.otherTimeOne!=null?o.otherTimeOne:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeTwo" style="width: 80px;" value='+(o.otherTimeTwo!=null?o.otherTimeTwo:"")+'></td>'
				      				+'<td class="text-center edit ">'+parseFloat((o.rabbTime).toFixed(5))+'</td>'
				      				+'<td class="text-center edit ">'+parseFloat((o.singleLaserTime).toFixed(5))+'</td>'
				      				+'<td class="text-center edit ">'+parseFloat((o.singleLaserHandTime).toFixed(5))+'</td>'
				      				+'<td class="text-center edit ">'+parseFloat((o.labourCost).toFixed(8))+'</td>'
				      				+'<td class="text-center edit ">'+parseFloat((o.equipmentPrice).toFixed(5))+'</td>'
				      				+'<td class="text-center edit ">'+parseFloat((o.administrativeAtaff).toFixed(5))+'</td>'
				      				+'<td class="text-center edit ">'+parseFloat((o.stallPrice).toFixed(5))+'</td></tr>'
				      			}); 
							   	layer.close(index);
							   	 $("#tablecontent2").html(html); 
							   	 self.loadEvents2();
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				  })
			  }
			  this.loadEvents2 = function(){
				  //遍历裁剪方式
				  var data = {
							type:"tailor",
						}
						var index;
					    var html = '';
					    var htmlto= '';
					    $.ajax({
						      url:"${ctx}/product/getBaseOne",
						      data:data,
						      type:"GET",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      				html +='<option value="'+o.id+'">'+o.name+'</option>'
				      			}); 
						       htmlto='<select class="text-center form-control selecttailorType2" disabled="disabled"><option value="">请选择</option>'+html+'</select>'
				      		  $(".tailorType2").html(htmlto)
				      		  //改变事件
				      		  $(".selecttailorType2").each(function(i,o){
				      		var id=	$(o).parent().data("tailortypeid2");
									$(o).val(id)
									}) 
				      		  },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);sa
							  }
						  });
					    
					    $('.perimeter2').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		perimeter:$(this).val(),
					    		stallPoint:$(this).parent().parent().find(".stallPoint2").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);sa
								  }
							  });
					    })
			  }
			this.loadEvents = function(){
				//遍历裁剪方式
				var data = {
						type:"tailor",
					}
					var index;
				    var html = '';
				    var htmlto= '';
				    $.ajax({
					      url:"${ctx}/product/getBaseOne",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			 $(result.data).each(function(i,o){
			      				html +='<option value="'+o.id+'">'+o.name+'</option>'
			      			}); 
					       htmlto='<select class="text-center form-control selecttailorType"><option value="">请选择</option>'+html+'</select>'
			      		  $(".tailorType").html(htmlto)
			      		  //改变事件
			      		  $(".selecttailorType").each(function(i,o){
			      		var id=	$(o).parent().data("tailortypeid");
								$(o).val(id)
								}) 
			      		  $(".selecttailorType").change(function(){
			      			var that=$(this);
			      			var a=$(this).parent().prev().find(".tailorSize").val();
			      			var id=$(this).parent().prevAll().find(".checkboxId").val();
			      			  if(a==""){
			      				$(that).each(function(i,o){
									
									$(o).val("")
									}) 
			      				  return layer.msg("请先填写裁片的平方M", {icon: 2});
			      			  }
				    var	datae={
				    			tailorSize:a,
				    			tailorTypeId:$(this).val(),
				    			id:id,
				    	}
				    	$.ajax({
						      url:"${ctx}/product/getOrdinaryLaserDate",
						      data:datae,
						      type:"POST",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      			that.parent().parent().find(".managePrice").text(o.managePrice);
				      			that.parent().parent().find(".experimentPrice").text(parseFloat((o.experimentPrice).toFixed(5)));
				      			 }); 
				      			layer.close(index)
				      			var datar={
				      				id:id,
				      				tailorSize:a,
					    			tailorTypeId:that.val(),
					    			managePrice:result.data.managePrice,
					    			experimentPrice:result.data.experimentPrice,
					    			ordinaryLaserId:that.val(),
				      			}
				      			$.ajax({
								      url:"${ctx}/product/addTailor",
								      data:datar,
								      type:"POST",
						      		  success: function (result) {
						      			 $(result.data).each(function(i,o){
						      				if(0==result.code){
												layer.msg(result.message, {icon: 1});
												layer.close(index);
												}else{
													layer.msg(result.message, {icon: 2});
													layer.close(index);
												}
						      			 }); 
						      			layer.close(index)
						      			
						      			
								      },error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(index);
									  }
								  });
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				    		})
			      		  },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);sa
						  }
					  });
			}
			this.events = function(){
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>
  
       
</body>

</html>