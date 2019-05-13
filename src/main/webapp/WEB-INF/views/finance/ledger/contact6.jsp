<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>


<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>报销申请</title>
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
							<td>报销人:</td>
							<td><input type="text" name="Username" id="firstNames" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td><select class="form-control" name="expenseDate" id="selectone">
									<option value="2018-10-08 00:00:00">申请日期</option>
								</select></td>
							<td>&nbsp;&nbsp;</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入开始时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<!-- <td>结束:</td>
							<td><input id="endTime" name="orderTimeEnd" placeholder="请输入结束时间" class="layui-input laydate-icon">
							</td> -->
							<!-- 修改如下 -->
							<td>&nbsp;&nbsp;</td>
							<td>是否预算:
							<td><select class="form-control" name="budget">
									<option value="">请选择</option>
									<option value="1">是</option>
									<option value="0">否</option>
							</select></td>
							
							<td>&nbsp;&nbsp;</td>
							<td>是否核对:
							<td><select class="form-control" name="flag">
									<option value="">请选择</option>
									<option value="0">未核对</option>
									<option value="1">已核对</option>
							</select></td>
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
			
			<shiro:hasAnyRoles name="personnel">
   				 <p id="totalAll" style="text-align:center;"></p>
			</shiro:hasAnyRoles>
			
		</div>
	</div>
	
	<div style="display: none;" id="layuiOpen">
			<table id="tableBudget" class="table_th_search" lay-filter="tableBudget"></table>
		</div>
	
	<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug'
			}).define(
				['tablePlug', 'laydate', 'element'],
				function() {
					var $ = layui.jquery
						,layer = layui.layer //弹层
						,form = layui.form //表单
						,table = layui.table //表格
						,laydate = layui.laydate //日期控件
						,tablePlug = layui.tablePlug //表格插件
						,element = layui.element;
					
					
					//预算报销总计、报销总计、共计。数据获取
					function getDate(){
						if(document.getElementById("totalAll")!=null){
							$.ajax({
								url:"${ctx}/fince/countConsumptionMoney",
								success:function(result){
									if(0==result.code){
										return;
										var html="";
										var data=result.data;
										html+='<label>预算报销总计:'+data+'<label>'
											+'<label>报销总计：'+data+'</label>'
											+'<label>共计：'+data+'</label>';
										$('#total').append(html);
									}else{
										$('#total').append('<label style="color:red;">获取数据异常</label>');
									}
								}
							})
						}
					}
					
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
					/* laydate.render({
						elem: '#endTime',
						type: 'datetime',
					}); */
				 
					$.ajax({
						url: '${ctx}/system/user/findAllUser',
						type: "GET",
						async: false,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							$(result.data).each(function(i, o) {
								htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
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
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						height:'700px',
						url: '${ctx}/fince/getOrder' ,
						where:{
							ashoreCheckr:0,
					  		orderTimeBegin:firstdate,
					  		orderTimeEnd:lastdate,	
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
						cellMinWidth: 90,
						colFilterRecord: true,
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
								field: "content",
								title: "乙方电话",
								align: 'center',
								templet: function(d){
									return d.contact.conPhone
								}
							},{
								field: "content",
								title: "乙方其他信息",
								align: 'center',
								templet: function(d){
									return d.contact.conWechat
								}
							},{
								field: "",
								title: "线上or线下",
								align: 'center',
								templet: function(d){
									if(d.online==1){
				      					return "线上"
				      				}else{
				      					return "线下"
				      				}
								}
							}, {
								field: "salesNumber",
								title: "当月销售编号",
								align: 'center',
							}, {
								field: "money",
								title: "合同签订日期",
								align: 'center',
								templet: function(d){
									return /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.contractTime);
								}
							}, {
								field: "firstNames",
								title: "甲方",
								align: 'center',
							}, {
								field: "partyNames",
								title: "乙方",
								align: 'center',
							}, {
								field: "batchNumber",
								title: "当批批次号",
								align: 'center',
							}, {
								field: "contractNumber",
								title: "当批合同数量",
								align: 'center',
							}, {
								field: "contractPrice",
								title: "当批合同总价（元）",
								align: 'center',
							}, {
								field: "remarksPrice",
								title: "预付款备注",
								align: 'center',
							}, {
								field: "price",
								title: "单只价格",
								align: 'center',
							}, {
								field: "roadNumber",
								title: "未到岸",
								align: 'center',
							},{
								field: "ashoreNumber",
								title: "到岸数量",
								edit:'text',
								align: 'center',
							},{
								field: "",
								title: "预计借款日期",
								align: 'center',
								templet: function(d){
									return /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.ashoreTime);
								}
							},{
								field: "disputeNumber",
								title: "争议数字",
								edit:'text',
								align: 'center',
							}]
						],
								});
					
					//监听单元格编辑
					table.on('edit(tableData)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							id = data.id;
							var postData = {
								id:id,
								ashoreTime:data.ashoreTime,
								[field]:value
							}
							//调用新增修改
							mainJs.fUpdate(postData);
					});
					//监听搜索
					form.on('submit(LAY-search)', function(obj) {		//修改此处
						var field = obj.field;
						var orderTime=field.orderTimeBegin.split('~');
						field.orderTimeBegin=orderTime[0];
						field.orderTimeEnd=orderTime[1];
						table.reload('tableData', {
							where: field
						});  
					});
					
					
					//封装ajax主方法
					var mainJs = {
					//修改							
				    fUpdate : function(data){
				    	if(data.id==""){
				    		return;
				    	}
				    	$.ajax({
							url: "${ctx}/fince/addOrder",
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
				    }
					}

				}
			)
		</script>
</body>

</html>