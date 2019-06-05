layui.define(['element','jquery','form'],function(exports){
	  var el = layui.element;
	  var $ = layui.$;
	  var form = layui.form;
	  	
	  var MENUTREE = 'menuTree';
	  var menuTree = {
		  data:[],
		  checked:[],
		  elem:'',
		  result:'',
		  sumNumber:false,
		  disabled:[],
		  render:function(obj){					//渲染数据。参数的初始化
			  if(obj.hasOwnProperty('elem'))	//如果没有elem属性
			  	this.elem = obj.elem;
			  if(obj.hasOwnProperty('url')){
				  var type = obj.hasOwnProperty('type')?obj.hasOwnProperty('type'):'get';
				  var self = this;
				  $.ajax({
					  url : obj.url,  type : type,
					  async : false,
					  success:function(r){
						  if(r.code == 0)
							  self.data = r.data;
						  else
							  self.result = r.message;
					  },error:function(r){
						  self.result = r;
					  }
				  })
			  }
			  else if(obj.hasOwnProperty('data'))
				  this.data = obj.data;
			  if(obj.hasOwnProperty('checked'))
				  this.checked = obj.checked;
			  if(obj.hasOwnProperty('disabled'))
				  this.disabled = obj.disabled;
			  if(obj.hasOwnProperty('sumNumber'))
				  this.sumNumber = obj.sumNumber;
			  this.createTree();
		  },
		  reload:function(){
			this.createTree();  
		  },
		  createTree:function(){
			  if(this.data.length==0)
				  this.result = '<span style="color:gray;">无给定数据</span>';
			  else{
				  this.result = '<div class="layui-form" id="'+this.elem.substring(1,99)+'-'+MENUTREE+'" >'+tree(this.data,'')+'</div>';
				  function tree(data,nbsp){
					  var html=''
					  , checked = menuTree.checked
					  , disabled = menuTree.disabled;
					  
					  for(var i=0; i < data.length; i++){
						  html+='<div class="layui-tree-grade">';
						  html+='<div class="layui-tree-entry layui-inline" style="height:24px;">';
						  html+=nbsp;																//加缩进
						  if(data[i].children!=null && data[i].children.length>0)
							  html+='<i class="layui-icon layui-icon-down"></i>&nbsp;';				//展开、收缩图标
						  else
							  html+='<i class="layui-icon layui-icon-file"></i>&nbsp;';
						  var c='';		//是否选中
						  var d='';		//是否可选
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
						  html+='<input type="checkbox" lay-filter="menuTreeCheckbox" parentId="'+data[i].parentId+'" value="'+data[i].id+'" '+c+' '+d+' lay-skin="primary">';//复选框
						  html+='<i class="layui-icon layui-icon-'+data[i].icon+'"></i>&nbsp;&nbsp;'			//菜单图标
						  html+='<span>'+data[i].name+'</span>&nbsp;&nbsp;';				//菜单名
						  if(menuTree.sumNumber)
							  html+='<span class="layui-badge layui-bg-green" lay-filter="menu-tree-number">0</span>';
						  html+='</div>'
						  if(data[i].children!=null && data[i].children.length>0)
								 html+='<div class="layui-tree-child" style="display:none">'+tree(data[i].children,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+nbsp)+'</div>';	//子菜单
						  html+='</div>'
					  }
					  return html;
				  }
			  }
			  this.showResult();
		  },
		  showResult:function(){
			  if(this.elem==''){ console.error('渲染树形菜单请绑定elem元素'); return; }			//如果没有elem属性
			  if(!$(this.elem).length>0){ console.warn('绑定的elem元素不存在'); }							//如果该元素不存在
			  $(this.elem).html(this.result);
			  form.render('checkbox');
			  $('.layui-icon-up').on('click',function(obj){ hide(obj); })
			  $('.layui-icon-down').on('click',function(obj){ show(obj); })
			  function show(obj){
				  $(obj.target).parent().next().slideDown();
				  $(obj.target).attr("class","layui-icon layui-icon-up");
				  $(obj.target).unbind();
				  $('.layui-icon-up').on('click',function(obj){ hide(obj); })
			  }
			  function hide(obj){
				  $(obj.target).parent().next().slideUp();
				  $(obj.target).attr("class","layui-icon layui-icon-down")
				  $(obj.target).unbind();
				  $('.layui-icon-down').on('click',function(obj){  show(obj); })
			  }
			  form.on('checkbox(menuTreeCheckbox)',function(obj){							
				  layui.each($(obj.elem).parent().next().find('input[type="checkbox"]'),function(index,item){		//遍历下级复选框，进行同步选择
					  item.checked = obj.elem.checked;
					  form.render();
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
		  },
		  getVal:function(elem){
			  var val=[];
			  if(elem!=undefined){
				  if($('#'+elem).find('input[type="checkbox"]').length==0)
					  console.warn('该elem下找不到树形结构，请查看elem是否有误！');
				  layui.each($('#'+elem).find('input[type="checkbox"]'),function(index,item){
					  if(item.checked)
						  val.push(item.value);
				  })
			  }
			  else{
				  layui.each($('div[id*='+MENUTREE+']'),function(index,item){
					  var vals=[];
					  layui.each($('#'+item.id).find('input[type="checkbox"]'),function(index,item){
						  if(item.checked)
							  vals.push(item.value);
					  })
					  val.push({ id : item.id, val : vals  });
				  })
			  }
			  return val;
		  },
		  getTreeData:function(elem){
			  if(elem==undefined){  console.error('获取树形数据时，需要指定获取对象的id'); return; }
			  var checked = this.getVal(elem);
			  var data = menuTree.data;
			  return createTreeData(data,checked);
			  function createTreeData(data,checked){
				  var child=[];
				  layui.each(data,function(index,item){
					  for(var i=0;i<checked.length;i++)
						  if(checked[i]==item.id){
							  var t={
									  id : item.id,
									  icon: item.icon,
									  name : item.name,
									  url : item.url,
									  children : [],
								  };
							  t.children = createTreeData(item.children,checked);
							  child.push(t)
							  break;
						  }
				  })
				  return child;
			  }
		  }
	  };

	exports('menuTree',menuTree);
});