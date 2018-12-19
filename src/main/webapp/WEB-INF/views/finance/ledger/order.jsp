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
    <title>订单管理</title>
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
                                <h3 class="panel-title">订单信息</h3>
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
								<td>甲方:</td><td><input type="text" name="name" id="firstNames"  class="form-control search-query name" /></td>
								<td>&nbsp&nbsp</td>
								<td>乙方:</td><td><input type="text" name="name" id="partyNames"  class="form-control search-query name" /></td>
								<td>&nbsp&nbsp</td>
								<td>产品名:</td><td><input type="text" name="name" id="productName"  class="form-control search-query name" /></td>
								<td>&nbsp&nbsp</td>
								<td>批次号:</td><td><input type="text" name="name" id="batchNumber2"  class="form-control search-query name" /></td>
								<td>合同开始:</td>
								<td>
								<input id="startTime" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
									<td>&nbsp&nbsp</td>
				<td>合同结束:</td>
				<td>
					<input id="endTime" placeholder="请输入结束时间" class="form-control laydate-icon"
             onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm navbar-right btn-3d searchtask">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
									<button type="button" class="btn btn-danger  btn-sm btn-3d start">
									一键删除
									</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
								<input type="file" name="file" id="upfile"  style="display:inline">
									 <button type="button" id="btn" class="btn btn-success  btn-sm btn-3d pull-right">
									点击导入
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
                                        	<th class="text-center">当月销售编号</th>
                                            <th class="text-center">合同签订日期</th>
                                            <th class="text-center">甲方</th>
                                            <th class="text-center">乙方</th>
                                            <th class="text-center">当批批次号</th>
                                            <th class="text-center">当批计划单号</th>
                                            <th class="text-center">当批产品名</th>
                                            <th class="text-center">当批合同数量</th>
                                            <th class="text-center">当批合同总价（元）</th>
                                            <th class="text-center">预付款备注</th>
                                            <th class="text-center">手动填写当批单只价格</th>
                                            <th class="text-center">线上or线下</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                        <tr>
                                    
                                        	<td class="text-center"></td>
                                        	<td class="text-center"></td>
                                            <td class="text-center" style="padding: 9px 0px 2px 4px;"><input id="contractTime" placeholder="请输入时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#contractTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" style="border: none;width:90px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="aName" class="aName2 text-center"  data-provide="typeahead" style="border: none;width:68px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="bName" class="bName2 text-center" style="border: none;width:68px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="batchNumber" class="text-center" style="border: none;width:105px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="planNumbers" class="text-center" placeholder="可不填" style="border: none;width:60px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="ProductName" class="text-center" style="border: none;width:150px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"><input type="text" id="contractNumber" class="text-center" style="border: none;width:50px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"></td>
                                            <td class="text-center"><input type="text" id="remarksPrice" class="text-center" placeholder="可不填" style="border: none;width:80px; height:30px; background-color: #BFBFBF;"></td>
                                            <td class="text-center"></td>
                                            <td class="text-center"><select class="text-center" id="online" style="border: none;width:68px; height:30px; background-color: #BFBFBF;"><option value="0">线下</option><option value="1">线上</option></select></td>
                                            <td class="text-center"><button type="button"  id="addgroup" class="btn btn-success btn-sm btn-3d pull-right">新增订单</button></td>
                                    
                                        </tr>
                                    
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
        <!--隐藏框 产品新增开始  -->
        <!-- <div id="addDictDivType" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeForm">
				<div class="form-group">
                                        <label class="col-sm-3 control-label">合同签订日期:</label>
                                        <div class="col-sm-6">
                                            <input id="contractTime" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#contractTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">甲方:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="aName" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">乙方:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="bName" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">当批批次号:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="batchNumber" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">当批产品名:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="ProductName" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">当批合同数量:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="contractNumber" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">预付款备注:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="remarksPrice" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">当批计划单号:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="planNumbers" class="form-control">
                                        </div>
                 </div>
				</form>
</div>
</div> -->
 <!--隐藏框 产品新增结束  -->
 
 
 <div class="wrap">
