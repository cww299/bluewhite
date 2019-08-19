<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广告信息</title>
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
				<td><select name="platformId" lay-search id="searchPlatformSelect"><option value="">获取数据中....</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="recruitTable" lay-filter="recruitTable"></table>
	</div>
</div>

</body>

<!-- 表格工具栏模板 -->
<script type="text/html" id="recruitToolbar">
<div class="layui-btn-container layui-inline">
	<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
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
		
		var allPlatform = [];
		getPlatform();
	 	tablePlug.smartReload.enable(true);  
	 			
	 	laydate.render({
			elem: '#searchTime',
			range:'~',
	 	})
	 	form.on('submit(search)',function(obj){
			table.reload('recruitTable',{
				where:{
					platformId : obj.field.platformId,
				},
				page : { curr:1 },
			})
		}) 
		table.render({
			elem:'#recruitTable',
			url:'${ctx}/personnel/getAdvertisement?type=0',
			toolbar:'#recruitToolbar',
			loading:true,
			page:true,
			size:'lg',
			smartReloadModel: true,    // 开启智能重载
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'广告平台',   field:'platformId', edit: false, templet: getPlatformSelect()},
			       {align:'center', title:'投放费用',   field:'price',     edit:true,},
			       {align:'center', title:'开始时间', 	field:'startTime',edit: false,	},
			       {align:'center', title:'结束时间',   field:'endTime',	  edit: false,   },
			       {align:'center', title:'合格简历数',   field:'number',edit: true,   },
			       {align:'center', title:'待定简历数',   field:'number2',edit: true,   }, 
			       {align:'center', title:'不合格简历数',   field:'number3',edit: true,   }, 
			       {align:'center', title:'收取简历数',   field:'number4',edit: true,   }, 
			       ]],
			done:function(){
				var tableView = this.elem.next();
				var tableElem = this.elem.next('.layui-table-view');
				layui.each(tableView.find('td[data-field="startTime"]'), function(index, tdElem) {
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
				form.render();
				form.on('select(platformSelect)',function(obj){
					var trElem = $(obj.elem).closest('tr');
					var index = trElem.data('index');			//当前行的索引
					table.cache['recruitTable'][index]['platformId'] = obj.value;
					if(index<0)
						return;
					var id = table.cache['recruitTable'][index]['id']
					var postData = { id : id, platformId : obj.value, };
					updateAjax(postData);
				})
			}
		})
		table.on('toolbar(recruitTable)',function(obj){
			var config = obj.config;
			var btnElem = $(this);
			var tableId = config.id;
			switch(obj.event){
			case 'addTempData': addTempData();
				break;
			case 'cleanTempData': table.cleanTemp('recruitTable');
				break;
			case 'saveTempData': saveTempData();
				break;
			case 'deleteSome': deleteSome();
			}
		})
		table.on('edit(recruitTable)',function(obj){	//修改价格
			if(!obj.data.id)
				return;
			var field = obj.field;
			if(isNaN(obj.value))
				layer.msg('投放费用只能为数字',{icon:2});		
			else{
				var postData = { id:obj.data.id,};
				postData[field] = obj.value;
				updateAjax(postData);
			}
			table.reload('recruitTable');
		})
		function addTempData(){			//添加临时数据
			allField = {platformId: '', price: '', startTime:'',endTime:'',type:'0'};
			table.addTemp('recruitTable',allField,function(trElem) {
 				var startTiemTd = trElem.find('td[data-field="startTime"]')[0];
				startTiemTd.onclick = function(event) { layui.stope(event) };
				laydate.render({
					elem: startTiemTd.children[0],
					format: 'yyyy-MM-dd HH:mm:ss',
					done: function(value, date) {
						var trElem = $(this.elem[0]).closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						table.cache['recruitTable'][trElem.data('index')]['startTime'] = value;
					}
				}) 
				var endTimeTd = trElem.find('td[data-field="endTime"]')[0];	
				endTimeTd.onclick = function(event) { layui.stope(event) };
				laydate.render({
					elem: endTimeTd.children[0],
					format: 'yyyy-MM-dd HH:mm:ss',
					done: function(value, date) {
						var trElem = $(this.elem[0]).closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						table.cache['recruitTable'][trElem.data('index')]['endTime'] = value;
					}
				})  
			});
	 	}
		function saveTempData(){			//保存临时数据
			var tempData = table.getTemp('recruitTable').data;
			for(var i=0;i<tempData.length;i++){
				var t = tempData[i];
				if(!t.endTime || !t.price || !t.startTime){
					layer.msg('新增数据字段不能为空！',{icon:2});
					return;
				}
				if(isNaN(t.price)){
					layer.msg('价格只能为数字！',{icon:2});
					return;
				}
			}
			var load = layer.load(1);
			var successAdd = 0;
			for(var i=0;i<tempData.length;i++){
				if(updateAjax(tempData[i]))
					successAdd++;
			}
			table.cleanTemp('recruitTable');
			table.reload('recruitTable',{ page:{ curr:1 } })
			if(tempData.length == successAdd)
				layer.msg('成功新增：'+successAdd+'条数据',{icon:1});
			else
				layer.msg('新增异常：'+(tempData.length-successAdd)+'条数据',{icon:2});
			layer.close(load);
		}
		function deleteSome(){				//删除数据
			var choosed=layui.table.checkStatus('recruitTable').data;
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
							table.reload('recruitTable',{ page: {curr: 1}});
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
			postData.endTime && (postData.endTime = postData.endTime.split(' ')[0]+' 23:59:59');
			var result = false;
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
						result = true;
						table.reload('recruitTable');
					}
					layer.msg(r.message,{icon:icon});
				}
			})
			layer.close(load);
			return result;
		}
		function getPlatformSelect() {
			return function(d) {
				var html = '<select lay-filter="platformSelect">'
				layui.each(allPlatform,function(j,item){
					var selected = d.platformId == item.id?'selected':'';
					html+='<option value="'+item.id+'" '+selected+'>'+item.name+'</option>'
				})
				html+='</select>';
				if(d.platformId == '')
					layui.table.cache['recruitTable'][d.LAY_INDEX]['platformId'] = allPlatform[0].id;
				return html;
			};
		};
		function getPlatform(){					//获取所有平台信息
			$.ajax({
				url: '${ctx}/basedata/list?type=platform&page=1&limit=99',
				success: function(r){
					if(r.code == 0){
						if(r.data.length==0)
							layer.msg('获取平台数据信息异常。可能造成无法新增！！');
						allPlatform = r.data;
						var html='<option value="">广告平台</option>';
						layui.each(allPlatform,function(j,item){
							html+='<option value="'+item.id+'">'+item.name+'</option>'
						})
						$('#searchPlatformSelect').html(html);
					}
				}
			})
		}
		
	}//end define function
)//endedefine
</script>
</html>