<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训信息</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
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
				<td><input type="text" readonly class="layui-input" id="recruitName"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>摊到招聘费用：&nbsp;</td>
				<td><input type="text" readonly class="layui-input" id="recruitMoney" value="0"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>培训费用：&nbsp;</td>
				<td><input type="text" readonly class="layui-input" id="trainMoney" value="0"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>招聘人的奖励金额：&nbsp;</td>
				<td><input type="text" readonly class="layui-input" id="awardMoney" value="0"></td>
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
</body>

<!-- 表格工具栏模板 -->
<script type="text/html" id="trainToolbar">
<div class="layui-btn-container layui-inline">
	<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
	<span class="layui-btn layui-btn-sm" lay-event="lookoverTotal">费用汇总</span>
	<span class="layui-btn layui-btn-sm" lay-event="departmentTotal">部门支出</span>
</div>
</script>

</body>

<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).define(
	['tablePlug','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, tablePlug = layui.tablePlug;
		
		var allPlatform = []
			,allTeacher = [];
		
		getRecruit();		
		getTeacher();
	 	tablePlug.smartReload.enable(true);  
	 	form.on('select(searchName)',function(obj){
	 		var time;
	 		layui.each($(obj.elem).find('option'),function(index,item){
	 			if($(item).attr('value') ==obj.value)
	 				$('#recruitName').val($(item).attr('data-recruitName')) 
	 				time = $(item).attr('data-time');
	 		})
	 		if(obj.value==''){
	 			table.reload('trainTable',{ data:[], url:'', })
				$('#recruitMoney').val(0);	 
	 			$('#trainMoney').val(0);	 
	 			$('#awardMoney').val(0);	 
	 		}
		 	else{
		 		table.reload('trainTable',{
		 			url:'${ctx}/personnel/getAdvertisement?type=1&recruitId='+obj.value,
		 			page : { curr :1 },
		 		})
		 		$.ajax({
		 			url:'${ctx}/personnel/getBasics?time='+time,
		 			success:function(r){
		 				$('#recruitMoney').val(r.data.occupyPrice);	 
		 			}
		 		}) 
		 		$.ajax({
		 			url:'${ctx}/personnel/findPrice?id='+obj.value,
		 			success:function(r){
			 			$('#awardMoney').val(r.data.receivePrice);	 
		 			}
		 		}) 
		 		$.ajax({
		 			url:'${ctx}/personnel/findRecruitId?id='+obj.value,
		 			success:function(r){
		 				$('#trainMoney').val(r.data.trainPrice);	  
		 			}
		 		}) 
		 	}
	 	})
	 			
		table.render({
			elem:'#trainTable',
			data:[],
			toolbar:'#trainToolbar',
			loading:true,
			page:true,
			size:'lg',
			smartReloadModel: true,    // 开启智能重载
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'开始时间', 	field:'startTime',edit: false, 	},
			       {align:'center', title:'结束时间',   field:'endTime',	  edit: false,}, 
			       {align:'center', title:'培训内容',   field:'train',   edit: true,},
			       {align:'center', title:'培训导师',   field:'userId',  edit: false, templet: getUserIdSelect(), },
			       {align:'center', title:'培训成本',   field:'price',   edit: true,},
			       {align:'center', title:'是否合格',   field:'qualified',edit: false, templet: getQualifiedSelect(),  },
			       ]],
			done:function(){
				var tableView = this.elem.next();
				var tableElem = this.elem.next('.layui-table-view');
				layui.each(tableView.find('td[data-field="startTime"]'), function(index, tdElem) {	//渲染开始时间，并修改
					laydate.render({
						elem: tdElem.children[0],
						format: 'yyyy-MM-dd HH:mm:ss',
						done: function(value, date) {
							var id = table.cache[tableView.attr('lay-id')][index].id
							var postData = { id: id,    startTime: value, }
							updateAjax(postData);
						}
					})
				})
				layui.each(tableView.find('td[data-field="endTime"]'), function(index, tdElem) {
					laydate.render({
						elem: tdElem.children[0],
						format: 'yyyy-MM-dd HH:mm:ss',
						done: function(value, date) {
							var id = table.cache[tableView.attr('lay-id')][index].id
							var postData = { id: id,    endTime: value, }
							updateAjax(postData);
						}
					})
				})
				form.on('select(qualifiedSelect)',function(obj){				// 监听是否合格下拉框，判断是否为新增行、是的话，记录选择的数据如果不是则进行修改
					var trElem = $(obj.elem).closest('tr');
					var index = trElem.data('index');			//当前行的索引
					table.cache['trainTable'][index]['qualified'] = obj.value;
					if(index<0)
						return;
					var id = table.cache['trainTable'][index]['id'];
					var postData = { id : id, qualified : obj.value, };
					updateAjax(postData);
				})
				form.on('select(userSelect)',function(obj){
					var trElem = $(obj.elem).closest('tr');
					var index = trElem.data('index');			
					table.cache['trainTable'][index]['userId'] = obj.value;
					if(index<0)
						return;
					var id = table.cache['trainTable'][index]['id'];
					var postData = { id : id, userId : obj.value, };
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
			case 'cleanTempData': table.cleanTemp('trainTable');
				break;
			case 'saveTempData': saveTempData();
				break;
			case 'deleteSome': deleteSome();
				break;
			case 'lookoverTotal': lookoverTotal();
				break;
			case 'departmentTotal': departmentTotal();
				break;
			}
		})
		table.on('edit(trainTable)',function(obj){	
			if(!obj.data.id)
				return;
			var postData ={};
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
		function departmentTotal(){
	 		layer.open({
	 			type:1,
	 			content: $('#departmentDiv'),
	 			shadeClose : true,
	 			area : ['60%','60%'],
	 		})
	 		table.render({
	 			elem: '#totalDepartmentTable',
	 			data: [],
				parseData:function(ret){ return { data:ret.data, msg:ret.message, code:ret.code } },
				cols: [[
				       {align:'center', title:'部门',   field:'username',	  }, 
				       {align:'center', title:'部门付出奖金',   field:'ReceivePrice',  },
				       {align:'center', title:'培训费用',   field:'trainPrice',  },
				       {align:'center', title:'该部门占应聘费用',   field:'occupyPrice',    },
		 			]],
	 		})
	 	}
	 	form.on('submit(searchDepartmentTotal)',function(obj){
	 		table.reload('totalDepartmentTable',{
		 		url: '${ctx}/personnel/findBasicsSummary',
		 		where : { time : obj.field.time+'-01 00:00:00'},
	 		})
	 	})
		function lookoverTotal(){
	 		layer.open({
	 			type:1,
	 			content: $('#lookoverDiv'),
	 			shadeClose : true,
	 			area : ['60%','60%'],
	 		})
	 		table.render({
	 			elem: '#totalTable',
	 			data: [],
	 			parseData:function(ret){ 
	 				var data = [];
	 				data.push(ret.data);
	 				return { data:data,  msg:ret.message, code:ret.code } 
	 			},
	 			cols: [[
				       {align:'center', title:'投入平台广告费',   field:'advertisementPrice',	  }, 
				       {align:'center', title:'应聘人员数量',   field:'number',  },
				       {align:'center', title:'每人占到应聘费用',   field:'occupyPrice',    },
				       {align:'center', title:'面试招聘人员费用',   field:'recruitUserPrice',  edit: true,  },
				       {align:'center', title:'培训费用',   field:'trainPrice',    },
	 			        ]],
	 		})
	 	}
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
		function addTempData(){
	 		if($('#searchName').val()==''){
	 			layer.msg('请选择应聘对象',{icon:2});
	 			return;
	 		}
			allField = {train: '', price: '', startTime:'',endTime:'',type:'1',qualified:'', userId:'', recruitId:$('#searchName').val(),};
			table.addTemp('trainTable',allField,function(trElem) {
				var startTiemTd = trElem.find('td[data-field="startTime"]')[0];
				laydate.render({
					elem: startTiemTd.children[0],
					format: 'yyyy-MM-dd HH:mm:ss',
					done: function(value, date) {
						var trElem = $(this.elem[0]).closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						table.cache['trainTable'][trElem.data('index')]['startTime'] = value;
					}
				}) 
				var endTimeTd = trElem.find('td[data-field="endTime"]')[0];	
				laydate.render({
					elem: endTimeTd.children[0],
					format: 'yyyy-MM-dd HH:mm:ss',
					done: function(value, date) {
						var trElem = $(this.elem[0]).closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						table.cache['trainTable'][trElem.data('index')]['endTime'] = value;
					}
				}) 
			});
	 	}
		function saveTempData(){
			var tempData = table.getTemp('trainTable').data;
			for(var i=0;i<tempData.length;i++){
				var t = tempData[i];
				if(!t.endTime || !t.price || !t.startTime || !t.train){
					layer.msg('新增数据字段不能为空！',{icon:2});
					return;
				}
				if(isNaN(t.price)){
					layer.msg('培训成本只能为数字！',{icon:2});
					return;
				}
			}
			var load = layer.load(1);
			var successAdd = 0;
			for(var i=0;i<tempData.length;i++){
				$.ajax({
					url: '${ctx}/personnel/addAdvertisement',
					type: 'post',
					async: false,
					data:  tempData[i],
					success: function(r){
						if(r.code==0)
							successAdd++;
						else
							layer.msg(r.message,{icon:2});
					}
				}) 
			}
			table.cleanTemp('trainTable');
			table.reload('trainTable',{ page:{ curr:1 } })
			if(successAdd==tempData.length)
				layer.msg('成功新增：'+successAdd+'条数据',{icon:1});
			else
				layer.msg('新增异常：'+(tempData.length-successAdd)+'条数据',{icon:2});
			layer.close(load);
		}
		function deleteSome(){
			var choosed=layui.table.checkStatus('trainTable').data;
			if(choosed.length<1){
				layer.msg('请选择相关信息',{icon:2});
				return;
			}
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
					error:function(result){
						layer.msg('ajax异常',{icon:2});
					}
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
		function getUserIdSelect(){
	 		return function(d){
	 			var html = '<select lay-filter="userSelect" lay-search>';
	 			layui.each(allTeacher,function(index,item){
	 				var selected = (item.id==d.userId?"selected":"");
	 				html+='<option value="'+item.id+'" '+selected+'>'+item.userName+'</option>'
	 			})
	 			html+='</select>';
	 			if(d.userId == '')
					layui.table.cache['trainTable'][d.LAY_INDEX]['userId'] = allTeacher[0].id;
	 			return html;
	 		} 
	 	}
	 	function getQualifiedSelect(){ //0 no 1 yes
	 		return function(d){
	 			var html = '<select lay-filter="qualifiedSelect" lay-search>';
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
						html+='<option value="'+item.id+'" data-recruitName="'+item.recruitName+'" data-time="'+item.testTime+'">'+item.name+'</option>';
					})
					$('#searchName').html(html);
					form.render();
				}
			})
		}
		function getTeacher(){
			$.ajax({
				url:'${ctx}/system/user/findUserList',	
				success:function(r){
					allTeacher = r.data;
				}
			}) 
		}
	}//end define function
)//endedefine
</script>

</html>