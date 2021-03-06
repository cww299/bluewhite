/**
 * 商品标签
 * @author:299
 */
layui.extend({
	jsBarcode: 'layui/myModules/util/jsBarcode',
}).define(['jquery','layer','table','myutil','laytpl','jsBarcode'],function(exports){
	var $ = layui.jquery
	,myutil = layui.myutil
	,printGetOrder = layui.printGetOrder
	,laytpl = layui.laytpl
	,layer = layui.layer;
	var STYLE = [
		'<style>',
			'.flagsDiv{',
				'margin-right:5px;margin-top:5px;',
			'}',
			'.hiddenPrint{',
				'display:none;',
			'}',
		'</style>',
	].join('');
	
	$('head').append(STYLE);
	
	var TPL = [
		'<div style="padding:10px;">',
		'{{# ',
			'layui.each(d,function(index,item){ }}',
				'<input type="number" class="layui-input" placeholder="打印张数"  value="{{ item.inventoryNumber }}" style="float: right;',
		    			'width: 200px;margin-top: 150px;">',
    			'<div style="page-break-after:always;" class="hiddenPrint">',
	    			'<p style="font-size: 10px;width: 100px;margin: auto; overflow: hidden;white-space: nowrap;">',
	    				 '{{ item.salePrice ? ("￥" + item.salePrice) : "" }}{{ item.name }}</p>',
	    			'<img class="id{{ item.id }}" style="width:100px;height:30px;"/>',
	    			'<p style="margin: 0px; font-size: 10px;">{{ item.qcCode }}</p>',
    			'</div>',
		    	'<div>',
					'<div style="border: 1px solid gray; padding: 10px;margin-right:5px;margin-top:5px;width: 320px;',
			    			'height:240px;display: flex;">',
			    		'<div style="align-self: center;text-align: center;">',
							'<p style="margin: 0px;line-height: 20px;width: 300px;">',
								'{{ item.salePrice ? ( "￥" + item.salePrice) : "" }}{{ item.name || "---"}}</p>',
							'<img class="id{{ item.id }}" />',
							'<p style="margin: 0px; ">{{ item.qcCode }}</p>',
						'</div>',
					'</div>',
				'</div>',
		'{{# }) }}',
		'</div>',
	].join(' ');
	
	var PRINT_TPL = [
	'<style>body{text-align:center;}</style>',
	'{{# ',
		'layui.each(d,function(index,item){ }}',
	    	
	'{{# }) }}',
	].join(' ');
	
	var goodFlag = {
	};
	
	goodFlag.print = function(data){
		layer.open({
			type: 1,
			shadeClose: true,
			area: ['600px','600px'],
			title: '打印商品标签',
			btn: ['打印','取消'],
			yes: function(layIndex,layerElem){
				var html = '<style>body{ margin:0px;text-align:center;}</style>';
				layui.each($(layerElem).find('input'),function(index,item){
					var val = $(item).val();
					if(val){
						for(var i=0;i<val;i++){
							html += $(item).next('.hiddenPrint').html();
						}
					}
				})
				goodFlag.printpage(html);
			},
			content: laytpl(TPL).render(data),
			success: function(layerElem,layerIndex){
				for(var i in data){
					JsBarcode(".id"+data[i].id, data[i].qcCode,{
						displayValue: false,
						margin: 0,
					});
				}
			}
		})
	}
	
	goodFlag.printpage = function(html){    
		var wind = window.open("",'newwindow');
		wind.document.body.innerHTML = html;
		wind.print();
		return false; 
	}  
	exports("goodFlag",goodFlag);
})