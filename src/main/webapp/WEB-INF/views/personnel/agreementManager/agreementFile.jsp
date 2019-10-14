<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>合同文件</title>
</head>
<body>

<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>合同种类:</td>
				<td style="width:150px;">
					<select name="contractKindId" id="searchKind" lay-search><option value="">请选择</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td>合同类型:</td>
				<td style="width:150px;">
					<select name="contractTypeId" id="searchType" lay-search><option value="">请选择</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td style="width:120px;"><select name="searchTimeType">
						<option value="star">开始时间</option>
						<option value="end">结束时间</option></select></td>
				<td><input name="orderTimeBegin" class="layui-input" id="searchStar"></td>
				<td>&nbsp;&nbsp;</td>
				<td>是否有效:</td>
				<td style="width:80px;"><select name="flag"><option value="">请选择</option>
										<option value="0">无效</option>
										<option value="1" selected>有效</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn" lay-submit lay-filter="search">搜索</button></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" id="warm" class="layui-btn">预警<span class="layui-badge" id="warmNumber">0</span></button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>

</body>
<!-- 新增修改模板 -->
<script type="text/html" id="addEditTpl">
<div class="layui-form layui-form-pane" style="padding:20px;">
  <div class="layui-form-item">
    <label class="layui-form-label">合同种类</label>
    <div class="layui-input-block">
      <select name="contractKindId" lay-verify="required"></select>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">合同类型</label>
    <div class="layui-input-block">
      <select name="contractTypeId" lay-verify="required">
      </select>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">合同年限</label>
    <div class="layui-input-block">
      <input type="text" name="duration" lay-verify="number" value="{{d.duration}}" placeholder="请输入合同年限" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">开始时间</label>
    <div class="layui-input-block">
      <input type="text" name="starTime" id="starTime"  
			lay-verify="required" placeholder="请输入开始时间" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">结束时间</label>
    <div class="layui-input-block">
      <input type="text" name="endTime" id="endTime"  
			lay-verify="required" placeholder="请输入结束时间" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">合同内容</label>
    <div class="layui-input-block">
      <input type="text" name="content" value="{{ d.content }}" 
			lay-verify="required" placeholder="请输入合同内容" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">合同金额</label>
    <div class="layui-input-block">
      <input type="text" name="amount"  value="{{ d.amount }}" 
			lay-verify="number" placeholder="请输入保险金额" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">公司</label>
    <div class="layui-input-block">
      <input type="text" name="company"  value="{{ d.company }}" 
			 placeholder="请输入公司" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">是否有效</label>
    <div class="layui-input-block">
      <input type="checkbox" name="flag" value="1" lay-text="有效|无效" lay-skin="switch" {{ d.flag==1?'checked':'' }}>
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">是否有效</label>
    <div class="layui-input-block">
      <button type="button" class="layui-btn layui-btn-sm" id="uploadPic">
  			<i class="layui-icon">&#xe67c;</i>上传图片</button>
    </div>
  </div>
 

  <input type="hidden" name="id" value="{{d.id}}">
  <span style="display:none;" id="sureBtn" lay-submit lay-filter="sureBtn"></span>
</div>
</script>
<!-- 表格工具栏模板 -->
<script type="text/html" id="tableTool">
<div>
	<span lay-event="add"  class="layui-btn layui-btn-sm" >新增合同</span>
	<span lay-event="update"  class="layui-btn layui-btn-sm" >修改合同</span>
