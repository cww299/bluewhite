<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员汇总</title>
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>  <!-- 时间插件 -->
	<script src="${ctx }/static/js/layer/layer.js"></script>
	<script src="${ctx }/static/js/laypage/laypage.js"></script> 
	<link rel="stylesheet" href="${ctx }/static/css/main.css">
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
</head>
<body>

<div class="panel panel-default">
	<div class="panel-body">
	
		<table>
			<tr>
				<td>员工姓名:</td>
				<td><input type="text" name="name" id="name"
					class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>小组查询:</td>
				<td id="groupp"></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="input-group-btn">
					<button type="button"
						class="btn btn-info  btn-sm btn-3d  searchtask">
						查&nbsp;找</button>
				</span></td>
			</tr>
		</table>
							
		<h1 class="page-header"></h1>
		<table>
			<tr>
				<td><button type="button"
						class="btn btn-info btn-square btn-sm btn-3d attendance">一键添加考勤</button>&nbsp;&nbsp;</td>
				<td><input id="startTime" placeholder="请输入考勤日期"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button"
						class="btn btn-info btn-square btn-sm btn-3d position">到岗预计小时收入</button>&nbsp;&nbsp;</td>
				<td><input id="endTime" placeholder="请输入时间"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;</td>
				<td><input type="text" name="name" id="workPrice"
					class="form-control search-query" placeholder="预计收入" /></td>
			</tr>
		</table>
		
		
		<table class="table table-hover">
			<thead>
				<tr style="font-size: 14px">
					<th class="center"><label> <input type="checkbox"
							class="ace checks" /> <span class="lbl"></span>
					</label></th>
					<th class="text-center">序号</th>
					<th class="text-center">姓名</th>
					<th class="text-center">部门</th>
					<th class="text-center">职位</th>
					<th class="text-center">考勤时间</th>
					<th class="text-center">当月预计收入</th>
					<th class="text-center">工作状态</th>
					<th class="text-center">员工分组</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
			<tbody id="tablecontent" style="font-size: 14px">
				</tbody>
			</table>
			<div id="pager" class="pull-right"></div>
	</div>
