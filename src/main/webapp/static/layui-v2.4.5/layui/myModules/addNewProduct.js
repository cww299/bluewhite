/* 2019/08/10
 * author: 299
 * 新增商品模块
 * addNew.render({ elem:'按钮'，success:function(){ 成功函数的回调 }  }) 绑定新增按钮
 * addNew.update({ elem:'绑定的按钮',table:'获取修改数据的回调',success:function(){ 成功函数的回调 } })
 * !!!如果绑定的elem是工具栏按钮，则addNew.render应该写在表格done的函数中，否则表格重载会对之前工具栏按钮的绑定事件失效!!
 */
layui.extend({
	myutil: 'layui/myModules/myutil',
}).define(['jquery','layer','form','myutil','laytpl','table'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		table = layui.table,
	    myutil = layui.myutil,
	    laytpl = layui.laytpl;
	var CHOOSEDTABLE = 'choosedTable';  //选择商品表格
	var ADDTPL = [  '<div class="layui-form layui-form-pane" style="padding:20px;" id="addEditDiv">',	//新增修改模板
					'<input type="hidden" name="id" value="{{d.id}}">',
					'<div class="layui-item">',
						'<label class="layui-form-label">商品编号</label>',
						'<div class="layui-input-block">',
							'<input type="hidden" name="number" id="addEditNumber">',
							'<input type="hidden" name="productId" id="addEditProductId">',
							'<input type="text" class="layui-input" id="choosedProductBtn" value="点击选择商品" name="skuCode" readonly>',
						'</div>',
					'</div>',
					'<div class="layui-item">',
						'<label class="layui-form-label">1688价/元</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="oseePrice" value="{{d.oseePrice==null?"":d.oseePrice}}">',
						'</div>',
					'</div>',
					'<div class="layui-item">',
						'<label class="layui-form-label">天猫价/元</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="tianmaoPrice" value="{{d.tianmaoPrice==null?"":d.tianmaoPrice}}">',
						'</div>',
					'</div>',
					'<div class="layui-item">',
						'<label class="layui-form-label">线下价/元</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="offlinePrice" value="{{d.offlinePrice==null?"":d.offlinePrice}}">',
						'</div>',
					'</div>',
					'<div class="layui-item">',
						'<label class="layui-form-label">商品重量/g</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="weight" value="{{d.weight==null?"":d.weight}}">',
						'</div>',
					'</div>',
					'<div class="layui-item">',
						'<label class="layui-form-label">商品高度/cm</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="size" value="{{d.size==null?"":d.size}}">',
						'</div>',
					'</div>',
					'<div class="layui-item">',
						'<label class="layui-form-label">成本/元</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="cost" value="{{d.cost==null?"":d.cost}}">',
						'</div>',
					'</div>',
					'<div class="layui-item">',
						'<label class="layui-form-label">广宣成本/元</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="propagandaCost" value="{{d.propagandaCost==null?"":d.propagandaCost}}">',
						'</div>',
					'</div>',
					'<div class="layui-item">',
						'<label class="layui-form-label">材质</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="material" value="{{d.material}}">',
						'</div>',
					'</div>',
					'<div class="layui-item">',
						'<label class="layui-form-label">填充物</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="fillers" value="{{d.fillers}}">',
						'</div>',
					'</div>',
					'<div class="layui-item">',
						'<label class="layui-form-label">备注</label>',
						'<div class="layui-input-block">',
							'<input class="layui-input" name="remark" value="{{d.remark}}">',
						'</div>',
					'</div>',
					'<p><button lay-submit lay-filter="addEditSureBtn" id="addEditSureBtn" style="display:none">确定</button></p>',
					'</div>',
	              ].join(' ');
	var CHOOSEDPRODUCT = [	'<div id="choosedProductDiv" style="padding:10px;">',	//商品选择模板
								'<table class="layui-form">',
									'<tr>',
										'<td>商品名称：</td>',
										'<td><input class="layui-input" type="text" name="name"></td>',
										'<td>&nbsp;&nbsp;</td>',
										'<td>商品编号：</td>',
										'<td><input class="layui-input" type="text" name="number"></td>',
										'<td>&nbsp;&nbsp;</td>',
										'<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="searchProduct" id="searchBtn">搜索</span></td>',
										'<td>&nbsp;&nbsp;</td>',
										'<td><span class="layui-badge">提示：双击选择</span></td>',
									'</tr>',
								'</table>',
								'<table id="'+CHOOSEDTABLE+'" lay-filter="'+CHOOSEDTABLE+'"></table>',
							'</div>',
	                      ].join(' ');
	var addNew = { },	//模块对象
		choosedWin = '', addEditWin = '';	//新增修改弹窗、选择商品弹窗
	
	addNew.render = function(opt){
		if(!opt.elem)
			return console.error('请给定绑定新增商品的elem');
		$(opt.elem).unbind().on('click',function(){
			var data={id:'',skuCode:'',weight:'',size:'',material:'',fillers:'',cost:'',propagandaCost:'',remark:'',tianmaoPrice:'',oseePrice:'',offlinePrice:''};
			opt.title = '新增商品';
			var html='';
			laytpl(ADDTPL).render(data,function(h){ html=h; })
			openAddEditWin(html,opt);
		});
	};
	addNew.update = function(opt){
		if(!opt.elem)
			return console.error('请给定绑定新增商品的elem');
		$(opt.elem).unbind().on('click',function(){
			var data = table.checkStatus(opt.table).data, msg ='';
			data.length<1 && (msg='请选择商品修改');
			data.length>1 && (msg='不能同时修改多个商品');
			if(data.length!=1)
				return myutil.emsg(msg);
			opt.title = '修改商品';
			var html='';
			laytpl(ADDTPL).render(data[0],function(h){ html=h; })
			openAddEditWin(html,opt,data[0]);
		})
	}
	
	function openAddEditWin(html,opt,data){
		addEditWin=layer.open({
			type : 1,
			title : opt.title,
			offset:'100px',
			btn: ['确定','取消'],
			area : ['40%','75%'],
			content : html,
			success: function(){
				if(data){	//修改时进行数据回显
					$("#addEditNumber").val(data.number);
					$("#choosedProductBtn").val(data.skuCode);
					$("#addEditProductId").val(data.productId);
				}
				$('#choosedProductBtn').unbind().on('click',function(){ 
					choosedWin = layer.open({
						title: '选择产品',
						type:1,
						offset:'120px;',
						area:['50%','70%'],
						content: CHOOSEDPRODUCT,
						success:function(){
							table.render({
								elem: '#'+CHOOSEDTABLE,
								page: true,
								loading: false,
								url: myutil.config.ctx+'/productPages',
								request:{ pageName:'page', limitName:'size' },
								parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total, msg:ret.message, code:ret.code } },
								cols:[[
										{ align:'center', title:'产品编号',	field:'number',	},
										{ align:'center', title:'产品名称',	field:'name',	},
								       ]],
							})
							table.on('rowDouble('+CHOOSEDTABLE+')',function(obj){
								$("#addEditNumber").val(obj.data.number);
								$("#addEditProductId").val(obj.data.id);
								$("#choosedProductBtn").val(obj.data.name);
								layer.close(choosedWin);
							})
							form.on('submit(searchProduct)',function(obj){
								table.reload('choosedTable',{
									where: obj.field,
									page: { curr:1 },
								})
							})
						}
					})
				})
				form.render();
			},
			yes: function(){
				$('#addEditSureBtn').click();
			}
		})
		form.render();
		form.on('submit(addEditSureBtn)',function(obj){
			if(obj.field.number=='')
				return myutil.emsg('请选择商品');
			myutil.saveAjax({
				url:'/inventory/addCommodity',
				data: obj.field,
				success:function(result){
					opt.success && (opt.success());
					layer.close(addEditWin);
				}
			})
		})
	}
	exports('addNew',addNew);
})