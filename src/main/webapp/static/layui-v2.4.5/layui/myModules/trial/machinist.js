/* author:299
 * 2019/8/28   试制模块：机工
 * machinist.render({ elem:'给定的元素。填充真正的内容', btn:'绑定的按钮' })
 */
layui.define(['mytable','element',['form']],function(exports){
	var $ = layui.jquery,
		mytable = layui.mytable,
		element = layui.element
		form =layui.form
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
	var allMaterial = [];		//所有除裁片以外物料
	var allCutparts = []; 		//所有裁片或上道
	var choosedPrice = [ {id: 1, name: '电脑推算价格' }, {id: 2, name: '试制费用价格'}, ];
	var allNeedlesize = myutil.getDataSync({ url: myutil.config.ctx+'/product/getBaseOne?type=needlesize'});
	var allWiresize  = myutil.getDataSync({ url: myutil.config.ctx+'/product/getBaseOne?type=wiresize'});
	var allNeedlespur  = myutil.getDataSync({ url: myutil.config.ctx+'/product/getBaseOne?type=needlespur'});
	
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
			curd: {		//新增配置
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
			autoUpdate:{	//修改、删除配置
				saveUrl:'/product/updateMachinist',
				deleUrl:'/product/deleteMachinist',
				field: { productMaterials_id:'productMaterialsId', },
				isReload: true,
			},
			parseData:function(ret){	//解析数据前先获取必要的数据
				var check = table.checkStatus('productTable').data;
				allMaterial.splice(0,999);	//删除所有元素
				allMaterial.push({
					id:'', materiel:{ name:'请选择'}
				})
				myutil.getDataSync({	//默认查找压货为机工的。id为81   获取用除裁片以外物料
					url: myutil.config.ctx+'/product/getProductMaterials?overstockId=81&size=99&productId='+check[0].id,	
					success: function(data){
						layui.each(data,function(index,item){
							allMaterial.push(item);
						})
					}
				});
				allCutparts.splice(0,999);	//删除所有元素
				myutil.getDataSync({		//获取机封上道和裁片
					url: myutil.config.ctx+'/product/getMachinistName?id='+check[0].id,	
					success: function(data){
						layui.each(data,function(index,item){
							allCutparts.push(item);
						})
					}
				});
				
				return {  msg:ret.message,  code:ret.code , data:ret.data.rows, count:ret.data.total }; 
			},
			colsWidth:[0,10,18,22,10,10,10,10,10,10,10,10,10,10,10,10,10,10],
			cols:[[
			       { type:'checkbox',},
			       { title:'填写机缝名',   		field:'machinistName',	edit:true,  },
			       { title:'其他物料',   		field:'productMaterials_id',type:'select',   select:{ data: allMaterial, name:'materiel_name',  }},
			       { title:'用到裁片或上道',   	field:'cutparts',	templet: getFormSelects(), edit:false, },
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
	        done:function(){
	        	var trIndex = ''; //记录多选框吸附的行
	        	layui.each($('td[data-field="cutparts"]').find('.layui-form-select').find('.layui-input'),function(index,item){	//遍历表格物料名称下拉框
					$(item).unbind().click(function(event){
						$(this).parent().parent().addClass('layui-form-selected');
						var width = $(this).parent().width();			
						var tdElem = $(this).closest('td');
						var Y = tdElem.offset().top;
						var X = tdElem.offset().left;
						(function getSelectHtml(){
							var html = '';
							for(var key in allCutparts){
								var item = allCutparts[key];
								var input = '<input type="checkbox" lay-filter="" lay-skin="primary">';
								html += '<dd data-value="'+item.price+'" style="text-align: left;">'+input+item.name+'</dd>';
							}
							$('#searchTipDiv').html(html);
							form.render();
						})();
						clickDD();
						$('#searchTipDiv').css("top",Y+45);	//定位搜索提示框位置并显示提示框
						$('#searchTipDiv').css("left",X+15);
						$('#searchTipDiv').css("width",width);
						$('#searchTipDiv').show();
						trIndex = $(this).closest('tr').data('index');
					});
				})
				function clickDD(){
	        		layui.each($('#searchTipDiv').find('dd'),function(index,item){
	        			$(item).unbind().click(function(obj){
	        				var checkbox = $(obj.target).find('input');
	        				var checked = $(checkbox).prop('checked');
	        				$(checkbox).prop('checked',!checked);
	        				form.render();
	        				var input = $('div[lay-id="'+tableId+'"]').find('tr[data-index="'+trIndex+'"]').find('td[data-field="cutparts"]').find('.layui-input');
	        				$(input).append('<span class="layui-badge" style="margin: 0 2px; height: 80%; font-size: 15px;  padding-top: 4;">'+
	        						'测试'+'<i class="layui-icon layui-icon-close"></i></span>')
	        			})
	        			$('.layui-icon-close').click(function(evnet){
	        				$(this).parent().remove();
	        			})
	        		});
	        	}
	        }
		})
		function getFormSelects(){
			return function(d){
				var text = '',c = d.cutparts?d.cutparts.split(','):[];
				for(var key in c){
					if(c[key]!='')
						text += '<span class="layui-badge">'+c[key]+'<i class="layui-icon layui-icon-close"></i></span>&nbsp;';
				}
				var html = ['<div class="layui-form-select">',
				            	'<div class="layui-select-title">',
				            		'<div class="layui-input" style="text-align: center;padding-top:2%;white-space: pre-line;">',
				            			text,
				            		'</div>',
				            		'<i class="layui-edge"></i>',
				            	'</div>',
				            '</div>',
				            ].join(' ');
				return html;
			}
		}
		mytable.render({
			elem: '#'+tableTimeId,
			data:[],
			size:'lg',
			colsWidth:[0,8,6,8,10,6,6,10,6,10,6,10,6,8,8,8,10,10],
			autoUpdate:{
				saveUrl:'/product/updateMachinist',
				deleUrl:'/product/deleteMachinist',
				field: { needlesize_id:'needleSizeId',wireSize_id:'wireSizeId',needleSpur_id:'needleSpurId' },
				isReload: true,
			},
			verify:{
				price: ['time',''],
				count: ['backStitchCount',],
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'机缝工序',   	field:'machinistName',	},
			       { title:'针号',   		field:'needleSize_id',	type:'select', select:{ data: allNeedlesize, },  },
			       { title:'线色或线号',   	field:'wireSize_id',	type:'select', select:{ data: allWiresize, },  },
			       { title:'针距',   		field:'needleSpur_id',	type:'select', select:{ data: allNeedlespur, },  },
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
			$('#'+btn).css('color','red');
			$('#'+btn).siblings().css('color','white');
			$('#'+elem).siblings().hide();
			$('#'+elem).show();
		})
	}
	exports('machinist',machinist)
})