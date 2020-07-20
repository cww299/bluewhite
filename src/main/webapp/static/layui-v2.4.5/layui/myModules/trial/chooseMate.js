/**选择面料模块
 * 2020/07/18
 */
layui.define(['mytable'],function(exports){
	var $ = layui.jquery,
		mytable = layui.mytable,
		table = layui.table,
		form = layui.form,
		laytpl = layui.laytpl,
		myutil = layui.myutil;
	
	const tableId = 'chooseMateTable';
	const tpl = `
	<div style="padding:10px">
		<table class="searchTable layui-form">
			<tr>
				<td></td>
				<td><input type="text" name="name" class="layui-input" placeholder="物料名"></td>
				<td></td>
				<td><input type="text" name="number" class="layui-input" placeholder="物料编号"></td>
				<td></td>
				{{# if(!d.isMate) { }}
					<td><select name="materielTypeIds">
						<option value="">面料类型</option>
						<option value="322">辅料</option>
						<option value="323">填充物</option>
						<option value="324">复合样</option>
						<option value="325">人工输入样</option>
						<option value="326">设计样</option></select></td>
				{{# } }}
				<td><button type="button" class="layui-btn" lay-submit lay-filter="searchMateTable">搜索</button>
					<span class="layui-badge">提示：双击进行选择</span></td>
			</tr>
		</table>
		
		<table id="${tableId}" lay-filter="${tableId}"></table>
	</div>
	`;
	
	//Id:321为面料、322为辅料 、323为填充物、 324为复合样、325人工、326设计
	
	var winIndex;
	
	const chooseMate = {
		mateId: '321',   // 只查找面料
		others: '322,323,324,325,326',	// 查面料外其他
		isMate: true,    // 默认查面料
	}
	
	
	chooseMate.choose = function(isMate = true){
		chooseMate.isMate = isMate
		return open(isMate);
	}
	
	function open() {
		return new Promise(function(resolve, reject) {
			layer.open({
				type: 1,
				title: '选择物料',
				area: ['80%', '80%'],
				content: laytpl(tpl).render(chooseMate),
				success: function(layerElem, layerIndex){
					winIndex = layerIndex;
					form.render()
					mytable.render({
						elem:'#' + tableId,
						url: myutil.config.ctx + '/product/getMaterielPage',
						where: {
							materielTypeIds: chooseMate.isMate ? chooseMate.mateId : chooseMate.others
						},
						cols:[[
						   { type:'checkbox',},
					       { title:'物料编号',   field:'number',	},
					       { title:'物料名',   field:'name',   },
					       { title:'单位',   field:'unit_name', 	},
					       { title:'物料类型',   field:'materielType_name',	},
					       { title:'物料定性',   field:'materialQualitative_name',	},
			            ]]
					})
					table.on('row(' + tableId + ')', function(obj){
						resolve(obj.data)
						layer.close(layerIndex)
					});
				}
			})
			
		})
	}
	
	form.on('submit(searchMateTable)', function(obj) {
		if(!obj.field.materielTypeIds) {
			obj.field.materielTypeIds = chooseMate.isMate ? chooseMate.mateId : chooseMate.others
		}
		table.reload(tableId, {
			where: obj.field,
			page: { curr: 1 }
		})
	})
	
	exports("chooseMate", chooseMate);
})