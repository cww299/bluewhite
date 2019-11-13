<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<style type="text/css">
		.imgDiv{
			width:100px;
			float:left;
			margin:5px;
			height:100px;
			border: 3px solid gray;
		}
		.imgDiv:hover{
			border-color: #ff0000b8;
			cursor: pointer;
		}
		.imgDiv img{
			max-width:100%;
			height:100%;
			float:left;
		}
		#addEditImgDiv{
			height: 120px;
		    overflow-x: auto;
			margin:10px 0;
			padding:10px;
		}
		.closeBtn{
		    cursor: pointer;
		    margin-top: 1px;
		    margin-left: -18px;
		    border: 1px solid gray;
		    border-radius: 11px;
		    background: #9E9E9E;
		    float: right;
		}
		.closeBtn:hover{
		    background: #8080804f;
		}
		#imgDivLook{
			text-align:center;
			/* height: 90%; */
			/* overflow-y: scroll; */
		}
		.layui-layer-loading .layui-layer-content{
			width:auto !important;
		}
		.transparentLayer{
			background-color: #ffffff00 !important;
		}
	</style>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body" style="height:800px;">
		<table class="layui-form">
			<tr>
				<td>产品名：</td>
				<td><input type="text" class="layui-input" name="name" ></td>
				<td>&nbsp;&nbsp;</td>
				<td>产品编号：</td>
				<td><input type="text" class="layui-input" name="departmentNumber" ></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-filter="find" lay-submit>查找</button>
					<button type="button" class="layui-btn layui-btn-sm" id="uploadData">
						  <i class="layui-icon">&#xe67c;</i>导入数据</button></td>
		</table>
		<table class="layui-table" id="productTable" lay-filter="productTable"></table>
	</div>
</div>
</body>
<!-- 编辑修改产品信息 -->
<script type="text/html" id="addEditTpl">
<div class="layui-form" style="padding:20px;">
	<input type="hidden" name="id" value="{{ d.id }}">
	<div class="layui-form-item">
		<label class="layui-form-label">产品编号</label>
		<div class="layui-input-block">
			<input type="text" name="departmentNumber" lay-verify="required" class="layui-input" value="{{ d.departmentNumber }}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">产品名</label>
		<div class="layui-input-block">
			<input type="text" name="name" lay-verify="required" class="layui-input" value="{{ d.name }}">
		</div>
	</div>
	<p align="center"><button lay-submit class="layui-btn layui-btn-sm" type="button" lay-filter="sure">确定</button></p>
</div>
</script>
<!-- 工具栏 -->
<!-- <span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">删除产品</span> -->
<script type="text/html" id="toolbarOfProduct">
	<div class="layui-btn-container layui-inline layui-form">
		<span class="layui-btn layui-btn-sm" lay-event="add">新增产品</span>
		<span class="layui-btn layui-btn-sm" lay-event="edit">编辑产品</span>
	</div>	
