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
		<%@include file="../decorator/leftbar.jsp"%> 
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
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-default btn-square btn-sm btn-3d  searchtask">
										查&nbsp找
									</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
									<button type="button" class="btn btn-success  btn-sm btn-3d addDict">
									新增员工
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
                                            <th>序号</th>
                                            <th>编号</th>
                                            <th>姓名</th>
                                            <th>手机号</th>
                                            <th>身份证号</th>
                                            <th>部门</th>
                                            <th>职位</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent">
                                    </tbody>
                                </table>
                                 <div id="pager">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </section>
        
        
        
        
        
        
         <!--隐藏框 修改开始  -->
 <!-- <div id="addbatch" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addbatchForm">
				<div class="form-group">
                                        <label class="col-sm-3 control-label">姓名:</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="proName" class="form-control">
                                        </div>
                 </div>
                 <div class="form-group">
                                        <label class="col-sm-3 control-label">部门:</label>
                                        <div class="col-sm-6 department">
                                            
                                        </div>
                 </div>
                
                <div class="form-group">
                                        <label class="col-sm-3 control-label">职位:</label>
                                        <div class="col-sm-6 position">
                                            
                                        </div>
                 </div>
				</form>
</div>
</div> -->       
    <!--隐藏框 修改结束  -->
        
          <!--隐藏框 新增  -->
   <div id="addDictDivType" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeForm">
					<div class="row col-xs-12  col-sm-12  col-md-12 ">
		
                 
						<div class="form-group">
                           <label class="col-sm-2 col-md-2 control-label">员工姓名:</label>
                              <div class="col-sm-2 col-md-2">
                                  <input type="text" class="form-control username">
                              </div>
                               <label class="col-sm-3 control-label">员工编号:</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control number">
                                      </div>
                            
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">名族:</label>
                              <div class="col-sm-2 working">
                              <select class="form-control nation"><option value="汉">汉</option><option value="少数名族">少数名族</option></select>
                              </div>
                              <label class="col-sm-3 control-label">手机号:</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control phone">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">邮箱:</label>
                              <div class="col-sm-2 working">
                              <input type="text" class="form-control email">
                              </div>
                              <label class="col-sm-3 control-label">性别:</label>
                                 <div class="col-sm-2">
                                  <select class="form-control gender"><option value="1">男</option><option value="2">女</option></select>
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">生日:</label>
                              <div class="col-sm-2 working">
                              <input id="birthDate" placeholder="请输入时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#birthDate', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                              </div>
                              <label class="col-sm-3 control-label">身份证号:</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control idCard">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">户籍地址:</label>
                              <div class="col-sm-2 working">
                              <input type="text" class="form-control permanentAddress">
                              </div>
                              <label class="col-sm-3 control-label">现居住地址:</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control livingAddress">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">婚姻状况:</label>
                              <div class="col-sm-2 working">
                               <select class="form-control marriage"><option value="已婚">已婚</option><option value="未婚">未婚</option></select>
                              </div>
                              <label class="col-sm-3 control-label">生育状况:</label>
                                 <div class="col-sm-2">
                                          <select class="form-control procreate"><option value="已育">已育</option><option value="未育">未育</option></select>
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">学历:</label>
                              <div class="col-sm-2 working">
                              <select class="form-control education"><option value="本科">本科</option><option value="大专">大专</option><option value="高中">高中</option><option value="初中及以下">初中及以下</option></select>
                              </div>
                              <label class="col-sm-3 control-label">毕业学校:</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control school">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">专业:</label>
                              <div class="col-sm-2 working">
                              <input type="text" class="form-control major">
                              </div>
                              <label class="col-sm-3 control-label">联系人:</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control contacts">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">联系方式:</label>
                              <div class="col-sm-2 working">
                              <input type="text" class="form-control information">
                              </div>
                              <label class="col-sm-3 control-label">入职时间:</label>
                                 <div class="col-sm-2">
                                          <input id="entry" placeholder="请输入时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#entry', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">预计转正时间:</label>
                              <div class="col-sm-2 working">
                             <input id="estimate" placeholder="请输入时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#estimate', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                              </div>
                              <label class="col-sm-3 control-label">实际转正时间:</label>
                                 <div class="col-sm-2">
                                          <input id="actua" placeholder="请输入时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#actua', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">社保缴纳时间:</label>
                              <div class="col-sm-2 working">
                              <input id="socialSecurity" placeholder="请输入时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#socialSecurity', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                              </div>
                              <label class="col-sm-3 control-label">协议</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control agreement">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">承诺书:</label>
                              <div class="col-sm-2 working">
                              <input type="text" class="form-control promise">
                              </div>
                              <label class="col-sm-3 control-label">合同</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control contract">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">银行卡1:</label>
                              <div class="col-sm-2 working">
                              <input type="text" class="form-control bankCard1">
                              </div>
                              <label class="col-sm-3 control-label">银行卡2</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control bankCard2">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">合同签订开始时间:</label>
                              <div class="col-sm-2 working">
                              <input id="contractDate" placeholder="请输入时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#contractDate', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                              </div>
                              <label class="col-sm-3 control-label">合同签订次数</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control frequency">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">工作状态:</label>
                              <div class="col-sm-2 working">
                               <select class="form-control quit"><option value="在职">在职</option><option value="离职">离职</option></select>
                              </div>
                              <label class="col-sm-3 control-label">离职时间</label>
                                 <div class="col-sm-2">
                                          <input id="quitDate" placeholder="请输入时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#quitDate', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                      </div>
                    	</div>
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">离职理由:</label>
                              <div class="col-sm-2 working">
                              <input type="text" class="form-control reason">
                              </div>
                              <label class="col-sm-3 control-label">简介</label>
                                 <div class="col-sm-2">
                                          <input type="text" class="form-control remark">
                                      </div>
                    	</div>
                    	<div class="form-group">
                                        <label class="col-sm-3 control-label">部门:</label>
                                        <div class="col-sm-4 department">
                                            
                                        </div>
                 </div>
                
                <div class="form-group">
                                        <label class="col-sm-3 control-label">职位:</label>
                                        <div class="col-sm-4 position">
                                            
                                        </div>
                 </div>
                 </div>

				</form>
