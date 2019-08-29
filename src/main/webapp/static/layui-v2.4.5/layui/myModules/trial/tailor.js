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
						'<div class="layui-tab-item">',			//裁剪激光
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
							'</tr><table>',
							'<table id="tailorLaserTable" lay-filter="tailorLaserTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//绣花激光
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
							'</tr><table>',
							'<table id="embroideryLaserTable" lay-filter="embroideryLaserTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//冲床
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
								'</tr><table>',
							'<table id="bedTable" lay-filter="bedTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//电烫
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
								'</tr><table>',
							'<table id="permTable" lay-filter="permTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//电推
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
								'</tr><table>',
							'<table id="electricTable" lay-filter="electricTable"></table>',
						'</div>',
						'<div class="layui-tab-item">',			//手工剪刀
							'<table class="layui-form"  style="margin-top:8px;"><tr>',
								'<td>裁剪价格</td>',
								'<td><input type="text" name="" class="layui-input"></td>',
								'<td>&nbsp;&nbsp;</td>',
								'<td><button lay-submit type="button" lay-filter="" class="layui-btn layui-btn-sm">搜索</button></td>',
								'</tr><table>',
							'<table id="scissorsTable" lay-filter="scissorsTable"></table>',
						'</div>',
					'</div>',
				'</div>',
	            ].join(' ');
	var tailor = {	//模块
			
	};
	tailor.render = function(opt){
		var elem = opt.elem,
			btn = opt.btn;
		$('#'+elem).html(html);	//填充真正的html内容
		element.render();
		var allTable = ['tailorPageTable','tailorLaserTable','embroideryLaserTable','bedTable','permTable','electricTable','scissorsTable'];
		mytable.render({			//裁片表格
			elem:'#'+allTable[0],
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
		element.on('tab(tabFilterTailor)', function(obj){
			var check = table.checkStatus('productTable').data;		//根据tab切换的选项下标，重载不同的表格
			var index = obj.index;
			var url = '';
			switch(index){
			case 0: 
				url = '';
				break;
			case 1: 
				url = '';
				break;
			case 2: 
				url = '';
				break;
			case 3: 
				url = '';
				break;
			case 4: 
				url = '';
				break;
			case 5: 
				url = '';
				break;
			case 6: 
				url = '';
				break;
			}
			/*table.reload(allTable[index],{
				url: myutil.config.ctx+url+'?productId='+check[0].id,
			})*/
		});
		$('#'+btn).on('click',function(){	//绑定按钮点击事件。切换至该选项卡时。默认加载第一个表格
			var check = table.checkStatus('productTable').data;
			if(check.length<1)
				return myutil.emsg('请选择相应的商品');
			if(check.length>1)
				return myutil.emsg('不能同时选择多个商品');
			element.tabChange('tabFilterTailor', 'tabTablePageFirst');		//切换至默认的第一个选项卡
			/*table.cache[tableId] && table.reload(tableId,{
				url: myutil.config.ctx+'/product/getMachinist?productId='+check[0].id,
				page: { curr:1 }
			})*/
			$('#'+elem).siblings().hide();
			$('#'+elem).show();
		})
	}
	exports('tailor',tailor)
})