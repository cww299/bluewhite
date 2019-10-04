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
	                  	'<table style="width:100%;" class="layui-form">',
	                  		'<tr>',
	                  			'<td>外调时间：</td>',
	                  			'<td><input type="text" class="layui-input" name="temporarilyDate" id="addNewTime"></td>',
	                  		'</tr>',
	                  		'<tr>',
	                  			'<td>外调人员：</td>',
	                  			'<td><select name="userId" id="addUserId" lay-search lay-verify="required">',
	                  					'<option value="">请选择</option></select></td>',
	                  		'</tr>',
	                  		'<tr>',
		              			'<td>工作时长：</td>',
		              			'<td><input type="text" class="layui-input" name="workTime"></td>',
              				'</tr>',
		              		'<tr>',
			          			'<td>小组：</td>',
			          			'<td><select name="groupId" id="addGroupId" lay-search lay-verify="required">',
              						'<option value="">请选择</option></select>',
			          				'<span style="display:none;" lay-submit lay-filter="sureAdd" id="sureAdd"></span>',
			          			'</td>',
			          		'</tr>',
		          		'</table>',
	                  '</div>',
	                  ].join(' ');
	
	
	Class.prototype.render = function(opt){
		var now = new Date().format('yyyy-MM-dd 00:00:00');
		laytpl(TPL).render({},function(h){
			$(opt.elem).append(h);
		})
		var allPeople = '';
		myutil.getData({
			url:'/system/user/findUserList?quit=0',
			success:function(d){
				for(var k in d){
					allPeople += '<option value="'+d[k].id+'">'+d[k].userName+'</option>';
				}
			}
		})
		mytable.renderNoPage({
			elem:'#tableData',
			url: '/production/getGroup?type='+opt.type,
			parseData:function(ret){
				if(ret.code==0)
					ret.data.push({ id:0, name:'借调组', })
				return {  msg:ret.message,  code:ret.code , data:ret.data, };
			},
			autoUpdate:{
				saveUrl:'/production/addGroup',
				deleUrl:'/production/group/delete',
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
						var html = '';
						laytpl(ADDNEW_TPL).render({},function(h){
							html = h;
						})
						var addNewWin = layer.open({
							type:1,
							area:['28%','50%'],
							btn:['确定','取消'],
							title:'新增外调人员',
							content:html,
							success:function(){
								laydate.render({
									elem:'#addNewTime',
									type:'datetime'
								})
								$('#addUserId').append(allPeople);
								myutil.getData({
									url:'/production/getGroup?type='+opt.type,
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
			cols:[[
			       { type:'checkbox' },
			       { title:'组名', 	field:'name', edit:true, },
			       { title:'人员信息', event:'lookover', templet:function(d){
			    	   return '<span class="layui-btn layui-btn-sm">查看人员</span>';
			       	 } },
			       ]],
		}) 
		table.on('tool(tableData)', function(obj){
			if(obj.event=='lookover'){
				var html = '';
				laytpl(LOOKOVER_TPL).render({},function(h){
					html = h;
				})
				layer.open({
					type:1,
					area:['40%','80%'],
					title:'人员信息',
					content: html,
					success:function(){
						laydate.render({
							elem:'#searchTime',
							value:now,
							type:'datetime'
						})
						mytable.renderNoPage({
							elem:'#lookoverTable',
							url:'/production/allGroup?id='+obj.data.id,
							parseData:function(r){
								var data = [];
								if(r.code==0){
									if(r.data.temporarilyUser)
										for(var k in r.data.temporarilyUser){
											r.data.temporarilyUser[k].isTemp = '是';
											data.push(r.data.temporarilyUser[k])
										}
									if(r.data.userList)
										for(var k in r.data.userList){
											r.data.userList[k].isTemp = '否';
											data.push(r.data.userList[k])
										}
								}
								return {  msg:r.message,  code:r.code , data:data, }
							},
							where:{
								temporarilyDate: now,
							},
							cols:[[
							       { field:'name', title:'人名' },
							       { field:'time', title:'所在组工作时长',edit:true, },
							       { field:'isTemp', title:'是否临时',filter:true, },
							       ]],
							done:function(){
								table.on('edit(lookoverTable)',function(obj){
									var url = '';
									if(obj.data.isTemp=='是')
										url = '/system/user/addTemporaryUser';
									var data = {
										id: obj.data.id,
										time: obj.data.time,
									};
									myutil.saveAjax({
										url: url,
										data: data,
									})
								})
							}
						})
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