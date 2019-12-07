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
	<style>
	div[lay-id="tableData"] .layui-table tbody tr:hover, .layui-table-hover {
		background-color: transparent; 
	}
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>产品名:</td>
				<td><input type="text" name="productName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>量化编号:</td>
				<td><input type="text" name="quantitativeNumber" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>包装时间:</td>
				<td><input type="text" name="orderTimeBegin" id="orderTimeBegin" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script type="text/html" id="printPackTpl">
<div style="padding:20px;">
	<table border="1" style="margin: auto;width: 80%;text-align:center;">
		<tr>
       	 	<td>包装组贴包人</td>
        	<td>编号</td>
	    </tr>
		<tr>
	        <td>{{ d.user?d.user.userName:'---' }}</td>
	        <td>{{ d.number}}</td>
	    </tr>
		<tr>
	        <td>产品名</td>
	        <td>当件内装数量</td>
	    </tr>
		{{# layui.each(d.packingChilds,function(index,item){  }}
		<tr>
	        <td>{{ item.product.name}}</td>
	        <td>{{ item.count}}</td>
	    </tr>
	    {{# }) }}
		<tr>
	        <td></td>
	        <td>已出货</td>
	    </tr>
	</table>
</div>
<hr>
</script>
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
		myutil.clickTr({
			noClick:'tableData',
		});
		laydate.render({
			elem: '#orderTimeBegin', range: '~',
		})
		var allUoloadOrder = myutil.getDataSync({ url: '${ctx}/temporaryPack/findPagesUnderGoods?size=99999', });
		var allMaterials = myutil.getDataSync({ url:'${ctx}/basedata/list?type=packagingMaterials', });
		var allUser ='';
		var tableDataNoTrans = [];
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/temporaryPack/findPagesQuantitative',
			toolbar:[
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="add">新增数据</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="update">修改数据</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="audit">审核</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="print">打印</span>',
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
						var i = 0;
						for(;i<tableDataNoTrans.length;i++){
							if(tableDataNoTrans[i].id==check[0].id)
								break;
						}
						addEdit('update',tableDataNoTrans[i]);
					}else if(obj.event=='audit'){
						myutil.deleTableIds({
							 table:'tableData', 
							 text:'请选择信息|是否确认发货？',
							 url:'/temporaryPack/auditQuantitative',
						})
					}else if(obj.event=='print'){
						printOrder();
					}
				},
			},
			autoUpdate:{
				deleUrl:'/temporaryPack/deleteQuantitative',
			},
			parseData:function(ret){
				if(ret.code==0){
					var data = [],d = ret.data.rows;
					tableDataNoTrans = d;
					for(var i=0,len=d.length;i<len;i++){
						var child = d[i].quantitativeChilds;
						if(!child)
							continue;
						for(var j=0,l=child.length;j<l;j++){
							data.push({
								id: d[i].id,
								quantitativeNumber: d[i].quantitativeNumber,
								time: d[i].time,
								user: d[i].user,
								product: child[j].product,
								sumPackageNumber: child[j].sumPackageNumber,
								singleNumber: child[j].singleNumber,
								underGoods: child[j].underGoods,
								number: child[j].number,
							})
						}
					}
					return {  msg:ret.message,  code:ret.code , data: data, count:ret.data.total }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'量化编号',   field:'quantitativeNumber',	},
			       { title:'包装时间',   field:'time',   },
			       { title:'产品名',   field:'underGoods_product_name', 	},
			       { title:'剩余发货数量',   field:'surplusSendNumber', 	},
			       { title:'剩余量化数量',   field:'surplusNumber', 	},
			       { title:'贴包人',   field:'user_userName', 	},
			       { title:'总包数',   field:'sumPackageNumber',	},
			       { title:'单包个数',   field:'singleNumber',	},
			       { title:'数量',   field:'number',	},
			       ]],
	       done:function(){
				merge('underGoods_product_name');
				merge('quantitativeNumber');
				merge('time');
				merge('user_userName');
				merge('surplusSendNumber');
				merge('surplusNumber');
				merge('0');
				function merge(field){
					var rowspan = 1,mainCols=0;
					var cache = table.cache['tableData'];
					var allCol = $('#tableData').next().find('td[data-field="'+field+'"]');
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
					});
					$(allCol[mainCols]).attr('rowspan',rowspan);
				}
			}
		})
		function printOrder(){	
			var choosed=layui.table.checkStatus('tableData').data;
			if(choosed.length<1)
				return myutil.emsg('请选择打印信息');
			var tpl = $('#printPackTpl').html(), html='<div id="printDiv">';
			layui.each(choosed,function(index,item){
				laytpl(tpl).render(item,function(h){ html += h; })
			})
			layer.open({
				title: '打印',
				area: ['80%','80%'],
				offset: '100px', 
				content: html+'</div>',
				btnAlign: 'c',
				btn: ['打印','取消'],
				shadeClose: true,
				yes: function(){
					printpage('printDiv');
					var ids = [];
					myutil.deleteAjax({
						ids: ids.join(),
						url:'/temporaryPack/printQuantitative',
						success:function(){
							table.reload('tableData');
						}
					})
				}
			})
		}
		function printpage(myDiv){    
			var printHtml = document.getElementById(myDiv).innerHTML;
			var wind = window.open("",'newwindow', 'height=800, width=1500, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
			wind.document.body.innerHTML = printHtml;
			wind.print();
			return false; 
		}  
		
		function addEdit(type,data){
			var title = '新增量化单';
			
			if(data.id){
				title = '修改量化单';
			}
			var addEditWin = layer.open({
				type:1,
				area:['90%','80%'],
				title: title,
				content: [
					'<div style="padding:10px;">',
						'<div>',
							'<table class="layui-form">',
								'<tr>',
									'<td>量化时间：</td>',
									'<td>',
										'<input class="layui-input" id="addEditTime" value="2019-12-06 17:55:50">',
									'</td>',
									'<td>&nbsp;&nbsp;贴包人：</td>',
									'<td>',
										'<select id="packPeopleSelect" lay-search><option value="">请选择</option></select>',
									'</td>',
									'<td>&nbsp;&nbsp;<span class="layui-btn" id="saveBtn">保存</span></td>',
								'</tr>',
							'</table>',
						'</div>',
						'<div style="float:left;width:68%;">',
							'<table id="addTable" lay-filter="addTable"></table>',
						'</div>',
						'<div style="float:right;width:30%;">',
							'<table id="addMaterTable" lay-filter="addMaterTable"></table>',
						'</div>',
					'</div>',
				].join(' '),
				success: function(){
					$('#saveBtn').click(function(){
						$('span[lay-event="saveTempData"]').click();
					})
					var addTable = [],addMate = [];
					laydate.render({
						elem:'#addEditTime',value: new Date(),type:'datetime',
					})
					$('#packPeopleSelect').append(allUser);
					if(data.id){
						addTable = data.quantitativeChilds;
						addMate = data.packingMaterials
						$('#packPeopleSelect').val(data.user?data.user.id:'');
						$('#addEditTime').val(data.time);
					}
					mytable.renderNoPage({
						elem: '#addTable',
						data: addTable,
						size:'lg',
						curd:{
							btn:[1,2,3],
							saveFun: function(d){
								var url = '/temporaryPack/saveQuantitative';
								var time = $('#addEditTime').val();
								var userId = $('#packPeopleSelect').val();
								if(!time)
									return myutil.emsg('量化单时间不能为空！');
								if(!userId)
									return myutil.emsg('请选择贴包人！');
								layui.each(table.cache['addTable'],function(index,item){
									if(typeof(item)==='object' && item.length==0)
										return;
									d.push({
										id: item.id,
										number: item.number,
										singleNumber: item.singleNumber,
										sumPackageNumber: item.sumPackageNumber,
										underGoodsId: item.underGoods.id,
									})
								})
								var mateData = table.getTemp('addMaterTable').data;	//
								layui.each(table.cache['addMaterTable'],function(index,item){
									if(typeof(item)==='object' && item.length==0)
										return;
									console.log(item)
									mateData.push({
										id: item.id,
										packagingId: item.packagingId,
										packagingCount: item.packagingCount,
									})
								})
								myutil.saveAjax({
									url: url,
									data:{
										id: data.id || '',
										time: time,
										userId: userId,
										child: JSON.stringify(d),
										packingMaterialsJson: JSON.stringify(mateData),
									},
									success:function(){
										layer.close(addEditWin);
										table.reload('tableData');
									}
								})
							},
							deleFun:function(ids,check){
								
							},
							addTemp:{
								underGoodsId: allUoloadOrder[0]?allUoloadOrder[0].id:"",
								sumPackageNumber: 0,
								singleNumber: 0,
								number: 0,
							},
						},
						autoUpdate:{
							field: { underGoods_id:'underGoodsId', },
						},
						verify:{
							count:['sumPackageNumber','singleNumber','number'],
						},
						cols:[[
							{ type:'checkbox',},
							{ title:'下货单~批次号~剩余数量', field:'underGoods_id', type:'select',
								select:{data: allUoloadOrder, name:['product_name','bacthNumber','number'],} },
							{ title:'总包数',   field:'sumPackageNumber', edit:true,	},
					        { title:'单包个数',   field:'singleNumber',	 edit:true,	},
					        { title:'总数量',   field:'number',	 edit:true, },
					        { title:'操作',   field:'de',	 event:'deleteTr', edit:false,
					        		templet:'<div><span class="layui-btn layui-btn-xs layui-btn-danger">删除</span></div>' },
						]],
						done:function(){
							$('span[lay-event="saveTempData"]').hide();
							table.on('tool(addTable)', function(obj){
								if(obj.event === 'deleteTr'){ //删除
								    layer.confirm('是否确认删除？', function(index){
								        obj.del();
								        layer.close(index);
								 	});
								}
							})
						}
					})
					mytable.renderNoPage({
						elem: '#addMaterTable',
						data: addMate,
						size:'lg',
						curd:{
							btn:[1,2,],
							addTemp:{
								packagingId: allMaterials[0]?allMaterials[0].id:"",
								packagingCount: 0,
							},
							deleFun:function(ids,check){
								
							},
						},
						autoUpdate:{
							field: { packagingMaterials_id:'packagingId', },
						},
						verify:{
							count:['packagingCount'],
						},
						cols:[[
							{ type:'checkbox',},
							{ title:'材料', field:'packagingMaterials_id', type:'select',select:{data: allMaterials, } },
							{ title:'数量',   field:'packagingCount',	edit:true, },
							{ title:'操作',   field:'de',	 event:'deleteTr', edit:false,
				        		templet:'<div><span class="layui-btn layui-btn-xs layui-btn-danger">删除</span></div>' },
						]],
						done:function(){
							table.on('tool(addMaterTable)', function(obj){
								if(obj.event === 'deleteTr'){
								    layer.confirm('是否确认删除？', function(index){
								        obj.del();
								        layer.close(index);
								 	});
								}
							})
						},
					})
					form.render();
				}
			})
		}
		myutil.getData({
			url: myutil.config.ctx+'/system/user/findUserList?foreigns=0&isAdmin=false&orgNameIds=55&quit=0',
			done: function(data){
				layui.each(data,function(index,item){
					allUser += '<option value="'+item.id+'">'+item.userName+'</option>';
				})
			}
		})
		form.on('submit(search)',function(obj){
			var field = obj.field;
			if(field.orderTimeBegin){
				var t = field.orderTimeBegin.split(' ~ ');
				field.orderTimeBegin = t[0]+' 00:00:00';
				field.orderTiemEnd = t[1]+' 23:59:59';
			}else
				field.orderTiemEnd = '';
			table.reload('tableData',{
				where: field,
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>