</script>
<script>
layui.config({
	base:'${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
}).define(
	['mytable','upload'],
	function(){
		var $ = layui.jquery
		, table = layui.table
		, laytpl = layui.laytpl
		, layer = layui.layer
		, form = layui.form
		, mytable = layui.mytable
		, upload = layui.upload
		, myutil = layui.myutil; 		
		myutil.clickTr();
		form.on('submit(find)',function(obj){
			table.reload('productTable',{
				url:"${ctx}/productPages?",
				where:{
					page:1,
					name:obj.field.name,
					departmentNumber:obj.field.departmentNumber
				}
			});
		})
		upload.render({
		   	  elem: '#uploadData'
		   	  ,url: '${ctx}/excel/importProduct'
		 	  ,before: function(obj){
		 		layer.load(1); 
			  }
		   	  ,done: function(res, index, upload){ 
		   		layer.closeAll();
		   		layer.msg(res.message);
		   		table.reload('productTable');
		   	  } 
		   	  ,accept: 'file' 
		   	  ,exts: 'xlsx'
		})
		var fileIds = [];
		var fileUrl = [];
		var img = [];
		mytable.render({
			elem:'#productTable',
			size:'lg',
			url:"${ctx}/productPages",
			toolbar:"#toolbarOfProduct",
			curd:{
				btn:[4],	
			},
			autoUpdate:{
				deleUrl:'${ctx}/deleteProduct',
			},
			cols : [[  
			            {type: 'checkbox',align : 'center',fixed: 'left'},
						{field : "id",title : "ID", sort : true}, 
						{field : "departmentNumber",title : "产品编号", sort : true}, 
						{field : "name",title : "产品名",}, 
						{field : "url",title : "图片", templet:getTpl(),}, 
					]],
			done:function(){
				$('.lookoverPic').unbind().on('click',function(obj){
					var index = $(obj.target).closest('tr').data('index');
					var trData = table.cache['productTable'][index];
					var html = '<div style="padding:10px;" id="allImgDiv"><span style="display:none;" id="uploadPic">上传</span>';
					var length = img.length;
					img = trData.fileSet;
					fileIds = [];
					fileUrl = [];
					for(var i in img){
						html+='<div class="imgDiv"><img src="'+img[i].url+'" data-id="'+i+'"><i class="layui-icon layui-icon-close closeBtn"></i></div>';
						fileUrl.push(img[i].url);
						fileIds.push(img[i].id);
					}
					layer.open({
						type:1,
						area:['50%','50%'],
						content: html+'</div>',
						btn:['上传','保存','取消'],
						yes:function(){
							$('#uploadPic').click();
							return false;
						},
						btn2:function(){
							myutil.saveAjax({
								url:"${ctx}/updateProduct",
								type:"post",
								data:{
									id: trData.id,
									fileIds: fileIds.join(','),
								},
								success:function(result){
									if(result.code==0){
										layer.closeAll();
										layer.msg(result.message,{icon:1});
										table.reload('productTable');
									}
									else
										layer.msg(result.code+' '+result.message,{icon:2});
								}
							})
							return false;
						},
						btn3:function(){ },
						shadeClose:true,
						success:function(){
							upload.render({
							   elem: '#uploadPic' 
							   ,url: '${ctx}/upload' 
							   ,data:{ filesTypeId: 382, }
							   ,done: function(res, index, upload){
							    if(res.code == 0){
							    	fileIds.push(res.data.id);
							    	$('#allImgDiv').append('<div class="imgDiv"><img src="'+res.data.url+'"><i class="layui-icon layui-icon-close closeBtn"></i></div>');
									lookOverBigPic();
							    }else
							   		myutil.emsg(res.message);
							  }
							});
							lookOverBigPic();
						}
					})
				})
			}
		})
		
		function getTpl(){
			return '<div><span class="layui-btn layui-btn-sm lookoverPic">查看照片</span></div>';
		}
		table.on('toolbar(productTable)',function(obj){	
			switch(obj.event){
			case 'add': addEdit('add'); break;
			case 'edit': addEdit('edit'); break;
			case 'delete': deletes(); break;
			}
		})
		function addEdit(type){
			console.table(layui.table.checkStatus('productTable').data)
			var choosed=layui.table.checkStatus('productTable').data
				,data={id:'',name:'',departmentNumber:''}
				,html=''
				,tpl=addEditTpl.innerHTML;
			var typeName=(type=='add'?'新增':'修改');
			if('edit'==type){
				if(choosed.length>1){
					layer.msg("只能编辑一条数据",{icon:2});
					return;
				}
				if(choosed.length<1){
					layer.msg("请选择至少一条数据编辑",{icon:2});
					return;
				}
				data=choosed[0];
			}
			laytpl(tpl).render(data,function(h){
				html=h;
			})
			layer.open({
				type:1,
				title:typeName+'产品',
				content:html,
				area:['30%','30%']
			})
			form.render();
			var url=type=='add'?'addProduct':'updateProduct';
			form.on('submit(sure)',function(obj){
				//去除空格
				obj.field.name=obj.field.name.replace(/\s*/g,"");
				obj.field.departmentNumber=obj.field.departmentNumber.replace(/\s*/g,"");
				var load=layer.load(1);
				$.ajax({
					url:"${ctx}/"+url,
					type:"post",
					data:obj.field,
					success:function(result){
						if(result.code==0){
							layer.closeAll();
							layer.msg(result.message,{icon:1});
							table.reload('productTable');
						}
						else
							layer.msg(result.code+' '+result.message,{icon:2});
						layer.close(load);
					}
				})
			})
		}
		function deletes(){
			var choosed=layui.table.checkStatus('productTable').data;
			if(choosed.length<1){
				layer.msg("请选择至少一条数据删除",{icon:2});
				return;
			}
			layer.confirm("是否确认删除"+choosed.length+'条数据',function(){
				var load=layer.load(1);
				var successDel=0;
				var targetDel=choosed.length;
				for(var i=0;i<choosed.length;i++){
					$.ajax({
						url:"${ctx}/deleteProduct",
						data:{id:choosed[i].id},
						async:false,
						success:function(result){
							if(0==result.code){
								successDel++;
							}	
							else
								layer.msg(result.code+' '+result.message,{icon:2});
						},
						error:function(result){
							layer.msg(result.message,{icon:2});
						}
					})
				}
				if(successDel==targetDel){
					layer.msg("成功删除"+choosed.length+'条数据',{icon:1});
				}
				else{
					layer.msg("删除发生异常，删除目标数："+targetDel+' 实际删除数：'+successDel,{icon:2});
				}
				table.reload('productTable');
				layer.close(load);
			})
		}
		
		function lookOverBigPic(){
			$('.closeBtn').unbind().on('click',function(obj){
				var id = $(obj.target).data('id');
				fileIds.splice(fileIds.indexOf(id),1);
				$(obj.target).parent().remove();
			})
			$('.imgDiv').unbind().on('click',function(obj){
				var deg = 0;
				var imgElem = null;
				if($(obj.target).find('img').length>0)
					imgElem = $(obj.target).find('img');
				else
					imgElem = $(obj.target);
				var lookoverWin = layer.open({
					shadeClose:true,
					type:1,
					area:['100%','100%'],
					title:'查看照片',
					skin: 'transparentLayer',
					btn:['旋转','关闭',],	//'上一张','下一张',
					content:'<div id="imgDivLook"><img style="max-width:50%;max-height:100%;" src="'+$(imgElem).attr('src')+'"'+
							' data-id="'+$(imgElem).data('id')+'">',
					yes: function(index, layero){
						deg+=90;
						$('#imgDivLook').find('img').css('transform','rotate('+deg+'deg)');
				    },
					btn2: function(index, layero){
				    },
				   /*  btn3: function(index, layero){
				    	var id = $('#imgDivLook').find('img').attr('data-id');
				    	id = (id-1)<0?length-1:id-1;
				    	$('#imgDivLook').find('img').attr('src',img[id].url);
				    	$('#imgDivLook').find('img').attr('data-id',id);
				    	return false;
				    },
				    btn4: function(){ 
				    	$('#imgDivLook').find('img').data('id');
				    	var id = $('#imgDivLook').find('img').attr('data-id');
				    	id = (id-(-1))%length;
				    	$('#imgDivLook').find('img').attr('src',img[id].url);
				    	$('#imgDivLook').find('img').attr('data-id',id);
				    	return false;
					} */
				})
			})
		}
	}
)
</script>
</html>