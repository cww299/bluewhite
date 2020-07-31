<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单管理</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td></td>
				<td><input type="text" class="layui-input" id="searchTime" placeholder="发货日期"></td>
				<td></td>
				<td style="width:150px;"><select name="audit">
									 <option value="">是否审核</option>
									 <option value="1">审核</option>
									 <option value="0" selected>未审核</option></select></td>
				<td></td>
				<td style="width:150px;"><select name="copyright">
									 <option value="">是否版权</option>
									 <option value="1">有版权</option>
									 <option value="0">无版权</option></select></td>
				<td></td>
				<td style="width:150px;"><select name="newBacth">
									 <option value="">是否借调</option>
									 <option value="1">借调</option>
									 <option value="0">非借调</option></select></td>
				<td></td>
				<td style="width:150px;"><select name="customerType">
									 <option value="">是否电商</option>
									 <option value="457">电商</option>
									 <option value="459">线下</option></select></td>
				<td></td>
				<td><input type="text" class="layui-input" name="customerName" placeholder="客户名"></td>
				<td></td>
				<td><input type="text" class="layui-input" name="userName" placeholder="业务员"></td>
				<td></td>
				<td><input type="text" class="layui-input" name="bacthNumber" placeholder="批次号"></td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button>
					<span style="display:none;" id="uploadSale">导入销售单</span></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script id="tableToolbar" type="text/html">
