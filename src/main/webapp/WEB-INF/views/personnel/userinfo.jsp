<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
	.layui-form-label{
		width: 130px !important;
	}
	.contractDiv .layui-form-pane .layui-form-switch, .layui-form-pane .layui-form-radio{
	    margin-left: 4px;
	    margin-right: 4px;
	}
	.userInfo{
		width:100%;
	}
	.userInfo td{
		height:48px;
	}
	.userInfo .titleTr{
	
	}
	.userInfo .titleTr td{
		color: gray;
	    font-weight: bold;
	    font-size: 15px;
	    padding: 4px 20px;
	    height: 25px;
	    background: #f2f2f2;
	}
	.userInfo .layui-input, .layui-textarea, .layui-form-select {
	    width: 160px;
	}
	@media screen and (max-width: 1420px){
      .layui-form-label,.layui-input-inline{
      	width:100% !important;
      }
      .userInfo .layui-input, .layui-textarea, .layui-form-select {
    	width:100% !important;
	  }
    }
	</style>
	<title>员工信息</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>员工姓名:</td>
				<td><input  class="layui-input" name="userName" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>在离职:</td>
				<td><select class="form-control" name="quit">
						<option value="">请选择</option>
						<option value="0">在职</option>
						<option value="1">离职</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>部门:</td>
				<td ><select class="form-control" id="orgName" name="orgNameIds" lay-search="true"></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>性别:</td>
				<td><select class="form-control" name="gender">
						<option value="">请选择</option>
						<option value="0">男</option>
						<option value="1">女</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>退休返聘:</td>
				<td><select class="form-control" id="retire" name="retire">
				 		<option value="">否</option>
						<option value="0">是</option></select></td>
			</tr>
			<tr>
				<td><div style="height: 10px"></div></td>
			</tr>
			<tr>
				<td>位置编号:</td>
				<td><input type="text" name="lotionNumber" class="layui-input" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>保险详情:</td>
				<td><select class="form-control" name="safe">
						<option value="">请选择</option>
						<option value="0">未缴</option>
						<option value="1">已缴</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>时间查询:</td>
				<td><select class="form-control" id="timesss">
						<option value="">请选择</option>
						<option value="entry">入职时间</option>
						<option value="actua">实际转正时间</option>
						<option value="estimate">预计转正时间</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>输入时间:</td>
				<td><input id="startTime" placeholder="请输入时间"  class="layui-input laydate-icon">
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>合同种类:</td>
				<td><select class="form-control" lay-search="true" id="commitments" name="commitmentId">
			 		<option value="">请选择</option></select></td>
			 	<td>&nbsp;&nbsp;</td>	
		 		<td>
				<div class="layui-inline">
					<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">
						<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
					</button>
				</div>
				</td>
			</tr>
			<tr>
				<td><div style="height: 10px"></div></td>
			</tr>
			<tr>
				<td>所属银行:</td>
				<td><input type="text" name="ascriptionBank1" class="layui-input" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>学历查询:</td>
				<td><select id="education2" class="form-control" name="education">
						<option value="">请选择</option>
						<option value="本科">本科</option>
						<option value="大专">大专</option>
						<option value="高中">高中</option>
						<option value="初中及以下">初中及以下</option></select></td>
				<td>&nbsp;&nbsp;</td>    <!-- 修改此处 -->
				<td>协议:</td>
				<td><select id="agreementsSelect" name="agreementId" class="form-control">
					</select></td>
					<td>&nbsp;&nbsp;</td>
				<td>合同状态:</td>
				<td><select class="form-control" name="commitment">
						<option value="">请选择</option>
						<option value="0">未签</option>
						<option value="1">已签</option>
						<option value="2">续签</option></select></td>
					<td>&nbsp;&nbsp;</td>
				<td>承诺书:</td>
				<td><select class="form-control" name="promise">
						<option value="">请选择</option>
						<option value="0">未签</option>
						<option value="1">已签</option></select></td>	
			</tr>
		</table>
		<table class="layui-form" id="recruitTable" lay-filter="recruitTable" ></table>
	</div>
</div>
<!-- 待转正人员 -->
<!-- <div id="specialWinDiv" style="diaplay:none;">
	<table class="layui-table" id="specialTable" lay-filter="specialTable"></table>
