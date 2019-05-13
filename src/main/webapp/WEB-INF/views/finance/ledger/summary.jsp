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
<title>汇总</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

	<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctx }/static/css/main.css">  <!-- 界面样式 -->
 
 	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
    <script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx }/static/js/layer/layer.js"></script>	
    <script src="${ctx }/static/js/vendor/typeahead.js"></script>
    <script src="${ctx }/static/js/laypage/laypage.js"></script> 
    <script src="${ctx }/static/js/vendor/mSlider.min.js"></script>
    <script src="${ctx }/static/js/laydate-icon/laydate.js"></script> 
</head>

<body>
	
<div class="panel panel-default">
	<div class="panel-body">					
		<table>
			<tr>
				<td>开始:</td>
				<td><input id="startTimetw" placeholder="请输入开始时间" class="form-control laydate-icon"
					onClick="laydate({elem: '#startTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>结束:</td>
				<td><input id="endTimetw" placeholder="请输入结束时间" class="form-control laydate-icon"
					onClick="laydate({elem: '#endTimetw', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"></td>
				<td>&nbsp;&nbsp;</td>
				<td>乙方:</td>
				<td><input type="text" name="name" id="partyNames" class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>在途款:</td>
				<td><input type="text" name="name" disabled="disabled" id="offshorePay" class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>已认可未到货款:</td>
				<td><input type="text" name="name" disabled="disabled" id="acceptPay" class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>客户预付款:</td>
				<td><input type="text" name="name" disabled="disabled" id="acceptPayable" class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>应收账款汇总:</td>
				<td><input type="text" name="name" disabled="disabled" id="allprice" class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="input-group-btn"><button type="button" class="btn btn-info btn-square btn-sm navbar-right btn-3d searchtask">
						查找 <i class="icon-search icon-on-right bigger-110"></i></button></span></td>
			</tr>
		</table>
		<h1 class="page-header"></h1>
					
						
		<table class="table table-hover">
			<thead>
				<tr>
					<th class="center"><label> <input type="checkbox"
							class="ace checks" /> <span class="lbl"></span>
					</label></th>
					<th class="text-center">乙方</th>
					<th class="text-center">当表已确定离岸货款值</th>
					<th class="text-center">当表经业务员跟进客户已认可的货款</th>
					<th class="text-center">当表双方都认可的除货款以外的应付</th>
					<th class="text-center">当表在途和有争议货款</th>
					<th class="text-center">当月未到货款</th>
					<th class="text-center">当月客户多付货款转下月应付</th>
					<th class="text-center">已到货款</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
			<tbody id="tablecontent">

			</tbody>

		</table>
		<div id="pager" class="pull-right"></div>
	</div>
