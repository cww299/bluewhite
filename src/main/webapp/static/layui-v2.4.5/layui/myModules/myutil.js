/* 工具模块
 * 2019/7/6
 * getSelectHtml：{ url、data、}
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
		var load = layer.load(1);
		$.ajax({
			url : options.url,
			type : options.type || 'post',	//默认post方法
			async : options.async || false,
			data : options.data,
			success: function(r){
				var msg = '成功';
				if(r.code == 0){
					r.message && (msg = r.message);
					callback && callback();
					options.success && options.success(r);
					myutil.smsg(msg);
				}else{
					msg = '失败';
					r.message && (msg = r.message);
					myutil.emsg(msg);
					error && error();
					options.error && options.error(r);
				}
			}
		})
		layer.close(load);
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
		var load = layer.load(1);
		$.ajax({
			url : options.url,
			type : options.type || 'get',	
			async : options.async || false,
			traditional: options.traditional || 'false',
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
		var html = '<select name="'+(options.name?options.name:"")+'" lay-filter="'+(options.filter?options.filter:"")+'">';
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
					if(r.data.rows)
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
	}

	myutil.saveAjax = function(options,callback,error){
		myutil.c.saveAjax(options,callback,error);
	};
	
	myutil.deleteAjax = function(options,callback,error){
		myutil.c.deleteAjax(options,callback,error);
	};
	
	myutil.deleTableIds = function(opt){
		/*url: '', id:'id', table:'', text:'', offset:'', success */
		if(!opt.table)
			return console.warn('请给定操作表格');
		var tid = opt.table, text = opt.text || '请选择相关信息删除|是否确认删除？',offset = opt.offset || '',ids = [];
		var choosed = table.checkStatus(tid).data;
		if(choosed.length<1)
			return myutil.emsg(text.split('|')[0]);
		layui.each(choosed,function(index,item){
			var id = opt.id || 'id', val = item;
			layui.each(id.split('_'),function(i1,t1){
				val = val[t1] || null;
			})
			ids.push(val);
		})
		layer.confirm(text.split('|')[1],{ offset:offset },function(){
			myutil.deleteAjax({
				url: opt.url,
				ids: ids.join(','),
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
		var iconAndOffset = { icon:2,};
		if(myutil.config.msgOffset)
			iconAndOffset.offset = myutil.config.msgOffset;
		if(opt)
			iconAndOffset = $.extend({},iconAndOffset,opt);
		layer.msg(msg,iconAndOffset);
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
	
	myutil.clickTr = function(){
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
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
	}
	
	exports('myutil',myutil);
})