</div> -->
<div id="userin" style="display: none; padding-left: 19px;" >
	<table>
		<tr>
			<th style="vertical-align: top">
				<table class="layui-table">
					<thead>
						<tr>
							<th class="text-center">即将退休人员</th>
							<th class="text-center" style="text-align: inherit;">时间</th>
						</tr>
					</thead>
					<tbody id="tablecontentfv"></tbody>
				</table>
			</th>
			<th style="vertical-align: top">
				<table class="layui-table">
					<thead>
						<tr>
							<th class="text-center">合同即将到期人员</th>
							<th class="text-center" style="text-align: inherit;">时间</th>
						</tr>
					</thead>
					<tbody id="tablecontentff"></tbody>
				</table>
			</th>
			<th style="vertical-align: top">
				<table class="layui-table">
					<thead>
						<tr>
							<th class="text-center">身份证即将到期人员</th>
							<th class="text-center" style="text-align: inherit;">时间</th>
						</tr>
					</thead>
					<tbody id="tablecontentff1"></tbody>
				</table>
			</th>
			<th style="vertical-align: top">
				<table class="layui-table">
					<thead>
						<tr>
							<th class="text-center">健康证将到期</th>
							<th class="text-center" style="text-align: inherit;">时间</th>
						</tr>
					</thead>
					<tbody id="healthCertificateTimeTable"></tbody>
				</table>
			</th>
		</tr>
	</table>