</div>

	<!--隐藏框已完成的批次开始  -->
	<div id="addworking" style="display: none;">
		<div class="panel-body">
			<div class="form-group"></div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="text-center">日期</th>
						<th class="text-center">到账款</th>
						<th class="text-center">批注</th>
						<th class="text-center">操作</th>
					</tr>
				</thead>
				<tr>
					<td class="text-center"><input id="contractTime"
						placeholder="请输入时间" class="form-control laydate-icon"
						onClick="laydate({elem: '#contractTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"></td>
					<td class="text-center"><input type="text"
						class="form-control" id="planNumbers"></td>
					<td class="text-center"><input type="text"
						class="form-control" id="planPrice"></td>
					<td class="text-center"><button type="button" id="addgroup"
							class="btn btn-success btn-sm btn-3d pull-right">新增</button></td>
				</tr>
				<tbody id="tableworking">
				</tbody>
			</table>
			<div id="pagerr" class="pull-right"></div>
		</div>
		<!--隐藏框 已完成的批次结束  -->
	</div>




	
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
				$('#startTimetw').val(firstdate);
				$('#endTimetw').val(lastdate);
			 var data={
						page:1,
				  		size:13,
				  		orderTimeBegin:firstdate,
				  		orderTimeEnd:lastdate,
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
				      url:"${ctx}/fince/getBill",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="hidden batch">'+o.id+'</td>'
		      				+'<td class="text-center  mixPartyNames">'+o.partyNames+'</td>'
		      				+'<td class="text-center edit offshorePay">'+o.offshorePay+'</td>'
		      				+'<td class="text-center editt acceptPay">'+o.acceptPay+'</td>'
		      				+'<td class="text-center editt acceptPayable">'+o.acceptPayable+'</td>'
		      				+'<td class="text-center editt disputePay">'+o.disputePay+'</td>'
		      				+'<td class="text-center editt nonArrivalPay">'+o.nonArrivalPay+'</td>'
		      				+'<td class="text-center editt overpaymentPay">'+o.overpaymentPay+'</td>'
		      				+'<td class="text-center editt overpaymentPay">'+o.arrivalPay+'</td>'
		      				+'<td class="text-center"> <button class="btn btn-sm btn-danger btn-trans Tips"  data-id='+o.id+'>已到款明细</button></td></tr>'
							
		      			}); 
		      			self.setCount(result.data.pageNum)
				        //显示分页
					   	 laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		 
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		orderTimeBegin:$("#startTimetw").val(),
								  			orderTimeEnd:$("#endTimetw").val(),
								  			partyNamesId:self.getCache(),
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   	self.loadEvents();
					   self.checkeddto();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			    
			    
			   
			    $.ajax({
				      url:"${ctx}/fince/collectBill",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      				$("#offshorePay").val(o.offshorePay)
		      				$("#acceptPay").val(o.acceptPay)
		      				$("#acceptPayable").val(o.acceptPayable)
		      				$("#allprice").val(o.offshorePay+o.acceptPay-o.acceptPayable)
		      			}); 
					   	layer.close(index);
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
				//提示
							$('.Tips').on('click',function(){
								$('#addgroup').off('click');
								var id=$(this).data('id')
								var html2="";
								var postData2={
								
										id:$(this).data('id'),
								}  
					$.ajax({
							url:"${ctx}/fince/getBillDate",
							data:postData2,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								$(result.data.data).each(function(i,o){
				      				html2 +='<tr>'
				      				+'<td class="text-center edit sumname">'+o.name+'</td>'
				      				+'<td class="text-center edit sumva">'+o.price+'</td>'
				      				+'<td class="text-center edit sumvatw">'+o.value+'</td>'
				      				+'<td><button class="btn btn-sm btn-danger btn-trans delete2">删除</button></td></tr>'
				      			}); 
								layer.close(index);
							   	 $("#tableworking").html(html2);
							   	$(".delete2").on('click',function(){
									$(this).parent().parent().remove();
								})
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
								$("#addgroup").on('click',function(){
									var html=""
									var a=$("#contractTime").val()
									var b=$("#planNumbers").val()
									var c=$("#planPrice").val()
									html='<tr><td class="text-center sumname">'+a+'</td><td class="text-center sumva">'+b+'</td><td class="text-center sumvatw">'+c+'</td><td><button class="btn btn-sm btn-danger btn-trans delete">删除</button></td></tr>'
									$("#tableworking").append(html)
									$(".delete").on('click',function(){
									$(this).parent().parent().remove();
								})
								})
								var dicDiv=$('#addworking');
								_index = layer.open({
									  type: 1,
									  skin: 'layui-layer-rim', //加上边框
									  area: ['30%', '70%'], 
									  btnAlign: 'c',//宽高
									  maxmin: true,
									  title:name,
									  content: dicDiv,
									  btn: ['确定', '取消'],
									  yes:function(index, layero){
										  var c;
										  var arr= new Array();
										  var date;
										  $('.sumname').each(function(i,o){
											var a= $(this).text();
											var b= $(this).next().text();
											var d= $(this).next().next().text();
											 c={"name":a,"value":d,"price":b};
											 arr.push(c);
										  })
										  date={"data":arr};
										  var postData = {
													id:id,
													dateToPay:JSON.stringify(date),
											}
										  var index;
											$.ajax({
												url:"${ctx}/fince/updateBill",
												data:postData,
												traditional: true,
												type:"Get",
												beforeSend:function(){
													index = layer.load(1, {
														  shade: [0.1,'#fff'] //0.1透明度的白色背景
														});
												},
												success:function(result){
													if(0==result.code){
														layer.msg("成功！", {icon: 1});
													 	var _date={
														  		orderTimeBegin:$("#startTimetw").val(),
													  			orderTimeEnd:$("#endTimetw").val(),
													  			partyNamesId:self.getCache(),	
														}
														self.loadPagination(_date) ;
													layer.close(index);
													}else{
														layer.msg("修改失败！", {icon: 2});
														layer.close(index);
													}
												},error:function(){
													layer.msg("操作失败！", {icon: 2});
													layer.close(index);
												}
											});
										},
									  end:function(){
										  $('#addworking').hide();
									  }
								});
										})
			}
			this.mater=function(){
				//提示乙方
				$(".bName2").typeahead({
					//ajax 拿way数据
					source : function(query, process) {
							return $.ajax({
								url : '${ctx}/fince/getContact',
								type : 'GET',
								data : {
									conPartyNames:query,
									
								},
								success : function(result) {
									//转换成 json集合
									 var resultList = result.data.rows.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.conPartyNames, id:item.id}
					                        //处理 json对象为字符串
					                        return JSON.stringify(aItem);
					                    });
									if(result.data.rows==""){
										 self.setCache("");
									}
									//提示框返回数据
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
					        self.setCache(item.id);
					    	return item.id
					    },
						//item是选中的数据
						updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
							self.setCache(item.id);
								return item.name
						},

						
					});
			}
			this.events = function(){
				$('.searchtask').on('click',function(){
					var data = {
				  			orderTimeBegin:$("#startTimetw").val(),
				  			orderTimeEnd:$("#endTimetw").val(),
				  			
				  			partyNamesId:self.getCache(),
				  	}
			
				self.loadPagination(data);
				});
				
				//提示乙方
				$("#partyNames").typeahead({
					//ajax 拿way数据
					source : function(query, process) {
							return $.ajax({
								url : '${ctx}/fince/getContact',
								type : 'GET',
								data : {
									conPartyNames:query,
									
								},
								success : function(result) {
									//转换成 json集合
									 var resultList = result.data.rows.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.conPartyNames, id:item.id}
					                        //处理 json对象为字符串
					                        return JSON.stringify(aItem);
					                    });
									if(result.data.rows==""){
										 self.setCache("");
									}
									//提示框返回数据
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
					        self.setCache(item.id);
					       
					    	return item.id
					    },
						//item是选中的数据
						updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
							self.setCache(item.id);
								return item.name
						},

						
					});
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>

</body>

</html>