<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<script src="${ctx}/static/js/common/iframeResizer.contentWindow.min.js"></script> 
<title>角色管理</title>





</head>

<body>
	
<div class="layui-card">
	<div class="layui-card-body">
		<div class="layui-form layui-card-header layuiadmin-card-header-auto">
			<table>
				<tr>
					<td>用户名：</td>
					<td><input type="text" class="layui-input" placeholder="请输入用户名"></td>
					<td>角色名：</td>
					<td><select name="city" lay-verify="required">
						        <option value=""></option>
						        <option value="0">北京</option>
						      </select></td>
				</tr>
			</table>
		</div>
		
	
	
		<div id="LAY-role-table" class="table_th_search" lay-filter="LAY-role-table"></div>
	</div>

	
</div>



<div class="layui-btn-container layui-inline layui-form" style="display:none" id="addPermission">
   	<table>
		<tbody>
			<tr id="tr-select"><td>选择菜单：</td><td style="width:150px;"><select class="layui-input" id="first-menus" lay-filter="first-menus">
											<option value="" >选择菜单</option></select></td><td>&nbsp;&nbsp;</td>
			</tr>	
			<tr><td><span class="layui-btn layui-btn-sm" lay-event="">确定</span></td></tr>							
		</tbody>
	</table>
</div>
	

			
<!-- 选择框组件 -->
<script type="text/html" id="switchTpl">
 	 	<input type="checkbox" name="isShow" value="{{d.isShow}}" lay-skin="switch" lay-text="可用|不可用" lay-filter="isShow" {{ d.isShow == true ? 'checked' : '' }} >
	</script>

<script type="text/html" id="toolbarDemo">
  	<div class="layui-btn-container layui-inline">
    	<span class="layui-btn layui-btn-sm" lay-event="getChecked">获得选中的数据</span>
   		<span class="layui-btn layui-btn-sm" lay-event="deleteSome">批量删除</span>
    	<span class="layui-btn layui-btn-sm" lay-event="addRole">添加角色</span>
    	<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="saveAddRole">保存角色添加</span>
    	<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanAddRole">清空添加角色</span>
	</div>
</script>

<script type="text/html" id="aboutPermission">
		<button type="button" class="layui-btn layui-btn-sm" lay-event="aaa">查看权限</button>
</script>

<script type="text/html" id="addPermission">
  	
</script>
<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script> 
<script>

