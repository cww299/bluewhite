/* 产品总汇模块			//未使用！！！
 * 2019/10/8
 * author: cww
 * 使用方法：引入模块、渲染
 * product.render({
 * 	type:'',  //引用类型
 *  ctx:'',	  //前缀路径
 *  elem:'',  //绑定元素
 * })
 * type: 一楼质检1，一楼包装2，二楼针工3，二楼机工4，八号裁剪5
 * 
 */
layui.config({
	base: 'static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
}).define(['jquery','table','form','mytable','laytpl','laydate','upload'],function(exports){
	var $ = layui.jquery
	, table = layui.table 
	, form = layui.form
	, upload = layui.upload
	, myutil = layui.myutil
	, laytpl = layui.laytpl
	, laydate = layui.laydate
	, mytable = layui.mytable
	, MODNAME = 'product'
		
	, product = {
			
	}
	, Class = function(opt){
		this.render(opt);
	};
	
	var TPL = [		//主页面模板
	           '<table class="layui-form">',
	             '<tr>',
	             	'<td>&nbsp;&nbsp;</td>',
	             	'<td>产品编号:</td>',
	             	'<td><input type="text" name="departmentNumber" class="layui-input"></td>',
	             	'<td>&nbsp;&nbsp;</td>',
	             	'<td>产品名称:</td>',
	             	'<td><input type="text" name="name" class="layui-input"></td>',
	             	'<td>&nbsp;&nbsp;</td>',
	             	'<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>',
	             	'<td>&nbsp;&nbsp;</td>',
	             	'<td><span class="layui-btn layui-btn-normal" style="display:none;" id="addNewPro">新增产品</span></td>',
	             '</tr>',
	           '</table>',
	           '<table id="tableData" lay-filter="tableData"></table>',
	           ].join(' ');
	
	var ADD_PRODUCE_TPL = [	//填写工序模板
                     	 '<div style="padding:15px;" >',
                     	 	'<table id="addProduceTable" lay-filter="addProduceTable"></table>',
	                     '</div>',
	                     ].join(' ');
	
	var  ADD_BATCH_TPL= [	//填写批次模板
	                      '<div style="padding:15px;" >',
		                  	'<div class="layui-form layui-form-pane">',
		                  		'<div class="layui-form-item" pane>',
		                    		'<label class="layui-form-label">产品名</label>',
		                    		'<div class="layui-input-block">',
		                    			'<input type="text" class="layui-input" id="productName" disabled>',
				                    '</div>',
				                '</div>',
				                '<div class="layui-form-item" pane id="addSelectDiv" style="display:none;">',
		                    		'<label class="layui-form-label" id="selectText">机工选择</label>',
		                    		'<div class="layui-input-block">',
		                    			'<select id="addSelect">',
		                    				'<option value="0">二楼机工</option>',
		                    				'<option value="1">三楼机工</option>',
		                    			'</select>',
				                    '</div>',
				                '</div>',
				                '<div class="layui-form-item" pane>',
		                    		'<label class="layui-form-label">批次号</label>',
		                    		'<div class="layui-input-block">',
		                    			'<input type="text" class="layui-input" name="bacthNumber" lay-verify="required">',
				                    '</div>',
				                '</div>',
				                '<div class="layui-form-item" pane>',
			                		'<label class="layui-form-label">数量</label>',
			                		'<div class="layui-input-block">',
				                      '<input type="text" class="layui-input" name="number" lay-verify="number">',
				                    '</div>',
				                '</div>',
				                '<div class="layui-form-item" pane>',
			                		'<label class="layui-form-label">备注</label>',
			                		'<div class="layui-input-block">',
				                      '<input type="text" class="layui-input" name="remarks">',
				                    '</div>',
			                    '</div>',
				                '<div class="layui-form-item" pane>',
				                	'<label class="layui-form-label">批次时间</label>',
				                	'<div class="layui-input-block">',
				                		'<input type="text" class="layui-input" name="allotTime" id="allotTime">',
				                    '</div>',
				                '</div>',
				                '<span lay-submit lay-filter="addSure" id="addSure" style="display:none;">add</span>',
			          		 '</div>',
		                   '</div>',
	                       ].join(' ');
	
	var baseType = ['','productFristQuality','productFristPack','productTwoDeedle','productTwoMachinist','productEightTailor'];
	var baseReType = ['','','','productTwoReDeedle','productTwoReMachinist',''];
	
	Class.prototype.render = function(opt){
		myutil.clickTr();
		var usallyCols = [		//通用表头
		                  	{ type:'checkbox', },
	                  		{ title:'产品序号', field:'id', },
	                  		{ title:'产品编号', field:'number', edit:opt.type==2, },		//此两列在一楼包装2时可修改
	                  		{ title:'产品名称', field:'name',  edit:opt.type==2, },
	                  		{ title: (opt.type==5?'激光':"")+'生产单价', field:'departmentPrice', },
	                  		{ title: (opt.type==5?'激光':"")+'外发价格', field:'hairPrice', edit:opt.type>2, },	//此列在3，4，5，type时可修改
                  		];
		var needPrice = [		//二楼针工表头 type 3
	                 		{ title:'针工价格', field:'deedlePrice', },
                 		];
		var eightPrice = [		//八号裁剪表头 type 5
	                  		{ title:'冲床生产单价', field:'puncherDepartmentPrice', },
	                  		{ title:'冲床外发价格', field:'puncherHairPrice', edit:true, },
                  		];
		var cols = [];
		switch(opt.type){
		case 3:  cols = usallyCols.concat(needPrice); break;
		case 5:  cols = usallyCols.concat(eightPrice); break;
		default: cols = usallyCols; break;
		}
		var toolbar = [
		               '<span class="layui-btn layui-btn-sm" lay-event="addBatch">填写批次</span>',
		               '<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="addProduce">填写工序</span>',
		               ];
		if(opt.type==3 || opt.type==4)
			toolbar.push('<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="addReword">填写返工工序</span>')
		laytpl(TPL).render({},function(h){
			$(opt.elem).append(h);
			if(opt.type==2){
				$('#addNewPro').show();
				$('#addNewPro').click(function(obj){
					layer.open({
						type:1,
						title:'新增产品',
						
					})
				})
			}
			form.render();
		})
		mytable.render({
			elem:'#tableData',
			url: opt.ctx+'/productPages?type='+opt.type,
			limit:15,
			limits:[10,15,20,30,50],
			ifNull:0,
			autoUpdate: {
				field:{ number:'departmentNumber', },
				saveUrl:'/updateProduct?type='+opt.type,
				isReload:true,
			},
			verify:{
				price:['hairPrice','puncherHairPrice'],
			},
			curd:{
				btn:[],
				otherBtn: otherBtn(),
			},
			cols:[ cols ],
			toolbar:toolbar.join(' '),
		}) 
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field
			})
		}) 
		function otherBtn(obj){
			return function(obj){
				var checked = layui.table.checkStatus('tableData').data;
				if(checked.length==0)
					return myutil.emsg('请选择相关信息');
				if(checked.length>1)
					return myutil.esmg('只能选择一天信息进行编辑');
				switch(obj.event){
				case 'addBatch': addBatch(); break;
				case 'addProduce': addProduce(); break;
				case 'addReword': addProduce('rework'); break;
				}
				function addBatch(){
					var html = '';
					laytpl(ADD_BATCH_TPL).render({},function(h){
						html = h;
					})
					var addBatchWin = layer.open({
						type:1,
						title:'填写批次：'+checked[0].name,
						area:['40%','60%'],
						content: html,
						btnAlign:'c',
						btn:['确定','取消'],
						success:function(){
							if(opt.type==4 || opt.type==5){
								$('#addSelectDiv').show();
								if(opt.type==5){
									$('#selectText').html('裁剪方式');
									$('#addSelect').html([
									                      '<option value="0">激光</option>',
									                      '<option value="1">冲床</option>',
									                      ].join(''));
								}
							}
							laydate.render({
								elem:'#allotTime',
								type:'datetime',
							})
							$('#productName').val(checked[0].name);
							form.on('submit(addSure)',function(obj){
								obj.field.productId = checked[0].id;
								obj.field.type = opt.type;
								obj.field.flag = 0;
								if(opt.type==4)
									obj.field.machinist = $('#addSelect').val();
								else if(opt.type==5)
									obj.field.sign = $('#addSelect').val();
								myutil.saveAjax({
									url: '/bacth/addBacth',
									data: obj.field,
									success: function(){
										layer.close(addBatchWin);
									}
								})
							})
							form.render();
						},
						yes:function(){
							$('#addSure').click();
						}
					})
				}//end addBatchNumber
				function addProduce(rework){
					var title = '填写工序：',flag = 0;
					var base = baseType[opt.type];
					if(rework){
						title = '填写返工工序：';
						flag = 1;
						base = baseReType[opt.type];
					}
					var allProcedure = myutil.getDataSync({
						url:opt.ctx+'/basedata/list?type='+base,
					});
					var html = '';
					laytpl(ADD_PRODUCE_TPL).render({},function(h){
						html = h;
					})
					var addProduceWin = layer.open({
						type:1,
						title: title+checked[0].name,
						area:['60%','80%'],
						content:html,
						success:function(){
							mytable.renderNoPage({
								elem:'#addProduceTable',
								size:'lg',
								url:'/production/getProcedure?productId='+checked[0].id+'&type='+opt.type+'&flag='+flag,
								autoUpdate:{
									saveUrl:'/production/addProcedure',
									deleUrl:'/production/delete',
									field:{ procedureType_id:'procedureTypeId', },
								},
								curd:{
									addTemp:{
										flag:flag,
										type:opt.type,
										productId:checked[0].id,
										procedureTypeId: allProcedure[0].id,
										name:'',
										workingTime:'',
									},
								},
								verify:{
									count:['workingTime'],
								},
								toolbar:'<span class="layui-btn layui-btn-sm" id="export">点击导入</span>',
								cols:[[
								       { type:'checkbox', },
								       { title:'工序名称', field:'name', },
								       { title:'工序时间(秒)', field:'workingTime', },
								       { title:'工序类型', field:'procedureType_id', type:'select', 
								    	   select:{ data:allProcedure, }},
								    	   ]],
								done:function(){
									upload.render({
									    elem: '#export',
									    url: '/excel/importMachinistProcedure',
									    data:{
									    	productId: checked[0].id,
									    	flag: flag,
											type: opt.type,
									    },
									    accept: 'file',
									    done: function(res){
									      if(res.code==0)
									    	  myutil.smsg(res.message);
									      else
									    	  myutil.emsg(res.message);
									    }
									});
								}
							})
						}
					})
				}
			}
		}//end otherBtn
	}
	product.render = function(opt){
		var s = new Class(opt);
		myutil.config.ctx = opt.ctx;
	}
	
	exports(MODNAME,product);
})