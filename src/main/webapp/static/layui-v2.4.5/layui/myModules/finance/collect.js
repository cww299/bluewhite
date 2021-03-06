/**2020/1/10  author:299  财务汇总模板
 * collect
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
		[	//type:1  报销审核  expenseAccount
			firstCols,
			{ field: "content", title: "报销内容", }, 
			{ field: "user_userName", title: "报销人", width:120,}, 
			{ field: "money", title: "报销申请金额", width:140,sort:true,}, 
			{ field: "expenseDate", title: "报销申请日期", type:'date',width:120, }, 
			{ field: "withholdReason", title: "扣款事由", width:150,}, 
			{ field: "withholdMoney", title: "扣款金额", width:120,}, 
			{ field: "settleAccountsMode", title: "结款模式", transData:{ data:["","现金","月结"], },width:120, }, 
			{ field: "orgName_name",  title: "申请部门", width:150,}, 
		],
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
	       { field: "expenseDate", 	title: "付款日期", 	type:'date',	 },
	       { field: "orderProcurement_arrivalTime",title: "到货时间",	type:'date', },
	       { field: "orderProcurement_expectPaymentTime", 	title: "预计付款时间", 	type:'date' }, 
	       { field: "paymentDate", 	title: "实际付款时间", 	type:'date',fixed:'right', }, 
	       { field: "paymentMoney",	title: "付款金额", 		 fixed:'right',},
		],
		[	//type:3 工资管理 wages
			firstCols,
			{ field: "content", title: "工资内容", },
			{ field: "money", title: "工资申请金额", },
			{ field: "expenseDate", title: "工资申请日期", },
			{ field: "paymentDate", title: "实际付款日期", },
			{ field: "withholdReason", title: "扣款事由", },
			{ field: "withholdMoney", title: "扣款金额", },
		],
		[	//type:4 税点报销 tax
			firstCols,
			{ field: "customer_name", title: "供应商名称", },
			{ field: "taxPoint", title: "票面金额", },
			{ field: "money", title: "税点", },
			{ field: "expenseDate", title: "申请日期", }, 
			{ field: "withholdReason", title: "扣款事由", }, 
			{ field: "withholdMoney", title: "扣款金额", }, 
		],
		[	// type:5 物流管理  logistics
			firstCols,
			{ field: "logistics_name", title: "物流点名称", },
			{ field: "money", title: "支付金额", },
			{ field: "expenseDate", title: "申请日期", type:'dateTime',}, 
			{ field: "expectDate", title: "预计付款日期",type:'dateTime',  }, 
			{ field: "paymentDate", title: "实际日期", type:'dateTime',}, 
			{ field: "paymentMoney", title: "已付款金额", },
		],
		[	//type:6  借款本金    loan
			firstCols,
			{ field: "content", title: "借款方", },
			{ field: "remark", title: "借款类型", },
			{ field: "money", title: "报销申请金额", },
			{ field: "expenseDate", title: "报销申请日期", },
			{ field: "paymentDate", title: "实际付款日期", },
		],
		[	//type:7 社保税收    social
			firstCols,
			{ field: "customer_name", title: "扣税单位", },
			{ field: "content", title: "税种", },
			{ field: "money", title: "金额", },
			{ field: "expenseDate", title: "预计付款日期", type:'date',},
			{ field: "paymentDate", title: "实际付款日期", type:'date',},
		],
		[	//type:8 材料管理财务审核 material
			firstCols,
			{ field: "customer_name", title: "供应商", },
			{ field: "content", title: "内容", }, 
			{ field: "money", title: "预付金额", },
			{ field: "expenseDate", title: "预计付款日期", },
			{ field: "paymentDate", title: "实际付款日期", },
			{ field: "withholdReason", title: "扣款事由", }, 
			{ field: "withholdMoney", title: "扣款金额", }, 
		],
		[	//type:9  周转资金   turnover
			firstCols,
			{ field: "user_userName", title: "申请人", }, 
			{ field: "money", title: "金额", }, 
			{ field: "expenseDate", title: "回款日期", }, 
			{ field: "withholdReason", title: "扣款事由", }, 
			{ field: "withholdMoney", title: "扣款金额", }, 
		],
		[	//type:10  利息 loanInterest
			firstCols,
			{ field: "content", 	title: "借款方",},
	        { field: "remark", 		title: "借款类型", },
		    { field: "money", 		title: "报销申请金额", },
		    { field: "expenseDate", title: "报销申请日期", },
		    { field: "paymentDate", title: "实际付款日期",},
		],
		[	//type:11  外发对账 
			firstCols,
			{ title:'申请日期', field:'expenseDate', type:'dateTime',},
			{ title:'外发单编号', field:'orderOutSource_outSourceNumber', },
			{ title:'生产单编号', field:'orderOutSource_materialRequisition_order_orderNumber', },
			{ title:'供应商', field:'customer_name', },
			{ title:'金额', field:'money', },
			{ title:'备注', field:'remark', },
	    ],
	    
	];
	var lastCols = [
       { field: "flag", title: "审核状态", 	 transData:{data:['未审核','审核','部分审核'],}, fixed:'right',width:120, }
	];
	
	var collect = {
		type: 1,
	};
	
	collect.render = function(opt){
		opt = opt || {};
		var TPL = 
			(function(){
				if(collect.type==5)
					return '<span class="layui-btn" id="customerCollect" style="float:right;">客户汇总</span>';
				return '';
			})()+`
			<table class="layui-form searchTable">
				<tr>
					<td style="width:130px;"><select class="layui-input" id="selectone">
							<option value="expenseDate">申请日期</option>
							<option value="paymentDate">实际付款日期</option>
							`+
							(function(){
								if(collect.type==5)
									return '<option value="expectDate">预计付款日期</option>';
								return "";
							})()
							+`</select></td>
					<td><input id="searchTime" name="orderTimeBegin"  class="layui-input" autocomplete="off"></td>
					`+
					(function(){
						var otherHtml = '';
						var text = '', name = '';
						switch(collect.type){
						case 1: text='内容'; name='content'; 
								otherHtml = '<td>报销人：</td>'+
								   			'<td style="width: 100px;"><input type="text" name="Username" class="layui-input" /></td>'+
								   			'<td>金额：</td>'+
								   			'<td style="width: 100px;"><input type="number" name="money" class="layui-input" /></td>'+
								   			'<td>是否预算：</td>'+
								   			'<td style="width:100px;"><select name="budget">'+
								   				'<option value="">请选择</option>'+
												'<option value="0">否</option>'+
												'<option value="1">是</option>'+
								   			'</select></td>';
								break;
						case 2: text='采购单编号'; name='content'; break;
						case 3: text='工资内容'; name='content'; break;
						case 4: text='供应商名称'; name='customerName'; break;
						case 5: text='物流点名称'; name='logisticsName'; break;
						case 6: text='借款内容'; name='content'; break;
						case 7: text='扣税单位'; name='customerName'; break;
						case 8: text='公司名称'; name='customerName'; break;
						case 9: text='申请内容'; name='content'; break;
						case 10: text='借款方'; name='customerName'; break;
						case 11: text='加工单编号'; name='content'; break;
						}
						return '<td>'+text+'：</td>'+
							   '<td><input type="text" name="'+name+'" class="layui-input" /></td>'+otherHtml;
					})()
					+
					`
					<td>是否审核：</td>
					<td style="width:100px;">
						<select name="flags">
							<option value="">请选择</option>
							<option value="0" selected>未审核</option>
							<option value="1">已审核</option>
							<option value="2">部分审核</option></select></td>
					<td><span class="layui-btn" lay-submit lay-filter="searchBtn">
							<i class="layui-icon layui-icon-search">搜索</i></span></td>
					<td style="width: 30px;"></td>
					<td style="font-size: 20px;">未支付总额:</td>
					<td id="allPrice" style="color:red;font-size: 20px;">0</td>
				</tr>
			</table>
			<table id="tableData" lay-filter="tableData"></table>
		`;
		
		$(opt.elem || '#app').html(TPL);
		form.render();
		myutil.clickTr();
		laydate.render({ elem:'#searchTime', range:'~', });
		var where = { flags: '0,2', };
		if(collect.type==5)
			where.mode = 2;
		var url = '/fince/getConsumption';
		mytable.render({
			elem: '#tableData',
			url: myutil.config.ctx+url+'?type='+collect.type,
			where: where,
			ifNull:'',
			scrollX:true,
			autoUpdate:{
				saveUrl:'/fince/addConsumption',
			},
			verify:{
				price:['paymentMoney'],
			},
			toolbar: [
				(function(){
					if(collect.type==5)
						return "";
					return 
						'<span class="layui-btn layui-btn-sm layui-btn-success" lay-event="audit">审核</span>'+
						'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="noAudit">取消审核</span>';
				})(),
			].join(''),
			limit:15,
			limits:[15,20,50,100,200,],
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
			cols: [ allCols[collect.type].concat(lastCols) ],
		});
		myutil.getData({
			url: myutil.config.ctx+'/fince/totalAmount?flags=0,2&type='+collect.type,
			success:function(d){
				$('#allPrice').html(d);
			}
		})
		$('#customerCollect').click(function(){
			layer.open({
				type: 1,
				title: '客户汇总',
				area: ['1200px','600px'],
				shadeClose: true,
				content: [
					'<div style="padding:10px;">',
						'<table class="searchTable layui-form">',
							'<tr>',
								'<td>业务员：</td>',
								'<td><input class="layui-input" name="saleUserName"></td>',
								'<td>部门：</td>',
								'<td><select name="orgNameId" id="orgNameId" lay-search>',
									'<option value="">请选择部门</option></select></td>',
								'<td>客户名：</td>',
								'<td><input class="layui-input" name="customerName"></td>',
								'<td>申请时间：</td>',
								'<td><input class="layui-input" name="orderTimeBegin" id="expenseDates" autocomplete="off"></td>',
								'<td><span class="layui-btn" lay-submit lay-filter="searchCustomer">搜索</span></td>',
							'</tr>',
						'</table>',
						'<table id="customerCollectTable" lay-filter="customerCollectTable"></table>',
					'</div>',
				].join(' '),
				success:function(){
					$('#orgNameId').append(myutil.getBaseDataSelect({ type:'orgName', }))
					laydate.render({ elem:'#expenseDates',range:'~' })
					form.render();
					mytable.renderNoPage({
						elem: '#customerCollectTable',
						toolbar:' ',
						url: myutil.config.ctx+'/fince/logisticsConsumption',
						cols: [[
							{ field:'name', title:'客户名', },
							{ field:'pay', title:'总费用', },
							{ field:'orgName', title:'部门', },
							{ field:'username', title:'业务员', },
						]],
						totalRow:['pay'],
					})
					form.on('submit(searchCustomer)',function(obj){
						var f = obj.field;
						if(f.orderTimeBegin){
							var time = f.orderTimeBegin.split(' ~ ');
							f.orderTimeBegin = time[0]+' 00:00:00';
							f.orderTimeEnd = time[1]+' 23:59:59';
						}else{
							f.orderTimeEnd = '';
						}
						f.expenseDate = "2020-05-28 16:33:00";
						table.reload('customerCollectTable',{
							where: f,
						})
					})
				}
			})
		})
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
			f.expenseDate = '';
			f.paymentDate = '';
			f.expectDate = '';
			f[timeType] = '2020-05-26 16:40:20';
			table.reload('tableData', {
				where: f
			});
			myutil.getData({
				url: myutil.config.ctx+'/fince/totalAmount?type='+collect.type,
				data: f,
				success:function(d){
					$('#allPrice').html(d);
				}
			})
		});
		form.render();
	}
	exports('collect',collect);
})