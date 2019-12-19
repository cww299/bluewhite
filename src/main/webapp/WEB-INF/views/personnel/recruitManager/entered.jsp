<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训信息</title>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>应聘人：</td>
				<td><select lay-search id="searchName" lay-filter="searchName"><option value="">获取数据中...</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>招聘人：</td>
				<td><input type="text" readonly class="layui-input" style="width: 150px;" id="recruitName"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>摊到招聘费用：&nbsp;</td>
				<td><input type="text" readonly class="layui-input" style="width: 150px;" id="recruitMoney" value="0"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>培训费用：&nbsp;</td>
				<td><input type="text" readonly class="layui-input" style="width: 150px;" id="trainMoney" value="0"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>招聘人的奖励金额：&nbsp;</td>
				<td><input type="text" readonly class="layui-input" style="width: 150px;" id="awardMoney" value="0"></td>
				<td>&nbsp;&nbsp;</td>
				<td>开始时间:</td>
				<td><input id="startTime" style="width: 190px;"  placeholder="请输入面试时间" class="layui-input laydate-icon">
				</td>
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
		<table class="layui-form" id="trainTable" lay-filter="trainTable"></table>
	</div>
</div>

<!-- 查看汇总弹窗 -->
<div style="display:none;padding:4px;" id="lookoverDiv">
	<table class="layui-form">
		<tr>
			<td><input type="text" name="time" id="totalTime" lay-verify="required" class="layui-input"></td>
			<td> &nbsp;&nbsp;</td>
			<td><button type="button" lay-filter="searchTotal"  lay-submit class="layui-btn layui-btn-sm">搜索</button> </td>
		</tr>
	</table>
	<table class="layui-table" id="totalTable" lay-filter="totalTable"></table>
</div>
<!-- 查看部门汇总弹窗 -->
<div style="display:none;padding:4px;" id="departmentDiv">
	<table class="layui-form">
		<tr>
			<td><input type="text" name="time" id="totalDepartmentTime" lay-verify="required" class="layui-input"></td>
			<td> &nbsp;&nbsp;</td>
			<td><button type="button" lay-filter="searchDepartmentTotal"  lay-submit class="layui-btn layui-btn-sm">搜索</button> </td>
		</tr>
	</table>
	<table class="layui-table" id="totalDepartmentTable" lay-filter="totalDepartmentTable"></table>
</div>

<form action="" id="layuiadmin-form-admin2"
		style="padding: 20px 0px 0 50px; display:none;  text-align:">
		<div class="layui-form layui-form-pane" lay-filter="layuiadmin-form-admin">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">应聘人</label>
				<div class="layui-input-inline">
					<input  style="width:190px;" lay-filter="id" id="name" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
		
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">招聘人</label>
				<div class="layui-input-inline">
					<input  style="width:190px;" lay-filter="id" id="recruitName2" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">开始时间</label>
					<div class="layui-input-inline">
						<input type="text" 
							style="width: 190px; position: absolute; float: left;" name="startTime"
							id="startTime2" lay-verify="tradeDaysTime" placeholder="请输入日期"
							class="layui-input laydate-icon">
					</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">结束时间</label>
					<div class="layui-input-inline">
						<input type="text" 
							style="width: 190px; position: absolute; float: left;" name="endTime"
							id="endTime2" lay-verify="tradeDaysTime" placeholder="请输入日期"
							class="layui-input laydate-icon">
					</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">培训内容</label>
				<div class="layui-input-inline">
					<input name="train" style="width:190px;" lay-filter="id" id="train" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
	
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">培训导师</label>
				<div class="layui-input-inline" id="userId2">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">培训成本</label>
				<div class="layui-input-inline">
					<input name="price" style="width:190px;" lay-filter="id" id="price" lay-search="true" class="layui-input laydate-icon">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">是否合格</label>
				<div class="layui-input-inline">
					<select class="layui-input laydate-icon" name="qualified"><option value="1">是</option><option value="0">否</option></select>
				</div>
			</div>
		</div>
	</form>

</body>

<!-- 表格工具栏模板 -->
<script type="text/html" id="trainToolbar">
<div class="layui-btn-container layui-inline">
	<span class="layui-btn layui-btn-sm" lay-event="addTempData">入职培训</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
