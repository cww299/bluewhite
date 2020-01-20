<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>奖励汇总</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>查询时间:</td>
							<td><input id="monthDate3" style="width: 180px;" placeholder="请输入时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td>招聘人:</td>
							<td><select class="form-control" name="recruitId" id="recruitIds">
							
							</select></td>
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
</div>

<form action="" id="layuiadmin-form-admin"
		style="padding: 20px 40px 0 60px; display: none;">
		<div class="layui-form layui-form-pane" lay-filter="layuiadmin-form-admin ">
			<input type="text" name="id" id="usID" style="display:none;">

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">时间</label>
				<div class="layui-input-inline">
					<input type="text"  name="time" id="time" lay-verify="required" class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">招聘人</label>
				<div class="layui-input-inline">
						<select name="recruitId"  lay-filter="recruitId" lay-verify="required" id="recruitId" lay-search="true">
								
						</select>

					</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">奖励</label>
				<div class="layui-input-inline">
					<input type="text"  name="price"  id="price"
						lay-verify="required" placeholder="请输入奖励"
						class="layui-input laydate-icon">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">被聘人</label>
				<div class="layui-input-inline">
				<select name="coverRecruitId"  lay-filter="coverRecruitId" lay-verify="required" id="coverRecruitId" lay-search="true">
								
						</select>
				</div>
			</div>

		   <div class="layui-form-item">
				<label class="layui-form-label" style="width: 130px;">备注</label>
				<div class="layui-input-inline">
					<input type="text"  name="remarks"
						id="remarks" 
						 class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item" style="display: none;">
				<label class="layui-form-label" style="width: 130px;"></label>
				<div class="layui-input-inline">
					<input type="text"  name="id"
						id="id" 
						 class="layui-input">
				</div>
			</div>
		</div>
	</form>	

</body>
<!-- 表格工具栏模板 --> 
<script type="text/html" id="personToolbar">
<div>
	<span class="layui-btn layui-btn-sm" lay-event="add">新增奖励</span>
	<span class="layui-btn layui-btn-sm" lay-event="update">修改奖励</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
