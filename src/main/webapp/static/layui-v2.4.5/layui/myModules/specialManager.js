layui.config({
	base: 'static/layui-v2.4.5/'
}).extend({
	tablePlug: 'tablePlug/tablePlug',
}).define(['jquery','laydate','table','form','tablePlug'],function(exports){
	var $ = layui.jquery
	, table = layui.table 
	, laydate = layui.laydate
	, form = layui.form
	, tablePlug = layui.tablePlug
	, MODNAME = 'specialManager'
	, specialManager = {
			
	}
	, Class = function(){
		
	};
	
	Class.prototype.render = function(type,ctx){
		var LOAD;
		laydate.render({
			elem: '#dayTime',
			type: 'date', 
			range : '~'
		})
		laydate.render({
			elem: '#monthTime',
			type: 'month', 
		})
		tablePlug.smartReload.enable(true); 
		table.render({
			elem:'#specialTable',
			data:[],
			totalRow:true,
			toolbar: true,
			loading:false,
			size:'sm',
			request:{ pageName:'page', limitName:'size' },
			parseData:function(ret){ return { data:ret.data,  msg:ret.message, code:ret.code } },
			cols:[[
			       {align:'center', title:'日期',   field:'date',	totalRowText:'合计',},
			       {align:'center', title:'分组/姓名', 	field:'name', filter:true,		},
			       {align:'center', title:'总工时',   field:'sumWorkTime',  totalRow:true,},
			       {align:'center', title:'工种',   field:'kindWork',	},
			       {align:'center', title:'是否工厂',   field:'foreigns',	},
			       {align:'center', title:'b工资',   field:'bPay',	totalRow:true,},
			       {align:'center', title:'小时单价',   field:'price',	edit:true,},
			       {align:'center', title:'总工资',   field:'sumPrice',	totalRow:true,},
			       ]],
			done:function(){
				layer.close(LOAD);
			}
		}) 
		var isUser = false;
		table.on('edit(specialTable)',function(obj){
			var val = obj.value,
			    data = obj.data;
			if(isNaN(val)){
				layer.msg('小时单价只能为数字',{icon:2,offset:'200px'})
			}else if(!isUser){
				layer.msg('无效修改！',{icon:2,offset:'200px'})
			}else if(val<0){
				layer.msg('小时单价不能为负数',{icon:2,offset:'200px'})
			}else{
				var load = layer.load(1);
				$.ajax({
					url: ctx+'/system/user/updateForeigns',
					type:'post',
					async:false,
					data: { id: data.id, price: parseFloat(val)},
					success:function(r){
						var icon = 2;
						if(r.code == 0)
							icon = 1;
						layer.msg(r.message,{icon:icon,offset:'200px'});
					}
				})
				layer.close(load);
			}
			table.reload('specialTable');
		})
		form.on('radio(time)', function(data){			//单选按钮切换
			switch(data.value){
			case '1':   $('#dayTime').show(); $('#monthTime').hide(); break;
			case '2':   $('#dayTime').hide(); $('#monthTime').show(); break;
			}
		});  
		
		form.on('submit(search)',function(obj){
			var data = obj.field;
			var msg="";
			if(data.viewTypeDate ==1){
				if($('#dayTime').val()==""){
					layer.msg('查询时间不能为空',{icon:2});
					return;
				}
				var time = $('#dayTime').val().split("~");
				data.orderTimeBegin = time[0]+"00:00:00";
				data.orderTimeEnd = time[1]+" 23:59:59";
			}else{
				if($('#monthTime').val()==""){
					layer.msg('查询时间不能为空',{icon:2});
					return;
				}
				var time = $('#monthTime').val();
				data.orderTimeBegin = time+"-01 00:00:00";
				data.orderTimeEnd = "";
			}
			if(data.viewTypeUser == 1)
				isUser = true;
			else 
				isUser = false;
			LOAD = layer.load(1);
			table.reload('specialTable',{
				url: ctx+'/production/sumTemporarily?type='+type,
				where:data
			}) 
		}) 
	}
	specialManager.render = function(type,ctx){
		var s = new Class();
		s.render(type,ctx);
	}
	exports(MODNAME,specialManager);
})