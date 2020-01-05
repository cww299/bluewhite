<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>奖励汇总</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-table" id="personTable" lay-filter="personTable"></table>
	</div>
</div>

<div style="display: none;" id="layuiShare">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>查询时间:</td>
							<td><input id="monthDate3" style="width: 180px;" placeholder="请输入时间" class="layui-input laydate-icon">
							</td>
							<td>&nbsp;&nbsp;</td>
							<td><select class="form-control" name="type">
							<option value="">请选择</option>
							<option value="0">奖励金额</option>
							<option value="1">领取金额</option></select></td>
							<td>&nbsp;&nbsp;</td>
							<td>
								<div class="layui-inline">
									<button class="layui-btn layuiadmin-btn-admin"  lay-submit lay-filter="LAY-search2">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="layuiShare2"  class="table_th_search" lay-filter="layuiShare"></table>
</div>
</body>
<!-- 表格工具栏模板 --> 
<script type="text/html" id="personToolbar">
<div>
	<span lay-event="lookoverGetMoney"  class="layui-btn layui-btn-sm" >领取流水</span>
	<span lay-event="lookoverAward"  class="layui-btn layui-btn-sm" >奖励流水</span>
	<span lay-event="lookoverSummary"  class="layui-btn layui-btn-sm" >汇总</span>
</div>
</script>
<script type="text/html" id="rewardInfoToolbar">
<div>
	<span class="layui-btn layui-btn-sm" lay-event="addTempData">新增一行</span>
	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空新增行</span>
	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveTempData">批量保存</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
