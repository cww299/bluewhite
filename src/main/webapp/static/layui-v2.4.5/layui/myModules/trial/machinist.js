/* author:299
 * 2019/8/28   试制模块：机工
 * machinist.render({ elem:'给定的元素。填充真正的内容', btn:'绑定的按钮' })
 */
layui.define(['mytable','element'],function(exports){
	var $ = layui.jquery,
		mytable = layui.mytable,
		element = layui.element
		table = layui.table,
		myutil = layui.myutil;
	var html = [
	            '<div class="layui-tab layui-tab-brief" lay-filter="tabMachinist">',
					'<ul class="layui-tab-title">',
						'<li class="layui-this" style="width: 45%;" lay-id="tabMachinistFirst">机工页面</li>',
						'<li style="width: 45%;">机缝时间</li>',
					'</ul>',
					'<div class="layui-tab-content">',
						'<div class="layui-tab-item layui-show">',
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
							'<table id="machinistPageTable" lay-filter="machinistPageTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
							'</tr><table>',
							'<table id="machinistTimeTable" lay-filter="machinistTimeTable"></table>',
						'</div>',
					'</div>',
				'</div>',
	            ].join(' ');
	var machinist = {	//模块
			
	};
	var allMaterial = [];
	var choosedPrice = [ {id: 1, name: '电脑推算价格' }, {id: 2, name: '试制费用价格'}, ];
	var allNeedlesize = myutil.getDataSync({ url:'/product/getBaseOne?type=needlesize'});
	var allWiresize  = myutil.getDataSync({ url:'/product/getBaseOne?type=wiresize'});
	var allNeedlespur  = myutil.getDataSync({ url:'/product/getBaseOne?type=needlespur'});
	var all  = myutil.getDataSync({ url:'/product/getBaseFour?sewingOrder="wiresize"'});
	
	machinist.render = function(opt){
		var elem = opt.elem,
			btn = opt.btn;
		$('#'+elem).html(html);	//填充真正的html内容
		element.render();
		var tableId = 'machinistPageTable';
		var tableTimeId = 'machinistTimeTable';
		mytable.render({			//裁片表格
			elem:'#'+tableId,
			data:[],
			size:'lg',
			colsWidth:[0,10,18,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10],
			curd: {
				addTemp:{
					machinistName: '',
					productMaterialsId:'',
					
					cutparts: '',
					
					reckoningSewingPrice: '',
					trialSewingPrice: '',
					costPrice: choosedPrice[0].id,
					allCostPrice: '',
					scaleMaterial: '',
					priceDown: '',
					priceDownRemark: '',
					needleworkPriceDown: '',
					machinistPriceDown: '',
				},
				saveFun:function(data){
					for(var i=0;i<data.length;i++){
						var check = table.checkStatus('productTable').data;
						data[i]['productId'] = check[0].id;		//添加产品id参数
						myutil.saveAjax({
							url: '/product/addMachinist', 
							data: data[i],
						})
					}
					table.reload(tableId);
				},
			},
			autoUpdate:{
				saveUrl:'/product/updateMachinist',
				deleUrl:'/product/deleteMachinist',
				field: { productMaterials_id:'productMaterialsId', },
				isReload: true,
			},
			parseData:function(ret){
				var check = table.checkStatus('productTable').data;
				allMaterial.splice(0,999);	//删除所有元素
				allMaterial.push({
					id:'', materiel:{ name:'请选择'}
				})
				 myutil.getDataSync({
					url: myutil.config.ctx+'/product/getProductMaterials?overstockId=81&size=99&productId='+check[0].id,	//默认查找压货为机工的。id为81
					success: function(data){
						layui.each(data,function(index,item){
							allMaterial.push(item);
						})
					}
				});
				return {  msg:ret.message,  code:ret.code , data:ret.data.rows, count:ret.data.total }; 
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'填写机缝名',   		field:'machinistName',	edit:true,  },
			       { title:'其他物料',   		field:'productMaterials_id',type:'select',   select:{ data: allMaterial, name:'materiel_name',  }},
			       { title:'所用裁片',   		field:'',	edit:false, },
			       { title:'用到裁片或上道',   	field:'cutparts',	edit:false, },
			       { title:'物料编号/名称', 	 	field:'',   edit:false, },
			       { title:'机缝工序费用',   	field:'reckoningSewingPrice',	edit:false, },
			       { title:'试制机缝工序费用',   field:'trialSewingPrice',	edit:false, },
			       { title:'选择入行成本价',   	field:'costPrice',		type:'select',   select:{ data: choosedPrice,}},
			       { title:'入成本价格',   		field:'allCostPrice',	edit:false, },
			       { title:'各单道比全套工价',   field:'scaleMaterial',  edit:false, },
			       { title:'物料和上道压（裁剪）价',   	field:'priceDown',  edit:false, },
			       { title:'物料和上道压', 		field:'priceDownRemark',  edit:false, },
			       { title:'为针工准备的压价',   		field:'needleworkPriceDown', edit:false,  },
			       { title:'单独机工工序外发的压价',  	field:'machinistPriceDown',  edit:false,  },
			       ]],
		})
		mytable.render({
			elem: '#'+tableTimeId,
			data:[],
			size:'lg',
			colsWidth:[0,8,6,6,6,6,6,10,6,10,6,10,6,8,8,8,10,10],
			autoUpdate:{
				saveUrl:'/product/updateMachinist',
				deleUrl:'/product/deleteMachinist',
				field: { needlesize_id:'needlesizeId',wiresize_id:'wiresizeId',needlespur_id:'needlespurId' },
				isReload: true,
			},
			verify:{
				price: ['time',''],
				count: ['backStitchCount',],
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'机缝工序',   	field:'machinistName',	},
			       { title:'针号',   		field:'needlesize_id',	type:'select', select:{ data: allNeedlesize, },  },
			       { title:'线色或线号',   	field:'wiresize_id',	type:'select', select:{ data: allWiresize, },  },
			       { title:'针距',   		field:'needlespur_id',	type:'select', select:{ data: allNeedlespur, },  },
			       { title:'快手时间',  		field:'time',   edit:true, },
			       { title:'回针次数',   	field:'backStitchCount',edit:true, 	},
			       { title:'直线机缝模式',   	field:'beeline',	type:'select', select:{ data: [], }, },
			       { title:'该工序满足G列',   	field:'beelineNumber',	edit:true, 		 },
			       { title:'弧线机缝模式',   	field:'arc',	    type:'select', select:{ data: [], }, },
			       { title:'该工序满足I列',   	field:'arcNumber',  	edit:true, 	},
			       { title:'弯曲复杂机缝模式',   field:'bend',  		type:'select', select:{ data: [], }, },
			       { title:'该工序满足K列', 		field:'bendNumber',  	edit:true, 	},
			       { title:'单一机缝需要时间/秒',   	field:'oneSewingTime',  },
			       { title:'设备折旧和房水电费',  	field:'equipmentPrice',  },
			       { title:'管理人员费用',   		field:'administrativeAtaff',  },
			       { title:'电脑推算机缝该工序费用', 	field:'reckoningSewingPrice',  },
			       { title:'试制机缝该工序费用',   	field:'trialSewingPrice',  },
			       ]],
		})
		element.on('tab(tabMachinist)', function(obj){
			var check = layui.table.checkStatus('productTable').data;		//根据tab切换的选项下标，重载不同的表格
			var table = tableId;
			if(obj.index==1){
				table = tableTimeId;
			}
			layui.table.cache[table] && layui.table.reload(table,{
				url: myutil.config.ctx+'/product/getMachinist?productId='+check[0].id,
				page: { curr:1 }
			})
		});
		$('#'+btn).on('click',function(){	//绑定按钮点击事件。切换至该选项卡时。默认加载第一个表格
			var check = table.checkStatus('productTable').data;
			if(check.length<1)
				return myutil.emsg('请选择相应的商品');
			if(check.length>1)
				return myutil.emsg('不能同时选择多个商品');
			element.tabChange('tabMachinist', 'tabMachinistFirst');		//切换至默认的第一个选项卡
			table.cache[tableId] && table.reload(tableId,{
				url: myutil.config.ctx+'/product/getMachinist?productId='+check[0].id,
				page: { curr:1 }
			})
			$('#'+elem).siblings().hide();
			$('#'+elem).show();
		})
	}
	exports('machinist',machinist)
})