<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员汇总</title>
</head>
<body>
    <section id="main-wrapper" class="theme-default">
		<%@include file="../../decorator/leftbar.jsp"%> 
            <section id="main-content" class="animated fadeInUp">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">人员信息</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                            <div class="row" style="height: 30px; margin:15px 0 10px">
			<div class="col-xs-8 col-sm-8  col-md-8">
				<form class="form-search" >
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-7">
							<div class="input-group"> 
								<table><tr>
								<td>员工姓名:</td><td><input type="text" name="name" id="name" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp</td>
								<td>小组查询:</td><td id="groupp"></td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d  searchtask">
										查&nbsp找
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
              <h1 class="page-header"></h1>  
              	<table><tr>           
                        <td><button type="button" class="btn btn-info btn-square btn-sm btn-3d attendance">一键添加考勤</button>&nbsp&nbsp</td> 
                        <td><input id="startTime" placeholder="请输入考勤日期" class="form-control laydate-icon"
             				onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
             			</td>
             			<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
             			<td><button type="button" class="btn btn-info btn-square btn-sm btn-3d position">修改到岗预计小时收入</button>&nbsp&nbsp</td> 
             			<td><input id="endTime" placeholder="请输入时间" class="form-control laydate-icon"
             				onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
             			</td>
             			<td>&nbsp&nbsp</td>
             			<td><input type="text" name="name" id="workPrice" class="form-control search-query" placeholder="预计收入" /></td>  
                 </tr></table>        
                            
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <thead>
                                        <tr style="font-size: 14px">
                                        	<th class="center">
											<label> 
											<input type="checkbox" class="ace checks" /> 
											<span class="lbl"></span>
											</label>
											</th>
                                            <th  class="text-center">序号</th>
                                            <th class="text-center">姓名</th>
                                            <th class="text-center">部门</th>
                                            <th class="text-center">工作时长</th>
                                            <th class="text-center hidden overtimet ">加班时间</th>
                                            <th class="text-center">缺勤时间</th>
                                            <th class="text-center">当月预计收入</th>
                                            <th class="text-center">工作状态</th>
                                            <th class="text-center">员工分组</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent" style="font-size: 14px">
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
 
    <!--Global JS-->
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
		      				+'<td class="hidden batch">'+o.id+'</td>'
		      				+'<td class="text-center ">'+order+'</td>'
		      				+'<td class="text-center ">'+o.userName+'</td>'
		      				+'<td class="text-center ">'+k+'</td>'
		      				+'<td class="text-center "><input class="work"  data-id="'+o.id+'"></input></td>'
		      				+'<td class="text-center hidden overtimets"><input class="workth"></input></td>'
		      				+'<td class="text-center "><input class="workto"></input></td>'
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
					   	if($('.selectcomplete').find("option:selected").attr("emoney")==120 || $('.selectcomplete').find("option:selected").attr("emoney")==113 || $('.selectcomplete').find("option:selected").attr("emoney")==116){
							$('.overtimets').removeClass("hidden");
							$('.overtimet').removeClass("hidden");
						}
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
			 $('.work').on('click',function(){
				 var ids=$(this).data("id");
				 $(".batch").each(function(i,o){
						var a=$(o).text();
						if(a==ids){
							$(o).parent().addClass("danger");
							$(o).parent().siblings().removeClass("danger");
						}
					})
			 }) 
				
			
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
								if(0==result.code || 2==result.code){
								layer.msg("修改成功！", {icon: 1});
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
			  		type:3,

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
						if(0==result.code){
							layer.msg("修改成功！", {icon: 1});
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
			
			
			$('.attendance').on('click',function(){
				  var  that=$(this);
				  var arr=new Array()//员工id
				  var time=new Array()//考勤时间
				  var overtime=new Array()
				  var dutyTime=new Array()
					$(this).parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
						time.push($(this).parent().parent().siblings().find(".work").val());
						overtime.push($(this).parent().parent().siblings().find(".workth").val());
						dutyTime.push($(this).parent().parent().siblings().find(".workto").val());
						arr.push($(this).val());   
					});
				  if(time==""){
						return layer.msg("工作时长不能为空！", {icon: 2});
					}
				 
				  if(arr.length<=0){
						return layer.msg("至少选择一个！", {icon: 2});
					}
				  
					var data={
							type:3,
							usersId:arr,
							workTimes:time,
							allotTime:$("#startTime").val(),
							overtimes:overtime,
							dutyTimes:dutyTime,
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
		  			type:3,
		  	}
		    $.ajax({
			      url:"${ctx}/production/getGroup",
			      data:data,
			      type:"GET",
	      		  success: function (result) {
	      			  $(result.data).each(function(k,j){
	      				htmlth +='<option value="'+j.id+'" emoney="'+j.kindWork.id+'">'+j.name+'</option>'
	      			  });  
	      			 $('#groupp').html("<select class='form-control selectcomplete'><option value="+""+">请选择&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</option>"+htmlth+"</select>") 
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
				 if($('.selectcomplete').find("option:selected").attr("emoney")==120 || $('.selectcomplete').find("option:selected").attr("emoney")==116 || $('.selectcomplete').find("option:selected").attr("emoney")==113){
					$('.overtimet').removeClass("hidden");
				}else{
					$('.overtimet').addClass("hidden");
				} 
			});
			
	  }
   	}
	var login = new Login();
	  login.init();
})
    </script>
    
    
        
</body>
</html>