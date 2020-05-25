<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>发货单</title>
	<style>
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body layui-form">
		<span style="float:right;">
			<input type="radio" name="sex" value="gs" lay-filter="tableType" title="公司">
			<input type="radio" name="sex" value="kh" lay-filter="tableType" title="客户" checked></span>
		<table class=" layui-form searchTable">
			<tr>
				<td>发货时间:</td>
				<td><input type="text" name="sendTime_be_date" class="layui-input" id="searchTime"></td>
				<td>产品名:</td>
				<td><input type="text" name="sendOrderChild.productName_like" class="layui-input"></td>
				<td>客户名:</td>
				<td><input type="text" name="customer.name_like" class="layui-input"></td>
				<td>物流点:</td>
				<td style="width:150px;"><select name="logisticsId" id="logisticsIdSelect">
						<option value="">请选择</option>
					</select></td>
				<td>包装方式:</td>
				<td style="width:120px;"><select name="outerPackagingId" id="outerPackagingIdSelect">
						<option value="">请选择</option>
					</select></td>
				<td>是否生成:</td>
				<td style="width:100px;"><select name="audit" id="">
						<option value="">请选择</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select></td>
				<td><button type="button" class="layui-btn layui-btn-" lay-submit lay-filter="search">搜索</button></td>
				<td></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
