/* author:299
 * 2019/8/29   试制模块：裁剪
 * tailor.render({ elem:'给定的元素。填充真正的内容', btn:'绑定的按钮' })
 */
layui.define(['mytable','element'],function(exports){
	var $ = layui.jquery,
		mytable = layui.mytable,
		element = layui.element
		table = layui.table,
		myutil = layui.myutil;
	var html = [
	            '<div class="layui-tab layui-tab-brief" lay-filter="tabFilterTailor">',
					'<ul class="layui-tab-title">',
						'<li class="layui-this" style="width: 12%;" lay-id="tabTablePageFirst">裁剪页面</li>',
						'<li style="width: 12%;">裁剪普通激光</li>',
						'<li style="width: 12%;">绣花定位激光</li>',
						'<li style="width: 12%;">冲床</li>',
						'<li style="width: 12%;">电烫</li>',
						'<li style="width: 12%;">电推</li>',
						'<li style="width: 12%;">手工剪刀</li>',
					'</ul>',
					'<div class="layui-tab-content">',
						'<div class="layui-tab-item layui-show">',	//裁剪页面
							'<table class="layui-form" style="margin-top:8px;"><tr>',
								'<td>产品名：</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td>数量：</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td>耗损：</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td>价格：</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
							'</tr><table>',
							'<table id="tailorPageTable" lay-filter="tailorPageTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//普通裁剪激光
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><span class="layui-badge">提示：小红点标志表示（含快手）</span></td>',
							'</tr><table>',
							'<table id="tailorLaserTable" lay-filter="tailorLaserTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//绣花激光
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><span class="layui-badge">提示：小红点标志表示（含快手）</span></td>',
							'</tr><table>',
							'<table id="embroideryLaserTable" lay-filter="embroideryLaserTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//冲床
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><span class="layui-badge">提示：小红点标志表示（含快手）</span></td>',
								'</tr><table>',
							'<table id="bedTable" lay-filter="bedTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//电烫
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><span class="layui-badge">提示：小红点标志表示（含快手）</span></td>',
								'</tr><table>',
							'<table id="permTable" lay-filter="permTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//电推
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><span class="layui-badge">提示：小红点标志表示（含快手）</span></td>',
								'</tr><table>',
							'<table id="electricTable" lay-filter="electricTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//手工剪刀
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><span class="layui-badge">提示：小红点标志表示（含快手）</span></td>',
								'</tr><table>',
							'<table id="scissorsTable" lay-filter="scissorsTable"></table>',
						'</div>',
					'</div>',
				'</div>',
	            ].join(' ');
	var tailor = {	//模块
			
	};
	
	
	var allTypeId = ['',71,72,75,73,76,77,];  //各类型实体id值。普通激光71、绣花激光72、冲床75、手工电烫73、电推76、手工剪刀77、设备电烫74、绣花领取78
	var allTable = ['tailorPageTable','tailorLaserTable','embroideryLaserTable','bedTable','permTable','electricTable','scissorsTable'];	//各实体对应的table
	var allType = [
	               { id: 71, name: '普通激光裁剪' },
	               { id: 72, name: '绣花激光裁剪' },
	               { id: 75, name: '冲床' },
	               { id: 73, name: '电烫' },
	               { id: 76, name: '电推' },
	               { id: 77, name: '手工剪刀' },
	               ];
	var choosedPrice = [{id: 1, name: '理论价值' },
		                {id: 2, name: '实验推算价格'},
	                    ];
	var allDouble = [{id: 1, name: '单'},
	                 {id: 2, name: '双'}];
	var dot = '<span class="layui-badge-dot"></span>';		//小圆点标志
	var allOrdinaryLaser = [];
	myutil.getDataSync({
		url:'/product/getBaseThree',
		success:function(data){
			layui.each(data,function(index,item){
				allOrdinaryLaser.push({
					id:	item.id,
					name: item.ordinaryLaser,
				})
			})
		}
	});
	
	tailor.render = function(opt){
		var elem = opt.elem,
			btn = opt.btn;
		$('#'+elem).html(html);	//填充真正的html内容
		element.render();
		rederMyTable({			//裁片汇总表格
			elem:'#'+allTable[0],
			colsWidth:[0,7,7,7,8,10,10,10,10,10,10,10,10,10,10,10,10,10],
			autoUpdate:{
				saveUrl:'/product/addOrdinaryLaser',
				field: { tailorSize_id:'tailorSizeId',tailorType_id:'tailorTypeId',},
			}, 
			cols:[[
			       { type:'checkbox',},
			       { title:'裁剪部位',   		field:'tailorName',	},
			       { title:'裁剪片数',   		field:'tailorNumber',	},
			       { title:'当批片数',   		field:'bacthTailorNumber',	},
			       { title:'裁片的平方M',   		field:'tailorSize_id',	   type:'select', select:{ data: allOrdinaryLaser }},
			       { title:'裁剪方式', 	 		field:'tailorType_id',     type:'select', select:{ data: allType }  },
			       { title:'理论价值含管理',   	field:'managePrice',	},
			       { title:'实验推算价格',   	field:'experimentPrice',},
			       { title:'市场价与实推价比',   field:'ratePrice',		},
			       { title:'选择入成本价格',   	field:'costPrice',	   type:'select', select:{ data: choosedPrice }},
			       { title:'入成本价格',   		field:'allCostPrice',  },
			       { title:'各单道比全套工价',   field:'scaleMaterial',  },
			       { title:'物料压价', 			field:'priceDown',  },
			       { title:'不含绣花环节的为机工压价',   	field:'noeMbroiderPriceDown',  },
			       { title:'含绣花环节的为机工压价',  	field:'embroiderPriceDown',  },
			       { title:'为机工准备的压价',  			field:'machinistPriceDown',  },
			       ]],
		})
		var autoUpdate = {		//其他普通页面的修改配置
				saveUrl:'/product/addOrdinaryLaser',
				field: { tailorType_id:'tailorTypeId'},
				//isReload: true,
			};
		for(var i =1;i<3;i++){	//普通、绣花激光
			rederMyTable({
				elem: '#'+allTable[i],
				colsWidth:[0,7,10,6,8,6,6,6,6,6,8,10,10,10,10,10,10,10],
				verify:{ 
					price:['perimeter','time','otherTimeOne','otherTimeTwo',],
					count:['stallPoint'],
				},
				cols:[[
				       { type:'checkbox',},
				       { title:'裁剪部位',   	field:'tailorName',	},
				       { title:'裁剪方式',   	field:'tailorType_id',	type:'select', select:{ data: allType,isDisabled:true,unsearch:true }},
				       { title:'裁片周长',   	field:'perimeter',	edit:true, },
				       { title:'激光停顿点',   	field:'stallPoint', edit:true, },
				       { title:'单双激光头', 	field:'singleDouble',  type:'select', select:{ data: allDouble }  },
				       { title:'捡片时间',   	field:'time',			edit:true, },
				       { title:'其他时间1',   	field:'otherTimeOne', 	edit:true, },
				       { title:'其他时间2',   	field:'otherTimeTwo', 	edit:true, },
				       { title:'拉布时间',   	field:'rabbTime',	   			},
				       { title:'单片激光净时', 	field:'singleLaserTime',  		},
				       { title:'单片激光放快手时间', field:'singleLaserHandTime',},
				       { title:'工价'+dot, 			field:'labourCost',  		},
				       { title:'设备折旧和房水电费', field:'equipmentPrice',  	},
				       { title:'管理人员费用',  		field:'administrativeAtaff',},
				       { title:'裁片费用',  			field:'stallPrice',  		},
				       ]],
			})
		}
		rederMyTable({		//冲床
			elem: '#'+allTable[3],
			colsWidth:[0,0,10,6,6,6,6,6,6,11,11,11,11,],
			verify:{ 
				price:['otherTimeOne','otherTimeTwo','otherTimeThree'],
				count:['layerNumber'],
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'裁剪部位',   		field:'tailorName',	},
			       { title:'裁剪方式',   		field:'tailorType_id',	type:'select', select:{ data: allType,isDisabled:true,unsearch:true }},
			       { title:'叠片层数',   		field:'layerNumber',	edit:true, },
			       { title:'其他时间1',   		field:'otherTimeOne',	edit:true, },
			       { title:'其他时间2', 	 		field:'otherTimeTwo',   edit:true, },
			       { title:'其他时间3',   		field:'otherTimeThree',	edit:true, },
			       { title:'叠布秒数'+dot,  		field:'overlappedSeconds',},
			       { title:'冲压秒数'+dot,  		field:'punchingSeconds',		 },
			       { title:'工价'+dot,   		field:'labourCost',	   },
			       { title:'设备折旧和房水电费', field:'equipmentPrice',  },
			       { title:'管理人员费用',   	field:'administrativeAtaff',  },
			       { title:'裁片费用', 			field:'stallPrice',  },
			       ]],
		})
		
		rederMyTable({		//电烫
			elem: '#'+allTable[4],
			colsWidth:[0,0,10,7,6,6,6,6,6,6,6,10,10,10,10,10,10],
			verify:{ 
				price:['otherTimeTwo','otherTimeOne'],
				count:['typesettingNumber',],
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'裁剪部位',   		field:'tailorName',	},
			       { title:'裁剪方式',   		field:'tailorType_id',	type:'select', select:{ data: allType,isDisabled:true,unsearch:true }},
			       { title:'一板排版片数',   	field:'typesettingNumber',	edit:true, },
			       { title:'其他时间1',   		field:'otherTimeOne',	 	edit:true, },
			       { title:'其他时间2', 	 		field:'otherTimeTwo',   	edit:true, },
			       { title:'电烫秒数',    		field:'permSeconds',	},
			       { title:'撕片秒数'+dot,    	field:'tearingSeconds',},
			       { title:'拉布秒数'+dot,    	field:'rabbTime',		 },
			       { title:'电烫工价'+dot,    	field:'permPrice',	   },
			       { title:'撕片工价',   		field:'tearingPrice',  },
			       { title:'设备折旧和房水电费', field:'equipmentPrice',  },
			       { title:'管理人员费用',   	field:'administrativeAtaff',  },
			       { title:'裁片费用', 			field:'stallPrice',  },
			       ]],
		})
		rederMyTable({		//电推
			elem: '#'+allTable[5],
			colsWidth:[0,0,10,6,6,6,6,6,6,10,10,10,10,],
			verify:{ 
				price:['perimeter','otherTimeOne','otherTimeTwo',],
				count:['layerNumber',],
			},
			cols:[[
				   { type:'checkbox',},
				   { title:'裁剪部位',   		field:'tailorName',	},
				   { title:'裁剪方式',   		field:'tailorType_id',	type:'select', select:{ data: allType,isDisabled:true,unsearch:true }},
				   { title:'叠片层数',   		field:'layerNumber',	edit:true, },
				   { title:'裁片周长',   		field:'perimeter',		edit:true, },
				   { title:'其他时间1',   		field:'otherTimeOne',	edit:true, },
			       { title:'其他时间2', 	 		field:'otherTimeTwo',   edit:true, },
			       { title:'叠布秒数'+dot,  		field:'overlappedSeconds',},
			       { title:'电推秒数'+dot,  		field:'electricSeconds',		 },
			       { title:'工价'+dot,   		field:'labourCost',	   },
			       { title:'设备折旧和房水电费', field:'equipmentPrice',  },
			       { title:'管理人员费用',   	field:'administrativeAtaff',  },
			       { title:'裁片费用', 			field:'stallPrice',  },
			       ]],
		})
		rederMyTable({		//手工剪刀
			elem: '#'+allTable[6],
			colsWidth:[0,0,10,7,7,7,7,12,12,12,12],
			verify:{ 
				price:['perimeter','otherTimeTwo','otherTimeThree'],
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'裁剪部位',   		field:'tailorName',	},
			       { title:'裁剪方式',   		field:'tailorType_id',	type:'select', select:{ data: allType,isDisabled:true,unsearch:true }},
			       { title:'裁片周长',   		field:'perimeter',	 edit:true,   },
			       { title:'其他时间1',   		field:'otherTimeOne',edit:true,	  },
			       { title:'其他时间2', 	 		field:'otherTimeTwo',edit:true,   },
			       { title:'手工秒数'+dot, 		field:'manualSeconds',	},
			       { title:'工价'+dot,   		field:'labourCost',	   },
			       { title:'设备折旧和房水电费', field:'equipmentPrice',  },
			       { title:'管理人员费用',   	field:'administrativeAtaff',  },
			       { title:'裁片费用', 			field:'stallPrice',  },
			       ]],
		})
		function rederMyTable(opt){
			var newopt = $.extend({},{ 
					data:[], 
					size:'lg', 
					autoUpdate: autoUpdate,
					toolbar: '<div><span class="showProductBtn">商品：-----</span></div>',
					done: function(){
						var check = table.checkStatus('productTable').data;
						check[0] && $('.showProductBtn').html('商品名称：'+check[0].name);
					}
				},opt);
			mytable.render(newopt);
		}
		element.on('tab(tabFilterTailor)', function(obj){
			var check = table.checkStatus('productTable').data;		
			var index = obj.index;
			table.reload(allTable[index],{		//根据tab切换的选项下标，重载不同的表格
				url: myutil.config.ctx+'/product/getOrdinaryLaser?productId='+check[0].id+'&getTailorTypeId='+allTypeId[index],
			})
		});
		$('#'+btn).on('click',function(){	//绑定按钮点击事件。切换至该选项卡时。默认加载第一个表格
			var check = table.checkStatus('productTable').data;
			if(check.length<1)
				return myutil.emsg('请选择相应的商品');
			if(check.length>1)
				return myutil.emsg('不能同时选择多个商品');
			element.tabChange('tabFilterTailor', 'tabTablePageFirst');		//切换至默认的第一个选项卡、重载第一个表格
			table.cache[allTable[0]] && table.reload(allTable[0],{
				url: myutil.config.ctx+'/product/getTailor?productId='+check[0].id,
				page: { curr:1 }
			})
			$('#'+elem).siblings().hide();
			$('#'+elem).show();
		})
	}
	exports('tailor',tailor)
})