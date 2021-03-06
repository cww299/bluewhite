/* author:299
 * 2019/8/29   试制模块：dd除裁片、生产用料
 * materials.render({ elem:'给定的元素。填充真正的内容', btn:'绑定的按钮' })
 */
layui.define(['mytable','form','chooseMate', 'upload'],function(exports){
	var $ = layui.jquery,
		chooseMate = layui.chooseMate,
		mytable = layui.mytable,
		table = layui.table,
		upload = layui.upload,
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
	            	'<td><button type="button" class="layui-btn" lay-submit lay-filter="">查找</button></td>',
	            	'<td>&nbsp;&nbsp;</td>',
	            '</tr></table>',
	            '<span style="display:none;" id="uploadMater">导入</span>',
	            '<table id="materialsTable" lay-filter="materialsTable"></table>'
	            ].join(' ');
	var allUnit = myutil.getDataSync({ url: myutil.config.ctx+'/product/getBaseOne?type=unit', });		//获取所有单位
	var allOverstock = myutil.getDataSync({ url: myutil.config.ctx+'/product/getBaseOne?type=overstock', });
	var allMaterielSelect = '';
	var materials = {	//模块
			
	};
	const tableId = 'materialsTable';
	
	const uploadData = {
		productId: null,
	}
	var load;
	
	materials.render = function(opt){
		var elem = opt.elem,
			btn = opt.btn;
		$('#'+elem).html(html);	//填充真正的html内容
		
		upload.render({
			elem: '#uploadMater',
			url: myutil.config.ctx + '/product/uploadProductMaterials',
    		data: uploadData,
    		before: function(){
    			load = layer.load(1,{ shade: 0.5 })
    		},
			done: function(res, index, upload){ //上传后的回调
				var tips = 'emsg'
				if(res.code == 0){
					tips = 'smsg';
					table.reload(tableId)
				}
		    	myutil[tips](res.message)
		    	layer.close(load)
			},
			error: function(){
				layer.close(load)
				myutil.esmg("接口出现异常，请联系管理员")
			},
			accept: 'file',
			exts: 'xls|xlsx',
		})
		
		mytable.render({			//生产用料表格
			elem:'#'+tableId,
			data:[],
			size:'lg',
			autoUpdate:{
				isReload: true,
				saveUrl:'/product/updateProductMaterials',
				deleUrl:'/product/deleteProductMaterials',
				field: { materiel:'materielId', overstock_id:'overstockId',unit_id:'unitId'},
			}, 
			scrollX : true,
			toolbar: [
				'<span class="layui-btn layui-btn-sm layui-btn" lay-event="upload">导入数据</span>'
			].join(' '),
			curd:{
				otherBtn: function(obj) {
					if(obj.event == 'upload') {
						var check = table.checkStatus('productTable').data;
						uploadData.productId = check[0].id;
						$('#uploadMater').click();
					}
				},
				addTemp:{
					materielId: '',
					oneMaterial: '',
					manualLoss: 0.03,
					batchMaterial: '',
					batchMaterialPrice: '',
					overstockId: allOverstock[0].id,
					unitId: allUnit[0].id,
				},
				saveFun:function(data){
					for(var i=0;i<data.length;i++){
						var check = table.checkStatus('productTable').data;
						data[i]['productId'] = check[0].id;		//添加产品id参数
						myutil.saveAjax({
							url: '/product/addProductMaterials', 
							data: data[i],
							success:function(){
								table.reload(tableId);
							}
						})
					}
				},
				addTempAfter: renderChoose,
			},
			verify:{ 
				notNull: ['materielId'],
				price:['oneMaterial','manualLoss','batchMaterial','batchMaterialPrice'] 
			},
			colsWidth:[0,0,6,6,8,8,15],
			cols:[[
			       { type:'checkbox',},
			       { title:'物料编号/名称/价格/单位',   	field:'materielId',	templet: getSelectHtml(), edit:false, },
			       { title:'单位',   		field:'unit_id',		 type:'select', select:{ data: allUnit }  },
			       { title:'单只用料',   	field:'oneMaterial',	edit:true, },
			       { title:'转换单位用量',   	field:'',	edit: false, templet: getConvert(), },
			       { title:'手动损耗', 		field:'manualLoss',  	edit:true, },
			       { title:'压货环节',   	field:'overstock_id', type:'select', select:{ data: allOverstock } ,},
			       ]],
	        done:function(){
	        	renderChoose()
			}
		})
		
		function getConvert() {
			return function(d) {
				var html = '---'
				const mate = d.materiel
				if(mate && mate.convertUnit) {
					if(d.unit.id == mate.unit.id) {	// 如果当前单位是转化前的单位，这里输出转化后的单位
						html = (d.oneMaterial * mate.converts).toFixed(2) + '/' + mate.convertUnit.name
					} else if(d.unit.id == mate.convertUnit.id) {
						html = (d.oneMaterial / mate.converts).toFixed(2) + '/' + mate.unit.name
					}
				}
				return html
			}
		}
		
		function renderChoose() {
			$('div[lay-id="'+ tableId +'"] .choose').unbind().on('click', function(){
				 var index = $(this).closest('tr').data("index")
				 var field = $(this).closest('td').data("field")
				 var isMate = $(this).data('ismate') 
				 var trData = table.cache[tableId][index]
				 chooseMate.choose(false).then(d => {
					 $(this).val(d.number+' ~ '+d.name+' ~ '+(d.unit && d.unit.name))
					 trData[field] = d.id
					 if(!trData.id)
						 return;
					 var data = { id: trData.id }
					 data[field] = d.id
					 myutil.saveAjax({
						 url:'/product/updateProductMaterials',
						 data: data,
					 })
				 })
			 })
			 $('div[lay-id="'+ tableId +'"] .closeChoose').unbind().on('click', function(){
				 var index = $(this).closest('tr').data("index")
				 var field = $(this).closest('td').data("field")
				 var trData = table.cache[tableId][index]
				 if(trData.id){
					myutil.emsg('无法删除') 
				 } else {
					 $(this).prev().val('')
					 trData[field] = ''
				 }
			 })
		}
		
		function getSelectHtml(){
			return function(r){
				var d = r.materiel;
				var html = [
            		'<input type="text" class="layui-input choose" placeholder="请选择"  readonly ',
        			'value="'+ (d ? (d.number+' ~ '+d.name+' ~ '+(d.unit && d.unit.name)) : '') +'">',
            		'<i class="layui-icon layui-icon-add-circle closeChoose"></i>'
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