/**2019/12/24
 * 申请列表模块
 * askforlist
 */
layui.extend({
}).define(['jquery','layer','form','laytpl','laydate','mytable','table'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		laytpl = layui.laytpl,
		mytable = layui.mytable,
		table = layui.table,
		myutil = layui.myutil;
	var tableId = 'askforListTable';
	var TPL_MAIN = `
		<table class="layui-form">
			<tr>
				<td>产品名:</td>
				<td><input type="text" name="productName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="`+tableId+`" lay-filter="`+tableId+`"></table>
	`;
	var askforlist = {
		type: 0,	//1.成品  2.皮壳
	};
	askforlist.render = function(opt){
		$(opt.elem || '#app').append(TPL_MAIN);
		mytable.render({
			elem:'#'+tableId,
			url: myutil.config.ctx+'/ledger/dispatch/applyVoucherPage',
			curd:{
				btn:[],
				otherBtn:function(obj){
					if(obj.event=='askfor'){
						myutil.deleTableIds({
							url:'/ledger/dispatch/passApplyVoucher',
							table: tableId,
							text:'请选择信息|是否确认通过申请？',
						})
					}else if(obj.event=='cancel'){
						myutil.deleTableIds({
							url:'/ledger/dispatch/cancelApplyVoucher',
							table: tableId,
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
			table.reload(tableId,{
				where: obj.field,
			})
		}) 
	}
	exports('askforlist',askforlist);
})