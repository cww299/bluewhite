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
	var allUnit = myutil.getDataSync({ url:'/product/getBaseOne?type=unit', });
	var allOverstock = myutil.getDataSync({ url:'/product/getBaseOne?type=overstock', });
	var allMaterielSelect = '';
	var materials = {	//模块
			
	};
	myutil.getData({ 
		url:'/product/getMateriel',
		success:function(data){
			layui.each(data,function(index,item){
				allMaterielSelect += '<dd data-value="'+item.id+'">'+item.number+' ~ '+item.name+' ~ ￥'+item.price+' ~ '+item.unit+'</dd>';
			})
		}
	});
	var tableId = 'materialsTable';
	var noneHtml = '<dd style="color:#999;">没有搜索到匹配的相关信息</dd>';
	var updateTrData = { }, inputElem = null, inputText = '',inputField='';	//用于记录所点击的下拉框的值、修改的当行id、输入框元素。用于修改、回滚等
	var renderSelectSearch = function(){		//自定义下拉框搜索
		renderTd('materiel');
		function renderTd(attribute){
			layui.each($('td[data-field="'+attribute+'"]').find('.layui-form-select'),function(index,item){	//遍历表格物料名称下拉框
				$(item).unbind().on('click',function(event){
					layui.stope(event);					//阻止事件冒泡、去除下拉框原本的点击事件、阻止点击事件自动隐藏
					$(this).addClass('layui-form-selected');
					var width = $(this).width();			
					var tdElem = $(this).closest('td');
					var val = $(this).find('input').val();
					var Y = tdElem.offset().top;
					var X = tdElem.offset().left;
					getSearchMateriael(val);
					$('#searchTipDiv').css("top",Y+45);	//定位搜索提示框位置并显示提示框
					$('#searchTipDiv').css("left",X+15);
					$('#searchTipDiv').css("width",width);
					$('#searchTipDiv').show();
					var i = $(this).closest('tr').data('index');
					var trData = layui.table.cache[tableId][i];
					updateTrData = trData;				//记录点击的当行数据和输入框、用于修改和修改成功后修改相应的输入框值
					inputElem = $(this).find('input');
					inputText = val;
					inputField = $(this).closest('td').data('field');
				})
			})
			layui.each($('td[data-field="'+attribute+'"]').find('.layui-form-select').find('input'),function(index,item){
				$(item).bind("input propertychange",function(event){	//监听输入框内容改变
					getSearchMateriael($(this).val());
				});
			})
		}
		 $(document).on('click',function(obj){		//监听其他点击事件、用于隐藏提示框
			 if($(obj).closest('#searchTipDiv').length==0){
				 $('#searchTipDiv').hide();
				 $(this).removeClass('layui-form-selected');
				 $(inputElem).val(inputText);
			 } 
		 })
	}
	function getSearchMateriael(name){	//根据输入的内容进行搜索、填充选择项
		name = name.split('~')[1]?name.split('~')[1].trim():'';
		var html = '';
		if(!name){
			html = allMaterielSelect;
			renderHtml();
		}
		else
			myutil.getData({
				url:'/product/getMateriel',
				data:{ name: name },
				success:function(data){
					if(data.length==0)
						html = noneHtml;
					layui.each(data,function(index,item){
						//var sty='style="background-color:#5fb878;"';
						html += '<dd data-value="'+item.id+'">'+item.number+' ~ '+item.name+' ~ ￥'+item.price+' ~ '+item.unit+'</dd>';
					})
					renderHtml();
				}
			})
		function renderHtml(){
			$('#searchTipDiv').html(html);
			$('#searchTipDiv').find('dd').on('click',function(obj){		//监听选择事件、如果选中某一个选项
				layui.stope(obj);
				var text = $(this).html();
				var val = $(this).data('value');
				if(!val)
					return;
				$('#searchTipDiv').hide();
				$(inputElem).closest('.layui-form-select').removeClass('layui-form-selected');
				$(inputElem).val(text);	 	//修改下拉框显示的值、缓存值
				inputText = text;
				updateTrData['materielId'] = val;
				if(!updateTrData.id)
					return;
				var data = {id: updateTrData.id,},field='';
				if(inputField == 'materiel')
					field = 'materielId';
				data[field] = val;
				myutil.saveAjax({
					url:'/product/updateProductMaterials',
					data: data,
				})
			})
		}
	}
	materials.render = function(opt){
		var elem = opt.elem,
			btn = opt.btn;
		$('#'+elem).html(html);	//填充真正的html内容
		mytable.render({			//生产用料表格
			elem:'#'+tableId,
			data:[],
			size:'lg',
			autoUpdate:{
				saveUrl:'/product/updateProductMaterials',
				deleUrl:'/product/deleteProductMaterials',
				field: { materiel:'materielId',unit_id:'unitId', },
			}, 
			curd:{
				addTemp:{
					materielId: '',
					oneMaterial: '',
					unitId: allUnit[0].id,
					manualLoss: '',
					batchMaterial: '',
					batchMaterialPrice: '',
				},
				addTempAfter: renderSelectSearch,
				saveFun:function(data){
					for(var i=0;i<data.length;i++){
						var check = table.checkStatus('productTable').data;
						data[i]['productId'] = check[0].id;		//添加产品id参数
						myutil.saveAjax({
							url: '/product/addProductMaterials', 
							data: data[i],
						})
					}
					table.reload(tableId);
				}
			},
			verify:{ 
				notNull: ['materiel'],
				price:['oneMaterial','manualLoss','batchMaterial','batchMaterialPrice'] 
			},
			colsWidth:[0,0,8,6,8,8,8],
			cols:[[
			       { type:'checkbox',},
			       { title:'物料编号/名称/价格/单位',   	field:'materiel',	templet: getSelectHtml(), },
			       { title:'单只用料',   	field:'oneMaterial',	},
			       { title:'单位',   		field:'unit_id',		 type:'select', select:{ data: allUnit }  },
			       { title:'手动损耗', 		field:'manualLoss',  },
			       { title:'当批当品种用量',  field:'batchMaterial',	},
			       { title:'当批当品种价格',  field:'batchMaterialPrice',   },
			       { title:'压货环节',   	field:'overstockId', type:'select', 		select:{ data: allOverstock } ,},
			       ]],
	        done:function(){
				 renderSelectSearch();
			}
		})
		function getSelectHtml(){
			return function(r){
				var d = r.materiel;
				var html = ['<div class="layui-form-select">',
				            	'<div class="layui-select-title">',
				            		'<input type="text" class="layui-input" placeholder="请选择" value="',
				            			d ? (d.number+' ~ '+d.name+' ~ ￥'+d.price+' ~ '+d.unit) : '',
				            		'">',
				            		'<i class="layui-edge"></i>',
				            	'</div>',
				            '</div>',
				            ].join(' ');
				return html;
			}
		};
		$('#'+btn).on('click',function(){	//绑定按钮点击事件
			var check = table.checkStatus('productTable').data;
			if(check.length<1)
				return myutil.emsg('请选择相应的商品');
			if(check.length>1)
				return myutil.emsg('不能同时选择多个商品');
			table.cache[tableId] && table.reload(tableId,{
				url: myutil.config.ctx+'/product/getProductMaterials?productId='+check[0].id,
				page: { curr:1 }
			})
			$('#'+elem).siblings().hide();
			$('#'+elem).show();
		})
	}
	exports('materials',materials)
})