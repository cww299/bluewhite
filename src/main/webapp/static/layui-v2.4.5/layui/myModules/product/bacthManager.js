/* 批次管理模块
 * 2019/10/5
 * author: cww
 * 使用方法：引入模块、渲染
 * bacthManager.render({
 *  ctx:'',	  //前缀路径
 *  elem:'',  //绑定元素
 *  type:'',  //引用类型
 * })
 * type: 1一楼质检   2一楼包装  3二楼针工  4二楼机工  5八号裁剪
 * 工序基础数据type
 * 1 productFristQuality   2  productFristPack  3 productTwoDeedle 4 productTwoMachinist 5 productEightTailor
 */
layui.config({
	base: 'static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
	menuTree : 'layui/myModules/menuTree',
}).define(['jquery','table','form','mytable','laytpl','laydate','layer','menuTree'],function(exports){
	var $ = layui.jquery
	, table = layui.table 
	, form = layui.form
	, myutil = layui.myutil
	, laytpl = layui.laytpl
	, laydate = layui.laydate
	, mytable = layui.mytable
	, menuTree = layui.menuTree
	, MODNAME = 'bacthManager'
		
	, bacthManager = {
			
	}
	, Class = function(){
		
	};
	
	var baseType = ['','productFristQuality','productFristPack','productTwoDeedle','productTwoMachinist','productEightTailor'];
	myutil.timeFormat();
	myutil.clickTr();
	myutil.config.msgOffset = '120px';
	
	var TPL_MAIN = [		//主页面模板
	           '<table class="layui-form searchTable">',
		           '<tr>',
			           '<td class="smHidden">批次号</td>',
			           '<td class="smHidden"><input type="text" name="bacthNumber" class="layui-input" placeholder="请输入批次号"></td>',
			           '<td class="smHidden">&nbsp;&nbsp;</td>',
			           '<td>产品名称</td>',
			           '<td><input type="text" name="name" class="layui-input" placeholder="请输入产品名称"></td>',
			           '<td>&nbsp;&nbsp;</td>',
			           '<td>时间</td>',
			           '<td><input style="width:230px !important;" type="text" name="orderTimeBegin" id="searchTime" class="layui-input" placeholder="请输入时间"></td>',
			           '<td>&nbsp;&nbsp;</td>',
			           '<td>完成状态</td>',
			           '<td style="width:120px;"><select name="status"><option value="0">未完成</option>',
			           						'<option value="1">已完成</option></select></td>',
			           '<td>&nbsp;&nbsp;</td>',
			           '<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="search">查找</span></td>',
			           '<td>&nbsp;&nbsp;</td>',
		           '</tr>',
	           '</table>',
	           '<table id="tableData" lay-filter="tableData"></table>',
	           '<div id="isSmallScreen"></div>',		//用来区分是否小屏
	           ].join(' ');
	
	
	var ALLO_TPL = [	//分配任务模板
                    '<div style="padding:15px;" class="layui-form">',
                    	'<p class="hiddenTip">',
                    		'<span class="layui-badge">提示:贴破洞数量需要点击编辑进行设置，当前设置为<b id="tiepodongNumber">0</b></span></p>',
	                    '<div class="procedureDiv">',
	                    	'<div class="layui-form-item">',
	                    		'<label class="layui-form-label">任务数量：</label>',
	                    		'<div class="layui-input-block">',
	                    			'<input type="text" class="layui-input" id="number">',
	                    		'</div>',
	                    	'</div>',
	                    	'<p>工序：</p>',
		                    '<div id="procedureTree"></div>',
	                    '</div>',
	                    '<div class="userDiv">',
	                    	'<div class="layui-form-item">',
		                		'<label class="layui-form-label">分配时间：</label>',
		                		'<div class="layui-input-block">',
		                			'<input type="text" class="layui-input" id="allotTime">',
		                		'</div>',
		                	'</div>',
		                    '<p>完成人：</p>',
		                    '<div id="userTree"></div>',
	                    '</div>',
                    '</div>',
                    ].join(' ');
	
	
	
	var LOOKOVER_ALLOT = [		//查看分配工序模板
	                  '<div style="padding:15px;" >',
	                  	'<table>',
	                  		'<tr>',
	                  			'<td>批次号：</td>',
	                  			'<td id="lookbacth">---</td>',
	                  			'<td>&nbsp;&nbsp;&nbsp;</td>',
	                  			'<td>产品名：</td>',
	                  			'<td id="lookname">---</td>',
	                  			'<td>&nbsp;&nbsp;&nbsp;</td>',
	                  			'<td>时间：</td>',
	                  			'<td id="looktime">---</td>',
	                  		'</tr>',
	                  	'</table>',
	                  	'<table id="lookTable" lay-filter="lookTable"></table>',
	                  '</div>',
	                  ].join(' ');
	
	
	Class.prototype.render = function(opt){
		var isSmall = false;
		var allProcedure = [],allUser = [];
		myutil.getData({
			url:opt.ctx+'/basedata/list?type='+baseType[opt.type],
			success: function(d){
				allProcedure = d;
			}
		});
		laytpl(TPL_MAIN).render({},function(h){
			$(opt.elem).append(h);
			if($('#isSmallScreen').css('display')=='none')
				isSmall = true;
		})
		var todata = new Date().format('yyyy-MM-dd');
		var firstDay = new Date().format('yyyy-MM-01');
		laydate.render({
			elem: '#searchTime',
			range:'~',
			value: firstDay +' ~ '+ todata
		})
		form.render();
		form.on('submit(search)',function(obj){
			var time = obj.field.orderTimeBegin;
			if(time!=''){
				time = time.split('~');
				obj.field.orderTimeBegin = time[0].trim()+' 00:00:00';
				obj.field.orderTimeEnd = time[1].trim()+' 23:59:59';
			}else
				obj.field.orderTimeEnd = '';
			table.reload('tableData',{
				where: obj.field,
				page: { curr:1 },
			})
		})
		var col = [
		           { type:'checkbox' },
			       { title:'批次号', 	field:'bacthNumber', },
			       { title:'时间', 	field:'allotTime', type:'date', edit:true, },
			       { title:'产品名', 	field:'product_name', },
			       { title:'数量', 	field:'number',edit:true, },
			       { title:'预计生产单价', 	field:'bacthDepartmentPrice', templet:function(d){ return d.bacthDepartmentPrice.toFixed(3);  } },
			       { title:'外发价格', 	field:'bacthHairPrice', },
			       { title:'任务价值', 	field:'sumTaskPrice', templet:function(d){ return d.sumTaskPrice?d.sumTaskPrice.toFixed(3):'---' } },
			       { title:'地区差价	', 	field:'regionalPrice',templet:function(d){ return d.regionalPrice?d.regionalPrice.toFixed(3):'---' }  },
			       { title:'当批用时	', 	field:'time', templet:function(d){ return d.time.toFixed(3) }  },
			       { title:'备注	', 	field:'remarks', },
			       { title:'状态	', 	field:'status', transData:{data:['未完成','已完成'],} },
			       { title:'操作', event:'allocation', templet:function(d){
			    	   return '<span class="layui-btn layui-btn-sm">分配</span>';
			       	 } },
			      ];
		if(opt.type==3){		//二楼针工增加针工价格
			col = [
		           { type:'checkbox' },
			       { title:'批次号', 	field:'bacthNumber', },
			       { title:'时间', 	field:'allotTime',type:'date', edit:true,   },
			       { title:'产品名', 	field:'product_name', },
			       { title:'数量', 	field:'number',edit:true, },
			       { title:'预计生产单价', 	field:'bacthDepartmentPrice', templet:function(d){ return d.bacthDepartmentPrice.toFixed(3);  } },
			       { title:'外发价格', 	field:'bacthHairPrice', },
			       { title:'针工价格', field:'bacthDeedlePrice',templet:function(d){ return d.sumTaskPrice?d.sumTaskPrice.toFixed(3):'---' }},
			       { title:'任务价值', 	field:'sumTaskPrice', templet:function(d){ return d.sumTaskPrice?d.sumTaskPrice.toFixed(3):'---' } },
			       { title:'地区差价	', 	field:'regionalPrice',templet:function(d){ return d.regionalPrice?d.regionalPrice.toFixed(3):'---' }  },
			       { title:'当批用时	', 	field:'time', templet:function(d){ return d.time.toFixed(3) }  },
			       { title:'备注	', 	field:'remarks', },
			       { title:'状态	', 	field:'status', transData:{data:['未完成','已完成'],} },
			       { title:'操作', event:'allocation', templet:function(d){
			    	   return '<span class="layui-btn layui-btn-sm">分配</span>';
			       	 } },
			      ];
		}
		if(isSmall){
			col = [
		           { type:'checkbox' },
			       { title:'时间', 	field:'allotTime',width:'18%',type:'date', edit:true,  },
			       { title:'产品名', 	field:'product_name', },
			       { title:'数量', 	field:'number', width:'12%', edit:true,},
			       { title:'备注	', 	field:'remarks',width:'8%', },
			       { title:'状态	', 	field:'status', width:'12%', transData:{data:['未完成','已完成'],} },
			       { title:'操作', event:'allocation',width:'12%', templet:function(d){
			    	   return '<span class="layui-btn layui-btn-sm">分配</span>';
			       	 } },
			      ];
		}
		var statData = '';
		var toolbar = ['<span class="layui-btn layui-btn-sm" lay-event="onekeyFinish">一键完成</span>',
				          '<span class="layui-btn layui-btn-sm" lay-event="lookover">查看分配工序</span>'];
		if(opt.type==4){	//二楼机工增加导出按钮
			toolbar.push(
				'<span class="layui-btn layui-btn-sm" lay-event="export">导出工序</span>'
			)
		}
		mytable.render({
			elem:'#tableData',
			url: opt.ctx+'/bacth/allBacth?type='+opt.type,
			totalRow:['number','sumTaskPrice','time'],
			parseData:function(ret){
				if(ret.code==0)
					statData = ret.data.statData;
				return {  msg:ret.message,  code:ret.code , data:ret.data.rows, count:ret.data.total }; 
			},
			where: {
				status:0,
				orderTimeBegin: firstDay+' 00:00:00',
				orderTimeEnd: todata+' 23:59:59',
			},
			size: isSmall?'sm':'',
			colsWidth:isSmall?[]:[0,8,10,0,8,10,6,6,6,6,6,6,6],
			autoUpdate:{
				saveUrl:'/bacth/addBacth',
				deleUrl:'/bacth/deleteBacth',
			},
			curd:{
				btn:[4],
				otherBtn: finish(),
			},
			toolbar: toolbar.join(' '),
            limit:'14',
            limits:[10,14,20,50],
			cols:[col],
			done:function(){
				$('.layui-table-total').find('td[data-field="number"]').find('div').html(statData.number);
				$('.layui-table-total').find('td[data-field="sumTaskPrice"]').find('div').html(statData.sumTaskPrice);
				$('.layui-table-total').find('td[data-field="time"]').find('div').html(statData.time);
			}
		}) 
		table.on('tool(tableData)', function(obj){
			if(obj.event=='allocation'){
				var tiepidongNumber = 0;
				var trData = obj.data;
				var html = '';
				laytpl(ALLO_TPL).render({},function(h){
					html = h;
				})
				var now = myutil.getSubDay( isSmall ? 0 : 1 );
				var procedureTree = [];
				getAllProcedureTree();
				var area = isSmall?['100%','80%']:['60%','80%'];
				var allotWin = layer.open({		//分配弹窗
					type:1,
					area: area,
					offset:'20px',
					title: trData.product.name,
					content: html,
					btn:['确定','取消'],
					btnAlign: 'c',
					success:function(){
						getUserData(now);
						laydate.render({
							elem:'#allotTime',
							value:now,
							type:'datetime',
							done: function(value){
								getUserData(value);
								menuTree.reload('userTree',{
									data: allUser,
								})
							}
						});
						menuTree.render({				
				    	  elem:'#userTree',
				    	  data : allUser,
						})
						var checked = [];
						if(opt.type==2){	//如果是包装，默认选中包装工序的全部，除去上车
							var t = procedureTree[0].children;
							for(var i in t){
								if(t[i].name=='包装'){
									for(var k in t[i].children){
										if(t[i].children[k].name.indexOf('上车')<0)
											checked.push(t[i].children[k].id);
									}
								}
							}
						}
						menuTree.render({				
				    	  elem:'#procedureTree',
				    	  data : procedureTree,
				    	  toolbar: opt.type==1?['edit']:[],
		    			  hide: false,
		    			  checked: checked,
				    	  showName: function(data){
				    		  if(isNaN(data.number))
				    			  return data.name;
				    		  else
				    			  return data.name+' 剩余:'+data.number;
				    	  }
						})
						if(opt.type==1){	//如果是一楼质检，开启编辑模式
							$('.hiddenTip').show();
							menuTree.onToolbar('procedureTree',function(obj){
								switch(obj.type){
								case 'edit': 
									if(obj.data.name=='贴破洞'){
										var numberWin = layer.prompt({offset:'120px', title: '请输入贴破洞数量',},function(value, index, elem){
											tiepidongNumber = value;
											myutil.smsg('贴破洞数量设置成功!');
											$('#tiepodongNumber').html(tiepidongNumber);
											layer.close(numberWin);
										});
									}
									break;
								}
							})
						}
						if(opt.type==1 || opt.type==2){
							$('#number').val(trData.number);
						}
						form.render();
					},
					yes:function(){
						var userIds = [],procedureIds = [],temporaryUserIds = [];
						var ids = [],temporaryIds = [];
						var userTreeId = menuTree.getVal('userTree'),procedureTreeId = menuTree.getVal('procedureTree');
						
						for(var i in userTreeId){	
							if(userTreeId[i]!=-1){	//区分是否为临时员工,临时员工的id： t-id~userId
								if(userTreeId[i].indexOf('-')>0){
									var t = userTreeId[i].split('-')[1].split('~');
									temporaryIds.push(t[0]);
									temporaryUserIds.push(t[1]);
								}else{
									var t = userTreeId[i].split('~');
									ids.push(t[0]);
									userIds.push(t[1]);
								}
							}
						}
						for(var i in procedureTreeId){
							if(procedureTreeId[i]!=-1){	
								var id = procedureTreeId[i].split('-');
								if(id[1]==0)
									return myutil.emsg('选择的工序剩余数量不能为0');
								if(id[1]>$('#number').val())
									return myutil.emsg('选择的工序剩余数量不能为任务分配数量');
								procedureIds.push(id[0]);
							}
						}
						var saveData = {
								type: opt.type,
								userIds: ids.join(','), 
								ids:  userIds.join(','),
								temporaryUserIds: temporaryIds.join(','),
								temporaryIds: temporaryUserIds.join(','),
								procedureIds: procedureIds.join(','),
								number: $('#number').val(),
								allotTime: $('#allotTime').val(),
								bacthId: trData.id,
								productId: trData.product.id,
								productName: trData.product.name,
								bacthNumber: trData.bacthNumber,
							};
						if(opt.type==1)
							saveData.holeNumber = tiepidongNumber;
						myutil.saveAjax({
							url:'/task/addTask',
							data:saveData,
							success:function(){
								if(opt.type==1 || opt.type==2)
									layer.close(allotWin);
								else{
									getAllProcedureTree();	//重新获取工序进行重载
									menuTree.reload('userTree',{
										checked:[],
									})
									menuTree.reload('procedureTree',{
										checked:[],
										data: procedureTree,
									})
								}
								table.reload('tableData');
							}
						})
					}
				})
				function getAllProcedureTree(){	//获取所有工序的树形结构
					procedureTree = [{
						id:-1,name:'全部',children:[],
					}];
					for(var k in allProcedure){
						(function(name,id){
							var da = { id:-1, name:name, children:[] }
							$.ajax({
								url: opt.ctx+'/production/typeToProcedure',
								async: false,
								data: {
									productId: trData.product.id,
									bacthId: trData.id,
									type: opt.type,
									procedureTypeId: id,
									flag: 0,
								},
								success:function(r){
									if(r.code==0){
										for(var i in r.data){
											da.children.push({
												id: r.data[i].id+'-'+r.data[i].residualNumber,	//拼接剩余数量，用于判断是否为0
												name: r.data[i].name,
												number: r.data[i].residualNumber,
											})
										}
										procedureTree[0].children.push(da);
									}
								}
							})
						})(allProcedure[k].name,allProcedure[k].id);
					}
				}
			}
		})
		function finish(){
			return function(obj){
				var check = table.checkStatus('tableData').data;
				switch(obj.event){
				case 'onekeyFinish': onekeyFinish(); break;
				case 'lookover': lookover(); break;
				case 'export' : exportProcedure(); break;
				}
				function onekeyFinish(){
					var inputTime = layer.open({
						type:1,
						area:['20%','20%'],
						offset:'200px',
						content:'<div style="padding:10px;"><input type="text" class="layui-input" id="finishTime" placeholder="请输入完成时间"></div>',
						btn:['确定','取消'],
						success:function(){
							laydate.render({
								elem:'#finishTime',
							})
						},
						yes:function(){
							var time = $('#finishTime').val(), ids = [];
							if(time)
								time+=' 00:00:00'
							if(check.length<1)
								return myutil.emsg('请选择相关信息');
							for(var k in check)
								ids.push(check[k].id);
							myutil.saveAjax({
								url:'/bacth/statusBacth',
								type:'get',
								data:{
									type: opt.type,
									status: 1,
									time: time,
									ids: ids.join(','),
								},
								success:function(){
									layer.close(inputTime);
									table.reload('tableData');
								}
							})
						}
					})
				}
				function lookover(){	//查看分配工序弹窗
					var html = '';
					if(check.length<1)
						return myutil.emsg('请选择相关信息');
					if(check.length>1)
						return myutil.emsg('不能同时查看多条信息');
					laytpl(LOOKOVER_ALLOT).render({},function(h){
						html = h;
					})
					var area = isSmall?['100%','80%']:['80%','80%'];
					layer.open({
						type:1,
						title: check[0].product.name,
						offset:'50px',
						shadeClose:true,
						area:area,
						content:html,
						success: function(){
							$('#lookbacth').html(check[0].bacthNumber);
							$('#lookname').html(check[0].product.name);
							$('#looktime').html(check[0].allotTime);
							mytable.render({
								elem:'#lookTable',
								url:opt.ctx+'/task/allTask?type='+opt.type+'&bacthId='+check[0].id,
								autoUpdate:{
									deleUrl:'/task/delete',
								},
								curd:{
									btn:[4],
									otherBtn: finish(),
								},
								cols:[[
								       { type:'checkbox', },
								       { title:'工序', field:'procedureName', },
								       { title:'预计时间', field:'expectTime', templet:function(d){ return d.expectTime.toFixed(4) }},
								       { title:'任务价值', field:'expectTaskPrice', templet:function(d){ return d.expectTaskPrice.toFixed(4)}},
								       { title:'b工资净值', field:'payB', templet:function(d){ return d.payB.toFixed(4)}},
								       { title:'数量', field:'number', },
								       { title:'工序加价', field:'performance', },
								       { title:'加绩工资', field:'performancePrice', templet:function(d){ return d.performancePrice.toFixed(4)} },
								       { title:'完成人', event:'finishPeople', templet:function(d){
								    	   return '<span class="layui-btn layui-btn-sm">查看完成人</span>';
								       	 	} },
								       ]],
								done:function(){
									table.on('tool(lookTable)', function(obj){
										if(obj.event=='finishPeople'){
											layer.open({
												type:1,
												shadeClose:true,
												offset:'80px',
												area:['20%','70%'],
												content:'<div><table id="peopleTable"></table></div>',
												success:function(){
													 mytable.renderNoPage({
														 elem:'#peopleTable',
														 url:opt.ctx+'/task/taskUser?id='+obj.data.id,
														 cols:[[
														        { field:'id', title:'id', },
														        { field:'userName', title:'完成人', },
														        ]]
													 })
												}
											})
										}
									})
								}
							})
						}
					})//later open end
				}
				function exportProcedure(){	//导出工序
					if(check.length!=1)
						return myutil.emsg('只能选择一条信息导出！');
					location.href= opt.ctx+'/excel/importExcel/DownBacth?id='+check[0].id;
				}
			}
		}//end finish
		
		function getUserData(day){
			allUser = [];
			myutil.getDataSync({	//获取所有分组用户的树形结构数据
				url: opt.ctx+'/production/getGroup?type='+opt.type,
				success:function(allGroup){
					for(var k in allGroup){
						(function(name){
							$.ajax({
								url: opt.ctx+'/production/allGroup',
								async: false,
								data:{
									id: allGroup[k].id,
									type: opt.type,
									temporarilyDate: day,
								},
								success:function(r){
									if(r.code==0){
										var groupPeople = r.data;
										var data = {
												id:-1,
												name: name,
												children:[]
										};
										if(groupPeople.temporarilyUser && groupPeople.temporarilyUser.length>0){
											var t = groupPeople.temporarilyUser;
											for(var k in t)
												data.children.push({
													id: 't-'+t[k].id+'~'+t[k].userId,
													name: t[k].name+' ---- <span class="layui-badge">临</span>',
												});
										}
										if(groupPeople.userList && groupPeople.userList.length>0){
											var t = groupPeople.userList;
											for(var k in t)
												data.children.push({
													id: t[k].id+'~'+t[k].userId,
													name: t[k].name+' ---- <span class="layui-badge layui-bg-green">正</span>',
												})
										}
										allUser.push(data);
									}
								}
							})
						})(allGroup[k].name);
					}
				}
			});
		}
	}//end render
	bacthManager.render = function(opt){
		var s = new Class();
		myutil.config.ctx = opt.ctx;
		s.render(opt);
	}
	exports(MODNAME,bacthManager);
})