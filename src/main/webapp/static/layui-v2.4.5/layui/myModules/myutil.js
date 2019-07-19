/* 工具模块
 * 2019/7/6
 */
layui.define(['jquery','layer','form'],function(exports){
	var $ = layui.jquery
	,layer = layui.layer
	,form = layui.form
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
		var load = layer.load(1);
		$.ajax({
			url : 'http://localhost:8080/bluewhite'+options.url,
			type : options.type || 'post',	//默认post方法
			async : options.async || false,
			data : options.data,
			success: function(r){
				var msg = '成功';
				if(r.code == 0){
					r.message && (msg = r.message);
					myutil.smsg(msg);
					callback && callback();
				}else{
					msg = '失败';
					r.message && (msg = r.message);
					myutil.emsg(msg);
					error && error();
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
		var load = layer.load(1);
		$.ajax({
			url : '${ctx}'+options.url,
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
				}else{
					msg = '失败';
					r.message && (msg = r.message);
					myutil.emsg(msg);
					error && error();
				}
			}
		})
		layer.close(load);
	}
	
	Class.prototype.getSelectHtml = function(options,init){ /* data、url、tips、name、filter、selectOption、value、title、*/
		var msg = '';
		if(!options.data && !options.url)
			msg = '请给定数据或接口生产下拉框';
		if(msg!=''){
			console.error(msg);
			return;
		}
		var data = [];
		if(!options.data){
			$.ajax({
				url: 'http://localhost:8080/bluewhite'+options.url,
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
		init && init();
		return html==''?'<select><option value="">无数据</option></select>':html;
	}
	
	Class.prototype.getData = function(options,callback){ /*url、type、async、data */
		var data = [];
		var ajax = {
			url : options.url,
			type : options.type || 'get',
			async : options.async || true,
			data : options.data || {},
			success : function(r){
				if(r.code == 0){
					if(r.data.rows)
						data = r.data.rows;
					else
						data = r.data;
					callback && callback();
					return data;
				}
			}
		}
		return $.ajax(ajax);
	}
	
	var myutil = {
		c : new Class(),
		config : {
			msgOffset : '',
		},
	}

	myutil.saveAjax = function(options,callback,error){
		myutil.c.saveAjax(options,callback,error);
	};
	
	myutil.deleteAjax = function(options,callback,error){
		myutil.c.saveAjax(options,callback,error);
	};
	
	myutil.smsg = function(msg){
		var iconAndOffset = { icon:1,};
		if(myutil.config.msgOffset)
			iconAndOffset.offset = myutil.config.msgOffset;
		layer.msg(msg,iconAndOffset);
	};
	
	myutil.emsg = function(msg){
		var iconAndOffset = { icon:2,};
		if(myutil.config.msgOffset)
			iconAndOffset.offset = myutil.config.msgOffset;
		layer.msg(msg,iconAndOffset);
	};
	
	myutil.getSelectHtml = function(options,init){
		return myutil.c.getSelectHtml(options,init);
	};
	
	myutil.getData = function(options,callback){
		myutil.c.getData(options,callback);
	};
	
	myutil.clickTr = function(){
		$(document).on('click', '.layui-table-view tbody tr', function(event) {
			var elemTemp = $(this);
			var tableView = elemTemp.closest('.layui-table-view');
			var trIndex = elemTemp.data('index');
			tableView.find('tr[data-index="' + trIndex + '"]').find('[name="layTableCheckbox"]+').last().click();
		})
	};
	
	exports('myutil',myutil);
})