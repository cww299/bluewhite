/* author:299
 * 2019/8/29   试制模块：dd除裁片、生产用料
 * materials.render({ elem:'给定的元素。填充真正的内容', btn:'绑定的按钮' })
 */
layui.define(['mytable','form'],function(exports){
	var $ = layui.jquery,
		mytable = layui.mytable,
		table = layui.table,
		form = layui.form,
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
	var allOverstock = myutil.getDataSync({ url: myutil.config.ctx+'/product/getBaseOne?type=overstock', });
	var allMaterielSelect = '';
	var materials = {	//模块
			
	};
	myutil.getData({ 
		url: myutil.config.ctx+'/product/getMateriel?materielTypeId=321',
		success:function(data){
			layui.each(data,function(index,item){
				allMaterielSelect += '<dd data-value="'+item.id+'" data-convertPrice="'+item.convertPrice+'" data-convertUnit="'+item.convertPrice+'">'+
						item.number+' ~ '+item.name+' ~ ￥'+item.price+' ~ '+(item.unit && item.unit.name )+'</dd>';
			})
		}
	});
	var tableId = 'materialsTable';
	var noneHtml = '<dd style="color:#999;">没有搜索到匹配的相关信息</dd>';
	
	
	var trIndex = '';			//记录获取焦点的输入框的索引
	var trMateriel = { };   	//记录各行的materiel实体、用于单位转换下拉框改变时使用
	var inputText = '';
	
	
	
	var updateTrData = { }, inputElem = null, inputText = '',inputField='';	//用于记录所点击的下拉框的值、修改的当行id、输入框元素。用于修改、回滚等
	
	
	var renderSelectSearch = function(){		//自定义下拉框搜索
		layui.each($('td[data-field="convertUnit"]').find('select'),function(index,item){
			var index = $(item).closest('tr').data('index');
			if(!trMateriel[index] || !trMateriel[index].convertUnit){
				$(item).prop('disabled','disabled');
			}
		})
		form.render();
		layui.each($('td[data-field="materiel"]').find('.layui-form-select').find('input'),function(index,item){	//遍历表格物料名称下拉框
			$(item).unbind().focus(function(event){
				$(this).parent().parent().addClass('layui-form-selected');
				inputText = $(this).val();
				trIndex = $(this).closest('tr').data('index');
				var width = $(this).parent().width();			
				var tdElem = $(this).closest('td');
				var val = $(this).val();
				var Y = tdElem.offset().top;
				var X = tdElem.offset().left;
				getSearchMateriael(val);
				$('#searchTipDiv').css("top",Y+45);	//定位搜索提示框位置并显示提示框
				$('#searchTipDiv').css("left",X+15);
				$('#searchTipDiv').css("width",width);
				$('#searchTipDiv').show();
			}).blur(function(obj){
				var self = this;
				setTimeout(function (obj) {
					$('#searchTipDiv').hide();
					$(self).parent().parent().removeClass('layui-form-selected');
					$(self).val(inputText);
			    }, 100);
			}).bind("input propertychange",function(event){		//监听输入框内容改变
				getSearchMateriael($(this).val());
			});
		})
	}
	function getSearchMateriael(name){	//根据输入的内容进行搜索、填充选择项
		name = name.split('~')[1]?name.split('~')[1].trim():name.trim();
		var html = '';
		if(!name){
			html = allMaterielSelect;
			renderHtml();
		}
		else
			myutil.getData({
				url: myutil.config.ctx+'/product/getMateriel?materielTypeId=321',
				data:{ name: name },
				success:function(data){
					if(data.length==0)
						html = noneHtml;
					layui.each(data,function(index,item){
						var sty= '';
						if(trMateriel[trIndex] && trMateriel[trIndex].id == item.id)
							sty= 'style="background-color:#5fb878;"';
						html += '<dd data-value="'+item.id+'" data-convertPrice="'+item.convertPrice+'" '+sty+' data-convertUnit="'+item.convertUnit+'" >'+
								item.number+' ~ '+item.name+' ~ ￥'+item.price+' ~ '+(item.unit && item.unit.name )+'</dd>';
					})
					renderHtml();
				}
			})
		function renderHtml(){
			$('#searchTipDiv').html(html);
			$('#searchTipDiv').find('dd').on('click',function(obj){		//监听选择事件、如果选中某一个选项
				var text = $(this).html().split('~');
				var val = $(this).data('value');
				var convertPrice = $(this).data('convertprice');
				var convertUnit = $(this).data('convertunit');
				if(!val)
					return;
				trMateriel[trIndex] = {	//记录更新当行的materiel实体
					id: val,
					number: text[0].trim(),
					name: text[1].trim(),
					price: text[2].trim().split('￥')[1],
					unit: text[3].trim(),
					convertPrice: convertPrice,
					convertUnit: convertUnit,
				};
				var select = $('tr[data-index="'+trIndex+'"').find('td[data-field="convertUnit"]').find('select');
				if(convertPrice){
					select.removeAttr('disabled');
				}else{
					select.val(0);
					select.prop('disabled','disabled');
				}
				form.render();
				var trData = table.cache[tableId][trIndex];
				inputText = $(this).html();		//修改输入框的值(在失去焦点的时候获取该值)
				trData['materielId'] = val;		//修改缓存值
				if(!trData.id)
					return;
				var data = {id: trData.id, materielId: val };
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
				field: { materiel:'materielId', overstock_id:'overstockId'},
			}, 
			curd:{
				addTemp:{
					materielId: '',
					convertUnit:0,
					oneMaterial: '',
					manualLoss: '',
					batchMaterial: '',
					batchMaterialPrice: '',
					overstockId: allOverstock[0].id,
				},
				addTempAfter: renderSelectSearch,
				saveFun:function(data){
					var successNum = 0;
					for(var i=0;i<data.length;i++){
						var check = table.checkStatus('productTable').data;
						data[i]['productId'] = check[0].id;		//添加产品id参数
						myutil.saveAjax({
							url: '/product/addProductMaterials', 
							data: data[i],
							success:function(){
								successNum++;
							}
						})
					}
					if(successNum == data.length)
						table.reload(tableId);
				}
			},
			verify:{ 
				notNull: ['materiel'],
				price:['oneMaterial','manualLoss','batchMaterial','batchMaterialPrice'] 
			},
			colsWidth:[0,0,8,6,8,8,8,15],
			cols:[[
			       { type:'checkbox',},
			       { title:'物料编号/名称/价格/单位',   	field:'materiel',	templet: getSelectHtml(), edit:false, },
			       { title:'是否转换',   	field:'convertUnit', type:'select',
			    	   select:{ data: [{id:0,name:'不转换'},{id:1,name:'转换'}],layFilter:'convertUnitSelect',unsearch:true, } ,},
			       { title:'单只用料',   	field:'oneMaterial',	edit:true, },
			       { title:'手动损耗', 		field:'manualLoss',  	edit:true, },
			       { title:'当批当品种用量',  field:'batchMaterial',	edit:true, },
			       { title:'当批当品种价格',  field:'batchMaterialPrice',   edit:true, },
			       { title:'压货环节',   	field:'overstock_id', type:'select', select:{ data: allOverstock } ,},
			       ]],
	        done:function(){
	        	 trMateriel = {};
				 layui.each(table.cache[tableId],function(index,item){
					 trMateriel[index] = item.materiel;
				 })
				 renderSelectSearch();
				 form.on('select(convertUnitSelect)',function(obj){
					 var index = $(obj.elem).closest('tr').data('index');
					 var t = trMateriel[index];
					 var trData = table.cache[tableId][index];
					 var convertPrice = t.convertPrice;
					 var convertUnit = t.convertUnit;
					 if(!convertPrice){
						 $(obj.elem).val(0);
						 form.render();
						 return myutil.emsg('该物料无单位转换');
					 }else{
						 if(obj.value==0){
							 convertPrice = t.price;
							 convertUnit = t.unit;
						 }
						 var text = t.number+' ~ '+t.name+' ~ ￥'+convertPrice+' ~ '+convertUnit;
						 $('tr[data-index="'+index+'"]').find('td[data-field="materiel"]').find('input').val(text);
						 if(trData.id){
							 myutil.saveAjax({
									url:'/product/updateProductMaterials',
									data: { id:trData.id, convertUnit:obj.value },
							})
						 }
					 }
				 })
			}
		})
		function getSelectHtml(){
			return function(r){
				var d = r.materiel;
				var html = ['<div class="layui-form-select">',
				            	'<div class="layui-select-title">',
				            		'<input type="text" class="layui-input" placeholder="请选择" value="',
				            			d ? (d.number+' ~ '+d.name+' ~ ￥'+d.price+' ~ '+(d.unit && d.unit.name )) : '',
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
			$('#'+btn).css('color','red');
			$('#'+btn).siblings().css('color','white');
			$('#'+elem).siblings().hide();
			$('#'+elem).show();
		})
	}
	exports('materials',materials)
})