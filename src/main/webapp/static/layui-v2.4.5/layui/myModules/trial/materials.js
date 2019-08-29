/* author:299
 * 2019/8/29   试制模块：dd除裁片、生产用料
 * materials.render({ elem:'给定的元素。填充真正的内容', btn:'绑定的按钮' })
 */
layui.define(['mytable'],function(exports){
	var $ = layui.jquery,
		mytable = layui.mytable,
		table = layui.table,
		myutil = layui.myutil;
	var html = [
	            '<table class="layui-form"><tr>',
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
	            	'<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="">查找</button></td>',
	            	'<td>&nbsp;&nbsp;</td>',
	            '</tr></table>',
	            '<table id="materialsTable" lay-filter="materialsTable"></table>'
	            ].join(' ');
	var materials = {	//模块
			
	};
	materials.render = function(opt){
		var elem = opt.elem,
			btn = opt.btn;
		$('#'+elem).html(html);	//填充真正的html内容
		var tableId = 'materialsTable';
		mytable.render({			//生产用料表格
			elem:'#'+tableId,
			data:[],
			size:'lg',
			autoUpdate:{
				saveUrl:'/product/updateCutParts',
				deleUrl:'/product/deleteCutParts',
			}, 
			curd:{
				addTemp:{cutPartsName:'',cutPartsNumber:1,perimeter:'',allPerimeter:'',materielName:'',composite:'',oneMaterial:'',unit:'',perimeter:'',
					scaleMaterial:'', manualLoss:'',productCost:'',productRemark:'',batchMaterial:'',batchMaterialPrice:'',addMaterial:'',id:'',
				},
				saveFun:function(data){
					for(var i=0;i<data.length;i++){
						var check = table.checkStatus('productTable').data;
						data[i]['productId'] = check[0].id;		//添加产品id参数
						myutil.saveAjax({
							url: '/product/addCutParts', 
							data: data[i],
						})
					}
					table.reload(tableId);
				}
			},
			//verify:{ count:[''], notNull:[''],price:[] },
			colsWidth:[0,6,6,6,5,20,6,6,6,6,6,6,6,4,6,7,7],
			cols:[[
			       { type:'checkbox',},
			       { title:'裁片名字',   	field:'',	},
			       { title:'使用片数',   	field:'',	},
			       { title:'单片周长',   	field:'',	},
			       { title:'总周长',   		field:'',	},
			       { title:'物料编号/名称',  field:'',   },
			       { title:'是否复合',   	field:'',	},
			       { title:'单片用料',   	field:'',	},
			       { title:'单位',   		field:'',	},
			       { title:'单片周长',   	field:'',	},
			       { title:'用料占比',   	field:'',  },
			       { title:'总用料',   		field:'',  },
			       { title:'手动损耗', 		field:'',  },
			       { title:'单价',   		field:'',  },
			       { title:'备注',  	 		field:'',  },
			       { title:'当批单片用料',   field:'',  },
			       { title:'当批单片价格',   field:'',  },
			       ]],
		})
		$('#'+btn).on('click',function(){	//绑定按钮点击事件
			var check = table.checkStatus('productTable').data;
			if(check.length<1)
				return myutil.emsg('请选择相应的商品');
			if(check.length>1)
				return myutil.emsg('不能同时选择多个商品');
			table.cache[tableId] && table.reload(tableId,{
				url: myutil.config.ctx+'/product/getCutParts?productId='+check[0].id,
				page: { curr:1 }
			})
			$('#'+elem).siblings().hide();
			$('#'+elem).show();
		})
	}
	exports('materials',materials)
})