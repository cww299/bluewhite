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
	<title>量化单</title>
	<style>
		/* .layui-table th, .layui-table td, .layui-table[lay-skin="line"], .layui-table[lay-skin="row"], .layui-table-view, .layui-table-tool, .layui-table-header, .layui-table-col-set, .layui-table-total, .layui-table-page, .layui-table-fixed-r, .layui-table-tips-main, .layui-table-grid-down {
		    
		    border-color: gray;
		} */
	
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td style="width:100px;"><select class="layui-input" id="selectone">
							<option value="time">包装时间</option>
							<option value="sendTime">发货时间</option></select></td>
				<td><input type="text" name="orderTimeBegin" id="orderTimeBegin" placeholder="请输入时间" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>产品名:</td>
				<td><input type="text" name="productName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>客户名:</td>
				<td><input type="text" name="customerName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>上车编号:</td>
				<td><input type="text" name="vehicleNumber" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td id="isAuditSearchTextTd"></td>
				<td id="isAuditSearchTd"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>是否打印:</td>
				<td style="width:100px;"><select name="print"><option value="">请选择</option>
										<option value="0">否</option>
										<option value="1">是</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>是否发货:</td>
				<td style="width:100px;"><select name="flag"><option value="">请选择</option>
										<option value="0">否</option>
										<option value="1">是</option></select></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script type="text/html" id="printPackTpl">