</div>
</script>
</body>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
}).define(
	['mytable','laydate','upload'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form	
		, upload = layui.upload 
		, table = layui.table 
		, myutil = layui.myutil
		, laydate = layui.laydate
		, mytable = layui.mytable
		, laytpl = layui.laytpl;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		var allType = [],allKind = [];
		$('#warm').click(function(){
			layer.open({
				type:1,
				title:'合同预警',
				area:['40%','60%'],
				shadeClose:true,
				content:'<div style="padding:0 10px;"><table id="warmTable" lay-filter="warmTable"></div>',
				success:function(){
					mytable.renderNoPage({
						elem: '#warmTable',
						url: '${ctx}/contract/remindContract',
						cols: [[
						        { title:'合同种类', field:'kind' },
						        { title:'合同类型', field:'type' },
						        { title:'结束时间', field:'time' },
						        { title:'合同内容', field:'content' },
						        ]]
					})
				}
			})
		})
		myutil.getDataSync({
			url:'${ctx}/contract/remindContract',
			success:function(d){
				if(d.length>0){
					$('#warmNumber').html(d.length);
					$('#warm').click();
				}else
					$('#warmNumber').html(0);
			}
		})
		myutil.getData({
			url:'${ctx}/basedata/list?type=contractType&page=1&limit=10',
			success:function(d){
				for(var key in d){
					allType += [
					            '<option value="'+d[key].id+'">',
					            	d[key].name,
					            '</option>',
					            ].join('');
				}
				$('#searchType').append(allType);
				myutil.getData({
					url:'${ctx}/basedata/list?type=contractKind&page=1&limit=10',
					success:function(d){
						for(var key in d){
							allKind += [
							            '<option value="'+d[key].id+'">',
							            	d[key].name,
							            '</option>',
							            ].join('');
						}
						$('#searchKind').append(allKind);
						form.render();
					}
				})
			}
		})
		
		laydate.render({
			elem:'#searchStar',
			type:'date',
			range:'~'
		})
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/contract/findContract',
			where:{ flag:1 },
			toolbar:'#tableTool',
			cols:[[
			       {type:'checkbox',},
			       {title:'合同种类',   field:'contractKind_name',	},
			       {title:'公司',   		field:'company',	},
			       {title:'合同类型',   field:'contractType_name',	},
			       {title:'合同年限',   field:'duration',	},
			       {title:'开始时间',   field:'starTime',	},
			       {title:'结束时间',   field:'endTime',	},
			       {title:'合同内容',   field:'content',	},
			       {title:'保险金额',   field:'amount',	},
			       {title:'是否有效',   field:'flag',	 transData:{data:['无效','有效']} },
			       {title:'查看',   templet:getLookBtn(),event:'lookPic' },
			       ]]
		})
		function getLookBtn(){
			return function(d){
				return '<p><span class="layui-btn layui-btn-sm" data-pic="'+d.pictureUrl+'">查看照片</span></p>';
			}
		}
		form.on('submit(search)',function(obj){
			obj.field.starTime = '';
			obj.field.endTime = '';
			if(obj.field.searchTimeType == 'star'){
				obj.field.starTime = '2019-09-29 00:00:00';
			}else
				obj.field.endTime = '2019-09-29 00:00:00';
			delete obj.field.searchTimeType;
			if(obj.field.orderTimeBegin){
				var time = obj.field.orderTimeBegin;
				obj.field.orderTimeBegin = time.split('~')[0].trim()+' 00:00:00';
				obj.field.orderTimeEnd = time.split('~')[1].trim()+' 23:59:59';
			}else{
				obj.field.orderTimeEnd = '';
			}
			table.reload('tableData',{
				where: obj.field
			})
		}) 
		table.on('toolbar(tableData)',function(obj){
			switch(obj.event){
			case 'add':	addEdit('add');		break;
			case 'update':	addEdit('edit'); 	break;
			case 'delete':	deletes();			break;
			}
		})
		table.on('tool(tableData)',function(obj){
			layer.open({
				type:1,
				area:['50%','50%'],
				content:'<div><img src="${ctx}'+obj.data.pictureUrl+'"></div>'
			})
		})
		
		function addEdit(type){
			var data={ id:'',contractKind:{name:''},contractType:{name:''},duration:'',
					starTime:'',endTime:'',content:'',amount:'',flag:1,company:'', pictureUrl:'',},
			choosed=layui.table.checkStatus('tableData').data,
			tpl=addEditTpl.innerHTML,
			title='新增合同',
			html='';
			if(type=='edit'){
				var msg = '';
				choosed.length>1 && (msg = "不能同时编辑多条信息");
				choosed.length<1 && (msg = "至少选择一条信息编辑");
				if(choosed.length!=1)
					return myutil.emsg(msg);
				data=choosed[0];
				title="修改合同";
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			var picUrl = data.pictureUrl;
			var addEditWin=layer.open({
				type:1,
				title:title,
				area:['40%','75%'],
				content:html,
				btn:['确定','取消'],
				btnAlign :'c',
				success: function(){
					var kid = data.contractKind.id || 0;
					var tid = data.contractType.id || 0;
					$("select[name='contractKindId']").append(allKind);
					$("select[name='contractTypeId']").append(allType);
					$("select[name='contractKindId']").find('option[value="'+kid+'"]').prop('selected','selected');
					$("select[name='contractTypeId']").find('option[value="'+tid+'"]').prop('selected','selected');
					laydate.render({
						elem:'#starTime',
						type:'date',
						value: data.starTime.split(' ')[0]
					})
					laydate.render({
						elem:'#endTime',
						type:'date',
						value: data.endTime.split(' ')[0]
					})
					upload.render({
					   elem: '#uploadPic' //绑定元素
					   ,url: '${ctx}/upload' 
					   ,data:{ filesTypeId:361, }
					   ,done: function(res, index, upload){
					    if(res.code == 0){
					    	picUrl.push(res.data.url);
					    }else
					   		myutil.emsg(res.message);
					  }
					});
					form.on('submit(sureBtn)',function(obj){
						obj.field.starTime += ' 00:00:00';
						obj.field.endTime += ' 00:00:00';
						obj.field.flag = obj.field.flag || 0;
						obj.field.pictureUrl = picUrl.join(',');
						myutil.saveAjax({
							url:'/contract/addContract',
							data:obj.field,
							success:function(result){
								table.reload('tableData');
								layer.close(addEditWin);
							},
						}) 
					})
					form.render();
				},
				yes:function(){
					$('#sureBtn').click();
				}
			})
			
		}
		
	}//end define function
)//endedefine
</script>
</html>