function removePermission(id){  //删除某一角色中的权限,删除权限只需要删除表中的某一条数据，因此只需要传该数据的id
		//layer.msg("修改成功！", {icon: 1});
		layer.msg("删除失败！", {icon: 2});
}	
function addPermission(roleId){    //给某一角色添加权限，需要该角色的id，和相关权限信息
	//$('#addPermission').show();
	var addPer=layer.open({
		 title: '添加权限'
		   ,type:1
		   ,area: ['30%', '80%']
		   ,content:$('#addPermission')
	})
	
	function showSelect(obj){
		console.log($('#dsadkjaaskjd').elem);
		var id=obj.value; 						//当前菜单id					
		var selectId;				//存放当前菜单子菜单select id
		if(obj.elem!=null)
			selectId=obj.elem.id+"-child";		//如果传值为jquer对象
		else
			selectId=obj.id;					//为dom对象
		var tdSelectId='td-'+selectId;          //存放当前菜单子菜单select的td 的id
		if(id==null||id==''){				//如果id为空即取消当前菜单的选择
			if(document.getElementById(selectId+'-child')!=null){   //如果子菜单下存在子菜单
				document.getElementById(selectId+'-child').value='';  
				showSelect(document.getElementById(selectId+'-child'));
			}
			document.getElementById(tdSelectId).innerHTML='';
		}else{
			for(var i=0;i<allMenu.length;i++){
				if(allMenu[i].id==id){   
					if(allMenu[i].parentId==0 ||allMenu[i].url=="#") {    //有下级菜单时
						if(document.getElementById(tdSelectId)!=null){  //如果存放select的td存在则清空内容
							document.getElementById(tdSelectId).innerHTML='';
						}else{  											//不存在，则拼接存放子菜单列表的td
							$('#tr-select').append('<td id="'+tdSelectId+'" style="width:150px;"></td><td>&nbsp;&nbsp;</td>');
						}
						var html='<select id="'+selectId+'"><option value="">请选择</option>';
						for(var j=0;j<allMenu.length;j++){                //拼接子菜单内容
							if(allMenu[j].parentId==id)
								html+=('<option value="'+allMenu[j].id+'">'+allMenu[j].name+'</optopn>');
						}
						html+='</select>';
						$('#td-'+selectId).append(html);
						if(document.getElementById(selectId+'-child')!=null){  //如果子子菜单存在
							document.getElementById(selectId+'-child').value='';  
							showSelect(document.getElementById(selectId+'-child'));
						}
						form.render();
						return;
					}
					else {   					//其他无下级菜单
						return;
					}
				}//end id=allMenu[i].id
			}//end for i<allMenu.length
		}//end else id!=null
	} //end showSelect
}
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug'
}).define(
	[ 'tablePlug', 'laydate' ],
	function() {
		var $ = layui.jquery
		, layer = layui.layer //弹层
		, form = layui.form //表单
		, table = layui.table //表格
		, laydate = layui.laydate //日期控件
		, tablePlug = layui.tablePlug; //表格插件
		
		// 处理操作列
		var fn1 = function(field) {
			return function(data) {
				return ['<select name="citye" lay-filter="city_selecte" lay-search="true">',
					'<option value="管理员">管理员</option>',
					'<option value="超级管理员">超级管理员</option>',
						'</select>' 
						].join('');
			};
		};
		
		table.render({
			elem : '#LAY-role-table',
			size : 'lg',
			url : '${ctx}/roles/page' ,
			request:{
				pageName: 'page' ,//页码的参数名称，默认：page
				limitName: 'size' //每页数据量的参数名，默认：limit
			},
			page : {},        //开启分页
			loading : true,     //开启加载动画
			toolbar : '#toolbarDemo',//开启工具栏，此处显示默认图标
			colFilterRecord : true,// 开启智能重载
			smartReloadModel : true,// 设置开启部分选项不可选
			done : function() {      //加载完后的动作
				var tableView = this.elem.next();
				tableView.find('.layui-table-grid-down').remove();
				var totalRow = tableView.find('.layui-table-total');
				var limit = this.page ? this.page.limit : this.limit;
				layui.each(totalRow.find('td'), function(index, tdElem) {
					tdElem = $(tdElem);
					var text = tdElem.text();
					if (text && !isNaN(text)) {
						text = (parseFloat(text) / limit).toFixed(2);
						tdElem.find('div.layui-table-cell').html(text);
					}
				});
				// 初始化laydate
				layui.each(tableView.find('td[data-field="birthday"]'), function(index, tdElem) {
					tdElem.onclick = function(event) {
						layui.stope(event)
					};
					laydate.render({
						elem : tdElem.children[0],
						format : 'yyyy/MM/dd',
						done : function(value, date) {
							var trElem = $(this.elem[0]).closest('tr');
							table.cache.demo[trElem.data('index')]['birthday'] = value;
						}
					})
				})
			},
			parseData : function(ret) {    //
				return {
					code : ret.code,
					msg : ret.msg,
					count : ret.data.total, 
					data : ret.data.rows
				}
			},
			checkStatus : {},
			cols : [ 
			[   {type: 'checkbox',align : 'center',fixed: 'left'},
				{field : "id",title : "ID",width : 80,sort : true}, 
				{field : "name",title : "角色名",edit : 'text'}, 
				{field : "role",title : "英文名称",edit : 'text'}, 
				{field : "roleType",title : "角色类型",align : 'center',search: true,edit: false, type: 'normal',templet:fn1('citye')}, 
				{field : "isShow",title : "是否可用",templet : '#switchTpl'},
				{field : "description",title : "具体描述",edit : 'text'},
				{title : "查看权限",templet : "#aboutPermission"}
			] ]
		});

		// 监听表格中的下拉选择将数据同步到table.cache中
		form.on('select(city_selecte)', function(data) {
			var selectElem = $(data.elem);
			var tdElem = selectElem.closest('td');
			var trElem = tdElem.closest('tr');
			var tableView = trElem.closest('.layui-table-view');
			table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
		});
		
		table.on('tool(LAY-role-table)',function(obj){   //监听查看权限按钮
			var data=obj.data;  
			var rp=data.resourcePermission;
			var permissionTable='<table class="layui-table" style="text-align:center"><thead><th>创建时间</th><th>菜单id</th><th>id</th><th>权限等级</th><th>'+
								'更新时间</th><th>移除</th></head><tbody>';
			if(rp.length>0){         //拼接权限的表格内容
				for(var i=0;i<rp.length;i++){
					var p=rp[i];
					permissionTable+=('<tr><td>'+p.createdAt+'</td><td>'+p.menuId+'</td><td>'+p.id+'</td><td>'+p.permissionIds[0]+'</td><td>'
										+p.updatedAt+'</td><td><button class="layui-btn layui-btn-sm" onclick="removePermission('+p.id+')">移除</button></td></tr>');
				}
			}
			else{
				permissionTable+='<tr><td colspan="6" style="text-align:center">该角色还没有权限</td></tr>';
			}
			permissionTable+='<tr><td colspan="6"><button class="layui-btn layui-btn-sm" onclick="addPermission('+data.id+')">新增权限</button></td></tr></tbody></table>';
			
			var aboutPermission=layer.open({     //打开查看权限内容的弹窗
				   title: '查看角色权限：'+data.name
				   ,type:1
				   ,area: ['80%', '80%']
				   ,content:permissionTable
			}); 
			
		})
		
		//监听头工具栏事件
	    table.on('toolbar(LAY-role-table)', function (obj) {
	      var config = obj.config;
	      var btnElem = $(this);
	      var tableId = config.id;
	      // var tableView = config.elem.next();
	      switch (obj.event) {
	        case 'addRole':  table.addTemp(tableId, function (trElem) { });
	         					 break;
	        case 'saveAddRole':
						          layer.alert('临时数据:' + JSON.stringify(table.getTemp(tableId).data));
						          break;
	        case 'cleanAddRole':
						          table.cleanTemp(tableId);  layer.msg('临时数据已删除');
						          break;
	        case 'openSelect':
					          layer.open({
					            type: 1,
					            title: '测试下拉效果单页面',
					            area: ['3000px', '160px'],
					            content: '<div class="layui-form" style="padding: 20px;"><select><option value="1">北京</option><option value="2">上海</option><option value="3">广州</option><option value="4">深圳</option></select></div>',
					            success: function (layero, index) {
					              form.render();
					            }
					          });
	          					break;
	        case 'openIframeSelect':
					          layer.open({
					            type: 2,
					            title: '测试下拉效果iframe',
					            shade: false,
					            area: ['300px', '160px'],
					            content: 'testIframe.html?time=' + new Date().getTime(),
					            success: function (layero, index) {
					            }
					          });
					          break;
	        case 'autoReload':
					          if (!layui._autoReloadIndex) {
					            layui._autoReloadIndex = setInterval(function () {
					              table.reload(tableId, {});
					            }, 300);
					          } else {
					            clearInterval(layui._autoReloadIndex);
					            layui._autoReloadIndex = 0;
					          }
					          break;
	        case 'LAYTABLE_EXPORT':  // 点击导出图标的时候
	          
					          $(this).find('.layui-table-tool-panel li').unbind('click').click(function () {
					            var dataTemp = table.cache[tableId];  // 干掉了原始的事件了，自己定义需要的
					            if (!dataTemp || !dataTemp.length) 
					              dataTemp = [{}];   // 处理如果没有数据的时候导出为空的excel，没有导出thead的问题
					            table.exportFile(tableId, dataTemp, $(this).data('type'));// 实际可以根据需要还可以直接请求导出全部，或者导出选中的数据而不是只导出当前的页的数据
					          });
					          break;
	        case 'getChecked':
					          layer.alert(JSON.stringify(table.checkStatus(tableId).data));
					          break;
	        case 'getCheckedStatus':
					          var status = table.checkStatus(tableId).status;
					          layer.alert('新增的：' + JSON.stringify(status[tablePlug.CHECK_TYPE_ADDITIONAL]) + '<br>'
					            + '删除的：' + JSON.stringify(status[tablePlug.CHECK_TYPE_REMOVED]));
					          break;
	        case 'deleteSome':
					          // 获得当前选中的，不管它的状态是什么？只要是选中的都会获得
					          var checkedIds = tablePlug.tableCheck.getChecked(tableId);
					          layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function () {
					            layer.alert('do something with: ' + JSON.stringify(checkedIds));
					          });
					          break;
	        case 'jump':
					          var pageCurr = btnElem.data('page');
					          table.reload(config.id, {url: 'json/data1' + pageCurr + '.json', page: {curr: pageCurr}});
					          break;
	        case 'reload':
				          var options = {page: {curr: 1}};
				          var urlTemp = btnElem.data('url');
				          if (urlTemp) {
				            options.url = 'json/' + urlTemp + '.json';
				          }
				          var optionTemp = eval('(' + (btnElem.data('option') || '{}') + ')');

				          table.reload(config.id, $.extend(true, options, optionTemp));
				          break;
	        case 'reloadIns':
				          tablePlug.getIns(config.id).reload({
				            // page: false
				          });
				          break;
	        case 'setDisabled':
				          // tablePlug.tableCheck.disabled(config.id, [10003, 10004, 10010]);
				          // table.reload(tableId, {});
				          tablePlug.disabledCheck(tableId, [10003, 10004, 10010]);
				          break;
	        case 'setDisabledNull':
				          tablePlug.disabledCheck(tableId, false);
				          break;
	        case 'ranksConversion':
				          // 表格行列转换
				          break;
	      }
	    });
		

		// tr点击触发复选列点击
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
		
		
	}
);
</script>



</body>
</html>