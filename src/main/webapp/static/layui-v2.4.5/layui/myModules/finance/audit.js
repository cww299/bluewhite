/**2020/1/4  author:299  财务审核模板
 * audit
 * type:{
 *   	1.报销
 *   	2.采购应付财务审核
 *   	3.工资管理财务审核
 *      4.税点报销财务审核
 *      5.物流管理财务审核
 *      6.借款本金财务审核
 *      7.社保税收财务审核
 *      8.材料管理财务审核
 *      9.周转资金财务审核
 *      10.借款利息财务审核
 *      11.外发对账财务审核
 * }
 */
layui.extend({
}).define(['jquery','layer','form','laytpl','laydate','mytable','table'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		laytpl = layui.laytpl,
		table = layui.table,
		mytable = layui.mytable,
		myutil = layui.myutil;
	var firstCols =  { type: 'checkbox', fixed: 'left' };
	var allCols = [
		[],
		[],
		[  //type:2 采购应付
		   firstCols,
	       { field: "content", title: "采购单编号",width:'25%',fixed: 'left'},
	       { field: "orderProcurement_order_bacthNumber", 	title: "批次号", 	 width:'10%',},
	       { field: "customer_name", 	title: "客户",   	width:'8%', }, 
	       { field: "user_userName",title: "订料人",  width:'8%',	},
	       { field: "orderProcurement_gramPrice", 	title: "缺克重价值",  }, 
	       { field: "orderProcurement_interest", 	title: "占用资金利息",  }, 
	       { field: "orderProcurement_materiel_materialQualitative_name", 	title: "货物类别",  }, 
	       { field: "orderProcurement_price", 		title: "单价", 			}, 
	       { field: "money", 		title: "金额", 			}, 
	       { field: "orderProcurement_arrivalTime",title: "到货时间",	type:'date', },
	       { field: "expenseDate", 	title: "预计付款时间", 	type:'date',	 },
		],
		[	//type:3 工资管理 wages
			firstCols,
			{ field: "content", title: "工资内容", }, 
			{ field: "money", title: "工资申请金额", }, 
			{ field: "expenseDate", title: "工资申请日期", }, 
			{ field: "withholdReason", title: "扣款事由", }, 
			{ field: "withholdMoney", title: "扣款金额", }, 
		],
		[	//type:4 税点报销 tax
			firstCols,
			{ field: "customer_name", title: "供应商名称", }, 
			{ field: "money", title: "票面金额", },
			{ field: "taxPoint", title: "税点", },
			{ field: "expenseDate", title: "申请日期", }, 
			{ field: "withholdReason", title: "扣款事由", }, 
			{ field: "withholdMoney", title: "扣款金额", }, 
		],
		[	// type:5 物流管理  logistics
			firstCols,
			{ field: "logisticsDate", title: "物流订单日期", }, 
			{ field: "contact_conPartyNames", title: "客户名称", }, 
			{ field: "custom_name", title: "物流点名称", }, 
			{ field: "money", title: "支付金额", }, 
			{ field: "expenseDate", title: "预计付款日期", }, 
		],
		[	//type:6  借款本金    loan
			firstCols,
		  	{ field: "content", title: "借款方", },
		  	{ field: "remark", title: "借款类型", },
		  	{ field: "money", title: "支付金额", }, 
		  	{ field: "expenseDate", title: "预计付款日期", },
		],
		[	//type:7 社保税收    social
			firstCols,
			{ field: "customer_name", title: "扣税单位", },
			{ field: "content", title: "税种", },
			{ field: "money", title: "金额", },
			{ field: "expenseDate", title: "预计付款日期", type:'date',},
		],
		[	//type:8 材料管理财务审核 material
			firstCols,
			{ field: "customer_name", title: "供应商", },
			{ field: "content", title: "内容", }, 
			{ field: "money", title: "预付金额", },
			{ field: "expenseDate", title: "预计入库日期", }, 
			{ field: "withholdReason", title: "扣款事由", }, 
			{ field: "withholdMoney", title: "扣款金额", }, 
		],
		[	//type:9  周转资金   turnover
			firstCols,
			{ field: "content", title: "申请内容", }, 
			{ field: "user_userName", title: "申请人", }, 
			{ field: "money", title: "金额", }, 
			{ field: "expenseDate", title: "回款日期", }, 
			{ field: "withholdReason", title: "扣款事由", }, 
			{ field: "withholdMoney", title: "扣款金额", }, 
		],
		[	//type:10  利息 loanInterest
			firstCols,
	        { field: "content", 	title: "借款方",},
	        { field: "remark", 		title: "借款类型",},
	        { field: "money", 			title: "支付金额", }, 
	        { field: "expenseDate", 	title: "预计付款日期", },
		],
		[	//type:11  外发对账 
			firstCols,
			{ title:'申请日期', field:'expenseDate', type:'dateTime',},
			{ title:'外发单编号', field:'orderOutSource_outSourceNumber', },
			{ title:'生产单编号', field:'orderOutSource_materialRequisition_order_orderNumber', },
			{ title:'供应商', field:'customer_name', },
			{ title:'金额', field:'money', },
			{ title:'备注', field:'remark', },
	        { field: "expenseDate", 	title: "预计付款时间", 	type:'date',	 },
	    ],
	    
	];
	var lastCols = [
	   { field: "paymentDate", 	title: "实际付款时间", style:'background-color: #d8fe83',edit:true,type:'date',fixed:'right', }, 
       { field: "paymentMoney",	title: "付款金额",    style:'background-color: #d8fe83', edit:'number', fixed:'right',},
       { field: "flag", 	    title: "审核状态", 	 transData:{data:['未审核','审核'],}, fixed:'right',}
	];
	
	var audit = {
		type: 1,
	};
	
	audit.render = function(opt){
		opt = opt || {};
		var TPL = `
			<table class="layui-form searchTable">
				<tr>
					<td style="width:130px;"><select class="layui-input" id="selectone">
							<option value="expenseDate">申请日期</option>
							<option value="paymentDate">实际付款日期</option></select></td>
					<td><input id="searchTime" name="orderTimeBegin"  class="layui-input" autocomplete="off"></td>
					`+
					(function(){
						var text = '', name = '';
						switch(audit.type){
						case 2: text='采购单编号'; name='content'; break;
						case 3: text='工资内容'; name='content'; break;
						case 4: text='供应商名称'; name='customerName'; break;
						case 5: text='物流点名称'; name='customerName'; break;
						case 6: text='借款方'; name='customerName'; break;
						case 7: text='扣税单位'; name='customerName'; break;
						case 8: text='公司名称'; name='customerName'; break;
						case 9: text='申请内容'; name='content'; break;
						case 10: text='借款方'; name='customerName'; break;
						case 11: text='加工单编号'; name='content'; break;
						}
						return '<td>'+text+'：</td>'+
							   '<td><input type="text" name="'+name+'" class="layui-input" /></td>';
					})()
					+
					`
					<td>是否审核：</td>
					<td style="width:100px;">
						<select name="flag">
							<option value="">请选择</option>
							<option value="0" selected>未审核</option>
							<option value="1">已审核</option></select></td>
					<td><span class="layui-btn" lay-submit lay-filter="searchBtn">
							<i class="layui-icon layui-icon-search">搜索</i></span></td>
				</tr>
			</table>
			<table id="tableData" lay-filter="tableData"></table>
		`;
		
		$(opt.elem || '#app').html(TPL);
		form.render();
		laydate.render({ elem:'#searchTime', range:'~', })
		mytable.render({
			elem: '#tableData',
			url: myutil.config.ctx+'/fince/getConsumption?type='+audit.type ,
			where:{ flag:0 },
			ifNull:'',
			scrollX:true,
			autoUpdate:{
				saveUrl:'/fince/addConsumption',
			},
			verify:{
				price:['paymentMoney'],
			},
			toolbar: [
					'<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="audit">审核</span>',
					'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="noAudit">取消审核</span>',
				].join(''),
			curd:{
				btn:[],
				otherBtn:function(obj){
					switch(obj.event) {
					case 'audit':
						myutil.deleTableIds({
							url:'/fince/auditConsumption?flag=1',
							table:'tableData',
							type:'post',
							text:'请选择相关信息进行审核|是否确认审核？',
						})
						break;
					case 'noAudit':
						myutil.deleTableIds({
							url:'/fince/auditConsumption?flag=0',
							table:'tableData',
							type:'post',
							text:'请选择相关信息进行审核|是否确认取消审核？',
						})
						break;
					}
				}
			},
			cellMinWidth:120,
			cols: [ allCols[audit.type].concat(lastCols) ],
		});
		
		form.on('submit(searchBtn)', function(obj) {
			var f = obj.field;
			var timeType = $('#selectone').val();
			if(f.orderTimeBegin){
				var time = f.orderTimeBegin.split(' ~ ');
				f.orderTimeBegin = time[0]+' 00:00:00';
				f.orderTimeEnd = time[1]+' 23:59:59';
			}else{
				f.orderTimeEnd = '';
			}
			if(timeType=='expenseDate'){
				f.expenseDate = '2019-11-20 17:18:34';
				f.paymentDate = '';
			}else{
				f.expenseDate = '';
				f.paymentDate = '2019-11-20 17:18:34';
			}
			table.reload('tableData', {
				where: f
			});
		});
		form.render();
	}
	exports('audit',audit);
})