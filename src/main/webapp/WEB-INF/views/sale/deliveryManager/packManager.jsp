<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>贴包管理</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
.layui-card .layui-table-cell{	/* 表格内容自动换行样式 */
	  height:auto !important; 
	  padding:0px; 
}
.layui-card  .layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(0%);
}
.layui-card .layui-table tbody tr:hover, .layui-table-hover {
	 background-color: transparent; 
}
td{
	text-align:center;
}
</style>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>包装时间：</td>
				<td><input type="text" class="layui-input" id="searchTime"></td>
				<td>&nbsp;&nbsp;</td>
				<td>编号：</td>
				<td style="width:120px;"><input type="text" class="layui-input" name="number"></td>
				<td>&nbsp;&nbsp;</td>
				<td>客户：</td>
				<td style="width:120px;"><input type="text" class="layui-input" name="customerName"></td>
				<td>&nbsp;&nbsp;</td>
				<td>批次号：</td>
				<td style="width:120px;"><input type="text" class="layui-input" name="bacthNumber"></td>
				<td>&nbsp;&nbsp;</td>
				<td>产品：</td>
				<td style="width:120px;"><input type="text" class="layui-input" name="productName"></td>
				<td>&nbsp;&nbsp;</td>
				<td>是否发货：</td>
				<td style="width:120px;"><select name="flag" lay-search><option value="">是否发货</option>
												   <option value="0" selected>未发货</option>
												   <option value="1">已发货</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table class="layui-form" id="packTable" lay-filter="packTable"></table>
	</div>
</div>
<div style="display:none;padding:10px;" id="addEditWin">
	<table class="layui-form">
		<tr>
			<td>贴单时间：</td>
			<td><input id="sendDate" class="layui-input"></td>
			<td>&nbsp;&nbsp;</td>
			<td>客户：</td>
			<td><select id="addEditCustomer" lay-search><option value="">请选择</option></select></td>
			<td>&nbsp;&nbsp;</td>
			<td>贴包人：</td>
			<td><select id="addEditPackPeople" lay-search><option value="">请选择</option></select></td>
			<td>&nbsp;&nbsp;</td>
			<td>编号：</td>
			<td><input id="addEditNumber" class="layui-input"></td>
			<td>&nbsp;&nbsp;</td>
			<td><span class="layui-btn layui-btn-sm" id="sureAddEidtBtn">确定新增</span></td>
		</tr>
	</table>
	<div style="width:68%;float:left;">
		<div id="childTable" lay-filter="childTable"></div>
	</div>
	<div style="width:30%;float:right;">
		<div id="materialTable" lay-filter="materialTable"></div>
	</div>
</div>

</body>

<!-- 表格工具栏模板 -->
<script type="text/html" id="packToolbar">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="deletes"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改</span>
	<span lay-event="onekey"  class="layui-btn layui-btn-sm layui-btn-normal" >一键发货</span>
	<span lay-event="printPack"  class="layui-btn layui-btn-sm layui-btn-warm" >打印贴包单</span>
	<span lay-event="printSend"  class="layui-btn layui-btn-sm layui-btn-warm" >打印发货单</span>
</div>
</script>
<script type="text/html" id="tableToolbar">
<div class="layui-btn-container">
	<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
	<span lay-event="deletes"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
