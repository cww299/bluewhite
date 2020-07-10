<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>扫码发货</title>
	<style type="text/css">
	.myTable{
		text-align: center;
		width: 100%;
	}
	.myTable td{
		border: 1px solid #33333378;
	}
	.myTable .title{
		background: #e2e2e2;
	}
	#content{
		padding-top: 20px;
	}
	.layui-card-body{
	   padding: 10px 5px;
	}
	.sendTable{
		width: 100%;
	}
	.sendTable td{
		text-align: center;
		padding: 6px;
	}
	</style>
</head>
<body>
<div class="layui-card" >
	<div class="layui-card-body">
		<div id="content"></div>
		<table class="sendTable layui-form">
			<tr>
				<td colspan="2">
					<select id="logisticsSelect" name="logisticsId"><option value="">请选择物流点</option></select>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<select id="outerPackagingSelect" name="outerPackagingId"><option value="">请选择包装方式</option></select>
				</td>
			</tr>
			<tr>
				<td>
				   <input type="text" class="layui-input" id="sendTimeInput" name="time" readonly></td>
				<td>
				   <input type="text" class="layui-input" name="no" placeholder="上车编号" >
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<span class="layui-btn" lay-submit lay-filter="sendOrderBtn">一键发货</span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<br/><br/><br/>
					<a href="http://sao315.com/w/api/saoyisao" class="layui-btn layui-btn-normal" style="width: 80%;">返回扫一扫</a>
				</td>
			</tr>
		</table>
	</div> 
</div>
<script type="text/html" id="printPackTpl">
<div >
	<table class="myTable">
		<tr>
       	 	<td class="title">收货人名</td>
        	<td class="title">当批外包编号</td>
			<td class="title">收货人电话</td>
	    </tr>
		<tr>
			<td>{{ d.customer?d.customer.name:'---' }}</td>
	        <td>{{ d.quantitativeNumber || '---' }}</td>
			<td></td>
	    </tr>
		<tr>
			<td class="title">批次号</td>
	        <td class="title">产品名</td>
	        <td class="title">当件内装数量</td>
	    </tr>
		{{# layui.each(d.quantitativeChilds,function(index,item){  }}
		<tr>
			<td style="padding:15px 0;">{{ item.underGoods.bacthNumber}}</td>
	        <td>{{ item.underGoods.product.name}}</td>
	        <td>{{ item.singleNumber}}</td>
	    </tr>
	    {{# }) }}
	</table>
</div>
<hr>
</script>

<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/',
	version:true,
}).extend({
	mytable : 'layui/myModules/mytable',
}).use(['jquery','mytable','form','laytpl','laydate'],function(){
	var $ = layui.jquery
		, layer = layui.layer
		, form = layui.form	
		, table = layui.table
		, laydate = layui.laydate 
		, menuTree = layui.menuTree 
		, myutil = layui.myutil
		, mytable = layui.mytable
		, laytpl = layui.laytpl;			//模板引擎
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		
		
		// https://bbs.csdn.net/topics/392405022?page=1
		// http://localhost:8080/bluewhite/twoDimensionalCode/scanSendOrder?id=25464
		var qr=GetQueryString("qrresult");
		if(qr){
		    // alert(qr);
		}
		function GetQueryString(name)
		{
		    var reg = new RegExp("\\b"+ name +"=([^&]*)");
		    var r = location.href.match(reg);
		    if (r!=null) return unescape(r[1]);
		}
		
		var data = JSON.parse('${data}');
		$('input[name="no"]').val(data.vehicleNumber);
		if (location.href.indexOf("qrresult=")>-1)
		    alert(location.href.split("qrresult=")[1]); //在您的程序中可对此数据进行处理
		laytpl($('#printPackTpl').html()).render(data,function(h){ $('#content').html(h) });
		if(data.flag){
			$('span[lay-filter="sendOrderBtn"]').html("已发货");
			$('span[lay-filter="sendOrderBtn"]').addClass("layui-btn-danger");
		}
		$('#logisticsSelect').append(myutil.getBaseDataSelect({ type:'logistics', }));
		$('#outerPackagingSelect').append(myutil.getBaseDataSelect({ type:'outerPackaging', }));
		laydate.render({ elem:'#sendTimeInput',format:'yyyyMMdd',value:new Date(), });
		
		form.on('submit(sendOrderBtn)',function(obj){
			if($('span[lay-filter="sendOrderBtn"]').hasClass("layui-btn-danger")){
				return;
			}
			var f = obj.field;
			if(!f.no || !f.time){
				return layer.msg('请正确填写发货数据',{ time:500,icon:2 })
			}
			if(f.no.indexOf('-')>-1){
				var last = f.no.split('-')[1];
				f.no = PrefixInteger(f.no.split('-')[0].trim(),4);
				f.no += ('-'+last.trim());
			}else
				f.no = PrefixInteger(f.no.trim(),4);
			var vn = f.time+ f.no;
			var lid = f.logisticsId;
			myutil.deleteAjax({
				url:'/temporaryPack/sendQuantitative?flag=1&vehicleNumber='+vn
						+'&logisticsId='+lid+'&outerPackagingId='+f.outerPackagingId,
				ids: data.id,
				success:function(){
					$('span[lay-filter="sendOrderBtn"]').html("已发货");
					$('span[lay-filter="sendOrderBtn"]').addClass("layui-btn-danger");
				}
			})
			function PrefixInteger(num, length) {
			 return ( "0000000000000000" + num ).substr( -length );
			}
		})
		form.render();
})
</script>
</body>
</html>