</div>



	<script>
   jQuery(function($){
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
			 var data={
						page:1,
				  		size:10,	
				} 
			this.init = function(){
			//注册绑定事件
				self.events();
				self.loadPagination(data);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html ='';
			    $.ajax({
				      url:"${ctx}/system/user/pages",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				  var a;
		      				 if(o.group==null){
		      					a=0
		      				 }else{
		      					 a=o.group.id
		      				 }
		      				 var order = i+1;
		      				var k;
		      				 if(o.orgName==null){
		      					 k=""
		      				 }else{
		      					 k=o.orgName.name
		      				 }
		      				 var l;
		      				 if(o.position==null){
		      					 l=""
		      				 }else{
		      					 l=o.position.name
		      				 }
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center ">'+order+'</td>'
		      				+'<td class="text-center ">'+o.userName+'</td>'
		      				+'<td class="text-center ">'+k+'</td>'
		      				+'<td class="text-center ">'+l+'</td>'
		      				+'<td class="text-center "><input class="work"></input></td>'
		      				+'<td class="text-center edit workPrice">'+o.price*1+'</td>'
							+'<td class="text-center" data-status="'+o.status+'" data-id="'+o.id+'"><input type="radio"   class="rest" value="0">工作<input type="radio"   class="rest" value="1">休息 </td>'
							+'<td class="text-center"><div class="groupChange" data-id="'+o.id+'" data-groupid="'+a+'" ></div></td>'
							+'<td class="text-center"> <button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button></td></tr>'
		      			}); 
				        //显示分页
					  laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
						        	var _data = {
						        			page:obj.curr,
									  		size:10,
									  		userName:$('#name').val(),
									  		groupId:$('.selectcomplete').val()
								  	}
						            self.loadPagination(_data);
							     }
					      }
					    });
				        
					   	layer.close(index);
					   	$("#tablecontent").html(html); 
					   	self.loadEvents();
					   self.checked();
					   self.checkedd();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			  this.checked=function(){
					
					$(".rest").each(function(i,o){
							
							var rest=$(o).parent().data("status");
							if($(o).val()==rest){
							
								$(o).attr('checked', 'checked');;
							}
					})
					
				}
			
			  this.checkedd=function(){
					
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
			$('.updateremake').on('click',function(){
				if($(this).text() == "编辑"){
					$(this).text("保存")
					
					$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

			            $(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
			        });
				}else{
						$(this).text("编辑")
					$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

				            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

				       
				                $(this).html(obj_text.val()); 
								
						});
						
						var postData = {
								type:5,
								id:$(this).data('id'),
								price:$(this).parent().parent('tr').find(".workPrice").text(),
						}
						var index;
						
						$.ajax({
							url:"${ctx}/system/user/update",
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
			
			
			
			
			
			$('.rest').on('click',function(){
				var  del=$(this);
				var id = $(this).parent().data('id');
				var rest = $(this).val();
				
			    	  $.ajax({
							url:"${ctx}/system/user/update",
							data:{
								id:id,
								status:rest,
								},
							type:"POST",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							success:function(result){
								//选择1
								if(rest>0){
									del.parent().find(".rest").eq(0).prop("checked", false);
										
								}else{
									del.parent().find(".rest").eq(1).prop("checked", false);	
								}
								layer.msg("操作成功！", {icon: 1});
								layer.close(index);
							
						
								
							},
							error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
			    	  
			 
	
			});
			
			//遍历组名信息
			var data={
					page:1,
			  		size:100,	
			  		type:5,

			} 
			var index;
		    var html = '';
		    $.ajax({
			      url:"${ctx}/production/getGroup",
			      data:data,
			      type:"GET",
			     
	      		  success: function (result) {
	      			 $(result.data).each(function(i,o){
	      				html +='<option value="'+o.id+'">'+o.name+'</option>'
	      			}); 
			       var htmlto='<select class="form-control  selectgroupChange"><option value="">去除分组</option>'+html+'</select>'
				   	$(".groupChange").html(htmlto); 
				   	self.chang();
				   	self.selected();
			      },error:function(){
						layer.msg("加载失败！", {icon: 2});
						layer.close(index);
				  }
			  });
	  }
		this.chang=function(){
			$('.selectgroupChange').change(function(){
				var that=$(this);
				var data={
						userIds:that.parent().data("id"),
						groupId:that.val(),
					}
				var _data={
						page:1,
				  		size:10,	
				} 
				$.ajax({
					url:"${ctx}/production/userGroup",
					data:data,
					type:"POST",
					success:function(result){
						if(0==result.code){
							layer.msg("分组成功！", {icon: 1});
							
						}else{
							layer.msg("分组失败", {icon: 2});			
						}
						
					},error:function(){
						layer.msg("操作失败！", {icon: 2});
					}
				})
				
				
			})
		}
		this.selected=function(){
			
			$('.selectgroupChange').each(function(i,o){
				var id=$(o).parent().data("groupid");
				$(o).val(id);
			})
			
		}
		this.events = function(){
			$('.position').on('click',function(){
				var  that=$(this);
				var arr=new Array()
				$(this).parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
					arr.push($(this).val());   
				});
				if(arr.length<=0){
					return layer.msg("必须选择一个用户", {icon: 2});
				}
				if($("#workPrice").val()==""){
					return layer.msg("预计收入不能为空", {icon: 2});
				}
				if($("#endTime").val()==""){
					return layer.msg("时间不能为空", {icon: 2});
				}
				var postData={
						usersId:arr,
						workPrice:$("#workPrice").val(),
						allotTime:$("#endTime").val(),
				}
				
				$.ajax({
					url:"${ctx}/finance/updateAllAttendance",
					data:postData,
		            traditional: true,
					type:"GET",
					beforeSend:function(){
						index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							});
					},
					
					success:function(result){
						if(0==result.code || 2==result.code){
							layer.msg(result.massage, {icon: 1});
						}else{
							layer.msg("修改失败", {icon: 2});
						}
						layer.close(index);
					},error:function(){
						layer.msg("操作失败！", {icon: 2});
						layer.close(index);
					}
				});  
			   })
			
			
			$('.attendance').on('click',function(){
				  var  that=$(this);
				  var arr=new Array()//员工id
				  var time=new Array()//考勤时间
					$(this).parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
						time.push($(this).parent().parent().siblings().find(".work").val());
						arr.push($(this).val());   
					});
				  if(arr.length<=0){
						return layer.msg("至少选择一个！", {icon: 2});
					}
					var data={
							type:5,
							usersId:arr,
							workTimes:time,
							allotTime:$("#startTime").val(),
					}
					$.ajax({
						url:"${ctx}/finance/addAttendance",
						data:data,
			            traditional: true,
						type:"POST",
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
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					}); 
			  })
			
			
			//遍历人名组别
			var htmlth="";
			var data = {
		  			type:5,
		  	}
		    $.ajax({
			      url:"${ctx}/production/getGroup",
			      data:data,
			      type:"GET",
	      		  success: function (result) {
	      			  $(result.data).each(function(k,j){
	      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
	      			  });  
	      			 $('#groupp').html("<select class='form-control selectcomplete'><option value="+""+">请选择&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>"+htmlth+"</select>") 
			      }
			  });
			
			
			$('.searchtask').on('click',function(){
				var data = {
			  			page:1,
			  			size:10,
			  			userName:$('#name').val(),
			  			groupId:$('.selectcomplete').val()
			  	}
				
	            self.loadPagination(data);
			});
			
	  }
   	}
	var login = new Login();
	  login.init();
})
    </script>



</body>
</html>