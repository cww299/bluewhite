<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单列表</title>
</head>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
.layui-form-item .layui-input-inline{
	width:100px;
	margin-left: 5px;
	margin-right:0px;
}
.layui-input, .layui-select, .layui-textarea {
    height: 28px;
}
.layui-form-label {
    padding: 5px 5px;
    width: 10px;
}
#positionChoose{		/* 客户所在地选择隐藏框样式*/
    height: 300px;
    width: 200px;
    padding:20px;
    z-index: 99;
    border:1px solid #d2d2d2;
    position: absolute;
    left: 430px;
    top: 105px;
    display:none;
    background-color:#f2f2f2;
}
</style>
<body>
<div class="layui-card">
	<div class="layui-card-body " style="height:850px;">
			<div class="layui-form">
				<div class="layui-form-item">
					<div class="layui-input-inline"><select lay-search name="status"><option value="">交易状态</option></select></div>
					<div class="layui-input-inline"><select lay-search name=""><option value="">打印状态</option></select></div>
					<div class="layui-input-inline"><select lay-search><option value="">出库状态</option></select></div>
					<div class="layui-input-inline"><select lay-search><option value="">评价状态</option></select></div>
					<label class="layui-form-label" style="width:30px;">日期</label>
					<div class="layui-input-inline"><input type="text" class="layui-input"></div>
					<div class="layui-input-inline"><select lay-search><option value="">买家id</option></select></div>
					<div class="layui-input-inline"><input type="text" class="layui-input"></div>
					<div class="layui-input-inline"><button class="layui-btn layui-btn-sm" lay-submit>搜索</button></div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-inline"><select><option value="">正常发货</option></select></div>
					<div class="layui-input-inline"><select><option value="">成交时间</option></select></div>
					<label class="layui-form-label">从</label>
					<div class="layui-input-inline" style="width:80px;"><select lay-search id="sinceHour"><option value="">00</option></select></div>
					<label class="layui-form-label">时</label>
					<div class="layui-input-inline" style="width:80px;"><select lay-search id="sinceMin"><option value="">00</option></select></div>
					<label class="layui-form-label">分</label>
					<label class="layui-form-label">到</label>
					<div class="layui-input-inline" style="width:80px;"><select lay-search id="forHour"><option value="">23</option></select></div>
					<label class="layui-form-label">时</label>
					<div class="layui-input-inline" style="width:80px;"><select lay-search id="forMin"><option value="">59</option></select></div>
					<label class="layui-form-label">分</label>
					<label class="layui-form-label"  style="width:30px;">排序</label>
					<div class="layui-input-inline"><select><option value="">成交时间</option></select></div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-inline"><select lay-search><option value="">订单来源</option></select></div>
					<div class="layui-input-inline"><select lay-search><option value="">审核状态</option></select></div>
					<div class="layui-input-inline"><select lay-search><option value="">物流公司</option></select></div>
					<div class="layui-input-inline"><select lay-search><option value="">物流方式</option></select></div>
					<div class="layui-input-inline"><a href="#" style="color:blue;" id="customPosition">收货人所在省份</a></div>
					
					<label class="layui-form-label" style=" width:60px;">订单金额:</label>
					<div class="layui-input-inline"  style="width:60px;"><input class="layui-input" value="0"></div>
					<label class="layui-form-label">至</label>
					<div class="layui-input-inline"  style="width:60px;"><input class="layui-input" value="0"></div>
					<label class="layui-form-label" style=" width:60px;">宝贝件数:</label>
					<div class="layui-input-inline"  style="width:60px;"><input class="layui-input" value="0"></div>
					<label class="layui-form-label">至</label>
					<div class="layui-input-inline"  style="width:60px;"><input class="layui-input" value="0"></div>
					<div class="layui-input-inline"><select><option value="">订单数</option></select></div>
				</div>
			</div>
		<table class="laui-table" id="onlineOrder" filter="onlineOrder" ></table>
	</div>
</div>

<!-- 位置选择隐藏框 -->
<div id="positionChoose">
	<input type="checkbox">广东
	<input type="checkbox">江苏
</div>	
		
</body>


<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).define(
	['tablePlug'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 				
		, tablePlug = layui.tablePlug;
		
		form.render();
		form.on('submit',function(obj){
			console.log(obj.field)
		})
		
		searchToolInit();
		
		$("#customPosition").hover(function() {
			$('#positionChoose').show();
			form.render();
		});
		$("#positionChoose").mouseleave (function () {	//mouseout进入复选框时会发生冒泡事件，引起out事件触发，因此需要使用mouseleave代替
			$('#positionChoose').hide();	
	    });

		
		
		
		table.render({
			elem:'#onlineOrder',
			height:'780',
			//url:'',
			loading:true,
			page:true,
			request:{
				pageName: 'page' ,		
				limitName: 'size' 		
			},
			parseData:function(ret){
				return{
					code:ret.code,
					msg:ret.message,
					data:ret.data,
					count:ret.total
					/* count : ret.data.total, 
					data : ret.data.rows, */
				}
			},
			cols:[[
			       {type:'checkbox',align:'center',fixed:'left'},
			      // {filed:'',            title:'下单时间',   align:'center'},
			       {filed:'number',        title:'订单号',     align:'center'},
			      // {filed:'',            title:'客户名称',   align:'center'},
			       {filed:'buyerRemarks',  title:'买家留言',   align:'center'},
			       {filed:'sellerRemarks', title:'卖家备注',   align:'center'},
			       {filed:'franking',      title:'邮费',       align:'center'},
			       {filed:'amountMoney',   title:'实收金额',   align:'center'},
			       {filed:'countable',     title:'件数',       align:'center'},
			       {filed:'trackingNumber',title:'运单号',     align:'center'},
			       {filed:'status',        title:'状态',       align:'center'},
			    //   {filed:'',            title:'收货地址',   align:'center'},
			       ]]
		}) 
		
		
		
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
		
		
		function searchToolInit(){
			console.log(1)
			var sinceHourHtml='';
			for(var i=0;i<24;i++){
				sinceHourHtml+='<option value="'+i+'">'+i+'</option>';
			}
			$('#sinceHour').html(sinceHourHtml);
			var sinceMinHtml='';
			for(var i=0;i<60;i++){
				sinceMinHtml+='<option value="'+i+'">'+i+'</option>';
			}
			$('#sinceMin').html(sinceMinHtml);
			
			var forHourHtml='';
			for(var i=0;i<24;i++){
				forHourHtml+='<option value="'+i+'">'+i+'</option>';
			}
			$('#forHour').html(forHourHtml);
			var forMinHtml='';
			for(var i=0;i<60;i++){
				forMinHtml+='<option value="'+i+'">'+i+'</option>';
			}
			$('#forMin').html(forMinHtml);
		}
		
	}//define function
)
</script>
</html>