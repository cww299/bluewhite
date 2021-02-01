<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>申请列表</title>
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
	mytable : 'layui/myModules/mytable',
}).define(
	['mytable'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/ledger/dispatch/applyVoucherPage',
			curd:{
				btn:[],
				otherBtn:function(obj){
					if(obj.event=='askfor'){
						myutil.deleTableIds({
							url:'/ledger/dispatch/passApplyVoucher',
							table:'tableData',
							text:'请选择信息|是否确认通过申请？',
						})
					}else if(obj.event=='cancel'){
						myutil.deleTableIds({
							url:'/ledger/dispatch/cancelApplyVoucher',
							table:'tableData',
							text:'请选择信息|是否确认取消通过申请？',
						})
					}
				},
			},
			toolbar:[
				'<span class="layui-btn layui-btn-sm" lay-event="askfor">确认申请</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cancel">取消确认</span>',
			].join(' '),
			autoUpdate:{
				//deleUrl:'/ledger/dispatch/deleteApplyVoucher',
				field:{ },
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'申请编号',   field:'applyNumber',	},
			       { title:'申请人',   field:'user_userName',   },
			       { title:'被申请人',   field:'approvalUser_userName',	},
			       { title:'申请数量',   field:'number', 	},
			       { title:'申请时间',   field:'time',	},
			       { title:'原因',   field:'cause',	},
			       { title:'通过时间',   field:'passTime',	},
			       ]]
		})
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>