</div>
</body>
<script type="text/html" id="addEditTpl">
<div style="padding:10px;">
	<table class="layui-form layui-form-pane userInfo">
		<tr class="titleTr">
			<td colspan="4"><i class="layui-icon layui-icon-app"></i>&nbsp;&nbsp;&nbsp;&nbsp;员工基本信息</td>
		</tr>
		<tr class="layui-form-item">
			<td rowspan="4" style="text-align: center;">
				<img class="layui-upload-img" id="demo1" src="{{ d.pictureUrl }}" style="width:150px; height:166px;">
    			<p id="demoText"></p>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">员工姓名</label>
			      <div class="layui-input-inline">
			        <input type="tel"  name="userName" value="{{ d.userName }}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">性别</label>
			      <div class="layui-input-inline">
					<select class="form-control gender" id="gender" name="gender" ><option value="0" {{ d.gender==0?'selected':'' }}>男</option><option value="1" {{ d.gender==1?'selected':'' }}>女</option></select>
			      </div>
			    </div>
			</td>
		    <td>
				<div class="layui-inline">
			      <label class="layui-form-label">手机号</label>
			      <div class="layui-input-inline">
			        <input type="tel"  name="phone" value="{{ d.phone }}" lay-verify="required|phone" autocomplete="off" class="layui-input">
			      </div>
			    </div>
		    </td>
		</tr>
		<tr class="layui-form-item">
			<td colspan="">
				<div class="layui-inline">
			      <label class="layui-form-label">户籍地址</label>
			      <div class="layui-input-inline">
			        <input type="text" name="permanentAddress" value="{{ d.permanentAddress }}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">现居住地址</label>
			      <div class="layui-input-inline">
			        <input type="text" name="livingAddress" value="{{ d.livingAddress }}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">名族</label>
			      <div class="layui-input-inline">
					<select class="form-control nation" name="nation"><option value="汉" {{ d.nation=='汉'?'selected':'' }}>汉</option><option value="少数民族" {{ d.nation=='少数民族'?'selected':'' }}>少数名族</option></select>
			      </div>
			    </div>
			</td>
		</tr>
		<tr class="layui-form-item">
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">出生日期</label>
			      <div class="layui-input-inline">
			        <input type="text" name="birthDate" id="birthDate" value="{{ d.birthDate==null ? "" : d.birthDate}}" readonly="readonly"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">身份证号</label>
			      <div class="layui-input-inline">
			        <input type="tel"  name="idCard" id="idCard" value="{{ d.idCard }}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">身份证到期时间</label>
			      <div class="layui-input-inline">
			        <input type="tel"  name="idCardEnd" value="{{ d.idCardEnd ||"" }}" id="idCardEnd"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
		</tr>
		<tr class="layui-form-item">
			<td>
				 <div class="layui-inline">
			      <label class="layui-form-label">婚姻状况</label>
			      <div class="layui-input-inline">
					<select class="form-control marriage" name="marriage"><option value="0" {{ d.marriage==0?'selected':'' }}>已婚</option> <option value="1" {{ d.marriage==1?'selected':'' }}>未婚</option></select>
			      </div>
			    </div>
			</td>
			<td>
			    <div class="layui-inline">
			      <label class="layui-form-label">生育状况</label>
			      <div class="layui-input-inline">
					<select class="form-control procreate" name="procreate"><option value="0" {{ d.procreate==0?'selected':'' }}>已育</option> <option value="1" {{ d.procreate==1?'selected':'' }}>未育</option></select>
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">邮箱</label>
			      <div class="layui-input-inline">
			        <input type="text" name="email" value="{{ d.email==null ? "" : d.email}}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
		</tr>
		<tr class="layui-form-item">
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">所属银行</label>
			      <div class="layui-input-inline">
			        <input type="text" name="ascriptionBank1" readonly="readonly" id="ascriptionBank1" value="{{ d.ascriptionBank1==null ? "" : d.ascriptionBank1}}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">银行卡</label>
			      <div class="layui-input-inline">
			        <input type="tel"  name="bankCard1" id="bankCard1" value="{{ d.bankCard1==null ? "" : d.bankCard1}}"   autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">紧急联系人</label>
			      <div class="layui-input-inline">
			        <input type="text" name="contacts" value="{{ d.contacts==null ? "" : d.contacts}}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">紧急联系方式</label>
			      <div class="layui-input-inline">
			        <input type="text" name="information" value="{{ d.information==null ? "" : d.information}}"   autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
		</tr>
		<tr class="layui-form-item">
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">学历</label>
			      <div class="layui-input-inline">
					<select class="form-control education" name="education">
						<option value="本科" {{ d.education=='本科'?'selected':'' }}>本科</option>
						<option value="大专" {{ d.education=='大专'?'selected':'' }}>大专</option>
						<option value="高中" {{ d.education=='高中'?'selected':'' }}>高中</option>
						<option value="初中及以下" {{ d.education=='初中及以下'?'selected':'' }}>初中及以下</option>
					</select>
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">毕业学校</label>
			      <div class="layui-input-inline">
			        <input type="text" name="school" value="{{ d.school==null ? "" : d.school}}" autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">专业</label>
			      <div class="layui-input-inline">
			      	 <input type="text" name="major" value="{{ d.major==null ? "" : d.major}}"  autocomplete="off" class="layui-input">
				  </div>
			    </div>
			</td>
			<td>
			</td>
		</tr>
		<tr class="titleTr">
			<td colspan="4"><i class="layui-icon layui-icon-app"></i>公司信息</td>
		</tr>
		<tr class="layui-form-item">
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">打卡机编号</label>
			      <div class="layui-input-inline">
			        <input type="text" name="" value="{{ d.number }}" readonly="readonly"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">档案编号</label>
			      <div class="layui-input-inline">
			        <input type="text" name="lotionNumber" value="{{ d.lotionNumber}}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>		
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">签订单位</label>
			      <div class="layui-input-inline">
			        <input type="text" name="company" value="{{ d.company==null ? "" : d.company}}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">承诺书</label>
			      <div class="layui-input-inline">
					<select class="form-control promise" name="promise"><option value="0" {{ d.promise==0?'selected':'' }}>未签</option> <option value="1" {{ d.promise==1?'selected':'' }}>已签</option></select>
			      </div>
			    </div>
			</td>
		</tr>
		<tr class="layui-form-item">
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">工作状态</label>
			      <div class="layui-input-inline">
					<select class="form-control quit" lay-filter="lay_selecte" name="quit"><option value="0" {{ d.quit==0?'selected':'' }}>在职</option> <option value="1" {{ d.quit==1?'selected':'' }}>离职</option></select>
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">离职时间</label>
			      <div class="layui-input-inline">
			        <input type="text" name="quitDate" value="{{ d.quitDate || "" }}" id="quitDate"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">离职类型</label>
			      <div class="layui-input-inline" >
			        <select class="form-control" name="quitTypeId" id="quitType">
					</select>
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">离职理由</label>
			      <div class="layui-input-inline">
			        <input type="text" name="reason" value="{{ d.reason==null ? "" : d.reason}}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
		</tr>
		<tr class="layui-form-item">
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">合同</label>
			      <div class="layui-input-inline">
					<select class="form-control" name="commitment"><option value="0" {{ d.commitment==0?'selected':'' }}>未签</option><option value="1" {{ d.commitment==1?'selected':'' }}>已签</option><option value="2" {{ d.commitment==2?'selected':'' }}>续签</option></select>
			      </div>
			    </div>
			</td>
			<td>
				 <div class="layui-inline">
			      <label class="layui-form-label">合同签订时间</label>
			      <div class="layui-input-inline">
			        <input type="tel"  name="contractDate" value="{{ d.contractDate || "" }}" id="contractDate"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">合同到期时间</label>
			      <div class="layui-input-inline">
			        <input type="tel"  name="contractDateEnd" value="{{ d.contractDateEnd || "" }}" id="contractDateEnd"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
			    <div class="layui-inline">
			      <label class="layui-form-label">合同签订次数</label>
			      <div class="layui-input-inline">
			        <input type="text" name="frequency" value="{{ d.frequency==null ? "" : d.frequency}}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
		</tr>
		<tr class="layui-form-item">
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">入职时间</label>
			      <div class="layui-input-inline">
			        <input type="text" name="entry" id="entry" value="{{d.entry || ""}}" autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">预计转正时间</label>
			      <div class="layui-input-inline">
			        <input type="tel"  name="estimate" value="{{ d.estimate || "" }}" id="estimate"   autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
	 			<div class="layui-inline">
			      <label class="layui-form-label" >实际转正时间</label>
			      <div class="layui-input-inline">
			        <input type="tel"  name="actua" id="actua" value="{{ d.actua || "" }}" autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">健康证时间</label>
			      <div class="layui-input-inline">
			        <input type="text" name="healthCertificateTime" id="healthCertificateTimeInput" value="{{ d.healthCertificateTime || ""}}"  autocomplete="off" class="layui-input">
			      </div>
			    </div>	
			</td>
		</tr>
		<tr class="layui-form-item">
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">部门</label>
			      <div class="layui-input-inline" >
			        <select class="form-control" lay-search="true" lay-filter="lay_selecte2" name="orgNameId" id="orgNameId">
								
					</select>
			      </div>
			    </div>
			</td>
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">职位</label>
			      <div class="layui-input-inline" >
			        <select class="form-control" lay-search="true" name="positionId" id="positionId">
					</select>
			      </div>
			    </div>	
			</td>
			<td colspan="2">
				<div class="layui-inline" >
			      <label class="layui-form-label">协议</label>
			      <div class="layui-input-inline"  name="agreementId" id="agreementId" style="width: 285px;">
				  </div>
			    </div>
			</td>
			<td>
			    
			</td>
		</tr>
		<tr class="layui-form-item">
			<td>
				<div class="layui-inline">
			      <label class="layui-form-label">保险情况</label>
			      <div class="layui-input-inline">
					<select class="form-control safe" name="safe"><option value="0" {{ d.safe==0?'selected':'' }}>未缴</option> <option value="1" {{ d.safe==1?'selected':'' }}>已缴</option></select>
			      </div>
			    </div>
			</td>
			<td>
			   <div class="layui-inline">
			      <label class="layui-form-label">社保缴纳时间</label>
			      <div class="layui-input-inline">
			        <input type="text" name="socialSecurity" value="{{ d.socialSecurity ||"" }}" id="socialSecurity"  autocomplete="off" class="layui-input">
			      </div>
			    </div>
			</td>
			<td>
			</td>
			<td>
			</td>
		</tr>
		<tr class="layui-form-item">
			<td colspan="4">
		      <div class="layui-inline contractDiv">
		        <label class="layui-form-label">合同</label>
				<div class="layui-input-block"  id="commitmentId" style="width: 874px;">
				</div>
		      </div>	
			</td>
		</tr>
	</table>