<div class="layer-right3" style="display: none;">
<div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-12 col-sm-12  col-md-12">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group">
							<table><tr>
								<td>乙方:</td><td><input type="text" name="name" id="partyNames2"  class="form-control search-query name" /></td>
								<td>&nbsp&nbsp</td>
								<td>产品名:</td><td><input type="text" name="name" id="productName2"  class="form-control search-query name" /></td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm navbar-right btn-3d searchtask2">
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
                                            <th class="text-center">当批产品名</th>
                                            <th class="text-center">乙方选择</th>
                                            <th class="text-center">单只价格（元）</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent2">
                                        
                                    </tbody>
                                    
                                </table>
                                <div id="pager2" class="pull-right">
                                
                                </div>
                            </div>
				</div>
  </div>
 
 
 
 
 
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
				      url:"${ctx}/fince/getOrder",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				var newDate=/\d{4}-\d{1,2}-\d{1,2}/g.exec(o.contractTime)
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="hidden batch">'+o.id+'</td>'
		      				+'<td class="hidden firstNamesId">'+o.firstNamesId+'</td>'
		      				+'<td class="hidden partyNamesId">'+o.partyNamesId+'</td>'
		      				+'<td class="text-center  salesNumber">'+o.salesNumber+'</td>'
		      				+'<td class="text-center edit contractTime">'+newDate+'</td>'
		      				+'<td class="text-center editt firstNames">'+o.firstNames+'</td>'
		      				+'<td class="text-center editt partyNames">'+o.partyNames+'</td>'
		      				+'<td class="text-center edit1 batchNumber">'+o.batchNumber+'</td>'
		      				+'<td class="text-center edit2 planNumbers">'+o.planNumbers+'</td>'
		      				+'<td class="text-center edit3 productName">'+o.productName+'</td>'
		      				+'<td class="text-center edit4 contractNumber">'+(o.contractNumber!=null ? o.contractNumber : 0)+'</td>'
		      				+'<td class="text-center  name contractPrice" style=" color:#c11f34">'+(o.contractPrice!=null ? o.contractPrice : 0)+'</td>'
		      				+'<td class="text-center edit5 remarksPrice">'+(o.remarksPrice!=null ? o.remarksPrice : "")+'</td>'
		      				+'<td class="text-center  name"><input type="text" class="price2" value="'+(o.price!=null ? o.price : "")+'" style="border: none;width:70px; height:30px; background-color: #BFBFBF;"></td>'
		      				+'<td class="text-center" edit5 data-online='+o.online+'><select class="text-center checkWork" disabled="disabled" style="border: none;width:68px; height:30px; background-color: #BFBFBF;"><option value="0">线下</option><option value="1">线上</option></select></td>'
		      				+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans update" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans Tips"  data-id='+o.id+' data-productname='+o.productName+' data-partynames='+o.partyNames+'>提示</button></td></tr>'
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
									  		productName:$('#productName').val(),
								  			firstNames:$('#firstNames').val(),
								  			partyNames:$('#partyNames').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			batchNumber:$("#batchNumber2").val(),
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
  				$('.checkWork').each(function(j,k){
					var ids=$(this).parent().data('online')
  					$(k).val(ids)
				});
				//修改方法
				$('.update').on('click',function(){
					if($(this).text() == "编辑"){
						$(this).text("保存")
						
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini'  style='border: none;width:90px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        });
						$(this).parent().siblings(".editt").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini aName2'  style='border: none;width:68px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        });
						$(this).parent().siblings(".edit1").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini'  style='border: none;width:105px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        });
						$(this).parent().siblings(".edit2").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini'  style='border: none;width:60px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        });
						$(this).parent().siblings(".edit3").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini'  style='border: none;width:150px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        });
						$(this).parent().siblings(".edit4").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini'  style='border: none;width:50px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        });
						$(this).parent().siblings(".edit5").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini'  style='border: none;width:80px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
						$(this).parent().parent().find(".checkWork").removeAttr("disabled")
				        });
						self.mater();
					}else{
							$(this).text("编辑")
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							$(this).parent().siblings(".editt").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							$(this).parent().siblings(".edit1").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							$(this).parent().siblings(".edit2").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							$(this).parent().siblings(".edit3").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							$(this).parent().siblings(".edit4").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							$(this).parent().siblings(".edit5").each(function() {  // 获取当前行的其他单元格
								$(this).parent().parent().find(".checkWork").attr('disabled','disabled');
					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							var c;
							var d;
							if(self.getIndex()==null){
								c=$(this).parent().parent().find(".firstNamesId").text();
							}else{
								c=self.getIndex();
							}
							if(self.getCache()==null){
								d=$(this).parent().parent().find(".partyNamesId").text();
							}else{
								d=self.getCache();
							}
							var postData = {
									id:$(this).data('id'),
									contractTime:$(this).parent().parent().find(".contractTime").text()+' '+'00:00:00',
									firstNames:$(this).parent().parent().find(".firstNames").text(),
									partyNames:$(this).parent().parent().find(".partyNames").text(),
									batchNumber:$(this).parent().parent().find(".batchNumber").text(),
									planNumbers:$(this).parent().parent().find(".planNumbers").text(),
									productName:$(this).parent().parent().find(".productName").text(),
									contractNumber:$(this).parent().parent().find(".contractNumber").text(),
									remarksPrice:$(this).parent().parent().find(".remarksPrice").text(),
									online:$(this).parent().parent().find(".checkWork").val(),
									firstNamesId:c,
									partyNamesId:d,
							}
							
							var index;
							$.ajax({
								url:"${ctx}/fince/addOrder",
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
										layer.msg("修改失败！", {icon: 1});
										layer.close(index);
									}
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
					}
				})
				
				
				$(".price2").blur(function(){
					var that=$(this)
					var postData = {
							id:$(this).parent().parent().find(".update").data('id'),
							price:$(this).val(),
					}
					var index;
					$.ajax({
						url:"${ctx}/fince/addOrder",
						data:postData,
						type:"POST",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						success:function(result){
							if(0==result.code){
							that.parent().parent().find(".contractPrice").text(result.data.contractPrice)
							layer.msg("当批合同总价为"+result.data.contractPrice+"", {icon: 1});
							layer.close(index);
							}else{
								layer.msg("失败！", {icon: 1});
								layer.close(index);
							}
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
				})				
				
				
				
				//提示
							$('.Tips').on('click',function(){
								var that=$(this)
								var id=$(this).data('id')
								self.setName(id)
								self.setCountall(that)
								var ids=that.data("id");
							$(".batch").each(function(i,o){
							var a=$(o).text();
							if(a==ids){
								$(o).parent().addClass("danger");
								$(o).parent().siblings().removeClass("danger");
							}
							})
								var postData = {
								page:1,
					  			size:10,
								cusProductName:$(this).data('productname'),
								cusPartyNames:$(this).data('partynames')
							}
								self.lodwork(postData,id,that)
					})
				
				
			}
			
			this.lodwork=function(postData,id,that,a){
				var index;
				$.ajax({
					url:"${ctx}/fince/getCustomer",
					data:postData,
					type:"GET",
					beforeSend:function(){
						index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							});
					},
					
					success:function(result){
						if(0==result.code){
							if(a!=1){
							$(".layer-right3").css("display","block");
							var demo = new mSlider({
								dom:".layer-right3",
								direction: "right",
								distance:"35%",
								
							})
							demo.open()
							}
			var html1="";				
		$(result.data.rows).each(function(i,o){
				html1 +='<tr>'
				+'<td class="text-center edit name">'+o.cusProductName+'</td>'
				+'<td class="text-center edit name">'+o.cusPartyNames+'</td>'
				+'<td class="text-center edit cusPrice">'+o.cusPrice+'</td>'
				+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans choice" data-id='+o.id+'>选择</button></tr>'
			}); 
		laypage({
		      cont: 'pager2', 
		      pages: result.data.totalPages, 
		      curr:  result.data.pageNum || 1, 
		      jump: function(obj, first){ 
		    	  if(!first){ 
		    		 
			        	var _data = {
			        			page:obj.curr,
						  		size:10,
						  		cusProductName:$('#productName2').val(),
					  			cusPartyNames:$('#partyNames2').val(),
					  	}
			        	var a=self.getCountall()
						var b=self.getName()
				       self.lodwork(_data,b,a,1);
				     }
		      }
		    }); 
		$("#tablecontent2").html(html1); 
		$(".choice").on('click',function(){
			var thtt=$(this)
			var postData = {
					id:id,
					price:$(this).parent().parent().find('.cusPrice').text(),
			}
			var index;
			$.ajax({
				url:"${ctx}/fince/addOrder",
				data:postData,
				type:"POST",
				beforeSend:function(){
					index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						});
				},
				success:function(result){
					if(0==result.code){
					that.parent().parent().find(".contractPrice").text(result.data.contractPrice)
					that.parent().parent().find(".price2").val(result.data.price)
					layer.msg("当批合同总价为"+result.data.contractPrice+"", {icon: 1});
					layer.close(index);
					}else{
						layer.msg("失败！", {icon: 1});
						layer.close(index);
					}
				},error:function(){
					layer.msg("操作失败！", {icon: 2});
					layer.close(index);
				}
			});
		})
							layer.close(index);
						}else{
							layer.msg("操作失败", {icon: 1});
							layer.close(index);
						}
					},error:function(){
						layer.msg("操作失败！", {icon: 2});
						layer.close(index);
					}
				});
				
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
									sale:1
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
				  			page:1,
				  			size:13,
				  			productName:$('#productName').val(),
				  			firstNames:$('#firstNames').val(),
				  			partyNames:$('#partyNames').val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  			batchNumber:$("#batchNumber2").val(),
				  	}
		            self.loadPagination(data);
				});
				
				$('.searchtask2').on('click',function(){
					var data = {
				  			page:1,
				  			size:10,
				  			cusProductName:$('#productName2').val(),
				  			cusPartyNames:$('#partyNames2').val(),
				  	}
				var a=self.getCountall()
				var b=self.getName()
		            self.lodwork(data,b,a,1);
				});
				//新增小组
				$('#addgroup').on('click',function(){
					self.mater();
					var _index;
					var index;
					var postData;
					if($("#contractTime").val()==""){
					return	layer.msg("请填写合同日期", {icon: 2});
					}
					if($("#aName").val()==""){
						return	layer.msg("请填写甲方", {icon: 2});
					}
					if($("#bName").val()==""){
						return	layer.msg("请填写乙方", {icon: 2});
					}
					if($("#batchNumber").val()==""){
						return layer.msg("请填写批次号", {icon: 2});
					}
					if($("#ProductName").val()==""){
						return layer.msg("请填写产品名", {icon: 2});
					}
					if($("#contractNumber").val()==""){
						return layer.msg("请填写当批合同数量", {icon: 2});
					}
					if(self.getIndex()==""){
						return layer.msg("甲方不是销售人员 请添加", {icon: 2});
					}
					var temper=$("#contractTime").val()
      				var dt = new Date(temper.replace(/-/,"/"))//转换成日期格式
      				var date2=dt.getDate();//获取天数
					var now2=year3+'-'+p(month3)+"-"+p(date2);
					  postData={
							  contractTime:$("#contractTime").val(),
							  firstNames:$("#aName").val(),
							  partyNames:$("#bName").val(),
							  batchNumber:$("#batchNumber").val(),
							  planNumbers:$("#planNumbers").val(),
							  productName:$("#ProductName").val(),
							  contractNumber:$("#contractNumber").val(),
							  remarksPrice:$("#remarksPrice").val(),
							  online:$("#online").val(),
							  firstNamesId:self.getIndex(),
							  partyNamesId:self.getCache(),
							  ashoreCheckr:0,
							  ashoreTime:now2+' '+'00:00:00',
							  orderTimeBegin:$("#startTime").val(),
					  		  orderTimeEnd:$("#endTime").val(),
					  }
					  $.ajax({
							url:"${ctx}/fince/addOrder",
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
									self.setCache(result.data.partyNamesId)
								 self.loadPagination(data); 
								 $("#batchNumber").val("")
								 $("#ProductName").val("")
								 $("#contractNumber").val("")
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
				//一键删除
				$('.start').on('click',function(){
					  var  that=$(".table-hover");
					  var arr=new Array()//员工id
					  	that.parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  var postData = {
								ids:arr,
						}
						
						var index;
						 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/fince/delete",
							data:postData,
							traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
								layer.msg("删除成功！", {icon: 1});
								var data = {
					        			page:self.getCount(),
								  		size:13,
								  		productName:$('#productName').val(),
							  			firstNames:$('#firstNames').val(),
							  			partyNames:$('#partyNames').val(),
							  			orderTimeBegin:$("#startTime").val(),
							  			orderTimeEnd:$("#endTime").val(),
							  			batchNumber:$("#batchNumber2").val(),
							  	}
								self.loadPagination(data)
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
				})
				
				
				//导入
				$('#btn').on('click',function(){
				
					if($('#upfile')[0].files[0]==null){
						return layer.msg("请选择需要导入的文件", {icon: 2});
					}
					  var imageForm = new FormData();
				
				  			
							imageForm.append("file",$('#upfile')[0].files[0]);
					 $.ajax({
							url:"${ctx}/excel/importOrder",
							data:imageForm,
							type:"post",
							processData:false,
							contentType: false,
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								if(0==result.code){
								layer.msg(result.message, {icon: 1});
								}else{
									layer.msg(result.message, {icon: 2});
								}
								layer.close(index);
							},
							error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						}); 
		          
					
				});
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>
       
</body>

</html>