</div>
</script>
</body>
<script>
var allCoverRecruit = [];	//所有被招聘人
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).use(
	['laytpl','jquery','layer','form','table','laydate','tablePlug'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, tablePlug = layui.tablePlug
		, laydate = layui.laydate
		, laytpl = layui.laytpl;
		
		laydate.render({
			elem: '#monthDate3',
			type : 'date',
			range:'~'
		});
		laydate.render({
			elem: '#time',
			type : 'datetime',
		});
		tablePlug.smartReload.enable(true);  
		$.ajax({								//获取部门列表数据，部门下拉框的填充
		      url:"${ctx}/personnel/listGroupRecruit",
		      type:"GET",
		      async:false,
    		  success: function (result) {				//初始填充部门
    			  var htmls="<option value=''>请选择</option>";
    			  $(result.data).each(function(k,j){
    				htmls +='<option value="'+j.recruitId+'">'+j.recruitName+'</option>'
    			  });
    		  $("#recruitId").html(htmls);
    		  $("#recruitIds").html(htmls);
    		  layui.form.render()
		      }
		  });
		 form.on('select(recruitId)', function(data){
			 var html="<option value=''>请选择</option>"
      			$.ajax({								
      				  url:"${ctx}/personnel/findCondition",
				      data:{
				    	  recruitId:data.value
				      },
				      type:"GET",
				      async:false,
		      		  success: function (result) {				
		      			  
		      			  	$(result.data).each(function(i,o){
			      				  html +='<option  value="'+o.id+'">'+o.name+'</option>'
		      				});  
		      			$("#coverRecruitId").html(html); 
		      			layui.form.render()
				      }
				  });
		 })
		form.on('submit(LAY-search2)', function(obj) {
						onlyField=obj.field;
						var orderTime=$("#monthDate3").val().split('~');
						if($("#monthDate3").val()!=""){
							onlyField.orderTimeBegin=orderTime[0]+' '+'00:00:00';
							onlyField.orderTimeEnd=orderTime[1]+' '+'23:59:59';	
						}
						onlyField.type=onlyField.type;
						table.reload("layuiShare2", {
							url: '${ctx}/personnel/getReward',
							where:onlyField,
			              })
					})
		table.render({
			elem:'#layuiShare2',
			url: '${ctx}/personnel/getReward' , 
			request:{ pageName:'page', limitName:'size' },
			limit:15,
			limits:[15,20,50,100,200,500,1000],
			page:true,
			toolbar: '#personToolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
			totalRow: true,		 //开启合计行 */
			parseData: function(ret) {
				return {
					code: ret.code,
					msg: ret.message,
					data: ret.data.rows,
					count:ret.data.total,
				}
			},
			cols: [[
				{align:'center', type:'checkbox',totalRowText: '合计'},
				{align:'center', title:'时间',   field:'time',templet:function(d){ return d.time==null ? "" : /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.time)}},
				{align:'center', title:'招聘人',   field:'recruitId', edit:false,templet:function(d){ return d.recruitName.recruitName; }},
				{align:'center', title:'奖励',   field:'price', edit:false,totalRow: true},
				{align:'center', title:'被聘人', templet:function(d){return d.recruitName==null ? "" : d.recruitName.name},edit:false,},
				{align:'center', title:'入职时间',   field:'entry', edit:false,templet:function(d){ return d.recruitName.user==null ? "" : /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.recruitName.user.entry)}},
				{align:'center', title:'离职时间',   field:'quitDate', edit:false,templet:function(d){ return d.recruitName.user.quitDate==null ? "" : /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.recruitName.user.quitDate)}},
				{align:'center', title:'备注',   field:'remarks',	edit:false,},
			]],
					});
		table.on('toolbar(layuiShare)',function(obj){
			var tableId = obj.config.id;
			switch(obj.event){
			case 'delete':
				var checkedIds = tablePlug.tableCheck.getChecked(tableId);
				layer.confirm('删除数据将会删除所有关联数据 是否删除' + checkedIds.length + '条记录？', function() {
					var postData = {
						ids: checkedIds,
					}
					$.ajax({
						url:'${ctx}/personnel/deleteReward',
						data: postData,
						traditional: true,
						type: "GET",
						success: function(result) {
							if(0 == result.code) {
								var configTemp = tablePlug.getConfig("layuiShare2");
					            if (configTemp.page && configTemp.page.curr > 1) {
					              table.reload("layuiShare2", {
					                page: {
					                  curr: configTemp.page.curr - 1
					                }
					              })
					            }else{
					            	table.reload("layuiShare2", {
						                page: {
						                }
						              })
					            };
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
							layer.msg("操作失败！", {
								icon: 2
							});
						}
					});
					
				});
			break;
			case 'update' :
				var checked = layui.table.checkStatus('layuiShare2').data;
				var data = table.checkStatus(tableId).data;
				if(checked.length>1){
					return layer.msg("只能选择一条",{icon: 2})
				}
				 var html="<option value=' '>请选择</option>"
		      			$.ajax({								
		      				  url:"${ctx}/personnel/findCondition",
						      data:{
						    	  recruitId:data[0].recruitId
						      },
						      type:"GET",
						      async:false,
				      		  success: function (result) {				
				      			  
				      			  	$(result.data).each(function(i,o){
					      				  html +='<option  value="'+o.id+'">'+o.name+'</option>'
				      				});  
				      			$("#coverRecruitId").html(html); 
				      			layui.form.render()
						      }
						  });
				var dicDiv=$('#layuiadmin-form-admin');
				var ins=layer.open({
					type:1,
					title:'修改奖励',
					area:['28%','80%'],
					btn:['确认','取消'],
					content:dicDiv,
					id: 'LAY_layuipro' ,
					btnAlign: 'c',
				    moveType: 1, //拖拽模式，0或者1
					success : function(layero, index) {
						$("#time").val(data[0].time);
						$("#recruitId").val(data[0].recruitId);
						$("#price").val(data[0].price);
						$("#coverRecruitId").val(data[0].coverRecruitId);
						$("#remarks").val(data[0].remarks);
						$("#id").val(data[0].id);
						layui.form.render();
			        	layero.addClass('layui-form');
						// 将保存按钮改变成提交按钮
						layero.find('.layui-layer-btn0').attr({
							'lay-filter' : 'addRole',
							'lay-submit' : ''
						})
			        },
					yes:function(){
						form.on('submit(addRole)', function(data) {
							data.field.type=1;
							 saveData(data.field);
							 document.getElementById("layuiadmin-form-admin").reset();
					        	layui.form.render();
					        	layer.close(ins);
						})
					},end:function(){ 
			        	document.getElementById("layuiadmin-form-admin").reset();
			        	layui.form.render();
			        	layer.close(ins);
					  }
				})
				break;
			case 'add' : 
				var dicDiv=$('#layuiadmin-form-admin');
				layer.open({
					type:1,
					title:'新增奖励',
					area:['28%','80%'],
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
							data.field.type=1;
							 saveData(data.field);
							 document.getElementById("layuiadmin-form-admin").reset();
					        	layui.form.render();
						})
					},end:function(){ 
			        	document.getElementById("layuiadmin-form-admin").reset();
			        	layui.form.render();
					  }
				})
				break;
			} 
		})

		function saveData(data){
			$.ajax({
				url:'${ctx}/personnel/addReward',
				type:'post',
				data:data,
				async : false,
				success:function(result){
					if(0==result.code){
						 table.reload("layuiShare2", {
				                page: {
				                }
				              })   
				              layer.msg("成功", {
									icon: 1,
									time:800
								});
					}else{
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
			})
		}
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	}//end define function
)//endedefine
</script>

</html>