</div>


		    
			
</script>
<!-- 表格工具栏模板 -->
<script type="text/html" id="barDemo">
	<span class="layui-btn layui-btn-trans layui-btn-xs layui-bg-green"  lay-event="addbatch">员工详情</span>
</script>

<script type="text/html" id="recruitToolbar">
<div class="layui-btn-container layui-inline">
	<span class="layui-btn layui-btn-sm" lay-event="userInfo">员工信息</span>
	<span class="layui-btn layui-btn-trans layui-btn-sm layui-bg-red"  lay-event="deleteSome">删除</span>
</div>
</script>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).define(
	['tablePlug','laydate','upload'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug
		,upload = layui.upload;
		
		laydate.render({
			elem: '#startTime',
			type: 'date',
			range: '~',
		});
		
 		$.ajax({
		  url:"${ctx}/basedata/list",
		  data:{type:"orgName"},
		  type:"GET",
		  async:false,
 		  success: function (result) {
			  var htmls='<option value="">请选择</option>';	
			  $(result.data).each(function(index,item){
				htmls+='<option value="'+item.id+'">'+item.name+'</option>'
				$("#orgName").html(htmls);
				form.render();
			  });
	      }
		});
 		$.ajax({
			url:'${ctx}/basedata/list',
			data:{type:"commitments"},		
			success:function(result){
				var htmls='<option value="">请选择</option>';	
				$(result.data).each(function(index,item){
	  				htmls+='<option value="'+item.id+'">'+item.name+'</option>'
	  				$("#commitments").html(htmls);
	  				form.render();
  			    });
			}
		})
	    $.ajax({
		      url:"${ctx}/basedata/list",
		      data:{type:'agreements'},
		      type:"GET",
		      async:false,
      		  success: function (result) {
      			  var htmls='<option value="">请选择</option>';
      			  $(result.data).each(function(index,item){
      				htmls+='<option value="'+item.id+'">'+item.name+'</option>'
      			  });
      			  $("#agreementsSelect").html(htmls);
      			  form.render();
		      }
		});
		var allPlatform = [];
	 	tablePlug.smartReload.enable(true);  
	 	table.render({
			elem:'#recruitTable',
			url:'${ctx}/system/user/pages',
			toolbar:'#recruitToolbar',
			loading:true,
			page:true,
			limit:14,
			limits:[14,20,50,100,500,1000],
			smartReloadModel: true,    // 开启智能重载
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[{align: 'center',type: 'checkbox', },
			       {align:'center', title:'位置编号',field:'lotionNumber', edit: false},
			       {align:'center', title:'姓名',   field:'userName'},
			       {align:'center', title:'手机号', width:150,	field:'phone',edit: false,	},
			       {align:'center', title:'年龄',   field:'age',	  edit: false,templet:function(d){return d.age==null ? "" : d.age}},
			       {align:'center',title: "合同", field:"commitment",templet: function(d){if(d.commitment==0){return "未签"}else if (d.commitment==1){return "已签"} else if (d.commitment==2){return "续签"} else {return ""}}},
			       {align:'center',field: "positionId",title: "承诺书",templet: function(d){if(d.promise==0){return "未签"}else if(d.promise==1){return "已签"}else{return ""}}},
			       {align:'center', title:'保险',field:'safe',templet: function(d){if(d.safe==0){return "未缴"}else if(d.safe==1){return "已缴"}else{return ""}}},
			       {align:'center', title:'合同到期时间',field:'contractDateEnd',templet:function(d){return (/\d{4}-\d{1,2}-\d{1,2}/g.exec(d.contractDateEnd)==null ? "" :  /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.contractDateEnd))}}, 
			       {align:'center', title:'预计转正时间',field:'estimate',templet:function(d){return (/\d{4}-\d{1,2}-\d{1,2}/g.exec(d.estimate)==null ? "" :  /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.estimate))}}, 
			       {align:'center', title:'入职时间',field:'entry',templet:function(d){return (/\d{4}-\d{1,2}-\d{1,2}/g.exec(d.entry)==null ? "" :  /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.entry))}}, 
			       {align:'center', title:'部门',field:'orgName',templet:function(d){return (d.orgName==null ? "" : d.orgName.name)}},
			       {align:'center', title:'是否在职',field:'orgName',templet:function(d){return (d.quit==0 ? "在职" : "离职")}},
			       {align:'center', width:'14%', title:'操作',toolbar: '#barDemo'},
			       ]],
		})
		table.on('tool(recruitTable)', function(obj) {
			var data=obj.data;
			switch(obj.event){
			case 'addbatch': addbatch(data);
				break;
			}
		})
		function addbatch(data){
			var id=data.id;
			var tpl=addEditTpl.innerHTML;
			var html="";
			laytpl(tpl).render(data,function(h){
				html=h;
     		})
     		var htmls='<option value="">请选择</option>';	
     		$.ajax({
			  url:"${ctx}/basedata/list",
			  data:{type:"quitType"},
			  type:"GET",
			  async:false,
	 		  success: function (result) {
	  			  $(result.data).each(function(index,item){
	  				  var select="";
	  				  if(item.id==data.quitTypeId){select="selected"}
	  					htmls+='<option value="'+item.id+'"  '+select+'>'+item.name+'</option>'
	  			  });
		      }
			});
     		var agreementId=data.agreementId.split(',')
     		var htmlth='';
     		$.ajax({
			      url:"${ctx}/basedata/list",
			      data:{type:"agreements"},
			      type:"GET",
			      async:false,
			      beforeSend:function(){
			    	  indextwo = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					  });
				  }, 
	      		  success: function (result) {
	      			  $(result.data).each(function(k,j){
	      				var checks=" ";	
	      				for (var i = 0; i < agreementId.length; i++) {
	      				  if(j.id==agreementId[i]){
	      					  checks="checked"
	      				  } 
						}
	      				htmlth+='<input type="checkbox" class="agreementId" name="agreementId" '+checks+' lay-skin="primary" value="'+j.id+'" title='+j.name+' >'
	      			  });
	      			layer.close(indextwo);
			      }
			  });
     		
     		var htmlst= '<option value="">请选择</option>';
		    $.ajax({
			      url:"${ctx}/basedata/list",
			      data:{type:"orgName"},
			      type:"GET",
			      async:false,
			      beforeSend:function(){
			    	  indextwo = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					  });
				  }, 
	      		  success: function (result) {
	      			  $(result.data).each(function(k,j){
	      				  var select="";
	      				 if(j.id==(data.orgName==null ? "" : data.orgName.id)){select="selected"}
	      				htmlst +='<option value="'+j.id+'" '+select+'>'+j.name+'</option>'
	      			  });
	      			layer.close(indextwo);
			      }
			  });
		    
		    var htmll='<option value="">请选择</option>';
		    if(data.orgName){
			    $.ajax({
				      url:"${ctx}/basedata/children",
				      data:{id: data.orgName.id},
				      type:"GET",
				      async:false,
				      beforeSend:function(){
				    	  indextwo = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			  $(result.data).each(function(k,j){
		      				  var select="";
		      				 if(j.id==(data.position==null ? "" : data.position.id)){select="selected"}
		      				htmll +='<option value="'+j.id+'" '+select+'>'+j.name+'</option>'
		      			  });
		      			layer.close(indextwo);
				      }
				  });
		    }
		     form.on('select(lay_selecte2)', function(data){
	      			$.ajax({								//获取当前部门下拉框选择的子数据：职位
					      url:"${ctx}/basedata/children",
					      data:{ id:data.value },
					      async:false,
			      		  success: function (result) {				//填充职位下拉框
		                  var htmlls='<option value="">请选择</option>'
			      			  	$(result.data).each(function(i,o){
			      			  		htmlls +='<option  value="'+o.id+'">'+o.name+'</option>'
			      				});
			      			  $("#positionId").html(htmlls);
					     		 form.render();
					      }
					  });
			 }) 
     		var htmltt='';
     		$.ajax({
			      url:"${ctx}/basedata/list",
			      data:{type:"commitments"},
			      type:"GET",
			      async:false,
			      beforeSend:function(){
			    	  indextwo = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					  });
				  }, 
	      		  success: function (result) {
	      			  $(result.data).each(function(k,j){
	      				var checks=" ";	
	      				  if(j.id==(data.commitments==null ? "" : data.commitments.id)){
	      					  checks="checked"
	      				  } 
	      				htmltt+='<input type="radio" class="commitments" name="commitmentId"   '+checks+' lay-skin="primary" value="'+j.id+'" title='+j.name+' >'
	      			  });
	      			layer.close(indextwo);
			      }
			  });
     		
     		 var pictureUrl=data.pictureUrl;	
			var index=layer.open({
				type:1,
				title:data.userName+'个人信息',
				area:['90%','95%'],
				btn:['确认','取消'],
				content:html,
				id: 'LAY_layuipro6' ,
				btnAlign: 'c',
			    moveType: 1, //拖拽模式，0或者1
				success : function(layero, index) {
					$("#quitType").html(htmls);
					$("#agreementId").html(htmlth);
					$("#commitmentId").html(htmltt);
					$("#orgNameId").html(htmlst);
					$("#positionId").html(htmll);
					laydate.render({ elem:'#actua', type:'datetime', });
					laydate.render({ elem:'#entry', type:'datetime', });
					laydate.render({ elem:'#estimate', type:'datetime',  });
					laydate.render({ elem:'#socialSecurity', type:'datetime',  });
					laydate.render({ elem:'#contractDate', type:'datetime',  });
					laydate.render({ elem:'#idCardEnd', type:'datetime',  });
					laydate.render({ elem:'#quitDate', type:'datetime',  });
					laydate.render({ elem:'#contractDateEnd', type:'datetime',  }); 
					laydate.render({ elem:'#healthCertificateTimeInput', type:'datetime', });
		        	layero.addClass('layui-form');
		        	
		        	$("#bankCard1").blur(function(){
		 				var idCard=$("#bankCard1").val();
		 				$.ajax({
		 				url:'${ctx}/system/user/getbank',
		 				type: 'GET',
		 				data: {idCard:idCard},
		 				async: false,
		 				success: function(r){
		 					if(r.code==0){
		 						$("#ascriptionBank1").val(r.data)
		 					}
		 				}
		 			})
		 			})
		        	
		 			$("#idCard").blur(function(){
						var UUserCard = $("#idCard").val();
						if(UUserCard != null && UUserCard != ''){
							//获取出生日期 
							var birthday = UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14); 
							$('#birthDate').val(birthday+' '+'00:00:00');
							//获取性别 
							if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
								$("#gender").val(0);
								form.render();
							}else { 
								$("#gender").val(1);
								form.render();
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
		 			
					//普通图片上传
		  var uploadInst = upload.render({
		    elem: '#demo1'
		    ,url: 'upload?filesTypeId='+360
		    ,before: function(obj){
		      //预读本地文件示例，不支持ie8
		      obj.preview(function(index, file, result){
		        $('#demo1').attr('src', result); //图片链接（base64）
		      });
		    }
		    ,done: function(res){
		      //如果上传失败
		      if(res.code==0){
		    	  pictureUrl=res.data.url
		      }
		      if(res.code > 0){
		        return layer.msg('上传失败');
		      }
		      //上传成功
		    }
		    ,error: function(){
		      //演示失败状态，并实现重传
		      var demoText = $('#demoText');
		      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
		      demoText.find('.demo-reload').on('click', function(){
		        uploadInst.upload();
		      });
		    }
		  });
					// 将保存按钮改变成提交按钮
					layero.find('.layui-layer-btn0').attr({
						'lay-filter' : 'addRole',
						'lay-submit' : ''
					})
		        },
				yes:function(){
					form.on('submit(addRole)', function(data) {
						var values=new Array()
						var numberr=new Array()
						$(".agreementId:checked").each(function() {   
							values.push($(this).val());
						});
						data.field.id=id
						data.field.agreementId=values
						data.field.pictureUrl=pictureUrl
						if(data.field.quit==1 &&  data.field.quitTypeId==""){
							return	layer.msg("请填写离职类型", {icon: 2});
						}
						if(data.field.quit==1 &&  data.field.quitDate==""){
							return	layer.msg("请填写离职时间", {icon: 2});
						}
						$.ajax({
							url:"${ctx}/system/user/update",
							data:data.field,
							type:"POST",
							traditional: true,
							success:function(result){
								if(0==result.code){
									layer.msg("修改成功！", {icon: 1});
									table.reload('recruitTable')
								}else if (2==result.code && $(".quit").val()!=1) {
									var init=layer.open({
										   title: '提示'
										  ,content:'信息修改成功，但该员工没有初始化设定,是否添加？'
										  ,btn: ['确认', '取消']
										,yes: function(index, layero){
											//window.location.href = "${ctx}/menusToUrl?url=personnel/init"
											//此处是修改员工后的询问
												window.parent.document.getElementById("personnelInit").click();
												layer.close(init); 								   
											}
										}); 
								}else if($(".quit").val()==1){
									layer.msg(result.message, {icon: 1});
								}else{
									layer.msg(result.message, {icon: 2});
								}
								table.reload('recruitTable');
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
			        	layui.form.render();
					})
				},end:function(){ 
		        	layui.form.render();
				  }
			})
			form.render();
		}
	 	form.on('select(lay_selecte)', function(data) {
	 		if(data.value==1){
	 			lock = false;
	 			 var ind=layer.open({
					   title: '提示'
					  ,content:'确定删除考勤机上的指纹吗？'
					  ,btn: ['确认', '取消']
					  ,yes: function(index, layero){
						layer.close(ind);
						lock = true;
		       		   },
		       		   end:function(){
		       			   if(!lock)
		       				   return;
						var loading = layer.load(1,{shade: [0.5,'black'], });
		       			var arr = ['192.168.1.204','192.168.7.123','192.168.1.205','192.168.14.201','192.168.6.73'];
		       			var suceess = 0,error=0,i=0;
		       			var postData={
							number:$('.number').val(),
							address:arr[i],
						};
		       			del();
		       			function del(){
		       				postData.address = arr[i];
			       			$.ajax({
								url:"${ctx}/personnel/deleteUser",
								data:postData,
								async: true,
								type:"GET",
								success:function(result){
									suceess++;
									i++;
									if(arr.length==(suceess+error))
										layer.close(loading);
									else{
										del();
									}
								},error:function(){
									error++;
									i++;
									if(arr.length==(suceess+error))
										layer.close(loading);
									else{
										del();
									}
									layer.msg("操作失败！", {icon: 2});
								}
							});
		       			}
		       		   }
					}); 
	 		}
	 	})
	 	table.on('toolbar(recruitTable)',function(obj){
			var config = obj.config;
			var btnElem = $(this);
			var tableId = config.id;
			switch(obj.event){
			case 'userInfo': userInfo();
				break;
			/* case 'export': 
					var a="";
					if($("#startTime").val()!=""){
						var orderTime=$("#startTime").val().split('~');
						a=orderTime[0]+' '+'00:00:00';
					}	
						location.href="${ctx}/excel/importExcel/retire?orderTimeBegin="+a+"";
			break; */
			case 'deleteSome':
				var checkedIds = tablePlug.tableCheck.getChecked(tableId);
				var index;
				 index = layer.confirm('<div>输入密码:<input id="password"  class="layui-input" /></div>', {btn: ['确定', '取消']},function(){
					 if($("#password").val()==3116){
						 $.ajax({
							url:"${ctx}/system/user/deleteUser",
							data:{id:checkedIds},
							traditional: true,
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
							},
							success:function(result){
								if(0==result.code){
								layer.msg(result.message, {icon: 1});
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
				break;
			}
		})
		function userInfo(){
	 		layer.open({
				title:'员工信息 ',
				type:1,
				shadeClose:true,
				area:['1000px','80%'],
				content:$('#userin'),
				end:function(){
					$('#userin').hide();		//在弹窗销毁后，隐藏弹窗内容DIV
				}
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
	      			var html,htmlh,htmlh1;
	      			 $(result.data.userBirth).each(function(i,o){
	      				html +='<tr>'
	      				+'<td class="text-center edit username2" event="setSign" data-id="'+o.userId+'">'+o.username+'</td>'
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
	      			var htmlHealth="";
	      			$(result.data.healthCertificateTime).each(function(i,o){
	      				htmlh1 +='<tr>'
	      				+'<td class="text-center edit username2" data-id="'+o.userId+'">'+o.username+'</td>'
	      				+'<td class="text-center edit price">'+o.healthCertificateTime+'</td></tr>'
	      			});
	      			$("#healthCertificateTimeTable").html(htmlHealth);
	      			tips();//调用点击事件
	      			layer.close(index);
			      },error:function(){
					layer.msg("加载失败！", {icon: 2});
					layer.close(index);
				  }
			  }); 
	 	}
	 	userInfo();
	 	function tips(){
	 		$('.username2').on('click',function(){
	 			var id=$(this).data('id');
	 			$.ajax({
				      url:"${ctx}/system/user/pages",
				      data:{id:id},
				      type:"GET",
				      async:false,
		      		  success: function (result) {
		      			addbatch(result.data.rows[0]); 
		      		  }
	 			})
	 		})
	 	}
	 	
	 	form.on('submit(LAY-search)', function(data) {
	 		var field = data.field;
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
			if(field.retire!="" && field.gender==""){
				return layer.msg("筛选退休返聘 请先选择性别",{icon:2});
			}
			field.entry=entry;
			field.estimate=estimate;
			field.actua=actua;
			var orderTime=$("#startTime").val().split('~');
			if($("#startTime").val()!=""){
			field.orderTimeBegin=orderTime[0]+' '+'00:00:00';
			field.orderTimeEnd=orderTime[1]+' '+'23:59:59';	
			}
	 		table.reload('recruitTable', {
				where: field,
				page: {
					curr:1
                }
			});
	 	})
	 	$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	 	//待转正人员
	 	/* function openSpecialWin(){					//打开特急人员弹窗
			var specialWin=layer.open({
				title:'待转正人员 ',
				type:1,
				area:['50%','80%'],
				content:$('#specialWinDiv'),
				end:function(){
					$('#specialWinDiv').hide();		//在弹窗销毁后，隐藏弹窗内容DIV
				}
			})
			table.render({
				elem:'#specialTable',
				toolbar:'#specialTableToolbar',
				url:'${ctx}/system/user/getPositiveUser',
				page:false,
				loading:true,
				parseData:function(ret){
					return{ code:ret.code, data:ret.data, }
				},
				cols:[[
				       {align:'center',type:'checkbox'},
				       {align:'center',field:'userName',title:'姓名'},
				       {align:'center',field:'type',	title:'类型',	templet:'#typeTpl'},
				       {align:'center',field:'phone',	title:'手机',	edit:true,},
				       ]]		
			})
			table.on('edit(specialTable)',function(obj){
				var load=layer.load(1);
			 	$.ajax({
					url:'${ctx}/system/user/update',
					type:'post',
					data:{	id:obj.data.id,
						  	phone:obj.data.phone,
						  	positive:'true'},
					success:function(r){
						if(2==r.code){
							layer.msg(r.message,{icon:1});
						}else
							layer.msg(r.message,{icon:2});
						layer.close(load);
					},
					error:function(){
						layer.msg('ajax异常',{icon:2});
						layer.close(load);
					}
				}) 
			})
			table.on('toolbar(specialTable)',function(obj){	
				switch(obj.event){
				case 'becomeFull':becomeFull(); break; 
				}
			})
			function becomeFull(){
				var choosed = layui.table.checkStatus('specialTable').data;
				if(choosed.length<1){
					layer.msg('请选择人员',{icon:2});
					return;
				}
				if(choosed.length>1){
					layer.msg('不能同时转正多名人员',{icon:2});
					return;
				}
				if(choosed[0].phone==''){
					layer.msg('转正人员需要填写号码！',{icon:2});
					return;
				}
				layer.confirm('是否确认转正？',function(){
					var positiveUser='';
					for(var i=0;i<choosed.length;i++){
						positiveUser+=(choosed[i].id+',');
					}
					var load=layer.load(1);
					$.ajax({
						url:'${ctx}/system/user/positiveUser?positiveUser='+positiveUser,
						success:function(result){
							if(0==result.code){
								positiveNumber--;
								 $('#lookoverBecome').html('待转正人员 <span class="layui-badge">'+positiveNumber+'</span></li>')
								table.reload('specialTable');
								$('#openEditBtn').data('id',choosed[0].id);
								$('#openEditBtn').click();
								layer.close(load);
								layer.msg(result.message,{icon:1});
							}else{
								layer.msg(result.message,{icon:2});
							}
							layer.close(load);
						}
					})
				})
			}
		} */
		
	}//end define function
)//endedefine
</script>
</html>