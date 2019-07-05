<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>奖励机制</title>
<style>
.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
     top: 50%;
     transform: translateY(-50%);
}
</style>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-table" id="personTable" lay-filter="personTable"></table>
	</div>
</div>

</body>

<!-- 表格工具栏模板 -->
<script type="text/html" id="personToolbar">
<div>
	<span lay-event="getReward"  class="layui-btn layui-btn-sm" >领取奖金</span>
	<span class="layui-badge">提示：双击查看奖金详情</span>
</div>
</script>

<script type="text/html" id="rewardInfoToolbar">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增</span>
	<span lay-event="delete"  class="layui-btn layui-btn-sm layui-btn-danger" >删除</span>
	<span class="layui-badge">提示：双击修改信息</span>
</div>
</script>

</body>

<!-- 新增、修改模板 -->
<script type="text/html" id="addEditTpl">
<div class="layui-form" style="padding:10px;">
	<div class="layui-item">
		<label class="layui-form-label">时间</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" id="addEditTime" value="{{ d.time }}" name="time" lay-verify="required">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">被招聘人</label>
		<div class="layui-input-block">
			<select name="coverRecruitId" id="addEditCoverRecruit">
				{{# layui.each(allCoverRecruit,function(index,item){ }}
					<option value="{{ item.id }}">{{ item.name }}</option>
				{{# }) }}
			</select>
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">奖励</label>
		<div class="layui-input-block">
			<input class="layui-input" name="price" value="{{d.price}}" lay-verify="number">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block">
			<input class="layui-input" name="remarks" value="{{d.remarks}}">
		</div>
	</div>
	<input type="hidden" name="recruitId" value="{{d.recruitId}}">
	<input type="hidden" name="type" value="{{d.type}}">
	<input type="hidden" name="id" value="{{d.id}}">
	<p style="display:none;"><button lay-submit lay-filter="sure" id="addEditSureBtn">确定</button></p>
</div>
</script>
<script type="text/html" id="getRewardTpl">
<div class="layui-form" style="padding:10px;">
	<div class="layui-item">
		<label class="layui-form-label">领取人</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input"  value="{{ d.recruitName }}" >
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">剩余金额</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" value="{{ d.price - d.ReceivePrice }}" readonly id="canGetMoney">
		</div>
	</div>
	<div class="layui-item">
		<label class="layui-form-label">领取金额</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="price" lay-verify="number">
		</div>
	</div>
	<input type="hidden" name="recruitId" value="{{d.recruitId}}">
	<input type="hidden" name="type" value="1">
	<p style="display:none;"><button lay-submit lay-filter="getRewardSureBtn" id="getRewardSureBtn">确定</button></p>
</div>
</script>
<script>
var allCoverRecruit = [];	//所有被招聘人

layui.use(
	['laytpl','jquery','layer','form','table','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, laytpl = layui.laytpl;
		
		var lookoverRecruitId; 
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
			switch(obj.event){
			case 'getReward':	getReward();			break;
			} 
		})
		table.on('rowDouble(personTable)',function(obj){
			lookoverRecruitId = obj.data.recruitId;
			getCoverRecruit();	//获取此招聘人的被招聘人
			layer.open({
				type:1,
				title:'流水详情',
				area:['80%','80%'],
				offset:'50px',
				content:'<div><table class="layui-table" id="rewardInfoTable" lay-filter="rewardInfoTable"></table></div>',
				success:function(){
					table.render({
						elem:'#rewardInfoTable',
						url:'${ctx}/personnel/getReward?type=0&recruitId='+lookoverRecruitId,
						request:{ pageName:'page', limitName:'size' },
						parseData:function(ret){ return { data:ret.data.rows, count:ret.data.total,msg:ret.message, code:ret.code } },
						toolbar:'#rewardInfoToolbar',
						cols:[[
								{align:'center', type:'checkbox',},
								{align:'center', title:'时间',   field:'time',	},
								{align:'center', title:'招聘人',   field:'recruitId',	},
								{align:'center', title:'被聘人',   field:'coverRecruitId',	},
								{align:'center', title:'奖励',   field:'price',	},
								{align:'center', title:'备注',   field:'remarks',	},
						       ]],
					})
				},
			})
			table.on('toolbar(rewardInfoTable)',function(obj){
				switch(obj.event){
				case 'add':  addEdit(); break;
				case 'delete': deleteRewardInfo(); break;
				}
			})
			table.on('rowDouble(rewardInfoTable)',function(obj){
				addEdit(obj.data);
			})
		})
		function getReward(){
			var checked = layui.table.checkStatus('personTable').data;
			if(checked.length!=1){
				layer.msg('只能选择一个人进行奖金领取',{icon:2,offset:'200px'});
				return;
			}
			var html = '',tpl = getRewardTpl.innerHTML;
			laytpl(tpl).render(checked[0],function(h){ html = h;})
			layer.open({
				type:1,
				btn:['确定','取消'],
				area:['30%','50%'],
				offset:'100px',
				content:html,
				yes:function(){
					$('#getRewardSureBtn').click();
				}
			})
			form.on('submit(getRewardSureBtn)',function(obj){
				var msg = '';
				if(obj.field.price > $('#canGetMoney').val())
					msg = '领取失败！领取金额不能大于剩余领取金额';
				else if(obj.field.price < 0 )
					msg = '领取失败！领取金额不能为负数！';
				if(msg!=''){
					layer.msg(msg,{icon:2,offset:'280px'});
					return;
				}
				var load=layer.load(1);
				$.ajax({
					url:'${ctx}/personnel/addReward',
					type:'post',
					data:obj.field,
					async : false,
					success:function(result){
						var icon = 2;
						if(0==result.code){
							table.reload('personTable');
							icon = 1;
							layer.closeAll();
						}
						layer.msg(result.message,{icon:icon,offset:'200px'});
					},
				})
				layer.close(load);
			})
		}
	
		
		function addEdit(editData){
			var data={ id:'',time:'',recruitId:lookoverRecruitId,coverRecruitId:'',price:'',type:0,remarks:'', },
			tpl=addEditTpl.innerHTML,
			title='新增奖励流水',
			html='';
			if(editData){
				data=editData;
				title="修改奖励流水";
			}
			laytpl(tpl).render(data,function(h){ html=h; })
			var addEditWin = layer.open({
				type:1,
				title:title,
				btn: ['确定','取消'],
				area:['30%','35%'],
				content:html,
				yes:function(){
					$('#addEditSureBtn').click();
				},
				success:function(){
					laydate.render({
						elem:'#addEditTime',
						type:'datetime',
					})
				}
			})
			form.render();
			form.on('submit(sure)',function(obj){
				var load=layer.load(1);
				$.ajax({
					url:'${ctx}/personnel/addReward',
					type:'post',
					data:obj.field,
					async : false,
					success:function(result){
						var icon = 2;
						if(0==result.code){
							table.reload('rewardInfoTable');
							icon = 1;
							layer.close(addEditWin);
						}
						layer.msg(result.message,{icon:icon,offset:'200px'});
					},
				})
				layer.close(load);
			})
		}
		function deleteRewardInfo(){
			var checked = layui.table.checkStatus('rewardInfoTable').data;
			if(!checked)
				layer.msg('请选择信息删除',{icon:2,offset:'200px'});
			else
				layer.confirm("是否确认删除？",{offset:'200px'},function(){
					var ids = [];
					for(var i=0;i<checked.length;i++)
						ids.push(checked[i].id);
					var load = layer.load(1);
					$.ajax({
						url:'${ctx}/personnel/deleteReward',
						traditional:true,
						async:false,
						data:{ids:ids},
						success:function(r){
							var icon=2;
							if(0==r.code){
								icon=1;
								table.reload('rewardInfoTable');
							}
							layer.msg(r.message,{icon:icon,offset:'200px'});
						}
					})
					layer.close(load);
				})
		}
		function getCoverRecruit(){			//获取所有被招聘人
			$.ajax({
				url: '${ctx}/personnel/findCondition?recruitId='+lookoverRecruitId,
				async:false,
				success:function(r){
					allCoverRecruit = r.data;
				}
			})
		}
	}//end define function
)//endedefine
</script>

</html>