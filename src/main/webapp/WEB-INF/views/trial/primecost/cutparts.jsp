<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>裁片填写</title>
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
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<!-- 表格工具栏模板 -->
<script type="text/html" id="tableToolbar">
<div>
	<span lay-event="sendGoods"  class="layui-btn layui-btn-sm">一键发货</span>
</div>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, myutil = layui.myutil
		, laydate = layui.laydate
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr();
		laydate.render({
			elem:'#sendDateSearch',
			range:'~'
		})
		form.on('submit(search)',function(obj){
			var val = $('#sendDateSearch').val(),beg = '',end = '';
			if(val!=''){
				beg = val.split('~')[0].trim()+' 00:00:00';
				end = val.split('~')[1].trim()+' 23:59:59';
			}
			obj.field.orderTimeBegin = beg;
			obj.field.orderTimeEnd = end;
			table.reload('tableData',{
				where: obj.field
			})
		})
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/product/getCutParts',
			//toolbar:'#tableToolbar',
			autoUpdate:{
				saveUrl:'/product/addCutParts', 
			},
			curd:{
				/* addTemp:{cutPartsName:'',cutPartsNumber:1,perimeter:'',allPerimeter:'',materielName:'',composite:'',oneMaterial:'',unit:'',perimeter:'',
					scaleMaterial:'', manualLoss:'',productCost:'',productRemark:'',batchMaterial:'',batchMaterialPrice:'',addMaterial:'',id:''
				} */
			},
			//verify:{ count:[''], notNull:[''],price:[] },
			colsWidth:[0,6,6,6,5,0,6,6,6,6,6,6,6,4,6,7,7],
			cols:[[
			       { type:'checkbox',},
			       { title:'裁片名字',   	field:'cutPartsName',	},
			       { title:'使用片数',   	field:'cutPartsNumber',	},
			       { title:'单片周长',   	field:'perimeter',	},
			       { title:'总周长',   		field:'allPerimeter',	},
			       { title:'物料名称',   	field:'materielName',	},
			       { title:'是否复合',   	field:'composite',	},
			       { title:'单片用料',   	field:'oneMaterial',	},
			       { title:'单位',   		field:'unit',	},
			       { title:'单片周长',   	field:'perimeter',	},
			       { title:'用料占比',   	field:'scaleMaterial',  },
			       { title:'总用料',   		field:'addMaterial',  },
			       { title:'手动损耗', 		field:'manualLoss',  },
			       { title:'单价',   		field:'productCost',  },
			       { title:'备注',  	 		field:'productRemark',  },
			       { title:'当批单片用料',   field:'batchMaterial',  },
			       { title:'当批单片价格',   field:'batchMaterialPrice',  },
			       ]],
		})
	}
)
</script>
</html> 