</div>
</script>
<script type="text/html" id="printSendTpl">
<div style="padding:20px;">          
    <table style="margin: auto;width: 80%;">
        <tr>
            <td style="text-align:center;">收货人名：</td>
            <td>&nbsp;&nbsp;</td>
            <td style="text-align:left;">{{ d.customer.name }}</td>
            <td>&nbsp;&nbsp;</td>
            <td style="text-align:center;">收货人电话：</td>
        </tr>
        <tr>
            <td style="text-align:center;">当批外包编号：</td>
            <td>&nbsp;&nbsp;</td>
            <td style="text-align:left;">{{ d.number}}</td>
            <td>&nbsp;&nbsp;</td>
            <td style="text-align:center;">---</td>
        </tr>
    </table>
    <table border="1" style="margin: auto;width: 80%;text-align:center;">
        <tr>
            <td>批次号</td>
            <td style="width:500px;">产品名</td>
            <td>当件内装数量</td>
        </tr>
		{{# layui.each(d.packingChilds,function(index,item){  }}
		<tr>
            <td>{{ item.bacthNumber }}</td>
            <td>{{ item.product.name}}</td>
            <td>{{ item.count}}</td>
        </tr>
        {{# }) }}
    </table>
</div>
<hr>
</script>
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
<script type="text/html" id="flagTpl">
{{#
  var color='green', text='未发货';
  if(d.flag==1)
      color='', text='已发货';
}}
<span class="layui-badge layui-bg-{{color}}">{{text}}</span>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
	myutil: 'layui/myModules/myutil',
}).define(
	['tablePlug','myutil','laydate','laytpl'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form		
		, table = layui.table 
		, laytpl = layui.laytpl
		, myutil = layui.myutil
		, laydate = layui.laydate
		, tablePlug = layui.tablePlug;
		myutil.config.ctx = '${ctx}';
		myutil.config.msgOffset = '250px';
		myutil.clickTr();
		myutil.timeFormat();
		var allMaterials = [], allSend = [], allCustom = [];
		var searchTime = new Date().format("yyyy-MM-dd");
		laydate.render({
			elem: '#sendDate', 
			value: searchTime,
			done: function(val){
				searchTime = val;
				getAllSend();
				table.reload('childTable');
			}
		})
		laydate.render({
			elem: '#searchTime',range: '~'
		})
		var addEditId = '',addEditWin = null;
		getData();
		table.render({
			elem:'#packTable',
			url:'${ctx}/ledger/packingPage',
			where: { flag:0 },
			toolbar:'#packToolbar',
			page:true,
			size:'lg',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'包装时间',   field:'packingDate',width:'8%',templet:'<span>{{ d.packingDate.split(" ")[0] }}</span>',	},
			       {align:'center', title:'编号',   field:'number',  width:'8%', },
			       {align:'center', title:'客户',   field:'customer',width:'8%', templet:'<span>{{ d.customer?d.customer.name:""}}</span>'	},
			       {align:'center', title:'是否发货',   field:'flag',  width:'8%',templet:'#flagTpl' },
			       {align:'center', title:'贴包人',   field:'user',  width:'6%', templet:'<span>{{ d.user?d.user.userName:"---"}}</span>'},
			       {align:'center', title:'包装物及数量',   field:'packingMaterials', 	templet: getMaterial(),},
			       {align:'center', title:'子单批次号',   field:'packingChilds',width:'8%',templet: getChildHtml('bacthNumber'),	},
			       {align:'center', title:'子单产品',   field:'packingChilds',width:'18%',templet: getChildHtml('product'),	},
			       {align:'center', title:'数量',   field:'packingChilds', width:'6%', templet: getChildHtml('count'),	},
			       ]]
		})
		function getChildHtml(field){
			return function(d){
				var html = '<table style="width:100%;" class="layui-table">';
				for(var i=0;i<d.packingChilds.length;i++){
					var t=d.packingChilds[i];
					var style = 'border-right:none;';
					if(i==d.packingChilds.length-1)
						style += 'border-bottom:none;';
					html+='<tr><td style="'+style+'">'+ (field=='product'?t[field]['name']:t[field]) +'&nbsp;</td></tr>';
				}
				if(d.packingChilds.length==0)
					html+='<tr><td style="border-right:none;border-bottom:none;">---&nbsp;</td></tr>';
				return html+'</table>';
			}
		}
		function getMaterial(){
			return function(d){
				var html = '';
				for(var i=0;i<d.packingMaterials.length;i++){
					var item = d.packingMaterials[i];
					var br = (i+1)%5==0?'<br>':'';
					html+= '<span class="layui-badge layui-bg-green">'+ item.packagingMaterials.name+":"+item.packagingCount + '</span>&nbsp;'+br;
				}
				return html;
			}
		}
		$('#sureAddEidtBtn').on('click',function(obj){
			var child =  table.getTemp('childTable').data, 
			    material =  table.getTemp('materialTable').data,
			    number = $('#addEditNumber').val(),
			    customerId = $('#addEditCustomer').val(),
			    packingDate =  $('#sendDate').val();
			if(addEditId!=''){
				layui.each(table.cache['childTable'],function(index,item){
					if(item.id && item.id!=''){
						child.push({
							sendGoodsId: item.sendGoodsId,
							count: item.count,
							id: item.id,
						})
					}
				})
				layui.each(table.cache['materialTable'],function(index,item){
					if(item.id && item.id!=''){
						material.push({
							packagingId: item.packagingId,
							packagingCount: item.packagingCount,
							id: item.id,
						})
					}
				})
			}
			var msg = '';
			number=='' && (msg='贴包单编号不能为空');
			customerId=='' && (msg='贴包单客户不能为空');
			packingDate=='' && (msg='贴包单贴单日期不能为空');
			layui.each(child,function(index,item){
				item.count==0 && (msg='发货数量不能为0');
				isNaN(item.count) && (msg='发货数量只能为数字');
				item.sendGoodsId=='' && (msg='日期、批次号、产品不能为空');
				if(msg!='') return;
			})
			layui.each(material,function(index,item){
				item.packagingCount==0 && (msg='发货包装材料不能为0');
				isNaN(item.packagingCount) && (msg='发货包装材料只能为数字');
				item.packagingId=='' && (msg='包装材料不能为空');
				if(msg!='') return;
			})
			if(msg!='')
				return myutil.emsg(msg);
			myutil.saveAjax({
				url:'/ledger/addPacking',
				data: {
					id: addEditId,
					userId: $('#addEditPackPeople').val(),
					number: number,
					packingDate: packingDate+' 00:00:00',
					customerId: customerId,
					childPacking: JSON.stringify(child),
					packingMaterialsJson: JSON.stringify(material),
				},
				success:function(){
					table.reload('packTable');
					layer.close(addEditWin);
				}
			})
		})
		form.on('submit(search)',function(obj){
			var begin='',end='',time = $('#searchTime').val();
			if(time!=''){
				time=time.split('~');
				begin = time[0].trim()+' 00:00:00';
				end = time[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = begin;
			obj.field.orderTimeEnd = end;
			table.reload('packTable',{
				where: obj.field,
				page: { curr:1 },
			})
		}) 
		table.on('toolbar(packTable)',function(obj){
			switch(obj.event){
			case 'add':	addEdit('add');		break;
			case 'update':	addEdit('edit'); 	break;
			case 'deletes':	deletes();			break;
			case 'onekey': onekey(); 	break;
			case 'printSend': printOrder(printSendTpl);	break;
			case 'printPack': printOrder(printPackTpl);	break;
			}
		})
		function printOrder(printTpl){	
			var choosed=layui.table.checkStatus('packTable').data;
			if(choosed.length<1)
				return myutil.emsg('请选择打印信息');
			var tpl = printTpl.innerHTML, html='<div id="printDiv">';
			layui.each(choosed,function(index,item){
				laytpl(tpl).render(item,function(h){ html += h; })
			})
			layer.open({
				title: '打印',
				area: ['80%','80%'],
				content: html+'</div>',
				btnAlign: 'c',
				btn: ['打印','取消'],
				shadeClose: true,
				yes: function(){
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
		function onekey(){
			var choosed=layui.table.checkStatus('packTable').data;
			if(choosed.length<1)
				return myutil.emsg('请选择发货信息');
			var defaultTime = choosed[0].packingDate;
			for(var i=0;i<choosed.length;i++){
				if(choosed[i].packingDate!=defaultTime)
					return myutil.emsg('一键发货只能选择同一天的包装时间单');
			}
			var sureTimeWin = layer.open({
				title:'提示',
				area: ['25%','35%'],
				btn: ['确认','取消'],
				content: '<div style="padding:5px;"><span class="layui-badge">提示：请确认发货时间</span><input type="text" class="layui-input" id="sureTime"></div>',
				success:function(){
					laydate.render({ elem: '#sureTime',value: defaultTime.split(' ')[0] })
				},
				yes: function(){
					var val = $('#sureTime').val()+' 00:00:00';
					var ids = [];
					layui.each(choosed,function(index,item){
						ids.push(item.id);
					})
					myutil.saveAjax({
						url: '/ledger/sendPacking',
						data: {
							sendDate: val,
							ids: ids.join(',')
						},
						success: function(){
							layer.close(sureTimeWin);
							table.reload('packTable');
						}
					})
				}
			})
		}
		renderTable({
			elem: '#materialTable',
			cols: [[
					{align:'center',type:'checkbox'},
			        {align:'center',field:'packagingId',title:'材料',edit:false,templet: getSelectHtml('materialTable','packagingMaterials') },
			        {align:'center',field:'packagingCount',title:'数量',edit: 'text',},
			        ]],
			done: function(res){
				layui.each(res.data,function(index,item){
					item.packagingId = item.packagingMaterials.id;
				})
			}
		})
		renderTable({
			elem: '#childTable',
			cols: [[
					{align:'center',type:'checkbox'},
			        {align:'center',field:'sendGoodsId',title:'日期 ~ 批次号 ~ 产品',edit:false, templet: getSelectHtml('childTable','bacthNumber')},
			        {align:'center',field:'count',title:'数量',width:'10%',edit: 'text',},
			        ]]
		})
		function getSelectHtml(tid,field){
			return function(d){
				var data = (field=='bacthNumber'?allSend:allMaterials);
				var html = '<select lay-search lay-filter="tableSelect" data-table="'+tid+'"><option value="">请选择</option>';
				for(var i=0;i<data.length;i++){
					var item = data[i]
					, id = d.packagingMaterials?d.packagingMaterials.id:''
					, title = item.name;
					if(field=='bacthNumber'){
						id = d.sendGoodsId;
						title = item.sendDate.split(' ')[0]+" ~ "+item.bacthNumber+" ~ "+item.product.name;
					}
					var selected = ( id==item.id?'selected':'');
					html += '<option value="'+item.id+'" '+selected+'>'+title+'</option>';
				}
				return html+='</select>';
			}
		}
		form.on('select(tableSelect)',function(obj){
			var index = $(obj.elem).closest('tr').attr('data-index');
			var field = $(obj.elem).closest('td').attr('data-field');
			var tableId = $(obj.elem).attr('data-table');
			var trData = layui.table.cache[tableId][index];
			trData[field] = obj.value;
		})
		table.on('toolbar(materialTable)',function(obj){
			switch(obj.event){
			case 'addTempData': addTempData('materialTable'); break;
			case 'cleanTempData': table.cleanTemp('materialTable'); break;
			case 'deletes':  deleteChild('materialTable'); break;
			}
		})
		table.on('toolbar(childTable)',function(obj){
			switch(obj.event){
			case 'addTempData': addTempData('childTable'); break;
			case 'cleanTempData': table.cleanTemp('childTable'); break;
			case 'deletes': deleteChild('childTable');  break;
			}
		})
		function addTempData(tid,data){
			var allField = { packagingId:'', packagingCount:0 };
			if(tid=='childTable'){
				allField = {  sendGoodsId:'', count:0 };
			}
			allField = data || allField;
			table.addTemp(tid,allField,function(trElem){ 
				var isDouble=0;
				$('td[data-edit="text"]').unbind().on('click',function(obj){	
					++isDouble;
					if(isDouble%2!=0)
						$(this).click();
					if( $('.layui-table-edit').length>0 ){
						$(this).find('input').select()
					}
				})
			});
		}
		function deleteChild(tid){
			var checked = layui.table.checkStatus(tid).data;
			if(checked.length==0)
				return myutil.emsg('请选择相关信息删除');
			layer.confirm('是否确认删除？',function(){
				var url='/ledger/deletePackingChild';
				tid=='materialTable' && (url='/ledger/deletePackingMaterials');
				var ids = [];
				layui.each(checked,function(index,item){
					ids.push(item.id)
				})
				myutil.deleteAjax({
					url: url,
					ids: ids.join(','),
					success: function(){
						getReloadTemp(tid,ids);		//重载表格需要将临时数据进行重新添加
					}
				})
			})
		}
		function addEdit(type){
			var choosed=layui.table.checkStatus('packTable').data,
			title='新增';
			addEditId = '';
			searchTime = new Date().format("yyyy-MM-dd");
			var childData = [],materialData = [],cusId = '',userId='';
			$('#addEditCustomer').val('');
			$('#sendDate').removeAttr('disabled');
			$('#addEditNumber').removeAttr('disabled');
			$('#sureAddEidtBtn').html('确定新增');
			if(type=='edit'){
				var msg = '';
				choosed.length>1 && (msg = "不能同时编辑多条信息");
				choosed.length<1 && (msg = "至少选择一条信息编辑");
				if(choosed.length!=1)
					return myutil.emsg(msg);
				data=choosed[0];
				title="修改";
				addEditId = data.id;	//当前修改对象的id
				cusId = data.customer.id;
				userId = data.user?data.user.id : '';
				childData = data.packingChilds;
				materialData = data.packingMaterials;
				searchTime = data.packingDate.split(' ')[0];
				$('#addEditNumber').val(data.number);
				$('#sureAddEidtBtn').html('确定修改');
				$('#sendDate').attr('disabled','disabled');
				$('#addEditNumber').attr('disabled','disabled');
			}else
				getNumber();
			addEditWin=layer.open({
				type:1,
				title:title,
				area:['90%','90%'],
				content: $('#addEditWin'),
				success: function(){
					getAllSend();
					$('#sendDate').val(searchTime);
					$('#addEditCustomer').val(cusId);
					$('#addEditPackPeople').val(userId);
					table.reload('childTable',{ data: childData });
					table.reload('materialTable',{ data: materialData });
					form.render();
				}
			})
		}
		function deletes(){
			var choosed=layui.table.checkStatus('packTable').data;
			if(choosed.length<1)
				return myutil.emsg('请选择删除信息');
			layer.confirm("是否确认删除？",function(){
				var ids=[];
				for(var i=0;i<choosed.length;i++)
					ids.push(choosed[i].id);
				myutil.deleteAjax({
					url:'/ledger/deletePacking',
					ids: ids.join(','),
					success: function(){
						table.reload('packTable');
					}
				})
			})
		}
		
		function renderTable(opt){
			table.render({
				elem: opt.elem,
				data: opt.data || [],
				toolbar : '#tableToolbar',
				height: '600',
				size:'lg',
				cols: opt.cols,
				done: function(res){
					var isDouble=0;
					$('td[data-edit="text"]').unbind().on('click',function(obj){	
						++isDouble;
						if(isDouble%2!=0)
							$(this).click();
						if( $('.layui-table-edit').length>0 ){
							$(this).find('input').select()
						}
					})
					opt.done && opt.done(res)
				}
			})
		}
		
		function getData(){
			myutil.getData({
				url:'${ctx}/basedata/list?type=packingMaterials',
				async: false,
				done: function(data){
					layui.each(data,function(index,item){
						allMaterials.push({
							id: item.id, name: item.name
						})
					})
				}
			});
			getCustomerSelect('');
			getPackPeople();
		}
		function getNumber(){
			myutil.getData({
				url:'${ctx}/ledger/getPackingNumber?sendDate='+searchTime+' 00:00:00',
				async: false,
				done: function(data){
					$('#addEditNumber').val(data);
				}
			});
		}
		function getAllSend(){
			myutil.getData({
				url:'${ctx}/ledger/getSearchSendGoods?sendDate='+searchTime+' 00:00:00',
				async: false,
				done: function(data){
					allSend = data;
				}
			});
		}
		function getCustomerSelect(){
			myutil.getSelectHtml({
				url:'/ledger/allCustomer',
				value: 'id',
				title: 'name',
				tips: '请选择客户',
				done: function(html){
					$('#addEditCustomer').html(html);
				}
			});
		}
		function getPackPeople(){
			myutil.getSelectHtml({
				url:'/system/user/findUserList?foreigns=0&isAdmin=false&orgNameIds=55&quit=0',
				value: 'id',
				title: 'userName',
				tips: '请选择贴包人',
				done: function(data){
					$('#addEditPackPeople').html(data);
				}
			})
		}
		function getReloadTemp(tid,deleteId){
			var tempData = [], tableData = [];
			var temp = table.getTemp(tid).data;
			for(var i=0;i<temp.length;i++){		//此处需要进行深拷贝
				tempData.push(temp[i]);
			}
			layui.each(table.cache[tid],function(index,item){
				console.log(1)
				var i=0;
				for(i=0;i<deleteId.length;i++)
					if(item.id == deleteId[i])
						break;
				!(i<deleteId.length) && (tableData.push(item))
			})
			table.reload(tid,{ data: tableData });
			layui.each(tempData,function(index,item){
				addTempData(tid,item);
			})
		}
	}//end define function
)//endedefine
</script>
</html>