/*
 * 用于记录筛选列显示的cookie模块
 */
layui.define(['jquery'],function(exports){
	var $ = layui.$
	, COOKIENAME = ''		//变量名，存放的cookie名
	, cookieCol = { };
	cookieCol.cookieName = function(name){
		COOKIENAME = name;
		init();
	};
	cookieCol.clearCookie = function(name){
		$.removeCookie(name); 
	};
	function init(){
		$.getScript('http://localhost:8080/bluewhite/static/js/vendor/jquery.cookie.js',function(){	//引入cookie文件
			readCookie();														//读取cookie文件
		})
		$(document).on('mousedown', '.layui-unselect', function (event) { 	//监听筛选列点击
			saveCookie(event);
		});
	}
	function readCookie(){							//读取cookie值并，设置筛选的字段
		var hideField = $.cookie(COOKIENAME);		//读取cookie
		if(hideField==undefined || hideField=='')	
			return;
		$('.layui-icon-cols').click();    			//打开筛选列
		var panel = $('.layui-table-tool-panel');	//筛选面板
		var hf = hideField.split(',');
		for(var i=0;i<hf.length;i++){	
			if(!isNaN(hf[i]))						// cookie 存储bug
				panel.find('.layui-form-checkbox')[hf[i]].click();
		}
		$('.layui-table').click(); 					//关闭筛选列，随便点击一个地方即关闭
	}
	function saveCookie(event){						//保存用户筛选的设置
		if($.cookie(COOKIENAME)==undefined)
			$.cookie(COOKIENAME,'');
		var panel = $('.layui-table-tool-panel');				//筛选面板
		var leng = panel.find('.layui-form-checkbox').length-1;	//获取所有字段的总数
		var key='';												//根据点击的不同位置获取点击的索引,可点击div span i
		if(event.target.localName=='i')
			key = $(event.target).parent().parent().children("input:first-child").attr('data-key')
			else if(event.target.localName=='span')
				key = $(event.target).parent().parent().children("input:first-child").attr('data-key')
				else if(event.target.localName=='div')
					key = $(event.target).parent().children("input:first-child").attr('data-key');
		var hideField = $.cookie(COOKIENAME);
		if(key==undefined){							//点击全选的时候
			var hf=[];
			if(hideField=='')
				for(var i=0;i<leng;i++)
					hf.push(i-(-1));				//第一个复选框为全选，因此需要加1
			$.cookie(COOKIENAME, hf.join(','));
		}else{
			var hf = hideField==''?[]:hideField.split(',');			
			var i;
			var index = (key.split('-'))[1]-(-1)
			for(i=0;i<hf.length;i++)
				if(hf[i] == index)
					break;
			if(i<hf.length)
				hf.splice(i,1);
			else
				hf.push(index);
			$.cookie(COOKIENAME, hf.join(','));
		}
	}
	exports('cookieCol',cookieCol);
})