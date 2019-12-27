/* 工具模块
 * 2019/7/6
 * getSelectHtml：{ url、data、},
 * saveAjax: 保存数值函数，默认同步➕遮罩➕post。设置ctx时，无需给前缀，成功回调success,closeLoad关闭遮罩
 * deleteAjax： 删除函数，同上，数据：ids
 * getData: 默认异步 success(data)回调
 * getDataSync: 默认同步
 */
layui.define(['jquery','layer','form','table'],function(exports){
	var $ = layui.jquery
	,layer = layui.layer
	,form = layui.form
	,table = layui.table
	,Class = function(){
		
	};
	
	Class.prototype.saveAjax = function(options,callback,error){
		var msg = '';
		if(!options.url)
			msg = '请给定url接口';
		else if(!options.data)
			msg = '请给定保存数据';
		if(msg!=''){
			console.error(msg);
			return;
		}
		if(myutil.config.ctx!='')
			options.url = myutil.config.ctx+options.url;
		if(!options.closeLoad){
			var load = layer.load(1,{
				shade: [0.5,'black'],
			});
		}
		$.ajax({
			url : options.url,
			type : options.type || 'post',	//默认post方法
			traditional: options.traditional || false,
			data : options.data,
			success: function(r){
				var msg = '成功';
				if(r.code == 0){
					r.message && (msg = r.message);
					callback && callback();
					options.success && options.success(r);
					if(!options.closeMsg)
						myutil.smsg(msg);
				}else{
					msg = '失败';
					r.message && (msg = r.message);
					myutil.emsg(msg);
					error && error();
					options.error && options.error(r);
				}
				if(!options.closeLoad){
					layer.close(load);
				}
			},
			error:function(){
				if(!options.closeLoad){
					layer.close(load);
				}
				myutil.emsg('请求发生错误！');
			}
		})
	}
	
	Class.prototype.deleteAjax = function(options,callback,error){
		var msg = '';
		if(!options.url)
			msg = '请给定url接口';
		else if(!options.ids)
			msg = '请给定删除数据';
		if(msg!=''){
			console.error(msg);
			return;
		}
		if(myutil.config.ctx!='')
			options.url = myutil.config.ctx+options.url;
		var load = layer.load(1,{
			shade: [0.5,'black'], 
		});
		$.ajax({
			url : options.url,
			type : options.type || 'get',	
			traditional: options.traditional || false,
			data : {ids: options.ids },
			success: function(r){
				var msg = '成功';
				if(r.code == 0){
					r.message && (msg = r.message);
					myutil.smsg(msg);
					callback && callback();
					options.success && options.success(r);
				}else{
					msg = '失败';
					r.message && (msg = r.message);
					myutil.emsg(msg);
					error && error();
					options.error && options.error(r);
				}
			},
			error:function(){
				if(!options.closeLoad){
					layer.close(load);
				}
				myutil.emsg('请求发生错误！');
			}
		})
		layer.close(load);
	}
	
	Class.prototype.getSelectHtml = function(options,init){ /* data、url、tips、name、filter、selectOption、value、title、*/
		var msg = '';
		if(!options.data && !options.url)
			msg = '请给定数据或接口生成下拉框';
		if(msg!=''){
			console.error(msg);
			return;
		}
		if(myutil.config.ctx!='')
			options.url = myutil.config.ctx+options.url;
		var data = [];
		if(!options.data){
			$.ajax({
				url: options.url,
				type : options.type || 'get',
				async : options.async || false,	//默认同步,异步需要修改
				success: function(r){
					if(0==r.code){
						if(r.data.rows)
							data = r.data.rows;
						else
							data = r.data;
					}else
						console.error('渲染html时，获取接口数据异常');
				}
			})
		}else
			data = options.data;
		var html = '<select name="'+(options.name?options.name:"")+'" lay-search lay-filter="'+(options.filter?options.filter:"")+'">';
		if(options.tips)
			html+='<option value="">'+(options.tips?options.tips:'请选择')+'</option>';
		var selectOption = (options.selectOption?options.selectOption:"")
		,value = options.value || 'value'
		,title = options.title || 'name';
		layui.each(data,function(index,item){
			var selected = '';
			if(item[value] == selectOption)
				selected = 'selected';
			html += '<option value="'+item[value]+'" '+selected+'>'+item[title]+'</option>'
		})
		init && init(html);
		options.done && options.done(html);
		return html==''?'<select><option value="">无数据</option></select>':html;
	}
	
	Class.prototype.getData = function(options,callback){ /*url、type、async、data  默认异步 */
		var data = [];
		var ajax = {
			url : options.url,
			type : options.type || 'get',
			async : (options.async==false?false:true),
			data : options.data || {},
			success : function(r){
				if(r.code == 0){
					if(r.data && r.data.rows)
						data = r.data.rows;
					else
						data = r.data;
					callback && callback(data);
					options.success && options.success(data);
					options.done && options.done(data);
					return data;
				}
			}
		}
		$.ajax(ajax);
		return data;
	}
	
	var myutil = {
		c : new Class(),
		config : {
			msgOffset : '',
			ctx:'',
		},
		lastData:'', //用于记录表格修改前的数据。主要用于表格修改非法时的数据回滚
	}

	myutil.saveAjax = function(options,callback,error){
		myutil.c.saveAjax(options,callback,error);
	};
	
	myutil.deleteAjax = function(options,callback,error){
		myutil.c.deleteAjax(options,callback,error);
	};
	
	myutil.deleTableIds = function(opt){
		/*url: '', id:'id', table:'', text:'', offset:'', success,verify(checked) */
		if(!opt.table)
			return console.warn('请给定操作表格');
		var tid = opt.table, text = opt.text || '请选择相关信息删除|是否确认删除？',offset = opt.offset || '',ids = new Set();
		var choosed = table.checkStatus(tid).data;
		if(choosed.length<1)
			return myutil.emsg(text.split('|')[0]);
		layui.each(choosed,function(index,item){
			var id = opt.id || 'id', val = item;
			layui.each(id.split('_'),function(i1,t1){
				val = val[t1] || null;
			})
			ids.add(val);
		})
		var msg = '';
		opt.verify && (msg = opt.verify(choosed));
		if(msg)
			return myutil.emsg(msg);
		layer.confirm(text.split('|')[1],{ offset:offset },function(){
			myutil.deleteAjax({
				url: opt.url,
				ids: Array.from(ids).join(','),
				type: opt.type || 'get',
				success: function(){
					table.reload(tid);
					opt.success && opt.success();
				}
			})
		})
	}

	myutil.smsg = function(msg,opt){
		var iconAndOffset = { icon:1,};
		if(myutil.config.msgOffset)
			iconAndOffset.offset = myutil.config.msgOffset;
		if(opt)
			iconAndOffset = $.extend({},iconAndOffset,opt);
		layer.msg(msg,iconAndOffset);
	};
	
	myutil.emsg = function(msg,opt){
		var tips = '';
		var iconAndOffset = { 
				icon:2,
				time:0,
				shadeClose:true,
				btn: ['关闭',],
				btnAlign:'c',
				yes:function(){
					layer.close(tips);
				}
		};
		if(myutil.config.msgOffset)
			iconAndOffset.offset = myutil.config.msgOffset;
		if(opt)
			iconAndOffset = $.extend({},iconAndOffset,opt);
		tips = layer.msg(msg,iconAndOffset);
	};
	
	myutil.getSelectHtml = function(options,init){
		return myutil.c.getSelectHtml(options,init);
	};
	
	myutil.getData = function(options,callback){	//异步获取数据
		return myutil.c.getData(options,callback);
	};
	myutil.getDataSync = function(options,callback){	//同步获取数据
		options.async = false;
		return myutil.c.getData(options,callback);
	};
	
	myutil.clickTr = function(opt){
		var noClick = opt && opt.noClick;
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			var trElem = tableView.find('tr[data-index="' + trIndex + '"]');
			var tableId = $(tableView).attr('lay-id');
			if(noClick){
				if(typeof(noClick)!=='object'){
					noClick = [noClick];
				}
				for(var i in noClick){
					if(noClick[i]==tableId)
						return;
				}
			}
			while($(trElem).find('[name="layTableCheckbox"]+').length==0){
				trElem = $(trElem).prev();
			}
			$(trElem).find('[name="layTableCheckbox"]+').last().click();
		})
	};
	myutil.keyDownEntry = function(callback){
		document.onkeydown = function(event) { 
			if (event.keyCode == "13") {
	            callback && callback();
	        }
		} 
	};
	myutil.timeFormat = function(){
		// new Date().format("yyyy-MM-dd hh:mm:ss");  获取当前时间		https://blog.csdn.net/qq_39985511/article/details/80031674
		// new Date().format("yyyy-MM-dd");
		Date.prototype.format = function(fmt) { 
		     var o = { 
		        "M+" : this.getMonth()+1,                 //月份 
		        "d+" : this.getDate(),                    //日 
		        "h+" : this.getHours(),                   //小时 
		        "m+" : this.getMinutes(),                 //分 
		        "s+" : this.getSeconds(),                 //秒 
		        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
		        "S"  : this.getMilliseconds()             //毫秒 
		    }; 
		    if(/(y+)/.test(fmt)) {
		            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		    }
		     for(var k in o) {
		        if(new RegExp("("+ k +")").test(fmt)){
		             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
		         }
		     }
		    return fmt; 
		}  
	};
	myutil.getSubDay = function(day,format){	//获取当前时间减去day天。
		myutil.timeFormat();
		var now = new Date();
		now.setTime(now.getTime()-24*60*60*1000*day)
		return now.format(format || 'yyyy-MM-dd 00:00:00');
	};
	
	myutil.getLastData = function(){	//用于记录表格单元格修改前的数据
		$(document).on('mouseup','td[data-edit="true"],td[data-edit="text"]',function(obj){
			var elem = obj.toElement;
			var name = elem.tagName;
			if(name=='TD'){		//如果点击的是td且存在输入框input
				if($(elem).find('input').length>0)
					return;
				elem = $(elem).find('div')[0];
			}else{			//如果点击的是div
				if($(elem).parent().find('input').length>0  )
					return;
			}
			myutil.lastData = elem.innerHTML;
		})
	};
	
	myutil.getLastMonth = function(format,firstOfEnd){
	    var date = new Date; 
	    var year = date.getFullYear();
	    var month = date.getMonth();
	    if(month == 0){
	         year = year -1;
	         month = 12; 
	    }
		return year+'-'+month;
	}
	
	myutil.scrollX = function(container){
        //判断浏览器
        var isIE = navigator.userAgent.match(/MSIE (\d)/i);
        isIE = isIE ? isIE[1] : undefined;
        var isFF = /FireFox/i.test(navigator.userAgent);
 
        if (isIE < 9) //传统浏览器使用MouseWheel事件
            container.attachEvent("onmousewheel", function (e) {
                //计算鼠标滚轮滚动的距离
                //一格3行，每行40像素
                let v = e.wheelDelta / 2;
                container.scrollLeft += v;
                //阻止浏览器默认方法
                return false;
            });
        else if (!isFF) //除火狐外的现代浏览器也使用MouseWheel事件
            container.addEventListener("mousewheel", function (e) {
                //计算鼠标滚轮滚动的距离
                let v = -e.wheelDelta / 2;
                container.scrollLeft += v;
                //阻止浏览器默认方法
                e.preventDefault();
            }, false);
        else //火狐使用DOMMouseScroll事件
            container.addEventListener("DOMMouseScroll", function (e) {
                //计算鼠标滚轮滚动的距离
                //一格是3行，但是要注意，这里和像素不同的是它是负值
                container.scrollLeft += e.detail * 80;
                //阻止浏览器默认方法
                e.preventDefault();
            }, false); 
	}
	
	exports('myutil',myutil);
})