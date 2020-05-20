/* 表格模块 @author：299
 * 2019/8/6
 * 主要使用方式：     使用本模块需要先在引用页面实例myutil模块，并设置${ctx}.如果不设置myutil，在设置给定url时需要给全路径即${ctx}不可省略
 * 合计行开启方式：totalRow:['需要开启合计行的字段',...]
 * 字段增加虚拟字段: cols:[[ { field:'user_userName' } ]] 对应user:{ userName: '' };
 * 增加数据转换模板: cols:[[ { field:'user_userName', transData:{data:['对应转化的值','按顺序对应0、1、2..'],text:'无值时的提示',skin:'是否开启皮肤模式 true\false'//默认开启， } ]]
 * 增加数据类型模板：cols:[[ { type:'select', select:{data:你的下拉框数据, id:'对应值',name:'显示名、虚拟字段'/[可多字段拼接],layFilter:'下拉框的lay-filter用于监听',
 * 								unsearch:true, isDisabled:true,} } ]]
 * 					cols:[[ { type:'date',  } ]]
 * 					cols:[[ { type:'dateTime', } ]]
 * 					cols:[[ { type:'price', }  ]]   修改时只能是数字，不能为空
 * 					cols:[[ { type:'count', }  ]]	修改时只能是正整数
 * 开启自动修改功能：autoUpdate:{  saveUrl:'新增的接口', deleUrl:'删除接口', updateUrl:'修改接口（如果不存在时，默认使用新增接口）',
 * 								nolyUpdateField:[],		//自动修改的字段，用于过滤可修改但需要点击保存的字段
 * 								closeMsg:关闭保存成功提示
 * 								closeLoad:关闭遮罩
 * 								field:{ 虚拟字段:'对应的上传值' },isReload：修改成功是否重载表格 ,success:成功后的回调 },   如：customer_id 对应的上传值customerId
 * 增加自动curd工具模板：curd: {
 * 							btn:[1,2,3,4],  需要显示的按钮，按顺序，默认全显
 *							addTemp:{ },  新增一行给定的默认值。不给的时候、默认为空值
 *							addTempAfter：function(),  新增一行后的后调，其他相关的监听操作等
 *							otherBtn: function(obj){} 其他扩展按钮监听事件,可增加额外的工具栏按钮直接给定<span class="layui-btn" lay-event="cleanTempData">额外按钮</span>'
 *							saveFun： function(data){}  覆盖默认的保存函数，data为临时行的值
 *							deleFun: function(ids){}  覆盖默认的删除函数
 *						},
 * 增加字段验证规则：verify:{ notNull:[ 非空的字段集合 ], price:[ 价格的字段集合 ], count:[ 数量的字段集合] ,otherVerify:function(trData){ 其他验证.返回msg }  }
 * 增加列宽度字段： colsWidth:[0,10,0,20,1,对应的各个字段宽度] 0为自适应，只填数字默认百分比。开启checkbox时，第一个数字应为0保留复选框自适应
 * 增加默认导出假字段 exportField: true, //true为关闭、默认开启
 * 增加null值时的提示：ifNull:'', //返回值为null获取''时自动转换
 * 增加自动搜索功能：searchTable:{ elem:'存放搜索表格的elem',field:{表格字段:'转成上传字段'},time:{ type:'',range:true/false,name:'',},   },
 *是否开启横向滚动：scrollX : true,
 *开启合并列 autoMerge:{			//合并复选框列时，需要增加额外样式隐藏原本的全选
 *	field:['0','',...],		//要合并的字段
 *	merge:function(data1,data2){ //合并条件，默认通过id判断
 *		if(data1.id==data2.id)
 *			return true;		//合并
 *		else
 *			return false;
 *	},
 *  evenColor:red,  //斑马纹颜色		
 *}
 *
 *智能重载  smartReloadModel:true,       layui.tablePlug.smartReload.enable(true);
 */