<div style="page-break-after: always;">
	<table border="1" style="margin: auto;width: 95%;text-align:center;">
		<tr>
       	 	<td style="">收货人</td>
        	<td>{{ d.customer?d.customer.name:'---' }}</td>
			<td rowspan="2">
				<div id="qrcode{{ d.id }}"></div>
				<img id='qrcodeImg{{ d.id }}'/>  
			</td>
	    </tr>
		<tr>
	        <td style="">编号</td>
	        <td>{{ d.quantitativeNumber || '---' }}</td>
	    </tr>
		<tr>
			<td style="">批次号</td>
	        <td>产品名</td>
	        <td>数量</td>
	    </tr>
		{{# layui.each(d.quantitativeChilds,function(index,item){  }}
		<tr>
			<td style="">{{ item.underGoods.bacthNumber}}</td>
	        <td>{{ item.underGoods.product.name}}</td>
	        <td>{{ item.singleNumber}}</td>
	    </tr>
	    {{# }) }}
	</table>
</div>
</script>
<!-- 
stickBagAccount: 吴婷
 -->
<div id="toolbarTpl" style="display:none;">
	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="update">修改数据</span>
	<shiro:hasAnyRoles name="superAdmin,stickBagAccount">
		<span class="layui-btn layui-btn-sm layui-btn-" lay-event="add" id="stickBagAccountBtn">新增数据</span>
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cancelAudit">取消审核</span>
		<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="audit">审核</span>
	</shiro:hasAnyRoles>
	<shiro:hasAnyRoles name="superAdmin,stickBagStick">
		<span class="layui-btn layui-btn-sm layui-btn-primary" lay-event="print" id="stickBagStickBtn">打印</span>
		<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="send">发货</span>
		<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cancelSend">取消发货</span>
		<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="updateSendTime">修改发货时间</span>
	</shiro:hasAnyRoles>
</div>
<script type="text/html" >
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
	qrcode : 'layui/myModules/common/qrcode',
}).define(
	['mytable','laydate','qrcode'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable
		,tablePlug = layui.tablePlug; 
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({
			elem: '#orderTimeBegin', range: '~',
		})
		var allPackagMethodHtml = myutil.getBaseDataSelect({ type: 'outerPackaging', });
		var isStickBagStick = $('#stickBagStickBtn').length>0;
		var isStickBagAccount = $('#stickBagAccountBtn').length>0;
		if(isStickBagAccount){
			$('#isAuditSearchTd').html([
				'<select name="audit"><option value="">请选择</option>',
				'<option value="0">否</option>',
				'<option value="1">是</option></select>',
			].join(''));
			$('#isAuditSearchTd').css('width','100px');
			$('#isAuditSearchTextTd').html('是否审核:');
			form.render();
		}
		var allMaterials = [];
		var allUser ='',allCustomer='';
		var tableDataNoTrans = [];
		var evenColor = 'rgb(133, 219, 245)';
		mytable.render({
			elem:'#tableData',
			size:'sm',
			url:'${ctx}/temporaryPack/findPagesQuantitative',
			where:{
				audit: isStickBagAccount?'':1
			},
			toolbar: $('#toolbarTpl').html(),
			limit:15,
			even:true,
			limits:[15,50,200,500,1000],
			curd:{
				btn: isStickBagAccount?[4]:[],
				otherBtn:function(obj){
					if(obj.event=='add'){
						addEdit('add',{});
					}else if(obj.event=='update'){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length<1)
							return myutil.emsg('请选择数据编辑');
						var i = 0;
						for(;i<tableDataNoTrans.length;i++){
							if(tableDataNoTrans[i].id==check[0].id)
								break;
						}
						var set = new Set();
						layui.each(check,function(index,item){
							set.add(item.id);
						})
						var updateData = $.extend({},tableDataNoTrans[i],{id: Array.from(set).join(',')});
						addEdit('update',updateData);
					}else if(obj.event=='audit'){
						myutil.deleTableIds({
							 table:'tableData', 
							 text:'请选择信息|是否确认审核？',
							 url:'/temporaryPack/auditQuantitative?audit=1',
						})
					}else if(obj.event=='cancelAudit'){
						myutil.deleTableIds({
							 table:'tableData', 
							 text:'请选择信息|是否确认取消审核？',
							 url:'/temporaryPack/auditQuantitative?audit=0',
						})
					}else if(obj.event=='print'){
						printOrder();
					}else if(obj.event=='send'){ 
						openSendGoodWin();
					}else if(obj.event=='cancelSend'){
						myutil.deleTableIds({
							 table:'tableData',  
							 text:'请选择信息|是否确认取消发货？',
							 url:'/temporaryPack/sendQuantitative?flag=0',
						})
					}else if(obj.event=='updateSendTime'){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length==0)
							return myutil.emsg('请选择数据');
						var updateWin = layer.open({
							type:1,
							title:'修改发货时间',
							offset:'120px',
							content:
							['<div style="padding:10px;">',
								'<table>',
									'<tr>',
										'<td>发货时间：</td>',
										'<td>',
											'<input type="text" class="layui-input" id="updateTime">',
										'</td>',
									'</tr>',
									'<tr>',
										'<td style="padding-top:10px;">密码：</td>',
										'<td style="padding-top:10px;">',
											'<input type="password" class="layui-input" id="pwd">',
										'</td>',
									'</tr>',
								'</table>',
							  '</div>'].join(' '),
							btn:['确定','取消'],
							btnAlign:'c',
							area:'auto',
							success:function(){
								laydate.render({
									elem:'#updateTime',type:'datetime',value:new Date(),
								});
							},
							yes:function(){
								if($('#pwd').val()!='456789')
									return myutil.emsg('密码错误！');
								var ids = [];
								layui.each(check,function(idex,item){
									ids.push(item.id);
								})
								myutil.deleteAjax({
									url:'/temporaryPack/updateQuantitativeSendTime?sendTime='+$('#updateTime').val(),
									ids: ids.join(','),
									success:function(){
										table.reload('tableData');
										layer.close(updateWin);
									}
								})
							}
						})
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
							data.push($.extend({},child[j],{childId: child[j].id,},d[i])); 
						}
					}
					return {  msg:ret.message,  code:ret.code , data: data, count:ret.data.total }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			},
			ifNull:'',
			cols:[[
			       { type:'checkbox',},
			       { title:'量化编号',   field:'quantitativeNumber', width:145,	},
			       { title:'上车编号',   field:'vehicleNumber', width:145,	},
			       { title:'包装时间',   field:'time', width:110, type:'date', },
			       { title:'发货时间',   field:'sendTime',  width:110,type:'date',  },
			       { title:'贴包人',    field:'user_userName', width:100,	},
			       { title:'客户',     field:'customer_name',	},
			       { title:'审核',   field:'audit', 	transData:true, width:60, },
			       { title:'发货',   field:'flag', 	transData:true, width:60, },
			       { title:'打印',   field:'print', 	transData:true, width:60, },
			       { title:'批次号',    field:'underGoods_bacthNumber',	minWidth:130, },
			       { title:'产品名',    field:'underGoods_product_name', width:280,	},
			       { title:'单包个数',   field:'singleNumber',	width:80, },
			       { title:'实际数量',   field:'actualSingleNumber',	width:90,event:'transColor', 
			    	   templet: function(d){
    	   					return '<span style="color:'+(d.checks?'red':"")+'" data-red="'+(d.checks?'red':"")+'">'+
    	   					d.actualSingleNumber+'<span>'; },
			       },
			       { title:'备注',   field:'remarks',	width:90, edit:true,},  				
			       ]],
	       autoMerge:{
	    	 field:['quantitativeNumber','vehicleNumber','time','sendTime','audit','print','flag',
	    		 'user_userName','surplusSendNumber','surplusNumber','customer_name','0'], 
	    	 evenColor: evenColor,
	       },
	       done:function(ret,curr, count){
	    	    form.render();
				table.on('edit(tableData)',function(obj){
					var data = obj.data; 
					myutil.saveAjax({
						url: '/temporaryPack/updateActualSingleNumber',
						data:{
							id: data.childId,
							remarks: obj.value,
						},
						success:function(){
							table.reload('tableData');
						}
					})
				})
				//background: white;
				var whiteTd = ['0','quantitativeNumber','vehicleNumber','time','sendTime','user_userName','customer_name','audit','flag','print'];
				layui.each(whiteTd,function(index,item){
					$('#tableData').next().find('td[data-field="'+item+'"]').css('background','white');
				})
				var blueTd = ['underGoods_bacthNumber','underGoods_product_name','actualSingleNumber','singleNumber','remarks'];
				layui.each(blueTd,function(index,item){
					$('#tableData').next().find('tr:nth-child(even) td[data-field="'+item+'"]').css('background',evenColor);
				})
				form.render();
				$('div[lay-event="LAYTABLE_EXPORT"]').unbind().on('click',function(e){
					layui.stope(e);
					var url = myutil.config.ctx+"/temporaryPack/import/excelQuantitative?"+searchTableWhere;
					location.href= url;
				})
			}
		})
		table.on('tool(tableData)',function(obj){
			if(isStickBagAccount){
				if(obj.event=='transColor'){
					if(obj.data.checks)
						layer.confirm('是否核对！<br>注意：核对后无法回溯原始贴包数字',function(){
							var data = obj.data;
							myutil.saveAjax({
								url:'/temporaryPack/checkNumber',
								data:{
									id: data.childId,
								},
								type:'get',
								success:function(){
									table.reload('tableData');
								}
							})
						})
				}
			}
			
		})
		var allLogistics = '';
		myutil.getData({
			url:myutil.config.ctx+'/basedata/list?type=logistics',
			success:function(data){
				for(var i in data)
					allLogistics += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
			}
		})
		function openSendGoodWin(){
			var check = layui.table.checkStatus('tableData').data;
			if(check.length==0)
				return myutil.emsg('请选择数据');
			var ids = [];
			for(var i in check)
				ids.push(check[i].id);
			layer.open({
				type:1,title:'是否确认发货',offset:'50px',
				content:[
					'<div style="padding:25px;">',
					'<table class="layui-form">',
						'<tr>',
							'<td>物流点：</td>',
							'<td colspan="2">',
								'<select name="logisticsId" lay-verify="required" lay-search>',
									'<option value="">请选择</option>',
									allLogistics,
								'</select>',
							'</td>',
						'</tr>',
						'<tr>',
							'<td>包装方式：</td>',
							'<td colspan="2">',
								'<select name="outerPackagingId" lay-search>',
									'<option value="">请选择</option>',
									allPackagMethodHtml,
								'</select>',
							'</td>',
						'</tr>',
						'<tr>',
							'<td>上车编号：</td>',
							'<td style="width:95px;padding-top: 8px;">',
							   '<input type="text" class="layui-input" id="sendTimeInput" lay-verify="required" name="time"></td>',
							'<td style="width:95px;padding-top: 8px;">',
							   '<input type="text" class="layui-input" name="no" lay-verify="required" ',
								' onkeyup="value=value.replace(/[^\\d]/g,\'\')"  placeholder="上车编号"></td>',
							'<td><span style="display:none;" lay-submit lay-filter="sureSendGoodBtn"></td>',
						'</tr>',
					'</table>',
					'</div>',
				].join(' '),
				btn:['确定','取消'],
				btnAlign:'c',
				success:function(layerElem,layerIndex){
					form.render();
					laydate.render({ elem:'#sendTimeInput',format:'yyyyMMdd',value:new Date(), });
					form.on('submit(sureSendGoodBtn)',function(obj){
						var f = obj.field;
						f.no = PrefixInteger(f.no,4);
						var vn = f.time+ f.no;
						var lid = f.logisticsId;
						myutil.deleteAjax({
							url:'/temporaryPack/sendQuantitative?flag=1&vehicleNumber='+vn
									+'&logisticsId='+lid+'&outerPackagingId='+f.outerPackagingId,
							ids: ids.join(','),
							success:function(){
								layer.close(layerIndex);
								table.reload('tableData');
							}
						})
						function PrefixInteger(num, length) {
						 return ( "0000000000000000" + num ).substr( -length );
						}
					})
				},
				yes:function(){
					$('span[lay-filter="sureSendGoodBtn"]').click();
				}
			})
		}
		function printOrder(){	
			var choosed=layui.table.checkStatus('tableData').data;
			if(choosed.length<1)
				return myutil.emsg('请选择打印信息');
			var printData = [];
			layui.each(choosed,function(index1,itemChoosed){
				layui.each(tableDataNoTrans,function(index2,itemNoTrans){
					if(itemChoosed.id == itemNoTrans.id){
						printData.push(itemNoTrans);
						return;
					}
				})
			})
			var tpl = $('#printPackTpl').html(), html='<div id="printDiv">';
			var allId = [];
			layui.each(printData,function(index,item){
				allId.push(item.id);
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
				success:function(){
					var host = window.location.host+myutil.config.ctx;
					for(var i in allId){
						var qrcode = $('#qrcode'+allId[i]).qrcode({
							render:'canvas',
							text: "http://"+host+"/twoDimensionalCode/scanSendOrder?id="+allId[i],
							width:160,height:160
						}).hide();	
						var canvas=qrcode.find('canvas').get(0);  
						$('#qrcodeImg'+allId[i]).attr('src',canvas.toDataURL('image/jpg'))  
					} 
				},
				yes: function(){
					var ids = new Set();
					layui.each(choosed,function(i,item){
						ids.add(item.id);
					})
					myutil.deleteAjax({
						ids: Array.from(ids).join(),
						url:'/temporaryPack/printQuantitative',
						success:function(){
							table.reload('tableData');
						}
					})
					printpage('printDiv');
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
		
		var tpl =  [
			'<div style="padding:10px;">',
			'<div>',
				'<table class="layui-form">',
					'<tr>',
						'<td>量化时间：</td>',
						'<td>',
							'<input class="layui-input" id="addEditTime" name="time" value="2019-12-06 17:55:50" lay-verify="required">',
						'</td>',
						'<td>&nbsp;&nbsp;贴包人：</td>',
						'<td>',
							'<select id="packPeopleSelect" lay-search name="userId">',
								'<option value="">请选择</option></select>',
						'</td>',
						'<td>&nbsp;&nbsp;客户：</td>',
						'<td>',
							'<select id="customerSelect" lay-search name="customerId"> ',
								'<option value="">请选择</option></select>',
						'</td>',
						'<td>&nbsp;&nbsp;总包数：</td>',
						'<td>',
							'<input class="layui-input" name="sumPackageNumber" value="{{ d.sumPackageNumber || 0}}" ',
								' id="sumPackageNumberInput" lay-verify="required">',
							'<input type="hidden" name="ids" value="{{ d.id || ""}}">',
						'</td>',
						'<td>&nbsp;&nbsp;<span class="layui-btn" id="saveBtn" lay-filter="saveBtn" lay-submit>保存</span></td>',
					'</tr>',
				'</table>',
			'</div>',
			'<div style="float:left;width:58%;">',
				'<table id="addTable" lay-filter="addTable"></table>',
			'</div>',
			'<div style="float:right;width:40%;">',
				'<table id="addMaterTable" lay-filter="addMaterTable"></table>',
			'</div>',
		'</div>',
		].join(' ');
		function addEdit(type,data){	//stickBagAccount角色操作左边  stickBagStick  右边
			var title = '新增量化单';
			if(data.id){
				title = '修改量化单';
			}
			var html = '';
			laytpl(tpl).render(data,function(h){
				html = h;
			})
			//getDataOfUoloadOrder();
			var addEditWin = layer.open({
				type:1,
				area:['90%','80%'],
				title: title,
				content: html,
				success: function(){
					var formData = {};
					form.on('submit(saveBtn)',function(obj){
						if(obj.field.sumPackageNumber==0)
							return myutil.emsg('总包数数量不能为0');
						if(!obj.field.customerId){
							layer.confirm("客户未填，若是名创客户可忽略填写,确定则新增，取消则返回填写。",function(){
								formData = obj.field;
								$('span[lay-event="saveTempData"]').click();
							})
						}else{
							formData = obj.field;
							$('span[lay-event="saveTempData"]').click();
						}
					})
					var addTable = [],addMate = [];
					laydate.render({
						elem:'#addEditTime',
						value: data.time || new Date(),
						type:'datetime',
					})
					$('#packPeopleSelect').append(allUser);
					$('#customerSelect').append(allCustomer);
					if(data.id){
						addTable = data.quantitativeChilds;
						addMate = data.packingMaterials
						layui.each(addMate,function(index,item){
							item.packagingId = item.packagingMaterials.id;
						})
						$('#sumPackageNumberInput').attr('disabled','disabled')
						$('#packPeopleSelect').val(data.user?data.user.id:'');
						$('#customerSelect').val(data.customer?data.customer.id:'');
					}
					mytable.renderNoPage({
						elem: '#addTable',
						data: addTable,
						size:'lg',
						curd:{
							btn: isStickBagAccount?[1,2,3]:[3],
							saveFun: function(d){
								var url = '/temporaryPack/saveQuantitative';
								layui.each(table.cache['addTable'],function(index,item){
									if(typeof(item)==='object' && item.length==0)
										return;
									d.push({
										id: item.id,
										singleNumber: item.singleNumber,
										underGoodsId: item.underGoodsId || item.underGoods.id,
									})
								})
								if(d.length===0)
									return myutil.emsg('下货单不能为空！');
								for(var i=0;i<d.length;i++){
									if(!d[i].underGoodsId)
										return myutil.emsg('请选择下货单！');
								}
								var mateData = table.getTemp('addMaterTable').data;
								layui.each(table.cache['addMaterTable'],function(index,item){
									if(typeof(item)==='object' && item.length==0)
										return;
									mateData.push({
										id: item.id,
										packagingId: item.packagingId,
										packagingCount: item.packagingCount,
									})
								})
								
								myutil.saveAjax({
									url: url,
									data: $.extend({},{
											child: JSON.stringify(d),
											packingMaterialsJson: JSON.stringify(mateData),
											},formData
										),
									success:function(){
										layer.close(addEditWin);
										table.reload('tableData');
									}
								})
							},
							deleFun:function(ids,check){ },
							addTemp:{
								underGoodsId: "",
								singleNumber: 0,actualSingleNumber:0,
							},
						},
						autoUpdate:{
							field: { underGoods_id:'underGoodsId', },
							saveUrl: '/temporaryPack/setActualSingleNumber',
							nolyUpdateField: ['actualSingleNumber'],
						},
						verify:{
							count:['actualSingleNumber'],
						},
						cols:[(function(){
							var cols = [
								{ type:'checkbox',},
								{ title:'下货单~批次号~剩余数量', field:'underGoods_id', edit:false,
									/* type:'select',
									select:{data: allUoloadOrder, name:['product_name','bacthNumber','surplusStickNumber'],} */  
									templet: getTableSelect(),
								},
						        { title:'单包个数',   field:'singleNumber',	 edit: isStickBagAccount?'number':false,	width:'10%',},
						        { title:'实际发货数量',   field:'actualSingleNumber',	 edit: isStickBagStick?'number':false,	width:'15%',},
							];
							if(isStickBagAccount)
								cols.push({ title:'操作',field:'de', event:'deleteTr', edit:false,width:'10%',
						        	templet:'<div><span class="layui-btn layui-btn-xs layui-btn-danger">删除</span></div>'});
							return cols;
						})(),],
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
							form.on('select(tableSelect)',function(obj){
								var index = $(obj.elem).closest('tr').data('index');
								var trData = layui.table.cache['addTable'][index];
								trData['underGoodsId'] = obj.value;
							})
						}
					})
					function getTableSelect(){
						return function(d){
							return ['<select lay-filter="tableSelect" lay-search '+
								'lay-url="${ctx}/temporaryPack/findUnderGoods" lay-searchName="productName"'+
								' lay-name="productName|bacthNumber|surplusStickNumber">',
									'<option value="">请选择</option>',
									(function(){
										if(d.id){
											var getData = myutil.getDataSync({
												url: myutil.config.ctx+'/temporaryPack/findUnderGoods?id='+d.underGoods.id,
											})
											var number = getData.surplusStickNumber || 0;
											return '<option value="'+d.id+'" selected>'+d.underGoods.product.name+' ~ '+
											d.underGoods.bacthNumber+' ~ '+number+'</option>';
										}
										return "";
									})(),
								'</select>',
							].join(' ');
						}
					}
					mytable.renderNoPage({
						elem: '#addMaterTable',
						data: addMate,
						size:'lg',
						curd:{
							btn:isStickBagStick?[1,2,]:[],
							addTemp:{
								packagingId: allMaterials[0]?allMaterials[0].id:"",
								packagingCount: 0,
								productCount: 0,
							},
							deleFun:function(ids,check){ },
						},
						autoUpdate:{
							field: { packagingMaterials_id:'packagingId', },
						},
						verify:{
							count:['packagingCount','productCount'],
						},
						cols:[
							(function(){
								var cols = [
									{ type:'checkbox',},
									{ title:'材料', field:'packagingMaterials_id', type:'select',select:{data: allMaterials, } },
									{ title:'数量',   field:'packagingCount',	edit:isStickBagStick, width:'15%', },
									{ title:'包装单个数量',   field:'productCount',	edit:isStickBagStick, width:'25%', },
									
								];
								if(isStickBagStick)
									cols.push({ title:'操作',   field:'de',	 event:'deleteTr', edit:false,width:'20%',
						        		templet:'<div><span class="layui-btn layui-btn-xs layui-btn-danger">删除</span></div>'});
								return cols;
							})()
						],
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
			url: myutil.config.ctx+'/basedata/list?type=packagingMaterials',
			done: function(data){
				allMaterials = data;
			}
		})
		myutil.getData({
			url: myutil.config.ctx+'/system/user/findUserList?foreigns=0&isAdmin=false&orgNameIds=55&quit=0',
			done: function(data){
				layui.each(data,function(index,item){
					allUser += '<option value="'+item.id+'">'+item.userName+'</option>';
				})
			}
		})
		myutil.getData({
			url: myutil.config.ctx+'/ledger/getCustomer?customerTypeId=459',
			done: function(data){
				layui.each(data,function(index,item){
					allCustomer += '<option value="'+item.id+'">'+item.name+'</option>';
				})
			}
		})
		var searchTableWhere = '';
		document.onkeyup = function(event) {  
			if(event.keyCode==13)
				$('button[lay-filter="search"]').click();
		}
		form.on('submit(search)',function(obj){
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
			field.time=a;
			field.sendTime=b;
			searchTableWhere = '';
			for(var key in field){
				searchTableWhere += ('&'+key+"="+field[key]);
			}
			table.reload('tableData',{
				where: field,
				page:{ curr:1 },
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>