package com.bluewhite.product.primecost.common.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.product.primecost.common.entity.CommonData;
import com.bluewhite.product.primecost.common.service.CommonDataService;

@Controller
public class CommonDataAction {
	
	private final static Log log = Log.getLog(CommonDataAction.class);
	
	@Autowired
	private CommonDataService commonDataService;
	
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(CommonData.class,"number","loss","type");
	}

	/**
	 *新增
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addCommonData", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addCommonData(HttpServletRequest request,CommonData commonData) {
		CommonResponse cr = new CommonResponse();
		commonDataService.save(commonData);
		cr.setMessage("添加成功");
		return cr;
	}
	
	
	/**
	 *查找
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getCommonData", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse getCommonData(HttpServletRequest request,CommonData commonData) {
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(commonDataService.findPages(commonData))
				.toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
}
