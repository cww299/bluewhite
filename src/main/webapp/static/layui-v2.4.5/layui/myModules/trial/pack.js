/* author:299
 * 2019/8/29   试制模块：包装
 * pack.render({ elem:'给定的元素。填充真正的内容', btn:'绑定的按钮' })
 */
layui.define(['mytable','element'],function(exports){
	var $ = layui.jquery,
		mytable = layui.mytable,
		element = layui.element
		table = layui.table,
		myutil = layui.myutil;
	var html = [
	            '<div class="layui-tab layui-tab-brief" lay-filter="tabFilterPack">',
					'<ul class="layui-tab-title">',
						'<li class="layui-this" style="width: 45%;" lay-id="tabFilterPackFirst">内外包装和杂工</li>',
						'<li style="width: 45%;">内外包装和杂工时间设定</li>',
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
							'<table id="packPageTable" lay-filter="packPageTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
							'</tr><table>',
							'<table id="packTimeTable" lay-filter="packTimeTable"></table>',
						'</div>',
					'</div>',
				'</div>',
	            ].join(' ');
	var pack = {	//模块
			
	};
	pack.render = function(opt){
		var elem = opt.elem,
			btn = opt.btn;
		$('#'+elem).html(html);	//填充真正的html内容
		element.render();
		var tableId = 'packPageTable';
		var tableTimeId = 'packTimeTable';
		mytable.render({			//裁片表格
			elem:'#'+tableId,
			data:[],
			size:'lg',
			colsWidth:[0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10],
			cols:[[
			       { type:'checkbox',},
			       { title:'填写机缝名',   		field:'',	},
			       { title:'其他物料',   		field:'',	},
			       { title:'所用裁片',   		field:'',	},
			       { title:'用到裁片或上道',   	field:'',	},
			       { title:'物料编号/名称', 	 	field:'',   },
			       { title:'机缝工序费用',   	field:'',	},
			       { title:'试制机缝工序费用',   field:'',	},
			       { title:'选择入行成本价',   	field:'',		 },
			       { title:'入成本价格',   		field:'',	},
			       { title:'各单道比全套工价',   field:'',  },
			       { title:'物料和上道压（裁剪）价',   	field:'',  },
			       { title:'物料和上道压', 		field:'',  },
			       { title:'为针工准备的压价',   		field:'',  },
			       { title:'单独机工工序外发的压价',  	field:'',  },
			       ]],
		})
		mytable.render({
			elem: '#'+tableTimeId,
			data:[],
			size:'lg',
			colsWidth:[0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10],
			cols:[[
			       { type:'checkbox',},
			       { title:'机缝工序',   	field:'',	},
			       { title:'针号',   		field:'',	},
			       { title:'线色或线号',   	field:'',	},
			       { title:'针距',   		field:'',	},
			       { title:'试制净快手时间',  field:'',   },
			       { title:'该工序回针次数',   	field:'',	},
			       { title:'直线机缝模式',   	field:'',	},
			       { title:'该工序满足G列',   	field:'',		 },
			       { title:'弧线机缝模式',   	field:'',	},
			       { title:'该工序满足I列',   	field:'',  },
			       { title:'弯曲复杂机缝模式',   field:'',  },
			       { title:'该工序满足K列', 		field:'',  },
			       { title:'单一机缝需要时间/秒',   	field:'',  },
			       { title:'设备折旧和房水电费',  	field:'',  },
			       { title:'管理人员费用',   		field:'',  },
			       { title:'电脑推算机缝该工序费用', 	field:'',  },
			       { title:'试制机缝该工序费用',   	field:'',  },
			       ]],
		})
		element.on('tab(tabFilterPack)', function(obj){
			var check = table.checkStatus('productTable').data;		//根据tab切换的选项下标，重载不同的表格
			switch(obj.index){
			case 0: 
				table.reload(tableId,{
					url: myutil.config.ctx+'/product/getMachinist?productId='+check[0].id,
				})
				break;
			case 1: 
				table.reload(tableTimeId,{
					url: myutil.config.ctx+'/product/getMachinist?productId='+check[0].id,
				})
				break;
			}
		});
		$('#'+btn).on('click',function(){	//绑定按钮点击事件。切换至该选项卡时。默认加载第一个表格
			var check = table.checkStatus('productTable').data;
			if(check.length<1)
				return myutil.emsg('请选择相应的商品');
			if(check.length>1)
				return myutil.emsg('不能同时选择多个商品');
			element.tabChange('tabFilterPack', 'tabFilterPackFirst');		//切换至默认的第一个选项卡
			table.cache[tableId] && table.reload(tableId,{
				url: myutil.config.ctx+'/product/getMachinist?productId='+check[0].id,
				page: { curr:1 }
			})
			$('#'+btn).css('color','red');
			$('#'+btn).siblings().css('color','white');
			$('#'+elem).siblings().hide();
			$('#'+elem).show();
		})
	}
	exports('pack',pack)
})