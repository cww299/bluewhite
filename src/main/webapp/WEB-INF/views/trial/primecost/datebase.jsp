<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>基础数据表</title>
	<style>
	.layui-form-item {
    	margin-bottom: 5px;
    }
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td></td>
				<td><input type="text" name="name" class="layui-input" placeholder="物料名"></td>
				<td></td>
				<td><input type="text" name="number" class="layui-input" placeholder="物料编号"></td>
				<td></td>
				<td><select name="materielTypeId" id="materielTypeIdSelect"><option value="">物料类型</option></select></td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<!-- 表格工具栏模板
	商品名： 类型-颜色-门幅-蓝白:色号-厂家-色号
 -->
<script type="text/html" id="addEditTpl">
<div style="padding:10px;" class="layui-form layui-form-pane">
	<div class="layui-form-item" pane>
	    <label class="layui-form-label"><b class="red">*</b>物料类型</label>
	    <div class="layui-input-block">
		    <select name="materielTypeId" lay-verify="required" id="materielTypeSelect" 
				lay-filter="materielTypeSelect"></select>
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label"><b class="red"></b>颜色</label>
	    <div class="layui-input-block">
		    <input value="{{ d.color || "" }}" lay-verify="" class="layui-input changeName">
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label"><b class="red"></b>门幅</label>
	    <div class="layui-input-block">
		    <input value="{{ d.door || "" }}" lay-verify="" class="layui-input changeName">
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label"><b class="red"></b>蓝白色号</label>
	    <div class="layui-input-block">
			<input value="{{ d.colorNumber || "" }}" lay-verify="" class="layui-input changeName color">
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label">厂家</label>
	    <div class="layui-input-block">
			<input value="{{ d.factory || "" }}" class="layui-input changeName">
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label">厂家色号</label>
	    <div class="layui-input-block">
			<input value="{{ d.factoryNumber || "" }}" class="layui-input changeName ">
		</div>
	</div>
    <div class="layui-form-item" pane>
	    <label class="layui-form-label"><b class="red">*</b>物料名</label>
	    <div class="layui-input-block">
		    <input name="name" lay-verify="required" value="{{ d.name || "" }}" class="layui-input" disabled id="metaName">
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label"><b class="red">*</b>物料编号</label> 
	    <div class="layui-input-block">
		    <input name="number" value="{{ d.number || "" }}" class="layui-input" lay-verify="required">
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label"><b class="red">*</b>单位</label>
	    <div class="layui-input-block">
		    <select name="unitId" lay-verify="required" id="unitSelect" lay-search></select>
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label">物料定性</label>
	    <div class="layui-input-block">
		    <select name="materialQualitativeId" id="materialQualitativeSelect">
				<option value="">请选择</option></select>
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label">转换单位</label>
	    <div class="layui-input-block">
		    <select name="convertUnitId" id="convertUnitSelect" lay-search>
				<option value="">请选择</option></select>
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label">转换比</label>
	    <div class="layui-input-block">
		    <input name="converts" value="{{ d.converts || "" }}" type="number" class="layui-input">
		</div>
	</div>
	<p style="display:none;">
		<input name="id" value="{{ d.id || "" }}" type="hidden">
		<span lay-submit lay-filter="sureAddEdit"></span>
	</p>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laydate', 'laytpl'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form
		, laytpl = layui.laytpl
		, table = layui.table 
		, myutil = layui.myutil
		, laydate = layui.laydate
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		
		$('#materielTypeIdSelect').append(myutil.getBaseDataSelect({ type: 'materielType'}));
		
		var unitHtml = ''
		myutil.getData({
			url: myutil.config.ctx+'/product/getBaseOne?type=unit',
			success: function(d){
				d.forEach(unit => {
					unitHtml += "<option value='" + unit.id + "'>" + unit.name + "</option>"
				})
			}
		});
		
		form.render();
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/product/getMaterielPage',
			ifNull: '---',
			autoUpdate:{
				deleUrl: '/product/deleteMateriel'
			},
			curd:{
				btn: [4],
				otherBtn: function(obj){
					if(obj.event == 'add') {
						addEdit();
					} else if(obj.event == 'edit') {
						var check = table.checkStatus('tableData').data
						if(check.length != 1)
							return myutil.emsg("请选择一条数据进行编辑")
						addEdit(check[0]);
					}
				}
			},
			toolbar: [
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="add">新增物料</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="edit">修改物料</span>',
			].join(' '),
			cols:[[
			   { type:'checkbox',},
		       { title:'物料编号',   field:'number',	},
		       { title:'物料名',   field:'name',   },
		       { title:'单位',   field:'unit_name', 	},
		       { title:'转换单位',   field:'convertUnit_name', 	templet: getConvert(), },
		       { title:'物料类型',   field:'materielType_name',	},
		       { title:'物料定性',   field:'materialQualitative_name',	},
            ]]
		})
		function getConvert() {
			return function(d) {
				var html = '---'
				if(d.convertUnit){
					html = ("1 " + d.unit.name + " = " + d.converts + " " + d.convertUnit.name)
				}
				return html
			}
		}
		function addEdit(data = {}) {
			if(data.name) {
				var text = data.name.split('-')
				if(text.length >= 4) {
					data.color = text[1]
					data.door = text[2]
					data.colorNumber = text[3]
					if(text[4]) {
						data.factory = text[4]
						data.factoryNumber = text[5]
					}
				}
			}
			layer.open({
				type: 1,
				title: '物料信息',
				content: laytpl($('#addEditTpl').html()).render(data),
				area: ['30%', '680px'],
				btn: ['保存', '返回'],
				success: function(layerElem, layIndex) {
					$('#convertUnitSelect').append(unitHtml);
					$('#unitSelect').html(unitHtml)
					$('#unitSelect').val(data.unit ? data.unit.id : "");
					$('#convertUnitSelect').val(data.convertUnit ? data.convertUnit.id : "");
					$('#materielTypeSelect').html(myutil.getBaseDataSelect({ 
						type: 'materielType', id: data.materielType ? data.materielType.id : ""}));
					$('#materialQualitativeSelect').append(myutil.getBaseDataSelect({ 
						type: 'materialQualitative', id: data.materialQualitative ? data.materialQualitative.id : ""}))
					form.render();
					form.on('submit(sureAddEdit)', function(obj) {
						myutil.saveAjax({
							url: '/product/addMateriel',
							data: obj.field,
							success: function() {
								table.reload('tableData');
								layer.close(layIndex)
							}
						})
					})
					$('.changeName').change(function(){
						changeName();
					})
					form.on('select(materielTypeSelect)',function(obj) {
						changeName();
					})
					function changeName() {
						var selectId = $('#materielTypeSelect').val();
						var selectName = $('#materielTypeSelect').find('option[value="'+ selectId +'"]').html()
						var name = [ selectName ];
						layui.each($('.changeName'), function(index, item) {
							var val = $(item).val();
							if(val) {
								if($(item).hasClass('color') && val.indexOf('蓝白') < 0)
									val = "蓝白:" + val;
								name.push(val)
							}
						})
						$('#metaName').val(name.join('-'));
					}
				},
				yes: function(){
					$('[lay-filter="sureAddEdit"]').click();
				}
			})
		}
		
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
				page: { curr: 1 },
			})
		})
	}
)
</script>
</html> 