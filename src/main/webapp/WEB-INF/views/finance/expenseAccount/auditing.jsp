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
    <title>财务审核</title>
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
                                <h3 class="panel-title">财务审核</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                            <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-12 col-sm-12  col-md-12">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group">
							<table><tr>
								<td>报销人:</td><td><input type="text" name="name" id="firstNames"  class="form-control search-query name" /></td>
								<td>&nbsp&nbsp</td>
								<td><select class="form-control" id="selectone"><option value="">请选择</option><option value="expenseDate">付款日期</option><option value="paymentDate">财务付款日期</option></select></td>
								<td>&nbsp&nbsp</td>
								<td>当月开始:</td>
								<td>
								<input id="startTime" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
									<td>&nbsp&nbsp</td>
				<td>当月结束:</td>
				<td>
					<input id="endTime" placeholder="请输入结束时间" class="form-control laydate-icon"
             onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</td>
								<td>&nbsp&nbsp</td>
								<td>是否核对:<td><select class="form-control" id="flag"><option value="">请选择</option><option value="0">未核对</option><option value="1">已核对</option></select></td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm navbar-right btn-3d searchtask">
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
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="center">
											<label> 
											<input type="checkbox" class="ace checks" /> 
											<span class="lbl"></span>
											</label>
											</th>
                                        	<th class="text-center">报销内容</th>
                                            <th class="text-center">报销人</th>
                                            <th class="text-center">是否是预算</th>
                                            <th class="text-center">付款日要付金额</th>
                                            <th class="text-center">付款日期</th>
                                            <th class="text-center">扣款事由</th>
                                            <th class="text-center">扣款金额</th>
                                            <th class="text-center">结款模式</th>
                                            <th class="text-center">请选择支付时间</th>
                                            <th class="text-center">支出现金或转账现金/元</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent">
                                        
                                    </tbody>
                                    
                                </table>
                                <div id="pager" class="pull-right">
                                
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
    <script src="${ctx }/static/js/vendor/typeahead.js"></script>
    <script src="${ctx }/static/js/vendor/mSlider.min.js"></script>
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
		  	this.setIndex = function(index){
		  		_index=index;
		  	}
		  	
		  	this.getIndex = function(){
		  		return _index;
		  	}
		  	this.setName = function(name){
		  		_name=name;
		  	}
		  	this.getName = function(){
		  		return _name;
		  	}
		  	this.getCount = function(){
		  		return _count;
		  	}
		  	this.setCount = function(count){
		  		_count=count;
		  	}
		  	this.getCountall = function(){
		  		return _countall;
		  	}
		  	this.setCountall = function(countall){
		  		_countall=countall;
		  	}
		  	self.setIndex(null)
		  	self.setCache(null)
		  	function p(s) {
				return s < 10 ? '0' + s: s;
				}
			var myDate = new Date();
				//获取当前年
				var year=myDate.getFullYear();
				//获取当前月
				var month=myDate.getMonth()+1;
				//获取当前日
				var date=myDate.getDate(); 
				var h=myDate.getHours();       //获取当前小时数(0-23)
				var m=myDate.getMinutes();     //获取当前分钟数(0-59)
				var s=myDate.getSeconds();  
				var myDate2 = new Date();
				//获取当前年
				var year2=myDate2.getFullYear();
				//获取当前月
				var month2=myDate2.getMonth()+1;
				//获取当前日
				var date2=myDate2.getDate();
				var now2=year2+'-'+p(month2)+"-"+p(date2)+' '+'00:00:00';
				var day = new Date(year,month,0);
				var firstdate = year + '-' + p(month) + '-01'+' '+'00:00:00';
				var getday = year + '-' + p(month) + date+' '+'00:00:00';
				var lastdate = year + '-' + p(month) + '-' + day.getDate() +' '+'23:59:59';
				$('#startTime').val(firstdate);
				$('#endTime').val(lastdate);
				
				var myDate3 = new Date();
				 myDate3.setMonth(month + 2);
				//获取当前年
				var year3=myDate3.getFullYear();
				//获取当前月
				var month3=myDate3.getMonth();
				//获取当前日
				var date3=myDate3.getDate(); 
			 var data={
						page:1,
				  		size:13,
				  		flag:0,
				} 
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
				self.mater();
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/fince/getExpenseAccount",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				var newDate=/\d{4}-\d{1,2}-\d{1,2}/g.exec(o.expenseDate)
		      				var newDate1=/\d{4}-\d{1,2}-\d{1,2}/g.exec(o.paymentDate)
		      				var k;
		      				 if(o.user==null){
		      					 k=""
		      				 }else{
		      					 k=o.user.userName
		      				 }
		      				 var a="";
		      				 if(o.budget==1){
		      					 a="预算"
		      				 }else{
		      					 a="";
		      				 }
		      				 var b;
		      				 if(o.settleAccountsMode==0){
		      					 b=""
		      				 }else if(o.settleAccountsMode==1){
		      					 b="现金"
		      				 }else if(o.settleAccountsMode==2){
		      					 b="月结"
		      				 }else{
		      					 b=""
		      				 }
		      				 
		      				var c;
		      				if(o.flag==0){
		      					c="未核对"
		      				}else{
		      					c="已核对"
		      				}
		      				var d;
		      				if(o.paymentMoney==null){
		      					d=o.money
		      				}else{
		      					d=o.paymentMoney
		      				}
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="hidden batch">'+o.id+'</td>'
		      				+'<td class="hidden userId ">'+o.userId+'</td>'
		      				+'<td class="text-center  content">'+o.content+'</td>'
		      				+'<td class="text-center  contractTime">'+k+'</td>'
		      				+'<td class="text-center  firstNames" data-online="'+o.budget+'">'+a+'</td>'
		      				+'<td class="text-center  money">'+o.money+'</td>'
		      				+'<td class="text-center  expenseDate">'+newDate+'</td>'
		      				+'<td class="text-center  withholdReason">'+o.withholdReason+'</td>'
		      				+'<td class="text-center  withholdMoney">'+o.withholdMoney+'</td>'
		      				+'<td class="text-center  contractNumber" data-online2="'+o.settleAccountsMode+'">'+b+'</td>'
		      				+'<td class="text-center "><input id="'+i+'" data-id="'+i+'" placeholder="请输入开始时间" class="form-control time laydate-icon" value="'+(newDate1 !=null ? newDate1 :"")+'"></td>'
		      				+'<td class="text-center"><input data-id="'+o.id+'" class="form-control paymentMoney" value="'+(d !=null ? d : "")+'"/></td>'
		      				+'<td class="text-center  name"><button class="btn btn-sm btn-info  btn-danger Tips" data-id='+o.id+'>'+c+'</button></td></tr>'
		      			}); 
		      			self.setCount(result.data.pageNum)
				        //显示分页
					   	 laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		  var expenseDate=""
										var paymentDate=""
										if($("#selectone").val()=="expenseDate"){
											expenseDate="2018-10-08 00:00:00"
										}
										if($("#selectone").val()=="paymentDate"){
											paymentDate="2018-10-08 00:00:00"
										}
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		Username:$('#firstNames').val(),
								  			paymentDate:paymentDate,
								  			expenseDate:expenseDate,
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			flag:$("#flag").val(),
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   	$('.checkWork').each(function(j,k){
							var ids=$(this).parent().data('online')
		  					$(k).val(ids)
						});
						$('.checkWork2').each(function(j,k){
							var ids=$(this).parent().data('online2')
		  					$(k).val(ids)
						});
					   	self.loadEvents();
					   self.checkeddto();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			  this.checkeddto=function(){
					
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
			this.loadEvents = function(){
				//核对
				$('.Tips').on('click',function(){
					var a;
					var that=$(this);
					if($(this).parent().parent().find('.paymentMoney').val()==""){
						return	layer.msg("请填写支付金额", {icon: 2});
					}
					if($(this).parent().parent().find('.time').val()==""){
						return	layer.msg("请填写支付时间", {icon: 2});
					}
					if($(this).text() == "未核对"){
						$(this).text("已核对")
						a=1
					}else{
						$(this).text("未核对")
						a=0
					}
  					var postData = {
							id:$(this).data('id'),
							paymentDate:$(this).parent().parent().find('.time').val()+' '+'00:00:00',
							paymentMoney:$(this).parent().parent().find('.paymentMoney').val(),
							flag:a
					}
  					var index;
					$.ajax({
						url:"${ctx}/fince/auditExpenseAccount",
						data:postData,
						type:"POST",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						success:function(result){
							if(0==result.code){
							layer.msg("核对成功", {icon: 1});
							/* var newDate=/\d{4}-\d{1,2}-\d{1,2}/g.exec(now2)
							that.parent().parent().find('.time').val(newDate) */
							layer.close(index);
							}else{
								layer.msg(result.message, {icon: 2});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					
				})
				
				
				
				
  				$('.checkWork').each(function(j,k){
					var ids=$(this).parent().data('online')
  					$(k).val(ids)
				});
  				
  				$(".time").on('click',function(){
  					var a=$(this).data('id')
  					laydate({elem: '#'+a+'', istime: true, format: 'YYYY-MM-DD',})
  				})
  				
  				
  	
  				
  				
  				$(".paymentMoney").blur(function(){
  					if($(this).val()==""){
						return	layer.msg("请填写转账金额", {icon: 2});
					}
  					if($(this).parent().parent().find('.time').val()==""){
						return	layer.msg("请填写支付时间", {icon: 2});
					}
  					var postData = {
							id:$(this).data('id'),
							paymentDate:$(this).parent().parent().find('.time').val()+' '+'00:00:00',
							paymentMoney:$(this).val(),
					}
  					var index;
					$.ajax({
						url:"${ctx}/fince/auditExpenseAccount",
						data:postData,
						type:"POST",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							if(0==result.code){
							layer.msg("修改成功！", {icon: 1});
							layer.close(index);
							}else{
								layer.msg(result.message, {icon: 2});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
  				})
				
			}
			
			this.mater=function(){
				//提示甲方
				$(".aName2").typeahead({
					//ajax 拿way数据
					source : function(query, process) {
							return $.ajax({
								url : '${ctx}/system/user/pages',
								type : 'GET',
								data : {
									userName:query,
								},
								success : function(result) {
									//转换成 json集合
									 var resultList = result.data.rows.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.userName, id:item.id}
					                        //处理 json对象为字符串
					                        return JSON.stringify(aItem);
					                    });
									//提示框返回数据
									//提示框返回数据
									 if(resultList==""){
											var aItemtw = {"name":"查无此人", "id":""}
											resultList.push(JSON.stringify(aItemtw)); 
										} 
									 return process(resultList);
								},
							})
						
							//提示框显示
						}, highlighter: function (item) {
						    //转出成json对象
							 var item = JSON.parse(item);
							return item.name
							//按条件匹配输出
		                }, matcher: function (item) {
		                	//转出成json对象
					        var item = JSON.parse(item);
					        self.setIndex(item.id);
					    	return item.id
					    },
						//item是选中的数据
						updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
							self.setIndex(item.id);
							
								return item.name
						},

						
					});
			}
			this.events = function(){
				
				$('.searchtask').on('click',function(){
					var expenseDate=""
					var paymentDate=""
					if($("#selectone").val()=="expenseDate"){
						expenseDate="2018-10-08 00:00:00"
					}
					if($("#selectone").val()=="paymentDate"){
						paymentDate="2018-10-08 00:00:00"
					}
					var data = {
				  			page:1,
				  			size:13,
				  			Username:$('#firstNames').val(),
				  			paymentDate:paymentDate,
				  			expenseDate:expenseDate,
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(),
				  			flag:$("#flag").val(),
				  	}
		            self.loadPagination(data);
				});
				//新增
				$('#addgroup').on('click',function(){
					self.mater();
					var _index;
					var index;
					var postData;
					if($("#content").val()==""){
						return	layer.msg("请填报销内容", {icon: 2});
					}
					if($("#money").val()==""){
						return layer.msg("请填写报销金额", {icon: 2});
					}
					  postData={
						content:$("#content").val(),
						budget:$("#budget").val(),
						money:$("#money").val(),
						expenseDate:$("#expenseDate").val(),
						withholdReason:$("#withholdReason").val(),
						withholdMoney:$("#withholdMoney").val(),
						settleAccountsMode:$("#settleAccountsMode").val(),
						userId:self.getIndex(),
					  }
					  $.ajax({
							url:"${ctx}/fince/addExpenseAccount",
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
								 $("#content").val("")
								 $("#user").val("")
								 $("#budget").val("0")
								 $("#money").val("")
								 $("#expenseDate").val("")
								 $("#withholdReason").val("")
								 $("#withholdMoney").val("")
								 $("#settleAccountsMode").val("0")
								}else{
									layer.msg("添加失败", {icon: 2});
								}
								
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
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