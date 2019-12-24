<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>我的申请</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
				<td style="width:100px;">
						<select id="timeType"><option value="askTime">申请时间</option>
								  <option value="passTime">通过时间</option></select></td>
				<td><input type="text" name="orderTimeBegin" id="orderTimeBegin" class="layui-input"></td>
				<td>申请编号：</td>
				<td><input type="text" name="applyNumber" class="layui-input"></td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filt er="tableData"></table>
	</div>
</div>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
	askfor : 'layui/myModules/askfor/askfor' ,
}).define(
	['mytable','laydate','askfor'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laydate = layui.laydate
		, laytpl = layui.laytpl
		, askfor = layui.askfor
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		
		laydate.render({ elem: '#orderTimeBegin',range:'~', });
		form.render();
		mytable.render({
			elem:'#tableData',
			url: myutil.config.ctx+'/ledger/dispatch/applyVoucherPage',
			curd:{
				btn:[],
				otherBtn:function(obj){
					if(obj.event=='askfor'){
						myutil.deleTableIds({
							url:'/ledger/dispatch/deleteApplyVoucher',
							table:'tableData',
							text:'请选择信息|是否确认撤销申请？',
						})
					}else if(obj.event=="edit"){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length!=1)
							return myutil.emsg('请选择一条数据进行修改');
						askfor.update({
							data: check[0],
							productId:1,
						})
					}
				},
			},
			toolbar:[
				'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="askfor">撤销申请</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="add">新增申请</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="edit">修改申请</span>',
			].join(' '),
			autoUpdate:{
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'申请编号',   field:'applyNumber',	},
			       { title:'被申请人',   field:'approvalUser_userName',	},
			       { title:'申请数量',   field:'number', 	},
			       { title:'申请时间',   field:'time',	},
			       { title:'原因',   field:'cause',	},
			       { title:'申请类型',   field:'applyVoucherType_name',	},
			       { title:'申请种类',   field:'applyVoucherKind_name',	},
			       { title:'是否通过',   field:'pass',	},
			       ]]
		})
		
		form.on('submit(search)',function(obj){
			var f = obj.field;
			if(f.orderTimeBegin){
				var type = $('#timeType').val();
				if(type=='askTime'){
					
				}else{
					
				}
				var time = f.orderTimeBegin.split(' ~ ');
				f.orderTimeBegin = time[0]+' 00:00:00';
				f.orderTimeEnd = time[1]+' 23:59:59';
			}else{
				f.orderTimeEnd = '';
			}
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>