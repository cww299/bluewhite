<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>产品汇总</title>
<style>
.barDiv{	/*  右侧导航按钮样式   */
    z-index: 999999;
    position: fixed;
    top: 100px;
    left: 99.5%;
    padding: 25px;
    background-color: grey;
}
.barDiv:hover{
	left: 94%;
	background-color: rgba(0,0,0,0)
}
.btnDiv button{
	margin:2px 0px;
}
.choose{
	padding-right: 28px;
	cursor: pointer;
}
.choose:hover + i{
    display: block;
}
.choose + i:hover {
	display: block;
}
.choose + i{
    position: absolute;
    right: 23px;
    top: -3%;
    cursor: pointer;
    transform: rotate(45deg);
    color: #808080bf;
    display: none;
}
</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<div id="tabDiv">
			<div id="productTab">
				<table class="layui-form">
					<tr>
						<td>产品名：</td>
						<td><input type="text" class="layui-input" name="name"></td>
						<td>&nbsp;&nbsp;</td>
						<td><button type="button" class="layui-btn" lay-submit lay-filter="searchBtn">搜索</button></td>
					</tr>
				</table>
				<table id="productTable" lay-filter="productTable"></table>
			</div>
			<div id="cutPartTab"   style="display:none;"></div>
			<div id="materialsTab" style="display:none;"></div>
			<div id="tailorTab"	   style="display:none;"></div>
			<div id="machinistTab" style="display:none;"></div>
			<div id="embroideryTab"style="display:none;"></div>
			<div id="needleworkTab"style="display:none;"></div>
			<div id="packTab"      style="display:none;"></div>
		</div>
		<div class="barDiv">
			<div class="btnDiv">
				<button type="button" class="layui-btn layui-btn-sm" id="productBtn" style="color:red;">首页</button><br>	
				<button type="button" class="layui-btn layui-btn-sm" id="cutPartBtn">裁片填写</button><br>
	            <button type="button" class="layui-btn layui-btn-sm" id="materialsBtn">生产辅料</button><br>
	            <button type="button" class="layui-btn layui-btn-sm" id="tailorBtn">裁剪填写</button><br>
	             <!-- <button type="button" class="layui-btn layui-btn-sm" id="machinistBtn">机工填写</button><br>
	            <button type="button" class="layui-btn layui-btn-sm" id="embroideryBtn">绣花填写</button><br>
	            <button type="button" class="layui-btn layui-btn-sm" id="needleworkBtn">针工填写</button><br>
	            <button type="button" class="layui-btn layui-btn-sm" id="packBtn">内外包装</button><br> -->
			</div>
		</div>
	</div>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
	chooseMate: 'layui/myModules/trial/chooseMate',
}).use(['mytable','form', 'chooseMate'],function(){
	var $ = layui.jquery,
		form = layui.form
		myutil = layui.myutil,
		mytable = layui.mytable;
	
	myutil.config.ctx = '${ctx}';
	myutil.config.msgOffset = '250px';
	myutil.clickTr();
	mytable.render({		//产品表格
		elem:'#productTable',
		url:'${ctx}/productPages',
		limit: 15,
		limits: [10,15,20,30,50,100,200],
		cols:[[
		       { type:'checkbox',},
		       { title:'产品编号',   	field:'number',	},
		       { title:'产品名称',   	field:'name',	},
		      // { title:'成本',   	field:'primeCost',	},
		       ]],
	})
	form.on('submit(searchBtn)',function(obj){
		table.reload('productTable',{
			where: obj.field
		})
	})
	$('#productBtn').on('click',function(){
		$('#productBtn').css('color','red');
		$('#productBtn').siblings().css('color','white');
		$('#productTab').siblings().hide();	//默认展示产品表格、隐藏其他
		$('#productTab').show();
	})
	
	$(document).click(function(event){
	    var div = $('#searchTipDiv');
	    if(!div.is(event.target) && div.has(event.target).length === 0 && $(event.target)[0].nodeName!='I'){
	    	$('#searchTipDiv').hide();
		}
	})
				
	layui.extend({	//异步加载不同的模块。减少页面加载元素时间。使页面刷新更流畅
		cutParts : 'layui/myModules/trial/cutParts',			//裁片
		materials: 'layui/myModules/trial/materials',			//dd除裁片
		tailor: 'layui/myModules/trial/tailor', 			 	//裁剪
		
		machinist: 'layui/myModules/trial/machinist',			//机工
		embroidery : 'layui/myModules/trial/embroidery',		//绣花
		needlework: 'layui/myModules/trial/needlework',			//针工
		pack: 'layui/myModules/trial/pack',						//包装
	}).define(
		['cutParts','materials','tailor',],  // 'machinist','embroidery','needlework','pack'
		function(){
			var cutParts = layui.cutParts
			, materials = layui.materials
			, tailor = layui.tailor
			, machinist = layui.machinist
			, embroidery = layui.embroidery
			, needlework = layui.needlework
			, pack = layui.pack;
		
			cutParts.render({		//裁片模块渲染
				elem: 'cutPartTab',
				btn: 'cutPartBtn',
			})
			materials.render({		//生产用料模块渲染
				elem: 'materialsTab',
				btn: 'materialsBtn',
			})
			tailor.render({
				elem: 'tailorTab',
				btn: 'tailorBtn'
			})
			/* machinist.render({
				elem: 'machinistTab',
				btn: 'machinistBtn',
			})
			embroidery.render({
				elem: 'embroideryTab',
				btn: 'embroideryBtn'
			})
			needlework.render({
				elem: 'needleworkTab',
				btn: 'needleworkBtn'
			})
			pack.render({
				elem: 'packTab',
				btn: 'packBtn'
			}) */
		}
	)
});

</script>
</html> 