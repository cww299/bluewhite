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
<title>各类杂支</title>
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
    <script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
</head>

<body>

<div class="panel panel-default">
	<div class="panel-body">					
		<table>
			<tr>
				<td>日期开始:</td>
				<td><input id="startTime" placeholder="请输入开始时间"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"></td>
				<td>&nbsp;&nbsp;</td>
				<td>日期结束:</td>
				<td><input id="endTime" placeholder="请输入结束时间"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>乙方:</td>
				<td><input type="text" name="name" id="partyNames"
					class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><span class="input-group-btn"><button type="button" class="btn btn-info btn-square btn-sm navbar-right btn-3d searchtask">
						查找 <i class="icon-search icon-on-right bigger-110"></i></button></span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> 
				<td><span class="input-group-btn"><button type="button"class="btn btn-danger  btn-sm btn-3d start">一键删除</button></span></td>
			</tr>
		</table>
		<h1 class="page-header"></h1>
										
						
		<table class="table table-hover">
			<thead>
				<tr>
					<th class="center"><label> <input type="checkbox"class="ace checks" /> <span class="lbl"></span></label></th>
					<th class="text-center">乙方</th>
					<th class="text-center">往来日期</th>
					<th class="text-center">往来明细</th>
					<th class="text-center">往来金额</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
			<tr>
				<td class="text-center"></td>
				<td class="text-center"><input type="text" id="bName" class="bName2 text-center"
					style="border: none; width: 68px; height: 30px; background-color: #BFBFBF;"></td>
				<td class="text-center" style="padding: 9px 0px 2px 350px;"><input id="mixtTime" placeholder="请输入时间"
					class="form-control laydate-icon" onClick="laydate({elem: '#mixtTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
					style="border: none; width: 90px; height: 30px; background-color: #BFBFBF;"></td>
				<td class="text-center"><input type="text" id="mixDetailed" style="border: none; width: 150px; height: 30px; background-color: #BFBFBF;"></td>
				<td class="text-center"><input type="text" id="mixPrice"
					style="border: none; width: 60px; height: 30px; background-color: #BFBFBF;"></td>
				<td class="text-center"><button type="button" 	id="addgroup" 	class="btn btn-success btn-sm btn-3d pull-right">新增</button></td>
			</tr>
			<tbody id="tablecontent">
			</tbody>
		</table>
		<div id="pager" class="pull-right"></div>
	</div>
</div>

	<div class="wrap">
		<div class="layer-right3" style="display: none;">
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
				<div id="pager2" class="pull-right"></div>
			</div>
		</div>
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
				$('#startTime').val(firstdate);
				$('#endTime').val(lastdate);
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
				      url:"${ctx}/fince/getMixes",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				var newDate=/\d{4}-\d{1,2}-\d{1,2}/g.exec(o.mixtTime)
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="hidden batch">'+o.id+'</td>'
		      				+'<td class="text-center edit  mixPartyNames">'+o.mixPartyNames+'</td>'
		      				+'<td class="text-center hidden  mixPartyNamesId">'+o.mixPartyNamesId+'</td>'
		      				+'<td class="text-center editt mixtTime">'+newDate+'</td>'
		      				+'<td class="text-center editt mixDetailed">'+o.mixDetailed+'</td>'
		      				+'<td class="text-center editt mixPrice">'+o.mixPrice+'</td>'
		      				+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans update" data-id='+o.id+'>编辑</button></td></tr>'
							
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
									  		mixPartyNames:$('#partyNames').val(),
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
				//修改方法
				$('.update').on('click',function(){
					if($(this).text() == "编辑"){
					
						$(this).text("保存")
						
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini bName2'  style='border: none;width:90px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
				        });
						$(this).parent().siblings(".editt").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini aName2'  style='border: none;width:68px; height:30px; background-color: #BFBFBF;'  type='text' value='"+$(this).text()+"'>");
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
							var c;
							if(self.getCache()==null){
								c=$(this).parent().parent().find(".mixPartyNamesId").text();
							}else{
								c=self.getCache();
							}
							var postData = {
									id:$(this).data('id'),
									mixPartyNames:$(this).parent().parent().find(".mixPartyNames").text(),
									mixtTime:$(this).parent().parent().find(".mixtTime").text()+' '+'00:00:00',
									mixDetailed:$(this).parent().parent().find(".mixDetailed").text(),
									mixPrice:$(this).parent().parent().find(".mixPrice").text(),
									mixPartyNamesId:c,
							}
							
							var index;
							$.ajax({
								url:"${ctx}/fince/addMixed",
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
			}
			this.mater=function(){
				
				
			/* 	var proposals = ['百度1', '百度2', '百度3', '百度4','a1','a2','a3','a4','b1','b2','b3','b4'];
				$('.bName2').autocomplete({
				    hints: proposals,
				    width: 300,
				    height: 30,
				    onSubmit: function(text){
				       /*  $('#message').html('Selected: <b>' + text + '</b>');
				    }
				}); */
				
				
				
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
				  			mixPartyNames:$('#partyNames').val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(),
				  			batchNumber:$("#batchNumber2").val(),
				  	}
		            self.loadPagination(data);
				});
				
				//新增
				$('#addgroup').on('click',function(){
					self.mater();
					var _index;
					var index;
					var postData;
					if($("#bName").val()==""){
						return	layer.msg("请填写乙方", {icon: 2});
					}
					if($("#mixPrice").val()==""){
						return layer.msg("请填写来往价格", {icon: 2});
					}
					if($("#mixtTime").val()==""){
						return layer.msg("请填写来往日期", {icon: 2});
					}
					if(self.getCache()==""){
						return layer.msg("乙方不在客户表中 请添加", {icon: 2});
					}
					  postData={
							  mixDetailed:$("#mixDetailed").val(),
							  mixPrice:$("#mixPrice").val(),
							  mixtTime:$("#mixtTime").val(),
							  mixPartyNames:$("#bName").val(),
							  mixPartyNamesId:self.getCache(),
							  mixtSubordinateTime:$('#startTime').val(),
					  }
					  $.ajax({
							url:"${ctx}/fince/addMixed",
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
									$("#mixPrice").val(""),
									$("#mixDetailed").val("")
									$("#mixtTime").val("")
									$("#bName").val("")
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
							url:"${ctx}/fince/deleteMixed",
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
								  		mixPartyNames:$('#partyNames').val(),
								  		orderTimeBegin:$("#startTime").val(),
							  			orderTimeEnd:$("#endTime").val(), 
							  			batchNumber:$("#batchNumber2").val(),
							  	}
								self.loadPagination(data)
								layer.close(index);
								}else{
									layer.msg("删除失败！", {icon: 2});
									layer.close(index);
								}
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 })
				})
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>

</body>

</html>