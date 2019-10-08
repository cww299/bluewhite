/* 版本2.0
 * 改用原型结构，对外接口menuTree记录所有实例对象。解决1.0无法存储多个实例对象问题，对象之间的配置数据污染
 * */
layui.define(['jquery','form'],function(exports){
    var $ = layui.$
    ,form = layui.form
	, MENUTREE = 'menuTree'
    ,SHOWICON = 'layui-icon-triangle-d'	//展开时的图标
    ,HIDEICON = 'layui-icon-triangle-r'
    ,menuTree = {
	    index: layui.menuTree ? (layui.menuTree.index + 10000) : 0
	    ,menuTreeObj : {}         //记录所有menuTree对象
	    ,menuTreeConfig : {}		//记录所有对象的配置
    }
    ,Class = function(options){		//新对象的配置：自身配置（实例过的对象继续render）+默认配置+本次配置
  	    var that = this;
	    that.index = ++menuTree.index;
	    that.config = $.extend({}, that.config, options);
	    that.render();
    };
    //每个实例对象的默认配置
    Class.prototype.config = {	
        data:[],		//显示的数据
        checked:[],	    //默认复选框选中的数据
        result:'',      //进行渲染的html文本结果
        sumNumber:false,//父菜单下子菜单选择的条数，是否开启
        disabled:[],	//不可选择的对象
        checkbox:true,	//是否开启复选框
        hide:true,		//是否隐藏子菜单
        showName:false,	//是否自定义显示名
		/*  除此默认配置外还可选的配置有： url，type，elem， */
    };
    //渲染菜单树	
    Class.prototype.render = function(){
        var self = this;
        var conf = self.config;
        if(conf.url){				//如果有URL则获取数据
            $.ajax({
                url : conf.url,  
                type : conf.type || 'get',
                async : false,
                success:function(r){
                    if(r.code == 0)
                        conf.data = r.data;
                    else
                        conf.result = '<span style="color:gray;">'+r.message+'</span>';
                },error:function(r){
                    self.result = '<span style="color:gray;">'+r+'</span>';
                }
            })
		}
		self.createTree();
	};
	//菜单树重载
    Class.prototype.reload = function(options){
    	var that = this;
    	that.config = $.extend({}, that.config, options);
    	that.render();  
    };
    //创建树形结构树
    Class.prototype.createTree = function(){
        var self = this;
	  	var conf = this.config;
	  	if(!conf.elem) 
	  	    conf.result = '<span style="color:gray;">渲染树形菜单请绑定elem元素<span>'; 		
		else if(!$(conf.elem).length>0) 
			conf.result = '<span style="color:gray;">绑定的elem元素不存在</span>'; 							
	  	else if(conf.data.length==0)
			conf.result = '<span style="color:gray;">'+(conf.text || "无数据")+'</span>';
		else{
			conf.result = '<div class="layui-form" id="'+conf.elem.substring(1,99)+'-'+MENUTREE+'" >'+tree(conf.data,'')+'</div>';
			function tree(data,nbsp){
				var html=''
				, checked = conf.checked
				, disabled = conf.disabled;
				if(conf.reloadConf){
					checked = conf.reloadConf.checked;
					var display = conf.reloadConf.display;
				}
				for(var i=0; i < data.length; i++){
					var c='';		//是否选中
					var d='';		//是否可选
					var di='none';		//是否展开
					for(var j=0;j<checked.length;j++){
						if(checked[j] == data[i].id ){
						    c='checked';
						    break;
					    }
				    }
				    for(var j=0;j<disabled.length;j++){
					    if(disabled[j] == data[i].id ){
						    d='disabled';
						    break;
					    }
				    }
				    if(display)
				    	 for(var j=0;j<display.length;j++){
						    if(display[j] == data[i].id ){
							    di='';
							    break;
						    }
						 }
					html+='<div class="layui-tree-grade">';
					html+='<div class="layui-tree-entry layui-inline" style="height:24px;">';
					html+=nbsp;																				//加缩进
					if(data[i].children!=null && data[i].children.length>0)
						html+='<i class="layui-icon '+( display? (di ? HIDEICON:SHOWICON): (conf.hide?HIDEICON:SHOWICON))+'"></i>&nbsp;';	//展开、收缩图标
					else
						html+='<i class="layui-icon layui-icon-file"></i>&nbsp;';
				    if(conf.checkbox)
					    html+='<input type="checkbox" lay-filter="menuTreeCheckbox-'+self.index+'" parentid="'+data[i].parentId+'" icon="'+data[i].icon+'" '+
					  		'value="'+data[i].id+'" '+c+' '+d+' lay-skin="primary">';//复选框
				    html+='<i class="layui-icon layui-icon-'+data[i].icon+'"></i>&nbsp;&nbsp;'			//菜单图标
				    if(conf.showName){
				    	html+='<span>'+conf.showName(data[i])+'</span>&nbsp;&nbsp;';	
				    }else
				    	html+='<span>'+data[i].name+'</span>&nbsp;&nbsp;';				//菜单名
				    if(conf.toolbar){
				    	conf.toolbar = conf.toolbar == true?['add','edit','delete'] : conf.toolbar;
				    	html += '<div class="menuControl" style="float: right;display:none;" lay-id="menuToolbar-'+self.index+'" data-id="'+data[i].id+'">';
				    	if(conf.toolbar.indexOf('add')!=-1)
				    	    html += '<i class="layui-icon layui-icon-add-1" type="add"></i>&nbsp;';
				    	if(conf.toolbar.indexOf('edit')!=-1)
				    		html += '<i class="layui-icon layui-icon-edit" type="edit"></i>&nbsp;';
				    	if(conf.toolbar.indexOf('delete')!=-1)
				    		html += '<i class="layui-icon layui-icon-delete" type="delete"></i>&nbsp;';
				    	if(conf.otherToolbar)
				    		html += conf.otherToolbar;
				    	html += '</div>';
				    }
				    if(conf.sumNumber)
					    html+='<span class="layui-badge layui-bg-green" lay-filter="menu-tree-number">0</span>';
				    html+='</div>'
				    if(data[i].children!=null && data[i].children.length>0)
						html+='<div class="layui-tree-child" style="display:'+( display? di : (conf.hide?"none":""))+';">'+
						 		tree(data[i].children,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+nbsp)+'</div>';	//子菜单
				    html+='</div>'
			    }
			    return html;
			}
		};
		self.showResult();
    };
    //结果展示
    Class.prototype.showResult = function(){
        var self = this;
        var conf = this.config;
        $(conf.elem).html(conf.result);
        form.render('checkbox');
        $('.'+SHOWICON).on('click',function(obj){ hide(obj); })
        $('.'+HIDEICON).on('click',function(obj){ show(obj); })
        function show(obj){
            $(obj.target).parent().next().slideDown();
            $(obj.target).attr("class","layui-icon "+SHOWICON);
            $(obj.target).unbind();
            $('.'+SHOWICON).on('click',function(obj){ hide(obj); })
        }
        function hide(obj){
            $(obj.target).parent().next().slideUp();
            $(obj.target).attr("class","layui-icon "+HIDEICON)
            $(obj.target).unbind();
            $('.'+HIDEICON).on('click',function(obj){  show(obj); })
        }
        if(!conf.closeCheckLink)
	        form.on('checkbox(menuTreeCheckbox-'+self.index+')',function(obj){		
	        	layui.each($(obj.elem).parent().next().find('input[type="checkbox"]'),function(index,item){		//遍历下级复选框，进行同步选择
	                item.checked = obj.elem.checked;
	            })
	            choosedParent($(obj.elem))
	            function choosedParent(par){						//选中父级菜单
	                if(par.parent().parent().parent().prev().find('input[type="checkbox"]').length>0){
	                    par.parent().parent().parent().prev().find('input[type="checkbox"]')[0].checked=true;
	                    choosedParent(par.parent().parent().parent().prev().find('input[type="checkbox"]'));
	                }
	            }
	        	form.render();
	        })
        var elem = $('div[class="layui-tree-entry layui-inline"');
	    if(conf.toolbar){
	    	elem.mouseover(function(){
	    		$(this).css("cursor","pointer");								  //鼠标变手
	    		$(this).find('div[lay-id="menuToolbar-'+self.index+'"]').show();  //显示工具
	    	}).mouseout(function (){  
	    		$(this).css("cursor","default");
	    		$(this).find('div[lay-id="menuToolbar-'+self.index+'"]').hide();  
	        });
	    	elem.find('div[lay-id="menuToolbar-'+self.index+'"]').find('i').mouseover(function(){
	    		$(this).css('color','#0bcc20');
	    	}).mouseout(function(){
	    		$(this).css('color','black');
	    	})
	    	elem.find('span').on('click',function(){
	    		$(this).parent().children(':first').click();
	    	})
	    }else{
	    	elem.mouseover(function(){
	    		$(this).css("cursor","pointer");								  //鼠标变手
	    	}).mouseout(function (){  
	    		$(this).css("cursor","default");
	        });
	    }
	    if(conf.reloadConf)			//删除数据重载配置、防止重载污染
	    	delete conf.reloadConf;
	    if(conf.onToolbar)
	    	self.onToolbar();
    };
    //绑定工具栏点击事件
    Class.prototype.onToolbar = function(){		
    	var self = this;
    	var allData = self.config.data;
		$(this.config.elem).find('div[class="layui-tree-entry layui-inline"]').find('div[class="menuControl"]').find('i').on('click',function(){
			var data = null;
			var id = $(this).parent().attr('data-id');
			findDataById(allData)
			function findDataById(d){				//根据id配置当行相关数据
				layui.each(d,function(index,item){
					if(data) return;
					if(item.id == id)
						data = item;
					if(item.children)
						findDataById(item.children)
				})
			}
			var obj = {
					elem : $(this),
					type : $(this).attr('type'),
					data : data,
			}
			if(obj.data)
				self.config.onToolbar(obj);
			else
				console.error('数据异常，无法找到相关数据');
		})
    }
    //监听控制按钮
    menuTree.onToolbar = function(id,callback){
    	if(!menuTree.menuTreeObj['#'+id])
    		console.error('监听事件找不到id：'+id);
    	else{
    		menuTree.menuTreeObj['#'+id].config.onToolbar = callback;
    		menuTree.menuTreeObj['#'+id].onToolbar()
    	}
    }
    //获取复选框选中的值
    menuTree.getVal = function(id){
        var val=[];
        if(id){
            if($('#'+id).find('input[type="checkbox"]').length==0)
                console.warn('该elem下找不到树形结构，请查看elem是否有误！');
            layui.each($('#'+id).find('input[type="checkbox"]'),function(index,item){
                if(item.checked)
                    val.push(item.value);
            })
        }else
            console.error('获取树形结构值时，请给出相关id！');
        return val;
    };
    //获取复选框选中的树形结构数据
    menuTree.getTreeData = function(id){
        if(!id){  console.error('获取树形数据时，需要指定获取对象的id'); return; }
        if(!menuTree.menuTreeObj['#'+id]){ console.error('找不到指定对象'+id); return;}
        var checked = $('#'+id).find('div[class~="layui-form-checked"]');
        return getTreeVal(0);
        function getTreeVal(parentId){
            var child = [];
            for(var i=0;i<checked.length;i++){
                if($(checked[i]).prev().attr('parentid')==parentId){
                    child.push({
                        id : $(checked[i]).prev().attr('value'),
                        name : $(checked[i]).siblings('span').html(),
                        parentId : parentId,
                        icon : $(checked[i]).prev().attr('icon'),
                        children : getTreeVal($(checked[i]).prev().attr('value')),
                    })
                }
            }
            return child;
        }
    };
  //获取复选框   没有   选中的树形结构数据
    menuTree.getUncheckedTreeData = function(id){
        if(!id){  console.error('获取树形数据时，需要指定获取对象的id'); return; }
        if(!menuTree.menuTreeObj['#'+id]){ console.error('找不到指定对象'+id); return;}
        var unchecked = $('#'+id).find('div[class="layui-unselect layui-form-checkbox"]');		//获取没有选中的
        return getTreeVal(0);
        function getTreeVal(parentId){
            var child = [];
            for(var i=0;i<unchecked.length;i++){
                if($(unchecked[i]).prev().attr('parentid')==parentId){
                    child.push({
                        id : $(unchecked[i]).prev().attr('value'),
                        name : $(unchecked[i]).siblings('span').html(),
                        parentId : parentId,
                        icon : $(unchecked[i]).prev().attr('icon'),
                        children : getTreeVal($(unchecked[i]).prev().attr('value')),
                    })
                }
            }
            return child;
        }
    };
    //获取所有的数据
    menuTree.getAllData = function(id){
        var val=[];
        if(!id){  console.error('获取数据时，需要指定获取对象的id'); return null; }
        if(!menuTree.menuTreeObj['#'+id]){ console.error('找不到指定对象'+id); return null;}
        return menuTree.menuTreeObj['#'+id].config.data;
    };
    menuTree.render = function(obj){
        var newTree = new Class(obj);
        var id = newTree.config.elem || newTree.index;			
        menuTree.menuTreeObj[id] = newTree;			//将当前实例对象记录下来
        menuTree.menuTreeConfig[id] = newTree.config;
    };
    menuTree.reload = function(id,options){
    	if(menuTree.menuTreeObj['#'+id])
    		menuTree.menuTreeObj['#'+id].reload(options);
    	else
    		console.error('重载时无法找到id:'+id);
    }
    menuTree.reloadData = function(id,options){
    	if(menuTree.menuTreeObj['#'+id]){
    		var display = [];
    		layui.each($('#'+id).find('div[class="layui-tree-entry layui-inline"]'),function(index,item){
    			if($(item).next().length > 0 && $(item).next().css('display') != 'none')
    				display.push($(item).find('div[class="menuControl"]').attr('data-id'))
    		})
    		options = options ? options : {};
    		options.reloadConf = {		//菜单树重载,只重载数据，需要记录重载前菜单的展开状态和选中状态
    			checked : menuTree.menuTreeObj['#'+id].config.checkbox ? menuTree.getVal(id) : [],
    			display : display
    		}
    		menuTree.menuTreeObj['#'+id].reload(options);
    	}
    	else
    		console.error('重载时无法找到id:'+id);
    }
	exports('menuTree',menuTree);
});