</div>     
        
        
        
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
			this.getCount = function(){
		  		return _count;
		  	}
		  	this.setCount = function(count){
		  		_count=count;
		  	}
			 var data={
						page:1,
				  		size:13,	
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
		      				 var z;
		      				 if(o.orgName==null){
		      					 z=""
		      				 }else{
		      					 z=o.orgName.id
		      				 }
		      				 var u;
		      				if(o.position==null){
		      					 u=""
		      				 }else{
		      					 u=o.position.id
		      				 }
		      				html +='<tr>'
		      				+'<td class="edit price">'+order+'</td>'
		      				+'<td class="edit price">'+o.number+'</td>'
		      				+'<td class="edit price">'+o.userName+'</td>'
		      				+'<td class="edit price">'+o.phone+'</td>'
		      				+'<td class="edit price">'+o.idCard+'</td>'
		      				+'<td class="edit price">'+k+'</td>'
		      				+'<td class="edit price">'+l+'</td>'
							+'<td><button class="btn btn-xs btn-success btn-trans addbatch" data-id='+o.id+' data-name='+o.userName+' data-nameid='+z+' data-postid='+u+'>修改</button></td></tr>'
							
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
									  		userName:$('#name').val(),
								  	}
						            self.loadPagination(_data);
							     }
					      }
					    });
				        
					   	layer.close(index);
					   	$("#tablecontent").html(html); 
					   	self.loadEvents();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			  this.loadEvents = function(){
				/*修改 */
					$('.addbatch').on('click',function(){
						var _index
						var index
						var postData   
						var postId=$(this).data('postid');
						var nameId=$(this).data('nameid');
						var dicDiv=$('#addDictDivType');
						var userName=$(this).data('name');
						var bacthDepartmentPrice=$(this).parent().parent().find('.departmentPrice').text();
						var bacthHairPrice=$(this).parent().parent().find('.hairPrice').text();
						$('#proName').val(userName);
						var id=$(this).data('id');
						var a="";
						var c="";
						//遍历工序类型
				    var indextwo;
				    var htmltwo = '';
				    var htmlth = '';
				    var htmlfr = '';
				    var html = '';
					    var getdata={type:"orgName",}
		      			$.ajax({
						      url:"${ctx}/basedata/list",
						      data:getdata,
						      type:"GET",
						      beforeSend:function(){
						    	  indextwo = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			  $(result.data).each(function(k,j){
				      				htmlfr +='<option value="'+j.id+'">'+j.name+'</option>'
				      			  });
				      			var htmlth='<select class="form-control  selectgroupChange"><option value="">去除分组</option>'+htmlfr+'</select>'
				      			
				      			$(".department").html(htmlth); 
				      			$('.selectgroupChange').each(function(i,o){
				    				var id=nameId;
				    				$(o).val(id);
				    			})
				      			layer.close(indextwo);
				      			//查询工序
				      			$(".selectgroupChange").change(function(){
				      			  var htmltwo = '';
				      				c=$(this).val();
				      				var data={
				    						id:c,
				    					  }
								     $.ajax({
									      url:"${ctx}/basedata/children",
									      data:data,
									      type:"GET",
									      beforeSend:function(){
									    	  indextwo = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											  });
										  }, 
							      			 
							      		  success: function (result) {
							      			
							      			  $(result.data).each(function(i,o){
							      				htmltwo +='<option value="'+o.id+'">'+o.name+'</option>'
							      			});  
							      			var html='<select class="form-control  selectChange">'+htmltwo+'</select>'
							      			$(".position").html(html);
							      			
							      			layer.close(indextwo);
									      },error:function(){
												layer.msg("加载失败！", {icon: 2});
												layer.close(indextwo);
										  }
									  }); 
				      			})
				      			
				      			
						      }
						  });
						
						
					    
					  var data={
							id:id		
						}
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
				      				 var z;
				      				 if(o.orgName==null){
				      					 z=""
				      				 }else{
				      					 z=o.orgName.id
				      				 }
				      				 var u;
				      				if(o.position==null){
				      					 u=""
				      				 }else{
				      					 u=o.position.id
				      				 }
				      				$('.userName').val(o.userName);
				      				$('.number').val(o.number);
				      				$('.phone').val(o.phone);
				      				$('.email').val(o.email);
				      				$('#birthDate').val(o.birthDate);
				      				$('.idCard').val(o.idCard);
				      				$('.permanentAddress').val(o.permanentAddress);
				      				$('.livingAddress').val(o.livingAddress);
				      				$('.school').val(o.school);
				      				$('.major').val(o.major);
				      				$('.contacts').val(o.contacts);
				      				$('.information').val(o.information);
				      				$('#entry').val(o.entry);
				      				$('#estimate').val(o.estimate);
				      				$('#actua').val(o.actua);
				      				$('#socialSecurity').val(o.socialSecurity);
				      				$('.bankCard1').val(o.bankCard1);
				      				$('.bankCard2').val(o.bankCard2);
				      				$('.agreement').val(o.agreement);
				      				$('.promise').val(o.promise);
				      				$('.contract').val(o.contract);
				      				$('#contractDate').val(o.contractDate);
				      				$('.frequency').val(o.frequency);
				      				$('#quitDate').val(o.quitDate);
				      				$('.reason').val(o.reason);
				      				$('.train').val(o.train);
				      				$('.remark').val(o.remark);
				      				var html='<input class="form-control" value="'+l+'" />'
					      			$(".position").html(html);
				      				$('.nation').each(function(j,k){
										var id=o.nation;
										$(k).val(id);
									})
									$('.gender').each(function(j,k){
										var id=o.gender;
										$(k).val(id);
									})
									$('.marriage').each(function(j,k){
										var id=o.marriage;
										$(k).val(id);
									})
									$('.procreate').each(function(j,k){
										var id=o.procreate;
										$(k).val(id);
									})
									$('.education').each(function(j,k){
										var id=o.education;
										$(k).val(id);
									})
									$('.quit').each(function(j,k){
										var id=o.quit;
										$(k).val(id);
									})
				      			}); 
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
					    
						_index = layer.open({
							  type: 1,
							  skin: 'layui-layer-rim', //加上边框
							  area: ['60%', '70%'], 
							  btnAlign: 'c',//宽高
							  maxmin: true,
							  title:userName,
							  content: dicDiv,
							  btn: ['确定', '取消'],
							  yes:function(index, layero){
								  postData={
										  id:id,
										  userName:$('.userName').val(),
											number:$('.number').val(),
											nation:$('.nation').val(),
											phone:$('.phone').val(),
											email:$('.email').val(),
											gender:$('.gender').val(),
											birthDate:$('#birthDate').val(),
											idCard:$('.idCard').val(),
											permanentAddress:$('.permanentAddress').val(),
											livingAddress:$('.livingAddress').val(),
											marriage:$('.marriage').val(),
											procreate:$('.procreate').val(),
											education:$('.education').val(),
											school:$('.school').val(),
											major:$('.major').val(),
											contacts:$('.contacts').val(),
											information:$('.information').val(),
											entry:$('#entry').val(),
											estimate:$('#estimate').val(),
											actua:$('#actua').val(),
											socialSecurity:$('#socialSecurity').val(),
											bankCard1:$('.bankCard1').val(),
											bankCard2:$('.bankCard2').val(),
											agreement:$('.agreement').val(),
											promise:$('.promise').val(),
											contract:$('.contract').val(),
											contractDate:$('#contractDate').val(),
											frequency:$('.frequency').val(),
											quit:$('.quit').val(),
											quitDate:$('#quitDate').val(),
											reason:$('.reason').val(),
											train:$('.train').val(),
											remark:$('.remark').val(),
											orgNameId:$('.selectgroupChange').val(),
											positionId:$('.selectChange').val()
								  }
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
												 
												var data = {
											  			page:self.getCount(),
											  			size:13,
											  			userName:$('#name').val(),
											  	}
												layer.close(index);
												self.loadPagination(data);
											}else{
												layer.msg("修改失败", {icon: 2});
											}
											
											layer.close(index);
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									}); 
								},
							  end:function(){
								  /*  $('.addbatchForm')[0].reset();  */
								  /*  $("#addDictDivType").hide(); */
								  layer.close(index);
							  }
						});
					})
			  }
			  
			this.events = function(){
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			userName:$('#name').val(),
				  	}
					
		            self.loadPagination(data);
				});
				
				/* 新增员工 */
				$('.addDict').on('click',function(){
					
					 var indextwo;
					    var htmltwo = '';
					    var htmlth = '';
					    var htmlfr = '';
					    var html = '';
						    var getdata={type:"orgName",}
			      			$.ajax({
							      url:"${ctx}/basedata/list",
							      data:getdata,
							      type:"GET",
							      beforeSend:function(){
							    	  indextwo = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									  });
								  }, 
					      		  success: function (result) {
					      			  $(result.data).each(function(k,j){
					      				htmlfr +='<option value="'+j.id+'">'+j.name+'</option>'
					      			  });
					      			var htmlth='<select class="form-control  selectgroupChange"><option value="">请选择</option>'+htmlfr+'</select>'
					      			
					      			$(".department").html(htmlth); 
					      			/* $('.selectgroupChange').each(function(i,o){
					    				var id=nameId;
					    				$(o).val(id);
					    			}) */
					      			layer.close(indextwo);
					      			//查询工序
					      			$(".selectgroupChange").change(function(){
					      			  var htmltwo = '';
					      				c=$(this).val();
					      				var data={
					    						id:c,
					    					  }
									     $.ajax({
										      url:"${ctx}/basedata/children",
										      data:data,
										      type:"GET",
										      beforeSend:function(){
										    	  indextwo = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												  });
											  }, 
								      			 
								      		  success: function (result) {
								      			
								      			  $(result.data).each(function(i,o){
								      				htmltwo +='<option value="'+o.id+'">'+o.name+'</option>'
								      			});  
								      			var html='<select class="form-control  selectChange">'+htmltwo+'</select>'
								      			$(".position").html(html);
								      		
								      			layer.close(indextwo);
										      },error:function(){
													layer.msg("加载失败！", {icon: 2});
													layer.close(indextwo);
											  }
										  }); 
					      			})
					      			
					      			
							      }
							  });
					
					var dicDiv=$('#addDictDivType');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '70%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"信息",
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var values=new Array()
							  var numberr=new Array()
								$(".checkWork:checked").each(function() {   
									values.push($(this).val());
									numberr.push($(this).data('residualnumber'));
								}); 
								var postData = {
										userName:$('.userName').val(),
										number:$('.number').val(),
										nation:$('.nation').val(),
										phone:$('.phone').val(),
										email:$('.email').val(),
										gender:$('.gender').val(),
										birthDate:$('#birthDate').val(),
										idCard:$('.idCard').val(),
										permanentAddress:$('.permanentAddress').val(),
										livingAddress:$('.livingAddress').val(),
										marriage:$('.marriage').val(),
										procreate:$('.procreate').val(),
										education:$('.education').val(),
										school:$('.school').val(),
										major:$('.major').val(),
										contacts:$('.contacts').val(),
										information:$('.information').val(),
										entry:$('#entry').val(),
										estimate:$('#estimate').val(),
										actua:$('#actua').val(),
										socialSecurity:$('#socialSecurity').val(),
										bankCard1:$('.bankCard1').val(),
										bankCard2:$('.bankCard2').val(),
										agreement:$('.agreement').val(),
										promise:$('.promise').val(),
										contract:$('.contract').val(),
										contractDate:$('#contractDate').val(),
										frequency:$('.frequency').val(),
										quit:$('.quit').val(),
										quitDate:$('#quitDate').val(),
										reason:$('.reason').val(),
										train:$('.train').val(),
										remark:$('.remark').val(),
										orgNameId:$('.selectgroupChange').val(),
										positionId:$('.selectChange').val()
								}
							    $.ajax({
									url:"${ctx}/system/user/add",
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
										  /* $('.addDictDivTypeForm')[0].reset(); */  
										  var htmlfv="";
											layer.msg("添加成功！", {icon: 1});
											var data = {
										  			page:1,
										  			size:13,
										  	}
											self.loadPagination(data);
											
										}else{
											layer.msg("添加失败", {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});  
							},
						   end:function(){
							  $('.addDictDivTypeForm')[0].reset(); 
							  $("#addDictDivType").hide();
							   $('.checkworking').text(""); 
							   /* var data={
										page:self.getCount(),
										size:13,
							  			type:3,
							  			name:$('#name').val(),
							  			bacthNumber:$('#number').val(),
							  			orderTimeBegin:$("#startTime").val(),
							  			orderTimeEnd:$("#endTime").val(), 
							  			status:$("#selectstate").val(),
							  			flag:0,
							  			statusTime:$("#startTime").val(),
								} 
							   self.loadPagination(data); */
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