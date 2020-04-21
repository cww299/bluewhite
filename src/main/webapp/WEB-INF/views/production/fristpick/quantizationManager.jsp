<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/myModules/css/bacthManager.css" media="all">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>量化管理</title>
	<style>
		.processP{
			line-height: 27px;
    		color: gray;
		}
		.processP input {
			border: 1px solid #80808066;
		    width: 50px;
		    height: 23px;
		    padding: 0px 4px;
		}
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form searchTable">
			<tr>
			   <td>批次号：</td>
	           <td><input type="text" name="bacthNumber" class="layui-input" placeholder="请输入产品名称"></td>
	           <td>产品名称：</td>
	           <td><input type="text" name="productName" class="layui-input" placeholder="请输入产品名称"></td>
	           <td>时间：</td>
	           <td><input style="width:230px !important;" type="text" name="orderTimeBegin" id="searchTime" class="layui-input" placeholder="请输入时间"></td>
	           <td>完成状态：</td>
	           <td style="width:120px;"><select name="status">
	           						<option value="0">未完成</option>
	           						<option value="1">已完成</option></select></td>
	           <td><span class="layui-btn layui-btn-sm" lay-submit lay-filter="search">查找</span></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<!--分配任务模板 -->
<script type="text/html" id="ALLO_TPL">	
<div style="padding:15px;" class="layui-form">
  <div class="procedureDiv">
  	<div class="layui-form-item">
  		<label class="layui-form-label">任务数量：</label>
  		<div class="layui-input-block">
  			<input type="number" class="layui-input" value="{{ d.number || "" }}"
				name="taskNumber" lay-verify="number" id="taskNumberInput">
  		</div>
  	</div>
    <div class="layui-form-item">
  		<label class="layui-form-label">包装类型：</label>
  		<div class="layui-input-block">
  			<select name="id" lay-verify="required" id="packMethodSelect" value={{ d.packagMethodId || "" }}><option value=""></option></select>
  		</div>
  	</div>
  	<p>工序：</p>
   <div id="procedureTree"></div>
  </div>
  <div class="userDiv">
  	<div class="layui-form-item">
		<label class="layui-form-label">分配时间：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" id="allotTime" readonly>
		</div>
	</div>
    <div class="layui-form-item">
		<label class="layui-form-label" style="width: 70px;padding: 0px 18px;">单个OPP袋装个数</label>
		<div class="layui-input-block">
			<input type="number" class="layui-input" name="count" value={{ d.productCount || "" }} 
					lay-verify="number" style="width: 100px;display: inline;">
			<span class="layui-btn layui-btn-sm" lay-submit lay-filter="getProcess">获取工序</span>
		</div>
	</div>
   	<p>完成人：</p>
   	<div id="userTree"></div>
  </div>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable',
	menuTree : 'layui/myModules/menuTree',
}).define(
['mytable','laydate','menuTree'],
function(){
	var $ = layui.jquery
	, layer = layui.layer 				
	, form = layui.form			 		
	, table = layui.table 
	, laydate = layui.laydate
	, menuTree = layui.menuTree
	, myutil = layui.myutil
	, laytpl = layui.laytpl
	, mytable = layui.mytable
	,tablePlug = layui.tablePlug; 
	myutil.config.ctx = '${ctx}';
	myutil.clickTr();
	laydate.render({ elem: '#searchTime', range:'~', })
	var isSmall = false;
	var device = layui.device()
	if(device.android===true || device.ios===true)
		isSmall = true;
	var tableDataNoTrans = [];
	//var evenColor = 'rgb(133, 219, 245)';
	mytable.render({
		elem:'#tableData',
		size:'sm',
		url:'${ctx}/temporaryPack/findPagesQuantitative',
		where:{
			audit: 1
		},
		toolbar: '<span class="layui-btn layui-btn-sm" lay-event="disbatch">分配</span>'+
				 '<span class="layui-btn layui-btn-sm layui-btn-normal" lay-event="lookover">查看分配工序</span>',
		limit:15,
		//even:true,
		colFilterRecord:'local',
		limits:[15,50,200,500,1000],
		curd:{
			btn: [],
			otherBtn:function(obj){
				if(obj.event=="disbatch"){
					var check = table.checkStatus('tableData').data;
					if(check.length==0)
						return myutil.emsg("请选择数据分配");
					disbatch(check);
				}else if(obj.event=="lookover"){
					var check = table.checkStatus('tableData').data;
					if(check.length!=1)
						return myutil.emsg("请选择一条数据分配");
					lookover(check[0]);
				}
			},
		},
		autoUpdate:{
			deleUrl:'/temporaryPack/deleteQuantitative',
		},
		parseData:function(ret){
			if(ret.code==0){
				var data = [],d = ret.data.rows;
				tableDataNoTrans = d;
				for(var i=0,len=d.length;i<len;i++){
					var child = d[i].quantitativeChilds;
					delete child.remarks;
					if(!child)
						continue;
					for(var j=0,l=child.length;j<l;j++){
						data.push($.extend({},child[j],{childId: child[j].id,},d[i])); 
					}
				}
				return {  msg:ret.message,  code:ret.code , data: data, count:ret.data.total }; 
			}
			else
				return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
		},
		ifNull:'',
		cols:[[
		       { type:'checkbox',},
		       { title:'量化编号',   field:'quantitativeNumber', width:145,	},
		       { title:'包装时间',   field:'time', width:110, type:'date', },
		       { title:'客户',     field:'customer_name',	},
		       { title:'数量', 	field:'number', },
		       { title:'预计生产单价', 	field:'departmentPrice', templet: getFixed('departmentPrice',3),  },
		       { title:'外发价格', 	field:'outPrice', },
		       { title:'任务价值', 	field:'sumTaskPrice', templet: getFixed('sumTaskPrice',3),  },
		       { title:'地区差价	', 	field:'regionalPrice',templet: getFixed('regionalPrice',3),  },
		       { title:'当批用时	', 	field:'sumTime', templet: getFixed('sumTime',3),  },
		       { title:'备注	', 	field:'remarks', edit:true,},
		       { title:'批次号',    field:'underGoods_bacthNumber',	minWidth:130, },
		       { title:'产品名',    field:'underGoods_product_name', width:280,	},
		       { title:'单包个数',   field:'singleNumber',	width:80, },
		       ]],
       autoMerge:{
    	 field:['quantitativeNumber','time','customer_name','0','departmentPrice','number',
    		 'outPrice','sumTaskPrice','regionalPrice','sumTime','remarks'], 
    	 //evenColor: evenColor,
       },
       done:function(ret,curr, count){
			//background: white;
			/* var whiteTd = ['0','quantitativeNumber','vehicleNumber','time','sendTime','user_userName','customer_name','audit','flag','print'];
			layui.each(whiteTd,function(index,item){
				$('#tableData').next().find('td[data-field="'+item+'"]').css('background','white');
			})
			var blueTd = ['underGoods_bacthNumber','underGoods_product_name','actualSingleNumber','singleNumber','remarks'];
			layui.each(blueTd,function(index,item){
				$('#tableData').next().find('tr:nth-child(even) td[data-field="'+item+'"]').css('background',evenColor);
			}) */
			form.render();
		}
	})
	
	
	var allUser = [],allGroup = [],nullGroupUser = [];
	var allPackMethodHtml = myutil.getBaseDataSelect({ type: 'packagMethod'});
	myutil.getData({	//获取所有分组
		url: myutil.config.ctx+'/production/getGroup?type=2',
		success:function(d){
			allGroup = d;
			for(var i in allGroup){
				nullGroupUser.push({
					id:'no-'+allGroup[i].id,
					name:allGroup[i].name,
					children:[
					   {id:'no-0',name:'<span style="color:gray;">获取数据中.....</span>',}
					],
				})
			}
		},
	})
	function lookover(data){
		var area = isSmall?['100%','80%']:['80%','80%'];
		layer.open({
			type:1,
			title: data.quantitativeNumber,
			offset:'50px',
			shadeClose:true,
			area:area,
			content: '<div style="padding:15px;">'+
					'<table id="lookTable" lay-filter="lookTable"></table>'+
				'</div>',
			success: function(){
				mytable.render({
					elem:'#lookTable',
					url: myutil.config.ctx+'/task/allTask?quantitativeId='+data.id,
					autoUpdate:{
						deleUrl:'/task/delete',
					},
					curd:{
						btn:[4],
					},
					cols:[[
					       { type:'checkbox', },
					       { title:'分配时间', field:'allotTime',width:'15%'},
					       { title:'工序', field:'procedureName', },
					       { title:'预计时间', field:'expectTime', templet: getFixed('expectTime',4), },
					       { title:'任务价值', field:'expectTaskPrice', templet: getFixed('expectTaskPrice',4), },
					       { title:'b工资净值', field:'payB', templet: getFixed('payB',4), },
					       { title:'数量', field:'number', },
					       { title:'工序加价', field:'performance', },
					       { title:'加绩工资', field:'performancePrice', templet: getFixed('performancePrice',4), },
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
	function disbatch(check){
		var trData = check[0];
		layer.open({		//分配弹窗
			type:1,
			area: isSmall?['100%','100%']:['800px',"80%"],
			offset: isSmall?"0px":'20px',
			title: trData.quantitativeNumber,
			content: laytpl($('#ALLO_TPL').html()).render(trData),
			btn:['确定','取消'],
			btnAlign: 'c',
			success:function(){
				$('#taskNumberInput').val(trData.number);
				$('#packMethodSelect').append(allPackMethodHtml);
				$('#packMethodSelect').val(trData.packagMethod?trData.packagMethod.id:"");
				laydate.render({
					elem:'#allotTime',
					value: trData.time.split(' ')[0],
					done:function(val){
						getUserData(val+' 00:00:00');
					}
				});
				window.setTimeout(function() {
					getUserData(trData.time);
				},1)
				menuTree.render({				
		    	  elem:'#userTree',
		    	  data : nullGroupUser,
		    	  done: function(value){
		    		  $('#userTree').val(value);
		    	  }
				})
				form.on('submit(getProcess)',function(obj){
					obj.field.quantitativeId = trData.id;
					myutil.getData({
						url: myutil.config.ctx+"/processes/getProcesses",
						data:obj.field,
						success:function(d){
							
							//默认选中非上车工序，如果工序剩余剩余都为0.则选择上车工序
							var chooseCar = true;
							for(var i in d){
								if(d[i].name.indexOf('上车')<0 && d[i].surplusCount!=0){
									chooseCar = false;
									break;
								}
							}
							
							
							
							var html = "<div class='processP'>当前任务数量：<b id='taskNumbers'>"+obj.field.taskNumber+"</b></div>"; 
							html += "<table>";
							html += '<tr class="processP">',
							html += '<td><input type="checkbox" value="0" title="全选"  lay-skin="primary"'+
								' lay-filter="choosedAllProcess"></td><td></td></tr>';
							for(var i in d){
								var checked = "";
								if(chooseCar){
									if(d[i].name.indexOf('上车')>0)
										checked = "checked";
								}else{
									if(d[i].name.indexOf('上车')<0)
										checked = "checked";
								}
								html += '<tr class="processP">',
								html += '<td><input type="checkbox" '+checked+' value="'+d[i].id+'" data-name="'+d[i].name+'" title="'+d[i].name+
										'  (剩余：'+d[i].surplusCount+')" lay-skin="primary" data-time="'+d[i].time+'"></td><td>';
								if(d[i].isWrite)
									html += '<input type="text" placeholder="耗时" >';
								html += '</td></tr>';
							}
							$('#procedureTree').html(html+'</table>');
							form.on('checkbox(choosedAllProcess)',function(obj,checked){
								var checked = obj.elem.checked;
								layui.each($('#procedureTree input[type="checkbox"]'),function(index,item){
									var check = $(item).prop('checked');
									var value = $(item).val();
									if(value && check!=checked)
										$(item).click();
								})
								form.render();
							})
							form.render();
						}
					})
				})
				if(trData.productCount && trData.number && trData.packagMethod.id){
					$('span[lay-filter="getProcess"]').click();
				}
				form.render();
			},
			yes:function(layerIndex){
				var taskNumber = $('#taskNumbers').html();
				var allotTime = $('#allotTime').val();
				var ids = [],loanIds = [],temporaryIds=[];
				var userTreeId = menuTree.getVal('userTree');
				var text = "";
				if(!taskNumber)
					text = "请填写任务数量";
				else if(!allotTime)
					text = "请填写分配时间";
				else if(userTreeId.length==0)
					text = "请选择分配人员";
				if(text)
					return myutil.emsg(text);
				var jsonData = [];
				var isTrue = true;
				layui.each($('#procedureTree input[type="checkbox"]:checked'),function(index,item){
					var id =  $(item).attr('value');
					var name = $(item).data('name');
					var time =  $(item).data('time');
					if(!time){
						time = $(item).closest('tr').find('input[type="text"]').val();
						if(!time && id!="0"){
							isTrue = false;
							return;
						}
					}
					if(id)
						jsonData.push({
							id: id,
							name: name,
							time: time,
						})
				})
				if(jsonData.length==0)
					return myutil.emsg('请选择工序！');
				if(!isTrue)
					return myutil.emsg('请填写工序时间！');
				for(var i in userTreeId){
					var d = userTreeId[i];
					var id = d.split('~');
					if(id[0]=='no')
						continue;
					if(id[0]=="z")
						ids.push(id[1]);
					else if(id[0]=="l")
						temporaryIds.push(id[1]);
					else if(id[0]=='j')
						loanIds.push(id[1]);
				}
				if(ids.length==0 && temporaryIds.length==0 && loanIds==0)
					return myutil.emsg("请选择分配人员！");
				var allIds = [];
				for(var i in check)
					allIds.push(check[i].id)
				var data = {
					quantitativeIds: allIds.join(','),
					number: taskNumber,
					productCount: $('input[name="count"]').val(),
					packagMethodId:$ ('#packMethodSelect').val(),
					allotTime: allotTime+" 00:00:00",
					ids: ids.join(','),  	//正式
					loanIds: loanIds.join(','),	//借调
					temporaryIds: temporaryIds.join(','),//临时
					processesJson: JSON.stringify(jsonData),
				};
				myutil.saveAjax({
					url:'/task/addTaskPack',
					data: data,
					success:function(){
						layer.close(layerIndex);
						table.reload('tableData');
					}
				})
			}
		})
	}
	function getUserData(day){ //获取所有分组用户的树形结构数据
		allUser = [];
		for(var k in allGroup){
			(function(name,id){
				$.ajax({
					url: myutil.config.ctx+'/production/allGroup',
					async:false,
					data:{
						id: id,
						type: 2,
						temporarilyDate: day,
					},
					success:function(r){
						if(r.code==0){
							var groupPeople = r.data;
							var data = {
									id:'no~'+id,
									name: name,
									children:[]
							};
							if(groupPeople.temporarilyUser && groupPeople.temporarilyUser.length>0){
								var t = groupPeople.temporarilyUser;
								for(var k in t)
									if(t[k].status==1)
										var type = 'l';
										data.children.push({
											id: type+'~'+t[k].userId,
											name: t[k].name+' ---- <span class="layui-badge">临</span>',
										});
							}
							if(groupPeople.userList && groupPeople.userList.length>0){
								//  secondment =0 借调, secondment = 1 正式
								var t = groupPeople.userList;
								for(var k in t)
									if(t[k].status==1){
										var color = t[k].secondment?"green":"orange";
										var text = t[k].secondment?"正":"借";
										var type = t[k].secondment?"z":"j";
										data.children.push({
											id: type+'~'+t[k].userId,
											name: t[k].name+' ---- <span class="layui-badge layui-bg-'+color+'">'+text+'</span>',
										})
									}
							}
							allUser.push(data);
							if(allUser.length==allGroup.length){
								menuTree.reload('userTree',{
									data: allUser,
								})
							}
						}
					}
				})
			})(allGroup[k].name,allGroup[k].id);
		}
	}
	function getFixed(field,number){
		return function(d){
			return d[field]?d[field].toFixed(number):'---';
		}
	}
	form.on('submit(search)',function(obj){
		var time = obj.field.orderTimeBegin;
		if(time!=''){
			time = time.split('~');
			obj.field.orderTimeBegin = time[0].trim()+' 00:00:00';
			obj.field.orderTimeEnd = time[1].trim()+' 23:59:59';
			obj.field.time = obj.field.orderTimeBegin;
		}else{
			obj.field.orderTimeEnd = '';
			obj.field.time = "";
		}
		table.reload('tableData',{
			where: obj.field,
			page: { curr:1 },
		})
	})
})
</script>
</html>