<div id="toolbarTpl" style="display:none;">
	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="audit">生成物流费用</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cancelAudit">取消生成</span>
	<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="info">贴包明细</span>
	<span class="layui-btn layui-btn-sm layui-btn-primary" lay-event="moreEdit">批量修改</span>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laydate'],
	function(){
	var $ = layui.jquery
	, layer = layui.layer 				
	, form = layui.form			 		
	, table = layui.table 
	, laydate = layui.laydate
	, myutil = layui.myutil
	, laytpl = layui.laytpl
	, mytable = layui.mytable; 
	myutil.config.ctx = '${ctx}';
	myutil.clickTr();
	var allLogistics = myutil.getBaseData({ type: 'logistics', });
	var allPackagMethod = myutil.getBaseData({ type: 'outerPackaging', });
	var isTax = [{ id:"",name:'请选择',},{id:0,name:"不含税"},{name:"含税",id:1,}];
	$('#outerPackagingIdSelect').html((function(){
		var html = "";
		for(var i in allPackagMethod){
			html += "<option value='"+allPackagMethod[i].id+"'>"+allPackagMethod[i].name+"</option>";
		}
		return html;
	})());
	$('#logisticsIdSelect').html((function(){
		var html = "";
		for(var i in allLogistics){
			html += "<option value='"+allLogistics[i].id+"'>"+allLogistics[i].name+"</option>";
		}
		return html;
	})());
	form.render();
	laydate.render({ elem:'#searchTime', range:'~' });
	layui.tablePlug.smartReload.enable(true);
	mytable.render({
		elem:'#tableData',
		colFilterRecord:'local',
		size:'lg',
		url: myutil.config.ctx+'/ledger/sendOrderPage',
		where:{ 
			interior:0,
		},
		smartReloadModel:true,
		toolbar: $('#toolbarTpl').html(),
		limit:15,
		limits:[15,50,200,500,1000],
		curd:{
			btn: [],
			otherBtn:function(obj){
				var check = table.checkStatus('tableData').data;
				if(obj.event=='audit'){
					var ids = [],msg = "";
					if(check.length<1)
						return myutil.emsg('请选择需要审核得数据');
					if(!isCompany){
						for(var i in check){
							if(check[i].sumPackageNumber != check[i].sendPackageNumber){
								return myutil.emsg("当前选择的第"+(i-(-1))+"条数据有未发货，无法生成物流费用");
							}
						}
					}
					myutil.deleTableIds({
						 table:'tableData', 
						 text:'请选择信息|是否确认生成物流费用？',
						 url:'/temporaryPack/auditSendOrder?audit=1',
					})
				}else if(obj.event=='cancelAudit'){
					myutil.deleTableIds({
						 table:'tableData', 
						 text:'请选择信息|取消生成物流费用？',
						 url:'/temporaryPack/auditSendOrder?audit=0',
					})
				}else if(obj.event=="info"){
					if(check.length!=1)
						return myutil.emsg("无法同时查看多条数据的贴包明细");
					openInfoWin(check[0]);
				}else if(obj.event=="moreEdit"){
					if(check.length==0)
						return myutil.emsg("请选择需要修改的数据");
					moreEdit(check);
				}
			},
		},
		autoUpdate:{
			updateUrl: '/temporaryPack/updateSendOrder',
			field:{ logistics_id:'logisticsId', outerPackaging_id:'outerPackagingId'},
			closeMsg:true,
			isReload: true,
			closeLoad: true,
		},
		ifNull:'',
		cols:[[
		       { type:'checkbox',},
		       { title:'客户名称',   field:'customer_name', event:'lookoverInfo', },
		       { title:'发货时间',   field:'sendTime', width:110, type:'date', },
		       { title:'总数',   field:'sumPackageNumber', width:80,	},
		       { title:'总个数',   field:'number',  width:80,  },
		       { title:'已发货包数',    field:'sendPackageNumber', width:100,	},
		       { title:'物流编号',   field:'logisticsNumber',width:130, edit:true,	},
		       { title:'物流点',   field:'logistics_id', type:'select',select:{ data: allLogistics, layFilter:'changePriceSelect', },width:150, },
		       { title:'外包装',   field:'outerPackaging_id',type:'select', select:{ data: allPackagMethod, layFilter:'changePriceSelect', },width:100, 	 },
		       { title:'是否含税',    field:'tax',	 width:100, type:'select',select:{  data: isTax, layFilter:'changePriceSelect', },  },
		       { title:'单价',    field:'singerPrice',width:120,templet: getSingerPriceSelect(), },
		       { title:'已发货费用',   field:'sendPrice',	width:100,  },
		       { title:'额外费用',   field:'extraPrice',	width:100, edit:'number', },
		       { title:'物流总费用',   field:'logisticsPrice',	width:120, },  
		       { title:'发货地点',   field:'warehouseType_name',	},  
		       { title:'生成',   field:'audit',	width:60,transData:true, },  
		       ]],
		totalRow:["sendPackageNumber","sendPrice","extraPrice","logisticsPrice"],
       	done:function(ret,curr, count){
       		var totalDiv = $('div[lay-id="tableData"] .layui-table-total');
       		var data = ret.statData;
       		$(totalDiv).find('td[data-field="sendPackageNumber"] div').html(data.sendPackageNumber);
       		$(totalDiv).find('td[data-field="sendPrice"] div').html(data.sendPrice);
       		$(totalDiv).find('td[data-field="extraPrice"] div').html(data.extraPrice);
       		$(totalDiv).find('td[data-field="logisticsPrice"] div').html(data.logisticsPrice);
       		//覆盖mytable 下拉框修改函数
       		form.on('select(changePriceSelect)',function(obj){
       			var index = $(obj.elem).closest('tr').data('index');
       			var trData = table.cache['tableData'][index];
       			var saveData = {
       				id: trData.id,
       				logisticsId: trData.logistics ? trData.logistics.id : null,
     				outerPackagingId: trData.outerPackaging ? trData.outerPackaging.id : null,
       				tax: trData.tax,
       				singerPrice: trData.singerPrice || 0,
       			}
       			var val = obj.value;
       			var field = $(obj.elem).closest('td').data('field');
       			switch(field){
       			case 'logistics_id': saveData.logisticsId = val; break;
       			case 'outerPackaging_id': saveData.outerPackagingId = val; break;
       			case 'tax': saveData.tax = val; break;
       			case 'singerPrice': saveData.singerPrice = (val || 0); break;
       			}
       			if(field != 'singerPrice')
       				saveData.singerPrice = 0;
       			myutil.saveAjax({
       				url:'/temporaryPack/updateSendOrder',
       				closeLoad:true,
       				data: saveData,
       				success:function(){
       					table.reload('tableData');
       				}
       			})
       		})
		}
	})
	function getSingerPriceSelect(){
		return function(data){
			var disabled = "",search = "",option = '<option value="">请选择</option>';
			if(data.customer && data.logistics && data.outerPackaging){
				myutil.getDataSync({
					url: myutil.config.ctx+'/ledger/findLogisticsCostsPrice',
					data:{
						customerId: data.customer.id,
						logisticsId: data.logistics.id,
						outerPackagingId: data.outerPackaging.id,
						tax: data.tax,
					},
					success:function(d){
						layui.each(d,function(index,item){
							var selected = item==data.singerPrice?'selected':"";
							option += "<option value='"+item+"' "+selected+">￥"+item+"</option>";
						})
					}
				})
				search = "lay-search";
			}else{
				disabled = "disabled"; 
			}
			return '<select lay-filter="changePriceSelect" '+search+' '+disabled+'>'+option+'</select>';
		}
	}
	table.on('tool(tableData)',function(obj){
		if(obj.event=="lookoverInfo"){
			lookoverInfo(obj);
		}
	})
	function moreEdit(datas){
		var ids = [],customerId = datas[0].customer.id;
		for(var i in datas){
			if(datas[i].customer.id != customerId)
				return myutil.emsg("只能批量修改同一个客户数据！");
			ids.push(datas[i].id);
		}
		layer.open({
			type: 1,
			title: '批量修改',
			area:['500px','400px'],
			btn:['保存','取消'],
			btnAlign:'c',
			content:[
				'<div style="padding:10px;">',
					'<div class="layui-form layui-form-pane">',
					  '<div class="layui-form-item" pane>',
					    '<label class="layui-form-label">物流点</label>',
					    '<div class="layui-input-block">',
					      '<select lay-filter="signChangeSelect" lay-search  name="logisticsId"></select>',
					    '</div>',
					  '</div>',
					  '<div class="layui-form-item" pane>',
					    '<label class="layui-form-label">外包装</label>',
					    '<div class="layui-input-block">',
					      '<select lay-filter="signChangeSelect" lay-search  name="outerPackagingId"></select>',
					    '</div>',
					  '</div>',
					  '<div class="layui-form-item" pane>',
					    '<label class="layui-form-label">是否含税</label>',
					    '<div class="layui-input-block">',
					      '<select lay-filter="signChangeSelect" name="tax">',
					      		'<option value="1">含税</option>',
					      		'<option value="0">不含税</option></select>',
					    '</div>',
					  '</div>',
					  '<div class="layui-form-item" pane>',
					    '<label class="layui-form-label">单价</label>',
					    '<div class="layui-input-block">',
					      '<select lay-search  name="singerPrice"></select>',
					    '</div>',
					  '</div>',
					  '<div class="layui-form-item" pane>',
					    '<label class="layui-form-label">额外费用</label>',
					    '<div class="layui-input-block">',
					      '<input type="number" class="layui-input" name="extraPrice">',
					    '</div>',
					    '<span lay-submit lay-filter="moreEditBtn"></span>',
					  '</div>',
					'</div>',
				'</div>',
			].join(' '),
			success:function(layerElem,layerIndex){
				form.render();
				form.on('select(signChangeSelect)',function(){
					var logisticsId = $(layerElem).find('select[name="logisticsId"]').val();
					var outerPackagingId = $(layerElem).find('select[name="outerPackagingId"]').val();
					var tax = $(layerElem).find('select[name="tax"]').val();
					if(logisticsId && outerPackagingId){
						myutil.getData({
							url: myutil.config.ctx+'/ledger/findLogisticsCostsPrice',
							data:{
								customerId: customerId,
								logisticsId: logisticsId,
								outerPackagingId: outerPackagingId,
								tax: tax,
							},
							success:function(d){
								var option = '<option value="">请选择</option>';
								layui.each(d,function(index,item){
									option += "<option value='"+item+"'>￥"+item+"</option>";
								})
								$(layerElem).find('select[name="singerPrice"]').html(option);
								form.render();
							}
						})
					}
				})
				var logisticsHtml = "",packagMethodHtml = "";
				for(var i in allLogistics){
					logisticsHtml += '<option value="'+allLogistics[i].id+'">'+allLogistics[i].name+'</option>';
				}
				for(var i in allPackagMethod){
					packagMethodHtml += '<option value="'+allPackagMethod[i].id+'">'+allPackagMethod[i].name+'</option>';
				}
				$(layerElem).find('select[name="logisticsId"]').append(logisticsHtml);
				$(layerElem).find('select[name="outerPackagingId"]').append(packagMethodHtml);
				form.render();
				form.on('submit(moreEditBtn)',function(obj){
					obj.field.ids = ids.join(',')
					myutil.saveAjax({
						url:'/temporaryPack/bacthUpdate',
						data: obj.field,
						success:function(){
							layer.close(layerIndex)
							table.reload('tableData');
						}
					})
				})
			},
			yes:function(){
				$('span[lay-filter="moreEditBtn"]').click();
			}
		})
	}
	var openWinIndex =[];
	function lookoverInfo(obj){
		if(isCompany)
			return;
		var data = obj.data;
		var index = $(obj.tr).data('index');
		if(openWinIndex.indexOf(index)>-1)
			return;
		openWinIndex.push(index);
		layer.open({
			type:1,
			offset: ['100px', (index+1)*60+250+'px'],
			title: data.customer_name,
			shade:0,
			area:['500px'],
			resize:true,
			btn:['关闭全部','关闭'],
			yes:function(){
				layer.closeAll();
				openWinIndex = [];
			},
			end:function(){
				openWinIndex.splice(openWinIndex.indexOf(index),1);
			},
			content:[
				'<table class="layui-table">',
				'<colgroup><col width="120"><col><col  width="90"> </colgroup>',
					'<thead>',
					    '<tr>',
					      '<th>批次号</th><th>商品名</th><th>单包数量</th>',
					    '</tr> ',
					'</thead>',
					'<tbody>',
						(function(){
							var html ="";
							layui.each(data.sendOrderChild,function(index,d){
								html += "<tr>"+
										"<td>"+d.bacthNumber+"</td>"+
										"<td>"+d.productName+"</td>"+
										"<td>"+d.singleNumber+"</td>"+
									"</tr>";
							})
							return html;
						})(),
					'</tbody>',
				'</table>',
			].join(' '),
			
		})
	}
	function openInfoWin(data){
		myutil.getDataSync({
			url: myutil.config.ctx+'/temporaryPack/getQuantitativeList?id='+data.id,
			success:function(d){
				var data = [];
				for(var i=0,len=d.length;i<len;i++){
					var child = d[i].quantitativeChilds;
					if(!child)
						continue;
					for(var j=0,l=child.length;j<l;j++){
						data.push($.extend({},child[j],{childId: child[j].id,},d[i])); 
					}
				}
				layer.open({
					type:1,
					title:'贴包明细',
					area:['80%','80%'],
					content:[
						'<div style="padding:10px">',
							'<table id="infoTable" lay-filter="infoTable"></table>',
						'</div>,'
					].join(' '),
					success:function(layerElem,layerIndex){
						mytable.renderNoPage({
							elem:'#infoTable',
							limit:999,
							data: data,
							ifNull:'---',
							cols:[[
								{ title:'量化编号',field:'quantitativeNumber',width:175, },
								{ title:'发货时间',field:'sendTime', type:'date',width:120,},
								{ title:'客户',field:'customer_name', width:120,},
								{ title:'是否发货',field:'flag', transData:true, width:100,},
								{ title:'批次号',    field:'underGoods_bacthNumber',	minWidth:200, },
						        { title:'产品名',    field:'underGoods_product_name', },
						        { title:'单包个数',   field:'singleNumber',	width:100, },
							]]
						})
					}
				})
			}
		})
	}
	var isCompany = false;
	form.on('radio(tableType)', function(data){
		if(data.value=="gs"){
			isCompany = true;
			table.reload('tableData',{
				where: { interior:1, },
				page: { curr: 1},
				done:function(){
					//table.thisTable.that
					var cols = table.thisTable.that.tableData.config.cols[0];
					cols[4].hide = true;
					cols[5].hide = true;
					cols[6].hide = true;
					table.thisTable.that.tableData.elem.find('*[data-field="number"]').addClass('layui-hide');
					table.thisTable.that.tableData.elem.find('*[data-field="sendPackageNumber"]').addClass('layui-hide');
					table.thisTable.that.tableData.elem.find('*[data-field="logisticsNumber"]').addClass('layui-hide');
					table.resize();
				}
			})
		}else{
			isCompany = false;
			table.reload('tableData',{
				where: { interior:0, },
				page: { curr: 1},
				done:function(){
					var cols = table.thisTable.that.tableData.config.cols[0];
					cols[4].hide = false;
					cols[5].hide = false;
					cols[6].hide = false;
					table.thisTable.that.tableData.elem.find('*[data-field="number"]').removeClass('layui-hide');
					table.thisTable.that.tableData.elem.find('*[data-field="sendPackageNumber"]').removeClass('layui-hide');
					table.thisTable.that.tableData.elem.find('*[data-field="logisticsNumber"]').removeClass('layui-hide');
					table.resize();
				}
			})
		}
	});
	form.on('submit(search)',function(obj){
		delete obj.field.sex;
		delete obj.field.layTableCheckbox;
		table.reload('tableData',{
			where: obj.field,
			page:{ curr:1 },
		})
	}) 
})//endedefine
</script>
</html>