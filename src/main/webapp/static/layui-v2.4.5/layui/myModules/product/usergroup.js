/* 员工分组模块
 * 2019/10/4
 * author: cww
 * 使用方法：引入模块、渲染
 * usergroup.render({
 *  ctx:'',	  //前缀路径
 *  elem:'',  //绑定元素
 *  type:'',  //引用类型
 * })
 */
layui.config({
	base: 'static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
}).define(['jquery','table','form','mytable','laytpl','laydate','layer'],function(exports){
	var $ = layui.jquery
	, table = layui.table 
	, form = layui.form
	, myutil = layui.myutil
	, laytpl = layui.laytpl
	, laydate = layui.laydate
	, mytable = layui.mytable
	, MODNAME = 'usergroup'
		
	, usergroup = {
			
	}
	, Class = function(){
		
	};
	
	myutil.timeFormat();
	
	var TPL = [		//主页面模板
	           '<table id="tableData" lay-filter="tableData"></table>',
	           ].join(' ');
	var LOOKOVER_TPL = [	//查看人员模板
	                    '<div style="padding:15px;">',
		                    '<table class="layui-form">',
		                    	'<tr>',
		                    		'<td>开始时间：</td>',
		                    		'<td><input type="text" class="layui-input" id="searchTime" name="temporarilyDate"></td>',
		                    		'<td>&nbsp;&nbsp;</td>',
		                    		'<td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="search">查找</span></td>',
		                    	'</tr>',
		                    '</table>',
		                    '<table id="lookoverTable" lay-filter="lookoverTable"></table>',
	                    '</div>',
	                    ].join(' ');
	var ADDNEW_TPL = [		//新增外调模板
	                  '<div style="padding:15px;" >',
	                  	'<div class="layui-form layui-form-pane">',
	                  		'<div class="layui-form-item" pane>',
	                    		'<label class="layui-form-label">借调时间</label>',
	                    		'<div class="layui-input-block">',
			                      '<input type="text" class="layui-input" name="temporarilyDate" id="addNewTime">',
			                    '</div>',
			                '</div>',
			                '<div class="layui-form-item" pane>',
	                    		'<label class="layui-form-label">借调人员</label>',
	                    		'<div class="layui-input-block">',
		                    		'<select name="userId" id="addUserId" lay-search lay-verify="required">',
	              					'<option value="">请选择</option></select>',
			                    '</div>',
			                '</div>',
			                '<div class="layui-form-item" pane>',
		                		'<label class="layui-form-label">工作时长</label>',
		                		'<div class="layui-input-block">',
			                      '<input type="text" class="layui-input" name="workTime" lay-verify="number">',
			                    '</div>',
			                '</div>',
			                '<div class="layui-form-item" pane>',
			                	'<label class="layui-form-label">小组</label>',
			                	'<div class="layui-input-block">',
				                	'<select name="groupId" id="addGroupId" lay-search lay-verify="required">',
	          						'<option value="">请选择</option></select>',
			          				'<span style="display:none;" lay-submit lay-filter="sureAdd" id="sureAdd"></span>',
			                    '</div>',
			                '</div>',
		          		'</div>',
	                  '</div>',
	                  ].join(' ');
	
	Class.prototype.render = function(opt){
		myutil.clickTr();
		var isSmall = window.screen.width<1400;
		var yesterDay = myutil.getSubDay(1);
		var today = myutil.getSubDay(0);
		laytpl(TPL).render({},function(h){
			$(opt.elem).append(h);
		})
		var allPeople = '';
		myutil.getData({
			url: opt.ctx+'/system/user/findUserList?quit=0',
			success:function(d){
				for(var k in d){
					allPeople += '<option value="'+d[k].id+'">'+d[k].userName+'</option>';
				}
			}
		})
		var kindWork = [];
		var cols =[
		           { type:'checkbox' },
		           { title:'组名', 	field:'name', edit:true, },
		           ];
		if(opt.type==3){
			myutil.getDataSync({
				url: opt.ctx+'/basedata/list?type=kindWork',
				success:function(d){
					kindWork = d;
				}
			})
			cols.push({
				type:'select',select:{ data:kindWork, },title:'工种',field:'kindWork_id'
			})
		}
		cols.push({
			title:'人员信息', event:'lookover', templet:function(d){
	        	   return '<span class="layui-btn layui-btn-sm">查看人员</span>';}
		})
		mytable.renderNoPage({
			elem:'#tableData',
			url: opt.ctx+'/production/getGroup?type='+opt.type,
			size:opt.type==3?'lg':'',
			parseData:function(ret){
				if(ret.code==0) 
					ret.data.push({ id:0, name:'借调组', })
				return {  msg:ret.message,  code:ret.code , data:ret.data, };
			},
			autoUpdate:{
				saveUrl:'/production/addGroup',
				deleUrl:'/production/group/delete',
				field:{ kindWork_id:'kindWorkId', },
			},
			toolbar: '<span class="layui-btn layui-btn-sm" lay-event="addAllot">借调人员</span>',
			curd:{ 
				saveFun:function(data){
					for(var k in data){
						data[k].type = opt.type;
						myutil.saveAjax({
							url:'/production/addGroup',
							data: data[k],
						})
					}
					table.reload('tableData');
				},
				otherBtn: function(obj){
					if(obj.event == 'addAllot'){
						var area = ['28%','40%'];
						if(isSmall)
							area = ['80%','40%'];
						var html = '';
						laytpl(ADDNEW_TPL).render({},function(h){
							html = h;
						})
						var addNewWin = layer.open({
							type:1,
							area:area,
							offset:'80px',
							btn:['确定','取消'],
							btnAlign:'c',
							title:'新增借调人员',
							content:html,
							success:function(){
								laydate.render({
									elem:'#addNewTime',
									type:'datetime',
									value: isSmall?today:yesterDay,
								})
								$('#addUserId').append(allPeople);
								myutil.getData({
									url: opt.ctx+'/production/getGroup?type='+opt.type,
									success:function(d){
										var html = '';
										for(var k in d){
											html += '<option value="'+d[k].id+'">'+d[k].name+'</option>'
										}
										$('#addGroupId').append(html);
										form.render();
									}
								})
								form.on('submit(sureAdd)',function(obj){
									obj.field.type = opt.type;
									myutil.saveAjax({
										url:'/production/addTemporarily',
										data: obj.field,
										success:function(){
											layer.close(addNewWin);
										}
									})
								})
							},
							yes:function(){
								$('#sureAdd').click();
							}
						})
					}
				},
			},
			cols:[ cols],
		}) 
		table.on('tool(tableData)', function(obj){
			if(obj.event=='lookover'){
				var html = '';
				laytpl(LOOKOVER_TPL).render({},function(h){
					html = h;
				})
				if(obj.data.id==0 && (opt.type==4 || opt.type==5 ))
					return myutil.emsg('无外调人员');
				layer.open({
					type:1,
					area:[ isSmall?'80%':'60%','80%'],
					title:'人员信息',
					shadeClose:true,
					content: html,
					success:function(){
						var day = isSmall?today:yesterDay;
						laydate.render({
							elem:'#searchTime',
							value: day,
							type:'datetime'
						})
						if(obj.data.id==0){		//如果查看的是借调组人员
							mytable.renderNoPage({
								elem:'#lookoverTable',
								url: opt.ctx+'/production/getTemporarily?type='+opt.type,
								where:{  temporarilyDate: day, },
								size:'lg',
								autoUpdate:{
									saveUrl:'/production/updateTemporarily',
									deleUrl:'/production/deleteTemporarily',
									field:{ group_id:'groupId', },
								},
								curd:{
									btn:[4],
								},
								cols:[[
								    { type:'checkbox', },
									{ field:'user_userName', title:'人名' },
									{ field:'workTime', title:'工作时长',edit:true, },
									{ field:'group_id', type:'select', title:'所在小组', select: {data:table.cache['tableData'], } ,  },
									{ field:'status', title:'工作状态', templet:'<span>工作</span>', },
								]],
							})
						}else{
							mytable.renderNoPage({
								elem:'#lookoverTable',
								url: opt.ctx+'/production/allGroup?id='+obj.data.id,
								where:{ temporarilyDate: day,  },
								toolbar:'<div><span class="layui-btn layui-btn-danger layui-btn-sm" lay-event="deletes">批量删除</span></div>',
								cols:[ [
									{ type:'checkbox', },
									{ field:'name', title:'人名' },
									{ field:'time', title:'工作时长', edit:true,},
									{ field:'isTemp', title:'是否临时',filter:true, },
									{ field:'status', title:'工作状态', templet:getStatus(), }, //0休息1工作
								]],
								parseData:function(r){
									var data = [];
									if(r.code==0){
										if(r.data.userList)
											for(var k in r.data.userList){
												r.data.userList[k].isTemp = '否';
												data.push(r.data.userList[k])
											}
										if(r.data.temporarilyUser)
											for(var k in r.data.temporarilyUser){
												r.data.temporarilyUser[k].isTemp = '是';
												data.push(r.data.temporarilyUser[k])
											}
									}
									return {  msg:r.message,  code:r.code , data:data, }
								},
								done:function(){
									form.on('switch(changeStatus)',function(data){
										var index = $(data.elem).closest('tr').data('index');
										var trData = table.cache['lookoverTable'][index];
										var html = [
										            '<div style="padding:15px;">',
										                '<h3 style="text-align: center;color: gray;padding: 10px 0;">',
										                	'是否确认修改:'+trData.name+' 工作时长：'+trData.time+' 的工作状态？<h3>',
										                	'<form class="layui-form layui-form-pane" action="">',
										                	  '<div class="layui-form-item" pane>',
										                	    '<label class="layui-form-label">签出时间</label>',
										                	    '<div class="layui-input-block">',
										                	      '<input type="text" id="outTime" class="layui-input">',
										                	    '</div>',
										                	  '</div>',
										                	'</form>',
										                '</h2>',
										            '</div>',
										            ].join(' ');
										var confirm = layer.open({
											type:1,
											content: html,
											offset:'100px',
											area: ['45%','30%'],
											btn:['确定','取消'],
											success:function(){
												laydate.render({
													elem: '#outTime',
													type: 'datetime',
													value: new Date().format('yyyy-MM-dd hh:mm:ss'),
												})
											},
											yes: function(){
												myutil.saveAjax({
													url: '/production/updateManualTime',
													type: 'get',
													data:{
														id: trData.userId,
														status: data.elem.checked?1:0,
														time: $('#outTime').val(),
													},
													success:function(){
														layer.close(confirm);
													}
												})
											},
											end:function(){
												table.reload('lookoverTable');
											}
										})
									})
								}
							})
							function getStatus(){
								return function(d){
									var disabled = 'disabled',checked = '';
									if(isSmall && d.isTemp=='否')	//小屏且非临时，可修改
										disabled = '';
									if(d.status==1)
										checked = 'checked';
									return '<input type="checkbox" lay-filter="changeStatus" lay-skin="switch" lay-text="工作|休息" '+checked+' '+disabled+'>';
								}
							}
							table.on('toolbar(lookoverTable)',function(obj){
								var checked = layui.table.checkStatus('lookoverTable').data;
								if(obj.event=='deletes'){
									if(checked.length==0)
										return myutil.emsg('请选择信息删除');
									var temporarilyIds = [];
									for(var i in checked)
										if(checked[i].secondment=='1')
											return myutil.emsg('不能删除正式包装员工，请去工资总汇删除');
										else
											temporarilyIds.push(checked[i].userId);
									myutil.deleteAjax({
										url: '/production/deleteTemporarily',
										ids: temporarilyIds.join(','),
										success:function(){
											table.reload('lookoverTable');
										}
									})
								}
							})
							table.on('edit(lookoverTable)',function(obj){
								if(obj.data.secondment==0){
									if(isNaN(obj.data.time)){
										table.reload('lookoverTable');
										return myutil.emsg('只能修改为数字！');
									}
									else
										myutil.saveAjax({
											url:'/production/updateTemporarily',
											data:{ 
												id:obj.data.userId,
												workTime:obj.data.time,
											},
											success:function(){
												table.reload('lookoverTable');
											}
										})
								}else{
									table.reload('lookoverTable');
									return myutil.emsg('不能修改正式包装员工');
								}
							})
						}
						form.on('submit(search)',function(obj){
							table.reload('lookoverTable',{
								where: obj.field,
							})
						});
					}
				})
			}
		})
	}
	usergroup.render = function(opt){
		var s = new Class();
		myutil.config.ctx = opt.ctx;
		s.render(opt);
	}
	exports(MODNAME,usergroup);
})