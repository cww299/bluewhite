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



</head>
<body>

	
	<div class="panel panel-default">
		<div class="panel-heading">
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
										<td><input type="text" id="name"
											class="form-control name" /></td>
										<td>&nbsp&nbsp</td>
										<td>在离职:</td>
										<td><select class="form-control" id="groupp"><option
													value="">请选择</option>
												<option value="0">在职</option>
												<option value="1">离职</option></select></td>
										<td>&nbsp&nbsp</td>
										<td>部门:</td>
										<td id="orgName"></td>
										<td>&nbsp&nbsp</td>
										<td>性别:</td>
										<td><select class="form-control" id="gender"><option
													value="">请选择</option>
												<option value="0">男</option>
												<option value="1">女</option></select></td>
										<td>&nbsp&nbsp</td>
										<td>退休返聘:</td>
										<td><select class="form-control" id="retire"><option
													value="">否</option>
												<option value="0">是</option></select></td>
										<td>&nbsp&nbsp</td>
										<td>合同:</td>
										<td><select class="form-control" id="commitment"><option
													value="">请选择</option>
												<option value="0">未签</option>
												<option value="1">已签</option>
												<option value="2">续签</option></select></td>
									</tr>
									<tr>
										<td><div style="height: 10px"></div></td>
									</tr>
									<tr>
										<td>位置编号:</td>
										<td><input type="text" id="number"
											class="form-control" /></td>
										<td>&nbsp&nbsp</td>
										<td>时间查询:</td>
										<td><select class="form-control" id="timesss"><option
													value="">请选择</option>
												<option value="entry">入职时间</option>
												<option value="actua">实际转正时间</option>
												<option value="estimate">预计转正时间</option></select></td>
										<td>&nbsp&nbsp</td>
										<td>开始:</td>
										<td><input id="startTime" placeholder="请输入开始时间"
											class="form-control laydate-icon"
											onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
										</td>
										<td>&nbsp&nbsp</td>
										<td>结束:</td>
										<td><input id="endTime" placeholder="请输入结束时间"
											class="form-control laydate-icon"
											onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
										</td>
										<td>&nbsp&nbsp</td>
										<td>保险详情:</td>
										<td><select class="form-control" id="safe"><option
													value="">请选择</option>
												<option value="0">未缴</option>
												<option value="1">已缴</option></select></td>
										<td>&nbsp&nbsp</td>
										<td>承诺书:</td>
										<td><select class="form-control" id="promise"><option
													value="">请选择</option>
												<option value="0">未签</option>
												<option value="1">已签</option></select></td>
									</tr>
									<tr>
										<td><div style="height: 10px"></div></td>
									</tr>
									<tr>
										<td>所属银行:</td>
										<td><input type="text" id="bankCardtw"
											class="form-control" /></td>
										<td>&nbsp&nbsp</td>
										<td>学历查询:</td>
										<td><select id="education2" class="form-control"><option
													value="">请选择</option>
												<option value="本科">本科</option>
												<option value="大专">大专</option>
												<option value="高中">高中</option>
												<option value="初中及以下">初中及以下</option></select></td>
									</tr>
								</table>
								<span class="input-group-btn">
									<button type="button"
										class="btn btn-default btn-square btn-sm btn-3d  searchtask">
										查&nbsp找</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td> <span class="input-group-btn">
									<button type="button"
										class="btn btn-success  btn-sm btn-3d addDict">
										新增员工</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td> <span class="input-group-btn">
									<button type="button"
										class="btn btn-success  btn-sm btn-3d savemode"
										data-toggle="modal" data-target="#myModal">员工提示
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
						<th class="text-center">位置编号</th>
						<th class="text-center">姓名</th>
						<th class="text-center">手机号</th>
						<th class="text-center">年龄</th>
						<th class="text-center">合同</th>
						<th class="text-center">承诺书</th>
						<th class="text-center">保险</th>
						<th class="text-center">入职时间</th>
						<th class="text-center">预计转正时间</th>
						<th class="text-center">合同到期时间</th>
						<th class="text-center">部门</th>
						<th class="text-center">是否在职</th>
						<th class="text-center">操作</th>
					</tr>
				</thead>
				<tbody id="tablecontent">
				</tbody>
				<thead>
					<tr>
						<td class="text-center">合计人数</td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center"></td>
						<td class="text-center" id="total"></td>
					</tr>
				</thead>
			</table>
			<div id="pager" class="pull-right"></div>
		</div>
	</div>



	<!--隐藏框 新增  -->
	<div id="addDictDivType" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>

			<!-- <div><table><tr>
								<td>图片类型:</td><td><select class="form-control" id="selecttype"><option value="introduce">产品</option><option value="details">产品描述</option><option value="bursting">爆款图片</option></select></td>
								</tr></table></div> -->
			<div style="padding-left: 450px">
				<div class="panel-body">
					<div style="font-size: 16px;">
						<b>员工照片:</b>
					</div>
					<form action="#" class="dropzone" style="widows: 150px"
						id="my-awesome-dropzone" enctype="multipart/form-data"></form>

				</div>
			</div>
			<form class="form-horizontal addDictDivTypeForm">
				<div class="row col-xs-12  col-sm-12  col-md-12 ">


					<div class="form-group">
						<label class="col-sm-2 col-md-2 control-label">员工姓名:</label>
						<div class="col-sm-2 col-md-2">
							<input type="text" class="form-control username">
						</div>
						<label class="col-sm-2 control-label">员工编号:</label>
						<div class="col-sm-2">
							<input type="text" disabled="disabled"
								class="form-control number">
						</div>
						<label class="col-sm-2 control-label">户籍地址:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control permanentAddress">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">民族:</label>
						<div class="col-sm-2 working">
							<select class="form-control nation"><option value="汉">汉</option>
								<option value="少数民族">少数民族</option></select>
						</div>
						<label class="col-sm-2 control-label">手机号:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control phone">
						</div>
						<label class="col-sm-2 control-label">现居住地址:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control livingAddress">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">邮箱:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control email">
						</div>
						<label class="col-sm-2 control-label">性别:</label>
						<div class="col-sm-2">
							<select class="form-control gender" disabled="disabled"><option
									value="0">男</option>
								<option value="1">女</option></select>
						</div>
						<label class="col-sm-2 control-label">出生日期:</label>
						<div class="col-sm-2 working">
							<input id="birthDate" disabled="disabled" placeholder="请输入时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#birthDate', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">婚姻状况:</label>
						<div class="col-sm-2 working">
							<select class="form-control marriage"><option value="0">已婚</option>
								<option value="1">未婚</option></select>
						</div>
						<label class="col-sm-2 control-label">生育状况:</label>
						<div class="col-sm-2">
							<select class="form-control procreate"><option value="0">已育</option>
								<option value="1">未育</option></select>
						</div>
						<label class="col-sm-2 control-label">身份证号:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control idCard">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">学历:</label>
						<div class="col-sm-2 working">
							<select class="form-control education"><option
									value="本科">本科</option>
								<option value="大专">大专</option>
								<option value="高中">高中</option>
								<option value="初中及以下">初中及以下</option></select>
						</div>
						<label class="col-sm-2 control-label">毕业学校:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control school">
						</div>
						<label class="col-sm-2 control-label">专业:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control major">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">紧急联系方式:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control information">
						</div>
						<label class="col-sm-2 control-label">紧急联系人:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control contacts">
						</div>
						<label class="col-sm-2 control-label">入职时间:</label>
						<div class="col-sm-2">
							<input id="entry" placeholder="请输入时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#entry', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">预计转正时间:</label>
						<div class="col-sm-2 working">
							<input id="estimate" placeholder="请输入时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#estimate', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
						<label class="col-sm-2 control-label">实际转正时间:</label>
						<div class="col-sm-2">
							<input id="actua" placeholder="请输入时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#actua', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
						<label class="col-sm-2 control-label">社保缴纳时间:</label>
						<div class="col-sm-2 working">
							<input id="socialSecurity" placeholder="请输入时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#socialSecurity', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">承诺书:</label>
						<div class="col-sm-2 working">
							<select class="form-control promise"><option value="0">未签</option>
								<option value="1">已签</option></select>
						</div>
						<label class="col-sm-2 control-label">合同:</label>
						<div class="col-sm-2">
							<select class="form-control commitment"><option
									value="0">未签</option>
								<option value="1">已签</option>
								<option value="2">续签</option></select>
						</div>
						<label class="col-sm-2 control-label">协议:</label>
						<div class="col-sm-2 agreementtw">
							<!-- <input type="text" class="form-control agreement"> -->
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">银行卡1:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control bankCard1">
						</div>
						<label class="col-sm-2 control-label">所属银行:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control  bankCardtw"
								disabled="disabled">
						</div>
						<label class="col-sm-2 control-label">保险情况:</label>
						<div class="col-sm-2 working">
							<select class="form-control safe"><option value="0">未缴</option>
								<option value="1">已缴</option></select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">合同签订开始时间:</label>
						<div class="col-sm-2 working">
							<input id="contractDate" placeholder="请输入时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#contractDate', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
						<label class="col-sm-2 control-label">合同签订次数:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control frequency">
						</div>
						<label class="col-sm-2 control-label">签订单位:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control company">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">工作状态:</label>
						<div class="col-sm-2 working">
							<select class="form-control quit"><option value="0">在职</option>
								<option value="1">离职</option></select>
						</div>
						<label class="col-sm-2 control-label">离职时间:</label>
						<div class="col-sm-2">
							<input id="quitDate" placeholder="请输入时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#quitDate', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
						<label class="col-sm-2 control-label">身份证到期时间:</label>
						<div class="col-sm-2">
							<input id="idCardEnd" placeholder="请输入时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#idCardEnd', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">离职理由:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control reason">
						</div>
						<label class="col-sm-2 control-label">合同:</label>
						<div class="col-sm-2 remarktww">
							<!-- <input type="text" class="form-control remark"> -->
						</div>
						<label class="col-sm-2 control-label">合同到期时间:</label>
						<div class="col-sm-2">
							<input id="contractDateEnd" placeholder="请输入时间"
								class="form-control laydate-icon"
								onClick="laydate({elem: '#contractDateEnd', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">归属车间:</label>
						<div class="col-sm-4">
							<select class="form-control" id="type4"><option value="">请选择</option>
								<option value="1">质检</option>
								<option value="2">包装</option>
								<option value="3">针工</option>
								<option value="4">机工</option>
								<option value="5">8号仓库</option></select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">部门:</label>
						<div class="col-sm-4 department"></div>
					</div>

					<div class="form-group">
						<label class="col-sm-4 control-label">职位:</label>
						<div class="col-sm-4 position"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">是否为销售人员:</label>
						<div class="col-sm-4">
							<select class="form-control" id="sale"><option value="">请选择</option>
								<option value="1">是</option></select>
						</div>
					</div>
					<div class="form-group hidden">
						<input type="text" id="productId" class="form-control">
					</div>
					<div class="form-group hidden">
						<input type="text" id="producturl" class="form-control">
					</div>
				</div>

			</form>
		</div>

	</div>

	<!--在职人员档案  -->
	<div id="addDictDivTypetw" style="display: none;">
		<div class=" col-xs-12  col-sm-12  col-md-12 ">
			<div class="space-10"></div>
			<div style="height: 30px"></div>
			<form class="form-horizontal addDictDivTypeFormtw">
				<div class="row col-xs-12  col-sm-12  col-md-12 ">
					<div class="form-group">
						<label class="col-sm-2 col-md-2 control-label">员工姓名:</label>
						<div class="col-sm-2 col-md-2">
							<input type="text" class="form-control usernametw"
								disabled="disabled">
						</div>
						<label class="col-sm-2 control-label">位置编号:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control numbertw">
						</div>

						<label class="col-sm-2 control-label">照片数量:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control pic">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">身份证数量:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control IdCardnumber">
						</div>
						<label class="col-sm-2 control-label">银行卡数量:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control bankCard">
						</div>
						<label class="col-sm-2 control-label">体检:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control physical">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">资格证书:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control qualification">
						</div>
						<label class="col-sm-2 control-label">学历证书:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control formalSchooling">
						</div>
						<label class="col-sm-2 control-label">其他协议:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control agreementnumbernumber">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">保密协议:</label>
						<div class="col-sm-2 working">
							<input type="text" class="form-control secrecyAgreementnumber">
						</div>
						<label class="col-sm-2 control-label">合同数量:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control contractnumber">
						</div>
						<label class="col-sm-2 control-label">其他资料:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control remarktw">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">员工档案:</label>
						<div class="col-sm-2">
							<input type="text" class="form-control archives">
						</div>
					</div>
				</div>

			</form>
		</div>

	</div>


	<div id="savegroupp">
		<table>
			<tr>
				<th style="vertical-align: top">
					<table class="table table-hover">
						<thead>
							<tr>
								<th class="text-center">即将退休人员</th>
								<th class="text-center">时间</th>
							</tr>
						</thead>
						<thead>
						<tbody id="tablecontentfv">

						</tbody>

					</table>
				</th>

				<th style="vertical-align: top">
					<table class="table table-hover">
						<thead>
							<tr>
								<th class="text-center">合同即将到期人员</th>
								<th class="text-center">时间</th>
							</tr>
						</thead>
						<thead>
						<tbody id="tablecontentff">

						</tbody>
					</table>
				</th>
				<th style="vertical-align: top">
					<table class="table table-hover">
						<thead>
							<tr>
								<th class="text-center">身份证即将到期人员</th>
								<th class="text-center">时间</th>
							</tr>
						</thead>
						<thead>
						<tbody id="tablecontentff1">

						</tbody>
					</table>
				</th>
			</tr>
		</table>
	</div>
	</section>
	

	
	
	
	
	

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
				  		foreigns:0,
				} 
			this.init = function(){
			//注册绑定事件
				self.events();
				self.loadPagination(data);
				self.loadPaginationtw();
			}
			 
			 this.loadPaginationtw = function(){
				 
				 var index;
				    var html ='';
				    var htmlh ='';
				    var htmlh1 ='';
				     var dicDiv=$('#savegroupp');
				     _index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['40%', '80%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"提示",
						  content: dicDiv,
						  end:function(){
							  $("#savegroupp").css("display",'none')
						  }
					});
				     $(".savemode").on('click',function(){
				    	 
				    
				   		  _index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['40%', '80%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:"提示",
						  content: dicDiv,
						  end:function(){
							  $("#savegroupp").css("display",'none')
						  }
					});
				     })
				     
				     $.ajax({
					      url:"${ctx}/system/user/remind",
					      type:"GET",
					      beforeSend:function(){
						 	  index = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			 $(result.data.userBirth).each(function(i,o){
			      				html +='<tr>'
			      				+'<td class="text-center edit username2" data-id="'+o.userId+'">'+o.username+'</td>'
			      				+'<td class="text-center edit price">'+o.birthDate+'</td></tr>'
			      			}); 
			      			$("#tablecontentfv").html(html); 
			      			$(result.data.userContract).each(function(i,o){
			      				htmlh +='<tr>'
			      				+'<td class="text-center edit username2" data-id="'+o.userId+'">'+o.username+'</td>'
			      				+'<td class="text-center edit price">'+o.contractDateEnd+'</td></tr>'
			      			});
			      			   
			      			$("#tablecontentff").html(htmlh);
			      			$(result.data.userCard).each(function(i,o){
			      				htmlh1 +='<tr>'
			      				+'<td class="text-center edit username2" data-id="'+o.userId+'">'+o.username+'</td>'
			      				+'<td class="text-center edit price">'+o.idCardEnd+'</td></tr>'
			      			});
			      			$("#tablecontentff1").html(htmlh1);
			      			self.select();
			      			layer.close(index);
					      },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);
						  }
					  }); 
			 }
			 
			 this.select=function(){
				 $('.username2').on('click',function(){
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
				    var htmlthh= '';
				    var htmlthhh= '';
					    var getdata={type:"orgName",}
		      			$.ajax({
						      url:"${ctx}/basedata/list",
						      data:getdata,
						      type:"GET",
						      async:false,
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
									      async:false,
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
						
					    var getdataa={type:"agreements",}
					    
					    $.ajax({
						      url:"${ctx}/basedata/list",
						      data:getdataa,
						      type:"GET",
						      async:false,
						      beforeSend:function(){
						    	  indextwo = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			  $(result.data).each(function(k,j){
				      		htmlthh+='<input type="checkbox" class="checkWorktw" value="'+j.id+'">'+j.name+'</input>'
				      			  });
				      			$(".agreementtw").html(htmlthh);
				      			layer.close(indextwo);
						      }
						  });
					    
						var getdataa={type:"commitments",}
					    
					    $.ajax({
						      url:"${ctx}/basedata/list",
						      data:getdataa,
						      type:"GET",
						      async:false,
						      beforeSend:function(){
						    	  indextwo = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			  $(result.data).each(function(k,j){
				      		htmlthhh+='<input type="radio" class="checkWork" name="cc" value="'+j.id+'">'+j.name+'</input>'
				      			  });
				      			$(".remarktww").html(htmlthhh);
				      			layer.close(indextwo);
						      }
						  });
					    
					  var data={
							id:id		
						}
					  var idCard="";
					    $.ajax({
						      url:"${ctx}/system/user/pages",
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
				      				 var order = i+1;
				      				var k;
				      				var th='';
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
				      			
				      				th='<div class="dz-preview dz-processing dz-image-preview dz-success"><div class="dz-details"><img data-dz-thumbnail  src='+o.pictureUrl+'></div><div class="dz-success-mark" ></div></div>'
				      				 $("#my-awesome-dropzone").html(th); 
				      				$('.dz-success-mark').on('click',function(){
				      					var thate=$(this);
				      					thate.parent().hide();
				      				})
				      				idCard=o.bankCard1	
				      				var de={
					      					idCard:idCard,
					      			}
					      			$.ajax({
									      url:"${ctx}/system/user/getbank",
									      data:de,
									      type:"GET",
									      async:false,
									      beforeSend:function(){
										 	  index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											  });
										  }, 
							      		  success: function (result) {
							      				$('.bankCardtw').val(result.data)
							      				layer.close(index);
									      },error:function(){
												layer.msg("加载失败！", {icon: 2});
												layer.close(index);
										  }
									  });
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
				      				$('.contract').val(o.contract);
				      				$('#contractDate').val(o.contractDate);
				      				$('.frequency').val(o.frequency);
				      				$('#quitDate').val(o.quitDate);
				      				$('.reason').val(o.reason);
				      				$('.train').val(o.train);
				      				$('.remark').val(o.remark);
				      				$('.company').val(o.company);
					      			$("#idCardEnd").val(o.idCardEnd);
					      			$("#contractDateEnd").val(o.contractDateEnd);
					      			$('#productId').val(o.fileId);
									$('#producturl').val(o.pictureUrl);
					      			var html='<input class="form-control" value="'+l+'" />'
					      			$(".position").html(html);
					      			$('.gender').each(function(j,k){
										var id=o.gender;
										$(k).val(id);
									});
									$('.marriage').each(function(j,k){
										var id=o.marriage;
										$(k).val(id);
									});
									$('.procreate').each(function(j,k){
										var id=o.procreate;
										$(k).val(id);
									});
									$('.education').each(function(j,k){
										var id=o.education;
										$(k).val(id);
									});
									$('.quit').each(function(j,k){
										var id=o.quit;
										$(k).val(id);
									});
									$('.safe').each(function(j,k){
										var id=o.safe;
										$(k).val(id);
									});
									$('.promise').each(function(j,k){
										var id=o.promise;
										$(k).val(id);
									});
				      				$('.nation').each(function(j,k){
										var id=o.nation;
										$(k).val(id);
									});
									$('.commitment').each(function(j,k){
										var id=o.commitment;
										$(k).val(id);
									});
									$('#type4').each(function(j,k){
										var id=o.type;
										$(k).val(id);
									});
									$('#sale').each(function(j,k){
										var id=o.sale;
										$(k).val(id);
									});
									if(o.commitments!=null){
									 	var ids=o.commitments.id;
									$('.checkWork').each(function(j,k){
										if(ids==$(k).val()){
											$(k).attr("checked","true"); 
										}
										
									}); 
									}
										var id=o.agreementId;
									 $('.checkWorktw').each(function(j,k){
										 if(id.indexOf($(k).val())>=0){
												$(k).attr("checked","true"); 
											}
									}) ;
									 
									 
									 $(".idCard").blur(function(){
											var UUserCard = $(".idCard").val();
											if(UUserCard != null && UUserCard != ''){
												//获取出生日期 
												var birthday = UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14); 
												$('#birthDate').val(birthday+' '+'00:00:00');
												//获取性别 
												if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) { 
													$('.gender').each(function(j,k){
														var id=0;//男
														$(k).val(id);
													});
												} else { 
													$('.gender').each(function(j,k){
														var id=1;//女
														$(k).val(id);
													});
												}
												//获取年龄 
												 var myDate = new Date(); 
												var month = myDate.getMonth() + 1; 
												var day = myDate.getDate();
												var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1; 
												if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) { 
												age++; 
												} 
												
											}
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
								  var values=new Array()
								  var numberr=new Array()
									$(".checkWork:checked").each(function() {   
										values.push($(this).val());
									}); 
								  $(".checkWorktw:checked").each(function() {   
									  numberr.push($(this).val());
									}); 
								  postData={
										  	id:id,
										  	agreementId:numberr,
											commitmentId:values,
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
											positionId:$('.selectChange').val(),
											idCardEnd:$('#idCardEnd').val(),
											contractDateEnd:$('#contractDateEnd').val(),
											fileId:$('#productId').val(),
											pictureUrl:$('#producturl').val(),
											company:$('.company').val(),
											commitment:$('.commitment').val(),
											safe:$('.safe').val(),
											type:$('#type4').val(),
											sale:$('#sale').val(),
											ascriptionBank1:$('.bankCardtw').val(),
								  }
								   $.ajax({
										url:"${ctx}/system/user/update",
										data:postData,
										type:"POST",
										traditional: true,
										beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										
										success:function(result){
											if(0==result.code){
												layer.msg("修改成功！", {icon: 1});
												/* $('.addDictDivTypeForm')[0].reset(); 
												$("#my-awesome-dropzone").text(""); */
												var entry="";
									  			var estimate="";
									  			var actua="";
													
													if($("#timesss").val()=="entry"){
														entry="2018-10-08 00:00:00"
													}
													if($("#timesss").val()=="estimate"){
														estimate="2018-10-08 00:00:00"
													}
													if($("#timesss").val()=="actua"){
														actua="2018-10-08 00:00:00"
													}
												var data = {
											  			page:self.getCount(),
											  			size:13,
											  			quit:$('#groupp').val(),
											  			foreigns:0,
											  			userName:$('#name').val(),
											  			orgNameIds:$('.sel').val(),
											  			gender:$('#gender').val(),
											  			retire:$('#retire').val(),
											  			commitment:$('#commitment').val(),
											  			promise:$('#promise').val(),
											  			safe:$('#safe').val(),
											  			lotionNumber:$('#number').val(),
											  			entry:entry,
											  			estimate:estimate,
											  			actua:actua,
											  			orderTimeBegin:$("#startTime").val(),
											  			orderTimeEnd:$("#endTime").val(),
											  			ascriptionBank1:$("#bankCardtw").val(),
											  			education:$("#education2").val(),
											  	}
												layer.close(index);
												self.loadPagination(data);
											}else if (2==result.code) {
												layer.open({
													   title: '提示'
													  ,content:'该员工没有初始化设定,请点击添加'
													  ,btn: ['确认', '取消']
													,yes: function(index, layero){
														window.location.href = "${ctx}/menusToUrl?url=personnel/init"
										       			 }
													}); 
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
								  $('.addDictDivTypeForm')[0].reset(); 
								  $("#my-awesome-dropzone").text("");
								  /*  $("#addDictDivType").hide(); */
								  layer.close(index);
							  }
						});
				 })
			 }
			//加载分页
			  this.loadPagination = function(data){
				$(".quit").change(function(){
					if($(".quit").val()==1){
					var ind=layer.open({
							   title: '提示'
							  ,content:'确定删除考勤机上的指纹吗？'
							  ,btn: ['确认', '取消']
							,yes: function(index, layero){
								layer.close(ind);
								var arr = ['192.168.1.204','192.168.1.250','192.168.1.205']
								var index = layer.load(1, {
									  shade: [0.1,'#fff'], //0.1透明度的白色背景
								});;
								for (var i = 0; i < arr.length; i++) {
						    	  var postData={
											number:$('.number').val(),
											address:arr[i],
									}
								   $.ajax({
									url:"${ctx}/personnel/deleteUser",
									data:postData,
									async: false,
									type:"GET",
									success:function(result){
										
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});  
								}
								layer.close(index);
									
				       			 }
							}); 
					}
				})
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
		      			$("#total").text(result.data.total)
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
		      				var m="";
		      				var v="";
		      				if(o.userContract!=null){
		      				if(o.userContract.id==null){
		      					 m=""
		      				 }else{
		      					 m=o.userContract.id
		      				 }
		      				if(o.userContract.number==null){
		      					 v=""
		      				 }else{
		      					 v=o.userContract.number
		      				 }
		      				}
		      				var r;
		      				 if(o.quit==0){
		      					 r="在职"
		      				 }else{
		      					 r="离职"
		      				 }
		      				
		      				var commitment="";
		      				 if(o.commitment==0){
		      					commitment="未签"
		      				 }else if(o.commitment==1){
		      					commitment="已签"
		      				 }else if(o.commitment==2){
		      					commitment="续签"
		      				 }else{
		      					o.commitment=commitment
		      				 }
		      				 
		      				 var promise="";
		      				if(o.promise==0){
		      					promise="未签"
		      				 }else if(o.promise==1){
		      					promise="已签"
		      				 }else{
		      					o.promise=promise
		      				 }
		      				var safe="";
		      				if(o.safe==0){
		      					safe="未缴"
		      				 }else if(o.safe==1){
		      					safe="已缴"
		      				 }else{
		      					o.safe=safe
		      				 }
		      				var age="";
		      				if(o.age==null){
		      					age=""
		      				}else{
		      					age=o.age
		      				}
		      				var newDate=/\d{4}-\d{1,2}-\d{1,2}/g.exec(o.contractDateEnd)
		      				var newDate1=/\d{4}-\d{1,2}-\d{1,2}/g.exec(o.estimate)
		      				var newDate2=/\d{4}-\d{1,2}-\d{1,2}/g.exec(o.entry)
		      				html +='<tr>'
		      				+'<td class="text-center edit price">'+v+'</td>'
		      				+'<td class="text-center edit price">'+o.userName+'</td>'
		      				+'<td class="text-center edit price">'+o.phone+'</td>'
		      				+'<td class="text-center edit price">'+age+'</td>'
		      				+'<td class="text-center edit price">'+commitment+'</td>'
		      				+'<td class="text-center edit price">'+promise+'</td>'
		      				+'<td class="text-center edit price">'+safe+'</td>'
		      				+'<td class="text-center edit price">'+(newDate2!=null ? newDate2 : "") +'</td>'
		      				+'<td class="text-center edit price">'+(newDate1!=null ? newDate1: "")+'</td>'
		      				+'<td class="text-center edit price">'+(newDate!=null ? newDate: "")+'</td>'
		      				+'<td class="text-center edit price">'+k+'</td>'
		      				+'<td class="text-center edit price">'+r+'</td>'
							+'<td class="text-center edit price"><button class="btn btn-xs btn-success btn-trans addbatch" data-id='+o.id+' data-name='+o.userName+' data-nameid='+z+' data-postid='+u+'>员工详情</button> <button class="btn btn-xs btn-success btn-trans addbatchtw" data-idd='+o.id+' data-ids='+m+' >档案位置详情</button> <button class="btn btn-xs btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>'
		      			}); 
		      			self.setCount(result.data.pageNum)
				        //显示分页
					  laypage({
					      cont: 'pager', 
					      skip: true,
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		  var entry="";
						  			var estimate="";
						  			var actua="";
										
										if($("#timesss").val()=="entry"){
											entry="2018-10-08 00:00:00"
										}
										if($("#timesss").val()=="estimate"){
											estimate="2018-10-08 00:00:00"
										}
										if($("#timesss").val()=="actua"){
											actua="2018-10-08 00:00:00"
										}
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		quit:$('#groupp').val(),
								  			foreigns:0,
								  			userName:$('#name').val(),
								  			orgNameIds:$('.sel').val(),
								  			gender:$('#gender').val(),
								  			retire:$('#retire').val(),
								  			commitment:$('#commitment').val(),
								  			promise:$('#promise').val(),
								  			safe:$('#safe').val(),
								  			lotionNumber:$('#number').val(),
								  			entry:entry,
								  			estimate:estimate,
								  			actua:actua,
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			ascriptionBank1:$("#bankCardtw").val(),
								  			education:$("#education2").val(),
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
				  
				  $('.delete').on('click',function(){
						var postData = {
								id:$(this).data('id'),
						}
						
						var index;
						 index = layer.confirm('<div>输入密码:<input id="password" /></div>', {btn: ['确定', '取消']},function(){
							 if($("#password").val()==3116){
						 $.ajax({
							url:"${ctx}/system/user/deleteUser",
							data:postData,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
								layer.msg(result.message, {icon: 1});
								var entry="";
					  			var estimate="";
					  			var actua="";
									
									if($("#timesss").val()=="entry"){
										entry="2018-10-08 00:00:00"
									}
									if($("#timesss").val()=="estimate"){
										estimate="2018-10-08 00:00:00"
									}
									if($("#timesss").val()=="actua"){
										actua="2018-10-08 00:00:00"
									}
								var _data = {
							  			page:1,
							  			size:13,
							  			quit:$('#groupp').val(),
							  			foreigns:0,
							  			userName:$('#name').val(),
							  			orgNameIds:$('.sel').val(),
							  			gender:$('#gender').val(),
							  			retire:$('#retire').val(),
							  			commitment:$('#commitment').val(),
							  			promise:$('#promise').val(),
							  			safe:$('#safe').val(),
							  			lotionNumber:$('#number').val(),
							  			entry:entry,
							  			estimate:estimate,
							  			actua:actua,
							  			orderTimeBegin:$("#startTime").val(),
							  			orderTimeEnd:$("#endTime").val(),
							  			ascriptionBank1:$("#bankCardtw").val(),
							  			education:$("#education2").val(),
							  	}
								self.loadPagination(_data)
								layer.close(index);
								}else if(1500==result.code){
									layer.msg("该名人员无法删除 存在数据关联", {icon: 2});
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
							 }else{
								 return layer.msg("请填写正确密码", {icon: 2});
							 }
						 })
			})
				  
				  
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
				    var htmlthh= '';
				    var htmlthhh= '';
					    var getdata={type:"orgName",}
		      			$.ajax({
						      url:"${ctx}/basedata/list",
						      data:getdata,
						      type:"GET",
						      async:false,
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
									      async:false,
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
						
					    var getdataa={type:"agreements",}
					    
					    $.ajax({
						      url:"${ctx}/basedata/list",
						      data:getdataa,
						      type:"GET",
						      async:false,
						      beforeSend:function(){
						    	  indextwo = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			  $(result.data).each(function(k,j){
				      		htmlthh+='<input type="checkbox" class="checkWorktw" value="'+j.id+'">'+j.name+'</input>'
				      			  });
				      			$(".agreementtw").html(htmlthh);
				      			layer.close(indextwo);
						      }
						  });
					    
						var getdataa={type:"commitments",}
					    
					    $.ajax({
						      url:"${ctx}/basedata/list",
						      data:getdataa,
						      type:"GET",
						      async:false,
						      beforeSend:function(){
						    	  indextwo = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			  $(result.data).each(function(k,j){
				      		htmlthhh+='<input type="radio" class="checkWork" name="cc" value="'+j.id+'">'+j.name+'</input>'
				      			  });
				      			$(".remarktww").html(htmlthhh);
				      			layer.close(indextwo);
						      }
						  });
					    
					  var data={
							id:id		
						}
					  var idCard="";
					    $.ajax({
						      url:"${ctx}/system/user/pages",
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
				      				 var order = i+1;
				      				var k;
				      				var th='';
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
				      			
				      				th='<div class="dz-preview dz-processing dz-image-preview dz-success"><div class="dz-details"><img data-dz-thumbnail  src='+o.pictureUrl+'></div><div class="dz-success-mark" ></div></div>'
				      				 $("#my-awesome-dropzone").html(th); 
				      				$('.dz-success-mark').on('click',function(){
				      					var thate=$(this);
				      					thate.parent().hide();
				      				})
				      				idCard=o.bankCard1	
				      				var de={
					      					idCard:idCard,
					      			}
					      			$.ajax({
									      url:"${ctx}/system/user/getbank",
									      data:de,
									      type:"GET",
									      async:false,
									      beforeSend:function(){
										 	  index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											  });
										  }, 
							      		  success: function (result) {
							      				$('.bankCardtw').val(result.data)
							      				layer.close(index);
									      },error:function(){
												layer.msg("加载失败！", {icon: 2});
												layer.close(index);
										  }
									  });
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
				      				$('.contract').val(o.contract);
				      				$('#contractDate').val(o.contractDate);
				      				$('.frequency').val(o.frequency);
				      				$('#quitDate').val(o.quitDate);
				      				$('.reason').val(o.reason);
				      				$('.train').val(o.train);
				      				$('.remark').val(o.remark);
				      				$('.company').val(o.company);
					      			$("#idCardEnd").val(o.idCardEnd);
					      			$("#contractDateEnd").val(o.contractDateEnd);
					      			$('#productId').val(o.fileId);
									$('#producturl').val(o.pictureUrl);
					      			var html='<input class="form-control" value="'+l+'" />'
					      			$(".position").html(html);
					      			$('.gender').each(function(j,k){
										var id=o.gender;
										$(k).val(id);
									});
									$('.marriage').each(function(j,k){
										var id=o.marriage;
										$(k).val(id);
									});
									$('.procreate').each(function(j,k){
										var id=o.procreate;
										$(k).val(id);
									});
									$('.education').each(function(j,k){
										var id=o.education;
										$(k).val(id);
									});
									$('.quit').each(function(j,k){
										var id=o.quit;
										$(k).val(id);
									});
									$('.safe').each(function(j,k){
										var id=o.safe;
										$(k).val(id);
									});
									$('.promise').each(function(j,k){
										var id=o.promise;
										$(k).val(id);
									});
				      				$('.nation').each(function(j,k){
										var id=o.nation;
										$(k).val(id);
									});
									$('.commitment').each(function(j,k){
										var id=o.commitment;
										$(k).val(id);
									});
									$('#type4').each(function(j,k){
										var id=o.type;
										$(k).val(id);
									});
									$('#sale').each(function(j,k){
										var id=o.sale;
										$(k).val(id);
									});
									if(o.commitments!=null){
									 	var ids=o.commitments.id;
									$('.checkWork').each(function(j,k){
										if(ids==$(k).val()){
											$(k).attr("checked","true"); 
										}
									}); 
									}
										var id=o.agreementId;
									 $('.checkWorktw').each(function(j,k){
										 if(id.indexOf($(k).val())>=0){
												$(k).attr("checked","true"); 
											}
									}) ;
									 
									 
									 $(".idCard").blur(function(){
											var UUserCard = $(".idCard").val();
											if(UUserCard != null && UUserCard != ''){
												//获取出生日期 
												var birthday = UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14); 
												$('#birthDate').val(birthday+' '+'00:00:00');
												//获取性别 
												if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) { 
													$('.gender').each(function(j,k){
														var id=0;//男
														$(k).val(id);
													});
												} else { 
													$('.gender').each(function(j,k){
														var id=1;//女
														$(k).val(id);
													});
												}
												//获取年龄 
												 var myDate = new Date(); 
												var month = myDate.getMonth() + 1; 
												var day = myDate.getDate();

												var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1; 
												if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) { 
												age++; 
												} 
												
											}
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
								  var values=new Array()
								  var numberr=new Array()
									$(".checkWork:checked").each(function() {   
										values.push($(this).val());
									}); 
								  $(".checkWorktw:checked").each(function() {   
									  numberr.push($(this).val());
									}); 
								  postData={
										  	id:id,
										  	agreementId:numberr,
											commitmentId:values,
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
											positionId:$('.selectChange').val(),
											idCardEnd:$('#idCardEnd').val(),
											contractDateEnd:$('#contractDateEnd').val(),
											fileId:$('#productId').val(),
											pictureUrl:$('#producturl').val(),
											company:$('.company').val(),
											commitment:$('.commitment').val(),
											safe:$('.safe').val(),
											type:$('#type4').val(),
											sale:$('#sale').val(),
											ascriptionBank1:$('.bankCardtw').val(),
								  }
								   $.ajax({
										url:"${ctx}/system/user/update",
										data:postData,
										type:"POST",
										traditional: true,
										beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										
										success:function(result){
											if(0==result.code){
												layer.msg("修改成功！", {icon: 1});
												/* $('.addDictDivTypeForm')[0].reset(); 
												$("#my-awesome-dropzone").text(""); */
												var entry="";
									  			var estimate="";
									  			var actua="";
													
													if($("#timesss").val()=="entry"){
														entry="2018-10-08 00:00:00"
													}
													if($("#timesss").val()=="estimate"){
														estimate="2018-10-08 00:00:00"
													}
													if($("#timesss").val()=="actua"){
														actua="2018-10-08 00:00:00"
													}
												var data = {
											  			page:self.getCount(),
											  			size:13,
											  			quit:$('#groupp').val(),
											  			foreigns:0,
											  			userName:$('#name').val(),
											  			orgNameIds:$('.sel').val(),
											  			gender:$('#gender').val(),
											  			retire:$('#retire').val(),
											  			commitment:$('#commitment').val(),
											  			promise:$('#promise').val(),
											  			safe:$('#safe').val(),
											  			lotionNumber:$('#number').val(),
											  			entry:entry,
											  			estimate:estimate,
											  			actua:actua,
											  			orderTimeBegin:$("#startTime").val(),
											  			orderTimeEnd:$("#endTime").val(),
											  			ascriptionBank1:$("#bankCardtw").val(),
											  			education:$("#education2").val(),
											  	}
												layer.close(index);
												self.loadPagination(data);
											}else if (2==result.code) {
												layer.open({
													   title: '提示'
													  ,content:'该员工没有初始化设定,请点击添加'
													  ,btn: ['确认', '取消']
													,yes: function(index, layero){
														window.location.href = "${ctx}/menusToUrl?url=personnel/init"
										       			 }
													}); 
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
								  $('.addDictDivTypeForm')[0].reset(); 
								  $("#my-awesome-dropzone").text("");
								  /*  $("#addDictDivType").hide(); */
								  layer.close(index);
							  }
						});
					})
					
					/* 在职人员档案 */
					$('.addbatchtw').on('click',function(){
						var _index
						var index
						var postData   
						var postId=$(this).data('postid');
						var nameId=$(this).data('nameid');
						var dicDiv=$('#addDictDivTypetw');
						var userName=$(this).data('name');
						var bacthDepartmentPrice=$(this).parent().parent().find('.departmentPrice').text();
						var bacthHairPrice=$(this).parent().parent().find('.hairPrice').text();
						$('#proName').val(userName);
						var id=$(this).data('idd');
						var ids=$(this).data('ids');
						if(ids==""){
							return layer.msg("该员工没有合同信息 请去员工信息中修改", {icon: 2});
						}
						var a="";
						var c="";
						//遍历工序类型
				    var indextwo;
				    var htmltwo = '';
				    var htmlth = '';
				    var htmlfr = '';
				    var html = '';
					  var data={
							id:id		
						}
					  
					    $.ajax({
						      url:"${ctx}/system/user/pages",
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
				      				$('.userNametw').val(o.userName);
				      				$('.numbertw').val(o.userContract.number);
				      				$('.archives').val(o.userContract.archives);
				      				$('.pic').val(o.userContract.pic);
				      				$('.IdCardnumber').val(o.userContract.idCard);
				      				$('.bankCard').val(o.userContract.bankCard);
				      				$('.physical').val(o.userContract.physical);
				      				$('.qualification').val(o.userContract.qualification);
				      				$('.formalSchooling').val(o.userContract.formalSchooling);
				      				$('.secrecyAgreementnumber').val(o.userContract.secrecyAgreement);
				      				$('.agreementnumbernumber').val(o.userContract.agreement);
				      				$('.remarktw').val(o.userContract.remark);
				      				$('.contractnumber').val(o.userContract.contract);
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
										  id:ids,
										  archives:$('.archives').val(),
										  pic:$('.pic').val(),
										  idCard:$('.IdCardnumber').val(),
										  bankCard:$('.bankCard').val(),
										  physical:$('.physical').val(),
										  qualification:$('.qualification').val(),
										  formalSchooling:$('.formalSchooling').val(),
										  agreement:$('.agreementnumbernumber').val(),
										  secrecyAgreement:$('.secrecyAgreementnumber').val(),
										  remark:$('.remarktw').val(),
										  contract:$('.contractnumber').val(),
										  number:$('.numbertw').val(),
								  }
								   $.ajax({
										url:"${ctx}/system/user/updateContract",
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
												var entry="";
									  			var estimate="";
									  			var actua="";
													
													if($("#timesss").val()=="entry"){
														entry="2018-10-08 00:00:00"
													}
													if($("#timesss").val()=="estimate"){
														estimate="2018-10-08 00:00:00"
													}
													if($("#timesss").val()=="actua"){
														actua="2018-10-08 00:00:00"
													}
												var data = {
											  			page:self.getCount(),
											  			size:13,
											  			quit:$('#groupp').val(),
											  			foreigns:0,
											  			userName:$('#name').val(),
											  			orgNameIds:$('.sel').val(),
											  			gender:$('#gender').val(),
											  			retire:$('#retire').val(),
											  			commitment:$('#commitment').val(),
											  			promise:$('#promise').val(),
											  			safe:$('#safe').val(),
											  			lotionNumber:$('#number').val(),
											  			entry:entry,
											  			estimate:estimate,
											  			actua:actua,
											  			orderTimeBegin:$("#startTime").val(),
											  			orderTimeEnd:$("#endTime").val(),
											  			ascriptionBank1:$("#bankCardtw").val(),
											  			education:$("#education2").val(),
											  	}
												layer.close(index);
												$("#productId").text("");
												self.loadPagination(data);
											}else if (2==result.code) {
												layer.open({
													   title: '提示'
													  ,content:'该员工没有初始化设定,请点击添加'
													  ,btn: ['确认', '取消']
													,yes: function(index, layero){
														window.location.href = "${ctx}/menusToUrl?url=personnel/init"
										       			 }
													}); 
											}else{
												layer.msg(result.message, {icon: 2});
												$("#productId").text("");
											}
											
											layer.close(index);
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									}); 
								},
							  end:function(){
								  
								  layer.close(index);
							  }
						});
					})
					
			  }
			  
			this.events = function(){
				var getdataa={type:"orgName",}
				var htmlfrn="";
				var htmlthn="";
			    $.ajax({
				      url:"${ctx}/basedata/list",
				      data:getdataa,
				      type:"GET",
				      async:false,
				      beforeSend:function(){
				    	  indextwo = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			  $(result.data).each(function(k,j){
		      				htmlfrn +='<option value="'+j.id+'">'+j.name+'</option>'
		      			  });
		      			var htmlthn='<select class="form-control  sel"><option value="">请选择</option>'+htmlfrn+'</select>'
		      			$("#orgName").html(htmlthn);
		      			layer.close(indextwo);
				      }
				  });
				$('.searchtask').on('click',function(){
					var entry="";
		  			var estimate="";
		  			var actua="";
						
						if($("#timesss").val()=="entry"){
							entry="2018-10-08 00:00:00"
						}
						if($("#timesss").val()=="estimate"){
							estimate="2018-10-08 00:00:00"
						}
						if($("#timesss").val()=="actua"){
							actua="2018-10-08 00:00:00"
						}
					var data = {
				  			page:1,
				  			size:13,
				  			quit:$('#groupp').val(),
				  			foreigns:0,
				  			userName:$('#name').val(),
				  			orgNameIds:$('.sel').val(),
				  			gender:$('#gender').val(),
				  			retire:$('#retire').val(),
				  			commitment:$('#commitment').val(),
				  			promise:$('#promise').val(),
				  			safe:$('#safe').val(),
				  			lotionNumber:$('#number').val(),
				  			entry:entry,
				  			estimate:estimate,
				  			actua:actua,
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(),
				  			ascriptionBank1:$("#bankCardtw").val(),
				  			education:$("#education2").val(),
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
					    var htmlthh='';
					    var htmlthhh='';
						    var getdata={type:"orgName",}
			      			$.ajax({
							      url:"${ctx}/basedata/list",
							      data:getdata,
							      type:"GET",
							      async:false,
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
										      async:false,
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
						    var getdataa={type:"agreements",}
						    
						    $.ajax({
							      url:"${ctx}/basedata/list",
							      data:getdataa,
							      type:"GET",
							      async:false,
							      beforeSend:function(){
							    	  indextwo = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									  });
								  }, 
					      		  success: function (result) {
					      			  $(result.data).each(function(k,j){
					      		htmlthh+='<input type="checkbox" class="checkWorktw" value="'+j.id+'">'+j.name+'</input>'
					      			  });
					      			$(".agreementtw").html(htmlthh);
					      			layer.close(indextwo);
							      }
							  });
						    
							var getdataa={type:"commitments",}
						    
						    $.ajax({
							      url:"${ctx}/basedata/list",
							      data:getdataa,
							      type:"GET",
							      async:false,
							      beforeSend:function(){
							    	  indextwo = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									  });
								  }, 
					      		  success: function (result) {
					      			  $(result.data).each(function(k,j){
					      		htmlthhh+='<input type="radio" class="checkWork" name="cc" value="'+j.id+'">'+j.name+'</input>'
					      			  });
					      			$(".remarktww").html(htmlthhh);
					      			layer.close(indextwo);
							      }
							  });
							
							
							$(".idCard").blur(function(){
								var UUserCard = $(".idCard").val();
								if(UUserCard != null && UUserCard != ''){
									//获取出生日期 
									var birthday = UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14); 
									$('#birthDate').val(birthday+' '+'00:00:00');
									//获取性别 
									if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) { 
										$('.gender').each(function(j,k){
											var id=0;//男
											$(k).val(id);
										});
									} else { 
										$('.gender').each(function(j,k){
											var id=1;//女
											$(k).val(id);
										});
									}
									//获取年龄 
									 var myDate = new Date(); 
									var month = myDate.getMonth() + 1; 
									var day = myDate.getDate();

									var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1; 
									if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) { 
									age++; 
									} 
									
								}
							})
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
								}); 
							  $(".checkWorktw:checked").each(function() {   
								  numberr.push($(this).val());
								}); 
							  if($('.userName').val()==""){
								  return layer.msg("姓名不能为空", {icon: 2});
							  }
								var postData = {
										agreementId:numberr,
										commitmentId:values,
										userName:$('.userName').val(),
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
										commitment:$('.commitment').val(),
										contractDate:$('#contractDate').val(),
										frequency:$('.frequency').val(),
										company:$('.company').val(),
										quit:$('.quit').val(),
										quitDate:$('#quitDate').val(),
										reason:$('.reason').val(),
										train:$('.train').val(),
										remark:$('.remark').val(),
										orgNameId:$('.selectgroupChange').val(),
										positionId:$('.selectChange').val(),
										safe:$('.safe').val(),
										fileId:$('#productId').val(),
										pictureUrl:$('#producturl').val(),
										idCardEnd:$('#idCardEnd').val(),
										contractDateEnd:$('#contractDateEnd').val(),
										type:$('#type4').val(),
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
										   $('.addDictDivTypeForm')[0].reset(); 
										  var htmlfv="";
											layer.msg(result.message, {icon: 1});
											var data = {
										  			page:1,
										  			size:13,
										  			/* quit:$('#groupp').val(),
										  			foreigns:0,
										  			userName:$('#name').val(),
										  			orgNameIds:$('.sel').val(),
										  			gender:$('#gender').val(),
										  			retire:$('#retire').val(),
										  			commitment:$('#commitment').val(),
										  			promise:$('#promise').val(),
										  			safe:$('#safe').val(), */
										  	}
											self.loadPagination(data);
										}
										
										if (2==result.code) {
											layer.open({
												   title: '提示'
												  ,content:'该员工没有初始化设定,请点击添加'
												  ,btn: ['确认', '取消']
												,yes: function(index, layero){
													window.location.href = "${ctx}/menusToUrl?url=personnel/init"
									       			 }
												});
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