/* author:299
 * 2019/8/29   试制模块：绣花
 * embroidery.render({ elem:'给定的元素。填充真正的内容', btn:'绑定的按钮' })
 */
layui.define(['mytable','element'],function(exports){
	var $ = layui.jquery,
		mytable = layui.mytable,
		element = layui.element
		table = layui.table,
		myutil = layui.myutil;
	var html = [
	            '<div class="layui-tab layui-tab-brief" lay-filter="tabFilterEmbroidery">',
					'<ul class="layui-tab-title">',
						'<li class="layui-this" style="width: 45%;" lay-id="tabFilterEmbroideryFirst">绣花页面</li>',
						'<li style="width: 45%;">绣花时间设定</li>',
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
							'<table id="embroideryPageTable" lay-filter="embroideryPageTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
							'</tr><table>',
							'<table id="embroideryTimeTable" lay-filter="embroideryTimeTable"></table>',
						'</div>',
					'</div>',
				'</div>',
	            ].join(' ');
	var embroidery = {	//模块
			
	};
	var choosedPrice = [{id: 1, name: '电脑推算价格' },
		                {id: 2, name: '市场价格'},];
	var addTempData = {
			embroideryName:'',reckoningEmbroideryPrice:'',reckoningSewingPrice:'',
			costPrice:choosedPrice[0].id,allCostPrice:'',scaleMaterial:'',priceDown:'',priceDownRemark:'',
			productId: '',
	};
	embroidery.render = function(opt){
		var elem = opt.elem,
			btn = opt.btn;
		$('#'+elem).html(html);	//填充真正的html内容
		element.render();
		var tableId = 'embroideryPageTable';
		var tableTimeId = 'embroideryTimeTable';
		mytable.render({			//裁片表格
			elem:'#'+tableId,
			data:[],
			size:'lg',
			autoUpdate:{
				saveUrl:'/product/addEmbroidery',
			}, 
			curd:{
				addTemp: addTempData,
			},
			colsWidth:[0,0,10,10,10,10,10,10,10,],
			cols:[[
			       { type:'checkbox',},
			       { title:'绣花步骤',   		field:'embroideryName',	},
			       { title:'电脑推算绣花价格',   field:'reckoningEmbroideryPrice', edit:false,	},
			       { title:'目前市场价格',   	field:'reckoningSewingPrice',	  edit:false,},
			       { title:'选择入成本价格',   	field:'costPrice',	type:'select', select:{ data: choosedPrice }},
			       { title:'入成本价格', 	 	field:'allCostPrice',   edit:false,  },
			       { title:'各单道比全套工价',   field:'scaleMaterial',	edit:false,  },
			       { title:'物料压价',  			field:'priceDown',		edit:false,  },
			       { title:'为机工准备的压价',   field:'priceDownRemark',edit:false,	 },
			       ]],
		})
		mytable.render({
			elem: '#'+tableTimeId,
			data:[],
			size:'lg',
			colsWidth:[0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10],
			cols:[[
			       { type:'checkbox',},
			       { title:'绣花步骤',   	field:'embroideryName',	},
			       { title:'针数',   		field:'needleNumber',	},
			       { title:'针号',   		field:'needlesize',	},
			       { title:'线粗细号',   	field:'wiresize',	},
			       { title:'贴布数',  		field:'applique',   },
			       { title:'绣花要用裁片1',  field:'sizeName',	},
			       { title:'绣花要用裁片2',  field:'sizeTwoName',	},
			       { title:'裁片1面积',   	field:'size',		 },
			       { title:'确认绣片面积',   field:'affirmSize',	},
			       { title:'裁片2面积',   	field:'sizeTwo',  },
			       { title:'确认绣片面积',   field:'affirmSizeTwo',  },
			       { title:'蒙薄膜层数', 	field:'membrane',  },
			       { title:'垫纸性质',   	field:'packingPaper',  },
			       { title:'绣花模式',  		field:'embroideryMode',  },
			       { title:'手填可绣多少片', field:'embroiderySlice',  },
			       { title:'几头操作', 		field:'few',  },
			       { title:'绣花针号',   	field:'embroideryNeedlesize',  },
			       { title:'绣花线号',   	field:'embroideryWiresize',  },
			       { title:'绣花线色', 		field:'embroideryColor',  },
			       { title:'绣花线色（数量）',field:'embroideryColorNumber',  },
			       { title:'请选贴布面积',   field:'appliqueSize',  },
			       { title:'贴布面积',   	field:'',  },
			       ]],
		})
		element.on('tab(tabFilter)', function(obj){
			var check = table.checkStatus('productTable').data;		//根据tab切换的选项下标，重载不同的表格
			var table = tableId;
			if(obj.index==1)
				table = tableTimeId;
			table.cache[table] && table.reload(table,{
				url: myutil.config.ctx+'/product/getEmbroidery?productId='+check[0].id,
				page: { curr:1 }
			})
		});
		$('#'+btn).on('click',function(){	//绑定按钮点击事件。切换至该选项卡时。默认加载第一个表格
			var check = table.checkStatus('productTable').data;
			addTempData.productId = check[0].id;
			if(check.length<1)
				return myutil.emsg('请选择相应的商品');
			if(check.length>1)
				return myutil.emsg('不能同时选择多个商品');
			element.tabChange('tabFilterEmbroidery', 'tabFilterEmbroideryFirst');		//切换至默认的第一个选项卡
			table.cache[tableId] && table.reload(tableId,{
				url: myutil.config.ctx+'/product/getEmbroidery?productId='+check[0].id,
				page: { curr:1 }
			})
			$('#'+elem).siblings().hide();
			$('#'+elem).show();
		})
	}
	exports('embroidery',embroidery);
})