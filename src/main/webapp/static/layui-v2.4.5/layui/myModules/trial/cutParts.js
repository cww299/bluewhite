/* author:299
 * 2019/8/28   试制模块：裁片
 * cutParts.render({ elem:'给定的元素。填充真正的内容', btn:'绑定的按钮' })
 */
layui.extend({
	mytable: 'layui/myModules/mytable',
}).define(['mytable'],function(exports){
	var $ = layui.jquery,
		mytable = layui.mytable,
		table = layui.table,
		myutil = layui.myutil;
	var html = [
	            '<table id="cutPartTable" lay-filter="cutPartTable"></table>'
	            ].join(' ');
	var allUnit = myutil.getDataSync({ url:'/product/getBaseOne?type=unit', });
	var allOverstock = myutil.getDataSync({ url:'/product/getBaseOne?type=overstock', });
	var cutParts = {	//模块
			
	};
	cutParts.render = function(opt){
		var elem = opt.elem,
			btn = opt.btn;
		$('#'+elem).html(html);	//填充真正的html内容
		var tableId = 'cutPartTable';
		var noneHtml = '<dd style="color:#999;">无数据</dd>';
		var updateTrData = { }, inputElem = null;
		var renderSelectSearch = function(){		//自定义下拉框搜索
			 layui.each($('td[data-field="materielId"]').find('.layui-form-select'),function(index,item){	//遍历表格物料名称下拉框
				 $(item).on('click',function(event){
					 layui.stope(event);					//阻止事件冒泡、去除下拉框原本的点击事件、阻止点击事件自动隐藏
					 var width = $(this).width();			
					 var tdElem = $(this).closest('td');
					 var val = $(this).find('input').val();
					 var X = tdElem.offset().top;
					 var Y = tdElem.offset().left;
					 $('#searchTipDiv').css("top",X+45);	//定位搜索提示框位置并显示提示框
					 $('#searchTipDiv').css("left",Y+15);
					 $('#searchTipDiv').css("width",width);
					 $('#searchTipDiv').show();
					 getSearchMateriael(val);				//获取初始搜索值
					 var i = $(this).closest('tr').data('index');
					 var trData = layui.table.cache[tableId][i];
					 updateTrData = trData;				//记录点击的当行数据和输入框、用于修改和修改成功后修改相应的输入框值
					 inputElem = $(this).find('input');
				 })
			 })
			 layui.each($('td[data-field="materielId"]').find('.layui-form-select').find('input'),function(index,item){
				 $(item).bind("input propertychange",function(event){	//监听输入框内容改变
					 getSearchMateriael($(this).val());
				 });
			 })
			 $(document).on('click',function(obj){		//监听其他点击事件、用于隐藏提示框
				 if($(obj).closest('#searchTipDiv').length==0){
					 $('#searchTipDiv').hide();
				 } 
			 })
		}
		mytable.render({			//裁片表格
			elem:'#'+tableId,
			data:[],
			size:'lg',
			autoUpdate:{
				saveUrl:'/product/updateCutParts',
				deleUrl:'/product/deleteCutParts',
			}, 
			curd:{
				addTemp:{
					id:'',
					cutPartsName:'',
					cutPartsNumber:1,
					perimeter:'',
					allPerimeter:'',
					materielId:'',
					oneMaterial:'',
					unitId: allUnit[0].id,
					scaleMaterial:'',
					addMaterial:'',
					manualLoss:'',
					productCost:'',
					productRemark:'',
					batchMaterial:'',
					batchMaterialPrice:'',
					composite: '',
					overstockId:allOverstock[0].id,
				},
				addTempAfter: renderSelectSearch,
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
			verify:{ 
				count:['cutPartsNumber',], 
				price:['perimeter','oneMaterial','manualLoss',], 
				notNull:['cutPartsName','materielId','perimeter','oneMaterial','manualLoss','cutPartsNumber','scaleMaterial'],
			},
			colsWidth:[0,6,6,6,5,20,6,6,6,6,6,6,6,7,6,7,7,20,7,7,7,7,7,7],
			cols:[[
			       { type:'checkbox', 		fixed:'left'},
			       { title:'裁片名字',   	field:'cutPartsName',	},
			       { title:'使用片数',   	field:'cutPartsNumber',},
			       { title:'单片周长',   	field:'perimeter',	},
			       { title:'总周长',   		field:'allPerimeter',	edit:false, },
			       { title:'物料编号/名称',  field:'materielId',  type:'select', select:{data:[] }  },
			       { title:'单片用料',   	field:'oneMaterial',	},
			       { title:'单位',   		field:'unitId',		 type:'select', select:{ data: allUnit }  },
			       { title:'用料占比',   	field:'scaleMaterial',  },
			       { title:'总用料',   		field:'addMaterial',  },
			       { title:'手动损耗', 		field:'manualLoss',  },
			       { title:'单价',   		field:'productCost',  },
			       { title:'备注',  	 		field:'productRemark',  },
			       { title:'当批单片用料',   field:'batchMaterial',  },
			       { title:'当批单片价格',   field:'batchMaterialPrice',  },
			       { title:'是否复合',   	field:'composite',	},
			       { title:'请选择复合物',   field:'complexMateriel',	},
			       { title:'是否双层对复',   field:'',	},
			       { title:'手动耗损（必填）',   field:'',	},
			       { title:'当批复合物用料',   	field:'',	},
			       { title:'当批复合物单片价格', field:'',	},
			       { title:'当批复合物加工费',   field:'',	},
			       { title:'压货环节',   	field:'overstockId', type:'select', select:{ data: allOverstock } ,},
			       ]],
			 done:function(){
				 renderSelectSearch();
			 }
		})
		function getSearchMateriael(name){	//根据输入的内容进行搜索、填充选择项
			myutil.getData({
				url:'/product/getMateriel?name='+name,
				success:function(data){
					var html = '';
					if(data.length==0)
						html = noneHtml;
					layui.each(data,function(index,item){
						html += '<dd data-value="'+item.id+'">'+item.name+'</dd>';
					})
					$('#searchTipDiv').html(html);
					$('#searchTipDiv').find('dd').on('click',function(obj){		//监听选择事件、如果选中某一个选项
						var text = $(this).html();
						var val = $(this).data('value');
						if(!val)
							return;
						$(inputElem).attr('placeholder',text);		//修改下拉框显示的值、缓存值
						updateTrData['materielId'] = val;
						if(!updateTrData.id)
							return;
						myutil.saveAjax({
							url:'/product/updateCutParts',
							data: {
								id: updateTrData.id,
								materielId: val,
							},
						})
					 })
				}
			})
		}
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
	exports('cutParts',cutParts)
})