layui.extend({
	myutil: 'layui/myModules/myutil',
	tablePlug: 'tablePlug/tablePlug'
}).define(['jquery','layer','form','myutil','tablePlug','laydate'],function(exports){
	"use strict";
	var $ = layui.jquery
	, form = layui.form
	, layer = layui.layer
	, table = layui.table 
	, laydate = layui.laydate
	, myutil = layui.myutil
	, tablePlug = layui.tablePlug;

	var REQ = { pageName: 'page' ,limitName: 'size'};	//请求分页
	var COLOR = ['red','green','orange','cyan','blue','black','gray'];
	var TOOLTPL = [		//工具栏模板
			'<div class="layui-btn-container layui-inline">',
				'<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>',		//保留</div>，方便接入其他扩展按钮
	     ];
	var mytable = {};

	mytable.render = function(opt,ob){
		var obj = ob || { parseData:parseDataPage(), request:REQ,page:true, };
		var totalRow = opt.totalRow || [];
		var dateField = [], 	//日期字段
			dateTimeField = [], //日期时间字段
			selectLay = [], 	//下拉框对应的lay-filter，用于下拉框监听
			allField = [],		//所有的字段
			price = [], 		//价格验证
			count = [], 		//数量验证
			notNull = [], 		//非空验证
			china = [], 		//字段对应的中文名
			search = [],		//可进行搜索字段
			exportCols = [];	//导出字段
		var tableId = opt.elem.split('#')[1];
		layui.each(opt.cols,function(index1,item1){					//表头模板设置------------------------------------------------
			layui.each(item1,function(index2,item2){
				if(index2==0 && totalRow.length>0)	//开启合计行
					(item2.totalRowText = '合计');
				layui.each(totalRow,function(index,item){			
					item == item2.field && (item2.totalRow = true);
				});
				if(opt.colsWidth)			//设置列宽度
					!item2.width && opt.colsWidth[index2] && opt.colsWidth[index2]!=0 && (item2.width = (opt.colsWidth[index2]+'%'));
				item2.align = item2.align || 'center';			//行内元素默认居中
				switch(item2.type){
				case 'select': break;
				case 'date': 	 dateField.indexOf(item2.field)<0 && item2.edit && dateField.push(item2.field); (item2.edit) && (item2.edit = false);	break;
				case 'dateTime': dateTimeField.indexOf(item2.field)<0 && item2.edit && dateTimeField.push(item2.field); (item2.edit) && (item2.edit = false); break;//开启日期时间
				}
				if(item2.field){
					allField.push(item2.field);			//记录字段和对应的中文名
					china[item2.field] = item2.title;
					if(item2.field.split('_').length>1 && exportCols.indexOf(item2.field)<0 ) //记录假字段、用于导出
						exportCols.push(item2.field);
					if(item2.type=='select'){	//初始无值时bug、需要进入后记录下拉框值
						var layFilter = item2.select.layFilter || item2.field;
						if(selectLay.indexOf(layFilter)<0)
							selectLay.push(layFilter);
					}
					var tep = function(d){								//默认模板
						var fie = item2.field.split('_');				
						var res = '';
						if(fie.length==1)	//如果不是虚拟字段，则返回原先的结果
							res = d[fie[0]];
						else{
							res = d;
							layui.each(fie,function(index,item){			//循环遍历查找对应的真正值
								res = res? res[item] : null;
							})
						}
						if(item2.type && item2.type=='select'){				//开启下拉框
							return getSelectHtml(res);
						}				
						else if(item2.type && item2.type=='date'){			//开启日期
							return res?res.split(' ')[0]:(typeof(opt.ifNull)!='undefined'?opt.ifNull:'');
						}
						if(item2.transData)
							return transData(res);	//数据转换
						if(typeof(opt.ifNull)!='undefined')
							if(res===null || res==='')
								res = opt.ifNull;
						return res; 		
					};
					function transData(r){
						var text = r;
						var data = item2.transData.data || ['否','是'];
						if(data[r])											//存在转换数据时
							text = data[r];	
						else{
							text = item2.transData.text || '未知';
							r = 6;
						}
						if(!item2.transData.skin)							//开启皮肤
							text = '<span class="layui-badge layui-bg-'+COLOR[r%7]+'">'+text+'</span>';
						return text;
					}
					function getSelectHtml(r){
						!item2.select && console.warn('请给定数据填充下拉框值');
						var data = item2.select.data, id = item2.select.id || 'id', name = item2.select.name || 'name',
							layFilter = item2.select.layFilter || item2.field, unsearch = item2.select.unsearch || false,
							disabled = item2.select.isDisabled?'disabled':'';
						var html = '<select '+(unsearch?"":"lay-search")+' lay-filter="'+(layFilter)+'" '+disabled+'>';
						layui.each(data,function(index,item){
							var selected = r == item.id ? 'selected' : '';
							var text = [];
							if(typeof(name)=='object'){
								layui.each(name,function(i,t){
									var field = item;
									layui.each(t.split('_'),function(i2,t2){
										field = field[t2]?field[t2]:(field[t2]!=0?'':0);
									})
									text.push(field);
								})
								text = text.join(' ~ ');
							}else{
								if(name.indexOf('_')>-1){
									text = item;
									var arr = name.split('_');
									for(var key in arr){
										text = text[arr[key]] || '数据异常';
									}
								}else
									text = item[name];
							}
							html+='<option value="'+item[id]+'" '+selected+'>'+text+'</option>'
						})
						return html+='</select>';
					}
					(!item2.templet) && (item2.templet = tep); //设置模板
				}
			})
		})
		if(opt.autoUpdate){		//开启自动修改
			myutil.getLastData();	//开启记录表格编辑单元前的数据、用于数据回滚
			opt.autoUpdate.field = opt.autoUpdate.field || [] ;		//没有给field时，默认空
		}
		if(opt.verify){					//设置验证
			opt.verify.notNull && addVerify(opt.verify.notNull,notNull);
			opt.verify.count && addVerify(opt.verify.count,count);
			opt.verify.price && addVerify(opt.verify.price,price);
			function addVerify(data,arr){
				layui.each(data,function(index,item){
					if(arr.indexOf(item)<0) {
						arr.push(item);
					}
				})
			}
		}
		var toolbar = '';						//设置工具模板
		if(opt.curd){					//增、删、改
			if(typeof(opt.curd.btn)== 'object'){
				var t = [];
				t.push(TOOLTPL[0]);
				for(var i=0;i<opt.curd.btn.length;i++){
					if(opt.curd.btn[i]<TOOLTPL.length)
						t.push(TOOLTPL[opt.curd.btn[i]])
				}
				toolbar = t.join('');
			}
			else
				toolbar = TOOLTPL.join('');
		}
		if(opt.toolbar){		//根据传入的不同模板类型进行相应的拼接
			if(opt.toolbar.indexOf("#")>0){
				toolbar = toolbar + $(opt.toolbar).html();
			}else{
				toolbar = toolbar + opt.toolbar;
			}
		} 
		opt.toolbar = toolbar;									//设置工具栏模板
		if(opt.toolbar == '')									//如果没有的话，就不开启删除相关配置
			delete opt.toolbar;
		var done = opt.done || null;//深拷贝回调函数
		function newDone(res, curr, cou){	 		 //时间、下拉框类型的渲染。修改值时的同步缓存操作、工具栏的操作等------------------------------
			if(!opt.exportField){ //开启虚拟字段导出
				layui.each(res.data,function(index1,item1){
					layui.each(exportCols,function(index2,item2){
						var t = item1;
						layui.each(item2.split('_'),function(index3,item3){
							t = t?(t[item3] || null):null;
						})
						item1[item2] = t;
					})
				})
			}
			renderData(dateField,'date');
			renderData(dateTimeField,'datetime');
			function renderData(data,type){
				layui.each(data,function(index1,item1){	 //渲染日期
					layui.each($(opt.elem).next().find('td[data-field="'+item1+'"]'),function(index,item){
						item.children[0].onclick = function(event) { layui.stope(event) };
						laydate.render({
							elem: item.children[0],
							type: type,
							done: function(val){
								var index = $(this.elem).closest('tr').data('index');
								var trData = table.cache[tableId][index];
								var f = item1;
								if(notNull.indexOf(f)>=0 && isNull(val))	//如果该字段存在非空验证
									return myutil.emsg('时间不能为空');
								opt.autoUpdate.field[f] && ( f = opt.autoUpdate.field[f]);  //存在对应的上传值
								if(!val)
									val = "";
								else if(val.split(' ').length==1)
									val += ' 00:00:00';
								if(opt.autoUpdate){		//开启自动修改
									if(opt.autoUpdate.nolyUpdateField){
										if(opt.autoUpdate.nolyUpdateField.indexOf(f)<0)
											return;
									}
									var data = { id: trData.id };
									data[f] = val;
									myutil.saveAjax({
										url: opt.autoUpdate.updateUrl || opt.autoUpdate.saveUrl,
										data: data,
										closeMsg: opt.autoUpdate.closeMsg,
										closeLoad: opt.autoUpdate.closeLoad,
										success:function(){
											opt.autoUpdate.success && opt.autoUpdate.success();
										}
									})
								}
								trData[f] = val;    	//修改缓存值
							}
						})
					})
				})
			}
			layui.each(selectLay,function(index,item){	//监听下拉框、同步缓存值
				form.on('select('+item+')',function(obj){
					var index = $(obj.elem).closest('tr').data('index');
					var field = $(obj.elem).closest('td').data('field');
					var trData = layui.table.cache[tableId][index];
					var f = field;
					opt.autoUpdate && opt.autoUpdate.field[f] && ( f = opt.autoUpdate.field[f]);
					trData[f] = obj.value; //修改本地数据缓存值
					if(opt.autoUpdate){
						if(index>=0){
							if(notNull.indexOf(field)>=0 && isNull(obj.value))
								myutil.emsg('修改失败,下拉框的值不能为空');
							else{
								var data = { id: trData.id };
								data[f] = obj.value;
								if(opt.autoUpdate.nolyUpdateField){
									if(opt.autoUpdate.nolyUpdateField.indexOf(f)<0)
										return;
								}
								myutil.saveAjax({
									url: opt.autoUpdate.updateUrl || opt.autoUpdate.saveUrl,
									data: data,
									closeMsg: opt.autoUpdate.closeMsg,
									closeLoad: opt.autoUpdate.closeLoad,
									success: function(){
										opt.autoUpdate.success && opt.autoUpdate.success(field,trData,obj);
										if(opt.autoUpdate.isReload)
											table.reload(tableId);
									},
									error: function(){
										table.reload(tableId);
									}
								})
							}
						}  
					}
					trData[f] = obj.value;
				})		
			})
			if(opt.autoUpdate)
				table.on('edit('+tableId+')',function(obj){
					var val = obj.value, trData = obj.data, data = { id : trData.id },field = obj.field,msg = '';
					var t = opt.autoUpdate.field[field] || field;	//查找对应的虚拟字段
					var input = $(this);
					if(data.id && data.id!=''){
						if(notNull.indexOf(field)>=0 && isNull(val))
							msg = '修改失败，该值不能为空';
						else if(price.indexOf(field)>=0 && !isPrice(val))
							msg = '修改失败，请正确填写'+china[field];
						else if(count.indexOf(field)>=0 && !isCount(val))
							msg = '修改失败，请正确填写'+china[field];
						else if(opt.verify && opt.verify.otherVerify)
							msg = opt.verify.otherVerify(trData) || '';
						if(msg!=''){
							$(this).val(myutil.lastData);   //回滚数据
							var index = $(obj.tr).data('index');
							table.cache[tableId][index][t] = myutil.lastData;	//修改缓存值。确保存在模板时，获取正确的缓存值！！！
							return myutil.emsg(msg);
						}
					}
					data[t] = val;
					if(opt.autoUpdate && trData.id>0){
						if(opt.autoUpdate.nolyUpdateField){
							if(opt.autoUpdate.nolyUpdateField.indexOf(t)<0)
								return;
						}
						myutil.saveAjax({
							url: opt.autoUpdate.updateUrl || opt.autoUpdate.saveUrl,
							data: data,
							closeMsg: opt.autoUpdate.closeMsg,
							closeLoad: opt.autoUpdate.closeLoad,
							success: function(){
								opt.autoUpdate.success && opt.autoUpdate.success(field,trData,this);
								if(opt.autoUpdate.isReload)
									table.reload(tableId);
							},
							error: function(){
								var index = $(obj.tr).data('index');
								table.cache[tableId][index][t] = myutil.lastData;
							}
						})
					}
					trData[t] = val;  //修改缓存值
				})
			if(opt.curd)
				table.on('toolbar('+tableId+')',function(obj){		//监听工具
					switch(obj.event){
					case 'addTempData': 	addTempData(); 	break;
					case 'saveTempData': 	saveTempData(); break;
					case 'deleteSome': 		deleteSome(); 	break;
					case 'cleanTempData': 	table.cleanTemp(tableId); break;
					}
					opt.curd.otherBtn && opt.curd.otherBtn(obj);	//如果有其他按钮操作
					function addTempData(){
						var field = (function(){	
							var field = {};
							if(opt.curd.addTemp){	//如果给出默认值,进行深拷贝
								for(var key in opt.curd.addTemp){
									field[key] = opt.curd.addTemp[key];
								}
							}
							else
								layui.each(allField,function(index,item){
									var t = opt.autoUpdate.field[item] || item;
									field[t] = '';
								})
							return field;
						})();
						table.addTemp(tableId,field,function(trElem){
							opt.curd.addTempAfter && opt.curd.addTempAfter(trElem);
							renderData(dateField,'date');
							renderData(dateTimeField,'datetime');
							function renderData(data,type){
								layui.each(data,function(index1,item1){	 //渲染日期
									layui.each($(opt.elem).next().find('td[data-field="'+item1+'"]'),function(index,item){
										item.children[0].onclick = function(event) { layui.stope(event) };
										var dateTd = trElem.find('td[data-field="'+item1+'"]')[0];
										laydate.render({
											elem: dateTd.children[0],
											type: type,
											done: function(val){
												var index = $(this.elem).closest('tr').data('index');
												var trData = table.cache[tableId][index];
												if(val.split(' ').length==1)
													val += ' 00:00:00';
												var t = opt.autoUpdate.field[item1]? opt.autoUpdate.field[item1] : item1;
												trData[t] = val;    	//修改缓存值
											}
										})
									})
								})
							}
						});
					}
					function saveTempData(){
						var data = table.getTemp(tableId).data,success = 0,msg='';
						layui.each(data,function(index,item){
							if(msg!='') return;
							for(var i=0;i<notNull.length;i++){
								var t = notNull[i];
								t = opt.autoUpdate.field[t]? opt.autoUpdate.field[t] : t;
								if(typeof(item[t])!="undefined" && isNull(item[t]))
									msg = '新增失败，'+china[notNull[i]]+'不能为空';
							}
							for(var i=0;i<price.length;i++){
								var t = price[i];
								t = opt.autoUpdate.field[t]? opt.autoUpdate.field[t] : t;
								if(typeof(item[t])!="undefined" && !isPrice(item[t]))
									msg = '新增失败，请正确填写'+china[t];
							}
							for(var i=0;i<count.length;i++){
								var t = count[i];
								t = opt.autoUpdate.field[t]? opt.autoUpdate.field[t] : t;
								if(typeof(item[t])!="undefined" && !isCount(item[t]))
									msg = '新增失败，请正确填写'+china[t];
							}
						})
						if(msg!='') 
							return myutil.emsg(msg);
						if(opt.curd.saveFun)
							opt.curd.saveFun(data);		//如果存在保存函数则执行，否则执行默认保存函数
						else{
							if(data.length==0)
								return myutil.emsg('无临时数据可保存！');
							for(var i=0;i<data.length;i++)
								myutil.saveAjax({
									url: opt.autoUpdate.saveUrl,
									data: data[i],
									closeMsg: opt.autoUpdate.closeMsg,
									closeLoad: opt.autoUpdate.closeLoad,
									success: function(){
										success++;
										if(success==data.length){
											myutil.smsg('成功新增：'+success+'条数据');
											table.reload(tableId);
										}
									}
								})
						}
					}
					function deleteSome(){
						var choosed=layui.table.checkStatus(tableId).data;
						if(choosed.length<1)
							return myutil.emsg('请选择相关信息');
						layer.confirm("是否确认删除？",function(){
							var ids='';
							for(var i=0;i<choosed.length;i++)
								ids+=(choosed[i].id+",");
							if(opt.curd.deleFun)
								opt.curd.deleFun(ids,choosed);		//如果存在删除函数则执行，否则执行默认删除函数
							else
								myutil.deleteAjax({
									url: opt.autoUpdate.deleUrl,
									ids: ids,
									success:function(){
										table.reload(tableId);
										opt.autoUpdate.success && opt.autoUpdate.success();
									},
								})
						})
					}
				})
			if(opt.scrollX){
				myutil.scrollX($(opt.elem).next().find('.layui-table-box').find('.layui-table-body')[0])
			}
			if(opt.autoMerge){
				var hiddenAllSty = `
				<style id="hiddenAllCheck`+tableId+`">
					div[lay-id="`+tableId+`"] .layui-table-header th[data-field="0"] input[name="layTableCheckbox"]+.layui-form-checkbox{
						display:none !important;
					}
				</style>
				`;
				var checkBoxLayFilter = "allCheckbox_"+tableId;
				$('input[lay-filter="allCheckbox"]').prop('checked',false);
				layui.each(opt.autoMerge.field,function(index,mergeField){
					merge(mergeField);
				})
				function merge(field){	//进行合并
					var rowspan = 1,mainCols=0;
					var cache = table.cache[tableId];
					var allRow = $('#'+tableId).next().find('td[data-field="'+field+'"]');
					layui.each(allRow,function(index,item){
						if(index!=0){
							var thisData = cache[index],lastData = index!=0?cache[index-1]:{id:-1};
							if(!thisData)
								return;
							var isMerge = false;
							if(opt.autoMerge.merge){
								isMerge = opt.autoMerge.merge(thisData,lastData);
							}else{
								isMerge = thisData.id==lastData.id;
							}
							if(isMerge){	//与上一列相同
								rowspan++;
								$(item).remove();
							}else{	
								$(allRow[mainCols]).attr('rowspan',rowspan)
								mainCols = index;
								rowspan = 1;
							}
						}
					});
					$(allRow[mainCols]).attr('rowspan',rowspan);
					if(field=="0"){	//如果合并复选框
						if($('#hiddenAllCheck').length==0){	//隐藏原本的全选复选框
							$('head').append(hiddenAllSty);
						}
						if($('input[lay-filter="'+checkBoxLayFilter+'"]').length==0)
							$('div[lay-id="'+tableId+'"] .layui-table-header th[data-field="0"] div').append(
								['<input type="checkbox" title="" lay-filter="'+checkBoxLayFilter+'" lay-skin="primary">'].join(' ')
							);
						var inputAllCheck = false;
			    	    form.on('checkbox('+checkBoxLayFilter+')', function(data){	//监听新加入复选框选择
			    	    	inputAllCheck = true;
			    	    	var check = data.elem.checked;
			    	    	var allCheck = $('#'+tableId).next().find('.layui-table-main').find('.layui-form-checkbox');
			    	    	layui.each(allCheck,function(index,item){
			    	    		if(check){
			    	    			if($(item).hasClass('layui-form-checked'))
			    	    				return;
			    	    			else
			    	    				$(item).click();
			    	    		}else{
			    	    			if($(item).hasClass('layui-form-checked'))
			    	    				$(item).click();
			    	    		}
			    	    	})
			    	    	form.render();
			    	    	inputAllCheck = false;
		    	    	});
			    	    $('input[name="layTableCheckbox"]').change(function() {	//监听表格复选框的变化
			    	    	if(!inputAllCheck){
				    	    	var check = $(this).prop("checked");
				    	    	var allTableCheck = $('td input[name="layTableCheckbox"]');
				    	    	if(check){
				    	    		for(var i=0;i<allTableCheck.length;i++){
				    	    			if($(allTableCheck[i]).prop("checked")!=check){
				    	    				return true;
				    	    			}
				    	    		}
				    	    	}
			    	    		$('input[lay-filter="'+checkBoxLayFilter+'"]').prop('checked',check);
			    	    		if(check)
			    	    			$('input[lay-filter="'+checkBoxLayFilter+'"]+').addClass('layui-form-checked');
			    	    		else
			    	    			$('input[lay-filter="'+checkBoxLayFilter+'"]+').removeClass('layui-form-checked');
			    	    		return true;
			    	    	}
			    	   	});
					}
				}
				$('div[lay-id="'+tableId+'"].layui-table-view tbody tr').hover(function(){	//悬浮行变色
					trTransColor.call(this,'#f2f2f2');
				},function(){
					trTransColor.call(this,'white');
''				})
				function trTransColor(color){
					var trElem = $(this);
					while($(trElem).find('[name="layTableCheckbox"]+').length==0){//如果没有复选框，为子元素，继续往上找父元素所在行
						trElem = $(trElem).prev();
						if($(trElem).length==0)
							break;
					}
					while($(trElem).length>0){		//从父元素开始，每个子元素变色
						//opt.even
						var c = color;
						if(color=='white' && opt.even){	//如果开启斑马线
							var index = $(trElem).data('index');
							if(index%2!=0)
								c = opt.autoMerge.evenColor || '#f2f2f2';
						}
						$(trElem).find('td').css('backgroundColor',c);
						if(color=='white' && opt.even)
							layui.each(opt.autoMerge.field,function(index2,item2){
								$(trElem).find('td[data-field="'+item2+'"]').css('backgroundColor',color);
							})
						trElem = $(trElem).next();
						if($(trElem).find('[name="layTableCheckbox"]+').length>0)
							break;
					}
				}
	    	    form.render();
			}
			done && done(res, curr, cou);
		}
		opt.done = newDone;
		opt.totalRow = totalRow.length>0?true:false;
		var render = $.extend({},obj,opt);
		//删除多余的配置参数   delete render.autoUpdate;	
		layui.each(render.cols,function(index,item){
			layui.each(item,function(index2,item2){

			})
		})
		render.curd && (delete render.curd);			//删除增、改、查、删配置
		render.ifNull && (delete render.ifNull);
		render.verify && (delete render.verify);
		render.colsWidth && (delete render.colsWidth);
		render.autoUpdate && (delete render.autoUpdate);
		render.scrollX && (delete render.scrollX);
		render.autoMerge && (delete render.autoMerge);
		table.render(render);
	}

	mytable.renderNoPage = function(opt){		//不开启分页
		var obj = { parseData:parseData(), };
		mytable.render(opt,obj);
	}
	function parseData(){
		return function(ret){
			return {  msg:ret.message,  code:ret.code , data:ret.data, };
		}
	}
	function parseDataPage(){		//分页请求数据解析
		return function(ret){
			if(ret.code==0)
				return {  msg:ret.message,  code:ret.code , data:ret.data.rows, count:ret.data.total,
							statData: ret.data.statData, }; 
			else
				return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
		}
	}
	function isCount(val){	//验证是否为正整数
		var res = true;
		isNaN(val) && (res=false);
		val<0 && (res=false);
		(val%1.0!=0.0) && (res=false);
		return res;
	}
	function isPrice(val){	//是否为合法数字
		var res = true;
		isNaN(val) && (res=false);
		val<0 && (res=false);
		return res;
	}
	function isNull(val){
		var res = false;
		val=='' && (res=true);
		!val && (res=true);
		return res;
	}
	exports('mytable',mytable);
}) 