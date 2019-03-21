<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>报销申请</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
</head>

<body>
	<section id="main-wrapper" class="theme-default">
		<%@include file="../../decorator/leftbar.jsp"%>
		<!--main content start-->
		<div id="demo" class="table_th_search" lay-filter="test"></div>	
	</section>

	</section>



<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	tablePlug : 'tablePlug/tablePlug'
}).define(
		[ 'tablePlug', 'laydate' ],function () {

		      var $ = layui.jquery
		        , layer = layui.layer //弹层
		        , form = layui.form //弹层
		        , table = layui.table //表格
		        , laydate = layui.laydate //日期控件
		        , tablePlug = layui.tablePlug; //表格插件
		     // 处理操作列
		      var fn1 = function (field) {
		          return function (data) {
		            // return data[field];
		            return [
		              '<select name="city" lay-filter="city_select" lay-search="true">',
		              '<option value="" >请选择或搜索</option>',
		              '<option value="北京" >北京</option>',
		              '<option value="天津" >天津</option>',
		              '<option value="上海" >上海</option>',
		              '<option value="广州" >广州</option>',
		              '<option value="深圳" >深圳</option>',
		              '<option value="佛山" >佛山</option>',
		              '<option value="佛山" >佛山</option>',
		              '<option value="佛山" >佛山</option>',
		              '</select>'
		            ].join('');
		          };
		        };
		        
		     
		        table.render({
		            elem: '#demo'
		            // , height: 'full-120'
		            , size: 'lg'
		            , url: '${ctx}/roles/page' //数据接口
		            // , data: data
		            , title: '用户表'
		            , page: {} //开启分页
		            , loading: true
		            , toolbar: '#toolbarDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		            , totalRow: true //开启合计行
		            , cellMinWidth: 90
		            // 是否开启字段筛选的记忆功能，支持true/false/'local'/'session'/其他 开启的情况下默认是session，除非显式的指定为'local'
		            , colFilterRecord: true
		            // 开启智能重载
		            , smartReloadModel: true
		            // 设置开启部分选项不可选
		            // 设置表格的主键（主要用在记录选中状态还有不可操作记录的时候用
		            , primaryKey: 'id'
		            , checkDisabled: {
		              enabled: true,
		              data: [10000, 10001, 10002, 10003, 10004, 10005, 10009]
		            }
		            // , pageLanguage: 'zh-TW' // 需要自己定义对应的文本
		            // , pageLanguage: 'en' // tablePlug里面已经定义了，如果觉得不满意可以用tablePlug.set去更新默认的配置;
		            // , pageLanguage: true // 无效的设置方式，只支持字符串或者对象的
		            // 也可以针对某个表格有特殊的配置如下面对象的设置方法,但是如果没有必要单独的自定义建议使用直接赋值成语言名称的字符串形式
		            , pageLanguage: {
		              lan: 'en',
		              // 可自定义text,lan为en的情况下
		              text: {
		                // jumpTo: 'jump to', // 到第
		                // page: 'page', // 页
		                // go: 'go', // 确定
		                // total: 'total', // 共
		                unit: 'item' // 条（单位，一般也可以不填）
		                // optionText: 'limit each page' // 条/页
		              }
		            }
		            , done: function () {
		              var tableView = this.elem.next();

		              tableView.find('.layui-table-grid-down').remove();
		              var totalRow = tableView.find('.layui-table-total');
		              var limit = this.page ? this.page.limit : this.limit;
		              layui.each(totalRow.find('td'), function (index, tdElem) {
		                tdElem = $(tdElem);
		                var text = tdElem.text();
		                if (text && !isNaN(text)) {
		                  text = (parseFloat(text) / limit).toFixed(2);
		                  tdElem.find('div.layui-table-cell').html(text);
		                }
		              });

		              // 初始化laydate
		              layui.each(tableView.find('td[data-field="birthday"]'), function (index, tdElem) {
		                tdElem.onclick = function (event) {
		                  layui.stope(event)
		                };
		                laydate.render({
		                  elem: tdElem.children[0],
		                  // closeStop: tdElem,
		                  format: 'yyyy/MM/dd',
		                  done: function (value, date) {
		                    var trElem = $(this.elem[0]).closest('tr');
		                    table.cache.demo[trElem.data('index')]['birthday'] = value;
		                  }
		                })
		              })

		              // table.eachCols(this.id, function (index, col) {
		              //   if (col.search && col.field && !tableView.find('th[data-field="'+col.field+'"]').find('div.layui-table-cell').find('.layui-table-th-search').length) {
		              //     tableView.find('th[data-field="'+col.field+'"]').find('div.layui-table-cell').append('<br><input class="layui-input layui-table-th-search">');
		              //   }
		              // })
		            },
		            parseData: function (ret) {
		              return {
		                code: ret.code,
		                msg: ret.msg,
		                count: ret.data ? (ret.data.total || 0) : 0,
		                data: ret.data.rows
		              }
		            }
		            , checkStatus: {}
		            // , autoSort: false
		            // , initSort: {
		            //   field: 'id' //排序字段，对应 cols 设定的各字段名
		            //   , type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
		            // }
		            , cols : [ 
						[  {
							type: 'checkbox',
							align : 'center',
							fixed: 'left'
							},
							{
							field : "id",
							title : "ID",
							width : 80,
							sort : true
						}, {
							field : "name",
							title : "角色名",
							edit : 'text'
						}, {
							field : "role",
							title : "英文名称",
							edit : 'text'
						}, {
							field : "roleType",
							title : "角色类型",
							align : 'center',
							search: true, 
							edit: false, 
							type: 'normal',
							templet:fn1('citye')
						}, {
							field : "isShow",
							title : "是否可用",
							templet : '#switchTpl'
						}, {
							field : "description",
							title : "具体描述",
							edit : 'text'
						} ] ]
		          });
		      //监听头工具栏事件
		        table.on('toolbar(test)', function (obj) {
		          var config = obj.config;
		          var btnElem = $(this);
		          var tableId = config.id;
		          // var tableView = config.elem.next();
		          switch (obj.event) {
		            case 'addTempData':
		              table.addTemp(tableId, function (trElem) {
		                // 进入回调的时候this是当前的表格的config
		                var that = this;
		                // 初始化laydate
		                layui.each(trElem.find('td[data-field="birthday"]'), function (index, tdElem) {
		                  tdElem.onclick = function(event) {layui.stope(event)};
		                  laydate.render({
		                    elem: tdElem.children[0],
		                    format: 'yyyy/MM/dd',
		                    done: function (value, date) {
		                      var trElem = $(this.elem[0]).closest('tr');
		                      table.cache[that.id][trElem.data('index')]['birthday'] = value;
		                    }
		                  })
		                })
		              });
		              break;
		            case 'getTempData':
		              layer.alert('临时数据:' + JSON.stringify(table.getTemp(tableId).data));
		              break;
		            case 'cleanTempData':
		              table.cleanTemp(tableId);
		              layer.msg('临时数据已删除');
		              break;
		            case 'openSelect':
		              layer.open({
		                type: 1,
		                title: '测试下拉效果单页面',
		                area: ['300px', '160px'],
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
		            case 'LAYTABLE_EXPORT':
		              // 点击导出图标的时候
		              $(this).find('.layui-table-tool-panel li').unbind('click').click(function () {
		                // 干掉了原始的事件了，自己定义需要的
		                var dataTemp = table.cache[tableId];
		                if (!dataTemp || !dataTemp.length) {
		                  // 处理如果没有数据的时候导出为空的excel，没有导出thead的问题
		                  dataTemp = [{}];
		                }
		                // 实际可以根据需要还可以直接请求导出全部，或者导出选中的数据而不是只导出当前的页的数据
		                table.exportFile(tableId, dataTemp, $(this).data('type'));
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
		        
		}
		)
</script>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container layui-inline">
    <span class="layui-btn layui-btn-sm layui-btn-primary" lay-event="reload">重载</span>
    <span class="layui-btn layui-btn-sm" lay-event="openIframeSelect">弹出iframe选择</span>
    <span class="layui-btn layui-btn-sm" lay-event="addTempData">添加临时数据</span>
    <span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="cleanTempData">清空临时数据</span>


    <!--<span class="layui-btn layui-btn-sm layui-btn-primary" lay-event="ranksConversion">行列转换</span>-->
  </div>
  <div class="layui-inline">
    <span><span style="color: red;">※</span>url模式测试用的是json文件所以翻页请用这里按钮，不要用table的中的laypage组件，实际开发中不会有这个问题</span>
  </div>
</script>
</body>
</html>