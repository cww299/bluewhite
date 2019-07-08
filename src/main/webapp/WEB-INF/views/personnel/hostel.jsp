<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/linkSelect/cyStyle.css" media="all">
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/linkSelect/cyType.css" media="all">
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/linkSelect/font-awesome.min.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
<script src="${ctx }/static/layui-v2.4.5/linkSelect/transferTool.js"></script>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>住宿管理</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>

<body>
	<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							
							<td>宿舍:</td>
							<td ><select class="form-control" lay-search="true" name="id" id="hostelName"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
			
			<shiro:hasAnyRoles name="superAdmin,personnel">
   				 <p id="totalAll" style="text-align:center;color:red;"></p>
			</shiro:hasAnyRoles> 
			<!-- 宿舍人员详情 -->
			<div style="display: none;" id="layuiOpen">
			<table id="tableBudget" class="table_th_search" lay-filter="tableBudget"></table>
			</div>
			
			<!-- 水电详情 -->
			<div style="display: none;" id="layuipower">
			<table id="powerWater" class="table_th_search" lay-filter="powerWater"></table>
			</div>
			
			<!-- 固定资产详情 -->
			<div style="display: none;" id="Fixed">
			<table id="Fixede" class="table_th_search" lay-filter="Fixed"></table>
			</div>
			
			<!-- 其他费用详情 -->
			<div style="display: none;" id="sundrye">
			<table id="sundry" class="table_th_search" lay-filter="sundry"></table>
			</div>
			
			<!-- 总电费 -->
			<div style="display: none;" id="summ">
			<table id="summary" class="table_th_search" lay-filter="summary"></table>
			</div>
			
			<!-- 总水费 -->
			<div style="display: none;" id="summpo">
			<table id="summaryer" class="table_th_search" lay-filter="summaryer"></table>
			</div>
		</div>
	</div>
	
			<div style="display: none;" id="layuiShare">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>分摊月份:</td>
							<td><input id="monthDate3" style="width: 180px;" name="monthDate" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>宿舍:</td>
							<td ><select class="form-control" lay-search="true" name="hostelId" id="hostelNames"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>部门:</td>
							<td ><select class="form-control" lay-search="true" name="orgNameId" id="orgName"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-search2">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="layuiShare2"  class="table_th_search" lay-filter="layuiShare"></table>
			</div>
	
	
	<div style="display: none;" id="layuiShare6">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>分摊月份:</td>
							<td><input id="monthDate4" style="width: 180px;" name="monthDate" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>宿舍:</td>
							<td ><select class="form-control" lay-search="true" name="hostelId" id="hostelNames6"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>部门:</td>
							<td ><select class="form-control" lay-search="true" name="orgNameId" id="orgName6"></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-search6">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="layuiShareser"  class="table_th_search" lay-filter="layuiShare5"></table>
			</div>
	
	
	<form action="" id="layuiadmin-form-admin"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" name="id" id="ids" style="display:none;">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">每吨水费价格</label>
				<div class="layui-input-inline">
					<input type="text"  name="keyValue" id="keyValue"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">水费标准</label>
				<div class="layui-input-inline">
					<input type="text" name="keyValueTwo" id="keyValueTwo"
						lay-verify="required"
						class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>	
	<form action="" id="layuiadmin-form-admin6"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" name="id" id="ids2" style="display:none;">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">每度电费价格</label>
				<div class="layui-input-inline">
					<input type="text"  name="keyValue" id="keyValue2"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">电费标准</label>
				<div class="layui-input-inline">
					<input type="text" name="keyValueTwo" id="keyValueTwo2"
						lay-verify="required"
						class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>	
	
	<form action="" id="layuiadmin-form-admin2"style=" display:none;  text-align:center;">
		<div class="layui-input-normal layui-form">
		<input type="text" name="id" id="hostelId" style="display: none;"><!--阻止回车键提交表单   如果form里面只有一个input type＝text，那么无论有没有submit按钮，在input中回车都会提交表单。
     如果不想回车提交，需要再加一个input type=text，然后设置display:none.  -->
    	<div cyType="transferTool" cyProps="url:'${ctx}/system/user/findUserList?foreigns=0'"  data_value="10,12,11"  id="divID" name="province[]" ></div>
		</div>
	</form>
	
	
	<form action="" id="layuiadmin-form-admin3"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">宿舍名</label>
				<div class="layui-input-inline">
					<input type="text"  name="name"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>
	<!-- 水费 -->
	<form action="" id="layuiadmin-form-admin4"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" id="water" name="hostelId" style="display: none;" />
		<input type="text" name="type" value="1" style="display: none;" />
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">所属月份</label>
				<div class="layui-input-inline">
					<input type="text" name="monthDate" id="monthDate"
						lay-verify="required" placeholder="请输入所属月份"
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">上月吨数</label>
				<div class="layui-input-inline">
					<input type="text"  name="upperDegreeNum" id="nowDegreeNumss"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">本月吨数</label>
				<div class="layui-input-inline">
					<input type="text"  name="nowDegreeNum"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>
	
	<!-- 水费 -->
	<form action="" id="layuiadmin-form-admin5"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" id="power" name="hostelId" style="display: none;" />
		<input type="text" name="type" value="2" style="display: none;" />
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">所属月份</label>
				<div class="layui-input-inline">
					<input type="text" name="monthDate" id="monthDate2"
						lay-verify="required" placeholder="请输入所属月份"
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">上月度数</label>
				<div class="layui-input-inline">
					<input type="text"  name="upperDegreeNum" id="nowDegreeNumss2"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">本月度数</label>
				<div class="layui-input-inline">
					<input type="text"  name="nowDegreeNum"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>
	
	<!-- 其他费用-->
	<form action="" id="layuiadmin-form-admin8"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" id="sundrys" name="hostelId" style="display: none;" />
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">所属月份</label>
				<div class="layui-input-inline">
					<input type="text" name="monthDate" id="monthDate7"
						lay-verify="required" placeholder="请输入所属月份"
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">房租费用</label>
				<div class="layui-input-inline">
					<input type="text"  name="rent" 
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">煤气费</label>
				<div class="layui-input-inline">
					<input type="text"  name="coal"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">宽带费</label>
				<div class="layui-input-inline">
					<input type="text"  name="broadband"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">当月安检管理费</label>
				<div class="layui-input-inline">
					<input type="text"  name="Administration"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>
	
	<!-- 固定资产-->
	<form action="" id="layuiadmin-form-admin7"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" id="fixeds" name="hostelId" style="display: none;" />
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">所属月份</label>
				<div class="layui-input-inline">
					<input type="text" name="monthDate" id="monthDate5"
						lay-verify="required" placeholder="请输入所属月份"
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">名称</label>
				<div class="layui-input-inline">
					<input type="text"  name="name" id="nowDegreeNumss2"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">价值</label>
				<div class="layui-input-inline">
					<input type="number"  name="sum"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">平摊多少个月</label>
				<div class="layui-input-inline">
					<select name="branch" lay-filter="branch" lay-search="true">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
					</select>
				</div>
			</div>
		</div>
	</form>
	
	
	<!-- 总水电 -->
	<form action="" id="layuiadmin-form-admin9"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" name="type" value="2" style="display: none;" />
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">所属月份</label>
				<div class="layui-input-inline">
					<input type="text" name="monthDate" id="monthDate10"
						lay-verify="required" placeholder="请输入所属月份"
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">二楼总表上月</label>
				<div class="layui-input-inline">
					<input type="text" id="ddds1" name="oneNowNum" 
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">二楼总表当月</label>
				<div class="layui-input-inline">
					<input type="text"  name="oneUpperNum"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">三楼总表上月</label>
				<div class="layui-input-inline">
					<input type="text" id="ddds2" name="twoNowNum" 
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">三楼总表当月</label>
				<div class="layui-input-inline">
					<input type="text"  name="twoUpperNum"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">四楼总表上月</label>
				<div class="layui-input-inline">
					<input type="text" id="ddds3" name="threeNowNum" 
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">四楼总表当月</label>
				<div class="layui-input-inline">
					<input type="text"  name="threeUpperNum"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">损耗电量</label>
				<div class="layui-input-inline">
					<input type="text"  name="loss"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">（按面积比例核算）</label>
				<div class="layui-input-inline">
					<input type="text"  name="buse"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">（铜铁损耗）</label>
				<div class="layui-input-inline">
					<input type="text"  name="copper"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">个体之间的损耗</label>
				<div class="layui-input-inline">
					<input type="text"  name="individual"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>
	
	<!-- 总水费 -->
	<form action="" id="layuiadmin-form-admin11"
		style="padding: 20px 30px 0 60px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin">
		<input type="text" name="type" value="1" style="display: none;" />
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">所属月份</label>
				<div class="layui-input-inline">
					<input type="text" name="monthDate" id="monthDate11"
						lay-verify="required" placeholder="请输入所属月份"
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">消防电量</label>
				<div class="layui-input-inline">
					<input type="text"  name="loss"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">耗损水量</label>
				<div class="layui-input-inline">
					<input type="text"  name="buse"
						lay-verify="required" 
						class="layui-input laydate-icon">
				</div>
			</div>
		</div>
	</form>
	
	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增宿舍</span>
				<span class="layui-btn layui-btn-sm" lay-event="openMeal">水费标准</span>
				<span class="layui-btn layui-btn-sm" lay-event="openMea2">电费费标准</span>
				<span class="layui-btn layui-btn-sm" lay-event="openuser">人员分摊</span>
				<span class="layui-btn layui-btn-sm" lay-event="department">部门分摊</span>
				<span class="layui-btn layui-btn-sm" lay-event="summary">总电费</span>
				<span class="layui-btn layui-btn-sm" lay-event="summaryery">总水费</span>
			</div>
	</script>
	
	<script type="text/html" id="tool2">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData10">新增总电费</span>
			</div>
	</script>
	
	<script type="text/html" id="tool3">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData11">新增总水费</span>
			</div>
	</script>
	<script type="text/html" id="toolbar5">
			<div class="layui-btn-container layui-inline">
				
			</div>
	</script>
	<script type="text/html" id="toolbarwater">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增水费</span>
			</div>
	</script>
	
	<script type="text/html" id="toolbarpower">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData2">新增电费</span>
			</div>
	</script>
	
	<script type="text/html" id="toolfixed">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData3">新增固定资产</span>
			</div>
	</script>
	
	<script type="text/html" id="toolsundry">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="addTempData4">新增其他费用</span>
			</div>
	</script>
	
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-trans layui-btn-sm"  lay-event="update">人员</a>
	</script>
	
	<script type="text/html" id="barDemo2">
  		<a class="layui-btn layui-btn-trans layui-btn-sm"  lay-event="live">宿舍人员详情</a>
	</script>
	
	<script type="text/html" id="barDemo3">
  		<a class="layui-btn layui-btn-trans layui-btn-sm"  lay-event="water">水费详情</a>
	</script>
	
	<script type="text/html" id="barDemo4">
  		<a class="layui-btn layui-btn-trans layui-btn-sm"  lay-event="power">电费详情</a>
	</script>
	<script type="text/html" id="barDemo5">
  		<a class="layui-btn layui-btn-trans layui-btn-sm"  lay-event="fixed">固定资产</a>
	</script>
	<script type="text/html" id="barDemo6">
  		<a class="layui-btn layui-btn-trans layui-btn-sm"  lay-event="sundry">其他费用</a>
	</script>
	<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug',
				linkSelect:'linkSelect/linkSelect'
			}).define(
				['tablePlug', 'laydate','element','linkSelect'],
				function() {
					var $ = layui.jquery
						,layer = layui.layer //弹层
						,form = layui.form //表单
						,table = layui.table //表格
						,laydate = layui.laydate //日期控件
						,tablePlug = layui.tablePlug //表格插件
						,element = layui.element
						,linkSelect = layui.linkSelect;
					//全部字段
					var allField;
					var self = this;
					this.setIndex = function(index){
				  		_index=index;
				  	}
				  	
				  	this.getIndex = function(){
				  		return _index;
				  	}
				  	
				  	
				  	
					//select全局变量
					var htmls = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
						range: '~',
					});
				 	
					laydate.render({
						elem: '#monthDate',
						type : 'month',
						format:'yyyy-MM-01 HH:mm:ss'
					});
					laydate.render({
						elem: '#monthDate2',
						type : 'month',
						format:'yyyy-MM-01 HH:mm:ss'
					});
					laydate.render({
						elem: '#monthDate3',
						type : 'month',
						format:'yyyy-MM-01 HH:mm:ss'
					});
					laydate.render({
						elem: '#monthDate5',
						type : 'month',
						format:'yyyy-MM-01 HH:mm:ss'
					});
					laydate.render({
						elem: '#monthDate7',
						type : 'month',
						format:'yyyy-MM-01 HH:mm:ss'
					});
					laydate.render({
						elem: '#monthDate4',
						type : 'month',
						format:'yyyy-MM-01 HH:mm:ss'
					});
					laydate.render({
						elem: '#monthDate10',
						type : 'month',
						format:'yyyy-MM-01 HH:mm:ss'
					});
					laydate.render({
						elem: '#monthDate11',
						type : 'month',
						format:'yyyy-MM-01 HH:mm:ss'
					});
				  // 多选
				  laydate.render({
				    elem: '#tradeDaysTime',
				    type: 'date',
				    range: '~',
				  });
				
					
					
					var htmlfrn= '<option value="">请选择</option>';
				    $.ajax({
					      url:"${ctx}/personnel/getHostel",
					      
					      type:"GET",
					      async:false,
					      beforeSend:function(){
					    	  indextwo = layer.load(1, {
							  shade: [0.1,'#fff'] //0.1透明度的白色背景
							  });
						  }, 
			      		  success: function (result) {
			      			  $(result.data.rows).each(function(k,j){
			      				htmlfrn +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });
			      			$("#hostelName").html(htmlfrn);
			      			$("#hostelNames").html(htmlfrn);
			      			$("#hostelNames6").html(htmlfrn);
			      			layer.close(indextwo);
					      }
					  });
					
				    var getdataa={type:"orgName",}
					var htmlfrn= '<option value="">请选择</option>';
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
			      			$("#orgName").append(htmlfrn);
			      			$("#orgName6").append(htmlfrn);
			      			form.render();
			      			layer.close(indextwo);
					      }
					  });
					
					
					// 处理操作列
					var fn1 = function(field) {
						return function(d) {
							return [
								'<select name="selectOne" lay-filter="lay_selecte" lay-search="true" data-value="' + d.userId + '">' +
								htmls +
								'</select>'
							].join('');

						};
					};

					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						url: '${ctx}/personnel/getHostel' ,
						where:{
							type:1
						},
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
						},//开启分页
						loading: true,
						toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						//totalRow: true,		 //开启合计行 */
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								count:ret.data.total,
								data: ret.data.rows
							}
						},
						cols: [
							[{
								type: 'checkbox',
								align: 'center',
								fixed: 'left'
							},{
								field: "name",
								title: "宿舍名",
								align: 'center',
								filter:true,
								edit: false,
							}
							,{fixed:'right', title:'宿舍详情', align: 'center', toolbar: '#barDemo2'}
							,{fixed:'right', title:'水费详情', align: 'center', toolbar: '#barDemo3'}
							,{fixed:'right', title:'电费详情', align: 'center', toolbar: '#barDemo4'}
							,{fixed:'right', title:'固定资产', align: 'center', toolbar: '#barDemo5'}
							,{fixed:'right', title:'其他费用', align: 'center', toolbar: '#barDemo6'}
							,{fixed:'right', title:'人员详情', align: 'center', toolbar: '#barDemo'}]
						],
								});
					
					
					
					
					
					
					
					// 监听表格中的下拉选择将数据同步到table.cache中
					form.on('select(lay_selecte)', function(data) {
						var selectElem = $(data.elem);
						var tdElem = selectElem.closest('td');
						var trElem = tdElem.closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						var field = tdElem.data('field');
						table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
						var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
						var postData = {
							id: id,
							[field]:data.value
						}
						//调用新增修改
						mainJs.fUpdate(postData);
					});
					
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData':
								var	dicDiv=$("#layuiadmin-form-admin3");
								layer.open({
									type:1,
									title:'新增宿舍',
									shadeClose:true,
									area:['45%','30%'],
									btn:['确认','取消'],
									content:dicDiv,
									id: 'LAY_layuipro' ,
									btnAlign: 'c',
								    moveType: 1, //拖拽模式，0或者1
									success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        },
									yes:function(){
										form.on('submit(addRole)', function(data) {
											mainJs.fAdd(data.field)
											document.getElementById("layuiadmin-form-admin3").reset();
								        	layui.form.render();
										})
									},end:function(){ 
							        	$('#fightDiv').html("");
									  }
								})
								break;
							case 'openuser':	
								table.reload("layuiShare2") 
								var dicDiv=$('#layuiShare');
								layer.open({
							         type: 1
							        ,title: '人员分摊' //不显示标题栏
							        ,closeBtn: false
							        ,zindex:-1
							        ,area:['50%', '90%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro2' //设定一个id，防止重复弹出
							        ,btn: ['取消']
							        ,btnAlign: 'c'
							        ,moveType: 1 //拖拽模式，0或者1
							        ,content:dicDiv
							        ,success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole2',
											'lay-submit' : ''
										})
							        }
							        ,end:function(){
							        	$("#layuiShare").hide();
									  } 
							      });
							break;
							
							case 'department':	
								table.reload("layuiShareser") 
								var dicDiv=$('#layuiShare6');
								layer.open({
							         type: 1
							        ,title: '人员分摊' //不显示标题栏
							        ,closeBtn: false
							        ,zindex:-1
							        ,area:['50%', '90%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro9' //设定一个id，防止重复弹出
							        ,btn: ['取消']
							        ,btnAlign: 'c'
							        ,moveType: 1 //拖拽模式，0或者1
							        ,content:dicDiv
							        ,success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole2',
											'lay-submit' : ''
										})
							        }
							        ,end:function(){
							        	$("#layuiShare6").hide();
									  } 
							      });
							break;
							
							
							case 'openMeal':
								$.ajax({
									url: '${ctx}/personnel/getpersonVariabledao',
									type: "GET",
									data:{
										type:2
									},
									async: false,
									beforeSend: function() {
										index;
									},
									success: function(result) {
										$(result.data).each(function(i, o) {
											$("#ids").val(o.id);
											$("#keyValue").val(o.keyValue);
											$("#keyValueTwo").val(o.keyValueTwo);
											$("#keyValueThree").val(o.keyValueThree);
										})
										
										layer.close(index);
									},
									error: function() {
										layer.msg("操作失败！", {
											icon: 2
										});
										layer.close(index);
									}
								});
								
								//报价修改
								var	dicDiv=$("#layuiadmin-form-admin");
									layer.open({
										type:1,
										title:'水费标准',
										area:['40%','60%'],
										btn:['确认','取消'],
										content:dicDiv,
										id: 'LAY_layuipro' ,
										btnAlign: 'c',
									    moveType: 1, //拖拽模式，0或者1
										success : function(layero, index) {
								        	layero.addClass('layui-form');
											// 将保存按钮改变成提交按钮
											layero.find('.layui-layer-btn0').attr({
												'lay-filter' : 'addRole',
												'lay-submit' : ''
											})
								        },
										yes:function(){
											form.on('submit(addRole)', function(data) {
												$.ajax({
													url: '${ctx}/personnel/addPersonVaiable',
													type: "POST",
													data:data.field,
													async: false,
													beforeSend: function() {
														index;
													},
													success: function(result) {
													if(result.code==0){
														layer.msg("修改成功！", {
															icon: 1
														});
													}else{
														layer.msg("修改失败！", {
															icon: 2
														});
													}
														layer.close(index);
													},
													error: function() {
														layer.msg("操作失败！", {
															icon: 2
														});
														layer.close(index);
													}
												});
											})
										}
									})
							break;
									
							case 'openMea2':
								$.ajax({
									url: '${ctx}/personnel/getpersonVariabledao',
									type: "GET",
									data:{
										type:3
									},
									async: false,
									beforeSend: function() {
										index;
									},
									success: function(result) {
										$(result.data).each(function(i, o) {
											$("#ids2").val(o.id);
											$("#keyValue2").val(o.keyValue);
											$("#keyValueTwo2").val(o.keyValueTwo);
											$("#keyValueThree2").val(o.keyValueThree);
										})
										
										layer.close(index);
									},
									error: function() {
										layer.msg("操作失败！", {
											icon: 2
										});
										layer.close(index);
									}
								});
								
								//报价修改
								var	dicDiv=$("#layuiadmin-form-admin6");
									layer.open({
										type:1,
										title:'电费标准',
										area:['40%','60%'],
										btn:['确认','取消'],
										content:dicDiv,
										id: 'LAY_layuipro' ,
										btnAlign: 'c',
									    moveType: 1, //拖拽模式，0或者1
										success : function(layero, index) {
								        	layero.addClass('layui-form');
											// 将保存按钮改变成提交按钮
											layero.find('.layui-layer-btn0').attr({
												'lay-filter' : 'addRole',
												'lay-submit' : ''
											})
								        },
										yes:function(){
											form.on('submit(addRole)', function(data) {
												$.ajax({
													url: '${ctx}/personnel/addPersonVaiable',
													type: "POST",
													data:data.field,
													async: false,
													beforeSend: function() {
														index;
													},
													success: function(result) {
													if(result.code==0){
														layer.msg("修改成功！", {
															icon: 1
														});
													}else{
														layer.msg("修改失败！", {
															icon: 2
														});
													}
														layer.close(index);
													},
													error: function() {
														layer.msg("操作失败！", {
															icon: 2
														});
														layer.close(index);
													}
												});
											})
										}
									})
							break;	
									
							case 'summary':
								table.render({
									elem: '#summary',
									size: 'lg',
									url: '${ctx}/personnel/getTotal' ,
									where:{
										type:2
									},
									request:{
										pageName: 'page' ,//页码的参数名称，默认：page
										limitName: 'size' //每页数据量的参数名，默认：limit
									},
									page: {
									},//开启分页
									loading: true,
									toolbar: '#tool2', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
									//totalRow: true,		 //开启合计行 */
									smartReloadModel: true,// 开启智能重载
									parseData: function(ret) {
										$("#ddds1").val(ret.data.rows[0].oneUpperNum)
										$("#ddds2").val(ret.data.rows[0].twoUpperNum)
										$("#ddds3").val(ret.data.rows[0].threeUpperNum)
										return {
											code: ret.code,
											msg: ret.message,
											count:ret.data.total,
											data: ret.data.rows
										}
									},
									cols: [
										[{
											field: "monthDate",
											title: "月份",
											align: 'center',
										},{
											field: "oneNowNum",
											title: "二楼上月抄表数",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "oneUpperNum",
											title: "二楼当月抄表数",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "twoNowNum",
											title: "三楼上月抄表数",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "twoUpperNum",
											title: "三楼当月抄表度数",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "threeNowNum",
											title: "四楼上月抄表数",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "threeUpperNum",
											title: "四楼当月抄表度数",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "loss",
											title: "损耗",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "buse",
											title: "按面积比例核算",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "copper",
											title: "铜铁耗损",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "individual",
											title: "个体之间的损耗",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "summary",
											title: "总电量",
											align: 'center',
										},{
											field: "summaryPrice",
											title: "总价值",
											align: 'center',
										}
										]
									],
									done: function() {
										var tableView = this.elem.next();
										tableView.find('.layui-table-grid-down').remove();
										var totalRow = tableView.find('.layui-table-total');
										var limit = this.page ? this.page.limit : this.limit;
										layui.each(totalRow.find('td'), function(index, tdElem) {
											tdElem = $(tdElem);
											var text = tdElem.text();
											if(text && !isNaN(text)) {
												text = (parseFloat(text) / limit).toFixed(2);
												tdElem.find('div.layui-table-cell').html(text);
											}
										});
									},
									//下拉框回显赋值
									done: function(res, curr, count) {
										var tableView = this.elem.next();
										var tableElem = this.elem.next('.layui-table-view');
										layui.each(tableElem.find('.layui-table-box').find('select'), function(index, item) {
											var elem = $(item);
											elem.val(elem.data('value'));
										});
										form.render();
										// 初始化laydate
										layui.each(tableView.find('td[data-field="monthDate"]'), function(index, tdElem) {
											tdElem.onclick = function(event) {
												layui.stope(event)
											};
											laydate.render({
												elem: tdElem.children[0],
												format: 'yyyy-MM-dd HH:mm:ss',
												done: function(value, date) {
														var id = table.cache[tableView.attr('lay-id')][index].id
														var postData = {
															id: id,
															monthDate: value,
														};
														//调用新增修改
														  mainJs.fUpdateToal(postData); 
															}
														})
													})
												},
											});
								
								var dicDiv=$('#summ');
								layer.open({
							         type: 1
							        ,title: '新增总水电' 
							        ,closeBtn: false
							        ,zindex:-1
							        ,area:['90%', '90%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro50' //设定一个id，防止重复弹出
							        ,btn: ['取消']
							        ,btnAlign: 'c'
							        ,moveType: 1 //拖拽模式，0或者1
							        ,content:dicDiv
							        ,success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole2',
											'lay-submit' : ''
										})
							        }
							        ,end:function(){
									  } 
							      });
								break;
								
								
							case 'summaryery':
								table.render({
									elem: '#summaryer',
									size: 'lg',
									url: '${ctx}/personnel/getTotal' ,
									where:{
										type:1
									},
									request:{
										pageName: 'page' ,//页码的参数名称，默认：page
										limitName: 'size' //每页数据量的参数名，默认：limit
									},
									page: {
									},//开启分页
									loading: true,
									toolbar: '#tool3', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
									//totalRow: true,		 //开启合计行 */
									smartReloadModel: true,// 开启智能重载
									parseData: function(ret) {
										return {
											code: ret.code,
											msg: ret.message,
											count:ret.data.total,
											data: ret.data.rows
										}
									},
									cols: [
										[{
											field: "monthDate",
											title: "月份",
											align: 'center',
											
										},{
											field: "loss",
											title: "消防水量",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "buse",
											title: "耗损水量",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "summary",
											title: "总电量",
											align: 'center',
											edit:"text",
											style:'background-color: #f7e932;'
										},{
											field: "summaryPrice",
											title: "总价值",
											align: 'center',
										}
										]
									],
									done: function() {
										var tableView = this.elem.next();
										tableView.find('.layui-table-grid-down').remove();
										var totalRow = tableView.find('.layui-table-total');
										var limit = this.page ? this.page.limit : this.limit;
										layui.each(totalRow.find('td'), function(index, tdElem) {
											tdElem = $(tdElem);
											var text = tdElem.text();
											if(text && !isNaN(text)) {
												text = (parseFloat(text) / limit).toFixed(2);
												tdElem.find('div.layui-table-cell').html(text);
											}
										});
									},
									//下拉框回显赋值
									done: function(res, curr, count) {
										var tableView = this.elem.next();
										var tableElem = this.elem.next('.layui-table-view');
										layui.each(tableElem.find('select'), function(index, item) {
											var elem = $(item);
											elem.val(elem.data('value'));
										});
										form.render();
										// 初始化laydate
										layui.each(tableView.find('td[data-field="monthDate"]'), function(index, tdElem) {
											tdElem.onclick = function(event) {
												layui.stope(event)
											};
											laydate.render({
												elem: tdElem.children[0],
												format: 'yyyy-MM-dd HH:mm:ss',
												done: function(value, date) {
														var id = table.cache[tableView.attr('lay-id')][index].id
														var postData = {
															id: id,
															monthDate: value,
														};
														//调用新增修改
														  mainJs.fUpdateToal2(postData); 
															}
														})
													})
												},
											});
								
								var dicDiv=$('#summpo');
								layer.open({
							         type: 1
							        ,title: '新增总水费' 
							        ,closeBtn: false
							        ,zindex:-1
							        ,area:['80%', '90%']
							        ,shade: 0.5
							        ,id: 'LAY_layuipro75' //设定一个id，防止重复弹出
							        ,btn: ['取消']
							        ,btnAlign: 'c'
							        ,moveType: 1 //拖拽模式，0或者1
							        ,content:dicDiv
							        ,success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole2',
											'lay-submit' : ''
										})
							        }
							        ,end:function(){
									  } 
							      });
								break;
						}
					});
					
					
					
					//监听单元格编辑
					table.on('tool(tableData)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							title=obj.data.name,
							id = data.id,
							userid=data.users,
							myArray = [];
						$('#water').val(obj.data.id);	
						$('#power').val(obj.data.id);
						$('#fixeds').val(obj.data.id);
						$('#sundrys').val(obj.data.id);
							for (var i = 0; i < userid.length; i++) {
								myArray.push(userid[i].id)
							}
						var a=0;
						if(obj.event === 'update'){
							$("#hostelId").val(id)
							$("#divID").attr("data_value",myArray)
							$("#divID").attr("name","province[]")
							var transferTools = $("[cyType='transferTool']");
						    for (var i = 0; i < transferTools.length; i++) {
						        $(transferTools[i]).transferTool();
						    }
						    form.render();
							var	dicDiv=$("#layuiadmin-form-admin2");
						var index=layer.open({
								type:1,
								title:title,
								area:['55%','60%'],
								btn:['确认','取消'],
								content:dicDiv,
								id: 'LAY_layuipro' ,
								btnAlign: 'c',
							    moveType: 1, //拖拽模式，0或者1
								success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole',
										'lay-submit' : ''
									})
						        },
								yes:function(){
									form.on('submit(addRole)', function(data) {
										post={
											jsonName:JSON.stringify(data.field)
										}
										 mainJs.fUpdate(post) 
										layer.close(index);
									})
								},end:function(){ 
						        	$('#fightDiv').html("");
								  }
							})
						}
						//查看宿舍人员详情
						if(obj.event === 'live'){
							table.render({
								elem: '#tableBudget',
								size: 'lg',
								url: '${ctx}/personnel/getLive' ,
								where:{
									hostelId:id,
									type:1,
								},
								request:{
									pageName: 'page' ,//页码的参数名称，默认：page
									limitName: 'size' //每页数据量的参数名，默认：limit
								},
								page: {
								},//开启分页
								loading: true,
								//toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
								//totalRow: true,		 //开启合计行 */
								smartReloadModel: true,// 开启智能重载
								parseData: function(ret) {
									return {
										code: ret.code,
										msg: ret.message,
										count:ret.data.total,
										data: ret.data.rows
									}
								},
								cols: [
									[{
										field: "userName",
										title: "姓名",
										align: 'center',
										templet:function(d){
											return d.user.userName
										}
									},{
										field: "name",
										title: "部门",
										align: 'center',
										templet:function(d){
											return d.user.orgName.name
										}
									},{
										field: "age",
										title: "年龄",
										align: 'center',
										templet:function(d){
											return d.user.age
										}
									},{
										field: "bed",
										title: "床号",
										align: 'center',
										edit:"text",
									},{
										field: "inLiveDate",
										title: "入住日期",
										align: 'center',
										edit:"text",
									},{
										field: "otLiveDate",
										title: "退房日期",
										align: 'center',
										edit:"text",
									},{
										field: "liveRemark",
										title: "备注",
										align: 'center',
										edit:"text",
									}
									]
								],
								done: function() {
									var tableView = this.elem.next();
									tableView.find('.layui-table-grid-down').remove();
									var totalRow = tableView.find('.layui-table-total');
									var limit = this.page ? this.page.limit : this.limit;
									layui.each(totalRow.find('td'), function(index, tdElem) {
										tdElem = $(tdElem);
										var text = tdElem.text();
										if(text && !isNaN(text)) {
											text = (parseFloat(text) / limit).toFixed(2);
											tdElem.find('div.layui-table-cell').html(text);
										}
									});
								},
								//下拉框回显赋值
								done: function(res, curr, count) {
									var tableView = this.elem.next();
									var tableElem = this.elem.next('.layui-table-view');
									layui.each(tableElem.find('select'), function(index, item) {
										var elem = $(item);
										elem.val(elem.data('value'));
									});
									form.render();
									// 初始化laydate
									layui.each(tableView.find('td[data-field="inLiveDate"]'), function(index, tdElem) {
										tdElem.onclick = function(event) {
											layui.stope(event)
										};
										laydate.render({
											elem: tdElem.children[0],
											format: 'yyyy-MM-dd HH:mm:ss',
											done: function(value, date) {
													var id = table.cache[tableView.attr('lay-id')][index].id
													var postData = {
														id: id,
														inLiveDate: value,
													};
													//调用新增修改
													  mainJs.fUpdateUser(postData); 
														}
													})
												})
									layui.each(tableView.find('td[data-field="otLiveDate"]'), function(index, tdElem) {
										tdElem.onclick = function(event) {
											layui.stope(event)
										};
										laydate.render({
											elem: tdElem.children[0],
											format: 'yyyy-MM-dd HH:mm:ss',
											done: function(value, date) {
													var id = table.cache[tableView.attr('lay-id')][index].id
													var postData = {
														id: id,
														otLiveDate: value,
													};
													//调用新增修改
													 mainJs.fUpdateUser(postData);
														}
													})
												})			
											},
										});
							
							var dicDiv=$('#layuiOpen');
							layer.open({
						         type: 1
						        ,title: title //不显示标题栏
						        ,closeBtn: false
						        ,zindex:-1
						        ,area:['80%', '90%']
						        ,shade: 0.5
						        ,id: 'LAY_layuipro2' //设定一个id，防止重复弹出
						        ,btn: ['取消']
						        ,btnAlign: 'c'
						        ,moveType: 1 //拖拽模式，0或者1
						        ,content:dicDiv
						        ,success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole2',
										'lay-submit' : ''
									})
						        }
						        ,end:function(){
								  } 
						      });
						}
						
						
						//查看宿舍水费详情
						if(obj.event === 'water'){
							table.render({
								elem: '#powerWater',
								size: 'lg',
								url: '${ctx}/personnel/getHydropower' ,
								where:{
									hostelId:id,
									type:1,
								},
								request:{
									pageName: 'page' ,//页码的参数名称，默认：page
									limitName: 'size' //每页数据量的参数名，默认：limit
								},
								page: {
								},//开启分页
								loading: true,
								toolbar: '#toolbarwater', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
								//totalRow: true,		 //开启合计行 */
								smartReloadModel: true,// 开启智能重载
								parseData: function(ret) {
									$("#nowDegreeNumss").val(ret.data.rows[0].nowDegreeNum);
									return {
										code: ret.code,
										msg: ret.message,
										count:ret.data.total,
										data: ret.data.rows
									}
								},
								cols: [
									[{
										field: "userName",
										title: "姓名",
										align: 'center',
										templet:function(d){
											var html=""
											 $(d.hostel.users).each(function(k,j){
												 html+=j.userName+' '
											 })
											return html
										}
									},{
										field: "number",
										title: "人数",
										align: 'center',
										templet:function(d){
											return d.hostel.number
										}
									},{
										field: "monthDate",
										title: "月份",
										align: 'center',
									},{
										field: "upperDegreeNum",
										title: "上月抄表",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "nowDegreeNum",
										title: "本月抄表",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "sum",
										title: "实际吨数",
										align: 'center',
									},{
										field: "price",
										title: "每一吨金额",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "summaryPrice",
										title: "总金额",
										align: 'center',
										style:'background-color: #31c798;'
									},{
										field: "talonNum",
										title: "标准",
										align: 'center',
										edit:"text",
										style:'background-color: #f7e932;'
									},{
										field: "exceedNum",
										title: "超支",
										align: 'center',
									},{
										field: "exceedPrice",
										title: "超支金额",
										align: 'center',
										style:'background-color: #31c798;'
									}
									]
								],
								done: function() {
									var tableView = this.elem.next();
									tableView.find('.layui-table-grid-down').remove();
									var totalRow = tableView.find('.layui-table-total');
									var limit = this.page ? this.page.limit : this.limit;
									layui.each(totalRow.find('td'), function(index, tdElem) {
										tdElem = $(tdElem);
										var text = tdElem.text();
										if(text && !isNaN(text)) {
											text = (parseFloat(text) / limit).toFixed(2);
											tdElem.find('div.layui-table-cell').html(text);
										}
									});
								},
								//下拉框回显赋值
								done: function(res, curr, count) {
									var tableView = this.elem.next();
									var tableElem = this.elem.next('.layui-table-view');
									layui.each(tableElem.find('select'), function(index, item) {
										var elem = $(item);
										elem.val(elem.data('value'));
									});
									form.render();
									// 初始化laydate
									layui.each(tableView.find('td[data-field="monthDate"]'), function(index, tdElem) {
										tdElem.onclick = function(event) {
											layui.stope(event)
										};
										laydate.render({
											elem: tdElem.children[0],
											type : 'month',
											format: 'yyyy-MM-dd HH:mm:ss',
											done: function(value, date) {
													var id = table.cache[tableView.attr('lay-id')][index].id
													var postData = {
														id: id,
														monthDate: value,
													};
													//调用新增修改
													  mainJs.fUpdatepower(postData); 
														}
													})
												})
											},
										});
							
							var dicDiv=$('#layuipower');
							layer.open({
						         type: 1
						        ,title: title //不显示标题栏
						        ,closeBtn: false
						        ,zindex:-1
						        ,area:['90%', '90%']
						        ,shade: 0.5
						        ,id: 'LAY_layuipro110' //设定一个id，防止重复弹出
						        ,btn: ['取消']
						        ,btnAlign: 'c'
						        ,moveType: 1 //拖拽模式，0或者1
						        ,content:dicDiv
						        ,success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole2',
										'lay-submit' : ''
									})
						        }
						        ,end:function(){
								  } 
						      });
						}
						
						//查看固定费用详情
						if(obj.event === 'fixed'){
							table.render({
								elem: '#Fixede',
								size: 'lg',
								url: '${ctx}/personnel/getFixed' ,
								where:{
									hostelId:id,
								},
								request:{
									pageName: 'page' ,//页码的参数名称，默认：page
									limitName: 'size' //每页数据量的参数名，默认：limit
								},
								page: {
								},//开启分页
								loading: true,
								toolbar: '#toolfixed', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
								//totalRow: true,		 //开启合计行 */
								smartReloadModel: true,// 开启智能重载
								parseData: function(ret) {
									return {
										code: ret.code,
										msg: ret.message,
										count:ret.data.total,
										data: ret.data.rows
									}
								},
								cols: [
									[{
										field: "monthDate",
										title: "月份",
										align: 'center',
									},{
										field: "name",
										title: "名称",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "sum",
										title: "价值",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "branch",
										title: "平摊多少月",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "price",
										title: "每月平摊金额",
										align: 'center',
									},{
										field: "surplusSum",
										title: "剩余平摊金额",
										align: 'center',
									}
									]
								],
								done: function() {
									var tableView = this.elem.next();
									tableView.find('.layui-table-grid-down').remove();
									var totalRow = tableView.find('.layui-table-total');
									var limit = this.page ? this.page.limit : this.limit;
									layui.each(totalRow.find('td'), function(index, tdElem) {
										tdElem = $(tdElem);
										var text = tdElem.text();
										if(text && !isNaN(text)) {
											text = (parseFloat(text) / limit).toFixed(2);
											tdElem.find('div.layui-table-cell').html(text);
										}
									});
								},
								//下拉框回显赋值
								done: function(res, curr, count) {
									var tableView = this.elem.next();
									var tableElem = this.elem.next('.layui-table-view');
									layui.each(tableElem.find('select'), function(index, item) {
										var elem = $(item);
										elem.val(elem.data('value'));
									});
									form.render();
									// 初始化laydate
									layui.each(tableView.find('td[data-field="monthDate"]'), function(index, tdElem) {
										tdElem.onclick = function(event) {
											layui.stope(event)
										};
										laydate.render({
											elem: tdElem.children[0],
											type : 'month',
											format: 'yyyy-MM-dd HH:mm:ss',
											done: function(value, date) {
													var id = table.cache[tableView.attr('lay-id')][index].id
													var postData = {
															id: id,
															monthDate: value,
														};
														//调用新增修改
														  mainJs.fUpdateFixed(postData);
														}
													})
												})
											},
										});
							
							var dicDiv=$('#Fixed');
							layer.open({
						         type: 1
						        ,title: title //不显示标题栏
						        ,closeBtn: false
						        ,zindex:-1
						        ,area:['80%', '90%']
						        ,shade: 0.5
						        ,id: 'LAY_layuipro3' //设定一个id，防止重复弹出
						        ,btn: ['取消']
						        ,btnAlign: 'c'
						        ,moveType: 1 //拖拽模式，0或者1
						        ,content:dicDiv
						        ,success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole2',
										'lay-submit' : ''
									})
						        }
						        ,end:function(){
								  } 
						      });
						}
						
						
						//查看宿舍电费费详情
						if(obj.event === 'power'){
							table.render({
								elem: '#powerWater',
								size: 'lg',
								url: '${ctx}/personnel/getHydropower' ,
								where:{
									hostelId:id,
									type:2,
								},
								request:{
									pageName: 'page' ,//页码的参数名称，默认：page
									limitName: 'size' //每页数据量的参数名，默认：limit
								},
								page: {
								},//开启分页
								loading: true,
								toolbar: '#toolbarpower', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
								//totalRow: true,		 //开启合计行 */
								smartReloadModel: true,// 开启智能重载
								parseData: function(ret) {
									$("#nowDegreeNumss2").val(ret.data.rows[0].nowDegreeNum);
									return {
										code: ret.code,
										msg: ret.message,
										count:ret.data.total,
										data: ret.data.rows
									}
								},
								cols: [
									[{
										field: "userName",
										title: "姓名",
										align: 'center',
										templet:function(d){
											var html=""
											 $(d.hostel.users).each(function(k,j){
												 html+=j.userName+' '
											 })
											return html
										}
									},{
										field: "number",
										title: "人数",
										align: 'center',
										templet:function(d){
											return d.hostel.number
										}
									},{
										field: "monthDate",
										title: "月份",
										align: 'center',
									},{
										field: "upperDegreeNum",
										title: "上月抄表",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "nowDegreeNum",
										title: "本月抄表",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "sum",
										title: "实际度数",
										align: 'center',
									},{
										field: "price",
										title: "每一度金额",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "summaryPrice",
										title: "总金额",
										align: 'center',
										style:'background-color: #31c798;'
									},{
										field: "talonNum",
										title: "标准",
										align: 'center',
										edit:"text",
										style:'background-color: #f7e932;'
									},{
										field: "exceedNum",
										title: "超支",
										align: 'center',
									},{
										field: "exceedPrice",
										title: "超支金额",
										align: 'center',
										style:'background-color: #31c798;'
									}
									]
								],
								done: function() {
									var tableView = this.elem.next();
									tableView.find('.layui-table-grid-down').remove();
									var totalRow = tableView.find('.layui-table-total');
									var limit = this.page ? this.page.limit : this.limit;
									layui.each(totalRow.find('td'), function(index, tdElem) {
										tdElem = $(tdElem);
										var text = tdElem.text();
										if(text && !isNaN(text)) {
											text = (parseFloat(text) / limit).toFixed(2);
											tdElem.find('div.layui-table-cell').html(text);
										}
									});
								},
								//下拉框回显赋值
								done: function(res, curr, count) {
									var tableView = this.elem.next();
									var tableElem = this.elem.next('.layui-table-view');
									layui.each(tableElem.find('select'), function(index, item) {
										var elem = $(item);
										elem.val(elem.data('value'));
									});
									form.render();
									// 初始化laydate
									layui.each(tableView.find('td[data-field="monthDate"]'), function(index, tdElem) {
										tdElem.onclick = function(event) {
											layui.stope(event)
										};
										laydate.render({
											elem: tdElem.children[0],
											type : 'month',
											format: 'yyyy-MM-dd HH:mm:ss',
											done: function(value, date) {
													var id = table.cache[tableView.attr('lay-id')][index].id
													var postData = {
														id: id,
														monthDate: value,
													};
													//调用新增修改
													  mainJs.fUpdatepower(postData); 
														}
													})
												})
											},
										});
							
							var dicDiv=$('#layuipower');
							layer.open({
						         type: 1
						        ,title: title //不显示标题栏
						        ,closeBtn: false
						        ,zindex:-1
						        ,area:['90%', '90%']
						        ,shade: 0.5
						        ,id: 'LAY_layuipro3' //设定一个id，防止重复弹出
						        ,btn: ['取消']
						        ,btnAlign: 'c'
						        ,moveType: 1 //拖拽模式，0或者1
						        ,content:dicDiv
						        ,success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole2',
										'lay-submit' : ''
									})
						        }
						        ,end:function(){
								  } 
						      });
						}
						
						//查看宿舍其他费用
						if(obj.event === 'sundry'){
							table.render({
								elem: '#sundry',
								size: 'lg',
								url: '${ctx}/personnel/getSundry' ,
								where:{
									hostelId:id,
								},
								request:{
									pageName: 'page' ,//页码的参数名称，默认：page
									limitName: 'size' //每页数据量的参数名，默认：limit
								},
								page: {
								},//开启分页
								loading: true,
								toolbar: '#toolsundry', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
								//totalRow: true,		 //开启合计行 */
								smartReloadModel: true,// 开启智能重载
								parseData: function(ret) {
									return {
										code: ret.code,
										msg: ret.message,
										count:ret.data.total,
										data: ret.data.rows
									}
								},
								cols: [
									[{
										field: "monthDate",
										title: "月份",
										align: 'center',
									},{
										field: "rent",
										title: "房租",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "water",
										title: "水费",
										align: 'center',
									},{
										field: "power",
										title: "电费",
										align: 'center',
									},{
										field: "coal",
										title: "煤气费",
										align: 'center',
										edit:'text',
										style:'background-color: #f7e932;'
									},{
										field: "fixed",
										title: "固定资产分摊",
										align: 'center',
									},{
										field: "broadband",
										title: "宽带费",
										align: 'center',
										edit:"text",
										style:'background-color: #f7e932;'
									},{
										field: "administration",
										title: "当月安检管理费",
										align: 'center',
										edit:"text",
										style:'background-color: #f7e932;'
									}
									]
								],
								done: function() {
									var tableView = this.elem.next();
									tableView.find('.layui-table-grid-down').remove();
									var totalRow = tableView.find('.layui-table-total');
									var limit = this.page ? this.page.limit : this.limit;
									layui.each(totalRow.find('td'), function(index, tdElem) {
										tdElem = $(tdElem);
										var text = tdElem.text();
										if(text && !isNaN(text)) {
											text = (parseFloat(text) / limit).toFixed(2);
											tdElem.find('div.layui-table-cell').html(text);
										}
									});
								},
								//下拉框回显赋值
								done: function(res, curr, count) {
									var tableView = this.elem.next();
									var tableElem = this.elem.next('.layui-table-view');
									layui.each(tableElem.find('select'), function(index, item) {
										var elem = $(item);
										elem.val(elem.data('value'));
									});
									form.render();
									// 初始化laydate
									layui.each(tableView.find('td[data-field="monthDate"]'), function(index, tdElem) {
										tdElem.onclick = function(event) {
											layui.stope(event)
										};
										laydate.render({
											elem: tdElem.children[0],
											type : 'month',
											format: 'yyyy-MM-dd HH:mm:ss',
											done: function(value, date) {
													var id = table.cache[tableView.attr('lay-id')][index].id
													var postData = {
														id: id,
														monthDate: value,
													};
													//调用新增修改
													  mainJs.fUpdatepower(postData); 
														}
													})
												})
											},
										});
							
							var dicDiv=$('#sundrye');
							layer.open({
						         type: 1
						        ,title: title //不显示标题栏
						        ,closeBtn: false
						        ,zindex:-1
						        ,area:['80%', '90%']
						        ,shade: 0.5
						        ,id: 'LAY_layuipro55' //设定一个id，防止重复弹出
						        ,btn: ['取消']
						        ,btnAlign: 'c'
						        ,moveType: 1 //拖拽模式，0或者1
						        ,content:dicDiv
						        ,success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole2',
										'lay-submit' : ''
									})
						        }
						        ,end:function(){
								  } 
						      });
						}
					});
					
					//监听总水电工具栏事件
					table.on('toolbar(summary)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData10':
								var	dicDiv=$("#layuiadmin-form-admin9");
								layer.open({
									type:1,
									title:'新增总电费',
									area:['40%','70%'],
									btn:['确认','取消'],
									content:dicDiv,
									id: 'LAY_layuipro65' ,
									btnAlign: 'c',
								    moveType: 1, //拖拽模式，0或者1
									success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        },
									yes:function(){
										form.on('submit(addRole)', function(data) {
											 mainJs.fUpdateToal(data.field) 
											document.getElementById("layuiadmin-form-admin9").reset();
								        	layui.form.render();
										})
									},end:function(){ 
							        	
									  }
								})
								break;
						}
					});
					
					//监听总电费工具栏事件
					table.on('toolbar(summaryer)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData11':
								var	dicDiv=$("#layuiadmin-form-admin11");
								layer.open({
									type:1,
									title:'新增总电费',
									area:['45%','70%'],
									btn:['确认','取消'],
									content:dicDiv,
									id: 'LAY_layuipro36' ,
									btnAlign: 'c',
								    moveType: 1, //拖拽模式，0或者1
									success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        },
									yes:function(){
										form.on('submit(addRole)', function(data) {
											 mainJs.fUpdateToal2(data.field) 
											document.getElementById("layuiadmin-form-admin11").reset();
								        	layui.form.render();
										})
									},end:function(){ 
							        	
									  }
								})
								break;
						}
					});
					
					//监听水费头工具栏事件
					table.on('toolbar(powerWater)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData':
								var	dicDiv=$("#layuiadmin-form-admin4");
								layer.open({
									type:1,
									title:'新增水费',
									shadeClose:true,
									area:['45%','50%'],
									btn:['确认','取消'],
									content:dicDiv,
									id: 'LAY_layuipro' ,
									btnAlign: 'c',
								    moveType: 1, //拖拽模式，0或者1
									success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        },
									yes:function(){
										form.on('submit(addRole)', function(data) {
											 mainJs.fUpdatepower(data.field) 
											document.getElementById("layuiadmin-form-admin4").reset();
								        	layui.form.render();
								        	$("#water").val(data.field.hostelId)
										})
									},end:function(){ 
							        	
									  }
								})
								break;
							case 'addTempData2':
								var	dicDiv=$("#layuiadmin-form-admin5");
								layer.open({
									type:1,
									title:'新增电费',
									shadeClose:true,
									area:['45%','50%'],
									btn:['确认','取消'],
									content:dicDiv,
									id: 'LAY_layuipro9' ,
									btnAlign: 'c',
								    moveType: 1, //拖拽模式，0或者1
									success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        },
									yes:function(){
										form.on('submit(addRole)', function(data) {
											 mainJs.fUpdatepower(data.field) 
											document.getElementById("layuiadmin-form-admin5").reset();
								        	layui.form.render();
								        	$("#power").val(data.field.hostelId)
										})
									},end:function(){ 
							        	
									  }
								})
								break;	
						}
					});
					
					//监听水费头工具栏事件
					table.on('toolbar(Fixed)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData3':
								var	dicDiv=$("#layuiadmin-form-admin7");
								layer.open({
									type:1,
									title:'新增固定资产',
									area:['45%','50%'],
									shadeClose:true,
									btn:['确认','取消'],
									content:dicDiv,
									id: 'LAY_layuipro' ,
									btnAlign: 'c',
								    moveType: 1, //拖拽模式，0或者1
									success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        },
									yes:function(){
										form.on('submit(addRole)', function(data) {
											 mainJs.fUpdateFixed(data.field) 
											document.getElementById("layuiadmin-form-admin7").reset();
								        	layui.form.render();
								        	$("#fixeds").val(data.field.hostelId)
										})
									},end:function(){ 
							        	
									  }
								})
								break;
						}
					});
					
					//监听其他费用事件
					table.on('toolbar(sundry)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
							case 'addTempData4':
								var	dicDiv=$("#layuiadmin-form-admin8");
								layer.open({
									type:1,
									title:'新增其他费用',
									shadeClose:true,
									area:['45%','50%'],
									btn:['确认','取消'],
									content:dicDiv,
									id: 'LAY_layuipro' ,
									btnAlign: 'c',
								    moveType: 1, //拖拽模式，0或者1
									success : function(layero, index) {
							        	layero.addClass('layui-form');
										// 将保存按钮改变成提交按钮
										layero.find('.layui-layer-btn0').attr({
											'lay-filter' : 'addRole',
											'lay-submit' : ''
										})
							        },
									yes:function(){
										form.on('submit(addRole)', function(data) {
											 mainJs.fUpdateSundry(data.field) 
											document.getElementById("layuiadmin-form-admin8").reset();
								        	layui.form.render();
								        	$("#sundrys").val(data.field.hostelId)
										})
									},end:function(){ 
							        	
									  }
								})
								break;
						}
					});
					
					//监听单元格编辑 (宿舍人员详情)
					table.on('edit(tableBudget)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							id = data.id;
							var postData = {
								id:id,
								[field]:value
							}
							//调用新增修改
							 mainJs.fUpdateUser(postData); 
					});
					
					//监听单元格编辑 (水费详情)
					table.on('edit(powerWater)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							id = data.id;
							var postData = {
								id:id,
								[field]:value
							}
							//调用新增修改
							mainJs.fUpdatepower(postData);
					});
					
					//监听单元格编辑 (固定资产)
					table.on('edit(Fixed)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							id = data.id;
							var postData = {
								id:id,
								[field]:value
							}
							//调用新增修改
							mainJs.fUpdateFixed(postData);
					});
					
					//监听单元格编辑 (其他费用详情)
					table.on('edit(sundry)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							id = data.id;
							var postData = {
								id:id,
								[field]:value
							}
							//调用新增修改
							 mainJs.fUpdateSundry(postData); 
					});
					
					//监听单元格编辑 (总用电费用详情)
					table.on('edit(summary)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							id = data.id;
							var postData = {
								id:id,
								[field]:value
							}
							//调用新增修改
							 mainJs.fUpdateToal(postData); 
					});
					//监听单元格编辑 (总用水费用详情)
					table.on('edit(summaryer)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							id = data.id;
							var postData = {
								id:id,
								[field]:value
							}
							//调用新增修改
							 mainJs.fUpdateToal2(postData); 
					});
					//监听搜索
					form.on('submit(LAY-search)', function(obj) {		//修改此处
						var field = obj.field;
						table.reload('tableData', {
							where: field
						});  
					});
					//人员分摊
					form.on('submit(LAY-search2)', function(obj) {
						onlyField=obj.field;
						event(onlyField);
						
					})
					var event=function(data){
						table.reload("layuiShare2",{
							where:data
						})
					};
					
					table.render({
						elem: '#layuiShare2',
						size: 'lg',
						url: '${ctx}/personnel/getSummaryShare' ,
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								count:ret.data.total,
								data: ret.data
							}
						},
						cols: [
							[{
								field: "username",
								title: "姓名",
								align: 'center',
								totalRowText: '合计'
							},{
								field: "OrgName",
								title: "部门",
								align: 'center',
							},{
								field: "money",
								title: "分摊费用",
								align: 'center',
								totalRow: true
							}
							]
						],
						done: function() {
							var tableView = this.elem.next();
							tableView.find('.layui-table-grid-down').remove();
							var totalRow = tableView.find('.layui-table-total');
							var limit = this.page ? this.page.limit : this.limit;
							layui.each(totalRow.find('td'), function(index, tdElem) {
								tdElem = $(tdElem);
								var text = tdElem.text();
								if(text && !isNaN(text)) {
									text = (parseFloat(text) / limit).toFixed(2);
									tdElem.find('div.layui-table-cell').html(text);
								}
							});
						},
						//下拉框回显赋值
						done: function(res, curr, count) {
							var tableView = this.elem.next();
							var tableElem = this.elem.next('.layui-table-view');
							layui.each(tableElem.find('select'), function(index, item) {
								var elem = $(item);
								elem.val(elem.data('value'));
							});
							form.render();
							// 初始化laydate
							layui.each(tableView.find('td[data-field="inLiveDate"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								laydate.render({
									elem: tdElem.children[0],
									format: 'yyyy-MM-dd HH:mm:ss',
									done: function(value, date) {
											var id = table.cache[tableView.attr('lay-id')][index].id
											var postData = {
												id: id,
												inLiveDate: value,
											};
											//调用新增修改
											  mainJs.fUpdateUser(postData); 
												}
											})
										})
							layui.each(tableView.find('td[data-field="otLiveDate"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								laydate.render({
									elem: tdElem.children[0],
									format: 'yyyy-MM-dd HH:mm:ss',
									done: function(value, date) {
											var id = table.cache[tableView.attr('lay-id')][index].id
											var postData = {
												id: id,
												otLiveDate: value,
											};
											//调用新增修改
											 mainJs.fUpdateUser(postData);
												}
											})
										})			
									},
								});
					
					//人员分摊
					form.on('submit(LAY-search6)', function(obj) {
						onlyField=obj.field;
						eventd(onlyField);
						
					})
					var eventd=function(data){
						table.reload("layuiShareser",{
							where:data
						})
					};
					table.render({
						elem: '#layuiShareser',
						size: 'lg',
						url: '${ctx}/personnel/getSummaryDepartment' ,
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						//开启分页
						loading: true,
						toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						totalRow: true,		 //开启合计行 */
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							return {
								code: ret.code,
								msg: ret.message,
								count:ret.data.total,
								data: ret.data
							}
						},
						cols: [
							[{
								field: "username",
								title: "姓名",
								align: 'center',
								totalRowText: '合计'
							},{
								field: "OrgName",
								title: "部门",
								align: 'center',
							},{
								field: "money",
								title: "分摊费用",
								align: 'center',
								totalRow: true
							}
							]
						],
						done: function() {
							var tableView = this.elem.next();
							tableView.find('.layui-table-grid-down').remove();
							var totalRow = tableView.find('.layui-table-total');
							var limit = this.page ? this.page.limit : this.limit;
							layui.each(totalRow.find('td'), function(index, tdElem) {
								tdElem = $(tdElem);
								var text = tdElem.text();
								if(text && !isNaN(text)) {
									text = (parseFloat(text) / limit).toFixed(2);
									tdElem.find('div.layui-table-cell').html(text);
								}
							});
						},
						//下拉框回显赋值
						done: function(res, curr, count) {
							var tableView = this.elem.next();
							var tableElem = this.elem.next('.layui-table-view');
							layui.each(tableElem.find('select'), function(index, item) {
								var elem = $(item);
								elem.val(elem.data('value'));
							});
							form.render();
							// 初始化laydate
							layui.each(tableView.find('td[data-field="inLiveDate"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								laydate.render({
									elem: tdElem.children[0],
									format: 'yyyy-MM-dd HH:mm:ss',
									done: function(value, date) {
											var id = table.cache[tableView.attr('lay-id')][index].id
											var postData = {
												id: id,
												inLiveDate: value,
											};
											//调用新增修改
											  mainJs.fUpdateUser(postData); 
												}
											})
										})
							layui.each(tableView.find('td[data-field="otLiveDate"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
								laydate.render({
									elem: tdElem.children[0],
									format: 'yyyy-MM-dd HH:mm:ss',
									done: function(value, date) {
											var id = table.cache[tableView.attr('lay-id')][index].id
											var postData = {
												id: id,
												otLiveDate: value,
											};
											//调用新增修改
											 mainJs.fUpdateUser(postData);
												}
											})
										})			
									},
								});
					
					
					
					//封装ajax主方法
					var mainJs = {
						//新增							
					    fAdd : function(data){
					    	$.ajax({
								url: "${ctx}/fince/addHostel",
								data: data,
								type: "POST",
								beforeSend: function() {
									index;
								},
								success: function(result) {
									if(0 == result.code) {
									 	 table.reload("tableData", {
							                page: {
							                }
							              }) 
										layer.msg(result.message, {
											icon: 1,
											time:800
										});
									
									} else {
										layer.msg(result.message, {
											icon: 2,
											time:800
										});
									}
								},
								error: function() {
									layer.msg("操作失败！请重试", {
										icon: 2
									});
								},
							});
							layer.close(index);
					    },
						
					//修改							
				    fUpdate : function(data){
				    	if(data.id==""){
				    		return;
				    	}
				    	$.ajax({
							url: "${ctx}/fince/updateUserHostelId",
							data: data,
							type: "POST",
							beforeSend: function() {
								index;
							},
							success: function(result) {
								if(0 == result.code) {
								 	 table.reload("tableData", {
						                page: {
						                }
						              }) 
									layer.msg(result.message, {
										icon: 1,
										time:800
									});
								
								} else {
									layer.msg(result.message, {
										icon: 2,
										time:800
									});
								}
							},
							error: function() {
								layer.msg("操作失败！请重试", {
									icon: 2
								});
							},
						});
						layer.close(index);
				    },
					    
					  //修改宿舍人员详情							
					    fUpdateUser : function(data){
					    	if(data.id==""){
					    		return;
					    	}
					    	$.ajax({
								url: "${ctx}/fince/addLive",
								data: data,
								type: "POST",
								beforeSend: function() {
									index;
								},
								success: function(result) {
									if(0 == result.code) {
									 	 table.reload("tableBudget", {
							                page: {
							                }
							              }) 
										layer.msg(result.message, {
											icon: 1,
											time:800
										});
									
									} else {
										layer.msg(result.message, {
											icon: 2,
											time:800
										});
									}
								},
								error: function() {
									layer.msg("操作失败！请重试", {
										icon: 2
									});
								},
							});
							layer.close(index);
					    },
				    
				  //新增宿水费详情							
				    fUpdatepower : function(data){
				    	if(data.id==""){
				    		return;
				    	}
				    	$.ajax({
							url: "${ctx}/fince/addHydropower",
							data: data,
							type: "POST",
							beforeSend: function() {
								index;
							},
							success: function(result) {
								if(0 == result.code) {
								 	 table.reload("powerWater", {
						                page: {
						                }
						              }) 
									layer.msg(result.message, {
										icon: 1,
										time:800
									});
								
								} else {
									layer.msg(result.message, {
										icon: 2,
										time:800
									});
								}
							},
							error: function() {
								layer.msg("操作失败！请重试", {
									icon: 2
								});
							},
						});
						layer.close(index);
				    },
					    
					    //新增固定资产							
					    fUpdateFixed : function(data){
					    	$.ajax({
								url: "${ctx}/fince/addFixed",
								data: data,
								type: "POST",
								beforeSend: function() {
									index;
								},
								success: function(result) {
									if(0 == result.code) {
									 	 table.reload("Fixede", {
							                page: {
							                }
							              }) 
										layer.msg(result.message, {
											icon: 1,
											time:800
										});
									
									} else {
										layer.msg(result.message, {
											icon: 2,
											time:800
										});
									}
								},
								error: function() {
									layer.msg("操作失败！请重试", {
										icon: 2
									});
								},
							});
							layer.close(index);
					    },   
				    
				  //新增其他费用						
				    fUpdateSundry : function(data){
				    	$.ajax({
							url: "${ctx}/fince/addSundry",
							data: data,
							type: "POST",
							beforeSend: function() {
								index;
							},
							success: function(result) {
								if(0 == result.code) {
								 	 table.reload("sundry", {
						                page: {
						                }
						              }) 
									layer.msg(result.message, {
										icon: 1,
										time:800
									});
								
								} else {
									layer.msg(result.message, {
										icon: 2,
										time:800
									});
								}
							},
							error: function() {
								layer.msg("操作失败！请重试", {
									icon: 2
								});
							},
						});
						layer.close(index);
				    } ,
				    
					  //新增总电费					
					    fUpdateToal : function(data){
					    	$.ajax({
								url: "${ctx}/fince/addTotal",
								data: data,
								type: "POST",
								beforeSend: function() {
									index;
								},
								success: function(result) {
									if(0 == result.code) {
									 	 table.reload("summary", {
							              }) 
										layer.msg(result.message, {
											icon: 1,
											time:800
										});
									
									} else {
										layer.msg(result.message, {
											icon: 2,
											time:800
										});
									}
								},
								error: function() {
									layer.msg("操作失败！请重试", {
										icon: 2
									});
								},
							});
							layer.close(index);
					    },
				    
				  //新增总电费					
				    fUpdateToal2 : function(data){
				    	$.ajax({
							url: "${ctx}/fince/addTotal",
							data: data,
							type: "POST",
							beforeSend: function() {
								index;
							},
							success: function(result) {
								if(0 == result.code) {
								 	 table.reload("summaryer", {
						              }) 
									layer.msg(result.message, {
										icon: 1,
										time:800
									});
								
								} else {
									layer.msg(result.message, {
										icon: 2,
										time:800
									});
								}
							},
							error: function() {
								layer.msg("操作失败！请重试", {
									icon: 2
								});
							},
						});
						layer.close(index);
				    } 
					}

				}
			)
		</script>
</body>

</html>
