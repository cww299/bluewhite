/* author:299
 * 2019/8/28   试制模块：裁片
 * cutParts.render({ elem:'给定的元素。填充真正的内容', btn:'绑定的按钮' })
 */
layui.define(['mytable', 'chooseMate', 'upload'],function(exports){
	var $ = layui.jquery,
		mytable = layui.mytable,
		upload = layui.upload,
		chooseMate = layui.chooseMate,
		table = layui.table,
		myutil = layui.myutil;
	var html = [
				'<span style="display:none;" id="uploadCut">导入</span>',
	            '<table id="cutPartTable" lay-filter="cutPartTable"></table>',
	            ].join(' ');
	var allUnit = myutil.getDataSync({ url: myutil.config.ctx+'/product/getBaseOne?type=unit', });		//获取所有单位
	var allOverstock = myutil.getDataSync({ url: myutil.config.ctx+'/product/getBaseOne?type=overstock', }); //获取所有压货
	var cutParts = {	//模块
			
	};
	//Id:321为面料、322为辅料 、323为填充物、 324为复合样、325人工、326设计
	
	const tableId = 'cutPartTable';
	
	const uploadData = {
		productId: null,
	}
	var load;
	cutParts.render = function(opt){
		var elem = opt.elem,
			btn = opt.btn;
		$('#'+elem).html(html);	//填充真正的html内容
		
		upload.render({
			elem: '#uploadCut',
			url: myutil.config.ctx + '/product/uploadCutParts',
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
		
		var sty = "background-color:#FF9800;";
		mytable.render({		//裁片表格
			elem:'#'+tableId,
			data:[],
			size:'lg',
			totalRowToFixed: 4,
			scrollX : true,
			autoUpdate:{
				isReload: true,
				saveUrl:'/product/updateCutParts',
				deleUrl:'/product/deleteCutParts',
				field:{ unit_id:'unitId', materiel:'materielId',complexMateriel:'complexMaterielId',overstock_id:'overstockId' }
			}, 
			toolbar: [
				'<span class="layui-btn layui-btn-sm layui-btn" lay-event="upload">导入数据</span>'
			].join(' '),
			curd:{
				otherBtn: function(obj) {
					if(obj.event == 'upload') {
						var check = table.checkStatus('productTable').data;
						uploadData.productId = check[0].id;
						$('#uploadCut').click();
					}
				},
				addTemp:{
					id:'',
					cutPartsName: '',
					cutPartsNumber: 1,
					perimeter: '',
					materielId: '',
					oneMaterial: '',
					allPerimeter:'',
					scaleMaterial: '',
					addMaterial: '',
					doubleComposite: 0,
					manualLoss: 0.03,
					compositeManualLoss: 0.03,
					unit: { id: 154, },
					unitId: 154,  // allUnit[0].id,
					overstockId: allOverstock[0].id,
				},
				saveFun:function(data){
					for(var i=0;i<data.length;i++){
						var check = table.checkStatus('productTable').data;
						data[i]['productId'] = check[0].id;		//添加产品id参数
						delete data[i].unit
						myutil.saveAjax({
							url: '/product/addCutParts', 
							data: data[i],
							success: function(){
								table.reload(tableId);
							}
						})
					}
				},
				addTempAfter: renderChoose,
			},
			verify:{ 
				count:['cutPartsNumber',], 
				price:['perimeter','oneMaterial','manualLoss','compositeManualLoss',], 
				notNull:['cutPartsName','materielId','perimeter','oneMaterial','manualLoss','cutPartsNumber',],
			},
			colsWidth:[0,6,6,6,5,30,6,6,6,6,6,30,7,7,10],
			totalRow: ['allPerimeter', 'addMaterial'],
			cols:[[
			       { type:'checkbox', 		fixed:'left'},
			       { title:'裁片名字',   	field:'cutPartsName',	edit:true, fixed:'left'},
			       { title:'使用片数',   	field:'cutPartsNumber', edit:true, },
			       { title:'单片周长',   	field:'perimeter',		edit:true, },
			       { title:'总周长',   		field:'allPerimeter',	edit:false, style:sty  },
			       { title:'物料编号/名称/价格/单位',  field:'materielId',  templet: getSelectHtml(), edit:false, },
			       { title:'单片用料',   	field:'oneMaterial',	edit:true,},
			       { title:'单位',   		field:'unit_id',		 type:'select', select:{ data: allUnit }  },
			       { title:'用料占比',   	field:'scaleMaterial',  edit:false, style:sty },
			       { title:'总用料',   		field:'addMaterial',  	edit:false, style:sty },
			       { title:'手动损耗', 		field:'manualLoss',  	edit:true,},
			       { title:'请选择复合物',   field:'complexMaterielId',templet: getSelectHtml('complexMateriel'),edit:false,	},
			       { title:'是否双层对复',   field:'doubleComposite',type:'select', select:{ data: [{name:'否',id:0},{name:'是',id:1},] } 	},
			       { title:'手动耗损',   field:'compositeManualLoss',			edit:true, },
			       { title:'压货环节',   	field:'overstock_id', type:'select', 		select:{ data: allOverstock } ,},
			       ]],
			 done:function(r, data){
				 renderChoose()
				 console.log(r.data)
			 }
		})
		
		function renderChoose() {
			$('div[lay-id="'+ tableId +'"] .choose').unbind().on('click', function(){
				 var index = $(this).closest('tr').data("index")
				 var field = $(this).closest('td').data("field")
				 var isMate = $(this).data('ismate') 
				 var trData = table.cache[tableId][index]
				 chooseMate.choose(isMate).then(d => {
					 $(this).val(d.number+' ~ '+d.name+' ~ '+(d.unit && d.unit.name))
					 trData[field] = d.id
					 if(!trData.id)
						 return;
					 var data = { id: trData.id }
					 data[field] = d.id
					 myutil.saveAjax({
						 url:'/product/updateCutParts',
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
		
		function getSelectHtml(type){
			return function(r){
				var d = type ? r.complexMateriel : r.materiel ;
				var html = [
	            		'<input type="text" class="layui-input choose" placeholder="请选择"  readonly ',
            			'value="'+ (d ? (d.number+' ~ '+d.name+' ~ '+(d.unit && d.unit.name)) : '') +'"',
            			'data-ismate="'+ (!type) +'">',
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
				url: myutil.config.ctx+'/product/getCutParts?productId='+check[0].id,
				page: { curr:1 }
			})
			$('#'+btn).css('color','red');
			$('#'+btn).siblings().css('color','white');
			$('#'+elem).siblings().hide();
			$('#'+elem).show();
		})
	}
	exports('cutParts',cutParts)
})