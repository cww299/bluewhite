<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>量化单</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>产品名:</td>
				<td><input type="text" name="productName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
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
		var allUoloadOrder = myutil.getDataSync({ url: '${ctx}/TemporaryPack/findPagesUnderGoods?size=99999', });
		mytable.render({
			elem:'#tableData',
			//url:'${ctx}/TemporaryPack/findPagesQuantitative',
			data:[],
			toolbar:[
				'<span class="layui-btn layui-btn-sm" lay-event="add">新增数据</span>',
				'<span class="layui-btn layui-btn-sm" lay-event="update">修改数据</span>',
			].join(' '),
			curd:{
				btn:[4],
				otherBtn:function(obj){
					if(obj.event=='add'){
						addEdit('add',{});
					}else if(obj.event=='update'){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据编辑');
						addEdit('update',check[0]);
					}
				},
			},
			autoUpdate:{
				deleUrl:'/TemporaryPack/deleteQuantitative',
			},
			parseData:function(r){
				if(ret.code==0){
					var data = [],d = ret.data.rows;
					for(var i=0,len=d.length;i<len;i++){
						var child = d[i].orderChilds;
						for(var j=0,l=child.length;j<l;j++){
							data.push({
								id: d[i].id,
								quantitativeNumber: d[i].quantitativeNumber,
								time: d[i].time,
								product: child[j].product,
								sumPackageNumber: child[j].sumPackageNumber,
								singleNumber: child[j].singleNumber,
							})
						}
					}
					return {  msg:ret.message,  code:ret.code , data:ret.data.rows, count:ret.data.total }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'量化编号',   field:'quantitativeNumber',	},
			       { title:'包装时间',   field:'time;',   },
			       { title:'产品名',   field:'product_name', 	},
			       { title:'总包数',   field:'sumPackageNumber;',	},
			       { title:'单包个数',   field:'singleNumber;',	},
			       ]],
	       done:function(){
				merge('product_name');
				merge('sumPackageNumber');
				merge('singleNumber');
				function merge(field){
					var rowspan = 1,mainCols=0;
					var cache = table.cache['tableAgreement'];
					var allCol = $('#tableAgreement').next().find('td[data-field="'+field+'"]');
					layui.each(allCol,function(index,item){
						if(index!=0){
							var thisData = cache[index],lastData = index!=0?cache[index-1]:{id:-1};
							if(!thisData)
								return;
							if(thisData.id!=lastData.id){
								$(allCol[mainCols]).attr('rowspan',rowspan)
								mainCols = index;
								rowspan = 1;
							}else{	//与上一列相同
								rowspan++;
								$(item).css('display','none')
							}
						}
					})
					$(allCol[mainCols]).attr('rowspan',rowspan)
				}
			}
		})
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
		
		function addEdit(type,data){
			var title = '新增量化单';
			
			if(data.id){
				title = '修改量化单';
			}
			layer.open({
				type:1,
				area:['80%','80%'],
				title: title,
				content: [
					'<div>',
						'<table id="addTable" lay-filter="addTable"></table>',
					'</div>',
				].join(' '),
				success: function(){
					mytable.render({
						elem: '#addTable',
						data: [],
						size:'lg',
						curd:{
							btn:[1,2,3],
							saveFun: function(data){
								console.log(data)
								layui.each(data,function(index,item){
									if(!item.singleNumber || !item.sumPackageNumber || !item.underGoods_id){
										return myutil.emsg('请正确填写数据！');
									}
								})
							}
						},
						autoUpdate:{
							field:[],
						},
						toolbar:'<span class="layui-btn layui-btn-primary layui-btn-sm" id="time">2019-12-06 17:55:50</span>',
						cols:[[
							{ type:'checkbox',},
							{ title:'下货单~批次号~批次数量', field:'underGoods_id', type:'select', 
								select:{data: allUoloadOrder, name:['product_name','bacthNumber','number'],} },
							{ title:'总包数',   field:'sumPackageNumber',	},
					        { title:'单包个数',   field:'singleNumber',	},
						]],
						done:function(){
							laydate.render({
								elem:'#time',value: new Date(),type:'datetime',
							})
						}
					})
				}
			})
		}
		
	}//end define function
)//endedefine
</script>

</html>