<div>
	<span class="layui-btn layui-btn-sm" lay-event="onekeyAudit">一键审核</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="unAudit">取消审核</span>
	<span class="layui-btn layui-btn-sm layui-btn-nromal" lay-event="addSale">生成销售单</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSale">删除销售单</span>
	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="uploadSale">导入销售单</span>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
}).define(
	['laydate','mytable','myutil','upload'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form		
		, mytable = layui.mytable
		, table = layui.table
		, laydate = layui.laydate
		, upload = layui.upload
		, myutil = layui.myutil
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.config.msgOffset = '200px';
		myutil.clickTr();
		var tipWin = '';
		laydate.render({
			elem:'#searchTime',range:'~'
		})
		var sty = "background-color: #5FB878;color: #fff;";
		var bg = "background-color: #ecf7b8;";
		layui.tablePlug.smartReload.enable(true);
		mytable.render({
			elem:'#tableData',
			smartReloadModel:true,
			url:'${ctx}/ledger/salePage?',	//deliveryStatus=1
			where: { audit: 0 },
			toolbar: '#tableToolbar',
			ifNull: '---',
			scrollX: true,
			parseData:function(ret){ 
				layui.each(ret.data.rows,function(index,item){
					if(item.deliveryStatus==0){
						item.deliveryNumber = '';
						item.deliveryDate = '';
						item.disputeNumber = '';
						item.disputeRemark = '';
					}
				})
				return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } 
			},
			verify:{ notNull:['price' ], price:[ 'price' ] },
			autoUpdate: {
				saveUrl: '/ledger/updateFinanceSale',
				isReload: true,
			},
			limits:[15,30,50,100,200,500,2000],
			limit:15,
			totalRow: ['count', 'sumPrice'],
			cols:[[
			       { type:'checkbox', fixed:'left',},
			       { title:'销售编号',	width:'11%',field:'saleNumber',   fixed:'left', style: sty },
			       { title:'发货日期',   	width:'7%',	field:'sendDate',templet:'<span>{{ d.sendDate?d.sendDate.split(" ")[0]:""}}</span>',  fixed:'left', style:sty },
			       { title:'业务员',   	width:'8%',	field:'customer_user_userName'},
			       { title:'客户',   		width:'8%',	field:'customer_name', },
			       { title:'批次号',   	width:'8%',	field:'bacthNumber',	},
			       { title:'是否借调',   	width:'6%',	field:'newBacth', templet:'<span>{{ d.newBacth==1?"借调":"非借调"}}</span>'	},
			       { title:'产品名',   	width:'15%',field:'product_name',},
			       { title:'离岸数量',   	width:'6%',	field:'count',	},
			       { title:'总价',   		width:'5%',	field:'sumPrice',	},
			       { title:'单价',   		width:'5%',	field:'price', 	edit: 'text', 	style: bg },
			       { title:'备注',   		width:'8%',	field:'remark', edit: 'text', 	style: bg },
			       { title:'到岸数量',   	width:'6%',	field:'deliveryNumber',	},
			       { title:'到岸日期',   	width:'7%',	field:'deliveryDate',templet:'<span>{{ d.deliveryDate?d.deliveryDate.split(" ")[0]:""}}</span>',	},
			       { title:'争议数量',   	width:'6%',	field:'disputeNumber',	},
			       { title:'争议备注',   	width:'8%',	field:'disputeRemark',	},
			       { title:'预计结款日期',  width:'8%',	field:'deliveryCollectionDate',	},
			       { title:'版权',   		width:'6%',	field:'copyright', 	fixed:'right',	templet:'<span>{{ d.copyright?"是":"否"}}</span>', style:sty},
			       { title:'是否审核', 	width:'6%',	field:'audit',		fixed:'right', 	templet:'<span>{{ d.audit==1?"是":"否"}}</span>',style:sty},
			       ]],
	       done:function(){
				var isDouble=0;
				$('td[data-field="price"]').on('click',function(obj){
					if(++isDouble%2!=0)
						$(this).click();
					if($('.layui-table-edit').length>0){
						var elem = this;
						var index = $(elem).closest('tr').attr('data-index');
						var trData = table.cache['tableData'][index];
						myutil.getData({
							url:'${ctx}/ledger/getSalePrice?customerId='+trData.customer.id+'&productId='+trData.product.id,
							done: function(data){
								var html = '无以往价格';
								if(data.length!=0){
									html='<div style="overflow: auto; max-height: 170px;">';
									layui.each(data,function(index,item){
										html += '<span style="margin:5px;" class="layui-badge layui-bg-green" data-price="'+item.price+'" data-id="'+trData.id+'">发货日期：'+
										item.sendDate.split(' ')[0]+' -- ￥'+item.price+'</span><br>';
									})
									html += "</div>"
								}
								tipWin = layer.tips(html, elem, {
									  tips: [3, '#78BA32'],
					                  time:0,
					                  success: function(layerElem){
					                	 // var left = Number.parseInt($(layerElem).css('left'))
					                	 // $(layerElem).css('left', (left-30)+'px')
					                	 $(layerElem).css('width','230px')
					                  }
					            });
								$('.layui-layer-tips .layui-badge').unbind().on('click',function(event){
									layui.stope(event)
									myutil.saveAjax({
										url:'/ledger/updateFinanceSale',
										data: {
											id: $(this).data('id'),
											price: $(this).data('price')
										},
										success:function(){ 
											layer.close(tipWin); 
											table.reload('tableData'); 
										}
									}) 
								}).mouseover(function(){
						    		$(this).css("cursor","pointer");								
						    	}).mouseout(function (){  
						    		$(this).css("cursor","default");
						        });
							}
						})
					}
				})
			}
		})
		$(document).on('mousedown', '', function (event) { 
			if($('.layui-layer-tips').length>0)
				layer.close(tipWin);
		});
		table.on('toolbar(tableData)',function(obj){
			switch(obj.event){
			case 'onekeyAudit': onekeyAudit(1); break;
			case 'unAudit': onekeyAudit(0); break;
			case 'addSale': addSale(); break;
			case 'deleteSale': deleteSale(); break;
			case 'uploadSale': uploadSale(); break;
			}
		})
		const uploadData = {
			customerType: null,
		}
		var load, customerTypeWin;
    	upload.render({
    		elem: '#uploadSale',
    		url: '${ctx}/temporaryPack/uploadSale',
    		data: uploadData,
    		before: function(){
    			load = layer.load(1,{ shade: 0.5 })
    		},
			done: function(res, index, upload){ //上传后的回调
				var tips = 'emsg'
				if(res.code == 0){
					tips = 'smsg';
					if(res.data && res.data.length > 0) {
						layer.open({
							type: 1,
							title: '导入异常',
							content: [
								'<span id="msg"></span>&nbsp;&nbsp;&nbsp;&nbsp;其中以下数据存在错误！',
								'<table id="uploadTable" lay-filter="uploadTable"></table>',
							].join(' '),
							offset: '50px',
							area: ['1200px', '600px'],
							success: function() {
								$('#msg').html(res.message);
								mytable.renderNoPage({
									elem: '#uploadTable',
									data: res.data,
									limit: 9999,
									height: '500px',
									curd: {
										btn: [],
									},
									cols: [(function(){
										var cols = [
											{ title: '行数', field: 'row', width: 70, },
											{ title: '错误', field: 'errorInfo', width: 150, },
											{ title: '发货日期', field: 'sendDate', },
											{ title: '批次号', field: 'bacthNumber', },
											{ title: '产品名', field: 'productName', },
											{ title: '数量', field: 'count', width: 70, },
											{ title: '客户', field: 'customerName', },
										]
										var dsCols = [
											{ title: '总价', field: 'sumPrice', width: 70, },
											{ title: '单价', field: 'price', width: 70, },
											{ title: '业务员', field: 'userName', },
										];
										if(uploadData.customerType == 457) {
											cols = cols.concat(dsCols)
										}
										return cols
									})()]
								})
							}
						})
					}
					table.reload('tableData')
					layer.close(customerTypeWin);
				}
		    	layer.close(load)
		    	myutil[tips](res.message)
			},
			error: function(){
				layer.close(load)
				myutil.esmg("接口出现异常，请联系管理员")
			},
			accept: 'file',
			exts: 'xls|xlsx',
    	})
		function uploadSale(){
			layer.open({
				type: 1,
				title: '请选择导入的客户类型',
				btn: ['确定','取消'],
				area: ['330px','160px'],
				offset: '50px',
				content: [
					'<div style="padding:10px;">',
						'<table class="layui-form"><tr>',
							'<td><b class="red">*</b>客户类型：</td>',
							'<td><select name="customerType" lay-verify="required">',
							// 457=电商  459=线下
									'<option value="457">电商</option>',
									'<option value="459">线下</option></select>',
									'<span lay-filter="uploadBtn" lay-submit></span></td>',
						'</tr></table>',
					'</div>',
				].join(' '),
				success: function(layerElem, layerIndex){
					customerTypeWin = layerIndex
					form.render();
					form.on('submit(uploadBtn)',function(obj){
						uploadData.customerType = obj.field.customerType
						$('#uploadSale').click();
					})
				},
				yes: function(){
					$('span[lay-filter="uploadBtn"]').click();
				}
			})
		}
    	var evenColor = 'rgb(133, 219, 245)';
		function addSale(){
			layer.open({
				type: 1,
				title: '生成销售单',
				area: ['90%','90%'],
				content: [
					'<div style="padding:10px;">',
						'<table class="layui-form searchTable">',
						'<tr>',
							'<td style="width:100px;"><select class="layui-input" id="selectone">',
										'<option value="sendTime">发货时间</option></select></td>',
							'<td><input type="text" name="orderTimeBegin" id="orderTimeBegin" placeholder="请输入时间" class="layui-input"></td>',
							'<td>产品名:</td>',
							'<td><input type="text" name="productName" class="layui-input"></td>',
							'<td>客户名:</td>',
							'<td><input type="text" name="customerName" class="layui-input"></td>',
							'<td><button type="button" class="layui-btn layui-btn-" lay-submit lay-filter="searchAdd">搜索</button></td>',
						'</tr>',
						'</table>',
						'<table id="addTable" lay-filter="addTable"></table>',
					'</div>',
				].join(''),
				success: function(layerElem,layerIndex){
					laydate.render({
						elem: '#orderTimeBegin', range: '~',
					})
					form.on('submit(searchAdd)',function(obj){
						var field = obj.field;
						if(field.orderTimeBegin){
							var t = field.orderTimeBegin.split(' ~ ');
							field.orderTimeBegin = t[0]+' 00:00:00';
							field.orderTimeEnd = t[1]+' 23:59:59';
						}else
							field.orderTimeEnd = '';
						var a="";
						var b="";
						if($("#selectone").val()=="time"){
							a="2019-05-08 00:00:00"
						}else{
							b="2019-05-08 00:00:00"
						}
						field.time = a;
						field.sendTime = b;
						table.reload('addTable',{
							where: field,
							page:{ curr:1 },
						})
					})
					var cols = [
				       { type:'checkbox',},
				       { title:'发货时间',   field:'sendTime',  width:110,type:'date',  },
				       { title:'贴包人',    field:'user_userName', width:100,	},
				       { title:'客户',     field:'customer_name',	},
				       { title:'批次号',    field:'underGoods_bacthNumber', minWidth:130, },
				       { title:'产品名',    field:'underGoods_product_name', width:280,	},
				       { title:'单包个数',   field:'singleNumber',	width:80, },
					];
					mytable.render({
						elem:'#addTable',
						size:'sm',
						url:'${ctx}/temporaryPack/findPagesQuantitative?flag=1&sale=0&interior=0&isSale=1',
						toolbar: [
							'<span class="layui-btn layui-btn-sm" lay-event="add">生成销售单</span>'
						].join(''),
						even:true,
						limits:[10,50,200,500,1000],
						curd:{
							btn: [],
							otherBtn:function(obj){
								if(obj.event=='add') {
									myutil.deleTableIds({
										url: '/temporaryPack/addSale',
										text: '请选择信息|是否确认生成销售单',
										type: 'post',
										table:'addTable',
										success: function(){
											table.reload('tableData')
											layer.close(layerIndex)
										}
									})
								}
							},
						},
						autoUpdate:{},
						parseData: parseData(),
						ifNull:'',
						cols:[ cols ],
				        autoMerge:{
				    	  field:['sendTime','user_userName','customer_name','0'], 
				    	  evenColor: evenColor,
				        },
				        done:function(ret,curr, count){
				    	    form.render();
							renderTableColor('#addTable');
							form.render();
						}
					})
				}
			})
		}
		function parseData(){
			return function(ret){
				if(ret.code==0){
					var data = [],d = ret.data.rows;
					tableDataNoTrans = d;
					for(var i=0,len=d.length;i<len;i++){
						var child = d[i].quantitativeChilds;
						if(!child || child.length==0){
							data.push($.extend({},{singleNumber:'',actualSingleNumber:'',remarks:''},d[i])); 
							continue;
						}
						for(var j=0,l=child.length;j<l;j++){
							if(child[j].saleId)
								continue;
							data.push($.extend({},child[j],{childId: child[j].id,},d[i])); 
						}
					}
					return {  msg:ret.message,  code:ret.code , data: data, count:ret.data.total }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			}
		}
		function renderTableColor(tableId){
			var whiteTd = ['0','sendTime','user_userName','customer_name'];
			layui.each(whiteTd,function(index,item){
				$(tableId).next().find('td[data-field="'+item+'"]').css('background','white');
			})
			var blueTd = ['underGoods_bacthNumber','underGoods_product_name','singleNumber'];
			layui.each(blueTd,function(index,item){
				$(tableId).next().find('tr:nth-child(even) td[data-field="'+item+'"]').css('background',evenColor);
			})
		}
		function deleteSale(){
			myutil.deleTableIds({
				url: '/temporaryPack/deleteSale',
				text: '请选择删除数据|是否确认删除？',
				table: 'tableData',
			})
		}
		function onekeyAudit(isAudit){
			var choosed = layui.table.checkStatus('tableData').data;
			if(choosed.length==0)
				return myutil.emsg('请选择相关信息');
			var ids = [];
			layui.each(choosed,function(index,item){
				ids.push(item.id);
			})
			myutil.saveAjax({
				url:'/ledger/auditSale?audit='+isAudit,
				data: { ids:ids.join(',') },
				success: function(){
					table.reload('tableData');
				}
			})
		}
		/*
		table.on('edit(tableData)',function(obj){
			var val = obj.value, msg='';
			isNaN(val) && (msg="请正确输入单价");
			val<0 && (msg="单价不能小于0");
			if(msg!='')
				myutil.emsg(msg);
			else {
				myutil.saveAjax({
					url:'/ledger/updateFinanceSale',
					data: {
						id: obj.data.id,
						price: val
					},
					success:function(){
						layer.close(tipWin);
						table.reload('tableData');
					}
				}) 
			}
		})
		*/
		form.on('submit(search)',function(obj){
			var val = $('#searchTime').val(), beg='',end='';
			if(val!=''){
				val = val.split('~');
				beg = val[0].trim()+' 00:00:00';
				end = val[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = beg;
			obj.field.orderTimeEnd = end;
			table.reload('tableData',{
				where: obj.field,
				page: { curr:1 },
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>