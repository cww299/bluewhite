/**2019/12/20
 * author:299
 * 客户管理模块 customer
 */
layui.extend({
	//formSelects : 'formSelect/formSelects-v4'
}).define(['jquery','layer','form','laytpl','laydate','mytable','table'],function(exports){
	"use strict";
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		layer = layui.layer,
		laytpl = layui.laytpl,
		mytable = layui.mytable,
		table = layui.table,
		myutil = layui.myutil;
	var tableId = 'customerTableData';
	var TPL_MAIN = `
		<table class="searchTable layui-form">
			<td>客户名称：</td>
			<td><input type="text" name="name" class="layui-input"></td>
			<td>手机号码：</td>
			<td><input type="text" name="phone" class="layui-input"></td>
			<td>所属业务员：</td>
			<td><input type="text" name="userName" class="layui-input"></td>
			<td></td>
			<td><span class="layui-btn" lay-submit lay-filter="search">
				<i class="layui-icon layui-icon-search"></i>搜索</span></td>
		</table>
		<table id="`+tableId+`" lay-filter="`+tableId+`"></table>
	`;
	var TPL_ADD_EDIT = `
	<div style="padding:0 10px;">
		<table class="layui-form layui-table" lay-skin="row">
			<tr>
				<td>客户名称</td>
				<td><input type="text" class="layui-input" name="name" lay-verify="required"
					value="{{ d.name || '' }}" ></td>
				<td>是否内部</td>
				<td><select name="interior">
						<option value="0" {{ d.interior!=1?"selected":"" }}>否</option>
						<option value="1" {{ d.interior==1?"selected":"" }}>是</option>
					</select></td>
			<tr>
				<td>手机</td>
				<td><input type="text" class="layui-input" name="phone" lay-verify="required" value="{{ d.phone || '' }}"></td>
				<td>业务员</td>
				<td>
					<select name="userId" id="userIdSelect" lay-search><option value="">获取数据中...</option></select>
				</td>
			</tr>
			<tr>
				<td>详细地址</td>
				<td><input type="text" class="layui-input" name="address" value="{{ d.address || '' }}"></td>
				<td>帐号</td>
				<td><input type="text" class="layui-input" name="account" value="{{ d.account || '' }}"></td>
			</tr>
			<tr>
				<td>所在地</td>			
				<td colspan="3">
					<div class="layui-form-">
						<div class="layui-input-inline">
							<select lay-search name="provincesId" id="addProvince" lay-filter="addressSelect"></select></div>
						<div class="layui-input-inline">
							<select lay-search name="cityId" id="addCity" lay-filter="addressSelect"></select></div>
						<div class="layui-input-inline">
							<select lay-search name="countyId" id="addArea" lay-filter="addressSelect"></select></div>
					</div>
				</td>
			</tr>
			<tr style="display:none;">
				<td>
					<input type="hidden" name="customerAttributionId" id="customerAttributionId">
					<input type="hidden" name="customerTypeId" id="customerTypeId">
					<input type="hidden" name="id" value="{{ d.id ||"" }}">
					<span id="sureSaveCustom" lay-submit lay-filter="sureSaveCustom">确定</span>
				</td>
			</tr>
		</table>
	</div>
	`;
	var customer = {
		bigType: 0,
		smallType: 0,
		allType:{
			cw: 448,	//财务归属
			cg: 449,	//采购归属
			sc: 450,	//生产归属
			xs: 451,	//销售归属
			hq: 453,	//后勤归属
		},
		allSmallType:{
			ssdw:454,	//税收单位
			cs:455,		//菜商
			gys:456,	//供应商
			ds:457,		//电商
			sc:458,		//商超
			xx:459,		//线下
			jgd:460,	//加工点
		}
		
	};
	var allUserSelect = '<option value="">请选择</option>',allCustomerTypeSelect = '<option value="">请选择</option>';
	customer.render = function(opt){
		customer.bigType = customer.allType[opt.bigType];
		customer.smallType = customer.allSmallType[opt.smallType];
		$(opt.elem || '#app').append(TPL_MAIN);
		form.render();
		mytable.render({
			elem:'#'+tableId,
			url: myutil.config.ctx+'/ledger/customerPage?customerTypeId='+customer.smallType,
			toolbar:`
				<span class="layui-btn layui-btn-sm layui-btn-" lay-event="add">新增</span>
				<span class="layui-btn layui-btn-sm layui-btn-warm" lay-event="edit">修改</span>
			`,
			curd:{
				btn:[4],
				otherBtn: function(obj){
					var check = layui.table.checkStatus(tableId).data;
					if(obj.event=='edit'){
						if(check.length!=1)
							return myutil.emsg('只能编辑一条数据');
						addEditCustomer(check[0]);
					}else if(obj.event=='add'){
						addEditCustomer({});
					}
				}
			},
			autoUpdate:{
				deleUrl:'/ledger/deleteCustomer',
			},
			ifNull:'--',
			cols:[[
				{ type:'checkbox', },
				{ title:'名称', field:'name', },
				{ title:'手机号码', field:'phone', },
				{ title:'账户', field:'account', },
				{ title:'业务员', field:'user_userName', },
			]]
		})
		var userType = '';
		if( opt.smallType=='xx'){	//如果是线下客户只查，电子商务部与销售部
			userType = '&orgNameIds=35,29';
		}
		myutil.getData({
			url: myutil.config.ctx+'/system/user/pages?size=999&quit=0'+userType,
			success:function(r){
				layui.each(r,function(index,item){
					allUserSelect += '<option value="'+item.id+'">'+item.userName+'</option>';
				})
			}
		})
		form.on('submit(search)',function(obj){
			table.reload(tableId,{
				where: obj.field,
				page:{ curr:1 },
			})
		})
	}
	function addEditCustomer(data){
		var title = '新增',html = '';
		laytpl(TPL_ADD_EDIT).render(data,function(h){
			html = h;
		})
		if(data.id){
			title = '修改';
		}
		var addEditWin = layer.open({
			type:1,
			title: title,
			content: html,
			offset:'100px',
			area:['1000px','450px'],
			btn:['保存','取消'],
			btnAlign:'c',
			success:function(){
				$('#customerAttributionId').val(customer.bigType);
				$('#customerTypeId').val(customer.smallType);
				$('#userIdSelect').html(allUserSelect);
				getdataOfSelect(0,'addProvince');
				getdataOfSelect(data.provinces?data.provinces.id:'110000','addCity');
				getdataOfSelect(data.city?data.city.id:'110100','addArea');
				if(data.id){
					if(data.user)
						$('#userIdSelect').val(data.user.id);
					if(data.provinces)
						$('#addProvince').val(data.provinces.id);
					if(data.city)
						$('#addCity').val(data.city.id);
					if(data.provinces)
						$('#addArea').val(data.county.id);
					form.render();
				}else{
					
				}
				form.on('submit(sureSaveCustom)',function(obj){
					myutil.saveAjax({
						url:'/ledger/addCustomer',
						data:obj.field,
						success:function(){
							table.reload(tableId);
							layer.close(addEditWin);
						}
					})
				})
				form.on('select(addressSelect)',function(obj){
					var id = $(obj.elem).attr('id');
					switch(id){
					case 'addProvince': getdataOfSelect($('#addProvince').val(),'addCity'); ;
					case 'addCity':		getdataOfSelect($('#addCity').val(),'addArea'); break;
					}
					form.render();
				})
				form.render();
			},
			yes:function(){
				$('#sureSaveCustom').click();
			},
		})
	}
	function getdataOfSelect(parentId,select){			//根据父id获取下级地址菜单的信息
		var child=[];
		myutil.getDataSync({
			url: myutil.config.ctx+"/regionAddress/queryProvince",
			data:{parentId:parentId},
			success:function(data){
				var html='';
				for(var i=0;i<data.length;i++){
					html+='<option value="'+data[i].id+'">'+data[i].regionName+'</option>';
				}
				$('#'+select).html(html);
			}
		});
	}
	exports('customer',customer);
})