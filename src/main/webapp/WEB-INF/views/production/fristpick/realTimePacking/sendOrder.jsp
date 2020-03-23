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
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td>产品名:</td>
				<td><input type="text" name="sendOrderChild.productName_like" class="layui-input"></td>
				<td>客户名:</td>
				<td><input type="text" name="customer_name_like" class="layui-input"></td>
				<!-- 
				<td>是否发货:</td>
				<td style="width:100px;"><select name="flag"><option value="">请选择</option>
										<option value="0">否</option>
										<option value="1">是</option></select></td> -->
				<td><button type="button" class="layui-btn layui-btn-" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
<div id="toolbarTpl" style="display:none;">
	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="audit">生成物流费用</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cancelAudit">取消生成</span>
	<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="info">贴包明细</span>
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
		layui.tablePlug.smartReload.enable(true);
		mytable.render({
			elem:'#tableData',
			size:'lg',
			url: myutil.config.ctx+'/ledger/sendOrderPage',
			where:{ },
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
						for(var i in check){
							if(check[i].sumPackageNumber != check[i].sendPackageNumber){
								return myutil.emsg("当前选择的第"+(i-(-1))+"条数据有未发货，无法生成物流费用");
							}
						}
						myutil.deleTableIds({
							 table:'tableData', 
							 text:'请选择信息|是否确认取消审核？',
							 url:'/temporaryPack/auditSendOrder?audit=1',
						})
					}else if(obj.event=='cancelAudit'){
						myutil.deleTableIds({
							 table:'tableData', 
							 text:'请选择信息|是否确认取消审核？',
							 url:'/temporaryPack/auditSendOrder?audit=0',
						})
					}else if(obj.event=="info"){
						if(check.length!=1)
							return myutil.emsg("无法同时查看多条数据的贴包明细");
						openInfoWin(check[0]);
					}
				},
			},
			autoUpdate:{
				updateUrl: '/temporaryPack/updateSendOrder',
				field:{ logistics_id:'logisticsId', outerPackaging_id:'outerPackagingId'},
				isReload: true,
			},
			parseData:function(ret){
				if(ret.code==0){
					var data = [],d = ret.data.rows;
					for(var i=0,len=d.length;i<len;i++){
						var child = d[i].sendOrderChild;
						if(!child)
							continue;
						for(var j=0,l=child.length;j<l;j++){
							data.push($.extend({},child[j],{childId: child[j].id,},d[i])); 
						}
					}
					return {  msg:ret.message,  code:ret.code , data: data, count:ret.data.total }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			},
			ifNull:'',
			//scrollX:true,
			cols:[[
			       { type:'checkbox',},
			       { title:'客户名称',   field:'customer_name', width:145,	},
			       { title:'发货时间',   field:'sendTime', width:110, type:'date', },
			       { title:'总包数',   field:'sumPackageNumber', width:80,	},
			       { title:'总数量',   field:'number',  width:80,  },
			       { title:'已发货包数',    field:'sendPackageNumber', width:100,	},
			       { title:'已发货费用',   field:'sendPrice',	width:100,  },
			       { title:'物流编号',   field:'logisticsNumber',width:100, edit:true,	},
			       { title:'物流点',   field:'logistics_id', type:'select',select:{ data:allLogistics, },width:150, },
			       { title:'外包装方式',   field:'outerPackaging_id',type:'select', select:{ data:allPackagMethod, },width:150, 	 },
			       { title:'是否含税',    field:'tax',	 width:120, type:'select',select:{ 
			    	   data: isTax, }, 
			    	  },
			       { title:'包装实际费用',    field:'singerPrice',width:120,templet: getSingerPriceSelect(), },
			       { title:'额外费用',   field:'extraPrice',	width:90, edit:'number', },
			       { title:'物流总费用',   field:'logisticsPrice',	width:120, },  
			       { title:'批次号',    field:'bacthNumber',	width:120,},
			       { title:'商品名',    field:'product_name',	width:180,},
			       { title:'单包数量',    field:'singleNumber',width:90,	},
			       ]],
	       autoMerge:{
	    	 field:['customer_name','sendTime','sumPackageNumber','number','sendPackageNumber','sendNumber','logistics_id',
	    		 'outerPackaging_id','logisticsNumber','tax','singerPrice','0','sendPrice','extraPrice','logisticsPrice',
	    		 'audit',], 
	       }, 
	       done:function(ret,curr, count){
	    	    allDataId = [];
	    	    form.render();
			}
		})
		var allDataId = [];
		function getSingerPriceSelect(){
			return function(data){
				var disabled = "",search = "",option = '<option value="">请选择</option>';
				if(data.customer && data.logistics && data.outerPackaging){
					for(var i in allDataId){
						if(allDataId[i]==data.id)
							return "";
					}
					allDataId.push(data.id);
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
		form.on('select(changePriceSelect)',function(obj){
			var index = $(obj.elem).closest('tr').data('index');
			var trData = table.cache['tableData'][index];
			
			myutil.saveAjax({
				url:'/temporaryPack/updateSendOrder',
				data:{
					id: trData.id,
					singerPrice: obj.value,
					sendPrice: obj.value*(trData.sendPackageNumber || 0),
					logisticsPrice: obj.value*(trData.sendPackageNumber || 0)+(trData.extraPrice || 0),
				}
			})
		})
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
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
				page:{ curr:1 },
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>