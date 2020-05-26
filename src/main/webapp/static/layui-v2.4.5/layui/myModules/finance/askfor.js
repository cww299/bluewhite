/**2020/1/11  author:299  财务申请模板
 * askfor
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
}).define(['jquery','layer','form','laytpl','laydate','mytable','table','upload'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		upload = layui.upload,
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
	        { field: "orderProcurement_arrivalTime",title: "到货时间",	type:'date', },
	        { field: "expenseDate", 	title: "预计付款时间", 	type:'date',	 },
		],
		[	//type:3 工资管理 wages
			firstCols,
			{ field: "content", title: "工资内容",edit:true, }, 
			{ field: "money", title: "工资申请金额", edit:'number',}, 
			{ field: "expenseDate", title: "工资申请日期", type:'dateTime',edit:true,}, 
			{ field: "withholdReason", title: "扣款事由",edit:true, }, 
			{ field: "withholdMoney", title: "扣款金额",edit:true, }, 
		],
		[	//type:4 税点应付申请 tax
			firstCols,
			{ field: "customer_id", title: "供应商名称", type:'select',select:{data:[],},}, 
			{ field: "money", title: "票面金额", edit:'number',},
			{ field: "taxPoint", title: "税点", edit:true,},
			{ field: "expenseDate", title: "申请日期", edit:true,type:'dateTime', }, 
			{ field: "withholdReason", title: "扣款事由",edit:true, }, 
			{ field: "withholdMoney", title: "扣款金额", edit:true,}, 
		],
		[	// type:5 物流管理  logistics
			firstCols,
			{ field: "logistics_name", title: "物流点名称",  }, 
			{ field: "money", title: "支付金额",  }, 
			{ field: "expenseDate", title: "申请日期", type:'dateTime', }, 
			{ field: "expectDate", title: "预计付款日期",type:'dateTime', edit:true, }, 
			{ field: "paymentDate", title: "实际日期", type:'dateTime',}, 
		],
		[	//type:6  借款本金    loan
			firstCols,
		  	{ field: "content", title: "借款方", edit:true, },
		  	{ field: "remark", title: "借款类型", edit:true, },
		  	{ field: "money", title: "支付金额", edit:'number', }, 
		  	{ field: "expenseDate", title: "申请日期", edit:true, type:'dateTime', },
		],
		[	//type:7 社保税收    social
			firstCols,
			{ field: "customer_id", title: "扣税单位", type:'select',select:{data:[], },},
			{ field: "content", title: "税种",edit:true, },
			{ field: "money", title: "金额", edit:true, },
			{ field: "expenseDate", title: "预计付款日期", type:'dateTime',edit:true, },
		],
		[	//type:8 材料管理财务审核 material
			firstCols,
			{ field: "customer_id", title: "供应商",type:'select',select:{data:[],}, },
			{ field: "content", title: "内容", edit:true,  }, 
			{ field: "money", title: "预付金额", edit:'number', },
			{ field: "expenseDate", title: "预计入库日期",type:'dateTime',edit:true, }, 
			{ field: "withholdReason", title: "扣款事由", edit:true, }, 
			{ field: "withholdMoney", title: "扣款金额", edit:true, }, 
		],
		[	//type:9  周转资金   turnover
			firstCols,
			{ field: "content", title: "申请内容", edit:true,}, 
			{ field: "user_id", title: "申请人",type:'select',select:{data:[],name:'userName', }, }, 
			{ field: "money", title: "金额", edit:'number',}, 
			{ field: "expenseDate", title: "回款日期", type:'dateTime',edit:true,}, 
			{ field: "withholdReason", title: "扣款事由", edit:true, }, 
			{ field: "withholdMoney", title: "扣款金额", edit:true,}, 
		],
		[	//type:10  利息 loanInterest
			firstCols,
			{ field: "content", 	title: "借款方", edit:true, },
	        { field: "remark", 		title: "借款类型", edit:true,},
	        { field: "money", 			title: "支付金额", edit:'number',}, 
	        { field: "expenseDate", 	title: "预计付款日期", edit:true, type:'dateTime', },
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
       { field: "flag",title: "审核状态",transData:{data:['未审核','审核','部分审核'],}, fixed:'right',width:120, }
	];
	
	var askfor = {
		type: 1,
	};
	
	askfor.render = function(opt){
		opt = opt || {};
		var TPL = `
			<div class="layui-form">`+
			(function(){
				if(askfor.type==5)
					return [
						'<div style="float:right;">',
							'<input type="radio" name="mode" value="1" title="按条查看"  checked >',
							'<input type="radio" name="mode" value="2" title="按月查看" >',
						'</div>',
					].join(' ');
				return "";
			})()+
		`
			<table class="searchTable">
				<tr>
					<td style="width:130px;"><select class="layui-input" id="selectone">
							<option value="expenseDate">申请日期</option>
							<option value="paymentDate">实际付款日期</option>
							
							`+
							(function(){
								if(askfor.type==5)
									return '<option value="expectDate">预计付款日期</option>';
								return "";
							})()
							+`
							</select></td>
					<td><input id="searchTime" name="orderTimeBegin"  class="layui-input" autocomplete="off"></td>
					`+
					(function(){
						var otherHtml = '';
						var text = '', name = '';
						switch(askfor.type){
						case 1: text='报销内容'; name='content'; 
								otherHtml = '<td>报销人：</td>'+
								   			'<td><input type="text" name="Username" class="layui-input" /></td>'+
								   			'<td>报销金额：</td>'+
								   			'<td><input type="number" name="money" class="layui-input" /></td>';
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
							<i class="layui-icon layui-icon-search">搜索</i></span></td><td></td>
					`+
					(function(){
						if(askfor.type==3)
							return '<td><span class="layui-btn" id="importWageBtn"><i class="layui-icon layui-icon-upload"></i>导入工资</span></td>';
						return "";
					})()
					+`
				</tr>
			</table>
			</div>
			<table id="tableData" lay-filter="tableData"></table>
		`;
		$(opt.elem || '#app').html(TPL);
		myutil.clickTr();
		form.render();
		laydate.render({ elem:'#searchTime', range:'~', });
		if(askfor.type==3){	//如果是工资、增加导入按钮
			upload.render({
			    elem: '#importWageBtn',
			    url: myutil.config.ctx+'/fince/excel/addConsumption?type=3',
			    accept: 'file',
			    done: function(res){
			        if(res.code==0){
			    	    table.reload("tableData")
			    	    return myutil.smsg(res.message);
			        }else{
			    	    return myutil.emsg('导入失败');
			        }
			    }
			});
		}else if(askfor.type==4 || askfor.type==8){	//如果是税点应付申请或者材料管理、获取供应商
			var allCustomer = myutil.getDataSync({
				url:myutil.config.ctx+'/ledger/getCustomer?customerAttributionId=449&customerTypeId=456',
			});
			allCustomer.unshift({id:'',name:'请选择'});
			allCols[askfor.type][1].select.data = allCustomer;
		}else if(askfor.type==9){	//应付周转资金, 
			var allCustomer = myutil.getDataSync({
				url:myutil.config.ctx+'/system/user/findAllUser',
			});
			allCustomer.unshift({id:'',userName:'请选择'});
			allCols[askfor.type][2].select.data = allCustomer;
		}else if(askfor.type==7){	//如果是社保税收
			var allCustomer = myutil.getDataSync({
				url:myutil.config.ctx+'/ledger/getCustomer?customerAttributionId=448&customerTypeId=454',
			});
			allCustomer.unshift({id:'',name:'请选择'});
			allCols[askfor.type][1].select.data = allCustomer;
		}
		var url = '/fince/getConsumption';
		mytable.render({
			elem: '#tableData',
			url: myutil.config.ctx+url+'?type='+askfor.type,
			where:{ flags:0, mode:1,},
			ifNull:'',
			size:'lg',
			autoUpdate:{
				saveUrl:'/fince/addConsumption?type='+askfor.type,
				deleUrl:'/fince/deleteConsumption',
				field:{ customer_id:'customerId',user_id:'userId',contact_id:'contactId' },
			},
			verify:{
				price:['money','withholdMoney'],
				notNull:['content','expenseDate','money','customer_id','user_id','contact_id'],
			},
			limits:[10,20,50,100,200,],
			curd:{
				btn: askfor.type==5?[4]:[1,2,3,4],
			},
			cellMinWidth:120,
			cols: [ allCols[askfor.type].concat(lastCols) ],
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
	exports('askfor',askfor);
})