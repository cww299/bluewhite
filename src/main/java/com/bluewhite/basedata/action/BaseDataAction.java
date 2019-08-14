package com.bluewhite.basedata.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.basedata.service.BaseDataService;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;



/**
 * 基础数据与动态表单数据控制层代码
 * @author TSOSilence
 *
 */
@Controller
@RequestMapping("basedata")
public class BaseDataAction{
	
	private final static ClearCascadeJSON ccj = 
			ClearCascadeJSON.get().addRetainTerm(BaseData.class, "id", "name", "type", "parentId", "ord", "remark","flag");
	@Autowired
	BaseDataService service;
	
	
	/**
	 * 获取基础数据类型列表
	 * @param request 请求
	 * @param response 返回
	 * @return cr 
	 */
	@RequestMapping(value = "types", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseDataTypes(HttpServletRequest request, HttpServletResponse response){
		CommonResponse cr = new CommonResponse();
		List<Map<String, String>> types = service.getBaseDataTypes();
		cr.setData(types);
		return cr;
	}
	
	/**
	 * 动态表单数据源及数据添加
	 * @param request 请求
	 * @param response 返回
	 * @return cr 
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse insertBaseData(HttpServletRequest request, HttpServletResponse response
			, BaseData baseData){
		CommonResponse cr = new CommonResponse();
		service.insertBaseDataType(baseData);
		cr.setMessage("成功");
		return cr;
	}
	
	/**
	 * 动态表单数据源及数据添加
	 * @param request 请求
	 * @param response 返回
	 * @return cr 
	 */
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	@ResponseBody
	public CommonResponse updateBaseData(HttpServletRequest request, HttpServletResponse response
			, BaseData baseData){
		CommonResponse cr = new CommonResponse();
		service.updateBaseDataType(baseData);
		cr.setMessage("成功");
		return cr;
	}
	
	/**
	 * 动态表单数据源及数据添加
	 * @param request 请求
	 * @param response 返回
	 * @return cr 
	 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse insertBaseData(Long id){
		CommonResponse cr = new CommonResponse();
		service.deleteBaseDataType(id);
		return cr;
	}
	
	/**
	 * 获取<code>type</code>类型的基础数据列表
	 * @param request 请求
	 * @param response 返回
	 * @param type 类型
	 * @return cr 
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseDataList(HttpServletRequest request, HttpServletResponse response
			, @RequestParam(value = "type", required = true)String type){
		CommonResponse cr = new CommonResponse();
		List<BaseData> baseDatas = service.getBaseDataListByType(type);
		cr.setData(ccj.format(baseDatas).toJSON());
		return cr;
	}
	
	
	/**
	 * 获取<code>type</code>类型的基础数据树形数据
	 * @param request 请求
	 * @param response 返回
	 * @param type 类型
	 * @return cr 
	 */
	@RequestMapping(value = "tree", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseDataTree(HttpServletRequest request, HttpServletResponse response
			, @RequestParam(value = "type", required = true)String type){
		CommonResponse cr = new CommonResponse();
		List<BaseData> baseDatas = service.getBaseDataTreeByType(type);
		cr.setData(ccj.format(baseDatas).toJSON());
		return cr;
	}
	/**
	 * 获取<code>type</code>类型的基础数据子数据
	 * @param request 请求
	 * @param response 返回
	 * @param type 类型
	 * @return cr 
	 */
	@RequestMapping(value= "children", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseDataChildren(HttpServletRequest request, HttpServletResponse response
			, @RequestParam(value = "id", required = true)Long id){
		CommonResponse cr = new CommonResponse();
		List<BaseData> baseDatas = service.getBaseDataChildrenById(id);
		cr.setData(ccj.format(baseDatas).toJSON());
		return cr;
	}
}