</div>
</script>
</body>
<script>
var allCoverRecruit = [];	//所有被招聘人
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug',
}).use(
	['laytpl','jquery','layer','form','table','laydate','tablePlug'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, tablePlug = layui.tablePlug
		, laydate = layui.laydate
		, laytpl = layui.laytpl;
		
		laydate.render({
			elem: '#monthDate3',
			type : 'date',
			range:'~'
		});
		tablePlug.smartReload.enable(true);  
		var lookoverObj; 	//查看的行对象的数据
		table.render({
			elem:'#personTable',
			url:'${ctx}/personnel/listGroupRecruit',
			toolbar:'#personToolbar',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data, msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', type:'checkbox',},
			       {align:'center', title:'姓名',   field:'recruitName',	},
			       {align:'center', title:'奖励',   field:'price',	},
			       {align:'center', title:'已领取金额',   field:'ReceivePrice',	},
			       ]]
		})
		table.on('toolbar(personTable)',function(obj){
			var checked = layui.table.checkStatus('personTable').data;
			
			if(checked.length!=1 && obj.event!='lookoverSummary'){
				layer.msg('只能选择一条数据',{icon:2,offest:'200px'});
				return;
			}
			lookoverObj = checked[0];
			switch(obj.event){
			case 'lookoverAward': 	lookoverAward(0);      break;
			case 'lookoverGetMoney':lookoverAward(1); 	break;
			case 'lookoverSummary': 
				var dicDiv=$('#layuiShare');
				table.reload("layuiShare2");
				layer.open({
			         type: 1
			        ,title: '每月汇总' //不显示标题栏
			        ,closeBtn: false
			        ,zindex:-1
			        ,area:['70%', '91%']
			        ,shade: 0.5
			        ,id: 'LAY_layuipro10' //设定一个id，防止重复弹出
			        ,btn: ['取消']
			        ,btnAlign: 'c'
			        ,moveType: 1 //拖拽模式，0或者1
			        ,content:dicDiv
			        ,success : function(layero, index) {
			        	layero.addClass('layui-form');
						// 将保存按钮改变成提交按钮
						layero.find('.layui-layer-btn0').attr({
							'lay-filter' : 'addRole2',
							'lay-submit' : ''
						})
			        }
			        ,end:function(){
			        	$("#layuiShare").hide();
					  } 
			      });
				break;
			} 
		})
		form.on('submit(LAY-search2)', function(obj) {
						onlyField=obj.field;
						var orderTime=$("#monthDate3").val().split('~');
						if($("#monthDate3").val()!=""){
							onlyField.orderTimeBegin=orderTime[0]+' '+'00:00:00';
							onlyField.orderTimeEnd=orderTime[1]+' '+'23:59:59';	
						}
						onlyField.type=onlyField.type;
						eventd(onlyField);
					})
		var eventd=function(data){
			table.reload("layuiShare2", {
				url: '${ctx}/personnel/getReward',
				where:data,
              })
		};
		var data="";
		table.render({
			elem:'#layuiShare2',
			where:data,
			/* url: '${ctx}/personnel/getReward' , */
			data:[],
			request:{ pageName:'page', limitName:'size' },
			limit:12,
			page:true,
			toolbar: '#toolbar5', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
			totalRow: true,		 //开启合计行 */
			parseData: function(ret) {
				return {
					code: ret.code,
					msg: ret.message,
					data: ret.data.rows,
					count:ret.data.total,
				}
			},
			cols: [[
				{align:'center', type:'checkbox',totalRowText: '合计'},
				{align:'center', title:'时间',   field:'time',templet:function(d){ return d.time==null ? "" : /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.time)}},
				{align:'center', title:'招聘人',   field:'recruitId', edit:false,templet:function(d){ return d.recruitName.recruitName; }},
				{align:'center', title:'奖励',   field:'price', edit:true,totalRow: true},
				{align:'center', title:'被聘人', templet:function(d){return d.recruitName==null ? "" : d.recruitName.name},edit:false,},
				{align:'center', title:'入职时间',   field:'entry', edit:false,templet:function(d){ return d.recruitName.user==null ? "" : /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.recruitName.user.entry)}},
				{align:'center', title:'离职时间',   field:'quitDate', edit:false,templet:function(d){ return d.recruitName.user.quitDate==null ? "" : /\d{4}-\d{1,2}-\d{1,2}/g.exec(d.recruitName.user.quitDate)}},
				{align:'center', title:'备注',   field:'remarks',	edit:true,},
				{align:'center', title:'备注',   field:'type',	edit:true,templet:function(d){return d.type==0 ? "<span class='layui-badge '>奖励金额</span>" :"<span class='layui-badge layui-bg-green'>领取金额</span> "}},
			]],
					});
		
		
		function lookoverAward(type){
					 
			var cols = [
						{align:'center', type:'checkbox',totalRowText: '合计'},
						{align:'center', title:'时间',   field:'time',	},
						{align:'center', title:'招聘人',   field:'recruitId', edit:false,templet:function(d){ return d.recruitName.recruitName; }	},
					    ];
			if(type==1){
				cols.push({align:'center', title:'领取奖励',   field:'price', edit:true,totalRow: true});
				cols.push({align:'center', title:'被聘人',   field:'coverRecruitId',templet:getSelectHtml(),edit:false,	});
				cols.push({align:'center', title:'入职时间',   field:'entry', edit:false,templet:function(d){ return d.recruitName.user.entry==null ? "" :d.recruitName.user.entry;}});
				cols.push({align:'center', title:'离职时间',   field:'quitDate', edit:false,templet:function(d){ return d.recruitName.user.quitDate==null ? "" : d.recruitName.user.quitDate;}});
			}
			else{
				cols.push({align:'center', title:'被聘人',   field:'coverRecruitId',templet:getSelectHtml(),edit:false,	});
				cols.push({align:'center', title:'奖励',   field:'price',edit:true,totalRow: true});
			}
			cols.push({align:'center', title:'备注',   field:'remarks',	edit:true,});
			layer.open({
				type:1,
				title: ''+(type==1?'领取奖金':'') +'流水详情',
				area:['80%','80%'],
				offset:'50px',
				content:'<div>'+(type==1?'<table class="layui-form"><tr><td>应聘人:</td><td><select lay-search id="searchName" lay-filter="searchName"><option value="">获取数据中...</option></select></td><td>总奖励：</td><td><input type="text" readonly="readonly" id="collarPrice" lay-verify="required" class="layui-input"></td><td>剩余奖励：</td><td><input type="text" readonly="readonly" id="hairPrice" lay-verify="required" class="layui-input"></td></tr></table>':'')+'<table class="layui-table" id="rewardInfoTable" lay-filter="rewardInfoTable"></table></div>',
				success:function(){
					form.render();
					table.render({
						elem:'#rewardInfoTable',
						url:'${ctx}/personnel/getReward',
						where:{
							type:type,
							recruitId:lookoverObj.recruitId
						},
						request:{ pageName:'page', limitName:'size' },
						parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total,msg:ret.message, code:ret.code } },
						totalRow: true,
						toolbar:'#rewardInfoToolbar',
						cols: [cols],
						size: 'lg',
						done: function(){
							var tableView = this.elem.next();
							var tableElem = this.elem.next('.layui-table-view');
							layui.each(tableView.find('td[data-field="time"]'), function(index, tdElem) {	//渲染开始时间，并修改
								laydate.render({
									elem: tdElem.children[0],
									type: 'datetime',
									done: function(value, date) {
										var id = table.cache['rewardInfoTable'][index].id
										var postData = { id: id,    time: value, }
										saveData(postData,function(){
											table.reload('rewardInfoTable');
										});
									}
								})
							})
							form.on('select(userSelect)',function(obj){				
								var trElem = $(obj.elem).closest('tr');
								var index = trElem.data('index');			//当前行的索引
								table.cache['rewardInfoTable'][index]['coverRecruitId'] = obj.value;
								if(index<0)
									return;
								var id = table.cache['rewardInfoTable'][index]['id'];
								var postData = { id : id, coverRecruitId : obj.value, };
								saveData(postData);
							})
						}
					})
				},
			})
			getCoverRecruit();	
			table.on('edit(rewardInfoTable)',function(obj){
				if(!obj.data.id)
					return;
				var data = {
						coverRecruitId: obj.data.coverRecruitId,
						id: obj.data.id,
						recruitId: obj.recruitId,
						type: obj.type,
				};
				data[obj.field] = obj.value;
				var load = layer.load(1);
				saveData(data,function(){
					table.reload("personTable");
				});
				layer.close(load);
			})
			table.on('toolbar(rewardInfoTable)',function(obj){
				switch(obj.event){
				case 'addTempData': addTempData(type);
					break;
				case 'cleanTempData': table.cleanTemp('rewardInfoTable');
					break;
				case 'saveTempData': saveTempData();
					break;
				case 'delete': deleteRewardInfo('rewardInfoTable'); break;
				}
			})
		}
		
		form.on('select(searchName)',function(obj){
			
			   $.ajax({
				      url:"${ctx}/personnel/getfindReward",
				      data:{
				    	  coverRecruitId:obj.value
				      },
				      type:"GET",
				      async:false,
				      beforeSend:function(){
				    	  indextwo = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			  $(result.data).each(function(k,j){
		      				$("#collarPrice").val(j.collarPrice)
		      				$("#hairPrice").val(j.hairPrice)
		      			  });
		      			form.render();
		      			layer.close(indextwo);
				      }
				  });
			
			
			table.reload('rewardInfoTable', {
				where:{
					coverRecruitId:obj.value
				},
				page: { curr : 1 }
			});
		})
		
		function addTempData(type){
		var coverRecruitId=	$("#searchName").val()
		if(type==1 && coverRecruitId==""){
			return layer.msg('请先选择应聘人',{icon:2,offset:'200px'})
		}
			allField = {price:'',
						recruitId:lookoverObj.recruitId,
						remarks:'',
						time:'',
						
						recruitName:{
							user:{ entry:''},
							recruitName:lookoverObj.recruitName
						},
						coverRecruitId:(type==1 ? coverRecruitId : allCoverRecruit[0].id) ,
						type:type,
						 };
			table.addTemp('rewardInfoTable',allField,function(trElem) {
				var timeTd = trElem.find('td[data-field="time"]')[0];
				laydate.render({
					elem: timeTd.children[0],
					type: 'datetime',
					done: function(value, date) {
						var trElem = $(this.elem[0]).closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						table.cache['rewardInfoTable'][trElem.data('index')]['time'] = value;
					}
				}) 
			});
	 	}
		function saveTempData(){
			var tempData = table.getTemp('rewardInfoTable').data;
			if(tempData.length==0){
				layer.msg('保存失败，无临时数据！',{icon:2,offset:'200px'});
				return;
			}
			for(var i=0;i<tempData.length;i++){
				var t = tempData[i];
				if( !t.price || !t.time ){
					layer.msg('新增数据字段不能为空！',{icon:2,offset:'200px'});
					return;
				}
				if(isNaN(t.price)){
					layer.msg('领取金额只能为数字！',{icon:2,offset:'200px'});
					return;
				}
			}
			var load = layer.load(1);
			var successAdd = 0;
			for(var i=0;i<tempData.length;i++){
				var data = {
						price: tempData[i].price,
						recruitId: tempData[i].recruitId,
						remarks: tempData[i].remarks,
						time: tempData[i].time,
						coverRecruitId: tempData[i].coverRecruitId,
						type: tempData[i].type,
				}
				saveData(data,function(){
					successAdd++;
				})
			}
			table.cleanTemp('rewardInfoTable');
			table.reload('rewardInfoTable',{ page:{ curr:1 } });
			table.reload("personTable");
			if(successAdd==tempData.length)
				layer.msg('成功新增：'+successAdd+'条数据',{icon:1});
			layer.close(load);
		}
		function deleteRewardInfo(tables){
			var checked = layui.table.checkStatus(tables).data;
			if(!checked)
				layer.msg('请选择信息删除',{icon:2,offset:'200px'});
			else
				layer.confirm("是否确认删除？",{offset:'200px'},function(){
					var ids = [];
					for(var i=0;i<checked.length;i++)
						ids.push(checked[i].id);
					deleteData(ids,function(){
						table.reload('rewardInfoTable');
					});
				})
		}
		function getSelectHtml(){
	 		return function(d){
	 			var html = '<select lay-filter="userSelect" lay-search>';
	 			layui.each(allCoverRecruit,function(index,item){
	 				var selected = (item.id==d.coverRecruitId?"selected":"");
	 				html+='<option value="'+item.id+'" '+selected+'>'+item.name+'</option>'
	 			})
	 			html+='</select>';
	 			if(d.coverRecruitId == '')
					layui.table.cache['rewardInfoTable'][d.LAY_INDEX]['coverRecruitId'] = allCoverRecruit[0].id;
	 			return html;
	 		} 
	 	}
		function getCoverRecruit(){			//获取所有被招聘人
			$.ajax({
				url: '${ctx}/personnel/findCondition?recruitId='+lookoverObj.recruitId,
				async:false,
				success:function(r){
					allCoverRecruit = r.data;
					var html = '<option value="">应聘人</option>';
					layui.each(r.data,function(index,item){
						html+='<option value="'+item.id+'" data-recruitName="'+item.recruitName+'" data-time="'+item.testTime+'">'+
							item.name+'  ('+item.orgName.name+'  '+item.position.name+')</option>';
					})
					$('#searchName').html(html);
					form.render();
				}
			})
		}
		function deleteData(ids,callback){
			var load = layer.load(1),
			result = false;
			$.ajax({
				url:'${ctx}/personnel/deleteReward',
				traditional:true,
				async:false,
				data:{ids:ids},
				success:function(r){
					var icon=2;
					if(0==r.code){
						icon=1;
						callback && callback();
					}
					layer.msg(r.message,{icon:icon,offset:'200px'});
				}
			})
			layer.close(load);
			return result;
		}
		function saveData(data,callback,error){
			var load = layer.load(1),
				result = false;
			$.ajax({
				url:'${ctx}/personnel/addReward',
				type:'post',
				data:data,
				async : false,
				success:function(result){
					var icon = 2;
					if(0==result.code){
						icon = 1;
						callback && callback();
					}else
						error && error();
					layer.msg(result.message,{icon:icon,offset:'200px'});
				},
			})
			layer.close(load);
			return result;
		}
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	}//end define function
)//endedefine
</script>

</html>