</div>
</script>

</body>

<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil: 'layui/myModules/myutil',
}).define(
	['tablePlug','laydate','myutil'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		
		var allPlatform = []
			,allTeacher = [];
		laydate.render({
			elem: '#startTime',
			type: 'date',
			range: '~',
		});
		laydate.render({
			elem: '#startTime2',
			type: 'datetime',
		});
		laydate.render({
			elem: '#endTime2',
			type: 'datetime',
		});
		laydate.render({
			elem: '#endTime',
			type: 'date',
			range: '~',
		});
		getRecruit();		
		allTeacher = myutil.getDataSync({ url:'${ctx}/system/user/findUserList?isAdmin=0',	 })
	 	tablePlug.smartReload.enable(true);  
	 	form.on('select(searchName)',function(obj){
	 		var time;
	 		layui.each($(obj.elem).find('option'),function(index,item){
	 			if($(item).attr('value') ==obj.value)
	 				$('#recruitName').val($(item).attr('data-recruitName')) 
	 				time = $(item).attr('data-time');
	 		})
	 		if(obj.value==''){
	 			table.reload('trainTable',{ 
	 				url:'${ctx}/personnel/getAdvertisement?type=1',
	 				page:{ curr:1 } })
				$('#recruitMoney').val(0);	 
	 			$('#trainMoney').val(0);	 
	 			$('#awardMoney').val(0);	 
	 		}
		 	else{
		 		table.reload('trainTable',{
		 			url:'${ctx}/personnel/getAdvertisement?type=1&mold=0&recruitId='+obj.value,
		 			page : { curr :1 },
		 		})
		 		$.ajax({
		 			url:'${ctx}/personnel/getBasics?time='+time,
		 			success:function(r){
		 				$('#recruitMoney').val(r.data.sharePrice);	 
		 			}
		 		}) 
		 		$.ajax({
		 			url:'${ctx}/personnel/findPrice?id='+obj.value,
		 			success:function(r){
			 			$('#awardMoney').val(r.data.receivePrice);	 
		 			}
		 		}) 
		 		$.ajax({
		 			url:'${ctx}/personnel/findRecruitId?recruitId='+obj.value,
		 			success:function(r){
		 				$('#trainMoney').val(r.data.price);	  
		 			}
		 		}) 
		 	}
	 	})
	 			
		table.render({
			elem:'#trainTable',
			url:'${ctx}/personnel/getAdvertisement?type=1&mold=0',
			toolbar:'#trainToolbar',
			page:true,
			size:'lg',
			smartReloadModel: true,    // 开启智能重载
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'应聘人', 	 field:'name',edit: false, 	templet:'<span>{{ d.recruitName.name }}</span>'},
			       {align:'center', title:'招聘人',  field:'recruitName',edit: false, 	templet:'<span>{{ d.recruitName.recruitName }}</span>' },
			       {align:'center', title:'开始时间',field:'startTime',edit: false, 	},
			       {align:'center', title:'结束时间',field:'endTime',	  edit: false,}, 
			       {align:'center', title:'培训内容',field:'train',   edit: true,},
			       {align:'center', title:'培训导师',field:'userId',  edit: false, templet: function(d){return d.user==null ? "" : d.user.userName}, },
			       {align:'center', title:'培训成本',field:'price',   edit: true,},
			       {align:'center', title:'是否合格',field:'qualified',edit: false, templet: getQualifiedSelect(),  },
			       {align:'center', title:'培训类型',field:'mold',edit: false, templet: function(d){if(d.mold==0){return "<span class='layui-badge layui-bg-green'>入职培训</span>"} if(d.mold==1){return "<span class='layui-badge'>内部培训</span>"}},  },
			       ]],
			done:function(){
				var tableView = this.elem.next();
				var tableElem = this.elem.next('.layui-table-view');
				;(function(arr){
					for(var key in arr){
						var field = arr[key];
						layui.each(tableView.find('td[data-field="'+field+'"]'), function(index, tdElem) {	//渲染开始时间，并修改
							(function(f){
								laydate.render({
									elem: tdElem.children[0],
									format: 'yyyy-MM-dd HH:mm:ss',
									done: function(value, date) {
										var id = table.cache[tableView.attr('lay-id')][index].id;
										var postData = { id: id,};
										postData[f] = value;
										updateAjax(postData);
									}
								})
							})(field);
						})
					}
				})(['startTime','endTime']);
				form.on('select(tableSelect)',function(obj){				// 监听是否合格下拉框，判断是否为新增行、是的话，记录选择的数据如果不是则进行修改
					var trElem = $(obj.elem).closest('tr');
					var index = trElem.data('index');			//当前行的索引
					var field = $(obj.elem).closest('td').data('field');
					table.cache['trainTable'][index][field] = obj.value;
					if(index<0)
						return;
					var id = table.cache['trainTable'][index]['id'];
					var postData = { id : id, };
					postData[field] = obj.value;
					updateAjax(postData);
				})
				form.render();
			}
		})
		table.on('toolbar(trainTable)',function(obj){
			var config = obj.config;
			var btnElem = $(this);
			var tableId = config.id;
			switch(obj.event){
			case 'addTempData': addTempData();
				break;
			case 'deleteSome': deleteSome();
				break;
			}
		})
		table.on('edit(trainTable)',function(obj){	
			if(!obj.data.id)
				return;
			var postData = null;
			if(obj.field == 'price'){			// 监听价格
				if(isNaN(obj.value))
					layer.msg('培训成本只能为数字',{icon:2});		
				else{
					postData = { id:obj.data.id, price: obj.value};
				}
			}else{
				postData = { id:obj.data.id, train: obj.value};
			}
			postData && updateAjax(postData);	//如果有数据，则进行修改
			table.reload('trainTable');
		})
		laydate.render({
	 		elem: '#totalDepartmentTime',
	 		type: 'month',
	 	})
	 	form.on('submit(searchDepartmentTotal)',function(obj){
	 		table.reload('totalDepartmentTable',{
		 		url: '${ctx}/personnel/findBasicsSummary',
		 		where : { time : obj.field.time+'-01 00:00:00'},
	 		})
	 	})
	 	var searchTime = '';  //记录搜索查询时间
	 	form.on('submit(searchTotal)',function(obj){
	 		searchTime = obj.field.time+'-01 00:00:00';
	 		table.reload('totalTable',{
		 		url: '${ctx}/personnel/getBasics',
		 		where : { time : searchTime},
	 		})
	 	})
	 	table.on('edit(totalTable)',function(obj){
	 		var load =layer.load(1);
	 		if(isNaN(obj.value)){
	 			layer.msg("招聘人员费用只能为数字！",{icon:2});
	 		}
	 		else{
		 		$.ajax({
		 			url : '${ctx}/personnel/addBasics',
		 			type: 'post',
		 			data: { recruitUserPrice: parseFloat(obj.value), id:table.cache['totalTable'][0]['id'], time:searchTime, },
		 			async: false,
		 			success: function(r){
		 				var icon = (r.code==0)? 1 : 2;
		 				layer.msg(r.message,{icon:icon});
		 			}
		 		})
	 		} 
	 		table.reload('totalTable');
	 		layer.close(load);
	 	})
	 	laydate.render({
	 		elem: '#totalTime',
	 		type: 'month',
	 	})
	 	//监听搜索
		form.on('submit(LAY-search)', function(data) {
			var field = data.field;
			var orderTime=$("#startTime").val().split('~');
			field.orderTimeBegin=orderTime[0]+' '+'00:00:00';
			field.orderTimeEnd=orderTime[1]+' '+'23:59:59';
			table.reload('trainTable', {
				where: field,
				page: {
					curr:1
                }
			});
		});
		function addTempData(){
	 		if($('#searchName').val()==''){
	 			layer.msg('请选择应聘对象',{icon:2});
	 			return;
	 		}
			var name = $('#searchName').find('option[value="'+$('#searchName').val()+'"]').html().split(' ')[0];
			$("#name").val(name)
	 		var recruitname = $('#searchName').find('option[value="'+$('#searchName').val()+'"]').data('recruitname');
			$("#recruitName2").val(recruitname)
			var testTime = $('#searchName').find('option[value="'+$('#searchName').val()+'"]').data('time');
			$("#startTime2").val(testTime)
			var orderTime=testTime.split(' ');
			var endTime=orderTime[0]+' '+'00:30:00'
			$("#endTime2").val(endTime)
	 		var	dicDiv=$("#layuiadmin-form-admin2");
		var index=layer.open({
				type:1,
				title:'入职培训',
				area:['23%','62%'],
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
						data.field.type=1
						data.field.mold=0
						data.field.recruitId=$('#searchName').val()
						$.ajax({
							url: '${ctx}/personnel/addAdvertisement',
							type: 'post',
							async: false,
							data: data.field,
							success: function(r){
								if(r.code==0)
									layer.msg(r.message,{icon:1});
								else
									layer.msg(r.message,{icon:2});
							}
						})
						document.getElementById("layuiadmin-form-admin2").reset(); 
			        	layui.form.render();
			        	table.reload('trainTable',{ page:{ curr:1 } })
			        	layer.close(index);
					})
				},end:function(){ 
		        	document.getElementById("layuiadmin-form-admin2").reset();
		        	layui.form.render();
				  }
			})
	 	
	 	}
		function deleteSome(){
			var choosed=layui.table.checkStatus('trainTable').data;
			if(choosed.length<1)
				return layer.msg('请选择相关信息',{icon:2});
			layer.confirm("是否确认删除？",function(){
				var ids=[];
				for(var i=0;i<choosed.length;i++){
					ids.push(choosed[i].id);
				}
				var load=layer.load(1);
				$.ajax({
					url:"${ctx}/personnel/deleteAdvertisement",
					data:{ ids:ids },
					async: false,
					traditional: true,
					success:function(result){
						var icon = 2;
						if(0==result.code){
							icon = 1;
							table.reload('trainTable',{ page: {curr: 1}});
						}
						layer.msg(result.message,{icon:icon});
					},
				})
				layer.close(load);
			}) 
		}
		function updateAjax(postData){			//修改数据
			var load = layer.load(1);
			$.ajax({
				url:'${ctx}/personnel/addAdvertisement',
				type: 'post',
				data: postData,
				async: false,
				success: function(r){
					var icon = 2;
					if(r.code==0){
						icon=1;
						table.reload('trainTable');
					}
					layer.msg(r.message,{icon:icon});
				}
			})
			layer.close(load);
		}
		getUserIdSelect();
		function getUserIdSelect(){
	 		return function(d){
	 			var html = '<select lay-filter="tableSelect" lay-search>';
	 			var htm2 = '<select lay-filter="tableSelect2" name="userId" lay-search>';
	 			layui.each(allTeacher,function(index,item){
	 				var selected = (item.id==d.userId?"selected":"");
	 				html+='<option value="'+item.id+'" '+selected+'>'+item.userName+'</option>'
	 				htm2+='<option value="'+item.id+'" '+selected+'>'+item.userName+'</option>'
	 			})
	 			html+='</select>';
	 			$("#userId2").html(htm2);
	 			form.render();
	 			if(d.userId == '')
					layui.table.cache['trainTable'][d.LAY_INDEX]['userId'] = allTeacher[0].id;
	 			return html;
	 		} 
	 	}
	 	function getQualifiedSelect(){ //0 no 1 yes
	 		return function(d){
	 			var html = '<select lay-filter="tableSelect" lay-search>';
	 			html+='<option value="0" '+ (d.qualified == 0? "selected":"")+'>否</option>';
	 			html+='<option value="1" '+ (d.qualified == 1? "selected":"")+'>是</option>'
	 			html+='</select>';
	 			if(d.qualified == null)
					layui.table.cache['trainTable'][d.LAY_INDEX]['qualified'] = 0;
	 			return html;
	 		}
	 	}
		function getRecruit(){
			$.ajax({
				url: '${ctx}/personnel/listRecruit',
				success: function(r){
					var html = '<option value="">应聘人</option>';
					layui.each(r.data,function(index,item){
						html+='<option value="'+item.id+'" data-recruitName="'+item.recruitName+'" data-time="'+item.testTime+'">'+
							item.name+'  ('+item.orgName.name+'  '+item.position.name+')</option>';
					})
					$('#searchName').html(html);
					form.render();
				}
			})
		}
	}//end define function
)//endedefine
</script>

</html>